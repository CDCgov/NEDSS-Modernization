/**
* Name:		referralDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               referral value object in the referral entity bean.
*               This class encapsulates all the JDBC calls made by the referralEJB
*               for a referral object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of referralEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Keith Welch  Matthew Pease
* @version	1.0
*/

package gov.cdc.nedss.act.referral.ejb.dao;

import gov.cdc.nedss.act.referral.dt.ReferralDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
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
import java.util.Iterator;



public class ReferralDAOImpl extends BMPBase
{
     //private static final String ACT_UID = "ACTIVITY_UID";

     //For logging
     static final LogUtils logger = new LogUtils(ReferralDAOImpl.class.getName());

     //private long observationUID = -1;
     public static final String INSERT_ACTIVITY = "INSERT INTO " +
     DataTables.ACTIVITY_TABLE + "(act_uid, class_cd, mood_cd) VALUES (?, ?, ?)";

     private static final String SELECT_REFERRAL_UID =
        "SELECT referral_uid FROM referral WHERE referral_uid = ?";

     private static final String INSERT_REFERRAL =
        "INSERT INTO referral (referral_uid, activity_duration_amt, "+
        "activity_duration_unit_cd, activity_from_time, activity_to_time, "+
        "add_reason_cd, add_time, add_user_id, cd, cd_desc_txt, "+
        "confidentiality_cd, confidentiality_desc_txt, effective_duration_amt,"+
        "effective_duration_unit_cd, effective_from_time, effective_to_time, "+
        "last_chg_reason_cd, last_chg_time, last_chg_user_id, reason_txt,"+
        "record_status_cd, record_status_time, referral_desc_txt, "+
        "repeat_nbr, status_cd, status_time, txt, user_affiliation_txt,"+
        "local_id, version_ctrl_nbr, program_jurisdiction_oid,shared_ind ) "+
        "VALUES (?, ?, ?, ?, ?, "+
                "?, ?, ?, ?, ?, ?,"+
                "?, ?, ?, ?, ?, ?,"+
                "?, ?, ?, ?, ?, ?,"+
                "?, ?, ?, ?, ?, ?,"+
                 "?, ?, ?)";

     private static final String UPDATE_REFERRAL =
        "UPDATE referral SET activity_duration_amt = ?, "+
        "activity_duration_unit_cd = ?, activity_from_time = ?, "+
        "activity_to_time = ?, add_reason_cd = ?, add_time = ?,"+
        "add_user_id = ?, cd = ?, cd_desc_txt = ?, "+
        "confidentiality_cd = ?, confidentiality_desc_txt = ?,"+
        "effective_duration_amt = ?, effective_duration_unit_cd = ?,"+
        "effective_from_time = ?, effective_to_time = ?, "+
        "last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?,"+
        "local_id = ?, reason_txt = ?, record_status_cd = ?, "+
        "record_status_time = ?, referral_desc_txt = ?, repeat_nbr = ?,"+
        "status_cd = ?, status_time = ?, txt = ?, user_affiliation_txt = ?,"+
        "version_ctrl_nbr = ?,program_jurisdiction_oid = ?,shared_ind = ? "+
        "WHERE referral_uid = ? AND version_ctrl_nbr = ?";

     private static final String SELECT_REFERRAL =
        "SELECT referral_uid \"referralUid\", "+
        "activity_duration_amt \"activityDurationAmt\", "+
        "activity_duration_unit_cd \"activityDurationUnitCd\", "+
        "activity_from_time \"activityFromTime\", "+
        "activity_to_time \"activityToTime\", add_reason_cd \"addReasonCd\","+
        "add_time \"addTime\", add_user_id \"addUserId\", cd \"cd\", "+
        "cd_desc_txt \"cdDescTxt\", confidentiality_cd \"confidentialityCd\", "+
        "confidentiality_desc_txt \"confidentialityDescTxt\", "+
        "effective_duration_amt \"effectiveDurationAmt\", " +
        "effective_duration_unit_cd \"effectiveDurationUnitCd\","+
        "effective_from_time \"effectiveFromTime\", "+
        "effective_to_time \"effectiveToTime\", "+
        "last_chg_reason_cd \"lastChgReasonCd\", "+
        "last_chg_time \"lastChgTime\", "+
        "last_chg_user_id \"lastChgUserId\","+
        "reason_txt \"reasonTxt\", record_status_cd \"recordStatusCd\", "+
        "record_status_time \"recordStatusTime\", "+
        "referral_desc_txt \"referralDescTxt\", "+
        "repeat_nbr \"repeatNbr\", status_cd \"statusCd\", "+
        "status_time \"statusTime\", txt \"txt\", "+
        "user_affiliation_txt \"userAffiliationTxt\","+
        "local_id \"localId\", version_ctrl_nbr \"versionControlNumber\", "+
        "program_jurisdiction_oid \"progJurisId\", "+
        "shared_ind\"sharedInd\" FROM referral WHERE referral_uid = ?";

