package gov.cdc.nedss.webapp.nbs.logicsheet.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator ;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

import gov.cdc.nedss.systemservice.ejb.questionmapejb.bean.QuestionMap;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.bean.QuestionMapHome;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.*;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import javax.rmi.PortableRemoteObject;

public class RuleAdminHelper {
	public static final LogUtils logger = new LogUtils(RuleAdminHelper.class
			.getName());

	private static QuestionMap qMap = null;

	public static Map<Object,Object> map = new TreeMap<Object,Object>();
	public static Map<Object,Object> rMap = new TreeMap<Object,Object>();
	public static Map<Object,Object> formRuleMap = new TreeMap<Object,Object>();
	public static ArrayList<Object> ruleListContents = new ArrayList<Object> ();
	public static ArrayList<Object> pamLabelListContents = new ArrayList<Object> ();
	public static ArrayList<Object> opTypeListContents = new ArrayList<Object> ();
	public static ArrayList<Object> errMessageListContents = new ArrayList<Object> ();
	public static ArrayList<Object> conseqListContents = new ArrayList<Object> ();

	private static QuestionMap getQuestionMapEJBRef() throws Exception {
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

	private static TreeMap<Object,Object> createQuestionMap(Collection<Object>  coll) {
    	TreeMap<Object,Object> qCodeMap = new TreeMap<Object,Object>();
    	TreeMap<Object,Object> qInvFormRVCTMap = new TreeMap<Object,Object>();
    	if (coll != null && coll.size() > 0) {
    		Iterator<Object>  ite = coll.iterator();
    		while (ite.hasNext()) {
    			NbsQuestionMetadata qMetadata = (NbsQuestionMetadata) ite
    			.next();
    			if (qMetadata.getInvestigationFormCd().equals(
    					NBSConstantUtil.INV_FORM_RVCT))
    				qInvFormRVCTMap.put(qMetadata.getQuestionIdentifier(),
    						qMetadata);
    		}
    		qCodeMap.put(NBSConstantUtil.INV_FORM_RVCT, qInvFormRVCTMap);
    	}
    	return qCodeMap;
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
		TreeMap<Object,Object> ruleMap = new TreeMap<Object,Object>();
		if (fieldRuleColl != null && fieldRuleColl.size() > 0) {
			Iterator<Object>  ruleIter = fieldRuleColl.iterator();
			while (ruleIter.hasNext()) {
				RulesDT rulesDT = (RulesDT) ruleIter.next();
				if (ruleMap.containsKey(rulesDT.getQuestionIdentifer())) {
					((ArrayList<Object> ) ruleMap.get(rulesDT.getQuestionIdentifer()))
							.add(rulesDT);
				} else {
					ArrayList<Object> rules = new ArrayList<Object> ();
					rules.add(rulesDT);
					ruleMap.put(rulesDT.getQuestionIdentifer(), rules);
				}
			}
		}

		return ruleMap;
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
		TreeMap<Object,Object> formRuleMap = new TreeMap<Object,Object>();
		if (formRuleColl != null && formRuleColl.size() > 0) {
			Iterator<Object>  ruleIter = formRuleColl.iterator();
			while (ruleIter.hasNext()) {
				RulesDT rulesDT = (RulesDT) ruleIter.next();
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

	/*
	    * Get all existing Rules for Rule Admin Tool
	    */
	   public static ArrayList<Object> getRules(){
	    	ArrayList<Object> ruleList = null;
	        if(ruleListContents != null && ruleListContents.size()>0){
	           	return ruleListContents;
	        }
	        try {
			if (getQuestionMapEJBRef() != null) {
				ruleList = getQuestionMapEJBRef().getRuleList();
				if (ruleList != null && ruleList.size() > 0) {
				   Iterator<Object>  iter = ruleList.iterator();  
				     DropDownCodeDT ddDT1 = new DropDownCodeDT();	
				     ddDT1.setKey(null);
                     ddDT1.setValue("");
                     ruleListContents.add(ddDT1);                
                    while (iter.hasNext()) {
						RulesDT rDT = (RulesDT) iter.next();
						DropDownCodeDT ddDT = new DropDownCodeDT();						
						ddDT.setKey(rDT.getRuleUid().toString());
						ddDT.setValue(rDT.getRuleName());
						ruleListContents.add(ddDT);
					}
				}

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return ruleListContents;
		}

	   /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  ArrayList<Object> findRuleInstances(String whereclause){
	    	ArrayList<Object> ruleInstancesList = null;

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   ruleInstancesList = getQuestionMapEJBRef().findRuleInstances(whereclause);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return ruleInstancesList;
		}
	   
	   /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  ArrayList<Object> findSourceFields(String whereclause){
	    	ArrayList<Object> sourceFieldList = null;

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   sourceFieldList = getQuestionMapEJBRef().findSourceFields(whereclause);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return sourceFieldList;
		}

	   /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  ArrayList<Object> findTargetFields(String whereclause){
	    	ArrayList<Object> targetFieldList = null;

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   targetFieldList = getQuestionMapEJBRef().findTargetFields(whereclause);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return targetFieldList;
		}

	   /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  ArrayList<Object> findSourceValues(String whereclause){
	    	ArrayList<Object> sourceValList = null;

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   sourceValList = getQuestionMapEJBRef().findSourceValues(whereclause);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return sourceValList;
		}

	   /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  ArrayList<Object> findTargetValues(String whereclause){
	    	ArrayList<Object> targetValList = null;

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   targetValList = getQuestionMapEJBRef().findTargetValues(whereclause);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return targetValList;
		}

	   /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  String deleteRuleIns(String strRuleInsUID){
	    	String ruleInstancesList = null;

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   ruleInstancesList = getQuestionMapEJBRef().deleteRuleIns(strRuleInsUID);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return ruleInstancesList;
		}

	   /*
	    * Get all existing Rules for Rule Admin Tool
	    */
	  /* public  ArrayList<Object> getConseqInd(){
	    	ArrayList<Object> conseqList = null;
	    	ArrayList<Object> conlist= new ArrayList<Object> ();
	    	DropDownCodeDT ddDT = new DropDownCodeDT();
	    	Map conMap = new HashMap<Object,Object>();
	        if(conseqListContents != null && conseqListContents.size()>0){
	           	return conseqListContents;
	        }
	        try {
			if (getQuestionMapEJBRef() != null) {
				conseqList = getQuestionMapEJBRef().getConseqInd();
				if (conseqList != null && conseqList.size() > 0) {
					
					for(int i=0;i<conseqList.size();i += 2)	{									
						ddDT.setKey(conseqList.get(i).toString());
						ddDT.setValue(conseqList.get(i+1).toString());
						//conseqListContents.add(ddDT);
						conMap.put(ddDT.getKey(),ddDT.getValue());
					}
						
					
				}
				if (conMap != null && conMap.size() > 0) {
					Collection<Object>  qColl =  conMap.keySet();
					Iterator<Object>  ite = qColl.iterator();
					while (ite.hasNext()){
						Object conseqId= ite.next();
						String conseqdesc=conMap.get(conseqId).toString();
						conlist.add(conseqId.toString());
						conlist.add(conseqdesc);
						
					}
				}
				

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return conlist;
		}*/
	   /*
	    * Get all existing Rules for Rule Admin Tool
	    */
	   public static ArrayList<Object> getConseqInd(){
	    	ArrayList<Object> conseqList = null;	    	
	        if(conseqListContents != null && conseqListContents.size()>0){
	           	return conseqListContents;
	        }
	        try {
			if (getQuestionMapEJBRef() != null) {
				conseqList = getQuestionMapEJBRef().getConseqInd();
				if (conseqList != null && conseqList.size() > 0) {
					Iterator<Object>  iter = conseqList.iterator();
					while (iter.hasNext()) {
						RulesDT rDT = (RulesDT) iter.next();
						DropDownCodeDT ddDT = new DropDownCodeDT();
						ddDT.setKey(rDT.getConseqIndUID().toString());						
						ddDT.setValue(rDT.getConseqIndtxt());
						conseqListContents.add(ddDT);
					}
				}

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return conseqListContents;
		}

	   /*
	    * Get all existing Rules for Rule Admin Tool
	    */
	   public static ArrayList<Object> getErrorMessages(){
	    	ArrayList<Object> errMList = null;
	        if(errMessageListContents != null && errMessageListContents.size()>0){
	           	return errMessageListContents;
	        }
	        try {
			if (getQuestionMapEJBRef() != null) {
				errMList = getQuestionMapEJBRef().getErrorMessages();
				if (errMList != null && errMList.size() > 0) {
					Iterator<Object>  iter = errMList.iterator();

					while (iter.hasNext()) {
						RulesDT rDT = (RulesDT) iter.next();
						DropDownCodeDT ddDT = new DropDownCodeDT();
						ddDT.setKey(rDT.getErrMessageUID().toString());
						ddDT.setValue(rDT.getErrMessagetxt());
						errMessageListContents.add(ddDT);
					}
				}

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return errMessageListContents;
		}

	   /*
	    * Get all existing Rules for Rule Admin Tool
	    */
	   public static ArrayList<Object> getOperatorTypes(){
	    	ArrayList<Object> opTypeList = null;
	        if(opTypeListContents != null && opTypeListContents.size()>0){
	           	return opTypeListContents;
	        }
	        try {
			if (getQuestionMapEJBRef() != null) {
				opTypeList = getQuestionMapEJBRef().getOperatorTypes();
				if (opTypeList != null && opTypeList.size() > 0) {
					Iterator<Object>  iter = opTypeList.iterator();

					while (iter.hasNext()) {
						RulesDT rDT = (RulesDT) iter.next();
						DropDownCodeDT ddDT = new DropDownCodeDT();
						ddDT.setKey(rDT.getOpTypeUID().toString());
						ddDT.setValue(rDT.getOpTypetxt());
						opTypeListContents.add(ddDT);
					}
				}

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return opTypeListContents;
		}

	   /*
	    * Get all existing Rules for Rule Admin Tool
	    */
	   public static ArrayList<Object> getPAMLabels(){
	    	ArrayList<Object> pamLabelList = null;
	        if(pamLabelListContents != null && pamLabelListContents.size()>0){
	           	return pamLabelListContents;
	        }
	        try {
			if (getQuestionMapEJBRef() != null) {
				pamLabelList = getQuestionMapEJBRef().getPAMLabels();
				if (pamLabelList != null && pamLabelList.size() > 0) {
				     DropDownCodeDT ddDT1 = new DropDownCodeDT();	
				     ddDT1.setKey(null);
                     ddDT1.setValue("");
                     pamLabelListContents.add(ddDT1); 
                     
					Iterator<Object>  iter = pamLabelList.iterator();

					while (iter.hasNext()) {
						RulesDT rDT = (RulesDT) iter.next();
						DropDownCodeDT ddDT = new DropDownCodeDT();
						ddDT.setKey(rDT.getNbsMetadataRuleUID().toString());
						String pamdesc=rDT.getPamLabeltxt() + " - " + rDT.getPamIdentifiertxt();
						ddDT.setValue(pamdesc);
						pamLabelListContents.add(ddDT);
					}
				}

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return pamLabelListContents;
		}
	   /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  String addRuleInstance(RulesDT rulesDT){
	    	
	    	String ruleInsUID="";

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   ruleInsUID = getQuestionMapEJBRef().addRuleInstance(rulesDT);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return ruleInsUID;
		}
	   
	   /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  String addSourceField(SourceFieldDT srcFieldDT){
	    	
	    	String srcFInsUID="";

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   srcFInsUID = getQuestionMapEJBRef().addSourceField(srcFieldDT);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return srcFInsUID;
		}

	   /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  String addTargetField(TargetFieldDT tarFieldDT){
	    	
	    	String tarFInsUID="";

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   tarFInsUID = getQuestionMapEJBRef().addTargetField(tarFieldDT);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return tarFInsUID;
		}

	   /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  String addSourceValue(SourceValueDT srcValueDT){
	    	
	    	String srcVInsUID="";

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   srcVInsUID = getQuestionMapEJBRef().addSourceValue(srcValueDT);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return srcVInsUID;
		}
	   
	   /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  String addTargetValue(TargetValueDT tarValueDT){
	    	
	    	String tarVInsUID="";

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   tarVInsUID = getQuestionMapEJBRef().addTargetValue(tarValueDT);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return tarVInsUID;
		}
		
		/*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  boolean updateSourceValue(SourceValueDT srcValueDT){
	    	
	    	boolean updatestatus=false;

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   updatestatus = getQuestionMapEJBRef().updateSourceValue(srcValueDT);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return updatestatus;
		}
		
		/*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  boolean updateTargetValue(TargetValueDT tarValueDT){
	    	
	    	boolean updatestatus=false;

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   updatestatus = getQuestionMapEJBRef().updateTargetValue(tarValueDT);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return updatestatus;
		}
    
    /*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  boolean deleteSourceField(String srcFieldUID){
	    	
	    	boolean deletestatus=false;

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   deletestatus = getQuestionMapEJBRef().deleteSourceField(srcFieldUID);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return deletestatus;
		}
		
		/*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  boolean deleteTargetField(String tarFieldUID){
	    	
	    	boolean deletestatus=false;

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   deletestatus = getQuestionMapEJBRef().deleteTargetField(tarFieldUID);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return deletestatus;
		}
		
		/*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  ArrayList<Object> getFormCode(Collection<Object>  pamQuestions){
	    	
	    	ArrayList<Object> formList = new ArrayList<Object> ();

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   formList = getQuestionMapEJBRef().getFormCode(pamQuestions);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return formList;
		}
		
		/*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  boolean associateFormCode(ArrayList<Object> RulesDT){
	    	
	    	boolean associatestatus= false;

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   associatestatus = getQuestionMapEJBRef().associateFormCode(RulesDT);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return associatestatus;
		}
		
		/*
	    * Get all existing Rules Instances for a particular Rule
	    */
	   public  ArrayList<Object> findfrmCodes(String ruleInsUID){
	    	
	    	ArrayList<Object> formCodeList = new ArrayList<Object> ();

	        try {
			   if (getQuestionMapEJBRef() != null) {
				   formCodeList = getQuestionMapEJBRef().findfrmCodes(ruleInsUID);

			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		   return formCodeList;
		}


}
