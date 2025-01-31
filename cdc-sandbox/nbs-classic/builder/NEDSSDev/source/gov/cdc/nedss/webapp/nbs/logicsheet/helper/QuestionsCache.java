package gov.cdc.nedss.webapp.nbs.logicsheet.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.dt.NBSDocumentMetadataDT;
import gov.cdc.nedss.systemservice.dt.PrePopMappingDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.bean.QuestionMap;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.bean.QuestionMapHome;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.AggregateSummaryDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.RulesDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.SourceFieldDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import javax.rmi.PortableRemoteObject;

public class QuestionsCache {
	public static final LogUtils logger = new LogUtils(QuestionsCache.class
			.getName());

	private static QuestionMap qMap = null;

	public static Map<Object,Object> map = new TreeMap<Object, Object>();
	public static Map<Object,Object> dmbMap = new TreeMap<Object, Object>();
	public static Map<Object,Object> contactMap = new TreeMap<Object, Object>();
	public static Map<Object,Object> rMap = new TreeMap<Object, Object>();
	public static Map<Object,Object> formRuleMap = new TreeMap<Object, Object>();
	public static Collection<Object>  summaryColl = new ArrayList<Object> ();
	public static Map<Object,Object> docMap = new TreeMap<Object, Object>();
	public static Map<Object,Object> docMapBySchemaLocation = new TreeMap<Object, Object>();
	public static Map<Object,Object> contactFormRuleMap = new TreeMap<Object, Object>();
	public static Map<Object,Object> contactRMap = new TreeMap<Object, Object>();
	public static Map<Object,Object> fromPrePopFormMapping = new TreeMap<Object, Object>();
	public static Map<Object,Object> toPrePopFormMapping = new TreeMap<Object, Object>();
	public static QuestionMap getQuestionMapEJBRef() throws Exception {
		if (qMap == null) {
			NedssUtils nu = new NedssUtils();
			Object objref = nu.lookupBean(JNDINames.QUESTION_MAP_EJB);
			if (objref != null) {
				QuestionMapHome home = (QuestionMapHome) PortableRemoteObject
				.narrow(objref, QuestionMapHome.class);
				qMap = home.create();
			}
		}
		return qMap;
	}
    public static TreeMap<Object,Object>  getQuestionMap() {
    	TreeMap<Object,Object> questionMap = null;	
    	
    	if (map != null && map.size() > 0) {
    	    return (TreeMap<Object,Object>) map;

		}
		
		try {
			if (getQuestionMapEJBRef() != null){
			Collection<Object>  qColl = 	getQuestionMapEJBRef().getPamQuestions();
			questionMap = createQuestionMap(qColl);
			}
		} catch (Exception e) {
			e.printStackTrace();	
			}	
		
		map.putAll(questionMap);		
		return questionMap;
	}
    
