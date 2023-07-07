package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Deduplication_activity_log")
public class DeduplicationActivityLog {
    @Id
    @Column(name = "deduplication_activity_log_uid", nullable = false)
    private Long id;

    @Column(name = "batch_start_time")
    private Instant batchStartTime;

    @Column(name = "batch_end_time")
    private Instant batchEndTime;

    @Column(name = "merged_records_identified_nbr")
    private Short mergedRecordsIdentifiedNbr;

    @Column(name = "merged_records_survived_nbr")
    private Short mergedRecordsSurvivedNbr;

    @Column(name = "override_ind")
    private Character overrideInd;

    @Column(name = "similar_group_nbr")
    private Integer similarGroupNbr;

    @Column(name = "process_type", length = 50)
    private String processType;

    @Column(name = "process_exception", length = 2000)
    private String processException;

}
