package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "publichealthcasefact")
public class PublicHealthCaseFact {
    @Id
    @Column(name = "public_health_case_uid", nullable = false)
    private Long id;

    @Column(name = "adults_in_house_nbr")
    private Short adultsInHouseNbr;

    @Column(name = "ageinmonths")
    private Short ageInMonths;

    @Column(name = "ageinyears")
    private Short ageInYears;

    @Column(name = "age_category_cd", length = 20)
    private String ageCategoryCd;

    @Column(name = "age_reported_time")
    private Instant ageReportedTime;

    @Column(name = "age_reported_unit_cd", length = 20)
    private String ageReportedUnitCd;

    @Column(name = "age_reported", precision = 8)
    private BigDecimal ageReported;

    @Column(name = "awareness_cd", length = 20)
    private String awarenessCd;

    @Column(name = "awareness_desc_txt", length = 100)
    private String awarenessDescTxt;

    @Column(name = "birth_gender_cd")
    private Character birthGenderCd;

    @Column(name = "birth_order_nbr")
    private Short birthOrderNbr;

    @Column(name = "birth_time")
    private Instant birthTime;

    @Column(name = "birth_time_calc")
    private Instant birthTimeCalc;

    @Column(name = "birth_time_std")
    private Instant birthTimeStd;

    @Column(name = "case_class_cd", nullable = false, length = 20)
    private String caseClassCd;

    @Column(name = "case_type_cd")
    private Character caseTypeCd;

    @Column(name = "cd_system_cd", length = 20)
    private String cdSystemCd;

    @Column(name = "cd_system_desc_txt", length = 100)
    private String cdSystemDescTxt;

    @Column(name = "census_block_cd", length = 20)
    private String censusBlockCd;

    @Column(name = "census_minor_civil_division_cd", length = 20)
    private String censusMinorCivilDivisionCd;

    @Column(name = "census_track_cd", length = 20)
    private String censusTrackCd;

    @Column(name = "cnty_code_desc_txt", length = 200)
    private String cntyCodeDescTxt;

    @Column(name = "children_in_house_nbr")
    private Short childrenInHouseNbr;

    @Column(name = "city_cd", length = 20)
    private String cityCd;

    @Column(name = "city_desc_txt", length = 100)
    private String cityDescTxt;

    @Column(name = "confidentiality_cd", length = 20)
    private String confidentialityCd;

    @Column(name = "confidentiality_desc_txt", length = 100)
    private String confidentialityDescTxt;

    @Column(name = "confirmation_method_cd", length = 300)
    private String confirmationMethodCd;

    @Column(name = "confirmation_method_time")
    private Instant confirmationMethodTime;

    @Column(name = "county")
    private String county;

    @Column(name = "cntry_cd", length = 20)
    private String cntryCd;

    @Column(name = "cnty_cd", length = 20)
    private String cntyCd;

    @Column(name = "curr_sex_cd")
    private Character currSexCd;

    @Column(name = "deceased_ind_cd", length = 20)
    private String deceasedIndCd;

    @Column(name = "deceased_time")
    private Instant deceasedTime;

    @Column(name = "detection_method_cd", length = 20)
    private String detectionMethodCd;

    @Column(name = "detection_method_desc_txt", length = 100)
    private String detectionMethodDescTxt;

    @Column(name = "diagnosis_date")
    private Instant diagnosisDate;

    @Column(name = "disease_imported_cd", length = 20)
    private String diseaseImportedCd;

    @Column(name = "disease_imported_desc_txt", length = 100)
    private String diseaseImportedDescTxt;

    @Column(name = "education_level_cd", length = 20)
    private String educationLevelCd;

    @Column(name = "ELP_class_cd", length = 10)
    private String elpClassCd;

    @Column(name = "ELP_from_time")
    private Instant elpFromTime;

    @Column(name = "ELP_to_time")
    private Instant elpToTime;

    @Column(name = "ethnic_group_ind", length = 20)
    private String ethnicGroupInd;

    @Column(name = "ethnic_group_ind_desc", length = 50)
    private String ethnicGroupIndDesc;

    @Column(name = "event_date")
    private Instant eventDate;

    @Column(name = "event_type", length = 10)
    private String eventType;

    @Column(name = "education_level_desc_txt", length = 100)
    private String educationLevelDescTxt;

    @Column(name = "firstnotificationsenddate")
    private Instant firstNotificationSenddate;

