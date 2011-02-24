package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Account extends Model {

    public String name;

    public Double credit;

    public String vatCode;

}
