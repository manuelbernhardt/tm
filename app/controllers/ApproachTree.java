package controllers;

import java.util.List;
import java.util.Map;

import models.general.TreeRoleHolder;
import models.general.UnitRole;
import models.tm.Project;
import models.tm.approach.Release;
import models.tm.approach.TestCycle;
import tree.persistent.Node;
import tree.persistent.NodeType;

import static models.general.UnitRole.roles;

/**
 * Test Approach tree - contains releases and test cycles
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ApproachTree extends TMTree implements TreeRoleHolder {

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
        if (projectId == null) {
            return null;
        }

        // TODO SECURITY check role (project edition)
        Project project = Lookups.getProject(Long.parseLong(projectId));

        if (type.getNodeClass().equals(TestCycle.class)) {
            TestCycle cycle = new TestCycle(project);
            cycle.name = name;
            cycle.project = project;
            return cycle;
        }

        if (type.getNodeClass().equals(Release.class)) {
            Release release = new Release(project);
            release.name = name;
            release.project = project;
            return release;
        }

        return null;
    }

    public List<UnitRole> getViewRoles() {
        return roles(UnitRole.PROJECTEDIT, UnitRole.TESTPREPCREATE);
    }

    public List<UnitRole> getCreateRoles() {
        return roles(UnitRole.PROJECTEDIT);
    }

    public List<UnitRole> getUpdateRoles() {
        return roles(UnitRole.PROJECTEDIT);
    }

    public List<UnitRole> getDeleteRoles() {
        return roles(UnitRole.PROJECTEDIT);
    }
}
