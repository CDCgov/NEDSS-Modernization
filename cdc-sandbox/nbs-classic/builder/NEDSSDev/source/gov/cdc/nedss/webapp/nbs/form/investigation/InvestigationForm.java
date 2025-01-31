

package gov.cdc.nedss.webapp.nbs.form.investigation;



import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.notification.util.NotificationUtil;
import gov.cdc.nedss.webapp.nbs.action.notification.util.RejectNotificationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.managectassociation.ManageCTAssociateForm;
import gov.cdc.nedss.webapp.nbs.form.util.CommonForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.util.BatchEntryHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import javax.servlet.http.HttpServletRequest;

/**
 * Title:         InvestigationForm is class.
 * Description:   Provides classes for creating and modifying Investigation Form
 * Copyright:     Copyright (c) 2001
 * Company:       CSC
 * @author        NEDSS TEAM
 * @version       1.0
 */

public class InvestigationForm extends CommonForm {
	
	private static final LogUtils logger = new LogUtils(InvestigationForm.class.getName());

  /**
    * form loads as boolean values for condition check.
    */
    private Boolean loadedFromDB = new Boolean(false);


    /**
    * form loads values to the InvestigationProxyVO objects
    */
    private InvestigationProxyVO proxy = null;

    /**
    * form loads old values to the InvestigationProxyVO objects
    */
    private InvestigationProxyVO old = null;

    /**
    * form loads antibiotic batch entry values to the collection
    */
    private Collection<Object>  antibioticBatchEntryCollection  = null;

    /**
    * form loads source batch entry values to the collection
    */
    private Collection<Object>  sourceBatchEntryCollection  = null;

    /**
    * form loads supplemental values to the collection
    */
    private Collection<ObservationVO>  supplementalCollection  = null;

    // this is to make revision patient as part of form
    private PersonVO patient;

    private PersonVO oldRevision;

    private TreeMap<Object,Object> observationMap;
    
    
    

    /**
    * Access method for the reset form objects.
    *
    */
    public void reset()
    {
      proxy = null;
      old = null;
      patient = null;
      oldRevision = null;
      antibioticBatchEntryCollection  = null;
      sourceBatchEntryCollection  = null;
      supplementalCollection  = null;
      patient = null;
      oldRevision = null;
      observationMap = null;
      super.resetLDF();
    }
 // Added By sita..starts here
    private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
    Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
    Map<Object,Object> searchMap = new HashMap<Object,Object>();
    private NotificationSummaryVO notificationSummaryVO = new NotificationSummaryVO();
    private ArrayList<Object> dateFilterList = new ArrayList<Object> ();
    private ArrayList<Object> submittedBy = new ArrayList<Object> ();
    private ArrayList<Object> conditions = new ArrayList<Object> ();
	private ArrayList<Object> caseStatus = new ArrayList<Object> ();
	private ArrayList<Object> type = new ArrayList<Object> ();
	private ArrayList<Object> recipient = new ArrayList<Object> ();
	
	private String confirmationMessage= "";
	private ArrayList<Object> rejectedQdateFilterList = new ArrayList<Object> ();
    private ArrayList<Object> rejectedQsubmittedBy = new ArrayList<Object> ();
    private ArrayList<Object> rejectedQconditions = new ArrayList<Object> ();
	private ArrayList<Object> rejectedQcaseStatus = new ArrayList<Object> ();
	private ArrayList<Object> rejectedQtype = new ArrayList<Object> ();
	private ArrayList<Object> rejectedQrecipient = new ArrayList<Object> ();
	private ArrayList<Object> rejectedQRejectedBy = new ArrayList<Object> ();
	
	NotificationUtil notifUtil = new NotificationUtil();

