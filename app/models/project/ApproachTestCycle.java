package models.project;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import controllers.tree.AbstractTree;
import models.tree.Node;
import play.data.validation.MaxSize;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class ApproachTestCycle extends ProjectModel implements Node {

    public String name;

    @MaxSize(5000)
    public String description;

    public Date fromDate;

    public Date toDate;

    public void setName(String name) {
        this.name = name;
        AbstractTree.rename(this, name);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
