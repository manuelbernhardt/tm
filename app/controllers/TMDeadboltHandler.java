package controllers;

import controllers.deadbolt.DeadboltHandler;
import controllers.deadbolt.ExternalizedRestrictionsAccessor;
import models.general.User;
import models.deadbolt.ExternalizedRestrictions;
import models.deadbolt.RoleHolder;
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
        String userName = Secure.Security.connected();
        return User.find("byEmail", userName).first();
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
}