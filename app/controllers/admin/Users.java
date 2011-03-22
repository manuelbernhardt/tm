package controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.tabularasa.TableController;
import models.general.Auth;
import models.general.UnitRole;
import models.project.Project;
import models.project.ProjectCategory;
import models.project.Role;
import models.tm.User;
import play.data.validation.Validation;
import play.db.jpa.GenericModel;
import play.mvc.Router;
import play.mvc.With;

/**
 * TODO security checks
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
@Restrict(UnitRole.ADMIN)
public class Users extends TMController {

    @Restrict(UnitRole.ADMIN)
    public static void index() {
        List<Auth> users = Auth.findAll();
        // this is how we'd select a user by default
//        Long selectedUser = 2l;
//        render(users, selectedUser);
        render(users);
    }

    @Restrict(UnitRole.ADMIN)
    public static void userDetails(Long userId) {
        Router.ActionDefinition action = Router.reverse("admin.Users.edit");
        User user = null;
        if (userId != null) {
            user = User.findById(userId);
            checkInAccount(user);
        }
        render("/general/userProfile.html", action, user);
    }

    @Restrict(UnitRole.ADMIN)
    public static void projects(Long userId) {
        User user = null;
        if (userId != null) {
            user = User.findById(userId);
            if (user == null) {
                notFound();
            } else {
                checkInAccount(user);
                List<ProjectCategory> projectCategories = ProjectCategory.findByAccount(user.authentication.account.getId());
                render("/admin/Users/projects.html", user, projectCategories);
            }
        } else {
            render("/admin/Users/projects.html");
        }
    }

    @Restrict(UnitRole.ADMIN)
    public static void account(Long userId) {
        User user = null;
        if (userId != null) {
            user = User.findById(userId);
        }
        render("/admin/Users/account.html", user);
    }


    @Restrict(UnitRole.ADMIN)
    public static void create(User user) {
        if (Validation.hasErrors()) {
            // TODO add some flash message
            render("@index", user);
        }

        user.authentication.account = getConnectedUser().authentication.account;
        user.create();
        index();
    }

    @Restrict(UnitRole.ADMIN)
    public static void edit(User user) {
        if (Validation.hasErrors()) {
            // TODO test if this works
            // display a message at least with the validation errors?
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
            String sLike = "%" + sSearch + "%";
            query = User.find("from User u where u.authentication.firstName like ? or u.authentication.lastName like ?", sLike, sLike);
        } else {
            query = User.all().from(iDisplayStart == null ? 0 : iDisplayStart);
        }
        List<User> people = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = User.count();
        TableController.renderJSON(people, User.class, totalRecords, sColumns, sEcho);
    }

    public static void projectOptions(Long categoryId, Long accountId) {
        if (categoryId == null || accountId == null) {
            error();
        } else {
            if (!accountId.equals(getUserAccount().getId())) {
                unauthorized();
            }
            List<Project> projects;
            if (categoryId == -1l) {
                // all non-assigned projects
                projects = Project.find("from Project p where p.projectCategory is null and p.account.id = ?", accountId).<Project>fetch();
            } else {
                ProjectCategory pc = ProjectCategory.findById(categoryId);
                checkInAccount(pc);
                projects = pc.getProjects();
            }
            Map<Long, String> m = new HashMap<Long, String>();
            for (Project p : projects) {
                m.put(p.getId(), p.name);
            }
            renderJSON(m);
        }
    }

    public static void rolesOptions(Long projectId) {
        if (projectId == null || projectId == null) {
            error();
        } else {
            Project p = Project.findById(projectId);
            if (p == null) {
                notFound();
            }
            checkInAccount(p);
            List<Role> roles = Role.find("from Role r where r.project.id = ?", projectId).<Role>fetch();
            Map<Long, String> m = new HashMap<Long, String>();
            for (Role r : roles) {
                m.put(r.getId(), r.name);
            }
            renderJSON(m);
        }
    }

}
