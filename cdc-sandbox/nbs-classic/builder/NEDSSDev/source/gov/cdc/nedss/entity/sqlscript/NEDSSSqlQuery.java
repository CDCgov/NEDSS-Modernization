/**
* Name:		NEDSSSql.java
* Description:	This class contains the SQL statements used in NEDSS.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
**/


package gov.cdc.nedss.entity.sqlscript;

import gov.cdc.nedss.util.*;



public class NEDSSSqlQuery
{
     private static final String SPACE = " ";
     private static final String COMMA = ",";
     //SQL statements for inserting into nbs_ods SQL server.
     public static final String INSERT_ENTITY = "INSERT INTO " + DataTables.ENTITY_TABLE + "(entity_uid, class_cd) VALUES (?, ?)";
     public static final String INSERT_ORGANIZATION = "INSERT INTO " + DataTables.ORGANIZATION_TABLE + "(add_reason_cd, add_time, add_user_id, cd, cd_desc_txt, description, duration_amt, duration_unit_cd, from_time, last_chg_reason_cd, last_chg_time, last_chg_user_id, record_status_cd, record_status_time, standard_industry_class_cd, standard_industry_desc_txt, status_cd, status_time, to_time, user_affiliation_txt, display_nm, street_addr1, street_addr2, city_cd, city_desc_txt, state_cd, cnty_cd, cntry_cd, zip_cd, phone_nbr, phone_cntry_cd, organization_uid, local_id, version_ctrl_nbr) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
     public static final String INSERT_NONPERSON = "INSERT INTO " + DataTables.NONPERSON_TABLE + "(add_reason_cd, add_time, add_user_id, birth_gender_cd, birth_order_nbr, birth_time, breed_cd, breed_desc_txt, cd, cd_desc_txt, deceased_ind_cd, deceased_time, description, last_chg_reason_cd, last_chg_time, last_chg_user_id, local_id, multiple_birth_ind, nm, org_acces_permis, prog_area_access_permis, record_status_cd, record_status_time, status_cd, status_time, taxonomic_classification_cd, taxonomic_classification_desc, user_affiliation_txt, non_person_uid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

     //Place History
     public static final String SELECT_PLACE_HIST =
     "select place_uid \"placeUid\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", cd \"cd\", cd_desc_txt \"cdDescTxt\", description \"description\", duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\", from_time \"fromTime\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", local_id \"localId\", nm \"nm\", org_access_permis \"orgAccessPermis\", prog_area_access_permis \"progAreaAccessPermis\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", status_cd \"statusCd\", status_time \"statusTime\", to_time \"toTime\", user_affiliation_txt \"userAffiliationTxt\", "+
     "street_addr1 \"streetAddr1\", street_addr2 \"streetAddr2\", city_cd \"cityCd\", city_desc_txt \"cityDescTxt\", state_cd \"stateCd\", zip_cd \"zipCd\", cnty_cd \"cntyCd\", cntry_cd \"cntryCd\", phone_nbr \"phoneNbr\", phone_cntry_cd \"phoneCntryCd\" "+
     "from place_hist where place_uid = ? and version_ctrl_nbr = ?";

     public static final String INSERT_PLACE_HIST =
     "insert into place_hist(place_uid, version_ctrl_nbr, add_reason_cd, add_time, add_user_id, cd, cd_desc_txt, description, duration_amt, duration_unit_cd, from_time, last_chg_reason_cd, last_chg_time, last_chg_user_id, "+
     "local_id, nm, org_access_permis, prog_area_access_permis, record_status_cd, record_status_time, status_cd, status_time, to_time, user_affiliation_txt, street_addr1, street_addr2, city_cd, city_desc_txt, state_cd, zip_cd, cnty_cd, "+
     "cntry_cd, phone_nbr, phone_cntry_cd) values(,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

     public static final String SELECT_PLACE_SEQ_NUMBER_HIST =
     "select version_ctrl_nbr from place_hist where place_uid = ?";
     //Entity Group History
     public static final String SELECT_ENTITY_GROUP_HIST =
    "select entity_group_uid \"entityGroupUid\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", cd \"cd\", "+
    "cd_desc_txt \"cdDescTxt\", description \"description\", duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\", from_time \"fromTime\", "+
    "group_cnt \"groupCnt\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", local_id \"localId\", "+
    "nm \"nm\", org_access_permis \"orgAccessPermis\", prog_area_access_permis \"progAreaAccessPermis\", record_status_cd \"recordStatusCd\", "+
    "record_status_time \"recordStatusTime\", status_cd \"statusCd\", status_time \"statusTime\", to_time \"toTime\", user_affiliation_txt \"userAffiliationTxt\" "+
    "from entity_group_hist where entity_group_uid = ? and version_ctrl_nbr = ?";

    public static final String INSERT_ENTITY_GROUP_HIST =
    "insert into entity_group_hist(entity_group_uid, version_ctrl_nbr, add_reason_cd, add_time, add_user_id, cd, cd_desc_txt, description, duration_amt, duration_unit_cd, "+
    "from_time, group_cnt, last_chg_reason_cd, last_chg_time, last_chg_user_id, local_id, nm, org_access_permis, prog_area_access_permis, record_status_cd, record_status_time, status_cd, "+
    "status_time, to_time, user_affiliation_txt) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String SELECT_ENTITY_GROUP_SEQ_NUMBER_HIST =
    "select version_ctrl_nbr from entity_group_hist where entity_group_uid = ?";

     //Organization history
     public static final String SELECT_ORGANIZATION_NAME_HIST = "SELECT organization_uid \"OrganizationUid\", organization_name_seq \"OrganizationNameSeq\", version_ctrl_nbr \"versionCtrlNbr\", "+
        "nm_txt \"NmTxt\", nm_use_cd \"NmUseCd\" FROM organization_name_hist WHERE organization_uid = ? and organization_name_seq = ? and versin_ctrl_nbr = ?";

     public static final String INSERT_ORGANIZATION_NAME_HIST = "INSERT into organization_name_hist(organization_uid, organization_name_seq, version_ctrl_nbr, nm_txt, nm_use_cd) "+
        "VALUES(?, ?, ?, ?, ?)";

     public static final String INSERT_ORGANIZATION_HIST = "INSERT into organization_hist(organization_uid, version_ctrl_nbr, "+
        "add_reason_cd, add_time, add_user_id, cd, cd_desc_txt, description, duration_amt, duration_unit_cd, from_time, last_chg_reason_cd, "+
        "last_chg_time, last_chg_user_id, local_id, org_access_permis, prog_area_access_permis, record_status_cd, record_status_time, standard_industry_class_cd, "+
        "standard_industry_desc_txt, status_cd, status_time, to_time, user_affiliation_txt, display_nm, street_addr1, street_addr2, city_cd, city_desc_txt, "+
        "state_cd, cnty_cd, cntry_cd, zip_cd, phone_nbr, phone_cntry_cd) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

     public static final String SELECT_ORGANIZATION_SEQ_NUMBER_HIST =
     "SELECT version_ctrl_nbr from organization_hist where organization_uid = ?";

