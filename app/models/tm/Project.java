package models.tm;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.account.Account;
import models.account.AccountModel;
import models.account.AccountProduct;
import org.hibernate.annotations.BatchSize;
import play.data.validation.MaxSize;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "account_id"})})
@BatchSize(size = 10)
public class Project extends AccountModel {

    public String name;

    @MaxSize(5000)
    public String description;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = true, fetch = FetchType.LAZY)
    public AccountProduct product;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
    public ProjectCategory projectCategory;

    public Integer reservedSeats;

    public Integer maxAvailableSeats;
    
    public Project() {

    }

    public Project(String name, Account account) {
        this.account = account;
        this.name = name;
    }
}
