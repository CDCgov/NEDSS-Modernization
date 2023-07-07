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
public class TreatmentProcedureHistId implements Serializable {
    private static final long serialVersionUID = -533883119606233916L;
    @Column(name = "treatment_uid", nullable = false)
    private Long treatmentUid;

    @Column(name = "treatment_procedure_seq", nullable = false)
    private Short treatmentProcedureSeq;

    @Column(name = "version_ctlr_nbr", nullable = false)
    private Short versionCtlrNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        TreatmentProcedureHistId entity = (TreatmentProcedureHistId) o;
        return Objects.equals(this.treatmentUid, entity.treatmentUid) &&
                Objects.equals(this.versionCtlrNbr, entity.versionCtlrNbr) &&
                Objects.equals(this.treatmentProcedureSeq, entity.treatmentProcedureSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(treatmentUid, versionCtlrNbr, treatmentProcedureSeq);
    }

}
