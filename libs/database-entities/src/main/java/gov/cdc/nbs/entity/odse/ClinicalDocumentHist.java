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
@Table(name = "Clinical_document_hist")
public class ClinicalDocumentHist {
    @EmbeddedId
    private ClinicalDocumentHistId id;

    @MapsId("clinicalDocumentUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clinical_document_uid", nullable = false)
    private ClinicalDocument clinicalDocumentUid;

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

    @Column(name = "cd", length = 50)
    private String cd;

    @Column(name = "cd_desc_txt", length = 100)
    private String cdDescTxt;

    @Column(name = "confidentiality_cd", length = 20)
    private String confidentialityCd;

    @Column(name = "confidentiality_desc_txt", length = 100)
    private String confidentialityDescTxt;

    @Column(name = "copy_from_time")
    private Instant copyFromTime;

    @Column(name = "copy_to_time")
    private Instant copyToTime;

    @Column(name = "effective_duration_amt", length = 20)
    private String effectiveDurationAmt;

    @Column(name = "effective_duration_unit_cd", length = 20)
    private String effectiveDurationUnitCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "practice_setting_cd", length = 20)
    private String practiceSettingCd;

    @Column(name = "practice_setting_desc_txt", length = 100)
    private String practiceSettingDescTxt;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "txt", length = 2000)
    private String txt;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "version_nbr")
    private Short versionNbr;

    @Column(name = "program_jurisdiction_oid")
    private Long programJurisdictionOid;

    @Column(name = "shared_ind", nullable = false)
    private Character sharedInd;

}
