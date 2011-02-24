package controllers;

import controllers.deadbolt.Deadbolt;
import models.general.User;
import models.project.Project;
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

    /**
     * Gets the active project for the connected user, <code>null</code> if none is set
     * @return the active {@see Project}
     */
    public static Project getActiveProject() {
        // TODO freaking cache this or we have an extra query each time we create a project-related entity!
        Long id = Long.valueOf(session.get("project"));
        if(id != null) {
            return Project.findById(id);
        }
        return null;
    }
}
