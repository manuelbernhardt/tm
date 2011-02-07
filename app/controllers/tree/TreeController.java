package controllers.tree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import models.tree.Node;
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
    private final static Map<String, Class<? extends Node>> allNodes = new HashMap<String, Class<? extends Node>>();

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

        // initialize all nodes that are being used
        for (AbstractTree tree : allTrees.values()) {

            for (NodeType nodeType : AbstractTree.nodeTypes.values()) {
                allNodes.put(nodeType.getName(), nodeType.getNodeClass());

                // workaround for gson not being smart enough (yet)
                builder.registerTypeAdapter(nodeType.getNodeClass(), serializer);
            }
        }
        gson = builder.create();
    }

    public static void create(String treeId, Long parentId, String parentType, Long position, String name, String type) {
        NodeType nt = null;
        NodeType pt = null;
        if (type == null) {
            nt = getTree(treeId).getRootType();
        } else {
            nt = AbstractTree.getNodeType(type);
        }
        if (parentType != null) {
            pt = AbstractTree.getNodeType(parentType);
        }
        Node node = getTree(treeId).createNode(parentId, pt, position, name, nt);

        JsonObject status = new JsonObject();
        status.addProperty("status", 1);
        status.addProperty("id", node.getId());
//        JsonObject attr = new JsonObject();
//        attr.addProperty("rel", node.getName());
//        attr.addProperty("title", nt.getName());
//        status.add("attr", attr);
        renderJSON(status.toString());

    }

    public static void getChildren(String treeId, Long id, String type) {
        List<? extends Node> children = null;
        if (type == null) {
            children = getTree(treeId).getChildren(id, getTree(treeId).getRootType());
        } else {
            children = getTree(treeId).getNode(id, getNodeClass(type)).getChildren();
        }
        System.out.println("Children: " + children);
        renderJSON(gson.toJson(children));
    }

    private static Class<? extends Node> getNodeClass(String type) {
        Class<? extends Node> nodeClass = allNodes.get(type);
        if (nodeClass == null) {
            throw new RuntimeException(String.format("Can't find node class for type '%s'", type));
        }
        return nodeClass;
    }

    private static AbstractTree getTree(String treeId) {
        AbstractTree tree = allTrees.get(treeId);
        if (tree == null) {
            throw new RuntimeException(String.format("Could not find implementation of tree '%s'. You need to implement it by extending AbstractTree", treeId));
        }
        return tree;
    }


}
