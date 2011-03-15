package models.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.data.validation.Required;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"project_id", "naturalId"})})
public class RequirementFolder extends ProjectModel {

    @Column(nullable = false)
    @Required
    public String name;

}
