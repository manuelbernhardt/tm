package models.general;

import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface TreeRoleHolder {

    /**
     * Gives the list of {@link UnitRole}-s allowed to view the tree nodes
     */
    public List<UnitRole> getViewRoles();
    
    /**
     * Gives the list of {@link UnitRole}-s allowed to create tree nodes
     */
    public List<UnitRole> getCreateRoles();

    /**
     * Gives the list of {@link UnitRole}-s allowed to update tree nodes
     */
    public List<UnitRole> getUpdateRoles();

    /**
     * Gives the list of {@link UnitRole}-s allowed to delete tree nodes
     */
    public List<UnitRole> getDeleteRoles();
}
