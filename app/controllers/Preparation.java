package controllers;

import models.project.TestScript;
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

    public static void content(Long nodeId) {
        TestScript script = getFromNode(nodeId);
        render(script);
    }

    public static void scriptDetails(Long scriptId) {
        TestScript script = getTestScript(scriptId);
        render(script);
    }

    public static void steps(Long scriptId) {
        TestScript script = getTestScript(scriptId);
        render(script);
    }

    public static void linked(Long scriptId) {
        TestScript script = getTestScript(scriptId);
        render(script);
    }

    public static void editScript(@Valid TestScript script) {
        checkInAccount(script);
        if (Validation.hasErrors()) {
            // TODO handle validation errors in view somehow
            error();
        }
        script.save();
        ok();

    }

    private static TestScript getTestScript(Long scriptId) {
        if (scriptId == null) {
            return null;
        }
        TestScript script = TestScript.findById(scriptId);
        if (script == null) {
            notFound();
        }
        checkInAccount(script);
        return script;
    }

    private static TestScript getFromNode(Long nodeId) {
        if (nodeId == null) {
            return null;
        }
        TreeNode node = TreeNode.find(nodeId, RepositoryTree.REPOSITORY_TREE);
        if (node == null) {
            notFound();
        }
        TestScript script = TestScript.findById(node.nodeId);
        if (script == null) {
            notFound();
        }
        checkInAccount(script);
        return script;
    }

}
