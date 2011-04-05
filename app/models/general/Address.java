package models.general;

import javax.persistence.Embeddable;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Embeddable
public class Address {

    public String street1;

    public String street2;

    public String postalCode;

    public String city;

    public String country;

}
