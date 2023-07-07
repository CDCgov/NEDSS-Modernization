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
public class DataMigrationRecordId implements Serializable {
    private static final long serialVersionUID = -4780516538803304081L;
    @Column(name = "data_migration_record_uid", nullable = false)
    private Long dataMigrationRecordUid;

    @Column(name = "data_migration_batch_uid", nullable = false)
    private Long dataMigrationBatchUid;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        DataMigrationRecordId entity = (DataMigrationRecordId) o;
        return Objects.equals(this.dataMigrationRecordUid, entity.dataMigrationRecordUid) &&
                Objects.equals(this.dataMigrationBatchUid, entity.dataMigrationBatchUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataMigrationRecordUid, dataMigrationBatchUid);
    }

}
