package models.tm;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

import models.account.Account;
import models.deadbolt.RoleHolder;
import models.account.AccountEntity;
import models.account.Auth;
import models.general.TemporalModel;
import models.general.UnitRole;
import play.data.validation.Valid;

/**
 * User for the TM application.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class User extends TemporalModel implements RoleHolder, AccountEntity {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    @Valid
    public Auth authentication;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    public Project activeProject;

    @ElementCollection
    @OrderColumn
    public List<String> accountRoles = new ArrayList<String>();

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

        for(String accountRole : accountRoles) {
            res.add(UnitRole.role(accountRole));
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

    public static List<User> listByAccount(Long accountId) {
        return User.find("from User u where u.authentication.account.id = ?", accountId).fetch();
    }

    public static List<User> listByProject(Long projectId) {
        // TODO cache this together with the caching of getRoles() (I mean, evict the caches together)
        List<User> res = new ArrayList<User>();
        Project project = Project.<Project>findById(projectId);
        if(project == null) {
            return null;
        }
        for(User u : User.listByAccount(project.account.getId())) {
            if(u.getProjects().contains(project)) {
                res.add(u);
            }
        }
        return res;
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