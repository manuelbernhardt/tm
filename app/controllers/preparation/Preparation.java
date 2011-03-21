package controllers.preparation;

import controllers.RepositoryTree;
import controllers.TMController;
import models.project.test.Script;
import models.tree.jpa.TreeNode;
import play.data.validation.Valid;
import play.data.validation.Validation;

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
