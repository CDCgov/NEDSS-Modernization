/**
 * Name:        NEDSSSql.java
 * Description:    This class contains the SQL statements used in NEDSS.
 * Copyright:    Copyright (c) 2001
 * Company:     Computer Sciences Corporation
 * @author    Brent Chen & NEDSS Development Team
 * @version    1.0
 **/
package gov.cdc.nedss.act.sqlscript;

import java.sql.Timestamp;

import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.NEDSSConstants;

public class WumSqlQuery {

  //SQL statements for inserting into nbs_ods SQL server.
  public static final String INSERT_ACTIVITY = "INSERT INTO " +
      DataTables.ACTIVITY_TABLE +
      "(act_uid, class_cd, mood_cd) VALUES (?, ?, ?)";
  public static final String INSERT_OBS_VALUE_TXT = "INSERT INTO " +
      DataTables.OBS_VALUE_TXT_TABLE +
      "(observation_uid,  obs_value_txt_seq, data_subtype_cd, encoding_type_cd, " +
     " txt_type_cd, value_image_txt, value_txt) VALUES (?, ?, ?, ?, ?, ?, ?)";
  public static final String INSERT_OBS_VALUE_DATE = "INSERT INTO " +
      DataTables.OBS_VALUE_DATE_TABLE +
      "(observation_uid, obs_value_date_seq, duration_amt, duration_unit_cd, "+
      " from_time, to_time) VALUES (?, ?, ?, ?, ?, ?)";
  public static final String INSERT_OBS_VALUE_CODED = "INSERT INTO " +
      DataTables.OBS_VALUE_CODED_TABLE +
      "(observation_uid, code, code_system_cd, code_system_desc_txt, "+
      " code_version, display_name, original_txt, alt_cd, alt_cd_desc_txt, "+
      " alt_cd_system_cd, alt_cd_system_desc_txt, code_derived_ind) "+
      " VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";
  public static final String INSERT_OBS_VALUE_CODED_MOD =
      "INSERT INTO " + DataTables.OBS_VALUE_CODED_MOD_TABLE +
      "(observation_uid, code, code_mod_cd, code_system_cd, "+
      " code_system_desc_txt, code_version, display_name, original_txt) "+
     "  VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String INSERT_OBSERVATION_INTERP = "INSERT INTO " +
      DataTables.OBSERVATION_INTERP_TABLE +
      "(observation_uid, interpretation_cd, interpretation_desc_txt) VALUES (?, ?, ?)";
  public static final String INSERT_OBS_VALUE_NUMERIC = "INSERT INTO " +
      DataTables.OBS_VALUE_NUMERIC_TABLE +
      "(observation_uid, obs_value_numeric_seq, high_range, low_range, "+
      " comparator_cd_1, numeric_value_1, numeric_value_2, numeric_unit_cd, "+
      "separator_cd, numeric_scale_1, numeric_scale_2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String INSERT_ACT_LOCATOR_PARTICIPATION =
      "INSERT INTO " +
      DataTables.ACTIVITY_LOCATOR_PARTICIPATION_TABLE +
      "(act_uid, locator_uid, entity_uid, add_reason_cd, add_time, "+
      " add_user_id, duration_amt, duration_unit_cd,  from_time, "+
     " last_chg_reason_cd, last_chg_time, last_chg_user_id, record_status_cd, "+
     " record_status_time,  to_time, status_cd, status_time, type_cd, "+
     " type_desc_txt, user_affiliation_txt) "+
     " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  //SQL statements for updating in nbs_ods SQL server.
  public static final String UPDATE_UID = "UPDATE " +
      DataTables.UID_TABLE +
      " SET uid_value = ? WHERE uid_name = ?";
  public static final String UPDATE_CONFIRMATION_METHOD =
      "UPDATE " + DataTables.CONFIRMATION_METHOD_TABLE +
      " set confirmation_method_desc_txt = ?, confirmation_method_time = ? "+
      " WHERE public_health_case_uid = ? AND confirmation_method_cd = ?";
  public static final String UPDATE_OBS_VALUE_TXT = "UPDATE " +
      DataTables.OBS_VALUE_TXT_TABLE +
      " set data_subtype_cd = ?, encoding_type_cd = ?, txt_type_cd = ?, "+
      " value_image_txt = ?, value_txt = ? WHERE observation_uid = ? "+
      " AND obs_value_txt_seq = ?";
  public static final String UPDATE_OBS_VALUE_DATE = "UPDATE " +
      DataTables.OBS_VALUE_DATE_TABLE +
      " set duration_amt = ?, duration_unit_cd = ?, from_time = ?, "+
      " to_time = ? WHERE observation_uid = ? AND obs_value_date_seq = ?";
  public static final String UPDATE_OBS_VALUE_CODED = "UPDATE " +
      DataTables.OBS_VALUE_CODED_TABLE +
      " set code = ?, code_system_cd = ?, code_system_desc_txt = ?, "+
      " code_version = ?, display_name = ?, original_txt = ? , alt_cd = ?, "+
     " alt_cd_desc_txt = ?, alt_cd_system_cd = ?, alt_cd_system_desc_txt = ?," +
     "  code_derived_ind = ? WHERE observation_uid = ? ";
  public static final String UPDATE_OBS_VALUE_CODED_MOD =
      "UPDATE " + DataTables.OBS_VALUE_CODED_MOD_TABLE +
      " set code_system_cd = ?, code_system_desc_txt = ?, code_version = ?,"+
     " display_name = ?, original_txt = ? WHERE observation_uid = ? "+
     " AND code = ? and code_mod_cd = ? ";
  public static final String UPDATE_OBSERVATION_INTERP = "UPDATE " +
      DataTables.OBSERVATION_INTERP_TABLE +
      " SET  interpretation_cd = ?, interpretation_desc_txt = ? WHERE observation_uid = ?";

  public static final String UPDATE_OBSERVATION_REASON = "UPDATE " +
      DataTables.OBSERVATION_REASON_TABLE +
      " SET reason_desc_txt = ? WHERE observation_uid = ? AND reason_cd = ?";
  public static final String UPDATE_OBS_VALUE_NUMERIC = "UPDATE " +
      DataTables.OBS_VALUE_NUMERIC_TABLE +
      " SET high_range = ?, low_range = ?, comparator_cd_1 = ?, "+
      " numeric_value_1 = ?, numeric_value_2 = ?, numeric_unit_cd = ?, "+
      " separator_cd = ?, numeric_scale_1 = ?,numeric_scale_2 = ?  WHERE observation_uid = ? AND obs_value_numeric_seq = ?";
  public static final String UPDATE_ACT_LOCATOR_PARTICIPATION =
      "UPDATE " + DataTables.ACTIVITY_LOCATOR_PARTICIPATION_TABLE +
      " SET add_reason_cd = ?, add_time  = ?, add_user_id  = ?, "+
      " duration_amt = ?, duration_unit_cd = ?,  from_time = ?,  "+
      " last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, "+
      " record_status_cd = ?, record_status_time = ?,  to_time = ?, "+
      " status_cd = ?, status_time = ?, type_cd = ?, type_desc_txt = ?, " +
      " user_affiliation_txt = ? WHERE act_uid = ? AND entity_uid = ? "+
      " AND locator_uid = ?";

  //SQL statements for selecting from nbs_ods SQL server.
  public static final String SELECT_UID = "SELECT uid_value FROM " +
      DataTables.UID_TABLE +
      " WHERE uid_name = ?";
  public static final String SELECT_NEW_ACTIVITY_UID =
      "SELECT MAX (act_uid) FROM " + DataTables.ACTIVITY_TABLE;
  public static final String SELECT_PUBLIC_HEALTH_CASE_UID =
      "SELECT public_health_case_uid FROM " +
      DataTables.PUBLIC_HEALTH_CASE_TABLE +
      " WHERE public_health_case_uid = ?";
  public static final String SELECT_CONFIRMATION_METHOD_UID =
      "SELECT public_health_case_uid FROM " +
      DataTables.CONFIRMATION_METHOD_TABLE +
      " WHERE public_health_case_uid = ? and confirmation_method_cd=? ";
  public static final String SELECT_CONFIRMATION_METHODS =
      "SELECT public_health_case_uid \"publicHealthCaseUid\", "+
      " confirmation_method_cd \"confirmationMethodCd\", "+
      " confirmation_method_desc_txt \"confirmationMethodDescTxt\", "+
      " confirmation_method_time \"confirmationMethodTime\" FROM " +
      DataTables.CONFIRMATION_METHOD_TABLE +
      " WHERE public_health_case_uid = ?";
  public static final String REMOVE_ALL_CONFIRMATION_METHODS =
      "DELETE  from " + DataTables.CONFIRMATION_METHOD_TABLE +
      "  where public_health_case_uid = ? ";
  public static final String SELECT_OBS_VALUE_TXT_UID =
      "SELECT OBSERVATION_UID FROM " + DataTables.OBS_VALUE_TXT_TABLE +
      " WITH (NOLOCK) WHERE OBSERVATION_UID = ?";
  public static final String SELECT_OBS_VALUE_TXT =
      "SELECT observation_uid \"observationUid\", "+
      " obs_value_txt_seq \"obsValueTxtSeq\", data_subtype_cd \"dataSubtypeCd\","+
     " encoding_type_cd \"encodingTypeCd\", txt_type_cd \"txtTypeCd\", "+
     " value_image_txt \"valueImageTxt\", value_txt \"valueTxt\" FROM " +
      DataTables.OBS_VALUE_TXT_TABLE + " WITH (NOLOCK) WHERE observation_uid = ?";
  public static final String SELECT_OBS_VALUE_DATE_UID =
      "SELECT OBSERVATION_UID FROM " + DataTables.OBS_VALUE_DATE_TABLE +
      " WHERE OBSERVATION_UID = ?";
  public static final String SELECT_OBS_VALUE_DATE =
      "SELECT observation_uid \"observationUid\", "+
      " obs_value_date_seq \"obsValueDateSeq\", duration_amt \"durationAmt\", "+
     "  duration_unit_cd \"durationUnitCd\", from_time \"fromTime\", "+
     " to_time \"toTime\" FROM " +
      DataTables.OBS_VALUE_DATE_TABLE + " WITH (NOLOCK) WHERE observation_uid = ?";
  public static final String SELECT_OBS_VALUE_CODED_UID =
      "SELECT OBSERVATION_UID FROM " +
      DataTables.OBS_VALUE_CODED_TABLE + " WHERE OBSERVATION_UID = ?";
  public static final String SELECT_OBS_VALUE_CODED =
      "SELECT observation_uid \"observationUid\", code \"code\", "+
      " code_system_cd \"codeSystemCd\", "+
      "code_system_desc_txt \"codeSystemDescTxt\", " +
      " code_version \"codeVersion\", display_name \"displayName\", "+
     " original_txt \"originalTxt\", alt_cd \"altCd\", " +
     " alt_cd_desc_txt \"altCdDescTxt\", alt_cd_system_cd \"altCdSystemCd\"," +
     " alt_cd_system_desc_txt \"altCdSystemDescTxt\", " +
     " code_derived_ind \"codeDerivedInd\" FROM " +
      DataTables.OBS_VALUE_CODED_TABLE + " WITH (NOLOCK) WHERE observation_uid = ?";
  public static final String SELECT_OBS_VALUE_CODED_MOD =
      "SELECT observation_uid \"observationUid\", code \"code\", " +
      " code_mod_cd \"codeModCd\", code_system_cd \"codeSystemCd\", " +
      " code_system_desc_txt \"codeSystemDescTxt\", " +
      " code_version \"codeVersion\", display_name \"displayName\", " +
      " original_txt \"originalTxt\" FROM " +
      DataTables.OBS_VALUE_CODED_MOD_TABLE +
      " WHERE observation_uid = ? and code = ?";
  public static final String SELECT_OBSERVATION_INTERP_UID =
      "SELECT observation_uid FROM " +
      DataTables.OBSERVATION_INTERP_TABLE +
      " WHERE observation_uid = ?";
  public static final String SELECT_OBSERVATION_INTERP =
      "SELECT observation_uid \"observationUid\", " +
      " interpretation_cd \"interpretationCd\", " +
      " interpretation_desc_txt \"interpretationDescTxt\" FROM " +
      DataTables.OBSERVATION_INTERP_TABLE +
      " WHERE observation_uid = ?";
  public static final String SELECT_OBS_VALUE_NUMERIC =
      "SELECT observation_uid \"observationUid\", " +
      " obs_value_numeric_seq \"obsValueNumericSeq\", "+
      " high_range \"highRange\", low_range \"lowRange\", " +
      " comparator_cd_1 \"comparatorCd1\", numeric_value_1 \"numericValue1\","+
     " numeric_value_2 \"numericValue2\", numeric_unit_cd \"numericUnitCd\", "+
     " numeric_scale_1 \"numericScale1\", numeric_scale_2 \"numericScale2\", "+
     " separator_cd \"separatorCd\" FROM " +
      DataTables.OBS_VALUE_NUMERIC_TABLE +
      " WITH (NOLOCK) WHERE observation_uid = ?";
  public static final String SELECT_OBS_VALUE_NUMERIC_UID =
      "SELECT observation_uid FROM " +
      DataTables.OBS_VALUE_NUMERIC_TABLE +
      " WHERE observation_uid = ?";
  public static final String SELECT_ENTITY_LOCATOR_PARTICIPATION =
      "SELECT entity_uid, locator_uid FROM " +
      DataTables.ENTITY_LOCATOR_PARTICIPATION_TABLE +
      " WHERE entity_uid = ? AND locator_uid = ?";
  public static final String SELECT_ACT_LOCATOR_PARTICIPATION =
      "SELECT act_uid, entity_uid, locator_uid FROM " +
      DataTables.ACTIVITY_LOCATOR_PARTICIPATION_TABLE +
      " WHERE act_uid = ? AND entity_uid = ? AND locator_uid = ?";
  public static final String SELECT_ACT_LOCATOR_PARTICIPATIONS =
      "SELECT act_uid \"actUid\", locator_uid \"locatorUid\", "+
     " entity_uid \"entityUid\", add_reason_cd \"addReasonCd\", "+
     " add_time \"addTime\", add_user_id \"addUserId\","+
    " duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\",  " +
    " from_time \"fromTime\",  last_chg_reason_cd \"lastChgReasonCd\", " +
    " last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", "+
    " record_status_cd \"recordStatusCd\", "+
    " record_status_time \"recordStatusTime\",  to_time \"toTime\", " +
    " status_cd \"statusCd\", status_time \"statusTime\", type_cd \"typeCd\","+
   " type_desc_txt \"typeDescTxt\", "+
   " user_affiliation_txt \"userAffiliationTxt\" FROM " +
      DataTables.ACTIVITY_LOCATOR_PARTICIPATION_TABLE +
      " WHERE act_uid = ?";

  //This block of Strings added by John Park to support ActivityIdDAOImpl
  public static final String SELECT_ACTIVITY_ID_UID = "SELECT act_uid FROM " +
      DataTables.ACTIVITY_ID_TABLE +
      " WHERE act_uid = ?";
  public static final String UPDATE_ACTIVITY_ID = "UPDATE " +
      DataTables.ACTIVITY_ID_TABLE +
      " set add_reason_cd = ?, add_time  = ?, add_user_id = ?, "+
      " assigning_authority_cd = ?, assigning_authority_desc_txt = ?, "+
      " duration_amt = ?, duration_unit_cd = ?, last_chg_reason_cd = ?, "+
      " last_chg_time = ?, last_chg_user_id = ?, record_status_cd = ?, "+
      " record_status_time = ?, root_extension_txt = ?, status_cd = ?,"+
     " status_time = ?, type_cd = ?, type_desc_txt = ?, "+
     " user_affiliation_txt = ?, valid_from_time = ?, valid_to_time = ? "+
     " WHERE act_uid = ? AND act_id_seq = ?";
  public static final String INSERT_ACTIVITY_ID = "INSERT INTO " +
      DataTables.ACTIVITY_ID_TABLE +
      "(act_uid, act_id_seq, add_reason_cd, add_time, add_user_id, "+
      " assigning_authority_cd, assigning_authority_desc_txt, duration_amt,"+
     " duration_unit_cd, last_chg_reason_cd, last_chg_time, last_chg_user_id,"+
    " record_status_cd, record_status_time, root_extension_txt, "+
    " status_cd, status_time, type_cd, type_desc_txt, user_affiliation_txt, "+
   " valid_from_time, valid_to_time) "+
   " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String SELECT_ACTIVITY_IDS =
      "SELECT act_uid \"actUid\", act_id_seq \"actIdSeq\", "+
      " add_reason_cd \"addReasonCd\", add_time \"addTime\", "+
      " add_user_id \"addUserId\", "+
      " assigning_authority_cd \"assigningAuthorityCd\", "+
      " assigning_authority_desc_txt \"assigningAuthorityDescTxt\", "+
      " duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\","+
      " last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\","+
      " last_chg_user_id \"lastChgUserId\", record_status_cd \"recordStatusCd\","+
      " record_status_time \"recordStatusTime\", "+
      " root_extension_txt \"rootExtensionTxt\", status_cd \"statusCd\", "+
      " status_time \"statusTime\", type_cd \"typeCd\", "+
      " type_desc_txt \"typeDescTxt\", "+
      " user_affiliation_txt \"userAffiliationTxt\", "+
      " valid_from_time \"validFromTime\", valid_to_time \"validToTime \"FROM " +
      DataTables.ACTIVITY_ID_TABLE + " WHERE act_uid = ?";
  public static final String DELETE_ACTIVITY_IDS = "DELETE FROM " +
      DataTables.ACTIVITY_ID_TABLE +
      " WHERE act_uid = ?";

  //This block of strings added by John Park to support ObservationReasonDAOIMpl
  public static final String SELECT_OBSERVATION_UID =
      "SELECT observation_uid FROM " + DataTables.OBSERVATION_TABLE +
      " WHERE observation_uid = ?";
  public static final String INSERT_OBSERVATION_REASON = "INSERT INTO " +
      DataTables.OBSERVATION_REASON_TABLE +
      "(observation_uid, reason_cd, reason_desc_txt) VALUES (?, ?, ?)";
  public static final String SELECT_OBSERVATION_REASONS =
      "SELECT observation_uid \"observationUid\", reason_cd \"reasonCd\", "+
      " reason_desc_txt \"reasonDescTxt\" FROM " +
      DataTables.OBSERVATION_REASON_TABLE +
      " WITH (NOLOCK) WHERE observation_uid = ?";
  public static final String DELETE_OBSERVATION_REASONS =
      "DELETE FROM " + DataTables.OBSERVATION_REASON_TABLE +
      " WHERE observation_uid = ?";
  public static final String DELETE_OBSERVATION_INTERP =
      "DELETE FROM " + DataTables.OBSERVATION_INTERP_TABLE +
      " WHERE observation_uid = ?";
  public static final String DELETE_OBS_VALUE_NUMERIC = "DELETE FROM " +
      DataTables.OBS_VALUE_NUMERIC_TABLE +
      " WHERE observation_uid = ?";

  public static final String DELETE_OBS_VALUES_TXT = "DELETE FROM " +
      DataTables.OBS_VALUE_TXT_TABLE +
      " WHERE observation_uid = ?";
  public static final String DELETE_OBS_VALUES_DATE = "DELETE FROM " +
      DataTables.OBS_VALUE_DATE_TABLE +
      " WHERE observation_uid = ?";
  public static final String DELETE_OBS_VALUES_CODED = "DELETE FROM " +
      DataTables.OBS_VALUE_CODED_TABLE +
      " WHERE observation_uid = ?";
  public static final String DELETE_OBS_VALUES_CODED_MOD =
      "DELETE FROM " + DataTables.OBS_VALUE_CODED_MOD_TABLE +
      " WHERE observation_uid = ?";
  public static final String DELETE_OBSERVATION = "DELETE FROM " +
      DataTables.OBSERVATION_TABLE +
      " WHERE observation_uid = ?";

  public static final String COUNT_OBSERVATION_GENERIC = "DELETE FROM " +
      DataTables.ACT_RELATIONSHIP +
      "WHERE source_act_uid = ? and  type_cd = ? and record_status_cd = ? ";
  public static final String COUNT_OBSERVATION_LABRESULTS =
      "DELETE FROM " + DataTables.ACT_RELATIONSHIP +
      "WHERE source_act_uid = ? and  type_cd in(?,?) and record_status_cd = ? ";
  public static final String SELECT_NOTIFICATION_UID =
      "SELECT * FROM notification WHERE notification_uid = ?";

  //SQL statements added for Intervention Table by Pradeep
  public static final String SELECT_INTERVENTION =
      "SELECT intervention_uid \"interventionUid\", "+
      " activity_duration_amt \"activityDurationAmt\", "+
      " activity_duration_unit_cd \"activityDurationUnitCd\", "+
      " activity_from_time \"activityFromTime\", "+
      " activity_to_time \"activityToTime\", " +
      " add_reason_cd \"addReasonCd\", add_time \"addTime\", "+
      " add_user_id \"addUserId\", cd \"cd\", cd_desc_txt \"cdDescTxt\", "+
      " class_cd \"classCd\", confidentiality_cd \"confidentialityCd\", "+
      " confidentiality_desc_txt \"confidentialityDescTxt\", "+
      " effective_duration_amt \"effectiveDurationAmt\", " +
      "effective_duration_unit_cd \"effectiveDurationUnitCd\","+
      " effective_from_time \"effectiveFromTime\", "+
      " effective_to_time \"effectiveToTime\", "+
      " last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", "+
      " last_chg_user_id \"lastChgUserId\", method_cd \"methodCd\","+
      " method_desc_txt \"methodDescTxt\", "+
      " org_access_permis \"orgAccessPermis\", priority_cd \"priorityCd\", "+
      " priority_desc_txt \"priorityDescTxt\", "+
      " prog_area_access_permis \"progAreaAccessPermis\", "+
      " qty_amt \"qtyAmt\", qty_unit_cd \"qtyUnitCd\", reason_cd \"reasonCd\","+
      " reason_desc_txt \"reasonDescTxt\", record_status_cd \"recordStatusCd\","+
      " record_status_time \"recordStatusTime\", repeat_nbr \"repeatNbr\","+
      " status_cd \"statusCd\", status_time \"statusTime\", "+
      " target_site_cd \"targetSiteCd\", "+
      " target_site_desc_txt \"targetSiteDescTxt\", txt \"txt\", "+
      " user_affiliation_txt \"userAffiliationTxt\", local_id \"localId\", " +
      " jurisdiction_cd \"jurisdictionCd\", prog_area_cd \"progAreaCd\", "+
      " cd_system_cd \"cdSystemCd\", cd_system_desc_txt \"cdSystemDescTxt\"  FROM " +
      DataTables.INTERVENTION_TABLE + " WHERE intervention_uid = ?";
  public static final String SELECT_INTERVENTION_UID =
      "SELECT intervention_uid FROM " + DataTables.INTERVENTION_TABLE +
      " WHERE intervention_uid = ?";
  public static final String DELETE_INTERVENTION = "DELETE FROM " +
      DataTables.INTERVENTION_TABLE +
      " WHERE intervention_uid = ?";
  public static final String UPDATE_INTERVENTION = "UPDATE " +
      DataTables.INTERVENTION_TABLE +
      " set activity_duration_amt = ?, activity_duration_unit_cd = ?, "+
      " activity_from_time = ?, activity_to_time = ?, add_reason_cd = ?, "+
      " add_time = ?, add_user_id = ?, cd = ?, cd_desc_txt = ?, class_cd = ?, "+
      " confidentiality_cd = ?, confidentiality_desc_txt = ?, "+
      " effective_duration_amt = ?, effective_duration_unit_cd = ?, "+
      " effective_from_time = ?, effective_to_time = ?, "+
      " last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, "+
      " method_cd = ?, method_desc_txt = ?, org_access_permis = ?,"+
      " priority_cd = ?, priority_desc_txt = ?, prog_area_access_permis = ?, "+
      " qty_amt = ?, qty_unit_cd = ?, reason_cd = ?, reason_desc_txt = ?, "+
      " record_status_cd = ?, record_status_time = ?, repeat_nbr = ?, "+
      " status_cd = ?, status_time = ?, target_site_cd = ?, "+
      " target_site_desc_txt = ?, txt = ?, user_affiliation_txt = ?, "+
      " jurisdiction_cd = ?, prog_area_cd = ?, cd_system_cd = ?, "+
      "cd_system_desc_txt = ? WHERE  intervention_uid = ?";
  public static final String INSERT_INTERVENTION = "INSERT INTO " +
      DataTables.INTERVENTION_TABLE +
      "(intervention_uid, activity_duration_amt, activity_duration_unit_cd, "+
      " activity_from_time, activity_to_time, add_reason_cd, add_time, "+
      " add_user_id, cd, cd_desc_txt, class_cd, confidentiality_cd, "+
      " confidentiality_desc_txt, effective_duration_amt, "+
      " effective_duration_unit_cd, effective_from_time, "+
      " effective_to_time, last_chg_reason_cd, last_chg_time, "+
      " last_chg_user_id, method_cd, method_desc_txt, org_access_permis,"+
      " priority_cd, priority_desc_txt, prog_area_access_permis, qty_amt, "+
      " qty_unit_cd, reason_cd, reason_desc_txt, record_status_cd, "+
      " record_status_time, repeat_nbr, status_cd, status_time, "+
      " target_site_cd, target_site_desc_txt, txt, user_affiliation_txt, "+
      " local_id, jurisdiction_cd, prog_area_cd, "+
      " cd_system_cd, cd_system_desc_txt) "+
      " Values (?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
      " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  public static final String SELECT_SUBSTANCE_ADMINISTRATION_HIST =
      "SELECT intervention_uid InterventionUid, " +
      "dose_qty \"DoseQty\", " + "dose_qty_unit_cd \"DoseQtyUnitCd\", " +
      "form_cd \"FormCd\", " + "form_desc_txt \"FormDescTxt\", " +
      "rate_qty \"RateQty\", " + "rate_qty_unit_cd \"RateQtyUnitCd\", " +
      "route_cd \"RouteCd\", " + "route_desc_txt \"RouteDescTxt\" " +
      "FROM substance_administration_hist " +
      "WHERE intervention_uid = ? and version_ctrl_nbr = ?";

  //SQL for Patient Encounter History
  public static final String SELECT_PATIENT_ENCOUNTER_SEQ_NUMBER_HIST =
      "SELECT version_ctrl_nbr from patient_encounter_hist "+
      " where patient_encounter_uid = ?";
  public static final String INSERT_PATIENT_ENCOUNTER_HIST =
      "INSERT into patient_encounter_hist(" + "patient_encounter_uid, " +
      "version_ctrl_nbr, " + "activity_duration_amt, " +
      "activity_duration_unit_cd, " + "activity_from_time, " +
      "activity_to_time, " + "acuity_level_cd, " +
      "acuity_level_desc_txt, " + "add_reason_cd, " + "add_time, " +
      "add_user_id, " + "admission_source_cd, " +
      "admission_source_desc_txt, " + "birth_encounter_ind, " + "cd, " +
      "cd_desc_txt, " + "confidentiality_cd, " +
      "confidentiality_desc_txt, " + "effective_duration_amt, " +
      "effective_duration_unit_cd, " + "effective_from_time, " +
      "effective_to_time, " + "last_chg_reason_cd, " + "last_chg_time, " +
      "last_chg_user_id, " + "local_id, " + "org_access_permis, " +
      "priority_cd, " + "priority_desc_txt, " +
      "prog_area_access_permis, " + "record_status_cd, " +
      "record_status_time, " + "referral_source_cd, " +
      "referral_source_desc_txt, " + "repeat_nbr, " + "status_cd, " +
      "status_time, " + "txt, " +
      "user_affiliation_txt) VALUES(,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+
      " ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  public static final String SELECT_PATIENT_FOR_VACCINATION_DELETION =
      "SELECT subject_entity_uid \"SubjectEntityUid\" FROM Participation " +
      "WHERE act_uid = ? AND type_cd = ? AND subject_class_cd = ? " +
      "AND record_status_cd = ? AND act_class_cd = ?";

  public static final String SELECT_PATIENT_FOR_INVESTIGATION_DELETION =
      "SELECT subject_entity_uid \"SubjectEntityUid\" FROM Participation " +
      "WHERE act_uid = ? AND type_cd = ? AND subject_class_cd = ? " +
      "AND record_status_cd = ? AND act_class_cd = ?";
  public static final String SELECT_PATIENT_FOR_OBSERVATION_DELETION =
      "SELECT subject_entity_uid \"SubjectEntityUid\" FROM Participation " +
      "WHERE act_uid = ? AND type_cd = ? AND subject_class_cd = ? " +
      "AND record_status_cd = ? AND act_class_cd = ?";

  public static final String SELECT_PATIENT_ENCOUNTER_HIST =
      "SELECT patient_encounter_uid PatientEncounterUid, " +
      "activity_duration_amt \"ActivityDurationAmt\", " +
      "activity_duration_unit_cd \"ActivityDurationUnitCd\", " +
      "activity_from_time \"ActivityFromTime\", " +
      "activity_to_time \"ActivityToTime\", " +
      "acuity_level_cd \"AcuityLevelCd\", " +
      "acuity_level_desc_txt \"AcuityLevelDescTxt\", " +
      "add_reason_cd \"AddReasonCd\", " + "add_time \"AddTime\", " +
      "add_user_id \"AddUserId\", " +
      "admission_source_cd \"AdmissionSourceCd\", " +
      "admission_source_desc_txt \"AdmissionSourceDescTxt\", " +
      "birth_encounter_ind \"BirthEncounterInd\", " + "cd \"Cd\", " +
      "cd_desc_txt \"CdDescTxt\", " +
      "confidentiality_cd \"ConfidentialityCd\", " +
      "confidentiality_desc_txt \"ConfidentialityDescTxt\", " +
      "effective_duration_amt \"EffectiveDurationAmt\", " +
      "effective_duration_unit_cd \"EffectiveDurationUnitCd\", " +
      "effective_from_time \"EffectiveFromTime\", " +
      "effective_to_time \"EffectiveToTime\", " +
      "last_chg_reason_cd \"LastChgReasonCd\", " +
      "last_chg_time \"LastChgTime\", " +
      "last_chg_user_id \"LastChgUserId\", " + "local_id \"LocalId\", " +
      "org_access_permis \"OrgAccessPermis\", " +
      "priority_cd \"PriorityCd\", " +
      "priority_desc_txt \"PriorityDescTxt\", " +
      "prog_area_access_permis \"ProgAreaAccessPermis\", " +
      "record_status_cd \"RecordStatusCd\", " +
      "record_status_time \"RecordStatusTime\", " +
      "referral_source_cd \"ReferralSourceCd\", " +
      "referral_source_desc_txt \"ReferralSourceDescTxt\", " +
      "repeat_nbr \"RepeatNbr\", " + "status_cd \"StatusCd\", " +
      "status_time \"StatusTime\", " + "txt \"Txt\", " +
      "user_affiliation_txt \"UserAffiliationTxt\" " +
      "from patient_encounter_hist " +
      "where patient_encounter_uid = ? and version_ctrl_nbr = ?";

