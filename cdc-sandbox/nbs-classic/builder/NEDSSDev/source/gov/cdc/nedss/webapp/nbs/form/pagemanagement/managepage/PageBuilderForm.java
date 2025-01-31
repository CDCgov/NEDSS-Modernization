package gov.cdc.nedss.webapp.nbs.form.pagemanagement.managepage;

import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageElementVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.WaTemplateVO;
import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managequestion.ManageQuestionsForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;
import gov.cdc.nedss.pagemanagement.wa.dto.PageElementDTO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Struts Action Form to populate DynamicForm Metadata
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Comput5er Sciences Corporation</p>
 * DynamicModuleBuilderForm.java
 * Oct 6, 2009
 * @version
 */
public class PageBuilderForm extends BaseForm 
{
	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils(PageBuilderForm.class.getName());	
	PageManagementActionUtil pageManagementUtil = new PageManagementActionUtil();
	GenericQueueUtil genericQueueUtil = new GenericQueueUtil();

	/**
	 * This will contain a reference to the PageManagementProxyVO object
	 */
	Object selection = new Object();



	/**
	 * To hold the current step in the page builder process 
	 * (add, edit, view etc...)
	 */
	String actionMode =null;
	ArrayList<Object> pageList =null;

	//For Filtering/sorting
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	private ArrayList<Object> eventType = new ArrayList<Object> ();
	private ArrayList<Object> templateNm = new ArrayList<Object> ();
	private ArrayList<Object> relatedConditions = new ArrayList<Object> ();
	private ArrayList<Object> lastUpdated = new ArrayList<Object> ();
	private ArrayList<Object> lastUpdatedBy = new ArrayList<Object> ();
	private ArrayList<Object> status = new ArrayList<Object> ();
	private ArrayList<Object> dateFilterList = new ArrayList<Object> ();	
	private Collection<Object>  WaTemplateDTColl;
	private ArrayList<Object> messageIdList = new ArrayList<Object> ();
	private ArrayList<Object> conditionList = new ArrayList<Object> ();
	private ArrayList<Object> conditionAllList = new ArrayList<Object> (); //also inactive
	private String[] conditionCodes;
	private ArrayList<Object> selectedCondList = new ArrayList<Object> ();
	private String[] selectedConditionCodes;
	private Collection<Object>  waPageCondMappingDTColl;
	private ArrayList<Object> templateList =  new ArrayList<Object>();
	private ArrayList<Object> invPageList =  new ArrayList<Object>();
	private ArrayList<Object> portEventType = new ArrayList<Object> ();
	private ArrayList<Object> mappingStatus = new ArrayList<Object> ();
	private ArrayList<Object> portCondition = new ArrayList<Object> ();
	private ArrayList<Object> portDate = new ArrayList<Object> ();
	private Collection<Object> portPageList = new ArrayList<Object>();
	private Long currentPgUid;
	private QueueDT queueDT;
	private ArrayList<QueueColumnDT> queueCollection;
	private String stringQueueCollection;
	private String CLASS_NAME = "gov.cdc.nedss.page.ejb.portproxyejb.dt.ManagePageDT";
	
	public ArrayList<Object> getPortEventType() {
		return portEventType;
	}
	public void setPortEventType(ArrayList<Object> portEventType) {
		this.portEventType = portEventType;
	}
	public ArrayList<Object> getMappingStatus() {
		return mappingStatus;
	}
	public void setMappingStatus(ArrayList<Object> mappingStatus) {
		this.mappingStatus = mappingStatus;
	}
	public ArrayList<Object> getPortCondition() {
		return portCondition;
	}
	public void setPortCondition(ArrayList<Object> portCondition) {
		this.portCondition = portCondition;
	}
	public ArrayList<Object> getPortDate() {
		return portDate;
	}
	public void setPortDate(ArrayList<Object> portDate) {
		this.portDate = portDate;
	}
	public Collection<Object> getPortPageList() {
		return portPageList;
	}
	public void setPortPageList(Collection<Object> portPageList) {
		this.portPageList = portPageList;
	}
	public Long getCurrentPgUid() {
		return currentPgUid;
	}
	public void setCurrentPgUid(Long PgUid){
		this.currentPgUid=PgUid;
	}

