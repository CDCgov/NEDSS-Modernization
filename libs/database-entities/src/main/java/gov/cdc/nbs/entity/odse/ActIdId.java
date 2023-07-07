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
public class ActIdId implements Serializable {
    private static final long serialVersionUID = -3235932436005538820L;
    @Column(name = "act_uid", nullable = false)
    private Long actUid;

    @Column(name = "act_id_seq", nullable = false)
    private Short actIdSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ActIdId entity = (ActIdId) o;
        return Objects.equals(this.actUid, entity.actUid) &&
                Objects.equals(this.actIdSeq, entity.actIdSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actUid, actIdSeq);
    }

}
