package models;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * Naming strategy so that we use qualified names for tables.
 * <br />
 * TODO this is not yet active!
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class NamespaceNamingStrategy extends ImprovedNamingStrategy {

    @Override
    public String classToTableName(String className) {
        // cut off "models"
        return addUnderscores(className.substring(7));
    }
}
