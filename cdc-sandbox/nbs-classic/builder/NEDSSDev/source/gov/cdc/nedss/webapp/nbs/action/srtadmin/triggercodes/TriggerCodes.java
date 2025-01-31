package gov.cdc.nedss.webapp.nbs.action.srtadmin.triggercodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt.TriggerCodesDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminUtil;
import gov.cdc.nedss.webapp.nbs.action.triggercodes.util.ImpAndExpConstants;
import gov.cdc.nedss.webapp.nbs.action.triggercodes.util.TriggerCodesUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.triggerCodes.TriggerCodeForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.queue.GenericForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;


public class TriggerCodes extends DispatchAction {
	static final LogUtils logger = new LogUtils(TriggerCodes.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";	
	private String className ="gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt.TriggerCodesDT";
	TriggerCodesUtil rsUtil = new TriggerCodesUtil();
	PropertyUtil properties = PropertyUtil.getInstance();
	QueueUtil queueUtil = new QueueUtil();
	GenericQueueUtil genericQueueUtil = new GenericQueueUtil();

	/**
	 * manageLoad: method called from the link in System management to show the Manage Trigger Codes for Case Reporting page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward manageLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		TriggerCodeForm triggerCodesForm = (TriggerCodeForm) form;
		clearTriggerCodesData(triggerCodesForm);
		request.setAttribute("accessMode", "");
		triggerCodesForm.setActionMode("");
		triggerCodesForm.clearAll();
        request.setAttribute("displayTable","display:none");
		request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.MANAGE_TRIGGER_CODES);
	
		//return (mapping.findForward("default"));
		return PaginationUtil.paginate(triggerCodesForm, request, "default",mapping);
	}
	
	
	/**
	 * clearTriggerCodesData: this method removes the previous data entered in the dropdowns/text boxes
	 * @param triggerCodesForm
	 */
	private void clearTriggerCodesData(TriggerCodeForm triggerCodesForm){
		triggerCodesForm.setCodingSystemSelected("");
		triggerCodesForm.setCodeSelected("");
		triggerCodesForm.setDisplayNameOperatorSelected("");
		triggerCodesForm.setDisplayNameSelected("");
		triggerCodesForm.setDefaultConditionSelected("");	
	}
	
	/**
	 * searchLabSubmit: this method submits the search based on the criteria selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward searchLabSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		try {
			request.setAttribute("displayTable","display:");
	
			TriggerCodeForm triggerCodeForm = (TriggerCodeForm) form;
			triggerCodeForm.setActionMode(null);
			String whereClause = SRTAdminUtil.triggerCodesSrchWhereClause(triggerCodeForm);	
			request.setAttribute("whereClause", whereClause);
			triggerCodeForm.getAttributeMap().clear();			
			Object[] searchParams = {whereClause};
				
			Object[] oParams = new Object[] { JNDINames.TRIGGER_CODES_RESULT_DAO_CLASS, "getTriggerCodeResultDTCollection", searchParams };
			ArrayList<Object> dtList = (ArrayList<Object> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			
			if(dtList.size() > 0) {
				Iterator<Object> iter = dtList.iterator();
				while(iter.hasNext()) {
					TriggerCodesDT dt = (TriggerCodesDT) iter.next();

					dt.setCodingSystem(dt.getCodingSystem());
					dt.setCodeColumn(dt.getCode());
					if(dt.getCodeDescTxt()!= null)
						dt.setDisplayName(dt.getCodeDescTxt());
					if(dt.getDiseaseNm()!= null &&  dt.getConditionCd()!=null){
						dt.setCondition(dt.getDiseaseNm()+" ("+ dt.getConditionCd()+")");
						dt.setConditions(dt.getDiseaseNm());
					}
					if(dt.getProgramArea()!= null){
						dt.setProgramArea(dt.getProgramArea());
					}
					dt.setStatus(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
					}
				
					QueueDT queueDT = fillQueueDT();
					triggerCodeForm.setQueueDT(queueDT);
				    ArrayList<QueueColumnDT> queueCollection = genericQueueUtil.convertQueueDTToList(queueDT);
				    triggerCodeForm.setQueueCollection(queueCollection);
				    String queueString = genericQueueUtil.convertToString(queueDT);
				    triggerCodeForm.setStringQueueCollection(queueString);
				    request.setAttribute("queueCollection",queueCollection);
				    request.setAttribute("stringQueueCollection",queueString);	
				
			}else {
				triggerCodeForm.getAttributeMap().put("NORESULT","NORESULT");
				triggerCodeForm.getAttributeMap().put("NoResultTable","NoResultTable");
				return (mapping.findForward("default"));
			}
			triggerCodeForm.setManageList(dtList);
			triggerCodeForm.getAttributeMap().put("manageList",dtList);
			triggerCodeForm.getAttributeMap().put("resultList",dtList);
		} catch (Exception e) {
			logger.error("Exception in searchLabSubmit: " + e.getMessage());
			e.printStackTrace();
			SRTAdminUtil.handleErrors(e, request, "search", "");
			return (mapping.findForward("default"));

		}finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_TRIGGER_CODES);
		}	
		
		return (mapping.findForward("results"));
	}	

	/**
	 * resultsLoad: this method shows the results in the table
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward resultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		boolean forceEJBcall = false;
		TriggerCodeForm triggerCodeForm = (TriggerCodeForm) form;
		ArrayList<Object> resultList = (ArrayList<Object> ) triggerCodeForm.getAttributeMap().get("resultList");
		Map<Object, Object> searchCriteria = (TreeMap<Object,Object>) triggerCodeForm.getAttributeMap().get("searchCriteria");
		boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
		
		if (initLoad && !PaginationUtil._dtagAccessed(request)) {
			triggerCodeForm.clearAll();
			forceEJBcall = true;		
		}
		String cnxt = request.getParameter("context");
		
		if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {		
			request.setAttribute("accessMode", "cancel");
			triggerCodeForm.setActionMode("cancel");
		}
		
		if(cnxt != null && cnxt.equalsIgnoreCase("removefilters")) {		
			triggerCodeForm.setManageList(resultList);
		} 
		
		Integer queueSize = properties.getQueueSize(NEDSSConstants.MANAGE_TRIGGERCODES_QUEUE_SIZE);
		triggerCodeForm.getAttributeMap().put("queueSize", queueSize);
		//To make sure SelectAll is checked, see if no criteria is applied
		if(triggerCodeForm.getSearchCriteriaArrayMap().size() == 0)
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
	
		Collection<Object>  displayVOColl  = new ArrayList<Object> ();
		
		try{
			HttpSession session = request.getSession(true);
			ArrayList<Object> recFacilityList = new ArrayList<Object> ();
			GenericForm genericForm= translateFromObservationSummaryDisplayVOToObservationSummaryDisplayVO(triggerCodeForm);
			if(forceEJBcall){
				recFacilityList = triggerCodeForm.getManageList();
				displayVOColl = (Collection<Object>) recFacilityList;
				triggerCodeForm.setTriggerCodesDTColl(displayVOColl);
				genericForm.setElementColl(displayVOColl);
				triggerCodeForm.initializeDropDowns(triggerCodeForm.getTriggerCodesDTColl(), genericForm, triggerCodeForm.getCLASS_NAME());
				Map<Object, Object> map = genericQueueUtil.setQueueCount(genericForm, genericForm.getQueueDT());
				triggerCodeForm.setAttributeMap(map);	
			}
			else {
				recFacilityList = (ArrayList<Object> ) triggerCodeForm.getAttributeMap().get("manageList");
			}
			setSortOrderAndMethod(genericForm);
			displayVOColl = (Collection<Object>) recFacilityList;
			
			boolean existing = request.getParameter("existing") == null ? false : true;
			boolean fromEdit = request.getAttribute("FromEdit") == null ? false : true;
			
			//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
			
			if(fromEdit)
				genericForm.setElementColl(recFacilityList);
		
			if(existing || fromEdit) {
			Collection<Object>  filteredColl = genericQueueUtil.filterQueue(genericForm, request, className);
				if(filteredColl != null){
					recFacilityList = (ArrayList<Object> ) filteredColl;
					displayVOColl = filteredColl;
				}	
				genericQueueUtil.sortQueue(genericForm, displayVOColl, true, request,"getCodeColumn");
			}
			else {
				genericQueueUtil.filterQueue(genericForm, request, className);
				genericQueueUtil.sortQueue(genericForm, displayVOColl, existing, request,"getCodeColumn");
			}
			
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel") && !existing) 	
					triggerCodeForm.getAttributeMap().put("searchCriteria", searchCriteria);
	
			request.setAttribute("queueCount", String.valueOf(displayVOColl.size()));
			triggerCodeForm.getAttributeMap().put("queueCount", String.valueOf(displayVOColl.size()));
			
			if(recFacilityList.size()== 0)
				triggerCodeForm.getAttributeMap().put("NoResultTable","NoResultTable");
			
			triggerCodeForm.getAttributeMap().put("manageList",recFacilityList);
			request.setAttribute("manageList", recFacilityList);
			triggerCodeForm.getAttributeMap().put("resultList",resultList);
			triggerCodeForm.setManageList(recFacilityList);
		} catch (Exception e) {
			logger.error("Exception in resultsLoad: " + e.getMessage());
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
		} finally {
			request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.MANAGE_TRIGGER_CODES);
		}
	
		
		if(triggerCodeForm.getQueueCollection()==null){//Add new code before searching
	
			QueueDT queueDT = fillQueueDT();
			triggerCodeForm.setQueueDT(queueDT);
		    ArrayList<QueueColumnDT> queueCollection = genericQueueUtil.convertQueueDTToList(queueDT);
		    triggerCodeForm.setQueueCollection(queueCollection);
		    String queueString = genericQueueUtil.convertToString(queueDT);
		    triggerCodeForm.setStringQueueCollection(queueString);
		    request.setAttribute("queueCollection",queueCollection);
		    request.setAttribute("stringQueueCollection",queueString);
		}
		   request.setAttribute("queueCollection",triggerCodeForm.getQueueCollection());
	       request.setAttribute("stringQueueCollection",triggerCodeForm.getStringQueueCollection());
	
	       int queueLength = 0;
	       
	       if(displayVOColl!=null)
	    		   queueLength = displayVOColl.size();
	       
	       if(queueLength==0)
	    	   request.setAttribute("displayTable","display:none");
			
	       if(triggerCodeForm.getAttributeMap().get("whereClause")==null)
	    		   triggerCodeForm.getAttributeMap().put("whereClause",request.getAttribute("whereClause"));
	      
	       return PaginationUtil.paginate(triggerCodeForm, request, "default",mapping);
	}
	
	/**
	 * createLoadTriggerCode: this method shows the Add new trigger code section
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createLoadTriggerCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
			
		TriggerCodeForm triggerCodeForm = (TriggerCodeForm) form;
		try{

			triggerCodeForm.resetSelection();
			triggerCodeForm.setSelection(new TriggerCodesDT());
			triggerCodeForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			triggerCodeForm.setPageTitle(ImpAndExpConstants.ADD_CODE, request);
			request.setAttribute("accessMode", "createTriggerCode");
			String nbsUid = request.getParameter("nbsUid");
			
			triggerCodeForm.getAttributeMap().put("cancel", "/nbs/TriggerCodes.do?method=resultsLoad&context=cancel");
			triggerCodeForm.getAttributeMap().put("Create", "/nbs/TriggerCodes.do?method=createTriggerCode&nbsUid="+nbsUid+"#expAlg");
		}catch (Exception e){
			request.setAttribute("error", e.getMessage());
				}
		finally {
			request.setAttribute("queueCollection",triggerCodeForm.getQueueCollection());
			 
	        request.setAttribute("stringQueueCollection",triggerCodeForm.getStringQueueCollection());
			request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.ADD_CODE);
		}
		Collection<Object>  triggerCodeList = triggerCodeForm.getManageList();
		Collection<?>  queueList = (ArrayList<?> ) triggerCodeForm.getAttributeMap().get("manageList");
		
		if(queueList!= null){
			request.setAttribute("manageList",queueList);
			
		}
		else {
		request.setAttribute("manageList", triggerCodeList);
		}
		return PaginationUtil.paginate(triggerCodeForm, request, "create",mapping);	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	
	public ActionForward filterTriggerCodesSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	
		TriggerCodeForm triggerCodeForm  = (TriggerCodeForm) form;
		
		//GENERIC QUEUE
		GenericForm genericForm= translateFromObservationSummaryDisplayVOToObservationSummaryDisplayVO(triggerCodeForm);
		//\GENERIC QUEUE
		
		Collection<Object>  recFacilityList = genericQueueUtil.filterQueue(genericForm, request, className);
		if(recFacilityList != null){
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
		//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
		}else{
			recFacilityList = (ArrayList<Object> ) triggerCodeForm.getTriggerCodesDTColl();
		}
		triggerCodeForm.getAttributeMap().put("manageList",recFacilityList);
		triggerCodeForm.getAttributeMap().put("queueList",recFacilityList);
		request.setAttribute("queueList", recFacilityList);
		request.setAttribute("manageList", recFacilityList);
		request.setAttribute("queueCount", String.valueOf(recFacilityList.size()));
		triggerCodeForm.getAttributeMap().put("queueCount", String.valueOf(recFacilityList.size()));
		request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.MANAGE_TRIGGER_CODES);
		triggerCodeForm.getAttributeMap().put("PageNumber", "1");
		if(recFacilityList.size()== 0)
			triggerCodeForm.getAttributeMap().put("NoResultTable","NoResultTable");
	    request.setAttribute("queueCollection",triggerCodeForm.getQueueCollection());
	    request.setAttribute("stringQueueCollection",triggerCodeForm.getStringQueueCollection());
	    
		return PaginationUtil.paginate(triggerCodeForm, request, "default",mapping);
	} 
	
	/**
	 * fillQueueDT: method used from the generic queue to create the queueDT
	 * @return
	 */
	public QueueDT fillQueueDT(){
	
	    //ArrayList<QueueColumnDT> queueDTcollection = new ArrayList<QueueColumnDT>();
	
	    QueueDT queueDT = new QueueDT();
	    QueueColumnDT queue = new QueueColumnDT();
	
	    //First column: Application Name
	    queue = new QueueColumnDT();
	
	    queue.setColumnId("column1");
	
	    queue.setColumnName("Coding System");
	
	    queue.setBackendId("codingSystemBackendId");//??
	
	    queue.setDefaultOrder("ascending");
	
	    queue.setSortable("true");
	
	    queue.setSortNameMethod("getCodingSystem");
	
	    queue.setMedia("html pdf csv");
	
	    queue.setMediaHtmlProperty("codingSystem");
	
	    queue.setMediaPdfProperty("codingSystem");
	
	    queue.setMediaCsvProperty("codingSystem");
	
	    queue.setColumnStyle("width:10%");
	
	    queue.setFilterType("2");//0 date, 1 text, 2 multiselect
	
	    queue.setDropdownProperty("CODINGSYSTEM");
	
	    queue.setDropdownStyleId("codingSystemId");
	
	    queue.setDropdownsValues("codingSystems");
	
	    queue.setErrorIdFiltering("??");//Same than BackendId?
	
	    queue.setConstantCount("CODING_SYSTEM_COUNT");
	
	    queue.setMethodFromElement("getCodingSystem");
	
	    queue.setMultipleValues("false");
	
	    queue.setMethodGeneralFromForm("getColumn1List");//Only for date/multiselect
	
	    queue.setFilterByConstant(NEDSSConstants.FILTERBYCODINGSYSTEM);
	
	    
	
	    queueDT.setColumn1(queue);
	
	
	    //Second column: Display Name
	
	    queue = new QueueColumnDT();
	
	    queue.setColumnId("column2");
	
	    queue.setColumnName("Code");
	
	    queue.setBackendId("codeBackendId");//TODO: change??
	
	    queue.setDefaultOrder("ascending");
	
	    queue.setSortable("true");
	
	    queue.setSortNameMethod("getCodeColumn");
	
	    queue.setMedia("html pdf csv");
	
	    queue.setMediaHtmlProperty("codeColumn");
	
	    queue.setMediaPdfProperty("codeColumn");
	
	    queue.setMediaCsvProperty("codeColumn");
	
	    queue.setColumnStyle("width:8%");
	
	    queue.setFilterType("1");//0 date, 1 text, 2 multiselect. TODO: there's no filter
	
	    queue.setDropdownProperty("SearchText1");
	
	    queue.setDropdownStyleId("codeDropDown");
	
	    queue.setDropdownsValues("noDataArray");
	
	    queue.setConstantCount("CODE_COUNT");
	
	    queue.setMethodFromElement("getCodeColumn");//TODO; change name??
	
	   // queue.setMethodGeneralFromForm("getColumn1List");//Only for date/multiselect
	
	    queue.setFilterByConstant(NEDSSConstants.FILTERBYCODE);
	
	    queueDT.setColumn2(queue);
	    
	    //Third column: Display Name
	
	    queue = new QueueColumnDT();
	
	    queue.setColumnId("column3");
	
	    queue.setColumnName("Display Name");
	
	    queue.setBackendId("displayNameBackendId");
	
	    queue.setDefaultOrder("ascending");
	
	    queue.setSortable("true");
	
	    queue.setSortNameMethod("getDisplayName");
	
	    queue.setMedia("html pdf csv");
	
	    queue.setMediaHtmlProperty("displayName");
	
	    queue.setMediaPdfProperty("displayName");
	
	    queue.setMediaCsvProperty("displayName");
	
	    queue.setColumnStyle("width:24%");
	
	    queue.setFilterType("1");//0 date, 1 text, 2 multiselect
	
	
	    queue.setDropdownProperty("SearchText2");
	
	    queue.setDropdownStyleId("displayNameDropDown");
	
	    queue.setDropdownsValues("noDataArray");
	
	
	    queue.setErrorIdFiltering("??");//Same than BackendId?
	
	    queue.setConstantCount("DISPLAY_NAME_COUNT");
	
	    queue.setMethodFromElement("getDisplayName");
	
	    //queue.setMethodGeneralFromForm("getColumn3List");//Only for date/multiselect
	
	    queue.setFilterByConstant(NEDSSConstants.FILTERBYDISPLAYNAME);
	
	    queueDT.setColumn3(queue);
	
	    //Forth column: Condition
	
	    queue = new QueueColumnDT();
	
	    queue.setColumnId("column4");
	
	    queue.setColumnName("Condition");
	
	    queue.setBackendId("conditionBackendId");//??
	
	    queue.setDefaultOrder("ascending");
	
	    queue.setSortable("true");
	
	    queue.setSortNameMethod("getCondition");
	
	    queue.setMedia("html pdf csv");
	
	    queue.setMediaHtmlProperty("condition");
	
	    queue.setMediaPdfProperty("condition");
	
	    queue.setMediaCsvProperty("condition");
	
	    queue.setColumnStyle("width:17%");
	
	    queue.setFilterType("2");//0 date, 1 text, 2 multiselect
	
	    queue.setDropdownProperty("CONDITION");
	
	    queue.setDropdownStyleId("conditionId");
	
	    queue.setDropdownsValues("conditions");
	
	    queue.setErrorIdFiltering("??");//Same than BackendId?
	    queue.setMultipleValues("false");
	    queue.setConstantCount("CONDITION_COUNT");
	    queue.setMethodGeneralFromForm("getColumn4List");
	
	    queue.setMethodFromElement("getConditions");
	    queue.setFilterByConstant(NEDSSConstants.FILTERBYCONDITION2);
	    queueDT.setColumn4(queue);
	   
	    	//Fifth column : Progrma Area
	  
	
	    queue = new QueueColumnDT();
	
	    queue.setColumnId("column5");
	
	    queue.setColumnName("Program Area");
	
	    queue.setBackendId("programAreaBackendId");
	
	    queue.setDefaultOrder("ascending");
	
	    queue.setSortable("true");
	
	    queue.setSortNameMethod("getProgramArea");
	
	    queue.setMedia("html pdf csv");
	
	    queue.setMediaHtmlProperty("programArea");
	
	    queue.setMediaPdfProperty("programArea");
	
	    queue.setMediaCsvProperty("programArea");
	
	    queue.setColumnStyle("width:8%");
	
	    queue.setFilterType("2");//0 date, 1 text, 2 multiselect
	
	    queue.setDropdownProperty("ProgramArea");
	
	    queue.setDropdownStyleId("programAreaId");
	
	    queue.setDropdownsValues("programAreas");
	    queue.setMultipleValues("false");
	    queue.setErrorIdFiltering("??");//Same than BackendId?
	
	    queue.setConstantCount("PROGRAM_AREA_COUNT");
	    queue.setMethodGeneralFromForm("getColumn5List");
	    queue.setMethodFromElement("getProgramArea");
	 
	    queue.setFilterByConstant(NEDSSConstants.FILTERBYPROGRAMAREA);
	    queueDT.setColumn5(queue);
	
	
	
	
	    //Sixth column: Status
	
	    queue = new QueueColumnDT();
	
	    queue.setColumnId("column6");
	
	    queue.setColumnName("Status");
	
	    queue.setBackendId("statusBackendId");
	
	    queue.setDefaultOrder("ascending");
	
	    queue.setSortable("true");
	
	    queue.setSortNameMethod("getStatus");
	
	    queue.setMedia("html pdf csv");
	
	    queue.setMediaHtmlProperty("status");
	
	    queue.setMediaPdfProperty("status");
	
	    queue.setMediaCsvProperty("status");
	
	    queue.setColumnStyle("width:8%");
	
	    queue.setFilterType("2");//0 date, 1 text, 2 multiselect
	
	    queue.setDropdownProperty("STATUS");
	
	    queue.setDropdownStyleId("statusId");
	
	    queue.setDropdownsValues("statusValues");
	
	    queue.setErrorIdFiltering("??");//Same than BackendId?
	
	    queue.setConstantCount("STATUS_COUNT");
	
	    queue.setMethodFromElement("getStatus");
	    queue.setMultipleValues("false");
	    queue.setMethodGeneralFromForm("getColumn6List");//Only for date/multiselect
	
	    queue.setFilterByConstant(NEDSSConstants.FILTERBYSTATUS2);//TODO: change
	
	    queueDT.setColumn6(queue);

	    return queueDT;
	
	}
	
	/**
	 * translateFromObservationSummaryDisplayVOToObservationSummaryDisplayVO: method used from the generic queue to translate from the specific form to the generic form
	 * @param obsNeedRevForm
	 * @return
	 */
	private GenericForm translateFromObservationSummaryDisplayVOToObservationSummaryDisplayVO(TriggerCodeForm obsNeedRevForm){
	
	    GenericForm genericForm = new GenericForm();
	
	    genericForm.setSearchCriteriaArrayMap(obsNeedRevForm.getSearchCriteriaArrayMap());
	    genericForm.setAttributeMap(obsNeedRevForm.getAttributeMap());
	    genericForm.setQueueDT(obsNeedRevForm.getQueueDT());
	
	    //general methods from form
	
	    genericForm.setColumn1List(obsNeedRevForm.getCodingSystems());
	    genericForm.setColumn4List(obsNeedRevForm.getConditions());
	    genericForm.setColumn5List(obsNeedRevForm.getProgramAreas());
	    genericForm.setColumn6List(obsNeedRevForm.getStatusValues());

	    Collection<Object> observationCollection = obsNeedRevForm.getTriggerCodesDTColl();
	    genericForm.setElementColl(obsNeedRevForm.getManageList());//NEW
	
	    return genericForm;
	
	}

	/**
	 * viewTriggerCode(): this method shows the trigger code values at the bottom in the view section
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	
	public ActionForward viewTriggerCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException {
		TriggerCodeForm triggerCodeForm  = (TriggerCodeForm) form;
		TriggerCodesDT dt = new TriggerCodesDT();
		ArrayList<?> filQueueList = new ArrayList<Object> ();
		String cnxt = request.getParameter("context");
		
		try {
			triggerCodeForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			
			request.setAttribute("accessMode", "viewTriggerCode");
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {		
				request.setAttribute("accessMode", "cancel");
				triggerCodeForm.setActionMode("cancel");
			}			
			String nbsUid = request.getParameter("nbsUid");
			
			triggerCodeForm.getAttributeMap().put("cancel", "/nbs/TriggerCodes.do?method=resultsLoad&context=cancel");
			triggerCodeForm.getAttributeMap().put("EditView", "/nbs/TriggerCodes.do?method=editTriggerCode&nbsUid="+nbsUid+"#expAlg");
			triggerCodeForm.getAttributeMap().put("AddNewCode", "/nbs/TriggerCodes.do?method=createLoadTriggerCode");

			if(nbsUid != null && nbsUid.length() > 0) {
				Collection<?>  queueList = (ArrayList<?> ) triggerCodeForm.getAttributeMap().get("manageList");
				 filQueueList = (ArrayList<?> )queueList;
				if(filQueueList!= null){
					Iterator<?> iter = filQueueList.iterator();
					while(iter.hasNext()) {			
						 dt = (TriggerCodesDT) iter.next();
						if(dt.getNbsUid().toString().equalsIgnoreCase(nbsUid) ) {
							triggerCodeForm.setTrigCodesDT(dt);
							//triggerCodeForm.setOldExpRecFacDT(dt);
							break;
						}
					}
				}
				else {

				ArrayList<Object> testList = triggerCodeForm.getManageList();
				Iterator<Object> iter = testList.iterator();
				while(iter.hasNext()) {			
					 dt = (TriggerCodesDT) iter.next();
					if(dt.getNbsUid().toString().equalsIgnoreCase(nbsUid) ) {
						triggerCodeForm.setTrigCodesDT(dt);
					//	triggerCodeForm.setOldExpRecFacDT(dt);
						break;
					}
				}
			 }
			}
			
			
		} catch (Exception e) {
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			logger.error("Exception in viewTriggerCode: " + e.getMessage());
		} finally {
			Collection<Object>  recFacilityList = triggerCodeForm.getManageList();
			
			if(filQueueList!= null && !(cnxt != null && cnxt.equalsIgnoreCase("cancel"))){
				request.setAttribute("manageList",filQueueList);
				
			}
			else {
			request.setAttribute("manageList", recFacilityList);
			}
			
			request.setAttribute("queueCollection",triggerCodeForm.getQueueCollection());
            request.setAttribute("stringQueueCollection",triggerCodeForm.getStringQueueCollection());
			request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.MANAGE_TRIGGER_CODES);
		}
		
		return PaginationUtil.paginate(triggerCodeForm, request, "default",mapping);
	
	}
	
	
	/**
	 * editTriggerCode(): this method shows the trigger code values in edit mode at the bottom
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editTriggerCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		TriggerCodeForm triggerCodeForm  = (TriggerCodeForm) form;
		TriggerCodesDT dt = new TriggerCodesDT();
		ArrayList<?> filQueueList = new ArrayList<Object> ();
		try{
			triggerCodeForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			String cnxt = request.getParameter("context");
			
			request.setAttribute("accessMode", "editTriggerCode");
			
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {					
				triggerCodeForm.setTrigCodesDT(triggerCodeForm.getTrigCodesDT());
				return (PaginationUtil.paginate(triggerCodeForm, request, "default",mapping));
			}
			String nbsUid = request.getParameter("nbsUid");
			if(nbsUid != null && nbsUid.length() > 0) {
				Collection<?>  queueList = (ArrayList<?> ) triggerCodeForm.getAttributeMap().get("manageList");
				 filQueueList = (ArrayList<?> )queueList;
				if(filQueueList!= null){
					Iterator<?> iter = filQueueList.iterator();
					while(iter.hasNext()) {			
						 dt = (TriggerCodesDT) iter.next();
						if(dt.getNbsUid().toString().equalsIgnoreCase(nbsUid) ) {
							triggerCodeForm.setSelection(dt);
							break;
						}
					}
				}
				else {
				ArrayList<Object> testList = triggerCodeForm.getManageList();
				Iterator<Object> iter = testList.iterator();
				while(iter.hasNext()) {			
					dt = (TriggerCodesDT) iter.next();
					if(dt.getNbsUid().toString().equalsIgnoreCase(nbsUid) ) {
						triggerCodeForm.setTrigCodesDT(dt);
						triggerCodeForm.setSelection(dt);
						break;
					}
				}
			  }
			}
		
		}
		catch (Exception e) {
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			logger.error("Exception in viewTriggerCode: " + e.getMessage());
		} finally {
			Collection<Object>  recFacilityList = triggerCodeForm.getManageList();
			
			if(filQueueList!= null){
				request.setAttribute("manageList",filQueueList);
				
			}
			else {
				request.setAttribute("manageList", recFacilityList);
			 }
			String nbsUid = dt.getNbsUid().toString();
			request.setAttribute("nbsUid", nbsUid);
			triggerCodeForm.getAttributeMap().put("cancel", "/nbs/TriggerCodes.do?method=resultsLoad&context=cancel");
			triggerCodeForm.getAttributeMap().put("Edit", "/nbs/TriggerCodes.do?method=updateTriggerCode&nbsUid="+nbsUid+"#expAlg");
			request.setAttribute("queueCollection",triggerCodeForm.getQueueCollection());
	        request.setAttribute("stringQueueCollection",triggerCodeForm.getStringQueueCollection());
			request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.MANAGE_TRIGGER_CODES);
		}		
		return (PaginationUtil.paginate(triggerCodeForm, request, "edit",mapping));		
	}
	
	/**
	 * updateTriggerCode: this method updates the trigger code
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	
	public ActionForward updateTriggerCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException {
	
		String forward = null;
		TriggerCodeForm triggerCodeForm  = (TriggerCodeForm) form;
		triggerCodeForm.setManageList(null);
		HttpSession session = request.getSession(true);
		TriggerCodesDT dt = (TriggerCodesDT)triggerCodeForm.getSelection();
		String diseaseNm = triggerCodeForm.getDiseaseNameFromCode(dt.getConditionCd());
		dt.setDiseaseNm(diseaseNm);
		Map<Object, Object> errorMap = null;
		try{
			
			if(dt.getDisplayName()==null || dt.getDisplayName().trim().isEmpty() || dt.getCodeSystemVersionId()== null || dt.getCodeSystemVersionId().trim().isEmpty()){
				
				StringBuffer buff= new StringBuffer();
				String errText = "";
				
				if(dt.getDisplayName()==null || dt.getDisplayName().trim().isEmpty()){
					errText="Display Name is required";
					buff.append("<ul>").append("<li>").append(HTMLEncoder.encodeHtml(errText)).append("</li>").append("</ul>");
				}
				if(dt.getCodeSystemVersionId()== null || dt.getCodeSystemVersionId().trim().isEmpty()){
					errText="Version ID is required";
					buff.append("<ul>").append("<li>").append(HTMLEncoder.encodeHtml(errText)).append("</li>").append("</ul>");
				}
				
				request.setAttribute("manageList",triggerCodeForm.getAttributeMap().get("manageList"));
				request.setAttribute("error",buff.toString());
				request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.MANAGE_TRIGGER_CODES);
				request.setAttribute("accessMode", "editTriggerCode");
				
				return (mapping.findForward("edit"));	
			}
			
			else{
			
			errorMap = rsUtil.updateTriggerCode(session, dt);
			if(errorMap.size()==0){
				String CONFIRM_MSG_EDIT = "The code <b>"+HTMLEncoder.encodeHtml(dt.getCode())+"</b> has been successfully updated in the library.";
				//request.setAttribute("ConfirmMesg" ,CONFIRM_MSG_EDIT);
				request.setAttribute("ConfirmMesg" ,dt.getCode());
				triggerCodeForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			    triggerCodeForm.setActionMode("");
				forward = "results";
			}
			else if(errorMap.size()!=0){	
				triggerCodeForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
				Iterator<Object> iter = errorMap.values().iterator();
				StringBuffer buff= new StringBuffer();
				while(iter.hasNext()){
					String errText = iter.next().toString();
					buff.append("<ul>").append("<li>").append(HTMLEncoder.encodeHtml(errText)).append("</li>").append("</ul>");
				}
				request.setAttribute("error",buff.toString());
				forward = "default";
				
			}	
			}
			
		}catch (Exception e){
			logger.error("Error in updateTriggerCode method" );
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			throw new ServletException("Error in updating the trigger code: "+e.getMessage(),e);
		}
		ArrayList<Object> algorithmList = new ArrayList<Object> ();
		try{

			Map<Object, Object> searchCriteria = (TreeMap<Object,Object>) triggerCodeForm.getAttributeMap().get("searchCriteria");
			
			String whereClause = SRTAdminUtil.triggerCodesSrchWhereClause(triggerCodeForm);		
			if(whereClause == null || whereClause.isEmpty()){
				whereClause = (String)triggerCodeForm.getAttributeMap().get("whereClause");
			}
			
			String pageNumber = (String) triggerCodeForm.getAttributeMap().get("PageNumber");
			triggerCodeForm.getAttributeMap().clear();
			triggerCodeForm.getAttributeMap().put("PageNumber", pageNumber);
			triggerCodeForm.getAttributeMap().put("whereClause",whereClause);
	
			Object[] searchParams = {whereClause};
				
			Object[] oParams = new Object[] { JNDINames.TRIGGER_CODES_RESULT_DAO_CLASS, "getTriggerCodeResultDTCollection", searchParams };
			algorithmList= (ArrayList<Object> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			request.setAttribute("FromEdit","true");
			
			Iterator<Object> iter = algorithmList.iterator();
			while(iter.hasNext()) {
				TriggerCodesDT dt2 = (TriggerCodesDT) iter.next();
				
				dt2.setCodingSystem(dt2.getCodingSystem());	  
				dt2.setCodeColumn(dt2.getCode());
				if(dt2.getCodeDescTxt()!= null)
					dt2.setDisplayName(dt2.getCodeDescTxt());
				if(dt2.getDiseaseNm()!= null &&  dt2.getConditionCd()!=null){
					dt2.setCondition(dt2.getDiseaseNm()+" ("+ dt2.getConditionCd()+")");
					dt2.setConditions(dt2.getDiseaseNm());
	
				}
				dt2.setProgramArea(dt2.getProgramArea());
				dt2.setStatus(CachedDropDowns.getCodeDescTxtForCd(dt2.getStatusCd(),"NBS_STATUS_CD"));
			}			
			triggerCodeForm.getAttributeMap().put("searchCriteria", searchCriteria);
			request.setAttribute("manageList", algorithmList);
			triggerCodeForm.getAttributeMap().put("manageList", algorithmList);
			triggerCodeForm.getAttributeMap().put("resultList",algorithmList);
			triggerCodeForm.setManageList(algorithmList);
			
		}catch (Exception e){
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
		}
		
		request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.MANAGE_TRIGGER_CODES);

		return (mapping.findForward(forward));	
	}
	
	
	/**
	 * createTriggerCode: this method creates a new trigger code
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward createTriggerCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException {
		
		String forward = null;
		TriggerCodeForm triggerCodeForm  = (TriggerCodeForm) form;
		triggerCodeForm.setManageList(null);
		HttpSession session = request.getSession(true);
		TriggerCodesDT dt = (TriggerCodesDT)triggerCodeForm.getSelection();
		String diseaseNm = triggerCodeForm.getDiseaseNameFromCode(dt.getConditionCd());
		dt.setDiseaseNm(diseaseNm);
		Map<Object, Object> errorMap = null;
		try{
			/*
			if(dt.getDisplayName()==null || dt.getDisplayName().trim().isEmpty() || dt.getCodeSystemVersionId()== null || dt.getCodeSystemVersionId().trim().isEmpty()
		|| dt.getCodingSystem()==null || dt.getCodingSystem().isEmpty() || dt.getCodeColumn() == null || dt.getCodeColumn().trim().isEmpty()){
				
				StringBuffer buff= new StringBuffer();
				String errText = "";
				
				if(dt.getCodingSystem()==null || dt.getCodingSystem().isEmpty()){
					errText="Coding System is required";
					buff.append("<ul>").append("<li>").append(errText).append("</li>").append("</ul>");
				}
				
				if(dt.getCodeColumn()==null || dt.getCodeColumn().trim().isEmpty()){
					errText="Code is required";
					buff.append("<ul>").append("<li>").append(errText).append("</li>").append("</ul>");
				}
				
				if(dt.getDisplayName()==null || dt.getDisplayName().trim().isEmpty()){
					errText="Display Name is required";
					buff.append("<ul>").append("<li>").append(errText).append("</li>").append("</ul>");
				}
				if(dt.getCodeSystemVersionId()== null || dt.getCodeSystemVersionId().trim().isEmpty()){
					errText="Version ID is required";
					buff.append("<ul>").append("<li>").append(errText).append("</li>").append("</ul>");
				}
				
				request.setAttribute("manageList",triggerCodeForm.getAttributeMap().get("manageList"));
				request.setAttribute("error",buff.toString());
				request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.MANAGE_TRIGGER_CODES);
				request.setAttribute("accessMode", "editTriggerCode");
				
				return (mapping.findForward("create"));	
			}*/
			
		//	else{
			
				errorMap = rsUtil.createTriggerCode(session, dt);
				if(errorMap==null || errorMap.size()==0){
					String CONFIRM_MSG_EDIT = "The code <b>"+HTMLEncoder.encodeHtml(dt.getCodeColumn())+"</b> has been successfully added to the library.";
					//request.setAttribute("ConfirmMesg" ,CONFIRM_MSG_EDIT);
					request.setAttribute("ConfirmMesg" ,dt.getCodeColumn());
					triggerCodeForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
				    triggerCodeForm.setActionMode("");
					forward = "results";
				}
				else if(errorMap.size()!=0){	
					triggerCodeForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
					Iterator<Object> iter = errorMap.values().iterator();
					StringBuffer buff= new StringBuffer();
					while(iter.hasNext()){
						String errText = iter.next().toString();
						buff.append("<ul>").append("<li>").append(HTMLEncoder.encodeHtml(errText)).append("</li>").append("</ul>");
					}
					request.setAttribute("error",buff.toString());
					request.setAttribute("accessMode", "createTriggerCode");
					forward = "default";
					
				}	
		//	}
			
		}catch (Exception e){
			logger.error("Error in createTriggerCode method" );
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			throw new ServletException("Error in Creating the Trigger Code"+e.getMessage(),e);
		}
		ArrayList<Object> algorithmList = new ArrayList<Object> ();
		try{
			
			
			Map<Object, Object> searchCriteria = (TreeMap<Object,Object>) triggerCodeForm.getAttributeMap().get("searchCriteria");
			
			String whereClause = SRTAdminUtil.triggerCodesSrchWhereClause(triggerCodeForm);		
		
			if(whereClause == null || whereClause.isEmpty()){
				whereClause = (String)triggerCodeForm.getAttributeMap().get("whereClause");
			}
			
			String pageNumber = (String) triggerCodeForm.getAttributeMap().get("PageNumber");
			triggerCodeForm.getAttributeMap().clear();
			triggerCodeForm.getAttributeMap().put("PageNumber", pageNumber);
			
			triggerCodeForm.getAttributeMap().put("whereClause",whereClause);
			Object[] searchParams = {whereClause};
			Object[] oParams = new Object[] { JNDINames.TRIGGER_CODES_RESULT_DAO_CLASS, "getTriggerCodeResultDTCollection", searchParams };
			algorithmList= (ArrayList<Object> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			request.setAttribute("FromEdit","true");
			
			Iterator<Object> iter = algorithmList.iterator();
			while(iter.hasNext()) {
				TriggerCodesDT dt2 = (TriggerCodesDT) iter.next();
				
				dt2.setCodingSystem(dt2.getCodingSystem());	  
				dt2.setCodeColumn(dt2.getCode());
				if(dt2.getCodeDescTxt()!= null)
					dt2.setDisplayName(dt2.getCodeDescTxt());
				if(dt2.getDiseaseNm()!= null &&  dt2.getConditionCd()!=null){
					dt2.setCondition(dt2.getDiseaseNm()+" ("+ dt2.getConditionCd()+")");
					//conditions.add(dt.getDiseaseNm());
					dt2.setConditions(dt2.getDiseaseNm());
	
				}
				dt2.setProgramArea(dt2.getProgramArea());		  
				dt2.setStatus(CachedDropDowns.getCodeDescTxtForCd(dt2.getStatusCd(),"NBS_STATUS_CD"));
			}		
			triggerCodeForm.getAttributeMap().put("searchCriteria", searchCriteria);
			request.setAttribute("manageList", algorithmList);
			triggerCodeForm.getAttributeMap().put("manageList", algorithmList);
			triggerCodeForm.getAttributeMap().put("resultList",algorithmList);
			triggerCodeForm.setManageList(algorithmList);
			
		}catch (Exception e){
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
		}
		triggerCodeForm.getAttributeMap().put("queueSize", properties.getQueueSize(NEDSSConstants.MANAGE_TRIGGERCODES_QUEUE_SIZE));
		request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.MANAGE_TRIGGER_CODES);
	    int queueLength = algorithmList.size();
	    
