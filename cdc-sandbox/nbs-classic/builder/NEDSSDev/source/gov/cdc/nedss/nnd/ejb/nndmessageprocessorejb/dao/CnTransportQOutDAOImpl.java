package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.CnTransportQOutDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;



/**
* Name:		CnTransportQOutDAOImpl.java
* Description:	DAO Object for New PAM NND Intermediary Messages
* Copyright:	Copyright (c) 2008
* Company: 	Computer Sciences Corporation
* @author	Beau Bannerman
*/

public class CnTransportQOutDAOImpl extends DAOBase{
	static final LogUtils logger = new LogUtils(CnTransportQOutDAOImpl.class.getName());
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private static String INSERT_CN_TRANSPORTQ_OUT_SQL = "INSERT INTO " + DataTables.CN_TRANSPORTQ_OUT_TABLE + " (notification_uid, add_reason_cd, add_time, add_user_id, last_chg_reason_cd, last_chg_time, last_chg_user_id, message_payload, report_status_cd, record_status_cd, record_status_time, notification_local_id, public_health_case_local_id, version_ctrl_nbr) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static String SELECT_CN_TRANSPORTQ_OUT = "SELECT cn_transportq_out_uid \"cnTransportQOutUID\", " +
													    "notification_uid \"notificationUid\", " +
													    "add_reason_cd \"addReasonCd\", " + 
													    "add_time \"addTime\", " +
													    "add_user_id \"addUserId\", " +
													    "last_chg_reason_cd \"lastChgReasonCd\", " +
													    "last_chg_time \"lastChgTime\", " +
													    "last_chg_user_id \"lastChgUserId\", " +
													    "report_status_cd \"reportStatusCd\", " +
													    "record_status_cd \"recordStatusCd\", " +
													    "record_status_time \"recordStatusTime\", " +
													    "message_payload \"message_payload\", " + 
													    "notification_local_id \"notificationLocalId\", " +
													    "public_health_case_local_id \"publicHealthCaseLocalId\", " +
													    "version_ctrl_nbr \"versionCtrlNbr\" " +
													    "FROM cn_transportq_out WHERE cn_transportq_out_uid = ?";
	private static String SELECT_PREVIOUS_CN_TRANSPORTQ_OUT = "SELECT cn_transportq_out_uid FROM " + DataTables.CN_TRANSPORTQ_OUT_TABLE + 
														"WHERE notification_uid = ? AND record_status_cd = '" + NEDSSConstants.NND_UNPROCESSED_MESSAGE + "'";
	
	private static String SELECT_CN_TRANSPORTQ_OUT_BY_CASE_LOCAL_ID = "SELECT cn_transportq_out_uid \"cnTransportQOutUID\", " +
		    "notification_uid \"notificationUid\", " +
		    "add_reason_cd \"addReasonCd\", " + 
		    "add_time \"addTime\", " +
		    "add_user_id \"addUserId\", " +
		    "last_chg_reason_cd \"lastChgReasonCd\", " +
		    "last_chg_time \"lastChgTime\", " +
		    "last_chg_user_id \"lastChgUserId\", " +
		    "report_status_cd \"reportStatusCd\", " +
		    "record_status_cd \"recordStatusCd\", " +
		    "record_status_time \"recordStatusTime\", " +
		    "message_payload \"messagePayload\", " + 
		    "notification_local_id \"notificationLocalId\", " +
		    "public_health_case_local_id \"publicHealthCaseLocalId\", " +
		    "version_ctrl_nbr \"versionCtrlNbr\" " +
		    "FROM cn_transportq_out WHERE cn_transportq_out_uid = (select max(cn_transportq_out_uid) from cn_transportq_out where public_health_case_local_id = ?)";
	
