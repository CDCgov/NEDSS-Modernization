package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Alert_Log")
public class AlertLog {
    @Id
    @Column(name = "alert_log_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alert_uid", nullable = false)
    private Alert alertUid;

    @Column(name = "event_local_id", length = 50)
    private String eventLocalId;

    @Column(name = "activity_log", length = 2000)
    private String activityLog;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

}
