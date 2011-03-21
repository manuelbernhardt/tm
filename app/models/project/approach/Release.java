package models.project.approach;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.project.ProjectModel;
import tree.persistent.Node;
import tree.persistent.NodeName;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(name = "ApproachRelease", uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class Release extends ProjectModel implements Node {

    @NodeName
    public String name;

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO
        return super.clone();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