    @Column(name = "firstnotificationdate")
    private Instant firstNotificationdate;

    @Column(name = "firstnotificationstatus", length = 20)
    private String firstNotificationStatus;

    @Column(name = "firstnotificationsubmittedby")
    private Long firstNotificationSubmittedBy;

    @Column(name = "geolatitude")
    private Float geoLatitude;

    @Column(name = "geolongitude")
    private Float geoLongitude;

    @Column(name = "group_case_cnt", precision = 11, scale = 5)
    private BigDecimal groupCaseCnt;

    @Column(name = "investigation_status_cd", length = 20)
    private String investigationStatusCd;

    @Column(name = "investigatorassigneddate")
    private Instant investigatorAssigneddate;

    @Column(name = "investigatorname", length = 102)
    private String investigatorName;

    @Column(name = "investigatorphone", length = 20)
    private String investigatorPhone;

    @Column(name = "jurisdiction_cd", length = 20)
    private String jurisdictionCd;

    @Column(name = "lastnotificationdate")
    private Instant lastNotificationdate;

    @Column(name = "lastnotificationsenddate")
    private Instant lastNotificationSenddate;

    @Column(name = "lastnotificationsubmittedby")
    private Long lastNotificationSubmittedBy;

    @Column(name = "marital_status_cd", length = 20)
    private String maritalStatusCd;

    @Column(name = "marital_status_desc_txt", length = 100)
    private String maritalStatusDescTxt;

    @Column(name = "mart_record_creation_date")
    private Instant martRecordCreationDate;

    @Column(name = "mart_record_creation_time")
    private Instant martRecordCreationTime;

    @Column(name = "mmwr_week", precision = 8)
    private BigDecimal mmwrWeek;

    @Column(name = "mmwr_year", precision = 8)
    private BigDecimal mmwrYear;

    @Column(name = "MSA_congress_district_cd", length = 20)
    private String msaCongressDistrictCd;

    @Column(name = "multiple_birth_ind", length = 20)
    private String multipleBirthInd;

    @Column(name = "notifcreatedcount")
    private Integer notifCreatedCount;

    @Column(name = "notificationdate")
    private Instant notificationdate;

    @Column(name = "notifsentcount")
    private Integer notifSentCount;

    @Column(name = "occupation_cd", length = 20)
    private String occupationCd;

    @Column(name = "onsetdate")
    private Instant onSetDate;

    @Column(name = "organizationname", length = 100)
    private String organizationName;

    @Column(name = "outcome_cd", length = 20)
    private String outcomeCd;

    @Column(name = "outbreak_from_time")
    private Instant outbreakFromTime;

    @Column(name = "outbreak_ind", length = 20)
    private String outbreakInd;

    @Column(name = "outbreak_name", length = 100)
    private String outbreakName;

    @Column(name = "outbreak_to_time")
    private Instant outbreakToTime;

    @Column(name = "PAR_type_cd", length = 50)
    private String parTypeCd;

    @Column(name = "pat_age_at_onset", precision = 8)
    private BigDecimal patAgeAtOnset;

    @Column(name = "pat_age_at_onset_unit_cd", length = 20)
    private String patAgeAtOnsetUnitCd;

    @Column(name = "postal_locator_uid")
    private Long postalLocatorUid;

    @Column(name = "person_cd", length = 50)
    private String personCd;

    @Column(name = "person_code_desc", length = 100)
    private String personCodeDesc;

    @Column(name = "person_uid")
    private Long personUid;

    @Column(name = "PHC_add_time")
    private Instant phcAddTime;

    @Column(name = "PHC_code", nullable = false, length = 50)
    private String phcCode;

    @Column(name = "PHC_code_desc", nullable = false, length = 100)
    private String phcCodeDesc;

    @Column(name = "PHC_code_short_desc", length = 50)
    private String phcCodeShortDesc;

    @Column(name = "prim_lang_cd", length = 20)
    private String primLangCd;

    @Column(name = "prim_lang_desc_txt", length = 100)
    private String primLangDescTxt;

    @Column(name = "prog_area_cd", nullable = false, length = 20)
    private String progAreaCd;

    @Column(name = "providerphone", length = 20)
    private String providerPhone;

    @Column(name = "providername", length = 102)
    private String providerName;

    @Column(name = "PST_record_status_time")
    private Instant pstRecordStatusTime;

    @Column(name = "PST_record_status_cd", length = 20)
    private String pstRecordStatusCd;

