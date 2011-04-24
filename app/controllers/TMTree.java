package controllers;

import controllers.tree.JPATreeStorage;
import models.tm.ProjectTreeNode;
import tree.persistent.AbstractTree;
import tree.persistent.GenericTreeNode;
import tree.persistent.TreeStorage;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public abstract class TMTree extends AbstractTree {

    @Override
    protected TreeStorage getStorage() {
        return new JPATreeStorage(ProjectTreeNode.class) {

            @Override
            public GenericTreeNode getNewTreeNode() {
                return new ProjectTreeNode(TMTreeController.projectThreadLocal.get());
            }
        };
    }
}
