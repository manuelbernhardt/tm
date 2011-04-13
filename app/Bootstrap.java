import java.util.List;

import models.SchemaCreation;
import models.tm.User;
import models.tree.jpa.TreeNode;
import org.hibernate.Session;
import play.Play;
import play.db.jpa.JPA;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@OnApplicationStart()
public class Bootstrap extends Job {
    @Override
    public void doJob() throws Exception {

        // this is a Bootstrap job for the development mode, making it easy to re-create data and schema elements out of nowhere
        // for production, we'll need to export the schema creation into a consolidated script
        if (Play.mode == Play.Mode.DEV) {
            if (User.count() == 0) {

                // for development: create triggers && validate model
                SchemaCreation schemaCreation = new SchemaCreation(((Session) JPA.em().getDelegate()).getSessionFactory());
                schemaCreation.validateCompositeModels();
                schemaCreation.createTriggers();

                Fixtures.loadModels("initial-data.yml");

                // fix the treeNodes
                List<TreeNode> rootNodes = TreeNode.find("from TreeNode n where n.threadRoot is null").fetch();
                for (TreeNode n : rootNodes) {
                    n.threadRoot = n;
                    n.save();
                }
            }
        }
    }
}
