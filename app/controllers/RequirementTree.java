package controllers;

import java.util.Map;

import controllers.tree.AbstractTree;
import controllers.tree.NodeType;
import models.project.Requirement;
import models.project.RequirementFolder;
import models.tree.Node;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class RequirementTree extends AbstractTree {

    public static final String REQUIREMENT_TREE = "requirementTree";

    @Override
    public String getName() {
        return "requirementTree";
    }

    @Override
    protected NodeType[] getNodes() {
        return new NodeType[]{type(RequirementFolder.class, true), type(Requirement.class, false)};
    }

    @Override
    protected NodeType getDefaultType() {
        return getNodeType(Requirement.class);
    }

    @Override
    protected NodeType getRootType() {
        return getNodeType(RequirementFolder.class);
    }

    @Override
    protected Node createObjectNode(String name, NodeType type, Map<String, String> args) {

        if(type.getNodeClass().equals(RequirementFolder.class)) {
            RequirementFolder folder = new RequirementFolder();
            folder.name = name;
            folder.project = TMController.getActiveProject();
            return folder;
        }

        if(type.getNodeClass().equals(Requirement.class)) {
            Requirement requirement = new Requirement();
            requirement.name = name;
            requirement.project = TMController.getActiveProject();
            requirement.createdBy = TMController.getConnectedUser();
            return requirement;
        }
        
        return null;
    }
}
