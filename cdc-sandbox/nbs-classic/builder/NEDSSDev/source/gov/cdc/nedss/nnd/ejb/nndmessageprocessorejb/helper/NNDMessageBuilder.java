package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.CaseNotificationDataDT;
import gov.cdc.nedss.nnd.dt.CnTransportQOutDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationMessageDAOImpl;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxy;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyHome;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.rmi.PortableRemoteObject;

/**
/**
 * Name: NNDMessageBuilder.java Description: Construct NNDIntermediaryMessage XML message using
 * XMLBeans derived from NNDIntermediaryMessage.xsd and populating with data from CaseNotificationDataDT's 
 * 
 * @author Beau Bannerman
 */
public class NNDMessageBuilder {
	private static LogUtils logger = new LogUtils(NNDMessageBuilder.class.getName());
	private static MessageBuilderHelper messageBuilderHelper = new MessageBuilderHelper();
	
	public void createNotificationMessage(NotificationDT notificationDT,
			Long publicHealthCaseUid, String publicHealthCaseLocalId,
			String nndEntityIdentifier, NBSSecurityObj nbsSecurityObj)
			throws NEDSSConcurrentDataException, NEDSSSystemException {

		// EDX Activity log 
		NotificationMessageDAOImpl notificationMessageDAOImpl = new NotificationMessageDAOImpl();
		Long notificationUid = notificationDT.getNotificationUid();
		
		// Retrieve Participations associated with the current Investigation (via publicHealthCaseUid)
		TreeMap<Object, Object> phcEntityParticipationsMap = notificationMessageDAOImpl.getPublicHealthCaseEntityParticipations(publicHealthCaseUid);

		Collection<Object>  caseDataDTCollection  = null;

		caseDataDTCollection  = notificationMessageDAOImpl.getCaseNotificationDataDTCollection(publicHealthCaseUid, notificationDT);

		messageBuilderHelper.retrieveNonNBSAnswerData(caseDataDTCollection, phcEntityParticipationsMap, publicHealthCaseUid, notificationUid,0);
		
		messageBuilderHelper.checkForRequiredMessageElements(caseDataDTCollection, phcEntityParticipationsMap);

		messageBuilderHelper.codeLookupforMessageData(caseDataDTCollection);
		int numVac = 0;
		if(notificationDT.getVaccineEnableInd()!=null && notificationDT.getVaccineEnableInd().equals(NEDSSConstants.YES))
		 numVac = notificationMessageDAOImpl.getNumberVacAssociatedToInvestigation(publicHealthCaseUid);
		
		/************************Begin: New methods for inserting Vaccination data in the Intermediary message************************/
		
		if (numVac>0 && nndEntityIdentifier != null
				&& !(nndEntityIdentifier.equals("Arbo_Case_Map_v1.0")
						|| nndEntityIdentifier.equals("Gen_Case_Map_v1.0")
						|| nndEntityIdentifier.equals("TB_Case_Map_v2.0")
						|| nndEntityIdentifier.equals("Var_Case_Map_v2.0") 
						|| nndEntityIdentifier.equals("MAL_Case_Map_v1.0"))) {

			// Read data from the nnd_metadata table, nbs_ui_metadata table,
			// etc., for the investigation_form ='VACC_FORM'
			Collection<Object> caseDataDTCollectionVacc = null;
			caseDataDTCollectionVacc = notificationMessageDAOImpl
					.getCaseNotificationDataDTCollectionVacc(
							publicHealthCaseUid, notificationDT);

			// Read data from the view
			Collection<Object> vaccionationDTCollection = null;
			vaccionationDTCollection = notificationMessageDAOImpl
					.retrieveVaccionationViewData(publicHealthCaseUid);

			// Create one caseNotificationDT per each vaccination
			Collection<Object> caseDataDTCollectionVaccWithAnswers = messageBuilderHelper
					.retrieveVaccinationAnswersFromView(
							caseDataDTCollectionVacc, vaccionationDTCollection);

			messageBuilderHelper
					.codeLookupforMessageDataVaccination(caseDataDTCollectionVaccWithAnswers);

			caseDataDTCollection.addAll(caseDataDTCollectionVaccWithAnswers);
		}
		
		/************************End: New methods for inserting Vaccination data in the Intermediary message************************/
		
		
		/************************Begin: New methods for inserting Lab data in the Intermediary message************************/
		logger.info("Beginning new methods for inserting Lab data in the Intermediary message");
		
		logger.info("Executing query for retrieving number of labs associated to the investigation with local id: "+publicHealthCaseLocalId);
		
		int numLabs = 0 ;
		
		if(notificationDT.getLabReportEnableInd()!=null && notificationDT.getLabReportEnableInd().equals(NEDSSConstants.YES))
			numLabs = notificationMessageDAOImpl.getNumberLabsAssociatedToInvestigation(publicHealthCaseUid);
		
		logger.info("The number of labs associated to the investigation is: "+numLabs);
		Collection<Object> caseDataDTCollectionOrder = null;
		Collection<Object> caseDataDTCollectionResult = null;
		Collection<Object> caseDataDTCollectionSusceptility = null;
		Collection<Object> caseDataDTCollectionSusceptilityResult = null;

		logger.info("nndEntityIdentifier: "+nndEntityIdentifier);
			if (numLabs>0 && nndEntityIdentifier != null
					&& !(nndEntityIdentifier.equals("Arbo_Case_Map_v1.0")
							|| nndEntityIdentifier.equals("Gen_Case_Map_v1.0")
							|| nndEntityIdentifier.equals("TB_Case_Map_v2.0")
							|| nndEntityIdentifier.equals("Var_Case_Map_v2.0") 
							|| nndEntityIdentifier.equals("MAL_Case_Map_v1.0"))) {
			logger.info("Reading data from the nnd_metadata and the nbs_ui_metadata tables");
			// Read data from the nnd_metadata table, nbs_ui_metadata table,
			// etc., for LABS
			Collection<Object> caseDataDTCollectionLab = null;
			caseDataDTCollectionLab = notificationMessageDAOImpl.getCaseNotificationDataDTCollectionLab(publicHealthCaseUid, notificationDT);
			
			logger.info("Calling store procedure to retrieve flat data into LAB_EVENT and LAB_EVENT_HIST tables");
			//Retrieve flat lab data in the LAB_EVENT and LAB_EVENT_HIST tables
			notificationMessageDAOImpl.retrieveDataInFlatTableForLabNotification(publicHealthCaseUid);
			
			//Create 3 collections:
			// -Order collection if question_order_nnd = 1
			// -Result collection, question_order_nnd = 2
			// -Susceptibility collection, question_order_nnd = 3
			// -Susceptibility Results collection, question_order_nnd = 4
			logger.info("Creating caseDataDTCollectionOrder for metadata with question_order_nnd: 1");
			caseDataDTCollectionOrder=notificationMessageDAOImpl.retrieveCollectionLab(caseDataDTCollectionLab,1);
			logger.info("Creating caseDataDTCollectionResult for metadata with question_order_nnd: 2");
			caseDataDTCollectionResult=notificationMessageDAOImpl.retrieveCollectionLab(caseDataDTCollectionLab,2);
			logger.info("Creating caseDataDTCollectionSusceptility for metadata with question_order_nnd: 3");
			caseDataDTCollectionSusceptility=notificationMessageDAOImpl.retrieveCollectionLab(caseDataDTCollectionLab,3);
			logger.info("Creating caseDataDTCollectionSusceptilityResult for metadata with question_order_nnd: 4");
			caseDataDTCollectionSusceptilityResult=notificationMessageDAOImpl.retrieveCollectionLab(caseDataDTCollectionLab,4);

			//Get the answer for each of the three collections
			logger.info("Reading NON NBS Answer Data for caseDataDTCollectionOrder");
			messageBuilderHelper.retrieveNonNBSAnswerData(caseDataDTCollectionOrder, phcEntityParticipationsMap, publicHealthCaseUid, notificationUid,1);
			logger.info("Reading NON NBS Answer Data for caseDataDTCollectionResult");
			messageBuilderHelper.retrieveNonNBSAnswerData(caseDataDTCollectionResult, phcEntityParticipationsMap, publicHealthCaseUid, notificationUid,2);
			logger.info("Reading NON NBS Answer Data for caseDataDTCollectionSusceptility");
			messageBuilderHelper.retrieveNonNBSAnswerData(caseDataDTCollectionSusceptility, phcEntityParticipationsMap, publicHealthCaseUid, notificationUid,3);
			logger.info("Reading NON NBS Answer Data for caseDataDTCollectionSusceptilityResult");
			messageBuilderHelper.retrieveNonNBSAnswerData(caseDataDTCollectionSusceptilityResult, phcEntityParticipationsMap, publicHealthCaseUid, notificationUid,4);
			
						
			logger.info("Cloning caseDataDTCollectionOrder for multiselect");
			caseDataDTCollectionOrder = messageBuilderHelper.cloneDataForMultiselect(caseDataDTCollectionOrder);
			logger.info("Cloning caseDataDTCollectionResult for multiselect");
			caseDataDTCollectionResult = messageBuilderHelper.cloneDataForMultiselect(caseDataDTCollectionResult);
			logger.info("Cloning caseDataDTCollectionSusceptility for multiselect");
			caseDataDTCollectionSusceptility = messageBuilderHelper.cloneDataForMultiselect(caseDataDTCollectionSusceptility);
			logger.info("Cloning caseDataDTCollectionSusceptilityResult for multiselect");
			caseDataDTCollectionSusceptilityResult = messageBuilderHelper.cloneDataForMultiselect(caseDataDTCollectionSusceptilityResult);
			
			logger.info("Code look up for caseDataDTCollectionOrder");
			messageBuilderHelper.codeLookupforMessageData(caseDataDTCollectionOrder);
			logger.info("Code look up for caseDataDTCollectionResult");
			messageBuilderHelper.codeLookupforMessageData(caseDataDTCollectionResult);
			logger.info("Code look up for caseDataDTCollectionSusceptility");
			messageBuilderHelper.codeLookupforMessageData(caseDataDTCollectionSusceptility);
			logger.info("Code look up for caseDataDTCollectionSusceptilityResult");
			messageBuilderHelper.codeLookupforMessageData(caseDataDTCollectionSusceptilityResult);
			
			}
			
			logger.info("Finishing new methods for inserting Lab data in the Intermediary message");
			
			/************************End: New methods for inserting Lab data in the Intermediary message************************/
			
			logger.info("Building message");
			String nbsIntermediaryMessageXML = buildMessage(caseDataDTCollection, caseDataDTCollectionOrder, caseDataDTCollectionResult,caseDataDTCollectionSusceptility, caseDataDTCollectionSusceptilityResult, notificationDT, nndEntityIdentifier, publicHealthCaseLocalId, publicHealthCaseUid);
	
			// Write NND message to file and/or CN_Transportq_out table
			messageBuilderHelper.writeNBSIntermediaryMessage(nbsIntermediaryMessageXML, nbsSecurityObj, publicHealthCaseLocalId, notificationDT, NNDConstantUtil.NND_ROOT_DIR);
		
			
		
	}

