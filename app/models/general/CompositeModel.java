package models.general;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Filter;

/**
 * A composite model, which has a "natural" identifier. The naturalIdentifier mechanism is implemented by means of MySQL triggers.
 *
 * @see models.SchemaCreation
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

@MappedSuperclass
@Filter(name = "account")
public class CompositeModel extends TemporalModel {

    @Column(insertable = false, updatable = false, columnDefinition = "bigint(20) NOT NULL DEFAULT -1")
    public Long naturalId;

}
