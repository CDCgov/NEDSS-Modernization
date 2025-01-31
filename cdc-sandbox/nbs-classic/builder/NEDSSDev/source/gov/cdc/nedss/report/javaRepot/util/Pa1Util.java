package gov.cdc.nedss.report.javaRepot.util;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dao.ReportPa1DAO;
import gov.cdc.nedss.report.javaRepot.dao.ReportPaDAO;
import gov.cdc.nedss.report.javaRepot.dao.ReportQueryReaderDAO;
import gov.cdc.nedss.report.javaRepot.dt.ReportPatientDT;
import gov.cdc.nedss.report.javaRepot.dt.ReportQueryReaderDT;
import gov.cdc.nedss.report.javaRepot.vo.Pa1VO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.StringUtils;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Util class for the STD Program Activity Report which runs from the RDB database.
 * See ReportPa1DAO.java for query.
 * @author Pradeep Kumar Sharma, Gregory Tucker
 * @version 1.0
 * @since   2015-02-24 
 * Copyright SRA International March 2015
 */


public class Pa1Util {
	static final LogUtils logger = new LogUtils(Pa1Util.class.getName());
	private String PA1_BASE="PA1_BASE";


	/**
	 * High level method to start the report
	 * @param fromDate - from activity date
	 * @param toDate - to activity date
	 * @param diagnosisArray - STD diagnosis codes
	 * @param workerArray (future, currently null)
	 * @param reportUid
	 * @param reportType - tells whether from/to dates based on Interview Assign Date or Case Close Date
	 * 		PA01 Case Management Report(Case Close Date) or PA01 Case Management Report(Interview Assign Date)
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSAppException
	 */
	public Object processRequest(String fromDate, String toDate, String[] diagnosisArray, String[] workerArray,
			Long reportUid, String reportType, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		
		Collection<Object> returnVal =  new ArrayList<Object>();
		try {
			//get report queries
			Timestamp fromConfirmTime = StringUtils.stringToStrutsTimestamp(fromDate);
			Timestamp toConfirmTime= StringUtils.stringToStrutsTimestamp(toDate);
			logger.debug("STD/HIV Program Activity Report processing for "+reportType);
			logger.debug("\nfromConfirmTime"+fromConfirmTime);
			logger.debug("\ntoConfirmTime"+toConfirmTime);
			logger.debug("\ndiagnosisArray"+diagnosisArray);
			//logger.debug("\nworkerArray"+workerArray);
			String diagnosis=ReportCommonUtility.convertArrayToStringSql(diagnosisArray);
			//String worker=ReportCommonUtility.convertArrayToStringSql(workerArray);
			
			Timestamp fromTime = StringUtils.stringToStrutsTimestamp(fromDate);
			Timestamp toTime = StringUtils.stringToStrutsTimestamp(toDate);
			String reportSTDorHIV = PA1Const.STD_REPORT_TYPE;
			if (hivCaseInDiagnosisList(diagnosis))
				reportSTDorHIV = PA1Const.HIV_REPORT_TYPE;
			
			ReportPa1DAO reportPa1Dao = new ReportPa1DAO();
			Collection<Object> pa1Coll = reportPa1Dao.getCasesContactsInterviewsWithinDateRangeAndDiagList(fromTime, toTime, diagnosis, reportType, nbsSecurityObj);
			
			if(pa1Coll!=null){
				returnVal= populateVO(pa1Coll, reportType, reportSTDorHIV);				
			}
		} catch (NEDSSSystemException ex) {
			logger.error("PA1Util.processRequest Exception caught:" , ex);
			throw new NEDSSAppException(ex.getMessage());
		}
		return returnVal;
	}
	
