package gov.cdc.nedss.webapp.nbs.action.pagemanagement.managecondition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.pagemanagement.wa.dt.ConditionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.DynamicPageConstants;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managecondition.ManageConditionForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;

public class ManageConditionAction extends DispatchAction{
	
	static final LogUtils logger = new LogUtils(ManageConditionAction.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	PropertyUtil properties = PropertyUtil.getInstance();
	QueueUtil queueUtil = new QueueUtil();
	private static final String daoName = JNDINames.CONDITION_DAO_CLASS;
	private static final String defaultCodingSys = "2.16.840.1.114222.4.5.277";
	
	public ActionForward ViewConditionLib(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException,
    ServletException {
		
			ManageConditionForm manageForm = (ManageConditionForm) form;	
			
			try {
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
					manageForm.clearAll();
					forceEJBcall = true;
					// get the number of records to be displayed per page
					Integer queueSize = properties.getQueueSize(NEDSSConstants.PAGE_MANAGEMENT_CONDITION_LIBRARY_QUEUE_SIZE);
					manageForm.getAttributeMap().put("queueSize", queueSize);
				}
				String contextAction = request.getParameter("context");
				if (contextAction != null) {
					if(contextAction.equals("cancel"))
						forceEJBcall = false;
					else if(contextAction.equals("ReturnToManage"))
						forceEJBcall = true;
				}
				//To make sure SelectAll is checked, see if no criteria is applied
				if(manageForm.getSearchCriteriaArrayMap().size() == 0)
					request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
				String actionMd = request.getParameter("actionMode");
				if(manageForm.getActionMode()== null || (actionMd!= null && actionMd.equalsIgnoreCase("Manage")) || manageForm.getActionMode().equals("Manage") )
					manageForm.setActionMode(DynamicPageConstants.MANAGE);
				else{
					manageForm.setActionMode(manageForm.getActionMode());
					ConditionDT dt = (ConditionDT) manageForm.getSelection();
					String activeInd = dt.getStatusCd();
					if(activeInd != null && activeInd.equals("A"))
					{
						request.setAttribute("ActiveInd", "Inactive");
					}else if(activeInd != null && activeInd.equals("I"))
					{
						request.setAttribute("ActiveInd", "Active");
					}
				}

				request.setAttribute("viewHref","/nbs/ManageCondition.do?method=viewCondition&fromConditionLib=fromConditionLib");
				try {

					ArrayList<Object> conditionLibraryList = new ArrayList<Object> ();
					boolean isPortReq = false;
					if(forceEJBcall){
						conditionLibraryList = PageManagementActionUtil.getConditionLib(session);
						NBSContext.store(session, NBSConstantUtil.DSConditionList, conditionLibraryList);
						manageForm.getAttributeMap().put("manageList",conditionLibraryList);
						//Set InvSummaryVOColl as property of Form first time since this list is used for distinct dropdown values in the form
						manageForm.initializeDropDowns(conditionLibraryList);
						manageForm.getAttributeMap().put("programAreaCount",new Integer(manageForm.getProgramArea().size()));
						manageForm.getAttributeMap().put("associatedPageCount",new Integer(manageForm.getAssociatedPage().size()));
						manageForm.getAttributeMap().put("nndConditionCount",new Integer(manageForm.getNndCondition().size()));
						manageForm.getAttributeMap().put("statusCount",new Integer(manageForm.getStatus().size()));
						manageForm.getAttributeMap().put("conditionFamilyCount",new Integer(manageForm.getConditionFamily().size()));
						manageForm.getAttributeMap().put("coinfGroupCount",new Integer(manageForm.getCoinfGroup().size()));
						Iterator<Object> iter = conditionLibraryList.iterator();
						while(iter.hasNext()) {
							ConditionDT dt = (ConditionDT) iter.next();
							String conditionCd = dt.getConditionCd().toString();
							request.setAttribute("conditionCd", conditionCd);
							dt.setNndIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getNndInd(),"YN"));
							dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
							dt.setFamilyCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getFamilyCd(),"CONDITION_FAMILY"));
							dt.setCoInfGroupDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getCoInfGroup(),"COINFECTION_GROUP"));
							if((dt.getInvestigationFormCd() != null && dt.getInvestigationFormCd().startsWith("PG_") && dt.getPortReqIndCd().equals("T")) 
									|| (dt.getInvestigationFormCd() == null && dt.getPageNm() != null) || 
									( dt.getInvestigationFormCd() != null && dt.getInvestigationFormCd().startsWith("INV_") && !dt.getInvestigationFormCd().equals(dt.getPageNm()) && dt.getPortReqIndCd().equals("T"))){
								StringBuffer sb = new StringBuffer();
								if(dt.getPageNm()==null){
									dt.setPageNmForDisplay(dt.getPageNm());
									dt.setPageNmForDisplayPrint(dt.getPageNm());
									
								}
								else{
									sb.append("<font color ='#b35f00'><b><i> ");
									sb.append(dt.getPageNm());
									sb.append("</i></b> </font color>");
									dt.setPageNmForDisplay(sb.toString());
									dt.setPageNmForDisplayPrint(dt.getPageNm());
								}
								isPortReq = true;
							}else{
								dt.setPageNmForDisplay(dt.getPageNm());
								dt.setPageNmForDisplayPrint(dt.getPageNm());
								
							}
						}	
					}else {
						conditionLibraryList = (ArrayList<Object> ) manageForm.getAttributeMap().get("manageList");
						Iterator<Object> ite1 = conditionLibraryList.iterator();
						while(ite1.hasNext()) {
							ConditionDT dt = (ConditionDT) ite1.next();
							if(dt.getPageNm() != null && dt.getPageNmForDisplay().startsWith("<font"))
								isPortReq = true;
						}
					}
					boolean existing = request.getParameter("existing") == null ? false : true;


					Collection<Object>  filteredColl = (ArrayList<Object> )filterConditionLib(manageForm, request);			
					conditionLibraryList = (ArrayList<Object> )filteredColl;			
					if(conditionLibraryList!= null){
						PageManagementActionUtil.updateConditionLinks(conditionLibraryList, request);
						sortManageCondition(manageForm, conditionLibraryList, existing, request);				
					}else{
						conditionLibraryList = (ArrayList<Object> ) manageForm.getAttributeMap().get("manageList");
						PageManagementActionUtil.updateConditionLinks(conditionLibraryList, request);
					}
					request.setAttribute("manageList",conditionLibraryList);
					if(conditionLibraryList!=null)
						request.setAttribute("queueCount", String.valueOf(conditionLibraryList.size()));
					request.setAttribute("manageList",conditionLibraryList);
					if(isPortReq)
						request.setAttribute("isPortReq", isPortReq);
					manageForm.getAttributeMap().put("queueCount", String.valueOf(conditionLibraryList.size()));
				} catch (Exception e) {
					logger.error("Exception in ViewConditionLib: " + e.getMessage(), e);
					request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
				} finally {
					request.setAttribute(DynamicPageConstants.PAGE_TITLE ,DynamicPageConstants.MANAGE_CONDITION_LIB);
					manageForm.getAttributeMap().put("Create", "/nbs/ManageCondition.do?method=createConditionLoad#condition");
				}

			}catch (Exception e) {
				logger.fatal("Exception in Manage Condition: " + e.getMessage(), e);
				e.printStackTrace();
				throw new ServletException("Error occurred in Manage Condition : "+e.getMessage(), e);
			}   
			
		//return (mapping.findForward("default"));
		return PaginationUtil.paginate(manageForm, request, "default",mapping);
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	private Collection<Object>  filterConditionLib(ManageConditionForm manageForm, HttpServletRequest request) throws Exception {
		
		Collection<Object>  manageCondlist = new ArrayList<Object> ();
		
		String srchCriteriaProgArea = null;
		String srchCriteriaAssoPage = null;
		String srchCriteriaNndCond = null;
		String srchCriteriaStatus = null;
		String srchCriteriaConditionFamily = null;
		String sortOrderParam = null;
		String srchCriteriaCondition = null;
		String srchCriteriaCode = null;
		String srchCriteriaCoinfGroup = null;
		
		try {
			
			Map<Object, Object> searchCriteriaMap = manageForm.getSearchCriteriaArrayMap();
			// Get the existing SummaryVO collection in the form
			ArrayList<Object> conditionList = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList);
			
			// Filter by the investigator
			manageCondlist = getFilteredConditionLib(conditionList, searchCriteriaMap);

			String[] programArea = (String[]) searchCriteriaMap.get("PROGRAMAREA");
	    	String[] assoPage = (String[]) searchCriteriaMap.get("ASSOCIATEDPAGE");
			String[] nndCond = (String[]) searchCriteriaMap.get("NNDCONDITION");
			String[] status = (String[]) searchCriteriaMap.get("STATUS");
			String[] conditionFamily = (String[]) searchCriteriaMap.get("CONDITIONFAMILY");
			String[] coinfectionGroup = (String[]) searchCriteriaMap.get("COINFECTIONGROUP");
			
			Integer programAreaCount = new Integer(programArea == null ? 0 : programArea.length);
			Integer assoPageCount = new Integer(assoPage == null  ? 0 : assoPage.length);
			Integer nndConditionCount = new Integer(nndCond == null  ? 0 : nndCond.length);
			Integer statusCount = new Integer(status == null ? 0 : status.length);
			Integer conditionFamilyCount = new Integer(conditionFamily == null ? 0 : conditionFamily.length);
			Integer coinfectionGroupCount = new Integer(coinfectionGroup == null ? 0 : coinfectionGroup.length);
			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				srchCriteriaCondition = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				srchCriteriaCode = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
			}

			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if(programAreaCount.equals((manageForm.getAttributeMap().get("programAreaCount"))) &&
					
					(assoPageCount.equals(manageForm.getAttributeMap().get("associatedPageCount"))) &&
					(nndConditionCount.equals(manageForm.getAttributeMap().get("nndConditionCount"))) &&
					(statusCount.equals(manageForm.getAttributeMap().get("statusCount")))&& 
					(conditionFamilyCount.equals(manageForm.getAttributeMap().get("conditionFamilyCount"))) &&
					(coinfectionGroupCount.equals(manageForm.getAttributeMap().get("coinfGroupCount"))) &&
					(srchCriteriaCondition==null && srchCriteriaCode==null))
				 {
				
				String sortMethod = getSortMethod(request, manageForm);
				String direction = getSortDirection(request, manageForm);			
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<?,?> sColl =  manageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<?,?>) manageForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = PageManagementActionUtil.getSortCriteria(direction, sortMethod);
				}				
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
				return manageCondlist;				
			}
						
			
			ArrayList<Object> programAreaList = manageForm.getProgramArea();
			ArrayList<Object> associatedPageList = manageForm.getAssociatedPage();
			ArrayList<Object> nndConditionList = manageForm.getNndCondition();
			ArrayList<Object> statusList = manageForm.getStatus();
			ArrayList<Object> conditionFamilyList = manageForm.getConditionFamily();
			ArrayList<Object> coinfGroupList =manageForm.getCoinfGroup();
			
			
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			String sortMethod = getSortMethod(request, manageForm);
			String direction = getSortDirection(request, manageForm);			
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object, Object> sColl =  manageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<Object, Object>) manageForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = PageManagementActionUtil.getSortCriteria(direction, sortMethod);
			}
			
			srchCriteriaProgArea = queueUtil.getSearchCriteria(programAreaList, programArea, NEDSSConstants.FILTERBYSUBMITTEDBY);
			
			srchCriteriaAssoPage = queueUtil.getSearchCriteria(associatedPageList, assoPage, NEDSSConstants.FILTERBYSUBMITTEDBY);
			srchCriteriaNndCond = queueUtil.getSearchCriteria(nndConditionList, nndCond, NEDSSConstants.FILTERBYSUBMITTEDBY);
			srchCriteriaStatus = queueUtil.getSearchCriteria(statusList, status, NEDSSConstants.FILTERBYSUBMITTEDBY);
			srchCriteriaConditionFamily = queueUtil.getSearchCriteria(conditionFamilyList, conditionFamily, NEDSSConstants.FILTERBYSUBMITTEDBY);
			srchCriteriaCoinfGroup =  queueUtil.getSearchCriteria(coinfGroupList, coinfectionGroup, NEDSSConstants.FILTERBYSUBMITTEDBY);
	
			
			//set the error message to the form
			if(sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if(srchCriteriaProgArea != null)
				searchCriteriaColl.put("INV147", srchCriteriaProgArea);
			if(srchCriteriaAssoPage != null)
					searchCriteriaColl.put("INV100", srchCriteriaAssoPage);
			if(srchCriteriaNndCond != null)
				searchCriteriaColl.put("INV163", srchCriteriaNndCond);
			if(srchCriteriaStatus != null)
				searchCriteriaColl.put("NOT118", srchCriteriaStatus);
			if(srchCriteriaCondition != null)
				searchCriteriaColl.put("INV111", srchCriteriaCondition);
			if(srchCriteriaCode != null)
				searchCriteriaColl.put("INV222", srchCriteriaCode);
			if(srchCriteriaConditionFamily != null)
				searchCriteriaColl.put("INV333", srchCriteriaConditionFamily);
			if(srchCriteriaCoinfGroup != null)
				searchCriteriaColl.put("STD111", srchCriteriaCoinfGroup);
			
			manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Error while filtering the investigation by Investigator: "+ e.getMessage(), e);
			throw new ServletException("Error  in filterConditionLib method: " +e.getMessage(), e);
		} 
		return manageCondlist;
	}
	public Collection<Object>  getFilteredConditionLib(Collection<Object>  conditionDTColl,
			Map<Object, Object> searchCriteriaMap) {
		
    	String[] programArea = (String[]) searchCriteriaMap.get("PROGRAMAREA");
    	String[] associatedPage = (String[]) searchCriteriaMap.get("ASSOCIATEDPAGE");
		String[] nndCondition = (String[]) searchCriteriaMap.get("NNDCONDITION");
		String[] status = (String[]) searchCriteriaMap.get("STATUS");
		String[] conditionFamily = (String[]) searchCriteriaMap.get("CONDITIONFAMILY");
		String[] coinfectionGroup = (String[]) searchCriteriaMap.get("COINFECTIONGROUP");
		
		String filterByConditionText = null;
		String filterByCodeText = null;
		if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
			filterByConditionText = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
		}				
		if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
			filterByCodeText = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
		}
		Map<Object, Object> programAreaMap = new HashMap<Object,Object>();
		Map<Object, Object> associatedPageMap = new HashMap<Object,Object>();
		Map<Object, Object> nndConditionMap = new HashMap<Object,Object>();
		Map<Object, Object> statusMap = new HashMap<Object,Object>();
		Map<Object, Object> conditionFamilyMap = new HashMap<Object,Object>();
		Map<Object, Object> coinfectionGroupMap = new HashMap<Object,Object>();
		
		if (programArea != null && programArea.length > 0)
			programAreaMap = queueUtil.getMapFromStringArray(programArea);
		
		if (associatedPage != null && associatedPage.length > 0)
			associatedPageMap = queueUtil.getMapFromStringArray(associatedPage);
		if (nndCondition != null && nndCondition.length > 0)
			nndConditionMap = queueUtil.getMapFromStringArray(nndCondition);
		if (status != null && status.length >0)
			statusMap = queueUtil.getMapFromStringArray(status);
		if (conditionFamily != null && conditionFamily.length >0)
			conditionFamilyMap = queueUtil.getMapFromStringArray(conditionFamily);
		if (coinfectionGroup != null && coinfectionGroup.length >0)
			coinfectionGroupMap = queueUtil.getMapFromStringArray(coinfectionGroup);
		
		
		if(programAreaMap != null && programAreaMap.size()>0)
			conditionDTColl = filterProgramArea(
					conditionDTColl, programAreaMap);
		if (associatedPageMap != null && associatedPageMap.size()>0)
			conditionDTColl = filterAssociatedPage(
					conditionDTColl, associatedPageMap);
		if (nndConditionMap != null && nndConditionMap.size()>0)
			conditionDTColl = filterNndCondition(
					conditionDTColl, nndConditionMap);
		if (statusMap != null && statusMap.size()>0)
			conditionDTColl = filterStatus(
					conditionDTColl, statusMap);
		if (conditionFamilyMap != null && conditionFamilyMap.size()>0)
			conditionDTColl = filterConditionFamily(
					conditionDTColl, conditionFamilyMap);
		if (coinfectionGroupMap != null && coinfectionGroupMap.size()>0)
			conditionDTColl = filterCoinfGroup(
					conditionDTColl, coinfectionGroupMap);
		
		if(filterByConditionText!= null){
			conditionDTColl = filterByConditionText(conditionDTColl, filterByConditionText);
		}
		if(filterByCodeText!= null){
			conditionDTColl = filterByCodeText(conditionDTColl, filterByCodeText);
		}
		return conditionDTColl;
		
	}
	
	
	public Collection<Object>  filterByConditionText(
			Collection<Object>  conditionDTColl, String filterByText) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (conditionDTColl != null) {
			Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if(dt.getConditionShortNm().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
			}
		}
		}catch(Exception ex){
			 logger.error("Error filtering the filterByText : "+ex.getMessage(), ex);
			 //throw new NEDSSSystemException(ex.getMessage());
		}
		return newTypeColl;
	}
	
	public Collection<Object>  filterByCodeText(
			Collection<Object>  conditionDTColl, String filterByText) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (conditionDTColl != null) {
			Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if(dt.getConditionCd().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
			}
		}
		}catch(Exception ex){
			 logger.error("Error filtering the filterByText : "+ex.getMessage(), ex);
			 //throw new NEDSSSystemException(ex.getMessage());
		}
		return newTypeColl;
	}
	
	public Collection<Object>  filterProgramArea(
			Collection<Object>  conditionDTColl, Map<Object,Object> programAreaMap) {
		Collection<Object>  newProgramAreaColl = new ArrayList<Object> ();
		if (conditionDTColl != null) {
			Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if (dt.getProgAreaCd() != null
						&& programAreaMap != null
						&& programAreaMap.containsKey(dt.getProgAreaCd())) {
					newProgramAreaColl.add(dt);
				}
				if(dt.getProgAreaCd() == null || dt.getProgAreaCd().equals("")){
					if(programAreaMap != null && programAreaMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newProgramAreaColl.add(dt);
					}
				}

			}

		}
		return newProgramAreaColl;

	}
	
	public Collection<Object>  filterAssociatedPage(
			Collection<Object>  conditionDTColl, Map<Object,Object> associatedPageMap) {
		Collection<Object>  newAssociatedPageColl = new ArrayList<Object> ();
		if (conditionDTColl != null) {
			Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if (dt.getPageNm() != null
						&& associatedPageMap != null
						&& associatedPageMap.containsKey(dt.getPageNm())) {
					newAssociatedPageColl.add(dt);
				}
				if(dt.getPageNm() == null || dt.getPageNm().equals("")){
					if(associatedPageMap != null && associatedPageMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newAssociatedPageColl.add(dt);
					}
				}

			}

		}
		return newAssociatedPageColl;

	}
	
	public Collection<Object>  filterNndCondition(
			Collection<Object>  conditionDTColl, Map<Object,Object> nndConditionMap) {
		Collection<Object>  newNndConditionColl = new ArrayList<Object> ();
		if (conditionDTColl != null) {
			Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if (dt.getNndInd() != null
						&& nndConditionMap != null
						&& nndConditionMap.containsKey(dt.getNndInd())) {
					newNndConditionColl.add(dt);
				}
				if(dt.getNndInd() == null || dt.getNndInd().equals("")){
					if(nndConditionMap != null && nndConditionMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newNndConditionColl.add(dt);
					}
				}

			}

		}
		return newNndConditionColl;

	}
	
	public Collection<Object>  filterStatus(
			Collection<Object>  conditionDTColl, Map<Object,Object> statusMap) {
		Collection<Object>  newStatusColl = new ArrayList<Object> ();
		if (conditionDTColl != null) {
			Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
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
	
	public Collection<Object>  filterConditionFamily(
			Collection<Object>  conditionDTColl, Map<Object,Object> conditionFamilyMap) {
		Collection<Object>  newConditionFamilyColl = new ArrayList<Object> ();
		if (conditionDTColl != null) {
			Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if (dt.getFamilyCd() != null
						&& conditionFamilyMap != null
						&& conditionFamilyMap.containsKey(dt.getFamilyCd())) {
					newConditionFamilyColl.add(dt);
				}
				if(dt.getFamilyCd() == null || dt.getFamilyCd().equals("")){
					if(conditionFamilyMap != null && conditionFamilyMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newConditionFamilyColl.add(dt);
					}
				}

			}

		}
		return newConditionFamilyColl;

	}
	
	public Collection<Object>  filterCoinfGroup(
			Collection<Object>  conditionDTColl, Map<Object,Object> coinfectionGroupMap) {
		Collection<Object>  newCoinfectionGroupColl = new ArrayList<Object> ();
		if (conditionDTColl != null) {
			Iterator<Object> iter = conditionDTColl.iterator();
			while (iter.hasNext()) {
				ConditionDT dt = (ConditionDT) iter.next();
				if (dt.getCoInfGroup() != null
						&& coinfectionGroupMap != null
						&& coinfectionGroupMap.containsKey(dt.getCoInfGroup())) {
					newCoinfectionGroupColl.add(dt);
				}
				if(dt.getCoInfGroup() == null || dt.getCoInfGroup().equals("")){
					if(coinfectionGroupMap != null && coinfectionGroupMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newCoinfectionGroupColl.add(dt);
					}
				}

			}

		}
		return newCoinfectionGroupColl;

	}
	
	
	private String getSortMethod(HttpServletRequest request, ManageConditionForm manageForm ) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
	} else{
		return manageForm.getAttributeMap().get("methodName") == null ? null : (String) manageForm.getAttributeMap().get("methodName");
		}
		
	}
	private String getSortDirection(HttpServletRequest request, ManageConditionForm manageForm) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		} else{
			return manageForm.getAttributeMap().get("sortOrder") == null ? "1": (String) manageForm.getAttributeMap().get("sortOrder");
		}
		
	}
	private void sortManageCondition(ManageConditionForm manageForm, Collection<Object>  recFacilityList, boolean existing, HttpServletRequest request) throws Exception{

		// Retrieve sort-order and sort-direction from displaytag params
		String sortMethod = getSortMethod(request, manageForm);
		String direction = getSortDirection(request, manageForm);

		boolean invDirectionFlag = true;
		if (direction != null && direction.equals("2"))
			invDirectionFlag = false;


		NedssUtils util = new NedssUtils();
		
		//Read from properties file to determine default sort order
		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
			sortMethod = "getConditionShortNm";
			invDirectionFlag = true;
		}
		
		if (sortMethod != null && recFacilityList != null && recFacilityList.size() > 0) {
			updateListBeforeSort(recFacilityList);
			//util.sortObjectByColumn(sortMethod,(Collection<Object>) recFacilityList, invDirectionFlag);
			util.sortObjectByColumnGeneric(sortMethod,(Collection<Object>) recFacilityList, invDirectionFlag);
			
			updateListAfterSort(recFacilityList);
			
		}
		
		
		if(!existing) {
			//Finally put sort criteria in form
			String sortOrderParam = PageManagementActionUtil.getSortCriteria(invDirectionFlag == true ? "1" : "2", sortMethod);
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			searchCriteriaColl.put("sortSt", sortOrderParam);
			manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}
		}
	private void updateListBeforeSort(Collection<Object>  recFacilityList) {
		Iterator<Object> iter = recFacilityList.iterator();
		while (iter.hasNext()) {
			ConditionDT dt = (ConditionDT)  iter.next();
			
			if (dt.getProgAreaCd()== null || (dt.getProgAreaCd() != null &&dt.getProgAreaCd().equals(""))) {
				dt.setProgAreaCd("ZZZZZ");
			}

			/*if (dt.getReceivingSystemShortName()== null || dt.getReceivingSystemShortName() != null dt.getReceivingSystemShortName().equals("")) {
				dt.setReceivingSystemShortName("ZZZZZ");
			}
				*/
		}
		
	}
	
	private void updateListAfterSort(Collection<Object>  notifiSummaryVOs) {
		Iterator<Object> iter = notifiSummaryVOs.iterator();
		while (iter.hasNext()) {
			ConditionDT dt = (ConditionDT)  iter.next();
			if (dt.getProgAreaCd() != null && dt.getProgAreaCd().equals("ZZZZZ")) {
				dt.setProgAreaCd("");
			}
			/*if (dt.getReceivingSystemShortName() != null && dt.getReceivingSystemShortName().equals("ZZZZZ")) {
				dt.setReceivingSystemShortName("");
			}*/
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward filterConditionLibSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ManageConditionForm manageForm  = (ManageConditionForm) form;
		Collection<Object>  conditionList = filterConditionLib(manageForm, request);
		if(conditionList != null){
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
		//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
		}else{
			conditionList = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList);
		}
		NBSContext.store(request.getSession(true), NBSConstantUtil.DSConditionList, conditionList);
		manageForm.getAttributeMap().put("queueList",conditionList);
		request.setAttribute("queueList", conditionList);
		sortManageCondition(manageForm, conditionList, true, request);
		request.setAttribute("manageList", conditionList);
		request.setAttribute("queueCount", String.valueOf(conditionList.size()));
		manageForm.getAttributeMap().put("queueCount", String.valueOf(conditionList.size()));
		request.setAttribute("PageTitle","Manage Condition Library");
		manageForm.getAttributeMap().put("PageNumber", "1");

		return PaginationUtil.paginate(manageForm, request, "default",mapping);
		
	} 
	
	
	
	@SuppressWarnings("unchecked")
	public ActionForward viewCondition(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		ManageConditionForm manageForm = (ManageConditionForm) form;
		String conditionCd = request.getParameter("conditionCd");
		String conditionShortNm = null;
		String activeInd = null;
		String portRequireInd = null;
		String templateType=null;
		try {
			manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			
			if(conditionCd != null && conditionCd.length() > 0) {
				ArrayList<?> testList = (ArrayList<Object>)NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList);
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					ConditionDT dt = (ConditionDT) iter.next();
					if(dt.getConditionCd().equalsIgnoreCase(conditionCd) ) {
						dt.setParentIsCdDescTxt(manageForm.getParentIsCdDesc(dt.getParentIsCd()));
						activeInd = dt.getStatusCd();
						dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
						if(activeInd != null && activeInd.equals("A"))
						{
							request.setAttribute("ActiveInd", "Inactive");
						}else if(activeInd != null && activeInd.equals("I"))
						{
							request.setAttribute("ActiveInd", "Active");
						}
						portRequireInd = dt.getPortReqIndCd();
						manageForm.setSelection(dt);
						WaTemplateDT waTemplateDT = new WaTemplateDT();
						try{
							PageManagementActionUtil pmaUtil = new PageManagementActionUtil();
							HttpSession session = request.getSession(true);
							waTemplateDT = pmaUtil.getWaTemplate(conditionCd, session);
						}catch (Exception e){
							logger.fatal( "Error in getting the waTemplate "+e.getMessage(), e);
							throw new ServletException(e.getMessage(), e);							
						}
						templateType = waTemplateDT.getTemplateType();
						request.setAttribute("NNDEntityId", HTMLEncoder.encodeHtml(dt.getNndInd()));
						manageForm.setOldDT(dt);
						manageForm.setSectionCondition("View Condition");
						conditionShortNm = dt.getConditionShortNm();
						break;
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			logger.error("Exception in viewCondition: " + e.getMessage(), e);
		} finally {
			request.setAttribute("conditionNm", HTMLEncoder.encodeHtml(conditionShortNm));
			request.setAttribute("manageList", (ArrayList<Object>)NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList));
			request.setAttribute(DynamicPageConstants.PAGE_TITLE ,DynamicPageConstants.VIEW_CONDITION);
			manageForm.getAttributeMap().put("Edit", "/nbs/ManageCondition.do?method=editConditionLoad&conditionCd="+conditionCd+"#"+"condition");
			manageForm.getAttributeMap().put("MakeInactive", "/nbs/ManageCondition.do?method=makeConditionInactive&conditionCd="+conditionCd+"#"+"condition");
			manageForm.getAttributeMap().put("MakeActive", "/nbs/ManageCondition.do?method=makeConditionActive&conditionCd="+conditionCd+"#"+"condition");
			manageForm.getAttributeMap().put("Create", "/nbs/ManageCondition.do?method=createConditionLoad#condition");
		}
		templateType = templateType!=null? templateType:"";
		if(request.getParameter("fromConditionLib")!=null && request.getParameter("pageNm")!=null && !request.getParameter("pageNm").startsWith("INV_FORM")&&( "T".equalsIgnoreCase(portRequireInd) || NEDSSConstants.DRAFT.equals(templateType))){ // For link from list page
			String fromConditionLib = (String)request.getParameter("fromConditionLib");
			request.setAttribute("fromConditionLib", HTMLEncoder.encodeHtml(fromConditionLib));
			request.setAttribute("pageNm", HTMLEncoder.encodeHtml((String)request.getParameter("pageNm")));
			
		}
		return (mapping.findForward("Manage"));
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward createConditionLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		ManageConditionForm manageForm = (ManageConditionForm) form;
		try {
			manageForm.resetSelection();
			ConditionDT conditionDt = new ConditionDT();
			//set the default values of the radio buttons as Yes
			conditionDt.setNndInd("Y");
			conditionDt.setReportableMorbidityInd("Y");
			conditionDt.setReportableSummaryInd("N");
			conditionDt.setContactTracingEnableInd("Y");
			conditionDt.setCodeSystemCd(defaultCodingSys);
			manageForm.setSelection(conditionDt);
			manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			manageForm.getAttributeMap().put("cancel", "/nbs/ManageCondition.do?method=ViewConditionLib&actionMode=Manage&context=cancel#condition");
			manageForm.getAttributeMap().put("submit", "/nbs/ManageCondition.do?method=createConditionSubmit");
			manageForm.setSectionCondition("Add Condition");
		}catch (Exception e) {
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			logger.error("Exception in viewCondition: " + e.getMessage(), e);
		}finally {
			request.setAttribute("manageList", (ArrayList<Object>)NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList));
			request.setAttribute(DynamicPageConstants.PAGE_TITLE ,DynamicPageConstants.ADD_CONDITION);
		}
		return (mapping.findForward("Manage"));
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward createConditionSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		ManageConditionForm manageForm = (ManageConditionForm) form;
		HttpSession session = request.getSession(true);
		ConditionDT dt = (ConditionDT) manageForm.getSelection();
		ArrayList<Object> conditionList = new ArrayList<Object>();
		request.setAttribute("manageList", (ArrayList<Object>)NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList));
		ActionMessages messages = new ActionMessages();
		Map<Object, Object> errorMap = null;
		StringBuffer sb = new StringBuffer();
		String clickHereLink = "/nbs/ManagePage.do?method=list&initLoad=true";
		String activeInd = null;
		try {
			// From the coding system , we need to get assigning_authority_cd, assigning_auto_desc_txt, code_sys_txt, and code_short_desc_txt
			conditionList = PageManagementActionUtil.getConditionLib(session);
			String codeSys = dt.getCodeSystemCd();
			Object[] searchParams1 = new Object[] {codeSys};
			Object[] oParams1 = new Object[] { daoName, "getCodingSysFields", searchParams1};
			ArrayList<?> dtList = (ArrayList<?> )PageManagementActionUtil.processRequest(oParams1, request.getSession());
			if(dtList.size() > 0) {
				Iterator<?> iter = dtList.iterator();
				while(iter.hasNext()) {
					ConditionDT conDt = (ConditionDT) iter.next();
					dt.setAssigningAuthorityCd(null);
					dt.setAssigningAuthorityDescTxt(null);
					dt.setCodeSystemCd(conDt.getCodeSystemCd());
					dt.setCodeSystemDescTxt(conDt.getCodeSystemDescTxt());
				}
			}
			dt.setVaccineModuleEnableInd("N");//ND-19671
			dt.setTreatmentModuleEnableInd("Y");
			dt.setLabReportModuleEnableInd("N");//ND-19671
			dt.setMorbReportModuleEnableInd("Y");
			dt.setStatusCd("A");
			dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
			dt.setConditionDescTxt(dt.getConditionShortNm());
			dt.setParentIsCdDescTxt(manageForm.getParentIsCdDesc(dt.getParentIsCd()));
			PageManagementActionUtil.trimSpaces(dt);
			
			errorMap = PageManagementActionUtil.insertCondition(session, dt);
			NBSContext.store(request.getSession(true), NBSConstantUtil.DSConditionList, conditionList);
			if(errorMap.size()==0)
			{
				//messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					//	new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "Condition"));
				//request.setAttribute("success_messages", messages);
				//new success message
				sb.append(" has been successfully added to the system. This condition will not be available in the system for individual investigation entry until it has been associated to a page and published.")
				.append(" ");
				
				request.setAttribute("ConfirmMesgCreate", sb.toString());
				request.setAttribute("ConditionShortNm", dt.getConditionShortNm());
				manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			}else if(errorMap.size()!=0){
				manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
				Iterator<Object> iter = errorMap.values().iterator();
				StringBuffer buff= new StringBuffer();
				while(iter.hasNext()){
					String errText = iter.next().toString();
					buff.append("<ul>").append("<li>").append(HTMLEncoder.encodeHtml(errText)).append("</li>").append("</ul>");
				}
				request.setAttribute("CreateError",buff.toString());
			}
			// To display Active/ Inactive button in the view page
			dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
			activeInd = dt.getStatusCd();
			if(activeInd != null && activeInd.equals("A"))
			{
				request.setAttribute("ActiveInd", "Inactive");
			}else if(activeInd != null && activeInd.equals("I"))
			{
				request.setAttribute("ActiveInd", "Active");
			}
			request.setAttribute("clickHereLk", clickHereLink);
			
		}catch (Exception e) {
			try{
				PageManagementActionUtil.handleErrors(e, request, NEDSSConstants.CREATE,NEDSSConstants.CONDITIONCD);
			}catch (Exception ex) {
				logger.fatal("Error in calling handleErrors  "+ex.getMessage(), ex);
				throw new ServletException(ex.getMessage(), ex);
			}
			messages.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY,
					new ActionMessage("A record already exists with this Condition Code. Please enter a unique Condition Code to create a new record."));
			manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			//request.setAttribute("error", e.getMessage());
			request.setAttribute(DynamicPageConstants.PAGE_TITLE ,DynamicPageConstants.ADD_CONDITION);
			logger.error("Exception in createConditionSubmit: " + e.getMessage(), e);
			return (mapping.findForward("Manage"));
		}finally {
			//request.setAttribute("manageList", manageForm.getManageList());
			String conditionCd = dt.getConditionCd();
			manageForm.getAttributeMap().put("Edit", "/nbs/ManageCondition.do?method=editConditionLoad&conditionCd="+conditionCd+"#"+"condition");
			manageForm.getAttributeMap().put("MakeInactive", "/nbs/ManageCondition.do?method=makeConditionInactive&conditionCd="+conditionCd+"#"+"condition");
			manageForm.getAttributeMap().put("MakeActive", "/nbs/ManageCondition.do?method=makeConditionActive&conditionCd="+conditionCd+"#"+"condition");
			
		}
		manageForm.setSelection(dt); 
		manageForm.setOldDT(dt);
		request.setAttribute(DynamicPageConstants.PAGE_TITLE ,DynamicPageConstants.VIEW_CONDITION);
		manageForm.setSectionCondition("View Condition");
		return (mapping.findForward("Manage"));
	}
	
	
