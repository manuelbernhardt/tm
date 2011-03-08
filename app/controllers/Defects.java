package controllers;

import java.util.List;

import controllers.tabularasa.TableController;
import models.project.Defect;
import models.project.Project;
import play.db.jpa.GenericModel;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Defects extends TMController {

    public static void index() {
        render();
    }

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

    public static void create(Defect defect) {
        defect.project = getActiveProject();
        defect.submittedBy = getConnectedUser();
        defect.create();
        index();
    }

}
