package models.tm;

/**
 * @author Nikola
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public enum ConstraintType {

    VALUE("value", "text"), // constraint on the value itself - should maybe be called "equals"
    STRINGMATCH("match", "text"), // constraint on the string content (startsWith, endsWith, ...), see StringMatcherType
    DATEFROM("dateFrom", "date"), // date boundary
    DATETO("dateTo", "date"), // date boundary
    AND("and", ""), // AND grouping
    OR("or", ""); // OR grouping

    String key;
    String fieldType;

    ConstraintType(String key, String fieldType) {
        this.key = key;
        this.fieldType = fieldType;
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

    public String getFieldType() {
        return fieldType;
    }

}
