package gov.cdc.nedss.elr.ejb.msginprocessor.helper;

import gov.cdc.nedss.util.*;
/**
 * Title:
 * Description:  Contains Constants and SQL statements utilized in the migration
 *               of elr messages from msgIn to ODS.
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ELRConstants {

    public ELRConstants() {}
    public static final String ELR_LOG_PROCESS = "ELR_LOG_PROCESS";
    public static final String TYPE = "LR";
    public static final String ELR_LOCAL_CD = "L";
    public static final String ELR_STATUS_CD_SUCCESS = "S";
    public static final String ELR_STATUS_CD_ERROR = "F";  //change from "E" to "F" for build D
    public static final String ELR_STATUS_CD_INFORMATIONAL = "I";
    public static final String ELR_CLIA = "CLIA";
    public static final String ELR_MSG_STATUS_CD_PROCESSED = "P";
    public static final String ELR_MSG_STATUS_CD_FAILED = "F";

    //ODS
    public static final String ELR_OBS_STATUS_CD_COMPLETED = "D";
    public static final String ELR_OBS_STATUS_CD_SUPERCEDED = "T";
    public static final String ELR_SNOMED_CD = "SNM";
    //MsgIn
    public static final String ELR_OBS_STATUS_CD_FINAL = "F";
    public static final String ELR_OBS_STATUS_CD_CORRECTION = "C";

    public static final String ELECTRONIC_IND = "Y";


    //Process Codes in use for Release1.1
    public static final Integer ELR_ACTIVITY_LOG_DETAIL_TXT_SIZE = new Integer(1000);
    public static final String EXCEPTION_MESSAGE_NOT_AVAILABLE = "Message not available: ";
    public static final String ELR_REPORTING_LAB_TYPE_CD = "FI";
    public static final String ELR_PROCESS_CD_MSGNACTIVTOTIME_NULL = "MSGNACTIVTOTIME_NULL";
    public static final String ELR_PROCESS_CD_ACTIVTOTIME_NULL = "ODSACTIVTOTIME_NULL";
    public static final String ELR_PROCESS_CD_ASTRESULTINTERP_FAIL = "ASTRESULTINTERP_FAIL";
    public static final String ELR_PROCESS_CD_COLLECTOR_FAIL = "COLLECTOR_FAIL";
    public static final String ELR_PROCESS_CD_COPYTOPROVIDER_FAIL = "COPYTOPROVIDER_FAIL";
    public static final String ELR_PROCESS_CD_EMERGCONTACT_FAIL = "EMERGCONTACT_FAIL";
    public static final String ELR_PROCESS_CD_FAILED_ON_ODS_WRITE = "FAILED_ON_ODS_WRITE";
    public static final String ELR_PROCESS_CD_FILLER_FAIL = "FILLER_FAIL";
    public static final String ELR_PROCESS_CD_FINAL_AFTER_CORRECTED = "FINAL_POST_CORRECTED";
    public static final String ELR_PROCESS_CD_SSN_INVALID = "INFO_SSN_INVALID";
//    public static final String ELR_PROCESS_CD_JURIS_ASSIGN = "JURIS_ASSIGN";
    //ELR_PROCESS_CD_JURISDICTION_FAIL SHOULD NOT BE USED. JDAUGHTERY
//    public static final String ELR_PROCESS_CD_JURISDICTION_FAIL = "JURISDICTION_FAIL";
    public static final String JURIS_ASSIGN_1 = "JURIS_ASSIGN_1";
    public static final String JURIS_ASSIGN_2 = "JURIS_ASSIGN_2";
    public static final String JURIS_ASSIGN_3 = "JURIS_ASSIGN_3";
    public static final String ELR_PROCESS_CD_LABTECH_FAIL = "LABTECH_FAIL";
    public static final String ELR_PROCESS_CD_LABTEST_SEQUENCE = "LABTEST_SEQUENCE";
    public static final String ELR_PROCESS_CD_MULTIPLE_COLLECTOR = "MULTIPLE_COLLECTOR";
    public static final String ELR_PROCESS_CD_MULTIPLE_INTERP = "MULTIPLE_INTERP";
    public static final String ELR_PROCESS_CD_MULTIPLE_ORDER_FAC = "MULTIPLE_ORDERFAC";
    public static final String ELR_PROCESS_CD_MULTIPLE_PERFORM_LAB = "MULTIPLE_PERFORMLAB";
    public static final String ELR_PROCESS_CD_MULTIPLE_PROVIDER = "MULTIPLE_PROVIDER";
    public static final String ELR_PROCESS_CD_MULTIPLE_RECEIVEFAC = "MULTIPLE_RECEIVEFAC";
    public static final String ELR_PROCESS_CD_MULTIPLE_SPECIMEN = "MULTIPLE_SPECIMEN";
    public static final String ELR_PROCESS_CD_MULTIPLE_SUBJECT = "MULTIPLE_SUBJECT";
    public static final String ELR_PROCESS_CD_NO_DRUG_NAME = "NO_DRUG_NAME";
    public static final String ELR_PROCESS_CD_NO_LOINC = "NO_LOINC";
    public static final String ELR_PROCESS_CD_NO_ORD_TEST_NAME = "NO_ORDTEST_NAME";
    public static final String ELR_PROCESS_CD_NO_REFLEX_ORDERED_NM = "NO_REFLEX_ORDERED_NM";
    public static final String ELR_PROCESS_CD_NO_REFLEX_RESULT_NM = "NO_REFLEX_RESULT_NM";
    public static final String ELR_PROCESS_CD_NO_RESULT_NAME = "NO_RESULT_NAME";
    public static final String ELR_PROCESS_CD_NO_ZIP = "NO_ZIP";
    public static final String ELR_PROCESS_CD_ORDER_FAC_RETRIEVE = "ORDER_FAC_RETRV";
    public static final String ELR_PROCESS_CD_ORDER_TEST_RETRIEVE = "ORDERTEST_RETRV";
    public static final String ELR_PROCESS_CD_RECEIVEFAC_FAIL = "RECEIVEFAC_RETRV";
    public static final String ELR_PROCESS_CD_SUCCESS = "SUCCESS";
    public static final String ELR_PROCESS_CD_TRANSLATE_ETHNICITY = "TRANSLATE_ETHNICITY";
    public static final String ELR_PROCESS_CD_TRANSLATE_RACE = "TRANSLATE_RACE";
    public static final String ELR_PROCESS_CD_TRANSLATE_SEX = "TRANSLATE_SEX";
    public static final String ELR_PROCESS_CD_TRANSLATE_OBS_METH = "TRANSLATE_OBS_METH";
    public static final String ELR_PROCESS_CD_TRANSLATE_OBS_STAT = "TRANSLATE_OBS_STAT";
    public static final String ELR_PROCESS_CD_FAIL_ON_ODS_WRITE =  "FAILED_ON_ODS_WRITE";
    //ELR_PROCESS_CD_PROGRAM_ASSIGN IS NO LONGER USED. JDAUGHTERY
//    public static final String ELR_PROCESS_CD_PROGRAM_ASSIGN = "PROGRAM_ASSIGN";
    public static final String PROGRAM_ASSIGN_1 = "PROGRAM_ASSIGN_1";
    public static final String PROGRAM_ASSIGN_2 = "PROGRAM_ASSIGN_2";
    public static final String ELR_PROCESS_CD_REPORTING_LAB_LOOKUP = "REPORTING_LAB_LOOKUP";
    public static final String ELR_PROCESS_CD_REPORTING_LAB_RETRIEVAL = "REPORTING_LAB_RETRV";
    public static final String ELR_PROCESS_CD_SUBJECT_RETRIEVE = "SUBJECT_RETRV";
    public static final String ELR_PROCESS_CD_PROVIDER_RETRIEVE = "PROVIDER_RETRV";
    public static final String ELR_PROCESS_CD_PERFORM_LAB_RETRIEVE = "PERFORM_LAB_RETRV";
    public static final String ELR_PROCESS_CD_RESULTINTERP_FAIL = "RESULTINTERP_FAIL";
    public static final String ELR_PROCESS_CD_TRANSCRIPT_FAIL = "TRANSCRIPT_FAIL";
    public static final String ELR_PROCESS_CD_SPECIMEN_RETRIEVE = "SPECIMEN_RETRV";
    public static final String ELR_PROCESS_CD_NO_ORDERINGPROVIDER = "PROVIDER_RETRV";
    public static final String ELR_PROCESS_CD_SYSTEM_EXCEPTION = "SYSTEM_EXCEPTION";
    public static final String ELR_PROCESS_CD_SUBJECTMATCH_CRITERIA = "SUBJECTMATCH_CRITERI";
    public static final String ELR_PROCESS_CD_INVALID_STATUS_COMBINATION = "FINAL_POST_CORRECTED";//using this until correct one is added to srt table.
    public static final String ELR_PROCESS_CD_MULTIPLE_RECEIVE_FAC = "MULTIPLE_RECEIVEFAC";
    public static final String ELR_PROCESS_CD_CLIA_NULL = "NULL_CLIA";

    public static final String ELR_OBSERVATION_RESULT = "Result";
    public static final String ELR_OBSERVATION_ORDER = "Order";
    //public static final String ELR_OBSERVATION_ISOLATE = "C_Order";
    //public static final String ELR_OBSERVATION_DRUGSUSC = "C_Result";
    public static final String ELR_OBSERVATION_REFLEX_ORDER = "R_Order";
    public static final String ELR_OBSERVATION_REFLEX_RESULT = "R_Result";

    public static final String ELR_OBSERVATION_COMPONENT = "COMP";
    public static final String ELR_OBSERVATION_REFERS = "REFR";
    public static final String ELR_OBSERVATION_LOINC = "LN";
    public static final String ELR_LAB180_CD_DESC = "REPORTED PATIENT AGE";
    public static final String ELR_LAB180_DOMAIN_CD = "LAB";
    public static final String ELR_LAB180_SYSTEM_CD = "L";
    public static final String ELR_LAB180_SYSTEM_CD_DESC = "Local";

    public static final String OBSERVATION_VO_COLLECTION_HASHMAP_KEY = "observationVOCollection";
    public static final String ORGANIZATION_VO_HASHMAP_KEY = "OrganizationVO";
    public static final String PERSON_VO_HASHMAP_KEY = "PersonVO";
    public static final String MATERIAL_VO_HASHMAP_KEY = "MaterialVO";
    public static final String ELR_ACTIVITY_LOG_DT_COLLECTION_HASHMAP_KEY = "elrActivityLogVOCollection";
    public static final String PARTICIPATION_DT_COLLECTION_HASHMAP_KEY = "ParticipationDTCollection";
    public static final String PERFORMING_LAB_COLLECTION_HASHMAP_KEY = "PerformingLabCollection";
    public static final String JURISDICTION_HASHMAP_KEY = "Jurisdiction";
    public static final String PROGRAM_AREA_HASHMAP_KEY = "ProgramArea";
    public static final String ELR_ACTRELATIONSHIP_DT_COLLECTION_HASHMAP_KEY = "elrActRelationshipDTCollection";

    public static final String MSG_IN_SUBJECT_UID = "MSG_IN_SUBJECT_UID";
    public static final String MSG_IN_PROVIDER_UID = "MSG_IN_PROVIDER_UID";
    public static final String MSG_IN_COPYTOPROVIDER_UID = "MSG_IN_COPYTOPROVIDER_UID";
    public static final String MSG_IN_EMERGENCYCONTACT_UID = "MSG_IN_EMERGENCYCONTACT_UID";
    public static final String MSG_IN_PRINCIPALRESULTINTERPRETER_UID = "MSG_IN_PRINCIPALRESULTINTERPRETER_UID";
    public static final String MSG_IN_ASSISTANTRESULTINTERPRETER_UID = "MSG_IN_ASSISTANTRESULTINTERPRETER_UID";
    public static final String MSG_IN_TECHNICIAN_UID = "MSG_IN_TECHNICIAN_UID";
    public static final String MSG_IN_COLLECTOR_UID = "MSG_IN_COLLECTOR_UID";
    public static final String MSG_IN_TRANSCRIPTIONIST_UID = "MSG_IN_TECHNICIAN_UID";
    public static final String LEGAL_NAME_CD = "L";
    public static final String ALIAS_NAME_CD = "A";
    public static final String ODS_SUBJECT_PARENT_UID = "ODS_SUBJECT_PARENT_UID";
    public static final String ODS_PROVIDER_UID = "ODS_PROVIDER_UID";
    public static final String ODS_COPYTOPROVIDER_UID = "ODS_COPYTOPROVIDER_UID";
    public static final String ODS_EMERGENCYCONTACT_UID = "ODS_EMERGENCYCONTACT_UID";
    public static final String ODS_PRINCIPALRESULTINTERPRETER_UID = "ODS_PRINCIPALRESULTINTERPRETER_UID";
    public static final String ODS_ASSISTANTRESULTINTERPRETER_UID = "ODS_ASSISTANTRESULTINTERPRETER_UID";
    public static final String ODS_TECHNICIAN_UID = "ODS_TECHNICIAN_UID";
    public static final String ODS_COLLECTOR_UID = "ODS_COLLECTOR_UID";
    public static final String ODS_TRANSCRIPTIONIST_UID = "ODS_TRANSCRIPTIONIST_UID";
    public static final String ODS_ORGANIZATION_UID = "ODS_ORGANIZATION_UID";

    public static final String ELR_PROCESS_CD_SUBJECTMATCH_MULT = "SUBJECTMATCH_MULT";
    public static final String ELR_PROCESS_CD_SUBJECTMATCH_NONE = "SUBJECTMATCH_NO";

    public static final String MESSAGE_ID = "MESSAGE_ID";
    public static final String CREATION_TIME = "CREATION_TIME";

    //These are constants for the SQL statements in the DAOs

    public static final String MSG_IN_TRANSMISSION_MESSAGE_ID_SELECT = "SELECT "
      + "id \"Id\", "
      + "creation_time \"CreationTime\" "
      + "from MsgIn_Transmission "
      + "where entity_uid = ? and transmission_uid = ? ";

    public static final String MSG_IN_TRANSMISSION_SELECT =  "SELECT "
      + "entity_uid \"EntityUid\", "
      + "communication_function_uid \"CommunicationFunctionUid\", "
      + "transmission_uid \"TransmissionUid\", "
      + "creation_time \"CreationTime\", "
      + "creation_time_zone \"CreationTimeZone\", "
      + "id \"Id\", "
      + "security \"Security\" "
      + "from MsgIn_Transmission ";

    public static final String MSG_IN_TRANSMISSION_SET_UID = "where entity_uid = ? ";

    public static final String MSG_IN_TELELOCATOR_SELECT = "SELECT "
      + "tele_locator_uid \"TeleLocatorUid\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "cntry_cd \"CntryCd\", "
      + "email_address \"EmailAddress\", "
      + "extension_txt \"ExtensionTxt\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "phone_nbr_txt \"PhoneNbrTxt\", "
      + "url_address \"UrlAddress\", "
      + "user_affiliation_txt \"UserAffiliationTxt\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\" "
      + "from MsgIn_Tele_locator ";

    public static final String MSG_IN_TELELOCATOR_SET_UID = "where tele_locator_uid = ? " ;

    public static final String MSG_IN_POSTALLOCATOR_SELECT = "SELECT "
      + "postal_locator_uid \"PostalLocatorUid\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "city_cd \"CityCd\", "
      + "city_desc_txt \"CityDescTxt\", "
      + "cntry_cd \"CntryCd\", "
      + "cntry_desc_txt \"CntryDescTxt\", "
      + "cnty_cd \"CntyCd\", "
      + "cnty_desc_txt \"CntyDescTxt\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "state_cd \"StateCd\", "
      + "street_addr1 \"StreetAddr1\", "
      + "street_addr2 \"StreetAddr2\", "
      + "user_affiliation_txt \"UserAffiliationTxt\", "
      + "zip_cd \"ZipCd\", "
      + "census_block_cd \"CensusBlockCd\", "
      + "census_minor_civil_division_cd \"CensusMinorCivilDivisionCd\", "
      + "census_track_cd \"CensusTrackCd\", "
      + "MSA_congress_district_cd \"MSACongressDistrictCd\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "region_district_cd \"RegionDistrictCd\" "
      + "from MsgIn_Postal_locator ";

    public static final String MSG_IN_POSTALLOCATOR_SET_UID =  "where postal_locator_uid = ? " ;

    public static final String MSG_IN_PERSONRACE_SELECT = "SELECT "
      + "person_uid \"PersonUid\", "
      + "race_cd \"RaceCd\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "race_category_cd \"RaceCategoryCd\", "
      + "race_desc_txt \"RaceDescTxt\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "user_affiliation_txt \"UserAffiliationTxt\" "
      + "from MsgIn_Person_race ";

    public static final String MSG_IN_PERSONRACE_SET_UID =  "where person_uid = ? ";

    public static final String MSG_IN_PERSONNAME_SELECT = "SELECT "
      + "person_uid \"PersonUid\", "
      + "person_name_seq \"PersonNameSeq\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "duration_amt \"DurationAmt\", "
      + "duration_unit_cd \"DurationUnitCd\", "
      + "first_nm \"FirstNm\", "
      + "first_nm_sndx \"FirstNmSndx\", "
      + "from_time \"FromTime\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "last_nm \"LastNm\", "
      + "last_nm_sndx \"LastNmSndx\", "
      + "last_nm2 \"LastNm2\", "
      + "last_nm2_sndx \"LastNm2Sndx\", "
      + "middle_nm \"MiddleNm\", "
      + "middle_nm2 \"MiddleNm2\", "
      + "nm_degree \"NmDegree\", "
      + "nm_prefix_cd \"NmPrefix\", "
      + "nm_suffix_cd \"NmSuffixCd\", "
      + "nm_use_cd \"NmUseCd\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "to_time \"ToTime\", "
      + "user_affiliation_txt \"UserAffiliationTxt\" "
      + "from MsgIn_Person_name ";

    public static final String MSG_IN_PERSONNAME_SET_UID =  "where person_uid = ? ";

    public static final String MSG_IN_PERSONETHNICGROUP_SELECT = "SELECT person_uid PersonUid, "+
  "ethnic_group_cd \"EthnicGroupCd\", "+
  "add_reason_cd \"AddReasonCd\", "+
  "add_time \"AddTime\", "+
  "add_user_id \"AddUserId\", "+
  "ethnic_group_desc_txt \"EthnicGroupDescTxt\", "+
  "last_chg_reason_cd \"LastChgReasonCd\", "+
  "last_chg_time \"LastChgTime\", "+
  "last_chg_user_id \"LastChgUserId\", "+
  "record_status_cd \"RecordStatusCd\", "+
  "record_status_time \"RecordStatusTime\", "+
  "user_affiliation_txt \"UserAffiliationTxt\" from MsgIn_Person_ethnic_group ";

   public static final String MSG_IN_PERSONETHNICGROUP_SET_UID =  "where person_uid = ? ";



   public static final String MSG_IN_PERSON_SELECT = "SELECT "
      + "person_uid \"PersonUid\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "age_calc \"AgeCalc\", "
      + "age_calc_time \"AgeCalcTime\", "
      + "age_calc_unit_cd \"AgeCalcUnitCd\", "
      + "age_category_cd \"AgeCategoryCd\", "
      + "age_reported \"AgeReported\", "
      + "age_reported_time \"AgeReportedTime\", "
      + "age_reported_unit_cd \"AgeReportedUnitCd\", "
      + "adults_in_house_nbr \"AdultsInHouseNbr\", "
      + "birth_gender_cd \"BirthGenderCd\", "
      + "birth_order_nbr \"BirthOrderNbr\", "
      + "birth_time \"BirthTime\", "
      + "birth_time_calc \"BirthTimeCalc\", "
      + "cd \"Cd\", "
      + "cd_desc_txt \"CdDescTxt\", "
      + "children_in_house_nbr \"ChildrenInHouseNbr\", "
      + "curr_sex_cd \"CurrSexCd\", "
      + "deceased_ind_cd \"DeceasedIndCd\", "
      + "deceased_time \"DeceasedTime\", "
      + "description \"Description\", "
      + "education_level_cd \"EducationLevelCd\", "
      + "education_level_desc_txt \"EducationLevelDescTxt\", "
      + "ethnic_group_ind \"EthnicGroupInd\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "local_id \"LocalId\", "
      + "marital_status_cd \"MaritalStatusCd\", "
      + "marital_status_desc_txt \"MaritalStatusDescTxt\", "
      + "mothers_maiden_nm \"MothersMaidenNm\", "
      + "multiple_birth_ind \"MultipleBirthInd\", "
      + "occupation_cd \"OccupationCd\", "
      + "prim_lang_cd \"PrimLangCd\", "
      + "prim_lang_desc_txt \"PrimLangDescTxt\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "user_affiliation_txt \"UserAffiliationTxt\", "
      + "version_ctrl_nbr \"VersionCtrlNbr\", "
      + "survived_ind_cd \"SurvivedIndCd\", "
      + "administrative_gender_cd \"AdministrativeGenderCd\", "
      + "preferred_gender_cd \"PreferredGenderCd\"  "
      + "from MsgIn_Person ";

    public static final String MSG_IN_PERSON_SET_UID =  "where person_uid = ? " ;

    public static final String MSG_IN_ORGANIZATIONNAME_SELECT = "SELECT "
      + "organization_uid \"OrganizationUid\", "
      + "organization_name_seq \"OrganizationNameSeq\", "
      + "nm_txt \"NmTxt\", "
      + "nm_use_cd \"NmUseCd\" "
      + "from MsgIn_Organization_name ";

    public static final String MSG_IN_ORGANIZATIONNAME_SET_UID =  "where organization_uid = ? ";

    public static final String MSG_IN_ORGANIZATION_SELECT = "SELECT "
      + "organization_uid \"OrganizationUid\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "cd \"Cd\", "
      + "cd_desc_txt \"CdDescTxt\", "
      + "description \"Description\", "
      + "duration_amt \"DurationAmt\", "
      + "duration_unit_cd \"DurationUnitCd\", "
      + "from_time \"FromTime\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "local_id \"LocalId\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "standard_industry_class_cd \"StandardIndustryClassCd\", "
      + "standard_industry_desc_txt \"StandardIndustryDescTxt\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "to_time \"ToTime\", "
      + "user_affiliation_txt \"UserAffiliationTxt\", "
      + "version_ctrl_nbr \"VersionCtrlNbr\" "
      + "from MsgIn_Organization " ;

    public static final String MSG_IN_ORGANIZATION_SET_UID =  "where organization_uid = ? " ;

    public static final String MSG_IN_OBSVALUETEXT_SELECT = "SELECT "
      + "observation_uid \"ObservationUid\", "
      + "obs_value_txt_seq \"ObsValueTxtSeq\", "
      + "data_subtype_cd \"DataSubtypeCd\", "
      + "encoding_type_cd \"EncodingTypeCd\", "
      + "txt_type_cd \"TxtTypeCd\", "
      + "value_image_txt \"ValueImageTxt\", "
      + "value_txt \"ValueTxt\" "
      + "from MsgIn_Obs_value_txt ";

    public static final String MSG_IN_OBSVALUETEXT_SET_UID =  "where observation_uid = ? " ;

    public static final String MSG_IN_OBSVALUENUMERIC_SELECT = "SELECT "
      + "observation_uid \"ObservationUid\", "
      + "obs_value_numeric_seq \"ObsValueNumericSeq\", "
      + "high_range \"HighRange\", "
      + "low_range \"LowRange\", "
      + "comparator_cd_1 \"ComparatorCd1\", "
      + "numeric_value_1 \"NumericValue1\", "
      + "numeric_value_2 \"NumericValue2\", "
      + "numeric_unit_cd \"NumericUnitCd\", "
      + "separator_cd \"SeparatorCd\" "
      + "from MsgIn_Obs_value_numeric ";

    public static final String MSG_IN_OBSVALUENUMERIC_SET_UID = "where observation_uid = ? " ;

    public static final String MSG_IN_OBSVALUECODED_SELECT = "SELECT "
      + "observation_uid \"ObservationUid\", "
      + "code \"Code\", "
      + "code_system_cd \"CodeSystemCd\", "
      + "code_system_desc_txt \"CodeSystemDescTxt\", "
      + "code_version \"CodeVersion\", "
      + "display_name \"DisplayName\", "
      + "original_txt \"OriginalTxt\", "
      + "alt_cd \"AltCd\", "
      + "ALT_CD_DESC_TXT \"AltCdDescTxt\", "
      + "ALT_CD_SYSTEM_CD \"AltCdSystemCd\", "
      + "ALT_CD_SYSTEM_DESC_TXT \"AltCdSystemDescTxt\", "
      + "CODE_DERIVED_IND \"CodeDerivedInd\" "
      + "from MsgIn_Obs_value_coded ";

    public static final String MSG_IN_OBSVALUECODED_SET_UID =  "where observation_uid = ? ";

    public static final String MSG_IN_OBSERVATIONREASON_SELECT = "SELECT "
      + "observation_uid \"ObservationUid\", "
      + "reason_cd \"ReasonCd\", "
      + "reason_desc_txt \"ReasonDescTxt\" "
      + "from MsgIn_Observation_reason ";

    public static final String MSG_IN_OBSERVATIONINTERP_SET_UID = "where observation_uid = ? ";

    public static final String MSG_IN_OBSERVATIONINTERP_SELECT = "SELECT "
      + "observation_uid \"ObservationUid\", "
      + "interpretation_cd \"InterpretationCd\", "
      + "interpretation_desc_txt \"InterpretationDescTxt\" "
      + "from MsgIn_Observation_interp ";

    public static final String MSG_IN_OBSERVATIONREASON_SET_UID =  "where observation_uid = ? ";

    public static final String MSG_IN_JURISDICTION_SELECT_ORACLE ="Select jurisdiction_cd \"key\" "+
     //     ", 1 \"value\"  FROM "
     " FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE+".jurisdiction_participation "+
     " WHERE fips_cd = ? and type_cd = ? ";

    public static final String MSG_IN_JURISDICTION_SELECT_SQL ="Select jurisdiction_cd \"key\" "+
   //     ", 1 \"value\"  FROM "
    " FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE+
    "..jurisdiction_participation WHERE fips_cd = ? and type_cd = ? ";

    public static final String MSG_IN_PROGRAM_AREA_SELECT = "SELECT c.prog_area_cd from condition_code c where "+
    "(condition_cd IN (SELECT condition_cd from loinc_condition where loinc_cd = ?))";

    public static final String MSG_IN_OBSERVATION_SELECT = "SELECT "
      + "observation_uid \"ObservationUid\", "
      + "activity_duration_amt \"ActivityDurationAmt\", "
      + "activity_duration_unit_cd \"ActivityDurationUnitCd\", "
      + "activity_from_time \"ActivityFromTime\", "
      + "activity_to_time \"ActivityToTime\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "cd \"Cd\", "
      + "cd_desc_txt \"CdDescTxt\", "
      + "cd_system_cd \"CdSystemCd\", "
      + "cd_system_desc_txt \"CdSystemDescTxt\", "
      + "confidentiality_cd \"ConfidentialityCd\", "
      + "confidentiality_desc_txt \"ConfidentialityDescTxt\", "
      + "ctrl_cd_display_form \"CtrlCdDisplayForm\", "
      + "ctrl_cd_user_defined_1 \"CtrlCdUserDefined1\", "
      + "ctrl_cd_user_defined_2 \"CtrlCdUserDefined2\", "
      + "ctrl_cd_user_defined_3 \"CtrlCdUserDefined3\", "
      + "ctrl_cd_user_defined_4 \"CtrlCdUserDefined4\", "
      + "derivation_exp \"DerivationExp\", "
      + "effective_duration_amt \"EffectiveDurationAmt\", "
      + "effective_duration_unit_cd \"EffectiveDurationUnitCd\", "
      + "effective_from_time \"EffectiveFromTime\", "
      + "effective_to_time \"EffectiveToTime\", "
      + "electronic_ind \"ElectronicInd\", "
      + "group_level_cd \"GroupLevelCd\", "
      + "jurisdiction_cd \"JurisdictionCd\", "
      + "lab_condition_cd \"LabConditionCd\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "local_id \"LocalId\", "
      + "method_cd \"MethodCd\", "
      + "method_desc_txt \"MethodDescTxt\", "
      + "obs_domain_cd \"ObsDomainCd\", "
      + "obs_domain_cd_st_1 \"ObsDomainCdSt1\", "
      + "pnu_cd \"PnuCd\", "
      + "priority_cd \"PriorityCd\", "
      + "priority_desc_txt \"PriorityDescTxt\", "
      + "prog_area_cd \"ProgAreaCd\", "
      + "program_jurisdiction_oid \"ProgramJurisdictionOid\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "repeat_nbr \"RepeatNbr\", "
      + "shared_ind \"SharedInd\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "person_uid \"PersonUid\", "
      + "target_site_cd \"TargetSiteCd\", "
      + "target_site_desc_txt \"TargetSiteDescTxt\", "
      + "txt \"Txt\", "
      + "user_affiliation_txt \"UserAffiliationTxt\", "
      + "value_cd \"ValueCd\", "
      + "version_ctrl_nbr \"VersionCtrlNbr\", "
      + "ynu_cd \"YnuCd\", "
      + "alt_cd \"AltCd\", "
      + "ALT_CD_DESC_TXT \"AltCdDescTxt\", "
      + "ALT_CD_SYSTEM_CD \"AltCdSystemCd\", "
      + "ALT_CD_SYSTEM_DESC_TXT \"AltCdSystemDescTxt\", "
      + "CD_DERIVED_IND \"CdDerivedInd\" "
      + "from MsgIn_Observation " ;

    public static final String MSG_IN_OBSERVATION_SET_UID = "where observation_uid = ? " ;

    public static final String MSG_IN_MESSAGE_UPDATE = "UPDATE MsgIn_Message SET "
      + "entity_uid = ?, "
      + "communication_function_uid = ?, "
      + "transmission_uid = ?, "
      + "message_uid = ?, "
      + "accept_ack_cd = ?, "
      + "accept_ack_desc_txt = ?, "
      + "application_ack_cd = ?, "
      + "application_ack_desc_txt = ?, "
      + "interaction_id = ?, "
      + "processing_cd = ?, "
      + "processing_desc_txt = ?, "
      + "processing_mode_cd = ?, "
      + "processing_mode_desc_txt = ?, "
      + "sequence_nbr = ?, "
      + "status_cd = ?, "
      + "status_time = ?, "
      + "version_id = ? ";

    public static final String MSG_IN_MESSAGE_SELECT = "SELECT "
      + "entity_uid \"EntityUid\", "
      + "communication_function_uid \"CommunicationFunctionUid\", "
      + "transmission_uid \"TransmissionUid\", "
      + "message_uid \"MessageUid\", "
      + "accept_ack_cd \"AcceptAckCd\", "
      + "accept_ack_desc_txt \"AcceptAckDescTxt\", "
      + "application_ack_cd \"ApplicationAckCd\", "
      + "application_ack_desc_txt \"ApplicationAckDescTxt\", "
      + "interaction_id \"InteractionId\", "
      + "processing_cd \"ProcessingCd\", "
      + "processing_desc_txt \"ProcessingDescTxt\", "
      + "processing_mode_cd \"ProcessingModeCd\", "
      + "processing_mode_desc_txt \"ProcessingModeDescTxt\", "
      + "sequence_nbr \"SequenceNbr\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "version_id \"VersionId\" "
      + "from MsgIn_Message ";

    public static final String MSG_IN_MESSAGE_SET_UID =  "where entity_uid = ? ";

    public static final String MSG_IN_MESSAGE_SET_PK = "where entity_uid = ? and communication_function_uid = ? and transmission_uid = ? and message_uid = ? ";

    public static final String MSG_IN_MESSAGE_SET_STATUS_CD = " where status_cd = 'N'";

    public static final String MSG_IN_MATERIAL_SELECT = "SELECT "
      + "material_uid \"MaterialUid\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "cd \"Cd\", "
      + "cd_desc_txt \"CdDescTxt\", "
      + "description \"Description\", "
      + "effective_duration_amt \"EffectiveDurationAmt\", "
      + "effective_duration_unit_cd \"EffectiveDurationUnitCd\", "
      + "effective_from_time \"EffectiveFromTime\", "
      + "effective_to_time \"EffectiveToTime\", "
      + "form_cd \"FormCd\", "
      + "form_desc_txt \"FormDescTxt\", "
      + "handling_cd \"HandlingCd\", "
      + "handling_desc_txt \"HandlingDescTxt\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "local_id \"LocalId\", "
      + "nm \"Nm\", "
      + "qty \"Qty\", "
      + "qty_unit_cd \"QtyUnitCd\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "risk_cd \"RiskCd\", "
      + "risk_desc_txt \"RiskDescTxt\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "user_affiliation_txt \"UserAffiliationTxt\", "
      + "version_ctrl_nbr \"VersionCtrlNbr\" "
      + "from MsgIn_Material " ;

    public static final String MSG_IN_MATERIAL_SET_UID =  "where material_uid = ? " ;

    public static final String MSG_IN_ENTLOCPART_SELECT = "SELECT "
      + "entity_uid \"EntityUid\", "
      + "locator_uid \"LocatorUid\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "cd \"Cd\", "
      + "cd_desc_txt \"CdDescTxt\", "
      + "class_cd \"ClassCd\", "
      + "duration_amt \"DurationAmt\", "
      + "duration_unit_cd \"DurationUnitCd\", "
      + "from_time \"FromTime\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "locator_desc_txt \"LocatorDescTxt\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "to_time \"ToTime\", "
      + "use_cd \"UseCd\", "
      + "user_affiliation_txt \"UserAffiliationTxt\", "
      + "valid_time_txt \"ValidTimeTxt\" "
      + "from MsgIn_Entity_locator_participa ";

    public static final String MSG_IN_ENTLOCPART_SET_UID =  "where entity_uid = ? ";

    public static final String MSG_IN_ENTITYID_SELECT = "SELECT "
      + "entity_uid \"EntityUid\", "
      + "entity_id_seq \"EntityIdSeq\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "assigning_authority_cd \"AssigningAuthorityCd\", "
      + "assigning_authority_desc_txt \"AssigningAuthorityDescTxt\", "
      + "duration_amt \"DurationAmt\", "
      + "duration_unit_cd \"DurationUnitCd\", "
      + "effective_from_time \"EffectiveFromTime\", "
      + "effective_to_time \"EffectiveToTime\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "root_extension_txt \"RootExtensionTxt\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "type_cd \"TypeCd\", "
      + "type_desc_txt \"TypeDescTxt\", "
      + "user_affiliation_txt \"UserAffiliationTxt\", "
      + "valid_from_time \"ValidFromTime\", "
      + "valid_to_time \"ValidToTime\" "
      + "from MsgIn_Entity_id ";

    public static final String MSG_IN_ENTITYID_SET_UID =  "where entity_uid = ? ";

    public static final String MSG_IN_ACTRELATIONSHIP_SELECT = "SELECT "
      + "target_act_uid \"TargetActUid\", "
      + "source_act_uid \"SourceActUid\", "
      + "type_cd \"TypeCd\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "duration_amt \"DurationAmt\", "
      + "duration_unit_cd \"DurationUnitCd\", "
      + "from_time \"FromTime\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "sequence_nbr \"SequenceNbr\", "
      + "source_class_cd \"SourceClassCd\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "target_class_cd \"TargetClassCd\", "
      + "to_time \"ToTime\", "
      + "type_desc_txt \"TypeDescTxt\", "
      + "user_affiliation_txt \"UserAffiliationTxt\" "
      + "from MsgIn_Act_relationship ";

    public static final String MSG_IN_ACTRELATIONSHIP_SET_UID =  "where target_act_uid = ? " ;
							         // and source_act_uid = ? " type_cd = ? " ;

    public static final String MSG_IN_ACTID_SELECT = "SELECT "
      + "act_uid \"ActUid\", "
      + "act_id_seq \"ActIdSeq\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "assigning_authority_cd \"AssigningAuthorityCd\", "
      + "assigning_authority_desc_txt \"AssigningAuthorityDescTxt\", "
      + "duration_amt \"DurationAmt\", "
      + "duration_unit_cd \"DurationUnitCd\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "root_extension_txt \"RootExtensionTxt\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "type_cd \"TypeCd\", "
      + "type_desc_txt \"TypeDescTxt\", "
      + "user_affiliation_txt \"UserAffiliationTxt\", "
      + "valid_from_time \"ValidFromTime\", "
      + "valid_to_time \"ValidToTime\" "
      + "from MsgIn_Act_id ";

    public static final String MSG_IN_ACTID_SET_UID =  "where act_uid = ? ";

    public static final String MSG_IN_ACTID_SELECT_FILLER_NBR
      = "select root_extension_txt Filler from MsgIn_Act_Id " +
        "where type_cd='FN' and act_uid=? ";

    public static final String MSG_IN_ACTLOG_SELECT_MAX_UID = "Select max(msg_observation_uid), process_time from nbs_odsc2..ELR_ACTIVITY_LOG where process_cd='success'";

    public static final String MSG_IN_ACTLOG_SELECT_UID = "where id = ?";

    public static final String MSG_IN_ACTLOG_SELECT_PK = "where elr_activity_log_seq = ? and id = ? ";

    public static final String MSG_IN_ACTLOG_SELECT
    = "SELECT "
      + "msg_observation_uid \"MsgObservationUid\", "
      + "elr_activity_log_seq \"ElrActivityLogSeq\", "
      + "filler_nbr \"FillerNbr\", "
      + "id \"Id\", "
      + "ods_observation_uid \"OdsObservationUid\", "
      + "status_cd \"StatusCd\", "
      + "process_time \"ProcessTime\", "
      + "process_cd \"ProcessCd\", "
      + "subject_nm \"SubjectNm\", "
      + "detail_txt \"DetailTxt\", "
      + "report_fac_nm \"ReportingFacNm\" "
      + "from Elr_activity_log ";

    public static final String MSG_IN_ACTLOG_FILTER
    = MSG_IN_ACTLOG_SELECT
    + "WHERE process_time between ? and ? and status_cd IN ";
    //+ "WHERE process_time > convert(datetime, ?) and process_time < convert(datetime, ?) and status_cd IN ";


    public static final String MSG_IN_MESSAGE_COUNT
    = "SELECT COUNT(DISTINCT msg_observation_uid) FROM ELR_activity_log "
    + " WHERE process_time > ? and process_time < ? ";
    //+ " WHERE process_time > convert(datetime, ?) and process_time < convert(datetime, ?) ";



      public static final String MSG_IN_ACTLOG_UPDATE
      = "UPDATE Elr_activity_log SET "
      + "msg_observation_uid = ?, "
      + "elr_activity_log_seq = ?, "
      + "filler_nbr = ?, "
      + "id = ?, "
      + "ods_observation_uid = ?, "
      + "status_cd = ?, "
      + "process_time = ?, "
      + "process_cd = ?, "
      + "subject_nm = ?, "
      + "detail_txt = ?, "
      + "report_fac_nm = ?";

      public static final String MSG_IN_ACTLOG_INSERT
      = "INSERT INTO ELR_activity_log ( "
      + "msg_observation_uid, "
      + "elr_activity_log_seq, "
      + "filler_nbr, "
      + "id, "
      + "ods_observation_uid, "
      + "status_cd, "
      + "process_time, "
      + "process_cd, "
      + "subject_nm, "
      + "report_fac_nm "
      + " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

      public static final String MSG_IN_PAT_SET_PK = "where subject_entity_uid = ? and act_uid = ? and type_cd = ?";
      public static final String MSG_IN_PAT_SET_UID = "where subject_entity_uid = ?";
      public static final String MSG_IN_PAT_SET_ACT_UID = "where act_uid = ?";
      public static final String MSG_IN_PAT_SET_TYPE = "where subject_entity_uid = ? and type_cd = ?";

      public static final String MSG_IN_PAT_SELECT_ORDERED_TEST_UID = "select act_uid from MsgIn_Participation where act_class_cd = 'OBS' and subject_entity_class_cd = 'ORG' and type_cd = 'AUT' and subject_entity_uid =? ";
      public static final String MSG_IN_PAT_SELECT_SUBJECT_UID = "select subject_entity_uid from MsgIn_Participation where act_class_cd = 'OBS' and subject_entity_class_cd = 'PSN' and type_cd = 'PATSBJ' and act_uid = ? ";
      public static final String ODS_PAT_SELECT_SUBJECT_UID = "select subject_entity_uid from Participation where act_class_cd = 'OBS' and subject_class_cd = 'PSN' and type_cd = 'PATSBJ' and act_uid = ? ";

      public static final String MSG_IN_COPY_TO_PROVIDER_SELECT_SUBJECT_UID = "select subject_entity_uid from MsgIn_Role where subject_class_cd = 'PROV' and scoping_class_cd = 'PAT' and cd = 'CT' and scoping_entity_uid = ? ";
      public static final String MSG_IN_EMERGENCY_CONTACT_SELECT_SUBJECT_UID = "select subject_entity_uid from MsgIn_Role where subject_class_cd = 'CON' and scoping_class_cd = 'PAT' and scoping_entity_uid = ? ";
      public static final String MSG_IN_COLLECTOR_SELECT_SUBJECT_UID = "select subject_entity_uid from MsgIn_Role where subject_class_cd = 'PROV' and  scoping_class_cd = 'PAT' and cd = 'SPP' and scoping_entity_uid = ?";

      public static final String MSG_IN_PAT_SELECT_RECEIVING_FACILITY = "select subject_entity_uid from MsgIn_Participation where act_class_cd = 'OBS' and subject_entity_class_cd = 'ORG' and type_cd = 'TRC' and act_uid = ?";
      public static final String MSG_IN_PAT_SELECT_TRANSCRIPTIONIST = "select subject_entity_uid from MsgIn_Participation where act_class_cd = 'OBS' and subject_entity_class_cd = 'PSN' and type_cd = 'ENT' and act_uid = ?";
      public static final String MSG_IN_PAT_SELECT_ASSISTANT_RESULT_INTERPRETER = "select subject_entity_uid from MsgIn_Participation where act_class_cd = 'OBS' and subject_entity_class_cd = 'PSN' and type_cd = 'ASS' and act_uid = ?";
      public static final String MSG_IN_PAT_SELECT_TECHNICIAN = "select subject_entity_uid from MsgIn_Participation where act_class_cd = 'OBS' and subject_entity_class_cd = 'PSN' and type_cd = 'PRF' and act_uid = ?";
      public static final String MSG_IN_PAT_SELECT_PRINCIPLE_RESULT_INTERPRETER = "select subject_entity_uid from MsgIn_Participation where act_class_cd = 'OBS' and subject_entity_class_cd = 'PSN' and type_cd = 'VRF' and act_uid = ?";
      public static final String MSG_IN_PAT_SELECT_PROVIDER_UID = "select subject_entity_uid from MsgIn_Participation where act_class_cd = 'OBS' and subject_entity_class_cd = 'PSN' and type_cd = 'ORD' and act_uid = ? ";
      public static final String MSG_IN_PAT_SELECT_FACILITY_UID = "select subject_entity_uid from MsgIn_Participation where act_class_cd = 'OBS' and subject_entity_class_cd = 'ORG' and type_cd = 'ORD' and act_uid = ? ";
      public static final String MSG_IN_PAT_SELECT_SUBMIT_LAB_UID = "select subject_entity_uid from MsgIn_Participation where act_class_cd = 'OBS' and subject_entity_class_cd = 'ORG' and type_cd = 'AUT' and act_uid = ? ";
      public static final String MSG_IN_PAT_SELECT_SPECIMEN_UID = "select subject_entity_uid from MsgIn_Participation where act_class_cd = 'OBS' and subject_entity_class_cd = 'MAT' and type_cd = 'SPC' and act_uid =? ";

      public static final String MSG_IN_PAT_SELECT
      = "SELECT "
      + "subject_entity_uid \"SubjectEntityUid\", "
      + "act_uid \"ActUid\", "
      + "act_class_cd \"ActClassCd\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "awareness_cd \"AwarenessCd\", "
      + "awareness_desc_txt \"AwarenessDescTxt\", "
      + "cd \"Cd\", "
      + "duration_amt \"DurationAmt\", "
      + "duration_unit_cd \"DurationUnitCd\", "
      + "from_time \"FromTime\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "role_seq \"RoleSeq\", "
      + "subject_entity_class_cd \"SubjectEntityClassCd\", "
      + "subject_class_cd \"SubjectClassCd\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "to_time \"ToTime\", "
      + "type_cd \"TypeCd\", "
      + "type_desc_txt \"TypeDescTxt\", "
      + "user_affiliation_txt \"UserAffiliationTxt\" "
      + "from MsgIn_Participation ";

      //Retrieve Reporting Lab Organization to Ordered Test Observation, type_cd="AUT" and subject_class_cd="ORG"
      //Retrieve Subject Person to Ordered Test Observation, type_cd="PATSUBJ" and subject_class_cd="PSN"
      //Retrieve Ordering Facility Organization to Ordered Test Observation, type_cd="ORD" and subject_class_cd="ORG"
      //Retrieve Specimen Material to Ordered Test Observation, type_cd="SPC" and subject_class_cd="MAT"
      public static final String MSG_IN_PAT_SELECT_BY_UID_FOR_ORDERED_TEST_PART = MSG_IN_PAT_SELECT + "where act_class_cd = 'OBS' and "
                    + " ( (subject_entity_class_cd = 'PSN' and type_cd = 'PATSBJ') or "
		    +   " (subject_entity_class_cd = 'ORG' and type_cd = 'AUT') or "
		    +   " (subject_entity_class_cd = 'ORG' and type_cd = 'ORD') or "
		    +   " (subject_entity_class_cd = 'PSN' and type_cd = 'ORD') or "
		    +   " (subject_entity_class_cd = 'MAT' and type_cd = 'SPC') ) and act_uid = ? ";

      //Retrieve Performing Lab Organization to Ordered Test Observation, type_cd="PRF"
      public static final String MSG_IN_PAT_SELECT_BY_UID_FOR_RESULT_TEST_PART = MSG_IN_PAT_SELECT + "where act_class_cd = 'OBS' and subject_entity_class_cd = 'ORG' and type_cd = 'PRF' and act_uid = ? ";

      public static final String SRT_SELECT_STATE_CD_ORACLE = "select state_cd \"key\" "+
      //     ", 1 \"value\"  FROM "
      " FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE+".State_code where state_nm = ?";
      public static final String SRT_SELECT_STATE_CD_SQL = "select state_cd \"key\" "+
      //     ", 1 \"value\"  FROM "
      " FROM " +  NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..State_code where state_nm = ?";

      public static final String MSG_PROG_AREA_LOINC_SNOMED_SP = "{call msgProgAreaLoincSnomed_sp(?, ?, ?, ?, ?)}";
      public static final String MSG_PROG_AREA_SP = "{call msgProgArea_sp(?,?,?,?,?)}";
      public static final String MSG_PROG_AREA_LOINC_SNOMED = "{call msgProgAreaLoincSnomed_sp(?,?,?,?,?)}";

        public static final String SELECT_LOCAL_RESULT_DEFAULT_PROGRAM_AREA_CD_SQL =
       "select default_prog_area_cd \"key\" "+
       //     ", 1 \"value\"  FROM "
      " FROM " +        NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..lab_result "+
       " where laboratory_id = ? and lab_result_cd = ?";


      public static final String SELECT_LOCAL_RESULT_DEFAULT_CONDITION_PROGRAM_AREA_CD_SQL =
		"SELECT prog_area_cd \"key\" FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code" +
		" WHERE condition_cd = (SELECT default_condition_cd FROM " +
      	NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..lab_result "+
       " where laboratory_id = ? and lab_result_cd = ?)";

      public static final String SELECT_LOCAL_TEST_DEFAULT_PROGRAM_AREA_CD_SQL =
       "select default_prog_area_cd \"key\" "+
       //     ", 1 \"value\"  FROM "
      " FROM " +        NEDSSConstants.SYSTEM_REFERENCE_TABLE+
       "..lab_test where laboratory_id = ? and lab_test_cd = ?";

      public static final String SELECT_LOCAL_TEST_DEFAULT_CONDITION_PROGRAM_AREA_CD_SQL =
		"SELECT prog_area_cd \"key\" FROM   " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code  " +
		" WHERE condition_cd = ( SELECT default_condition_cd FROM " +
      	NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..lab_test "+
       " where laboratory_id = ? and lab_test_cd = ? )";


      public static final String MSG_IN_ROLE_SELECT  =
	 "SELECT "
      + "subject_entity_uid \"SubjectEntityUid\", "
      + "cd \"Cd\", "
      + "role_seq \"RoleSeq\", "
      + "add_reason_cd \"AddReasonCd\", "
      + "add_time \"AddTime\", "
      + "add_user_id \"AddUserId\", "
      + "subject_class_cd \"SubjectClassCd\", "
      + "scoping_class_cd \"ScopingClassCd\", "
      + "cd_desc_txt \"CdDescTxt\", "
      + "effective_duration_amt \"EffectiveDurationAmt\", "
      + "effective_duration_unit_cd \"EffectiveDurationUnitCd\", "
      + "effective_from_time \"EffectiveFromTime\", "
      + "effective_to_time \"EffectiveToTime\", "
      + "last_chg_reason_cd \"LastChgReasonCd\", "
      + "last_chg_time \"LastChgTime\", "
      + "last_chg_user_id \"LastChgUserId\", "
      + "record_status_cd \"RecordStatusCd\", "
      + "record_status_time \"RecordStatusTime\", "
      + "scoping_entity_uid \"ScopingEntityUid\", "
      + "scoping_role_cd \"ScopingRoleCd\", "
      + "scoping_role_seq \"ScopingRoleSeq\", "
      + "status_cd \"StatusCd\", "
      + "status_time \"StatusTime\", "
      + "user_affiliation_txt \"UserAffiliationTxt\" "
      + "from MsgIn_Role ";

	  public static final String MSG_IN_ROLE_SET_UID =  "where subject_entity_uid = ? ";

		public static final String JURISDICTION_SELECT_SQL = "Select b.jurisdiction_cd \"key\", 1 \"value\" " +
			"from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..jurisdiction_code a, " +
			NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..jurisdiction_participation b " +
			"where a.code = b.jurisdiction_cd " +
			"and (b.fips_cd = ?) " +
			"and (b.type_cd = ?) ";

                    public static final String JURISDICTION_CITY_SELECT_SQL =
                        "select a.jurisdiction_cd \"key\", 1 \"value\" " +
                        "from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                        "..jurisdiction_participation a, " +
                        NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..city_code_value b " +
                        "where a.fips_cd= b.code " +
                        "and a.type_cd = ? " +
                        "and substring(b.code_desc_txt, 0, { fn LENGTH(b.code_desc_txt) }- 3) =  ? " +
                        "and b.parent_is_cd = ? ";

                    public static final String JURISDICTION_CITY_SELECT_ORACLE_DM =
                        "select a.jurisdiction_cd \"key\", 1 \"value\" " +
                        "from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                        ".jurisdiction_participation a, " +
                        NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".city_code_value b " +
                        "where a.fips_cd= b.code " +
                        "and a.type_cd = ? " +
                        "and b.code_desc_txt =  ? " ;

                    public static final String JURISDICTION_CITY_SELECT_SQL_DM =
                        "select a.jurisdiction_cd \"key\", 1 \"value\" " +
                        "from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                        "..jurisdiction_participation a, " +
                        NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..city_code_value b " +
                        "where a.fips_cd= b.code " +
                        "and a.type_cd = ? " +
                        "and b.code_desc_txt =  ? " ;

}
