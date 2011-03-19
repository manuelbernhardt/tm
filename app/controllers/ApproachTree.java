package controllers;

import java.util.Map;

import controllers.tree.AbstractTree;
import controllers.tree.NodeType;
import models.project.ApproachRelease;
import models.project.ApproachTestCycle;
import models.tree.Node;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ApproachTree extends AbstractTree {

    public static final String APPROACH_TREE = "approachTree";

    @Override
    protected NodeType[] getNodes() {
        return new NodeType[]{type(ApproachRelease.class, true), type(ApproachTestCycle.class, false)};
    }

    @Override
    protected NodeType getDefaultType() {
        return getNodeType(ApproachTestCycle.class);
    }

    @Override
    protected NodeType getRootType() {
        return getNodeType(ApproachRelease.class);
    }

    @Override
    protected Node createObjectNode(String name, NodeType type, Map<String, String> args) {

        if(type.getNodeClass().equals(ApproachTestCycle.class)) {
            ApproachTestCycle cycle = new ApproachTestCycle();
            cycle.name = name;
            cycle.project = TMController.getActiveProject();
            return cycle;
        }

        if(type.getNodeClass().equals(ApproachRelease.class)) {
            ApproachRelease release = new ApproachRelease();
            release.name = name;
            release.project = TMController.getActiveProject();
            return release;
        }

        return null;
    }
}
