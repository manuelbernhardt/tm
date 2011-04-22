package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.tm.User;
import tree.JSTreeNode;
import tree.TreeDataHandler;
import tree.simple.ChildProducer;
import tree.simple.SimpleNode;

/**
 * Simple tree that only displays users of the current account.
 * This class was implemented in a small garden house in the north of Austria.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class UserTree implements TreeDataHandler {

    public static final String USER = "user";

    public String getName() {
        return "userTree";
    }

    public List<? extends JSTreeNode> getChildren(Long parentId, String type, Map<String, String> args) {
        final ChildProducer rootChildProducer = new ChildProducer() {
            public List<JSTreeNode> produce(Long id) {
                List<JSTreeNode> nodes = new ArrayList<JSTreeNode>();
                List<User> users = User.listByAccount(TMController.getUserAccount().getId());
                for (User u : users) {
                    nodes.add(new SimpleNode(u.getId(), u.getFullName(), USER, false, false, null));
                }
                return nodes;
            }
        };

        if (parentId == -1) {
            SimpleNode root = new SimpleNode(1l, "Users", "root", true, true, rootChildProducer);
            List<JSTreeNode> results = new ArrayList<JSTreeNode>();
            results.add(root);
            return results;
        }
        return null;
    }

    public Long create(Long parentId, String parentType, Long position, String name, String type, Map<String, String> args) {
        return null;
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
        return false;
    }
}
