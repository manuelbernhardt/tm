package controllers;

import java.io.StringReader;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import controllers.deadbolt.Restrict;
import controllers.tabularasa.TableController;
import models.general.UnitRole;
import models.tm.TMUser;
import models.tm.approach.Release;
import models.tm.test.ExecutionStatus;
import models.tm.test.Instance;
import models.tm.test.InstanceParam;
import models.tm.test.Script;
import models.tm.test.ScriptParam;
import models.tm.test.Tag;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.db.jpa.GenericModel;
import play.mvc.Before;
import util.Logger;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Preparation extends TMController {

    @Restrict(UnitRole.TESTPREPVIEW)
    public static void index() {
        Long releases = Release.count("project.id = ?", getActiveProjectId());
        Boolean hasReleases = releases > 0;
        render(hasReleases);
    }

    @Restrict(UnitRole.TESTPREPVIEW)
    public static void instanceDetailsData(Long baseObjectId, String[] fields) {
        Object base = Lookups.getInstance(baseObjectId);
        renderFields(base, fields);
    }

    @Restrict(UnitRole.TESTPREPVIEW)
    public static void tags(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        Lookups.tags(instance.tags);
    }

    @Restrict(UnitRole.TESTPREPVIEW)
    public static void schedule(Long baseObjectId, String[] fields) {
        Instance instance = Lookups.getInstance(baseObjectId);
        renderFields(instance, fields);
    }

    @Restrict(UnitRole.TESTPREPVIEW)
    public static void instanceParameters(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        if (instance == null) {
            Logger.error(Logger.LogType.TECHNICAL, "Instance with ID %s was not found", instanceId);
            notFound("Instance with ID " + instanceId + " could not be found");
        }
        List<InstanceParam> parameters = instance.getParams();
        JsonObject params = new JsonObject();
        JsonArray array = new JsonArray();
        for (InstanceParam p : parameters) {
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
    public static void allTags(String q) {
        Lookups.allTags(getActiveProject().getId(), Tag.TagType.TESTINSTANCE, q);
    }

    @Before
    public static void handleTags() {
        if (request.actionMethod.equals("edit")) {
            processTags("instance.tags", Tag.TagType.TESTINSTANCE);
        }
    }

    @Restrict(UnitRole.TESTPREPEDIT)
    public static void edit(@Valid Instance instance) {
        checkInAccount(instance);
        checkAuthenticity();
        if (Validation.hasErrors()) {
            // TODO handle validation errors in view somehow
            error();
        }
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
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("params")) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        Long id = -1l;
                        String paramValue = "", paramName;
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String n = reader.nextName();
                            if (n.equals("id")) {
                                id = reader.nextLong();
                            }
                            if (n.equals("value")) {
                                try {
                                    paramValue = reader.nextString();
                                } catch (IllegalStateException e) {
                                    e.fillInStackTrace();
                                    reader.skipValue();
                                }
                            }
                            if (n.equals("name")) {
                                reader.nextString();
                            }
                        }
                        reader.endObject();
                        InstanceParam p = Lookups.getInstanceParam(id);
                        p.value = paramValue;
                        p.save();

                    }
                    reader.endArray();
                }
            }
            reader.endObject();

        } catch (Throwable t) {
            Logger.error(Logger.LogType.TECHNICAL, t, "Could not extract JSON parameters for test instance parameter update, JSON string is '%s'", paramsJson);
            error();
        }
        ok();
    }

    @Restrict(UnitRole.TESTPREPVIEW)
    public static void instances(String tableId, Integer iDisplayStart, Integer iDisplayLength, String sColumns, String sEcho, Long scriptId) {
        GenericModel.JPAQuery query;
        if (scriptId != null) {
            query = Instance.find("script.id = ?", scriptId);
        } else {
            query = Instance.all();
        }
        List<Instance> instances = query.fetch();
        TableController.renderJSON(instances, Instance.class, Instance.count(), sColumns, sEcho);
    }

    @Restrict(UnitRole.TESTPREPCREATE)
    public static void createInstance(Long scriptId) {
        Script script = Lookups.getScript(scriptId);
        if (script == null) {
            Logger.error(Logger.LogType.TECHNICAL, "Could not find script with ID %s", scriptId);
            notFound("Could not find script with ID " + scriptId);
        }
        Instance instance = new Instance(script.project);
        instance.script = script;
        instance.executionStatus = ExecutionStatus.NOT_RUN;
        instance.name = script.name + " " + (Instance.count("from Instance i where i.script = ?", script) + 1);

        boolean created = instance.create();
        if (!created) {
            Logger.error(Logger.LogType.DB, "Could not create new instance");
            error("Error creating new instance, please try again");
        } else {
            // create instance params
            for (ScriptParam param : script.getParams()) {
                InstanceParam instanceParam = new InstanceParam(script.project);
                instanceParam.scriptParam = param;
                instanceParam.instance = instance;
                instanceParam.create();
            }
            ok();
        }
    }

    @Restrict(UnitRole.TESTPREPDELETE)
    public static void deleteInstance(Long instanceId) {
        Instance instance = Lookups.getInstance(instanceId);
        if (instance != null) {
            try {
                instance.delete();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } else {
            Logger.error(Logger.LogType.SECURITY, "Could not delete instance with id %s", instanceId);
            error("Instance with given id doesn't exist!");
        }
    }
}
