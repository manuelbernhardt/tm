import models.general.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@OnApplicationStart
public class Bootstrap extends Job {
    @Override
    public void doJob() throws Exception {
        if(User.count() == 0) {
            Fixtures.load("initial-data.yml");
        }
    }
}
