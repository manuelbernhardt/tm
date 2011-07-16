package models.tm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import play.db.jpa.JPABase;

/**
 * @author Nikola
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"project_id", "naturalId"})}, name = "tm_FilterConstraint")
public class FilterConstraint extends ProjectModel {

    @Column(nullable = false)
    public String entity;

    @Column(nullable = false)
    public String property;

    @Column(nullable = false)
    public String type;

    @Column
    public String groupId;

    @Transient
    public ConstraintType constraintType;

    public String value;

    public FilterConstraint(Project project) {
        super(project);
    }

    @Override
    public boolean create() {
        if (constraintType != null) {
            this.type = constraintType.getKey();
        }
        return super.create();
    }

    @Override
    public JPABase save() {
        if (constraintType != null) {
            this.type = constraintType.getKey();
        }
        return super.save();
    }

    @PostLoad
    public void doLoad() {
        this.constraintType = ConstraintType.fromKey(this.type);
    }
}
