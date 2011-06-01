package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.tm.ConstraintType;
import models.tm.Filter;
import models.tm.FilterConstraint;
import models.tm.Project;
import play.mvc.Util;
import util.Logger;

/**
 * nikola
 * Date: 5/19/11
 * Time: 5:57 PM
 */
public class Filters extends TMController {

    @Util
    public static void saveFilter(String name, String entity) {

        Project project = getActiveProject();

        Filter filter = new Filter(project);
        filter.name = name;
        filter.entity = entity;
        filter.owner = getConnectedUser();
        filter.filterConstraints = new ArrayList<FilterConstraint>();

        if(!filter.create()) {
            Logger.error(Logger.LogType.DB, "Could not create filter %s for entity %s", name, entity);
            error("Error saving filter, please try again");
        }

        List<String> par = new ArrayList<String>();
        for (String p : params.all().keySet()) {
            if (p.startsWith("constraint")) {
                par.add(p);
            }
        }

        for (String fp : par) {
            Matcher m = tagParameterPattern("constraint").matcher(fp);
            if (m.matches()) {
                FilterConstraint filterConstraint = new FilterConstraint(project);
                String property = m.group(1);
                String type = m.group(2).replace("[", "").replace("]", "");
                String value = params.get(fp);
                filterConstraint.property = property;
                filterConstraint.constraintType = ConstraintType.fromKey(type);
                filterConstraint.value = value;
                if(!filterConstraint.create()) {
                    Logger.error(Logger.LogType.DB, "Could not create FilterConstraint");
                    error("Error saving filter, please try again");
                }
                filter.filterConstraints.add(filterConstraint);
                filter.save();
            }
        }
        renderJSON(true);
    }

    @Util
    public static List<Filter> getFilters() {
        return Filter.find("owner.id = ?", getConnectedUserId()).fetch();
    }

    @Util
    public static void loadFilters(String entity) {
        JsonObject result = new JsonObject();
        JsonArray jsonFilters = new JsonArray();
        List<Filter> filterList = Filter.find("from Filter f where f.entity = ? and f.owner.id = ?", entity, getConnectedUserId()).fetch();
        for (Filter f : filterList) {
            JsonObject c = new JsonObject();
            c.addProperty("filterId", f.getId());
            c.addProperty("name", f.name);
            jsonFilters.add(c);
        }
        result.add("availableFilters", jsonFilters);
        renderJSON(result.toString());
    }

    @Util
    public static void loadFilterById(Long id) {
        Filter filter = Filter.find("from Filter f where f.id = ? and f.owner.id = ?", id, getConnectedUserId()).<Filter>first();
        JsonObject c = new JsonObject();
        for (FilterConstraint fc : filter.filterConstraints) {
            c.addProperty(fc.property, fc.value);
        }
        renderJSON(c.toString());
    }
}
