package gov.cdc.nbs.entity.srte;

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
public class LabResultSnomedId implements Serializable {
    private static final long serialVersionUID = -8569013545954717929L;
    @Column(name = "lab_result_cd", nullable = false, length = 20)
    private String labResultCd;

    @Column(name = "laboratory_id", nullable = false, length = 20)
    private String laboratoryId;

    @Column(name = "snomed_cd", nullable = false, length = 20)
    private String snomedCd;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        LabResultSnomedId entity = (LabResultSnomedId) o;
        return Objects.equals(this.labResultCd, entity.labResultCd) &&
                Objects.equals(this.snomedCd, entity.snomedCd) &&
                Objects.equals(this.laboratoryId, entity.laboratoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labResultCd, snomedCd, laboratoryId);
    }

}