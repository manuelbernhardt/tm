package controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controllers.TMController;
import tree.simple.ChildProducer;
import tree.JSTreeNode;
import tree.simple.SimpleNode;
import tree.TreeDataHandler;
import models.project.Role;
import models.tm.User;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class RolesUserTreeDataHandler implements TreeDataHandler {

    public String getName() {
        return "rolesUserTree";
    }

    public List<? extends JSTreeNode> getChildren(Long parentId, final Map<String, String> args) {

        final RoleChildProducer roleChildProducer = new RoleChildProducer();
        if (parentId == -1) {
            List<JSTreeNode> res = new ArrayList<JSTreeNode>();
            List<Role> roles = Role.findByProject(Long.parseLong(args.get("projectId")));
            for (Role r : roles) {
                JSTreeNode roleNode = new SimpleNode(r.id, r.name, "role", true, true, roleChildProducer);
                res.add(roleNode);
            }
            return res;
        } else {
            // return users for a specific category
            return roleChildProducer.produce(parentId);
        }
    }

    public Long create(Long parentId, Long position, String name, String type, Map<String, String> args) {
        return editAssignment(Long.parseLong(args.get("roleId")), Long.parseLong(args.get("userId")), true);
    }

    public static Long editAssignment(Long roleId, Long userId, boolean assign) {

        if (roleId == null || userId == null || roleId == -1 || userId == -1) {
            return null;
        }
        Role role = Role.findById(roleId);
        User user = User.findById(userId);
        if (role == null || user == null) {
            return null;
        } else {
            if (!role.isInAccount(TMController.getUserAccount()) || !user.isInAccount(TMController.getUserAccount())) {
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
            return userId;
        }
    }


    public boolean rename(Long id, String name, String type) {
        return false;
    }

    public void copy(Long id, Long target, Long position) {
    }

    public void move(Long id, Long target, Long position) {
    }

    public boolean remove(Long id, Long parentId, String type, Map<String, String> args) {
        try {
            editAssignment(parentId, id, false);
        } catch (Throwable t) {
            return false;
        }
        return true;
    }

    private static class RoleChildProducer implements ChildProducer {

        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> res = new ArrayList<JSTreeNode>();
            List<User> users = User.find("select u from User u, Role r where r in elements(u.projectRoles) and r.id = ?", id).fetch();
            for (User u : users) {
                JSTreeNode n = new SimpleNode(u.id, u.getFullName(), "default", false, false, null);
                res.add(n);
            }
            return res;
        }
    }


}
