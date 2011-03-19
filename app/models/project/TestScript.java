package models.project;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import controllers.tree.AbstractTree;
import models.tm.User;
import models.tree.Node;
import play.data.validation.MaxSize;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class TestScript extends ProjectModel implements Node {

    public String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    public User createdBy;

    @MaxSize(5000)
    public String description;

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