	private String buildMessage(Collection<Object> caseNotificationDataDTCollection, Collection<Object> caseDataDTCollectionOrder, Collection<Object> caseDataDTCollectionResult,Collection<Object> caseDataDTCollectionSusceptility, Collection<Object> caseDataDTCollectionSusceptilityResult, NotificationDT notificationDT, 
								String nndEntityIdentifier, String publicHealthCaseLocalId, Long publicHealthCaseUid) {
		// Add necessary NND message elements
		addNNDNotificationInfo(caseNotificationDataDTCollection, notificationDT, nndEntityIdentifier);

		// Sort by NND Identifier and QuestionGroupSeqNbr
		List<Object> list = (ArrayList<Object> )caseNotificationDataDTCollection;
		Collections.sort(list, CaseNotificationDataDT.HL7MessageSort);

		messageBuilderHelper.populateUnits(caseNotificationDataDTCollection);
		
		if(caseDataDTCollectionOrder!= null && !caseDataDTCollectionOrder.isEmpty()){
			logger.info("Populating units for caseDataDTCollectionOrder");
			messageBuilderHelper.populateUnitsLab(caseDataDTCollectionOrder);
		}
		if(caseDataDTCollectionResult!= null && !caseDataDTCollectionResult.isEmpty()){
			logger.info("Populating units for caseDataDTCollectionResult");
			messageBuilderHelper.populateUnitsLab(caseDataDTCollectionResult);
		}
		if(caseDataDTCollectionSusceptility!= null && !caseDataDTCollectionSusceptility.isEmpty()){
			logger.info("Populating units for caseDataDTCollectionSusceptility");
			messageBuilderHelper.populateUnitsLab(caseDataDTCollectionSusceptility);
		}
		if(caseDataDTCollectionSusceptilityResult!= null && !caseDataDTCollectionSusceptilityResult.isEmpty()){
			logger.info("Populating units for caseDataDTCollectionSusceptilityResult");
			messageBuilderHelper.populateUnitsLab(caseDataDTCollectionSusceptilityResult);
		}

		return messageBuilderHelper.createNBSNNDIntermediaryMessageXML(caseNotificationDataDTCollection, caseDataDTCollectionOrder, caseDataDTCollectionResult,caseDataDTCollectionSusceptility, caseDataDTCollectionSusceptilityResult);
	}