     private static final String DELETE_REFERRAL = "";

	private long referralUID = -1;

	public ReferralDAOImpl()
		throws  NEDSSSystemException
	{

	}
  /**
   * persists the collection of passed objects into database
   * @param obj -- collection of Objects to be persisted
   * @throws NEDSSSystemException
   */
	 public long create(Object obj ) throws  NEDSSSystemException
	 {
		 try{
		 	 ReferralDT referralDT = (ReferralDT) obj;
	
		     referralUID = insertItem(referralDT);
		     referralDT.setItNew(false);
		     referralDT.setItDirty(false);
		     referralDT.setItDelete(false);
		     return referralUID;
		 }catch(Exception ex){
			 logger.fatal("Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		 }
	 }
     /**
     * Store the  updated referral object
	* @param obj -- updated object to be persisted
	* @throws NEDSSSystemException
	* @throws NEDSSConcurrentDataException
	*/
	public void store(Object obj)
		throws  NEDSSSystemException,NEDSSConcurrentDataException
	{
		try{
			updateItem( (ReferralDT) obj);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
     /**
     * Delete the referral object
	* @param referralUID -- UID for the object to be deleted
	* @throws NEDSSSystemException
	*/
	public void remove(long referralUID)
		throws  NEDSSSystemException
	{
		try{
			removeItem(referralUID);
		}catch(Exception ex){
			logger.fatal("referralUID: "+referralUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
    /**
     * Load the referral object with specified UID
	* @param referralUID -- UID for the object to be Loaded
	* @throws NEDSSSystemException
	*/
    public Object loadObject(long referralUID) throws NEDSSSystemException
    {
    	try{
	        ReferralDT referralDT = selectItem(referralUID);
	        referralDT.setItNew(false);
	        referralDT.setItDirty(false);
	        referralDT.setItDelete(false);
	        return referralDT;
    	}catch(Exception ex){
			logger.fatal("referralUID: "+referralUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
    }
    /**
     * Check if the perticular record exists in the database
	* @param referralUID -- Uid for the object to be checked for existence
	* @throws NEDSSSystemException
     */
    public Long findByPrimaryKey(long referralUID)
    	throws  NEDSSSystemException
    {
    	try{
	        if (itemExists(referralUID))
	            return (new Long(referralUID));
	        else
	            logger.error("No referral found for this primary key :" + referralUID);
	            return null;
    	}catch(Exception ex){
			logger.fatal("referralUID: "+referralUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
    }

    /**
     * Check if the perticular record exists in the database
	* @param referralUID -- Uid for the object to be checked for existence
	* @throws NEDSSSystemException
     */
    protected boolean itemExists (long referralUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_REFERRAL_UID);
            preparedStmt.setLong(1, referralUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                referralUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an"
                            + " existing referral's uid in referral table-&gt; " + referralUID, sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while checking for an"
                            + " existing referral's uid in referral table-&gt; " +
                            referralUID , ex);
            throw new NEDSSDAOSysException( ex.getMessage());
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
     * insert the new record for Referral
	* @param referralDT -- the Referral DT
	* @return localUID -- The uid for newly created Referral Object
	* @throws NEDSSSystemException
     */
    private long insertItem(ReferralDT referralDT)
                throws NEDSSSystemException
    {
        /**
         * Starts inserting a new referral
         */
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
       // ResultSet resultSet = null;
        //long referralUID = -1;
        String localUID = null;
        UidGeneratorHelper uidGen = null;
        int resultCount = 0;

            try
            {
                uidGen = new UidGeneratorHelper();
                referralUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(INSERT_ACTIVITY);

                int i = 1;
                preparedStmt.setLong(i++, referralUID);
                preparedStmt.setString(i++,  NEDSSConstants.REFERRAL_CLASS_CODE);
                preparedStmt.setString(i++, NEDSSConstants.EVENT_MOOD_CODE);
                resultCount = preparedStmt.executeUpdate();

            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while generating " +
                        "uid for REFERRAL_TABLE: \n" , sex);
                throw new NEDSSSystemException( sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into ACT_TABLE, referralUID = " + referralUID, ex);
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
                    logger.error("Error while inserting " +
                            "uid into ACT_TABLE for a new referral, resultCount = " +
                            resultCount);
                }

                 // insert into REFERRAL_TABLE

                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(INSERT_REFERRAL);
                uidGen = new UidGeneratorHelper();
                localUID = uidGen.getLocalID(UidClassCodes.REFERRAL_CLASS_CODE);


                    int i = 1;
                // Set auto generated PK field
                 preparedStmt.setLong(i++,referralUID);

                // Set all non generated fields
                      preparedStmt.setString(i++,referralDT.getActivityDurationAmt());
                     preparedStmt.setString(i++,referralDT.getActivityDurationUnitCd());
                if (referralDT.getActivityFromTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,referralDT.getActivityFromTime());
                if (referralDT.getActivityToTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,referralDT.getActivityToTime());
                     preparedStmt.setString(i++,referralDT.getAddReasonCd());
                if (referralDT.getAddTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,referralDT.getAddTime());
                if (referralDT.getAddUserId() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setLong(i++,referralDT.getAddUserId().longValue());
                     preparedStmt.setString(i++,referralDT.getCd());
                     preparedStmt.setString(i++,referralDT.getCdDescTxt());
                     preparedStmt.setString(i++,referralDT.getConfidentialityCd());
                     preparedStmt.setString(i++,referralDT.getConfidentialityDescTxt());
                     preparedStmt.setString(i++,referralDT.getEffectiveDurationAmt());
                     preparedStmt.setString(i++,referralDT.getEffectiveDurationUnitCd());
                if (referralDT.getEffectiveFromTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,referralDT.getEffectiveFromTime());
                if (referralDT.getEffectiveToTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,referralDT.getEffectiveToTime());
                     preparedStmt.setString(i++,referralDT.getLastChgReasonCd());
                if (referralDT.getLastChgTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,referralDT.getLastChgTime());
                if (referralDT.getLastChgUserId() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setLong(i++,referralDT.getLastChgUserId().longValue());
                     //preparedStmt.setString(i++,referralDT.getLoaclId());
                     //preparedStmt.setString(i++,referralDT.getOrgAccessPermis());
                     //preparedStmt.setString(i++,referralDT.getProgAreaAccessPermis());
                     preparedStmt.setString(i++,referralDT.getReasonTxt());
                     preparedStmt.setString(i++,referralDT.getRecordStatusCd());
                if (referralDT.getRecordStatusTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,referralDT.getRecordStatusTime());
                     preparedStmt.setString(i++,referralDT.getReferralDescTxt());
                if (referralDT.getRepeatNbr() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setInt(i++,referralDT.getRepeatNbr().intValue());
                preparedStmt.setString(i++,referralDT.getStatusCd());
                if (referralDT.getStatusTime() == null)
                      preparedStmt.setNull(i++,Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,referralDT.getStatusTime());
                     preparedStmt.setString(i++,referralDT.getTxt());
                     preparedStmt.setString(i++,referralDT.getUserAffiliationTxt());
                     preparedStmt.setString(i++, localUID);

                preparedStmt.setInt(i++,referralDT.getVersionCtrlNbr().intValue());
                preparedStmt.setInt(i++,referralDT.getProgramJurisdictionOid().intValue());
                preparedStmt.setString(i++,referralDT.getSharedInd());



                resultCount = preparedStmt.executeUpdate();
    logger.debug("done insert referral! referralUID = " + referralUID);

                return referralUID;
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while inserting " +
                        "referral into REFERRAL_TABLE: \n", sex);
                throw new NEDSSSystemException( sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into REFERRAL_TABLE, id = " + referralUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }
            finally
            {
              closeStatement(preparedStmt);
              releaseConnection(dbConnection);
            }
    }//end of inserting referral

    /**
     * This method will Update the Referal Object in the database
	* @param ReferalDT -the Referal Object to be updated
	* @throws NEDSSSystemException
     */
    private void updateItem(ReferralDT referralDT) throws NEDSSSystemException,NEDSSConcurrentDataException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            //Updates ReferralDT table
            if (referralDT != null)
            {

                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(UPDATE_REFERRAL);

		int i = 1;

		// first set non-PK on UPDATE statement
                  preparedStmt.setString(i++,referralDT.getActivityDurationAmt());
                 preparedStmt.setString(i++,referralDT.getActivityDurationUnitCd());
            if (referralDT.getActivityFromTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,referralDT.getActivityFromTime());
            if (referralDT.getActivityToTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,referralDT.getActivityToTime());
                 preparedStmt.setString(i++,referralDT.getAddReasonCd());
            if (referralDT.getAddTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,referralDT.getAddTime());
            if (referralDT.getAddUserId() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
            else
                 preparedStmt.setLong(i++,referralDT.getAddUserId().longValue());
                 preparedStmt.setString(i++,referralDT.getCd());
                 preparedStmt.setString(i++,referralDT.getCdDescTxt());
                 preparedStmt.setString(i++,referralDT.getConfidentialityCd());
                 preparedStmt.setString(i++,referralDT.getConfidentialityDescTxt());
                 preparedStmt.setString(i++,referralDT.getEffectiveDurationAmt());
                 preparedStmt.setString(i++,referralDT.getEffectiveDurationUnitCd());
            if (referralDT.getEffectiveFromTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,referralDT.getEffectiveFromTime());
            if (referralDT.getEffectiveToTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,referralDT.getEffectiveToTime());
                 preparedStmt.setString(i++,referralDT.getLastChgReasonCd());
            if (referralDT.getLastChgTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,referralDT.getLastChgTime());
            if (referralDT.getLastChgUserId() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
            else
                 preparedStmt.setLong(i++,referralDT.getLastChgUserId().longValue());
                 preparedStmt.setString(i++,referralDT.getLocalId());
//                 preparedStmt.setString(i++,referralDT.getOrgAccessPermis());
//                 preparedStmt.setString(i++,referralDT.getProgAreaAccessPermis());
                 preparedStmt.setString(i++,referralDT.getReasonTxt());
                 preparedStmt.setString(i++,referralDT.getRecordStatusCd());
            if (referralDT.getRecordStatusTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,referralDT.getRecordStatusTime());
                 preparedStmt.setString(i++,referralDT.getReferralDescTxt());
            if (referralDT.getRepeatNbr() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
            else
                 preparedStmt.setInt(i++,referralDT.getRepeatNbr().intValue());
            preparedStmt.setString(i++,referralDT.getStatusCd());
            if (referralDT.getStatusTime() == null)
                 preparedStmt.setNull(i++,Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,referralDT.getStatusTime());
                 preparedStmt.setString(i++,referralDT.getTxt());
                 preparedStmt.setString(i++,referralDT.getUserAffiliationTxt());


              preparedStmt.setInt(i++,referralDT.getVersionCtrlNbr().intValue());
              preparedStmt.setInt(i++,referralDT.getProgramJurisdictionOid().intValue());
              preparedStmt.setString(i++,referralDT.getSharedInd());
              // next set PK items on UPDATE's WHERE clause
             if (referralDT.getReferralUid() == null)
                 logger.error("field referralUid is null and the database tables don't allow it.");
            else
                 preparedStmt.setLong(i++,referralDT.getReferralUid().longValue());


                 preparedStmt.setInt(i++,referralDT.getVersionCtrlNbr().intValue()-1);


             resultCount = preparedStmt.executeUpdate();

             if ( resultCount != 1 )
                {
                    logger.error("Error: none or more than one referral updated at a time, " + "resultCount = " + resultCount);
                    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
                }
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while updating " +
                    "referral into REFERRAL_TABLE: \n", sex);
            throw new NEDSSSystemException( sex.toString() );
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }
    /**
     * Load the Referal object corrosponding to the passed Uid
	* @param referralUID -- The UID for the referal Object to be Loaded
	* @return referralDT -- The Loaded Object
	* @throws NEDSSSystemException
	*/
    private ReferralDT selectItem(long referralUID)
    	throws NEDSSSystemException, NEDSSSystemException
    {
        ReferralDT referralDT = new ReferralDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();


        /**
         * Selects referral from referral table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_REFERRAL);
            preparedStmt.setLong(1, referralUID);
            resultSet = preparedStmt.executeQuery();

            logger.debug("referralDT object for: referralUID = " + referralUID);

            resultSetMetaData = resultSet.getMetaData();

            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, referralDT.getClass(), pList);

            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
            {
                referralDT = (ReferralDT)anIterator.next();
                referralDT.setItNew(false);
                referralDT.setItDirty(false);
                referralDT.setItDelete(false);
            }

        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while selecting " +
                            "referral vo; id = " + referralUID, sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "referral vo; id = " + referralUID, ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

		logger.debug("returning referral DT object");
        return referralDT;
    }//end of selecting item
    /**
     * Remove the referral and Objects related to it corrosponding to the UID
	* @param referralUID -- Uid of the object to be removed
	* @throws NEDSSSystemException
	* @throws NEDSSDAOSysException
     */
    private void removeItem(long referralUID) throws NEDSSSystemException, NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Deletes referrals
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(DELETE_REFERRAL);
            preparedStmt.setLong(1, referralUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete referral from REFERRAL_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "referral; id = " + referralUID, sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }

}
