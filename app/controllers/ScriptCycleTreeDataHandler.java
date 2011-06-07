package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.general.TreeRoleHolder;
import models.general.UnitRole;
import models.tm.approach.Release;
import models.tm.approach.TestCycle;
import models.tm.test.Instance;
import models.tm.test.InstanceParam;
import models.tm.test.Script;
import models.tm.test.ScriptParam;
import play.libs.F;
import tree.JSTreeNode;
import tree.TreeDataHandler;
import tree.simple.ChildProducer;
import tree.simple.SimpleNode;
import util.Logger;

import static models.general.UnitRole.roles;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ScriptCycleTreeDataHandler implements TreeDataHandler, TreeRoleHolder {

    public static final String INSTANCE = "default";
    public static final String TEST_CYCLE = "testCycle";
    public static final String RELEASE = "release";

    public String getName() {
        return "scriptCycleTree";
    }

    public List<? extends JSTreeNode> getChildren(Long parentId, String type, Map<String, String> args) {
        String sId = args.get("scriptId");
        if (sId.equals("-1")) {
            return null;
        }
        Script script = getScript(sId);
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
                Release r = c.getRelease();
                List<TestCycle> cycleList = releases.get(r);
                if (cycleList == null) {
                    cycleList = new ArrayList<TestCycle>();
                    releases.put(r, cycleList);
                }
                if (!cycleList.contains(c)) {
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
        if (!ts.isInAccount(TMController.getConnectedUserAccount())) {
            return null;
        }
        return ts;
    }

    public F.Tuple<Long, String> create(Long parentId, String parentType, Long position, String name, String type, Map<String, String> args) {

        if (type.equals(ScriptCycleTreeDataHandler.TEST_CYCLE)) {
            String cycleNodeId = args.get("cycleNodeId");
            String scriptId = args.get("scriptId");
            if (cycleNodeId == null || scriptId == null) {
                return null;
            }
            TestCycle cycle = TestCycle.findById(Long.parseLong(cycleNodeId));
            Script script = Script.findById(Long.parseLong(scriptId));
            if (cycle == null || script == null) {
                return null;
            }
            if (!cycle.isInAccount(TMController.getConnectedUserAccount()) || !script.isInAccount(TMController.getConnectedUserAccount())) {
                return null;
            }
            Instance ti = new Instance(script.project);
            ti.testCycle = cycle;
            ti.script = script;
            ti.name = script.name + " " + (Instance.count("from Instance i where i.script = ? and i.testCycle = ?", script, cycle) + 1);

            boolean created = ti.create();
            if (!created) {
                Logger.error(Logger.LogType.TECHNICAL, "Could not create test instance");
                return null;
            }

            // create the InstanceParams
            for (ScriptParam param : script.getParams()) {
                InstanceParam instanceParam = new InstanceParam(script.project);
                instanceParam.scriptParam = param;
                instanceParam.instance = ti;
                instanceParam.project = param.project;
                instanceParam.create();
            }

            return new F.Tuple<Long, String>(ti.getId(), INSTANCE);
        }

        return null;
    }

    public boolean rename(Long id, String name, String type) {

        if (INSTANCE.equals(type)) {
            Instance instance = Instance.findById(id);
            instance.name = name;
            instance.save();
            return true;
        }
        return false;
    }

    public boolean copy(Long id, Long target, Long position) {
        return false;
    }

    public boolean move(Long id, String type, Long target, String targetType, Long position) {
        return false;
    }

    public boolean remove(Long id, Long parentId, String type, Map<String, String> args) {
        String scriptId = args.get("scriptId");
        if (scriptId == null) {
            return false;
        }
        Script script = Script.findById(Long.parseLong(scriptId));
        if (script == null || !script.isInAccount(TMController.getConnectedUserAccount())) {
            return false;
        }
        Instance instance = Instance.findById(id);
        if (instance == null || !instance.isInAccount(TMController.getConnectedUserAccount())) {
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

    public static class ReleaseChildProducer implements ChildProducer {
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

    public List<UnitRole> getViewRoles() {
        return roles(UnitRole.TESTPREPVIEW);
    }

    public List<UnitRole> getCreateRoles() {
        return roles(UnitRole.TESTPREPCREATE);
    }

    public List<UnitRole> getUpdateRoles() {
        return roles(UnitRole.TESTPREPEDIT);
    }

    public List<UnitRole> getDeleteRoles() {
        return roles(UnitRole.TESTPREPDELETE);
    }
}
