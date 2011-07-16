package models;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.account.AccountModel;
import models.general.CompositeModel;
import models.tm.ProjectModel;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.Joinable;
import play.Play;
import play.classloading.ApplicationClasses;
import play.db.DB;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class SchemaCreation {

    private static final String CREATE_ACCOUNTMODEL_TRIGGER_SYNTAX =
            "CREATE TRIGGER naturalIdTrigger_%1$s\n" +
                    "BEFORE INSERT ON %1$s FOR EACH ROW\n" +
                    "BEGIN\n" +
                    "  DECLARE row_count BIGINT;\n" +
                    "  SELECT CASE WHEN MAX(naturalId) IS NULL THEN 0 ELSE MAX(naturalId) END\n" +
                    "  INTO row_count\n" +
                    "  FROM %1$s\n" +
                    "  WHERE %2$s=NEW.%2$s;\n" +
                    "  SET NEW.naturalId=row_count + 1;\n" +
                    "END\n";

    private static final String CREATE_PROJECTMODEL_TRIGGER_SYNTAX =
            "CREATE TRIGGER naturalIdTrigger_%1$s\n" +
                    "BEFORE INSERT ON %1$s FOR EACH ROW\n" +
                    "BEGIN\n" +
                    "  DECLARE row_count BIGINT;\n" +
                    "  SELECT CASE WHEN MAX(naturalId) IS NULL THEN 0 ELSE MAX(naturalId) END\n" +
                    "  INTO row_count\n" +
                    "  FROM %1$s\n" +
                    "  WHERE %2$s=NEW.%2$s AND %3$s=NEW.%3$s;\n" +
                    "  SET NEW.naturalId=row_count + 1;\n" +
                    "END\n";

    private static final String CREATE_ACCOUNT_ID_FUNCTION_SYNTAX =
            "CREATE FUNCTION current_account_id() RETURNS INTEGER DETERMINISTIC NO SQL return @account";

    private static final String CREATE_VIEW =
            "CREATE VIEW %s AS SELECT %s FROM %s WHERE account_id = current_account_id()";

    public static final String ACCOUNT_ID = "account_id";


    private final SessionFactory sessionFactory;

    public SchemaCreation(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createStuff() {
        Connection c = DB.getConnection();
        for (String stmt : getAllSortsOfStatements()) {
            try {
                c.createStatement().executeUpdate(stmt);
                System.out.println(stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<String> getAllSortsOfStatements() {
        List<String> statements = new ArrayList<String>();

        //statements.add(CREATE_ACCOUNT_ID_FUNCTION_SYNTAX);

        List<Class<CompositeModel>> models = getCompositeModels();

        for (Class<CompositeModel> c : models) {
            if (isSubclass(ProjectModel.class, c)) {
                statements.add(String.format(CREATE_PROJECTMODEL_TRIGGER_SYNTAX, getTableName(c), "project_id", "account_id"));
            } else if (isSubclass(AccountModel.class, c)) {
                statements.add(String.format(CREATE_ACCOUNTMODEL_TRIGGER_SYNTAX, getTableName(c), "account_id"));
            } else if (!c.equals(ProjectModel.class) && !c.equals(AccountModel.class)) {
                throw new RuntimeException("Quantum leak in the hyperdrive: " + c);
            }

            List<String> columnNames = new ArrayList<String>();
            Connection connection = DB.getConnection();
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(String.format("SELECT * from %s", getTableName(c)));
                ResultSetMetaData metaData = resultSet.getMetaData();
                int cols = metaData.getColumnCount();
                for (int i = 1; i <= cols; i++) {
                    String name = metaData.getColumnName(i);
                    if (!name.equals(ACCOUNT_ID)) {
                        columnNames.add(name);
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
                throw new RuntimeException(t);
            }
            //statements.add(String.format(CREATE_VIEW, getTableName(c) + "View", JavaExtensions.join(columnNames, ", "), getTableName(c)));
        }
        return statements;
    }

    /**
     * Custom validation method to make sure we did not forget to annotate one class
     * <p/>
     * TODO this should actually be a unit test !!!
     */
    public void validateCompositeModels() {
        List<Class<CompositeModel>> compositeModels = getCompositeModels();
        for (Class<CompositeModel> compositeModel : compositeModels) {
            Table table = compositeModel.getAnnotation(Table.class);
            if (table == null) {
                throw new RuntimeException("No @Table annotation with unique constraints found for entity " + compositeModel.getName());
            }
            for (UniqueConstraint uniqueConstraint : table.uniqueConstraints()) {
                if (isSubclass(ProjectModel.class, compositeModel)) {
                    boolean valid = ArrayUtils.contains(uniqueConstraint.columnNames(), "project_id");
                    valid = valid && ArrayUtils.contains(uniqueConstraint.columnNames(), "naturalId");
                    if (!valid) {
                        throw new RuntimeException("WRONGLY MAPPED PROJECT MODEL: MISSING UNIQUE CONSTRAINT ON (naturalId, project_id)");
                    }
                } else if (isSubclass(AccountModel.class, compositeModel)) {
                    boolean valid = ArrayUtils.contains(uniqueConstraint.columnNames(), "account_id");
                    valid = valid && ArrayUtils.contains(uniqueConstraint.columnNames(), "naturalId");
                    if (!valid) {
                        throw new RuntimeException("WRONGLY MAPPED ACCOUNT MODEL: MISSING UNIQUE CONSTRAINT ON (naturalId, project_id)");
                    }
                } else {
                    throw new RuntimeException("Subspace anomaly");
                }
            }

        }
    }

    private List<Class<CompositeModel>> getCompositeModels() {
        List<Class<CompositeModel>> models = new ArrayList<Class<CompositeModel>>();

        List<ApplicationClasses.ApplicationClass> compositeModels = Play.classes.getAssignableClasses(CompositeModel.class);
        for (ApplicationClasses.ApplicationClass c : compositeModels) {
            Class<CompositeModel> javaClass = (Class<CompositeModel>) c.javaClass;
            if (!models.contains(javaClass) && !javaClass.equals(ProjectModel.class) && !javaClass.equals(AccountModel.class)) {
                models.add(javaClass);
            }
        }
        return models;
    }

    private boolean isSubclass(Class parent, Class kid) {
        return parent.isAssignableFrom(kid) && !kid.equals(parent);
    }

    private String getTableName(Class<?> mappedClass) {
        ClassMetadata cmd = sessionFactory.getClassMetadata(mappedClass);

        //check that the class is mapped to something with a table name
        if (cmd == null || !Joinable.class.isInstance(cmd))
            throw new RuntimeException("Can't get table name for class " + mappedClass);

        return Joinable.class.cast(cmd).getTableName();
    }

}
