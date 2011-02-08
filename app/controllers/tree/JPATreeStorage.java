package controllers.tree;

import models.tree.Node;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import play.utils.Java;

import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class JPATreeStorage extends TreeStorage {

    @Override
    public Node save(Node n) {
        ((Model) n).save();
        return n;
    }

    @Override
    public Node getNode(Long id, Class<? extends Node> nodeClass) {
        try {
            GenericModel.JPAQuery query = (GenericModel.JPAQuery) Java.invokeStaticOrParent(nodeClass, "find", new Object[]{String.format("from %s n where n.id = ?", nodeClass.getSimpleName()), new Object[]{id}});
            return query.first();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Node> getChildren(Long parentId, Class<? extends Node> nodeClass) {
        System.out.println("Looking for children of " + parentId);
        try {
            GenericModel.JPAQuery query = null;
            if (parentId == null || parentId == -1) {
                query = (GenericModel.JPAQuery) Java.invokeStaticOrParent(nodeClass, "find", new Object[]{"from AbstractNode n where n.parent is null", new Object[]{}});
            } else {
                query = (GenericModel.JPAQuery) Java.invokeStaticOrParent(nodeClass, "find", new Object[]{"from AbstractNode n where n.parent.id = ?", new Object[]{parentId}});
            }
            return query.fetch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
