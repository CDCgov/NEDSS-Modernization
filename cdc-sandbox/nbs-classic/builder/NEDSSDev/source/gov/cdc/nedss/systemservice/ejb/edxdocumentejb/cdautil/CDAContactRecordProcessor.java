package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil;

import gov.cdc.nedss.act.ctcontact.dt.CTContactAnswerDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.phdc.cda.ANY;
import gov.cdc.nedss.phdc.cda.CE;
import gov.cdc.nedss.phdc.cda.ClinicalDocumentDocument1;
import gov.cdc.nedss.phdc.cda.II;
import gov.cdc.nedss.phdc.cda.POCDMT000040Entry;
import gov.cdc.nedss.phdc.cda.POCDMT000040Section;
import gov.cdc.nedss.phdc.cda.ST;
import gov.cdc.nedss.phdc.cda.TS;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxCDAInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPersonEntityProcessor;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessCaseSummaryDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.CDAXMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.RenderConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlbeans.XmlObject;

public class CDAContactRecordProcessor {

	static final LogUtils logger = new LogUtils(CDAContactRecordProcessor.class.getName());
	public static Map<Object, Object> beanMethodMap = new HashMap<Object, Object>();
	public static Map<Object, Object> dataLocationToBeanMap = new HashMap<Object, Object>();
	
	public static void initializeProcessor(){
		dataLocationToBeanMap.put(RenderConstants.CT_CONTACT,
				new CTContactDT());
		dataLocationToBeanMap.put(RenderConstants.PARTICIPANT,
				new HashMap<Object, Object>());
		dataLocationToBeanMap.put(RenderConstants.INTERESTED_PARTIES,
				new HashMap<Object, Object>());
		dataLocationToBeanMap.put(RenderConstants.CT_CONTACT_ANSWER,
				new HashMap<Object, Object>());
	}

