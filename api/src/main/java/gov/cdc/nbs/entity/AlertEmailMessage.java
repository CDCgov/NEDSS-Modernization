package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Alert_Email_Message")
public class AlertEmailMessage {
    @Id
    @Column(name = "alert_email_message_uid", nullable = false)
    private Long id;

    @Column(name = "type_cd", nullable = false, length = 20)
    private String typeCd;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "severity_cd", nullable = false, length = 20)
    private String severityCd;

    @Column(name = "severity", nullable = false, length = 50)
    private String severity;

    @Column(name = "simulated_alert")
    private Character simulatedAlert;

    @Column(name = "jurisdiction_cd", nullable = false, length = 20)
    private String jurisdictionCd;

    @Column(name = "jurisdiction_description", nullable = false, length = 100)
    private String jurisdictionDescription;

    @Column(name = "associated_condition_cd", length = 20)
    private String associatedConditionCd;

    @Column(name = "associated_condition_desc", length = 100)
    private String associatedConditionDesc;

    @Column(name = "event_add_time", nullable = false)
    private Instant eventAddTime;

    @Column(name = "alert_add_time", nullable = false)
    private Instant alertAddTime;

    @Column(name = "event_local_id", nullable = false, length = 50)
    private String eventLocalId;

    @Column(name = "transmission_status", nullable = false, length = 20)
    private String transmissionStatus;

    @Column(name = "Prog_area_cd", length = 20)
    private String progAreaCd;

    @Column(name = "Prog_area_description", length = 100)
    private String progAreaDescription;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alert_uid", nullable = false)
    private Alert alertUid;

    @Column(name = "email_sent_time")
    private Instant emailSentTime;

}