	/**
	 * Cycle through the returned data and create a Pa1VO for each Worker. At the end, create a summary if there was any data.
	 * @param coll - data returned from query, Maybe several records for a case if the case has contacts and interviews.
	 * @param interviewAssignOrCaseClose 
	 * @param reportType - Interview Assign Or Case Close
	 * @param reportSTDorHIV
	 * @return
	 */
	private Collection<Object> populateVO(Collection<Object> coll, String reportType, String reportSTDorHIV ){
		
		Collection<Object> pa1VoColl = new ArrayList<Object>();
		
		
		HashSet<Long> listOfProcessedCases = new HashSet<Long>();
		HashSet<Long> listOfProcessedInterviews= new HashSet<Long>();
		HashSet<Long> listOfProcessedContacts= new HashSet<Long>();
		HashMap<Long,String> interviewTypeMap = getTheInterviewTypeMap(coll);
		//A Summary Record is displayed at the end with accumulated counts
		Pa1VO summaryVO= new Pa1VO();
		initializeCaseWorkerVO(summaryVO, reportSTDorHIV);
		summaryVO.setWorker(PA1Const.WORKER_SUMMARY);
		String currentWorker = "";
		Pa1VO caseWorkerVO = null;
		//Run through the collection, there may be more that one record for each case..
		Long currentCase = 0L;
		boolean lastCaseHadOneAorCDispo = false;
		boolean lastCaseSourceOfInfectionFound = false;
		boolean lastCaseHadNoPartners = true;
		boolean lastCaseHadNoClusters = true;
		boolean isHivCase = false;
		Iterator<Object> iter = coll.iterator();
		while(iter.hasNext()){
			ReportPatientDT reportPatientDT= (ReportPatientDT)iter.next();
			String worker = getRelevantWorker(reportPatientDT, reportType);
			if (!worker.equals(currentWorker)) {
				if (!currentWorker.isEmpty())
					computePercentagesAndIndexesForWorker(caseWorkerVO);
				//new container
				caseWorkerVO= new Pa1VO(); //patientDT case list sorted by worker
				initializeCaseWorkerVO(caseWorkerVO, reportSTDorHIV);
				caseWorkerVO.setWorker(worker);
				pa1VoColl.add(caseWorkerVO);
				currentWorker = worker;
				currentCase = 0L;
			}
			//new case - should count last case in index?
			if (reportPatientDT.getInvestigationKey() != currentCase) {
				if (currentCase != 0L) {
					if (lastCaseHadOneAorCDispo) { // increment Disease Intervention Index numerator
						caseWorkerVO.setDiseaseInterventionIndex(parseIndexValue(caseWorkerVO.getDiseaseInterventionIndex()));
						summaryVO.setDiseaseInterventionIndex(parseIndexValue(summaryVO.getDiseaseInterventionIndex()));
					}
					if (lastCaseSourceOfInfectionFound) {
						caseWorkerVO.setCasesWithSourceIdentifiedCount(parseIntegerValue(caseWorkerVO.getCasesWithSourceIdentifiedCount()));
						summaryVO.setCasesWithSourceIdentifiedCount(parseIntegerValue(summaryVO.getCasesWithSourceIdentifiedCount()));
					}
					if (lastCaseHadNoPartners) {
						caseWorkerVO.setCasesWithNoPartnersCount(parseIndexValue(caseWorkerVO.getCasesWithNoPartnersCount()));
						summaryVO.setCasesWithNoPartnersCount(parseIndexValue(summaryVO.getCasesWithNoPartnersCount()));
					}
					if (lastCaseHadNoClusters) {
						caseWorkerVO.setCasesWithNoClustersCount(parseIndexValue(caseWorkerVO.getCasesWithNoClustersCount()));
						summaryVO.setCasesWithNoClustersCount(parseIndexValue(summaryVO.getCasesWithNoClustersCount()));
					}
				}
				//initialize for next case..
				currentCase = reportPatientDT.getInvestigationKey();
				lastCaseHadOneAorCDispo = false;
				lastCaseSourceOfInfectionFound = false;
				lastCaseHadNoPartners = true;
				lastCaseHadNoClusters = true;
			} //new case

			//Case may repeat with each interview/contact so don't count twice
			if (!listOfProcessedCases.contains(reportPatientDT.getInvestigationKey())) {
					//new Investigation - process case info
					getCaseAssignmentsAndOutcomesCaseCounts(reportPatientDT,caseWorkerVO, summaryVO);
					getPartnersAndClustersInitiatedCounts(reportPatientDT,caseWorkerVO, summaryVO);
					isHivCase = isThisHivCase(reportPatientDT.getDx()); //is this HIV case?
					listOfProcessedCases.add(reportPatientDT.getInvestigationKey());
			}
			
			if (reportPatientDT.getInterviewKey() != null && !listOfProcessedInterviews.contains(reportPatientDT.getInterviewKey())) {
				processNewInterview(reportPatientDT, caseWorkerVO, summaryVO);
				listOfProcessedInterviews.add(reportPatientDT.getInterviewKey());
			}

		    //processing for Contact
			if (reportPatientDT.getContactRecordKey() != null && !listOfProcessedContacts.contains(reportPatientDT.getContactRecordKey())) {
				listOfProcessedContacts.add(reportPatientDT.getContactRecordKey()); //no need to process more than once..
				
				if (reportPatientDT.getContactSourceSpread() != null) {
					if (reportPatientDT.getContactSourceSpread().startsWith("2")) //2 - Source
						lastCaseSourceOfInfectionFound = true;
				}
				
				if (reportPatientDT.getContactDispositionDesc() != null) {
					String contactDispo = reportPatientDT.getContactDispositionDesc();
					//std
					if (contactDispo.startsWith("A") || contactDispo.startsWith("C")) {
						caseWorkerVO.setTreatmentIndex(parseIndexValue(caseWorkerVO.getTreatmentIndex())); //add to the numerator
						if (reportPatientDT.getContactProcessingDecisionDesc().equals(PA1Const.FIELD_FOLLOWUP))
							lastCaseHadOneAorCDispo = true;
					} //A or C	
					//hiv
					if (contactDispo.startsWith("2") || contactDispo.startsWith("3") || contactDispo.startsWith("5") || contactDispo.startsWith("6")) {
						caseWorkerVO.setPartnerNotificationIndex(parseIndexValue(caseWorkerVO.getPartnerNotificationIndex()));
						summaryVO.setPartnerNotificationIndex(parseIndexValue(summaryVO.getPartnerNotificationIndex()));
					}
				} //contact dispo not null
				//total partners Initiated (R)
				///////////////+++++++++++++PARTNER++++++++++////////////////////
				if (reportPatientDT.getContactInterviewKey() != null &&
						reportPatientDT.getContactReferralBasis() != null &&
						reportPatientDT.getContactReferralBasis().startsWith("P") &&
						reportPatientDT.getContactProcessingDecisionDesc() != null &&
						(reportPatientDT.getContactProcessingDecisionDesc().equals(PA1Const.FIELD_FOLLOWUP) ||
								reportPatientDT.getContactProcessingDecisionDesc().equals(PA1Const.RECORD_SEARCH_CLOSURE))) {
							caseWorkerVO.setTotalPartnersInitiatedCount(parseIndexValue(caseWorkerVO.getTotalPartnersInitiatedCount()));
							summaryVO.setTotalPartnersInitiatedCount(parseIndexValue(summaryVO.getTotalPartnersInitiatedCount()));
							String contDisp = reportPatientDT.getContactDispositionDesc();
							// if A B C D or F (PM)
							if (contDisp != null && (contDisp.startsWith("A") || contDisp.startsWith("B") || contDisp.startsWith("C") ||
									contDisp.startsWith("D") || contDisp.startsWith("F")) ) {
								//PM - new partners examined
								caseWorkerVO.setNewPartnersExaminedCount(parseIndexValue(caseWorkerVO.getNewPartnersExaminedCount()));
								summaryVO.setNewPartnersExaminedCount(parseIndexValue(summaryVO.getNewPartnersExaminedCount()));
								//also Speed of Exam
								caseWorkerVO.setSeNewPartnersExaminedCount(parseIndexValue(caseWorkerVO.getSeNewPartnersExaminedCount()));
								summaryVO.setSeNewPartnersExaminedCount(parseIndexValue(summaryVO.getSeNewPartnersExaminedCount()));
				
								if (contDisp != null && contDisp.startsWith("A")) { //(PA)
									//this is confusing but any case started from File - is considered OOJ (per Tammy) and not included here..
									//may consider adding - if (reportPatientDT.getReferralOOJ() == null) {
									caseWorkerVO.setNewPartnersPreventativeRxCount(parseIndexValue(caseWorkerVO.getNewPartnersPreventativeRxCount()));
									summaryVO.setNewPartnersPreventativeRxCount(parseIndexValue(summaryVO.getNewPartnersPreventativeRxCount()));
								} else if (contDisp != null && contDisp.startsWith("B")) { //(PB)
									caseWorkerVO.setNewPartnersRefusedPrevRxCount(parseIndexValue(caseWorkerVO.getNewPartnersRefusedPrevRxCount()));
									summaryVO.setNewPartnersRefusedPrevRxCount(parseIndexValue(summaryVO.getNewPartnersRefusedPrevRxCount()));
								} else if (contDisp != null && contDisp.startsWith("C")) { //(PC)
									caseWorkerVO.setNewPartnersInfectedRxCount(parseIndexValue(caseWorkerVO.getNewPartnersInfectedRxCount()));
									summaryVO.setNewPartnersInfectedRxCount(parseIndexValue(summaryVO.getNewPartnersInfectedRxCount()));
								} else if (contDisp != null && contDisp.startsWith("D")) {//(PD)
									caseWorkerVO.setNewPartnersInfectedNoRxCount(parseIndexValue(caseWorkerVO.getNewPartnersInfectedNoRxCount()));
									summaryVO.setNewPartnersInfectedNoRxCount(parseIndexValue(summaryVO.getNewPartnersInfectedNoRxCount()));
								} else if (contDisp != null && contDisp.startsWith("F")) {//(PF)
									caseWorkerVO.setNewPartnersNotInfectedCount(parseIndexValue(caseWorkerVO.getNewPartnersNotInfectedCount()));
									summaryVO.setNewPartnersNotInfectedCount(parseIndexValue(summaryVO.getNewPartnersNotInfectedCount()));
								}
							} 
							//if G H J K L V X or Z (PN)
							if (contDisp != null && (contDisp.startsWith("G") || contDisp.startsWith("H") 
									|| contDisp.startsWith("J") || 	contDisp.startsWith("K") || 
									contDisp.startsWith("L")) ) {
								if (isHivCase) {
									caseWorkerVO.setNewPartnersNotNotifiedCount(parseIndexValue(caseWorkerVO.getNewPartnersNotNotifiedCount()));
									summaryVO.setNewPartnersNotNotifiedCount(parseIndexValue(summaryVO.getNewPartnersNotNotifiedCount()));
								} else {
									caseWorkerVO.setNewPartnersNoExamCount(parseIndexValue(caseWorkerVO.getNewPartnersNoExamCount()));
									summaryVO.setNewPartnersNoExamCount(parseIndexValue(summaryVO.getNewPartnersNoExamCount()));
								}
								if (contDisp != null && contDisp.startsWith("G")) { //(PG)
									if (isHivCase) {
										caseWorkerVO.setNewPartnersNotNotifiedInsufficientInfoCount(parseIndexValue(caseWorkerVO.getNewPartnersNotNotifiedInsufficientInfoCount()));
										summaryVO.setNewPartnersNotNotifiedInsufficientInfoCount(parseIndexValue(summaryVO.getNewPartnersNotNotifiedInsufficientInfoCount()));
									} else {
										caseWorkerVO.setNewPartnersInsufficientInfoCount(parseIndexValue(caseWorkerVO.getNewPartnersInsufficientInfoCount()));
										summaryVO.setNewPartnersInsufficientInfoCount(parseIndexValue(summaryVO.getNewPartnersInsufficientInfoCount()));
									}
								} else if (contDisp != null && contDisp.startsWith("H")) { //(PH)
									if (isHivCase) {
										caseWorkerVO.setNewPartnersNotNotifiedUnableToLocateCount(parseIndexValue(caseWorkerVO.getNewPartnersNotNotifiedUnableToLocateCount()));
										summaryVO.setNewPartnersNotNotifiedUnableToLocateCount(parseIndexValue(summaryVO.getNewPartnersNotNotifiedUnableToLocateCount()));
									} else {
										caseWorkerVO.setNewPartnersUnableToLocateCount(parseIndexValue(caseWorkerVO.getNewPartnersUnableToLocateCount()));
										summaryVO.setNewPartnersUnableToLocateCount(parseIndexValue(summaryVO.getNewPartnersUnableToLocateCount()));
									}
								} else if (contDisp != null && contDisp.startsWith("J")) { //(PJ)
									if (isHivCase) {
										caseWorkerVO.setNewPartnersNotNotifiedRefusedExamCount(parseIndexValue(caseWorkerVO.getNewPartnersNotNotifiedRefusedExamCount()));
										summaryVO.setNewPartnersNotNotifiedRefusedExamCount(parseIndexValue(summaryVO.getNewPartnersNotNotifiedRefusedExamCount()));
									} else {
										caseWorkerVO.setNewPartnersRefusedExamCount(parseIndexValue(caseWorkerVO.getNewPartnersRefusedExamCount()));
										summaryVO.setNewPartnersRefusedExamCount(parseIndexValue(summaryVO.getNewPartnersRefusedExamCount()));
									}
								} else if (contDisp != null && contDisp.startsWith("K")) { //(PK)
									if (isHivCase) {
										caseWorkerVO.setNewPartnersNotNotifiedOOJCount(parseIndexValue(caseWorkerVO.getNewPartnersNotNotifiedOOJCount()));
										summaryVO.setNewPartnersNotNotifiedOOJCount(parseIndexValue(summaryVO.getNewPartnersNotNotifiedOOJCount()));
									} else {
										caseWorkerVO.setNewPartnersOOJCount(parseIndexValue(caseWorkerVO.getNewPartnersOOJCount()));
										summaryVO.setNewPartnersOOJCount(parseIndexValue(summaryVO.getNewPartnersOOJCount()));
									}
								} else if (contDisp != null && contDisp.startsWith("L")) { //(PL)
									if (isHivCase) {
										caseWorkerVO.setNewPartnersNotNotifiedOtherCount(parseIndexValue(caseWorkerVO.getNewPartnersNotNotifiedOtherCount()));
										summaryVO.setNewPartnersNotNotifiedOtherCount(parseIndexValue(summaryVO.getNewPartnersNotNotifiedOtherCount()));
									} else {
										caseWorkerVO.setNewPartnersOtherCount(parseIndexValue(caseWorkerVO.getNewPartnersOtherCount()));
										summaryVO.setNewPartnersOtherCount(parseIndexValue(summaryVO.getNewPartnersOtherCount()));
									}
								}//why no V, X or Z on form? RDV, Deceased, Prev Rx
	
							}//if G, H, J, K L
							if (contDisp != null && contDisp.startsWith("E")) {
								caseWorkerVO.setNewPartnersPreviousRxCount(parseIndexValue(caseWorkerVO.getNewPartnersPreviousRxCount()));
								summaryVO.setNewPartnersPreviousRxCount(parseIndexValue(summaryVO.getNewPartnersPreviousRxCount()));
							}
							if (contDisp == null) {  //No Dispo yet (PO)
								if (isHivCase) {
									caseWorkerVO.setNewPartnersHivOpenCount(parseIndexValue(caseWorkerVO.getNewPartnersHivOpenCount()));
									summaryVO.setNewPartnersHivOpenCount(parseIndexValue(summaryVO.getNewPartnersHivOpenCount()));
								} else {
									caseWorkerVO.setNewPartnersStdOpenCount(parseIndexValue(caseWorkerVO.getNewPartnersStdOpenCount()));
									summaryVO.setNewPartnersStdOpenCount(parseIndexValue(summaryVO.getNewPartnersStdOpenCount()));
								}
							}
							//HIV only
							if (contDisp != null && contDisp.startsWith("1")) { //Prev 
								caseWorkerVO.setNewPartnersPrevPosCount(parseIndexValue(caseWorkerVO.getNewPartnersPrevPosCount()));
								summaryVO.setNewPartnersPrevPosCount(parseIndexValue(summaryVO.getNewPartnersPrevPosCount()));
							} else if (contDisp != null && (contDisp.startsWith("2") || contDisp.startsWith("3") || contDisp.startsWith("4") ||
									contDisp.startsWith("5") || contDisp.startsWith("6")  || contDisp.startsWith("7"))) {
								caseWorkerVO.setNewPartnersNotifiedCount(parseIndexValue(caseWorkerVO.getNewPartnersNotifiedCount()));
								summaryVO.setNewPartnersNotifiedCount(parseIndexValue(summaryVO.getNewPartnersNotifiedCount()));
								//speed of Exam also
								caseWorkerVO.setSeNewPartnersNotifiedCount(parseIndexValue(caseWorkerVO.getNewPartnersNotifiedCount()));
								summaryVO.setSeNewPartnersNotifiedCount(parseIndexValue(summaryVO.getNewPartnersNotifiedCount()));
								if (contDisp != null && contDisp.startsWith("2") ) {
									caseWorkerVO.setNewPartnersPrevNegNewPosCount(parseIndexValue(caseWorkerVO.getNewPartnersPrevNegNewPosCount()));
									summaryVO.setNewPartnersPrevNegNewPosCount(parseIndexValue(summaryVO.getNewPartnersPrevNegNewPosCount()));
								} else if (contDisp != null && contDisp.startsWith("3") ) {
									caseWorkerVO.setNewPartnersPrevNegStillNegCount(parseIndexValue(caseWorkerVO.getNewPartnersPrevNegStillNegCount()));
									summaryVO.setNewPartnersPrevNegStillNegCount(parseIndexValue(summaryVO.getNewPartnersPrevNegStillNegCount()));
								} else if (contDisp != null && contDisp.startsWith("4") ) {
									caseWorkerVO.setNewPartnersPrevNegNoTestCount(parseIndexValue(caseWorkerVO.getNewPartnersPrevNegNoTestCount()));
									summaryVO.setNewPartnersPrevNegNoTestCount(parseIndexValue(summaryVO.getNewPartnersPrevNegNoTestCount()));
								}else if (contDisp != null && contDisp.startsWith("5") ) {
									caseWorkerVO.setNewPartnersNoPrevNewPosCount(parseIndexValue(caseWorkerVO.getNewPartnersNoPrevNewPosCount()));
									summaryVO.setNewPartnersNoPrevNewPosCount(parseIndexValue(summaryVO.getNewPartnersNoPrevNewPosCount()));
								}else if (contDisp != null && contDisp.startsWith("6") ) {
									caseWorkerVO.setNewPartnersNoPrevNewNegCount(parseIndexValue(caseWorkerVO.getNewPartnersNoPrevNewNegCount()));
									summaryVO.setNewPartnersNoPrevNewNegCount(parseIndexValue(summaryVO.getNewPartnersNoPrevNewNegCount()));
								} else if (contDisp != null && contDisp.startsWith("7") ) {
									caseWorkerVO.setNewPartnersNoPrevNoTestCount(parseIndexValue(caseWorkerVO.getNewPartnersNoPrevNoTestCount()));
									summaryVO.setNewPartnersNoPrevNoTestCount(parseIndexValue(summaryVO.getNewPartnersNoPrevNoTestCount()));
								} 

							}
							
				}  // ================== End Partner =================================
				// Counts of Partner cases From OI and RI (S & T)
				if (reportPatientDT.getContactInterviewKey() != null  &&
						reportPatientDT.getContactReferralBasis() != null &&
						reportPatientDT.getContactReferralBasis().startsWith("P")) {
					String interviewType = interviewTypeMap.get(reportPatientDT.getContactInterviewKey());
					if (interviewType != null) {
						if (interviewType.equals(PA1Const.INITIAL_ORIGINAL_INTERVIEW)) {
							caseWorkerVO.setPartnersInitiatedFromOICount(parseIndexValue(caseWorkerVO.getPartnersInitiatedFromOICount()));
							summaryVO.setPartnersInitiatedFromOICount(parseIndexValue(summaryVO.getPartnersInitiatedFromOICount()));
						} else if (interviewType.equals(PA1Const.RE_INTERVIEW)) {
							caseWorkerVO.setPartnersInitiatedFromRICount(parseIndexValue(caseWorkerVO.getPartnersInitiatedFromRICount()));
							summaryVO.setPartnersInitiatedFromRICount(parseIndexValue(summaryVO.getPartnersInitiatedFromRICount()));
						}
					}
				}
				//cases with no partners (V)
				if (reportPatientDT.getContactInterviewKey() != null &&
						reportPatientDT.getContactReferralBasis() != null &&
						reportPatientDT.getContactReferralBasis().startsWith("P")) {
					lastCaseHadNoPartners = false;
				}
				// Counts of Cluster cases From OI and RI (W)
				if (reportPatientDT.getContactInterviewKey() != null  &&
						reportPatientDT.getContactReferralBasis() != null &&
						(reportPatientDT.getContactReferralBasis().startsWith("A") ||
								reportPatientDT.getContactReferralBasis().startsWith("S"))	) {	
					lastCaseHadNoClusters = false;
					caseWorkerVO.setTotalClusterInitiatedCount(parseIndexValue(caseWorkerVO.getTotalClusterInitiatedCount()));
					summaryVO.setTotalClusterInitiatedCount(parseIndexValue(summaryVO.getTotalClusterInitiatedCount()));
				}
				//////////////======CLUSTER==========/////////////////////////////
				if (reportPatientDT.getContactInterviewKey() != null &&
						reportPatientDT.getContactReferralBasis() != null &&
						(reportPatientDT.getContactReferralBasis().startsWith("A") ||
								reportPatientDT.getContactReferralBasis().startsWith("S") ||
								reportPatientDT.getContactReferralBasis().startsWith("C")) &&
						reportPatientDT.getContactProcessingDecisionDesc() != null &&
						(reportPatientDT.getContactProcessingDecisionDesc().equals(PA1Const.FIELD_FOLLOWUP) ||
								reportPatientDT.getContactProcessingDecisionDesc().equals(PA1Const.RECORD_SEARCH_CLOSURE))) {				
					// if A B C D or F (CM)
					String contDisp = reportPatientDT.getContactDispositionDesc();
					if (contDisp != null && (contDisp.startsWith("A") || contDisp.startsWith("B") || contDisp.startsWith("C") ||
							contDisp.startsWith("D") || contDisp.startsWith("F")) ) {
			
						caseWorkerVO.setNewClustersExaminedCount(parseIndexValue(caseWorkerVO.getNewClustersExaminedCount()));
						summaryVO.setNewClustersExaminedCount(parseIndexValue(summaryVO.getNewClustersExaminedCount()));
						caseWorkerVO.setSeNewClustersExaminedCount(parseIndexValue(caseWorkerVO.getSeNewClustersExaminedCount()));
						summaryVO.setSeNewClustersExaminedCount(parseIndexValue(summaryVO.getSeNewClustersExaminedCount()));	
						
							if (contDisp != null && contDisp.startsWith("A")) { //(CA)
								//this is confusing but any case started from File - is considered OOJ (per Tammy) and not included here..
								//may consider adding - if (reportPatientDT.getReferralOOJ() == null) {
								caseWorkerVO.setNewClustersPreventativeRxCount(parseIndexValue(caseWorkerVO.getNewClustersPreventativeRxCount()));
								summaryVO.setNewClustersPreventativeRxCount(parseIndexValue(summaryVO.getNewClustersPreventativeRxCount()));
							} else if (contDisp != null && contDisp.startsWith("B")) { //(CB)
								caseWorkerVO.setNewClustersRefusedPrevRxCount(parseIndexValue(caseWorkerVO.getNewClustersRefusedPrevRxCount()));
								summaryVO.setNewClustersRefusedPrevRxCount(parseIndexValue(summaryVO.getNewClustersRefusedPrevRxCount()));
							} else if (contDisp != null && contDisp.startsWith("C")) { //(CC)
								caseWorkerVO.setNewClustersInfectedRxCount(parseIndexValue(caseWorkerVO.getNewClustersInfectedRxCount()));
								summaryVO.setNewClustersInfectedRxCount(parseIndexValue(summaryVO.getNewClustersInfectedRxCount()));
							} else if (contDisp != null && contDisp.startsWith("D")) {//(CD)
								caseWorkerVO.setNewClustersInfectedNoRxCount(parseIndexValue(caseWorkerVO.getNewClustersInfectedNoRxCount()));
								summaryVO.setNewClustersInfectedNoRxCount(parseIndexValue(summaryVO.getNewClustersInfectedNoRxCount()));
							} else if (contDisp != null && contDisp.startsWith("F")) {//(CF)
								caseWorkerVO.setNewClustersNotInfectedCount(parseIndexValue(caseWorkerVO.getNewClustersNotInfectedCount()));
								summaryVO.setNewClustersNotInfectedCount(parseIndexValue(summaryVO.getNewClustersNotInfectedCount()));
							}
							//Speed of Exam Partner
							Timestamp contactInvestigationStartDate = reportPatientDT.getContactInvestigationStartDate();
							Timestamp contactDispositionDate = reportPatientDT.getContactDispositionDate();
							if (contactInvestigationStartDate != null && contactDispositionDate != null) {
							
								long intStartVrsDispo = differenceInDays(contactDispositionDate, contactInvestigationStartDate);
								
								if(intStartVrsDispo<4){
									if (isHivCase) {
										caseWorkerVO.setSeNewPartnersNotifiedWI3dCount(parseIntegerValue(caseWorkerVO.getSeNewPartnersNotifiedWI3dCount()));	
										summaryVO.setSeNewPartnersNotifiedWI3dCount(parseIntegerValue(summaryVO.getSeNewPartnersNotifiedWI3dCount()));	
									} else {
										caseWorkerVO.setSeNewPartnersExaminedWI3dCount(parseIntegerValue(caseWorkerVO.getSeNewPartnersExaminedWI3dCount()));	
										summaryVO.setSeNewPartnersExaminedWI3dCount(parseIntegerValue(summaryVO.getSeNewPartnersExaminedWI3dCount()));	
									}
								}
								if(intStartVrsDispo<6){
									if (isHivCase) {
										caseWorkerVO.setSeNewPartnersNotifiedWI5dCount(parseIntegerValue(caseWorkerVO.getSeNewPartnersNotifiedWI5dCount()));	
										summaryVO.setSeNewPartnersNotifiedWI5dCount(parseIntegerValue(summaryVO.getSeNewPartnersNotifiedWI5dCount()));	
									} else {
										caseWorkerVO.setSeNewPartnersExaminedWI5dCount(parseIntegerValue(caseWorkerVO.getSeNewPartnersExaminedWI5dCount()));	
										summaryVO.setSeNewPartnersExaminedWI5dCount(parseIntegerValue(summaryVO.getSeNewPartnersExaminedWI5dCount()));
									}
								}
								if(intStartVrsDispo<8){
									if (isHivCase) {
										caseWorkerVO.setSeNewPartnersNotifiedWI7dCount(parseIntegerValue(caseWorkerVO.getSeNewPartnersNotifiedWI7dCount()));	
										summaryVO.setSeNewPartnersNotifiedWI7dCount(parseIntegerValue(summaryVO.getSeNewPartnersNotifiedWI7dCount()));	
									} else {								
										caseWorkerVO.setSeNewPartnersExaminedWI7dCount(parseIntegerValue(caseWorkerVO.getSeNewPartnersExaminedWI7dCount()));	
										summaryVO.setSeNewPartnersExaminedWI7dCount(parseIntegerValue(summaryVO.getSeNewPartnersExaminedWI7dCount()));
									}
								}
								if(intStartVrsDispo<15){
									if (isHivCase) {
										caseWorkerVO.setSeNewPartnersNotifiedWI14dCount(parseIntegerValue(caseWorkerVO.getSeNewPartnersNotifiedWI14dCount()));	
										summaryVO.setSeNewPartnersNotifiedWI14dCount(parseIntegerValue(summaryVO.getSeNewPartnersNotifiedWI14dCount()));	
									} else {								
										caseWorkerVO.setSeNewPartnersExaminedWI14dCount(parseIntegerValue(caseWorkerVO.getSeNewPartnersExaminedWI14dCount()));	
										summaryVO.setSeNewPartnersExaminedWI14dCount(parseIntegerValue(summaryVO.getSeNewPartnersExaminedWI14dCount()));
									}
								}
							} //SE dates not null
							
					} // end if A,B,C,D,F
					//HIV only..
					if (contDisp != null && contDisp.startsWith("1")) { //Prev 
						caseWorkerVO.setNewClustersHivPrevPosCount(parseIndexValue(caseWorkerVO.getNewClustersHivPrevPosCount()));
						summaryVO.setNewClustersHivPrevPosCount(parseIndexValue(summaryVO.getNewClustersHivPrevPosCount()));
					}
					if (contDisp != null && (contDisp.startsWith("2") || contDisp.startsWith("3") || contDisp.startsWith("4") ||
							contDisp.startsWith("5") || contDisp.startsWith("6")  || contDisp.startsWith("7"))) {
						caseWorkerVO.setNewClustersNotifiedCount(parseIndexValue(caseWorkerVO.getNewClustersNotifiedCount()));
						summaryVO.setNewClustersNotifiedCount(parseIndexValue(summaryVO.getNewClustersNotifiedCount()));
						//speed of Exam also
						caseWorkerVO.setSeNewClustersNotifiedCount(parseIndexValue(caseWorkerVO.getNewClustersNotifiedCount()));
						summaryVO.setSeNewClustersNotifiedCount(parseIndexValue(summaryVO.getNewClustersNotifiedCount()));
						if (contDisp != null && contDisp.startsWith("2") ) {
							caseWorkerVO.setNewClustersPrevNegNewPosCount(parseIndexValue(caseWorkerVO.getNewClustersPrevNegNewPosCount()));
							summaryVO.setNewClustersPrevNegNewPosCount(parseIndexValue(summaryVO.getNewClustersPrevNegNewPosCount()));
						} else if (contDisp != null && contDisp.startsWith("3") ) {
							caseWorkerVO.setNewClustersPrevNegStillNegCount(parseIndexValue(caseWorkerVO.getNewClustersPrevNegStillNegCount()));
							summaryVO.setNewClustersPrevNegStillNegCount(parseIndexValue(summaryVO.getNewClustersPrevNegStillNegCount()));
						} else if (contDisp != null && contDisp.startsWith("4") ) {
							caseWorkerVO.setNewClustersPrevNegNoTestCount(parseIndexValue(caseWorkerVO.getNewClustersPrevNegNoTestCount()));
							summaryVO.setNewClustersPrevNegNoTestCount(parseIndexValue(summaryVO.getNewClustersPrevNegNoTestCount()));
						}else if (contDisp != null && contDisp.startsWith("5") ) {
							caseWorkerVO.setNewClustersNoPrevNewPosCount(parseIndexValue(caseWorkerVO.getNewClustersNoPrevNewPosCount()));
							summaryVO.setNewClustersNoPrevNewPosCount(parseIndexValue(summaryVO.getNewClustersNoPrevNewPosCount()));
						}else if (contDisp != null && contDisp.startsWith("6") ) {
							caseWorkerVO.setNewClustersNoPrevNewNegCount(parseIndexValue(caseWorkerVO.getNewClustersNoPrevNewNegCount()));
							summaryVO.setNewClustersNoPrevNewNegCount(parseIndexValue(summaryVO.getNewClustersNoPrevNewNegCount()));
						} else if (contDisp != null && contDisp.startsWith("7") ) {
							caseWorkerVO.setNewClustersNoPrevNoTestCount(parseIndexValue(caseWorkerVO.getNewClustersNoPrevNoTestCount()));
							summaryVO.setNewClustersNoPrevNoTestCount(parseIndexValue(summaryVO.getNewClustersNoPrevNoTestCount()));
						} 

					}
					
					//if G H J K L V X or Z (CN)
					if (contDisp != null && (contDisp.startsWith("G") || contDisp.startsWith("H") 
							|| contDisp.startsWith("J") || 	contDisp.startsWith("K") || 
							contDisp.startsWith("L")) ) {
						//new cluster no examine count...
						if (isHivCase) {
							caseWorkerVO.setNewClustersNotNotifiedCount(parseIndexValue(caseWorkerVO.getNewClustersNotNotifiedCount()));
							summaryVO.setNewClustersNotNotifiedCount(parseIndexValue(summaryVO.getNewClustersNotNotifiedCount()));
						} else {
							caseWorkerVO.setNewClustersNoExamCount(parseIndexValue(caseWorkerVO.getNewClustersNoExamCount()));
							summaryVO.setNewClustersNoExamCount(parseIndexValue(summaryVO.getNewClustersNoExamCount()));
						}
						if (contDisp != null && contDisp.startsWith("G")) { //(CG)
							if (isHivCase) {
								caseWorkerVO.setNewClustersNotNotifiedInsufficientInfoCount(parseIndexValue(caseWorkerVO.getNewClustersNotNotifiedInsufficientInfoCount()));
								summaryVO.setNewClustersNotNotifiedInsufficientInfoCount(parseIndexValue(summaryVO.getNewClustersNotNotifiedInsufficientInfoCount()));
							} else {
								caseWorkerVO.setNewClustersInsufficientInfoCount(parseIndexValue(caseWorkerVO.getNewClustersInsufficientInfoCount()));
								summaryVO.setNewClustersInsufficientInfoCount(parseIndexValue(summaryVO.getNewClustersInsufficientInfoCount()));
							}
						} else if (contDisp != null && contDisp.startsWith("H")) { //(CH)
							if (isHivCase) {
								caseWorkerVO.setNewClustersNotNotifiedUnableToLocateCount(parseIndexValue(caseWorkerVO.getNewClustersNotNotifiedUnableToLocateCount()));
								summaryVO.setNewClustersNotNotifiedUnableToLocateCount(parseIndexValue(summaryVO.getNewClustersNotNotifiedUnableToLocateCount()));
							} else {
								caseWorkerVO.setNewClustersUnableToLocateCount(parseIndexValue(caseWorkerVO.getNewClustersUnableToLocateCount()));
								summaryVO.setNewClustersUnableToLocateCount(parseIndexValue(summaryVO.getNewClustersUnableToLocateCount()));
							}
						} else if (contDisp != null && contDisp.startsWith("J")) { //(CJ)
							if (isHivCase) {
								caseWorkerVO.setNewClustersNotNotifiedRefusedExamCount(parseIndexValue(caseWorkerVO.getNewClustersNotNotifiedRefusedExamCount()));
								summaryVO.setNewClustersNotNotifiedRefusedExamCount(parseIndexValue(summaryVO.getNewClustersNotNotifiedRefusedExamCount()));
							} else {
								caseWorkerVO.setNewClustersRefusedExamCount(parseIndexValue(caseWorkerVO.getNewClustersRefusedExamCount()));
								summaryVO.setNewClustersRefusedExamCount(parseIndexValue(summaryVO.getNewClustersRefusedExamCount()));
							}
						} else if (contDisp != null && contDisp.startsWith("K")) { //(CK)
							if (isHivCase) {
								caseWorkerVO.setNewClustersNotNotifiedOOJCount(parseIndexValue(caseWorkerVO.getNewClustersNotNotifiedOOJCount()));
								summaryVO.setNewClustersNotNotifiedOOJCount(parseIndexValue(summaryVO.getNewClustersNotNotifiedOOJCount()));
							} else {
								caseWorkerVO.setNewClustersOOJCount(parseIndexValue(caseWorkerVO.getNewClustersOOJCount()));
								summaryVO.setNewClustersOOJCount(parseIndexValue(summaryVO.getNewClustersOOJCount()));
							}
						} else if (contDisp != null && contDisp.startsWith("L")) { //(CL)
							if (isHivCase) {
								caseWorkerVO.setNewClustersNotNotifiedOtherCount(parseIndexValue(caseWorkerVO.getNewClustersNotNotifiedOtherCount()));
								summaryVO.setNewClustersNotNotifiedOtherCount(parseIndexValue(summaryVO.getNewClustersNotNotifiedOtherCount()));
							} else {					
								caseWorkerVO.setNewClustersOtherCount(parseIndexValue(caseWorkerVO.getNewClustersOtherCount()));
								summaryVO.setNewClustersOtherCount(parseIndexValue(summaryVO.getNewClustersOtherCount()));
							}
						} //why no V, X or Z on form? RDV, Deceased, Prev Rx
						//Speed of Exam Cluster
						Timestamp contactInvestigationStartDate = reportPatientDT.getContactInvestigationStartDate();
						Timestamp contactDispositionDate = reportPatientDT.getContactDispositionDate();
						if (contactInvestigationStartDate != null && contactDispositionDate != null) {
		
							long intStartVrsDispo = differenceInDays(contactDispositionDate, contactInvestigationStartDate);
							
							if(intStartVrsDispo<4){
								if (isHivCase) {
									caseWorkerVO.setSeNewClustersNotifiedWI3dCount(parseIntegerValue(caseWorkerVO.getSeNewClustersNotifiedWI3dCount()));	
									summaryVO.setSeNewClustersNotifiedWI3dCount(parseIntegerValue(summaryVO.getSeNewClustersNotifiedWI3dCount()));	
								} else {
									caseWorkerVO.setSeNewClustersExaminedWI3dCount(parseIntegerValue(caseWorkerVO.getSeNewClustersExaminedWI3dCount()));	
									summaryVO.setSeNewClustersExaminedWI3dCount(parseIntegerValue(summaryVO.getSeNewClustersExaminedWI3dCount()));
								}
							}
							if(intStartVrsDispo<6){
								if (isHivCase) {
									caseWorkerVO.setSeNewClustersNotifiedWI5dCount(parseIntegerValue(caseWorkerVO.getSeNewClustersNotifiedWI5dCount()));	
									summaryVO.setSeNewClustersNotifiedWI5dCount(parseIntegerValue(summaryVO.getSeNewClustersNotifiedWI5dCount()));	
								} else {
									caseWorkerVO.setSeNewClustersExaminedWI5dCount(parseIntegerValue(caseWorkerVO.getSeNewClustersExaminedWI5dCount()));	
									summaryVO.setSeNewClustersExaminedWI5dCount(parseIntegerValue(summaryVO.getSeNewClustersExaminedWI5dCount()));
								}
							}
							if(intStartVrsDispo<8){
								if (isHivCase) {
									caseWorkerVO.setSeNewClustersNotifiedWI7dCount(parseIntegerValue(caseWorkerVO.getSeNewClustersNotifiedWI7dCount()));	
									summaryVO.setSeNewClustersNotifiedWI7dCount(parseIntegerValue(summaryVO.getSeNewClustersNotifiedWI7dCount()));	
								} else {								
									caseWorkerVO.setSeNewClustersExaminedWI7dCount(parseIntegerValue(caseWorkerVO.getSeNewClustersExaminedWI7dCount()));	
									summaryVO.setSeNewClustersExaminedWI7dCount(parseIntegerValue(summaryVO.getSeNewClustersExaminedWI7dCount()));
								}
							}
							if(intStartVrsDispo<15){
								if (isHivCase) {
									caseWorkerVO.setSeNewClustersNotifiedWI14dCount(parseIntegerValue(caseWorkerVO.getSeNewClustersNotifiedWI14dCount()));	
									summaryVO.setSeNewClustersNotifiedWI14dCount(parseIntegerValue(summaryVO.getSeNewClustersNotifiedWI14dCount()));	
								} else {								
								caseWorkerVO.setSeNewClustersExaminedWI14dCount(parseIntegerValue(caseWorkerVO.getSeNewClustersExaminedWI14dCount()));	
								summaryVO.setSeNewClustersExaminedWI14dCount(parseIntegerValue(summaryVO.getSeNewClustersExaminedWI14dCount()));
								}
							}
						}
					} //Cluster - Contact Disposition not null and G,H,J,K,L
					if (contDisp != null && contDisp.startsWith("E")) {
						caseWorkerVO.setNewClustersPreviousRxCount(parseIndexValue(caseWorkerVO.getNewClustersPreviousRxCount()));
						summaryVO.setNewClustersPreviousRxCount(parseIndexValue(summaryVO.getNewClustersPreviousRxCount()));
					}
					if (contDisp == null) {  //No Dispo yet (CO)
							if (isHivCase) {
								caseWorkerVO.setNewClustersHivOpenCount(parseIndexValue(caseWorkerVO.getNewClustersHivOpenCount()));
								summaryVO.setNewClustersHivOpenCount(parseIndexValue(summaryVO.getNewClustersHivOpenCount()));
							} else {						
								caseWorkerVO.setNewClustersStdOpenCount(parseIndexValue(caseWorkerVO.getNewClustersPreviousRxCount()));
								summaryVO.setNewClustersStdOpenCount(parseIndexValue(summaryVO.getNewClustersPreviousRxCount()));
							}
					}					
				} //Cluster -- Referral basis A, S or C

				
			} //New Contact not already processed
		}
	
		//if we have data include the summary, if not, empty collection
		if (pa1VoColl.size() > 0)
			pa1VoColl.add(summaryVO);
		return pa1VoColl; 
		
	}
	
