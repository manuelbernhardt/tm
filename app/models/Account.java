package models;

import play.data.validation.Email;
import play.db.jpa.Model;
import sun.security.krb5.Realm;

import javax.persistence.Entity;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Account extends Model {

    public String name;

    public Double credit;

    public String VATcode;

}
