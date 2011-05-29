package importer

import java.io.{FileInputStream, File}
import collection.JavaConversions._
import org.apache.poi.ss.usermodel.{Row, WorkbookFactory, Workbook, Cell}
import collection.immutable.List
import java.lang.reflect.{Method, Field}
import models.tm.{Project, TMUser}
import reflect.ClassManifest
import models.general.TemporalModel
import java.lang.{String, Class}
import util.Logger
import models.tm.test.Tag
import play.db.jpa.GenericModel

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

class ExcelImporter extends Importer {

  @throws(classOf[Throwable])
  def importFile(baseModelType: Class[_ <: GenericModel], contextData: java.util.Map[String, AnyRef], project: Project, file: File): java.util.List[ImportError] = {

    val wb: Workbook = WorkbookFactory.create(new FileInputStream(file));
    val sheet = wb.getSheetAt(0);

    sheet.view.zipWithIndex foreach {
      // skip the header line
      case (row: Row, rowIndex: Int) if rowIndex > 0 => {

        val instance: GenericModel = baseModelType.getConstructor(classOf[Project]).newInstance(project)
        var anyDataAtAll = false

        // TODO think of a better way of passing the project... the instance creation should be decoupled from the main importer
        contextData.put(TMImport.PROJECT, project)

        // go over the column conversion rules for each column
        TMImport.cellConverters.get(baseModelType.getName).get.view.zipWithIndex foreach {
          case (rule: ColumnImportRule, colIndex: Int) => {
            Logger.debug(rowIndex + ":" + colIndex + " " + rule)

            val c: Cell = row.getCell(colIndex)
            if (c != null) {
              if (rule.hasValidType(c.getCellType)) {
                val value: Any = rule.computeConvertedValue(c, baseModelType, instance, contextData).get
                anyDataAtAll = setFieldValue(baseModelType, rule, instance, value)
              }
            }
          }
        }

        // because Play defines a scala Model, we can't really use create() directly here so we use invocation. doh.

        if (anyDataAtAll) {
          val create: Method = baseModelType.getMethod("create")
          val created: Boolean = create.invoke(instance).asInstanceOf[Boolean]

          if (!created) {
            // TODO add import error
          }
        }

      }
      case _ => // skip this row
    }

    return List[ImportError]()
  }

  def setFieldValue(baseModelType: Class[_ <: GenericModel], rule: ColumnImportRule, instance: GenericModel, v: Any): Boolean = {
    val field: Field = baseModelType.getField(rule.propertyName)
    try {
      Logger.debug("Setting value '%s' to field %s of entity %s via field assignment" format (v, rule.propertyName, baseModelType.getName))
      field.set(instance, v)
      true
    } catch {
      case _ => {
        try {
          val setter: Method = baseModelType.getMethod("set" + rule.propertyName.capitalize)
          Logger.debug("Setting value '%s' to field %s of entity %s via setter invocation" format (v, rule.propertyName, baseModelType.getName))
          setter.invoke(instance, v.asInstanceOf[AnyRef])
          true
        } catch {
          case _ => throw new ImportError("Could not set field %s of entity %s to value '%s'".format(rule.propertyName, baseModelType.getName, v))
        }
      }
    }

  }
}

trait ColumnImportRule {

  val colIndex: Int
  val propertyName: String
  val classType: Class[_]
  // keep the erasure thanks to Manifest
  protected val valueManifest = ClassManifest.fromClass(classType)
  val cellType: Int

  def hasValidType(cellType: Int): Boolean = {
    cellType == this.cellType
  }

  def computeConvertedValue(cell: Cell, baseModelType: Class[_ <: GenericModel], instance:GenericModel, contextData: java.util.Map[String, AnyRef]): Option[Any] = {
    valueManifest match {
      case m if m <:< classManifest[String] => Some(cell.getStringCellValue)
      case m if m <:< classManifest[Number] => Some(java.lang.Double.valueOf(cell.getNumericCellValue))
      case m if m <:< classManifest[Boolean] => Some(java.lang.Boolean.valueOf(cell.getBooleanCellValue))
      case m if m <:< classManifest[TemporalModel] => {
        val converter: ModelConverter[_] = TMImport.modelConverters.get(classType.getName).get // TODO orElse add error
        val value: Any = converter.convert(cell.getStringCellValue, baseModelType, instance, contextData)

        if (converter.afterConvert != null) {
          converter.afterConvert(contextData)
        }
        Some(value)
      }
    }

  }
}

case class StringColumnImportRule(colIndex: Int, propertyName: String, override val classType: Class[_] = classOf[java.lang.String], override val cellType: Int = Cell.CELL_TYPE_STRING) extends ColumnImportRule()
case class UserColumnImportRule(colIndex: Int, propertyName: String, override val classType: Class[_] = classOf[TMUser], override val cellType: Int = Cell.CELL_TYPE_STRING) extends ColumnImportRule()
case class TagsColumnImportRule(colIndex: Int, propertyName: String, override val classType: Class[_] = classOf[Tag], override val cellType: Int = Cell.CELL_TYPE_STRING) extends ColumnImportRule()
