
/**
* Name:		workupDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               workup value object in the workup entity bean.
*               This class encapsulates all the JDBC calls made by the workupEJB
*               for a workup object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of workupEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Keith Welch  Matthew Pease
* @version	1.0
*/

package gov.cdc.nedss.act.file.ejb.dao;

import gov.cdc.nedss.act.file.dt.WorkupDT;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
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




public class WorkupDAOImpl extends BMPBase
{
     //private static final String ACT_UID = "ACTIVITY_UID";

     //For logging
     static final LogUtils logger = new LogUtils(WorkupDAOImpl.class.getName());

     private long observationUID = -1;
	private static final String SELECT_WORKUP_UID = "SELECT workup_uid FROM workup WHERE workup_uid = ?";
	private static final String INSERT_WORKUP = "INSERT INTO workup (workup_uid, activity_duration_amt, activity_duration_unit_cd, activity_from_time, activity_to_time, add_reason_cd, add_time, add_user_id, assign_time, assign_worker_id, cd, cd_desc_txt, confidentiality_cd, confidentiality_desc_txt, diagnosis_cd, diagnosis_desc_txt, disposition_cd, disposition_desc_txt, disposition_time, disposition_worker_id, effective_duration_amt, effective_duration_unit_cd, effective_from_time, effective_to_time, exposure_frequency, exposure_from_time, exposure_to_time, last_chg_reason_cd, last_chg_time, last_chg_user_id, local_id, record_status_cd, record_status_time, repeat_nbr, status_cd, status_time, txt, user_affiliation_txt, program_jurisdiction_oid, shared_ind, version_ctrl_nbr) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_WORKUP = "UPDATE workup SET activity_duration_amt = ?, activity_duration_unit_cd = ?, activity_from_time = ?, activity_to_time = ?, add_reason_cd = ?, add_time = ?, add_user_id = ?, assign_time = ?, assign_worker_id = ?, cd = ?, cd_desc_txt = ?, confidentiality_cd = ?, confidentiality_desc_txt = ?, diagnosis_cd = ?, diagnosis_desc_txt = ?, disposition_cd = ?, disposition_desc_txt = ?, disposition_time = ?, disposition_worker_id = ?, effective_duration_amt = ?, effective_duration_unit_cd = ?, effective_from_time = ?, effective_to_time = ?, exposure_frequency = ?, exposure_from_time = ?, exposure_to_time = ?, last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, record_status_cd = ?, record_status_time = ?, repeat_nbr = ?, status_cd = ?, status_time = ?, txt = ?, user_affiliation_txt = ? , program_jurisdiction_oid = ?, shared_ind = ?,  version_ctrl_nbr = ? WHERE workup_uid = ?";

