package controllers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import controllers.deadbolt.Restrict;
import controllers.tabularasa.TableController;
import models.general.UnitRole;
import models.tm.Defect;
import models.tm.TMUser;
import models.tm.approach.Release;
import models.tm.test.Instance;
import models.tm.test.InstanceParam;
import models.tm.test.Script;
import models.tm.test.Tag;
import play.data.validation.Valid;
import play.db.jpa.GenericModel;
import util.Logger;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Preparation extends TMController {

    @Restrict(UnitRole.TESTPREPVIEW)
    public static void index() {
        Long releases = Release.count("project.id=?", getActiveProjectId());

        render(releases);
    }

    @Restrict(UnitRole.TESTPREPVIEW)
    public static void tags(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        Lookups.tags(instance.tags);
    }

    @Restrict(UnitRole.TESTPREPVIEW)
    public static void allTags(String q) {
        Lookups.allTags(getActiveProject().getId(), Tag.TagType.TESTINSTANCE, q);
    }

    @Restrict(UnitRole.TESTPREPVIEW)
    public static void schedule(Long baseObjectId, String[] fields) {
        Instance instance = Lookups.getInstance(baseObjectId);
        renderFields(instance, fields);
    }

    @Restrict(UnitRole.TESTPREPVIEW)
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

    @Restrict(UnitRole.TESTPREPEDIT)
    public static void updateTags(Long instanceId, String tags) {
        Instance instance = Lookups.getInstance(instanceId);
        instance.tags = getTags(tags, Tag.TagType.TESTINSTANCE);
        instance.save();
        ok();
    }

    @Restrict(UnitRole.TESTPREPEDIT)
    public static void updateSchedule(Long instanceId, @Valid Instance instance) {
        // here we sort of cheat; instead of making Play! happy and passing all the 1000 parameters necessary for it to properly bind the instance, we just copy over the
        // parameters given by the "stale" instance.
        Instance existing = Lookups.getInstance(instanceId);
        TMUser responsible = instance.responsible;
        checkInAccount(responsible);
        existing.responsible = responsible;
        existing.plannedExecution = instance.plannedExecution;
        existing.save();
        ok();
    }

    @Restrict(UnitRole.TESTPREPEDIT)
    public static void updateParameters(Long instanceId) {
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
            Logger.error(Logger.LogType.TECHNICAL, t, "Could not extract JSON parameters for test instance parameter update, instanceId is '%s' and JSON string is '%s'", instanceId, paramsJson);
            error();
        }
        ok();
    }

    public static void instances(String tableId,
                               Integer iDisplayStart,
                               Integer iDisplayLength,
                               String sColumns,
                               String sEcho,
                               Long instanceId) {

        GenericModel.JPAQuery query;

        if(instanceId!=null){
            query = Instance.find("script.id=?", instanceId);
        }else{
            query = Instance.all();
        }

        List<Instance> instances = query.fetch();

        TableController.renderJSON(instances, Instance.class, Instance.count(), sColumns, sEcho);
    }
}
