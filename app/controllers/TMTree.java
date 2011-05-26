package controllers;

import models.tm.ProjectTreeNode;
import tree.persistent.AbstractTree;
import tree.persistent.Node;
import tree.persistent.TreeStorage;

/**
 * TM specific implementation of an {@link AbstractTree}.
 * It customizes the node persistence (by telling the Tree module to use the @{link ProjectTreeNode}
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public abstract class TMTree extends AbstractTree {

    @Override
    protected TreeStorage getStorage() {
        return new TMJPATreeStorage();
    }

    public static String getPath(Long id, Class<? extends Node> type, Class<? extends TMTree> treeClass) {
        ProjectTreeNode node = ProjectTreeNode.find("from ProjectTreeNode n where n.nodeId = ? and n.type = ? and n.treeId = ?", id, getNodeType(type).getName(), getTreeId(treeClass)).<ProjectTreeNode>first();
        return node.getPath();

    }

    private static String getTreeId(Class<? extends TMTree> treeClass) {
        return treeClass.getSimpleName().substring(0, 1) + treeClass.getSimpleName().substring(1);
    }

}