@SuppressWarnings("unchecked")
public ActionForward editConditionLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		ManageConditionForm manageForm = (ManageConditionForm) form;		
		try {
			manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);				
			String conditionCd = request.getParameter("conditionCd");
			
			if(conditionCd != null && conditionCd.length() > 0) {
				ArrayList<?> testList = (ArrayList<Object>)NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList);
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					ConditionDT dt = (ConditionDT) iter.next();
					if(dt.getConditionCd().equalsIgnoreCase(conditionCd)) {
						dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
						manageForm.setSelection(dt);						
						manageForm.setOldDT(dt);
						request.setAttribute("NNDEntityId", HTMLEncoder.encodeHtml(dt.getNndInd()));
						break;
					}
				}				
			}				
			manageForm.getAttributeMap().put("cancel", "/nbs/ManageCondition.do?method=viewCondition&conditionCd="+conditionCd+"&context=cancel#condition");
			manageForm.getAttributeMap().put("submit", "/nbs/ManageCondition.do?method=editConditionSubmit");
		} catch (Exception e) {
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			logger.error("Exception in editConditionLoad: " + e.getMessage(), e);
		} finally {
			request.setAttribute("manageList", (ArrayList<Object>)NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList));
			request.setAttribute(DynamicPageConstants.PAGE_TITLE ,DynamicPageConstants.EDIT_CONDITION);
			manageForm.setSectionCondition("Edit Condition");
		}
		
		return (mapping.findForward("Manage"));		
		
	}

