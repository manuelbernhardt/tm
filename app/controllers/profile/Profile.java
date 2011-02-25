package controllers.profile;

import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Profile extends TMController {

    public static void index() {
        render();
    }
}
