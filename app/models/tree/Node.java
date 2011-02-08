package models.tree;

/**
 * This interface represents a Node that is associated with a {@link GenericTreeNode}.
 * A class that needs to be a node in a tree should implement this interface.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface Node {

    /**
     * Accessor for the GenericTreeNode instance in the tree
     */
    GenericTreeNode getTreeNode();

    /**
     * Setter for the GenericTreeNode instance
     */
    void setTreeNode(GenericTreeNode node);

    /**
     * Setter for the name (when a node is created in the tree)
     * @param name
     */
    void setName(String name);

}
