package models.tree;

import controllers.tree.NodeType;

import java.util.List;

/**
 * A generic node in a tree. Implementations of this interface need to also have an equivalent {@link controllers.tree.TreeStorage} implementation
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface GenericTreeNode {

    Long getId();

    String getName();
    void setName(String name);

    NodeType getType();
    void setType(NodeType type);

    GenericTreeNode getParent();
    void setParent(GenericTreeNode parent);

    List<? extends GenericTreeNode> getChildren();
    void addChild(GenericTreeNode child);

    Node getNode();
    void setNode(Node n);

    boolean isOpen();
    void setOpen(boolean open);

    String getPath();
    void setPath(String path);

}
