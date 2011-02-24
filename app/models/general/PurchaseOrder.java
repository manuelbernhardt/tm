package models.general;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class PurchaseOrder extends Model {

    @ManyToOne
    public AccountProduct product;

    public Integer seats;

    public Date orderDate;

    public Date dateFrom;

    public Date dateTo;

    public Double pricePerSeatMonth;

}
