package models.tm;

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

    public String[] getUnitRoles() {
        return unitRoles;
    }
}
