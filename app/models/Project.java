package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"naturalId", "account_id"})})
public class Project extends AccountModel {

    @ManyToOne
    public AccountProduct product;

    public String name;

    public Project() {

    }

    public Project(String name, Account account) {
        this.account = account;
        this.name = name;
    }
}
