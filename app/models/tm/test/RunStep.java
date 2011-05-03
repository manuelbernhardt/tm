package models.tm.test;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import util.ParameterHandler;
import models.tm.Project;
import models.tm.ProjectModel;
import play.data.validation.MaxSize;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class RunStep extends ProjectModel {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    public Run run;

    public Integer position;

    public String name;

    @MaxSize(5000)
    public String description;

    @MaxSize(5000)
    public String expectedResult;

    @MaxSize(5000)
    public String actualResult;

    public Integer status;

    public RunStep(Project project) {
        super(project);
    }

    public String getDescriptionHTML() {
        return ParameterHandler.applyClass(description, this.run, ParameterHandler.VIEW_CLASS);
    }

    public String getExpectedResultHTML() {
        return ParameterHandler.applyClass(expectedResult, this.run, ParameterHandler.VIEW_CLASS);
    }

    public String getDescriptionEditableHTML() {
        return ParameterHandler.applyClass(description, this.run, ParameterHandler.EDIT_CLASS);
    }

    public String getExpectedResultEditableHTML() {
        return ParameterHandler.applyClass(expectedResult, this.run, ParameterHandler.EDIT_CLASS);
    }

    @Transient
    public ExecutionStatus executionStatus;

    @PostLoad
    public void doLoad() {
        if (status != null) {
            this.executionStatus = ExecutionStatus.fromPosition(status);
        }
    }

    @PrePersist
    @PreUpdate
    public void doSave() {
        if (executionStatus != null) {
            this.status = executionStatus.getPosition();
        }
    }


}
