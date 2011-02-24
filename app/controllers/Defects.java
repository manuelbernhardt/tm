package controllers;

import java.util.List;

import controllers.deadbolt.Deadbolt;
import models.Defect;
import play.mvc.Controller;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Defects extends Controller {

    public static void index() {
        List<Defect> defects = Defect.findAll();
        render(defects);
    }

    public static void create(Defect defect) {
        defect.create();
        index();
    }

}
