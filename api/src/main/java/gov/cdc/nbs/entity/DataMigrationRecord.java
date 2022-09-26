package gov.cdc.nbs.entity;

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
@Table(name = "Data_migration_record")
public class DataMigrationRecord {
    @EmbeddedId
    private DataMigrationRecordId id;

    @MapsId("dataMigrationBatchUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "data_migration_batch_uid", nullable = false)
    private DataMigrationBatch dataMigrationBatchUid;

    @Column(name = "case_id_txt", length = 100)
    private String caseIdTxt;

    @Column(name = "data_migration_sts", length = 10)
    private String dataMigrationSts;

    @Lob
    @Column(name = "failed_record_txt")
    private String failedRecordTxt;

    @Column(name = "sub_nm", length = 100)
    private String subNm;

}