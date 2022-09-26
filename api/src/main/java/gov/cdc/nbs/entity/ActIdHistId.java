package gov.cdc.nbs.entity;

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
public class ActIdHistId implements Serializable {
    private static final long serialVersionUID = 8490780434273145327L;
    @Column(name = "act_uid", nullable = false)
    private Long actUid;

    @Column(name = "act_id_seq", nullable = false)
    private Short actIdSeq;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ActIdHistId entity = (ActIdHistId) o;
        return Objects.equals(this.actUid, entity.actUid) &&
                Objects.equals(this.actIdSeq, entity.actIdSeq) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actUid, actIdSeq, versionCtrlNbr);
    }

}