  //SQL for Referral History
  public static final String SELECT_REFERRAL_HIST =
      "SELECT referral_uid \"ReferralUid\", " +
      "activity_duration_amt \"ActivityDurationAmt\", " +
      "activity_duration_unit_cd \"ActivityDurationUnitCd\", " +
      "activity_from_time \"ActivityFromTime\", " +
      "activity_to_time \"ActivityToTime\", " +
      "add_reason_cd \"AddReasonCd\", " + "add_time \"AddTime\", " +
      "add_user_id \"AddUserId\", " + "cd \"Cd\", " +
      "cd_desc_txt \"CdDescTxt\", " +
      "confidentiality_cd \"ConfidentialityCd\", " +
      "confidentiality_desc_txt \"ConfidentialityDescTxt\", " +
      "effective_duration_amt \"EffectiveDurationAmt\", " +
      "effective_duration_unit_cd \"EffectiveDurationUnitCd\", " +
      "effective_from_time \"EffectiveFromTime\", " +
      "effective_to_time \"EffectiveToTime\", " +
      "last_chg_reason_cd \"LastChgReasonCd\", " +
      "last_chg_time \"LastChgTime\", " +
      "last_chg_user_id \"LastChgUserId\", " + "local_id \"LocalId\", " +
      "org_access_permis \"OrgAccessPermis\", " +
      "prog_area_access_permis \"ProgAreaAccessPermis\", " +
      "reason_txt \"ReasonTxt\", " +
      "record_status_cd \"RecordStatusCd\", " +
      "record_status_time \"RecordStatusTime\", " +
      "referral_desc_txt \"ReferralDescTxt\", " +
      "repeat_nbr \"RepeatNbr\", " + "status_cd \"StatusCd\", " +
      "status_time \"StatusTime\", " + "txt \"Txt\", " +
      "user_affiliation_txt \"UserAffiliationTxt\" " +
      "from referral_hist WHERE referral_uid = ? and version_ctrl_nbr = ?";
  public static final String INSERT_REFERRAL_HIST =
      "INSERT into referral_hist(referral_uid, " +
      "version_ctrl_nbr, " + "activity_duration_amt, " +
      "activity_duration_unit_cd, " + "activity_from_time, " +
      "activity_to_time, " + "add_reason_cd, " + "add_time, " +
      "add_user_id, " + "cd, " + "cd_desc_txt, " +
      "confidentiality_cd, " + "confidentiality_desc_txt, " +
      "effective_duration_amt, " + "effective_duration_unit_cd, " +
      "effective_from_time, " + "effective_to_time, " +
      "last_chg_reason_cd, " + "last_chg_time, " + "last_chg_user_id, " +
      "local_id, " + "org_access_permis, " + "prog_area_access_permis, " +
      "reason_txt, " + "record_status_cd, " + "record_status_time, " +
      "referral_desc_txt, " + "repeat_nbr, " + "status_cd, " +
      "status_time, " + "txt, " +
      "user_affiliation_txt) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+
      " ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  public static final String SELECT_REFERRAL_SEQ_NUMBER_HIST =
      "SELECT version_ctrl_nbr from referral_hist where referral_uid = ?";
  public static final String INSERT_SUBSTANCE_ADMIN_HIST =
      "INSERT into substance_administration_hist(intervention_uid, "+
      " version_ctrl_nbr, dose_qty, dose_qty_unit_cd, form_cd, form_desc_txt, "+
      " rate_qty, rate_qty_unit_cd, route_cd, " +
      "route_desc_txt) VALUES(?,?,?,?,?,?,?,?,?,?)";

  //SQL for Notification history table
  public static final String SELECT_NOTIFICATION_HIST =
      "SELECT notification_uid \"NotificationUid\", " +
      "version_ctrl_nbr \"VersionCtrlNbr\", " +
      "activity_duration_amt \"ActivityDurationAmt\", " +
      "activity_duration_unit_cd \"ActivityDurationUnitCd\", " +
      "activity_from_time \"ActivityFromTime\", " +
      "activity_to_time \"ActivityToTime\", " +
      "add_reason_cd \"AddReasonCd\", " + "add_time \"AddTime\", " +
      "add_user_id \"AddUserId\", " + "case_class_cd \"CaseClassCd\", " +
      "case_condition_cd \"CaseConditionCd\", " + "cd \"Cd\", " +
      "cd_desc_txt \"CdDescTxt\", " +
      "confidentiality_cd \"ConfidentialityCd, " +
      "confidentiality_desc_txt \"ConfidentialityDescTxt\", " +
      "confirmation_method_cd \"ConfirmationMethodCd\", " +
      "effective_duration_amt \"EffectiveDurationAmt\", " +
      "effective_duration_unit_cd \"EffectiveDurationUnitCd\", " +
      "effective_from_time \"EffectiveFromTime\", " +
      "effective_to_time \"EffectiveToTime\", " +
      "jurisdiction_cd \"JurisdictionCd\", " +
      "last_chg_reason_cd \"LastChgReasonCd\", " +
      "last_chg_time \"LastChgTime\", " +
      "last_chg_user_id \"LastChgUserId\", " + "local_id \"LocalId\", " +
      "message_txt \"MessageTxt\", " + "method_cd \"MethodCd\", " +
      "method_desc_txt \"MethodDescTxt\", " + "mmwr_week \"MmwrWeek\", " +
      "mmwr_year \"Mmwr_Year\", " +
      "nedss_version_nbr \"NedssVersionNbr\", " +
      "prog_area_cd \"ProgAreaCd\", " + "reason_cd \"ReasonCd\", " +
      "reason_desc_txt \"ReasonDescTxt\", " +
      "record_count \"RecordCount\", " +
      "record_status_cd \"RecordStatusCd\", " +
      "record_status_time \"RecordStatusTime\", " +
      "repeat_nbr \"RepeatNbr\", " + "rpt_sent_time \"RptSentTime\", " +
      "rpt_source_cd \"RptSourceCd, " +
      "rpt_source_type_cd \"RptSourceTypeCd\", " +
      "status_cd \"StatusCd\", " + "status_time \"StatusTime\", " +
      "txt \"Txt\", " + "shared_ind \"sharedInd\", " +
      "program_jurisdiction_oid \"programJurisdictionOid\", " +
      "user_affiliation_txt \"UserAffiliationTxt\" " +
      "FROM notification_hist WHERE notification_uid = ? and version_ctrl_nbr = ?";
  public static final String INSERT_NOTIFICATION_HIST =
      "INSERT into notification_hist(" + "notification_uid, " +
      "version_ctrl_nbr, " + "activity_duration_amt, " +
      "activity_duration_unit_cd, " + "activity_from_time, " +
      "activity_to_time, " + "add_reason_cd, " + "add_time, " +
      "add_user_id, " + "case_class_cd, " + "case_condition_cd, " +
      "cd, " + "cd_desc_txt, " + "confidentiality_cd, " +
      "confidentiality_desc_txt, " + "confirmation_method_cd, " +
      "effective_duration_amt, " + "effective_duration_unit_cd, " +
      "effective_from_time, " + "effective_to_time, " +
      "jurisdiction_cd, " + "last_chg_reason_cd, " + "last_chg_time, " +
      "last_chg_user_id, " + "local_id, " + "message_txt, " +
      "method_cd, " + "method_desc_txt, " + "mmwr_week, " +
      "mmwr_year, " + "nedss_version_nbr, " + "prog_area_cd, " +
      "reason_cd, " + "reason_desc_txt, " + "record_count, " +
      "record_status_cd, " + "record_status_time, " + "repeat_nbr, " +
      "rpt_sent_time, " + "rpt_source_cd, " + "rpt_source_type_cd, " +
      "status_cd, " + "status_time, " + "txt, " +
      "user_affiliation_txt, " + "shared_ind, " +
      "program_jurisdiction_oid) " +
      "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
      "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  public static final String SELECT_NOTIFICATIONS_SEQ_NUMBER_HIST =
      "SELECT version_ctrl_nbr from notification_hist where notification_uid = ?";

  //SQL for Intervention and Procedure History tables
  public static final String SELECT_PROCEDURES_1_HIST =
      "SELECT intervention_uid \"InterventionUid\", " +
      "version_ctrl_nbr \"versionCtrlNbr\", " +
      "approach_site_cd \"ApproachSiteCd\", " +
      "approach_site_desc_txt \"ApproachSiteDescTxt\" " +
      "FROM procedure1_hist " +
      "WHERE intervention_uid = ? and version_ctrl_nbr = ?";
  public static final String INSERT_PROCEDURE1_HIST =
      "INSERT into procedure1_hist(" +
      "intervention_uid, version_ctrl_nbr, approach_site_cd, approach_site_desc_txt) " +
      "VALUES(?,?,?,?)";
  public static final String SELECT_INTERVENTION_SEQ_NUMBER_HIST =
      "SELECT version_ctrl_nbr from intervention_hist where intervention_uid = ?";
  public static final String INSERT_INTERVENTION_HIST =
      "INSERT into intervention_hist(intervention_uid, " +
      "version_ctrl_nbr, " + "activity_duration_amt, " +
      "activity_duration_unit_cd, " + "activity_from_time, " +
      "activity_to_time, " + "add_reason_cd, " + "add_time, " +
      "add_user_id, " + "cd, " + "cd_desc_txt, " + "cd_system_cd, " +
      "cd_system_desc_txt, " + "class_cd, " + "confidentiality_cd, " +
      "confidentiality_desc_txt, " + "effective_duration_amt, " +
      "effective_duration_unit_cd, " + "effective_from_time, " +
      "effective_to_time, " + "jurisdiction_cd, " +
      "last_chg_reason_cd, " + "last_chg_time, " + "last_chg_user_id, " +
      "local_id, " + "method_cd, " + "method_desc_txt, " +
      "org_access_permis, " + "priority_cd, " + "priority_desc_txt, " +
      "prog_area_access_permis, " + "prog_area_cd, " + "qty_amt, " +
      "qty_unit_cd, " + "reason_cd, " + "reason_desc_txt, " +
      "record_status_cd, " + "record_status_time, " + "repeat_nbr, " +
      "status_cd, " + "status_time, " + "target_site_cd, " +
      "target_site_desc_txt, " + "txt, " +
      "user_affiliation_txt) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
      "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  public static final String SELECT_INTERVENTION_HIST =
      "SELECT intervention_uid \"InterventionUid\", " +
      "version_ctrl_nbr \"versionCtrlNbr\", " +
      "activity_duration_amt \"ActivityDurationAmt\", " +
      "activity_duration_unit_cd \"ActivityDurationUnitCd\", " +
      "activity_from_time \"ActivityFromTime\", " +
      "activity_to_time \"ActivityToTime\", " +
      "add_reason_cd \"AddReasonCd\", " + "add_time \"AddTime\", " +
      "add_user_id \"AddUserId\", " + "cd \"Cd\", " +
      "cd_desc_txt \"CdDescTxt\", " + "cd_system_cd \"CdSystemCd, " +
      "cd_system_desc_txt \"CdSystemDescTxt\", " +
      "class_cd \"ClassCd\", " +
      "confidentiality_cd \"ConfidentialityCd\", " +
      "confidentiality_desc_txt \"ConfidentialityDescTxt\", " +
      "effective_duration_amt \"EffectiveDurationAmt\", " +
      "effective_duration_unit_cd \"EffectiveDurationUnitCd\", " +
      "effective_from_time \"EffectiveFromTime\", " +
      "effective_to_time \"EffectiveToTime\", " +
      "jurisdiction_cd \"JurisdictionCd\", " +
      "last_chg_reason_cd \"LastChgReasonCd\", " +
      "last_chg_time \"LastChgTime\", " +
      "last_chg_user_id \"LastChgUserId\", " + "local_id \"LocalId\", " +
      "method_cd \"MethodCd\", " + "method_desc_txt \"MethodDescTxt\", " +
      "org_access_permis \"OrgAccessPermis\", " +
      "priority_cd \"PriorityCd\", " +
      "priority_desc_txt \"PriorityDescTxt\", " +
      "prog_area_access_permis \"ProgAreaAccessPermis\", " +
      "prog_area_cd \"ProgAreaCd\", " + "qty_amt \"QtyAmt\", " +
      "qty_unit_cd \"QtyUnitCd\", " + "reason_cd \"ReasonCd\", " +
      "reason_desc_txt \"ReasonDescTxt\", " +
      "record_status_cd \"RecordStatusCd\", " +
      "record_status_time \"RecordStatusTime\", " +
      "repeat_nbr \"RepeatNbr\", " + "status_cd \"StatusCd\", " +
      "status_time \"StatusTime\", " +
      "target_site_cd \"TargetSiteCd\", " +
      "target_site_desc_txt \"TargetSiteDescTxt\", " + "txt \"Txt\", " +
      "user_affiliation_txt \"UserAffiliationTxt\" " +
      "FROM intervention_hist " +
      "WHERE intervention_uid = ? and version_ctrl_nbr = ?";

  //SQL statements added for Procedure1 Table by Pradeep
  public static final String SELECT_PROCEDURE1 =
      "SELECT intervention_uid \"interventionUid\", "+
     "  approach_site_cd \"approachSiteCd\", "+
     " approach_site_desc_txt \"approachSiteDescTxt\" FROM " +
      DataTables.PROCEDURE1_TABLE + " WHERE intervention_uid = ?";
  public static final String SELECT_PROCEDURE1_UID =
      "SELECT intervention_uid FROM " + DataTables.PROCEDURE1_TABLE +
      " WHERE intervention_uid = ?";
  public static final String DELETE_PROCEDURE1 = "DELETE FROM " +
      DataTables.PROCEDURE1_TABLE +
      " WHERE intervention_uid = ?";
  public static final String UPDATE_PROCEDURE1 = "UPDATE " +
      DataTables.PROCEDURE1_TABLE +
      " set approach_site_cd = ?, approach_site_desc_txt  = ? "+
      " WHERE  intervention_uid = ?";
  public static final String INSERT_PROCEDURE1 = "INSERT INTO " +
      DataTables.PROCEDURE1_TABLE +
      "(intervention_uid, approach_site_cd, approach_site_desc_txt) " +
      " Values (?, ?, ?)";

  //SQL statements added for SubstanceAdministration Table by Pradeep
  public static final String SELECT_SUBSTANCE_ADMINISTRATION_UID =
      "SELECT intervention_uid FROM  " +
      DataTables.SUBSTANCE_ADMINISTRATION_TABLE +
      " WHERE intervention_uid = ?";
  public static final String INSERT_SUBSTANCE_ADMINISTRATION =
      "INSERT INTO " + DataTables.SUBSTANCE_ADMINISTRATION_TABLE +
      "(intervention_uid, dose_qty, dose_qty_unit_cd, form_cd, form_desc_txt,"+
     " rate_qty, rate_qty_unit_cd, route_cd, route_desc_txt) " +
      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String UPDATE_SUBSTANCE_ADMINISTRATION =
      "UPDATE " + DataTables.SUBSTANCE_ADMINISTRATION_TABLE +
      " SET dose_qty = ?, dose_qty_unit_cd = ?, form_cd = ?, "+
      " form_desc_txt = ?, rate_qty = ?, rate_qty_unit_cd = ?, route_cd = ?, "+
      " route_desc_txt = ? WHERE intervention_uid = ?";
  public static final String SELECT_SUBSTANCE_ADMINISTRATION =
      "SELECT intervention_uid \"interventionUid\", dose_qty \"doseQty\","+
      " dose_qty_unit_cd \"doseQtyUnitCd\", form_cd \"formCd\","+
      " form_desc_txt \"formDescTxt\", rate_qty \"rateQty\", "+
      " rate_qty_unit_cd \"rateQtyUnitCd\", route_cd \"routeCd\","+
      " route_desc_txt \"routeDescTxt\" FROM " +
      DataTables.SUBSTANCE_ADMINISTRATION_TABLE +
      " WHERE intervention_uid = ?";
  public static final String DELETE_SUBSTANCE_ADMINISTRATION =
      "DELETE FROM " + DataTables.SUBSTANCE_ADMINISTRATION_TABLE +
      " WHERE intervention_uid = ?";

  //SQL statements added for WorkupProxyEJB by Rick
  public static final String SELECT_WORKUP_SQL =
      "select Public_health_case.investigation_status_cd  investigationStatusCd , " +
      "code1.code_short_desc_txt  investigationStatusDescTxt, " +
      "Public_health_case.local_id  localId , " +
      "Public_health_case.public_health_case_uid publicHealthCaseUid, " +
      "Public_health_case.activity_from_time  activityFromTime , " +
      "Public_health_case.cd  cd , " +
      "cond.condition_short_nm conditionCodeText, " +
      "Public_health_case.case_class_cd  caseClassCd , " +
      "code2.code_short_desc_txt caseClassCodeTxt, " +
      "Public_health_case.record_status_cd recordStatusCd, " +
      "Public_health_case.jurisdiction_cd  jurisdictionCd, " +
      "jcd.code_short_desc_txt   jurisdictionDescTxt, " +
      "Public_health_case.status_cd  statusCd " + "from Person p with (nolock) " +
      "LEFT OUTER JOIN  participation par with (nolock)  ON P.person_uid = par.subject_entity_uid " +
      "LEFT OUTER JOIN Public_health_case Public_health_case with (nolock) "+
      "ON par.act_uid = Public_health_case.Public_health_case_uid " +
      "and Public_health_case.investigation_status_cd IN ( 'O', 'C') " +
      "and Public_health_case.record_status_cd <> '" +
      NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE + "' " +
      "and par.act_class_cd ='CASE' " +
      "and par.record_status_cd ='ACTIVE' " + "and par.type_cd = '" +
      NEDSSConstants.PHC_PATIENT + "' " +
      "and par.subject_class_cd ='PSN' " + "LEFT OUTER JOIN " +
      NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1 with (nolock) ON " +
      "code1.code_set_nm = 'PHC_IN_STS' "+
      " and Public_health_case.investigation_status_cd = code1.code " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..Condition_code cond with (nolock) ON cond.condition_cd = Public_health_case.cd " +
      " LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code2 with (nolock) ON " +
      "code2.code_set_nm = 'PHC_CLASS' "+
     " and  Public_health_case.case_class_cd = code2.code " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..Jurisdiction_code jcd with (nolock) ON Public_health_case.jurisdiction_cd = jcd.code " +
      "where person_uid= ?";

  //SQL statement added by shailender for PiT, collection of uids
  public static final String SELECT_WORKUP_SQL_IN =
      "select Public_health_case.investigation_status_cd  investigationStatusCd ,"+
      "code1.code_short_desc_txt  investigationStatusDescTxt," +
      "Public_health_case.local_id  localId ," +
      "Public_health_case.public_health_case_uid publicHealthCaseUid," +
      "Public_health_case.activity_from_time  activityFromTime ," +
      "Public_health_case.add_time addTime, " +
      "Public_health_case.cd  cd ," +
      "cond.condition_short_nm conditionCodeText," +
      "Public_health_case.case_class_cd  caseClassCd , " +
      "code2.code_short_desc_txt caseClassCodeTxt, " +
      "Public_health_case.record_status_cd recordStatusCd, " +
      "Public_health_case.jurisdiction_cd  jurisdictionCd, " +
      "Public_health_case.prog_area_cd  progAreaCd, " +
      "jcd.code_short_desc_txt   jurisdictionDescTxt, " +
      "Public_health_case.status_cd  statusCd, " +
      "Public_health_case.curr_process_state_cd currProcessStateCd, " +
      "noti.record_status_cd notifRecordStatusCd, "+
      "person_name.first_nm investigatorFirstName, "+
      "person_name.last_nm investigatorLastName, "+
      "par.subject_entity_uid patientRevisionUid, "+
      "Public_health_case.coinfection_id coinfectionId, "+
      "p2.PERSON_PARENT_UID \"investigatorMPRUid\", "+
      "cm.fld_foll_up_dispo disposition, "+
      "cm.epi_link_id epiLinkId, "+
      "cm.pat_intv_status_cd patIntvStatusCd "+
      "from Person p with (nolock) " +
      "inner JOIN  participation par with (nolock) "  +
      "ON P.person_uid = par.subject_entity_uid " +
      "inner JOIN Public_health_case Public_health_case with (nolock) " +
      "ON par.act_uid = Public_health_case.Public_health_case_uid " +
      "and Public_health_case.investigation_status_cd IN ( 'O', 'C') "  +
      "and Public_health_case.record_status_cd <> 'LOG_DEL' " +
      "and par.act_class_cd ='CASE' " +
      "and par.record_status_cd ='ACTIVE' " +
      "and par.type_cd = 'SubjOfPHC' " +
      "and par.subject_class_cd ='PSN' " +
      "inner JOIN nbs_srte..code_value_general code1 with (nolock) "  +
      "ON code1.code_set_nm = 'PHC_IN_STS' "  +
      "and Public_health_case.investigation_status_cd = code1.code "  +
      "inner JOIN nbs_srte..Condition_code cond with (nolock) " +
      "ON cond.condition_cd = Public_health_case.cd "  +
      " OUTER APPLY (SELECT source_act_uid, " + 
      "           target_act_uid, " + 
      "           type_cd" + 
      "    FROM act_relationship ar WITH(NOLOCK)," + 
      "         notification noti WITH(NOLOCK)" + 
      "    WHERE ar.source_act_uid = noti.notification_uid" + 
      "          AND noti.cd = 'NOTF'" + 
      "		  and ar.target_act_uid = Public_Health_Case.public_health_case_uid" + 
      "		  and ar.type_cd = 'Notification'" + 
      ") ar "+
      "LEFT OUTER JOIN notification noti with (nolock) on ar.source_act_uid = noti.notification_uid "+
      "and noti.cd='NOTF' "+
      "LEFT OUTER JOIN (select subject_entity_uid,act_uid from participation with (nolock) where type_cd='InvestgrOfPHC') par1 on par1.act_uid = Public_health_case.public_health_case_uid "+
      "LEFT OUTER JOIN person_name with (nolock) on par1.subject_entity_uid = person_name.person_uid "+
      "LEFT OUTER JOIN nbs_srte..code_value_general code2 with (nolock) " +
      "ON code2.code_set_nm = 'PHC_CLASS' "  +
      "and  Public_health_case.case_class_cd = code2.code " +
      "LEFT OUTER JOIN nbs_srte..Jurisdiction_code jcd with (nolock) " +
      "ON Public_health_case.jurisdiction_cd = jcd.code "  +
      "LEFT OUTER JOIN Person p2 with (nolock) on p2.person_uid  =  person_name.person_uid "+
      "LEFT OUTER JOIN case_management cm with (nolock) " +
      "ON Public_health_case.public_health_case_uid = cm.public_health_case_uid "  +
      "where p.person_parent_uid= ";

 
  public static final String SELECT_NOTIFICATION_SQL =
      "select Notification.notification_uid NotificationUid, " +
      " Notification.add_time AddTime,Notification.rpt_sent_time RptSentTime," +
      " Public_health_case.cd Cd, code1.condition_desc_txt CdTxt, " +
      " Public_health_case.case_class_cd CaseClassCd, " +
      " code2.code_short_desc_txt CaseClassCdTxt, " +
      " code1.nnd_ind \"nndInd\" , " +
      " Notification.local_id LocalId, Notification.txt Txt" +
      " from Public_health_case Public_health_case, act_relationship ar, " +
      " notification Notification, " +
      " nbs_srte..Condition_code code1, " + 
      " nbs_srte..code_value_general code2 " + " where " +
      " Public_health_case.Public_health_case_uid = ar.target_act_uid " +
      " and ar.type_cd = 'Notification' " + 
      " and ar.status_cd = 'A' " +
      " and Notification.notification_uid = ar.source_act_uid" +
      " and code1.condition_codeset_nm = 'PHC_TYPE' "+
      " and Public_health_case.cd = code1.condition_cd " +
      " and code2.code_set_nm = 'PHC_CLASS' "+
      " and Public_health_case.case_class_cd = code2.code " +
      " and Public_health_case.record_status_cd <> 'LOG_DEL' " +
      " and Public_health_case.public_health_case_uid = ?";

  //---------------------------------
  public static final String SELECT_NOTIFICATION_FOR_INVESTIGATION_SQL =
      "select Notification.notification_uid NotificationUid,  Notification.cd cdNotif, " +
      "Notification.add_time AddTime,Notification.rpt_sent_time RptSentTime, " +
      "Notification.record_status_time RecordStatusTime, " +
      " Notification.case_condition_cd Cd, " +
      " Notification.jurisdiction_cd jurisdictionCd , "+
      " Notification.program_jurisdiction_oid programJurisdictionOid , "+
      " Public_health_case.case_class_cd ," +
      " Notification.auto_resend_ind AutoResendInd, "+
      " Notification.case_class_cd CaseClassCd, "+
      " Notification.local_id LocalId, Notification.txt Txt, "+
      " Notification.record_status_cd RecordStatusCd, 'F' isHistory ," +
      " cc.nnd_ind \"nndInd\" , " +
      "exportReceiving.receiving_system_nm recipient " +
      " from Public_health_case Public_health_case  with (nolock) , act_relationship ar  with (nolock) , nbs_srte..condition_code cc  with (nolock) , "+
      " notification Notification   with (nolock) " +
      "LEFT JOIN Export_receiving_facility exportReceiving  with (nolock) " +
      " ON exportReceiving.export_receiving_facility_uid = Notification.export_receiving_facility_uid " +
      " where " +
      " Public_health_case.Public_health_case_uid = ar.target_act_uid " +
      " and ar.type_cd = '" +
      NEDSSConstants.ACT106_TYP_CD +
      "' and ar.target_class_cd = 'CASE' " +
      " and Notification.notification_uid = ar.source_act_uid" +
      " and Notification.case_condition_cd = cc.condition_cd " +
      " and Public_health_case.record_status_cd <> '" +
      NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE + "'" +
      " and Public_health_case.public_health_case_uid = ?";

  public static final String SELECT_NOTIFICATION_HIST_FOR_INVESTIGATION_SQL =
      "select notHist.notification_uid NotificationUid,  Notification.cd cdNotif,"+
      " notHist.add_time AddTime, notHist.rpt_sent_time RptSentTime, " +
      " notHist.record_status_time RecordStatusTime, " +
      " notHist.jurisdiction_cd jurisdictionCd, "+
      " notHist.program_jurisdiction_oid programJurisdictionOid, "+
      " notHist.case_condition_cd Cd, notHist.version_ctrl_nbr VersionCtrlNbr,"+
      " Public_health_case.case_class_cd ," +
      " notHist.case_class_cd CaseClassCd, "+
      " notHist.local_id LocalId, notHist.txt Txt, "+
      " notHist.record_status_cd RecordStatusCd, 'T' isHistory ," +
      " cc.nnd_ind \"nndInd\" , " +
      " exportReceiving.receiving_system_nm recipient " +
      " from Public_health_case Public_health_case  with (nolock) , act_relationship ar  with (nolock) , nbs_srte..condition_code cc  with (nolock) , "+
      " notification Notification  with (nolock) , notification_hist notHist  with (nolock)  " +
      "LEFT JOIN Export_receiving_facility exportReceiving  with (nolock) " +
      " ON exportReceiving.export_receiving_facility_uid = notHist.export_receiving_facility_uid " +
      " where " +
      " Public_health_case.Public_health_case_uid = ar.target_act_uid " +
      " and ar.type_cd = '" +
      NEDSSConstants.ACT106_TYP_CD +
      "' and ar.target_class_cd = 'CASE' " +
      " and Notification.notification_uid = ar.source_act_uid " +
      " and Notification.case_condition_cd = cc.condition_cd " +
      " and notHist.notification_uid = Notification.notification_uid " +
      " and Public_health_case.record_status_cd <> '" +
      NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE + "'" +
      " and Public_health_case.public_health_case_uid = ?";

  public static final String SELECT_NOTIFICATION_FOR_INVESTIGATION_SQL1 =
      " select Notification.notification_uid NotificationUid,  Notification.cd cdNotif,"+
     "  Notification.add_time AddTime, " +
      " Notification.rpt_sent_time RptSentTime, " +
      " Notification.record_status_time \"recordStatusTime\", " +
      " Public_health_case.case_class_cd, " +
      " Notification.case_condition_cd \"Cd\", "+
      " Notification.jurisdiction_cd \"jurisdictionCd\" , "+
      " Notification.program_jurisdiction_oid \"programJurisdictionOid\" , "+
      " Notification.auto_resend_ind AutoResendInd, "+
      " Notification.case_class_cd CaseClassCd, "+
      " Notification.local_id LocalId, Notification.txt Txt, "+
      " Notification.record_status_cd RecordStatusCd, 'F' isHistory  ," +
      " cc.nnd_ind \"nndInd\" , " +
      " exportReceiving.receiving_system_nm recipient " +
      " from Public_health_case Public_health_case  with (nolock) "+
      " inner join  act_relationship ar  with (nolock)  on " +
      " Public_health_case.Public_health_case_uid = ar.target_act_uid  " +
      " and ar.type_cd = '" + NEDSSConstants.ACT106_TYP_CD + "'" +
      " and ar.target_class_cd = 'CASE'  " +
      " inner join notification Notification  with (nolock) on " +
      " Notification.notification_uid = ar.source_act_uid " +
      " inner join nbs_srte..condition_code cc  with (nolock) on " +
	  " Notification.case_condition_cd = cc.condition_cd " +
      " LEFT JOIN Export_receiving_facility exportReceiving  with (nolock) " +
      " ON exportReceiving.export_receiving_facility_uid = Notification.export_receiving_facility_uid " +
      " where Public_health_case.record_status_cd <> '" +
      NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE + "'" +
      " and Public_health_case.public_health_case_uid = ?";

  public static final String SELECT_NOTIFICATION_HIST_FOR_INVESTIGATION_SQL1 =
      " select notHist.notification_uid NotificationUid,  notHist.cd cdNotif, notHist.add_time AddTime, " +
      " notHist.rpt_sent_time RptSentTime, " +
      " notHist.record_status_time \"recordStatusTime\", " +
      " Public_health_case.case_class_cd, " +
      " notHist.case_condition_cd \"Cd\", " +
      " notHist.jurisdiction_cd \"jurisdictionCd\" , "+
      " notHist.program_jurisdiction_oid \"programJurisdictionOid\" , "+
      " notHist.case_class_cd CaseClassCd, "+
      " notHist.version_ctrl_nbr VersionCtrlNbr, "+
      " notHist.local_id LocalId, notHist.txt Txt, "+
      " notHist.record_status_cd RecordStatusCd, 'T' isHistory ," +
      " cc.nnd_ind \"nndInd\" , " +
      " exportReceiving.receiving_system_nm recipient " +
      " from Public_health_case Public_health_case  with (nolock) "+
      " inner join  act_relationship ar  with (nolock) on " +
      " Public_health_case.Public_health_case_uid = ar.target_act_uid  " +
      " and ar.type_cd = '" + NEDSSConstants.ACT106_TYP_CD + "'" +
      " and ar.target_class_cd = 'CASE'  " +
      " inner join notification Notification  with (nolock) on " +
      " Notification.notification_uid = ar.source_act_uid " +
      " inner join nbs_srte..condition_code cc  with (nolock) on " +
	  " Notification.case_condition_cd = cc.condition_cd " +
      " inner join notification_hist notHist  with (nolock) on" +
      " notHist.notification_uid = Notification.notification_uid " +
      " LEFT JOIN Export_receiving_facility exportReceiving  with (nolock) " +
      " ON exportReceiving.export_receiving_facility_uid = notHist.export_receiving_facility_uid " +
      " where Public_health_case.record_status_cd <> '" +
      NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE + "'" +
      " and Public_health_case.public_health_case_uid = ?";

  public static final String SELECT_NOTIFICATION_AUTO_RESEND_SQL = " "+
      "select n.notification_uid \"NotificationUid\" , " +
      "p.cd Cd, " +
      "p.case_class_cd CaseClassCd, " +
      "p.jurisdiction_cd JurisdictionCd, " +
      "p.prog_area_cd ProgAreaCd, " +
      "p.shared_ind SharedInd, " +
      "n.auto_resend_ind AutoResendInd " +
      "from   notification n  with (nolock) , " +
      "act_relationship a  with (nolock) , " +
      "act_relationship a1  with (nolock) , " +
      "public_health_case p  with (nolock) " +
      "where  n.auto_resend_ind = 'T' and " +
      "n.notification_uid = a.source_act_uid and " +
      "p.public_health_case_uid = a.target_act_uid and " +
      "a.target_class_cd = 'CASE' and " +
      "a.source_class_cd = 'NOTF' and " +
      "a.record_status_cd = 'ACTIVE' and  " +
      "a.target_act_uid = a1.target_act_uid and " +
      "a1.record_status_cd = 'ACTIVE' and " +
      "a1.TARGET_CLASS_CD = 'CASE' and " +
      "a1.source_class_cd = ? and " +
      "a1.type_cd = ? and " +
      "a1.source_act_uid = ? ";

  
  public static final String SELECT_CUSTOM_QUEUES_SQL = "SELECT queue_name queueName, source_table sourceTable, query_string_part_1 queryStringPart1, query_string_part_2 queryStringPart2, description description, list_of_users listOfUsers, record_status_cd recordStatusCd, record_status_time recordStatusTime, last_chg_time lastChgTime, last_chg_user_id lastChgUserId, add_time addTime, add_user_id addUserId, search_criteria_desc searchCriteriaDesc, search_criteria_cd searchCriteriaCd  FROM Custom_queues WHERE record_status_cd = 'ACTIVE' AND (list_of_users = 'ALL'";
  
