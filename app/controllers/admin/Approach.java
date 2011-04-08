package controllers.admin;

import controllers.ApproachTree;
import controllers.Lookups;
import controllers.TMController;
import controllers.deadbolt.Restrict;
import models.general.UnitRole;
import models.tm.Project;
import models.tm.approach.Release;
import models.tm.approach.TestCycle;
import models.tree.jpa.TreeNode;
import play.data.validation.Valid;
import play.data.validation.Validation;

public class Approach extends TMController {

    @Restrict(UnitRole.PROJECTEDIT)
    public static void cycles(Long projectId) {
        Project project = Lookups.getProject(projectId);
        render(project);
    }

    @Restrict(UnitRole.PROJECTEDIT)
    public static void cycleDetails(Long cycleId) {
        TestCycle cycle = getCycle(cycleId);
        render(cycle);
    }

    @Restrict(UnitRole.PROJECTEDIT)
    public static void releaseDetails(Long releaseId) {
        Release release = getRelease(releaseId);
        render(release);
    }


    @Restrict(UnitRole.PROJECTEDIT)
    public static void editCycle(@Valid TestCycle cycle) {
        checkInAccount(cycle);
        if (Validation.hasErrors()) {
            // TODO return validation errors, somehow
            error();
        }
        cycle.save();
        ok();
    }

    @Restrict(UnitRole.PROJECTEDIT)
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
