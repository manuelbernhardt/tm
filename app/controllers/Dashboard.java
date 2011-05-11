package controllers;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.tm.ProjectWidget;
import models.tm.Widget;

public class Dashboard extends TMController {

    private static Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeHierarchyAdapter(Widget.class, new WidgetSerializer());
        gson = builder.create();
    }

    public static void index() {
        render();
    }

    public static void widgets() {
        List<ProjectWidget> projectWidgets = getConnectedUser().projectWidgets;
        JsonObject o = new JsonObject();
        JsonArray data = new JsonArray();
        for(ProjectWidget w : projectWidgets) {
            data.add(gson.toJsonTree(w));
        }
        o.add("data", data);
        renderJSON(o.toString());
    }

}