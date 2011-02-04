package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Project extends Model {

    @ManyToOne
    public Account account;

    @ManyToOne
    public Product product;

    public String name;

}
