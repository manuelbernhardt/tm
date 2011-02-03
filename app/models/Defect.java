package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
public class Defect extends Model {

    public String title;
    public User assignedTo;
    public User submittedBy;
    public DefectStatus status;

}
