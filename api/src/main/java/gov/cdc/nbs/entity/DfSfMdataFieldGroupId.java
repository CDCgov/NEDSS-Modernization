package gov.cdc.nbs.entity;

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
public class DfSfMdataFieldGroupId implements Serializable {
    private static final long serialVersionUID = -8006842914999371282L;
    @Column(name = "df_sf_metadata_group_uid", nullable = false)
    private Long dfSfMetadataGroupUid;

    @Column(name = "field_uid", nullable = false)
    private Long fieldUid;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        DfSfMdataFieldGroupId entity = (DfSfMdataFieldGroupId) o;
        return Objects.equals(this.dfSfMetadataGroupUid, entity.dfSfMetadataGroupUid) &&
                Objects.equals(this.fieldUid, entity.fieldUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dfSfMetadataGroupUid, fieldUid);
    }

}