	private ArrayList<Object> conditionCdDesc = new ArrayList<Object> ();

	public ArrayList<Object> getRelatedConditions() {
		return relatedConditions;
	}

	public void setRelatedConditions(ArrayList<Object> relatedConditions) {
		this.relatedConditions = relatedConditions;
	}

	public ArrayList<Object> getTemplateNm() {
		return templateNm;
	}

	public void setTemplateNm(ArrayList<Object> templateNm) {
		this.templateNm = templateNm;
	}
	public ArrayList<Object> getConditionCdDesc() {
		return conditionCdDesc;
	}

	public void setConditionCdDesc(ArrayList<Object> conditionCdDesc) {
		this.conditionCdDesc = conditionCdDesc;
	}

	public ArrayList<Object> getConditionList() {
		if((conditionList == null || conditionList != null && conditionList.size()==0) || (!this.getActionMode().equals(NEDSSConstants.CLONE_LOAD_ACTION)) && !this.getActionMode().equals(NEDSSConstants.PORT_LOAD_ACTION)){
			this.conditionList = CachedDropDowns.getConditionCode();
		}
		return conditionList;
	}

	public void setConditionList(ArrayList<Object> conditionList) {
		this.conditionList = conditionList;		
	}

	public ArrayList<Object> getConditionAllListByBO(String busObjType) {
		if(!this.getActionMode().equals(NEDSSConstants.CLONE_LOAD_ACTION)){
			this.conditionAllList = CachedDropDowns.getAvailableConditions(busObjType);
		}
		return conditionAllList;
	}

	public ArrayList<Object> getConditionAllList() {
		return conditionAllList;
	}

	public void setConditionAllList(ArrayList<Object> conditionAllList) {
		this.conditionAllList = conditionAllList;		
	}

	public String[] getConditionCodes() {
		return conditionCodes;
	}

	public void setConditionCodes(String[] conditionCodes) {
		this.conditionCodes = conditionCodes;
	}


	public ArrayList<Object> getSelectedCondList() {
		return selectedCondList;
	}

	public void setSelectedCondList(ArrayList<Object> selectedCondList) {
		this.selectedCondList = selectedCondList;
	}


	public String[] getSelectedConditionCodes() {
		return selectedConditionCodes;
	}

	public void setSelectedConditionCodes(String[] selectedConditionCodes) {
		this.selectedConditionCodes = selectedConditionCodes;
	}

	public Collection<Object> getWaTemplateDTColl() {
		return WaTemplateDTColl;
	}

	public void setWaTemplateDTColl(Collection<Object> waTemplateDTColl) {
		WaTemplateDTColl = waTemplateDTColl;
	}

	public Object getSelection() {
		return selection;
	}

	public void setSelection(Object selection) {
		this.selection = selection;
	}

	public String getActionMode() {
		return actionMode;
	}

	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	/**
	 * A DWR event handler that is responsible for providing the
	 * 	data set for rendering the initial UI for the page builder. 
	 */
	public String retrieveCurrentPage() throws Exception 
	{
		WebContext context = WebContextFactory.get();
		HttpServletRequest request = context.getHttpServletRequest();
		String returnJsonValue = "";
		Collection<PageElementDTO> pageElements = new ArrayList();
		try {
			// retrieve the collection of PageElementVOs
			PageManagementActionUtil pmaUtil = new PageManagementActionUtil();
			pageElements = pmaUtil.getPageElementDTOColl(request.getSession());

			//    		// CMK FIXME: remove this hard coded logic once the back end flags for
			//    		// isStandardized and isPublished are in place.
			//    		boolean isPublished = true;
			//    		for (PageElementDTO peDto : pageElements) {
			//    		    if (peDto.getPageElementUid() % 2 == 0) {
			//    		        if (isPublished) {
			//    		            peDto.setIsPublished("Y");
			//    		            isPublished = false;
			//    		        }
			//    		        else {
			//    		            peDto.setIsStandardized("Y");
			//    		            isPublished = true;
			//    		        }
			//    		    }
			//    		}
		}
		catch (Exception e){
			logger.error("Error on retriving the current page"+e.getMessage());
		}

		// convert the collection of PageElementVOs into equivalent JSON string
		Type typeOfSrc = new TypeToken<Collection<PageElementDTO>>(){}.getType();
		returnJsonValue = convertElementsToJSON(pageElements, typeOfSrc);

		// return the JSON value
		return returnJsonValue;
	}

