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
public class ObsValueTxtId implements Serializable {
    private static final long serialVersionUID = -5190269685775953486L;
    @Column(name = "observation_uid", nullable = false)
    private Long observationUid;

    @Column(name = "obs_value_txt_seq", nullable = false)
    private Short obsValueTxtSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ObsValueTxtId entity = (ObsValueTxtId) o;
        return Objects.equals(this.obsValueTxtSeq, entity.obsValueTxtSeq) &&
                Objects.equals(this.observationUid, entity.observationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(obsValueTxtSeq, observationUid);
    }

}
