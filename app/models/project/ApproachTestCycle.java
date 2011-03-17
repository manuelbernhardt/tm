package models.project;

import play.data.validation.MaxSize;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class ApproachTestCycle extends ProjectModel {

    public String name;

    @MaxSize(5000)
    public String description;

    public Date fromDate;

    public Date toDate;

}
