package models.tm;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public enum WidgetType {

    GRAPH(0, "viewGraph", "editGraph");

    Integer key;
    String viewAction;
    String editAction;

    WidgetType(Integer key, String viewAction, String editAction) {
        this.key = key;
        this.viewAction = viewAction;
        this.editAction = editAction;
    }

    public Integer getKey() {
        return this.key;
    }

    public static WidgetType fromKey(Integer key) {
        for (WidgetType k : WidgetType.values()) {
            if (k.getKey().equals(key)) {
                return k;
            }
        }
        throw new RuntimeException("No widget type for key " + key);
    }

}
