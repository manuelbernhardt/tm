package models.general;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@MappedSuperclass
public class CompositeModel extends TemporalModel {

    @Column(nullable = false)
    public Long naturalId;

    @PrePersist
    public void prePersist() {
        naturalId = CompositeModelIdentifierGenerator.getNextNaturalIdentifier(this.getClass());
    }

}
