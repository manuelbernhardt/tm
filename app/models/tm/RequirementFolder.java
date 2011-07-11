package models.tm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.data.validation.Required;
import tree.persistent.Node;
import tree.persistent.NodeName;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"project_id", "naturalId"})}, name = "tm_RequirementFolder")
public class RequirementFolder extends ProjectModel implements Node {

    @Column(nullable = false)
    @Required
    @NodeName
    public String name;

    public RequirementFolder(Project project) {
        super(project);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO
        return super.clone();
    }


}
