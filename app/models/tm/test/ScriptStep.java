package models.tm.test;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import controllers.ParameterHandler;
import models.tm.Project;
import models.tm.ProjectModel;
import play.data.validation.MaxSize;
import play.data.validation.Min;
import play.data.validation.Required;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class ScriptStep extends ProjectModel {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    public Script script;

    @Column(nullable = false)
    @Required
    @Min(0)
    public Integer position;

    @Column(nullable = false)
    @Required
    public String name;

    @MaxSize(5000)
    public String description;

    @MaxSize(5000)
    public String expectedResult;

    public ScriptStep(Project project) {
        super(project);
    }

    public String getDescriptionHTML() {
        return ParameterHandler.applyClass(description, this.script, ParameterHandler.VIEW_CLASS);
    }

    public String getExpectedResultHTML() {
        return ParameterHandler.applyClass(expectedResult, this.script, ParameterHandler.VIEW_CLASS);
    }


}
