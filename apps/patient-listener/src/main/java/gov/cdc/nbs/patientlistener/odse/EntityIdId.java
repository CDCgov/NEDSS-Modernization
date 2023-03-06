package gov.cdc.nbs.patientlistener.odse;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;


import org.hibernate.Hibernate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class EntityIdId implements Serializable {
    private static final long serialVersionUID = 4848600210434268223L;
    @Column(name = "entity_uid", nullable = false)
    private Long entityUid;

    @Column(name = "entity_id_seq", nullable = false)
    private Short entityIdSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        EntityIdId entity = (EntityIdId) o;
        return Objects.equals(this.entityUid, entity.entityUid) &&
                Objects.equals(this.entityIdSeq, entity.entityIdSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityUid, entityIdSeq);
    }

}