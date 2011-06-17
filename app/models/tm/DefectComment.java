package models.tm;

import javax.persistence.*;
import java.util.Date;

import play.data.validation.MaxSize;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"project_id", "naturalId"})})
public class DefectComment extends ProjectModel {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    public Defect defect;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = true)
    public TMUser submittedBy;

    public Date subnittedOn;

    @MaxSize(2000)
    @Column(length = 2000)
    public String comment;

}
