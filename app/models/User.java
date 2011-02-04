package models;

import play.data.validation.Email;
import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class User extends Model {

    public String firstName;
    public String lastName;

    @Email
    public String email;

    public String password;


    public User(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}
