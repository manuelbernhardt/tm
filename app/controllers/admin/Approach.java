package controllers.admin;

import controllers.Lookups;
import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.deadbolt.Restrictions;
import models.general.UnitRole;
import models.tm.Project;
import models.tm.approach.Release;
import models.tm.approach.TestCycle;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.With;

@With(Deadbolt.class)
public class Approach extends TMController {

    @Restrictions({@Restrict(UnitRole.PROJECTEDIT), @Restrict(UnitRole.PROJECTADMIN)})
    public static void cycleDetails(Long baseObjectId, String[] fields) {
        TestCycle cycle = Lookups.getCycle(baseObjectId);
        renderFields(cycle, fields);
    }

    @Restrictions({@Restrict(UnitRole.PROJECTEDIT), @Restrict(UnitRole.PROJECTADMIN)})
    public static void releaseDetails(Long baseObjectId, String[] fields) {
        Release release = Lookups.getRelease(baseObjectId);
        renderFields(release, fields);
    }


    @Restrictions({@Restrict(UnitRole.PROJECTEDIT), @Restrict(UnitRole.PROJECTADMIN)})
    public static void editCycle(@Valid TestCycle cycle) {
        checkInAccount(cycle);
        if (Validation.hasErrors()) {
            // TODO return validation errors, somehow
            error();
        }
        cycle.save();
        ok();
    }

    @Restrictions({@Restrict(UnitRole.PROJECTEDIT), @Restrict(UnitRole.PROJECTADMIN)})
    public static void editRelease(@Valid Release release) {
        checkInAccount(release);
        if (Validation.hasErrors()) {
            // TODO return validation errors, somehow
            error();
        }
        release.save();
        ok();
    }


}
