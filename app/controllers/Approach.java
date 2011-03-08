package controllers;

import controllers.deadbolt.Restrict;
import models.general.UnitRole;

public class Approach extends TMController {

    @Restrict(UnitRole.USER)
    public static void index() {
        render();
    }

}
