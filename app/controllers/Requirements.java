package controllers;

import models.project.Requirement;
import models.tree.jpa.TreeNode;
import play.data.validation.Valid;
import play.data.validation.Validation;

/**
 * TODO security
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Requirements extends TMController {

    public static void index() {
        render();
    }

    public static void requirementDetails(Long requirementId) {
        Requirement requirement = getRequirement(requirementId);
        render(requirement);
    }

    public static void linked(Long requirementId) {
        Requirement requirement = getRequirement(requirementId);
        render(requirement);
    }

    public static void edit(@Valid Requirement requirement) {

        checkInAccount(requirement);

        if(Validation.hasErrors()) {
            // TODO not the right thing to do, I suppose, since we do an AJAX call
            render("@index", requirement);
        }

        requirement.save();

        ok();

    }

    /**
     * Resolves a Requirement given a TreeNode id
     */
    private static Requirement getRequirement(Long requirementId) {
        Requirement requirement = null;
        if(requirementId != null) {
            TreeNode n = TreeNode.findById(requirementId);
            if(n != null) {
                requirement = Requirement.findById(n.getNodeId());
                checkInAccount(requirement);
            }
        }
        return requirement;
    }


}