	/**
	 * A DWR event handler to add the questions selected. Following steps are 
	 * performed in this method.
	 * 
	 * 1. All QuestionDT objects corresponding to the questionIds selected are
	 *     retrieved from the session.
	 * 2. PageElementVO objects are created for all the QuestionDT objects. The waElementUid
	 *     are randomly generated negative numbers.
	 * 3. PageElementVO objects created in step 2 are added to the PageManagementProxyVO objects
	 *     store in the user session.     
	 * 4. PageElementDTO objects are then created from the objects created in step 2. 
	 * 5. These lightweight DTO objects are later converted into a JSON string that
	 *     can be used to update the UI.  
	 * 
	 * @param qUIDCsv - a CSV (comma separate values) string of questions
	 * #return A String representation of the JSON questions object
	 */
	@SuppressWarnings("unchecked")
	public String addQuestionsToPMProxyVO(String qUIdCsv) throws Exception{
		String retVal = "";
		if (qUIdCsv.trim().length() > 0) {
			
			// 1. get the QuestionDT objects in session 
			WebContext context = WebContextFactory.get();
			HttpServletRequest request = context.getHttpServletRequest();
			PageManagementActionUtil pmaUtil = new PageManagementActionUtil();
			
			String numChild = (String)request.getSession().getAttribute("childEltsCount");
			
			// TODO This is for Batch entry
			String subSectionPageElementId = (String) request.getSession().getAttribute("SubsectionId");
			Integer groupSeqNbr = null;
			PageElementVO pageElementVO = new PageElementVO();
			PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
			for (Object peVo : pmpVo.getThePageElementVOCollection()) {
				Long val1 = ((PageElementVO) peVo).getPageElementUid();
				Long val2 = Long.parseLong(subSectionPageElementId);
				if (val1 != null && val2 != null && val1.equals(val2)) {
					pageElementVO = (PageElementVO) peVo;
					groupSeqNbr = pageElementVO.getWaUiMetadataDT().getQuestionGroupSeqNbr();
					break;
				}
			}
			// remove the attribute from the session
			request.getSession().removeAttribute("SubsectionId");
			request.getSession().removeAttribute("childEltsCount");
			Collection<WaQuestionDT> questionDTs = (Collection<WaQuestionDT>) NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSManageList);

			// 2. create PageElementVO objects for all the questions selected.
			Collection<Object> selectedElementsVOs = new ArrayList<Object>();
			String[] qUIdsArray = qUIdCsv.split(",");
			for (String id : qUIdsArray) {
				for (WaQuestionDT singleQ : questionDTs) {
					
					
					String id2 = String.valueOf(singleQ.getWaQuestionUid());
					if (id2.trim().equals(id.trim())//To avoid adding a note (nbs_component_uid = 1019) to a subsection with other questions
							&& !(singleQ.getNbsUiComponentUid()!=null && singleQ.getNbsUiComponentUid().toString().equalsIgnoreCase("1019") && !numChild.equalsIgnoreCase("0"))) {
						PageElementVO peVo = new PageElementVO();
						peVo.setPageElementUid(new Long(0));
						peVo.setWaQuestionDT(singleQ);

						try {
							// initialize the UI meta data DT with question DT data
							// and other fields
							WaUiMetadataDT uiMetadataDt = new WaUiMetadataDT(); 
							PropertyUtils.copyProperties(uiMetadataDt, singleQ);
							uiMetadataDt.setDescTxt(singleQ.getDescription());
							uiMetadataDt.setIsRequired(NEDSSConstants.FALSE);                          
							uiMetadataDt.setIsRequiredInMessage(NEDSSConstants.FALSE);                          
							uiMetadataDt.setDisplayInd(NEDSSConstants.TRUE);
							uiMetadataDt.setEnableInd(NEDSSConstants.TRUE);
							uiMetadataDt.setRequiredInd(NEDSSConstants.FALSE);
							uiMetadataDt.setReportLabel(singleQ.getReportAdminColumnNm());
							uiMetadataDt.setNbsUiComponentUid(singleQ.getNbsUiComponentUid());
							//TODO assign groupUid to the new question
							if(groupSeqNbr != null)
								uiMetadataDt.setQuestionGroupSeqNbr(groupSeqNbr);
							// add the UI meta data DT to the element VO
							pmaUtil.updateQuestionMetadataWhileAddingOnPage(pmpVo.getWaTemplateDT().getBusObjType(), uiMetadataDt, request);
							peVo.setWaUiMetadataDT(uiMetadataDt);

						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						selectedElementsVOs.add(peVo);
						break;
					}
				
				}
			}
			//Rearrange selectedElementsVOs depending on questionIdentifier
			ArrayList<Object> as = new ArrayList<Object>(selectedElementsVOs);  
			Collections.sort( as , new Comparator<Object>() {   
				public int compare( Object o1 , Object o2 )   
				{   
					PageElementVO c1 = (PageElementVO) o1;
					PageElementVO c2 = (PageElementVO) o2; 
					String first = (String)c1.getWaUiMetadataDT().getQuestionIdentifier();   
					String second = (String)c2.getWaUiMetadataDT().getQuestionIdentifier();   
					return first.compareTo( second );   
				}   
			}); 

			// 3. update the PageManagementProxyVO object in session
			
			selectedElementsVOs = pmaUtil.addPageElementsToPMProxyVO(request, as);

			// 4. get the PageElementDTOs for the VO's above.
			Collection<PageElementDTO> selectedElementsDTOs = 
					pmaUtil.getPageElementDTOsFromElementVOColl(selectedElementsVOs); 

			// 5. generate the JSON
			Gson gson = new GsonBuilder()
					.serializeNulls()
					.setDateFormat(DateFormat.LONG)
					.setPrettyPrinting()
					.create();
			Type typeOfSrc = new TypeToken<Collection<PageElementDTO>>(){}.getType();
			retVal = gson.toJson(selectedElementsDTOs, typeOfSrc);
		}
		return retVal;
	}