  //-----------------------------
  public static final String SELECT_MY_INVESTIGATIONS_SQL =
      "select " +
      "Condition_code.investigation_form_cd investigationFormCode,person.last_nm investigatorLastName, person.first_nm investigatorFirstName, person.local_id investigatorLocalId, "+
      "Public_health_case.investigation_status_cd  investigationStatusCd , " +
      "code1.code_short_desc_txt  investigationStatusDescTxt, " +
      "Public_health_case.local_id  localId , " +
      "Public_health_case.activity_from_time  activityFromTime , " +
      "Public_health_case.cd  cd , " +
      "Public_health_case.cd_desc_txt conditionCodeText, " +
      "Public_health_case.case_class_cd  caseClassCd , " +
      "code3.code_short_desc_txt caseClassCodeTxt, " +
      "Public_health_case.jurisdiction_cd  jurisdictionCd, " +
      "ISNULL(person1.last_nm,'No Last') patientLastName, ISNULL(person1.first_nm,'No First') patientFirstName, person1.person_parent_uid MPRUid,person1.birth_time birthTime, person1.curr_sex_cd currSexCd, person1.local_id personLocalId, " +
       "code4.code_desc_txt   jurisdictionDescTxt, " +
      "Public_health_case.public_health_case_uid publicHealthCaseUid, " +
      "Public_health_case.status_cd  statusCd, " +
      "Public_health_case.record_status_cd recordStatusCd, " +
      "Notification.record_status_cd  notifRecordStatusCd " +
      "from Public_health_case " +
      DataTables.PUBLIC_HEALTH_CASE_TABLE +
      " WITH(NOLOCK) INNER JOIN  participation PAR116 WITH(NOLOCK) ON PAR116.type_cd = 'SubjOfPHC' " + 
      " and Public_health_case.Public_health_case_uid = PAR116.act_uid INNER JOIN person person1 WITH(NOLOCK) ON person1.person_uid= PAR116.subject_entity_uid " + 
      " LEFT OUTER JOIN   participation PAR121 WITH(NOLOCK) ON "   +
      " PAR121.type_cd = 'InvestgrOfPHC' " +
      " and Public_health_case.Public_health_case_uid = PAR121.act_uid  "+
      " LEFT OUTER JOIN person person WITH(NOLOCK) ON " +
      " person.person_uid= PAR121.subject_entity_uid "  +
      " OUTER apply (SELECT source_act_uid, target_act_uid, type_cd FROM act_relationship ar WITH(NOLOCK), notification noti WITH(NOLOCK) WHERE ar.source_act_uid = noti.notification_uid "+
      " AND noti.cd = 'NOTF' and ar.target_act_uid = Public_Health_Case.public_health_case_uid and ar.type_cd = 'Notification' )  ARNOTF " +
      " LEFT OUTER JOIN Notification AS Notification WITH(NOLOCK) ON Notification.Notification_uid = ARNOTF.source_act_uid " +
      " LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1 ON " +
      "code1.code_set_nm = 'PHC_IN_STS' "+
      " and Public_health_case.investigation_status_cd = code1.code " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code3 ON " +
      "code3.code_set_nm = 'PHC_CLASS'  "+
      " and  Public_health_case.case_class_cd = code3.code " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..Jurisdiction_code code4 ON " +
      " Public_health_case.jurisdiction_cd = code4.code " +
      
      " inner join "+NEDSSConstants.SYSTEM_REFERENCE_TABLE +".dbo.Condition_code Condition_code WITH(NOLOCK) ON Public_health_case.cd=Condition_code.Condition_cd ";

  //-------------------------------------------------------------------------
  
  public static final String SELECT_LAB_REPORTS_EVENT_SEARCH_SQL=
		  " SELECT  obs.observation_uid \"ObservationUid\", "
	   		       + " obs.local_id \"LocalId\", "
	   		       + " obs.shared_ind \"sharedInd\", "
	   		          + " OBS.electronic_ind \"electronicInd\", "
	   		          + " OBS.add_user_id \"addUserId\", "
	                  + " obs.jurisdiction_cd \"JurisdictionCd\", "
	                  + " obs.prog_area_cd \"ProgramArea\", "
	                  + " obs.rpt_to_state_time \"DateReceived\", "
	                  + " obs.version_ctrl_nbr \"versionCtrlNbr\", "
	                  + " obs.ctrl_cd_display_form \"ctrlCdDisplayForm\", "
	                  + " obs.status_cd \"Status\", "
	                  + " obs1.cd_desc_txt \"resultedTest\","
	                  + " obs1.cd \"resultedTestCd\", "
	                  + " obs1.cd_system_cd \"resultedTestCdSystemCd\", "
	                  + " obs.record_status_cd \"recordStatusCd\",  "
					+ " ISNULL(pnm.last_nm,'No Last') \"lastName\","
					+ " ISNULL(pnm.first_nm,'No First') \"firstName\","
					+ " p.curr_sex_cd \"currentSex\", "
					+ " p.local_id \"personLocalID\",  " 
					+ " p.birth_time \"birthTime\",  " 
					+ " p.person_parent_uid \"MPRUid\", " 
					+ " p.age_reported \"ageReported\", " 
					+ " p.age_reported_unit_cd \"ageUnit\" " 
					+ " FROM observation obs with(nolock) "
					+ " inner join act_relationship act with(nolock)  on obs.observation_uid = act.target_act_uid "
					 + " inner join observation obs1 with(nolock)  on act.source_act_uid = obs1.observation_uid "
					 + " inner join Participation part with(nolock)  on part.act_uid = obs.observation_uid "
					 + " inner join person p with(nolock)  on p.person_uid = part.subject_entity_uid "
					 + " inner join person_name pnm  with(nolock)  on pnm.person_uid = p.person_uid "
	                  + " AND obs1.obs_domain_cd_st_1 = 'Result'"
	                  + " AND (act.source_act_uid = obs1.observation_uid)"
	                  + " AND (act.target_class_cd = 'OBS')"
	                  + " AND (act.type_cd = 'COMP') "
	                  + " AND (act.source_class_cd = 'OBS')"
	                  + " AND (act.record_status_cd = 'ACTIVE')"
	                  + " and (obs.record_status_cd in ('PROCESSED', 'UNPROCESSED'))"  
	                  + " and obs.ctrl_cd_display_form = 'LabReport' ";
  
  public static final String SELECT_LAB_REPORTS_EVENT_SEARCH_SQL_BY_PROCESS_STATE=
		  " SELECT  obs.observation_uid \"ObservationUid\", "
   		       + " obs.local_id \"LocalId\", "
   		       + " obs.shared_ind \"sharedInd\", "
   		          + " OBS.electronic_ind \"electronicInd\", "
   		          + " OBS.add_user_id \"addUserId\", "
                  + " obs.jurisdiction_cd \"JurisdictionCd\", "
                  + " obs.prog_area_cd \"ProgramArea\", "
                  + " obs.rpt_to_state_time \"DateReceived\", "
                  + " obs.version_ctrl_nbr \"versionCtrlNbr\", "
                  + " obs.ctrl_cd_display_form \"ctrlCdDisplayForm\", "
                  + " obs.status_cd \"Status\", "
                  + " obs1.cd_desc_txt \"resultedTest\","
                  + " obs1.cd \"resultedTestCd\", "
                  + " obs1.cd_system_cd \"resultedTestCdSystemCd\", "
                  + " obs.record_status_cd \"recordStatusCd\",  "
				+ " ISNULL(pnm.last_nm,'No Last') \"lastName\","
				+ " ISNULL(pnm.first_nm,'No First') \"firstName\","
				+ " p.curr_sex_cd \"currentSex\", "
				+ " p.birth_time \"birthTime\",  " 
				+ " p.local_id \"personLocalID\",  " 
				+ " p.person_parent_uid \"MPRUid\", "
				+ " p.age_reported_unit_cd \"ageUnit\" " 
				+ " FROM observation obs with(nolock) "
					+ " inner join act_relationship act with(nolock)  on obs.observation_uid = act.target_act_uid "
					 + " inner join observation obs1 with(nolock)  on act.source_act_uid = obs1.observation_uid "
					 + " inner join Participation part with(nolock)  on part.act_uid = obs.observation_uid "
					 + " inner join person p with(nolock)  on p.person_uid = part.subject_entity_uid "
					 + " inner join person_name pnm  with(nolock)  on pnm.person_uid = p.person_uid "
                  /*+ " FROM observation obs with(nolock),observation obs1 with(nolock),"
                  + " act_relationship act with(nolock) "
                  + " WHERE "
                  + " act.target_act_uid = obs.observation_uid "*/
                  
                  + " AND obs1.obs_domain_cd_st_1 = 'Result'"
                  + " AND (act.source_act_uid = obs1.observation_uid)"
                  + " AND (act.target_class_cd = 'OBS')"
                  + " AND (act.type_cd = 'COMP') "
                  + " AND (act.source_class_cd = 'OBS')"
                  + " AND (act.record_status_cd = 'ACTIVE')"
                 // + " and (obs.record_status_cd in ('PROCESSED', 'UNPROCESSED'))"  
                  + " and obs.ctrl_cd_display_form = 'LabReport' ";
  
   
  public static final String SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL =
	      "select " +
	      "person.last_nm investigatorLastName, person.first_nm investigatorFirstName, person.person_uid investigatorUid, person.local_id investigatorLocalId, "+
	      "Public_health_case.investigation_status_cd  investigationStatusCd , " +
	      "code1.code_short_desc_txt  investigationStatusDescTxt, " +
	      "Public_health_case.local_id  localId , " +
	      "Public_health_case.activity_from_time  activityFromTime , " +
	      "Public_health_case.cd  cd , " +
	      "Public_health_case.cd_desc_txt conditionCodeText, " +
	      "Public_health_case.case_class_cd  caseClassCd , " +
	      "Public_health_case.add_user_id  addUserId , " +
	      "Public_health_case.last_chg_user_id lastChgUserId , " +
	      "code3.code_short_desc_txt caseClassCodeTxt, " +
	      "Public_health_case.jurisdiction_cd  jurisdictionCd, " +
	      "ISNULL(person1.last_nm,'No Last') patientLastName, ISNULL(person1.first_nm,'No First') patientFirstName, person1.person_parent_uid MPRUid,person1.curr_sex_cd currentSex, person1.birth_time birthTime, person1.local_id personLocalID, " +
	      "code4.code_desc_txt   jurisdictionDescTxt, " +
	      "Public_health_case.public_health_case_uid publicHealthCaseUid, " +
	      "Public_health_case.status_cd  statusCd, " +
	      "Public_health_case.record_status_cd recordStatusCd, " +
	      "Notification.record_status_cd  notifRecordStatusCd, " +
	      "Notification.local_id  notifLocalId " +
	      "from Public_health_case " +
	      DataTables.PUBLIC_HEALTH_CASE_TABLE + " with (nolock)" +
	      " LEFT OUTER JOIN   participation PAR121 with (nolock) ON "  +
	      "PAR121.type_cd = 'InvestgrOfPHC' " +
	      "and Public_health_case.Public_health_case_uid = PAR121.act_uid  "+
	      " LEFT OUTER JOIN       person person  with (nolock) ON " +
	      "person.person_uid= PAR121.subject_entity_uid "  +
	        "LEFT OUTER JOIN  participation PAR116  with (nolock) ON PAR116.type_cd = 'SubjOfPHC' "+ 
	        "and Public_health_case.Public_health_case_uid = PAR116.act_uid   LEFT OUTER JOIN person person1  with (nolock) ON person1.person_uid= PAR116.subject_entity_uid "+
	        //Support for Notification.Record_Status_Cd
	        "LEFT OUTER JOIN  (Select source_act_uid,target_act_uid, type_cd from act_relationship ar with (nolock), notification noti with (nolock) where ar.source_act_uid = noti.notification_uid and noti.cd='NOTF') "+
	        "AS ARNOTF ON ARNOTF.type_cd = 'Notification' AND Public_Health_Case.public_health_case_uid = ARNOTF.target_act_uid LEFT OUTER JOIN " +
	      "Notification AS Notification  with (nolock) ON Notification.Notification_uid = ARNOTF.source_act_uid " +

	      " LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	      "..code_value_general code1  with (nolock) ON " +
	      "code1.code_set_nm = 'PHC_IN_STS' "+
	      " and Public_health_case.investigation_status_cd = code1.code " +
	      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	      "..code_value_general code3  with (nolock) ON " +
	      "code3.code_set_nm = 'PHC_CLASS'  "+
	      " and  Public_health_case.case_class_cd = code3.code " +
	      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
	      "..Jurisdiction_code code4  with (nolock) ON " +
	      " Public_health_case.jurisdiction_cd = code4.code ";

	
	  
  	  public static final String VACCINATIONS_FOR_A_PHC =
  	  "SELECT phc.public_health_case_uid \"phcUid\",  "+
      "Intervention.intervention_uid \"interventionUid\",  "+
      "Intervention.activity_from_time \"activityFromTime\", "+
      "Intervention.effective_from_time \"effectiveFromTime\", "+
      "Intervention.local_id \"localId\", "+
      "Intervention.electronic_ind \"electronicInd\", "+
      "code1.code_short_desc_txt \"vaccineAdministered\" "+
      "FROM   Public_health_case   phc  with (nolock) "+
      "INNER JOIN   Act_relationship   ar  with (nolock) "+
      "ON phc.public_health_case_uid = ar.target_act_uid "+
      "AND UPPER(ar.record_status_cd) = 'ACTIVE' "+
      "AND (ar.type_cd = '1180') "+
      "AND (ar.source_class_cd = 'INTV' ) "+
      "AND ar.target_class_cd = 'CASE'   INNER JOIN "+
      "Intervention   Intervention  with (nolock) "+
      "ON Intervention.intervention_uid = ar.source_act_uid "+
      "INNER JOIN Participation  with (nolock) ON Intervention.intervention_uid = Participation.act_uid and Participation.subject_class_cd='PAT' and upper(Participation.type_cd)= 'SUBOFVACC' "+
      "LEFT OUTER JOIN   "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..code_value_general code1   with (nolock)  ON "+
      "code1.code_set_nm = 'VAC_NM'   AND   Intervention.material_cd = code1.code "+
      "WHERE phc.public_health_case_uid = ? ";
  	  
      public static final String DOCUMENT_FOR_A_PHC =
    		  "WITH added_row_number AS ( "+
    		  "SELECT " +
      "phc.public_health_case_uid \"phcUid\"," +
      "Document.nbs_document_uid  \"nbsDocumentUid\"," +
      "Document.doc_type_cd \"docType\", "+
      "Document.cd_desc_txt \"cdDescTxt\", " +
      "Document.add_time \"addTime\",  " +
      "Document.last_chg_time \"lastChgTime\",  " +
      "Document.local_id \"localId\"," +
      "Document.external_version_ctrl_nbr \"externalVersionCtrlNbr\"," +
      "Document.doc_purpose_cd \"docPurposeCd\", " +
      "ndm.doc_type_cd \"docTypeCd\", " +
      "ndm.nbs_document_metadata_uid \"nbsDocumentMetadataUid\", " +
      "Document.doc_status_cd \"docStatusCd\", "
      + "eep.doc_event_type_cd   \"docEventTypeCd\" " +
      
      ",ROW_NUMBER() OVER(PARTITION BY Document.sending_app_event_id,Document.sending_facility_nm ORDER BY Document.add_time desc) AS row_number "+
      
      "FROM " +
      "Public_Health_Case phc  with (nolock)  inner join " +
      "Act_Relationship ar  with (nolock) on "
      + "phc.public_health_case_uid = ar.target_act_uid "
      + "AND UPPER(ar.record_status_cd) = 'ACTIVE' "
      + "AND ar.source_class_cd = 'DOC' "
      + "AND ar.target_class_cd = 'CASE' "
      + "AND ar.status_cd='A' "
      + "AND ar.type_cd='DocToPHC' "
      + "inner join nbs_document Document  with (nolock) on "
      + "Document.nbs_document_uid = ar.source_act_uid "
      + "AND UPPER(Document.record_status_cd) = 'PROCESSED' "
      + "inner join nbs_document_metadata ndm  with (nolock) on "
      + "Document.nbs_document_metadata_uid = ndm.nbs_document_metadata_uid "
	  + " left outer join edx_event_process eep  with (nolock) on "
	  + " eep.nbs_document_uid = Document.nbs_document_uid "
	  + " and eep.doc_event_type_cd in('CASE','LabReport','MorbReport','CT') "
	  + " and eep.parsed_ind = 'N' "
      + " Where phc.public_health_case_uid = ? )"
	  + " SELECT  * FROM added_row_number WHERE row_number = 1";


  //------------SELECT_OBSERVATION_SUMMARY_LIST_FOR_WORKUP--------------------------------------------------------------

  public static final String
      SELECT_MORB_OBSERVATION_SUMMARY_LIST_FOR_WORKUP_SQL =
      /* + "par.type_cd \"ParTypeCd\", "
       + "par.record_status_cd \"RecordStatusCd\" " */

      "select  obs.observation_uid \"ObservationUid\", " +
      "obs.add_time \"AddTime\", " +
      "obs.ctrl_cd_display_form \"CtrlCdDisplayForm\", " +
      "obs.local_id \"LocalId\", " +
      "obsd.from_time \"FromTime\"," +
      "code1.code_desc_txt \"MorbidityReportType\" ," +
      "code1.code_short_desc_txt \"MorbidityReportTypeSRTText\", " +
      "code2.condition_desc_txt \"MorbidityConditionSRTText\" " +
      "from person per  with (nolock) " +
      "INNER  JOIN  participation par  with (nolock) on per.person_uid = par.subject_entity_uid " +
      "and par.act_class_cd = '" + NEDSSConstants.PART_ACT_CLASS_CD +
      "' and upper(par.record_status_cd) = '" +
      NEDSSConstants.PAR_RECORD_STATUS_CD +
      "' and par.type_cd = 'SubjOfMorbReport'" +
      " and par.subject_class_cd = '" +
      NEDSSConstants.PAR_SUB_CLASS_CD +
      "' INNER  JOIN observation obs  with (nolock)  ON par.act_uid = obs.observation_uid " +
      "and obs.record_status_cd in( '" +
      NEDSSConstants.OBS_PROCESSED + "'" +
      ")" +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..condition_code code2  with (nolock) ON " +
      " code2.condition_codeset_nm = 'PHC_TYPE' " +
      " and  obs.cd= code2.condition_cd " +
      "LEFT OUTER JOIN Obs_value_coded obsv  with (nolock) on " +
      "obsv.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN Obs_value_date obsd  with (nolock) on " +
      "obsd.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1  with (nolock) ON " +
      "code1.code_set_nm = 'MORB_RPT_TYPE'  and  obsv.code = code1.code " +
      // " and test_type_cd = 'O' " +
      " where  obs.Ctrl_Cd_Display_Form = 'MorbReport' " +
      "and per.person_uid = ? ";

  public static final String
      SELECT_MORB_OBSERVATION_SUMMARY_LIST_FOR_WORKUP_SQL_IN =
      /* + "par.type_cd \"ParTypeCd\", "
       + "par.record_status_cd \"RecordStatusCd\" " */

      "select  obs.observation_uid \"ObservationUid\", " +
      "obs.add_time \"AddTime\", " +
      "obs.ctrl_cd_display_form \"CtrlCdDisplayForm\", " +
      "obs.local_id \"LocalId\", " +
      "obsd.from_time \"FromTime\"," +
      "code1.code_desc_txt \"MorbidityReportType\" ," +
      "code1.code_short_desc_txt \"MorbidityReportTypeSRTText\", " +
      "code2.condition_desc_txt \"MorbidityConditionSRTText\" " +
      "from person per  with (nolock) " +
      "INNER  JOIN  participation par  with (nolock)  on per.person_uid = par.subject_entity_uid " +
      "and par.act_class_cd = '" + NEDSSConstants.PART_ACT_CLASS_CD +
      "' and upper(par.record_status_cd) = '" +
      NEDSSConstants.PAR_RECORD_STATUS_CD +
      "' and par.type_cd = 'SubjOfMorbReport'" +
      " and par.subject_class_cd = '" +
      NEDSSConstants.PAR_SUB_CLASS_CD +
      "' INNER  JOIN observation obs  with (nolock) ON par.act_uid = obs.observation_uid " +
      "and obs.record_status_cd in( '" +
      NEDSSConstants.OBS_PROCESSED + "'" +
      ")" +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..condition_code code2  with (nolock) ON " +
      " code2.condition_codeset_nm = 'PHC_TYPE' " +
      " and  obs.cd= code2.condition_cd " +
      "LEFT OUTER JOIN Obs_value_coded obsv  with (nolock) on " +
      "obsv.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN Obs_value_date obsd  with (nolock) on " +
      "obsd.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1  with (nolock) ON " +
      "code1.code_set_nm = 'MORB_RPT_TYPE'  and  obsv.code = code1.code " +
      // " and test_type_cd = 'O' " +
      " where  obs.Ctrl_Cd_Display_Form = 'MorbReport' " +
      "and per.person_uid IN ";

 

  public static final String SELECT_OBSERVATION_SUMMARY_LIST_FOR_WORKUP_SQL =
      /* + "par.type_cd \"ParTypeCd\", "
       + "par.record_status_cd \"RecordStatusCd\" " */
      "select  obs.observation_uid \"observationUid\", " +
      "obs.add_time \"addTime\", " +
      "obs.effective_from_time \"activityFromTime\", " +
      "obs.ctrl_cd_display_form \"ctrlCdDisplayForm\", " +
      "obs.cd \"cd\", " + "obs.cd_desc_txt \"cdDescTxt\", " +
      "obs.local_id \"localId\", " +
      "obs.record_status_cd \"recordStatusCd\", " +
      "code1.nbs_test_desc_txt \"cdShortDescTxt\" " +
      "from person per " +
      "INNER  JOIN  participation par on per.person_uid = par.subject_entity_uid " +
      "and par.act_class_cd = '" + NEDSSConstants.PART_ACT_CLASS_CD +
      "' and upper(par.record_status_cd) = '" +
      NEDSSConstants.PAR_RECORD_STATUS_CD + "' and par.type_cd = '" +
      NEDSSConstants.PAR110_TYP_CD + "' and par.subject_class_cd = '" +
      NEDSSConstants.PAR_SUB_CLASS_CD +
      "' INNER  JOIN observation obs ON par.act_uid = obs.observation_uid " +
      "and obs.record_status_cd in( '" +
      NEDSSConstants.OBS_PROCESSED + "','" +
      NEDSSConstants.OBS_UNPROCESSED + "')" + " left outer join  " +
      NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      ".." +
      NEDSSConstants.LAB_TEST_TABLE +
      "  code1 on  obs.cd = code1.nbs_test_code " +
      // " and test_type_cd = 'O' " +
      " where obs.ctrl_cd_display_form = 'LabReport' and per.person_uid = ? ";
  public static final String SELECT_OBSERVATION_SUMMARY_LIST_FOR_WORKUP_SQL_IN =
      /* + "par.type_cd \"ParTypeCd\", "
       + "par.record_status_cd \"RecordStatusCd\" " */
      "select  obs.observation_uid \"observationUid\", " +
      "obs.add_time \"addTime\", " +
      "obs.effective_from_time \"activityFromTime\", " +
      "obs.ctrl_cd_display_form \"ctrlCdDisplayForm\", " +
      "obs.cd \"cd\", " + "obs.cd_desc_txt \"cdDescTxt\", " +
      "obs.local_id \"localId\", " +
      "obs.record_status_cd \"recordStatusCd\", " +
      "code1.nbs_test_desc_txt \"cdShortDescTxt\" " +
      "from person per " +
      "INNER  JOIN  participation par on per.person_uid = par.subject_entity_uid " +
      "and par.act_class_cd = '" + NEDSSConstants.PART_ACT_CLASS_CD +
      "' and upper(par.record_status_cd) = '" +
      NEDSSConstants.PAR_RECORD_STATUS_CD + "' and par.type_cd = '" +
      NEDSSConstants.PAR110_TYP_CD + "' and par.subject_class_cd = '" +
      NEDSSConstants.PAR_SUB_CLASS_CD +
      "' INNER  JOIN observation obs ON par.act_uid = obs.observation_uid " +
      "and obs.record_status_cd in( '" +
      NEDSSConstants.OBS_PROCESSED + "','" +
      NEDSSConstants.OBS_UNPROCESSED + "')" + " left outer join  " +
      NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      ".." +
      NEDSSConstants.LAB_TEST_TABLE +
      "  code1 on  obs.cd = code1.nbs_test_code " +
      // " and test_type_cd = 'O' " +
      " where obs.ctrl_cd_display_form = 'LabReport' and per.person_uid IN ";

  

public static final String SELECT_OBSERVATIONUIDS_FOR_WORKUP_IN =
      /*"SELECT act_uid \"uid\" FROM Participation, Observation " +
      "WHERE Participation.ACT_UID = Observation.OBSERVATION_UID "+
      "AND Participation.act_class_cd = 'OBS' "+
      "AND Participation.type_cd= 'PATSBJ' "+
      "AND Participation.subject_class_cd = 'PSN' " +
      "AND Participation.record_status_cd = 'ACTIVE' " +
      "AND Observation.RECORD_STATUS_CD NOT IN ('LOG_DEL') " +
      "AND Participation.subject_entity_uid IN ";*/
        "SELECT participation.act_uid \"uid\" "+
        "FROM observation OBS, person, " +
        "participation  " +
        "WHERE OBS.observation_uid=participation.act_uid " +
        "AND participation.subject_entity_uid=person.person_uid " +
        "AND participation.type_cd='PATSBJ' " +
        "AND Participation.act_class_cd = 'OBS' " +
        "AND Participation.subject_class_cd = 'PSN'  " +
        "AND Participation.record_status_cd = 'ACTIVE' " +
        "AND OBS.RECORD_STATUS_CD NOT IN ('LOG_DEL') " +
        "AND person_parent_uid = " ;

public static final String SELECT_OBSERVATIONUIDS_FOR_SUMMARY_WORKUP_IN =
//      "SELECT act_uid \"uid\" FROM Participation, Observation " +
//      "WHERE Participation.ACT_UID = Observation.OBSERVATION_UID "+
//      "AND Participation.act_class_cd = 'OBS' "+
//      "AND Participation.type_cd= 'PATSBJ' "+
//      "AND Participation.subject_class_cd = 'PSN' " +
//      "AND Participation.record_status_cd = 'ACTIVE' " +
//      "AND Observation.RECORD_STATUS_CD IN ('UNPROCESSED', 'UNPROCESSED_PREV_D') " +
//      "AND Participation.subject_entity_uid IN ";

        "SELECT participation.act_uid \"uid\" "+
        "FROM observation OBS, person, "+
        "participation "+
        "WHERE "+
        "OBS.observation_uid=participation.act_uid " +
        "AND participation.subject_entity_uid=person.person_uid " +
        "AND participation.type_cd='PATSBJ' " +
        "AND Participation.act_class_cd = 'OBS' " +
        "AND Participation.subject_class_cd = 'PSN' " +
        "AND Participation.record_status_cd = 'ACTIVE' " +
        "AND OBS.RECORD_STATUS_CD = 'UNPROCESSED' " +
        "AND person_parent_uid = ";

public static final String SELECT_LABSUMMARY_FORWORKUP =
      "SELECT local_id \"LocalId\", jurisdiction_cd \"Jurisdiction\","+
      "  status_cd \"Status\", record_status_cd \"RecordStatusCd\","+
      " cd_desc_txt \"OrderedTest\", " +
      "observation_uid \"ObservationUid\", prog_area_cd \"ProgramArea\", "+
      " rpt_to_state_time \"DateReceived\", " +
      " activity_from_time \"activityFromTime\", "+
      " cd_system_cd \"cdSystemCd\", " +
      " effective_from_time \"DateCollected\", " +
      " processing_decision_cd \"processingDecisionCd\", " +
      " electronic_ind \"electronicInd\" "+
      " FROM observation " +
      "where observation_uid = ?";

	public static final String SELECT_LAB_SUMMARY_FOR_MORB = "SELECT local_id \"LocalId\", jurisdiction_cd \"Jurisdiction\","
			+ " status_cd \"Status\", record_status_cd \"RecordStatusCd\"," + " cd_desc_txt \"OrderedTest\", "
			+ " observation_uid \"ObservationUid\", prog_area_cd \"ProgramArea\", "
			+ " rpt_to_state_time \"DateReceived\", " + " activity_from_time \"activityFromTime\", "
			+ " cd_system_cd \"cdSystemCd\", " + " effective_from_time \"DateCollected\", "
			+ " processing_decision_cd \"processingDecisionCd\", " + " electronic_ind \"electronicInd\" "
			+ " FROM observation with(nolock) " + "where observation_uid in";


public static final String SELECT_LABSUMMARY_FORWORKUPNEW =
    "SELECT participation.act_uid \"uid\", " +
    "OBS.local_id \"LocalId\", OBS.jurisdiction_cd \"Jurisdiction\", " +
    "OBS.status_cd \"Status\", OBS.record_status_cd \"RecordStatusCd\", " +
    "OBS.cd_desc_txt \"OrderedTest\", " +
    "OBS.observation_uid \"ObservationUid\", OBS.prog_area_cd \"ProgramArea\", " +
    "OBS.RECORD_STATUS_CD \"recordStatusCode\", " +
    "OBS.rpt_to_state_time \"DateReceived\", " +
    "OBS.activity_from_time \"activityFromTime\", "+
    "OBS.cd_system_cd \"cdSystemCd\", " +
    "OBS.effective_from_time \"DateCollected\", "+
    "OBS.processing_decision_cd \"processingDecisionCd\", "+
    "OBS.electronic_ind \"electronicInd\" "+
    "FROM observation OBS, person, " +
    "participation " +
    "WHERE " +
    "OBS.observation_uid=participation.act_uid " +
    "AND participation.subject_entity_uid=person.person_uid " +
    "AND participation.type_cd='PATSBJ' " +
    "AND Participation.act_class_cd = 'OBS' " +
    "AND Participation.subject_class_cd = 'PSN'   " +
    "AND Participation.record_status_cd = 'ACTIVE' " +
    "AND person_parent_uid = ?";
 

     public static final String SELECT_LABRESULTED_REFLEXTEST_SUMMARY_FORWORKUP_SQL =
      "SELECT distinct " +
      " obs.observation_uid \"observationUid\" , "+
            " obs1.ctrl_Cd_User_Defined_1 \"ctrlCdUserDefined1\", " +
            " act.source_act_uid \"sourceActUid\", " +
      " obs1.local_id \"localId\", obs1.cd_desc_txt \"resultedTest\", "+
      " obs1.cd \"resultedTestCd\", " +
      " obs1.cd_system_cd \"cdSystemCd\", "+
      " obs1.status_cd \"resultedTestStatusCd\", "+ // Added this line for ER16368
      " obsvaluecoded.code \"codedResultValue\", " +
      " obsvaluecoded.display_name \"organismName\", " +
      " obsvaluecoded.code_system_cd \"organismCodeSystemCd\", " +
      " obsnumeric.high_range \"highRange\","+
      " obsnumeric.low_range \"lowRange\","+
      " obsnumeric.comparator_cd_1 \"numericResultCompare\","+
      " obsnumeric.separator_cd \"numericResultSeperator\", " +
      " obsnumeric.numeric_value_1 \"numericResultValue1\","+
      " obsnumeric.numeric_value_2 \"numericResultValue2\", " +
      " obsnumeric.numeric_scale_1 \"numericScale1\"," +
      " obsnumeric.numeric_scale_2 \"numericScale2\", " +
      " obsnumeric.numeric_unit_cd \"numericResultUnits\", "+
      " obsvaluetext.value_txt \"textResultValue\" " +
            "FROM observation obs " +
            "    inner JOIN act_relationship act ON  act.target_act_uid = obs.observation_uid " +
            "        AND (act.target_class_cd = 'OBS') " +
            "        AND (act.type_cd = ?) " +
            "        AND (act.source_class_cd = 'OBS') " +
            "        AND (act.record_status_cd = 'ACTIVE') " +
            "    inner JOIN observation obs1 ON  act.source_act_uid = obs1.observation_uid " +
            "   and (obs1.obs_domain_cd_st_1 = 'Result')"  +
            "    LEFT OUTER JOIN obs_value_numeric obsnumeric on obsnumeric.observation_uid= obs1.observation_uid " +
            "    LEFT OUTER JOIN obs_value_coded obsvaluecoded on obsvaluecoded.observation_uid = obs1.observation_uid " +
            "    LEFT OUTER JOIN obs_value_txt obsvaluetext on obsvaluetext.observation_uid = obs1.observation_uid " +
            "    and ((obsvaluetext.txt_type_cd IS NULL) or (obsvaluetext.txt_type_cd = 'O'))" +
                        "       and obsvaluetext.obs_value_txt_seq='1'" +
            "WHERE " +
            "act.TARGET_ACT_UID = ?";



