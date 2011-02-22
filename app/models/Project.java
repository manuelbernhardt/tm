package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"naturalId", "account_id"})})
public class Project extends CompositeModel implements AccountModel {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Account account;

    public Account getAccount() {
        return account;
    }

    @ManyToOne
    public AccountProduct product;

    public String name;

    public Project() {
    }

}
