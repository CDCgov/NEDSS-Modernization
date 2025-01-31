package gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dao.DSMAlgorithmDaoImpl;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO.*;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.helper.QuestionDropDownCreator;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

public class DecisionSupportForm extends BaseForm {
	static final LogUtils logger = new LogUtils(
			DecisionSupportForm.class.getName());

	private static final long serialVersionUID = 1L;

	public static PropertyUtil propertyUtil = PropertyUtil.getInstance();

	private DecisionSupportClientVO decisionSupportClientVO = new DecisionSupportClientVO();

	private Map<Object, ArrayList<BatchEntry>> batchEntryMap = new HashMap<Object, ArrayList<BatchEntry>>();

	private Map<Object, ArrayList<BatchEntry>> advancedCriteriaBatchEntryMap = new HashMap<Object, ArrayList<BatchEntry>>();

	Object oldDT = new Object();

	// for action Batch List
	private ArrayList<BatchEntry> idBatchEntryList = new ArrayList<BatchEntry>();

	private static int nextSeqId = 1;
	
	private ArrayList<BatchEntry> advancedInvCriteriaBatchEntryList = new ArrayList<BatchEntry>();

	// for action Batch List
	private ArrayList<BatchEntry> advancedCriteriaBatchEntryList = new ArrayList<BatchEntry>();
	private static int nextAdvSeqId = 1;

	DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();

	public static Map<Object, Object> questionMap = new HashMap<Object, Object>();
	
	public static Map<Object, Object> advInvQuestionMap = new HashMap<Object, Object>();

	

	private FormFile importFile;

	private String fromImport = "No";

	private String numericResultList;

	private String textResultList;
	
	public String getFromImport() {
		return fromImport;
	}

	public void setFromImport(String fromImport) {
		this.fromImport = fromImport;
	}
	
	public static Map<Object, Object> getAdvInvQuestionMap() {
		return advInvQuestionMap;
	}

	public static void setAdvInvQuestionMap(Map<Object, Object> advInvQuestionMap) {
		DecisionSupportForm.advInvQuestionMap = advInvQuestionMap;
	}
	
	public static void addToAdvInvQuestionMap(Map<Object, Object> map) {
		DecisionSupportForm.advInvQuestionMap.putAll(map);
	}

	public static Map<Object, Object> getQuestionMap() {
		return questionMap;
	}

	public static void setQuestionMap(Map<Object, Object> questionMap) {
		DecisionSupportForm.questionMap = questionMap;
	}

	public static void addToQuestionMap(Map<Object, Object> map) {
		DecisionSupportForm.questionMap.putAll(map);
	}

	private ArrayList<Object> dwrQuestions = new ArrayList<Object>();

	private ArrayList<Object> advDwrQuestions = new ArrayList<Object>();

	private ArrayList<Object> advInvDwrQuestions = new ArrayList<Object>();
	
	private ArrayList<Object> dwrConditions = new ArrayList<Object>();

	private ArrayList<Object> dwrAction = new ArrayList<Object>();

	private ArrayList<Object> dwrAdvanceValues = new ArrayList<Object>();
	private ArrayList<Object> dwrAdvanceInvValues = new ArrayList<Object>();

	private ArrayList<Object> codedResultList = new ArrayList<Object>();
	private ArrayList<Object> resultTypeList = new ArrayList<Object>();
	
	public ArrayList<Object> getDwrAdvanceInvValues() {
		return dwrAdvanceInvValues;
	}

	public void setDwrAdvanceInvValues(ArrayList<Object> dwrAdvanceInvValues) {
		this.dwrAdvanceInvValues = dwrAdvanceInvValues;
	}

	public ArrayList<Object> getAdvDwrQuestions() {
		return advDwrQuestions;
	}
	
	public void setAdvDwrQuestions(ArrayList<Object> advDwrQuestions) {
		this.advDwrQuestions = advDwrQuestions;
	}
	
	public ArrayList<Object> getAdvInvDwrQuestions() {
		return advInvDwrQuestions;
	}
	
	public void setAdvInvDwrQuestions(ArrayList<Object> advInvDwrQuestions) {
		this.advInvDwrQuestions = advInvDwrQuestions;
	}

	public ArrayList<Object> getDwrAdvanceValues() {
		return dwrAdvanceValues;
	}

	public void setDwrAdvanceValues(ArrayList<Object> dwrAdvanceValues) {
		this.dwrAdvanceValues = dwrAdvanceValues;
	}

	public ArrayList<Object> getDwrAction() {
		return dwrAction;
	}

	public void setDwrAction(ArrayList<Object> dwrAction) {
		this.dwrAction = dwrAction;
	}

	public ArrayList<Object> getDwrConditions() {
		return dwrConditions;
	}

	public void setDwrConditions(ArrayList<Object> dwrConditions) {
		this.dwrConditions = dwrConditions;
	}

	public ArrayList<Object> getDwrValue() {
		return dwrValue;
	}

	public void setDwrValue(ArrayList<Object> dwrValue) {
		this.dwrValue = dwrValue;
	}

	private ArrayList<Object> dwrValue = new ArrayList<Object>();

	private String eventTy;
	
	private ArrayList<Object> advInvDwrValue = new ArrayList<Object>();


	public ArrayList<Object> getAdvInvDwrValue() {
		return advInvDwrValue;
	}

	public void setAdvInvDwrValue(ArrayList<Object> advInvDwrValue) {
		this.advInvDwrValue = advInvDwrValue;
	}

	public String getEventTy() {
		return eventTy;
	}

	public void setEventTy(String eventTy) {
		this.eventTy = eventTy;
	}

	public DecisionSupportClientVO getDecisionSupportClientVO() {
		return decisionSupportClientVO;
	}

	public void setDecisionSupportClientVO(
			DecisionSupportClientVO decisionSupportClientVO) {
		this.decisionSupportClientVO = decisionSupportClientVO;
	}

	public Map<Object, ArrayList<BatchEntry>> getBatchEntryMap() {
		return batchEntryMap;
	}

	public void setBatchEntryMap(
			Map<Object, ArrayList<BatchEntry>> batchEntryMap) {
		this.batchEntryMap = batchEntryMap;
	}

