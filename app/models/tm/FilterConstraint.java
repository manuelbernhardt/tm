package models.tm;

import play.db.jpa.JPABase;

import javax.persistence.*;
import java.util.ArrayList;

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

    public FilterConstraint(String property, String type, String value){
        this.property = property;
        this.type = type;
        this.value = value;
    }

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
