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
public class StateDefinedFieldDataHistId implements Serializable {
    private static final long serialVersionUID = -5270716412761141941L;
    @Column(name = "ldf_uid", nullable = false)
    private Long ldfUid;

    @Column(name = "business_object_uid", nullable = false)
    private Long businessObjectUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        StateDefinedFieldDataHistId entity = (StateDefinedFieldDataHistId) o;
        return Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.ldfUid, entity.ldfUid) &&
                Objects.equals(this.businessObjectUid, entity.businessObjectUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionCtrlNbr, ldfUid, businessObjectUid);
    }

}
