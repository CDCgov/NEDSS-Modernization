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
public class ObsValueDateId implements Serializable {
    private static final long serialVersionUID = -809276053345219186L;
    @Column(name = "observation_uid", nullable = false)
    private Long observationUid;

    @Column(name = "obs_value_date_seq", nullable = false)
    private Short obsValueDateSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ObsValueDateId entity = (ObsValueDateId) o;
        return Objects.equals(this.obsValueDateSeq, entity.obsValueDateSeq) &&
                Objects.equals(this.observationUid, entity.observationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(obsValueDateSeq, observationUid);
    }

}
