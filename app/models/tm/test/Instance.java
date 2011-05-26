package models.tm.test;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import models.tm.Defect;
import models.tm.Project;
import models.tm.ProjectModel;
import models.tm.TMUser;
import models.tm.approach.TestCycle;
import play.db.jpa.JPABase;
import play.templates.JavaExtensions;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class Instance extends ProjectModel {

    public Instance(Project project) {
        super(project);
    }

    public String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    public Script script;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    public TestCycle testCycle;

    public Integer status;

    @Transient
    public ExecutionStatus executionStatus;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = true)
    public TMUser responsible;

    public Date plannedExecution;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    public List<Tag> tags;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    public List<Defect> defects;


    public String getTagNames() {
        return JavaExtensions.join(tags, ", ");
    }

    /** for export, clumsy but works **/
    public String getExecutionDate() {
        return getNiceDate(plannedExecution);
    }

    public List<InstanceParam> getParams() {
        return InstanceParam.find("from InstanceParam p where p.instance = ?", this).<InstanceParam>fetch();
    }

    public List<Run> getRuns() {
        return Run.find("from Run r where r.instance = ?", this).<Run>fetch();
    }

    public void updateStatus() {
        // instance status = status of the last run
        Run run = Run.find("from Run r where r.instance = ? order by r.executionDate desc", this).<Run>first();
        if (run != null) {
            executionStatus = run.executionStatus;
            save();
        }
    }

    @Override
    public JPABase delete() {
        // remove associated entities
        for(InstanceParam p : getParams()) {
            p.delete();
        }
        for(Run r : getRuns()) {
            r.delete();
        }

        return super.delete();
    }

    @Override
    public boolean create() {
        if(executionStatus != null) {
            this.status = executionStatus.getPosition();
        }
        return super.create();
    }

    @Override
    public JPABase save() {
        if(executionStatus != null) {
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

    public static List<Instance> find(Script script, TestCycle cycle) {
        return Instance.find("from Instance i where i.script = ? and i.testCycle = ?", script, cycle).fetch();
    }


}
