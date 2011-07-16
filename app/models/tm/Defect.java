package models.tm;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.tm.test.Instance;
import models.tm.test.Tag;
import models.tm.test.TagHolder;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.JPA;
import play.db.jpa.JPABase;
import play.templates.JavaExtensions;

/**
 * @author Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"project_id", "naturalId"})}, name = "tm_Defect")
public class Defect extends ProjectModel implements TagHolder {

    @Required
    @Column(nullable = false)
    public String name;

    @MaxSize(8000)
    @Column(length = 8000)
    public String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    public TMUser assignedTo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    public TMUser submittedBy;

    @Required
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    public DefectStatus status;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    public List<Tag> tags;

    public Defect(Project project) {
        super(project);
    }

    @Override
    public JPABase delete() {

        // remove comments
        JPA.em().createQuery("delete from DefectComment dc where dc.defect = :defect").setParameter("defect", this).executeUpdate();

        // unlink test instances
        List<Instance> instances = Instance.find("from Instance i where ? in elements(i.defects)", this).<Instance>fetch();
        for(Instance i : instances) {
            i.defects.remove(this);
            i.save();
        }

        return super.delete();
    }

    public String getTagNames() {
        return JavaExtensions.join(tags, ", ");
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String toString() {
        return this.name;
    }
}
