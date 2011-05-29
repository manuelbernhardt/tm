package models.tm;

import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import play.db.jpa.JPABase;

/**
 * nikola
 * Date: 5/19/11
 * Time: 5:37 PM
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"project_id", "naturalId"})})
public class FilterConstraint extends ProjectModel {

    public String property;
    public String type;
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