		/**
	 * Get the counts that key on an interview. 
	 * @param reportPatientDT
	 * @param caseWorkerVO
	 * @param summaryVO
	 */
	private void processNewInterview(ReportPatientDT reportPatientDT,
			Pa1VO caseWorkerVO, Pa1VO summaryVO) {
	
		
		//case assignments & outcomes section
		if(reportPatientDT.getInterviewDate()!=null && 
				reportPatientDT.getInterviewType() != null &&
				reportPatientDT.getInterviewType().equals(PA1Const.INITIAL_ORIGINAL_INTERVIEW)){
			caseWorkerVO.setCasesIxdCount(parseIntegerValue(caseWorkerVO.getCasesIxdCount()));
			summaryVO.setCasesIxdCount(parseIntegerValue(summaryVO.getCasesIxdCount()));
			if(reportPatientDT.getInterviewDate()!=null){
				Timestamp assignDate = reportPatientDT.getInitialInterviewerAssignmentDate();
				if (reportPatientDT.getIntervewerAssignmentDate() != null)
					assignDate = reportPatientDT.getIntervewerAssignmentDate();
				long intAssignVsInterviewed= differenceInDays(assignDate, reportPatientDT.getInterviewDate());
				
				if(intAssignVsInterviewed<4){
					caseWorkerVO.setCasesIxdWI3dCount(parseIntegerValue(caseWorkerVO.getCasesIxdWI3dCount()));	
					summaryVO.setCasesIxdWI3dCount(parseIntegerValue(summaryVO.getCasesIxdWI3dCount()));	
				}
				if(intAssignVsInterviewed<6){
					caseWorkerVO.setCasesIxdWI5dCount(parseIntegerValue(caseWorkerVO.getCasesIxdWI5dCount()));	
					summaryVO.setCasesIxdWI5dCount(parseIntegerValue(summaryVO.getCasesIxdWI5dCount()));	
				}
				if(intAssignVsInterviewed<8){
					caseWorkerVO.setCasesIxdWI7dCount(parseIntegerValue(caseWorkerVO.getCasesIxdWI7dCount()));	
					summaryVO.setCasesIxdWI7dCount(parseIntegerValue(summaryVO.getCasesIxdWI7dCount()));	
				}
				if(intAssignVsInterviewed<15){
					caseWorkerVO.setCasesIxdWI14dCount(parseIntegerValue(caseWorkerVO.getCasesIxdWI14dCount()));		
					summaryVO.setCasesIxdWI14dCount(parseIntegerValue(summaryVO.getCasesIxdWI14dCount()));	
				}
			}
		}//orig interv
		if(reportPatientDT.getInterviewDate()!=null && 
				reportPatientDT.getInterviewType() != null &&
				reportPatientDT.getInterviewType().equals(PA1Const.RE_INTERVIEW)){
			caseWorkerVO.setCasesReinterviewedCount(parseIntegerValue(caseWorkerVO.getCasesReinterviewedCount()));
			summaryVO.setCasesReinterviewedCount(parseIntegerValue(summaryVO.getCasesReinterviewedCount()));
		}		
	}

	/**
	 * Get the case counts for the first section.
	 * @param reportPatientDT
	 * @param caseWorkerVO
	 * @param summaryVO
	 */
	private void getCaseAssignmentsAndOutcomesCaseCounts(
			ReportPatientDT reportPatientDT, Pa1VO caseWorkerVO, Pa1VO summaryVO) {
		
		caseWorkerVO.setNumCasesAssigned(parseIntegerValue(caseWorkerVO.getNumCasesAssigned()));	
		summaryVO.setNumCasesAssigned(parseIntegerValue(summaryVO.getNumCasesAssigned()));
		
		if(reportPatientDT.getDateClosed()!=null){
			caseWorkerVO.setCasesClosedCount(parseIntegerValue(caseWorkerVO.getCasesClosedCount()));
			summaryVO.setCasesClosedCount(parseIntegerValue(summaryVO.getCasesClosedCount()));
		}
		
		if (reportPatientDT.getNineHundredStatus() != null) {
			String the900Status = reportPatientDT.getNineHundredStatus();
			if (the900Status.startsWith("3") || the900Status.startsWith("4") || the900Status.startsWith("5")) {
				caseWorkerVO.setHivPrevPositiveCount(parseIntegerValue(caseWorkerVO.getHivPrevPositiveCount()));
				summaryVO.setHivPrevPositiveCount(parseIntegerValue(summaryVO.getHivPrevPositiveCount()));
			}
		}
		if (reportPatientDT.getHivNineHundredTestInd() != null && reportPatientDT.getHivNineHundredTestInd().equals(PA1Const.YES)) {
			caseWorkerVO.setHivTestedCount(parseIntegerValue(caseWorkerVO.getHivTestedCount()));
			summaryVO.setHivTestedCount(parseIntegerValue(summaryVO.getHivTestedCount()));
		}
		if (reportPatientDT.getHivNineHundredResult() != null && reportPatientDT.getHivNineHundredResult().startsWith("1")) {
			caseWorkerVO.setHivNewPositiveCount(parseIntegerValue(caseWorkerVO.getHivNewPositiveCount()));
			summaryVO.setHivNewPositiveCount(parseIntegerValue(summaryVO.getHivNewPositiveCount()));
		}
		if (reportPatientDT.getHivPostTestNineHundredCounselling() != null && reportPatientDT.getHivPostTestNineHundredCounselling().equals(PA1Const.YES)) {
			caseWorkerVO.setHivPostTestCounselCount(parseIntegerValue(caseWorkerVO.getHivPostTestCounselCount()));
			summaryVO.setHivPostTestCounselCount(parseIntegerValue(summaryVO.getHivPostTestCounselCount()));
		}

	}