    public void initializeDropDowns(ArrayList<Object> notificationColl) {
    	QueueUtil queueUtil = new QueueUtil();  	
    	submittedBy = notifUtil.getSubmittedByDropDowns(notificationColl);
    	dateFilterList = queueUtil.getStartDateDropDownValues();
    	conditions = notifUtil.getConditionDropDowns(notificationColl);
    	caseStatus = notifUtil.getCaseStatusDropDowns(notificationColl);
    	type = notifUtil.getTypeDropDowns(notificationColl);
    	recipient=notifUtil.getRecipientDropDowns(notificationColl);
    	
    	
    }
    public void initializeRejectedDropDowns(ArrayList<Object> notificationList) {
    	QueueUtil queueUtil = new QueueUtil(); 
    	RejectNotificationUtil rejNitif = new RejectNotificationUtil();
    	rejectedQsubmittedBy = rejNitif.getSubmittedByForRejectedQueueDropDowns(notificationList);
    	rejectedQdateFilterList = queueUtil.getStartDateDropDownValues();
    	rejectedQconditions = rejNitif.getConditionForRejectedQueueDropDowns(notificationList);
    	rejectedQcaseStatus = rejNitif.getCaseStatusForRejectedQueueDropDowns(notificationList);
    	rejectedQtype = rejNitif.getTypeForRejectedQueueDropDowns(notificationList);
    	rejectedQrecipient=rejNitif.getRecipientForRejectedQueueDropDowns(notificationList);
    	rejectedQRejectedBy=rejNitif.getRejectedByForRejectedQueueDropDowns(notificationList);
    	
    }

