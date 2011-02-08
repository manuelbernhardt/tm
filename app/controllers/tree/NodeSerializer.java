package controllers.tree;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.tree.Node;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class NodeSerializer implements JsonSerializer<Node> {

    public JsonElement serialize(Node node, Type type, JsonSerializationContext context) {
        JsonObject o = new JsonObject();
        populateBasicProperties(node, context, o);

        if (node.getType().isContainer()) {
            List<? extends Node> c = node.getChildren();
            JsonArray children = new JsonArray();
            if(node.isOpen()) {
                // render full children
                o.add("children", context.serialize(node.getChildren()));
            } else {
                // render "closed" children
                for (Node n : c) {
                    JsonObject child = new JsonObject();
                    children.add(child);
                    populateBasicProperties(n, context, child);
                }
                o.add("children", children);
            }
        }
        return o;
    }

    private void populateBasicProperties(Node node, JsonSerializationContext context, JsonObject o) {
        o.addProperty("data", node.getName());
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("id", node.getId());
        attributes.put("rel", node.getType().getName());
        o.add("attr", context.serialize(attributes));
        if (node.getType().isContainer()) {
            o.addProperty("state", state(node.isOpen()));
        }
    }

    private String state(boolean open) {
        return open ? "open" : "closed";
    }
}
