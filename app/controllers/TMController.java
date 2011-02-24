package controllers;

import controllers.deadbolt.Deadbolt;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

/**
 * Parent controller for the TM application.
 * Sets common view template data such as username, visible menu elements, ...
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class TMController extends Controller {

    @Before
    public static void setConnectedUser() {
        if(Security.isConnected()) {
            User u = User.find("byEmail", Security.connected()).first();
            renderArgs.put("firstName", u.firstName);
            renderArgs.put("lastName", u.lastName);
        }
    }
}
