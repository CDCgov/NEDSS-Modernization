package gov.cdc.nbs.entity.elasticsearch;

import static gov.cdc.nbs.util.Constants.DATE_PATTERN;

import java.time.Instant;
import java.util.List;

import javax.persistence.Id;
import gov.cdc.nbs.patient.search.ElasticsearchGenderConverter;
import gov.cdc.nbs.patient.search.ElasticsearchSuffixConverter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.enums.converter.DeceasedConverter;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.entity.enums.converter.ElasticsearchInstantValueConverter;
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
    public static final String PERSON_UID = "person_uid";
    public static final String ADD_REASON_CD = "add_reason_cd";
    public static final String ADD_TIME = "add_time";
    public static final String ADD_USER_ID = "add_user_id";
    public static final String ADMINISTRATIVE_GENDER_CD = "administrative_gender_cd";
    public static final String AGE_CALC = "age_calc";
    public static final String AGE_CALC_TIME = "age_calc_time";
    public static final String AGE_CALC_UNIT_CD = "age_calc_unit_cd";
    public static final String AGE_CATEGORY_CD = "age_category_cd";
    public static final String AGE_REPORTED = "age_reported";
    public static final String AGE_REPORTED_TIME = "age_reported_time";
    public static final String AGE_REPORTED_UNIT_CD = "age_reported_unit_cd";
    public static final String BIRTH_GENDER_CD = "birth_gender_cd";
    public static final String BIRTH_ORDER_NBR = "birth_order_nbr";
    public static final String BIRTH_TIME = "birth_time";
    public static final String BIRTH_TIME_CALC = "birth_time_calc";
    public static final String CD_FIELD = "cd";
    public static final String CD_DESC_TXT = "cd_desc_txt";
    public static final String CURR_SEX_CD = "curr_sex_cd";
    public static final String DECEASED_IND_CD = "deceased_ind_cd";
    public static final String DECEASED_TIME = "deceased_time";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String EDUCATION_LEVEL_CD = "education_level_cd";
    public static final String EDUCATION_LEVEL_DESC_TXT = "education_level_desc_txt";
    public static final String ETHNIC_GROUP_IND = "ethnic_group_ind";
    public static final String LAST_CHG_REASON_CD = "last_chg_reason_cd";
    public static final String LAST_CHG_TIME = "last_chg_time";
    public static final String LAST_CHG_USER_ID = "last_chg_user_id";
    public static final String LOCAL_ID = "local_id";
    public static final String MARITAL_STATUS_CD = "marital_status_cd";
    public static final String MARITAL_STATUS_DESC_TXT = "marital_status_desc_txt";
    public static final String MOTHERS_MAIDEN_NM = "mothers_maiden_nm";
    public static final String MULTIPLE_BIRTH_IND = "multiple_birth_ind";
    public static final String OCCUPATION_CD = "occupation_cd";
    public static final String PREFERRED_GENDER_CD = "preferred_gender_cd";
    public static final String PRIM_LANG_CD = "prim_lang_cd";
    public static final String PRIM_LANG_DESC_TXT = "prim_lang_desc_txt";
    public static final String RECORD_STATUS_CD = "record_status_cd";
    public static final String RECORD_STATUS_TIME = "record_status_time";
    public static final String STATUS_CD = "status_cd";
    public static final String STATUS_TIME = "status_time";
    public static final String SURVIVED_IND_CD = "survived_ind_cd";
    public static final String USER_AFFILIATION_TXT = "user_affiliation_txt";
    public static final String FIRST_NM = "first_nm";
    public static final String LAST_NM = "last_nm";
    public static final String TEXT = "text";
    public static final String MIDDLE_NM = "middle_nm";
    public static final String NM_PREFIX = "nm_prefix";
    public static final String NM_SUFFIX = "nm_suffix";
    public static final String PREFERRED_NM = "preferred_nm";
    public static final String HM_STREET_ADDR1 = "hm_street_addr1";
    public static final String HM_STREET_ADDR2 = "hm_street_addr2";
    public static final String HM_CITY_CD = "hm_city_cd";
    public static final String HM_CITY_DESC_TXT = "hm_city_desc_txt";
    public static final String HM_STATE_CD = "hm_state_cd";
    public static final String HM_ZIP_CD = "hm_zip_cd";
    public static final String HM_CNTY_CD = "hm_cnty_cd";
    public static final String HM_CNTRY_CD = "hm_cntry_cd";
    public static final String HM_PHONE_NBR = "hm_phone_nbr";
    public static final String HM_PHONE_CNTRY_CD = "hm_phone_cntry_cd";
    public static final String HM_EMAIL_ADDR = "hm_email_addr";
    public static final String CELL_PHONE_NBR = "cell_phone_nbr";
    public static final String WK_STREET_ADDR1 = "wk_street_addr1";
    public static final String WK_STREET_ADDR2 = "wk_street_addr2";
    public static final String WK_CITY_CD = "wk_city_cd";
    public static final String WK_CITY_DESC_TXT = "wk_city_desc_txt";
    public static final String WK_STATE_CD = "wk_state_cd";
    public static final String WK_ZIP_CD = "wk_zip_cd";
    public static final String WK_CNTY_CD = "wk_cnty_cd";
    public static final String WK_CNTRY_CD = "wk_cntry_cd";
    public static final String WK_PHONE_NBR = "wk_phone_nbr";
    public static final String WK_PHONE_CNTRY_CD = "wk_phone_cntry_cd";
    public static final String WK_EMAIL_ADDR = "wk_email_addr";
    public static final String SSN_FIELD = "SSN";
    public static final String MEDICAID_NUM = "medicaid_num";
    public static final String DL_NUM = "dl_num";
    public static final String DL_STATE_CD = "dl_state_cd";
    public static final String RACE_CD = "race_cd";
    public static final String RACE_SEQ_NBR = "race_seq_nbr";
    public static final String RACE_CATEGORY_CD = "race_category_cd";
    public static final String ETHNICITY_GROUP_CD = "ethnicity_group_cd";
    public static final String ETHNIC_GROUP_SEQ_NBR = "ethnic_group_seq_nbr";
    public static final String ADULTS_IN_HOUSE_NBR = "adults_in_house_nbr";
    public static final String CHILDREN_IN_HOUSE_NBR = "children_in_house_nbr";
    public static final String BIRTH_CITY_CD = "birth_city_cd";
    public static final String BIRTH_CITY_DESC_TXT = "birth_city_desc_txt";
    public static final String BIRTH_CNTRY_CD = "birth_cntry_cd";
    public static final String BIRTH_STATE_CD = "birth_state_cd";
    public static final String RACE_DESC_TXT = "race_desc_txt";
    public static final String ETHNIC_GROUP_DESC_TXT = "ethnic_group_desc_txt";
    public static final String VERSION_CTRL_NBR = "version_ctrl_nbr";
    public static final String AS_OF_DATE_ADMIN = "as_of_date_admin";
    public static final String AS_OF_DATE_ETHNICITY = "as_of_date_ethnicity";
    public static final String AS_OF_DATE_GENERAL = "as_of_date_general";
    public static final String AS_OF_DATE_MORBIDITY = "as_of_date_morbidity";
    public static final String AS_OF_DATE_SEX = "as_of_date_sex";
    public static final String ELECTRONIC_IND = "electronic_ind";
    public static final String DEDUP_MATCH_IND = "dedup_match_ind";
    public static final String GROUP_NBR = "group_nbr";
    public static final String GROUP_TIME = "group_time";
    public static final String EDX_IND = "edx_ind";
    public static final String SPEAKS_ENGLISH_CD = "speaks_english_cd";
    public static final String ADDITIONAL_GENDER_CD = "additional_gender_cd";
    public static final String EHARS_ID = "ehars_id";
    public static final String ETHNIC_UNK_REASON_CD = "ethnic_unk_reason_cd";
    public static final String SEX_UNK_REASON_CD = "sex_unk_reason_cd";
    public static final String NAME_FIELD = "name";
    public static final String ADDRESS_FIELD = "address";
    public static final String PHONE_FIELD = "phone";
    public static final String EMAIL_FIELD = "email";
    public static final String RACE_FIELD = "race";
    public static final String ENTITY_ID_FIELD = "entity_id";

    @Id
    private String id;

    @Field(name = PERSON_UID, type = FieldType.Long)
    private Long personUid;

    @Field(name = ADD_REASON_CD, type = FieldType.Keyword)
    private String addReasonCd;

    @Field(name = ADD_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant addTime;

    @Field(name = ADD_USER_ID, type = FieldType.Long)
    private Long addUserId;

    @Field(name = ADMINISTRATIVE_GENDER_CD, type = FieldType.Keyword)
    private String administrativeGenderCd;

    @Field(name = AGE_CALC, type = FieldType.Short)
    private Short ageCalc;

    @Field(name = AGE_CALC_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant ageCalcTime;

    @Field(name = AGE_CALC_UNIT_CD, type = FieldType.Keyword)
    private Character ageCalcUnitCd;

    @Field(name = AGE_CATEGORY_CD, type = FieldType.Keyword)
    private String ageCategoryCd;

    @Field(name = AGE_REPORTED, type = FieldType.Keyword)
    private String ageReported;

    @Field(name = AGE_REPORTED_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant ageReportedTime;

    @Field(name = AGE_REPORTED_UNIT_CD, type = FieldType.Keyword)
    private String ageReportedUnitCd;

    @ValueConverter(ElasticsearchGenderConverter.class)
    @Field(name = BIRTH_GENDER_CD, type = FieldType.Keyword)
    private Gender birthGenderCd;

    @Field(name = BIRTH_ORDER_NBR, type = FieldType.Short)
    private Short birthOrderNbr;

    @Field(name = BIRTH_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant birthTime;

    @Field(name = BIRTH_TIME_CALC, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant birthTimeCalc;

    @Field(name = CD_FIELD, type = FieldType.Keyword)
    private String cd;

    @Field(name = CD_DESC_TXT, type = FieldType.Text)
    private String cdDescTxt;

    @ValueConverter(ElasticsearchGenderConverter.class)
    @Field(name = CURR_SEX_CD, type = FieldType.Keyword)
    private Gender currSexCd;

    @Field(name = DECEASED_IND_CD, type = FieldType.Keyword)
    @ValueConverter(DeceasedConverter.class)
    private Deceased deceasedIndCd;

    @Field(name = DECEASED_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant deceasedTime;

    @Field(name = DESCRIPTION_FIELD, type = FieldType.Text)
    private String description;

    @Field(name = EDUCATION_LEVEL_CD, type = FieldType.Keyword)
    private String educationLevelCd;

    @Field(name = EDUCATION_LEVEL_DESC_TXT, type = FieldType.Text)
    private String educationLevelDescTxt;

    @Field(name = ETHNIC_GROUP_IND, type = FieldType.Keyword)
    private String ethnicGroupInd;

    @Field(name = LAST_CHG_REASON_CD, type = FieldType.Keyword)
    private String lastChgReasonCd;

    @Field(name = LAST_CHG_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant lastChgTime;

    @Field(name = LAST_CHG_USER_ID, type = FieldType.Long)
    private Long lastChgUserId;

    @Field(name = LOCAL_ID, type = FieldType.Keyword)
    private String localId;

    @Field(name = MARITAL_STATUS_CD, type = FieldType.Keyword)
    private String maritalStatusCd;

    @Field(name = MARITAL_STATUS_DESC_TXT, type = FieldType.Text)
    private String maritalStatusDescTxt;

    @Field(name = MOTHERS_MAIDEN_NM, type = FieldType.Text)
    private String mothersMaidenNm;

    @Field(name = MULTIPLE_BIRTH_IND, type = FieldType.Keyword)
    private String multipleBirthInd;

    @Field(name = OCCUPATION_CD, type = FieldType.Keyword)
    private String occupationCd;

    @Field(name = PREFERRED_GENDER_CD, type = FieldType.Keyword)
    private String preferredGenderCd;

    @Field(name = PRIM_LANG_CD, type = FieldType.Keyword)
    private String primLangCd;

    @Field(name = PRIM_LANG_DESC_TXT, type = FieldType.Text)
    private String primLangDescTxt;

    @Field(name = RECORD_STATUS_CD, type = FieldType.Keyword)
    private RecordStatus recordStatusCd;

    @Field(name = RECORD_STATUS_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant recordStatusTime;

    @Field(name = STATUS_CD, type = FieldType.Keyword)
    private Character statusCd;

    @Field(name = STATUS_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant statusTime;

    @Field(name = SURVIVED_IND_CD, type = FieldType.Keyword)
    private Character survivedIndCd;

    @Field(name = USER_AFFILIATION_TXT, type = FieldType.Text)
    private String userAffiliationTxt;

    @Field(name = FIRST_NM, type = FieldType.Text)
    private String firstNm;

    // allows sorting
    @MultiField(mainField = @Field(name = LAST_NM, type = FieldType.Text, fielddata = true), otherFields = {
            @InnerField(suffix = TEXT, type = FieldType.Text)
    })
    private String lastNm;

    @Field(name = MIDDLE_NM, type = FieldType.Text)
    private String middleNm;

    @Field(name = NM_PREFIX, type = FieldType.Text)
    private String nmPrefix;

    @Field(name = NM_SUFFIX, type = FieldType.Text)
    @ValueConverter(ElasticsearchSuffixConverter.class)
    private Suffix nmSuffix;

    @Field(name = PREFERRED_NM, type = FieldType.Text)
    private String preferredNm;

    @Field(name = HM_STREET_ADDR1, type = FieldType.Text)
    private String hmStreetAddr1;

    @Field(name = HM_STREET_ADDR2, type = FieldType.Text)
    private String hmStreetAddr2;

    @Field(name = HM_CITY_CD, type = FieldType.Keyword)
    private String hmCityCd;

    @Field(name = HM_CITY_DESC_TXT, type = FieldType.Text)
    private String hmCityDescTxt;

    @Field(name = HM_STATE_CD, type = FieldType.Keyword)
    private String hmStateCd;

    @Field(name = HM_ZIP_CD, type = FieldType.Keyword)
    private String hmZipCd;

    @Field(name = HM_CNTY_CD, type = FieldType.Keyword)
    private String hmCntyCd;

    @Field(name = HM_CNTRY_CD, type = FieldType.Keyword)
    private String hmCntryCd;

    @Field(name = HM_PHONE_NBR, type = FieldType.Text)
    private String hmPhoneNbr;

    @Field(name = HM_PHONE_CNTRY_CD, type = FieldType.Keyword)
    private String hmPhoneCntryCd;

    @Field(name = HM_EMAIL_ADDR, type = FieldType.Keyword)
    private String hmEmailAddr;

    @Field(name = CELL_PHONE_NBR, type = FieldType.Keyword)
    private String cellPhoneNbr;

    @Field(name = WK_STREET_ADDR1, type = FieldType.Text)
    private String wkStreetAddr1;

    @Field(name = WK_STREET_ADDR2, type = FieldType.Text)
    private String wkStreetAddr2;

    @Field(name = WK_CITY_CD, type = FieldType.Keyword)
    private String wkCityCd;

    @Field(name = WK_CITY_DESC_TXT, type = FieldType.Text)
    private String wkCityDescTxt;

    @Field(name = WK_STATE_CD, type = FieldType.Keyword)
    private String wkStateCd;

    @Field(name = WK_ZIP_CD, type = FieldType.Keyword)
    private String wkZipCd;

    @Field(name = WK_CNTY_CD, type = FieldType.Keyword)
    private String wkCntyCd;

    @Field(name = WK_CNTRY_CD, type = FieldType.Keyword)
    private String wkCntryCd;

    @Field(name = WK_PHONE_NBR, type = FieldType.Keyword)
    private String wkPhoneNbr;

    @Field(name = WK_PHONE_CNTRY_CD, type = FieldType.Keyword)
    private String wkPhoneCntryCd;

    @Field(name = WK_EMAIL_ADDR, type = FieldType.Keyword)
    private String wkEmailAddr;

    @Field(name = SSN_FIELD, type = FieldType.Keyword)
    private String ssn;

    @Field(name = MEDICAID_NUM, type = FieldType.Keyword)
    private String medicaidNum;

    @Field(name = DL_NUM, type = FieldType.Keyword)
    private String dlNum;

    @Field(name = DL_STATE_CD, type = FieldType.Keyword)
    private String dlStateCd;

    @Field(name = RACE_CD, type = FieldType.Keyword)
    private String raceCd;

    @Field(name = RACE_SEQ_NBR, type = FieldType.Short)
    private Short raceSeqNbr;

    @Field(name = RACE_CATEGORY_CD, type = FieldType.Keyword)
    private String raceCategoryCd;

    @Field(name = ETHNICITY_GROUP_CD, type = FieldType.Keyword)
    private String ethnicityGroupCd;

    @Field(name = ETHNIC_GROUP_SEQ_NBR, type = FieldType.Short)
    private Short ethnicGroupSeqNbr;

    @Field(name = ADULTS_IN_HOUSE_NBR, type = FieldType.Short)
    private Short adultsInHouseNbr;

    @Field(name = CHILDREN_IN_HOUSE_NBR, type = FieldType.Short)
    private Short childrenInHouseNbr;

    @Field(name = BIRTH_CITY_CD, type = FieldType.Keyword)
    private String birthCityCd;

    @Field(name = BIRTH_CITY_DESC_TXT, type = FieldType.Text)
    private String birthCityDescTxt;

    @Field(name = BIRTH_CNTRY_CD, type = FieldType.Keyword)
    private String birthCntryCd;

    @Field(name = BIRTH_STATE_CD, type = FieldType.Keyword)
    private String birthStateCd;

    @Field(name = RACE_DESC_TXT, type = FieldType.Text)
    private String raceDescTxt;

    @Field(name = ETHNIC_GROUP_DESC_TXT, type = FieldType.Text)
    private String ethnicGroupDescTxt;

    @Field(name = VERSION_CTRL_NBR, type = FieldType.Short)
    private Short versionCtrlNbr;

    @Field(name = AS_OF_DATE_ADMIN, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant asOfDateAdmin;

    @Field(name = AS_OF_DATE_ETHNICITY, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant asOfDateEthnicity;

    @Field(name = AS_OF_DATE_GENERAL, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant asOfDateGeneral;

    @Field(name = AS_OF_DATE_MORBIDITY, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant asOfDateMorbidity;

    @Field(name = AS_OF_DATE_SEX, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant asOfDateSex;

    @Field(name = ELECTRONIC_IND, type = FieldType.Keyword)
    private Character electronicInd;

    @Field(name = DEDUP_MATCH_IND, type = FieldType.Keyword)
    private Character dedupMatchInd;

    @Field(name = GROUP_NBR)
    private Integer groupNbr;

    @Field(name = GROUP_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant groupTime;

    @Field(name = EDX_IND, type = FieldType.Keyword)
    private String edxInd;

    @Field(name = SPEAKS_ENGLISH_CD, type = FieldType.Keyword)
    private String speaksEnglishCd;

    @Field(name = ADDITIONAL_GENDER_CD, type = FieldType.Keyword)
    private String additionalGenderCd;

    @Field(name = EHARS_ID, type = FieldType.Keyword)
    private String eharsId;

    @Field(name = ETHNIC_UNK_REASON_CD, type = FieldType.Keyword)
    private String ethnicUnkReasonCd;

    @Field(name = SEX_UNK_REASON_CD, type = FieldType.Keyword)
    private String sexUnkReasonCd;

    @Field(name = NAME_FIELD, type = FieldType.Nested)
    private List<NestedName> name;

    @Field(name = ADDRESS_FIELD, type = FieldType.Nested)
    private List<NestedAddress> address;

    @Field(name = PHONE_FIELD, type = FieldType.Nested)
    private List<NestedPhone> phone;

    @Field(name = EMAIL_FIELD, type = FieldType.Nested)
    private List<NestedEmail> email;

    @Field(name = RACE_FIELD, type = FieldType.Nested)
    private List<NestedRace> race;

    @Field(name = ENTITY_ID_FIELD, type = FieldType.Nested)
    private List<NestedEntityId> entityId;
}