    /**
     * @methodname loadObject
     * This method load a cnTransportQOutDT record in database.
     * @param long cnTransportQOutUID
     * @return Object a cnTransportQOutDT
     * @throws NEDSSSystemException
     */
    public Object loadObject(long cnTransportQOutUID)
                      throws
                             NEDSSSystemException
    {

    	CnTransportQOutDT cnTransportQOutDT = selectItem(cnTransportQOutUID);
    	cnTransportQOutDT.setItNew(false);
    	cnTransportQOutDT.setItDirty(false);
    	cnTransportQOutDT.setItDelete(false);

        return cnTransportQOutDT;
    }

    /**
     * @methodname selectItem
     * This is a private method. This method load a cnTransportQOutDT record by cnTransportQOutUID(primary key) in database .
     * @param long cnTransportQOutUID
     * @return CnTransportQOutDT a cnTransportQOutDT
     * @throws NEDSSSystemException
     */
    private CnTransportQOutDT selectItem(long cnTransportQOutUID)
                               throws NEDSSSystemException,
                                      NEDSSSystemException
    {
    	CnTransportQOutDT cnTransportQOutDT = new CnTransportQOutDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();

        /**
         * Selects CnTransportQOutDT from CnTransportQOut table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_CN_TRANSPORTQ_OUT);
            preparedStmt.setLong(1, cnTransportQOutUID);
            resultSet = preparedStmt.executeQuery();
            logger.debug(
                    "cnTransportQOutDT object for: cnTransportQOutUID = " +
                    cnTransportQOutUID);
            resultSetMetaData = resultSet.getMetaData();
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                              resultSetMetaData,
                                                              cnTransportQOutDT.getClass(),
                                                              pList);

            for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
            {
            	cnTransportQOutDT = (CnTransportQOutDT)anIterator.next();
            	cnTransportQOutDT.setItNew(false);
            	cnTransportQOutDT.setItDirty(false);
            	cnTransportQOutDT.setItDelete(false);
                logger.info(
                        "cnTransportQOutDT.getCnTransportQOutUID() - : " +
                        cnTransportQOutDT.getCnTransportQOutUID());
                logger.info(
                        "cnTransportQOutDT.getRecordStatusCd() - : " +
                        cnTransportQOutDT.getRecordStatusCd());
                logger.info(
                        "cnTransportQOutDT.getRecordStatusTime() - : " +
                        cnTransportQOutDT.getRecordStatusTime());
            }
        }
        catch (SQLException sex)
        {
            logger.fatal(
                    "SQLException while selecting " +
                    "CnTransportQOutDT; id = " + cnTransportQOutUID, sex);
            throw new NEDSSSystemException(sex.getMessage());
        }
        catch (Exception ex)
        {
            logger.fatal(
                    "Exception while selecting CnTransportQOutDT; id = " +
                    cnTransportQOutUID, ex);
            throw new NEDSSSystemException(ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        logger.debug(
		        "Returning CnTransportQOutDT object:\n" +
		        cnTransportQOutDT.toString());

        return cnTransportQOutDT;
    } //end of selecting item
    
    /**
     * @methodname selectItem
     * This is a private method. This method load a cnTransportQOutDT record by case Local Id in database .
     * @param String caseLocalId
     * @return CnTransportQOutDT a cnTransportQOutDT
     * @throws NEDSSSystemException
     */
    public CnTransportQOutDT selectItemByCaseLocalId(String  caseLocalId)
                               throws NEDSSSystemException,
                                      NEDSSSystemException
    {
    	CnTransportQOutDT cnTransportQOutDT = new CnTransportQOutDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;

        /**
         * Selects CnTransportQOutDT from CnTransportQOut table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_CN_TRANSPORTQ_OUT_BY_CASE_LOCAL_ID);
            preparedStmt.setString(1, caseLocalId);
            resultSet = preparedStmt.executeQuery();
            logger.debug(
                    "cnTransportQOutDT object for: caseLocalId = " +
                    		caseLocalId);
            while(resultSet.next()) {
            	cnTransportQOutDT.setCnTransportQOutUID(new Long(resultSet.getLong("cnTransportQOutUID")));
            	cnTransportQOutDT.setNotificationUID(resultSet.getLong("notificationUid"));
            	cnTransportQOutDT.setAddReasonCd(resultSet.getString("addReasonCd")); 
            	cnTransportQOutDT.setAddTime(resultSet.getTimestamp("addTime")); 
            	cnTransportQOutDT.setAddUserId(new Long(resultSet.getLong("addUserId")));
            	cnTransportQOutDT.setLastChgReasonCd(resultSet.getString("lastChgReasonCd")); 
            	cnTransportQOutDT.setLastChgTime(resultSet.getTimestamp("lastChgTime")); 
            	cnTransportQOutDT.setLastChgUserId(new Long(resultSet.getLong("lastChgUserId")));
            	cnTransportQOutDT.setReportStatus(resultSet.getString("reportStatusCd"));
            	cnTransportQOutDT.setRecordStatusCd(resultSet.getString("recordStatusCd")); 
            	cnTransportQOutDT.setRecordStatusTime(resultSet.getTimestamp("recordStatusTime")); 
            	cnTransportQOutDT.setLastChgUserId(new Long(resultSet.getLong("lastChgUserId")));
            	cnTransportQOutDT.setNotificationLocalId(resultSet.getString("notificationLocalId"));
            	cnTransportQOutDT.setPublicHealthCaseLocalId(resultSet.getString("publicHealthCaseLocalId"));
            	cnTransportQOutDT.setVersionCtrlNbr(new Integer(resultSet.getInt("versionCtrlNbr")));
    			cnTransportQOutDT.setMessagePayload(resultSet.getString("messagePayload"));
    			
    		logger.debug("returned Document Information");    		
            
            }
        }
        catch (SQLException sex)
        {
            logger.fatal(
                    "SQLException while selecting " +
                    "CnTransportQOutDT; case Local id = " + caseLocalId, sex);
            throw new NEDSSSystemException(sex.getMessage());
        }
        catch (Exception ex)
        {
            logger.fatal(
                    "Exception while selecting CnTransportQOutDT; case local id = " +
                    		caseLocalId, ex);
            throw new NEDSSSystemException(ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return cnTransportQOutDT;
    } //end of selecting item

	
    
    public void findPreviousCnTransportQOutDT(long cnTransportQOutUID) throws NEDSSDAOSysException
    {
    	CnTransportQOutDT cnTransportQOutDT = new CnTransportQOutDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();

        /**
         * Selects CnTransportQOutDT from CnTransportQOut table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_PREVIOUS_CN_TRANSPORTQ_OUT);
            preparedStmt.setLong(1, cnTransportQOutUID);
            resultSet = preparedStmt.executeQuery();
            logger.debug(
                    "cnTransportQOutDT object for: cnTransportQOutUID = " +
                    cnTransportQOutUID);
            resultSetMetaData = resultSet.getMetaData();
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                              resultSetMetaData,
                                                              cnTransportQOutDT.getClass(),
                                                              pList);

            for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
            {
            	cnTransportQOutDT = (CnTransportQOutDT)anIterator.next();
            	cnTransportQOutDT.setItNew(false);
            	cnTransportQOutDT.setItDirty(false);
            	cnTransportQOutDT.setItDelete(false);
                logger.info(
                        "cnTransportQOutDT.getCnTransportQOutUID() - : " +
                        cnTransportQOutDT.getCnTransportQOutUID());
                logger.info(
                        "cnTransportQOutDT.getRecordStatusCd() - : " +
                        cnTransportQOutDT.getRecordStatusCd());
                logger.info(
                        "cnTransportQOutDT.getRecordStatusTime() - : " +
                        cnTransportQOutDT.getRecordStatusTime());
            }
        }
        catch (SQLException sex)
        {
        	String errorMsg = "Exception while selecting CnTransportQOutDT; id = " +
                    cnTransportQOutUID + "\n"; 
            logger.fatal(errorMsg, sex);
            throw new NEDSSSystemException(errorMsg + sex.getMessage());
        }
        catch (Exception ex)
        {
        	String errorMsg = "Exception while selecting CnTransportQOutDT; id = " +
            cnTransportQOutUID + "\n"; 
            ex.printStackTrace();
            logger.fatal(errorMsg, ex);
            throw new NEDSSSystemException(errorMsg + ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        logger.debug(
		        "Returning CnTransportQOutDT object:\n" +
		        cnTransportQOutDT.toString());
    }
    
	public void insertCnTransportQOutDT (CnTransportQOutDT cnTransportQOutDT) throws NEDSSDAOSysException
    {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
		    ResultSet resultSet = null;
			String sql;
		    String payload = cnTransportQOutDT.getMessagePayload();
			
	        sql = INSERT_CN_TRANSPORTQ_OUT_SQL;

			logger.debug("INSERT_CN_TRANSPORTQ_OUT="+sql);
			dbConnection = getConnection();
			try{
				preparedStmt = dbConnection.prepareStatement(sql);
				int i = 1;
				
			    preparedStmt.setLong(i++, cnTransportQOutDT.getNotificationUID().longValue()); //1
			    preparedStmt.setString(i++, cnTransportQOutDT.getAddReasonCd()); //2
			    preparedStmt.setTimestamp(i++, cnTransportQOutDT.getAddTime()); //3
			    if(cnTransportQOutDT.getAddUserId()==null)
				    preparedStmt.setNull(i++, Types.INTEGER);//4
			    else
			    	preparedStmt.setLong(i++, cnTransportQOutDT.getAddUserId().longValue()); //4
			    preparedStmt.setString(i++, cnTransportQOutDT.getLastChgReasonCd()); //5
			   	preparedStmt.setTimestamp(i++, cnTransportQOutDT.getLastChgTime()); //6
			    if(cnTransportQOutDT.getLastChgUserId()==null)
				    preparedStmt.setNull(i++, Types.INTEGER);//7
			    else
			    	preparedStmt.setLong(i++, cnTransportQOutDT.getLastChgUserId().longValue()); //7
			    //8 - Empty BLOB for Oracle, messagePayload string for SQL
		        preparedStmt.setString(i++, cnTransportQOutDT.getMessagePayload()); //8
			    preparedStmt.setString(i++, cnTransportQOutDT.getReportStatus()); //9
			    preparedStmt.setString(i++, cnTransportQOutDT.getRecordStatusCd()); //10
			    preparedStmt.setTimestamp(i++, cnTransportQOutDT.getRecordStatusTime()); //11
			    preparedStmt.setString(i++, cnTransportQOutDT.getNotificationLocalId()); //12
			    preparedStmt.setString(i++, cnTransportQOutDT.getPublicHealthCaseLocalId()); //13
                preparedStmt.setInt(i++, cnTransportQOutDT.getVersionCtrlNbr().intValue()); //14
			    preparedStmt.executeUpdate();
			}
			catch(SQLException sqlex)
			{
				String errorMsg = "SQLException while inserting CnTransportQOutDT into INSERT_CN_TRANSPORTQ_OUT: " +
								   cnTransportQOutDT.toString() + "\n";
			    logger.fatal(errorMsg, sqlex);
			    throw new NEDSSDAOSysException( errorMsg + sqlex.toString() );
			}
			catch(Exception ex)
			{
				String errorMsg = "SQLException while inserting CnTransportQOutDT into INSERT_CN_TRANSPORTQ_OUT: " +
				   cnTransportQOutDT.toString() + "\n";
			    logger.fatal(errorMsg, ex);
			    throw new NEDSSSystemException(errorMsg + ex.toString());
			}
			finally
			{
			  closeStatement(preparedStmt);
			  releaseConnection(dbConnection);
			}

}//end of insert method
	
	
	
	

}
