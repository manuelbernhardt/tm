package models.tm;

/**
 * nikola
 * Date: 5/19/11
 * Time: 5:39 PM
 */
public enum ConstraintType {

    VALUE("value", "text"), // constraint on the value itself - should maybe be called "equals"
    MATCHES("matches", "text"),
    STARTSWITH("startsWith", "text"),
    ENDSWITH("endsWith", "text"),
    NOTCONTAIN("notContains", "text"),
    CONTAINS("contains","text"),
    DATEFROM("dateFrom", "date"),
    DATETO("dateTo", "date");

    String key;
    String action;

    ConstraintType(String key, String action){
        this.key = key;
        this.action = action;
    }

    public static ConstraintType fromKey(String key) {
        for (ConstraintType k : ConstraintType.values()) {
            if (k.getKey().equals(key)) {
                return k;
            }
        }
        throw new RuntimeException("No constraint type type for key " + key);
    }


    public String getKey() {
        return this.key.toString();
    }

    public String getAction() {
        return action;
    }

}
