package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import gov.cdc.nedss.act.observation.ejb.dao.ObservationDAOImpl;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.dsm.ActionType;
import gov.cdc.nedss.dsm.AlgorithmDocument;
import gov.cdc.nedss.dsm.AlgorithmDocument.Algorithm;
import gov.cdc.nedss.dsm.CodedType;
import gov.cdc.nedss.dsm.CreateInvestigationType;
import gov.cdc.nedss.dsm.CreateInvestigationWithNNDType;
import gov.cdc.nedss.dsm.DeleteDocumentType;
import gov.cdc.nedss.dsm.ElrAdvancedCriteriaType;
import gov.cdc.nedss.dsm.ElrCriteriaType;
import gov.cdc.nedss.dsm.ElrNumericType;
import gov.cdc.nedss.dsm.EventDateLogicType;
import gov.cdc.nedss.dsm.InvCriteriaType;
import gov.cdc.nedss.dsm.InvValueType;
import gov.cdc.nedss.dsm.InvestigationDefaultValuesType;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dao.DSMAlgorithmDaoImpl;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DSMAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleManageDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRDocumentUtil;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.ValidateDecisionSupport;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJBException;

import org.apache.xmlbeans.XmlOptions;
/**
 * Utility class to validate Decision support algorithm for HL7 ELR Messages
 * @author Pradeep Kumar Sharma 2012
 * Updated code to accomodate for AND/OR logic, and Date logic in the ELR algorithms
 * @update Pradeep Kumar Sharma 2014
 * @update Greg Tucker 2016 - updated matching for new comparators. See DSMLabMatchHelper
 * @company CSRA International, Inc
 */

public class HL7ELRValidateDecisionSupport {
	static final LogUtils logger = new LogUtils(HL7ELRValidateDecisionSupport.class.getName());

	static final String ApplyToAllSystems = "ALL";
	private static List<DSMLabMatchHelper> activeElrAlgorithmList = new ArrayList<DSMLabMatchHelper>();
	private static Boolean elrAlgorithmsPresent = true;  //any active algorithms?
	


