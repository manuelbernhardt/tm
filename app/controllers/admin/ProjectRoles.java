package controllers.admin;

import controllers.TMController;
import controllers.tabularasa.TableController;
import models.project.Project;
import models.project.Role;
import models.tabularasa.TableModel;
import play.db.jpa.GenericModel;

import java.util.List;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
public class ProjectRoles extends TMController {

    public static void create(Role role, Long projectId) {
        role.project = Project.findById(projectId);
        role.create();
        // TODO validation
        ok();
    }

    public static void data(String tableId,
                            Integer iDisplayStart,
                            Integer iDisplayLength,
                            String sColumns,
                            String sEcho,
                            Long projectId) {
        GenericModel.JPAQuery query = null;
        query = Role.all().from(iDisplayStart == null ? 0 : iDisplayStart);

        List<Role> roles = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
        long totalRecords = Role.count();
        TableController.renderJSON(roles, Role.class, totalRecords, sColumns, sEcho);

    }

}
