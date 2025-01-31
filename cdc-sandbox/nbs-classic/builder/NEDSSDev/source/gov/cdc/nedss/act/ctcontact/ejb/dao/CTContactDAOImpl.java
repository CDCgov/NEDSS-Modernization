package gov.cdc.nedss.act.ctcontact.ejb.dao;

import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssTimeLogger;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

/**
 * getEpilinkDTCollection method updated to get contact records that are associated to active CR only. Please see comment in the method for more details
 * @update author Pradeep Sharma
 *
 */

public class CTContactDAOImpl  extends DAOBase {
	private static final LogUtils logger = new LogUtils(CTContactDAOImpl.class.getName());
	
	private static final String SELECT_CT_CONTACT_UID = "SELECT CT_CONTACT_UID FROM CT_CONTACT WHERE CT_CONTACT_UID = ?";
    
	private static final String INSERT_CT_CONTACT = "INSERT INTO "+ DataTables.CT_CONTACT +" (add_Time, add_User_Id, contact_Entity_Phc_Uid, contact_Entity_Uid,contact_Status, ct_Contact_Uid," 
		+"  disposition_Cd, disposition_Date, evaluation_Completed_Cd, evaluation_Date,evaluation_Txt,  "
		+"  group_Name_Cd, health_Status_Cd, investigator_Assigned_Date, jurisdiction_Cd,last_Chg_Time, last_Chg_User_Id,  "
		+"  local_Id, named_On_Date, priority_Cd,program_Jurisdiction_Oid,prog_Area_Cd,record_Status_Cd,record_Status_Time, relationship_Cd, "
		+"  risk_Factor_Cd,  risk_Factor_Txt,shared_Ind_Cd, subject_Entity_Phc_Uid,subject_Entity_Uid,symptom_Cd, symptom_Onset_Date, symptom_Txt, "
		+"  third_Party_Entity_Uid, third_Party_Entity_Phc_Uid, treatment_End_Cd, treatment_End_Date, treatment_Initiated_Cd, "
		+"  treatment_Not_End_Rsn_Cd,treatment_Not_Start_Rsn_Cd, treatment_Start_Date, treatment_Txt, txt, version_Ctrl_Nbr, " 
		+"  processing_decision_cd, subject_entity_epi_link_id, contact_entity_epi_link_id,named_during_interview_uid, contact_referral_basis_cd) " 
		+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String INSERT_CT_CONTACT_HIST = "INSERT INTO "+ DataTables.CT_CONTACT_HIST +" (add_Time, add_User_Id, contact_Entity_Phc_Uid, contact_Entity_Uid,contact_Status, ct_Contact_Uid,"
	+"  disposition_Cd, disposition_Date, evaluation_Completed_Cd, evaluation_Date,evaluation_Txt,  "
	+"  group_Name_Cd, health_Status_Cd, investigator_Assigned_Date, jurisdiction_Cd,last_Chg_Time, last_Chg_User_Id,  "
	+"  local_Id, named_On_Date, priority_Cd,program_Jurisdiction_Oid,prog_Area_Cd,record_Status_Cd,record_Status_Time, relationship_Cd, "
	+"  risk_Factor_Cd, risk_Factor_Txt,shared_Ind_Cd, subject_Entity_Phc_Uid,subject_Entity_Uid,symptom_Cd, symptom_Onset_Date, symptom_Txt,  "
	+"  third_Party_Entity_Uid, third_Party_Entity_Phc_Uid, treatment_End_Cd, treatment_End_Date,treatment_Initiated_Cd, "
	+"  treatment_Not_End_Rsn_Cd,treatment_Not_Start_Rsn_Cd, treatment_Start_Date, treatment_Txt, txt, version_Ctrl_Nbr, " 
	+"  processing_decision_cd, subject_entity_epi_link_id, contact_entity_epi_link_id,named_during_interview_uid, contact_referral_basis_cd) " 		
		+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String UPDATE_CT_CONTACT="UPDATE "+ DataTables.CT_CONTACT 	+ "	SET  CONTACT_ENTITY_PHC_UID=?, CONTACT_STATUS=?, DISPOSITION_CD=?, DISPOSITION_DATE =?, EVALUATION_COMPLETED_CD=?, EVALUATION_DATE=?,  EVALUATION_TXT=?,"
	+" GROUP_NAME_CD=?,HEALTH_STATUS_CD=?, INVESTIGATOR_ASSIGNED_DATE=?, LAST_CHG_TIME=?, LAST_CHG_USER_ID=?, "
	+" NAMED_ON_DATE=?, PRIORITY_CD=?, RECORD_STATUS_CD=?, RECORD_STATUS_TIME=?, RELATIONSHIP_CD=?, RISK_FACTOR_CD=?, RISK_FACTOR_TXT=?, "
	+" SHARED_IND_CD=?, SYMPTOM_CD=?, SYMPTOM_ONSET_DATE=?, SYMPTOM_TXT=?, THIRD_PARTY_ENTITY_UID=?, THIRD_PARTY_ENTITY_PHC_UID=?, TREATMENT_END_CD=?, TREATMENT_END_DATE=?, TREATMENT_INITIATED_CD=?, TREATMENT_NOT_END_RSN_CD=?, "
	+" TREATMENT_NOT_START_RSN_CD=?, TREATMENT_START_DATE=?, TREATMENT_TXT =?, txt=?, VERSION_CTRL_NBR=?, JURISDICTION_CD=?, PROGRAM_JURISDICTION_OID=?, processing_decision_cd=?, subject_entity_epi_link_id=?, contact_entity_epi_link_id=?, named_during_interview_uid=?, contact_referral_basis_cd=? "
	+" where CT_CONTACT_UID = ?";

