package models.tm;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public enum WidgetType {

    GRAPH("graph", "graph", "editGraph");

    String key;
    String viewAction;
    String editAction;

    WidgetType(String key, String viewAction, String editAction) {
        this.key = key;
        this.viewAction = viewAction;
        this.editAction = editAction;
    }

    public String getKey() {
        return this.key.toString();
    }

    public String getViewAction() {
        return viewAction;
    }

    public String getEditAction() {
        return editAction;
    }

    public static WidgetType fromKey(String key) {
        for (WidgetType k : WidgetType.values()) {
            if (k.getKey().equals(key)) {
                return k;
            }
        }
        throw new RuntimeException("No widget type for key " + key);
    }

}
