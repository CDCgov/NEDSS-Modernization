

package gov.cdc.nedss.elr.ejb.msginprocessor.dao;

import gov.cdc.nedss.elr.ejb.msginprocessor.helper.ELRConstants;
import gov.cdc.nedss.elr.helper.ELRActivityLogSearchDT;
import gov.cdc.nedss.elr.helper.ElrActivityLogDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMap;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMapHome;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.rmi.PortableRemoteObject;



/**
* Name:        ElrActivityLogDAOImpl.java
* Description:  This class is for ELRActivityLog Data Access Object class
*               which provides access to a particular ELRActivityLog record
*               in database. 
* Copyright:    Copyright (c) 2002
* Company:      Computer Sciences Corporation
* @author:      NEDSS Development Team
* @version      NBS1.1
*/
public class ElrActivityLogDAOImpl extends DAOBase
{

    static final LogUtils logger = new LogUtils( ElrActivityLogDAOImpl.class.getName());
    private String SELECT_PASS_FAIL_COUNT = "select fail.failCount, pass.passCount from (";
    private String SELECT_FAIL_COUNT = "select count(*) failCount from elr_activity_log where status_cd = 'F' and process_time between ? and ?";//) fail, "+
    private String SELECT_PASS_COUNT = "(select count(*) passCount from elr_activity_log where status_cd = 'S' and process_time between ? and ?";//) pass";

    private static final String SET_PK
      = ELRConstants.MSG_IN_ACTLOG_SELECT_PK;

    private static final String SET_UID
      = ELRConstants.MSG_IN_ACTLOG_SELECT_UID;

    private static final String SELECT_BY_PK
      = ELRConstants.MSG_IN_ACTLOG_SELECT
      + SET_PK;

    private static final String SELECT_BY_UID
      = ELRConstants.MSG_IN_ACTLOG_SELECT
      + SET_UID;

    private static final String UPDATE
      = ELRConstants.MSG_IN_ACTLOG_UPDATE
      + SET_PK;

    private static final String INSERT
      = ELRConstants.MSG_IN_ACTLOG_INSERT;

    private static int seq = 1;

    private static Map<Object,Object> processDescCollection;
    public ElrActivityLogDAOImpl()
    {
	seq++;
    }

