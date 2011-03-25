package controllers;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
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


}