	/**
	 * Get the counts for the second section of the report.
	 * @param reportPatientDT
	 * @param caseWorkerVO
	 * @param summaryVO
	 */
	private void getPartnersAndClustersInitiatedCounts(
			ReportPatientDT reportPatientDT, Pa1VO caseWorkerVO, Pa1VO summaryVO) {
		
		getTotalPeriodPartners(reportPatientDT, caseWorkerVO, summaryVO);

	}
	/**
	 * Add up the total period partners.
	 * @param reportPatientDT
	 * @param caseWorkerVO
	 * @param summaryVO
	 */
	private void getTotalPeriodPartners(ReportPatientDT reportPatientDT,
			Pa1VO caseWorkerVO, Pa1VO summaryVO) {
		//total period partners
		int numPeriodPartnersFemale = 0;
		int numPeriodPartnersMale = 0;
		int numPeriodPartnersTrans = 0;
		if (reportPatientDT.getFemalePartners() != null) 
			numPeriodPartnersFemale = getIntValue(reportPatientDT.getFemalePartners());
		if (reportPatientDT.getMalePartners() != null) 
			numPeriodPartnersMale = getIntValue(reportPatientDT.getMalePartners());
		if (reportPatientDT.getTransgenderPartners() != null) 
			numPeriodPartnersTrans = getIntValue(reportPatientDT.getTransgenderPartners()); 
		int totalPeriodPartners = numPeriodPartnersFemale + numPeriodPartnersMale + numPeriodPartnersTrans;
		if (totalPeriodPartners != 0) {
			String curCountStr = caseWorkerVO.getTotalPeriodPartnersCount();
			int curCount = getIntValue(curCountStr) + totalPeriodPartners;
			caseWorkerVO.setTotalPeriodPartnersCount(curCount + "");
			curCountStr = summaryVO.getTotalPeriodPartnersCount();
			curCount = getIntValue(curCountStr) + totalPeriodPartners;
			summaryVO.setTotalPeriodPartnersCount(curCount + "");
		}
		
	}

