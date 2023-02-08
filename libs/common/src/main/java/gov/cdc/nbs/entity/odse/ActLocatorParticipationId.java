package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ActLocatorParticipationId implements Serializable {
    private static final long serialVersionUID = 6477826606920064348L;
    @Column(name = "entity_uid", nullable = false)
    private Long entityUid;

    @Column(name = "locator_uid", nullable = false)
    private Long locatorUid;

    @Column(name = "act_uid", nullable = false)
    private Long actUid;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ActLocatorParticipationId entity = (ActLocatorParticipationId) o;
        return Objects.equals(this.actUid, entity.actUid) &&
                Objects.equals(this.entityUid, entity.entityUid) &&
                Objects.equals(this.locatorUid, entity.locatorUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actUid, entityUid, locatorUid);
    }

}