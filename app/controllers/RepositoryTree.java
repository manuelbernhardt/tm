package controllers;

import java.util.Map;

import models.tm.test.Script;
import models.tm.test.ScriptFolder;
import tree.persistent.Node;
import tree.persistent.NodeType;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class RepositoryTree extends TMTree {

    @Override
    protected NodeType[] getNodes() {
        return new NodeType[] {type(Script.class, false), type(ScriptFolder.class, true)};
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
    protected Node createObjectNode(String name, NodeType type, Map<String, String> args) {

        if(type.getNodeClass().equals(ScriptFolder.class)) {
            ScriptFolder folder = new ScriptFolder();
            folder.name = name;
            folder.project = TMController.getActiveProject();
            return folder;
        }

        if(type.getNodeClass().equals(Script.class)) {
            Script script = new Script();
            script.name = name;
            script.project = TMController.getActiveProject();
            script.createdBy = TMController.getConnectedUser();
            return script;
        }

        return null;
    }

}
