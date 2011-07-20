package controllers.admin;

import com.sun.servicetag.UnauthorizedAccessException;
import controllers.TMController;
import notifiers.TMMails;
import play.mvc.results.Unauthorized;
import util.Logger;

/**
 * @author Nikola Milivojevic
 */
public class Admin extends TMController {
    public static void index() {
        if (TMController.isUserSuperAdmin()) {
            Users.index();
        } else if (TMController.isProjectSuperAdmin()) {
            Projects.index();
        } else if (TMController.isAccountAdmin()) {
            AccountSettings.index();
        } else {
            Logger.error(Logger.LogType.SECURITY, "Unauthorized user tried to access admin area. User id " + getConnectedUserId());
            forbidden();
        }
    }

    public static void sendFeedbackEmail(String name, String message, String location) {
        if (play.Play.id.equals("demo"))
            TMMails.feedbackEmail(name, message, location, request.headers.toString());
    }
}
