package models.tm;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

import models.general.UnitRole;
import org.hibernate.annotations.BatchSize;
import play.data.validation.Required;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"project_id", "naturalId"})}, name = "tm_ProjectRole")
@BatchSize(size = 10)
public class ProjectRole extends ProjectModel {

    @Column(nullable = false)
    @Required
    public String name;

    @ElementCollection
    @CollectionTable(name = "tm_ProjectRole_unitRoles", joinColumns = {
            @JoinColumn(name="tm_ProjectRole_id", referencedColumnName="id")
    })
    @OrderColumn
    public List<String> unitRoles = new ArrayList<String>();

    public ProjectRole(Project project) {
        super(project);
    }

    public List<UnitRole> getAuthenticationUnitRoles() {
        List<UnitRole> res = new ArrayList<UnitRole>();
        for (String r : unitRoles) {
            res.add(new UnitRole(r));
        }
        return res;
    }

    public static List<ProjectRole> findByProject(Long id) {
        return ProjectRole.find("from ProjectRole r where r.project.id = ?", id).fetch();
    }


}