	public Map<Object, ArrayList<BatchEntry>> getAdvancedCriteriaBatchEntryMap() {
		return advancedCriteriaBatchEntryMap;
	}

	public void setAdvancedCriteriaBatchEntryMap(
			Map<Object, ArrayList<BatchEntry>> advancedCriteriaBatchEntryMap) {
		this.advancedCriteriaBatchEntryMap = advancedCriteriaBatchEntryMap;
	}

	public Object getOldDT() {
		return oldDT;
	}

	public void setOldDT(Object oldDT) {
		this.oldDT = copyObject(oldDT);
	}

	private static Object copyObject(Object param) {
		Object deepCopy = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(param);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			deepCopy = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deepCopy;
	}

	public Object getCodedValue(String key) {
		ArrayList<Object> aList = (ArrayList<Object>) CachedDropDowns
				.getCodedValueForType(key);

		if ("PUBLIC_HEALTH_EVENT".equals(key)) {
			Iterator iterator = aList.iterator();
			ArrayList<Object> tempList = new ArrayList<Object>();

			while (iterator.hasNext()) {
				DropDownCodeDT dropDownCodeDT = (DropDownCodeDT) iterator
						.next();
				if (!"CMR".equals(dropDownCodeDT.getKey()) && !"Z32".equals(dropDownCodeDT.getKey()))
					tempList.add(dropDownCodeDT);
			}
			aList = tempList;
		}
		if ("NBS_EVENT_ACTION".equals(key)
				&& NEDSSConstants.PHC_236.equals(decisionSupportClientVO
						.getAnswer(DecisionSupportConstants.EVENT_TYPE))) {
			Iterator iterator = aList.iterator();
			ArrayList<Object> tempList = new ArrayList<Object>();

			while (iterator.hasNext()) {
				DropDownCodeDT dropDownCodeDT = (DropDownCodeDT) iterator
						.next();
				if (!"3".equals(dropDownCodeDT.getKey()))
					tempList.add(dropDownCodeDT);
			}
			aList = tempList;
		}

		return aList;
	}

	public ArrayList<Object> getCodedValueForApplyTo(String key) {
		ArrayList<Object> aList = (ArrayList<Object>) CachedDropDowns
				.getCodedValueForType(key);
		Iterator<Object> it1 = aList.iterator();
		while (it1.hasNext()) {
			DropDownCodeDT listItem = (DropDownCodeDT) it1.next();
			if (listItem.getKey().equals("")) {
				it1.remove();
			}
		}

		Collections.sort(aList, new Comparator() {
			public int compare(Object a, Object b) {
				return (((DropDownCodeDT) a).getKey())
						.compareToIgnoreCase(((DropDownCodeDT) b).getKey());
			}
		});

		return aList;
	}
	
	public ArrayList<Object> getDwrQuestions() {
		return dwrQuestions;
	}

	public void setDwrQuestions(ArrayList<Object> dwrQuestions) {
		this.dwrQuestions = dwrQuestions;
	}

	public ArrayList<Object> getConditionList() {
		return CachedDropDowns.getConditionWithNoPortReqInd();
	}

	public ArrayList<Object> getRelatedPage() {
		return CachedDropDowns.getInvestigationTypeRelatedPage();
	}

	public ArrayList<Object> getSendingSystemList() {
		return CachedDropDowns.getSendingSystemList(NEDSSConstants.PHC_236);
	}

	public ArrayList<Object> getLaboratoryList() {
		return CachedDropDowns.getSendingSystemList("11648804");
	}

	public void reset() {

		decisionSupportClientVO = new DecisionSupportClientVO();
		nextSeqId = 1;
		idBatchEntryList = new ArrayList<BatchEntry>();
		advancedCriteriaBatchEntryList = new ArrayList<BatchEntry>();
		advancedInvCriteriaBatchEntryList = new ArrayList<BatchEntry>();
		fromImport = "No";
		questionMap = new HashMap<Object, Object>();
		advInvQuestionMap = new HashMap<Object, Object>();

		dwrQuestions = new ArrayList<Object>();
		advDwrQuestions = new ArrayList<Object>();
		advInvDwrQuestions = new ArrayList<Object>();
		super.resetLDF();
	}

	public ArrayList<BatchEntry> getAllBatchAnswer(String subSectionNm) {
		return (idBatchEntryList);
	}

	public ArrayList<BatchEntry> getAllAdvancedCriteriaBatchAnswer(
			String subSectionNm) {
		return (advancedCriteriaBatchEntryList);
	}
	public ArrayList<BatchEntry> getAllAdvancedInvCriteriaBatchAnswer(
			String subSectionNm) {
		return (advancedInvCriteriaBatchEntryList);
	}

	public void deleteBatchAnswer(BatchEntry batchEntry) {
		// determine what sequence number this item should be
		int seq = batchEntry.getId();
		Iterator<BatchEntry> it1 = this.idBatchEntryList.iterator();
		while (it1.hasNext()) {
			BatchEntry listItem = (BatchEntry) it1.next();
			if (seq == listItem.getId()) {
				it1.remove();
			}
		}
	}

	public void clearBatchAnswer() {
		idBatchEntryList = new ArrayList<BatchEntry>();
	}

	public void clearAdvancedBatchEntry() {
		advancedCriteriaBatchEntryList = new ArrayList<BatchEntry>();
	}
	public void clearAdvancedInvBatchEntry() {
		advancedInvCriteriaBatchEntryList = new ArrayList<BatchEntry>();
	}

	public void deleteAdvancedCriteriaBatchAnswer(BatchEntry batchEntry) {
		// determine what sequence number this item should be
		int seq = batchEntry.getId();
		Iterator<BatchEntry> it1 = this.advancedCriteriaBatchEntryList
				.iterator();
		while (it1.hasNext()) {
			BatchEntry listItem = (BatchEntry) it1.next();
			if (seq == listItem.getId()) {
				it1.remove();
			}
		}
	}
	
