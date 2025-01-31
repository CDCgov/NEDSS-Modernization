package gov.cdc.nedss.webapp.nbs.action.pagemanagement.managetemplates;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pagemanagement.wa.xml.util.TemplateExportType;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.DynamicPageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managetemplate.ManageTemplateForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

public class ManageTemplatesAction extends DispatchAction 
{
	static final LogUtils logger = new LogUtils(ManageTemplatesAction.class.getName());
	public static final String nedssDirectory = new StringBuffer(System.getProperty("nbs.dir")).append(File.separator).toString().intern();
	public static final String propertiesDirectory = new StringBuffer(nedssDirectory).append("Properties").append(File.separator).toString().intern();
	public static final String xml2jspDirectory = new StringBuffer(propertiesDirectory).append("xmltojsp").append(File.separator).toString().intern();


	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	PropertyUtil properties = PropertyUtil.getInstance();
	QueueUtil queueUtil = new QueueUtil();
	GenericQueueUtil genericQueueUtil = new GenericQueueUtil();
	private String className = "gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT";
	private static final String daoName = JNDINames.TEMPLATE_DAO;

	public ActionForward ManageTemplatesLib(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException,
	ServletException {


		ManageTemplateForm manageForm = (ManageTemplateForm) form;	
		HttpSession session = request.getSession(true);
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
				"NBSSecurityObject");
		NBSAuthHelper helper = new NBSAuthHelper();
		boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION);
		if (securityCheck != true) {
			session.setAttribute("error", "Failed at security checking. OR Page Management permission");
			throw new ServletException("Failed at security checking. OR Page Management permission");
		}
		
		//Generic Queue code
		QueueDT queueDT = fillQueueDT();
		manageForm.setQueueDT(queueDT);
        ArrayList<QueueColumnDT> queueCollection = genericQueueUtil.convertQueueDTToList(queueDT);
        manageForm.setQueueCollection(queueCollection);
        String queueString = genericQueueUtil.convertToString(queueDT);
        manageForm.setStringQueueCollection(queueString);
        request.setAttribute("queueCollection",queueCollection);
        request.setAttribute("stringQueueCollection",queueString);
        
		boolean forceEJBcall = false;
		boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
		if (initLoad && !PaginationUtil._dtagAccessed(request)) {
			manageForm.clearAll();
			forceEJBcall = true;
			// get the number of records to be displayed per page
			Integer queueSize = properties.getQueueSize(NEDSSConstants.PAGE_MANAGEMENT_TEMPLATE_LIBRARY_QUEUE_SIZE);
			manageForm.getAttributeMap().put("queueSize", queueSize);
		}
		String contextAction = request.getParameter("context");
		if (contextAction != null) {
			if(contextAction.equals("cancel"))
				forceEJBcall = false;
			else if(contextAction.equals("ReturnToManage") || contextAction.equals("RemoveAllFilters"))
				forceEJBcall = true;
		}
		
		
		//This flag becomes true when user clicks on Remove all filters
		if(contextAction!= null && contextAction.equals("RemoveAllFilters"))
			manageForm.setClearFilter(true);
			
		//To make sure SelectAll is checked, see if no criteria is applied
		if(manageForm.getSearchCriteriaArrayMap().size() == 0)
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
		
		
		String actionMd = request.getParameter("actionMode");
		if(manageForm.getActionMode()== null || (actionMd!= null && actionMd.equalsIgnoreCase("Manage")) || manageForm.getActionMode().equals("Manage") )
			manageForm.setActionMode(DynamicPageConstants.MANAGE);
		else{
			manageForm.setActionMode(manageForm.getActionMode());
			WaTemplateDT dt = (WaTemplateDT) manageForm.getSelection();
			String activeInd = dt.getRecordStatusCd();
			if(activeInd != null && activeInd.equals("Active"))
			{
				request.setAttribute("ActiveInd", "Inactive");
			}else if(activeInd != null && activeInd.equals("Inactive"))
			{
				request.setAttribute("ActiveInd", "Active");
			}
		}

		
		try {
			//GENERIC QUEUE
			GenericForm genericForm= translateFromTemplateLibraryListToObservationSummaryDisplayVO(manageForm);
			//\GENERIC QUEUE
			
			ArrayList<Object> TemplatesLibraryList = new ArrayList<Object> ();
			if(forceEJBcall){
				TemplatesLibraryList = PageManagementActionUtil.getTemplateCollection(session);
				
				manageForm.getAttributeMap().put("manageList",TemplatesLibraryList);
				//Set InvSummaryVOColl as property of Form first time since this list is used for distinct dropdown values in the form
				manageForm.setWaTemplateDTColl(TemplatesLibraryList);
				
				manageForm.getAttributeMap().put("templateNCount",new Integer(manageForm.getLastUpdated().size()));
				manageForm.getAttributeMap().put("templateDCount",new Integer(manageForm.getLastUpdatedBy().size()));
				
				manageForm.getAttributeMap().put("lastUpdatedCount",new Integer(manageForm.getLastUpdated().size()));
				manageForm.getAttributeMap().put("lastUpdatedByCount",new Integer(manageForm.getLastUpdatedBy().size()));
				manageForm.getAttributeMap().put("sourceCount",new Integer(manageForm.getSource().size()));
				manageForm.getAttributeMap().put("statusCount",new Integer(manageForm.getStatus().size()));
				Iterator<Object> iter = TemplatesLibraryList.iterator();
				while(iter.hasNext()) {
					WaTemplateDT dt = (WaTemplateDT) iter.next();
					String templateUid = dt.getWaTemplateUid().toString();
					request.setAttribute("templateUid", templateUid);
					dt.setLastChgUserNm(helper.getUserName(dt.getLastChgUserId()));
					dt.setLastChgDate(StringUtils.formatDate(dt.getLastChgTime()));
					
					//dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
				}	
				
				manageForm.getAttributeMap().put("manageList",TemplatesLibraryList);
				manageForm.setWaTemplateDTColl(TemplatesLibraryList);
				genericForm.setElementColl(TemplatesLibraryList);
				
				//GENERIC QUEUE
				manageForm.initializeDropDowns(manageForm.getWaTemplateDTColl(), genericForm, manageForm.getCLASS_NAME());
				
				Map<Object, Object> map = genericQueueUtil.setQueueCount(genericForm, genericForm.getQueueDT());
				manageForm.setAttributeMap(map);
				//\GENERIC QUEUE
				
			}else {
				TemplatesLibraryList = (ArrayList<Object> ) manageForm.getAttributeMap().get("manageList");
			}
			boolean existing = request.getParameter("existing") == null ? false : true;
			if(contextAction != null && contextAction.equalsIgnoreCase("ReturnToManage")) {
				PageManagementActionUtil.updateTemplateLinks(TemplatesLibraryList, request);
				//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
				Collection<Object>  filteredColl = genericQueueUtil.filterQueue(genericForm, request, className);
				if(filteredColl != null)
					TemplatesLibraryList = (ArrayList<Object> ) filteredColl;
				genericQueueUtil.sortQueue(genericForm, TemplatesLibraryList, true, request,"getTemplateNm");
			} else {
				
				genericQueueUtil.sortQueue(genericForm, TemplatesLibraryList, existing, request,"getTemplateNm");
				
				//This flag becomes true when user clicks on Remove all filters
				if(!manageForm.isClearFilter()){
					//Map<Object, Object> defaultFilter = new HashMap<Object, Object>();
					String[] filterValue = {"Active","Inactive"};//Generic?
					String filterProperty = "STATUS";//Generic?
					//defaultFilter.put(filterProperty, filterValue);
					Map<Object, Object> searchCriteriaMap = manageForm.getSearchCriteriaArrayMap();
					searchCriteriaMap.put(filterProperty, filterValue);
					genericForm.setSearchCriteriaArrayMap(searchCriteriaMap);
					Collection<Object>  filteredColl = genericQueueUtil.filterQueueWithDefaultCodedFilter(genericForm, request, className,searchCriteriaMap);
					//manageForm.setClearFilter(true);
					if(filteredColl != null)
						TemplatesLibraryList = (ArrayList<Object> ) filteredColl;
					request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
				}
				
				//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
				if(!existing) {
					PageManagementActionUtil.updateTemplateLinks(TemplatesLibraryList, request);
				}else {
					genericQueueUtil.filterQueue(genericForm, request, className);
					PageManagementActionUtil.updateTemplateLinks(TemplatesLibraryList, request);
				}
				
			}
			
			request.setAttribute("manageList", TemplatesLibraryList);
			manageForm.getAttributeMap().put("queueCount", String.valueOf(TemplatesLibraryList.size()));
			request.setAttribute("queueCount", String.valueOf(TemplatesLibraryList.size()));
			manageForm.setManageList(TemplatesLibraryList);
		} catch (Exception e) {
			logger.error("Exception in ManageTemplatesLib: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		} finally {
			request.setAttribute(DynamicPageConstants.PAGE_TITLE ,"Manage Templates: Template Library");
			manageForm.getAttributeMap().put("Create", "/nbs/ManageTemplates.do?method=createTemplatesLoad#template");
		}
		
		   request.setAttribute("queueCollection",manageForm.getQueueCollection());
	       request.setAttribute("stringQueueCollection",manageForm.getStringQueueCollection());
		
	       return PaginationUtil.paginate(manageForm, request, "default",mapping);

	}



