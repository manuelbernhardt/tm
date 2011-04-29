import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import models.SchemaCreation;
import models.account.Account;
import models.tm.ProjectTreeNode;
import models.tm.TMUser;
import models.tree.jpa.TreeNode;
import org.hibernate.Session;
import play.Play;
import play.db.DB;
import play.db.jpa.JPA;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.F;
import play.test.Fixtures;
import util.Logger;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@OnApplicationStart()
public class Bootstrap extends Job<F.None> {
    @Override
    public void doJob() throws Exception {

        // for the fun of it
        Logger.info(Logger.LogType.TECHNICAL, false, "\n=== Ground control to major Tom\n=== Commencing countdown engines on");

        // this is a Bootstrap job for the development mode, making it easy to re-create data and schema elements out of nowhere
        // for production, we'll need to export the schema creation into a consolidated script
        if (Play.mode == Play.Mode.DEV) {
            // create schema?
            Connection c = DB.getConnection();
            ResultSet triggers = c.createStatement().executeQuery("SELECT * FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA = '" + DB.getConnection().getCatalog() + "'");
            if (!triggers.first()) {
                System.out.println();
                System.out.println();
                System.out.println(String.format("=== SCHEMA CREATION ON SCHEMA %s ===", c.getCatalog()));
                System.out.println();
                System.out.println();

                // trigger JPA schema creation
                TMUser.count();

                // for development: create triggers && validate model
                SchemaCreation schemaCreation = new SchemaCreation(((Session) JPA.em().getDelegate()).getSessionFactory());
                schemaCreation.validateCompositeModels();
                schemaCreation.createTriggers();
            }
            c.close();

            if (Account.count() == 0) {
                Fixtures.deleteDatabase();
                Fixtures.loadModels("initial-data.yml");
                // fix the treeNodes
                List<ProjectTreeNode> rootNodes = TreeNode.find("from ProjectTreeNode n where n.threadRoot is null").fetch();
                for (ProjectTreeNode n : rootNodes) {
                    n.threadRoot = n;
                    n.save();
                }
            }
        }

        Logger.info(Logger.LogType.TECHNICAL, false, "\n=== This is major Tom to ground control\n=== I'm stepping through the door\n=== and I'm in floating in the most perculiar way\n=== and the stars look very different today");
    }
}