    public static TreeMap<Object,Object>  getDMBQuestionMap() {    	
    	TreeMap<Object,Object> dmbQuestionMap = null; 
    	if (dmbMap != null && dmbMap.size() > 0) {
    	    return (TreeMap<Object,Object>) dmbMap;

		}	
		try {
			if (getQuestionMapEJBRef() != null){
			Collection<Object>  qColl = 	getQuestionMapEJBRef().getDMBQuestions();
			dmbQuestionMap = createDMBQuestionMap(qColl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		dmbMap.putAll(dmbQuestionMap);
		return dmbQuestionMap;
	}
    
    public static TreeMap<Object,Object>  getDMBQuestionMapAfterPublish() {    	
    	TreeMap<Object,Object> dmbQuestionMap = null; 
    	
		try {
			if (getQuestionMapEJBRef() != null){
			Collection<Object>  qColl = 	getQuestionMapEJBRef().getDMBQuestions();
			dmbQuestionMap = createDMBQuestionMap(qColl);
			}
		} catch (Exception e) {
			logger.error("Exception in getDMBQuestionMapAfterPublish() " + e.getMessage());
			e.printStackTrace();
		}	
		if(dmbQuestionMap != null)
			dmbMap.putAll(dmbQuestionMap);
		return dmbQuestionMap;
	}

	private static TreeMap<Object,Object> createQuestionMap(Collection<Object>  coll) {
    	TreeMap<Object, Object> qCodeMap = new TreeMap<Object, Object>();
    	TreeMap<Object,Object> qInvFormRVCTMap = new TreeMap<Object,Object>();
    	TreeMap<Object,Object> qInvFormVarMap = new TreeMap<Object,Object>();
    	TreeMap<Object,Object> qInvFormFluMap = new TreeMap<Object,Object>();
    	TreeMap<Object,Object> homePageLDFMap = new TreeMap<Object,Object>();   	
    	
    	//For Demo Purpose CHOLERA Metadata
    	TreeMap<Object,Object> qInvFormChlrMap = new TreeMap<Object,Object>();
    	
    	if (coll != null && coll.size() > 0) {    		
    		Iterator ite = coll.iterator();
    		while (ite.hasNext()) {		
    			NbsQuestionMetadata qMetadata = (NbsQuestionMetadata) ite.next();    			
    			String dataType = qMetadata.getDataType();
    			ArrayList<Object> aList = new ArrayList<Object>();
    			if(dataType != null && dataType.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)){
    				aList = CachedDropDowns.getCodedValueForType(qMetadata.getCodeSetNm());
    				qMetadata.setAList(aList);
    			}
    			if (qMetadata.getInvestigationFormCd() != null && qMetadata.getInvestigationFormCd().trim().equals(NBSConstantUtil.INV_FORM_RVCT)) {
    				String questionId = qMetadata.getQuestionIdentifier() == null ? "" : qMetadata.getQuestionIdentifier();
    				String ldfPageId = qMetadata.getLdfPageId() == null ? "" : qMetadata.getLdfPageId();
    				String uiMetadataUid = qMetadata.getNbsUiMetadataUid() == null ? "" : qMetadata.getNbsUiMetadataUid().toString();
    				if(questionId.equals("")) {
        				//define a questionId for a new Tab added
        				if(!ldfPageId.equals("")) {
        					qMetadata.setQuestionIdentifier("LDFTAB");
        					qInvFormRVCTMap.put("LDFTAB", qMetadata);
        				} else {
        					qInvFormRVCTMap.put(uiMetadataUid, qMetadata);
        				}    					
    				} else {
        				qInvFormRVCTMap.put(questionId ,qMetadata);    					
    				}
    			}
    			else if(qMetadata.getInvestigationFormCd() != null && qMetadata.getInvestigationFormCd().trim().equals(NBSConstantUtil.INV_FORM_VAR))
    			{
    				String questionId = qMetadata.getQuestionIdentifier() == null ? "" : qMetadata.getQuestionIdentifier();
    				String ldfPageId = qMetadata.getLdfPageId() == null ? "" : qMetadata.getLdfPageId();
    				String uiMetadataUid = qMetadata.getNbsUiMetadataUid() == null ? "" : qMetadata.getNbsUiMetadataUid().toString();
    				if(questionId.equals("")) {
        				//define a questionId for a new Tab added
        				if(!ldfPageId.equals("")) {
        					qMetadata.setQuestionIdentifier("LDFTAB");
        					qInvFormVarMap.put("LDFTAB", qMetadata);
        				} else {
        					qInvFormVarMap.put(uiMetadataUid, qMetadata);
        				}    					
    				} else {
    					qInvFormVarMap.put(questionId ,qMetadata);    					
    				}
    			}
    			//For Demo, Cholera
    			else if(qMetadata.getInvestigationFormCd() != null && qMetadata.getInvestigationFormCd().trim().equals("INV_FORM_CHLR"))
    			{
    				String questionId = qMetadata.getQuestionIdentifier() == null ? "" : qMetadata.getQuestionIdentifier();
    				String ldfPageId = qMetadata.getLdfPageId() == null ? "" : qMetadata.getLdfPageId();
    				String uiMetadataUid = qMetadata.getNbsUiMetadataUid() == null ? "" : qMetadata.getNbsUiMetadataUid().toString();
    				if(!questionId.equals("")) 
    					qInvFormChlrMap.put(questionId ,qMetadata);
    			}
    			
    			//Flu H1N1
    			else if(qMetadata.getInvestigationFormCd() != null && qMetadata.getInvestigationFormCd().trim().equals(NBSConstantUtil.INV_FORM_FLU))
    			{
    				String questionId = qMetadata.getQuestionIdentifier() == null ? "" : qMetadata.getQuestionIdentifier();   					   
   					//Check if nbsTableUid is not empty, put  SummaryInfo Collection(retrieved from nbsTableUid) in the Map
   					if(qMetadata.getNbsTableUid() != null && qMetadata.getNbsTableUid().longValue() > 0) {
   						retrieveSummaryCollByTableUid(qMetadata, qInvFormFluMap, qMetadata.getNbsTableUid());
   					} else {
   						qInvFormFluMap.put(questionId ,qMetadata);
   					}
    			}
    			//HomePage LDFs
    			else if(qMetadata.getInvestigationFormCd() != null && qMetadata.getInvestigationFormCd().trim().equals(NEDSSConstants.HOME_PAGE_LDF))
    			{
    				String uiMetadataUid = qMetadata.getNbsUiMetadataUid() == null ? "" : qMetadata.getNbsUiMetadataUid().toString();
    				homePageLDFMap.put(uiMetadataUid, qMetadata);
    			}  			  			

    		}
    		qCodeMap.put(NBSConstantUtil.INV_FORM_RVCT, qInvFormRVCTMap);
    		qCodeMap.put(NBSConstantUtil.INV_FORM_VAR, qInvFormVarMap);
    		qCodeMap.put(NBSConstantUtil.INV_FORM_FLU, qInvFormFluMap);
    		qCodeMap.put(NEDSSConstants.HOME_PAGE_LDF, homePageLDFMap);
    		//For R4.0 demo purpose...
    		qCodeMap.put("INV_FORM_CHLR", qInvFormChlrMap);
    	}
    	return qCodeMap;
    }

	/**
	 * Method to load SummaryData by TableUid appropriately
	 * @param qMetadata
	 * @param qInvFormFluMap
	 * @param tableUid
	 */
	private static void retrieveSummaryCollByTableUid(NbsQuestionMetadata qMetadata, Map<Object,Object> qInvFormFluMap, Long tableUid) {
		Iterator<Object>  iter = summaryColl.iterator();
		ArrayList<Object> summList = new ArrayList<Object> ();
		String qId = null;
		while(iter.hasNext()) {
			AggregateSummaryDT dt = (AggregateSummaryDT) iter.next();
			if(dt.getNbsTableUid().compareTo(tableUid) == 0) {
				summList.add(dt);
				if(qId == null) {
					qId = dt.getQuestionIdentifier() + "_" + dt.getNbsTableUid();
					//Since QuestionUid in NBS_Table_Metadata represents a SUMXXX (that dont have an entry in NBS_UI_Metadata and not loaded in questionCache), populated that Uid for storage ease
					qMetadata.setNbsQuestionUid(dt.getNbsQuestionUid());
					//SUMXXX also needs to be put in the METADATA to retrieve any UI specific attributes (in future) 
					qInvFormFluMap.put(dt.getQuestionIdentifier(), qMetadata);				
				}
			}
		}
		if(qId != null) 
			qInvFormFluMap.put(qId, summList);
	}
	
	
	 /*
     * Get the rules Collection<Object>  and create a Map<Object,Object> out of it
     */
    public static TreeMap<Object,Object> getRuleMap() {

        TreeMap<Object,Object> ruleMap = null;
        if(rMap != null && rMap.size()>0){
        	return (TreeMap<Object,Object>)rMap;
        }
        try {
			if (getQuestionMapEJBRef() != null){
			Collection<Object>  ruleColl = 	getQuestionMapEJBRef().getRuleCollection();
			ruleMap = createRuleMap(ruleColl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rMap.putAll(ruleMap);
		return ruleMap;
	}

     /*
	 * Create the Map<Object,Object> with key as QuestionIdentifier and value as the collection
	 * of Rules related to the source
	 */
	private static TreeMap<Object,Object> createRuleMap(Collection<Object>  ruleColl) {

		Collection<Object>  fieldRuleColl = filterRuleCollection(ruleColl,NEDSSConstants.ON_CHANGE);
		TreeMap<Object,Object> formRuleMap = new TreeMap<Object,Object>();
		TreeMap<Object,Object> ruleMapRVCT = new TreeMap<Object,Object>();
		TreeMap<Object,Object> ruleMapVAR = new TreeMap<Object,Object>();
		TreeMap<Object,Object> ruleMapCT = new TreeMap<Object,Object>();
		if (fieldRuleColl != null && fieldRuleColl.size() > 0) {
			Iterator<Object>  ruleIter = fieldRuleColl.iterator();
			while (ruleIter.hasNext()) {
				RulesDT rulesDT = (RulesDT) ruleIter.next();
				if(rulesDT.getFormCode() != null && rulesDT.getFormCode().equals(NBSConstantUtil.INV_FORM_RVCT)){
					if (ruleMapRVCT.containsKey(rulesDT.getQuestionIdentifer())) {
						((ArrayList<Object> ) ruleMapRVCT.get(rulesDT.getQuestionIdentifer()))
								.add(rulesDT);
					} else {
						ArrayList<Object> rules = new ArrayList<Object> ();
						rules.add(rulesDT);
						ruleMapRVCT.put(rulesDT.getQuestionIdentifer(), rules);
					}
				}
				else if(rulesDT.getFormCode() != null && rulesDT.getFormCode().equals(NBSConstantUtil.INV_FORM_VAR)){
					if (ruleMapVAR.containsKey(rulesDT.getQuestionIdentifer())) {
						((ArrayList<Object> ) ruleMapVAR.get(rulesDT.getQuestionIdentifer()))
								.add(rulesDT);
					} else {
						ArrayList<Object> rules = new ArrayList<Object> ();
						rules.add(rulesDT);
						ruleMapVAR.put(rulesDT.getQuestionIdentifer(), rules);
					}
				}else if(rulesDT.getFormCode() != null && rulesDT.getFormCode().equals(NBSConstantUtil.INV_FORM_CONTACT)){
					if (ruleMapCT.containsKey(rulesDT.getQuestionIdentifer())) {
						((ArrayList<Object> ) ruleMapCT.get(rulesDT.getQuestionIdentifer()))
								.add(rulesDT);
					} else {
						ArrayList<Object> rules = new ArrayList<Object> ();
						rules.add(rulesDT);
						ruleMapCT.put(rulesDT.getQuestionIdentifer(), rules);
					}
				}
				
				
					
					//((ArrayList<Object> )((TreeMap)formRuleMap.get(rulesDT.getFormCode())).get(rulesDT.getQuestionIdentifer())).add(rulesDT);
				
			}
			formRuleMap.put(NBSConstantUtil.INV_FORM_RVCT,ruleMapRVCT);
			formRuleMap.put(NBSConstantUtil.INV_FORM_VAR,ruleMapVAR);
			formRuleMap.put(NBSConstantUtil.INV_FORM_CONTACT,ruleMapCT);
			
		}

		return formRuleMap;
	}
	   /*
	    * Get the formRuleMap
	    */
	   public static TreeMap<Object,Object> getFormRuleMap(){
	    	TreeMap<Object,Object> ruleMap = null;
	        if(formRuleMap != null && formRuleMap.size()>0){
	        	return (TreeMap<Object,Object>)formRuleMap;
	        }
	        try {
				if (getQuestionMapEJBRef() != null){
				Collection<Object>  ruleColl = 	getQuestionMapEJBRef().getRuleCollection();
				ruleMap = createFormRuleMap(ruleColl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			formRuleMap.putAll(ruleMap);
			return ruleMap;
		}
	/*
	 * Get the RuleMap for the form
	 */
	private static TreeMap<Object,Object> createFormRuleMap(Collection<Object>  ruleColl) {

		Collection<Object>  formRuleColl = filterRuleCollection(ruleColl,NEDSSConstants.ON_SUBMIT);
		formRuleColl.addAll(filterRuleCollection(ruleColl,NEDSSConstants.ON_CREATE_SUBMIT));
		TreeMap<Object,Object> formRuleMap = new TreeMap<Object,Object>();
		if (formRuleColl != null && formRuleColl.size() > 0) {
			Iterator<Object>  ruleIter = formRuleColl.iterator();
			while (ruleIter.hasNext()) {

				RulesDT rulesDT = (RulesDT) ruleIter.next();
				if(rulesDT != null && rulesDT.getFormCode() != null){
					if (formRuleMap.containsKey(rulesDT.getFormCode())) {
						((ArrayList<Object> ) formRuleMap.get(rulesDT.getFormCode()))
								.add(rulesDT);
					} else {
						ArrayList<Object> rules = new ArrayList<Object> ();
						rules.add(rulesDT);
						formRuleMap.put(rulesDT.getFormCode(), rules);
					}
				}
			}
		}
		return formRuleMap;
	}
	
	   /*
	    * Get the formRuleMap
	    */
	   public static TreeMap<Object,Object> getContactFormRuleMap(){
	    	TreeMap<Object,Object> contRuleMap = null;
	        if(contactFormRuleMap != null && contactFormRuleMap.size()>0){
	        	return (TreeMap<Object,Object>)contactFormRuleMap;
	        }
	        try {
				if (getQuestionMapEJBRef() != null){
				Collection<Object>  ruleColl = 	getQuestionMapEJBRef().getRuleCollection();
				contRuleMap = createContactFormRuleMap(ruleColl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			contactFormRuleMap.putAll(contRuleMap);
			return contRuleMap;
		}
	
	/*
	 * Get the RuleMap for the Contact form
	 */
	private static TreeMap<Object,Object> createContactFormRuleMap(Collection<Object>  ruleColl) {

		Collection<Object>  formRuleColl = filterRuleCollection(ruleColl,NEDSSConstants.ON_SUBMIT);
		formRuleColl.addAll(filterRuleCollection(ruleColl,NEDSSConstants.ON_CREATE_SUBMIT));
		TreeMap<Object,Object> contactFormRuleMap = new TreeMap<Object,Object>();
		if (formRuleColl != null && formRuleColl.size() > 0) {
			Iterator<Object>  ruleIter = formRuleColl.iterator();
			while (ruleIter.hasNext()) {

				RulesDT rulesDT = (RulesDT) ruleIter.next();
				if(rulesDT != null && rulesDT.getFormCode() != null){
					if(rulesDT.getFormCode().equals(NEDSSConstants.CONTACT_FORMCODE))
					if (contactFormRuleMap.containsKey(rulesDT.getFormCode())) {
						((ArrayList<Object> ) contactFormRuleMap.get(rulesDT.getFormCode()))
								.add(rulesDT);
					} 
				}
			}
		}
		return contactFormRuleMap;
	}
	
	

	 /*
    * Get the rules Collection<Object>  and create a Map<Object,Object> out of it
    */
   public static TreeMap<Object,Object> getContactRuleMap() {

       TreeMap<Object,Object> ruleMap = null;
       if(contactRMap != null && contactRMap.size()>0){
       	return (TreeMap<Object,Object>)contactRMap;
       }
       try {
			if (getQuestionMapEJBRef() != null){
			Collection<Object>  ruleColl = 	getQuestionMapEJBRef().getRuleCollection();
			ruleMap = createContactRuleMap(ruleColl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		contactRMap.putAll(ruleMap);
		return ruleMap;
	}

    /*
	 * Create the Map<Object,Object> with key as QuestionIdentifier and value as the collection
	 * of Rules related to the source
	 */
	private static TreeMap<Object,Object> createContactRuleMap(Collection<Object>  ruleColl) {

		Collection<Object>  fieldRuleColl = filterRuleCollection(ruleColl,NEDSSConstants.ON_CHANGE);
		TreeMap<Object,Object> formRuleMap = new TreeMap<Object,Object>();
		TreeMap<Object,Object> ruleMapContact = new TreeMap<Object,Object>();
		if (fieldRuleColl != null && fieldRuleColl.size() > 0) {
			Iterator<Object>  ruleIter = fieldRuleColl.iterator();
			while (ruleIter.hasNext()) {
				RulesDT rulesDT = (RulesDT) ruleIter.next();
				if(rulesDT.getFormCode() != null && rulesDT.getFormCode().equals(NBSConstantUtil.INV_FORM_CONTACT)){
					if (ruleMapContact.containsKey(rulesDT.getQuestionIdentifer())) {
						((ArrayList<Object> ) ruleMapContact.get(rulesDT.getQuestionIdentifer()))
								.add(rulesDT);
					} else {
						ArrayList<Object> rules = new ArrayList<Object> ();
						rules.add(rulesDT);
						ruleMapContact.put(rulesDT.getQuestionIdentifer(), rules);
					}
				}
				
				}
		
				
					
					//((ArrayList<Object> )((TreeMap)formRuleMap.get(rulesDT.getFormCode())).get(rulesDT.getQuestionIdentifer())).add(rulesDT);
				
		
			formRuleMap.put(NBSConstantUtil.INV_FORM_CONTACT,ruleMapContact);
					
		}

		return formRuleMap;
	}

	private static Collection<Object>  filterRuleCollection(Collection<Object>  ruleColl, String ConseqCode){
		Collection<Object>  formRuleColl = new ArrayList<Object> ();
		if (ruleColl != null && ruleColl.size() > 0) {
			Iterator<Object>  rIter = ruleColl.iterator();
			while(rIter.hasNext()){
				RulesDT rulesDT = (RulesDT)rIter.next();
				if(rulesDT.getConseqIndCode().equals(ConseqCode)){
					formRuleColl.add(rulesDT);
				}
			}
		}
	  return formRuleColl;
	}
	/**
	 * getSummaryCollection  returns AggregateSummaryCollection<AggregateSummaryDTs>
	 * @return
	 */
	public static Collection<Object>  getSummaryCollection(){
	        if(summaryColl != null && summaryColl.size()>0){
	        	return summaryColl;
	        }
	        try {
				if (getQuestionMapEJBRef() != null){
					summaryColl =  getQuestionMapEJBRef().retrieveAggregateData();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return summaryColl;
		}
	
	   public static TreeMap<Object,Object> getNBSDocMetadataMap() {
	        TreeMap<Object,Object> nbsDocMetadataMap = null;
			//if (docMap != null && docMap.size() > 0) {
			//	return (TreeMap<Object,Object>) docMap;
			//}

			try {
				if (getQuestionMapEJBRef() != null){
				Collection<Object>  docMetaColl = 	getQuestionMapEJBRef().retrieveNBSDocumentMetadata();
				nbsDocMetadataMap = createDocMetaDataMap(docMetaColl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//try{
			//	docMap.putAll(nbsDocMetadataMap);
			
			//}catch (Exception ex){
			//	ex.printStackTrace();
			//	ex.getMessage();
			//}
			return nbsDocMetadataMap;
		}
	   
	   private static TreeMap<Object,Object> createDocMetaDataMap(Collection<Object>  coll) {
		   TreeMap<Object,Object> nbsDocMetaMap = new TreeMap<Object,Object>();
		   if(coll != null){
			  Iterator<Object>  iter = coll.iterator();
			   while(iter.hasNext()){
				   NBSDocumentMetadataDT nbsDocMDT = (NBSDocumentMetadataDT)iter.next();
				   nbsDocMetaMap.put(nbsDocMDT.getNbsDocumentMetadataUid(), nbsDocMDT);
				   
			   }
		   }
		   return nbsDocMetaMap;
	   }
	   
	   public static Map<Object,Object>  getNBSDocMetadataMapBySchemaLocation() {
			if (docMapBySchemaLocation != null && docMapBySchemaLocation.size() > 0) {
				return (TreeMap<Object,Object>) docMapBySchemaLocation;
			}
			try {
				if (getQuestionMapEJBRef() != null){
				Collection<Object>  docMetaColl = 	getQuestionMapEJBRef().retrieveNBSDocumentMetadata();
				docMapBySchemaLocation = createDocMetaDataMapBySchemaLocation(docMetaColl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return docMapBySchemaLocation;
		}
	   
	   private static TreeMap<Object,Object> createDocMetaDataMapBySchemaLocation(Collection<Object>  coll) {
		   TreeMap<Object,Object> nbsDocMetaMapBySchemaLocation = new TreeMap<Object,Object>();
		   if(coll != null){
			  Iterator<Object>  iter = coll.iterator();
			   while(iter.hasNext()){
				   NBSDocumentMetadataDT nbsDocMDT = (NBSDocumentMetadataDT)iter.next();
				   nbsDocMetaMapBySchemaLocation.put(nbsDocMDT.getXmlSchemaLocation(), nbsDocMDT);
				   
			   }
		   }
		   return nbsDocMetaMapBySchemaLocation;
	   }
	   
	   public static TreeMap<Object,Object>  getContactQuestionMap() {
	        TreeMap<Object,Object> contactQuestionMap = null;
			if (contactMap != null && contactMap.size() > 0) {
				return (TreeMap<Object,Object>) contactMap;
			}

			try {
				if (getQuestionMapEJBRef() != null){
				Collection<Object>  qColl = 	getQuestionMapEJBRef().getContactQuestions();
				contactQuestionMap = createContactQuestionMap(qColl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			contactMap.putAll(contactQuestionMap);
			return contactQuestionMap;
		}
	   
	   private static TreeMap<Object,Object> createContactQuestionMap(Collection<Object>  coll) {
	    	TreeMap<Object, Object> qCodeMap = new TreeMap<Object, Object>();
	    	TreeMap<Object,Object> qContactMap = new TreeMap<Object,Object>();
	    	
	    	if (coll != null && coll.size() > 0) {    		
	    		Iterator ite = coll.iterator();
	    		while (ite.hasNext()) {		
	    			NbsQuestionMetadata qMetadata = (NbsQuestionMetadata) ite.next();    			
	    			String dataType = qMetadata.getDataType();
	    			ArrayList<Object> aList = new ArrayList<Object>();
	    			if(dataType != null && dataType.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)){
	    				aList = CachedDropDowns.getCodedValueForType(qMetadata.getCodeSetNm());
	    				qMetadata.setAList(aList);
	    			}
	    			if (qMetadata.getInvestigationFormCd() != null && qMetadata.getInvestigationFormCd().trim().equals(NBSConstantUtil.CONTACT_REC)) {
	    				String questionId = qMetadata.getQuestionIdentifier() == null ? "" : qMetadata.getQuestionIdentifier();
	    				String uiMetadataUid = qMetadata.getNbsUiMetadataUid() == null ? "" : qMetadata.getNbsUiMetadataUid().toString();
	    				if(questionId.equals("")) {
	        				//define a questionId for a new Tab added
	    					qContactMap.put(uiMetadataUid, qMetadata);
	    				} else {
	    					qContactMap.put(questionId ,qMetadata);    					
	    				}
	    			}
	    		}
	    		qCodeMap.put(NBSConstantUtil.CONTACT_REC, qContactMap);
	    	}
	    	return qCodeMap;
	    }
	   
	   private static TreeMap<Object,Object> createDMBQuestionMap(Collection<Object>  coll) throws Exception{
	    	TreeMap<Object, Object> qCodeMap = new TreeMap<Object, Object>();	    
	    	int count =0;
	    	int loopcount=0;
	    	int sizecount=0;
	    	String currentFormCode="";
	    	String previousFormCode="";
	    	
	    	//For Demo Purpose CHOLERA Metadata
	    	TreeMap<Object,Object> qInvFormChlrMap = new TreeMap<Object,Object>();
	    	TreeMap<Object, Object>[] map ;
			map = new TreeMap[coll.size()];
			NbsQuestionMetadata qMetadata = null;
	    	try{
	    	if (coll != null && coll.size() > 0) {    		
	    		Iterator ite = coll.iterator();
	    		while (ite.hasNext()) {	
	    			sizecount++;
	    			qMetadata = (NbsQuestionMetadata) ite.next();    			
	    			String dataType = qMetadata.getDataType();
	    			ArrayList<Object> aList = new ArrayList<Object>();
	    			if(dataType != null && dataType.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)){
	    				aList = CachedDropDowns.getCodedValueForType(qMetadata.getCodeSetNm());
	    				qMetadata.setAList(aList);
	    			}    			
	    			
	    			 if(qMetadata.getInvestigationFormCd() != null){
	    				
	    				if(loopcount==0){
	    			    	previousFormCode = qMetadata.getInvestigationFormCd();
	    					String questionId = qMetadata.getQuestionIdentifier() == null ? "" : qMetadata.getQuestionIdentifier();
	        				String ldfPageId = qMetadata.getLdfPageId() == null ? "" : qMetadata.getLdfPageId();
	        				String uiMetadataUid = qMetadata.getNbsUiMetadataUid() == null ? "" : qMetadata.getNbsUiMetadataUid().toString();
	        				if(!questionId.equals("")) {
	        					map[count] = new TreeMap<Object, Object>();
	        					map[count].put(questionId ,qMetadata);
	        					loopcount++;	
	        				}        				
	    					
	    				}else{
	    					   currentFormCode = qMetadata.getInvestigationFormCd();
	    					   if(currentFormCode.equals(previousFormCode)){
	    						   String questionId = qMetadata.getQuestionIdentifier() == null ? "" : qMetadata.getQuestionIdentifier();
	    	        				String ldfPageId = qMetadata.getLdfPageId() == null ? "" : qMetadata.getLdfPageId();
	    	        				String uiMetadataUid = qMetadata.getNbsUiMetadataUid() == null ? "" : qMetadata.getNbsUiMetadataUid().toString();
	    	        				if(!questionId.equals("")) {    	        					
	    	        					map[count].put(questionId ,qMetadata);
	    	        				}   	        				
	    					  
	    					   }else{
    							   qCodeMap.put(previousFormCode, map[count]);
	    						    count= count+1; 
	    						    String questionId = qMetadata.getQuestionIdentifier() == null ? "" : qMetadata.getQuestionIdentifier();
		   	        				String ldfPageId = qMetadata.getLdfPageId() == null ? "" : qMetadata.getLdfPageId();
		   	        				String uiMetadataUid = qMetadata.getNbsUiMetadataUid() == null ? "" : qMetadata.getNbsUiMetadataUid().toString();
		   	        				if(!questionId.equals("")) {  
		   	        				map[count] = new TreeMap<Object, Object>();	
	   	        					map[count].put(questionId ,qMetadata);
	   	        			    	}  						   
	    						   
	    					   }
	    					   previousFormCode = currentFormCode; 	
	    					   loopcount++;	
	    					  
	    					
	    				}	    								
	    				
	    			}   
	    			 if(sizecount==coll.size())
	    			 {
	    				 qCodeMap.put(qMetadata.getInvestigationFormCd(), map[count]);
	    			 }

	    		}
	    	
	    	}
	    	}
	    	catch(Exception ex){
	    		throw new NEDSSSystemException("The caching failed due to question label :" + qMetadata.getQuestionLabel()+" in form cd :"+ qMetadata.getInvestigationFormCd());
	    	}
	    	
	    	return qCodeMap;
	    }
	   
	public static void fillPrePopMap() {

		if (fromPrePopFormMapping == null || fromPrePopFormMapping.size() == 0) {
			try {
				if (getQuestionMapEJBRef() != null) {
					Collection<Object> qColl = getQuestionMapEJBRef().getPrePopMapping();
					createPrePopFromMap(qColl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		if (toPrePopFormMapping==null || toPrePopFormMapping.size() == 0){
			try {
				if (getQuestionMapEJBRef() != null) {
					Collection<Object> qColl = getQuestionMapEJBRef().getPrePopMapping();
					createPrePopToMap(qColl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	   
	private static void createPrePopFromMap(Collection<Object> coll) throws Exception {
		int count = 0;
		int loopcount = 0;
		int sizecount = 0;
		String currentFormCode = "";
		String previousFormCode = "";

		TreeMap<Object, Object>[] map = new TreeMap[coll.size()];
		PrePopMappingDT qMetadata = null;
		try {
			if (coll != null && coll.size() > 0) {
				Iterator<Object> ite = coll.iterator();
				while (ite.hasNext()) {
					sizecount++;
					qMetadata = (PrePopMappingDT) ite.next();

					if (qMetadata.getFromFormCd() != null) {

						if (loopcount == 0) {
							previousFormCode = qMetadata.getFromFormCd();
							String fromQuestionId = qMetadata.getFromQuestionIdentifier() == null ? ""
									: qMetadata.getFromQuestionIdentifier();
							String fromAns = qMetadata.getFromAnswerCode();
							map[count] = new TreeMap<Object, Object>();
							if (!fromQuestionId.equals("")) {
								if (fromAns != null) {
									fromQuestionId = fromQuestionId + "$" + fromAns;
									map[count].put(fromQuestionId, qMetadata);
								}
								PrePopMappingDT qMetadata1 = (PrePopMappingDT)qMetadata.deepCopy();
								qMetadata1.setFromAnswerCode(null);
								map[count].put(qMetadata.getFromQuestionIdentifier(), qMetadata1);
								loopcount++;
							}

						} else {
							currentFormCode = qMetadata.getFromFormCd();
							if (currentFormCode.equals(previousFormCode)) {
								String fromQuestionId = qMetadata.getFromQuestionIdentifier() == null ? ""
										: qMetadata.getFromQuestionIdentifier();
								String fromAns = qMetadata.getFromAnswerCode();
								if (!fromQuestionId.equals("")) {
									if (fromAns != null) {
										fromQuestionId = fromQuestionId + "$" + fromAns;
										map[count].put(fromQuestionId, qMetadata);
									}
									PrePopMappingDT qMetadata1 = (PrePopMappingDT)qMetadata.deepCopy();
									qMetadata1.setFromAnswerCode(null);
									map[count].put(qMetadata.getFromQuestionIdentifier(), qMetadata1);
								}

							} else {
								fromPrePopFormMapping.put(previousFormCode, map[count]);
								count = count + 1;
								String fromQuestionId = qMetadata.getFromQuestionIdentifier() == null ? ""
										: qMetadata.getFromQuestionIdentifier();
								String fromAns = qMetadata.getFromAnswerCode();
								if (!fromQuestionId.equals("")) {
									map[count] = new TreeMap<Object, Object>();
									if (fromAns != null) {
										fromQuestionId = fromQuestionId + "$" + fromAns;
										map[count].put(fromQuestionId, qMetadata);
									}
									PrePopMappingDT qMetadata1 = (PrePopMappingDT)qMetadata.deepCopy();
									qMetadata1.setFromAnswerCode(null);
									map[count].put(qMetadata.getFromQuestionIdentifier(), qMetadata1);
								}

							}
							previousFormCode = currentFormCode;
							loopcount++;
						}

					}
					if (sizecount == coll.size()) {
						fromPrePopFormMapping.put(qMetadata.getFromFormCd(), map[count]);
					}

				}

			}
		} catch (Exception ex) {
			throw new NEDSSSystemException("The from prepop caching failed due to question label :"
					+ qMetadata.getFromQuestionIdentifier() + " in form cd :" + qMetadata.getFromFormCd());
		}

	}

	private static void createPrePopToMap(Collection<Object> coll) throws Exception {
		int count = 0;
		int loopcount = 0;
		int sizecount = 0;
		String currentFormCode = "";
		String previousFormCode = "";

		TreeMap<Object, Object>[] map = new TreeMap[coll.size()];
		PrePopMappingDT qMetadata = null;
		try {
			if (coll != null && coll.size() > 0) {
				Iterator<Object> ite = coll.iterator();
				while (ite.hasNext()) {
					sizecount++;
					qMetadata = (PrePopMappingDT) ite.next();

					if (qMetadata.getToFormCd() != null) {

						if (loopcount == 0) {
							previousFormCode = qMetadata.getToFormCd();
							String toQuestionId = qMetadata.getToQuestionIdentifier() == null ? ""
									: qMetadata.getToQuestionIdentifier();
							String fromQuestionId = qMetadata.getFromQuestionIdentifier() == null ? ""
									: qMetadata.getFromQuestionIdentifier();
							toQuestionId = toQuestionId+'^'+fromQuestionId;
							String fromAns = qMetadata.getFromAnswerCode();
							String toQuestionIdWithAns = null;
							map[count] = new TreeMap<Object, Object>();
							if (!toQuestionId.equals("")) {
								if (fromAns != null) {
									toQuestionIdWithAns = toQuestionId + "$" + fromAns;
									map[count].put(toQuestionIdWithAns, qMetadata);
								}
								PrePopMappingDT qMetadata1 = (PrePopMappingDT)qMetadata.deepCopy();
								qMetadata1.setToAnswerCode(null);
								map[count].put(toQuestionId, qMetadata1);
								loopcount++;
							}

						} else {
							currentFormCode = qMetadata.getToFormCd();
							if (currentFormCode.equals(previousFormCode)) {
								String toQuestionId = qMetadata.getToQuestionIdentifier() == null ? ""
										: qMetadata.getToQuestionIdentifier();
								String fromAns = qMetadata.getFromAnswerCode();
								String fromQuestionId = qMetadata.getFromQuestionIdentifier() == null ? ""
										: qMetadata.getFromQuestionIdentifier();
								String toQuestionIdWithAns = null;
								toQuestionId = toQuestionId+'^'+fromQuestionId;
								if (!toQuestionId.equals("")) {
									if (fromAns != null) {
										toQuestionIdWithAns = toQuestionId + "$" + fromAns;
										map[count].put(toQuestionIdWithAns, qMetadata);
									}
									PrePopMappingDT qMetadata1 = (PrePopMappingDT)qMetadata.deepCopy();
									qMetadata1.setToAnswerCode(null);
									map[count].put(toQuestionId, qMetadata1);
								}

							} else {
								toPrePopFormMapping.put(previousFormCode, map[count]);
								count = count + 1;
								String toQuestionId = qMetadata.getToQuestionIdentifier() == null ? ""
										: qMetadata.getToQuestionIdentifier();
								String fromAns = qMetadata.getFromAnswerCode();
								String fromQuestionId = qMetadata.getFromQuestionIdentifier() == null ? ""
										: qMetadata.getFromQuestionIdentifier();
								toQuestionId = toQuestionId+'^'+fromQuestionId;
								String toQuestionIdWithAns = null;
								map[count] = new TreeMap<Object, Object>();
								if (!toQuestionId.equals("")) {
									if (fromAns != null) {
										toQuestionIdWithAns = toQuestionId + "$" + fromAns;
										map[count].put(toQuestionIdWithAns, qMetadata);
									}
									PrePopMappingDT qMetadata1 = (PrePopMappingDT)qMetadata.deepCopy();
									qMetadata1.setToAnswerCode(null);
									map[count].put(toQuestionId, qMetadata1);
								}

							}
							previousFormCode = currentFormCode;
							loopcount++;
						}

					}
					if (sizecount == coll.size()) {
						toPrePopFormMapping.put(qMetadata.getToFormCd(), map[count]);
					}

				}

			}
		} catch (Exception ex) {
			throw new NEDSSSystemException("The to prepop caching failed due to question Identifier :"
					+ qMetadata.getToQuestionIdentifier() + " in form cd :" + qMetadata.getToFormCd());
		}

	}
	
	@SuppressWarnings("unchecked")
	public static TreeMap<Object, Object> getToPrePopFormMapping(String formCd) throws NEDSSSystemException {
		TreeMap<Object, Object> returnMap = null;
		try {
			returnMap = (TreeMap<Object, Object>) toPrePopFormMapping.get(formCd);
			if (returnMap == null) {
				if (getQuestionMapEJBRef() != null) {
					Collection<Object> qColl = getQuestionMapEJBRef().getPrePopMapping();
					createPrePopToMap(qColl);
				}
				returnMap = (TreeMap<Object, Object>) toPrePopFormMapping.get(formCd);
			}
			return returnMap;
		} catch (Exception ex) {
			throw new NEDSSSystemException("The to prepop caching failed for form Cd: " + formCd);
		}

	}

	@SuppressWarnings("unchecked")
	public static TreeMap<Object, Object> getFromPrePopFormMapping(String formCd) throws NEDSSSystemException {
		TreeMap<Object, Object> returnMap = null;
		try {
			returnMap = (TreeMap<Object, Object>) fromPrePopFormMapping.get(formCd);
			if (returnMap == null) {
				if (getQuestionMapEJBRef() != null) {
					Collection<Object> qColl = getQuestionMapEJBRef().getPrePopMapping();
					createPrePopFromMap(qColl);
				}
				returnMap = (TreeMap<Object, Object>) fromPrePopFormMapping.get(formCd);
			}
			return returnMap;
		} catch (Exception ex) {
			throw new NEDSSSystemException("The From prepop caching failed for form Cd: " + formCd);
		}

	}


}
