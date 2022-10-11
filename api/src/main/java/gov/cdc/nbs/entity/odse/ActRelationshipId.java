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
public class ActRelationshipId implements Serializable {
    private static final long serialVersionUID = -999273621141625312L;
    @Column(name = "source_act_uid", nullable = false)
    private Long sourceActUid;

    @Column(name = "target_act_uid", nullable = false)
    private Long targetActUid;

    @Column(name = "type_cd", nullable = false, length = 50)
    private String typeCd;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ActRelationshipId entity = (ActRelationshipId) o;
        return Objects.equals(this.typeCd, entity.typeCd) &&
                Objects.equals(this.sourceActUid, entity.sourceActUid) &&
                Objects.equals(this.targetActUid, entity.targetActUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeCd, sourceActUid, targetActUid);
    }

}