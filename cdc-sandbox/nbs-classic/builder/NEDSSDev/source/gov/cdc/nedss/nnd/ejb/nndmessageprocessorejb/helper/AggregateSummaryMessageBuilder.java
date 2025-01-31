package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.CaseNotificationDataDT;
import gov.cdc.nedss.nnd.dt.SummaryCaseRepeatingDataDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationMessageDAOImpl;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
/**
 * Name: AggregateSummaryMessageBuilder.java Description: Construct NBSNNDIntermediaryMessage XML message using
 * XMLBeans derived from NNDIntermediaryMessage.xsd and populating with data from CaseNotificationDataDT's. 
 * End result will be Rhapsody route processing xml to create Aggregate Summary Case Message.  
 * 
 * @author Beau Bannerman
 */
public class AggregateSummaryMessageBuilder {
	private static LogUtils logger = new LogUtils(AggregateSummaryMessageBuilder.class.getName());
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	private static MessageBuilderHelper messageBuilderHelper = new MessageBuilderHelper();
	
	public void createNotificationMessage(NotificationDT notificationDT,
			Long publicHealthCaseUid, String publicHealthCaseLocalId,
			String nndSummaryEntityIdentifier, NBSSecurityObj nbsSecurityObj)
			throws NEDSSConcurrentDataException, NEDSSSystemException {

		NotificationMessageDAOImpl notificationMessageDAOImpl = new NotificationMessageDAOImpl();
		
		// Retrieve Participations associated with the current Investigation (via publicHealthCaseUid)
		TreeMap<Object, Object> phcEntityParticipationsMap = notificationMessageDAOImpl.getPublicHealthCaseEntityParticipations(publicHealthCaseUid);

		Collection<Object>  caseDataDTCollection  = null;
		//TODO - pass in investigation form code?
		caseDataDTCollection  = notificationMessageDAOImpl.getCaseNotificationDataDTCollection(publicHealthCaseUid, notificationDT);
		Long notificationUid = notificationDT.getNotificationUid();
		messageBuilderHelper.retrieveNonNBSAnswerData(caseDataDTCollection, phcEntityParticipationsMap, publicHealthCaseUid, notificationUid,0);
		
		messageBuilderHelper.checkForRequiredMessageElements(caseDataDTCollection, phcEntityParticipationsMap);

		// Add Summary Case data repeating data - via NBS_case_answer data that has table_metadata_uid assigned
		caseDataDTCollection.addAll(retrieveSummaryCaseRepeatingData(publicHealthCaseUid));
		
		messageBuilderHelper.codeLookupforMessageData(caseDataDTCollection);
		String nbsIntermediaryMessageXML = buildMessage(caseDataDTCollection, notificationDT, nndSummaryEntityIdentifier, publicHealthCaseLocalId);

		// Write message to file and/or CN_Transportq_out table
		messageBuilderHelper.writeNBSIntermediaryMessage(nbsIntermediaryMessageXML, nbsSecurityObj, publicHealthCaseLocalId, notificationDT, NNDConstantUtil.SUMMARY_ROOT_DIR);
	}

