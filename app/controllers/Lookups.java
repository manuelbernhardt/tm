package controllers;

import models.project.Project;
import models.project.test.Instance;
import models.project.test.Run;
import play.mvc.Util;

/**
 * Lookups. There must be a way to make these methods generic.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Lookups {

    @Util
    public static Instance getInstance(Long instanceId) {
        if (instanceId == null) {
            return null;
        }
        Instance instance = Instance.<Instance>findById(instanceId);
        if (instance == null) {
            return null;
        }
        TMController.checkInAccount(instance);
        return instance;
    }

    @Util
    public static Run getRun(Long runId) {
        if (runId == null) {
            return null;
        }
        Run run = Run.<Run>findById(runId);
        if (run == null) {
            return null;
        }
        TMController.checkInAccount(run);
        return run;
    }


    @Util
    public static Project getProject(Long projectId) {
        Project project = null;
        if (projectId != null) {
            project = Project.<Project>findById(projectId);
        }
        if (project == null) {
            return null;
        }
        TMController.checkInAccount(project);
        return project;
    }
}
