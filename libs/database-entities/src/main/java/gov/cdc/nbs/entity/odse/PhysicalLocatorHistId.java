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
public class PhysicalLocatorHistId implements Serializable {
    private static final long serialVersionUID = 638968017268227544L;
    @Column(name = "physical_locator_uid", nullable = false)
    private Long physicalLocatorUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        PhysicalLocatorHistId entity = (PhysicalLocatorHistId) o;
        return Objects.equals(this.physicalLocatorUid, entity.physicalLocatorUid) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(physicalLocatorUid, versionCtrlNbr);
    }

}