	private Collection<Object>  retrieveSummaryCaseRepeatingData(Long publicHealthCaseUid) {
		Collection<Object>  caseNotificationDataDTCollection  = new ArrayList<Object> ();

		NotificationMessageDAOImpl notificationMessageDAOImpl = new NotificationMessageDAOImpl();
		Collection<Object>  summaryCaseRepeatingDataDTCollection  = notificationMessageDAOImpl.getSummaryCaseRepeatingData(publicHealthCaseUid);

		Iterator<Object> summaryCaseRepeatingDataIter = summaryCaseRepeatingDataDTCollection.iterator();
		int observationSubID = 1;
		while (summaryCaseRepeatingDataIter.hasNext()) {
			SummaryCaseRepeatingDataDT summaryCaseRepeatingDataDT = (SummaryCaseRepeatingDataDT)summaryCaseRepeatingDataIter.next();
			caseNotificationDataDTCollection.addAll(summaryCaseRepeatingDataDT.toCaseNotificationDTs(String.valueOf(observationSubID)));
			observationSubID++;
		}
		
		
		if ( caseNotificationDataDTCollection.size() < 1 ){
			String errString = "AggregateSummaryMessageBuilder.retrieveSummaryCaseRepeatingData:  No summary case data - Counts, Aggregation, Indicators found for public_heath_case uid = " + publicHealthCaseUid;
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
		
		return caseNotificationDataDTCollection;
	}
	
	@SuppressWarnings("unchecked")
	private String buildMessage(Collection<Object> caseNotificationDataDTCollection, NotificationDT notificationDT, 
								String nndSummaryEntityIdentifier, String publicHealthCaseLocalId) {
		// Add necessary NND message elements
		addAggregateSummaryInfo(caseNotificationDataDTCollection, notificationDT, nndSummaryEntityIdentifier);

		// Sort by NND Identifier and QuestionGroupSeqNbr
		List<Object> list = (ArrayList<Object> )caseNotificationDataDTCollection;
		Collections.sort(list, CaseNotificationDataDT.HL7MessageSort);

		messageBuilderHelper.populateUnits(caseNotificationDataDTCollection);

		return messageBuilderHelper.createNBSNNDIntermediaryMessageXML(caseNotificationDataDTCollection, null, null, null, null);
	}


	private Collection<Object>  addAggregateSummaryInfo(Collection<Object> caseNotificationDataDTCollection,
											   NotificationDT notificationDT, String nndSummaryEntityIdentifier) {

		// Create DT's for notification.add_time and last_chg_time, Result Status and Message Control ID
		CaseNotificationDataDT notMsgControlId = new CaseNotificationDataDT();
		CaseNotificationDataDT notNNDEntityIdentifier = new CaseNotificationDataDT();
		CaseNotificationDataDT notNNDIDNumber = new CaseNotificationDataDT();
		CaseNotificationDataDT notResultStatus = new CaseNotificationDataDT();
		CaseNotificationDataDT notAddTime = new CaseNotificationDataDT();
		CaseNotificationDataDT notLastChgTime = new CaseNotificationDataDT();

		// MSH 10.0 - Message Control ID - need Notification local id
		notMsgControlId.setHl7SegmentField(NEDSSConstants.NND_HL7_SEGMENT_MSH_10);
		notMsgControlId.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_ST);
		notMsgControlId.setAnswerTxt(notificationDT.getLocalId());
		caseNotificationDataDTCollection.add(notMsgControlId);

		// MSH 21.0 - Entity Identifier - Condition specific
		notNNDEntityIdentifier.setHl7SegmentField(NEDSSConstants.NND_HL7_SEGMENT_MSH_21);
		notNNDEntityIdentifier.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_ST);
    	notNNDEntityIdentifier.setOrderGroupId("2"); //Second member of MSH 21.1 array
		notNNDEntityIdentifier.setAnswerTxt(nndSummaryEntityIdentifier);
		caseNotificationDataDTCollection.add(notNNDEntityIdentifier);

		// PID 3.1 - ID Number - Setting to 'SUMMARY' since this is required, but since this is
		// a Summary message, there is no unique patient.
		notNNDIDNumber.setHl7SegmentField(NEDSSConstants.NND_HL7_SEGMENT_PID_3_1);
		notNNDIDNumber.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_ST);
		notNNDIDNumber.setAnswerTxt("SUMMARY");
		caseNotificationDataDTCollection.add(notNNDIDNumber);
		
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

		// OBR 22.0 - last change time
		notLastChgTime.setHl7SegmentField(NEDSSConstants.NND_HL7_SEGMENT_OBR_22);
		notLastChgTime.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_TS);
		notLastChgTime.setAnswerTxt(notificationDT.getLastChgTime().toString());
		caseNotificationDataDTCollection.add(notLastChgTime);

		return caseNotificationDataDTCollection;
	}
}