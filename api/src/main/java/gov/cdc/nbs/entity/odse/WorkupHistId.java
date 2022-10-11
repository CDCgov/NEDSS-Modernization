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
public class WorkupHistId implements Serializable {
    private static final long serialVersionUID = -7970300056733547063L;
    @Column(name = "workup_uid", nullable = false)
    private Long workupUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        WorkupHistId entity = (WorkupHistId) o;
        return Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.workupUid, entity.workupUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionCtrlNbr, workupUid);
    }

}