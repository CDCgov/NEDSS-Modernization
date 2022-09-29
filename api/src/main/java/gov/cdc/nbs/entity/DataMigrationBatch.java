package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Data_migration_batch")
public class DataMigrationBatch {
    @Id
    @Column(name = "data_migration_batch_uid", nullable = false)
    private Long id;

    @Column(name = "batch_nm", length = 300)
    private String batchNm;

    @Column(name = "batch_start_time")
    private Instant batchStartTime;

    @Column(name = "batch_end_time")
    private Instant batchEndTime;

    @Column(name = "records_to_migrate_nbr")
    private Short recordsToMigrateNbr;

    @Column(name = "records_migrated_nbr")
    private Short recordsMigratedNbr;

    @Column(name = "records_failed_nbr")
    private Short recordsFailedNbr;

}