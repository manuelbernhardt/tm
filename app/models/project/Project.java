package models.project;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.general.Account;
import models.general.AccountModel;
import models.general.AccountProduct;
import play.data.validation.MaxSize;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "account_id"})})
public class Project extends AccountModel {

    public String name;

    @MaxSize(5000)
    public String description;

    @ManyToOne
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
