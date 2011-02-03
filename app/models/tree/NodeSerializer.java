package models.tree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class NodeSerializer implements JsonSerializer<Node> {

    public JsonElement serialize(Node node, Type type, JsonSerializationContext context) {
        JsonObject o = new JsonObject();
        o.add("data", context.serialize(node.getTitle()));
        o.add("attr", context.serialize(node.getAttributes()));
        if(node.isContainer()) {
            o.add("state", context.serialize(node.isOpened() ? "opened" : "closed"));
        }
        if(node.isContainer()) {
            o.add("children", context.serialize(node.getChildren()));
        }

        return o;
    }

    private static Gson gson = new GsonBuilder().registerTypeAdapter(Node.class, new NodeSerializer()).create();

    public static String serialize(Object n) {
        return gson.toJson(n);
    }
}
