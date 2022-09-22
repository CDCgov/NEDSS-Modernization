package com.enquizit.nbs.model.patient;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Person", schema = "dbo", catalog = "NBS_ODSE")
public class Patient {
    @Id
    @Column(name = "person_uid", nullable = false)
    private long personUid;
    @Basic
    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;
    @Basic
    @Column(name = "add_time")
    private LocalDateTime addTime;
    @Basic
    @Column(name = "add_user_id")
    private Long addUserId;
    @Basic
    @Column(name = "administrative_gender_cd", length = 20)
    private String administrativeGenderCd;
    @Basic
    @Column(name = "age_calc")
    private Short ageCalc;
    @Basic
    @Column(name = "age_calc_time")
    private LocalDateTime ageCalcTime;
    @Basic
    @Column(name = "age_calc_unit_cd", length = 1)
    private String ageCalcUnitCd;
    @Basic
    @Column(name = "age_category_cd", length = 20)
    private String ageCategoryCd;
    @Basic
    @Column(name = "age_reported", length = 10)
    private String ageReported;
    @Basic
    @Column(name = "age_reported_time")
    private LocalDateTime ageReportedTime;
    @Basic
    @Column(name = "age_reported_unit_cd", length = 20)
    private String ageReportedUnitCd;
    @Basic
    @Column(name = "birth_gender_cd", length = 1)
    private String birthGenderCd;
    @Basic
    @Column(name = "birth_order_nbr")
    private Short birthOrderNbr;
    @Basic
    @Column(name = "birth_time")
    private LocalDateTime birthTime;
    @Basic
    @Column(name = "birth_time_calc")
    private LocalDateTime birthTimeCalc;
    @Basic
    @Column(name = "cd", length = 50)
    private String cd;
    @Basic
    @Column(name = "cd_desc_txt", length = 100)
    private String cdDescTxt;
    @Basic
    @Column(name = "curr_sex_cd", length = 1)
    private String currSexCd;
    @Basic
    @Column(name = "deceased_ind_cd", length = 20)
    private String deceasedIndCd;
    @Basic
    @Column(name = "deceased_time")
    private LocalDateTime deceasedTime;
    @Basic
    @Column(name = "description", length = 2000)
    private String description;
    @Basic
    @Column(name = "education_level_cd", length = 20)
    private String educationLevelCd;
    @Basic
    @Column(name = "education_level_desc_txt", length = 100)
    private String educationLevelDescTxt;
    @Basic
    @Column(name = "ethnic_group_ind", length = 20)
    private String ethnicGroupInd;
    @Basic
    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;
    @Basic
    @Column(name = "last_chg_time")
    private LocalDateTime lastChgTime;
    @Basic
    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;
    @Basic
    @Column(name = "local_id", length = 50)
    private String localId;
    @Basic
    @Column(name = "marital_status_cd", length = 20)
    private String maritalStatusCd;
    @Basic
    @Column(name = "marital_status_desc_txt", length = 100)
    private String maritalStatusDescTxt;
    @Basic
    @Column(name = "mothers_maiden_nm", length = 50)
    private String mothersMaidenNm;
    @Basic
    @Column(name = "multiple_birth_ind", length = 20)
    private String multipleBirthInd;
    @Basic
    @Column(name = "occupation_cd", length = 20)
    private String occupationCd;
    @Basic
    @Column(name = "preferred_gender_cd", length = 20)
    private String preferredGenderCd;
    @Basic
    @Column(name = "prim_lang_cd", length = 20)
    private String primLangCd;
    @Basic
    @Column(name = "prim_lang_desc_txt", length = 100)
    private String primLangDescTxt;
    @Basic
    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;
    @Basic
    @Column(name = "record_status_time")
    private LocalDateTime recordStatusTime;
    @Basic
    @Column(name = "status_cd", length = 1)
    private String statusCd;
    @Basic
    @Column(name = "status_time")
    private LocalDateTime statusTime;
    @Basic
    @Column(name = "survived_ind_cd", length = 1)
    private String survivedIndCd;
    @Basic
    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;
    @Basic
    @Column(name = "first_nm", length = 50)
    private String firstNm;
    @Basic
    @Column(name = "last_nm", length = 50)
    private String lastNm;
    @Basic
    @Column(name = "middle_nm", length = 50)
    private String middleNm;
    @Basic
    @Column(name = "nm_prefix", length = 20)
    private String nmPrefix;
    @Basic
    @Column(name = "nm_suffix", length = 20)
    private String nmSuffix;
    @Basic
    @Column(name = "preferred_nm", length = 50)
    private String preferredNm;
    @Basic
    @Column(name = "hm_street_addr1", length = 100)
    private String hmStreetAddr1;
    @Basic
    @Column(name = "hm_street_addr2", length = 100)
    private String hmStreetAddr2;
    @Basic
    @Column(name = "hm_city_cd", length = 20)
    private String hmCityCd;
    @Basic
    @Column(name = "hm_city_desc_txt", length = 100)
    private String hmCityDescTxt;
    @Basic
    @Column(name = "hm_state_cd", length = 20)
    private String hmStateCd;
    @Basic
    @Column(name = "hm_zip_cd", length = 20)
    private String hmZipCd;
    @Basic
    @Column(name = "hm_cnty_cd", length = 20)
    private String hmCntyCd;
    @Basic
    @Column(name = "hm_cntry_cd", length = 20)
    private String hmCntryCd;
    @Basic
    @Column(name = "hm_phone_nbr", length = 20)
    private String hmPhoneNbr;
    @Basic
    @Column(name = "hm_phone_cntry_cd", length = 20)
    private String hmPhoneCntryCd;
    @Basic
    @Column(name = "hm_email_addr", length = 100)
    private String hmEmailAddr;
    @Basic
    @Column(name = "cell_phone_nbr", length = 20)
    private String cellPhoneNbr;
    @Basic
    @Column(name = "wk_street_addr1", length = 100)
    private String wkStreetAddr1;
    @Basic
    @Column(name = "wk_street_addr2", length = 100)
    private String wkStreetAddr2;
    @Basic
    @Column(name = "wk_city_cd", length = 20)
    private String wkCityCd;
    @Basic
    @Column(name = "wk_city_desc_txt", length = 100)
    private String wkCityDescTxt;
    @Basic
    @Column(name = "wk_state_cd", length = 20)
    private String wkStateCd;
    @Basic
    @Column(name = "wk_zip_cd", length = 20)
    private String wkZipCd;
    @Basic
    @Column(name = "wk_cnty_cd", length = 20)
    private String wkCntyCd;
    @Basic
    @Column(name = "wk_cntry_cd", length = 20)
    private String wkCntryCd;
    @Basic
    @Column(name = "wk_phone_nbr", length = 20)
    private String wkPhoneNbr;
    @Basic
    @Column(name = "wk_phone_cntry_cd", length = 20)
    private String wkPhoneCntryCd;
    @Basic
    @Column(name = "wk_email_addr", length = 100)
    private String wkEmailAddr;
    @Basic
    @Column(name = "SSN", length = 100)
    private String ssn;
    @Basic
    @Column(name = "medicaid_num", length = 100)
    private String medicaidNum;
    @Basic
    @Column(name = "dl_num", length = 100)
    private String dlNum;
    @Basic
    @Column(name = "dl_state_cd", length = 20)
    private String dlStateCd;
    @Basic
    @Column(name = "race_cd", length = 20)
    private String raceCd;
    @Basic
    @Column(name = "race_seq_nbr")
    private Short raceSeqNbr;
    @Basic
    @Column(name = "race_category_cd", length = 20)
    private String raceCategoryCd;
    @Basic
    @Column(name = "ethnicity_group_cd", length = 20)
    private String ethnicityGroupCd;
    @Basic
    @Column(name = "ethnic_group_seq_nbr")
    private Short ethnicGroupSeqNbr;
    @Basic
    @Column(name = "adults_in_house_nbr")
    private Short adultsInHouseNbr;
    @Basic
    @Column(name = "children_in_house_nbr")
    private Short childrenInHouseNbr;
    @Basic
    @Column(name = "birth_city_cd", length = 20)
    private String birthCityCd;
    @Basic
    @Column(name = "birth_city_desc_txt", length = 100)
    private String birthCityDescTxt;
    @Basic
    @Column(name = "birth_cntry_cd", length = 20)
    private String birthCntryCd;
    @Basic
    @Column(name = "birth_state_cd", length = 20)
    private String birthStateCd;
    @Basic
    @Column(name = "race_desc_txt", length = 100)
    private String raceDescTxt;
    @Basic
    @Column(name = "ethnic_group_desc_txt", length = 100)
    private String ethnicGroupDescTxt;
    @Basic
    @Column(name = "version_ctrl_nbr", nullable = false)
    private short versionCtrlNbr;
    @Basic
    @Column(name = "as_of_Date_admin")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDateTime asOfLocalDateTimeAdmin;
    @Basic
    @Column(name = "as_of_Date_ethnicity")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDateTime asOfLocalDateTimeEthnicity;
    @Basic
    @Column(name = "as_of_Date_general")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDateTime asOfLocalDateTimeGeneral;
    @Basic
    @Column(name = "as_of_Date_morbidity")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDateTime asOfLocalDateTimeMorbidity;
    @Basic
    @Column(name = "as_of_Date_sex")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDateTime asOfLocalDateTimeSex;
    @Basic
    @Column(name = "electronic_ind", length = 1)
    private String electronicInd;
    @Basic
    @Column(name = "person_parent_uid")
    private Long personParentUid;
    @Basic
    @Column(name = "dedup_match_ind", length = 1)
    private String dedupMatchInd;
    @Basic
    @Column(name = "group_nbr")
    private Integer groupNbr;
    @Basic
    @Column(name = "group_time")
    private LocalDateTime groupTime;
    @Basic
    @Column(name = "edx_ind", length = 1)
    private String edxInd;
    @Basic
    @Column(name = "speaks_english_cd", length = 20)
    private String speaksEnglishCd;
    @Basic
    @Column(name = "additional_gender_cd", length = 50)
    private String additionalGenderCd;
    @Basic
    @Column(name = "ehars_id", length = 20)
    private String eharsId;
    @Basic
    @Column(name = "ethnic_unk_reason_cd", length = 20)
    private String ethnicUnkReasonCd;
    @Basic
    @Column(name = "sex_unk_reason_cd", length = 20)
    private String sexUnkReasonCd;
}
