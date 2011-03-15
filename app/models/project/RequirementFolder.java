package models.project;

import play.data.validation.Required;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
public class RequirementFolder extends ProjectModel {

    @Column(nullable = false)
    @Required
    public String name;

}