	@SuppressWarnings("unchecked")
	public static Object createProxyVO(EdxCDAInformationDT informationDT, POCDMT000040Section[] sections, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException{
	
			CTContactProxyVO proxyVO = null;
			Long UserId = new Long(nbsSecurityObj.getTheUserProfile().getTheUser()
					.getEntryID());
			int j = 0;
			try {
				CTContactVO contactVO = new CTContactVO();
				contactVO.setcTContactDT((CTContactDT)dataLocationToBeanMap
						.get(RenderConstants.CT_CONTACT));
				/*Check to see if there are existing related case for subject and contact, set up the appropriate relationships*/
				EDXEventProcessCaseSummaryDT edxProcessCaseSummaryForSubject = (EDXEventProcessCaseSummaryDT) informationDT
						.getRuleAlgorothmManagerDT()
						.geteDXEventProcessCaseSummaryDTMap()
						.get(EdxCDAConstants.SUBJECT_INV);
				
				if(edxProcessCaseSummaryForSubject == null){
					return proxyVO;
				}
				
				EDXEventProcessCaseSummaryDT edxProcessCaseSummaryForContact = (EDXEventProcessCaseSummaryDT) informationDT
						.getRuleAlgorothmManagerDT()
						.geteDXEventProcessCaseSummaryDTMap()
						.get(EdxCDAConstants.CONTACT_INV);
				
				EDXEventProcessCaseSummaryDT edxProcessNamedInInterview = (EDXEventProcessCaseSummaryDT) informationDT
						.getRuleAlgorothmManagerDT()
						.geteDXEventProcessCaseSummaryDTMap()
						.get(EdxCDAConstants.NAMED_IN_INTEVIEW);
				
				contactVO.getcTContactDT().setSubjectEntityUid(edxProcessCaseSummaryForSubject.getPersonUid());
				contactVO.getcTContactDT().setSubjectEntityPhcUid(edxProcessCaseSummaryForSubject.getNbsEventUid());
				contactVO.getcTContactDT().setProgAreaCd(edxProcessCaseSummaryForSubject.getProgAreaCd());
				contactVO.getcTContactDT().setJurisdictionCd(edxProcessCaseSummaryForSubject.getJurisdictionCd());
				contactVO.getcTContactDT().setProgramJurisdictionOid(edxProcessCaseSummaryForSubject.getProgramJurisdictionOid());
				contactVO.getcTContactDT().setContactEntityUid(new Long(--j));
				if(edxProcessCaseSummaryForContact!=null)
					contactVO.getcTContactDT().setContactEntityPhcUid(edxProcessCaseSummaryForContact.getNbsEventUid());
				
				if(edxProcessNamedInInterview!=null)
					contactVO.getcTContactDT().setNamedDuringInterviewUid(edxProcessNamedInInterview.getNbsEventUid());
				
				contactVO.getcTContactDT().setCtContactUid(new Long(--j));
				contactVO.getcTContactDT().setAddTime(
						informationDT.getRuleAlgorothmManagerDT().getLastChgTime());
				contactVO.getcTContactDT().setLastChgTime(
						informationDT.getRuleAlgorothmManagerDT().getLastChgTime());
				contactVO.getcTContactDT().setAddUserId(UserId);
				contactVO.getcTContactDT().setLastChgUserId(UserId);
				contactVO.getcTContactDT().setRecordStatusCd(NEDSSConstants.ACTIVE);
				contactVO.getcTContactDT().setRecordStatusTime(
						informationDT.getRuleAlgorothmManagerDT().getLastChgTime());
				contactVO.setItNew(true);
				contactVO.setItDirty(false);
				contactVO.getcTContactDT().setItNew(true);
				contactVO.getcTContactDT().setItDirty(false);

				proxyVO = new CTContactProxyVO();
				proxyVO.setcTContactVO(contactVO);
				proxyVO.setItNew(true);
				proxyVO.setItDirty(false);
			
		if (sections != null && sections.length > 0) {
			for (XmlObject section : sections) {
				if (((POCDMT000040Section) section).getCode() != null
						&& ((POCDMT000040Section) section).getCode().getCode() != null
						&& ((POCDMT000040Section) section).getCode().getCode()
								.equals(EdxCDAConstants.EXPOSURE_INFO_CD)) {/* contact record section*/
					
					POCDMT000040Entry[] entries = ((POCDMT000040Section) section)
							.getEntryArray();
					
						populateProxyVOFromEntries(informationDT, entries, proxyVO,
									nbsSecurityObj);
					
						if (((POCDMT000040Section) section).getId()!=null && ((POCDMT000040Section) section).getId().getExtension() != null) {
							ArrayList<Object> ids = new ArrayList<Object>();
							ids.add(((POCDMT000040Section) section).getId().getExtension());
							proxyVO.getcTContactVO().
									setEdxEventProcessDTCollection(XMLTypeToNBSObject
											.prepareEventProcessListForNonDocEvents(ids, informationDT,
													NEDSSConstants.CLASS_CD_CONTACT));
						}
					}
				else if(((POCDMT000040Section) section).getCode() != null/* for section containing the contact patient related data*/
						&& ((POCDMT000040Section) section).getCode()
						.getCode() != null
				&& ((POCDMT000040Section) section).getCode()
						.getCode().equals(EdxCDAConstants.PATIENT_INFO_CD)){
					POCDMT000040Entry[] entries = ((POCDMT000040Section) section)
							.getEntryArray();
					
						populateProxyVOFromEntries(informationDT, entries, proxyVO,
									nbsSecurityObj);
				}
				
					else if(((POCDMT000040Section) section).getCode() != null/* for section containing the interested parties(Entities related to case)*/
							&& ((POCDMT000040Section) section).getCode()
									.getCode() != null
							&& ((POCDMT000040Section) section).getCode()
									.getCode().equals(EdxCDAConstants.INTERESTED_PARTY_CD)){
						CDAEntityProcessor.populateEntitiesFromEntries(((POCDMT000040Section) section)
								.getEntryArray(), (HashMap<Object, Object>)dataLocationToBeanMap.get(RenderConstants.INTERESTED_PARTIES));
					}
				}
				ClinicalDocumentDocument1 rootDoc  = ((ClinicalDocumentDocument1)informationDT.getRuleAlgorothmManagerDT().getDocumentDT().getDocumentObject());
				/*Extract patient data and set it in proxy*/
				PersonVO patientVO = CDAEntityProcessor.getPatient(rootDoc
						.getClinicalDocument().getRecordTargetArray(),
						informationDT,  CDAXMLTypeToNBSObject.getSocialHistorySection(rootDoc.getClinicalDocument()));
				if(edxProcessCaseSummaryForContact!=null)
					patientVO.getThePersonDT().setPersonParentUid(edxProcessCaseSummaryForContact.getPersonParentUid());
				else
					patientVO.getThePersonDT().setPersonParentUid(informationDT.getRuleAlgorothmManagerDT().getMPRUid());
				
				proxyVO.setContactPersonVO(patientVO);	
				
				/*create act entity relationship for patient*/
				EdxPersonEntityProcessor.setActEntityForContactRecord(proxyVO,
					NEDSSConstants.CONTACT_ENTITY,
					patientVO.getThePersonDT().getPersonUid());

				/*create  act entities relationship for remaining entities other than patient*/
				CDAEntityProcessor.createActEntityandParticipations(
						proxyVO,
						(HashMap<Object, Object>) dataLocationToBeanMap
								.get(RenderConstants.PARTICIPANT),
						(HashMap<Object, Object>) dataLocationToBeanMap
								.get(RenderConstants.INTERESTED_PARTIES),
						informationDT, NEDSSConstants.CLASS_CD_CONTACT,
						nbsSecurityObj);

				createActRelationshipForContactRecordDoc(informationDT
						.getRuleAlgorothmManagerDT().getDocumentDT()
						.getNbsDocumentUid(), proxyVO.getcTContactVO());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new NEDSSAppException(
					"CDAContactRecordProcessor.Exception :"+ex.getMessage(), ex);
		}
		return proxyVO;
	}
	

	@SuppressWarnings("unchecked")
	public static void populateProxyVOFromEntries(EdxCDAInformationDT informationDT,
			POCDMT000040Entry[] entries, CTContactProxyVO proxyVO, NBSSecurityObj nbsSecurityObj)
			throws NEDSSAppException {
		try{
			/*extract data from CDA entries and populate ContactVO*/
			populateContactRecordValuesFromEntries(
					CDAProcessorUtil.loadQuestions(informationDT.getRuleAlgorothmManagerDT().getDocumentDT().getCd(),
							NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE),
							entries, proxyVO.getcTContactVO().getcTContactDT(),
					nbsSecurityObj);
			/*Set contact answer map to proxy */
			proxyVO.getcTContactVO().setCtContactAnswerDTMap(
					(HashMap<Object, Object>) dataLocationToBeanMap
							.get(RenderConstants.CT_CONTACT_ANSWER));
		} catch (NumberFormatException e) {
			throw new NEDSSAppException(
					"CDAContactRecordProcessor.NumberFormatException :"
							+ e.getMessage(), e);
		} catch (Exception e) {
			String errorString = "Exception raised while populating contactproxyVO from PHDC "+e.getMessage();
			logger.error(errorString);
			throw new NEDSSAppException(errorString, e);
		}
	}

	public static void setStandardNBSAnswerVals(CTContactDT contactDT,
			NbsAnswerDT nbsAnswerDT) {

		nbsAnswerDT.setActUid(contactDT.getCtContactUid());
		nbsAnswerDT.setAddTime(contactDT.getAddTime());
		nbsAnswerDT.setLastChgTime(contactDT.getLastChgTime());
		nbsAnswerDT.setAddUserId(contactDT.getAddUserId());
		nbsAnswerDT.setLastChgUserId(contactDT.getLastChgUserId());
		nbsAnswerDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
		if (nbsAnswerDT.getSeqNbr() != null
				&& nbsAnswerDT.getSeqNbr().intValue() < 0)
			nbsAnswerDT.setSeqNbr(new Integer(0));
		nbsAnswerDT.setRecordStatusTime(contactDT.getRecordStatusTime());
		nbsAnswerDT.setItNew(true);
	}

	public static void prepareActRelationship(ActRelationshipDT actDoc,
			Long targetActUid, Long sourceActUid, String sourceClassCd,
			String typeCd) {
		actDoc.setItNew(true);
		actDoc.setSourceActUid(sourceActUid);
		actDoc.setSourceClassCd(sourceClassCd);
		actDoc.setStatusCd(NEDSSConstants.STATUS_ACTIVE); 
		actDoc.setTargetActUid(targetActUid);
		actDoc.setTargetClassCd(NEDSSConstants.CASE);
		actDoc.setRecordStatusCd(NEDSSConstants.ACTIVE);
		actDoc.setTypeCd(typeCd);
		actDoc.setItDirty(false);
	}

	@SuppressWarnings("unchecked")
	public static void populateContactRecordValuesFromEntries(
			Map<Object, Object> questionMap, POCDMT000040Entry[] entries,
			CTContactDT ctContactDT, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		String processingQid = null;
		if(questionMap == null){
			throw new NEDSSSystemException("Question cache for contact record is null, cannot create contact record from PHDC document" );
		}
		try {
				if (entries != null) {  
					for(POCDMT000040Entry entry:entries){
						if(entry.getObservation()!=null && entry.getObservation().getCode()!=null){
							processingQid = entry.getObservation().getCode().getCode();
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
								if(valueArray==null) {
									//do nothing 
								}
								else if(valueArray!=null && multi){// for multi-select questions
									colvals = new String[valueArray.length];
									for (int j = 0;j<colvals.length;j++){
								        if(valueArray[j] instanceof CE)
								        	colvals[j]=XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), ((CE) valueArray[j]).getCode(), dt.getCodeSetClassCd()); 
								        else if(valueArray[j] instanceof ST)
								        	colvals[j]=((ST) valueArray[j]).newCursor().getTextValue();
								    }
								}
								else if (valueArray[0] instanceof CE && !multi) //coded questions
									colVal = XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), ((CE) valueArray[0]).getCode(), dt.getCodeSetClassCd()); 
								
								else if (valueArray[0] instanceof TS && !multi) //date time questions
									colVal = CDAXMLTypeToNBSObject
											.parseStringDate(((TS) valueArray[0])
													.getValue());
								
								else if (valueArray[0] instanceof ST && !multi) //Text type questions
									colVal = ((ST) valueArray[0]).newCursor().getTextValue();
								
								else if (valueArray[0] instanceof II && !multi){ //ID Type Questions
									colVal = ((II)valueArray[0]).getExtension();
									if(dataLoc.startsWith(RenderConstants.PERSON) || dataLoc.startsWith(RenderConstants.ORGANIZATION) ){//these are participants
										ParticipationTypeVO participationType = CDAEntityProcessor.getParticipationTypeByQidAndEventType(dt.getQuestionIdentifier(), NEDSSConstants.CLASS_CD_CONTACT);
									    String code = "";
									    if(participationType!=null && participationType.getSubjectClassCd()!=null)
									    	code = participationType.getSubjectClassCd();
										((HashMap<Object, Object>) dataLocationToBeanMap
												.get(RenderConstants.PARTICIPANT))
												.put(dt.getQuestionIdentifier(),
														colVal+code);
									}
								}
								if ((colVal != null || colvals!=null)  //for CT_CONTACT data
										&& colNm != null && dataLocationToBeanMap.get(st[0].toUpperCase()) != null
										&& !dataLoc.contains(RenderConstants.CT_CONTACT_ANSWER)
										&& dataLocationToBeanMap.get(st[0].toUpperCase()) != null) {
									DynamicBeanBinding.populateBean(dataLocationToBeanMap.get(st[0].toUpperCase()),	colNm, colVal);
									
								} else if ((colVal != null || colvals!=null)  //for CT_CONTACT_ANSWER data
										&& colNm != null && dataLocationToBeanMap.get(st[0].toUpperCase()) != null
										&& dataLoc.contains(RenderConstants.CT_CONTACT_ANSWER)) {
									if(colVal != null ){ //set answerDT for single entry questions
									CTContactAnswerDT answerDT = new CTContactAnswerDT();
									answerDT.setAnswerTxt(colVal);
									setStandardNBSAnswerVals(ctContactDT,	answerDT);
									answerDT.setNbsQuestionUid(dt.getNbsQuestionUid());
									answerDT.setSeqNbr(0);
									((HashMap<Object, Object>) dataLocationToBeanMap
											.get(RenderConstants.CT_CONTACT_ANSWER))
											.put(dt.getQuestionIdentifier(),answerDT);
									
								}else if(colvals != null){ //set answerDT for multi-select questions
									int i=0;
									ArrayList<Object> answerDTList = new ArrayList<Object>();
									for(String value: colvals){
											NbsCaseAnswerDT answerDT = new NbsCaseAnswerDT();
											answerDT.setAnswerTxt(value);
											setStandardNBSAnswerVals(ctContactDT,	answerDT);
											answerDT.setNbsQuestionUid(dt.getNbsQuestionUid());
												answerDT.setSeqNbr(i + 1);
											answerDTList.add(answerDT);
											i++;
										}
									((HashMap<Object, Object>) dataLocationToBeanMap
											.get(RenderConstants.CT_CONTACT_ANSWER))
											.put(dt.getQuestionIdentifier(), answerDTList);
									}
								}
							}
						}
					}
				  }
				 }
				}
		} catch (Exception e) {
			String error = "Error in populateContactRecordValuesFromEntries : Question Identifier: "+processingQid+" "+e.getMessage() ;
			throw new NEDSSAppException(error, e); 
		}
	}
	
	public static void setupDocumentForContactRecord(
			NBSDocumentVO nbsDocumentVO, POCDMT000040Section section)
			throws NEDSSSystemException {
		try {
				for( POCDMT000040Entry entry : section.getEntryArray()){
					ArrayList<EDXEventProcessCaseSummaryDT> eventProcessCaseSummaryList = null;
					String relationshipType = null;
					if(entry.getAct()!=null && entry.getAct().getCode()!=null && entry.getAct().getCode().getCode()!=null)
						relationshipType = entry.getAct().getCode().getCode();
					NbsDocumentDAOImpl nbsDocumentDAOImpl = new NbsDocumentDAOImpl();
					if (relationshipType!=null && entry.getAct().getReferenceArray()!=null 
							&& entry.getAct().getReferenceArray(0).getExternalDocument() != null
							&& entry.getAct().getReferenceArray(0).getExternalDocument().getIdArray() != null
							&& entry.getAct().getReferenceArray(0).getExternalDocument().getIdArray(0).getExtension() != null) {
						
						String sourceEventId  = entry.getAct().getReferenceArray(0).getExternalDocument().getIdArray(0).getExtension();
						if (sourceEventId != null) {
							eventProcessCaseSummaryList = nbsDocumentDAOImpl.getEDXEventProcessCaseSummaryByEventId(sourceEventId);
							if (eventProcessCaseSummaryList != null && eventProcessCaseSummaryList.size()>0) {
								if (nbsDocumentVO.geteDXEventProcessCaseSummaryDTMap() == null)
									nbsDocumentVO.seteDXEventProcessCaseSummaryDTMap(new HashMap<String, EDXEventProcessCaseSummaryDT>());
								nbsDocumentVO.geteDXEventProcessCaseSummaryDTMap().put(relationshipType, eventProcessCaseSummaryList	.get(0));
							}
						}
					
						if (relationshipType!=null && relationshipType.equals( EdxCDAConstants.SUBJECT_INV) 
								&& entry.getAct().getEntryRelationshipArray() !=null && entry.getAct().getEntryRelationshipArray().length>0 && entry.getAct().getEntryRelationshipArray(0).getAct() != null
								&& entry.getAct().getEntryRelationshipArray(0).getAct().getReferenceArray()!=null 
								&& entry.getAct().getEntryRelationshipArray(0).getAct().getReferenceArray(0).getExternalAct() != null
								&& entry.getAct().getEntryRelationshipArray(0).getAct().getReferenceArray(0).getExternalAct().getIdArray() != null
								&& entry.getAct().getEntryRelationshipArray(0).getAct().getReferenceArray(0).getExternalAct().getIdArray(0).getExtension() != null) {
							
							String interviewId = entry.getAct().getEntryRelationshipArray(0).getAct().getReferenceArray(0).getExternalAct().getIdArray(0).getExtension();
							EDXEventProcessDT eventProcessDT = nbsDocumentDAOImpl.getEDXEventProcessDTBySourceIdandEventType(interviewId, NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE);
							if(eventProcessDT!=null){
								EDXEventProcessCaseSummaryDT processDT  = new EDXEventProcessCaseSummaryDT();
								processDT.setNbsEventUid(eventProcessDT.getNbsEventUid());
								processDT.setSourceEventId(eventProcessDT.getSourceEventId());
								processDT.setDocEventTypeCd(eventProcessDT.getDocEventTypeCd());
								nbsDocumentVO.geteDXEventProcessCaseSummaryDTMap()
								.put(EdxCDAConstants.NAMED_IN_INTEVIEW,processDT);
							}
						}
					}
			}
		} catch (IndexOutOfBoundsException ex) {
			String errString = "Named in interview does not exists"
					+ ex.getMessage();
			logger.error(errString);
		}
		catch (Exception e) {
			String errString = "Exception while preparing the document for contact record  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
	}

	
	private static void createActRelationshipForContactRecordDoc(Long documentUid,
			CTContactVO contactVO) {
		ActRelationshipDT actDoc = new ActRelationshipDT();
		CDAXMLTypeToNBSObject.prepareActRelationship(actDoc, contactVO
				.getcTContactDT().getCtContactUid(), NEDSSConstants.CLASS_CD_CONTACT, documentUid,
				NEDSSConstants.ACT_CLASS_CD_FOR_DOC, NEDSSConstants.DocToCON);
		if (contactVO.getTheActRelationshipDTCollection() == null) {
			Collection<ActRelationshipDT> coll = new ArrayList<ActRelationshipDT>();
			coll.add(actDoc);
			contactVO.setTheActRelationshipDTCollection(coll);
		}
	}

}