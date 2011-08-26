package controllers.profile;

import java.util.List;

import controllers.Lookups;
import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import models.deadbolt.Role;
import models.tm.AccountRole;
import models.tm.Project;
import models.tm.TMUser;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Router;
import play.mvc.With;

/**
 * Controller for user profiles, for the currently connected user.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Profile extends TMController {

    public static void index() {
        TMUser user = getConnectedUser();
        Router.ActionDefinition action = Router.reverse("profile.Profile.edit");
        List<AccountRole> accountRoles = AccountRole.getAccountRoles(user.accountRoles);

        boolean userAdmin = false;
        boolean projectAdmin = false;
        boolean accountAdmin = false;

        if (accountRoles.contains(AccountRole.USER_ADMIN)) {
            userAdmin = true;
        }
        if (accountRoles.contains(AccountRole.PROJECT_ADMIN)) {
            projectAdmin = true;
        }
        if (accountRoles.contains(AccountRole.ACCOUNT_ADMIN)) {
            accountAdmin = true;
        }
        render("/profile/Profile/index.html", action, user, userAdmin, projectAdmin, accountAdmin);
    }

    public static void edit(@Valid TMUser user) {

        if (!user.getId().equals(getConnectedUser().getId())) {
            forbidden("You are not allowed to temper with data from another user!");
        }

        if (Validation.hasErrors()) {
            render("@index", user);
        }

        user.save();

        index();
    }


}
