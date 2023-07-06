package gov.cdc.nbs.entity.odse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "CT_contact")
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
    @JoinColumn(
        name = "subject_entity_phc_uid",
        nullable = false
    )
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

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public NBSEntity getSubjectNBSEntityUid() {
        return subjectNBSEntityUid;
    }

    public void setSubjectNBSEntityUid(NBSEntity subjectNBSEntityUid) {
        this.subjectNBSEntityUid = subjectNBSEntityUid;
    }

    public NBSEntity getContactNBSEntityUid() {
        return contactNBSEntityUid;
    }

    public void setContactNBSEntityUid(NBSEntity contactNBSEntityUid) {
        this.contactNBSEntityUid = contactNBSEntityUid;
    }

    public PublicHealthCase getSubjectEntityPhcUid() {
        return subjectEntityPhcUid;
    }

    public void setSubjectEntityPhcUid(PublicHealthCase subjectEntityPhcUid) {
        this.subjectEntityPhcUid = subjectEntityPhcUid;
    }

    public PublicHealthCase getContactEntityPhcUid() {
        return contactEntityPhcUid;
    }

    public void setContactEntityPhcUid(PublicHealthCase contactEntityPhcUid) {
        this.contactEntityPhcUid = contactEntityPhcUid;
    }

    public String getRecordStatusCd() {
        return recordStatusCd;
    }

    public void setRecordStatusCd(String recordStatusCd) {
        this.recordStatusCd = recordStatusCd;
    }

    public Instant getRecordStatusTime() {
        return recordStatusTime;
    }

    public void setRecordStatusTime(Instant recordStatusTime) {
        this.recordStatusTime = recordStatusTime;
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    public Instant getAddTime() {
        return addTime;
    }

    public void setAddTime(Instant addTime) {
        this.addTime = addTime;
    }

    public Instant getLastChgTime() {
        return lastChgTime;
    }

    public void setLastChgTime(Instant lastChgTime) {
        this.lastChgTime = lastChgTime;
    }

    public Long getLastChgUserId() {
        return lastChgUserId;
    }

    public void setLastChgUserId(Long lastChgUserId) {
        this.lastChgUserId = lastChgUserId;
    }

    public String getProgAreaCd() {
        return progAreaCd;
    }

    public void setProgAreaCd(String progAreaCd) {
        this.progAreaCd = progAreaCd;
    }

    public String getJurisdictionCd() {
        return jurisdictionCd;
    }

    public void setJurisdictionCd(String jurisdictionCd) {
        this.jurisdictionCd = jurisdictionCd;
    }

    public Long getProgramJurisdictionOid() {
        return programJurisdictionOid;
    }

    public void setProgramJurisdictionOid(Long programJurisdictionOid) {
        this.programJurisdictionOid = programJurisdictionOid;
    }

    public Character getSharedIndCd() {
        return sharedIndCd;
    }

    public void setSharedIndCd(Character sharedIndCd) {
        this.sharedIndCd = sharedIndCd;
    }

    public String getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }

    public String getPriorityCd() {
        return priorityCd;
    }

    public void setPriorityCd(String priorityCd) {
        this.priorityCd = priorityCd;
    }

    public String getGroupNameCd() {
        return groupNameCd;
    }

    public void setGroupNameCd(String groupNameCd) {
        this.groupNameCd = groupNameCd;
    }

    public Instant getInvestigatorAssignedDate() {
        return investigatorAssignedDate;
    }

    public void setInvestigatorAssignedDate(Instant investigatorAssignedDate) {
        this.investigatorAssignedDate = investigatorAssignedDate;
    }

    public String getDispositionCd() {
        return dispositionCd;
    }

    public void setDispositionCd(String dispositionCd) {
        this.dispositionCd = dispositionCd;
    }

    public Instant getDispositionDate() {
        return dispositionDate;
    }

    public void setDispositionDate(Instant dispositionDate) {
        this.dispositionDate = dispositionDate;
    }

    public Instant getNamedOnDate() {
        return namedOnDate;
    }

    public void setNamedOnDate(Instant namedOnDate) {
        this.namedOnDate = namedOnDate;
    }

    public String getRelationshipCd() {
        return relationshipCd;
    }

    public void setRelationshipCd(String relationshipCd) {
        this.relationshipCd = relationshipCd;
    }

    public String getHealthStatusCd() {
        return healthStatusCd;
    }

    public void setHealthStatusCd(String healthStatusCd) {
        this.healthStatusCd = healthStatusCd;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getSymptomCd() {
        return symptomCd;
    }

    public void setSymptomCd(String symptomCd) {
        this.symptomCd = symptomCd;
    }

    public Instant getSymptomOnsetDate() {
        return symptomOnsetDate;
    }

    public void setSymptomOnsetDate(Instant symptomOnsetDate) {
        this.symptomOnsetDate = symptomOnsetDate;
    }

    public String getSymptomTxt() {
        return symptomTxt;
    }

    public void setSymptomTxt(String symptomTxt) {
        this.symptomTxt = symptomTxt;
    }

    public String getRiskFactorCd() {
        return riskFactorCd;
    }

    public void setRiskFactorCd(String riskFactorCd) {
        this.riskFactorCd = riskFactorCd;
    }

    public String getRiskFactorTxt() {
        return riskFactorTxt;
    }

    public void setRiskFactorTxt(String riskFactorTxt) {
        this.riskFactorTxt = riskFactorTxt;
    }

    public String getEvaluationCompletedCd() {
        return evaluationCompletedCd;
    }

    public void setEvaluationCompletedCd(String evaluationCompletedCd) {
        this.evaluationCompletedCd = evaluationCompletedCd;
    }

    public Instant getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Instant evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public String getEvaluationTxt() {
        return evaluationTxt;
    }

    public void setEvaluationTxt(String evaluationTxt) {
        this.evaluationTxt = evaluationTxt;
    }

    public String getTreatmentInitiatedCd() {
        return treatmentInitiatedCd;
    }

    public void setTreatmentInitiatedCd(String treatmentInitiatedCd) {
        this.treatmentInitiatedCd = treatmentInitiatedCd;
    }

    public Instant getTreatmentStartDate() {
        return treatmentStartDate;
    }

    public void setTreatmentStartDate(Instant treatmentStartDate) {
        this.treatmentStartDate = treatmentStartDate;
    }

    public String getTreatmentNotStartRsnCd() {
        return treatmentNotStartRsnCd;
    }

    public void setTreatmentNotStartRsnCd(String treatmentNotStartRsnCd) {
        this.treatmentNotStartRsnCd = treatmentNotStartRsnCd;
    }

    public String getTreatmentEndCd() {
        return treatmentEndCd;
    }

    public void setTreatmentEndCd(String treatmentEndCd) {
        this.treatmentEndCd = treatmentEndCd;
    }

    public Instant getTreatmentEndDate() {
        return treatmentEndDate;
    }

    public void setTreatmentEndDate(Instant treatmentEndDate) {
        this.treatmentEndDate = treatmentEndDate;
    }

    public String getTreatmentNotEndRsnCd() {
        return treatmentNotEndRsnCd;
    }

    public void setTreatmentNotEndRsnCd(String treatmentNotEndRsnCd) {
        this.treatmentNotEndRsnCd = treatmentNotEndRsnCd;
    }

    public String getTreatmentTxt() {
        return treatmentTxt;
    }

    public void setTreatmentTxt(String treatmentTxt) {
        this.treatmentTxt = treatmentTxt;
    }

    public Short getVersionCtrlNbr() {
        return versionCtrlNbr;
    }

    public void setVersionCtrlNbr(Short versionCtrlNbr) {
        this.versionCtrlNbr = versionCtrlNbr;
    }

    public NBSEntity getThirdPartyNBSEntityUid() {
        return thirdPartyNBSEntityUid;
    }

    public void setThirdPartyNBSEntityUid(NBSEntity thirdPartyNBSEntityUid) {
        this.thirdPartyNBSEntityUid = thirdPartyNBSEntityUid;
    }

    public Act getThirdPartyEntityPhcUid() {
        return thirdPartyEntityPhcUid;
    }

    public void setThirdPartyEntityPhcUid(Act thirdPartyEntityPhcUid) {
        this.thirdPartyEntityPhcUid = thirdPartyEntityPhcUid;
    }

    public String getProcessingDecisionCd() {
        return processingDecisionCd;
    }

    public void setProcessingDecisionCd(String processingDecisionCd) {
        this.processingDecisionCd = processingDecisionCd;
    }

    public String getSubjectEntityEpiLinkId() {
        return subjectEntityEpiLinkId;
    }

    public void setSubjectEntityEpiLinkId(String subjectEntityEpiLinkId) {
        this.subjectEntityEpiLinkId = subjectEntityEpiLinkId;
    }

    public String getContactEntityEpiLinkId() {
        return contactEntityEpiLinkId;
    }

    public void setContactEntityEpiLinkId(String contactEntityEpiLinkId) {
        this.contactEntityEpiLinkId = contactEntityEpiLinkId;
    }

    public Long getNamedDuringInterviewUid() {
        return namedDuringInterviewUid;
    }

    public void setNamedDuringInterviewUid(Long namedDuringInterviewUid) {
        this.namedDuringInterviewUid = namedDuringInterviewUid;
    }

    public String getContactReferralBasisCd() {
        return contactReferralBasisCd;
    }

    public void setContactReferralBasisCd(String contactReferralBasisCd) {
        this.contactReferralBasisCd = contactReferralBasisCd;
    }
}