     public static final String SELECT_ORGANIZATION_HIST =
     "SELECT organization_uid \"OrganizationUid\", "+
     "version_ctrl_nbr \"VersionCtrlNbr\", "+
     "add_reason_cd \"AddReasonCd\", "+
     "add_time \"AddTime\", "+
     "add_user_id \"AddUserId\", "+
     "cd \"Cd\", "+
     "cd_desc_txt \"CdDescTxt\", "+
     "description \"Description\", "+
     "duration_amt \"DurationAmt\", "+
     "duration_unit_cd \"DurationUnitCd\", "+
     "from_time \"FromTime\", "+
     "last_chg_reason_cd \"LastChgReasonCd\", "+
     "last_chg_time \"LastChgTime\", "+
     "last_chg_user_id \"LastChgUserId, "+
     "local_id \"LocalId\", "+
     "org_access_permis \"OrgAccessPermis\", "+
     "prog_area_access_permis \"ProgAreaAccessPermis\", "+
     "record_status_cd \"RecordStatusCd\", "+
     "record_status_time \"RecordStatusTime\", "+
     "standard_industry_class_cd \"StandardIndustryClassCd\", "+
     "standard_industry_desc_txt \"StandardIndustryDescTxt\", "+
     "status_cd \"StatusCd\", "+
     "status_time \"StatusTime\", "+
     "to_time \"ToTime\", "+
     "user_affiliation_txt \"UserAffiliationTxt\", "+
     "display_nm \"DisplayNm\", "+
     "street_addr1 \"StreetAddr1\", "+
     "street_Addr2 \"StreetAddr2\", "+
     "city_cd \"CityCd\", "+
     "city_desc_txt \"CityDescTxt\", "+
     "state_cd \"StateCd\", "+
     "cnty_cd \"CntyCd\", "+
     "cntry_cd \"CntryCd\", "+
     "zip_cd \"ZipCd\", "+
     "phone_nbr \"PhoneNbr\", "+
     "phone_cntry_cd \"PhoneCntryCd\" "+
     "FROM organization_hist "+
     "WHERE organization_uid = ? and version_ctrl_nbr = ?";

     // Material and associated tables
     public static final String SELECT_MANUFACTURED_MATERIAL_HIST =
     "Select material_uid \"materialUid\", "+
     "manufactured_material_seq \"manufacturedMaterialSeq\", "+
     "version_ctrl_nbr \"versionCntrlNbr\", "+
     "add_reason_cd \"addReasonCd\", "+
     "add_time \"addTime\", "+
     "add_user_id \"addUserId\", "+
     "expiration_time \"expirationTime\", "+
     "last_chg_reason_cd \"lastChgReasonCd\", "+
     "last_chg_time \"lastChgTime\", "+
     "last_chg_user_id \"lastChgUserId\", "+
     "lot_nm \"lotNm\", "+
     "record_status_cd \"recordStatusCd\", "+
     "record_status_time \"recordStatusTime\", "+
     "user_affiliation_txt \"userAffiliationTxt\", "+
     "stability_from_time \"stabilityFromTime\", "+
     "stability_to_time \"stabilityToTime\", "+
     "stability_duration_cd \"stabilityDurationCd\", "+
     "stability_duration_unit_cd \"stabilityDurationUnitCd\", "+
     "from manufactured_material_hist where material_uid = ? and "+
     "manufactured_material_seq =  ? and version_ctrl_nbr = ?";

     public static final String INSERT_MANUFACTURED_MATERIAL_HIST =
     "Insert into manufactured_material_hist(material_uid, manufactured_material_seq, "+
     "version_ctrl_nbr, add_reason_cd, add_time, add_user_id, expiration_time, last_chg_reason_cd, last_chg_time, "+
     "last_chg_user_id, lot_nm, record_status_cd, record_status_time, user_affiliation_txt, stability_from_time, stability_to_time, "+
     "stability_duration_cd, stability_duration_unit_cd) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

     public static final String INSERT_MATERIAL =
        "INSERT INTO"+ SPACE +
            DataTables.MATERIAL_TABLE+ SPACE+
          "("+
            "material_uid"+ COMMA+
            "add_reason_cd"+ COMMA+
            "add_time"+ COMMA+
            "add_user_id"+ COMMA+
            "cd"+ COMMA+
            "cd_desc_txt"+ COMMA+
            "description"+ COMMA+
            "effective_duration_amt"+ COMMA+
            "effective_duration_unit_cd"+ COMMA+
            "effective_from_time"+ COMMA+
            "effective_to_time"+ COMMA+
            "form_cd"+ COMMA+
            "form_desc_txt"+ COMMA+
            "handling_cd"+ COMMA+
            "handling_desc_txt"+ COMMA+
            "last_chg_reason_cd"+ COMMA+
            "last_chg_time"+ COMMA+
            "last_chg_user_id"+ COMMA+
            "local_id"+ COMMA+
            "nm"+ COMMA+
            "org_access_permis"+ COMMA+
            "prog_area_access_permis"+ COMMA+
            "qty"+ COMMA+
            "qty_unit_cd"+ COMMA+
            "record_status_cd"+ COMMA+
            "record_status_time"+ COMMA+
            "risk_cd"+ COMMA+
            "risk_desc_txt"+ COMMA+
            "status_cd"+ COMMA+
            "status_time"+ COMMA+
            "user_affiliation_txt"+ SPACE+
          ")"+
        "VALUES"+ SPACE+
          "("+
            "?"+ COMMA+ //             "material_uid"
            "?"+ COMMA+ //             "add_reason_cd"
            "?"+ COMMA+ //             "add_time"
            "?"+ COMMA+ //             "add_user_id"
            "?"+ COMMA+ //             "cd"
            "?"+ COMMA+ //             "cd_desc_txt"
            "?"+ COMMA+ //             "description"
            "?"+ COMMA+ //             "effective_duration_amt"
            "?"+ COMMA+ //             "effective_duration_unit_cd"
            "?"+ COMMA+ //             "effective_from_time"
            "?"+ COMMA+ //             "effective_to_time"
            "?"+ COMMA+ //             "form_cd"
            "?"+ COMMA+ //             "form_desc_txt"
            "?"+ COMMA+ //             "handling_cd"
            "?"+ COMMA+ //             "handling_desc_txt"
            "?"+ COMMA+ //             "last_chg_reason_cd"
            "?"+ COMMA+ //             "last_chg_time"
            "?"+ COMMA+ //             "last_chg_user_id"
            "?"+ COMMA+ //             "local_id"
            "?"+ COMMA+ //             "nm"
            "?"+ COMMA+ //             "org_access_permis"
            "?"+ COMMA+ //             "prog_area_acces_permis"
            "?"+ COMMA+ //             "qty"
            "?"+ COMMA+ //             "qty_unit_cd"
            "?"+ COMMA+ //             "record_status_cd"
            "?"+ COMMA+ //             "record_status_time"
            "?"+ COMMA+ //             "risk_cd"
            "?"+ COMMA+ //             "risk_desc_txt"
            "?"+ COMMA+ //             "status_cd"
            "?"+ COMMA+ //             "status_time"
            "?"+        //             "user_affiliation_txt"
          ")";


