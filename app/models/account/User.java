package models.account;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import models.deadbolt.RoleHolder;
import models.general.TemporalModel;
import models.general.UnitRole;
import play.data.binding.NoBinding;
import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.libs.Crypto;

/**
 * Generic user. Does not extend AccountModel because natural IDs are not necessary.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class User extends TemporalModel implements RoleHolder {

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    public Account account;

    @Required
    @Column(nullable = false)
    public String firstName;

    @Required
    @Column(nullable = false)
    public String lastName;

    @Email
    @Required
    @Column(nullable = false)
    public String email;

    public String phone;

    @Required
    @Password
    @Column(nullable = false)
    public String password;

    @NoBinding
    public Boolean active = true;

    public String getFullName() {
        return firstName + " " + lastName;
    }

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
        setAccountRoles(res);
        return res;
    }

    private void setAccountRoles(List<UnitRole> res) {
        // TODO
    }
}