	/**
	 * Check to see if question is associated with rules either source or target
	 * @return
	 */
	public String checkRulesAccociation(String elementId) throws Exception
	{
		WebContext context = WebContextFactory.get();
		HttpServletRequest request = context.getHttpServletRequest();
		PageManagementActionUtil pmaUtil = new PageManagementActionUtil();
		String associatedRules = pmaUtil.checkAssociatedRulesForQuestionId(request, elementId);
		if (associatedRules != null && !associatedRules.isEmpty()) {
			return associatedRules;
		}
		else {
			return null;
		}
	}

	/**
	 * Delete the page element from the PageManagementProxyVO
	 * @return
	 */
	public String deletePageElement(String elementId) throws Exception
	{
		WebContext context = WebContextFactory.get();
		HttpServletRequest request = context.getHttpServletRequest();
		PageManagementActionUtil pmaUtil = new PageManagementActionUtil();
		boolean deleteSuccess = pmaUtil.deletePageElementInPMProxyVO(request, elementId);
		if (deleteSuccess) {
			return "deleteSuccess";
		}
		else {
			return "deleteFailure";
		}
	}

	/**
	 * Save the PageManagementProxtVO stored in session.
	 * @param pageElementsOrder
	 * @return
	 */
	public String savePageAsDraft(String pageElementsOrderCsv)
	{
		Long draftId = null;
		String successMsg = "Draft was saved successfully.";
		PageManagementActionUtil pmaUtil = new PageManagementActionUtil();
		try{
			draftId = pmaUtil.savePageAsDraft(null, pageElementsOrderCsv,null);
		}catch(Exception e){
			logger.error("Error while submitting the edit PAgeBuilder from PageBuilderForm"+e.getMessage());
		}
		if(draftId != null && draftId > 0)
			return successMsg;
		else {
			return ("Error occurred while saving this page as a Draft.");
		}
	}

	/**
	 * Save the page data (PageManagementProxyVO object in session)
	 * as a template.
	 * @param pageElementsOrder
	 * @return
	 */
	public String savePageAsTemplate(String pageElementsOrderCsv)
	{
		Long templateId = null;
		PageManagementActionUtil pmaUtil = new PageManagementActionUtil();
		try{
			// TODO: Call the appropriate method in the PageManagementActionUtil
			// to save this page as a template. The function called will 
			// return true if the page was successfully saved as a template
			// or false otherwise
			String templInd = "true";
			templateId = pmaUtil.savePageAsDraft(null, pageElementsOrderCsv,templInd);
		}
		catch(Exception e){
			logger.error("Error while saving this page as a template." + e.getMessage());
		}

		if (templateId != null && templateId > 0) {
			return ("Page was successfully saved as a template.");
		}
		else {
			return ("Error occurred while saving this page as a template.");
		}
	}

