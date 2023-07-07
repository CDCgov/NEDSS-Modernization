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
public class ManufacturedMaterialId implements Serializable {
    private static final long serialVersionUID = 7390196943489408322L;
    @Column(name = "material_uid", nullable = false)
    private Long materialUid;

    @Column(name = "manufactured_material_seq", nullable = false)
    private Short manufacturedMaterialSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ManufacturedMaterialId entity = (ManufacturedMaterialId) o;
        return Objects.equals(this.materialUid, entity.materialUid) &&
                Objects.equals(this.manufacturedMaterialSeq, entity.manufacturedMaterialSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materialUid, manufacturedMaterialSeq);
    }

}
