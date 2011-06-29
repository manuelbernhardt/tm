package models.tm.test;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.tm.Project;
import models.tm.ProjectModel;
import tree.persistent.Node;
import tree.persistent.NodeName;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})}, name = "tm_test_ScriptFolder")
public class ScriptFolder extends ProjectModel implements Node {

    @NodeName
    public String name;

    public ScriptFolder(Project project) {
        super(project);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO
        return super.clone();
    }
}
