import java.util.List;

import models.tm.ProjectTreeNode;
import models.tree.jpa.TreeNode;
import play.test.Fixtures;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class SeleniumTestDataLoader {

    public SeleniumTestDataLoader() {
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
