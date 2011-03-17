package models.project;

import play.data.validation.MaxSize;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class TestScriptStep extends ProjectModel {

    public TestScript testScript;

    public Integer position;

    @MaxSize(5000)
    public String description;

    @MaxSize(5000)
    public String expectedResult;

}
