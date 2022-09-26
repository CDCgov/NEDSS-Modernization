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
@Table(name = "Geocoding_activity_log")
public class GeocodingActivityLog {
    @Id
    @Column(name = "geocoding_activity_log_uid", nullable = false)
    private Long id;

    @Column(name = "batch_run_mode", length = 1)
    private String batchRunMode;

    @Column(name = "batch_start_time")
    private Instant batchStartTime;

    @Column(name = "batch_end_time")
    private Instant batchEndTime;

    @Column(name = "completed_ind", length = 1)
    private String completedInd;

    @Column(name = "completion_reason", length = 1000)
    private String completionReason;

    @Column(name = "total_nbr")
    private Integer totalNbr;

    @Column(name = "single_match_nbr")
    private Integer singleMatchNbr;

    @Column(name = "multi_match_nbr")
    private Integer multiMatchNbr;

    @Column(name = "zero_match_nbr")
    private Integer zeroMatchNbr;

    @Column(name = "error_record_nbr")
    private Integer errorRecordNbr;

    @Column(name = "error_nbr")
    private Integer errorNbr;

}