    @Column(name = "race_concatenated_txt", length = 100)
    private String raceConcatenatedTxt;

    @Column(name = "race_concatenated_desc_txt", length = 500)
    private String raceConcatenatedDescTxt;

    @Column(name = "region_district_cd", length = 20)
    private String regionDistrictCd;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "reportername", length = 102)
    private String reporterName;

    @Column(name = "reporterphone", length = 20)
    private String reporterPhone;

    @Column(name = "rpt_cnty_cd", length = 20)
    private String rptCntyCd;

    @Column(name = "rpt_form_cmplt_time")
    private Instant rptFormCmpltTime;

    @Column(name = "rpt_source_cd", length = 20)
    private String rptSourceCd;

    @Column(name = "rpt_source_desc_txt", length = 100)
    private String rptSourceDescTxt;

    @Column(name = "rpt_to_county_time")
    private Instant rptToCountyTime;

    @Column(name = "rpt_to_state_time")
    private Instant rptToStateTime;

    @Column(name = "shared_ind", length = 1)
    private String sharedInd;

    @Column(name = "state")
    private String state;

    @Column(name = "state_cd", length = 20)
    private String stateCd;

    @Column(name = "state_code_short_desc_txt", length = 200)
    private String stateCodeShortDescTxt;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "street_addr1", length = 100)
    private String streetAddr1;

    @Column(name = "street_addr2", length = 100)
    private String streetAddr2;

    @Column(name = "ELP_use_cd", length = 20)
    private String elpUseCd;

    @Column(name = "zip_cd", length = 20)
    private String zipCd;

    @Column(name = "patientname", length = 102)
    private String patientName;

    @Column(name = "jurisdiction", length = 50)
    private String jurisdiction;

    @Column(name = "investigationstartdate")
    private Instant investigationstartdate;

    @Column(name = "program_jurisdiction_oid")
    private Long programJurisdictionOid;

    @Column(name = "report_date")
    private Instant reportDate;

    @Column(name = "person_parent_uid")
    private Long personParentUid;

    @Column(name = "person_local_id", length = 50)
    private String personLocalId;

    @Column(name = "sub_addr_as_of_date")
    private Instant subAddrAsOfDate;

    @Column(name = "state_case_id", length = 199)
    private String stateCaseId;

    @Column(name = "LOCAL_ID", length = 50)
    private String localId;

    @Column(name = "NOTIFCURRENTSTATE", length = 50)
    private String notifcurrentstate;

    @Column(name = "age_reported_unit_desc_txt", length = 300)
    private String ageReportedUnitDescTxt;

    @Column(name = "birth_gender_desc_txt", length = 300)
    private String birthGenderDescTxt;

    @Column(name = "case_class_desc_txt", length = 300)
    private String caseClassDescTxt;

    @Column(name = "cntry_desc_txt", length = 300)
    private String cntryDescTxt;

    @Column(name = "curr_sex_desc_txt", length = 300)
    private String currSexDescTxt;

    @Column(name = "investigation_status_desc_txt", length = 300)
    private String investigationStatusDescTxt;

    @Column(name = "occupation_desc_txt", length = 300)
    private String occupationDescTxt;

    @Column(name = "outcome_desc_txt", length = 300)
    private String outcomeDescTxt;

    @Column(name = "pat_age_at_onset_unit_desc_txt", length = 300)
    private String patAgeAtOnsetUnitDescTxt;

    @Column(name = "prog_area_desc_txt", length = 300)
    private String progAreaDescTxt;

    @Column(name = "rpt_cnty_desc_txt", length = 300)
    private String rptCntyDescTxt;

    @Column(name = "outbreak_name_desc", length = 300)
    private String outbreakNameDesc;

    @Column(name = "confirmation_method_desc_txt", length = 300)
    private String confirmationMethodDescTxt;

    @Column(name = "LASTUPDATE")
    private Instant lastupdate;

    @Column(name = "PHCTXT", length = 2000)
    private String phctxt;

    @Column(name = "NOTITXT", length = 2000)
    private String notitxt;

    @Column(name = "NOTIFICATION_LOCAL_ID", length = 50)
    private String notificationLocalId;

    @Column(name = "HSPTL_ADMISSION_DT")
    private Instant hsptlAdmissionDt;

    @Column(name = "HSPTL_DISCHARGE_DT")
    private Instant hsptlDischargeDt;

    @Column(name = "hospitalized_ind", length = 100)
    private String hospitalizedInd;

}