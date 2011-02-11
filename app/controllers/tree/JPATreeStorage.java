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
        if(node.getParent() != null) {
            ((TreeNode) node).setLevel(((TreeNode) node.getParent()).getLevel() + 1);
        } else {
            ((TreeNode) node).setLevel(0);
        }
        ((TreeNode) node).create();
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
            updateQuery("delete from AbstractNode a where a.id = ?", oid);
            deleteNode(id, removeObject);
        } else {
            updateQuery("update AbstractNode a set a.treeNode = null where a.treeNode.id = ?", id);
            removeReferences(id);
            deleteNode(id, removeObject);
        }
    }

    private void deleteNode(Long id, boolean removeObject) {
        // kill the kids
        TreeNode parent = TreeNode.findById(id);
        Query q = JPA.em().createQuery("select n.id from TreeNode n where n.path like :path and n.id <> :id order by n.path desc");
        q.setParameter("id", id);
        String pathLike = parent.getPath() + "%";
        q.setParameter("path", pathLike);
        List<Long> kids = q.getResultList();
        System.out.println();
        System.out.println("*** " + pathLike);
        System.out.println("*** " + kids);
        updateQuery("update TreeNode n set n.parent = null, n.abstractNode = null where n.path like ? and n.id <> ?", pathLike, id);
        if (removeObject) {
            namedUpdateQuery("delete from AbstractNode a where a.treeNode.id in (:kids)", "kids", kids);
        } else {
            namedUpdateQuery("update AbstractNode a set a.treeNode = null where a.treeNode.id in (:kids)", "kids", kids);
        }
        namedUpdateQuery("delete from TreeNode n where n.id in (:kids)", "kids", kids);
        updateQuery("delete from TreeNode n where n.id = ?", id);
    }

    private void removeReferences(Long id) {
        updateQuery("update TreeNode n set n.abstractNode = null, n.parent = null where n.id = ?", id);
    }

    private Long getAbstractNodeId(Long id) {
        return query("select a.id from TreeNode n join n.abstractNode a where n.id = :id", id, Long.class);
    }

    @Override
    public List<GenericTreeNode> getChildren(Long parentId) {
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

    @Override
    public void move(Long id, Long target) {
        TreeNode parent = TreeNode.findById(target);
        if (parent.getPath() != null) {
            updateQuery("update TreeNode n set n.parent = ?, n.path = " +
                    "concat( ? , concat( '___', concat( n.name, n.id ) ) ) where n.id = ?", parent, parent.getPath(), id);
            // update the children's paths
            updateQuery("update TreeNode n set n.path = " +
                    "(concat( ?, concat('___', concat( n.name, n.id ) ) ) ) where n.parent.id = ?", parent.getPath(), id);

        } else {
            updateQuery("update TreeNode n set n.parent = (select p from TreeNode p where p.id = ?), n.path = " +
                    "( concat( n.name, n.id ) ) ) where n.id = ?", target, parent.getPath(), id);
            // update the children's paths
            updateQuery("update TreeNode n set n.path = " +
                    "( concat( n.name, n.id ) ) where n.parent.id = ?", id);
        }

    }

    @Override
    public void copy(Long id, Long target, boolean copyObject) {
        if (!copyObject) {
            // copy the tree structure
            Query q = JPA.em().createQuery(String.format("insert into TreeNode (name, typeName, opened, %s path, copyParentId, copyBatchId) select c.name, c.typeName, c.opened, %s c.path, c.parent.path, concat(:id, :target) from TreeNode c where (c.id = :id or c.parent.id = :id)", "abstractNode,", "c.abstractNode,"));
            q.setParameter("target", target);
            q.setParameter("id", id);
            q.executeUpdate();

            // need to set the parent
            // this sucks but it's the best I could come up with now.
            String copyBatch = new String(id + "" + target);

            // update the kids
            List<String> parentPaths = queryList("select n.copyParentId from TreeNode n where n.copyBatchId = :id group by n.copyParentId", copyBatch, String.class);
            for (String pp : parentPaths) {
                TreeNode p = query("select n from TreeNode n where n.path = ? and n.path is not null and n.copyBatchId = ?", TreeNode.class, pp, copyBatch);
                updateQuery("update TreeNode set parent = ? where copyParentId = ? and copyBatchId = ?", p, pp, copyBatch);
            }
            // update the root of the copy
            TreeNode origin = TreeNode.findById(id);
            TreeNode parent = TreeNode.findById(target);
            updateQuery("update TreeNode set parent = ? where copyBatchId = ? and name = ? and typeName = ? and opened = ? and path = ?", parent, copyBatch, origin.getName(), origin.getType().getName(), origin.isOpen(), origin.getPath());

            // re-compute the paths

            // clean up


        } else {

        }

    }

    private <T> T query(String query, Object id, Class<T> type) {
        Query q = JPA.em().createQuery(query);
        q.setParameter("id", id);
        return (T) q.getSingleResult();
    }

    private <T> T query(String query, Class<T> type, Object... args) {
        Query q = JPA.em().createQuery(query);
        for (int i = 0; i < args.length; i++) {
            q.setParameter(i + 1, args[i]);
        }
        List r = q.getResultList();
        if (r.isEmpty()) return null;
        return (T) r.get(0);
    }


    private <T> List<T> queryList(String query, Object id, Class<T> type) {
        Query q = JPA.em().createQuery(query);
        q.setParameter("id", id);
        return (List<T>) q.getResultList();
    }

    private void updateQuery(String query, Object... args) {
        Query q = JPA.em().createQuery(query);
        for (int i = 0; i < args.length; i++) {
            q.setParameter(i + 1, args[i]);
        }
        q.executeUpdate();
    }

    private void namedUpdateQuery(String query, String argName, Object arg) {
        Query q = JPA.em().createQuery(query);
        q.setParameter(argName, arg);
        q.executeUpdate();
    }
}
