package controllers;

import java.util.Map;

import models.tm.Project;
import models.tm.approach.Release;
import models.tm.approach.TestCycle;
import tree.persistent.AbstractTree;
import tree.persistent.Node;
import tree.persistent.NodeType;

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

        String projectId = args.get("projectId");
        if(projectId == null) {
            return null;
        }
        Project project = Project.findById(Long.parseLong(projectId));

        // TODO check role as well
        if(!project.isInAccount(TMController.getUserAccount())) {
            return null;
        }

        if(type.getNodeClass().equals(TestCycle.class)) {
            TestCycle cycle = new TestCycle();
            cycle.name = name;
            cycle.project = project;
            return cycle;
        }

        if(type.getNodeClass().equals(Release.class)) {
            Release release = new Release();
            release.name = name;
            release.project = project;
            return release;
        }

        return null;
    }
}