    /**
     * @methodname create
     * This method inserts a ElrActivityLogDT object into database, if ElrActivityLogDT object is new.
     * After inserting the records, set all the flags of the ElrActivityLogDT object to false
     * @param long messageUID
     * @param Object a ElrActivityLogDT object
     * @return  long a positive value if successful
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    public long create(long uid, Object obj)
      throws NEDSSSystemException
    {
    	try{
	        ElrActivityLogDT dt = (ElrActivityLogDT)obj;
	        if(dt == null)
	           throw new NEDSSSystemException("Error: try to create null ElrActivityLogDT object.");
	        insertElrActivityLog((ElrActivityLogDT)obj);
	        return uid;
    	}catch(Exception ex){
    		logger.fatal("Exception  uid:"+uid+" Exception = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }


    /**
     * @methodname store
     * This method updates a ElrActivityLogDT record in database.
     * @param Object a ElrActivityLogDT object
     * @throws NEDSSSystemException
     */
    public void store(Object obj)
      throws NEDSSSystemException
    {
    	try{
	        ElrActivityLogDT dt = (ElrActivityLogDT)obj;
	        if(dt == null)
	           throw new NEDSSSystemException("Error: try to store null ElrActivityLogDT object.");
	        if(dt.isItNew())
	          insertElrActivityLog(dt);
	        if(dt.isItDirty())
	          updateElrActivityLog(dt);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

      /**
     * @methodname load
     * This method loads  ElrActivityLogDT collection in database.
     * @param long messageUID
     * @return Object a collection of ElrActivityLogDTs
     * @throws NEDSSSystemException
     */
    public Collection<Object>  load(long uid)
      throws NEDSSSystemException
    {
    	try{
    		return selectElrActivityLog(uid);
    	}catch(Exception ex){
    		logger.fatal("Exception  uid:"+uid+" Exception = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

     /**
     * @methodname getActivityReport
     * This method loads a ElrActivityLogDT collection from database by their time and status.
     * @param String from time of ElrActivityLog
     * @param String to time of ElrActivityLog
     * @param ArrayList<Object> statusList of ElrActivityLog
     * @return Collection<Object>  a collection of ElrActivityLogDTs
     * @throws NEDSSSystemException
     */
    @SuppressWarnings("unchecked")
	public Collection<Object>  getActivityReport(ELRActivityLogSearchDT elrActivityLogSearchDT)
    throws NEDSSSystemException
    {
    	try{
	      Collection<Object>  statusColl = elrActivityLogSearchDT.getStatusCd();
	      if(statusColl == null||statusColl.isEmpty())
	        throw new NEDSSSystemException("statusCd collection null or empty!");
	
	      //set status cd
	      StringBuffer sql = new StringBuffer(ELRConstants.MSG_IN_ACTLOG_FILTER + "(");
	       Iterator<Object>  statusCdIter = statusColl.iterator();
	        while(statusCdIter.hasNext()){
	          String statusCd = (String)statusCdIter.next();
	          sql.append("'");
	          sql.append(statusCd);
	          sql.append("'");
	          if(statusCdIter.hasNext())
	            sql.append(",");
	        }
	        sql.append(")");
	
	        //set timestamp where clause
	        ArrayList<Object> bindVariables = new ArrayList<Object> ();
	        bindVariables.add(elrActivityLogSearchDT.getProcessStartTime());//add start time
	        bindVariables.add(elrActivityLogSearchDT.getProcessEndTime());//add end time
	        //set all individual fields in where clause
	        if(elrActivityLogSearchDT.getFillerNbr() != null)
	          sql.append(" and filler_nbr = '" + elrActivityLogSearchDT.getFillerNbr() + "'");
	        if(elrActivityLogSearchDT.getMsgObservationUid() != null)
	          sql.append(" and msg_observation_uid = "+elrActivityLogSearchDT.getMsgObservationUid().toString());
	        if(elrActivityLogSearchDT.getOdsObservationUid() != null)
	          sql.append(" and ods_observation_uid = '"+elrActivityLogSearchDT.getOdsObservationUid()+"'");
	        if(elrActivityLogSearchDT.getReportingFacNm() != null)
	          sql.append(" and report_fac_nm = '"+elrActivityLogSearchDT.getReportingFacNm()+"'");
	        if(elrActivityLogSearchDT.getSubjectNm()!= null)
	          sql.append(" and subject_nm like '%"+elrActivityLogSearchDT.getSubjectNm().toUpperCase()+"%'");
	        //set order by
	        sql.append(" order by id,elr_activity_log_seq ");
	        //query data
	        ElrActivityLogDT elrActivityLogDT = new ElrActivityLogDT();
	        System.out.println("sql="+sql.toString());
	        ArrayList<Object> firstRS = (ArrayList<Object> )super.preparedStmtMethod(elrActivityLogDT,bindVariables,sql.toString(),"SELECT");
	        //process processMessageCollection
	        ArrayList<Object> newRS = new ArrayList<Object> ();
	       Iterator<Object>  firstRSIter = firstRS.iterator();
	        ElrActivityLogDT dt1 = null;
	        ElrActivityLogDT dt2 = null;
	        Collection<Object>  processDescColl = null;
	        while(firstRSIter.hasNext()){
	          if(dt1 == null){//at first record
	            dt1 = (ElrActivityLogDT)firstRSIter.next();
	            processDescColl = new ArrayList<Object> ();
	            //call to get message for process cd
	            String processDesc = getProcessCdDescription(dt1);
	
	            processDescColl.add(processDesc);
	            //add coll to dt
	            dt1.setProcessMessageCollection(processDescColl);
	            //add dt to new RS
	            newRS.add(dt1);
	
	          }//end if
	          else{
	            dt2 = (ElrActivityLogDT)firstRSIter.next();
	            if (dt2.getMsgObservationUid().equals(dt1.getMsgObservationUid())){ //same log record
	              //add desc to collection
	              processDescColl.add(getProcessCdDescription(dt2));
	              if (dt2.getStatusCd().equalsIgnoreCase("S")){
	                newRS.add(dt2);
	              }
	            }//end nested if
	            else{ //new log record
	                //create new collection
	                processDescColl = new ArrayList<Object> ();
	                processDescColl.add(getProcessCdDescription(dt2));
	                dt2.setProcessMessageCollection(processDescColl);
	                newRS.add(dt2);
	            }//end nested else
	            dt1 = dt2; //asssign dt2 to dt1
	          }//end else
	
	        }//end while
	
	        //sort the collection
	        NedssUtils nedssUtils = new NedssUtils();
	        if(elrActivityLogSearchDT.getSortOrder()!= null&&newRS!=null&&!newRS.isEmpty())
	          nedssUtils.sortObjectByColumn(elrActivityLogSearchDT.getSortOrder(),newRS);
	        else
	          logger.error("elrActivityLogSearchDT.getSortOrder() is null!");
	        return newRS;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    private String getProcessCdDescription(ElrActivityLogDT elrActivityLogDt)throws NEDSSSystemException{
      String aProcessCd = elrActivityLogDt.getProcessCd();
      if(processDescCollection  == null||!processDescCollection.containsKey(aProcessCd)){
        //get the desc from ejb
        NedssUtils nedssUtils = new NedssUtils();
        Object theLookedUpObject;
       logger.debug("get reference for SRTMapEJB");
       String sBeanJndiName = JNDINames.SRT_CACHE_EJB;
       theLookedUpObject = nedssUtils.lookupBean(sBeanJndiName);
       SRTMapHome srtMapHome = (SRTMapHome) PortableRemoteObject.narrow(theLookedUpObject, SRTMapHome.class);
       try{
         SRTMap srtMap = srtMapHome.create();
         this.processDescCollection  = srtMap.getCodedValues(ELRConstants.ELR_LOG_PROCESS);
         return (String)processDescCollection.get(aProcessCd);
       }
       catch(Exception e){
    	 logger.fatal("Exception  = "+e.getMessage(), e);
         throw new NEDSSSystemException("error getting srtmap values in ELRActivityLog.getProcessCdDesc!");
       }


        //set to map
        //return string

      }
      else{
        //look to see if it is in the map
          String message = (String)processDescCollection.get(aProcessCd);
          if(aProcessCd.equals(ELRConstants.JURIS_ASSIGN_1) ||
               aProcessCd.equals(ELRConstants.JURIS_ASSIGN_2) ||
               aProcessCd.equals(ELRConstants.JURIS_ASSIGN_3) ||
               aProcessCd.equals(ELRConstants.ELR_PROCESS_CD_SYSTEM_EXCEPTION)) {
              message = message + " "+elrActivityLogDt.getDetailTxt();
            }
          return message;
      }//end of else
    }
     /**
     * @methodname getMessageCount
     * This method counts how many ELR messages have been sent during certain time line,
     * including all the status of those messages.
     * @param String from time of ElrActivityLog
     * @param String to time of ElrActivityLog
     * @return Integer number of all the messages in certain time line.
     * @throws NEDSSSystemException
     */
    public Integer getMessageCount(String from, String to)
    throws NEDSSSystemException
    {
	Connection dbConnection = null;
	try
        {
           dbConnection = getConnection(NEDSSConstants.ODS);
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " + nsex.getMessage(), nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

	PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
	int count = 0;

        try
        {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            java.util.Date fromDate = df.parse(from);
            java.util.Date toDate = df.parse(to);
            String sql = sql = ELRConstants.MSG_IN_MESSAGE_COUNT;

            preparedStmt = dbConnection.prepareStatement(sql);
	    preparedStmt.setTimestamp(1, new Timestamp(fromDate.getTime()));
            preparedStmt.setTimestamp(2, new Timestamp(toDate.getTime()));
            resultSet = preparedStmt.executeQuery();
	    if(resultSet.next())
                count = resultSet.getInt(1);
	    logger.info("getMessageCount: " + count);
	    return new Integer(count);

        }
        catch(SQLException se)
        {
        	logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSSystemException("Error: SQLException while selecting \n" + se.getMessage());
        }
        catch(ParseException pe)
        {
        	logger.fatal("ParseException  = "+pe.getMessage(), pe);
          throw new NEDSSSystemException("Error: ParseException while attempting to convert to and from time string to date objects \n"+pe.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }

    public Collection<Object>  getPassFailCount(ELRActivityLogSearchDT elrActivityLogSearchDT)throws NEDSSSystemException {
          Connection conn = null;
          PreparedStatement pstmt = null;
          ResultSet rs = null;
          ArrayList<Object> toReturn = new ArrayList<Object> ();
          try
            {
               conn = getConnection(NEDSSConstants.ODS);
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("SQLException while obtaining database connection " + nsex.getMessage(), nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

          try {

            if(elrActivityLogSearchDT.getSubjectNm() != null) {
              SELECT_FAIL_COUNT = SELECT_FAIL_COUNT + " and subject_nm = ?";
              SELECT_PASS_COUNT = SELECT_PASS_COUNT + " and subject_nm = ?";
            }
            if(elrActivityLogSearchDT.getMsgObservationUid() != null) {
              SELECT_FAIL_COUNT = SELECT_FAIL_COUNT + " and msg_observation_uid = ?";
              SELECT_PASS_COUNT = SELECT_PASS_COUNT + " and msg_observation_uid = ?";
            }
            if(elrActivityLogSearchDT.getOdsObservationUid() != null) {
              SELECT_FAIL_COUNT = SELECT_FAIL_COUNT + " and ods_observation_uid = ?";
              SELECT_PASS_COUNT = SELECT_PASS_COUNT + " and ods_observation_uid = ?";
            }
            if(elrActivityLogSearchDT.getReportingFacNm() != null) {
              SELECT_FAIL_COUNT = SELECT_FAIL_COUNT + " and report_fac_nm = ?";
              SELECT_PASS_COUNT = SELECT_PASS_COUNT + " and report_fac_nm = ?";
            }
            if(elrActivityLogSearchDT.getFillerNbr() != null) {
              SELECT_FAIL_COUNT = SELECT_FAIL_COUNT + " and filler_nbr = ?";
              SELECT_PASS_COUNT = SELECT_PASS_COUNT + " and filler_nbr = ?";
            }

            SELECT_PASS_FAIL_COUNT = SELECT_PASS_FAIL_COUNT+SELECT_FAIL_COUNT+") fail, "+SELECT_PASS_COUNT+") pass";
            int i = 1;
            pstmt = conn.prepareStatement(SELECT_PASS_FAIL_COUNT);
            pstmt.setTimestamp(i++, elrActivityLogSearchDT.getProcessStartTime());
            pstmt.setTimestamp(i++, elrActivityLogSearchDT.getProcessEndTime());
            if(elrActivityLogSearchDT.getSubjectNm() != null)
              pstmt.setString(i++, elrActivityLogSearchDT.getSubjectNm());
            if(elrActivityLogSearchDT.getMsgObservationUid() != null)
              pstmt.setLong(i++, elrActivityLogSearchDT.getMsgObservationUid().longValue());
            if(elrActivityLogSearchDT.getOdsObservationUid() != null)
              pstmt.setString(i++, elrActivityLogSearchDT.getOdsObservationUid());
            if(elrActivityLogSearchDT.getReportingFacNm() != null)
              pstmt.setString(i++, elrActivityLogSearchDT.getReportingFacNm());
            if(elrActivityLogSearchDT.getFillerNbr() != null)
              pstmt.setString(i++, elrActivityLogSearchDT.getFillerNbr());
            pstmt.setTimestamp(i++, elrActivityLogSearchDT.getProcessStartTime());
            pstmt.setTimestamp(i++, elrActivityLogSearchDT.getProcessEndTime());
            if(elrActivityLogSearchDT.getSubjectNm() != null)
              pstmt.setString(i++, elrActivityLogSearchDT.getSubjectNm());
            if(elrActivityLogSearchDT.getMsgObservationUid() != null)
              pstmt.setLong(i++, elrActivityLogSearchDT.getMsgObservationUid().longValue());
            if(elrActivityLogSearchDT.getOdsObservationUid() != null)
              pstmt.setString(i++, elrActivityLogSearchDT.getOdsObservationUid());
            if(elrActivityLogSearchDT.getReportingFacNm() != null)
              pstmt.setString(i++, elrActivityLogSearchDT.getReportingFacNm());
            if(elrActivityLogSearchDT.getFillerNbr() != null)
              pstmt.setString(i++, elrActivityLogSearchDT.getFillerNbr());

            rs = pstmt.executeQuery();

            rs.next();
            for(int k = 1; k < 3; k++) {

                toReturn.add(new Integer(rs.getInt(k)));
            }//end of for

          } catch(SQLException se) {
        	  logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSSystemException("Error: SQLException while attempting to obtain elr pass/fail count \n"+se.getMessage());

          } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            releaseConnection(conn);
          }
          return toReturn;
        }

    /**
     * @methodname insertElrActivityLog
     * This is a private method. This method insert a ElrActivityLogDT record in database .
     * @param ElrActivityLogDT dt
     * @throws NEDSSSystemException
     */
    public void insertElrActivityLog(ElrActivityLogDT dt)
    throws  NEDSSSystemException
    {
        if(dt == null)
	{
	    logger.fatal("ElrActivityLogDT is null!");
	    throw new NEDSSSystemException("ElrActivityLogDT is null!");
	}

	Connection dbConnection = null;
        CallableStatement sProc = null;

        try
        {
            dbConnection = getConnection(NEDSSConstants.ODS);
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining dbConnection "+nsex.getMessage(), nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            logger.debug("About to call stored procedure");
            String sQuery  = "{call addELRActivityLog_sp(?,?,?,?,?,?,?,?,?,?)}";
	    logger.info("sQuery = " + sQuery);
            sProc = dbConnection.prepareCall(sQuery);
	    logger.debug("after prepareCall");

            if(dt.getMsgObservationUid() != null)
	        sProc.setLong(1, dt.getMsgObservationUid().longValue());
	    else
		sProc.setNull(1, java.sql.Types.INTEGER);
            sProc.setString(2, dt.getFillerNbr());
            sProc.setString(3, dt.getId());
            sProc.setString(4, dt.getOdsObservationUid());
	    sProc.setString(5, dt.getStatusCd());
	    sProc.setTimestamp(6, dt.getProcessTime());
	    sProc.setString(7, dt.getProcessCd());
	    sProc.setString(8, dt.getSubjectNm());
            sProc.setString(9, dt.getReportingFacNm());
            sProc.setString(10, dt.getDetailTxt());

            logger.debug("before execute");
            sProc.execute();
            logger.debug("after execute");


        }
        catch(SQLException se)
        {
        	logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSSystemException("Error: SQLException while obtaining database connection.\n" + se.getMessage());
        }
        catch(NEDSSSystemException nse)
        {
        	logger.fatal("NEDSSSystemException  = "+nse.getMessage(), nse);
            throw new NEDSSSystemException("Error: NEDSSSystemException while obtaining database connection.\n" + nse.getMessage());
        }
		catch(Exception e)
		{
			logger.fatal("Exception  = "+e.getMessage(), e);
		    throw new NEDSSSystemException("exception = " + e.toString());
		}
        finally
        {
            closeCallableStatement(sProc);
            releaseConnection(dbConnection);
        }
    }

      /**
     * @methodname updateElrActivityLog
     * This is a private method. This method update a ElrActivityLogDT record in database .
     * @param ElrActivityLogDT elrActivityLogDT
     * @throws NEDSSSystemException
     */
    private void updateElrActivityLog(ElrActivityLogDT dt)
      throws NEDSSSystemException
    {
        Connection dbConnection = null;
        try
        {
            dbConnection = getConnection(NEDSSConstants.ODS);
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining dbConnection "+nsex.getMessage(), nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

		int resultCount = 0;
		PreparedStatement preparedStmt = null;
        try
        {
            preparedStmt = dbConnection.prepareStatement(UPDATE);

            int i = setPreparedStatement(preparedStmt, dt);
            //primary keys values for selection

            preparedStmt.setInt(i++, dt.getElrActivityLogSeq().intValue());
            preparedStmt.setString(i++, dt.getId());

            resultCount = preparedStmt.executeUpdate();
            if ( resultCount != 1 )
            {
                throw new NEDSSSystemException ("Error: none or more than one entity updated at a time, resultCount = " + resultCount);
            }
            else
            {
                dt.setItNew(false);
                dt.setItDirty(false);
            }
        }
        catch(SQLException se)
        {
        	logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while updating\n" + se.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    /**
     * @methodname selectElrActivityLog
     * This is a private method. This method loads a ElrActivityLogDT record by message uid in database .
     * @param long message uid
     * @return Collection<Object>  a collection of  ElrActivityLogDTs
     * @throws NEDSSSystemException
     */
    private Collection<Object>  selectElrActivityLog(long uid)
        throws  NEDSSSystemException
    {

    	Connection dbConnection = null;
        try
        {
           dbConnection = getConnection(NEDSSConstants.ODS);
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " + nsex.getMessage(), nsex);
            throw new NEDSSSystemException(nsex.toString());
        }
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ElrActivityLogDT dt = new ElrActivityLogDT();

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_BY_UID);
            preparedStmt.setLong(1, uid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object> retval = new ArrayList<Object> ();
            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, dt.getClass(), retval);
            for(Iterator<Object> anIterator = retval.iterator(); anIterator.hasNext(); )
            {
                dt = (ElrActivityLogDT)anIterator.next();
                dt.setItNew(false);
                dt.setItDirty(false);
            }
            return retval;
        }
        catch(SQLException se)
        {
        	logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while selecting \n" + se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
        	logger.fatal("ResultSetUtilsException  = "+rsuex.getMessage(), rsuex);
            throw new NEDSSSystemException("Error in result set handling while populate ElrActivityLogDTs.");
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    private int setPreparedStatement(PreparedStatement preparedStmt, ElrActivityLogDT dt)
    throws SQLException
    {
        int i = 1;
        try{
	        if( dt.getMsgObservationUid()==null)
	            preparedStmt.setNull(i++, Types.BIGINT );
	        else
	            preparedStmt.setLong(i++, dt.getMsgObservationUid().longValue() );
	
	        preparedStmt.setInt(i++, i);
	
	        preparedStmt.setString(i++, dt.getFillerNbr());
	
	        preparedStmt.setString(i++, dt.getId());
	
	        preparedStmt.setString(i++, dt.getOdsObservationUid());
	
	        preparedStmt.setString(i++, dt.getStatusCd());
	
	        preparedStmt.setTimestamp(i++, dt.getProcessTime());
	
	        preparedStmt.setString(i++, dt.getProcessCd());
	
	        preparedStmt.setString(i++, dt.getSubjectNm());
	
	        preparedStmt.setString(i++, dt.getDetailTxt());
	
	        return i;
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new SQLException(ex.toString());
        }
    }


}

