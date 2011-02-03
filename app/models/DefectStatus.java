package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
public class DefectStatus extends Model {

    public Integer Position;
    public String Status;

}
