
package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.CaseNotificationDataDT;
import gov.cdc.nedss.nnd.dt.CnTransportQOutDT;
import gov.cdc.nedss.nnd.dt.VaccinationDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.CnTransportQOutDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationMessageDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationSRTCodeLookupTranslationDAOImpl;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement;
import gov.cdc.nedss.nnd.intermediarymessage.NBSNNDIntermediaryMessageDocument;
import gov.cdc.nedss.nnd.intermediarymessage.NBSNNDIntermediaryMessageDocument.NBSNNDIntermediaryMessage;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.CeDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.CweDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.DtDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.IdDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.IsDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.NmDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.SnDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.SnunitDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.StDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.TsDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.TxDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.CxDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.HdDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.XpnDataType;
import gov.cdc.nedss.nnd.intermediarymessage.MessageElement.DataElement.XtnDataType;
import gov.cdc.nedss.nnd.intermediarymessage.EiDataType;
import gov.cdc.nedss.nnd.intermediarymessage.LabReportEvent;
import gov.cdc.nedss.nnd.intermediarymessage.LabReportEvent.ResultedTest;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
import gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.CodeValueGeneralDAOImpl;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTGenericCodeDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 *
 */
public class MessageBuilderHelper {
	private static LogUtils logger = new LogUtils(MessageBuilderHelper.class.getName());
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	private static ArrayList<Object> vaccineInformationSource = new ArrayList<Object>();
	private static ArrayList<Object> VaccineAnatomicSite = new ArrayList<Object>();
	private static ArrayList<Object> VaccineManufacturer = new ArrayList<Object>();
	private static ArrayList<Object> VaccineName = new ArrayList<Object>();
	public MessageBuilderHelper() {}


	public String createNBSNNDIntermediaryMessageXML(Collection<Object> caseNotificationDataDTCollection, Collection<Object> caseDataDTCollectionOrder, Collection<Object> caseDataDTCollectionResult, Collection<Object> caseDataDTCollectionSusceptility,Collection<Object> caseDataDTCollectionSusceptilityResult) throws NEDSSSystemException {
		
		
		NBSNNDIntermediaryMessageDocument nndIntMessageDoc = null;

		logger.info("Creating NBS NND Intermediary Message XML");
		// Create new NND Intermediary Message Doc and Message for Case data
		nndIntMessageDoc = NBSNNDIntermediaryMessageDocument.Factory.newInstance();
		NBSNNDIntermediaryMessage nndIntMessage = nndIntMessageDoc.addNewNBSNNDIntermediaryMessage();
		//nndIntMessage.addNewLabReportEvent()
		if (caseNotificationDataDTCollection  != null) {
			Iterator<Object> it = caseNotificationDataDTCollection.iterator();
			while (it.hasNext()) {
				Object object = it.next();
				if (object instanceof CaseNotificationDataDT) {
					CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)object;

					// Create new NBSNNDIntermediaryMessage element, populating it with NBS Case Data
					// Need to make sure this is not a "unit" question, we don't want to create a new element
					// because it will get added in the "parent's" element. The parent is the question that
					// contains the value that the unit qualifies.
					if (caseNotificationDataDT.getUnitParentIdentifier() == null ||
					    caseNotificationDataDT.getUnitParentIdentifier().trim().length() == 0) {
						
						MessageElement nndIntMessageElement = populateIntermediaryMessageWithCaseData(
																				caseNotificationDataDT,
																				nndIntMessage.addNewMessageElement(),
																				caseNotificationDataDTCollection);
					}
					
				} else {
					String errString = "MessageBuilderHelper.createNBSNNDIntermediaryMessageXML:  Invalid class type found in CaseNotificationDataDTCollection";
					logger.error(errString);
					throw new NEDSSSystemException(errString);
				}
			}
		}
		
		logger.info("Processing labs");
		processLabs(caseDataDTCollectionOrder, caseDataDTCollectionResult, caseDataDTCollectionSusceptility, caseDataDTCollectionSusceptilityResult,nndIntMessage);
		
