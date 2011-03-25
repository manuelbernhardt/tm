package controllers;

import java.util.List;

import controllers.deadbolt.Deadbolt;
import controllers.tabularasa.TableController;
import models.project.approach.Release;
import models.project.test.Instance;
import models.project.test.Run;
import models.project.test.RunStep;
import play.db.jpa.GenericModel;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Execution extends TMController {

    public static void index() {
        List<Release> releases = Release.find("from Release r where r.project = ?", getActiveProject()).fetch();
        render(releases);
    }

    public static void content(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        render(instance);
    }

    public static void runContent(Long runId) {
        Run run = Lookups.getRun(runId);
        render(run);

    }

    public static void instances(String tableId,
                                 Integer iDisplayStart,
                                 Integer iDisplayLength,
                                 String sColumns,
                                 String sEcho,
                                 String sSearch) {
        GenericModel.JPAQuery query = null;
        if (sSearch != null && sSearch.length() > 0) {
            // TODO implement the search
            query = Instance.find("from Instance i where i.project.id = ?", TMController.getActiveProject().getId());
        } else {
            query = Instance.find("from Instance i where i.project.id = ?", TMController.getActiveProject().getId()).from(iDisplayStart == null ? 0 : iDisplayStart);
        }
        List<Instance> instances = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = Instance.count();
        TableController.renderJSON(instances, Instance.class, totalRecords, sColumns, sEcho);
    }

    public static void runs(String tableId, Long instanceId,
                                 Integer iDisplayStart,
                                 Integer iDisplayLength,
                                 String sColumns,
                                 String sEcho,
                                 String sSearch) {
        Instance instance = Lookups.getInstance(instanceId);
        GenericModel.JPAQuery query = null;
        if (sSearch != null && sSearch.length() > 0) {
            // TODO implement the search
            query = Run.find("from Run r where r.instance = ? and r.project = ?", instance, TMController.getActiveProject());
        } else {
            query = Run.all().from(iDisplayStart == null ? 0 : iDisplayStart);
        }
        List<Run> runs = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = Run.count();
        TableController.renderJSON(runs, Run.class, totalRecords, sColumns, sEcho);
    }

    public static void runSteps(String tableId, Long runId,
                                 Integer iDisplayStart,
                                 Integer iDisplayLength,
                                 String sColumns,
                                 String sEcho,
                                 String sSearch) {
        Run run = Lookups.getRun(runId);
        GenericModel.JPAQuery query = null;
            // TODO implement the search
        if (sSearch != null && sSearch.length() > 0) {
            query = RunStep.find("from RunStep s where s.run = ? and r.project = ?", run, TMController.getActiveProject());
        } else {
            query = RunStep.find("from RunStep s where s.run = ? and r.project = ?").from(iDisplayStart == null ? 0 : iDisplayStart);
        }
        List<RunStep> runSteps = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = RunStep.count();
        TableController.renderJSON(runSteps, RunStep.class, totalRecords, sColumns, sEcho);
    }


}
