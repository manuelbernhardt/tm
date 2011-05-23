import importer.ExcelImporter
import java.io.File
import models.account.Account
import models.SchemaCreation
import models.tm.{ProjectCategory, Project, Requirement}
import org.hibernate.{Session, SessionFactory}
import org.junit.{Before, Test}
import org.scalatest.junit.AssertionsForJUnit
import play.db.jpa.JPA
import play.test.UnitTestCase

/**
 * Does not work - bug in Play
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

class ZDefunctExcelImporterTest extends UnitTestCase with AssertionsForJUnit {

  @Before
  def setUp: Unit = {
    new SchemaCreation((JPA.em.getDelegate.asInstanceOf[Session]).getSessionFactory).createTriggers
    new SeleniumTestDataLoader
  }

  @Test
  def dummyTest() {

    val a:Account = JPA.em().createQuery("from Account a where a.subdomain = 'oxiras'").getResultList.get(0).asInstanceOf[Account]
    val p:Project = JPA.em().createQuery("from Project p where p.name = 'Test Management Application'").getResultList.get(0).asInstanceOf[Project]

    val i:ExcelImporter = new ExcelImporter
    val f:File = new File("test/ExcelImport.xls")
    i.importFile(classOf[Requirement], null, p, f)
  }

}
