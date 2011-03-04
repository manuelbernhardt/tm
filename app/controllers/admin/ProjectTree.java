package controllers.admin;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.TMController;
import controllers.simpletree.ChildProducer;
import controllers.simpletree.JSTreeNodeSerializer;
import controllers.simpletree.SimpleNode;
import controllers.tree.TreeController;
import models.project.Project;
import models.project.ProjectCategory;
import models.tree.JSTreeNode;

/**
 * TODO refactor me!
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ProjectTree extends TMController {

    private static Gson gson;

    static {
        GsonBuilder b = new GsonBuilder();
        JSTreeNodeSerializer jsTreeNodeSerializer = new JSTreeNodeSerializer();
        b.registerTypeAdapter(JSTreeNode.class, jsTreeNodeSerializer);
        b.registerTypeAdapter(SimpleNode.class, jsTreeNodeSerializer);
        gson = b.create();
    }
    

    public static void create(String treeId, Long parentId, Long position, String name, String type) {

        // project
        if (type.equals("default")) {
            ProjectCategory category = ProjectCategory.findById(parentId);
            if (category == null) {
                error("Project category for id " + parentId + " not found");
            } else {
                Project project = new Project();
                project.account = getUserAccount();
                project.name = name;
                project.projectCategory = category;
                project.save();
                renderJSON(TreeController.makeStatus(1, project.id).toString());
            }

        } else if (type.equals("category")) {
            ProjectCategory category = new ProjectCategory();
            category.name = name;
            category.account = getUserAccount();
            category.save();
            renderJSON(TreeController.makeStatus(1, category.id).toString());
        } else {
            throw new RuntimeException("Houston, we have a problem");
        }
    }

    public static void move() {
    }

    public static void remove(String treeId, Long id) {
    }

    public static void getChildren(String treeId, Long id, String type) {
        if (id == -1) {
            List<ProjectCategory> pcs = ProjectCategory.findAll();
            List<SimpleNode> cats = new ArrayList<SimpleNode>();
            for (ProjectCategory pc : pcs) {
                ChildProducer producer = new CategoryChildProducer();
                SimpleNode pdn = new SimpleNode(pc.id, pc.name, "category", true, true, producer);
                cats.add(pdn);
            }
            renderJSON(gson.toJson(cats));
        } else {
            ProjectCategory cat = ProjectCategory.findById(id);
            SimpleNode pdn = new SimpleNode(cat.id, cat.name, "default", false, false, null);
            renderJSON(gson.toJson(pdn));
        }
    }

    public static void rename(String treeId, Long id, String name, String type) {
        if (type.equals("default")) {
            Project p = Project.findById(id);
            p.name = name;
            p.save();
            renderJSON(TreeController.makeStatus(1, null).toString());
        } else if (type.equals("category")) {
            ProjectCategory p = ProjectCategory.findById(id);
            p.name = name;
            p.save();
            renderJSON(TreeController.makeStatus(1, null).toString());
        } else {
            error("Error");
        }

    }

    private static class CategoryChildProducer implements ChildProducer {
        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> ps = new ArrayList<JSTreeNode>();
            List<Project> projects = Project.find("from Project p where p.projectCategory.id = ?", id).fetch();
            for (Project p : projects) {
                SimpleNode pdn = new SimpleNode(p.id, p.name, "default", false, false, null);
                ps.add(pdn);
            }
            return ps;
        }
    }
}
