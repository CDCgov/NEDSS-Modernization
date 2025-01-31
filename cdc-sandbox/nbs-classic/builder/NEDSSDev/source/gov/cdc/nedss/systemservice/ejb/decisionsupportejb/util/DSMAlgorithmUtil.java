package gov.cdc.nedss.systemservice.ejb.decisionsupportejb.util;

import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.dsm.ActionType;
import gov.cdc.nedss.dsm.AdvancedCriteriaType;
import gov.cdc.nedss.dsm.AlgorithmDocument;
import gov.cdc.nedss.dsm.AlgorithmDocument.Algorithm;
import gov.cdc.nedss.dsm.ApplyToConditionsType;
import gov.cdc.nedss.dsm.CodedType;
import gov.cdc.nedss.dsm.CreateInvestigationType;
import gov.cdc.nedss.dsm.CreateInvestigationWithNNDType;
import gov.cdc.nedss.dsm.CriteriaType;
import gov.cdc.nedss.dsm.DefaultParticipationType;
import gov.cdc.nedss.dsm.DefaultValueType;
import gov.cdc.nedss.dsm.ElrAdvancedCriteriaType;
import gov.cdc.nedss.dsm.ElrCriteriaType;
import gov.cdc.nedss.dsm.ElrNumericType;
import gov.cdc.nedss.dsm.EntryMethodType;
import gov.cdc.nedss.dsm.IntegerNumericType;
import gov.cdc.nedss.dsm.InvCriteriaType;
import gov.cdc.nedss.dsm.InvValueType;
import gov.cdc.nedss.dsm.InvestigationDefaultValuesType;
import gov.cdc.nedss.dsm.MarkAsReviewedType;
import gov.cdc.nedss.dsm.ReportingFacilityType;
import gov.cdc.nedss.dsm.SendingSystemType;
import gov.cdc.nedss.dsm.EventDateLogicType;
import gov.cdc.nedss.dsm.TextType;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dao.CaseNotificationDAOImpl;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean.DSMAlgorithm;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean.DSMAlgorithmHome;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DSMAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.bean.EdxPHCRDocument;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.bean.EdxPHCRDocumentHome;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxRuleConstants;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.ObservationUtil;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO.DSMSummaryDisplay;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO.DecisionSupportClientVO;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.StringReader;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xmlbeans.XmlException;

/**
 * Name: DSMAlgorithmUtil.java Description: Helper class for Decision Support
 * Algorithm manipulation including marshaling and demarshaling the DSMAlgorithm
 * XML to and from the DSMAlgorithmDT.algorithmPayload. Copyright: Copyright (c)
 * 2011 Company: Computer Sciences Corporation
 * 
 * @author Beau Bannerman
 */
public class DSMAlgorithmUtil {
	private static LogUtils logger = new LogUtils(DSMAlgorithmUtil.class.getName());

