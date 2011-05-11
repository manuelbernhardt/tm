package models.tm;

import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyClass;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
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

    @Column(name = "widgetColumn")
    public String column;

    public boolean open;

    // this damn thing is called wType instead of type because hibernate has apparently a problem with fields named type....
    public String wType;

    @Transient
    public WidgetType widgetType;

    public boolean publicWidget;

    @ElementCollection(targetClass = String.class)
    @MapKeyClass(String.class)
    public Map<String, Object> parameters;

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

    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public boolean create() {
        if (this.wType == null) {
            this.wType = this.widgetType.getKey();
        }
        return super.create();
    }

    @Override
    public <T extends JPABase> T save() {
        if (this.wType == null) {
            this.wType = this.widgetType.getKey();
        }
        return super.save();
    }

    @PostLoad
    public void doLoad() {
        this.widgetType = WidgetType.fromKey(wType);
    }

}
