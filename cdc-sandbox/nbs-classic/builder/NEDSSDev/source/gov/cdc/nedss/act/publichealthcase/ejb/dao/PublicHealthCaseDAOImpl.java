/**
* Name:		PublicHealthCaseDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               PublicHealthCase value object in the PublicHealthCase entity bean.
*               This class encapsulates all the JDBC calls made by the PublicHealthCaseEJB
*               for a PublicHealthCaseVO object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of PublicHealthCaseEJB is
*               implemented here.
*Added support for isReentrant variable for merge case scenario               
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
* @updatedByAuthor Pradeep Sharma
 * @company: SAIC
 * @version 4.5
* @updatedByAuthor Pradeep Sharma
 * @company: CSRA
 * @version 5.2
*/

package gov.cdc.nedss.act.publichealthcase.ejb.dao;

import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.queue.vo.SupervisorReviewVO;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PublicHealthCaseDAOImpl extends BMPBase {
	private long phcUID = -1;
	private static final String SELECT_PUBLIC_HEALTH_CASE_UID = "SELECT public_health_case_uid FROM Public_Health_Case WHERE public_health_case_uid = ?";
	private static final String INSERT_ACTIVITY = "INSERT INTO Act (act_uid," + " class_cd," + " mood_cd)"
			+ " VALUES (?, ?, ?)";
	private static final String INSERT_PUBLIC_HEALTH_CASE = " INSERT INTO Public_Health_Case (public_health_case_uid,"
			+ " activity_duration_amt," + " activity_duration_unit_cd," + " activity_from_time," + " activity_to_time,"
			+ " add_reason_cd," + " add_time," + " add_user_id," + " case_class_cd," + " cd," + " cd_desc_txt,"
			+ " cd_system_cd," + " cd_system_desc_txt," + " confidentiality_cd," + " confidentiality_desc_txt,"
			+ " detection_method_cd," + " detection_method_desc_txt," + " disease_imported_cd,"
			+ " disease_imported_desc_txt," + " effective_duration_amt," + " effective_duration_unit_cd,"
			+ " effective_from_time," + " effective_to_time," + " group_case_cnt," + " investigation_status_cd,"
			+ " jurisdiction_cd," + " last_chg_reason_cd," + " last_chg_time," + " last_chg_user_id," + " local_id,"
			+ " mmwr_week," + " mmwr_year,"
			// +" org_access_permis,"
			+ " outbreak_name," + " outbreak_from_time," + " outbreak_ind," + " outbreak_to_time," + " outcome_cd,"
			+ " patient_group_id,"
			// +" prog_area_access_permis,"
			+ " prog_area_cd," + " record_status_cd," + " record_status_time," + " repeat_nbr," + " rpt_cnty_cd,"
			+ " status_cd," + " status_time," + " transmission_mode_cd," + " transmission_mode_desc_txt," + " txt,"
			+ " user_affiliation_txt," + " pat_age_at_onset," + " pat_age_at_onset_unit_cd," + " rpt_form_cmplt_time,"
			+ " rpt_source_cd," + " rpt_source_cd_desc_txt," + " rpt_to_county_time," + " rpt_to_state_time,"
			+ " diagnosis_time," + " program_jurisdiction_oid," + " shared_ind," + " case_type_cd,"
			+ " version_ctrl_nbr," + " investigator_assigned_time," + " hospitalized_ind_cd,"
			+ " hospitalized_admin_time," + " hospitalized_discharge_time," + " hospitalized_duration_amt,"
			+ " pregnant_ind_cd,"
			// +" die_from_illness_ind_cd,"
			+ " day_care_ind_cd," + " food_handler_ind_cd," + " imported_country_cd," + " imported_state_cd,"
			+ " imported_city_desc_txt," + " imported_county_cd," + " deceased_time," + " count_interval_cd,"
			+ " priority_cd," + " infectious_from_date," + " infectious_to_date," + " contact_inv_status_cd,"
			+ " contact_inv_txt, " + "referral_basis_cd, " + "curr_process_state_cd, "
			+ "inv_priority_cd, coinfection_id)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?,"
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String UPDATE_PUBLIC_HEALTH_CASE_1 = "UPDATE Public_Health_Case set activity_duration_amt = ?,"
			+ " activity_duration_unit_cd = ?," + " activity_from_time = ?," + " activity_to_time = ?,"
			+ " add_reason_cd = ?," + " add_time = ?," + " add_user_id = ?," + " case_class_cd = ?," + " cd = ?,"
			+ " cd_desc_txt = ?," + " cd_system_cd = ?," + " cd_system_desc_txt = ?," + " confidentiality_cd = ?,"
			+ " confidentiality_desc_txt = ?," + " detection_method_cd = ?," + " detection_method_desc_txt = ?,"
			+ " disease_imported_cd = ?," + " disease_imported_desc_txt = ?," + " effective_duration_amt = ?,"
			+ " effective_duration_unit_cd = ?," + " effective_from_time = ?," + " effective_to_time = ?,"
			+ " group_case_cnt = ?," + " investigation_status_cd = ?," + " jurisdiction_cd = ?,"
			+ " last_chg_reason_cd = ?," + " last_chg_time = ?," + " last_chg_user_id = ?," + " mmwr_week = ?,"
			+ " mmwr_year = ?,"
			// +" org_access_permis = ?,"
			+ " outbreak_name = ?," + " outbreak_from_time = ?," + " outbreak_ind = ?," + " outbreak_to_time = ?,"
			+ " outcome_cd = ?," + " patient_group_id = ?,"
			// +" prog_area_access_permis = ?,"
			+ " prog_area_cd = ?," + " record_status_cd = ?," + " record_status_time = ?," + " repeat_nbr = ?,"
			+ " rpt_cnty_cd = ?," + " status_cd = ?," + " status_time = ?," + " transmission_mode_cd = ?,"
			+ " transmission_mode_desc_txt = ?," + " txt = ?," + " user_affiliation_txt = ?," + " pat_age_at_onset = ?,"
			+ " pat_age_at_onset_unit_cd = ?," + " rpt_form_cmplt_time = ?," + " rpt_source_cd = ?,"
			+ " rpt_source_cd_desc_txt = ?," + " rpt_to_county_time = ?," + " rpt_to_state_time = ?,"
			+ " diagnosis_time = ?," + " program_jurisdiction_oid = ?," + " shared_ind = ?," + " version_ctrl_nbr = ? ,"
			+ " case_type_cd = ? ," + " investigator_assigned_time = ? ," + " hospitalized_ind_cd = ? ,"
			+ " hospitalized_admin_time = ? ," + " hospitalized_discharge_time = ? ,"
			+ " hospitalized_duration_amt = ? ," + " pregnant_ind_cd = ? ,"
			// +" die_from_illness_ind_cd = ? ,"
			+ " day_care_ind_cd = ? ," + " food_handler_ind_cd = ? ," + " imported_country_cd = ? ,"
			+ " imported_state_cd = ? ," + " imported_city_desc_txt = ? ," + " imported_county_cd = ? ,"
			+ " deceased_time = ? ," + " count_interval_cd = ?," + " priority_cd = ?," + " infectious_from_date = ?,"
			+ " infectious_to_date = ?," + " contact_inv_status_cd = ?," + " contact_inv_txt = ?, "
			+ " referral_basis_cd = ?, " + " curr_process_state_cd = ?, " + " inv_priority_cd = ?, "
			+ " coinfection_id=? " + " WHERE public_health_case_uid = ? ";
	
	private static final String UPDATE_PUBLIC_HEALTH_CASE_2 = "AND version_ctrl_nbr = ?";

	private static final String SELECT_PUBLIC_HEALTH_CASE = "SELECT public_health_case_uid \"publicHealthCaseUid\","
			+ " activity_duration_amt \"activityDurationAmt\","
			+ " activity_duration_unit_cd \"activityDurationUnitCd\"," + " activity_from_time \"activityFromTime\","
			+ " activity_to_time \"activityToTime\"," + " add_reason_cd \"addReasonCd\"," + " add_time \"addTime\","
			+ " add_user_id \"addUserId\"," + " case_class_cd \"caseClassCd\"," + " cd \"cd\","
			+ " cd_desc_txt \"cdDescTxt\"," + " cd_system_cd \"cdSystemCd\","
			+ " cd_system_desc_txt \"cdSystemDescTxt\"," + " confidentiality_cd \"confidentialityCd\","
			+ " confidentiality_desc_txt \"confidentialityDescTxt\"," + " detection_method_cd \"detectionMethodCd\","
			+ " detection_method_desc_txt \"detectionMethodDescTxt\"," + " disease_imported_cd \"diseaseImportedCd\","
			+ " disease_imported_desc_txt \"diseaseImportedDescTxt\","
			+ " effective_duration_amt \"effectiveDurationAmt\","
			+ " effective_duration_unit_cd \"effectiveDurationUnitCd\"," + " effective_from_time \"effectiveFromTime\","
			+ " effective_to_time \"effectiveToTime\", " + " group_case_cnt \"groupCaseCnt\","
			+ " investigation_status_cd \"investigationStatusCd\"," + " jurisdiction_cd \"jurisdictionCd\","
			+ " last_chg_reason_cd \"lastChgReasonCd\"," + " last_chg_time \"lastChgTime\","
			+ " last_chg_user_id \"lastChgUserId\"," + " local_id \"localId\"," + " mmwr_week \"mmwrWeek\","
			+ " mmwr_year \"mmwrYear\","
			// +" org_access_permis \"orgAccessPermis\","
			+ " outbreak_name \"outbreakName\"," + " outbreak_from_time \"outbreakFromTime\","
			+ " outbreak_ind \"outbreakInd\"," + " outbreak_to_time \"outbreakToTime\"," + " outcome_cd \"outcomeCd\","
			+ " patient_group_id \"patientGroupId\","
			// +" prog_area_access_permis \"progAreaAccessPermis\","
			+ " prog_area_cd \"progAreaCd\"," + " record_status_cd \"recordStatusCd\","
			+ " record_status_time \"recordStatusTime\"," + " repeat_nbr \"repeatNbr\"," + " rpt_cnty_cd \"rptCntyCd\","
			+ " status_cd \"statusCd\"," + " status_time \"statusTime\","
			+ " transmission_mode_cd \"transmissionModeCd\","
			+ " transmission_mode_desc_txt \"transmissionModeDescTxt\"," + " txt \"txt\","
			+ " user_affiliation_txt \"userAffiliationTxt\", " + "pat_age_at_onset \"patAgeAtOnset\","
			+ " pat_age_at_onset_unit_cd \"patAgeAtOnsetUnitCd\"," + " rpt_form_cmplt_time \"rptFormCmpltTime\","
			+ " rpt_source_cd \"rptSourceCd\"," + " rpt_source_cd_desc_txt \"rptSourceCdDescTxt\","
			+ " rpt_to_county_time \"rptToCountyTime\"," + " rpt_to_state_time \"rptToStateTime\","
			+ " diagnosis_time \"diagnosisTime\" ," + " program_jurisdiction_oid \"programJurisdictionOid\" ,"
			+ " shared_ind \"sharedInd\" ," + " version_ctrl_nbr \"versionCtrlNbr\" , "
			+ " case_type_cd \"caseTypeCd\" , " + " investigator_assigned_time \"investigatorAssignedTime\" ,"
			+ " hospitalized_ind_cd \"hospitalizedIndCd\" ," + " hospitalized_admin_time \"hospitalizedAdminTime\" ,"
			+ " hospitalized_discharge_time \"hospitalizedDischargeTime\" ,"
			+ " hospitalized_duration_amt \"hospitalizedDurationAmt\" ," + " pregnant_ind_cd \"pregnantIndCd\" ,"
			// +" die_from_illness_ind_cd \"dieFromIllnessIndCD\" ,"
			+ " day_care_ind_cd \"dayCareIndCd\" ," + " food_handler_ind_cd \"foodHandlerIndCd\" ,"
			+ " imported_country_cd \"importedCountryCd\" ," + " imported_state_cd \"importedStateCd\" ,"
			+ " imported_city_desc_txt \"importedCityDescTxt\" ," + " imported_county_cd \"importedCountyCd\" ,"
			+ " deceased_time \"deceasedTime\" ," + " count_interval_cd \"countIntervalCd\","
			+ " priority_cd \"priorityCd\"," + " infectious_from_date \"infectiousFromDate\","
			+ " infectious_to_date \"infectiousToDate\"," + " contact_inv_status_cd \"contactInvStatus\","
			+ " contact_inv_txt \"contactInvTxt\", " + " referral_basis_cd \"referralBasisCd\", "
			+ " curr_process_state_cd \"currProcessStateCd\", " + " inv_priority_cd \"invPriorityCd\", "
			+ " coinfection_id \"coinfectionId\" " + " FROM " + " public_health_case WHERE public_health_case_uid = ?";

	
	
	
	private static final String SELECT_PUBLIC_HEALTH_CASE_LOACLID = "SELECT public_health_case_uid FROM Public_Health_Case WHERE local_id = ?";

	// For logging
	static final LogUtils logger = new LogUtils(PublicHealthCaseDAOImpl.class.getName());

	public PublicHealthCaseDAOImpl() {
	}

	public long create(Object obj) throws NEDSSSystemException {
		try {
			if (obj != null) {
				phcUID = insertPublicHealthCase((PublicHealthCaseDT) obj);
			}
			return phcUID;
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void store(Object obj) throws NEDSSSystemException, NEDSSConcurrentDataException {
		logger.debug("Start DAO store().");
		try {
			PublicHealthCaseDT phcDT = (PublicHealthCaseDT) obj;
			if (phcDT != null && phcDT.isItNew()
					&& !publicHealthCaseExists(phcDT.getPublicHealthCaseUid().longValue())) {
				create(phcDT);
			} else {
				if (phcDT != null && phcDT.isItDirty()
						&& publicHealthCaseExists(phcDT.getPublicHealthCaseUid().longValue())) {
					updatePublicHealthCase(phcDT);
				}
			}
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		logger.debug("Done ejbStore() for phc.");
	}

	public Object loadObject(long phcUID) throws NEDSSSystemException {
		/**
		 * Selects PublicHealthCaseDT
		 */
		try {
			PublicHealthCaseDT phcDT = selectPublicHealthCase(phcUID);

			return phcDT;
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void remove(long PublicHealthCaseUID) throws NEDSSSystemException {

	}

	public Long findByPrimaryKey(long phcUID) throws NEDSSSystemException {
		try {
			if (publicHealthCaseExists(phcUID))
				return (new Long(phcUID));
			else
				logger.error("Primary key not found in PUBLIC_HEALTH_CASE_TABLE:" + phcUID);
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return null;
	}

	public int getPublicHealthCasesBySupervisorCount(NBSSecurityObj secObj) {
		int resultCount = 0;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		try {
			dbConnection = getConnection();
			String whereClause = "";
			whereClause = secObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT, "phc");
			String query = WumSqlQuery.SUPERVISOR_REVIEW_COUNT
					+ ((whereClause != null && whereClause.length() > 0) ? " and " + whereClause : "");
			preparedStmt = dbConnection.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();
			if (resultSet.next())
				resultCount = resultSet.getInt(1);
			logger.debug("getPublicHealthCasesBySupervisorCount resultCount is " + resultCount);
		} catch (Exception ex) {
			logger.fatal("Error while getting count from " + DataTables.PUBLIC_HEALTH_CASE_TABLE, ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return resultCount;
	}

	public List getPublicHealthCasesBySupervisor(NBSSecurityObj secObj) {
		List arSupervisorVos = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		try {
			dbConnection = getConnection();
			String whereClause = "";
			whereClause = secObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT, "phc");
			String query = WumSqlQuery.SUPERVISOR_REVIEW_SELECT
					+ ((whereClause != null && whereClause.length() > 0) ? " and " + whereClause : "");
			preparedStmt = dbConnection.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();

			resultSetUtils.mapRsToBeanList(resultSet, resultSet.getMetaData(), SupervisorReviewVO.class,
					arSupervisorVos);

			logger.debug("getPublicHealthCasesBySupervisor", query);
		} catch (Exception ex) {
			logger.fatal("Error while getting count from " + DataTables.PUBLIC_HEALTH_CASE_TABLE, ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return arSupervisorVos;
	}

	protected boolean publicHealthCaseExists(long phcUID) throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		boolean returnValue = false;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_PUBLIC_HEALTH_CASE_UID);
			logger.debug("phcUID = " + phcUID);
			preparedStmt.setLong(1, phcUID);
			resultSet = preparedStmt.executeQuery();

			if (!resultSet.next()) {
				returnValue = false;
			} else {
				phcUID = resultSet.getLong(1);
				returnValue = true;
			}
		} catch (SQLException sex) {
			logger.fatal("SQLException while checking for an"
					+ " existing public health case's uid in PUBLIC_HEALTH_CASE_TABLE -> " + phcUID, sex);
			throw new NEDSSDAOSysException(sex.getMessage());
		} catch (NEDSSSystemException nsex) {
			logger.fatal("Exception while getting dbConnection for checking for an"
					+ " existing public health case's uid -> " + phcUID, nsex);
			throw new NEDSSDAOSysException(nsex.getMessage());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return returnValue;
	}

	public Long getpublicHealthCaseUid(String localId) throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		Long phcUID = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_PUBLIC_HEALTH_CASE_LOACLID);
			logger.debug("localId = " + localId);
			preparedStmt.setString(1, localId);
			resultSet = preparedStmt.executeQuery();

			if (!resultSet.next()) {

			} else {
				phcUID = resultSet.getLong(1);
				return phcUID;
			}
		} catch (SQLException sex) {
			logger.fatal("SQLException while checking for an"
					+ " existing public health case's uid in PUBLIC_HEALTH_CASE_TABLE -> " + phcUID, sex);
			throw new NEDSSDAOSysException(sex.getMessage());
		} catch (NEDSSSystemException nsex) {
			logger.fatal("Exception while getting dbConnection for checking for an"
					+ " existing public health case's uid -> " + phcUID, nsex);
			throw new NEDSSDAOSysException(nsex.getMessage());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return phcUID;
	}

	private long insertPublicHealthCase(PublicHealthCaseDT phcDT) throws NEDSSSystemException {
		/**
		 * Starts inserting a new public health case
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		// ResultSet resultSet = null;
		String localUID = null;
		String coInfectionGroupID = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;

		try {
			/**
			 * Inserts into act table for a new public health case
			 */
			if (phcDT.getCaseTypeCd().equals(NEDSSConstants.I) && (phcDT.getInvestigationStatusCd() == null
					|| phcDT.getInvestigationStatusCd().trim().equals("") || phcDT.getProgAreaCd() == null
					|| phcDT.getProgAreaCd().trim().equals("") || phcDT.getJurisdictionCd() == null
					|| phcDT.getJurisdictionCd().equals(""))) {

				String error = "********#Investigation canot be inserted with partial information for these fields : Program Area Cd = "
						+ phcDT.getProgAreaCd() + " Jurisdiction Code = " + phcDT.getJurisdictionCd()
						+ " Investigation Status = " + phcDT.getInvestigationStatusCd();
				logger.debug(error + "PublicHealthCaseDT :" + phcDT.toString());
				throw new NEDSSDAOSysException(error);
			}
			uidGen = new UidGeneratorHelper();
			phcUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
			logger.debug("New phcuid is : " + phcUID);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_ACTIVITY);
			int i = 1;
			preparedStmt.setLong(i++, phcUID);
			preparedStmt.setString(i++, NEDSSConstants.PUBLIC_HEALTH_CASE_CLASS_CODE);
			preparedStmt.setString(i++, NEDSSConstants.EVENT_MOOD_CODE);
			resultCount = preparedStmt.executeUpdate();
		} catch (Exception e) {
			logger.fatal("Exception while getting dbConnection for checking for an"
					+ " existing public health case's uid -> " + phcUID, e);
			throw new NEDSSDAOSysException(e.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);

		}
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_PUBLIC_HEALTH_CASE);
			uidGen = new UidGeneratorHelper();
			localUID = uidGen.getLocalID(UidClassCodes.PUBLIC_HEALTH_CASE_CLASS_CODE);
			logger.debug("New localuid for PHC is: " + localUID);

			if (phcDT.getCoinfectionId() != null
					&& phcDT.getCoinfectionId().equalsIgnoreCase(NEDSSConstants.COINFCTION_GROUP_ID_NEW_CODE)) {
				coInfectionGroupID = uidGen.getLocalID(UidClassCodes.COINFECTION_GROUP_CLASS_CODE);
				phcDT.setCoinfectionId(coInfectionGroupID);
				logger.debug("New localuid for COINFECTION_GROUP_CLASS_CODE is: " + coInfectionGroupID);
			}

			int i = 1;

			preparedStmt.setLong(i++, phcUID);
			preparedStmt.setString(i++, phcDT.getActivityDurationAmt());
			preparedStmt.setString(i++, phcDT.getActivityDurationUnitCd());
			if (phcDT.getActivityFromTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getActivityFromTime());
			if (phcDT.getActivityToTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getActivityToTime());
			// 5
			preparedStmt.setString(i++, phcDT.getAddReasonCd());
			if (phcDT.getAddTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getAddTime());
			if (phcDT.getAddUserId() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, (phcDT.getAddUserId()).longValue());
			preparedStmt.setString(i++, phcDT.getCaseClassCd());
			preparedStmt.setString(i++, phcDT.getCd());
			preparedStmt.setString(i++, phcDT.getCdDescTxt());
			// 10
			// preparedStmt.setString(i++, phcDT.getClassStatusCd());
			// preparedStmt.setString(i++, phcDT.getConditionCd());
			preparedStmt.setString(i++, phcDT.getCdSystemCd());
			preparedStmt.setString(i++, phcDT.getCdSystemDescTxt());
			preparedStmt.setString(i++, phcDT.getConfidentialityCd());
			preparedStmt.setString(i++, phcDT.getConfidentialityDescTxt());
			// preparedStmt.setString(i++, phcDT.getConfirmationMethodCd());
			// 15
			// preparedStmt.setString(i++, phcDT.getConfirmationDescTxt());
			preparedStmt.setString(i++, phcDT.getDetectionMethodCd());
			preparedStmt.setString(i++, phcDT.getDetectionMethodDescTxt());
			preparedStmt.setString(i++, phcDT.getDiseaseImportedCd());
			preparedStmt.setString(i++, phcDT.getDiseaseImportedDescTxt());
			// 20
			preparedStmt.setString(i++, phcDT.getEffectiveDurationAmt());
			preparedStmt.setString(i++, phcDT.getEffectiveDurationUnitCd());
			if (phcDT.getEffectiveFromTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getEffectiveFromTime());
			if (phcDT.getEffectiveToTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getEffectiveToTime());
			// preparedStmt.setString(i++, phcDT.getEtiologicStatusCd());
			// 25
			if (phcDT.getGroupCaseCnt() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setInt(i++, phcDT.getGroupCaseCnt().intValue());
			preparedStmt.setString(i++, phcDT.getInvestigationStatusCd());
			preparedStmt.setString(i++, phcDT.getJurisdictionCd());
			preparedStmt.setString(i++, phcDT.getLastChgReasonCd());
			if (phcDT.getLastChgTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getLastChgTime());
			// 30
			if (phcDT.getLastChgUserId() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, (phcDT.getLastChgUserId()).longValue());
			preparedStmt.setString(i++, localUID);
			preparedStmt.setString(i++, phcDT.getMmwrWeek());
			preparedStmt.setString(i++, phcDT.getMmwrYear());
			// preparedStmt.setString(i++, phcDT.getOrgAccessPermis());
			preparedStmt.setString(i++, phcDT.getOutbreakName());
			if (phcDT.getOutbreakFromTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getOutbreakFromTime());
			preparedStmt.setString(i++, phcDT.getOutbreakInd());
			// 35
			if (phcDT.getOutbreakToTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getOutbreakToTime());
			preparedStmt.setString(i++, phcDT.getOutcomeCd());
			// preparedStmt.setString(i++, phcDT.getPartyClassTypeCd());
			if (phcDT.getPatientGroupId() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, phcDT.getPatientGroupId().longValue());
			// preparedStmt.setString(i++, phcDT.getProgAreaAccessPermis());
			// 40
			preparedStmt.setString(i++, phcDT.getProgAreaCd());
			preparedStmt.setString(i++, phcDT.getRecordStatusCd());
			if (phcDT.getRecordStatusTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getRecordStatusTime());
			if (phcDT.getRepeatNbr() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, phcDT.getRepeatNbr().longValue());
			preparedStmt.setString(i++, phcDT.getRptCntyCd());
			// 45
			if (phcDT.getStatusCd() == null) {
				preparedStmt.setString(i++, phcDT.getStatusCd());
			} else
				preparedStmt.setString(i++, phcDT.getStatusCd());
			if (phcDT.getStatusTime() == null) {
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			} else
				preparedStmt.setTimestamp(i++, phcDT.getStatusTime());
			preparedStmt.setString(i++, phcDT.getTransmissionModeCd());
			preparedStmt.setString(i++, phcDT.getTransmissionModeDescTxt());
			preparedStmt.setString(i++, phcDT.getTxt());
			// 50
			preparedStmt.setString(i++, phcDT.getUserAffiliationTxt());
			preparedStmt.setString(i++, phcDT.getPatAgeAtOnset());
			preparedStmt.setString(i++, phcDT.getPatAgeAtOnsetUnitCd());
			if (phcDT.getRptFormCmpltTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getRptFormCmpltTime());
			preparedStmt.setString(i++, phcDT.getRptSourceCd());
			// 55
			preparedStmt.setString(i++, phcDT.getRptSourceCdDescTxt());
			if (phcDT.getRptToCountyTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getRptToCountyTime());
			if (phcDT.getRptToStateTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getRptToStateTime());
			if (phcDT.getDiagnosisTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getDiagnosisTime());
			logger.debug("OID is: " + phcDT.getProgramJurisdictionOid());
			if (phcDT.getProgramJurisdictionOid() == null) {
				logger.debug("OID long value is: " + phcDT.getProgramJurisdictionOid());
				preparedStmt.setNull(i++, Types.INTEGER);
			} else {
				logger.debug("OID long value is: " + phcDT.getProgramJurisdictionOid().longValue());
				preparedStmt.setLong(i++, phcDT.getProgramJurisdictionOid().longValue());
			}
			preparedStmt.setString(i++, phcDT.getSharedInd());

			if (phcDT.getCaseTypeCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getCaseTypeCd());

			preparedStmt.setInt(i++, phcDT.getVersionCtrlNbr().intValue());

			if (phcDT.getInvestigatorAssignedTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getInvestigatorAssignedTime());

			if (phcDT.getHospitalizedIndCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getHospitalizedIndCd());

			if (phcDT.getHospitalizedAdminTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getHospitalizedAdminTime());

			if (phcDT.getHospitalizedDischargeTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getHospitalizedDischargeTime());

			if (phcDT.getHospitalizedDurationAmt() == null)
				preparedStmt.setNull(i++, Types.NUMERIC);
			else
				preparedStmt.setInt(i++, phcDT.getHospitalizedDurationAmt().intValue());

			if (phcDT.getPregnantIndCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getPregnantIndCd());

			/*
			 * if(phcDT.getDieFromIllnessIndCD() == null)
			 * preparedStmt.setNull(i++, Types.VARCHAR); else
			 * preparedStmt.setString(i++, phcDT.getDieFromIllnessIndCD());
			 */

			if (phcDT.getDayCareIndCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getDayCareIndCd());

			if (phcDT.getFoodHandlerIndCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getFoodHandlerIndCd());

			if (phcDT.getImportedCountryCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getImportedCountryCd());

			if (phcDT.getImportedStateCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getImportedStateCd());

			if (phcDT.getImportedCityDescTxt() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getImportedCityDescTxt());

			if (phcDT.getImportedCountyCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getImportedCountyCd());

			if (phcDT.getDeceasedTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getDeceasedTime());

			if (phcDT.getCountIntervalCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getCountIntervalCd());
			// added for contact tracing
			if (phcDT.getPriorityCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getPriorityCd());

			if (phcDT.getInfectiousFromDate() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getInfectiousFromDate());

			if (phcDT.getInfectiousToDate() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, phcDT.getInfectiousToDate());

			if (phcDT.getContactInvStatus() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getContactInvStatus());

			if (phcDT.getContactInvTxt() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getContactInvTxt());

			if (phcDT.getReferralBasisCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getReferralBasisCd());

			if (phcDT.getCurrProcessStateCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getCurrProcessStateCd());
			if (phcDT.getInvPriorityCd() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getInvPriorityCd());
			if (phcDT.getCoinfectionId() == null)
				preparedStmt.setNull(i++, Types.VARCHAR);
			else
				preparedStmt.setString(i++, phcDT.getCoinfectionId());

			resultCount = preparedStmt.executeUpdate();
			logger.debug("done insert a new public health case! phcUID = " + phcUID);
			phcDT.setItNew(false);
			phcDT.setItDirty(false);
			phcDT.setItDelete(false);
			return phcUID;
		} catch (SQLException sex) {
			logger.fatal("SQLException while inserting " + "a new public health case into PUBLIC_HEALTH_CASE_TABLE: \n",
					sex);
			sex.printStackTrace();
			throw new NEDSSDAOSysException(sex.toString());
		} catch (Exception ex) {
			logger.fatal("Error while inserting into PUBLIC_HEALTH_CASE_TABLE, id = " + phcUID, ex);
			ex.printStackTrace();
			throw new NEDSSSystemException(ex.toString());
		}

		finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}// end of inserting public health case

	private void updatePublicHealthCase(PublicHealthCaseDT publicHealthCase)
			throws NEDSSSystemException, NEDSSConcurrentDataException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;

		/**
		 * Updates a PublicHealthCaseDT object
		 */

		if (publicHealthCase != null) {

			try {
								
				if (publicHealthCase.getCaseTypeCd().equals(NEDSSConstants.I)
						&& (publicHealthCase.getInvestigationStatusCd() == null
						|| publicHealthCase.getInvestigationStatusCd().trim().equals("")
						|| publicHealthCase.getProgAreaCd() == null
						|| publicHealthCase.getProgAreaCd().trim().equals("")
						|| publicHealthCase.getJurisdictionCd() == null
						|| publicHealthCase.getJurisdictionCd().equals(""))) {

					String error = "********#Investigation canot be updated with partial information for these fields : Program Area Cd = "
							+ publicHealthCase.getProgAreaCd() + " Jurisdiction Code = "
							+ publicHealthCase.getJurisdictionCd() + " Investigation Status = "
							+ publicHealthCase.getInvestigationStatusCd();
					logger.debug(error + "PublicHealthCaseDT :" + publicHealthCase.toString());
					throw new NEDSSDAOSysException(error);
				}

				dbConnection = getConnection();
				/**TODO PKS UPDATE FOR MERGE INVESTIGATIONS
				 * if (publicHealthCase.isReentrant()) {
				 
					//This is a very special scenario that is applicable to merge investigations only!!!!!!
					preparedStmt = dbConnection.prepareStatement(UPDATE_PUBLIC_HEALTH_CASE_1);
				} else {
					preparedStmt = dbConnection.prepareStatement(UPDATE_PUBLIC_HEALTH_CASE_1+UPDATE_PUBLIC_HEALTH_CASE_2 );
				}*/

				preparedStmt = dbConnection.prepareStatement(UPDATE_PUBLIC_HEALTH_CASE_1+UPDATE_PUBLIC_HEALTH_CASE_2 );
				int i = 1;
				preparedStmt.setString(i++, publicHealthCase.getActivityDurationAmt());
				preparedStmt.setString(i++, publicHealthCase.getActivityDurationUnitCd());
				if (publicHealthCase.getActivityFromTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getActivityFromTime());

				if (publicHealthCase.getActivityToTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getActivityToTime());

				preparedStmt.setString(i++, publicHealthCase.getAddReasonCd());
				// 5
				if (publicHealthCase.getAddTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getAddTime());
				if (publicHealthCase.getAddUserId() == null)
					preparedStmt.setNull(i++, Types.INTEGER);
				else
					preparedStmt.setLong(i++, (publicHealthCase.getAddUserId()).longValue());
				preparedStmt.setString(i++, publicHealthCase.getCaseClassCd());
				preparedStmt.setString(i++, publicHealthCase.getCd());
				preparedStmt.setString(i++, publicHealthCase.getCdDescTxt());
				// preparedStmt.setString(i++,
				// publicHealthCase.getClassStatusCd());
				// 10
				// preparedStmt.setString(i++,
				// publicHealthCase.getConditionCd());
				preparedStmt.setString(i++, publicHealthCase.getCdSystemCd());
				preparedStmt.setString(i++, publicHealthCase.getCdSystemDescTxt());
				preparedStmt.setString(i++, publicHealthCase.getConfidentialityCd());
				preparedStmt.setString(i++, publicHealthCase.getConfidentialityDescTxt());
				// preparedStmt.setString(i++,
				// publicHealthCase.getConfirmationMethodCd());
				// preparedStmt.setString(i++,
				// publicHealthCase.getConfirmationDescTxt());
				// 15
				preparedStmt.setString(i++, publicHealthCase.getDetectionMethodCd());
				preparedStmt.setString(i++, publicHealthCase.getDetectionMethodDescTxt());
				preparedStmt.setString(i++, publicHealthCase.getDiseaseImportedCd());
				preparedStmt.setString(i++, publicHealthCase.getDiseaseImportedDescTxt());
				preparedStmt.setString(i++, publicHealthCase.getEffectiveDurationAmt());
				// 20
				preparedStmt.setString(i++, publicHealthCase.getEffectiveDurationUnitCd());

				if (publicHealthCase.getEffectiveFromTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getEffectiveFromTime());

				if (publicHealthCase.getEffectiveToTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getEffectiveToTime());
				// preparedStmt.setString(i++,
				// publicHealthCase.getEtiologicStatusCd());
				if (publicHealthCase.getGroupCaseCnt() == null)
					preparedStmt.setNull(i++, Types.INTEGER);
				else
					preparedStmt.setInt(i++, publicHealthCase.getGroupCaseCnt().intValue());
				// 25
				preparedStmt.setString(i++, publicHealthCase.getInvestigationStatusCd());
				preparedStmt.setString(i++, publicHealthCase.getJurisdictionCd());
				preparedStmt.setString(i++, publicHealthCase.getLastChgReasonCd());

				if (publicHealthCase.getLastChgTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getLastChgTime());

				if (publicHealthCase.getLastChgUserId() == null)
					preparedStmt.setNull(i++, Types.INTEGER);
				else
					preparedStmt.setLong(i++, publicHealthCase.getLastChgUserId().longValue());
				// 30
				// preparedStmt.setString(i++, publicHealthCase.getLocalId());
				preparedStmt.setString(i++, publicHealthCase.getMmwrWeek());
				preparedStmt.setString(i++, publicHealthCase.getMmwrYear());
				// preparedStmt.setString(i++,
				// publicHealthCase.getOrgAccessPermis());
				preparedStmt.setString(i++, publicHealthCase.getOutbreakName());
				preparedStmt.setTimestamp(i++, publicHealthCase.getOutbreakFromTime());
				preparedStmt.setString(i++, publicHealthCase.getOutbreakInd());
				preparedStmt.setTimestamp(i++, publicHealthCase.getOutbreakToTime());
				// 35
				preparedStmt.setString(i++, publicHealthCase.getOutcomeCd());
				// preparedStmt.setString(i++,
				// publicHealthCase.getPartyClassTypeCd());
				if (publicHealthCase.getPatientGroupId() == null)
					preparedStmt.setNull(i++, Types.INTEGER);
				else
					preparedStmt.setLong(i++, publicHealthCase.getPatientGroupId().longValue());
				// preparedStmt.setString(i++,
				// publicHealthCase.getProgAreaAccessPermis());
				preparedStmt.setString(i++, publicHealthCase.getProgAreaCd());
				// 40
				preparedStmt.setString(i++, publicHealthCase.getRecordStatusCd());
				preparedStmt.setTimestamp(i++, publicHealthCase.getRecordStatusTime());
				if (publicHealthCase.getRepeatNbr() == null)
					preparedStmt.setNull(i++, Types.INTEGER);
				else
					preparedStmt.setInt(i++, publicHealthCase.getRepeatNbr().intValue());
				preparedStmt.setString(i++, publicHealthCase.getRptCntyCd());
				if (publicHealthCase.getStatusCd() == null) {
					preparedStmt.setString(i++, publicHealthCase.getStatusCd());
				} else
					preparedStmt.setString(i++, publicHealthCase.getStatusCd().trim());
				// 45
				if (publicHealthCase.getStatusTime() == null) {
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				} else
					preparedStmt.setTimestamp(i++, publicHealthCase.getStatusTime());

				preparedStmt.setString(i++, publicHealthCase.getTransmissionModeCd());
				preparedStmt.setString(i++, publicHealthCase.getTransmissionModeDescTxt());
				preparedStmt.setString(i++, publicHealthCase.getTxt());
				preparedStmt.setString(i++, publicHealthCase.getUserAffiliationTxt());
				// 50
				/*
				 * if(publicHealthCase.getConfirmationMethodSeqNbr() == null)
				 * preparedStmt.setNull(i++, Types.INTEGER); else
				 * preparedStmt.setInt(i++,
				 * publicHealthCase.getConfirmationMethodSeqNbr().intValue());
				 */
				preparedStmt.setString(i++, publicHealthCase.getPatAgeAtOnset());
				preparedStmt.setString(i++, publicHealthCase.getPatAgeAtOnsetUnitCd());
				if (publicHealthCase.getRptFormCmpltTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getRptFormCmpltTime());
				preparedStmt.setString(i++, publicHealthCase.getRptSourceCd());
				preparedStmt.setString(i++, publicHealthCase.getRptSourceCdDescTxt());
				// 55
				if (publicHealthCase.getRptToCountyTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getRptToCountyTime());
				if (publicHealthCase.getRptToStateTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getRptToStateTime());
				if (publicHealthCase.getDiagnosisTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getDiagnosisTime());
				if (publicHealthCase.getProgramJurisdictionOid() == null)
					preparedStmt.setNull(i++, Types.BIGINT);
				else
					preparedStmt.setLong(i++, publicHealthCase.getProgramJurisdictionOid().longValue());
				preparedStmt.setString(i++, publicHealthCase.getSharedInd());
				if (publicHealthCase.getVersionCtrlNbr() == null)// 42
				{
					logger.error("** VersionCtrlNbr cannot be null *** Public Health Case UID :"
							+ publicHealthCase.getPublicHealthCaseUid());
				} else {
					logger.debug("VersionCtrlNbr exists" + publicHealthCase.getVersionCtrlNbr());
					preparedStmt.setInt(i++, (publicHealthCase.getVersionCtrlNbr().intValue()));
					logger.debug("new versioncontrol number:" + (publicHealthCase.getVersionCtrlNbr().intValue() + 1));
				}

				if (publicHealthCase.getCaseTypeCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getCaseTypeCd());

				if (publicHealthCase.getInvestigatorAssignedTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getInvestigatorAssignedTime());

				if (publicHealthCase.getHospitalizedIndCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getHospitalizedIndCd());

				if (publicHealthCase.getHospitalizedAdminTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getHospitalizedAdminTime());

				if (publicHealthCase.getHospitalizedDischargeTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getHospitalizedDischargeTime());

				if (publicHealthCase.getHospitalizedDurationAmt() == null)
					preparedStmt.setNull(i++, Types.NUMERIC);
				else
					preparedStmt.setInt(i++, publicHealthCase.getHospitalizedDurationAmt().intValue());

				if (publicHealthCase.getPregnantIndCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getPregnantIndCd());

				/*
				 * if(publicHealthCase.getDieFromIllnessIndCD() == null)
				 * preparedStmt.setNull(i++, Types.VARCHAR); else
				 * preparedStmt.setString(i++,
				 * publicHealthCase.getDieFromIllnessIndCD());
				 */

				if (publicHealthCase.getDayCareIndCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getDayCareIndCd());

				if (publicHealthCase.getFoodHandlerIndCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getFoodHandlerIndCd());

				if (publicHealthCase.getImportedCountryCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getImportedCountryCd());

				if (publicHealthCase.getImportedStateCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getImportedStateCd());

				if (publicHealthCase.getImportedCityDescTxt() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getImportedCityDescTxt());

				if (publicHealthCase.getImportedCountyCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getImportedCountyCd());

				if (publicHealthCase.getDeceasedTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getDeceasedTime());

				if (publicHealthCase.getCountIntervalCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getCountIntervalCd());

				// added for contact tracing
				if (publicHealthCase.getPriorityCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getPriorityCd());

				if (publicHealthCase.getInfectiousFromDate() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getInfectiousFromDate());

				if (publicHealthCase.getInfectiousToDate() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++, publicHealthCase.getInfectiousToDate());

				if (publicHealthCase.getContactInvStatus() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getContactInvStatus());

				if (publicHealthCase.getContactInvTxt() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getContactInvTxt());

				if (publicHealthCase.getReferralBasisCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getReferralBasisCd());

				if (publicHealthCase.getCurrProcessStateCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getCurrProcessStateCd());

				if (publicHealthCase.getInvPriorityCd() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getInvPriorityCd());
				if (publicHealthCase.getCoinfectionId() == null)
					preparedStmt.setNull(i++, Types.VARCHAR);
				else
					preparedStmt.setString(i++, publicHealthCase.getCoinfectionId());

				preparedStmt.setLong(i++, publicHealthCase.getPublicHealthCaseUid().longValue());
				/**TODO PKS UPDATE FOR MERGE INVESTIGATIONS
				  if (!publicHealthCase.isReentrant()) {
				
					preparedStmt.setInt(i++, (publicHealthCase.getVersionCtrlNbr().intValue() - 1));
				}*/
				preparedStmt.setInt(i++, (publicHealthCase.getVersionCtrlNbr().intValue() - 1));
				

				resultCount = preparedStmt.executeUpdate();
				logger.debug("Done updating a PublicHealthCase object");
				if (resultCount != 1) {
					logger.error("Error: none or more than one publicHealthCase updated at a time, " + "resultCount = "
							+ resultCount);
					throw new NEDSSConcurrentDataException(
							"NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
				}
			} catch (SQLException sex) {
				logger.fatal("SQLException while updating " + "a PublicHealthCase, \n", sex);
				throw new NEDSSDAOSysException(sex.toString());
			} finally {
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
		}
	}// end of updating PublicHealthCase table

	private PublicHealthCaseDT selectPublicHealthCase(long phcUID) throws NEDSSSystemException {
		PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		/**
		 * Selects a PublicHealthCase from PublicHealthCase table
		 */
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_PUBLIC_HEALTH_CASE);
			preparedStmt.setLong(1, phcUID);
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			publicHealthCaseDT = (PublicHealthCaseDT) resultSetUtils.mapRsToBean(resultSet, resultSetMetaData,
					publicHealthCaseDT.getClass());
			publicHealthCaseDT.setItNew(false);
			publicHealthCaseDT.setItDirty(false);
			publicHealthCaseDT.setItDelete(false);
		} catch (SQLException sex) {
			logger.fatal("SQLException while selecting " + "a PublicHealthCase vo; uid = " + phcUID, sex);
			throw new NEDSSDAOSysException(sex.getMessage());
		} catch (Exception ex) {
			logger.fatal("Exception while selecting " + "a PublicHealthCase vo; uid = " + phcUID, ex);
			throw new NEDSSDAOSysException(ex.getMessage());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("return a PublicHealthCase object");
		return publicHealthCaseDT;
	}// end of selecting a PublicHealthCase

	
//	public Collection<Object> getPublicHealthCaseDTCollectionFromPHCLocalIdList(String listPhc) throws NEDSSSystemException {
//		PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();
//		Connection dbConnection = null;
//		PreparedStatement preparedStmt = null;
//		ResultSet resultSet = null;
//		ResultSetMetaData resultSetMetaData = null;
//		ResultSetUtils resultSetUtils = new ResultSetUtils();
//		Collection<Object> listPHCDT = new ArrayList<Object>();
//		/**
//		 * Selects a PublicHealthCase from PublicHealthCase table
//		 */
//		try {
//			dbConnection = getConnection();
//			preparedStmt = dbConnection.prepareStatement(SELECT_PUBLIC_HEALTH_CASE_FROM_LOCAL_ID_LIST);
//			preparedStmt.setString(1, listPhc);
//			resultSet = preparedStmt.executeQuery();
//			
//			
//			while(resultSet.next()){
//			resultSetMetaData = resultSet.getMetaData();
//			publicHealthCaseDT = (PublicHealthCaseDT) resultSetUtils.mapRsToBean(resultSet, resultSetMetaData,
//					publicHealthCaseDT.getClass());
//			publicHealthCaseDT.setItNew(false);
//			publicHealthCaseDT.setItDirty(false);
//			publicHealthCaseDT.setItDelete(false);
//			listPHCDT.add(publicHealthCaseDT);
//			}
//		} catch (SQLException sex) {
//			logger.fatal("SQLException while selecting " + "a PublicHealthCase vo; uid = " + phcUID, sex);
//			throw new NEDSSDAOSysException(sex.getMessage());
//		} catch (Exception ex) {
//			logger.fatal("Exception while selecting " + "a PublicHealthCase vo; uid = " + phcUID, ex);
//			throw new NEDSSDAOSysException(ex.getMessage());
//		} finally {
//			closeResultSet(resultSet);
//			closeStatement(preparedStmt);
//			releaseConnection(dbConnection);
//		}
//		logger.debug("return a PublicHealthCase object");
//		return listPHCDT;
//	}// end of selecting a PublicHealthCase
//	
//	
	public static void main(String argments[]) {
		PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
		PublicHealthCaseDT phcDT = new PublicHealthCaseDT();
		ArrayList<Object> cmDTColl = new ArrayList<Object>();
		ConfirmationMethodDT cmDT1 = new ConfirmationMethodDT();
		ConfirmationMethodDT cmDT2 = new ConfirmationMethodDT();
		// phcDT.setAddUserId(new Long(100));
		cmDTColl.add(cmDT1);
		cmDTColl.add(cmDT2);
		phcVO.setThePublicHealthCaseDT(phcDT);
		phcVO.setTheConfirmationMethodDTCollection(cmDTColl);

		try {

			PublicHealthCaseDAOImpl phcDAO = new PublicHealthCaseDAOImpl();
			phcDAO.create(phcVO);
		} catch (Exception ex) {
			logger.error("Exception =" + ex.getMessage(), ex);

		}
	}

	public boolean publicHealthCaseExist(PublicHealthCaseVO phcVO) throws NEDSSSystemException {

		PublicHealthCaseDT phcDT = phcVO.getThePublicHealthCaseDT();
		// Retrieve Surveillance Method(SUM117) from answerCollection
		String SUM117 = "";
		Collection<Object> answerColl = phcVO.getNbsAnswerCollection() == null ? new ArrayList<Object>()
				: phcVO.getNbsAnswerCollection();
		Iterator<Object> iter = answerColl.iterator();
		while (iter.hasNext()) {
			NbsCaseAnswerDT aDT = (NbsCaseAnswerDT) iter.next();
			if (aDT.getNbsQuestionUid() != null && aDT.getNbsQuestionUid().intValue() == 7046)
				SUM117 = aDT.getAnswerTxt();
		}

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		boolean returnValue = false;
		String SELECT_PUBLIC_HEALTH_CASE_DT = "SELECT phc.public_health_case_uid FROM Public_Health_Case phc INNER JOIN NBS_case_answer nca ON phc.public_health_case_uid = nca.act_uid WHERE phc.cd = ? and "
				+ " phc.rpt_cnty_cd = ? and  phc.case_class_cd = ? and  phc.mmwr_week = ? and  phc.mmwr_year = ?  and  phc.record_status_cd = ? AND nca.nbs_question_uid = 7046 AND nca.answer_txt = ? ";

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_PUBLIC_HEALTH_CASE_DT);
			preparedStmt.setString(1, phcDT.getCd());
			preparedStmt.setString(2, phcDT.getRptCntyCd());
			preparedStmt.setString(3, phcDT.getCaseClassCd());
			preparedStmt.setString(4, phcDT.getMmwrWeek());
			preparedStmt.setString(5, phcDT.getMmwrYear());
			preparedStmt.setString(6, phcDT.getRecordStatusCd());
			preparedStmt.setString(7, SUM117);

			resultSet = preparedStmt.executeQuery();

			if (!resultSet.next()) {
				returnValue = false;
			} else {
				phcUID = resultSet.getLong(1);
				returnValue = true;
			}
		} catch (SQLException sex) {
			logger.fatal("SQLException while checking for an" + " existing phcDT in PUBLIC_HEALTH_CASE_TABLE:", sex);
			throw new NEDSSDAOSysException(sex.getMessage());
		} catch (NEDSSSystemException nsex) {
			logger.fatal("Exception while getting dbConnection for checking for an existing phcDT ", nsex);
			throw new NEDSSDAOSysException(nsex.getMessage());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return returnValue;
	}

	public PublicHealthCaseDT getOpenPublicHealthCaseWithInvestigatorDT(Long uid) throws NEDSSSystemException {
		PublicHealthCaseDT phcDT = new PublicHealthCaseDT();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		try {
			String SELECT_PHC_AND_INVESTIGATOR = " select  public_health_case.public_health_case_uid \"publicHealthCaseUid\", public_health_case.cd  \"cd\", prog_area_cd \"progAreaCd\",  pat.subject_entity_uid  \"currentPatientUid\", "
					+ " prov.subject_entity_uid  \"currentInvestigatorUid\" "
					+ " from Public_health_case public_health_case " + " left outer join Participation pat "
					+ " on pat.act_uid=public_health_case.public_health_case_uid " + " and pat.type_cd='SubjOfPHC' "
					+ " left outer join Participation prov "
					+ " on prov.act_uid=public_health_case.public_health_case_uid "
					+ " and prov.type_cd='InvestgrOfPHC' " + " where   investigation_status_cd='O' "
					+ " and public_health_case_uid=?";

			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_PHC_AND_INVESTIGATOR);
			preparedStmt.setLong(1, uid);
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();

			phcDT = (PublicHealthCaseDT) resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, phcDT.getClass());

		} catch (SQLException sqlException) {
			logger.fatal(
					"PublicHealthCaseDAOImpl.getOpenPublicHealthCaseWithInvestigatorDT sqlException while getting dbConnection for checking for an existing phcDT ",
					sqlException);
			throw new NEDSSDAOSysException(sqlException.getMessage());
		} catch (NEDSSSystemException nedssException) {
			logger.fatal(
					"PublicHealthCaseDAOImpl.getOpenPublicHealthCaseWithInvestigatorDT nedssException while getting dbConnection for checking for an existing phcDT ",
					nedssException);
			throw new NEDSSDAOSysException(nedssException.getMessage());
		} catch (ResultSetUtilsException resultsetException) {
			logger.fatal(
					"PublicHealthCaseDAOImpl.getOpenPublicHealthCaseWithInvestigatorDT openPublicHealthCaseForLab():resultsetException in result set handling while populate openPublicHealthCaseForLab.",
					resultsetException);
			throw new NEDSSDAOSysException("Error in result set handling while populate openPublicHealthCaseForLab.");
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		return phcDT;
	}

}// end of PublicHealthCaseDAOImpl class
