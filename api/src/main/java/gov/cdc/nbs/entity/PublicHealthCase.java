package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Public_health_case")
public class PublicHealthCase {
    @Id
    @Column(name = "public_health_case_uid", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "public_health_case_uid", nullable = false)
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

    @Column(name = "case_type_cd")
    private Character caseTypeCd;

    @Column(name = "cd", length = 50)
    private String cd;

    @Column(name = "cd_desc_txt", length = 100)
    private String cdDescTxt;

    @Column(name = "cd_system_cd", length = 20)
    private String cdSystemCd;

    @Column(name = "cd_system_desc_txt", length = 100)
    private String cdSystemDescTxt;

    @Column(name = "confidentiality_cd", length = 20)
    private String confidentialityCd;

    @Column(name = "confidentiality_desc_txt", length = 100)
    private String confidentialityDescTxt;

    @Column(name = "detection_method_cd", length = 20)
    private String detectionMethodCd;

    @Column(name = "detection_method_desc_txt", length = 100)
    private String detectionMethodDescTxt;

    @Column(name = "diagnosis_time")
    private Instant diagnosisTime;

    @Column(name = "disease_imported_cd", length = 20)
    private String diseaseImportedCd;

    @Column(name = "disease_imported_desc_txt", length = 100)
    private String diseaseImportedDescTxt;

    @Column(name = "effective_duration_amt", length = 20)
    private String effectiveDurationAmt;

    @Column(name = "effective_duration_unit_cd", length = 20)
    private String effectiveDurationUnitCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "group_case_cnt")
    private Short groupCaseCnt;

    @Column(name = "investigation_status_cd", length = 20)
    private String investigationStatusCd;

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

    @Column(name = "mmwr_week", length = 10)
    private String mmwrWeek;

    @Column(name = "mmwr_year", length = 10)
    private String mmwrYear;

    @Column(name = "outbreak_ind", length = 20)
    private String outbreakInd;

    @Column(name = "outbreak_from_time")
    private Instant outbreakFromTime;

    @Column(name = "outbreak_to_time")
    private Instant outbreakToTime;

    @Column(name = "outbreak_name", length = 100)
    private String outbreakName;

    @Column(name = "outcome_cd", length = 20)
    private String outcomeCd;

    @Column(name = "pat_age_at_onset", length = 20)
    private String patAgeAtOnset;

    @Column(name = "pat_age_at_onset_unit_cd", length = 20)
    private String patAgeAtOnsetUnitCd;

    @Column(name = "patient_group_id")
    private Long patientGroupId;

    @Column(name = "prog_area_cd", length = 20)
    private String progAreaCd;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "repeat_nbr")
    private Short repeatNbr;

    @Column(name = "rpt_cnty_cd", length = 20)
    private String rptCntyCd;

    @Column(name = "rpt_form_cmplt_time")
    private Instant rptFormCmpltTime;

    @Column(name = "rpt_source_cd", length = 20)
    private String rptSourceCd;

    @Column(name = "rpt_source_cd_desc_txt", length = 100)
    private String rptSourceCdDescTxt;

    @Column(name = "rpt_to_county_time")
    private Instant rptToCountyTime;

    @Column(name = "rpt_to_state_time")
    private Instant rptToStateTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "transmission_mode_cd", length = 20)
    private String transmissionModeCd;

    @Column(name = "transmission_mode_desc_txt", length = 100)
    private String transmissionModeDescTxt;

    @Column(name = "txt", length = 2000)
    private String txt;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "program_jurisdiction_oid")
    private Long programJurisdictionOid;

    @Column(name = "shared_ind", nullable = false)
    private Character sharedInd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Column(name = "investigator_assigned_time")
    private Instant investigatorAssignedTime;

    @Column(name = "hospitalized_ind_cd", length = 20)
    private String hospitalizedIndCd;

    @Column(name = "hospitalized_admin_time")
    private Instant hospitalizedAdminTime;

    @Column(name = "hospitalized_discharge_time")
    private Instant hospitalizedDischargeTime;

    @Column(name = "hospitalized_duration_amt", precision = 18)
    private BigDecimal hospitalizedDurationAmt;

    @Column(name = "pregnant_ind_cd", length = 20)
    private String pregnantIndCd;

    @Column(name = "day_care_ind_cd", length = 20)
    private String dayCareIndCd;

    @Column(name = "food_handler_ind_cd", length = 20)
    private String foodHandlerIndCd;

    @Column(name = "imported_country_cd", length = 20)
    private String importedCountryCd;

    @Column(name = "imported_state_cd", length = 20)
    private String importedStateCd;

    @Column(name = "imported_city_desc_txt", length = 250)
    private String importedCityDescTxt;

    @Column(name = "imported_county_cd", length = 20)
    private String importedCountyCd;

    @Column(name = "deceased_time")
    private Instant deceasedTime;

    @Column(name = "count_interval_cd", length = 20)
    private String countIntervalCd;

    @Column(name = "priority_cd", length = 50)
    private String priorityCd;

    @Column(name = "contact_inv_txt", length = 2000)
    private String contactInvTxt;

    @Column(name = "infectious_from_date")
    private Instant infectiousFromDate;

    @Column(name = "infectious_to_date")
    private Instant infectiousToDate;

    @Column(name = "contact_inv_status_cd", length = 50)
    private String contactInvStatusCd;

    @Column(name = "referral_basis_cd", length = 20)
    private String referralBasisCd;

    @Column(name = "curr_process_state_cd", length = 20)
    private String currProcessStateCd;

    @Column(name = "inv_priority_cd", length = 20)
    private String invPriorityCd;

    @Column(name = "coinfection_id", length = 50)
    private String coinfectionId;

}