package controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controllers.Lookups;
import controllers.TMController;
import models.tm.Project;
import models.tm.Role;
import models.tm.TMUser;
import tree.JSTreeNode;
import tree.TreeDataHandler;
import tree.simple.ChildProducer;
import tree.simple.SimpleNode;
import util.Logger;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ProjectRolesAssignmentTreeDataHandler implements TreeDataHandler {

    public static final String PROJECT_ROLE = "projectRole";
    public static final String USER = "default";

    public String getName() {
        return "projectRolesAssignmentTree";
    }

    public List<? extends JSTreeNode> getChildren(Long parentId, String type, Map<String, String> args) {
        Long projectId = Long.parseLong(args.get("projectId"));
        Project project = Project.findById(projectId);
        if(!project.isInAccount(TMController.getUserAccount())) {
            Logger.error(Logger.LogType.SECURITY, "Project not in account, project ID '%s', connected user '%s'", projectId, TMController.getConnectedUser().user.email);
        }
        final RoleChildProducer roleChildProducer = new RoleChildProducer();
        final ProjectRoleChildProducer projectRoleChildProducer = new ProjectRoleChildProducer(projectId, roleChildProducer);

        if (parentId == -1) {
            List<JSTreeNode> results = new ArrayList<JSTreeNode>();
            results.add(new SimpleNode(1l, "Project roles", "root", true, true, projectRoleChildProducer));
            return results;
        }
        return null;
    }

    private static class ProjectRoleChildProducer implements ChildProducer {

        private final Long projectId;
        private final RoleChildProducer roleChildProducer;

        private ProjectRoleChildProducer(Long projectId, RoleChildProducer roleChildProducer) {
            this.projectId = projectId;
            this.roleChildProducer = roleChildProducer;
        }

        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> result = new ArrayList<JSTreeNode>();
            List<Role> roles = Role.findByProject(projectId);
            for (Role r : roles) {
                result.add(new SimpleNode(r.getId(), r.name, PROJECT_ROLE, true, true, roleChildProducer));
            }
            return result;
        }
    }

    private static class RoleChildProducer implements ChildProducer {

        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> res = new ArrayList<JSTreeNode>();
            List<TMUser> users = TMUser.listUsersInProjectRole(id);
            for (TMUser u : users) {
                JSTreeNode n = new SimpleNode(u.id, u.getFullName(), "default", false, false, null);
                res.add(n);
            }
            return res;
        }
    }


    public Long create(Long parentId, String parentType, Long position, String name, String type, Map<String, String> args) {
        return null;
    }

    public boolean rename(Long id, String name, String type) {
        return false;
    }

    public boolean copy(Long id, Long target, Long position) {
        if(target == null || target == -1) {
            return false;
        }
        Role role = Lookups.getRole(target);

        TMUser u = Lookups.getUser(id);
        if(TMUser.listUsersInProjectRole(role.getId()).contains(u)) {
            // do nothing
            return false;
        }
        u.projectRoles.add(role);
        u.save();
        return true;
    }

    public boolean move(Long id, String type, Long target, String targetType, Long position) {
        return false;
    }

    public boolean remove(Long id, Long parentId, String type, Map<String, String> args) {
        TMUser u = Lookups.getUser(id);
        if (u == null) {
            Logger.error(Logger.LogType.TECHNICAL, "Could not find user '%s' in order to remove project role '%s', operation performed by user '%s'", id, parentId, TMController.getConnectedUser().user.email);
            return false;
        }
        Role role = Lookups.getRole(parentId);
        if(role == null) {
            Logger.error(Logger.LogType.TECHNICAL, "Could not find role with id '%s' in order to remove it from user '%s', operation performed by user '%s'", parentId, id, TMController.getConnectedUser().user.email);
            return false;
        }
        u.projectRoles.remove(role);
        u.save();
        return true;
    }
}