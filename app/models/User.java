package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import models.deadbolt.RoleHolder;
import net.sf.oval.constraint.NotEmpty;
import play.data.validation.Email;
import play.db.jpa.Model;
import play.libs.Crypto;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class User extends Model implements RoleHolder {

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    public Account account;

    @NotEmpty
    public String firstName;

    @NotEmpty
    public String lastName;

    @Email
    public String email;

    public String phone;

    private String password;

    @NotEmpty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Crypto.passwordHash(password);
    }

    @ManyToMany
    public List<Project> project;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    public List<Role> projectRoles;

    public boolean connect(String password) {
        return this.password.equals(Crypto.passwordHash(password));
    }

    // TODO cache this, as it is called at each permission check! but evict the cache on Role definition change
    public List<? extends models.deadbolt.Role> getRoles() {
        List<UnitRole> res = new ArrayList<UnitRole>();
        setApplicationRoles(res);
        setAccountRoles(res);
        setProjectRoles(res);
        return res;
    }

    private void setApplicationRoles(List<UnitRole> res) {
        // first off, everyone is a user
        res.add(UnitRole.user());
    }

    private void setAccountRoles(List<UnitRole> res) {
        // TODO
    }

    private void setProjectRoles(List<UnitRole> res) {
        for (Role r : projectRoles) {
            for (UnitRole ur : r.getUnitRoles()) {
                if (!res.contains(ur)) {
                    res.add(ur);
                }
            }
        }
    }
}