	    if(queueLength==0){
	    	triggerCodeForm.getAttributeMap().put("NoResultTable","NoResultTable"); 
	    	triggerCodeForm.getAttributeMap().put("NORESULT","NORESULT");
	    }
	    //   return PaginationUtil.paginate(triggerCodeForm, request, forward,mapping);
	    
	    triggerCodeForm.getAttributeMap().put("cancel", "/nbs/TriggerCodes.do?method=resultsLoad&context=cancel");
		return (mapping.findForward(forward));
	}
	
	/**
	 * setSortOrderAndMethod: this method sort the results on the table
	 * @param genericForm
	 */
	public void setSortOrderAndMethod(GenericForm genericForm){
		String sortOrderString, sortOrder = "", sortMethod = "";
		Map<Object, Object>  sColl =  (TreeMap<Object,Object>) genericForm.getAttributeMap().get("searchCriteria");
		if(sColl!=null){
			sortOrderString = (String) sColl.get("sortSt");
			if(sortOrderString!=null & sortOrderString.indexOf("in") != -1){
				sortMethod = sortOrderString.substring(0, sortOrderString.indexOf("in")-1).trim();
				sortOrder = sortOrderString.substring(sortOrderString.indexOf("in")+2,sortOrderString.indexOf("order")-1).trim();
		
				QueueDT queueDT = genericForm.getQueueDT();
				for(int i = 1; i <= queueDT.getQueueDTSize();i++){
					QueueColumnDT queue = queueDT.getColumn(i);
					if(sortMethod.equalsIgnoreCase(queue.getColumnName())){
						sortMethod = queue.getSortNameMethod();
						genericForm.getAttributeMap().put("methodName",sortMethod);
						break;
					}
				}
				if(sortOrder!= null){
					if("ascending".equalsIgnoreCase(sortOrder))
						sortOrder = "1";
					else 
						sortOrder = "2";
					genericForm.getAttributeMap().put("sortOrder",sortOrder);
					
				}
			}	
		}
	}
}
