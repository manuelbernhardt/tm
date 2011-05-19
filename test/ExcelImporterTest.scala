import importer.ExcelImporter
import java.io.File
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit

/**
 * 
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

class ExcelImporterTest extends AssertionsForJUnit {

  @Test
  def dummyTest() {
    val i:ExcelImporter = new ExcelImporter
    val f:File = new File("test/ExcelImport.xls")
    i.importFile(f)
  }

}
