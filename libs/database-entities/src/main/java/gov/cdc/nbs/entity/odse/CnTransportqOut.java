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
@Table(name = "CN_transportq_out")
public class CnTransportqOut {
    @Id
    @Column(name = "cn_transportq_out_uid", nullable = false)
    private Long id;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Lob
    @Column(name = "message_payload", columnDefinition = "TEXT")
    private String messagePayload;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notification_uid", nullable = false)
    private Notification notificationUid;

    @Column(name = "notification_local_id", length = 50)
    private String notificationLocalId;

    @Column(name = "public_health_case_local_id", length = 50)
    private String publicHealthCaseLocalId;

    @Column(name = "report_status_cd", length = 20)
    private String reportStatusCd;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "version_ctrl_nbr")
    private Short versionCtrlNbr;

}
