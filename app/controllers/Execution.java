package controllers;

import java.util.List;

import controllers.deadbolt.Deadbolt;
import controllers.tabularasa.TableController;
import models.project.test.Instance;
import play.db.jpa.GenericModel;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Execution extends TMController {

    public static void index() {
        render();
    }

    public static void content(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        render(instance);
    }

    public static void data(String tableId,
                            Integer iDisplayStart,
                            Integer iDisplayLength,
                            String sColumns,
                            String sEcho,
                            String sSearch) {
        GenericModel.JPAQuery query = null;
        if (sSearch != null && sSearch.length() > 0) {
            // TODO
            query = Instance.find("from Instance i where i.project.id = ?", TMController.getActiveProject().getId());
        } else {
            query = Instance.all().from(iDisplayStart == null ? 0 : iDisplayStart);
        }
        List<Instance> people = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = Instance.count();
        TableController.renderJSON(people, Instance.class, totalRecords, sColumns, sEcho);
    }

}
