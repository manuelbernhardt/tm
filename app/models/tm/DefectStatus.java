package models.tm;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"project_id", "naturalId"})}, name = "tm_DefectStatus")
public class DefectStatus extends ProjectModel {

    public Integer position;
    public String name;
    public boolean defaultOnCreation;

    public DefectStatus(Project project) {
        super(project);
    }

    public static List<DefectStatus> defectStatuses(){
        return DefectStatus.findAll();
    }

    // TODO later, when we allow users to create their own defect stati, we need to check if there are not more of those
    public static DefectStatus getDefaultDefectStatus() {
        return DefectStatus.find("defaultOnCreation = true").first();
    }

    public String toString(){
        return name;
    }

}
