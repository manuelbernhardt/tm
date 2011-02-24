package controllers;

import java.util.List;

import models.project.Defect;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Defects extends TMController {

    public static void index() {
        List<Defect> defects = Defect.findAll();
        render(defects);
    }

    public static void create(Defect defect) {
        defect.create();
        index();
    }

}
