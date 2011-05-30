package importer

import java.io.{FileInputStream, File}
import collection.JavaConversions._
import org.apache.poi.ss.usermodel.{Row, WorkbookFactory, Workbook, Cell}
import collection.immutable.List
import java.lang.reflect.{Method, Field}
import reflect.ClassManifest
import java.lang.{String, Class}
import util.Logger
import models.tm.test.Tag
import play.db.jpa.Model
import models.tm.{ProjectTreeNode, Project, TMUser}
import java.util.Map

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

class ExcelImporter extends Importer {

  val afterCallbacks = for (converter <- TMImport.modelConverters.values) yield (converter.afterConvert _)

  @throws(classOf[Throwable])
  def importFile(baseModelType: Class[_ <: Model], contextData: java.util.Map[String, AnyRef], project: Project, file: File): java.util.List[ImportError] = {

    val wb: Workbook = WorkbookFactory.create(new FileInputStream(file));
    val sheet = wb.getSheetAt(0);

    sheet.view.zipWithIndex foreach {
      // skip the header line
      case (row: Row, rowIndex: Int) if rowIndex > 0 => {

        val instance: Model = baseModelType.getConstructor(classOf[Project]).newInstance(project)
        var anyDataAtAll = false

        // TODO think of a better way of passing the project... the instance creation should be decoupled from the main importer
        contextData.put(TMImport.PROJECT, project)

        // go over the column conversion rules for each column
        TMImport.cellConverters.get(baseModelType.getName).get.view.zipWithIndex foreach {
          case (rule: ColumnImportRule, colIndex: Int) => {
            Logger.debug(rowIndex + ":" + colIndex + " " + rule)

            val c: Cell = row.getCell(colIndex)
            if (c != null && c.getCellType() != Cell.CELL_TYPE_BLANK) {
              if (rule.hasValidType(c.getCellType)) {
                rule.computeConvertedValue(c, baseModelType, instance, contextData, this) match {
                  case Some(value) => anyDataAtAll = setFieldValue(baseModelType, rule, instance, value)
                  case None => // do nothing
                }
              }
            }
          }
        }

        if (anyDataAtAll) {
          val created: Boolean = instance.create()

          if (!created) {
            // TODO add import error
          } else {
            afterCallbacks foreach(f => f(instance, contextData))
          }
        }

      }
      case _ => // skip this row
    }

    List[ImportError]()
  }

  def setFieldValue(baseModelType: Class[_ <: Model], rule: ColumnImportRule, instance: Model, v: Any): Boolean = {
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

  def computeConvertedValue(cell: Cell, baseModelType: Class[_ <: Model], instance: Model, contextData: java.util.Map[String, AnyRef], importer: ExcelImporter): Option[Any] = {
    if(cell.getStringCellValue != null && cell.getStringCellValue.trim().length() > 0) {
      valueManifest match {
        case m if m <:< classManifest[String] => Some(cell.getStringCellValue)
        case m if m <:< classManifest[Number] => Some(java.lang.Double.valueOf(cell.getNumericCellValue))
        case m if m <:< classManifest[Boolean] => Some(java.lang.Boolean.valueOf(cell.getBooleanCellValue))
        case m if m <:< classManifest[Model] => {
          val converter: ModelConverter[_] = TMImport.modelConverters.get(classType.getName).get // TODO orElse add error
          val value: Any = converter.convert(cell.getStringCellValue, baseModelType, instance, contextData)
          if(value != null) {
            Some(value)
          } else {
            None
          }
        }
      }
    } else {
      None
    }

  }
}

case class StringColumnImportRule(colIndex: Int, propertyName: String, override val classType: Class[_] = classOf[java.lang.String], override val cellType: Int = Cell.CELL_TYPE_STRING) extends ColumnImportRule

case class UserColumnImportRule(colIndex: Int, propertyName: String, override val classType: Class[_] = classOf[TMUser], override val cellType: Int = Cell.CELL_TYPE_STRING) extends ColumnImportRule

case class TagsColumnImportRule(colIndex: Int, propertyName: String, override val classType: Class[_] = classOf[Tag], override val cellType: Int = Cell.CELL_TYPE_STRING) extends ColumnImportRule

case class ProjectTreeNodeColumnImportRule(colIndex: Int, propertyName: String, override val classType: Class[_] = classOf[ProjectTreeNode], override val cellType: Int = Cell.CELL_TYPE_STRING) extends ColumnImportRule
