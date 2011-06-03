package controllers.admin;

import java.util.List;

import controllers.Lookups;
import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.tabularasa.TableController;
import models.general.UnitRole;
import models.tm.Project;
import models.tm.ProjectRole;
import models.tm.TMUser;
import play.db.jpa.GenericModel;
import play.mvc.With;
import util.Logger;

/**
 * @author Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@With(Deadbolt.class)
public class ProjectRoles extends TMController {

    @Restrict(UnitRole.PROJECTEDIT)
    public static void create(ProjectRole role, Long projectId) {
        role.project = Project.findById(projectId);
        role.create();
        // TODO validation
        ok();
    }

    @Restrict(UnitRole.PROJECTEDIT)
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

    @Restrict(UnitRole.PROJECTEDIT)
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

    @Restrict(UnitRole.PROJECTEDIT)
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

    @Restrict(UnitRole.PROJECTEDIT)
    public static void roleDelete(Long roleId) {
        ProjectRole role = Lookups.getRole(roleId);
        if (role != null) {
            List<TMUser> tmUser = TMUser.find("from TMUser tmu where ? in elements(tmu.projectRoles)", role).fetch();
            if (tmUser.size() == 0) {
                role.delete();
            } else {
                error("This role is assigned at least to one of users. Role is not deleted!");
                Logger.error(Logger.LogType.USER, "Attempt to delete a role id %s which is assigned to at least one user", roleId);
            }
        } else {
            error("Not existing role!");
            Logger.error(Logger.LogType.SECURITY, "Role with id %s doesn't exist", roleId);
        }
    }
}
