package models;

import org.hibernate.cfg.EJB3NamingStrategy;

/**
 * Naming strategy so that we use qualified names for tables.
 * <br />
 * TODO does not work yet due to http://opensource.atlassian.com/projects/hibernate/browse/HHH-4312
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class NamespaceNamingStrategy extends EJB3NamingStrategy {

    public NamespaceNamingStrategy() {

    }

    @Override
    public String classToTableName(String className) {
        return className.indexOf("models.") > -1 ? className.substring("models.".length()).replaceAll("\\.", "_") : className.replaceAll("\\.", "_");
    }
}
