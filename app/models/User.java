package models;

import play.data.validation.Email;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class User extends Model {

    @ManyToOne
    public Account account;

    public String firstName;
    public String lastName;

    @Email
    public String email;

    public String phone;

    public String password;

    @ManyToMany
    public List<Project> project;

}
