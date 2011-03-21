package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.project.approach.Release;
import models.project.approach.TestCycle;
import models.project.test.Instance;
import models.project.test.Script;
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

            Script script = getScript(args.get("scriptId"));
            List<Instance> instances = Instance.find("from Instance i where i.script = ?", script).fetch();
            List<TestCycle> cycles = new ArrayList<TestCycle>();
            for (Instance instance : instances) {
                cycles.add(instance.testCycle);
            }
            final Map<Release, List<TestCycle>> releases = new HashMap<Release, List<TestCycle>>();
            for (TestCycle c : cycles) {
                TreeNode n = TreeNode.find("from TreeNode n where n.nodeId = ? and n.treeId = ? and n.type = ?", c.getId(), ApproachTree.APPROACH_TREE, "release").first();
                Release r = Release.findById(n.getParent().getNodeId());
                List<TestCycle> cycleList = releases.get(r);
                if (cycleList == null) {
                    cycleList = new ArrayList<TestCycle>();
                    releases.put(r, cycleList);
                }
                cycleList.add(c);
            }

            List<JSTreeNode> result = new ArrayList<JSTreeNode>();
            for (Release r : releases.keySet()) {
                ChildProducer p = new ChildProducer() {
                    public List<JSTreeNode> produce(Long id) {
                        List<JSTreeNode> children = new ArrayList<JSTreeNode>();
                        for (Release parent : releases.keySet()) {
                            if (parent.getId().equals(id)) {
                                for (TestCycle c : releases.get(parent)) {
                                    children.add(new SimpleNode(c.getId(), c.name, "testCycle", false, false, null));
                                }
                            }
                        }
                        return children;
                    }
                };
                SimpleNode rNode = new SimpleNode(r.getId(), r.name, "release", true, true, p);
                result.add(rNode);
            }
            return result;
        }

        return null;
    }

    private Script getScript(String sId) {
        if (sId == null) {
            return null;
        }
        Script ts = Script.findById(Long.parseLong(sId));
        if (ts == null) {
            return null;
        }
        if (!ts.isInAccount(TMController.getUserAccount())) {
            return null;
        }
        return ts;
    }

    public Long create(Long parentId, Long position, String name, String type, Map<String, String> args) {

        if (type.equals("testCycle")) {
            String cycleNodeId = args.get("cycleNodeId");
            String scriptId = args.get("scriptId");
            if (cycleNodeId == null || scriptId == null) {
                return null;
            }

            TreeNode cycleNode = TreeNode.find(Long.parseLong(cycleNodeId), ApproachTree.APPROACH_TREE);
            if (cycleNode == null) {
                return null;
            }
            TestCycle cycle = TestCycle.findById(cycleNode.nodeId);
            Script script = Script.findById(Long.parseLong(scriptId));
            if (cycle == null || script == null) {
                return null;
            }
            if (!cycle.isInAccount(TMController.getUserAccount()) || !script.isInAccount(TMController.getUserAccount())) {
                return null;
            }
            Instance ti = Instance.find(script, cycle);
            if (ti != null) {
                return null;
            }
            ti = new Instance();
            ti.project = script.project;
            ti.testCycle = cycle;
            ti.script = script;
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
        Script script = Script.findById(Long.parseLong(scriptId));
        if (script == null || !script.isInAccount(TMController.getUserAccount())) {
            return false;
        }
        TestCycle cycle = TestCycle.findById(id);
        if (cycle == null || !cycle.isInAccount(TMController.getUserAccount())) {
            return false;
        }
        Instance ti = Instance.find(script, cycle);
        try {
            ti.delete();
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return true;
    }
}