@SuppressWarnings("unchecked")
public ActionForward editConditionSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
	ManageConditionForm manageForm = (ManageConditionForm) form;
	ConditionDT dt = (ConditionDT) manageForm.getSelection();
	dt.setParentIsCdDescTxt(manageForm.getParentIsCdDesc(dt.getParentIsCd()));
	HttpSession session = request.getSession(true);
	ArrayList<Object> conditionList = new ArrayList<Object>();
	String activeInd = null;
	StringBuffer sb = new StringBuffer();
	try {
		PageManagementActionUtil.trimSpaces(dt);
		dt.setConditionDescTxt(dt.getConditionShortNm());
		//if(dt.getNndInd() != null && dt.getNndInd().equals("N")) dt.setNndEntityId(null);
		//Object[] searchParams = new Object[] {dt};
		//Object[] oParams = new Object[] { daoName, "updateCondition", searchParams };
		Map<Object, Object> errorMap = PageManagementActionUtil.updateCondition(session, dt);
		if(errorMap.size()==0)
		{
			//ActionMessages messages = new ActionMessages();
			//messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
				// ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "Condition"));
			//new success message
			sb.append(" has been successfully updated in the system. Please restart the application server to make the change effective in the system.");
			
			request.setAttribute("ConfirmMesg", sb.toString());
			request.setAttribute("ConditionShortNm", dt.getConditionShortNm());
			conditionList = PageManagementActionUtil.getConditionLib(session);
		}else if(errorMap.size()!=0){
			manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			Iterator<Object> iter = errorMap.values().iterator();
			StringBuffer buff= new StringBuffer();
			while(iter.hasNext()){
				String errText = iter.next().toString();
				buff.append("<ul>").append("<li>").append(HTMLEncoder.encodeHtml(errText)).append("</li>").append("</ul>");
			}
			request.setAttribute("error",buff.toString());
		}
		// To display Active/ Inactive button in the view page
		dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
		activeInd = dt.getStatusCd();
		if(activeInd != null && activeInd.equals("A"))
		{
			request.setAttribute("ActiveInd", "Inactive");
		}else if(activeInd != null && activeInd.equals("I"))
		{
			request.setAttribute("ActiveInd", "Active");
		}
		
	} catch (Exception e) {
		try{
			PageManagementActionUtil.handleErrors(e, request, NEDSSConstants.CREATE,NEDSSConstants.CONDITIONCD);		
			NBSContext.store(request.getSession(true), NBSConstantUtil.DSConditionList,conditionList);
			PageManagementActionUtil.handleErrors(e, request, "edit",NEDSSConstants.LABID);
		}catch (Exception ex) {
			logger.fatal("Error in calling handleErrors  "+ex.getMessage(), ex);
			throw new ServletException(ex.getMessage(), ex);
		}
		
		request.setAttribute("manageList", (ArrayList<Object>)NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList));
		logger.error("Exception in updateLab: " + e.getMessage(), e);
		request.setAttribute(DynamicPageConstants.PAGE_TITLE ,DynamicPageConstants.EDIT_CONDITION);
		return (mapping.findForward("Manage"));
	} finally
	{
		String conditionCd = dt.getConditionCd();
		manageForm.getAttributeMap().put("Edit", "/nbs/ManageCondition.do?method=editConditionLoad&conditionCd="+conditionCd+"#"+"condition");
		manageForm.getAttributeMap().put("MakeInactive", "/nbs/ManageCondition.do?method=makeConditionInactive&conditionCd="+conditionCd+"#"+"condition");
		manageForm.getAttributeMap().put("MakeActive", "/nbs/ManageCondition.do?method=makeConditionActive&conditionCd="+conditionCd+"#"+"condition");
		
	}
	NBSContext.store(request.getSession(true), NBSConstantUtil.DSConditionList, conditionList);
	manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
	request.setAttribute(DynamicPageConstants.PAGE_TITLE ,DynamicPageConstants.VIEW_CONDITION);
	request.setAttribute("manageList", (ArrayList<Object>)NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList));
	manageForm.setSectionCondition("View Condition");
	manageForm.setSelection(dt);
	return (mapping.findForward("Manage"));		
}

