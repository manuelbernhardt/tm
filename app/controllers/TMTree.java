package controllers;

import controllers.tree.JPATreeStorage;
import models.tm.ProjectTreeNode;
import tree.persistent.AbstractTree;
import tree.persistent.GenericTreeNode;
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

    private static class TMJPATreeStorage extends JPATreeStorage {

        public TMJPATreeStorage() {
            super(ProjectTreeNode.class);
        }

        @Override
        public GenericTreeNode getNewTreeNode() {
            return new ProjectTreeNode(TMTreeController.projectThreadLocal.get());
        }

        @Override
        public GenericTreeNode persistTreeNode(GenericTreeNode node) {
            ProjectTreeNode treeNode = (ProjectTreeNode) node;
            treeNode.create();
            return node;
        }
    }
}
