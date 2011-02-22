import models.Account;
import models.Project;
import org.junit.Test;
import play.test.UnitTest;

public class BasicTest extends UnitTest {

    @Test
    public void modelCreation() {
        Account account = new Account();
        account.name = "Oxiras";
        account.create();

        Project p = new Project();
        p.account = account;
        p.create();

        Project found = Project.find("id = ? and account = ?", 1l, account).first();
        assertNotNull(found);
    }

}
