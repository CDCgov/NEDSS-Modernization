package gov.cdc.nedss.act.patientencounter.ejb.dao;

import gov.cdc.nedss.act.patientencounter.dt.PatientEncounterDT;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
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


/**
* Name:		patient_encounterDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               patient_encounter value object in the patient_encounter entity bean.
*               This class encapsulates all the JDBC calls made by the patient_encounterEJB
*               for a patient_encounter object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of patient_encounterEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Keith Welch  Matthew Pease
* @version	1.0
*/

public class PatientEncounterDAOImpl
    extends BMPBase {

    // private static final String ACT_UID = "ACTIVITY_UID";
    //For logging
    static final LogUtils logger = new LogUtils(PatientEncounterDAOImpl.class.getName());
   // private long observationUID = -1;
    private static final String SELECT_PATIENTENCOUNTER_UID =
        "SELECT patient_encounter_uid  " + "FROM patient_encounter WHERE " + " patient_encounter_uid = ?";
    private static final String INSERT_PATIENTENCOUNTER =
        "INSERT INTO patient_encounter" + "(patient_encounter_uid, activity_duration_amt, activity_duration_unit_cd, " + "activity_from_time, activity_to_time, acuity_level_cd, acuity_level_desc_txt," + "add_reason_cd, add_time, add_user_id, admission_source_cd, " + "admission_source_desc_txt, birth_encounter_ind, cd, cd_desc_txt, " + " confidentiality_cd, confidentiality_desc_txt, effective_duration_amt, " + "effective_duration_unit_cd, effective_from_time, effective_to_time, " + "last_chg_reason_cd, last_chg_time, last_chg_user_id, local_id, priority_cd," + "priority_desc_txt, record_status_cd, record_status_time, referral_source_cd, " + "referral_source_desc_txt, repeat_nbr, status_cd,  " + " status_time, txt, user_affiliation_txt,version_ctrl_nbr," + "program_jurisdiction_oid, shared_ind)" + " VALUES (?, ?, ?, ?, ?, ?," + "?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?," + "?, ?, ?)";
    private static final String UPDATE_PATIENTENCOUNTER =
        "UPDATE patient_encounter SET activity_duration_amt = ?, " + "activity_duration_unit_cd = ?, activity_from_time = ?, activity_to_time = ?, " + "acuity_level_cd = ?, acuity_level_desc_txt = ?, add_reason_cd = ?, add_time = ?, " + "add_user_id = ?, admission_source_cd = ?, admission_source_desc_txt = ?, " + "birth_encounter_ind = ?, cd = ?, cd_desc_txt = ?, confidentiality_cd = ?," + "confidentiality_desc_txt = ?, effective_duration_amt = ?, " + "effective_duration_unit_cd = ?, effective_from_time = ?, effective_to_time = ?, " + "last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, " + "org_access_permis = ?, priority_cd = ?, priority_desc_txt = ?, " + "record_status_time = ?, referral_source_cd = ?, referral_source_desc_txt = ?," + "repeat_nbr = ?, status_cd = ?" + "status_time = ?, txt = ?, user_affiliation_txt = ?, version_ctrl_nbr = ?," + "program_jurisdiction_oid= ?, shared_ind=?  WHERE patient_encounter_uid = ?" + " and version_ctrl_nbr = ? ";
    private static final String SELECT_PATIENTENCOUNTER =
        "SELECT patient_encounter_uid \"patientEncounterUid\", " + "activity_duration_amt \"activityDurationAmt\", activity_duration_unit_cd\"activityDurationUnitCd\", " + "activity_from_time \"activityFromTime\", " + "activity_to_time \"activityToTime\", acuity_level_cd \"acuityLevelCd\"," + "acuity_level_desc_txt \"acuityLevelDescTxt\",add_reason_cd \"addReasonCd\"," + "add_time \"addTime\", add_user_id \"addUserId\",admission_source_cd \"admissionSourceCd\"," + "admission_source_desc_txt \"admissionSourceDescTxt\",birth_encounter_ind \"birthEncounterInd\", " + "cd \"cd\", cd_desc_txt \"cdDescTxt\", " + "confidentiality_cd \"confidentialityCd\", confidentiality_desc_txt \"confidentialityDescTxt\", " + "effective_duration_amt \"effectiveDurationAmt\", effective_duration_unit_cd \"effectiveDurationUnitCd\"," + "effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\"," + "last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\"," + "last_chg_user_id \"lastChgUserId\",local_id \"localId\"," + "version_ctrl_nbr \"versionControlNumber\", priority_cd \"priorityCd\"," + "priority_desc_txt \"priorityDescTxt\", program_jurisdiction_oid \"programJurisOid\"," + "shared_ind \"sharedInd\",record_status_cd \"recordStatusCd\"," + "record_status_time \"recordStatusTime\", referral_source_cd \"referralSourceCd\"," + "referral_source_desc_txt \"referralSourceDescTxt\", repeat_nbr \"repeatNbr\"," + "status_cd \"statusCd\", " + "status_time \"statusTime\"," + "txt \"txt\", user_affiliation_txt \"userAffiliationTxt\"" + "FROM patient_encounter WHERE patient_encounter_uid = ?";
    private static final String DELETE_PATIENTENCOUNTER = "";
    private long patientencounterUID = -1;
    public static final String SELECT_PATIENT_ENCOUNTER_SEQ_NUMBER_HIST = "SELECT patient_encounter_hist_seq from patient_encounter_hist where patient_encounter_uid = ?";

    public PatientEncounterDAOImpl() {
    }

    /**
     *
     * @param obj
     * @return long patientencounterUID
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public long create(Object obj)
                throws NEDSSSystemException {

    	try{
	        PatientEncounterDT patientEncounterDT = (PatientEncounterDT)obj;
	        patientencounterUID = insertItem(patientEncounterDT);
	        patientEncounterDT.setItNew(false);
	        patientEncounterDT.setItDirty(false);
	        patientEncounterDT.setItDelete(false);
	
	        return patientencounterUID;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     *
     * @param obj
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public void store(Object obj)
               throws NEDSSSystemException, NEDSSConcurrentDataException {
    	try{
    		updateItem((PatientEncounterDT)obj);
    	}catch(NEDSSConcurrentDataException ex){
    		logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
    		throw new NEDSSConcurrentDataException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     *
     * @param patientencounterUID
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public void remove(long patientencounterUID)
                throws NEDSSSystemException {
    	try{
    		removeItem(patientencounterUID);
    	}catch(Exception ex){
    		logger.fatal("patientencounterUID: "+patientencounterUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     *
     * @param patientencounterUID
     * @return Object patientEncounterDT
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public Object loadObject(long patientencounterUID)
                      throws NEDSSSystemException {

    	try{
	        PatientEncounterDT patientEncounterDT = selectItem(patientencounterUID);
	        patientEncounterDT.setItNew(false);
	        patientEncounterDT.setItDirty(false);
	        patientEncounterDT.setItDelete(false);
	
	        return patientEncounterDT;
    	}catch(Exception ex){
    		logger.fatal("patientencounterUID: "+patientencounterUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     *
     * @param patientencounterUID
     * @return Long patientencounterUID
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public Long findByPrimaryKey(long patientencounterUID)
                          throws NEDSSSystemException {
    	try{
	        if (itemExists(patientencounterUID))
	
	            return (new Long(patientencounterUID));
	        else
	            logger.error("No patientencounter found for this primary key :" + patientencounterUID);
	
	        return null;
    	}catch(Exception ex){
    		logger.fatal("patientencounterUID: "+patientencounterUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     *
     * @param patientencounterUID
     * @return boolean
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    protected boolean itemExists(long patientencounterUID)
                          throws NEDSSSystemException, NEDSSSystemException {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_PATIENTENCOUNTER_UID);
            preparedStmt.setLong(1, patientencounterUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next()) {
                returnValue = false;
            } else {
                patientencounterUID = resultSet.getLong(1);
                returnValue = true;
            }
        } catch (SQLException sex) {
            logger.fatal("SQLException while checking for an" + " existing patientencounter's uid in patientencounter table-&gt; " + patientencounterUID, sex);
            throw new NEDSSDAOSysException(sex.getMessage());
        }
         catch (Exception ex) {
            logger.fatal("Exception while checking for an" + " existing patientencounter's uid in patientencounter table-&gt; " + patientencounterUID, ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        return returnValue;
    }

    /**
     *
     * @param patientEncounterDT
     * @return long
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private long insertItem(PatientEncounterDT patientEncounterDT)
                     throws NEDSSSystemException, NEDSSSystemException {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;

        //ResultSet resultSet = null;
        //long patientencounterUID = -1;
        String localId = null;
        UidGeneratorHelper uidGen = null;
        int resultCount = 0;

        try {
            uidGen = new UidGeneratorHelper();
            patientencounterUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE)
                  .longValue();
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_ACTIVITY);

            int i = 1;
            preparedStmt.setLong(i++, patientencounterUID);
            preparedStmt.setString(i++, NEDSSConstants.PATIENT_ENCOUNTER_CLASS_CODE);
            preparedStmt.setString(i++, NEDSSConstants.EVENT_MOOD_CODE);
            resultCount = preparedStmt.executeUpdate();
        } catch (SQLException sex) {
            logger.fatal("SQLException while generating " + "uid for PATIENTENCOUNTER_TABLE: \n", sex);
            throw new NEDSSSystemException(sex.toString());
        }
         catch (Exception ex) {
            logger.fatal("Error while inserting into ACTIVITY_TABLE, id = " + patientencounterUID, ex);
            throw new NEDSSSystemException(ex.toString());
        } finally {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        try {

            if (resultCount != 1) {
                logger.error("Error while inserting " + "uid into ACTIVITY_TABLE for a new PATIENT ENCOUNTER, resultCount = " + resultCount);
            }

            // insert into PATIENTENCOUNTER_TABLE
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(INSERT_PATIENTENCOUNTER);
            uidGen = new UidGeneratorHelper();
            localId = uidGen.getLocalID(UidClassCodes.PATIENT_ENCOUNTER_CLASS_CODE);

            int i = 1;

            // Set auto generated PK field
            preparedStmt.setLong(i++, patientencounterUID);

            // Set all non generated fields
            preparedStmt.setString(i++, patientEncounterDT.getActivityDurationAmt());
            preparedStmt.setString(i++, patientEncounterDT.getActivityDurationUnitCd());

            if (patientEncounterDT.getActivityFromTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, patientEncounterDT.getActivityFromTime());

            if (patientEncounterDT.getActivityToTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, patientEncounterDT.getActivityToTime());

            preparedStmt.setString(i++, patientEncounterDT.getAcuityLevelCd());
            preparedStmt.setString(i++, patientEncounterDT.getAcuityLevelDescTxt());
            preparedStmt.setString(i++, patientEncounterDT.getAddReasonCd());

            if (patientEncounterDT.getAddTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, patientEncounterDT.getAddTime());

            if (patientEncounterDT.getAddUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, patientEncounterDT.getAddUserId().longValue());

            preparedStmt.setString(i++, patientEncounterDT.getAdmissionSourceCd());
            preparedStmt.setString(i++, patientEncounterDT.getAdmissionSourceDescTxt());
            preparedStmt.setString(i++, patientEncounterDT.getBirthEncounterInd());
            preparedStmt.setString(i++, patientEncounterDT.getCd());
            preparedStmt.setString(i++, patientEncounterDT.getCdDescTxt());
            preparedStmt.setString(i++, patientEncounterDT.getConfidentialityCd());
            preparedStmt.setString(i++, patientEncounterDT.getConfidentialityDescTxt());
            preparedStmt.setString(i++, patientEncounterDT.getEffectiveDurationAmt());
            preparedStmt.setString(i++, patientEncounterDT.getEffectiveDurationUnitCd());

            if (patientEncounterDT.getEffectiveFromTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, patientEncounterDT.getEffectiveFromTime());

            if (patientEncounterDT.getEffectiveToTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, patientEncounterDT.getEffectiveToTime());

            preparedStmt.setString(i++, patientEncounterDT.getLastChgReasonCd());

            if (patientEncounterDT.getLastChgTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, patientEncounterDT.getLastChgTime());

            if (patientEncounterDT.getLastChgUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, patientEncounterDT.getLastChgUserId().longValue());

            preparedStmt.setString(i++, localId);

            //        preparedStmt.setString(i++,patientEncounterDT.getOrgAccessPermis());
            preparedStmt.setString(i++, patientEncounterDT.getPriorityCd());
            preparedStmt.setString(i++, patientEncounterDT.getPriorityDescTxt());

            //        preparedStmt.setString(i++,patientEncounterDT.getProgAreaAccessPermis());
            preparedStmt.setString(i++, patientEncounterDT.getRecordStatusCd());

            if (patientEncounterDT.getRecordStatusTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, patientEncounterDT.getRecordStatusTime());

            preparedStmt.setString(i++, patientEncounterDT.getReferralSourceCd());
            preparedStmt.setString(i++, patientEncounterDT.getReferralSourceDescTxt());

            if (patientEncounterDT.getRepeatNbr() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setInt(i++, patientEncounterDT.getRepeatNbr().intValue());

            preparedStmt.setString(i++, patientEncounterDT.getStatusCd());

            //                 preparedStmt.setString(i++,patientEncounterDT.getStatusReasonCd());
            //                 preparedStmt.setString(i++,patientEncounterDT.getStatusReasonDescTxt());
            if (patientEncounterDT.getStatusTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, patientEncounterDT.getStatusTime());

            preparedStmt.setString(i++, patientEncounterDT.getTxt());
            preparedStmt.setString(i++, patientEncounterDT.getUserAffiliationTxt());
            preparedStmt.setInt(i++, patientEncounterDT.getVersionCtrlNbr().intValue());
            preparedStmt.setInt(i++, patientEncounterDT.getProgramJurisdictionOid().intValue());
            preparedStmt.setString(i++, patientEncounterDT.getSharedInd());
            resultCount = preparedStmt.executeUpdate();
            logger.debug("done insert patientencounter! patientencounterUID = " + patientencounterUID);

            return patientencounterUID;
        } catch (SQLException sex) {
            logger.fatal("SQLException while inserting " + "patientencounter into PATIENTENCOUNTER_TABLE: \n", sex);
            throw new NEDSSSystemException(sex.toString());
        }
         catch (Exception ex) {
            logger.fatal("Error while inserting into PATIENTENCOUNTER_TABLE, id = " + patientencounterUID, ex);
            throw new NEDSSSystemException(ex.toString());
        } finally {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    } //end of inserting patientencounter

    /**
     *
     * @param patientEncounterDT
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    private void updateItem(PatientEncounterDT patientEncounterDT)
                     throws NEDSSSystemException, NEDSSConcurrentDataException {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try {

            //Updates PatientEncounterDT table
            if (patientEncounterDT != null) {
                dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(UPDATE_PATIENTENCOUNTER);

                int i = 1;

                // first set non-PK on UPDATE statement
                preparedStmt.setString(i++, patientEncounterDT.getActivityDurationAmt());
                preparedStmt.setString(i++, patientEncounterDT.getActivityDurationUnitCd());

                if (patientEncounterDT.getActivityFromTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, patientEncounterDT.getActivityFromTime());

                if (patientEncounterDT.getActivityToTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, patientEncounterDT.getActivityToTime());

                preparedStmt.setString(i++, patientEncounterDT.getAcuityLevelCd());
                preparedStmt.setString(i++, patientEncounterDT.getAcuityLevelDescTxt());
                preparedStmt.setString(i++, patientEncounterDT.getAddReasonCd());

                if (patientEncounterDT.getAddTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, patientEncounterDT.getAddTime());

                if (patientEncounterDT.getAddUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, patientEncounterDT.getAddUserId().longValue());

                preparedStmt.setString(i++, patientEncounterDT.getAdmissionSourceCd());
                preparedStmt.setString(i++, patientEncounterDT.getAdmissionSourceDescTxt());
                preparedStmt.setString(i++, patientEncounterDT.getBirthEncounterInd());
                preparedStmt.setString(i++, patientEncounterDT.getCd());
                preparedStmt.setString(i++, patientEncounterDT.getCdDescTxt());
                preparedStmt.setString(i++, patientEncounterDT.getConfidentialityCd());
                preparedStmt.setString(i++, patientEncounterDT.getConfidentialityDescTxt());
                preparedStmt.setString(i++, patientEncounterDT.getEffectiveDurationAmt());
                preparedStmt.setString(i++, patientEncounterDT.getEffectiveDurationUnitCd());

                if (patientEncounterDT.getEffectiveFromTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, patientEncounterDT.getEffectiveFromTime());

                if (patientEncounterDT.getEffectiveToTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, patientEncounterDT.getEffectiveToTime());

                preparedStmt.setString(i++, patientEncounterDT.getLastChgReasonCd());

                if (patientEncounterDT.getLastChgTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, patientEncounterDT.getLastChgTime());

                if (patientEncounterDT.getLastChgUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, patientEncounterDT.getLastChgUserId().longValue());

                //preparedStmt.setString(i++,patientEncounterDT.getLocalId());
                //                 preparedStmt.setString(i++,patientEncounterDT.getOrgAccessPermis());
                preparedStmt.setString(i++, patientEncounterDT.getPriorityCd());
                preparedStmt.setString(i++, patientEncounterDT.getPriorityDescTxt());

                //                 preparedStmt.setString(i++,patientEncounterDT.getProgAreaAccessPermis());
                preparedStmt.setString(i++, patientEncounterDT.getRecordStatusCd());

                if (patientEncounterDT.getRecordStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, patientEncounterDT.getRecordStatusTime());

                preparedStmt.setString(i++, patientEncounterDT.getReferralSourceCd());
                preparedStmt.setString(i++, patientEncounterDT.getReferralSourceDescTxt());

                if (patientEncounterDT.getRepeatNbr() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setInt(i++, patientEncounterDT.getRepeatNbr().intValue());

                preparedStmt.setString(i++, patientEncounterDT.getStatusCd());

                //                 preparedStmt.setString(i++,patientEncounterDT.getStatusReasonCd());
                //                 preparedStmt.setString(i++,patientEncounterDT.getStatusReasonDescTxt());
                if (patientEncounterDT.getStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, patientEncounterDT.getStatusTime());

                preparedStmt.setString(i++, patientEncounterDT.getTxt());
                preparedStmt.setString(i++, patientEncounterDT.getUserAffiliationTxt());
                preparedStmt.setInt(i++, patientEncounterDT.getVersionCtrlNbr().intValue());
                preparedStmt.setInt(i++, patientEncounterDT.getProgramJurisdictionOid().intValue());
                preparedStmt.setString(i++, patientEncounterDT.getSharedInd());

                // next set PK items on UPDATE's WHERE clause
                //             if (patientEncounterDT.getPatientEncounterUid() == null)
                //               logger.error("field patientEncounterUid is null and the database tables don't allow it.");
                //          else
                preparedStmt.setLong(i++, patientEncounterDT.getPatientEncounterUid().longValue());
                preparedStmt.setInt(i++, patientEncounterDT.getVersionCtrlNbr()
                  .intValue() - 1);
                resultCount = preparedStmt.executeUpdate();

                if (resultCount != 1) {
                    logger.error("Error: none or more than one Clinical Documnet updated at a time, " + "resultCount = " + resultCount);
                    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
                }
            }
        } catch (SQLException sex) {
            logger.fatal("SQLException while updating " + "patientencounter into PATIENTENCOUNTER_TABLE: \n", sex);
            throw new NEDSSSystemException(sex.toString());
        } finally {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    /**
     *
     * @param patientencounterUID
     * @return PatientEncounterDT
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private PatientEncounterDT selectItem(long patientencounterUID)
                                   throws NEDSSSystemException, NEDSSSystemException {

        PatientEncounterDT patientEncounterDT = new PatientEncounterDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();

        try {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_PATIENTENCOUNTER);
            preparedStmt.setLong(1, patientencounterUID);
            resultSet = preparedStmt.executeQuery();
            logger.debug("patientEncounterDT object for: patientencounterUID = " + patientencounterUID);
            resultSetMetaData = resultSet.getMetaData();
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, patientEncounterDT.getClass(), pList);

            for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();) {
                patientEncounterDT = (PatientEncounterDT)anIterator.next();
                patientEncounterDT.setItNew(false);
                patientEncounterDT.setItDirty(false);
                patientEncounterDT.setItDelete(false);
            }
        } catch (SQLException sex) {
            logger.fatal("SQLException while selecting " + "patientEncounter vo; id = " + patientencounterUID, sex);
            throw new NEDSSSystemException(sex.getMessage());
        }
         catch (Exception ex) {
            logger.fatal("Exception while selecting " + "patientEncounter vo; id = " + patientencounterUID, ex);
            throw new NEDSSSystemException(ex.getMessage());
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        logger.debug("returning patientEncounter DT object");

        return patientEncounterDT;
    } //end of selecting item

    /**
     *
     * @param patientencounterUID
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private void removeItem(long patientencounterUID)
                     throws NEDSSSystemException, NEDSSSystemException {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(DELETE_PATIENTENCOUNTER);
            preparedStmt.setLong(1, patientencounterUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1) {
                logger.error("Error: cannot delete patientencounter from PATIENTENCOUNTER_TABLE!! resultCount = " + resultCount);
            }
        } catch (SQLException sex) {
            logger.fatal("SQLException while removing " + "patientencounter; id = " + patientencounterUID, sex);
            throw new NEDSSSystemException(sex.getMessage());
        } finally {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }
}