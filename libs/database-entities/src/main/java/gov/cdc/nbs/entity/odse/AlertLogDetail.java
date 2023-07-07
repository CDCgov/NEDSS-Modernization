package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Alert_Log_Detail")
public class AlertLogDetail {
    @Id
    @Column(name = "alert_log_detail_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alert_log_uid", nullable = false)
    private AlertLog alertLogUid;

    @Column(name = "alert_activity_detail_log", length = 2000)
    private String alertActivityDetailLog;

}
