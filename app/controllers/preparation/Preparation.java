package controllers.preparation;

import java.util.List;

import javax.persistence.Query;

import controllers.RepositoryTree;
import controllers.TMController;
import controllers.tabularasa.TableController;
import models.project.test.Script;
import models.project.test.ScriptStep;
import models.tree.jpa.TreeNode;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Preparation extends TMController {

    public static void index() {
        render();
    }

    public static void content(Long scriptNodeId) {
        Script script = getFromNode(scriptNodeId);
        render(script);
    }

    public static void scriptDetails(Long scriptId) {
        Script script = getTestScript(scriptId);
        render(script);
    }

    public static void steps(Long scriptId) {
        Script script = getTestScript(scriptId);
        render(script);
    }

    public static void linked(Long scriptId) {
        Script script = getTestScript(scriptId);
        render(script);
    }

    public static void editScript(@Valid Script script) {
        checkInAccount(script);
        if (Validation.hasErrors()) {
            // TODO handle validation errors in view somehow
            error();
        }
        script.save();
        ok();
    }

    public static void createStep(@Valid ScriptStep step, Long scriptId) {
        if (scriptId == null) {
            error();
        }
        Script script = Script.findById(scriptId);
        if (script == null) {
            notFound();
        } else {
            checkInAccount(script);
            step.project = TMController.getActiveProject();
            step.script = script;

            // what's your favourite position?
            if(ScriptStep.count("script.id = ? and position = ?", script.getId(), step.position) > 0) {
                Query query = JPA.em().createQuery("update ScriptStep s set s.position = s.position + 1 where s.script.id = :scriptId and s.position > :position");
                query.setParameter("scriptId", script.getId());
                query.setParameter("position", step.position - 1);
                query.executeUpdate();
            }

            boolean created = step.create();
            if (!created) {
                error();
            } else {
                ok();
            }

        }

    }

    public static void stepData(String tableId, Long scriptId,
                                Integer iDisplayStart,
                                Integer iDisplayLength,
                                String sColumns,
                                String sEcho,
                                String sSearch) {
        GenericModel.JPAQuery query = ScriptStep.find("script.id = ? order by position", scriptId).from(iDisplayStart == null ? 0 : iDisplayStart);
        List<ScriptStep> steps = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = ScriptStep.count("script.id", scriptId);
        TableController.renderJSON(steps, ScriptStep.class, totalRecords, sColumns, sEcho);
    }


    private static Script getTestScript(Long scriptId) {
        if (scriptId == null) {
            return null;
        }
        Script script = Script.findById(scriptId);
        if (script == null) {
            notFound();
        }
        checkInAccount(script);
        return script;
    }

    private static Script getFromNode(Long nodeId) {
        if (nodeId == null) {
            return null;
        }
        TreeNode node = TreeNode.find(nodeId, RepositoryTree.REPOSITORY_TREE);
        if (node == null) {
            notFound();
        }
        Script script = Script.findById(node.nodeId);
        if (script == null) {
            notFound();
        }
        checkInAccount(script);
        return script;
    }

}
