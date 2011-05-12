package models.tm;

import java.util.Map;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface Widget {

    Long getId();

    String getTitle();

    String getCategory();

    String getDescription();

    String getCreator();

    boolean isTemplate();

    WidgetType getType();

    Map<String, Object> getParameters();

    String getColumn();

    boolean isOpen();
}
