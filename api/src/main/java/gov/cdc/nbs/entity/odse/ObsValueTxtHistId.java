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
public class ObsValueTxtHistId implements Serializable {
    private static final long serialVersionUID = -1218911114145533466L;
    @Column(name = "observation_uid", nullable = false)
    private Long observationUid;

    @Column(name = "obs_value_txt_seq", nullable = false)
    private Short obsValueTxtSeq;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ObsValueTxtHistId entity = (ObsValueTxtHistId) o;
        return Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.obsValueTxtSeq, entity.obsValueTxtSeq) &&
                Objects.equals(this.observationUid, entity.observationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionCtrlNbr, obsValueTxtSeq, observationUid);
    }

}