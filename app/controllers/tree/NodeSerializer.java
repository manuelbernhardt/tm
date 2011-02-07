package controllers.tree;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.tree.Node;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class NodeSerializer implements JsonSerializer<Node> {

    private Map<Object, Integer> levels = new HashMap<Object, Integer>();

    public JsonElement serialize(Node node, Type type, JsonSerializationContext context) {
        JsonObject o = new JsonObject();
        o.add("data", context.serialize(node.getName()));
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("id", node.getId());
        attributes.put("rel", node.getType().getName());
        o.add("attr", context.serialize(attributes));
        if(node.getType().isContainer()) {
            o.add("state", context.serialize(node.isOpened() ? "opened" : "closed"));
        }
        if(node.getType().isContainer() && !levels.containsKey(node)) { // don't recurse
            o.add("children", context.serialize(node.getChildren()));
            levels.put(node, 1);
        }
        return o;
    }
}
