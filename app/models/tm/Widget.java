package models.tm;

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

}
