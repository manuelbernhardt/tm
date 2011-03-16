package controllers;

import models.project.Requirement;
import models.tree.jpa.TreeNode;

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
