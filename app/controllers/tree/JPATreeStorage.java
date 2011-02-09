package controllers.tree;

import models.tree.GenericTreeNode;
import models.tree.Node;
import models.tree.jpa.TreeNode;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;

import javax.persistence.Query;
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
            updateQuery("delete from AbstractNode a where a.id = :id", oid);
            deleteNode(id, removeObject);
        } else {
            updateQuery("update AbstractNode a set a.treeNode = null where a.treeNode.id = :id", id);
            removeReferences(id);
            deleteNode(id, removeObject);
        }
    }

    private void deleteNode(Long id, boolean removeObject) {
        // kill the kids
        Query q = JPA.em().createQuery("select n.id from TreeNode n where n.parent.id = :id order by n.path desc");
        q.setParameter("id", id);
        List<Long> kids = q.getResultList();
        updateQuery("update TreeNode n set n.parent = null, n.abstractNode = null where n.parent.id = :id", id);
        if(removeObject) {
            updateQuery("delete from AbstractNode a where a.treeNode.id in (:kids)", "kids", kids);
        } else {
            updateQuery("update AbstractNode a set a.treeNode = null where a.treeNode.id in (:kids)", "kids", kids);
        }
        updateQuery("delete from TreeNode n where n.id in (:kids)", "kids", kids);
        updateQuery("delete from TreeNode n where n.id = :id", id);
    }

    private void removeReferences(Long id) {
        updateQuery("update TreeNode n set n.abstractNode = null, n.parent = null where n.id = :id", id);
    }

    private Long getAbstractNodeId(Long id) {
        return idQuery("select a.id from TreeNode n join n.abstractNode a where n.id = :id", id);
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

    @Override
    public void rename(Long id, String name) {
        updateQuery("update TreeNode n set n.name = ? where n.id = ?", name, id);
        updateQuery("update AbstractNode n set n.name = ? where n.treeNode.id = ?", name, id);
    }

    private Long idQuery(String query, Long id) {
        Query q = JPA.em().createQuery(query);
        q.setParameter("id", id);
        return (Long) q.getSingleResult();
    }

    private void updateQuery(String query, Long id) {
        Query q = JPA.em().createQuery(query);
        q.setParameter("id", id);
        q.executeUpdate();
    }

    private void updateQuery(String query, Object... args) {
        Query q = JPA.em().createQuery(query);
        for (int i = 0; i < args.length; i++) {
            q.setParameter(i+1, args[i]);
        }
        q.executeUpdate();
    }

    private void updateQuery(String query, String argName, Object arg) {
        Query q = JPA.em().createQuery(query);
        q.setParameter(argName, arg);
        q.executeUpdate();
    }
}
