package controllers;

import controllers.deadbolt.Restrict;
import models.general.UnitRole;
import models.project.approach.Release;
import models.project.approach.TestCycle;
import models.tree.jpa.TreeNode;
import play.data.validation.Valid;
import play.data.validation.Validation;

public class Approach extends TMController {

    @Restrict(UnitRole.USER)
    public static void index() {
        render();
    }

    @Restrict(UnitRole.USER)
    public static void cycleDetails(Long cycleId) {
        TestCycle cycle = getCycle(cycleId);
        render(cycle);
    }

    @Restrict(UnitRole.USER)
    public static void releaseDetails(Long releaseId) {
        Release release = getRelease(releaseId);
        render(release);
    }


    @Restrict(UnitRole.USER)
    public static void editCycle(@Valid TestCycle cycle) {
        checkInAccount(cycle);
        if (Validation.hasErrors()) {
            // TODO return validation errors, somehow
            error();
        }
        cycle.save();
        ok();
    }

    @Restrict(UnitRole.USER)
    public static void editRelease(@Valid Release release) {
        checkInAccount(release);
        if (Validation.hasErrors()) {
            // TODO return validation errors, somehow
            error();
        }
        release.save();
        ok();
    }


    private static TestCycle getCycle(Long cycleId) {
        TestCycle cycle = null;
        if (cycleId != null) {
            TreeNode node = TreeNode.find(cycleId, ApproachTree.APPROACH_TREE);
            if(node == null) {
                return null;
            }
            cycle = TestCycle.findById(node.nodeId);
            checkInAccount(cycle);
        }
        return cycle;
    }

    private static Release getRelease(Long releaseId) {
        Release release = null;
        if (releaseId != null) {
            TreeNode node = TreeNode.find(releaseId, ApproachTree.APPROACH_TREE);
            if (node == null) {
                return null;
            }
            release = Release.findById(node.nodeId);
            checkInAccount(release);
        }
        return release;
    }


}
