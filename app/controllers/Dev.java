package controllers;

import java.util.List;

import models.tm.ProjectTreeNode;
import models.tree.jpa.TreeNode;
import play.Play;
import play.mvc.Controller;
import play.test.Fixtures;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Dev extends Controller {

    public static void reloadData() {
        if (Play.mode == Play.Mode.DEV) {
            Fixtures.deleteDatabase();
            Fixtures.loadModels("initial-data.yml");
            // fix the treeNodes
            List<ProjectTreeNode> rootNodes = TreeNode.find("from ProjectTreeNode n where n.threadRoot is null").fetch();
            for (ProjectTreeNode n : rootNodes) {
                n.threadRoot = n;
                n.save();
            }
            redirect(request.controller + ".index");
        }
    }
}
