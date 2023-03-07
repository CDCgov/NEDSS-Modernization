package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Data_migration_detail")
public class DataMigrationDetail {
    @EmbeddedId
    private DataMigrationDetailId id;

    @MapsId("dataMigrationBatchUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "data_migration_batch_uid", nullable = false)
    private DataMigrationBatch dataMigrationBatchUid;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "data_migration_record_uid", referencedColumnName = "data_migration_record_uid",
            nullable = false)
    @JoinColumn(name = "data_migration_batch_uid", referencedColumnName = "data_migration_batch_uid", nullable = false)
    private DataMigrationRecord dataMigrationRecord;

    @Column(name = "failed_detail_txt", length = 4000)
    private String failedDetailTxt;

    @Column(name = "failed_reason_txt", length = 300)
    private String failedReasonTxt;

}
