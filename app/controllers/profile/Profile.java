package controllers.profile;

import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import models.tm.User;
import play.mvc.With;

/**
 * TODO make sure the edited user == the authenticated user
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Profile extends TMController {

    public static void index() {
        User user = getConnectedUser();
        render(user);
    }

    public static void editUser(User user) {
        // TODO validation
        user.save();
    }


}
