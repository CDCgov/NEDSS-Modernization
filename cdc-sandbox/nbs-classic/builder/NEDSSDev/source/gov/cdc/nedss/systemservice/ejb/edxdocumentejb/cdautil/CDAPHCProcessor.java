package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.phdc.cda.ANY;
import gov.cdc.nedss.phdc.cda.CE;
import gov.cdc.nedss.phdc.cda.ClinicalDocumentDocument1;
import gov.cdc.nedss.phdc.cda.II;
import gov.cdc.nedss.phdc.cda.POCDMT000040Author;
import gov.cdc.nedss.phdc.cda.POCDMT000040Component4;
import gov.cdc.nedss.phdc.cda.POCDMT000040Entry;
import gov.cdc.nedss.phdc.cda.POCDMT000040EntryRelationship;
import gov.cdc.nedss.phdc.cda.POCDMT000040Section;
import gov.cdc.nedss.phdc.cda.ST;
import gov.cdc.nedss.phdc.cda.TS;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxCDAInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPersonEntityProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxRuleConstants;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.CDAXMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.RenderConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJBException;

import org.apache.xmlbeans.XmlObject;
/**
 * CDAPHCProcessor is util class for custom processing of CDA PHCR code. In this class, CDA document is parsed into NBS Objects.
 * @author Maninder Jit 
 * @updateBy: Pradeep Kumar Sharma
 *
 */
public class CDAPHCProcessor {
	public static final String _REQUIRED = "_REQUIRED";
	static final LogUtils logger = new LogUtils(CDAPHCProcessor.class.getName());
	public static Map<Object, Object> beanMethodMap = new HashMap<Object, Object>();
	public static Map<Object, Object> dataLocationToBeanMap = new HashMap<Object, Object>();
	public static String lineSeperator = System.getProperty("line.seperator");
	
	
	public static void initializeProcessor() throws NEDSSAppException{
		try {
			dataLocationToBeanMap.put(RenderConstants.CASE_MANAGEMENT,
					new CaseManagementDT());
			dataLocationToBeanMap.put(RenderConstants.PUBLIC_HEALTH_CASE,
					new PublicHealthCaseDT());
			dataLocationToBeanMap.put(RenderConstants.ACT_ID,
					new ArrayList<Object>());
			dataLocationToBeanMap.put(RenderConstants.PARTICIPANT,
					new HashMap<Object, Object>());
			dataLocationToBeanMap.put(RenderConstants.INTERESTED_PARTIES,
					new HashMap<Object, Object>());
			dataLocationToBeanMap.put(RenderConstants.NBS_CASE_ANSWER,
					new HashMap<Object, Object>());
			dataLocationToBeanMap.put(RenderConstants.REPEATING_NBS_CASE_ANSWER,
					new HashMap<Object, Object>());
			dataLocationToBeanMap.put(RenderConstants.CONFIRMATION_METHOD,
					new ArrayList<Object>());
			dataLocationToBeanMap.put(RenderConstants.COINFECTION_ID,
					"");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NEDSSAppException(" initializeProcessor failed with exception:-"+e.getMessage(), e);
		}
	}

	public static Object createPageActProxyVO(String datamigration, NBSDocumentVO nBSDocumentVO,
			EdxCDAInformationDT informationDT, NBSSecurityObj nbsSecurityObj)
			throws NEDSSAppException {
		Object object = createProxyVO(nBSDocumentVO, nBSDocumentVO
				.getNbsDocumentDT().getCd(), informationDT, nbsSecurityObj);
		return object;
	}

