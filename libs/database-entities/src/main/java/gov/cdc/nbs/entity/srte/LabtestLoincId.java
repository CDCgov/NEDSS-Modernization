package gov.cdc.nbs.entity.srte;

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
public class LabtestLoincId implements Serializable {
    private static final long serialVersionUID = -8228574534236978690L;
    @Column(name = "lab_test_cd", nullable = false, length = 20)
    private String labTestCd;

    @Column(name = "laboratory_id", nullable = false, length = 20)
    private String laboratoryId;

    @Column(name = "loinc_cd", nullable = false, length = 20)
    private String loincCd;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        LabtestLoincId entity = (LabtestLoincId) o;
        return Objects.equals(this.laboratoryId, entity.laboratoryId) &&
                Objects.equals(this.loincCd, entity.loincCd) &&
                Objects.equals(this.labTestCd, entity.labTestCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(laboratoryId, loincCd, labTestCd);
    }

}
