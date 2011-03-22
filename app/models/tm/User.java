package models.tm;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import models.deadbolt.RoleHolder;
import models.general.Account;
import models.general.AccountEntity;
import models.general.Auth;
import models.general.UnitRole;
import models.project.Project;
import models.project.Role;
import play.data.validation.Valid;
import play.db.jpa.Model;

/**
 * User for the TM application.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class User extends Model implements RoleHolder, AccountEntity {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    @Valid
    public Auth authentication;

    public boolean isApplicationAdmin;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    public Project activeProject;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    public List<Role> projectRoles;

    public String getFullName() {
        return authentication.firstName + " " + authentication.lastName;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    // TODO cache this, as it is called at each permission check! but evict the cache on Role definition change
    public List<? extends models.deadbolt.Role> getRoles() {
        List<models.deadbolt.Role> res = new ArrayList<models.deadbolt.Role>();

        if(isApplicationAdmin) {
            res.add(UnitRole.role(UnitRole.ADMIN));
        }

        for (Role r : projectRoles) {
            for (UnitRole ur : r.getAuthenticationUnitRoles()) {
                if (!res.contains(ur)) {
                    res.add(ur);
                }
            }
        }

        return res;
    }

    public boolean isInAccount(Account account) {
        return authentication.account.getId().equals(account.getId());
    }

    public static List<User> findByAccount(Long accountId) {
        return User.find("from User u where u.authentication.account.id = ?", accountId).fetch();
    }

    public List<Project> getProjects() {
        // TODO cache this together with the caching of getRoles() (I mean, evict the caches together)
        List<Project> res = new ArrayList<Project>();
        for(Role r : projectRoles) {
            if(!res.contains(r.project)) {
                res.add(r.project);
            }
        }
        return res;
    }
}