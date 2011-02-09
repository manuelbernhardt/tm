package controllers.tree;

import models.tree.GenericTreeNode;
import models.tree.Node;
import models.tree.jpa.TreeNode;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;

import javax.persistence.Query;
import javax.swing.*;
import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class JPATreeStorage extends TreeStorage {

    @Override
    public GenericTreeNode getNewGenericTreeNode() {
        return new TreeNode();
    }

    @Override
    public GenericTreeNode create(GenericTreeNode node) {
        ((Model) node).create();
        return node;
    }

    @Override
    public Node create(Node concrete) {
        ((Model) concrete).create();
        return concrete;
    }

    @Override
    public Node update(Node node) {
        return (Node) ((Model) node).save();
    }

    @Override
    public GenericTreeNode update(GenericTreeNode node) {
        return (GenericTreeNode) ((Model) node).save();
    }

    @Override
    public GenericTreeNode getTreeNode(Long id) {
        return (GenericTreeNode) TreeNode.findById(id);

    }

    @Override
    public void remove(Long id, boolean removeObject) {

        // making this behaviour configurable is some manual labor
        if (removeObject) {
            Long oid = getAbstractNodeId(id);

            removeReferences(id);

            Query deleteObject = JPA.em().createQuery("delete from AbstractNode a where a.id = :id");
            deleteObject.setParameter("id", oid);
            deleteObject.executeUpdate();

            deleteNode(id);
        } else {
            Long oid = getAbstractNodeId(id);

            // de-reference the node
            Query updateObject = JPA.em().createQuery("update AbstractNode a set a.treeNode = null where a.id = :oid");
            updateObject.setParameter("oid", oid);
            updateObject.executeUpdate();

            removeReferences(id);

            deleteNode(id);
        }
    }

    private void deleteNode(Long id) {
        // delete the node
        Query deleteNode = JPA.em().createQuery("delete from TreeNode n where n.id = :id");
        deleteNode.setParameter("id", id);
        deleteNode.executeUpdate();
    }

    private void removeReferences(Long id) {
        // remove references to other objects
        Query updateRef = JPA.em().createQuery("update TreeNode n set n.abstractNode = null, n.parent = null where n.id = :id");
        updateRef.setParameter("id", id);
        updateRef.executeUpdate();
    }

    private Long getAbstractNodeId(Long id) {
        Query objectId = JPA.em().createQuery("select a.id from TreeNode n join n.abstractNode a where n.id = :id");
        objectId.setParameter("id", id);
        return (Long) objectId.getSingleResult();
    }

    @Override
    public List<GenericTreeNode> getChildren(Long parentId) {
        System.out.println("Looking for children of " + parentId);
        GenericModel.JPAQuery query = null;
        if (parentId == null || parentId == -1) {
            query = TreeNode.find("from TreeNode n where n.parent is null", null);
        } else {
            query = TreeNode.find("from TreeNode n where n.parent.id = ?", parentId);
        }
        return query.fetch();
    }
}
