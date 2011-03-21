package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.project.ApproachRelease;
import models.project.ApproachTestCycle;
import models.project.TestInstance;
import models.project.TestScript;
import models.tree.jpa.TreeNode;
import tree.JSTreeNode;
import tree.TreeDataHandler;
import tree.simple.ChildProducer;
import tree.simple.SimpleNode;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ScriptCycleTreeDataHandler implements TreeDataHandler {

    public String getName() {
        return "scriptCycleTree";
    }

    public List<? extends JSTreeNode> getChildren(Long parentId, Map<String, String> args) {

        if (parentId == -1) {

            TestScript script = getScript(args.get("scriptId"));
            List<TestInstance> instances = TestInstance.find("from TestInstance i where i.testScript = ?", script).fetch();
            List<ApproachTestCycle> cycles = new ArrayList<ApproachTestCycle>();
            for (TestInstance testInstance : instances) {
                cycles.add(testInstance.testCycle);
            }
            final Map<ApproachRelease, List<ApproachTestCycle>> releases = new HashMap<ApproachRelease, List<ApproachTestCycle>>();
            for (ApproachTestCycle c : cycles) {
                TreeNode n = TreeNode.find("from TreeNode n where n.nodeId = ? and n.treeId = ? and n.type = ?", c.getId(), ApproachTree.APPROACH_TREE, "approachRelease").first();
                ApproachRelease r = ApproachRelease.findById(n.getParent().getNodeId());
                List<ApproachTestCycle> cycleList = releases.get(r);
                if (cycleList == null) {
                    cycleList = new ArrayList<ApproachTestCycle>();
                    releases.put(r, cycleList);
                }
                cycleList.add(c);
            }

            List<JSTreeNode> result = new ArrayList<JSTreeNode>();
            for (ApproachRelease r : releases.keySet()) {
                ChildProducer p = new ChildProducer() {
                    public List<JSTreeNode> produce(Long id) {
                        List<JSTreeNode> children = new ArrayList<JSTreeNode>();
                        for (ApproachRelease parent : releases.keySet()) {
                            if (parent.getId().equals(id)) {
                                for (ApproachTestCycle c : releases.get(parent)) {
                                    children.add(new SimpleNode(c.getId(), c.name, "approachTestCycle", false, false, null));
                                }
                            }
                        }
                        return children;
                    }
                };
                SimpleNode rNode = new SimpleNode(r.getId(), r.name, "approachRelease", true, true, p);
                result.add(rNode);
            }
            return result;
        }

        return null;
    }

    private TestScript getScript(String sId) {
        if (sId == null) {
            return null;
        }
        TestScript ts = TestScript.findById(Long.parseLong(sId));
        if (ts == null) {
            return null;
        }
        if (!ts.isInAccount(TMController.getUserAccount())) {
            return null;
        }
        return ts;
    }

    public Long create(Long parentId, Long position, String name, String type, Map<String, String> args) {

        if (type.equals("approachTestCycle")) {
            String cycleNodeId = args.get("cycleNodeId");
            String scriptId = args.get("scriptId");
            if (cycleNodeId == null || scriptId == null) {
                return null;
            }

            TreeNode cycleNode = TreeNode.find(Long.parseLong(cycleNodeId), ApproachTree.APPROACH_TREE);
            if (cycleNode == null) {
                return null;
            }
            ApproachTestCycle cycle = ApproachTestCycle.findById(cycleNode.nodeId);
            TestScript script = TestScript.findById(Long.parseLong(scriptId));
            if (cycle == null || script == null) {
                return null;
            }
            if (!cycle.isInAccount(TMController.getUserAccount()) || !script.isInAccount(TMController.getUserAccount())) {
                return null;
            }
            TestInstance ti = TestInstance.find(script, cycle);
            if (ti != null) {
                return null;
            }
            ti = new TestInstance();
            ti.project = script.project;
            ti.testCycle = cycle;
            ti.testScript = script;
            ti.name = script.name;
            boolean created = ti.create();
            if (!created) {
                // TODO log error
                return null;
            }
            return ti.getId();
        }

        return null;
    }

    public boolean rename(Long id, String name, String type) {
        return false;
    }

    public void copy(Long id, Long target, Long position) {
    }

    public void move(Long id, Long target, Long position) {
    }

    public boolean remove(Long id, Long parentId, String type, Map<String, String> args) {
        String scriptId = args.get("scriptId");
        if (scriptId == null) {
            return false;
        }
        TestScript script = TestScript.findById(Long.parseLong(scriptId));
        if (script == null || !script.isInAccount(TMController.getUserAccount())) {
            return false;
        }
        ApproachTestCycle cycle = ApproachTestCycle.findById(id);
        if (cycle == null || !cycle.isInAccount(TMController.getUserAccount())) {
            return false;
        }
        TestInstance ti = TestInstance.find(script, cycle);
        try {
            ti.delete();
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return true;
    }
}
