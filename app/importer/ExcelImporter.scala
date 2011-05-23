package importer

import java.io.{FileInputStream, File}
import collection.JavaConversions._
import org.apache.poi.ss.usermodel.{Row, WorkbookFactory, Workbook, Cell}
import collection.immutable.List
import java.lang.reflect.{Method, Field}
import collection.mutable.Map
import models.tm.{Project, TMUser, Requirement, Defect}
import reflect.ClassManifest
import models.general.{TemporalModel, CompositeModel}
import java.lang.{String, Class}
import play.db.jpa.Model
import util.Logger

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

class ExcelImporter extends Importer {


  @throws(classOf[Throwable])
  def importFile(baseModelType: Class[_ <: CompositeModel], contextData: java.util.Map[String, AnyRef], project: Project, file: File): java.util.List[ImportError] = {

    val wb: Workbook = WorkbookFactory.create(new FileInputStream(file));
    val sheet = wb.getSheetAt(0);

    sheet.view.zipWithIndex foreach {
      // skip the header line
      case (row: Row, rowIndex: Int) if rowIndex > 0 => {

        val instance:CompositeModel = baseModelType.getConstructor(classOf[Project]).newInstance(project)
        var anyDataAtAll = false

        // go over the column conversion rules for each column
        cellConverters.get(baseModelType).get.view.zipWithIndex foreach {
          case (rule: ColumnImportRule, colIndex: Int) => {
            Logger.debug(rowIndex + ":" + colIndex + " " + rule)

            val c: Cell = row.getCell(colIndex)
            if (c != null) {
              if (rule.hasValidType(c.getCellType)) {
                rule.computeConvertedValue(c, contextData) map {
                  v => {
                    val field: Field = baseModelType.getField(rule.propertyName)
                    try {
                      Logger.debug("Setting value '%s' to field %s of entity %s via field assignment", (v, rule.propertyName, baseModelType.getName))
                      field.set(instance, v)
                      anyDataAtAll = true
                    } catch {
                      case _ => {
                        val setter: Method = baseModelType.getMethod("set" + rule.propertyName.capitalize)
                        try {
                          Logger.debug("Setting value '%s' to field %s of entity %s via setter invocation", (v, rule.propertyName, baseModelType.getName))
                          setter.invoke(instance, v.asInstanceOf[AnyRef])
                          anyDataAtAll = true
                        } catch {
                          case _ => throw new ImportError("Could not set field %s of entity %s".format(rule.propertyName, baseModelType.getName))
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }

        // because Play defines a scala Model, we can't really use create() directly here.

        if(anyDataAtAll) {
          val create:Method = baseModelType.getMethod("create")
          val created: Boolean = create.invoke(instance).asInstanceOf[Boolean]

          if(!created) {
            // TODO add import error
          }
        }

      }
      case _ => // skip this row
    }

    return List[ImportError]()
  }


  val cellConverters = Map[Class[_ <: CompositeModel], List[ColumnImportRule]](
    classOf[Requirement] -> List(
      StringColumnImportRule(0, "name"),
      StringColumnImportRule(1, "description"),
      UserColumnImportRule(2, "createdBy")
      // later ,StringColumnImportRule(3, "tags")
    ),
    classOf[Defect] -> List(
      StringColumnImportRule(0, "name"),
      StringColumnImportRule(1, "description"),
      StringColumnImportRule(2, "submittedBy")
    )
  )

}

trait ColumnImportRule {

  val modelConverters = Map[String, ModelConverter[_]](
    classOf[Requirement].getName -> RequirementConverter,
    classOf[TMUser].getName -> TMUserConverter
  )

  val colIndex: Int
  val propertyName: String
  val classType: Class[_]
  // keep the erasure thanks to Manifest
  protected val valueManifest = ClassManifest.fromClass(classType)
  val cellType: Int

  def hasValidType(cellType: Int): Boolean = {
    cellType == this.cellType
  }

  def computeConvertedValue(cell: Cell, contextData: java.util.Map[String, AnyRef]): Option[Any] = {
    valueManifest match {
      case m if m <:< classManifest[String] => Option(cell.getStringCellValue)
      case m if m <:< classManifest[Number] => Option(java.lang.Double.valueOf(cell.getNumericCellValue))
      case m if m <:< classManifest[Boolean] => Option(java.lang.Boolean.valueOf(cell.getBooleanCellValue))
      case m if m <:< classManifest[TemporalModel] => {
        val converter: ModelConverter[_] = modelConverters.get(classType.getName).get // TODO orElse add error
        val value: Option[Any] = converter.convert(cell.getStringCellValue, contextData)

        if (converter.afterConvert != null) {
          converter.afterConvert(contextData)
        }
        value
      }
    }

  }
}

case class StringColumnImportRule(colIndex: Int, propertyName: String, override val classType: Class[_] = classOf[java.lang.String], override val cellType: Int = Cell.CELL_TYPE_STRING) extends ColumnImportRule()

case class UserColumnImportRule(colIndex: Int, propertyName: String, override val classType: Class[_] = classOf[TMUser], override val cellType: Int = Cell.CELL_TYPE_STRING) extends ColumnImportRule()
