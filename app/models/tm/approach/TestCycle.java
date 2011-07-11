package models.tm.approach;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import controllers.ApproachTree;
import controllers.Lookups;
import controllers.ScriptCycleTreeDataHandler;
import models.tm.Project;
import models.tm.ProjectModel;
import models.tm.ProjectTreeNode;
import models.tm.test.Instance;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import tree.persistent.Node;
import tree.persistent.NodeName;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})}, name = "tm_approach_TestCycle")
public class TestCycle extends ProjectModel implements Node {

    @NodeName
    @Required
    @Column(nullable = false)
    public String name;

    @MaxSize(8000)
    @Column(length = 8000)
    public String description;

    public Date fromDate;

    public Date toDate;

    public TestCycle(Project project) {
        super(project);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Release getRelease() {
        ProjectTreeNode n = ProjectTreeNode.find("from ProjectTreeNode n where n.nodeId = ? and n.treeId = ? and n.type = ?", getId(), ApproachTree.APPROACH_TREE, ScriptCycleTreeDataHandler.TEST_CYCLE).first();
        return Lookups.getRelease(n.getParent().getNodeId());
    }

    public List<Instance> getInstances() {
        return Instance.find("from Instance i where i.testCycle = ?", this).<Instance>fetch();
    }
}