	public void parseAlgorithmPayload(String dsmAlgorithmPayload, DecisionSupportClientVO decisionSupportClientVO, HttpSession session) {
		try {
			AlgorithmDocument algorithmDoc = AlgorithmDocument.Factory.parse(dsmAlgorithmPayload);

			Algorithm algorithm = algorithmDoc.getAlgorithm();

			decisionSupportClientVO.setAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE, algorithm.getInvestigationType());
			
			
			if (algorithm.getAdvancedCriteria() != null) {
				ArrayList<BatchEntry> advancedCriteriaList = new ArrayList<BatchEntry>();

				AdvancedCriteriaType advancedCriteria = algorithm.getAdvancedCriteria();
				CriteriaType[] criteria = advancedCriteria.getCriteriaArray();

				for (int i = 0; i < criteria.length; i++) {
					BatchEntry batchEntry = new BatchEntry();

					CodedType question = criteria[i].getCriteriaQuestion();
					CodedType logic = criteria[i].getCriteriaLogic();

					Map<Object, Object> batchAnswerMap = new HashMap<Object, Object>();
					batchAnswerMap.put(DecisionSupportConstants.CRITERIA_QUESTION, question.getCode());
					batchAnswerMap.put(DecisionSupportConstants.CRITERIA_LOGIC, logic.getCode());

					if (criteria[i].getCriteriaStringValue() != null) {
						batchAnswerMap.put(DecisionSupportConstants.CRITERIA_VALUE, criteria[i].getCriteriaStringValue());
					} else if (criteria[i].getCriteriaCodedValueArray() != null && criteria[i].getCriteriaCodedValueArray().length > 0) {
						String[] codeMultiselect =  new String[criteria[i].getCriteriaCodedValueArray().length];
						batchAnswerMap.put(DecisionSupportConstants.VALUE, codeMultiselect);
						for (int x = 0; x < criteria[i].getCriteriaCodedValueArray().length; x++) {
							codeMultiselect[x] = criteria[i].getCriteriaCodedValueArray()[x].getCode();
						}
		                batchAnswerMap.put(DecisionSupportConstants.CRITERIA_VALUE, codeMultiselect);
					} // else SN?
					batchEntry.setDsmAnswerMap(batchAnswerMap);

					advancedCriteriaList.add(batchEntry);
				}
				decisionSupportClientVO.setAdvancedCriteriaBatchEntryList(advancedCriteriaList);

			}else if(algorithm.getElrAdvancedCriteria() != null)
			{
				ArrayList<BatchEntry> advancedCriteriaList = new ArrayList<BatchEntry>();
				ArrayList<BatchEntry> advancedInvCriteriaList = new ArrayList<BatchEntry>();
				ElrAdvancedCriteriaType elrAdvancedCriteriaType = algorithm.getElrAdvancedCriteria();
				
				
				
					try {
						decisionSupportClientVO.setAnswer(DecisionSupportConstants.USE_EVENT_DATE_LOGIC, algorithm.getElrAdvancedCriteria().getEventDateLogic().getElrTimeLogic().getElrTimeLogicInd().getCode());
						decisionSupportClientVO.setAnswer(DecisionSupportConstants.AND_OR_LOGIC, elrAdvancedCriteriaType.getAndOrLogic());
						decisionSupportClientVO.setAnswer(DecisionSupportConstants.USE_INV_CRITERIA_LOGIC, elrAdvancedCriteriaType.getInvLogic().getInvLogicInd().getCode());
						EventDateLogicType eventDateLogic = elrAdvancedCriteriaType.getEventDateLogic();
						CodedType nbsEventDate = eventDateLogic.getElrSourceElement();
						decisionSupportClientVO.setAnswer(DecisionSupportConstants.NBS_EVENT_DATE_SELECTED, nbsEventDate.getCode());
						IntegerNumericType intNumericType = eventDateLogic.getWithinTimePeriod();
						CodedType timeFrameOperator = intNumericType.getComparatorCode();
						decisionSupportClientVO.setAnswer(DecisionSupportConstants.TIMEFRAME_OPERATOR_SELECTED, timeFrameOperator.getCode());
						BigInteger numDaysBigInt = intNumericType.getValue1();
						decisionSupportClientVO.setAnswer(DecisionSupportConstants.TIMEFRAME_DAYS, numDaysBigInt.toString());
						logger.debug("DSM Event Date Logic present ");
					} catch (Exception ex) {
						logger.error("DSMAlgorithmUtil.parseAlgorithmPayload - exception parsing Event Date XML:  " + ex.getMessage());
				} //parse Event Date Logic				
				
				ElrCriteriaType[] elrCriteria = elrAdvancedCriteriaType.getElrCriteriaArray();
				
				for (int i = 0; i < elrCriteria.length; i++){
					BatchEntry batchEntry = new BatchEntry();
					Map<Object, Object> batchAnswerMap = new HashMap<Object, Object>();
					
					CodedType resultedTest = elrCriteria[i].getResultedTest();
					String resultedTestCode = resultedTest.getCode();
					String resultedTestName = resultedTest.getCodeDescTxt();
					if(resultedTestCode != null && resultedTestCode != "")
					{
						batchAnswerMap.put(DecisionSupportConstants.RESULTEDTEST_CODE, resultedTestCode);
						batchAnswerMap.put(DecisionSupportConstants.RESULTEDTEST_NAME, resultedTestName);
					}
					
					CodedType codedResult = elrCriteria[i].getElrCodedResultValue();
					if(codedResult != null)
					{
						String codedResultCode = codedResult.getCode();
						String codedResultName = codedResult.getCodeDescTxt();
						if(codedResultCode != null && codedResultCode != "")
						{
							batchAnswerMap.put(DecisionSupportConstants.CODED_RESULT, codedResultCode);
							batchAnswerMap.put(DecisionSupportConstants.CODED_RESULT_TXT, codedResultName);
							batchAnswerMap.put(DecisionSupportConstants.RESULT_NAME, codedResultName);
						}
					}
					
					
					if(elrCriteria[i].getElrTextResultValue() != null && elrCriteria[i].getElrTextResultValue().getTextValue() != null && elrCriteria[i].getElrTextResultValue().getTextValue() != "")
					{
						String textResultValue = elrCriteria[i].getElrTextResultValue().getTextValue();
						batchAnswerMap.put(DecisionSupportConstants.TEXT_RESULT, textResultValue);
						batchAnswerMap.put(DecisionSupportConstants.RESULT_NAME, textResultValue);
					}
					
					if(elrCriteria[i].getElrTextResultValue() != null && elrCriteria[i].getElrTextResultValue().getComparatorCode()!=null && elrCriteria[i].getElrTextResultValue().getComparatorCode().getCode()!=null){
						String textOperatorValue = elrCriteria[i].getElrTextResultValue().getComparatorCode().getCode();
						String textOperatorValueDesc = elrCriteria[i].getElrTextResultValue().getComparatorCode().getCodeDescTxt();
						batchAnswerMap.put(DecisionSupportConstants.TEXT_RESULT_CRITERIA, textOperatorValue);
						batchAnswerMap.put(DecisionSupportConstants.TEXT_RESULT_CRITERIA_TXT, textOperatorValueDesc);
					}
					
					ElrNumericType numericType = elrCriteria[i].getElrNumericResultValue();
					String unitCode = "";
					String unitName = "";
					if(numericType != null)
					{
						CodedType unitType = numericType.getUnit();
						if(unitType != null)
						{
							unitCode = unitType.getCode();
							unitName = unitType.getCodeDescTxt();
							if(unitCode != null && unitCode != "")
							{
								batchAnswerMap.put(DecisionSupportConstants.NUMERIC_RESULT_TYPE, unitCode);
								batchAnswerMap.put(DecisionSupportConstants.NUMERIC_RESULT_TYPE_TXT, unitName);
							}
						}
						
						String comparatorCode = "";
						String comparatorCodeDesc = "";
						if(numericType.getComparatorCode() != null && numericType.getComparatorCode().getCode() != null){
							comparatorCode = numericType.getComparatorCode().getCode();
							comparatorCodeDesc = numericType.getComparatorCode().getCodeDescTxt();
							
							if(comparatorCodeDesc==null){
								comparatorCodeDesc = CachedDropDowns.getCodeDescTxtForCd(comparatorCode, "SEARCH_NUMERIC");
							}
						}
						String value1 = numericType.getValue1();
						String seperatorCode = numericType.getSeperatorCode();
						if(seperatorCode == null)
							seperatorCode = "";
						String value2 = numericType.getValue2();
						if(value2 == null)
							value2 = "";
						
						String numericValue = value1 + seperatorCode + value2;
						if(numericValue != null && numericValue != "")
						{
							batchAnswerMap.put(DecisionSupportConstants.NUMERIC_RESULT_CRITERIA, comparatorCode);
							batchAnswerMap.put(DecisionSupportConstants.NUMERIC_RESULT_CRITERIA_TXT, comparatorCodeDesc);
							batchAnswerMap.put(DecisionSupportConstants.TEXT_RESULT_CRITERIA_TXT, comparatorCodeDesc);
							batchAnswerMap.put(DecisionSupportConstants.NUMERIC_RESULT, numericValue);
							batchAnswerMap.put(DecisionSupportConstants.RESULT_NAME, numericValue + " " + unitName);
						}
					}
					
					batchEntry.setDsmAnswerMap(batchAnswerMap);
					advancedCriteriaList.add(batchEntry);
				}
				decisionSupportClientVO.setAdvancedCriteriaBatchEntryList(advancedCriteriaList);			
				
				/*Set batch for investigation advanced criteria*/
				InvCriteriaType invCriteriaType = elrAdvancedCriteriaType.getInvCriteria();
				if (invCriteriaType != null) {
					InvValueType[] invValue = invCriteriaType
							.getInvValueArray();
					for (int i = 0; i < invValue.length; i++) {
						BatchEntry batchEntry = new BatchEntry();

						CodedType question = invValue[i].getInvQuestion();
						CodedType logic = invValue[i].getInvQuestionLogic();

						Map<Object, Object> batchAnswerMap = new HashMap<Object, Object>();
						batchAnswerMap
								.put(DecisionSupportConstants.ADV_INV_CRITERIA_QUESTION,
										question.getCode());
						batchAnswerMap
								.put(DecisionSupportConstants.ADV_INV_CRITERIA_LOGIC,
										logic.getCode());

						if (invValue[i].getInvStringValue() != null) {
							batchAnswerMap
									.put(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE,
											invValue[i].getInvStringValue());
						} else if (invValue[i].getInvCodedValueArray() != null
								&& invValue[i].getInvCodedValueArray().length > 0) {
							String[] codeMultiselect = new String[invValue[i]
									.getInvCodedValueArray().length];
							batchAnswerMap.put(DecisionSupportConstants.VALUE,
									codeMultiselect);
							for (int x = 0; x < invValue[i]
									.getInvCodedValueArray().length; x++) {
								codeMultiselect[x] = invValue[i]
										.getInvCodedValueArray()[x].getCode();
							}
							batchAnswerMap
									.put(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE,
											codeMultiselect);
						}
						batchEntry.setDsmAnswerMap(batchAnswerMap);

						advancedInvCriteriaList.add(batchEntry);
					}
				}
				decisionSupportClientVO.setAdvancedInvCriteriaBatchEntryList(advancedInvCriteriaList);
				/*End Set batch for investigation advanced criteria*/
			}

			ActionType action = algorithm.getAction();

			if (action.getCreateInvestigation() != null) {
				CreateInvestigationType createInvestigation = action.getCreateInvestigation();
				if (createInvestigation.getInvestigationDefaultValues() != null) {
					parseDefaultValues(createInvestigation.getInvestigationDefaultValues().getDefaultValueArray(), decisionSupportClientVO, session);
				}

				//decisionSupportClientVO.setAnswer(DecisionSupportConstants.UPDATE_ACTION, createInvestigation.getUpdateAction().getCode());
				decisionSupportClientVO.setAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_INV, 
						                          createInvestigation.getOnFailureToCreateInvestigation().getCode());
			} else if (action.getCreateInvestigationWithNND() != null) {
				CreateInvestigationWithNNDType createInvestigationTypeWithNNDType = action.getCreateInvestigationWithNND();
				if (createInvestigationTypeWithNNDType.getInvestigationDefaultValues() != null) {
					parseDefaultValues(createInvestigationTypeWithNNDType.getInvestigationDefaultValues().getDefaultValueArray(), 
							           decisionSupportClientVO, session);
				}

				//decisionSupportClientVO.setAnswer(DecisionSupportConstants.UPDATE_ACTION,
				//		                          createInvestigationTypeWithNNDType.getUpdateAction().getCode());
				decisionSupportClientVO.setAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_INV,
												  createInvestigationTypeWithNNDType.getOnFailureToCreateInvestigation().getCode());
				decisionSupportClientVO.setAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION,
												  createInvestigationTypeWithNNDType.getOnFailureToCreateNND().getCode());
				if (createInvestigationTypeWithNNDType.getNNDComment() != null && createInvestigationTypeWithNNDType.getNNDComment().length() > 0) {
					decisionSupportClientVO.setAnswer(DecisionSupportConstants.NOTIFICATION_COMMENTS,
													  createInvestigationTypeWithNNDType.getNNDComment());
				}
			}else if (action.getMarkAsReviewed() != null)
			{
				MarkAsReviewedType markAsReviewed = action.getMarkAsReviewed();
				decisionSupportClientVO.setAnswer(DecisionSupportConstants.ON_FAILURE_TO_MARK_REVIEWED, 
						markAsReviewed.getOnFailureToMarkAsReviewed().getCode());
				if (markAsReviewed.getAdditionalComment() != null && markAsReviewed.getAdditionalComment().length() > 0) {
					decisionSupportClientVO.setAnswer(DecisionSupportConstants.ADDITIONAL_COMMENTS,
							markAsReviewed.getAdditionalComment());
				}
			}
		} catch (XmlException ex) {
			String errString = "DSMAlgorithmUtil.parseAlgorithmPayload - failed parsing Algorithm XML:  " + ex.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, ex);
		}

	}

	public void parseDefaultValues(DefaultValueType[] defaultValueArray, DecisionSupportClientVO decisionSupportClientVO, HttpSession session) {
		ArrayList<BatchEntry> defaultValuesList = new ArrayList<BatchEntry>();

		for (int i = 0; i < defaultValueArray.length; i++) {
			BatchEntry batchEntry = new BatchEntry();

			DefaultValueType defaultValue = defaultValueArray[i];
			Map<Object, Object> batchAnswerMap = new HashMap<Object, Object>();

			CodedType question = defaultValue.getDefaultQuestion();
			CodedType behavoir = defaultValue.getDefaultBehavior();
			//ignore the default Notes
			if(question.getCode()!=null && !question.getCode().equals("INV167")){
			batchAnswerMap.put(DecisionSupportConstants.QUESTION, question.getCode());
			batchAnswerMap.put(DecisionSupportConstants.BEHAVIOR, behavoir.getCode());

			if (defaultValue.getDefaultStringValue() != null) {
				batchAnswerMap.put(DecisionSupportConstants.VALUE, defaultValue.getDefaultStringValue());
			} else if (defaultValue.getDefaultCodedValueArray() != null && defaultValue.getDefaultCodedValueArray().length > 0) {
				String[] codeMultiselect =  new String[defaultValue.getDefaultCodedValueArray().length];
				for (int x = 0; x < defaultValue.getDefaultCodedValueArray().length; x++) {
					codeMultiselect[x] = defaultValue.getDefaultCodedValueArray()[x].getCode();
				}
				batchAnswerMap.put(DecisionSupportConstants.VALUE, codeMultiselect);
			} else if (defaultValue.getDefaultParticipation() != null) {
				String entityUid = defaultValue.getDefaultParticipation().getEntityUid();
				batchAnswerMap.put(DecisionSupportConstants.ENTITY_VALUE, entityUid);
				String entityClass = defaultValue.getDefaultParticipation().getEntityClass();
				batchAnswerMap.put(DecisionSupportConstants.ENTITY_CLASS, entityClass);
				//gstToDo - get the display string from the Uid
				String participationSrchRslt = getTheParticipationEntityDisplayString(entityUid, entityClass, session);
				batchAnswerMap.put(DecisionSupportConstants.PARTICIPATION_SEARCH_RESULT, participationSrchRslt);
				
				String valValueTxt = participationSrchRslt; 
				//if (valValueTxt.contains("<BR>"))
				//		valValueTxt = participationSrchRslt.replaceAll("<BR>", " ");
				//else if (valValueTxt.contains("<br/>"))
				//		valValueTxt = participationSrchRslt.replaceAll("<br/>", " ");
				//if (valValueTxt.contains("<B>"))
				//	valValueTxt = participationSrchRslt.replaceAll("<B>", " ");
				//if (valValueTxt.contains("</B>"))
				//	valValueTxt = participationSrchRslt.replaceAll("</B>", " ");
				batchAnswerMap.put(DecisionSupportConstants.VALUE, valValueTxt); //i.e.Dr. Felder, MD			
			}
			batchEntry.setDsmAnswerMap(batchAnswerMap);
			defaultValuesList.add(batchEntry);
			}
			else if (question.getCode()!=null && question.getCode().equals("INV167")){
				//decisionSupportClientVO.setAnswer(DecisionSupportConstants.NOTES, defaultValue.getDefaultCommentValue());
			
				batchAnswerMap.put(DecisionSupportConstants.QUESTION, question.getCode());
				batchAnswerMap.put(DecisionSupportConstants.BEHAVIOR, behavoir.getCode());
				batchAnswerMap.put(DecisionSupportConstants.VALUE, defaultValue.getDefaultStringValue());
				
				batchEntry.setDsmAnswerMap(batchAnswerMap);
				defaultValuesList.add(batchEntry);
			
			}
		}
		decisionSupportClientVO.setBatchEntryList(defaultValuesList);

	}

	public void createAlgorithmPayload(Map<Object, Object> questionMap, DSMAlgorithmDT dsmAlgorithmDT, 
			                           DecisionSupportClientVO decisionSupportClientVO, Map<Object, Object> advInvQuestionMap) {

		ArrayList<BatchEntry> batchEntryList = decisionSupportClientVO.getBatchEntryList();
		ArrayList<BatchEntry> advancedCriteriaList = decisionSupportClientVO.getAdvancedCriteriaBatchEntryList();
		ArrayList<BatchEntry> advancedInvCriteriaList = decisionSupportClientVO.getAdvancedInvCriteriaBatchEntryList();

		AlgorithmDocument algorithmDoc = AlgorithmDocument.Factory.newInstance();

		// Create Algorithm and populate strings
		Algorithm algorithm = algorithmDoc.addNewAlgorithm();
		algorithm.setAlgorithmName(dsmAlgorithmDT.getAlgorithmNm());
		algorithm.setComment(dsmAlgorithmDT.getAdminComment());

		// Do Event code lookup and create and populate Event
		CodedType event = algorithm.addNewEvent();
		setCodedType(event, dsmAlgorithmDT.getEventType(), DataTables.CODE_VALUE_GENERAL, "PUBLIC_HEALTH_EVENT");

		
			if("11648804".equals(dsmAlgorithmDT.getEventType()))
			{
				ElrAdvancedCriteriaType elrAdvancedCriteria = algorithm.addNewElrAdvancedCriteria();
				addEventDateAndOrLogic(elrAdvancedCriteria, decisionSupportClientVO); 
				addElrAdvancedCriteria(elrAdvancedCriteria, advancedCriteriaList);
				addAdvancedInvCriteria(elrAdvancedCriteria, advancedInvCriteriaList,advInvQuestionMap);
				elrAdvancedCriteria.addNewInvLogic().addNewInvLogicInd().setCode(getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.USE_INV_CRITERIA_LOGIC)));
				
			}else{
				if (advancedCriteriaList != null && advancedCriteriaList.size() > 0) {
				AdvancedCriteriaType advancedCriteria = algorithm.addNewAdvancedCriteria();
				addAdvancedCriteria(advancedCriteria, advancedCriteriaList, questionMap);
				}
			}

		// Do Action code lookup and create and populate Action
		ActionType action = algorithm.addNewAction();
		if (dsmAlgorithmDT.getEventAction().equalsIgnoreCase(EdxRuleConstants.CREATE_INVESTIGATION)) {
			CreateInvestigationType createInvestigation = action.addNewCreateInvestigation();

			//CodedType updateAction = createInvestigation.addNewUpdateAction();
			//setCodedType(updateAction, 
			//		     getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.UPDATE_ACTION)),
			//		     DataTables.CODE_VALUE_GENERAL, "NBS_UPDATE_ACTION");

			CodedType onFailureToCreateInvestigation = createInvestigation.addNewOnFailureToCreateInvestigation();
			setCodedType(onFailureToCreateInvestigation, 
					     getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_INV)),
					     DataTables.CODE_VALUE_GENERAL, "NBS_FAILURE_RESPONSE");
			InvestigationDefaultValuesType invDefaultValues = null;
			if (batchEntryList != null && batchEntryList.size() > 0) {
				invDefaultValues = createInvestigation.addNewInvestigationDefaultValues();
				addDefaultValues(invDefaultValues, batchEntryList, questionMap);
			}
			String notes = getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.NOTES));
			if(notes !=null && !notes.equals("")){
				if(invDefaultValues==null)
					invDefaultValues = createInvestigation.addNewInvestigationDefaultValues();
				addNotes(invDefaultValues,notes);
			}


		} else if (dsmAlgorithmDT.getEventAction().equalsIgnoreCase(EdxRuleConstants.CREATE_INVESTIGATION_AND_NND)) {
			CreateInvestigationWithNNDType createInvestigationWithNND = action.addNewCreateInvestigationWithNND();

			//CodedType updateAction = createInvestigationWithNND.addNewUpdateAction();
			//setCodedType(updateAction, 
			//		     getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.UPDATE_ACTION)),
			//		     DataTables.CODE_VALUE_GENERAL, "NBS_UPDATE_ACTION");

			CodedType onFailureToCreateInvestigation = createInvestigationWithNND.addNewOnFailureToCreateInvestigation();
			setCodedType(onFailureToCreateInvestigation,
					     getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_INV)),
					     DataTables.CODE_VALUE_GENERAL, "NBS_FAILURE_RESPONSE");
			InvestigationDefaultValuesType invDefaultValues = null;
			if (batchEntryList != null && batchEntryList.size() > 0) {
				invDefaultValues = createInvestigationWithNND.addNewInvestigationDefaultValues();
				addDefaultValues(invDefaultValues, batchEntryList, questionMap);
				
			}
			String notes = getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.NOTES));
			if(notes !=null && !notes.trim().equals("")){
				if(invDefaultValues==null)
					invDefaultValues = createInvestigationWithNND.addNewInvestigationDefaultValues();
				addNotes(invDefaultValues,notes);	
			}
			createInvestigationWithNND.setQueueForApproval(false);

			CodedType onFailureToCreateNND = createInvestigationWithNND.addNewOnFailureToCreateNND();
			setCodedType(onFailureToCreateNND,
					     getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION)),
					     DataTables.CODE_VALUE_GENERAL, "NBS_NOT_FAILURE_RESPONSE");

			if (getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.NOTIFICATION_COMMENTS)) != null) {
				createInvestigationWithNND.setNNDComment(getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.NOTIFICATION_COMMENTS)));
			}

		} else if (dsmAlgorithmDT.getEventAction().equalsIgnoreCase(
				"DeleteDocumentType")) {
			// DeleteDocumentType deleteDocument =
			// action.addNewDeleteDocument();
			// CodedType reasonForDeletion =
			// deleteDocument.addNewReasonForDeletion();
			// setCodedType(ReasonForDeletion,getVal(answerMap.get(DecisionSupportConstants.REASON_FOR_DELETION)),DataTables.CODE_VALUE_GENERAL,
			// "DELETE_REASON");
			// deleteDocument.setComment(getVal(answerMap.get(DecisionSupportConstants.DELETE_COMMENTS));
			// <xs:element name="Alert" type="nbs:AlertType" minOccurs="0"/>
		}else if(dsmAlgorithmDT.getEventAction().equalsIgnoreCase(EdxRuleConstants.MARK_AS_REVIEWED))
		{
			MarkAsReviewedType markAsReviewed = action.addNewMarkAsReviewed();
			CodedType onFailureToMarkAsReviewed = markAsReviewed.addNewOnFailureToMarkAsReviewed();
			setCodedType(onFailureToMarkAsReviewed,
				     getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.ON_FAILURE_TO_MARK_REVIEWED)),
				     DataTables.CODE_VALUE_GENERAL, "NBS_FAILURE_RESPONSE");
			
			markAsReviewed.setAdditionalComment(getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.ADDITIONAL_COMMENTS)));
		}

		// Do Frequency code lookup and create and populate Frequency
		CodedType frequency = algorithm.addNewFrequency();
		setCodedType(frequency, dsmAlgorithmDT.getFrequency(), DataTables.CODE_VALUE_GENERAL, "NBS_FREQUENCY");

		// Set Investigation Type
		algorithm.setInvestigationType(getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE)));

		// Iterate through collection of Conditions
		// Do Condition code lookup and create and populate each Condition
		if (dsmAlgorithmDT.getConditionList() != null && dsmAlgorithmDT.getConditionList().trim().length() > 0) {
			ApplyToConditionsType applyToConditions = algorithm.addNewApplyToConditions();
			String conditionList = dsmAlgorithmDT.getConditionList();
			String[] conditions = conditionList.split("\\^");
			for (int i=0; i < conditions.length; i++) {
				String conditionString = conditions[i].trim();
				if(conditionString.length() > 0)
				{
					CodedType condition = applyToConditions.addNewCondition();
					setCodedType(condition, conditionString, DataTables.CONDITION_CODE_VIEW, "PHC_TYPE");
				}
			}
		}

		CaseNotificationDAOImpl caseNotificationDAOImpl = new CaseNotificationDAOImpl();

		// Iterate through collection of SendingSystems
		// Do SendingSystem code lookup and create and populate each
		// SendingSystem
		if (dsmAlgorithmDT.getSendingSystemList() != null && dsmAlgorithmDT.getSendingSystemList().trim().length() > 0) {
			SendingSystemType sendingSystemType = algorithm.addNewApplyToSendingSystems();
			String sendingSystemList = dsmAlgorithmDT.getSendingSystemList();
			String[] sendingSystems = sendingSystemList.split("\\^");
			for (int i=0; i < sendingSystems.length; i++) {
				String sendingSystemString = sendingSystems[i].trim();
				if (sendingSystemString.length() > 0) {
					ExportReceivingFacilityDT exportReceivingFacilityDT = caseNotificationDAOImpl.getReceivingFacilityByShortNm(sendingSystemString);
	
					CodedType sendingSystem = sendingSystemType.addNewSendingSystem();
					if("11648804".equals(dsmAlgorithmDT.getEventType()))
					{
						sendingSystem.setCode(sendingSystemString);
						sendingSystem.setCodeDescTxt(exportReceivingFacilityDT.getReceivingSystemOwner());
						sendingSystem.setCodeSystemCode(exportReceivingFacilityDT.getReceivingSystemOwnerOid());
					}else
					{
						sendingSystem.setCode(sendingSystemString);
						sendingSystem.setCodeDescTxt(sendingSystemString);
						sendingSystem.setCodeSystemCode(exportReceivingFacilityDT.getReceivingSystemOid());
					}
				}
			}
		}

		// Parse through list of ReportingFacilities
		// Do ReportingFacility code lookup and create and populate each
		// ReportingFacility
		if (dsmAlgorithmDT.getReportingSystemList() != null && dsmAlgorithmDT.getReportingSystemList().trim().length() > 0) {
			ReportingFacilityType reportingFacilityType = algorithm.addNewApplyToReportingFacilities();
			String reportingFacilityList = dsmAlgorithmDT.getReportingSystemList();
			while (reportingFacilityList.contains("^")) {
				String reportingFacilityString = reportingFacilityList.substring(0, reportingFacilityList.indexOf("^"));

				if (reportingFacilityList.indexOf("^") > 0) {
					reportingFacilityList = reportingFacilityList.split("\\^")[1];
				} else {
					reportingFacilityList = "";
				}

				ExportReceivingFacilityDT exportReceivingFacilityDT = caseNotificationDAOImpl.getReceivingFacilityByShortNm(reportingFacilityString);

				CodedType reportingFacility = reportingFacilityType.addNewReportingFacility();
				reportingFacility.setCode(reportingFacilityString);
				reportingFacility.setCodeDescTxt(reportingFacilityString);
				reportingFacility.setCodeSystemCode(exportReceivingFacilityDT.getReceivingSystemOid());
			}
			if (reportingFacilityList.trim().length() > 0) {
				String reportingFacilityString = reportingFacilityList;

				ExportReceivingFacilityDT exportReceivingFacilityDT = caseNotificationDAOImpl.getReceivingFacilityByShortNm(reportingFacilityString);

				CodedType reportingFacility = reportingFacilityType.addNewReportingFacility();
				reportingFacility.setCode(reportingFacilityString);
				reportingFacility.setCodeDescTxt(reportingFacilityString);
				reportingFacility.setCodeSystemCode(exportReceivingFacilityDT.getReceivingSystemOid());
			}
		}

		// Parse through list of ApplyToEntryMethod settings
		// Do ApplyTo code lookup and create and populate each ApplyTo setting
		if (dsmAlgorithmDT.getApplyTo() != null && dsmAlgorithmDT.getApplyTo().trim().length() > 0) {
			EntryMethodType entryMethod = algorithm.addNewAppliesToEntryMethods();
			String applyToList = dsmAlgorithmDT.getApplyTo();
			while (applyToList.contains("^")) {
				String applyToString = applyToList.substring(0, applyToList.indexOf("^"));

				if (applyToList.indexOf("^") != applyToList.length() - 1) {
					applyToList = applyToList.split("^")[1];
				} else {
					applyToList = "";
				}

				CodedType applyTo = entryMethod.addNewEntryMethod();
				setCodedType(applyTo, applyToString, DataTables.CODE_VALUE_GENERAL, "NBS_ENTRY_METHOD");
			}
			if (applyToList.trim().length() > 0) {
				String applyToString = applyToList;
				CodedType applyTo = entryMethod.addNewEntryMethod();
				setCodedType(applyTo, applyToString, DataTables.CODE_VALUE_GENERAL, "NBS_ENTRY_METHOD");
			}
		}	
		
		dsmAlgorithmDT.setAlgorithmPayload(algorithmDoc.toString());
	}

	private void addEventDateAndOrLogic(ElrAdvancedCriteriaType elrAdvancedCriteria, DecisionSupportClientVO decisionSupportClientVO) { 
	//check for And/Or Logic Setting - optional element
	String andOrLogic = getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.AND_OR_LOGIC));
	if (andOrLogic != null)
		elrAdvancedCriteria.setAndOrLogic(andOrLogic);

	if (elrAdvancedCriteria.getAndOrLogic() != null)
		decisionSupportClientVO.setAnswer(DecisionSupportConstants.AND_OR_LOGIC, elrAdvancedCriteria.getAndOrLogic());
	else //default to Or Logic
		decisionSupportClientVO.setAnswer(DecisionSupportConstants.AND_OR_LOGIC, DecisionSupportConstants.OR_AND_OR_LOGIC);

	String eventDateLogicStr = getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.USE_EVENT_DATE_LOGIC));
	EventDateLogicType eventDateLogic = elrAdvancedCriteria.addNewEventDateLogic();
	eventDateLogic.addNewElrTimeLogic().addNewElrTimeLogicInd().setCode(eventDateLogicStr);
	if (eventDateLogicStr.equalsIgnoreCase(DecisionSupportConstants.USE_EVENT_DATE_LOGIC_YES)) {
		try {
			
			//source element is the date field in the ELR to use
			CodedType elrSourceElement = eventDateLogic.addNewElrSourceElement();
			String nbsEventDateSelected = getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.NBS_EVENT_DATE_SELECTED));
			setCodedType(elrSourceElement, nbsEventDateSelected, DataTables.CODE_VALUE_GENERAL, "NBS_EVENT_DATE");

			//within time period is the comparator and number of days
			IntegerNumericType withinTimePeriod = eventDateLogic.addNewWithinTimePeriod();
			String operatorSelected = getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.TIMEFRAME_OPERATOR_SELECTED));
			CodedType comparatorCode = withinTimePeriod.addNewComparatorCode();
			setCodedType(comparatorCode, operatorSelected, DataTables.CODE_VALUE_GENERAL, "NBS_BRE_LOGIC_1");
			
			String nbrDaysStr = getVal(decisionSupportClientVO.getAnswer(DecisionSupportConstants.TIMEFRAME_DAYS));
			BigInteger nbrDays = new BigInteger(nbrDaysStr);
			withinTimePeriod.setValue1(nbrDays);
			
			CodedType daysCode = withinTimePeriod.addNewUnit();
			setCodedType(daysCode, "D", DataTables.CODE_VALUE_GENERAL, "AGE_UNIT");
			
			logger.debug("DSM Adding Event Date Logic ->  " + nbsEventDateSelected + operatorSelected + nbrDaysStr);
		} catch (Exception ex) {
			logger.error("DSMAlgorithmUtil.addEventDateAndOrLogic - exception adding Event Date Logic to XML:  " + ex.getMessage());
		}
	} //EventDateLogic present		
	else{
		// if time period logic is 'NO' remove the time period attributes from VO
		if(decisionSupportClientVO.getAnswerMap().containsKey(DecisionSupportConstants.NBS_EVENT_DATE_SELECTED));
			decisionSupportClientVO.getAnswerMap().remove(DecisionSupportConstants.NBS_EVENT_DATE_SELECTED);
		if(decisionSupportClientVO.getAnswerMap().containsKey(DecisionSupportConstants.TIMEFRAME_OPERATOR_SELECTED));
			decisionSupportClientVO.getAnswerMap().remove(DecisionSupportConstants.TIMEFRAME_OPERATOR_SELECTED);
		if(decisionSupportClientVO.getAnswerMap().containsKey(DecisionSupportConstants.TIMEFRAME_DAYS));
			decisionSupportClientVO.getAnswerMap().remove(DecisionSupportConstants.TIMEFRAME_DAYS);
	
}	
	
}	
	
	private void addAdvancedCriteria(AdvancedCriteriaType advancedCriteria,
									 ArrayList<BatchEntry> advancedCriteriaList,
									 Map<Object, Object> questionMap) {
		Iterator<BatchEntry> anIter = advancedCriteriaList.iterator();
		while (anIter.hasNext()) {
			CriteriaType criteria = advancedCriteria.addNewCriteria();
			BatchEntry batchEntry = anIter.next();
			Map<Object, Object> batchAnswerMap = batchEntry.getDsmAnswerMap();

			CodedType question = criteria.addNewCriteriaQuestion();
			String questionID = getVal(batchAnswerMap.get(DecisionSupportConstants.CRITERIA_QUESTION));
			if (questionMap.containsKey(questionID)) {
				NbsQuestionMetadata questionMetadata = (NbsQuestionMetadata) questionMap.get(questionID);
				question.setCode(questionID);
				question.setCodeDescTxt(questionMetadata.getQuestionLabel());
				question.setCodeSystemCode(questionMetadata.getQuestionOid());

				String dataType = questionMetadata.getDataType();
				if (dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
					if (batchAnswerMap.get(DecisionSupportConstants.CRITERIA_VALUE).getClass().isArray()) {
						String[] codeStringArray = (String[]) batchAnswerMap.get(DecisionSupportConstants.CRITERIA_VALUE);
						for (int i = 0; codeStringArray != null && i < codeStringArray.length; ++i) {
							CodedType defaultCodeValue = criteria.addNewCriteriaCodedValue();
							setCodedType(defaultCodeValue, codeStringArray[i], questionMetadata.getCodeSetGroupId());
						}
					} else {
						String valueString = getVal(batchAnswerMap.get(DecisionSupportConstants.CRITERIA_VALUE));
						criteria.setCriteriaStringValue(valueString);
					}
				} else if (dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE)) {
					String valueString = getVal(batchAnswerMap.get(DecisionSupportConstants.CRITERIA_VALUE));
					criteria.setCriteriaStringValue(valueString);
				} else if (dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)) {
					String valueString = getVal(batchAnswerMap.get(DecisionSupportConstants.CRITERIA_VALUE));
					criteria.setCriteriaStringValue(valueString);
				} else if (dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
					// TODO: Use Numeric type and parse
					String valueString = getVal(batchAnswerMap.get(DecisionSupportConstants.CRITERIA_VALUE));
					criteria.setCriteriaStringValue(valueString);
				}

				CodedType criteriaLogic = criteria.addNewCriteriaLogic();
				if (dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE)) {
					setCodedType(criteriaLogic,
							     getVal(batchAnswerMap.get(DecisionSupportConstants.CRITERIA_LOGIC)),
							     DataTables.CODE_VALUE_GENERAL, 
							     "SEARCH_NUM");
				} else if(dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)){
					if(questionMetadata.getMask() != null && questionMetadata.getMask().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_MASK_NUMERIC)
							|| (questionMetadata.getMask() != null && questionMetadata.getMask().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_MASK_NUMERIC_YEAR)))
							setCodedType(criteriaLogic,
								     getVal(batchAnswerMap.get(DecisionSupportConstants.CRITERIA_LOGIC)),
								     DataTables.CODE_VALUE_GENERAL, 
								     "SEARCH_NUM");
					else {
						setCodedType(criteriaLogic,
									 getVal(batchAnswerMap.get(DecisionSupportConstants.CRITERIA_LOGIC)),
									 DataTables.CODE_VALUE_GENERAL, 
									 "SEARCH_ALPHA");
					}
				} else if(dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)){
							setCodedType(criteriaLogic,
								     getVal(batchAnswerMap.get(DecisionSupportConstants.CRITERIA_LOGIC)),
								     DataTables.CODE_VALUE_GENERAL, 
								     "SEARCH_NUM");
				}
				else {
					setCodedType(criteriaLogic,
								 getVal(batchAnswerMap.get(DecisionSupportConstants.CRITERIA_LOGIC)),
								 DataTables.CODE_VALUE_GENERAL, 
								 "SEARCH_ALPHA");
				}

			} else {
				// TODO: Throw exception?
			}

		}
	}
	
	private void addElrAdvancedCriteria(ElrAdvancedCriteriaType elrAdvancedCriteria,
			ArrayList<BatchEntry> advancedCriteriaList) {
		Iterator<BatchEntry> anIter = advancedCriteriaList.iterator();
		while (anIter.hasNext()) {
			ElrCriteriaType criteria = elrAdvancedCriteria.addNewElrCriteria();
			BatchEntry batchEntry = anIter.next();
			Map<Object, Object> batchAnswerMap = batchEntry.getDsmAnswerMap();

			String resultedTestString = (String)batchAnswerMap.get(DecisionSupportConstants.RESULTEDTEST_CODE);
			if(resultedTestString != null && !resultedTestString.equals(""))
			{
				CodedType resultedTest = criteria.addNewResultedTest();
				resultedTest.setCode((String)batchAnswerMap.get(DecisionSupportConstants.RESULTEDTEST_CODE));
				resultedTest.setCodeDescTxt((String)batchAnswerMap.get(DecisionSupportConstants.RESULTEDTEST_NAME));
			}
			
			String codeResultString = (String)batchAnswerMap.get(DecisionSupportConstants.CODED_RESULT);
			if(codeResultString != null && !codeResultString.equals(""))
			{
				CodedType codedResultValue = criteria.addNewElrCodedResultValue();
				codedResultValue.setCode(codeResultString);
				codedResultValue.setCodeDescTxt((String)batchAnswerMap.get(DecisionSupportConstants.CODED_RESULT_TXT));
			}
			
			String numericResultCriteria = (String)batchAnswerMap.get(DecisionSupportConstants.NUMERIC_RESULT_CRITERIA);
			//String numericValue = (String)batchAnswerMap.get(DecisionSupportConstants.NUMERIC_RESULT);
			if(numericResultCriteria != null && numericResultCriteria.length()>0)
			{
				String numericValue = (String)batchAnswerMap.get(DecisionSupportConstants.NUMERIC_RESULT);
								
				ObsValueNumericDT obsValueNumericDT = new ObsValueNumericDT();
				obsValueNumericDT.setNumericValue(numericValue);
				obsValueNumericDT = new ObservationUtil().parseNumericResultWithoutDefault(obsValueNumericDT);
				
				ElrNumericType numericResult = criteria.addNewElrNumericResultValue();
				if(numericResultCriteria != null){
					CodedType codedComparator = numericResult.addNewComparatorCode();
					codedComparator.setCode(numericResultCriteria);
					codedComparator.setCodeDescTxt((String)batchAnswerMap.get(DecisionSupportConstants.NUMERIC_RESULT_CRITERIA_TXT));
				}
				if(obsValueNumericDT.getNumericValue1() != null)
					numericResult.setValue1(obsValueNumericDT.getNumericValue1().toString());
				
				//In case of Between operator user need to enter numericValueHigh
				String numericValueHigh = (String)batchAnswerMap.get(DecisionSupportConstants.NUMERIC_RESULT_HIGH);
				if(numericValueHigh != null && numericValueHigh.length()>0){	// While creating DSW numeric result with between operator
					numericResult.setSeperatorCode(DecisionSupportConstants.NUMERIC_RESULT_SEPERATOR);
					numericResult.setValue2(numericValueHigh);
				}else{	// While editing existing DSW numeric result with between operator
					if(obsValueNumericDT.getSeparatorCd() != null)
						numericResult.setSeperatorCode(obsValueNumericDT.getSeparatorCd());
					if(obsValueNumericDT.getNumericValue2() != null)
						numericResult.setValue2(obsValueNumericDT.getNumericValue2().toString());
				}
				String numTypeCode = (String)batchAnswerMap.get(DecisionSupportConstants.NUMERIC_RESULT_TYPE);
				if(numTypeCode != null && !numTypeCode.equals(""))
				{
					CodedType unit = numericResult.addNewUnit();
					unit.setCode((String)batchAnswerMap.get(DecisionSupportConstants.NUMERIC_RESULT_TYPE));
					unit.setCodeDescTxt((String)batchAnswerMap.get(DecisionSupportConstants.NUMERIC_RESULT_TYPE_TXT));
				}
			}
			
			String textResultCriteriaString = (String)batchAnswerMap.get(DecisionSupportConstants.TEXT_RESULT_CRITERIA);
			//String textResultString = (String)batchAnswerMap.get(DecisionSupportConstants.TEXT_RESULT);
			if(textResultCriteriaString != null && textResultCriteriaString.length()>0){

				TextType elrTextResultValue = criteria.addNewElrTextResultValue();
				CodedType codedComparator = elrTextResultValue.addNewComparatorCode();
				codedComparator.setCode(textResultCriteriaString);
				codedComparator.setCodeDescTxt((String)batchAnswerMap.get(DecisionSupportConstants.TEXT_RESULT_CRITERIA_TXT));
				
				String textResultString = (String)batchAnswerMap.get(DecisionSupportConstants.TEXT_RESULT);
				if(textResultString != null && !textResultString.equals("")){
					//criteria.getElrTextResultValue().setTextValue(textResultString);
					//TextType elrTextResultValue = criteria.addNewElrTextResultValue();
					elrTextResultValue.setTextValue(textResultString);
				}
			}
			
		}
	}
	
	private void addAdvancedInvCriteria(
			ElrAdvancedCriteriaType elrAdvancedCriteria,
			ArrayList<BatchEntry> advancedInvCriteriaList,
			Map<Object, Object> questionMap) {
		if(advancedInvCriteriaList==null || advancedInvCriteriaList.size()==0)
			return;
		Iterator<BatchEntry> anIter = advancedInvCriteriaList.iterator();
		InvCriteriaType criteria = elrAdvancedCriteria.addNewInvCriteria();
		while (anIter.hasNext()) {
			
			BatchEntry batchEntry = anIter.next();
			Map<Object, Object> batchAnswerMap = batchEntry.getDsmAnswerMap();

			InvValueType invValue = criteria.addNewInvValue();

			CodedType question = invValue.addNewInvQuestion();
			String questionID = getVal(batchAnswerMap
					.get(DecisionSupportConstants.ADV_INV_CRITERIA_QUESTION));
			if (questionMap.containsKey(questionID)) {
				NbsQuestionMetadata questionMetadata = (NbsQuestionMetadata) questionMap
						.get(questionID);
				question.setCode(questionID);
				question.setCodeDescTxt(questionMetadata.getQuestionLabel());
				question.setCodeSystemCode(questionMetadata.getQuestionOid());

				String dataType = questionMetadata.getDataType();
				if (dataType
						.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
					if (batchAnswerMap
							.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE)
							.getClass().isArray()) {
						String[] codeStringArray = (String[]) batchAnswerMap
								.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE);
						for (int i = 0; codeStringArray != null
								&& i < codeStringArray.length; ++i) {
							CodedType invCodeValue = invValue
									.addNewInvCodedValue();
							setCodedType(invCodeValue, codeStringArray[i],
									questionMetadata.getCodeSetGroupId());
						}
					} else {
						String valueString = getVal(batchAnswerMap
								.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE));
						invValue.setInvStringValue(valueString);
					}
				} else if (dataType
						.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE)) {
					String valueString = getVal(batchAnswerMap
							.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE));
					invValue.setInvStringValue(valueString);
				} else if (dataType
						.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)) {
					String valueString = getVal(batchAnswerMap
							.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE));
					invValue.setInvStringValue(valueString);
				} else if (dataType
						.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
					// TODO: Use Numeric type and parse
					String valueString = getVal(batchAnswerMap
							.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE));
					invValue.setInvStringValue(valueString);
				}

				CodedType questionLogic = invValue.addNewInvQuestionLogic();
				if (dataType
						.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE)) {
					setCodedType(
							questionLogic,
							getVal(batchAnswerMap
									.get(DecisionSupportConstants.ADV_INV_CRITERIA_LOGIC)),
							DataTables.CODE_VALUE_GENERAL, "SEARCH_NUM");
				} else if (dataType
						.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)) {
					if (questionMetadata.getMask() != null
							&& questionMetadata.getMask().equalsIgnoreCase(
									NEDSSConstants.NBS_QUESTION_MASK_NUMERIC)
							|| (questionMetadata.getMask() != null && questionMetadata
									.getMask()
									.equalsIgnoreCase(
											NEDSSConstants.NBS_QUESTION_MASK_NUMERIC_YEAR)))
						setCodedType(
								questionLogic,
								getVal(batchAnswerMap
										.get(DecisionSupportConstants.ADV_INV_CRITERIA_LOGIC)),
								DataTables.CODE_VALUE_GENERAL, "SEARCH_NUM");
					else {
						setCodedType(
								questionLogic,
								getVal(batchAnswerMap
										.get(DecisionSupportConstants.ADV_INV_CRITERIA_LOGIC)),
								DataTables.CODE_VALUE_GENERAL, "SEARCH_ALPHA");
					}
				} else if (dataType
						.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
					setCodedType(
							questionLogic,
							getVal(batchAnswerMap
									.get(DecisionSupportConstants.ADV_INV_CRITERIA_LOGIC)),
							DataTables.CODE_VALUE_GENERAL, "SEARCH_NUM");
				} else {
					setCodedType(
							questionLogic,
							getVal(batchAnswerMap
									.get(DecisionSupportConstants.ADV_INV_CRITERIA_LOGIC)),
							DataTables.CODE_VALUE_GENERAL, "SEARCH_ALPHA");
				}

			} else {
				// TODO: Throw exception?
			}

		}
	}
	

	private void addDefaultValues(InvestigationDefaultValuesType invDefaultValues,
								  ArrayList<BatchEntry> batchEntryList,
								  Map<Object, Object> questionMap) {
		try{
		Iterator<BatchEntry> anIter = batchEntryList.iterator();
		while (anIter.hasNext()) {
			DefaultValueType invDefaultValue = invDefaultValues.addNewDefaultValue();
			BatchEntry batchEntry = anIter.next();
			Map<Object, Object> batchAnswerMap = batchEntry.getDsmAnswerMap();

			CodedType question = invDefaultValue.addNewDefaultQuestion();
			String questionID = getVal(batchAnswerMap.get(DecisionSupportConstants.QUESTION));
			if (questionMap.containsKey(questionID)) {
				NbsQuestionMetadata questionMetadata = (NbsQuestionMetadata) questionMap.get(questionID);
				question.setCode(questionID);
				question.setCodeDescTxt(questionMetadata.getQuestionLabel());
				question.setCodeSystemCode(questionMetadata.getQuestionOid());

				String dataType = questionMetadata.getDataType();
				
				//See if we have a question with a participation type assigned - if so, then the answer will be the uid
				
				if (dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_PARTICIPATION)) {
					String partTypeCd = ""; //default
					if (questionMetadata.getPartTypeCd() != null)
						partTypeCd = questionMetadata.getPartTypeCd();
					else{ 
						partTypeCd = getParticipationTypeForIdentifier(questionMetadata.getQuestionIdentifier());
						if (partTypeCd == null) {
							String errString = "DSMAlgorithmUtil.addDefaultValues - ParticipationTypeCode is missing for:  " + questionMetadata.getQuestionLabel();
							logger.error(errString);
							throw new NEDSSSystemException("Missing Participation Type Code - pls. check log.");
						}
					}
					logger.debug("DSM ALgorithmUtil.addDefaultValues: Participation Type Code is " + questionMetadata.getPartTypeCd());
					ParticipationTypeVO ptvo = (ParticipationTypeVO)CachedDropDowns.getParticipationTypeList().get(partTypeCd+NEDSSConstants.PART_CACHED_MAP_KEY_SEPARATOR+questionMetadata.getQuestionIdentifier());
					String uidString = getVal(batchAnswerMap.get(DecisionSupportConstants.ENTITY_VALUE));
					String entityClassCd = getVal(batchAnswerMap.get(DecisionSupportConstants.ENTITY_CLASS));
					DefaultParticipationType defaultParticipationType = invDefaultValue.addNewDefaultParticipation();
					defaultParticipationType.setEntityClass(entityClassCd); 
					defaultParticipationType.setEntityUid(uidString);
					CodedType participationType = defaultParticipationType.addNewParticipationType();
					setCodedTypeWithoutLookup(participationType, partTypeCd,ptvo.getTypeDescTxt(), "L");
				} else if (dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
					if (batchAnswerMap.get(DecisionSupportConstants.VALUE).getClass().isArray()) {
						String[] codeStringArray = (String[]) batchAnswerMap.get(DecisionSupportConstants.VALUE);
						for (int i = 0; codeStringArray != null && i < codeStringArray.length; ++i) {
							CodedType defaultCodeValue = invDefaultValue.addNewDefaultCodedValue();
							setCodedType(defaultCodeValue, codeStringArray[i], questionMetadata.getCodeSetGroupId());
						}
					} else {
						String valueString = getVal(batchAnswerMap.get(DecisionSupportConstants.VALUE));
						invDefaultValue.setDefaultStringValue(valueString);
					}
				} else if (dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE)) {
					String valueString = getVal(batchAnswerMap.get(DecisionSupportConstants.VALUE));
					invDefaultValue.setDefaultStringValue(valueString);
				} else if (dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)) {
					String valueString = getVal(batchAnswerMap.get(DecisionSupportConstants.VALUE));
					invDefaultValue.setDefaultStringValue(valueString);
				} else if (dataType.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
					// TODO: Use Numeric type and parse
					String valueString = getVal(batchAnswerMap.get(DecisionSupportConstants.VALUE));
					invDefaultValue.setDefaultStringValue(valueString);
				}
				CodedType behavoir = invDefaultValue.addNewDefaultBehavior();
				setCodedType(behavoir, 
						     getVal(batchAnswerMap.get(DecisionSupportConstants.BEHAVIOR)),
						     DataTables.CODE_VALUE_GENERAL, 
						     "NBS_DEFAULT_BEHAVIOR");

			} else {
				// TODO: Throw exception?
			}

		}
		}
		catch(Exception ex){
			logger.error("Error populating Default values in DSMAlgorithmUtil.addDefaultValues :"+ex.getMessage());
		}
	}

	private void setCodedType(CodedType codedType, String code, Long codeSetGroupId) {
		Coded codeLookup = new Coded(code, codeSetGroupId);
		codedType.setCode(code);
		codedType.setCodeDescTxt(codeLookup.getCodeDescription());
		codedType.setCodeSystemCode(codeLookup.getCodeSystemCd());
	}

	private void setCodedType(CodedType codedType, String code, String tableName, String codeSetNm) {
		Coded codeLookup = new Coded(code, tableName, codeSetNm);
		codedType.setCode(code);
		codedType.setCodeDescTxt(codeLookup.getCodeDescription());
		codedType.setCodeSystemCode(codeLookup.getCodeSystemCd());
	}
	
	private void setCodedTypeWithoutLookup(CodedType codedType, String code, String desc, String codeSystem) {
		codedType.setCode(code);
		codedType.setCodeDescTxt(desc);
		codedType.setCodeSystemCode(codeSystem);
	}

	public static String getVal(Object obj) {
		return obj == null ? "" : (String) obj;

	}
	public void addNotes(InvestigationDefaultValuesType invDefaultValues, String notes){
		DefaultValueType invDefaultValue = invDefaultValues.addNewDefaultValue();
		CodedType question = invDefaultValue.addNewDefaultQuestion();
		setCodedTypeWithoutLookup(question, 
			     "INV167",
			     "General Comments", 
			     "2.16.840.1.114222.4.5.232");
		CodedType behaviour = invDefaultValue.addNewDefaultBehavior();
		setCodedTypeWithoutLookup(behaviour, 
			     "1",
			     "Overwrite Existing Values", 
			     "L");
		invDefaultValue.setDefaultCommentValue(notes);
	}
	
	
    private String getTheParticipationEntityDisplayString(String entityUidStr, String entityClass, HttpSession session)
    { 
    	PersonVO perVO = null;
    	OrganizationVO orgVO = null;
    	QuickEntryEventHelper helper = new QuickEntryEventHelper();
        try
        {
        	Long entityUid = new Long(entityUidStr);
            MainSessionCommand msCommand = null;
            String sBeanJndiName = JNDINames.EntityControllerEJB;
            String sMethod = "getProvider";
            if (entityClass.equalsIgnoreCase(NEDSSConstants.ORGANIZATION_CLASS_CODE))
            	sMethod = "getOrganization";
            Object[] oParams = { entityUid };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            ArrayList<?> resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            if (entityClass.equalsIgnoreCase(NEDSSConstants.PERSON_CLASS_CODE)) {
            	perVO = (PersonVO) resultUIDArr.get(0);
            	return (helper.makePRVDisplayString(perVO));
            } else {
            	orgVO = (OrganizationVO) resultUIDArr.get(0);
            	return (helper.makeORGDisplayString(orgVO));
            }
        }
        catch (Exception e)
        {
            logger.error("Exception in getTheParticipationEntityDisplayString(" + entityUidStr + "," + entityClass + ")", e);
        }
        return entityClass + "not found";
    }
    
    /*
     * If the participation type code is not in the metadata, (this could be true for Generic Investigation)
     * Look through the cache to get the correct par type for the question identifier.
     */
    private String getParticipationTypeForIdentifier(String questionId) {
    	TreeMap<Object,Object> parTypeList = gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns.getParticipationTypeList();
    	ParticipationTypeVO parTypeVO = null;
    	Iterator parTypeIter = parTypeList.entrySet().iterator();
    	while (parTypeIter.hasNext()) {
    		Map.Entry mapPair = (Map.Entry)parTypeIter.next();
    		String keyVal = (String) mapPair.getKey();
    		parTypeVO = (ParticipationTypeVO) mapPair.getValue();
    		if (parTypeVO.getQuestionIdentifier().equalsIgnoreCase(questionId) && parTypeVO.getActClassCd()!=null && parTypeVO.getActClassCd().equals(NEDSSConstants.CASE)) {
    			return parTypeVO.getTypeCd();
    		}
    	}
    	return null;
    }
    
    
    public void updateDSMAlgorithms(NBSSecurityObj nbsSecurityObj) throws Exception{
    	
    	NedssUtils nedssUtils = new NedssUtils();
    	Long algorithId = null;
		try{
			String sBeanJndiName = JNDINames.DSMAlgorithmEJB;
			
			Object DSMAlgorithmLookedUpObject = nedssUtils.lookupBean(sBeanJndiName);
			DSMAlgorithmHome dsmAlgorithmHome = (DSMAlgorithmHome) PortableRemoteObject.narrow(DSMAlgorithmLookedUpObject, DSMAlgorithmHome.class);
			DSMAlgorithm dsmAlgorithm = dsmAlgorithmHome.create();
			
			ArrayList<?>  dwmAlgDTList  = (ArrayList<?>) dsmAlgorithm.selectDSMAlgorithmDTList(nbsSecurityObj);
			logger.debug("dwmAlgDTList.size() to transform -----------------------------"+dwmAlgDTList.size());
			if(dwmAlgDTList != null && dwmAlgDTList.size()>0){
				
					
				for(int i=0;i<dwmAlgDTList.size();i++){
					DSMAlgorithmDT dsmDT = (DSMAlgorithmDT) dwmAlgDTList.get(i);
				
					DSMAlgorithmDT dt = dsmAlgorithm.selectDSMAlgorithm(dsmDT.getDsmAlgorithmUid(), nbsSecurityObj);
					/*Release 5.4, update for lab Algorithms only*/
					if(dt.getAlgorithmPayload()!=null && dt.getEventType()!=null && dt.getEventType().equals(NEDSSConstants.LAB_11648804)){
						algorithId = dsmDT.getDsmAlgorithmUid();
						logger.debug("Updating AlgorithmUid--------"+algorithId);
						String payload = transformDSMAlgorith(dt.getAlgorithmPayload());
						dsmDT.setAlgorithmPayload(payload);
						dsmAlgorithm.updateDSMAlgorithm(dsmDT, nbsSecurityObj);
					}
				}
				
			}
		  }catch (Exception ex) {
				logger.fatal("AlgorithId - "+algorithId+" Exception while updating DSMAlgorithm : "+ ex.getMessage(), ex);
				throw ex;
		  }
		
    }
    
    public String transformDSMAlgorith(String inputPayload) throws Exception{
    	String outputPayload = null;
    	
    	try{
	    	String stylesheet = PropertyUtil.propertiesDir + "UpdateDSMAlgorithms.xsl" ; 
			java.io.StringWriter transformedXML = new java.io.StringWriter();
			javax.xml.transform.TransformerFactory tFactory =
					javax.xml.transform.TransformerFactory.newInstance();
			javax.xml.transform.Transformer transformer =
					tFactory.newTransformer(
						new javax.xml.transform.stream.StreamSource( stylesheet ));
			
			transformer.transform(new javax.xml.transform.stream.StreamSource(new StringReader(inputPayload)),new javax.xml.transform.stream.StreamResult(transformedXML));
			
			outputPayload = transformedXML.toString().replaceAll(" xmlns=\"\"", "");
			
			// Validate transformed XML
			AlgorithmDocument algorithmDoc = AlgorithmDocument.Factory.parse(outputPayload);
	        boolean isXmlValid = algorithmDoc.validate();
	        if(!isXmlValid)
	        	throw new XmlException("DSWAlgorithm Xml Validation failed.");
    	}catch (TransformerConfigurationException ex) {
    		logger.fatal("TransformerConfigurationException while transforming DSMAlgorithm : "+ ex.getMessage(), ex);
    		throw ex;
		}catch (TransformerException ex) {
			logger.fatal("TransformerException while transforming DSMAlgorithm : "+ ex.getMessage(), ex);
    		throw ex;
		}catch(Exception ex){
    		logger.fatal("Exception while transforming DSMAlgorithm : "+ ex.getMessage(), ex);
    		throw ex;
    	}
		return outputPayload;
    }
}