	private Collection<Object>  filterTemplateLib(ManageTemplateForm manageForm, HttpServletRequest request) throws Exception {

		Collection<Object>  manageTemplist = new ArrayList<Object> ();

		String srchCriteriaLastUpdated = null;
		String srchCriteriaLastUpdatedBy = null;
		String srchCriteriaSource = null;
		String srchCriteriaStatus = null;
		String sortOrderParam = null;
		String srchCriteriaTemplateNm = null;
		String srchCriteriaTemplateDescription = null;

		try {

			Map<Object, Object> searchCriteriaMap = manageForm.getSearchCriteriaArrayMap();
			//GENERIC QUEUE
			GenericForm genericForm= translateFromTemplateLibraryListToObservationSummaryDisplayVO(manageForm);
			//\GENERIC QUEUE
			// Get the existing SummaryVO collection in the form
			ArrayList<Object> templateList = (ArrayList<Object> ) manageForm.getWaTemplateDTColl();
		
			// Filter by the investigator
			manageTemplist = getFilteredTemplateLib(templateList, searchCriteriaMap);
			String[] templateName = (String[]) searchCriteriaMap.get("TEMPLATENAME");
			String[] templateDescription = (String[]) searchCriteriaMap.get("TEMPLATEDESCRIPTION");
			
			String[] lastUpdated = (String[]) searchCriteriaMap.get("LASTUPDATED");
			String[] lastUpdatedBy = (String[]) searchCriteriaMap.get("LASTUPDATEDBY");
			String[] source = (String[]) searchCriteriaMap.get("SOURCE");
			String[] status = (String[]) searchCriteriaMap.get("STATUS");

			Integer templateNameCount = new Integer(templateName == null ? 0 : templateName.length);
			Integer templateDescriptionCount = new Integer(templateDescription == null  ? 0 : templateDescription.length);
			
			Integer lastUpdatedCount = new Integer(lastUpdated == null ? 0 : lastUpdated.length);
			Integer lastUpdatedByCount = new Integer(lastUpdatedBy == null  ? 0 : lastUpdatedBy.length);
			Integer sourceCount = new Integer(source == null  ? 0 : source.length);
			Integer statusCount = new Integer(status == null ? 0 : status.length);


			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if(templateNameCount.equals((manageForm.getAttributeMap().get("templateNCount"))) &&

					(templateDescriptionCount.equals(manageForm.getAttributeMap().get("templateDCount"))) && 
					
					lastUpdatedCount.equals((manageForm.getAttributeMap().get("lastUpdatedCount"))) &&

					(lastUpdatedByCount.equals(manageForm.getAttributeMap().get("lastUpdatedByCount"))) &&
					(sourceCount.equals(manageForm.getAttributeMap().get("sourceCount"))) &&
					(statusCount.equals(manageForm.getAttributeMap().get("statusCount"))) )
			{

				String sortMethod = getSortMethod(request, manageForm);
				String direction = getSortDirection(request, manageForm);			
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<?,?> sColl =  manageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<?,?>) manageForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = PageManagementActionUtil.getSortCriteriaForTemplate(direction, sortMethod);
				}				
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
				return null;				
			}

			
			ArrayList<Object> lastUpdatedList = manageForm.getLastUpdated();
			ArrayList<Object> lastUpdatedListByList = manageForm.getLastUpdatedBy();
			ArrayList<Object> sourceList = manageForm.getSource();
			ArrayList<Object> statusList = manageForm.getStatus();


			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			String sortMethod = getSortMethod(request, manageForm);
			String direction = getSortDirection(request, manageForm);			
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object, Object> sColl =  manageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<Object, Object>) manageForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = PageManagementActionUtil.getSortCriteriaForTemplate(direction, sortMethod);
			}
			
			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				srchCriteriaTemplateNm = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				srchCriteriaTemplateDescription = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
			}
			
			srchCriteriaLastUpdated = queueUtil.getSearchCriteria(lastUpdatedList, lastUpdated, NEDSSConstants.FILTERBYLASTUPDATEDATE);

			srchCriteriaLastUpdatedBy = queueUtil.getSearchCriteria(lastUpdatedListByList, lastUpdatedBy, NEDSSConstants.FILTERBYLASTUPDATEBY);
			srchCriteriaSource = queueUtil.getSearchCriteria(sourceList, source, NEDSSConstants.FILTERBYSOURCE);
			srchCriteriaStatus = queueUtil.getSearchCriteria(statusList, status, NEDSSConstants.FILTERBYRECORDSTATUS);


			//set the error message to the form
			if(sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if(srchCriteriaLastUpdated != null)
				searchCriteriaColl.put("INV147", srchCriteriaLastUpdated);
			if(srchCriteriaLastUpdatedBy != null)
				searchCriteriaColl.put("INV100", srchCriteriaLastUpdatedBy);
			if(srchCriteriaSource != null)
				searchCriteriaColl.put("INV163", srchCriteriaSource);
			if(srchCriteriaStatus != null)
				searchCriteriaColl.put("NOT118", srchCriteriaStatus);
			if(srchCriteriaTemplateNm != null)
				searchCriteriaColl.put("TEMPLATENM", srchCriteriaTemplateNm);
			if(srchCriteriaTemplateDescription != null)
				searchCriteriaColl.put("TEMPLATEDESCR", srchCriteriaTemplateDescription);
			
			manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);

		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Error while filtering the investigation by Investigator: "+ e.getMessage(), e);
			throw new ServletException("Error  in filterTemplateLib method: " +e.getMessage());
		} 
		 request.setAttribute("queueCollection",manageForm.getQueueCollection());
         request.setAttribute("stringQueueCollection",manageForm.getStringQueueCollection());
 		
		return manageTemplist;
	}
	public Collection<Object>  getFilteredTemplateLib(Collection<Object>  WaTemplateDTColl,
			Map<Object, Object> searchCriteriaMap) throws Exception {

		String[] lastUpdated = (String[]) searchCriteriaMap.get("LASTUPDATED");
		String[] lastUpdatedBy = (String[]) searchCriteriaMap.get("LASTUPDATEDBY");
		String[] source = (String[]) searchCriteriaMap.get("SOURCE");
		String[] status = (String[]) searchCriteriaMap.get("STATUS");

		String filterByTemplateNmText = null;
		String filterByTemplateDescriptionText = null;
		if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
			filterByTemplateNmText = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
		}
		if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
			filterByTemplateDescriptionText = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
		}
		Map<Object, Object> lastUpdatedMap = new HashMap<Object,Object>();
		Map<Object, Object> lastUpdatedByMap = new HashMap<Object,Object>();
		Map<Object, Object> sourceMap = new HashMap<Object,Object>();
		Map<Object, Object> statusMap = new HashMap<Object,Object>();

		if (lastUpdated != null && lastUpdated.length > 0)
			lastUpdatedMap = queueUtil.getMapFromStringArray(lastUpdated);

		if (lastUpdatedBy != null && lastUpdatedBy.length > 0)
			lastUpdatedByMap = queueUtil.getMapFromStringArray(lastUpdatedBy);
		if (source != null && source.length > 0)
			sourceMap = queueUtil.getMapFromStringArray(source);
		if (status != null && status.length >0)
			statusMap = queueUtil.getMapFromStringArray(status);

		try {
		if(lastUpdatedMap != null && lastUpdatedMap.size()>0)
			WaTemplateDTColl = filterLastUpdated(
					WaTemplateDTColl, lastUpdatedMap);
		if (sourceMap != null && sourceMap.size()>0)
			WaTemplateDTColl = filterSource(
					WaTemplateDTColl, sourceMap);
		if (lastUpdatedByMap != null && lastUpdatedByMap.size()>0)
			WaTemplateDTColl = filterLastChgUserId(
					WaTemplateDTColl, lastUpdatedByMap);
		if (statusMap != null && statusMap.size()>0)
			WaTemplateDTColl = filterStatus(
					WaTemplateDTColl, statusMap);
		if(filterByTemplateNmText!= null){
			
				WaTemplateDTColl = filterByText(WaTemplateDTColl, filterByTemplateNmText, NEDSSConstants.TEMPLATE_NM);
			
		}
		if(filterByTemplateDescriptionText!= null){
			WaTemplateDTColl = filterByText(WaTemplateDTColl, filterByTemplateDescriptionText, NEDSSConstants.TEMPLATE_DESCR);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Error while filtering the getFilteredTemplateLib: "+ e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}
		return WaTemplateDTColl;

	}


	public Collection<Object>  filterLastUpdated(Collection<Object> WaTemplateDTColl, Map<Object, Object> lastUpdatedMap) {
		Map<Object, Object>  newTempMap = new HashMap<Object,Object>();
		String strDateKey = null;
		if (WaTemplateDTColl != null) {
			Iterator<Object>  iter = WaTemplateDTColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if (dt.getLastChgTime() != null && lastUpdatedMap != null
						&& (lastUpdatedMap.size()>0 )) {
					Collection<Object>  dateSet = lastUpdatedMap.keySet();
					if(dateSet != null){
						Iterator<Object>  iSet = dateSet.iterator();
						while (iSet.hasNext()){
							strDateKey = (String)iSet.next();
							if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY))){
								if(queueUtil.isDateinRange(dt.getLastChgTime(),strDateKey)){
									newTempMap.put(dt.getWaTemplateUid().toString(), dt);
								}	

							}  
						}
					}
				}

				if(dt.getLastChgTime() == null || dt.getLastChgTime().equals("")){
					if(lastUpdatedMap != null && lastUpdatedMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)){
						newTempMap.put(dt.getWaTemplateUid().toString(), dt);
					}
				}

			}
		} 	


		return convertTempMaptoColl(newTempMap);

	}

	private Collection<Object>  convertTempMaptoColl(Map<Object, Object>  newTempMap){
		Collection<Object>  invColl = new ArrayList<Object> ();
		if(newTempMap !=null && newTempMap.size()>0){
			Collection<Object>  tempKeyColl = newTempMap.keySet();
			Iterator<Object>  iter = tempKeyColl.iterator();
			while(iter.hasNext()){
				String tempKey = (String)iter.next();
				invColl.add(newTempMap.get(tempKey));
			}
		}
		return invColl;
	}


	public Collection<Object>  filterLastChgUserId(
			Collection<Object>  WaTemplateDTColl, Map<Object,Object> nndLastChgUserIdMap) {
		Collection<Object>  newLastUpdatedByColl = new ArrayList<Object> ();
		NBSAuthHelper helper = new NBSAuthHelper();
		if (WaTemplateDTColl != null) {
			Iterator<Object> iter = WaTemplateDTColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if (dt.getLastChgUserId() != null
						&& nndLastChgUserIdMap != null
						&& nndLastChgUserIdMap.containsKey(helper.getUserName(dt.getLastChgUserId()))) {
					newLastUpdatedByColl.add(dt);
				}
				if(dt.getLastChgUserId() == null || dt.getLastChgUserId().equals("")){
					if(nndLastChgUserIdMap != null && nndLastChgUserIdMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newLastUpdatedByColl.add(dt);
					}
				}

			}

		}
		return newLastUpdatedByColl;

	}

	public Collection<Object>  filterStatus(
			Collection<Object>  WaTemplateDTColl, Map<Object,Object> statusMap) {
		Collection<Object>  newStatusColl = new ArrayList<Object> ();
		if (WaTemplateDTColl != null) {
			Iterator<Object> iter = WaTemplateDTColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if (dt.getRecStatusCd() != null	&& statusMap != null && statusMap.containsKey(dt.getRecStatusCd())) {
					newStatusColl.add(dt);
				}
				if(dt.getRecStatusCd() == null || dt.getRecStatusCd().equals("")){
					if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newStatusColl.add(dt);
					}
				}

			}

		}
		return newStatusColl;

	}
	
	public Collection<Object>  filterByText(
			Collection<Object>  waTemplateDtColl, String filterByText,String column) throws Exception {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (waTemplateDtColl != null) {
			Iterator<Object> iter = waTemplateDtColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if(column.equals(NEDSSConstants.TEMPLATE_NM) && dt.getTemplateNm() != null && dt.getTemplateNm().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
				if(column.equals(NEDSSConstants.TEMPLATE_DESCR) && dt.getDescTxt()!= null && dt.getDescTxt().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
			}
		}
		}catch(Exception ex){
			 logger.fatal("Error filtering the filterByText : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return newTypeColl;
	}
	
	public Collection<Object>  filterSource(
			Collection<Object>  WaTemplateDTColl, Map<Object,Object> sourceMap) {
		Collection<Object>  newSourceColl = new ArrayList<Object> ();
		if (WaTemplateDTColl != null) {
			Iterator<Object> iter = WaTemplateDTColl.iterator();
			while (iter.hasNext()) {
				WaTemplateDT dt = (WaTemplateDT) iter.next();
				if (dt.getSourceNm() != null	&& sourceMap != null && sourceMap.containsKey(dt.getSourceNm())) {
					newSourceColl.add(dt);
				}
				if(dt.getSourceNm() == null || dt.getSourceNm().equals("")){
					if(sourceMap != null && sourceMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newSourceColl.add(dt);
					}
				}

			}

		}
		return newSourceColl;

	}
	private String getSortMethod(HttpServletRequest request, ManageTemplateForm manageForm ) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
		} else{
			return manageForm.getAttributeMap().get("methodName") == null ? null : (String) manageForm.getAttributeMap().get("methodName");
		}

	}
	private String getSortDirection(HttpServletRequest request, ManageTemplateForm manageForm) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		} else{
			return manageForm.getAttributeMap().get("sortOrder") == null ? "1": (String) manageForm.getAttributeMap().get("sortOrder");
		}

	}
	private void sortManageTemplate(ManageTemplateForm manageForm, Collection<Object>  recFacilityList, boolean existing, HttpServletRequest request) throws Exception{

		// Retrieve sort-order and sort-direction from displaytag params
		String sortMethod = getSortMethod(request, manageForm);
		String direction = getSortDirection(request, manageForm);

		boolean invDirectionFlag = true;
		if (direction != null && direction.equals("2"))
			invDirectionFlag = false;


		NedssUtils util = new NedssUtils();

		//Read from properties file to determine default sort order
		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
			sortMethod = "getTemplateNm";
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
			String sortOrderParam = PageManagementActionUtil.getSortCriteriaForTemplate(invDirectionFlag == true ? "1" : "2", sortMethod);
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			searchCriteriaColl.put("sortSt", sortOrderParam);
			manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}
	}
	private void updateListBeforeSort(Collection<Object>  recFacilityList) {
		Iterator<Object> iter = recFacilityList.iterator();
		while (iter.hasNext()) {
			WaTemplateDT dt = (WaTemplateDT)  iter.next();

			if (dt.getWaTemplateUid().toString()== null || (dt.getWaTemplateUid().toString() != null && dt.getWaTemplateUid().toString().equals(""))) {
				dt.setWaTemplateUid(null);
			}
		}

	}

	private void updateListAfterSort(Collection<Object>  notifiSummaryVOs) {
		Iterator<Object> iter = notifiSummaryVOs.iterator();
		while (iter.hasNext()) {
			WaTemplateDT dt = (WaTemplateDT)  iter.next();
			if (dt.getWaTemplateUid().toString() != null && dt.getWaTemplateUid().toString().equals("ZZZZZ")) {
				dt.setWaTemplateUid(null);
			}


		}

	}

	public ActionForward filterTemplateSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ManageTemplateForm manageForm  = (ManageTemplateForm) form;
		
		//This flag becomes true when user clicks on Remove all filters
	/*	if(!manageForm.isClearFilter()) 
		{
			Map<Object, Object> searchCriteriaMap = manageForm.getSearchCriteriaArrayMap();
			String[] filterValue = {"Active"};//Generic?
			String filterProperty = "STATUS";//Generic?
			searchCriteriaMap.put(filterProperty, filterValue);
		}*/
		
		//GENERIC QUEUE
		GenericForm genericForm= translateFromTemplateLibraryListToObservationSummaryDisplayVO(manageForm);
		//\GENERIC QUEUE
		
		Collection<Object>  templateList = genericQueueUtil.filterQueue(genericForm, request, className);
		if(templateList != null){
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
			//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
		}else{
			templateList = (ArrayList<Object> ) manageForm.getWaTemplateDTColl();
		}
		manageForm.getAttributeMap().put("manageList",templateList); 
		manageForm.getAttributeMap().put("queueList",templateList);
		request.setAttribute("queueList", templateList);
		genericQueueUtil.sortQueue(genericForm, templateList, true, request,"getTemplateNm");
		request.setAttribute("manageList", templateList);
		//request.setAttribute("queueCount", String.valueOf(recFacilityList.size()));
		manageForm.getAttributeMap().put("queueCount", String.valueOf(templateList.size()));
		request.setAttribute("PageTitle","Manage Templates: Template Library");
		manageForm.getAttributeMap().put("PageNumber", "1");
		request.setAttribute("queueCollection",manageForm.getQueueCollection());
        request.setAttribute("stringQueueCollection",manageForm.getStringQueueCollection());
		return PaginationUtil.paginate(manageForm, request, "default",mapping);

	} 

	public ActionForward viewTemplate(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception
			{	
		try{
			Long waTemplateUid = null;
			PageForm pageForm = (PageForm)form; 
			pageForm.setSelection(new WaTemplateDT());
			PageManagementProxyVO pmProxyVO = new PageManagementProxyVO();  
			PageManagementActionUtil util = new PageManagementActionUtil();
			if(request.getParameter("templateUid") != null){
				//waTemplateUid= (Long)Long.parseLong((request.getParameter("templateUid")));
					try{
						 waTemplateUid= (Long)Long.parseLong((request.getParameter("templateUid")));
						 request.getSession().setAttribute("waTemplateUid", waTemplateUid);
						 if(request.getParameter("src") != null && request.getParameter("src").toString().equalsIgnoreCase("import"))
			    	        request.setAttribute("confirmation", "uploadsuccess");
						 else if(request.getParameter("src") != null && request.getParameter("src").toString().equalsIgnoreCase("export"))
				    	        request.setAttribute("confirmation", "exportsuccess");
					}catch(Exception ex) {  
						if(request.getParameter("templateUid").toString().equalsIgnoreCase("templateAlreadyExists"))
							request.setAttribute("status", "templateAlreadyExists");
						if(request.getParameter("templateUid").toString().equalsIgnoreCase("filenotfound"))
							request.setAttribute("status", "corrupt file template");
						if(request.getParameter("templateUid").toString().equalsIgnoreCase("TNF"))//template not found
							request.setAttribute("status", "filenotfound");
						if(request.getParameter("srcTemplateNm")!= null)
						 request.setAttribute("srcTemplateNm",HTMLEncoder.encodeHtml(request.getParameter("srcTemplateNm").toString()));
			          	 request.setAttribute("confirmation", "uploaderror");
			          	 request.setAttribute("Template", "Template");
	          	         return mapping.findForward("errorimportTemplate");
					}
			}			  
			else {
				waTemplateUid= (Long)request.getSession().getAttribute("templateUid");
			}
			request.setAttribute("templateUid", waTemplateUid);
			request.setAttribute("PageTitle", "Manage Templates: View Template");
			try{
				 Map<Object,Object> batchMap = util.getBatchMap(waTemplateUid, request.getSession());
	        	 pageForm.setSubSecStructureMap(batchMap);
	        	 request.setAttribute("SubSecStructureMap", batchMap);
	        	 request.setAttribute("disableSubmitButton", "yes");
	        	 pageForm.setBatchEntryMap(new HashMap<Object,ArrayList<BatchEntry>>());
				pmProxyVO =  util.getPageDraft(waTemplateUid, request.getSession());
			}catch(Exception ex){
				logger.fatal("Error in getting the page draft "+ ex.getMessage(), ex);
				throw new ServletException(ex.getMessage(), ex);
			}
			String result = "";
			String type = "TEMP";
			try{
				result = util.generateXMLandJSP(pmProxyVO,type);
			}catch(Exception ex){
				logger.fatal("Error in genarating the JSP : "+ ex.getMessage(), ex);
				throw new ServletException(ex.getMessage(), ex);
			}
			if(result!= null && !result.equals("failure"))
				pageForm.setActionMode(NEDSSConstants.PREVIEW_ACTION);	 
			_setRenderDir(request,pmProxyVO);
			String templateNm = pmProxyVO.getWaTemplateDT().getTemplateNm();
			pageForm.setPageTitle("View "+templateNm,request);
			request.setAttribute("conditiondesc", HTMLEncoder.encodeHtml(templateNm));
			request.getSession().setAttribute("PageForm", pageForm);
			request.setAttribute("mode", request.getParameter("mode"));
			BaseForm baseform = pageForm;
			request.getSession().setAttribute("BaseForm", baseform);
			HttpSession session = request.getSession();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			boolean viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
					NBSOperationLookup.VIEW);

			pageForm.getSecurityMap().put("checkToViewContactTracing", String.valueOf(viewContactTracing));
			boolean HIVQuestionPermission = nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL,
					NBSOperationLookup.HIVQUESTIONS);
			if(HIVQuestionPermission)
				pageForm.getSecurityMap().put("hasHIVPermissions", NEDSSConstants.TRUE);
				
			String messageInd="";
			if(request.getParameter("status") != null && request.getParameter("status").toString().equalsIgnoreCase("I"))
				messageInd = "INACTIVE";
			if(request.getParameter("status") != null && request.getParameter("status").toString().equalsIgnoreCase("A"))
				messageInd = "ACTIVE";
			String conditionCd = "";
			String mode ="";
			
			if(request.getParameter("mode")!=null)
				mode = request.getParameter("mode").toString();
			
			request.setAttribute("conditionCode", HTMLEncoder.encodeHtml(conditionCd)); 	
			 
			request.setAttribute("messageInd", messageInd);
			request.setAttribute("Template", result);
			request.getSession().setAttribute("templateNm",templateNm);
			request.getSession().setAttribute("pgPageName",templateNm);

			String activeInd = pmProxyVO.getWaTemplateDT().getRecStatusCd();
			
			
			if(activeInd != null && activeInd.equals("Active") && !mode.equalsIgnoreCase("print"))
			{
				request.setAttribute("ActiveInd", "Inactive");
			}else if(activeInd != null && activeInd.equals("Inactive") && !mode.equalsIgnoreCase("print"))
			{
				request.setAttribute("ActiveInd", "Active");
			}

			return mapping.findForward("viewTemplate");
		}catch(Exception ex){
			logger.fatal("Error in the preview of the page :" + ex.getMessage(), ex);
			throw new ServletException(ex.getMessage(), ex);
		}

			}
	private void _setRenderDir(HttpServletRequest request,PageManagementProxyVO pmProxyVO) {
		String templateUid = "";
		String renderDir = "";
		if(pmProxyVO.getWaTemplateDT()!= null)
			templateUid = pmProxyVO.getWaTemplateDT().getWaTemplateUid().toString();		

		if(templateUid != null && !templateUid.equals("")) {
			renderDir = "preview"+"/PG_" + templateUid;
			request.setAttribute("renderDir", renderDir);
			request.getSession().setAttribute(NBSConstantUtil.DSInvestigationCondition, templateUid);
		}

	}	


	public ActionForward makeInactive(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		ActionForward forwardNew = new ActionForward();
		ManageTemplateForm manageForm = (ManageTemplateForm) form;	
		ArrayList<Object> templateList = new ArrayList<Object>();
		HttpSession session = request.getSession(true);
		StringBuffer sb = new StringBuffer();
		String templateUid = null;
		try {
			templateUid = request.getParameter("templateUid");
			WaTemplateDT dt = null;
			if(templateUid != null && templateUid.length() > 0) {
				ArrayList<?> testList = manageForm.getManageList();
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					dt = (WaTemplateDT) iter.next();
					if(dt.getWaTemplateUid().toString().equals(templateUid)) {
						manageForm.setSelection(dt);						
						manageForm.setOldDT(dt);
						break;
					}
				}				
			}	
			Object[] searchParams = new Object[] {dt.getWaTemplateUid()};
			Object[] oParams = new Object[] { daoName, "inactivateTemplate", searchParams };
			PageManagementActionUtil.processRequest(oParams, request.getSession());
			sb.append("<b>").append(HTMLEncoder.encodeHtml(dt.getTemplateNm())).append("</b>  has been made inactive.");
			request.setAttribute("ConfirmMesg", sb.toString());
			templateList = PageManagementActionUtil.getTemplateCollection(session);
			WaTemplateDT activeDt = (WaTemplateDT) manageForm.getSelection();
			activeDt.setRecStatusCd("Inactive");
			manageForm.setSelection(activeDt);
			request.setAttribute("templateUid", dt.getWaTemplateUid());
		}catch (Exception e) {
			manageForm.setManageList(templateList);
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			logger.error("Exception in makeInactive: " + e.getMessage(), e);
		}
		manageForm.setManageList(templateList);
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		request.setAttribute(DynamicPageConstants.PAGE_TITLE ,"Manage Templates: View Template");
		String strURL ="/PreviewTemplate.do?method=viewTemplate&templateUid="+templateUid+"&status=I";
		forwardNew.setPath(strURL.toString());
		return forwardNew;		
	}

	public ActionForward makeActive(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		ManageTemplateForm manageForm = (ManageTemplateForm) form;	
		ActionForward forwardNew = new ActionForward();
		ArrayList<Object> templateList = new ArrayList<Object>();
		HttpSession session = request.getSession(true);
		StringBuffer sb = new StringBuffer();
		String templateUid = null;
		try {
			templateUid = request.getParameter("templateUid");
			WaTemplateDT dt = null;
			if(templateUid != null && templateUid.length() > 0) {
				ArrayList<?> testList = manageForm.getManageList();
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					dt = (WaTemplateDT) iter.next();
					if(dt.getWaTemplateUid().toString().equals(templateUid)) {
						manageForm.setSelection(dt);						
						manageForm.setOldDT(dt);
						break;
					}
				}				
			}	
			Object[] searchParams = new Object[] {dt.getWaTemplateUid()};
			Object[] oParams = new Object[] { daoName, "activateTemplate", searchParams };
			PageManagementActionUtil.processRequest(oParams, request.getSession());
			sb.append("<b>").append(HTMLEncoder.encodeHtml(dt.getTemplateNm())).append("</b>  has been made active.");
			request.setAttribute("ConfirmMesg", sb.toString());
			templateList = PageManagementActionUtil.getTemplateCollection(session);
			WaTemplateDT activeDt = (WaTemplateDT) manageForm.getSelection();
			activeDt.setRecStatusCd("Active");
			manageForm.setSelection(activeDt);
			request.setAttribute("templateUid", dt.getWaTemplateUid());
		}catch (Exception e) {
			manageForm.setManageList(templateList);
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			logger.error("Exception in makeActive: " + e.getMessage(), e);
		}
		manageForm.setManageList(templateList);
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		request.setAttribute(DynamicPageConstants.PAGE_TITLE ,"Manage Templates: View Template");
		String strURL ="/PreviewTemplate.do?method=viewTemplate&templateUid="+templateUid+"&status=A";
		forwardNew.setPath(strURL.toString());
		return forwardNew;	
	}
	
	public ActionForward exportTemplate(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		ManageTemplateForm manageForm = (ManageTemplateForm) form;	
		ActionForward forwardNew = new ActionForward();
		ArrayList<Object> templateList = new ArrayList<Object>();
		HttpSession session = request.getSession(true);
		StringBuffer sb = new StringBuffer();
		String templateUid = null;
		String templateNm="";
		String status="";
		try {
			templateUid = request.getParameter("templateUid");
			PageManagementActionUtil util = new PageManagementActionUtil();
			WaTemplateDT dt = null;
			if(templateUid != null && templateUid.length() > 0) {
				ArrayList<?> testList = manageForm.getManageList();
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					dt = (WaTemplateDT) iter.next();
					if(dt.getWaTemplateUid().toString().equals(templateUid)) {
						manageForm.setSelection(dt);						
						manageForm.setOldDT(dt);
						templateNm = dt.getTemplateNm();
						break;
					}
				}				
			}	
			//String ret = util.handleExportImportofXML(session,templateUid);
			TemplateExportType obj = util.handleExportImportofXML(session,templateUid,templateNm);
			
		    int                 length   = 0;
			    response.setContentType("application/octet-stream"); 
		        response.setHeader("Content-disposition", "attachment;filename=\""+templateNm+".xml\""); 
		        ServletOutputStream op       = response.getOutputStream();		      
		        byte[] bbuf = new byte[1000];
		       // InputStream is = new ByteArrayInputStream(text.getBytes("UTF-8"));        
		      
		        InputStream is = new ByteArrayInputStream(obj.xmlText().getBytes("UTF-8")); 
		        DataInputStream in = new DataInputStream(is);
		        while ((in != null) && ((length = in.read(bbuf)) != -1))
		        {
		            op.write(bbuf,0,length);
		        }
		        in.close();
		        op.flush();
		        op.close();

			request.setAttribute("manageListExport",obj);
			request.setAttribute("templateUid", HTMLEncoder.encodeHtml(templateUid));
			status="success";

		}
		catch (Exception e) {
			status="fail";
			request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
			logger.error("Exception in exportTemplate: " + e.getMessage(), e);
			e.printStackTrace();
		}		
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		request.setAttribute(DynamicPageConstants.PAGE_TITLE ,"Manage Templates: View Template");
		String strURL ="/PreviewTemplate.do?method=viewTemplate&templateUid="+templateUid+"&src=export&status="+status;
		
			forwardNew.setPath(strURL.toString());
			return forwardNew;		
		
	}
	public ActionForward importTemplateLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		return mapping.findForward("importTemplateLoad");
		
	}
	