@SuppressWarnings("unchecked")
public ActionForward makeConditionInactive(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
	ManageConditionForm manageForm = (ManageConditionForm) form;	
	ArrayList<Object> conditionList = new ArrayList<Object>();
	HttpSession session = request.getSession(true);
	StringBuffer sb = new StringBuffer();
	try {
		//manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);				
		String conditionCd = request.getParameter("conditionCd");
		ConditionDT dt = null;
		if(conditionCd != null && conditionCd.length() > 0) {
			ArrayList<?> testList = (ArrayList<Object>)NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList);
			Iterator<?> iter = testList.iterator();
			while(iter.hasNext()) {			
				dt = (ConditionDT) iter.next();
				if(dt.getConditionCd().equalsIgnoreCase(conditionCd)) {
					manageForm.setSelection(dt);						
					manageForm.setOldDT(dt);
					break;
				}
			}				
		}	
		Object[] searchParams = new Object[] {dt.getConditionCd()};
		Object[] oParams = new Object[] { daoName, "inactivateCondition", searchParams };
		PageManagementActionUtil.processRequest(oParams, request.getSession());
		sb.append(" condition has been made inactive. Please restart the application server to make the change effective in the system.");
		request.setAttribute("ConfirmMesg", sb.toString());
		request.setAttribute("ConditionShortNm", dt.getConditionShortNm());
		conditionList = PageManagementActionUtil.getConditionLib(session);
		ConditionDT activeDt = (ConditionDT) manageForm.getSelection();
		activeDt.setStatusCd("I");
		activeDt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd("I","NBS_STATUS_CD"));
		manageForm.setSelection(activeDt);
		// To display Active/ Inactive button in the view page
		String activeInd = "I";
		if(activeInd != null && activeInd.equals("A"))
		{
			request.setAttribute("ActiveInd", "Inactive");
		}else if(activeInd != null && activeInd.equals("I"))
		{
			request.setAttribute("ActiveInd", "Active");
		}
		request.setAttribute("conditionNm", HTMLEncoder.encodeHtml(dt.getConditionCd()));
	}catch (Exception e) {
		NBSContext.store(request.getSession(true), NBSConstantUtil.DSConditionList, conditionList);
		request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
		logger.error("Exception in makeConditionInactive: " + e.getMessage(), e);
	}
	NBSContext.store(request.getSession(true), NBSConstantUtil.DSConditionList, conditionList);
	manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
	request.setAttribute(DynamicPageConstants.PAGE_TITLE ,DynamicPageConstants.VIEW_CONDITION);
	manageForm.setSectionCondition("View Condition");
	return (mapping.findForward("Manage"));
}

