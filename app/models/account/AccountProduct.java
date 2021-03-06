package models.account;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.general.Product;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"naturalId", "account_id"})}, name="account_AccountProduct")
public class AccountProduct extends AccountModel {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false, fetch = FetchType.LAZY)
    public Product product;

    public Boolean active;

    public Double pricePerSeatMonth;

    public AccountProduct(Account account) {
        super(account);
    }

}
