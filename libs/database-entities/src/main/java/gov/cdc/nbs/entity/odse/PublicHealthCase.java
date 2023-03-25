package gov.cdc.nbs.entity.odse;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Public_health_case")
public class PublicHealthCase {
    @Id
    @Column(name = "public_health_case_uid", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(
        fetch = FetchType.LAZY,
        optional = false,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
        },
        orphanRemoval = true
    )
    @JoinColumn(name = "public_health_case_uid", nullable = false)
    private Act act;

    @OneToMany(
        mappedBy = "subjectEntityPhcUid",
        fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
        },
        orphanRemoval = true
    )
    private List<CtContact> subjectContacts;

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

    public PublicHealthCase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    public List<CtContact> getSubjectContacts() {
        return List.copyOf(subjectContacts);
    }

    private List<CtContact> ensureSubjectContacts() {
        if(this.subjectContacts == null) {
            this.subjectContacts = new ArrayList<>();
        }

        return this.subjectContacts;
    }

    public void addSubjectContact(final CtContact contact) {
        this.ensureSubjectContacts().add(contact);
    }

    public String getActivityDurationAmt() {
        return activityDurationAmt;
    }

    public void setActivityDurationAmt(String activityDurationAmt) {
        this.activityDurationAmt = activityDurationAmt;
    }

    public String getActivityDurationUnitCd() {
        return activityDurationUnitCd;
    }

    public void setActivityDurationUnitCd(String activityDurationUnitCd) {
        this.activityDurationUnitCd = activityDurationUnitCd;
    }

    public Instant getActivityFromTime() {
        return activityFromTime;
    }

    public void setActivityFromTime(Instant activityFromTime) {
        this.activityFromTime = activityFromTime;
    }

    public Instant getActivityToTime() {
        return activityToTime;
    }

    public void setActivityToTime(Instant activityToTime) {
        this.activityToTime = activityToTime;
    }

    public String getAddReasonCd() {
        return addReasonCd;
    }

    public void setAddReasonCd(String addReasonCd) {
        this.addReasonCd = addReasonCd;
    }

    public Instant getAddTime() {
        return addTime;
    }

    public void setAddTime(Instant addTime) {
        this.addTime = addTime;
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    public String getCaseClassCd() {
        return caseClassCd;
    }

    public void setCaseClassCd(String caseClassCd) {
        this.caseClassCd = caseClassCd;
    }

    public Character getCaseTypeCd() {
        return caseTypeCd;
    }

    public void setCaseTypeCd(Character caseTypeCd) {
        this.caseTypeCd = caseTypeCd;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getCdDescTxt() {
        return cdDescTxt;
    }

    public void setCdDescTxt(String cdDescTxt) {
        this.cdDescTxt = cdDescTxt;
    }

    public String getCdSystemCd() {
        return cdSystemCd;
    }

    public void setCdSystemCd(String cdSystemCd) {
        this.cdSystemCd = cdSystemCd;
    }

    public String getCdSystemDescTxt() {
        return cdSystemDescTxt;
    }

    public void setCdSystemDescTxt(String cdSystemDescTxt) {
        this.cdSystemDescTxt = cdSystemDescTxt;
    }

    public String getConfidentialityCd() {
        return confidentialityCd;
    }

    public void setConfidentialityCd(String confidentialityCd) {
        this.confidentialityCd = confidentialityCd;
    }

    public String getConfidentialityDescTxt() {
        return confidentialityDescTxt;
    }

    public void setConfidentialityDescTxt(String confidentialityDescTxt) {
        this.confidentialityDescTxt = confidentialityDescTxt;
    }

    public String getDetectionMethodCd() {
        return detectionMethodCd;
    }

    public void setDetectionMethodCd(String detectionMethodCd) {
        this.detectionMethodCd = detectionMethodCd;
    }

    public String getDetectionMethodDescTxt() {
        return detectionMethodDescTxt;
    }

    public void setDetectionMethodDescTxt(String detectionMethodDescTxt) {
        this.detectionMethodDescTxt = detectionMethodDescTxt;
    }

    public Instant getDiagnosisTime() {
        return diagnosisTime;
    }

    public void setDiagnosisTime(Instant diagnosisTime) {
        this.diagnosisTime = diagnosisTime;
    }

    public String getDiseaseImportedCd() {
        return diseaseImportedCd;
    }

    public void setDiseaseImportedCd(String diseaseImportedCd) {
        this.diseaseImportedCd = diseaseImportedCd;
    }

    public String getDiseaseImportedDescTxt() {
        return diseaseImportedDescTxt;
    }

    public void setDiseaseImportedDescTxt(String diseaseImportedDescTxt) {
        this.diseaseImportedDescTxt = diseaseImportedDescTxt;
    }

    public String getEffectiveDurationAmt() {
        return effectiveDurationAmt;
    }

    public void setEffectiveDurationAmt(String effectiveDurationAmt) {
        this.effectiveDurationAmt = effectiveDurationAmt;
    }

    public String getEffectiveDurationUnitCd() {
        return effectiveDurationUnitCd;
    }

    public void setEffectiveDurationUnitCd(String effectiveDurationUnitCd) {
        this.effectiveDurationUnitCd = effectiveDurationUnitCd;
    }

    public Instant getEffectiveFromTime() {
        return effectiveFromTime;
    }

    public void setEffectiveFromTime(Instant effectiveFromTime) {
        this.effectiveFromTime = effectiveFromTime;
    }

    public Instant getEffectiveToTime() {
        return effectiveToTime;
    }

    public void setEffectiveToTime(Instant effectiveToTime) {
        this.effectiveToTime = effectiveToTime;
    }

    public Short getGroupCaseCnt() {
        return groupCaseCnt;
    }

    public void setGroupCaseCnt(Short groupCaseCnt) {
        this.groupCaseCnt = groupCaseCnt;
    }

    public String getInvestigationStatusCd() {
        return investigationStatusCd;
    }

    public void setInvestigationStatusCd(String investigationStatusCd) {
        this.investigationStatusCd = investigationStatusCd;
    }

    public String getJurisdictionCd() {
        return jurisdictionCd;
    }

    public void setJurisdictionCd(String jurisdictionCd) {
        this.jurisdictionCd = jurisdictionCd;
    }

    public String getLastChgReasonCd() {
        return lastChgReasonCd;
    }

    public void setLastChgReasonCd(String lastChgReasonCd) {
        this.lastChgReasonCd = lastChgReasonCd;
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

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getMmwrWeek() {
        return mmwrWeek;
    }

    public void setMmwrWeek(String mmwrWeek) {
        this.mmwrWeek = mmwrWeek;
    }

    public String getMmwrYear() {
        return mmwrYear;
    }

    public void setMmwrYear(String mmwrYear) {
        this.mmwrYear = mmwrYear;
    }

    public String getOutbreakInd() {
        return outbreakInd;
    }

    public void setOutbreakInd(String outbreakInd) {
        this.outbreakInd = outbreakInd;
    }

    public Instant getOutbreakFromTime() {
        return outbreakFromTime;
    }

    public void setOutbreakFromTime(Instant outbreakFromTime) {
        this.outbreakFromTime = outbreakFromTime;
    }

    public Instant getOutbreakToTime() {
        return outbreakToTime;
    }

    public void setOutbreakToTime(Instant outbreakToTime) {
        this.outbreakToTime = outbreakToTime;
    }

    public String getOutbreakName() {
        return outbreakName;
    }

    public void setOutbreakName(String outbreakName) {
        this.outbreakName = outbreakName;
    }

    public String getOutcomeCd() {
        return outcomeCd;
    }

    public void setOutcomeCd(String outcomeCd) {
        this.outcomeCd = outcomeCd;
    }

    public String getPatAgeAtOnset() {
        return patAgeAtOnset;
    }

    public void setPatAgeAtOnset(String patAgeAtOnset) {
        this.patAgeAtOnset = patAgeAtOnset;
    }

    public String getPatAgeAtOnsetUnitCd() {
        return patAgeAtOnsetUnitCd;
    }

    public void setPatAgeAtOnsetUnitCd(String patAgeAtOnsetUnitCd) {
        this.patAgeAtOnsetUnitCd = patAgeAtOnsetUnitCd;
    }

    public Long getPatientGroupId() {
        return patientGroupId;
    }

    public void setPatientGroupId(Long patientGroupId) {
        this.patientGroupId = patientGroupId;
    }

    public String getProgAreaCd() {
        return progAreaCd;
    }

    public void setProgAreaCd(String progAreaCd) {
        this.progAreaCd = progAreaCd;
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

    public Short getRepeatNbr() {
        return repeatNbr;
    }

    public void setRepeatNbr(Short repeatNbr) {
        this.repeatNbr = repeatNbr;
    }

    public String getRptCntyCd() {
        return rptCntyCd;
    }

    public void setRptCntyCd(String rptCntyCd) {
        this.rptCntyCd = rptCntyCd;
    }

    public Instant getRptFormCmpltTime() {
        return rptFormCmpltTime;
    }

    public void setRptFormCmpltTime(Instant rptFormCmpltTime) {
        this.rptFormCmpltTime = rptFormCmpltTime;
    }

    public String getRptSourceCd() {
        return rptSourceCd;
    }

    public void setRptSourceCd(String rptSourceCd) {
        this.rptSourceCd = rptSourceCd;
    }

    public String getRptSourceCdDescTxt() {
        return rptSourceCdDescTxt;
    }

    public void setRptSourceCdDescTxt(String rptSourceCdDescTxt) {
        this.rptSourceCdDescTxt = rptSourceCdDescTxt;
    }

    public Instant getRptToCountyTime() {
        return rptToCountyTime;
    }

    public void setRptToCountyTime(Instant rptToCountyTime) {
        this.rptToCountyTime = rptToCountyTime;
    }

    public Instant getRptToStateTime() {
        return rptToStateTime;
    }

    public void setRptToStateTime(Instant rptToStateTime) {
        this.rptToStateTime = rptToStateTime;
    }

    public Character getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Character statusCd) {
        this.statusCd = statusCd;
    }

    public Instant getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Instant statusTime) {
        this.statusTime = statusTime;
    }

    public String getTransmissionModeCd() {
        return transmissionModeCd;
    }

    public void setTransmissionModeCd(String transmissionModeCd) {
        this.transmissionModeCd = transmissionModeCd;
    }

    public String getTransmissionModeDescTxt() {
        return transmissionModeDescTxt;
    }

    public void setTransmissionModeDescTxt(String transmissionModeDescTxt) {
        this.transmissionModeDescTxt = transmissionModeDescTxt;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getUserAffiliationTxt() {
        return userAffiliationTxt;
    }

    public void setUserAffiliationTxt(String userAffiliationTxt) {
        this.userAffiliationTxt = userAffiliationTxt;
    }

    public Long getProgramJurisdictionOid() {
        return programJurisdictionOid;
    }

    public void setProgramJurisdictionOid(Long programJurisdictionOid) {
        this.programJurisdictionOid = programJurisdictionOid;
    }

    public Character getSharedInd() {
        return sharedInd;
    }

    public void setSharedInd(Character sharedInd) {
        this.sharedInd = sharedInd;
    }

    public Short getVersionCtrlNbr() {
        return versionCtrlNbr;
    }

    public void setVersionCtrlNbr(Short versionCtrlNbr) {
        this.versionCtrlNbr = versionCtrlNbr;
    }

    public Instant getInvestigatorAssignedTime() {
        return investigatorAssignedTime;
    }

    public void setInvestigatorAssignedTime(Instant investigatorAssignedTime) {
        this.investigatorAssignedTime = investigatorAssignedTime;
    }

    public String getHospitalizedIndCd() {
        return hospitalizedIndCd;
    }

    public void setHospitalizedIndCd(String hospitalizedIndCd) {
        this.hospitalizedIndCd = hospitalizedIndCd;
    }

    public Instant getHospitalizedAdminTime() {
        return hospitalizedAdminTime;
    }

    public void setHospitalizedAdminTime(Instant hospitalizedAdminTime) {
        this.hospitalizedAdminTime = hospitalizedAdminTime;
    }

    public Instant getHospitalizedDischargeTime() {
        return hospitalizedDischargeTime;
    }

    public void setHospitalizedDischargeTime(Instant hospitalizedDischargeTime) {
        this.hospitalizedDischargeTime = hospitalizedDischargeTime;
    }

    public BigDecimal getHospitalizedDurationAmt() {
        return hospitalizedDurationAmt;
    }

    public void setHospitalizedDurationAmt(BigDecimal hospitalizedDurationAmt) {
        this.hospitalizedDurationAmt = hospitalizedDurationAmt;
    }

    public String getPregnantIndCd() {
        return pregnantIndCd;
    }

    public void setPregnantIndCd(String pregnantIndCd) {
        this.pregnantIndCd = pregnantIndCd;
    }

    public String getDayCareIndCd() {
        return dayCareIndCd;
    }

    public void setDayCareIndCd(String dayCareIndCd) {
        this.dayCareIndCd = dayCareIndCd;
    }

    public String getFoodHandlerIndCd() {
        return foodHandlerIndCd;
    }

    public void setFoodHandlerIndCd(String foodHandlerIndCd) {
        this.foodHandlerIndCd = foodHandlerIndCd;
    }

    public String getImportedCountryCd() {
        return importedCountryCd;
    }

    public void setImportedCountryCd(String importedCountryCd) {
        this.importedCountryCd = importedCountryCd;
    }

    public String getImportedStateCd() {
        return importedStateCd;
    }

    public void setImportedStateCd(String importedStateCd) {
        this.importedStateCd = importedStateCd;
    }

    public String getImportedCityDescTxt() {
        return importedCityDescTxt;
    }

    public void setImportedCityDescTxt(String importedCityDescTxt) {
        this.importedCityDescTxt = importedCityDescTxt;
    }

    public String getImportedCountyCd() {
        return importedCountyCd;
    }

    public void setImportedCountyCd(String importedCountyCd) {
        this.importedCountyCd = importedCountyCd;
    }

    public Instant getDeceasedTime() {
        return deceasedTime;
    }

    public void setDeceasedTime(Instant deceasedTime) {
        this.deceasedTime = deceasedTime;
    }

    public String getCountIntervalCd() {
        return countIntervalCd;
    }

    public void setCountIntervalCd(String countIntervalCd) {
        this.countIntervalCd = countIntervalCd;
    }

    public String getPriorityCd() {
        return priorityCd;
    }

    public void setPriorityCd(String priorityCd) {
        this.priorityCd = priorityCd;
    }

    public String getContactInvTxt() {
        return contactInvTxt;
    }

    public void setContactInvTxt(String contactInvTxt) {
        this.contactInvTxt = contactInvTxt;
    }

    public Instant getInfectiousFromDate() {
        return infectiousFromDate;
    }

    public void setInfectiousFromDate(Instant infectiousFromDate) {
        this.infectiousFromDate = infectiousFromDate;
    }

    public Instant getInfectiousToDate() {
        return infectiousToDate;
    }

    public void setInfectiousToDate(Instant infectiousToDate) {
        this.infectiousToDate = infectiousToDate;
    }

    public String getContactInvStatusCd() {
        return contactInvStatusCd;
    }

    public void setContactInvStatusCd(String contactInvStatusCd) {
        this.contactInvStatusCd = contactInvStatusCd;
    }

    public String getReferralBasisCd() {
        return referralBasisCd;
    }

    public void setReferralBasisCd(String referralBasisCd) {
        this.referralBasisCd = referralBasisCd;
    }

    public String getCurrProcessStateCd() {
        return currProcessStateCd;
    }

    public void setCurrProcessStateCd(String currProcessStateCd) {
        this.currProcessStateCd = currProcessStateCd;
    }

    public String getInvPriorityCd() {
        return invPriorityCd;
    }

    public void setInvPriorityCd(String invPriorityCd) {
        this.invPriorityCd = invPriorityCd;
    }

    public String getCoinfectionId() {
        return coinfectionId;
    }

    public void setCoinfectionId(String coinfectionId) {
        this.coinfectionId = coinfectionId;
    }
}
