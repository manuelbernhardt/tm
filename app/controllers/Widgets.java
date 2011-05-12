package controllers;

import models.tm.Defect;
import play.db.jpa.JPA;
import play.libs.F;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

            render(countList,dateList, graphTitle, graphLabel);

        }
        else if(graphType !=null && graphType.equals("relational")){
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
                Object count =  o[0];
                countList.add(count);
                Object d =  o[1];
                dateList.add(d);
            }

            render(countList,dateList, graphTitle, graphLabel);
            
        }
        else {
            objects = Defect.find("select count(d), day(d.created) from Defect d group by day(d.created)").fetch();

            for (Object[] o : objects) {
                Object count =  o[0];
                countList.add(count);
                Object d =  o[1];
                dateList.add(d);
            }

            render(countList,dateList, graphTitle, graphLabel);
        }
    }

    private static F.Tuple<String, ExpressionType> transformExpression(String axis, String tmp) {

        String queryFragment;
        ExpressionType type;
        type = ExpressionType.valueOf(axis.toUpperCase());
        queryFragment = axis + "(" + tmp + ") ";
        return new F.Tuple<String, ExpressionType>(queryFragment, type);
    }

}
