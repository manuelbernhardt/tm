package controllers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.Lookups;
import controllers.TMController;
import models.general.TreeRoleHolder;
import models.general.UnitRole;
import models.tm.Project;
import models.tm.ProjectCategory;
import models.tm.ProjectRole;
import models.tm.TMUser;
import play.libs.F;
import tree.JSTreeNode;
import tree.TreeDataHandler;
import tree.simple.ChildProducer;
import tree.simple.SimpleNode;

import static models.general.UnitRole.roles;


/**
 * Assignment of project roles to users, in the admin -> users -> projects view.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ProjectRolesTreeDataHandler implements TreeDataHandler, TreeRoleHolder {

    public static final String ROLE = "default";
    public static final String CATEGORY = "category";
    public static final String PROJECT = "project";

    public String getName() {
        return "projectRolesTree";
    }

    public List<? extends JSTreeNode> getChildren(Long parentId, String type, Map<String, String> args) {

        if (parentId == -1) {
            Long userId = Long.parseLong(args.get("userId"));
            TMUser u = Lookups.getUser(userId);
            if (u == null) {
                return new ArrayList<JSTreeNode>();
            }

            final ProjectChildProducer pcp = new ProjectChildProducer(u);
            final CategoryChildProducer ccp = new CategoryChildProducer(pcp, u);

            // produce project categories and "loose" projects
            List<JSTreeNode> rootNodes = new ArrayList<JSTreeNode>();
            Map<ProjectCategory, JSTreeNode> categories = new HashMap<ProjectCategory, JSTreeNode>();
            Map<Project, JSTreeNode> projects = new HashMap<Project, JSTreeNode>();
            for (Project p : u.getProjects()) {
                ProjectCategory pc = p.projectCategory;
                if (pc != null && !categories.containsKey(pc)) {
                    categories.put(pc, new SimpleNode(pc.id, pc.name, CATEGORY, true, true, ccp));
                } else if (pc == null && !projects.containsKey(p)) {
                    projects.put(p, new SimpleNode(p.id, p.name, PROJECT, true, true, pcp));
                }
            }
            rootNodes.addAll(categories.values());
            rootNodes.addAll(projects.values());

            return rootNodes;
        }
        return null;
    }

    public F.Tuple<Long, String> create(Long parentId, String parentType, Long position, String name, String type, Map<String, String> args) {
        return editAssignment(Long.parseLong(args.get("roleId")), Long.parseLong(args.get("userId")), true);
    }

    public boolean rename(Long id, String name, String type) {
        return false;
    }

    public boolean copy(Long id, Long target, Long position) {
        return false;
    }

    public boolean move(Long id, String type, Long target, String targetType, Long position) {
        return false;
    }

    public boolean remove(Long id, Long parentId, String type, Map<String, String> args) {
        F.Tuple<Long, String> r = editAssignment(id, Long.parseLong(args.get("userId")), false);
        return r != null;
    }

    public static class CategoryChildProducer implements ChildProducer {

        final private TMUser user;

        final private ChildProducer projectChildProducer;

        public CategoryChildProducer(ChildProducer projectChildProducer, TMUser user) {
            this.projectChildProducer = projectChildProducer;
            this.user = user;
        }

        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> ps = new ArrayList<JSTreeNode>();
            for (Project p : user.getProjects()) {
                ProjectCategory pc = p.projectCategory;
                if (pc.getId().equals(id)) {
                    ps.add(new SimpleNode(p.id, p.name, PROJECT, true, true, projectChildProducer));
                }
            }
            return ps;
        }

    }
    public static class ProjectChildProducer implements ChildProducer {

        private final TMUser user;

        public ProjectChildProducer(TMUser user) {
            this.user = user;
        }

        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> rs = new ArrayList<JSTreeNode>();
            for (ProjectRole r : user.projectRoles) {
                if (r.project.getId().equals(id)) {
                    rs.add(new SimpleNode(r.id, r.name, ROLE, false, false, null));
                }
            }
            return rs;
        }

    }

    public static F.Tuple<Long, String> editAssignment(Long roleId, Long userId, boolean assign) {

        if (roleId == null || userId == null || roleId == -1 || userId == -1) {
            return null;
        }
        ProjectRole role = Lookups.getRole(roleId);
        TMUser user = Lookups.getUser(userId);
        if (role == null || user == null) {
            return null;
        } else {
            if (!role.isInAccount(TMController.getConnectedUserAccount()) || !user.isInAccount(TMController.getConnectedUserAccount())) {
                return null;
            }

            if (assign && !user.projectRoles.contains(role)) {
                user.projectRoles.add(role);
                user.save();
            } else if (assign && user.projectRoles.contains(role)) {
                return null;
            } else if (user.projectRoles.contains(role) && !assign) {
                user.projectRoles.remove(role);
                user.save();
            }
            return new F.Tuple<Long, String>(userId, ROLE);
        }
    }

    public List<UnitRole> getViewRoles() {
        return roles(UnitRole.PROJECTEDIT, UnitRole.PROJECTADMIN, UnitRole.ACCOUNTADMIN);
    }

    public List<UnitRole> getCreateRoles() {
        return roles(UnitRole.PROJECTEDIT, UnitRole.PROJECTADMIN, UnitRole.ACCOUNTADMIN);
    }

    public List<UnitRole> getUpdateRoles() {
        return roles(UnitRole.PROJECTEDIT, UnitRole.PROJECTADMIN, UnitRole.ACCOUNTADMIN);
    }

    public List<UnitRole> getDeleteRoles() {
        return roles(UnitRole.PROJECTEDIT, UnitRole.PROJECTADMIN, UnitRole.ACCOUNTADMIN);
    }
}
