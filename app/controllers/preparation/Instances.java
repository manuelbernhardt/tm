package controllers.preparation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import controllers.Lookups;
import controllers.TMController;
import models.project.test.Instance;
import models.project.test.Tag;
import models.tm.User;

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
        List<Tag> tags = Tag.find("from Tag t where t.project = ? and t.name like ?", instance.project, term + "%").fetch();
        renderJSON(tags, tagSerializer);
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
            if(!tagList.contains(t)) {
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

    /**
     * Renders all users in the active project
     *
     * We may want to move this someplace else.
     */
    public static void allUsers() {
        List<User> users = User.find("from User u where u.authentication.account = ? and exists(from u.projectRoles r where r.project = ?)", getUserAccount(), getActiveProject()).<User>fetch();
        renderJSON(users, userSerializer);
    }

    // TODO generify by making an interface for the autocompletable entities
    private static final TagSerializer tagSerializer = new TagSerializer();
    private static final UserSerializer userSerializer = new UserSerializer();

    private static class TagSerializer implements JsonSerializer<Tag> {
        public JsonElement serialize(Tag tag, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("id", tag.id);
            object.addProperty("label", tag.name);
            object.addProperty("value", tag.name);
            return object;
        }
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

}
