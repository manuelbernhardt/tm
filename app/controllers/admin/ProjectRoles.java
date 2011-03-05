package controllers.admin;

import java.util.List;

import controllers.TMController;
import controllers.tabularasa.TableController;
import models.project.Project;
import models.project.Role;
import play.db.jpa.GenericModel;

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

        if(projectId == null) {
            error();
        } else {
            GenericModel.JPAQuery query = null;
            query = Role.find("from Role r where r.project.id = ?",projectId).from(iDisplayStart == null ? 0 : iDisplayStart);

            List<Role> roles = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
            long totalRecords = Role.count();
            TableController.renderJSON(roles, Role.class, totalRecords, sColumns, sEcho);
            ok();
        }
    }

    public static void roleDefinition(Long roleId) {
        if(roleId == null) {
            error("No roleId provided");
        } else {
            Role role = Role.findById(roleId);
            render(role);
        }
    }

}
