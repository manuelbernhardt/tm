package controllers;

import controllers.deadbolt.Restrict;
import models.general.UnitRole;
import models.project.ApproachTestCycle;
import play.data.validation.Valid;
import play.data.validation.Validation;

public class Approach extends TMController {

    @Restrict(UnitRole.USER)
    public static void index() {
        render();
    }

    @Restrict(UnitRole.USER)
    public static void cycleDetails(Long cycleId) {
        ApproachTestCycle cycle = getCycle(cycleId);
        render(cycle);
    }

    @Restrict(UnitRole.USER)
    public static void edit(@Valid ApproachTestCycle cycle) {
        checkInAccount(cycle);
        if (Validation.hasErrors()) {
            // TODO return validation errors, somehow
            error();
        }
        cycle.save();
        ok();
    }


    /**
     * Resolves an ApproachTestCycle given a TreeNode id
     */
    private static ApproachTestCycle getCycle(Long cycleId) {
        ApproachTestCycle cycle = null;
        if (cycleId != null) {
            cycle = ApproachTestCycle.findById(cycleId);
            checkInAccount(cycle);
        }
        return cycle;
    }


}
