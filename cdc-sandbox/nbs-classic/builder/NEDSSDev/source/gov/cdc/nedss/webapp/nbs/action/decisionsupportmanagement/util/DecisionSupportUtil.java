package gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMessage;

import gov.cdc.nedss.dsm.ActionType;
import gov.cdc.nedss.dsm.AdvancedCriteriaType;
import gov.cdc.nedss.dsm.AlgorithmDocument.Algorithm;
import gov.cdc.nedss.dsm.CodedType;
import gov.cdc.nedss.dsm.CreateInvestigationType;
import gov.cdc.nedss.dsm.CreateInvestigationWithNNDType;
import gov.cdc.nedss.dsm.CriteriaType;
import gov.cdc.nedss.dsm.DefaultValueType;
import gov.cdc.nedss.dsm.ElrAdvancedCriteriaType;
import gov.cdc.nedss.dsm.ElrCriteriaType;
import gov.cdc.nedss.dsm.ElrNumericType;
import gov.cdc.nedss.dsm.InvestigationDefaultValuesType;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DSMAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxRuleConstants;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO.DecisionSupportClientVO;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement.DecisionSupportForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.helper.QuestionDropDownCreator;

public class DecisionSupportUtil {
	
	static final LogUtils logger = new LogUtils(DecisionSupportUtil.class.getName());
	public static Map<Object,Object> questionMap;
	public static Map<Object,Object> advInvQuestionMap;
	
	
	public static void setAlgorithmInfoOnForm(DecisionSupportForm dsForm, DSMAlgorithmDT dsmAlgoDt)
	{
		try {
			DecisionSupportClientVO clientVO = dsForm.getDecisionSupportClientVO();
			//Basic Criteria tab
			clientVO.setAnswer(DecisionSupportConstants.ALGORITHM_NM, dsmAlgoDt.getAlgorithmNm());
			clientVO.setAnswer(DecisionSupportConstants.EVENT_TYPE, dsmAlgoDt.getEventType());
			if("11648804".equals(dsmAlgoDt.getEventType()))
				clientVO.setAnswer(DecisionSupportConstants.PUBLISHED_CONDITION, dsmAlgoDt.getConditionList());
			if(NEDSSConstants.PHC_236.equals(dsmAlgoDt.getEventType()))
				clientVO.setAnswer(DecisionSupportConstants.CONDITIONS, dsmAlgoDt.getConditionList());
			clientVO.setAnswer(DecisionSupportConstants.FREQUENCY, dsmAlgoDt.getFrequency());
			clientVO.setAnswer(DecisionSupportConstants.APPLY_TO, dsmAlgoDt.getApplyTo());
			clientVO.setAnswer(DecisionSupportConstants.SENDING_SYS, dsmAlgoDt.getSendingSystemList());
			clientVO.setAnswer(DecisionSupportConstants.ADMINISTRATIVE_COMMENTS, dsmAlgoDt.getAdminComment());

			//Action Tab
			clientVO.setAnswer(DecisionSupportConstants.ACTION, dsmAlgoDt.getEventAction());

		} catch (Exception ex) {
			logger.error("Exception in setAlgorithmInfoOnForm encountered.." + ex.getMessage());
			ex.printStackTrace();
		}			
	}
	
	
	public static void setDecisionSupportDtforCreateEdit(DecisionSupportForm dsForm, DSMAlgorithmDT dsmAlgoDt)
	{
		try {
			Map<Object,Object> answerMap = dsForm.getDecisionSupportClientVO().getAnswerMap();

			//Basic Criteria tab
			dsmAlgoDt.setAlgorithmNm(getVal(answerMap.get(DecisionSupportConstants.ALGORITHM_NM)));
			dsmAlgoDt.setEventType(getVal(answerMap.get(DecisionSupportConstants.EVENT_TYPE)));
			dsmAlgoDt.setFrequency(getVal(answerMap.get(DecisionSupportConstants.FREQUENCY)));
			dsmAlgoDt.setApplyTo(getVal(answerMap.get(DecisionSupportConstants.APPLY_TO)));
			dsmAlgoDt.setAdminComment(getVal(answerMap.get(DecisionSupportConstants.ADMINISTRATIVE_COMMENTS)));
			// Action Tab
			dsmAlgoDt.setEventAction(getVal(answerMap.get(DecisionSupportConstants.ACTION)));
			dsmAlgoDt.setLastChgTime(new Timestamp((new Date()).getTime()));
			dsmAlgoDt.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
			dsmAlgoDt.setStatusTime(new Timestamp((new Date()).getTime()));
			if(dsmAlgoDt.getAlgorithmPayload()==null)
				dsmAlgoDt.setAlgorithmPayload("Empty String");
		} catch (Exception ex) {
			logger.error("Exception in setDecisionSupportDtforCreateEdit encountered.." + ex.getMessage());
			ex.printStackTrace();
		}	
	}
		
	public static String getVal(Object obj) {
		return obj == null ? "" : (String) obj;

	}
	
