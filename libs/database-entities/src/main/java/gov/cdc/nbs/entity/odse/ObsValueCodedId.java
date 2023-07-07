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
public class ObsValueCodedId implements Serializable {
    private static final long serialVersionUID = 7553021853109481071L;
    @Column(name = "observation_uid", nullable = false)
    private Long observationUid;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ObsValueCodedId entity = (ObsValueCodedId) o;
        return Objects.equals(this.code, entity.code) &&
                Objects.equals(this.observationUid, entity.observationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, observationUid);
    }

}
