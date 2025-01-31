package gov.cdc.nedss.webapp.nbs.action.pagemanagement.managetemplates;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.DynamicPageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managetemplate.ManageImportExportLogForm;

public class ManageImportExportLogAction extends DispatchAction {
	
	static final LogUtils logger = new LogUtils(ManageImportExportLogAction.class.getName());
	QueueUtil queueUtil = new QueueUtil();
	PropertyUtil properties = PropertyUtil.getInstance();
	
	
	public ActionForward manageImportExportLogLib(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException,
	ServletException {
		ManageImportExportLogForm manageForm = (ManageImportExportLogForm) form;	
		HttpSession session = request.getSession(true);
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
				"NBSSecurityObject");
		// Do we need a security check here  -- what is the business object?
		
		boolean forceEJBcall = false;
		boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
		if (initLoad && !PaginationUtil._dtagAccessed(request)) {
			manageForm.clearAll();
			forceEJBcall = true;
			// get the number of records to be displayed per page
			Integer queueSize = properties.getQueueSize(NEDSSConstants.PAGE_MANAGEMENT_IMPORTEXPORT_LIBRARY_QUEUE_SIZE);
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
		}
		
		try {

            ArrayList<Object> impExpLogList = new ArrayList<Object> ();
			if(forceEJBcall){				
				impExpLogList = PageManagementActionUtil.getActivityLogCollection(session);
				manageForm.getAttributeMap().put("manageList",impExpLogList);
				manageForm.setActivityLogColl(impExpLogList);
				manageForm.initializeDropDowns();
				manageForm.getAttributeMap().put("processedTime",new Integer(manageForm.getProcessedTime().size()));
				manageForm.getAttributeMap().put("type",new Integer(manageForm.getType().size()));
				manageForm.getAttributeMap().put("templateName",new Integer(manageForm.getTemplateName().size()));
				manageForm.getAttributeMap().put("source",new Integer(manageForm.getSource().size()));
				manageForm.getAttributeMap().put("status",new Integer(manageForm.getStatus().size()));
				
				PageManagementActionUtil.updateImpExpLogLinks(impExpLogList, request);

			    }else{
			    	impExpLogList = (ArrayList<Object> ) manageForm.getAttributeMap().get("manageList");				
			    	request.setAttribute("manageList",impExpLogList);
			    }   
			    boolean existing = request.getParameter("existing") == null ? false : true;
			    //do we need to set the context?
				if(contextAction != null && contextAction.equalsIgnoreCase("ReturnToManage")) 
				{
					Collection<Object>  filteredColl = filterImpExpLogColl(manageForm, request);
					if(filteredColl != null)
						impExpLogList = (ArrayList<Object> ) filteredColl;
					sortManageLog(manageForm, impExpLogList, existing, request);	
				}else {
					sortManageLog(manageForm, impExpLogList, existing, request);
					//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
					if(!existing) {
						PageManagementActionUtil.updateImpExpLogLinks(impExpLogList, request);
					}else {
						filterImpExpLogColl(manageForm, request);
						PageManagementActionUtil.updateImpExpLogLinks(impExpLogList, request);
					}
				}
				manageForm.getAttributeMap().put("queueCount", String.valueOf(impExpLogList.size()));
				manageForm.getAttributeMap().put("manageList",impExpLogList);
				request.setAttribute("manageList",impExpLogList);
		} catch (Exception e) {
			logger.error("Exception in ManageImportExportLib: " + e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
		} finally {
			request.setAttribute(DynamicPageConstants.PAGE_TITLE ,"Manage Templates: Import/Export Activity Log");
			
		}
		return (mapping.findForward("default"));

		
	}
	
	public ActionForward viewDetailedError(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		ManageImportExportLogForm manageForm = (ManageImportExportLogForm) form;
		try {
			manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			manageForm.setReturnToLink("<a href=\"/nbs/ManageImpExpLog.do?method=ManageImportExportLogLib\">Return To Activity log</a> ");
			String cnxt = request.getParameter("context");
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				manageForm.setSelection(manageForm.getOldDT());
				return (mapping.findForward("default"));
			}
				String EdxActivityLogId = request.getParameter("edxActivityLogUid");

				if(EdxActivityLogId != null && EdxActivityLogId.length() > 0 ) {
					ArrayList<?> testList = manageForm.getManageList();
				
					Iterator<?> iter = testList.iterator();
					while(iter.hasNext()) {
						EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
						if(dt.getEdxActivityLogUid().toString().equalsIgnoreCase(EdxActivityLogId)) {
							manageForm.getAttributeMap().put("templateName",dt.getDocName());
							manageForm.getAttributeMap().put("processedTime",dt.getRecordStatusTime());
							manageForm.getAttributeMap().put("source",dt.getSrcName());
							manageForm.setSelection(dt);
							manageForm.setOldDT(dt);
							break;
						}
					}
				}

		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in viewDetailedError: " + e.getMessage(), e);
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,"Manage Templates: View Activity Details");
		}
		return (mapping.findForward("viewDetailError"));
	}
	
	public ActionForward viewActivityLogDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		ManageImportExportLogForm manageForm = (ManageImportExportLogForm) form;
		
		String EdxActivityLogId="";
		String redirectReq = "";
		try {
			manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			manageForm.setReturnToLink("<a href=\"/nbs/ManageImpExpLog.do?method=ManageImportExportLogLib\">Return To Activity log</a> ");
			String cnxt = request.getParameter("context");
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				manageForm.setSelection(manageForm.getOldDT());
				return (mapping.findForward("default"));
			}
				 EdxActivityLogId = request.getParameter("edxActivityLogUid");

				if(EdxActivityLogId != null && EdxActivityLogId.length() > 0 ) {
					ArrayList<?> testList = manageForm.getManageList();
					if(testList.size()==0){
						testList = PageManagementActionUtil.getActivityLogCollection(request.getSession());
						Integer queueSize = properties.getQueueSize(NEDSSConstants.PAGE_MANAGEMENT_IMPORTEXPORT_LIBRARY_QUEUE_SIZE);
						//manageForm.clearAll();
						manageForm.getAttributeMap().put("queueSize", queueSize);
					}
					
					Iterator<?> iter = testList.iterator();
					while(iter.hasNext()) {
						EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
						if(dt.getEdxActivityLogUid().toString().equalsIgnoreCase(EdxActivityLogId)) {
							manageForm.getAttributeMap().put("templateName",dt.getDocName());
							manageForm.getAttributeMap().put("processedTime",dt.getRecordStatusTime());
							manageForm.getAttributeMap().put("source",dt.getSrcName());
						  if(dt.getRecordStatusCd().equals("Success")){	
								Iterator<Object> it = dt.getEDXActivityLogDTWithVocabDetails().iterator();
								ArrayList<Object> quesList = new ArrayList<Object>();
								ArrayList<Object> vocabList = new ArrayList<Object>();
								while(it.hasNext()){
									vocabList.add((EDXActivityDetailLogDT)it.next());
								}
								it = dt.getEDXActivityLogDTWithQuesDetails().iterator();							
								while(it.hasNext()){
									quesList.add((EDXActivityDetailLogDT)it.next());
								}
								 String sort = request.getParameter("sort");								 
								
								manageForm.setManageVocabList(vocabList);								
								
								manageForm.setManageQuesList(quesList);	
								
								manageForm.setManageErrorList( new ArrayList<Object>());
								manageForm.setOldDT(dt);
																
								filterConceptCd(manageForm,request, manageForm.getManageVocabList(),manageForm.getManageQuesList(),sort);							
													
								redirectReq = "viewActivityLogDetails";
						  }else{
							 ArrayList<Object> dtList = new ArrayList<Object>();
							  dtList.add(dt);
							  manageForm.setSelection(dt);
							  manageForm.setManageErrorList(dtList);
							  manageForm.setManageVocabList( new ArrayList<Object>());	
								manageForm.setManageQuesList( new ArrayList<Object>());	
							  redirectReq = "viewDetailError";
						  }
							
						}
					}
					 
				}else if(manageForm.getOldDT()!= null && ((EDXActivityLogDT)manageForm.getOldDT()).getEdxActivityLogUid() != null){
					 String sort = request.getParameter("sort");	
					redirectReq = "viewActivityLogDetails";
					filterConceptCd(manageForm,request, manageForm.getManageVocabList(),manageForm.getManageQuesList(),sort);
					
				}
				if(request.getParameter("printTab") != null){
				    String printTab = (String)request.getParameter("printTab");
				    request.setAttribute("printTab", printTab);
				    
				    
				}
				
				

		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in viewDetailedError: " + e.getMessage(), e);
			e.printStackTrace();
		} finally {
			request.setAttribute("manageVocabList", manageForm.getManageVocabList());
			request.setAttribute("manageQuesList", manageForm.getManageQuesList());
			request.setAttribute("manageErrorList", manageForm.getManageErrorList());
			request.setAttribute("edxActivityLogUid",HTMLEncoder.encodeHtml(EdxActivityLogId));
			if(request.getParameter("mode")!= null && request.getParameter("mode").toString().equalsIgnoreCase("print"))
				request.setAttribute("mode","print");
				
				
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,"Manage Templates: View Activity Details");
		}
		return (mapping.findForward(redirectReq));
	}
	/*
	 * To filter the Log Collection
	 */
	private Collection<Object>  filterImpExpLogColl(ManageImportExportLogForm manageForm, HttpServletRequest request) throws Exception {

		Collection<Object>  manageTemplist = new ArrayList<Object> ();

		String srchCriteriaProcessedTime = null;
		String srchCriteriaType = null;
		String srchCriteriaTemplateName = null;
		String srchCriteriaSource = null;
		String srchCriteriaStatus = null;
		String sortOrderParam = null;


		try {

			Map<Object, Object> searchCriteriaMap = manageForm.getSearchCriteriaArrayMap();
			// Get the existing SummaryVO collection in the form
			ArrayList<Object> impExpLogColl = (ArrayList<Object> ) manageForm.getActivityLogColl();

			// Filter by the investigator
			manageTemplist = getFilteredTemplateLib(impExpLogColl, searchCriteriaMap);

			String[] processedTime = (String[]) searchCriteriaMap.get("PROCESSEDTIME");
			String[] type = (String[]) searchCriteriaMap.get("TYPE");
			String[] templateName = (String[]) searchCriteriaMap.get("TEMPLATENAME");
			String[] source = (String[]) searchCriteriaMap.get("SOURCE");
			String[] status = (String[]) searchCriteriaMap.get("STATUS");

			Integer processedTimeCount = new Integer(processedTime == null ? 0 : processedTime.length);
			Integer typeCount = new Integer(type == null  ? 0 : type.length);
			Integer templateNameCount = new Integer(templateName == null  ? 0 : templateName.length);
			
			Integer sourceCount = new Integer(source == null  ? 0 : source.length);
			Integer statusCount = new Integer(status == null ? 0 : status.length);


			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if(processedTimeCount.equals((manageForm.getAttributeMap().get("processedTimeCount"))) &&
					(typeCount.equals(manageForm.getAttributeMap().get("typeCount"))) &&
					(templateNameCount.equals(manageForm.getAttributeMap().get("templateNameCount"))) &&
					(sourceCount.equals(manageForm.getAttributeMap().get("sourceCount"))) &&
					(statusCount.equals(manageForm.getAttributeMap().get("statusCount"))) )
			{

				String sortMethod = getSortMethod(request, manageForm,"");
				String direction = getSortDirection(request, manageForm,"");			
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<?,?> sColl =  manageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<?,?>) manageForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = PageManagementActionUtil.getSortCriteriaForImpExpLog(direction, sortMethod);
				}				
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
				return null;				
			}


			ArrayList<Object> processedTimeList = manageForm.getProcessedTime();
			ArrayList<Object> typeList = manageForm.getType();
			ArrayList<Object> templateNameList = manageForm.getTemplateName();
			ArrayList<Object> sourceList = manageForm.getSource();
			ArrayList<Object> statusList = manageForm.getStatus();


			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			String sortMethod = getSortMethod(request, manageForm,"");
			String direction = getSortDirection(request, manageForm,"");			
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object, Object> sColl =  manageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<Object, Object>) manageForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = PageManagementActionUtil.getSortCriteriaForTemplate(direction, sortMethod);
			}

			srchCriteriaProcessedTime = queueUtil.getSearchCriteria(processedTimeList, processedTime, NEDSSConstants.FILTERBYPROCESSEDTIME);

			srchCriteriaType = queueUtil.getSearchCriteria(typeList, type, NEDSSConstants.FILTERBYTYPE);
			srchCriteriaTemplateName = queueUtil.getSearchCriteria(templateNameList, templateName, NEDSSConstants.FILTERBYTEMPLATENAME);
			srchCriteriaSource = queueUtil.getSearchCriteria(sourceList, source, NEDSSConstants.FILTERBYSOURCE);
			srchCriteriaStatus = queueUtil.getSearchCriteria(statusList, status, NEDSSConstants.FILTERBYRECORDSTATUS);


			//set the error message to the form
			if(sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if(srchCriteriaProcessedTime != null)
				searchCriteriaColl.put("INV147", srchCriteriaProcessedTime);
			if(srchCriteriaType != null)
				searchCriteriaColl.put("INV100", srchCriteriaType);
			if(srchCriteriaTemplateName != null)
				searchCriteriaColl.put("INV150", srchCriteriaTemplateName);
			if(srchCriteriaSource != null)
				searchCriteriaColl.put("INV163", srchCriteriaSource);
			if(srchCriteriaStatus != null)
				searchCriteriaColl.put("NOT118", srchCriteriaStatus);
			


			manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering the ImportExportLog  by  : "+ e.toString(), e);
			throw new ServletException("Error  in filterTemplateLib method: " +e.getMessage());
		} 
		return manageTemplist;
	}
	private String getSortMethod(HttpServletRequest request, ManageImportExportLogForm manageForm, String sortType) {
		if (PaginationUtil._dtagAccessed(request)) {
			if(sortType != null && sortType.equalsIgnoreCase("Ques"))
				return request.getParameter((new ParamEncoder("parent1")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
			else
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
		} else{
			return manageForm.getAttributeMap().get("methodName") == null ? null : (String) manageForm.getAttributeMap().get("methodName");
		}

	}
	
	private String getSortDirection(HttpServletRequest request, ManageImportExportLogForm manageForm, String sortType) {
		if (PaginationUtil._dtagAccessed(request)) {
			if(sortType != null && sortType.equalsIgnoreCase("Ques"))
				return request.getParameter((new ParamEncoder("parent1")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
			else
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		} else{
			return manageForm.getAttributeMap().get("sortOrder") == null ? "1": (String) manageForm.getAttributeMap().get("sortOrder");
		}

	}
	
	public Collection<Object>  getFilteredTemplateLib(Collection<Object>  activityLogColl,
			Map<Object, Object> searchCriteriaMap) {

		String[] processedTime = (String[]) searchCriteriaMap.get("PROCESSEDTIME");
		String[] type = (String[]) searchCriteriaMap.get("TYPE");
		String[] templateName = (String[]) searchCriteriaMap.get("TEMPLATENAME");		
		String[] source = (String[]) searchCriteriaMap.get("SOURCE");
		String[] status = (String[]) searchCriteriaMap.get("STATUS");


		Map<Object, Object> processedTimeMap = new HashMap<Object,Object>();
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		Map<Object, Object> templateNameMap = new HashMap<Object,Object>();
		Map<Object, Object> sourceMap = new HashMap<Object,Object>();
		Map<Object, Object> statusMap = new HashMap<Object,Object>();

		if (processedTime != null && processedTime.length > 0)
			processedTimeMap = queueUtil.getMapFromStringArray(processedTime);
		if (type != null && type.length > 0)
			typeMap = queueUtil.getMapFromStringArray(type);
		if (type != null && type.length > 0)
			templateNameMap = queueUtil.getMapFromStringArray(templateName);
		if (source != null && source.length > 0)
			sourceMap = queueUtil.getMapFromStringArray(source);
		if (status != null && status.length >0)
			statusMap = queueUtil.getMapFromStringArray(status);


		if(processedTimeMap != null && processedTimeMap.size()>0)
			activityLogColl = filterProcessedTime(
					activityLogColl, processedTimeMap);
		if (typeMap != null && typeMap.size()>0)
			activityLogColl = filterType(
					activityLogColl, typeMap);
		if (templateNameMap != null && templateNameMap.size()>0)
			activityLogColl = filterTemplateName(
					activityLogColl, templateNameMap);
		if (sourceMap != null && sourceMap.size()>0)
			activityLogColl = filterSource(
					activityLogColl, sourceMap);		
		if (statusMap != null && statusMap.size()>0)
			activityLogColl = filterStatus(
					activityLogColl, statusMap);

		return activityLogColl;

	}
	

	public Collection<Object>  filterProcessedTime(Collection<Object> activityLogColl, Map<Object, Object> processedTimeMap) {
		Map<Object, Object>  newTempMap = new HashMap<Object,Object>();
		String strDateKey = null;
		if (activityLogColl != null) {
			Iterator<Object>  iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if (dt.getRecordStatusTime()!= null && processedTimeMap != null
						&& (processedTimeMap.size()>0 )) {
					Collection<Object>  dateSet = processedTimeMap.keySet();
					if(dateSet != null){
						Iterator<Object>  iSet = dateSet.iterator();
						while (iSet.hasNext()){
							strDateKey = (String)iSet.next();
							if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY))){
								if(queueUtil.isDateinRange(dt.getRecordStatusTime(),strDateKey)){
									newTempMap.put(dt.getEdxActivityLogUid().toString(), dt);
								}	

							}  
						}
					}
				}

				if(dt.getRecordStatusTime() == null || dt.getRecordStatusTime().equals("")){
					if(processedTimeMap != null && processedTimeMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)){
						newTempMap.put(dt.getEdxActivityLogUid().toString(), dt);
					}
				}

			}
		} 	


		return convertTempMaptoColl(newTempMap);

	}
	
	public Collection<Object>  filterType(
			Collection<Object>  activityLogColl, Map<Object,Object> typeMap) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if (dt.getImpExpIndCd() != null	&& typeMap != null && typeMap.containsKey(dt.getImpExpIndCd())) {
					newTypeColl.add(dt);
				}
				if(dt.getImpExpIndCd() == null || dt.getImpExpIndCd().equals("")){
					if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newTypeColl.add(dt);
					}
				}

			}

		}
		return newTypeColl;

	}
	
	public Collection<Object>  filterTemplateName(
			Collection<Object>  activityLogColl, Map<Object,Object> templateNameMap) {
		Collection<Object>  newtemplateNameColl = new ArrayList<Object> ();
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if (dt.getDocName() != null	&& templateNameMap != null && templateNameMap.containsKey(dt.getDocName())) {
					newtemplateNameColl.add(dt);
				}
				if(dt.getDocName() == null || dt.getDocName().equals("")){
					if(templateNameMap != null && templateNameMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newtemplateNameColl.add(dt);
					}
				}

			}

		}
		return newtemplateNameColl;

	}
	
	public Collection<Object>  filterSource(
			Collection<Object>  activityLogColl, Map<Object,Object> statusMap) {
		Collection<Object>  newStatusColl = new ArrayList<Object> ();
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if (dt.getSrcName() != null	&& statusMap != null && statusMap.containsKey(dt.getSrcName())) {
					newStatusColl.add(dt);
				}
				if(dt.getSrcName() == null || dt.getSrcName().equals("")){
					if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newStatusColl.add(dt);
					}
				}

			}

		}
		return newStatusColl;

	}
	
	public Collection<Object>  filterStatus(
			Collection<Object>  activityLogColl, Map<Object,Object> statusMap) {
		Collection<Object>  newStatusColl = new ArrayList<Object> ();
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if (dt.getRecordStatusCd() != null	&& statusMap != null && statusMap.containsKey(dt.getRecordStatusCd())) {
					newStatusColl.add(dt);
				}
				if(dt.getRecordStatusCd() == null || dt.getRecordStatusCd().equals("")){
					if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newStatusColl.add(dt);
					}
				}

			}

		}
		return newStatusColl;

	}
	public ActionForward filterImpExpLogSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ManageImportExportLogForm manageForm  = (ManageImportExportLogForm) form;
		Collection<Object>  impExpist = filterImpExpLogColl(manageForm, request);
		if(impExpist != null){
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
			//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
		}else{
			impExpist = (ArrayList<Object> ) manageForm.getActivityLogColl();
		}
		manageForm.getAttributeMap().put("manageList",impExpist);
		manageForm.getAttributeMap().put("queueList",impExpist);
		request.setAttribute("queueList", impExpist);
		sortManageLog(manageForm, impExpist, true, request);
		request.setAttribute("manageList", impExpist);
		//request.setAttribute("queueCount", String.valueOf(recFacilityList.size()));
		manageForm.getAttributeMap().put("queueCount", String.valueOf(impExpist.size()));
		request.setAttribute("PageTitle","Manage Templates: Import/Export Actiity Log");
		manageForm.getAttributeMap().put("PageNumber", "1");

		return PaginationUtil.paginate(manageForm, request, "default",mapping);

	} 

	
	private void sortManageLog(ManageImportExportLogForm manageForm, Collection<Object>  impExpLogList, boolean existing, HttpServletRequest request) throws Exception{

		// Retrieve sort-order and sort-direction from displaytag params
		String sortMethod = getSortMethod(request, manageForm,"");
		String direction = getSortDirection(request, manageForm,"");

		boolean invDirectionFlag = true;
		if (direction != null && direction.equals("2"))
			invDirectionFlag = false;


		NedssUtils util = new NedssUtils();

		//Read from properties file to determine default sort order
		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
			sortMethod = "getRecordStatusTime";
			invDirectionFlag = false;
		}

	if (sortMethod != null && impExpLogList != null && impExpLogList.size() > 0) {
			updateListBeforeSort(impExpLogList);
			util.sortObjectByColumn(sortMethod,(Collection<Object>) impExpLogList, invDirectionFlag);

			updateListAfterSort(impExpLogList);

		}

		if(!existing) {
			//Finally put sort criteria in form
			String sortOrderParam = PageManagementActionUtil.getSortCriteriaForImpExpLog(invDirectionFlag == true ? "1" : "2", sortMethod);
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			searchCriteriaColl.put("sortSt", sortOrderParam);
			manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}
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
	
	
	public void saveAsTextFile(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		ManageImportExportLogForm manageForm = (ManageImportExportLogForm) form;	
		//ActionForward forwardNew = new ActionForward();
		
		EDXActivityLogDT dt = new EDXActivityLogDT();
		try {
			Iterator<?> it = manageForm.getManageErrorList().iterator();
			while(it.hasNext()){
				 dt = (EDXActivityLogDT)it.next();
			}
			//String ret = util.handleExportImportofXML(session,templateUid);
			
			
		    int length   = 0;
		        response.setContentType("text/plain");
		        response.setHeader("Content-disposition", "attachment;filename=output.txt"); 
		        ServletOutputStream op       = response.getOutputStream();		      
		        byte[] bbuf = new byte[1000];
		       // InputStream is = new ByteArrayInputStream(text.getBytes("UTF-8"));        
		      
		       // InputStream is = new ByteArrayInputStream(obj.xmlText().getBytes("UTF-8")); 
		        InputStream is = new ByteArrayInputStream(dt.getException().getBytes());
		        DataInputStream in = new DataInputStream(is);
		        while ((in != null) && ((length = in.read(bbuf)) != -1))
		        {
		            op.write(bbuf,0,length);
		        }
		        in.close();
		        op.flush();
		        op.close();

		}
		catch (Exception e) {			
			request.setAttribute("Error in ManageImportExportLogAction. saveAsTextFile", e.getMessage());
			e.printStackTrace();
			logger.error("Exception in saveAsTextFile: " + e.getMessage(), e);
		}		
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		request.setAttribute(DynamicPageConstants.PAGE_TITLE ,"Manage Templates: save As Text File");

	}
	
	private void updateListBeforeSort(Collection<Object>  impExpLogList) {
		Iterator<Object> iter = impExpLogList.iterator();
		while (iter.hasNext()) {
			EDXActivityLogDT dt = (EDXActivityLogDT)  iter.next();

			if (dt.getEdxActivityLogUid()== null || (dt.getEdxActivityLogUid().toString() != null && dt.getEdxActivityLogUid().toString().equals(""))) {
				dt.setEdxActivityLogUid(null);
			}

			if (dt.getExceptionShort()== null || dt.getExceptionShort() != null  && dt.getExceptionShort().equals("")) {
				dt.setExceptionShort("ZZZZZ");
			}
			 
		}

	}

	private void updateListAfterSort(Collection<Object>  notifiSummaryVOs) {
		Iterator<Object> iter = notifiSummaryVOs.iterator();
		while (iter.hasNext()) {
			EDXActivityLogDT dt = (EDXActivityLogDT)  iter.next();
			if (dt.getEdxActivityLogUid()!= null && dt.getEdxActivityLogUid().toString().equals("ZZZZZ")) {
				dt.setEdxActivityLogUid(null);
			}
			if (dt.getExceptionShort() != null && dt.getExceptionShort().equals("ZZZZZ")) {
				dt.setExceptionShort("");
			}

		}

	}
	
	private void filterConceptCd(ManageImportExportLogForm manageForm, HttpServletRequest request, ArrayList<?> theLogVocabDtCollection,ArrayList<?> theLogQuesDtCollection, String sortType)
	{
		boolean existing = request.getParameter("existing") == null ? false : true;
		if(existing){
			if(sortType == null || sortType.equalsIgnoreCase("Vocab"))
			sortActLog(manageForm, theLogVocabDtCollection, existing, request,sortType);
			if(sortType != null && sortType.equalsIgnoreCase("Ques"))
			sortActLog(manageForm, theLogQuesDtCollection, existing, request,sortType);
		}
		else{
			sortActLog(manageForm, theLogVocabDtCollection, existing, request,"Vocab");
			sortActLog(manageForm, theLogQuesDtCollection, existing, request,"Ques");
		}
		
	}
	
	private void sortActLog(ManageImportExportLogForm manageForm, Collection<?>  theLogDtCollection, boolean existing, HttpServletRequest request, String sortType) {

		// Retrieve sort-order and sort-direction from displaytag params
		String sortMethod = getSortMethod(request, manageForm, sortType);
		String direction = getSortDirection(request, manageForm, sortType);
		String logType="";

		boolean directionFlag = true;
		if (direction != null && direction.equals("2"))
			directionFlag = false;

       Iterator<?> it = theLogDtCollection.iterator();
       while(it.hasNext()){
    	   EDXActivityDetailLogDT dt = (EDXActivityDetailLogDT)it.next();
    	   logType =  dt.getLogType();
    	   break;
       }
		NedssUtils util = new NedssUtils();
		
		//Read from properties file to determine default sort order
		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))|| (!existing)) {
			if(logType.equalsIgnoreCase("question"))
			sortMethod = "getRecordId"; 
			else
			sortMethod = "getRecordId"; 
			
			directionFlag = true;
		}
		
		if (sortMethod != null && theLogDtCollection != null && theLogDtCollection.size() > 0) {
			util.sortObjectByColumn(sortMethod,(Collection<Object>) theLogDtCollection, directionFlag);
			
		}
		
		//Finally put sort criteria in form
		String sortOrderParam = getSortCriteria(directionFlag == true ? "1" : "2", sortMethod,logType);
		manageForm.getAttributeMap().put("sortSt", sortOrderParam);
		
		}
	
	
	
public static String getSortCriteria(String sortOrder, String methodName, String sortType){
		
		String sortOrdrStr = null;
		if(sortType.equalsIgnoreCase("question")){
		if(methodName != null) {
			if(methodName.equals("getRecordId"))
				sortOrdrStr = "Question ID";
			else if(methodName.equals("getRecordName"))
				sortOrdrStr = "Question Unique Name";
			else if(methodName.equals("getRecordType"))
				sortOrdrStr = "Question Type";
			else if(methodName.equals("getComment"))
				sortOrdrStr = "New/Exisiting Question";
			
		} else {
			sortOrdrStr = "Question ID";
		}
		}else{
			if(methodName != null) {
				if(methodName.equals("getRecordId"))
					sortOrdrStr = "ID";
				else if(methodName.equals("getRecordName"))
					sortOrdrStr = "Unique Name";
				else if(methodName.equals("getRecordType"))
					sortOrdrStr = "Type";
				else if(methodName.equals("getComment"))
					sortOrdrStr = "New/Exisiting";
				
			} else {
				sortOrdrStr = "ID";
			}			
			
		}
		
		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+"| in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+"| in descending order ";

		return sortOrdrStr;
			
	}
	

}
