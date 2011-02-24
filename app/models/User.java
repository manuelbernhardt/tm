package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import models.deadbolt.RoleHolder;
import play.data.validation.Email;
import play.db.jpa.Model;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class User extends Model implements RoleHolder {

    @ManyToOne
    public Account account;

    public String firstName;
    public String lastName;

    @Email
    public String email;

    public String phone;

    public String password;

    @ManyToMany
    public List<Project> project;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    public List<Role> roles;

    // TODO cache this, as it is called at each permission check!
    // but evict the cache on Role definition change
    public List<? extends models.deadbolt.Role> getRoles() {
        List<UnitRole> res = new ArrayList<UnitRole>();

        // application roles

        // first off, everyone is a user
        res.add(new UnitRole("user"));

        // next, we have to somehow fetch whether this user is an admin for an account
        // TODO

        // project-specific roles
        for(Role r : roles) {
            for(UnitRole ur : r.getUnitRoles()) {
                if(!res.contains(ur)) {
                    res.add(ur);
                }
            }
        }

        return res;
    }
}