	public static String convertElementsToJSON(Collection elements, Type typeOfElement) 
	{
		String retVal = "";
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setDateFormat(DateFormat.LONG)
				.setPrettyPrinting()
				.create();
		retVal = gson.toJson(elements, typeOfElement);
		return retVal;
	}

	public ArrayList<Object> getConditionCode(){
		ArrayList<Object> list = CachedDropDowns.getConditionCode();
		return list;
	}


	public ArrayList<Object> getTemplateListByBO(String busObjType){

		if(!this.getActionMode().equals(NEDSSConstants.CLONE_LOAD_ACTION)){
			templateList = CachedDropDowns.getActiveTemplates(busObjType);
		}else if(this.getActionMode().equals(NEDSSConstants.CLONE_LOAD_ACTION)){
			templateList = new ArrayList<Object>(1);
			WaTemplateVO waTemplateVO = (WaTemplateVO)this.getSelection();
			DropDownCodeDT ddDT = new DropDownCodeDT();
			ddDT.setKey(waTemplateVO.getWaTemplateDT().getWaTemplateUid().toString());
			ddDT.setValue(waTemplateVO.getWaTemplateDT().getPageNm());
			templateList.add(ddDT);			
		}
		return templateList;
	}

	public void setTemplateList(ArrayList<Object> templateList) {
		this.templateList = templateList;
	}

	public ArrayList<Object> getBusObjTypeColl(){
		DropDownCodeDT ddDT = new DropDownCodeDT();
		ddDT.setKey("INV");
		ddDT.setValue("Investigation");
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(ddDT);
		return list;
	}

	public ArrayList<Object> getPageList() {
		return pageList;
	}

	public void setPageList(ArrayList<Object>  pageList) {
		this.pageList = pageList;
	}

	public ArrayList<Object> getEventType() {
		return eventType;
	}

	public void setEventType(ArrayList<Object> eventType) {
		this.eventType = eventType;
	}

