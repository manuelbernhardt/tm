package controllers.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import models.tm.ProjectRole;
import models.tm.TMUser;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.db.jpa.GenericModel;
import play.mvc.With;
import play.templates.JavaExtensions;
import util.Logger;

/**
 * TODO security checks
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Users extends TMController {

    @Restrict(UnitRole.USEREDIT)
    public static void index() {
        List<User> users = User.findAll();
        render(users);
    }

    @Restrict(UnitRole.USEREDIT)
    public static void userDetailsData(Long baseObjectId, String[] fields) {
        Object base = Lookups.getUser(baseObjectId);
        renderFields(base, fields);
    }

    @Restrict(UnitRole.USERCREATE)
    public static void create(TMUser user) {
        if (Validation.hasErrors()) {
            // TODO add some flash message
            render("@index", user);
        }

        user.user.account = getConnectedUser().user.account;
        user.account = getConnectedUser().user.account;
        user.create();
        index();
    }

    @Restrict(UnitRole.USEREDIT)
    public static void edit(@Valid TMUser tmUser) {
        if (Validation.hasErrors()) {
            error();
        }
        tmUser.save();
        ok();
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void getAccountRoles(Long userId) {
        TMUser user = null;
        if (userId != null) {
            user = Lookups.getUser(userId);
        }
        String[] accounts = new String[3];
        Gson gson = new Gson();

        boolean userAdmin = false, projectAdmin = false, accountAdmin = false;
        if (user == null) {
            error("Could not find user " + userId);
        }

        List<AccountRole> accountRoles = AccountRole.getAccountRoles(user.accountRoles);

        if (accountRoles.contains(AccountRole.USER_ADMIN)) {
            accounts[0] = "userAdmin";
        }
        if (accountRoles.contains(AccountRole.PROJECT_ADMIN)) {
            accounts[1] = "projectAdmin";
        }
        if (accountRoles.contains(AccountRole.ACCOUNT_ADMIN)) {
            accounts[2] = "accountAdmin";
        }
        renderJSON(gson.toJson(accounts));
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void updateAccountRoles(Long userId, String[] roles) {
        List<String> accountRoles = new ArrayList<String>();
        if (roles != null) {
            for (String role : roles) {
                if (role.equals("userAdmin")) {
                    accountRoles.addAll(AccountRole.USER_ADMIN.getUnitRoles());
                }
                if (role.equals("projectAdmin")) {
                    accountRoles.addAll(AccountRole.PROJECT_ADMIN.getUnitRoles());
                }
                if (role.equals("accountAdmin")) {
                    accountRoles.addAll(AccountRole.ACCOUNT_ADMIN.getUnitRoles());
                }
            }
        }
        TMUser user = Lookups.getUser(userId);
        if (user != null) {
            Logger.info(Logger.LogType.ADMIN, "Updating roles of user '%s' to contain '%s'", user.user.getDebugString(), JavaExtensions.join(accountRoles, ", "));
            user.accountRoles.clear();
            user.accountRoles.addAll(accountRoles);
            user.save();
        } else {
            Logger.fatal(Logger.LogType.SECURITY, "Attempting to update account roles of unknown user '%s'", userId);
            notFound();
        }
        ok();
    }

    @Restrict(UnitRole.USERDELETE)
    public static void removeUser(Long userId) {
        User u = Lookups.getAccountUser(userId);
        if (u != null) {
            Logger.info(Logger.LogType.ADMIN, "Deactivating user '%s'", u.getDebugString());

            TMUser tmUser = TMUser.find("from TMUser tmu where tmu.user = ?", u).first();

            u.active = false;
            u.save();

            tmUser.accountRoles.clear();
            tmUser.projectRoles.clear();
            tmUser.save();
        } else {
            Logger.fatal(Logger.LogType.SECURITY, "Attempting to deactivate unknown user '%s'", userId);
            notFound();
        }
        ok();
    }

    @Restrict(UnitRole.USEREDIT)
    public static void data(String tableId,
                            Integer iDisplayStart,
                            Integer iDisplayLength,
                            String sColumns,
                            String sEcho,
                            String sSearch) {
        GenericModel.JPAQuery query = null;
        if (sSearch != null && sSearch.length() > 0) {
            String sLike = "%" + sSearch + "%";
            query = TMUser.find("from TMUser u where u.user.firstName like ? or u.user.lastName like ? order by u.user.lastName", sLike, sLike);
        } else {
            query = TMUser.find("from TMUser u order by u.user.lastName").from(iDisplayStart == null ? 0 : iDisplayStart);
        }
        List<TMUser> people = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = TMUser.count();
        TableController.renderJSON(people, TMUser.class, totalRecords, sColumns, sEcho);
    }

    @Restrict(UnitRole.USEREDIT)
    public static void rolesData() {
        renderJSON(computeRolesData(null, null, null));
    }

    @Restrict(UnitRole.USEREDIT)
    public static void rolesTreeData(Long categoryId, Long projectId, Long roleId) {

        List<ProjectRole> projectRoles = new ArrayList<ProjectRole>();
        List<Project> projects = new ArrayList<Project>();
        List<ProjectCategory> projectCategories = new ArrayList<ProjectCategory>();

        if (categoryId != null && projectId == null && roleId == null) {
            projectCategories.add(Lookups.getProjectCategory(categoryId));
            renderJSON(computeRolesData(projectCategories, null, null));
        } else if (categoryId != null && projectId != null && roleId == null) {
            projectCategories.add(Lookups.getProjectCategory(categoryId));
            projects.add(Lookups.getProject(projectId));
            renderJSON(computeRolesData(projectCategories, projects, null));
        } else if (categoryId != null && projectId != null && roleId != null) {
            // we're stuffed
            projectCategories.add(Lookups.getProjectCategory(categoryId));
            projects.add(Lookups.getProject(projectId));
            projectRoles.add(Lookups.getRole(roleId));
            renderJSON(computeRolesData(projectCategories, projects, projectRoles));
        } else {
            Logger.error(Logger.LogType.TECHNICAL, "Incorrect roles pre-selection: categoryId %s, projectId %s, roleId %s", categoryId, projectId, roleId);
            error("Invalid role pre-selection");
        }
    }

    private static String computeRolesData(@Nullable List<ProjectCategory> projectCategories, @Nullable List<Project> projects, @Nullable List<ProjectRole> projectRoles) {
        JsonObject res = new JsonObject();
        JsonArray categories = new JsonArray();
        res.add("categories", categories);
        if (projectCategories == null) {
            projectCategories = ProjectCategory.findAll();
        }
        for (ProjectCategory pc : projectCategories) {
            JsonObject category = new JsonObject();
            categories.add(category);
            category.addProperty("name", pc.name);
            category.addProperty("id", pc.getId());
            JsonArray projectsArray = new JsonArray();
            category.add("projects", projectsArray);
            if (projects == null) {
                projects = pc.getProjects();
            }
            for (Project p : projects) {
                JsonObject project = new JsonObject();
                projectsArray.add(project);
                project.addProperty("id", p.getId());
                project.addProperty("name", p.name);
                JsonArray roles = new JsonArray();
                project.add("roles", roles);
                if (projectRoles == null) {
                    projectRoles = ProjectRole.findByProject(p.getId());
                }
                for (ProjectRole r : projectRoles) {
                    JsonObject role = new JsonObject();
                    roles.add(role);
                    role.addProperty("id", r.getId());
                    role.addProperty("name", r.name);
                }
                projectRoles = null;
            }
            projects = null;
        }
        return res.toString();
    }

}