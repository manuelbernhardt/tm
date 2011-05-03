package controllers;

import java.util.List;
import java.util.Map;

import models.general.TreeRoleHolder;
import models.general.UnitRole;
import models.tm.Requirement;
import models.tm.RequirementFolder;
import tree.persistent.Node;
import tree.persistent.NodeType;

import static models.general.UnitRole.roles;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class RequirementTree extends TMTree implements TreeRoleHolder {

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

        // TODO security check roles

        if(type.getNodeClass().equals(RequirementFolder.class)) {
            RequirementFolder folder = new RequirementFolder(TMController.getActiveProject());
            folder.name = name;
            folder.project = TMController.getActiveProject();
            return folder;
        }

        if(type.getNodeClass().equals(Requirement.class)) {
            Requirement requirement = new Requirement(TMController.getActiveProject());
            requirement.name = name;
            requirement.project = TMController.getActiveProject();
            requirement.createdBy = TMController.getConnectedUser();
            return requirement;
        }
        
        return null;
    }

    public List<UnitRole> getViewRoles() {
        return roles(UnitRole.REQVIEW);
    }

    public List<UnitRole> getCreateRoles() {
        return roles(UnitRole.REQEDIT);
    }

    public List<UnitRole> getUpdateRoles() {
        return roles(UnitRole.REQEDIT);
    }

    public List<UnitRole> getDeleteRoles() {
        return roles(UnitRole.REQDELETE);
    }
}
