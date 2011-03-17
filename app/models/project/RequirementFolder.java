package models.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import controllers.tree.AbstractTree;
import models.tree.Node;
import play.data.validation.Required;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"project_id", "naturalId"})})
public class RequirementFolder extends ProjectModel implements Node {

    @Column(nullable = false)
    @Required
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
