package controllers;

import controllers.tree.JPATreeStorage;
import models.tm.ProjectTreeNode;
import tree.persistent.GenericTreeNode;

/**
* @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
*/
public class TMJPATreeStorage extends JPATreeStorage {

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