	public void deleteAdvancedInvCriteriaBatchAnswer(BatchEntry batchEntry) {
		// determine what sequence number this item should be
		int seq = batchEntry.getId();
		Iterator<BatchEntry> it1 = this.advancedInvCriteriaBatchEntryList
				.iterator();
		while (it1.hasNext()) {
			BatchEntry listItem = (BatchEntry) it1.next();
			if (seq == listItem.getId()) {
				it1.remove();
			}
		}
	}

	public void updateBatchAnswer(BatchEntry batchEntry) {
		// find the id and replace it
		boolean removedIt = false;
		int entrySeq = batchEntry.getId();
		Iterator<BatchEntry> it1 = this.idBatchEntryList.iterator();
		while (it1.hasNext()) {
			BatchEntry listItem = (BatchEntry) it1.next();
			if (entrySeq == listItem.getId()) {
				it1.remove();
				removedIt = true;
			}
		}
		if (removedIt)
			idBatchEntryList.add(batchEntry);
	}

	public void updateAdvancedCriteriaBatchAnswer(BatchEntry batchEntry) {
		// find the id and replace it
		boolean removedIt = false;
		int entrySeq = batchEntry.getId();
		Iterator<BatchEntry> it1 = this.advancedCriteriaBatchEntryList
				.iterator();
		while (it1.hasNext()) {
			BatchEntry listItem = (BatchEntry) it1.next();
			if (entrySeq == listItem.getId()) {
				it1.remove();
				removedIt = true;
			}
		}
		if (removedIt)
			advancedCriteriaBatchEntryList.add(batchEntry);
	}
	
	public void updateAdvancedInvCriteriaBatchAnswer(BatchEntry batchEntry) {
		// find the id and replace it
		boolean removedIt = false;
		int entrySeq = batchEntry.getId();
		Iterator<BatchEntry> it1 = this.advancedInvCriteriaBatchEntryList
				.iterator();
		while (it1.hasNext()) {
			BatchEntry listItem = (BatchEntry) it1.next();
			if (entrySeq == listItem.getId()) {
				it1.remove();
				removedIt = true;
			}
		}
		if (removedIt)
			advancedInvCriteriaBatchEntryList.add(batchEntry);
	}

	public void updateElrAdvancedCriteriaBatchAnswer(BatchEntry batchEntry) {
		updateAdvancedCriteriaBatchAnswer(batchEntry);
	}

	public ArrayList<BatchEntry> getIdBatchEntryList() {
		return this.idBatchEntryList;
	}

	public void setIdBatchEntryList(ArrayList<BatchEntry> idBatchEntryList) {
		this.idBatchEntryList = idBatchEntryList;
	}

	public ArrayList<BatchEntry> getAdvancedCriteriaBatchEntryList() {
		return this.advancedCriteriaBatchEntryList;
	}

	public void setAdvancedCriteriaBatchEntryList(
			ArrayList<BatchEntry> advancedCriteriaBatchEntryList) {
		this.advancedCriteriaBatchEntryList = advancedCriteriaBatchEntryList;
	}
	
	public ArrayList<BatchEntry> getAdvancedInvCriteriaBatchEntryList() {
		return this.advancedInvCriteriaBatchEntryList;
	}

	public void setAdvancedInvCriteriaBatchEntryList(
			ArrayList<BatchEntry> advancedCriteriaBatchEntryList) {
		this.advancedInvCriteriaBatchEntryList = advancedCriteriaBatchEntryList;
	}

	public static synchronized int getNextSeqId() {
		return nextSeqId++;
	}

