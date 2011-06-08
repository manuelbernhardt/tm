package controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

import com.google.gson.JsonObject;
import controllers.deadbolt.Restrict;
import controllers.tabularasa.TableController;
import models.general.UnitRole;
import models.tm.Defect;
import models.tm.DefectStatus;
import models.tm.StringMatcherType;
import models.tm.TMUser;
import models.tm.test.Instance;
import models.tm.test.Run;
import models.tm.test.RunStep;
import models.tm.test.Tag;
import org.apache.commons.lang.StringUtils;
import play.mvc.Before;
import util.FilterQuery;


/**
 * @author Nikola Milivojevic
 */
public class Defects extends TMController {

    private static final String[] sortBy = {"id", "name", "tags", "assignedTo", "submittedBy", "status.name", "created"};

    @Restrict(UnitRole.DEFECTVIEW)
    public static void index() {
        List<TMUser> users = TMUser.listByProject(getActiveProject().getId());
        render(users);
    }

    public static void create(Long runId) {
        boolean create = true;
        
        List<RunStep> runSteps = RunStep.find("from RunStep rs where rs.run.id=? and rs.status=3", runId).fetch();
        Instance instance = Run.find("select r.instance from Run r where r.id=?",runId ).first();

        String defectDescription="Test instance ran: "+instance.name;

        for(RunStep runStep:runSteps){
            defectDescription = defectDescription + "\\n\\nExpected result: "
                    +runStep.expectedResult + "\\nActual result: " +runStep.actualResult;
        }

        render("Defects/index.html", create, runId, defectDescription);
    }

    @Restrict(UnitRole.DEFECTVIEW)
    public static void defects(String tableId,
                               Integer iDisplayStart,
                               Integer iDisplayLength,
                               String sColumns,
                               String sEcho,
                               String title,
                               String titleCase,
                               String tags,
                               String status,
                               Long assignedToId,
                               Long submittedById,
                               Date dateFrom,
                               Date dateTo,
                               Integer iSortCol_0,
                               String sSortDir_0) {


        FilterQuery fq = new FilterQuery(Defect.class);

        fq.addFilter("project", "=", getActiveProject());

        if (StringUtils.isNotEmpty(title)) {
            StringMatcherType type = null;
            if (titleCase == null) {
                type = StringMatcherType.CONTAINS;
            } else {
                type = StringMatcherType.valueOf(titleCase.toUpperCase());
            }
            switch (type) {
                case EQUALS:
                    fq.addFilter("name", "=", title);
                    break;
                case CONTAINS:
                    fq.addFilter("name", "like", '%' + title + '%');
                    break;
                case STARTSWITH:
                    fq.addFilter("name", "like", title + '%');
                    break;
                case ENDSWITH:
                    fq.addFilter("name", "not like", '%' + title + '%');
                    break;
            }
        }
        if (StringUtils.isNotEmpty(tags)) {
            fq.addJoin("tags", "o", "t");
            fq.setDistinct(true);
            fq.addWhere("t.name in (:tags)", "tags", Arrays.asList(tags.split(",")));
            fq.addAfterWhere("group by o.id having count(t.id) = " + tags.split(",").length);
        }
        if (StringUtils.isNotEmpty(status)) {
            fq.addFilter("status.name", "=", status);
        }
        if (assignedToId != null) {
            fq.addFilter("assignedTo.id", "=", assignedToId);
        }
        if (submittedById != null) {
            fq.addFilter("submittedBy.id", "=", submittedById);
        }
        if (dateFrom != null) {
            fq.addWhere("o.created >= :dateFrom", "dateFrom", dateFrom);
        }
        if (dateTo != null) {
            fq.addWhere("o.created <= :dateTo", "dateTo", dateTo);
        }

        if (iSortCol_0 != null && iSortCol_0 > -1)
            fq.addAfterWhere("order by " + sortBy[iSortCol_0] + " " + sSortDir_0);


        Query query = fq.build();

        if (iDisplayStart != null) {
            query.setFirstResult(iDisplayStart);
        }
        if (iDisplayLength != null) {
            query.setMaxResults(iDisplayLength);
        }

        try {
            List defects = query.getResultList();
            TableController.renderJSON(defects, Defect.class, Defect.count(), sColumns, sEcho);
        } catch (Throwable t) {
            t.printStackTrace();
            error();
        }

    }

    @Before
    public static void handleTags() {
        if (request.actionMethod.equals("createDefect") || request.actionMethod.equals("updateDefect")) {
            processTags("defect.tags", Tag.TagType.DEFECT);
        }
    }

    @Restrict(UnitRole.DEFECTCREATE)
    public static void createDefect(Defect defect) {
        defect.submittedBy = getConnectedUser();
        defect.account = getConnectedUserAccount();
        defect.project = getActiveProject();
        defect.status = DefectStatus.getDefaultDefectStatus();
        defect.tags = getTags(params.get("defect.tags"), Tag.TagType.DEFECT);
        defect.create();
        
        Long runId = Long.valueOf(params.get("runId"));
        if(runId!=null){
            Instance instance = Run.find("select r.instance from Run r where r.id=?", runId).first();
            instance.defects.add(defect);
            instance.save();
        }
        ok();
    }

    @Restrict(UnitRole.DEFECTEDIT)
    public static void updateDefect(Defect defect) {
        Defect d = Defect.findById(defect.id);
        d.name = defect.name;
        d.description = defect.description;
        d.assignedTo = defect.assignedTo;
        d.status = defect.status;
        defect.tags = getTags(params.get("defect.tags"), Tag.TagType.DEFECT);
        d.save();
        ok();
    }

    @Restrict(UnitRole.DEFECTVIEW)
    public static void defectDetails(Long baseObjectId, String[] fields) {
        Defect defect = Defect.findById(baseObjectId);
        renderFields(defect, fields);
    }

    @Restrict(UnitRole.DEFECTDELETE)
    public static void deleteDefect(Long defectId) {
        Defect defect = Defect.findById(defectId);
        if (defect == null) {
            error("Defect is not found!");
        }
        defect.delete();
        ok();
    }

    @Restrict(UnitRole.DEFECTVIEW)
    public static void allTags(String q) {
        Lookups.allTags(getActiveProject().getId(), Tag.TagType.DEFECT, q);
    }

    @Restrict(UnitRole.DEFECTVIEW)
    public static void defectDescription(Long defectId) {
        Defect defect = Defect.findById(defectId);
        if (defect == null) {
            error("Defect is not found!");
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("defectTitle", defect.name);
        jsonObject.addProperty("defectAssignedTo", defect.assignedTo == null ? "" : defect.assignedTo.toString());
        jsonObject.addProperty("defectSubmittedBy", defect.submittedBy == null ? "" : defect.submittedBy.toString());
        jsonObject.addProperty("defectStatus", defect.status == null ? "" : defect.status.toString());
        jsonObject.addProperty("defectCreated", defect.created == null ? "" : defect.created.toString());
        jsonObject.addProperty("defectTags", defect.tags == null ? "" :  defect.tags.toString());
        jsonObject.addProperty("defectDescription", defect.description == null ? "" : defect.description);
        renderJSON(jsonObject.toString());
    }
}
