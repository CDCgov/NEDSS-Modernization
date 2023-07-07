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
public class StateDefinedFieldDatumId implements Serializable {
    private static final long serialVersionUID = 1506211232440408281L;
    @Column(name = "ldf_uid", nullable = false)
    private Long ldfUid;

    @Column(name = "business_object_uid", nullable = false)
    private Long businessObjectUid;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        StateDefinedFieldDatumId entity = (StateDefinedFieldDatumId) o;
        return Objects.equals(this.ldfUid, entity.ldfUid) &&
                Objects.equals(this.businessObjectUid, entity.businessObjectUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ldfUid, businessObjectUid);
    }

}
