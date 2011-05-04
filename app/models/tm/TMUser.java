package models.tm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import controllers.TMController;
import models.account.Account;
import models.account.AccountEntity;
import models.account.User;
import models.general.TemporalModel;
import models.general.UnitRole;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;
import play.data.validation.Valid;
import play.db.jpa.JPA;

/**
 * User for the TM application.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Filters({@Filter(name = "account"), @Filter(name="activeTMUser")})
public class TMUser extends TemporalModel implements AccountEntity {

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    public Account account;

    @Valid
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    public User user;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    public Project activeProject;

    @Temporal(TemporalType.TIMESTAMP)
    public Date sessionExpirationTime;

    @ElementCollection
    @OrderColumn
    public List<String> accountRoles = new ArrayList<String>();

    @ManyToMany(cascade = {CascadeType.REFRESH})
    @BatchSize(size = 10)
    public List<Role> projectRoles;

    public String getFullName() {
        return user.firstName + " " + user.lastName;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    // TODO cache this, as it is called at each permission check! but evict the cache on Role definition change
    public List<? extends models.deadbolt.Role> getRoles() {
        List<models.deadbolt.Role> res = new ArrayList<models.deadbolt.Role>();

        for (String accountRole : accountRoles) {
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
        return user.account.getId().equals(account.getId());
    }

    public static List<TMUser> listByAccount(Long accountId) {
        return TMUser.find("from TMUser u where u.user.account.id = ?", accountId).fetch();
    }

    public static List<TMUser> listByProject(Long projectId) {
        // TODO cache this together with the caching of getRoles() (I mean, evict the caches together)
        List<TMUser> res = new ArrayList<TMUser>();
        Project project = Project.<Project>findById(projectId);
        if (project == null) {
            return null;
        }
        for (TMUser u : TMUser.listByAccount(project.account.getId())) {
            if (u.getProjects().contains(project)) {
                res.add(u);
            }
        }
        return res;
    }

    public List<Project> getProjects() {
        // TODO cache this together with the caching of getRoles() (I mean, evict the caches together)
        List<Project> res = new ArrayList<Project>();
        for (Role r : projectRoles) {
            if (!res.contains(r.project)) {
                res.add(r.project);
            }
        }
        return res;
    }

    public static List<TMUser> listByActiveProject() {
        return TMUser.find("from TMUser u where and u.user.account = ? and exists(from u.projectRoles r where r.project = ?)", TMController.getConnectedUserAccount(), TMController.getActiveProject()).<TMUser>fetch();
    }

    public static List<TMUser> listUsersInProjectRole(Long roleId) {
        return TMUser.find("select u from TMUser u, Role r where r in elements(u.projectRoles) and r.id = ?", roleId).<TMUser>fetch();
    }

    public static List<TMUser> listUsersInAccountRole(AccountRole role) {
        Query query = JPA.em().createQuery("select u from TMUser u join u.accountRoles r where u.user.account = :account and r in (:unitRoles) group by u");
        query.setParameter("account", TMController.getConnectedUserAccount());
        query.setParameter("unitRoles", role.getUnitRoles());
        return query.getResultList();
    }

    public static TMUser findById(Long id) {
        return TMUser.find("from TMUser u where u.id = ?", id).<TMUser>first();
    }
}