package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseDAOImpl;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.helper.PSFClientDT;
import gov.cdc.nedss.nnd.helper.PSFIndexDT;
import gov.cdc.nedss.nnd.helper.PSFPartnerDT;
import gov.cdc.nedss.nnd.helper.PSFRiskDT;
import gov.cdc.nedss.nnd.helper.PSFSessionDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJBException;

/**
 * PartnerServicesDAOV3 - Retrieves data for the time range from each of the PSF tables.
 * @author Fatima Lopez Calzado
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: GDIT</p>
 * August 6th, 2018
 * @version 1
 */

public class PartnerServicesDAOV3 extends DAOBase {

	static final LogUtils logger = new LogUtils(PartnerServicesDAOV3.class.getName());
	
	/**
	 * getPSFSessionData: reads the session data from PSF_SESSION by invLocalId and stores it in a collection of psfSessionDT.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	/*public ArrayList<Object> getPSFSessionData(String invLocalId){
		
		PSFSessionDT psfSessionDT = new PSFSessionDT();
		
		ArrayList<Object> psfSessionColl = new ArrayList<Object>();
		String SELECT_PSF_SESSION=
				"SELECT localClientId \"localClientId\", "
				+"clientIdSTDMIS \"clientIdSTDMIS\", "
				+"clientIdPSID \"clientIdPSID\", "
				+"clientIdLocalId \"clientIdLocalId\", "
				+"clientFirstName \"clientFirstName\", "
				+"clientLastName \"clientLastName\", "
				+"clientDOB \"clientDOB\", "
				+"caseNumberPS \"caseNumberPS\", "
				+"caseNumberSTDMIS \"caseNumberSTDMIS\", "
				+"caseNumberLegacyId \"caseNumberLegacyId\", "
				+"caseNumberLocalId \"caseNumberLocalId\", "
				+"sessionDate \"sessionDate\", "
				+"siteId \"siteId\", "
				+"siteTypeValueCode \"siteTypeValueCode\", "
				+"careStatusAtInterview \"careStatusAtInterview\", "
				+"irLocalId \"irLocalId\", "
				+"irStatusCd \"irStatusCd\", "
				+"irAddTime \"irAddTime\", "
				+"irLastChgTime \"irLastChgTime\"";
				
		String SELECT_PARTNER_SERVICES_SQL = "FROM NBS_MSGOUTE..PSF_SESSION WHERE (caseNumberLocalId = ?) ";
		String SELECT_PARTNER_SERVICES_ORA = "FROM NBS_MSGOUTE.PSF_SESSION WHERE (caseNumberLocalId = ?) ";
		
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(invLocalId);
		
		String selectPartnerServices = "";
		boolean oracle = propertyUtil.getDatabaseServerType().equals(NEDSSConstants.ORACLE_ID);
		
		if (oracle)
			selectPartnerServices = SELECT_PSF_SESSION + SELECT_PARTNER_SERVICES_ORA;
		else 
			selectPartnerServices = SELECT_PSF_SESSION + SELECT_PARTNER_SERVICES_SQL;
		
		try {
			psfSessionColl = (ArrayList<Object>) preparedStmtMethod(psfSessionDT, paramList, selectPartnerServices, NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAO.getPSFSessionData for investigation local id ="
					+ invLocalId +": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		
		return psfSessionColl;
	}
	*/
	//actual date
	//success/failure
	//error message
	//mode
	//actual date
	//starts
	//ends
	
	String INSERT_ACTIVITY_LOG_SQL = "INSERT INTO dbo.MsgOut_activity_log values ('PSF XML', null, 'Active', ?, "+
			"?,COALESCE(?,''),'Populate PSF XML', 10000000, ?, ?, ?)";
	
