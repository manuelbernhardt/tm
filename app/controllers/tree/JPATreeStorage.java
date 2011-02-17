package controllers.tree;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;

import models.tree.GenericTreeNode;
import models.tree.Node;
import models.tree.jpa.AbstractNode;
import models.tree.jpa.TreeNode;
import play.db.jpa.JPA;
import play.db.jpa.JPABase;
import play.db.jpa.Model;

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
        TreeNode treeNode = (TreeNode) node;
        treeNode.create();
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
        JPABase node = TreeNode.findById(id);
        return (GenericTreeNode) node;

    }

    @Override
    public void remove(Long id, boolean removeObject) {
        TreeNode parent = TreeNode.findById(id);

        String pathLike = parent.getPath() + "%";
        List<Long> kids = queryList("select n.id from TreeNode n where n.path like ? and n.level > ? and n.threadRoot.id = ? order by n.path desc", Long.class, pathLike, parent.getLevel(), parent.getThreadRoot().getId());
        if (!kids.isEmpty()) {
            List<Long> nodes = queryList("select n.abstractNode.id from TreeNode n where n.path like ? and n.level > ? and n.threadRoot.id = ? order by n.path desc", Long.class, pathLike, parent.getLevel(), parent.getThreadRoot().getId());
            if (removeObject) {
                namedUpdateQuery("update TreeNode n set n.abstractNode = null where n.id in (:kids)", "kids", kids);
                namedUpdateQuery("delete from AbstractNode a where a.id in (:nodes)", "nodes", nodes);
            }
            namedUpdateQuery("delete from TreeNode n where n.id in (:kids)", "kids", kids);
        }

        updateQuery("update TreeNode n set n.abstractNode = null, n.threadRoot = null where n.id = ?", id);

        if (removeObject) {
            updateQuery("delete from AbstractNode a where a.id = ?", parent.abstractNode.getId());
        }
        updateQuery("delete from TreeNode n where n.id = ?", id);
    }

    @Override
    public List<GenericTreeNode> getChildren(Long parentId) {
        if (parentId == null || parentId == -1) {
            return TreeNode.find("from TreeNode n where n.threadRoot = n").fetch();
        } else {
            TreeNode parent = TreeNode.findById(parentId);
            return getChildren(parent.getLevel(), parent.getPath(), parent.getThreadRoot());
        }
    }

    public static List<GenericTreeNode> getChildren(Integer parentLevel, String parentPath, TreeNode parentThreadRoot) {
        return TreeNode.find("from TreeNode n where n.level = ? and n.path like ? and n.threadRoot = ?", parentLevel + 1, parentPath + "%", parentThreadRoot).fetch();
    }

    @Override
    public void rename(Long id, String name) {
        updateQuery("update TreeNode n set n.name = ? where n.id = ?", name, id);
        updateQuery("update AbstractNode a set a.name = ? where a.id = (select n.abstractNode.id from TreeNode n where n.id = ?)", name, id);
    }

    @Override
    public void move(Long id, Long target) {
        TreeNode node = TreeNode.findById(id);
        TreeNode oldParent = (TreeNode) node.getParent();
        TreeNode parent = TreeNode.findById(target);

        String newPath = parent.getPath();
        Integer delta = parent.getLevel() - node.getLevel() + 1;

        if (node.getThreadRoot().getId() == node.getId()) {
            updateQuery("update TreeNode set path = concat(?, path), level = level + ? where threadRoot = ?", newPath + "____", delta, parent.getThreadRoot());
        } else {
            String oldPath = node.getPath();
            Integer oldPathLength = oldParent.getPath().length();
            String pathLike = oldPath + "%";
            updateQuery("update TreeNode set path = concat(?, substring(path, ?, length(path))), level = level + ? where threadRoot = ? and path like ?", newPath, oldPathLength + 1, delta, parent.getThreadRoot(), pathLike);
        }
    }


    @Override
    public void copy(Long id, Long target, boolean copyObject, NodeType[] types) {
        TreeNode node = TreeNode.findById(id);
        TreeNode parent = TreeNode.findById(target);
        Integer delta = parent.getLevel() - node.getLevel() + 1;
        String oldPath = node.getPath();
        String newPath = parent.getThreadRoot().getId() == parent.getId() ? parent.getPath() + "___" : parent.getPath();
        Integer oldPathLength = node.getParent().getPath().length();
        String pathLike = oldPath + "%";

        // see http://opensource.atlassian.com/projects/hibernate/browse/HHH-2692
//            Query query = JPA.em().createQuery("insert into TreeNode (name, typeName, opened, level, path, threadRoot, abstractNode) " +
//                    "select c.name, c.typeName, c.opened, c.level + :delta, concat(:newPath, substring(path, :oldPathLength, length(path))), (select :newThreadRoot from AbstractNode), c.abstractNode " +
//                    "from TreeNode c " +
//                    "where c.threadRoot = :oldThreadRoot and c.path like :pathLike");
//            query.setParameter("delta", delta);
//            query.setParameter("newPath", newPath);
//            query.setParameter("oldPathLength", oldPathLength + 1);
//            query.setParameter("newThreadRoot", parent.getThreadRoot());
//            query.setParameter("oldThreadRoot", node.getThreadRoot());
//            query.setParameter("pathLike", pathLike);

        Query query = JPA.em().createNativeQuery("insert into TreeNode (name, typeName, opened, level, path, threadRoot_id, abstractNode_id) " +
                "select c.name, c.typeName, c.opened, c.level + ?, concat(?, substring(path, ?, length(path))), ?, c.abstractNode_id " +
                "from TreeNode c " +
                "where c.threadRoot_id = ? and c.path like ?");
        query.setParameter(1, delta);
        query.setParameter(2, newPath);
        query.setParameter(3, oldPathLength + 1);
        query.setParameter(4, parent.getThreadRoot().getId());
        query.setParameter(5, node.getThreadRoot().getId());
        query.setParameter(6, pathLike);

        query.executeUpdate();

        if (copyObject) {
            // fetch all (type, (AbstractNode, TreeNode)) where TreeNode-s are the freshly copied nodes (they still point to the original AbstractNode)
            Map<String, Map<Long, Long>> nodeIdsByType = new HashMap<String, Map<Long, Long>>();
            Query copied = JPA.em().createNativeQuery("select n.typeName, n.abstractNode_id, n.id from TreeNode n join TreeNode o where n.abstractNode_id = o.abstractNode_id and n.id <> o.id and n.path not like ?");
            copied.setParameter(1, pathLike);
            List<Object[]> ids = (List<Object[]>) copied.getResultList();
            for (Object[] p : ids) {
                String type = (String) p[0];
                Map<Long, Long> i = nodeIdsByType.get(type);
                if (i == null) {
                    i = new HashMap<Long, Long>();
                    nodeIdsByType.put(type, i);
                }
                i.put(((BigInteger) p[1]).longValue(), ((BigInteger) p[2]).longValue());
            }

            // with the inheritance strategy in use we have no choice but to copy the concrete nodes by hand using reflection
            for (NodeType type : types) {
                Map<Long, Long> copyIds = new HashMap<Long, Long>();
                Map<Long, Long> i = nodeIdsByType.get(type.getName());
                if(i != null) {
                    try {
                        Query q = JPA.em().createQuery("from " + type.getNodeClass().getSimpleName() + " n where n.id in (:ids)");
                        q.setParameter("ids", i.keySet());
                        List<AbstractNode> original = q.getResultList();
                        for (AbstractNode n : original) {
                            AbstractNode copy = (AbstractNode) n.clone();
                            copy.id = null;
                            copy.create();
                            copyIds.put(i.get(n.getId()), copy.getId());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // TODO see if this can be optimized
                    for (Long tn : copyIds.values()) {
                        updateQuery("update TreeNode set abstractNode = (select a.id from AbstractNode a where a.id = ?) where id = ?", copyIds.get(tn), tn);
                    }
                }
            }
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

    private <T> List<T> queryList(String query, Class<T> type, Object... args) {
        Query q = JPA.em().createQuery(query);
        for (int i = 0; i < args.length; i++) {
            q.setParameter(i + 1, args[i]);
        }
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

    private static class CopyId {
        private final Long id;
        private final Long abstractNodeId;
        private final String type;

        private CopyId(Long id, Long abstractNodeId, String type) {
            this.id = id;
            this.abstractNodeId = abstractNodeId;
            this.type = type;
        }

        public Long getId() {
            return id;
        }

        public Long getAbstractNodeId() {
            return abstractNodeId;
        }

        public String getType() {
            return type;
        }
    }
}
