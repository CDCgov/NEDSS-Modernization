package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.dao.CTContactSummaryDAO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.PartnerServicesLookupDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.PartnerServicesMigratedCaseDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * PartnerServicesDAO - Retrieves data for the time range and all cases. Also retrieves migrated (i.e. STD*MIS) data.
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: CSRA for CDC</p>
 * Dec 22nd, 2016
 * @version 0.9
 */

public class PartnerServicesDAO extends DAOBase {

	static final LogUtils logger = new LogUtils(PartnerServicesDAO.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
/*
 *  This will return all the cases within the time period. Some cases may be standard cases with no Index or Partners.
 *  Some cases are Index cases if they have named others. If they are named then they may be Partner cases.
 *  In rare cases, a client could have both Index information and Partner information.
 *  900 is AIDS. 10560 is HIV.
 */
	public ArrayList<Object> getHivPartnerServicesMatchColl(String startDate, String endDate)
	throws NEDSSDAOSysException, NEDSSSystemException {
		PartnerServicesLookupDT partnerServicesLookupDT = new PartnerServicesLookupDT();
		String SELECT_PARTNER_SERVICES_BASE = 
		  "Select 1 \"drivingCase\", public_health_case.public_health_case_uid \"publicHealthCaseUid\" ,"
		  +" public_health_case.prog_area_cd \"programAreaCd\", case_management.fld_foll_up_dispo \"fldFollUpDispo\","
		  +" intv.interview_uid \"interviewUid\", public_health_case.activity_from_time \"activityFromTime\","
		  +" public_health_case.last_chg_time \"lastChgTime\","
		  +" public_health_case.local_id \"localId\", public_health_case.referral_basis_cd \"referralBasisCd\","
		  +" Person.local_id \"personLocalId\""
		  +" from Public_health_case "
		  +" INNER JOIN case_management on case_management.public_health_case_uid = public_health_case.public_health_case_uid "
		  +" INNER JOIN Participation ON public_health_case.public_health_case_uid = Participation.act_uid and type_cd = 'SubjOfPHC' and Participation.record_status_cd = 'ACTIVE'" 
		  +" INNER JOIN Person ON Participation.subject_entity_uid = Person.person_uid"
	      +" LEFT JOIN (select target_act_uid,source_act_uid from act_relationship "
		  +" where source_act_uid in "
	      +" (select interview_uid from interview where interview_type_cd='INITIAL')) "
		  +" ar on Public_health_case.public_health_case_uid = ar.target_act_uid "
	      +" LEFT JOIN interview intv on ar.source_act_uid = intv.interview_uid " 
	      +" where Public_health_case.cd in ('900','10560') "
	      +" and fld_foll_up_dispo in ('1','2','5') "
	      +" and Public_health_case.record_status_cd != 'LOG_DEL'";
		String SELECT_PARTNER_SERVICES_SQL = "and ((Public_health_case.ACTIVITY_FROM_TIME BETWEEN ? AND ?) OR (PUBLIC_HEALTH_CASE.LAST_CHG_TIME  BETWEEN ? AND ?)) "
				+ "  order by Public_health_case.ACTIVITY_FROM_TIME";
		ArrayList<Object> paramList = new ArrayList<Object>();
		ArrayList<Object> partnerServicesColl = new ArrayList<Object>();
		paramList.add(startDate);
		paramList.add(endDate);
		paramList.add(startDate);
		paramList.add(endDate);		
		String selectPartnerServices = "";
		selectPartnerServices = SELECT_PARTNER_SERVICES_BASE + SELECT_PARTNER_SERVICES_SQL;
		try {
			partnerServicesColl = (ArrayList<Object>) preparedStmtMethod(partnerServicesLookupDT, paramList, selectPartnerServices, NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger
			.fatal("Exception in PartnerServicesDAO.getHivPartnerServicesMatchColl for date range  ="
					+ startDate + " to " + endDate + ": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return partnerServicesColl;
	}
	/*
	 * getNamedAndNamedBy - get the contact summaries associated with each investigation
	 */
	public void getNamedAndNamedBy(ArrayList<Object> partnerServicesColl, NBSSecurityObj nbsSecurityObj) {
		
		CTContactSummaryDAO cTContactSummaryDAO = new CTContactSummaryDAO();
		try{
			int counter = 0;
			Iterator partnerServicesIter = partnerServicesColl.iterator();
			while (partnerServicesIter.hasNext()) {
				++counter;
				PartnerServicesLookupDT partnerServicesDT = (PartnerServicesLookupDT) partnerServicesIter.next();
				Long publicHealthCaseUID = partnerServicesDT.getPublicHealthCaseUid();
				logger.info("Getting contact information: Counter at " +counter +" for phc:" +publicHealthCaseUID.toString());
				partnerServicesDT.setIsContactRcd(false);
				//get the ContactSummary collection (if any) for the Investigation
				Collection<Object> contactCollection = cTContactSummaryDAO
					.getContactListForInvestigation(publicHealthCaseUID,
							nbsSecurityObj);
				if (contactCollection == null)
					partnerServicesDT.setContactSummaryColl(new ArrayList<Object>());
				else
					partnerServicesDT.setContactSummaryColl(contactCollection);
			}
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/*
	 *  This will return all the HIV/AIDS cases in the system.
	 *  Condition code 900 is AIDS. 10560 is HIV.
	 *  We want to use the legacy_case_number if it is present. If it is not, we look 
	 *  to see if there is a migrated document with a case no. If not, we use the local
	 *  id if the first case.
	 */
		public ArrayList<Object> getAll900CasesInOurSystem()
		throws NEDSSDAOSysException, NEDSSSystemException {
			PartnerServicesLookupDT partnerServicesLookupDT = new PartnerServicesLookupDT();
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(getEndDateForAllCaseQuery());
			String SELECT_900_BASE = 
			  "Select public_health_case.public_health_case_uid \"publicHealthCaseUid\" , public_health_case.prog_area_cd \"programAreaCd\", "
				+" case_management.fld_foll_up_dispo \"fldFollUpDispo\", public_health_case.local_id \"localId\","
				+" public_health_case.activity_from_time \"activityFromTime\", public_health_case.last_chg_time \"lastChgTime\","
				+" public_health_case.referral_basis_cd \"referralBasisCd\","
				+" act_id.root_extension_txt \"legacyCaseNo\","
				+" Person.local_id \"personLocalId\""
				+" from Public_health_case"
				+" INNER JOIN case_management on case_management.public_health_case_uid = public_health_case.public_health_case_uid"
				+" INNER JOIN Participation ON public_health_case.public_health_case_uid = Participation.act_uid and type_cd = 'SubjOfPHC' and Participation.record_status_cd = 'ACTIVE'" 
				+" INNER JOIN Person ON Participation.subject_entity_uid = Person.person_uid"
				+" LEFT JOIN Act_id ON public_health_case.public_health_case_uid = Act_id.act_uid and Act_id.type_cd = 'LEGACY' and Act_id.status_cd = 'A'"
				+" where PUBLIC_HEALTH_CASE.cd in ('900','10560') and case_class_cd != 'N' and PUBLIC_HEALTH_CASE.record_status_cd != 'LOG_DEL'";
			
				String SELECT_PARTNER_SERVICES_SQL = " and public_health_case.activity_from_time < ?"
						+" order by PUBLIC_HEALTH_CASE.ACTIVITY_FROM_TIME";
				String SELECT_PARTNER_SERVICES_ORA = " and public_health_case.activity_from_time < TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS')"
						+" order by PUBLIC_HEALTH_CASE.ACTIVITY_FROM_TIME";
				String selectPartnerServices = "";
				selectPartnerServices = SELECT_900_BASE + SELECT_PARTNER_SERVICES_SQL;
			ArrayList<Object> case900Coll = new ArrayList<Object>();
			try {
				case900Coll = (ArrayList<Object>) preparedStmtMethod(partnerServicesLookupDT, paramList, selectPartnerServices, NEDSSConstants.SELECT);
			} catch (Exception ex) {
				logger
				.fatal("Exception in PartnerServicesDAO.getAll900Coll"
						+ ": ERROR = " + ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			return case900Coll;
		}
		
		public ArrayList<Object> getAllMigrated900Cases()
		throws NEDSSDAOSysException, NEDSSSystemException {
			PartnerServicesMigratedCaseDT partnerServicesMigratedCaseDT = new PartnerServicesMigratedCaseDT();
			String SELECT_MIGRATED_BASE = 
					"SELECT EDX_event_process.edx_event_process_uid \"edxEventProcessUid\", "
					+" EDX_event_process.nbs_document_uid \"nbsDocumentUid\","
					+" EDX_event_process.nbs_event_uid \"nbsInvestigationUid\","
					+" Public_health_case.local_id \"nbsInvestigationLocalId\","
					+" NBS_document.local_id \"nbsDocumentLocalId\","
					+" Person.local_id \"nbsPatientLocalId\","
					+" NBS_document.sending_app_patient_id \"legacyPatientId\","
					+" Act_id.root_extension_txt \"legacyCaseNo\""
					+" FROM EDX_event_process "
					+" INNER JOIN NBS_document ON EDX_event_process.nbs_document_uid = NBS_document.nbs_document_uid"
					+" INNER JOIN Participation ON NBS_document.nbs_document_uid = Participation.act_uid and type_cd = 'SubjOfDoc'" 
					+" INNER JOIN Person ON Participation.subject_entity_uid = Person.person_uid"
					+" LEFT JOIN Public_health_case ON EDX_event_process.nbs_event_uid = Public_health_case.public_health_case_uid"
					+" LEFT JOIN Act_id ON Act_id.act_uid = Public_health_case.public_health_case_uid and Act_id.type_cd = 'LEGACY'"
					+" WHERE  (EDX_event_process.doc_event_type_cd = 'CASE') and PUBLIC_HEALTH_CASE.cd in ('900','10560') and PUBLIC_HEALTH_CASE.record_status_cd != 'LOG_DEL'";

			ArrayList<Object> paramList = new ArrayList<Object>();
			ArrayList<Object> migrated900CaseCol = new ArrayList<Object>();
			try {
				migrated900CaseCol = (ArrayList<Object>) preparedStmtMethod(partnerServicesMigratedCaseDT, paramList, SELECT_MIGRATED_BASE, NEDSSConstants.SELECT);
			} catch (Exception ex) {
				logger
				.fatal("Exception in PartnerServicesDAO.getAllMigrated900Cases "
						+ ": ERROR = " + ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			return migrated900CaseCol;
		}
		
		/**
		 * This will return a single case based in the passed in PHC Uid
		 * @param publicHealthCaseUid
		 * @return
		 * @throws NEDSSDAOSysException
		 * @throws NEDSSSystemException
		 */
			public ArrayList<Object> getHivPartnerServicesCase(Long publicHealthCaseUid)
			throws NEDSSDAOSysException, NEDSSSystemException {
				PartnerServicesLookupDT partnerServicesLookupDT = new PartnerServicesLookupDT();
				String SELECT_PHC_BASE = 
				  "Select public_health_case.public_health_case_uid \"publicHealthCaseUid\" ,"
				  +" public_health_case.prog_area_cd \"programAreaCd\", case_management.fld_foll_up_dispo \"fldFollUpDispo\","
				  +" intv.interview_uid \"interviewUid\", public_health_case.activity_from_time \"activityFromTime\","
				  +" public_health_case.last_chg_time \"lastChgTime\","
				  +" public_health_case.local_id \"localId\", public_health_case.referral_basis_cd \"referralBasisCd\","
				  +" Person.local_id \"personLocalId\""
				  +" from Public_health_case "
				  +" INNER JOIN case_management on case_management.public_health_case_uid = public_health_case.public_health_case_uid "
				  +" INNER JOIN Participation ON public_health_case.public_health_case_uid = Participation.act_uid and type_cd = 'SubjOfPHC' and Participation.record_status_cd = 'ACTIVE'" 
				  +" INNER JOIN Person ON Participation.subject_entity_uid = Person.person_uid"
			      +" LEFT JOIN (select target_act_uid,source_act_uid from act_relationship "
				  +" where source_act_uid in "
			      +" (select interview_uid from interview where interview_type_cd='INITIAL')) "
				  +" ar on Public_health_case.public_health_case_uid = ar.target_act_uid "
			      +" LEFT JOIN interview intv on ar.source_act_uid = intv.interview_uid " 
			      +" where Public_health_case.public_health_case_uid = ?";		
					ArrayList<Object> paramList = new ArrayList<Object>();
				ArrayList<Object> partnerServicesColl = new ArrayList<Object>();
				paramList.add(publicHealthCaseUid);

				String selectPartnerServices = "";
				selectPartnerServices = SELECT_PHC_BASE;
				try {
					partnerServicesColl = (ArrayList<Object>) preparedStmtMethod(partnerServicesLookupDT, paramList, selectPartnerServices, NEDSSConstants.SELECT);
				} catch (Exception ex) {
					logger
					.fatal("Exception in PartnerServicesDAO.getHivPartnerServicesCase for public_health_Case_uid  ="
							+ publicHealthCaseUid.toString() + ": ERROR = " + ex);
					throw new NEDSSSystemException(ex.toString(), ex);
				}
				return partnerServicesColl;
			}	
		
		/**
		 * The report is typically run in March and August
		 * @return
		 */
		private String getEndDateForAllCaseQuery() {
			
			String endingMonth = "";
			String endingDay = "";
			
			String currentYearStr = new SimpleDateFormat("yyyy").format(new Date());
			int curYear = Integer.parseInt(currentYearStr);
			String currentMonthStr = new SimpleDateFormat("MM").format(new Date());
			int curMonth = Integer.parseInt(currentMonthStr);
			if (curMonth < 7) {
				curYear = --curYear;
				endingMonth = "12";
				endingDay = "31";
			} else {
				endingMonth = "06";
				endingDay = "30";
			}
			String rptYearStr = String.valueOf(curYear);
			String endingTime = " 23:59:59";
		
			String endingDateStr = rptYearStr + "-" + endingMonth + "-" + endingDay + endingTime;

			logger.info("Partner Services: Ending date for All Cases query is: " +endingDateStr);

			return endingDateStr;
			
		}
	
}
