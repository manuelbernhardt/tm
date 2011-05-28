package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.tm.Defect;
import models.tm.ProjectWidget;
import models.tm.Requirement;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import play.db.jpa.JPA;
import play.libs.F;
import play.mvc.Router;
import play.mvc.Util;
import util.Logger;

/**
 * FIXME TODO access rights management for report and graphs
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Widgets extends TMController {

    public static final String GRAPH_TEMPORAL = "temporal";
    public static final String GRAPH_RELATIONAL = "relational";

    public static enum ExpressionType {
        DAY(Integer.class), HOUR(Integer.class), COUNT(Long.class), DATE(Date.class), WEEK(Integer.class), MONTH(Integer.class), YEAR(Integer.class);
        Class<?> type;

        ExpressionType(Class<?> type) {
            this.type = type;
        }

        public Class<?> getType() {
            return type;
        }
    }


    public static void graph(String entity, String graphType, String yAxis, String xAxis, String temporalField,
                             String graphTitle, String graphLabel, String graphAppearance) {

        List<Object[]> objects = null;
        List<Object> countList = new ArrayList<Object>();
        List<Object> dateList = new ArrayList<Object>();

        if (StringUtils.isEmpty(graphAppearance)) {
            graphAppearance = "bar";
        }

        if (graphType != null && graphType.equals(GRAPH_TEMPORAL)) {
            renderTemporalGraph(entity, yAxis, xAxis, temporalField, graphTitle, graphLabel, graphAppearance, objects, countList, dateList);
        } else if (graphType != null && graphType.equals(GRAPH_RELATIONAL)) {
            renderRelationalGraph(entity, yAxis, xAxis, graphTitle, graphLabel, graphAppearance, objects, countList, dateList);
        }
    }

    @Util
    private static void renderRelationalGraph(String entity, String yAxis, String xAxis, String graphTitle, String graphLabel, String graphAppearance, List<Object[]> objects, List<Object> countList, List<Object> dateList) {
        StringBuffer sb = new StringBuffer();
        sb.append("select ");
        sb.append(String.format("count(%s)", xAxis));
        sb.append(",");
        sb.append(yAxis);
        sb.append(" from ");
        sb.append(entity);
        sb.append(" group by ");
        sb.append(yAxis);

        try {
            objects = JPA.em().createQuery(sb.toString()).getResultList();
        } catch (Throwable t) {
            error();
        }


        for (Object[] o : objects) {
            Object count = o[0];
            countList.add(count);
            Object d = o[1];
            dateList.add(d);
        }

        render(countList, dateList, graphTitle, graphLabel, graphAppearance);
    }

    @Util
    private static void renderTemporalGraph(String entity, String yAxis, String xAxis, String temporalField, String graphTitle, String graphLabel, String graphAppearance, List<Object[]> objects, List<Object> countList, List<Object> dateList) {
        StringBuffer sb = new StringBuffer();
        sb.append("select ");
        sb.append(transformExpression(xAxis, temporalField)._1); // xAxis = COUNT
        sb.append(",");
        sb.append(transformExpression(yAxis, temporalField)._1); // yAxis = DAY
        sb.append("from ");
        sb.append(entity);
        sb.append(" group by ");
        sb.append(transformExpression(xAxis, temporalField)._1);


        try {
            objects = JPA.em().createQuery(sb.toString()).getResultList();
        } catch (Throwable t) {
            error();
        }

        int countIndex = 0;
        int dateIndex = 1;
        if (transformExpression(xAxis, temporalField)._2 != ExpressionType.COUNT) {
            countIndex = 1;
            dateIndex = 0;
        }

        for (Object[] o : objects) {
            Object count = o[countIndex];
            countList.add(count);
            Object d = o[dateIndex];
            dateList.add(d);
        }

        render(countList, dateList, graphTitle, graphLabel, graphAppearance);
    }

    private final static ImmutableMap<String, String[]> defaultColumnNames = ImmutableMap.of(
            Defect.class.getSimpleName(), new String[]{"Name", "Description", "Submitted By", "Assigned To", "Status", "Tags"},
            Requirement.class.getSimpleName(), new String[]{"Name", "Description", "Created By", "Tags"}
    );

    private final static ImmutableMap<String, String[]> defaultColumnExpressions = ImmutableMap.of(
            Defect.class.getSimpleName(), new String[]{"name", "description", "submittedBy", "assignedTo", "status", "tagNames"},
            Requirement.class.getSimpleName(), new String[]{"name", "description", "createdBy", "tagNames"}
    );


    public static void report(String entity, String title) {

        // sanity check - since we send the entity over as HTTP param someone might want to hack around here
        if(!defaultColumnNames.containsKey(entity)) {
            Logger.error(Logger.LogType.SECURITY, "Trying to render report for entity %s", entity);
            error("Rendering report for unknown entity");
        }

        final String[] columnNames = defaultColumnNames.get(entity);
        final String[] columnExpressions = defaultColumnExpressions.get(entity);
        final List<Object> rowObjects = Defect.find(String.format("from %s d", entity)).fetch();
        List<String[]> rowList = new ArrayList<String[]>();

        for (Object bean : rowObjects) {
            String[] values = new String[columnExpressions.length];
            for (int i = 0; i < columnExpressions.length; i++) {
                try {
                    values[i] = BeanUtils.getSimpleProperty(bean, columnExpressions[i]);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            rowList.add(values);
        }

        render(columnNames, rowList, rowList, title);
    }

    private static F.Tuple<String, ExpressionType> transformExpression(String axis, String tmp) {

        String queryFragment;
        ExpressionType type;
        type = ExpressionType.valueOf(axis.toUpperCase());
        queryFragment = axis + "(" + tmp + ") ";
        return new F.Tuple<String, ExpressionType>(queryFragment, type);
    }

    /**
     * widget categories for all widgets
     */
    public static void categories() {
        JsonObject result = new JsonObject();
        JsonArray jsonCategories = new JsonArray();
        List<Object[]> categories = JPA.em().createQuery("select w.id, w.category, count(category) from ProjectWidget w where w.templateWidget = true group by category").getResultList();
        for (Object[] o : categories) {
            JsonObject c = new JsonObject();
            c.addProperty("id", (Number) o[0]);
            c.addProperty("title", (String) o[1]);
            c.addProperty("amount", (Number) o[2]);
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("category", (String) o[1]);
            c.addProperty("url", Router.reverse("Widgets.widgets", m).toString());
            jsonCategories.add(c);
        }
        result.add("category", jsonCategories);
        renderJSON(result.toString());
    }

    /**
     * all widgets for one category
     */
    public static void widgets(String category) {
        if (category == null) {
            error("No category provided");
            return;
        }

        JsonObject result = new JsonObject();
        JsonArray jsonWidgets = new JsonArray();
        List<ProjectWidget> widgets = ProjectWidget.find("templateWidget = true and category = ?", category).<ProjectWidget>fetch();
        for (ProjectWidget w : widgets) {
            JsonObject c = new JsonObject();
            c.addProperty("id", w.getId());
            c.addProperty("title", w.title);
            c.addProperty("description", w.description);
            c.addProperty("creator", w.creator);
            c.addProperty("url", Router.reverse("Widgets." + w.widgetType.getViewAction(), w.parameters).toString());
            c.addProperty("image", String.format("/public/images/widgets/%s.png", w.widgetType.getKey()));
            jsonWidgets.add(c);
        }
        result.add("data", jsonWidgets);
        renderJSON(result.toString());
    }

}
