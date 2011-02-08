package controllers.tree;

import models.tree.GenericTreeNode;
import models.tree.Node;
import models.tree.jpa.TreeNode;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;

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
        ((Model)node).create();
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
        TreeNode treeNode = TreeNode.findById(id);
        if(removeObject) {
            ((Model)treeNode.getNode()).delete();
        } else {
            treeNode.getNode().setTreeNode(null);
            ((Model)treeNode.getNode()).save();
        }
        ((Model)treeNode).delete();
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
