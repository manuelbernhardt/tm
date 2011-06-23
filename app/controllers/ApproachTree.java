package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.general.TreeRoleHolder;
import models.general.UnitRole;
import models.tm.Project;
import models.tm.ProjectTreeNode;
import models.tm.approach.Release;
import models.tm.approach.TestCycle;
import models.tree.jpa.TreeNode;
import play.db.jpa.JPA;
import play.libs.F;
import tree.JSTreeNode;
import tree.persistent.GenericTreeNode;
import tree.persistent.Node;
import tree.persistent.NodeType;
import tree.persistent.RootNode;

import static models.general.UnitRole.roles;

/**
 * Test Approach tree - contains releases and test cycles.
 * This tree is special in that it is shared between the admin area and the rest of the application, and there is no
 * an "active project" concept in the admin area, this we have to handle the project linking differently.
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
    protected String getRootName() {
        return "Releases";
    }

    @Override
    public List<? extends JSTreeNode> getChildren(final Long parentId, final String type, Map<String, String> args) {
        final Long projectId = Long.parseLong(args.get("projectId"));

        if (projectId == null) {
            return null;
        }

        final TMJPATreeStorage storage = (TMJPATreeStorage) getStorage();

        List<JSTreeNode> children = null;
        if (parentId == null || parentId == -1) {
            children = storage.findJSTreeNodes("from TreeNode n where n.treeId = '" + getName() + "' and n.threadRoot = n and n.project.id = ?", projectId);
        } else {
            GenericTreeNode parent = storage.getTreeNode(parentId, type, getName());
            children = storage.findJSTreeNodes("from TreeNode n where n.treeId = '" + getName() + "' and n.level = ? and n.path like ? and n.threadRoot = ?", parent.getLevel() + 1, parent.getPath() + "%", parent.getThreadRoot());
        }

        RootNode rootNode = new RootNode(getRootName(), -1l, true, true, "root", children);
        List<JSTreeNode> nodes = new ArrayList<JSTreeNode>();
        nodes.add(rootNode);
        return nodes;
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

    @Override
    public boolean remove(Long id, Long parentId, String type, Map<String, String> args) {

        // before removing a Release or a Cycle, check whether there are no linked test instances.
        if(getNodeType(type).getNodeClass().equals(Release.class)) {
            Release release = Lookups.getRelease(id);
            if(release == null) {
                return false;
            }

            // Release -> TreeNode of Release -> TreeNodes of Children -> Cycles -> Instances
            ProjectTreeNode releaseNode = TreeNode.find("from ProjectTreeNode n where n.nodeId = ? and n.treeId = ? and n.type = ?", release.getId(), ApproachTree.APPROACH_TREE, ScriptCycleTreeDataHandler.RELEASE).first();
            List resultList = JPA.em().createQuery("from ProjectTreeNode n, Instance i, TestCycle c where i.testCycle = c and n.nodeId = c.id and n.treeId = :treeId and n.level = :level and n.path like :pathLike and n.threadRoot = :threadRoot")
                    .setParameter("treeId", ApproachTree.APPROACH_TREE)
                    .setParameter("level", releaseNode.getLevel() + 1)
                    .setParameter("pathLike", releaseNode.getPath() + "%")
                    .setParameter("threadRoot", releaseNode.getThreadRoot())
                    .getResultList();
            if(resultList.size() > 0) {
                return false;
            }
        } else if(getNodeType(type).getNodeClass().equals(TestCycle.class)) {
            TestCycle cycle = Lookups.getCycle(id);
            if(cycle == null) {
                return false;
            }
            if(cycle.getInstances().size() > 0) {
                return false;
            }
        }

        return super.remove(id, parentId, type, args);
    }

    public List<UnitRole> getViewRoles() {
        return roles(UnitRole.PROJECTEDIT, UnitRole.PROJECTADMIN, UnitRole.TESTPREPCREATE);
    }

    public List<UnitRole> getCreateRoles() {
        return roles(UnitRole.PROJECTEDIT, UnitRole.PROJECTADMIN);
    }

    public List<UnitRole> getUpdateRoles() {
        return roles(UnitRole.PROJECTEDIT, UnitRole.PROJECTADMIN);
    }

    public List<UnitRole> getDeleteRoles() {
        return roles(UnitRole.PROJECTEDIT, UnitRole.PROJECTADMIN);
    }
}
