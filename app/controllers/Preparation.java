package controllers;

import java.util.ArrayList;
import java.util.List;

import models.tm.User;
import models.tm.test.Instance;
import models.tm.test.InstanceParam;
import models.tm.test.Script;
import models.tm.test.Tag;
import org.apache.commons.lang.StringUtils;
import play.data.validation.Valid;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Preparation extends TMController {

    public static void index() {
        render();
    }

    public static void scriptContent(Long scriptNodeId) {
        Script script = Lookups.getScript(scriptNodeId);
        render(script);
    }

    public static void content(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        render(instance);
    }

    public static void schedule(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        render(instance);
    }

    public static void loadScheduleData(Long baseObjectId, String[] fields) {
        Instance instance = Lookups.getInstance(baseObjectId);
        renderFields(instance, fields);
    }

    public static void testData(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        render(instance);
    }

    public static void tags(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        Lookups.tags(instance);
    }

    public static void allTags(Long instanceId, String q) {
        Instance instance = Lookups.getInstance(instanceId);
        Lookups.allTags(instance.project, q);
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

    public static void editSchedule(Long instanceId, @Valid Instance instance) {
        // here we sort of cheat; instead of making Play! happy and passing all the 1000 parameters necessary for it to properly bind the instance, we just copy over the
        // parameters given by the "stale" instance.
        Instance existing = Lookups.getInstance(instanceId);
        User responsible = instance.responsible;
        checkInAccount(responsible);
        existing.responsible = responsible;
        existing.plannedExecution = instance.plannedExecution;
        existing.save();
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