     //SQL statements for updating in nbs_ods SQL server.
     public static final String UPDATE_UID = "UPDATE " + DataTables.UID_TABLE + " SET uid_value = ? WHERE uid_name = ?";
     public static final String UPDATE_ORGANIZATION_NAME = "UPDATE " + DataTables.ORGANIZATION_NAME_TABLE + " set nm_txt = ?, nm_use_cd = ?, record_status_cd = ?, default_nm_ind = ? WHERE organization_uid = ? AND organization_name_seq = ?";
     public static final String UPDATE_ORGANIZATION =
        "UPDATE " + DataTables.ORGANIZATION_TABLE +
        " set add_reason_cd = ?, add_time = ?, " +
        "add_user_id = ?, cd = ?, cd_desc_txt = ?,"+
        " description = ?, duration_amt = ?, " +
        "duration_unit_cd = ?, from_time = ?, " +
        "last_chg_reason_cd = ?, last_chg_time = ?, "+
        "last_chg_user_id = ?, record_status_cd = ?, " +
        "record_status_time = ?, standard_industry_class_cd = ?, " +
        "standard_industry_desc_txt = ?, status_cd = ?, " +
        "status_time = ?, to_time = ?, user_affiliation_txt = ?, " +
        "display_nm = ?, street_addr1 = ?, street_addr2 = ?," +
        " city_cd = ?, city_desc_txt = ?, state_cd = ?, cnty_cd = ?, " +
        "cntry_cd = ?, zip_cd = ?, phone_nbr = ?, phone_cntry_cd = ?, " +
        "version_ctrl_nbr = ? WHERE organization_uid = ? and version_ctrl_nbr = ?";
     public static final String UPDATE_NONPERSON = "UPDATE " + DataTables.NONPERSON_TABLE + " set add_reason_cd = ?, add_time = ?, add_user_id = ?, birth_gender_cd = ?, birth_order_nbr = ?, birth_time = ?, breed_cd = ?, breed_desc_txt = ?, cd = ?, cd_desc_txt = ?, deceased_ind_cd = ?, deceased_time = ?, description = ?, last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, multiple_birth_ind = ?, nm = ?, org_acces_permis = ?, prog_area_access_permis = ?, record_status_cd = ?, record_status_time = ?, status_cd = ?, status_time = ?, taxonomic_classification_cd = ?, taxonomic_classification_desc = ?, user_affiliation_txt = ? WHERE non_person_uid = ? and version_ctrl_nbr =?";

     // Material Table
     public static final String UPDATE_MATERIAL =
        "UPDATE"+ SPACE +
            DataTables.MATERIAL_TABLE+ SPACE+
        "SET"+ SPACE+
            "add_reason_cd = ?"+ COMMA+
            "add_time = ?"+ COMMA+
            "add_user_id = ?"+ COMMA+
            "cd = ?"+ COMMA+
            "cd_desc_txt = ?"+ COMMA+
            "description = ?"+ COMMA+
            "effective_duration_amt = ?"+ COMMA+
            "effective_duration_unit_cd = ?"+ COMMA+
            "effective_from_time = ?"+ COMMA+
            "effective_to_time = ?"+ COMMA+
            "form_cd = ?"+ COMMA+
            "form_desc_txt = ?"+ COMMA+
            "handling_cd = ?"+ COMMA+
            "handling_desc_txt = ?"+ COMMA+
            "last_chg_reason_cd = ?"+ COMMA+
            "last_chg_time = ?"+ COMMA+
            "last_chg_user_id = ?"+ COMMA+
           // "local_id = ?"+ COMMA+
            "nm = ?"+ COMMA+
            "org_access_permis = ?"+ COMMA+
            "prog_area_access_permis = ?"+ COMMA+
            "qty = ?"+ COMMA+
            "qty_unit_cd = ?"+ COMMA+
            "record_status_cd = ?"+ COMMA+
            "record_status_time = ?"+ COMMA+
            "risk_cd = ?"+ COMMA+
            "risk_desc_txt = ?"+ COMMA+
            "status_cd = ?"+ COMMA+
            "status_time = ?"+ COMMA+
            "user_affiliation_txt = ?"+ SPACE+
        "WHERE"+ SPACE+
            "material_uid = ?";

     //SQL statements for selecting from nbs_ods SQL server.
     public static final String SELECT_UID = "SELECT uid_value FROM " + DataTables.UID_TABLE + " WHERE uid_name = ?";
     public static final String SELECT_NEW_ENTITY_UID = "SELECT MAX (entity_uid) FROM " + DataTables.ENTITY_TABLE;
     public static final String SELECT_PERSON_UID = "SELECT person_UID FROM " + DataTables.PERSON_TABLE + " WHERE person_UID = ?";
     public static final String SELECT_PERSON = "SELECT person_uid \"personUid\", administrative_gender_cd \"administrativeGenderCd\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", age_calc \"ageCalc\", age_calc_time \"ageCalcTime\", age_calc_unit_cd \"ageCalcUnitCd\", age_category_cd \"ageCategoryCd\", age_reported \"ageReported\", age_reported_time \"ageReportedTime\", age_reported_unit_cd \"ageReportedUnitCd\", birth_gender_cd \"birthGenderCd\", birth_order_nbr \"birthOrderNbr\", birth_time \"birthTime\", birth_time_calc \"birthTimeCalc\", cd \"cd\", cd_desc_txt \"cdDescTxt\", curr_sex_cd \"currSexCd\", deceased_ind_cd \"deceasedIndCd\", deceased_time \"deceasedTime\", description \"description\", education_level_cd \"educationLevelCd\", education_level_desc_txt \"educationLevelDescTxt\", ethnic_group_ind \"ethnicGroupInd\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", marital_status_cd \"maritalStatusCd\", "+
                 " marital_status_desc_txt \"maritalStatusDescTxt\", mothers_maiden_nm \"mothersMaidenNm\", multiple_birth_ind \"multipleBirthInd\", " +
                 "occupation_cd \"occupationCd\", org_acces_permis \"orgAccesPermis\", preferred_gender_cd \"preferredGenderCd\", prim_lang_cd \"primLangCd\", prim_lang_desc_txt \"primLangDescTxt\", prog_area_access_permis \"progAreaAccessPermis\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", status_cd \"statusCd\", status_time \"statusTime\", survived_ind_cd \"survivedIndCd\", user_affiliation_txt \"userAffiliationTxt\", " +
                 "first_nm \"firstNm\", last_nm \"lastNm\", middle_nm \"middleNm\", nm_prefix \"nmPrefix\", nm_suffix \"nmSuffix\", preferred_nm \"preferredNm\", hm_street_addr1 \"hmStreetAddr1\", hm_street_addr2 \"hmStreetAddr2\", hm_city_cd \"hmCityCd\", hm_city_desc_txt \"hmCityDescTxt\", hm_state_cd \"hmStateCd\", hm_zip_cd \"hmZipCd\", hm_cnty_cd \"hmCntyCd\", hm_cntry_cd \"hmCntryCd\", hm_phone_nbr \"hmPhoneNbr\", hm_phone_cntry_cd \"hmPhoneCntryCd\", hm_email_addr \"hmEmailAddr\", cell_phone_nbr \"cellPhoneNbr\", wk_street_addr1 \"wkStreetAddr1\", wk_street_addr2 \"wkStreetAddr2\", wk_city_cd \"wkCityCd\", wk_city_desc_txt \"wkCityDescTxt\", wk_state_cd \"wkStateCd\", wk_zip_cd \"wkZipCd\", wk_cnty_cd \"wkCntyCd\", wk_cntry_cd \"wkCntryCd\", wk_phone_nbr \"wkPhoneNbr\", wk_phone_cntry_cd \"wkPhoneCntryCd\", wk_email_addr \"wkEmailAddr\", SSN \"SSN\", medicaid_num \"medicaidNum\", dl_num \"dlNum\", dl_state_cd \"dlStateCd\", race_cd \"raceCd\", race_seq_nbr \"raceSeqNbr\","+
                 " race_category_cd \"raceCategoryCd\", ethnicity_group_cd \"ethnicityGroupCd\", ethnic_group_seq_nbr \"ethnicGroupSeqNbr\", adults_in_house_nbr \"adultsInHouseNbr\", " +
                 "children_in_house_nbr \"childrenInHouseNbr\", birth_city_cd \"birthCityCd\", birth_city_desc_txt \"birthCityDescTxt\", birth_cntry_cd \"birthCntryCd\", birth_state_cd \"birthStateCd\", local_id \"localId\" FROM " + DataTables.PERSON_TABLE + " WHERE person_uid = ?";
     public static final String SELECT_ORGANIZATION_NAME_UID = "SELECT organization_UID FROM " + DataTables.ORGANIZATION_NAME_TABLE + " WHERE organization_UID = ?";
     public static final String SELECT_ORGANIZATION_NAMES = "SELECT organization_UID \"organizationUid\", organization_name_seq \"organizationNameSeq\", nm_txt \"nmTxt\",nm_use_cd \"nmUseCd\"  FROM " + DataTables.ORGANIZATION_NAME_TABLE + " WHERE organization_UID = ?";
     public static final String SELECT_ORGANIZATION = "SELECT add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", cd \"cd\", cd_desc_txt \"cdDescTxt\", description \"description\", duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\", from_time \"fromTime\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", standard_industry_class_cd \"standardIndustryClassCd\", standard_industry_desc_txt \"standardIndustryDescTxt\", status_cd \"statusCd\", status_time \"statusTime\", to_time \"toTime\", user_affiliation_txt \"userAffiliationTxt\", display_nm \"displayNm\", street_addr1 \"streetAddr1\", street_addr2 \"streetAddr2\", city_cd \"cityCd\", city_desc_txt \"cityDescTxt\", state_cd \"stateCd\", cnty_cd \"cntyCd\", cntry_cd \"cntryCd\", zip_cd \"zipCd\", phone_nbr \"phoneNbr\", " +
                                                        "phone_cntry_cd \"phoneCntryCd\", organization_uid \"organizationUid\", local_id \"localId\", version_ctrl_nbr \"versionCtrlNbr\" FROM " + DataTables.ORGANIZATION_TABLE + " WHERE organization_UID = ?";
     public static final String SELECT_ORGANIZATION_UID = "SELECT organization_uid \"organizationUid\" FROM " + DataTables.ORGANIZATION_TABLE + " WHERE organization_UID = ?";
     public static final String SELECT_NONPERSON = "SELECT add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", birth_gender_cd \"birthGenderCd\", birth_order_nbr \"birthOrderNbr\", birth_time \"birthTime\", breed_cd \"breedCd\", breed_desc_txt \"breedDescTxt\", cd \"cd\", cd_desc_txt \"cdDescTxt\", deceased_ind_cd \"deceasedIndCd\", deceased_time \"deceasedTime\", description \"description\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", local_id \"localId\", multiple_birth_ind \"multipleBirthInd\", nm \"nm\", org_acces_permis \"orgAccesPermis\", prog_area_access_permis \"progAreaAccessPermis\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", status_cd \"statusCd\", status_time \"statusTime\", taxonomic_classification_cd \"taxonomicClassificationCd\", taxonomic_classification_desc \"taxonomicClassificationDesc\", user_affiliation_txt \"userAffiliationTxt\", non_person_uid \"nonPersonUid\" FROM " + DataTables.NONPERSON_TABLE +
                " WHERE non_person_uid = ?";
     public static final String SELECT_NONPERSON_UID = "SELECT non_person_uid \"nonPersonUid\" FROM " + DataTables.NONPERSON_TABLE + " WHERE non_person_uid = ?";