		// Return XML for NBSNNDIntermediaryMessage
		String nbsIntermediaryMessageXML = nndIntMessageDoc.toString();
		nbsIntermediaryMessageXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + nbsIntermediaryMessageXML;
		return nbsIntermediaryMessageXML;
	}

	/**
	 * processLabs: New method to support LAB NND. This method calls the populateIntermediaryMessageWithCaseData method with each of the lab DTs and the processResult method to process the results associated to the corresponding lab.
	 * @param caseDataDTCollectionOrder
	 * @param caseDataDTCollectionResult
	 * @param caseDataDTCollectionSusceptility
	 * @param nndIntMessage
	 */
	private void processLabs(Collection<Object> caseDataDTCollectionOrder, Collection<Object> caseDataDTCollectionResult, Collection<Object> caseDataDTCollectionSusceptility, Collection<Object> caseDataDTCollectionSusceptilityResult, NBSNNDIntermediaryMessage nndIntMessage){
		
		try{
				if(caseDataDTCollectionOrder!=null && caseDataDTCollectionOrder.size()>0)//the investigation contains at least one lab
				{
					LabReportEvent lab = nndIntMessage.addNewLabReportEvent();
				
					Iterator<Object> it = caseDataDTCollectionOrder.iterator();
					Long observationUid = null;
					Long previousObservationUid = null;
					//boolean stop = false;
					
					while (it.hasNext() ) {
						Object object = it.next();
						if (object instanceof CaseNotificationDataDT) {
							CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)object;
							observationUid = caseNotificationDataDT.getObservationUid();
							//Changed to .equals to compare long objects as != was not working
							if(observationUid!=null && !observationUid.equals(previousObservationUid) && previousObservationUid!=null){
								logger.info("Processing results observationUid: "+previousObservationUid);
								processResults(caseDataDTCollectionResult, caseDataDTCollectionSusceptility, caseDataDTCollectionSusceptilityResult, lab, previousObservationUid);
								if(it.hasNext())
									lab = nndIntMessage.addNewLabReportEvent();	
							}
									
								// Create new NBSNNDIntermediaryMessage element, populating it with NBS Case Data
								// Need to make sure this is not a "unit" question, we don't want to create a new element
								// because it will get added in the "parent's" element. The parent is the question that
								// contains the value that the unit qualifies.
								if (caseNotificationDataDT.getUnitParentIdentifier() == null ||
								    caseNotificationDataDT.getUnitParentIdentifier().trim().length() == 0) {
									logger.info("Labs: Populating intermediary message, questionIdentifier = "+caseNotificationDataDT.getQuestionIdentifier()+" questionIdentifierNND = "+caseNotificationDataDT.getQuestionIdentifierNND());
									MessageElement nndIntMessageElement = populateIntermediaryMessageWithCaseData(
																							caseNotificationDataDT,
																							lab.addNewMessageElement(),
																							caseDataDTCollectionOrder);
								}
								
								if(!it.hasNext()){//new lab or last lab
									logger.info("Processing results observationUid: "+previousObservationUid);
									processResults(caseDataDTCollectionResult, caseDataDTCollectionSusceptility, caseDataDTCollectionSusceptilityResult, lab, previousObservationUid);
								}
								previousObservationUid = observationUid;

						} else {
							String errString = "MessageBuilderHelper.processLabs:  Invalid class type found in CaseNotificationDataDTCollection";
							logger.error(errString);
							throw new NEDSSSystemException(errString);
						}
					}
	}
		}catch (NEDSSSystemException e){
			String error = "MessageBuilderHelper.processLabs: "+e.getMessage();
			logger.fatal(error);
			throw new NEDSSSystemException(error,e);
		}
	}
	
	/**
	 * processResults: New method to support LAB NND. This method calls the populateIntermediaryMessageWithCaseData method with each of the result DTs and the processSusceptibility method to process the susceptibilities associated to the corresponding result.
	 * @param caseDataDTCollectionResult
	 * @param caseDataDTCollectionSusceptility
	 * @param lab
	 * @param parent
	 */
	
	private void processResults(Collection<Object> caseDataDTCollectionResult, Collection<Object> caseDataDTCollectionSusceptility, Collection<Object> caseDataDTCollectionSusceptilityResult, LabReportEvent lab, Long parent){
		try{
				if(caseDataDTCollectionResult!=null && caseDataDTCollectionResult.size()>0)//the investigation contains at least one lab
				{
					ResultedTest resultedTest = lab.addNewResultedTest();
				
					Iterator<Object> it = caseDataDTCollectionResult.iterator();
					Long observationUid = null;
					Long parentObs = null;
					Long previousObservationUid = null;
					Long previousParent = null;
					
					while (it.hasNext() ) {
						Object object = it.next();
						if (object instanceof CaseNotificationDataDT) {
							CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)object;
							observationUid = caseNotificationDataDT.getObservationUid();
							parentObs = caseNotificationDataDT.getUid();
							if((previousParent!=null && previousParent.equals(parent)) || (previousParent == null && parentObs.equals(parent))){
								if(!observationUid.equals(previousObservationUid) && previousObservationUid!=null){//new result
									    if(propertyUtil.getIncludeSusceptibilitiesInLabNND()!=null && propertyUtil.getIncludeSusceptibilitiesInLabNND().equals("Y")){
									    	logger.info("Processing susceptibilities observationUid: "+previousObservationUid);
									    	processSusceptibilities(caseDataDTCollectionSusceptility, caseDataDTCollectionSusceptilityResult, resultedTest, previousObservationUid);
									    }
										if(it.hasNext() && parentObs.equals(parent))
											resultedTest = lab.addNewResultedTest();
										
							}
									
							// Create new NBSNNDIntermediaryMessage element, populating it with NBS Case Data
							// Need to make sure this is not a "unit" question, we don't want to create a new element
							// because it will get added in the "parent's" element. The parent is the question that
							// contains the value that the unit qualifies.
							if (caseNotificationDataDT.getUnitParentIdentifier() == null ||
							    caseNotificationDataDT.getUnitParentIdentifier().trim().length() == 0) {
								logger.info("Results: Populating intermediary message, questionIdentifier = "+caseNotificationDataDT.getQuestionIdentifier()+" questionIdentifierNND = "+caseNotificationDataDT.getQuestionIdentifierNND());
								MessageElement nndIntMessageElement = populateIntermediaryMessageWithCaseData(
																						caseNotificationDataDT,
																						resultedTest.addNewMessageElement(),
																						caseDataDTCollectionResult);
							}

							if(!it.hasNext() && propertyUtil.getIncludeSusceptibilitiesInLabNND()!=null && propertyUtil.getIncludeSusceptibilitiesInLabNND().equals("Y")){//last result
								logger.info("Processing susceptibilities observationUid: "+previousObservationUid);
								processSusceptibilities(caseDataDTCollectionSusceptility, caseDataDTCollectionSusceptilityResult, resultedTest, previousObservationUid);
							}
							previousObservationUid = observationUid;
							previousParent = parentObs;
							}
						} else {
							String errString = "MessageBuilderHelper.processResults:  Invalid class type found in CaseNotificationDataDTCollection";
							logger.error(errString);
							throw new NEDSSSystemException(errString);
						}
					}
		
			}
		}catch (NEDSSSystemException e){
			String error = "MessageBuilderHelper.processResults: "+e.getMessage();
			logger.fatal(error);
			throw new NEDSSSystemException(error,e);
		}
	}
	
	private void processSusceptibilities(Collection<Object> caseDataDTCollectionSusceptility, Collection<Object> caseDataDTCollectionSusceptilityResult, ResultedTest resultedTest, Long parent){
		boolean isParentOfAnySusceptibility = checkIfIsParent(
				caseDataDTCollectionSusceptility, parent);
		
	try{
		if (isParentOfAnySusceptibility)
		if(caseDataDTCollectionSusceptility!=null && caseDataDTCollectionSusceptility.size()>0)//the investigation contains at least one lab
		{
			LabReportEvent susceptibility = resultedTest.addNewLabReportEvent();
		
			Iterator<Object> it = caseDataDTCollectionSusceptility.iterator();
			Long observationUid = null;
			Long parentObs = null;
			Long previousObservationUid = null;
			Long previousParent = null;
			boolean susceptibilityOBR = true;
			
			boolean resultTest = false; 
			while (it.hasNext() ) {
				Object object = it.next();
				if (object instanceof CaseNotificationDataDT) {
					CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)object;
					observationUid = caseNotificationDataDT.getObservationUid();
					parentObs = caseNotificationDataDT.getUid();
					if((previousParent!=null && previousParent.equals(parent)) || (previousParent == null && parentObs.equals(parent))){
						if(!observationUid.equals(previousObservationUid) && previousObservationUid!=null){//new result
							
								logger.info("Processing susceptibility results observationUid: "+previousObservationUid+" parent: "+parent);
								processSusceptibilitiesResult(caseDataDTCollectionSusceptilityResult, susceptibility, previousObservationUid, previousParent);
								susceptibilityOBR =false;	
					}
							
					// Create new NBSNNDIntermediaryMessage element, populating it with NBS Case Data
					// Need to make sure this is not a "unit" question, we don't want to create a new element
					// because it will get added in the "parent's" element. The parent is the question that
					// contains the value that the unit qualifies.
					if ((caseNotificationDataDT.getUnitParentIdentifier() == null ||
					    caseNotificationDataDT.getUnitParentIdentifier().trim().length() == 0) && susceptibilityOBR) {
						
						logger.info("Susceptibilities: Populating intermediary message, questionIdentifier = "+caseNotificationDataDT.getQuestionIdentifier()+" questionIdentifierNND = "+caseNotificationDataDT.getQuestionIdentifierNND());
						MessageElement nndIntMessageElement = populateIntermediaryMessageWithCaseData(
																				caseNotificationDataDT,
																				susceptibility.addNewMessageElement(),
																				caseDataDTCollectionSusceptility);
					
						
					}
					

					if(!it.hasNext()){//last result
						logger.info("Processing susceptibility results observationUid: "+previousObservationUid+" parent: "+parent);
						processSusceptibilitiesResult(caseDataDTCollectionSusceptilityResult, susceptibility, previousObservationUid, parent);
					}
					previousObservationUid = observationUid;
					previousParent=parentObs;
					}
					else
						if((previousParent!=null && previousParent.equals(parent)) || (previousParent == null && parentObs.equals(parent)))
							if(previousObservationUid!=null && !observationUid.equals(previousObservationUid) && !resultTest){//new result
								logger.info("Processing susceptibility results observationUid: "+previousObservationUid+" parent: "+parent);
								processSusceptibilitiesResult(caseDataDTCollectionSusceptilityResult, susceptibility, previousObservationUid, parent);
								previousObservationUid = observationUid;
								previousParent=parentObs;
								resultTest = true;
								/*if(it.hasNext())
									susceptibility = resultedTest.addNewLabReportEvent();*/
						
					}
				} else {
					String errString = "MessageBuilderHelper.processResults:  Invalid class type found in CaseNotificationDataDTCollection";
					logger.error(errString);
					throw new NEDSSSystemException(errString);
				}
			}

		}
	}catch (NEDSSSystemException e){
		String error = "MessageBuilderHelper.processSusceptibilities: "+e.getMessage();
		logger.fatal(error);
		throw new NEDSSSystemException(error,e);
	}
}

	/**
	 * checkIfIsParent: this method checks if any of the susceptibility has an uid (parent) = the variable parent
	 * @param caseDataDTCollectionSusceptility
	 * @param parent
	 * @return
	 */
	private boolean checkIfIsParent(Collection<Object> caseDataDTCollectionSusceptilityResult, Long parent){
		
		
		Iterator<Object> it = caseDataDTCollectionSusceptilityResult.iterator();
		boolean isParentSusceptibility = false;
		
		while (it.hasNext() && !isParentSusceptibility) {
		
			Object object = it.next();
			if (object instanceof CaseNotificationDataDT) {
				CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)object;
				Long uid = caseNotificationDataDT.getUid();
				if (uid!=null && uid.equals(parent))
					isParentSusceptibility=true;
			}
		}
		
		return isParentSusceptibility;
		
	}
	
	/**
	 * processSusceptibilities: New method to support LAB NND. This method calls
	 * the populateIntermediaryMessageWithCaseData method with each of the
	 * susceptibilities DTs.
	 * 
	 * @param caseDataDTCollectionSusceptility
	 * @param resultedTest
	 * @param parent
	 */

	private void processSusceptibilitiesResult(
			Collection<Object> caseDataDTCollectionSusceptilityResult,
			LabReportEvent susceptility, Long observationUidSuscept, Long parent) {
		
		try{
			if (caseDataDTCollectionSusceptilityResult != null
					&& caseDataDTCollectionSusceptilityResult.size() > 0) {
				ResultedTest resultedTest = susceptility.addNewResultedTest();

				Iterator<Object> it = caseDataDTCollectionSusceptilityResult
						.iterator();
				Long parentObs = null;
				Long observationUid = null;
				Long previousObservationUid = null;
				while (it.hasNext()) {
					Object object = it.next();
					if (object instanceof CaseNotificationDataDT) {
						CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT) object;
						parentObs = caseNotificationDataDT.getUid();
						observationUid = caseNotificationDataDT.getObservationUid();
		
							if(observationUid.equals(observationUidSuscept)){//new result
							
							/*
							 * Create new NBSNNDIntermediaryMessage element,
							 * populating it with NBS Case Data Need to make
							 * sure this is not a "unit" question, we don't want
							 * to create a new element because it will get added
							 * in the "parent's" element. The parent is the
							 * question that contains the value that the unit
							 * qualifies.
							 */
							if (caseNotificationDataDT.getUnitParentIdentifier() == null
									|| caseNotificationDataDT.getUnitParentIdentifier().trim().length() == 0) {
								logger.info("Susceptibility results: Populating intermediary message, questionIdentifier = "+caseNotificationDataDT.getQuestionIdentifier()+" questionIdentifierNND = "+caseNotificationDataDT.getQuestionIdentifierNND());
									MessageElement nndIntMessageElement = populateIntermediaryMessageWithCaseData(
											caseNotificationDataDT,
											resultedTest.addNewMessageElement(),
											caseDataDTCollectionSusceptilityResult);
							}
							previousObservationUid = observationUid;
						}
					} else {
						String errString = "MessageBuilderHelper.processSusceptibilitiesResult:  Invalid class type found in CaseNotificationDataDTCollection";
						logger.error(errString);
						throw new NEDSSSystemException(errString);
					}
				}
			}
		}catch (NEDSSSystemException e){
			String error = "MessageBuilderHelper.processSusceptibilitiesResult: "+e.getMessage();
			logger.fatal(error);
			throw new NEDSSSystemException(error,e);
		}
	}

	
	private MessageElement populateIntermediaryMessageWithCaseData(CaseNotificationDataDT caseNotificationDataDT,
																   MessageElement nndIntMessageElement,
																   Collection<Object>  CaseNotificationDataDTCollection)
																   throws NEDSSSystemException {
	
		logger.info("PopulateIntermediaryMessageWithCaseData");
		try {
			// Set top level MessageElement data
			nndIntMessageElement.setQuestionIdentifierNND(caseNotificationDataDT.getQuestionIdentifierNND());
			nndIntMessageElement.setQuestionLabelNND(caseNotificationDataDT.getQuestionLabelNND());
			nndIntMessageElement.setQuestionOID(caseNotificationDataDT.getQuestionOID());
			nndIntMessageElement.setQuestionIdentifier(caseNotificationDataDT.getQuestionIdentifier());

			
			if (caseNotificationDataDT.getUiMetadataQuestionLabel() != null &&
				caseNotificationDataDT.getUiMetadataQuestionLabel().trim().length() > 0)
				nndIntMessageElement.setQuestionLabel(caseNotificationDataDT.getUiMetadataQuestionLabel());
			else
				nndIntMessageElement.setQuestionLabel(caseNotificationDataDT.getNbsQuestionLabel());

			nndIntMessageElement.setHl7SegmentField(caseNotificationDataDT.getHl7SegmentField());
			nndIntMessageElement.setOrderGroupId(caseNotificationDataDT.getOrderGroupId());

			if (caseNotificationDataDT.getObservationSubID() != null &&
				caseNotificationDataDT.getObservationSubID().trim().length() > 0)
				nndIntMessageElement.setObservationSubID(caseNotificationDataDT.getObservationSubID());

			// This field is used to group related questions together and the value will populate the
			// OBX-4 segment.  It will only be populated for questions that are a member of a question group.
			if (caseNotificationDataDT.getQuestionGroupSeqNbr() != null &&
				caseNotificationDataDT.getQuestionGroupSeqNbr().intValue() > 0)
				nndIntMessageElement.setQuestionGroupSeqNbr(caseNotificationDataDT.getQuestionGroupSeqNbr().toString());

			DataElement dataElement = nndIntMessageElement.addNewDataElement();
			dataElement.setQuestionDataTypeNND(caseNotificationDataDT.getQuestionDataTypeNND());
			
			//Changes to support Discrete to Repeating from UI to NND (HL7)
			if (caseNotificationDataDT.getRepeatGroupSeqNbr() != null && caseNotificationDataDT.getRepeatGroupSeqNbr().intValue() > 0)
				nndIntMessageElement.setRepeatGroupSeqNbr(caseNotificationDataDT.getRepeatGroupSeqNbr().toString());
			
			if (caseNotificationDataDT.getQuestionMap() != null && caseNotificationDataDT.getQuestionMap().trim().length() > 0)			
				nndIntMessageElement.setQuestionMap(caseNotificationDataDT.getQuestionMap());
			
			if (caseNotificationDataDT.getIndicatorCd() != null && caseNotificationDataDT.getIndicatorCd().trim().length() > 0)
				nndIntMessageElement.setIndicatorCd(caseNotificationDataDT.getIndicatorCd());
			
			
			logger.info("questionIdentifierNND: "+nndIntMessageElement.getQuestionIdentifierNND()
					+ " questionLabelNND: "+nndIntMessageElement.getQuestionLabelNND()
					+ " questionOID: "+nndIntMessageElement.getQuestionOID()
					+ " questionIdentifier: "+nndIntMessageElement.getQuestionIdentifier()
					+ " questionLabel: "+nndIntMessageElement.getQuestionLabel()
					+ " Hl7SegmentField: "+nndIntMessageElement.getHl7SegmentField()
					+ " orderGroupId: "+nndIntMessageElement.getOrderGroupId()
					+ " observationSubID: "+nndIntMessageElement.getObservationSubID()
					+ " questionGroupSeqNbr: "+nndIntMessageElement.getQuestionGroupSeqNbr()
					+ " repeatGroupSeqNbr: "+nndIntMessageElement.getRepeatGroupSeqNbr()
					+ " questionMap: "+nndIntMessageElement.getQuestionMap()
					+ " indicatorCd: "+nndIntMessageElement.getIndicatorCd());
			
			// HL7 SN -> Structured Numeric
			if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_SN)) {
				// Check if question has a unit question associated to it - TB/Varicella legacy
 				if (caseNotificationDataDT.getQuestionUnitIdentifier() != null ) {
					if ((caseNotificationDataDT.getCodedValue() == null || caseNotificationDataDT.getCodedValue().trim().length() == 0) &&
					    (caseNotificationDataDT.getLocalCodedValue() == null || caseNotificationDataDT.getLocalCodedValue().trim().length() == 0)	) {
						// No unit for field indicating it has a unit, need to fail the message
						String errString = "MessageBuilderHelper.populateIntermediaryMessageWithCaseData - Missing unit for structured numeric.  Attempting to find unit question identifer: " +
								caseNotificationDataDT.getQuestionUnitIdentifier() +
								" for question identifier: " + caseNotificationDataDT.getQuestionIdentifier();

						logger.error(errString);
						throw new NEDSSSystemException(errString);
					}

					// This means we have a structured numeric with a unit.
					dataElement.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_SN_WITH_UNIT);

					// First add structured numeric value
					SnunitDataType snunitDataType = dataElement.addNewSnunitDataType();
					parseStructuredNumericLab(caseNotificationDataDT.getAnswerTxt(), snunitDataType);
					
					//snunitDataType.setNum1(caseNotificationDataDT.getAnswerTxt());

					// Unit's coded info should be in the DT's code attributes
					snunitDataType.setCeCodedValue(caseNotificationDataDT.getCodedValue());
					snunitDataType.setCeCodedValueDescription(caseNotificationDataDT.getCodedValueDescription());
					snunitDataType.setCeCodedValueCodingSystem(caseNotificationDataDT.getCodedValueCodingSystem());
					snunitDataType.setCeLocalCodedValue(caseNotificationDataDT.getLocalCodedValue());
					snunitDataType.setCeLocalCodedValueDescription(caseNotificationDataDT.getLocalCodedValueDescription());
					snunitDataType.setCeLocalCodedValueCodingSystem(caseNotificationDataDT.getLocalCodedValueCodingSystem());
				} else { // otherwise it is structured numeric stored in answer_txt delimited with unit (if populated) - delimiter '^'
					     // [num1][separator][num2][comparator]^[unit code/unit literal] - parse
						 // if unit included, look up metadata to see if it is literal or code - if code, do code look up

					SnDataType snData = null;
					SnunitDataType snunitDataType = parseStructuredNumericUnit(caseNotificationDataDT, dataElement);

					if (snunitDataType == null) {
						// No unit, so use this datatype
						snData = dataElement.addNewSnDataType();
					} else {
						// Have a unit, so set this flag for route mapper
						dataElement.setQuestionDataTypeNND(NEDSSConstants.NND_HL7_DATATYPE_SN_WITH_UNIT);
					}

					String structuredNumeric = caseNotificationDataDT.getAnswerTxt();

					if (structuredNumeric == null) {
						String errString = "MessageBuilderHelper.populateIntermediaryMessageWithCaseData:  Structured numeric is missing, caseNotificationDataDT=" + caseNotificationDataDT.toString();
						logger.error(errString);
						throw new NEDSSSystemException(errString);
					}

					int countSeparator = structuredNumeric.length()-structuredNumeric.replace("^","").length();
					
					if(countSeparator>1)//This is in case the numeric doesn't have an unit but the format is like Num1^Comparator^Num2^separator instead of using ^ for separating the the whole number and the units, like the else
						parseStructuredNumericLabWithoutUnit(structuredNumeric, snData, snunitDataType);
					else
						parseStructuredNumeric(structuredNumeric, snData, snunitDataType);
					
				}
			// HL7 ST -> String
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_ST)) {
				StDataType stData = dataElement.addNewStDataType();
				//This is for data that is captured as coded value in NBS but sent as ST in NND Message
				if(caseNotificationDataDT.getCodesetGroupId()!=null && caseNotificationDataDT.getAnswerTxt()!=null){
					NotificationSRTCodeLookupTranslationDAOImpl srtCodeLookUpDAO = new NotificationSRTCodeLookupTranslationDAOImpl();
					caseNotificationDataDT.setCodedValue(caseNotificationDataDT.getAnswerTxt());
					srtCodeLookUpDAO.retrieveCodeDescAndCodingSystemWithCodesetGroupId(caseNotificationDataDT);
					if(caseNotificationDataDT.getCodedValueDescription()!=null)
						stData.setStringData(caseNotificationDataDT.getCodedValueDescription());
					else
						stData.setStringData(caseNotificationDataDT.getLocalCodedValueDescription());
				}else
					stData.setStringData(caseNotificationDataDT.getAnswerTxt());
			// HL7 XPN -> Extended person name
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_XPN)) {
				XpnDataType xpnData = dataElement.addNewXpnDataType();
				String name = caseNotificationDataDT.getAnswerTxt();
				try {
					//Parse name, in format of 'LASTNAME,FIRSTNAME'
					xpnData.setFamilyName(name.split(",")[0]);
					xpnData.setGivenName(name.split(",")[1]);
				} catch (Exception e) {
					String errString = "MessageBuilderHelper.populateIntermediaryMessageWithCaseData:  Invalid XPN-datatype name format:  '" + caseNotificationDataDT.getAnswerTxt() + "', for question identifier:  " +  caseNotificationDataDT.getQuestionIdentifier();
					logger.error(errString);
					throw new NEDSSSystemException(errString);
				}
			// HL7 XTN -> Extended telecommunications number
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_XTN)) {
				XtnDataType xtnData = dataElement.addNewXtnDataType();
				xtnData.setTelecommunicationUseCode(caseNotificationDataDT.getDataUseCd());
				xtnData.setTelecommunicationEquipmentType(caseNotificationDataDT.getDataCd());
				if (caseNotificationDataDT.getDataCd().equalsIgnoreCase(NEDSSConstants.INTERNET)) {
					String email = caseNotificationDataDT.getAnswerTxt();
					xtnData.setEmailAddress(email);
				} else if (caseNotificationDataDT.getDataCd().equalsIgnoreCase(NEDSSConstants.PHONE) ||
						caseNotificationDataDT.getDataCd().equalsIgnoreCase(NEDSSConstants.FAX)) {
					String phoneNumber = caseNotificationDataDT.getAnswerTxt();
					try {
						//Parse phone number, in format of 404-555-1212
						xtnData.setAreaOrCityCode(phoneNumber.split("-")[0]);
						xtnData.setPhoneNumber(phoneNumber.split("-")[1]+phoneNumber.split("-")[2]);
					} catch (Exception e) {
						String errString = "MessageBuilderHelper.populateIntermediaryMessageWithCaseData:  Invalid XTN-datatype phone format (should be XXX-XXX-XXXX):  '" + caseNotificationDataDT.getAnswerTxt() + "', for question identifier:  " +  caseNotificationDataDT.getQuestionIdentifier();
						logger.error(errString);
						throw new NEDSSSystemException(errString);
					}
				} else {
					String errString = "MessageBuilderHelper.populateIntermediaryMessageWithCaseData:  Unsupported XTN-datatype data_cd:  '" + caseNotificationDataDT.getDataCd() + "', for question identifier:  " + caseNotificationDataDT.getQuestionIdentifier();
					logger.error(errString);
					throw new NEDSSSystemException(errString);
				}
			// HL7 TX -> Text data
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_TX)) {
				TxDataType txData = dataElement.addNewTxDataType();
				txData.setTextData(caseNotificationDataDT.getAnswerTxt());
			// HL7 CX -> Composite ID for HL7 tables
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_CX)) {
				CxDataType cxData = dataElement.addNewCxDataType();
				cxData.setCxData(caseNotificationDataDT.getAnswerTxt());
			// HL7 CX -> Composite ID for HL7 tables
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_HD)) {
				HdDataType hdData = dataElement.addNewHdDataType();
				hdData.setNamespaceId(caseNotificationDataDT.getNamespaceId());
				hdData.setUniversalId(caseNotificationDataDT.getUniversalId());
				hdData.setUniversalIdType(caseNotificationDataDT.getUniversalIdType());
			// HL7 ID -> Coded values for HL7 tables
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_ID)) {
				IdDataType idData = dataElement.addNewIdDataType();
				idData.setIdCodedValue(caseNotificationDataDT.getAnswerTxt());
			// HL7 IS -> Coded values for user-defined tables
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_IS)) {
				IsDataType isData = dataElement.addNewIsDataType();
				isData.setIsCodedValue(caseNotificationDataDT.getAnswerTxt());
			// HL7 EI -> Entity Identifier
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_EI)) {
				EiDataType eiData = dataElement.addNewEiDataType();
				String eiDataTxt = caseNotificationDataDT.getAnswerTxt();
				
				if (eiDataTxt.contains("^")) {
					eiData.setEntityIdentifier(eiDataTxt.split("\\^")[0]);
					eiData.setNamespaceId(eiDataTxt.split("\\^")[1]);
					eiData.setUniversalId(eiDataTxt.split("\\^")[2]);
					eiData.setUniversalIdType(eiDataTxt.split("\\^")[3]);
				}
				
			// HL7 EIP -> Entity Identifier Pair
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_EIP)) {
				//Don't do anything 
				/*EipDataType eipData = dataElement.addNewEipDataType();
			
				if(caseNotificationDataDT.getDataCd().equals("")){
					FillerAssignedIdentifier faID = eipData.addNewFillerAssignedIdentifier();
					EiDataType eiData = faID.addNewEiDataType();
					eiData.setEntityIdentifier(caseNotificationDataDT.getAnswerTxt());
					eiData.setNamespaceId(caseNotificationDataDT.getNamespaceId());
					eiData.setUniversalId(caseNotificationDataDT.getUniversalId())	;
					eiData.setUniversalIdType(caseNotificationDataDT.getUniversalIdType());
					eipData.setFillerAssignedIdentifier(faID);
				}
				if(caseNotificationDataDT.getDataCd().equals("")){
					PlacerAssignedIdentifier paID = eipData.addNewPlacerAssignedIdentifier();
					EiDataType eiData = paID.addNewEiDataType();
					eiData.setEntityIdentifier(caseNotificationDataDT.getAnswerTxt());
					eiData.setNamespaceId(caseNotificationDataDT.getNamespaceId());
					eiData.setUniversalId(caseNotificationDataDT.getUniversalId());
					eiData.setUniversalIdType(caseNotificationDataDT.getUniversalIdType());
					paID.setEiDataType(eiData);
				}*/
				
			// HL7 CWE -> Coded With Exception
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_CWE)) {
				CweDataType cweData = dataElement.addNewCweDataType();
				if ( ( caseNotificationDataDT.getCodedValueCodingSystem() == null || caseNotificationDataDT.getCodedValueCodingSystem().length() == 0 ) &&
					 ( caseNotificationDataDT.getLocalCodedValue() == null || caseNotificationDataDT.getLocalCodedValue().length() == 0 ) ) {
					// No coding system, so codedValue contains a local, uncoded value that should be moved to localCodeDescription - probably City
					// Also, check that local isn't already populated (in case it is already fully "locally coded"
					cweData.setCweLocalCodedValueDescription(caseNotificationDataDT.getCodedValue());
				} else {
					cweData.setCweCodedValue(caseNotificationDataDT.getCodedValue());
					cweData.setCweCodedValueDescription(caseNotificationDataDT.getCodedValueDescription());
					cweData.setCweCodedValueCodingSystem(caseNotificationDataDT.getCodedValueCodingSystem());
					cweData.setCweLocalCodedValue(caseNotificationDataDT.getLocalCodedValue());
					cweData.setCweLocalCodedValueDescription(caseNotificationDataDT.getLocalCodedValueDescription());
					cweData.setCweLocalCodedValueCodingSystem(caseNotificationDataDT.getLocalCodedValueCodingSystem());
				}
				if (caseNotificationDataDT.getOriginalText() != null && caseNotificationDataDT.getOriginalText().trim().length() > 0) {
					cweData.setCweOriginalText(caseNotificationDataDT.getOriginalText());
				}
			// HL7 CE -> Coded element
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_CE)) {
				CeDataType ceData = dataElement.addNewCeDataType();
				ceData.setCeCodedValue(caseNotificationDataDT.getCodedValue());
				ceData.setCeCodedValueDescription(caseNotificationDataDT.getCodedValueDescription());
				ceData.setCeCodedValueCodingSystem(caseNotificationDataDT.getCodedValueCodingSystem());
				ceData.setCeLocalCodedValue(caseNotificationDataDT.getLocalCodedValue());
				ceData.setCeLocalCodedValueDescription(caseNotificationDataDT.getLocalCodedValueDescription());
				ceData.setCeLocalCodedValueCodingSystem(caseNotificationDataDT.getLocalCodedValueCodingSystem());
			// HL7 TS -> Time stamp
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_TS)) {
				TsDataType tsData = dataElement.addNewTsDataType();

				// If only 4 places, assume just year and store in Year element (for MMWR Year)
				if ((caseNotificationDataDT.getAnswerTxt() != null) &&
					caseNotificationDataDT.getAnswerTxt().length() == 4) {
					tsData.setYear(caseNotificationDataDT.getAnswerTxt());
				}
				// Otherwise format and store
				else if (caseNotificationDataDT.getAnswerTxt() != null && !caseNotificationDataDT.getAnswerTxt().isEmpty()) {
					tsData.setTime(convertNBSDateToCalendar(caseNotificationDataDT.getAnswerTxt()));
				}
			// HL7 DT -> Date
			} else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_DT)) {
				DtDataType dtData = dataElement.addNewDtDataType();
				// If only 4 places, assume just year and store in Year element (for MMWR Year)
				if ((caseNotificationDataDT.getAnswerTxt() != null) &&
					caseNotificationDataDT.getAnswerTxt().length() == 4) {
					dtData.setYear(caseNotificationDataDT.getAnswerTxt());
				}
				// Otherwise format and store
				else if (caseNotificationDataDT.getAnswerTxt() != null) {
						dtData.setDate(convertNBSDateToCalendar(caseNotificationDataDT.getAnswerTxt()));
				}
			// HL7 NM -> Numeric
			}else if (caseNotificationDataDT.getQuestionDataTypeNND().equals(NEDSSConstants.NND_HL7_DATATYPE_NM)) {
				NmDataType nwData = dataElement.addNewNmDataType();
				nwData.setNum(caseNotificationDataDT.getAnswerTxt());
			}
			else {
				String errString = "MessageBuilderHelper.populateIntermediaryMessageWithCaseData:  Unsupported NND data type for " +
						caseNotificationDataDT.getQuestionIdentifier() + ":  " + caseNotificationDataDT.getQuestionDataTypeNND() +
						", Current CaseNotificationDT:  " + caseNotificationDataDT.toString();
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}

			return nndIntMessageElement;
		} catch (Exception e) {
			String errString = "MessageBuilderHelper.populateIntermediaryMessageWithCaseData: questionIdentifier:  "+caseNotificationDataDT.getQuestionIdentifier()+" " + e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
	}



	private void parseStructuredNumeric(String structuredNumeric, SnDataType snData, SnunitDataType snunitDataType) {

		if (structuredNumeric.contains("^")) {
			structuredNumeric = structuredNumeric.split("\\^")[0];
		}

		// numeric = \\d*
		// comparator = [<>=][=>]?
		// separator = [/-:+]

		String num1 = "";
		String comparator = "";
		String num2 = "";
		String separatorSuffix = "";

		// [A number]
		if (structuredNumeric.matches("^[0-9]*\\.?[0-9]+$")) {
			num1 = structuredNumeric;
		} else
		// [Comparator][A number]
		if (structuredNumeric.matches("^[<>=]{1}[=>]?[0-9]*\\.?[0-9]+$")) {
			Pattern pattern = Pattern.compile("([<>=]{1}[=>]?)([0-9]*\\.?[0-9]+)");
		    Matcher matcher = pattern.matcher(structuredNumeric);

		    if(matcher.matches()) {
		    	comparator = matcher.group(1);
				num1 = matcher.group(2);
		    }
		} else
		// [A number][Separator/-:][A number]
		if (structuredNumeric.matches("^[0-9]*\\.?[0-9]+[/\\-:]{1}[0-9]*\\.?[0-9]+$")) {
			Pattern pattern = Pattern.compile("([0-9]*\\.?[0-9]+)([/\\-:]{1})([0-9]*\\.?[0-9]+)");
		    Matcher matcher = pattern.matcher(structuredNumeric);

		    if(matcher.matches()) {
				num1 = matcher.group(1);
				separatorSuffix = matcher.group(2);
				num2 = matcher.group(3);
		    }
		} else
		// [A number][Separator+]
		if (structuredNumeric.matches("^[0-9]*\\.?[0-9]+[+]{1}$")) {
			Pattern pattern = Pattern.compile("([0-9]*\\.?[0-9]+)([+]{1})");
		    Matcher matcher = pattern.matcher(structuredNumeric);

		    if(matcher.matches()) {
				num1 = matcher.group(1);
				separatorSuffix = matcher.group(2);
		    }
		} else
		// [Comparator][A number][Separator/-][A number]
		if (structuredNumeric.matches("^[<>=]{1}[=>]?[0-9]*\\.?[0-9]+[/\\-:][0-9]*\\.?[0-9]+$")) {
			Pattern pattern = Pattern.compile("([<>=]{1}[=>]?)([0-9]*\\.?[0-9]+)([/\\-:])([0-9]*\\.?[0-9]+)");
		    Matcher matcher = pattern.matcher(structuredNumeric);

		    if(matcher.matches()) {
				comparator = matcher.group(1);
				num1 = matcher.group(2);
				separatorSuffix = matcher.group(3);
				num2 = matcher.group(4);
		    }
		} else {
			String errString = "MessageBuilderHelper.parseStructuredNumeric:  Unsupported SN format: " + structuredNumeric;
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}

		if (snData != null) {
			snData.setNum1(num1);
			snData.setNum2(num2);
			snData.setComparator(comparator);
			snData.setSeparatorSuffix(separatorSuffix);
		} else if (snunitDataType != null) {
			snunitDataType.setNum1(num1);
			snunitDataType.setNum2(num2);
			snunitDataType.setComparator(comparator);
			snunitDataType.setSeparatorSuffix(separatorSuffix);
		} else {
			String errString = "MessageBuilderHelper.parseStructuredNumeric:  Problem parsing: " + structuredNumeric + ", snData and snunitData were both NULL.";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}


	}

	private void parseStructuredNumericLab(String structuredNumeric, SnunitDataType snunitDataType) {

		

		// numeric = \\d*
		// comparator = [<>=][=>]?
		// separator = [/-:+]

		String num1 = "";
		String comparator = "";
		String num2 = "";
		String separatorSuffix = "";
		
		if (structuredNumeric.contains("^")) {
			structuredNumeric = structuredNumeric.replace("^", " ^ ");
			num1 = structuredNumeric.split("\\^")[0];
			comparator = structuredNumeric.split("\\^")[1];
			num2 = structuredNumeric.split("\\^")[2];
			separatorSuffix = structuredNumeric.split("\\^")[3];
			
			if (snunitDataType != null) {
				snunitDataType.setNum1(num1.trim());
				snunitDataType.setNum2(num2.trim());
				snunitDataType.setComparator(comparator.trim());
				snunitDataType.setSeparatorSuffix(separatorSuffix.trim());
			} else {
				String errString = "MessageBuilderHelper.parseStructuredNumeric:  Problem parsing: " + structuredNumeric + ", snData and snunitData were both NULL.";
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}

		}
		else 
			snunitDataType.setNum1(structuredNumeric);

		

	}
	
