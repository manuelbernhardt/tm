package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.tm.Defect;
import models.tm.ProjectWidget;
import play.db.jpa.JPA;
import play.libs.F;
import play.mvc.Router;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Widgets extends TMController {

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


    public static void graph(String entity,
                             String graphType,
                             String yAxis,
                             String xAxis,
                             String temporalField,
                             String graphTitle,
                             String graphLabel) {

        List<Object[]> objects = null;
        List<Object> countList = new ArrayList<Object>();
        List<Object> dateList = new ArrayList<Object>();


        if (graphType != null && graphType.equals("temporal")) {

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
            if (transformExpression(xAxis, temporalField)._2 != Widgets.ExpressionType.COUNT) {
                countIndex = 1;
                dateIndex = 0;
            }

            for (Object[] o : objects) {
                Object count = o[countIndex];
                countList.add(count);
                Object d = o[dateIndex];
                dateList.add(d);
            }

            render(countList, dateList, graphTitle, graphLabel);

        } else if (graphType != null && graphType.equals("relational")) {
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

            render(countList, dateList, graphTitle, graphLabel);

        } else {
            objects = Defect.find("select count(d), day(d.created) from Defect d group by day(d.created)").fetch();

            for (Object[] o : objects) {
                Object count = o[0];
                countList.add(count);
                Object d = o[1];
                dateList.add(d);
            }

            render(countList, dateList, graphTitle, graphLabel);
        }
    }

    private static F.Tuple<String, ExpressionType> transformExpression(String axis, String tmp) {

        String queryFragment;
        ExpressionType type;
        type = ExpressionType.valueOf(axis.toUpperCase());
        queryFragment = axis + "(" + tmp + ") ";
        return new F.Tuple<String, ExpressionType>(queryFragment, type);
    }

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
            c.addProperty("title", w.getTitle());
            c.addProperty("description", w.getDescription());
            c.addProperty("creator", w.getCreator());
            c.addProperty("url", Router.reverse("Widgets." + w.getType().getViewAction(), w.getParameters()).toString());
            c.addProperty("image", String.format("/public/images/widgets/%s.png", w.getType().getKey()));
            jsonWidgets.add(c);
        }
        result.add("data", jsonWidgets);
        renderJSON(result.toString());
    }

}
