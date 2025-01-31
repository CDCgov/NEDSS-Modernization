


package gov.cdc.nedss.act.notification.ejb.dao;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
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
* Name:        NotificationDAOImpl.java
* Description:  This class is a Notification Data Access Object class
*               which provides access to a particular notification record
*               in database.Actual logic of inserting/reading/updating/deleting
*               the data in relational database tables to mirror the state of
*               NotificationEJB is implemented here.
* Copyright:    Copyright (c) 2002
* Company:      Computer Sciences Corporation
* @author:      NEDSS Development Team
* @version      NBS1.1
*/


public class NotificationDAOImpl
    extends BMPBase
{
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
    public final String INSERT_ACTIVITY = "INSERT INTO act(act_uid, class_cd, mood_cd) VALUES (?, ?, ?)";
    public final String SELECT_NOTIFICATION_UID = "SELECT * FROM notification WHERE notification_uid = ?";
    public final String DELETE_NOTIFICATION = "delete notification where notification_uid = ?";
    public final String SELECT_NOTIFICATION =
        "SELECT notification_uid \"notificationUid\", " +
         "activity_duration_amt \"activityDurationAmt\", " +
         "activity_duration_unit_cd \"activityDurationUnitCd\", " +
         "activity_from_time \"activityFromTime\", " +
         "activity_to_time \"activityToTime\", " +
         "add_reason_cd \"addReasonCd\", " + "add_time \"addTime\", " +
         "add_user_id \"addUserId\", " + "case_class_cd \"caseClassCd\", " +
         "case_condition_cd \"caseConditionCd\", " + "cd \"cd\", " +
         "cd_desc_txt \"cdDescTxt\", " +
         "confidentiality_cd \"confidentialityCd\", " +
         "confidentiality_desc_txt \"confidentialityDescTxt\", " +
         "effective_duration_amt \"effectiveDurationAmt\", " +
         "effective_duration_unit_cd \"effectiveDurationUnitCd\", " +
         "notification.effective_from_time \"effectiveFromTime\", " +
         "notification.effective_to_time \"effectiveToTime\", " +
         "last_chg_reason_cd \"lastChgReasonCd\", " +
         "last_chg_time \"lastChgTime\", " +
         "last_chg_user_id \"lastChgUserId\", " + "local_id \"localId\", " +
         "method_cd \"methodCd\", " + "method_desc_txt \"methodDescTxt\", " +
         "mmwr_week \"mmwrWeek\", " + "mmwr_year \"mmwrYear\", " +
         "nedss_version_nbr \"nedssVersionNbr\", " +
         "reason_cd \"reasonCd\", " + "reason_desc_txt \"reasonDescTxt\", " +
         "record_count \"recordCount\", " +
         "record_status_cd \"recordStatusCd\", " +
         "record_status_time \"recordStatusTime\", " +
         "repeat_nbr \"repeatNbr\", " + "rpt_sent_time \"rptSentTime\", " +
         "rpt_source_cd \"rptSourceCd\", " +
         "rpt_source_type_cd \"rptSourceTypeCd\", " +
         "notification.status_cd \"statusCd\", " + "notification.status_time \"statusTime\", " +
         "txt \"txt\", " + "user_affiliation_txt \"userAffiliationTxt\", " +
         "jurisdiction_cd \"jurisdictionCd\", " +
         "notification.prog_area_cd \"progAreaCd\", " +
         "program_jurisdiction_oid \"programJurisdictionOid\", " +
         "shared_ind \"sharedInd\", " +
         "version_ctrl_nbr \"versionCtrlNbr\", " +
         "auto_resend_ind \"autoResendInd\", " +
         "export_receiving_facility_uid \"exportReceivingFacilityUid\", " +
         "nbs_interface_uid \"nbsInterfaceUid\", " +
         "nnd_ind \"nndInd\", " +
         "vaccine_enable_ind \"vaccineEnableInd\", " +
         "lab_report_enable_ind \"labReportEnableInd\" " +
         "FROM notification, nbs_srte..condition_code cc WHERE notification.case_condition_cd = cc.condition_cd and notification.notification_uid = ?";
    public final String UPDATE_NOTIFICATION = "UPDATE notification SET " +
                                              "activity_duration_amt = ?, activity_duration_unit_cd = ?, " +
                                              "activity_from_time = ?, activity_to_time = ?, " +
                                              "add_reason_cd = ?, add_time = ?, " +
                                              "add_user_id = ?, case_class_cd = ?, " +
                                              "case_condition_cd = ?, cd = ?, " +
                                              "cd_desc_txt = ?, confidentiality_cd = ?, " +
                                              "confidentiality_desc_txt = ?, effective_duration_amt = ?, " +
                                              "effective_duration_unit_cd = ?, effective_from_time = ?, " +
                                              "effective_to_time = ?, last_chg_reason_cd = ?, " +
                                              "last_chg_time = ?, last_chg_user_id = ?, " +
                                              "local_id = ?, method_cd = ?, " +
                                              "method_desc_txt = ?, mmwr_week = ?, " +
                                              "mmwr_year = ?, nedss_version_nbr = ?, " +
                                              "reason_cd = ?, reason_desc_txt = ?, " +
                                              "record_count = ?, record_status_cd = ?, " +
                                              "record_status_time = ?, repeat_nbr = ?, " +
                                              "rpt_sent_time = ?, rpt_source_cd = ?, " +
                                              "rpt_source_type_cd = ?, status_cd = ?, " +
                                              "status_time = ?, txt = ?, " +
                                              "user_affiliation_txt = ?, jurisdiction_cd = ?, " +
                                              "prog_area_cd = ?, program_jurisdiction_oid = ?, " +
                                              "shared_ind = ?, version_ctrl_nbr = ?, " +
                                              "auto_resend_ind = ? ," +
                                              "export_receiving_facility_uid = ?, " +
                                              "nbs_interface_uid = ? " +
                                              "WHERE notification_uid = ? " +
                                              "AND version_ctrl_nbr = ?";
    public final String INSERT_NOTIFICATION = "INSERT INTO notification ( " +
                                              "notification_uid,  " +
                                              "activity_duration_amt, " +
                                              "activity_duration_unit_cd, " +
                                              "activity_from_time,  " +
                                              "activity_to_time,  " +
                                              "add_reason_cd,  " +
                                              "add_time,  " +
                                              "add_user_id,  " +
                                              "case_class_cd,  " +
                                              "case_condition_cd,  " +
                                              "cd,  " + "cd_desc_txt,  " +
                                              "confidentiality_cd,  " +
                                              "confidentiality_desc_txt,  " +
                                              "effective_duration_amt,  " +
                                              "effective_duration_unit_cd,  " +
                                              "effective_from_time,  " +
                                              "effective_to_time,  " +
                                              "last_chg_reason_cd,  " +
                                              "last_chg_time,  " +
                                              "last_chg_user_id,  " +
                                              "local_id,  " +
                                              "method_cd,  " +
                                              "method_desc_txt,  " +
                                              "mmwr_week,  " +
                                              "mmwr_year,  " +
                                              "nedss_version_nbr,  " +
                                              "reason_cd,  " +
                                              "reason_desc_txt,  " +
                                              "record_count,  " +
                                              "record_status_cd,  " +
                                              "record_status_time,  " +
                                              "repeat_nbr,  " +
                                              "rpt_sent_time,  " +
                                              "rpt_source_cd,  " +
                                              "rpt_source_type_cd, " +
                                              "status_cd,  " +
                                              "status_time,  " + "txt,  " +
                                              "user_affiliation_txt,  " +
                                              "jurisdiction_cd,  " +
                                              "prog_area_cd, " +
                                              "shared_ind, " +
                                              "version_ctrl_nbr, " +
                                              "auto_resend_ind, " +
                                              "program_jurisdiction_oid, " +
                                              "export_receiving_facility_uid, " +
                                              "nbs_interface_uid) " +
                                              "VALUES ( " +
                                              "?, ?, ?, ?, ?, " +
                                              "?, ?, ?, ?, ?, " +
                                              "?, ?, ?, ?, ?, " +
                                              "?, ?, ?, ?, ?, " +
                                              "?, ?, ?, ?, ?, " +
                                              "?, ?, ?, ?, ?, " +
                                              "?, ?, ?, ?, ?, " +
                                              "?, ?, ?, ?, ?, " +
                                              "?, ?, ?, ?, ?, " +
                                              "?, ?, ?)";

    //For logging
    static final LogUtils logger = new LogUtils(NotificationDAOImpl.class.getName());
    private static final String ACT_UID = "ACTIVITY_UID";
    private long observationUID = -1;
    private long notificationUID = -1;
    private String localUID = null;

    public NotificationDAOImpl()

    {
    }

    /**
     * @methodname create
     * This method inserts a notificationDT object into database, if notificationDT object is new.
     * After inserting the records, set all the flags of the notificationDT object to false
     * @param Object a notificationDT object
     * @return  long a positive value if successful
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    public long create(Object obj)
                throws  NEDSSSystemException
    {
    	try{
	        NotificationDT notificationDT = (NotificationDT)obj;
	        notificationUID = insertItem(notificationDT);
	        notificationDT.setItNew(false);
	        notificationDT.setItDirty(false);
	        notificationDT.setItDelete(false);
	
	        return notificationUID;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

      /**
     * @methodname store
     * This method update a notificationDT record in database.
     * @param Object a notificationDT object
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public void store(Object obj)
               throws
                      NEDSSSystemException, NEDSSConcurrentDataException
    {
    	try{
    		updateItem((NotificationDT)obj);
    	}catch(NEDSSConcurrentDataException ex){
    		logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
    		throw new NEDSSConcurrentDataException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

     /**
     * @methodname remove
     * This method remove a notificationDT record in database.
     * @param long notificationUID
     * @throws NEDSSSystemException
     */
    public void remove(long notificationUID)
                throws
                       NEDSSSystemException
    {
    	try{
    		removeItem(notificationUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

     /**
     * @methodname loadObject
     * This method load a notificationDT record in database.
     * @param long notificationUID
     * @return Object a notificationDT
     * @throws NEDSSSystemException
     */
    public Object loadObject(long notificationUID)
                      throws
                             NEDSSSystemException
    {
    	try{
	        NotificationDT notificationDT = selectItem(notificationUID);
	        notificationDT.setItNew(false);
	        notificationDT.setItDirty(false);
	        notificationDT.setItDelete(false);
	
	        return notificationDT;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

  /**
     * @methodname findByPrimaryKey
     * This method load a notificationDT record by primary key(notificationUID) in database .
     * @param long notificationUID
     * @return Long notification UID
     * @throws NEDSSSystemException
     */
    public Long findByPrimaryKey(long notificationUID)
                          throws
                                 NEDSSSystemException
    {
    	try{
	        if (itemExists(notificationUID))
	
	            return (new Long(notificationUID));
	        else
	            logger.error(
	                    "No notification found for this primary key :" +
	                    notificationUID);
	
	        return null;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * @methodname itemExists
     * This method checks a notificationDT record by primary key(notificationUID) in database .
     * @param long notificationUID
     * @return boolean returns true if there is a notification record by its primary key(notificationUID).
     * @throws NEDSSSystemException
     */
    protected boolean itemExists(long notificationUID)
                          throws
                                 NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(
                                   SELECT_NOTIFICATION_UID);
            preparedStmt.setLong(1, notificationUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                notificationUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch (SQLException sex)
        {
            logger.fatal(
                    "SQLException while checking for an" +
                    " existing notification's uid in notification table-&gt; " +
                    notificationUID, sex);
            throw new NEDSSDAOSysException(sex.getMessage());
        }
        catch (Exception ex)
        {
            logger.fatal(
                    "Exception while checking for an" +
                    " existing notification's uid in notification table-&gt; " +
                    notificationUID, ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        return returnValue;
    }

    /**
     * @methodname insertItem
     * This is a private method. This method insert a notificationDT record in database .
     * @param long notificationUID
     * @return long a positive number if inserting a record is successful.
     * @throws NEDSSSystemException
     */
    private long insertItem(NotificationDT notificationDT)
                     throws NEDSSSystemException, NEDSSSystemException
    {
        logger.info("Starts inserting a new notification--------------");

        /**
         * Starts inserting a new notification
         */
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;

        //long notificationUID = -1;
        int resultCount = 0;


            try
            {
                logger.info("INSERT_ACTIVITY = " + INSERT_ACTIVITY);
                 dbConnection = getConnection();
                 preparedStmt = dbConnection.prepareStatement(INSERT_ACTIVITY);

                UidGeneratorHelper uidGen = new UidGeneratorHelper();
                notificationUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
                logger.info("notificationUID = " + notificationUID);
                logger.info(
                        "NEDSSConstants.NOTIFICATION_CLASS_CODE = " +
                        NEDSSConstants.NOTIFICATION_CLASS_CODE);
                logger.info(
                        "NEDSSConstants.EVENT_MOOD_CODE = " +
                        NEDSSConstants.EVENT_MOOD_CODE);

                int i = 1;
                preparedStmt.setLong(i++, notificationUID);
                preparedStmt.setString(i++, NEDSSConstants.NOTIFICATION_CLASS_CODE);
                preparedStmt.setString(i++, NEDSSConstants.EVENT_MOOD_CODE);
                resultCount = preparedStmt.executeUpdate();
            }
            catch (SQLException sex)
            {
                logger.fatal(
                        "SQLException while generating " +
                        "uid for NOTIFICATION_TABLE: \n", sex);
                throw new NEDSSSystemException(sex.toString());
            }
            catch (Exception ex)
            {
                logger.fatal(
                        "Error while inserting into ACTIVITY_TABLE, notificationUID = " +
                        notificationUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }
            finally
            {
              closeStatement(preparedStmt);
              releaseConnection(dbConnection);
            }


            try
            {

                if (resultCount != 1)
                {
                    logger.error(
                            "Error while inserting " +
                            "uid into ACTIVITY_TABLE for a new notification, resultCount = " +
                            resultCount);
                }

                // Get the new local UID for this notification
                UidGeneratorHelper uidGen = new UidGeneratorHelper();
                localUID = uidGen.getLocalID(UidClassCodes.NOTITICATION_CLASS_CODE);
                logger.info("insertItem - localUID = " + localUID);
                logger.info("INSERT_NOTIFICATION = " + INSERT_NOTIFICATION);
                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(INSERT_NOTIFICATION);

                int i = 1;

                // Set auto generated PK field
                preparedStmt.setLong(i++, notificationUID); //1

                // Set all non generated fields
                preparedStmt.setString(i++,
                                       notificationDT.getActivityDurationAmt() //2
                                       );
                preparedStmt.setString(i++,
                                       notificationDT.getActivityDurationUnitCd() //3
                                       );

                if (notificationDT.getActivityFromTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP); //4
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getActivityFromTime());

                if (notificationDT.getActivityToTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP); //5
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getActivityToTime());

                preparedStmt.setString(i++, notificationDT.getAddReasonCd()); //6

                if (notificationDT.getAddTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP); //7
                else
                    preparedStmt.setTimestamp(i++, notificationDT.getAddTime());

                if (notificationDT.getAddUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER); //8
                else
                    preparedStmt.setLong(i++,
                                         notificationDT.getAddUserId().longValue());

                preparedStmt.setString(i++, notificationDT.getCaseClassCd()); //9
                preparedStmt.setString(i++, notificationDT.getCaseConditionCd()); //10
                preparedStmt.setString(i++, notificationDT.getCd()); //11
                preparedStmt.setString(i++, notificationDT.getCdDescTxt()); //12
                preparedStmt.setString(i++, notificationDT.getConfidentialityCd()); //13
                preparedStmt.setString(i++,
                                       notificationDT.getConfidentialityDescTxt() //14
                                       );
                preparedStmt.setString(i++,
                                       notificationDT.getEffectiveDurationAmt() //15
                                       );
                preparedStmt.setString(i++,
                                       notificationDT.getEffectiveDurationUnitCd() //16
                                       );

                if (notificationDT.getEffectiveFromTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP); //17
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getEffectiveFromTime());

                if (notificationDT.getEffectiveToTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP); //18
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getEffectiveToTime());

                preparedStmt.setString(i++, notificationDT.getLastChgReasonCd()); //19

                if (notificationDT.getLastChgTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP); //20
                else
                    preparedStmt.setTimestamp(i++, notificationDT.getLastChgTime());

                if (notificationDT.getLastChgUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER); //21
                else
                    preparedStmt.setLong(i++,
                                         notificationDT.getLastChgUserId().longValue());

                preparedStmt.setString(i++, localUID); //22
                preparedStmt.setString(i++, notificationDT.getMethodCd()); //23
                preparedStmt.setString(i++, notificationDT.getMethodDescTxt()); //24
                preparedStmt.setString(i++, notificationDT.getMmwrWeek()); //25
                preparedStmt.setString(i++, notificationDT.getMmwrYear()); //26
                preparedStmt.setString(i++, notificationDT.getNedssVersionNbr()); //27

                //preparedStmt.setString(i++,notificationDT.getOrgAccessPermis());//28
                //preparedStmt.setString(i++,notificationDT.getProgAreaAccessPermis());//29
                preparedStmt.setString(i++, notificationDT.getReasonCd()); //30
                preparedStmt.setString(i++, notificationDT.getReasonDescTxt()); //31
                preparedStmt.setString(i++, notificationDT.getRecordCount()); //32
                preparedStmt.setString(i++, notificationDT.getRecordStatusCd()); //33

                if (notificationDT.getRecordStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP); //34
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getRecordStatusTime());

                if (notificationDT.getRepeatNbr() == null)
                    preparedStmt.setNull(i++, Types.INTEGER); //35
                else
                    preparedStmt.setInt(i++,
                                        notificationDT.getRepeatNbr().intValue());

                if (notificationDT.getRptSentTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP); //36
                else
                    preparedStmt.setTimestamp(i++, notificationDT.getRptSentTime());

                preparedStmt.setString(i++, notificationDT.getRptSourceCd()); //37
                preparedStmt.setString(i++, notificationDT.getRptSourceTypeCd()); //38
                if (notificationDT.getStatusCd() == null)
                  preparedStmt.setString(i++, notificationDT.getStatusCd());
                else
                  preparedStmt.setString(i++, notificationDT.getStatusCd().trim());

                if (notificationDT.getStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, notificationDT.getStatusTime());

                preparedStmt.setString(i++, notificationDT.getTxt()); //41
                preparedStmt.setString(i++, notificationDT.getUserAffiliationTxt()); //42
                logger.info(
                        "notificationDT.getJurisdictionCd() = " +
                        notificationDT.getJurisdictionCd());
                preparedStmt.setString(i++, notificationDT.getJurisdictionCd()); //43
                preparedStmt.setString(i++, notificationDT.getProgAreaCd()); //44
               preparedStmt.setString(i++, notificationDT.getSharedInd());
               preparedStmt.setInt(i++, notificationDT.getVersionCtrlNbr().intValue());

               if (notificationDT.getAutoResendInd() == null)
                 preparedStmt.setString(i++, notificationDT.getAutoResendInd());
               else
                 preparedStmt.setString(i++, notificationDT.getAutoResendInd().trim());



                if (notificationDT.getProgramJurisdictionOid() == null)
                    preparedStmt.setNull(i++, Types.BIGINT);
                else
                    preparedStmt.setLong(i++,
                                         notificationDT.getProgramJurisdictionOid().longValue());
                if (notificationDT.getExportReceivingFacilityUid() == null)
                    preparedStmt.setNull(i++, Types.BIGINT);
                else
                preparedStmt.setLong(i++,
                        notificationDT.getExportReceivingFacilityUid().longValue());
                if (notificationDT.getNbsInterfaceUid() == null)
                    preparedStmt.setNull(i++, Types.BIGINT);
                else
                preparedStmt.setLong(i++,
                        notificationDT.getNbsInterfaceUid().longValue());
                resultCount = preparedStmt.executeUpdate();
                logger.debug(
                        "done insert notification! notificationUID = " +
                        notificationUID);

                return notificationUID;
            }
            catch (SQLException sex)
            {
                logger.fatal(
                        "SQLException while inserting " +
                        "notification into NOTIFICATION_TABLE: \n", sex);
                throw new NEDSSSystemException(sex.toString());
            }
            catch (Exception ex)
            {
                logger.fatal(
                        "Error while inserting into NOTIFICATION_TABLE, id = " +
                        notificationUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }
            finally
          {

            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
          }
    } //end of inserting notification

    /**
     * @methodname updateItem
     * This is a private method. This method update a notificationDT record in database .
     * @param NotificationDT notificationDT
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    private void updateItem(NotificationDT notificationDT)
                     throws NEDSSSystemException, NEDSSConcurrentDataException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {

            //Updates NotificationDT table
            if (notificationDT != null)
            {
                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(
                                       UPDATE_NOTIFICATION);

                int i = 1;

                // first set non-PK on UPDATE statement
                preparedStmt.setString(i++,
                                       notificationDT.getActivityDurationAmt() //1
                                       );
                preparedStmt.setString(i++,
                                       notificationDT.getActivityDurationUnitCd());

                if (notificationDT.getActivityFromTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getActivityFromTime());

                if (notificationDT.getActivityToTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getActivityToTime());

                preparedStmt.setString(i++, notificationDT.getAddReasonCd());

                if (notificationDT.getAddTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, notificationDT.getAddTime());

                if (notificationDT.getAddUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++,
                                         notificationDT.getAddUserId().longValue());

                preparedStmt.setString(i++, notificationDT.getCaseClassCd());
                preparedStmt.setString(i++,
                                       notificationDT.getCaseConditionCd());
                preparedStmt.setString(i++, notificationDT.getCd());
                preparedStmt.setString(i++, notificationDT.getCdDescTxt());
                preparedStmt.setString(i++,
                                       notificationDT.getConfidentialityCd());
                preparedStmt.setString(i++,
                                       notificationDT.getConfidentialityDescTxt());
                preparedStmt.setString(i++,
                                       notificationDT.getEffectiveDurationAmt());
                preparedStmt.setString(i++,
                                       notificationDT.getEffectiveDurationUnitCd());

                if (notificationDT.getEffectiveFromTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getEffectiveFromTime());

                if (notificationDT.getEffectiveToTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getEffectiveToTime());

                preparedStmt.setString(i++,
                                       notificationDT.getLastChgReasonCd());

                if (notificationDT.getLastChgTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getLastChgTime());

                if (notificationDT.getLastChgUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++,
                                         notificationDT.getLastChgUserId().longValue());

                logger.info(
                        "notificationDT.getLocalId() = " +
                        notificationDT.getLocalId());
                logger.debug("localUID: " + localUID);

                /**
                 *Commented out by Pradeep Sharma: Local id should never be set in a updated statement 
                 if (localUID != null)
                    preparedStmt.setString(i++, localUID);
                else
                */	
                    preparedStmt.setString(i++, notificationDT.getLocalId());

                preparedStmt.setString(i++, notificationDT.getMethodCd());
                preparedStmt.setString(i++, notificationDT.getMethodDescTxt());
                preparedStmt.setString(i++, notificationDT.getMmwrWeek());
                preparedStmt.setString(i++, notificationDT.getMmwrYear());
                preparedStmt.setString(i++,
                                       notificationDT.getNedssVersionNbr());
                preparedStmt.setString(i++, notificationDT.getReasonCd());
                preparedStmt.setString(i++, notificationDT.getReasonDescTxt());
                preparedStmt.setString(i++, notificationDT.getRecordCount());
                logger.debug(
                        "notificationDT.getRecordStatusCd():" +
                        notificationDT.getRecordStatusCd());
                preparedStmt.setString(i++, notificationDT.getRecordStatusCd());

                if (notificationDT.getRecordStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getRecordStatusTime());

                if (notificationDT.getRepeatNbr() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setInt(i++,
                                        notificationDT.getRepeatNbr().intValue());

                if (notificationDT.getRptSentTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getRptSentTime());

                preparedStmt.setString(i++, notificationDT.getRptSourceCd());
                preparedStmt.setString(i++,
                                       notificationDT.getRptSourceTypeCd());
                if (notificationDT.getStatusCd() == null)
                  preparedStmt.setString(i++, notificationDT.getStatusCd());
                else
                  preparedStmt.setString(i++, notificationDT.getStatusCd().trim());

                if (notificationDT.getStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++,
                                              notificationDT.getStatusTime());

                preparedStmt.setString(i++, notificationDT.getTxt());
                preparedStmt.setString(i++,
                                       notificationDT.getUserAffiliationTxt());
                preparedStmt.setString(i++, notificationDT.getJurisdictionCd());
                preparedStmt.setString(i++, notificationDT.getProgAreaCd());

                if (notificationDT.getProgramJurisdictionOid() == null)
                    preparedStmt.setNull(i++, Types.BIGINT);
                else
                    preparedStmt.setLong(i++,
                                         notificationDT.getProgramJurisdictionOid().longValue());

                preparedStmt.setString(i++, notificationDT.getSharedInd());
                logger.debug(notificationDT.getVersionCtrlNbr());
                preparedStmt.setInt(i++, notificationDT.getVersionCtrlNbr().intValue());

                if (notificationDT.getAutoResendInd() == null)
                  preparedStmt.setString(i++, notificationDT.getAutoResendInd());
                else
                  preparedStmt.setString(i++, notificationDT.getAutoResendInd().trim());
                
                if (notificationDT.getExportReceivingFacilityUid() == null)
                    preparedStmt.setNull(i++, Types.BIGINT);
                else
                    preparedStmt.setLong(i++,
                                         notificationDT.getExportReceivingFacilityUid().longValue());
                if (notificationDT.getNbsInterfaceUid() == null)
                    preparedStmt.setNull(i++, Types.BIGINT);
                else
                    preparedStmt.setLong(i++,
                                         notificationDT.getNbsInterfaceUid().longValue());

                logger.debug("notificationDT.getNotificationUid() = " + notificationDT.getNotificationUid());

                if (notificationDT.getNotificationUid() == null)
                    logger.error("field notificationUid is null and the database tables don't allow it.");
                else
                    preparedStmt.setLong(i++, notificationDT.getNotificationUid().longValue());

                logger.debug("notificationDT.getVersionCtrlNbr() = " + notificationDT.getVersionCtrlNbr());
                preparedStmt.setInt(i++, notificationDT.getVersionCtrlNbr().intValue()-1);

                resultCount = preparedStmt.executeUpdate();

                if (resultCount != 1)
                {
                    logger.error(
                            "Error: none or more than one Notification updated at a time, " +
                            "resultCount = " + resultCount);
                    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
                }
            }
        }
        catch (SQLException sex)
        {
            logger.fatal(
                    "SQLException while updating " +
                    "notification into NOTIFICATION_TABLE: \n", sex);
            throw new NEDSSSystemException(sex.toString());
        }
        catch (Exception e)
        {
        	logger.fatal("Exception  = "+e.getMessage(), e);
            throw new NEDSSSystemException(e.toString());
        }
        finally
        {

            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    /**
     * @methodname selectItem
     * This is a private method. This method load a notificationDT record by notificationUID(primary key) in database .
     * @param long notificationUID
     * @return NotificationDT a notificationDT
     * @throws NEDSSSystemException
     */
    public NotificationDT selectItem(long notificationUID)
                               throws NEDSSSystemException
    {

        NotificationDT notificationDT = new NotificationDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();

        /**
         * Selects notification from notification table
         */
        try
        {
            dbConnection = getConnection();
            String aQuery = null;
            aQuery = SELECT_NOTIFICATION;
            preparedStmt = dbConnection.prepareStatement(aQuery);
            preparedStmt.setLong(1, notificationUID);
            resultSet = preparedStmt.executeQuery();
            logger.debug(
                    "notificationDT object for: notificationUID = " +
                    notificationUID);
            resultSetMetaData = resultSet.getMetaData();
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                              resultSetMetaData,
                                                              notificationDT.getClass(),
                                                              pList);

            for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
            {
                notificationDT = (NotificationDT)anIterator.next();
                notificationDT.setItNew(false);
                notificationDT.setItDirty(false);
                notificationDT.setItDelete(false);
                logger.info(
                        "notificationDT.getLocalId() - : " +
                        notificationDT.getLocalId());
                logger.info(
                        "notificationDT.getStatusCd() - : " +
                        notificationDT.getStatusCd());
                logger.info(
                        "notificationDT.getStatusTime() - : " +
                        notificationDT.getStatusTime());
                logger.info(
                        "notificationDT.getNedssVersionNbr() - : " +
                        notificationDT.getNedssVersionNbr());
                logger.info(
                        "notificationDT.getJurisdictionCd() - : " +
                        notificationDT.getJurisdictionCd());

                //logger.info("notificationDT.getUid() - : " + notificationDT.getUid());
                logger.info(
                        "notificationDT.getRecordStatusCd() - : " +
                        notificationDT.getRecordStatusCd());
            }
        }
        catch (SQLException sex)
        {
            logger.fatal(
                    "SQLException while selecting " +
                    "notification vo; id = " + notificationUID, sex);
            throw new NEDSSSystemException(sex.getMessage());
        }
        catch (Exception ex)
        {
            logger.fatal(
                    "Exception while selecting notification vo; id = " +
                    notificationUID, ex);
            throw new NEDSSSystemException(ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        if (notificationDT == null)
        {
            logger.debug("Null notification returned");
        }
        else
        {
            logger.debug(
                    "Returning notification DT object:\n" +
                    notificationDT.toString());
        }

        return notificationDT;
    } //end of selecting item

    /**
     * @methodname removeItem
     * This is a private method. This method remove a notificationDT record by notificationUID(primary key) in database .
     * @param long notificationUID
     * @throws NEDSSSystemException
     */
    private void removeItem(long notificationUID)
                     throws NEDSSSystemException, NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;


        /**
         * Deletes notifications
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(DELETE_NOTIFICATION);
            preparedStmt.setLong(1, notificationUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error(
                        "Error: cannot delete notification from NOTIFICATION_TABLE!! resultCount = " +
                        resultCount);
            }
        }
        catch (SQLException sex)
        {
            logger.fatal(
                    "SQLException while removing " + "notification; id = " +
                    notificationUID, sex);
            throw new NEDSSSystemException(sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }
}
