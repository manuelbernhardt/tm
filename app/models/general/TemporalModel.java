package models.general;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

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
	public Date created;

	@PrePersist
	protected void onCreate() {
		created = new Date();
	}
}
