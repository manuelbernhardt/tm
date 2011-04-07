package models.tm.test;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import models.tm.ProjectModel;
import models.tm.User;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class Run extends ProjectModel {

    /** this flag indicates that the Run is being created by the user but was not saved yet **/
    boolean temporary = true;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    public Instance instance;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = true)
    public User tester;

    public Date executionDate;

    public Integer status;

    public List<RunStep> getSteps() {
        return RunStep.find("from RunStep r where r.run = ?", this).<RunStep>fetch();
    }

    public void updateStatus() {
        boolean passed = true;
        for(RunStep step : getSteps()) {
            if(step.executionStatus == ExecutionStatus.FAILED) {
                this.executionStatus = ExecutionStatus.FAILED;
                passed = false;
                break;
            }
            if(step.executionStatus == ExecutionStatus.NOT_COMPLETED || step.executionStatus == ExecutionStatus.NOT_RUN) {
                this.executionStatus = ExecutionStatus.NOT_COMPLETED;
                passed = false;
                break;
            }
        }
        if(passed) {
            this.executionStatus = ExecutionStatus.PASSED;
        }

        // TODO this feels like a Play bug - we should not need to invoke the PreUpdate callback manually
        doSave();
        save();
    }

    @Transient
    public ExecutionStatus executionStatus;

    @PostLoad
    public void doLoad() {
        if (status != null) {
            this.executionStatus = ExecutionStatus.fromPosition(status);
        }
    }

    @PreUpdate
    @PrePersist
    public void doSave() {
        if (executionStatus != null) {
            this.status = executionStatus.getPosition();
        }
    }


}
