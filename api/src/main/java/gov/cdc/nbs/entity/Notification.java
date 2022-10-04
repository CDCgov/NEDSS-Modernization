package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Notification {
    @Id
    @Column(name = "notification_uid", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notification_uid", nullable = false)
    private Act act;

    @Column(name = "activity_duration_amt", length = 20)
    private String activityDurationAmt;

    @Column(name = "activity_duration_unit_cd", length = 20)
    private String activityDurationUnitCd;

    @Column(name = "activity_from_time")
    private Instant activityFromTime;

    @Column(name = "activity_to_time")
    private Instant activityToTime;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "case_class_cd", length = 20)
    private String caseClassCd;

    @Column(name = "case_condition_cd", length = 20)
    private String caseConditionCd;

    @Column(name = "cd", length = 50)
    private String cd;

    @Column(name = "cd_desc_txt", length = 100)
    private String cdDescTxt;

    @Column(name = "confidentiality_cd", length = 20)
    private String confidentialityCd;

    @Column(name = "confidentiality_desc_txt", length = 100)
    private String confidentialityDescTxt;

    @Column(name = "confirmation_method_cd", length = 20)
    private String confirmationMethodCd;

    @Column(name = "effective_duration_amt", length = 20)
    private String effectiveDurationAmt;

    @Column(name = "effective_duration_unit_cd", length = 20)
    private String effectiveDurationUnitCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "jurisdiction_cd", length = 20)
    private String jurisdictionCd;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Lob
    @Column(name = "message_txt", columnDefinition = "TEXT")
    private String messageTxt;

    @Column(name = "method_cd", length = 20)
    private String methodCd;

    @Column(name = "method_desc_txt", length = 100)
    private String methodDescTxt;

    @Column(name = "mmwr_week", length = 10)
    private String mmwrWeek;

    @Column(name = "mmwr_year", length = 10)
    private String mmwrYear;

    @Column(name = "nedss_version_nbr", length = 10)
    private String nedssVersionNbr;

    @Column(name = "prog_area_cd", length = 20)
    private String progAreaCd;

    @Column(name = "reason_cd", length = 20)
    private String reasonCd;

    @Column(name = "reason_desc_txt", length = 100)
    private String reasonDescTxt;

    @Column(name = "record_count", length = 10)
    private String recordCount;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "repeat_nbr")
    private Short repeatNbr;

    @Column(name = "rpt_sent_time")
    private Instant rptSentTime;

    @Column(name = "rpt_source_cd", length = 20)
    private String rptSourceCd;

    @Column(name = "rpt_source_type_cd", length = 20)
    private String rptSourceTypeCd;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "txt", length = 1000)
    private String txt;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "program_jurisdiction_oid")
    private Long programJurisdictionOid;

    @Column(name = "shared_ind", nullable = false)
    private Character sharedInd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Column(name = "auto_resend_ind")
    private Character autoResendInd;

    @Column(name = "export_receiving_facility_uid")
    private Long exportReceivingFacilityUid;

    @Column(name = "nbs_interface_uid")
    private Long nbsInterfaceUid;

}