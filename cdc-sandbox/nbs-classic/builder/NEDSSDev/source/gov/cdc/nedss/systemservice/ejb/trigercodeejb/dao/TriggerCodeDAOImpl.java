package gov.cdc.nedss.systemservice.ejb.trigercodeejb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportTriggerDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportTriggerNNDFieldsDT;
import gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt.TriggerCodesDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

public class TriggerCodeDAOImpl extends DAOBase {

	static final LogUtils logger = new LogUtils(TriggerCodeDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();


	private static final String RETRIEVE_CODE_DESC_FROM_CODE_VALUE_GENERAL = "SELECT "
			+" code_set_nm \"codeSetNm\","
			+" code \"code\","
			+" code_desc_txt \"codeDescTxt\","
			+" code_short_desc_txt \"codeShortDescTxt\","
			+" code_system_cd \"codeSystemCd\","
			+" code_system_desc_txt \"codeSystemDescTxt\","
			+" effective_from_time \"effectiveFromTime\","
			+" effective_to_time \"effectiveToTime\","
			+" indent_level_nbr \"indentLevelNbr\","
			+" is_modifiable_ind \"isModifiableInd\","
			+" parent_is_cd \"parentIsCd\","
			+" nbs_uid \"nbsUid\","
			+" source_concept_id \"sourceConceptId\","
			+" super_code_set_nm \"superCodeSetNm\","
			+" super_code \"superCode\","
			+" status_cd \"statusCd\","
			+" concept_type_cd \"conceptTypeCd\","
			+" concept_code \"conceptCode\","
			+" status_time \"statusTime\","

			+" concept_nm \"conceptNm\","
			+" concept_preferred_nm \"conceptPreferredNm\","
			+" concept_status_cd \"conceptStatusCd\","
			+" concept_status_time \"conceptStatusTime\","
			+" code_system_version_nbr \"codeSystemVersionNbr\","
			+" concept_order_nbr \"conceptOrderNbr\","
			+" admin_comments \"adminComments\","
			+" add_time \"addTime\","
			+" add_user_id \"addUserId\" "
			+" FROM "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
			+"..code_value_general"
			+" WHERE code =? and code_set_nm = 'CODE_SYSTEM_CRLM' order by code_short_desc_txt";



	public Collection<Object> getExportCaseNotifAlgorithm()throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for case Notification: ExportAlgorithm  in CaseNotificationDAO", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		//  logic to check if code has separate table
		sqlQuery = WumSqlQuery.SELECT_EXPORT_ALGORITHM_SQL;
		try {
			preparedStmt = dbConnection
			.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  exportAlgList = new ArrayList<> ();
			if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();


				exportAlgList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
						resultSetMetaData, ExportAlgorithmDT.class, exportAlgList);

				logger.debug("returned questions list");
			}
			return exportAlgList;
		} catch (SQLException se) {
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "Export Algorithm List: CaseNotificationDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger
			.fatal(
					"Error in result set handling while selecting pexport Algorithm: CaseNotificationDAOImpl.",
					reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}


	public ArrayList<Object>  getAlgorithmTriggersforAlgrthm(Long algUid)throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;
		int i = 1;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for case Notification: ExportAlgorithm  in CaseNotificationDAO", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		//  logic to check if code has separate table
		sqlQuery = WumSqlQuery.SELECT_EXPORT_ALGORITHM_TRIGGERS_SQL;
		try {
			preparedStmt = dbConnection
			.prepareStatement(sqlQuery);
			preparedStmt.setLong(i++, algUid.longValue());
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  exportAlgTriggerList = new ArrayList<> ();
			if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();


				exportAlgTriggerList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
						resultSetMetaData, ExportTriggerDT.class, exportAlgTriggerList);

				logger.debug("returned questions list");
			}
			return exportAlgTriggerList;
		} catch (SQLException se) {
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "Export Algorithm List: CaseNotificationDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger
			.fatal(
					"Error in result set handling while selecting pexport Algorithm: CaseNotificationDAOImpl.",
					reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	public Collection<Object> getTriggerCodes()throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;


		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for case Notification: getTriggerCodes  in CaseNotificationDAO", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		//  logic to check if code has separate table
		sqlQuery = WumSqlQuery.SELECT_TRIGGER_CODE_SQL;
		try {
			preparedStmt = dbConnection.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  triggerCodesList = new ArrayList<> ();
			if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();

				triggerCodesList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,resultSetMetaData, TriggerCodesDT.class, triggerCodesList);

				logger.debug("returned questions list");
			}
			return triggerCodesList;



		} catch (SQLException se) {
			logger.fatal("SQLException while selecting Export Algorithm List: TriggerCodeDAOImpl "+se.getMessage(), se);
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "Export Algorithm List: CaseNotificationDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger
			.fatal(
					"Error in result set handling while selecting pexport Algorithm: TriggerCodeDAOImpl.",
					reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	public ExportReceivingFacilityDT getReceivingFacilityByShortNm(String shortNm)throws NEDSSSystemException {
		ExportReceivingFacilityDT exportReceivingFacilityDT = new ExportReceivingFacilityDT();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ArrayList<Object>  pList = new ArrayList<> ();
		String sqlQuery = null;
		String WHERE_CLAUSE = " WHERE receiving_system_short_nm = ?";

		//  logic to check if code has separate table
		sqlQuery = WumSqlQuery.SELECT_RECEIVING_FACILITY_SQL + WHERE_CLAUSE;

		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(sqlQuery);
			preparedStmt.setString(1, shortNm);
			resultSet = preparedStmt.executeQuery();
			logger.debug("CaseNotificationDAOImpl.getReceivingFacilityByShortNm - retrieving receivingShortNm = " + shortNm);
			resultSetMetaData = resultSet.getMetaData();
			pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
					resultSetMetaData,
					exportReceivingFacilityDT.getClass(),
					pList);

			Iterator<Object> itr = pList.iterator();
			if (itr.hasNext())
				return (ExportReceivingFacilityDT)itr.next();
			else {
				logger.debug("CaseNotificationDAOImpl.getReceivingFacilityByShortNm - failed to find receivingShortNm = " + shortNm);
				return null;
			}
		} catch (SQLException se) {
			String errMsg = "SQLException - getReceivingFacilityByShortNm.getReceivingFacility - receivingShortNm = " + shortNm + " - " + se.getMessage();
			logger.fatal(errMsg+se.getMessage(),se);
			throw new NEDSSSystemException(errMsg, se);
		} catch (Exception ex) {
			String errMsg = "CaseNotificationDAOImpl.getReceivingFacilityByShortNm - receivingShortNm = " + shortNm + " - " + ex.getMessage();
			logger.fatal(errMsg+ex.getMessage(),ex);
			throw new NEDSSSystemException(errMsg, ex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	public ExportReceivingFacilityDT getReceivingFacility(Long receivingFacilityUid)throws NEDSSSystemException {
		ExportReceivingFacilityDT exportReceivingFacilityDT = new ExportReceivingFacilityDT();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ArrayList<Object>  pList = new ArrayList<> ();
		String sqlQuery = null;
		String WHERE_CLAUSE = " WHERE export_receiving_facility_uid = ?";

		//  logic to check if code has separate table
		sqlQuery = WumSqlQuery.SELECT_RECEIVING_FACILITY_SQL + WHERE_CLAUSE;

		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(sqlQuery);
			preparedStmt.setLong(1, receivingFacilityUid.longValue());
			resultSet = preparedStmt.executeQuery();
			logger.debug("CaseNotificationDAOImpl.getReceivingFacility - retrieving receivingFacilityUid = " + receivingFacilityUid);
			resultSetMetaData = resultSet.getMetaData();
			pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
					resultSetMetaData,
					exportReceivingFacilityDT.getClass(),
					pList);

			Iterator<Object> itr = pList.iterator();
			if (itr.hasNext())
				return (ExportReceivingFacilityDT)itr.next();
			else {
				logger.debug("CaseNotificationDAOImpl.getReceivingFacility - failed to find receivingFacilityUid = " + receivingFacilityUid);
				return null;
			}
		} catch (SQLException se) {
			String errMsg = "SQLException - CaseNotificationDAOImpl.getReceivingFacility - receivingFacilityUid = " + receivingFacilityUid + " - " + se.getMessage();
			logger.fatal(errMsg+se.getMessage(),se);
			throw new NEDSSSystemException(errMsg, se);
		} catch (Exception ex) {
			String errMsg = "CaseNotificationDAOImpl.getReceivingFacility - receivingFacilityUid = " + receivingFacilityUid + " - " + ex.getMessage();
			logger.fatal(errMsg+ex.getMessage(),ex);
			throw new NEDSSSystemException(errMsg, ex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}



	public void insertExportAlgorithm(ExportAlgorithmDT dt)throws NEDSSDAOSysException, NEDSSSystemException
	{
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		int resultCount = 0;
		int i = 1;

		try
		{
			dbConnection = getConnection();
			pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_EXPORT_ALGORITHM);

			pStmt.setString(i++, dt.getAlgorithmName());
			pStmt.setString(i++, dt.getDocumentType());
			pStmt.setString(i++, dt.getLevelOfReview());
			pStmt.setLong(i++, (new Long(dt.getReceivingSystem())).longValue());
			pStmt.setString(i++, dt.getRecordStatusCd());

			pStmt.setTimestamp(i++, dt.getAddTime());
			pStmt.setLong(i++, dt.getAddUserId().longValue());
			pStmt.setTimestamp(i++, dt.getLastChgTime());
			pStmt.setLong(i++, dt.getLastChgUserId().longValue());

			resultCount = pStmt.executeUpdate();

			if (resultCount != 1)
			{
				throw new NEDSSSystemException("Error: none or more than one entity inserted at a time, resultCount = " +
						resultCount);
			}
		}
		catch (SQLException se)
		{
			logger.fatal("Error: SQLException while inserting into Export Algorithm table  = "+se.getMessage(), se);
			throw new NEDSSDAOSysException("Error: SQLException while inserting into Export Algorithm table\n" +
					se.getMessage(), se);
		}
		finally
		{
			closeStatement(pStmt);
			releaseConnection(dbConnection);
		} //end of finally




	}

	public void insertExportAlgthmTrigger(ExportTriggerDT dt)throws NEDSSDAOSysException, NEDSSSystemException
	{
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		int resultCount = 0;
		int i = 1;

		try
		{
			dbConnection = getConnection();
			pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_EXPORT_ALGTHM_TRIGGER);

			pStmt.setLong(i++, dt.getExportAlgorithmUid().longValue());
			pStmt.setTimestamp(i++, dt.getAddTime());
			pStmt.setLong(i++, dt.getAddUserId().longValue());
			pStmt.setTimestamp(i++, dt.getLastChgTime());
			pStmt.setLong(i++, dt.getLastChgUserId().longValue());
			pStmt.setLong(i++, new Long(dt.getTriggerField()).longValue());
			pStmt.setString(i++, dt.getTriggerFilter());
			pStmt.setString(i++, dt.getTriggerLogic());
			pStmt.setString(i++, "A");
			resultCount = pStmt.executeUpdate();

			if (resultCount != 1)
			{
				throw new NEDSSSystemException("Error: none or more than one entity inserted at a time, resultCount = " +
						resultCount);
			}
		}
		catch (SQLException se)
		{
			logger.fatal("Error: SQLException while inserting in Export_Trigger table  = "+se.getMessage(), se);
			throw new NEDSSDAOSysException("Error: SQLException while inserting in Export_Trigger table\n" +
					se.getMessage(), se);
		}
		finally
		{
			closeStatement(pStmt);
			releaseConnection(dbConnection);
		} //end of finally




	}


	private String GET_EXPORT_ALGORITHM="SELECT export_algorithm_uid \"exportAlgorithmUid\",add_time \"addTime\",add_user_id \"addUserId\",doc_type \"docType\",export_algorithm_nm \"exportAlgorithmNm\",export_receiving_facility_uid \"exportReceivingFacilityUid\",last_chg_time \"lastChgTime\",last_chg_user_id \"lastChgUserId\",level_of_review \"levelOfReview\",record_status_cd \"recordStatusCd\"FROM Export_Algorithm where record_status_cd='A'";
	private String GET_EXPORT_TRIGGERS="SELECT export_trigger_uid \"exportTriggerUid\",export_algorithm_uid \"exportAlgorithmUid\" ,add_time \"addTime\",add_user_Id  \"addUserId\" ,last_Chg_Time \"lastChgTime\",last_chg_user_id \"lastChgUserId\" ,nbs_question_uid \"nbsQuestionUid\",trigger_filter \"triggerFilter\",trigger_logic \"triggerLogic\",record_status_cd \"recordStatusCd\" FROM Export_Trigger where export_Algorithm_uid=?";

	@SuppressWarnings("unchecked")
	public Collection<Object>  getExportAlgorithmrCollection() throws NEDSSSystemException{
		ExportAlgorithmDT  exportAlgorithmDT  = new ExportAlgorithmDT();
		ArrayList<Object>  exportAlgorithmDTCollection = new ArrayList<> ();
		try
		{
			exportAlgorithmDTCollection = (ArrayList<Object> )preparedStmtMethod(exportAlgorithmDT, exportAlgorithmDTCollection, GET_EXPORT_ALGORITHM, NEDSSConstants.SELECT);

		}
		catch (Exception ex) {
			logger.fatal("Exception in getPamAnswerDTCollection:  ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return exportAlgorithmDTCollection;
	}


	@SuppressWarnings("unchecked")
	public Collection<Object>  getExportTrigggerCollection(Long exportAlgorithmUid) throws NEDSSSystemException{
		ExportTriggerDT  exportTriggerDT  = new ExportTriggerDT();
		ArrayList<Object>  exportTriggerDTCollection = new ArrayList<> ();
		exportTriggerDTCollection.add(exportAlgorithmUid);
		try
		{
			exportTriggerDTCollection = (ArrayList<Object> )preparedStmtMethod(exportTriggerDT, exportTriggerDTCollection, GET_EXPORT_TRIGGERS, NEDSSConstants.SELECT);

		}
		catch (Exception ex) {
			logger.fatal("Exception in getPamAnswerDTCollection:  ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return exportTriggerDTCollection;
	}

	public Collection<Object>  getExportTrigggerPHCCollection(String sql) throws NEDSSSystemException{
		Connection dbConnection = null;
		dbConnection = getConnection();
		Collection<Object> coll = new ArrayList<> ();
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = dbConnection.prepareStatement(sql);
			ResultSet  rs = preparedStmt.executeQuery();
			while (rs.next()) {
				logger.debug("Export PHCUID = " + rs.getLong(1));
				Long  phcUid=new Long( rs.getLong(1));
				coll.add(phcUid);
			}
		}catch (Exception ex) {
			logger.fatal("Exception while getting count of 'code_set_nm' : ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		} finally {
			try {
				dbConnection.close();
				preparedStmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return coll;
	}

	public Collection<Object> getExportTriggerFields()throws NEDSSSystemException{

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for case Notification: getExportTriggerFields  in CaseNotificationDAO", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		//  logic to check if code has separate table
		sqlQuery = WumSqlQuery.SELECT_EXPORT_TRIGGERFIELDS_ORACLE;
		try {
			preparedStmt = dbConnection
			.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  exportAlgorithmList = new ArrayList<> ();
			if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();


				exportAlgorithmList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
						resultSetMetaData, ExportTriggerNNDFieldsDT.class, exportAlgorithmList);

				logger.debug("returned Trigger Fields list");
			}
			return exportAlgorithmList;
		} catch (SQLException se) {
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "Export  Trigger Field List: CaseNotificationDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger
			.fatal(
					"Error in result set handling while selecting Trigger Fields: CaseNotificationDAOImpl."+reuex.getMessage(),
					reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}


	}

	public Collection<Object> getSRTCodeSets()throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for case Notification: ExportAlgorithm  in CaseNotificationDAO"+nsex.getMessage(), nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		//  logic to check if code has separate table
		sqlQuery = WumSqlQuery.SELECT_QUESTION_SRTS;
		try {
			preparedStmt = dbConnection
			.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  exportTriggerSRTs = new ArrayList<> ();
			if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();


				exportTriggerSRTs = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
						resultSetMetaData, ExportTriggerNNDFieldsDT.class, exportTriggerSRTs);

				logger.debug("returned questions list");
			}
			return exportTriggerSRTs;
		} catch (SQLException se) {
			logger.fatal("SQLException while selecting Export-  export Trigger SRTs: CaseNotificationDAOImpl "+se.getMessage(), se);
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "Export-  export Trigger SRTs: CaseNotificationDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger
			.fatal(
					"Error in result set handling while selecting export Trigger SRTs: CaseNotificationDAOImpl."+reuex.getMessage(),
					reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	public ArrayList<Object>  getAlgorithmUid(String algorithmName)throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;
		int i = 1;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for case getAlgorithmUid: getAlgorithmUid  in CaseNotificationDAO"+nsex.getMessage(), nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		//  logic to check if code has separate table
		sqlQuery = WumSqlQuery.SELECT_EXPORT_ALGORITHM_UID_SQL;
		try {
			preparedStmt = dbConnection
			.prepareStatement(sqlQuery);
			preparedStmt.setString(i++, algorithmName);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  exportTriggerFields = new ArrayList<> ();
			if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();


				exportTriggerFields = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
						resultSetMetaData, ExportAlgorithmDT.class, exportTriggerFields);

				logger.debug("returned questions list");
			}
			return exportTriggerFields;
		} catch (SQLException se) {
			logger.fatal("SQLException while selecting Export Algorithm List: CaseNotificationDAOImpl "+se.getMessage(), se);
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "Export Algorithm List: CaseNotificationDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger
			.fatal(
					"Error in result set handling while selecting pexport Algorithm: CaseNotificationDAOImpl."+reuex.getMessage(),
					reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	public Map<Object, Object> insertReceivingSystems(ExportReceivingFacilityDT dt)throws NEDSSDAOSysException, NEDSSSystemException
	{
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		int resultCount = 0;
		int i = 1;
		int count = 0;
		Map<Object, Object> errorMap = new HashMap<>();
		int countReturned =0;
		boolean isError=false;

		//Throw exception if duplicate combination of System OID and System Owner OID is entered
		countReturned=UniqueFieldCheckForRecSysmOidNSystemOwnerOid(dt);
		if(countReturned >= 1) {
			errorMap.put(new Integer(count++), " The combination of this Application OID and this Facility OID already exists. Please enter a unique combination of Application OID and Facility OID to create a new record. ");
			isError=true;
		}
		//Throw exception if duplicate SystemShortName is entered
		countReturned=UniqueFieldCheckForRecSysmShortName(dt);
	   	if(countReturned >= 1) {
	   	   errorMap.put(new Integer(count++), " A record already exists with this Display Name. Please enter a unique Display Name to create a new record. ");
	   	 isError=true;
	   	}
		        //Throw exception if duplicate System Name is entered
		   	/*countReturned=UniqueFieldCheckForRecSysmName(dt);
		   	 if( countReturned >= 1) {
		   	    errorMap.put(new Integer(count++), " A record already exists with this SystemName. Please enter a unique SystemName to create a new record. ");
		   	    isError=true;
		   	 }*/


		if( !isError){
			try
			{
				dbConnection = getConnection();
				pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_RECEIVING_FACILITY_SYSTEMS);

				pStmt.setTimestamp(i++, dt.getAddTime());
				pStmt.setLong(i++, dt.getAddUserId().longValue());
				pStmt.setTimestamp(i++, dt.getLastChgTime());
				pStmt.setLong(i++, dt.getLastChgUserId().longValue());

				pStmt.setString(i++, dt.getReceivingSystemNm());
				pStmt.setString(i++, (dt.getReceivingSystemOid()));
				pStmt.setString(i++, dt.getReceivingSystemShortName());
				pStmt.setString(i++, dt.getReceivingSystemOwner());
				pStmt.setString(i++, dt.getReceivingSystemDescTxt());
				pStmt.setString(i++, dt.getSendingIndCd());
				pStmt.setString(i++, dt.getReceivingIndCd());
				pStmt.setString(i++, dt.getAllowTransferIndCd());
				pStmt.setString(i++, dt.getJurDeriveIndCd());
				pStmt.setString(i++, dt.getReportType());
				pStmt.setString(i++, dt.getAdminComment());
				pStmt.setString(i++, dt.getReceivingSystemOwnerOid());

				resultCount = pStmt.executeUpdate();

				if (resultCount != 1)
				{
					throw new NEDSSSystemException("Error: none or more than one entity inserted at a time, resultCount = " +
							resultCount);
				}
			}
			catch (Exception se)
			{
				logger.fatal("Error: SQLException while inserting into Export_receiving_facility table "+se.getMessage(), se);
				throw new NEDSSDAOSysException("Error: SQLException while inserting into Export_receiving_facility table\n" +
						se.getMessage(), se);
			}
			finally
			{
				closeStatement(pStmt);
				releaseConnection(dbConnection);
			} //end of finally
		}
		else {
			return errorMap;
		}
		return errorMap;
	}

	public Map<Object, Object> updateTriggerCode(TriggerCodesDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		int resultCount = 0;
		int i = 1;
		int count = 0;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for case getAlgorithmUid: getAlgorithmUid  in CaseNotificationDAO", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}

		Map<Object, Object> errorMap = new HashMap<>();
		int countReturned =0;
		boolean isError=false;

		//Throw exception if duplicate combination of System OID and System Owner OID is entered
		//countReturned=UniqueFieldCheckForRecSysmOidNSystemOwnerOidForEdit(dt);
		if(countReturned >= 1) {
			errorMap.put(new Integer(count++), " The combination of this Application OID and this Facility OID already exists. Please enter a unique combination of Application OID and Facility OID to create a new record. ");
			isError=true;
		}

		//Throw exception if duplicate SystemShortName is entered
		//countReturned=UniqueFieldCheckForRecSysmShortNameForEdit(dt);
	   	if(countReturned >= 1) {
	   	   errorMap.put(new Integer(count++), " A record already exists with this Display Name. Please enter a unique Display Name to create a new record. ");
	   	 isError=true;
	   	}

		if( !isError){
			try {
				String sqlQuery ="";

				sqlQuery = WumSqlQuery.UPDATE_TRIGGER_CODE_SQL;

				dbConnection = getConnection();
				pStmt = dbConnection.prepareStatement(sqlQuery);
				pStmt.setString(i++, dt.getDisplayName());

				String diseaseNm=dt.getDiseaseNm();
				if(diseaseNm==null || diseaseNm.isEmpty())
					diseaseNm=null;

				pStmt.setString(i++, diseaseNm);
				if(dt.getConditionCd()==null || dt.getConditionCd().isEmpty())
					pStmt.setString(i++, null);
				else
					pStmt.setString(i++, dt.getConditionCd());
				pStmt.setString(i++, dt.getCodeSystemVersionId());
				pStmt.setInt(i++, dt.getNbsUid());
				resultCount = pStmt.executeUpdate();

				if (resultCount != 1)
				{
					throw new NEDSSSystemException("Error: none or more than one entity inserted at a time, resultCount = " +
							resultCount);
				}
			}

			catch (SQLException se)
			{
				logger.fatal("Error: SQLException while updating code_to_condition table "+se.getMessage(),se);
				throw new NEDSSDAOSysException("Error: SQLException while updating code_to_condition table\n" +
						se.getMessage(), se);
			}
			catch (Exception se)
			{
				logger.fatal("Error: Exception while updating code_to_condition table "+se.getMessage(),se);
				throw new NEDSSDAOSysException("Error: Exception while updating code_to_condition table\n" +
						se.getMessage(), se);
			}
			finally
			{
				closeStatement(pStmt);
				releaseConnection(dbConnection);
			}
		}
		else {
			return errorMap;
		}
		return errorMap; //end of finally
	}

	/**
	 * createTriggerCode:
	 * @param dt
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Map<Object, Object> createTriggerCode(TriggerCodesDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		int resultCount = 0;
		int i = 1;
		int count = 0;

		try {
			dbConnection = getConnection(NEDSSConstants.SRT);
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for case getAlgorithmUid: getAlgorithmUid  in CaseNotificationDAO", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}

		Map<Object, Object> errorMap = new HashMap<>();
		int countReturned =0;
		boolean isError=false;

		//Throw exception if duplicate combination of System OID and System Owner OID is entered
		countReturned=UniqueFieldTriggerCodeForCreate(dt);
		if(countReturned >= 1) {
			errorMap.put(new Integer(count++), " The code <b>"+dt.getCodeColumn()+"</b> already exists for the coding system selected. Please enter a unique code and coding system combination to create a new record.");
			isError=true;
		}

		//Throw exception if duplicate SystemShortName is entered
		//countReturned=UniqueFieldCheckForRecSysmShortNameForEdit(dt);
	   	/*if(countReturned >= 1) {
	   	   errorMap.put(new Integer(count++), " A record already exists with this Display Name. Please enter a unique Display Name to create a new record. ");
	   	 isError=true;
	   	}*/

		if( !isError){
			try {
				String sqlQuery ="";

				sqlQuery = WumSqlQuery.CREATE_TRIGGER_CODE_SQL;

				dbConnection = getConnection(NEDSSConstants.SRT);
				pStmt = dbConnection.prepareStatement(sqlQuery);

				String codingSystemCd = dt.getCodingSystem();

				String codingSystemDesc = "";
				if(codingSystemCd.equalsIgnoreCase("ICD_10_CM"))
					codingSystemDesc="ICD-10-CM Diagnosis";
				else
					if(codingSystemCd.equalsIgnoreCase("SNOMED_CT_HL7"))
					codingSystemDesc="SNOMED-CT";


				String codeSystemCd = getCodeSystemCdFromCodeValueGeneral(dt, codingSystemCd);
				String code = dt.getCodeColumn();
				String codeDescriptionTxt = dt.getDisplayName();
				String codeSystemVersionId = dt.getCodeSystemVersionId();
				String conditionCd = dt.getConditionCd();
				if(conditionCd==null || conditionCd.isEmpty())
					conditionCd=null;
				String diseaseNm = dt.getDiseaseNm();

				if(diseaseNm==null || diseaseNm.isEmpty())
					diseaseNm=null;

				Integer nbsUid = getMaxNbsUid(dt)+1;

				pStmt.setString(i++, codeSystemCd);
				pStmt.setString(i++, codingSystemDesc);
				pStmt.setString(i++, code);
				pStmt.setString(i++, codeDescriptionTxt);
				pStmt.setString(i++, codeSystemVersionId);
				pStmt.setString(i++, conditionCd);
				pStmt.setString(i++, diseaseNm);
				pStmt.setInt(i++, nbsUid);

				resultCount = pStmt.executeUpdate();

				if (resultCount != 1)
				{
					throw new NEDSSSystemException("Error: none or more than one entity inserted at a time, resultCount = " +
							resultCount);
				}
			}

			catch (SQLException se)
			{
				logger.fatal("Error: SQLException while updating code_to_condition table "+se.getMessage(),se);
				throw new NEDSSDAOSysException("Error: SQLException while updating code_to_condition table\n" +
						se.getMessage(), se);
			}
			catch (Exception se)
			{
				logger.fatal("Error: Exception while updating code_to_condition table "+se.getMessage(),se);
				throw new NEDSSDAOSysException("Error: Exception while updating code_to_condition table\n" +
						se.getMessage(), se);
			}
			finally
			{
				closeStatement(pStmt);
				releaseConnection(dbConnection);
			}
		}
		else {
			return errorMap;
		}
		return errorMap; //end of finally
	}
	public void getConditionFromTriggerCode(Coded coded)throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;
		String resultcd = null;
		String whereClause = " where code_system_cd = ? AND code = ? ";
		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for case Notification: getTriggerCodes  in CaseNotificationDAO", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		//  logic to check if code has separate table
		sqlQuery = WumSqlQuery.SELECT_AllTRIGGER_CODE_SQL + whereClause;

		try {
			preparedStmt = dbConnection.prepareStatement(sqlQuery);
			preparedStmt.setString(1, coded.getCodeSystemCd());
			preparedStmt.setString(2, coded.getCode());
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  triggerCodesList = new ArrayList<> ();
			if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();

				triggerCodesList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,resultSetMetaData, TriggerCodesDT.class, triggerCodesList);

				logger.debug("returned triggercode list");
				 for (Object object : triggerCodesList) {
						if(object instanceof TriggerCodesDT)
						{
					    	TriggerCodesDT trigCode= (TriggerCodesDT)object;
					    	coded.setCode(trigCode.getConditionCd());

					    	if(trigCode.getDiseaseNm()==null)
					    		coded.setCodeDescription("");
					    	else
					    		coded.setCodeDescription(trigCode.getDiseaseNm());

						}
					}
				if(triggerCodesList.size()==0)
					coded.setCode(null);
			}




		} catch (SQLException se) {
			logger.fatal("SQLException while selecting Trigger Code List: TriggerCodeDAOImpl "+se.getMessage(), se);
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "Trigger Code List: TriggerCodeDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger
			.fatal(
					"Error in result set handling while selecting Trigger Code List: TriggerCodeDAOImpl.",
					reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

//			return triggerCodesList;








	}
	public int getMaxNbsUid(TriggerCodesDT dt){

		int nbsUid=1;
		ArrayList<Object>  paramList = new ArrayList<> ();

		String codeSql = "select max(nbs_uid) from NBS_SRTE..code_to_condition";
		nbsUid = (Integer) preparedStmtMethod(dt, paramList, codeSql, NEDSSConstants.SELECT_COUNT);

		if(nbsUid==0)
			nbsUid=1;

		return nbsUid;

	}
	public int UniqueFieldCheckForRecSysmName(ExportReceivingFacilityDT dt)throws NEDSSDAOSysException, NEDSSSystemException
	{
		int count = 0;
		Integer intCount = null;
		String codeSql = "";
		ArrayList<Object>  paramList = new ArrayList<> ();

		//Throw exception if duplicate System Name is entered
		codeSql = "select count(*) from  Export_receiving_facility where receiving_system_nm =? ";
		try {
			paramList.add(dt.getReceivingSystemNm());
			intCount =  (Integer) preparedStmtMethod(dt, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
			count =intCount.intValue();
			//If a duplicate System Name is found, throw an exception
			if(intCount != null && intCount.intValue() >= 1) {
				logger.error("Import and Export Administration - Create ManageSystems will not permit to add duplicate SystemName");
				//throw new NEDSSSystemException("SQLException PK Violated - Duplicate Code " + dt.getReceivingSystemNm() + " entered for codeset: " );
			}
		} catch (Exception ex) {
			logger.fatal("Exception while getting count of 'SystemName' : ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		} finally {
			paramList = new ArrayList<> ();
		}

		return count;
	}

	public int UniqueFieldCheckForRecSysmShortName(ExportReceivingFacilityDT dt)throws NEDSSDAOSysException, NEDSSSystemException
	{
		int count = 0;
		Integer intCount = null;
		String codeSql = "";
		ArrayList<Object>  paramList = new ArrayList<> ();

		//Throw exception if duplicate System Name is entered
		codeSql = "select count(*) from  Export_receiving_facility where receiving_system_short_nm =? ";
		try {
			paramList.add(dt.getReceivingSystemShortName());
			intCount =  (Integer) preparedStmtMethod(dt, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
			count =intCount.intValue();
			//If a duplicate System Name is found, throw an exception
			if(intCount != null && intCount.intValue() >= 1) {

				logger.error("Import and Export Administration - Create ManageSystems will not permit to add duplicate SystemShortName");
				//throw new NEDSSSystemException("SQLException PK Violated - Duplicate Code " + dt.getReceivingSystemShortName() + " entered for codeset: " );
			}
		} catch (Exception ex) {
			logger.fatal("Exception while getting count of 'SystemShortName' : ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		} finally {
			paramList = new ArrayList<> ();
		}

		return count;
	}

	public int UniqueFieldCheckForRecSysmShortNameForEdit(ExportReceivingFacilityDT dt)throws NEDSSDAOSysException, NEDSSSystemException
	{
		int count = 0;
		Integer intCount = null;
		String codeSql = "";
		ArrayList<Object>  paramList = new ArrayList<> ();

		//Throw exception if duplicate System Name is entered
		codeSql = "select count(*) from  Export_receiving_facility where receiving_system_short_nm =? and EXPORT_RECEIVING_FACILITY_UID <> ?";
		try {
			paramList.add(dt.getReceivingSystemShortName());
			paramList.add(dt.getExportReceivingFacilityUid());
			intCount =  (Integer) preparedStmtMethod(dt, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
			count =intCount.intValue();
			//If a duplicate System Name is found, throw an exception
			if(intCount != null && intCount.intValue() >= 1) {

				logger.error("Import and Export Administration - Edit ManageSystems will not permit to add duplicate SystemShortName");
				//throw new NEDSSSystemException("SQLException PK Violated - Duplicate Code " + dt.getReceivingSystemShortName() + " entered for codeset: " );
			}
		} catch (Exception ex) {
			logger.fatal("Exception while getting count of 'SystemShortName' : ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		} finally {
			paramList = new ArrayList<> ();
		}

		return count;
	}

	public int UniqueFieldCheckForRecSysmOidNSystemOwnerOid(ExportReceivingFacilityDT dt)throws NEDSSDAOSysException, NEDSSSystemException
	{
		int count = 0;
		Integer intCount = null;
		String codeSql = "";
		ArrayList<Object>  paramList = new ArrayList<> ();

		//Throw exception if duplicate System Name is entered
		codeSql = "select count(*) from  Export_receiving_facility where receiving_system_oid =? and RECEIVING_SYSTEM_OWNER_OID = ?";
		try {
			paramList.add(dt.getReceivingSystemOid());
			paramList.add(dt.getReceivingSystemOwnerOid());
			intCount =  (Integer) preparedStmtMethod(dt, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
			count =intCount.intValue();
			//If a duplicate System Name is found, throw an exception
			if(intCount != null && intCount.intValue() >= 1) {
				logger.error("Import and Export Administration - Create ManageSystems will not permit to add duplicate combination of SystemOid and SystemOwnerOid");
				//throw new NEDSSSystemException("SQLException PK Violated - Duplicate Code " + dt.getReceivingSystemOid() + " entered for codeset: " );
			}
		} catch (Exception ex) {
			logger.fatal("Exception while getting count of combination of 'SystemOid' and 'SystemOwnerOid': ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		} finally {
			paramList = new ArrayList<> ();
		}

		return count;
	}


public int UniqueFieldTriggerCodeForCreate(TriggerCodesDT dt)throws NEDSSDAOSysException, NEDSSSystemException
{
	int count = 0;
	Integer intCount = null;
	String codeSql = "";
	ArrayList<Object>  paramList = new ArrayList<> ();

	//Throw exception if duplicate System Name is entered
	codeSql = "select count(*) from  NBS_SRTE..code_to_condition where code_system_cd =? and code = ?";
	try {
		paramList.add(dt.getCodingSystem());
		paramList.add(dt.getCodeColumn());
		intCount =  (Integer) preparedStmtMethod(dt, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
		count =intCount.intValue();
		//If a duplicate System Name is found, throw an exception
		if(intCount != null && intCount.intValue() >= 1) {
			logger.error("Import and Export Administration - Edit ManageSystems will not permit to add duplicate combination of SystemOid and SystemOwnerOid");
			//throw new NEDSSSystemException("SQLException PK Violated - Duplicate Code " + dt.getReceivingSystemOid() + " entered for codeset: " );
		}
	} catch (Exception ex) {
		logger.fatal("Exception while getting count of combination of 'SystemOid' and 'SystemOwnerOid': ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString(), ex);
	} finally {
		paramList = new ArrayList<> ();
	}

	return count;
}


/**
 * getCodeDescTxtFromCodeValueGeneral: returns the code_desc_txt from code_value_genera where code_system_cd = CODE_SYSTEM_CRLM and code = to the parameter received
 * @param dt
 * @param codeSetNm
 * @return
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
public String getCodeSystemCdFromCodeValueGeneral(TriggerCodesDT dt, String code)throws NEDSSDAOSysException, NEDSSSystemException
{
	String codeDescTxt = null;
	ArrayList<Object> arrayCodeValueGeneral = new ArrayList<>();
	String codeSql = "";
	ArrayList<Object>  paramList = new ArrayList<> ();
	CodeValueGeneralDT codeValueGeneralDT = new CodeValueGeneralDT();

	codeSql = RETRIEVE_CODE_DESC_FROM_CODE_VALUE_GENERAL;
	try {

		arrayCodeValueGeneral.add(code);
		arrayCodeValueGeneral = (ArrayList<Object> ) preparedStmtMethod(codeValueGeneralDT, arrayCodeValueGeneral, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
		if(arrayCodeValueGeneral!=null && arrayCodeValueGeneral.size()>0)
			codeDescTxt = ((CodeValueGeneralDT)arrayCodeValueGeneral.get(0)).getCodeDescTxt();

	} catch (Exception ex) {
		logger.fatal("Exception while getting count of combination of 'SystemOid' and 'SystemOwnerOid': ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString(), ex);
	} finally {
		paramList = new ArrayList<> ();
	}

	return codeDescTxt;
}




	public Long getReceivingSystem (ExportReceivingFacilityDT dt) throws
	NEDSSSystemException
	{
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		Long exportUid = null;
		String codeSql ="SELECT EXPORT_RECEIVING_FACILITY_UID from Export_receiving_facility where receiving_system_oid =? and RECEIVING_SYSTEM_OWNER_OID = ?";
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(codeSql);
			//logger.debug("observationUID = " + observationUID);
			preparedStmt.setString(1, dt.getReceivingSystemOid());
			preparedStmt.setString(2, dt.getReceivingSystemOwnerOid());
			resultSet = preparedStmt.executeQuery();
			if(resultSet.next())
				 exportUid  = resultSet.getLong(1);
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException while checking for an"
					+ " existing Exportuid in Export_receiving_facility table "+sex.getMessage(), sex );
			throw new NEDSSDAOSysException( sex.getMessage(), sex);
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("Exception while getting dbConnection for checking for an"
					+ " existing Exportuid "+nsex.getMessage(), nsex );
			throw new NEDSSDAOSysException( nsex.getMessage(), nsex);
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return exportUid;
	}

	public String getJurDeriveIndCd (String facilityName, String facilityOID) throws
	NEDSSSystemException
	{
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		String jurDeriveIndCd = null;
		String codeSql ="SELECT JUR_DERIVE_IND_CD from Export_receiving_facility where receiving_system_owner= ? and  receiving_system_owner_oid=?";
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(codeSql);
			preparedStmt.setString(1, facilityName);//facilityName
			preparedStmt.setString(2, facilityOID);//facilityOID
			resultSet = preparedStmt.executeQuery();
			if(resultSet.next())
				jurDeriveIndCd  = resultSet.getString(1);
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException while getting jurisdcition derive indicator from Export_receiving_facility table "+sex.getMessage(), sex );
			throw new NEDSSDAOSysException( sex.getMessage(), sex);
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("Exception getting dbConnection while getting jurisdcition derive indicator"+nsex.getMessage(), nsex );
			throw new NEDSSDAOSysException( nsex.getMessage(), nsex);
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return jurDeriveIndCd;
	}

	public String getNNDInd (String conditionCd) throws NEDSSSystemException
	{
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		String nndInd = null;
		String codeSql ="SELECT NND_IND from  nbs_srte..condition_code where condition_cd = ?";
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(codeSql);
			preparedStmt.setString(1, conditionCd);//conditionCd
			resultSet = preparedStmt.executeQuery();
			if(resultSet.next())
				nndInd  = resultSet.getString(1);
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException while getting NND Ind from condition Code table "+sex.getMessage(), sex );
			throw new NEDSSDAOSysException( sex.getMessage(), sex);
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("System exception while getting NND Ind from condition Code table"+nsex.getMessage(), nsex );
			throw new NEDSSDAOSysException( nsex.getMessage(), nsex);
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return nndInd;
	}


}