	private static final String SELECT_WORKUP = "SELECT workup_uid \"workupUid\", activity_duration_amt \"activityDurationAmt\", "
												+ "activity_duration_unit_cd \"activityDurationUnitCd\", "
												+ "activity_from_time \"activityFromTime\", "
												+ "activity_to_time \"activityToTime\", "
												+ "add_reason_cd \"addReasonCd\", "
												+ "add_time \"addTime\", "
												+ "add_user_id \"addUserId\", "
												+ "assign_time \"assignTime\", "
												+ "assign_worker_id \"assignWorkerId\", "
												+ "cd \"cd\", "
												+ "cd_desc_txt \"cdDescTxt\", "
												+ "confidentiality_cd \"confidentialityCd\", "
												+ "confidentiality_desc_txt \"confidentialityDescTxt\", "
												+ "diagnosis_cd \"diagnosisCd\", "
												+ "diagnosis_desc_txt \"diagnosisDescTxt\", "
												+ "disposition_cd \"dispositionCd\", "
												+ "disposition_desc_txt \"dispositionDescTxt\", "
												+ "disposition_time \"dispositionTime\", "
												+ "disposition_worker_id \"dispositionWorkerId\", "
												+ "effective_duration_amt \"effectiveDurationAmt\", "
												+ "effective_duration_unit_cd \"effectiveDurationUnitCd\", "
												+ "effective_from_time \"effectiveFromTime\", "
												+ "effective_to_time \"effectiveToTime\", "
												+ "exposure_frequency \"exposureFrequency\", "
												+ "exposure_from_time \"exposureFromTime\", "
												+ "exposure_to_time \"exposureToTime\", "
												+ "last_chg_reason_cd \"lastChgReasonCd\", "
												+ "last_chg_time \"lastChgTime\", "
												+ "last_chg_user_id \"lastChgUserId\", "
												+ "local_id \"localId\", "
												//+ "org_access_permis \"orgAccessPermis\", "
												//+ "prog_area_access_permis \"progAreaAccessPermis\", "
												+ "record_status_cd \"recordStatusCd\", "
												+ "record_status_time \"recordStatusTime\", "
												+ "repeat_nbr \"repeatNbr\", "
												+ "status_cd \"statusCd\", "
												+ "status_time \"statusTime\", "
												+ "txt \"txt\", "
												+ "user_affiliation_txt \"userAffiliationTxt\", "
												+ "program_jurisdiction_oid  \"programJurisdictionOid\", "
												+ "shared_ind \"sharedInd\", "
												+ "version_ctrl_nbr \"versionCtrlNbr\" "
												+ "FROM workup WHERE workup_uid = ?";
	private static final String DELETE_WORKUP = "";

	private long workupUID = -1;

	public WorkupDAOImpl()

	{

	}

