package controllers.tree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import models.tree.GenericTreeNode;
import models.tree.jpa.TreeNode;
import play.Play;
import play.classloading.ApplicationClasses;
import play.mvc.Controller;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class TreeController extends Controller {

    private final static Map<String, AbstractTree> allTrees = new HashMap<String, AbstractTree>();

    private static Gson gson = null;

    // initialization code
    static {

        // initialize all trees
        List<ApplicationClasses.ApplicationClass> trees = Play.classes.getAssignableClasses(AbstractTree.class);
        for (ApplicationClasses.ApplicationClass tree : trees) {

            try {
                Constructor c = tree.javaClass.getDeclaredConstructor();
                AbstractTree t = (AbstractTree) c.newInstance();
                String name = t.getName();
                if (name == null) {
                    throw new RuntimeException("No valid name given for tree '" + tree.javaClass.getSimpleName() + "'. Are you sure you implemented getName() ?");
                }
                t.init();
                allTrees.put(name, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        final GsonBuilder builder = new GsonBuilder();
        // FIXME using the same guy all the time could get us into troubles because it keeps state because
        // gson doesn't provide a way to pass information in a serialization context
        final NodeSerializer serializer = new NodeSerializer();

        // workaround for gson not being smart enough (yet)
        builder.registerTypeAdapter(GenericTreeNode.class, serializer);
        builder.registerTypeAdapter(TreeNode.class, serializer);
        gson = builder.create();
    }

    public static void create(String treeId, Long parentId, Long position, String name, String type) {

        System.out.println("parentId " + parentId);
        NodeType nt = null;
        if (type == null) {
            nt = getTree(treeId).getRootType();
        } else {
            nt = AbstractTree.getNodeType(type);
        }

        GenericTreeNode node = getTree(treeId).createNode(parentId, position, name, nt);
        JsonObject status = new JsonObject();
        if (node == null) {
            status.addProperty("status", 0);
        } else {
            status.addProperty("status", 1);
            status.addProperty("id", node.getId());
        }
        renderJSON(status.toString());

    }

    public static void getChildren(String treeId, Long id, String type) {
        List<? extends GenericTreeNode> children = null;
        if (type == null) {
            children = getTree(treeId).getChildren(id, getTree(treeId).getRootType());
        } else {
            children = getTree(treeId).getNode(id).getChildren();
        }
        System.out.println("Children: " + children);
        renderJSON(gson.toJson(children));
    }

    private static AbstractTree getTree(String treeId) {
        AbstractTree tree = allTrees.get(treeId);
        if (tree == null) {
            throw new RuntimeException(String.format("Could not find implementation of tree '%s'. You need to implement it by extending AbstractTree", treeId));
        }
        return tree;
    }


}
