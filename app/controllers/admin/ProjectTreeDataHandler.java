package controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controllers.TMController;
import models.account.Account;
import models.general.TreeRoleHolder;
import models.general.UnitRole;
import models.tm.Project;
import models.tm.ProjectCategory;
import play.libs.F;
import tree.JSTreeNode;
import tree.TreeDataHandler;
import tree.simple.ChildProducer;
import tree.simple.SimpleNode;

import static models.general.UnitRole.roles;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ProjectTreeDataHandler implements TreeDataHandler, TreeRoleHolder {


    public static final String CATEGORY = "category";
    public static final String PROJECT = "default";

    public String getName() {
        return "projectTree";
    }

    public List<? extends JSTreeNode> getChildren(Long id, String type, Map<String, String> args) {
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

    public F.Tuple<Long, String> create(Long parentId, String parentType, Long position, String name, String type, Map<String, String> args) {
        Account userAccount = TMController.getConnectedUserAccount();
        if (type.equals(ProjectTreeDataHandler.PROJECT)) {
            ProjectCategory category = ProjectCategory.findById(parentId);
            Project project = new Project(name, userAccount, category);
            project.create(userAccount);
            return new F.Tuple<Long, String>(project.id, PROJECT);
        } else if (type.equals(ProjectTreeDataHandler.CATEGORY)) {
            ProjectCategory category = new ProjectCategory(userAccount, name);
            category.name = name;
            category.account = userAccount;
            category.create(userAccount);
            return new F.Tuple<Long, String>(category.id, CATEGORY);
        }
        return null;
    }

    public boolean rename(Long id, String name, String type) {
        Account userAccount = TMController.getConnectedUserAccount();
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

    public boolean copy(Long id, Long target, Long position) {
        return false;
    }

    public boolean move(Long id, String type, Long target, String targetType, Long position) {
        return false;
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
            for (ProjectCategory pc : ProjectCategory.find("from ProjectCategory pc where pc.account = ?", TMController.getConnectedUserAccount()).<ProjectCategory>fetch()) {
                SimpleNode pdn = new SimpleNode(pc.id, pc.name, CATEGORY, true, true, producer);
                nodes.add(pdn);
            }
            for (Project p : Project.find("from Project p where p.projectCategory is null and p.account = ?", TMController.getConnectedUserAccount()).<Project>fetch()) {
                SimpleNode pn = new SimpleNode(p.id, p.name, PROJECT, false, false, null);
                nodes.add(pn);
            }
            return nodes;
        }
    }

    public List<UnitRole> getViewRoles() {
        return roles(UnitRole.PROJECTEDIT);
    }

    public List<UnitRole> getCreateRoles() {
        return roles(UnitRole.PROJECTCREATE);
    }

    public List<UnitRole> getUpdateRoles() {
        return roles(UnitRole.PROJECTEDIT);
    }

    public List<UnitRole> getDeleteRoles() {
        return roles(UnitRole.PROJECTDELETE);
    }
}