	public static int getNextAdvSeqId() {
		return nextAdvSeqId++;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Object> getAdvDwrQuestionsList(String invFormCd) {
		WebContext ctx = WebContextFactory.get();
		ArrayList<Object> questionList = new ArrayList<Object>();
		DSMAlgorithmDaoImpl dsmAlgoDao = new DSMAlgorithmDaoImpl();
		Map advQuestionMap = new HashMap<Object, Object>();
		
		if(invFormCd != null && !invFormCd.equals("")) {
				advQuestionMap = new HashMap<Object, Object>();
	
			if(invFormCd.equals(NBSConstantUtil.INV_FORM_RVCT)|| invFormCd.equals(NBSConstantUtil.INV_FORM_VAR)){
				if(QuestionsCache.getQuestionMap()!=null && QuestionsCache.getQuestionMap().containsKey(invFormCd))
    			{
					advQuestionMap = (Map<Object, Object> )QuestionsCache.getQuestionMap().get(invFormCd);
    			}
			}
    		else 
    		{
    			if(QuestionsCache.dmbMap.containsKey(invFormCd))
    				advQuestionMap.putAll((Map<Object, Object> )QuestionsCache.dmbMap.get(invFormCd));
    			else if(!QuestionsCache.dmbMap.containsKey(invFormCd) && propertyUtil.getServerRestart()!=null && propertyUtil.getServerRestart().equals("F"))
    			{
    				Map<Object, Object> questions = (Map<Object, Object> )QuestionsCache.getDMBQuestionMapAfterPublish().get(invFormCd);
    				if(questions != null)
    					advQuestionMap.putAll(questions);
    			}
    			else
    				advQuestionMap = new HashMap<Object,Object>();
    		}
			// create ArrayList for the question drop down
			int i = 1;
			DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
			dropDownCodeDT.setKey("");
			dropDownCodeDT.setValue("");
			questionList.add(0, dropDownCodeDT);
	
			Iterator<Object> iter = advQuestionMap.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) advQuestionMap
						.get(key);
				DropDownCodeDT dropDownCodeDT1 = new DropDownCodeDT();
				if (metaData.getNbsUiComponentUid() != null
						&& (metaData.getNbsUiComponentUid().intValue() == 1007
								|| metaData.getNbsUiComponentUid().intValue() == 1008
								|| metaData.getNbsUiComponentUid().intValue() == 1009
								|| metaData.getNbsUiComponentUid().intValue() == 1013
								|| metaData.getNbsUiComponentUid().intValue() == 1017
								)
						&& metaData.getStandardNndIndCd() != null
						&& metaData.getStandardNndIndCd().equals("F")
						&& (metaData.getHl7SegmentField() == null || (metaData.getHl7SegmentField() != null && metaData
								.getHl7SegmentField().equals("OBX-3.0")))
						&& metaData.getQuestionGroupSeqNbr() == null
						&& metaData.getQuestionUnitIdentifier() == null && (metaData.getDataLocation().toUpperCase().startsWith("PUBLIC_HEALTH_CASE") || metaData.getDataLocation().toUpperCase().startsWith("CONFIRMATION_METHOD"))) {
					dropDownCodeDT1.setKey(metaData.getQuestionIdentifier());
					String testcharvalue=metaData.getQuestionLabel() + " ("+metaData.getQuestionIdentifier() + ")";
		    		int maxLength=100;
		    		dropDownCodeDT1.setValue(charLimitCheck(testcharvalue,maxLength));
					questionList.add(i, dropDownCodeDT1);
					i++;
				}
			}

		}
		if (questionList != null && questionList.size()>0)
			Collections.sort(questionList, new Comparator() {
				public int compare(Object a, Object b) {
					return (((DropDownCodeDT) a).getValue())
							.compareToIgnoreCase(((DropDownCodeDT) b)
									.getValue());
				}
			});
		if (invFormCd != null
				&& invFormCd.equals(DecisionSupportConstants.CORE_INV_FORM)){
			setAdvInvDwrQuestions(questionList);
		addToAdvInvQuestionMap(advQuestionMap);
		}else{
			setAdvDwrQuestions(questionList);
		addToQuestionMap(advQuestionMap);
		}
		return questionList;

	}
	
	public ArrayList<Object> getCoreQuestionsList(){
		ArrayList<Object> questionList = getAdvDwrQuestionsList(DecisionSupportConstants.CORE_INV_FORM) ;

		return questionList ;
	}

	public String charLimitCheck(String testString, int maxLength) {
		String validString = null;

		if (testString.length() >= maxLength) {
			validString = testString.substring(0, maxLength) + " ...";
		} else {
			validString = testString;
		}
		return validString;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Object> getDwrQuestionsList(String invFormCd){
	    	ArrayList<Object> questionList = new ArrayList<Object> ();
	    	Map<Object,Object> tempMap = new HashMap<Object,Object>();
	    	Map<String, DropDownCodeDT> dropdownMap = new HashMap<String, DropDownCodeDT>();
	    	//Check to see if it is single condition or multiple conditions
	    	if(invFormCd != null){
    			if(invFormCd.equals(NBSConstantUtil.INV_FORM_RVCT)|| invFormCd.equals(NBSConstantUtil.INV_FORM_VAR)){
					if(QuestionsCache.getQuestionMap()!=null && QuestionsCache.getQuestionMap().containsKey(invFormCd))
	    			{
	    				tempMap = (Map<Object, Object> )QuestionsCache.getQuestionMap().get(invFormCd);
	    			}
    			}
	    		else 
	    		{
	    			if(QuestionsCache.dmbMap.containsKey(invFormCd))
	    				tempMap.putAll((Map<Object, Object> )QuestionsCache.dmbMap.get(invFormCd));
	    			else if(!QuestionsCache.dmbMap.containsKey(invFormCd) && propertyUtil.getServerRestart()!=null && propertyUtil.getServerRestart().equals("F"))
	    			{
	    				Map<Object, Object> questions = (Map<Object, Object> )QuestionsCache.getDMBQuestionMapAfterPublish().get(invFormCd);
	    				if(questions != null)
	    					tempMap.putAll(questions);
	    			}
	    			else
	    				tempMap = new HashMap<Object,Object>();
	    		}
	    		
	    		// create ArrayList for the question drop down
		    	int i =1;
		    	DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		    	dropDownCodeDT.setKey(""); dropDownCodeDT.setValue("");
		    	questionList.add(0, dropDownCodeDT);
		    	if(tempMap != null){
		    		Iterator<Object> tempIter = tempMap.keySet().iterator();    	
			    	while(tempIter.hasNext()) {
			    		String key = (String) tempIter.next();
			    		NbsQuestionMetadata metaData = (NbsQuestionMetadata) tempMap.get(key);
 			    		 if( metaData.getNbsUiComponentUid() != null && 
			    				(metaData.getNbsUiComponentUid().intValue() == 1007 ||metaData.getNbsUiComponentUid().intValue() == 1008 || metaData.getNbsUiComponentUid().intValue() == 1009 || metaData.getNbsUiComponentUid().intValue() == 1013 || metaData.getNbsUiComponentUid().intValue() == 1017 || metaData.getNbsUiComponentUid().intValue() == 1024|| metaData.getNbsUiComponentUid().intValue() == 1025 || metaData.getNbsUiComponentUid().intValue() == 1026)
			    						 && metaData.getStandardNndIndCd() != null && metaData.getStandardNndIndCd().equals("F") && 
			    						 (metaData.getHl7SegmentField()==null || (metaData.getHl7SegmentField() != null && metaData.getHl7SegmentField().equals("OBX-3.0"))) 
			    						 && metaData.getQuestionGroupSeqNbr() == null && metaData.getQuestionUnitIdentifier()==null)
			    		{
			    			 if("S_JURDIC_C".equals(metaData.getCodeSetNm()) || "S_PROGRA_C".equals(metaData.getCodeSetNm()) || "SubjOfPHC".equals(metaData.getPartTypeCd()))
		    					 continue;
			    			DropDownCodeDT dropDownCodeDT1 = new DropDownCodeDT();
				    		dropDownCodeDT1.setKey(metaData.getQuestionIdentifier());
				    		
				    		String testcharvalue=metaData.getQuestionLabel() + " ("+metaData.getQuestionIdentifier() + ")";
			    		
				    		dropDownCodeDT1.setValue(charLimitCheck(testcharvalue,100));
				    		 dropdownMap.put(key, dropDownCodeDT1);
				    		i++;
			    		}
			    	}
			    	
		    	}
	    	}    	
	    	questionList.addAll(dropdownMap.values());
	    	if(questionList != null)
	    	Collections.sort( questionList, new Comparator()
	        {
	        public int compare( Object a, Object b )
	           {
	           return( ((DropDownCodeDT)a).getValue() ).compareToIgnoreCase( ((DropDownCodeDT) b).getValue() );
	           }
	        } );
		if (invFormCd != null
				&& invFormCd.equals(DecisionSupportConstants.CORE_INV_FORM)){
			setAdvInvDwrQuestions(questionList);
			addToAdvInvQuestionMap(tempMap);
		}
		else{
			setDwrQuestions(questionList);
	    	addToQuestionMap(tempMap);
		}
	    	return questionList;

	    }

	public ArrayList<Object> getValueList() {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String codeSet = request.getAttribute("valueSet").toString();
		ArrayList<Object> aList = CachedDropDowns.getCodedValueForType(codeSet);
		return aList;
	}

	public ArrayList<Object> getConditionDropDown(String relatedPage) {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		ArrayList<Object> aList = CachedDropDowns
				.getConditionDropDown(relatedPage);
		request.setAttribute("conditionList", aList);
		setDwrConditions(aList);
		return aList;
	}

	public ArrayList<Object> getPublishedConditionDropDown() {
		ArrayList<Object> aList = CachedDropDowns
				.getPublishedConditionDropDown("dummyData");
		return aList;
	}
	public ArrayList<Object> getAllConditions() {
		ArrayList<Object> aList = CachedDropDowns.getAllConditions();
		return aList;
	}
	public DropDownCodeDT getPublishedConditionPage(String conditionCode) {
		DropDownCodeDT conditionPageDT = CachedDropDowns
				.getConditionPage(conditionCode);
		return conditionPageDT;
	}
	
	public String getDWRAdvInvQuestionProperties(String questionUid) throws Exception {
		WebContext ctx = WebContextFactory.get();
		String questionProp = "";
		Iterator<Object> iter = advInvQuestionMap.keySet().iterator();
		NbsQuestionMetadata queMetaData = new NbsQuestionMetadata();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			NbsQuestionMetadata metaData = (NbsQuestionMetadata) advInvQuestionMap
					.get(key);
			if (questionUid != null
					&& questionUid.equals(metaData.getQuestionIdentifier())) {
				queMetaData = metaData;
				break;
			}
		}
		if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)
				&& !queMetaData.getNbsUiComponentUid().toString().equals("1009")      //not TextArea
				&& !queMetaData.getNbsUiComponentUid().toString().equals("1017")) {    //not Participation 
						if (queMetaData.getMask() != null
									&& queMetaData.getMask().equalsIgnoreCase(
											NEDSSConstants.NBS_QUESTION_MASK_NUMERIC))
								questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC; //Numeric
						else if (queMetaData.getMask() != null
								&& queMetaData.getMask().equalsIgnoreCase(
										NEDSSConstants.NBS_QUESTION_MASK_NUMERIC_YEAR))
								questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC; //Numeric
						else
							    questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT; //Text
		} else if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)
				&& queMetaData.getNbsUiComponentUid().toString().equals("1009"))
						questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_TEXTAREA;
		else if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_DATE))
						questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_DATE;
		else if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
					if (queMetaData.getNbsUiComponentUid() != null
								&& (queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT) || 
										queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT_READONLY_SAVE))) {
								questionProp = "MULTISELECT," + "valueSet="	+ queMetaData.getCodeSetNm();
					} else if (queMetaData.getNbsUiComponentUid() != null
							&& !queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT)) { //coded - not MultiSelect
				questionProp = "SINGLESELECT," + "valueSet=" + queMetaData.getCodeSetNm();
			}
		} else if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
					if (queMetaData.getUnitTypeCd() != null
						&& queMetaData.getUnitTypeCd().equalsIgnoreCase("CODED")) {
						String returnedCode = "";
						Long codeSetGroupId = new Long(queMetaData.getUnitValue());
						if (codeSetGroupId != null) {
							SRTMapDAOImpl map = new SRTMapDAOImpl();
							returnedCode = map.getConceptCode(codeSetGroupId);
						}
						questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC + "codedUnit=" + returnedCode;
					} else if (queMetaData.getUnitTypeCd() != null
							&& queMetaData.getUnitTypeCd().equalsIgnoreCase("LITERAL")) {
						questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC + "unitValue=" + queMetaData.getUnitValue();
					} else if (queMetaData.getMask() != null) {
						String nMask = queMetaData.getMask();
						if (nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_STRUCTURE_NUMERIC)
								|| nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_DD)
								|| nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_EXT)
								|| nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_MM)
								|| nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_TEMP)
								|| nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_YYYY)) {
							questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC
									+ "," + "nMask=" + nMask;
						} else {
							questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC;
						}
					} else {
						questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC;
					}
		} else if (queMetaData.getNbsUiComponentUid() != null
				&& queMetaData.getNbsUiComponentUid().toString().equals("1017")) { //Participation
		    String subjectClassCd = "PSN"; //default to person
			if (queMetaData.getPartTypeCd() != null) {
				CachedDropDowns cdd = new CachedDropDowns();
				TreeMap<Object, Object> participationTypeCaseMap = cdd.getParticipationTypeList();
				ParticipationTypeVO parTypeVO = (ParticipationTypeVO) participationTypeCaseMap.get(queMetaData.getPartTypeCd()+NEDSSConstants.PART_CACHED_MAP_KEY_SEPARATOR+queMetaData.getQuestionIdentifier());
				if (parTypeVO != null)
					subjectClassCd = parTypeVO.getSubjectClassCd();
			}
			if (subjectClassCd.equalsIgnoreCase(NEDSSConstants.ORGANIZATION_CLASS_CODE))
				questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_PARTICIPATION_ORGANIZATION;
			else if (subjectClassCd.equalsIgnoreCase(NEDSSConstants.PLACE_CLASS_CODE))
				questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_PARTICIPATION_PLACE;
			else
				questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_PARTICIPATION_PERSON;
		}

		return questionProp;
	}

	public String getDWRQuestionProperties(String questionUid) throws Exception {
		WebContext ctx = WebContextFactory.get();
		String questionProp = "";
		Iterator<Object> iter = questionMap.keySet().iterator();
		NbsQuestionMetadata queMetaData = new NbsQuestionMetadata();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap
					.get(key);
			if (questionUid != null
					&& questionUid.equals(metaData.getQuestionIdentifier())) {
				queMetaData = metaData;
				break;
			}
		}
		if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)
				&& !queMetaData.getNbsUiComponentUid().toString().equals("1009")      //not TextArea
				&& !queMetaData.getNbsUiComponentUid().toString().equals("1017")) {    //not Participation 
						if (queMetaData.getMask() != null
									&& queMetaData.getMask().equalsIgnoreCase(
											NEDSSConstants.NBS_QUESTION_MASK_NUMERIC))
								questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC; //Numeric
						else if (queMetaData.getMask() != null
								&& queMetaData.getMask().equalsIgnoreCase(
										NEDSSConstants.NBS_QUESTION_MASK_NUMERIC_YEAR))
								questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC; //Numeric
						else
							    questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT; //Text
		} else if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)
				&& queMetaData.getNbsUiComponentUid().toString().equals("1009"))
						questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_TEXTAREA;
		else if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_DATE))
						questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_DATE;
		else if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
					if (queMetaData.getNbsUiComponentUid() != null
								&& (queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT) || 
										queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT_READONLY_SAVE))) {
								questionProp = "MULTISELECT," + "valueSet="	+ queMetaData.getCodeSetNm();
					} else if (queMetaData.getNbsUiComponentUid() != null
							&& !queMetaData.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT)) { //coded - not MultiSelect
				questionProp = "SINGLESELECT," + "valueSet=" + queMetaData.getCodeSetNm();
			}
		} else if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
					if (queMetaData.getUnitTypeCd() != null
						&& queMetaData.getUnitTypeCd().equalsIgnoreCase("CODED")) {
						String returnedCode = "";
						Long codeSetGroupId = new Long(queMetaData.getUnitValue());
						if (codeSetGroupId != null) {
							SRTMapDAOImpl map = new SRTMapDAOImpl();
							returnedCode = map.getConceptCode(codeSetGroupId);
						}
						questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC + "codedUnit=" + returnedCode;
					} else if (queMetaData.getUnitTypeCd() != null
							&& queMetaData.getUnitTypeCd().equalsIgnoreCase("LITERAL")) {
						questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC + "unitValue=" + queMetaData.getUnitValue();
					} else if (queMetaData.getMask() != null) {
						String nMask = queMetaData.getMask();
						if (nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_STRUCTURE_NUMERIC)
								|| nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_DD)
								|| nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_EXT)
								|| nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_MM)
								|| nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_TEMP)
								|| nMask.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_YYYY)) {
							questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC
									+ "," + "nMask=" + nMask;
						} else {
							questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC;
						}
					} else {
						questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC;
					}
		} else if (queMetaData.getNbsUiComponentUid() != null
				&& queMetaData.getNbsUiComponentUid().toString().equals("1017")) { //Participation
		    String subjectClassCd = "PSN"; //default to person
			if (queMetaData.getPartTypeCd() != null) {
				CachedDropDowns cdd = new CachedDropDowns();
				TreeMap<Object, Object> participationTypeCaseMap = cdd.getParticipationTypeList();
				ParticipationTypeVO parTypeVO = (ParticipationTypeVO) participationTypeCaseMap.get(queMetaData.getPartTypeCd()+NEDSSConstants.PART_CACHED_MAP_KEY_SEPARATOR+queMetaData.getQuestionIdentifier());
				if (parTypeVO != null)
					subjectClassCd = parTypeVO.getSubjectClassCd();
			}
			if (subjectClassCd.equalsIgnoreCase(NEDSSConstants.ORGANIZATION_CLASS_CODE))
				questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_PARTICIPATION_ORGANIZATION;
			else if (subjectClassCd.equalsIgnoreCase(NEDSSConstants.PLACE_CLASS_CODE))
				questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_PARTICIPATION_PLACE;
			else
				questionProp = NEDSSConstants.NBS_QUESTION_DATATYPE_PARTICIPATION_PERSON;
		}

		return questionProp;
	}
	
	
	

	public ArrayList<Object> getValueSetDropDown(String questionUid) {
		WebContext ctx = WebContextFactory.get();
		ArrayList<Object> aList = new ArrayList<Object>();
		Iterator<Object> iter = questionMap.keySet().iterator();
		NbsQuestionMetadata queMetaData = new NbsQuestionMetadata();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap
					.get(key);
			if (questionUid != null
					&& questionUid.equals(metaData.getQuestionIdentifier())) {
				queMetaData = metaData;
				break;
			}
		}
		if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
			if (queMetaData.getNbsUiComponentUid() != null
					&& queMetaData.getNbsUiComponentUid().toString()
							.equals(NEDSSConstants.MULTISELECT_COMPONENT)) {
				aList = CachedDropDowns.getCodedValueForType(queMetaData
						.getCodeSetNm());
			} else if (queMetaData.getNbsUiComponentUid() != null
					&& !queMetaData.getNbsUiComponentUid().toString()
							.equals(NEDSSConstants.MULTISELECT_COMPONENT)) {
				aList = CachedDropDowns.getCodedValueForType(queMetaData
						.getCodeSetNm());
			}
		} else if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)
				&& queMetaData.getUnitTypeCd() != null
				&& queMetaData.getUnitTypeCd().equalsIgnoreCase("CODED")) {
			aList = CachedDropDowns.getCodedValueForType(queMetaData
					.getUnitTypeCd());
		}
		return aList;
	}
	
	public ArrayList<Object> getAdvInvValueSetDropDown(String questionUid) {
		WebContext ctx = WebContextFactory.get();
		ArrayList<Object> aList = new ArrayList<Object>();
		Iterator<Object> iter = advInvQuestionMap.keySet().iterator();
		NbsQuestionMetadata queMetaData = new NbsQuestionMetadata();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			NbsQuestionMetadata metaData = (NbsQuestionMetadata) advInvQuestionMap
					.get(key);
			if (questionUid != null
					&& questionUid.equals(metaData.getQuestionIdentifier())) {
				queMetaData = metaData;
				break;
			}
		}
		if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
			if (queMetaData.getNbsUiComponentUid() != null
					&& queMetaData.getNbsUiComponentUid().toString()
							.equals(NEDSSConstants.MULTISELECT_COMPONENT)) {
				aList = CachedDropDowns.getCodedValueForType(queMetaData
						.getCodeSetNm());
			} else if (queMetaData.getNbsUiComponentUid() != null
					&& !queMetaData.getNbsUiComponentUid().toString()
							.equals(NEDSSConstants.MULTISELECT_COMPONENT)) {
				aList = CachedDropDowns.getCodedValueForType(queMetaData
						.getCodeSetNm());
			}
		} else if (queMetaData.getDataType() != null
				&& queMetaData.getDataType().equalsIgnoreCase(
						NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)
				&& queMetaData.getUnitTypeCd() != null
				&& queMetaData.getUnitTypeCd().equalsIgnoreCase("CODED")) {
			aList = CachedDropDowns.getCodedValueForType(queMetaData
					.getUnitTypeCd());
		}
		return aList;
	}


	public ArrayList<Object> getDWRValueList(String valueSet) {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		ArrayList<Object> valueList = new ArrayList<Object>();
		valueList = QuestionDropDownCreator.getInstance()
				.getQuestionDropDownList(valueSet);
		;

		setDwrValue(valueList);
		req.setAttribute("valueList1", valueList);
		return valueList;

	}

	public ArrayList<Object> getDWRAdvancedValueList(String valueSet) {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		ArrayList<Object> valueList = new ArrayList<Object>();
		valueList = CachedDropDowns.getCodedValueForType(valueSet);
		setDwrValue(valueList);
		req.setAttribute("valueAdvList", valueList);
		return valueList;

	}
	public ArrayList<Object> getDWRAdvancedInvValueList(String valueSet) {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		ArrayList<Object> valueList = new ArrayList<Object>();
		valueList = CachedDropDowns.getCodedValueForType(valueSet);
		setAdvInvDwrValue(valueList);
		req.setAttribute("valueAdvInvList", valueList);
		return valueList;

	}

	public void setBatchAnswer(BatchEntry batchEntry) {
		// determine what sequence number this item should be
		Iterator<BatchEntry> it1 = this.idBatchEntryList.iterator();
		int highSeq = 0;
		while (it1.hasNext()) {
			BatchEntry ansItem = (BatchEntry) it1.next();
			if (highSeq < ansItem.getId()) {
				highSeq = ansItem.getId();
			}
		}

		if (highSeq != 0) {
			batchEntry.setId(highSeq + 1);
		} else {
			batchEntry.setId(getNextSeqId());
		}
		idBatchEntryList.add(batchEntry);

	}

	public void setAdvancedCriteriaBatchAnswer(BatchEntry batchEntry) {
		// determine what sequence number this item should be
		Iterator<BatchEntry> it1 = this.advancedCriteriaBatchEntryList
				.iterator();
		int highSeq = 0;
		while (it1.hasNext()) {
			BatchEntry ansItem = (BatchEntry) it1.next();
			if (highSeq < ansItem.getId()) {
				highSeq = ansItem.getId();
			}
		}

		if (highSeq != 0) {
			batchEntry.setId(highSeq + 1);
		} else {
			batchEntry.setId(getNextAdvSeqId());
		}
		advancedCriteriaBatchEntryList.add(batchEntry);

	}
	
	public void setAdvancedInvCriteriaBatchAnswer(BatchEntry batchEntry) {
		// determine what sequence number this item should be
		Iterator<BatchEntry> it1 = this.advancedInvCriteriaBatchEntryList
				.iterator();
		int highSeq = 0;
		while (it1.hasNext()) {
			BatchEntry ansItem = (BatchEntry) it1.next();
			if (highSeq < ansItem.getId()) {
				highSeq = ansItem.getId();
			}
		}

		if (highSeq != 0) {
			batchEntry.setId(highSeq + 1);
		} else {
			batchEntry.setId(getNextAdvSeqId());
		}
		advancedInvCriteriaBatchEntryList.add(batchEntry);

	}

	public void setElrAdvancedCriteriaBatchAnswer(BatchEntry batchEntry) {
		setAdvancedCriteriaBatchAnswer(batchEntry);
	}

	public ArrayList<Object> getActionDropdown(String valueSet) {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		ArrayList<Object> valueList = new ArrayList<Object>();
		valueList = CachedDropDowns.getCodedValueForType(valueSet);
		setDwrAction(valueList);
		req.setAttribute("ActionList", valueList);
		return valueList;
	}

	public ArrayList<Object> getEventActionDropdown(String valueSet,
			String evenTypeCode) {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		ArrayList<Object> valueList = new ArrayList<Object>();
		valueList = CachedDropDowns.getCodedValueForType(valueSet);
		ArrayList<Object> eventActionList = new ArrayList<Object>();

		if (valueList != null) {
			Iterator iterator = valueList.iterator();
			while (iterator.hasNext()) {
				DropDownCodeDT dt = (DropDownCodeDT) iterator.next();
				if (NEDSSConstants.PHC_236.equals(evenTypeCode)) {
					if (!"3".equals(dt.getKey()))
						eventActionList.add(dt);
				} else if ("11648804".equals(evenTypeCode))
					eventActionList.add(dt);
			}
		}
		setDwrAction(eventActionList);
		req.setAttribute("ActionList", eventActionList);
		return eventActionList;
	}

	public String[] getMultiselectValues(String fieldname) {
		WebContext ctx = WebContextFactory.get();
		String[] multiselectList = null;
		if (fieldname != null
				&& fieldname.equals(DecisionSupportConstants.CONDITIONS))
			multiselectList = getDecisionSupportClientVO().getAnswerArray(
					fieldname);
		else if (fieldname != null
				&& fieldname.equals(DecisionSupportConstants.PUBLISHED_CONDITION))
			multiselectList = getDecisionSupportClientVO().getAnswerArray(
					fieldname);
		else if (fieldname != null
				&& fieldname.equals(DecisionSupportConstants.VALUE)) {
			boolean readFromFormflag = true;
			ArrayList<BatchEntry> batchList = getIdBatchEntryList();
			if (batchList == null || batchList.size() == 0) {
				batchList = getDecisionSupportClientVO().getBatchEntryList();
				readFromFormflag = false;
			}
			Iterator<BatchEntry> it1 = batchList.iterator();
			while (it1.hasNext()) {
				BatchEntry listItem = (BatchEntry) it1.next();
				Object obj = null;
				if (readFromFormflag) {
					String multSelectString = (String) listItem.getAnswerMap()
							.get("valueList2");
					if (multSelectString != null
							&& !multSelectString.equals(""))
						obj = tokenStringToArray(multSelectString);

				}
				if (obj == null) {
					obj = listItem.getDsmAnswerMap().get(
							DecisionSupportConstants.VALUE);
				}
				if (obj instanceof java.lang.String[])
					multiselectList = (String[]) obj;
			}
		} else if (fieldname != null
				&& fieldname.equals(DecisionSupportConstants.CRITERIA_VALUE)) {
			boolean readFromFormflag = true;
			ArrayList<BatchEntry> batchList = getAdvancedCriteriaBatchEntryList();
			if (batchList == null || batchList.size() == 0) {
				batchList = getDecisionSupportClientVO()
						.getAdvancedCriteriaBatchEntryList();
				readFromFormflag = false;
			}
			Iterator<BatchEntry> it1 = batchList.iterator();
			while (it1.hasNext()) {
				BatchEntry listItem = (BatchEntry) it1.next();
				Object obj = null;
				if (readFromFormflag) {
					String multSelectString = (String) listItem.getAnswerMap()
							.get("AdvValueList2");
					if (multSelectString != null
							&& !multSelectString.equals(""))
						obj = tokenStringToArray(multSelectString);

				}
				if (obj == null) {
					obj = listItem.getDsmAnswerMap().get(
							DecisionSupportConstants.CRITERIA_VALUE);
				}
				if (obj instanceof java.lang.String[])
					multiselectList = (String[]) obj;
			}
		}
		
		else if (fieldname != null
				&& fieldname.equals(DecisionSupportConstants.ADV_INV_CRITERIA_VALUE)) {
			boolean readFromFormflag = true;
			ArrayList<BatchEntry> batchList = getAdvancedInvCriteriaBatchEntryList();
			if (batchList == null || batchList.size() == 0) {
				batchList = getDecisionSupportClientVO()
						.getAdvancedInvCriteriaBatchEntryList();
				readFromFormflag = false;
			}
			Iterator<BatchEntry> it1 = batchList.iterator();
			while (it1.hasNext()) {
				BatchEntry listItem = (BatchEntry) it1.next();
				Object obj = null;
				if (readFromFormflag) {
					String multSelectString = (String) listItem.getAnswerMap()
							.get("AdvInvValueList2");
					if (multSelectString != null
							&& !multSelectString.equals(""))
						obj = tokenStringToArray(multSelectString);

				}
				if (obj == null) {
					obj = listItem.getDsmAnswerMap().get(
							DecisionSupportConstants.ADV_INV_CRITERIA_VALUE);
				}
				if (obj instanceof java.lang.String[])
					multiselectList = (String[]) obj;
			}
		}
		return multiselectList;
	}

	private Object tokenStringToArray(String multSelectString) {
		StringTokenizer st = new StringTokenizer(multSelectString, "^^");
		int arraySize = st.countTokens();
		String[] stringArray = new String[arraySize];
		int i = 0;
		while (st.hasMoreTokens()) {
			stringArray[i] = st.nextToken();
			i++;
		}

		return stringArray;
	}

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}

	public ArrayList<Object> getCodedResultList() {
		codedResultList = new CachedDropDownValues().getCodedResultValueList();

		DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		dropDownCodeDT.setKey("");
		dropDownCodeDT.setValue("");
		codedResultList.add(0, dropDownCodeDT);

		return codedResultList;
	}

	public void setCodedResultList(ArrayList<Object> codedResultList) {
		this.codedResultList = codedResultList;
	}

	public ArrayList<Object> getResultTypeList() {
		DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		dropDownCodeDT.setKey("");
		dropDownCodeDT.setValue("");

		resultTypeList = new CachedDropDownValues()
				.getCodedValuesList("UNIT_ISO");
		resultTypeList.add(0, dropDownCodeDT);

		return resultTypeList;
	}

	public void setResultTypeList(ArrayList<Object> resultTypeList) {
		this.resultTypeList = resultTypeList;
	}

	public String getDwrPopulateResultedTestByUid(String description,
			String testCode) {
		String descriptionWithCode=description+" ("+testCode+")";
		getAttributeMap().put("ResultedTestDescriptionWithCode", descriptionWithCode);
		getAttributeMap().put("ResultDescription", description);
		getAttributeMap().put("ResultCode", testCode);
		return descriptionWithCode;
	}

	public String getDwrPopulateCodeResultByUid(String description,
			String testCode) {
		String descriptionWithCode=description+" ("+testCode+")";
		getAttributeMap().put("TestDescriptionWithCode", descriptionWithCode);
		getAttributeMap().put("TestDescription", description);
		getAttributeMap().put("TestCode", testCode);
		return descriptionWithCode;
	}

	public void setAnswer(String key, String answer) {
		if (decisionSupportClientVO != null) {
			decisionSupportClientVO.setAnswer(key, answer);
		}
	}
	
	public ArrayList<Object> getNumericResultList() {
		return CachedDropDowns.getCodedValueForType("SEARCH_NUMERIC");
	}

	public void setNumericResultList(String numericResultList) {
		this.numericResultList = numericResultList;
	}
	
	public ArrayList<Object> getTextResultList() {
		ArrayList<Object> values = CachedDropDowns.getCodedValueForType("SEARCH_TEXT");
		
		boolean found=false;
		
		for(Object dropdown : values){
			
			if(((DropDownCodeDT)dropdown).getKey().equalsIgnoreCase("")){
				found=true;
				break;
			}
		}

		if(!found){
			 DropDownCodeDT dDownDT = null;
	         dDownDT = new DropDownCodeDT();
	         dDownDT.setKey("");
	         dDownDT.setValue("");
	         values.add(0,dDownDT);
		}
		
		return values;
	}

	public void setTextResultList(String textResultList) {
		this.textResultList = textResultList;
	}

	
}
