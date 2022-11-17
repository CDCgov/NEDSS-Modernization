package gov.cdc.nbs.entity.odse;

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
@Table(name = "CT_contact_hist")
public class CtContactHist {
    @Id
    @Column(name = "ct_contact_hist_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ct_contact_uid", nullable = false)
    private CtContact ctContactUid;

    @Column(name = "local_id", nullable = false, length = 50)
    private String localId;

    @Column(name = "subject_entity_uid", nullable = false)
    private Long subjectEntityUid;

    @Column(name = "contact_entity_uid", nullable = false)
    private Long contactEntityUid;

    @Column(name = "subject_entity_phc_uid", nullable = false)
    private Long subjectEntityPhcUid;

    @Column(name = "contact_entity_phc_uid")
    private Long contactEntityPhcUid;

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

}