	/**
	 * Get the worker name to display. Display format is quick code, dash,  followed by name.
	 * @param reportPatientDT
	 * @param interviewAssignOrCaseClose
	 * @return
	 */
	private String getRelevantWorker(ReportPatientDT reportPatientDT,
			String interviewAssignOrCaseClose) {
		
		StringBuffer sb = new StringBuffer("");
		if (interviewAssignOrCaseClose.contains("Case Close")) { //PA01 Case Management Report(Case Close Date)
			String investigatorQEC = reportPatientDT.getInvestigatorQEC();
			String investigatorName = reportPatientDT.getInvestigatorName();
			if (investigatorQEC != null && !investigatorQEC.trim().isEmpty()) 
				sb.append(investigatorQEC.trim() + " - ");
			if (investigatorName != null && !investigatorName.trim().isEmpty()) 
				sb.append(investigatorName.trim());					
		} else {
			String interviewerQEC = reportPatientDT.getInterviewerQEC();
			String interviewerName = reportPatientDT.getInterviewerName();
			if (interviewerQEC != null && !interviewerQEC.trim().isEmpty()) 
				sb.append(interviewerQEC.trim() + " - ");
			if (interviewerName != null && !interviewerName.trim().isEmpty()) 
				sb.append(interviewerName.trim());		
		}
		return sb.toString();
	}
	/**
	 * Initialize all the fields in the report with zero values. Set the Report Type to either HIV or STD.
	 * @param caseWorkerVO
	 * @param reportSTDorHIV
	 */
	private void initializeCaseWorkerVO(Pa1VO caseWorkerVO, String reportSTDorHIV) {
		caseWorkerVO.setReportType(reportSTDorHIV);
		caseWorkerVO.setWorkerLabel(PA1Const.WORKER_LABEL);
		caseWorkerVO.setCaseAssignmentsAndOutcomesSectionName(PA1Const.CASE_ASSIGNMENTS_AND_OUTCOMES_SECTION_NAME);
		caseWorkerVO.setNumCasesAssignedLabel(PA1Const.NUM_CASES_ASSIGNED_LABEL);
		caseWorkerVO.setNumCasesAssigned(PA1Const.ZERO_COUNT);
		
		caseWorkerVO.setCasesClosedLabel(PA1Const.CASES_CLOSED_LABEL);
		caseWorkerVO.setCasesClosedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setCasesClosedPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setCasesIxdLabel(PA1Const.CASES_IXD_LABEL);
		caseWorkerVO.setCasesIxdCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setCasesIxdPercent(PA1Const.ZERO_PERCENT);
		
		
		caseWorkerVO.setCasesIxdWI3dLabel(PA1Const.CASES_IXD_WI_3D_LABEL);
		caseWorkerVO.setCasesIxdWI3dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setCasesIxdWI3dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setCasesIxdWI5dLabel(PA1Const.CASES_IXD_WI_5D_LABEL);
		caseWorkerVO.setCasesIxdWI5dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setCasesIxdWI5dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setCasesIxdWI7dLabel(PA1Const.CASES_IXD_WI_7D_LABEL);
		caseWorkerVO.setCasesIxdWI7dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setCasesIxdWI7dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setCasesIxdWI14dLabel(PA1Const.CASES_IXD_WI_14D_LABEL);
		caseWorkerVO.setCasesIxdWI14dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setCasesIxdWI14dPercent(PA1Const.ZERO_PERCENT);		
		
		caseWorkerVO.setCasesReinterviewedLabel(PA1Const.CASES_REINTERVIEWED_LABEL);
		caseWorkerVO.setCasesReinterviewedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setCasesReinterviewedPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setHivPrevPositiveLabel(PA1Const.HIV_PREV_POS_LABEL);
		caseWorkerVO.setHivPrevPositiveCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setHivPrevPositivePercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setHivTestedLabel(PA1Const.HIV_TESTED_LABEL);
		caseWorkerVO.setHivTestedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setHivTestedPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setHivNewPositiveLabel(PA1Const.HIV_NEW_POSITIVE_LABEL);
		caseWorkerVO.setHivNewPositiveCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setHivNewPositivePercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setHivPostTestCounselLabel(PA1Const.HIV_POST_TEST_COUNSEL_LABEL);
		caseWorkerVO.setHivPostTestCounselCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setHivPostTestCounselPercent(PA1Const.ZERO_PERCENT);
		//STD only
		caseWorkerVO.setDiseaseInterventionIndexLabel(PA1Const.DISEASE_INTERVENTION_INDEX_LABEL);
		caseWorkerVO.setDiseaseInterventionIndex(PA1Const.ZERO_COUNT);

		caseWorkerVO.setTreatmentIndexLabel(PA1Const.TREATMENT_INDEX_LABEL);
		caseWorkerVO.setTreatmentIndex(PA1Const.ZERO_COUNT);
		
		caseWorkerVO.setCasesWithSourceIdentifiedLabel(PA1Const.CASES_WITH_SOURCE_IDENTIFIED_LABEL);
		caseWorkerVO.setCasesWithSourceIdentifiedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setCasesWithSourceIdentifiedPercent(PA1Const.ZERO_PERCENT);
		
		//HIV only
		caseWorkerVO.setPartnerNotificationIndexLabel(PA1Const.PARTNER_NOTIFICATION_INDEX_LABEL);
		caseWorkerVO.setPartnerNotificationIndex(PA1Const.ZERO_INDEX);
		
		caseWorkerVO.setTestingIndexLabel(PA1Const.TESTING_INDEX_LABEL);
		caseWorkerVO.setTestingIndex(PA1Const.ZERO_INDEX);
		
		//Partners and Clusters Initiated - Col1
		caseWorkerVO.setPartnersAndClustersInitiatedSectionName(PA1Const.PARTNERS_AND_CLUSTERS_INITIATED_SECTION_NAME);
		caseWorkerVO.setTotalPeriodPartnersLabel(PA1Const.TOTAL_PERIOD_PARTNERS_LABEL);
		caseWorkerVO.setTotalPeriodPartnersCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setTotalPeriodPartnersPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setTotalPartnersInitiatedLabel(PA1Const.TOTAL_PARTNERS_INITIATED_LABEL);
		caseWorkerVO.setTotalPartnersInitiatedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setTotalPartnersInitiatedPercent(PA1Const.ZERO_PERCENT);
		//orig interview
		caseWorkerVO.setPartnersInitiatedFromOILabel(PA1Const.PARTNERS_INITIATED_FROM_OI_LABEL);
		caseWorkerVO.setPartnersInitiatedFromOICount(PA1Const.ZERO_COUNT);
		//re-interview
		caseWorkerVO.setPartnersInitiatedFromRILabel(PA1Const.PARTNERS_INITIATED_FROM_RI_LABEL);
		caseWorkerVO.setPartnersInitiatedFromRICount(PA1Const.ZERO_COUNT);
		
		caseWorkerVO.setContactIndexLabel(PA1Const.CONTACT_INDEX_LABEL);
		caseWorkerVO.setContactIndex(PA1Const.ZERO_INDEX);
		
		caseWorkerVO.setCasesWithNoPartnersLabel(PA1Const.CASES_WITH_NO_PARTNER_LABEL);
		caseWorkerVO.setCasesWithNoPartnersCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setCasesWithNoPartnersPercent(PA1Const.ZERO_PERCENT);
		
		//Partners and Clusters Initiated - Col2
		caseWorkerVO.setTotalClusterInitiatedLabel(PA1Const.TOTAL_CLUSTER_INITIATED_LABEL);
		caseWorkerVO.setTotalClusterInitiatedCount(PA1Const.ZERO_COUNT);
		
		caseWorkerVO.setClusterIndexLabel(PA1Const.CLUSTER_INDEX_LABEL);
		caseWorkerVO.setClusterIndex(PA1Const.ZERO_INDEX);
		
		caseWorkerVO.setCasesWithNoClustersLabel(PA1Const.CASES_WITH_NO_CLUSTERS_LABEL);
		caseWorkerVO.setCasesWithNoClustersCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setCasesWithNoClustersPercent(PA1Const.ZERO_PERCENT);
		
		//STD Dispositions - Col1
		caseWorkerVO.setDispositionsPartnersAndClustersSectionName(PA1Const.DISPOSITIONS_PARTNERS_AND_CLUSTERS_SECTION_NAME);
		
		caseWorkerVO.setNewPartnersExaminedLabel(PA1Const.NEW_PARTNERS_EXAMINED_LABEL);
		caseWorkerVO.setNewPartnersExaminedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersExaminedPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersPreventativeRxLabel(PA1Const.NEW_PARTNERS_PREVENTATIVE_RX_LABEL);
		caseWorkerVO.setNewPartnersPreventativeRxCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersPreventativeRxPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersRefusedPrevRxLabel(PA1Const.NEW_PARTNERS_REFUSED_PREVENTATIVE_RX_LABEL);
		caseWorkerVO.setNewPartnersRefusedPrevRxCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersRefusedPrevRxPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersInfectedRxLabel(PA1Const.NEW_PARTNERS_INFECTED_RX_LABEL);
		caseWorkerVO.setNewPartnersInfectedRxCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersInfectedRxPercent(PA1Const.ZERO_PERCENT);

		caseWorkerVO.setNewPartnersInfectedNoRxLabel(PA1Const.NEW_PARTNERS_INFECTED_NO_RX_LABEL);
		caseWorkerVO.setNewPartnersInfectedNoRxCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersInfectedNoRxPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersNotInfectedLabel(PA1Const.NEW_PARTNERS_NOT_INFECTED_LABEL);
		caseWorkerVO.setNewPartnersNotInfectedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersNotInfectedPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersNoExamLabel(PA1Const.NEW_PARTNERS_NO_EXAM_LABEL);
		caseWorkerVO.setNewPartnersNoExamCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersNoExamPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersInsufficientInfoLabel(PA1Const.NEW_PARTNERS_INSUFFICIENT_INFO_LABEL);
		caseWorkerVO.setNewPartnersInsufficientInfoCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersInsufficientInfoPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersUnableToLocateLabel(PA1Const.NEW_PARTNERS_UNABLE_TO_LOCATE_LABEL);
		caseWorkerVO.setNewPartnersUnableToLocateCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersUnableToLocatePercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersRefusedExamLabel(PA1Const.NEW_PARTNERS_REFUSED_EXAM_LABEL);
		caseWorkerVO.setNewPartnersRefusedExamCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersRefusedExamPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersOOJLabel(PA1Const.NEW_PARTNERS_OOJ_LABEL);
		caseWorkerVO.setNewPartnersOOJCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersOOJPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersOtherLabel(PA1Const.NEW_PARTNERS_OTHER_LABEL);
		caseWorkerVO.setNewPartnersOtherCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersOtherPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersPreviousRxLabel(PA1Const.NEW_PARTNERS_PREVIOUS_RX_LABEL);
		caseWorkerVO.setNewPartnersPreviousRxCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersPreviousRxPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersStdOpenLabel(PA1Const.NEW_PARTNERS_STD_OPEN_LABEL);
		caseWorkerVO.setNewPartnersStdOpenCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersStdOpenPercent(PA1Const.ZERO_PERCENT);
		
		//STD Dispositions - Col2
		
		caseWorkerVO.setNewClustersExaminedLabel(PA1Const.NEW_CLUSTERS_EXAMINED_LABEL);
		caseWorkerVO.setNewClustersExaminedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersExaminedPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersPreventativeRxLabel(PA1Const.NEW_CLUSTERS_PREVENTATIVE_RX_LABEL);
		caseWorkerVO.setNewClustersPreventativeRxCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersPreventativeRxPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersRefusedPrevRxLabel(PA1Const.NEW_CLUSTERS_REFUSED_PREVENTATIVE_RX_LABEL);
		caseWorkerVO.setNewClustersRefusedPrevRxCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersRefusedPrevRxPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersInfectedRxLabel(PA1Const.NEW_CLUSTERS_INFECTED_RX_LABEL);
		caseWorkerVO.setNewClustersInfectedRxCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersInfectedRxPercent(PA1Const.ZERO_PERCENT);

		caseWorkerVO.setNewClustersInfectedNoRxLabel(PA1Const.NEW_CLUSTERS_INFECTED_NO_RX_LABEL);
		caseWorkerVO.setNewClustersInfectedNoRxCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersInfectedNoRxPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersNotInfectedLabel(PA1Const.NEW_CLUSTERS_NOT_INFECTED_LABEL);
		caseWorkerVO.setNewClustersNotInfectedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNotInfectedPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersNoExamLabel(PA1Const.NEW_CLUSTERS_NO_EXAM_LABEL);
		caseWorkerVO.setNewClustersNoExamCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNoExamPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersInsufficientInfoLabel(PA1Const.NEW_CLUSTERS_INSUFFICIENT_INFO_LABEL);
		caseWorkerVO.setNewClustersInsufficientInfoCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersInsufficientInfoPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersUnableToLocateLabel(PA1Const.NEW_CLUSTERS_UNABLE_TO_LOCATE_LABEL);
		caseWorkerVO.setNewClustersUnableToLocateCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersUnableToLocatePercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersRefusedExamLabel(PA1Const.NEW_CLUSTERS_REFUSED_EXAM_LABEL);
		caseWorkerVO.setNewClustersRefusedExamCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersRefusedExamPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersOOJLabel(PA1Const.NEW_CLUSTERS_OOJ_LABEL);
		caseWorkerVO.setNewClustersOOJCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersOOJPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersOtherLabel(PA1Const.NEW_CLUSTERS_OTHER_LABEL);
		caseWorkerVO.setNewClustersOtherCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersOtherPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersPreviousRxLabel(PA1Const.NEW_CLUSTERS_PREVIOUS_RX_LABEL);
		caseWorkerVO.setNewClustersPreviousRxCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersPreviousRxPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersStdOpenLabel(PA1Const.NEW_CLUSTERS_STD_OPEN_LABEL);
		caseWorkerVO.setNewClustersStdOpenCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersStdOpenPercent(PA1Const.ZERO_PERCENT);		
		
		//HIV Dispositions - Col1
		caseWorkerVO.setNewPartnersNotifiedLabel(PA1Const.NEW_PARTNERS_NOTIFIED_LABEL);
		caseWorkerVO.setNewPartnersNotifiedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersNotifiedPercent(PA1Const.ZERO_PERCENT);
		
		
		caseWorkerVO.setNewPartnersPrevNegNewPosLabel(PA1Const.NEW_PARTNERS_PREV_NEG_NEW_POS_LABEL);
		caseWorkerVO.setNewPartnersPrevNegNewPosCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersPrevNegNewPosPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersPrevNegStillNegLabel(PA1Const.NEW_PARTNERS_PREV_NEG_STILL_NEG_LABEL);
		caseWorkerVO.setNewPartnersPrevNegStillNegCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersPrevNegStillNegPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersPrevNegNoTestLabel(PA1Const.NEW_PARTNERS_PREV_NEG_NO_TEST_LABEL);
		caseWorkerVO.setNewPartnersPrevNegNoTestCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersPrevNegNoTestPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersNoPrevNewPosLabel(PA1Const.NEW_PARTNERS_NO_PREV_NEW_POS_LABEL);
		caseWorkerVO.setNewPartnersNoPrevNewPosCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersNoPrevNewPosPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersNoPrevNewNegLabel(PA1Const.NEW_PARTNERS_NO_PREV_NEW_NEG_LABEL);
		caseWorkerVO.setNewPartnersNoPrevNewNegCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersNoPrevNewNegPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersNoPrevNoTestLabel(PA1Const.NEW_PARTNERS_NO_PREV_NO_TEST_LABEL);
		caseWorkerVO.setNewPartnersNoPrevNoTestCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersNoPrevNoTestPercent(PA1Const.ZERO_PERCENT);		
	
		caseWorkerVO.setNewPartnersNotNotifiedLabel(PA1Const.NEW_PARTNERS_NOT_NOTIFIED_LABEL);
		caseWorkerVO.setNewPartnersNotNotifiedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersNotNotifiedPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersNotNotifiedInsufficientInfoLabel(PA1Const.NEW_PARTNERS_NOT_NOTIFIED_INSUFFICIENT_INFO_LABEL);
		caseWorkerVO.setNewPartnersNotNotifiedInsufficientInfoCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersNotNotifiedInsufficientInfoPercent(PA1Const.ZERO_PERCENT);		
		
		caseWorkerVO.setNewPartnersNotNotifiedUnableToLocateLabel(PA1Const.NEW_PARTNERS_NOT_NOTIFIED_UNABLE_TO_LOCATE_LABEL);
		caseWorkerVO.setNewPartnersNotNotifiedUnableToLocateCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersNotNotifiedUnableToLocatePercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersNotNotifiedRefusedExamLabel(PA1Const.NEW_PARTNERS_NOT_NOTIFIED_REFUSED_EXAM_LABEL);
		caseWorkerVO.setNewPartnersRefusedExamCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersRefusedExamPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersNotNotifiedOOJLabel(PA1Const.NEW_PARTNERS_NOT_NOTIFIED_OOJ_LABEL);
		caseWorkerVO.setNewPartnersNotNotifiedOOJCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersNotNotifiedOOJPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersNotNotifiedOtherLabel(PA1Const.NEW_PARTNERS_NOT_NOTIFIED_OTHER_LABEL);
		caseWorkerVO.setNewPartnersNotNotifiedOtherCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersNotNotifiedOtherPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersPrevPosLabel(PA1Const.NEW_PARTNERS_PREV_POS_LABEL);
		caseWorkerVO.setNewPartnersPrevPosCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersPrevPosPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewPartnersHivOpenLabel(PA1Const.NEW_PARTNERS_HIV_OPEN_LABEL);
		caseWorkerVO.setNewPartnersHivOpenCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewPartnersHivOpenPercent(PA1Const.ZERO_PERCENT);
		
		//HIV Dispositions - Col2
		caseWorkerVO.setNewClustersNotifiedLabel(PA1Const.NEW_CLUSTERS_NOTIFIED_LABEL);
		caseWorkerVO.setNewClustersNotifiedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNotifiedPercent(PA1Const.ZERO_PERCENT);
		
		
		caseWorkerVO.setNewClustersPrevNegNewPosLabel(PA1Const.NEW_CLUSTERS_PREV_NEG_NEW_POS_LABEL);
		caseWorkerVO.setNewClustersPrevNegNewPosCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersPrevNegNewPosPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersPrevNegStillNegLabel(PA1Const.NEW_CLUSTERS_PREV_NEG_STILL_NEG_LABEL);
		caseWorkerVO.setNewClustersPrevNegStillNegCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersPrevNegStillNegPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersPrevNegNoTestLabel(PA1Const.NEW_CLUSTERS_PREV_NEG_NO_TEST_LABEL);
		caseWorkerVO.setNewClustersPrevNegNoTestCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersPrevNegNoTestPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersNoPrevNewPosLabel(PA1Const.NEW_CLUSTERS_NO_PREV_NEW_POS_LABEL);
		caseWorkerVO.setNewClustersNoPrevNewPosCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNoPrevNewPosPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersNoPrevNewNegLabel(PA1Const.NEW_CLUSTERS_NO_PREV_NEW_NEG_LABEL);
		caseWorkerVO.setNewClustersNoPrevNewNegCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNoPrevNewNegPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersNoPrevNoTestLabel(PA1Const.NEW_CLUSTERS_NO_PREV_NO_TEST_LABEL);
		caseWorkerVO.setNewClustersNoPrevNoTestCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNoPrevNoTestPercent(PA1Const.ZERO_PERCENT);	
		
		caseWorkerVO.setNewClustersNotNotifiedLabel(PA1Const.NEW_CLUSTERS_NOT_NOTIFIED_LABEL);
		caseWorkerVO.setNewClustersNotNotifiedCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNotNotifiedPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersNotNotifiedInsufficientInfoLabel(PA1Const.NEW_CLUSTERS_NOT_NOTIFIED_INSUFFICIENT_INFO_LABEL);
		caseWorkerVO.setNewClustersNotNotifiedInsufficientInfoCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNotNotifiedInsufficientInfoPercent(PA1Const.ZERO_PERCENT);		
		
		caseWorkerVO.setNewClustersNotNotifiedUnableToLocateLabel(PA1Const.NEW_CLUSTERS_NOT_NOTIFIED_UNABLE_TO_LOCATE_LABEL);
		caseWorkerVO.setNewClustersNotNotifiedUnableToLocateCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNotNotifiedUnableToLocatePercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersNotNotifiedRefusedExamLabel(PA1Const.NEW_CLUSTERS_NOT_NOTIFIED_REFUSED_EXAM_LABEL);
		caseWorkerVO.setNewClustersNotNotifiedRefusedExamCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNotNotifiedRefusedExamPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersNotNotifiedOOJLabel(PA1Const.NEW_CLUSTERS_NOT_NOTIFIED_OOJ_LABEL);
		caseWorkerVO.setNewClustersNotNotifiedOOJCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNotNotifiedOOJPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersNotNotifiedOtherLabel(PA1Const.NEW_CLUSTERS_NOT_NOTIFIED_OTHER_LABEL);
		caseWorkerVO.setNewClustersNotNotifiedOtherCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersNotNotifiedOtherPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersHivPrevPosLabel(PA1Const.NEW_CLUSTERS_HIV_PREV_POS_LABEL);
		caseWorkerVO.setNewClustersHivPrevPosCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersHivPrevPosPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setNewClustersHivOpenLabel(PA1Const.NEW_CLUSTERS_HIV_OPEN_LABEL);
		caseWorkerVO.setNewClustersHivOpenCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setNewClustersHivOpenPercent(PA1Const.ZERO_PERCENT);		
		
		//Speed of Exam STD Section - Col1
		
		caseWorkerVO.setSpeedOfExamSectionName(PA1Const.SPEED_OF_EXAM_STD_SECTION_NAME);
		
		caseWorkerVO.setSeNewPartnersExaminedLabel(PA1Const.SE_NEW_PARTNERS_EXAMINED_LABEL);
		caseWorkerVO.setSeNewPartnersExaminedCount(PA1Const.ZERO_COUNT);
		
		caseWorkerVO.setSeNewPartnersExaminedWI3dLabel(PA1Const.SE_NEW_PARTNERS_EXAMINED_WI3D_LABEL);
		caseWorkerVO.setSeNewPartnersExaminedWI3dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewPartnersExaminedWI3dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setSeNewPartnersExaminedWI5dLabel(PA1Const.SE_NEW_PARTNERS_EXAMINED_WI5D_LABEL);
		caseWorkerVO.setSeNewPartnersExaminedWI5dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewPartnersExaminedWI5dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setSeNewPartnersExaminedWI7dLabel(PA1Const.SE_NEW_PARTNERS_EXAMINED_WI7D_LABEL);
		caseWorkerVO.setSeNewPartnersExaminedWI7dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewPartnersExaminedWI7dPercent(PA1Const.ZERO_PERCENT);
			
		caseWorkerVO.setSeNewPartnersExaminedWI14dLabel(PA1Const.SE_NEW_PARTNERS_EXAMINED_WI14D_LABEL);
		caseWorkerVO.setSeNewPartnersExaminedWI14dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewPartnersExaminedWI14dPercent(PA1Const.ZERO_PERCENT);
	
		//Speed of Exam STD Section -Col2
		caseWorkerVO.setSeNewClustersExaminedLabel(PA1Const.SE_NEW_CLUSTERS_EXAMINED_LABEL);
		caseWorkerVO.setSeNewPartnersExaminedCount(PA1Const.ZERO_COUNT);
		
		caseWorkerVO.setSeNewClustersExaminedWI3dLabel(PA1Const.SE_NEW_CLUSTERS_EXAMINED_WI3D_LABEL);
		caseWorkerVO.setSeNewClustersExaminedWI3dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewClustersExaminedWI3dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setSeNewClustersExaminedWI5dLabel(PA1Const.SE_NEW_CLUSTERS_EXAMINED_WI5D_LABEL);
		caseWorkerVO.setSeNewClustersExaminedWI5dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewClustersExaminedWI5dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setSeNewClustersExaminedWI7dLabel(PA1Const.SE_NEW_CLUSTERS_EXAMINED_WI7D_LABEL);
		caseWorkerVO.setSeNewClustersExaminedWI7dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewClustersExaminedWI7dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setSeNewClustersExaminedWI14dLabel(PA1Const.SE_NEW_CLUSTERS_EXAMINED_WI14D_LABEL);
		caseWorkerVO.setSeNewClustersExaminedWI14dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewClustersExaminedWI14dPercent(PA1Const.ZERO_PERCENT);
		
		//Speed of Exam HIV Section - Col1
		
		caseWorkerVO.setSpeedOfNotificationSectionName(PA1Const.SPEED_OF_EXAM_STD_SECTION_NAME);
		
		caseWorkerVO.setSeNewPartnersNotifiedLabel(PA1Const.SE_NEW_PARTNERS_NOTIFIED_LABEL);
		caseWorkerVO.setSeNewPartnersNotifiedCount(PA1Const.ZERO_COUNT);
		
		caseWorkerVO.setSeNewPartnersNotifiedWI3dLabel(PA1Const.SE_NEW_PARTNERS_EXAMINED_WI3D_LABEL);
		caseWorkerVO.setSeNewPartnersNotifiedWI3dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewPartnersNotifiedWI3dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setSeNewPartnersNotifiedWI5dLabel(PA1Const.SE_NEW_PARTNERS_EXAMINED_WI5D_LABEL);
		caseWorkerVO.setSeNewPartnersNotifiedWI5dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewPartnersNotifiedWI5dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setSeNewPartnersNotifiedWI7dLabel(PA1Const.SE_NEW_PARTNERS_EXAMINED_WI7D_LABEL);
		caseWorkerVO.setSeNewPartnersNotifiedWI7dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewPartnersNotifiedWI7dPercent(PA1Const.ZERO_PERCENT);
			
		caseWorkerVO.setSeNewPartnersNotifiedWI14dLabel(PA1Const.SE_NEW_PARTNERS_NOTIFIED_WI14D_LABEL);
		caseWorkerVO.setSeNewPartnersNotifiedWI14dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewPartnersNotifiedWI14dPercent(PA1Const.ZERO_PERCENT);

		//Speed of Exam HIV Section -Col2
		caseWorkerVO.setSeNewClustersNotifiedLabel(PA1Const.SE_NEW_CLUSTERS_NOTIFIED_LABEL);
		caseWorkerVO.setSeNewPartnersNotifiedCount(PA1Const.ZERO_COUNT);
		
		caseWorkerVO.setSeNewClustersNotifiedWI3dLabel(PA1Const.SE_NEW_CLUSTERS_NOTIFIED_WI3D_LABEL);
		caseWorkerVO.setSeNewClustersNotifiedWI3dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewClustersNotifiedWI3dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setSeNewClustersNotifiedWI5dLabel(PA1Const.SE_NEW_CLUSTERS_NOTIFIED_WI5D_LABEL);
		caseWorkerVO.setSeNewClustersNotifiedWI5dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewClustersNotifiedWI5dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setSeNewClustersNotifiedWI7dLabel(PA1Const.SE_NEW_CLUSTERS_NOTIFIED_WI7D_LABEL);
		caseWorkerVO.setSeNewClustersNotifiedWI7dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewClustersNotifiedWI7dPercent(PA1Const.ZERO_PERCENT);
		
		caseWorkerVO.setSeNewClustersNotifiedWI14dLabel(PA1Const.SE_NEW_CLUSTERS_NOTIFIED_WI14D_LABEL);
		caseWorkerVO.setSeNewClustersNotifiedWI14dCount(PA1Const.ZERO_COUNT);
		caseWorkerVO.setSeNewClustersNotifiedWI14dPercent(PA1Const.ZERO_PERCENT);
			
		return;
	}
	/**
	 * When we are looking at a Contact we have the contact interview key. We need a map of the Interview Type.
	 * @param coll
	 * @return Interview Key, Interview Type Desc i.e. Initial/Original or Re-Interview
	 */
	private HashMap<Long, String> getTheInterviewTypeMap(Collection<Object> coll) {
		HashMap<Long, String> interviewTypeMap = new HashMap<Long, String>();
		Iterator<Object> iter = coll.iterator();
		while(iter.hasNext()){
			ReportPatientDT reportPatientDT= (ReportPatientDT)iter.next();
			if (reportPatientDT.getInterviewKey() != null && reportPatientDT.getInterviewType() != null)
				interviewTypeMap.put(reportPatientDT.getInterviewKey(), reportPatientDT.getInterviewType());
		}
		return interviewTypeMap;
	}
	/**
	 * Increment the string int value by one and return the string.
	 * @param numberString
	 * @return
	 */
	private String parseIntegerValue(String numberString){
		String returnValue=(getIntValue(numberString)+1)+"";
		return returnValue;
	}
	/**
	 *  We have to return and compute the index at the end of processing for the Worker.
	 *  So as we are processing we will keep it as a count.
	 * @param indexString
	 * @return currentCount
	 */
	private String parseIndexValue(String indexString){
		if (indexString.equals(PA1Const.ZERO_INDEX))
			return("1");
		String returnValue=(getIntValue(indexString)+1)+"";
		return returnValue;
	}
	/**
	 * Get the int value for the string. Return 0 if err.
	 * @param intStr
	 * @return
	 */
	private static int getIntValue(String intStr) {
		int intVal = 0;
		if (intStr == null || intStr.isEmpty())
			return intVal;
	    try
	    {
	      intVal = Integer.parseInt(intStr.trim());
	    }
	    catch (NumberFormatException nfe)
	    {
	      logger.debug("number format converting string to int for string " + intStr);
	    }
	    return intVal;
	}
	
