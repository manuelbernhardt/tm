package models.general;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * FIXME this could be an embeddable rather than an own table?
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Address extends TemporalModel {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
    public Account account;

    public String street1;

    public String street2;

    public String postalCode;

    public String city;

    public String country;

}
