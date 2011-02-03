package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
public class Defect extends Model {

    public String title;

    @ManyToOne
    public User assignedTo;

    @ManyToOne
    public User submittedBy;

    @OneToOne
    public DefectStatus status;

}
