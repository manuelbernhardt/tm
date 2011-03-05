package models.general;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import play.db.jpa.JPABase;

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@MappedSuperclass
public class AccountModel extends CompositeModel {


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Account account;

    public void save(Account account) {
        if(!this.account.getId().equals(account.getId())) {
            throw new RuntimeException("Attempting to save an account-bound entity for the wrong account!");
        }
        super.save();
    }

    @Override
    public <T extends JPABase> T save() {
        throw new RuntimeException("You should use save(Account account)");
    }
}
