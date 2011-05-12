package controllers;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.tm.ProjectWidget;
import models.tm.TMUser;
import models.tm.Widget;
import util.Logger;

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
        o.addProperty("layout", getConnectedUser().dashboardLayout);
        renderJSON(o.toString());
    }

    public static void changeLayout(String layout) {
        if(layout != null) {
            Logger.info(Logger.LogType.USER, "Changing dashboard layout to %s", layout);
            TMUser u = getConnectedUser();
            u.dashboardLayout = layout;
            u.save();
            ok();
        } else {
            Logger.error(Logger.LogType.USER, "Layout change with null layout");
            error("No layout provided");
        }
    }

}