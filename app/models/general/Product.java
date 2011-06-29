package models.general;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(name = "general_Product")
public class Product extends TemporalModel {

    public String name;

}
