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
public class NndActivityLogId implements Serializable {
    private static final long serialVersionUID = -7748229521575672453L;
    @Column(name = "nnd_activity_log_uid", nullable = false)
    private Long nndActivityLogUid;

    @Column(name = "nnd_activity_log_seq", nullable = false)
    private Short nndActivityLogSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        NndActivityLogId entity = (NndActivityLogId) o;
        return Objects.equals(this.nndActivityLogSeq, entity.nndActivityLogSeq) &&
                Objects.equals(this.nndActivityLogUid, entity.nndActivityLogUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nndActivityLogSeq, nndActivityLogUid);
    }

}
