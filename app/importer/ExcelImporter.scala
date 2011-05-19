package importer

import java.io.{FileInputStream, File}
import org.apache.poi.ss.usermodel.{WorkbookFactory, Workbook}
import collection.JavaConversions._

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

class ExcelImporter extends Importer {

  @throws(classOf[Throwable])
  def importFile(file: File): java.util.List[ImportError] = {

    /*
    try {

        InputStream inp = new FileInputStream(file);

        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet = wb.getSheetAt(0);

        for (Row row : sheet) {
            for (Cell cell : row) {
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                System.out.print(cellRef.formatAsString());
                System.out.print(" - ");

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.println(cell.getRichStringCellValue().getString());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            System.out.println(cell.getDateCellValue());
                        } else {
                            System.out.println(cell.getNumericCellValue());
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.println(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        System.out.println(cell.getCellFormula());
                        break;
                    default:
                        System.out.println();
                }
            }
        }
    } catch (t:Throwable) {
        error(t.getLocalizedMessage());
    }

    */


    val wb:Workbook = WorkbookFactory.create(new FileInputStream(file));
    val sheet = wb.getSheetAt(0);

    sheet.foreach(row => {
      row.foreach(cell => {
        println(cell)
      })
    })




    return null;

  }

}