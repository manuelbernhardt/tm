package models.project.test;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.project.ProjectModel;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class StatusExecution extends ProjectModel {

    public Integer position;
    public String name;

}
