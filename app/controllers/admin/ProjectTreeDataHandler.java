package controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controllers.TMController;
import tree.simple.ChildProducer;
import tree.JSTreeNode;
import tree.simple.SimpleNode;
import tree.TreeDataHandler;
import models.general.Account;
import models.project.Project;
import models.project.ProjectCategory;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ProjectTreeDataHandler implements TreeDataHandler {


    public static final String CATEGORY = "category";
    public static final String PROJECT = "default";

    public String getName() {
        return "projectTree";
    }

    public List<? extends JSTreeNode> getChildren(Long id, Map<String, String> args) {
        List<JSTreeNode> l = new ArrayList<JSTreeNode>();
        final ChildProducer producer = new CategoryChildProducer();
        if (id == -1) {
            // virtual root node
            ChildProducer rootChildProducer = new ProjectsRootChildProducer(producer);
            SimpleNode root = new SimpleNode(0l, "Projects", "root", true, true, rootChildProducer);
            l.add(root);
        } else {
            l.addAll(producer.produce(id));
        }
        return l;
    }

    public Long create(Long parentId, Long position, String name, String type, Map<String, String> args) {
        Account userAccount = TMController.getUserAccount();
        if (type.equals(ProjectTreeDataHandler.PROJECT)) {
            ProjectCategory category = ProjectCategory.findById(parentId);
            Project project = new Project();
            project.account = userAccount;
            project.name = name;
            project.projectCategory = category;
            project.create(userAccount);
            return project.id;
        } else if (type.equals(ProjectTreeDataHandler.CATEGORY)) {
            ProjectCategory category = new ProjectCategory();
            category.name = name;
            category.account = userAccount;
            category.create(userAccount);
            return category.id;
        }
        return null;
    }

    public boolean rename(Long id, String name, String type) {
        Account userAccount = TMController.getUserAccount();
        if (type.equals(ProjectTreeDataHandler.PROJECT)) {
            Project p = Project.<Project>findById(id);
            if (!p.isInAccount(userAccount)) {
                return false;
            }
            p.name = name;
            p.save();
            return true;
        } else if (type.equals(ProjectTreeDataHandler.CATEGORY)) {
            ProjectCategory p = ProjectCategory.findById(id);
            if (!p.isInAccount(userAccount)) {
                return false;
            }
            p.name = name;
            p.save();
            return true;
        }
        return false;
    }

    public void copy(Long id, Long target, Long position) {
        // TODO
    }

    public void move(Long id, Long target, Long position) {
        // TODO
    }

    public boolean remove(Long id, Long parentId, String type, Map<String, String> args) {
        if (type.equals(ProjectTreeDataHandler.PROJECT)) {
            Project project = Project.findById(id);
            project.delete();
            return true;
        } else if (type.equals(ProjectTreeDataHandler.CATEGORY)) {
            ProjectCategory category = ProjectCategory.findById(id);
            category.delete();
            return true;
        }
        return false;
    }

    private static class CategoryChildProducer implements ChildProducer {
        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> ps = new ArrayList<JSTreeNode>();
            List<Project> projects = Project.find("from Project p where p.projectCategory.id = ?", id).fetch();
            for (Project p : projects) {
                SimpleNode pdn = new SimpleNode(p.id, p.name, PROJECT, false, false, null);
                ps.add(pdn);
            }
            return ps;
        }
    }


    private static class ProjectsRootChildProducer implements ChildProducer {
        private final ChildProducer producer;

        public ProjectsRootChildProducer(ChildProducer producer) {
            this.producer = producer;
        }

        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> nodes = new ArrayList<JSTreeNode>();
            for (ProjectCategory pc : ProjectCategory.find("from ProjectCategory pc where pc.account = ?", TMController.getUserAccount()).<ProjectCategory>fetch()) {
                SimpleNode pdn = new SimpleNode(pc.id, pc.name, CATEGORY, true, true, producer);
                nodes.add(pdn);
            }
            for (Project p : Project.find("from Project p where p.projectCategory is null and p.account = ?", TMController.getUserAccount()).<Project>fetch()) {
                SimpleNode pn = new SimpleNode(p.id, p.name, PROJECT, false, false, null);
                nodes.add(pn);
            }
            return nodes;
        }
    }
}