	public EdxLabInformationDT validateProxyVO(LabResultProxyVO labResultProxyVO, EdxLabInformationDT edxLabInformationDT,  NBSSecurityObj nbsSecurityObj) {
		try {
			
			if (elrAlgorithmsPresent && activeElrAlgorithmList.isEmpty()) {
				logger.debug("HL7ELRValidateDecisionSupport.validateProxyVO Reloading ELR Algorithm Cache");
				Collection<Object> algorithmCollection =selectDSMAlgorithmDTCollection(nbsSecurityObj);
				if (algorithmCollection == null || algorithmCollection.isEmpty())  {
					//no algorithms defined
					elrAlgorithmsPresent = false;
					logger.debug("HL7ELRValidateDecisionSupport.validateProxyVO No DSM Lab algorithm has been defined or there are no active Lab algorithms.");
					return edxLabInformationDT;
				}
				elrAlgorithmsPresent = false; //could be only inactive algorithms or only Case reports	
				Iterator<Object> iterator = algorithmCollection.iterator();
				while(iterator.hasNext()){
					DSMAlgorithmDT algorithmDT = (DSMAlgorithmDT)iterator.next();
					String algorithmString =algorithmDT.getAlgorithmPayload();
					//skip inactive and case reports
					if (algorithmDT.getStatusCd() != null && algorithmDT.getStatusCd().contentEquals(NEDSSConstants.INACTIVE) ||
							algorithmDT.getEventType() != null && algorithmDT.getEventType().equals(NEDSSConstants.PHC_236))
						continue; //skip inactive
					AlgorithmDocument algorithmDocument = parseAlgorithmXml(algorithmString);
					//helper class DSMLabMatchHelper will assist with algorithm matching
					DSMLabMatchHelper dsmLabMatchHelper = null;
					try {
						if (algorithmDocument != null)
							dsmLabMatchHelper = new DSMLabMatchHelper(algorithmDocument);
					} catch (NEDSSAppException e) {
						//if one fails to parse - continue processing with error
						if (algorithmDocument.getAlgorithm() != null && algorithmDocument.getAlgorithm().getAlgorithmName() != null)
							logger.error("HL7ELRValidateDecisionSupport.dsmLabMatchHelper for algorithm " + algorithmDocument.getAlgorithm().getAlgorithmName() + " failed");
						else
							logger.error("HL7ELRValidateDecisionSupport.dsmLabMatchHelper algorithm Document read issue");
					}
					if (dsmLabMatchHelper != null) {
						activeElrAlgorithmList.add(dsmLabMatchHelper);
						elrAlgorithmsPresent = true;
					}
					//parseXmDocument(algorithmDocument);
				} //hasNext
				//didn't find any?
				if (!elrAlgorithmsPresent) {
					logger.debug("HL7ELRValidateDecisionSupport.validateProxyVO No active Lab algorithm found...");
					return edxLabInformationDT;
				}
			} //algorithmsPresent
			
		} catch (NEDSSAppException e1) {
			logger.error("HL7ELRValidateDecisionSupport.validateProxyVO unable to process algorithm as NEDSSAppException thrown:-", e1);
			throw new EJBException("ERROR:-ValidateDecisionSupport.validateProxyVO unable to process algorithm as NEDSSAppException . Please check."+e1);
		} catch (Exception e1) {
			logger.error("HL7ELRValidateDecisionSupport.validateProxyVO unable to process algorithm as exception thrown:-", e1);
			throw new EJBException("ERROR:-ValidateDecisionSupport.validateProxyVO unable to process algorithm as exception . Please check."+e1);
		}
		
		Collection<Object>  resultedTestColl=new ArrayList<Object>();
		ObservationVO orderedTestObservationVO=null;
		Collection<Object> resultedTestCodeColl =  new ArrayList<Object>();

		Map<Object, Object> questionIdentifierMap = null;
		try 
		{
			AutoInvestigationHandler autoInvestigationHandler = new AutoInvestigationHandler();
			Collection<Object> personVOCollection=new ArrayList<Object>();
			if (labResultProxyVO.getThePersonVOCollection() != null)
				personVOCollection=labResultProxyVO.getThePersonVOCollection();
			
			for (Iterator<ObservationVO> it = labResultProxyVO.getTheObservationVOCollection().iterator(); it.hasNext(); )
			{
				ObservationVO obsVO = (ObservationVO) it.next();
				String obsDomainCdSt1 = obsVO.getTheObservationDT().getObsDomainCdSt1();
				if (obsDomainCdSt1 != null && obsDomainCdSt1.equalsIgnoreCase(EdxELRConstants.ELR_RESULT_CD))
				{
					resultedTestColl.add(obsVO);
					String labResultedTestCd = obsVO.getTheObservationDT().getCd();
					if (obsVO.getTheObservationDT().getCd() == null)
						labResultedTestCd = obsVO.getTheObservationDT().getAltCd();
					if(labResultedTestCd != null && !resultedTestCodeColl.contains(labResultedTestCd))
						resultedTestCodeColl.add(labResultedTestCd);
				}else if (obsDomainCdSt1 != null && obsDomainCdSt1.equalsIgnoreCase(EdxELRConstants.ELR_ORDER_CD))
				{
					orderedTestObservationVO=obsVO;
					orderedTestObservationVO.getTheObservationDT().setObservationUid(edxLabInformationDT.getRootObserbationUid());
					orderedTestObservationVO.setTheParticipationDTCollection(labResultProxyVO.getTheParticipationDTCollection());
				}
			}
			
			//See if we have a matching algorithm for this lab in the order of Algorithm Names
			logger.debug("HL7ELRValidateDecisionSupport.validateProxyVO Checking if a Decision Support ALgorithm is present for this Lab...");
			Collections.sort(activeElrAlgorithmList, AlGORITHM_NM_ORDER);
			Iterator<DSMLabMatchHelper> algorithmIter = activeElrAlgorithmList.iterator();
			while (algorithmIter.hasNext()) {
				DSMLabMatchHelper dsmLabMatchHelper = (DSMLabMatchHelper) algorithmIter.next();
				boolean criteriaMatch = false;
				PageActProxyVO pageActProxyVO =null;
				PamProxyVO pamProxyVO = null;
				PublicHealthCaseVO publicHealthCaseVO =null;
				AlgorithmDocument algorithmDocument =null;
				//reset for every algorithm processing
				edxLabInformationDT.setAssociatedPublicHealthCaseUid(new Long(-1));
				edxLabInformationDT.setMatchingPublicHealthCaseDTColl(null);
				//if returns true, lab matched algorithm, continue with the investigation criteria match is one exists.
				if (dsmLabMatchHelper.isThisLabAMatch(resultedTestCodeColl, resultedTestColl, edxLabInformationDT.getSendingFacilityClia(),edxLabInformationDT.getSendingFacilityName() )) {
					algorithmDocument = dsmLabMatchHelper.getAlgorithmDocument();
					criteriaMatch=true;
					logger.debug("HL7ELRValidateDecisionSupport.validateProxyVO Algorithm " + dsmLabMatchHelper.getAlgorithmNm() + " MATCHED");
				} else {
					logger.debug("HL7ELRValidateDecisionSupport.validateProxyVO Algorithm " + dsmLabMatchHelper.getAlgorithmNm() + " did not match");
					continue;
				}
	
				String conditionCode = null;
				if(algorithmDocument!=null && algorithmDocument.getAlgorithm().getApplyToConditions()!=null){
					CodedType[] conditionArray=algorithmDocument.getAlgorithm().getApplyToConditions().getConditionArray();
					for(int i=0; i<conditionArray.length; i++){
						CodedType codeType =(CodedType)conditionArray[i];
						conditionCode=codeType.getCode();
						logger.debug("HL7ELRValidateDecisionSupport conditionCode is "+conditionCode);
					}
				}
				if (algorithmDocument!=null && criteriaMatch && algorithmDocument.getAlgorithm().getAction()!= null && (algorithmDocument.getAlgorithm().getAction().isSetCreateInvestigation() 
						||algorithmDocument.getAlgorithm().getAction().isSetCreateInvestigationWithNND()) 
						|| (algorithmDocument!=null && algorithmDocument.getAlgorithm().getAction().isSetMarkAsReviewed())) {
					
					if(conditionCode!=null)
					 questionIdentifierMap = EdxPHCRDocumentUtil.loadQuestions(conditionCode);
					edxLabInformationDT.setConditionCode(conditionCode);
					boolean isdateLogicValidForNewInv= false;
					boolean applyAdvInvLogic = false;
					
				if (algorithmDocument.getAlgorithm().getElrAdvancedCriteria()
						.getInvLogic() != null
						&& algorithmDocument.getAlgorithm()
								.getElrAdvancedCriteria().getInvLogic()
								.getInvLogicInd().getCode()
								.equals(NEDSSConstants.YES))
					applyAdvInvLogic = true;
				
				EventDateLogicType eventDateLogicType = algorithmDocument
						.getAlgorithm().getElrAdvancedCriteria()
						.getEventDateLogic();
				
				if (applyAdvInvLogic && eventDateLogicType != null)
				//isdateLogicValidForNewInv = true means no existing investigation was found matching the time logic, so return with no matching algorithms*/
					isdateLogicValidForNewInv = specimenCollectionDateCriteria(
							eventDateLogicType, edxLabInformationDT);
				else
					isdateLogicValidForNewInv = true;
				
				
				boolean isAdvancedInvCriteriaValid = false;
				
				if(algorithmDocument.getAlgorithm().getAction().isSetMarkAsReviewed() && applyAdvInvLogic)
					isAdvancedInvCriteriaValid = checkAdvancedInvCriteria(algorithmDocument, edxLabInformationDT, questionIdentifierMap);
				
				else if(!algorithmDocument.getAlgorithm().getAction().isSetMarkAsReviewed() && applyAdvInvLogic)
					isAdvancedInvCriteriaValid = checkAdvancedInvCriteriaForCreateInvNoti(algorithmDocument, edxLabInformationDT, questionIdentifierMap);

				if(algorithmDocument.getAlgorithm().getAction().isSetMarkAsReviewed() && (!applyAdvInvLogic || (applyAdvInvLogic &&  !isdateLogicValidForNewInv && isAdvancedInvCriteriaValid))){
					edxLabInformationDT.setDsmAlgorithmName(algorithmDocument.getAlgorithm().getAlgorithmName());	
					if(conditionCode!=null)
						edxLabInformationDT.setConditionName(CachedDropDowns.getConditionDesc(conditionCode));
					logger.debug("HL7ELRValidateDecisionSupport is marked ar Reviewed!!!!!!"+edxLabInformationDT.toString());
					edxLabInformationDT.setMatchingAlgorithm(true);
					if(algorithmDocument.getAlgorithm().getAction()!=null && algorithmDocument.getAlgorithm().getAction().getMarkAsReviewed()!=null)
						edxLabInformationDT.setAction(DecisionSupportConstants.MARK_AS_REVIEWED);
					//for create Investigation and/or notification action
				}else if(!algorithmDocument.getAlgorithm().getAction().isSetMarkAsReviewed() && (!applyAdvInvLogic || (applyAdvInvLogic &&  isdateLogicValidForNewInv) || (applyAdvInvLogic &&  !isdateLogicValidForNewInv && isAdvancedInvCriteriaValid))){
						//algorithmDocument.getAlgorithm().getAction().getCreateInvestigation().getInvestigationDefaultValues()
					edxLabInformationDT.setMatchingAlgorithm(true);
						if(algorithmDocument!=null && algorithmDocument.getAlgorithm().getAction()!=null && algorithmDocument.getAlgorithm().getAction().getCreateInvestigation()!=null)
							edxLabInformationDT.setAction(DecisionSupportConstants.CREATE_INVESTIGATION_VALUE);
						else if(algorithmDocument!=null &&  algorithmDocument.getAlgorithm().getAction()!=null && algorithmDocument.getAlgorithm().getAction().getCreateInvestigationWithNND()!=null)
							edxLabInformationDT.setAction(DecisionSupportConstants.CREATE_INVESTIGATION_WITH_NND_VALUE);
						edxLabInformationDT.setInvestigationType(algorithmDocument.getAlgorithm().getInvestigationType());
						Object obj =autoInvestigationHandler.autoCreateInvestigation(orderedTestObservationVO,  edxLabInformationDT, nbsSecurityObj);
						PamVO pamVO= null;
						if (obj instanceof PageProxyVO) {
							pageActProxyVO = (PageActProxyVO) obj;
							publicHealthCaseVO= pageActProxyVO.getPublicHealthCaseVO();
							pamVO=pageActProxyVO.getPageVO();
						}else{
							pamProxyVO = (PamProxyVO) obj;
							publicHealthCaseVO=pamProxyVO.getPublicHealthCaseVO();
							pamVO=pamProxyVO.getPamVO();
						}
						processAction(edxLabInformationDT,algorithmDocument.getAlgorithm());
	
						Map<Object, Object> applyMap = edxLabInformationDT.getEdxRuleApplyDTMap();
						Collection<Object> entityMapCollection =new ArrayList<Object>();
						if (applyMap != null && applyMap.size() > 0 && questionIdentifierMap!=null) {
							Set<Object> set = applyMap.keySet();
							Iterator<?> applyIterator = set.iterator();
							while (applyIterator.hasNext()) {
								String questionId = (String) applyIterator.next();
								EdxRuleManageDT edxRuleManageDT = (EdxRuleManageDT) applyMap.get(questionId);
									NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionIdentifierMap.get(questionId);
									try {
										if (metaData.getDataLocation() != null && metaData.getDataLocation().trim().toUpperCase().startsWith("PUBLIC_HEALTH_CASE")) {
											ValidateDecisionSupport.processNbsObject(edxRuleManageDT, publicHealthCaseVO, metaData);
										} else if (metaData.getDataLocation() != null && metaData.getDataLocation().trim().toUpperCase().startsWith("NBS_CASE_ANSWER")) {
											ValidateDecisionSupport.processNBSCaseAnswerDT(edxRuleManageDT, publicHealthCaseVO, pamVO, metaData);
										} else if (metaData.getDataLocation() != null && metaData.getDataLocation().trim().toUpperCase().startsWith("CONFIRMATION_METHOD.CONFIRMATION_METHOD_CD")) {
											ValidateDecisionSupport.processConfirmationMethodCodeDT(edxRuleManageDT, publicHealthCaseVO, metaData);
										} else if (metaData.getDataLocation() != null && metaData.getDataLocation().trim().toUpperCase().startsWith("CONFIRMATION_METHOD.CONFIRMATION_METHOD_TIME")) {
											ValidateDecisionSupport.processConfirmationMethodTimeDT(edxRuleManageDT, publicHealthCaseVO, metaData);
										} else if(metaData.getDataLocation() != null && metaData.getDataLocation().trim().toUpperCase().startsWith("ACT_ID.ROOT_EXTENSION_TXT")){
											ValidateDecisionSupport.processActIds(edxRuleManageDT, publicHealthCaseVO, metaData);
										}else if(metaData.getDataLocation() != null && metaData.getDataLocation().trim().toUpperCase().startsWith("CASE_MANAGEMENT")  && obj instanceof PageProxyVO){
											ValidateDecisionSupport.processNBSCaseManagementDT(edxRuleManageDT, publicHealthCaseVO, metaData);
										}else if(metaData.getDataLocation() != null && metaData.getDataType().toUpperCase().startsWith("PART")){
											entityMapCollection.add(edxRuleManageDT);
											if(edxRuleManageDT.getParticipationTypeCode()== null || edxRuleManageDT.getParticipationUid()==null  || edxRuleManageDT.getParticipationClassCode()==null){
												logger.error("ValidateDecisionSupport.validateProxyVO Exception is edxRuleManageDT for participation is not valid"+ edxRuleManageDT.toString());
												logger.error("ValidateDecisionSupport.validateProxyVO Exception is edxRuleManageDT for participation is not valid"+ edxRuleManageDT.toString());
												throw new Exception("ValidateDecisionSupport.validateProxyVO Exception thrown for edxRuleManageDT:-" + edxRuleManageDT);
												
											}
										}
									} catch (Exception e) {
										logger.error("ValidateDecisionSupport.validateProxyVO Exception thrown for questionId:-" + questionId);
										logger.error("ValidateDecisionSupport.validateProxyVO Exception thrown for metaData:-" + metaData);
										throw new EJBException("ERROR:-ValidateDecisionSupport.validateProxyVO Exception thrown Please check." + e, e);
									}
	
							}
							
							ValidateDecisionSupport.processConfirmationMethodCodeDTRequired(publicHealthCaseVO);

						}
						
						
						autoInvestigationHandler.transferValuesTOActProxyVO(pageActProxyVO,pamProxyVO, personVOCollection, orderedTestObservationVO,entityMapCollection,questionIdentifierMap);
						if(questionIdentifierMap!=null && questionIdentifierMap.get(EdxPHCRDocumentUtil._REQUIRED)!=null){
							Map<Object, Object> nbsAnswerMap=pamVO.getPamAnswerDTMap();
							Map<Object, Object> requireMap =(Map<Object, Object>)questionIdentifierMap.get(EdxPHCRDocumentUtil._REQUIRED);
							String errorText = EdxPHCRDocumentUtil.requiredFieldCheck(requireMap, nbsAnswerMap);
							publicHealthCaseVO.setErrorText(errorText);
						}
						edxLabInformationDT.setObject(obj);
						edxLabInformationDT.setConditionName(CachedDropDowns.getConditionDesc(publicHealthCaseVO.getThePublicHealthCaseDT().getCd()));
					}
					else{
						edxLabInformationDT.setMatchingAlgorithm(false);
						//return edxLabInformationDT;
					}
				}else{
					edxLabInformationDT.setMatchingAlgorithm(false);
				}
				if(edxLabInformationDT.isMatchingAlgorithm()){
					edxLabInformationDT.setDsmAlgorithmName(algorithmDocument.getAlgorithm().getAlgorithmName());	
					break;
				}
			}
			
			return edxLabInformationDT;
			
		} catch (NEDSSAppException e) {
			logger.error("ValidateDecisionSupport.validateProxyVO Exception thrown:-" + e);
			throw new EJBException("ERROR:-ValidateDecisionSupport.validateProxyVO No action has been specified. Please check." + e, e);
		} catch (Exception e) {
			logger.error("ValidateDecisionSupport.validateProxyVO Exception thrown:-" + e);
			throw new EJBException("ERROR:-ValidateDecisionSupport.validateProxyVO No action has been specified. Please check." + e, e);
		}
	}

