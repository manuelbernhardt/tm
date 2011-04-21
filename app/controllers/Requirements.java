package controllers;

import java.util.List;

import controllers.tabularasa.TableController;
import models.tm.Defect;
import models.tm.Requirement;
import models.tm.test.Instance;
import models.tm.test.Script;
import models.tm.test.Tag;
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

    public static void requirementsDetailsData(Long baseObjectId, String[] fields) {
        Object base = Lookups.getRequirement(baseObjectId);
        renderFields(base, fields);
    }

    public static void tags(Long requirementId) {
        Requirement requirement = Lookups.getRequirement(requirementId);
        Lookups.tags(requirement.tags);
    }

    public static void allTags(String q) {
        Lookups.allTags(getActiveProject(), Tag.TagType.REQUIREMENT, q);
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
        long totalRecords = scripts.size();
        TableController.renderJSON(scripts, Script.class, totalRecords, sColumns, sEcho);
    }

    public static void linkedInstances(String tableId,
                                       String sColumns,
                                       String sEcho,
                                       Long requirementId) {
        GenericModel.JPAQuery query = Requirement.find("select i from Requirement r, Script s, Instance i where r.id = ? and s in elements(r.linkedScripts) and i.script = s", requirementId);
        List<Instance> instances = query.fetch();
        long totalRecords = instances.size();
        TableController.renderJSON(instances, Instance.class, totalRecords, sColumns, sEcho);
    }

    public static void linkedDefects(String tableId,
                                     String sColumns,
                                     String sEcho,
                                     Long requirementId) {
        GenericModel.JPAQuery query = Requirement.find("select d from Requirement r, Script s, Instance i, Defect d where r.id = ? and s in elements(r.linkedScripts) and i.script = s and d in elements(i.defects)", requirementId);
        List<Defect> defects = query.fetch();
        long totalRecords = defects.size();
        TableController.renderJSON(defects, Defect.class, totalRecords, sColumns, sEcho);
    }

    public static void edit(@Valid Requirement requirement, String tags) {
        checkInAccount(requirement);
        if (Validation.hasErrors()) {
            // TODO handle validation errors in view somehow
            error();
        }

        requirement.tags = getTags(tags, Tag.TagType.REQUIREMENT);
        if(requirement.tags.isEmpty()) {
            requirement.tags.clear();
        }
        requirement.save();
        ok();
    }

}
