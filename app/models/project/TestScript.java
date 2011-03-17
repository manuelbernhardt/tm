package models.project;

import models.tm.User;
import play.data.validation.MaxSize;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class TestScript extends ProjectModel {

    public String name;

    public User createdBy;

    @MaxSize(5000)
    public String description;

}
