import models.tm.Project;
import models.tm.ProjectWidget;
import org.junit.Test;
import play.db.jpa.JPA;
import play.test.UnitTest;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ModelTest extends UnitTest {

    @Test
    public void doTest() {
        Project p = Project.all().first();

        ProjectWidget w = new ProjectWidget(p);
        w.params.put("foo", "bar");
        w.create();

        ProjectWidget w1 = new ProjectWidget(p);
        w1.params.put("foo", "bar");
        w1.create();

        JPA.em().flush();

        System.out.println(w.params.size());
        System.out.println(w1.params.size());


    }
}