public ActionForward importTemplate(ActionMapping mapping, 
		ActionForm aForm, 
		HttpServletRequest request, 
		HttpServletResponse response) {
	 ActionForward actionForward = new ActionForward();
	 ArrayList<Object> returnVal =  new ArrayList<Object>();
	 String ret ="";
	 String srcTemplateNm="";
	 try {
		 PageManagementActionUtil util = new PageManagementActionUtil();
         ManageTemplateForm form = (ManageTemplateForm) aForm;
          String  filePath = request.getParameter("FilePath"); 
          FormFile file = form.getImportFile();
          int maxSizeInMB = PropertyUtil.getInstance().getMaxFileAttachmentSizeInMB();
          long maxSizeInBytes = maxSizeInMB * 1024 * 1024;          
               
        
           if(file == null || file.getFileData() == null){
        	   ret = "TNF";
        	   String strURL ="/nbs/PreviewTemplate.do?method=viewTemplate&srcTemplateNm="+srcTemplateNm+"&src=Import&templateUid="+ret;
         	     response.sendRedirect(strURL);
        	   
           }
            returnVal = util.readImportXML(request.getSession(), filePath,file.getFileData());  
            ret = returnVal.get(0).toString();
            srcTemplateNm = returnVal.get(1).toString();
           
             try{ 
            	 new java.math.BigInteger(ret);  
            	 request.setAttribute("confirmation", "uploadsuccess");
            	 }
             catch(NumberFormatException ex) {  
            	 request.setAttribute("confirmation", "uploaderror");
            	 }        

         request.setAttribute("templateUid", ret);

         String strURL ="/nbs/PreviewTemplate.do?method=viewTemplate&srcTemplateNm="+srcTemplateNm+"&src=Import&templateUid="+ret;
   	     response.sendRedirect(strURL);
		
	  } catch (Exception e) {
        logger.error("Error in ImportTemplate " + e.getMessage(), e);
        e.printStackTrace();
        request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
      }
	  

      return null;
  }

