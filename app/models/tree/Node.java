package models.tree;

import controllers.tree.NodeType;

import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface Node {

    Long getId();

    String getName();
    void setName(String name);

    NodeType getType();
    void setType(NodeType type);

    Node getParent();
    void setParent(Node parent);

    List<? extends Node> getChildren();
    void addChild(Node child);

    boolean isOpen();
    void setOpened(boolean opened);

}
