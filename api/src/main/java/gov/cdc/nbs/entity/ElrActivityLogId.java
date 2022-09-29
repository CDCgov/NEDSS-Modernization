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
public class ElrActivityLogId implements Serializable {
    private static final long serialVersionUID = -9182050132743269131L;
    @Column(name = "msg_observation_uid", nullable = false)
    private Long msgObservationUid;

    @Column(name = "elr_activity_log_seq", nullable = false)
    private Short elrActivityLogSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ElrActivityLogId entity = (ElrActivityLogId) o;
        return Objects.equals(this.msgObservationUid, entity.msgObservationUid) &&
                Objects.equals(this.elrActivityLogSeq, entity.elrActivityLogSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgObservationUid, elrActivityLogSeq);
    }

}