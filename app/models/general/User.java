package models.general;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import models.deadbolt.RoleHolder;
import net.sf.oval.constraint.NotEmpty;
import play.data.validation.Email;
import play.db.jpa.Model;
import play.libs.Crypto;

/**
 * Generic user
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends Model implements RoleHolder {

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    public Account account;

    @NotEmpty
    @Column(nullable = false)
    public String firstName;

    @NotEmpty
    @Column(nullable = false)
    public String lastName;

    @Email
    public String email;

    public String phone;

    private String password;

    @NotEmpty
    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Crypto.passwordHash(password);
    }

    public boolean connect(String password) {
        return this.password.equals(Crypto.passwordHash(password));
    }

    public List<? extends models.deadbolt.Role> getRoles() {
        List<UnitRole> res = new ArrayList<UnitRole>();
        setApplicationRoles(res);
        setAccountRoles(res);
        return res;
    }

    private void setApplicationRoles(List<UnitRole> res) {
        res.add(UnitRole.user());
    }

    private void setAccountRoles(List<UnitRole> res) {
        // TODO
    }
}
