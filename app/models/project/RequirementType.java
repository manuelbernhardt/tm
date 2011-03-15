package models.project;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"project_id", "naturalId"})})
public class RequirementType extends ProjectModel {

    public Integer position;
    public String type;

}
