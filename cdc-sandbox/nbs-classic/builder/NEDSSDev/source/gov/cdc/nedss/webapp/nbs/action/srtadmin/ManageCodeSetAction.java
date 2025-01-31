package gov.cdc.nedss.webapp.nbs.action.srtadmin;

import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PhinVadsSystemVO;
import gov.cdc.nedss.pagemanagement.wa.dt.ConditionDT;
import gov.cdc.nedss.srtadmin.dt.CodeSetDT;
import gov.cdc.nedss.srtadmin.dt.CodeSetGpMetaDataDT;
import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.phinvads.ValueSetVersionUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.DynamicPageConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.srtadmin.SrtManageForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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

public class ManageCodeSetAction extends DispatchAction{
	
	static final LogUtils logger = new LogUtils(ManageCodeSetAction.class.getName());
	private static final String daoName = JNDINames.CODE_SET_DAO_CLASS;
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	static final String CONFIRM_MSG = "This code set has been successfully updated";
	static final String ASSGN_AUTHORITY = "ASSGN_AUTHORITY";
	static final String CODE_SYSTEM = "CODE_SYSTEM";
	String editLinkEnabledIcon = "page_white_edit.gif", editLinkDisabledIcon = "page_white_edit_disabled.gif";
	PropertyUtil properties = PropertyUtil.getInstance();
	QueueUtil queueUtil = new QueueUtil();
	
	
	@SuppressWarnings("unchecked")
	public ActionForward ViewValueSetLib(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException,
    ServletException {
		
		
			SrtManageForm manageForm = (SrtManageForm) form;	
			HttpSession session = request.getSession(true);
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
		    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION);
		    if (securityCheck != true) {
		      session.setAttribute("error", "Failed at security checking. OR Page Management permission");
		      throw new ServletException("Failed at security checking. OR Page Management permission");
		    }
			boolean forceEJBcall = false;
			boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
			if (initLoad && !PaginationUtil._dtagAccessed(request)) {
				manageForm.clearSelections();
				manageForm.clearAll();
				forceEJBcall = true;
				// get the number of records to be displayed per page
				Integer queueSize = properties.getQueueSize(NEDSSConstants.PAGE_MANAGEMENT_VALUE_SET_LIBRARY_QUEUE_SIZE);
				manageForm.getAttributeMap().put("queueSize", queueSize);
			}
			String contextAction = request.getParameter("context");
			if (contextAction != null) {
				if(contextAction.equals("cancel"))
					forceEJBcall = false;
				else if(contextAction.equals("ReturnToManage"))
					forceEJBcall = true;
				Integer queueSize = properties.getQueueSize(NEDSSConstants.PAGE_MANAGEMENT_VALUE_SET_LIBRARY_QUEUE_SIZE);
				manageForm.getAttributeMap().put("queueSize", queueSize);

				
			}
			//To make sure SelectAll is checked, see if no criteria is applied
			if(manageForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			String actionMd = request.getParameter("actionMode");
			if(manageForm.getActionMode()== null || (actionMd!= null && actionMd.equalsIgnoreCase("Manage")) || manageForm.getActionMode().equals("Manage") )
				manageForm.setActionMode(DynamicPageConstants.MANAGE);
			else{
				manageForm.setActionMode(manageForm.getActionMode());
				CodeSetDT dt = (CodeSetDT) manageForm.getSelection();
				String activeInd = dt.getStatusCd();
				if(activeInd != null && activeInd.equals("A"))
				{
					request.setAttribute("ActiveInd", "Inactive");
				}else if(activeInd != null && activeInd.equals("I"))
				{
					request.setAttribute("ActiveInd", "Active");
				}
			}
			
		try {
			
			ArrayList<Object> codesetLibraryList = new ArrayList<Object> ();
			if(forceEJBcall){
				Object[] searchParams = {};
				Object[] oParams = new Object[] { daoName, "getExistingCodeSets", searchParams };
				codesetLibraryList = (ArrayList<Object> ) SRTAdminUtil.processRequest(oParams, request.getSession());
				
				NBSContext.store(session, NBSConstantUtil.DSCodeSetList, codesetLibraryList);
				//Set InvSummaryVOColl as property of Form first time since this list is used for distinct dropdown values in the form
				manageForm.initializeDropDowns(codesetLibraryList);
				manageForm.getAttributeMap().put("typeCount",new Integer(manageForm.getType().size()));
				manageForm.getAttributeMap().put("statusCount",new Integer(manageForm.getStatus().size()));
				Iterator<Object> iter = codesetLibraryList.iterator();
				while(iter.hasNext()) {
					CodeSetDT dt = (CodeSetDT) iter.next();
					String codeSetNm = HTMLEncoder.encodeHtml(dt.getCodeSetNm().toString());
					request.setAttribute("conditionCd", codeSetNm);
					dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
					String viewUrl = "<a href=\"javascript:viewValueset('" + codeSetNm + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
					dt.setViewLink(viewUrl);
					String editUrl = "<a href=\"javascript:editValueset('" + codeSetNm + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>";
					dt.setEditLink(editUrl);
					}	
			}else {
				codesetLibraryList = (ArrayList<Object> ) NBSContext.retrieve(session, NBSConstantUtil.DSCodeSetList);
			}
			boolean existing = request.getParameter("existing") == null ? false : true;
			
			if(contextAction != null && contextAction.equalsIgnoreCase("ReturnToManage")) {
				Iterator<Object> iter = codesetLibraryList.iterator();
				while(iter.hasNext()) {
					CodeSetDT dt = (CodeSetDT) iter.next();
					String codeSetNm = HTMLEncoder.encodeHtml(dt.getCodeSetNm().toString());
					request.setAttribute("conditionCd", codeSetNm);
					dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
					String viewUrl = "<a href=\"javascript:viewValueset('" + codeSetNm + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
					dt.setViewLink(viewUrl);
					String editUrl = "<a href=\"javascript:editValueset('" + codeSetNm + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>";
					dt.setEditLink(editUrl);
					}	
				//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
				Collection<Object>  filteredColl = filterCodesetLib(manageForm, request);
				if(filteredColl != null)
					codesetLibraryList = (ArrayList<Object> ) filteredColl;
				sortManageCodeset(manageForm, codesetLibraryList, existing, request);
			} else {
				Collection<Object>  filteredColl = (ArrayList<Object> )filterCodesetLib(manageForm, request);
				if(filteredColl != null)
					codesetLibraryList = (ArrayList<Object> ) filteredColl;
				sortManageCodeset(manageForm, codesetLibraryList, existing, request);
			//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
				if(!existing) {
					Iterator<Object> iter = codesetLibraryList.iterator();
					while(iter.hasNext()) {
						CodeSetDT dt = (CodeSetDT) iter.next();
						String codeSetNm = HTMLEncoder.encodeHtml(dt.getCodeSetNm().toString());
						request.setAttribute("conditionCd", codeSetNm);
						dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
						String viewUrl = "<a href=\"javascript:viewValueset('" + codeSetNm + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
						dt.setViewLink(viewUrl);
						String editUrl = "<a href=\"javascript:editValueset('" + codeSetNm + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>";
						dt.setEditLink(editUrl);
						}	
				}else {
					Iterator<Object> iter = codesetLibraryList.iterator();
					while(iter.hasNext()) {
						CodeSetDT dt = (CodeSetDT) iter.next();
						String codeSetNm = HTMLEncoder.encodeHtml(dt.getCodeSetNm().toString());
						request.setAttribute("conditionCd", codeSetNm);
						dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
						String viewUrl = "<a href=\"javascript:viewValueset('" + codeSetNm + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
						dt.setViewLink(viewUrl);
						String editUrl = "<a href=\"javascript:editValueset('" + codeSetNm + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>";
						dt.setEditLink(editUrl);
						}	
				}
			}
			
			request.setAttribute("manageList", codesetLibraryList);
			manageForm.getAttributeMap().put("queueCount", String.valueOf(codesetLibraryList.size()));
			request.setAttribute("queueCount", String.valueOf(codesetLibraryList.size()));
			NBSContext.store(session, NBSConstantUtil.DSCodeSetList, codesetLibraryList);
			//manageForm.setManageList(codesetLibraryList);
		} catch (Exception e) {
			logger.error("Exception in ViewValueSetLib: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_CODE_SET);
			manageForm.getAttributeMap().put("Create", "/nbs/ManageCondition.do?method=createConditionLoad#condition");
		}
	
		return PaginationUtil.paginate(manageForm, request, "default",mapping);
		
	}
	
	@SuppressWarnings("unchecked")
	private Collection<Object>  filterCodesetLib(SrtManageForm manageForm, HttpServletRequest request) {
		
		Collection<Object>  manageCondlist = new ArrayList<Object> ();
		
		String srchCriteriaType = null;
		String srchCriteriaStatus = null;
		String sortOrderParam = null;
		String srchCriteriaCode = null;
		String srchCriteriaName = null;
		String srchCriteriaDescription = null;
		try {
			
			Map<Object, Object> searchCriteriaMap = manageForm.getSearchCriteriaArrayMap();
			ArrayList<Object> codesetList = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSCodeSetList);
			
			manageCondlist = getFilteredCodesetLib(codesetList, searchCriteriaMap);

			String[] type = (String[]) searchCriteriaMap.get("TYPE");
			String[] status = (String[]) searchCriteriaMap.get("STATUS");
			
			Integer typeCount = new Integer(type == null ? 0 : type.length);
			Integer statusCount = new Integer(status == null ? 0 : status.length);
			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				srchCriteriaCode = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				srchCriteriaName = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
				srchCriteriaDescription = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
			}
						

			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if(typeCount.equals((manageForm.getAttributeMap().get("typeCount"))) &&
					(statusCount.equals(manageForm.getAttributeMap().get("statusCount")))&&
					(srchCriteriaCode == null && srchCriteriaName == null && srchCriteriaDescription == null))
				 {
				
				String sortMethod = getSortMethod(request, manageForm);
				String direction = getSortDirection(request, manageForm);			
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<?,?> sColl =  manageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<?,?>) manageForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = SRTAdminUtil.getSortCriteria(direction, sortMethod);
				}				
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
				return null;				
			}
			ArrayList<Object> typeList = manageForm.getType();
			ArrayList<Object> statusList = manageForm.getStatus();
			
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			String sortMethod = getSortMethod(request, manageForm);
			String direction = getSortDirection(request, manageForm);			
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object, Object> sColl =  manageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<Object, Object>) manageForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = SRTAdminUtil.getSortCriteria(direction, sortMethod);
			}
			
			srchCriteriaType = queueUtil.getSearchCriteria(typeList, type, NEDSSConstants.FILTERBYSUBMITTEDBY);
			srchCriteriaStatus = queueUtil.getSearchCriteria(statusList, status, NEDSSConstants.FILTERBYSUBMITTEDBY);
			
			//set the error message to the form
			if(sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if(srchCriteriaType != null)
				searchCriteriaColl.put("INV111", srchCriteriaType);
			if(srchCriteriaStatus != null)
				searchCriteriaColl.put("INV222", srchCriteriaStatus);
			if(srchCriteriaCode != null)
				searchCriteriaColl.put("INV333", srchCriteriaCode);			
			if(srchCriteriaName != null)
				searchCriteriaColl.put("INV444", srchCriteriaName);			
			if(srchCriteriaDescription != null)
				searchCriteriaColl.put("INV555", srchCriteriaDescription);
						
			
			manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering the codeset: "+ e.toString());
		} 
		return manageCondlist;
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward filterCodesetLibSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		SrtManageForm manageForm  = (SrtManageForm) form;
		Collection<Object>  codesetList = filterCodesetLib(manageForm, request);
		if(codesetList != null){
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
		//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
		}else{
			codesetList = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSCodeSetList);
		}

		request.setAttribute("queueList", codesetList);
		sortManageCodeset(manageForm, codesetList, true, request);
		request.setAttribute("manageList", codesetList);
		request.setAttribute("queueCount", String.valueOf(codesetList.size()));
		manageForm.getAttributeMap().put("queueCount", String.valueOf(codesetList.size()));
		request.setAttribute("PageTitle",SRTAdminConstants.MANAGE_CODE_SET);
		manageForm.getAttributeMap().put("PageNumber", "1");
		manageForm.setManageList((ArrayList<Object>)codesetList);
		return PaginationUtil.paginate(manageForm, request, "default",mapping);
		
	} 
	
	public Collection<Object>  getFilteredCodesetLib(Collection<Object>  codesetDTColl,
			Map<Object, Object> searchCriteriaMap) {
		
    	String[] type = (String[]) searchCriteriaMap.get("TYPE");
		String[] status = (String[]) searchCriteriaMap.get("STATUS");
		String filterByCodeText = null;		
		String filterByNameText = null;
		String filterByDescriptionText = null;
		if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
			filterByCodeText = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
		}		
		if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
			filterByNameText = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
		}				
		if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
			filterByDescriptionText = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
		}				
				
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		Map<Object, Object> statusMap = new HashMap<Object,Object>();
		
		if (type != null && type.length > 0)
			typeMap = queueUtil.getMapFromStringArray(type);
		
		if (status != null && status.length >0)
			statusMap = queueUtil.getMapFromStringArray(status);
		
		
		if(typeMap != null && typeMap.size()>0)
			codesetDTColl = filterType(
					codesetDTColl, typeMap);
		if (statusMap != null && statusMap.size()>0)
			codesetDTColl = filterStatus(
					codesetDTColl, statusMap);
		if(filterByCodeText!= null){
			codesetDTColl = filterByCodeText(codesetDTColl, filterByCodeText);
		}
		if(filterByNameText!= null){
			codesetDTColl = filterByNameText(codesetDTColl, filterByNameText);
		}
		if(filterByDescriptionText!= null){
			codesetDTColl = filterByDescriptionText(codesetDTColl, filterByDescriptionText);
		}		
		return codesetDTColl;
		
	}
	
	public Collection<Object>  filterByCodeText(
			Collection<Object>  codesetDTColl, String filterByText) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (codesetDTColl != null) {
			Iterator<Object> iter = codesetDTColl.iterator();
			while (iter.hasNext()) {
				CodeSetDT dt = (CodeSetDT) iter.next();
				if(dt.getValueSetCode()!=null && dt.getValueSetCode().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
			}
		}
		}catch(Exception ex){
			 logger.error("Error filtering the filterByText : "+ex.getMessage());
		}
		return newTypeColl;
	}	
	
	public Collection<Object>  filterByNameText(
			Collection<Object>  codesetDTColl, String filterByText) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (codesetDTColl != null) {
			Iterator<Object> iter = codesetDTColl.iterator();
			while (iter.hasNext()) {
				CodeSetDT dt = (CodeSetDT) iter.next();
				if(dt.getValueSetNm()!=null && dt.getValueSetNm().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
			}
		}
		}catch(Exception ex){
			 logger.error("Error filtering the filterByText : "+ex.getMessage());
		}
		return newTypeColl;
	}
	
	public Collection<Object>  filterByDescriptionText(
			Collection<Object>  codesetDTColl, String filterByText) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (codesetDTColl != null) {
			Iterator<Object> iter = codesetDTColl.iterator();
			while (iter.hasNext()) {
				CodeSetDT dt = (CodeSetDT) iter.next();
				if(dt.getCodeSetDescTxt()!=null && dt.getCodeSetDescTxt().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
			}
		}
		}catch(Exception ex){
			 logger.error("Error filtering the filterByText : "+ex.getMessage());
		}
		return newTypeColl;
	}	
	public Collection<Object>  filterStatus(
			Collection<Object>  codesetDTColl, Map<Object,Object> statusMap) {
		Collection<Object>  newStatusColl = new ArrayList<Object> ();
		if (codesetDTColl != null) {
			Iterator<Object> iter = codesetDTColl.iterator();
			while (iter.hasNext()) {
				CodeSetDT dt = (CodeSetDT) iter.next();
				if (dt.getStatusCd() != null
						&& statusMap != null
						&& statusMap.containsKey(dt.getStatusCd())) {
					newStatusColl.add(dt);
				}
				if(dt.getStatusCd() == null || dt.getStatusCd().equals("")){
					if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newStatusColl.add(dt);
					}
				}

			}

		}
		return newStatusColl;

	}
	
	public Collection<Object>  filterType(
			Collection<Object>  codesetDTColl, Map<Object,Object> typeMap) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		if (codesetDTColl != null) {
			Iterator<Object> iter = codesetDTColl.iterator();
			while (iter.hasNext()) {
				CodeSetDT dt = (CodeSetDT) iter.next();
				if (dt.getValueSetTypeCd()!= null
						&& typeMap != null
						&& typeMap.containsKey(dt.getValueSetTypeCd())) {
					newTypeColl.add(dt);
				}
				if(dt.getValueSetTypeCd() == null || dt.getValueSetTypeCd().equals("")){
					if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newTypeColl.add(dt);
					}
				}
			}
		}
		return newTypeColl;

	}
	
	
	private void sortManageCodeset(SrtManageForm manageForm, Collection<Object>  codesetList, boolean existing, HttpServletRequest request) {

		// Retrieve sort-order and sort-direction from displaytag params
		String sortMethod = getSortMethod(request, manageForm);
		String direction = getSortDirection(request, manageForm);

		boolean invDirectionFlag = true;
		if (direction != null && direction.equals("2"))
			invDirectionFlag = false;
		
		NedssUtils util = new NedssUtils();
		
		//Read from properties file to determine default sort order
		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
			sortMethod = "getCodeSetNm";
			invDirectionFlag = true;
		}
		
		if (sortMethod != null && codesetList != null && codesetList.size() > 0) {
			util.sortObjectByColumnGeneric(sortMethod,(Collection<Object>) codesetList, invDirectionFlag);
		}
		if(!existing) {
			//Finally put sort criteria in form
			String sortOrderParam = SRTAdminUtil.getSortCriteria(invDirectionFlag == true ? "1" : "2", sortMethod);
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			searchCriteriaColl.put("sortSt", sortOrderParam);
			manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}
		}
	

	
	public ActionForward editCodeSet(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		String mode = request.getParameter("mode");
		try
		{
			manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			String codeSetNm = request.getParameter("codeSetNm");
			
			if((codeSetNm != null && codeSetNm.length() > 0) ) {
				ArrayList<?> testList = (ArrayList<?>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSCodeSetList);
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					CodeSetDT dt = (CodeSetDT) iter.next();
					if(dt.getCodeSetNm().equalsIgnoreCase(codeSetNm)){
						if(dt.getValueSetTypeCd()!= null && dt.getValueSetTypeCd().equals("SYS") )
							request.setAttribute("SYSValue", "SYS");
						dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
						manageForm.setSelection(dt);
						manageForm.setOldDT(dt);
						break;
					}
				}
				
			} 
			if(mode != null && mode.equals("view"))
			{
				manageForm.getAttributeMap().put("submit", "/nbs/ManageCodeSet.do?method=updateCodeSet&mode=view");
				// if edit is from view page
				manageForm.getAttributeMap().put("cancel", "/nbs/ManageCodeSet.do?method=viewCodeSet&codeSetNm="+codeSetNm);
				
			}else{
				// if edit is from the library
				manageForm.getAttributeMap().put("cancel", "/nbs/ManageCodeSet.do?method=ViewValueSetLib&context=cancel");
				manageForm.getAttributeMap().put("submit", "/nbs/ManageCodeSet.do?method=updateCodeSet");
			}
			
			
		} catch (Exception e) {
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			logger.error("Exception in editCodeSet: " + e.getMessage());
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_CODE_SET);
		}
		
			return (mapping.findForward("Manage"));		
			
	}

	@SuppressWarnings("unchecked")
	public ActionForward updateCodeSet(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setManageList(null);
		CodeSetDT dt = (CodeSetDT) manageForm.getSelection();
		String mode = request.getParameter("mode");
		String confirmMesg = null;
		try {
			SRTAdminUtil.trimSpaces(dt);
			manageForm.setReturnToLink("<a href=\"/nbs/ManageCodeSet.do?method=searchLabSubmit\">Return To Manage Results</a> ");
			Object[] searchParams = new Object[] {dt};
			Object[] oParams = new Object[] { JNDINames.CODE_SET_DAO_CLASS, "updateCodeSet", searchParams };
			SRTAdminUtil.processRequest(oParams, request.getSession());
			
			
			CodeSetGpMetaDataDT codeSetGpMetaDataDt = new CodeSetGpMetaDataDT();
			codeSetGpMetaDataDt.setCodeSetNm(HTMLEncoder.encodeHtml(dt.getCodeSetNm()));
			codeSetGpMetaDataDt.setCodeSetGroupId(dt.getCodeSetGroupId());
			codeSetGpMetaDataDt.setCodeSetDescTxt(HTMLEncoder.encodeHtml(dt.getCodeSetDescTxt()));
			codeSetGpMetaDataDt.setCodeSetShortDescTxt(HTMLEncoder.encodeHtml(dt.getValueSetNm()));
			if(dt.getLdfPicklistIndCd()!= null && dt.getLdfPicklistIndCd().equals("Y")){
				codeSetGpMetaDataDt.setLdfPicklistIndCd("Y");
				}
			else
				codeSetGpMetaDataDt.setLdfPicklistIndCd(null);
			
			codeSetGpMetaDataDt.setVads_value_set_code(HTMLEncoder.encodeHtml(dt.getValueSetCode()));
			codeSetGpMetaDataDt.setPhinStadValueSetInd(HTMLEncoder.encodeHtml(dt.getPhinStadValueSetInd()));
			dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
			SRTAdminUtil.trimSpaces(codeSetGpMetaDataDt);
			
			Object[] searchParamsState = new Object[] {codeSetGpMetaDataDt};
			Object[] oParamsState = new Object[] { JNDINames.CODE_SET_DAO_CLASS, "updateCodeSetGRMetadata", searchParamsState };
			SRTAdminUtil.processRequest(oParamsState, request.getSession());
		
			if(mode != null && mode.equals("view"))
			{
				confirmMesg = " has been successfully updated in the system.";
				request.setAttribute("ConfirmMesg", confirmMesg);
				request.setAttribute("codeSetNm", dt.getCodeSetNm());
			}
			
			// populate the manageList
			ArrayList<Object> codesetLibraryList = new ArrayList<Object> ();
			Object[] search = {};
			Object[] param = new Object[] { daoName, "getExistingCodeSets", search };
			codesetLibraryList = (ArrayList<Object> ) SRTAdminUtil.processRequest(param, request.getSession());
			NBSContext.store(request.getSession(true), NBSConstantUtil.DSCodeSetList, codesetLibraryList);
			
			// To display Active/Inactive button on view
			String activeInd = dt.getStatusCd();
			if(activeInd != null && activeInd.equals("A"))
			{
				request.setAttribute("ActiveInd", "Inactive");
			}else if(activeInd != null && activeInd.equals("I"))
			{
				request.setAttribute("ActiveInd", "Active");
			}
			if(dt.getValueSetTypeCd()!= null && dt.getValueSetTypeCd().equals("SYS") )
				request.setAttribute("SYSValue", "SYS");
			//Edit and Make Inactive button action
			manageForm.getAttributeMap().put("EditValueSet", "/nbs/ManageCodeSet.do?method=editCodeSet&mode=view&codeSetNm="+dt.getCodeSetNm());
			manageForm.getAttributeMap().put("MakeInactive", "/nbs/ManageCodeSet.do?method=makeCodeSetInactive&codeSetNm="+dt.getCodeSetNm());
			manageForm.getAttributeMap().put("MakeActive", "/nbs/ManageCodeSet.do?method=makeCodeSetActive&codeSetNm="+dt.getCodeSetNm());
			
			// get the code_value_general data from the code_set_nm	
			Object[] codeSetParams = null;
			
			codeSetParams = new Object[] {dt.getCodeSetNm()};;
			
			Object[] oParamsCVG = new Object[] { JNDINames.CODE_VALUE_GENERAL_DAO_CLASS, "retrieveCodeSetValGenFields", codeSetParams };
			ArrayList<Object> dtListCVG = (ArrayList<Object> ) SRTAdminUtil.processRequest(oParamsCVG, request.getSession());
			Iterator<?> iter = dtListCVG.iterator();
			while(iter.hasNext()) {
				CodeValueGeneralDT dtCVG = (CodeValueGeneralDT) iter.next();
				dtCVG.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dtCVG.getStatusCd(),"NBS_STATUS_CD"));
				String viewUrl = "<a href=\"javascript:viewConceptCd('" + HTMLEncoder.encodeHtml(dtCVG.getCodeSetNm()) + "','" + HTMLEncoder.encodeHtml(dtCVG.getCode()) + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" title = \"View\" alt = \"View\" /></a>";
				dtCVG.setViewLink(viewUrl);
				String editUrl = "";//"<a href=\"javascript:editConceptCd('" + dtCVG.getCodeSetNm() + "','" + dtCVG.getCode() + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" /></a>";
				if(request.getAttribute("SYSValue")!= null && request.getAttribute("SYSValue").toString().equals("SYS"))
					 editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
				else
					editUrl = "<a href=\"javascript:editConceptCd('" + HTMLEncoder.encodeHtml(dtCVG.getCodeSetNm()) + "','" + HTMLEncoder.encodeHtml(dtCVG.getCode()) + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>";
				
				dtCVG.setEditLink(editUrl);
			}			
			manageForm.setCodeValueGnList(dtListCVG);
			request.setAttribute("CodeValGNList",dtListCVG);
			request.setAttribute("queueConcept", String.valueOf(dtListCVG.size()));
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, "edit",NEDSSConstants.CODE_SET);
			logger.error("Exception in updateCodeSet: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_CODE_SET);
			return (mapping.findForward("Manage"));

		}
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_CODE_SET);
		request.setAttribute("manageList", NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSCodeSetList));
		return (mapping.findForward("Manage"));		
	}
	
	
	public ActionForward viewCodeSet(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
			
			SrtManageForm manageForm = (SrtManageForm) form;
			String activeInd = null;
			String importSuccess = (String)manageForm.getAttributeMap().get("importSuccess");
			ArrayList<Object> dtList = new ArrayList<Object> ();
			String codeSetNm = null;
			try {
				manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
				
				String cnxt = request.getParameter("context");
				if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {				
					manageForm.setSelection(manageForm.getOldDT());
					return (mapping.findForward("default"));
				}	
				codeSetNm = request.getParameter("codeSetNm");
				if((codeSetNm != null && codeSetNm.length() > 0) ) {
					ArrayList<?> codeSetList = (ArrayList<?>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSCodeSetList);
					Iterator<?> iter = codeSetList.iterator();
					while(iter.hasNext()) {			
						CodeSetDT dt = (CodeSetDT) iter.next();
						if(dt.getCodeSetNm().equalsIgnoreCase(codeSetNm)){
							if(dt.getValueSetTypeCd()!= null && dt.getValueSetTypeCd().equals("SYS") )
								request.setAttribute("SYSValue", "SYS");
							dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
							//Check to see if Active/Inactive button 
							activeInd = dt.getStatusCd();
							if(activeInd != null && activeInd.equals("A"))
							{
								request.setAttribute("ActiveInd", "Inactive");
							}else if(activeInd != null && activeInd.equals("I"))
							{
								request.setAttribute("ActiveInd", "Active");
							}
							manageForm.setSelection(dt);
							manageForm.setOldDT(dt);
							break;
						}
					}
				} 
			// get the code_value_general data from the code_set_nm	
				Object[] codeSetParams = null;
				
				codeSetParams = new Object[] {codeSetNm};;
				
				Object[] oParams = new Object[] { JNDINames.CODE_VALUE_GENERAL_DAO_CLASS, "retrieveCodeSetValGenFields", codeSetParams };
				dtList = (ArrayList<Object> ) SRTAdminUtil.processRequest(oParams, request.getSession());
				Iterator<?> iter = dtList.iterator();
				while(iter.hasNext()) {
					CodeValueGeneralDT dt = (CodeValueGeneralDT) iter.next();
					dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
					String viewUrl = "<a href=\"javascript:viewConceptCd('" + dt.getCodeSetNm() + "','" + dt.getCode() + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
					dt.setViewLink(viewUrl);
					String editUrl = "";
					if(request.getAttribute("SYSValue")!= null && request.getAttribute("SYSValue").toString().equals("SYS"))
						 editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
					else
						editUrl = "<a href=\"javascript:editConceptCd('" + dt.getCodeSetNm() + "','" + dt.getCode() + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>";
					dt.setEditLink(editUrl);
					
				}			
				manageForm.setCodeValueGnList(dtList);
				
				filterConceptCd(manageForm,request, dtList);
				request.setAttribute("CodeValGNList",dtList);
				request.setAttribute("CodesetNm", codeSetNm);
				request.setAttribute("queueConcept", String.valueOf(dtList.size()));
				//Edit and Make Inactive button action
				manageForm.getAttributeMap().put("EditValueSet", "/nbs/ManageCodeSet.do?method=editCodeSet&mode=view&codeSetNm="+codeSetNm);
				manageForm.getAttributeMap().put("MakeInactive", "/nbs/ManageCodeSet.do?method=makeCodeSetInactive&codeSetNm="+codeSetNm);
				manageForm.getAttributeMap().put("MakeActive", "/nbs/ManageCodeSet.do?method=makeCodeSetActive&codeSetNm="+codeSetNm);
				String conceptConfMsg = (String)request.getSession().getAttribute("confirmConceptMsg");
				if(conceptConfMsg != null && conceptConfMsg.length()>0)
				{
					request.setAttribute("confirmConceptMsg", conceptConfMsg);
					request.getSession().removeAttribute("confirmConceptMsg");
				}
				// added by jayasudha to display add success message
				 String confirmAddConceptMsg = (String)request.getSession().getAttribute("confirmAddConceptMsg");
					if(confirmAddConceptMsg != null && confirmAddConceptMsg.length()>0)
					{
						if(request.getParameter("fromView")==null)
							request.setAttribute("confirmAddConceptMsg", confirmAddConceptMsg);
						//request.getSession().removeAttribute("confirmAddConceptMsg");
					}
					
				String errorConcept = (String)request.getSession().getAttribute("errorConcept");
				if(errorConcept != null && errorConcept.length()>0)
				{
					request.setAttribute("errorConcept", errorConcept);
					request.getSession().removeAttribute("errorConcept");
				}
				
			} catch (Exception e) {
				request.setAttribute("error", e.getMessage());
				logger.error("Exception in viewCodeSet: " + e.getMessage());
				e.printStackTrace();
			} finally {
				request.setAttribute("manageList", NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSCodeSetList));
				request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_CODE_SET);
			}
			return (mapping.findForward("Manage"));
		}
		
	
	
	public ActionForward createCodeSet(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		   
		SrtManageForm manageForm = (SrtManageForm) form;
		   try {			 
			   manageForm.resetSelection();
				manageForm.setSelection(new CodeSetDT());
				manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
				manageForm.getAttributeMap().put("cancel", "/nbs/ManageCodeSet.do?method=ViewValueSetLib&actionMode=Manage&context=cancel");
			    manageForm.getAttributeMap().put("submit", "/nbs/ManageCodeSet.do?method=createCodeSetSubmit");
			    CodeSetDT dt = (CodeSetDT)manageForm.getSelection();
					dt.setCodeSetNm(dt.getValueSetCode());
					dt.setLdfPicklistIndCd("Y");
					dt.setAssigningAuthorityCd("L");
					dt.setAssigningAuthorityDescTxt("Local");
					dt.setPhinStadValueSetInd("N");
					dt.setSourceDomainNm(null);
					dt.setSourceVersionTxt(null);
					dt.setIsModifiableInd("Y");
							
		   } catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in createCodeSet: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSCodeSetList));
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_CODE_SET);
			}
 			return (mapping.findForward("Manage"));	
		}


	@SuppressWarnings("unchecked")
	public ActionForward createCodeSetSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setManageList(null);
		request.setAttribute("manageList", NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSCodeSetList));
		manageForm.getAttributeMap().put("cancel", "/nbs/ManageCodeSet.do?method=ViewValueSetLib&context=cancel");
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
		StringBuffer confirmMesg = new StringBuffer();
		CodeSetDT dt = (CodeSetDT)manageForm.getSelection();
		dt.setCodeSetNm(dt.getValueSetCode());
		dt.setCodeSetShortDescTxt(dt.getValueSetNm());
		//dt.setVadsValueSetCode(dt.getValueSetCode());
		dt.setAddUserId(new Long(Long.parseLong(nbsSecurityObj.getEntryID())));
		CodeSetGpMetaDataDT metaDataDT = new CodeSetGpMetaDataDT();
		metaDataDT.setCodeSetNm(dt.getCodeSetNm());
		metaDataDT.setCodeSetGroupId(dt.getCodeSetGroupId());
		metaDataDT.setCodeSetDescTxt(dt.getCodeSetDescTxt());
		metaDataDT.setPhinStadValueSetInd(dt.getPhinStadValueSetInd());
		metaDataDT.setVads_value_set_code(dt.getValueSetCode());
		
		 // added by jayasudha
		
	
	 	
	 	metaDataDT.setLocalCode(dt.getLocalCode());
	 	metaDataDT.setLongDisplayName(dt.getLongDisplayName());
	 	metaDataDT.setShortDisplayName(dt.getShortDisplayName());
	 	metaDataDT.setLocalEffectiveFromTime(dt.getLocalEffectiveFromTime());
	 	metaDataDT.setLocalEffectiveToTime(dt.getLocalEffectiveToTime());
	 	metaDataDT.setStatusCode(dt.getStatusCode());
	 	
	 	
		if(dt.getLdfPicklistIndCd().equals("Y")){
			metaDataDT.setLdfPicklistIndCd("Y");
				}
		else{
				metaDataDT.setLdfPicklistIndCd(null);
			}
		metaDataDT.setCodeSetShortDescTxt(dt.getValueSetNm());
		dt.setStatusCd("A");
		dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
		try {
			manageForm.getAttributeMap().clear();
			SRTAdminUtil.trimSpaces(dt);
			SRTAdminUtil.trimSpaces(metaDataDT);
			Object[] searchParams = new Object[] {dt,metaDataDT};
			Object[] oParams = new Object[] {daoName, "createCodeSet", searchParams};
			SRTAdminUtil.processRequest(oParams, request.getSession());
			
			// populate the manageList
			ArrayList<Object> codesetLibraryList = new ArrayList<Object> ();
			Object[] search = {};
			Object[] param = new Object[] { daoName, "getExistingCodeSets", search };
			codesetLibraryList = (ArrayList<Object> ) SRTAdminUtil.processRequest(param, request.getSession());
			NBSContext.store(request.getSession(true), NBSConstantUtil.DSCodeSetList, codesetLibraryList);
			
			// To display Active/Inactive button on view
			String activeInd = dt.getStatusCd();
			if(activeInd != null && activeInd.equals("A"))
			{
				request.setAttribute("ActiveInd", "Inactive");
			}else if(activeInd != null && activeInd.equals("I"))
			{
				request.setAttribute("ActiveInd", "Active");
			}
			//Edit and Make Inactive button action
			manageForm.getAttributeMap().put("EditValueSet", "/nbs/ManageCodeSet.do?method=editCodeSet&mode=view&codeSetNm="+dt.getCodeSetNm());
			manageForm.getAttributeMap().put("MakeInactive", "/nbs/ManageCodeSet.do?method=makeCodeSetInactive&codeSetNm="+dt.getCodeSetNm());
			manageForm.getAttributeMap().put("MakeActive", "/nbs/ManageCodeSet.do?method=makeCodeSetActive&codeSetNm="+dt.getCodeSetNm());
			request.setAttribute("CodesetNm", HTMLEncoder.encodeHtml(dt.getCodeSetNm()));
		} catch (Exception e) {
			String errorString = e.toString();
			if(errorString.contains("ValueSetName"))
				request.setAttribute("error","A record already exists with this Value Set Name. Please enter a unique Value Set Name to create a new record.");
			else
				SRTAdminUtil.handleErrors(e, request, NEDSSConstants.CREATE, NEDSSConstants.CODE_SET);
			request.setAttribute("manageList", manageForm.getManageList());
			manageForm.setSelection(dt);
			manageForm.getAttributeMap().put("cancel", "/nbs/ManageCodeSet.do?method=createCodeSet");
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_CODE_SET);
			manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			logger.error("Exception in createCodeSetSubmit: " + e.getMessage());
			return (mapping.findForward("Manage"));
		}
		manageForm.setSelection(dt);
		manageForm.setOldDT(dt);
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_CODE_SET);
		confirmMesg.append(" has been successfully added to the system.")
		.append(" You can add concept codes to this new value set by clicking on the Add New button in the Value Set Concept portion of this  page.");
		request.setAttribute("ConfirmMesg", confirmMesg.toString());
		request.setAttribute("codeSetNm", dt.getCodeSetNm());
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		return (mapping.findForward("Manage"));
		
	}
	
	public ActionForward importValueSetLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		SrtManageForm manageForm = (SrtManageForm) form;
		String actionForward = "";
		 try {	
			 manageForm.setSelection(new CodeSetDT());
			 CodeSetDT dt = (CodeSetDT)manageForm.getSelection();
			 String newVads = request.getParameter("vads");
			 String isMoreAllowed = "false";
			 dt.setValueSetCode(newVads);
			 boolean isVadsInSystem = false;
			 StringBuffer confirmSb = new StringBuffer();
			 StringBuffer confirmVSSb = new StringBuffer();
			// StringBuffer displaySb = new StringBuffer();
			 PhinVadsSystemVO phinVadsSystemVO = null;
			 boolean forceEJBcall = false;
			// Reset Pagination first time
			boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
			if (initLoad && !PaginationUtil._dtagAccessed(request)) {
				forceEJBcall = true;
			}
			 // Check if user is trying to import a VAD thats already in the system
			 if(dt.getValueSetCode()!= null)
			 {
				 Boolean isVadsInSystemObj = (Boolean)SRTAdminUtil.checkVADSInSystem(request.getSession(),dt);
				 isVadsInSystem = isVadsInSystemObj.booleanValue();
			 }
			 
			 // Check if the user opts to import a VAD that is not there in the PHIN VADS
			 String[] arguments = new String[] {dt.getValueSetCode()};
			 if(!isVadsInSystem)
			 {
				 if(forceEJBcall)
				 {
					 if(request.getParameter("isMoreAllowed") != null)
						 isMoreAllowed = request.getParameter("isMoreAllowed");
					 phinVadsSystemVO =ValueSetVersionUtil.findPhinVads(arguments, request, isMoreAllowed);
					 if(request.getAttribute("ConceptCount") != null)
					 {
						 int conceptCount = (int)request.getAttribute("ConceptCount");
						 confirmVSSb.append(" The ").append(HTMLEncoder.encodeHtml(dt.getValueSetCode())).append(" contains ").append(conceptCount).append(" concepts.")
						 .append(" If you would still like to import the value set, click Ok. if not, click Cancel.");
						 request.setAttribute("newVads", dt.getValueSetCode());
						 request.setAttribute("confirm_Concept_Msg", confirmVSSb.toString()); 
						 actionForward = "importFail";
						 return (mapping.findForward(actionForward));
					 }
				 }
				 else
					 phinVadsSystemVO = manageForm.getPhinVadsSystemVo();
			 }
			 
			 if(isVadsInSystem){
				 confirmSb.append("You have indicated that you would like to import the PHIN standard value set ").append(HTMLEncoder.encodeHtml(dt.getValueSetCode())).append(".") 
				 .append(" This value set already exists within the system.");
				 actionForward = "importFail";
			 }
			 else if(phinVadsSystemVO == null)
			 {
				 confirmSb.append("You have indicated that you would like to import the PHIN standard value set ").append(HTMLEncoder.encodeHtml(dt.getValueSetCode())).append(".")
				 .append(" No value sets were found matching this value set code. Please check the value set code and try again.");
				 
				 actionForward = "importFail";
			 }else {
				 manageForm.setPhinVadsSystemVo(phinVadsSystemVO);
				 ArrayList<Object> codeValueGeneralDtCollection = phinVadsSystemVO.getTheCodeValueGenaralDtCollection();
				 filterConceptCd(manageForm,request, codeValueGeneralDtCollection);
				 request.setAttribute("queueCountVads", String.valueOf(codeValueGeneralDtCollection.size()));
				 request.setAttribute("phinList", codeValueGeneralDtCollection);
				 CodeSetDT codesetDt = phinVadsSystemVO.getCodeSetDT();
				 manageForm.setSelection(codesetDt);
				 manageForm.setOldDT(codesetDt);
				
				 
				 actionForward = "importValueSet";
			 }
			 
			 request.setAttribute("confirm_Msg", confirmSb.toString());
			// displaySb.append("You have indicated that you would like to import the PHIN standard value set <b>").append(HTMLEncoder.encodeHtml(dt.getValueSetCode()))
			// .append("</b>. Please review the value set details below and then select OK to continue with the import process, or select Cancel to return to Manage Value Sets.");
			 
			 request.setAttribute("VadsValueSetCode1", "You have indicated that you would like to import the PHIN standard value set ");
			 request.setAttribute("VadsValueSetCode", dt.getValueSetCode());
			 request.setAttribute("VadsValueSetCode2", ". Please review the value set details below and then select OK to continue with the import process, or select Cancel to return to Manage Value Sets.");
			 request.setAttribute("queueCount", manageForm.getAttributeMap().get("queueCount"));
			 
		 }catch (Exception e) {
			 	SRTAdminUtil.handleErrors(e, request, "import", "");
				request.setAttribute("error", "Unable to connect to the service. Please alert your system administrator with the following details: "+ HTMLEncoder.encodeHtml(e.getMessage()));
				logger.error("Exception in importValueSet: " + e.getMessage());
				e.printStackTrace();
				return (mapping.findForward("default"));
		} finally {
				request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.IMPORT_VALUE_SET);
		}
		
		return (mapping.findForward(actionForward));
	}
		
	public ActionForward importValueSetStore(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		SrtManageForm manageForm = (SrtManageForm) form;
		String actionForward = "viewImportCodeSet";
		PhinVadsSystemVO phinVadsVo = null;
		ArrayList<Object> manageList = new ArrayList<Object> ();
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
		phinVadsVo = (PhinVadsSystemVO)manageForm.getPhinVadsSystemVo();
		try{
			//populate remaining fields
			phinVadsVo.getCodeSetGpMetaDataDT().setLdfPicklistIndCd("Y");
			phinVadsVo.getCodeSetGpMetaDataDT().setPhinStadValueSetInd("Y");

			CodeSetDT codesetDt = phinVadsVo.getCodeSetDT();
			codesetDt.setAssigningAuthorityCd("2.16.840.1.114222");
			codesetDt.setAssigningAuthorityDescTxt("Centers of Disease Control and Prevention");
			codesetDt.setIsModifiableInd("Y");
			codesetDt.setSourceDomainNm("VADS");
			codesetDt.setStatusCd("A");
			codesetDt.setValueSetTypeCd("PHIN");
			codesetDt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(codesetDt.getStatusCd(),"NBS_STATUS_CD"));
			codesetDt.setLdfPicklistIndCd("Y");
			codesetDt.setPhinStadValueSetInd(phinVadsVo.getCodeSetGpMetaDataDT().getPhinStadValueSetInd());
			codesetDt.setCodeSetShortDescTxt(phinVadsVo.getCodeSetGpMetaDataDT().getCodeSetShortDescTxt());
			codesetDt.setAddUserId(Long.valueOf(99999999));
			SRTAdminUtil.makeCodeSetLink(codesetDt, VIEW);
			SRTAdminUtil.makeCodeSetLink(codesetDt, EDIT);
			SRTAdminUtil.trimSpaces(codesetDt);
			manageList.add(codesetDt);

			ArrayList<Object> codeValueGenaralDtCollection = phinVadsVo.getTheCodeValueGenaralDtCollection();
			if(codeValueGenaralDtCollection.size()>0){
				Iterator<Object> iter = codeValueGenaralDtCollection.iterator();
				while(iter.hasNext())
				{
					CodeValueGeneralDT codeValueGeneralDt = (CodeValueGeneralDT)iter.next();
					codeValueGeneralDt.setIndentLevelNbr(new Integer(1));
					codeValueGeneralDt.setIsModifiableInd("Y");
					codeValueGeneralDt.setStatusCd("A");
					codeValueGeneralDt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(codeValueGeneralDt.getStatusCd(),"NBS_STATUS_CD"));
					codeValueGeneralDt.setConceptTypeCd("PHIN");
					codeValueGeneralDt.setAddUserId(new Long(Long.parseLong(nbsSecurityObj.getEntryID())));
					SRTAdminUtil.trimSpaces(codeValueGeneralDt);
				}
			}
			SRTAdminUtil.trimSpaces(phinVadsVo.getCodeSetGpMetaDataDT());
			SRTAdminUtil.importValueSet(phinVadsVo,request.getSession());
		}catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, "create", "");
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			logger.error("Exception in importValueSetStore: " + e.getMessage());
			e.printStackTrace();
			ArrayList<Object> codeValueGeneralDtCollection = phinVadsVo.getTheCodeValueGenaralDtCollection();
			filterConceptCd(manageForm,request, codeValueGeneralDtCollection);
			request.setAttribute("queueCountVads", String.valueOf(codeValueGeneralDtCollection.size()));
			request.setAttribute("phinList", codeValueGeneralDtCollection);
			actionForward = "importValueSet";
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_CODE_SET);
		}
		manageForm.setManageList(manageList);	
		request.setAttribute("manageList", manageList);
		manageForm.setActionMode(SRTAdminConstants.MANAGE);
		manageForm.getAttributeMap().put("importSuccess","ImportSuccess");
		return (mapping.findForward(actionForward));
	}
	
	
	public ActionForward viewImportCodeSet(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		String activeInd = null;
		ArrayList<Object> dtList = new ArrayList<Object> ();
		String codeSetNm = null;
		StringBuffer ConfirmMesgImport = new StringBuffer();
		try {
			manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			
			String cnxt = request.getParameter("context");
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {				
				manageForm.setSelection(manageForm.getOldDT());
				return (mapping.findForward("default"));
			}	
			
			PhinVadsSystemVO phinVadsSystemVO = null;
			phinVadsSystemVO = manageForm.getPhinVadsSystemVo();
			CodeSetDT csDt = phinVadsSystemVO.getCodeSetDT();
			manageForm.setSelection(csDt);
			manageForm.setOldDT(csDt);
			csDt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(csDt.getStatusCd(),"NBS_STATUS_CD"));
			//Check to see if Active/Inactive button 
			activeInd = csDt.getStatusCd();
			if(activeInd != null && activeInd.equals("A"))
			{
				request.setAttribute("ActiveInd", "Inactive");
			}else if(activeInd != null && activeInd.equals("I"))
			{
				request.setAttribute("ActiveInd", "Active");
			}
			
			dtList = phinVadsSystemVO.getTheCodeValueGenaralDtCollection();
			Iterator<?> iter = dtList.iterator();
			while(iter.hasNext()) {
				CodeValueGeneralDT cvgdt = (CodeValueGeneralDT) iter.next();
				cvgdt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(cvgdt.getStatusCd(),"NBS_STATUS_CD"));
				String viewUrl = "<a href=\"javascript:viewConceptCd('" + cvgdt.getCodeSetNm() + "','" + cvgdt.getCode() + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
				cvgdt.setViewLink(viewUrl);
			
				String editUrl = "";//"<a href=\"javascript:editConceptCd('" + dtCVG.getCodeSetNm() + "','" + dtCVG.getCode() + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" /></a>";
				if(request.getAttribute("SYSValue")!= null && request.getAttribute("SYSValue").toString().equals("SYS"))
					 editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
				else
					editUrl = "<a href=\"javascript:editConceptCd('" + cvgdt.getCodeSetNm() + "','" + cvgdt.getCode() + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>";
				
				cvgdt.setEditLink(editUrl);
			}			
			manageForm.setCodeValueGnList(dtList);
			codeSetNm = csDt.getCodeSetNm();
			manageForm.getAttributeMap().remove("importSuccess");
			ConfirmMesgImport.append(" has been successfully added to the system.")
			.append(" You can add concept codes to this new value set by clicking on the Add New button in the Value Set Concept portion of this  page.");
			request.setAttribute("ConfirmMesgImport", ConfirmMesgImport.toString());
		
			
			filterConceptCd(manageForm,request, dtList);
			request.setAttribute("CodeValGNList",dtList);
			request.setAttribute("CodesetNm", codeSetNm);
			request.setAttribute("queueConcept", String.valueOf(dtList.size()));
			//Edit and Make Inactive button action
			manageForm.getAttributeMap().put("EditValueSet", "/nbs/ManageCodeSet.do?method=editCodeSet&mode=view&codeSetNm="+codeSetNm);
			manageForm.getAttributeMap().put("MakeInactive", "/nbs/ManageCodeSet.do?method=makeCodeSetInactive&codeSetNm="+codeSetNm);
			manageForm.getAttributeMap().put("MakeActive", "/nbs/ManageCodeSet.do?method=makeCodeSetActive&codeSetNm="+codeSetNm);
			String conceptConfMsg = (String)request.getSession().getAttribute("confirmConceptMsg");
			if(conceptConfMsg != null && conceptConfMsg.length()>0)
			{
				request.setAttribute("confirmConceptMsg", HTMLEncoder.encodeHtml(conceptConfMsg));
				request.getSession().removeAttribute("confirmConceptMsg");
			}
			
			// added by jayasudha to display  the add success message
