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
public class ObservationReasonId implements Serializable {
    private static final long serialVersionUID = -5516984347280193183L;
    @Column(name = "observation_uid", nullable = false)
    private Long observationUid;

    @Column(name = "reason_cd", nullable = false, length = 20)
    private String reasonCd;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ObservationReasonId entity = (ObservationReasonId) o;
        return Objects.equals(this.observationUid, entity.observationUid) &&
                Objects.equals(this.reasonCd, entity.reasonCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observationUid, reasonCd);
    }

}