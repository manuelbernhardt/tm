package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Address extends Model {

    @ManyToOne
    public Account account;

    public String street1;

    public String street2;

    public String postalCode;

    public String country;

}
