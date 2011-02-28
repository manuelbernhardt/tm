package controllers.admin;

import java.util.List;

import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.tabularasa.TableController;
import mappers.UserMapper;
import models.general.Auth;
import models.tm.User;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Users extends TMController {

    static {
        TableController.addObjectValueMapper(User.class, new UserMapper());
    }

    public static void index() {
        List<Auth> users = Auth.findAll();
        render(users);
    }

    public static void create(Auth user) {
        user.create();
        index();
    }

    public static void data(String tableId,
                            Integer iDisplayStart,
                            Integer iDisplayLength,
                            String sColumns,
                            String sEcho) {
        List<User> people = User.all().from(iDisplayStart == null ? 0 : iDisplayStart).fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = User.count();
        TableController.renderJSON(people,
                User.class,
                totalRecords,
                sColumns,
                sEcho);
    }


}
