package models;

import play.data.validation.Email;
import play.db.jpa.Model;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class User extends Model {

    public String firstname;
    public String lastname;

    @Email
    public String email;

    public String password;


    public User(String email, String firstname, String lastname, String password) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }
}
