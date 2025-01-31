package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import gov.cdc.nedss.act.interview.dt.InterviewDT;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.phdc.cda.ANY;
import gov.cdc.nedss.phdc.cda.CE;
import gov.cdc.nedss.phdc.cda.II;
import gov.cdc.nedss.phdc.cda.POCDMT000040Author;
import gov.cdc.nedss.phdc.cda.POCDMT000040Component4;
import gov.cdc.nedss.phdc.cda.POCDMT000040Entry;
import gov.cdc.nedss.phdc.cda.POCDMT000040EntryRelationship;
import gov.cdc.nedss.phdc.cda.POCDMT000040Participant2;
import gov.cdc.nedss.phdc.cda.ST;
import gov.cdc.nedss.phdc.cda.TS;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxCDAInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPersonEntityProcessor;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.CDAXMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.RenderConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;

public class CDAInterviewProcessor {
	static final LogUtils logger = new LogUtils(CDAInterviewProcessor.class.getName());
	public static Map<Object, Object> beanMethodMap = new HashMap<Object, Object>();
	public static Map<Object, Object> dataLocationToBeanMap = new HashMap<Object, Object>();
	public static String lineSeperator = System.getProperty("line.seperator");
	public static void initializeProcessor(){
		dataLocationToBeanMap.put(RenderConstants.INTERVIEW,
				new InterviewDT());
		dataLocationToBeanMap.put(RenderConstants.PARTICIPANT,
				new HashMap<Object, Object>());
		dataLocationToBeanMap.put(RenderConstants.INTERESTED_PARTIES,
				new HashMap<Object, Object>());
		dataLocationToBeanMap.put(RenderConstants.NBS_ANSWER,
				new HashMap<Object, Object>());
		dataLocationToBeanMap.put(RenderConstants.REPEATING_NBS_ANSWER,
				new HashMap<Object, Object>());
	}


	@SuppressWarnings("unchecked")
	public static Object createProxyVO(EdxCDAInformationDT informationDT,
			POCDMT000040Entry encounterEntry, NBSSecurityObj nbsSecurityObj)
			throws NEDSSSystemException {
		Object obj = null;
		PageActProxyVO pageActProxyVO = null;
		Long UserId = new Long(nbsSecurityObj.getTheUserProfile().getTheUser()
				.getEntryID());
		int j = 0;

		try {
			InterviewVO interviewVO = new InterviewVO();
			interviewVO.setTheInterviewDT((InterviewDT) dataLocationToBeanMap
					.get(RenderConstants.INTERVIEW));
			interviewVO.getTheInterviewDT().setInterviewUid(new Long(--j));
			interviewVO.getTheInterviewDT().setAddTime(
					informationDT.getRuleAlgorothmManagerDT().getLastChgTime());
			interviewVO.getTheInterviewDT().setLastChgTime(
					informationDT.getRuleAlgorothmManagerDT().getLastChgTime());
			interviewVO.getTheInterviewDT().setAddUserId(UserId);
			interviewVO.getTheInterviewDT().setLastChgUserId(UserId);
			interviewVO.getTheInterviewDT().setRecordStatusCd(NEDSSConstants.ACTIVE);
			interviewVO.getTheInterviewDT().setRecordStatusTime(
					informationDT.getRuleAlgorothmManagerDT().getLastChgTime());
			interviewVO.setItNew(true);
			interviewVO.setItDirty(false);
			interviewVO.getTheInterviewDT().setItNew(true);
			interviewVO.getTheInterviewDT().setItDirty(false);

			pageActProxyVO = new PageActProxyVO();
			pageActProxyVO.setInterviewVO(interviewVO);
			pageActProxyVO.setItNew(true);
			pageActProxyVO.setItDirty(false);
			obj = pageActProxyVO;

			populateInterviewValuesFromEntries(
					CDAProcessorUtil.loadQuestions(informationDT.getRuleAlgorothmManagerDT().getDocumentDT().getCd(),
							NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE),
					encounterEntry, interviewVO.getTheInterviewDT(),
					nbsSecurityObj);
			
			createActRelationshipForInterview(informationDT.getRuleAlgorothmManagerDT().getPHCUid(), pageActProxyVO);

			pageActProxyVO.getPageVO().setAnswerDTMap(
					(HashMap<Object, Object>) dataLocationToBeanMap
							.get(RenderConstants.NBS_ANSWER));

			CDAEntityProcessor
					.createActEntityandParticipations(pageActProxyVO,
							(HashMap<Object, Object>) dataLocationToBeanMap
									.get(RenderConstants.PARTICIPANT),
							(HashMap<Object, Object>) dataLocationToBeanMap
									.get(RenderConstants.INTERESTED_PARTIES),
							informationDT, NEDSSConstants.IXS_SOURCE_CD,
							nbsSecurityObj);
			// Act Entity relationship and participation for Interviewee
			EdxPersonEntityProcessor.setActEntityForCreate(pageActProxyVO,
					null, NEDSSConstants.SBJ_INTERVIEW, new Long(informationDT
							.getRuleAlgorothmManagerDT().getPHCRevisionUid()),
					NEDSSConstants.PERSON);
			
			//need to process the repeating section in the last
			if(encounterEntry.getEncounter()!=null && encounterEntry.getEncounter().getEntryRelationshipArray()!=null && encounterEntry.getEncounter().getEntryRelationshipArray().length>0){
				POCDMT000040EntryRelationship[] entries = encounterEntry.getEncounter().getEntryRelationshipArray();
						populateInterviewRepeatingQuestionsFromEntryRelationships(
						CDAProcessorUtil.loadQuestions(informationDT.getRuleAlgorothmManagerDT().getDocumentDT().getCd(),NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE), entries,
								interviewVO.getTheInterviewDT(), null,
						nbsSecurityObj);
			}
			
			pageActProxyVO.getPageVO().setPageRepeatingAnswerDTMap(
					(HashMap<Object, Object>) dataLocationToBeanMap
							.get(RenderConstants.REPEATING_NBS_ANSWER));

		} catch (Exception e) {
			String errorString = " Exception while creating interview Proxy VO from PHDC "+e.getMessage();
			logger.error(errorString);
			e.printStackTrace();
			throw new NEDSSSystemException(errorString, e);
		}
		return obj;
	}

