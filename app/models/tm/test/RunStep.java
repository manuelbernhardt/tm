package models.tm.test;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import java.util.regex.Matcher;

import models.tm.Project;
import models.tm.ProjectModel;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.JPABase;
import util.ParameterHandler;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})}, name = "tm_test_RunStep")
public class RunStep extends ProjectModel {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    public Run run;

    public Integer position;

    @Required
    @Column(nullable = false)
    public String name;

    @MaxSize(8000)
    @Column(length = 8000)
    public String description;

    @MaxSize(2000)
    @Column(length = 2000)
    public String expectedResult;

    @MaxSize(2000)
    @Column(length = 2000)
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

    public String getExpectedResultText() {
        return ParameterHandler.getRawParameter(expectedResult, this.run);
    }

    public String getDescriptionEditableHTML() {
        return ParameterHandler.applyClass(description, this.run, ParameterHandler.EDIT_CLASS);
    }

    public String getExpectedResultEditableHTML() {
        return ParameterHandler.applyClass(expectedResult, this.run, ParameterHandler.EDIT_CLASS);
    }

    @Transient
    public ExecutionStatus executionStatus;

    @Override
    public boolean create() {
        if (executionStatus != null) {
            this.status = executionStatus.getPosition();
        }
        return super.create();
    }

    @Override
    public JPABase save() {
        if (executionStatus != null) {
            this.status = executionStatus.getPosition();
        }
        return super.save();
    }

    @PostLoad
    public void doLoad() {
        if (status != null) {
            this.executionStatus = ExecutionStatus.fromPosition(status);
        }
    }

}