	@SuppressWarnings("unchecked")
	public static Object createProxyVO(NBSDocumentVO nBSDocumentVO,
			String conditionCode, EdxCDAInformationDT informationDT,
			NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		Object obj = null;
		PageActProxyVO pageActProxyVO = new PageActProxyVO();
		Timestamp time = new Timestamp(new Date().getTime());

		int j = 0;

		try {
			PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();
			publicHealthCaseVO
					.setThePublicHealthCaseDT((PublicHealthCaseDT) dataLocationToBeanMap
							.get(RenderConstants.PUBLIC_HEALTH_CASE));
			publicHealthCaseVO.getThePublicHealthCaseDT()
					.setPublicHealthCaseUid(new Long(--j));
			publicHealthCaseVO.getThePublicHealthCaseDT().setJurisdictionCd(
					nBSDocumentVO.getNbsDocumentDT().getJurisdictionCd());
			publicHealthCaseVO.getThePublicHealthCaseDT().setAddTime(
					nBSDocumentVO.getNbsDocumentDT().getAddTime());
			publicHealthCaseVO.getThePublicHealthCaseDT().setLastChgTime(
					nBSDocumentVO.getNbsDocumentDT().getAddTime());
			publicHealthCaseVO.getThePublicHealthCaseDT().setAddUserId(
					nBSDocumentVO.getNbsDocumentDT().getAddUserId());
			publicHealthCaseVO.getThePublicHealthCaseDT().setLastChgUserId(
					nBSDocumentVO.getNbsDocumentDT().getAddUserId());
			publicHealthCaseVO.getThePublicHealthCaseDT().setRecordStatusTime(
					nBSDocumentVO.getNbsDocumentDT().getAddTime());
			publicHealthCaseVO.getThePublicHealthCaseDT().setSharedInd(
					NEDSSConstants.TRUE);
			publicHealthCaseVO.getThePublicHealthCaseDT().setGroupCaseCnt(
					new Integer(1));
			publicHealthCaseVO.setItNew(true);
			publicHealthCaseVO.setItDirty(false);
			publicHealthCaseVO.getThePublicHealthCaseDT().setItNew(true);
			publicHealthCaseVO.getThePublicHealthCaseDT().setItDirty(false);
			publicHealthCaseVO.getThePublicHealthCaseDT()
					.setInvestigationStatusCd(NEDSSConstants.STATUS_OPEN);
			publicHealthCaseVO.getThePublicHealthCaseDT().setActivityFromTime(
					time);
			publicHealthCaseVO.getThePublicHealthCaseDT().setCaseTypeCd(NEDSSConstants.I);
			
			
			//set the sending facility
			
			if(nBSDocumentVO.getNbsDocumentDT().getSendingFacilityNm()!=null){
				ExportReceivingFacilityDT exportReceivingFacilityDT  = new ExportReceivingFacilityDT();
				exportReceivingFacilityDT.setReceivingSystemDescTxt(nBSDocumentVO.getNbsDocumentDT().getSendingFacilityNm());
				exportReceivingFacilityDT.setReceivingSystemOid(nBSDocumentVO.getNbsDocumentDT().getSendingFacilityOID());
				exportReceivingFacilityDT.setReceivingIndCd(NEDSSConstants.DEFAULT);
				if(pageActProxyVO!=null)
					pageActProxyVO.setExportReceivingFacilityDT(exportReceivingFacilityDT);
			}

			ClinicalDocumentDocument1 rootDoc = (ClinicalDocumentDocument1) nBSDocumentVO
					.getNbsDocumentDT().getDocumentObject();
			XmlObject[] sections = rootDoc.getClinicalDocument().selectPath(EdxCDAConstants.CDA_NAMESPACE
					 + EdxCDAConstants.CDA_STRUCTURED_XML_SECTION);
			String sourceCaseId = null;
			HashMap<Object, Object> legacyToNBSEntityUidMap = new HashMap<Object, Object>();
			POCDMT000040Section repeatingQuestionsSection = null;
			Map<Object, Object> questionMap = loadQuestions(nBSDocumentVO.getNbsDocumentDT()
					.getCd());
			if (sections != null && sections.length > 0) {
				for (XmlObject section : sections) {
					if (((POCDMT000040Section) section).getCode() != null // for section containing the case data
							&& ((POCDMT000040Section) section).getCode()
									.getCode() != null
							&& ((POCDMT000040Section) section).getCode()
									.getCode().equals(EdxCDAConstants.EPI_INFO_CD)) {
						POCDMT000040Entry[] entries = ((POCDMT000040Section) section)
								.getEntryArray();
						II sourceCaseId1 = ((POCDMT000040Section) section)
								.getId();
						if (sourceCaseId1 != null)
							sourceCaseId = sourceCaseId1.getExtension();
						populatePHCValuesFromEntries(questionMap, entries,
								publicHealthCaseVO.getThePublicHealthCaseDT(),  nbsSecurityObj);
					}else if(((POCDMT000040Section) section).getCode() != null// for section containing the interested parties(Entities related to case)
							&& ((POCDMT000040Section) section).getCode()
									.getCode() != null
							&& ((POCDMT000040Section) section).getCode()
									.getCode().equals(EdxCDAConstants.INTERESTED_PARTY_CD)){
						CDAEntityProcessor.populateEntitiesFromEntries(((POCDMT000040Section) section)
								.getEntryArray(), (HashMap<Object, Object>)dataLocationToBeanMap.get(RenderConstants.INTERESTED_PARTIES));
					}
					else if (((POCDMT000040Section) section).getCode() != null// for section containing the Signs and Symptoms(Repeating Section)
							&& ((POCDMT000040Section) section).getCode()
									.getCode() != null
							&& ((POCDMT000040Section) section).getCode()
									.getCode()
									.equals(EdxCDAConstants.SIGNS_SYMPTOMS_CD)) {
						POCDMT000040Entry[] entries = ((POCDMT000040Section) section)
								.getEntryArray();
						populateSignsandSymptomsFromEntries(questionMap , entries,
								publicHealthCaseVO.getThePublicHealthCaseDT(),
								nbsSecurityObj);
					}
					
					else if (((POCDMT000040Section) section).getCode() != null// for section containing the STD History(Repeating Section) 
							&& ((POCDMT000040Section) section).getCode()
									.getCode() != null
							&& ((POCDMT000040Section) section).getCode()
									.getCode()
									.equals(EdxCDAConstants.STD_HISTORY_CD)) {
						POCDMT000040Entry[] entries = ((POCDMT000040Section) section)
								.getEntryArray();
						populateSTDHistoryFromEntries(questionMap, entries,
								publicHealthCaseVO.getThePublicHealthCaseDT(),
								nbsSecurityObj);
					}
					
					else if (((POCDMT000040Section) section).getCode() != null// for section containing the generic repeating entries
							&& ((POCDMT000040Section) section).getCode()
									.getCode() != null
							&& ((POCDMT000040Section) section).getCode()
									.getCode()
									.equals(EdxCDAConstants.REPEATING_QUESTIONS_CD)) {
						repeatingQuestionsSection = (POCDMT000040Section) section;
						updateParticipantsFromRepeatingQuestions((POCDMT000040Section) section);
					}
				}			
			}
			
			if (sourceCaseId != null) {
				ArrayList<Object> ids = new ArrayList<Object>();
				ids.add(sourceCaseId);
				publicHealthCaseVO
						.setEdxEventProcessDTCollection(XMLTypeToNBSObject
								.prepareEventProcessListForNonDocEvents(ids, informationDT,
										NEDSSConstants.CASE));
			}

			TreeMap<Object, Object> condAndFormCdTreeMap = CachedDropDowns
					.getConditionCdAndInvFormCd();
			if (condAndFormCdTreeMap.get(conditionCode) == null) {
				throw new NEDSSAppException(
						"The Condition code mapping to Investigation does not exist: Please check the existence of "
								+ "condition code :"
								+ conditionCode
								+ ": in the nbs_srte..condition_code.");
			} else {
				ProgramAreaVO programAreaVO = CachedDropDowns
						.getProgramAreaForCondition(conditionCode);
				publicHealthCaseVO.getThePublicHealthCaseDT().setCd(
						conditionCode);
				publicHealthCaseVO.getThePublicHealthCaseDT().setCdDescTxt(
						programAreaVO.getConditionShortNm());
				if (!(nBSDocumentVO.getNbsDocumentDT().getProgAreaCd() == null)
						&& !(nBSDocumentVO.getNbsDocumentDT()
								.getJurisdictionCd() == null)) {
					String progAreaCd = nBSDocumentVO.getNbsDocumentDT()
							.getProgAreaCd();
					publicHealthCaseVO.getThePublicHealthCaseDT()
							.setProgAreaCd(progAreaCd);
					String jurisdictionCd = nBSDocumentVO.getNbsDocumentDT()
							.getJurisdictionCd();
					long pajHash = ProgramAreaJurisdictionUtil.getPAJHash(
							progAreaCd, jurisdictionCd);
					Long aProgramJurisdictionOid = new Long(pajHash);
					publicHealthCaseVO.getThePublicHealthCaseDT()
							.setProgramJurisdictionOid(aProgramJurisdictionOid);
					publicHealthCaseVO.getThePublicHealthCaseDT()
							.setJurisdictionCd(jurisdictionCd);
				}
			}
			// publicHealthCaseVO.setTheConfirmationMethodDTCollection(new
			// ArrayList()aConfirmationMethodDTCollection);
			CaseManagementDT cmDT = (CaseManagementDT) dataLocationToBeanMap
					.get(RenderConstants.CASE_MANAGEMENT);
			
			/*
			if (cmDT != null && cmDT.getInitFollUp() != null) {
				cmDT.setCaseManagementDTPopulated(true);
				publicHealthCaseVO.setTheCaseManagementDT(cmDT);
			}*/
			
			
			try{
				boolean isSTDProgramArea = PropertyUtil.isStdOrHivProgramArea(publicHealthCaseVO.getThePublicHealthCaseDT().getProgAreaCd());
				if (cmDT!= null && isSTDProgramArea) {
					cmDT.setPublicHealthCaseUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
					cmDT.setCaseManagementDTPopulated(true);
					publicHealthCaseVO.setTheCaseManagementDT(cmDT);
				}
			} catch(Exception ex){
					logger.error("Exception setting CaseManagementDT to PHC = "+ex.getMessage(), ex);
					throw new NEDSSSystemException("Unexpected exception setting CaseManagementDT to PHC -->" +ex.toString());
			}
			
			
			if(publicHealthCaseVO.getThePublicHealthCaseDT().getCd() != null)
				publicHealthCaseVO.setCoinfectionCondition(CachedDropDowns.getConditionCoinfectionMap().containsKey(publicHealthCaseVO.getThePublicHealthCaseDT().getCd())? true:false);
			
			
			 pageActProxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
			PersonVO patientVO = CDAEntityProcessor.getPatient(rootDoc
					.getClinicalDocument().getRecordTargetArray(),
					informationDT, CDAXMLTypeToNBSObject.getSocialHistorySection(rootDoc.getClinicalDocument()));
			patientVO.setMPRUpdateValid(false);
			EdxPersonEntityProcessor.setActEntityForCreate(pageActProxyVO,
					null, NEDSSConstants.PHC_PATIENT, patientVO
							.getThePersonDT().getPersonUid(),
					NEDSSConstants.PERSON);
			patientVO.getThePersonDT().setPersonParentUid(
					nBSDocumentVO.getPatientVO().getThePersonDT()
							.getPersonParentUid());
			if (pageActProxyVO != null) {
				if (pageActProxyVO.getThePersonVOCollection() == null
						|| (pageActProxyVO.getThePersonVOCollection() != null && pageActProxyVO
								.getThePersonVOCollection().size() == 0)) {
					Collection<Object> personColl = new ArrayList<Object>();
					personColl.add(patientVO);
					pageActProxyVO.setThePersonVOCollection(personColl);
				} else {
					pageActProxyVO.getThePersonVOCollection().add(patientVO);
				}
				pageActProxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
				
				publicHealthCaseVO
						.setTheActIdDTCollection((ArrayList<Object>) dataLocationToBeanMap
								.get(RenderConstants.ACT_ID));
				
				//NBS requires act ids to be in the ACT_ID table even if id is blank, so that edit works. This method is to handle that scenario.
				updateActIdCollection(publicHealthCaseVO, questionMap);
				
				publicHealthCaseVO
				.setTheConfirmationMethodDTCollection((ArrayList<Object>) dataLocationToBeanMap
						.get(RenderConstants.CONFIRMATION_METHOD));
				publicHealthCaseVO.getThePublicHealthCaseDT().setCoinfectionId((String)dataLocationToBeanMap
						.get(RenderConstants.COINFECTION_ID));
				if(publicHealthCaseVO.getThePublicHealthCaseDT().getCoinfectionId()!=null && publicHealthCaseVO.getThePublicHealthCaseDT().getCoinfectionId().trim().equals(""))
					publicHealthCaseVO.getThePublicHealthCaseDT().setCoinfectionId(null);
				pageActProxyVO.setItNew(true);
				pageActProxyVO.setItDirty(false);
				obj = pageActProxyVO;
			}
			createActRelationshipForDoc(nBSDocumentVO.getNbsDocumentDT()
					.getNbsDocumentUid(), publicHealthCaseVO);
			
			legacyToNBSEntityUidMap = CDAEntityProcessor.createActEntityandParticipations(pageActProxyVO,
					(HashMap<Object, Object>) dataLocationToBeanMap
							.get(RenderConstants.PARTICIPANT),
					(HashMap<Object, Object>) dataLocationToBeanMap
							.get(RenderConstants.INTERESTED_PARTIES), informationDT,NEDSSConstants.CASE,
					nbsSecurityObj);
			//need to process the repeating section in the last
			if(repeatingQuestionsSection!=null){
				POCDMT000040Entry[] entries = ((POCDMT000040Section) repeatingQuestionsSection)
						.getEntryArray();
				populateGenericRepeatingQuestionsFromEntries(questionMap, entries,
						publicHealthCaseVO.getThePublicHealthCaseDT(), legacyToNBSEntityUidMap,
						nbsSecurityObj);
			}

			pageActProxyVO.getPageVO().setPamAnswerDTMap(
					(HashMap<Object, Object>) dataLocationToBeanMap
							.get(RenderConstants.NBS_CASE_ANSWER));
			
			pageActProxyVO.getPageVO().setPageRepeatingAnswerDTMap(
					(HashMap<Object, Object>) dataLocationToBeanMap
							.get(RenderConstants.REPEATING_NBS_CASE_ANSWER));
			
		}catch (NEDSSAppException e) {
			String errorString = "Application Exception while creating pageProxyVO from PHDC massage. "+e.getMessage();
			e.printStackTrace();
			logger.error(errorString
					+ e);
			if (e.getErrorCd() != null
					&& e.getErrorCd().equals(
							EdxRuleConstants.EDX_FAIL_INV_ON_REQ_FIELDS)) {
				throw e;
			} else {
				throw new EJBException(errorString, e);
			}
		} catch (Exception e) {
			String errorString = "General Exception while creating pageProxyVO from PHDC massage. "+e.getMessage();
			logger.error(errorString+e);
			e.printStackTrace();
			throw new EJBException(errorString, e);
		}
		return obj;
	}

