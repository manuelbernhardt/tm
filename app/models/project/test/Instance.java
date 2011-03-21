package models.project.test;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.project.ProjectModel;
import models.project.approach.TestCycle;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class Instance extends ProjectModel {

    public String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    public Script script;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    public TestCycle testCycle;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    public StatusExecution executionStatus;

    public static List<Instance> find(Script script, TestCycle cycle) {
        return Instance.find("from Instance i where i.script = ? and i.testCycle = ?", script, cycle).fetch();
    }

}
