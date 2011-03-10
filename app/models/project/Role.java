package models.project;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.general.UnitRole;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"project_id", "naturalId"})})
public class Role extends ProjectModel {

    public String name;

    @ElementCollection
    public List<String> unitRoles;

    public List<UnitRole> getUnitRoles() {
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
