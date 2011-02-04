package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Order extends Model {

    @ManyToOne
    public Account account;

    @ManyToOne
    public Product product;

    public Integer seats;

    public Date orderDate;

    public Date dateFrom;

    public Date dateTo;

    public Double pricePerSeatMonth;

}
