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
            return (Node) Java.invokeStatic(nodeClass, "findById", new Object[]{id});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Node> getChildren(Long parentId, Class<? extends Node> nodeClass) {
        try {
            System.out.println("parent " + parentId);
            GenericModel.JPAQuery query = null;
            if(parentId == -1) {
                query = (GenericModel.JPAQuery) Java.invokeStaticOrParent(nodeClass, "find", new Object[]{String.format("from %s n where n.parent is null", nodeClass.getSimpleName()), new Object[]{}});
            } else {
                query = (GenericModel.JPAQuery) Java.invokeStaticOrParent(nodeClass, "find", new Object[]{String.format("from %s n where n.parent.id = ?", nodeClass.getSimpleName()), new Object[]{parentId}});
            }
            return query.fetch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
