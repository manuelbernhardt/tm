package models.tm;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 *  nikola
 * Date: 5/19/11
 * Time: 5:34 PM
 */
@Entity
public class Filter extends ProjectModel{

    public String name;
    
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    public List<FilterConstraint> filterConstraints;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public TMUser owner;
    public String entity;

    public Filter(Project project) {
        super(project);
    }

    public String getName() {
        return name;
    }

    public List<FilterConstraint> getFilterConstraints() {
        return filterConstraints;
    }

    public TMUser getOwner() {
        return owner;
    }

    public String getEntity() {
        return entity;
    }
}
