package controllers.admin;

import java.util.List;

import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.tabularasa.TableController;
import models.general.Auth;
import models.general.UnitRole;
import models.tm.User;
import play.data.validation.Validation;
import play.db.jpa.GenericModel;
import play.mvc.Router;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
@Restrict(UnitRole.ADMIN)
public class Users extends TMController {

    @Restrict(UnitRole.ADMIN)
    public static void index() {
        List<Auth> users = Auth.findAll();
        Long selectedUser = 2l;
        render(users, selectedUser);
    }

    public static void userDetails(Long userId) {
        Router.ActionDefinition action = Router.reverse("admin.Users.edit");
        User user = null;
        if (userId != null) {
            user = User.findById(userId);
        }
        render("/general/userProfile.html", action, user);
    }

    public static void projects(Long userId) {
        User user = null;
        if (userId != null) {
            user = User.findById(userId);
        }
        render("/admin/Users/projects.html", user);
    }

    public static void account(Long userId) {
        User user = null;
        if (userId != null) {
            user = User.findById(userId);
        }
        render("/admin/Users/account.html", user);
    }


    @Restrict(UnitRole.ADMIN)
    public static void create(User user) {

        // TODO validation
        user.authentication.account = getConnectedUser().authentication.account;
        user.create();
        index();
    }

    @Restrict(UnitRole.ADMIN)
    public static void edit(User user) {

        if(Validation.hasErrors()) {
            Long selectedUser = user.getId();
            render("@index", user, selectedUser);
        }

        user.save();
        ok();
    }

    @Restrict(UnitRole.ADMIN)
    public static void data(String tableId,
                            Integer iDisplayStart,
                            Integer iDisplayLength,
                            String sColumns,
                            String sEcho,
                            String sSearch) {
        GenericModel.JPAQuery query = null;
        if (sSearch != null && sSearch.length() > 0) {
            // TODO look into criteria API to make this in a more convenient fashion
            String sLike = "%" + sSearch + "%";
            query = User.find("from User u where u.authentication.firstName like ? or u.authentication.lastName like ?", sLike, sLike);
        } else {
            query = User.all().from(iDisplayStart == null ? 0 : iDisplayStart);
        }
        List<User> people = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = User.count();
        TableController.renderJSON(people, User.class, totalRecords, sColumns, sEcho);
    }


}