	public static long differenceInDays(Timestamp fromTime, Timestamp toTime){
		if (fromTime == null || toTime == null)
			return 0L;
	
		long diff = toTime.getTime() - fromTime.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		return diffDays;
	}
	/**
	 * If diagnosis starts with '9' it is HIV case.
	 * @param investigationDiagnosis
	 * @return
	 */
	private boolean isThisHivCase(String investigationDiagnosis) {
		if (investigationDiagnosis == null)
			return false;
		else if (investigationDiagnosis.startsWith("9"))
			return true;
		
		return false;
		
	}
	/**
	 * Does the Diag List contain an HIV condition?
	 * @param diagnosisList
	 * @return
	 */
	private boolean hivCaseInDiagnosisList(String diagnosisList) {
		if (diagnosisList != null && (diagnosisList.contains("900") || (diagnosisList.contains("950")))) //HIV or AIDS
			return true;
		
		return false;
		
	}	

	/**
	 * Percentages are calculated at the end for each worker.
	 * @param caseWorkerVO
	 */
	private void computePercentagesAndIndexesForWorker(Pa1VO caseWorkerVO) {
		
		calculatePercentagesForCaseAssignmentsAndOutcomesSection(caseWorkerVO);
		calculateIndexesForCaseAssignmentsAndOutcomesSection(caseWorkerVO);
		calculatePercentagesForPartnersAndClustersInitiatedSection(caseWorkerVO);
		calculateIndexesForPartnersAndClustersInitiatedSection(caseWorkerVO);
		calculatePercentagesForStdPartnerDispositions(caseWorkerVO);
		calculatePercentagesForStdClusterDispositions(caseWorkerVO);
		calculatePercentagesForHivPartnerDispositions(caseWorkerVO);
		calculatePercentagesForHivClustersDispositions(caseWorkerVO);		
		calculatePercentagesForNewPartnersStdSpeedOfExam(caseWorkerVO);
		calculatePercentagesForNewClustersStdSpeedOfExam(caseWorkerVO);
		calculatePercentagesForNewPartnersHivSpeedOfNotification(caseWorkerVO);
		calculatePercentagesForNewClustersHivSpeedOfNotification(caseWorkerVO);	

		return;
		
	}

	/**
	 * Calculate percentages for thr first section.
	 * @param caseWorkerVO
	 */
	private void calculatePercentagesForCaseAssignmentsAndOutcomesSection(
			Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int numCasesAssigned = getIntValue(caseWorkerVO.getNumCasesAssigned());
		if (numCasesAssigned != 0) {
			int numCasesClosed = getIntValue(caseWorkerVO.getCasesClosedCount());
			if (numCasesClosed != 0) {
				try {
					float percentageCasesClosed = (float) (numCasesClosed / numCasesAssigned) * 100;
					caseWorkerVO.setCasesClosedPercent(numberFormat.format(percentageCasesClosed) + "%");
				} catch (NumberFormatException nfe) {
					logger.warn("Number divide or format error for percentageCasesClosed?");		
				}
			}
			int numCasesIxd = getIntValue(caseWorkerVO.getCasesIxdCount());
			if (numCasesIxd != 0) {
				try {
					float percentageCasesInterviewed = (float) (numCasesIxd / numCasesAssigned) * 100;
					caseWorkerVO.setCasesClosedPercent(numberFormat.format(percentageCasesInterviewed) + "%");
				} catch (NumberFormatException nfe) {
					logger.warn("Number divide or format error for percentageCasesInterviewed?");		
				}
				int numCasesIxd3D = getIntValue(caseWorkerVO.getCasesIxdWI3dCount());
				if (numCasesIxd3D != 0) {
					try {
						float percentageCasesInterviewed3D = (float) (numCasesIxd3D / numCasesIxd) * 100;
						caseWorkerVO.setCasesIxdWI3dPercent(numberFormat.format(percentageCasesInterviewed3D) + "%");
					} catch (NumberFormatException nfe) {
						logger.warn("Number divide or format error for percentageCasesInterviewed3D?");		
					}
				}
				int numCasesIxd5D = getIntValue(caseWorkerVO.getCasesIxdWI5dCount());
				if (numCasesIxd5D != 0) {
					try {
						float percentageCasesInterviewed5D = (float) (numCasesIxd5D / numCasesIxd) * 100;
						caseWorkerVO.setCasesIxdWI5dPercent(numberFormat.format(percentageCasesInterviewed5D) + "%");
					} catch (NumberFormatException nfe) {
						logger.warn("Number divide or format error for percentageCasesInterviewed5D?");		
					}
				}
				int numCasesIxd7D = getIntValue(caseWorkerVO.getCasesIxdWI7dCount());
				if (numCasesIxd7D != 0) {
					try {
						float percentageCasesInterviewed7D = (float) (numCasesIxd7D / numCasesIxd) * 100;
						caseWorkerVO.setCasesIxdWI7dPercent(numberFormat.format(percentageCasesInterviewed7D) + "%");
					} catch (NumberFormatException nfe) {
						logger.warn("Number divide or format error for percentageCasesInterviewed7D?");		
					}
				}
				int numCasesIxd14D = getIntValue(caseWorkerVO.getCasesIxdWI14dCount());
				if (numCasesIxd14D != 0) {
					try {
						float percentageCasesInterviewed14D = (float) (numCasesIxd14D / numCasesIxd) * 100;
						caseWorkerVO.setCasesIxdWI14dPercent(numberFormat.format(percentageCasesInterviewed14D) + "%");
					} catch (NumberFormatException nfe) {
						logger.warn("Number divide or format error for percentageCasesInterviewed14D?");		
					}
				}				
			}
			
			int casesReinterviewedCount = getIntValue(caseWorkerVO.getCasesReinterviewedCount());
			if (casesReinterviewedCount != 0) {
				try {
					float percentageCasesReinterviewedCount = (float) (casesReinterviewedCount / numCasesAssigned) * 100;
					caseWorkerVO.setCasesReinterviewedPercent(numberFormat.format(percentageCasesReinterviewedCount) + "%");
				} catch (NumberFormatException nfe) {
					logger.warn("Number divide or format error for percentageCasesReinterviewed?");		
				}
			}			
			int hivPrevPosCount = getIntValue(caseWorkerVO.getHivPrevPositiveCount());
			if (hivPrevPosCount != 0) {
				try {
					float percentageHivPrevPositive = (float) (hivPrevPosCount / numCasesAssigned) * 100;
					caseWorkerVO.setHivPrevPositivePercent(numberFormat.format(percentageHivPrevPositive) + "%");
				} catch (NumberFormatException nfe) {
					logger.warn("Number divide or format error for percentageHivPrevPositive?");		
				}
			}
			int hivTestedCount = getIntValue(caseWorkerVO.getHivTestedCount());
			if (hivTestedCount != 0) {
				try {
					float percentageHivTested = (float) (hivTestedCount / numCasesAssigned) * 100;
					caseWorkerVO.setHivTestedPercent(numberFormat.format(percentageHivTested) + "%");
				} catch (NumberFormatException nfe) {
					logger.warn("Number divide or format error for percentageHivTested?");		
				}
			}			
			int hivNewPositiveCount = getIntValue(caseWorkerVO.getHivNewPositiveCount());
			if (hivNewPositiveCount != 0) {
				try {
					float percentageHivNewPos = (float) (hivNewPositiveCount / numCasesAssigned) * 100;
					caseWorkerVO.setHivNewPositivePercent(numberFormat.format(percentageHivNewPos) + "%");
				} catch (NumberFormatException nfe) {
					logger.warn("Number divide or format error for percentageHivTested?");		
				}
			}
			int hivPostTestCounselCount = getIntValue(caseWorkerVO.getHivPostTestCounselCount());
			if (hivPostTestCounselCount != 0) {
				try {
					float percentageHivPostTestCounsel = (float) (hivPostTestCounselCount / hivTestedCount) * 100;
					caseWorkerVO.setHivPostTestCounselPercent(numberFormat.format(percentageHivPostTestCounsel) + "%");
				} catch (NumberFormatException nfe) {
					logger.warn("Number divide or format error for percentageHivPostTestCounsel?");		
				}
			}
			int casesWithSourceIdentifiedCount = getIntValue(caseWorkerVO.getCasesWithSourceIdentifiedCount());
			if (casesWithSourceIdentifiedCount != 0) {
				try {
					float percentageCasesWithSourceIdentified = (float) (casesWithSourceIdentifiedCount / numCasesAssigned) * 100;
					caseWorkerVO.setCasesWithSourceIdentifiedPercent(numberFormat.format(percentageCasesWithSourceIdentified) + "%");
				} catch (NumberFormatException nfe) {
					logger.warn("Number divide or format error for percentageHivPostTestCounsel?");		
				}
			}
		}
		
	}
	/**
	 * Calculate Indexes for the first section.
	 * @param caseWorkerVO
	 */
	private void calculateIndexesForCaseAssignmentsAndOutcomesSection(
			Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int diseasePreventionCount = getIntValue(caseWorkerVO.getDiseaseInterventionIndex());
		int interviewCount = getIntValue(caseWorkerVO.getCasesIxdCount()); 
		if (diseasePreventionCount!= 0 && interviewCount != 0) {
			try {
				float diseaseInterventionIndex = (float) (diseasePreventionCount / interviewCount) * 100;
				caseWorkerVO.setDiseaseInterventionIndex(numberFormat.format(diseaseInterventionIndex) + "");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for diseaseInterventionIndex?");		
			}
		}
		
		int treatmentCount = getIntValue(caseWorkerVO.getTreatmentIndex());
		if (treatmentCount!= 0 && interviewCount != 0) {
			try {
				float treatmentIndex = (float) (treatmentCount / interviewCount) * 100;
				caseWorkerVO.setTreatmentIndex(numberFormat.format(treatmentIndex) + "");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for treatmentIndex?");		
			}
		}
	}

	/**
	 * Calculate indexed for the second Partners and CLusters section.
	 * @param caseWorkerVO
	 */
	private void calculateIndexesForPartnersAndClustersInitiatedSection(
			Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int interviewCount = getIntValue(caseWorkerVO.getCasesIxdCount()); 
		int totalClustersInitiated = getIntValue(caseWorkerVO.getTotalClusterInitiatedCount());
		if (totalClustersInitiated!= 0 && interviewCount != 0) {
			try {
				float clusterIndex = (float) (totalClustersInitiated / interviewCount) * 100;
				caseWorkerVO.setClusterIndex(numberFormat.format(clusterIndex) + "");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for clusterIndex?");		
			}
		}		
	}

