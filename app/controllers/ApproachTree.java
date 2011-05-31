package controllers;

import java.util.List;
import java.util.Map;

import models.general.TreeRoleHolder;
import models.general.UnitRole;
import models.tm.Project;
import models.tm.approach.Release;
import models.tm.approach.TestCycle;
import play.libs.F;
import tree.JSTreeNode;
import tree.persistent.GenericTreeNode;
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
    public List<? extends JSTreeNode> getChildren(Long parentId, String type, Map<String, String> args) {
        Long projectId = Long.parseLong(args.get("projectId"));

        if (projectId == null) {
            return null;
        }

        TMJPATreeStorage storage = (TMJPATreeStorage) getStorage();
        if (parentId == null || parentId == -1) {
            return storage.findJSTreeNodes("from TreeNode n where n.treeId = '" + getName() + "' and n.threadRoot = n and n.project.id = ?", projectId);
        } else {
            GenericTreeNode parent = storage.getTreeNode(parentId, type, getName());
            return storage.findJSTreeNodes("from TreeNode n where n.treeId = '" + getName() + "' and n.level = ? and n.path like ? and n.threadRoot = ?", parent.getLevel() + 1, parent.getPath() + "%", parent.getThreadRoot());
        }
    }

    @Override
    public F.Tuple<Long, String> create(Long parentId, String parentType, Long position, String name, String type, Map<String, String> args) {
        Long projectId = Long.parseLong(args.get("projectId"));
        if (projectId == null) {
            return null;
        }
        Project p = Lookups.getProject(projectId);

        // we need to set the threadLocal by hand for the rest of the mechanism to work (see TMJPATreeStorage)
        TMTreeController.projectThreadLocal.set(p);
        F.Tuple<Long, String> res = super.create(parentId, parentType, position, name, type, args);
        TMTreeController.projectThreadLocal.set(null);

        return res;
    }

    @Override
    protected Node createObjectNode(String name, NodeType type, Map<String, String> args) {

        String projectId = args.get("projectId");
        if (projectId == null) {
            return null;
        }

        Project project = Lookups.getProject(Long.parseLong(projectId));

        if (type.getNodeClass().equals(TestCycle.class)) {
            TestCycle cycle = new TestCycle(project);
            cycle.name = name;
            return cycle;
        }

        if (type.getNodeClass().equals(Release.class)) {
            Release release = new Release(project);
            release.name = name;
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
