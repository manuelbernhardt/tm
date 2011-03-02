package controllers.admin;

import controllers.CRUD;
import controllers.deadbolt.Deadbolt;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Projects extends CRUD {

    public static void index() {
        render();
    }

    public static void projectDetails() {

    }

    public static void roles() {

    }

    public static void users() {

    }

}
