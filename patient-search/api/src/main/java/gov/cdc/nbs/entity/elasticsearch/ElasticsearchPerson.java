package gov.cdc.nbs.entity.elasticsearch;

import static gov.cdc.nbs.config.ElasticSearchConfig.DATE_PATTERN;

import java.time.Instant;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

import gov.cdc.nbs.entity.enums.Deceased;
import gov.cdc.nbs.entity.enums.Ethnicity;
import gov.cdc.nbs.entity.enums.Gender;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.enums.Suffix;
import gov.cdc.nbs.entity.enums.converter.DeceasedConverter;
import gov.cdc.nbs.entity.enums.converter.EthnicityConverter;
import gov.cdc.nbs.entity.enums.converter.InstantConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "person")
public class ElasticsearchPerson {
    @Id
    private String id;

    @Field(name = "person_uid", type = FieldType.Long)
    private Long personUid;

    @Field(name = "add_reason_cd", type = FieldType.Keyword)
    private String addReasonCd;

    @Field(name = "add_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant addTime;

    @Field(name = "add_user_id", type = FieldType.Long)
    private Long addUserId;

    @Field(name = "administrative_gender_cd", type = FieldType.Keyword)
    private String administrativeGenderCd;

    @Field(name = "age_calc", type = FieldType.Short)
    private Short ageCalc;

    @Field(name = "age_calc_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant ageCalcTime;

    @Field(name = "age_calc_unit_cd", type = FieldType.Keyword)
    private Character ageCalcUnitCd;

    @Field(name = "age_category_cd", type = FieldType.Keyword)
    private String ageCategoryCd;

    @Field(name = "age_reported", type = FieldType.Keyword)
    private String ageReported;

    @Field(name = "age_reported_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant ageReportedTime;

    @Field(name = "age_reported_unit_cd", type = FieldType.Keyword)
    private String ageReportedUnitCd;

    @Field(name = "birth_gender_cd", type = FieldType.Keyword)
    private Gender birthGenderCd;

    @Field(name = "birth_order_nbr", type = FieldType.Short)
    private Short birthOrderNbr;

    @Field(name = "birth_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant birthTime;

    @Field(name = "birth_time_calc", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant birthTimeCalc;

    @Field(name = "cd", type = FieldType.Keyword)
    private String cd;

    @Field(name = "cd_desc_txt", type = FieldType.Text)
    private String cdDescTxt;

    @Field(name = "curr_sex_cd", type = FieldType.Keyword)
    private Gender currSexCd;

    @Field(name = "deceased_ind_cd", type = FieldType.Keyword)
    @ValueConverter(DeceasedConverter.class)
    private Deceased deceasedIndCd;

    @Field(name = "deceased_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant deceasedTime;

    @Field(name = "description", type = FieldType.Text)
    private String description;

    @Field(name = "education_level_cd", type = FieldType.Keyword)
    private String educationLevelCd;

    @Field(name = "education_level_desc_txt", type = FieldType.Text)
    private String educationLevelDescTxt;

    @Field(name = "ethnic_group_ind", type = FieldType.Keyword)
    @ValueConverter(EthnicityConverter.class)
    private Ethnicity ethnicGroupInd;

    @Field(name = "last_chg_reason_cd", type = FieldType.Keyword)
    private String lastChgReasonCd;

    @Field(name = "last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant lastChgTime;

    @Field(name = "last_chg_user_id", type = FieldType.Long)
    private Long lastChgUserId;

    @Field(name = "local_id", type = FieldType.Keyword)
    private String localId;

    @Field(name = "marital_status_cd", type = FieldType.Keyword)
    private String maritalStatusCd;

    @Field(name = "marital_status_desc_txt", type = FieldType.Text)
    private String maritalStatusDescTxt;

    @Field(name = "mothers_maiden_nm", type = FieldType.Text)
    private String mothersMaidenNm;

    @Field(name = "multiple_birth_ind", type = FieldType.Keyword)
    private String multipleBirthInd;

    @Field(name = "occupation_cd", type = FieldType.Keyword)
    private String occupationCd;

    @Field(name = "preferred_gender_cd", type = FieldType.Keyword)
    private Gender preferredGenderCd;

    @Field(name = "prim_lang_cd", type = FieldType.Keyword)
    private String primLangCd;

    @Field(name = "prim_lang_desc_txt", type = FieldType.Text)
    private String primLangDescTxt;

    @Field(name = "record_status_cd", type = FieldType.Keyword)
    private RecordStatus recordStatusCd;

    @Field(name = "record_status_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant recordStatusTime;

    @Field(name = "status_cd", type = FieldType.Keyword)
    private Character statusCd;

    @Field(name = "status_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant statusTime;

    @Field(name = "survived_ind_cd", type = FieldType.Keyword)
    private Character survivedIndCd;

    @Field(name = "user_affiliation_txt", type = FieldType.Text)
    private String userAffiliationTxt;

    @Field(name = "first_nm", type = FieldType.Text)
    private String firstNm;

    // allows sorting
    @MultiField(mainField = @Field(name = "last_nm", type = FieldType.Keyword), otherFields = {
            @InnerField(suffix = "text", type = FieldType.Text)
    })
    private String lastNm;

    @Field(name = "middle_nm", type = FieldType.Text)
    private String middleNm;

    @Field(name = "nm_prefix", type = FieldType.Text)
    private String nmPrefix;

    @Field(name = "nm_suffix", type = FieldType.Text)
    private Suffix nmSuffix;

    @Field(name = "preferred_nm", type = FieldType.Text)
    private String preferredNm;

    @Field(name = "hm_street_addr1", type = FieldType.Text)
    private String hmStreetAddr1;

    @Field(name = "hm_street_addr2", type = FieldType.Text)
    private String hmStreetAddr2;

    @Field(name = "hm_city_cd", type = FieldType.Keyword)
    private String hmCityCd;

    @Field(name = "hm_city_desc_txt", type = FieldType.Text)
    private String hmCityDescTxt;

    @Field(name = "hm_state_cd", type = FieldType.Keyword)
    private String hmStateCd;

    @Field(name = "hm_zip_cd", type = FieldType.Keyword)
    private String hmZipCd;

    @Field(name = "hm_cnty_cd", type = FieldType.Keyword)
    private String hmCntyCd;

    @Field(name = "hm_cntry_cd", type = FieldType.Keyword)
    private String hmCntryCd;

    @Field(name = "hm_phone_nbr", type = FieldType.Text)
    private String hmPhoneNbr;

    @Field(name = "hm_phone_cntry_cd", type = FieldType.Keyword)
    private String hmPhoneCntryCd;

    @Field(name = "hm_email_addr", type = FieldType.Keyword)
    private String hmEmailAddr;

    @Field(name = "cell_phone_nbr", type = FieldType.Keyword)
    private String cellPhoneNbr;

    @Field(name = "wk_street_addr1", type = FieldType.Text)
    private String wkStreetAddr1;

    @Field(name = "wk_street_addr2", type = FieldType.Text)
    private String wkStreetAddr2;

    @Field(name = "wk_city_cd", type = FieldType.Keyword)
    private String wkCityCd;

    @Field(name = "wk_city_desc_txt", type = FieldType.Text)
    private String wkCityDescTxt;

    @Field(name = "wk_state_cd", type = FieldType.Keyword)
    private String wkStateCd;

    @Field(name = "wk_zip_cd", type = FieldType.Keyword)
    private String wkZipCd;

    @Field(name = "wk_cnty_cd", type = FieldType.Keyword)
    private String wkCntyCd;

    @Field(name = "wk_cntry_cd", type = FieldType.Keyword)
    private String wkCntryCd;

    @Field(name = "wk_phone_nbr", type = FieldType.Keyword)
    private String wkPhoneNbr;

    @Field(name = "wk_phone_cntry_cd", type = FieldType.Keyword)
    private String wkPhoneCntryCd;

    @Field(name = "wk_email_addr", type = FieldType.Keyword)
    private String wkEmailAddr;

    @Field(name = "SSN", type = FieldType.Keyword)
    private String ssn;

    @Field(name = "medicaid_num", type = FieldType.Keyword)
    private String medicaidNum;

    @Field(name = "dl_num", type = FieldType.Keyword)
    private String dlNum;

    @Field(name = "dl_state_cd", type = FieldType.Keyword)
    private String dlStateCd;

    @Field(name = "race_cd", type = FieldType.Keyword)
    private String raceCd;

    @Field(name = "race_seq_nbr", type = FieldType.Short)
    private Short raceSeqNbr;

    @Field(name = "race_category_cd", type = FieldType.Keyword)
    private String raceCategoryCd;

    @Field(name = "ethnicity_group_cd", type = FieldType.Keyword)
    private String ethnicityGroupCd;

    @Field(name = "ethnic_group_seq_nbr", type = FieldType.Short)
    private Short ethnicGroupSeqNbr;

    @Field(name = "adults_in_house_nbr", type = FieldType.Short)
    private Short adultsInHouseNbr;

    @Field(name = "children_in_house_nbr", type = FieldType.Short)
    private Short childrenInHouseNbr;

    @Field(name = "birth_city_cd", type = FieldType.Keyword)
    private String birthCityCd;

    @Field(name = "birth_city_desc_txt", type = FieldType.Text)
    private String birthCityDescTxt;

    @Field(name = "birth_cntry_cd", type = FieldType.Keyword)
    private String birthCntryCd;

    @Field(name = "birth_state_cd", type = FieldType.Keyword)
    private String birthStateCd;

    @Field(name = "race_desc_txt", type = FieldType.Text)
    private String raceDescTxt;

    @Field(name = "ethnic_group_desc_txt", type = FieldType.Text)
    private String ethnicGroupDescTxt;

    @Field(name = "version_ctrl_nbr", type = FieldType.Short)
    private Short versionCtrlNbr;

    @Field(name = "as_of_date_admin", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant asOfDateAdmin;

    @Field(name = "as_of_date_ethnicity", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant asOfDateEthnicity;

    @Field(name = "as_of_date_general", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant asOfDateGeneral;

    @Field(name = "as_of_date_morbidity", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant asOfDateMorbidity;

    @Field(name = "as_of_date_sex", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant asOfDateSex;

    @Field(name = "electronic_ind", type = FieldType.Keyword)
    private Character electronicInd;

    @Field(name = "dedup_match_ind", type = FieldType.Keyword)
    private Character dedupMatchInd;

    @Field(name = "group_nbr")
    private Integer groupNbr;

    @Field(name = "group_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant groupTime;

    @Field(name = "edx_ind", type = FieldType.Keyword)
    private String edxInd;

    @Field(name = "speaks_english_cd", type = FieldType.Keyword)
    private String speaksEnglishCd;

    @Field(name = "additional_gender_cd", type = FieldType.Keyword)
    private Gender additionalGenderCd;

    @Field(name = "ehars_id", type = FieldType.Keyword)
    private String eharsId;

    @Field(name = "ethnic_unk_reason_cd", type = FieldType.Keyword)
    private String ethnicUnkReasonCd;

    @Field(name = "sex_unk_reason_cd", type = FieldType.Keyword)
    private String sexUnkReasonCd;
}
