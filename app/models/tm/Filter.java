package models.tm;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.data.validation.Required;

/**
 *  nikola
 * Date: 5/19/11
 * Time: 5:34 PM
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"project_id", "naturalId"})})
public class Filter extends ProjectModel{

    @Required
    @Column(nullable = false)
    public String name;
    
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    public List<FilterConstraint> filterConstraints;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public TMUser owner;

    /** for per-entity filters, may be nullable in case of multi-entity filters **/
    @Column(nullable = true)
    public String entity;

    public Filter(Project project) {
        super(project);
    }
}
