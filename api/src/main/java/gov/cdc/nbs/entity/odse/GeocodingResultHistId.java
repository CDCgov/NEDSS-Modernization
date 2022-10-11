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
public class GeocodingResultHistId implements Serializable {
    private static final long serialVersionUID = -7374111254805553318L;
    @Column(name = "geocoding_result_uid", nullable = false)
    private Long geocodingResultUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        GeocodingResultHistId entity = (GeocodingResultHistId) o;
        return Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.geocodingResultUid, entity.geocodingResultUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionCtrlNbr, geocodingResultUid);
    }

}