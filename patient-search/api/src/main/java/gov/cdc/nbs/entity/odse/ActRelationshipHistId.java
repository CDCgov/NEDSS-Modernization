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
public class ActRelationshipHistId implements Serializable {
    private static final long serialVersionUID = 3309018125356125662L;
    @Column(name = "source_act_uid", nullable = false)
    private Long sourceActUid;

    @Column(name = "target_act_uid", nullable = false)
    private Long targetActUid;

    @Column(name = "type_cd", nullable = false, length = 50)
    private String typeCd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ActRelationshipHistId entity = (ActRelationshipHistId) o;
        return Objects.equals(this.typeCd, entity.typeCd) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.sourceActUid, entity.sourceActUid) &&
                Objects.equals(this.targetActUid, entity.targetActUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeCd, versionCtrlNbr, sourceActUid, targetActUid);
    }

}