     // PersonHistory tables
      public static final String SELECT_SEQ_NUMBER_HIST
      = "SELECT version_ctrl_nbr from Person_hist where person_uid = ?";



 public static final String INSERT_ENTITY_ID_HIST
      = "INSERT INTO Entity_id_hist ( "
      + "entity_uid, "
      + "entity_id_seq, "
      + "version_ctrl_nbr, "
      + "add_reason_cd, "
      + "add_time, "
      + "add_user_id, "
      + "as_of_date, "
      + "assigning_authority_cd, "
      + "assigning_authority_desc_txt, "
      + "duration_amt, "
      + "duration_unit_cd, "
      + "last_chg_reason_cd, "
      + "last_chg_time, "
      + "last_chg_user_id, "
      + "record_status_cd, "
      + "record_status_time, "
      + "root_extension_txt, "
      + "status_cd, "
      + "status_time, "
      + "type_cd, "
      + "type_desc_txt, "
      + "user_affiliation_txt, "
      + "valid_from_time, "
      + "valid_to_time, "
      +	"assigning_authority_id_type"
      + " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";


    public static final String INSERT_ENTITY_LOC_PARC_HIST
      = "INSERT INTO Entity_loc_participation_hist ( "
      + "entity_uid, "
      + "locator_uid, "
      + "version_ctrl_nbr, "
      + "add_reason_cd, "
      + "add_time, "
      + "add_user_id, "
      + "cd, "
      + "cd_desc_txt, "
	  + "class_cd, "
	  + "duration_amt, "
      + "duration_unit_cd, "
      + "from_time, "
	  + "last_chg_reason_cd, "
	  + "last_chg_time, "
      + "last_chg_user_id, "
      + "locator_desc_txt, "
      + "record_status_cd, "
      + "record_status_time, "
      + "status_cd, "
      + "status_time, "
      + "to_time, "
      + "use_cd, "
      + "user_affiliation_txt, "
      + "valid_time_txt, "
      + "as_of_date "
      + " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";


     public static final String INSERT_PHYSICAL_LOCATOR_HIST = "INSERT INTO " + DataTables.PHYSICAL_LOCATOR_TABLE_HIST  + "(physical_locator_uid, version_ctrl_nbr, add_reason_cd, add_time, add_user_id, image_txt, last_chg_reason_cd, last_chg_time, last_chg_user_id, locator_txt, user_affiliation_txt) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
     public static final String INSERT_POSTAL_LOCATOR_HIST = "INSERT INTO " + DataTables.POSTAL_LOCATOR_TABLE_HIST + "(postal_locator_uid, version_ctrl_nbr, add_reason_cd, add_time, add_user_id, census_block_cd, census_minor_civil_division_cd, census_track_cd, city_cd, city_desc_txt, cntry_cd, cnty_cd,  last_chg_reason_cd, last_chg_time, last_chg_user_id, msa_congress_district_cd, region_district_cd, state_cd, street_addr1, street_addr2, user_affiliation_txt, zip_cd, geocode_match_ind, within_city_limits_ind) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
     public static final String INSERT_TELE_LOCATOR_HIST = "INSERT INTO " + DataTables.TELE_LOCATOR_TABLE_HIST + "(tele_locator_uid, version_ctrl_nbr, add_reason_cd, add_time, add_user_id, cntry_cd, email_address, extension_txt, last_chg_reason_cd, last_chg_time, last_chg_user_id, phone_nbr_txt, url_address, user_affiliation_txt) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";


