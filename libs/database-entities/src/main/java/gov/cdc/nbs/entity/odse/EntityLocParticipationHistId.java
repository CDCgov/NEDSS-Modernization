package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class EntityLocParticipationHistId implements Serializable {
    private static final long serialVersionUID = 1180730114325976436L;
    @Column(name = "entity_uid", nullable = false)
    private Long entityUid;

    @Column(name = "locator_uid", nullable = false)
    private Long locatorUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        EntityLocParticipationHistId entity = (EntityLocParticipationHistId) o;
        return Objects.equals(this.entityUid, entity.entityUid) &&
                Objects.equals(this.locatorUid, entity.locatorUid) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityUid, locatorUid, versionCtrlNbr);
    }

}
