import org.hibernate.mapping.PersistentClass
import org.junit.{Test, Before}
import org.scalatest.junit.AssertionsForJUnit
import play.db.Model
import play.test.{UnitTestCase, UnitTest}

class ModelIntegrityTest extends UnitTestCase with AssertionsForJUnit {

  val allowedNonAccountEntities: List[String] = List("models.general.Product", "models.account.Account", "models.account.PurchaseOrder", "models.tabularasa.TableModel", "models.tabularasa.TableOwner", "models.tabularasa.TableColumn", "models.tree.jpa.TreeNode")

  @Before
  def setUp: Unit = {
    new TestDataLoader
  }

  @Test
  def verifyAccountFilters = {
    /*    val it: java.util.Iterator[_] = TestJPAConfigurationExtension.configuration.getHibernateConfiguration().getClassMappings();

    while (it.hasNext) {
      val m: PersistentClass = it.next.asInstanceOf[PersistentClass];
      if (classOf[Model].isAssignableFrom(m.getMappedClass)) {
        if (!m.getFilterMap.containsKey("account") && !allowedNonAccountEntities.contains(m.getMappedClass.getName)) {
          fail("Account entity " + m.getMappedClass.getName + " has no accout filter enabled")
        }
      }
    }
    */
  }
}