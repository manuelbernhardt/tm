package controllers.admin;

import java.util.List;

import controllers.TMController;
import controllers.tabularasa.TableController;
import models.tm.Project;
import models.tm.ProjectRole;
import play.db.jpa.GenericModel;

/**
 * @author Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
public class ProjectRoles extends TMController {

    public static void create(ProjectRole role, Long projectId) {
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

        if (projectId == null) {
            error();
        } else {
            GenericModel.JPAQuery query;
            query = ProjectRole.find("from ProjectRole r where r.project.id = ?", projectId).from(iDisplayStart == null ? 0 : iDisplayStart);

            List<ProjectRole> roles = query.fetch(iDisplayLength == null ? 10 : iDisplayLength);
            long totalRecords = ProjectRole.count();
            TableController.renderJSON(roles, ProjectRole.class, totalRecords, sColumns, sEcho);
            ok();
        }
    }

    public static void roleDefinition(Long roleId) {
        if (roleId == null) {
            error("No roleId provided");
        } else {
            ProjectRole role = ProjectRole.findById(roleId);
            checkInAccount(role);
            List<String> unitRoles = role.unitRoles;
            render(role, unitRoles);
        }
    }

    public static void edit(Long roleId, String[] unitRoles) {
        if (roleId == null) {
            error("No roleId provided");
        } else {
            ProjectRole role = ProjectRole.findById(roleId);

            checkInAccount(role);

            role.unitRoles.clear();
            for (int i = 0, unitRolesLength = unitRoles.length; i < unitRolesLength; i++) {
                String unitRole = unitRoles[i];
                role.unitRoles.add(unitRole);
            }
            role.save();
            ok();
        }
    }
}
