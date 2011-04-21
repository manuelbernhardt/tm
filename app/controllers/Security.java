package controllers;

import models.account.Auth;
import models.tm.User;
import util.Logger;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        Auth a = Auth.find("byEmailAndActive", username, true).first();
        boolean success = a != null && a.connect(password);
        if(success) {
            Logger.info(Logger.LogType.AUTHENTICATION, "Authenticated user '%s'", username);
        } else {
            Logger.warn(Logger.LogType.AUTHENTICATION, "Unsuccessful authentication attempt by user '%s'", username);
        }
        return success;
    }

    static void onAuthenticated() {
        User u = User.find("from User u where u.authentication.email = ? and u.authentication.active = true", Security.connected()).first();

        // bind the basic session variables
        // we really want to keep the user session as thin as possible as it is sent at each request
        session.put("account", u.authentication.account.getId());
        if(u.activeProject != null) {
            session.put("activeProject", u.activeProject.getId());
        }
    }

}