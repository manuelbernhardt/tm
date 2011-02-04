package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Product extends Model {

    public String name;

}
