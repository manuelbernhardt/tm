package controllers;

import java.util.Map;

import models.project.approach.Release;
import models.project.approach.TestCycle;
import tree.persistent.AbstractTree;
import tree.persistent.NodeType;
import tree.persistent.Node;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ApproachTree extends AbstractTree {

    public static final String APPROACH_TREE = "approachTree";

    @Override
    protected NodeType[] getNodes() {
        return new NodeType[]{type(Release.class, true), type(TestCycle.class, false)};
    }

    @Override
    protected NodeType getDefaultType() {
        return getNodeType(TestCycle.class);
    }

    @Override
    protected NodeType getRootType() {
        return getNodeType(Release.class);
    }

    @Override
    protected Node createObjectNode(String name, NodeType type, Map<String, String> args) {

        if(type.getNodeClass().equals(TestCycle.class)) {
            TestCycle cycle = new TestCycle();
            cycle.name = name;
            cycle.project = TMController.getActiveProject();
            return cycle;
        }

        if(type.getNodeClass().equals(Release.class)) {
            Release release = new Release();
            release.name = name;
            release.project = TMController.getActiveProject();
            return release;
        }

        return null;
    }
}
