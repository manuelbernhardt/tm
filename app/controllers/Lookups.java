package controllers;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.tm.Project;
import models.tm.Requirement;
import models.tm.User;
import models.tm.test.Instance;
import models.tm.test.Run;
import models.tm.test.RunParam;
import models.tm.test.Script;
import models.tm.test.Tag;
import play.mvc.Controller;
import play.mvc.Util;

/**
 * Lookups. There must be a way to make these methods generic.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Lookups extends TMController {

    @Util
    public static Script getScript(Long scriptId) {
        if (scriptId == null) {
            return null;
        }
        Script script = Script.<Script>findById(scriptId);
        if (script == null) {
            return null;
        }
        checkInAccount(script);
        return script;
    }


    @Util
    public static Instance getInstance(Long instanceId) {
        if (instanceId == null) {
            return null;
        }
        Instance instance = Instance.<Instance>findById(instanceId);
        if (instance == null) {
            return null;
        }
        checkInAccount(instance);
        return instance;
    }

    @Util
    public static Run getRun(Long runId) {
        if (runId == null) {
            return null;
        }
        Run run = Run.<Run>findById(runId);
        if (run == null) {
            return null;
        }
        checkInAccount(run);
        return run;
    }

    @Util
    public static Project getProject(Long projectId) {
        Project project = null;
        if (projectId != null) {
            project = Project.<Project>findById(projectId);
        }
        if (project == null) {
            return null;
        }
        checkInAccount(project);
        return project;
    }

    @Util
    public static RunParam getRunParam(Long runParamId) {
        RunParam runParam = null;
        if (runParamId != null) {
            runParam = RunParam.<RunParam>findById(runParamId);
        }
        if (runParam == null) {
            return null;
        }
        checkInAccount(runParam);
        return runParam;
    }


    @Util
    public static void allUsers() {
        List<User> users = User.listByActiveProject();
        Controller.renderJSON(users, userSerializer);
    }

    @Util
    public static void tags(List<Tag> tags) {
        renderJSON(tags, tagSerializer);
    }

    @Util
    public static void allTags(Project project, Tag.TagType type, String term) {
        List<Tag> tags = Tag.find("from Tag t where t.type = ? and t.project = ? and lower(t.name) like ?", type, project, term + "%").fetch();
        renderJSON(tags, tagSerializer);
    }

    // TODO generify these serializers
    private static final UserSerializer userSerializer = new UserSerializer();

    public static Script getTestScript(Long scriptId) {
        if (scriptId == null) {
            return null;
        }
        Script script = Script.findById(scriptId);
        if (script == null) {
            return null;
        }
        checkInAccount(script);
        return script;
    }

    static Requirement getRequirement(Long requirementId) {
        if (requirementId == null) {
            return null;
        }
        Requirement requirement = Requirement.<Requirement>findById(requirementId);
        if (requirement == null) {
            return null;
        }
        checkInAccount(requirement);
        return requirement;
    }

    private static class UserSerializer implements JsonSerializer<User> {
        public JsonElement serialize(User user, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("id", user.id);
            object.addProperty("label", user.getFullName());
            object.addProperty("value", user.id);
            return object;
        }
    }

    // TODO generify by making an interface for the autocompletable entities
    private static final TagSerializer tagSerializer = new TagSerializer();

    private static class TagSerializer implements JsonSerializer<Tag> {
        public JsonElement serialize(Tag tag, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("id", tag.id);
            object.addProperty("label", tag.name);
            object.addProperty("value", tag.name);
            object.addProperty("name", tag.name);
            return object;
        }
    }

}