     public static final String SELECT_PERSON_HIST = "SELECT person_uid \"personUid\", administrative_gender_cd \"administrativeGenderCd\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", age_calc \"ageCalc\", age_calc_time \"ageCalcTime\", age_calc_unit_cd \"ageCalcUnitCd\", age_category_cd \"ageCategoryCd\", age_reported \"ageReported\", age_reported_time \"ageReportedTime\", age_reported_unit_cd \"ageReportedUnitCd\", birth_gender_cd \"birthGenderCd\", birth_order_nbr \"birthOrderNbr\", birth_time \"birthTime\", birth_time_calc \"birthTimeCalc\", cd \"cd\", cd_desc_txt \"cdDescTxt\", curr_sex_cd \"currSexCd\", deceased_ind_cd \"deceasedIndCd\", deceased_time \"deceasedTime\", description \"description\", education_level_cd \"educationLevelCd\", education_level_desc_txt \"educationLevelDescTxt\", ethnic_group_ind \"ethnicGroupInd\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", marital_status_cd \"maritalStatusCd\", "+
                 " marital_status_desc_txt \"maritalStatusDescTxt\", mothers_maiden_nm \"mothersMaidenNm\", multiple_birth_ind \"multipleBirthInd\", " +
                 "occupation_cd \"occupationCd\", org_access_permis \"orgAccessPermis\", preferred_gender_cd \"preferredGenderCd\", prim_lang_cd \"primLangCd\", prim_lang_desc_txt \"primLangDescTxt\", prog_area_access_permis \"progAreaAccessPermis\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", status_cd \"statusCd\", status_time \"statusTime\", survived_ind_cd \"survivedIndCd\", user_affiliation_txt \"userAffiliationTxt\", " +
                 "first_nm \"firstNm\", last_nm \"lastNm\", middle_nm \"middleNm\", nm_prefix \"nmPrefix\", nm_suffix \"nmSuffix\", preferred_nm \"preferredNm\", hm_street_addr1 \"hmStreetAddr1\", hm_street_addr2 \"hmStreetAddr2\", hm_city_cd \"hmCityCd\", hm_city_desc_txt \"hmCityDescTxt\", hm_state_cd \"hmStateCd\", hm_zip_cd \"hmZipCd\", hm_cnty_cd \"hmCntyCd\", hm_cntry_cd \"hmCntryCd\", hm_phone_nbr \"hmPhoneNbr\", hm_phone_cntry_cd \"hmPhoneCntryCd\", hm_email_addr \"hmEmailAddr\", cell_phone_nbr \"cellPhoneNbr\", wk_street_addr1 \"wkStreetAddr1\", wk_street_addr2 \"wkStreetAddr2\", wk_city_cd \"wkCityCd\", wk_city_desc_txt \"wkCityDescTxt\", wk_state_cd \"wkStateCd\", wk_zip_cd \"wkZipCd\", wk_cnty_cd \"wkCntyCd\", wk_cntry_cd \"wkCntryCd\", wk_phone_nbr \"wkPhoneNbr\", wk_phone_cntry_cd \"wkPhoneCntryCd\", wk_email_addr \"wkEmailAddr\", SSN \"SSN\", medicaid_num \"medicaidNum\", dl_num \"dlNum\", dl_state_cd \"dlStateCd\", race_cd \"raceCd\", race_seq_nbr \"raceSeqNbr\","+
                 " race_category_cd \"raceCategoryCd\", adults_in_house_nbr \"adultsInHouseNbr\", " +
                 "children_in_house_nbr \"childrenInHouseNbr\", birth_city_cd \"birthCityCd\", birth_city_desc_txt \"birthCityDescTxt\", birth_cntry_cd \"birthCntryCd\", birth_state_cd \"birthStateCd\", local_id \"localId\" FROM " + DataTables.PERSON_TABLE_HIST + " WHERE person_uid = ? AND version_ctrl_nbr = ? ";

//     public static final String SELECT_PERSON_NAMES_HIST = "SELECT person_uid \"personUid\", person_name_seq \"personNameSeq\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\", first_nm \"firstNm\", first_nm_sndx \"firstNmSndx\", from_time \"fromTime\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", last_nm \"lastNm\", last_nm_sndx \"lastNmSndx\", last_nm2 \"lastNm2\", last_nm2_sndx \"lastNm2Sndx\", middle_nm \"middleNm\", middle_nm2 \"middleNm2\", nm_degree \"nmDegree\", nm_prefix \"nmPrefix\", nm_suffix \"nmSuffix\", nm_use_cd \"nmUseCd\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", status_cd \"statusCd\", status_time \"statusTime\", to_time \"toTime\", user_affiliation_txt \"userAffiliationTxt\" FROM " + DataTables.PERSON_NAME_TABLE_HIST + " WHERE person_uid = ? AND version_ctrl_nbr = ? ";

//     public static final String SELECT_PERSON_RACES_HIST = "SELECT person_uid \"personUid\", race_cd \"raceCd\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", race_category_cd \"raceCategoryCd\", race_desc_txt \"raceDescTxt\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", user_affiliation_txt \"userAffiliationTxt\" FROM " + DataTables.PERSON_RACE_TABLE_HIST + " WHERE person_uid = ? AND version_ctrl_nbr = ? ";

//     public static final String SELECT_PERSON_ETHNIC_GROUPS_HIST = "SELECT person_uid \"personUid\", ethnic_group_cd \"ethnicGroupCd\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", ethnic_group_desc_txt \"ethnicGroupDescTxt\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", user_affiliation_txt \"userAffiliationTxt\" FROM " + DataTables.PERSON_ETHNIC_GROUP_TABLE_HIST + " WHERE person_uid = ? AND version_ctrl_nbr = ? ";

    public static final String SELECT_ENTITY_IDS_HIST = "SELECT entity_uid \"entityUid\", entity_id_seq \"entityIdSeq\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", as_of_date \"asOfDate\",assigning_authority_cd \"assigningAuthorityCd\", assigning_authority_desc_txt \"assigningAuthorityDescTxt\", duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", root_extension_txt \"rootExtensionTxt\", status_cd \"statusCd\", status_time \"statusTime\", type_cd \"typeCd\", type_desc_txt \"typeDescTxt\", user_affiliation_txt \"userAffiliationTxt\", valid_from_time \"validFromTime\", valid_to_time \"validToTime\",   assigning_authority_id_type \"assigningAuthorityIdType\"  FROM " + DataTables.ENTITY_ID_TABLE_HIST +  " WHERE entity_uid = ? AND version_ctrl_nbr = ?";

     public static final String SELECT_ENTITY_LOCATOR_PARTICIPATION_HIST = "SELECT entity_uid \"entityUid\", locator_uid \"locatorUid\", cd \"cd\",  cd_desc_txt \"cdDescTxt\", class_cd \"classCd\", duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\",  from_time \"fromTime\",  locator_desc_txt \"locatorDescTxt\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\",  status_cd \"statusCd\", status_time \"statusTime\", to_time \"toTime\", use_cd \"useCd\", user_affiliation_txt \"userAffiliationTxt\", valid_time_txt \"validTimeTxt\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\",  last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", as_of_date \"asOfDate\" FROM " + DataTables.ENTITY_LOCATOR_PARTICIPATION_TABLE_HIST + " WHERE entity_uid = ? AND version_ctrl_nbr = ?";

     public static final String SELECT_CURRENT_VIEW_HIST = "select last_chg_time \"LastChgTime\" from Person " +
                                "where person_uid = ?";

    public static final String SELECT_PERSON_HIST_ITEMS = "select last_chg_time \"LastChgTime\", version_ctrl_nbr \"versionCtrlNbr\" from Person_hist " +
                                "where person_uid = ?" ;

     public static final String SELECT_PHYSICAL_LOCATOR_HIST = "SELECT physical_locator_uid \"physicalLocatorUid\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_User_id \"addUserId\",last_chg_reason_cd \"lastChgReasonCd\", image_txt \"imageTxt\", locator_txt \"locatorTxt\", user_affiliation_txt \"userAffiliationTxt\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\" FROM " + DataTables.PHYSICAL_LOCATOR_TABLE_HIST + " WHERE physical_locator_uid = ? AND version_ctrl_nbr = ?";
     public static final String SELECT_POSTAL_LOCATOR_HIST = "SELECT postal_locator_uid \"postalLocatorUid\", census_block_cd \"censusBlockCd\", census_minor_civil_division_cd \"censusMinorCivilDivisionCd\", census_track_cd \"censusTrackCd\", city_cd \"cityCd\" , city_desc_txt \"cityDescTxt\", cntry_cd \"cntryCd\", cnty_cd \"cntyCd\", msa_congress_district_cd \"msaCongressDistrictCd\", region_district_cd \"regionDistrictCd\", state_cd \"stateCd\", street_addr1 \"streetAddr1\", street_addr2 \"streetAddr2\", user_affiliation_txt \"userAffiliationTxt\", zip_cd \"zipCd\", geocode_match_ind \"geocodeMatchInd\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", within_city_limits_ind \"withinCityLimitsInd\" FROM " + DataTables.POSTAL_LOCATOR_TABLE_HIST + " WHERE postal_locator_uid = ? AND postal_locator_seq = ?";
     public static final String SELECT_TELE_LOCATOR_HIST = "SELECT tele_locator_uid \"teleLocatorUid\", cntry_cd \"cntryCd\", email_address \"emailAddress\", extension_txt \"extensionTxt\", phone_nbr_txt \"phoneNbrTxt\", url_address \"urlAddress\", user_affiliation_txt \"userAffiliationTxt\",add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\" FROM " + DataTables.TELE_LOCATOR_TABLE_HIST + " WHERE tele_locator_uid = ? AND version_ctrl_nbr = ?";

