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
@Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"project_id", "naturalId"})})
public class FilterConstraint extends ProjectModel{

    public String property;
    public String type;
    @Transient
    public ConstraintType constraintType;
    public String value;

    public FilterConstraint(Project project) {
        super(project);
    }

    @Override
    public JPABase save() {
        if(!this.type.equals("value"))
            this.type = constraintType.getKey();
        return super.save();
    }

    @PostLoad
    public void doLoad() {
        if(!this.type.equals("value") && !this.type.equals("case"))
            this.constraintType = ConstraintType.valueOf(this.type);
    }
}