private void parseStructuredNumericLabWithoutUnit(String structuredNumeric,  SnDataType snData, SnunitDataType snunitDataType) {

		// numeric = \\d*
		// comparator = [<>=][=>]?
		// separator = [/-:+]

		String num1 = "";
		String comparator = "";
		String num2 = "";
		String separatorSuffix = "";
		
		if (structuredNumeric.contains("^")) {
			structuredNumeric = structuredNumeric.replace("^", " ^ ");
			num1 = structuredNumeric.split("\\^")[0];
			comparator = structuredNumeric.split("\\^")[1];
			num2 = structuredNumeric.split("\\^")[2];
			separatorSuffix = structuredNumeric.split("\\^")[3];
			
			if (snData != null) {
				snData.setNum1(num1.trim());
				snData.setNum2(num2.trim());
				snData.setComparator(comparator.trim());
				snData.setSeparatorSuffix(separatorSuffix.trim());
			} else {
				String errString = "MessageBuilderHelper.parseStructuredNumeric:  Problem parsing: " + structuredNumeric + ", snData and snunitData were both NULL.";
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}

		}
		else 
			snData.setNum1(structuredNumeric);

		

	}

	private SnunitDataType parseStructuredNumericUnit(CaseNotificationDataDT caseNotificationDataDT, DataElement dataElement) {
		SnunitDataType snunitDataType = null;
		String structuredNumeric = caseNotificationDataDT.getAnswerTxt();

		if (caseNotificationDataDT.getUnitTypeCd() != null && caseNotificationDataDT.getUnitTypeCd().equalsIgnoreCase(NEDSSConstants.CODED) ) {
			if (structuredNumeric != null && structuredNumeric.indexOf("^") > 0) {
				//This means we have a coded unit, need to do code lookup
				String code = structuredNumeric.split("\\^")[1];
				Long codeSetGroupId = Long.parseLong(caseNotificationDataDT.getUnitValue());
				Coded codeLookup = new Coded(code, codeSetGroupId);
				if (codeLookup == null || codeLookup.getCodeSystemCd() == null || codeLookup.getCodeSystemCd().trim().length() < 1) {
					String errString = "MessageBuilderHelper.parseStructuredNumericUnit:  Unit code lookup failed.  CaseNotificationDT = " + caseNotificationDataDT.toString();
					logger.error(errString);
					throw new NEDSSSystemException(errString);
				}
				snunitDataType = dataElement.addNewSnunitDataType();
				if (codeLookup.getCodeSystemCd().equalsIgnoreCase(NEDSSConstants.NND_LOCALLY_CODED)) {
					snunitDataType.setCeLocalCodedValue(codeLookup.getCode());
					snunitDataType.setCeLocalCodedValueDescription(codeLookup.getCodeDescription());
					snunitDataType.setCeLocalCodedValueCodingSystem(codeLookup.getCodeSystemCd());
				} else {
					snunitDataType.setCeCodedValue(codeLookup.getCode());
					snunitDataType.setCeCodedValueDescription(codeLookup.getCodeDescription());
					snunitDataType.setCeCodedValueCodingSystem(codeLookup.getCodeSystemCd());
				}
			} else {
				String errString = "MessageBuilderHelper.parseStructuredNumericUnit:  Unit code is missing.  CaseNotificationDT = " + caseNotificationDataDT.toString();
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}
		} else if (caseNotificationDataDT.getUnitTypeCd() != null && caseNotificationDataDT.getUnitTypeCd().equalsIgnoreCase(NEDSSConstants.LITERAL) ){
			//Literal, just parse into three parts - coded, value, codeSystem
			String literalCoding = caseNotificationDataDT.getUnitValue();
			boolean error = false;
			String code="";
			String codeDescription="";
			String codeSystemCode="";

			if (literalCoding != null && literalCoding.indexOf("^") > 0) {
				try {
					code = literalCoding.split("\\^")[0];
					codeDescription = literalCoding.split("\\^")[1];
					codeSystemCode = literalCoding.split("\\^")[2];
				} catch (Exception e) {
					// Problem parsing the three components
					error = true;
				}

				snunitDataType = dataElement.addNewSnunitDataType();

				if (codeSystemCode != null && codeSystemCode.equalsIgnoreCase(NEDSSConstants.NND_LOCALLY_CODED)) {
					snunitDataType.setCeLocalCodedValue(code);
					snunitDataType.setCeLocalCodedValueDescription(codeDescription);
					snunitDataType.setCeLocalCodedValueCodingSystem(codeSystemCode);
				} else {
					snunitDataType.setCeCodedValue(code);
					snunitDataType.setCeCodedValueDescription(codeDescription);
					snunitDataType.setCeCodedValueCodingSystem(codeSystemCode);
					}
			} else {
				error = true;
			}

			if (error) {
				String errString = "MessageBuilderHelper.parseStructuredNumericUnit:  Unit code literal is invalid:  " + caseNotificationDataDT.getUnitValue() + " - CaseNotificationDT = " + caseNotificationDataDT.toString();
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}
		}

		return snunitDataType;
	}



	/**
	 * retrieveLabAnswersFromView(): method for creating one caseNotificationDT per each lab per each caseNotificationDT read from the metadata.
	 * If there are 6 caseNotificationDT read from the metadata, and 2 lab, the result will be 12 (6x2) caseNotificationDT where the difference between
	 * each lab is the observationSubID (incremented by 1). TODO: update this comment
	 * @param caseDataDTCollectionVacc
	 * @param vaccinationDTCollection
	 * @return
	 */
	
	public Collection<Object> retrieveLabAnswersFromView(Collection<Object> caseDataDTCollectionVacc, Collection<Object> vaccinationDTCollection){
		
		ArrayList<Object> caseDataDTCollectionVaccWithAnswers = new ArrayList();
		
		return caseDataDTCollectionVaccWithAnswers;
	}
	
	public void codeLookupforMessageDataLab(Collection<Object> caseNotificationDataDTCollection) {
		return;
		
	}
	
	
	
	/**
	 * retrieveVaccinationAnswersFromView(): method for creating one caseNotificationDT per each vaccination per each caseNotificationDT read from the metadata.
	 * If there are 6 caseNotificationDT read from the metadata, and 2 vaccinations, the result will be 12 (6x2) caseNotificationDT where the difference between
	 * each vaccination is the observationSubID (incremented by 1)
	 * @param caseDataDTCollectionVacc
	 * @param vaccinationDTCollection
	 * @return
	 */
	
	public Collection<Object> retrieveVaccinationAnswersFromView(Collection<Object> caseDataDTCollectionVacc, Collection<Object> vaccinationDTCollection){
		
		ArrayList<Object> caseDataDTCollectionVaccWithAnswers = new ArrayList();
		
		if(vaccinationDTCollection!=null){
			
			Iterator<Object> it = vaccinationDTCollection.iterator();
			int index=1;
			
			while(it.hasNext()){
				insertVaccinationIntoCaseDataDT(caseDataDTCollectionVaccWithAnswers, caseDataDTCollectionVacc, (VaccinationDT)it.next(), index);
				index++;
			}
		}
		return caseDataDTCollectionVaccWithAnswers;
	}
	
	
	

	
	public void insertVaccinationIntoCaseDataDT(ArrayList<Object> caseDataDTCollectionVaccWithAnswers, Collection<Object> caseDataDTCollectionVacc, VaccinationDT vaccination, int index){
		
		if(caseDataDTCollectionVacc!=null){
			
			Iterator<Object> it = caseDataDTCollectionVacc.iterator();
			
			while(it.hasNext()){
				
				CaseNotificationDataDT notificationDT = (CaseNotificationDataDT)it.next();
				CaseNotificationDataDT notificationDTNew = new CaseNotificationDataDT();
				String questionIdentifier = notificationDT.getQuestionIdentifier();
				String answer="", codedValue="";
				Timestamp answerDate;
				copyNotification(notificationDT, notificationDTNew);
				
				switch(questionIdentifier){
					
				case "30956_7"://Vaccine Type
				case "VAC101":
					answer=vaccination.getVaccType();
					codedValue=vaccination.getVaccTypeCd();
					if(codedValue == null || codedValue.equalsIgnoreCase("null"))
						continue;
					notificationDTNew.setObservationSubID(index);
					if (getVaccineNameConceptCode(codedValue) != null)
						notificationDTNew.setCodedValue(getVaccineNameConceptCode(codedValue));
					else
						notificationDTNew.setCodedValue(codedValue);
					if (getVaccineNamePreferredName(codedValue) != null) {
						notificationDTNew.setAnswerTxt(getVaccineNamePreferredName(codedValue));
						notificationDTNew.setCodedValueDescription(getVaccineNamePreferredName(codedValue));
					}else {
						notificationDTNew.setAnswerTxt(answer);
						notificationDTNew.setCodedValueDescription(answer);
					}
					if (getVaccineNameOid(codedValue) != null)
						notificationDTNew.setCodedValueCodingSystem(getVaccineNameOid(codedValue));

					break;
				case "30957_5"://Vaccine Manufacturer
				case "VAC107":
					answer=vaccination.getVaccMfgr();
					codedValue=vaccination.getVaccMfgrCd()+"";
					if(codedValue == null || codedValue.equalsIgnoreCase("null"))
						continue;
					notificationDTNew.setObservationSubID(index);
					if (getVaccineManufacturerConceptCode(codedValue) != null)
						notificationDTNew.setCodedValue(getVaccineManufacturerConceptCode(codedValue));
					else
						notificationDTNew.setCodedValue(codedValue);
					if (getVaccineManufacturerPreferredName(codedValue) != null) {
						notificationDTNew.setAnswerTxt(getVaccineManufacturerPreferredName(codedValue));
						notificationDTNew.setCodedValueDescription(getVaccineManufacturerPreferredName(codedValue));
					} else {
						notificationDTNew.setAnswerTxt(answer);
						notificationDTNew.setCodedValueDescription(answer);
					}
					if (getVaccineManufacturerOid(codedValue) != null)
						notificationDTNew.setCodedValueCodingSystem(getVaccineManufacturerOid(codedValue));
					break;
				case "30952_6"://Vaccine Administered Date
				case "VAC103":
					answerDate=vaccination.getVaccAdminDt();
					if(answerDate!=null){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						answer = dateFormat.format(answerDate);
					}
					else
						continue;
					notificationDTNew.setAnswerTxt(answer);
					notificationDTNew.setObservationSubID(index);
					break;
				case "30959_1"://Vaccine Lot number
				case "VAC108":
					answer=vaccination.getVaccLotNbr();
					if(answer == null || answer.isEmpty())
						continue;
					notificationDTNew.setAnswerTxt(answer);
					notificationDTNew.setObservationSubID(index);

					break;
				case "VAC120"://Vaccine Dose number
					if (vaccination.getVaccDoseNbr() == null)
						continue;
					answer=vaccination.getVaccDoseNbr().toString();
					notificationDTNew.setAnswerTxt(answer);
					notificationDTNew.setObservationSubID(index);
					if(answer == null || answer.isEmpty())
						continue;
					break;					
				case "VAC109"://Vaccine Expiration date
					answerDate=vaccination.getVaccExpDt();
					if(answerDate!=null){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						answer = dateFormat.format(answerDate);
					}
					else
						continue;
					notificationDTNew.setAnswerTxt(answer);
					notificationDTNew.setObservationSubID(index);
					break;
				case "VAC102"://Vaccine Record Identifier
					answer=vaccination.getVaccLocalId();
					notificationDTNew.setAnswerTxt(answer);
					notificationDTNew.setObservationSubID(index);
					break;
					
				case "VAC104"://Vaccine Anatomical Site
					answer=vaccination.getVaccBodySite();
					codedValue=vaccination.getVaccBodySiteCd();
					if(codedValue == null || codedValue.equalsIgnoreCase("null"))
						continue;
					notificationDTNew.setObservationSubID(index);
					
					if (getVaccineAnatomicSiteConceptCode(codedValue) != null)
						notificationDTNew.setCodedValue(getVaccineAnatomicSiteConceptCode(codedValue));
					else
						notificationDTNew.setCodedValue(codedValue);
					if (getVaccineAnatomicSitePreferredName(codedValue) != null) {
						notificationDTNew.setAnswerTxt(getVaccineAnatomicSitePreferredName(codedValue));
						notificationDTNew.setCodedValueDescription(getVaccineAnatomicSitePreferredName(codedValue));
					} else {
						notificationDTNew.setAnswerTxt(answer);
						notificationDTNew.setCodedValueDescription(answer);
					}
					if (getVaccineAnatomicSiteOid(codedValue) != null)
						notificationDTNew.setCodedValueCodingSystem(getVaccineAnatomicSiteOid(codedValue));
					break;					
				
				case "VAC147"://Vaccine Event Information Source
					answer=vaccination.getVaccInfoSource();
					codedValue=vaccination.getVaccInfoSourceCd();
					if(codedValue == null || codedValue.equalsIgnoreCase("null"))
						continue;
					notificationDTNew.setObservationSubID(index);
					
					if (getVaccineInformationSourceConceptCode(codedValue) != null)
						notificationDTNew.setCodedValue(getVaccineInformationSourceConceptCode(codedValue));
					else
						notificationDTNew.setCodedValue(codedValue);
					
					//get the concept code and the preferred name and OID
					if (getVaccineInformationSourcePreferredName(codedValue) != null) {
						notificationDTNew.setAnswerTxt(getVaccineInformationSourcePreferredName(codedValue));
						notificationDTNew.setCodedValueDescription(getVaccineInformationSourcePreferredName(codedValue));
					} else {
						notificationDTNew.setAnswerTxt(answer);
						notificationDTNew.setCodedValueDescription(answer);
					}
					if (getVaccineInformationSourceOid(codedValue) != null)
						notificationDTNew.setCodedValueCodingSystem(getVaccineInformationSourceOid(codedValue));
					break;					
				}
				caseDataDTCollectionVaccWithAnswers.add(notificationDTNew);
			}		
		}
	}
	
	public void copyNotification (CaseNotificationDataDT notificationDT, CaseNotificationDataDT notificationNew){
		
		notificationNew.setAnswerGroupSeqNbr(notificationDT.getAnswerGroupSeqNbr());
		notificationNew.setAnswerLargeTxt(notificationDT.getAnswerLargeTxt());
		notificationNew.setBatchTableColumnWidth(notificationDT.getBatchTableColumnWidth());
		notificationNew.setBatchTableHeader(notificationDT.getBatchTableHeader());
		notificationNew.setClassCd(notificationDT.getClassCd());
		notificationNew.setCodedValue(notificationDT.getCodedValue());
		notificationNew.setCodedValueCodingSystem(notificationDT.getCodedValueCodingSystem());
		notificationNew.setCodedValueDescription(notificationDT.getCodedValueDescription());
		notificationNew.setCodesetGroupId(notificationDT.getCodesetGroupId());
		notificationNew.setCodeSetNm(notificationDT.getCodeSetNm());
		notificationNew.setDataCd(notificationDT.getDataCd());
		notificationNew.setDataLocation(notificationDT.getDataLocation());
		notificationNew.setDataType(notificationDT.getDataType());
		notificationNew.setDataUseCd(notificationDT.getDataUseCd());
		notificationNew.setHl7SegmentField(notificationDT.getHl7SegmentField());
		notificationNew.setLegacyDataLocation(notificationDT.getLegacyDataLocation());
		notificationNew.setLocalCodedValue(notificationDT.getLocalCodedValue());
		notificationNew.setLocalCodedValueCodingSystem(notificationDT.getLocalCodedValueCodingSystem());
		notificationNew.setLocalCodedValueDescription(notificationDT.getLocalCodedValueDescription());
		notificationNew.setNbsQuestionLabel(notificationDT.getNbsQuestionLabel());
		notificationNew.setOrderGroupId(notificationDT.getOrderGroupId());
		notificationNew.setOriginalText(notificationDT.getOriginalText());
		notificationNew.setOtherValueIndCd(notificationDT.getOtherValueIndCd());
		notificationNew.setPartTypeCd(notificationDT.getPartTypeCd());
		notificationNew.setQuestionDataTypeNND(notificationDT.getQuestionDataTypeNND());
		notificationNew.setQuestionGroupSeqNbr(notificationDT.getQuestionGroupSeqNbr());
		notificationNew.setQuestionIdentifier(notificationDT.getQuestionIdentifier());
		notificationNew.setQuestionIdentifierNND(notificationDT.getQuestionIdentifierNND());
		notificationNew.setQuestionLabelNND(notificationDT.getQuestionLabelNND());
		notificationNew.setQuestionOID(notificationDT.getQuestionOID());
		notificationNew.setQuestionOIDSystemTxt(notificationDT.getQuestionOIDSystemTxt());
		notificationNew.setQuestionOrderNND(notificationDT.getQuestionOrderNND());
		notificationNew.setQuestionRequiredNND(notificationDT.getQuestionRequiredNND());
		notificationNew.setQuestionGroupSeqNbr(notificationDT.getQuestionGroupSeqNbr());
		notificationNew.setQuestionIdentifier(notificationDT.getQuestionIdentifier());
		notificationNew.setQuestionIdentifierNND(notificationDT.getQuestionIdentifierNND());
		notificationNew.setQuestionLabelNND(notificationDT.getQuestionLabelNND());
		notificationNew.setQuestionOID(notificationDT.getQuestionOID());
		notificationNew.setQuestionOIDSystemTxt(notificationDT.getQuestionOIDSystemTxt());
		notificationNew.setQuestionOrderNND(notificationDT.getQuestionOrderNND());
		notificationNew.setQuestionRequiredNND(notificationDT.getQuestionRequiredNND());
		notificationNew.setQuestionUnitIdentifier(notificationDT.getQuestionUnitIdentifier());
		notificationNew.setTranslationTableNm(notificationDT.getTranslationTableNm());
		notificationNew.setUiMetadataQuestionLabel(notificationDT.getUiMetadataQuestionLabel());
		notificationNew.setUnitParentIdentifier(notificationDT.getUnitParentIdentifier());
		notificationNew.setUnitTypeCd(notificationDT.getUnitTypeCd());
		notificationNew.setUnitValue(notificationDT.getUnitValue());
		notificationNew.setXmlDataType(notificationDT.getXmlDataType());
		notificationNew.setXmlPath(notificationDT.getXmlPath());
		notificationNew.setXmlTag(notificationDT.getXmlTag());
		

	}
	
	
	public void codeLookupforMessageData(Collection<Object> caseNotificationDataDTCollection) {
		try {
			NotificationSRTCodeLookupTranslationDAOImpl srtCodeLookUpDAO = new NotificationSRTCodeLookupTranslationDAOImpl();
		//	boolean HDFound = false;
		//	String hdQuestionId = null;
			Iterator<Object> it = caseNotificationDataDTCollection.iterator();
			while (it.hasNext()) {
				Object object = it.next();
				if (object instanceof CaseNotificationDataDT) {
					CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)object;
					String hl7DataType = caseNotificationDataDT.getQuestionDataTypeNND();
					String dataType = caseNotificationDataDT.getDataType();
					String translationTableName = caseNotificationDataDT.getTranslationTableNm();

					logger.info("hl7DataType: "+hl7DataType+" dataType: "+dataType+" translationTableName: "+translationTableName);
					// Look for CWE and CE data types for creating HL7 NND messages and for Coded data types for
					// Case Report Export/Share messages to do an SRT lookup for CodedValueDescription and CodedValueCoding System
					if (((hl7DataType != null) && ((hl7DataType.equals(NEDSSConstants.NND_HL7_DATATYPE_CWE)) ||
							                       (hl7DataType.equals(NEDSSConstants.NND_HL7_DATATYPE_CE)))) ||
							                       ((dataType != null) && ((dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_CODED))))) {

						if ( (dataType != null && dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_CODED) && caseNotificationDataDT.getAnswerTxt().startsWith("OTH^")) ||
							 (hl7DataType != null && hl7DataType.equals(NEDSSConstants.NND_HL7_DATATYPE_CWE) &&
							  caseNotificationDataDT.getAnswerTxt() != null && caseNotificationDataDT.getAnswerTxt().startsWith("OTH^")) ) {
								//Other text found - goes in Original Text - OBX 5.9
								caseNotificationDataDT.setCodedValue("OTH");
								if (caseNotificationDataDT.getAnswerTxt().trim().length() > 4) {
									caseNotificationDataDT.setOriginalText(caseNotificationDataDT.getAnswerTxt().split("\\^")[1]);
								} else {
									caseNotificationDataDT.setOriginalText("");
								}
								logger.info("OriginalText: "+caseNotificationDataDT.getOriginalText());
						} else {
							caseNotificationDataDT.setCodedValue(caseNotificationDataDT.getAnswerTxt());
							logger.info("CodedValue: "+caseNotificationDataDT.getAnswerTxt());
						}

						// Have to ignore "NA" in code_set_group_id = "2200" PHC_CONF_M as this is an "unauthorized" addition - need to remove
					    if (caseNotificationDataDT.getCodesetGroupId() != null && caseNotificationDataDT.getCodesetGroupId().compareTo(new Long(2200)) == 0 && caseNotificationDataDT.getAnswerTxt().equals("NA")) {
					    	logger.info("Ignoring NA in code_set_group_id = 2200 PHC_CONF_M as this is an unauthorized addition");
					    	it.remove();
					    	continue;
					    }	
					    
					 // Have to ignore "NULL" or "" for SPM-4.0
					    else if (caseNotificationDataDT.getHl7SegmentField() != null && caseNotificationDataDT.getHl7SegmentField().equalsIgnoreCase("SPM-4.0") 
					    		&& (caseNotificationDataDT.getAnswerTxt() == null || caseNotificationDataDT.getAnswerTxt().trim().equals(""))) {
					    	logger.info("Ignoring NULL or '' for SPM-4.0");
					    	it.remove();
					    	continue;
					    }	
					   else if (caseNotificationDataDT.getCodesetGroupId() != null)
					    	srtCodeLookUpDAO.retrieveCodeDescAndCodingSystemWithCodesetGroupId(caseNotificationDataDT);
					}
					else if (hl7DataType != null && hl7DataType.equals(NEDSSConstants.NND_HL7_DATATYPE_HD)) {
						caseNotificationDataDT.setCodedValue(caseNotificationDataDT.getAnswerTxt());
						if (caseNotificationDataDT.getCodesetGroupId() != null){
					    	srtCodeLookUpDAO.retrieveCodeDescAndCodingSystemWithCodesetGroupId(caseNotificationDataDT);
						} 
					    	//  if code_system_code is 'L' then move code, code description and code system code to local, otherwise
					        // it is a standardized code and should be in code, code description, code system code
					        if (caseNotificationDataDT.getLocalCodedValueCodingSystem() != null && caseNotificationDataDT.getLocalCodedValue()!= null) {
					        	logger.info("Setting local values: NamespaceId: "+caseNotificationDataDT.getLocalCodedValue()
					        			+" UniversalId: "+caseNotificationDataDT.getLocalCodedValueCodingSystem()
					        			+" UniversalIdType: "+caseNotificationDataDT.getLocalCodedValueDescription());
					        	
					        	caseNotificationDataDT.setNamespaceId(caseNotificationDataDT.getLocalCodedValue());
								caseNotificationDataDT.setUniversalId(caseNotificationDataDT.getLocalCodedValueCodingSystem());
								caseNotificationDataDT.setUniversalIdType(caseNotificationDataDT.getLocalCodedValueDescription());
					        } else if(caseNotificationDataDT.getCodedValueCodingSystem() != null && caseNotificationDataDT.getCodedValue()!= null) {
					        	logger.info("Setting Coded values: NamespaceId: "+caseNotificationDataDT.getCodedValue()
					        			+" UniversalId: "+caseNotificationDataDT.getCodedValueCodingSystem()
					        			+" UniversalIdType: "+caseNotificationDataDT.getCodedValueDescription());
					        	
					        	caseNotificationDataDT.setNamespaceId(caseNotificationDataDT.getCodedValue());
								caseNotificationDataDT.setUniversalId(caseNotificationDataDT.getCodedValueCodingSystem());
								caseNotificationDataDT.setUniversalIdType(caseNotificationDataDT.getCodedValueDescription());
					        }
						
					}
					
					// Look for translationTableNm and use it to do a code translation if found
					if ((translationTableName != null)
							&& (translationTableName.length() > 0)) {

						if (caseNotificationDataDT.getLocalCodedValue() != null && caseNotificationDataDT.getLocalCodedValue().trim().length() < 1) {
							// If not already there, copy existing codes to localCoded - going to retrieve the standard codes from SRT
							logger.info("LocalCoded: localCodedValue: "+caseNotificationDataDT.getCodedValue()+" localCodedValueCodingSystem: "
									+NEDSSConstants.NND_LOCALLY_CODED+" localCodedValueDescription: "+caseNotificationDataDT.getCodedValueDescription());
							caseNotificationDataDT.setLocalCodedValue(caseNotificationDataDT.getCodedValue());
							caseNotificationDataDT.setLocalCodedValueCodingSystem(NEDSSConstants.NND_LOCALLY_CODED); // Set to L for local
							caseNotificationDataDT.setLocalCodedValueDescription(caseNotificationDataDT.getCodedValueDescription());
						}

						// Retrieve standard codes from translation table
						srtCodeLookUpDAO.translateNBSCodetoPHINCode(caseNotificationDataDT);
					}

			    	//If locally coded, move to locally coded fields
		    		if (caseNotificationDataDT.getCodedValueCodingSystem() != null && caseNotificationDataDT.getCodedValueCodingSystem().equalsIgnoreCase(NEDSSConstants.NND_LOCALLY_CODED))
			    	{
		    			logger.info("Locally coded, moving to locally coded fields: localCodedValue: "+caseNotificationDataDT.getCodedValue()
		    					+" localCodedValueCodingSystem: "+caseNotificationDataDT.getCodedValueCodingSystem()
		    					+" localCOdedValueDescription: "+caseNotificationDataDT.getCodedValueDescription());
		    			
		    			caseNotificationDataDT.setLocalCodedValue(caseNotificationDataDT.getCodedValue());
		    			caseNotificationDataDT.setLocalCodedValueCodingSystem(caseNotificationDataDT.getCodedValueCodingSystem());
		    			caseNotificationDataDT.setLocalCodedValueDescription(caseNotificationDataDT.getCodedValueDescription());
			    		//clear previous values
		    			caseNotificationDataDT.setCodedValue("");
		    			caseNotificationDataDT.setCodedValueCodingSystem("");
		    			caseNotificationDataDT.setCodedValueDescription("");
			    	}
				}
			}
			
			/*if(HDFound){
				while (it.hasNext()) {
					Object object = it.next();
					if (object instanceof CaseNotificationDataDT) {
						CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)object;
						String hl7DataType = caseNotificationDataDT.getQuestionDataTypeNND();
						if (hl7DataType != null && hl7DataType.equals(NEDSSConstants.NND_HL7_DATATYPE_CX)) {
							
						}	
					}
				}
			}*/
		} catch (Exception e) {
			String errString = "MessageBuilderHelper.codeLookupforMessageData:  " + e.getMessage();
			logger.fatal(errString);
			throw new NEDSSSystemException(errString, e);
		}
	}

	
	
	
	/**
	 * NOTE: TRANSLATION TABLE NO LONGER USED starting with release 5.2
	 * codeLookupforMessageDataVaccination(): method for inserting the corresponding coded value in case the data type is Coded and also, if the translation table
	 * has a value different than null, the value is translated according to the translation table. 
	 * @param caseNotificationDataDTCollection
	 */
	
	public void codeLookupforMessageDataVaccination(Collection<Object> caseNotificationDataDTCollection) {
		return;
		/*
		try {
			NotificationSRTCodeLookupTranslationDAOImpl srtCodeLookUpDAO = new NotificationSRTCodeLookupTranslationDAOImpl();

			Iterator<Object> it = caseNotificationDataDTCollection.iterator();
			while (it.hasNext()) {
				Object object = it.next();
				if (object instanceof CaseNotificationDataDT) {
					CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)object;
					String hl7DataType = caseNotificationDataDT.getQuestionDataTypeNND();
					String dataType = caseNotificationDataDT.getDataType();
					String translationTableName = caseNotificationDataDT.getTranslationTableNm();


					// Look for CWE and CE data types for creating HL7 NND messages and for Coded data types for
					// Case Report Export/Share messages to do an SRT lookup for CodedValueDescription and CodedValueCoding System
					if (((hl7DataType != null) && ((hl7DataType.equals(NEDSSConstants.NND_HL7_DATATYPE_CWE)) ||
							                       (hl7DataType.equals(NEDSSConstants.NND_HL7_DATATYPE_CE)))) ||
							                       ((dataType != null) && ((dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_CODED))))) {
							caseNotificationDataDT.setCodedValue(caseNotificationDataDT.getCodedValue());
			    			caseNotificationDataDT.setCodedValueCodingSystem(caseNotificationDataDT.getQuestionOID());
			    			caseNotificationDataDT.setCodedValueDescription(caseNotificationDataDT.getAnswerTxt());
					}

					// Look for translationTableNm and use it to do a code translation if found
					if ((translationTableName != null)
							&& (translationTableName.length() > 0)) {
							caseNotificationDataDT.setCodeSetNm("VAC_MFGR_LOCAL");
							caseNotificationDataDT.setLocalCodedValue(caseNotificationDataDT.getCodedValue());
							
							//In case there's no translation, we dont keep the previous value
							caseNotificationDataDT.setCodedValue("");
			    			caseNotificationDataDT.setCodedValueCodingSystem("");
			    			caseNotificationDataDT.setCodedValueDescription("");
			    			
							// Retrieve standard codes from translation table
							srtCodeLookUpDAO.translateNBSCodetoPHINCode(caseNotificationDataDT);
							caseNotificationDataDT.setLocalCodedValue(null);
					}
				}
			}
		} catch (Exception e) {
			String errString = "MessageBuilderHelper.codeLookupforMessageData:  " + e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
		*/
	}

	
	
	public void retrieveNonNBSAnswerData(Collection<Object> caseNotificationDataDTCollection, TreeMap<Object, Object> entityUIDMap, Long publicHealthCaseUid, Long notificationUid, int nTypeObs) {
		HashMap<Object, Object> retrievalGroupsMap = new HashMap<Object, Object>();

		// Iterate through collection and find caseNotificationDT's that have a
		// data location <> "NBS_Answer.answer_txt" and remove from CaseNotificationDataDTCollection,
		// then add it to Retrieval HashMap<Object, Object><Object,Object> in groups.
		// Groups = unique by table name, ELP.cd, ELP.use_cd and participation type
		if (caseNotificationDataDTCollection  != null) {
			Iterator<Object> itr = caseNotificationDataDTCollection.iterator();
			while (itr.hasNext()) {
				CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)itr.next();
				String dataLoc = caseNotificationDataDT.getDataLocation() == null ? "" : caseNotificationDataDT.getDataLocation();
				
				if (dataLoc.length() > 0 && !dataLoc.equalsIgnoreCase(NEDSSConstants.DATA_LOCATION_CASE_ANSWER_TEXT))
					if (dataLoc.indexOf(":") >= 0) { // If contains ":" it is from nedss.properties
						retrieveNedssProperty(caseNotificationDataDT);
					} else {
						retrievalGroupsMap = groupNonPamAnswerDataRetrieval(retrievalGroupsMap, caseNotificationDataDT);
						itr.remove(); // Remove this CaseNotificationDataDT from CaseNotificationDataDTCollection,
								      // we're going to replace it after looking up data from data location
					}
			}
		}

		// Now have Retrieval Groups - time to dynamically build query for each group to retrieve data
		Collection<Object>  updatedCaseNotificationDataDTCollection;
		Collection<Object>  groups = retrievalGroupsMap.values();
		ParticipationDAOImpl participationDAOImpl = new ParticipationDAOImpl();
		NotificationMessageDAOImpl notificationMessageDAOImpl = new NotificationMessageDAOImpl();
		Iterator<Object> groupItr = groups.iterator();

		// iterate through Groups and query for non Answer data
		while (groupItr.hasNext()) {
			updatedCaseNotificationDataDTCollection  = processGroupDataRetrieval(entityUIDMap, publicHealthCaseUid, (HashMap<?, ?>)groupItr.next(), notificationUid,nTypeObs);


			// Try and look up legacy data for CaseNotificationDataDT's that still have no answer_txt
			// data populated and have legacy_data_location populated. This will just be for
			// EXP_NOTF/EXP_SHARE (CR_FORM questions)
			Iterator<Object> itr = updatedCaseNotificationDataDTCollection.iterator();
			while (itr.hasNext()) {
				CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)(itr.next());
				if (caseNotificationDataDT.getLegacyDataLocation() != null &&
					caseNotificationDataDT.getLegacyDataLocation().trim().length() > 0 &&
					(caseNotificationDataDT.getAnswerTxt() == null || caseNotificationDataDT.getAnswerTxt().trim().length() == 0)) {
					// Have a CaseNotificationDataDT with no answer populated and we have a legacy data location
					//that we can check still Currently this will either be a Observation or Participation based attribute.
					String legacyTableName = getTableNameFromDataLocation(caseNotificationDataDT.getLegacyDataLocation()) == null ? "" : getTableNameFromDataLocation(caseNotificationDataDT.getLegacyDataLocation());
					String legacyColumnName = getColumnNameFromDataLocation(caseNotificationDataDT.getLegacyDataLocation()) == null ? "" : getColumnNameFromDataLocation(caseNotificationDataDT.getLegacyDataLocation());

					if (legacyTableName.toUpperCase().startsWith("OBS")) {
						//If we couldn't populate answer with legacy data, remove it from collection unless required
						if ( (caseNotificationDataDT.getAnswerTxt() == null || caseNotificationDataDT.getAnswerTxt().trim().length() == 0) &&
						     !caseNotificationDataDT.getQuestionRequiredNND().equalsIgnoreCase(NEDSSConstants.NND_REQUIRED_FIELD))
							itr.remove();

					} else if (legacyTableName.equalsIgnoreCase(DataTables.PARTICIPATION_TABLE)) {
						boolean found = false;
						Collection<Object>  participationCollection  = participationDAOImpl.loadAct(publicHealthCaseUid.longValue());
						Iterator<Object> pItr = participationCollection.iterator();
						while (pItr.hasNext() && !found) {
							ParticipationDT partDT = (ParticipationDT)(pItr.next());
							if (partDT.getTypeCd().equals(caseNotificationDataDT.getPartTypeCd())) {
								try {
									Class<?> participationDTClass = Class.forName("gov.cdc.nedss.association.dt.ParticipationDT");
									Method meth = (Method)participationDTClass.getMethod("get" + convertToCamelCase(legacyColumnName),  (Class[])null);
									Object obj = meth.invoke(partDT,  (Object[])null);
									if (obj != null) { // May not be populated
										caseNotificationDataDT.setAnswerTxt(obj.toString());
									}
									found = true;
								} catch (Exception e) {
									String errString = "MessageBuilderHelper.retrieveNonNBSAnswerData:  " + e.getMessage();
									logger.fatal(errString);
									throw new NEDSSSystemException(errString);
								}
							}
						}
						//If we couldn't populate answer with legacy data, remove it from collection unless required
						if ( !found && !caseNotificationDataDT.getQuestionRequiredNND().equalsIgnoreCase(NEDSSConstants.NND_REQUIRED_FIELD) ) {
							itr.remove();
						} else if ( !caseNotificationDataDT.getQuestionRequiredNND().equalsIgnoreCase(NEDSSConstants.NND_REQUIRED_FIELD) &&
							       (caseNotificationDataDT.getAnswerTxt() == null || caseNotificationDataDT.getAnswerTxt().trim().length() == 0) ) {
							itr.remove();
						}
					} else {
						//Have a bad legacy data location
						String errString = "NNDMessageBuilder.retrieveNonNBSAnswerData:  Invalid legacy data location:  " +
							legacyTableName + "." + legacyColumnName;
						logger.fatal(errString);
						throw new NEDSSSystemException(errString);
					}
				}
			}

			// add updated CaseNotificationDataDTs back into the collection
			caseNotificationDataDTCollection.addAll(updatedCaseNotificationDataDTCollection);
		}
	}

	// We are building groups which contain the columns for a row that we will be retrieving via a dynamic query
	private HashMap<Object, Object> groupNonPamAnswerDataRetrieval(HashMap<Object,Object> retrievalGroupsMap, CaseNotificationDataDT caseNotificationDataDT) {
		try {
			String tableName = getTableNameFromDataLocation(caseNotificationDataDT.getDataLocation()).toUpperCase();
			String columnName = getColumnNameFromDataLocation(caseNotificationDataDT.getDataLocation()).toUpperCase();
			String partType = caseNotificationDataDT.getPartTypeCd() == null ? "" : caseNotificationDataDT.getPartTypeCd().toUpperCase();
			String dataCd = caseNotificationDataDT.getDataCd() == null ? "" : caseNotificationDataDT.getDataCd().toUpperCase();
			String dataUseCd = caseNotificationDataDT.getDataUseCd() == null ? "" : caseNotificationDataDT.getDataUseCd().toUpperCase();

			// Key is Table name (parsed from Data Location) + Participation type_cd + data_cd + data_use_cd
			String groupKey = tableName + partType + dataCd + dataUseCd;

			// Value is an HashMap<Object, Object><Object,Object> of Columns - its key is Column name and its value is the CaseNotificationDataDT
			if (retrievalGroupsMap.get(groupKey) == null) { // If new key, add the new group
				HashMap<Object, Object> groupColumns = new HashMap<Object, Object>();
				Collection<Object>  caseNotificationDTCollection  = new ArrayList<Object> ();
				caseNotificationDTCollection.add(caseNotificationDataDT);
				groupColumns.put(columnName, caseNotificationDTCollection);
				retrievalGroupsMap.put(groupKey, groupColumns);
			} else { // If existing key - retrieve and add column to groupColumns map
				HashMap<Object, Object> groupColumns = (HashMap<Object, Object>)retrievalGroupsMap.get(groupKey);
				if (groupColumns.get(columnName) == null) { // test if column already exists
					Collection<Object>  caseNotificationDTCollection  = new ArrayList<Object> ();
					caseNotificationDTCollection.add(caseNotificationDataDT);
					groupColumns.put(columnName, caseNotificationDTCollection);
					retrievalGroupsMap.put(groupKey, groupColumns);
				} else {
					Collection<Object>  caseNotificationDTCollection  = (Collection<Object>)groupColumns.get(columnName);
					caseNotificationDTCollection.add(caseNotificationDataDT);
					groupColumns.put(columnName, caseNotificationDTCollection);
					retrievalGroupsMap.put(groupKey, groupColumns);
				}
			}

			return retrievalGroupsMap;
		} catch (Exception e) {
			String errString = "MessageBuilderHelper.groupNonPamAnswerDataRetrieval:  " + e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
	}


	private Collection<Object>  processGroupDataRetrieval(TreeMap<Object, Object> entityUIDMap, Long publicHealthCaseUid, HashMap<?,?> groupColumns, Long notificationUid, int nTypeObs) {
		Collection<Object>  updatedCaseNotificationDataDTCollection  = new ArrayList<Object> ();

		Collection<?>  columns = groupColumns.values();
		Iterator<?> columnsItr = columns.iterator();
		CaseNotificationDataDT currentCaseNotificationDataDT = null;

		if (columnsItr.hasNext()) {
			Collection<?>  columnDTCollection  = (Collection<?>)columnsItr.next();
			if (columnDTCollection.iterator().hasNext())
				currentCaseNotificationDataDT = (CaseNotificationDataDT)columnDTCollection.iterator().next(); // grab first DT for column
			else
				throw new NEDSSSystemException(
						"NNDMessageBuilder.processGroupDataRetrieval:  Invalid column meta data for non-NBS_Answer data retrieval.");
		} else
			throw new NEDSSSystemException(
					"NNDMessageBuilder.processGroupDataRetrieval:  Invalid column meta data for non-NBS_Answer data retrieval.");

		// Build where clause using table information in currentCaseNotificationDataDT
		String fromWhereClause = buildDynamicNonAnswerFromWhereClause(currentCaseNotificationDataDT,
																	  entityUIDMap,
																	  publicHealthCaseUid,
																	  notificationUid,nTypeObs);

		// If a participation_type was used to try and find an entity uid and it was unsuccessful,
		// the fromWhereClause will come back null and the rest of these operations are unnecessary - this
		// piece of data is missing
		if (fromWhereClause != null) {
			// Iterate through Column HashMap<Object, Object><Object,Object> and build column part of SQL Select
			String selectColumns = getColumnNameFromDataLocation(currentCaseNotificationDataDT.getDataLocation());
			String tableName = getTableNameFromDataLocation(currentCaseNotificationDataDT.getDataLocation());
			while (columnsItr.hasNext()) {
				Collection<?>  columnDTCollection  = (Collection<?>)columnsItr.next();

				if (columnDTCollection.iterator().hasNext())
					currentCaseNotificationDataDT = (CaseNotificationDataDT)columnDTCollection.iterator().next(); // grab first DT for column
				else
					throw new NEDSSSystemException("NNDMessageBuilder.processGroupDataRetrieval:  Invalid column meta data for non-NBS_Answer data retrieval.");

				selectColumns += ", " + getColumnNameFromDataLocation(currentCaseNotificationDataDT.getDataLocation());
			}

			// Put together query string and call DAO to retrieve information
			// and update each CaseNotificationDataDT
			String queryString = "";
			
			queryString = "SELECT " + selectColumns + fromWhereClause;
			if(tableName.equalsIgnoreCase(DataTables.LAB_EVENT)){
				if(nTypeObs==1)
					queryString = "SELECT distinct observation_uid, " + selectColumns + fromWhereClause;
				if(nTypeObs==2)
					queryString = "SELECT distinct observation_uid, result_uid, " + selectColumns + fromWhereClause;
				if(nTypeObs==3)
					queryString = "SELECT distinct result_uid, susceptibility_uid, " + selectColumns + fromWhereClause + " order by result_uid";
				if(nTypeObs==4)
					queryString = "SELECT distinct result_uid, susceptibility_uid, " + selectColumns + fromWhereClause;
			}
			NotificationMessageDAOImpl notificationMessageDAOImpl = new NotificationMessageDAOImpl();
			updatedCaseNotificationDataDTCollection  = notificationMessageDAOImpl.getUpdatedNonAnswerCaseNotificationDataDTs(queryString, groupColumns, tableName, nTypeObs);
		}

		return updatedCaseNotificationDataDTCollection;
	}

	private String buildDynamicNonAnswerFromWhereClause(CaseNotificationDataDT currentCaseNotificationDataDT, TreeMap<Object, Object> entityUIDMap, Long publicHealthCaseUid, Long notificationUid, int nTypeObs) {
		String fromWhereClause = "";
		String tableName = getTableNameFromDataLocation(currentCaseNotificationDataDT.getDataLocation());
		String dataCd = currentCaseNotificationDataDT.getDataCd() == null ? "" : currentCaseNotificationDataDT.getDataCd();
		String dataUseCd = currentCaseNotificationDataDT.getDataUseCd() == null ? "" : currentCaseNotificationDataDT.getDataUseCd();
		String partTypeCd = currentCaseNotificationDataDT.getPartTypeCd() == null ? "" : currentCaseNotificationDataDT.getPartTypeCd();
		String uid = "";

		if (tableName.equalsIgnoreCase(NNDConstantUtil.PUBLIC_HEALTH_CASE_TABLE)) {
			fromWhereClause = " FROM Public_health_case WHERE public_health_case_uid =  " + publicHealthCaseUid.toString();
		} else if (tableName.equalsIgnoreCase(NNDConstantUtil.NOTIFICATION_TABLE)) {
			fromWhereClause = " FROM Notification WHERE notification_uid =  " + notificationUid.toString();
		} else {
			if (partTypeCd.length() > 0) {
				if (entityUIDMap.get(partTypeCd) != null) {
					uid = entityUIDMap.get(partTypeCd).toString();
				} else {
					return null;
				}
				// If required then the UID should be present in the map - if optional then it may or
				// may not be available.  Going to just return a null and let the existing required
				// check handle things.
			} else
				uid = publicHealthCaseUid.toString(); // No participation, so must be an Act


			// Now see if it a specific table (hard coded where clause approach) or if we can apply generic approach
			if (tableName.equalsIgnoreCase(DataTables.ENTITY_LOCATOR_PARTICIPATION_TABLE)) {
				fromWhereClause = " FROM Entity_locator_participation WHERE entity_uid = "+ uid;
			} else if (tableName.equalsIgnoreCase(DataTables.TELE_LOCATOR_TABLE)) {
				fromWhereClause = " FROM Entity_locator_participation INNER JOIN Tele_locator ON Entity_locator_participation.locator_uid = Tele_locator.tele_locator_uid WHERE Entity_locator_participation.entity_uid = " +
								  uid + " and Entity_locator_participation.cd = '" + dataCd +
								  "' AND Entity_locator_participation.use_cd = '" + dataUseCd + "'";
			} else if (tableName.equalsIgnoreCase(DataTables.POSTAL_LOCATOR_TABLE)) {
				fromWhereClause = " FROM Entity_locator_participation INNER JOIN Postal_locator ON Entity_locator_participation.locator_uid = Postal_locator.postal_locator_uid WHERE Entity_locator_participation.entity_uid =  " +
						uid + " and Entity_locator_participation.cd =  '" + dataCd + "' AND Entity_locator_participation.use_cd = '"
						+ dataUseCd + "'";
			} else if (tableName.equalsIgnoreCase(DataTables.CONFIRMATION_METHOD_TABLE)) {
				fromWhereClause = " FROM "+ DataTables.CONFIRMATION_METHOD_TABLE + " WHERE public_health_case_uid = " + uid;
			} 
			else if (tableName.equalsIgnoreCase(DataTables.CASE_MANAGEMENT_TABLE)) {
				fromWhereClause = " FROM "+ DataTables.CASE_MANAGEMENT_TABLE + " WHERE public_health_case_uid = " + uid;
			}else if (tableName.equalsIgnoreCase(DataTables.ACTIVITY_ID_TABLE)) {
				fromWhereClause = " FROM " + DataTables.ACTIVITY_ID_TABLE + " WHERE act_uid = " + uid;
				if(dataCd!=null && (dataCd.equals(NEDSSConstants.ACT_ID_STATE_TYPE_CD)||(dataCd.equals(NEDSSConstants.ACT_ID_CITY_TYPE_CD)) || dataCd.equals(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD)))
					fromWhereClause = fromWhereClause + " AND type_cd = \'"+dataCd+"\'";
			} else if (tableName.equalsIgnoreCase(DataTables.PARTICIPATION_TABLE)) {
				fromWhereClause = " FROM " + DataTables.PARTICIPATION_TABLE + " WHERE subject_entity_uid = " + uid +
						  " AND type_cd = '" + partTypeCd + "'";
			} else if (tableName.equalsIgnoreCase(DataTables.LAB_EVENT)) {
				if(nTypeObs==1 || nTypeObs==2)
					fromWhereClause = " FROM " + DataTables.LAB_EVENT + " WHERE investigation_uid = " + uid ;
				if(nTypeObs==3 || nTypeObs==4)
					fromWhereClause = " FROM " + DataTables.LAB_EVENT + " WHERE investigation_uid = " + uid +" and susceptibility_uid is not null" ;
			}
			else // For all other tables, assume generic approach will work - parse data location up to
				   // first underscore and use that with "_uid" appended as the primary key name
			{
				String primaryKeySuffix = getPrimaryKeySuffixFromDataLocation(currentCaseNotificationDataDT.getDataLocation());
				fromWhereClause = " FROM " + tableName + " WHERE " + primaryKeySuffix + "_uid = " + uid;
			}
		}

		return fromWhereClause;
	}

	private String getTableNameFromDataLocation(String dataLocation) {
		// Return table name from dataLocation - return string text up to the first "."
		if (dataLocation != null && dataLocation.indexOf(".") > 0)
			return dataLocation.split("\\.")[0];
		else
			throw new NEDSSSystemException("NNDMessageBuilder.getTableNameFromDataLocation:  Invalid Data Location metadata - unable to parse Table Name.");
	}

	// Return a key containing participation type code, xml path for grouping purposes
	public String getXMLGroupingKey(CaseNotificationDataDT caseNotificationDataDT) {
		return caseNotificationDataDT.getPartTypeCd() + ":" + caseNotificationDataDT.getXmlPath();
	}

	private String getColumnNameFromDataLocation(String dataLocation) {
		// Return column name from dataLocation - return string text after the first "."
		if (dataLocation != null && dataLocation.indexOf(".") > 0) {
			return dataLocation.split("\\.")[1];
		} else {
			throw new NEDSSSystemException(
					"NNDMessageBuilder.getColumnNameFromDataLocation:  Invalid Data Location metadata - unable to parse Column Name.");
		}
	}

	private String convertToCamelCase(String convertToCamelCase) {
		StringBuffer sb = new StringBuffer();
		String[] str = convertToCamelCase.split("_");
		for (int i=0; i < str.length; i++) {
			String temp = str[i];
			sb.append(Character.toUpperCase(temp.charAt(0)));
			sb.append(temp.substring(1).toLowerCase());
		}
        return sb.toString();
	}


	private String getPrimaryKeySuffixFromDataLocation(String dataLocation) {
		String primaryKeySuffix = "";

		if (dataLocation != null && dataLocation.indexOf(".") > 0) {
			// Get table part of dataLocation
			primaryKeySuffix = dataLocation.split("\\.")[0];

			// If it contains an underscore, we just want the first part up to the underscore to create primary key
			if (dataLocation.indexOf("_") > 0)
				return primaryKeySuffix.split("_")[0];
			else
				return primaryKeySuffix;
		}
		// Bad dataLocation, throw exception
		throw new NEDSSSystemException(
				"NNDMessageBuilder.getPrimaryKeySuffixFromDataLocation:  Invalid Data Location metadata - unable to parse Primary Key Suffix.");
	}

	public Calendar convertNBSDateToCalendar(String dateString) {
		Calendar cal = Calendar.getInstance();

		try {
			if ((dateString != null) && (dateString.length() > 4) && (dateString.length() <= 10)) {
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				Date date = df.parse(dateString);
				cal.setTime(date);
				return cal;
			} else if (dateString != null) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(dateString);
				cal.setTime(date);
				return cal;
			} else {
				String errString = "MessageBuilderHelper.convertNBSDateToCalendar - Missing or invalid format YEAR/DATE/TIME for date field:  '" + dateString + "'";
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}
		} catch (ParseException e) {
			String answer = dateString == null ? "" : dateString;
			String errString = "MessageBuilderHelper.convertNBSDateToCalendar:  Invalid date:  '" +
							   answer + "':  " + e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
	}
	
	public void populateUnits(Collection<Object> caseNotificationDataDTCollection) {
		Iterator<Object> unitParentIter = caseNotificationDataDTCollection.iterator();

		// Iterate through whole Collection<Object>  looking for Unit Parents - when a parent is found, find its unit
		while (unitParentIter.hasNext()) {
			CaseNotificationDataDT unitParentDT = (CaseNotificationDataDT)unitParentIter.next();

			boolean found = true; // Initialize to true, if we don't have any value's requiring units left
							      // then we want to avoid exception thrown at end.

			// Find a Item that is a value requiring a unit and make sure unit isn't already set (checking codedValue)
			if (unitParentDT.getQuestionUnitIdentifier() != null &&
				unitParentDT.getQuestionUnitIdentifier().trim().length() > 0 &&
				( (unitParentDT.getCodedValue() == null || unitParentDT.getCodedValue().trim().length() == 0) &&
				  (unitParentDT.getLocalCodedValue() == null || unitParentDT.getLocalCodedValue().trim().length() == 0)) ) {
				// Found a numeric that has a unit, now find the unit
				Iterator<Object> unitIter = caseNotificationDataDTCollection.iterator();
				found = false;
				while (unitIter.hasNext() && !found) {
					CaseNotificationDataDT unitDT = (CaseNotificationDataDT)unitIter.next();
					
					if ( (unitDT.getQuestionIdentifier() != null) &&
						 (unitParentDT.getQuestionUnitIdentifier().equalsIgnoreCase(unitDT.getQuestionIdentifier()) &&
						 ( (unitParentDT.getPartTypeCd() == null && unitDT.getPartTypeCd() == null) ||
 					       (unitParentDT.getPartTypeCd() != null && unitDT.getPartTypeCd() != null && unitParentDT.getPartTypeCd().equalsIgnoreCase(unitDT.getPartTypeCd()))) ) ) {
						// Found it, now set the code values in the parent's CaseNotificationDataDT

						if (unitDT.getCodedValue() != null && unitDT.getCodedValue().trim().length() > 0) {
							unitParentDT.setCodedValue(unitDT.getCodedValue());
							unitParentDT.setCodedValueDescription(unitDT.getCodedValueDescription());
							unitParentDT.setCodedValueCodingSystem(unitDT.getCodedValueCodingSystem());
						}
						if (unitDT.getLocalCodedValue() != null && unitDT.getLocalCodedValue().trim().length() > 0) {
							unitParentDT.setLocalCodedValue(unitDT.getLocalCodedValue());
							unitParentDT.setLocalCodedValueDescription(unitDT.getLocalCodedValueDescription());
							unitParentDT.setLocalCodedValueCodingSystem(unitDT.getLocalCodedValueCodingSystem());
						}

						// Remove the unit's CaseNotificationDataDT from the caseNotificationDataDTCollection
						unitIter.remove();

						// Found unit, go onto search for next value in collection needing unit
						found = true;

						// Start over for next item
						unitParentIter = caseNotificationDataDTCollection.iterator();
					}
				}
			}
			if (!found) { // Didn't find a matching unit code for this value
				String errString = "NNDMessageBuilder.populateUnits:  Failed to find matching unit code:  " + unitParentDT.getQuestionIdentifier() + " expected for:  " + unitParentDT.getQuestionUnitIdentifier();
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}
		}
	}
	
	public void populateUnitsLab(Collection<Object> caseNotificationDataDTCollection) {
		
		try{
			Iterator<Object> unitParentIter = caseNotificationDataDTCollection.iterator();
			logger.info("Populating Units lab");
			// Iterate through whole Collection<Object>  looking for Unit Parents - when a parent is found, find its unit
			while (unitParentIter.hasNext()) {
				CaseNotificationDataDT unitParentDT = (CaseNotificationDataDT)unitParentIter.next();
	
				boolean found = true; // Initialize to true, if we don't have any value's requiring units left
								      // then we want to avoid exception thrown at end.
	
				// Find a Item that is a value requiring a unit and make sure unit isn't already set (checking codedValue)
				if (unitParentDT.getQuestionUnitIdentifier() != null &&
					unitParentDT.getQuestionUnitIdentifier().trim().length() > 0 &&
					( (unitParentDT.getCodedValue() == null || unitParentDT.getCodedValue().trim().length() == 0) &&
					  (unitParentDT.getLocalCodedValue() == null || unitParentDT.getLocalCodedValue().trim().length() == 0)) ) {
					// Found a numeric that has a unit, now find the unit
					Iterator<Object> unitIter = caseNotificationDataDTCollection.iterator();
					
					//For lab data if numeric result is empty then, don't find unit and remove that DT
					String numericResult = unitParentDT.getAnswerTxt();
					if(numericResult!= null && numericResult.trim().equals("^^^")){
						unitParentIter.remove();
						continue;
					}
					found = false;
				
					while (unitIter.hasNext() && !found) {
						CaseNotificationDataDT unitDT = (CaseNotificationDataDT)unitIter.next();
						
						if ( (unitDT.getQuestionIdentifier() != null) &&
							 (unitParentDT.getQuestionUnitIdentifier().equalsIgnoreCase(unitDT.getQuestionIdentifier()) &&
							 ( (unitParentDT.getPartTypeCd() == null && unitDT.getPartTypeCd() == null) ||
	 					       (unitParentDT.getPartTypeCd() != null && unitDT.getPartTypeCd() != null && unitParentDT.getPartTypeCd().equalsIgnoreCase(unitDT.getPartTypeCd())))
							 && ( (unitParentDT.getObservationSubID() == null && unitDT.getObservationSubID() == null) || (unitParentDT.getObservationSubID() != null && unitDT.getObservationSubID() != null && unitParentDT.getObservationSubID().equalsIgnoreCase(unitDT.getObservationSubID())) ))) {
							// Found it, now set the code values in the parent's CaseNotificationDataDT
	
							if (unitDT.getCodedValue() != null && unitDT.getCodedValue().trim().length() > 0) {
								unitParentDT.setCodedValue(unitDT.getCodedValue());
								unitParentDT.setCodedValueDescription(unitDT.getCodedValueDescription());
								unitParentDT.setCodedValueCodingSystem(unitDT.getCodedValueCodingSystem());
								
								logger.info("CodedValue: "+unitDT.getCodedValue()
										+" CodedValueDescription: "+unitDT.getCodedValueDescription()
										+" CodedValueCodingSYstem: "+unitDT.getCodedValueCodingSystem());
							}
							if (unitDT.getLocalCodedValue() != null && unitDT.getLocalCodedValue().trim().length() > 0) {
								unitParentDT.setLocalCodedValue(unitDT.getLocalCodedValue());
								unitParentDT.setLocalCodedValueDescription(unitDT.getLocalCodedValueDescription());
								unitParentDT.setLocalCodedValueCodingSystem(unitDT.getLocalCodedValueCodingSystem());
								
								logger.info("LocalCodedValue: "+unitDT.getLocalCodedValue()
										+" LocalCodedValueDescription: "+unitDT.getLocalCodedValueDescription()
										+" LocalCodedValueCodingSYstem: "+unitDT.getLocalCodedValueCodingSystem());
							}
	
							// Remove the unit's CaseNotificationDataDT from the caseNotificationDataDTCollection
							unitIter.remove();
	
							// Found unit, go onto search for next value in collection needing unit
							found = true;
	
							// Start over for next item
							unitParentIter = caseNotificationDataDTCollection.iterator();
						}
					}
				}
				//there can be lab numeric values without unit 
				if (!found) { // Didn't find a matching unit code for this value
					unitParentDT.setQuestionUnitIdentifier(null);
					unitParentDT.setAnswerTxt(unitParentDT.getAnswerTxt().replaceAll("^", ""));
					
					logger.info("A matching unit code wasn't found for this value: AnswerTxt: "+unitParentDT.getAnswerTxt());
				}
			}
			
		}catch (NEDSSSystemException e){
			String error = "MessageBuilderHelper.populateUnitsLab: "+e.getMessage();
			logger.fatal(error);
			throw new NEDSSSystemException(error,e);
		}
	}

	public void checkForRequiredMessageElements(Collection<Object> caseNotificationDataDTCollection,
												TreeMap<Object, Object> phcEntityParticipationsMap) {
		boolean requiredFieldsPresent = true;
		String missingRequiredFields = "";

		if (caseNotificationDataDTCollection  != null) {
			Iterator<Object> it = caseNotificationDataDTCollection.iterator();
			while (it.hasNext()) {
				Object object = it.next();
				if (object instanceof CaseNotificationDataDT) {
					CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)object;


					// If we have a participation type code populated, but its value isn't in the phcEntityParticipationsMap
					// it means this entity wasn't attached to this PHC, so we should drop this field - can't be required if
					// we don't have this entity.
					if (caseNotificationDataDT.getPartTypeCd() != null &&
						caseNotificationDataDT.getPartTypeCd().trim().length() > 1 &&
						phcEntityParticipationsMap.get(caseNotificationDataDT.getPartTypeCd()) == null) {
						it.remove();
						continue;
					}

					// Check for required fields and make sure that they are populated
					if (caseNotificationDataDT.getQuestionRequiredNND() != null &&
						caseNotificationDataDT.getQuestionRequiredNND().equalsIgnoreCase(NEDSSConstants.NND_REQUIRED_FIELD)) {
						if (((caseNotificationDataDT.getAnswerTxt() == null || caseNotificationDataDT.getAnswerTxt().length() == 0))) {
							requiredFieldsPresent = false;
							String missingField = (caseNotificationDataDT.getQuestionIdentifierNND()== null || caseNotificationDataDT.getQuestionIdentifierNND().trim().length() < 1) ? caseNotificationDataDT.getQuestionIdentifier() : caseNotificationDataDT.getQuestionIdentifierNND();
							String participationType = caseNotificationDataDT.getPartTypeCd() != null ? ":" + caseNotificationDataDT.getPartTypeCd(): "";
							missingRequiredFields += missingField +
													 participationType +
													 "|";
						}
					}
				}
			}
		}

		if (!requiredFieldsPresent) {
			// No answer for required field(s), need to fail the message
			String errString = "PHDCMessageBuilder.checkForRequiredNotificationElements:  Required message field(s) missing:  " + missingRequiredFields.substring(0, missingRequiredFields.length());
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}

	}

	public String getResultStatus(NotificationDT notificationDT) {
		// If Notification Record Status Code is PENDING_DEL, then this is a deleted investigation
		if (notificationDT.getRecordStatusCd().equals(NEDSSConstants.NOTIFICATION_PEND_DEL_IN_BATCH_PROCESS))
			return NEDSSConstants.NND_DELETED_MESSAGE;

		// Check AutoResend flag - if off, then this is a new notification - so send as final
		// if it is off, then this is an update - so send it as corrected
		if (notificationDT.getAutoResendInd().equals(NEDSSConstants.NOTIFICATION_AUTO_RESEND_OFF))
			return NEDSSConstants.NND_FINAL_MESSAGE;
		else
			return NEDSSConstants.NND_CORRECTED_MESSAGE;
	}

	public SRTGenericCodeDT reversePHINToNBSCodeTranslation(String codeSetName, String code, String translationTableName) {
		SRTGenericCodeDT srtCode = new SRTGenericCodeDT();
		NotificationSRTCodeLookupTranslationDAOImpl srtCodeLookUpDAO = new NotificationSRTCodeLookupTranslationDAOImpl();

		CaseNotificationDataDT translatedCode = srtCodeLookUpDAO.translatePHINCodetoNBSCode(code, codeSetName,translationTableName);
		srtCode.setCode(translatedCode.getLocalCodedValue());
		srtCode.setCodeDescTxt(translatedCode.getLocalCodedValueDescription());

		return srtCode;
	}

	private void retrieveNedssProperty(CaseNotificationDataDT CaseNotificationDataDT) {
		String nedssProperty = CaseNotificationDataDT.getDataLocation().split(":")[1];
		String propertyValue = propertyUtil.getProperty(nedssProperty, "");
		if (propertyValue.length() > 0)
			CaseNotificationDataDT.setAnswerTxt(propertyValue);
		else {
			String errString = "NNDMessageBuilder.retrieveNedssProperty:  Invalid Data Location metadata - unable to retrieve nedss.property for: " + nedssProperty;
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
	}

	public void writeNBSIntermediaryMessage(String nbsIntermediaryMessageXML,
			                                NBSSecurityObj nbsSecurityObj,
			                                String publicHealthCaseLocalId,
			                                NotificationDT notificationDT,
			                                String filePath)
											throws NEDSSConcurrentDataException {
		
		try{
			CnTransportQOutDAOImpl cnTransportQOutDAOImpl = new CnTransportQOutDAOImpl();
	
			CnTransportQOutDT cnTransportQOutDT = prepareCnTransportQOut(nbsIntermediaryMessageXML, nbsSecurityObj, notificationDT, publicHealthCaseLocalId);
	
	
			// Write payload to Cn_transportq_out table in ODSE
			if (propertyUtil.getNNDPamIntermediaryMessageOutput() != null &&
				propertyUtil.getNNDPamIntermediaryMessageOutput().equalsIgnoreCase(NEDSSConstants.MESSAGE_OUTPUT_TABLE)) {
				cnTransportQOutDAOImpl.insertCnTransportQOutDT(cnTransportQOutDT);
				return;
			}
	
			// Write payload (XML) to file
			if (propertyUtil.getNNDPamIntermediaryMessageOutput() != null &&
				propertyUtil.getNNDPamIntermediaryMessageOutput().equalsIgnoreCase(NEDSSConstants.MESSAGE_OUTPUT_FILE)) {
				writeToFile(cnTransportQOutDT.getMessagePayload(), notificationDT.getLocalId(), filePath);
				return;
			}
	
			// Write payload (XML) to file and to Cn_transportq_out table in ODSE
			if (propertyUtil.getNNDPamIntermediaryMessageOutput() != null &&
				propertyUtil.getNNDPamIntermediaryMessageOutput().equalsIgnoreCase(NEDSSConstants.MESSAGE_OUTPUT_BOTH)) {
				cnTransportQOutDAOImpl.insertCnTransportQOutDT(cnTransportQOutDT);
				writeToFile(cnTransportQOutDT.getMessagePayload(), notificationDT.getLocalId(), filePath);
				return;
			}
	
			// No property found - just write to table
			cnTransportQOutDAOImpl.insertCnTransportQOutDT(cnTransportQOutDT);
		
		}catch (NEDSSSystemException e){
			String error = "MessageBuilderHelper.writeNBSIntermediaryMessage: "+e.getMessage();
			logger.fatal(error);
			throw new NEDSSSystemException(error,e);
		}
	}


	private CnTransportQOutDT prepareCnTransportQOut(String nbsIntermediaryMessageXML,
			 										 NBSSecurityObj nbsSecurityObj,
			 										 NotificationDT notificationDT,
			 										 String publicHealthCaseLocalId) throws NEDSSConcurrentDataException {
		CnTransportQOutDT cnTransportQOutDT = new CnTransportQOutDT();
		Date dateTime = new Date();
		Timestamp systemTime = new Timestamp(dateTime.getTime());

		cnTransportQOutDT.setMessagePayload(nbsIntermediaryMessageXML);

		cnTransportQOutDT.setVersionCtrlNbr(new Integer(1));
		cnTransportQOutDT.setRecordStatusCd(NEDSSConstants.NND_UNPROCESSED_MESSAGE);

		cnTransportQOutDT.setReportStatus(getResultStatus(notificationDT));

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


	public void writeToFile(String messagePayload, String notificationLocalId, String directory) {
		try {
			File nbsDirectoryPath = new File(directory);

			if (!nbsDirectoryPath.exists())
				nbsDirectoryPath.mkdirs(); // make the directory if it does not exist

			// name the file with the date and time
			// e.g., C:\Nedss\NND\2002.09.06-12.06.06.123-{Notification LocalId}.xml
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss.SSS");
			Date currentTime_1 = new Date();
			String dateString = formatter.format(currentTime_1);

			File payloadXML = new File(nbsDirectoryPath, dateString + "-" + notificationLocalId + ".xml");

			FileWriter xmlOut = new FileWriter(payloadXML);

			if (messagePayload != null)
				xmlOut.write(messagePayload);

			xmlOut.close(); // close file
		} catch (IOException e) {
			logger.error("MessageBuilderHelper.writeToFile, IOException:  " + e);
		}
	}
	
	/**
	 * getVaccineInformationSourceConceptCode - get concept code for VAC147
	 * @param cd
	 * @return
	 */
	private String getVaccineInformationSourceConceptCode (String cd) {
		if (vaccineInformationSource.isEmpty()) {
				vaccineInformationSource = getCodeListByCodeSetNm("PHVS_VACCINEEVENTINFORMATIONSOURCE_NND");
		}
		return (getConceptCode(cd,vaccineInformationSource));	
	}
	/**
	 * getVaccineInformationSourcePreferredName - get perferred name for VAC147
	 * @param cd
	 * @return
	 */
	private String getVaccineInformationSourcePreferredName (String cd) {
		if (vaccineInformationSource.isEmpty()) {
				vaccineInformationSource = getCodeListByCodeSetNm("PHVS_VACCINEEVENTINFORMATIONSOURCE_NND");
		}
		return (getConceptPreferredName(cd,vaccineInformationSource));	
	}
	/**
	 * getVaccineInformationSourceOid - get OID for VAC147
	 * @param cd
	 * @return
	 */
	private String getVaccineInformationSourceOid (String cd) {
		if (vaccineInformationSource.isEmpty()) {
				vaccineInformationSource = getCodeListByCodeSetNm("PHVS_VACCINEEVENTINFORMATIONSOURCE_NND");
		}
		return (getCodeSystemCd(cd,vaccineInformationSource));	
	}
	/**
	 * getVaccineAnatomicSiteConceptCode for VAC104
	 * @param cd
	 * @return
	 */
	private String getVaccineAnatomicSiteConceptCode (String cd) {
		if (VaccineAnatomicSite.isEmpty()) {
				VaccineAnatomicSite = getCodeListByCodeSetNm("NIP_ANATOMIC_ST");
		}
		return (getConceptCode(cd,VaccineAnatomicSite));	
	}
	/**
	 * getVaccineAnatomicSitePreferredName for VAC104
	 * @param cd
	 * @return
	 */
	private String getVaccineAnatomicSitePreferredName (String cd) {
		if (VaccineAnatomicSite.isEmpty()) {
				VaccineAnatomicSite = getCodeListByCodeSetNm("NIP_ANATOMIC_ST");
		}
		return (getConceptPreferredName(cd,VaccineAnatomicSite));	
	}
	/**
	 * getVaccineAnatomicSiteOid - get OID for VAC104
	 * @param cd
	 * @return
	 */
	private String getVaccineAnatomicSiteOid (String cd) {
		if (VaccineAnatomicSite.isEmpty()) {
				VaccineAnatomicSite = getCodeListByCodeSetNm("NIP_ANATOMIC_ST");
		}
		return (getCodeSystemCd(cd,VaccineAnatomicSite));	
	}
	/**
	 * getVaccineManufacturerConceptCode for VAC107
	 * @param cd
	 * @return
	 */
	private String getVaccineManufacturerConceptCode (String cd) {
		if (VaccineManufacturer.isEmpty()) {
				VaccineManufacturer = getCodeListByCodeSetNm("VAC_MFGR");
		}
		return (getConceptCode(cd,VaccineManufacturer));	
	}
	/**
	 * getVaccineManufacturerPreferredName for VAC107
	 * @param cd
	 * @return
	 */
	private String getVaccineManufacturerPreferredName (String cd) {
		if (VaccineManufacturer.isEmpty()) {
				VaccineManufacturer = getCodeListByCodeSetNm("VAC_MFGR");
		}
		return (getConceptPreferredName(cd,VaccineManufacturer));	
	}
	/**
	 * getVaccineManufacturerOid for VAC107
	 * @param cd
	 * @return
	 */
	private String getVaccineManufacturerOid (String cd) {
		if (VaccineManufacturer.isEmpty()) {
				VaccineManufacturer = getCodeListByCodeSetNm("VAC_MFGR");
		}
		return (getCodeSystemCd(cd,VaccineManufacturer));	
	}
	/**
	 * getVaccineNameConceptCode for VAC101
	 * @param cd
	 * @return
	 */
	private String getVaccineNameConceptCode (String cd) {
		if (VaccineName.isEmpty()) {
				VaccineName = getCodeListByCodeSetNm("VAC_NM");
		}
		return (getConceptCode(cd,VaccineName));	
	}
	/**
	 * getVaccineNamePreferredName for VAC101
	 * @param cd
	 * @return
	 */
	private String getVaccineNamePreferredName (String cd) {
		if (VaccineName.isEmpty()) {
				VaccineName = getCodeListByCodeSetNm("VAC_NM");
		}
		return (getConceptPreferredName(cd,VaccineName));	
	}
	/**
	 * getVaccineNameOid for VAC101
	 * @param cd
	 * @return
	 */
	private String getVaccineNameOid (String cd) {
		if (VaccineName.isEmpty()) {
				VaccineName = getCodeListByCodeSetNm("VAC_NM");
		}
		return (getCodeSystemCd(cd,VaccineName));	
	}
	/**
	 * getConceptCode from cache
	 * @param cd
	 * @param codesetCache - ArrayList of CodeValueGeneralDT
	 * @return
	 */
	private String getConceptCode(String cd,
			ArrayList<Object> codesetCache) {
		for (int i=0; i<codesetCache.size(); ++i) {
			CodeValueGeneralDT codeValueGeneralDT = (CodeValueGeneralDT) codesetCache.get(i);
			if (codeValueGeneralDT.getCode().equalsIgnoreCase(cd)) {
				if (codeValueGeneralDT.getConceptCode() != null)
					return codeValueGeneralDT.getConceptCode();
				else 
					return codeValueGeneralDT.getCode();
			}		
		}// for
		return null;
	}
	/**
	 * getConceptPreferredName to use in Msg
	 * @param cd
	 * @param codesetCache  - ArrayList of CodeValueGeneralDT
	 * @return
	 */
	private String getConceptPreferredName(String cd,
			ArrayList<Object> codesetCache) {
		for (int i=0; i<codesetCache.size(); ++i) {
			CodeValueGeneralDT codeValueGeneralDT = (CodeValueGeneralDT) codesetCache.get(i);
			if (codeValueGeneralDT.getCode().equalsIgnoreCase(cd)) {
				if (codeValueGeneralDT.getConceptPreferredNm() != null)
					return codeValueGeneralDT.getConceptPreferredNm();
				else if (codeValueGeneralDT.getConceptNm() != null)
					return codeValueGeneralDT.getConceptNm();
				else if (codeValueGeneralDT.getCodeShortDescTxt() != null)
					return codeValueGeneralDT.getCodeShortDescTxt();
			}		
		}// for
		return null;
	}
	/**
	 * getCodeSystemCd - get the OID to use for the answer
	 * @param cd
	 * @param codesetCache  - ArrayList of CodeValueGeneralDT
	 * @return
	 */
	private String getCodeSystemCd(String cd,
			ArrayList<Object> codesetCache) {
		for (int i=0; i<codesetCache.size(); ++i) {
			CodeValueGeneralDT codeValueGeneralDT = (CodeValueGeneralDT) codesetCache.get(i);
			if (codeValueGeneralDT.getCode().equalsIgnoreCase(cd)) {
				if (codeValueGeneralDT.getCodeSystemCd() != null)
					return codeValueGeneralDT.getCodeSystemCd();
				else logger.error("MessageBuilderHelper.getCodeSystemCd -> Code_System_Cd is null for code  " +cd);
			}		
		}// for
		return null;
	}
	/**
	 * getCodeListByCodeSetNm - get arrayList for the codeset
	 * @param codeSetNm
	 * @return
	 * 
	 */
	public ArrayList<Object> getCodeListByCodeSetNm(String codeSetNm) {
        ArrayList<Object> codeList = new ArrayList<Object>();
        CodeValueGeneralDAOImpl cvgDAO = new CodeValueGeneralDAOImpl();   
        try{ 
            codeList= (ArrayList<Object>) cvgDAO.retrieveCodeSetValGenFields(codeSetNm);
		}catch(Exception ex){
		     logger.error("Error retrieving codeset for vaccination " +codeSetNm +" in MessageBuilderHelper: " +ex.getMessage(), ex);
		}
        return codeList;
	}

	/**
	 * cloneDataForMultiselect: creating multiple caseNotificationDataDT in case there are multiple values in the answer (answerTxt contains ~).
	 * @param caseNotificationDataDTCollection
	 * @return
	 */
	public Collection<Object> cloneDataForMultiselect(Collection<Object> caseNotificationDataDTCollection) {
		// TODO Auto-generated method stub
		try {
			NotificationSRTCodeLookupTranslationDAOImpl srtCodeLookUpDAO = new NotificationSRTCodeLookupTranslationDAOImpl();
			ArrayList<Object> caseNotificationDataDTCollection1 = null;
		//	boolean HDFound = false;
		//	String hdQuestionId = null;
			
			Iterator<Object> it = caseNotificationDataDTCollection.iterator();
			while (it.hasNext()) {
				Object object = it.next();
				if (object instanceof CaseNotificationDataDT) {
					CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)object;
					String hl7DataType = caseNotificationDataDT.getQuestionDataTypeNND();
					String dataType = caseNotificationDataDT.getDataType();
					String translationTableName = caseNotificationDataDT.getTranslationTableNm();

					logger.info("hl7DataType: "+hl7DataType+" dataType: "+dataType+" translationTableName: "+translationTableName);
					// Look for CWE and CE data types for creating HL7 NND messages and for Coded data types for
					// Case Report Export/Share messages to do an SRT lookup for CodedValueDescription and CodedValueCoding System
					if (((hl7DataType != null) && ((hl7DataType.equals(NEDSSConstants.NND_HL7_DATATYPE_CWE)) ||
							                       (hl7DataType.equals(NEDSSConstants.NND_HL7_DATATYPE_CE)))) ||
							                       ((dataType != null) && ((dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_CODED))))) {

						if (caseNotificationDataDT.getCodesetGroupId() != null && caseNotificationDataDT.getAnswerTxt()!= null && caseNotificationDataDT.getAnswerTxt().contains("~") )
					   {
						   
							   //Instead use while loop for | and create deep copy accordingly
							   String answer = caseNotificationDataDT.getAnswerTxt();
							   String count[] = answer.split("\\~");
									   //caseNotificationDataDT.getAnswerTxt(), "|");
							   caseNotificationDataDTCollection1 = new ArrayList<Object>();
							   for(int i = 0; i<count.length; i++){
								   CaseNotificationDataDT caseNotificationDataDT1 = (CaseNotificationDataDT)caseNotificationDataDT.deepCopy();
								   logger.info("AnswerTxt: "+count[i].trim());
								   caseNotificationDataDT1.setAnswerTxt(count[i].trim());
								   //caseNotificationDataDT1.setAnswerGroupSeqNbr(i+1);
								   caseNotificationDataDTCollection1.add(caseNotificationDataDT1);
							   }
							   it.remove();
							   continue;
					   }
					}
				}
			}
			if(caseNotificationDataDTCollection1!= null && !caseNotificationDataDTCollection1.isEmpty())
			{	
				caseNotificationDataDTCollection.addAll(caseNotificationDataDTCollection1);
			
			 ArrayList<Object> caseNotificationDataDTCollectionSorted = new ArrayList<Object>(caseNotificationDataDTCollection);
			logger.debug("Before sorting the caseNotificationDataDTCollectionSorted");
			Collections.sort( caseNotificationDataDTCollectionSorted, new Comparator()
            {
            public int compare( Object a, Object b )
               {
                return(((CaseNotificationDataDT)a).getObservationUid()).compareTo( ((CaseNotificationDataDT) b).getObservationUid());
               }
            } );
			
			logger.debug("After sorting the caseNotificationDataDTCollectionSorted");
			return caseNotificationDataDTCollectionSorted;
			}
			else 
				return caseNotificationDataDTCollection;
		} catch (Exception e) {
			String errString = "MessageBuilderHelper.cloneDataForMultiselect:  " + e.getMessage();
			logger.fatal(errString);
			throw new NEDSSSystemException(errString, e);
		}
	
	}
	
}