	public static void setAnswerArrayMapAnswers(DecisionSupportForm form, DSMAlgorithmDT dsmAlgoDt) {
		try {
			if(dsmAlgoDt.getEventType().equals("11648804"))
			{
				ArrayList<BatchEntry> uiBatchEntryList = form.getDecisionSupportClientVO().getAdvancedCriteriaBatchEntryList();
				StringBuffer answerList = new StringBuffer();

				if(uiBatchEntryList != null && uiBatchEntryList.size() > 0)
				{
					Collections.sort( uiBatchEntryList, new Comparator()
					{
						public int compare( Object a, Object b )
						{
							return((String) ((BatchEntry)a).getDsmAnswerMap().get(DecisionSupportConstants.RESULTEDTEST_CODE)).compareToIgnoreCase((String) ((BatchEntry) b).getDsmAnswerMap().get(DecisionSupportConstants.RESULTEDTEST_CODE));
						}
					} );

					for(int i=0;i<uiBatchEntryList.size();i++)
					{
						BatchEntry listItem = (BatchEntry)uiBatchEntryList.get(i);
						String testCode = (String)listItem.getDsmAnswerMap().get(DecisionSupportConstants.RESULTEDTEST_CODE);
						if(i<uiBatchEntryList.size()-1)
							answerList = answerList.append(testCode).append("^");
						else
							answerList = answerList.append(testCode);				
					}

					dsmAlgoDt.setResultedTestList(answerList.toString());
				}
			}

			Map<Object,Object> answerArrayMap = form.getDecisionSupportClientVO().getArrayAnswerMap();
			//form.getDecisionSupportClientVO().getArrayAnswerMap().putAll(form.getDecisionSupportClientVO().getArrayAnswerMap()); //why? gt
			if(answerArrayMap != null && answerArrayMap.size() > 0) {

				Iterator<Object> anIter = answerArrayMap.keySet().iterator();
				while(anIter.hasNext()) {
					StringBuffer answerList = new StringBuffer();
					String questionId = getVal(anIter.next());
					if(questionId.equals(DecisionSupportConstants.CONDITIONS) || questionId.equals(DecisionSupportConstants.SENDING_SYS)|| questionId.equals(DecisionSupportConstants.APPLY_TO)
							|| questionId.equals(DecisionSupportConstants.PUBLISHED_CONDITION))
					{
						//	continue;
						String[] answers = (String[])answerArrayMap.get(questionId);
						for(int i=1; i<=answers.length; i++) {
							String answerTxt = answers[i-1];
							if((answerTxt != null || (answerTxt != "")) && i != answers.length)
								answerList = answerList.append(answerTxt).append("^");
							else if((answerTxt != null || (answerTxt != "")) && i == answers.length)
								answerList = answerList.append(answerTxt);
						}
					}
					if((questionId.equals(DecisionSupportConstants.CONDITIONS)
							||  questionId.equals(DecisionSupportConstants.PUBLISHED_CONDITION)) && answerList!=null)
					{
						dsmAlgoDt.setConditionList(answerList.toString());
					}
					else if(questionId.equals(DecisionSupportConstants.SENDING_SYS))
						dsmAlgoDt.setSendingSystemList(answerList.toString());
					else if(questionId.equals(DecisionSupportConstants.APPLY_TO))
						dsmAlgoDt.setApplyTo(answerList.toString());
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in setAnswerArrayMapAnswers encountered.." + ex.getMessage());
			ex.printStackTrace();
		}	
	}
	public static void setDtToAnswerArrayMap(DecisionSupportForm form, DSMAlgorithmDT dsmAlgoDt)
	{
		try {
			DecisionSupportClientVO clientVO = form.getDecisionSupportClientVO();
			if(dsmAlgoDt.getSendingSystemList() != null)
			{
				StringTokenizer st2 = new StringTokenizer(dsmAlgoDt.getSendingSystemList(),"^");	
				String [] answerList = new String[st2.countTokens()];
				int noOfTokens = st2.countTokens();
				for(int i=0; i<noOfTokens; i++){
					answerList[i] = (String)st2.nextElement();
				}
				clientVO.setAnswerArray(DecisionSupportConstants.SENDING_SYS, answerList);
			}else
			{
				clientVO.setAnswerArray(DecisionSupportConstants.SENDING_SYS, new String[0]);
			}

			if(dsmAlgoDt.getApplyTo() != null)
			{
				StringTokenizer st2 = new StringTokenizer(dsmAlgoDt.getApplyTo(),"^");	
				String [] answerList = new String[st2.countTokens()];
				int noOfTokens = st2.countTokens();
				for(int i=0; i<noOfTokens; i++){
					answerList[i] = (String)st2.nextElement();
				}
				clientVO.setAnswerArray(DecisionSupportConstants.APPLY_TO, answerList);
			}
			if(dsmAlgoDt.getConditionList() != null)
			{
				StringTokenizer st2 = new StringTokenizer(dsmAlgoDt.getConditionList(),"^");	
				String [] answerList = new String[st2.countTokens()];
				int noOfTokens = st2.countTokens();
				for(int i=0; i<noOfTokens; i++){
					answerList[i] = (String)st2.nextToken();
				}
				if("11648804".equals(dsmAlgoDt.getEventType()))
					clientVO.setAnswerArray(DecisionSupportConstants.PUBLISHED_CONDITION, answerList);
				if(NEDSSConstants.PHC_236.equals(dsmAlgoDt.getEventType()))
					clientVO.setAnswerArray(DecisionSupportConstants.CONDITIONS, answerList);
			}
		} catch (Exception ex) {
			logger.error("Exception in setDtToAnswerArrayMap encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void createBatchMap(DecisionSupportForm form){
		ArrayList<BatchEntry> xmlBatchEntryList = new ArrayList<BatchEntry>();
		try {
			ArrayList<BatchEntry> uiBatchEntryList = form.getIdBatchEntryList();
			Iterator<BatchEntry> it1 = uiBatchEntryList.iterator();
			while(it1.hasNext()){
				BatchEntry xmlBatchEntry = new BatchEntry();
				BatchEntry listItem = (BatchEntry)it1.next();
				listItem.getId();
				Map<Object, Object> xmlAnsMap = new HashMap<Object, Object>();
				xmlAnsMap.put(DecisionSupportConstants.QUESTION, listItem.getAnswerMap().get("questionList")); 
				// check to see if question is a multiselect
				if(listItem.getAnswerMap().get("valueList2") != null && listItem.getAnswerMap().get("valueList2").indexOf("^^") != -1)
				{
					String multiList = listItem.getAnswerMap().get("valueList2");
					StringTokenizer tokenizer = new StringTokenizer(multiList,"^^");
					String [] answerArray = new String[tokenizer.countTokens()];
					int i = 0;
					while(tokenizer.hasMoreTokens()){
						String token = tokenizer.nextToken();
						answerArray[i] = token;
						i++;
					}	

					xmlAnsMap.put(DecisionSupportConstants.VALUE, answerArray); 
				}else if(listItem.getAnswerMap().get("valueList1") != null)
				{
					xmlAnsMap.put(DecisionSupportConstants.VALUE,listItem.getAnswerMap().get("valueList1")); 
				}else if(listItem.getAnswerMap().get("valueList3") != null)
				{
					String value = listItem.getAnswerMap().get("valueList3");
					xmlAnsMap.put(DecisionSupportConstants.VALUE,value); 
				} else if (listItem.getAnswerMap().get("participationUid") != null) {
					String partUid = listItem.getAnswerMap().get("participationUid");
					xmlAnsMap.put(DecisionSupportConstants.ENTITY_VALUE, partUid);
					String questionType = listItem.getAnswerMap().get("questionType");
					if (questionType.contains("Org"))
						xmlAnsMap.put(DecisionSupportConstants.ENTITY_CLASS, NEDSSConstants.ORGANIZATION_CLASS_CODE);
					else
						xmlAnsMap.put(DecisionSupportConstants.ENTITY_CLASS, NEDSSConstants.PERSON_CLASS_CODE); //default to PSN
					xmlAnsMap.put(DecisionSupportConstants.VALUE,listItem.getAnswerMap().get("valValueTxt"));

				} else if(listItem.getAnswerMap().get("valValueTxt") != null){
					xmlAnsMap.put(DecisionSupportConstants.VALUE,listItem.getAnswerMap().get("valValueTxt")); 

				} 
				xmlAnsMap.put(DecisionSupportConstants.BEHAVIOR, listItem.getAnswerMap().get("behavior")); 
				xmlBatchEntry.setDsmAnswerMap(xmlAnsMap);
				xmlBatchEntryList.add(xmlBatchEntry);
			}
			form.getDecisionSupportClientVO().setBatchEntryList(xmlBatchEntryList);
		} catch (Exception ex) {
			logger.error("Exception in createBatchMap encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void createAdvCriteriaBatchMap(DecisionSupportForm form)
	{
		ArrayList<BatchEntry> xmlBatchEntryList = new ArrayList<BatchEntry>();
		try {
			ArrayList<BatchEntry> uiBatchEntryList = form.getAdvancedCriteriaBatchEntryList();
			Iterator<BatchEntry> it1 = uiBatchEntryList.iterator();
			while(it1.hasNext()){
				BatchEntry xmlBatchEntry = new BatchEntry();
				BatchEntry listItem = (BatchEntry)it1.next();
				listItem.getId();
				Map<Object, Object> xmlAnsMap = new HashMap<Object, Object>();
				xmlAnsMap.put(DecisionSupportConstants.CRITERIA_QUESTION,listItem.getAnswerMap().get("AdvQuestionList")); 
				// check to see if question is a multiselect
				if(listItem.getAnswerMap().get("AdvValueList2") != null && listItem.getAnswerMap().get("AdvValueList2").indexOf("^^") != -1)
				{
					String multiList = listItem.getAnswerMap().get("AdvValueList2");
					StringTokenizer tokenizer = new StringTokenizer(multiList,"^^");
					String [] answerArray = new String[tokenizer.countTokens()];
					int i = 0;
					while(tokenizer.hasMoreTokens()){
						String token = tokenizer.nextToken();
						answerArray[i] = token;
						i++;
					}	

					xmlAnsMap.put(DecisionSupportConstants.CRITERIA_VALUE, answerArray); 
				}else if(listItem.getAnswerMap().get("AdvValueList1") != null)
				{
					xmlAnsMap.put(DecisionSupportConstants.CRITERIA_VALUE,listItem.getAnswerMap().get("AdvValueList1")); 
				}else if(listItem.getAnswerMap().get("AdvValueList3") != null)
				{
					String value = listItem.getAnswerMap().get("AdvValueList3");
					xmlAnsMap.put(DecisionSupportConstants.CRITERIA_VALUE,value); 
				}
				else if(listItem.getAnswerMap().get("AdvValValueTxt") != null){
					xmlAnsMap.put(DecisionSupportConstants.CRITERIA_VALUE,listItem.getAnswerMap().get("AdvValValueTxt")); 
				}
				xmlAnsMap.put(DecisionSupportConstants.CRITERIA_LOGIC,listItem.getAnswerMap().get("AdvLogicList"));
				//it is for ELR algorithm
				xmlAnsMap.put(DecisionSupportConstants.RESULTEDTEST_CODE,listItem.getAnswerMap().get("ResultedTestCode"));
				xmlAnsMap.put(DecisionSupportConstants.RESULTEDTEST_NAME,listItem.getAnswerMap().get("ResultedTestName"));

				String codeResult = listItem.getAnswerMap().get("CodedResultAdvList");
				String codeResultTxt = listItem.getAnswerMap().get("CodedResultAdvListTxt");
				String numericResult = listItem.getAnswerMap().get("NumericResultTxt");
				String numericResultHigh = listItem.getAnswerMap().get("numericResultHighTxt");
				String numericResultType = listItem.getAnswerMap().get("NumericResultTypeList");
				String numericResultTypeTxt = listItem.getAnswerMap().get("NumericResultTypeListTxt");
				String NumericResultNameOperator = listItem.getAnswerMap().get("NumericResultNameOperator");
				//String NumericResultNameOperatorTxt = listItem.getAnswerMap().get("ResultNameOperatorTxt"); //GST
				String NumericResultNameOperatorTxt = listItem.getAnswerMap().get("NumericResultNameOperatorTxt"); 
				
				String textResultTxt = listItem.getAnswerMap().get("TextResultTxt");
				String resultNameOperator = listItem.getAnswerMap().get("ResultNameOperator");
				String resultNameOperatorTxt = listItem.getAnswerMap().get("ResultNameOperatorTxt");
				if(codeResult != null && codeResult != "")
				{
					xmlAnsMap.put(DecisionSupportConstants.CODED_RESULT,listItem.getAnswerMap().get("CodedResultAdvList"));
					xmlAnsMap.put(DecisionSupportConstants.CODED_RESULT_TXT,listItem.getAnswerMap().get("CodedResultAdvListTxt"));
					xmlAnsMap.put(DecisionSupportConstants.RESULT_NAME, codeResultTxt);
				}else if(numericResult !=null && numericResult.length()>0)
				{
					if(numericResult != null && numericResult != "")
						xmlAnsMap.put(DecisionSupportConstants.NUMERIC_RESULT, numericResult);
					
					if(NumericResultNameOperator != null && NumericResultNameOperator.length()>0)
					{
						xmlAnsMap.put(DecisionSupportConstants.NUMERIC_RESULT_CRITERIA, NumericResultNameOperator);

						if (NumericResultNameOperatorTxt != null)
							xmlAnsMap.put(DecisionSupportConstants.NUMERIC_RESULT_CRITERIA_TXT, NumericResultNameOperatorTxt);
						else if (NumericResultNameOperatorTxt == null && resultNameOperatorTxt != null && resultNameOperator == null )
							xmlAnsMap.put(DecisionSupportConstants.NUMERIC_RESULT_CRITERIA_TXT, resultNameOperatorTxt); //on create
					}
					
					if(numericResultType != null && numericResultType != "")
					{
						xmlAnsMap.put(DecisionSupportConstants.NUMERIC_RESULT_TYPE, numericResultType);
						xmlAnsMap.put(DecisionSupportConstants.NUMERIC_RESULT_TYPE_TXT, numericResultTypeTxt);
						
						if(numericResultHigh!=null && numericResultHigh.length()>0)
							xmlAnsMap.put(DecisionSupportConstants.RESULT_NAME, numericResult +DecisionSupportConstants.NUMERIC_RESULT_SEPERATOR+ numericResultHigh + " " +numericResultTypeTxt);
						else
							xmlAnsMap.put(DecisionSupportConstants.RESULT_NAME, numericResult + " " + numericResultTypeTxt);
					}else
					{
						xmlAnsMap.put(DecisionSupportConstants.RESULT_NAME, numericResult);
					}
					
					if(numericResultHigh!=null && numericResultHigh.length()>0){
						xmlAnsMap.put(DecisionSupportConstants.NUMERIC_RESULT_HIGH, numericResultHigh);
					}
				}else if(textResultTxt!=null && textResultTxt.length()>0)
				{
					if(textResultTxt != null && textResultTxt != ""){
						xmlAnsMap.put(DecisionSupportConstants.TEXT_RESULT, textResultTxt);
						xmlAnsMap.put(DecisionSupportConstants.RESULT_NAME, textResultTxt);
					}
					if(resultNameOperator != null && resultNameOperator.length()>0)
					{
						xmlAnsMap.put(DecisionSupportConstants.TEXT_RESULT_CRITERIA, resultNameOperator);
						xmlAnsMap.put(DecisionSupportConstants.TEXT_RESULT_CRITERIA_TXT, resultNameOperatorTxt);
					}/*else
					{
						xmlAnsMap.put(DecisionSupportConstants.RESULT_NAME, textResultTxt);
					}*/
				}
				//TODO operator
				xmlBatchEntry.setDsmAnswerMap(xmlAnsMap);
				xmlBatchEntryList.add(xmlBatchEntry);
			}
			form.getDecisionSupportClientVO().setAdvancedCriteriaBatchEntryList(xmlBatchEntryList);
		} catch (Exception ex) {
			logger.error("Exception in createAdvCriteriaBatchMap encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void createAdvInvCriteriaBatchMap(DecisionSupportForm form)
	{
		ArrayList<BatchEntry> xmlBatchEntryList = new ArrayList<BatchEntry>();
		try {
			ArrayList<BatchEntry> uiBatchEntryList = form.getAdvancedInvCriteriaBatchEntryList();
			Iterator<BatchEntry> it1 = uiBatchEntryList.iterator();
			while(it1.hasNext()){
				BatchEntry xmlBatchEntry = new BatchEntry();
				BatchEntry listItem = (BatchEntry)it1.next();
				listItem.getId();
				Map<Object, Object> xmlAnsMap = new HashMap<Object, Object>();
				xmlAnsMap.put(DecisionSupportConstants.ADV_INV_CRITERIA_QUESTION,listItem.getAnswerMap().get("AdvInvQuestionList")); 
				// check to see if question is a multiselect
				if(listItem.getAnswerMap().get("AdvInvValueList2") != null && listItem.getAnswerMap().get("AdvInvValueList2").indexOf("^^") != -1)
				{
					String multiList = listItem.getAnswerMap().get("AdvInvValueList2");
					StringTokenizer tokenizer = new StringTokenizer(multiList,"^^");
					String [] answerArray = new String[tokenizer.countTokens()];
					int i = 0;
					while(tokenizer.hasMoreTokens()){
						String token = tokenizer.nextToken();
						answerArray[i] = token;
						i++;
					}	

					xmlAnsMap.put(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE, answerArray); 
				}else if(listItem.getAnswerMap().get("AdvInvValueList1") != null)
				{
					xmlAnsMap.put(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE,listItem.getAnswerMap().get("AdvInvValueList1")); 
				}else if(listItem.getAnswerMap().get("AdvInvValueList3") != null)
				{
					String value = listItem.getAnswerMap().get("AdvInvValueList3");
					xmlAnsMap.put(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE,value); 
				}
				else if(listItem.getAnswerMap().get("AdvInvValValueTxt") != null){
					xmlAnsMap.put(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE,listItem.getAnswerMap().get("AdvInvValValueTxt")); 
				}
				xmlAnsMap.put(DecisionSupportConstants.ADV_INV_CRITERIA_LOGIC,listItem.getAnswerMap().get("AdvInvLogicList"));
				xmlBatchEntry.setDsmAnswerMap(xmlAnsMap);
				xmlBatchEntryList.add(xmlBatchEntry);
			}
			form.getDecisionSupportClientVO().setAdvancedInvCriteriaBatchEntryList(xmlBatchEntryList);
		} catch (Exception ex) {
			logger.error("Exception in createAdvInvCriteriaBatchMap encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static Long sendDtToEJBForCreate(DSMAlgorithmDT dt, HttpServletRequest request) throws NEDSSAppConcurrentDataException, Exception{

		Long returnVal = null;
		try {
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.DSM_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			String sMethod = "createDSMAlgorithm";

			Object[]  oParams = {dt};
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			returnVal = (Long) resultUIDArr.get(0);
		} catch (Exception ex) {
			logger.error("Exception in sendDtToEJBForCreate encountered.." + ex.getMessage());
			ex.printStackTrace();
	        throw new NEDSSSystemException("Exception in sendDtToEJBForCreate encountered.." + ex.getMessage());
	        
		}
		
		
		
		return returnVal;
	}
	
	public static Long sendDtToEJBForEdit(DSMAlgorithmDT dt, HttpServletRequest request) throws NEDSSAppConcurrentDataException, Exception{

		Long returnVal = null;

		try {
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.DSM_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			String sMethod = "updateDSMAlgorithm";

			Object[]  oParams = {dt};
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			returnVal = (Long) resultUIDArr.get(0);
		} catch (Exception ex) {
			logger.error("Exception in sendDtToEJBForCreate encountered.." + ex.getMessage());
			ex.printStackTrace();
			throw new NEDSSSystemException("Exception in sendDtToEJBForCreate encountered.." + ex.getMessage());
		}		      
		return returnVal;
	}
	
	public static Long sendDtToEJBForActivationInactivation(Long algorithmUid,String status, HttpServletRequest request) throws NEDSSAppConcurrentDataException, Exception{

		Long returnVal = null;
		try {
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.DSM_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			String sMethod = "updateDSMAlgorithmStatus";

			Object[]  oParams = {algorithmUid, status};
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			returnVal = (Long) resultUIDArr.get(0);
		} catch (Exception ex) {
			logger.error("Exception in sendDtToEJBForActivationInactivation encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return returnVal;
	}
	
	public static String getCodedescForCode(String code, String codeSetNm){
		ArrayList<Object> aList = new ArrayList<Object>();
		StringBuffer desc = new StringBuffer("");

		try {
			aList = QuestionDropDownCreator.getInstance().getQuestionDropDownList(codeSetNm);

			if (aList != null && aList.size() > 0) {
				Iterator<Object> ite = aList.iterator();
				while (ite.hasNext()) {
					DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
					if (ddcDT.getKey().equals(code)) {
						desc.append(ddcDT.getValue());
						break;
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in getCodedescForCode encountered.." + ex.getMessage());
			ex.printStackTrace();
		}		
		return desc.toString();
	}
	
	public static String getQueLabelForId(String questionId, DecisionSupportForm form)throws Exception{
		Map<Object, Object> questionMap = form.getQuestionMap();
		Iterator<Object> iter = questionMap.keySet().iterator();
		String questionLabel= null;
		try {
			while(iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
				if(metaData.getQuestionIdentifier() != null && metaData.getQuestionIdentifier().equals(questionId))
				{
					questionLabel = metaData.getQuestionLabel();
					break;
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in getQueLabelForId encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return questionLabel;
	}
	
	public static String getAdvInvQueLabelForId(String questionId, DecisionSupportForm form)throws Exception{
		Map<Object, Object> questionMap = form.getAdvInvQuestionMap();
		Iterator<Object> iter = questionMap.keySet().iterator();
		String questionLabel= null;
		try {
			while(iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
				if(metaData.getQuestionIdentifier() != null && metaData.getQuestionIdentifier().equals(questionId))
				{
					questionLabel = metaData.getQuestionLabel()+" ("+metaData.getQuestionIdentifier()+")";
					break;
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in getQueLabelForId encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return questionLabel;
	}
	
	public static String getQueValueSet(String questionId, DecisionSupportForm form)throws Exception{

		Map<Object, Object> questionMap = form.getQuestionMap();
		Iterator<Object> iter = questionMap.keySet().iterator();
		String valueset= null;
		try {
			while(iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
				if(metaData.getQuestionIdentifier() != null && metaData.getQuestionIdentifier().equals(questionId))
				{
					if(metaData.getDataType() != null && metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE))
					{
						if(metaData.getNbsUiComponentUid()!=null && metaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT))
						{
							valueset = metaData.getCodeSetNm();
						}else if(metaData.getNbsUiComponentUid()!=null && !metaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT))
						{
							valueset= metaData.getCodeSetNm()+ "^^";
						}
					}else if(metaData.getUnitTypeCd()!= null && metaData.getUnitTypeCd().equalsIgnoreCase("CODED"))
					{
						String returnedCode = "";
						Long codeSetGroupId= new Long(metaData.getUnitValue());
						if(codeSetGroupId!=null){
							SRTMapDAOImpl map = new SRTMapDAOImpl();
							returnedCode = map.getConceptCode(codeSetGroupId);
							valueset = returnedCode+"^CODED";
						}
					}
					break;
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in getQueValueSet encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return valueset;

	}
	
	public static String getAdvInvQueValueSet(String questionId, DecisionSupportForm form)throws Exception{

		Map<Object, Object> questionMap = form.getAdvInvQuestionMap();
		Iterator<Object> iter = questionMap.keySet().iterator();
		String valueset= null;
		try {
			while(iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
				if(metaData.getQuestionIdentifier() != null && metaData.getQuestionIdentifier().equals(questionId))
				{
					if(metaData.getDataType() != null && metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE))
					{
						if(metaData.getNbsUiComponentUid()!=null && metaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT))
						{
							valueset = metaData.getCodeSetNm();
						}else if(metaData.getNbsUiComponentUid()!=null && !metaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT))
						{
							valueset= metaData.getCodeSetNm()+ "^^";
						}
					}else if(metaData.getUnitTypeCd()!= null && metaData.getUnitTypeCd().equalsIgnoreCase("CODED"))
					{
						String returnedCode = "";
						Long codeSetGroupId= new Long(metaData.getUnitValue());
						if(codeSetGroupId!=null){
							SRTMapDAOImpl map = new SRTMapDAOImpl();
							returnedCode = map.getConceptCode(codeSetGroupId);
							valueset = returnedCode+"^CODED";
						}
					}
					break;
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in getQueValueSet encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return valueset;
	}
	
	public String getLogicValueSet(String questionId, DecisionSupportForm form)throws Exception{

		Map<Object, Object> questionMap = form.getQuestionMap();
		Iterator<Object> iter = questionMap.keySet().iterator();
		String valueSet= null;
		String questionType = "";
		try {
			while(iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata queMetaData = (NbsQuestionMetadata) questionMap.get(key);
				if(queMetaData.getQuestionIdentifier() != null && queMetaData.getQuestionIdentifier().equals(questionId))
				{
					if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)&& !queMetaData.getNbsUiComponentUid().toString().equals("1009"))
					{
						if(queMetaData.getMask() != null && queMetaData.getMask().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_MASK_NUMERIC))
							valueSet="SEARCH_NUM";
						else if(queMetaData.getMask() != null && queMetaData.getMask().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_MASK_NUMERIC_YEAR))
							valueSet="SEARCH_NUM";
						else{
							valueSet="SEARCH_ALPHA";
						}
					}
					else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)&& queMetaData.getNbsUiComponentUid().toString().equals("1009"))
						valueSet="SEARCH_ALPHA";
					else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE))
						valueSet="SEARCH_NUM";
					else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE))
					{
						if(queMetaData.getNbsUiComponentUid()!=null && queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT))
						{
							valueSet="SEARCH_ALPHA";
						}else if(queMetaData.getNbsUiComponentUid()!=null && !queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT))
						{
							valueSet="SEARCH_ALPHA";
						}
					}else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC) ){
						if(queMetaData.getUnitTypeCd()!= null && queMetaData.getUnitTypeCd().equalsIgnoreCase("CODED"))
						{
							valueSet="SEARCH_ALPHA";
						}else if(queMetaData.getUnitTypeCd()!= null && queMetaData.getUnitTypeCd().equalsIgnoreCase("LITERAL")){
							valueSet="SEARCH_NUM";
						}else{
							valueSet="SEARCH_NUM";
						}
						break;
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in getLogicValueSet encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return valueSet;
	}
	
	public String getAdvInvLogicValueSet(String questionId, DecisionSupportForm form)throws Exception{

		Map<Object, Object> questionMap = form.getAdvInvQuestionMap();
		Iterator<Object> iter = questionMap.keySet().iterator();
		String valueSet= null;
		String questionType = "";
		try {
			while(iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata queMetaData = (NbsQuestionMetadata) questionMap.get(key);
				if(queMetaData.getQuestionIdentifier() != null && queMetaData.getQuestionIdentifier().equals(questionId))
				{
					if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)&& !queMetaData.getNbsUiComponentUid().toString().equals("1009"))
					{
						if(queMetaData.getMask() != null && queMetaData.getMask().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_MASK_NUMERIC))
							valueSet="SEARCH_NUM";
						else if(queMetaData.getMask() != null && queMetaData.getMask().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_MASK_NUMERIC_YEAR))
							valueSet="SEARCH_NUM";
						else{
							valueSet="SEARCH_ALPHA";
						}
					}
					else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)&& queMetaData.getNbsUiComponentUid().toString().equals("1009"))
						valueSet="SEARCH_ALPHA";
					else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE))
						valueSet="SEARCH_NUM";
					else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE))
					{
						if(queMetaData.getNbsUiComponentUid()!=null && queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT))
						{
							valueSet="SEARCH_ALPHA";
						}else if(queMetaData.getNbsUiComponentUid()!=null && !queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT))
						{
							valueSet="SEARCH_ALPHA";
						}
					}else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC) ){
						if(queMetaData.getUnitTypeCd()!= null && queMetaData.getUnitTypeCd().equalsIgnoreCase("CODED"))
						{
							valueSet="SEARCH_ALPHA";
						}else if(queMetaData.getUnitTypeCd()!= null && queMetaData.getUnitTypeCd().equalsIgnoreCase("LITERAL")){
							valueSet="SEARCH_NUM";
						}else{
							valueSet="SEARCH_NUM";
						}
						break;
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in getLogicValueSet encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return valueSet;
	}
    	
	public static void createDefaultUIBatchMap(DecisionSupportForm form, String typeRelPage)throws Exception {
		try {
			// get questionMap from the typeRelPage
			form.getDwrQuestionsList(typeRelPage);
			form.getAdvDwrQuestionsList(typeRelPage);
			ArrayList<BatchEntry> xmlBatchEntryList = form.getDecisionSupportClientVO().getBatchEntryList();

			ArrayList<BatchEntry> uiBatchEntryList = new ArrayList<BatchEntry>();
			int id = 1;
			Iterator<BatchEntry> it1 = xmlBatchEntryList.iterator();
			while(it1.hasNext()){
				BatchEntry uiBatchEntry = new BatchEntry();
				BatchEntry listItem = (BatchEntry)it1.next();
				listItem.getId();
				//iterate to get the questionMetadata
				String questionUid = (String)listItem.getDsmAnswerMap().get(DecisionSupportConstants.QUESTION);
				questionMap = form.getQuestionMap();
				Iterator<Object> iter = questionMap.keySet().iterator();
				NbsQuestionMetadata queMetaData = new NbsQuestionMetadata();
				while(iter.hasNext()) {
					String key = (String) iter.next();
					NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
					if(questionUid != null && questionUid.equals(metaData.getQuestionIdentifier())){
						queMetaData = metaData;
						break;
					}
				}
				Map<String, String> uiAnsMap = new HashMap<String, String>();
				// this is for question column
				uiAnsMap.put("questionList", listItem.getDsmAnswerMap().get(DecisionSupportConstants.QUESTION).toString()); 
				uiAnsMap.put("queListTxt", queMetaData.getQuestionLabel()); 
				// this is for value column
				if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)&& !queMetaData.getNbsUiComponentUid().toString().equals("1009"))
				{
					uiAnsMap.put("valValueTxt", listItem.getDsmAnswerMap().get(DecisionSupportConstants.VALUE).toString()); 
					uiAnsMap.put("questionType","Text"); 
				}else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)&& queMetaData.getNbsUiComponentUid().toString().equals("1009"))
				{
					uiAnsMap.put("valValueTxt", listItem.getDsmAnswerMap().get(DecisionSupportConstants.VALUE).toString()); 
					uiAnsMap.put("questionType","TextArea");
				}else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE))
				{
					uiAnsMap.put("valValueTxt", listItem.getDsmAnswerMap().get(DecisionSupportConstants.VALUE).toString()); 
					uiAnsMap.put("questionType","Date");
				}else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE))
				{
					if(queMetaData.getNbsUiComponentUid()!=null && queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT))
					{
						StringBuffer valueList = new StringBuffer();
						StringBuffer valueText = new StringBuffer();
						String[] answerArray = new String[1];
						Object temp = listItem.getDsmAnswerMap().get(DecisionSupportConstants.VALUE);
						if(temp instanceof String)
							answerArray[0] = (String)temp;
						else
							answerArray = (String[])temp;

						if(answerArray != null && answerArray.length > 0) {
							for (int i=1; i<=answerArray.length; i++)
							{
								valueList = valueList.append("^^");
								String answerTxt = answerArray[i-1];
								if((answerTxt != null || (answerTxt != "")) && i != answerArray.length){
									valueList = valueList.append(answerTxt).append("^^");
									valueText = valueText.append(getCodedescForCode(answerTxt,queMetaData.getCodeSetNm())).append(", ");
								}
								else if((answerTxt != null || (answerTxt != "")) && i == answerArray.length){
									valueList = valueList.append(answerTxt);
									valueText = valueText.append(getCodedescForCode(answerTxt,queMetaData.getCodeSetNm()));
								}
							}
						}
						uiAnsMap.put("valueList2", valueList.toString()); 
						uiAnsMap.put("valValueTxt", valueText.toString()); 
						uiAnsMap.put("questionType","MULTISELECT");
					}else if(queMetaData.getNbsUiComponentUid()!=null && !queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT))
					{
						uiAnsMap.put("valueList1", listItem.getDsmAnswerMap().get(DecisionSupportConstants.VALUE).toString()); 
						uiAnsMap.put("valValueTxt", getCodedescForCode((String)listItem.getDsmAnswerMap().get(DecisionSupportConstants.VALUE), queMetaData.getCodeSetNm())); 
						uiAnsMap.put("questionType","SINGLESELECT");
					}
				}else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC) ){
					if(queMetaData.getUnitTypeCd()!= null && queMetaData.getUnitTypeCd().equalsIgnoreCase("CODED"))
					{
						String structCodedValue = (String)listItem.getDsmAnswerMap().get(DecisionSupportConstants.VALUE);
						String value = structCodedValue.substring(0,structCodedValue.indexOf("^"));
						String code = structCodedValue.substring(structCodedValue.indexOf("^"), structCodedValue.length());
						String returnedCode = "";
						Long codeSetGroupId= new Long(queMetaData.getUnitValue());
						if(codeSetGroupId!=null){
							SRTMapDAOImpl map = new SRTMapDAOImpl();
							returnedCode = map.getConceptCode(codeSetGroupId);
						}
						uiAnsMap.put("valValueTxt",structCodedValue);
						uiAnsMap.put("valueList3",value+getCodedescForCode(code,returnedCode));
						uiAnsMap.put("valCodeTxt",getCodedescForCode(code,returnedCode));
						uiAnsMap.put("questionType","NumericCoded");
					}else if(queMetaData.getUnitTypeCd()!= null && queMetaData.getUnitTypeCd().equalsIgnoreCase("LITERAL"))
					{
						uiAnsMap.put("valValueTxt", listItem.getDsmAnswerMap().get(DecisionSupportConstants.VALUE).toString()); 
						uiAnsMap.put("questionType","NumericLiteral");

					} else
					{
						if(queMetaData.getMask() != null && 
								(queMetaData.getMask().equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_STRUCTURE_NUMERIC)
										||	queMetaData.getMask().equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_DD)
										|| queMetaData.getMask().equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_EXT)
										|| queMetaData.getMask().equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_MM)
										|| queMetaData.getMask().equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_TEMP)
										|| queMetaData.getMask().equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_YYYY)))
						{
							uiAnsMap.put("numericMask",queMetaData.getMask());
						}
						uiAnsMap.put("valValueTxt", listItem.getDsmAnswerMap().get(DecisionSupportConstants.VALUE).toString()); 
						uiAnsMap.put("questionType","NumericLiteral");
					}
				} else if(queMetaData.getDataType() != null && queMetaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_PARTICIPATION))
				{   //Participation
					uiAnsMap.put("valValueTxt", listItem.getDsmAnswerMap().get(DecisionSupportConstants.VALUE).toString());
					uiAnsMap.put("participationSrchRslt", listItem.getDsmAnswerMap().get(DecisionSupportConstants.PARTICIPATION_SEARCH_RESULT).toString());
					uiAnsMap.put("participationUid", listItem.getDsmAnswerMap().get(DecisionSupportConstants.ENTITY_VALUE).toString());  //may not be needed
					String entityClass = listItem.getDsmAnswerMap().get(DecisionSupportConstants.ENTITY_CLASS).toString();
					if (entityClass.equalsIgnoreCase(NEDSSConstants.ORGANIZATION_CLASS_CODE))
						uiAnsMap.put("questionType","ParticipationOrganization");
					else
						uiAnsMap.put("questionType","ParticipationPerson");						
				}

				// this is for behavior column
				String behaviorCode = (String)listItem.getDsmAnswerMap().get(DecisionSupportConstants.BEHAVIOR);
				uiAnsMap.put("behavior", listItem.getDsmAnswerMap().get(DecisionSupportConstants.BEHAVIOR).toString()); 
				uiAnsMap.put("behaviorTxt", getCodedescForCode(behaviorCode, "NBS_DEFAULT_BEHAVIOR")); 
				uiBatchEntry.setAnswerMap(uiAnsMap);
				uiBatchEntry.setId(id);
				uiBatchEntryList.add(uiBatchEntry);
				id++;
			}
			form.setIdBatchEntryList(uiBatchEntryList);

		} catch (Exception ex) {
			logger.error("Exception in createDefaultUIBatchMap encountered.." + ex.getMessage());
			ex.printStackTrace();
		}		
	}
	
	public static void createAdvancedInvCriteriaUIBatchMap(
			DecisionSupportForm form, String typeRelPage) throws Exception {
		try {
			// get questionMap from the typeRelPage
			form.getDwrQuestionsList(typeRelPage);
			form.getAdvDwrQuestionsList(DecisionSupportConstants.CORE_INV_FORM);
			ArrayList<BatchEntry> xmlBatchEntryList = form
					.getDecisionSupportClientVO()
					.getAdvancedInvCriteriaBatchEntryList();

			ArrayList<BatchEntry> uiBatchEntryList = new ArrayList<BatchEntry>();
			int id = 1;
			if (xmlBatchEntryList != null && xmlBatchEntryList.size() > 0) {
				Iterator<BatchEntry> it1 = xmlBatchEntryList.iterator();
				while (it1.hasNext()) {
					BatchEntry uiBatchEntry = new BatchEntry();
					BatchEntry listItem = (BatchEntry) it1.next();
					listItem.getId();
					// iterate to get the questionMetadata
					String questionUid = (String) listItem
							.getDsmAnswerMap()
							.get(DecisionSupportConstants.ADV_INV_CRITERIA_QUESTION);
					advInvQuestionMap = form.getAdvInvQuestionMap();
					Iterator<Object> iter = advInvQuestionMap.keySet().iterator();
					NbsQuestionMetadata queMetaData = new NbsQuestionMetadata();
					while (iter.hasNext()) {
						String key = (String) iter.next();
						NbsQuestionMetadata metaData = (NbsQuestionMetadata) advInvQuestionMap
								.get(key);
						if (questionUid != null
								&& questionUid.equals(metaData
										.getQuestionIdentifier())) {
							queMetaData = metaData;
							break;
						}
					}
					Map<String, String> uiAnsMap = new HashMap<String, String>();
					// this is for question column
					uiAnsMap.put(
							"AdvInvQuestionList",
							listItem.getDsmAnswerMap()
									.get(DecisionSupportConstants.ADV_INV_CRITERIA_QUESTION)
									.toString());
					uiAnsMap.put("AdvInvQueListTxt",
							queMetaData.getQuestionLabel() +" ("+queMetaData.getQuestionIdentifier()+")");
					// this is for value column
					if (queMetaData.getDataType() != null
							&& queMetaData.getDataType().equalsIgnoreCase(
									NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)
							&& !queMetaData.getNbsUiComponentUid().toString()
									.equals("1009")) {
						uiAnsMap.put(
								"AdvInvValValueTxt",
								listItem.getDsmAnswerMap()
										.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE)
										.toString());
						uiAnsMap.put("AdvInvQuestionType", "Text");
						
						if (queMetaData.getMask() != null
								&& (queMetaData
										.getMask()
										.equals(NEDSSConstants.NBS_QUESTION_MASK_NUMERIC) || queMetaData
										.getMask()
										.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_YYYY))) {
							uiAnsMap.put("AdvInvNumericMask",
									queMetaData.getMask());
							uiAnsMap.put("AdvInvQuestionType", "NumericLiteral");
						}
					} else if (queMetaData.getDataType() != null
							&& queMetaData.getDataType().equalsIgnoreCase(
									NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)
							&& queMetaData.getNbsUiComponentUid().toString()
									.equals("1009")) {
						uiAnsMap.put(
								"AdvInvValValueTxt",
								listItem.getDsmAnswerMap()
										.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE)
										.toString());
						uiAnsMap.put("AdvInvQuestionType", "TextArea");
					} else if (queMetaData.getDataType() != null
							&& queMetaData.getDataType().equalsIgnoreCase(
									NEDSSConstants.NBS_QUESTION_DATATYPE_DATE)) {
						uiAnsMap.put(
								"AdvInvValValueTxt",
								listItem.getDsmAnswerMap()
										.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE)
										.toString());
						uiAnsMap.put("AdvInvQuestionType", "Date");
					} else if (queMetaData.getDataType() != null
							&& queMetaData
									.getDataType()
									.equalsIgnoreCase(
											NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
						if (queMetaData.getNbsUiComponentUid() != null
								&& queMetaData
										.getNbsUiComponentUid()
										.toString()
										.equals(NEDSSConstants.MULTISELECT_COMPONENT)) {
							StringBuffer valueList = new StringBuffer();
							StringBuffer valueText = new StringBuffer();
							String[] answerArray = new String[1];
							Object temp = listItem
									.getDsmAnswerMap()
									.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE);
							if (temp instanceof String)
								answerArray[0] = (String) temp;
							else
								answerArray = (String[]) temp;

							if (answerArray != null && answerArray.length > 0) {
								for (int i = 1; i <= answerArray.length; i++) {
									valueList = valueList.append("^^");
									String answerTxt = answerArray[i - 1];
									if ((answerTxt != null || (answerTxt != ""))
											&& i != answerArray.length) {
										valueList = valueList.append(answerTxt)
												.append("^^");
										valueText = valueText
												.append(getCodedescForCode(
														answerTxt, queMetaData
																.getCodeSetNm()))
												.append(", ");
									} else if ((answerTxt != null || (answerTxt != ""))
											&& i == answerArray.length) {
										valueList = valueList.append(answerTxt);
										valueText = valueText
												.append(getCodedescForCode(
														answerTxt, queMetaData
																.getCodeSetNm()));
									}
								}
							}
							uiAnsMap.put("AdvInvValueList2",
									valueList.toString());
							uiAnsMap.put("AdvInvValValueTxt",
									valueText.toString());
							uiAnsMap.put("AdvInvQuestionType", "MULTISELECT");
						} else if (queMetaData.getNbsUiComponentUid() != null
								&& !queMetaData
										.getNbsUiComponentUid()
										.toString()
										.equals(NEDSSConstants.MULTISELECT_COMPONENT)) {
							uiAnsMap.put(
									"AdvInvValueList1",
									listItem.getDsmAnswerMap()
											.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE)
											.toString());
							uiAnsMap.put(
									"AdvInvValValueTxt",
									getCodedescForCode(
											(String) listItem
													.getDsmAnswerMap()
													.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE),
											queMetaData.getCodeSetNm()));
							uiAnsMap.put("AdvInvQuestionType", "SINGLESELECT");
						}
					} else if (queMetaData.getDataType() != null
							&& queMetaData
									.getDataType()
									.equalsIgnoreCase(
											NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
						if (queMetaData.getUnitTypeCd() != null
								&& queMetaData.getUnitTypeCd()
										.equalsIgnoreCase("CODED")) {
							String structCodedValue = (String) listItem
									.getDsmAnswerMap()
									.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE);
							String value = structCodedValue.substring(0,
									structCodedValue.indexOf("^"));
							String code = structCodedValue.substring(
									structCodedValue.indexOf("^"),
									structCodedValue.length());
							String returnedCode = "";
							Long codeSetGroupId = new Long(
									queMetaData.getUnitValue());
							if (codeSetGroupId != null) {
								SRTMapDAOImpl map = new SRTMapDAOImpl();
								returnedCode = map
										.getConceptCode(codeSetGroupId);
							}
							uiAnsMap.put("AdvInvValValueTxt", structCodedValue);
							uiAnsMap.put("AdvInvValueList3", value
									+ getCodedescForCode(code, returnedCode));
							uiAnsMap.put("AdvInvValCodeTxt",
									getCodedescForCode(code, returnedCode));
							uiAnsMap.put("AdvInvQuestionType", "NumericCoded");
						} else if (queMetaData.getUnitTypeCd() != null
								&& queMetaData.getUnitTypeCd()
										.equalsIgnoreCase("LITERAL")) {
							uiAnsMap.put(
									"AdvInvValValueTxt",
									listItem.getDsmAnswerMap()
											.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE)
											.toString());
							uiAnsMap.put("AdvInvQuestionType", "NumericLiteral");

						} else {
							if (queMetaData.getMask() != null
									&& (queMetaData
											.getMask()
											.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_STRUCTURE_NUMERIC)
											|| queMetaData
													.getMask()
													.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_DD)
											|| queMetaData
													.getMask()
													.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_EXT)
											|| queMetaData
													.getMask()
													.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_MM)
											|| queMetaData
													.getMask()
													.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_TEMP) || queMetaData
											.getMask()
											.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_YYYY))) {
								uiAnsMap.put("AdvInvNumericMask",
										queMetaData.getMask());
							}
							uiAnsMap.put(
									"AdvInvValValueTxt",
									listItem.getDsmAnswerMap()
											.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE)
											.toString());
							uiAnsMap.put("AdvInvQuestionType", "NumericLiteral");
						}
					} else if (queMetaData.getDataType() != null
							&& queMetaData
									.getDataType()
									.equalsIgnoreCase(
											NEDSSConstants.NBS_QUESTION_DATATYPE_PARTICIPATION)) { // Participation
						uiAnsMap.put(
								"AdvInvValValueTxt",
								listItem.getDsmAnswerMap()
										.get(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE)
										.toString());
						uiAnsMap.put(
								"AdvInvParticipationSrchRslt",
								listItem.getDsmAnswerMap()
										.get(DecisionSupportConstants.PARTICIPATION_SEARCH_RESULT)
										.toString());
						uiAnsMap.put(
								"AdvInvParticipationUid",
								listItem.getDsmAnswerMap()
										.get(DecisionSupportConstants.ADV_INV_ENTITY_VALUE)
										.toString()); // may not be needed
						String entityClass = listItem
								.getDsmAnswerMap()
								.get(DecisionSupportConstants.ADV_INV_ENTITY_CLASS)
								.toString();
						if (entityClass
								.equalsIgnoreCase(NEDSSConstants.ORGANIZATION_CLASS_CODE))
							uiAnsMap.put("AdvInvQuestionType",
									"ParticipationOrganization");
						else
							uiAnsMap.put("AdvInvQuestionType",
									"ParticipationPerson");
					}

					// this is for logic column
					String logic = (String) listItem.getDsmAnswerMap().get(
							DecisionSupportConstants.ADV_INV_CRITERIA_LOGIC);
					uiAnsMap.put(
							"AdvInvLogicList",
							listItem.getDsmAnswerMap()
									.get(DecisionSupportConstants.ADV_INV_CRITERIA_LOGIC)
									.toString());
					if (uiAnsMap.get("AdvInvQuestionType").toString() == "Date")
						uiAnsMap.put("AdvInvLogicTxt",
								getCodedescForCode(logic, "SEARCH_NUM"));
					else if (uiAnsMap.get("AdvInvQuestionType").toString() == "NumericLiteral")
						uiAnsMap.put("AdvInvLogicTxt",
								getCodedescForCode(logic, "SEARCH_NUM"));
					else
						uiAnsMap.put("AdvInvLogicTxt",
								getCodedescForCode(logic, "SEARCH_ALPHA"));
					uiBatchEntry.setAnswerMap(uiAnsMap);
					uiBatchEntry.setId(id);
					uiBatchEntryList.add(uiBatchEntry);
					id++;
				}
			}
			form.setAdvancedInvCriteriaBatchEntryList(uiBatchEntryList);

		} catch (Exception ex) {
			logger.error("Exception in createAdvancedInvCriteriaUIBatchMap encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void createAdvancedCriteriaUIBatchMap(
			DecisionSupportForm form, String typeRelPage) throws Exception {
		// get questionMap from the typeRelPage
		form.getDwrQuestionsList(typeRelPage);
		ArrayList<BatchEntry> xmlAdvCriteriaBatchEntryList = form
				.getDecisionSupportClientVO()
				.getAdvancedCriteriaBatchEntryList();
		if(xmlAdvCriteriaBatchEntryList==null || xmlAdvCriteriaBatchEntryList.size()==0)
			return;
		ArrayList<BatchEntry> uiAdvCriteriaBatchEntryList = new ArrayList<BatchEntry>();
		try {
			int id = 1;
			Iterator<BatchEntry> it1 = xmlAdvCriteriaBatchEntryList.iterator();
			String eventType = form.getDecisionSupportClientVO().getAnswer(
					DecisionSupportConstants.EVENT_TYPE);
			if (NEDSSConstants.PHC_236.equals(eventType)) {
				while (it1.hasNext()) {
					BatchEntry uiBatchEntry = new BatchEntry();
					BatchEntry listItem = (BatchEntry) it1.next();
					listItem.getId();
					// iterate to get the questionMetadata
					String questionUid = (String) listItem.getDsmAnswerMap().get(
							DecisionSupportConstants.CRITERIA_QUESTION);
					questionMap = form.getQuestionMap();
					Iterator<Object> iter = questionMap.keySet().iterator();
					NbsQuestionMetadata queMetaData = new NbsQuestionMetadata();
					while (iter.hasNext()) {
						String key = (String) iter.next();
						NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap
								.get(key);
						if (questionUid != null
								&& questionUid.equals(metaData
										.getQuestionIdentifier())) {
							queMetaData = metaData;
							break;
						}
					}
					Map<String, String> uiAnsMap = new HashMap<String, String>();
					// this is for question column
					uiAnsMap.put(
							"AdvQuestionList",
							listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.CRITERIA_QUESTION)
							.toString());
					uiAnsMap.put("AdvQueListTxt", queMetaData.getQuestionLabel());
					// this is for value column
					if (queMetaData.getDataType() != null
							&& queMetaData.getDataType().equalsIgnoreCase(
									NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)
									&& !queMetaData.getNbsUiComponentUid().toString()
									.equals("1009")) {
						if (queMetaData.getMask() != null
								&& queMetaData.getMask().equalsIgnoreCase(
										NEDSSConstants.NBS_QUESTION_MASK_NUMERIC)) {
							uiAnsMap.put(
									"AdvValValueTxt",
									listItem.getDsmAnswerMap()
									.get(DecisionSupportConstants.CRITERIA_VALUE)
									.toString());
							uiAnsMap.put("AdvQuestionType", "NumericLiteral");
						} else if (queMetaData.getMask() != null
								&& queMetaData
								.getMask()
								.equalsIgnoreCase(
										NEDSSConstants.NBS_QUESTION_MASK_NUMERIC_YEAR)) {
							uiAnsMap.put(
									"AdvValValueTxt",
									listItem.getDsmAnswerMap()
									.get(DecisionSupportConstants.CRITERIA_VALUE)
									.toString());
							uiAnsMap.put("AdvQuestionType", "NumericLiteral");
						} else {
							uiAnsMap.put(
									"AdvValValueTxt",
									listItem.getDsmAnswerMap()
									.get(DecisionSupportConstants.CRITERIA_VALUE)
									.toString());
							uiAnsMap.put("AdvQuestionType", "Text");
						}
					} else if (queMetaData.getDataType() != null
							&& queMetaData.getDataType().equalsIgnoreCase(
									NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)
									&& queMetaData.getNbsUiComponentUid().toString()
									.equals("1009")) {
						uiAnsMap.put("AdvValValueTxt", listItem.getDsmAnswerMap()
								.get(DecisionSupportConstants.CRITERIA_VALUE)
								.toString());
						uiAnsMap.put("AdvQuestionType", "TextArea");
					} else if (queMetaData.getDataType() != null
							&& queMetaData.getDataType().equalsIgnoreCase(
									NEDSSConstants.NBS_QUESTION_DATATYPE_DATE)) {
						uiAnsMap.put("AdvValValueTxt", listItem.getDsmAnswerMap()
								.get(DecisionSupportConstants.CRITERIA_VALUE)
								.toString());
						uiAnsMap.put("AdvQuestionType", "Date");
					} else if (queMetaData.getDataType() != null
							&& queMetaData
							.getDataType()
							.equalsIgnoreCase(
									NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
						if (queMetaData.getNbsUiComponentUid() != null
								&& queMetaData
								.getNbsUiComponentUid()
								.toString()
								.equals(NEDSSConstants.MULTISELECT_COMPONENT)) {
							StringBuffer valueList = new StringBuffer();
							StringBuffer valueText = new StringBuffer();
							String[] answerArray = (String[]) listItem
									.getDsmAnswerMap()
									.get(DecisionSupportConstants.CRITERIA_VALUE);
							if (answerArray != null && answerArray.length > 0) {
								for (int i = 1; i <= answerArray.length; i++) {
									valueList = valueList.append("^^");
									String answerTxt = answerArray[i - 1];
									if ((answerTxt != null || (answerTxt != ""))
											&& i != answerArray.length) {
										valueList = valueList.append(answerTxt)
												.append("^^");
										valueText = valueText
												.append(getCodedescForCode(
														answerTxt,
														queMetaData.getCodeSetNm()))
														.append(", ");
									} else if ((answerTxt != null || (answerTxt != ""))
											&& i == answerArray.length) {
										valueList = valueList.append(answerTxt);
										valueText = valueText
												.append(getCodedescForCode(
														answerTxt,
														queMetaData.getCodeSetNm()));
									}
								}
							}
							uiAnsMap.put("AdvValueList2", valueList.toString());
							uiAnsMap.put("AdvValValueTxt", valueText.toString());
							uiAnsMap.put("AdvQuestionType", "MULTISELECT");
						} else if (queMetaData.getNbsUiComponentUid() != null
								&& !queMetaData
								.getNbsUiComponentUid()
								.toString()
								.equals(NEDSSConstants.MULTISELECT_COMPONENT)) {
							uiAnsMap.put(
									"AdvValueList1",
									listItem.getDsmAnswerMap()
									.get(DecisionSupportConstants.CRITERIA_VALUE)
									.toString());
							uiAnsMap.put(
									"AdvValValueTxt",
									getCodedescForCode(
											(String) listItem
											.getDsmAnswerMap()
											.get(DecisionSupportConstants.CRITERIA_VALUE),
											queMetaData.getCodeSetNm()));
							uiAnsMap.put("AdvQuestionType", "SINGLESELECT");
						}
					} else if (queMetaData.getDataType() != null
							&& queMetaData.getDataType().equalsIgnoreCase(
									NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
						if (queMetaData.getUnitTypeCd() != null
								&& queMetaData.getUnitTypeCd().equalsIgnoreCase(
										"CODED")) {
							String structCodedValue = (String) listItem
									.getDsmAnswerMap()
									.get(DecisionSupportConstants.CRITERIA_VALUE);
							String value = structCodedValue.substring(0,
									structCodedValue.indexOf("^"));
							String code = structCodedValue.substring(
									structCodedValue.indexOf("^"),
									structCodedValue.length());
							String returnedCode = "";
							Long codeSetGroupId = new Long(
									queMetaData.getUnitValue());
							if (codeSetGroupId != null) {
								SRTMapDAOImpl map = new SRTMapDAOImpl();
								returnedCode = map.getConceptCode(codeSetGroupId);
							}
							uiAnsMap.put("AdvValValueTxt", structCodedValue);
							uiAnsMap.put("AdvValueList3", value
									+ getCodedescForCode(code, returnedCode));
							uiAnsMap.put("AdvValCodeTxt",
									getCodedescForCode(code, returnedCode));
							uiAnsMap.put("AdvQuestionType", "NumericCoded");
						} else if (queMetaData.getUnitTypeCd() != null
								&& queMetaData.getUnitTypeCd().equalsIgnoreCase(
										"LITERAL")) {
							uiAnsMap.put(
									"AdvValValueTxt",
									listItem.getDsmAnswerMap()
									.get(DecisionSupportConstants.CRITERIA_VALUE)
									.toString());
							uiAnsMap.put("AdvQuestionType", "NumericLiteral");
						} else {
							uiAnsMap.put(
									"AdvValValueTxt",
									listItem.getDsmAnswerMap()
									.get(DecisionSupportConstants.CRITERIA_VALUE)
									.toString());
							uiAnsMap.put("AdvQuestionType", "NumericLiteral");
						}
					}

					// this is for behavior column
					String behaviorCode = (String) listItem.getDsmAnswerMap().get(
							DecisionSupportConstants.CRITERIA_LOGIC);
					uiAnsMap.put(
							"AdvLogicList",
							listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.CRITERIA_LOGIC)
							.toString());
					if (uiAnsMap.get("AdvQuestionType").toString() == "Date")
						uiAnsMap.put("AdvLogicTxt",
								getCodedescForCode(behaviorCode, "SEARCH_NUM"));
					else if (uiAnsMap.get("AdvQuestionType").toString() == "NumericLiteral")
						uiAnsMap.put("AdvLogicTxt",
								getCodedescForCode(behaviorCode, "SEARCH_NUM"));
					else
						uiAnsMap.put("AdvLogicTxt",
								getCodedescForCode(behaviorCode, "SEARCH_ALPHA"));
					uiBatchEntry.setAnswerMap(uiAnsMap);
					uiBatchEntry.setId(id);
					uiAdvCriteriaBatchEntryList.add(uiBatchEntry);
					id++;
				}
			}
			else if("11648804".equals(eventType))
			{
				while (it1.hasNext()) 
				{
					BatchEntry uiBatchEntry = new BatchEntry();
					BatchEntry listItem = (BatchEntry) it1.next();
					Map<String, String> uiAnsMap = new HashMap<String, String>();
					// this is for question column
					Object resultedTestCode = listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.RESULTEDTEST_CODE);
					if(resultedTestCode != null)
						uiAnsMap.put(
								"ResultedTestCode",
								resultedTestCode.toString());
					Object resultedTestName = listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.RESULTEDTEST_NAME);
					if(resultedTestName != null)
						uiAnsMap.put(
								"ResultedTestName",
								resultedTestName.toString());

					Object codedResultAdvList = listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.CODED_RESULT);
					if(codedResultAdvList != null)
						uiAnsMap.put(
								"CodedResultAdvList",
								codedResultAdvList.toString());

					Object codedResultAdvListTxt = listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.CODED_RESULT_TXT);
					if(codedResultAdvListTxt != null)
						uiAnsMap.put(
								"CodedResultAdvListTxt",
								codedResultAdvListTxt.toString());

					Object numericResultTxt = listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.NUMERIC_RESULT);
					if(numericResultTxt != null)
						uiAnsMap.put(
								"NumericResultTxt",
								numericResultTxt.toString());
					// Looking for numericResultHigh value ( Applicable for Between operator )
					Object numericResultHighTxt = listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.NUMERIC_RESULT_HIGH);
					if(numericResultHighTxt != null)
						uiAnsMap.put(
								"numericResultHighTxt",
								numericResultHighTxt.toString());

					Object numericResultTypeList = listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.NUMERIC_RESULT_TYPE);
					if(numericResultTypeList != null)
						uiAnsMap.put(
								"NumericResultTypeList",
								numericResultTypeList.toString());

					Object numericResultTypeListTxt = listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.NUMERIC_RESULT_TYPE_TXT);
					if(numericResultTypeListTxt != null)
						uiAnsMap.put(
								"NumericResultTypeListTxt",
								numericResultTypeListTxt.toString());

					Object textResultTxt = listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.TEXT_RESULT);
					if(textResultTxt != null)
						uiAnsMap.put(
								"TextResultTxt",
								textResultTxt.toString());

					Object textResultCriteria = listItem.getDsmAnswerMap().get(DecisionSupportConstants.TEXT_RESULT_CRITERIA);
					if(textResultCriteria != null)
						uiAnsMap.put("ResultNameOperator",textResultCriteria.toString());
					
					Object textResultCriteriaTxt = listItem.getDsmAnswerMap().get(DecisionSupportConstants.TEXT_RESULT_CRITERIA_TXT);
					if(textResultCriteriaTxt != null)
						uiAnsMap.put("ResultNameOperatorTxt",textResultCriteriaTxt.toString());
					
					Object textNumericResultCriteria = listItem.getDsmAnswerMap().get(DecisionSupportConstants.NUMERIC_RESULT_CRITERIA);
					if(textNumericResultCriteria != null)
						uiAnsMap.put("NumericResultNameOperator",textNumericResultCriteria.toString());
					
					Object textNumericResultCriteriaTxt = listItem.getDsmAnswerMap().get(DecisionSupportConstants.NUMERIC_RESULT_CRITERIA_TXT);
					if(textNumericResultCriteriaTxt != null)
						uiAnsMap.put("NumericResultNameOperatorTxt",textNumericResultCriteriaTxt.toString());
					
					Object resultNameTxt = listItem.getDsmAnswerMap()
							.get(DecisionSupportConstants.RESULT_NAME);
					if(resultNameTxt != null)
						uiAnsMap.put(
								"ResultNameTxt",
								resultNameTxt.toString());

					uiBatchEntry.setAnswerMap(uiAnsMap);
					uiBatchEntry.setId(id);
					uiAdvCriteriaBatchEntryList.add(uiBatchEntry);
					id++;
				}
			}
			form.setAdvancedCriteriaBatchEntryList(uiAdvCriteriaBatchEntryList);
		} catch (Exception ex) {
			logger.error("Exception in createAdvancedCriteriaBatchMap encountered.." + ex.getMessage());
			ex.printStackTrace();
		}	
	}

	public static DSMAlgorithmDT getDtfromEJBForViewEdit(Long algorithmUid, HttpServletRequest request) throws NEDSSAppConcurrentDataException, Exception{
		DSMAlgorithmDT returnVal = null;
		try {
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.DSM_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			String sMethod = "selectDSMAlgorithm";

			Object[]  oParams = {algorithmUid};
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			returnVal = (DSMAlgorithmDT) resultUIDArr.get(0);
		} catch (Exception ex) {
			logger.error("Exception in getDtfromEJBForViewEdit encountered.." + ex.getMessage());
			ex.printStackTrace();
		}			      
		return returnVal;
	}

	public static Map<String, String> getRalatedPagesForConditionCodes(ArrayList<String> conditionCodes, HttpServletRequest request) throws NEDSSAppConcurrentDataException, Exception{
		Map<String, String> conditionCodeRalatedPages = null;
		try {
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.DSM_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			String sMethod = "retrieveRalatedPagesForConditionCodes";

			Object[]  oParams = {conditionCodes};
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			conditionCodeRalatedPages = (Map<String, String>) resultUIDArr.get(0);
		} catch (Exception ex) {
			logger.error("Exception in getRalatedPagesForConditionCodes encountered.." + ex.getMessage());
			ex.printStackTrace();
		}	
		return conditionCodeRalatedPages;
	}
	
	public static String getRelatedPageCodeForConditionCode(String conditionCode, HttpServletRequest request) throws NEDSSAppConcurrentDataException, Exception{
		String pageCode = null;
		try {
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.DSM_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			String sMethod = "retrievePageCodeForConditionCode";

			Object[]  oParams = {conditionCode};
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			pageCode = (String) resultUIDArr.get(0);
		} catch (Exception ex) {
			logger.error("Exception in getRalatedPagesForConditionCodes encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return pageCode;
	}
	
	public static void writeXMLResponseStream(HttpServletResponse response, String xmlString, String algorithmName) throws IOException
	{
		int length   = 0;
	    response.setContentType("application/octet-stream"); 
        response.setHeader("Content-disposition", "attachment;filename="+ algorithmName + ".xml"); 
        ServletOutputStream op = null;
        DataInputStream in = null;
		try {
			op = response.getOutputStream();      
	        byte[] bbuf = new byte[1000];       
	      
	        InputStream is = new ByteArrayInputStream(xmlString.getBytes("UTF-8")); 
	        in = new DataInputStream(is);
	        while ((in != null) && ((length = in.read(bbuf)) != -1))
	        {
	        	op.write(bbuf,0,length);
	        }
	        in.close();
	        op.flush();
	        op.close();
		} finally {
				try {
					if(in != null)
						in.close();
					if(op != null)
						op.close();
				} catch (IOException e) {
			}			
		}	
	}
	
	public static void processElrResultedTest(Algorithm algorithm,
			Map<String, ActionMessage> resultedTestMessageMap,
			Map<String, ActionMessage> codedResultMessageMap,
			Map<String, ActionMessage> numericTypeMessageMap,
			DecisionSupportForm dsForm, HttpServletRequest request) throws Exception {
		try { 
			if(algorithm.getElrAdvancedCriteria() != null)
			{
				ElrAdvancedCriteriaType elrAdvancedCriteria = algorithm.getElrAdvancedCriteria();
				ElrCriteriaType[] criteria = elrAdvancedCriteria.getElrCriteriaArray();
				List<ElrCriteriaType> codedResultCriteriaList = new ArrayList <ElrCriteriaType>();
				List<CodedType> codedResultNoExistingCriteriaList = new ArrayList <CodedType>();
				List<Object> codedResultList = dsForm.getCodedResultList();

				List<ElrCriteriaType> numericResultCriteriaList = new ArrayList <ElrCriteriaType>();
				List<CodedType> numericResultNoExistingCriteriaList = new ArrayList <CodedType>();
				List<Object> numericResultList = dsForm.getResultTypeList();

				List<CodedType> resultedTestNoExistingCriteriaList = new ArrayList <CodedType>();

				List<ElrCriteriaType> goodResultedTestList = new ArrayList <ElrCriteriaType>();


				for (int i = 0; i < criteria.length; i++) {
					ElrCriteriaType elrCriteriaType = criteria[i];
					boolean isRecordGood = true;
					CodedType resultedTestCodedType = elrCriteriaType.getResultedTest();
					if(resultedTestCodedType != null)
					{
						String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
						String sMethod = "findLabResultedTestByCode";
						Object[] oParams = new Object[]{"DEFAULT", null, resultedTestCodedType.getCode(), null};
						HttpSession session = request.getSession(false);
						MainSessionHolder holder = new MainSessionHolder();
						MainSessionCommand msCommand = holder.getMainSessionCommand(session);
						ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
						ArrayList<?> resultedTestList = (ArrayList<?> )arrList.get(0);
						if(resultedTestList == null || resultedTestList.size()==0)
						{
							resultedTestNoExistingCriteriaList.add(resultedTestCodedType);
							isRecordGood = false;
						}
					}

					CodedType codedResultCodedType = elrCriteriaType.getElrCodedResultValue();
					if(codedResultCodedType != null)
					{
						try {
							String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
							String sMethod = "findLabCodedResultByCode";
							Object[] oParams = new Object[]{"DEFAULT", null, codedResultCodedType.getCode(), null};
							HttpSession session = request.getSession(false);
							MainSessionHolder holder = new MainSessionHolder();
							MainSessionCommand msCommand = holder.getMainSessionCommand(session);
							ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
							ArrayList<?> codedResults = (ArrayList<?> )arrList.get(0);

							if(codedResults == null || codedResults.size()==0)
							{
								codedResultNoExistingCriteriaList.add(codedResultCodedType);
								isRecordGood = false;
							}
						} catch (Exception ex) {
							logger.error("Exception calling findLabCodedResultByCode encountered.." + ex.getMessage());
							ex.printStackTrace();
						}
					}
					ElrNumericType numericType = elrCriteriaType.getElrNumericResultValue();
					if(numericType != null)
					{
						CodedType numericResultCodedType = elrCriteriaType.getElrNumericResultValue().getUnit();
						if(numericResultCodedType != null)
						{
							Iterator iterator = numericResultList.iterator();
							boolean isExist = false;

							while(iterator.hasNext())
							{
								DropDownCodeDT numericResultDT = (DropDownCodeDT)iterator.next();
								if(numericResultCodedType.getCode().equals(numericResultDT.getKey()))
								{
									numericResultCriteriaList.add(elrCriteriaType);
									isExist = true;
									break;
								}
							}
							if(!isExist)
							{
								numericResultNoExistingCriteriaList.add(numericResultCodedType);
								isRecordGood = false;
							}
						}
					}
					if(isRecordGood)
						goodResultedTestList.add(elrCriteriaType);
				}

				if(!resultedTestNoExistingCriteriaList.isEmpty())
				{
					StringBuffer buffer = bufferStringListWithCode(resultedTestNoExistingCriteriaList, ",");			
					ActionMessage message = new ActionMessage(NBSPageConstants.IMPORT_WARNING_RESULTEDTEST_NOMATCH_MESSAGE_KEY, algorithm.getAlgorithmName(), buffer.toString());
					resultedTestMessageMap.put("warning", message);
				}	
				if(!codedResultNoExistingCriteriaList.isEmpty())
				{
					StringBuffer buffer = bufferStringList(codedResultNoExistingCriteriaList, ",");			
					ActionMessage message = new ActionMessage(NBSPageConstants.IMPORT_WARNING_CODEDRESULT_NOMATCH_MESSAGE_KEY, algorithm.getAlgorithmName(), buffer.toString());
					codedResultMessageMap.put("warning", message);
				}	
				if(!numericResultNoExistingCriteriaList.isEmpty())
				{
					StringBuffer buffer = bufferStringList(numericResultNoExistingCriteriaList, ",");			
					ActionMessage message = new ActionMessage(NBSPageConstants.IMPORT_WARNING_NUMRESULTUNIT_NOMATCH_MESSAGE_KEY, algorithm.getAlgorithmName(), buffer.toString());
					numericTypeMessageMap.put("warning", message);
				}

				ElrCriteriaType[] goodResultedTestArray = new ElrCriteriaType[goodResultedTestList.size()];
				elrAdvancedCriteria.setElrCriteriaArray(goodResultedTestList.toArray(goodResultedTestArray));
			}

		} catch (Exception ex) {
			logger.error("Exception calling processElrResultedTest encountered.." + ex.getMessage());
			ex.printStackTrace();
		}		
	}

	public static void processQuestionList(Algorithm algorithm, Map<String, ActionMessage> advQuestionMessageMap,
			Map<String, ActionMessage> questionMessageMap,
			DecisionSupportForm dsForm) {
		try {
			if (algorithm.getAdvancedCriteria() != null)
			{
				AdvancedCriteriaType advancedCriteria = algorithm.getAdvancedCriteria();
				CriteriaType[] criteria = advancedCriteria.getCriteriaArray();
				List<CriteriaType> criteriaList = new ArrayList <CriteriaType>();
				List<CodedType> noExistingCriteriaList = new ArrayList <CodedType>();

				List<Object> advDwrQuestionsList = dsForm.getAdvDwrQuestionsList(algorithm.getInvestigationType());
				for (int i = 0; i < criteria.length; i++) {
					CriteriaType criteriaType = criteria[i];
					CodedType question = criteriaType.getCriteriaQuestion();

					Iterator iterator = advDwrQuestionsList.iterator();
					boolean isExist = false;
					while(iterator.hasNext())
					{
						DropDownCodeDT questionDT = (DropDownCodeDT)iterator.next();
						if(question.getCode().equals(questionDT.getKey()))
						{
							criteriaList.add(criteriaType);
							isExist = true;
							break;
						}
					}

					if(!isExist)
						noExistingCriteriaList.add(question);
				}

				if(!noExistingCriteriaList.isEmpty())
				{
					StringBuffer buffer = bufferStringList(noExistingCriteriaList, ",");			
					ActionMessage message = new ActionMessage(NBSPageConstants.IMPORT_WARNING_ADVQUESTION_PARTMATCH_MESSAGE_KEY, "Advanced Criteria", buffer.toString());
					advQuestionMessageMap.put("warning", message);
				}	

				if(criteriaList.isEmpty())
					algorithm.setAdvancedCriteria(null);
				else
				{
					CriteriaType []array = new CriteriaType[criteriaList.size()]; 
					advancedCriteria.setCriteriaArray(criteriaList.toArray(array));
				}
			}
			ActionType action = algorithm.getAction();
			InvestigationDefaultValuesType investigationDefaultValues = null;
			CreateInvestigationType createInvestigation = null;
			CreateInvestigationWithNNDType createInvestigationWithNND = null;
			if (action.getCreateInvestigation() != null)
			{
				createInvestigation = action.getCreateInvestigation();
				investigationDefaultValues = createInvestigation.getInvestigationDefaultValues();
			}else if (action.getCreateInvestigationWithNND() != null)
			{
				createInvestigationWithNND = action.getCreateInvestigationWithNND();
				investigationDefaultValues = createInvestigationWithNND.getInvestigationDefaultValues();
			}

			if (investigationDefaultValues != null)
			{
				String investigationType = algorithm.getInvestigationType();
				if(investigationType == null)
				{
					if(createInvestigation != null)
					{
						createInvestigation.setInvestigationDefaultValues(null);
					}else if (createInvestigationWithNND !=  null)
					{
						createInvestigationWithNND.setInvestigationDefaultValues(null);
					}
				}
				else
				{
					List<DefaultValueType> criteriaList = new ArrayList <DefaultValueType>();
					List<CodedType> noExistingCriteriaList = new ArrayList <CodedType>();
					DefaultValueType[] defaultValueArray = investigationDefaultValues.getDefaultValueArray();
					List<Object> dwrQuestionsList = dsForm.getDwrQuestionsList(investigationType);

					for (int i = 0; i < defaultValueArray.length; i++)
					{
						DefaultValueType defaultValue = defaultValueArray[i];
						CodedType question = defaultValue.getDefaultQuestion();

						Iterator iterator = dwrQuestionsList.iterator();
						boolean isExist = false;
						while(iterator.hasNext())
						{
							DropDownCodeDT questionDT = (DropDownCodeDT)iterator.next();
							if(question.getCode().equals(questionDT.getKey()))
							{
								criteriaList.add(defaultValue);
								isExist = true;
								break;
							}
						}
						if(!isExist && !question.getCode().equals("INV167"))
							noExistingCriteriaList.add(question);
						if(!isExist && question.getCode().equals("INV167"))
							criteriaList.add(defaultValue);
					}

					if(!noExistingCriteriaList.isEmpty())
					{
						StringBuffer buffer = bufferStringList(noExistingCriteriaList, ",");		
						ActionMessage message = new ActionMessage(NBSPageConstants.IMPORT_WARNING_QUESTION_PARTMATCH_MESSAGE_KEY, "Investigation Default Values", buffer.toString());
						questionMessageMap.put("warning", message);
					}	

					if(criteriaList.isEmpty())
					{
						investigationDefaultValues.setDefaultValueArray(null);
					}else
					{
						DefaultValueType []array = new DefaultValueType[criteriaList.size()]; 
						investigationDefaultValues.setDefaultValueArray(criteriaList.toArray(array));
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in processQuestionList encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static DSMAlgorithmDT parseAlgorithmXMLToDT(Algorithm algorithm, 
			Map<String, ActionMessage> sendingSystemMessageMap, Map<String, ActionMessage> pageConditionMessageMap,
			HttpServletRequest request) throws NEDSSAppConcurrentDataException, Exception {		
		DSMAlgorithmDT dsmAlgorithmDT = new DSMAlgorithmDT();
		try {
			String algorithmName = algorithm.getAlgorithmName();
			dsmAlgorithmDT.setAlgorithmNm(algorithmName);
			String comment = algorithm.getComment();
			dsmAlgorithmDT.setAdminComment(comment);

			String applyTo = calculateApplyToList(algorithm);
			dsmAlgorithmDT.setApplyTo(applyTo);
			String conditionList = calculateConditionList(algorithm, pageConditionMessageMap, request);
			dsmAlgorithmDT.setConditionList(conditionList);
			if(algorithm.getAction() != null)
			{
				if(algorithm.getAction().getCreateInvestigation() != null)
					dsmAlgorithmDT.setEventAction(EdxRuleConstants.CREATE_INVESTIGATION);
				if(algorithm.getAction().getCreateInvestigationWithNND()!= null)
					dsmAlgorithmDT.setEventAction(EdxRuleConstants.CREATE_INVESTIGATION_AND_NND);
				if(algorithm.getAction().getMarkAsReviewed()!= null)
					dsmAlgorithmDT.setEventAction(EdxRuleConstants.MARK_AS_REVIEWED);
			}
			if(algorithm.getEvent()!= null && algorithm.getEvent().getCode() != null)
				dsmAlgorithmDT.setEventType(algorithm.getEvent().getCode());
			if(algorithm.getFrequency() != null && algorithm.getFrequency().getCode() != null)
				dsmAlgorithmDT.setFrequency(algorithm.getFrequency().getCode());

			String sendingSystemList = calculateSendingSystemList(algorithm, sendingSystemMessageMap);
			dsmAlgorithmDT.setSendingSystemList(sendingSystemList);

			dsmAlgorithmDT.setItNew(true);
			dsmAlgorithmDT.setItDirty(false);
			dsmAlgorithmDT.setItDelete(false);
		} catch (Exception ex) {
			logger.error("Exception parseAlgorithmXMLToDT encountered.." + ex.getMessage());
			ex.printStackTrace();
		}		
		return dsmAlgorithmDT;
	}
	
	private static String calculateConditionList(Algorithm algorithm, 
			Map<String, ActionMessage> pageConditionMessageMap, HttpServletRequest request) throws NEDSSAppConcurrentDataException, Exception {
		StringBuffer resultStrBuffer = new StringBuffer();
		try {
			
			if(algorithm.getApplyToConditions() == null || 
					algorithm.getApplyToConditions().getConditionArray() == null)
				return null;

			CodedType[] codedTypes = algorithm.getApplyToConditions().getConditionArray();
			ArrayList<Object> conditions = CachedDropDowns.getAllConditions();
			List<CodedType> importedConditions = new ArrayList<CodedType>(Arrays.asList(codedTypes)); 
			List<CodedType> existingConditions = new ArrayList<CodedType>();
			List<CodedType> nonExistingConditions = new ArrayList<CodedType>();

			for(int i=0; i<codedTypes.length-1; i++){
				String systemCode = codedTypes[i].getCode();
				Iterator iterator = conditions.iterator();
				boolean isExisting = false;
				while(iterator.hasNext())
				{
					DropDownCodeDT system = (DropDownCodeDT)iterator.next();
					if(system.getKey().equals(systemCode))
					{
						existingConditions.add(codedTypes[i]);
						resultStrBuffer.append(codedTypes[i].getCode());
						resultStrBuffer.append("^");
						isExisting = true;
						break;
					}			
				}
				if(!isExisting)
					nonExistingConditions.add(codedTypes[i]);
			}

			CodedType lastCodedType = codedTypes[codedTypes.length-1];
			Iterator iterator = conditions.iterator();
			boolean isExisting = false;
			while(iterator.hasNext())
			{
				DropDownCodeDT system = (DropDownCodeDT)iterator.next();
				if(system.getKey().equals(lastCodedType.getCode()))
				{
					existingConditions.add(lastCodedType);
					resultStrBuffer.append(lastCodedType.getCode());
					isExisting = true;
					break;
				}			
			}
			if(!isExisting)
				nonExistingConditions.add(lastCodedType);

			if(existingConditions.isEmpty())
			{
				StringBuffer buffer = bufferStringList(nonExistingConditions, ",");		
				ActionMessage message = new ActionMessage(NBSPageConstants.IMPORT_ERROR_CONDITION_NOMATCH_MESSAGE_KEY, buffer.toString());
				pageConditionMessageMap.put("error", message);
			}
			else{
				Iterator iter = existingConditions.iterator();
				List<CodedType> appliedConditions = new ArrayList<CodedType>();
				List<CodedType> notAppliedConditions = new ArrayList<CodedType>();
				boolean isOnePageApplied = true;
				String firstPageCode = null;
				while(iter.hasNext())
				{			
					CodedType existingCondition = (CodedType)iter.next();
					String pageCode = getRelatedPageCodeForConditionCode(existingCondition.getCode(), request);

					if(firstPageCode == null && pageCode != null)
						firstPageCode = pageCode;
					if(firstPageCode != null && pageCode != null && !firstPageCode.equals(pageCode))
					{
						isOnePageApplied = false;
						StringBuffer buffer = bufferStringList(importedConditions, ",");
						ActionMessage message = new ActionMessage(NBSPageConstants.IMPORT_WARNING_CONDITION_MUTIPAGESMATCH_MESSAGE_KEY, buffer.toString());
						pageConditionMessageMap.put("warning", message);
						request.setAttribute("highLightTab", "action");
					}

					if(pageCode == null && !algorithm.getAction().isSetMarkAsReviewed())
						notAppliedConditions.add(existingCondition);
					else
						appliedConditions.add(existingCondition);
				}
				if(algorithm.getInvestigationType() != null && (NBSConstantUtil.INV_FORM_VAR.equals(algorithm.getInvestigationType())
						|| NBSConstantUtil.INV_FORM_RVCT.equals(algorithm.getInvestigationType())))
					return resultStrBuffer.toString();

				if(isOnePageApplied)
				{
					resultStrBuffer = new StringBuffer();
					algorithm.setInvestigationType(firstPageCode);
					if(!appliedConditions.isEmpty())
					{
						for(int i = 0; i < appliedConditions.size()-1; i++)
						{
							resultStrBuffer.append(appliedConditions.get(i).getCode());
							resultStrBuffer.append("^");
						}
						resultStrBuffer.append(appliedConditions.get(appliedConditions.size()-1).getCode());
					}	
				}
				else
					algorithm.setInvestigationType(null);
				if(pageConditionMessageMap.isEmpty() && appliedConditions.isEmpty() && !algorithm.getAction().isSetMarkAsReviewed())
				{
					StringBuffer buffer = bufferStringList(importedConditions, ",");
					ActionMessage message = new ActionMessage(NBSPageConstants.IMPORT_ERROR_CONDITION_NOMATCHPAGE_MESSAGE_KEY, buffer.toString());
					pageConditionMessageMap.put("error", message);
				}
				if(pageConditionMessageMap.isEmpty() && (!notAppliedConditions.isEmpty()|| !nonExistingConditions.isEmpty()) && !appliedConditions.isEmpty())
				{
					StringBuffer buffer1 = bufferStringList(appliedConditions, ",");
					StringBuffer buffer2 = new StringBuffer();
					notAppliedConditions.addAll(nonExistingConditions);
					buffer2.append(bufferStringList(notAppliedConditions, ","));
					ActionMessage message = new ActionMessage(NBSPageConstants.IMPORT_WARNING_CONDITION_PAGEPARTMATCH_MESSAGE_KEY, algorithm.getAlgorithmName(),
							buffer1.toString(),buffer2.toString());
					pageConditionMessageMap.put("warning", message);
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in calculateConditionList encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return resultStrBuffer.toString();
	}
	
	private static String calculateSendingSystemList(Algorithm algorithm, Map<String, ActionMessage> sendingSystemMessageMap) {
		StringBuffer resultStrBuffer = new StringBuffer();
		try {
			if(algorithm.getApplyToSendingSystems() == null || 
					algorithm.getApplyToSendingSystems().getSendingSystemArray() == null)
				return null;
			ArrayList<Object> systems = CachedDropDowns.getSendingSystemList(algorithm.getEvent().getCode());
			CodedType[] codedTypes = algorithm.getApplyToSendingSystems().getSendingSystemArray();
			List<CodedType> existingSystems = new ArrayList<CodedType>();
			List<CodedType> nonExistingSystems = new ArrayList<CodedType>();
			for(int i=0; i<codedTypes.length-1; i++){
				String systemCode = codedTypes[i].getCode();
				Iterator iterator = systems.iterator();
				boolean isExisting = false;
				while(iterator.hasNext())
				{
					DropDownCodeDT system = (DropDownCodeDT)iterator.next();
					if(system.getKey().equals(systemCode))
					{
						existingSystems.add(codedTypes[i]);
						resultStrBuffer.append(codedTypes[i].getCode());
						resultStrBuffer.append("^");
						isExisting = true;
						break;
					}			
				}
				if(!isExisting)
					nonExistingSystems.add(codedTypes[i]);		
			}

			CodedType lastCodedType = codedTypes[codedTypes.length-1];
			Iterator iterator = systems.iterator();
			boolean isExisting = false;
			while(iterator.hasNext())
			{
				DropDownCodeDT system = (DropDownCodeDT)iterator.next();
				if(system.getKey().equals(lastCodedType.getCode()))
				{
					existingSystems.add(lastCodedType);
					resultStrBuffer.append(lastCodedType.getCode());
					isExisting = true;
					break;
				}			
			}
			if(!isExisting)
				nonExistingSystems.add(lastCodedType);

			if(existingSystems.isEmpty() && !nonExistingSystems.isEmpty())
			{
				String algorithmName = algorithm.getAlgorithmName();
				ActionMessage message = new ActionMessage(NBSPageConstants.IMPORT_WARNING_SENDSYSTEM_NOMATCH_MESSAGE_KEY, algorithmName);
				sendingSystemMessageMap.put("warning", message);
			}

			if(!existingSystems.isEmpty() && !nonExistingSystems.isEmpty())
			{
				String algorithmName = algorithm.getAlgorithmName();
				StringBuffer buffer1 = bufferStringList(existingSystems, ",");
				StringBuffer buffer2 = bufferStringList(nonExistingSystems, ",");
				ActionMessage message = new ActionMessage(NBSPageConstants.IMPORT_WARNING_SENDSYSTEM_PARTMATCH_MESSAGE_KEY, algorithmName, buffer1.toString(), buffer2.toString());
				sendingSystemMessageMap.put("warning", message);
			}
		} catch (Exception ex) {
			logger.error("Exception in calculateSendingSystemList encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return resultStrBuffer.toString();
	}
	
	private static StringBuffer bufferStringList(List<CodedType> list, String delimiter)
	{
		StringBuffer buffer = new StringBuffer();
		try {
			if(list != null && !list.isEmpty())
			{
				for(int i=0; i<list.size()-1; i++){
					buffer.append(list.get(i).getCodeDescTxt());
					buffer.append(delimiter);
					buffer.append(" ");
				}
				buffer.append(list.get(list.size()-1).getCodeDescTxt());
			}
		} catch (Exception ex) {
			logger.error("Exception in bufferStringList encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return buffer;
	}
	
	private static StringBuffer bufferStringListWithCode(List<CodedType> list, String delimiter)
	{
		StringBuffer buffer = new StringBuffer();
		try {
			if(list != null && !list.isEmpty())
			{
				for(int i=0; i<list.size()-1; i++){
					buffer.append(list.get(i).getCodeDescTxt() + "(" + list.get(i).getCode() + ")");
					buffer.append(delimiter);
					buffer.append(" ");
				}
				buffer.append(list.get(list.size()-1).getCodeDescTxt() + "(" + list.get(list.size()-1).getCode() + ")");
			}
		} catch (Exception ex) {
			logger.error("Exception in bufferStringList encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return buffer;
	}
	
	private static String calculateApplyToList(Algorithm algorithm) {
		StringBuffer resultStrBuffer = new StringBuffer();
		try {
			if(algorithm.getAppliesToEntryMethods() == null || 
					algorithm.getAppliesToEntryMethods().getEntryMethodArray() == null)
				return null;

			CodedType[] codedTypes = algorithm.getAppliesToEntryMethods().getEntryMethodArray();	
			for(int i=0; i<codedTypes.length-1; i++){
				resultStrBuffer.append(codedTypes[i].getCode());
				resultStrBuffer.append("^");
			}
			resultStrBuffer.append(codedTypes[codedTypes.length-1].getCode());	
		} catch (Exception ex) {
			logger.error("Exception in bufferStringList encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		return resultStrBuffer.toString();
	}

	public static String parseISToString(java.io.InputStream is){
		java.io.DataInputStream din = new java.io.DataInputStream(is);
		StringBuffer sb = new StringBuffer();
		try{
			String line = null;
			while((line=din.readLine()) != null){
				sb.append(line+"\n");
			}
		}catch(Exception ex){
			ex.getMessage();
		}finally{
			try{
				is.close();
			}catch(Exception ex){}
		}
		return sb.toString();
	}
}
