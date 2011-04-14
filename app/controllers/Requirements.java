package controllers;

import models.tm.Requirement;
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

    public static void requirementsDetailsData(Long baseObjectId, String[] fields) {
        // TODO implement this right
        Object base = Requirement.findById(baseObjectId);

        renderFields(base, fields);
    }

    public static void linked(Long requirementId) {
        Requirement requirement = getRequirement(requirementId);
        render(requirement);
    }
    

    public static void edit(@Valid Requirement requirement) {
        checkInAccount(requirement);
        if(Validation.hasErrors()) {
            // TODO handle validation errors in view somehow
            error();
        }
        requirement.save();
        ok();
    }

    /**
     * Resolves a Requirement given a TreeNode id
     */
    private static Requirement getRequirement(Long requirementId) {
        if (requirementId == null) {
            return null;
        }
        Requirement requirement = Requirement.<Requirement>findById(requirementId);
        if (requirement == null) {
            return null;
        }
        checkInAccount(requirement);
        return requirement;
    }

}
