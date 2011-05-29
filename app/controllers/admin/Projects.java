package controllers.admin;

import java.util.ArrayList;
import java.util.List;

import controllers.Lookups;
import controllers.TMController;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.tabularasa.TableController;
import models.general.UnitRole;
import models.tm.*;
import models.tm.test.Instance;
import models.tm.test.Script;
import models.tm.test.Tag;
import models.tm.test.TagHolder;
import org.apache.commons.collections.iterators.ArrayListIterator;
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
        Tag tag = Tag.find("select t from Tag t where t.id=? and t.project.id=?", tagId, projectId).first();

        // are there duplicate tags
        List<Tag> tags = Tag.find("select t from Tag t where t.name=? and t.type=? and t.project.id=?", tagNewName, tag.type, projectId).fetch();

        if(tags.size()==0){
            tag.name = tagNewName;
            tag.save();
        }
        else if(tags.size()>0){
            renderJSON(tags.get(0).getId());
        }
        

    }

    @Restrict(UnitRole.ACCOUNTADMIN)
    public static void mergeTags(Long firstTagId, Long secondTagId, Long projectId){

        Tag firstTag = Tag.findById(firstTagId);
        Tag secondTag = Tag.findById(secondTagId);
        List<TagHolder> tagHolder = new ArrayList<TagHolder>();

        if(firstTag.type== Tag.TagType.REQUIREMENT){
            tagHolder = Requirement.find("from Requirement r where r.project.id=?",projectId).fetch();
        }
        else if(firstTag.type== Tag.TagType.TESTSCRIPT){
            tagHolder = Script.find("from Script s where s.project.id=?", projectId).fetch();
        }
        else if(firstTag.type==Tag.TagType.TESTINSTANCE){
            tagHolder =  Instance.find("from Instance i where i.project.id=?", projectId).fetch();
        }
        else if(firstTag.type==Tag.TagType.DEFECT){
            tagHolder = Defect.find("from Defect d where d.project.id=?", projectId).fetch();
        }

         for(TagHolder th:tagHolder){
             if(th.getTags().contains(firstTag)) {
                th.getTags().remove(firstTag);
                th.getTags().add(secondTag);
             }
             ((ProjectModel)th).save();
         }
        firstTag.delete();
    }

}
