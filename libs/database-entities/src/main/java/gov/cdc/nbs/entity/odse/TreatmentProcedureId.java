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
public class TreatmentProcedureId implements Serializable {
    private static final long serialVersionUID = 8387940135158604641L;
    @Column(name = "treatment_uid", nullable = false)
    private Long treatmentUid;

    @Column(name = "treatment_procedure_seq", nullable = false)
    private Short treatmentProcedureSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        TreatmentProcedureId entity = (TreatmentProcedureId) o;
        return Objects.equals(this.treatmentUid, entity.treatmentUid) &&
                Objects.equals(this.treatmentProcedureSeq, entity.treatmentProcedureSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(treatmentUid, treatmentProcedureSeq);
    }

}
