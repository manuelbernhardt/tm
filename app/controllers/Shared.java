package controllers;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.project.Project;
import models.project.test.Instance;
import models.project.test.Tag;
import models.tm.User;
import play.mvc.Util;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Shared extends TMController {

    @Util
    public static void allUsers() {
        List<User> users = User.find("from User u where u.authentication.account = ? and exists(from u.projectRoles r where r.project = ?)", getUserAccount(), getActiveProject()).<User>fetch();
        renderJSON(users, userSerializer);
    }

    @Util
    public static void allTags(Project project, String term) {
        List<Tag> tags = Tag.find("from Tag t where t.project = ? and t.name like ?", project, term + "%").fetch();
        renderJSON(tags, tagSerializer);
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
