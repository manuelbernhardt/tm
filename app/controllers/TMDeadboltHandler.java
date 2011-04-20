package controllers;

import java.util.ArrayList;
import java.util.List;

import controllers.deadbolt.DeadboltHandler;
import controllers.deadbolt.ExternalizedRestrictionsAccessor;
import models.account.Auth;
import models.deadbolt.ExternalizedRestrictions;
import models.deadbolt.Role;
import models.deadbolt.RoleHolder;
import models.tm.User;
import play.mvc.Controller;

public class TMDeadboltHandler extends Controller implements DeadboltHandler {
    public void beforeRoleCheck() {

        if (!Security.isConnected()) {
            try {
                if (!session.contains("username")) {
                    flash.put("url", "GET".equals(request.method) ? request.url : "/");
                    Secure.login();
                }
            } catch (Throwable t) {
                // TODO handle me
                t.printStackTrace();
            }
        }
    }

    public RoleHolder getRoleHolder() {
        // TODO cache this!

        // we want to collect all roles (Auth roles + TM roles)
        CollectingRoleHolder crh = new CollectingRoleHolder();

        String userName = Secure.Security.connected();

        Auth a = Auth.find("byEmailAndActive", userName, true).first();
        crh.addRoles(a.getRoles());

        User u = User.find("byAuthentication", a).first();
        crh.addRoles(u.getRoles());

        return crh;
    }

    public void onAccessFailure(String controllerClassName) {
        forbidden();
    }

    public ExternalizedRestrictionsAccessor getExternalizedRestrictionsAccessor() {
        return new ExternalizedRestrictionsAccessor() {
            public ExternalizedRestrictions getExternalizedRestrictions(String name) {
                return null;
            }
        };
    }

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