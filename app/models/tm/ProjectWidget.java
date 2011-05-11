package models.tm;

import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.db.jpa.JPABase;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "id", columnNames = {"project_id", "naturalId"})})
public class ProjectWidget extends ProjectModel implements Widget {

    public String title;
    public String widgetIdentifier;
    public String column;
    public boolean open;
    public Integer type;
    public transient WidgetType widgetType;
    public boolean publicWidget = true;

    @ElementCollection
    public Map<String, String> parameters;

    public ProjectWidget(Project project) {
        super(project);
    }

    public String getWidgetIdentifier() {
        return widgetIdentifier;
    }

    public String getTitle() {
        return title;
    }

    public String getColumn() {
        return column;
    }

    public boolean isOpen() {
        return open;
    }

    public WidgetType getType() {
        return widgetType;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public boolean create() {
        this.type = this.widgetType.getKey();
        return super.create();
    }

    @Override
    public <T extends JPABase> T save() {
        this.type = this.widgetType.getKey();
        return super.save();
    }

    @PostLoad
    public void doLoad() {
        this.widgetType = WidgetType.fromKey(this.type);
    }

}
