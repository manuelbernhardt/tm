package models.general;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.Play;
import play.db.jpa.Model;
import play.templates.JavaExtensions;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@MappedSuperclass
public class TemporalModel extends Model {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    public Date updated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, insertable = true, updatable = false, columnDefinition = "TIMESTAMP DEFAULT '0000-00-00 00:00:00'")
    public Date created = new Date();

    protected String getNiceDate(Date d) {
        return JavaExtensions.format(d, Play.configuration.getProperty("date.format"));
    }
}