@SuppressWarnings("unchecked")
public ActionForward makeConditionActive(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
	ManageConditionForm manageForm = (ManageConditionForm) form;	
	ArrayList<Object> conditionList = new ArrayList<Object>();
	HttpSession session = request.getSession(true);
	StringBuffer sb = new StringBuffer();
	try {
		//manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);				
		String conditionCd = request.getParameter("conditionCd");
		ConditionDT dt = null;
		if(conditionCd != null && conditionCd.length() > 0) {
			ArrayList<?> testList = (ArrayList<Object>)NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSConditionList);
			Iterator<?> iter = testList.iterator();
			while(iter.hasNext()) {			
				dt = (ConditionDT) iter.next();
				if(dt.getConditionCd().equalsIgnoreCase(conditionCd)) {
					manageForm.setSelection(dt);						
					manageForm.setOldDT(dt);
					break;
				}
			}				
		}	
		Object[] searchParams = new Object[] {dt.getConditionCd()};
		Object[] oParams = new Object[] { daoName, "activateCondition", searchParams };
		PageManagementActionUtil.processRequest(oParams, request.getSession());
		sb.append(" condition has been made active in the system and now will be available to users when creating an investigation or summary report.");
		request.setAttribute("ConfirmMesg", sb.toString());
		request.setAttribute("ConditionShortNm", dt.getConditionShortNm());
		conditionList = PageManagementActionUtil.getConditionLib(session);
		// To display Active/ Inactive button in the view page
		ConditionDT activeDt = (ConditionDT) manageForm.getSelection();
		activeDt.setStatusCd("A");
		activeDt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd("A","NBS_STATUS_CD"));
		manageForm.setSelection(activeDt);
		String activeInd = "A";
		if(activeInd != null && activeInd.equals("A"))
		{
			request.setAttribute("ActiveInd", "Inactive");
		}else if(activeInd != null && activeInd.equals("I"))
		{
			request.setAttribute("ActiveInd", "Active");
		}
	}catch (Exception e) {
		NBSContext.store(request.getSession(true), NBSConstantUtil.DSConditionList, conditionList);
		request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
		logger.error("Exception in makeConditionInactive: " + e.getMessage(), e);
	}
	NBSContext.store(request.getSession(true), NBSConstantUtil.DSConditionList, conditionList);
	manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
	request.setAttribute(DynamicPageConstants.PAGE_TITLE ,DynamicPageConstants.VIEW_CONDITION);
	manageForm.setSectionCondition("View Condition");
	return (mapping.findForward("Manage"));
}
}
