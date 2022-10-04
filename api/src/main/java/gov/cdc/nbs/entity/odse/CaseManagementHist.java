package gov.cdc.nbs.entity.odse;

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
@Table(name = "case_management_hist")
public class CaseManagementHist {
    @Id
    @Column(name = "case_management_hist_uid", nullable = false)
    private Long id;

    @Column(name = "case_management_uid", nullable = false)
    private Long caseManagementUid;

    @Column(name = "public_health_case_uid", nullable = false)
    private Long publicHealthCaseUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Column(name = "status_900", length = 20)
    private String status900;

    @Column(name = "ehars_id", length = 10)
    private String eharsId;

    @Column(name = "epi_link_id", length = 20)
    private String epiLinkId;

    @Column(name = "field_foll_up_ooj_outcome", length = 20)
    private String fieldFollUpOojOutcome;

    @Column(name = "field_record_number", length = 20)
    private String fieldRecordNumber;

    @Column(name = "fld_foll_up_dispo", length = 20)
    private String fldFollUpDispo;

    @Column(name = "fld_foll_up_dispo_date")
    private Instant fldFollUpDispoDate;

    @Column(name = "fld_foll_up_exam_date")
    private Instant fldFollUpExamDate;

    @Column(name = "fld_foll_up_expected_date")
    private Instant fldFollUpExpectedDate;

    @Column(name = "fld_foll_up_expected_in", length = 20)
    private String fldFollUpExpectedIn;

    @Column(name = "fld_foll_up_internet_outcome", length = 20)
    private String fldFollUpInternetOutcome;

    @Column(name = "fld_foll_up_notification_plan", length = 20)
    private String fldFollUpNotificationPlan;

    @Column(name = "fld_foll_up_prov_diagnosis", length = 20)
    private String fldFollUpProvDiagnosis;

    @Column(name = "fld_foll_up_prov_exm_reason", length = 20)
    private String fldFollUpProvExmReason;

    @Column(name = "init_foll_up", length = 20)
    private String initFollUp;

    @Column(name = "init_foll_up_clinic_code", length = 50)
    private String initFollUpClinicCode;

    @Column(name = "init_foll_up_closed_date")
    private Instant initFollUpClosedDate;

    @Column(name = "init_foll_up_notifiable", length = 20)
    private String initFollUpNotifiable;

    @Column(name = "internet_foll_up", length = 20)
    private String internetFollUp;

    @Column(name = "ooj_agency", length = 20)
    private String oojAgency;

    @Column(name = "ooj_due_date")
    private Instant oojDueDate;

    @Column(name = "ooj_number", length = 20)
    private String oojNumber;

    @Column(name = "pat_intv_status_cd", length = 20)
    private String patIntvStatusCd;

    @Column(name = "subj_complexion", length = 20)
    private String subjComplexion;

    @Column(name = "subj_hair", length = 20)
    private String subjHair;

    @Column(name = "subj_height", length = 20)
    private String subjHeight;

    @Column(name = "subj_oth_idntfyng_info", length = 2000)
    private String subjOthIdntfyngInfo;

    @Column(name = "subj_size_build", length = 20)
    private String subjSizeBuild;

    @Column(name = "surv_closed_date")
    private Instant survClosedDate;

    @Column(name = "surv_patient_foll_up", length = 20)
    private String survPatientFollUp;

    @Column(name = "surv_prov_diagnosis", length = 20)
    private String survProvDiagnosis;

    @Column(name = "surv_prov_exm_reason", length = 20)
    private String survProvExmReason;

    @Column(name = "surv_provider_contact", length = 20)
    private String survProviderContact;

    @Column(name = "act_ref_type_cd", length = 20)
    private String actRefTypeCd;

    @Column(name = "initiating_agncy", length = 20)
    private String initiatingAgncy;

    @Column(name = "ooj_initg_agncy_outc_due_date")
    private Instant oojInitgAgncyOutcDueDate;

    @Column(name = "ooj_initg_agncy_outc_snt_date")
    private Instant oojInitgAgncyOutcSntDate;

    @Column(name = "ooj_initg_agncy_recd_date")
    private Instant oojInitgAgncyRecdDate;

    @Column(name = "case_review_status", length = 20)
    private String caseReviewStatus;

    @Column(name = "surv_assigned_date")
    private Instant survAssignedDate;

    @Column(name = "foll_up_assigned_date")
    private Instant follUpAssignedDate;

    @Column(name = "init_foll_up_assigned_date")
    private Instant initFollUpAssignedDate;

    @Column(name = "interview_assigned_date")
    private Instant interviewAssignedDate;

    @Column(name = "init_interview_assigned_date")
    private Instant initInterviewAssignedDate;

    @Column(name = "case_closed_date")
    private Instant caseClosedDate;

    @Column(name = "case_review_status_date")
    private Instant caseReviewStatusDate;

}