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
public class DtpropertyId implements Serializable {
    private static final long serialVersionUID = 6879629491967050719L;
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "property", nullable = false, length = 64)
    private String property;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        DtpropertyId entity = (DtpropertyId) o;
        return Objects.equals(this.property, entity.property) &&
                Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(property, id);
    }

}