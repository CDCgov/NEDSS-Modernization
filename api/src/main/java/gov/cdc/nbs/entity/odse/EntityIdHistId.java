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
public class EntityIdHistId implements Serializable {
    private static final long serialVersionUID = 4400948625133030053L;
    @Column(name = "entity_uid", nullable = false)
    private Long entityUid;

    @Column(name = "entity_id_seq", nullable = false)
    private Short entityIdSeq;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        EntityIdHistId entity = (EntityIdHistId) o;
        return Objects.equals(this.entityUid, entity.entityUid) &&
                Objects.equals(this.entityIdSeq, entity.entityIdSeq) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityUid, entityIdSeq, versionCtrlNbr);
    }

}