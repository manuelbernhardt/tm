package models.account;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import models.general.CompositeModel;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@MappedSuperclass
public class AccountModel extends CompositeModel implements AccountEntity {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Account account;

    public boolean create(Account account) {
        if (!this.account.getId().equals(account.getId())) {
            throw new RuntimeException("Attempting to save an account-bound entity for the wrong account!");
        }
        return super.create();
    }

    @Override
    public boolean create() {
        throw new RuntimeException("You should use create(Account account)");
    }

    public boolean isInAccount(Account account) {
        return this.account.getId().equals(account.getId());
    }
}
