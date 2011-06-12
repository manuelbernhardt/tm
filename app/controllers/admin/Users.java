package controllers.admin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import play.mvc.Router;
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

    @Restrict(UnitRole.USEREDIT)
    public static void userDetailsData(Long baseObjectId, String[] fields){
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
            user = TMUser.findById(userId);
        }
        String[] accounts = new String[3];
        Gson gson = new Gson();

        boolean userAdmin = false, projectAdmin = false, accountAdmin = false;
        if (user != null) {
            List<AccountRole> accountRoles = AccountRole.getAccountRoles(user.accountRoles);

            userAdmin = accountRoles.contains(AccountRole.USER_ADMIN);
            if(userAdmin){
                accounts[0] = "userAdmin";
            }
            projectAdmin = accountRoles.contains(AccountRole.PROJECT_ADMIN);
            if(projectAdmin){
                accounts[1] = "projectAdmin";
            }
            accountAdmin = accountRoles.contains(AccountRole.ACCOUNT_ADMIN);
            if(accountAdmin){
                accounts[2] = "accountAdmin";
            }

        }
        renderJSON(gson.toJson(accounts));
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void updateAccountRoles(Long userId, String[] roles) {
        List<String> accountRoles = new ArrayList<String>();
        if(roles!=null){
            for(String role: roles){
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
        User u = User.findById(userId);
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
            query = TMUser.find("from TMUser u where u.user.firstName like ? or u.user.lastName like ?", sLike, sLike);
        } else {
            query = TMUser.find("from TMUser u").from(iDisplayStart == null ? 0 : iDisplayStart);
        }
        List<TMUser> people = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = TMUser.count();
        TableController.renderJSON(people, TMUser.class, totalRecords, sColumns, sEcho);
    }

    @Restrict(UnitRole.USEREDIT)
    public static void categoryOptions(){
        List<ProjectCategory> projectCategories = ProjectCategory.findAll();
        JsonArray jsonArray = new JsonArray();
        JsonObject result = new JsonObject();
        for(ProjectCategory projectCategory:projectCategories){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("categoryValue", projectCategory.id);
            jsonObject.addProperty("categoryName", projectCategory.name);
            jsonArray.add(jsonObject);
        }
        result.add("categories", jsonArray);
        renderJSON(result.toString());
    }

    @Restrict(UnitRole.USEREDIT)
    public static void projectOptions(Long categoryId) {
        if (categoryId == null) {
            error();
        } else {

            List<Project> projects;
            if (categoryId == -1l) {
                // all non-assigned projects
                projects = Project.find("from Project p where p.projectCategory is null and p.account.id = ?", getConnectedUserAccountId()).<Project>fetch();
            } else {
                ProjectCategory pc = ProjectCategory.findById(categoryId);
                checkInAccount(pc);
                projects = pc.getProjects();
            }
            JsonObject result = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            for (Project project : projects) {
                JsonObject property = new JsonObject();
                property.addProperty("projectValue", project.id);
                property.addProperty("projectName", project.name);
                jsonArray.add(property);
            }
            result.add("projects", jsonArray);
            renderJSON(result.toString());
        }
    }

    @Restrict(UnitRole.USEREDIT)
    public static void rolesOptions(Long projectId) {
        if (projectId == null) {
            error();
        } else {
            List<ProjectRole> roles;
            if (projectId == -1l) {
                System.out.println("is null");
                roles = ProjectRole.find("from ProjectRole r where r.project = null").<ProjectRole>fetch();
            }
            else{
                Project p = Project.findById(projectId);
                if (p == null) {
                    notFound();
                }
                checkInAccount(p);
                roles = ProjectRole.find("from ProjectRole r where r.project.id = ?", projectId).<ProjectRole>fetch();
            }
            
            JsonObject result = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            for(ProjectRole role: roles){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("roleName", role.name);
                jsonObject.addProperty("roleValue", role.id);
                jsonArray.add(jsonObject);
            }
            result.add("roles", jsonArray);
            renderJSON(result.toString());
        }
    }

    @Restrict(UnitRole.USEREDIT)
    public static void rolesData() {
        List<ProjectCategory> projectCategories = ProjectCategory.findAll();
        JsonObject res = new JsonObject();
        JsonArray categories = new JsonArray();
        res.add("categories", categories);
        for(ProjectCategory pc : projectCategories) {
           JsonObject category = new JsonObject();
            categories.add(category);
            category.addProperty("name", pc.name);
            category.addProperty("id", pc.getId());
            JsonArray projects = new JsonArray();
            category.add("projects", projects);
            for(Project p : pc.getProjects()) {
                JsonObject project = new JsonObject();
                projects.add(project);
                project.addProperty("id", p.getId());
                project.addProperty("name", p.name);
                JsonArray roles = new JsonArray();
                project.add("roles", roles);
                for(ProjectRole r : ProjectRole.findByProject(p.getId())) {
                    JsonObject role = new JsonObject();
                    roles.add(role);
                    role.addProperty("id", r.getId());
                    role.addProperty("name", r.name);
                }
            }
        }
        renderJSON(res.toString());
    }

    @Restrict(UnitRole.USEREDIT)
    public static void rolesTreeData(Long categoryId, Long projectId, Long roleId) {

        if(categoryId==null){
            error("Category id must not be null!");
        }
        List<ProjectRole> projectRoles = new ArrayList<ProjectRole>();
        List<Project> projects = new ArrayList<Project>();
        List<ProjectCategory> projectCategories = new ArrayList<ProjectCategory>();
        if(roleId!=null){
            projectRoles = ProjectRole.find("id=?", roleId).fetch();
        }
        if(projectId!=null){
            projects = Project.find("id=?", projectId).fetch();
            if(roleId==null){
                projectRoles = ProjectRole.findByProject(projectId);
            }
        }
        if (projectCategories!=null){
            projectCategories = ProjectCategory.find("id=?", categoryId).fetch();
            if(projectId==null){
                for(ProjectCategory pc: projectCategories){
                    for(Project p:pc.getProjects()){
                       projects.add(p);
                        if(roleId==null){
                            for(ProjectRole pr: ProjectRole.findByProject(p.getId())){
                                projectRoles.add(pr);
                            }
                        }
                    }

                }

            }
        }


        JsonObject res = new JsonObject();
        JsonArray categories = new JsonArray();
        res.add("categories", categories);
        for(ProjectCategory pc : projectCategories) {
           JsonObject category = new JsonObject();
            categories.add(category);
            category.addProperty("name", pc.name);
            category.addProperty("id", pc.getId());
            JsonArray projectsArray = new JsonArray();
            category.add("projects", projectsArray);
            for(Project p : projects) {
                JsonObject project = new JsonObject();
                projectsArray.add(project);
                project.addProperty("id", p.getId());
                project.addProperty("name", p.name);
                JsonArray roles = new JsonArray();
                project.add("roles", roles);
                for(ProjectRole r : projectRoles) {
                    JsonObject role = new JsonObject();
                    roles.add(role);
                    role.addProperty("id", r.getId());
                    role.addProperty("name", r.name);
                }
            }
        }
        renderJSON(res.toString());
    }

}
