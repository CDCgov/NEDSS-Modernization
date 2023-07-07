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
public class TreatmentAdministeredHistId implements Serializable {
    private static final long serialVersionUID = -1567021867928817572L;
    @Column(name = "treatment_uid", nullable = false)
    private Long treatmentUid;

    @Column(name = "treatment_administered_seq", nullable = false)
    private Short treatmentAdministeredSeq;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        TreatmentAdministeredHistId entity = (TreatmentAdministeredHistId) o;
        return Objects.equals(this.treatmentUid, entity.treatmentUid) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.treatmentAdministeredSeq, entity.treatmentAdministeredSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(treatmentUid, versionCtrlNbr, treatmentAdministeredSeq);
    }

}
