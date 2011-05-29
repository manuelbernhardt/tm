package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.tm.Filter;
import models.tm.FilterConstraint;
import models.tm.Project;
import play.mvc.Util;

/**
 * nikola
 * Date: 5/19/11
 * Time: 5:57 PM
 */
public class Filters extends TMController {

    @Util
    public static void saveFilter() {

        Project project = getActiveProject();

        Filter filter = new Filter(project);
        filter.name = params.get("name");
        filter.entity = params.get("entity");
        filter.owner = getConnectedUser();
        filter.filterConstraints = new ArrayList<FilterConstraint>();

        filter.create();

        List<String> par = new ArrayList<String>();
        for (String p : params.all().keySet()) {
            if (p.startsWith("constraint")) {
                par.add(p);
            }
        }

        for (String fp : par) {
            Matcher m = tagParameterPattern("constraint").matcher(fp);
            FilterConstraint filterConstraint = new FilterConstraint(project);

            if (m.matches()) {
                String key1 = m.group(1).replace("'", "");
                String key2 = m.group(2).replace("['", "").replace("']", "");
                String value = params.get(fp);

                filterConstraint.property = key1;
                filterConstraint.type = key2;
                filterConstraint.value = value;


                filter.filterConstraints.add(filterConstraint);
                filter.save();


            }
            filterConstraint.create();
        }
        renderJSON(true);
    }

    public static List<Filter> getFilters() {
        return Filter.find("owner=?", getConnectedUser()).fetch();
    }

    @Util
    public static void loadFilters(String entity){
        JsonObject result = new JsonObject();
        JsonArray jsonFilters = new JsonArray();
        List<Filter> filterList = Filter.find("from Filter f where f.entity=?", entity).fetch();
        for (Filter f : filterList) {
            JsonObject c = new JsonObject();
            c.addProperty("filterId", f.getId());
            c.addProperty("name", f.getName());
            jsonFilters.add(c);
        }
        result.add("availableFilters", jsonFilters);
        renderJSON(result.toString());
    }

    @Util
    public static void loadFilterById(Long id){
        Filter filter = Filter.find("from Filter f where f.id = ? and f.owner = ?", id, getConnectedUser()).<Filter>first();
        JsonObject c = new JsonObject();
        for (FilterConstraint fc : filter.filterConstraints) {
            c.addProperty(fc.property, fc.value);
        }
        renderJSON(c.toString());
    }
}
