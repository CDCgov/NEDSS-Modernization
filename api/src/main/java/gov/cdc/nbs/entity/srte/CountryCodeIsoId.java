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
public class CountryCodeIsoId implements Serializable {
    private static final long serialVersionUID = -3693345843592767566L;
    @Column(name = "code_set_nm", nullable = false, length = 256)
    private String codeSetNm;

    @Column(name = "seq_num", nullable = false)
    private Short seqNum;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        CountryCodeIsoId entity = (CountryCodeIsoId) o;
        return Objects.equals(this.code, entity.code) &&
                Objects.equals(this.seqNum, entity.seqNum) &&
                Objects.equals(this.codeSetNm, entity.codeSetNm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, seqNum, codeSetNm);
    }

}