	private static final String UPDATE_MERGE_CT_CONTACT="UPDATE "+ DataTables.CT_CONTACT 	+ "	SET  CONTACT_ENTITY_PHC_UID=?, CONTACT_STATUS=?, DISPOSITION_CD=?, DISPOSITION_DATE =?, EVALUATION_COMPLETED_CD=?, EVALUATION_DATE=?,  EVALUATION_TXT=?,"
	+" GROUP_NAME_CD=?,HEALTH_STATUS_CD=?, INVESTIGATOR_ASSIGNED_DATE=?, LAST_CHG_TIME=?, LAST_CHG_USER_ID=?, "
	+" NAMED_ON_DATE=?, PRIORITY_CD=?, RECORD_STATUS_CD=?, RECORD_STATUS_TIME=?, RELATIONSHIP_CD=?, RISK_FACTOR_CD=?, RISK_FACTOR_TXT=?, "
	+" SHARED_IND_CD=?, SYMPTOM_CD=?, SYMPTOM_ONSET_DATE=?, SYMPTOM_TXT=?, THIRD_PARTY_ENTITY_UID=?, THIRD_PARTY_ENTITY_PHC_UID=?, TREATMENT_END_CD=?, TREATMENT_END_DATE=?, TREATMENT_INITIATED_CD=?, TREATMENT_NOT_END_RSN_CD=?, "
	+" TREATMENT_NOT_START_RSN_CD=?, TREATMENT_START_DATE=?, TREATMENT_TXT =?, txt=?, VERSION_CTRL_NBR=?, JURISDICTION_CD=?, PROGRAM_JURISDICTION_OID=?, processing_decision_cd=?, subject_entity_epi_link_id=?, contact_entity_epi_link_id=?, named_during_interview_uid=?, contact_referral_basis_cd=?, "
	+" subject_Entity_Phc_Uid=?, subject_Entity_Uid=? "
	+" where CT_CONTACT_UID = ?";

	private static final String SELECT_CT_CONTACT = "Select ADD_TIME \"addTime\", ADD_USER_ID \"addUserId\", CONTACT_ENTITY_PHC_UID \"contactEntityPhcUid\","
      +"  CONTACT_ENTITY_UID \"contactEntityUid\", CONTACT_STATUS \"contactStatus\", CT_CONTACT_UID \"ctContactUid\",DISPOSITION_CD \"dispositionCd\","
      +"  DISPOSITION_DATE \"dispositionDate\", EVALUATION_COMPLETED_CD \"evaluationCompletedCd\", EVALUATION_DATE \"evaluationDate\", EVALUATION_TXT \"evaluationTxt\","
      +"  GROUP_NAME_CD \"groupNameCd\","
      +"  HEALTH_STATUS_CD \"healthStatusCd\", INVESTIGATOR_ASSIGNED_DATE \"investigatorAssignedDate\", JURISDICTION_CD \"jurisdictionCd\",LAST_CHG_TIME \"lastChgTime\","
      +"  LAST_CHG_USER_ID \"lastChgUserId\", LOCAL_ID \"localId\", NAMED_ON_DATE \"namedOnDate\","
      +"  PRIORITY_CD \"priorityCd\", PROGRAM_JURISDICTION_OID \"programJurisdictionOid\", PROG_AREA_CD \"progAreaCd\", RECORD_STATUS_CD \"recordStatusCd\","
      +"  RECORD_STATUS_TIME \"recordStatusTime\", RELATIONSHIP_CD \"relationshipCd\", RISK_FACTOR_CD \"riskFactorCd\",  RISK_FACTOR_TXT \"riskFactorTxt\","
      +"  SHARED_IND_CD \"sharedIndCd\", SUBJECT_ENTITY_PHC_UID \"subjectEntityPhcUid\", SUBJECT_ENTITY_UID \"subjectEntityUid\", SYMPTOM_CD \"symptomCd\", SYMPTOM_ONSET_DATE \"symptomOnsetDate\","
      +"  SYMPTOM_TXT \"symptomTxt\", THIRD_PARTY_ENTITY_UID \"thirdPartyEntityUid\", THIRD_PARTY_ENTITY_PHC_UID \"thirdPartyEntityPhcUid\", TREATMENT_END_CD \"treatmentEndCd\", TREATMENT_END_DATE \"treatmentEndDate\","
      +"  TREATMENT_INITIATED_CD \"treatmentInitiatedCd\", TREATMENT_NOT_END_RSN_CD \"treatmentNotEndRsnCd\", TREATMENT_NOT_START_RSN_CD \"treatmentNotStartRsnCd\", TREATMENT_START_DATE \"treatmentStartDate\","
      +"  TREATMENT_TXT \"treatmentTxt\", TXT \"txt\", VERSION_CTRL_NBR \"versionCtrlNbr\", processing_decision_cd \"processingDecisionCd\", subject_entity_epi_link_id \"subEntityEpilinkId\","
      +"  contact_entity_epi_link_id \"conEntityEpilinkId\", named_during_interview_uid \"namedDuringInterviewUid\", contact_referral_basis_cd \"contactReferralBasisCd\" FROM "+ DataTables.CT_CONTACT +" WHERE ct_contact_uid = ? and record_status_cd='ACTIVE'";


	private static final String INSERT_ACTIVITY = "INSERT INTO Act (act_uid, class_cd, mood_cd) VALUES (?, ?, ?)";

	private static final String LOGICALLY_DELETE_CT_CONTACT = "UPDATE " + DataTables.CT_CONTACT + " SET last_chg_time=?, last_chg_user_id=?,  record_status_cd=?, record_status_time=? WHERE ct_contact_uid=?";

	private static final String UPDATE_CONTACT_FOR_NEW_INVESTIGATION = "update " + DataTables.CT_CONTACT + " set contact_entity_phc_uid = null, subject_entity_uid=?, subject_entity_phc_uid=?, prog_area_cd=?, program_jurisdiction_oid=? where subject_entity_phc_uid=?";
	
	private static final String UPDATE_CONTACT_FOR_SURVIVING_INVESTIGATION = "update " + DataTables.CT_CONTACT + " set subject_entity_uid=?, subject_entity_phc_uid=? where subject_entity_phc_uid=?";
	
	private static final String UPDATE_PATIENT_NAMED_BY_CONTACT_FOR_SURVIVING_INVESTIGATION = "update " + DataTables.CT_CONTACT + " set contact_entity_phc_uid=? where contact_entity_phc_uid=?";
	
	private static final String UPDATE_CONTACT_FOR_CHANGE_CONDITION = "update " + DataTables.CT_CONTACT + " set prog_area_cd=?, program_jurisdiction_oid=? where subject_entity_phc_uid=?";
	
	private static final String DELINK_CONTACTS_FOR_OLD_INVESTIGATION = "update " + DataTables.CT_CONTACT + " set contact_entity_phc_uid = null where contact_entity_phc_uid = ?";
	public CTContactDAOImpl() {
	}

