package controllers.admin;

import java.util.ArrayList;
import java.util.List;

import controllers.tree.ChildProducer;
import controllers.tree.SimpleNode;
import controllers.tree.TreeDataHandler;
import models.project.Role;
import models.tm.User;
import models.tree.JSTreeNode;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class RolesUserTreeDataHandler implements TreeDataHandler {

    public String getName() {
        return "rolesUserTree";
    }

    public List<? extends JSTreeNode> getChildren(Long parentId, final String[] args) {

        final RoleChildProducer roleChildProducer = new RoleChildProducer();
        if (parentId == -1) {
            List<JSTreeNode> res = new ArrayList<JSTreeNode>();
            List<Role> roles = Role.findByProject(Long.parseLong(args[0]));
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

    public Long create(Long parentId, Long position, String name, String type) {
        return null;
    }

    public boolean rename(Long id, String name, String type) {
        return false;
    }

    public void copy(Long id, Long target, Long position) {
    }

    public void move(Long id, Long target, Long position) {
    }

    public boolean remove(Long id, String type) throws Exception {
        return false;
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
