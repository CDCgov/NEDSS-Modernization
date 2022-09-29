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
public class DataMigrationDetailId implements Serializable {
    private static final long serialVersionUID = 2149896724567521678L;
    @Column(name = "data_migration_detail_uid", nullable = false)
    private Long dataMigrationDetailUid;

    @Column(name = "data_migration_batch_uid", nullable = false)
    private Long dataMigrationBatchUid;

    @Column(name = "data_migration_record_uid", nullable = false)
    private Long dataMigrationRecordUid;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        DataMigrationDetailId entity = (DataMigrationDetailId) o;
        return Objects.equals(this.dataMigrationDetailUid, entity.dataMigrationDetailUid) &&
                Objects.equals(this.dataMigrationRecordUid, entity.dataMigrationRecordUid) &&
                Objects.equals(this.dataMigrationBatchUid, entity.dataMigrationBatchUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataMigrationDetailUid, dataMigrationRecordUid, dataMigrationBatchUid);
    }

}