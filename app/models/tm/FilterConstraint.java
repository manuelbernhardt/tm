package models.tm;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * nikola
 * Date: 5/19/11
 * Time: 5:37 PM
 */
@Entity
public class FilterConstraint extends ProjectModel{

    public String property;
    @Transient
    public ConstraintType type;
    public String value;

    public FilterConstraint(Project project) {
        super(project);
    }
}
