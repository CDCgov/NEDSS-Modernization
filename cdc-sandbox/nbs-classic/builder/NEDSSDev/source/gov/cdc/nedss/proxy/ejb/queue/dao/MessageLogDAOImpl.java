package gov.cdc.nedss.proxy.ejb.queue.dao;

import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.proxy.ejb.queue.vo.MessageLogVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MessageLogDAOImpl extends DAOBase
{
	
	static final PropertyUtil propUtil = PropertyUtil.getInstance();
	
    static final LogUtils logger = new LogUtils(MessageLogDAOImpl.class.getName());
    public static final String INSERT_MESSAGE_LOG = 
            "INSERT INTO message_log "
            + " (message_txt "
            + " ,condition_cd "
            + " ,person_uid "
            + " ,assigned_to_uid "
            + " ,event_uid "
            + " ,event_type_cd "
            + " ,message_status_cd "
            + " ,record_status_cd "
            + " ,record_status_time "
            + " ,add_time "
            + " ,add_user_id "
            + " ,last_chg_time "
            + " ,last_chg_user_id) "
        + " VALUES "
            + " (?,?,?,?,?,?,?,?,?,?,?,?,?) ";



    
    public Integer getCount(MessageLogDT messageLogDt, NBSSecurityObj nbssecurityObj)
    {  
        int resultCount = 0;  
        try
        { 
            ArrayList al = new ArrayList();
            al.add( messageLogDt.getAssignedToUid() );
            String whereClause = nbssecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT, "phc");
            String query = WumSqlQuery.COUNT_MESSAGE_LOG  + ( (whereClause != null && whereClause.length() > 0) ? " and " + whereClause : "" );
            resultCount = ((Integer) preparedStmtMethod(null, al, query, NEDSSConstants.SELECT_COUNT));
            logger.debug("Message_Log resultCount is " + resultCount);
        } 
        catch (Exception ex)
        {
            logger.fatal("Error while updating " + DataTables.MESSAGE_LOG, ex);
            throw new NEDSSSystemException(ex.toString());
        } 
        return resultCount;
    }
    
    public List<Object> getMessageLog(Long assignedToId, NBSSecurityObj nbssecurityObj)
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null; 
        ResultSet rs = null;
        try{
        	dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
        	logger.fatal("SQLException while obtaining database connection for getMessageLog ", nsex);
        	throw new NEDSSSystemException(nsex.toString());
        }
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        List<Object> arMessageVos = new ArrayList<Object>();
        try
        {
            String whereClause = nbssecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT, "phc");
            String query=WumSqlQuery.SELECT_MESSAGE_LOG  + ( (whereClause != null && whereClause.length() > 0) ? " and " + whereClause : "" );
            	
           
            preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setLong(1, assignedToId);

            rs = preparedStmt.executeQuery();  
            
            resultSetUtils.mapRsToBeanList(rs, rs.getMetaData(), MessageLogVO.class, arMessageVos);
            
        }
        catch (SQLException sqlex)
        {
            logger.fatal("SQLException in selecting from MESSAGE_LOG :"+sqlex.getMessage(), sqlex);
            throw new NEDSSDAOSysException(sqlex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Error while selecting from MESSAGE_LOG "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
        	closeResultSet(rs);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return arMessageVos;
    }
    
    public int update(MessageLogVO messageLogVO) throws NEDSSDAOSysException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        logger.debug(" Update " + DataTables.MESSAGE_LOG  + "=" + WumSqlQuery.UPDATE_MESSAGE_LOG);
        try{
        	dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
        	logger.fatal("SQLException while obtaining database connection for update ", nsex);
        	throw new NEDSSSystemException(nsex.toString());
        }
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.UPDATE_MESSAGE_LOG);  
            preparedStmt.setString(1, messageLogVO.getMessageLogDT().getMessageStatusCd());
            preparedStmt.setLong(2, messageLogVO.getMessageLogDT().getAssignedToUid()); 
            preparedStmt.setLong(3, messageLogVO.getMessageLogDT().getMessageLogUid());
            resultCount = preparedStmt.executeUpdate();
            logger.debug("resultCount is " + resultCount);
        }
        catch (SQLException sqlex)
        {
            logger.fatal("SQLException messageTxt:"+sqlex.getMessage(), sqlex);
            throw new NEDSSDAOSysException(sqlex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Error while updating " + DataTables.MESSAGE_LOG, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return resultCount;
    }
    
     public Integer delete(MessageLogVO messageLogVO) throws NEDSSDAOSysException
    {
        
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        logger.debug(" Delete " + DataTables.MESSAGE_LOG  + "=" + WumSqlQuery.UPDATE_MESSAGE_LOG);
        try{
        	dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
        	logger.fatal("SQLException while obtaining database connection for delete ", nsex);
        	throw new NEDSSSystemException(nsex.toString());
        }
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.DELETE_MESSAGE_LOG); 
            int i = 1;
            
            preparedStmt.setString(i++, messageLogVO.getMessageLogDT().getRecordStatusCd());
            preparedStmt.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
            preparedStmt.setLong(i++, messageLogVO.getMessageLogDT().getLastChgUserId());
            
            preparedStmt.setLong(i++, messageLogVO.getMessageLogDT().getAssignedToUid());
            preparedStmt.setLong(i++, messageLogVO.getMessageLogDT().getMessageLogUid());
            
            resultCount = preparedStmt.executeUpdate();
            logger.debug("resultCount is " + resultCount);
        }
        catch (SQLException sqlex)
        {
            logger.fatal("SQLException messageTxt:", sqlex);
            throw new NEDSSDAOSysException(sqlex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Error while deleting " + DataTables.MESSAGE_LOG, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return resultCount;
    }
    
    public void storeMessageLogDTCollection(Collection<MessageLogDT> messageLogDTCollection)
    {
    	try{
	        if(messageLogDTCollection!=null)
	        {
	           java.util.Iterator<MessageLogDT> it = messageLogDTCollection.iterator();
	           while(it.hasNext()){
	               MessageLogDT messageLogDT=(MessageLogDT)it.next();
	               logger.debug(messageLogDT);
	               insert(messageLogDT);
	           }
	        }
    	}catch(Exception ex){
    		logger.fatal("Exception = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
   }

    public  void insert(MessageLogDT messageLogDT) throws NEDSSDAOSysException
    {
        
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        logger.debug("  Insert " + DataTables.MESSAGE_LOG  + "=" + INSERT_MESSAGE_LOG);
        dbConnection = getConnection();
        try
        {
            int i =1;
            preparedStmt = dbConnection.prepareStatement(INSERT_MESSAGE_LOG);  
            preparedStmt.setString(i++, messageLogDT.getMessageTxt());
            preparedStmt.setString(i++, messageLogDT.getConditionCd());
            preparedStmt.setLong(i++, messageLogDT.getPersonUid());
            preparedStmt.setLong(i++, messageLogDT.getAssignedToUid());
            preparedStmt.setLong(i++, messageLogDT.getEventUid());
            preparedStmt.setString(i++, messageLogDT.getEventTypeCd());
            preparedStmt.setString(i++, messageLogDT.getMessageStatusCd());
            preparedStmt.setString(i++, messageLogDT.getRecordStatusCd());
            preparedStmt.setTimestamp(i++, messageLogDT.getRecordStatusTime());
            preparedStmt.setTimestamp(i++, messageLogDT.getAddTime());
            preparedStmt.setLong(i++, messageLogDT.getUserId());
            preparedStmt.setTimestamp(i++, messageLogDT.getLastChgTime());
            preparedStmt.setLong(i++, messageLogDT.getLastChgUserId());
            resultCount = preparedStmt.executeUpdate();
            logger.debug("resultCount is " + resultCount);
        }
        catch (SQLException sqlex)
        {
            logger.fatal("SQLException messageTxt:", sqlex);
            throw new NEDSSDAOSysException(sqlex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Error while updating " + DataTables.MESSAGE_LOG, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }
}

