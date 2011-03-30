package controllers.preparation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controllers.Lookups;
import controllers.TMController;
import models.project.test.Instance;
import models.project.test.InstanceParam;
import models.project.test.Tag;
import models.tm.User;
import org.apache.commons.lang.StringUtils;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Instances extends TMController {

    public static void content(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        render(instance);
    }

    public static void tags(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        instance.refresh();
        List<Tag> tags = instance.tags;
        render(instance, tags);
    }

    public static void schedule(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        render(instance);
    }

    public static void testData(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        render(instance);
    }

    public static void allTags(Long instanceId, String term) {
        Instance instance = Lookups.getInstance(instanceId);
        Lookups.allTags(instance.project, term);
    }

    public static void addTags(Long instanceId, String tags) {
        Instance instance = Lookups.getInstance(instanceId);
        List<Tag> tagList = new ArrayList<Tag>();
        for (String name : tags.split(",")) {
            Tag t = Tag.find("from Tag t where t.name = ? and t.project = ?", name, instance.project).first();
            if (t == null) {
                t = new Tag();
                t.name = name;
                t.project = instance.project;
                t.create();
            }
            if (!tagList.contains(t)) {
                tagList.add(t);
            }
        }
        instance.tags = tagList;
        instance.save();
        ok();
    }

    public static void editSchedule(Long instanceId, Long responsibleId, Date plannedExecution) {
        Instance instance = Lookups.getInstance(instanceId);
        User responsible = User.<User>findById(responsibleId);
        checkInAccount(responsible);
        instance.responsible = responsible;
        instance.plannedExecution = plannedExecution;
        instance.save();
        ok();
    }

    public static void editData(Long instanceId) {
        String paramValue_ = "paramValue_";

        for (String p : params.all().keySet()) {
            if (p.startsWith(paramValue_)) {
                String id = p.substring(paramValue_.length());
                if (StringUtils.isNotEmpty(id)) {
                    try {
                        Long lid = Long.parseLong(id);
                        InstanceParam ip = InstanceParam.<InstanceParam>findById(lid);
                        checkInAccount(ip);
                        ip.value = (String) params.all().get(p)[0];
                        ip.save();
                    } catch (NumberFormatException nfe) {
                        // TODO log
                        nfe.printStackTrace();
                    }
                }
            }
        }
        ok();

    }

    public static void allUsers() {
        Lookups.allUsers();
    }

}
