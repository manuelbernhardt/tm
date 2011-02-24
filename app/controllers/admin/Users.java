package controllers.admin;

import java.util.List;

import controllers.deadbolt.Deadbolt;
import models.User;
import play.mvc.Controller;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Users extends Controller {

    public static void index() {
        List<User> users = User.findAll();
        render(users);
    }

    public static void create(User user) {
        user.create();
        index();
    }

}
