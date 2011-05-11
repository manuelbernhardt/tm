package controllers;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.tm.Widget;
import play.mvc.Router;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class WidgetSerializer implements JsonSerializer<Widget> {
    public JsonElement serialize(Widget widget, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject o = new JsonObject();
        o.addProperty("title", widget.getTitle());
        o.addProperty("id", widget.getWidgetIdentifier());
        o.addProperty("column", widget.getColumn());
        o.addProperty("open", widget.isOpen());
        o.addProperty("url", Router.reverse("Widgets." + widget.getType().getViewAction(), widget.getParameters()).toString());
        o.addProperty("editUrl", Router.reverse("Widgets." + widget.getType().getEditAction(), widget.getParameters()).toString());
        return o;
    }
}
