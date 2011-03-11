package controllers.admin;

import java.util.List;

import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import models.general.UnitRole;
import models.project.Project;
import models.project.Role;
import models.tm.User;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Projects extends TMController {

    @Restrict(UnitRole.ADMIN)
    public static void index() {
        render();
    }

    @Restrict(UnitRole.ADMIN)
    public static void projectDetails(Long projectId) {
        Project project = null;
        if (projectId != null) {
            project = Project.findById(projectId);
        }
        render(project);
    }

    @Restrict(UnitRole.ADMIN)
    public static void edit(Project project) {
        checkInAccount(project);
        project.save();
        // TODO validation
        ok();
    }

    @Restrict(UnitRole.ADMIN)
    public static void roles(Long projectId) {
        Project project = null;
        if (projectId != null) {
            project = Project.findById(projectId);
        }
        render(project);
    }

    @Restrict(UnitRole.ADMIN)
    public static void users(Long projectId) {
        Project project = null;
        if (projectId != null) {
            project = Project.findById(projectId);
        }
        List<Role> projectRoles = Role.findByProject(projectId);
        List<User> accountUsers = User.findByAccount(getUserAccount().getId());
        render(project, projectRoles, accountUsers);
    }
}
