package models.tm.test;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.tm.Project;
import models.tm.ProjectModel;
import models.tm.TMUser;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import tree.persistent.Node;
import tree.persistent.NodeName;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"naturalId", "project_id"})})
public class Script extends ProjectModel implements Node, ParameterHolder {

    @NodeName
    @Required
    @Column(nullable = false)
    public String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    public TMUser createdBy;

    @MaxSize(5000)
    public String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    public List<Tag> tags;

    public Script(Project project) {
        super(project);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO
        return super.clone();
    }

    public List<ScriptStep> getSteps() {
        return ScriptStep.find("from ScriptStep step where step.script = ?", this).<ScriptStep>fetch();
    }

    public List<Instance> getInstances() {
        return Instance.find("from Instance i where i.script = ?", this).<Instance>fetch();
    }

    public List<ScriptParam> getParams() {
        return ScriptParam.find("from ScriptParam param where param.script = ?", this).<ScriptParam>fetch();
    }

    public ScriptParam getParam(String name) {
        return ScriptParam.find("from ScriptParam p where p.script = ? and p.name = ?", this, name).<ScriptParam>first();
    }

}
