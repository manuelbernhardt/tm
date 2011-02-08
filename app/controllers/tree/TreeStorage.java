package controllers.tree;

import models.tree.GenericTreeNode;
import models.tree.Node;

import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public abstract class TreeStorage {

    public abstract Node create(Node node);

    public abstract Node update(Node node);

    public abstract GenericTreeNode getNewGenericTreeNode();

    public abstract GenericTreeNode create(GenericTreeNode node);

    public abstract GenericTreeNode update(GenericTreeNode node);

    public abstract GenericTreeNode getTreeNode(Long id);

    public abstract List<GenericTreeNode> getChildren(Long parentId);

    public abstract void remove(Long id, boolean removeObject);
}
