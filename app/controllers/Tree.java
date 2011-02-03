package controllers;

import models.tree.Node;
import models.tree.NodeSerializer;
import models.tree.NodeType;
import models.tree.TreeStore;
import play.mvc.Controller;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Tree extends Controller {

    private static NodeSerializer nodeSerializer = new NodeSerializer();

    public static void create(Long id, Long position, String title, String type) {
        System.out.println("Created node " + title);
    }

    public static void get(String operation, Long id) {
        TreeStore t = new TreeStore();
        t.createNode(t.getNode(1l), "Release 1", NodeType.ROOT);
        Node n = t.createNode(t.getNode(1l), "Release 2", NodeType.ROOT);
        t.createNode(n, "System Test", NodeType.FOLDER);
        Node uat = t.createNode(n, "User Acceptance Test", NodeType.FOLDER);
        t.createNode(uat, "Cycle 1", NodeType.FILE);
        t.createNode(uat, "Cycle 2", NodeType.FILE);

        renderJSON(t.getChildren(id), nodeSerializer);
    }
}
