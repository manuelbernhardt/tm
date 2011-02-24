package controllers;

import models.general.User;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        User u = User.find("byEmail", username).first();
        return u != null && u.connect(password);
    }

    static void onAuthenticated() {
        User u = User.find("byEmail", Security.connected()).first();

        // bind the basic session variables
        // we really want to keep the user session as thin as possible as it is sent at each request
        session.put("account", u.account.getId());
    }

}