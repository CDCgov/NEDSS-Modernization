package gov.cdc.nedss.act.notification.ejb.dao;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;

/**
* Name:        NotificationHistDAOImpl.java
* Description:  This class is a Notification History Data Access Object class
*               which provides access to a particular notification history record
*               in database. Actual logic of inserting/reading
*               the data in relational database tables to mirror the state of
*               NotificationEJB is implemented here.
* Copyright:    Copyright (c) 2002
* Company:      Computer Sciences Corporation
* @author:      NEDSS Development Team
* @version      NBS1.1
*/
public class NotificationHistDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils(NotificationHistDAOImpl.class.getName());
  private long notificationUid = -1;
  private short versionCtrlNbr = 0;
  public final String SELECT_NOTIFICATIONS_SEQ_NUMBER_HIST =
     "SELECT version_control_nbr from notification_hist where notification_uid = ?";

  public final String SELECT_NOTIFICATION_HIST =
     "SELECT notification_uid \"NotificationUid\", "+
     "version_ctrl_nbr \"VersionCtrlNbr\", "+
     "activity_duration_amt \"ActivityDurationAmt\", "+
     "activity_duration_unit_cd \"ActivityDurationUnitCd\", "+
     "activity_from_time \"ActivityFromTime\", "+
     "activity_to_time \"ActivityToTime\", "+
     "add_reason_cd \"AddReasonCd\", "+
     "add_time \"AddTime\", "+
     "add_user_id \"AddUserId\", "+
     "case_class_cd \"CaseClassCd\", "+
     "case_condition_cd \"CaseConditionCd\", "+
     "cd \"Cd\", "+
     "cd_desc_txt \"CdDescTxt\", "+
     "confidentiality_cd \"ConfidentialityCd, "+
     "confidentiality_desc_txt \"ConfidentialityDescTxt\", "+
     "confirmation_method_cd \"ConfirmationMethodCd\", "+
     "effective_duration_amt \"EffectiveDurationAmt\", "+
     "effective_duration_unit_cd \"EffectiveDurationUnitCd\", "+
     "effective_from_time \"EffectiveFromTime\", "+
     "effective_to_time \"EffectiveToTime\", "+
     "jurisdiction_cd \"JurisdictionCd\", "+
     "last_chg_reason_cd \"LastChgReasonCd\", "+
     "last_chg_time \"LastChgTime\", "+
     "last_chg_user_id \"LastChgUserId\", "+
     "local_id \"LocalId\", "+
     "message_txt \"MessageTxt\", "+
     "method_cd \"MethodCd\", "+
     "method_desc_txt \"MethodDescTxt\", "+
     "mmwr_week \"MmwrWeek\", "+
     "mmwr_year \"Mmwr_Year\", "+
     "nedss_version_nbr \"NedssVersionNbr\", "+
     "prog_area_cd \"ProgAreaCd\", "+
     "reason_cd \"ReasonCd\", "+
     "reason_desc_txt \"ReasonDescTxt\", "+
     "record_count \"RecordCount\", "+
     "record_status_cd \"RecordStatusCd\", "+
     "record_status_time \"RecordStatusTime\", "+
     "repeat_nbr \"RepeatNbr\", "+
     "rpt_sent_time \"RptSentTime\", "+
     "rpt_source_cd \"RptSourceCd, "+
     "rpt_source_type_cd \"RptSourceTypeCd\", "+
     "status_cd \"StatusCd\", "+
     "status_time \"StatusTime\", "+
     "txt \"Txt\", "+
     "shared_ind \"sharedInd\", "+
     "program_jurisdiction_oid \"programJurisdictionOid\", "+
     "user_affiliation_txt \"UserAffiliationTxt\", "+
     "nbs_interface_uid \"nbsInterfaceUid\" "+
     "FROM notification_hist WHERE notification_uid = ? and version_ctrl_nbr = ?";

  public final String INSERT_NOTIFICATION_HIST =
     "INSERT into notification_hist("+
     "notification_uid, "+
     "version_ctrl_nbr, "+
     "activity_duration_amt, "+
     "activity_duration_unit_cd, "+
     "activity_from_time, "+
     "activity_to_time, "+
     "add_reason_cd, "+
     "add_time, "+
     "add_user_id, "+
     "case_class_cd, "+
     "case_condition_cd, "+
     "cd, "+
     "cd_desc_txt, "+
     "confidentiality_cd, "+
     "confidentiality_desc_txt, "+
     "confirmation_method_cd, "+
     "effective_duration_amt, "+
     "effective_duration_unit_cd, "+
     "effective_from_time, "+
     "effective_to_time, "+
     "jurisdiction_cd, "+
     "last_chg_reason_cd, "+
     "last_chg_time, "+
     "last_chg_user_id, "+
     "local_id, "+
     "message_txt, "+
     "method_cd, "+
     "method_desc_txt, "+
     "mmwr_week, "+
     "mmwr_year, "+
     "nedss_version_nbr, "+
     "prog_area_cd, "+
     "reason_cd, "+
     "reason_desc_txt, "+
     "record_count, "+
     "record_status_cd, "+
     "record_status_time, "+
     "repeat_nbr, "+
     "rpt_sent_time, "+
     "rpt_source_cd, "+
     "rpt_source_type_cd, "+
     "status_cd, "+
     "status_time, "+
     "txt, "+
     "user_affiliation_txt, "+
     "shared_ind, "+
     "auto_resend_ind, " +
     "program_jurisdiction_oid, " +
     "export_receiving_facility_uid, " +
     "nbs_interface_uid) "+
     "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+
     "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


  public NotificationHistDAOImpl()  {

  }//end of constructor

  public NotificationHistDAOImpl(long notificationUid, short versionCtrlNbr)throws
  NEDSSSystemException {
    this.notificationUid = notificationUid;
    this.versionCtrlNbr = versionCtrlNbr;
    //getNextNotificationHistId();
  }//end of constructor

    /**
     * @methodname store
     * This method update a notification history record in database.
     * @param Object a notificationDT object
     * @throws NEDSSSystemException
     */
   public void store(Object obj)
      throws  NEDSSSystemException {
	   try{
	        NotificationDT dt = (NotificationDT)obj;
	        if(dt == null)
	           throw new NEDSSSystemException("Error: try to store null NotificationDT object.");
	          insertNotificationHist(dt);
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new NEDSSSystemException(ex.toString());
	   }
    }//end of store()

       /**
     * @methodname store
     * This method update a collection of notification history records in database.
     * @param Collection<Object>  collection of notification history DTs.
     * @throws NEDSSSystemException
     */
    public void store(Collection<Object> coll)
	   throws  NEDSSSystemException {
    	try{
	       Iterator<Object>  iterator = null;
	        if(coll != null)
	        {
	        	iterator = coll.iterator();
		        while(iterator.hasNext())
		        {
	                  store(iterator.next());
		        }//end of while
	        }//end of if
    	}catch(Exception ex){
 		   logger.fatal("Exception  = "+ex.getMessage(), ex);
 		   throw new NEDSSSystemException(ex.toString());
 	   }
    }//end of store(Collection)


     /**
     * @methodname load
     * This method loads a notificationDT record from database by notification uid and its version control number.
     * @param Long notificationUid
     * @param Integer versionCtrlNbr
     * @return NotificationDT a notificationDT
     * @throws NEDSSSystemException
     */
    public NotificationDT load(Long notUid, Integer versionCtrlNbr) throws NEDSSSystemException {
    	try{
	        logger.info("Starts loadObject() for a notification history...");
	        NotificationDT notDT = selectNotificationHist(notUid.longValue(),versionCtrlNbr.shortValue());
	        notDT.setItNew(false);
	        notDT.setItDirty(false);
	        logger.info("Done loadObject() for a intervention history - return: " + notDT.toString());
	        return notDT;
    	}catch(Exception ex){
  		   logger.fatal("Exception  = "+ex.getMessage(), ex);
  		   throw new NEDSSSystemException(ex.toString());
  	   	}

    }//end of load

     /**
     * @methodname getVersionCtrlNbr
     * This method get the version control number of the notification history record.
     * @return short a version control number of the notification history record.
     */
    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

   /////////////////////////////Private Class methods////////////////////////
  /**
     * @methodname selectNotificationHist
     * This method loads a NotificationDT from database by notification uid and its version control number.
     * @param Long notificationUid
     * @param short versionCtrlNbr
     * @return NotificationDT
     * @throws NEDSSSystemException
     */
  private NotificationDT selectNotificationHist(long notUid, short versionCtrlNbr)
  throws NEDSSSystemException {


        NotificationDT notDT = new NotificationDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectNotificationHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_NOTIFICATION_HIST);
            preparedStmt.setLong(1, notUid);
	           preparedStmt.setShort(2, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           logger.debug("NotificationDT object for Notification history: notificationUid = " + notUid);

            resultSetMetaData = resultSet.getMetaData();

            notDT = (NotificationDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, notDT.getClass());

            notDT.setItNew(false);
            notDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "notification history; id = " + notUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "notification vo; id = " + notUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return notificationDT for notification history");

        return notDT;


  }//end of selectNotificationHist(...)

  /**
     * @methodname insertNotificationHist
     * This method inserts a NotificationDT record to database.
     * @param NotificationDT dt
     * @throws NEDSSSystemException
     */
  private void insertNotificationHist(NotificationDT dt)throws
    NEDSSSystemException {
    if(dt.getNotificationUid() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {
              dbConnection = getConnection();
              pStmt = dbConnection.prepareStatement(INSERT_NOTIFICATION_HIST);
              pStmt.setLong(i++, dt.getNotificationUid().longValue());
              pStmt.setShort(i++, dt.getVersionCtrlNbr().shortValue());

              if(dt.getActivityDurationAmt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getActivityDurationAmt());

              if(dt.getActivityDurationUnitCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getActivityDurationUnitCd());

              if(dt.getActivityFromTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getActivityFromTime());

              if(dt.getActivityToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getActivityToTime());

              if(dt.getAddReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getAddReasonCd());

              if(dt.getAddTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getAddTime());

              if(dt.getAddUserId() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getAddUserId().longValue());

              if(dt.getCaseClassCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCaseClassCd());

              if(dt.getCaseConditionCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCaseConditionCd());

              if(dt.getCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCd());

              if(dt.getCdDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCdDescTxt());

              if(dt.getConfidentialityCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getConfidentialityCd());

              if(dt.getConfidentialityDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getConfidentialityDescTxt());

              if(dt.getConfirmationMethodCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getConfirmationMethodCd());

              if(dt.getEffectiveDurationAmt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getEffectiveDurationAmt());

              if(dt.getEffectiveDurationUnitCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getEffectiveDurationUnitCd());

              if(dt.getEffectiveFromTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getEffectiveFromTime());

              if(dt.getEffectiveToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getEffectiveToTime());

              if(dt.getJurisdictionCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getJurisdictionCd());

              if(dt.getLastChgReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLastChgReasonCd());

              if(dt.getLastChgTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getLastChgTime());

              if(dt.getLastChgUserId() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getLastChgUserId().longValue());

              if(dt.getLocalId() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLocalId());

              if(dt.getMessageTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getMessageTxt());

              if(dt.getMethodCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getMethodCd());

              if(dt.getMethodDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getMethodDescTxt());

              if(dt.getMmwrWeek() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getMmwrWeek());

              if(dt.getMmwrYear() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getMmwrYear());

              if(dt.getNedssVersionNbr() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getNedssVersionNbr());

              if(dt.getProgAreaCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getProgAreaCd());

              if(dt.getReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getReasonCd());

              if(dt.getReasonDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getReasonDescTxt());

              if(dt.getRecordCount() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRecordCount());

              if(dt.getRecordStatusCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getRecordStatusTime());

              if(dt.getRepeatNbr() == null)
                pStmt.setNull(i++, Types.SMALLINT);
              else
                pStmt.setShort(i++, dt.getRepeatNbr().shortValue());

              if(dt.getRptSentTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getRptSentTime());

              if(dt.getRptSourceCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRptSourceCd());

              if(dt.getRptSourceTypeCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRptSourceTypeCd());


                pStmt.setString(i++, dt.getStatusCd());


                pStmt.setTimestamp(i++, dt.getStatusTime());

              if(dt.getTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTxt());

              if(dt.getUserAffiliationTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getUserAffiliationTxt());

              pStmt.setString(i++, dt.getSharedInd());

              //auto-resend Ind R1.1 update
              pStmt.setString(i++, dt.getAutoResendInd());


              if(dt.getProgramJurisdictionOid() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getProgramJurisdictionOid().longValue());
              
              if(dt.getExportReceivingFacilityUid() == null)
                  pStmt.setNull(i++, Types.BIGINT);
                else
                  pStmt.setLong(i++, dt.getExportReceivingFacilityUid().longValue());

              if(dt.getNbsInterfaceUid() == null)
                  pStmt.setNull(i++, Types.BIGINT);
                else
                  pStmt.setLong(i++, dt.getNbsInterfaceUid().longValue());

              resultCount = pStmt.executeUpdate();
              if ( resultCount != 1 )
              {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(SQLException se) {
        	  logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertNotificationHist()

}//end of class
