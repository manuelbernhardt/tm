package models.project;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.general.UnitRole;
import play.data.validation.Required;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"project_id", "naturalId"})})
public class Role extends ProjectModel {

    @Column(nullable = false)
    @Required
    public String name;

    @ElementCollection
    @OrderColumn
    public List<String> unitRoles = new ArrayList<String>();

    public List<UnitRole> getAuthenticationUnitRoles() {
        List<UnitRole> res = new ArrayList<UnitRole>();
        for(String r : unitRoles) {
            res.add(new UnitRole(r));
        }
        return res;
    }

    public static List<Role> findByProject(Long id) {
        return Role.find("from Role r where r.project.id = ?", id).fetch();
    }

    
}