	private void addNNDNotificationInfo(Collection<Object> caseNotificationDataDTCollection, NotificationDT notificationDT, String nndEntityIdentifier) {

		// Create DT's for notification.add_time and last_chg_time, Result Status and Message Control ID
		CaseNotificationDataDT notAddTime = new CaseNotificationDataDT();
		CaseNotificationDataDT notLastChgTime = new CaseNotificationDataDT();
		CaseNotificationDataDT notResultStatus = new CaseNotificationDataDT();
		CaseNotificationDataDT notMsgControlId = new CaseNotificationDataDT();
		CaseNotificationDataDT notNNDEntityIdentifier1 = new CaseNotificationDataDT();
		CaseNotificationDataDT notNNDEntityIdentifier2 = new CaseNotificationDataDT();

		// MSH 10.0 - Message Control ID - need Notification local id
		notMsgControlId.setHl7SegmentField(NEDSSConstants.NND_HL7_SEGMENT_MSH_10);
		notMsgControlId.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_ST);
		notMsgControlId.setAnswerTxt(notificationDT.getLocalId());
		caseNotificationDataDTCollection.add(notMsgControlId);

		// MSH 21.0 - Entity Identifier - Condition specific
		// Note: MMG v2.0 requires a Message Profile ID (NOT115) like:
		// NND_ORU_v2.1^PHINProfileID^2.16.840.1.114222.4.10.3^ISO~GEN_Case_Map_v2.0^PHINMsgMapID^2.16.840.1.114222.4.10.4^ISO~STD_Case_Map_v1.0^PHINMsgMapID^2.16.840.1.114222.4.10.4^ISO
		
