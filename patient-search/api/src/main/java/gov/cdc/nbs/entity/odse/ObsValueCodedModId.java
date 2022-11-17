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
public class ObsValueCodedModId implements Serializable {
    private static final long serialVersionUID = -2243447834608368926L;
    @Column(name = "observation_uid", nullable = false)
    private Long observationUid;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "code_mod_cd", nullable = false, length = 20)
    private String codeModCd;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ObsValueCodedModId entity = (ObsValueCodedModId) o;
        return Objects.equals(this.code, entity.code) &&
                Objects.equals(this.codeModCd, entity.codeModCd) &&
                Objects.equals(this.observationUid, entity.observationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, codeModCd, observationUid);
    }

}