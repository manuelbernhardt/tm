package controllers.admin;

import controllers.CRUD;
import controllers.deadbolt.Deadbolt;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Accounts extends CRUD {
}
