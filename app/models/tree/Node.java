package models.tree;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface Node {

    Long getId();

    GenericTreeNode getTreeNode();
    void setTreeNode(GenericTreeNode node);

    String getName();
    void setName(String name);

}
