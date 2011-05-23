package controllers.admin;

import java.util.List;

import controllers.Lookups;
import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import models.general.UnitRole;
import models.tm.Project;
import models.tm.ProjectRole;
import models.tm.TMUser;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Projects extends TMController {

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void index() {
        render();
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void projectDetails(Long projectId) {
        Project project = Lookups.getProject(projectId);
        render(project);
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void edit(Project project) {
        checkInAccount(project);
        project.save();
        // TODO validation
        ok();
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void roles(Long projectId) {
        Project project = Lookups.getProject(projectId);
        render(project);
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void users(Long projectId) {
        Project project = Lookups.getProject(projectId);
        List<ProjectRole> projectRoles = ProjectRole.findByProject(projectId);
        List<TMUser> accountUsers = TMUser.listByAccount(getConnectedUserAccount().getId());
        render(project, projectRoles, accountUsers);
    }

}
