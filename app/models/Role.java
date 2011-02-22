package models;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

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
