package models.tm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.general.UnitRole;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public enum AccountRole {

    ACCOUNT_ADMIN(UnitRole.ACCOUNTADMIN),
    PROJECT_ADMIN(UnitRole.PROJECTCREATE, UnitRole.PROJECTEDIT, UnitRole.PROJECTDELETE),
    USER_ADMIN(UnitRole.USERCREATE, UnitRole.USEREDIT, UnitRole.USERDELETE);

    private String[] unitRoles;

    AccountRole(String... unitRoles) {
        this.unitRoles = unitRoles;
    }

    public List<String> getUnitRoles() {
        return Arrays.asList(unitRoles);
    }

    public List<AccountRole> getAccountRole(List<String> unitRoles) {
        List<AccountRole> roles = new ArrayList<AccountRole>();
        boolean hasProjectCreate, hasProjectEdit, hasProjectDelete;
        boolean hasUserCreate, hasUserEdit, hasUserDelete;
        if (unitRoles.contains(UnitRole.ACCOUNTADMIN)) {
            roles.add(ACCOUNT_ADMIN);
        }
        hasProjectCreate = unitRoles.contains(UnitRole.PROJECTCREATE);
        hasProjectEdit = unitRoles.contains(UnitRole.PROJECTEDIT);
        hasProjectDelete = unitRoles.contains(UnitRole.PROJECTDELETE);
        if (hasProjectCreate && hasProjectEdit && hasProjectDelete) {
            roles.add(PROJECT_ADMIN);
        }
        hasUserCreate = unitRoles.contains(UnitRole.USERCREATE);
        hasUserEdit = unitRoles.contains(UnitRole.USEREDIT);
        hasUserDelete = unitRoles.contains(UnitRole.USERDELETE);
        if (hasUserCreate && hasUserEdit && hasUserDelete) {
            roles.add(USER_ADMIN);
        }
        return roles;
    }
}
