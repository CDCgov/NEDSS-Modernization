package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper;

import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.phdc.ContainerDocument;
import gov.cdc.nedss.phdc.cda.ClinicalDocumentDocument1;
import gov.cdc.nedss.phdc.cda.POCDMT000040Entry;
import gov.cdc.nedss.phdc.cda.POCDMT000040Section;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.util.DSMAlgorithmCache;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDAContactRecordProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDAInterviewProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDALabReportProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDAMorbReportProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDAPHCProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.EdxCDAConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxCDAInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRDocumentUtil;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxRuleConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.ValidateDecisionSupport;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.DSMUpdateAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.UpdateCaseSummaryVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.xmlbeans.XmlObject;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class EdxPHCRHelper {
	static final LogUtils logger = new LogUtils(EdxPHCRHelper.class.getName());
	static   PropertyUtil properties = PropertyUtil.getInstance();
	 
	public EdxRuleAlgorothmManagerDT createDocumentInvestigation
	(	String datamigration,
		NbsInterfaceDT nbsInterfaceDT, 
		NBSSecurityObj nbsSecurityObj,
		SessionContext sessionCtx
	) throws EJBException, RemoteException, NEDSSAppException {
		NedssUtils nedssUtils = new NedssUtils();
		/*
		 * create a EdxRuleAlgorothmManagerDT
		 */
		EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT = new EdxRuleAlgorothmManagerDT();
		edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
		EDXActivityLogDT edxActivityLogDT = edxRuleAlgorithmManagerDT.getEdxActivityLogDT();
		edxActivityLogDT.setException("");
		edxRuleAlgorithmManagerDT.setErrorText("");
		// TESTING
		boolean generalTestingException = false;
		boolean investigationTestingException = false;
		boolean notificationTestingException = false;
		boolean documentUpdated = false;
		boolean skipDRRQDocumentUpdated = false;
		String xStatus;
		UserTransaction userTrans = sessionCtx.getUserTransaction();
		
		PropertyUtil propertyUtil = new PropertyUtil();
		
		
		
		try {
			if (generalTestingException) {
				throw new Exception("Testing");
			}
			boolean authorized = nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT, NBSOperationLookup.VIEW);
			if (authorized) {
				authorized = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.DECISION_SUPPORT_ADMIN);
			}
			if (!authorized) {
				String uid = "";
				try {
					uid = nbsSecurityObj.getTheUserProfile().getTheUser().getUserID();
				} catch (Exception ignore) {
				}
				edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
				edxRuleAlgorithmManagerDT.setErrorText("User " + uid + " is not authorized to autocreate Document");

			} else {
				/*
				 * create transaction
				 */
				String ofci;
				String ofcn;

				/*
				 * create a NbsDocument EJB
				 */
				userTrans.begin();
				
				// TESTING
				xStatus = getTransactionStatus(userTrans.getStatus());
				System.out.println(xStatus);
				
				Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
				NbsDocumentHome docHome = (NbsDocumentHome) PortableRemoteObject.narrow(theLookedUpObject, NbsDocumentHome.class);
				NbsDocument nbsDocument = docHome.create();

				/*
				 * create a NBSDocumentVO from NbsInterfaceDT
				 */
				
				NBSDocumentVO nbsDocumentVO = nbsDocument.createNBSDocument(nbsInterfaceDT, nbsSecurityObj);
				
				documentUpdated = nbsDocumentVO.isDocumentUpdate();
				
				
				if(documentUpdated &&(propertyUtil.getPHDCSkipDRRQ() != null && (propertyUtil.getPHDCSkipDRRQ().equals(NEDSSConstants.YES))))
					skipDRRQDocumentUpdated = true;//It will be used to skip the WDS
						
				edxRuleAlgorithmManagerDT.setContactRecordDoc(nbsDocumentVO.isContactRecordDoc());
				edxRuleAlgorithmManagerDT.setLabReportDoc(nbsDocumentVO.isLabReportDoc());
				edxRuleAlgorithmManagerDT.setMorbReportDoc(nbsDocumentVO.isMorbReportDoc());
				edxRuleAlgorithmManagerDT.seteDXEventProcessCaseSummaryDTMap(nbsDocumentVO.geteDXEventProcessCaseSummaryDTMap());
				edxRuleAlgorithmManagerDT.setMPRUid(nbsDocumentVO.getPatientVO().getThePersonDT().getPersonParentUid());
				edxRuleAlgorithmManagerDT.setDocumentDT(nbsDocumentVO.getNbsDocumentDT());
				if(nbsDocumentVO.getOriginalPHCRLocalId()!=null && nbsDocumentVO.getOriginalPHCRLocalId().trim().length()>0) {
					edxRuleAlgorithmManagerDT.setUpdatedDocument(true);
					
					/* TODO: Handle document update scenario*/
					
				}
				// TESTING
				xStatus = getTransactionStatus(userTrans.getStatus());
				System.out.println(xStatus);
				
				edxActivityLogDT.setBusinessObjLocalId(nbsDocumentVO.getNbsDocumentDT().getLocalId());
				edxActivityLogDT.setTargetUid(nbsDocumentVO.getNbsDocumentDT().getNbsDocumentUid());
				edxActivityLogDT.setDocType(nbsDocumentVO.getNbsDocumentDT().getDocTypeCd());
				edxActivityLogDT.setSrcName(nbsDocumentVO.getNbsDocumentDT().getSendingFacilityNm());
				edxActivityLogDT.setDocName(nbsDocumentVO.getDocName());

				/*
				 * create an EntityController and use it to retrieve a PersonVO
				 * (patient)
				 */
				boolean patientIsOld = nbsDocumentVO.getIsExistingPatient();
				boolean multiplePatients = nbsDocumentVO.getIsMultiplePatFound();
				long patientUid = nbsDocumentVO.getPatientVO().getThePersonDT().getPersonParentUid();

				boolean error = false;

				/*
				 * create a actUid for later use
				 */
				Long actUid = null;

				/*
				 * determine if there is a jurisdiction
				 */
				String noJursDictionError = null;
				if (nbsDocumentVO.getNbsDocumentDT().getJurisdictionCd() == null) {
					noJursDictionError = "Patient Information was not sufficient to derive Jurisdiction. " + "Review Documents Requiring Security Assignment queue.";
					logger.debug(noJursDictionError);

					edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
					edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_JURISDICTION_FAIL);
					addJurisdictionFailDetailMsg(edxRuleAlgorithmManagerDT);
				
					userTrans.commit();
					//documentUpdated = nbsDocumentVO.isDocumentUpdate();
					if(!documentUpdated)
						addDocumentSuccessDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
					
					
					addDocumentMatchDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
					if(!documentUpdated){
						if (multiplePatients) {
							addMultiplePatientDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
						} else {
							if (!patientIsOld) {
								addPatientNewDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
							} else {
								addPatientOldDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
							}
						}
					}
				} else {
					if(nbsDocumentVO.isAssociatedInv()){
						
						//If the jurisdiction wasn't gotten from the previous document!!
						if(!nbsDocumentVO.isJurisdictionDerivedFromPreviousDoc())
							addJurisdictionSuccessDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO);
						
					
						if(!documentUpdated){
							addPatientOldDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
							addDocumentSuccessDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
						}
						addDocumentMatchDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
						if(!skipDRRQDocumentUpdated)//Don't show investigation found message
							addExistingInvMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
						edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
						
						if((nbsDocumentVO.getAssoSummaryCaseList()==null || nbsDocumentVO.getAssoSummaryCaseList().size()==0) && (nbsDocumentVO.getSummaryCaseListWithInTimeFrame()==null || nbsDocumentVO.getSummaryCaseListWithInTimeFrame().size()==0)) {
							
							if(!skipDRRQDocumentUpdated)//Dont show message logging in the DRRQ
								edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_INVESTIGATION_CREATE_FAIL_1+nbsDocumentVO.getConditionName()+EdxPHCRConstants.SUM_MSG_INVESTIGATION_CREATE_FAIL_2);
							userTrans.commit();
						}
						else {
							
							//update Scenario
							UpdateCaseSummaryVO summaryVO = identifyCaseToBeUpdated(nbsDocumentVO);
							
							if(skipDRRQDocumentUpdated)//if Update and SkipDRRQ, we don't update the investigations.
								summaryVO=null;
								
							DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT = nbsDocumentVO.getDsmUpdateAlgorithmDT();
							if (summaryVO != null && summaryVO.getPublicHealthCaseUid() != null) {
								PageActProxyVO OldPageActProxyVO = EdxPHCRDocumentUtil.getPageProxyVOWithoutSummary(
										summaryVO.getPublicHealthCaseUid(), nbsSecurityObj);
								Object pageObj = null;
								EdxCDAInformationDT informationDT = new EdxCDAInformationDT(edxRuleAlgorithmManagerDT);
								informationDT.setPatientUid(-2);
								java.util.Date dateTime = new java.util.Date();
								Timestamp time = new Timestamp(dateTime.getTime());
								informationDT.setAddTime(time);
								CDAPHCProcessor.initializeProcessor();
								pageObj = CDAPHCProcessor.createPageActProxyVO(datamigration, nbsDocumentVO,
										informationDT, nbsSecurityObj);
								PageActProxyVO pageActProxyVO = (PageActProxyVO) pageObj;
								EdxPHCRUpdateHelper updateHelper = new EdxPHCRUpdateHelper();
								updateHelper.preparePageActProxyVOForUpdate(pageActProxyVO, OldPageActProxyVO,nbsDocumentVO.getDsmUpdateAlgorithmDT());
								// Based on the update flags this will update the investigation
								actUid = setPageProxy(pageActProxyVO, nbsSecurityObj, NBSOperationLookup.EDIT);
								edxRuleAlgorithmManagerDT.setCaseUpdated(true);
								if (summaryVO.getScenario() != null
										&& (summaryVO.getScenario().equals(NEDSSConstants.SINGLE_OPEN)))
									edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_INVESTIGATION_OPEN
											+ nbsDocumentVO.getConditionName()
											+ EdxPHCRConstants.SUM_MSG_INVESTIGATION_UPDATE_2 + summaryVO.getLocalId());
								else if (summaryVO.getScenario() != null
										&& (summaryVO.getScenario().equals(NEDSSConstants.MULTI_OPEN)))
									edxRuleAlgorithmManagerDT.setErrorText(
											EdxPHCRConstants.SUM_MSG_INVESTIGATION_MULTI_OPEN_CREATE_FAIL_1
													+ nbsDocumentVO.getConditionName()
													+ EdxPHCRConstants.SUM_MSG_INVESTIGATION_UPDATE_2
													+ summaryVO.getLocalId());
								else if (summaryVO.getScenario() != null
										&& (summaryVO.getScenario().equals(NEDSSConstants.SINGLE_CLOSED)))
									edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_INVESTIGATION_CLOSED
											+ nbsDocumentVO.getConditionName()
											+ EdxPHCRConstants.SUM_MSG_INVESTIGATION_UPDATE_2 + summaryVO.getLocalId());
								else if (summaryVO.getScenario() != null
										&& (summaryVO.getScenario().equals(NEDSSConstants.MULTI_CLOSED)))
									edxRuleAlgorithmManagerDT.setErrorText(
											EdxPHCRConstants.SUM_MSG_INVESTIGATION_MULTI_CLOSED_CREATE_FAIL_1
													+ nbsDocumentVO.getConditionName()
													+ EdxPHCRConstants.SUM_MSG_INVESTIGATION_UPDATE_2
													+ summaryVO.getLocalId());

							}
							else {

								if (summaryVO != null && summaryVO.getScenario() != null
										&& summaryVO.getScenario().equals(NEDSSConstants.SINGLE_CLOSED)) {
									String singleClosed = dsmUpdateAlgorithmDT == null ? null
											: dsmUpdateAlgorithmDT.getUpdateClosedBehaviour();
									if (singleClosed != null && singleClosed.equals("DRRQ"))
										edxRuleAlgorithmManagerDT.setErrorText(
												EdxPHCRConstants.SUM_MSG_INVESTIGATION_CLOSED_CREATE_FAIL_1
														+ nbsDocumentVO.getConditionName()
														+ EdxPHCRConstants.SUM_MSG_INVESTIGATION_CREATE_FAIL_2);
									else {
										NBSDocumentDT nbsDocumentDT = nbsDocumentVO.getNbsDocumentDT();
										nbsDocumentDT.setRecordStatusCd(NEDSSConstants.OBS_PROCESSED);
										nbsDocument.updateDocumentAsProcessed(nbsDocumentDT, nbsSecurityObj);
										edxRuleAlgorithmManagerDT.setErrorText(
												EdxPHCRConstants.SUM_MSG_INVESTIGATION_CLOSED_CREATE_FAIL_1
														+ nbsDocumentVO.getConditionName()
														+ EdxPHCRConstants.SUM_MSG_INVESTIGATION_UPDATE_3);
									}

								}

								else if (summaryVO != null && summaryVO.getScenario() != null
										&& summaryVO.getScenario().equals(NEDSSConstants.MULTI_CLOSED)) {
									String multiClosed = dsmUpdateAlgorithmDT == null ? null
											: dsmUpdateAlgorithmDT.getUpdateMultiClosedBehaviour();					
									if (multiClosed != null && multiClosed.equals("DRRQ"))
										edxRuleAlgorithmManagerDT.setErrorText(
												EdxPHCRConstants.SUM_MSG_INVESTIGATION_MULTI_CLOSED_CREATE_FAIL_1
														+ nbsDocumentVO.getConditionName()
														+ EdxPHCRConstants.SUM_MSG_INVESTIGATION_CREATE_FAIL_2);
									else {
										NBSDocumentDT nbsDocumentDT = nbsDocumentVO.getNbsDocumentDT();
										nbsDocumentDT.setRecordStatusCd(NEDSSConstants.OBS_PROCESSED);
										nbsDocument.updateDocumentAsProcessed(nbsDocumentDT, nbsSecurityObj);
										edxRuleAlgorithmManagerDT.setErrorText(
												EdxPHCRConstants.SUM_MSG_INVESTIGATION_MULTI_CLOSED_CREATE_FAIL_1
														+ nbsDocumentVO.getConditionName()
														+ EdxPHCRConstants.SUM_MSG_INVESTIGATION_UPDATE_3);
									}

								}

								else if (summaryVO != null && summaryVO.getScenario() != null
										&& summaryVO.getScenario().equals(NEDSSConstants.MULTI_OPEN)) {
									String multiOpen = dsmUpdateAlgorithmDT == null ? null
											: dsmUpdateAlgorithmDT.getUpdateMultiOpenBehaviour();
									if (multiOpen != null && multiOpen.equals("DRRQ"))
										edxRuleAlgorithmManagerDT.setErrorText(
												EdxPHCRConstants.SUM_MSG_INVESTIGATION_MULTI_OPEN_CREATE_FAIL_1
														+ nbsDocumentVO.getConditionName()
														+ EdxPHCRConstants.SUM_MSG_INVESTIGATION_CREATE_FAIL_2);
									else {
										NBSDocumentDT nbsDocumentDT = nbsDocumentVO.getNbsDocumentDT();
										nbsDocumentDT.setRecordStatusCd(NEDSSConstants.OBS_PROCESSED);
										nbsDocument.updateDocumentAsProcessed(nbsDocumentDT, nbsSecurityObj);
										edxRuleAlgorithmManagerDT.setErrorText(
												EdxPHCRConstants.SUM_MSG_INVESTIGATION_MULTI_OPEN_CREATE_FAIL_1
														+ nbsDocumentVO.getConditionName()
														+ EdxPHCRConstants.SUM_MSG_INVESTIGATION_UPDATE_3);
									}

								}

								else
									if(!skipDRRQDocumentUpdated)
									edxRuleAlgorithmManagerDT
											.setErrorText(EdxPHCRConstants.SUM_MSG_INVESTIGATION_CREATE_FAIL_1
													+ nbsDocumentVO.getConditionName()
													+ EdxPHCRConstants.SUM_MSG_INVESTIGATION_CREATE_FAIL_2);
							}
							userTrans.commit();
						}//nbsDocumentVO.getAssoSummaryCaseList()
						
					}else{
						if(!nbsDocumentVO.isJurisdictionDerivedFromPreviousDoc())
							addJurisdictionSuccessDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO);
						/* if contact Record Doc, add the necessary activity logs, commit the transaction and return*/
						if(edxRuleAlgorithmManagerDT.isContactRecordDoc() || nbsDocumentVO.isLabReportDoc() || nbsDocumentVO.isMorbReportDoc() || !nbsDocumentVO.isOngoingCase()){
							userTrans.commit();
							
							
					
							
							if(!documentUpdated)
								addDocumentSuccessDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
							
							addDocumentMatchDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
							
							if(!documentUpdated){
								if (multiplePatients) {
									addMultiplePatientDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
								} else {
									if (!patientIsOld) {
										addPatientNewDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
									} else {
										addPatientOldDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
									}
								} 
							}
							return edxRuleAlgorithmManagerDT;
						}
						
						
						
						/*
						 * create a PageActProxyVO if PageBuilder page and matching algorithm
						 */
						/* Defect fix for Rel4.3.0.1 patch - Don't create a page object if there is no active algorithm for condition*/
					
						
						
						boolean algorithmExists = true;
						Collection<Object> algColl = null;
						//call the cache to see if any Case algorithms exist for the condition
						if(!skipDRRQDocumentUpdated)
							algColl = DSMAlgorithmCache.getCaseAlgorithmsForConditionCollection(nbsDocumentVO.getNbsDocumentDT().getCd(), nbsSecurityObj);
						if(algColl == null || algColl.size() == 0)//FATIMA: CONTINUE HERE
									algorithmExists = false;
						
						Object pageObj = null;
						if (nbsDocumentVO.isConditionFound() && algorithmExists) {
							if(nbsDocumentVO.getNbsDocumentDT().getDocumentObject() instanceof ContainerDocument)
								pageObj = EdxPHCRDocumentUtil.createPageActProxyVO(nbsDocumentVO, nbsSecurityObj);
							else if(nbsDocumentVO.getNbsDocumentDT().getDocumentObject() instanceof ClinicalDocumentDocument1){
								EdxCDAInformationDT informationDT = new EdxCDAInformationDT(edxRuleAlgorithmManagerDT);
								informationDT.setPatientUid(-2);
								java.util.Date dateTime = new java.util.Date();
								Timestamp time = new Timestamp(dateTime.getTime());
								informationDT.setAddTime(time);
								CDAPHCProcessor.initializeProcessor();
								pageObj = CDAPHCProcessor.createPageActProxyVO(datamigration, nbsDocumentVO, informationDT, nbsSecurityObj);
							}
							// TESTING
							xStatus = getTransactionStatus(userTrans.getStatus());
							System.out.println(xStatus);
											
						}
	
						boolean reqFieldsError = false;
						String reqFieldsMessage = null;
						/*
						 * get the rule algorithm using ValidateDecisionSupport
						 * passing the page object
						 */
						if (pageObj != null) {
							/*
							 * add details collected elsewhere
							 */
							Collection<EDXActivityDetailLogDT> newDetails;
							
							if (pageObj instanceof PageActProxyVO) {
								
								PageActProxyVO pageActProxyVO = (PageActProxyVO) pageObj;
								newDetails = pageActProxyVO.getPublicHealthCaseVO().getEdxPHCRLogDetailDTCollection();
								reqFieldsMessage = pageActProxyVO.getPublicHealthCaseVO().getErrorText();
								if (reqFieldsMessage != null) {
									reqFieldsError = true;	
								}
							} else if (pageObj instanceof PamProxyVO) {
								newDetails = ((PamProxyVO) pageObj).getPublicHealthCaseVO().getEdxPHCRLogDetailDTCollection();
							} else {
								throw new NEDSSAppException("Unknown page type: " + pageObj.getClass().getCanonicalName());
							}
							if (newDetails != null) {
								edxRuleAlgorithmManagerDT.getEdxActivityLogDT().getEDXActivityLogDTWithVocabDetails().addAll(newDetails);
							}
	
							EdxRuleAlgorothmManagerDT oldEdxRuleAlgorithmManagerDT = edxRuleAlgorithmManagerDT;
							
							// TESTING
							xStatus = getTransactionStatus(userTrans.getStatus());
							System.out.println(xStatus);
							
							if(!skipDRRQDocumentUpdated)
								edxRuleAlgorithmManagerDT = ValidateDecisionSupport.validateProxyVO(pageObj, algColl, nbsSecurityObj);
							
							// TESTING
							xStatus = getTransactionStatus(userTrans.getStatus());
							System.out.println(xStatus);
							
	
							if (edxRuleAlgorithmManagerDT == null) {
								edxRuleAlgorithmManagerDT = oldEdxRuleAlgorithmManagerDT;
							} else {
								edxRuleAlgorithmManagerDT.setEdxActivityLogDT(oldEdxRuleAlgorithmManagerDT.getEdxActivityLogDT());
								edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
							}
						}
	
						//if(!skipDRRQDocumentUpdated)
						if (edxRuleAlgorithmManagerDT.getObject() == null || reqFieldsError || userTrans.getStatus() != Status.STATUS_ACTIVE) {
							boolean rolledBack = false;
							if (edxRuleAlgorithmManagerDT.getObject() != null && userTrans.getStatus() == Status.STATUS_ACTIVE) {
								ofci = edxRuleAlgorithmManagerDT.getOnFailureToCreateInv();
								if (userTrans.getStatus() == Status.STATUS_ACTIVE) {
									if (ofci != null && ofci.equalsIgnoreCase(EdxRuleConstants.INV_ROLL_BACK_ALL)) {
										userTrans.rollback();
									}
								}
							} 
							if (!rolledBack && userTrans.getStatus() == Status.STATUS_ACTIVE) {
								userTrans.commit();
								
						//		documentUpdated = nbsDocumentVO.isDocumentUpdate();
								if(!documentUpdated)
									addDocumentSuccessDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
								addDocumentMatchDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
								
								if(!documentUpdated){
									if (multiplePatients) {
										addMultiplePatientDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
									} else {
										if (!patientIsOld) {
											addPatientNewDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
										} else {
											addPatientOldDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
										}
									}
								}
							}
	
							edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
	
							if (reqFieldsError) {
								edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
								edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_MISSING_FLDS+EdxPHCRConstants.INV_STR);
								addProvidedFailureDetailMsg(edxRuleAlgorithmManagerDT, reqFieldsMessage);
								if (edxRuleAlgorithmManagerDT.getObject() != null) {
									addOfciDetailMsg(edxRuleAlgorithmManagerDT, edxRuleAlgorithmManagerDT.getOnFailureToCreateInv());
									edxActivityLogDT.setAlgorithmName(edxRuleAlgorithmManagerDT.getDsmAlgorithmName());
									String action = edxRuleAlgorithmManagerDT.getAction();
									edxActivityLogDT.setAlgorithmAction(action);
								}
							} else {
								
								if(!skipDRRQDocumentUpdated){
								if (nbsDocumentVO.isConditionFound()) {
									if(nbsDocumentVO.getNbsDocumentDT()!=null && nbsDocumentVO.getNbsDocumentDT().getRecordStatusCd()!=null && nbsDocumentVO.getNbsDocumentDT().getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_PROCESSED)){
									edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_ALGORITHM_FAIL1 + nbsDocumentVO.getConditionName() + EdxPHCRConstants.SUM_MSG_ALGORITHM_FAIL3);
									addAlgorithmFailDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO);
									}else{
										edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_ALGORITHM_FAIL1 + nbsDocumentVO.getConditionName() + EdxPHCRConstants.SUM_MSG_ALGORITHM_FAIL2);
										addAlgorithmFailDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO);
									}
								} else {
									edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_NO_CONDITION);
									addNoConditionDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO);
								}
								}
							}
							
							// TESTING
	//						 if (userTrans.getStatus() == Status.STATUS_MARKED_ROLLBACK) {
	//							 userTrans.rollback();
	//						 }
							xStatus = getTransactionStatus(userTrans.getStatus());
							System.out.println(xStatus);
						} else {
							edxActivityLogDT.setAlgorithmName(edxRuleAlgorithmManagerDT.getDsmAlgorithmName());
							String action = edxRuleAlgorithmManagerDT.getAction();
							edxActivityLogDT.setAlgorithmAction(action);
							/*
							 * The following code uses the negative case to
							 * determine the positive processing path, e.g. if 'what
							 * to do in case of failure to create an investigation'
							 * is PURGE_INV_RETAIN_EV that implies that an
							 * investigation and event (document) is to be created.
							 */
							/*
							 * Values from web page:
							 * 
							 * On Failure to Create Investigation OFCI Rollback All
							 * (RB_ALL_I) Rollback all Rollback Investigation
							 * (RB_INV_I) Rollback Investigation
							 * 
							 * On Failure to Create Notification OFCN Per 10/25/2011
							 * update, specific values for ofcn are ignored; just
							 * use null/non-null test
							 */
							// try{
							/*
							 * set the (perhaps null) jurisdiction match error text
							 * in the EdxRuleAlgorothmManagerDT
							 */
							edxRuleAlgorithmManagerDT.setErrorText(noJursDictionError);
							/*
							 * ofci = on failure to create investigation 
							 * ofcn = on failure to create notification
							 */
							ofci = edxRuleAlgorithmManagerDT.getOnFailureToCreateInv();
							ofcn = edxRuleAlgorithmManagerDT.getOnFailureToCreateNND();
	
							/*
							 * Note that transaction is in progress due to earlier
							 * insert person and document
							 */
	
							if (ofci == null && ofcn == null) {
								logger.error("ERROR:-ValidateDecisionSupport.validateProxyVO No action has been specified. Please check." + edxRuleAlgorithmManagerDT.toString());
								throw new EJBException("ERROR:-ValidateDecisionSupport.validateProxyVO No action has been specified. Please check." + edxRuleAlgorithmManagerDT.toString());
							}
	
							if (ofci!=null && ofci.equalsIgnoreCase(EdxRuleConstants.INV_ROLL_BACK_ALL)) {
	
								// OFCI = "Roll-Back All (including event record)"
								try {
									actUid = setPageProxy(pageObj, nbsSecurityObj, NBSOperationLookup.ADD);
									
									if (investigationTestingException) {
										throw new Exception("test");
									}
									if (actUid >= 0L) {
										if (pageObj instanceof PageActProxyVO) {
											edxRuleAlgorithmManagerDT
													.setPHCUid(actUid);
											//TODO: revisit setting the patient id
											edxRuleAlgorithmManagerDT
													.setPHCRevisionUid(nbsDocumentVO.getPatientVO().getThePersonDT().getPersonUid());
											edxRuleAlgorithmManagerDT.setDocumentDT(nbsDocumentVO.getNbsDocumentDT());
										}
										userTrans.commit();
										
									//	documentUpdated = nbsDocumentVO.isDocumentUpdate();
										if(!documentUpdated)
											addDocumentSuccessDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
										addDocumentMatchDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
										
										if(!documentUpdated){
											if (multiplePatients) {
												addMultiplePatientDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
											} else {
												if (!patientIsOld) {
													addPatientNewDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
												} else {
													addPatientOldDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
												}
											} 
										}
										addInvestigationSuccessDetailMsg(edxRuleAlgorithmManagerDT, actUid);
									} else {
										userTrans.rollback();
										edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
										edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_INVESTIGATION_FAIL);
										addInvestigationNotAuthDetailMsg(edxRuleAlgorithmManagerDT, nbsSecurityObj);
										addOfciDetailMsg(edxRuleAlgorithmManagerDT, ofci);
										error = true;
									}
								} catch (Exception e) {
									error = true;
									if (userTrans.getStatus() == Status.STATUS_ACTIVE) {
										userTrans.rollback();
									}
									edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
									edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_INVESTIGATION_FAIL);
									addInvestigationFailDetailMsg(edxRuleAlgorithmManagerDT, e);
									addOfciDetailMsg(edxRuleAlgorithmManagerDT, ofci);
									edxActivityLogDT.setException(e + "\n" + stackTraceToString(e));
								}
							} else if (ofci!=null && ofci.equalsIgnoreCase(EdxRuleConstants.RETAIN_EVENT)) {
								/*
								 * As this is RETAIN_EVENT, commit the existing
								 * transaction and start a new transaction
								 */
								userTrans.commit();
							//	documentUpdated = nbsDocumentVO.isDocumentUpdate();
								if(!documentUpdated)
									addDocumentSuccessDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
								addDocumentMatchDetailMessage(edxRuleAlgorithmManagerDT, nbsDocumentVO);
								
								if(!documentUpdated){
									if (multiplePatients) {
										addMultiplePatientDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
									} else {
										if (!patientIsOld) {
											addPatientNewDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
										} else {
											addPatientOldDetailMsg(edxRuleAlgorithmManagerDT, nbsDocumentVO, patientUid);
										}
									}
								}
	
								userTrans.begin();
	
								// OFCI = "Retain Event Record"
								try {
									actUid = setPageProxy(pageObj, nbsSecurityObj, NBSOperationLookup.ADD);
	
									if (investigationTestingException) {
										throw new Exception("test");
									}
									if (actUid >= 0L) { 
										if (pageObj instanceof PageActProxyVO) {
											edxRuleAlgorithmManagerDT
													.setPHCUid(actUid);
											//TODO: revisit setting the patient id
											edxRuleAlgorithmManagerDT
													.setPHCRevisionUid(nbsDocumentVO.getPatientVO().getThePersonDT().getPersonUid());
											edxRuleAlgorithmManagerDT.setDocumentDT(nbsDocumentVO.getNbsDocumentDT());
										}
										addInvestigationSuccessDetailMsg(edxRuleAlgorithmManagerDT, actUid);
									} else {
										edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
										edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_INVESTIGATION_FAIL);
										addInvestigationNotAuthDetailMsg(edxRuleAlgorithmManagerDT, nbsSecurityObj);
										error = true;
									}
									if (error) {
										userTrans.rollback();
										addOfciDetailMsg(edxRuleAlgorithmManagerDT, ofci);
									} else {
										userTrans.commit();
									}
								} catch (Exception e) {
									error = true;
									if (userTrans.getStatus() == Status.STATUS_ACTIVE) {
										userTrans.rollback();
									}
									userTrans.rollback();
									edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
									edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_INVESTIGATION_FAIL);
									// edxActivityLogDT.setException(SUM_MSG_INVESTIGATION_FAIL);
									addInvestigationFailDetailMsg(edxRuleAlgorithmManagerDT, e);
									addOfciDetailMsg(edxRuleAlgorithmManagerDT, ofci);
									edxActivityLogDT.setException(e + "\n" + stackTraceToString(e));
								}
							}
	
							if (ofcn != null && !error) {
								userTrans.begin();
								try {
									if (notificationTestingException) {
										throw new Exception("test");
									}
									EdxCommonHelper.createNotification(edxRuleAlgorithmManagerDT, pageObj, nbsSecurityObj);
									
									if (edxRuleAlgorithmManagerDT.getStatus().equals(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure))
									{
										userTrans.rollback();
										addOfcnDetailMsg(edxRuleAlgorithmManagerDT, ofcn);
									} else {
										userTrans.commit();
									}
								} catch (Exception e) {
									error = true;
									if (userTrans.getStatus() == Status.STATUS_ACTIVE) {
										userTrans.rollback();
									}
									edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
									edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_NOTIFICATION_FAIL);
									addNotificationFailDetailMsg(edxRuleAlgorithmManagerDT, e);
									addOfcnDetailMsg(edxRuleAlgorithmManagerDT, ofcn);
									edxActivityLogDT.setException(e + "\n" + stackTraceToString(e));
								}
							}
						}
					}
					
					
				}
			}
		} catch (Exception e) {
			logger.error("Exception in createDocumentInvestigation", e);
			if(e.getMessage()!=null && (e.getMessage().indexOf("Invalid XML")!=-1 || (e.getMessage().indexOf("Error while parsing")!=-1)))
				edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_INVALID_XML);
			else if(e.getMessage()!=null && e.getMessage().indexOf("Unexpected character")!=-1)
				edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_UNEXPECTED_CHARACTER);
			else if(e.getMessage()!=null && e.getMessage().indexOf("PHC_TYPE from table:  v_Condition_code")!=-1)
				edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_NO_CONDITION_ERROR);
			else if(e.getMessage()!=null && e.getMessage().indexOf("String or binary data would be truncated")!=-1)
				edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_TRUNCATED_DATA);
			else if(e.getMessage()!=null && e.getMessage().indexOf("activity time is out of sequence")!=-1)
				edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_UPDATE_ERROR);
			edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
			edxRuleAlgorithmManagerDT.getEdxActivityLogDT().setException(e.getMessage()==null?""+ e: e.getMessage()+ e);
			addUnexpectedExceptionDetailMsg(edxRuleAlgorithmManagerDT, e);
			try{
				if (userTrans.getStatus() == Status.STATUS_ACTIVE || userTrans.getStatus() == Status.STATUS_MARKED_ROLLBACK) {
					userTrans.rollback();
				}
			}catch(Exception ex){
				logger.error("Exception while rolling back transaction in createDocumentInvestigation", ex);
			}
		}
		return edxRuleAlgorithmManagerDT;
	}


	private Long setPageProxy(Object pageObj, NBSSecurityObj nbsSecurityObj, String operation) throws Exception, NEDSSAppException {
		Long actUid;
		boolean authorized = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, operation); // ADD CREATE AUTOCREATE UPDATE
		if (authorized) {
			actUid = EdxPHCRDocumentUtil.createPageProxyVO(pageObj, nbsSecurityObj, NEDSSConstants.CASE);
		} else {
			actUid = -1L;
		}
		return actUid;
	}
	
	public Object createInvestigation(Long documentUid, NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException, NEDSSAppException {
		EdxPHCRDocumentUtil nbsPHCRDocumentUtil = new EdxPHCRDocumentUtil();
		Object obj = nbsPHCRDocumentUtil.createInvestigation(documentUid, nbsSecurityObj);
		return obj;
	}

	

	private String getTransactionStatus(int status) {
		if (status == Status.STATUS_ACTIVE) return "STATUS_ACTIVE";
		if (status == Status.STATUS_COMMITTED) return "STATUS_COMMITTED";
		if (status == Status.STATUS_COMMITTING) return "STATUS_COMMITTING";
		if (status == Status.STATUS_MARKED_ROLLBACK) return "STATUS_MARKED_ROLLBACK";
		if (status == Status.STATUS_NO_TRANSACTION) return "STATUS_NO_TRANSACTION";
		if (status == Status.STATUS_PREPARED) return "STATUS_PREPARED";
		if (status == Status.STATUS_PREPARING) return "STATUS_PREPARING";
		if (status == Status.STATUS_ROLLEDBACK) return "STATUS_ROLLEDBACK";
		if (status == Status.STATUS_ROLLING_BACK) return "STATUS_ROLLING_BACK";
		if (status == Status.STATUS_UNKNOWN) return "STATUS_UNKNOWN";
		return "unknown: "+status;
	}

	private void addToActivityDetailLog(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, String msg, EdxPHCRConstants.MSG_TYPE msgType, String uid, EdxRuleAlgorothmManagerDT.STATUS_VAL status) {
		EDXActivityDetailLogDT edxActivityDetailLogDT = new EDXActivityDetailLogDT();
		edxActivityDetailLogDT.setRecordId(uid);
		edxActivityDetailLogDT.setRecordType(msgType.name());
		edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
		edxActivityDetailLogDT.setLogType(status.name());

		if (msg.length() > EdxPHCRConstants.MAX_DETAIL_COMMENT_LEN) {
			msg = msg.substring(0, EdxPHCRConstants.MAX_DETAIL_COMMENT_LEN) + "...";
		}

		edxActivityDetailLogDT.setComment(msg);

		if (edxRuleAlgorithmManagerDT.getEdxActivityLogDT().getEDXActivityLogDTWithVocabDetails() == null) {
			ArrayList<Object> al = new ArrayList<Object>();
			edxRuleAlgorithmManagerDT.getEdxActivityLogDT().setEDXActivityLogDTWithVocabDetails(al);
		}
		edxRuleAlgorithmManagerDT.getEdxActivityLogDT().getEDXActivityLogDTWithVocabDetails().add(edxActivityDetailLogDT);
	}
	
	private void addDocumentSuccessDetailMessage(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, NBSDocumentVO nbsDocumentVO) {
			addToActivityDetailLog
			( 
				edxRuleAlgorithmManagerDT, 
				EdxPHCRConstants.DET_MSG_DOCUMENT_SUCCESS1 + nbsDocumentVO.getNbsDocumentDT().getLocalId() + EdxPHCRConstants.DET_MSG_DOCUMENT_SUCCESS2, 
				EdxPHCRConstants.MSG_TYPE.Document, 
				"" + nbsDocumentVO.getNbsDocumentDT().getNbsDocumentUid(), 
				EdxRuleAlgorothmManagerDT.STATUS_VAL.Success
			);
	}
	



	private void addDocumentMatchDetailMessage(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, NBSDocumentVO nbsDocumentVO) {
		if(nbsDocumentVO.getOriginalPHCRLocalId()!=null && nbsDocumentVO.getOriginalPHCRLocalId().trim().length()>0){
			addToActivityDetailLog
			( 
				edxRuleAlgorithmManagerDT, 
				EdxPHCRConstants.DET_MSG_DOCUMENT_SUCCESS1 + EdxPHCRConstants.DET_MSG_UPDATE_DOCUMENT +nbsDocumentVO.getOriginalPHCRLocalId(), 
				EdxPHCRConstants.MSG_TYPE.Document, 
				"" + nbsDocumentVO.getNbsDocumentDT().getNbsDocumentUid(), 
				EdxRuleAlgorothmManagerDT.STATUS_VAL.Success
			);
		}
	}
	private void addMultiplePatientDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, NBSDocumentVO nbsDocumentVO, long patientUid) {
		PersonNameDT patientName = nbsDocumentVO.getPatientVO().getPersonNameDT_s(0);
		String firstNm = patientName.getFirstNm();
		String lastNm = patientName.getLastNm();
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			EdxPHCRConstants.DET_MSG_MULTIPLE_PATIENTS + lastNm + ", " + firstNm + " (UID:" + patientUid + ")", 
			EdxPHCRConstants.MSG_TYPE.Patient, 
			"" + patientUid, 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Success
		);
	}

	private void addPatientNewDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, NBSDocumentVO nbsDocumentVO, long patientUid) {
		PersonNameDT patientName = nbsDocumentVO.getPatientVO().getPersonNameDT_s(0);
		String firstNm = patientName.getFirstNm();
		String lastNm = patientName.getLastNm();
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			EdxPHCRConstants.DET_MSG_PATIENT_NEW_SUCCESS + lastNm + ", " + firstNm + " (UID:" + patientUid + ")", 
			EdxPHCRConstants.MSG_TYPE.Patient, 
			"" + patientUid, 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Success
		);
	}

	private void addPatientOldDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, NBSDocumentVO nbsDocumentVO, long patientUid) {
		PersonNameDT patientName = nbsDocumentVO.getPatientVO().getPersonNameDT_s(0);
		String firstNm = patientName.getFirstNm();
		String lastNm = patientName.getLastNm();
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			EdxPHCRConstants.DET_MSG_PATIENT_OLD_SUCCESS + lastNm + ", " + firstNm + " (UID:" + patientUid + ")", 
			EdxPHCRConstants.MSG_TYPE.Patient, 
			"" + patientUid, 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Success
		);
	}
	
	private void addNoConditionDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, NBSDocumentVO nbsDocumentVO) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			nbsDocumentVO.getConditionName()+EdxPHCRConstants.DET_MSG_NO_CONDITION,  
			EdxPHCRConstants.MSG_TYPE.Algorithm, 
			"", 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure
		);
	}

	private void addJurisdictionFailDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			EdxPHCRConstants.DET_MSG_JURISDICTION_FAIL, 
			EdxPHCRConstants.MSG_TYPE.Jurisdiction, 
			"", 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure
		);

	}

	private void addJurisdictionSuccessDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, NBSDocumentVO nbsDocumentVO) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			EdxPHCRConstants.DET_MSG_JURISDICTION_SUCCESS, 
			EdxPHCRConstants.MSG_TYPE.Jurisdiction, 
			nbsDocumentVO.getNbsDocumentDT().getJurisdictionCd(), 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Success
		);

	}

	private void addAlgorithmFailDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, NBSDocumentVO nbsDocumentVO) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			EdxPHCRConstants.DET_MSG_ALGORITHM_FAIL1 + nbsDocumentVO.getConditionName() + EdxPHCRConstants.DET_MSG_ALGORITHM_FAIL2, 
			EdxPHCRConstants.MSG_TYPE.Algorithm, 
			"",
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure
		);
	}

	private void addInvestigationSuccessDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, long actUid) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			EdxPHCRConstants.DET_MSG_INVESTIGATION_SUCCESS + " (UID:" + actUid + ")", 
			EdxPHCRConstants.MSG_TYPE.Investigation, 
			"" + actUid, 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Success
		);

	}

	private void addInvestigationNotAuthDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, NBSSecurityObj nbsSecurityObj) {
		String uid = nbsSecurityObj.getTheUserProfile().getTheUser().getUserID();
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			EdxPHCRConstants.DET_MSG_NOT_AUTH_1+uid+EdxPHCRConstants.DET_MSG_NOT_AUTH_2+EdxPHCRConstants.INV_STR, 
			EdxPHCRConstants.MSG_TYPE.Investigation, 
			uid, 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure
		);

	}

	private void addInvestigationFailDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, Exception e) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			EdxPHCRConstants.DET_MSG_INVESTIGATION_FAIL + "\n" + e + "\n" + stackTraceToString(e), 
			EdxPHCRConstants.MSG_TYPE.Investigation, 
			"", 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure
		);
	}

	private void addOfciDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, String ofci) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT,
			"Successfully " + CachedDropDowns.getCodeDescTxtForCd(ofci, "NBS_FAILURE_RESPONSE"), 
			EdxPHCRConstants.MSG_TYPE.Investigation, 
			"", 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Success
		);
	}

	private void addNotificationFailDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, Exception e) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			EdxPHCRConstants.DET_MSG_NOTIFICATION_FAIL + "\n" + e + "\n" + stackTraceToString(e), 
			EdxPHCRConstants.MSG_TYPE.Notification, 
			"", 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure
		);
	}

	private void addOfcnDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, String ofcn) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			"Successfully " + CachedDropDowns.getCodeDescTxtForCd(ofcn, "NBS_NOT_FAILURE_RESPONSE"), 
			EdxPHCRConstants.MSG_TYPE.Notification, 
			"", 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Success
		);
	}

	private void addUnexpectedExceptionDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, Exception e) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			"Unexpected Exception: " + e.getMessage() + "\n" + stackTraceToString(e), 
			EdxPHCRConstants.MSG_TYPE.NBS_System, 
			"", 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure
		);
	}
	
	private void addProvidedFailureDetailMsg(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, String msg) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			msg, 
			EdxPHCRConstants.MSG_TYPE.Document, 
			"", 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure
		);
	}
	
	private static String stackTraceToString(Exception e) {
		String ret = "";
		StackTraceElement[] stes = e.getStackTrace();
		for (StackTraceElement ste : stes) {
			ret += ste.toString() + "\n";
		}
		return ret;
	}
	private void addExistingInvMessage(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, NBSDocumentVO nbsDocumentVO) {
		addToActivityDetailLog
		( 
			edxRuleAlgorithmManagerDT, 
			EdxPHCRConstants.EXISTING_INV_FOUND + nbsDocumentVO.getConditionName(), 
			EdxPHCRConstants.MSG_TYPE.Investigation, 
			"" + nbsDocumentVO.getNbsDocumentDT().getNbsDocumentUid(), 
			EdxRuleAlgorothmManagerDT.STATUS_VAL.Success
		);
	}
	
	public void createDocumentInterviews(
			EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT,
			NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException, NEDSSAppException {

		try {
			Object docObject = null;
			if(edxRuleAlgorithmManagerDT!=null && edxRuleAlgorithmManagerDT.getDocumentDT()!=null)
				docObject = edxRuleAlgorithmManagerDT.getDocumentDT().getDocumentObject();
			if (edxRuleAlgorithmManagerDT != null
					&& edxRuleAlgorithmManagerDT.getPHCUid() != null
					&& edxRuleAlgorithmManagerDT.getPHCRevisionUid() != null
					&& docObject != null
					&& docObject instanceof ClinicalDocumentDocument1) {
				XmlObject[] sections = ((ClinicalDocumentDocument1) docObject)
						.getClinicalDocument().selectPath(EdxCDAConstants.CDA_NAMESPACE
								 + EdxCDAConstants.CDA_STRUCTURED_XML_SECTION);
				EdxCDAInformationDT informationDT = new EdxCDAInformationDT(
						edxRuleAlgorithmManagerDT);
				if (sections != null && sections.length > 0) {
					for (XmlObject section : sections) {
						if (((POCDMT000040Section) section).getCode() != null
								&& ((POCDMT000040Section) section).getCode()
										.getCode() != null
								&& ((POCDMT000040Section) section)
										.getCode()
										.getCode()
										.equals(EdxCDAConstants.INTERVIEW_INFO_CD)) {
							POCDMT000040Entry[] entries = ((POCDMT000040Section) section)
									.getEntryArray();
							for (POCDMT000040Entry encounter : entries) {
								CDAInterviewProcessor.initializeProcessor();
								PageActProxyVO interview = (PageActProxyVO) CDAInterviewProcessor
										.createProxyVO(informationDT,
												encounter, nbsSecurityObj);
								
								if (encounter.getEncounter().getIdArray(0).getExtension() != null) {
									ArrayList<Object> ids = new ArrayList<Object>();
									ids.add(encounter.getEncounter().getIdArray(0).getExtension());
									interview.getInterviewVO().
											setEdxEventProcessDTCollection(XMLTypeToNBSObject
													.prepareEventProcessListForNonDocEvents(ids, informationDT,
															NEDSSConstants.IXS_SOURCE_CD));
								}
								Long actUid = EdxPHCRDocumentUtil
										.createPageProxyVO(
												interview,
												nbsSecurityObj,
												NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE);
								interview.getInterviewVO().getTheInterviewDT().setInterviewUid(actUid);
								addInterviewSuccessDetailMsg(edxRuleAlgorithmManagerDT,actUid);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			String error = "Exception while creating interviews from PHCR Document Cause: "+ex.getCause()+System.getProperty("line.separator")+" Exception Message :" +ex.getMessage();
			edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
			addInterviewFailDetailMsg(edxRuleAlgorithmManagerDT, new NEDSSAppException(error, ex));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void createDocumentContactRecord(
			EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			RemoteException, NEDSSAppException {

		try {
			NBSDocumentDT documentDT = edxRuleAlgorithmManagerDT.getDocumentDT();
			Object docObject = edxRuleAlgorithmManagerDT.getDocumentDT()
					.getDocumentObject();
			EdxCDAInformationDT informationDT = new EdxCDAInformationDT(
					edxRuleAlgorithmManagerDT);
			XmlObject[] sections = ((ClinicalDocumentDocument1) docObject)
					.getClinicalDocument()
					.selectPath(
							EdxCDAConstants.CDA_NAMESPACE
									+ EdxCDAConstants.CDA_STRUCTURED_XML_SECTION);
			informationDT.setPatientUid(-2);
			java.util.Date dateTime = new java.util.Date();
			Timestamp time = new Timestamp(dateTime.getTime());
			informationDT.setAddTime(time);
			informationDT.setUserId(documentDT.getAddUserId());
			CDAContactRecordProcessor.initializeProcessor();
			CTContactProxyVO contactProxyVO = (CTContactProxyVO) CDAContactRecordProcessor
					.createProxyVO(informationDT,
							(POCDMT000040Section[]) sections, nbsSecurityObj);
			if(contactProxyVO!=null){
				Long actUid = EdxPHCRDocumentUtil
						.createPageProxyVO(contactProxyVO, nbsSecurityObj,
								NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE);
				contactProxyVO.getcTContactVO().getcTContactDT()
						.setCtContactUid(actUid);
				addContactRecordSuccessDetailMsg(edxRuleAlgorithmManagerDT, actUid);
			}

		} catch (Exception ex) {
			String error = "Exception while creating contact records from PHCR Document";
			edxRuleAlgorithmManagerDT
					.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
			addContactRecordFailDetailMsg(edxRuleAlgorithmManagerDT, ex);
			throw new NEDSSAppException(error, ex);
		}

	}
	
	@SuppressWarnings("unchecked")
	public void createDocumentLabReport(
			EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			RemoteException, NEDSSAppException {

		try {
			CDALabReportProcessor.processLabDocument(edxRuleAlgorithmManagerDT,
							nbsSecurityObj);
				//addContactRecordSuccessDetailMsg(edxRuleAlgorithmManagerDT, actUid);
		} catch (Exception ex) {
			String error = "Exception while processing PHDC lab report "+ex.getMessage();
//			edxRuleAlgorithmManagerDT
//					.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
//			addContactRecordFailDetailMsg(edxRuleAlgorithmManagerDT, ex);
			throw new NEDSSAppException(error, ex);
		}

	}
	
	@SuppressWarnings("unchecked")
	public void createDocumentMorbReport(
			EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			RemoteException, NEDSSAppException {

		try {
			CDAMorbReportProcessor.processMorbDocument(
					edxRuleAlgorithmManagerDT, nbsSecurityObj);
		} catch (Exception ex) {
			String error = "Exception while processing PHDC morb report "
					+ ex.getMessage();
			throw new NEDSSAppException(error, ex);
		}
	}
	
	private void addInterviewSuccessDetailMsg(
			EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, long actUid) {
		addToActivityDetailLog(edxRuleAlgorithmManagerDT,
				EdxPHCRConstants.DET_MSG_INTERVIEW_SUCCESS + " (UID:" + actUid
						+ ")", EdxPHCRConstants.MSG_TYPE.Interview,
				"" + actUid, EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
	}
	
	private void addContactRecordSuccessDetailMsg(
			EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, long actUid) {
		addToActivityDetailLog(edxRuleAlgorithmManagerDT,
				EdxPHCRConstants.DET_MSG_CONTACT_RECORD_SUCCESS + " (UID:" + actUid
						+ ")", EdxPHCRConstants.MSG_TYPE.ContactRecord,
				"" + actUid, EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
	}

	private void addInterviewFailDetailMsg(
			EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, Exception e) {
		addToActivityDetailLog(edxRuleAlgorithmManagerDT,
				EdxPHCRConstants.DET_MSG_INTERVIEW_FAIL + "\n" + e.getMessage() + "\n"
						+ stackTraceToString(e),
				EdxPHCRConstants.MSG_TYPE.Interview, "",
				EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
	}
	
	private void addContactRecordFailDetailMsg(
			EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, Exception e) {
		addToActivityDetailLog(edxRuleAlgorithmManagerDT,
				EdxPHCRConstants.DET_MSG_CONTACT_RECORD_FAIL + "\n" + e.getMessage() + "\n"
						+ stackTraceToString(e),
				EdxPHCRConstants.MSG_TYPE.ContactRecord, "",
				EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
	}
	
	private UpdateCaseSummaryVO identifyCaseToBeUpdated(NBSDocumentVO nbsDocumentVO) throws NEDSSAppException {
		String scenarioExecuted = null;
		try {
			UpdateCaseSummaryVO returnVO = new UpdateCaseSummaryVO();
			ArrayList<Object> multiOpen = new ArrayList<Object>();
			ArrayList<Object> multiClosed = new ArrayList<Object>();
			DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT = nbsDocumentVO.getDsmUpdateAlgorithmDT();
			NedssUtils util = new NedssUtils();

			// Check
			if (nbsDocumentVO.getAssoSummaryCaseList() != null && nbsDocumentVO.getAssoSummaryCaseList().size() == 1) {
				scenarioExecuted = "Single Investigation associated to CR with in configured timeframe";
				UpdateCaseSummaryVO summaryVO = (UpdateCaseSummaryVO) nbsDocumentVO.getAssoSummaryCaseList().get(0);
				if (summaryVO.getInvestigationStatusCd() != null
						&& summaryVO.getInvestigationStatusCd().equals(NEDSSConstants.INVESTIGATION_STATUS_CD))
					summaryVO.setScenario(NEDSSConstants.SINGLE_OPEN);
				else
					summaryVO.setScenario(NEDSSConstants.SINGLE_CLOSED);
				return summaryVO;
			} else if (nbsDocumentVO.getAssoSummaryCaseList() != null
					&& nbsDocumentVO.getAssoSummaryCaseList().size() > 1) {
				Iterator<Object> ite = nbsDocumentVO.getAssoSummaryCaseList().iterator();
				while (ite.hasNext()) {
					UpdateCaseSummaryVO sumVO = (UpdateCaseSummaryVO) ite.next();
					if (sumVO.getInvestigationStatusCd() != null
							&& sumVO.getInvestigationStatusCd().equals(NEDSSConstants.INVESTIGATION_STATUS_CD))
						multiOpen.add(sumVO);
					else
						multiClosed.add(sumVO);
				}

				if (multiOpen != null && multiOpen.size() == 1) {

					scenarioExecuted = "Single Open Investigation associated to CR within configured timeframe (Multi OPEN/CLOSED)";
					returnVO = (UpdateCaseSummaryVO) multiOpen.get(0);
					returnVO.setScenario(NEDSSConstants.SINGLE_OPEN);
					return returnVO;
				} else if (multiOpen != null && multiOpen.size() > 1) {
					scenarioExecuted = "Most Recent Open Investigation associated to CR within configured timeframe";
					util.sortObjectByColumn("getEventDate", (Collection<Object>) multiOpen, true);
					returnVO = (UpdateCaseSummaryVO) multiOpen.get(multiOpen.size() - 1);
					returnVO.setScenario(NEDSSConstants.MULTI_OPEN);
					return returnVO;
				} else if (multiClosed != null && multiClosed.size() == 1) {
					scenarioExecuted = "Single Closed Investigation associated to CR within configured timeframe (Multi OPEN/CLOSED)";
					returnVO = (UpdateCaseSummaryVO) multiClosed.get(0);
					returnVO.setScenario(NEDSSConstants.SINGLE_CLOSED);
					return returnVO;
				} else if (multiClosed != null && multiClosed.size() > 1) {
					scenarioExecuted = "Most Recent Closed Investigation associated to CR within configured timeframe";
					util.sortObjectByColumn("getEventDate", (Collection<Object>) multiClosed, true);
					returnVO = (UpdateCaseSummaryVO) multiClosed.get(multiClosed.size() - 1);
					returnVO.setScenario(NEDSSConstants.MULTI_CLOSED);
					return returnVO;
				}

			}

			else if (nbsDocumentVO.getSummaryCaseListWithInTimeFrame() != null
					&& nbsDocumentVO.getSummaryCaseListWithInTimeFrame().size() == 1) {
				returnVO = (UpdateCaseSummaryVO) nbsDocumentVO.getSummaryCaseListWithInTimeFrame().get(0);
				if (returnVO.getInvestigationStatusCd() != null
						&& returnVO.getInvestigationStatusCd().equals(NEDSSConstants.INVESTIGATION_STATUS_CD)) {
					scenarioExecuted = "Single Open Investigation in patient file within configured timeframe";
					returnVO.setScenario(NEDSSConstants.SINGLE_OPEN);
					return returnVO;
				} else if (returnVO.getInvestigationStatusCd() != null
						&& !returnVO.getInvestigationStatusCd().equals(NEDSSConstants.INVESTIGATION_STATUS_CD)) {
					String updClosedInv = dsmUpdateAlgorithmDT==null?null: dsmUpdateAlgorithmDT.getUpdateClosedBehaviour();
					if (updClosedInv != null && updClosedInv.equalsIgnoreCase("UPDATE")) {
						scenarioExecuted = "Single Closed Investigation in patient file within configured timeframe, Configured to UPDATE";
						returnVO.setScenario(NEDSSConstants.SINGLE_CLOSED);
						return returnVO;
					} else {
						returnVO.setPublicHealthCaseUid(null);
						returnVO.setScenario(NEDSSConstants.SINGLE_CLOSED);
						return returnVO;
					}
				}

			}

			else if (nbsDocumentVO.getSummaryCaseListWithInTimeFrame() != null
					&& nbsDocumentVO.getSummaryCaseListWithInTimeFrame().size() > 1) {
				Iterator<Object> ite = nbsDocumentVO.getSummaryCaseListWithInTimeFrame().iterator();
				while (ite.hasNext()) {
					UpdateCaseSummaryVO sumVO = (UpdateCaseSummaryVO) ite.next();
					if (sumVO.getInvestigationStatusCd() != null
							&& sumVO.getInvestigationStatusCd().equals(NEDSSConstants.INVESTIGATION_STATUS_CD))
						multiOpen.add(sumVO);
					else
						multiClosed.add(sumVO);
				}

				if (multiOpen != null && multiOpen.size() == 1) {
					scenarioExecuted = "Single Open Investigation in patient file within configured timeframe (Multi OPEN/CLOSED)";
					returnVO = (UpdateCaseSummaryVO) multiOpen.get(0);
					returnVO.setScenario(NEDSSConstants.SINGLE_OPEN);
					return returnVO;
				} else if (multiOpen != null && multiOpen.size() > 1) {
					String updMultiOpenInv = dsmUpdateAlgorithmDT == null ? null
							: dsmUpdateAlgorithmDT.getUpdateMultiOpenBehaviour();
					if (updMultiOpenInv != null && updMultiOpenInv.equals("UPDATE")) {
						scenarioExecuted = "Most recent Open Investigation in patient file within configured timeframe, Configured to UPDATE";
						util.sortObjectByColumn("getEventDate", (Collection<Object>) multiOpen, true);
						returnVO = (UpdateCaseSummaryVO) multiOpen.get(multiOpen.size() - 1);
						returnVO.setScenario(NEDSSConstants.MULTI_OPEN);
						return returnVO;
					} else {
						returnVO.setScenario(NEDSSConstants.MULTI_OPEN);
						return returnVO;
					}
				} else if (multiClosed != null && multiClosed.size() == 1) {
					String updClosedInv = dsmUpdateAlgorithmDT == null ? null
							: dsmUpdateAlgorithmDT.getUpdateClosedBehaviour(); 
					if (updClosedInv != null && updClosedInv.equals("UPDATE")) {
						scenarioExecuted = "Single Closed Investigation in patient file within configured timeframe (Multi OPEN/CLOSED), Configured to UPDATE";
						returnVO = (UpdateCaseSummaryVO) multiClosed.get(0);
						returnVO.setScenario(NEDSSConstants.SINGLE_CLOSED);
						return returnVO;
					} else {
						returnVO.setScenario(NEDSSConstants.SINGLE_CLOSED);
						return returnVO;
					}
				} else if (multiClosed != null && multiClosed.size() > 1) {
					String updMultiClosedInv = dsmUpdateAlgorithmDT == null ? null
							: dsmUpdateAlgorithmDT.getUpdateMultiClosedBehaviour(); 
					if (updMultiClosedInv != null && updMultiClosedInv.equals("UPDATE")) {
						scenarioExecuted = "Most recent Closed Investigation in patient file within configured timeframe, Configured to UPDATE";
						util.sortObjectByColumn("getEventDate", (Collection<Object>) multiClosed, true);
						returnVO = (UpdateCaseSummaryVO) multiClosed.get(multiClosed.size() - 1);
						returnVO.setScenario(NEDSSConstants.MULTI_CLOSED);
						return returnVO;
					} else {
						returnVO.setScenario(NEDSSConstants.MULTI_CLOSED);
						return returnVO;
					}
				}
			}
			logger.debug("Executing Scenario: " + scenarioExecuted);
			return returnVO;
		} catch (Exception ex) {
			String error = "Exception while identifying case to be updated: " + scenarioExecuted + " "
					+ ex.getMessage();
			throw new NEDSSAppException(error, ex);

		}
	}

}
