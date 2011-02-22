package models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import play.db.jpa.Model;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@MappedSuperclass
public class CompositeModel extends Model {

    @Column(nullable = false)
    public Long naturalId;

    @PrePersist
    public void prePersist() {
        naturalId = CompositeIdentifierGenerator.getNextNaturalIdentifier(this.getClass());
    }

}
