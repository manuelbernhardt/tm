package controllers;

import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import play.mvc.Controller;
import play.mvc.With;

@With(Deadbolt.class)
public class Approach extends Controller {

    @Restrict("admin")
    public static void index() {
        render();
    }

}
