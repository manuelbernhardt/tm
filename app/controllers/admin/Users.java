package controllers.admin;

import java.util.List;

import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import models.general.Auth;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Users extends TMController {

    public static void index() {
        List<Auth> users = Auth.findAll();
        render(users);
    }

    public static void create(Auth user) {
        user.create();
        index();
    }

}
