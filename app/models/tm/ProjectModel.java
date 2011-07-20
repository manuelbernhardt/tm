package models.tm;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import models.account.Account;
import models.account.AccountEntity;
import models.general.CompositeModel;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@MappedSuperclass
@Filters({@Filter(name = "projects")})
public class ProjectModel extends CompositeModel implements AccountEntity {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Account account;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Project project;

    public boolean isInAccount(Account account) {
        // TODO add logging here
        return this.account.getId().equals(account.getId());
    }

    public ProjectModel(Project project) {
        this.project = project;
        this.account = project.account;
    }
}