     // Material Table
     public static final String SELECT_MATERIAL =
         "SELECT"+ SPACE+
            "material_uid AS \"materialUid\" "+ COMMA+
            "add_reason_cd AS \"addReasonCd\" "+ COMMA+
            "add_time AS \"addTime\" "+ COMMA+
            "add_user_id AS \"addUserId\" "+ COMMA+
            "cd AS \"cd\" "+ COMMA+
            "cd_desc_txt AS \"cdDescTxt\" "+ COMMA+
            "description AS \"description\" "+ COMMA+
            "effective_duration_amt AS \"effectiveDurationAmt\" "+ COMMA+
            "effective_duration_unit_cd AS \"effectiveDurationUnitCd\" "+ COMMA+
            "effective_from_time AS \"effectiveFromTime\" "+ COMMA+
            "effective_to_time AS \"effectiveToTime\" "+ COMMA+
            "form_cd AS \"formCd\" "+ COMMA+
            "form_desc_txt AS \"formDescTxt\" "+ COMMA+
            "handling_cd AS \"handlingCd\" "+ COMMA+
            "handling_desc_txt AS \"handlingDescTxt\" "+ COMMA+
            "last_chg_reason_cd AS \"lastChgReasonCd\" "+ COMMA+
            "last_chg_time AS \"lastChgTime\" "+ COMMA+
            "last_chg_user_id AS \"lastChgUserId\" "+ COMMA+
            "local_id AS \"localId\" "+ COMMA+
            "nm AS \"nm\" "+ COMMA+
            "org_access_permis AS \"orgAccessPermis\" "+ COMMA+
            "prog_area_access_permis AS \"progAreaAccesPermis\" "+ COMMA+
            "qty AS \"qty\" "+ COMMA+
            "qty_unit_cd AS \"qtyUnitCd\" "+ COMMA+
            "record_status_cd AS \"recordStatusCd\" "+ COMMA+
            "record_status_time AS \"recordStatusTime\" "+ COMMA+
            "risk_cd AS \"riskCd\" "+ COMMA+
            "risk_desc_txt AS \"riskDescTxt\" "+ COMMA+
            "status_cd AS \"statusCd\" "+ COMMA+
            "status_time AS \"statusTime\" "+ COMMA+
            "user_affiliation_txt AS \"userAffiliationTxt\" "+ SPACE+
        "FROM"+ SPACE+
            DataTables.MATERIAL_TABLE+ SPACE+
        "WHERE"+ SPACE+
            "material_uid = ?";

     public static final String SELECT_MATERIAL_UID =
        "SELECT"+ SPACE+
            "material_uid AS MaterialUid"+ SPACE+
        "FROM"+ SPACE+
            DataTables.MATERIAL_TABLE+ SPACE+
        "WHERE"+ SPACE+
        "material_UID = ? ";

     //SQL statements for deleting from nbs_ods SQL server.
     public static final String DELETE_PERSON_NAMES = "DELETE FROM " + DataTables.PERSON_NAME_TABLE + " WHERE person_uid = ?";
     public static final String DELETE_PERSON_RACES = "DELETE FROM " + DataTables.PERSON_RACE_TABLE + " WHERE person_uid = ?";
     public static final String DELETE_PERSON_ETHNIC_GROUPS = "DELETE FROM " + DataTables.PERSON_ETHNIC_GROUP_TABLE + " WHERE person_uid = ?";
     public static final String DELETE_ENTITY_IDS = "DELETE FROM " + DataTables.ENTITY_ID_TABLE + " WHERE entity_uid = ?";
     public static final String DELETE_PERSON = "DELETE FROM " + DataTables.PERSON_TABLE + " WHERE person_uid = ?";
     public static final String DELETE_ORGANIZATION_NAMES = "DELETE FROM " + DataTables.ORGANIZATION_NAME_TABLE + " WHERE organization_uid = ?";
     public static final String DELETE_ORGANIZATION = "DELETE FROM " + DataTables.ORGANIZATION_TABLE + " WHERE organization_uid = ?";
     public static final String DELETE_NONPERSON = "DELETE FROM " + DataTables.NONPERSON_TABLE + " WHERE non_person_uid = ?";

     //Sql statements for entitygroup

     public static final String ENTITY_UID = "ENTITY_UID";
     public static final String SELECT_ENTITYGROUP_UID = "SELECT entity_group_uid FROM " + DataTables.ENTITY_GROUP_TABLE + " WHERE entity_group_uid = ?";
     public static final String INSERT_ENTITYGROUP = "INSERT INTO " + DataTables.ENTITY_GROUP_TABLE + " (entity_group_uid, add_reason_cd, add_time, add_user_id, cd, cd_desc_txt, description, duration_amt, duration_unit_cd, from_time, group_cnt, last_chg_reason_cd, last_chg_time, last_chg_user_id, local_id, nm, org_access_permis, prog_area_access_permis, record_status_cd, record_status_time, status_cd, status_time, to_time, user_affiliation_txt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
     public static final String UPDATE_ENTITYGROUP = "UPDATE " + DataTables.ENTITY_GROUP_TABLE + " SET add_reason_cd = ?, add_time = ?, add_user_id = ?, cd = ?, cd_desc_txt = ?, description = ?, duration_amt = ?, duration_unit_cd = ?, from_time = ?, group_cnt = ?, last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, nm = ?, org_access_permis = ?, prog_area_access_permis = ?, record_status_cd = ?, record_status_time = ?, status_cd = ?, status_time = ?, to_time = ?, user_affiliation_txt = ? WHERE entity_group_uid = ?";
     public static final String SELECT_ENTITYGROUP = "SELECT entity_group_uid \"entityGroupUid, add_reason_cd \"addReasonCd, add_time \"addTime, add_user_id \"addUserId, cd \"cd, cd_desc_txt \"cdDescTxt, description \"description, duration_amt \"durationAmt, duration_unit_cd \"durationUnitCd, from_time \"fromTime, group_cnt \"groupCnt, last_chg_reason_cd \"lastChgReasonCd, last_chg_time \"lastChgTime, last_chg_user_id \"lastChgUserId, local_id \"localId, nm \"nm, org_access_permis \"orgAccessPermis, prog_area_access_permis \"progAreaAccessPermis, record_status_cd \"recordStatusCd, record_status_time \"recordStatusTime, status_cd \"statusCd, status_time \"statusTime, to_time \"toTime, user_affiliation_txt \"userAffiliationTxt FROM " + DataTables.ENTITY_GROUP_TABLE + " WHERE entity_group_uid = ?";
     public static final String DELETE_ENTITYGROUP = "DELETE FROM " + DataTables.ENTITY_GROUP_TABLE + " WHERE (entity_group_uid = ?)";

     // Material Table
     public static final String DELETE_MATERIAL =
        "DELETE FROM"+ SPACE+
            DataTables.MATERIAL_TABLE+ SPACE+
        "WHERE"+ SPACE+
            "material_uid = ?";

