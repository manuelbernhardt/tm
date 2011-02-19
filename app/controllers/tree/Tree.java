package controllers.tree;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.tree.GenericTreeNode;
import models.tree.jpa.TreeNode;
import play.Play;
import play.classloading.ApplicationClasses;

/**
 * Entry point for Tree operations. This should probably become some part of a play module?
 * /!\ Persistance operations via Hibernate rely on the Play! controller so any testing or outside operations need to take care of flushing and cache refresh.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Tree {
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

    public static Gson getGson() {
        return gson;
    }

    public static AbstractTree getTree(String treeId) {
        AbstractTree tree = allTrees.get(treeId);
        if (tree == null) {
            throw new RuntimeException(String.format("Could not find implementation of tree '%s'. You need to implement it by extending AbstractTree", treeId));
        }
        return tree;
    }


}
