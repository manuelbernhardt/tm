package controllers;

import play.Play;
import play.mvc.Controller;
import util.TestDataLoader;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Dev extends Controller {

    public static void index() {
      redirect("Dashboard.index");
    }

    public static void reloadData() {
        if (Play.mode == Play.Mode.DEV) {
            new TestDataLoader();
            redirect(request.controller + ".index");
        }
    }
}
