package controllers.admin;

import java.util.ArrayList;
import java.util.List;

import controllers.Lookups;
import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.tabularasa.TableController;
import models.general.UnitRole;
import models.tm.Project;
import models.tm.Role;
import models.tm.TMUser;
import models.tm.test.Tag;
import play.db.jpa.GenericModel;
import play.mvc.With;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class Projects extends TMController {

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void index() {
        render();
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void projectDetails(Long projectId) {
        Project project = Lookups.getProject(projectId);
        render(project);
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void edit(Project project) {
        checkInAccount(project);
        project.save();
        // TODO validation
        ok();
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void roles(Long projectId) {
        Project project = Lookups.getProject(projectId);
        render(project);
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void users(Long projectId) {
        Project project = Lookups.getProject(projectId);
        List<Role> projectRoles = Role.findByProject(projectId);
        List<TMUser> accountUsers = TMUser.listByAccount(getConnectedUserAccount().getId());
        render(project, projectRoles, accountUsers);
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void tags(Long projectId){
        render(projectId);
    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void tagsData(String tableId,
                                Integer tagType,
                            Integer iDisplayStart,
                            Integer iDisplayLength,
                            String sColumns,
                            String sEcho,
                            Long projectId) {
        if (projectId == null) {
            error();
        } else {
            GenericModel.JPAQuery query;
            List<Tag> tags = new ArrayList<Tag>();
            switch (tagType){
                case 0:
                    query = Tag.find("from Tag t where t.project.id = ? and t.type = ?", projectId, Tag.TagType.REQUIREMENT).from(iDisplayStart == null ? 0 : iDisplayStart);
                    tags = query.fetch();
                    break;
                case 1:
                    query = Tag.find("from Tag t where t.project.id = ? and t.type = ?", projectId, Tag.TagType.TESTSCRIPT).from(iDisplayStart == null ? 0 : iDisplayStart);
                    tags = query.fetch();
                    break;
                case 2:
                    query = Tag.find("from Tag t where t.project.id = ? and t.type = ?", projectId, Tag.TagType.TESTINSTANCE).from(iDisplayStart == null ? 0 : iDisplayStart);
                    tags = query.fetch();
                    break;
                case 3:
                    query = Tag.find("from Tag t where t.project.id = ? and t.type = ?", projectId, Tag.TagType.DEFECT).from(iDisplayStart == null ? 0 : iDisplayStart);
                    tags = query.fetch();
                    break;
            }


            long totalRecords = Tag.count();
            TableController.renderJSON(tags, Tag.class, totalRecords, sColumns, sEcho);
            ok();
        }
    }

    public static void renameTag(Long tagId, String tagNewName, Long projectId){
        List<Tag> tags = Tag.find("select t from Tag t where t.id=? and t.project.id=?", tagId, projectId).fetch();
        if(tags.size()>1){
            
        }
        else if(tags.size()==1){
            tags.get(0).name = tagNewName;
            tags.get(0).save();
        }
    }

}