     /*"FROM observation obs, observation obs1, "+
      " act_relationship act, obs_value_numeric obsnumeric, " +
      " obs_value_coded obsvaluecoded, obs_value_txt obsvaluetext " +
      "WHERE (    (act.target_act_uid = obs.observation_uid) " +
      " AND (act.source_act_uid = obs1.observation_uid) " +
      " AND (obsnumeric.observation_uid(+) = obs1.observation_uid) " +
      " AND (obsvaluecoded.observation_uid(+) = obs1.observation_uid) " +
      " AND (obsvaluetext.observation_uid(+) = obs1.observation_uid) " +
            " AND (obsvaluetext.txt_type_cd(+) = null) " +
      " AND (act.target_class_cd = 'OBS') " +
      " AND (act.type_cd = ?) " +
      " AND (act.source_class_cd = 'OBS') " +
      " AND (act.record_status_cd = 'ACTIVE') " +
      " AND (act.TARGET_ACT_UID = ?) ) ";
            */
            //"    AND (obsvaluetext.txt_type_cd IS NULL)";

  //---------------------------------------------------------------------------
  public static final String
      SELECT_MORB_OBSERVATION_SUMMARY_LIST_FOR_INVESTIGATION_SQL =
      //'Morbidity Report'
      "select ar.source_act_uid \"observationUid\", " +
      "obs.add_time \"addTime\", " +
      "obs.ctrl_cd_display_form \"ctrlCdDisplayForm\", " +
      "obs.local_id \"localId\", " +
      "obsd.from_time \"fromTime\"," +
      "code1.code_desc_txt \"morbidityReportType\"," +
      "ar.target_act_uid \"targetActUid\", " +
      "ar.status_cd \"statusCd\", " +
      "ar.record_status_cd \"recordStatusCd\", " +
      "ar.type_cd \"typeCd\", " +
      "code1.code_short_desc_txt \"morbidityReportTypeSRTText\", " +
      "code2.condition_desc_txt \"morbidityConditionSRTText\", " +
      DataTables.PUBLIC_HEALTH_CASE_TABLE + ".cd \"morbidityCondition\" " +
      "from public_health_case " +
      DataTables.PUBLIC_HEALTH_CASE_TABLE +
      " inner JOIN act_relationship ar ON ar.target_act_uid = " +
      DataTables.PUBLIC_HEALTH_CASE_TABLE +
      ".public_health_case_uid " +
      "and upper(ar.record_status_cd) = 'ACTIVE' " +
      "and ar.type_cd in ('MorbReport') " +
      "and ar.source_class_cd = 'OBS' " +
      "and ar.target_class_cd = 'CASE' " +
      "inner join observation obs on obs.observation_uid = ar.source_act_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..condition_code code2 ON " +
      "code2.condition_codeset_nm = 'PHC_TYPE' and  obs.cd= code2.condition_cd " +
      "LEFT OUTER JOIN Obs_value_coded obsv on " +
      "obsv.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN Obs_value_date obsd on " +
      "obsd.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1 ON " +
      "code1.code_set_nm = 'MORB_RPT_TYPE'  and  obsv.code = code1.code " +
      "where " + DataTables.PUBLIC_HEALTH_CASE_TABLE +
      ".public_health_case_uid = ? ";
  public static final String
      SELECT_MORB_OBSERVATION_SUMMARY_LIST_FOR_INVESTIGATION_SQL_IN =
      //'Morbidity Report'
      "select ar.source_act_uid \"observationUid\", " +
      "obs.add_time \"addTime\", " +
      "obs.ctrl_cd_display_form \"ctrlCdDisplayForm\", " +
      "obs.local_id \"localId\", " +
      "obsd.from_time \"fromTime\"," +
      "code1.code_desc_txt \"morbidityReportType\"," +
      "ar.target_act_uid \"targetActUid\", " +
      "ar.status_cd \"statusCd\", " +
      "ar.record_status_cd \"recordStatusCd\", " +
      "ar.type_cd \"typeCd\", " +
      "code1.code_short_desc_txt \"morbidityReportTypeSRTText\", " +
      "code2.condition_desc_txt \"morbidityConditionSRTText\", " +
      DataTables.PUBLIC_HEALTH_CASE_TABLE + ".cd \"morbidityCondition\" " +
      "from public_health_case " +
      DataTables.PUBLIC_HEALTH_CASE_TABLE +
      " inner JOIN act_relationship ar ON ar.target_act_uid = " +
      DataTables.PUBLIC_HEALTH_CASE_TABLE +
      ".public_health_case_uid " +
      "and upper(ar.record_status_cd) = 'ACTIVE' " +
      "and ar.type_cd in ('MorbReport') " +
      "and ar.source_class_cd = 'OBS' " +
      "and ar.target_class_cd = 'CASE' " +
      "inner join observation obs on obs.observation_uid = ar.source_act_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..condition_code code2 ON " +
      "code2.condition_codeset_nm = 'PHC_TYPE' and  obs.cd= code2.condition_cd " +
      "LEFT OUTER JOIN Obs_value_coded obsv on " +
      "obsv.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN Obs_value_date obsd on " +
      "obsd.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1 ON " +
      "code1.code_set_nm = 'MORB_RPT_TYPE'  and  obsv.code = code1.code " +
      "where " + DataTables.PUBLIC_HEALTH_CASE_TABLE +
      ".public_health_case_uid IN ";

  
  public static final String
      SELECT_OBSERVATION_SUMMARY_LIST_FOR_INVESTIGATION_SQL =
      //'GenericObs', 'LabGenObs'
      "select ar.source_act_uid \"ObservationUid\", " +
      "obs.add_time \"AddTime\", " +
      "obs.effective_from_time \"ActivityFromTime\", " +
      "obs.ctrl_cd_display_form \"CtrlCdDisplayForm\", " +
      "obs.cd \"Cd\", " + "obs.cd_desc_txt \"CdDescTxt\", " +
      "obs.local_id \"LocalId\", " +
      "ar.target_act_uid \"TargetActUid\", " +
      "ar.status_cd \"StatusCd\", " +
      "ar.record_status_cd \"RecordStatusCd\", " +
      "ar.type_cd \"TypeCd\", " +
      "code1.code_short_desc_txt \"CdShortDescTxt\" " +
      "from public_health_case " +
      DataTables.PUBLIC_HEALTH_CASE_TABLE +
      " inner JOIN act_relationship ar ON ar.target_act_uid = " +
      DataTables.PUBLIC_HEALTH_CASE_TABLE +
      ".public_health_case_uid " +
      "and upper(ar.record_status_cd) = 'ACTIVE' " +
      "and ar.type_cd in ('LabReport') " +
      "and ar.source_class_cd = 'OBS' " +
      "and ar.target_class_cd = 'CASE' " +
      "inner join observation obs on obs.observation_uid = ar.source_act_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1 ON " +
      "code1.code_set_nm = 'AR_TYPE'  and  ar.type_cd = code1.code " +
      "where " + DataTables.PUBLIC_HEALTH_CASE_TABLE +
      ".public_health_case_uid = ? ";
  
  public static final String SELECT_OBSERVATION_SUMMARY_LIST_FOR_MANAGE_SQL =
      "select ar.source_act_uid \"ObservationUid\", " +
      "obs.add_time \"AddTime\", " +
      "obs.effective_from_time \"ActivityFromTime\", " +
      "obs.ctrl_cd_display_form \"CtrlCdDisplayForm\", " +
      "obs.cd_desc_txt \"CdDescTxt\", " + "obs.local_id \"LocalId\", " +
      "obs.cd \"Cd\", " + "ar.target_act_uid \"TargetActUid\", " +
      "ar.status_cd \"StatusCd\", " +
      "ar.record_status_cd \"RecordStatusCd\", " +
      "ar.type_cd \"TypeCd\", " +
      "code1.code_short_desc_txt \"CdShortDescTxt\" " +
      "from public_health_case phc " +
      "inner JOIN act_relationship ar "+
      " ON ar.target_act_uid = phc.public_health_case_uid " +
      "and upper(ar.record_status_cd) = 'ACTIVE' " +
      "and ar.type_cd = 'LabReport' " + "and ar.source_class_cd='OBS' " +
      "and ar.target_class_cd='CASE' " +
      "inner join observation obs on obs.observation_uid = ar.source_act_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1 ON " +
      "code1.code_set_nm = 'AR_TYPE'  and  ar.type_cd = code1.code " +
      "where obs.ctrl_cd_display_form = 'LabReport' "+
      " and phc.public_health_case_uid = ? ";
  
  public static final String SELECT_OBSERVATION_SUMMARY_LIST_FOR_MANAGE1_SQL =
      "select  obs.observation_uid \"ObservationUid\", " +
      "obs.add_time \"AddTime\", " +
      "obs.effective_from_time \"ActivityFromTime\", " +
      "obs.ctrl_cd_display_form \"CtrlCdDisplayForm\", " +
      "obs.cd_desc_txt \"CdDescTxt\", " + "obs.local_id \"LocalId\", " +
      "obs.cd \"Cd\", " + "par.type_cd \"ParTypeCd\", " +
      "par.record_status_cd \"RecordStatusCd\" " + "from person per " +
      "INNER  JOIN  participation par on per.person_uid = par.subject_entity_uid " +
      "and par.act_class_cd='OBS' " + "and par.subject_class_cd='PSN' " +
      "and upper(par.record_status_cd) = 'ACTIVE' " +
      "and par.type_cd ='PATSBJ' " +
      "INNER  JOIN observation obs ON par.act_uid = obs.observation_uid " +
      "and obs.record_status_cd IN ('" +
      NEDSSConstants.OBS_PROCESSED + "','" + NEDSSConstants.OBS_UNPROCESSED +
      "') " +
      "where obs.ctrl_cd_display_form = 'LabReport' and per.person_uid = ? ";
  

  public static final String
      SELECT_MORB_OBSERVATION_SUMMARY_LIST_FOR_MANAGE_SQL =
      "SELECT obs.add_time \"AddTime\", " +
      "'Y' AS \"IsAssociated\"," +
      "obsd.from_time \"FromTime\", " +
      "code1.code_desc_txt \"MorbidityReportType\", " +
      "obs.ctrl_cd_display_form \"CtrlCdDisplayForm\", " +
      //"cond.condition_short_nm \"ConditionShortNm\", "+
      "obs.local_id \"LocalId\", " +
      "obs.Observation_uid \"ObservationUid\", " +
      //"phc.public_health_case_uid \"PublicHealthCaseUid\", "+
      "act.record_status_cd \"RecordStatusCd\", " +
      //"act.source_act_uid \"SourceActUid\" "+
      "code1.code_short_desc_txt \"MorbidityReportTypeSRTText\", " +
      "code2.condition_desc_txt \"MorbidityConditionSRTText\", " +
      "phc.cd \"MorbidityCondition\" " +
      "FROM public_health_case phc " +
      "inner join act_relationship act on " +
      "phc.public_health_case_uid = act.target_act_uid " +
      "and act.source_class_cd = 'OBS' " +
      "and act.target_class_cd = 'CASE' " +
      "inner join observation obs on " +
      "obs.observation_uid = act.source_act_uid " +
      "and upper(obs.ctrl_cd_display_form)= 'MORBREPORT' " +
      "and (obs.record_status_cd IN ('" +
      NEDSSConstants.OBS_PROCESSED + "')) " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..condition_code code2 ON " +
      "code2.condition_codeset_nm = 'PHC_TYPE' and  obs.cd= code2.condition_cd " +
      "LEFT OUTER JOIN Obs_value_coded obsv on " +
      "obsv.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN Obs_value_date obsd on " +
      "obsd.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1 ON " +
      "code1.code_set_nm = 'MORB_RPT_TYPE'  and  obsv.code = code1.code " +
      "where phc.public_health_case_uid = ? " +
      "union " +
      "SELECT obs.add_time \"AddTime\", " +
      "'N' AS \"IsAssociated\", " +
      "obsd.from_time \"FromTime\", " +
      "code1.code_desc_txt \"MorbidityReportType\", " +
      "obs.ctrl_cd_display_form \"CtrlCdDisplayForm\", " +
      //"cond.condition_short_nm \"ConditionShortNm\", "+
      "obs.local_id \"LocalId\", " +
      "obs.Observation_uid \"ObservationUid\", " +
      //"phc.public_health_case_uid \"PublicHealthCaseUid\", "+
      "act.record_status_cd \"RecordStatusCd\", " +
      //"act.source_act_uid \"SourceActUid\" "+
      "code1.code_short_desc_txt \"MorbidityReportTypeSRTText\", " +
      "code2.condition_desc_txt \"MorbidityConditionSRTText\", " +
      "phc.cd \"MorbidityCondition\" " +
      "FROM Observation obs " +
      "inner join act_relationship act on " +
      "obs.observation_uid != act.source_act_uid " +
      "and upper(obs.ctrl_cd_display_form)= 'MORBREPORT' " +
      "and upper(act.type_cd) = 'MORBREPORT' " +
      "and act.source_class_cd = 'OBS' " +
      "and act.target_class_cd = 'CASE' " +
      "and act.record_status_cd = 'ACTIVE' " +
      "and (obs.record_status_cd IN ('" +
      NEDSSConstants.OBS_PROCESSED + "')) " +
      "inner join public_health_case phc on " +
      "public_health_case_uid = act.target_act_uid " +
      "inner join participation par on " +
      "obs.observation_uid = par.act_uid " +
      "and par.type_cd = 'SubjOfMorbReport' " +
      "and par.act_class_cd = 'OBS' " +
      "and par.subject_class_cd = 'PSN' " +
      "and par.record_status_cd = 'ACTIVE' " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..condition_code code2 ON " +
      "code2.condition_codeset_nm = 'PHC_TYPE' and  obs.cd= code2.condition_cd " +
      "LEFT   OUTER JOIN Obs_value_coded obsv on " +
      "obsv.observation_uid = obs.observation_uid " +
      "LEFT  OUTER JOIN Obs_value_date obsd on " +
      "obsd.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1 ON " +
      "code1.code_set_nm = 'MORB_RPT_TYPE'  and  obsv.code = code1.code " +
      "where par.subject_entity_uid = ? ";

  public static final String SELECT_MORB_OBSERVATION_SUMMARY_PART1_SQL =
      "SELECT obs.add_time \"AddTime\", " +
      "'Y' AS \"IsAssociated\"," +
      "obsd.from_time \"FromTime\", " +
      "code1.code_desc_txt \"MorbidityReportType\", " +
      "obs.ctrl_cd_display_form \"CtrlCdDisplayForm\", " +
      //"cond.condition_short_nm \"ConditionShortNm\", "+
      "obs.local_id \"LocalId\", " +
      "obs.Observation_uid \"ObservationUid\", " +
      //"phc.public_health_case_uid \"PublicHealthCaseUid\", "+
      "act.record_status_cd \"RecordStatusCd\", " +
      //"act.source_act_uid \"SourceActUid\" "+
      "code1.code_short_desc_txt \"MorbidityReportTypeSRTText\", " +
      "code2.condition_desc_txt \"MorbidityConditionSRTText\", " +
      "phc.cd \"MorbidityCondition\" " +
      "FROM public_health_case phc " +
      "inner join act_relationship act on " +
      "phc.public_health_case_uid = act.target_act_uid " +
      "and act.source_class_cd = 'OBS' " +
      "and act.target_class_cd = 'CASE' " +
      "inner join observation obs on " +
      "obs.observation_uid = act.source_act_uid " +
      "and upper(obs.ctrl_cd_display_form)= 'MORBREPORT' " +
      "and (obs.record_status_cd IN ('" +
      NEDSSConstants.OBS_PROCESSED + "')) " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..condition_code code2 ON " +
      "code2.condition_codeset_nm = 'PHC_TYPE' and  obs.cd= code2.condition_cd " +
      "LEFT OUTER JOIN Obs_value_coded obsv on " +
      "obsv.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN Obs_value_date obsd on " +
      "obsd.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1 ON " +
      "code1.code_set_nm = 'MORB_RPT_TYPE'  and  obsv.code = code1.code " +
      "where phc.public_health_case_uid = ? ";

  public static final String SELECT_MORB_OBSERVATION_SUMMARY_PART2_SQL =
      "SELECT obs.add_time \"AddTime\", " +
      "'N' AS \"IsAssociated\", " +
      "obsd.from_time \"FromTime\", " +
      "code1.code_desc_txt \"MorbidityReportType\", " +
      "obs.ctrl_cd_display_form \"CtrlCdDisplayForm\", " +
      //"cond.condition_short_nm \"ConditionShortNm\", "+
      "obs.local_id \"LocalId\", " +
      "obs.Observation_uid \"ObservationUid\", " +
      //"phc.public_health_case_uid \"PublicHealthCaseUid\", "+
      "act.record_status_cd \"RecordStatusCd\", " +
      //"act.source_act_uid \"SourceActUid\" "+
      "code1.code_short_desc_txt \"MorbidityReportTypeSRTText\", " +
      "code2.condition_desc_txt \"MorbidityConditionSRTText\", " +
      "phc.cd \"MorbidityCondition\" " +
      "FROM Observation obs " +
      "LEFT OUTER JOIN act_relationship act on " +
      "obs.observation_uid = act.source_act_uid " +
      "and upper(obs.ctrl_cd_display_form)= 'MORBREPORT' " +
      //"and upper(act.type_cd) = 'MORBREPORT' "+
      //"and act.source_class_cd = 'OBS' "+
      //"and act.target_class_cd = 'CASE' "+
      //"and act.record_status_cd = 'ACTIVE' "+
      //"and act.target_act_uid is null "+
      "and (obs.record_status_cd IN ('" +
      NEDSSConstants.OBS_PROCESSED + "')) " +
      "LEFT OUTER JOIN public_health_case phc on " +
      "public_health_case_uid = act.target_act_uid " +
      "inner join participation par on " +
      "obs.observation_uid = par.act_uid " +
      "and par.type_cd = 'SubjOfMorbReport' " +
      "and par.act_class_cd = 'OBS' " +
      "and par.subject_class_cd = 'PSN' " +
      "and par.record_status_cd = 'ACTIVE' " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..condition_code code2 ON " +
      "code2.condition_codeset_nm = 'PHC_TYPE' and  obs.cd= code2.condition_cd " +
      "LEFT   OUTER JOIN Obs_value_coded obsv on " +
      "obsv.observation_uid = obs.observation_uid " +
      "LEFT  OUTER JOIN Obs_value_date obsd on " +
      "obsd.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1 ON " +
      "code1.code_set_nm = 'MORB_RPT_TYPE'  and  obsv.code = code1.code " +
      "where act.target_act_uid is null and par.subject_entity_uid = ? "+
      " and (obs.record_status_cd IN ('" +
      NEDSSConstants.OBS_PROCESSED + "')) ";

  

  //-----------NOTIFICATIONS_FOR_APPROVAL--------------------------------------------------
	public static final String NOTIFICATIONS_FOR_APPROVAL_SQL = "select " + "Notification.add_time \"addTime\", "
			+ "Notification.add_user_id \"addUserId\", " + "Notification.txt \"txt\", "
			+ "Notification.cd \"notificationCd\", " + "Notification.notification_uid \"notificationUid\", "
			+ "Notification.local_id \"localId\", " + "Public_health_case.local_id \"publicHealthCaseLocalId\", "
			+ "Public_health_case.cd \"cd\", " + "code1.condition_desc_txt \"CdTxt\", " + "code1.nnd_ind \"nndInd\" , "
			+ "Public_health_case.case_class_cd \"caseClassCd\", " + "Notification.jurisdiction_cd \"jurisdictionCd\", "
			+ "Public_health_case.public_health_case_uid \"publicHealthCaseUid\", "
			+ "ISNULL(per.last_nm,'No Last')  \"lastNm\", ISNULL(per.first_nm,'No First') \"firstNm\", "
			+ "per.curr_sex_cd \"currSexCd\", " + "per.person_parent_uid \"MPRUid\", "
			+ "per.birth_time_calc \"birthTimeCalc\" ," + "au.User_First_Nm + ' ' + au.User_Last_Nm \"addUserName\", "
			+ "exportReceiving.receiving_system_nm \"recipient\" " + "from " + DataTables.NOTIFICATION_TABLE
			+ " Notification with (nolock) " + "INNER JOIN " + DataTables.ACT_RELATIONSHIP + " act with (nolock) "
			+ "ON act.source_act_uid = Notification.notification_uid  " + "INNER JOIN "
			+ DataTables.PUBLIC_HEALTH_CASE_TABLE + " Public_health_case with (nolock) "
			+ "ON Public_health_case.public_health_case_uid = act.target_act_uid " + "INNER JOIN "
			+ DataTables.PARTICIPATION_TABLE + " par with (nolock) " + "ON par.act_uid = Public_health_case.public_health_case_uid "
			+ "INNER JOIN " + DataTables.PERSON_TABLE + " per with (nolock) " + "ON per.person_uid = par.subject_entity_uid "
			+ "LEFT JOIN " + DataTables.EXPORT_RECEIVING_FACILITY + " exportReceiving with (nolock) "
			+ "ON exportReceiving.export_receiving_facility_uid = Notification.export_receiving_facility_uid " +

			"LEFT OUTER JOIN AUTH_USER au with (nolock) " + "ON Notification.add_user_id = au.nedss_Entry_Id " +

			"LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..condition_code code1 with (nolock) "
			+ "ON Public_health_case.cd = code1.condition_cd " +
			/*
			 * "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
			 * "..code_value_general code2 " + "ON code2.code_set_nm = 'PHC_CLASS' "+
			 * " and Public_health_case.case_class_cd = code2.code " + "LEFT OUTER JOIN " +
			 * NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Jurisdiction_code code3 " +
			 * "ON Notification.jurisdiction_cd = code3.code " +
			 */
			"where Notification.record_status_cd = '" + NEDSSConstants.NOTIFICATION_PENDING_CODE + "' "
			+ "and act.type_cd = '" + NEDSSConstants.ACT106_TYP_CD + "' " + "AND par.type_cd = '"
			+ NEDSSConstants.PHC_PATIENT + "' ";

  

  //------------SELECT_COUNT_OF_NOTIFICATIONS_FOR_APPROVAL-------------------------------------------------------------------

  public static final String SELECT_COUNT_OF_NOTIFICATIONS_FOR_APPROVAL_SQL_REVISITED =
      "select count(*) from " +
      DataTables.NOTIFICATION_TABLE + " Notification  with (nolock) " +
      "INNER JOIN " + DataTables.ACT_RELATIONSHIP + " act with (nolock) " +
      "ON  act.source_act_uid = Notification.notification_uid " +
      "INNER JOIN " +
      DataTables.PUBLIC_HEALTH_CASE_TABLE + " Public_health_case with (nolock)  " +
      "ON  Public_health_case.public_health_case_uid = act.target_act_uid " +
      "where Notification.record_status_cd = '" +
      NEDSSConstants.NOTIFICATION_PENDING_CODE + "' " +
      "and Public_health_case.record_status_cd <> '" +
      NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE + "' " +
      "and act.type_cd = '" + NEDSSConstants.ACT106_TYP_CD + "' ";

     //------------SELECT_COUNT_OF_UPDATED_NOTIFICATIONS_FOR_APPROVAL-------------------------------------------------------------------


    public static final String SELECT_COUNT_OF_UPDATED_NOTIFICATIONS_FOR_APPROVAL_SQL_REVISITED =
        "select count(*) from nbs_odse..updated_notification with (nolock) " +
        "INNER JOIN "
        + DataTables.NOTIFICATION_TABLE + " with (nolock)  " + " on " +
        "notification.notification_uid=updated_notification.notification_uid " +
        "where updated_notification.status_cd='A' " +
        "and " +
        "notification.record_status_cd <> '" +
        NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE + "' ";

 
  //-----------------------------------------------------------------------------------------
  public static final String SELECT_CLINICAL_DOCUMENT_HIST =
      "Select clinical_document_uid \"clinicalDocumentUid\", " +
      "activity_duration_amt \"activityDurationAmt\", " +
      "activity_duration_unit_cd \"activityDurationUnitCd\", " +
      "activity_from_time \"activityFromTime\", " +
      "activity_to_time \"activityToTime\", " +
      "add_reason_cd \"addReasonCd\", " + "add_time \"addTime\", " +
      "add_user_id \"addUserId\", " + "cd \"cd\", " +
      "cd_desc_txt \"cdDescTxt\", " +
      "confidentiality_cd \"confidentialityCd\", " +
      "confidentiality_desc_txt \"confidentialityDescTxt\", " +
      "copy_from_time \"copyFromTime\", " +
      "copy_to_time \"copyToTime\", " +
      "effective_duration_amt \"effectiveDurationAmt\", " +
      "effective_duration_unit_cd \"effectiveDurationUnitCd\", " +
      "effective_from_time \"effectiveFromTime\", " +
      "effective_to_time \"effectiveToTime\", " +
      "last_chg_reason_cd \"lastChgReasonCd\", " +
      "last_chg_time \"lastChgTime\", " +
      "last_chg_user_id \"lastChgUserId\", " + "local_id \"localId\", " +
      "org_access_permis \"orgAccessPermis\", " +
      "practice_setting_cd \"practiceSettingCd\", " +
      "practice_setting_desc_txt \"practiceSettingDescTxt\", " +
      "prog_area_access_permis \"progAreaAccessPermis\", " +
      "record_status_cd \"recordStatusCd\", " +
      "record_status_time \"recordStatusTime\", " +
      "status_cd \"statusCd\", " + "status_time \"statusTime\", " +
      "txt \"Txt\", " + "user_affiliation_txt \"userAffiliationTxt\", " +
      "version_nbr versionNbr from clinical_document_hist "+
      " where clinical_document_uid = ? and version_ctrl_nbr = ?";
  public static final String INSERT_CLINICAL_DOCUMENT_HIST =
      "INSERT into clinical_document_hist(" + "clinical_document_uid, " +
      "version_ctrl_nbr, " + "activity_duration_amt, " +
      "activity_duration_unit_cd, " + "activity_from_time, " +
      "activity_to_time, " + "add_reason_cd, " + "add_time, " +
      "add_user_id, " + "cd, " + "cd_desc_txt, " +
      "confidentiality_cd, " + "confidentiality_desc_txt, " +
      "copy_from_time, " + "copy_to_time, " + "effective_duration_amt, " +
      "effective_duration_unit_cd, " + "effective_from_time, " +
      "effective_to_time, " + "last_chg_reason_cd, " + "last_chg_time, " +
      "last_chg_user_id, " + "local_id, " + "org_access_permis, " +
      "practice_setting_cd, " + "practice_setting_desc_txt, " +
      "prog_area_access_permis, " + "record_status_cd, " +
      "record_status_time, " + "status_cd, " + "status_time, " + "txt, " +
      "user_affiliation_txt, " +
      "version_nbr) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+
      " ?,?,?,?,?,?,?,?,?)";
  public static final String SELECT_CLINICAL_DOCUMENT_SEQ_NUMBER_HIST =
      "SELECT version_ctrl_nbr from clinical_document_hist where clinical_document_uid = ?";
  public static final String SELECT_CLINICALDOCUMENT_UID =
      "SELECT clinical_document_uid " + "FROM clinical_document " +
      "WHERE clinical_document_uid = ?";
  public static final String INSERT_CLINICALDOCUMENT =
      //1
      //2
      //3
      //4
      //5
      //6
      //7
      //8
      //9
      //10
      //11
      //12
      //13
      //14
      //                        "copy_time, " +
      //15
      //16
      //17
      //18
      //                        "encounter_from_time, " +
      //                        "encounter_to_time, " +
      //19
      //20
      //21
      //22
      //23
      //24
      //25
      //26
      //27
      //28
      //29
      //30
      //31
      //32
      //33
      "INSERT INTO clinical_document ( " + "clinical_document_uid, " +
      "activity_duration_amt, " + "activity_duration_unit_cd, " +
      "activity_from_time, " + "activity_to_time, " + "add_reason_cd, " +
      "add_time, " + "add_user_id, " + "cd, " + "cd_desc_txt, " +
      "confidentiality_cd, " + "confidentiality_desc_txt, " +
      "copy_from_time, " + "copy_to_time, " + "effective_duration_amt, " +
      "effective_duration_unit_cd, " + "effective_from_time, " +
      "effective_to_time, " + "last_chg_reason_cd, " + "last_chg_time, " +
      "last_chg_user_id, " + "local_id, " + "org_access_permis, " +
      "practice_setting_cd, " + "practice_setting_desc_txt, " +
      "prog_area_access_permis, " + "record_status_cd, " +
      "record_status_time, " + "status_cd, " + "status_time, " + "txt, " +
      "user_affiliation_txt, " + "version_nbr) " + "VALUES (" +
      "?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, " +
      "?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, " +
      "?, ?, ?)";
  public static final String UPDATE_CLINICALDOCUMENT =
      //1
      //2
      //3
      //4
      //5
      //6
      //7
      //8
      //9
      //10
      //11
      //12
      //13
      //                        "copy_time = ?, " +
      //14
      //15
      //16
      //17
      //                        "encounter_from_time = ?, " +
      //                        "encounter_to_time = ?, " +
      //18
      //19
      //20
      //21
      //22
      //23
      //24
      //25
      //26
      //27
      //28
      //29
      //30
      //31
      "UPDATE clinical_document SET " + "activity_duration_amt = ?, " +
      "activity_duration_unit_cd = ?, " + "activity_from_time = ?, " +
      "activity_to_time = ?, " + "add_reason_cd = ?, " +
      "add_time = ?, " + "add_user_id = ?, " + "cd = ?, " +
      "cd_desc_txt = ?, " + "confidentiality_cd = ?, " +
      "confidentiality_desc_txt = ?, " + "copy_from_time = ?, " +
      "copy_to_time = ?, " + "effective_duration_amt = ?, " +
      "effective_duration_unit_cd = ?, " + "effective_from_time = ?, " +
      "effective_to_time = ?, " + "last_chg_reason_cd = ?, " +
      "last_chg_time = ?, " + "last_chg_user_id = ?, " +
      "local_id = ?, " + "org_access_permis = ?, " +
      "practice_setting_cd = ?, " + "practice_setting_desc_txt = ?, " +
      "prog_area_access_permis = ?, " + "record_status_cd = ?, " +
      "record_status_time = ?, " + "status_cd = ?, " +
      "status_time = ?, " + "txt = ?, " + "user_affiliation_txt = ?, " +
      "version_nbr = ? " + "WHERE clinical_document_uid = ?";
  public static final String SELECT_CLINICALDOCUMENT =
      //                        "copy_time \"copyTime\", " +
      //                        "encounter_from_time \"encounterFromTime\", " +
      //                        "encounter_to_time \"encounterToTime\", " +
      "SELECT " + "clinical_document_uid \"clinicalDocumentUid\", " +
      "activity_duration_amt \"activityDurationAmt\", " +
      "activity_duration_unit_cd \"activityDurationUnitCd\", " +
      "activity_from_time \"activityFromTime\", " +
      "activity_to_time \"activityToTime\", " +
      "add_reason_cd \"addReasonCd\", " +
      "add_time \"addTime\", " +
      "add_user_id \"addUserId\", " +
      "cd \"cd\", " +
      "cd_desc_txt \"cdDescTxt\", " +
      "confidentiality_cd \"confidentialityCd\", " +
      "confidentiality_desc_txt \"confidentialityDescTxt\", " +
      "copy_from_time \"copyFromTime\", " +
      "copy_to_time \"copyToTime\", " +
      "effective_duration_amt \"effectiveDurationAmt\", " +
      "effective_duration_unit_cd \"effectiveDurationUnitCd\", " +
      "effective_from_time \"effectiveFromTime\", " +
      "effective_to_time \"effectiveToTime\", " +
      "last_chg_reason_cd \"lastChgReasonCd\", " +
      "last_chg_time \"lastChgTime\", " +
      "last_chg_user_id \"lastChgUserId\", " +
      "local_id \"localId\", " +
      "org_access_permis \"orgAccessPermis\", " +
      "practice_setting_cd \"practiceSettingCd\", " +
      "practice_setting_desc_txt \"practiceSettingDescTxt\", " +
      "prog_area_access_permis \"progAreaAccessPermis\", " +
      "record_status_cd \"recordStatusCd\", " +
      "record_status_time \"recordStatusTime\", " +
      "status_cd \"statusCd\", " +
      "status_time \"statusTime\", " +
      "txt \"txt\", " +
      "user_affiliation_txt \"userAffiliationTxt\", " +
      "version_nbr \"versionNbr\" " +
      "FROM " +
      "clinical_document WHERE clinical_document_uid = ?";
  public static final String DELETE_CLINICALDOCUMENT = "";