	/**
	 * Second section percentages.
	 * @param caseWorkerVO
	 */
	private void calculatePercentagesForPartnersAndClustersInitiatedSection(
			Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int totalPartnersInitiated = getIntValue(caseWorkerVO.getTotalPartnersInitiatedCount());
		int totalPeriodPartners = getIntValue(caseWorkerVO.getTotalPeriodPartnersCount());
		if (totalPartnersInitiated != 0 && totalPeriodPartners != 0) {
			try {
				float percentageTotalPeriodPartners = (float) (totalPeriodPartners / totalPartnersInitiated) * 100;
				caseWorkerVO.setTotalPeriodPartnersPercent(numberFormat.format(percentageTotalPeriodPartners) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageHivPostTestCounsel?");		
			}
		}
		
		int casesWithNoPartners = getIntValue(caseWorkerVO.getCasesWithNoPartnersCount());
		if (casesWithNoPartners != 0 && totalPartnersInitiated != 0) {
			try {
				float percentageCasesWithNoPartners = (float) (casesWithNoPartners / totalPartnersInitiated) * 100;
				caseWorkerVO.setCasesWithNoPartnersPercent(numberFormat.format(casesWithNoPartners) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageCasesWithNoPartners?");		
			}
		}
		int totalClustersInitiated = getIntValue(caseWorkerVO.getTotalClusterInitiatedCount());
		int casesWithNoClusters = getIntValue(caseWorkerVO.getCasesWithNoClustersCount());
		if (casesWithNoClusters != 0 && totalClustersInitiated != 0) {
			try {
				float percentageCasesWithNoClusters = (float) (casesWithNoClusters/ totalClustersInitiated) * 100;
				caseWorkerVO.setCasesWithNoClustersPercent(numberFormat.format(casesWithNoClusters) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageCasesWithNoClusters?");		
			}
		}
	}
	/**
	 * Get Percentages for the STD Dispositions left side section.
	 * @param caseWorkerVO
	 */
	private void calculatePercentagesForStdPartnerDispositions(Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int newPartnersExamined = getIntValue(caseWorkerVO.getNewPartnersExaminedCount());
		int totalPartnersInitiated = getIntValue(caseWorkerVO.getTotalPartnersInitiatedCount());
		if (totalPartnersInitiated != 0 && newPartnersExamined != 0) {
			try {
				float percentageNewPartnersExamined = (float) (newPartnersExamined / totalPartnersInitiated) * 100;
				caseWorkerVO.setNewPartnersExaminedPercent(numberFormat.format(percentageNewPartnersExamined) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersExamined?");		
			}
		}
		
		int newPartnersPreventativeRx = getIntValue(caseWorkerVO.getNewPartnersPreventativeRxCount());
		if (newPartnersExamined != 0 && newPartnersPreventativeRx != 0) {
			try {
				float percentageNewPartnersPreventativeRx = (float) (newPartnersPreventativeRx / newPartnersExamined) * 100;
				caseWorkerVO.setNewPartnersPreventativeRxPercent(numberFormat.format(percentageNewPartnersPreventativeRx) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersPreventativeRx?");		
			}
		}		
		
		int newPartnersRefusedPrevRx = getIntValue(caseWorkerVO.getNewPartnersRefusedPrevRxCount());
		if (newPartnersExamined != 0 && newPartnersRefusedPrevRx != 0) {
			try {
				float percentageNewPartnersRefusedPrevRx = (float) (newPartnersRefusedPrevRx / newPartnersExamined) * 100;
				caseWorkerVO.setNewPartnersRefusedPrevRxPercent(numberFormat.format(percentageNewPartnersRefusedPrevRx) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersRefusedPrevRx?");		
			}
		}
		int newPartnersInfectedRx = getIntValue(caseWorkerVO.getNewPartnersInfectedRxCount());
		if (newPartnersExamined != 0 && newPartnersInfectedRx != 0) {
			try {
				float percentageNewPartnersInfectedRx = (float) (newPartnersInfectedRx / newPartnersExamined) * 100;
				caseWorkerVO.setNewPartnersInfectedRxPercent(numberFormat.format(percentageNewPartnersInfectedRx) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersInfectedRx?");		
			}
		}
		int newPartnersInfectedNoRx = getIntValue(caseWorkerVO.getNewPartnersInfectedNoRxCount());
		if (newPartnersExamined != 0 && newPartnersInfectedNoRx != 0) {
			try {
				float percentageNewPartnersInfectedNoRx = (float) (newPartnersInfectedNoRx / newPartnersExamined) * 100;
				caseWorkerVO.setNewPartnersInfectedNoRxPercent(numberFormat.format(percentageNewPartnersInfectedNoRx) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersInfectedNoRx?");		
			}
		}		
		int newPartnersNotInfected = getIntValue(caseWorkerVO.getNewPartnersNotInfectedCount());
		if (newPartnersExamined != 0 && newPartnersNotInfected != 0) {
			try {
				float percentageNewPartnersNotInfected = (float) (newPartnersNotInfected / newPartnersExamined) * 100;
				caseWorkerVO.setNewPartnersNotInfectedPercent(numberFormat.format(percentageNewPartnersNotInfected) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersNotInfected?");		
			}
		}				
		int newPartnersNoExam= getIntValue(caseWorkerVO.getNewPartnersNoExamCount());
		if (totalPartnersInitiated != 0 && newPartnersNoExam != 0) {
			try {
				float percentageNewPartnersNoExam = (float) (newPartnersNoExam / totalPartnersInitiated) * 100;
				caseWorkerVO.setNewPartnersNoExamPercent(numberFormat.format(percentageNewPartnersNoExam) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersNoExam?");		
			}
		}
		int newPartnersInsufficientInfo = getIntValue(caseWorkerVO.getNewPartnersInsufficientInfoCount());
		if (newPartnersNoExam != 0 && newPartnersInsufficientInfo != 0) {
			try {
				float percentageNewPartnersInsufficientInfo = (float) (newPartnersInsufficientInfo / newPartnersNoExam) * 100;
				caseWorkerVO.setNewPartnersInsufficientInfoPercent(numberFormat.format(percentageNewPartnersInsufficientInfo) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersInsufficientInfo?");		
			}
		}
		int newPartnersUnableToLocate = getIntValue(caseWorkerVO.getNewPartnersUnableToLocateCount());
		if (newPartnersNoExam != 0 && newPartnersUnableToLocate != 0) {
			try {
				float percentageNewPartnersUnableToLocate = (float) (newPartnersUnableToLocate / newPartnersNoExam) * 100;
				caseWorkerVO.setNewPartnersUnableToLocatePercent(numberFormat.format(percentageNewPartnersUnableToLocate) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersUnableToLocate?");		
			}
		}		
		int newPartnersRefusedExam = getIntValue(caseWorkerVO.getNewPartnersRefusedExamCount());
		if (newPartnersNoExam != 0 && newPartnersRefusedExam != 0) {
			try {
				float percentageNewPartnersRefusedExam = (float) (newPartnersRefusedExam / newPartnersNoExam) * 100;
				caseWorkerVO.setNewPartnersRefusedExamPercent(numberFormat.format(percentageNewPartnersRefusedExam) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersRefusedExam?");		
			}
		}	
		int newPartnersOOJ = getIntValue(caseWorkerVO.getNewPartnersOOJCount());
		if (newPartnersNoExam != 0 && newPartnersOOJ != 0) {
			try {
				float percentageNewPartnersOOJ = (float) (newPartnersOOJ / newPartnersNoExam) * 100;
				caseWorkerVO.setNewPartnersOOJPercent(numberFormat.format(percentageNewPartnersOOJ) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersOOJ?");		
			}
		}			
		int newPartnersOther = getIntValue(caseWorkerVO.getNewPartnersOtherCount());
		if (newPartnersNoExam != 0 && newPartnersOther != 0) {
			try {
				float percentageNewPartnersOther = (float) (newPartnersOther / newPartnersNoExam) * 100;
				caseWorkerVO.setNewPartnersOtherPercent(numberFormat.format(percentageNewPartnersOther) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersOther?");		
			}
		}
		int newPartnersPreviousRx = getIntValue(caseWorkerVO.getNewPartnersPreviousRxCount());
		if (totalPartnersInitiated != 0 && newPartnersOther != 0) {
			try {
				float percentagePreviousRx = (float) (newPartnersPreviousRx / totalPartnersInitiated) * 100;
				caseWorkerVO.setNewPartnersPreviousRxPercent(numberFormat.format(percentagePreviousRx) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersPrevRx?");		
			}
		}
		int newPartnersStdOpen = getIntValue(caseWorkerVO.getNewPartnersStdOpenCount());
		if (totalPartnersInitiated != 0 && newPartnersStdOpen != 0) {
			try {
				float percentageOpen = (float) (newPartnersStdOpen / totalPartnersInitiated) * 100;
				caseWorkerVO.setNewPartnersStdOpenPercent(numberFormat.format(percentageOpen) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersOpen?");		
			}
		}
		
	}	
	/**
	 * Get Percentages for the STD Dispositions right side section.
	 * @param caseWorkerVO
	 */
	private void calculatePercentagesForStdClusterDispositions(Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int newClustersExamined = getIntValue(caseWorkerVO.getNewClustersExaminedCount());
		int totalClustersInitiated = getIntValue(caseWorkerVO.getTotalClusterInitiatedCount());
		if (totalClustersInitiated != 0 && newClustersExamined != 0) {
			try {
				float percentageNewClustersExamined = (float) (newClustersExamined / totalClustersInitiated) * 100;
				caseWorkerVO.setNewClustersExaminedPercent(numberFormat.format(percentageNewClustersExamined) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClusterExamined?");		
			}
		}
		
		int newClustersPreventativeRx = getIntValue(caseWorkerVO.getNewClustersPreventativeRxCount());
		if (newClustersExamined != 0 && newClustersPreventativeRx != 0) {
			try {
				float percentageNewClustersPreventativeRx = (float) (newClustersPreventativeRx / newClustersExamined) * 100;
				caseWorkerVO.setNewPartnersPreventativeRxPercent(numberFormat.format(percentageNewClustersPreventativeRx) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersPreventativeRx?");		
			}
		}		
		
		int newClustersRefusedPrevRx = getIntValue(caseWorkerVO.getNewClustersRefusedPrevRxCount());
		if (newClustersExamined != 0 && newClustersRefusedPrevRx != 0) {
			try {
				float percentageNewClustersRefusedPrevRx = (float) (newClustersRefusedPrevRx / newClustersExamined) * 100;
				caseWorkerVO.setNewClustersRefusedPrevRxPercent(numberFormat.format(percentageNewClustersRefusedPrevRx) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersRefusedPrevRx?");		
			}
		}
		int newClustersInfectedRx = getIntValue(caseWorkerVO.getNewClustersInfectedRxCount());
		if (newClustersExamined != 0 && newClustersInfectedRx != 0) {
			try {
				float percentageNewClustersInfectedRx = (float) (newClustersInfectedRx / newClustersExamined) * 100;
				caseWorkerVO.setNewClustersInfectedRxPercent(numberFormat.format(percentageNewClustersInfectedRx) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersInfectedRx?");		
			}
		}
		int newClustersInfectedNoRx = getIntValue(caseWorkerVO.getNewClustersInfectedNoRxCount());
		if (newClustersExamined != 0 && newClustersInfectedNoRx != 0) {
			try {
				float percentageNewClustersInfectedNoRx = (float) (newClustersInfectedNoRx / newClustersExamined) * 100;
				caseWorkerVO.setNewClustersInfectedNoRxPercent(numberFormat.format(percentageNewClustersInfectedNoRx) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersInfectedNoRx?");		
			}
		}		
		int newClustersNotInfected = getIntValue(caseWorkerVO.getNewClustersNotInfectedCount());
		if (newClustersExamined != 0 && newClustersNotInfected != 0) {
			try {
				float percentageNewClustersNotInfected = (float) (newClustersNotInfected / newClustersExamined) * 100;
				caseWorkerVO.setNewClustersNotInfectedPercent(numberFormat.format(percentageNewClustersNotInfected) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersNotInfected?");		
			}
		}				
		int newClustersNoExam= getIntValue(caseWorkerVO.getNewClustersNoExamCount());
		if (totalClustersInitiated != 0 && newClustersNoExam != 0) {
			try {
				float percentageNewClustersNoExam = (float) (newClustersNoExam / totalClustersInitiated) * 100;
				caseWorkerVO.setNewClustersNoExamPercent(numberFormat.format(percentageNewClustersNoExam) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersNoExam?");		
			}
		}
		int newClustersInsufficientInfo = getIntValue(caseWorkerVO.getNewClustersInsufficientInfoCount());
		if (newClustersNoExam != 0 && newClustersInsufficientInfo != 0) {
			try {
				float percentageNewClustersInsufficientInfo = (float) (newClustersInsufficientInfo / newClustersNoExam) * 100;
				caseWorkerVO.setNewClustersInsufficientInfoPercent(numberFormat.format(percentageNewClustersInsufficientInfo) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersInsufficientInfo?");		
			}
		}
		int newClustersUnableToLocate = getIntValue(caseWorkerVO.getNewClustersUnableToLocateCount());
		if (newClustersNoExam != 0 && newClustersUnableToLocate != 0) {
			try {
				float percentageNewClustersUnableToLocate = (float) (newClustersUnableToLocate / newClustersNoExam) * 100;
				caseWorkerVO.setNewClustersUnableToLocatePercent(numberFormat.format(percentageNewClustersUnableToLocate) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersUnableToLocate?");		
			}
		}		
		int newClustersRefusedExam = getIntValue(caseWorkerVO.getNewClustersRefusedExamCount());
		if (newClustersNoExam != 0 && newClustersRefusedExam != 0) {
			try {
				float percentageNewClustersRefusedExam = (float) (newClustersRefusedExam / newClustersNoExam) * 100;
				caseWorkerVO.setNewClustersRefusedExamPercent(numberFormat.format(percentageNewClustersRefusedExam) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersRefusedExam?");		
			}
		}	
		int newClustersOOJ = getIntValue(caseWorkerVO.getNewClustersOOJCount());
		if (newClustersNoExam != 0 && newClustersOOJ != 0) {
			try {
				float percentageNewClustersOOJ = (float) (newClustersOOJ / newClustersNoExam) * 100;
				caseWorkerVO.setNewClustersOOJPercent(numberFormat.format(percentageNewClustersOOJ) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersOOJ?");		
			}
		}			
		int newClustersOther = getIntValue(caseWorkerVO.getNewClustersOtherCount());
		if (newClustersNoExam != 0 && newClustersOther != 0) {
			try {
				float percentageNewClustersOther = (float) (newClustersOther / newClustersNoExam) * 100;
				caseWorkerVO.setNewClustersOtherPercent(numberFormat.format(percentageNewClustersOther) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersOther?");		
			}
		}
		int newClustersPreviousRx = getIntValue(caseWorkerVO.getNewClustersPreviousRxCount());
		if (totalClustersInitiated != 0 && newClustersOther != 0) {
			try {
				float percentagePreviousRx = (float) (newClustersPreviousRx / totalClustersInitiated) * 100;
				caseWorkerVO.setNewClustersPreviousRxPercent(numberFormat.format(percentagePreviousRx) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersPrevRx?");		
			}
		}
		int newClustersStdOpen = getIntValue(caseWorkerVO.getNewClustersStdOpenCount());
		if (totalClustersInitiated != 0 && newClustersStdOpen != 0) {
			try {
				float percentageOpen = (float) (newClustersStdOpen / totalClustersInitiated) * 100;
				caseWorkerVO.setNewClustersStdOpenPercent(numberFormat.format(percentageOpen) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersOpen?");		
			}
		}
		
	}
	/**
	 * Get StD percentages for the last section.
	 * @param caseWorkerVO
	 */
	private void calculatePercentagesForNewPartnersStdSpeedOfExam(Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int newPartnersExamined = getIntValue(caseWorkerVO.getSeNewPartnersExaminedCount());
		if (newPartnersExamined == 0)
			return; //nothing to do..
		int casesWithIn3Days = getIntValue(caseWorkerVO.getSeNewPartnersExaminedWI3dCount());
		if (casesWithIn3Days != 0) {
			try {
				float percentageCasesWithIn3Days = (float) (casesWithIn3Days / newPartnersExamined) * 100;
				caseWorkerVO.setSeNewPartnersExaminedWI3dPercent(numberFormat.format(percentageCasesWithIn3Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageSeNewPartnerCasesWithIn3Days?");		
			}
		}
		int casesWithIn5Days = getIntValue(caseWorkerVO.getSeNewPartnersExaminedWI5dCount());
		if (casesWithIn5Days != 0) {
			try {
				float percentageCasesWithIn5Days = (float) (casesWithIn5Days / newPartnersExamined) * 100;
				caseWorkerVO.setSeNewPartnersExaminedWI5dPercent(numberFormat.format(percentageCasesWithIn5Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageSeNewPartnerCasesWithIn5Days?");		
			}
		}
		int casesWithIn7Days = getIntValue(caseWorkerVO.getSeNewPartnersExaminedWI7dCount());
		if (casesWithIn7Days != 0) {
			try {
				float percentageCasesWithIn7Days = (float) (casesWithIn7Days / newPartnersExamined) * 100;
				caseWorkerVO.setSeNewPartnersExaminedWI7dPercent(numberFormat.format(percentageCasesWithIn7Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageSeNewPartnerCasesWithIn7Days?");		
			}
		}
		int casesWithIn14Days = getIntValue(caseWorkerVO.getSeNewPartnersExaminedWI14dCount());
		if (casesWithIn14Days != 0) {
			try {
				float percentageCasesWithIn14Days = (float) (casesWithIn14Days / newPartnersExamined) * 100;
				caseWorkerVO.setSeNewPartnersExaminedWI14dPercent(numberFormat.format(percentageCasesWithIn14Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageSeNewPartnerCasesWithIn14Days?");	
			}
		}
		return;
	}
	/**
	 * Get STD percentages for the right side of the last section.
	 * @param caseWorkerVO
	 */
	private void calculatePercentagesForNewClustersStdSpeedOfExam(Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int newClustersExamined = getIntValue(caseWorkerVO.getSeNewClustersExaminedCount());
		if (newClustersExamined == 0)
			return; //nothing to do..
		int casesWithIn3Days = getIntValue(caseWorkerVO.getSeNewClustersExaminedWI3dCount());
		if (casesWithIn3Days != 0) {
			try {
				float percentageCasesWithIn3Days = (float) (casesWithIn3Days / newClustersExamined) * 100;
				caseWorkerVO.setSeNewClustersExaminedWI3dPercent(numberFormat.format(percentageCasesWithIn3Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for PercentageNewClusterCasesWithIn3Days?");		
			}
		}
		int casesWithIn5Days = getIntValue(caseWorkerVO.getSeNewClustersExaminedWI5dCount());
		if (casesWithIn5Days != 0) {
			try {
				float percentageCasesWithIn5Days = (float) (casesWithIn5Days / newClustersExamined) * 100;
				caseWorkerVO.setSeNewClustersExaminedWI5dPercent(numberFormat.format(percentageCasesWithIn5Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for PercentageNewClustereCasesWithIn5Days?");		
			}
		}
		int casesWithIn7Days = getIntValue(caseWorkerVO.getSeNewClustersExaminedWI7dCount());
		if (casesWithIn7Days != 0) {
			try {
				float percentageCasesWithIn7Days = (float) (casesWithIn7Days / newClustersExamined) * 100;
				caseWorkerVO.setSeNewClustersExaminedWI7dPercent(numberFormat.format(percentageCasesWithIn7Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for PercentageNewClusterCasesWithIn7Days?");		
			}
		}
		int casesWithIn14Days = getIntValue(caseWorkerVO.getSeNewClustersExaminedWI14dCount());
		if (casesWithIn14Days != 0) {
			try {
				float percentageCasesWithIn14Days = (float) (casesWithIn14Days / newClustersExamined) * 100;
				caseWorkerVO.setSeNewClustersExaminedWI14dPercent(numberFormat.format(percentageCasesWithIn14Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for PercentageNewClusterCasesWithIn14Days?");	
			}
		}
		return;		
		
	}
	/**
	 * Get HIV percentages for the last section left side.
	 * @param caseWorkerVO
	 */
	private void calculatePercentagesForHivPartnerDispositions(Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int newPartnersNotified = getIntValue(caseWorkerVO.getNewPartnersNotifiedCount());

		
		int prevNegNewPos = getIntValue(caseWorkerVO.getNewPartnersPrevNegNewPosCount());
		if (prevNegNewPos != 0 && newPartnersNotified != 0) {
			try {
				float percentagePrevNegNewPos = (float) (prevNegNewPos / newPartnersNotified) * 100;
				caseWorkerVO.setNewPartnersPrevNegNewPosPercent(numberFormat.format(percentagePrevNegNewPos) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersPrevNegNewPos?");		
			}
		}
		
		int prevNegStillNeg = getIntValue(caseWorkerVO.getNewPartnersPrevNegStillNegCount());
		if (prevNegStillNeg != 0 && newPartnersNotified != 0) {
			try {
				float percentagePrevNegStillNeg = (float) (prevNegStillNeg / newPartnersNotified) * 100;
				caseWorkerVO.setNewPartnersPrevNegStillNegPercent(numberFormat.format(percentagePrevNegStillNeg) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersPrevNegStillNeg?");		
			}
		}	
		int prevNegNoTest = getIntValue(caseWorkerVO.getNewPartnersPrevNegNoTestCount());
		if (prevNegNoTest != 0 && newPartnersNotified != 0) {
			try {
				float percentagePrevNegNoTest = (float) (prevNegNoTest / newPartnersNotified) * 100;
				caseWorkerVO.setNewPartnersPrevNegNoTestPercent(numberFormat.format(percentagePrevNegNoTest) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersPrevNegNoTest?");		
			}
		}
		int noPrevNewPos = getIntValue(caseWorkerVO.getNewPartnersNoPrevNewPosCount());
		if (noPrevNewPos != 0 && newPartnersNotified != 0) {
			try {
				float percentageNoPrevNewPos = (float) (noPrevNewPos / newPartnersNotified) * 100;
				caseWorkerVO.setNewPartnersNoPrevNewPosPercent(numberFormat.format(percentageNoPrevNewPos) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersNoPrevNewPos?");		
			}
		}		
		int noPrevNewNeg = getIntValue(caseWorkerVO.getNewPartnersNoPrevNewNegCount());
		if (noPrevNewNeg != 0 && newPartnersNotified != 0) {
			try {
				float percentageNoPrevNewNeg = (float) (noPrevNewNeg / newPartnersNotified) * 100;
				caseWorkerVO.setNewPartnersNoPrevNewNegPercent(numberFormat.format(percentageNoPrevNewNeg) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersNoPrevNewNeg?");		
			}
		}
		int noPrevNoTest = getIntValue(caseWorkerVO.getNewPartnersNoPrevNoTestCount());
		if (noPrevNoTest != 0 && newPartnersNotified != 0) {
			try {
				float percentageNoPrevNoTest = (float) (noPrevNoTest / newPartnersNotified) * 100;
				caseWorkerVO.setNewPartnersNoPrevNewPosPercent(numberFormat.format(percentageNoPrevNoTest) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersNoPrevNoTest?");		
			}
		}
		//Not Notified
		int newPartnersNotNotifiedCount = getIntValue(caseWorkerVO.getNewPartnersNotNotifiedCount());	
		int newPartnersNotNotifiedInsufficientInfoCount	 = getIntValue(caseWorkerVO.getNewPartnersNotNotifiedInsufficientInfoCount());
		if (newPartnersNotNotifiedInsufficientInfoCount != 0 && newPartnersNotNotifiedCount != 0) {
			try {
				float percentageNotNotifiedInsufficientInfo = (float) (newPartnersNotNotifiedInsufficientInfoCount / newPartnersNotNotifiedCount) * 100;
				caseWorkerVO.setNewPartnersNotNotifiedInsufficientInfoPercent(numberFormat.format(percentageNotNotifiedInsufficientInfo) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNotNotifiedInsufficientInfo?");		
			}
		}		
		int newPartnersNotNotifiedUnableToLocateCount	 = getIntValue(caseWorkerVO.getNewPartnersNotNotifiedUnableToLocateCount());
		if (newPartnersNotNotifiedUnableToLocateCount != 0 && newPartnersNotNotifiedCount != 0) {
			try {
				float percentageNotNotifiedUnableToLocate = (float) (newPartnersNotNotifiedUnableToLocateCount / newPartnersNotNotifiedCount) * 100;
				caseWorkerVO.setNewPartnersNotNotifiedUnableToLocatePercent(numberFormat.format(percentageNotNotifiedUnableToLocate) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNotNotifiedUnableToLocate?");		
			}
		}
		int newPartnersNotNotifiedRefusedExamCount	 = getIntValue(caseWorkerVO.getNewPartnersNotNotifiedUnableToLocateCount());
		if (newPartnersNotNotifiedRefusedExamCount != 0 && newPartnersNotNotifiedCount != 0) {
			try {
				float percentageNotNotifiedRefusedExam = (float) (newPartnersNotNotifiedRefusedExamCount / newPartnersNotNotifiedCount) * 100;
				caseWorkerVO.setNewPartnersNotNotifiedRefusedExamPercent(numberFormat.format(percentageNotNotifiedRefusedExam) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNotNotifiedRefusedExam?");		
			}
		}
		int newPartnersNotNotifiedOOJCount	 = getIntValue(caseWorkerVO.getNewPartnersNotNotifiedOOJCount());
		if (newPartnersNotNotifiedOOJCount != 0 && newPartnersNotNotifiedCount != 0) {
			try {
				float percentageNotNotifiedOOJ = (float) (newPartnersNotNotifiedOOJCount / newPartnersNotNotifiedCount) * 100;
				caseWorkerVO.setNewPartnersNotNotifiedOOJPercent(numberFormat.format(percentageNotNotifiedOOJ) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNotNotifiedOOJ?");		
			}
		}
		int newPartnersNotNotifiedOtherCount	 = getIntValue(caseWorkerVO.getNewPartnersNotNotifiedOtherCount());
		if (newPartnersNotNotifiedOtherCount != 0 && newPartnersNotNotifiedCount != 0) {
			try {
				float percentageNotNotifiedOther = (float) (newPartnersNotNotifiedOtherCount / newPartnersNotNotifiedCount) * 100;
				caseWorkerVO.setNewPartnersNotNotifiedOtherPercent(numberFormat.format(percentageNotNotifiedOther) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNotNotifiedOther?");		
			}
		}
		//believe denominator is new partners notified...
		int prevPos = getIntValue(caseWorkerVO.getNewPartnersPrevPosCount());
		if (prevPos != 0 && newPartnersNotified != 0) {
			try {
				float percentagePrevPos = (float) (prevPos / newPartnersNotified) * 100;
				caseWorkerVO.setNewPartnersPrevPosPercent(numberFormat.format(percentagePrevPos) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersPrevPos?");		
			}
		}
		//believe denominator is new partners notified...
		int hivOpen = getIntValue(caseWorkerVO.getNewPartnersHivOpenCount());
		if (hivOpen != 0 && newPartnersNotified != 0) {
			try {
				float percentageHivOpen = (float) (hivOpen / newPartnersNotified) * 100;
				caseWorkerVO.setNewPartnersHivOpenPercent(numberFormat.format(percentageHivOpen) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewPartnersHivOpen?");		
			}
		}
		
		return;
	}	

	/**
	 * Get HIV percentages for the last section right side.
	 * @param caseWorkerVO
	 */
	private void calculatePercentagesForHivClustersDispositions(Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int newClustersNotified = getIntValue(caseWorkerVO.getNewClustersNotifiedCount());

		
		int prevNegNewPos = getIntValue(caseWorkerVO.getNewClustersPrevNegNewPosCount());
		if (prevNegNewPos != 0 && newClustersNotified != 0) {
			try {
				float percentagePrevNegNewPos = (float) (prevNegNewPos / newClustersNotified) * 100;
				caseWorkerVO.setNewClustersPrevNegNewPosPercent(numberFormat.format(percentagePrevNegNewPos) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersPrevNegNewPos?");		
			}
		}
		
		int prevNegStillNeg = getIntValue(caseWorkerVO.getNewClustersPrevNegStillNegCount());
		if (prevNegStillNeg != 0 && newClustersNotified != 0) {
			try {
				float percentagePrevNegStillNeg = (float) (prevNegStillNeg / newClustersNotified) * 100;
				caseWorkerVO.setNewClustersPrevNegStillNegPercent(numberFormat.format(percentagePrevNegStillNeg) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersPrevNegStillNeg?");		
			}
		}	
		int prevNegNoTest = getIntValue(caseWorkerVO.getNewClustersPrevNegNoTestCount());
		if (prevNegNoTest != 0 && newClustersNotified != 0) {
			try {
				float percentagePrevNegNoTest = (float) (prevNegNoTest / newClustersNotified) * 100;
				caseWorkerVO.setNewClustersPrevNegNoTestPercent(numberFormat.format(percentagePrevNegNoTest) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersPrevNegNoTest?");		
			}
		}
		int noPrevNewPos = getIntValue(caseWorkerVO.getNewClustersNoPrevNewPosCount());
		if (noPrevNewPos != 0 && newClustersNotified != 0) {
			try {
				float percentageNoPrevNewPos = (float) (noPrevNewPos / newClustersNotified) * 100;
				caseWorkerVO.setNewClustersNoPrevNewPosPercent(numberFormat.format(percentageNoPrevNewPos) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersNoPrevNewPos?");		
			}
		}		
		int noPrevNewNeg = getIntValue(caseWorkerVO.getNewClustersNoPrevNewNegCount());
		if (noPrevNewNeg != 0 && newClustersNotified != 0) {
			try {
				float percentageNoPrevNewNeg = (float) (noPrevNewNeg / newClustersNotified) * 100;
				caseWorkerVO.setNewClustersNoPrevNewNegPercent(numberFormat.format(percentageNoPrevNewNeg) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersNoPrevNewNeg?");		
			}
		}
		int noPrevNoTest = getIntValue(caseWorkerVO.getNewClustersNoPrevNoTestCount());
		if (noPrevNoTest != 0 && newClustersNotified != 0) {
			try {
				float percentageNoPrevNoTest = (float) (noPrevNoTest / newClustersNotified) * 100;
				caseWorkerVO.setNewClustersNoPrevNewPosPercent(numberFormat.format(percentageNoPrevNoTest) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersNoPrevNoTest?");		
			}
		}
		//Not Notified
		int newClustersNotNotifiedCount = getIntValue(caseWorkerVO.getNewClustersNotNotifiedCount());	
		int newClustersNotNotifiedInsufficientInfoCount	 = getIntValue(caseWorkerVO.getNewClustersNotNotifiedInsufficientInfoCount());
		if (newClustersNotNotifiedInsufficientInfoCount != 0 && newClustersNotNotifiedCount != 0) {
			try {
				float percentageNotNotifiedInsufficientInfo = (float) (newClustersNotNotifiedInsufficientInfoCount / newClustersNotNotifiedCount) * 100;
				caseWorkerVO.setNewClustersNotNotifiedInsufficientInfoPercent(numberFormat.format(percentageNotNotifiedInsufficientInfo) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersNotNotifiedInsufficientInfo?");		
			}
		}		
		int newClustersNotNotifiedUnableToLocateCount	 = getIntValue(caseWorkerVO.getNewClustersNotNotifiedUnableToLocateCount());
		if (newClustersNotNotifiedUnableToLocateCount != 0 && newClustersNotNotifiedCount != 0) {
			try {
				float percentageNotNotifiedUnableToLocate = (float) (newClustersNotNotifiedUnableToLocateCount / newClustersNotNotifiedCount) * 100;
				caseWorkerVO.setNewClustersNotNotifiedUnableToLocatePercent(numberFormat.format(percentageNotNotifiedUnableToLocate) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersNotNotifiedUnableToLocate?");		
			}
		}
		int newClustersNotNotifiedRefusedExamCount	 = getIntValue(caseWorkerVO.getNewClustersNotNotifiedUnableToLocateCount());
		if (newClustersNotNotifiedRefusedExamCount != 0 && newClustersNotNotifiedCount != 0) {
			try {
				float percentageNotNotifiedRefusedExam = (float) (newClustersNotNotifiedRefusedExamCount / newClustersNotNotifiedCount) * 100;
				caseWorkerVO.setNewClustersNotNotifiedRefusedExamPercent(numberFormat.format(percentageNotNotifiedRefusedExam) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersNotNotifiedRefusedExam?");		
			}
		}
		int newClustersNotNotifiedOOJCount	 = getIntValue(caseWorkerVO.getNewClustersNotNotifiedOOJCount());
		if (newClustersNotNotifiedOOJCount != 0 && newClustersNotNotifiedCount != 0) {
			try {
				float percentageNotNotifiedOOJ = (float) (newClustersNotNotifiedOOJCount / newClustersNotNotifiedCount) * 100;
				caseWorkerVO.setNewClustersNotNotifiedOOJPercent(numberFormat.format(percentageNotNotifiedOOJ) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersNotNotifiedOOJ?");		
			}
		}
		int newClustersNotNotifiedOtherCount	 = getIntValue(caseWorkerVO.getNewClustersNotNotifiedOtherCount());
		if (newClustersNotNotifiedOtherCount != 0 && newClustersNotNotifiedCount != 0) {
			try {
				float percentageNotNotifiedOther = (float) (newClustersNotNotifiedOtherCount / newClustersNotNotifiedCount) * 100;
				caseWorkerVO.setNewClustersNotNotifiedOtherPercent(numberFormat.format(percentageNotNotifiedOther) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNotNotifiedOther?");		
			}
		}
		//believe denominator is new clusters notified...
		int prevPos = getIntValue(caseWorkerVO.getNewClustersHivPrevPosCount());
		if (prevPos != 0 && newClustersNotified != 0) {
			try {
				float percentagePrevPos = (float) (prevPos / newClustersNotified) * 100;
				caseWorkerVO.setNewClustersHivPrevPosPercent(numberFormat.format(percentagePrevPos) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersPrevPos?");		
			}
		}
		//believe denominator is new clusters notified...
		int hivOpen = getIntValue(caseWorkerVO.getNewClustersHivOpenCount());
		if (hivOpen != 0 && newClustersNotified != 0) {
			try {
				float percentageHivOpen = (float) (hivOpen / newClustersNotified) * 100;
				caseWorkerVO.setNewClustersHivOpenPercent(numberFormat.format(percentageHivOpen) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageNewClustersHivOpen?");		
			}
		}
		
		return;
	}	
	/**
	 * Get Percentages for the HIV Speed of Exam side left side section.
	 * @param caseWorkerVO
	 */
	private void calculatePercentagesForNewPartnersHivSpeedOfNotification(Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int newPartnersNotified = getIntValue(caseWorkerVO.getSeNewPartnersNotifiedCount());
		if (newPartnersNotified == 0)
			return; //nothing to do..
		int casesWithIn3Days = getIntValue(caseWorkerVO.getSeNewPartnersNotifiedWI3dCount());
		if (casesWithIn3Days != 0) {
			try {
				float percentageCasesWithIn3Days = (float) (casesWithIn3Days / newPartnersNotified) * 100;
				caseWorkerVO.setSeNewPartnersNotifiedWI3dPercent(numberFormat.format(percentageCasesWithIn3Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageSeNewPartnerNotifiedCasesWithIn3Days?");		
			}
		}
		int casesWithIn5Days = getIntValue(caseWorkerVO.getSeNewPartnersNotifiedWI5dCount());
		if (casesWithIn5Days != 0) {
			try {
				float percentageCasesWithIn5Days = (float) (casesWithIn5Days / newPartnersNotified) * 100;
				caseWorkerVO.setSeNewPartnersNotifiedWI5dPercent(numberFormat.format(percentageCasesWithIn5Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageSeNewPartnerNotifiedCasesWithIn5Days?");		
			}
		}
		int casesWithIn7Days = getIntValue(caseWorkerVO.getSeNewPartnersNotifiedWI7dCount());
		if (casesWithIn7Days != 0) {
			try {
				float percentageCasesWithIn7Days = (float) (casesWithIn7Days / newPartnersNotified) * 100;
				caseWorkerVO.setSeNewPartnersNotifiedWI7dPercent(numberFormat.format(percentageCasesWithIn7Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageSeNewPartnerNotifiedCasesWithIn7Days?");		
			}
		}
		int casesWithIn14Days = getIntValue(caseWorkerVO.getSeNewPartnersNotifiedWI14dCount());
		if (casesWithIn14Days != 0) {
			try {
				float percentageCasesWithIn14Days = (float) (casesWithIn14Days / newPartnersNotified) * 100;
				caseWorkerVO.setSeNewPartnersNotifiedWI14dPercent(numberFormat.format(percentageCasesWithIn14Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for percentageSeNewPartnerNotifiedCasesWithIn14Days?");	
			}
		}
		return;
	}
	/**
	 * Get Percentages for the HIV Speed of Exam right side cluster section.
	 * @param caseWorkerVO
	 */
	private void calculatePercentagesForNewClustersHivSpeedOfNotification(Pa1VO caseWorkerVO) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(1);
		
		int newClustersNotified = getIntValue(caseWorkerVO.getSeNewClustersNotifiedCount());
		if (newClustersNotified  == 0)
			return; //nothing to do..
		int casesWithIn3Days = getIntValue(caseWorkerVO.getSeNewClustersNotifiedWI3dCount());
		if (casesWithIn3Days != 0) {
			try {
				float percentageCasesWithIn3Days = (float) (casesWithIn3Days / newClustersNotified) * 100;
				caseWorkerVO.setSeNewClustersNotifiedWI3dPercent(numberFormat.format(percentageCasesWithIn3Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for PercentageNewClusterNotifiedCasesWithIn3Days?");		
			}
		}
		int casesWithIn5Days = getIntValue(caseWorkerVO.getSeNewClustersNotifiedWI5dCount());
		if (casesWithIn5Days != 0) {
			try {
				float percentageCasesWithIn5Days = (float) (casesWithIn5Days / newClustersNotified) * 100;
				caseWorkerVO.setSeNewClustersNotifiedWI5dPercent(numberFormat.format(percentageCasesWithIn5Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for PercentageNewClustersNotifiedCasesWithIn5Days?");		
			}
		}
		int casesWithIn7Days = getIntValue(caseWorkerVO.getSeNewClustersNotifiedWI7dCount());
		if (casesWithIn7Days != 0) {
			try {
				float percentageCasesWithIn7Days = (float) (casesWithIn7Days / newClustersNotified) * 100;
				caseWorkerVO.setSeNewClustersNotifiedWI7dPercent(numberFormat.format(percentageCasesWithIn7Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for PercentageNewClusterNotifiedCasesWithIn7Days?");		
			}
		}
		int casesWithIn14Days = getIntValue(caseWorkerVO.getSeNewClustersNotifiedWI14dCount());
		if (casesWithIn14Days != 0) {
			try {
				float percentageCasesWithIn14Days = (float) (casesWithIn14Days / newClustersNotified) * 100;
				caseWorkerVO.setSeNewClustersNotifiedWI14dPercent(numberFormat.format(percentageCasesWithIn14Days) + "%");
			} catch (NumberFormatException nfe) {
				logger.warn("Number divide or format error for PercentageNewClusterNotifiedCasesWithIn14Days?");	
			}
		}
		return;		
		
	}

	
}