	public ArrayList<Object> getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(ArrayList<Object> lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public ArrayList<Object> getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(ArrayList<Object> lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public ArrayList<Object> getStatus() {
		return status;
	}

	public void setStatus(ArrayList<Object> status) {
		this.status = status;
	}

	public Map<Object,Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}
	public void setSearchCriteriaArrayMap(Map<Object,Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}	
	public ArrayList<Object> getStartDateDropDowns(){
		return dateFilterList;
	}	
	public void initializeDropDowns() throws Exception {
		QueueUtil queueUtil = new QueueUtil();		
		lastUpdated = queueUtil.getStartDateDropDownValues();
		lastUpdatedBy = pageManagementUtil.getLastUpdatedBy(WaTemplateDTColl);
		status = pageManagementUtil.getPageStatus(WaTemplateDTColl);
		eventType = pageManagementUtil.getEventType(WaTemplateDTColl);
		String codeSet ="NBS_MSG_PROFILE";
		this.setMessageIdList(CachedDropDowns.getCachedDropDownList(codeSet) );
	}
	
	public void initializeDropDowns(Collection<Object> observationColls) {
		
		ArrayList<ArrayList<Object>> dropdownsToInitialize = new ArrayList<ArrayList<Object>>();


		
		dropdownsToInitialize = genericQueueUtil.initializeDropdowns(observationColls, queueDT, CLASS_NAME);
		
		portEventType=dropdownsToInitialize.get(0);
		mappingStatus=dropdownsToInitialize.get(1);
		portCondition=dropdownsToInitialize.get(2);
		portDate=dropdownsToInitialize.get(3);

	}

	public String[] getAnswerArray(String key) {
		return (String[])searchCriteriaArrayMap.get(key);
	}

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

	public ArrayList<Object> getDateFilterList() {
		return dateFilterList;
	}

	public void setDateFilterList(ArrayList<Object> dateFilterList) {
		this.dateFilterList = dateFilterList;
	}

	public ArrayList<Object> getMessageIdList() {
		return CachedDropDowns.getCodedValueForType("NBS_MSG_PROFILE");
	}

	public void setMessageIdList(ArrayList<Object> messageIdList) {
		this.messageIdList = messageIdList;
	}

	public ArrayList<Object> getUniqueTemplateNm(String templateNm, String templateType) throws Exception{
		PageManagementActionUtil util = new PageManagementActionUtil();
		ArrayList<Object> errorList = null;
		if(templateNm == null || templateNm.equals(""))
			return null;
		try{
			errorList  = util.validateTemplateNmUniqueness(templateNm,templateType);
		}catch (Exception ex){
			logger.error("Error while checking the uniqueness of template Name :"+ex.getMessage());
			throw new Exception(ex.getMessage());
		}
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		request.setAttribute("errors",errorList);
		return errorList;
	}	
	public ArrayList<Object> getUniquePageNm(String pageNm, String templateType) throws Exception{
		PageManagementActionUtil util = new PageManagementActionUtil();
		ArrayList<Object> errorList = null;
		if(templateNm == null || templateNm.equals(""))
			return null;
		Long selectionUid = 0L;
		WaTemplateVO thisSelection = (WaTemplateVO) this.getSelection();
		if (thisSelection.getWaTemplateDT() != null)
			if (thisSelection.getWaTemplateDT().getWaTemplateUid() != null)
				selectionUid = thisSelection.getWaTemplateDT().getWaTemplateUid();
		try{
			errorList  = util.validatePageNmUniqueness(pageNm, selectionUid,errorList, templateType);
		}catch (Exception ex){
			logger.error("Error while checking the uniqueness of the Page Name :"+ex.getMessage());
			throw new Exception(ex.getMessage());
		}
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		request.setAttribute("errors",errorList);
		return errorList;
	}	

	public void clearAll() {
		getAttributeMap().clear();
		conditionCdDesc = new ArrayList();
		conditionList = new ArrayList();
		conditionAllList = new ArrayList();
		templateNm = new ArrayList();
		relatedConditions = new ArrayList();
		selectedCondList = new ArrayList();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
		invPageList = new ArrayList();
	}

	public void updateBatchUidForBatchEntry(String elementIds) throws Exception
	{
		WebContext context = WebContextFactory.get();
		HttpServletRequest request = context.getHttpServletRequest();
		PageManagementActionUtil pmaUtil = new PageManagementActionUtil();

		pmaUtil.updateBatchUidForBatchEntry(request,elementIds);
	}


	public void updateBatchUidForUnBatch(String elementIds) throws Exception
	{
		WebContext context = WebContextFactory.get();
		HttpServletRequest request = context.getHttpServletRequest();
		PageManagementActionUtil pmaUtil = new PageManagementActionUtil();

		pmaUtil.updateBatchUidForUnbatch(request,elementIds);
	}

	public void updateBatchUidRelocatedFieldRow(String sourceSubsectionEltId,String sourceChildEltsCount, String targetElementId, String sourceElementId)
			throws Exception{
		WebContext context = WebContextFactory.get();
		HttpServletRequest request = context.getHttpServletRequest();
		PageManagementActionUtil pmaUtil = new PageManagementActionUtil();

		pmaUtil.updateBatchUidForRelocatedFieldRow(request,sourceSubsectionEltId, sourceChildEltsCount, targetElementId, sourceElementId);
	}

	public void updateBatchUidForDraggedFieldRow(String targetElementId, String sourceElementId) throws Exception{

		WebContext context = WebContextFactory.get();
		HttpServletRequest request = context.getHttpServletRequest();
		PageManagementActionUtil pmaUtil = new PageManagementActionUtil();

		//pmaUtil.updateBatchUidForDraggedFieldRow(request, targetElementId, sourceElementId);
	}

	public void updateBatchUidForImportedFieldRow(String fieldElementIds, String sourceSubsectionElementId) throws Exception{

		WebContext context = WebContextFactory.get();
		HttpServletRequest request = context.getHttpServletRequest();
		PageManagementActionUtil pmaUtil = new PageManagementActionUtil();

		pmaUtil.updateBatchUidForImportedFieldRow(request,fieldElementIds, sourceSubsectionElementId);
	}

	public Collection<Object> getWaPageCondMappingDTColl() {
		return waPageCondMappingDTColl;
	}

	public void setWaPageCondMappingDTColl(
			Collection<Object> waPageCondMappingDTColl) {
		this.waPageCondMappingDTColl = waPageCondMappingDTColl;
	}
	/**
	 * Get a list of values for a given key
	 * @param key
	 * @return
	 */
	public ArrayList <Object> getCodedValue(String key) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(key);
		return aList;
	}


