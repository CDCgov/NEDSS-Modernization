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
public class CdfSubformImportDataLogId implements Serializable {
    private static final long serialVersionUID = -7601482563214637335L;
    @Column(name = "import_log_uid", nullable = false)
    private Long importLogUid;

    @Column(name = "data_oid", nullable = false, length = 50)
    private String dataOid;

    @Column(name = "data_type", nullable = false, length = 20)
    private String dataType;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        CdfSubformImportDataLogId entity = (CdfSubformImportDataLogId) o;
        return Objects.equals(this.importLogUid, entity.importLogUid) &&
                Objects.equals(this.dataOid, entity.dataOid) &&
                Objects.equals(this.dataType, entity.dataType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(importLogUid, dataOid, dataType);
    }

}