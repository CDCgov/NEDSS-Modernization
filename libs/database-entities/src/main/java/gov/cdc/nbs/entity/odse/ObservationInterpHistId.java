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
public class ObservationInterpHistId implements Serializable {
    private static final long serialVersionUID = 8544308894860491598L;
    @Column(name = "observation_uid", nullable = false)
    private Long observationUid;

    @Column(name = "interpretation_cd", nullable = false, length = 20)
    private String interpretationCd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Column(name = "interpretation_txt", nullable = false, length = 100)
    private String interpretationTxt;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ObservationInterpHistId entity = (ObservationInterpHistId) o;
        return Objects.equals(this.interpretationCd, entity.interpretationCd) &&
                Objects.equals(this.interpretationTxt, entity.interpretationTxt) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.observationUid, entity.observationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interpretationCd, interpretationTxt, versionCtrlNbr, observationUid);
    }

}
