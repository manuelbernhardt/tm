package models.tree;

import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface JSTreeNode {

    String getName();
    Long getId();
    boolean isContainer();
    boolean isOpen();
    String getType();
    List<JSTreeNode> getChildren();
}
