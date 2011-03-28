package controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;

import controllers.deadbolt.Deadbolt;
import controllers.tabularasa.TableController;
import models.project.approach.Release;
import models.project.test.Instance;
import models.project.test.Run;
import models.project.test.RunStep;
import models.tm.User;
import org.apache.commons.lang.StringUtils;
import play.db.jpa.GenericModel;
import play.mvc.With;
import util.FilterQuery;

/**
 * TODO security
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Execution extends TMController {

    public static void index() {
        List<Release> releases = Release.find("from Release r where r.project = ?", getActiveProject()).fetch();
        List<User> users = User.listByProject(getActiveProject().getId());
        render(releases, users);
    }

    public static void content(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        render(instance);
    }

    public static void runContent(Long runId) {
        Run run = Lookups.getRun(runId);
        render(run);
    }

    public static void allUsers() {
        Shared.allUsers();
    }

    public static void allTags(String term) {
        Shared.allTags(getActiveProject(), term);
    }


    public static void instances(String tableId,
                                 Integer iDisplayStart,
                                 Integer iDisplayLength,
                                 String sColumns,
                                 String sEcho,
                                 Long cycle,
                                 String status,
                                 String tags,
                                 Long responsible,
                                 Date dateFrom,
                                 Date dateTo) {


        FilterQuery fq = new FilterQuery(Instance.class);

        Map<String, Object> filters = new HashMap<String, Object>();

        fq.addFilter("project", "=", getActiveProject());

        if (cycle != null) {
            fq.addFilter("testCycle.id", "=", cycle);
        }
        if (status != null && !StringUtils.isEmpty(status)) {
            fq.addFilter("status", "=", status);
        }
        if (tags != null && !StringUtils.isEmpty(tags)) {
            fq.addJoin("tags", "o", "t");
            fq.setDistinct(true);
            // Hibernate has a nasty bug that will yield in a ClassCastException when passing a String[], so we need to cast here
            fq.addWhere("t.name in (:tags)", "tags", Arrays.asList(tags.split(",")));
            fq.addAfterWhere("group by o.id having count(t.id) = " + tags.split(",").length);
        }
        if (responsible != null) {
            fq.addFilter("responsible.id", "=", responsible);
        }
        if (dateFrom != null) {
            fq.addWhere("o.plannedExecution >= :dateFrom", "dateFrom", dateFrom);
        }
        if (dateTo != null) {
            fq.addWhere("o.plannedExecution <= :dateTo", "dateTo", dateTo);
        }

        Query query = fq.build();
        if (iDisplayStart != null) {
            query.setFirstResult(iDisplayStart);
        }
        if (iDisplayLength != null) {
            query.setMaxResults(iDisplayLength);
        }
        List instances = query.getResultList();
        long totalRecords = instances.size();

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
