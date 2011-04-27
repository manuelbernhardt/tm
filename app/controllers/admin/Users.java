package controllers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.Lookups;
import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.tabularasa.TableController;
import models.account.User;
import models.general.UnitRole;
import models.tm.AccountRole;
import models.tm.Project;
import models.tm.ProjectCategory;
import models.tm.Role;
import models.tm.TMUser;
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
@Restrict(UnitRole.ACCOUNTADMIN)
public class Users extends TMController {

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void index() {
        List<User> users = User.find("active = true").<User>fetch();
        render(users);
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void userDetails(Long userId) {
        Router.ActionDefinition action = Router.reverse("admin.Users.edit");
        TMUser user = null;
        if (userId != null) {
            user = TMUser.findById(userId);
            checkInAccount(user);
        }
        render("/general/userProfile.html", action, user);
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void projects(Long userId) {
        TMUser user = null;
        if (userId != null) {
            user = TMUser.findById(userId);
            if (user == null) {
                notFound();
            } else {
                checkInAccount(user);
                List<ProjectCategory> projectCategories = ProjectCategory.findByAccount(user.user.account.getId());
                render("/admin/Users/projects.html", user, projectCategories);
            }
        } else {
            render("/admin/Users/projects.html");
        }
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void account(Long userId) {
        TMUser user = null;
        if (userId != null) {
            user = TMUser.findById(userId);
        }
        boolean userAdmin = false, projectAdmin = false, accountAdmin = false;
        if (user != null) {
            List<AccountRole> accountRoles = AccountRole.getAccountRoles(user.accountRoles);
            userAdmin = accountRoles.contains(AccountRole.USER_ADMIN);
            projectAdmin = accountRoles.contains(AccountRole.PROJECT_ADMIN);
            accountAdmin = accountRoles.contains(AccountRole.ACCOUNT_ADMIN);
        }
        render("/admin/Users/account.html", user, userAdmin, projectAdmin, accountAdmin);
    }


    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void create(TMUser user) {
        if (Validation.hasErrors()) {
            // TODO add some flash message
            render("@index", user);
        }

        user.user.account = getConnectedUser().user.account;
        user.create();
        index();
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void edit(TMUser user) {
        if (Validation.hasErrors()) {
            // TODO test if this works
            // display a message at least with the validation errors?
            Long selectedUser = user.getId();
            render("@index", user, selectedUser);
        }
        user.save();
        ok();
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void updateAccountRoles(Long userId, boolean userAdmin, boolean projectAdmin, boolean accountAdmin) {
        List<String> accountRoles = new ArrayList<String>();
        if(userAdmin) {
            accountRoles.addAll(AccountRole.USER_ADMIN.getUnitRoles());
        }
        if(projectAdmin) {
            accountRoles.addAll(AccountRole.PROJECT_ADMIN.getUnitRoles());
        }
        if(accountAdmin) {
            accountRoles.addAll(AccountRole.ACCOUNT_ADMIN.getUnitRoles());
        }
        TMUser user = Lookups.getUser(userId);
        if(user != null) {
            user.accountRoles.clear();
            user.accountRoles.addAll(accountRoles);
            user.save();
        } else {
            notFound();
        }
        ok();
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void removeUser(Long userId) {
        User u = User.findById(userId);
        if (u != null) {
            u.active = false;
            // TODO actually also retrieve the TMUser and clear all roles!
            u.save();
        } else {
            // TODO logging
            notFound();
        }
        ok();
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void data(String tableId,
                            Integer iDisplayStart,
                            Integer iDisplayLength,
                            String sColumns,
                            String sEcho,
                            String sSearch) {
        GenericModel.JPAQuery query = null;
        if (sSearch != null && sSearch.length() > 0) {
            String sLike = "%" + sSearch + "%";
            query = TMUser.find("from TMUser u where u.user.active = true and u.user.firstName like ? or u.user.lastName like ?", sLike, sLike);
        } else {
            query = TMUser.find("user.active = true").from(iDisplayStart == null ? 0 : iDisplayStart);
        }
        List<TMUser> people = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = TMUser.count();
        TableController.renderJSON(people, TMUser.class, totalRecords, sColumns, sEcho);
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