	public static void setStandardNBSAnswerVals(InterviewDT interviewDT,
			NbsAnswerDT nbsAnswerDT) {
		try {
			nbsAnswerDT.setActUid(interviewDT.getInterviewUid());
			nbsAnswerDT.setAddTime(interviewDT.getAddTime());
			nbsAnswerDT.setLastChgTime(interviewDT.getLastChgTime());
			nbsAnswerDT.setAddUserId(interviewDT.getAddUserId());
			nbsAnswerDT.setLastChgUserId(interviewDT.getLastChgUserId());
			nbsAnswerDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			if (nbsAnswerDT.getSeqNbr() != null
					&& nbsAnswerDT.getSeqNbr().intValue() < 0)
				nbsAnswerDT.setSeqNbr(new Integer(0));
			nbsAnswerDT.setRecordStatusTime(interviewDT.getRecordStatusTime());
			nbsAnswerDT.setItNew(true);
		} catch (Exception e) {
			String errorString = " Exception while setting up standard answer data for PHDC "
					+ e.getMessage();
			logger.error(errorString);
			e.printStackTrace();
			throw new NEDSSSystemException(errorString, e);
		}
	}

	public static void createActRelationshipForInterview(Long caseUid,
			PageActProxyVO proxyVO) throws NEDSSSystemException{
		try{
		ActRelationshipDT actRelDT = new ActRelationshipDT();
		prepareActRelationship(actRelDT, caseUid, proxyVO.getInterviewVO()
				.getTheInterviewDT().getInterviewUid(),
				NEDSSConstants.CLASS_CD_OBS,
				NEDSSConstants.INTERVIEW_CLASS_CODE);
		if (((PageActProxyVO) proxyVO).getTheActRelationshipDTCollection() == null) {
			Collection<Object> coll = new ArrayList<Object>();
			coll.add(actRelDT);
			((PageActProxyVO) proxyVO).setTheActRelationshipDTCollection(coll);
		} else {
			((PageActProxyVO) proxyVO).getTheActRelationshipDTCollection().add(
					actRelDT);
		}
		}catch(Exception e){
			String errorString = " Exception while creating act relationship for interview record "
					+ e.getMessage();
			logger.error(errorString);
			e.printStackTrace();
			throw new NEDSSSystemException(errorString, e);
		}

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
	public static void populateInterviewValuesFromEntries(
			Map<Object, Object> questionMap, POCDMT000040Entry entry,
			InterviewDT interviewDT, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
		String processingQid = null;
		try {
				if (entry.getEncounter() != null) {  
					interviewDT.setInterviewDate(CDAXMLTypeToNBSObject
							.parseDateAsTimestamp(entry.getEncounter().getEffectiveTime()));
					interviewDT.setInterviewStatusCd(entry.getEncounter().getStatusCode().getCode());
					populateEntitiesFromParticipants(entry.getEncounter().getParticipantArray());
					POCDMT000040EntryRelationship[] entryRelationshipArray = entry.getEncounter().getEntryRelationshipArray();
					for(POCDMT000040EntryRelationship entryRelationship:entryRelationshipArray){
						if(entryRelationship.getObservation()!=null && entryRelationship.getObservation().getCode()!=null){
						processingQid = entryRelationship.getObservation().getCode().getCode();
					NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap
							.get(entryRelationship.getObservation().getCode().getCode());
					if (dt != null) { //make sure meta data exists for the question identifier code
						String dataLoc = dt.getDataLocation() == null ? "" : dt
								.getDataLocation();
						if (dataLoc != null) {//Check for data location 
							String[] st = dataLoc.split("\\.");
							if (st != null && st.length > 1) {
								String colNm = st[1];
								String colVal = null; //single entry data
								String[] colvals = null; //multi-select data
								ANY[] valueArray = entryRelationship.getObservation()
										.getValueArray();
								
								boolean multi = false;
								if(dt.getNbsUiComponentUid() != null
										&& (dt.getNbsUiComponentUid()
												.compareTo(NEDSSConstants.MULTI_SELECT_CD) == 0))
									multi=true;
								if(valueArray==null){
									continue;
								}else if(valueArray.length>0 && multi){// for multi-select questions
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
								}
								
								if ((colVal != null || colvals!=null)  //for non NBS_CASE_ANSWER data
										&& colNm != null && dataLocationToBeanMap.get(st[0].toUpperCase()) != null
										&& !dataLoc.contains(RenderConstants.NBS_ANSWER)
										&& dataLocationToBeanMap.get(st[0].toUpperCase()) != null) {
									DynamicBeanBinding.populateBean(dataLocationToBeanMap.get(st[0].toUpperCase()),	colNm, colVal);
									
								} else if ((colVal != null || colvals!=null)  //for NBS_CASE_ANSWER data
										&& colNm != null && dataLocationToBeanMap.get(st[0].toUpperCase()) != null
										&& dataLoc.contains(RenderConstants.NBS_ANSWER)) {
									if(colVal != null ){ //set answerDT for single entry questions
									NbsAnswerDT answerDT = new NbsAnswerDT();
									answerDT.setAnswerTxt(colVal);
									setStandardNBSAnswerVals(interviewDT,	answerDT);
									answerDT.setNbsQuestionUid(dt.getNbsQuestionUid());
									answerDT.setSeqNbr(0);
									((HashMap<Object, Object>) dataLocationToBeanMap
											.get(RenderConstants.NBS_ANSWER))
											.put(dt.getQuestionIdentifier(),answerDT);
									
								}else if(colvals != null){ //set answerDT for multi-select questions
									int i=0;
									ArrayList<Object> caseAnswerDTList = new ArrayList<Object>();
									for(String value: colvals){
											NbsCaseAnswerDT answerDT = new NbsCaseAnswerDT();
											answerDT.setAnswerTxt(value);
											setStandardNBSAnswerVals(interviewDT,	answerDT);
											answerDT.setNbsQuestionUid(dt.getNbsQuestionUid());
											answerDT.setSeqNbr(i + 1);
											caseAnswerDTList.add(answerDT);
											i++;
										}
									((HashMap<Object, Object>) dataLocationToBeanMap
											.get(RenderConstants.NBS_ANSWER))
											.put(dt.getQuestionIdentifier(), caseAnswerDTList);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			String error = "Exception raised in populateInterviewValuesFromEntries: Question Identifier: "+processingQid+" "+ e.getMessage();
			e.printStackTrace();
			throw new NEDSSSystemException(error,	e);
		}
	}
					
	@SuppressWarnings("unchecked")
	public static void populateInterviewRepeatingQuestionsFromEntryRelationships(
			Map<Object, Object> questionMap, POCDMT000040EntryRelationship[] entries,
			InterviewDT ixsDT, HashMap<Object, Object> legacyToNBSEntityUidMap, NBSSecurityObj nbsSecurityObj)
			throws NEDSSSystemException {
		String processingQid = null;
		try {
			HashMap<Object, Object> organizedEntities = CDAProcessorUtil.organizeRepeatingQuestionsInMapFromEntryRelationship(entries);
			if(organizedEntities!=null && organizedEntities.size()>0){
		    for(Object value: organizedEntities.values()){
		    	processingQid = null;
		    	int i = 1;
		    	Collection<POCDMT000040EntryRelationship> repeatingEntries =(Collection<POCDMT000040EntryRelationship>)value;
		    	for(POCDMT000040EntryRelationship entry: repeatingEntries){
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
								if(valueArray!=null && valueArray.length>0 && valueArray[0] instanceof CE && !multiselect)// coded questions
									createRepeatingQuestionMap(
											createRepeatingAnswerDT(XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), ((CE)valueArray[0]).getCode(), dt.getCodeSetClassCd()), ixsDT, dt,	nbsSecurityObj, 0, i));
								else if (valueArray!=null && valueArray.length>0 && valueArray[0] instanceof TS && !multiselect) //date time questions
									createRepeatingQuestionMap(
											createRepeatingAnswerDT(CDAXMLTypeToNBSObject
													.parseStringDate(((TS)valueArray[0]).getValue()), ixsDT, dt,	nbsSecurityObj, 0, i));
								else if (valueArray!=null && valueArray.length>0 && valueArray[0] instanceof ST && !multiselect){ //Text type questions
									if (dt.getNbsUiComponentUid() != null
											&& dt.getNbsUiComponentUid()
													.compareTo(
															NEDSSConstants.MULTI_LINE_NOTES_W_USER_DATE_STAMP_CD) == 0) {
										createRepeatingQuestionMap(
												createRepeatingAnswerDTForUserNotes(((ST)valueArray[0]).newCursor().getTextValue(), ixsDT, dt,	nbsSecurityObj, 0, i, notesTimestamp,notesProviderId));
									}else
									createRepeatingQuestionMap(
											createRepeatingAnswerDT(((ST) valueArray[0]).newCursor().getTextValue(), ixsDT, dt,	nbsSecurityObj, 0, i));
								
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
											createRepeatingAnswerDT(newId==null?legacyId:newId, ixsDT, dt,	nbsSecurityObj, 0, i));
								}
								else if(valueArray!=null && valueArray.length>0 && valueArray[0] instanceof CE && multiselect){ //set answerDT for multi-select questions
									int j=1;
									for(ANY ansValue: valueArray){
										createRepeatingQuestionMap(createRepeatingAnswerDT(XMLTypeToNBSObject.getNBSCodeFromPHINCodes(dt.getCodeSetNm(), ((CE)ansValue).getCode(), dt.getCodeSetClassCd()), ixsDT, dt,	nbsSecurityObj, j++, i));
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
			String error = "Exception in populateInterviewRepeatingQuestionsFromEntryRelationships: "+processingQid +lineSeperator+ex.getMessage();
			throw new NEDSSSystemException(error,ex);
		}

	}

	
	@SuppressWarnings("unchecked")
	public static void populateEntitiesFromParticipants(
			POCDMT000040Participant2[] participantArray)
			throws NEDSSSystemException {
		try {
			if (participantArray != null) {
				for (POCDMT000040Participant2 participant : participantArray) {
					// create a Map with participant ID and participant
					II[] idArrays = participant.getParticipantRole()
							.getIdArray();
					if (idArrays != null && idArrays.length > 0) {
						for (II id : idArrays) {
							if (id != null
									&& id.getAssigningAuthorityName() != null
									&& id.getAssigningAuthorityName()
											.equals(NEDSSConstants.PHCR_LOCAL_REGISTRY_ID)) {
								((HashMap<Object, Object>) dataLocationToBeanMap
										.get(RenderConstants.INTERESTED_PARTIES))
										.put(id.getExtension(), participant
												.getParticipantRole());
								if (participant.getParticipantRole().getCode() != null)
									((HashMap<Object, Object>) dataLocationToBeanMap
											.get(RenderConstants.PARTICIPANT))
											.put(participant
													.getParticipantRole()
													.getCode().getCode(),
													id.getExtension());
							}
						}
					}
				}
			}

		} catch (Exception e) {
			String error = "Exception in populateEntitiesFromParticipants for Interviews "+e.getMessage();
			e.printStackTrace();
			throw new NEDSSSystemException(error,	e);
		}
	}
	
	public static NbsAnswerDT createRepeatingAnswerDT(String value,
			InterviewDT ixsDT, NbsQuestionMetadata dt,
			NBSSecurityObj nbsSecurityObj, int seqNo, int groupSeqNo)
			throws NEDSSSystemException {
		if(value == null)
			return null;
		NbsAnswerDT answerDT = new NbsCaseAnswerDT();
		try {
			answerDT.setAnswerTxt(value);
			setStandardNBSCaseAnswerVals(ixsDT, answerDT);
			answerDT.setNbsQuestionUid(dt.getNbsQuestionUid());
			answerDT.setSeqNbr(seqNo);
			answerDT.setAnswerGroupSeqNbr(groupSeqNo);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception in createRepeatingAnswerDTs:");
			throw new NEDSSSystemException(
					"createRepeatingAnswerDTs: Error while creating repeated answers, exception is raised: "+ex.getMessage(),
					ex);
		}
		return answerDT;
	}
	
	public static NbsAnswerDT createRepeatingAnswerDTForUserNotes(String value,
			InterviewDT ixsDT, NbsQuestionMetadata dt,
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
			 notesDate = StringUtils.formatDatewithHrMinSec(ixsDT.getAddTime());
		NbsAnswerDT answerDT = new NbsAnswerDT();
		try {
			answerDT.setAnswerTxt(value);
			setStandardNBSCaseAnswerVals(ixsDT, answerDT);
			answerDT.setNbsQuestionUid(dt.getNbsQuestionUid());
			answerDT.setSeqNbr(seqNo);
			answerDT.setAnswerGroupSeqNbr(groupSeqNo);
			answerDT.setAnswerTxt(userName+"~"+notesDate+"~~"+value);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception in createRepeatingAnswerDTForUserNotes:");
			throw new NEDSSSystemException(
					"createRepeatingAnswerDTForUserNotes: Error while creating repeated answers, exception is raised: "+ex.getMessage(),
					ex);
		}
		return answerDT;
	}
	
	public static void setStandardNBSCaseAnswerVals(InterviewDT ixsDT,
			NbsAnswerDT nbsAnswerDT) {
		try {
			nbsAnswerDT.setActUid(ixsDT.getInterviewUid());
			nbsAnswerDT.setAddTime(ixsDT.getAddTime());
			nbsAnswerDT.setLastChgTime(ixsDT.getLastChgTime());
			nbsAnswerDT.setAddUserId(ixsDT.getAddUserId());
			nbsAnswerDT.setLastChgUserId(ixsDT.getLastChgUserId());
			nbsAnswerDT
					.setRecordStatusCd(NEDSSConstants.ACTIVE);
			if (nbsAnswerDT.getSeqNbr() != null
					&& nbsAnswerDT.getSeqNbr().intValue() < 0)
				nbsAnswerDT.setSeqNbr(new Integer(0));
			nbsAnswerDT.setRecordStatusTime(ixsDT.getRecordStatusTime());
			nbsAnswerDT.setItNew(true);
		} catch (Exception ex) {
			String errorString = "Exception occured while setting standard values for NBS Answer DT. "+ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSSystemException(errorString,ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void createRepeatingQuestionMap(NbsAnswerDT answerDT)
			throws NEDSSSystemException {
		try {
			if(answerDT==null)
				return;
			if (((HashMap<Object, Object>) dataLocationToBeanMap
					.get(RenderConstants.REPEATING_NBS_ANSWER))
					.get(answerDT.getNbsQuestionUid()) == null) {
				Collection<Object> collection = new ArrayList<Object>();
				collection.add(answerDT);
				((HashMap<Object, Object>) dataLocationToBeanMap
						.get(RenderConstants.REPEATING_NBS_ANSWER)).put(
						answerDT.getNbsQuestionUid(), collection);
			} else {
				Collection<Object> collection = (Collection<Object>) ((HashMap<Object, Object>) dataLocationToBeanMap
						.get(RenderConstants.REPEATING_NBS_ANSWER))
						.get(answerDT.getNbsQuestionUid());
				collection.add(answerDT);
				((HashMap<Object, Object>) dataLocationToBeanMap
						.get(RenderConstants.REPEATING_NBS_ANSWER)).put(
						answerDT.getNbsQuestionUid(), collection);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception in createRepeatingQuestionMap for Interview:");
			throw new NEDSSSystemException(
					"createRepeatingQuestionMap: Error while creating repeated answers Map for Interview, exception is raised: "+ex.getMessage(),
					ex);
		}
	}
}