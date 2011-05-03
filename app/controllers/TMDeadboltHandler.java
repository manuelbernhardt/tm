package controllers;

import java.util.ArrayList;
import java.util.List;

import controllers.deadbolt.DeadboltHandler;
import controllers.deadbolt.ExternalizedRestrictionsAccessor;
import controllers.deadbolt.RestrictedResourcesHandler;
import models.account.User;
import models.deadbolt.AccessResult;
import models.deadbolt.ExternalizedRestrictions;
import models.deadbolt.Role;
import models.deadbolt.RoleHolder;
import models.tm.Project;
import models.tm.TMUser;
import play.mvc.Controller;
import play.mvc.Util;
import util.Logger;

public class TMDeadboltHandler extends Controller implements DeadboltHandler {

    public void beforeRoleCheck() {

        if (!Security.isConnected()) {
            try {
                if (!session.contains("username")) {
                    flash.put("url", "GET".equals(request.method) ? request.url : "/");
                    Secure.login();
                }
            } catch (Throwable t) {
                Logger.error(Logger.LogType.TECHNICAL, t, "Error before role check while calling Secure.login");
            }
        }
    }

    public RoleHolder getRoleHolder() {
        return getUserRoles(null);
    }

    /**
     * Gets the {@link models.general.UnitRole}-s of the connected user, given a project.
     * If no parameter is passed, only account-level roles are retrieved.
     *
     * @return a {@link RoleHolder} containing all unit roles (account and project) of the authenticated user
     */
    @Util
    public static RoleHolder getUserRoles(Project project) {
        // TODO cache this!

        CollectingRoleHolder crh = new CollectingRoleHolder();

        String userName = Secure.Security.connected();

        User a = User.find("byEmail", userName).first();
        crh.addRoles(a.getRoles());

        // TODO FIXME this needs to be by project (active project)
        TMUser u = TMUser.find("byUser", a).first();
        crh.addRoles(u.getRoles());

        return crh;
    }

    public void onAccessFailure(String controllerClassName) {
        Logger.warn(Logger.LogType.SECURITY, "Access failure for controller '%s', user is '%s'", controllerClassName, Security.connected());
        forbidden();
    }

    public ExternalizedRestrictionsAccessor getExternalizedRestrictionsAccessor() {
        return new ExternalizedRestrictionsAccessor() {
            public ExternalizedRestrictions getExternalizedRestrictions(String name) {
                return null;
            }
        };
    }

    public RestrictedResourcesHandler getRestrictedResourcesHandler() {
        return new RestrictedResourcesHandler() {
            public AccessResult checkAccess(List<String> resourceNames) {
                return null;
            }
        };
    }

    /**
     * A utility class to combine Roles from several locations (e.g. account roles and unit roles)
     */
    public static class CollectingRoleHolder implements RoleHolder {
        List<Role> roles = new ArrayList<Role>();

        public void addRoles(List<? extends Role> r) {
            roles.addAll(r);
        }

        public List<? extends Role> getRoles() {
            return roles;
        }
    }
}