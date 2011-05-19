import importer.ExcelImporter
import java.io.File
import models.account.Account
import models.tm.{ProjectCategory, Project, Requirement}
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit

/**
 * 
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

class ExcelImporterTest extends AssertionsForJUnit {

  @Test
  def dummyTest() {

    val a:Account = new Account()
    a.name = "Oxiras"
    val p:Project = new Project("Dummy", a, null)

    val i:ExcelImporter = new ExcelImporter
    val f:File = new File("test/ExcelImport.xls")
    i.importFile(classOf[Requirement], p, f)
  }

}
