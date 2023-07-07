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
public class ObservationInterpId implements Serializable {
    private static final long serialVersionUID = -5631069844023508310L;
    @Column(name = "observation_uid", nullable = false)
    private Long observationUid;

    @Column(name = "interpretation_cd", nullable = false, length = 20)
    private String interpretationCd;

    @Column(name = "interpretation_desc_txt", nullable = false, length = 100)
    private String interpretationDescTxt;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ObservationInterpId entity = (ObservationInterpId) o;
        return Objects.equals(this.interpretationDescTxt, entity.interpretationDescTxt) &&
                Objects.equals(this.interpretationCd, entity.interpretationCd) &&
                Objects.equals(this.observationUid, entity.observationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interpretationDescTxt, interpretationCd, observationUid);
    }

}
