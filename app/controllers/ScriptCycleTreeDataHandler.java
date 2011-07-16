package controllers;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static models.general.UnitRole.roles;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ScriptCycleTreeDataHandler implements TreeDataHandler, TreeRoleHolder {

    public static final String INSTANCE = "default";
    public static final String TEST_CYCLE = "testCycle";
    public static final String RELEASE = "release";
    public static final String ROOT = "root";

    public String getName() {
        return "scriptCycleTree";
    }

    public List<? extends JSTreeNode> getChildren(Long parentId, String type, Map<String, String> args) {
        String sId = args.get("scriptId");
        if (sId.equals("-1")) {
            return null;
        }
        final Script script = getScript(sId);
        final CycleChildProducer cycleChildProducer = new CycleChildProducer(script);

        if (parentId == -1) {

            final Map<Release, List<TestCycle>> releases = new HashMap<Release, List<TestCycle>>();

            // releases and cycles
            List<Release> releaseList = Release.find("from Release r").fetch();
            for(Release r : releaseList) {
                List<TestCycle> testCycles = r.getTestCycles();
                if(testCycles.size() > 0) {
                    releases.put(r, testCycles);
                }
            }

            ChildProducer releaseChildProducer = new ReleaseChildProducer(releases, cycleChildProducer);

            List<JSTreeNode> result = new ArrayList<JSTreeNode>();
            for (Release r : releases.keySet()) {
                SimpleNode rNode = new SimpleNode(r.getId(), r.name, RELEASE, true, true, releaseChildProducer);
                result.add(rNode);
            }

            // also display unattached instances
            List<Instance> unattachedInstances = Instance.find("from Instance i where i.script = ? and i.testCycle is null", script).fetch();
            for (Instance i : unattachedInstances) {
                result.add(new SimpleNode(i.getId(), i.name, INSTANCE, false, false, null));
            }

            Collections.sort(result);
            return result;
        } else {
            return null;
        }
    }

    private Script getScript(String sId) {
        if (sId == null) {
            return null;
        }
        Script ts = Lookups.getScript(Long.parseLong(sId));
        if (ts == null) {
            return null;
        }
        if (!ts.isInAccount(TMController.getConnectedUserAccount())) {
            return null;
        }
        return ts;
    }

    public F.Tuple<Long, String> create(Long parentId, String parentType, Long position, String name, String type, Map<String, String> args) {

        if (type.equals(ScriptCycleTreeDataHandler.INSTANCE)) {
            String cycleNodeId = args.get("cycleNodeId");
            String scriptId = args.get("scriptId");
            if (cycleNodeId == null || scriptId == null) {
                return null;
            }
            TestCycle cycle = Lookups.getCycle(Long.parseLong(cycleNodeId));
            Script script = Lookups.getScript(Long.parseLong(scriptId));
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
            Instance instance = Lookups.getInstance(id);
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

        if (!type.equals(INSTANCE)) {
            return false;
        } else {
            if (targetType.equals(RELEASE)) {
                return false;
            }
            if (targetType.equals(TEST_CYCLE)) {
                TestCycle cycle = Lookups.getCycle(target);
                if (target == null) {
                    Logger.error(Logger.LogType.TECHNICAL, "Could not retrieve cycle with ID %s", target);
                    return false;
                }
                Instance instance = Lookups.getInstance(id);
                if (instance == null) {
                    Logger.error(Logger.LogType.TECHNICAL, "Could not retrieve instance with ID %s", id);
                    return false;
                }
                instance.testCycle = cycle;
                try {
                    instance.save();
                    return true;
                } catch (Throwable t) {
                    Logger.error(Logger.LogType.DB, t, "Error while saving instance %s", id);
                    return false;
                }
            }
            if (targetType.equals(ROOT)) {
                Instance instance = Lookups.getInstance(id);
                if (instance == null) {
                    Logger.error(Logger.LogType.TECHNICAL, "Could not retrieve instance with ID %s", id);
                    return false;
                }
                instance.testCycle = null;
                try {
                    instance.save();
                    return true;
                } catch (Throwable t) {
                    Logger.error(Logger.LogType.DB, t, "Error while saving instance %s", id);
                    return false;
                }
            }
        }
        return false;
    }

    public boolean remove(Long id, Long parentId, String type, Map<String, String> args) {
        String scriptId = args.get("scriptId");
        if (scriptId == null) {
            return false;
        }
        Script script = Lookups.getScript(Long.parseLong(scriptId));
        if (script == null || !script.isInAccount(TMController.getConnectedUserAccount())) {
            return false;
        }
        Instance instance = Lookups.getInstance(id);
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
            Collections.sort(children);
            return children;
        }
    }

    public static class CycleChildProducer implements ChildProducer {
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
            Collections.sort(children);
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
