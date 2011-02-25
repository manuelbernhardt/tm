package controllers;

import models.general.Auth;
import models.tm.User;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        Auth a = Auth.find("byEmail", username).first();
        return a != null && a.connect(password);
    }

    static void onAuthenticated() {
        User u = User.find("from User u where u.authentication.email = ?", Security.connected()).first();

        // bind the basic session variables
        // we really want to keep the user session as thin as possible as it is sent at each request
        session.put("account", u.authentication.account.getId());
        if(u.activeProject != null) {
            session.put("project", u.activeProject);
        }
    }

}