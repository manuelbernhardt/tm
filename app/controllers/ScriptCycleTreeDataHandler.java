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

    public static final String INSTANCE = "default";
    public static final String TEST_CYCLE = "testCycle";
    public static final String RELEASE = "release";

    public String getName() {
        return "scriptCycleTree";
    }

    public List<? extends JSTreeNode> getChildren(Long parentId, Map<String, String> args) {
        Script script = getScript(args.get("scriptId"));
        final CycleChildProducer cycleChildProducer = new CycleChildProducer(script);

        if (parentId == -1) {
            // releases
            List<Instance> instances = Instance.find("from Instance i where i.script = ?", script).fetch();
            List<TestCycle> cycles = new ArrayList<TestCycle>();
            for (Instance instance : instances) {
                cycles.add(instance.testCycle);
            }
            final Map<Release, List<TestCycle>> releases = new HashMap<Release, List<TestCycle>>();
            for (TestCycle c : cycles) {
                TreeNode n = TreeNode.find("from TreeNode n where n.nodeId = ? and n.treeId = ? and n.type = ?", c.getId(), ApproachTree.APPROACH_TREE, "testCycle").first();
                Release r = Release.findById(n.getParent().getNodeId());
                List<TestCycle> cycleList = releases.get(r);
                if (cycleList == null) {
                    cycleList = new ArrayList<TestCycle>();
                    releases.put(r, cycleList);
                }
                if(!cycleList.contains(c)) {
                    cycleList.add(c);
                }
            }
            ChildProducer releaseChildProducer = new ReleaseChildProducer(releases, cycleChildProducer);

            List<JSTreeNode> result = new ArrayList<JSTreeNode>();
            for (Release r : releases.keySet()) {
                SimpleNode rNode = new SimpleNode(r.getId(), r.name, RELEASE, true, true, releaseChildProducer);
                result.add(rNode);
            }
            return result;
        } else {
            return null;
        }
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
            Instance ti = new Instance();
            ti.project = script.project;
            ti.testCycle = cycle;
            ti.script = script;
            ti.name = "Test instance " + (Instance.count("from Instance i where i.script = ? and i.testCycle = ?", script, cycle) + 1) + " for test script " + script.name;
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
        Instance instance = Instance.findById(id);
        if (instance == null || !instance.isInAccount(TMController.getUserAccount())) {
            return false;
        }
        try {
            instance.delete();
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return true;
    }

    private static class ReleaseChildProducer implements ChildProducer {
        private final Map<Release, List<TestCycle>> releases;
        private final CycleChildProducer cycleChildProducer;

        public ReleaseChildProducer(Map<Release, List<TestCycle>> releases, CycleChildProducer cycleChildProducer) {
            this.releases = releases;
            this.cycleChildProducer = cycleChildProducer;
        }

        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> children = new ArrayList<JSTreeNode>();
            for (Release parent : releases.keySet()) {
                if (parent.getId().equals(id)) {
                    for (TestCycle c : releases.get(parent)) {
                        children.add(new SimpleNode(c.getId(), c.name, TEST_CYCLE, true, true, cycleChildProducer));
                    }
                }
            }
            return children;
        }
    }

    private static final class CycleChildProducer implements ChildProducer {
        private final Script script;

        private CycleChildProducer(Script script) {
            this.script = script;
        }

        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> children = new ArrayList<JSTreeNode>();
            List<Instance> instances = Instance.find("from Instance i where i.script = ? and i.testCycle.id = ?", script, id).<Instance>fetch();
            for (Instance instance : instances) {
                children.add(new SimpleNode(instance.getId(), instance.name, INSTANCE, false, false, null));
            }
            return children;
        }
    }

}