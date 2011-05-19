package importer

import java.io.{FileInputStream, File}
import collection.JavaConversions._
import org.apache.poi.ss.usermodel.{Row, WorkbookFactory, Workbook, Cell}
import java.lang.Class
import collection.immutable.List
import java.lang.reflect.{Method, Field}
import collection.mutable.HashMap
import collection.mutable.Map
import models.tm.{Project, TMUser, Requirement, Defect}
import reflect.ClassManifest
import models.general.{TemporalModel, CompositeModel}

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

class ExcelImporter extends Importer {


  @throws(classOf[Throwable])
  def importFile(baseModelType: Class[_ <: CompositeModel], project: Project, file: File): java.util.List[ImportError] = {

    val wb: Workbook = WorkbookFactory.create(new FileInputStream(file));
    val sheet = wb.getSheetAt(0);

    val contextData: Map[String, AnyRef] = new HashMap[String, AnyRef]

    sheet.view.zipWithIndex foreach {
      case (row: Row, rowIndex: Int) => {

        val instance = baseModelType.getConstructor(classOf[Project]).newInstance(project)

        cellConverters.get(baseModelType).get.view.zipWithIndex foreach {
          case (rule: ColumnImportRule, colIndex: Int) => {
            println(rowIndex + ":" + colIndex + " " + rule)

            val c: Cell = row.getCell(colIndex)
            if (c != null) {
              if (rule.hasValidType(c.getCellType)) {
                val value: AnyRef = rule.getValue(c, contextData)
                val field: Field = baseModelType.getField(rule.propertyName)
                try {
                  field.set(instance, value)
                } catch {
                  case _ => {
                    val setter: Method = baseModelType.getMethod("set" + rule.propertyName.capitalize)
                    try {
                      setter.invoke(instance, value)
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

  def getValue(cell: Cell, contextData: Map[String, AnyRef]): AnyRef = {
    valueManifest match {
      case m if m <:< classManifest[String] => cell.getStringCellValue
      case m if m <:< classManifest[Number] =>java.lang.Double.valueOf(cell.getNumericCellValue)
      case m if m <:< classManifest[Boolean] => java.lang.Boolean.valueOf(cell.getBooleanCellValue)
      case m if m <:< classManifest[TemporalModel] => {
        val converter: ModelConverter[_] = modelConverters.get(classType.getName).get // TODO orElse add error
        val value = converter.convert(cell.getStringCellValue, contextData)

        if (converter.afterConvert != null) {
          converter.afterConvert(contextData)
        }

        value.asInstanceOf[AnyRef]
      }
    }

  }
}

case class StringColumnImportRule(colIndex: Int, propertyName: String, override val classType: Class[_] = classOf[java.lang.String], override val cellType: Int = Cell.CELL_TYPE_STRING) extends ColumnImportRule()

case class UserColumnImportRule(colIndex: Int, propertyName: String, override val classType: Class[_] = classOf[TMUser], override val cellType: Int = Cell.CELL_TYPE_STRING) extends ColumnImportRule()
