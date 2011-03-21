package models.project;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.data.validation.MaxSize;
import tree.persistent.Node;
import tree.persistent.NodeName;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class ApproachTestCycle extends ProjectModel implements Node {

    @NodeName
    public String name;

    @MaxSize(5000)
    public String description;

    public Date fromDate;

    public Date toDate;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
