package models.project;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class TestStatusExecution extends ProjectModel {

    public Integer position;
    public String status;

}