	/**
	 * Clear the ELR Algorithm Cache. It will be re-populated when the first lab result is processed.
	 * @param areElrAlgorithmsPresent
	 * @param theActiveElrAlgorithmList
	 */
	public void resetElrDsmAlgorithmCache() {
		activeElrAlgorithmList = new ArrayList<DSMLabMatchHelper>();
		elrAlgorithmsPresent = true;
	}

	/**
	 * This is called by ValidateDecisionSupport for processing PHCR Case (not ELR).
	 * @param edxLabInformationDT
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSAppException
	 */
	public Collection<Object> createAlgorithmMap( EdxLabInformationDT edxLabInformationDT, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
				NBSOperationLookup.DECISION_SUPPORT_ADMIN);

		if (check == false)
		{
			throw new NEDSSSystemException(
					"HL7ELRValidateDecisionSupport.createAlgorithmMap Sorry, you don't have permission to select a Decision Support Mgr Algorithm Record List by Condition!");
		}

		Collection<Object> coll = new ArrayList<Object>();
		Collection<Object> returnColl = new ArrayList<Object>();
		if (coll != null && coll.size() > 0) {
			Iterator<Object> iter = coll.iterator();
			while (iter.hasNext()) {
				DSMAlgorithmDT dSMAlgorithmDT = (DSMAlgorithmDT) iter.next();
				try {
					EdxLabInformationDT edxRuleAlgorothmManagerDT = parseDSMAlgorithmDT(dSMAlgorithmDT);
					edxRuleAlgorothmManagerDT.setSendingFacilityClia(edxLabInformationDT.getSendingFacilityClia());
					edxRuleAlgorothmManagerDT.setSendingFacilityName(edxLabInformationDT.getSendingFacilityName());

					returnColl.add(edxRuleAlgorothmManagerDT);
				} catch (Exception e) {
					logger.error("HL7ELRValidateDecisionSupport.createAlgorithmMap failure for dSMAlgorithmDT:" + dSMAlgorithmDT.getAlgorithmNm());
					throw new NEDSSAppException("HL7ELRValidateDecisionSupport.createAlgorithmMap failure for dSMAlgorithmDT:" + e);
				}
			}
		}
		return returnColl;
	}
	
	
	
	public Collection<Object> selectDSMAlgorithmDTCollection(NBSSecurityObj nbsSecurityObj) throws
	   EJBException, RemoteException
	  {
	  ArrayList<Object> algorithmList = new ArrayList<Object> ();
	  boolean check = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.DECISION_SUPPORT_ADMIN);

	  if (check == false)
	  {
	   throw new NEDSSSystemException(
	       "HL7ELRValidateDecisionSupport.selectDSMAlgorithmDTCollection Sorry, you don't have permission to select a Decision Support Mgr Algorithm Record List by Condition!");
	  }
	  
	  try 
	  {
	  	DSMAlgorithmDaoImpl algorithmDao = new DSMAlgorithmDaoImpl();
	  	algorithmList = (ArrayList<Object> ) algorithmDao.selectDsmAlgorithmDTCollection();
	  	} catch(NEDSSSystemException se2) {
	  		String errorString = "HL7ELRValidateDecisionSupport.selectDSMAlgorithmDTCollection System Exception:  "+ se2.getMessage();
	  		logger.error(errorString);
	  		throw new EJBException(errorString, se2);
	  }

	  	catch(Exception e) {
	  		String errorString = "HL7ELRValidateDecisionSupport.selectDSMAlgorithmDTCollection General Exception:  "+ e.getMessage();
	  		logger.error(errorString);
	  		throw new EJBException(errorString, e);
	  }
	  return algorithmList; //return all recs
	  } //selectDSMAlgorithmDTCollection
	
	@SuppressWarnings("deprecation")
	private EdxLabInformationDT parseDSMAlgorithmDT(DSMAlgorithmDT dSMAlgorithmDT) throws Exception {
		EdxLabInformationDT edxRuleAlgorothmManagerDT;
		try {
			edxRuleAlgorothmManagerDT = new EdxLabInformationDT();
			edxRuleAlgorothmManagerDT.setLastChgTime(dSMAlgorithmDT.getLastChgTime());
			edxRuleAlgorothmManagerDT.setDsmAlgorithmUid(dSMAlgorithmDT.getDsmAlgorithmUid());
			String payloadContent = dSMAlgorithmDT.getAlgorithmPayload();
			AlgorithmDocument algorithmDocument = ValidateDecisionSupport.parseAlgorithmDocumentTypeXml(payloadContent);
			Map<Object, Object> advanceCriteriaMap = new HashMap<Object, Object>();
			Algorithm algorithm = algorithmDocument.getAlgorithm();
			edxRuleAlgorothmManagerDT.setDsmAlgorithmName(algorithm.getAlgorithmName());
			if(algorithm.getAction()!=null && algorithm.getAction().getMarkAsReviewed()!=null)
				edxRuleAlgorothmManagerDT.setAction(DecisionSupportConstants.MARK_AS_REVIEWED);
			else if(algorithm.getAction()!=null && algorithm.getAction().getCreateInvestigation()!=null)
				edxRuleAlgorothmManagerDT.setAction(DecisionSupportConstants.CREATE_INVESTIGATION_VALUE);
			else if(algorithm.getAction()!=null && algorithm.getAction().getCreateInvestigationWithNND()!=null)
				edxRuleAlgorothmManagerDT.setAction(DecisionSupportConstants.CREATE_INVESTIGATION_WITH_NND_VALUE);
			if (algorithm.getApplyToSendingSystems() != null && algorithm.getApplyToSendingSystems().getSendingSystemArray() != null) {
				CodedType[] codedArray = algorithm.getApplyToSendingSystems().getSendingSystemArray();
				if (codedArray != null && codedArray.length > 0) {
					Collection<Object> coll = new ArrayList<Object>();
					for (int j = 0; j < codedArray.length; j++) {
						ExportReceivingFacilityDT exportReceivingFacilityDT = new ExportReceivingFacilityDT();
						CodedType codedType = codedArray[j];
						exportReceivingFacilityDT.setReceivingSystemDescTxt(codedType.getCode());
						exportReceivingFacilityDT.setReceivingSystemOid(codedType.getCodeSystemCode());
						coll.add(exportReceivingFacilityDT);
					}
					edxRuleAlgorothmManagerDT.setSendingFacilityColl(coll);
				}
			}
			ElrAdvancedCriteriaType advanceCriteriaType = algorithm.getElrAdvancedCriteria();
			
			if (advanceCriteriaType != null) {
				//Set And/Or Logic indicator if present
				if (advanceCriteriaType.getAndOrLogic() == null)
					edxRuleAlgorothmManagerDT.setAlgorithmAndOrLogic(DecisionSupportConstants.AND_AND_OR_LOGIC);
				else
					edxRuleAlgorothmManagerDT.setAlgorithmAndOrLogic(advanceCriteriaType.getAndOrLogic());
				
				for (int i = 0; i < advanceCriteriaType.getElrCriteriaArray().length; i++) {
					ElrCriteriaType criteriaType = advanceCriteriaType.getElrCriteriaArray()[i];
					
					CodedType criteriaResultQuestionType = criteriaType.getResultedTest();
					if(criteriaResultQuestionType==null || criteriaResultQuestionType.getCode()==null)
						throw new NEDSSAppException("HL7ELRValidateDecisionSupport.parseDSMAlgorithmDT Resulted Test for the algorithm is null. Please check the algorithm with algorithm Name:-"+dSMAlgorithmDT.getAlgorithmNm());
					
					if(criteriaType.getElrCodedResultValue()!=null){
						CodedType criteriaCodedType = criteriaType.getElrCodedResultValue();
						EdxRuleManageDT edxRuleManageDT = new EdxRuleManageDT();
						edxRuleManageDT.setDsmAlgorithmUid(dSMAlgorithmDT.getDsmAlgorithmUid());
						//edxRuleManageDT.setQuestionId(criteriaCodedType.getCode());
						edxRuleManageDT.setLogic(criteriaCodedType.getCode());
						edxRuleManageDT.setAdvanceCriteria(true);
						edxRuleManageDT.setType(EdxELRConstants.ELR_CODED_TYPE);
						edxRuleManageDT.setValue(criteriaCodedType.getCode());
						if(advanceCriteriaMap.get(criteriaType.getResultedTest().getCode())!=null)
							throw new NEDSSAppException("HL7ELRValidateDecisionSupport.parseDSMAlgorithmDT Algorithm contains  two or more Resulted Test for the same algorithm. Please check the algorithm with algorithm Name:-"+dSMAlgorithmDT.getAlgorithmNm());
						advanceCriteriaMap.put(criteriaResultQuestionType.getCode() +criteriaCodedType.getCode(), edxRuleManageDT);
						
					}  
					if(criteriaType.getElrNumericResultValue()!=null){
					ElrNumericType criteriaNumericType = criteriaType.getElrNumericResultValue();
					EdxRuleManageDT edxRuleManageDT = new EdxRuleManageDT();
					edxRuleManageDT.setDsmAlgorithmUid(dSMAlgorithmDT.getDsmAlgorithmUid());
					edxRuleManageDT.setAdvanceCriteria(true);
					edxRuleManageDT.setType(EdxELRConstants.ELR_NUMERIC_TYPE);

					StringBuffer logicCriteria=new StringBuffer("");
					if(criteriaNumericType.getComparatorCode()!=null)
						logicCriteria.append(criteriaNumericType.getComparatorCode());
					if(criteriaNumericType.getValue1()!=null)
						logicCriteria.append(criteriaNumericType.getValue1());
					if(criteriaNumericType.getSeperatorCode()!=null)
						logicCriteria.append(criteriaNumericType.getSeperatorCode());
					if(criteriaNumericType.getValue2()!=null)
						logicCriteria.append(criteriaNumericType.getValue2());
					if(criteriaNumericType.getUnit()!=null)
						logicCriteria.append(criteriaNumericType.getUnit().getCode());

					edxRuleManageDT.setValue(logicCriteria.toString());
					if(advanceCriteriaMap.get(criteriaType.getResultedTest().getCode())!=null)
						throw new NEDSSAppException("HL7ELRValidateDecisionSupport.parseDSMAlgorithmDT Algorithm contains  two or more Resulted Test for the same algorithm. Please check the algorithm with algorithm Name:-"+dSMAlgorithmDT.getAlgorithmNm());
					advanceCriteriaMap.put(criteriaResultQuestionType.getCode() +logicCriteria, edxRuleManageDT);
					}
					if(criteriaType.getElrTextResultValue()!=null && criteriaType.getElrTextResultValue().getTextValue()!=null){
						String criteriaTextType = criteriaType.getElrTextResultValue().getTextValue();
						EdxRuleManageDT edxRuleManageDT = new EdxRuleManageDT();
						edxRuleManageDT.setDsmAlgorithmUid(dSMAlgorithmDT.getDsmAlgorithmUid());
						edxRuleManageDT.setType(EdxELRConstants.ELR_STRING_TYPE);
						//edxRuleManageDT.setQuestionId(criteriaCodedType.getCode());
						///edxRuleManageDT.setLogic(criteriaCodedType.getCode());
						edxRuleManageDT.setAdvanceCriteria(true);
						edxRuleManageDT.setValue(criteriaTextType);
						if(advanceCriteriaMap.get(criteriaType.getResultedTest().getCode())!=null)
							throw new NEDSSAppException("HL7ELRValidateDecisionSupport.parseDSMAlgorithmDT Algorithm contains  two or more Resulted Test for the same algorithm. Please check the algorithm with algorithm Name:-"+dSMAlgorithmDT.getAlgorithmNm());
						advanceCriteriaMap.put(criteriaResultQuestionType.getCode() +criteriaTextType, edxRuleManageDT);
						
					}

				}
			}
			edxRuleAlgorothmManagerDT.setEdxRuleAdvCriteriaDTMap(advanceCriteriaMap);
			processAction(edxRuleAlgorothmManagerDT,algorithm);
			if(algorithm.getApplyToConditions()!=null){
				CodedType[] conditionArray=algorithm.getApplyToConditions().getConditionArray();
				for(int i=0; i<conditionArray.length; i++){
					CodedType codeType =(CodedType)conditionArray[i];
					edxRuleAlgorothmManagerDT.setConditionCode(codeType.getCode());
				}
			}
		} catch (Exception e) {
			logger.error("HL7ELRValidateDecisionSupport.parseDSMAlgorithmDT: exception caught. "+e);
			throw new NEDSSAppException("HL7ELRValidateDecisionSupport.parseDSMAlgorithmDT: exception caught. "+e);
		}
		
		return edxRuleAlgorothmManagerDT;
	}
	private void processAction(EdxLabInformationDT edxRuleAlgorothmManagerDT, Algorithm algorithm) throws NEDSSAppException {
		//applicationMap 
		Map<Object, Object> applicationMap= new HashMap<Object, Object>();
		try {
			ActionType actionType = algorithm.getAction();
			if (actionType.getCreateInvestigation() != null) {
				CreateInvestigationType specificActionType = actionType.getCreateInvestigation();
				edxRuleAlgorothmManagerDT.setAction(DecisionSupportConstants.CREATE_INVESTIGATION_VALUE);
				InvestigationDefaultValuesType investigationDefaultValuesType = specificActionType.getInvestigationDefaultValues();
				if (investigationDefaultValuesType != null)
					ValidateDecisionSupport.parseInvestigationDefaultValuesType(applicationMap, investigationDefaultValuesType);

				CodedType failureToCreateType = specificActionType.getOnFailureToCreateInvestigation();
				edxRuleAlgorothmManagerDT.setOnFailureToCreateInv(failureToCreateType.getCode());
				if (specificActionType.getUpdateAction() != null)
					edxRuleAlgorothmManagerDT.setUpdateAction(specificActionType.getUpdateAction().getCode());
			} else if (actionType.getCreateInvestigationWithNND() != null) {
				CreateInvestigationWithNNDType specificActionType = actionType.getCreateInvestigationWithNND();
				InvestigationDefaultValuesType investigationDefaultValuesType = specificActionType.getInvestigationDefaultValues();
				edxRuleAlgorothmManagerDT.setAction(DecisionSupportConstants.CREATE_INVESTIGATION_WITH_NND_VALUE);
				if (investigationDefaultValuesType != null)
					ValidateDecisionSupport.parseInvestigationDefaultValuesType(applicationMap, investigationDefaultValuesType);
				CodedType failureToCreateType = specificActionType.getOnFailureToCreateInvestigation();
				edxRuleAlgorothmManagerDT.setOnFailureToCreateInv(failureToCreateType.getCode());
				if (specificActionType.getUpdateAction() != null)
					edxRuleAlgorothmManagerDT.setUpdateAction(specificActionType.getUpdateAction().getCode());
				edxRuleAlgorothmManagerDT.setNndComment(specificActionType.getNNDComment());
				if (specificActionType.getOnFailureToCreateNND() != null)
					edxRuleAlgorothmManagerDT.setOnFailureToCreateNND(specificActionType.getOnFailureToCreateNND().getCode());

			} else if (actionType.getDeleteDocument() != null) {
				DeleteDocumentType specificActionType = actionType.getDeleteDocument();
				specificActionType.getAlert();
				specificActionType.getComment();
				specificActionType.getReasonForDeletion();
			}
			
			if (applicationMap != null && applicationMap.size() > 0)
				edxRuleAlgorothmManagerDT.setEdxRuleApplyDTMap(applicationMap);
			
		} catch (Exception e) {
			logger.error("HL7ELRValidateDecisionSupport.processAction: exception caught. "+e);
			throw new NEDSSAppException("HL7ELRValidateDecisionSupport.processAction: exception caught. "+e);
		}
	}
	
	
	
	
	private AlgorithmDocument parseAlgorithmXml(String xmlPayLoadContent)
			throws Exception {
		AlgorithmDocument algorithmDocument = null;
		try {
			ArrayList<Object> validationErrors = new ArrayList<Object>();
			XmlOptions validationOptions = new XmlOptions();
			validationOptions.setErrorListener(validationErrors);

			algorithmDocument = AlgorithmDocument.Factory.parse(xmlPayLoadContent);

			boolean isValid = algorithmDocument.validate(validationOptions);

			// Print the errors if the XML is invalid.
			if (!isValid) {
				Iterator<Object> iter = validationErrors.iterator();
				StringBuffer buff = new StringBuffer();
				buff.append("HL7ELRValidateDecisionSupport.parseAlgorithmXml Inbound message failed in parseAlgorithmXml.");
				while (iter.hasNext()) {
					buff.append(iter.next() + "\n");
				}
				logger.error(buff.toString());
				logger.error("HL7ELRValidateDecisionSupport.parseAlgorithmXml:-Error thrown as XMl is invalid"+buff.toString());
				throw new NEDSSAppException("HL7ELRValidateDecisionSupport.parseAlgorithmXml Invalid XML "+buff.toString());
			}
		} catch (Exception e) {
			logger.error(e.toString());
			logger.error("HL7ELRValidateDecisionSupport.parseAlgorithmXml:-Error thrown as XMl is invalid"+e);
			throw new NEDSSAppException("HL7ELRValidateDecisionSupport.parseAlgorithmXml Invalid XML "+e);
		}

		return algorithmDocument;
	}
	/*sort PublicHealthCaseDTs by add_time descending*/
	final Comparator<PublicHealthCaseDT> ADDTIME_ORDER = new Comparator<PublicHealthCaseDT>() {
		public int compare(PublicHealthCaseDT e1, PublicHealthCaseDT e2) {
			return e2.getAddTime().compareTo(e1.getAddTime());
		}
	};

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean specimenCollectionDateCriteria(EventDateLogicType eventDateLogicType,EdxLabInformationDT edxLabInformationDT){
		boolean isdateLogicValidForNewInv = false;
		String comparatorCode="";
		int value=0;
		Long associatedPHCUid= new Long(-1);
		Long mprUid=new Long(-1);
		ObservationDAOImpl obsDaoImpl = new ObservationDAOImpl();
		if(edxLabInformationDT.getPersonParentUid()>0)
			mprUid =edxLabInformationDT.getPersonParentUid();
		//see if the selection is no for time period, check for all any investigation with the desired condition code
		if(eventDateLogicType.getElrTimeLogic()!=null && eventDateLogicType.getElrTimeLogic().getElrTimeLogicInd()!=null && eventDateLogicType.getElrTimeLogic().getElrTimeLogicInd().getCode()!=null &&
				eventDateLogicType.getElrTimeLogic().getElrTimeLogicInd().getCode().equals(NEDSSConstants.NO)){
			edxLabInformationDT.setMatchingPublicHealthCaseDTColl( obsDaoImpl.associatedPublicHealthCaseForMprForCondCd(mprUid, edxLabInformationDT.getConditionCode()));
		}
		//see if the selection is yes for time period, check for all any investigation with the desired condition code
		else if(eventDateLogicType.getElrTimeLogic()!=null && eventDateLogicType.getElrTimeLogic().getElrTimeLogicInd()!=null && eventDateLogicType.getElrTimeLogic().getElrTimeLogicInd().getCode()!=null &&
				eventDateLogicType.getElrTimeLogic().getElrTimeLogicInd().getCode().equals(NEDSSConstants.YES))
		{
		if(eventDateLogicType.getWithinTimePeriod()!=null && eventDateLogicType.getWithinTimePeriod().getComparatorCode()!=null && 
				eventDateLogicType.getWithinTimePeriod().getComparatorCode().getCode()!=null){
			comparatorCode= eventDateLogicType.getWithinTimePeriod().getComparatorCode().getCode();
		}
		if(eventDateLogicType.getWithinTimePeriod()!=null && eventDateLogicType.getWithinTimePeriod().getUnit()!=null && 
				eventDateLogicType.getWithinTimePeriod().getValue1()!=null){
			value=eventDateLogicType.getWithinTimePeriod().getValue1().intValue();
		}
		
		Timestamp specimenCollectionDate=new Timestamp(edxLabInformationDT.getRootObservationVO().getTheObservationDT().getEffectiveFromTime().getTime());
		Long specimenCollectionDays = specimenCollectionDate.getTime()/(1000 * 60 * 60 * 24);
		logger.debug("HL7ELRValidateDecisionSupport.specimenCollectionDateCriteria initial specimenCollectionDate:" + specimenCollectionDate);

		logger.debug("HL7ELRValidateDecisionSupport.specimenCollectionDateCriteria comparatorCode:" + comparatorCode+"\nvalue:"+value+"\nmprUid"+mprUid);
		logger.debug("HL7ELRValidateDecisionSupport.specimenCollectionDateCriteria updated specimenCollectionDate:" + specimenCollectionDate);
		
		if(specimenCollectionDate!=null && comparatorCode.length()>0  && mprUid>0){
			Collection<Object> associatedPhcDTCollection = obsDaoImpl.associatedPublicHealthCaseForMprForCondCd(mprUid, edxLabInformationDT.getConditionCode());
			
			if(associatedPhcDTCollection!=null && associatedPhcDTCollection.size()>0){
				Iterator<Object> it = associatedPhcDTCollection.iterator();
				while(it.hasNext()){
					boolean isdateLogicValidWithThisInv = true;
					PublicHealthCaseDT phcDT = (PublicHealthCaseDT)it.next();
					Long dateCompare=null;
					if( phcDT.getAssociatedSpecimenCollDate()!=null){
						logger.debug("HL7ELRValidateDecisionSupport.specimenCollectionDateCriteria associated specimen collection date for this PHC is not null. AddTime logic :"+phcDT.getAssociatedSpecimenCollDate());
						dateCompare= phcDT.getAssociatedSpecimenCollDate().getTime()/(1000 * 60 * 60 * 24);
					}else{
						logger.debug("HL7ELRValidateDecisionSupport.specimenCollectionDateCriteria associated specimen collection date for this PHC is  AddTime logic:"+phcDT.getAddTime());
						dateCompare= phcDT.getAddTime().getTime()/(1000 * 60 * 60 * 24);
						phcDT.setAssociatedSpecimenCollDate(phcDT.getAddTime());
					}
					int daysDifference=	specimenCollectionDays.intValue()-dateCompare.intValue();	
						
					logger.debug(phcDT.toString());
						if(phcDT.getAssociatedSpecimenCollDate()!=null){
							if(comparatorCode.contains( NEDSSConstants.LESS_THAN_LOGIC) && daysDifference>value){
								isdateLogicValidWithThisInv= false;
								logger.debug("HL7ELRValidateDecisionSupport.specimenCollectionDateCriteria matched PHC for LESS_THAN_LOGIC, associated phclab details are"+phcDT.toString());
							}else if(comparatorCode.contains( NEDSSConstants.GREATER_THAN_LOGIC) && daysDifference<value){
								isdateLogicValidWithThisInv= false;
								logger.debug("HL7ELRValidateDecisionSupport.specimenCollectionDateCriteria matched PHC for GREATER_THAN_LOGIC, associated phclab details are"+phcDT.toString());
							}else if(comparatorCode.equals( NEDSSConstants.EQUAL_LOGIC) && daysDifference!=value){
								isdateLogicValidWithThisInv= false;
								logger.debug("HL7ELRValidateDe7cisionSupport.specimenCollectionDateCriteria matched PHC for EQUAL_LOGIC, associated phclab details are"+phcDT.toString());
							}
							else if(!comparatorCode.contains(NEDSSConstants.EQUAL_LOGIC) && daysDifference==value){
								isdateLogicValidWithThisInv= false;
								logger.debug("HL7ELRValidateDe7cisionSupport.specimenCollectionDateCriteria matched OPE PHC for less than or greater than, and values are same,  associated phclab details are"+phcDT.toString());
							}
						}
						if(isdateLogicValidWithThisInv){
							logger.debug("HL7ELRValidateDecisionSupport.specimenCollectionDateCriteria for INVESTIGATION_STATUS_CODE_CLOSED MAYBE Potential match found :"+phcDT.getAssociatedSpecimenCollDate());
							if(edxLabInformationDT.getMatchingPublicHealthCaseDTColl()==null)
								edxLabInformationDT.setMatchingPublicHealthCaseDTColl(new ArrayList<Object>());
							edxLabInformationDT.getMatchingPublicHealthCaseDTColl().add(phcDT);
					}
				}
			logger.debug("HL7ELRValidateDecisionSupport.specimenCollectionDateCriteria isdateLogicValid:"+isdateLogicValidForNewInv);
			}else{
			logger.debug("HL7ELRValidateDecisionSupport.specimenCollectionDateCriteria There was not match criteria defined for the date logic. Hence returning true so that next step of algorithm match can be processed");
			isdateLogicValidForNewInv= true;
			}
		}
		}
		if (edxLabInformationDT.getMatchingPublicHealthCaseDTColl() != null
				&& edxLabInformationDT.getMatchingPublicHealthCaseDTColl()
						.size() > 0){
			List phclist = new ArrayList<Object>(edxLabInformationDT.getMatchingPublicHealthCaseDTColl());		
			Collections.sort(phclist, ADDTIME_ORDER);
			associatedPHCUid = 	((PublicHealthCaseDT)phclist.get(0)).getPublicHealthCaseUid();
			isdateLogicValidForNewInv= false;
		}
		else
			isdateLogicValidForNewInv= true;
		edxLabInformationDT.setAssociatedPublicHealthCaseUid(associatedPHCUid);
		return isdateLogicValidForNewInv;
	}
	
	private boolean checkAdvancedInvCriteria(AlgorithmDocument algorithmDocument, EdxLabInformationDT edxLabInformationDT, Map<Object, Object> questionIdentifierMap) throws NEDSSAppException{
		boolean isAdvancedInvCriteriaMet = false;
		
		try{
		
		Map<String, Object> advanceInvCriteriaMap = getAdvancedInvCriteriaMap(algorithmDocument);
		/*
		 * return match as true if there is no investigation is compare and
		 * advanceInvCriteriaMap is empty
		 */
		if ((edxLabInformationDT.getMatchingPublicHealthCaseDTColl() == null || edxLabInformationDT
				.getMatchingPublicHealthCaseDTColl().size() == 0)
				&& advanceInvCriteriaMap == null
				|| advanceInvCriteriaMap.size() == 0)
			isAdvancedInvCriteriaMet = true;
		
		/*
		 * for each matched PHC see if the advanced criteria matches. for mark
		 * as reviewed, if the match is found break and use that PHC for
		 * association and mark the lab as reviewed. for create Investigation
		 * and/or Notification actions to succeed, the advanced criteria should
		 * return false, so that new investigation can be created.
		 */
		if(edxLabInformationDT
				.getMatchingPublicHealthCaseDTColl()!=null && edxLabInformationDT
						.getMatchingPublicHealthCaseDTColl().size()>0){
		for (Object phcDT : edxLabInformationDT
				.getMatchingPublicHealthCaseDTColl()) {

			if (advanceInvCriteriaMap != null
					&& advanceInvCriteriaMap.size() > 0) {
				Set<String> criteriaSet = advanceInvCriteriaMap.keySet();
				Iterator<String> criteriaIterator = criteriaSet.iterator();
				
				while (criteriaIterator.hasNext()) {
					String questionId = criteriaIterator.next();
					Object object = advanceInvCriteriaMap.get(questionId);
					if (object instanceof EdxRuleManageDT) {
						EdxRuleManageDT edxRuleManageDT = (EdxRuleManageDT) object;
						NbsQuestionMetadata criteriaMetaData = (NbsQuestionMetadata) questionIdentifierMap
								.get(questionId);
						if (criteriaMetaData != null)
							isAdvancedInvCriteriaMet = ValidateDecisionSupport
									.checkNbsObject(edxRuleManageDT,
											(PublicHealthCaseDT) phcDT,
											criteriaMetaData);
						if (!isAdvancedInvCriteriaMet)
							break;
					} else if (object instanceof Collection<?>) {
						Collection<?> collection = (ArrayList<?>) object;
						Iterator<?> iter = collection.iterator();
						while (iter.hasNext()) {
							EdxRuleManageDT edxRuleManageDT = (EdxRuleManageDT) iter
									.next();
							NbsQuestionMetadata criteriaMetaData = (NbsQuestionMetadata) questionIdentifierMap
									.get(questionId);
							if (criteriaMetaData != null)
								isAdvancedInvCriteriaMet = ValidateDecisionSupport
										.checkNbsObject(edxRuleManageDT,
												(PublicHealthCaseDT) phcDT,
												criteriaMetaData);
							if (!isAdvancedInvCriteriaMet)
								break;
						}
					}
				}
				/*
				 * If one of the investigation matches break and get out of the
				 * loop
				 */
				if (isAdvancedInvCriteriaMet){
					edxLabInformationDT.setAssociatedPublicHealthCaseUid(((PublicHealthCaseDT)phcDT).getPublicHealthCaseUid());
					break;
				}
			} else {// There is no advanced investigation criteria selected to be matched
				isAdvancedInvCriteriaMet = true;
			}
		}
		}
		/*
		 * if the advanced criteria is not met reset the associated
		 * PublicHealthCaseUid to -1, it might have been set by time criteria
		 */
		if(!isAdvancedInvCriteriaMet && edxLabInformationDT
				.getMatchingPublicHealthCaseDTColl()!=null && edxLabInformationDT
				.getMatchingPublicHealthCaseDTColl().size()>0)
			edxLabInformationDT.setAssociatedPublicHealthCaseUid(new Long(-1));
		}catch(Exception ex){
			throw new NEDSSAppException ("Exception while checking advanced Investigation Criteria for Lab mark as reviewed: ", ex);
		}
		return isAdvancedInvCriteriaMet;
	}
	
	private Map<String, Object> getAdvancedInvCriteriaMap(AlgorithmDocument algorithmDocument) throws NEDSSAppException{
		
		Map<String, Object> advanceInvCriteriaMap = new HashMap<String, Object>();
		try{
		InvCriteriaType advanceInvCriteriaType = algorithmDocument
				.getAlgorithm().getElrAdvancedCriteria().getInvCriteria();
		/* Create the advanced Criteria map to compare against matched PHCs */
		if (advanceInvCriteriaType != null) {
			for (int i = 0; i < advanceInvCriteriaType.getInvValueArray().length; i++) {
				InvValueType criteriaType = advanceInvCriteriaType
						.getInvValueArray()[i];
				CodedType criteriaQuestionType = criteriaType.getInvQuestion();
				CodedType criteriaLogicType = criteriaType
						.getInvQuestionLogic();

				if (criteriaType.getInvStringValue() == null
						&& criteriaType.getInvCodedValueArray().length > 0) {
					String value = null;
					String[] array = new String[criteriaType
												.getInvCodedValueArray().length];
					for (int j = 0; j < criteriaType.getInvCodedValueArray().length; j++) {
						array[j] = criteriaType.getInvCodedValueArray()[j]
								.getCode();
					}
					Arrays.sort(array);
					value = String.join(",", array);
					EdxRuleManageDT edxRuleManageDT = new EdxRuleManageDT();
					edxRuleManageDT.setQuestionId(criteriaQuestionType
							.getCode());
					edxRuleManageDT.setLogic(criteriaLogicType.getCode());
					edxRuleManageDT.setAdvanceCriteria(true);
					edxRuleManageDT.setValue(value);
					advanceInvCriteriaMap.put(criteriaQuestionType.getCode(),
							edxRuleManageDT);

				} else {
					EdxRuleManageDT edxRuleManageDT = new EdxRuleManageDT();
					edxRuleManageDT.setQuestionId(criteriaQuestionType
							.getCode());
					edxRuleManageDT.setLogic(criteriaLogicType.getCode());
					edxRuleManageDT.setAdvanceCriteria(true);
					edxRuleManageDT.setValue(criteriaType.getInvStringValue());
					advanceInvCriteriaMap.put(criteriaQuestionType.getCode(),
							edxRuleManageDT);
				}
			}
		}
	}catch(Exception ex){
		throw new NEDSSAppException ("Exception while creating advanced Investigation Criteria Map: ", ex);
	}
		return advanceInvCriteriaMap;
	}
	
	private boolean checkAdvancedInvCriteriaForCreateInvNoti(
			AlgorithmDocument algorithmDocument,
			EdxLabInformationDT edxLabInformationDT,
			Map<Object, Object> questionIdentifierMap) throws NEDSSAppException {

		try{
		Map<String, Object> advanceInvCriteriaMap = getAdvancedInvCriteriaMap(algorithmDocument);

		/*
		 * return match as true if there is no investigation to compare and
		 * advanceInvCriteriaMap is empty
		 */
		if ((edxLabInformationDT.getMatchingPublicHealthCaseDTColl() == null || edxLabInformationDT
				.getMatchingPublicHealthCaseDTColl().size() == 0)
				&& (advanceInvCriteriaMap == null || advanceInvCriteriaMap
						.size() == 0))
			return true;

		/*
		 * return match as false if there are investigation to compare and
		 * advanceInvCriteriaMap is empty
		 */
		if ((edxLabInformationDT.getMatchingPublicHealthCaseDTColl() != null && edxLabInformationDT
				.getMatchingPublicHealthCaseDTColl().size() > 0)
				&& (advanceInvCriteriaMap == null || advanceInvCriteriaMap
						.size() == 0))
			return false;

		/*
		 * for each PHC see if the advanced criteria matches. the matching will
		 * be exclusive (e.g., if the algorithm says investigation status not
		 * equals open and there is investigation with open status, it should
		 * return true)
		 */
		if (edxLabInformationDT.getMatchingPublicHealthCaseDTColl() != null
				&& edxLabInformationDT.getMatchingPublicHealthCaseDTColl()
						.size() > 0) {
			for (Object phcDT : edxLabInformationDT
					.getMatchingPublicHealthCaseDTColl()) {

				if (advanceInvCriteriaMap != null
						&& advanceInvCriteriaMap.size() > 0) {
					Set<String> criteriaSet = advanceInvCriteriaMap.keySet();
					Iterator<String> criteriaIterator = criteriaSet.iterator();
                   
					boolean isInvCriteriaValidForAllelements = false;

					while (criteriaIterator.hasNext()) {
						
						String questionId = criteriaIterator.next();
						Object object = advanceInvCriteriaMap.get(questionId);
						boolean isAdvancedInvCriteriaMet = false;

							EdxRuleManageDT edxRuleManageDT = (EdxRuleManageDT) object;
							NbsQuestionMetadata criteriaMetaData = (NbsQuestionMetadata) questionIdentifierMap
									.get(questionId);
							if (criteriaMetaData != null)

								isAdvancedInvCriteriaMet = ValidateDecisionSupport
										.checkNbsObject(edxRuleManageDT,
												(PublicHealthCaseDT) phcDT,
												criteriaMetaData);
							
							if (!isAdvancedInvCriteriaMet)
								isInvCriteriaValidForAllelements = true;
					}
					
					if(isInvCriteriaValidForAllelements)
						return false;
				}
			}
			/*
			 * all the investigations were scanned to match the criteria, no
			 * existing investigation found return true to create new
			 * investigation.
			 */
			return true;
		}
		}catch(Exception ex){
			throw new NEDSSAppException ("Exception while checking advanced Investigation Criteria for creating Investigation and/or Notification: ", ex);
		}
		return false;
	}
	
	final Comparator<DSMLabMatchHelper> AlGORITHM_NM_ORDER = new Comparator<DSMLabMatchHelper>() {
		public int compare(DSMLabMatchHelper e1, DSMLabMatchHelper e2) {
			return e1.getAlgorithmNm().compareToIgnoreCase(e2.getAlgorithmNm());
		}
	};

}
