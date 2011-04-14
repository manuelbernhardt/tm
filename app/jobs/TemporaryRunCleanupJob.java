package jobs;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import models.tm.test.Run;
import play.jobs.Job;
import play.jobs.On;

/**
 * TODO logging, including exception logging
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

/**
 * Fire at 12pm (noon) every day *
 */
@On("0 0 12 * * ?")
public class TemporaryRunCleanupJob extends Job {

    @Override
    public void doJob() throws Exception {
        // we have to make sure that this date is going to be always in sync with the creation timestamp...it should, unless the db server runs on a machine with a different configuration
        Calendar c = Calendar.getInstance();
        // if a Run is temporary for 5 hours it probably can be trashed
        c.add(Calendar.HOUR, -5);
        Timestamp t = new Timestamp(c.getTime().getTime());
        List<Run> runs = Run.find("from Run r where r.temporary = true and r.created <= ?", t).<Run>fetch();
        for (Run run : runs) {
            run.delete();
        }
    }

    @Override
    public void onException(Throwable e) {
        // TODO logging
        e.printStackTrace();
        super.onException(e);
    }
}