    public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
	}
    public String[] getAnswerArray(String key) {
		return (String[])searchCriteriaArrayMap.get(key);
	}

	/**
	 * @param key
	 * @param answer
	 */
	public void setAnswerArray(String key, String[] answer) {
		if(answer.length > 0) {
			String [] answerList = new String[answer.length];
			boolean selected = false;
			for(int i=1; i<=answer.length; i++) {
				String answerTxt = answer[i-1];
				if(!answerTxt.equals("")) {
					selected = true;
					answerList[i-1] = answerTxt;
				}
			}
			if(selected)
				searchCriteriaArrayMap.put(key,answerList);
		}
	}
	
	public void setAnswerArrayText(String key, String answer) {
		if(answer!=null && answer.length() > 0) {
			String newKey = key+"_FILTER_TEXT";
				searchCriteriaArrayMap.put(newKey,answer);
		}
	}
	
    /**
    * Access method for proxy object exists.
    *
    * @return   boolean values
    */
    public boolean hasProxy()
    {
      if (proxy == null)
	return false;
      else
	return true;
    }

       /**
    * Access method for the proxy property.
    *
    * @return  InvestigationProxyVO the current value of the InvestigationProxyVO property
    */

    public InvestigationProxyVO getProxy()
    {
      if (proxy == null)
	proxy = new InvestigationProxyVO();

	return this.proxy;
    }

    /**
    * Sets the value of the proxy property.
    *
    * @param the new value of the InvestigationProxyVO property
    */
    public void setProxy(InvestigationProxyVO newVal)
    {
	this.proxy = newVal;
    }

    /**
    * Access method for the old proxy property.
    *
    * @return InvestigationProxyVO  the current value of the InvestigationProxyVO property
    */
    public InvestigationProxyVO getOldProxy()
    {
	return this.old;
    }

    /**
    * Sets the value of the old proxy property.
    *
    * @param InvestigationProxyVO the new value of the InvestigationProxyVO property
    */
    public void setOldProxy(InvestigationProxyVO old)
    {
	this.old = old;
    }

    /**
    * gets the value of the LoadedFromDB property.
    *
    * @return ItDelete the new value of the boolean property
    */
    public boolean isLoadedFromDB()
    {
      return (this.loadedFromDB.booleanValue());
    }


    /**
    * Sets the value of the LoadedFromDB property.
    *
    * @param boolean the newVal
    */
    public void setLoadedFromDB(boolean newVal)
    {
      this.loadedFromDB = new Boolean(newVal);
    }

    /**
    * Sets the value of the old AntibioticBatchEntryCollection  property.
    *
    * @param the new value of the AntibioticBatchEntryCollection  property
    */
    public void setAntibioticBatchEntryCollection(Collection<Object> antibioticBatchEntryCollection)
    {
      this.antibioticBatchEntryCollection  = antibioticBatchEntryCollection;
    }

    /**
    * Access method for the old AntibioticBatchEntryCollection  property.
    *
    * @return   the current value of the AntibioticBatchEntryCollection  property collection
    */
    public Collection<Object>  getAntibioticBatchEntryCollection()
    {
      return this.antibioticBatchEntryCollection;
    }

    /**
    * Access method for the old AntibioticBatchEntryCollection  property.
    *
    * @param int the index
    * @return   the current value of the AntibioticBatchEntryCollection  property collection
    */
    public BatchEntryHelper getAntibioticBatchEntry_s(int index)
    {
      // this should really be in the constructor
      if (this.antibioticBatchEntryCollection  == null)
	  this.antibioticBatchEntryCollection  = new ArrayList<Object> ();

      int currentSize = this.antibioticBatchEntryCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
	try {
	  Object[] tempArray = this.antibioticBatchEntryCollection.toArray();

	  Object tempObj  = tempArray[index];

	  BatchEntryHelper temp = (BatchEntryHelper) tempObj;

	  return temp;
	}
	catch (Exception e) {
	   //##!! System.out.println(e);
        } // do nothing just continue
      }

       BatchEntryHelper temp = null;

	for (int i = currentSize; i < index+1; i++)
	{
	  temp = new BatchEntryHelper();
	  this.antibioticBatchEntryCollection.add(temp);
	}
	return temp;
    }


    /**
    * Sets the value of the old SourceBatchEntryCollection  property.
    *
    * @param the new value of the SourceBatchEntryCollection  as collection
    */
    public void setSourceBatchEntryCollection(Collection<Object> sourceBatchEntryCollection)
    {
      this.sourceBatchEntryCollection  = sourceBatchEntryCollection;
    }

    /**
    * Access method for the old SourceBatchEntryCollection  property.
    *
    * @return   the current value of the SourceBatchEntryCollection  as collection
    */
    public Collection<Object>  getSourceBatchEntryCollection()
    {
      return this.sourceBatchEntryCollection;
    }

    /**
    * Sets the value of the old SupplementalCollection  property.
    *
    * @param the new value of the SupplementalCollection  as collection
    */
    public void setSupplementalCollection(Collection<ObservationVO> coll)
    {
      this.supplementalCollection  = coll;
    }

    /**
    * Access method for the old SupplementalCollection  property.
    *
    * @return   the current value of the SupplementalCollection  as collection
    */
    public Collection<ObservationVO>  getSupplementalCollection()
    {
      return this.supplementalCollection;
    }

    /**
    * Access method for the old SourceBatchEntry property.
    *
    * @param int the index
    * @return   the current value of the SourceBatchEntry as object
    */
    public BatchEntryHelper getSourceBatchEntry_s(int index)
    {
      // this should really be in the constructor
      if (this.sourceBatchEntryCollection  == null)
	  this.sourceBatchEntryCollection  = new ArrayList<Object> ();

      int currentSize = this.sourceBatchEntryCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
	try {
	  Object[] tempArray = this.sourceBatchEntryCollection.toArray();

	  Object tempObj  = tempArray[index];

	  BatchEntryHelper temp = (BatchEntryHelper) tempObj;

	  return temp;
	}
	catch (Exception e) {
	   //##!! System.out.println(e);
        } // do nothing just continue
      }

       BatchEntryHelper temp = null;

	for (int i = currentSize; i < index+1; i++)
	{
	  temp = new BatchEntryHelper();
	  this.sourceBatchEntryCollection.add(temp);
	}
	return temp;
    }
    
    
    public ArrayList<Object> getJurisdictionList(){
		return CachedDropDowns.getJurisdictionList();
	}
    public ArrayList<Object> getExportFacilityList(){
		return CachedDropDowns.getExportFacilityListForTransferOwnership();
	}
    
    public String getExportJurisdictionList(){
    	Collection<Object>  coll = CachedDropDowns.getExportJurisdictionList();
    	StringBuffer buff = new StringBuffer();
    	if(coll!=null && coll.iterator()!=null){
    		java.util.Iterator<Object> it = coll.iterator();
    		while(it.hasNext()){
    			DropDownCodeDT dropdownDT= (DropDownCodeDT)it.next();
    			buff.append(dropdownDT.getKey() + "|");
    			
    		}
    	}
    	return buff.toString();
	}

  /**
    * Access method for the old SourceBatchEntry property.
    *
    * @param int the index
    * @return   the current value of the Supplemental as ObservationVO object
    */
    public ObservationVO getSupplemental_s(int index)
    {
      // this should really be in the constructor
      if (this.supplementalCollection  == null)
	  this.supplementalCollection  = new ArrayList<ObservationVO> ();

      int currentSize = this.supplementalCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
	try {
	  Object[] tempArray = this.supplementalCollection.toArray();

	  Object tempObj  = tempArray[index];

	  ObservationVO temp = (ObservationVO) tempObj;

	  return temp;
	}
	catch (Exception e) {
	   //##!! System.out.println(e);
        } // do nothing just continue
      }

       ObservationVO temp = null;

	for (int i = currentSize; i < index+1; i++)
	{
	  temp = new ObservationVO();
	  temp.getTheObservationDT().setCtrlCdDisplayForm(NEDSSConstants.supplemental);
	  this.supplementalCollection.add(temp);
	}
	return temp;
    }
    /**
     * getPatient to retrieve revision patient for Invg.
     */
    public PersonVO getPatient() {
       if (this.patient == null)
        this.patient = new PersonVO();
      return this.patient;
    }

    /**
     * setPatient to persist revision patient for Invg.
     */
    public void setPatient(PersonVO patient)
    {
      this.patient = patient;
    }

    /**
     * getOldRevision to retrieve old revision patient for Invg.
     */
    public PersonVO getOldRevision() {
      return this.oldRevision;
    }

    /**
     * setPatient to persist revision patient for Invg.
     */
    public void setOldRevision(PersonVO patient)
    { 
      this.oldRevision = patient;
    }

    /**
     * getObservationMap to retrieve old  mapping of Observation to cd.
     */
    public TreeMap<Object,Object> getObservationMap() {
      return this.observationMap;
    }

    /**
     * setObservationMap to store mapping of Observation to cd.
     */
    public void setObservationMap(TreeMap<Object,Object> aObservationMap)
    {
      this.observationMap = aObservationMap;
    }

	public Map<Object,Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map attributeMap) {
		this.attributeMap = attributeMap;
	}
	public Map<Object,Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}
	public void setSearchCriteriaArrayMap(Map<Object,Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}
	public Map<Object,Object> getSearchMap() {
		return searchMap;
	}
	public void setSearchMap(Map<Object,Object> searchMap) {
		this.searchMap = searchMap;
	}
	public NotificationSummaryVO getNotificationSummaryVO() {
		return notificationSummaryVO;
	}
	public void setNotificationSummaryVO(NotificationSummaryVO notificationSummaryVO) {
		this.notificationSummaryVO = notificationSummaryVO;
	}
	public ArrayList<Object> getDateFilterList() {
		return dateFilterList;
	}
	public void setDateFilterList(ArrayList<Object>  dateFilterList) {
		this.dateFilterList = dateFilterList;
	}
	public ArrayList<Object> getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(ArrayList<Object>  submittedBy) {
		this.submittedBy = submittedBy;
	}
	public ArrayList<Object> getConditions() {
		return conditions;
	}
	public void setConditions(ArrayList<Object>  conditions) {
		this.conditions = conditions;
	}
	public ArrayList<Object> getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(ArrayList<Object>  caseStatus) {
		this.caseStatus = caseStatus;
	}
	public ArrayList<Object> getType() {
		return type;
	}
	public void setType(ArrayList<Object>  type) {
		this.type = type;
	}
	public String getConfirmationMessage() {
		return confirmationMessage;
	}
	public void setConfirmationMessage(String confirmationMessage) {
		this.confirmationMessage = confirmationMessage;
	}
	public ArrayList<Object> getRecipient() {
		return recipient;
	}
	public void setRecipient(ArrayList<Object>  recipient) {
		this.recipient = recipient;
	}
	public ArrayList<Object> getRejectedQdateFilterList() {
		return rejectedQdateFilterList;
	}
	public void setRejectedQdateFilterList(ArrayList<Object>  rejectedQdateFilterList) {
		this.rejectedQdateFilterList = rejectedQdateFilterList;
	}
	public ArrayList<Object> getRejectedQsubmittedBy() {
		return rejectedQsubmittedBy;
	}
	public void setRejectedQsubmittedBy(ArrayList<Object>  rejectedQsubmittedBy) {
		this.rejectedQsubmittedBy = rejectedQsubmittedBy;
	}
	public ArrayList<Object> getRejectedQconditions() {
		return rejectedQconditions;
	}
	public void setRejectedQconditions(ArrayList<Object>  rejectedQconditions) {
		this.rejectedQconditions = rejectedQconditions;
	}
	public ArrayList<Object> getRejectedQcaseStatus() {
		return rejectedQcaseStatus;
	}
	public void setRejectedQcaseStatus(ArrayList<Object>  rejectedQcaseStatus) {
		this.rejectedQcaseStatus = rejectedQcaseStatus;
	}
	public ArrayList<Object> getRejectedQtype() {
		return rejectedQtype;
	}
	public void setRejectedQtype(ArrayList<Object>  rejectedQtype) {
		this.rejectedQtype = rejectedQtype;
	}
	public ArrayList<Object> getRejectedQrecipient() {
		return rejectedQrecipient;
	}
	public void setRejectedQrecipient(ArrayList<Object>  rejectedQrecipient) {
		this.rejectedQrecipient = rejectedQrecipient;
	}
	public ArrayList<Object> getRejectedQRejectedBy() {
		return rejectedQRejectedBy;
	}
	public void setRejectedQRejectedBy(ArrayList<Object>  rejectedQRejectedBy) {
		this.rejectedQRejectedBy = rejectedQRejectedBy;
	}
	
	/*public boolean removeNotifiSummaryVOFromColl(String notUID) {
		boolean removed = false;
		if(notifiSummaryVOColl != null && notifiSummaryVOColl.size() > 0) {
			Iterator iter = notifiSummaryVOColl.iterator();
			while(iter.hasNext()) {
				NotificationSummaryVO vo = (NotificationSummaryVO) iter.next();
				if(vo.getNotificationUid().compareTo(Long.valueOf(notUID)) == 0) {
					notifiSummaryVOColl.remove(vo);
					removed = true;
					break;
				}
			}
			
		}
		return removed;
	}	*/
	public void callChildForm(String filler) {
		
		   WebContext ctx = WebContextFactory.get();
		   HttpServletRequest req = ctx.getHttpServletRequest();
		   ManageCTAssociateForm childForm = (ManageCTAssociateForm)req.getSession().getAttribute("manageCTAssociateForm");
		   childForm.updateCheckboxIds(filler);
		
		
	}
	
	/**
	 * checkForExistingNotificationsByPublicHealthCaseUid: returns if there's any notification associated to the investigation.
	 * It is used from a DWR call.
	 * @param publicHealthCaseUid
	 * @return
	 */
	public boolean checkForExistingNotificationsByPublicHealthCaseUid(String publicHealthCaseUid){
		boolean isNotificationExist=false;
		try{
			WebContext context = WebContextFactory.get();
			HttpServletRequest request = context.getHttpServletRequest();
			
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
	        String sMethod = "checkForExistingNotificationsByPublicHealthUid";
	        
	        Long uid = null;
	        if(publicHealthCaseUid!=null){
	        	uid = Long.parseLong(publicHealthCaseUid);
	        }
	        Object[] oParams = new Object[] {uid};
	        Object obj = CallProxyEJB.callProxyEJB(oParams,sBeanJndiName,sMethod,request.getSession());
	        isNotificationExist = (boolean) obj;
		}catch(Exception ex){
			logger.error("Error in calling the checkForExistingNotificationsByPublicHealthCaseUid, actUidStr: "+publicHealthCaseUid+", Exception: "+ex.getMessage(),ex);
		}
		return isNotificationExist;
	}

}//InvestigationForm