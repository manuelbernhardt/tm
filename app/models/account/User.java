package models.account;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.deadbolt.Role;
import models.deadbolt.RoleHolder;
import models.general.UnitRole;
import org.hibernate.annotations.Filter;
import play.data.binding.NoBinding;
import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.libs.Crypto;

/**
 * Generic user.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "account_id"})})
@Filter(name = "activeUser")
public class User extends AccountModel implements RoleHolder {

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
        return new StringBuilder().append(firstName).append(" ").append(lastName).toString();
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

    public List<? extends Role> getRoles() {
        List<UnitRole> res = new ArrayList<UnitRole>();
        setAccountRoles(res);
        return res;
    }

    private void setAccountRoles(List<UnitRole> res) {
        // there are no cross-product account roles yet.
    }

    public String getDebugString() {
        return new StringBuilder().append(getFullName()).append(", id ").append(id).append(", account ").append(account.name).toString();
    }
}