  public static final String SELECT_VACCINATION_SUMMARY_LIST_FOR_WORKUP =
	  "select per.person_uid \"PersonUid\", "+
      " Intervention.intervention_uid \"InterventionUid\", par2.type_cd \"par2TypeCd\", " +
      " Intervention.local_id \"LocalId\", "+
      " Intervention.activity_from_time \"ActivityFromTime\",    " +
      " Intervention.electronic_ind \"electronicInd\", "+
      " par.record_status_cd \"RecordStatusCd\", par.type_cd \"TypeCd\", "+
      " Intervention.material_cd \"Nm\",  " +
      " code1.code_short_desc_txt \"VaccineAdministered\" " +
      " from person per  with (nolock) INNER join participation par  with (nolock) "+
      " on per.person_uid = par.subject_entity_uid " +
      " and par.type_cd= 'SubOfVacc' and par.record_status_cd = 'ACTIVE' " +
      " and par.subject_class_cd = 'PAT'  Inner  JOIN intervention Intervention with (nolock) ON " +
      " par.act_uid = Intervention.intervention_uid "+
      " and Intervention.record_status_cd = 'ACTIVE' " +
      " Inner JOIN  participation par2  with (nolock) on Intervention.intervention_uid = par2.act_uid " +
      " and par2.record_status_cd='ACTIVE' and upper(par2.type_cd)= 'SUBOFVACC' " +
      " and par2.act_class_cd = 'INTV' "+
      " LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general code1  with (nolock) " +
      " ON code1.code_set_nm = 'VAC_NM' AND Intervention.material_cd = code1.code " +
      " where Intervention.record_status_cd= 'ACTIVE' AND per.person_parent_uid = ? ";
  
      /*"select per.person_uid PersonUid, "+
      " Intervention.intervention_uid InterventionUid, par2.type_cd par2TypeCd, " +
      " mt.nm Nm, Intervention.local_id LocalId, "+
      " Intervention.activity_from_time ActivityFromTime,    " +
      " par.record_status_cd RecordStatusCd, par.type_cd TypeCd, "+
      " mt.material_uid MaterialUid,  " +
      " code1.code_short_desc_txt VaccineAdministered " +
      " from person per INNER join participation par "+
      " on per.person_uid = par.subject_entity_uid " +
      " and par.type_cd= 'SubOfVacc' and par.record_status_cd = 'ACTIVE' " +
      " and par.subject_class_cd = 'PSN'  Inner  JOIN intervention Intervention ON " +
      " par.act_uid = Intervention.intervention_uid "+
      " and Intervention.record_status_cd = 'ACTIVE' " +
      " Inner JOIN  participation par2 on Intervention.intervention_uid = par2.act_uid " +
      " and par2.record_status_cd='ACTIVE' and upper(par2.type_cd)= 'VACCGIVEN' " +
      " and par2.act_class_cd = 'INTV' INNER JOIN  material mt "+
      " ON par2.subject_entity_uid =  mt.material_uid " +
      " LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      " ..code_value_general code1 " +
      " ON code1.code_set_nm = 'VAC_NM' AND mt.nm = code1.code " +
      " where Intervention.record_status_cd= 'ACTIVE' AND per.person_parent_uid = ? ";*/

  public static final String SELECT_VACCINATION_SUMMARY_LIST_FOR_WORKUP_SQL_IN =
	  " SELECT per.person_uid AS PersonUid, "+
	  " Intervention.intervention_uid AS InterventionUid, par2.type_cd AS par2TypeCd, "+
	  " Intervention.add_time AS createDate, "+ 
	  " Intervention.material_cd Nm, Intervention.local_id AS LocalId, "+
	  " Intervention.activity_from_time AS ActivityFromTime, "+
	  " Intervention.effective_from_time EffectiveFromTime, "+
	  " Intervention.electronic_ind AS electronicInd, "+
	  " par.record_status_cd AS RecordStatusCd, "+
	  " par.type_cd AS TypeCd, "+ 
	  " code1.code_short_desc_txt AS VaccineAdministered "+
	  " FROM Person  AS per with (nolock) INNER JOIN Participation AS par  with (nolock) "+
	  " ON per.person_uid = par.subject_entity_uid "+
	  " AND par.type_cd = 'SubOfVacc' AND par.record_status_cd = 'ACTIVE' "+ 
	  " AND par.subject_class_cd = 'PAT' INNER JOIN Intervention AS Intervention  with (nolock) "+
	  " ON par.act_uid = Intervention.intervention_uid "+
	  " AND Intervention.record_status_cd = 'ACTIVE' INNER JOIN "+
	  " Participation AS par2  with (nolock) ON Intervention.intervention_uid = par2.act_uid "+
	  " AND par2.record_status_cd = 'ACTIVE' "+ 
	  " AND (upper(par2.type_cd)= 'SubOfVacc'  OR upper(par2.type_cd)='VaccGiven')  AND par2.act_class_cd = 'INTV' "+
	  " LEFT OUTER JOIN "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Code_value_general AS code1  with (nolock) "+
	  " ON Intervention.material_cd = code1.code AND code1.code_set_nm = 'VAC_NM' "+
	  " WHERE  (Intervention.record_status_cd = 'ACTIVE') AND per.person_parent_uid IN ";

  public static final String GET_ASSOCIATED_VACCINE_RECORDS =
      "SELECT COUNT(*) FROM Act_relationship  with (nolock)  WHERE type_cd = '1180' "+
      " AND record_status_cd = 'ACTIVE' AND source_act_uid = ?";
  public static final String GET_ASSOCIATED_TREATMENT_RECORDS =
	      "SELECT COUNT(*) FROM Act_relationship  with (nolock) WHERE type_cd = 'TreatmentToPHC' "+
	      " AND record_status_cd = 'ACTIVE' AND source_act_uid = ?";
  public static final String SELECT_OBSERVATION_VALUE_TXT_HIST =
      "SELECT observation_uid \"ObservationUid\", "+
      " obs_value_txt_seq \"obsValueTxtSeq\", version_ctrl_nbr \"versionCtrlNbr\", " +
      "data_subtype_cd \"DataSubtypeCd\", encoding_type_cd \"EncodingTypeCd\","+
     " txt_type_cd \"TxtTypeCd\", value_image_txt \"ValueImageTxt\", "+
     "value_txt \"ValueTxt\" FROM obs_value_txt_hist WHERE observation_uid = ? "+
     " and obs_value_txt_seq = ? and version_ctrl_nbr = ?";
  public static final String INSERT_OBSERVATION_VALUE_TXT_HIST =
      "INSERT into obs_value_txt_hist(observation_uid, "+
      " obs_value_txt_seq, version_ctrl_nbr, " +
      "data_subtype_cd, encoding_type_cd, txt_type_cd, value_image_txt, "+
     "  value_txt) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String SELECT_OBSERVATION_VALUE_CODED_MOD_HIST =
      "SELECT observation_uid \"ObservationUid\", code \"Code\", "+
      " code_mod_cd \"CodeModCd\", " +
      "code_system_cd \"CodeSystemCd\", "+
      " code_system_desc_txt \"CodeSystemDescTxt\", "+
      " code_version \"CodeVersion\", display_name \"DisplayName\", "+
      " original_txt \"OriginalTxt\" FROM obs_value_coded_mod_hist "+
      " WHERE observation_uid = ? and code = ? and code_mode_cd = ? "+
      " and version_ctrl_nbr = ?";
  public static final String INSERT_OBSERVATION_VALUE_CODED_MOD_HIST =
      "INSERT into obs_value_coded_mod_hist(observation_uid, code, "+
      " code_mod_cd, version_ctrl_nbr, " +
      "code_system_cd, code_system_desc_txt, code_version, "+
      " display_name, original_txt) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String SELECT_OBSERVATION_VALUE_CODED_HIST =
      "SELECT  observation_uid \"ObservationUid\", code \"Code\", " +
      "code_system_cd \"CodeSystemCd\", "+
      " code_system_desc_txt \"CodeSystemDescTxt\","+
     " code_version \"CodeVersion\", display_name \"DisplayName\", "+
     " original_txt \"OriginalTxt\", alt_cd \"altCd\", "+
     " alt_cd_desc_txt \"altCdDescTxt\", alt_cd_system_cd \"altCdSystemCd\", "+
     " alt_cd_system_desc_txt \"altCdSystemDescTxt\", "+
     " code_derived_ind \"codeDerivedInd\" FROM obs_value_coded_hist "+
    " WHERE observation_uid = ? and code = ? and version_ctrl_nbr = ?";
  public static final String INSERT_OBSERVATION_VALUE_CODED_HIST =
      "INSERT into obs_value_coded_hist(observation_uid, code, version_ctrl_nbr, " +
      "code_system_cd, code_system_desc_txt, code_version, display_name, "+
     " original_txt,alt_cd, alt_cd_desc_txt, alt_cd_system_cd, "+
     "alt_cd_system_desc_txt, code_derived_ind) "+
     " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String SELECT_OBSERVATION_INTERP_HIST =
      "SELECT observation_uid \"ObservationUid\", "+
      " interpretation_cd \"InterpretationCd\", " +
      "interpretation_txt \"InterpretationDescTxt\" "+
      "from observation_interp_hist where observation_uid = ? "+
      " and interpretation_cd = ? and version_ctrl_nbr = ?";
  public static final String INSERT_OBSERVATION_INTERP_HIST =
      "INSERT into observation_interp_hist(observation_uid, "+
      " interpretation_cd, version_ctrl_nbr, " +
      "interpretation_txt) VALUES(?, ?, ?, ?)";
  public static final String SELECT_OBSERVATION_HIST =
      "SELECT observation_uid \"ObservationUid\", " +
      " activity_duration_amt \"ActivityDurationAmt\", "+
      " activity_duration_unit_cd \"ActivityDurationUnitCd\", "+
      " activity_from_time \"ActivityFromTime\", "+
      " activity_to_time \"ActivityToTime\", add_reason_cd \"AddReasonCd\","+
      " add_time \"AddTime\", add_user_id \"AddUserId\", cd \"Cd\", "+
      " cd_desc_txt \"CdDescTxt\", cd_system_cd \"CdSystemCd\", "+
      " cd_system_desc_txt \"CdSystemDescTxt\", " +
      " confidentiality_cd \"ConfidentialityCd\", "+
      " confidentiality_desc_txt \"ConfidentialityDescTxt\", "+
      " ctrl_cd_display_form \"CtrlCdDisplayForm\", "+
      " ctrl_cd_user_defined_1 \"CtrlCdUserDefined1\", "+
      " ctrl_cd_user_defined_2 \"CtrlCdUserDefined2\", "+
      " ctrl_cd_user_defined_3 \"CtrlCdUserDefined3\", "+
      " ctrl_cd_user_defined_4 \"CtrlCdUserDefined4\", "+
      " derivation_exp \"DerivationExp\", " +
      " effective_duration_amt \"EffectiveDurationAmt\", "+
      " effective_duration_unit_cd \"EffectiveDurationUnitCd\", "+
      " effective_from_time EffectiveFromTime,"+
      " effective_to_time EffectiveToTime, "+
      " electronic_ind ElectronicInd, group_level_cd GroupLevelCd, "+
      " jurisdiction_cd JurisdictionCd, "+
      " last_chg_reason_cd LastChgReasonCd, "+
      " last_chg_time LastChgTime, last_chg_user_id LastChgUserId, " +
      " lab_condition_cd \"LabConditionCd\", local_id \"LocalId\", "+
      " obs_domain_cd \"ObsDomainCd\", obs_domain_cd_st_1 \"ObsDomainCdSt1\", "+
      " org_access_permis \"OrgAccessPermis\", pnu_cd \"PnuCd\", "+
      " priority_cd \"PriorityCd\", priority_desc_txt \"PriorityDescTxt\", "+
      " prog_area_access_permis \"ProgAreaAccessPermis\", " +
      " prog_area_cd \"ProgAreaCd\", record_status_cd \"RecordStatusCd\", "+
      " record_status_time \"RecordStatusTime\", repeat_nbr \"RepeatNbr\", "+
      " status_cd \"StatusCd\", status_time \"StatusTime\","+
      " subject_person_uid \"SubjectPersonUid\", "+
      " target_site_cd \"TargetSiteCd\","+
      " target_site_desc_txt \"TargetSiteDescTxt\", txt \"Txt\", " +
      " user_affiliation_txt \"UserAffiliationTxt\", value_cd \"ValueCd\","+
      " ynu_cd \"YnuCd\" FROM observation_hist "+
      " WHERE observation_uid = ? and version_ctrl_nbr = ?";
  public static final String INSERT_OBSERVATION_HIST =
      "INSERT into observation_hist (observation_uid, version_ctrl_nbr, " +
      " activity_duration_amt, activity_duration_unit_cd , activity_from_time, "+
      " activity_to_time, add_reason_cd, " +
      " add_time, add_user_id, cd, cd_desc_txt, cd_system_cd,"+
      "  cd_system_desc_txt, confidentiality_cd, confidentiality_desc_txt, " +
      " ctrl_cd_display_form, ctrl_cd_user_defined_1, ctrl_cd_user_defined_2, "+
      " ctrl_cd_user_defined_3, " +
      " ctrl_cd_user_defined_4, derivation_exp, effective_duration_amt, "+
      " effective_duration_unit_cd, effective_from_time, " +
      " effective_to_time, electronic_ind, group_level_cd,"+
      " jurisdiction_cd, last_chg_reason_cd, last_chg_time, "+
      " last_chg_user_id, lab_condition_cd, " +
      " local_id, obs_domain_cd, obs_domain_cd_st_1, org_access_permis, "+
      " pnu_cd, priority_cd, priority_desc_txt, prog_area_access_permis, " +
      " prog_area_cd, record_status_cd, record_status_time, repeat_nbr, "+
      " status_cd, status_time, subject_person_uid, target_site_cd, " +
      " target_site_desc_txt, txt, user_affiliation_txt, value_cd, ynu_cd) " +
      " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+
      " ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  public static final String SELECT_SEQ_NUMBER_HIST =
      "SELECT version_ctrl_nbr from observation_hist where observation_uid = ?";
  public static final String SELECT_OBSERVATION_REASON_HIST =
      "SELECT observation_uid \"ObservationUid\", reason_cd \"ReasonCd\", " +
      "reason_desc_txt \"ReasonDescTxt\" FROM observation_reason_hist "+
      " WHERE observation_uid = ? and reason_cd = ? and version_ctrl_nbr = ?";
  public static final String INSERT_OBSERVATION_REASON_HIST =
      "INSERT into observation_reason_hist(observation_uid, reason_cd, "+
      " version_ctrl_nbr, reason_desc_txt) VALUES(?, ?, ?, ?)";
  public static final String SELECT_OBSERVATION_VALUE_NUMERIC_HIST =
      "SELECT observation_uid \"ObservationUid\", "+
      "observation_value_numeric_seq \"ObsValueNumericSeq\", " +
      "high_range \"HighRange\", low_range  \"LowRange\", "+
      "comparator_cd_1 \"ComparatorCd1\", numeric_value_1 \"NumericValue1\", "+
      "numeric_value_2 \"NumericValue2\", numeric_unit_cd \"NumericUnitCd\","+
      "numeric_scale_1 \"NumericScale1\", numeric_scale_2 \"NumericScale2\","+
      "separator_cd \"SeparatorCd\" FROM obs_value_numeric_hist "+
      "WHERE observation_uid = ? and " +
      "obs_value_numeric_seq = ? and version_ctrl_nbr = ?";
  public static final String INSERT_OBSERVATION_VALUE_NUMERIC_HIST =
      "INSERT into obs_value_numeric_hist(observation_uid, "+
      "obs_value_numeric_seq, version_ctrl_nbr, " +
      "high_range, low_range, comparator_cd_1, numeric_value_1, "+
      "numeric_value_2, numeric_scale_1,numeric_scale_2,numeric_unit_cd, separator_cd) "+
      " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
  public static final String SELECT_OBSERVATION_VALUE_DATE_HIST =
      "SELECT observation_uid \"ObservationUid\","+
     " obs_value_date_seq \"ObsValueDateSeq\", " +
      "duration_amt \"DurationAmt\", duration_unit_cd \"DurationUnitCd\", "+
      " from_time \"FromTime\", to_time \"ToTime\" "+
      " FROM obs_value_date_hist WHERE observation_uid = ? "+
      " and obs_value_date_seq = ? and version_ctrl_nbr = ?";
  public static final String INSERT_OBSERVATION_VALUE_DATE_HIST =
      "INSERT into obs_value_date_hist(observation_uid, "+
      " obs_value_date_seq, version_ctrl_nbr, " +
      "duration_amt, duration_unit_cd, from_time, to_time) "+
      " VALUES(?, ?, ?, ?, ?, ?, ?)";
  public static final String SELECT_PUBLIC_HEALTH_CASE_HIST =
      "SELECT public_health_case_uid \"PublicHealthCaseUid\", version_ctrl_nbr, " +
      " activity_duration_amt \"ActivityDurationAmt\", "+
      " activity_duration_unit_cd \"ActivityDurationUnitCd\","+
      " activity_from_time \"ActivityFromTime\", "+
      " activity_to_time \"ActivityToTime\", add_reason_cd \"AddReasonCd\","+
      " add_time \"AddTime\", add_user_id \"AddUserId\", cd \"Cd\", "+
      " cd_desc_txt \"CdDescTxt\", cd_system_cd \"CdSystemCd\", " +
      " cd_system_desc_txt \"CdSystemDescTxt\", case_class_cd \"CaseClassCd\", "+
      " confidentiality_cd \"ConfidentialityCd\", "+
      " confidentiality_desc_txt \"ConfidentialityDescTxt\", "+
      " detection_method_cd \"DetectionMethodCd\", "+
      " detection_method_desc_txt \"DetectionMethodDescTxt\", "+
      " diagnosis_time \"DiagnosisDate\", "+
      " disease_imported_cd \"DiseaseImportedCd\", " +
      " disease_imported_desc_txt \"DiseaseImportedDescTxt\", "+
      " effective_duration_amt \"EffectiveDurationAmt\", "+
      " effective_duration_unit_cd \"EffectiveDurationUnitCd\", "+
      " effective_from_time \"EffectiveFromTime\", "+
      " effective_to_time \"EffectiveToTime\", group_case_cnt \"GroupCaseCnt\","+
      "  investigation_status_cd \"InvestigationStatusCd\", "+
      " jurisdiction_cd \"JurisdictionCd\", " +
      " last_chg_reason_cd \"LastChgReasonCd\", last_chg_time \"LastChgTime\","+
      " last_chg_user_id \"LastChgUserId\", local_id \"LocalId\", "+
      " mmwr_year \"MmwrYear\", mmwr_week \"MmwrWeek\", "+
      " org_access_permis \"OrgAccessPermis\", outbreak_ind \"OutbreakInd\", "+
      " outbreak_from_time \"OutbreakFromTime\", "+
      " outbreak_to_time \"OutbreakToTime\", outbreak_name \"OutbreakName\", " +
      " outcome_cd \"OutcomeCd\", pat_age_at_onset \"PatAgeAtOnset\", "+
      " pat_age_at_onset_unit_cd \"PatAgeAtOnsetUnitCd\", "+
      " patient_group_id \"PatientGroupId\", "+
      " prog_area_access_permis \"ProgAreaAccessPermis\", "+
      " prog_area_cd \"ProgAreaCd\", record_status_cd \"RecordStatusCd\", "+
      " record_status_time \"RecordStatusTime\", repeat_nbr \"RepeatNbr\", " +
      " rpt_cnty_cd \"RptCntyCd\", rpt_form_cmplt_time \"RptFormCmpltTime\","+
      " rpt_source_cd \"RptSourceCd\", "+
      " rpt_source_cd_desc_txt \"RptSourceDescTxt\", "+
      " rpt_to_county_time \"RptToCountyTime\", "+
      " rpt_to_state_time \"RptToStateTime\", status_cd \"StatusCd\", "+
      " status_time \"StatusTime\","+
      " transmission_mode_cd \"TransmissionModeCd\", "+
      " transmission_mode_desc_txt \"TransmissionModeDescTxt\", " +
      " txt \"Txt\", user_affiliation_txt \"UserAffiliationTxt\" "+
      " FROM public_health_case_hist WHERE public_health_case_uid = ? "+
      " and version_ctrl_nbr = ?";
  public static final String INSERT_PUBLIC_HEALTH_CASE_HIST =
      "INSERT into public_health_case_hist(public_health_case_uid, "+
      " version_ctrl_nbr, " +
      " activity_duration_amt, activity_duration_unit_cd, activity_from_time, "+
      " activity_to_time, add_reason_cd, add_time, add_user_id, cd, "+
      " cd_desc_txt, cd_system_cd, " +
      " cd_system_desc_txt, case_class_cd, confidentiality_cd, "+
      " confidentiality_desc_txt, detection_method_cd, "+
      " detection_method_desc_txt, diagnosis_time, disease_imported_cd, " +
      " disease_imported_desc_txt, effective_duration_amt, "+
      " effective_duration_unit_cd, effective_from_time, "+
      " effective_to_time, group_case_cnt, investigation_status_cd, "+
      " jurisdiction_cd, " +
      " last_chg_reason_cd, last_chg_time, last_chg_user_id, local_id, "+
      " mmwr_year, mmwr_week, org_access_permis, outbreak_ind, "+
      " outbreak_from_time, outbreak_to_time, outbreak_name, " +
      " outcome_cd, pat_age_at_onset, pat_age_at_onset_unit_cd, "+
      " patient_group_id, prog_area_access_permis, prog_area_cd, "+
      " record_status_cd, record_status_time, repeat_nbr, " +
      " rpt_cnty_cd, rpt_form_cmplt_time, rpt_source_cd, "+
      " rpt_source_cd_desc_txt, rpt_to_county_time, rpt_to_state_time, "+
      " status_cd, status_time, transmission_mode_cd, transmission_mode_desc_txt, " +
      " txt, user_affiliation_txt) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+
      " ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+
      " ?,?,?,?,?,?,?,?,?)";
  public static final String SELECT_PHC_SEQ_NUMBER_HIST =
      "SELECT version_ctrl_nbr from public_health_case_hist "+
      " where public_health_case_uid = ?";
  public static final String SELECT_CONFIRMATION_METHOD_HIST =
      "SELECT public_health_case_uid \"PublicHealthCaseUid\", "+
      " confirmation_method_cd \"ConfirmationMethodCd\", "+
      " confirmation_method_desc_txt \"ConfirmationMethodDescTxt\", "+
      " confirmation_method_time \"ConfirmationMethodTime\" " +
      " FROM confirmation_method_hist WHERE public_health_case_uid = ? "+
      " and confirmation_method_cd = ? and version_ctrl_nbr = ?";
  public static final String INSERT_CONFIRMATION_METHOD_HIST =
      "INSERT into confirmation_method_hist( " +
      "public_health_case_uid, confirmation_method_cd, version_ctrl_nbr, "+
      "confirmation_method_desc_txt, confirmation_method_time) " +
      "VALUES(?,?,?,?,?)";
  public static final String PhcPersonViewQueryString =
      "SELECT " + "PHC.public_health_case_uid,  " +
      "PER.person_uid,           " +
      "PHC.diagnosis_time AS diagnosis_date,      " +
      "PHC.cd AS PHC_code,                        " +
      "PHC.cd_desc_txt AS PHC_code_desc,   " +
      "PHC.case_class_cd,               " +
      "PHC.cd_system_cd,                " +
      "PHC.cd_system_desc_txt,          " +
      "PHC.detection_method_cd,         " +
      "PHC.detection_method_desc_txt,     " +
      "PHC.disease_imported_cd,         " +
      "PHC.disease_imported_desc_txt,   " +
      "PHC.group_case_cnt,             " +
      "PHC.investigation_status_cd,    " +
      "PHC.jurisdiction_cd,            " +
      "PHC.mmwr_week,                  " +
      "PHC.mmwr_year,                  " +
      "PHC.outbreak_ind,              " +
      "PHC.outbreak_from_time,        " +
      "PHC.outbreak_to_time,          " +
      "PHC.outbreak_name,             " +
      "PHC.outcome_cd,                " +
      "PHC.pat_age_at_onset,          " +
      "PHC.pat_age_at_onset_unit_cd,  " +
      "PHC.prog_area_cd,              " +
      "PHC.record_status_cd,         " +
      "PHC.rpt_cnty_cd,              " +
      "PHC.rpt_source_cd,            " +
      "PHC.rpt_to_state_time,        " +
      "PHC.status_cd,                " +
      "PER.adults_in_house_nbr,   " +
      "PER.age_category_cd,       " +
      "PER.age_reported,           " +
      "PER.age_reported_time,     " +
      "PER.age_reported_unit_cd,   " +
      "PER.birth_gender_cd,     " +
      "PER.birth_order_nbr,     " +
      "PER.birth_time,          " +
      "PER.birth_time_calc,      " +
      "PER.cd,                    " +
      "PER.cd_desc_txt AS Person_code_desc,     " +
      "PER.children_in_house_nbr,  " +
      "PER.curr_sex_cd,       " +
      "PER.deceased_ind_cd,  " +
      "PER.deceased_time,  " +
      "PER.education_level_cd,  " +
      "PER.ethnic_group_ind,  " +
      "PER.occupation_cd, " +
      "PER.local_id as Person_local_id " +
      "FROM  Public_health_case PHC, Participation PAR,  Person PER " +
      " WHERE PAR.act_uid = PHC.public_health_case_uid " +
      "AND PAR.type_cd = '" +
      NEDSSConstants.PHC_PATIENT +
      "'" +
      " AND PER.person_uid = PAR.subject_entity_uid  " +
      " AND PHC.public_health_case_uid = ";
  public static final String LocatorQueryString =
      "SELECT " + "Postal_locator.city_cd, " + "Postal_locator.cntry_cd, " +
      "Postal_locator.cnty_cd, " +
      "Postal_locator.state_cd, " +
      "Postal_locator.zip_cd, " +
      "Participation.type_cd " +
      "FROM Public_health_case, Participation, "+
      " Entity_locator_participation, Postal_locator " +
      " WHERE Public_health_case.public_health_case_uid = Participation.act_uid " +
      " AND Participation.subject_entity_uid = Entity_locator_participation.entity_uid " +
      " AND  Entity_locator_participation.locator_uid = Postal_locator.postal_locator_uid " +
      " AND (Public_health_case.public_health_case_uid = ?) "+
      " AND (Entity_locator_participation.class_cd = '" +
      NEDSSConstants.POSTAL +
      "') ";
  public static final String RaceQuery =
      "SELECT Person_race.race_category_cd " +
      "FROM  Person, Person_race WHERE Person.person_uid = Person_race.person_uid " +
      " AND (Person.person_uid = ?)";
  public static final String EthnicQuery =

      "SELECT Person_ethnic_group.ethnic_group_cd FROM  Person, Person_ethnic_group " +
      " WHERE Person.person_uid = Person_ethnic_group.person_uid " +
      " AND (Person.person_uid = ?) ";
  public static final String ASSOC_MORB_COUNT =
      "Select count(*) from act_relationship act " +
      "inner join observation obs on act.source_act_uid = obs.observation_uid " +
      "and act.record_status_cd = 'ACTIVE' " +
      "and act.source_class_cd = 'OBS' " +
      "and act.target_class_cd = 'CASE' " +
      "and act.type_cd = 'MorbReport' " +
      "where obs.observation_uid = ? ";


  public static final String ASSOC_LAB_COUNT =
      "Select count(*) from act_relationship act, observation obs " +
      "where act.source_act_uid = obs.observation_uid " +
      "and act.record_status_cd = 'ACTIVE' " +
      "and act.source_class_cd = 'OBS' " +
      "and act.target_class_cd = 'CASE' " +
      "and act.type_cd = 'LabReport' " +
      "and obs.observation_uid = ?";

  public static final String SELECT_TREATMENT_SUMMARY_LIST_FOR_WORKUP_ORACLE =
		     "SELECT Treatment.treatment_uid \"treatmentUid\", " +
		     "Treatment.cd_desc_txt \"customTreatmentNameCode\", " +
		     "Treatment.local_id \"localId\", " +
		     " treatment_administered.effective_from_time  \"activityFromTime\"  "+
		     "FROM  "+ DataTables.TREATMENT_TABLE +" with (nolock) , treatment_administered treatment_administered  with (nolock) " + " WHERE Treatment.Treatment_uid in " +
		    "(SELECT act_uid FROM Participation  with (nolock) WHERE Participation.subject_entity_uid in (select person_uid from person  with (nolock) where "+
		    " person.PERSON_PARENT_UID = ?) "+
		    " AND  Participation.type_cd = 'SubjOfTrmt' "+
		    " AND Participation.record_status_cd = 'ACTIVE' "+
		    " AND Participation.act_class_cd = 'TRMT' "+
		    " AND treatment.treatment_uid=treatment_administered.treatment_uid " +
		    " AND Participation.subject_class_cd = 'PSN') AND Treatment.record_status_cd = 'ACTIVE' ";
  
  public static final String SELECT_TREATMENT_SUMMARY_LIST_FOR_WORKUP_ORACLE_IN =
	      "SELECT per.person_uid \"personUid\", " +
	      "Treatment.treatment_uid \"treatmentUID\", " +
	      "Treatment.cd \"treatmentNameCode\", " +
	      "Treatment.cd_desc_txt \"customTreatmentNameCode\", " +
	      "Treatment.local_id \"localId\", " +
	      "Treatment.activity_from_time \"activityFromTime\", " +
	      "par.record_status_cd \"recordStatusCd\", " +
	      "par.type_cd \"typeCd\", "  +
	      "ar.type_cd \"arTypeCd\", "  +
	      "ar.target_act_uid \"parentUid\" "  +
	      "FROM " +    DataTables.PERSON_TABLE + " per  with (nolock) , " +
	                   DataTables.PARTICIPATION_TABLE + " par  with (nolock) , " +
	                   DataTables.ACT_RELATIONSHIP + " ar  with (nolock) , " +
	                   DataTables.TREATMENT_TABLE + " Treatment  with (nolock) " +
	      "WHERE       per.person_uid = par.subject_entity_uid " +
	      "AND         ar.source_act_uid = Treatment.treatment_uid " +
	      "AND         par.type_cd = '" + NEDSSConstants.SUBJECT_OF_TREATMENT + "' " +
	      "AND         upper(par.record_status_cd) = 'ACTIVE' " +
	      "AND         par.subject_class_cd = '" + NEDSSConstants.PERSON + "' " +
	      "AND         par.act_uid = Treatment.treatment_uid " +
	      "AND         Treatment.record_status_cd= 'ACTIVE' " +
	      "AND         per.person_uid IN ";

  
     public static final String SELECT_TREATMENT_SUMMARY_LIST_FOR_MORB =
      "SELECT " +
      "Treatment.treatment_uid \"treatmentUid\", " +
      "Treatment.cd \"treatmentNameCode\", " +
      "Treatment.cd_desc_txt \"customTreatmentNameCode\", " +
      "Treatment.local_id \"localId\", " +
      "Treatment.activity_to_time \"activityToTime\" " +
      "FROM " +
             DataTables.ACT_RELATIONSHIP + " ar  with (nolock) , " +
             DataTables.TREATMENT_TABLE + " Treatment  with (nolock) " +
      "WHERE " +
      "ar.source_act_uid = Treatment.treatment_uid " +
            "AND         Treatment.record_status_cd= 'ACTIVE' " +
      "AND         Treatment.treatment_uid IN ";

  public static final String SELECT_TREATMENT_SUMMARY_LIST_FOR_WORKUP_SQL =
      "SELECT per.person_uid PersonUid, " +
      "Treatment.treatment_uid TreatmentUID,  " +
      "Treatment.local_id LocalId, " +
      "Treatment.activity_from_time ActivityFromTime,    " +
      "par.record_status_cd RecordStatusCd, " +
      "par.type_cd TypeCd  " +
      "FROM " +    DataTables.PERSON_TABLE + " per, " +
                   DataTables.PARTICIPATION_TABLE + " par, " +
                   DataTables.TREATMENT_TABLE + " Treatment " +
      "WHERE       per.person_uid = par.subject_entity_uid " +
      "AND         par.type_cd= '" + NEDSSConstants.SUBJECT_OF_TREATMENT + "' " +
      "AND         par.record_status_cd = 'ACTIVE' " +
      "AND         par.subject_class_cd = '" + NEDSSConstants.PERSON + "' " +
      "AND         par.act_uid = Treatment.treatment_uid " +
      "AND         Treatment.record_status_cd = 'ACTIVE' " +
      "AND         Treatment.record_status_cd= 'ACTIVE' AND per.person_uid = ? ";

  public static final String SELECT_TREATMENT_SUMMARY_LIST_FOR_WORKUP_SQL_IN =
      "SELECT per.person_uid PersonUid, " +
      "Treatment.treatment_uid TreatmentUID, " +
      "Treatment.local_id LocalId, " +
      "Treatment.activity_from_time ActivityFromTime, " +
      "par.record_status_cd RecordStatusCd, " +
      "par.type_cd TypeCd " +
      "FROM " +    DataTables.PERSON_TABLE + " per, " +
                   DataTables.PARTICIPATION_TABLE + " par, " +
                   DataTables.TREATMENT_TABLE + " Treatment " +
      "WHERE       per.person_uid = par.subject_entity_uid " +
      "AND         par.type_cd= '" + NEDSSConstants.SUBJECT_OF_TREATMENT + "' "+
      "AND         par.record_status_cd = 'ACTIVE' " +
      "AND         par.subject_class_cd = '" + NEDSSConstants.PERSON + "' " +
      "AND         par.act_uid = Treatment.treatment_uid " +
      "AND         Treatment.record_status_cd = 'ACTIVE' " +
      "AND         Treatment.record_status_cd= 'ACTIVE' " +
      "AND         per.person_uid IN ";

