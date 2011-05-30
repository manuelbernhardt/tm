package controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controllers.TMController;
import models.general.TreeRoleHolder;
import models.general.UnitRole;
import models.tm.AccountRole;
import models.tm.TMUser;
import play.libs.F;
import tree.JSTreeNode;
import tree.TreeDataHandler;
import tree.simple.ChildProducer;
import tree.simple.SimpleNode;
import util.Logger;

import static models.general.UnitRole.roles;



/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class AccountRolesAssignmentTreeDataHandler implements TreeDataHandler, TreeRoleHolder {

    public static final String ADMIN_ROLE = "adminRole";
    public static final String USER = "default";

    public String getName() {
        return "accountRolesAssignmentTree";
    }

    public List<? extends JSTreeNode> getChildren(Long parentId, String type, Map<String, String> args) {
        final ChildProducer rootProducer = new ChildProducer() {
            final AccountRoleChildProducer userAdminChildProducer = new AccountRoleChildProducer(AccountRole.USER_ADMIN);
            final AccountRoleChildProducer projectAdminChildProducer = new AccountRoleChildProducer(AccountRole.PROJECT_ADMIN);
            final AccountRoleChildProducer accountAdminChildProducer = new AccountRoleChildProducer(AccountRole.ACCOUNT_ADMIN);

            public List<JSTreeNode> produce(Long id) {
                SimpleNode userAdmin = new SimpleNode(AccountRole.USER_ADMIN.getId(), "User administration", ADMIN_ROLE, true, true, userAdminChildProducer);
                SimpleNode projectAdmin = new SimpleNode(AccountRole.PROJECT_ADMIN.getId(), "Project administration", ADMIN_ROLE, true, true, projectAdminChildProducer);
                SimpleNode accountAdmin = new SimpleNode(AccountRole.ACCOUNT_ADMIN.getId(), "Account administration", ADMIN_ROLE, true, true, accountAdminChildProducer);
                List<JSTreeNode> results = new ArrayList<JSTreeNode>();
                results.add(userAdmin);
                results.add(projectAdmin);
                results.add(accountAdmin);
                return results;
            }
        };
        if (parentId == -1) {
            List<JSTreeNode> results = new ArrayList<JSTreeNode>();
            results.add(new SimpleNode(1l, "Administrative roles", "root", true, true, rootProducer));
            return results;
        }
        return null;
    }

    public static class AccountRoleChildProducer implements ChildProducer {

        private final AccountRole accountRole;

        public AccountRoleChildProducer(AccountRole accountRole) {
            this.accountRole = accountRole;
        }

        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> result = new ArrayList<JSTreeNode>();
            List<TMUser> users = TMUser.listUsersInAccountRole(accountRole);
            for (TMUser u : users) {
                result.add(new SimpleNode(u.getId(), u.getFullName(), USER, false, false, null));
            }
            return result;
        }
    }

    public F.Tuple<Long, String> create(Long parentId, String parentType, Long position, String name, String type, Map<String, String> args) {
        return null;
    }

    public boolean rename(Long id, String name, String type) {
        return false;
    }

    public boolean copy(Long id, Long target, Long position) {
        if(target == null || target == -1) {
            return false;
        }
        AccountRole role = AccountRole.getById(target);
        TMUser u = TMUser.findById(id);
        if(TMUser.listUsersInAccountRole(role).contains(u)) {
            // do nothing
            return false;
        }
        if (!u.isInAccount(TMController.getConnectedUserAccount())) {
            Logger.error(Logger.LogType.SECURITY, "Trying to assign user to role in wrong account, target user id '%s', role type '%s'", id, role.name());
            return false;
        }
        for (String unitRole : role.getUnitRoles()) {
            u.accountRoles.add(unitRole);
        }
        u.save();
        return true;
    }

    public boolean move(Long id, String type, Long target, String targetType, Long position) {
        return false;
    }

    public boolean remove(Long id, Long parentId, String type, Map<String, String> args) {
        TMUser u = TMUser.findById(id);
        if (u == null) {
            Logger.error(Logger.LogType.TECHNICAL, "Could not find user '%s' in order to remove administration role '%s'", id, parentId);
            return false;
        }
        for (String unitRole : AccountRole.getById(parentId).getUnitRoles()) {
            u.accountRoles.remove(unitRole);
        }
        u.save();
        return true;
    }

    public List<UnitRole> getViewRoles() {
        return roles(UnitRole.ACCOUNTADMIN);
    }

    public List<UnitRole> getCreateRoles() {
        return roles(UnitRole.ACCOUNTADMIN);
    }

    public List<UnitRole> getUpdateRoles() {
        return roles(UnitRole.ACCOUNTADMIN);
    }

    public List<UnitRole> getDeleteRoles() {
        return roles(UnitRole.ACCOUNTADMIN);
    }
}
