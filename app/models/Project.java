package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Project extends Model {

    public Account account;

    public Product product;

    public String name;

}
