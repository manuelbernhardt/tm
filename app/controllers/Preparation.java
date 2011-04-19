package controllers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import models.tm.User;
import models.tm.test.Instance;
import models.tm.test.InstanceParam;
import models.tm.test.Tag;
import play.data.validation.Valid;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Preparation extends TMController {

    public static void index() {
        render();
    }

    public static void tags(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        Lookups.tags(instance);
    }

    public static void allTags(Long instanceId, String q) {
        Instance instance = Lookups.getInstance(instanceId);
        Lookups.allTags(instance.project, q);
    }

    public static void scheduleData(Long baseObjectId, String[] fields) {
        Instance instance = Lookups.getInstance(baseObjectId);
        renderFields(instance, fields);
    }

    public static void instanceParameters(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        List<InstanceParam> parameters = instance.getParams();
        JsonObject params = new JsonObject();
        JsonArray array = new JsonArray();
        for(InstanceParam p : parameters) {
            JsonObject object = new JsonObject();
            object.addProperty("id", p.id);
            object.addProperty("name", p.scriptParam.name);
            object.addProperty("value", p.value);
            array.add(object);
        }
        params.add("params", array);
        renderJSON(params.toString());
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
        String paramsJson = params.get("params");
        JsonReader reader = new JsonReader(new StringReader(paramsJson));
        try {
            reader.beginObject();
            while(reader.hasNext()) {
                String name = reader.nextName();
                if(name.equals("params")) {
                    reader.beginArray();
                    while(reader.hasNext()) {
                        reader.beginObject();
                        Long id = -1l;
                        String paramValue = "", paramName;
                        while(reader.hasNext()) {
                            String n = reader.nextName();
                            if(n.equals("id")) {
                                id = reader.nextLong();
                            }
                            if(n.equals("value")) {
                                paramValue = reader.nextString();
                            }
                            if(n.equals("name")) {
                                reader.nextString();
                            }
                        }
                        InstanceParam p = InstanceParam.<InstanceParam>findById(id);
                        p.value = paramValue;
                        p.save();
                    }
                }
            }

        } catch(Throwable t) {
            // todo logging
            t.printStackTrace();
            error();
        }
        ok();
    }

    public static void allUsers() {
        Lookups.allUsers();
    }


}