//			 String confirmAddConceptMsg = (String)request.getSession().getAttribute("confirmAddConceptMsg");
//				if(confirmAddConceptMsg != null && confirmAddConceptMsg.length()>0)
//				{
//					request.setAttribute("confirmAddConceptMsg", confirmAddConceptMsg);
//					request.getSession().removeAttribute("confirmAddConceptMsg");
//				}
				
			String errorConcept = (String)request.getSession().getAttribute("errorConcept");
			if(errorConcept != null && errorConcept.length()>0)
			{
				request.setAttribute("errorConcept", HTMLEncoder.encodeHtml(errorConcept));
				request.getSession().removeAttribute("errorConcept");
			}
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in viewImportCodeSet: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSCodeSetList));
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_CODE_SET);
		}
		return (mapping.findForward("Manage"));
	}
	
	
	private void filterConceptCd(SrtManageForm manageForm, HttpServletRequest request, ArrayList<Object> theCodeValueGenaralDtCollection)
	{
		boolean existing = request.getParameter("existing") == null ? false : true;
		if(existing) {
			sortCodeSet(manageForm, theCodeValueGenaralDtCollection, existing, request);
		}
		
	}
	
	private void sortCodeSet(SrtManageForm manageForm, Collection<Object>  theCodeValueGenaralDtCollection, boolean existing, HttpServletRequest request) {

		// Retrieve sort-order and sort-direction from displaytag params
		String sortMethod = getSortMethod(request, manageForm);
		String direction = getSortDirection(request, manageForm);

		boolean directionFlag = true;
		if (direction != null && direction.equals("2"))
			directionFlag = false;


		NedssUtils util = new NedssUtils();
		
		//Read from properties file to determine default sort order
		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))|| (!existing)) {
			sortMethod = "getCodeSetShortDescTxt"; 
			directionFlag = true;
		}
		
		if (sortMethod != null && theCodeValueGenaralDtCollection != null && theCodeValueGenaralDtCollection.size() > 0) {
			util.sortObjectByColumnGeneric(sortMethod,(Collection<Object>) theCodeValueGenaralDtCollection, directionFlag);
			
		}
		
		//Finally put sort criteria in form
		String sortOrderParam = getSortCriteria(directionFlag == true ? "1" : "2", sortMethod);
		manageForm.getAttributeMap().put("sortSt", sortOrderParam);
		
		}
	private String getSortMethod(HttpServletRequest request, SrtManageForm manageForm ) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
	} else{
		return manageForm.getAttributeMap().get("methodName") == null ? null : (String) manageForm.getAttributeMap().get("methodName");
		}
		
	}
	private String getSortDirection(HttpServletRequest request, SrtManageForm manageForm) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		} else{
			return manageForm.getAttributeMap().get("sortOrder") == null ? "1": (String) manageForm.getAttributeMap().get("sortOrder");
		}
		
	}
	
	public static String getSortCriteria(String sortOrder, String methodName){
		
		String sortOrdrStr = null;
		if(methodName != null) {
			if(methodName.equals("getCode"))
				sortOrdrStr = "Concept Code";
			else if(methodName.equals("getCodeDescTxt"))
				sortOrdrStr = "Concept Name";
			else if(methodName.equals("getCodeShortDescTxt"))
				sortOrdrStr = "Preferred Concept Name";
			else if(methodName.equals("getCodeSystemDescTxt"))
				sortOrdrStr = "Code System Name";
			else if(methodName.equals("getCodeSetNm"))
				sortOrdrStr = "Value Set Code";
		} else {
			sortOrdrStr = "Concept Code";
		}
		
		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+"| in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+"| in descending order ";

		return sortOrdrStr;
			
	}	
	public ActionForward loadImportPopup(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		SrtManageForm manageForm = (SrtManageForm) form;	
		manageForm.setSearchCriteria("VADValueSetCd", "");
		return (mapping.findForward("importPopUp"));
	}
	public ActionForward makeCodeSetInactive(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		SrtManageForm manageForm = (SrtManageForm) form;	
		StringBuffer sb = new StringBuffer();
		String codeSetNm = HTMLEncoder.encodeHtml(request.getParameter("codeSetNm"));
		CodeSetDT dt = null;
		try{
			if((codeSetNm != null && codeSetNm.length() > 0) ) {
				ArrayList<?> codeSetList = (ArrayList<?>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSCodeSetList);
				Iterator<?> iter = codeSetList.iterator();
				while(iter.hasNext()) {			
					dt = (CodeSetDT) iter.next();
					if(dt.getCodeSetNm().equalsIgnoreCase(codeSetNm) ){
						dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
						if(dt.getValueSetTypeCd()!= null && dt.getValueSetTypeCd().equals("SYS") )
							request.setAttribute("SYSValue", "SYS");
						manageForm.setSelection(dt);
						manageForm.setOldDT(dt);
						break;
					}
				}
			}
			Object[] searchParams = new Object[] {dt.getCodeSetNm()};
			Object[] oParams = new Object[] { JNDINames.CODE_SET_DAO_CLASS, "inactivateCodeset", searchParams };
			SRTAdminUtil.processRequest(oParams, request.getSession());
			CodeSetDT activeDt = (CodeSetDT) manageForm.getSelection();
			activeDt.setStatusCd("I");
			activeDt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd("I","NBS_STATUS_CD"));
			manageForm.setSelection(activeDt);
			// To display Active/ Inactive button in the view page
			request.setAttribute("ActiveInd", "Active");
			sb.append(" has been made inactive.");
			request.setAttribute("ConfirmMesg", sb.toString());
			request.setAttribute("codeSetNm", codeSetNm);
			// Concept display
			// get the code_value_general data from the code_set_nm	
			Object[] codeSetParams = null;
			codeSetParams = new Object[] {codeSetNm};
			ArrayList<Object> dtList = new ArrayList<Object> ();
			Object[] oParamsCVG = new Object[] { JNDINames.CODE_VALUE_GENERAL_DAO_CLASS, "retrieveCodeSetValGenFields", codeSetParams };
			dtList = (ArrayList<Object> ) SRTAdminUtil.processRequest(oParamsCVG, request.getSession());
			Iterator<?> iter = dtList.iterator();
			while(iter.hasNext()) {
				CodeValueGeneralDT dtCVG = (CodeValueGeneralDT) iter.next();
				dtCVG.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dtCVG.getStatusCd(),"NBS_STATUS_CD"));
				String viewUrl = "<a href=\"javascript:viewConceptCd('" + dtCVG.getCodeSetNm() + "','" + dtCVG.getCode() + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
				dtCVG.setViewLink(viewUrl);
				//String editUrl = "<a href=\"javascript:editConceptCd('" + dtCVG.getCodeSetNm() + "','" + dtCVG.getCode() + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"Edit\"/></a>";
				String editUrl = "";//"<a href=\"javascript:editConceptCd('" + dtCVG.getCodeSetNm() + "','" + dtCVG.getCode() + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" /></a>";
				if(request.getAttribute("SYSValue")!= null && request.getAttribute("SYSValue").toString().equals("SYS"))
					 editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
				else
					editUrl = "<a href=\"javascript:editConceptCd('" + dtCVG.getCodeSetNm() + "','" + dtCVG.getCode() + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>";
				
				dtCVG.setEditLink(editUrl);
			}			
			manageForm.setCodeValueGnList(dtList);
			request.setAttribute("CodeValGNList",dtList);
			request.setAttribute("CodesetNm", codeSetNm);
			request.setAttribute("queueConcept", String.valueOf(dtList.size()));
		}catch (Exception e) {
			//manageForm.setManageList(conditionList);
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in makeCodeSetInactive: " + e.getMessage());
		}finally
		{
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_CODE_SET);
		}
		return (mapping.findForward("Manage"));
	}
	public ActionForward makeCodeSetActive(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		SrtManageForm manageForm = (SrtManageForm) form;	
		StringBuffer sb = new StringBuffer();
		String codeSetNm = request.getParameter("codeSetNm");
		CodeSetDT dt = null;
		try{
			if((codeSetNm != null && codeSetNm.length() > 0)) {
				ArrayList<?> codeSetList = (ArrayList<?>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSCodeSetList);
				Iterator<?> iter = codeSetList.iterator();
				while(iter.hasNext()) {			
					dt = (CodeSetDT) iter.next();
					if(dt.getCodeSetNm().equalsIgnoreCase(codeSetNm) ){
						dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
						if(dt.getValueSetTypeCd()!= null && dt.getValueSetTypeCd().equals("SYS") )
							request.setAttribute("SYSValue", "SYS");
						manageForm.setSelection(dt);
						manageForm.setOldDT(dt);
						break;
					}
				}
			}
			Object[] searchParams = new Object[] {dt.getCodeSetNm()};
			Object[] oParams = new Object[] { JNDINames.CODE_SET_DAO_CLASS, "activateCodeset", searchParams };
			SRTAdminUtil.processRequest(oParams, request.getSession());
			
			CodeSetDT activeDt = (CodeSetDT) manageForm.getSelection();
			activeDt.setStatusCd("A");
			activeDt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd("A","NBS_STATUS_CD"));
			manageForm.setSelection(activeDt);
			request.setAttribute("ActiveInd", "Inactive");
			sb.append(" has been made active.");
			request.setAttribute("ConfirmMesg", sb.toString());
			request.setAttribute("codeSetNm", codeSetNm);
			
			// Concept display
			// get the code_value_general data from the code_set_nm	
			Object[] codeSetParams = null;
			codeSetParams = new Object[] {codeSetNm};
			ArrayList<Object> dtList = new ArrayList<Object> ();
			Object[] oParamsCVG = new Object[] { JNDINames.CODE_VALUE_GENERAL_DAO_CLASS, "retrieveCodeSetValGenFields", codeSetParams };
			dtList = (ArrayList<Object> ) SRTAdminUtil.processRequest(oParamsCVG, request.getSession());
			Iterator<?> iter = dtList.iterator();
			while(iter.hasNext()) {
				CodeValueGeneralDT dtCVG = (CodeValueGeneralDT) iter.next();
				dtCVG.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dtCVG.getStatusCd(),"NBS_STATUS_CD"));
				String viewUrl = "<a href=\"javascript:viewConceptCd('" + dtCVG.getCodeSetNm() + "','" + dtCVG.getCode() + "');\"><img src=\"page_white_text.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
				dtCVG.setViewLink(viewUrl);
				//String editUrl = "<a href=\"javascript:editConceptCd('" + dtCVG.getCodeSetNm() + "','" + dtCVG.getCode() + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"Edit\"/></a>";
				String editUrl = "";//"<a href=\"javascript:editConceptCd('" + dtCVG.getCodeSetNm() + "','" + dtCVG.getCode() + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" /></a>";
				if(request.getAttribute("SYSValue")!= null && request.getAttribute("SYSValue").toString().equals("SYS"))
					 editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
				else
					editUrl = "<a href=\"javascript:editConceptCd('" + dtCVG.getCodeSetNm() + "','" + dtCVG.getCode() + "');\"><img src=\"page_white_edit.gif\" tabindex=\"0\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>";
				
				dtCVG.setEditLink(editUrl);
			}				
			manageForm.setCodeValueGnList(dtList);
			request.setAttribute("CodeValGNList",dtList);
			request.setAttribute("CodesetNm", codeSetNm);
			request.setAttribute("queueConcept", String.valueOf(dtList.size()));
		}catch (Exception e) {
			//manageForm.setManageList(conditionList);
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			logger.error("Exception in makeCodeSetActive: " + e.getMessage());
			e.printStackTrace();
		}finally
		{
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_CODE_SET);
		}
		return (mapping.findForward("Manage"));
	
	}
	
	public ActionForward viewCodeValGenCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		SrtManageForm manageForm = (SrtManageForm) form;
	try {
		
		String cnxt = request.getParameter("context");
		if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {					
			manageForm.setCodeValGnSelection(manageForm.getOldDT());
			setViewActionMode(manageForm);
			return (mapping.findForward("conceptPopUp"));
		}
		CodeSetDT codesetDt = (CodeSetDT)manageForm.getSelection();
		String codeValGenCode = request.getParameter("codeVal");
		String codesetNm = request.getParameter("codesetNm");
		if(codeValGenCode != null && codeValGenCode.length() > 0 && codesetNm != null && codesetNm.length()>0) {
			ArrayList<?> testList = manageForm.getCodeValueGnList();
			Iterator<?> iter = testList.iterator();
			while(iter.hasNext()) {			
				CodeValueGeneralDT dt = (CodeValueGeneralDT) iter.next();
				if(dt.getCode().equalsIgnoreCase(codeValGenCode) && codesetDt.getCodeSetNm().equalsIgnoreCase(codesetNm)) {
					manageForm.setCodeValGnSelection(dt);
					manageForm.setOldDT(dt);
					request.setAttribute("codeSetNm", dt.getCodeSetNm());
					break;
				}
			}
		}
		request.setAttribute("codeValGenCode", HTMLEncoder.encodeHtml(codeValGenCode));
		request.setAttribute("codesetNm", HTMLEncoder.encodeHtml(codesetDt.getCodeSetNm()));
		manageForm.getAttributeMap().put("EditConcept", "/nbs/ManageCodeSet.do?method=editCodeValGenCode&code="+codeValGenCode);
		manageForm.getAttributeMap().put("CancelConcept", "/nbs/ManageCodeSet.do?method=viewCodeSet&codeSetNm="+codesetDt.getCodeSetNm());
		//setViewActionMode(manageForm);
	} catch (Exception e) {
		request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
		logger.error("Exception in viewCodeValGenCode: " + e.getMessage());
		e.printStackTrace();
	} finally {
		request.setAttribute("SearchResult", "SearchResult");
		request.setAttribute("manageList", NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSCodeSetList));
		//request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_CODE_VALUE_GEN);
		manageForm.setPageTitle(SRTAdminConstants.VIEW_VALUE_SET, request);
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
	}
	
	
	return (mapping.findForward("conceptPopUp"));
	}
	private void setViewActionMode(SrtManageForm manageForm) {
		
		CodeValueGeneralDT dt = (CodeValueGeneralDT) manageForm.getCodeValGnSelection();
		String codeSetNm = dt.getCodeSetNm();
		if(! codeSetNm.equals(ASSGN_AUTHORITY)  && ! codeSetNm.equals(CODE_SYSTEM))
			manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		else
			manageForm.setActionMode("ViewSP");						
	}
	
	public ActionForward editCodeValGenCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		SrtManageForm manageForm = (SrtManageForm) form;
		CodeValueGeneralDT dt = new CodeValueGeneralDT();
		try {			
			CodeSetDT codesetDt = (CodeSetDT)manageForm.getSelection();
			String codeValGenCode = request.getParameter("codeVal");		
			String codesetNm = request.getParameter("codesetNm");
			if(codeValGenCode != null && codeValGenCode.length() > 0 && codesetNm != null && codesetNm.length()>0) {
				ArrayList<?> testList = manageForm.getCodeValueGnList();
				Iterator<?> iter = testList.iterator();				
				while(iter.hasNext()) {			
					 dt= (CodeValueGeneralDT) iter.next();
					if(dt.getCode().equalsIgnoreCase(codeValGenCode)&& codesetDt.getCodeSetNm().equalsIgnoreCase(codesetNm)) {
						SRTAdminUtil.updateCodingSystemforEdit(dt);
						manageForm.setCodeValGnSelection(dt);						
						manageForm.setOldDT(dt);
						request.setAttribute("codeSetNm", HTMLEncoder.encodeHtml(dt.getCodeSetNm()));
						request.setAttribute(NEDSSConstants.ADD_TIME_FOR_SRT_FILTERING, dt.getAddTime());
						break;
					}
				}
			} 			
			String codeSetNm = dt.getCodeSetNm();
			manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			
			manageForm.getAttributeMap().put("submit", "/nbs/ManageCodeSet.do?method=updateCodeValGenCode");
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.EDIT_LOAD_ACTION,NEDSSConstants.CODE);
			logger.error("Exception in editCodeValueGeneral: " + e.getMessage());
		} finally {
			request.setAttribute("SearchResult", "SearchResult");
			request.setAttribute("manageList", manageForm.getCodeValueGnList());
			manageForm.setPageTitle(SRTAdminConstants.EDIT_VALUE_SET, request);
		}
		
		return (mapping.findForward("conceptPopUp"));		
		
	}
	
	
	
	public ActionForward createCodeValueGenCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		   
		   SrtManageForm manageForm = (SrtManageForm) form;
		   try {			 
			manageForm.resetCodeValGnSelection();
			CodeValueGeneralDT codeValueGeneralDT =  new CodeValueGeneralDT();
			CodeSetDT codesetDt = (CodeSetDT)manageForm.getSelection();
			String codeSetNm = codesetDt.getCodeSetNm();
			codeValueGeneralDT.setCodeSetNm(codeSetNm);
			// displaying the code system num value as empty at add new screen
			codeValueGeneralDT.setCodeSystemCd("");
			codeValueGeneralDT.setCodeSystemDescTxt("");
			
			//Effective from time = Current date
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			String dateString = dateFormat.format(date);
			codeValueGeneralDT.setEffectiveFromTime(dateString);
			
			//Status Code = Active
			codeValueGeneralDT.setStatusCd("A");
			
		    manageForm.setCodeValGnSelection(codeValueGeneralDT);
		   	manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);

		    manageForm.getAttributeMap().put("submit", "/nbs/ManageCodeSet.do?method=createCodeValGenSubmit");
		    manageForm.getAttributeMap().put("CancelConcept", "/nbs/ManageCodeSet.do?method=viewCodeSet&codeSetNm="+codesetDt.getCodeSetNm());
		    request.setAttribute("codeSetNm", codesetDt.getCodeSetNm());
		    Date date1 = new java.util.Date();
		    Timestamp currentTime = new Timestamp(date1.getTime());
		    request.setAttribute(NEDSSConstants.ADD_TIME_FOR_SRT_FILTERING, currentTime);
		    
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.CREATE, NEDSSConstants.CODE);
			request.setAttribute("manageList", manageForm.getCodeValueGnList());
			request.setAttribute("SearchResult", "SearchResult");
			logger.error("Exception in createCodeValueGenCode: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("SearchResult", "SearchResult");
			request.setAttribute("manageList", manageForm.getCodeValueGnList());
			manageForm.setPageTitle(SRTAdminConstants.ADD_VALUE_SET, request);
		}
		return (mapping.findForward("conceptPopUp"));		
	}
	
	}


