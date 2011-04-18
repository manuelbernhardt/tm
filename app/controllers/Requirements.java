package controllers;

import java.util.List;

import controllers.tabularasa.TableController;
import models.tm.Defect;
import models.tm.Requirement;
import models.tm.test.Instance;
import models.tm.test.Script;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.db.jpa.GenericModel;

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
        Requirement requirement = Lookups.getRequirement(requirementId);
        render(requirement);
    }

    public static void requirementsDetailsData(Long baseObjectId, String[] fields) {
        Object base = Lookups.getRequirement(baseObjectId);
        renderFields(base, fields);
    }

    public static void linkScript(Long requirementId, Long scriptId) {
        Requirement requirement = Lookups.getRequirement(requirementId);
        Script script = Lookups.getScript(scriptId);
        requirement.linkedScripts.add(script);
        requirement.save();
        ok();
    }

    public static void unlinkScript(Long requirementId, Long scriptId) {
        Requirement requirement = Lookups.getRequirement(requirementId);
        Script script = Lookups.getScript(scriptId);
        requirement.linkedScripts.remove(script);
        requirement.save();
        ok();
    }


    public static void linkedScripts(String tableId,
                                     String sColumns,
                                     String sEcho,
                                     Long requirementId) {
        GenericModel.JPAQuery query = Requirement.find("select s from Requirement r, Script s where r.id = ? and s in elements(r.linkedScripts)", requirementId);
        List<Script> scripts = query.fetch();
        long totalRecords = Script.count("project = ?", getActiveProject());
        TableController.renderJSON(scripts, Script.class, totalRecords, sColumns, sEcho);
    }

    public static void linkedInstances(String tableId,
                                     String sColumns,
                                     String sEcho,
                                     Long requirementId) {
        GenericModel.JPAQuery query = Requirement.find("select i from Requirement r, Script s, Instance i where r.id = ? and s in elements(r.linkedScripts) and i.script = s", requirementId);
        List<Instance> instances = query.fetch();
        long totalRecords = Instance.count("project = ?", getActiveProject());
        TableController.renderJSON(instances, Instance.class, totalRecords, sColumns, sEcho);
    }

    public static void linkedDefects(String tableId,
                                     String sColumns,
                                     String sEcho,
                                     Long requirementId) {
        GenericModel.JPAQuery query = Requirement.find("select d from Requirement r, Script s, Instance i, Run run, Defect d where r.id = ? and s in elements(r.linkedScripts) and i.script = s and i.script = s and run.instance = i and run.defect = d", requirementId);
        List<Defect> instances = query.fetch();
        long totalRecords = Instance.count("project = ?", getActiveProject());
        TableController.renderJSON(instances, Defect.class, totalRecords, sColumns, sEcho);
    }

    public static void edit(@Valid Requirement requirement) {
        checkInAccount(requirement);
        if (Validation.hasErrors()) {
            // TODO handle validation errors in view somehow
            error();
        }
        requirement.save();
        ok();
    }

}
