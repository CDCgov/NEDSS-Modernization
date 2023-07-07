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
public class TreatmentAdministeredId implements Serializable {
    private static final long serialVersionUID = -5638831059360610939L;
    @Column(name = "treatment_uid", nullable = false)
    private Long treatmentUid;

    @Column(name = "treatment_administered_seq", nullable = false)
    private Short treatmentAdministeredSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        TreatmentAdministeredId entity = (TreatmentAdministeredId) o;
        return Objects.equals(this.treatmentUid, entity.treatmentUid) &&
                Objects.equals(this.treatmentAdministeredSeq, entity.treatmentAdministeredSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(treatmentUid, treatmentAdministeredSeq);
    }

}