		String msgPrfl1 = null;
		String msgPrfl2 = null;
		
		Coded coded = new Coded(nndEntityIdentifier,
				"code_value_general", "NBS_MSG_PROFILE");
		 String code  = coded.getCode();
		if(code!=null && code.indexOf("^")>1){
			String[] msgProfileArray = code.split("\\^");
			msgPrfl1 = msgProfileArray[0];
			msgPrfl2 = msgProfileArray[1];
		}
		else
			msgPrfl2 = code;
		
		if (msgPrfl1!=null) {
			notNNDEntityIdentifier1.setHl7SegmentField(NEDSSConstants.NND_HL7_SEGMENT_MSH_21);
			notNNDEntityIdentifier1.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_ST);
			notNNDEntityIdentifier1.setOrderGroupId("1"); //First member of MSH 21.1 array
			notNNDEntityIdentifier1.setAnswerTxt(msgPrfl1);
			caseNotificationDataDTCollection.add(notNNDEntityIdentifier1);
		}
		
		notNNDEntityIdentifier2.setHl7SegmentField(NEDSSConstants.NND_HL7_SEGMENT_MSH_21);
		notNNDEntityIdentifier2.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_ST);
		notNNDEntityIdentifier2.setOrderGroupId("2"); //Second member of MSH 21.1 array
		notNNDEntityIdentifier2.setAnswerTxt(msgPrfl2);
		caseNotificationDataDTCollection.add(notNNDEntityIdentifier2);

		// OBR 25.0 - result status
		notResultStatus.setHl7SegmentField(NEDSSConstants.NND_HL7_SEGMENT_OBR_25);
		notResultStatus.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_ID);
		notResultStatus.setAnswerTxt(messageBuilderHelper.getResultStatus(notificationDT));
		caseNotificationDataDTCollection.add(notResultStatus);

		// OBR 7.0 - add time
		notAddTime.setHl7SegmentField(NEDSSConstants.NND_HL7_SEGMENT_OBR_7);
		notAddTime.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_TS);
		notAddTime.setAnswerTxt(notificationDT.getAddTime().toString());
		caseNotificationDataDTCollection.add(notAddTime);
		
		// OBR 22.0 - add time, if first notification to CDC (auto_resend_ind=F), otherwise last change time (Jira Task# ND-1379)
		if(notificationDT.getAutoResendInd()!=null && notificationDT.getAutoResendInd().equals(NEDSSConstants.FALSE)){
			notLastChgTime.setHl7SegmentField(NEDSSConstants.NND_HL7_SEGMENT_OBR_22);
			notLastChgTime.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_TS);
			notLastChgTime.setAnswerTxt(notificationDT.getAddTime().toString());
			caseNotificationDataDTCollection.add(notLastChgTime);
		}else{		
		// OBR 22.0 - last change time
		notLastChgTime.setHl7SegmentField(NEDSSConstants.NND_HL7_SEGMENT_OBR_22);
		notLastChgTime.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_TS);
		notLastChgTime.setAnswerTxt(notificationDT.getLastChgTime().toString());
		caseNotificationDataDTCollection.add(notLastChgTime);
		}
	}

	private CnTransportQOutDT prepareCnTransportQOut(String messagePayload, 
													 NBSSecurityObj nbsSecurityObj,
													 NotificationDT notificationDT,
													 String publicHealthCaseLocalId) throws NEDSSConcurrentDataException {
		CnTransportQOutDT cnTransportQOutDT = new CnTransportQOutDT();
		Date dateTime = new Date();
		Timestamp systemTime = new Timestamp(dateTime.getTime());

		cnTransportQOutDT.setMessagePayload(messagePayload);

		cnTransportQOutDT.setVersionCtrlNbr(new Integer(1));
		cnTransportQOutDT.setRecordStatusCd(NEDSSConstants.NND_UNPROCESSED_MESSAGE);

		cnTransportQOutDT.setReportStatus(messageBuilderHelper.getResultStatus(notificationDT));

		cnTransportQOutDT.setAddUserId(new Long(Long.parseLong(nbsSecurityObj.getEntryID())));
		cnTransportQOutDT.setAddTime(systemTime);
		cnTransportQOutDT.setLastChgUserId(new Long(Long.parseLong(nbsSecurityObj.getEntryID())));
		cnTransportQOutDT.setLastChgTime(systemTime);
		cnTransportQOutDT.setRecordStatusTime(systemTime);

		cnTransportQOutDT.setNotificationUID(notificationDT.getNotificationUid());
		cnTransportQOutDT.setNotificationLocalId(notificationDT.getLocalId());
		cnTransportQOutDT.setPublicHealthCaseLocalId(publicHealthCaseLocalId);

		return cnTransportQOutDT;
	}
	
	/**
	 * getPageProxyObject - get the PageBuilder PageProxyObject for the Public Health Case
	 * @param - public health case uid (Long)
	 * @param - nbsSecurityObj
	 * @return - PageProxyObject
	 */
	public static PageActProxyVO getPageProxyObject(Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj) {
		PageProxyVO pageProxyVO = null;
		NedssUtils nedssUtils = new NedssUtils();
		try {

        	Object object = nedssUtils.lookupBean(JNDINames.PAGE_PROXY_EJB);
	        PageProxyHome home = (PageProxyHome) PortableRemoteObject.narrow(object, PageProxyHome.class);
	        PageProxy pageproxy = home.create();
	        pageProxyVO =  pageproxy.getPageProxyVO(NEDSSConstants.CASE, publicHealthCaseUid, nbsSecurityObj);
	        //translatePageCaseAnswerDTsToAnswerDTs(pageProxyVO);

		} catch (Exception ex) {
			logger.fatal("NNDMessageBuilder:getPageProxyObject: ", ex);
		}

		return (PageActProxyVO) pageProxyVO;
	}
}