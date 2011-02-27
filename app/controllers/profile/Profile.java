package controllers.profile;

import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import models.general.UnitRole;
import models.tm.User;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.With;

/**
 * Controller for user profiles, for the currently connected user.
 * 
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Profile extends TMController {

    @Restrict(UnitRole.USER)
    public static void index() {
        User user = getConnectedUser();
        render(user);
    }

    @Restrict(UnitRole.USER)
    public static void editUser(@Valid User user) {

        if(!user.getId().equals(getConnectedUser().getId())) {
            forbidden("You are not allowed to temper with data from another user!");
        }

        if(Validation.hasErrors()) {
            render("@index", user);
        }

        user.save();

        index();
    }


}
