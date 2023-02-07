package gov.cdc.nbs.entity.odse;

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
public class ObsValueNumericHistId implements Serializable {
    private static final long serialVersionUID = 4281786284358072426L;
    @Column(name = "observation_uid", nullable = false)
    private Long observationUid;

    @Column(name = "obs_value_numeric_seq", nullable = false)
    private Short obsValueNumericSeq;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ObsValueNumericHistId entity = (ObsValueNumericHistId) o;
        return Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.obsValueNumericSeq, entity.obsValueNumericSeq) &&
                Objects.equals(this.observationUid, entity.observationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionCtrlNbr, obsValueNumericSeq, observationUid);
    }

}