package gov.cdc.nbs.entity.srte;

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
public class ElrXrefId implements Serializable {
    private static final long serialVersionUID = -5514531319659572972L;
    @Column(name = "from_code_set_nm", nullable = false, length = 256)
    private String fromCodeSetNm;

    @Column(name = "from_seq_num", nullable = false)
    private Short fromSeqNum;

    @Column(name = "from_code", nullable = false, length = 20)
    private String fromCode;

    @Column(name = "to_code_set_nm", nullable = false, length = 256)
    private String toCodeSetNm;

    @Column(name = "to_seq_num", nullable = false)
    private Short toSeqNum;

    @Column(name = "to_code", nullable = false, length = 20)
    private String toCode;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ElrXrefId entity = (ElrXrefId) o;
        return Objects.equals(this.toSeqNum, entity.toSeqNum) &&
                Objects.equals(this.toCode, entity.toCode) &&
                Objects.equals(this.toCodeSetNm, entity.toCodeSetNm) &&
                Objects.equals(this.fromSeqNum, entity.fromSeqNum) &&
                Objects.equals(this.fromCodeSetNm, entity.fromCodeSetNm) &&
                Objects.equals(this.fromCode, entity.fromCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toSeqNum, toCode, toCodeSetNm, fromSeqNum, fromCodeSetNm, fromCode);
    }

}
