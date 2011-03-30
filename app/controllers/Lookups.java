package controllers;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.project.Project;
import models.project.test.Instance;
import models.project.test.Run;
import models.project.test.Tag;
import models.tm.User;
import play.mvc.Controller;
import play.mvc.Util;

/**
 * Lookups. There must be a way to make these methods generic.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Lookups extends TMController {

    @Util
    public static Instance getInstance(Long instanceId) {
        if (instanceId == null) {
            return null;
        }
        Instance instance = Instance.<Instance>findById(instanceId);
        if (instance == null) {
            return null;
        }
        TMController.checkInAccount(instance);
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
        TMController.checkInAccount(run);
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
        TMController.checkInAccount(project);
        return project;
    }

    @Util
    public static void allUsers() {
        List<User> users = User.find("from User u where u.authentication.account = ? and exists(from u.projectRoles r where r.project = ?)", TMController.getUserAccount(), TMController.getActiveProject()).<User>fetch();
        Controller.renderJSON(users, userSerializer);
    }

    @Util
    public static void allTags(Project project, String term) {
        List<Tag> tags = Tag.find("from Tag t where t.project = ? and t.name like ?", project, term + "%").fetch();
        Controller.renderJSON(tags, tagSerializer);
    }

    // TODO generify these serializers
    private static final UserSerializer userSerializer = new UserSerializer();

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
            return object;
        }
    }

}
