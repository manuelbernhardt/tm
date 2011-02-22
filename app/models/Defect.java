package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"project_id", "naturalId"})})
public class Defect extends CompositeModel implements ProjectModel {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Project project;

    public Project getProject() {
        return project;
    }

    public String title;

    @ManyToOne
    public User assignedTo;

    @ManyToOne
    public User submittedBy;

    @ManyToOne
    public DefectStatus status;
}
