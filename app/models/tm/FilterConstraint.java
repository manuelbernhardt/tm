package models.tm;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

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
}
