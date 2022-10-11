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
public class CodesetId implements Serializable {
    private static final long serialVersionUID = 3612746303723060159L;
    @Column(name = "class_cd", nullable = false, length = 30)
    private String classCd;

    @Column(name = "code_set_nm", nullable = false, length = 256)
    private String codeSetNm;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        CodesetId entity = (CodesetId) o;
        return Objects.equals(this.codeSetNm, entity.codeSetNm) &&
                Objects.equals(this.classCd, entity.classCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeSetNm, classCd);
    }

}