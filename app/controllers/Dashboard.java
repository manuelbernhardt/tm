package controllers;

import controllers.deadbolt.Restrict;
import models.UnitRole;

public class Dashboard extends TMController {

    @Restrict(UnitRole.USER)
    public static void index() {
        render();
    }


}