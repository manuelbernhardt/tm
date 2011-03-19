package controllers;

import java.util.Map;

import controllers.tree.AbstractTree;
import controllers.tree.NodeType;
import models.project.TestScript;
import models.project.TestScriptFolder;
import models.tree.Node;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class RepositoryTree extends AbstractTree {

    public static final String REPOSITORY_TREE = "repositoryTree";

    @Override
    public String getName() {
        return "repositoryTree";
    }

    @Override
    protected NodeType[] getNodes() {
        return new NodeType[] {type(TestScript.class, false), type(TestScriptFolder.class, true)};
    }

    @Override
    protected NodeType getDefaultType() {
        return getNodeType(TestScript.class);
    }

    @Override
    protected NodeType getRootType() {
        return getNodeType(TestScriptFolder.class);
    }

    @Override
    protected Node createObjectNode(String name, NodeType type, Map<String, String> args) {

        if(type.getNodeClass().equals(TestScriptFolder.class)) {
            TestScriptFolder folder = new TestScriptFolder();
            folder.name = name;
            folder.project = TMController.getActiveProject();
            return folder;
        }

        if(type.getNodeClass().equals(TestScript.class)) {
            TestScript testScript = new TestScript();
            testScript.name = name;
            testScript.project = TMController.getActiveProject();
            testScript.createdBy = TMController.getConnectedUser();
            return testScript;
        }

        return null;
    }

}