	public static String requiredFieldCheck(
			Map<Object, Object> requiredQuestionIdentifierMap,
			Map<Object, Object> nbsCaseAnswerMap) throws NEDSSAppException {
		//
		String requireFieldError = null;
		Iterator<Object> iter = (requiredQuestionIdentifierMap.keySet())
				.iterator();
		Collection<Object> errorTextColl = new ArrayList<Object>();
		try {
			while (iter.hasNext()) {
				String reqdKey = (String) iter.next();
				logger.error(reqdKey);
				if (reqdKey != null) {
					if (nbsCaseAnswerMap == null
							|| nbsCaseAnswerMap.get(reqdKey) == null) {
						NbsQuestionMetadata metaData = (NbsQuestionMetadata) requiredQuestionIdentifierMap
								.get(reqdKey);
						if (metaData.getQuestionGroupSeqNbr() == null) {
							errorTextColl.add("[" + metaData.getQuestionLabel()
									+ "]");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getCause());
			logger.error("Exception raised" + e);
			e.printStackTrace();
		}
		if (errorTextColl != null && errorTextColl.size() > 0) {
			Iterator<Object> iterator = errorTextColl.iterator();
			String errorTextString = "";
			while (iterator.hasNext()) {
				String errorText = (String) iterator.next();
				if (errorTextColl.size() == 1) {
					errorTextString = errorText;
				} else {
					if (iterator.hasNext()) {
						errorTextString = errorTextString + errorText + "; ";
					} else {
						errorTextString = errorTextString + " and " + errorText
								+ ". ";
					}
				}
			}
			if (errorTextColl.size() == 1) {
				requireFieldError = "The following required field is missing: "
						+ errorTextString;
			} else if (errorTextColl.size() > 1) {
				requireFieldError = "The following required field(s) are missing: "
						+ errorTextString;
			} else
				requireFieldError = null;

		}
		return requireFieldError;
	}

	public static void setStandardNBSCaseAnswerVals(PublicHealthCaseDT phcDT,
			NbsCaseAnswerDT nbsCaseAnswerDT) {
		try {
			nbsCaseAnswerDT.setActUid(phcDT.getPublicHealthCaseUid());
			nbsCaseAnswerDT.setAddTime(phcDT.getAddTime());
			nbsCaseAnswerDT.setLastChgTime(phcDT.getLastChgTime());
			nbsCaseAnswerDT.setAddUserId(phcDT.getAddUserId());
			nbsCaseAnswerDT.setLastChgUserId(phcDT.getLastChgUserId());
			nbsCaseAnswerDT
					.setRecordStatusCd(NEDSSConstants.OPEN_INVESTIGATION);
			if (nbsCaseAnswerDT.getSeqNbr() != null
					&& nbsCaseAnswerDT.getSeqNbr().intValue() < 0)
				nbsCaseAnswerDT.setSeqNbr(new Integer(0));
			nbsCaseAnswerDT.setRecordStatusTime(phcDT.getRecordStatusTime());
			nbsCaseAnswerDT.setItNew(true);
		} catch (Exception ex) {
			String errorString = "Exception occured while setting standard values for NBS Case Answer DT. "+ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSSystemException(errorString,ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<Object, Object> loadQuestions(String conditionCode)
			throws NEDSSSystemException {
		Map<Object, Object> questionIdentifiers = null;
		String InvestigationFormCd=null;
		try{
		TreeMap<Object, Object> condAndFormCdTreeMap = CachedDropDowns
				.getConditionCdAndInvFormCd();
		
		Map<Object, Object> questionMap;
		InvestigationFormCd = condAndFormCdTreeMap.get(conditionCode).toString();

		if (InvestigationFormCd!=null && (InvestigationFormCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR)
				|| InvestigationFormCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT))) {
			if (QuestionsCache.map.containsKey(InvestigationFormCd))
				questionMap = (Map<Object, Object>) QuestionsCache.map
						.get(InvestigationFormCd);
			else
				questionMap = (Map<Object, Object>) QuestionsCache
						.getQuestionMap().get(InvestigationFormCd);
		} else {
			if (QuestionsCache.dmbMap.containsKey(InvestigationFormCd))
				questionMap = (Map<Object, Object>) QuestionsCache.dmbMap
						.get(InvestigationFormCd);
			else
				questionMap = (Map<Object, Object>) QuestionsCache
						.getDMBQuestionMap().get(InvestigationFormCd);
		}

		questionIdentifiers = loadQuestionKeys(questionMap,
				InvestigationFormCd);
		
		if (questionMap == null)
			throw new NEDSSSystemException("\n *************** Question Cache for "
					+ InvestigationFormCd + " is empty!!! *************** \n");
		}catch( Exception ex){
			ex.printStackTrace();
			throw new NEDSSSystemException("Question Cache for condition "+conditionCode+" and Investigation form Cd "	+ InvestigationFormCd + " is empty!!!");
		}
		
		return questionIdentifiers;

	}

	public static Map<Object, Object> loadQuestionKeys(
			Map<Object, Object> questionMap, String invFormCd) {
		if (questionMap == null)
			return null;
		Map<Object, Object> questionIdentifierMap = new HashMap<Object, Object>();
		Map<Object, Object> requiredQuestionIdentifierMap = new HashMap<Object, Object>();

		try {
			Iterator<Object> iter = (questionMap.keySet()).iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap
						.get(key);
				try {
					questionIdentifierMap.put(metaData.getQuestionIdentifier(),
							metaData);
					if (metaData.getRequiredInd() != null
							&& metaData.getRequiredInd().equalsIgnoreCase("T")
							&& metaData.getDataLocation() != null
							&& metaData.getDataLocation().equalsIgnoreCase(
									"NBS_Case_Answer.answer_txt")) {
						requiredQuestionIdentifierMap.put(
								metaData.getQuestionIdentifier(), metaData);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(
							"NbsQuestionMetadata error thrown for investigation form cd: "+invFormCd+lineSeperator
									+ metaData.toString() +lineSeperator+e.getMessage(), e);
				}
			}
			if (requiredQuestionIdentifierMap != null
					&& requiredQuestionIdentifierMap.size() > 0)
				questionIdentifierMap.put(_REQUIRED,
						requiredQuestionIdentifierMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("NbsQuestionMetadata error thrown " + e.getMessage(), e);
		}
		return questionIdentifierMap;
	}

	public static void createActRelationshipForDoc(Long documentUid,
			PublicHealthCaseVO publicHealthCaseVO) throws NEDSSAppException{
		try{
		ActRelationshipDT actDoc = new ActRelationshipDT();
		CDAXMLTypeToNBSObject.prepareActRelationship(actDoc, publicHealthCaseVO
				.getThePublicHealthCaseDT().getPublicHealthCaseUid(), NEDSSConstants.CASE,
				documentUid, NEDSSConstants.ACT_CLASS_CD_FOR_DOC,
				NEDSSConstants.DocToPHC);
		if (publicHealthCaseVO.getTheActRelationshipDTCollection() == null) {
			Collection<Object> coll = new ArrayList<Object>();
			coll.add(actDoc);
			publicHealthCaseVO.setTheActRelationshipDTCollection(coll);
		} else
			publicHealthCaseVO.getTheActRelationshipDTCollection().add(actDoc);
		//Act relationships for events contained in the document like 'Treatments', 'Interviews' etc.
		NbsDocumentDAOImpl nbsDocumentDAOImpl = new NbsDocumentDAOImpl();
		Map<String, EDXEventProcessDT> eventMap = nbsDocumentDAOImpl
				.getEDXEventProcessMap(documentUid);
		if (eventMap != null) {
			Collection<EDXEventProcessDT> eventList = eventMap.values();
			for (EDXEventProcessDT eventProcessDT : eventList) {
				actDoc = new ActRelationshipDT();
				if (eventProcessDT.getDocEventTypeCd().equals(
						NEDSSConstants.TREATMENT_ACT_TYPE_CD)){
					CDAXMLTypeToNBSObject.prepareActRelationship(actDoc, publicHealthCaseVO
							.getThePublicHealthCaseDT()
							.getPublicHealthCaseUid(), NEDSSConstants.CASE,
							eventProcessDT.getNbsEventUid(),
							NEDSSConstants.TREATMENT_ACT_TYPE_CD,
							NEDSSConstants.TRT_TO_PHC);
				publicHealthCaseVO.getTheActRelationshipDTCollection().add(
						actDoc);
				}
			}
		}
		}catch(Exception ex){
			String error = "Error while creating act relationship between case and document"+ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSAppException(error, ex);
		}
	}



	@SuppressWarnings("unchecked")
	public static void populatePHCValuesFromEntries(
			Map<Object, Object> questionMap, POCDMT000040Entry[] entries,
			PublicHealthCaseDT phcDT, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
		String processingQid = null;
		if(questionMap == null){
			throw new NEDSSSystemException("Question cache for condition "+phcDT.getCd() +" is null, cannot create investigation from PHDC document" );
		}
		try {
			for (POCDMT000040Entry entry : entries) {//  iterate through entities
				if (entry.getObservation() != null
						&& entry.getObservation().getCode() != null
						&& entry.getObservation().getCode().getCode() != null) {
					boolean beanPupulate = true;
					processingQid=entry.getObservation().getCode().getCode();
					NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap
							.get(entry.getObservation().getCode().getCode());
					if (dt != null) { //make sure meta data exists for the question identifier code
						String dataLoc = dt.getDataLocation() == null ? "" : dt
								.getDataLocation();
						if (dataLoc != null) {//Check for data location 
							String[] st = dataLoc.split("\\.");
							if (st != null && st.length > 1) {
								String colNm = st[1];
								String colVal = null; //single entry data
								String[] colvals = null; //multi-select data
								ANY[] valueArray = entry.getObservation()
										.getValueArray();
								boolean multi = false;
								if(dt.getNbsUiComponentUid() != null
										&& (dt.getNbsUiComponentUid()
												.compareTo(NEDSSConstants.MULTI_SELECT_CD) == 0))
									multi=true;
								
								if(valueArray!=null && multi){// for multi-select questions
									colvals = new String[valueArray.length];
									for (int j = 0;j<colvals.length;j++){
								        if(valueArray[j] instanceof CE){
								        	String displayString = null;
								        	String otherSpecifyValue = null;
								        	colvals[j]=XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), ((CE) valueArray[j]).getCode(), dt.getCodeSetClassCd());
								        	if(((CE) valueArray[j]).getTranslationArray()!=null && ((CE) valueArray[j]).getTranslationArray().length>0)
								        		displayString = ((CE) valueArray[j]).getTranslationArray()[0].getDisplayName();
								        	if (colvals[j] != null && colvals[j].equalsIgnoreCase(NEDSSConstants.OTHER) && displayString != null
								    				&& displayString.indexOf(NEDSSConstants.CARET) > 0) {
								    			otherSpecifyValue = displayString.substring(displayString.indexOf(NEDSSConstants.CARET)+1);
								    		}
								    		if (otherSpecifyValue != null)
								    			colvals[j] = colvals[j] + NEDSSConstants.CARET + otherSpecifyValue;
								        	
								        }
								        else if(valueArray[j] instanceof ST)
								        	colvals[j]=((ST) valueArray[j]).newCursor().getTextValue();
								    }
								}
								
								else if (valueArray!=null && valueArray[0] instanceof CE && !multi){ //coded questions
									String displayString = null;
						        	String otherSpecifyValue = null;
									colVal = XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(),  ((CE) valueArray[0]).getCode(), dt.getCodeSetClassCd());
									if(((CE) valueArray[0]).getTranslationArray()!=null && ((CE) valueArray[0]).getTranslationArray().length>0)
						        		displayString = ((CE) valueArray[0]).getTranslationArray()[0].getDisplayName();
						        	if (colVal != null && colVal.equalsIgnoreCase(NEDSSConstants.OTHER) && displayString != null
						    				&& displayString.indexOf(NEDSSConstants.CARET) > 0) {
						    			otherSpecifyValue = displayString.substring(displayString.indexOf(NEDSSConstants.CARET)+1);
						    		}
						    		if (otherSpecifyValue != null)
						    			colVal = colVal + NEDSSConstants.CARET + otherSpecifyValue;
								}
								else if (valueArray!=null && valueArray[0] instanceof TS && !multi) //date time questions
									colVal = CDAXMLTypeToNBSObject
											.parseStringDate(((TS) valueArray[0])
													.getValue());
								else if (valueArray!=null && valueArray[0] instanceof ST && !multi) //Text type questions
									colVal = ((ST) valueArray[0]).newCursor().getTextValue();
								
								else if (valueArray!=null && valueArray[0] instanceof II && !multi){ //Act Ids or participants
									colVal = ((II)valueArray[0]).getExtension();
									if(!dataLoc.startsWith(RenderConstants.ACT_ID)){//these are participants
										    ParticipationTypeVO participationType = CDAEntityProcessor.getParticipationTypeByQidAndEventType(dt.getQuestionIdentifier(), NEDSSConstants.CASE);
										    String code = "";
										    if(participationType!=null && participationType.getSubjectClassCd()!=null)
										    	code = participationType.getSubjectClassCd();
										((HashMap<Object, Object>) dataLocationToBeanMap
												.get(RenderConstants.PARTICIPANT))
												.put(dt.getQuestionIdentifier(),
														colVal+code);
									}
								}
								
								if((colVal != null || colvals!=null)  //for non NBS_CASE_ANSWER data
										&& colNm != null && dataLocationToBeanMap.get(st[0]) != null
										&& dataLoc.contains(RenderConstants.ACT_ID)
										&& dataLocationToBeanMap.get(st[0]) != null) //ACT Ids
								{
									beanPupulate=false;
									createActIdsForCase(dt,phcDT, colVal);
								}
								if((colVal != null || colvals!=null)  //for non NBS_CASE_ANSWER data
										&& colNm != null && dataLocationToBeanMap.get(st[0]) != null
										&& dataLoc.contains(RenderConstants.CONFIRMATION_METHOD)
										&& dataLocationToBeanMap.get(st[0]) != null) //Confirmation method
								{
									beanPupulate=false;
									createConfirmationMethodsForCase(dt,phcDT,colVal,colvals);
								}
								else if ((colVal != null || colvals!=null)  //for non NBS_CASE_ANSWER data
										&& colNm != null && dataLocationToBeanMap.get(st[0]) != null
										&& !dataLoc.contains(RenderConstants.NBS_CASE_ANSWER)
										&& beanPupulate
										&& dataLocationToBeanMap.get(st[0]) != null) {
									DynamicBeanBinding.populateBean(
											dataLocationToBeanMap.get(st[0]),
											colNm, colVal);
									
								} else if ((colVal != null || colvals!=null)  //for NBS_CASE_ANSWER data
										&& colNm != null && dataLocationToBeanMap.get(st[0]) != null
										&& dataLoc.contains(RenderConstants.NBS_CASE_ANSWER)) {
									if(colVal != null ){ //set answerDT for single entry questions
									NbsCaseAnswerDT caseAnswerDT = new NbsCaseAnswerDT();
									caseAnswerDT.setAnswerTxt(colVal);
									setStandardNBSCaseAnswerVals(phcDT,	caseAnswerDT);
									caseAnswerDT.setNbsQuestionUid(dt.getNbsQuestionUid());
									caseAnswerDT.setNbsQuestionVersionCtrlNbr(dt.getQuestionVersionNbr());
									caseAnswerDT.setSeqNbr(0);
									((HashMap<Object, Object>) dataLocationToBeanMap
											.get(RenderConstants.NBS_CASE_ANSWER))
											.put(dt.getQuestionIdentifier(),
													caseAnswerDT);
									
								}else if(colvals != null){ //set answerDT for multi-select questions
									int i=1;
									ArrayList<Object> caseAnswerDTList = new ArrayList<Object>();
									for(String value: colvals){
											NbsCaseAnswerDT caseAnswerDT = new NbsCaseAnswerDT();
											caseAnswerDT.setAnswerTxt(value);
											setStandardNBSCaseAnswerVals(phcDT,	caseAnswerDT);
											caseAnswerDT.setNbsQuestionUid(dt.getNbsQuestionUid());
											caseAnswerDT.setNbsQuestionVersionCtrlNbr(dt.getQuestionVersionNbr());
											caseAnswerDT.setSeqNbr(i++);
											caseAnswerDTList.add(caseAnswerDT);
										}
									((HashMap<Object, Object>) dataLocationToBeanMap
											.get(RenderConstants.NBS_CASE_ANSWER))
											.put(dt.getQuestionIdentifier(), caseAnswerDTList);
									}
								}
							}
						}
					}
					else if(processingQid!=null && processingQid.equals("INV099") && entry.getObservation().getValueArray()!=null && entry.getObservation().getValueArray().length>0){// special case for co-infectionId
						String value = entry.getObservation().getValueArray(0).newCursor().getTextValue();
						dataLocationToBeanMap.put(RenderConstants.COINFECTION_ID, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String error = "Exception in populatePHCValuesFromEntries: Question Identifer: "+processingQid+lineSeperator+e.getMessage();
			throw new NEDSSSystemException(error,e);
		}
	}
	
	public static void populateSignsandSymptomsFromEntries(
			Map<Object, Object> questionMap, POCDMT000040Entry[] entries,
			PublicHealthCaseDT phcDT, NBSSecurityObj nbsSecurityObj)
			throws NEDSSSystemException {
		try {
			int i = 1;
			for (POCDMT000040Entry entry : entries) {// iterate through entities
				if (entry.getObservation() != null
						&& entry.getObservation().getCode() != null) {
					NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap
							.get("INV272");
					createRepeatingQuestionMap(
							createRepeatingAnswerDT(XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), entry.getObservation()
									.getCode().getCode(), dt.getCodeSetClassCd()), null, phcDT, dt,	nbsSecurityObj, 0, i));
				}
				if (entry.getObservation()!=null && entry.getObservation().getEffectiveTime()!=null) {
					NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap
							.get("NBS247");
					createRepeatingQuestionMap(
							createRepeatingAnswerDT(CDAXMLTypeToNBSObject
									.parseStringDate(entry.getObservation().getEffectiveTime().getValue()), null, phcDT, dt,	nbsSecurityObj, 0, i));
				}
				if (entry.getObservation()!=null && entry.getObservation().getInformantArray()!=null && entry.getObservation().getInformantArray().length>0) {
					NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap
							.get("NBS246");
					createRepeatingQuestionMap(
							createRepeatingAnswerDT(XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), entry.getObservation().getInformantArray(0).getAssignedEntity().getCode().getCode(), dt.getCodeSetClassCd()), null, phcDT, dt,	nbsSecurityObj, 0, i));
				}
				if (entry.getObservation()!=null && entry.getObservation() != null
						&& entry.getObservation().getValueArray() != null && entry.getObservation().getValueArray().length>0) {
					NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap
							.get("STD121");
					createRepeatingQuestionMap(
							createRepeatingAnswerDT(XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), ((CE)entry.getObservation().getValueArray(0)).getCode(), dt.getCodeSetClassCd()), null, phcDT, dt,	nbsSecurityObj, 0, i));
				}
				i++;
			}
		} catch (Exception ex) {
			String errorString = "Error while populating  signs and symtoms and exception is raised:"+ex.getMessage();
			logger.error(errorString+lineSeperator+ex);
			ex.printStackTrace();
			throw new NEDSSSystemException(errorString);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void populateGenericRepeatingQuestionsFromEntries(
			Map<Object, Object> questionMap, POCDMT000040Entry[] entries,
			PublicHealthCaseDT phcDT, HashMap<Object, Object> legacyToNBSEntityUidMap, NBSSecurityObj nbsSecurityObj)
			throws NEDSSSystemException {
		String processingQid = null;
		try {
			HashMap<Object, Object> organizedEntities = CDAProcessorUtil.organizeRepeatingQuestionsInMap(entries);
			if(organizedEntities!=null && organizedEntities.size()>0){
		    for(Object value: organizedEntities.values()){
		    	processingQid = null;
		    	int i = 1;
		    	Collection<POCDMT000040Entry> repeatingEntries =(Collection<POCDMT000040Entry>)value;
		    	for(POCDMT000040Entry entry: repeatingEntries){
		    		processingQid = null;
		    		if(entry.getOrganizer()!=null && entry.getOrganizer().getComponentArray()!=null && entry.getOrganizer().getComponentArray().length>0){
		    			for(POCDMT000040Component4 component : entry.getOrganizer().getComponentArray()){
		    				processingQid = null;
							boolean multiselect = false;
							if(component.getObservation()!=null && component.getObservation().getCode()!=null){
								processingQid = component.getObservation().getCode().getCode();
								String notesTimestamp = "";
								String notesProviderId = null;
								if(component.getObservation().getAuthorArray()!=null && component.getObservation().getAuthorArray().length>0){
									POCDMT000040Author author = component.getObservation().getAuthorArray(0);
									if(author!=null){
										notesTimestamp = CDAXMLTypeToNBSObject.parseStringDate(author.getTime().getValue());
										if(notesTimestamp==null)
											notesTimestamp="";
										if(author.getAssignedAuthor()!=null && author.getAssignedAuthor().getIdArray()!=null && author.getAssignedAuthor().getIdArray().length>0)
											notesProviderId = author.getAssignedAuthor().getIdArray(0).getExtension();
									}
								}
								NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap
										.get(component.getObservation().getCode().getCode());
								if(dt!=null){
									if(dt.getNbsUiComponentUid() != null
										&& ((dt.getNbsUiComponentUid()
												.compareTo(NEDSSConstants.MULTI_SELECT_CD) == 0 )))
									multiselect=true;
								ANY[] valueArray = component.getObservation().getValueArray();
								if(valueArray!=null && valueArray.length>0 && valueArray[0] instanceof CE && !multiselect) {
									// coded questions
									String displayName =  null;
									
									if(((CE)valueArray[0]).getTranslationArray()!=null && ((CE) valueArray[0]).getTranslationArray().length>0)
										displayName = ((CE)valueArray[0]).getTranslationArray()[0].getDisplayName();
									createRepeatingQuestionMap(
											createRepeatingAnswerDT(XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), ((CE)valueArray[0]).getCode(), dt.getCodeSetClassCd()), displayName, phcDT, dt,	nbsSecurityObj, 0, i));
								}
								else if (valueArray!=null && valueArray.length>0 && valueArray[0] instanceof TS && !multiselect) //date time questions
									createRepeatingQuestionMap(
											createRepeatingAnswerDT(CDAXMLTypeToNBSObject
													.parseStringDate(((TS)valueArray[0]).getValue()), null, phcDT, dt,	nbsSecurityObj, 0, i));
								else if (valueArray!=null && valueArray.length>0 && valueArray[0] instanceof ST && !multiselect){ //Text type questions
									if (dt.getNbsUiComponentUid() != null
											&& dt.getNbsUiComponentUid()
													.compareTo(
															NEDSSConstants.MULTI_LINE_NOTES_W_USER_DATE_STAMP_CD) == 0) {
										createRepeatingQuestionMap(
												createRepeatingAnswerDTForUserNotes(((ST)valueArray[0]).newCursor().getTextValue(), phcDT, dt,	nbsSecurityObj, 0, i, notesTimestamp,notesProviderId));
									}else
									createRepeatingQuestionMap(
											createRepeatingAnswerDT(((ST) valueArray[0]).newCursor().getTextValue(), null, phcDT, dt,	nbsSecurityObj, 0, i));
								}
								else if (valueArray!=null && valueArray[0] instanceof II && !multiselect){ //participants in repeating Section
									String legacyId = ((II)valueArray[0]).getExtension();
									String newId = null;
									if(legacyToNBSEntityUidMap!=null){// ParticipantType
										ParticipationTypeVO participationType = CDAEntityProcessor.getParticipationTypeByQidAndEventType(dt.getQuestionIdentifier(), NEDSSConstants.CASE);
									    String code = "";
									    if(participationType!=null && participationType.getSubjectClassCd()!=null)
									    	code = participationType.getSubjectClassCd();
										newId = (String)legacyToNBSEntityUidMap.get(legacyId+code);
									}
									createRepeatingQuestionMap(
											createRepeatingAnswerDT(newId==null?legacyId:newId, null, phcDT, dt,	nbsSecurityObj, 0, i));
								}
								else if(valueArray!=null && valueArray.length>0 && valueArray[0] instanceof CE && multiselect){ //set answerDT for multi-select questions
									for(ANY ansValue: valueArray){
										String displayName =  null;
										if(ansValue!=null && ((CE)ansValue).getTranslationArray()!=null && ((CE) ansValue).getTranslationArray().length>0)
											displayName = ((CE)valueArray[0]).getTranslationArray()[0].getDisplayName();
										if(dt!=null)
											createRepeatingQuestionMap(
													createRepeatingAnswerDT( XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), ((CE)ansValue).getCode(), dt.getCodeSetClassCd()), displayName, phcDT, dt,	nbsSecurityObj, 0, i));
											}
										}
									}
								}
							}
						}
						i++;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String error = "Exception in populateGenericRepeatingQuestionsFromEntries: "+processingQid +lineSeperator+ex.getMessage();
			throw new NEDSSSystemException(error,ex);
		}
	}
	
	public static void populateSTDHistoryFromEntries(
			Map<Object, Object> questionMap, POCDMT000040Entry[] entries,
			PublicHealthCaseDT phcDT, NBSSecurityObj nbsSecurityObj)
			throws NEDSSSystemException {
		String processingQid = null;
		try {
			int i = 1;
			
			for (POCDMT000040Entry entry : entries) {// iterate through entities
				processingQid = null;
				if (entry.getObservation() != null
						&& entry.getObservation().getCode() != null) {
					processingQid=entry.getObservation().getCode().getCode();
					NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap
							.get("NBS250");
					createRepeatingQuestionMap(
							createRepeatingAnswerDT(XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), ((CE)entry.getObservation()
									.getValueArray(0)).getCode(), dt.getCodeSetClassCd()), null, phcDT, dt,	nbsSecurityObj, 0, i));
				}
				if (entry.getObservation()!=null && entry.getObservation().getEffectiveTime()!=null) {
					NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap
							.get("NBS251");
					createRepeatingQuestionMap(
							createRepeatingAnswerDT(CDAXMLTypeToNBSObject
									.parseStringDate(entry.getObservation().getEffectiveTime().getValue()), null, phcDT, dt,	nbsSecurityObj, 0, i));
				}
				if (entry.getObservation()!=null && entry.getObservation().getEntryRelationshipArray()!=null){
					for(POCDMT000040EntryRelationship entryRelationship : entry.getObservation().getEntryRelationshipArray()){
						processingQid=null;
						boolean multiselect = false;
						if(entryRelationship.getObservation()!=null && entryRelationship.getObservation().getCode()!=null){
							processingQid=entryRelationship.getObservation().getCode().getCode();
							NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap
									.get(entryRelationship.getObservation().getCode().getCode());
							if(dt != null){
							if(dt.getNbsUiComponentUid() != null
									&& (dt.getNbsUiComponentUid()
											.compareTo(NEDSSConstants.MULTI_SELECT_CD) == 0))
								multiselect=true;
							ANY[] valueArray = entryRelationship.getObservation().getValueArray();
							if(valueArray!=null && valueArray.length>0 && valueArray[0] instanceof CE && !multiselect) {// coded questions
								String displayName =  null;
								if(((CE)valueArray[0]).getTranslationArray()!=null && ((CE) valueArray[0]).getTranslationArray().length>0)
									displayName = ((CE)valueArray[0]).getTranslationArray()[0].getDisplayName();
								createRepeatingQuestionMap(
										createRepeatingAnswerDT(XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), ((CE)valueArray[0]).getCode(), dt.getCodeSetClassCd()), displayName, phcDT, dt,	nbsSecurityObj, 0, i));
							}else if (valueArray!=null && valueArray.length>0 && valueArray[0] instanceof TS && !multiselect) //date time questions
								createRepeatingQuestionMap(
										createRepeatingAnswerDT(CDAXMLTypeToNBSObject
												.parseStringDate(((TS)valueArray[0]).getValue()), null, phcDT, dt,	nbsSecurityObj, 0, i));
							else if (valueArray!=null && valueArray.length>0 && valueArray[0] instanceof ST && !multiselect) //Text type questions
								createRepeatingQuestionMap(
										createRepeatingAnswerDT(((ST) valueArray[0]).newCursor().getTextValue(), null, phcDT, dt,	nbsSecurityObj, 0, i));
							else if(valueArray!=null && valueArray.length>0 && valueArray[0] instanceof CE && multiselect){ //set answerDT for multi-select questions
								for(ANY value: valueArray){
									String displayName =  null;
									if(value!=null && ((CE)value).getTranslationArray()!=null && ((CE) value).getTranslationArray().length>0)
										displayName = ((CE)valueArray[0]).getTranslationArray()[0].getDisplayName();
									if(dt!=null)
									createRepeatingQuestionMap(
													createRepeatingAnswerDT( XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), ((CE)value).getCode(), dt.getCodeSetClassCd()),displayName, phcDT, dt,	nbsSecurityObj, 0, i));
								}
							}
						}
						}
					}
				}
				i++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String error = "Exception in populateSTDHistoryFromEntries for question identifier: "+processingQid+lineSeperator+ex.getMessage();
			throw new NEDSSSystemException(error,	ex);
		}
	}

	public static NbsCaseAnswerDT createRepeatingAnswerDT(String value, String displayName, PublicHealthCaseDT phcDT,
			NbsQuestionMetadata dt, NBSSecurityObj nbsSecurityObj, int seqNo, int groupSeqNo)
			throws NEDSSSystemException {
		String otherSpecifyValue = null;
		if (value == null)
			return null;
		// To handle Other specify value
		if (value != null && value.equalsIgnoreCase(NEDSSConstants.OTHER) && displayName != null
				&& displayName.indexOf(NEDSSConstants.CARET) > 0) {
			otherSpecifyValue = displayName.substring(displayName.indexOf(NEDSSConstants.CARET)+1);
		}
		if (otherSpecifyValue != null)
			value = value + NEDSSConstants.CARET + otherSpecifyValue;
		NbsCaseAnswerDT caseAnswerDT = new NbsCaseAnswerDT();
		try {
			caseAnswerDT.setAnswerTxt(value);
			setStandardNBSCaseAnswerVals(phcDT, caseAnswerDT);
			caseAnswerDT.setNbsQuestionUid(dt.getNbsQuestionUid());
			caseAnswerDT.setNbsQuestionVersionCtrlNbr(dt.getQuestionVersionNbr());
			caseAnswerDT.setSeqNbr(seqNo);
			caseAnswerDT.setAnswerGroupSeqNbr(groupSeqNo);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception in createRepeatingAnswerDTs:");
			throw new NEDSSSystemException(
					"createRepeatingAnswerDTs: Error while creating repeated answers, exception is raised: "
							+ ex.getMessage(),
					ex);
		}
		return caseAnswerDT;
	}
	
	public static NbsCaseAnswerDT createRepeatingAnswerDTForUserNotes(String value,
			PublicHealthCaseDT phcDT, NbsQuestionMetadata dt,
			NBSSecurityObj nbsSecurityObj, int seqNo, int groupSeqNo, String notesDate, String notesUserId)
			throws NEDSSSystemException {
		if(value==null)
			return null;
		NbsDocumentDAOImpl documentDAO = new NbsDocumentDAOImpl();
		String userName = documentDAO.getUserNameByProviderEntityID(notesUserId);
		if(userName == null || userName.trim().equals(""))
			 userName = nbsSecurityObj.getTheUserProfile().getTheUser()
					.getFirstName()+ " "+ nbsSecurityObj.getTheUserProfile().getTheUser()
							.getLastName();
		 if(notesDate==null || notesDate.trim().equals(""))
			 notesDate = StringUtils.formatDatewithHrMinSec(phcDT.getAddTime());
		NbsCaseAnswerDT caseAnswerDT = new NbsCaseAnswerDT();
		try {
			caseAnswerDT.setAnswerTxt(value);
			setStandardNBSCaseAnswerVals(phcDT, caseAnswerDT);
			caseAnswerDT.setNbsQuestionUid(dt.getNbsQuestionUid());
			caseAnswerDT.setNbsQuestionVersionCtrlNbr(dt.getQuestionVersionNbr());
			caseAnswerDT.setSeqNbr(seqNo);
			caseAnswerDT.setAnswerGroupSeqNbr(groupSeqNo);
			caseAnswerDT.setAnswerTxt(userName+"~"+notesDate+"~~"+value);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception in createRepeatingAnswerDTForUserNotes:");
			throw new NEDSSSystemException(
					"createRepeatingAnswerDTForUserNotes: Error while creating repeated answers, exception is raised: "+ex.getMessage(),
					ex);
		}
		return caseAnswerDT;
	}
	
	@SuppressWarnings("unchecked")
	public static void createRepeatingQuestionMap(NbsAnswerDT answerDT)
			throws NEDSSSystemException {
		try {
			if(answerDT==null)
				return;
			if (((HashMap<Object, Object>) dataLocationToBeanMap
					.get(RenderConstants.REPEATING_NBS_CASE_ANSWER))
					.get(answerDT.getNbsQuestionUid()) == null) {
				Collection<Object> collection = new ArrayList<Object>();
				collection.add(answerDT);
				((HashMap<Object, Object>) dataLocationToBeanMap
						.get(RenderConstants.REPEATING_NBS_CASE_ANSWER)).put(
						answerDT.getNbsQuestionUid(), collection);
			} else {
				Collection<Object> collection = (Collection<Object>) ((HashMap<Object, Object>) dataLocationToBeanMap
						.get(RenderConstants.REPEATING_NBS_CASE_ANSWER))
						.get(answerDT.getNbsQuestionUid());
				collection.add(answerDT);
				((HashMap<Object, Object>) dataLocationToBeanMap
						.get(RenderConstants.REPEATING_NBS_CASE_ANSWER)).put(
						answerDT.getNbsQuestionUid(), collection);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception in createRepeatingQuestionMap:");
			throw new NEDSSSystemException(
					"createRepeatingQuestionMap: Error while creating repeated answers Map, exception is raised: "+ex.getMessage(),
					ex);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static void createActIdsForCase(NbsQuestionMetadata dt,
			PublicHealthCaseDT phcDT, String idValue)
			throws NEDSSSystemException {
		try {
			int actSeq = 0;
			if (dt != null
					&& dt.getDataCd() != null
					&& dt.getDataCd().equals(
							NEDSSConstants.ACT_ID_STATE_TYPE_CD))
				actSeq = 1;
			if (dt != null
					&& dt.getDataCd() != null
					&& dt.getDataCd()
							.equals(NEDSSConstants.ACT_ID_CITY_TYPE_CD))
				actSeq = 2;
			if (dt != null
					&& dt.getDataCd() != null
					&& dt.getDataCd().equals(
							NEDSSConstants.ACT_ID_LEGACY_TYPE_CD))
				actSeq = 3;
			ActIdDT actIdDT = new ActIdDT();
			actIdDT.setActIdSeq(actSeq);
			actIdDT.setActUid(phcDT.getPublicHealthCaseUid());
			if(dt!=null)
				actIdDT.setTypeCd(dt.getDataCd());
			actIdDT.setRootExtensionTxt(idValue);
			actIdDT.setRecordStatusTime(phcDT.getAddTime());
			actIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			actIdDT.setAddTime(phcDT.getAddTime());
			actIdDT.setAddUserId(phcDT.getAddUserId());
			actIdDT.setAddUserId(phcDT.getAddUserId());
			actIdDT.setAddUserId(phcDT.getAddUserId());
			actIdDT.setLastChgTime(phcDT.getLastChgTime());
			actIdDT.setLastChgUserId(phcDT.getLastChgUserId());
			((ArrayList<Object>) dataLocationToBeanMap
					.get(RenderConstants.ACT_ID)).add(actIdDT);
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorTxt = "Error while setting Act Ids for CDA Document"+ex.getMessage();
			throw new NEDSSSystemException(errorTxt, ex);
		}
	}
	
	
	public static void updateActIdCollection(PublicHealthCaseVO phcVO, Map<Object, Object> questionMap )
			throws NEDSSSystemException {
		try {
			
			boolean doesStateCaseIdExists = false;
			boolean doesCityCaseIdExists = false;
			boolean doesLegacyCaseIdExists = false;
			
			Collection<Object> ActIds = phcVO.getTheActIdDTCollection();
			if(ActIds!=null && ActIds.size()>0){
				for(Object actIdDT: ActIds){
					ActIdDT actId= (ActIdDT)actIdDT;
					if(actId.getTypeCd()!=null && actId.getTypeCd().equals(NEDSSConstants.ACT_ID_STATE_TYPE_CD))
						doesStateCaseIdExists=true;
					else if(actId.getTypeCd()!=null && actId.getTypeCd().equals(NEDSSConstants.ACT_ID_CITY_TYPE_CD))
						doesCityCaseIdExists=true;
					if(actId.getTypeCd()!=null && actId.getTypeCd().equals(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD))
						doesLegacyCaseIdExists=true;
				}
			}
			PublicHealthCaseDT phcDT = phcVO.getThePublicHealthCaseDT();
			if(ActIds==null)
				phcVO.setTheActIdDTCollection(new ArrayList<Object>());
			
			if(!doesStateCaseIdExists && questionMap.containsKey("INV173")){
				ActIdDT actIdDT = new ActIdDT();
				actIdDT.setActIdSeq(1);
				actIdDT.setActUid(phcDT.getPublicHealthCaseUid());
				actIdDT.setTypeCd(NEDSSConstants.ACT_ID_STATE_TYPE_CD);
				actIdDT.setRootExtensionTxt(null);
				actIdDT.setRecordStatusTime(phcDT.getAddTime());
				actIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
				actIdDT.setAddTime(phcDT.getAddTime());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setLastChgTime(phcDT.getLastChgTime());
				actIdDT.setLastChgUserId(phcDT.getLastChgUserId());
				phcVO.getTheActIdDTCollection().add(actIdDT);
			}
			
			if(!doesCityCaseIdExists && questionMap.containsKey("INV198")){
				ActIdDT actIdDT = new ActIdDT();
				actIdDT.setActIdSeq(2);
				actIdDT.setActUid(phcDT.getPublicHealthCaseUid());
				actIdDT.setTypeCd(NEDSSConstants.ACT_ID_CITY_TYPE_CD);
				actIdDT.setRootExtensionTxt(null);
				actIdDT.setRecordStatusTime(phcDT.getAddTime());
				actIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
				actIdDT.setAddTime(phcDT.getAddTime());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setLastChgTime(phcDT.getLastChgTime());
				actIdDT.setLastChgUserId(phcDT.getLastChgUserId());
				phcVO.getTheActIdDTCollection().add(actIdDT);
			}
			
			if(!doesLegacyCaseIdExists && questionMap.containsKey("INV200")){
				ActIdDT actIdDT = new ActIdDT();
				actIdDT.setActIdSeq(3);
				actIdDT.setActUid(phcDT.getPublicHealthCaseUid());
				actIdDT.setTypeCd(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD);
				actIdDT.setRootExtensionTxt(null);
				actIdDT.setRecordStatusTime(phcDT.getAddTime());
				actIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
				actIdDT.setAddTime(phcDT.getAddTime());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setLastChgTime(phcDT.getLastChgTime());
				actIdDT.setLastChgUserId(phcDT.getLastChgUserId());
				phcVO.getTheActIdDTCollection().add(actIdDT);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorTxt = "Error while updating Act Ids for CDA Document"+ex.getMessage();
			throw new NEDSSSystemException(errorTxt, ex);
		}
	}


	@SuppressWarnings("unchecked")
	public static void createConfirmationMethodsForCase(NbsQuestionMetadata dt,
			PublicHealthCaseDT phcDT, String confirmationMethodDate,
			String[] confirmationMethods) throws NEDSSSystemException {
		try {
			ArrayList<Object> confirmationMethodList = ((ArrayList<Object>) dataLocationToBeanMap
					.get(RenderConstants.CONFIRMATION_METHOD));
			if (confirmationMethods != null && confirmationMethods.length > 0) {
				Timestamp confirmationDate = null;
				for (String confirmationMethodCd : confirmationMethods) {
					if (confirmationMethodList.size() > 0
							&& ((ConfirmationMethodDT) confirmationMethodList
									.get(0)).getConfirmationMethodTime() != null
							&& ((ConfirmationMethodDT) confirmationMethodList
									.get(0)).getConfirmationMethodCd().equals(
									NEDSSConstants.NOT_APPLICABLE)) {
						((ConfirmationMethodDT) confirmationMethodList.get(0))
								.setConfirmationMethodCd(confirmationMethodCd);
						confirmationDate = ((ConfirmationMethodDT) confirmationMethodList
								.get(0)).getConfirmationMethodTime();
					} else {
						ConfirmationMethodDT confirmationMethodDT = new ConfirmationMethodDT();
						confirmationMethodDT
								.setConfirmationMethodCd(XMLTypeToNBSObject
										.getNBSCodeFromPHINCodes(
												dt.getCodeSetNm(),
												confirmationMethodCd,
												dt.getCodeSetClassCd()) == null ? confirmationMethodCd
										: XMLTypeToNBSObject
												.getNBSCodeFromPHINCodes(
														dt.getCodeSetNm(),
														confirmationMethodCd,
														dt.getCodeSetClassCd()));
						confirmationMethodDT.setPublicHealthCaseUid(phcDT
								.getPublicHealthCaseUid());
						confirmationMethodDT
								.setConfirmationMethodTime(confirmationDate);
						if(confirmationMethodDT.getConfirmationMethodCd()!=null)
							((ArrayList<Object>) dataLocationToBeanMap
									.get(RenderConstants.CONFIRMATION_METHOD))
									.add(confirmationMethodDT);
					}
				}
			} else if (confirmationMethodDate != null
					&& confirmationMethodList.size() > 0) {
				for (Object confirmationMethodDT : confirmationMethodList) {
					((ConfirmationMethodDT) confirmationMethodDT)
							.setConfirmationMethodTime_s(confirmationMethodDate);
				}

			} else if (confirmationMethodDate != null
					&& confirmationMethodList.size() == 0) {
				ConfirmationMethodDT confirmationMethodDT = new ConfirmationMethodDT();
				confirmationMethodDT
						.setConfirmationMethodCd(NEDSSConstants.NOT_APPLICABLE);
				confirmationMethodDT.setPublicHealthCaseUid(phcDT
						.getPublicHealthCaseUid());
				confirmationMethodDT
						.setConfirmationMethodTime_s(confirmationMethodDate);
				((ArrayList<Object>) dataLocationToBeanMap
						.get(RenderConstants.CONFIRMATION_METHOD))
						.add(confirmationMethodDT);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorTxt = "Error while setting confirmation method cd/date for CDA Document"+ex.getMessage();
			throw new NEDSSSystemException(errorTxt, ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void updateParticipantsFromRepeatingQuestions(POCDMT000040Section section){
		try{
		POCDMT000040Entry[] entryArray = section.getEntryArray();
		if(entryArray!=null && entryArray.length>0){
			int i=0;
			for(POCDMT000040Entry entry:entryArray){
				if(entry!=null && entry.getOrganizer()!=null && entry.getOrganizer().getComponentArray()!=null && entry.getOrganizer().getComponentArray().length>0){
					for(POCDMT000040Component4 component : entry.getOrganizer().getComponentArray()){
						if(component.getObservation().getCode()!=null && component.getObservation().getCode().getCode()!=null){
							String qId = component.getObservation().getCode().getCode();
							ANY[] valueArray = component.getObservation().getValueArray();
							if (valueArray!=null && valueArray[0] instanceof II){ 
								ParticipationTypeVO participationType = CDAEntityProcessor.getParticipationTypeByQidAndEventType(qId, NEDSSConstants.CASE);
								String code="";
							    if(participationType!=null && participationType.getSubjectClassCd()!=null)
							    	code = participationType.getSubjectClassCd();
							((HashMap<Object, Object>) dataLocationToBeanMap
									.get(RenderConstants.PARTICIPANT))
									.put(qId+"^"+i++, ((II)valueArray[0]).getExtension()+code);
							}
						}
					}
				}
			}
		}
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorTxt = "Error while setting participants from repeating questions "+ex.getMessage();
			throw new NEDSSSystemException(errorTxt, ex);
		}
	}
}