	 public long create(Object obj ) throws  NEDSSSystemException
	 {
		 try{
			 WorkupDT workupDT = (WorkupDT) obj;

		     workupUID = insertItem(workupDT);
		     workupDT.setItNew(false);
		     workupDT.setItDirty(false);
		     workupDT.setItDelete(false);
		     return workupUID;
		 }catch(Exception ex){
			 logger.fatal("Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		 }
	 }

	public void store(Object obj)
		throws  NEDSSSystemException
	{
		try{
			updateItem( (WorkupDT) obj);
		}catch(Exception ex){
			 logger.fatal("Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		 }
	}

	public void remove(long workupUID)
		throws  NEDSSSystemException
	{
		try{
			removeItem(workupUID);
		}catch(Exception ex){
			 logger.fatal("Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		 }
	}

    public Object loadObject(long workupUID) throws
		 NEDSSSystemException
    {
    	try{
	        WorkupDT workupDT = selectItem(workupUID);
	        workupDT.setItNew(false);
	        workupDT.setItDirty(false);
	        workupDT.setItDelete(false);
	        return workupDT;
    	}catch(Exception ex){
			 logger.fatal("Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		 }
    }

    public Long findByPrimaryKey(long workupUID)
    	throws  NEDSSSystemException
    {
    	try{
	        if (itemExists(workupUID))
	            return (new Long(workupUID));
	        else
	            logger.error("No workup found for this primary key :" + workupUID);
	            return null;
    	}catch(Exception ex){
			 logger.fatal("Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		 }
    }


    protected boolean itemExists (long workupUID) throws
            NEDSSSystemException, NEDSSSystemException
    {
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        Connection dbConnection = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining dbConnection " +
                "for checking workup existence: " , nsex);
            throw new NEDSSSystemException( nsex.getMessage() );
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(this.SELECT_WORKUP_UID);
            preparedStmt.setLong(1, workupUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                workupUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an"
                            + " existing workup's uid in workup table-&gt; " + workupUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while checking for an"
                            + " existing workup's uid in workup table-&gt; " +
                            workupUID, ex);
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

    private long insertItem(WorkupDT workupDT)
                throws NEDSSSystemException, NEDSSSystemException
    {
        /**
         * Starts inserting a new workup
         */
        Connection dbConnection =null;
        PreparedStatement preparedStmt = null;
        String localUID = null;
        UidGeneratorHelper uidGen = null;
        int resultCount = 0;

        try
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining db connection " +
                    "while inserting into Entity table", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                uidGen = new UidGeneratorHelper();
                workupUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
                preparedStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_ACTIVITY);

                int i = 1;
                preparedStmt.setLong(i++, workupUID);
                preparedStmt.setString(i++,  NEDSSConstants.WORKUP_CLASS_CODE);
                preparedStmt.setString(i++, NEDSSConstants.EVENT_MOOD_CODE);
                resultCount = preparedStmt.executeUpdate();

                // close statement before reuse
                closeStatement(preparedStmt);
                preparedStmt = null;
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while generating " +
                        "uid for WORKUP_TABLE: \n", sex);
                throw new NEDSSSystemException( sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into ACT_TABLE, workupUID = " + workupUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }

            try
            {
                if (resultCount != 1)
                {
                    logger.error("Error while inserting " +
                            "uid into ACTIVITY_TABLE for a new workup, resultCount = " +
                            resultCount);
                }

                 // insert into WORKUP_TABLE

                preparedStmt = dbConnection.prepareStatement(this.INSERT_WORKUP);
                uidGen = new UidGeneratorHelper();
                localUID = uidGen.getLocalID(UidClassCodes.WORKUP_CLASS_CODE);


                    int i = 1;
                // Set auto generated PK field
                 preparedStmt.setLong(i++,workupUID);

                // Set all non generated fields
                      preparedStmt.setString(i++,workupDT.getActivityDurationAmt());
                     preparedStmt.setString(i++,workupDT.getActivityDurationUnitCd());
                if (workupDT.getActivityFromTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,workupDT.getActivityFromTime());
                if (workupDT.getActivityToTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,workupDT.getActivityToTime());
                     preparedStmt.setString(i++,workupDT.getAddReasonCd());
                if (workupDT.getAddTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,workupDT.getAddTime());
                if (workupDT.getAddUserId() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setLong(i++,workupDT.getAddUserId().longValue());
                if (workupDT.getAssignTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,workupDT.getAssignTime());
                if (workupDT.getAssignWorkerId() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setLong(i++,workupDT.getAssignWorkerId().longValue());
                     preparedStmt.setString(i++,workupDT.getCd());
                     preparedStmt.setString(i++,workupDT.getCdDescTxt());
                     preparedStmt.setString(i++,workupDT.getConfidentialityCd());
                     preparedStmt.setString(i++,workupDT.getConfidentialityDescTxt());
                     preparedStmt.setString(i++,workupDT.getDiagnosisCd());
                     preparedStmt.setString(i++,workupDT.getDiagnosisDescTxt());
                     preparedStmt.setString(i++,workupDT.getDispositionCd());
                     preparedStmt.setString(i++,workupDT.getDispositionDescTxt());
                if (workupDT.getDispositionTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,workupDT.getDispositionTime());
                     preparedStmt.setLong(i++,workupDT.getDispositionWorkerId().longValue());
                     preparedStmt.setString(i++,workupDT.getEffectiveDurationAmt());
                     preparedStmt.setString(i++,workupDT.getEffectiveDurationUnitCd());
                if (workupDT.getEffectiveFromTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,workupDT.getEffectiveFromTime());
                if (workupDT.getEffectiveToTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,workupDT.getEffectiveToTime());
                     preparedStmt.setString(i++,workupDT.getExposureFrequency());
                if (workupDT.getExposureFromTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,workupDT.getExposureFromTime());
                if (workupDT.getExposureToTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,workupDT.getExposureToTime());
                     preparedStmt.setString(i++,workupDT.getLastChgReasonCd());
                if (workupDT.getLastChgTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,workupDT.getLastChgTime());
                if (workupDT.getLastChgUserId() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setLong(i++,workupDT.getLastChgUserId().longValue());
                     preparedStmt.setString(i++,localUID);
                     //preparedStmt.setString(i++,workupDT.getOrgAccessPermis());
                     //preparedStmt.setString(i++,workupDT.getProgAreaAccessPermis());
                     preparedStmt.setString(i++,workupDT.getRecordStatusCd());
                if (workupDT.getRecordStatusTime() == null)
                                preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                     preparedStmt.setTimestamp(i++,workupDT.getRecordStatusTime());
                if (workupDT.getRepeatNbr() == null)
                                preparedStmt.setNull(i++, Types.INTEGER);
                else
                     preparedStmt.setInt(i++,workupDT.getRepeatNbr().intValue());
                if (workupDT.getStatusCd() == null)
                     logger.error("field statusCd is null and the database tables don't allow it.");
                else
                     preparedStmt.setString(i++,workupDT.getStatusCd());
                if (workupDT.getStatusTime() == null)
                     logger.error("field statusTime is null and the database tables don't allow it.");
                else
                     preparedStmt.setTimestamp(i++,workupDT.getStatusTime());
                     preparedStmt.setString(i++,workupDT.getTxt());
                     preparedStmt.setString(i++,workupDT.getUserAffiliationTxt());
                                    preparedStmt.setLong(i++, workupDT.getProgramJurisdictionOid().longValue());
                                    preparedStmt.setString(i++, workupDT.getSharedInd());
                                    preparedStmt.setInt(i++, workupDT.getVersionCtrlNbr().intValue());



                resultCount = preparedStmt.executeUpdate();
    logger.debug("done insert workup! workupUID = " + workupUID);

                return workupUID;
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while inserting " +
                        "workup into WORKUP_TABLE: \n", sex);
                throw new NEDSSSystemException( sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into WORKUP_TABLE, id = " + workupUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }//end of inserting workup


    private void updateItem(WorkupDT workupDT) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining db connection " +
                "while updating workup table", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            //Updates WorkupDT table
            if (workupDT != null)
            {

                preparedStmt = dbConnection.prepareStatement(this.UPDATE_WORKUP);

		int i = 1;

		// first set non-PK on UPDATE statement
                  preparedStmt.setString(i++,workupDT.getActivityDurationAmt());
                 preparedStmt.setString(i++,workupDT.getActivityDurationUnitCd());
            if (workupDT.getActivityFromTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,workupDT.getActivityFromTime());
            if (workupDT.getActivityToTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,workupDT.getActivityToTime());
                 preparedStmt.setString(i++,workupDT.getAddReasonCd());
            if (workupDT.getAddTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,workupDT.getAddTime());
            if (workupDT.getAddUserId() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
            else
                 preparedStmt.setLong(i++,workupDT.getAddUserId().longValue());
            if (workupDT.getAssignTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,workupDT.getAssignTime());
            if (workupDT.getAssignWorkerId() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
            else
                 preparedStmt.setLong(i++,workupDT.getAssignWorkerId().longValue());
                 preparedStmt.setString(i++,workupDT.getCd());
                 preparedStmt.setString(i++,workupDT.getCdDescTxt());
                 preparedStmt.setString(i++,workupDT.getConfidentialityCd());
                 preparedStmt.setString(i++,workupDT.getConfidentialityDescTxt());
                 preparedStmt.setString(i++,workupDT.getDiagnosisCd());
                 preparedStmt.setString(i++,workupDT.getDiagnosisDescTxt());
                 preparedStmt.setString(i++,workupDT.getDispositionCd());
                 preparedStmt.setString(i++,workupDT.getDispositionDescTxt());
            if (workupDT.getDispositionTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,workupDT.getDispositionTime());
                 preparedStmt.setLong(i++,workupDT.getDispositionWorkerId().longValue());
                 preparedStmt.setString(i++,workupDT.getEffectiveDurationAmt());
                 preparedStmt.setString(i++,workupDT.getEffectiveDurationUnitCd());
            if (workupDT.getEffectiveFromTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,workupDT.getEffectiveFromTime());
            if (workupDT.getEffectiveToTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,workupDT.getEffectiveToTime());
                 preparedStmt.setString(i++,workupDT.getExposureFrequency());
            if (workupDT.getExposureFromTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,workupDT.getExposureFromTime());
            if (workupDT.getExposureToTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,workupDT.getExposureToTime());
                 preparedStmt.setString(i++,workupDT.getLastChgReasonCd());
            if (workupDT.getLastChgTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,workupDT.getLastChgTime());
            if (workupDT.getLastChgUserId() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
            else
                 preparedStmt.setLong(i++,workupDT.getLastChgUserId().longValue());
                 //preparedStmt.setString(i++,workupDT.getLocalId());
                 //preparedStmt.setString(i++,workupDT.getOrgAccessPermis());
                 //preparedStmt.setString(i++,workupDT.getProgAreaAccessPermis());
                 preparedStmt.setString(i++,workupDT.getRecordStatusCd());
            if (workupDT.getRecordStatusTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                 preparedStmt.setTimestamp(i++,workupDT.getRecordStatusTime());
            if (workupDT.getRepeatNbr() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
            else
                 preparedStmt.setInt(i++,workupDT.getRepeatNbr().intValue());
            if (workupDT.getStatusCd() == null)
                 logger.error("field statusCd is null and the database tables don't allow it.");
            else
                 preparedStmt.setString(i++,workupDT.getStatusCd());
            if (workupDT.getStatusTime() == null)
                 logger.error("field statusTime is null and the database tables don't allow it.");
            else
                 preparedStmt.setTimestamp(i++,workupDT.getStatusTime());
                 preparedStmt.setString(i++,workupDT.getTxt());
                 preparedStmt.setString(i++,workupDT.getUserAffiliationTxt());
				preparedStmt.setLong(i++, workupDT.getProgramJurisdictionOid().longValue());
				preparedStmt.setString(i++, workupDT.getSharedInd());
				preparedStmt.setInt(i++, workupDT.getVersionCtrlNbr().intValue());


             // next set PK items on UPDATE's WHERE clause
             if (workupDT.getWorkupUid() == null)
                 logger.error("field workupUid is null and the database tables don't allow it.");
            else
                 preparedStmt.setLong(i++,workupDT.getWorkupUid().longValue());


             resultCount = preparedStmt.executeUpdate();

             if ( resultCount != 1 )
                {
                    logger.error("Error: none or more than one workup updated at a time, " + "resultCount = " + resultCount);
                }
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while updating " +
                    "workup into WORKUP_TABLE: \n", sex);
            throw new NEDSSSystemException( sex.toString() );
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    private WorkupDT selectItem(long workupUID)
    	throws NEDSSSystemException, NEDSSSystemException
    {
        WorkupDT workupDT = new WorkupDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " + "for selectworkup " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects workup from workup table
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(this.SELECT_WORKUP);
            preparedStmt.setLong(1, workupUID);
            resultSet = preparedStmt.executeQuery();

logger.debug("workupDT object for: workupUID = " + workupUID);

            resultSetMetaData = resultSet.getMetaData();

            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, workupDT.getClass(), pList);

            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
            {
                workupDT = (WorkupDT)anIterator.next();
                workupDT.setItNew(false);
                workupDT.setItDirty(false);
                workupDT.setItDelete(false);
            }

        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "workup vo; id = " + workupUID, ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

		logger.debug("returning workup DT object");
        return workupDT;
    }//end of selecting item

    private void removeItem(long workupUID) throws NEDSSSystemException, NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for deleting workup " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Deletes workups
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(this.DELETE_WORKUP);
            preparedStmt.setLong(1, workupUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete workup from WORKUP_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "workup; id = " + workupUID, sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }

}
