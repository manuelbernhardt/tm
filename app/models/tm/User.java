package models.tm;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import models.deadbolt.RoleHolder;
import models.general.Auth;
import models.general.UnitRole;
import models.project.Project;
import models.project.Role;
import play.db.jpa.Model;

/**
 * User for the TM application.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class User extends Model implements RoleHolder {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, optional = false)
    public Auth authentication;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    public List<Project> projects;

    public Project activeProject;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    public List<Role> projectRoles;

    // TODO cache this, as it is called at each permission check! but evict the cache on Role definition change
    public List<? extends models.deadbolt.Role> getRoles() {
        List<models.deadbolt.Role> res = new ArrayList<models.deadbolt.Role>();

        for (Role r : projectRoles) {
            for (UnitRole ur : r.getUnitRoles()) {
                if (!res.contains(ur)) {
                    res.add(ur);
                }
            }
        }

        return res;
    }
}