  public static final String TREATMENTS_FOR_A_PHC_ORACLE =
	      "SELECT       phc.public_health_case_uid \"phcUid\", " +
	      "Treatment.treatment_uid \"treatmentUid\", " +
	      "Treatment.cd \"treatmentNameCode\", " +
	      "Treatment.cd_desc_txt \"customTreatmentNameCode\", " +
	      "Treatment.activity_from_time \"activityFromTime\",  " +
	      "Treatment.local_id \"localId\" " +
	      "FROM " +     DataTables.PUBLIC_HEALTH_CASE_TABLE + " phc with (nolock) , " +
	                    DataTables.ACT_RELATIONSHIP + " ar with (nolock) , " +
	                    DataTables.TREATMENT_TABLE + " Treatment with (nolock) , " +
	                    DataTables.PARTICIPATION_TABLE + " par  with (nolock) " +
	      "WHERE        phc.public_health_case_uid = ar.target_act_uid " +
	      "AND          UPPER(ar.record_status_cd) = 'ACTIVE' " +
	      "AND          UPPER(Treatment.record_status_cd) = 'ACTIVE' " +
	      "AND          ar.source_class_cd = '" + NEDSSConstants.TREATMENT_CLASS_CODE + "' " +
	      "AND          ar.target_class_cd = '" + NEDSSConstants.PUBLIC_HEALTH_CASE_CLASS_CODE + "' " +
	      "AND          Treatment.treatment_uid = ar.source_act_uid " +
	      "AND          par.act_uid =  Treatment.treatment_uid " +
	      "AND          par.act_class_cd = '" + NEDSSConstants.TREATMENT_CLASS_CODE + "' " +
	      "AND          par.record_status_cd = 'ACTIVE' " +
	      "AND          phc.public_health_case_uid = ? ";

  public static final String
      TREATMENTS_MORB_OBSERVATION_SUMMARY_LIST_FOR_WORKUP_SQL =
      /* + "par.type_cd \"ParTypeCd\", "
       + "par.record_status_cd \"RecordStatusCd\" " */

      "select  obs.observation_uid \"ObservationUid\", " +
      "obs.add_time \"AddTime\", " +
      "obs.ctrl_cd_display_form \"CtrlCdDisplayForm\", " +
      "obs.local_id \"LocalId\", " +
      "obsd.from_time \"FromTime\"," +
      "code1.code_desc_txt \"MorbidityReportType\" ," +
      "code1.code_short_desc_txt \"MorbidityReportTypeSRTText\", " +
      "code2.condition_desc_txt \"MorbidityConditionSRTText\" " +
      "from person per  with (nolock) " +
      "INNER  JOIN  participation par  with (nolock) on per.person_uid = par.subject_entity_uid " +
      "and par.act_class_cd = '" + NEDSSConstants.PART_ACT_CLASS_CD +
      "' and upper(par.record_status_cd) = '" +
      NEDSSConstants.PAR_RECORD_STATUS_CD +
      "' and par.type_cd = 'SubjOfMorbReport'" +
      " and par.subject_class_cd = '" +
      NEDSSConstants.PAR_SUB_CLASS_CD +
      "' INNER  JOIN observation obs  with (nolock) ON par.act_uid = obs.observation_uid " +
      "and obs.record_status_cd in( '" +
      NEDSSConstants.OBS_PROCESSED + "'" +
      ")" +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..condition_code code2  with (nolock) ON " +
      " code2.condition_codeset_nm = 'PHC_TYPE' " +
      " and  obs.cd= code2.condition_cd " +
      "LEFT OUTER JOIN Obs_value_coded obsv  with (nolock) on " +
      "obsv.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN Obs_value_date obsd  with (nolock) on " +
      "obsd.observation_uid = obs.observation_uid " +
      "LEFT OUTER JOIN " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general code1  with (nolock) ON " +
      "code1.code_set_nm = 'MORB_RPT_TYPE'  and  obsv.code = code1.code " +
      // " and test_type_cd = 'O' " +
      " where  obs.Ctrl_Cd_Display_Form = 'MorbReport' " +
      "and per.person_uid = ? ";

  public static final String SELECT_MORBSUMMARY_FORWORKUP =
          "select local_id \"localId\", observation_uid \"observationUid\", cd \"condition\", activity_to_time \"reportDate\", activity_from_time \"activityFromTime\", rpt_to_state_time \"dateReceived\", "+
          " prog_area_cd \"programArea\", jurisdiction_cd \"jurisdiction\", processing_decision_cd \"processingDecisionCd\",  "+
          " record_status_cd \"recordStatusCd\" " +
          " from observation  with (nolock) where observation_uid = ?";

  public static final String SELECT_MORBSUMMARY_FORWORKUPNEW =
          "SELECT participation.act_uid \"uid\", " +
        "OBS.local_id \"LocalId\", OBS.jurisdiction_cd \"Jurisdiction\", " +
        "OBS.status_cd \"Status\", OBS.record_status_cd \"RecordStatusCd\", " +
        "OBS.cd_desc_txt \"OrderedTest\", " +
        "OBS.observation_uid \"ObservationUid\", OBS.prog_area_cd \"ProgramArea\", " +
        "OBS.RECORD_STATUS_CD \"recordStatusCode\", " +
        "OBS.add_time \"DateReceived\", " +
        "OBS.activity_from_time \"activityFromTime\", "+
        "OBS.cd_system_cd \"cdSystemCd\", " +
        "OBS.effective_from_time \"DateCollected\", "+
        "OBS.processing_decision_cd \"processingDecisionCd\", "+
        "OBS.cd \"condition\", OBS.activity_to_time \"reportDate\" "+
        "FROM observation OBS  with (nolock) , person with (nolock) , " +
        "participation  with (nolock) " +
        "WHERE " +
        "OBS.observation_uid=participation.act_uid " +
        "AND participation.subject_entity_uid=person.person_uid " +
        "AND participation.type_cd='SubjOfMorbReport' " +
        "AND Participation.act_class_cd = 'OBS' " +
        "AND Participation.subject_class_cd = 'PSN'   " +
        "AND Participation.record_status_cd = 'ACTIVE' " +
        "AND person_parent_uid = ?";
  public static final String SELECT_MORBSTATUS_FORWORKUP =
      "SELECT  c.CODE \"reportType\" " +
      " FROM " +
      " act_relationship a, " +
      " observation b, " +
      " obs_value_coded c " +
      " WHERE " +
      " b.observation_uid = a.source_act_uid and " +
      " c.observation_uid = b.observation_uid and " +
     " b.CD = 'MRB100' and   a.target_act_uid = ? ";
  public static final String SELECT_TREATMENT_TARGETS =
          "select source_act_uid \"uid\" from act_relationship where record_status_cd = 'ACTIVE' and type_cd = 'TreatmentToMorb' and target_act_uid = ?";

  public static final String SELECT_LAB_TARGETS =
          "select source_act_uid \"uid\" from act_relationship where record_status_cd = 'ACTIVE' and type_cd = 'LabReport' and target_act_uid = ?";

  public static final String SELECT_LAB_TARGETS_FOR_MORB =
          "select CAST(TARGET_ACT_UID AS VARCHAR(20)) + '|' + CAST(source_act_uid AS VARCHAR(20)) \"uniqueMapKey\", source_act_uid \"uid\" , target_act_uid \"linkingUid\" from act_relationship with(nolock) where record_status_cd = 'ACTIVE' and type_cd = 'LabReport' and target_act_uid in";

  
  public static final String SELECT_OBSERVATIONMORBUIDS_FOR_WORKUP_IN =
          /*"SELECT act_uid \"uid\" FROM Participation, Observation " +
          "WHERE Participation.ACT_UID = Observation.OBSERVATION_UID "+
          "AND Participation.act_class_cd = 'OBS' "+
          "AND Participation.type_cd= 'SubjOfMorbReport' "+
          "AND Participation.subject_class_cd = 'PSN' " +
          "AND Participation.record_status_cd = 'ACTIVE' " +
          "AND Observation.RECORD_STATUS_CD NOT IN ('LOG_DEL') " +
          "AND Participation.subject_entity_uid IN ";*/
           "SELECT participation.act_uid \"uid\" " +
         "FROM observation OBS, person, participation WHERE " +
         "OBS.observation_uid=participation.act_uid " +
         "AND participation.subject_entity_uid=person.person_uid " +
         "AND participation.type_cd='SubjOfMorbReport' " +
         "AND Participation.act_class_cd = 'OBS' " +
         "AND Participation.subject_class_cd = 'PSN' " +
         "AND Participation.record_status_cd = 'ACTIVE' " +
         "AND OBS.RECORD_STATUS_CD NOT IN ('LOG_DEL') " +
          "AND person_parent_uid = ";


     public static final String SELECT_OBSERVATIONMORBUIDS_FOR_SUMMARY_WORKUP_IN =
         /* "SELECT act_uid \"uid\" FROM Participation, Observation " +
          "WHERE Participation.ACT_UID = Observation.OBSERVATION_UID "+
          "AND Participation.act_class_cd = 'OBS' "+
          "AND Participation.type_cd= 'SubjOfMorbReport' "+
          "AND Participation.subject_class_cd = 'PSN' " +
          "AND Participation.record_status_cd = 'ACTIVE' " +
          "AND Observation.RECORD_STATUS_CD IN ('UNPROCESSED') " +
          "AND Participation.subject_entity_uid IN ";*/
         "SELECT participation.act_uid \"uid\" " +
         "FROM observation OBS, person, " +
         "participation WHERE " +
         "OBS.observation_uid=participation.act_uid " +
         "AND participation.subject_entity_uid=person.person_uid " +
         "AND participation.type_cd='SubjOfMorbReport' " +
         "AND Participation.act_class_cd = 'OBS' " +
         "AND Participation.subject_class_cd = 'PSN' " +
         "AND Participation.record_status_cd = 'ACTIVE' " +
         "AND OBS.RECORD_STATUS_CD IN ('UNPROCESSED') " +
          "AND person_parent_uid = ";


      public static final String REVERSE_TRANS_LABID_LABTEXTDESCTXT_PA_CONDDESCTXT_SQL = "SELECT lab_test_cd str1 FROM " +
          NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..labtest_progarea_mapping WHERE laboratory_id = ? "+
         " AND lab_test_desc_txt = ? AND test_type_cd = ? "+
         " AND prog_area_cd = ? ";

     public static final String REVERSE_TRANSSQL2_ORACLE ="SELECT lab_test_cd \"str1\" FROM "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".Lab_test a " +
         " WHERE laboratory_id =  ? AND lab_test_desc_txt = ? "+
         " AND (test_type_cd = ? ) "+
         " AND NOT exists( SELECT 1 FROM "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".labtest_progarea_mapping b "+
                        " WHERE a.lab_test_cd = b.lab_test_cd )";
     public static final String REVERSE_TRANSSQL2_SQL ="SELECT  lab_test_cd \"str1\" FROM "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Lab_test a " +
         " WHERE laboratory_id =  ? AND lab_test_desc_txt = ? "+
         " AND (test_type_cd = ? ) "+
         " AND NOT exists( SELECT 1 FROM "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..labtest_progarea_mapping b "+
                       " WHERE a.lab_test_cd = b.lab_test_cd )";

     public static final String GET_SOURCE_ACT_UID_FOR_SUSCEPTIBILITES_SQL = "SELECT distinct " +
                "act.source_act_uid \"uid\" " +
                "FROM " +
                "act_relationship act " +
                "WHERE ( (act.target_class_cd = 'OBS') " +
                "AND (act.type_cd = ?) " +
                "AND (act.source_class_cd = 'OBS') " +
                "AND (act.record_status_cd = 'ACTIVE') " +
                "AND (act.TARGET_ACT_UID = ?) )";
        
  public static final String SELECT_LABSUSCEPTIBILITES_REFLEXTEST_SUMMARY_FORWORKUP_SQLSERVER =
  "SELECT DISTINCT " +
  "                      obs.observation_uid observationUid, obs.ctrl_Cd_User_Defined_1 ctrlCdUserDefined1, act.source_act_uid sourceActUid, obs1.local_id localId, " +
  "                      obs1.cd_desc_txt resultedTest, obs1.cd resultedTestCd, obs1.cd_system_cd cdSystemCd, obsvaluecoded.code codedResultValue, " +
  "                      obsvaluecoded.display_name organismName, obsnumeric.comparator_cd_1 numericResultCompare, " +
  "                      obsnumeric.high_range highRange,obsnumeric.low_range lowRange,obsnumeric.separator_cd numericResultSeperator, obsnumeric.numeric_value_1 numericResultValue1, " +
  "                      obsnumeric.numeric_value_2 numericResultValue2, obsnumeric.numeric_scale_1 numericScale1, obsnumeric.numeric_scale_2 numericScale2, obsnumeric.numeric_unit_cd numericResultUnits " +
  " FROM         observation obs " +
  " inner join act_relationship act on act.target_act_uid = obs.observation_uid " +
  " inner join  observation obs1 on act.source_act_uid = obs1.observation_uid   " +
  "    and obs1.obs_domain_cd_st_1 = 'R_Result'" +
  " left outer join obs_value_numeric obsnumeric on obsnumeric.observation_uid = obs1.observation_uid " +
  " left outer join obs_value_coded obsvaluecoded on obsvaluecoded.observation_uid = obs1.observation_uid " +
  " WHERE " +
  " act.target_class_cd = 'OBS' " +
  " AND act.type_cd = ? AND act.source_class_cd = 'OBS' " +
  " AND act.record_status_cd = 'ACTIVE' " +
  " AND act.TARGET_ACT_UID = ?";
  
