package models.project;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import controllers.tree.AbstractTree;
import models.tree.Node;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class TestScriptFolder extends ProjectModel implements Node {

    public String name;

    public void setName(String name) {
        this.name = name;
        AbstractTree.rename(this, name);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO
        return super.clone();
    }
}
