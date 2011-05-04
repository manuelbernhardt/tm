package controllers;

import java.util.List;

import controllers.deadbolt.Restrict;
import controllers.tabularasa.TableController;
import models.general.UnitRole;
import models.tm.Defect;
import models.tm.Project;
import play.db.jpa.GenericModel;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Defects extends TMController {

    public static void index() {
        render();
    }

    @Restrict(UnitRole.DEFECTVIEW)
    public static void data(String tableId,
                            Integer iDisplayStart,
                            Integer iDisplayLength,
                            String sColumns,
                            String sEcho) {

        Project activeProject = getActiveProject();

        GenericModel.JPAQuery query = null;
        query = Defect.find("from Defect d where d.project = ?", activeProject).from(iDisplayStart == null ? 0 : iDisplayStart);

        List<Defect> roles = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = Defect.count();
        TableController.renderJSON(roles, Defect.class, totalRecords, sColumns, sEcho);
        ok();
    }

    @Restrict(UnitRole.DEFECTCREATE)
    public static void create(Defect defect) {
        defect.project = getActiveProject();
        defect.submittedBy = getConnectedUser();
        defect.create();
        index();
    }

}