  public static final String SELECT_LABSUSCEPTIBILITES_REFLEXTEST_SUMMARY_FORWORKUP_SQLSERVER_DRRQ =
  "SELECT DISTINCT " +
  "                      obs.observation_uid observationUid, obs.ctrl_Cd_User_Defined_1 ctrlCdUserDefined1, act.source_act_uid sourceActUid, obs1.local_id localId, " +
  "                      obs1.cd_desc_txt resultedTest, obs1.cd resultedTestCd, obs1.cd_system_cd cdSystemCd, obsvaluecoded.code codedResultValue, " +
  "                      obsvaluecoded.display_name organismName, obsnumeric.comparator_cd_1 numericResultCompare, " +
  "                      obsnumeric.high_range highRange,obsnumeric.low_range lowRange,obsnumeric.separator_cd numericResultSeperator, obsnumeric.numeric_value_1 numericResultValue1, " +
  "                      obsnumeric.numeric_value_2 numericResultValue2, obsnumeric.numeric_scale_1 numericScale1, obsnumeric.numeric_scale_2 numericScale2, obsnumeric.numeric_unit_cd numericResultUnits " +
  " FROM         observation obs " +
  " inner join act_relationship act on act.target_act_uid = obs.observation_uid " +
  " inner join  observation obs1 on act.source_act_uid = obs1.observation_uid   " +
  "    and obs1.obs_domain_cd_st_1 = 'R_Result'" +
  " left outer join obs_value_numeric obsnumeric on obsnumeric.observation_uid = obs1.observation_uid " +
  " left outer join obs_value_coded obsvaluecoded on obsvaluecoded.observation_uid = obs1.observation_uid " +
  " WHERE " +
  " act.target_class_cd = 'OBS' " +
  " AND act.type_cd = ? AND act.source_class_cd = 'OBS' " +
  " AND act.record_status_cd = 'ACTIVE' " +
  " AND act.TARGET_ACT_UID in ("+GET_SOURCE_ACT_UID_FOR_SUSCEPTIBILITES_SQL+")";

  
  
 
         public static final String SELECT_REPORT_TYPE =
      " SELECT " +
                "c.CODE \"reportType\" " +
            "FROM  " +
            "    act_relationship a, " +
            "    observation b, " +
            "    obs_value_coded c " +
            "WHERE  " +
            "    a.target_act_uid = ?  and " +
            "    b.observation_uid = a.source_act_uid and " +
            "    c.observation_uid = b.observation_uid and " +
            "    b.CD = 'MRB100' ";

public static final String SELECT_COUNT_OF_REJECTED_NOTIFICATIONS_FOR_APPROVAL_ORACLE_REVISITED =
    "SELECT /*+ USE_NL(NOTIFICATION, public_health_case, Act_relationship) */ count(*) FROM Act_relationship, Public_health_case, Notification " +
    "where Act_relationship.target_act_uid =   Public_health_case.public_health_case_uid " +
    "and Act_relationship.source_act_uid =   Notification.notification_uid " +
    "and Notification.record_status_cd = 'REJECTED' " +
    "and (sysdate -Notification.LAST_CHG_TIME) < ";

public static final String SELECT_COUNT_OF_REJECTED_NOTIFICATIONS_FOR_APPROVAL_SQL_REVISITED =
    "SELECT count(*) FROM Notification with (nolock) " +
    "where Notification.record_status_cd = 'REJECTED' " +
    "and (getdate() -Notification.LAST_CHG_TIME) < ";

public static final String REJECTED_NOTIFICATIONS_SQL = "SELECT  Public_health_case.public_health_case_uid publicHealthCaseUid,  " +
" Public_health_case.cd conditionCode,  " +
" ISNULL(pnm.first_nm,'No First') firstName,   ISNULL(pnm.last_nm,'No Last') lastName, "+
"                             Public_health_case.last_chg_reason_cd,   Public_health_case.case_class_cd caseStatusCd,   " +
"                            Notification.last_chg_user_id RejectedByUserId," +
"Notification.cd \"notificationCd\", " +
"                             Public_health_case.jurisdiction_cd jurisdictionCd,   Notification.txt cdTxt,   " +
"                             Notification.add_time addTime,  p.person_parent_uid patientUid, " +
"                             Notification.add_user_id addUserId, ccode.condition_short_nm condition, " +
" ccode.nnd_ind \"nndInd\" , " +
"exportReceiving.receiving_system_nm \"recipient\" " +
" FROM                       Person p  with (nolock) Left outer join   Person_name pnm with (nolock) on p.person_uid=pnm.person_uid ,Participation with (nolock), Act_relationship with (nolock), Public_health_case with (nolock), nbs_srte..condition_code ccode with (nolock),Notification with (nolock) " +
"LEFT OUTER JOIN  EXPORT_RECEIVING_FACILITY "+
" exportReceiving with (nolock)"+
"ON exportReceiving.export_receiving_facility_uid = Notification.export_receiving_facility_uid "+
" where                     p.person_uid =   Participation.subject_entity_uid  " +

" and                       Act_relationship.target_act_uid =   Public_health_case.public_health_case_uid  " +
" and                       Act_relationship.source_act_uid =   Notification.notification_uid  " +
" and                       Participation.act_uid =   Public_health_case.public_health_case_uid  " +
" and                            ccode.condition_cd = Public_health_case.cd  " +
" and                       Notification.record_status_cd = 'REJECTED' " +
" and                         p.cd = 'PAT' " +
" AND                         (pnm.nm_use_cd = 'L' or pnm.nm_use_cd IS NULL) " +
" and                          (getdate() -Notification.LAST_CHG_TIME) < ";

public static final String PAM_QUESTION_OID_METADATA_SQL = "SELECT "
    + "NBS_UI_METADATA.nbs_question_uid nbsQuestionUid, "
    + "NBS_UI_METADATA.add_time addTime, "
    + "NBS_UI_METADATA.add_user_id addUserId, "
    + "NBS_UI_METADATA.code_set_group_id codeSetGroupId, "
    + "NBS_UI_METADATA.data_type dataType, "
    + "NBS_UI_METADATA.investigation_form_cd investigationFormCd, "
    + "NBS_UI_METADATA.last_chg_time lastChgTime, "
    + "NBS_UI_METADATA.last_chg_user_id lastChgUserId, "
    + "NBS_UI_METADATA.question_label AS questionLabel, "
    + "NBS_UI_METADATA.question_tool_tip AS questionToolTip, "
    + "NBS_UI_METADATA.version_ctrl_nbr questionVersionNbr, "
    + "NBS_UI_METADATA.tab_order_id tabId, "
    + "NBS_UI_METADATA.enable_ind enableInd, "
    + "NBS_UI_METADATA.order_nbr orderNbr, "
    + "NBS_UI_METADATA.default_value defaultValue, "
    + "NBS_UI_METADATA.required_ind requiredInd, "
    + "NBS_UI_METADATA.display_ind displayInd, "
    + "NND_METADATA.nnd_metadata_uid nndMetadataUid, "
    + "NBS_UI_METADATA.question_identifier questionIdentifier, "
    + "NND_METADATA.question_identifier_nnd questionIdentifierNnd,"
    + "NND_METADATA.question_required_nnd questionRequiredNnd,"
    + "NBS_UI_METADATA.question_oid questionOid, "
    + "NBS_UI_METADATA.question_oid_system_txt questionOidSystemTxt, "
    + "NBS_UI_Metadata.coinfection_ind_cd coinfectionIndCd, "
    + "CODE_SET.code_set_nm codeSetNm, "
    + "CODE_SET.class_cd codeSetClassCd, "
    + "NBS_UI_METADATA.data_location dataLocation, "
    + "NBS_UI_METADATA.data_cd dataCd, "
    + "NBS_UI_METADATA.data_use_cd dataUseCd, "
    + "NBS_UI_METADATA.field_size fieldSize, "
    + "NBS_UI_METADATA.parent_uid parentUid, "
    + "NBS_UI_METADATA.ldf_page_id ldfPageId, "
    + "NBS_UI_METADATA.nbs_ui_metadata_uid nbsUiMetadataUid, "
    + "NBS_UI_Component.nbs_ui_component_uid nbsUiComponentUid, "
    + "NBS_UI_METADATA.nbs_table_uid nbsTableUid, "
    + "NBS_UI_METADATA.part_type_cd partTypeCd, "
    + "NBS_UI_METADATA.standard_nnd_ind_cd \"standardNndIndCd\", "
    + "NND_METADATA.HL7_segment_field \"hl7SegmentField\", "
    + "NBS_UI_METADATA.question_group_seq_nbr \"questionGroupSeqNbr\" "
    + "FROM "
    + "NBS_UI_Metadata INNER JOIN NBS_UI_Component ON NBS_UI_Metadata.nbs_ui_component_uid = NBS_UI_Component.nbs_ui_component_uid "
    + "LEFT OUTER JOIN "
    + "NND_Metadata ON NBS_UI_METADATA.nbs_ui_metadata_uid = NND_Metadata.nbs_ui_metadata_uid LEFT OUTER JOIN "
    + "(SELECT DISTINCT code_set_group_id, code_set_nm, class_cd FROM "
    + "NBS_SRTE..CODESET) CODE_SET "
    + "ON CODE_SET.code_set_group_id = NBS_UI_METADATA.code_set_group_id "
    + "where NBS_UI_Metadata.record_status_cd = 'Active' "
    + "order by NBS_UI_METADATA.investigation_form_cd, NBS_UI_METADATA.order_nbr  ";


public static final String RULES_METADATA_SQL = "SELECT "
    + "R.rule_uid ruleUid,"
    + "R.rule_name ruleName,"
    + "R.comments ruleComment,"
    + "RI.rule_instance_uid ruleInstanceUid,"
    + "FRI.form_code formCode,"
    + "RI.comments comments,"
    + "CI.conseq_ind_code conseqIndCode "
    + "FROM "
    + "dbo.[rule_instance] RI,"
    + "dbo.[rule] R, "
    + "dbo.[Consequence_indicator] CI, "
    + "Form_rule_instance FRI "
    + "WHERE "
    + "RI.rule_uid = R.rule_uid and "
    + "RI.conseq_ind_uid =  CI.conseq_ind_uid and "
    + "RI.rule_instance_uid = FRI.rule_instance_uid "
    + "and FRI.record_status_cd = 'Active'";



public static final String RULES_METADATA_SOURCE_SQL="SELECT "
    + "SF.source_field_uid sourceFieldUid, "
    + "SF.rule_instance_uid ruleInstanceUid, "
    + "SF.source_field_seq_nbr sourceFieldSeqNbr, "
    + "NMR.component_identifier questionIdentifier, "
    + "NQ.question_label questionLabel "
    //+ "PQ.investigation_form_cd investigationFormCd "
    + "FROM "
    + "dbo.[Source_Field] SF, "
    + "dbo.[NBS_Metadata_rule] NMR, "
    + "dbo.[NBS_question] NQ "
    + "WHERE "
    + "SF.nbs_metadata_rule_uid = NMR.nbs_metadata_rule_uid and "
    + "NMR.component_uid = NQ.nbs_question_uid ";


public static final String RULES_METADATA_SOURCE_VALUE_SQL="SELECT "
    + "SV.source_field_uid sourceFieldUid, "
    + "SV.source_value sourceValue, "
    + "OT.operator_type_code operatorTypeCode "
    + "FROM "
    + "dbo.[Source_Value] SV,"
    + "dbo.[operator_type] OT "
    + "WHERE "
    + "SV.operator_type_uid = "
    + "OT.operator_type_uid";

public static final String RULES_METADATA_TARGET_SQL = "SELECT "
    + "TF.target_field_uid targetFieldUid, "
    + "TF.rule_instance_uid ruleInstanceUid, "
    + "NMR.component_identifier questionIdentifier, "
    + "NQ.question_label questionLabel "
    + "FROM "
    + "dbo.Target_Field TF "
    + "JOIN dbo.NBS_Metadata_Rule NMR ON "
    + "TF.nbs_metadata_rule_uid =  NMR.nbs_metadata_rule_uid "
    + "JOIN dbo.NBS_Question NQ ON "
    + "NMR.component_uid = NQ.nbs_question_uid;";

public static final String RULES_METADATA_TARGET_VALUE_SQL="SELECT "
    + "TV.target_value_uid targetValueUid, "
    + "TV.target_field_uid targetFieldUid, "
    + "TV.target_value targetValue, "
    + "CI.conseq_ind_code conseqIndCode, "
    + "OT.operator_type_code operatorTypeCode, "
    + "EM.error_desc_txt errorDescTxt "
    + "FROM "
    + "dbo.Target_Value TV "
    + "JOIN dbo.Target_Field TF ON "
    + "TV.target_field_uid = TF.target_field_uid "
    + "JOIN dbo.consequence_indicator CI ON "
    + "TV.conseq_ind_uid = CI.conseq_ind_uid "
    + "JOIN dbo.Error_Message EM ON "
    + "TV.error_message_uid = EM.error_message_uid "
    + "JOIN dbo.operator_type OT ON "
    + "OT.operator_type_uid = TV.operator_type_uid";


public static final String SELECT_PUBLIC_HEALTHCASE_FOR_INVESTIGATION_LOG =
    "SELECT Public_health_case.add_user_id \"addUserId\", "+
    "Public_health_case.case_class_cd \"caseStatusCd\", Public_health_case.jurisdiction_cd \"jurisdictionCd\", "+
    "Public_health_case.last_chg_time \"lastChgTime\",Public_health_case.add_time \"addTime\", "+
    "Public_health_case.last_chg_user_id \"lastChgUserId\",Public_health_case.version_ctrl_nbr \"versionCtrlNbr\""+
    "FROM Public_health_case  with (nolock) " +
    "WHERE Public_health_case.public_health_case_uid = ? ";


public static final String SELECT_PUBLIC_HEALTHCASE_HISTORY_FOR_INVESTIGATION_LOG =
    "SELECT Public_health_case_hist.add_user_id \"addUserId\", "+
    "Public_health_case_hist.case_class_cd \"caseStatusCd\", Public_health_case_hist.jurisdiction_cd \"jurisdictionCd\", "+
    "Public_health_case_hist.last_chg_time \"lastChgTime\",Public_health_case_hist.add_time \"addTime\", "+
    "Public_health_case_hist.last_chg_user_id \"lastChgUserId\",Public_health_case_hist.version_ctrl_nbr \"versionCtrlNbr\""+
    "FROM Public_health_case_hist  with (nolock) " +
    "WHERE Public_health_case_hist.public_health_case_uid = ? ";

public static final String RULES_LIST_SQL = "SELECT distinct "
    + "R.rule_uid ruleUid, "
    + "R.rule_name ruleName "
    + "FROM "
    + "dbo.[rule] R "
    + "ORDER BY "
    + "R.rule_name";


public static final String RULES_INSTANCES_LIST_SQL = "SELECT distinct "
    + "C.rule_instance_uid ruleInstanceUid, "
    + "A.rule_name ruleName, "
    + "B.conseq_ind_desc_txt conseqIndtxt, "
    + "C.comments comments, "
    + "C.rule_uid ruleUid ";

public static final String SOURCE_FIELDS_LIST_SQL = "SELECT distinct "
    + "C.source_field_uid sourceFieldUid,"
    + "A.question_label questionLabel, "
    + "A.question_identifier questionIdentifier "
    + "FROM "
    + "nbs_question A,source_field C,nbs_metadata_rule B";

public static final String TARGET_FIELDS_LIST_SQL = "SELECT distinct "
    + "C.target_field_uid targetFieldUid,"
    + "A.question_label questionLabel, "
    + "A.question_identifier questionIdentifier "
    + "FROM "
    + "nbs_question A,target_field C,nbs_metadata_rule B";

public static final String SOURCE_VALUES_LIST_SQL = "SELECT distinct "
    + "A.source_value_uid sourceValueUid, "
    + "A.source_field_uid sourceFieldUid, "
    + "A.source_value sourceValue, "
    + "B.operator_type_desc_txt operatorTypeDesc "
    + "FROM "
    + "source_value A,operator_type B ";

public static final String TARGET_VALUES_LIST_SQL = "SELECT distinct "
    + "A.target_value_uid targetValueUid, "
    + "A.target_field_uid targetFieldUid, "
    + "A.target_value targetValue, "
    + "B.error_desc_txt errorDescTxt, "
    + "B.error_cd error_cd, "
    + "C.conseq_ind_desc_txt conseqIndtxt, "
    + "D.operator_type_desc_txt operatorTypeDesc "
    + "FROM "
    + "target_value A,error_message B,consequence_indicator C,operator_type D ";

public static final String CONSEQ_IND_LIST_SQL = "SELECT distinct "
    + "conseq_ind_uid conseqIndUID, "
    + "conseq_ind_desc_txt conseqIndtxt "
    + "FROM "
    + "consequence_indicator order by conseq_ind_desc_txt ";

public static final String ERR_MESSAGE_LIST_SQL = "SELECT distinct "
    + "error_message_uid errMessageUID, "
    + "error_cd errMessagetxt "
    + "FROM "
    + "error_message order  by error_cd ";

public static final String OPERATOR_TYPE_LIST_SQL = "SELECT distinct "
    + "operator_type_uid opTypeUID, "
    + "operator_type_desc_txt opTypetxt "
    + "FROM "
    + "Operator_type order by operator_type_desc_txt ";

public static final String PAM_LABELS_LIST_SQL = "SELECT distinct "
    + "A.nbs_metadata_rule_uid nbsMetadataRuleUID, "
    + "C.question_label pamLabeltxt, "
    + "A.component_identifier pamIdentifiertxt "
    + "FROM "
    + "nbs_metadata_rule A, nbs_question C "
    + "where  "
    + "C.nbs_question_uid =  A.component_uid "
    + "ORDER BY A.component_identifier";
   // + "nbs_question A,nnd_metadata B "
   // + "WHERE "
  //  + "A.nbs_question_uid = B.nbs_question_uid "
  //  + "ORDER BY A.question_label";

public static final String GET_INV_FORMCODE = "SELECT distinct NBSM.Investigation_form_cd  formCode"
    + " FROM NBS_UI_metadata NBSM, NBS_Question NBSQ "
    + " WHERE "
    + " NBSM.nbs_question_uid = NBSQ.nbs_question_uid and "
    + " NBSQ.question_identifier in ";


public static final String DELETE_SOURCE_VALUE_SQL = "delete source_value where source_field_uid "
    + "in (select source_field_uid from source_field where rule_instance_uid in "
    + "(select rule_instance_uid from rule_instance where rule_instance_uid = ?))";

public static final String DELETE_TARGET_VALUE_SQL = "delete target_value "
    + "where target_field_uid in (select target_field_uid from target_field "
    + "where rule_instance_uid in (select rule_instance_uid from rule_instance where rule_instance_uid = ?))";

public static final String DELETE_SOURCE_FIELD_SQL = "delete source_field where rule_instance_uid in "
    +"(select rule_instance_uid from rule_instance where rule_instance_uid = ?)";

public static final String DELETE_TARGET_FIELD_SQL = "delete target_field where rule_instance_uid in "
    +"(select rule_instance_uid from rule_instance where rule_instance_uid = ?)";

public static final String DELETE_RULE_INSTANCE_SQL =  "delete rule_instance where rule_instance_uid = ?";
public static final String ADD_RULE_INSTANCE_SQL  = "INSERT INTO rule_instance ( conseq_ind_uid, rule_uid, comments) VALUES(?,?,?)";

public static final String MAX_RULE_INSTANCEUID_SQL  = "Select max(rule_instance_uid) as RuleInsUid from rule_instance";

public static final String ADD_SF_INSTANCE_SQL  =  "INSERT INTO source_field (nbs_metadata_rule_uid, rule_instance_uid) VALUES(?,?)";

public static final String MAX_SF_INSTANCEUID_SQL  = "Select max(source_field_uid) as SourceFUID from Source_field";

public static final String ADD_TF_INSTANCE_SQL  =  "INSERT INTO target_field (nbs_metadata_rule_uid, rule_instance_uid) VALUES(?,?)";

public static final String MAX_TF_INSTANCEUID_SQL  = "Select max(target_field_uid) as TargetFUID from Target_field";

public static final String ADD_SV_INSTANCE_SQL  =   "INSERT INTO source_value (source_field_uid, source_value,operator_type_uid) VALUES(?,?,?)";

public static final String MAX_SV_INSTANCEUID_SQL  = "Select max(source_value_uid) as SourceVUID from source_value";

public static final String ADD_TV_INSTANCE_SQL  =   "INSERT INTO target_value (target_field_uid, target_value,error_message_uid,conseq_ind_uid,operator_type_uid) VALUES(?,?,?,?,?)";

public static final String MAX_TV_INSTANCEUID_SQL  = "Select max(target_value_uid) as TargetVUID from target_value";

public static final String UPDATE_SV_INSTANCE_SQL = "UPDATE source_value" +
" SET source_value = ?,operator_type_uid = ? WHERE source_value_uid = ?";

public static final String UPDATE_TV_INSTANCE_SQL = "UPDATE target_value" +
" SET target_value = ?,conseq_ind_uid = ?,error_message_uid = ? WHERE target_value_uid = ?";

public static final String DELETE_SOURCE_VALUE_SF_SQL = "delete source_value where source_field_uid in (?)";

public static final String DELETE_TARGET_VALUE_TF_SQL = "delete target_value where target_field_uid in (?)";


public static final String DELETE_SOURCE_FIELD_SF_SQL = "delete source_field where source_field_uid = ?";

public static final String DELETE_TARGET_FIELD_TF_SQL = "delete target_field where target_field_uid = ?";

public static final String INSERT_FRM_CODE_SQL  =  "INSERT INTO form_rule_instance (rule_instance_uid, form_code,record_status_cd) VALUES(?,?,?)";

public static final String FRM_CODE_LIST_SQL = "SELECT distinct "
    + "A.rule_instance_uid ruleInstanceUid, "
    + "A.form_code formCode "
    + "FROM "
    + "form_rule_instance A "
    + "Where A.record_status_cd='Active' and rule_instance_uid =?";

public static final String DELETE_FRM_CODE_SQL = "delete form_rule_instance where rule_instance_uid =  ?";

public static final String GET_ASSIGNING_AUTH_STATE_CASE_SQL = "SELECT dbo.Act_id.root_extension_txt"
                                 + " FROM dbo.Public_health_case INNER JOIN"
                                 + " Act_id ON"
                                 + " Public_health_case.public_health_case_uid = dbo.Act_id.act_uid"
                                 + " INNER JOIN nbs_srte..condition_code cc "
                                 + " on Public_health_case.cd = cc.condition_cd"
                                 + " and cc.investigation_form_cd=?"
                                 + " and act_id_seq=? and Act_id.root_extension_txt=? where Public_health_case.local_id<>?";
public static final String CREATE_ASSIGNING_AUTH_STATE_CASE_SQL = "SELECT dbo.Act_id.root_extension_txt"
                                + " FROM dbo.Public_health_case INNER JOIN"
                                + " Act_id ON"
                                + " Public_health_case.public_health_case_uid = dbo.Act_id.act_uid"
                                + " INNER JOIN nbs_srte..condition_code cc "
                                + " on Public_health_case.cd = cc.condition_cd"
                                + " and cc.investigation_form_cd=?"
                                + " and act_id_seq=? and Act_id.root_extension_txt=?";

public static final String AGGREGATE_METADATA_QUERY= "SELECT  NT.nbs_table_uid nbsTableUid, "
                                + "NTM.nbs_table_metadata_uid nbsTableMetaDataUid,"
                                + "NT.nbs_table_nm nbsTableName,"
                                + "NQ.question_identifier questionIdentifier, "
                                + "NTM.nbs_indicator_uid nbsIndicatorUid, "
                                + "NTM.nbs_aggregate_uid nbsAggregateUid, "
                                + "NTM.nbs_question_uid nbsQuestionUid, "
                                + "NI.label indicatorLabel, "
                                + "NI.tool_tip indicatorToolTip, "
                                + "NA.label aggregateLabel, "
                                + "NA.tool_tip aggregateToolTip, "
                                + "NTM.aggregate_seq_nbr aggregateSeqNbr, "
                                + "NTM.indicator_seq_nbr indicatorSeqNbr "
                                + "FROM NBS_table_metadata NTM INNER JOIN "
                                + "NBS_indicator NI ON NTM.nbs_indicator_uid = NI.nbs_indicator_uid INNER JOIN "
                                + "NBS_aggregate NA ON NTM.nbs_aggregate_uid = NA.nbs_aggregate_uid INNER JOIN "
                                + "NBS_Question NQ ON NTM.nbs_question_uid = NQ.nbs_question_uid INNER JOIN "
                                + "NBS_table NT ON NTM.nbs_table_uid = NT.nbs_table_uid "
                                + "WHERE NTM.RECORD_STATUS_CD='Active' ORDER BY aggregateSeqNbr, indicatorSeqNbr ";


public static final String SELECT_EXPORT_ALGORITHM_SQL = "SELECT "
    +" exAlgorithm.export_algorithm_uid exportAlgorithmUid,"
    +" exAlgorithm.export_algorithm_nm algorithmName,"
    +" exAlgorithm.doc_type documentType,"
    +" exAlgorithm.level_of_review levelOfReview,"
    +" exAlgorithm.record_status_cd  recordStatusCd,"
    +" exReceivingFacility.receiving_system_nm  receivingSystemDescTxt"
    +" FROM"
    +" Export_Algorithm exAlgorithm,"
    +" Export_Receiving_Facility exReceivingFacility"
    +" WHERE"
    +" exAlgorithm.export_receiving_facility_uid = exReceivingFacility.export_receiving_facility_uid" ;


public static final String SELECT_EXPORT_ALGORITHM_TRIGGERS_SQL = "SELECT "
    +" export_trigger_uid exportTriggerUid,"
    +" nbs_question_uid  nbsQuestionUid,"
    +" trigger_filter triggerFilter,"
    +" trigger_logic triggerLogic "
    +" FROM "
    +" Export_Trigger "
    +" WHERE "
    +" record_status_cd = 'A' "
    +" AND export_algorithm_uid = ?" ;

public static final String SELECT_RECEIVING_FACILITY_SQL="SELECT "
    +" receiving_system_nm  receivingSystemNm,"
    +" receiving_system_short_nm  receivingSystemShortName,"
    +" receiving_system_oid  receivingSystemOid,"
    +" receiving_system_owner receivingSystemOwner,"
    +" receiving_system_owner_oid  receivingSystemOwnerOid,"
    +" receiving_system_desc_txt  receivingSystemDescTxt,"
    +" sending_ind_cd sendingIndCd,"
    +" receiving_ind_cd receivingIndCd,"
    +" allow_transfer_ind_cd allowTransferIndCd,"
    +" jur_derive_ind_cd jurDeriveIndCd,"
    +" type_cd reportType,"
    +" admin_comment adminComment,"
    +" export_receiving_facility_uid exportReceivingFacilityUid  "
    +" FROM "
    +" Export_Receiving_Facility ";


public static final String INSERT_RECEIVING_FACILITY_SYSTEMS="INSERT INTO Export_receiving_facility("
        +" add_time,"
        +" add_user_id,"
        +" last_chg_time,"
        +" last_chg_user_id,"
        +" receiving_system_nm,"
        +" receiving_system_oid,"
        +" record_status_cd,"
        +" message_recipient,"
        +" public_key_ldapAddress,"
        +" public_key_ldapBaseDN,"
        +" public_key_ldapDN,"
        +" priority_int,"
        +" encrypt,"
        +" signature,"
        +" receiving_system_short_nm,"
        +" receiving_system_owner,"
        +" receiving_system_desc_txt,"
        +" sending_ind_cd,"
        +" receiving_ind_cd,"
        +" allow_transfer_ind_cd,"
        +" jur_derive_ind_cd,"
        +" type_cd,"
        +" admin_comment,"
        +" receiving_system_owner_oid )"
        +" VALUES (?,?,?,?,?,?,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,?,?,?,?,?,?,?,?,?,?)";


public static final String UPDATE_RECEIVING_SYSTEMS_SQL="UPDATE "
    + "Export_receiving_facility "
    + "SET  add_time=?, "
    + "add_user_id=?, last_chg_time=?, "
    + "last_chg_user_id=?, " +
    "receiving_system_nm=?, " +
    "receiving_system_oid=?, " +
    "record_status_cd='ACTIVE', " +
    "message_recipient=NULL, " +
    "public_key_ldapAddress=NULL, " +
    "public_key_ldapBaseDN=NULL, " +
    "public_key_ldapDN=NULL, " +
    "priority_int=NULL, " +
    "encrypt=NULL, " +
    "signature=NULL, " +
    "receiving_system_short_nm=?," +
    " receiving_system_owner=?," +
    " receiving_system_desc_txt=?,"+
    " sending_ind_cd=?,"+
    " receiving_ind_cd=?,"+
    " allow_transfer_ind_cd=?,"+
    " jur_derive_ind_cd=?,"+
    " type_cd=?,"+
    " admin_comment=? ," +
    " receiving_system_owner_oid=? " +
    " WHERE export_receiving_facility_uid=? ";



public static final String UPDATE_TRIGGER_CODE_SQL="UPDATE "
    + "NBS_SRTE..code_to_condition "
    + "SET code_desc_txt=?, " +
    "disease_nm=?, " +
    "condition_cd=?, " +
    "code_system_version_id=? " +
    " WHERE nbs_uid=? ";


public static final String CREATE_TRIGGER_CODE_SQL="INSERT INTO "
	    + "NBS_SRTE..code_to_condition "
		+ "(code_system_cd, code_system_desc_txt, code, code_desc_txt, code_system_version_id, condition_cd, disease_nm, status_cd, status_time, nbs_uid, effective_from_time, effective_to_time)"
	    + "values (?,?,?,?,?,?,?,'A',getDate(),?,getDate(),NULL)";

public static final String SELECT_TRIGGER_CODE_SQL="SELECT  cc.code_system_cd \"codeSystemCd\" "
		+", cc.code_system_desc_txt \"codingSystem\" "
		+",cc.code \"code\""
		+", cc.code_desc_txt \"codeDescTxt\""
		+",cc.code_system_version_id \"codeSystemVersionId\" "
		+", cc.condition_cd \"conditionCd\""
		+",ISNULL(cc.disease_nm,'') \"diseaseNm\""
		+",cc.status_cd \"statusCd\""
		+",cc.status_time \"statusTime\" "
		+ ",cc.nbs_uid \"nbsUid\" "
		+ ",cc.effective_from_time \"effectiveFromTime\" "
		+ ",cc.effective_to_time \"effectiveToTime\" "
		+ ",cc1.prog_area_cd \"programArea\" "
		+" FROM  nbs_srte..code_to_condition cc "
		+" LEFT JOIN  nbs_srte..Condition_code cc1 on "
		+"  cc.condition_cd = cc1.condition_cd ";
	
public static final String SELECT_AllTRIGGER_CODE_SQL="SELECT  code_system_cd \"codeSystemCd\" "
		+", code_system_desc_txt \"codingSystem\" "
		+",code \"code\""
		+", code_desc_txt \"codeDescTxt\""
		+",code_system_version_id \"codeSystemVersionId\" "
		+", condition_cd \"conditionCd\""
		+",ISNULL(disease_nm,'') \"diseaseNm\""
		+",status_cd \"statusCd\""
		+",status_time \"statusTime\" "
		+ ",nbs_uid \"nbsUid\" "
		+ ",effective_from_time \"effectiveFromTime\" "
		+ ",effective_to_time \"effectiveToTime\" "
		+" FROM  nbs_srte..code_to_condition  ";
	
public static final String LOGICAL_DELETE_RECEIVING_SYSTEMS="Update "
    + "Export_receiving_facility " +
    " SET record_status_cd ='LOG_DEL' where export_receiving_facility_uid=?";

public static final String SELECT_EXPORT_ALGORITHM_UID_SQL = "SELECT "
    +" exAlgorithm.export_algorithm_uid exportAlgorithmUid "
    +" FROM"
    +" Export_Algorithm exAlgorithm "
    +" WHERE"
    +" exAlgorithm.export_algorithm_nm = ?" ;


public static final String SELECT_QUESTION_SRTS = "SELECT distinct NQ.nbs_question_uid nbsquestionuid,"
    + " NQ.question_identifier questionIdentifier,"
    + " nq.question_label questionLabel, CS.CODE_SET_NM codeSetName, NQ.data_type dataType"
    + " FROM NBS_ODSE..NBS_Question NQ, "
    + " NBS_SRTE..CODESET CS "
    + " WHERE NQ.CODE_SET_GROUP_ID=CS.CODE_SET_GROUP_ID"
    + " ORDER BY"
    + " NQ.question_identifier";

public static final String SELECT_EXPORT_TRIGGERFIELDS_ORACLE= "SELECT "
	    +" nndMetadata.nbs_question_uid \"nbsquestionuid\","
	    +" nndMetadata.investigation_form_cd \"invFormCode\","
	    +" nndMetadata.question_identifier \"questionIdentifier\","
	    +" nbsQuestion.question_label \"questionLabel\" "
	    +" FROM"
	    +" NND_Metadata nndMetadata, nbs_Question nbsQuestion"
	    +" WHERE"
	    +" nndMetadata.nbs_question_uid = nbsQuestion.nbs_question_uid and"
	    +" nndMetadata.msg_Trigger_ind_cd = 'T' and  nndMetadata.record_status_cd = 'Active'" ;


public static final String SELECT_REC_FACILITY_SQL = "SELECT "
    +" exReceivingFacility.receiving_system_oid  receivingSystemOid,"
    +" exReceivingFacility.receiving_system_nm  receivingSystem"
    +" FROM"
    +" Export_Receiving_Facility_System exReceivingFacility";

public static final String INSERT_EXPORT_ALGORITHM = "insert into Export_Algorithm("
    +" export_algorithm_nm,"
    +" doc_type,"
    +" level_of_review,"
    +" export_receiving_facility_uid,"
    +" record_status_cd,"
    +" add_time,"
    +" add_user_id ,"
    +" last_chg_time,"
    +" last_chg_user_id)"
    +" values(?,?,?,?,?,?,?,?,?) ";

public static final String INSERT_EXPORT_ALGTHM_TRIGGER = "insert into Export_Trigger("
    +" export_algorithm_uid,"
    +" add_time,"
    +" add_user_id,"
    +" last_chg_time,"
    +" last_chg_user_id,"
    +" nbs_question_uid,"
    +" trigger_filter ,"
    +" trigger_logic,"
    +" record_status_cd)"
    +" values(?,?,?,?,?,?,?,?,?) ";


public static final String INSERT_EDX_ACTIVITY_LOG = "insert into EDX_Activity_Log("
    +" source_uid,"
    +" target_uid,"
    +" doc_type,"
    +" record_status_cd,"
    +" record_status_time, "
    +" exception,"
    +" imp_exp_ind_cd, "
    +" source_type_cd, "
    +" target_type_cd, "
    +" business_obj_localId )"
    +" values(?,?,?,?,?,?,?,?,?,?) ";


public static final String INSERT_NBS_INTERFACE_SQL = "insert into NBS_Interface("
    +" payload,"
    +" imp_exp_ind_cd, "
    +" record_status_cd,"
    +" record_status_time, "
    +" add_time, "
    +" system_nm,"
    +" doc_type_cd)"
    +" values(?,?,?,?,?,?,?) SELECT @@IDENTITY AS 'Identity'";

public static final String GET_NBS_INTERFACE_FIELDS_SQL = " SELECT "
    +" nbs_interface_uid nBSInterfaceUid,"
    +" payload payload, "
    +" imp_exp_ind_cd impExpIndCd,"
    +" record_status_cd recordStatusCd,"
    +" record_status_time recordStatusTime,"
    +" sending_system_nm sendingSystemnm,"
    +" add_time addTime,"
    +" receiving_system_nm receivingSystemnm,"
    +" notification_uid notificationUid,"
    +" nbs_document_uid nbsDocumentUid"
    +" from NBS_Interface";

public static final String GET_DOCUMENTS_FOR_REVIEW_SQL = " SELECT"
    + " nbsdoc.nbs_document_uid  nbsDocumentUid, "
    + " nbsdoc.local_id  localId,"
    + " nbsdoc.doc_type_cd  docType,"
    + " per.person_parent_uid MPRUid,"
    + " nbsdoc.jurisdiction_cd jurisdiction,"
    + " nbsdoc.prog_area_cd  programArea,"
    + " pername.first_nm  firstName,"
    + " pername.last_nm lastName, "
    + " nbsdoc.doc_status_cd statusCd,"
    + " nbsdoc.add_time addTime, "
    + " nbsdoc.cd cd "
    + " FROM nbs_document nbsdoc, participation particip, person per, Person_name pername "
    + " WHERE  nbsdoc.nbs_document_uid in "
    + " (select top 300 nbs_document_uid from nbs_document nbd  "
    + " where  (nbd.record_status_cd = 'UNPROCESSED') "
    + " and nbd.doc_type_cd ='"+ NEDSSConstants.PHC_236 +"'"
    + " order by  nbd.nbs_document_uid)"
    + " and particip.act_uid = nbsdoc.nbs_document_uid "
    + " and particip.subject_entity_uid = per.person_uid "
    + " and particip.type_cd='SubjOfDOC' "
    + " and per.person_uid = pername.person_uid";


public static final String GET_NBS_DOCUMENT = " SELECT"
    + " nbsdoc.nbs_document_uid  \"nbsDocumentUid\", "
    + " nbsdoc.local_id  \"localId\","
    + " nbsdoc.doc_type_cd  \"docTypeCd\","
    + " nbsdoc.jurisdiction_cd \"jurisdictionCd\","
    + " nbsdoc.prog_area_cd  \"progAreaCd\","
    + " nbsdoc.doc_status_cd \"docStatusCd\", "
    + " nbsdoc.add_time \"addTime\", "
    + " nbsdoc.txt \"txt\", "
    + " nbsdoc.version_ctrl_nbr \"versionCtrlNbr\", "
    + " nbsdoc.external_version_ctrl_nbr \"externalVersionCtrlNbr\", "
    + " nbsdoc.doc_purpose_cd \"docPurposeCd\", "
    + " nbsdoc.cd_desc_txt \"cdDescTxt\", "
    + " nbsdoc.sending_facility_nm \"sendingFacilityNm\", "
    + " nbsdoc.add_user_id \"addUserId\", "
    + " nbsdoc.record_status_cd \"recordStatusCd\", "
    + " nbsdoc.cd \"cd\", "
    + " nbsdoc.doc_payload \"docPayload\", "
    + " nbsdoc.record_status_Time \"recordStatusTime\", "
    + " nbsdoc.program_jurisdiction_oid \"programJurisdictionOid\", "
    + " nbsdoc.shared_ind \"sharedInd\", "
    + " nbsdoc.last_chg_time \"lastChgTime\", "
    + " nbsdoc.last_chg_user_id \"lastChgUserId\", "
    + " nbsdoc.nbs_interface_uid \"nbsInterfaceUid\", "
    + " nbsdoc.nbs_document_metadata_uid \"nbsDocumentMetadataUid\", "
    + " nbsdoc.phdc_doc_derived \"phdcDocDerived\", "
    + " nbsdoc.payload_view_ind_cd \"payloadViewIndCd\", "
    + " nbsdoc.processing_decision_cd \"processingDecisionCd\", "
    + " nbsdoc.processing_decision_txt \"processingDecisiontxt\", "
    + " nbsdoc.effective_time \"effectiveTime\", "
    + " nbsdoc.sending_app_event_id \"sendingAppEventId\", "
    + " nbsdoc.sending_app_patient_id \"sendingAppPatientId\", "
    + " nbsdoc.program_jurisdiction_oid \"programJurisdictionOid\", "
    + " per.person_uid  \"personUid\", "
    + " per.person_parent_uid \"MPRUid\", "
    + " eep.doc_event_type_cd \"docEventTypeCd\" "
    + " FROM nbs_document nbsdoc "
    + " inner join participation particip on "
    + " particip.act_uid = nbsdoc.nbs_document_uid "
    + " inner join person per on "
    + " particip.subject_entity_uid = per.person_uid "
    + " and particip.type_cd='"+NEDSSConstants.SUBJECT_OF_DOC+ "' "
    + " inner join person_name pername on "
    + " per.person_uid = pername.person_uid "
	  + " left outer join edx_event_process eep on "
	  + " eep.nbs_document_uid = nbsdoc.nbs_document_uid "
	  + " and eep.doc_event_type_cd in('CASE','LabReport','MorbReport','CT') "
	  + " and eep.parsed_ind = 'N' "
    + " WHERE  nbsdoc.nbs_document_uid =?";



public static final String GET_NBS_DOCUMENT_PHDC_WORKFLOW = " SELECT"
    + " nbsdoc.nbs_document_uid  \"nbsDocumentUid\", "
    + " nbsdoc.local_id  \"localId\","
    + " nbsdoc.doc_type_cd  \"docTypeCd\","
    + " nbsdoc.jurisdiction_cd \"jurisdictionCd\","
    + " nbsdoc.prog_area_cd  \"progAreaCd\","
    + " nbsdoc.doc_status_cd \"docStatusCd\", "
    + " nbsdoc.add_time \"addTime\", "
    + " nbsdoc.txt \"txt\", "
    + " nbsdoc.version_ctrl_nbr \"versionCtrlNbr\", "
    + " nbsdoc.external_version_ctrl_nbr \"externalVersionCtrlNbr\", "
    + " nbsdoc.doc_purpose_cd \"docPurposeCd\", "
    + " nbsdoc.cd_desc_txt \"cdDescTxt\", "
    + " nbsdoc.sending_facility_nm \"sendingFacilityNm\", "
    + " nbsdoc.add_user_id \"addUserId\", "
    + " nbsdoc.record_status_cd \"recordStatusCd\", "
    + " nbsdoc.cd \"cd\", "
    + " nbsdoc.doc_payload \"docPayload\", "
    + " nbsdoc.record_status_Time \"recordStatusTime\", "
    + " nbsdoc.program_jurisdiction_oid \"programJurisdictionOid\", "
    + " nbsdoc.shared_ind \"sharedInd\", "
    + " nbsdoc.last_chg_time \"lastChgTime\", "
    + " nbsdoc.last_chg_user_id \"lastChgUserId\", "
    + " nbsdoc.nbs_interface_uid \"nbsInterfaceUid\", "
    + " nbsdoc.nbs_document_metadata_uid \"nbsDocumentMetadataUid\", "
    + " nbsdoc.phdc_doc_derived \"phdcDocDerived\", "
    + " nbsdoc.payload_view_ind_cd \"payloadViewIndCd\", "
    + " nbsdoc.processing_decision_cd \"processingDecisionCd\", "
    + " nbsdoc.processing_decision_txt \"processingDecisiontxt\", "
    + " nbsdoc.effective_time \"effectiveTime\", "
    + " nbsdoc.sending_app_event_id \"sendingAppEventId\", "
    + " nbsdoc.sending_app_patient_id \"sendingAppPatientId\", "
    + " per.person_uid  \"personUid\", "
    + " per.person_parent_uid \"MPRUid\", "
    + " eep.doc_event_type_cd \"docEventTypeCd\" "
    + " FROM nbs_document nbsdoc "
    + " inner join participation particip on "
    + " particip.act_uid = nbsdoc.nbs_document_uid "
    + " inner join person per on "
    + " particip.subject_entity_uid = per.person_uid "
    + " and particip.type_cd='"+NEDSSConstants.SUBJECT_OF_DOC+ "' "
  /*  + " inner join person_name pername on "
    + " per.person_uid = pername.person_uid "*/
	  + " left outer join (SELECT DISTINCT doc_event_type_cd, nbs_document_uid, parsed_ind FROM edx_event_process ) eep on "
	  + " eep.nbs_document_uid = nbsdoc.nbs_document_uid "
	  + " and eep.doc_event_type_cd in('CASE','LabReport','MorbReport','CT') "
	  + " and eep.parsed_ind = 'N' "
    + " WHERE  nbsdoc.nbs_document_uid =?";




public static final String UPDATE_NBS_DOCUMENT="UPDATE "
    + " NBS_Document  SET  "
    + " last_chg_time=?, "
    + " last_chg_user_id=?, "
    + " record_status_cd=?, "
    + " record_status_time=?, "
    + " prog_area_cd=?, "
    + " jurisdiction_cd=?, "
    + " program_jurisdiction_oid=?, "
    + " txt =?, "
    + " version_ctrl_nbr =?, "
    + " cd =?, "
    + " doc_purpose_cd =?, "
    + " doc_status_cd =?, "
    + " cd_desc_txt =?, "
    + " sending_facility_nm =?, "
    + " processing_decision_cd =?, "
    + " processing_decision_txt =?, "
    + " external_version_ctrl_nbr =?, "
    + " doc_payload =?, "
    + " nbs_interface_uid =? "
    
