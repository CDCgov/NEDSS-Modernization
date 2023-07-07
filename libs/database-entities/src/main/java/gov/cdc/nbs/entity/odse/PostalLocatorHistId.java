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
public class PostalLocatorHistId implements Serializable {
    private static final long serialVersionUID = -1464585045511526743L;
    @Column(name = "postal_locator_uid", nullable = false)
    private Long postalLocatorUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        PostalLocatorHistId entity = (PostalLocatorHistId) o;
        return Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.postalLocatorUid, entity.postalLocatorUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionCtrlNbr, postalLocatorUid);
    }

}
