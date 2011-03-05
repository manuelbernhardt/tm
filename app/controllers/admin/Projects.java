package controllers.admin;

import controllers.CRUD;
import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import models.general.UnitRole;
import models.project.Project;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Projects extends TMController {

    public static void index() {
        render();
    }

    public static void projectDetails(Long projectId) {
        Project project = null;
        if (projectId != null) {
            project = Project.findById(projectId);
        }
        render(project);
    }

    @Restrict(UnitRole.ADMIN)
    public static void edit(Project project) {
        project.save(getUserAccount());
        // TODO validation
        ok();
    }

    public static void roles(Long projectId) {
        Project project = null;
        if (projectId != null) {
            project = Project.findById(projectId);
        }
        render(project);
    }

    public static void users(Long projectId) {
        Project project = null;
        if (projectId != null) {
            project = Project.findById(projectId);
        }
        render(project);
    }

}
