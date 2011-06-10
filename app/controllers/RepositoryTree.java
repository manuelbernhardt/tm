package controllers;

import java.util.List;
import java.util.Map;

import models.general.TreeRoleHolder;
import models.general.UnitRole;
import models.tm.test.Script;
import models.tm.test.ScriptFolder;
import tree.persistent.Node;
import tree.persistent.NodeType;

import static models.general.UnitRole.roles;


/**
 * Test Script repository
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class RepositoryTree extends TMTree implements TreeRoleHolder {

    @Override
    protected NodeType[] getNodes() {
        return new NodeType[]{type(Script.class, false), type(ScriptFolder.class, true)};
    }

    @Override
    protected NodeType getDefaultType() {
        return getNodeType(Script.class);
    }

    @Override
    protected NodeType getRootType() {
        return getNodeType(ScriptFolder.class);
    }

    @Override
    protected String getRootName() {
        return "Test Scripts";
    }

    @Override
    protected Node createObjectNode(String name, NodeType type, Map<String, String> args) {

        if (type.getNodeClass().equals(ScriptFolder.class)) {
            ScriptFolder folder = new ScriptFolder(TMController.getActiveProject());
            folder.name = name;
            folder.project = TMController.getActiveProject();
            return folder;
        }

        if (type.getNodeClass().equals(Script.class)) {
            Script script = new Script(TMController.getActiveProject());
            script.name = name;
            script.project = TMController.getActiveProject();
            script.createdBy = TMController.getConnectedUser();
            return script;
        }

        return null;
    }

    public List<UnitRole> getViewRoles() {
        return roles(UnitRole.TESTREPOVIEW, UnitRole.TESTPREPVIEW, UnitRole.REQEDIT);
    }

    public List<UnitRole> getCreateRoles() {
        return roles(UnitRole.TESTREPOCREATE);
    }

    public List<UnitRole> getUpdateRoles() {
        return roles(UnitRole.TESTREPOEDIT);
    }

    public List<UnitRole> getDeleteRoles() {
        return roles(UnitRole.TESTREPODELETE);
    }
}