	// Gets List of Investigation pages.
	public ArrayList<Object> getInvPageList() {
		invPageList = new ArrayList();
	
		for(int i=0;i<pageList.size();i++){
			WaTemplateDT waDT = (WaTemplateDT)pageList.get(i);
			//WaTemplateVO waVO = new WaTemplateVO();
			
			if("Investigation".equalsIgnoreCase(waDT.getBusObjType())){
                  if(!currentPgUid.equals(waDT.getWaTemplateUid())){
                	
                	  
				invPageList.add(waDT);
              		
			}
                  }
			}
			
			return invPageList;
	
	}
	public void setInvPageList(ArrayList<Object> invPageList) {
		this.invPageList = invPageList;
	}
	

	/*The following method gets the MMG and Page Description of TO PAGE By UID*/
	public ArrayList<String> getMessageAndDescByUid(Long uid){
			String mmg="";
			String descTxt="";
		ArrayList<String> mmgAndDesc = new ArrayList<String>();
			WaTemplateVO waTemplateVO = new WaTemplateVO();
			
	       PageManagementActionUtil util = new PageManagementActionUtil();
	       WebContext context = WebContextFactory.get();
	        HttpServletRequest request = context.getHttpServletRequest();
	       HttpSession session = request.getSession();
	       try{
	              waTemplateVO = util.getPageDetails(uid, session);
	       }catch(Exception e){
	              logger.error("Error in calling the getPageDetails "+e.getMessage(),e);
	       }
	       
	       if(waTemplateVO!=null){
	              mmg = waTemplateVO.getWaTemplateDT().getMessageId();
	              mmgAndDesc.add(mmg);
	       
	        descTxt= waTemplateVO.getWaTemplateDT().getDescTxt();
	        if(descTxt==null){
	        	mmgAndDesc.add("");
	        }else{
	        mmgAndDesc.add(descTxt);
	       }
	   
	       }
	        return mmgAndDesc;
	}
	
	/*This method is for getting related conditions for To page*/
	public ArrayList<Object> getConditionListByUid(Long Uid){                    
		if(this.getActionMode().equals(NEDSSConstants.PORT_LOAD_ACTION)){
			this.conditionAllList=CachedDropDowns.getConditionDropDownForTemplate(Uid);
		}
		return conditionAllList;
	}
	
	public QueueDT getQueueDT() {
		return queueDT;
	}
	public void setQueueDT(QueueDT queueDT) {
		this.queueDT = queueDT;
	}
	public ArrayList<QueueColumnDT> getQueueCollection() {
		return queueCollection;
	}
	public void setQueueCollection(ArrayList<QueueColumnDT> queueCollection) {
		this.queueCollection = queueCollection;
	}
	public String getStringQueueCollection() {
		return stringQueueCollection;
	}
	public void setStringQueueCollection(String stringQueueCollection) {
		this.stringQueueCollection = stringQueueCollection;
	}
	public String getCLASS_NAME() {
		return CLASS_NAME;
	}
	public void setCLASS_NAME(String cLASS_NAME) {
		CLASS_NAME = cLASS_NAME;
	}

	/**
	 * @param busObjType
	 * @return waTemplateUid
	 * @throws Exception
	 */
	public Long findPageExistenceByBusinessObjType(String busObjType) throws Exception{
		Long waTemplateUid = null;
		try{
			WebContext context = WebContextFactory.get();
			HttpServletRequest request = context.getHttpServletRequest();
			PageManagementActionUtil util = new PageManagementActionUtil();
			waTemplateUid = util.findPageExistenceByBusinessObjType(busObjType,request);		
		}catch(Exception ex){
			logger.error("Error findWatemplateUidByBusinessObjType :"+ex.getMessage(), ex);
			throw new Exception(ex.getMessage());
		}
		return waTemplateUid;
	}

	
}