	String INSERT_ACTIVITY_LOG_DETAIL_SQL = "INSERT INTO dbo.MsgOut_activity_log_detail values ((SELECT top(1)activity_log_uid from dbo.MsgOut_activity_log order by 1 desc), null, ?, 'PopulatePSFXML', "+
			"?,COALESCE(?,''), ?, ?)";
	
		
	public void writeXMLLog(String actualDate, String successFailure, String errorMessage, String mode, String startDate, String endDate){
		
		PSFSessionDT psfSessionDT = new PSFSessionDT();
		
	//	ArrayList<Object> psfSessionColl = new ArrayList<Object>();
	
		//if(errorMessage == null)
			//errorMessage = " ";
		
		ArrayList<Object> paramList = new ArrayList<Object>();
		ArrayList<Object> paramList2 = new ArrayList<Object>();
		
		int activityLogUid = 0;
		ArrayList<Object> activityLogUidArray=null;
		
		
		paramList.add(actualDate);
		paramList.add(successFailure);
		paramList.add(errorMessage);
		paramList.add(actualDate);
		paramList.add(startDate);
		paramList.add(endDate);
		
		String insertActivityLog = "";
	//	String selectActivityLogUid = "";
		
		insertActivityLog = INSERT_ACTIVITY_LOG_SQL;
		//	selectActivityLogUid = "SELECT top(1) activity_log_uid \"caseNumberPS\" from .MsgOut_activity_log order by 1 desc;";
		
		try {
			int resultCount = (Integer) preparedStmtMethod(null, paramList, insertActivityLog, NEDSSConstants.UPDATE, NEDSSConstants.MSGOUT);
			
		//	activityLogUidArray = (ArrayList<Object>) preparedStmtMethod(psfSessionDT, paramList2, selectActivityLogUid, NEDSSConstants.SELECT);
			
			
			
			
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAOC3.writeXMLLog ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	
//	return activityLogUid;
	
	}
	
	
	
public void writeXMLLogDetail(String recordType, String successFailure, String errorMessage, String startDate, String endDate){
		
		PSFSessionDT psfSessionDT = new PSFSessionDT();
		
	//	ArrayList<Object> psfSessionColl = new ArrayList<Object>();
	
		//if(errorMessage == null)
			//errorMessage = " ";
		
		ArrayList<Object> paramList = new ArrayList<Object>();
		ArrayList<Object> paramList2 = new ArrayList<Object>();
		
		int activityLogUid = 0;
		ArrayList<Object> activityLogUidArray=null;
		
		
		paramList.add(recordType);
		paramList.add(successFailure);
		paramList.add(errorMessage);
		paramList.add(startDate);
		paramList.add(endDate);
		
		String insertActivityLog = "";
	//	String selectActivityLogUid = "";
		
		insertActivityLog = INSERT_ACTIVITY_LOG_DETAIL_SQL;
		//	selectActivityLogUid = "SELECT top(1) activity_log_uid \"caseNumberPS\" from .MsgOut_activity_log order by 1 desc;";
		
		try {
			int resultCount = (Integer) preparedStmtMethod(null, paramList, insertActivityLog, NEDSSConstants.UPDATE, NEDSSConstants.MSGOUT);
			
		//	activityLogUidArray = (ArrayList<Object>) preparedStmtMethod(psfSessionDT, paramList2, selectActivityLogUid, NEDSSConstants.SELECT);
			
			
			
			
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAOC3.writeXMLLog ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	
//	return activityLogUid;
	
	}
	
	
	/**
	 * getPSFSessionData: reads the session data from PSF_SESSION and stores it in a collection of psfSessionDT.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	
	public ArrayList<Object> getPSFSessionDataByLocalClientIdAndCaseNumberPS(String localClientIdCaseNumberPSList){
		
		PSFSessionDT psfSessionDT = new PSFSessionDT();
		
		ArrayList<Object> psfSessionColl = new ArrayList<Object>();
		String SELECT_PSF_SESSION=//"SELECT agencyId \"agencyID\", "
				"SELECT localClientId \"localClientId\", "
				+"clientIdSTDMIS \"clientIdSTDMIS\", "
				+"clientIdPSID \"clientIdPSID\", "
				+"clientIdLocalId \"clientIdLocalId\", "
				+"clientFirstName \"clientFirstName\", "
				+"clientLastName \"clientLastName\", "
				+"clientDOB \"clientDOB\", "
				+"caseNumberPS \"caseNumberPS\", "
				+"caseNumberSTDMIS \"caseNumberSTDMIS\", "
				+"caseNumberLegacyId \"caseNumberLegacyId\", "
				+"caseNumberLocalId \"caseNumberLocalId\", "
				+"sessionDate \"sessionDate\", "
				+"siteId \"siteId\", "
				+"siteTypeValueCode \"siteTypeValueCode\", "
				+"careStatusAtInterview \"careStatusAtInterview\", "
				+"irLocalId \"irLocalId\", "
				+"irStatusCd \"irStatusCd\", "
				+"irAddTime \"irAddTime\", "
				+"invLocalId \"invLocalId\", "
				+"irLastChgTime \"irLastChgTime\"";
				
		String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_SESSION WHERE ("+localClientIdCaseNumberPSList+") and irStatusCd <> 'LOG_DEL'";
		
		ArrayList<Object> paramList = new ArrayList<Object>();

		String selectPartnerServices = SELECT_PSF_SESSION + SELECT_PARTNER_SERVICES_SQL;
		
		try {
			psfSessionColl = (ArrayList<Object>) preparedStmtMethod(psfSessionDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAO.getPSFSessionDataByLocalClientIdAndCaseNumberPS for localClientIdCaseNumberPSList ="
					+ localClientIdCaseNumberPSList +": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		
		return psfSessionColl;
	}


	/**
	 * getPSFSessionDataByLocalClientIdList: returns an array of PSFSessionDT by localClientIdList
	 * @param localClientIdList
	 * @return
	 */
	public ArrayList<Object> getPSFSessionDataByLocalClientIdList(String localClientIdList){
		
		PSFSessionDT psfSessionDT = new PSFSessionDT();
		
		ArrayList<Object> psfSessionColl = new ArrayList<Object>();
		String SELECT_PSF_SESSION=
				"SELECT localClientId \"localClientId\", "
				+"clientIdSTDMIS \"clientIdSTDMIS\", "
				+"clientIdPSID \"clientIdPSID\", "
				+"clientIdLocalId \"clientIdLocalId\", "
				+"clientFirstName \"clientFirstName\", "
				+"clientLastName \"clientLastName\", "
				+"clientDOB \"clientDOB\", "
				+"caseNumberPS \"caseNumberPS\", "
				+"caseNumberSTDMIS \"caseNumberSTDMIS\", "
				+"caseNumberLegacyId \"caseNumberLegacyId\", "
				+"caseNumberLocalId \"caseNumberLocalId\", "
				+"sessionDate \"sessionDate\", "
				+"siteId \"siteId\", "
				+"siteTypeValueCode \"siteTypeValueCode\", "
				+"careStatusAtInterview \"careStatusAtInterview\", "
				+"irLocalId \"irLocalId\", "
				+"irStatusCd \"irStatusCd\", "
				+"irAddTime \"irAddTime\", "
				+"invLocalId \"invLocalId\", "
				+"irLastChgTime \"irLastChgTime\"";
				
		String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_SESSION WHERE localClientId in ("+localClientIdList+") and irStatusCd <> 'LOG_DEL'";
		
		ArrayList<Object> paramList = new ArrayList<Object>();
		
		String selectPartnerServices = SELECT_PSF_SESSION + SELECT_PARTNER_SERVICES_SQL;
		
		try {
			psfSessionColl = (ArrayList<Object>) preparedStmtMethod(psfSessionDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAO.getPSFSessionDataByLocalClientIdList for localClientIdList ="
					+ localClientIdList +": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		
		return psfSessionColl;
	}

	
	/**
	 * getPSFSessionDataByLocalClientIdAndCaseNumberPSOOJ: returns an array of PSFSessionDT by localClientIdList and caseNumberPS is NULL or blank (OOJ)
	 * @param localClientIdCaseNumberPSList
	 * @return
	 */
	public ArrayList<Object> getPSFSessionDataByLocalClientIdOOJ(String localClientIdCaseNumberPSList){
		
		PSFSessionDT psfSessionDT = new PSFSessionDT();
		
		ArrayList<Object> psfSessionColl = new ArrayList<Object>();
		String SELECT_PSF_SESSION=
				"SELECT localClientId \"localClientId\", "
				+"clientIdSTDMIS \"clientIdSTDMIS\", "
				+"clientIdPSID \"clientIdPSID\", "
				+"clientIdLocalId \"clientIdLocalId\", "
				+"clientFirstName \"clientFirstName\", "
				+"clientLastName \"clientLastName\", "
				+"clientDOB \"clientDOB\", "
				+"caseNumberPS \"caseNumberPS\", "
				+"caseNumberSTDMIS \"caseNumberSTDMIS\", "
				+"caseNumberLegacyId \"caseNumberLegacyId\", "
				+"caseNumberLocalId \"caseNumberLocalId\", "
				+"sessionDate \"sessionDate\", "
				+"siteId \"siteId\", "
				+"siteTypeValueCode \"siteTypeValueCode\", "
				+"careStatusAtInterview \"careStatusAtInterview\", "
				+"irLocalId \"irLocalId\", "
				+"irStatusCd \"irStatusCd\", "
				+"irAddTime \"irAddTime\", "
				+"invLocalId \"invLocalId\", "
				+"irLastChgTime \"irLastChgTime\"";
				
		String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_SESSION WHERE localClientId in ("+localClientIdCaseNumberPSList+") and (caseNumberPS is NULL or caseNumberPS ='') and irStatusCd <> 'LOG_DEL'";
		
		ArrayList<Object> paramList = new ArrayList<Object>();
		
		String selectPartnerServices = SELECT_PSF_SESSION + SELECT_PARTNER_SERVICES_SQL;
		
		try {
			psfSessionColl = (ArrayList<Object>) preparedStmtMethod(psfSessionDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAO.getPSFSessionDataByLocalClientIdOOJ for localClientIdCaseNumberPSList ="
					+ localClientIdCaseNumberPSList +": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		
		return psfSessionColl;
	}
	
	
	/**
	 * getLocalClientIdFromPartner: returns a list of localClientId from PSF_PARTNER where the caseNumberPS is in the caseNumberPSList list
	 * @param caseNumberPSList
	 * @return
	 */
	
	public ArrayList<Object> getLocalClientIdFromPartner(String caseNumberPSList){
		

		PSFPartnerDT psfPartnerDT = new PSFPartnerDT();
		
		ArrayList<Object> psfPartnerColl = new ArrayList<Object>();
		String SELECT_PSF_PARTNER="SELECT "
				+"localClientId \"localClientId\" ";

		String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_PARTNER WHERE (caseNumberPS in ("+caseNumberPSList+")) AND (patientStatusCd <> 'LOG_DEL' and (crStatusCd is NULL or crStatusCd ='ACTIVE' or crStatusCd ='OPEN'))";
		
		ArrayList<Object> paramList = new ArrayList<Object>();
			
		String selectPartnerServices = "";
		selectPartnerServices = SELECT_PSF_PARTNER + SELECT_PARTNER_SERVICES_SQL;
		
		try {
			psfPartnerColl = (ArrayList<Object>) preparedStmtMethod(psfPartnerDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAO.getLocalClientIdFromPartner for caseNumberPSList ="+caseNumberPSList+": ERROR = "+ ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		
		return psfPartnerColl;		
	}
	
	
	/**
	 * getLocalClientIdFromPartnerOOJ: returns a localClientId list from PSF_PARTNER that are OOJ and are within the date range
	 * @param startingDateStr
	 * @param endingDateStr
	 * @return
	 */
	
	public ArrayList<Object> getLocalClientIdFromPartnerOOJ(String startingDateStr, String endingDateStr){
		

		PSFPartnerDT psfPartnerDT = new PSFPartnerDT();
		
		ArrayList<Object> psfPartnerColl = new ArrayList<Object>();
		String SELECT_PSF_PARTNER="SELECT "
				+"localClientId \"localClientId\" ";
				
		
		String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_PARTNER WHERE caseNumberPS like 'OOJ%' and (partnerLastChgDt  BETWEEN ? AND ?) AND (patientStatusCd <> 'LOG_DEL' and (crStatusCd is NULL or crStatusCd ='ACTIVE' or crStatusCd ='OPEN'))";
		
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(startingDateStr);
		paramList.add(endingDateStr);
		
		
		
		String selectPartnerServices = "";
		selectPartnerServices = SELECT_PSF_PARTNER + SELECT_PARTNER_SERVICES_SQL;
		
		try {
			psfPartnerColl = (ArrayList<Object>) preparedStmtMethod(psfPartnerDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAO.getLocalClientIdFromPartnerOOJ for date range  ="
						+ startingDateStr + " to " + endingDateStr + ": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		
		return psfPartnerColl;		
	}
	
	
	/**
	 * getPSFIndexData: reads the index data from PSF_INDEX and stores it in a collection of psfIndexDT.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	
	public ArrayList<Object> getPSFIndexData(String startDate, String endDate){
			
			PSFIndexDT psfIndexDT = new PSFIndexDT();
			
			ArrayList<Object> psfIndexColl = new ArrayList<Object>();
			String SELECT_PSF_SESSION="SELECT agencyId \"agencyId\", "
					+"localClientId \"localClientId\", "
					+"clientIdSTDMIS \"clientIdSTDMIS\", "
					+"clientIdPSID \"clientIdPSID\", "
					+"clientIdLocalId \"clientIdLocalId\", "
					+"clientFirstName \"clientFirstName\", "
					+"clientLastName \"clientLastName\", "
					+"clientDOB \"clientDOB\", "
					+"caseNumberPS \"caseNumberPS\", "
					+"caseNumberSTDMIS \"caseNumberSTDMIS\", "
					+"caseNumberLegacyId \"caseNumberLegacyId\", "
					+"caseNumberLocalId \"caseNumberLocalId\", "
					+"indexDateDemographicsCollected \"indexDateDemographicsCollected\", "
					+"attemptToLocateOutcome \"attemptToLocateOutcome\", "
					+"reasonForUnsuccessfulAttempt \"reasonForUnsuccessfulAttempt\", "
					+"enrollmentStatus \"enrollmentStatus\", "
					+"totalNumberOfPartnersClaimed \"totalNumberOfPartnersClaimed\", "
					+"totalNumberOfNamedPartners \"totalNumberofNamedPartners\", "
					+"caseOpenDate \"caseOpenDate\", "
					+"caseCloseDate \"caseCloseDate\", "
					+"clientHIVStatus \"clientHIVStatus\", "
					+"careStatusAtCaseClose \"careStatusAtCaseClose\", "
					+"patientAddTime \"patientAddTime\", "
					+"patientLastChgTime \"patientLastChgTime\", "
					+"patientStatusCd \"patientStatusCd\", "
					+"invAddTime \"invAddtime\", "
					+"invLastChgTime \"invLastChgTime\", "
					+"invStatusCd \"invStatusCd\" ";
					
					
			String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_INDEX WHERE (indexLastChgDt  BETWEEN ? AND ?) AND invStatusCD <> 'LOG_DEL'";// or (invLastChgTime BETWEEN ? AND ?)) ";
			
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(startDate);
			paramList.add(endDate);
			
			
			String selectPartnerServices = "";
			selectPartnerServices = SELECT_PSF_SESSION + SELECT_PARTNER_SERVICES_SQL;
			
			try {
				psfIndexColl = (ArrayList<Object>) preparedStmtMethod(psfIndexDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
			} catch (Exception ex) {
				logger
				.fatal("Exception in PartnerServicesDAO.getPSFIndexData for date range  ="
						+ startDate + " to " + endDate + ": ERROR = " + ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			
			return psfIndexColl;
	}
	

	/**
	 * getPSFPartnerData: reads the partner data from PSF_PARTNER by clientLocalId and stores it in a collection of psfPartnerDT.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	/*
	public ArrayList<Object> getPSFPartnerData(String clientLocalId){
			
			PSFPartnerDT psfPartnerDT = new PSFPartnerDT();
			
			ArrayList<Object> psfPartnerColl = new ArrayList<Object>();
			String SELECT_PSF_PARTNER="SELECT agencyId \"agencyId\", "
					+"localClientId \"localClientId\", "
					+"clientIdSTDMIS \"clientIdSTDMIS\", "
					+"clientIdPSID \"clientIdPSID\", "
					+"clientIdLocalId \"clientIdLocalId\", "
					+"clientFirstName \"clientFirstName\", "
					+"clientLastName \"clientLastName\", "
					+"clientDOB \"clientDOB\", "
					+"caseNumberPS \"caseNumberPS\", "
					+"caseNumberSTDMIS \"caseNumberSTDMIS\", "
					+"caseNumberLegacyId \"caseNumberLegacyId\", "
					+"caseNumberLocalId \"caseNumberLocalId\", "
					+"partnerType \"partnerType\", "
					+"attemptToLocateOutcome \"attemptToLocateOutcome\", "
					+"reasonForUnsuccessfulAttempt \"reasonForUnsuccessfulAttempt\", "
					+"enrollmentStatus \"enrollmentStatus\", "
					+"partnerNotifiability \"partnerNotifiability\", "
					+"actualNotificationMethod \"actualNotificationMethod\", "
					+"previousHivTestValueCode \"previousHivTestValueCode\", "
					+"previousHIVTestResult \"previousHIVTestResult\", "
					+"HIVTestPerformed \"hivTestPerformed\", "
					+"sampleDate \"sampleDate\", "
					+"HIVTestResult \"hivTestResult\", "
					+"provisionOfResultValueCode \"provisionOfResultValueCode\", "
					+"syphilisTest \"syphilisTest\", "
					+"syphilisTestResult \"syphilisTestResult\", "
					+"currentHIVMedicalCareStatus \"currentHIVMedicalCareStatus\", "
					+"firstMedicalCareAppointmentDate \"firstMedicalCareAppointmentDate\", "
					+"currentlyOnPrEP \"currentlyOnPrEP\", "
					+"referredToPrEP \"referredToPrEP\", "
					+"patientAddTime \"patientAddTime\", "
					+"patientLastChgTime \"patientLastChgTime\", "
					+"patientStatusCd \"patientStatusCd\", "
					+"invAddtime \"invAddtime\", "
					+"invLastChgTime \"invLastChgTime\", "
					+"invStatusCd \"invStatusCd\", "
					+"invLocalId \"invLocalId\", "
					+"crAddTime \"crAddTime\", "
					+"crLastChgTime \"crLastChgTime\", "
					+"crStatusCd \"crStatusCd\", "
					+"crLocalId \"crLocalId\" ";
					
			String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_PARTNER WHERE ((clientIdLocalId = ?) AND (patientStatusCd <> 'LOG_DEL' and crStatusCd <> 'LOG_DEL'))";
			String SELECT_PARTNER_SERVICES_ORA = "FROM PSF_PARTNER WHERE ((clientIdLocalId = ?) AND (patientStatusCd <> 'LOG_DEL' and crStatusCd <> 'LOG_DEL'))";
			
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(clientLocalId);
			
			
			String selectPartnerServices = "";
			boolean oracle = propertyUtil.getDatabaseServerType().equals(NEDSSConstants.ORACLE_ID);
			
			if (oracle)
				selectPartnerServices = SELECT_PSF_PARTNER + SELECT_PARTNER_SERVICES_ORA;
			else 
				selectPartnerServices = SELECT_PSF_PARTNER + SELECT_PARTNER_SERVICES_SQL;
			
			try {
				psfPartnerColl = (ArrayList<Object>) preparedStmtMethod(psfPartnerDT, paramList, selectPartnerServices, NEDSSConstants.SELECT);
			} catch (Exception ex) {
				logger
				.fatal("Exception in PartnerServicesDAO.getPSFPartnerData for clientLocalId in "
						+ clientLocalId +": ERROR = " + ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			
			return psfPartnerColl;
	}
	
	*/
	/**
	 * getPSFRiskData: gets all the records from PSF_RISK table which localClientId is in the localClientIdList list
	 * @param clientLocalId
	 * @return
	 */
	
	public ArrayList<Object> getPSFRiskData(String localClientIdList){
		
		PSFRiskDT psRiskDT = new PSFRiskDT();
		
		ArrayList<Object> psfRiskColl = new ArrayList<Object>();
		String SELECT_PSF_RISK=
				"SELECT localClientId \"localClientId\", "
				+"clientIdSTDMIS \"clientIdSTDMIS\", "
				+"clientIdPSID \"clientIdPSID\", "
				+"clientIdLocalId \"clientIdLocalId\", "
				+"clientFirstName \"clientFirstName\", "
				+"clientLastName \"clientLastName\", "
				+"clientDOB \"clientDOB\", "
				+"invLocalId \"invLocalId\", "
				+"dateCollectedForRiskProfile \"dateCollectedForRiskProfile\", "
				+"withMale \"withMale\", "
				+"withFemale \"withFemale\", "
				+"withTransgender \"withTransgender\", "
				+"vaginalOrAnalSexWithoutCondomP \"vaginalOrAnalSexWithoutCondomPS\", "
				+"injectionDrugUse \"injectionDrugUse\", "
				+"invAddTime \"invAddtime\", "
				+"invLastChgTime \"invLastChgTime\", "
				+"invStatusCd \"invStatusCd\" ";
		
		String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_RISK WHERE (localClientId in ("+localClientIdList+") and invStatusCd<>'LOG_DEL') ";
		ArrayList<Object> paramList = new ArrayList<Object>();

		String selectPartnerServices = "";
		selectPartnerServices = SELECT_PSF_RISK + SELECT_PARTNER_SERVICES_SQL;
		
		try {
			psfRiskColl = (ArrayList<Object>) preparedStmtMethod(psRiskDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAO.getPSFRiskData for localClientId in "
					+ localClientIdList +": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		
		return psfRiskColl;
		
	}
	
	
	/**
	 * getPSFAsAnIndexData: returns all the records from PSF_Index which localClientId is in the localClientIdList list.
	 * @param clientLocalId
	 * @return
	 */
	
	public ArrayList<Object> getPSFAsAnIndexData(String localClientIdList, String startingDateStr, String endingDateStr){
		
		PSFIndexDT psfIndexDT = new PSFIndexDT();
		
		ArrayList<Object> psfAsAnIndexColl = new ArrayList<Object>();
		String SELECT_PSF_AS_AN_INDEX=
				"SELECT agencyId \"agencyId\", "
						+"localClientId \"localClientId\", "
						+"clientIdSTDMIS \"clientIdSTDMIS\", "
						+"clientIdPSID \"clientIdPSID\", "
						+"clientIdLocalId \"clientIdLocalId\", "
						+"clientFirstName \"clientFirstName\", "
						+"clientLastName \"clientLastName\", "
						+"clientDOB \"clientDOB\", "
						+"caseNumberPS \"caseNumberPS\", "
						+"caseNumberEarliestPS \"caseNumberEarliestPS\", "
						+"caseNumberSTDMIS \"caseNumberSTDMIS\", "
						+"caseNumberLegacyId \"caseNumberLegacyId\", "
						+"caseNumberLocalId \"caseNumberLocalId\", "
						+"indexDateDemographicsCollected \"indexDateDemographicsCollected\", "
						+"attemptToLocateOutcome \"attemptToLocateOutcome\", "
						+"reasonForUnsuccessfulAttempt \"reasonForUnsuccessfulAttempt\", "
						+"enrollmentStatus \"enrollmentStatus\", "
						+"totalNumberOfPartnersClaimed \"totalNumberOfPartnersClaimed\", "
						+"totalNumberOfNamedPartners \"totalNumberofNamedPartners\", "
						+"caseOpenDate \"caseOpenDate\", "
						+"caseCloseDate \"caseCloseDate\", "
						+"clientHIVStatus \"clientHIVStatus\", "
						+"careStatusAtCaseClose \"careStatusAtCaseClose\", "
						+"patientAddTime \"patientAddTime\", "
						+"patientLastChgTime \"patientLastChgTime\", "
						+"patientStatusCd \"patientStatusCd\", "
						+"invAddTime \"invAddtime\", "
						+"invLastChgTime \"invLastChgTime\", "
						+"invStatusCd \"invStatusCd\" ";
						
		
		String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_INDEX WHERE (localClientId in ("+localClientIdList+") AND invStatusCD <> 'LOG_DEL') and (indexLastChgDt  BETWEEN '"+startingDateStr+"' AND '"+endingDateStr+"')";
		
		ArrayList<Object> paramList = new ArrayList<Object>();
		
		String selectPartnerServices = "";
		selectPartnerServices = SELECT_PSF_AS_AN_INDEX + SELECT_PARTNER_SERVICES_SQL;
		
		try {
			psfAsAnIndexColl = (ArrayList<Object>) preparedStmtMethod(psfIndexDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAO.getPSFAsAnIndexData for localClientId in "
					+ localClientIdList +": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		
		return psfAsAnIndexColl;
		
	}

	
	/**
	   * getPublicHealthCaseDTFromListOfPHCLocalId: used to create the notification of the investigations in PSF
	   * @param listPhc
	   * @return
	   */
	
	private static final String SELECT_PUBLIC_HEALTH_CASE_FROM_LOCAL_ID_LIST = "SELECT public_health_case_uid \"publicHealthCaseUid\","
			+ " activity_duration_amt \"activityDurationAmt\","
			+ " activity_duration_unit_cd \"activityDurationUnitCd\"," + " activity_from_time \"activityFromTime\","
			+ " activity_to_time \"activityToTime\"," + " add_reason_cd \"addReasonCd\"," + " add_time \"addTime\","
			+ " add_user_id \"addUserId\"," + " case_class_cd \"caseClassCd\"," + " cd \"cd\","
			+ " cd_desc_txt \"cdDescTxt\"," + " cd_system_cd \"cdSystemCd\","
			+ " cd_system_desc_txt \"cdSystemDescTxt\"," + " confidentiality_cd \"confidentialityCd\","
			+ " confidentiality_desc_txt \"confidentialityDescTxt\"," + " detection_method_cd \"detectionMethodCd\","
			+ " detection_method_desc_txt \"detectionMethodDescTxt\"," + " disease_imported_cd \"diseaseImportedCd\","
			+ " disease_imported_desc_txt \"diseaseImportedDescTxt\","
			+ " effective_duration_amt \"effectiveDurationAmt\","
			+ " effective_duration_unit_cd \"effectiveDurationUnitCd\"," + " effective_from_time \"effectiveFromTime\","
			+ " effective_to_time \"effectiveToTime\", " + " group_case_cnt \"groupCaseCnt\","
			+ " investigation_status_cd \"investigationStatusCd\"," + " jurisdiction_cd \"jurisdictionCd\","
			+ " last_chg_reason_cd \"lastChgReasonCd\"," + " last_chg_time \"lastChgTime\","
			+ " last_chg_user_id \"lastChgUserId\"," + " local_id \"localId\"," + " mmwr_week \"mmwrWeek\","
			+ " mmwr_year \"mmwrYear\","
			// +" org_access_permis \"orgAccessPermis\","
			+ " outbreak_name \"outbreakName\"," + " outbreak_from_time \"outbreakFromTime\","
			+ " outbreak_ind \"outbreakInd\"," + " outbreak_to_time \"outbreakToTime\"," + " outcome_cd \"outcomeCd\","
			+ " patient_group_id \"patientGroupId\","
			// +" prog_area_access_permis \"progAreaAccessPermis\","
			+ " prog_area_cd \"progAreaCd\"," + " record_status_cd \"recordStatusCd\","
			+ " record_status_time \"recordStatusTime\"," + " repeat_nbr \"repeatNbr\"," + " rpt_cnty_cd \"rptCntyCd\","
			+ " status_cd \"statusCd\"," + " status_time \"statusTime\","
			+ " transmission_mode_cd \"transmissionModeCd\","
			+ " transmission_mode_desc_txt \"transmissionModeDescTxt\"," + " txt \"txt\","
			+ " user_affiliation_txt \"userAffiliationTxt\", " + "pat_age_at_onset \"patAgeAtOnset\","
			+ " pat_age_at_onset_unit_cd \"patAgeAtOnsetUnitCd\"," + " rpt_form_cmplt_time \"rptFormCmpltTime\","
			+ " rpt_source_cd \"rptSourceCd\"," + " rpt_source_cd_desc_txt \"rptSourceCdDescTxt\","
			+ " rpt_to_county_time \"rptToCountyTime\"," + " rpt_to_state_time \"rptToStateTime\","
			+ " diagnosis_time \"diagnosisTime\" ," + " program_jurisdiction_oid \"programJurisdictionOid\" ,"
			+ " shared_ind \"sharedInd\" ," + " version_ctrl_nbr \"versionCtrlNbr\" , "
			+ " case_type_cd \"caseTypeCd\" , " + " investigator_assigned_time \"investigatorAssignedTime\" ,"
			+ " hospitalized_ind_cd \"hospitalizedIndCd\" ," + " hospitalized_admin_time \"hospitalizedAdminTime\" ,"
			+ " hospitalized_discharge_time \"hospitalizedDischargeTime\" ,"
			+ " hospitalized_duration_amt \"hospitalizedDurationAmt\" ," + " pregnant_ind_cd \"pregnantIndCd\" ,"
			// +" die_from_illness_ind_cd \"dieFromIllnessIndCD\" ,"
			+ " day_care_ind_cd \"dayCareIndCd\" ," + " food_handler_ind_cd \"foodHandlerIndCd\" ,"
			+ " imported_country_cd \"importedCountryCd\" ," + " imported_state_cd \"importedStateCd\" ,"
			+ " imported_city_desc_txt \"importedCityDescTxt\" ," + " imported_county_cd \"importedCountyCd\" ,"
			+ " deceased_time \"deceasedTime\" ," + " count_interval_cd \"countIntervalCd\","
			+ " priority_cd \"priorityCd\"," + " infectious_from_date \"infectiousFromDate\","
			+ " infectious_to_date \"infectiousToDate\"," + " contact_inv_status_cd \"contactInvStatus\","
			+ " contact_inv_txt \"contactInvTxt\", " + " referral_basis_cd \"referralBasisCd\", "
			+ " curr_process_state_cd \"currProcessStateCd\", " + " inv_priority_cd \"invPriorityCd\", "
			+ " coinfection_id \"coinfectionId\" " + " FROM " ;
	
	/**
	 * getPublicHealthCaseDTFromListOfPHCLocalId: executes a query that returns the collection of publicHealthCaseDT which local_id is in the listPhc parameter
	 * @param listPhc
	 * @return
	 */
	  public Collection<Object> getPublicHealthCaseDTFromListOfPHCLocalId (String listPhc){
		  
			PublicHealthCaseDT phcDT = new PublicHealthCaseDT();
			
			Collection<Object> psfAsAPartnerColl = new ArrayList<Object>();
			String SELECT_PSF_PARTNER=SELECT_PUBLIC_HEALTH_CASE_FROM_LOCAL_ID_LIST;
							
			
			String SELECT_PARTNER_SERVICES_SQL = " NBS_ODSE..public_health_case WHERE local_id in ("+listPhc+")";
			ArrayList<Object> paramList = new ArrayList<Object>();
			
			String selectPartnerServices = "";
			selectPartnerServices = SELECT_PSF_PARTNER + SELECT_PARTNER_SERVICES_SQL;
			
			try {
				psfAsAPartnerColl = (Collection<Object>) preparedStmtMethod(phcDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
			} catch (Exception ex) {
				logger
				.fatal("Exception in PartnerServicesDAO.getPublicHealthCaseDTFromListOfPHCLocalId for local_id in "
						+ listPhc +": ERROR = " + ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			
			return psfAsAPartnerColl;
		  
	  }
	  
	  
	  public ArrayList<Object> getActRelationshipDTFromListOfPHCLocalId (String listPhc){
		  
		  

			PublicHealthCaseDT phcDT = new PublicHealthCaseDT();
			
			ArrayList<Object> psfAsAPartnerColl = new ArrayList<Object>();
			String SELECT_PSF_PARTNER_SQL="select distinct CONCAT(CONCAT(public_health_case.local_id,'-'),notification.notification_uid) \"localId\" from NBS_ODSE..public_health_case inner join NBS_ODSE..act_relationship"+
			" on Public_health_case_uid = target_act_uid"+
			" inner join NBS_ODSE..notification on"+
			" source_act_uid = notification_uid"+
			" where  notification.cd ='PSF_NOTF' and act_relationship.type_cd = 'Notification'"+
			" and public_health_case.local_id in ("+listPhc+")";
							
			
			ArrayList<Object> paramList = new ArrayList<Object>();
			
			String selectPartnerServices = "";
			selectPartnerServices = SELECT_PSF_PARTNER_SQL;
			
			try {
				psfAsAPartnerColl = (ArrayList<Object>) preparedStmtMethod(phcDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
			} catch (Exception ex) {
				logger
				.fatal("Exception in PartnerServicesDAO.getPublicHealthCaseDTFromListOfPHCLocalId for localClientId in "
						+ listPhc +": ERROR = " + ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			
			return psfAsAPartnerColl;
		  
	  }
	  
	  
	/**
	 * getPSFAsAPartnerData: returns all the records from PSF_Partner table which localClientId is in the localClientIdList list
	 * @param clientLocalId
	 * @return
	 */
	
	public ArrayList<Object> getPSFAsAPartnerData(String localClientIdList){
	
	PSFPartnerDT psfPartnerDT = new PSFPartnerDT();
	
	ArrayList<Object> psfAsAPartnerColl = new ArrayList<Object>();
	String SELECT_PSF_PARTNER="SELECT agencyId \"agencyId\", "
			+"localClientId \"localClientId\", "
			+"clientIdSTDMIS \"clientIdSTDMIS\", "
			+"clientIdPSID \"clientIdPSID\", "
			+"clientIdLocalId \"clientIdLocalId\", "
			+"clientFirstName \"clientFirstName\", "
			+"clientLastName \"clientLastName\", "
			+"clientDOB \"clientDOB\", "
			+"caseNumberPS \"caseNumberPS\", "
			+"caseNumberEarliestPS \"caseNumberEarliestPS\", "
			+"caseNumberSTDMIS \"caseNumberSTDMIS\", "
			+"caseNumberLegacyId \"caseNumberLegacyId\", "
			+"caseNumberLocalId \"caseNumberLocalId\", "
			+"partnerDateDemographicsCollected \"partnerDateDemographicsCollected\", "
			+"partnerType \"partnerType\", "
			+"attemptToLocateOutcome \"attemptToLocateOutcome\", "
			+"reasonForUnsuccessfulAttempt \"reasonForUnsuccessfulAttempt\", "
			+"enrollmentStatus \"enrollmentStatus\", "
			+"partnerNotifiability \"partnerNotifiability\", "
			+"actualNotificationMethod \"actualNotificationMethod\", "
			+"previousHivTestValueCode \"previousHivTestValueCode\", "
			+"previousHIVTestResult \"previousHIVTestResult\", "
			+"HIVTestPerformed \"hivTestPerformed\", "
			+"sampleDate \"sampleDate\", "
			+"HIVTestResult \"hivTestResult\", "
			+"provisionOfResultValueCode \"provisionOfResultValueCode\", "
			+"syphilisTest \"syphilisTest\", "
			+"syphilisTestResult \"syphilisTestResult\", "
			+"currentHIVMedicalCareStatus \"currentHIVMedicalCareStatus\", "
			+"firstMedicalCareAppointmentDate \"firstMedicalCareAppointmentDate\", "
			+"currentlyOnPrEP \"currentlyOnPrEP\", "
			+"referredToPrEP \"referredToPrEP\", "
			+"patientAddTime \"patientAddTime\", "
			+"patientLastChgTime \"patientLastChgTime\", "
			+"patientStatusCd \"patientStatusCd\", "
			+"invAddtime \"invAddtime\", "
			+"invLastChgTime \"invLastChgTime\", "
			+"invStatusCd \"invStatusCd\", "
			+"invLocalId \"invLocalId\", "
			+"crAddTime \"crAddTime\", "
			+"crLastChgTime \"crLastChgTime\", "
			+"crStatusCd \"crStatusCd\", "
			+"crLocalId \"crLocalId\", "
			+"partnerLastChgDt \"partnerLastChgDt\" ";
					
	
	String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_PARTNER WHERE (localClientId in ("+localClientIdList+") AND (patientStatusCd <> 'LOG_DEL' and (crStatusCd is NULL or crStatusCd ='ACTIVE' or crStatusCd ='OPEN')))";
	
	ArrayList<Object> paramList = new ArrayList<Object>();
	
	String selectPartnerServices = "";
	selectPartnerServices = SELECT_PSF_PARTNER + SELECT_PARTNER_SERVICES_SQL;
	
	try {
		psfAsAPartnerColl = (ArrayList<Object>) preparedStmtMethod(psfPartnerDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
	} catch (Exception ex) {
		logger
		.fatal("Exception in PartnerServicesDAO.getPSFAsAPartnerData for localClientId in "
				+ localClientIdList +": ERROR = " + ex);
		throw new NEDSSSystemException(ex.toString(), ex);
	}
	
	return psfAsAPartnerColl;
	
}
	
	
	/**
	 * getPSFAsAPartnerDataByCaseNumberPS: returns and arrayList of PSFPartnerDT by caseNumberPSList
	 * @param caseNumberPSList
	 * @return
	 */
	
	public ArrayList<Object> getPSFAsAPartnerDataByCaseNumberPS(String caseNumberPSList){
		
		PSFPartnerDT psfPartnerDT = new PSFPartnerDT();
		
		ArrayList<Object> psfAsAPartnerColl = new ArrayList<Object>();
		String SELECT_PSF_PARTNER="SELECT agencyId \"agencyId\", "
				+"localClientId \"localClientId\", "
				+"clientIdSTDMIS \"clientIdSTDMIS\", "
				+"clientIdPSID \"clientIdPSID\", "
				+"clientIdLocalId \"clientIdLocalId\", "
				+"clientFirstName \"clientFirstName\", "
				+"clientLastName \"clientLastName\", "
				+"clientDOB \"clientDOB\", "
				+"caseNumberPS \"caseNumberPS\", "
				+"caseNumberEarliestPS \"caseNumberEarliestPS\", "
				+"caseNumberSTDMIS \"caseNumberSTDMIS\", "
				+"caseNumberLegacyId \"caseNumberLegacyId\", "
				+"caseNumberLocalId \"caseNumberLocalId\", "
				+"partnerDateDemographicsCollected \"partnerDateDemographicsCollected\", "
				+"partnerType \"partnerType\", "
				+"attemptToLocateOutcome \"attemptToLocateOutcome\", "
				+"reasonForUnsuccessfulAttempt \"reasonForUnsuccessfulAttempt\", "
				+"enrollmentStatus \"enrollmentStatus\", "
				+"partnerNotifiability \"partnerNotifiability\", "
				+"actualNotificationMethod \"actualNotificationMethod\", "
				+"previousHivTestValueCode \"previousHivTestValueCode\", "
				+"previousHIVTestResult \"previousHIVTestResult\", "
				+"HIVTestPerformed \"hivTestPerformed\", "
				+"sampleDate \"sampleDate\", "
				+"HIVTestResult \"hivTestResult\", "
				+"provisionOfResultValueCode \"provisionOfResultValueCode\", "
				+"syphilisTest \"syphilisTest\", "
				+"syphilisTestResult \"syphilisTestResult\", "
				+"currentHIVMedicalCareStatus \"currentHIVMedicalCareStatus\", "
				+"firstMedicalCareAppointmentDate \"firstMedicalCareAppointmentDate\", "
				+"currentlyOnPrEP \"currentlyOnPrEP\", "
				+"referredToPrEP \"referredToPrEP\", "
				+"patientAddTime \"patientAddTime\", "
				+"patientLastChgTime \"patientLastChgTime\", "
				+"patientStatusCd \"patientStatusCd\", "
				+"invAddtime \"invAddtime\", "
				+"invLastChgTime \"invLastChgTime\", "
				+"invStatusCd \"invStatusCd\", "
				+"invLocalId \"invLocalId\", "
				+"crAddTime \"crAddTime\", "
				+"crLastChgTime \"crLastChgTime\", "
				+"crStatusCd \"crStatusCd\", "
				+"crLocalId \"crLocalId\", "
				+"partnerLastChgDt \"partnerLastChgDt\" ";
						
		
		String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_PARTNER WHERE (caseNumberPS in ("+caseNumberPSList+") AND (patientStatusCd <> 'LOG_DEL' and crStatusCd <> 'LOG_DEL')) ";

		ArrayList<Object> paramList = new ArrayList<Object>();
		
		String selectPartnerServices = "";
		selectPartnerServices = SELECT_PSF_PARTNER + SELECT_PARTNER_SERVICES_SQL;
		
		try {
			psfAsAPartnerColl = (ArrayList<Object>) preparedStmtMethod(psfPartnerDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAO.getPSFAsAPartnerDataByCaseNumberPS for localClientId in "
					+ caseNumberPSList +": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		
		return psfAsAPartnerColl;
		
	}


	/**
	 * getPSFClientData: reads the client data from PSF_CLIENT which localClientId is within clientLocalIdList list the and stores it in a collection of psfClientDT.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	
	public ArrayList<Object> getPSFClientData(String localClientIdList){
			
			PSFClientDT psfClientDT = new PSFClientDT();
			
			ArrayList<Object> psfClientColl = new ArrayList<Object>();
			String SELECT_PSF_CLIENT="SELECT localClientId \"localClientId\", "
					+"clientIdSTDMIS \"clientIdSTDMIS\", "
					+"clientIdPSID \"clientIdPSID\", "
					+"clientIdLocalId \"clientIdLocalId\", "
					+"clientFirstName \"clientFirstName\", "
					+"clientLastName \"clientLastName\", "
					+"clientDOB \"clientDOB\", "
					+"birthYear \"birthYear\", "
					+"ethnicity \"ethnicity\", "
					+"raceValueCode1 \"raceValueCode1\", "
					+"raceValueCode2 \"raceValueCode2\", "
					+"raceValueCode3 \"raceValueCode3\", "
					+"raceValueCode4 \"raceValueCode4\", "
					+"raceValueCode5 \"raceValueCode5\", "
					+"birthGenderValueCode \"birthGenderValueCode\", "
					+"currentGenderValueCode \"currentGenderValueCode\", "
					+"eHarsStateNumber \"eHarsStateNumber\", "
					+"lastModifiedDate \"lastModifiedDate\", "
					+"patientLastChgTime \"patientLastChgTime\", "
					+"patientAddTime \"patientAddTime\", "
					+"patientStatusCd \"patientStatusCd\", "
					+"clientUid \"clientUid\" ";
			
			String SELECT_PARTNER_SERVICES_SQL = "FROM dbo.PSF_CLIENT WHERE (localClientId in ("+localClientIdList+")) ";
			
			ArrayList<Object> paramList = new ArrayList<Object>();
			
			String selectPartnerServices = "";
			selectPartnerServices = SELECT_PSF_CLIENT + SELECT_PARTNER_SERVICES_SQL;
			
			try {
				psfClientColl = (ArrayList<Object>) preparedStmtMethod(psfClientDT, paramList, selectPartnerServices, NEDSSConstants.SELECT, NEDSSConstants.MSGOUT);
			} catch (Exception ex) {
				logger
				.fatal("Exception in PartnerServicesDAO.getPSFClientData for localClientId in "
						+ localClientIdList +": ERROR = " + ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			
			return psfClientColl;
	}	
}
