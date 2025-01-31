package gov.cdc.nedss.systemservice.ejb.casenotificationejb.dao;

import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportTriggerDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportTriggerNNDFieldsDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

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

public class CaseNotificationDAOImpl extends DAOBase {

	static final LogUtils logger = new LogUtils(CaseNotificationDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

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
		{
			sqlQuery = WumSqlQuery.SELECT_EXPORT_ALGORITHM_SQL;
		}
		try {
			preparedStmt = dbConnection
			.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  exportAlgList = new ArrayList<Object> ();
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
		{
			sqlQuery = WumSqlQuery.SELECT_EXPORT_ALGORITHM_TRIGGERS_SQL;
		}
		try {
			preparedStmt = dbConnection
			.prepareStatement(sqlQuery);
			preparedStmt.setLong(i++, algUid.longValue());   
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  exportAlgTriggerList = new ArrayList<Object> ();
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

	public Collection<Object> getReceivingFacility()throws NEDSSSystemException {
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
					+ "for case Notification: getReceivingFacility  in CaseNotificationDAO", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		//  logic to check if code has separate table
		{
			sqlQuery = WumSqlQuery.SELECT_RECEIVING_FACILITY_SQL;
		}
		try {
			preparedStmt = dbConnection.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  recFaclityList = new ArrayList<Object> ();
			if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();

				recFaclityList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,resultSetMetaData, ExportReceivingFacilityDT.class, recFaclityList);

				logger.debug("returned questions list");
			}
			return recFaclityList;



		} catch (SQLException se) {
			logger.fatal("SQLException while selecting Export Algorithm List: CaseNotificationDAOImpl "+se.getMessage(), se);
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

	public ExportReceivingFacilityDT getReceivingFacilityByShortNm(String shortNm)throws NEDSSSystemException {
		ExportReceivingFacilityDT exportReceivingFacilityDT = new ExportReceivingFacilityDT();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ArrayList<Object>  pList = new ArrayList<Object> ();
		String sqlQuery = null;
		String WHERE_CLAUSE = " WHERE receiving_system_short_nm = ?";

		//  logic to check if code has separate table
		{
			sqlQuery = WumSqlQuery.SELECT_RECEIVING_FACILITY_SQL + WHERE_CLAUSE;
		}

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
		ArrayList<Object>  pList = new ArrayList<Object> ();
		String sqlQuery = null;
		String WHERE_CLAUSE = " WHERE export_receiving_facility_uid = ?";

		//  logic to check if code has separate table
		{
			sqlQuery = WumSqlQuery.SELECT_RECEIVING_FACILITY_SQL + WHERE_CLAUSE;
		}

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
		ArrayList<Object>  exportAlgorithmDTCollection = new ArrayList<Object> ();
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
		ArrayList<Object>  exportTriggerDTCollection = new ArrayList<Object> ();
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
		Collection<Object> coll = new ArrayList<Object> ();
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
		{
			sqlQuery = WumSqlQuery.SELECT_EXPORT_TRIGGERFIELDS_ORACLE;
		}
		try {
			preparedStmt = dbConnection
			.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  exportAlgorithmList = new ArrayList<Object> ();
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
		{
			sqlQuery = WumSqlQuery.SELECT_QUESTION_SRTS;
		}
		try {
			preparedStmt = dbConnection
			.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  exportTriggerSRTs = new ArrayList<Object> ();
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
		{
			sqlQuery = WumSqlQuery.SELECT_EXPORT_ALGORITHM_UID_SQL;
		}
		try {
			preparedStmt = dbConnection
			.prepareStatement(sqlQuery);
			preparedStmt.setString(i++, algorithmName);    				
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  exportTriggerFields = new ArrayList<Object> ();
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
		Map<Object, Object> errorMap = new HashMap<Object, Object>();
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


		if( isError!=true){
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

	public Map<Object, Object> updateReceivingSystems(ExportReceivingFacilityDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
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

		Map<Object, Object> errorMap = new HashMap<Object, Object>();
		int countReturned =0;   
		boolean isError=false;

		//Throw exception if duplicate combination of System OID and System Owner OID is entered 
		countReturned=UniqueFieldCheckForRecSysmOidNSystemOwnerOidForEdit(dt);
		if(countReturned >= 1) {
			errorMap.put(new Integer(count++), " The combination of this Application OID and this Facility OID already exists. Please enter a unique combination of Application OID and Facility OID to create a new record. ");
			isError=true;
		}
		
		//Throw exception if duplicate SystemShortName is entered 
		countReturned=UniqueFieldCheckForRecSysmShortNameForEdit(dt);
	   	if(countReturned >= 1) {
	   	   errorMap.put(new Integer(count++), " A record already exists with this Display Name. Please enter a unique Display Name to create a new record. ");
	   	 isError=true;
	   	}

		if( isError!=true){
			try {
				dbConnection = getConnection();
				pStmt = dbConnection.prepareStatement(WumSqlQuery.UPDATE_RECEIVING_SYSTEMS_SQL);
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
				if(dt.getReceivingIndCd()== null || dt.getReceivingIndCd().equals("N")){
					dt.setAllowTransferIndCd(null);
					pStmt.setString(i++, dt.getAllowTransferIndCd());
				}
				else {
					pStmt.setString(i++, dt.getAllowTransferIndCd());
				}
				pStmt.setString(i++, dt.getJurDeriveIndCd());
				pStmt.setString(i++, dt.getReportType());
				pStmt.setString(i++, dt.getAdminComment()); 
				pStmt.setString(i++, dt.getReceivingSystemOwnerOid());
				pStmt.setLong(i++,dt.getExportReceivingFacilityUid().longValue());


				resultCount = pStmt.executeUpdate();

				if (resultCount != 1)
				{
					throw new NEDSSSystemException("Error: none or more than one entity inserted at a time, resultCount = " +
							resultCount);
				}
			}

			catch (SQLException se)
			{
				logger.fatal("Error: SQLException while updating Export_receiving_facility table "+se.getMessage(),se);
				throw new NEDSSDAOSysException("Error: SQLException while updating Export_receiving_facility table\n" +
						se.getMessage(), se);
			}
			catch (Exception se)
			{
				logger.fatal("Error: Exception while updating Export_receiving_facility table "+se.getMessage(),se);
				throw new NEDSSDAOSysException("Error: Exception while updating Export_receiving_facility table\n" +
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



	public int UniqueFieldCheckForRecSysmName(ExportReceivingFacilityDT dt)throws NEDSSDAOSysException, NEDSSSystemException
	{
		int count = 0;
		Integer intCount = null;
		String codeSql = "";
		ArrayList<Object>  paramList = new ArrayList<Object> ();

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
			paramList = new ArrayList<Object> ();
		}

		return count;
	}

	public int UniqueFieldCheckForRecSysmShortName(ExportReceivingFacilityDT dt)throws NEDSSDAOSysException, NEDSSSystemException
	{
		int count = 0;
		Integer intCount = null;
		String codeSql = "";
		ArrayList<Object>  paramList = new ArrayList<Object> ();

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
			paramList = new ArrayList<Object> ();
		}

		return count;
	}
	
	public int UniqueFieldCheckForRecSysmShortNameForEdit(ExportReceivingFacilityDT dt)throws NEDSSDAOSysException, NEDSSSystemException
	{
		int count = 0;
		Integer intCount = null;
		String codeSql = "";
		ArrayList<Object>  paramList = new ArrayList<Object> ();

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
			paramList = new ArrayList<Object> ();
		}

		return count;
	}	

	public int UniqueFieldCheckForRecSysmOidNSystemOwnerOid(ExportReceivingFacilityDT dt)throws NEDSSDAOSysException, NEDSSSystemException
	{
		int count = 0;
		Integer intCount = null;
		String codeSql = "";
		ArrayList<Object>  paramList = new ArrayList<Object> ();

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
			paramList = new ArrayList<Object> ();
		}

		return count;
	}	


public int UniqueFieldCheckForRecSysmOidNSystemOwnerOidForEdit(ExportReceivingFacilityDT dt)throws NEDSSDAOSysException, NEDSSSystemException
{
	int count = 0;
	Integer intCount = null;
	String codeSql = "";
	ArrayList<Object>  paramList = new ArrayList<Object> ();

	//Throw exception if duplicate System Name is entered 
	codeSql = "select count(*) from  Export_receiving_facility where receiving_system_oid =? and RECEIVING_SYSTEM_OWNER_OID = ? and EXPORT_RECEIVING_FACILITY_UID <> ?";
	try {			
		paramList.add(dt.getReceivingSystemOid());
		paramList.add(dt.getReceivingSystemOwnerOid());
		paramList.add(dt.getExportReceivingFacilityUid());
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
		paramList = new ArrayList<Object> ();
	}

	return count;
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
	
	public String getNNDInd (String conditionCd) throws
	NEDSSSystemException
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

