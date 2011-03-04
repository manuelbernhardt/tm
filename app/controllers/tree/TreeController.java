package controllers.tree;

import java.util.List;

import com.google.gson.JsonObject;
import models.tree.GenericTreeNode;
import play.mvc.Controller;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class TreeController extends Controller {

    public static void create(String treeId, Long parentId, Long position, String name, String type) {

        NodeType nt = null;
        if (type == null) {
            nt = Tree.getTree(treeId).getRootType();
        } else {
            nt = AbstractTree.getNodeType(type);
        }

        GenericTreeNode node = Tree.getTree(treeId).createNode(parentId, position, name, nt);
        JsonObject status = null;
        if (node == null) {
            status = makeStatus(0, null);
        } else {
            status = makeStatus(1, node.getId());
        }
        renderJSON(status.toString());
    }

    public static void remove(String treeId, Long id) {
        try {
            Tree.getTree(treeId).remove(id);
        } catch (Exception e) {
            e.printStackTrace();
            renderJSON(makeStatus(0, null).toString());
        }
        renderJSON(makeStatus(1, null).toString());
    }

    public static void rename(String treeId, Long id, String name) {
        try {
            Tree.getTree(treeId).rename(id, name);
        } catch (Exception e) {
            e.printStackTrace();
            renderJSON(makeStatus(0, null).toString());
        }
        renderJSON(makeStatus(1, null).toString());
    }

    public static void move(String treeId, Long id, Long target, Long position, String name, boolean copy) {
        try {
            if (copy) {
                Tree.getTree(treeId).copy(id, target, position);
            } else {
                Tree.getTree(treeId).move(id, target, position);
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJSON(makeStatus(0, null).toString());
        }
        renderJSON(makeStatus(1, null).toString());
    }

    public static void getChildren(String treeId, Long id, String type) {
        List<? extends GenericTreeNode> children = null;
        if (type == null) {
            children = Tree.getTree(treeId).getChildren(id, Tree.getTree(treeId).getRootType());
        } else {
            children = Tree.getTree(treeId).getNode(id).getChildren();
        }
        System.out.println("Children: " + children);
        renderJSON(Tree.getGson().toJson(children));
    }

    public static JsonObject makeStatus(int status, Long id) {
        JsonObject r = new JsonObject();
        r.addProperty("status", status);
        if (id != null) {
            r.addProperty("id", id);
        }
        return r;
    }


}
