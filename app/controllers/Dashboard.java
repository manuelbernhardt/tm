package controllers;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.tm.ProjectWidget;
import models.tm.TMUser;
import models.tm.UserWidget;
import models.tm.Widget;
import play.db.jpa.JPA;
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
        List<UserWidget> userWidgets = getConnectedUser().getUserWidgets();
        JsonObject o = new JsonObject();
        JsonArray data = new JsonArray();
        for (UserWidget w : userWidgets) {
            data.add(gson.toJsonTree(w));
        }
        o.add("data", data);
        o.addProperty("layout", getConnectedUser().dashboardLayout);
        renderJSON(o.toString());
    }

    public static void changeLayout(String layout) {
        if (layout != null) {
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

    public static void changeColumn(Long widgetId, String column) {
        if (column != null) {
            Logger.info(Logger.LogType.USER, "Changing column of widget %s to %s", widgetId, column);
            JPA.em().createQuery("update UserWidget w set w.column = :column where w.id = :widgetId and w.user.id = :userId")
                    .setParameter("column", column).setParameter("widgetId", widgetId).setParameter("userId", getConnectedUserId())
                    .executeUpdate();
            ok();
        } else {
            Logger.error(Logger.LogType.USER, "Column change with null column");
            error("No layout provided");
        }
    }

    public static void toggleWidgetWindow(Long widgetId, Boolean state) {
        if (widgetId != null) {
            Logger.info(Logger.LogType.USER, "Changing state of widget %s to %s", widgetId, state);
            JPA.em().createQuery("update UserWidget w set w.open = :state where w.id=:widgetId and w.user.id = :userId")
                    .setParameter("widgetId", widgetId).setParameter("state", state).setParameter("userId", getConnectedUserId())
                    .executeUpdate();
        } else {
            Logger.error(Logger.LogType.TECHNICAL, "widgetId in toggle widget window is null");
        }
    }

    public static void addWidget(Long widgetId) {
        if (widgetId != null) {
            ProjectWidget projectWidget = ProjectWidget.find("id = ? and templateWidget = true", widgetId).<ProjectWidget>first();

            Logger.info(Logger.LogType.USER, "Adding widget '%s'", projectWidget.title);

            // create a new UserWidget
            UserWidget uw = new UserWidget(projectWidget.project);
            uw.column = "first";
            uw.open = true;
            uw.widget = projectWidget;
            uw.user = getConnectedUser();

            boolean created = uw.create();
            if (!created) {
                Logger.error(Logger.LogType.DB, "Could not create new UserWidget");
                error("Widget could not be added");
            } else {
                JsonObject response = new JsonObject();
                response.addProperty("id", uw.getId());
                renderJSON(response.toString());
            }

        } else {
            Logger.error(Logger.LogType.USER, "Attempting to add widget with empty widgetID");
            error("No layout provided");
        }
    }

    public static void deleteWidget(Long widgetId) {
        if (widgetId != null) {
            UserWidget widget = UserWidget.find("id = ? and user.id = ?", widgetId, getConnectedUserId()).<UserWidget>first();
            if (widget != null) {
                widget.delete();
                ok();
            } else {
                Logger.error(Logger.LogType.DB, "Could not delete widget with ID %s", widgetId);
                error("Widget to delete could not be found");
            }
        }
    }
}