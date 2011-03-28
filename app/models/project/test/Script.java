package models.project.test;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.project.ProjectModel;
import models.tm.User;
import play.data.validation.MaxSize;
import tree.persistent.Node;
import tree.persistent.NodeName;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class Script extends ProjectModel implements Node {

    @NodeName
    public String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    public User createdBy;

    @MaxSize(5000)
    public String description;

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO
        return super.clone();
    }

    public List<ScriptStep> getSteps() {
        return ScriptStep.find("from ScriptStep step where step.script = ?", this).<ScriptStep>fetch();
    }

    public List<ScriptParam> getParams() {
        return ScriptParam.find("from ScriptParam param where param.script = ?, this").<ScriptParam>fetch();
    }
}
