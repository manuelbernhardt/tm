import java.util.List;

import models.tm.User;
import models.tree.jpa.TreeNode;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@OnApplicationStart
public class Bootstrap extends Job {
    @Override
    public void doJob() throws Exception {
        if(User.count() == 0) {
            Fixtures.loadModels("initial-data.yml");

            // fix the treeNodes
            List<TreeNode> rootNodes = TreeNode.find("from TreeNode n where n.threadRoot is null").fetch();
            for(TreeNode n : rootNodes) {
                n.threadRoot = n;
                n.save();
            }
        }
    }
}
