package models.tm;

import java.util.Map;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface Widget {

    Long getId();
    String getWidgetIdentifier();
    String getTitle();
    String getColumn();
    boolean isOpen();
    WidgetType getType();
    Map<String, Object> getParameters();

}
