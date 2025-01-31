package gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dao;
/**
 * Name:  DSMAlgorithmDaoImpl.java
 * Description:DAO class to Insert/Update/Select and Delete DSMAlgorithmDT
 * This contains the Decision Support Algorithm High Level Database Service Methods
 * Note that the XML field algorithm_payload contains the XML for the rules.
 * Copyright:	Copyright (c) 2011
 * Company: 	CSC
 * @author	Gregory Tucker
 * @version	1.0
 */

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DSMAlgorithmDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DSMAlgorithmDaoImpl extends DAOBase{
	//For logging
    static final LogUtils logger = new LogUtils(DSMAlgorithmDaoImpl.class.getName());
    private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
    Collection<Object> DSMAlgorithmList = null;


	/**
	 * Insert the dsmAlgorithmDT record specified
	 * @param dsmAlgorithmDT with fields
	 * @return DSMAlgorithmDT with updated UID
	 * @throws NEDSSSystemException
	 */
    public  DSMAlgorithmDT insertDSMAlgorithmDT(DSMAlgorithmDT dsmAlgorithmDT)
	throws NEDSSSystemException{

		logger.debug("Inserting new record into DSM_algorithm table.");
		//Throw exception if duplicate algorithmNm entered
		boolean isUnique = isAlgorithmNmUnique(dsmAlgorithmDT);
		if(isUnique == false){
			logger.error("The Algorithm Name already exists. Please enter a unique Algorithm Name to create a new record. ");
			throw new NEDSSSystemException("The Algorithm Name already exists. Please enter a unique Algorithm Name to create a new record. ");
		}
		/**
		 * Inserting a new DSM_algorithm
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rss = null;
		Statement statement = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;
		Long  dsmAlgorithmUid = null;

		String INSERT_DSM_ALGORITHM = "INSERT INTO DSM_algorithm(algorithm_nm,event_type," +
			"condition_list,resulted_test_list,frequency,apply_to,sending_system_list,reporting_system_list,event_action," +
			"algorithm_payload,admin_comment,status_cd,status_time,last_chg_user_id,last_chg_time) ";

		String INSERT_DSM_ALGORITHM_ORACLE= INSERT_DSM_ALGORITHM +
			"VALUES(?,?,?,?,?,?,?,?,?,EMPTY_BLOB(),?,?,?,?,?)";

		String INSERT_DSM_ALGORITHM_SQL= INSERT_DSM_ALGORITHM +
			"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PropertyUtil propUtil = PropertyUtil.getInstance();
		int i = 1;
		
		//Begin SQL Server
		try{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_DSM_ALGORITHM_SQL);
			preparedStmt.setString(i++,dsmAlgorithmDT.getAlgorithmNm());//1
			preparedStmt.setString(i++,dsmAlgorithmDT.getEventType());//2
			preparedStmt.setString(i++,dsmAlgorithmDT.getConditionList());//3
			preparedStmt.setString(i++,dsmAlgorithmDT.getResultedTestList());//3
			preparedStmt.setString(i++,dsmAlgorithmDT.getFrequency());//4
			preparedStmt.setString(i++,dsmAlgorithmDT.getApplyTo());//5
			preparedStmt.setString(i++,dsmAlgorithmDT.getSendingSystemList());//6
			preparedStmt.setString(i++,dsmAlgorithmDT.getReportingSystemList());//7
			preparedStmt.setString(i++,dsmAlgorithmDT.getEventAction());//8
			preparedStmt.setAsciiStream(i++,new ByteArrayInputStream(dsmAlgorithmDT.getAlgorithmPayload().getBytes()),
						dsmAlgorithmDT.getAlgorithmPayload().length());//9
			preparedStmt.setString(i++,dsmAlgorithmDT.getAdminComment());//10
			if(dsmAlgorithmDT.getStatusCd() == null)
				preparedStmt.setString(i++,"A");//11
			else
				preparedStmt.setString(i++, dsmAlgorithmDT.getStatusCd());

			if (dsmAlgorithmDT.getStatusTime() == null) //12
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, dsmAlgorithmDT.getStatusTime());//9
			if (dsmAlgorithmDT.getLastChgUserId()==null) //13
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, dsmAlgorithmDT.getLastChgUserId().longValue());//10
			if (dsmAlgorithmDT.getLastChgTime() == null) //14
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, dsmAlgorithmDT.getLastChgTime());


			resultCount = preparedStmt.executeUpdate();
			logger.debug("done insert a new DSM_algorithm! "+dsmAlgorithmDT.toString());

			preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(dsm_algorithm_uid) from DSM_algorithm");

			rs = preparedStmt2.executeQuery();
			if (rs.next()) {
				logger.debug("dsm_algorithm_uid = " + rs.getLong(1));
				dsmAlgorithmUid = new Long( rs.getLong(1));
				dsmAlgorithmDT.setDsmAlgorithmUid(dsmAlgorithmUid);
			}
		}catch(SQLException sqlex)
		{
			logger.fatal("SQLException while inserting " +
						"a new entry in DSM_algorithm table: \n", sqlex);
			logger.fatal("dsmAlgorithmDT DT is "+dsmAlgorithmDT.toString());
			logger.fatal("dsmAlgorithmDT resultCount is "+resultCount);
			throw new NEDSSSystemException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while inserting into DSM_algorithm TABLE, exception = " + ex.getMessage(), ex);
			logger.fatal("dsmAlgorithmDT DT is "+dsmAlgorithmDT.toString());
			logger.fatal("dsmAlgorithmDT resultCount is "+resultCount);
			throw new NEDSSSystemException(ex.toString(), ex);
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		return dsmAlgorithmDT;
	}

	/**
	 * Update the dsmAlgorithmDT record specified by the passed Uid
	 * @param dsmAlgorithmDT with updated fields and dsmAlgorithmUid
	 * @return DSMAlgorithmDT
	 * @throws NEDSSSystemException
	 */
	public  DSMAlgorithmDT updateDSMAlgorithmDT(DSMAlgorithmDT dsmAlgorithmDT)
	throws NEDSSSystemException {
		logger.debug("Updating DSM Algorithm record..");
		
		boolean isUnique = isAlgorithmNmUnique(dsmAlgorithmDT);
		if(isUnique == false){
			logger.error("The Algorithm Name already exists. Please enter a unique Algorithm Name to create a new record. ");
			throw new NEDSSSystemException("The Algorithm Name already exists. Please enter a unique Algorithm Name to create a new record. ");
		}
		/**
		 * insert a new history record before update
		 */
		updateDSMAlgorithmHistory(dsmAlgorithmDT.getDsmAlgorithmUid());

		/**
		 * Update as existing DSM_algorithm record
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet rss=null;
		int resultCount = 0;

		String UPDATE_DSM_ALGORITHM_SQL="UPDATE DSM_algorithm set algorithm_nm=?, event_type=?, condition_list=?, resulted_test_list=?, frequency=?, " +
			"apply_to=?, sending_system_list=?, reporting_system_list=?, event_action=?, algorithm_payload=?, " +
			"admin_comment=?, status_cd=?, status_time=?, last_chg_user_id=?, last_chg_time=? " +
			"WHERE dsm_algorithm_uid = ? ";

		String UPDATE_DSM_ALGORITHM_ORACLE="UPDATE DSM_algorithm set algorithm_nm=?, event_type=?, condition_list=?, resulted_test_list=?, frequency=?, " +
		"apply_to=?, sending_system_list=?, reporting_system_list=?, event_action=?, " +
		"admin_comment=?, status_cd=?, status_time=?, last_chg_user_id=?, last_chg_time=? " +
		"WHERE dsm_algorithm_uid = ? ";
		//SQL Server
		try {
			int i = 1;
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_DSM_ALGORITHM_SQL);
			preparedStmt.setString(i++,dsmAlgorithmDT.getAlgorithmNm());//1
			preparedStmt.setString(i++,dsmAlgorithmDT.getEventType());//2
			preparedStmt.setString(i++,dsmAlgorithmDT.getConditionList());//3
			preparedStmt.setString(i++,dsmAlgorithmDT.getResultedTestList());//3
			preparedStmt.setString(i++,dsmAlgorithmDT.getFrequency());//4
			preparedStmt.setString(i++,dsmAlgorithmDT.getApplyTo());//5
			preparedStmt.setString(i++,dsmAlgorithmDT.getSendingSystemList());//6
			preparedStmt.setString(i++,dsmAlgorithmDT.getReportingSystemList());//7
			preparedStmt.setString(i++,dsmAlgorithmDT.getEventAction());//8
			preparedStmt.setAsciiStream(i++,new ByteArrayInputStream(dsmAlgorithmDT.getAlgorithmPayload().getBytes()),
						dsmAlgorithmDT.getAlgorithmPayload().length());//9
			preparedStmt.setString(i++,dsmAlgorithmDT.getAdminComment());//10
			preparedStmt.setString(i++,dsmAlgorithmDT.getStatusCd());

			if (dsmAlgorithmDT.getStatusTime() == null) //12
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, dsmAlgorithmDT.getStatusTime());//9
			if (dsmAlgorithmDT.getLastChgUserId()==null) //13
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, dsmAlgorithmDT.getLastChgUserId().longValue());//10
			if (dsmAlgorithmDT.getLastChgTime() == null) //14
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, dsmAlgorithmDT.getLastChgTime());
			preparedStmt.setLong(i++, dsmAlgorithmDT.getDsmAlgorithmUid().longValue());
			resultCount = preparedStmt.executeUpdate();
			logger.debug("Done updating a DSM_algorithm -> "+dsmAlgorithmDT.toString());
			}
			catch(SQLException sqlex)
			{
				logger.fatal("SQLException while updating " +
					"UID= " + dsmAlgorithmDT.getDsmAlgorithmUid().toString() + " in DSM_algorithm table: \n", sqlex);
				logger.fatal("dsmAlgorithmDt DT is "+dsmAlgorithmDT.toString());
				logger.fatal("dsmAlgorithmDt resultCount is "+resultCount);
				throw new NEDSSSystemException( sqlex.toString(), sqlex );
			}
			catch(Exception ex)
			{
				logger.fatal("Error while updating DSM_algorithmTABLE, exception = " + ex.getMessage(), ex);
				logger.fatal("dsmAlgorithmDt DT is "+dsmAlgorithmDT.toString());
				logger.fatal("dsmAlgorithmDt resultCount is "+resultCount);
				throw new NEDSSSystemException(ex.toString(), ex);
			}


			finally
			{
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}

		return dsmAlgorithmDT;
	}


	/**
	 * Select a single dsmAlgorithmDT record specified by the passed Uid
	 * @param dsmAlgorithmUid
	 * @return DSMAlgorithmDT
	 * @throws NEDSSSystemException
	 */
	public DSMAlgorithmDT selectDSMAlgorithmDT(Long dsmAlgorithmUid) throws NEDSSSystemException
    {
		logger.debug("Selecting a DSM Algorithm record");
		DSMAlgorithmDT dsmAlgorithmDT = new DSMAlgorithmDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        String SELET_DSM_ALGORITHM_BY_UID="SELECT dsm_algorithm_uid \"dsmAlgorithmUid\",  algorithm_nm \"algorithmNm\", event_type \"eventType\", condition_list \"conditionList\", frequency \"frequency\" , apply_to \"applyTo\", sending_system_list \"sendingSystemList\", reporting_system_list \"reportingSystemList\", event_action \"eventAction\","
        	+" algorithm_payload \"algorithmPayload\", admin_comment \"adminComment\", status_cd \"statusCd\", status_time \"statusTime\", last_chg_user_id \"lastChgUserId\", last_chg_time \"lastChgTime\" "
        	+" FROM DSM_algorithm where dsm_algorithm_uid=?";

        /**
         * Selects a DSMAlgorithmDT from DSM_algorithm table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELET_DSM_ALGORITHM_BY_UID);
            preparedStmt.setLong(1, dsmAlgorithmUid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            dsmAlgorithmDT = (DSMAlgorithmDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, dsmAlgorithmDT.getClass());
            dsmAlgorithmDT.setItNew(false);
            dsmAlgorithmDT.setItDirty(false);
            dsmAlgorithmDT.setItDelete(false);
        }
        catch(SQLException sqlException)
        {
            logger.fatal("SQLException while selecting " +
                            "a dsmAlgorithmDT ; uid = " + dsmAlgorithmUid.toString(), sqlException);
            throw new NEDSSSystemException( sqlException.getMessage(), sqlException);
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "a dsmAlgorithmDT ; uid = " + dsmAlgorithmUid.toString(), ex);
            throw new NEDSSSystemException( ex.getMessage(), ex);
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        logger.debug("returning a DSMAlgorithmDT object");
        return dsmAlgorithmDT;
    }//end of selecting a DSM_algorithm record by uid


	/**
	 * Get all the records in the DSM_algorithm table
	 * NOTE: Does not return the payload
	 * @param none
	 * @return Collection of DSMAlgorithmDTs as objects (without payload)
	 * @throws NEDSSSystemException
	 */
    public Collection<Object>  selectDSMAlgorithmDTList() throws NEDSSSystemException {
    	logger.debug("Selecting all DSM Algorithm records..");
    	Connection dbConnection = null;
    	PreparedStatement preparedStmt = null;
    	ResultSet resultSet = null;
    	ResultSetMetaData resultSetMetaData = null;
    	ResultSetUtils resultSetUtils = new ResultSetUtils();
    	// note algorithm_payload \"algorithmPayload\", is not selected for the list
        String SELET_DSM_ALGORITHM_LIST ="SELECT dsm_algorithm_uid \"dsmAlgorithmUid\",  algorithm_nm \"algorithmNm\", event_type \"eventType\", condition_list \"conditionList\", resulted_test_list \"resultedTestList\", frequency \"frequency\" , apply_to \"applyTo\", sending_system_list \"sendingSystemList\", reporting_system_list \"reportingSystemList\", event_action \"eventAction\","
            	+"  admin_comment \"adminComment\", status_cd \"statusCd\", status_time \"statusTime\", last_chg_user_id \"lastChgUserId\", last_chg_time \"lastChgTime\" "
            	+" FROM DSM_algorithm";

    	try {
    		dbConnection = getConnection();
    	} catch (NEDSSSystemException nedssEx) {
    		logger.fatal("SQLException while obtaining database connection "
    					+ "for DSMAlgorithmDaoImpl.selectDSMAlgorithmDTList: ", nedssEx);
    		throw new NEDSSSystemException(nedssEx.getMessage(), nedssEx);
    	}
    	try {
    		preparedStmt = dbConnection.prepareStatement(SELET_DSM_ALGORITHM_LIST);
    		resultSet = preparedStmt.executeQuery();
    		ArrayList<Object> algorithmList = new ArrayList<Object> ();
    		if (resultSet != null) {
    			resultSetMetaData = resultSet.getMetaData();
    			algorithmList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
    						resultSet, resultSetMetaData, DSMAlgorithmDT.class,
    						algorithmList);

    				logger.debug("returned Decision Support Mgr algorithms list");
    			}
    			return algorithmList;
    		} catch (SQLException sqlEx) {
    			throw new NEDSSSystemException("SQLException while selecting all algorithm records occurred in "
    					+ "DSMAlgorithmDaoImpl.selectDSMAlgorithmDTList "
    					+ sqlEx.getMessage(), sqlEx);
    		} catch (ResultSetUtilsException resultSetUtilEx) {
    			logger.fatal("Error in result set handling while selecting algorithm list collection: DSMAlgorithmDaoImpl.selectDSMAlgorithmDTList."+resultSetUtilEx.getMessage(), resultSetUtilEx);
    			throw new NEDSSSystemException(resultSetUtilEx.toString(), resultSetUtilEx);
    		} finally {
    			closeResultSet(resultSet);
    			closeStatement(preparedStmt);
    			releaseConnection(dbConnection);
    		}
    	}


	/**
	 * Get all the records in the DSM_algorithm table for a condition that is in the condition_list
	 * @param conditionCd i.e. 10020
	 * @param eventType either PHC236 (case) or 11648804 (lab)
	 * @return Collection of DSMAlgorithmDTs as objects
	 * @throws NEDSSSystemException
	 */
    public Collection<Object>  selectDSMAlgorithmDTForCondition(String conditionCd, String eventType) throws NEDSSSystemException {
    	logger.debug("Selecting all " +eventType+ " DSM Algorithm records for condition " + conditionCd);
    	Connection dbConnection = null;
    	PreparedStatement preparedStmt = null;
    	ResultSet resultSet = null;
    	ResultSetMetaData resultSetMetaData = null;
    	ResultSetUtils resultSetUtils = new ResultSetUtils();
    	// note algorithm_payload \"algorithmPayload\", is not selected for the list
    	// ND-2463 Aug 2016 GST added event_type to Predicate
        String SELET_DSM_ALGORITHM_LIST ="SELECT dsm_algorithm_uid \"dsmAlgorithmUid\",  algorithm_nm \"algorithmNm\", event_type \"eventType\", condition_list \"conditionList\", frequency \"frequency\" , apply_to \"applyTo\", sending_system_list \"sendingSystemList\", reporting_system_list \"reportingSystemList\", event_action \"eventAction\","
        	+" algorithm_payload \"algorithmPayload\", admin_comment \"adminComment\", status_cd \"statusCd\", status_time \"statusTime\", last_chg_user_id \"lastChgUserId\", last_chg_time \"lastChgTime\" "
            	+" FROM DSM_algorithm where condition_list like '%" + conditionCd + "%' and status_cd='A' and event_type='" +eventType+ "'";
    	try {
    		dbConnection = getConnection();
    	} catch (NEDSSSystemException nedssEx) {
    		logger.fatal("SQLException while obtaining database connection "
    					+ "for DSMAlgorithmDaoImpl.selectDSMAlgorithmDTForCondition: ", nedssEx);
    		throw new NEDSSSystemException(nedssEx.getMessage(), nedssEx);
    	}
    	try {
    		preparedStmt = dbConnection.prepareStatement(SELET_DSM_ALGORITHM_LIST);
    		resultSet = preparedStmt.executeQuery();
    		ArrayList<Object> algorithmList = new ArrayList<Object> ();
    		if (resultSet != null) {
    			resultSetMetaData = resultSet.getMetaData();
    			algorithmList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
    						resultSet, resultSetMetaData, DSMAlgorithmDT.class,
    						algorithmList);

    				logger.debug("returned Decision Support Mgr algorithms for condition " + conditionCd);
    			}
    			return algorithmList;
    		} catch (SQLException sqlEx) {
    			throw new NEDSSSystemException("SQLException while selecting algorithm records for condition <" + conditionCd + "> occurred in "
    					+ "DSMAlgorithmDaoImpl.selectDSMAlgorithmForCondition "
    					+ sqlEx.getMessage(), sqlEx);
    		} catch (ResultSetUtilsException resultSetUtilEx) {
    			logger.fatal("Error in result set handling while selecting algorithm list for condition: DSMAlgorithmDaoImpl.selectDSMAlgorithmForCondition.",resultSetUtilEx);
    			throw new NEDSSSystemException(resultSetUtilEx.toString(), resultSetUtilEx);
    		} finally {
    			closeResultSet(resultSet);
    			closeStatement(preparedStmt);
    			releaseConnection(dbConnection);
    		}
    	}
    	    public ArrayList<Object>  selectDSMAlgorithmDTForResultedTests(Collection<Object> tests) throws NEDSSSystemException {
		    	logger.debug("Selecting all DSM Algorithm records..");
		    	Connection dbConnection = null;
		    	PreparedStatement preparedStmt = null;
		    	ResultSet resultSet = null;
		    	ResultSetMetaData resultSetMetaData = null;
		    	ResultSetUtils resultSetUtils = new ResultSetUtils();
		    	// note algorithm_payload \"algorithmPayload\", is not selected for the list
		    	String SELECT_DSM_ALGORITHM_LIST=null;
		        		StringBuffer sb = new StringBuffer("");
		        if(tests != null && tests.size()>0){
		        	Object[] codes = (Object[])tests.toArray();
		        	Arrays.sort(codes);
		        	for(int i=0; i<codes.length;i++){
		        		sb.append(codes[i]);
		         		sb.append("^");
		        	}
		        	String results = sb.substring(0, sb.length()-1);
		        	SELECT_DSM_ALGORITHM_LIST ="SELECT dsm_algorithm_uid \"dsmAlgorithmUid\",  algorithm_nm \"algorithmNm\", event_type \"eventType\", condition_list \"conditionList\", frequency \"frequency\" , apply_to \"applyTo\", sending_system_list \"sendingSystemList\", reporting_system_list \"reportingSystemList\", event_action \"eventAction\","
		                	+" algorithm_payload \"algorithmPayload\", admin_comment \"adminComment\", status_cd \"statusCd\", status_time \"statusTime\", last_chg_user_id \"lastChgUserId\", last_chg_time \"lastChgTime\" "
		                    	+" FROM DSM_algorithm where resulted_test_list = '"+results+"' and status_cd='A'";
		        }
		    	try {
		    		dbConnection = getConnection();
		    	} catch (NEDSSSystemException nedssEx) {
		    		logger.fatal("SQLException while obtaining database connection "
		    					+ "for DSMAlgorithmDaoImpl.selectDSMAlgorithmDTForResultedTests: ", nedssEx);
		    		throw new NEDSSSystemException(nedssEx.getMessage(), nedssEx);
		    	}
		    	try {
		    		preparedStmt = dbConnection.prepareStatement(SELECT_DSM_ALGORITHM_LIST);
		    		resultSet = preparedStmt.executeQuery();
		    		ArrayList<Object> algorithmList = new ArrayList<Object> ();
		    		if (resultSet != null) {
		    			resultSetMetaData = resultSet.getMetaData();
		    			algorithmList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
		    						resultSet, resultSetMetaData, DSMAlgorithmDT.class,
		    						algorithmList);

		    				logger.debug("returned Decision Support Mgr algorithms for resulted tests " + sb.toString());
		    			}
		    			return algorithmList;
		    		} catch (SQLException sqlEx) {
		    			throw new NEDSSSystemException("SQLException while selecting algorithm records for resulted tests <" + sb.toString() + "> occurred in "
		    					+ "DSMAlgorithmDaoImpl.selectDSMAlgorithmDTForResultedTests "
		    					+ sqlEx.getMessage(), sqlEx);
		    		} catch (ResultSetUtilsException resultSetUtilEx) {
		    			logger.fatal("Error in result set handling while selecting algorithm list for resulted tests: DSMAlgorithmDaoImpl.selectDSMAlgorithmDTForResultedTests."+resultSetUtilEx.getMessage(), resultSetUtilEx);
		    			throw new NEDSSSystemException(resultSetUtilEx.toString(), resultSetUtilEx);
		    		} finally {
		    			closeResultSet(resultSet);
		    			closeStatement(preparedStmt);
		    			releaseConnection(dbConnection);
		    		}
    	}

    public ArrayList<Object>  selectDSMAlgorithmDTCollection() throws NEDSSSystemException {
    	logger.debug("Process of selecting all DSM Algorithm records within the system begins.....");
    	Connection dbConnection = null;
    	PreparedStatement preparedStmt = null;
    	ResultSet resultSet = null;
    	ResultSetMetaData resultSetMetaData = null;
    	ResultSetUtils resultSetUtils = new ResultSetUtils();
    	String SELECT_DSM_ALGORITHM_LIST=null;
        	SELECT_DSM_ALGORITHM_LIST ="SELECT dsm_algorithm_uid \"dsmAlgorithmUid\",  algorithm_nm \"algorithmNm\", event_type \"eventType\", condition_list \"conditionList\", frequency \"frequency\" , apply_to \"applyTo\", sending_system_list \"sendingSystemList\", reporting_system_list \"reportingSystemList\", event_action \"eventAction\","
                	+" algorithm_payload \"algorithmPayload\", admin_comment \"adminComment\", status_cd \"statusCd\", status_time \"statusTime\", last_chg_user_id \"lastChgUserId\", last_chg_time \"lastChgTime\" "
                    	+" FROM DSM_algorithm where status_cd='A'";
    	try {
    		dbConnection = getConnection();
    	} catch (NEDSSSystemException nedssException) {
    		logger.fatal("SQLException while obtaining database connection for DSMAlgorithmDaoImpl.selectDSMAlgorithmDTCollection: ", nedssException);
    		throw new NEDSSSystemException(nedssException.getMessage(), nedssException);
    	}
    	try {
    		preparedStmt = dbConnection.prepareStatement(SELECT_DSM_ALGORITHM_LIST);
    		resultSet = preparedStmt.executeQuery();
    		ArrayList<Object> algorithmList = new ArrayList<Object> ();
    		if (resultSet != null) {
    			resultSetMetaData = resultSet.getMetaData();
    			algorithmList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
    						resultSet, resultSetMetaData, DSMAlgorithmDT.class,
    						algorithmList);
    				logger.debug("returned Decision Support Manager algorithms with algorithmList, "+algorithmList);
    		}
    		return algorithmList;
		}catch(SQLException sqlEx){
			throw new NEDSSSystemException("SQLException while selecting algorithm records selectDSMAlgorithmDTCollection SQLException occurred in "
					+ "DSMAlgorithmDaoImpl.selectDSMAlgorithmDTCollection "+ sqlEx.getMessage(), sqlEx);
		} catch(ResultSetUtilsException resultSetUtilEx){
			logger.fatal("Error in result set handling while selecting algorithm list for resulted tests: DSMAlgorithmDaoImpl.selectDSMAlgorithmDTCollection.",resultSetUtilEx);
			throw new NEDSSSystemException(resultSetUtilEx.toString(), resultSetUtilEx);
		}finally{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
    }
    public Map<String, String>  retrieveRalatedPagesForConditionCodes(ArrayList<String> conditionCodes) throws NEDSSSystemException {
    	Connection dbConnection = null;
    	Map<String, String> conditionRelatedPageMap = new HashMap<String, String>();

    	try
    	{
    		dbConnection = getConnection();
    		Iterator<String> iterator = conditionCodes.iterator();
    		while(iterator.hasNext())
    		{
    			String conditionCode = iterator.next();
    			String pageCode = retrievePageCodeForConditionCode(conditionCode, dbConnection);
    			if(pageCode != null)
    				conditionRelatedPageMap.put(conditionCode, pageCode);
    		}
    	} catch (NEDSSSystemException nedssEx) {
    		logger.fatal("Error in DSMAlgorithmDaoImpl.retrieveRalatedPagesForConditionCodes: "+nedssEx.getMessage(), nedssEx);
    		throw new NEDSSSystemException(nedssEx.getMessage(), nedssEx);
    	}
    	finally {
    			releaseConnection(dbConnection);
    	}
    	return conditionRelatedPageMap;
    }

    public String  retrievePageCodeForConditionCode(String conditionCode) throws NEDSSSystemException {
    	Connection dbConnection = null;
    	String pageCode = null;

    	try
    	{
    		dbConnection = getConnection();
    		pageCode = retrievePageCodeForConditionCode(conditionCode, dbConnection);

    	} catch (NEDSSSystemException nedssEx) {
    		logger.fatal("conditionCode: "+conditionCode+" Error in DSMAlgorithmDaoImpl.retrievePageCodeForConditionCode: "+nedssEx.getMessage(), nedssEx);
    		throw new NEDSSSystemException(nedssEx.getMessage(), nedssEx);
    	}
    	finally {
    			releaseConnection(dbConnection);
    	}
    	return pageCode;
    }

	private String retrievePageCodeForConditionCode(String conditionCode,
			Connection dbConnection) {
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		String selectPageCodeForConditionCode =
				"select form_cd from wa_template, page_cond_mapping " +
				"where wa_template.wa_template_uid = page_cond_mapping.wa_template_uid and page_cond_mapping.condition_cd = ? " +
				"and wa_template.template_type in ('Published', 'Published With Draft') and wa_template.bus_obj_type='INV'";
		try {
			preparedStmt = dbConnection.prepareStatement(selectPageCodeForConditionCode);
			preparedStmt.setString(1, conditionCode);
			resultSet = preparedStmt.executeQuery();
			if(resultSet.next())
				return resultSet.getString(1);
		} catch (SQLException e) {
			logger.fatal("Exception while selecting " +
                    "page code for condition code  = " + conditionCode, e);
			throw new NEDSSSystemException( e.getMessage(), e);
		}finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
	}
		return null;

	}

	/**
	 * Change the status to the status specified in the DT
	 * Adds a record to the history table
	 * @param dsmAlgorithmDT - should set statusCd, statusTime, lastChgUserId, lastChgTime and the dsm_algorithm_uid
	 * @return nothing
	 * @throws NEDSSSystemException
	 */
	public void logicallyDeleteDSMAlgorithmDT(DSMAlgorithmDT dsmAlgorithmDT)	throws NEDSSSystemException {
		logger.debug("logically deleting DSM Algorithm rec");

		/**
		 * insert a new history record before status update
		 */
		updateDSMAlgorithmHistory(dsmAlgorithmDT.getDsmAlgorithmUid());


		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;
		String LOGICALLY_DELETE_DSM_ALGORITHM="UPDATE DSM_algorithm set status_cd=?, status_time=?, last_chg_user_id=?, last_chg_time=? " +
		"WHERE dsm_algorithm_uid = ? ";
		try {
			int i = 1;
			logger.debug("Logically Delete DSM_Algorithm record being called :"+ dsmAlgorithmDT.toString());
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(LOGICALLY_DELETE_DSM_ALGORITHM);
			if (dsmAlgorithmDT.getStatusCd() == null)
				preparedStmt.setString(i++, NEDSSConstants.STATUS_INACTIVE); //1
			else
				preparedStmt.setString(i++, dsmAlgorithmDT.getStatusCd()); //1
			if (dsmAlgorithmDT.getStatusCd() == null)
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new java.util.Date().getTime()));
			else
				preparedStmt.setTimestamp(i++, dsmAlgorithmDT.getStatusTime()); //2
			preparedStmt.setLong(i++, dsmAlgorithmDT.getLastChgUserId().longValue());//3
			if (dsmAlgorithmDT.getLastChgTime() == null)
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new java.util.Date().getTime()));
			else
				preparedStmt.setTimestamp(i++, dsmAlgorithmDT.getLastChgTime()); //4
			preparedStmt.setLong(i++, dsmAlgorithmDT.getDsmAlgorithmUid()); //5
			preparedStmt.executeUpdate();
		} catch (SQLException se) {
		    logger.fatal("SQLException while logically deleting DSM_algorithm "+se.getMessage(), se);
			throw new NEDSSSystemException("Error: SQLException while logically deleting\n" + dsmAlgorithmDT.getDsmAlgorithmUid().toString()+ se.getMessage(), se);
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	public void updateDSMAlgorithmHistory(Long pageUid) {
		try{
			updateDSMAlgorithmHistorySQL(pageUid);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Insert into the history table for the specified dsm_algorithm_uid
	 * @param dsmAlgorithmUid
	 * @return nothing
	 * @throws NEDSSSystemException
	 */
	private void updateDSMAlgorithmHistorySQL(Long dsmAlgorithmUid) throws NEDSSSystemException {

		String INSERT_DSM_ALGORITHM_HIST_SQL = "INSERT INTO DSM_algorithm_hist(dsm_algorithm_uid,algorithm_nm,event_type,condition_list,resulted_test_list,frequency,apply_to,"
	           + "sending_system_list,reporting_system_list,event_action,algorithm_payload,admin_comment,status_cd,status_time,last_chg_user_id,last_chg_time,version_ctrl_nbr)"
	  	+ " SELECT dsm_algorithm_uid,algorithm_nm,event_type,condition_list,resulted_test_list,frequency,apply_to,"
	  	+ "sending_system_list,reporting_system_list,event_action,algorithm_payload,admin_comment,status_cd,status_time,last_chg_user_id,last_chg_time,"
	  	+ " ISNULL((select max(version_ctrl_nbr)+1 from DSM_algorithm_hist where dsm_algorithm_uid = ? ),1 ) FROM DSM_algorithm"
	  	+ " where dsm_algorithm_uid = ? ";

		ArrayList<Object> paramList = new ArrayList<Object> ();
		//where dsmAlgorithmUid = ?
		paramList.add(dsmAlgorithmUid);
		paramList.add(dsmAlgorithmUid);
		try {
			preparedStmtMethod(null, paramList, INSERT_DSM_ALGORITHM_HIST_SQL, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateDSMAlgorithmHistory: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	}

	public String getInvFormCd(String conditionCd, String nndEntityId) throws NEDSSSystemException
    {
		Connection dbConnection = null;
		ResultSet resultSetCode = null;
		PreparedStatement preparedStmtCode = null;
		String invFormCd = "";
		String query1= "";
		String query2 = "";

		try{
			
				query1 = "SELECT investigation_form_cd FROM NBS_SRTE..CONDITION_CODE WHERE CONDITION_CD=?";
				query2 = "SELECT TOP 1 investigation_form_cd FROM NBS_SRTE..CONDITION_CODE WHERE nnd_entity_identifier=?";
			
			dbConnection = getConnection();
			if(nndEntityId == null) {
				preparedStmtCode = dbConnection.prepareStatement(query1);
				preparedStmtCode.setString(1, conditionCd);
			}else if(nndEntityId != null && nndEntityId.equals("Gen_Case_Map_v1.0")) {
				preparedStmtCode = dbConnection.prepareStatement(query2);
				preparedStmtCode.setString(1, nndEntityId);
			}
			//if(nndEntityId == null) preparedStmtCode.setString(1, conditionCd);
	        resultSetCode = preparedStmtCode.executeQuery();
	        resultSetCode.next();
	        invFormCd = resultSetCode.getString(1);
		}catch(SQLException sqlException)
        {
            logger.fatal("SQLException while selecting " +
                            "investigation_form_cd ; conditionCd = " + conditionCd, sqlException);
            throw new NEDSSDAOSysException( sqlException.getMessage(), sqlException);
        }
        catch(Exception ex)
        {
            logger.fatal("SQLException while selecting " +
                    "investigation_form_cd ; conditionCd = " + conditionCd, ex);
            throw new NEDSSDAOSysException( ex.getMessage(), ex);
        }
        finally
        {
            closeResultSet(resultSetCode);
            closeStatement(preparedStmtCode);
            releaseConnection(dbConnection);
        }
        logger.debug("returning a investigation_form_cd");

		return invFormCd;
    }

	
	public boolean isAlgorithmNmUnique(DSMAlgorithmDT dsmAlgorithmDT){
		boolean isUnique = true;
		Connection dbConnection = null;
		ResultSet resultSetCode = null;
		PreparedStatement preparedStmtCode = null;
		try{
			String query ="SELECT count(*) FROM dsm_algorithm where algorithm_nm=? ";
			if(dsmAlgorithmDT.getDsmAlgorithmUid()!=null)
				query =  "SELECT count(*) FROM dsm_algorithm where algorithm_nm=?  and dsm_algorithm_uid !=?";
			dbConnection = getConnection();
			preparedStmtCode = dbConnection.prepareStatement(query);
			preparedStmtCode.setString(1, dsmAlgorithmDT.getAlgorithmNm());
			// to cover the update scenario
			if(dsmAlgorithmDT.getDsmAlgorithmUid()!=null)
				preparedStmtCode.setLong(2, dsmAlgorithmDT.getDsmAlgorithmUid().longValue());
	        resultSetCode = preparedStmtCode.executeQuery();
	        resultSetCode.next();
	        int count = resultSetCode.getInt(1);
	        if(count >= 1)
	        	isUnique = false;
	        else
	        	isUnique = true;
		}catch(SQLException sqlException)
        {
            logger.fatal("SQLException while checking unique algorithm name; " +
                            "dsmAlgorithmUid = " + sqlException.getMessage(), sqlException);
            throw new NEDSSDAOSysException( sqlException.getMessage(), sqlException);
        }
        catch(Exception ex)
        {
        	 logger.fatal("SQLException while checking unique algorithm name; " +
                     "dsmAlgorithmUid = " + ex.getMessage(), ex);
            throw new NEDSSDAOSysException( ex.getMessage(), ex);
        }
        finally
        {
            closeResultSet(resultSetCode);
            closeStatement(preparedStmtCode);
            releaseConnection(dbConnection);
        }
        logger.debug("returning a boolean");

		return isUnique;
	}

	public void updateDSMAlgorithmStatus(Long algorithmUid, String newStatus) throws NEDSSDAOSysException, NEDSSSystemException {

		logger.debug(" in Algorithm DAO - updating Algorithm status to " + newStatus + "for " + algorithmUid);

		String codeSql ="UPDATE dsm_algorithm SET status_cd=?, status_time=? WHERE dsm_algorithm_uid=?";

		ArrayList<Object>  paramList = new ArrayList<Object> ();
		paramList.add(newStatus);
		paramList.add (new Timestamp(new Date().getTime()));
		paramList.add(algorithmUid);

		try {
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in Update Condition Status: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}

		logger.debug(" ...leaving Algorithm DAO - updated algorithm status ..");
	}

	
	  public Collection<Object>  selectDsmAlgorithmDTCollection() throws NEDSSSystemException {
	    	logger.debug("selectDsmAlgorithmDTCollection all DSM Algorithm records..");
	    	Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ResultSetMetaData resultSetMetaData = null;
	    	ResultSetUtils resultSetUtils = new ResultSetUtils();
	    	// note algorithm_payload \"algorithmPayload\", is not selected for the list
	        String SELET_DSM_ALGORITHM_LIST ="SELECT dsm_algorithm_uid \"dsmAlgorithmUid\",  algorithm_nm \"algorithmNm\", event_type \"eventType\", condition_list \"conditionList\", frequency \"frequency\" , apply_to \"applyTo\", sending_system_list \"sendingSystemList\", reporting_system_list \"reportingSystemList\", event_action \"eventAction\","
	        	+" algorithm_payload \"algorithmPayload\", admin_comment \"adminComment\", status_cd \"statusCd\", status_time \"statusTime\", last_chg_user_id \"lastChgUserId\", last_chg_time \"lastChgTime\" "
	            	+" FROM DSM_algorithm where status_cd='A'";
	    	try {
	    		dbConnection = getConnection();
	    	} catch (NEDSSSystemException nedssEx) {
	    		logger.fatal("SQLException while obtaining database connection "
	    					+ "for DSMAlgorithmDaoImpl.selectDsmAlgorithmDTCollection: "+nedssEx.getMessage(), nedssEx);
	    		throw new NEDSSSystemException(nedssEx.getMessage(), nedssEx);
	    	}
	    	try {
	    		preparedStmt = dbConnection.prepareStatement(SELET_DSM_ALGORITHM_LIST);
	    		resultSet = preparedStmt.executeQuery();
	    		ArrayList<Object> algorithmList = new ArrayList<Object> ();
	    		if (resultSet != null) {
	    			resultSetMetaData = resultSet.getMetaData();
	    			algorithmList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, DSMAlgorithmDT.class,algorithmList);

	    				logger.debug("Returned Decision Support Mgr algorithms collection");
	    			}
	    			return algorithmList;
    		} catch (SQLException sqlEx) {
    			logger.fatal("SQLException while selecting algorithm records occurred in DSMAlgorithmDaoImpl.selectDsmAlgorithmDTCollection "+sqlEx.getMessage(), sqlEx);
    			throw new NEDSSSystemException("SQLException while selecting algorithm records occurred in DSMAlgorithmDaoImpl.selectDsmAlgorithmDTCollection "+ sqlEx.getMessage(), sqlEx);
    		} catch (ResultSetUtilsException resultSetUtilEx) {
    			logger.fatal("Error in result set handling while selecting algorithm list: DSMAlgorithmDaoImpl.selectDsmAlgorithmDTCollection.",resultSetUtilEx);
    			throw new NEDSSSystemException(resultSetUtilEx.toString(), resultSetUtilEx);
    		} finally {
    			closeResultSet(resultSet);
    			closeStatement(preparedStmt);
    			releaseConnection(dbConnection);
    		}
    	}
}
