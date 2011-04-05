package models.general;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import play.data.validation.Email;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Account extends TemporalModel {

    public String name;

    public String contactFirstName;

    public String contactLastName;

    @Email
    public String email;

    public String phone;

    @Embedded
    public Address address;

    public Double credit;

    public String vatCode;

}