	public CTContactDT updateCTContact(CTContactDT ctContactDT) throws NEDSSConcurrentDataException {
		long start = NedssTimeLogger.generateTimeDiffStartLog(this.getClass().getCanonicalName(), "updateCTContact");
		//NedssTimeLogger.generateStartLog(this.getClass(), this.getClass().);
		
		
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		int resultCount = 0;
		int i = 1;
		String sqlQuery = null;
		try {
			dbConnection = getConnection();
			if(ctContactDT.getIsMergeCaseInd()){
				pStmt = dbConnection.prepareStatement(UPDATE_MERGE_CT_CONTACT);
			}else{
				pStmt = dbConnection.prepareStatement(UPDATE_CT_CONTACT);
			}
			if(ctContactDT.getContactEntityPhcUid() == null)
				pStmt.setNull(i++, Types.INTEGER);
            else
            	pStmt.setLong(i++,ctContactDT.getContactEntityPhcUid());//1
			pStmt.setString(i++,ctContactDT.getContactStatus());//2
			pStmt.setString(i++,ctContactDT.getDispositionCd());//3
			if(ctContactDT.getDispositionDate() == null)
				pStmt.setNull(i++, Types.TIMESTAMP);
            else
            	pStmt.setTimestamp(i++,ctContactDT.getDispositionDate());//4
			pStmt.setString(i++,ctContactDT.getEvaluationCompletedCd());//5
			if(ctContactDT.getEvaluationDate() == null)
				pStmt.setNull(i++, Types.TIMESTAMP);
            else
            	pStmt.setTimestamp(i++,ctContactDT.getEvaluationDate());//6
			pStmt.setString(i++,ctContactDT.getEvaluationTxt());//7
			pStmt.setString(i++,ctContactDT.getGroupNameCd());//8
			pStmt.setString(i++,ctContactDT.getHealthStatusCd());//9
			if(ctContactDT.getInvestigatorAssignedDate() == null)
				pStmt.setNull(i++, Types.TIMESTAMP);
            else
            	pStmt.setTimestamp(i++,ctContactDT.getInvestigatorAssignedDate());//10
			if(ctContactDT.getLastChgTime() == null)
				pStmt.setNull(i++, Types.TIMESTAMP);
            else
            	pStmt.setTimestamp(i++,ctContactDT.getLastChgTime());//11
			if(ctContactDT.getLastChgUserId() == null)
				pStmt.setNull(i++, Types.INTEGER);
            else
            	pStmt.setLong(i++,ctContactDT.getLastChgUserId());//12
			if(ctContactDT.getNamedOnDate() == null)
				pStmt.setNull(i++, Types.TIMESTAMP);
            else
            	pStmt.setTimestamp(i++,ctContactDT.getNamedOnDate());//13
			pStmt.setString(i++,ctContactDT.getPriorityCd());//14
			pStmt.setString(i++,ctContactDT.getRecordStatusCd());//15
			if(ctContactDT.getRecordStatusTime() == null)
				pStmt.setNull(i++, Types.TIMESTAMP);
            else
            pStmt.setTimestamp(i++,ctContactDT.getRecordStatusTime());//16
			pStmt.setString(i++,ctContactDT.getRelationshipCd());//17
			pStmt.setString(i++,ctContactDT.getRiskFactorCd());//18
			pStmt.setString(i++,ctContactDT.getRiskFactorTxt());//19
			pStmt.setString(i++,ctContactDT.getSharedIndCd());//20
			pStmt.setString(i++,ctContactDT.getSymptomCd());//21
			if(ctContactDT.getSymptomOnsetDate() == null)
				pStmt.setNull(i++, Types.TIMESTAMP);
            else
            	pStmt.setTimestamp(i++,ctContactDT.getSymptomOnsetDate());//22
			pStmt.setString(i++,ctContactDT.getSymptomTxt());//23
			if(ctContactDT.getThirdPartyEntityUid() == null)
				pStmt.setNull(i++, Types.INTEGER);
            else
            	pStmt.setLong(i++,ctContactDT.getThirdPartyEntityUid());//24
			if(ctContactDT.getThirdPartyEntityPhcUid() == null)
				pStmt.setNull(i++, Types.INTEGER);
            else			
            	pStmt.setLong(i++,ctContactDT.getThirdPartyEntityPhcUid());//25
			pStmt.setString(i++,ctContactDT.getTreatmentEndCd());//26
			if(ctContactDT.getTreatmentEndDate() == null)
				pStmt.setNull(i++, Types.TIMESTAMP);
            else
            	pStmt.setTimestamp(i++,ctContactDT.getTreatmentEndDate());//27
			pStmt.setString(i++,ctContactDT.getTreatmentInitiatedCd());//28
			pStmt.setString(i++,ctContactDT.getTreatmentNotEndRsnCd());//29
			pStmt.setString(i++,ctContactDT.getTreatmentNotStartRsnCd());//30
			if(ctContactDT.getTreatmentStartDate() == null)
				pStmt.setNull(i++, Types.TIMESTAMP);
            else
            	pStmt.setTimestamp(i++,ctContactDT.getTreatmentStartDate());//31
			pStmt.setString(i++,ctContactDT.getTreatmentTxt());//32
			pStmt.setString(i++,ctContactDT.getTxt());//33
			if(ctContactDT.getVersionCtrlNbr() == null)
				pStmt.setNull(i++, Types.INTEGER);
            else
            pStmt.setInt(i++,ctContactDT.getVersionCtrlNbr());//34
			pStmt.setString(i++, ctContactDT.getJurisdictionCd());//35
			pStmt.setLong(i++, ctContactDT.getProgramJurisdictionOid());//36
			pStmt.setString(i++, ctContactDT.getProcessingDecisionCd());//37
			pStmt.setString(i++, ctContactDT.getSubEntityEpilinkId());//38
			pStmt.setString(i++, ctContactDT.getConEntityEpilinkId());//39
			if(ctContactDT.getNamedDuringInterviewUid() == null)
				pStmt.setNull(i++, Types.INTEGER);
            else			
            	pStmt.setLong(i++,ctContactDT.getNamedDuringInterviewUid());//40			
			pStmt.setString(i++, ctContactDT.getContactReferralBasisCd());//41
			if(ctContactDT.getIsMergeCaseInd()){
				pStmt.setLong(i++, ctContactDT.getSubjectEntityPhcUid());//**42 Merge case 
				pStmt.setLong(i++, ctContactDT.getSubjectEntityUid());//**43 Merge case 
			}
			pStmt.setLong(i++,ctContactDT.getCtContactUid());//42//
			resultCount = pStmt.executeUpdate();
			NedssTimeLogger.generateTimeDiffEndLog(this.getClass().getCanonicalName(), "updateCTContact", start);
			
			if (resultCount != 1)
			{
				   logger.error("Error: none or more than one CTContactDT updated at a time, " +
	                        "resultCount = " + resultCount);
	             	throw new NEDSSConcurrentDataException("Error: none or more than one entity updated at a time, resultCount = " +
						resultCount);
			}
		}
		catch (SQLException se)
		{
			logger.fatal("Error: SQLException while updating CT_CONTACT table "+se.getMessage(),se);
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "updateCTContact", se);
			throw new NEDSSDAOSysException("Error: SQLException while updating CT_CONTACT table\n" +
					se.getMessage());
		}
		catch (Exception se)
		{
			logger.error("Exception raised:"+ ctContactDT.toString());
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "updateCTContact", se);
			throw new NEDSSDAOSysException("Error: Exception while updating CT_CONTACT table\n" +
					se.getMessage());
		}
		finally
		{
			closeStatement(pStmt);
			releaseConnection(dbConnection);
		}//end of finally

		return ctContactDT;

	}

	public void deleteCTContact(CTContactDT ctContactDT) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(this.getClass().getCanonicalName(), "deleteCTContact");
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		
		int resultCount = 0;
		logger.debug("LOGICALLY_DELETE_CT_CONTACT = " + LOGICALLY_DELETE_CT_CONTACT);

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(LOGICALLY_DELETE_CT_CONTACT);
			int i = 1;
			preparedStmt.setTimestamp(i++, ctContactDT.getLastChgTime()); //1
			preparedStmt.setLong(i++, ctContactDT.getLastChgUserId().longValue());//2 

			preparedStmt.setString(i++, ctContactDT.getRecordStatusCd()); //3
			preparedStmt.setTimestamp(i++, ctContactDT.getRecordStatusTime()); //4
			preparedStmt.setLong(i++, ctContactDT.getCtContactUid()); //5

			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
			NedssTimeLogger.generateTimeDiffEndLog(this.getClass().getCanonicalName(), "deleteCTContact", start);
		} catch(SQLException sqlex) {
			logger.fatal("SQLException while deleteCTContact - ctContactDT into CT_contact: " + ctContactDT.toString(), sqlex);
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "deleteCTContact", sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch(Exception ex) {
			logger.fatal("Error while update into CT_contact for logical delete, ctContactDT = " + ctContactDT.toString(), ex);
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "deleteCTContact", ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		
	}
	
	public CTContactDT insertCTContact(CTContactDT ctContactDT){ 
		long start = NedssTimeLogger.generateTimeDiffStartLog(this.getClass().getCanonicalName(), "insertCTContact");
		// Starts inserting a new record into CT_contact table
		long ctContactUid = -1;
		Connection dbConnection = null;
		Connection dbConnection2 = null;
		PreparedStatement preparedStmt = null;
		PreparedStatement preparedStmt2 = null;
		String localUid = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;
		
		try  {
			// Inserts an act table record for a new CT_CONTACT
			uidGen = new UidGeneratorHelper();
			ctContactUid  = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
			logger.debug("New ct_contact_uid is : " + ctContactUid);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_ACTIVITY);
			int i = 1;
			preparedStmt.setLong(i++, ctContactUid);
			preparedStmt.setString(i++, NEDSSConstants.CLASS_CD_CONTACT);
			preparedStmt.setString(i++, NEDSSConstants.EVENT_MOOD_CODE);
			ctContactDT.setCtContactUid(ctContactUid);
			resultCount = preparedStmt.executeUpdate();
		} catch(Exception e) {
			logger.fatal("Exception attempting to insert ACT record for new CT_CONTACT -> " + ctContactUid, e);
			throw new NEDSSDAOSysException( e.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);

		}
		
		try {
			dbConnection2 = getConnection();
			preparedStmt2 = dbConnection2.prepareStatement(INSERT_CT_CONTACT);
			uidGen = new UidGeneratorHelper();
			localUid = uidGen.getLocalID(UidClassCodes.CONTACT_CLASS_CODE);
			logger.debug("New localId for CT_CONTACT is: " + localUid);
			ctContactDT.setLocalId(localUid);
			int i = 1;
			preparedStmt2.setTimestamp(i++,ctContactDT.getAddTime()); 
			if(ctContactDT.getAddUserId() == null)
				preparedStmt2.setNull(i++, Types.INTEGER);
            else
            	preparedStmt2.setLong(i++, ctContactDT.getAddUserId()); 
			if(ctContactDT.getContactEntityPhcUid() == null)
				preparedStmt2.setNull(i++, Types.INTEGER);
            else
            	preparedStmt2.setLong(i++, ctContactDT.getContactEntityPhcUid()); 
			preparedStmt2.setLong(i++, ctContactDT.getContactEntityUid());
			preparedStmt2.setString(i++,ctContactDT.getContactStatus()); 
			
			if(ctContactDT.getCtContactUid() == null)
				preparedStmt2.setNull(i++, Types.INTEGER);
            else
            	preparedStmt2.setLong(i++, ctContactDT.getCtContactUid());
			preparedStmt2.setString(i++,ctContactDT.getDispositionCd());
			if (ctContactDT.getDispositionDate() == null)
				preparedStmt2.setNull(i++, Types.TIMESTAMP);
            else
            	preparedStmt2.setTimestamp(i++,ctContactDT.getDispositionDate());
			preparedStmt2.setString(i++,ctContactDT.getEvaluationCompletedCd()); 
			if (ctContactDT.getEvaluationDate() == null)
				preparedStmt2.setNull(i++, Types.TIMESTAMP);
            else
            	preparedStmt2.setTimestamp(i++,ctContactDT.getEvaluationDate());
			preparedStmt2.setString(i++,ctContactDT.getEvaluationTxt()); 
			preparedStmt2.setString(i++,ctContactDT.getGroupNameCd()); 
			preparedStmt2.setString(i++,ctContactDT.getHealthStatusCd()); 
			if (ctContactDT.getInvestigatorAssignedDate() == null)
				preparedStmt2.setNull(i++, Types.TIMESTAMP);
            else
            	preparedStmt2.setTimestamp(i++,ctContactDT.getInvestigatorAssignedDate()); 
			preparedStmt2.setString(i++,ctContactDT.getJurisdictionCd());
			preparedStmt2.setTimestamp(i++,ctContactDT.getLastChgTime()); 
			if(ctContactDT.getLastChgUserId() == null)
				preparedStmt2.setNull(i++, Types.INTEGER);
            else
            	preparedStmt2.setLong(i++, ctContactDT.getLastChgUserId()); 
			preparedStmt2.setString(i++,ctContactDT.getLocalId()); 
			if (ctContactDT.getNamedOnDate() == null)
				preparedStmt2.setNull(i++, Types.TIMESTAMP);
            else
            	preparedStmt2.setTimestamp(i++,ctContactDT.getNamedOnDate()); 
			preparedStmt2.setString(i++,ctContactDT.getPriorityCd());
			preparedStmt2.setLong(i++, ctContactDT.getProgramJurisdictionOid());
			preparedStmt2.setString(i++,ctContactDT.getProgAreaCd());
			preparedStmt2.setString(i++,ctContactDT.getRecordStatusCd());
			if (ctContactDT.getRecordStatusTime() == null)
				preparedStmt2.setNull(i++, Types.TIMESTAMP);
            else
            	preparedStmt2.setTimestamp(i++,ctContactDT.getRecordStatusTime()); 
			preparedStmt2.setString(i++,ctContactDT.getRelationshipCd()); 
			preparedStmt2.setString(i++,ctContactDT.getRiskFactorCd()); 
			preparedStmt2.setString(i++,ctContactDT.getRiskFactorTxt());
			preparedStmt2.setString(i++,ctContactDT.getSharedIndCd()); 
			if(ctContactDT.getSubjectEntityPhcUid() == null)
				preparedStmt2.setNull(i++, Types.INTEGER);
            else
            	preparedStmt2.setLong(i++, ctContactDT.getSubjectEntityPhcUid());
			preparedStmt2.setLong(i++, ctContactDT.getSubjectEntityUid());
			preparedStmt2.setString(i++,ctContactDT.getSymptomCd()); 
			if (ctContactDT.getSymptomOnsetDate() == null)
				preparedStmt2.setNull(i++, Types.TIMESTAMP);
            else
            	preparedStmt2.setTimestamp(i++,ctContactDT.getSymptomOnsetDate()); 
			preparedStmt2.setString(i++,ctContactDT.getSymptomTxt()); 
			if(ctContactDT.getThirdPartyEntityUid() == null)
				preparedStmt2.setNull(i++, Types.INTEGER);
            else
            	preparedStmt2.setLong(i++,ctContactDT.getThirdPartyEntityUid());
			if(ctContactDT.getThirdPartyEntityPhcUid() == null)
				preparedStmt2.setNull(i++, Types.INTEGER);
            else			
            	preparedStmt2.setLong(i++,ctContactDT.getThirdPartyEntityPhcUid());
			preparedStmt2.setString(i++,ctContactDT.getTreatmentEndCd()); 
			if (ctContactDT.getTreatmentEndDate() == null)
				preparedStmt2.setNull(i++, Types.TIMESTAMP);
            else
            	preparedStmt2.setTimestamp(i++,ctContactDT.getTreatmentEndDate());
			preparedStmt2.setString(i++,ctContactDT.getTreatmentInitiatedCd()); 
			preparedStmt2.setString(i++,ctContactDT.getTreatmentNotEndRsnCd());
			preparedStmt2.setString(i++,ctContactDT.getTreatmentNotStartRsnCd()); 
			if (ctContactDT.getTreatmentStartDate() == null)
				preparedStmt2.setNull(i++, Types.TIMESTAMP);
            else
            	preparedStmt2.setTimestamp(i++,ctContactDT.getTreatmentStartDate()); 
			preparedStmt2.setString(i++,ctContactDT.getTreatmentTxt()); 
			preparedStmt2.setString(i++,ctContactDT.getTxt()); 
			preparedStmt2.setInt(i++, ctContactDT.getVersionCtrlNbr());
			preparedStmt2.setString(i++,ctContactDT.getProcessingDecisionCd()); 
			preparedStmt2.setString(i++, ctContactDT.getSubEntityEpilinkId());
			preparedStmt2.setString(i++, ctContactDT.getConEntityEpilinkId());
			if(ctContactDT.getNamedDuringInterviewUid() != null){
			preparedStmt2.setLong(i++, ctContactDT.getNamedDuringInterviewUid());
			}else{
			preparedStmt2.setNull(i++, Types.BIGINT);
			}
			preparedStmt2.setString(i++, ctContactDT.getContactReferralBasisCd()); 		
			resultCount = preparedStmt2.executeUpdate();
			
			logger.debug("Insert complete - new CT_CONTACT, ctContactUid = " + ctContactUid);
			NedssTimeLogger.generateTimeDiffEndLog(this.getClass().getCanonicalName(), "insertCTContact", start);
			return ctContactDT;
			
		} catch(SQLException sqlex) {
			logger.fatal("SQLException while inserting a new ctContactDT into CT_CONTACT: \n", sqlex);
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "insertCTContact", sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch(Exception ex) {
			logger.fatal("Error while inserting into CT_CONTACT = " + ctContactDT.toString(), ex);
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "insertCTContact", ex);
			throw new NEDSSSystemException(ex.toString());
		}  finally {
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection2);
		}

	}

	public CTContactDT insertNBSContactHist(CTContactDT cTContactDT) { 
		long start = NedssTimeLogger.generateTimeDiffStartLog(this.getClass().getCanonicalName(), "insertNBSContactHist");
		/**
		 * Starts inserting a new record into CT_CONTACT_HIST table
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int i = 1;
		int resultCount=0;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_CT_CONTACT_HIST);

			preparedStmt.setTimestamp(i++,cTContactDT.getAddTime());//1 
			preparedStmt.setLong(i++, cTContactDT.getAddUserId()); //2
			if (cTContactDT.getContactEntityPhcUid() == null)//3
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, cTContactDT.getContactEntityPhcUid()); 
			if (cTContactDT.getContactEntityUid() == null)//4
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, cTContactDT.getContactEntityUid());
			preparedStmt.setString(i++,cTContactDT.getContactStatus()); 
			if (cTContactDT.getCtContactUid() == null)//5
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, cTContactDT.getCtContactUid());
			preparedStmt.setString(i++,cTContactDT.getDispositionCd());//7
			if (cTContactDT.getDispositionDate() == null)//8
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++,cTContactDT.getDispositionDate());
			preparedStmt.setString(i++,cTContactDT.getEvaluationCompletedCd()); //9
			if (cTContactDT.getEvaluationDate() == null)//	10
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++,cTContactDT.getEvaluationDate());
			preparedStmt.setString(i++,cTContactDT.getEvaluationTxt()); //11
			preparedStmt.setString(i++,cTContactDT.getGroupNameCd()); //14
			preparedStmt.setString(i++,cTContactDT.getHealthStatusCd()); //15
			if (cTContactDT.getInvestigatorAssignedDate() == null)//16
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++,cTContactDT.getInvestigatorAssignedDate()); 
			preparedStmt.setString(i++,cTContactDT.getJurisdictionCd());//17
			if (cTContactDT.getLastChgTime() == null)//18
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++,cTContactDT.getLastChgTime()); 
			preparedStmt.setLong(i++, cTContactDT.getLastChgUserId());//19 
			preparedStmt.setString(i++,cTContactDT.getLocalId());//21 
			if (cTContactDT.getNamedOnDate() == null)//22
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++,cTContactDT.getNamedOnDate()); 
			preparedStmt.setString(i++,cTContactDT.getPriorityCd());//23
			if (cTContactDT.getProgramJurisdictionOid() == null)//24
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, cTContactDT.getProgramJurisdictionOid());
			preparedStmt.setString(i++,cTContactDT.getProgAreaCd());//25
			preparedStmt.setString(i++,cTContactDT.getRecordStatusCd());//26
			if (cTContactDT.getRecordStatusTime() == null)//27
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++,cTContactDT.getRecordStatusTime()); 
			preparedStmt.setString(i++,cTContactDT.getRelationshipCd()); //28
			preparedStmt.setString(i++,cTContactDT.getRiskFactorCd()); //29
			preparedStmt.setString(i++,cTContactDT.getRiskFactorTxt());//30
			preparedStmt.setString(i++,cTContactDT.getSharedIndCd()); //31
			if (cTContactDT.getSubjectEntityPhcUid() == null)//32
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, cTContactDT.getSubjectEntityPhcUid());
        	if (cTContactDT.getSubjectEntityUid() == null)//33
        		preparedStmt.setNull(i++, Types.INTEGER);
        	else
        		preparedStmt.setLong(i++, cTContactDT.getSubjectEntityUid());
			preparedStmt.setString(i++,cTContactDT.getSymptomCd()); //34
			if (cTContactDT.getSymptomOnsetDate() == null)//35
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++,cTContactDT.getSymptomOnsetDate()); 
			preparedStmt.setString(i++,cTContactDT.getSymptomTxt());//36
			if(cTContactDT.getThirdPartyEntityUid() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
            else
            	preparedStmt.setLong(i++,cTContactDT.getThirdPartyEntityUid());//37
			if(cTContactDT.getThirdPartyEntityPhcUid() == null)
				preparedStmt.setNull(i++, Types.INTEGER);
            else			
            	preparedStmt.setLong(i++,cTContactDT.getThirdPartyEntityPhcUid());//38		
			preparedStmt.setString(i++,cTContactDT.getTreatmentEndCd());//39 
			if (cTContactDT.getTreatmentEndDate() == null)//40
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++,cTContactDT.getTreatmentEndDate());
			preparedStmt.setString(i++,cTContactDT.getTreatmentInitiatedCd()); //41
			preparedStmt.setString(i++,cTContactDT.getTreatmentNotEndRsnCd());//42
			preparedStmt.setString(i++,cTContactDT.getTreatmentNotStartRsnCd()); //43
			if (cTContactDT.getTreatmentStartDate() == null)//44
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++,cTContactDT.getTreatmentStartDate()); 
			preparedStmt.setString(i++,cTContactDT.getTreatmentTxt()); //45
			preparedStmt.setString(i++,cTContactDT.getTxt()); //46
			if(cTContactDT.getVersionCtrlNbr() == null)//47
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setInt(i++, cTContactDT.getVersionCtrlNbr());
			preparedStmt.setString(i++,cTContactDT.getProcessingDecisionCd()); //48
			preparedStmt.setString(i++, cTContactDT.getSubEntityEpilinkId());
			preparedStmt.setString(i++, cTContactDT.getConEntityEpilinkId());
			
        	if (cTContactDT.getNamedDuringInterviewUid() == null)//51
        		preparedStmt.setNull(i++, Types.INTEGER);
        	else
        		preparedStmt.setLong(i++, cTContactDT.getNamedDuringInterviewUid());			
			
			preparedStmt.setString(i++, cTContactDT.getContactReferralBasisCd()); 				
			
			resultCount = preparedStmt.executeUpdate();
			logger.debug("done insert a cTContactDT! CTContactDTUID = " + cTContactDT.getCtContactUid());
			NedssTimeLogger.generateTimeDiffEndLog(this.getClass().getCanonicalName(), "insertNBSContactHist", start);
			return cTContactDT;
		} catch(SQLException sqlex) {
			logger.fatal("SQLException while inserting " +
					"a new CTContactHistDT into nbs_document: \n", sqlex);
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "insertNBSContactHist", sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		} catch(Exception ex) {
			logger.fatal("Error while inserting into CTContactHistDT, id = " + cTContactDT.getCtContactUid(), ex);
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "insertNBSContactHist", ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	public Object loadObject(long aUID) throws NEDSSDAOSysException,
	NEDSSSystemException {
		CTContactDT cTContactDT = selectCTContact(aUID);
		return cTContactDT;
	};

	public CTContactDT selectCTContact(long ctContactUid){
		long start = NedssTimeLogger.generateTimeDiffStartLog(this.getClass().getCanonicalName(), "selectCTContact");		
		Connection dbConnection = null;
		CTContactDT ctContactDT = new CTContactDT();
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		int i = 1;

		CTContactVO ctContactVO = new CTContactVO();

		try {
			 
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_CT_CONTACT);
			preparedStmt.setLong(i++, ctContactUid);             		
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			ctContactDT = (CTContactDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, ctContactDT.getClass());
			logger.debug("returned Contact Information for ctContactUid = " + ctContactDT.getCtContactUid());    
			ctContactVO.setcTContactDT(ctContactDT);
			NedssTimeLogger.generateTimeDiffEndLog(this.getClass().getCanonicalName(), "selectCTContact", start);
		} catch(Exception e){
			logger.error("Error in fetching DocumentDT by DocumentUID "+e.getMessage(), e) ; 
			throw new NEDSSSystemException(e.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);    
		}

		return ctContactDT;
	}
	
	
	public boolean ctContactExists (long ctContactUID) throws
    NEDSSSystemException
    {
		long start = NedssTimeLogger.generateTimeDiffStartLog(this.getClass().getCanonicalName(), "ctContactExists");
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		boolean returnValue = false;

		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_CT_CONTACT_UID);
			logger.debug("ctContactUID = " + ctContactUID);
			preparedStmt.setLong(1, ctContactUID);
			resultSet = preparedStmt.executeQuery();

			if (!resultSet.next())
			{
				returnValue = false;
			}
			else
			{
				ctContactUID = resultSet.getLong(1);
				returnValue = true;
			}
		}
		catch(SQLException sqlException)
		{
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "ctContactExists", sqlException);
				logger.fatal("SQLException while checking for an"
					+ " existing ct_contact's uid in CT_CONTACT_TABLE -> "
					+ ctContactUID, sqlException);
			throw new NEDSSDAOSysException( sqlException.getMessage());
		}
		catch(Exception exception)
		{
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "ctContactExists", exception);
				logger.fatal("Exception while getting dbConnection for checking for an"
					+ " existing ct_contact's uid -> " + ctContactUID, exception);
			throw new NEDSSDAOSysException( exception.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		NedssTimeLogger.generateTimeDiffEndLog(this.getClass().getCanonicalName(), "ctContactExists", start);
		return returnValue;
    }
	
	public void updateContactsForNewInvestigation(Long oldInvestigationUid,
			Long newInvestigationUid, Long revisionUid,
			String programArea, Long programJurisdictionOid)throws
    NEDSSSystemException{
		long start = NedssTimeLogger.generateTimeDiffStartLog(this.getClass().getCanonicalName(), "updateContactsForNewInvestigation");
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_CONTACT_FOR_NEW_INVESTIGATION);
			logger.debug("oldInvestigation = " + oldInvestigationUid);
			logger.debug("newInvestigation = " + newInvestigationUid);
			logger.debug("revisionUid = " + revisionUid);
			preparedStmt.setLong(1, revisionUid);
			preparedStmt.setLong(2, newInvestigationUid);
			preparedStmt.setString(3, programArea);
			preparedStmt.setLong(4, programJurisdictionOid);
			preparedStmt.setLong(5, oldInvestigationUid);
			preparedStmt.executeUpdate();

		}
		catch(SQLException sqlException)
		{
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "updateContactsForNewInvestigation", sqlException);
				logger.fatal("SQLException while updating Contacts for new investigation"
					+ newInvestigationUid, sqlException);
			throw new NEDSSDAOSysException( sqlException.getMessage());
		}
		catch(Exception exception)
		{
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "updateContactsForNewInvestigation", exception);
				logger.fatal("Exception while updating Contacts for new investigation " + newInvestigationUid, exception);
			throw new NEDSSDAOSysException( exception.getMessage());
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		NedssTimeLogger.generateTimeDiffEndLog(this.getClass().getCanonicalName(), "updateContactsForNewInvestigation", start);

	}
	public void updateContactsForSurvivingInvestigation(Long oldInvestigationUid,
			Long newInvestigationUid, Long revisionUid)throws
    NEDSSSystemException{
		long start = NedssTimeLogger.generateTimeDiffStartLog(this.getClass().getCanonicalName(), "updateContactsForNewInvestigation");
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_CONTACT_FOR_SURVIVING_INVESTIGATION);
			logger.debug("oldInvestigation = " + oldInvestigationUid);
			logger.debug("newInvestigation = " + newInvestigationUid);
			logger.debug("revisionUid = " + revisionUid);
			preparedStmt.setLong(1, revisionUid);
			preparedStmt.setLong(2, newInvestigationUid);
			preparedStmt.setLong(3, oldInvestigationUid);
			preparedStmt.executeUpdate();

		}
		catch(SQLException sqlException)
		{
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "updateContactsForSurvivingInvestigation", sqlException);
				logger.fatal("SQLException while updating Contacts for new investigation"
					+ newInvestigationUid, sqlException);
			throw new NEDSSDAOSysException( sqlException.getMessage());
		}
		catch(Exception exception)
		{
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "updateContactsForSurvivingInvestigation", exception);
				logger.fatal("Exception while updating Contacts for new investigation " + newInvestigationUid, exception);
			throw new NEDSSDAOSysException( exception.getMessage());
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		NedssTimeLogger.generateTimeDiffEndLog(this.getClass().getCanonicalName(), "updateContactsForSurvivingInvestigation", start);

	}
	public void updatePatientNamedByContactsForSurvivingInvestigation(Long oldInvestigationUid,
			Long newInvestigationUid)throws
    NEDSSSystemException{
		long start = NedssTimeLogger.generateTimeDiffStartLog(this.getClass().getCanonicalName(), "updatePatientNamedByContactsForSurvivingInvestigation");
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_PATIENT_NAMED_BY_CONTACT_FOR_SURVIVING_INVESTIGATION);
			logger.debug("oldInvestigation = " + oldInvestigationUid);
			logger.debug("newInvestigation = " + newInvestigationUid);
			preparedStmt.setLong(1, newInvestigationUid);
			preparedStmt.setLong(2, oldInvestigationUid);
			preparedStmt.executeUpdate();

		}
		catch(SQLException sqlException)
		{
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "updatePatientNamedByContactsForSurvivingInvestigation", sqlException);
				logger.fatal("SQLException while updating Contacts for surviving investigation"
					+ newInvestigationUid, sqlException);
			throw new NEDSSDAOSysException( sqlException.getMessage());
		}
		catch(Exception exception)
		{
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "updatePatientNamedByContactsForSurvivingInvestigation", exception);
				logger.fatal("Exception while updating Contacts for surviving investigation " + newInvestigationUid, exception);
			throw new NEDSSDAOSysException( exception.getMessage());
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		NedssTimeLogger.generateTimeDiffEndLog(this.getClass().getCanonicalName(), "updatePatientNamedByContactsForSurvivingInvestigation", start);

	}
	
	public void deLinkContactsForOldInvestigation(Long oldInvestigationUid)throws
    NEDSSSystemException{
		long start = NedssTimeLogger.generateTimeDiffStartLog(this.getClass().getCanonicalName(), "updateContactsForNewInvestigation");
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELINK_CONTACTS_FOR_OLD_INVESTIGATION);
			logger.debug("oldInvestigation = " + oldInvestigationUid);
			preparedStmt.setLong(1, oldInvestigationUid);
			preparedStmt.executeUpdate();

		}
		catch(SQLException sqlException)
		{
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "deLinkContactsForOldInvestigation", sqlException);
				logger.fatal("SQLException while updating Contacts for old investigation"
					+ oldInvestigationUid, sqlException);
			throw new NEDSSDAOSysException( sqlException.getMessage());
		}
		catch(Exception exception)
		{
			NedssTimeLogger.generateErrorLog(this.getClass().getCanonicalName(), "deLinkContactsForOldInvestigation", exception);
				logger.fatal("Exception while updating Contacts for old investigation " + oldInvestigationUid, exception);
			throw new NEDSSDAOSysException( exception.getMessage());
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		NedssTimeLogger.generateTimeDiffEndLog(this.getClass().getCanonicalName(), "deLinkContactsForOldInvestigation", start);

	}

	
	/**
	 * This method has been updated to bring only ACTIVE cases associated to a epi link id. This has been done to prevent nullpointer exception when we try update the 
	 * contact record. LOG_DEL records are not available in the contact records get method.
	 * @param epilinkId
	 * @return
	 * @throws NEDSSAppException
	 */
	public ArrayList<Object> getEpilinkDTCollection(String epilinkId) throws NEDSSAppException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStatement= null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> contactEpilinkDTColl = new ArrayList<Object>();

        String SELECT_CONTACT_EPILINK_ID="select CT_Contact_uid \"ctContactUid\" from CT_contact where ( subject_entity_epi_link_id=? OR contact_entity_epi_link_id=? ) and record_status_cd = 'ACTIVE'";
        try
        {
            dbConnection = getConnection();
            preparedStatement = dbConnection.prepareStatement(SELECT_CONTACT_EPILINK_ID);
            preparedStatement.setString(1, epilinkId);
            preparedStatement.setString(2, epilinkId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null)
            {
                resultSetMetaData = resultSet.getMetaData();
                contactEpilinkDTColl = (ArrayList<Object>) resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, CTContactDT.class, contactEpilinkDTColl);
            }
        }
        catch (SQLException e)
        {
            logger.fatal("CTContactDAOImpl.getEpilinkDTCollection SQLException while selecting " + "a CT_ContactDT; epilinkId = " + epilinkId, e);
            throw new NEDSSDAOSysException(e.getMessage());
        }
        catch (Exception exception)
        {
            logger.fatal("CTContactDAOImpl.getEpilinkDTCollection Exception while selecting " + "a CT_ContactDT; epilinkId = " + epilinkId, exception);
            throw new NEDSSDAOSysException(exception.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            releaseConnection(dbConnection);
        }
        logger.debug("returns a contactEpilinkDTColl collection with a size:"+ contactEpilinkDTColl.size());
        return contactEpilinkDTColl;
    }
    
	public void updateNamedAsContactDispoInvestigation(String dispositionCd, Timestamp fldFollowUpDispDate, Long phcUid)throws  NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		String UPDATE_DISPO_CONTACT_PHC_COLLECTION="UPDATE ct_contact SET DISPOSITION_CD= ?, DISPOSITION_DATE = ? WHERE record_status_cd='ACTIVE' AND CONTACT_ENTITY_PHC_UID=?";
		logger.debug("UPDATE_DISPO_CONTACT_PHC_COLLECTION = " + UPDATE_DISPO_CONTACT_PHC_COLLECTION);
		dbConnection = getConnection();
		try{
			preparedStmt = dbConnection.prepareStatement(UPDATE_DISPO_CONTACT_PHC_COLLECTION);
			int i = 1;
			preparedStmt.setString(i++, dispositionCd); //1
			if(fldFollowUpDispDate==null) {
				preparedStmt.setNull(i++, Types.TIMESTAMP);//2
			}else {
				preparedStmt.setTimestamp(i++, fldFollowUpDispDate);//2
			}
			preparedStmt.setLong(i++, phcUid.longValue());//3
			resultCount = preparedStmt.executeUpdate();
			logger.debug("Number of contact_records updated is  with dispositionCd-" + dispositionCd+ "and phcUid-"+phcUid +" is :"+ resultCount);
		} catch(SQLException sqlex) {
			logger.fatal("Sql query is "+UPDATE_DISPO_CONTACT_PHC_COLLECTION);
			logger.fatal("CTContactDAOImpl.updateContactRecordDisposition:SQLException while updateContactRecordDisposition with dispositionCd:" + dispositionCd+ "\nphcUid:"+phcUid, sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		} catch(Exception ex) {
			logger.fatal("Sql query is "+UPDATE_DISPO_CONTACT_PHC_COLLECTION);
			logger.fatal("CTContactDAOImpl.updateContactRecordDisposition:Error while uupdateContactRecordDisposition:" + dispositionCd+ "\nphcUid:"+phcUid, ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	public int countNamedAsContactDispoInvestigations(Long phcUid)throws  NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		String SELECT_DISPO_CONTACT_PHC_COLLECTION="SELECT COUNT(*) FROM ct_contact WHERE record_status_cd='ACTIVE' AND CONTACT_ENTITY_PHC_UID=?";
		logger.debug("SELECT_DISPO_CONTACT_PHC_COLLECTION = " + SELECT_DISPO_CONTACT_PHC_COLLECTION);
		dbConnection = getConnection();
		int numberOfAssociatedInvestigations=0;
		ResultSet resultSet = null;
		try{
			preparedStmt = dbConnection.prepareStatement(SELECT_DISPO_CONTACT_PHC_COLLECTION);
			int i = 1;
			preparedStmt.setLong(i++, phcUid.longValue());//1
			resultSet = preparedStmt.executeQuery();
			  if (resultSet.next())
	            {
				  numberOfAssociatedInvestigations = resultSet.getInt(1);
	            }
			logger.debug("numberOfAssociatedInvestigations is " +numberOfAssociatedInvestigations);
			
			
			logger.debug("Number of numberOfAssociatedInvestigations :"+ resultCount);
		} catch(SQLException sqlex) {
			logger.fatal("Sql query is "+SELECT_DISPO_CONTACT_PHC_COLLECTION);
			logger.fatal("CTContactDAOImpl.countNamedAsContactDispoInvestigations:SQLException while countNamedAsContactDispoInvestigations with nphcUid:"+phcUid, sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		} catch(Exception ex) {
			logger.fatal("Sql query is "+SELECT_DISPO_CONTACT_PHC_COLLECTION);
			logger.fatal("CTContactDAOImpl.countNamedAsContactDispoInvestigations:Error while countNamedAsContactDispoInvestigations with phcUid:"+phcUid, ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return numberOfAssociatedInvestigations;
	}
	
	public void updateContactsForChangeCondition(Long phcUid, String programArea, Long programJurisdictionOid)throws  NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		try{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_CONTACT_FOR_CHANGE_CONDITION);
			logger.debug("phcUid = " + phcUid);
			logger.debug("programAreaCode = " + programArea);
			logger.debug("programJurisdictionOid = " + programJurisdictionOid);
			preparedStmt.setString(1, programArea);
			preparedStmt.setLong(2, programJurisdictionOid);
			preparedStmt.setLong(3, phcUid);
			preparedStmt.executeUpdate();
		}catch(SQLException sqlException)
		{
			logger.fatal("SQLException while updating Contacts for change condition", sqlException);
		throw new NEDSSDAOSysException( sqlException.getMessage());
		}
		catch(Exception exception)
		{
			logger.fatal("Exception while updating Contacts for change condition ", exception);
			throw new NEDSSDAOSysException( exception.getMessage());
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
}
