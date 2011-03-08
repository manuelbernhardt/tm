package models.general;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import models.deadbolt.RoleHolder;
import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.libs.Crypto;

/**
 * Generic user authentication. Does not extend AccountModel because natural IDs are not necessary.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Auth extends Model implements RoleHolder {

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    public Account account;

    @Column(nullable = false)
    @Required
    public String firstName;

    @Column(nullable = false)
    @Required
    public String lastName;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Email
    @Required
    @Column(nullable = false)
    public String email;

    public String phone;

    @Required
    @Column(nullable = false)
    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // TODO minimal length
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
        res.add(UnitRole.role(UnitRole.USER));
    }

    private void setAccountRoles(List<UnitRole> res) {
        // TODO
    }
}
