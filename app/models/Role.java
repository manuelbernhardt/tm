package models;

import play.data.validation.Email;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Role extends Model {

    public String name;

    @ManyToOne
    public Project project;

    @ElementCollection
    public List<String> unitRoles;

}