    + " WHERE nbs_document_uid=? ";

public static final String SELECT_SUMMARY_NOTIFICATION_SQL =
    "select Notification.notification_uid NotificationUid, " +
    " Notification.add_time AddTime,Notification.rpt_sent_time RptSentTime," +
    " Public_health_case.cd Cd, code1.condition_desc_txt CdTxt, " +
    " Public_health_case.case_class_cd CaseClassCd, " +
    " code2.code_short_desc_txt CaseClassCdTxt, " +
    " code1.nnd_ind \"nndInd\" , " +
    " Notification.local_id LocalId, Notification.txt Txt" +
    " from Public_health_case Public_health_case  with (nolock) , act_relationship ar  with (nolock) , " +
    " notification Notification, " + 
    " nbs_srte..Condition_code code1  with (nolock) , " +
    " nbs_srte..code_value_general code2  with (nolock) " + " where " +
    " Public_health_case.Public_health_case_uid = ar.target_act_uid " +
    " and ar.type_cd = 'SummaryNotification'" +
    " and ar.status_cd = 'A' " +
    " and Notification.notification_uid = ar.source_act_uid" +
    " and code1.condition_codeset_nm = 'PHC_TYPE' "+
    " and Public_health_case.cd = code1.condition_cd " +
    " and code2.code_set_nm = 'PHC_CLASS' "+
    " and Public_health_case.case_class_cd = code2.code " +
    " and Public_health_case.record_status_cd <> 'LOG_DEL' " +
    " and Public_health_case.public_health_case_uid = ?";

public static final String CONTACT_QUESTION_OID_METADATA_SQL = "SELECT "
    + "NBS_QUESTION.nbs_question_uid nbsQuestionUid, "
    + "NBS_QUESTION.add_time addTime, "
    + "NBS_QUESTION.add_user_id addUserId, "
    + "NBS_QUESTION.code_set_group_id codeSetGroupId, "
    + "NBS_QUESTION.data_type dataType, "
    + "NBS_UI_Metadata.investigation_form_cd investigationFormCd, "
    + "NBS_QUESTION.last_chg_time lastChgTime, "
    + "NBS_QUESTION.last_chg_user_id lastChgUserId, "
    + "    ISNULL(NBS_UI_Metadata.question_label, NBS_Question.question_label) AS questionLabel, "
    + "ISNULL(NBS_UI_Metadata.question_tool_tip, NBS_Question.question_tool_tip) AS questionToolTip, "
    + "NBS_QUESTION.version_ctrl_nbr questionVersionNbr, "
    + "NBS_UI_Metadata.tab_order_id tabId, "
    + "NBS_UI_Metadata.enable_ind enableInd, "
    + "NBS_UI_Metadata.order_nbr orderNbr, "
    + "NBS_UI_Metadata.default_value defaultValue, "
    + "NBS_UI_Metadata.required_ind requiredInd, "
    + "NBS_UI_Metadata.display_ind displayInd, "
    + "NND_METADATA.nnd_metadata_uid nndMetadataUid, "
    + "NBS_Question.question_identifier questionIdentifier, "
    + "NND_METADATA.question_identifier_nnd questionIdentifierNnd,"
    + "NND_METADATA.question_required_nnd questionRequiredNnd,"
    + "NBS_Question.question_oid questionOid, "
    + "NBS_Question.question_oid_system_txt questionOidSystemTxt, "
    + "CODE_SET.code_set_nm codeSetNm, "
    + "CODE_SET.class_cd codeSetClassCd, "
    + "NBS_QUESTION.data_location dataLocation, "
    + "NBS_QUESTION.data_cd dataCd, "
    + "NBS_QUESTION.data_use_cd dataUseCd, "
    + "NBS_UI_Metadata.field_size fieldSize, "
    + "NBS_UI_Metadata.parent_uid parentUid, "
    + "NBS_UI_Metadata.ldf_page_id ldfPageId, "
    + "NBS_UI_Metadata.nbs_ui_metadata_uid nbsUiMetadataUid, "
    + "NBS_UI_Metadata.coinfection_ind_cd coinfectionIndCd, "
    + "NBS_UI_Component.nbs_ui_component_uid nbsUiComponentUid, "
    + "NBS_UI_Metadata.nbs_table_uid nbsTableUid "
    + "FROM "
    + "NBS_UI_Metadata INNER JOIN NBS_UI_Component ON NBS_UI_Metadata.nbs_ui_component_uid = NBS_UI_Component.nbs_ui_component_uid "
    + "LEFT OUTER JOIN "
    + "NBS_Question ON NBS_UI_Metadata.nbs_question_uid = NBS_Question.nbs_question_uid LEFT OUTER JOIN "
    + "NND_Metadata ON NBS_UI_METADATA.nbs_ui_metadata_uid = NND_Metadata.nbs_ui_metadata_uid LEFT OUTER JOIN "
    + "(SELECT DISTINCT code_set_group_id, code_set_nm, class_cd FROM  "
    + "NBS_SRTE..CODESET) CODE_SET "
    + "ON CODE_SET.code_set_group_id = NBS_Question.code_set_group_id "
    + "where NBS_UI_Metadata.record_status_cd = 'Active' "
    + "and  NBS_UI_Metadata.investigation_form_cd='CONTACT_REC'"
    + "order by NBS_UI_Metadata.order_nbr  ";

public static final String DMB_QUESTION_OID_METADATA_SQL = "SELECT "
    + "NBS_UI_METADATA.nbs_question_uid nbsQuestionUid, "
    + "NBS_UI_METADATA.add_time addTime, "
    + "NBS_UI_METADATA.add_user_id addUserId, "
    + "NBS_UI_METADATA.code_set_group_id codeSetGroupId, "
    + "NBS_UI_METADATA.data_type dataType, "
    + "NBS_UI_METADATA.mask \"mask\", "
    + "NBS_UI_METADATA.investigation_form_cd investigationFormCd, "
    + "NBS_UI_METADATA.last_chg_time lastChgTime, "
    + "NBS_UI_METADATA.last_chg_user_id lastChgUserId, "
    + "NBS_UI_METADATA.question_label AS questionLabel, "
    + "NBS_UI_METADATA.question_tool_tip AS questionToolTip, "
    + "NBS_UI_METADATA.version_ctrl_nbr questionVersionNbr, "
    + "NBS_UI_METADATA.tab_order_id tabId, "
    + "NBS_UI_METADATA.enable_ind enableInd, "
    + "NBS_UI_METADATA.order_nbr orderNbr, "
    + "NBS_UI_METADATA.default_value defaultValue, "
    + "NBS_UI_METADATA.required_ind requiredInd, "
    + "NBS_UI_METADATA.display_ind displayInd, "
    + "NBS_UI_Metadata.coinfection_ind_cd coinfectionIndCd, "
    + "NND_METADATA.nnd_metadata_uid nndMetadataUid, "
    + "NBS_UI_METADATA.question_identifier questionIdentifier, "
    + "NND_METADATA.question_identifier_nnd questionIdentifierNnd,"
    + "NND_METADATA.question_required_nnd questionRequiredNnd,"
    + "NBS_UI_METADATA.question_oid questionOid, "
    + "NBS_UI_METADATA.question_oid_system_txt questionOidSystemTxt, "
    + "CODE_SET.code_set_nm codeSetNm, "
    + "CODE_SET.class_cd codeSetClassCd, "
    + "NBS_UI_METADATA.data_location dataLocation, "
    + "NBS_UI_METADATA.data_cd dataCd, "
    + "NBS_UI_METADATA.data_use_cd dataUseCd, "
    + "NBS_UI_METADATA.field_size fieldSize, "
    + "NBS_UI_METADATA.parent_uid parentUid, "
    + "NBS_UI_METADATA.ldf_page_id ldfPageId, "
    + "NBS_UI_METADATA.nbs_ui_metadata_uid nbsUiMetadataUid, "
    + "NBS_UI_Component.nbs_ui_component_uid nbsUiComponentUid, "
    + "NBS_UI_METADATA.unit_type_cd unitTypeCd, "
    + "NBS_UI_METADATA.unit_value unitValue, "
    + "NBS_UI_METADATA.nbs_table_uid nbsTableUid, "
    + "NBS_UI_METADATA.part_type_cd partTypeCd, "
    + "NBS_UI_METADATA.standard_nnd_ind_cd \"standardNndIndCd\", "
    + "NBS_UI_METADATA.sub_group_nm subGroupNm, "
    + "NND_METADATA.HL7_segment_field \"hl7SegmentField\", "
    + "NBS_UI_METADATA.question_group_seq_nbr \"questionGroupSeqNbr\", "
    + "NBS_UI_METADATA.question_unit_identifier \"questionUnitIdentifier\" "
    + "FROM "
    + "NBS_UI_Metadata INNER JOIN NBS_UI_Component ON NBS_UI_Metadata.nbs_ui_component_uid = NBS_UI_Component.nbs_ui_component_uid "
    + "LEFT OUTER JOIN "
    + "NND_Metadata ON NBS_UI_METADATA.nbs_ui_metadata_uid = NND_Metadata.nbs_ui_metadata_uid LEFT OUTER JOIN "
    + "(SELECT DISTINCT code_set_group_id, code_set_nm, class_cd FROM  "
    + "NBS_SRTE..CODESET) CODE_SET "
    + "ON CODE_SET.code_set_group_id = NBS_UI_METADATA.code_set_group_id "
    + "where upper(NBS_UI_Metadata.record_status_cd) = 'ACTIVE' and NBS_UI_Metadata.question_identifier is not null "
    + "order by NBS_UI_METADATA.investigation_form_cd, NBS_UI_METADATA.order_nbr  ";


public static final String GENERIC_QUESTION_OID_METADATA_SQL ="SELECT " +
     "wa_question.question_unit_identifier \"questionUnitIdentifier\", " +
     "wa_question.add_time \"addTime\", " +
     "wa_question.add_user_id \"addUserId\", " +
     "wa_question.code_set_group_id \"codeSetGroupId\", " +
     "wa_question.data_type \"dataType\", " +
     "wa_question.mask \"mask\", " +
     "'CORE_INV_FORM' \"investigationFormCd\", "+ 
     "wa_question.last_chg_time \"lastChgTime\", " +
     "wa_question.last_chg_user_id \"lastChgUserId\", " +
     "wa_question.question_nm \"questionLabel\", " +
     "wa_question.question_tool_tip \"questionToolTip\", " +
     "wa_question.version_ctrl_nbr \"questionVersionNbr\", " +
     "wa_question.default_value \"defaultValue\", " +
     "wa_question.question_identifier \"questionIdentifier\", " +
     "wa_question.question_oid \"questionOid\", " +
     "wa_question.question_oid_system_txt \"questionOidSystemTxt\", " +
     "CODESET.code_set_nm \"codeSetNm\", " +
     "CODESET.class_cd \"codeSetClassCd\", " +
     "wa_question.data_location \"dataLocation\", " +
     "wa_question.data_cd \"dataCd\", " +
     "wa_question.data_use_cd \"dataUseCd\", " +
     "wa_question.field_size \"fieldSize\", " +
     "NBS_UI_Component.nbs_ui_component_uid \"nbsUiComponentUid\", " +
     "wa_question.unit_type_cd \"unitTypeCd\", " +
     "wa_question.unit_value \"unitValue\", " +
     "wa_question.coinfection_ind_cd \"coinfectionIndCd\", " +
     "wa_question.standard_nnd_ind_cd \"standardNndIndCd\",     " +
     "wa_question.question_group_seq_nbr \"questionGroupSeqNbr\"    " +
     "FROM " +
     "wa_question "+
     "INNER JOIN NBS_UI_Component ON wa_question.nbs_ui_component_uid = NBS_UI_Component.nbs_ui_component_uid "+
     "LEFT OUTER JOIN "+
     "NBS_SRTE..CODESET "+  
     "ON CODESET.code_set_group_id = wa_question.code_set_group_id "+  
     " WHERE(UPPER( wa_question.data_location ) LIKE UPPER( 'public_health_case%' ) "+
     " OR UPPER( wa_question.data_location ) LIKE UPPER( 'confirmation_method%' )) " +
     " AND (wa_question.code_set_group_id IS NULL " +
     "   OR wa_question.code_set_group_id IN "+
     "   ( "+
     "     SELECT code_set_group_id " +
     "      FROM nbs_srte..codeset "+
     "      WHERE UPPER( class_cd )= UPPER('code_value_general') "+
     "    ))" ;

public static final String SELECT_PUBLIC_HEALTH_CASE_FOR_DSM_ADV_INV_SQL = "SELECT distinct Public_health_case.public_health_case_uid \"publicHealthCaseUid\","
		+ " Public_health_case.activity_duration_amt \"activityDurationAmt\","
		+ " Public_health_case.activity_duration_unit_cd \"activityDurationUnitCd\","
		+ " Public_health_case.activity_from_time \"activityFromTime\","
		+ " Public_health_case.activity_to_time \"activityToTime\","
		+ " Public_health_case.add_reason_cd \"addReasonCd\","
		+ " Public_health_case.add_time \"addTime\","
		+ " Public_health_case.add_user_id \"addUserId\","
		+ " Public_health_case.case_class_cd \"caseClassCd\","
		+ " Public_health_case.cd \"cd\","
		+ " Public_health_case.cd_desc_txt \"cdDescTxt\","
		+ " Public_health_case.cd_system_cd \"cdSystemCd\","
		+ " Public_health_case.cd_system_desc_txt \"cdSystemDescTxt\","
		+ " Public_health_case.confidentiality_cd \"confidentialityCd\","
		+ " Public_health_case.confidentiality_desc_txt \"confidentialityDescTxt\","
		+ " Public_health_case.detection_method_cd \"detectionMethodCd\","
		+ " Public_health_case.detection_method_desc_txt \"detectionMethodDescTxt\","
		+ " Public_health_case.disease_imported_cd \"diseaseImportedCd\","
		+ " Public_health_case.disease_imported_desc_txt \"diseaseImportedDescTxt\","
		+ " Public_health_case.effective_duration_amt \"effectiveDurationAmt\","
		+ " Public_health_case.effective_duration_unit_cd \"effectiveDurationUnitCd\","
		+ " Public_health_case.effective_from_time \"effectiveFromTime\","
		+ " Public_health_case.effective_to_time \"effectiveToTime\", "
		+ " Public_health_case.group_case_cnt \"groupCaseCnt\","
		+ " Public_health_case.investigation_status_cd \"investigationStatusCd\","
		+ " Public_health_case.jurisdiction_cd \"jurisdictionCd\","
		+ " Public_health_case.last_chg_reason_cd \"lastChgReasonCd\","
		+ " Public_health_case.last_chg_time \"lastChgTime\","
		+ " Public_health_case.last_chg_user_id \"lastChgUserId\","
		+ " Public_health_case.local_id \"localId\","
		+ " Public_health_case.mmwr_week \"mmwrWeek\","
		+ " Public_health_case.mmwr_year \"mmwrYear\","
		// +" org_access_permis \"orgAccessPermis\","
		+ " Public_health_case.outbreak_name \"outbreakName\","
		+ " Public_health_case.outbreak_from_time \"outbreakFromTime\","
		+ " Public_health_case.outbreak_ind \"outbreakInd\","
		+ " Public_health_case.outbreak_to_time \"outbreakToTime\","
		+ " Public_health_case.outcome_cd \"outcomeCd\","
		+ " Public_health_case.patient_group_id \"patientGroupId\","
		// +" prog_area_access_permis \"progAreaAccessPermis\","
		+ " Public_health_case.prog_area_cd \"progAreaCd\","
		+ " Public_health_case.record_status_cd \"recordStatusCd\","
		+ " Public_health_case.record_status_time \"recordStatusTime\","
		+ " Public_health_case.repeat_nbr \"repeatNbr\","
		+ " Public_health_case.rpt_cnty_cd \"rptCntyCd\","
		+ " Public_health_case.status_cd \"statusCd\","
		+ " Public_health_case.status_time \"statusTime\","
		+ " Public_health_case.transmission_mode_cd \"transmissionModeCd\","
		+ " Public_health_case.transmission_mode_desc_txt \"transmissionModeDescTxt\","
		+ " Public_health_case.txt \"txt\","
		+ " Public_health_case.user_affiliation_txt \"userAffiliationTxt\", "
		+ " Public_health_case.pat_age_at_onset \"patAgeAtOnset\","
		+ " Public_health_case.pat_age_at_onset_unit_cd \"patAgeAtOnsetUnitCd\","
		+ " Public_health_case.rpt_form_cmplt_time \"rptFormCmpltTime\","
		+ " Public_health_case.rpt_source_cd \"rptSourceCd\","
		+ " Public_health_case.rpt_source_cd_desc_txt \"rptSourceCdDescTxt\","
		+ " Public_health_case.rpt_to_county_time \"rptToCountyTime\","
		+ " Public_health_case.rpt_to_state_time \"rptToStateTime\","
		+ " Public_health_case.diagnosis_time \"diagnosisTime\" ,"
		+ " Public_health_case.program_jurisdiction_oid \"programJurisdictionOid\" ,"
		+ " Public_health_case.shared_ind \"sharedInd\" ,"
		+ " Public_health_case.version_ctrl_nbr \"versionCtrlNbr\" , "
		+ " Public_health_case.case_type_cd \"caseTypeCd\" , "
		+ " Public_health_case.investigator_assigned_time \"investigatorAssignedTime\" ,"
		+ " Public_health_case.hospitalized_ind_cd \"hospitalizedIndCd\" ,"
		+ " Public_health_case.hospitalized_admin_time \"hospitalizedAdminTime\" ,"
		+ " Public_health_case.hospitalized_discharge_time \"hospitalizedDischargeTime\" ,"
		+ " Public_health_case.hospitalized_duration_amt \"hospitalizedDurationAmt\" ,"
		+ " Public_health_case.pregnant_ind_cd \"pregnantIndCd\" ,"
		// +" die_from_illness_ind_cd \"dieFromIllnessIndCD\" ,"
		+ " Public_health_case.day_care_ind_cd \"dayCareIndCd\" ,"
		+ " Public_health_case.food_handler_ind_cd \"foodHandlerIndCd\" ,"
		+ " Public_health_case.imported_country_cd \"importedCountryCd\" ,"
		+ " Public_health_case.imported_state_cd \"importedStateCd\" ,"
		+ " Public_health_case.imported_city_desc_txt \"importedCityDescTxt\" ,"
		+ " Public_health_case.imported_county_cd \"importedCountyCd\" ,"
		+ " Public_health_case.deceased_time \"deceasedTime\" ,"
		+ " Public_health_case.count_interval_cd \"countIntervalCd\","
		+ " Public_health_case.priority_cd \"priorityCd\","
		+ " Public_health_case.infectious_from_date \"infectiousFromDate\","
		+ " Public_health_case.infectious_to_date \"infectiousToDate\","
		+ " Public_health_case.contact_inv_status_cd \"contactInvStatus\","
		+ " Public_health_case.contact_inv_txt \"contactInvTxt\", "
		+ " Public_health_case.referral_basis_cd \"referralBasisCd\", "
		+ " Public_health_case.curr_process_state_cd \"currProcessStateCd\", "
		+ " Public_health_case.inv_priority_cd \"invPriorityCd\", "
		+ " Public_health_case.coinfection_id \"coinfectionId\", "
		+ " observation.effective_from_time \"associatedSpecimenCollDate\", "
		+ " Conf.Confirmation_method_cd \"confirmationMethodCd\", "
		+ " Conf.confirmation_method_time \"confirmationMethodTime\" "
		+ " from "
		+ " Participation "
		+ " inner join person "
		+ " on person.person_uid=Participation.subject_entity_uid "
		+ " inner join Public_health_case "
		+ " on Participation.act_uid = Public_health_case.public_health_case_uid and Public_health_case.record_status_cd!='LOG_DEL'"
		+ " left outer Join (SELECT CM.public_health_case_uid, "
		+ " Confirmation_method_cd=REPLACE( "
		+ "                                ( "
		+ "                                  SELECT Confirmation_method_cd "
		+ "                                  AS [data()] "
		+ "                                 FROM Confirmation_method "
		+ "                                 AS CM1 "
		+ "                                WHERE CM1.public_health_case_uid=CM.public_health_case_uid "
		+ "                               ORDER BY Confirmation_method_cd "
		+ "                               FOR XML PATH( '' ) "
		+ "                            ), ' ', ',' ), "
		+ " cm.confirmation_method_time "
		+ " FROM Confirmation_method "
		+ " AS CM "
		+ " GROUP BY public_health_case_uid, "
		+ "        cm.confirmation_method_time) Conf on conf.public_health_case_uid = Public_health_case.public_health_case_uid "
		+ " left outer  join Act_relationship "
		+ " on  Public_health_case.public_health_case_uid= Act_relationship.target_act_uid  and Act_relationship.type_cd = 'LabReport' "
		+ " left outer  join observation "
		+ " on Act_relationship.source_act_uid = observation.observation_uid and observation.record_status_cd!='LOG_DEL' and observation.ctrl_cd_display_form='LabReport' and obs_domain_cd_st_1='Order'"
		+ " where Public_health_case.cd = ? "
		+ " and person.person_parent_uid =? "
		+ " order by observation.effective_from_time asc";

    public static final String INSERT_EDX_DOCUMENT = "INSERT INTO EDX_DOCUMENT"
            + "(act_uid, payload, record_status_cd, "
            + " record_status_time, add_time, doc_type_cd, nbs_document_metadata_uid, original_payload, original_doc_type_cd, edx_document_parent_uid) "
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_EDX_DOCUMENT_COLLECTION = "SELECT EDX_Document_uid \"eDXDocumentUid\", act_uid \"actUid\", "
            + " add_time \"addTime\" FROM " + " EDX_Document WITH (NOLOCK) WHERE act_uid = ? order by add_time desc";

    public static final String SELECT_INDIVIDUAL_EDX_DOCUMENT = "SELECT EDX_Document.EDX_Document_uid \"eDXDocumentUid\", EDX_Document.act_uid \"actUid\", "
            + " EDX_Document.payload \"payload\" , "
            + " EDX_Document.record_status_cd \"recordStatusCd\" , "
            + " EDX_Document.record_status_time \"recordStatusTime\" , "
            + " EDX_Document.add_time \"addTime\" , "
            + " EDX_Document.doc_type_cd \"docTypeCd\" , "
            + " NBS_document_metadata.document_view_xsl \"documentViewXsl\" , "
            + " NBS_document_metadata.xml_schema_location \"xmlSchemaLocation\"  "
            + " FROM EDX_Document, NBS_document_metadata "
            + " WHERE EDX_Document.nbs_document_metadata_uid=NBS_document_metadata.nbs_document_metadata_uid "
            + " and EDX_Document.EDX_Document_Uid  = ?";

    public static final String COUNT_MESSAGE_LOG = "select count(*) from Message_Log ml with (nolock) , Person_Name p with (nolock) , Auth_User au with (nolock) , Public_health_case phc  with (nolock)  "
            + "where ml.person_uid = p.person_uid and au.nedss_entry_id = ml.add_user_id and ml.assigned_to_uid = ? and ml.record_status_cd != 'LOG_DEL' and p.nm_use_cd = 'L'"
            + " and phc.public_health_case_uid = ml.event_uid  and phc.record_status_cd != 'LOG_DEL' ";

    public static final String SELECT_MESSAGE_LOG = "select ml.message_log_uid \"messageLogUid\", ml.message_txt \"messageTxt\", phc.public_health_case_uid \"eventUid\","
            + " phc.cd_desc_txt \"conditionCd\", ml.event_type_cd \"eventTypeCd\", ml.message_status_cd \"messageStatusCd\","
            + " ml.add_time \"addTime\", ISNULL(p.first_nm,'No First') \"firstNm\", ISNULL(p.last_nm,'No Last') \"lastNm\", au.user_first_nm \"userFirstNm\", "
            + " au.user_last_nm \"userLastNm\" "
            + " from Message_Log ml, Person_Name p, Auth_User au, Public_health_case phc  "
            + " where ml.person_uid = p.person_uid and au.nedss_entry_id = ml.add_user_id and assigned_to_uid = ? "
            + " and ml.record_status_cd != 'LOG_DEL' and p.nm_use_cd = 'L' and phc.public_health_case_uid = ml.event_uid and phc.record_status_cd != 'LOG_DEL'";
    public static final String UPDATE_MESSAGE_LOG = " update message_log set message_status_cd = ? where assigned_to_uid = ? and message_log_uid = ? ";

    public static final String DELETE_MESSAGE_LOG = " update message_log set record_status_cd = ?, last_chg_time = ? , "
            + "last_chg_user_id = ? where assigned_to_uid = ? and message_log_uid = ? ";

    public static final String SUPERVISOR_REVIEW_COUNT = " SELECT count(*) from case_management cm with (nolock) , Public_Health_Case phc with (nolock) "
            + " WHERE phc.public_health_case_uid  = cm.public_health_case_uid AND cm.case_review_status = 'Ready' AND phc.record_status_cd <> 'LOG_DEL' AND phc.investigation_status_cd = 'C'";


    public static final String SUPERVISOR_REVIEW_SELECT = " SELECT distinct pat1.first_nm \"patientFirstNm\", pat1.last_nm \"patientLastNm\", patPer.person_parent_uid \"MPRUid\" , phc.cd_desc_txt  \"condition\", "
            + " inv1.first_nm \"investigatorFirstNm\", inv1.last_nm \"investigatorLastNm\" , phc.public_health_case_uid \"publicHealthCaseUid\", phc.referral_basis_cd \"referralBasisCd\", cm.case_closed_date \"caseClosedDate\", "
            + " cm.fld_foll_up_dispo \"fldFollUpDispo\", fcr1.last_nm \"supervisorLastNm\", fcr1.first_nm \"superviorFirstNm\", ccr1.last_nm \"ccrLastNm\", ccr1.first_nm \"ccrFirstNm\", cm.case_review_status_date \"submitDate\" "
            + " FROM Public_Health_Case phc "
            + " INNER JOIN case_management cm on cm.public_health_case_uid = phc.public_health_case_uid and cm.case_review_status = 'Ready'"
            + " INNER JOIN NBS_act_entity pat on pat.type_cd = 'SubjOfPHC' and pat.act_uid = phc.public_health_case_uid "
            + " INNER JOIN Person_name pat1 on pat1.person_uid = pat.entity_uid "
            + " INNER JOIN Person patPer on patPer.person_uid = pat.entity_uid "
            + " LEFT OUTER JOIN NBS_act_entity inv on inv.type_cd = 'InvestgrOfPHC' and inv.act_uid = phc.public_health_case_uid "
            + " LEFT OUTER JOIN Person_name inv1 on inv1.person_uid = inv.entity_uid "
            + " LEFT OUTER JOIN NBS_act_entity fcr on fcr.type_cd = 'FldFupSupervisorOfPHC' and fcr.act_uid = phc.public_health_case_uid "
            + " LEFT OUTER JOIN Person_name fcr1 on fcr1.person_uid = fcr.entity_uid "
            + " LEFT OUTER JOIN NBS_act_entity ccr on ccr.type_cd = 'CASupervisorOfPHC' and ccr.act_uid = phc.public_health_case_uid "
            + " LEFT OUTER JOIN Person_name ccr1 on ccr1.person_uid = ccr.entity_uid "
            + " WHERE phc.record_status_cd <> 'LOG_DEL' AND phc.investigation_status_cd = 'C' AND pat1.nm_use_cd = 'L'";

    public static final String ANSWER_GROUP_SEQ_NBR = " SELECT max(answer_group_seq_nbr) FROM "
            + " NBS_case_answer ca, Public_Health_Case phc, NBS_ui_metadata ui "
            + " WHERE ca.act_uid = phc.public_health_case_uid AND ui.nbs_question_uid = ca.nbs_question_uid "
            + " AND ca.record_status_cd <> 'LOG_DEL' AND phc.public_health_case_uid = ? AND ui.question_identifier = ?  ";

    public static final String  UPDATE_EPILINK =" update case_management set epi_link_id = ? where epi_link_id = ?";
	public static final String COINFECTION_INV_LIST_FOR_GIVEN_COINFECTION_ID_SQL = "select distinct phc.public_health_case_uid \"publicHealthCaseUid\", phc.cd \"conditionCd\" from "
			+ "Public_health_case phc, person, Participation   "
			+ "where phc.investigation_status_cd='O'  and phc.record_status_cd !='LOG_DEL'  "
			+ "and phc.public_health_case_uid=participation.act_uid "
			+ "and participation.type_cd ='SubjOfPHC' "
			+ "and participation.subject_entity_uid =person.person_uid "
			+ "and coinfection_id=? "
			+ "and person.person_parent_uid = ? ";

	public static final String MERGE_COINFECTION_INV_LIST_FOR_GIVEN_COINFECTION_ID_SQL = "select distinct phc.public_health_case_uid \"publicHealthCaseUid\", phc.cd \"conditionCd\", "
			+ "investigation_status_cd \"investigationStatus\" from "
			+ "Public_health_case phc, person, Participation   "
			+ "where phc.record_status_cd !='LOG_DEL'  "
			+ "and phc.public_health_case_uid=participation.act_uid "
			+ "and participation.type_cd ='SubjOfPHC' "
			+ "and participation.subject_entity_uid =person.person_uid "
			+ "and coinfection_id=? "
			+ "and person.person_parent_uid = ? ";

	public static final String COINFECTION_INV_LIST_SQL = "select distinct phc.public_health_case_uid \"publicHealthCaseUid\", "
			+ "phc.local_id \"localId\", "
			+ "phc.coinfection_id \"coinfectionId\", "
			+ "pn.last_nm \"investigatorLastNm\", "
			+ "pn.first_nm \"intestigatorFirstNm\", "
			+ "phc.cd \"conditionCd\", "
			+ "phc.jurisdiction_cd \"jurisdictionCd\", "
			+ "phc.prog_area_cd \"progAreaCd\", "
			+ "phc.investigation_status_cd \"investigationStatus\", "
			+ "phc.case_class_cd \"caseClassCd\", "
			+ "phc.activity_from_time \"investigationStartDate\", "
			+ "phc.add_time \"createDate\", "
			+ "phc.last_chg_time \"updateDate\", "
			+ "cm.epi_link_id \"epiLinkId\", "
			+ "cm.field_record_number \"fieldRecordNumber\" "
			+ "from Public_health_case phc  with (nolock) "
			+ "inner join nbs_srte..Condition_code cc1  with (nolock) on "
			+ "phc.cd = cc1.condition_cd "
			+ "and phc.investigation_status_cd='O' "
			+ "and phc.record_status_cd !='LOG_DEL' "
			+ "inner join nbs_srte..Condition_code cc2  with (nolock) on "
			+ "cc2.coinfection_grp_cd = cc1.coinfection_grp_cd "
			+ "inner join NBS_act_entity nae1  with (nolock) on "
			+ "nae1.act_uid = phc.public_health_case_uid "
			+ "inner join Person per  with (nolock) on "
			+ "nae1.entity_uid = per.person_uid "
			+ "and nae1.type_cd = 'SubjOfPHC' "
			+ "left outer join case_management cm  with (nolock) on "
			+ "cm.public_health_case_uid = phc.public_health_case_uid "
			+ "left outer join NBS_act_entity nae2  with (nolock) on "
			+ "nae2.act_uid = phc.public_health_case_uid "
			+ "and nae2.type_cd = 'InvestgrOfPHC' "
			+ "left outer join Person_name pn  with (nolock) on "
			+ "nae2.entity_uid = pn.person_uid "
			+ "where per.person_parent_uid = ? "
			+ "and cc2.condition_cd = ? order by phc.add_time desc";


    //retrieve all INVs associated with a particular co-infection id passing a public_health_case_uid
	public static final String COINFECTION_SPECIFIED_INV_LIST_SQL = "select distinct phc.public_health_case_uid \"publicHealthCaseUid\", "
			+ "phc.local_id \"localId\", "
			+ "phc.coinfection_id \"coinfectionId\", "
			+ "pn.last_nm \"investigatorLastNm\", "
			+ "pn.first_nm \"intestigatorFirstNm\", "
			+ "phc.cd \"conditionCd\", "
			+ "phc.jurisdiction_cd \"jurisdictionCd\", "
			+ "phc.prog_area_cd \"progAreaCd\", "
			+ "phc.program_jurisdiction_oid \"programJurisdictionOid\", "
			+ "phc.investigation_status_cd \"investigationStatus\", "
			+ "phc.case_class_cd \"caseClassCd\", "
			+ "phc.activity_from_time \"investigationStartDate\", "
			+ "phc.add_time \"createDate\", "
			+ "phc.last_chg_time \"updateDate\", "
			+ "nae1.entity_uid \"patientRevisionUid\", "
			+ "cm.epi_link_id \"epiLinkId\", "
			+ "cm.field_record_number \"fieldRecordNumber\", "
			+ "cm.pat_intv_status_cd  \"patIntvStatusCd\" "
			+ "from Public_health_case phc with (nolock)"
			+ "inner join nbs_srte..Condition_code cc1 with (nolock) on "
			+ "phc.cd = cc1.condition_cd "
			+ "and phc.record_status_cd !='LOG_DEL' "
			+ "inner join NBS_act_entity nae1 with (nolock) on "
			+ "nae1.act_uid = phc.public_health_case_uid "
			+ "inner join Person per with (nolock) on "
			+ "nae1.entity_uid = per.person_uid "
			+ "and nae1.type_cd = 'SubjOfPHC' "
			+ "left outer join case_management cm with (nolock) on "
			+ "cm.public_health_case_uid = phc.public_health_case_uid "
			+ "left outer join NBS_act_entity nae2 with (nolock) on "
			+ "nae2.act_uid = phc.public_health_case_uid "
			+ "and nae2.type_cd = 'InvestgrOfPHC' "
			+ "left outer join Person_name pn with (nolock) on "
			+ "nae2.entity_uid = pn.person_uid "
			+ "where phc.coinfection_id in (select coinfection_id from public_health_case with (nolock) where public_health_case_uid = ?) "
			+ "order by phc.add_time desc";
	
	public static final String COINFECTION_SPECIFIED_INV_LIST_SQL_PHC = "select distinct phc.public_health_case_uid \"publicHealthCaseUid\", "
			+ "phc.cd \"conditionCd\", "
			+ "cm.pat_intv_status_cd  \"patIntvStatusCd\" "
			+ "from Public_health_case phc with (nolock) "
			+ "inner join case_management cm with (nolock) on "
			+ "cm.public_health_case_uid = phc.public_health_case_uid "
			+ "and phc.record_status_cd !='LOG_DEL' "
			+ "where phc.coinfection_id in (select coinfection_id from public_health_case with (nolock) where public_health_case_uid = ?) ";

	public static final String COINFECTION_SPECIFIED_INV_LIST_SQL_COUNT = "select count(*) from "
			+" Public_health_case phc with (nolock) "
			+ "where phc.coinfection_id in (select coinfection_id from public_health_case with (nolock) where public_health_case_uid = ?) "
			+ "and phc.record_status_cd !='LOG_DEL' ";

	//retrieve all INVs associated with a particular co-infection id passing a public_health_case_uid


	public static final String OPEN_INV_LIST = "select distinct phc.public_health_case_uid \"publicHealthCaseUid\", "
			+ "phc.local_id \"localId\", "
			+ "phc.coinfection_id \"coinfectionId\", "
			+ "pn.last_nm \"investigatorLastNm\", "
			+ "pn.first_nm \"intestigatorFirstNm\", "
			+ "phc.cd \"conditionCd\", "
			+ "phc.jurisdiction_cd \"jurisdictionCd\", "
			+ "phc.prog_area_cd \"progAreaCd\", "
			+ "phc.investigation_status_cd \"investigationStatus\", "
			+ "phc.case_class_cd \"caseClassCd\", "
			+ "phc.activity_from_time \"investigationStartDate\", "
			+ "phc.add_time \"createDate\", "
			+ "phc.last_chg_time \"updateDate\", "
			+ "cm.epi_link_id \"epiLinkId\", "
			+ "cm.field_record_number \"fieldRecordNumber\" "
			+ "from Public_health_case phc  with (nolock) "
			+ "inner join NBS_act_entity nae1  with (nolock) on "
			+ "nae1.act_uid = phc.public_health_case_uid "
			+ "and phc.investigation_status_cd = 'O' "
			+ "and phc.record_status_cd != 'LOG_DEL' "
			+ "inner join Person per  with (nolock) on "
			+ "nae1.entity_uid = per.person_uid "
			+ "and nae1.type_cd = 'SubjOfPHC' "
			+ "left outer join case_management cm  with (nolock) on "
			+ "cm.public_health_case_uid = phc.public_health_case_uid "
			+ "left outer join NBS_act_entity nae2  with (nolock) on "
			+ "nae2.act_uid = phc.public_health_case_uid "
			+ "and nae2.type_cd = 'InvestgrOfPHC' "
			+ "left outer join Person_name pn  with (nolock) on "
			+ "nae2.entity_uid = pn.person_uid "
			+ "where per.person_parent_uid = ? "
			+ "and phc.cd = ? order by phc.add_time desc";
	public static final String SELECT_COUNT_OF_OPEN_INVESTIGATION = "SELECT count(*) " + 
			"FROM Public_health_case with (nolock)" + 
			"WHERE investigation_status_cd = 'O' " + 
			"AND record_status_cd <> 'LOG_DEL' ";
	
	public static final String PRE_POP_MAPPING_SQL = "SELECT LOOKUP_QUESTION.LOOKUP_QUESTION_uid \"lookupQuestionUid\"," + 
			"		       FROM_QUESTION_IDENTIFIER \"fromQuestionIdentifier\"," + 
			"		       LOOKUP_QUESTION.FROM_CODE_SYSTEM_CD \"fromCodeSystemCode\"," + 
			"		       FROM_DATA_TYPE \"fromDataType\"," + 
			"			   FROM_FORM_CD \"fromFormCd\"," + 
			"			   TO_FORM_CD \"toFormCd\"," + 
			"		       TO_QUESTION_IDENTIFIER \"toQuestionIdentifier\"," + 
			"		       LOOKUP_QUESTION.TO_CODE_SYSTEM_CD \"toCodeSystemCd\"," + 
			"		       TO_DATA_TYPE \"toDataType\"," + 
			"		       LOOKUP_ANSWER_UID \"lookupAnswerUid\"," + 
			"		       FROM_ANSWER_CODE \"fromAnswerCode\"," + 
			"		       LOOKUP_ANSWER.FROM_CODE_SYSTEM_CD \"fromAnsCodeSystemCd\"," + 
			"		       TO_ANSWER_CODE \"toAnswerCode\" ," + 
			"		       LOOKUP_ANSWER.TO_CODE_SYSTEM_CD \"toAnsCodeSystemCd\"" + 
			"		FROM nbs_odse..LOOKUP_QUESTION" + 
			"		     LEFT OUTER JOIN nbs_odse..LOOKUP_ANSWER ON LOOKUP_QUESTION.LOOKUP_QUESTION_uid = LOOKUP_ANSWER.LOOKUP_QUESTION_UID "
			+ "order by FROM_FORM_CD, TO_FORM_CD"; 

} //end of WUMSqlQuery class