	// SQL Statements involving the Place table/entity
	public static final String SELECT_PLACE_UID = "SELECT place_uid FROM " + DataTables.PLACE_TABLE + " WHERE place_uid = ?";
	public static final String SELECT_PLACE =
		 	"SELECT " +
		 	"place_uid as 'placeUid', " +
		 	"add_reason_cd as 'addReasonCd'," +
		 	"add_time as 'addTime'," +
		 	"add_user_id as 'addUserId'," +
		 	"cd as 'cd'," +
		 	"cd_desc_txt as 'cdDescTxt'," +
		 	"description as 'description'," +
		 	"duration_amt as 'durationAmt'," +
		 	"duration_unit_cd as 'durationUnitCd'," +
		 	"from_time as 'fromTime'," +
		 	"last_chg_reason_cd as 'lastChgReasonCd'," +
		 	"last_chg_time as 'lastChgTime'," +
		 	"last_chg_user_id as 'lastChgUserId'," +
		 	"nm as 'nm'," +
		 	"record_status_cd as 'recordStatusCd," +
		 	"record_status_time as 'recordStatusTime'," +
		 	"status_cd as 'statusCd'," +
		 	"status_time as 'statusTime'," +
		 	"to_time as 'toTime'," +
		 	"user_affiliation_txt as 'userAffiliationTxt'," +
		 	"street_addr1 as 'streetAddr1'," +
		 	"street_addr2 as 'streetAddr2'," +
		 	"city_cd as 'cityCd'," +
		 	"city_desc_txt 'cityDescTxt'," +
		 	"state_cd as 'stateCd'," +
		 	"zip_cd as 'zipCd'," +
		 	"cnty_cd as 'cntyCd'," +
		 	"cntry_cd as 'cntryCd'," +
		 	"phone_nbr as 'phoneNbr'," +
		 	"phone_cntry_cd as 'phoneCntryCd'," +
                        "version_ctrl_nbr as 'versionCtrlNbr', "+
		 	"local_id as 'localId' FROM  " +
		 	DataTables.PLACE_TABLE +
		 	" WHERE (place_uid=?)";
	public static final String INSERT_PLACE = "INSERT INTO " + DataTables.PLACE_TABLE + " (place_uid, add_reason_cd, add_time, add_user_id, cd, cd_desc_txt, description, duration_amt, duration_unit_cd, from_time, last_chg_reason_cd, " +
			" last_chg_time, last_chg_user_id, nm, record_status_cd, record_status_time, status_cd, status_time, to_time, user_affiliation_txt, street_addr1, street_addr2, city_cd, city_desc_txt, state_cd, zip_cd, cnty_cd, cntry_cd, phone_nbr, phone_cntry_cd, local_id, version_ctrl_nbr) " +
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_PLACE = "UPDATE " + DataTables.PLACE_TABLE + " SET add_reason_cd = ?, add_time = ?, add_user_id = ?, cd = ?, cd_desc_txt = ?, description = ?, duration_amt = ?, duration_unit_cd = ?, from_time = ?, " +
			" last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, nm = ?, record_status_cd = ?, " +
			" record_status_time = ?, status_cd = ?, status_time = ?, to_time = ?, user_affiliation_txt = ?, street_addr1 = ?, street_addr2 = ?, city_cd = ?, " +
			" city_desc_txt = ?, state_cd = ?, zip_cd = ?, cnty_cd = ?, cntry_cd = ?, phone_nbr = ?, phone_cntry_cd = ?, version_ctrl_nbr = ? " +
			" WHERE (place_uid = ?)";
	public static final String DELETE_PLACE = "DELETE FROM " + DataTables.PLACE_TABLE + " WHERE (place_uid = ?)";




         /* To test new optimization */
    public static final String BASENAMEQUERYSQL =
      " SELECT p.person_uid \"personUid\", p.local_id \"localId\", pn.last_nm \"lastName\", pn.middle_nm \"nmMiddle\",  "+
      " pn.first_nm \"firstName\", p.record_status_cd \"recordStatusCd\", "+
      " pn.nm_use_cd \"nameUseCd\", " +
      " pn.nm_suffix \"nmSuffix\" , " +
      " pn.nm_degree \"nmDegree\" , " +
      " p.marital_status_cd \"maritalStatusCd\" ," +
      " p.ethnic_group_ind \"ethnicGroupInd\" , " +
      "  convert(varchar, p.birth_time,101)  \"dob\", "+
      " p.curr_sex_cd \"currentSex\",  "+
      " p.person_parent_uid \"personParentUid\", "+
      " p.version_ctrl_nbr \"versionCtrlNbr\", "+
      " p.deceased_time \"deceasedTime\" " +
      " FROM person p with (nolock) , person_name pn with (nolock) "+
      " where " +
      "	p.person_uid = pn.person_uid "+
      " and pn.record_status_cd = 'ACTIVE' " ;

    public static final String BASEADDRESSQUERYSQL =
      " SELECT p.person_uid \"personUid\", p.local_id \"localId\", "+
      " p.record_status_cd \"recordStatusCd\", "+
      " p.version_ctrl_nbr \"versionCtrlNbr\", " +
      " pl.postal_locator_uid \"locatorUid\", "+
      "	elp1.class_Cd \"classCd\", "+
       " elp1.cd \"locatorTypeCdDesc\" , " +
      "	elp1.use_cd \"locatorUseCd\", elp1.cd \"locatorCd\", "+
      " pl.street_addr1 \"streetAddr1\", "+
      " pl.street_addr2 \"streetAddr2\", "+
      "	pl.city_desc_txt \"city\", pl.zip_cd \"zip\", pl.cnty_cd \"cntyCd\", pl.state_cd \"state\", "+
      " p.person_parent_uid \"personParentUid\" "+
      "	from person p with (nolock), entity_locator_participation elp1 with (nolock), " +
      "	postal_locator pl with (nolock) "+
      "	where p.person_uid = elp1.entity_uid  "+
      "	and elp1.class_cd = 'PST' "+
      "	and elp1.status_cd = 'A' "+
      "	and elp1.locator_uid = pl.postal_locator_uid " ;


   public static final String BASEIDQUERYSQL =
    " SELECT p.person_uid \"personUid\", p.local_id \"localId\", "+
    " ei.root_extension_txt \"eiRootExtensionTxt\" ,  " +
    " ei.type_cd \"eiTypeDesc\", " +
    " ei.type_cd \"eiTypeCd\", " +
    " ei.assigning_authority_cd \"eiAssigningAuthorityCd\", " +
    " ei.assigning_authority_desc_txt \"eiAssigningAuthorityDescTxt\", " +
    " p.person_parent_uid \"personParentUid\", version_ctrl_nbr \"versionCtrlNbr\" "+
    "	from " +
    "	person p with (nolock),  Entity_id ei with (nolock)"+
    "	where  "+
    "	p.person_uid = ei.entity_uid and ei.status_cd = 'A' and p.record_status_cd='ACTIVE'" ;

    public static final String BASETELEQUERYSQL =
      "	SELECT p.person_uid \"personUid\", p.local_id \"localId\", "+
      " p.version_ctrl_nbr \"versionCtrlNbr\", " +
      " tl.tele_locator_uid \"locatorUid\","+
      " elp2.class_cd \"classCd\", "+
      " elp2.use_cd \"locatorUseCd\", elp2.cd \"locatorCd\", "+
        " tl.phone_nbr_txt \"telephoneNbr\", " +
      "	tl.extension_txt \"extensionTxt\", tl.email_address \"emailAddress\", "+
      "	null \"eiRootExtensionTxt\", null \"eiTypeDesc\", "+
      " p.person_parent_uid \"personParentUid\" "+
      "	from " +
      "	person p with (nolock), entity_locator_participation elp2 with (nolock), " +
      "	tele_locator tl with (nolock) "+
      "  where  "+
      "	p.person_uid = elp2.entity_uid   "+
      "	and elp2.class_cd = 'TELE' "+
      "	and elp2.status_cd= 'A' "+
      "	and elp2.locator_uid  = tl.tele_locator_uid ";


