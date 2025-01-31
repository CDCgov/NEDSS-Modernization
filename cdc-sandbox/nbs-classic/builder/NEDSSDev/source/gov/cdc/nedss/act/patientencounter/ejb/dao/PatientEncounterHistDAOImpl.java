package gov.cdc.nedss.act.patientencounter.ejb.dao;

import gov.cdc.nedss.act.patientencounter.dt.PatientEncounterDT;
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
 * Title:PatientEncounterHistDAOImpl
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

public class PatientEncounterHistDAOImpl
    extends BMPBase {

    static final LogUtils logger = new LogUtils(PatientEncounterHistDAOImpl.class.getName());
   private long patientEncounterUid = -1;
    private short versionCtrlNbr = 0;
    public static final String INSERT_PATIENT_ENCOUNTER_HIST =

        //"org_access_permis, "+
        //"prog_area_access_permis, "+
        "INSERT into patient_encounter_hist(" + "patient_encounter_uid, " + "version_ctrl_nbr, " + "activity_duration_amt, " + "activity_duration_unit_cd, " + "activity_from_time, " + "activity_to_time, " + "acuity_level_cd, " + "acuity_level_desc_txt, " + "add_reason_cd, " + "add_time, " + "add_user_id, " + "admission_source_cd, " + "admission_source_desc_txt, " + "birth_encounter_ind, " + "cd, " + "cd_desc_txt, " + "confidentiality_cd, " + "confidentiality_desc_txt, " + "effective_duration_amt, " + "effective_duration_unit_cd, " + "effective_from_time, " + "effective_to_time, " + "last_chg_reason_cd, " + "last_chg_time, " + "last_chg_user_id, " + "local_id, " + "priority_cd, " + "priority_desc_txt, " + "record_status_cd, " + "record_status_time, " + "referral_source_cd, " + "referral_source_desc_txt, " + "repeat_nbr, " + "status_cd, " + "status_time, " + "txt, " + "user_affiliation_txt, " + "program_jurisdiction_oid, " + "shared_ind) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String SELECT_PATIENT_ENCOUNTER_HIST =

        //"org_access_permis \"OrgAccessPermis\", "+
        //"prog_area_access_permis \"ProgAreaAccessPermis\", "+
        "SELECT patient_encounter_uid \"PatientEncounterUid\", " + "version_ctrl_nbr \"versionCtrlNbr\", " + "activity_duration_amt \"ActivityDurationAmt\", " + "activity_duration_unit_cd \"ActivityDurationUnitCd\", " + "activity_from_time \"ActivityFromTime\", " + "activity_to_time \"ActivityToTime\", " + "acuity_level_cd \"AcuityLevelCd\", " + "acuity_level_desc_txt \"AcuityLevelDescTxt\", " + "add_reason_cd \"AddReasonCd\", " + "add_time \"AddTime\", " + "add_user_id \"AddUserId\", " + "admission_source_cd \"AdmissionSourceCd\", " + "admission_source_desc_txt \"AdmissionSourceDescTxt\", " + "birth_encounter_ind \"BirthEncounterInd\", " + "cd \"Cd\", " + "cd_desc_txt \"CdDescTxt\", " + "confidentiality_cd \"ConfidentialityCd\", " + "confidentiality_desc_txt \"ConfidentialityDescTxt\", " + "effective_duration_amt \"EffectiveDurationAmt\", " + "effective_duration_unit_cd \"EffectiveDurationUnitCd\", " + "effective_from_time \"EffectiveFromTime\", " + "effective_to_time \"EffectiveToTime\", " + "last_chg_reason_cd \"LastChgReasonCd\", " + "last_chg_time \"LastChgTime\", " + "last_chg_user_id \"LastChgUserId\", " + "local_id \"LocalId\", " + "priority_cd \"PriorityCd\", " + "priority_desc_txt \"PriorityDescTxt\", " + "record_status_cd \"RecordStatusCd\", " + "record_status_time \"RecordStatusTime\", " + "referral_source_cd \"ReferralSourceCd\", " + "referral_source_desc_txt \"ReferralSourceDescTxt\", " + "repeat_nbr \"RepeatNbr\", " + "status_cd \"StatusCd\", " + "status_time \"StatusTime\", " + "txt \"Txt\", " + "program_jurisdiction_oid \"programJurisdictionOid\", " + "shared_ind \"sharedInd\", " + "user_affiliation_txt \"UserAffiliationTxt\" " + "from patient_encounter_hist " + "where patient_encounter_uid = ? and version_ctrl_nbr = ?";

    public PatientEncounterHistDAOImpl() {
    } //end of constructor

    public PatientEncounterHistDAOImpl(long patientEncounterUid, short versionCtrlNbr) {
        this.patientEncounterUid = patientEncounterUid;
        this.versionCtrlNbr = versionCtrlNbr;

        //getNextPatientEncounterHistId();
    } //end of constructor

    /**
     *
     * @param obj
     * @throws NEDSSSystemException
     */
    public void store(Object obj)
               throws NEDSSSystemException {
    	try{
	        PatientEncounterDT dt = (PatientEncounterDT)obj;
	
	        if (dt == null)
	            throw new NEDSSSystemException("Error: try to store null PatientEncounterDT object.");
	
	        insertPatientEncounterHist(dt);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    } //end of store()

    /**
     *
     * @param coll
     * @throws NEDSSSystemException
     */
    public void store(Collection<Object> coll)
               throws NEDSSSystemException {
    	try{
	        Iterator<Object> iterator = null;
	
	        if (coll != null) {
	            iterator = coll.iterator();
	
	            while (iterator.hasNext()) {
	                store(iterator.next());
	            } //end of while
	        } //end of if
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    } //end of store(Collection)

    /**
     *
     * @param peUid
     * @param versionCtrlNbr
     * @return PatientEncounterDT
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    public PatientEncounterDT load(Long peUid, Integer versionCtrlNbr)
                            throws NEDSSSystemException {
    	try{
	        logger.info("Starts loadObject() for a patient encounter history...");
	
	        PatientEncounterDT peDT = selectPatientEncounterHist(peUid.longValue(), versionCtrlNbr.shortValue());
	        peDT.setItNew(false);
	        peDT.setItDirty(false);
	        logger.info("Done loadObject() for a patient encounter history - return: " + peDT.toString());
	
	        return peDT;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    } //end of load

    /**
     *
     * @return short versionCtrlNbr
     */
    public short getVersionCtrlNbr() {

        return this.versionCtrlNbr;
    }

     /////////////////////////////////private class methods////////////////

    /**
     *
     * @param peUid
     * @param versionCtrlNbr
     * @return PatientEncounterDT
     * @throws NEDSSSystemException
     * @throws NEDSSSystemException
     */
    private PatientEncounterDT selectPatientEncounterHist(long peUid, short versionCtrlNbr)
                                                   throws NEDSSSystemException {

        PatientEncounterDT peDT = new PatientEncounterDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try {
            dbConnection = getConnection();
        } catch (NEDSSSystemException nsex) {
            logger.fatal("SQLException while obtaining database connection " + "for selectPatientEncounterHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try {
            preparedStmt = dbConnection.prepareStatement(SELECT_PATIENT_ENCOUNTER_HIST);
            preparedStmt.setLong(1, peUid);
            preparedStmt.setShort(2, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();
            logger.debug("PatientEncounterDT object for Patient Encounter history: patientEncounterUid = " + peUid);
            resultSetMetaData = resultSet.getMetaData();
            peDT = (PatientEncounterDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, peDT.getClass());
            peDT.setItNew(false);
            peDT.setItDirty(false);
        } catch (SQLException se) {
            logger.fatal("SQLException while selecting " + "patient encounter history; id = " + peUid, se);
            throw new NEDSSSystemException(se.toString());
        }
         catch (Exception ex) {
            logger.fatal("Exception while selecting " + "patient encounter vo; id = " + peUid, ex);
            throw new NEDSSSystemException(ex.toString());
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        logger.info("return patientEncounterDT for patient encounter history");

        return peDT;
    } //end of selectPatientEncountersHist(...)

    /**
     *
     * @param dt
     * @throws NEDSSSystemException
     */
    private void insertPatientEncounterHist(PatientEncounterDT dt)
                                     throws NEDSSSystemException {

        if (dt.getPatientEncounterUid() != null) {

            int resultCount = 0;
            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;

            try {
                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(INSERT_PATIENT_ENCOUNTER_HIST);
                pStmt.setLong(i++, dt.getPatientEncounterUid().longValue());
                pStmt.setShort(i++, dt.getVersionCtrlNbr().shortValue());

                if (dt.getActivityDurationAmt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getActivityDurationAmt());

                if (dt.getActivityDurationUnitCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getActivityDurationUnitCd());

                if (dt.getActivityFromTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getActivityFromTime());

                if (dt.getActivityToTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getActivityToTime());

                if (dt.getAcuityLevelCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getAcuityLevelCd());

                if (dt.getAcuityLevelDescTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getAcuityLevelCd());

                if (dt.getAddReasonCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getAddReasonCd());

                if (dt.getAddTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getAddTime());

                if (dt.getAddUserId() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setLong(i++, dt.getAddUserId().longValue());

                if (dt.getAdmissionSourceCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getAdmissionSourceCd());

                if (dt.getAdmissionSourceDescTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getAdmissionSourceDescTxt());

                if (dt.getBirthEncounterInd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getBirthEncounterInd());

                if (dt.getCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getCd());

                if (dt.getCdDescTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getCdDescTxt());

                if (dt.getConfidentialityCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getConfidentialityCd());

                if (dt.getConfidentialityDescTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getConfidentialityDescTxt());

                if (dt.getEffectiveDurationAmt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getEffectiveDurationAmt());

                if (dt.getEffectiveDurationUnitCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getEffectiveDurationUnitCd());

                if (dt.getEffectiveFromTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getEffectiveFromTime());

                if (dt.getEffectiveToTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getEffectiveToTime());

                if (dt.getLastChgReasonCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getLastChgReasonCd());

                if (dt.getLastChgTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getLastChgTime());

                if (dt.getLastChgUserId() == null)
                    pStmt.setNull(i++, Types.BIGINT);
                else
                    pStmt.setLong(i++, dt.getLastChgUserId().longValue());

                if (dt.getLocalId() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getLocalId());

                if (dt.getPriorityCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getPriorityCd());

                if (dt.getPriorityDescTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getPriorityDescTxt());

                if (dt.getRecordStatusCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getRecordStatusCd());

                if (dt.getRecordStatusTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getRecordStatusTime());

                if (dt.getReferralSourceCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getReferralSourceCd());

                if (dt.getReferralSourceDescTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getReferralSourceDescTxt());

                if (dt.getRepeatNbr() == null)
                    pStmt.setNull(i++, Types.SMALLINT);
                else
                    pStmt.setShort(i++, dt.getRepeatNbr().shortValue());

                pStmt.setString(i++, dt.getStatusCd());
                pStmt.setTimestamp(i++, dt.getStatusTime());

                if (dt.getTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getTxt());

                if (dt.getUserAffiliationTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getUserAffiliationTxt());

                if (dt.getProgramJurisdictionOid() == null)
                    pStmt.setNull(i++, Types.BIGINT);
                else
                    pStmt.setLong(i++, dt.getProgramJurisdictionOid().longValue());

                pStmt.setString(i++, dt.getSharedInd());
                resultCount = pStmt.executeUpdate();

                if (resultCount != 1) {
                    throw new NEDSSSystemException("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
                }
            } catch (SQLException se) {
            	logger.fatal("Exception  = "+se.getMessage(), se);
                throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
            } finally {
                closeStatement(pStmt);
                releaseConnection(dbConnection);
            } //end of finally
        } //end of if
    } //end of insertPatientEncounterHist()
} //end of class