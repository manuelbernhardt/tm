import models.project.Project;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

public class ModelTest extends UnitTest {

    @Before
    public void before() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("testModel.yml");
    }

    @Test
    public void projectCreation() {

        Project found = Project.find("name = ?", "Test Project 1").first();
        assertNotNull(found);

        Project found2 = Project.find("name = ?", "Test Project 2").first();
        assertNotNull(found);
    }

}
