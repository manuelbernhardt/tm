package models.tm;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import models.account.Account;
import models.account.AccountEntity;
import models.general.CompositeModel;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@MappedSuperclass
public class ProjectModel extends CompositeModel implements AccountEntity {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Project project;

    public boolean isInAccount(Account account) {
        // TODO add logging here
        return project.account.getId().equals(account.getId());
    }
}
