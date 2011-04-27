package models.tm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.general.UnitRole;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public enum AccountRole {

    USER_ADMIN(1l, UnitRole.USERCREATE, UnitRole.USEREDIT, UnitRole.USERDELETE),
    PROJECT_ADMIN(2l, UnitRole.PROJECTCREATE, UnitRole.PROJECTEDIT, UnitRole.PROJECTDELETE),
    ACCOUNT_ADMIN(3l, UnitRole.ACCOUNTADMIN);

    private final Long id;
    private final String[] unitRoles;

    AccountRole(Long id, String... unitRoles) {
        this.id = id;
        this.unitRoles = unitRoles;
    }

    public List<String> getUnitRoles() {
        return Arrays.asList(unitRoles);
    }

    public Long getId() {
        return this.id;
    }

    public static AccountRole getById(Long id) {
        for(AccountRole r : values()) {
            if(r.getId().equals(id)) {
                return r;
            }
        }
        throw new RuntimeException("No admin role with id " + id);
    }

    public static List<AccountRole> getAccountRoles(List<String> unitRoles) {
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
