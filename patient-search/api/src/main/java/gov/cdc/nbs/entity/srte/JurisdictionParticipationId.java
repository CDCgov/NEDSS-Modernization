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
public class JurisdictionParticipationId implements Serializable {
    private static final long serialVersionUID = 2417544729818215597L;
    @Column(name = "jurisdiction_cd", nullable = false, length = 20)
    private String jurisdictionCd;

    @Column(name = "fips_cd", nullable = false, length = 20)
    private String fipsCd;

    @Column(name = "type_cd", nullable = false, length = 20)
    private String typeCd;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        JurisdictionParticipationId entity = (JurisdictionParticipationId) o;
        return Objects.equals(this.typeCd, entity.typeCd) &&
                Objects.equals(this.jurisdictionCd, entity.jurisdictionCd) &&
                Objects.equals(this.fipsCd, entity.fipsCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeCd, jurisdictionCd, fipsCd);
    }

}