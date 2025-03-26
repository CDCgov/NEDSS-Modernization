package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "CT_contact")
@SuppressWarnings("javaarchitecture:S7027")
public class CtContact {
    @Id
    @Column(name = "ct_contact_uid", nullable = false)
    private Long id;

    @Column(name = "local_id", nullable = false, length = 50)
    private String localId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_entity_uid", nullable = false)
    private NBSEntity subjectNBSEntityUid;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contact_entity_uid", nullable = false)
    private NBSEntity contactNBSEntityUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_entity_phc_uid", nullable = false)
    private PublicHealthCase subjectEntityPhcUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_entity_phc_uid")
    private PublicHealthCase contactEntityPhcUid;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "prog_area_cd", length = 20)
    private String progAreaCd;

    @Column(name = "jurisdiction_cd", length = 20)
    private String jurisdictionCd;

    @Column(name = "program_jurisdiction_oid")
    private Long programJurisdictionOid;

    @Column(name = "shared_ind_cd")
    private Character sharedIndCd;

    @Column(name = "contact_status", length = 20)
    private String contactStatus;

    @Column(name = "priority_cd", length = 20)
    private String priorityCd;

    @Column(name = "group_name_cd", length = 20)
    private String groupNameCd;

    @Column(name = "investigator_assigned_date")
    private Instant investigatorAssignedDate;

    @Column(name = "disposition_cd", length = 20)
    private String dispositionCd;

    @Column(name = "disposition_date")
    private Instant dispositionDate;

    @Column(name = "named_on_date")
    private Instant namedOnDate;

    @Column(name = "relationship_cd", length = 20)
    private String relationshipCd;

    @Column(name = "health_status_cd", length = 20)
    private String healthStatusCd;

    @Column(name = "txt", length = 2000)
    private String txt;

    @Column(name = "symptom_cd", length = 20)
    private String symptomCd;

    @Column(name = "symptom_onset_date")
    private Instant symptomOnsetDate;

    @Column(name = "symptom_txt", length = 2000)
    private String symptomTxt;

    @Column(name = "risk_factor_cd", length = 20)
    private String riskFactorCd;

    @Column(name = "risk_factor_txt", length = 2000)
    private String riskFactorTxt;

    @Column(name = "evaluation_completed_cd", length = 20)
    private String evaluationCompletedCd;

    @Column(name = "evaluation_date")
    private Instant evaluationDate;

    @Column(name = "evaluation_txt", length = 2000)
    private String evaluationTxt;

    @Column(name = "treatment_initiated_cd", length = 20)
    private String treatmentInitiatedCd;

    @Column(name = "treatment_start_date")
    private Instant treatmentStartDate;

    @Column(name = "treatment_not_start_rsn_cd", length = 20)
    private String treatmentNotStartRsnCd;

    @Column(name = "treatment_end_cd", length = 20)
    private String treatmentEndCd;

    @Column(name = "treatment_end_date")
    private Instant treatmentEndDate;

    @Column(name = "treatment_not_end_rsn_cd", length = 20)
    private String treatmentNotEndRsnCd;

    @Column(name = "treatment_txt", length = 2000)
    private String treatmentTxt;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "third_party_entity_uid")
    private NBSEntity thirdPartyNBSEntityUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "third_party_entity_phc_uid")
    private Act thirdPartyEntityPhcUid;

    @Column(name = "processing_decision_cd", length = 20)
    private String processingDecisionCd;

    @Column(name = "subject_entity_epi_link_id", length = 20)
    private String subjectEntityEpiLinkId;

    @Column(name = "contact_entity_epi_link_id", length = 20)
    private String contactEntityEpiLinkId;

    @Column(name = "named_during_interview_uid")
    private Long namedDuringInterviewUid;

    @Column(name = "contact_referral_basis_cd", length = 20)
    private String contactReferralBasisCd;

    public CtContact() {
        // no args constructor required for JPA
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersionCtrlNbr(Short versionCtrlNbr) {
        this.versionCtrlNbr = versionCtrlNbr;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public void setSubjectNBSEntityUid(NBSEntity subjectNBSEntityUid) {
        this.subjectNBSEntityUid = subjectNBSEntityUid;
    }

    public void setContactNBSEntityUid(NBSEntity contactNBSEntityUid) {
        this.contactNBSEntityUid = contactNBSEntityUid;
    }

    public void setSubjectEntityPhcUid(PublicHealthCase subjectEntityPhcUid) {
        this.subjectEntityPhcUid = subjectEntityPhcUid;
    }

    public void setRecordStatusCd(String recordStatusCd) {
        this.recordStatusCd = recordStatusCd;
    }

    public void setRecordStatusTime(Instant recordStatusTime) {
        this.recordStatusTime = recordStatusTime;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    public void setAddTime(Instant addTime) {
        this.addTime = addTime;
    }

    public void setLastChgTime(Instant lastChgTime) {
        this.lastChgTime = lastChgTime;
    }

    public void setLastChgUserId(Long lastChgUserId) {
        this.lastChgUserId = lastChgUserId;
    }

}