    public static final String BASERACEQUERYSQL =
        " SELECT  p.person_uid \"personUid\", p.local_id \"localId\", "+
        " pr.race_cd \"raceCd\", "+
        " p.version_ctrl_nbr \"versionCtrlNbr\", " +
        " p.person_parent_uid \"personParentUid\" "+
        " FROM person p with (nolock), person_race pr with (nolock)"+
        " where " +
       	" p.person_uid = pr.person_uid " +
        " and pr.record_status_cd = 'ACTIVE' ";

    public static final String BASEETHNICITYQUERYSQL =
        " SELECT  p.person_uid \"personUid\", p.local_id \"localId\", "+
        " p.version_ctrl_nbr \"versionCtrlNbr\", " +
        " p.person_parent_uid \"personParentUid\" "+
        " FROM person p with (nolock) " ;

    public static final String BASEROLEQUERYSQL =
        " SELECT  p.person_uid \"personUid\", p.local_id \"localId\", "+
        " p.version_ctrl_nbr \"versionCtrlNbr\", " +
        " p.person_parent_uid \"personParentUid\" "+
        " FROM person p with (nolock), role r with (nolock) " +
        " where p.person_uid = r.subject_entity_uid " ;


    public static final String PATIENTUIDBYPARENTUID =
        " SELECT  p.person_uid \"personUid\" "+
        " FROM person p with (nolock) " +
        " where p.person_parent_uid = ? AND p.record_status_cd = 'ACTIVE' " ;

    public static final String PATIENTPARENTUID_BY_UID =
        " SELECT p.person_parent_uid \"personParentUid\" " +
        " FROM person p with (nolock) " +
        " where p.person_uid = ? AND p.record_status_cd = 'ACTIVE' ";


     public static final String SELECT_ORDERED_TEST_SEARCH_SQL =
          "Select LABORATORY_ID \"laboratoryId\", "+
          " LAB_TEST_CD \"labTestCd\", "+
          " LAB_TEST_DESC_TXT \"labTestDescription\" from " +
          NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..LAB_TEST " +
          " where test_type_cd = \'O\' ";
    
    public static final String SELECT_CODE_SET_GROUP_ID_SQL = "select code_set_group_id from nbs_odse..wa_question";
        
    public static final String SELECT_CODED_RESULT_SEARCH_SQL =
            " Select LABORATORY_ID \"laboratoryId\", LAB_RESULT_CD \"labResultCd\", LAB_RESULT_DESC_TXT \"labResultDescription\" " +
            " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..LAB_RESULT " +
            " where ORGANISM_NAME_IND = \'N\' ";

    public static final String SELECT_CODED_RESULT_SNOMED_SEARCH_SQL =
            " Select snomed_cd \"snomedCd\", snomed_desc_txt \"snomedDescTxt\" " +
            " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..SNOMED_code " +
            " where ";
    
    public static final String SELECT_RESULTED_TEST_SEARCH_SQL =
                " Select LABORATORY_ID \"laboratoryId\", LAB_TEST_CD \"labTestCd\", LAB_TEST_DESC_TXT \"labTestDescription\", ORGANISM_RESULT_TEST_IND \"labOrganismIndicator\" " +
                " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..LAB_TEST " +
                " where test_type_cd = \'R\' ";

    public static final String SELECT_DRUG_SEARCH_SQL =
                  "Select lab_test.LABORATORY_ID \"laboratoryId\", lab_test.LAB_TEST_CD \"labTestCd\", lab_test.LAB_TEST_DESC_TXT \"labTestDescription\" " +
                  " from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Lab_test lab_test, " +
                    NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Lab_coding_system Lab_coding_system" +
                  " where Lab_coding_system.laboratory_id = Lab_test.laboratory_id " +
                  " and Lab_test.drug_test_ind = \'Y\' ";


  

		/*
		Because of the changes for SNOMED search

		public static final String SELECT_ORGANISM_SEARCH_SQL =
               "Select lab_result.LABORATORY_ID \"laboratoryId\", lab_result.LAB_RESULT_CD \"labTestCd\", lab_result.LAB_RESULT_DESC_TXT \"labTestDescription\" " +
               " from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..lab_result lab_result" +
               " where lab_result.organism_name_ind = \'Y\' ";
		*/
	public static final String SELECT_ORGANISM_SEARCH_SQL =
               "Select SNOMED_CD \"labTestCd\",SNOMED_DESC_TXT \"labTestDescription\" " +
               " from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..SNOMED_CODE " +
								" where ";

   public static final String SELECT_RESULTED_ORDERED_LOINC_TEST_SEARCH_SQL =
              "Select Loinc_code.loinc_cd \"loincCd\", " +
                    " Loinc_code.component_name \"loincComponentName\","+
                    " Loinc_code.method_type \"loincMethod\", " +
                    " Loinc_code.system_cd \"loincSystem\", " +
                    " Loinc_code.property \"loincProperty\", " +
                    " Loinc_code.related_class_cd \"relatedClassCd\" " +
                    " from  nbs_srte..Loinc_code Loinc_code" +
                    " where Loinc_code.related_class_cd in (\'ABXBACT\', \'BC\', \'CELLMARK\',\'CHAL\',\'CHALSKIN\',\'CHEM\',\'COAG\',\'CYTO\',\'DRUG\',\'DRUG/TOX\',\'HEM\',\'HEM/BC\',\'MICRO\',\'MISC\',\'PANEL.ABXBACT\',\'PANEL.BC\',\'PANEL.CHEM\',\'PANEL.MICRO\',\'PANEL.OBS\',\'PANEL.SERO\',\'PANEL.TOX\',\'PANEL.UA\',\'SERO\',\'SPEC\',\'TOX\',\'UA\',\'VACCIN\') ";


   public static final String SELECT_DRUG_LOINC_SEARCH_SQL =
                 "Select Loinc_code.loinc_cd \"loincCd\", Loinc_code.component_name \"loincComponentName\", Loinc_code.method_type \"loincMethod\", Loinc_code.system_cd \"loincSystem\", Loinc_code.property \"loincProperty\" " +
                 " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE+ "..Loinc_code Loinc_code" +
                 " where Loinc_code.property = \'SUSC\' " +
                 " and Loinc_code.related_class_cd = \'ABXBACT\' ";

  public static final String SELECT_ORGANISM_LIST_SQL  =
           "Select  Lab_result.LAB_RESULT_CD \"key\", lab_result_desc_txt \"value\" from "
                + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Lab_result Lab_result, "
                + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Lab_coding_system Lab_coding_system WHERE "+
                " Lab_coding_system.laboratory_id = 'DEFAULT' and "+
                " Lab_result.organism_name_ind = 'Y'";

  public static final String BASELOCALIDQUERYSQL =
      " SELECT p.person_uid \"personUid\", p.local_id \"localId\",  "+
      " p.record_status_cd \"recordStatusCd\", "+
      " convert(varchar, p.birth_time,101)  \"dob\", "+
      " p.curr_sex_cd \"currentSex\",  "+
      " p.person_parent_uid \"personParentUid\", "+
      " p.version_ctrl_nbr \"versionCtrlNbr\", "+
      " p.deceased_time \"deceasedTime\" " +
      " FROM person p  with (nolock) ";


}//end of NEDSSSqlQuery class

