package models.project.test;

import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.project.Project;
import models.project.ProjectModel;
import play.data.validation.Required;
import play.db.jpa.JPA;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class Tag extends ProjectModel {

    @Column(nullable = false)
    @Required
    public String name;

    public enum TagType {REQUIREMENT, TESTSCRIPT, TESTINSTANCE, DEFECT}

    public TagType type;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Tag tag = (Tag) o;

        if (!(name.equals(tag.name) && tag.project.id.equals(project.id))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public static List byNamesAndProject(String[] names, Project project) {
        Query query = JPA.em().createQuery("select t from Tag t where t.project = :project and t.name in :names");
        query.setParameter("project", project);
        query.setParameter("names", Arrays.asList(names));
        return query.getResultList();
    }
}