public static byte[] getBytes(Object obj) throws java.io.IOException{
    ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
    ObjectOutputStream oos = new ObjectOutputStream(bos); 
    oos.writeObject(obj);
    oos.flush(); 
    oos.close(); 
    bos.close();
    byte [] data = bos.toByteArray();
    return data;
}

public ActionForward manageImportExportLogLib(ActionMapping mapping, 
		ActionForm aForm, 
		HttpServletRequest request, 
		HttpServletResponse response) {
	 try {
         
         String strURL ="/nbs/ManageImpExpLog.do?method=manageImportExportLogLib&actionMode=Manage&initLoad=true";
   	     response.sendRedirect(strURL);
		
	  } catch (Exception e) {
        logger.error("Error in manageImportExportLogLib = " +  e.getMessage(), e);
        e.printStackTrace();
        request.setAttribute("error", HTMLEncoder.encodeHtml(e.getMessage()));
      }
      return null;
  } 
public QueueDT fillQueueDT(){

    

    //ArrayList<QueueColumnDT> queueDTcollection = new ArrayList<QueueColumnDT>();

    QueueDT queueDT = new QueueDT();

    //First column: Template Name

    QueueColumnDT queue = new QueueColumnDT();
    queue.setColumnId("column1");
    queue.setColumnName("Template Name");
    queue.setBackendId("TEMPLATENM");
    queue.setDefaultOrder("ascending");
    queue.setSortable("true");
    queue.setSortNameMethod("getTemplateNm");
    queue.setMedia("html pdf csv");
    queue.setMediaHtmlProperty("templateNm");
    queue.setMediaPdfProperty("templateNm");
    queue.setMediaCsvProperty("templateNm");
    queue.setColumnStyle("width:15%");
    queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    queue.setDropdownProperty("SearchText1");
    queue.setDropdownStyleId("templateName");
    queue.setDropdownsValues("noDataArray");
    queue.setErrorIdFiltering("TEMPLATENM");//Same than BackendId?
    queue.setConstantCount("templateDCount");
    queue.setMethodFromElement("getTemplateNm");//TODO; change name??
    queue.setMethodGeneralFromForm("??");//Only for date/multiselect
    queue.setFilterByConstant("??");
    queueDT.setColumn1(queue);


    //Second column: Template Description

    queue = new QueueColumnDT();
    queue.setColumnId("column2");
    queue.setColumnName("Template Description");
    queue.setBackendId("TEMPLATEDESCR");
    queue.setDefaultOrder("ascending");
    queue.setSortable("true");
    queue.setSortNameMethod("getDescTxt");
    queue.setMedia("html pdf csv");
    queue.setMediaHtmlProperty("descTxt");
    queue.setMediaPdfProperty("descTxt");
    queue.setMediaCsvProperty("descTxt");
    queue.setColumnStyle("width:22%");
    queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    queue.setDropdownProperty("SearchText2");
    queue.setDropdownStyleId("templateDescr");
    queue.setDropdownsValues("noDataArray");
    queue.setErrorIdFiltering("TEMPLATEDESCR");//Same than BackendId?
    queue.setConstantCount("templateDCount");
    queue.setMethodFromElement("getDescTxt");
    queue.setMethodGeneralFromForm("??");//Only for date/multiselect
    queue.setFilterByConstant("??");
    queueDT.setColumn2(queue);

    //Third column: Last Updated

    queue = new QueueColumnDT();
    queue.setColumnId("column3");
    queue.setColumnName("Last Updated");
    queue.setBackendId("INV147");
    queue.setDefaultOrder("ascending");
    queue.setSortable("true");
    queue.setSortNameMethod("getLastChgTime");
    queue.setMedia("html pdf csv");
    queue.setMediaHtmlProperty("lastChgDate");
    queue.setMediaPdfProperty("lastChgDate");
    queue.setMediaCsvProperty("lastChgDate");
    queue.setColumnStyle("width:10%");
    queue.setFilterType("0");//0 date, 1 text, 2 multiselect
    queue.setDropdownProperty("LASTUPDATED");
    queue.setDropdownStyleId("lUpdated");
    queue.setDropdownsValues("lastUpdated");
    queue.setErrorIdFiltering("INV147");//Same than BackendId?
    queue.setConstantCount("lastUpdatedCount");
    queue.setMethodFromElement("getLastChgTime");
    queue.setMethodGeneralFromForm("getColumn3List");
    queue.setFilterByConstant(NEDSSConstants.FILTERBYLASTUPDATEDATE);
    queueDT.setColumn3(queue);

    //Forth column: Last Updated By

    queue = new QueueColumnDT();
    queue.setColumnId("column4");
    queue.setColumnName("Last Updated By");
    queue.setBackendId("INV100");//??
    queue.setDefaultOrder("ascending");
    queue.setSortable("true");
    queue.setSortNameMethod("getLastChgUserNm");
    queue.setMedia("html pdf csv");
    queue.setMediaHtmlProperty("lastChgUserNm");
    queue.setMediaPdfProperty("lastChgUserNm");
    queue.setMediaCsvProperty("lastChgUserNm");
    queue.setColumnStyle("width:12%");
    queue.setFilterType("2");//0 date, 1 text, 2 multiselect
    queue.setDropdownProperty("LASTUPDATEDBY");
    queue.setDropdownStyleId("lUpdatedBy");
    queue.setDropdownsValues("lastUpdatedBy");
    queue.setErrorIdFiltering("INV100");//Same than BackendId?
    queue.setConstantCount("lastUpdatedByCount");
    queue.setMethodGeneralFromForm("getColumn4List");
    queue.setMethodFromElement("getLastChgUserNm");
    queue.setFilterByConstant(NEDSSConstants.FILTERBYLASTUPDATEBY);
    queueDT.setColumn4(queue);


    //Fifth column: Source

    queue = new QueueColumnDT();

    queue.setColumnId("column5");
    queue.setColumnName("Source");
    queue.setBackendId("INV163");//??
    queue.setDefaultOrder("ascending");
    queue.setSortable("true");
    queue.setSortNameMethod("getSourceNm");
    queue.setMedia("html pdf csv");
    queue.setMediaHtmlProperty("sourceNm");
    queue.setMediaPdfProperty("sourceNm");
    queue.setMediaCsvProperty("sourceNm");
    queue.setColumnStyle("width:8%");
    queue.setFilterType("2");//0 date, 1 text, 2 multiselect
    queue.setDropdownProperty("SOURCE");
    queue.setDropdownStyleId("source");
    queue.setDropdownsValues("source");
    queue.setErrorIdFiltering("INV163");//Same than BackendId?
    queue.setConstantCount("sourceCount");
    queue.setMethodFromElement("getSourceNm");
    queue.setMultipleValues("false");
    queue.setMethodGeneralFromForm("getColumn5List");//Only for date/multiselect
    queue.setFilterByConstant(NEDSSConstants.FILTERBYSOURCE);
        queueDT.setColumn5(queue);


    //Sixth column: Status

    queue = new QueueColumnDT();
    queue.setColumnId("column6");
    queue.setColumnName("Status");
    queue.setBackendId("NOT118");
    queue.setDefaultOrder("ascending");
    queue.setSortable("true");
    queue.setSortNameMethod("getRecStatusCd");
    queue.setMedia("html pdf csv");
    queue.setMediaHtmlProperty("recStatusCd");
    queue.setMediaPdfProperty("recStatusCd");
    queue.setMediaCsvProperty("recStatusCd");
    queue.setColumnStyle("width:6%");
    queue.setFilterType("2");//0 date, 1 text, 2 multiselect
    queue.setDropdownProperty("STATUS");
    queue.setDropdownStyleId("status");
    queue.setDropdownsValues("status");
    queue.setErrorIdFiltering("NOT118");//Same than BackendId?
    queue.setConstantCount("statusCount");
    queue.setMethodFromElement("getRecStatusCd");
    queue.setMethodGeneralFromForm("getColumn6List");//Only for date/multiselect
    queue.setFilterByConstant(NEDSSConstants.FILTERBYRECORDSTATUS);
    queueDT.setColumn6(queue);
    return queueDT;

}

private GenericForm translateFromTemplateLibraryListToObservationSummaryDisplayVO(ManageTemplateForm manageForm){

    

    GenericForm genericForm = new GenericForm();

    genericForm.setSearchCriteriaArrayMap(manageForm.getSearchCriteriaArrayMap());

    genericForm.setAttributeMap(manageForm.getAttributeMap());

    genericForm.setQueueDT(manageForm.getQueueDT());

    //general methods from form
 
    genericForm.setColumn3List(manageForm.getLastUpdated());
    genericForm.setColumn4List(manageForm.getLastUpdatedBy());
    genericForm.setColumn5List(manageForm.getSource());
    genericForm.setColumn6List(manageForm.getStatus());

    

    Collection<Object> observationCollection = manageForm.getWaTemplateDTColl();

    //Collection<Object> genericCollection = new ArrayList<Object>();

    genericForm.setElementColl(observationCollection);

    

    return genericForm;

}
}
