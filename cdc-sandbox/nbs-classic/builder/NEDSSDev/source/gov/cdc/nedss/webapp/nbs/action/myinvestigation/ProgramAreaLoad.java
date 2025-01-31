package gov.cdc.nedss.webapp.nbs.action.myinvestigation;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
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

import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.homepage.charts.ChartConstants;
import gov.cdc.nedss.webapp.nbs.action.observation.review.util.ObservationReviewQueueUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.myinvestigation.ProgramAreaForm;

/**
 * Title: ProgramAreaLoad is an action class Description: This is actions class
 * to Provides next action Copyright: Copyright (c) 2001 Company: CSC
 * 
 * @author Nedss Team
 * @version 1.0




 */

public class ProgramAreaLoad extends DispatchAction {

	static final LogUtils logger = new LogUtils(ProgramAreaLoad.class.getName());
	protected ObservationSummaryVO obsSumVO = null;
	ProgramAreaUtil paUtil = new ProgramAreaUtil();
	PropertyUtil properties = PropertyUtil.getInstance();
	QueueUtil queueUtil = new QueueUtil();

	/**
	 * loadQueue method used for initial Load and for sorting of the Open Investigation Queue.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return org.apache.struts.action.ActionForward
	 * @throws IOException
	 * @throws ServletException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward loadQueue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ProgramAreaForm paForm = (ProgramAreaForm) form;
		boolean forceEJBcall = false;
		// Reset Pagination first time
		boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
		if (initLoad && !PaginationUtil._dtagAccessed(request)) {
			paForm.clearAll();
			forceEJBcall = true;
			// get the number of records to be displayed per page
			Integer queueSize = properties.getQueueSize(NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS_QUEUE_SIZE);
			paForm.getAttributeMap().put("queueSize", queueSize);
		}
	
		HttpSession session = request.getSession();
		request.getSession().removeAttribute("SupplementalInfo");
		// get page context
		String contextAction = request.getParameter("ContextAction");
		ArrayList<Object> investigationSummaryVOs = new ArrayList<Object> ();
		ArrayList<Object> investigationSummaryVOsFull = new ArrayList<Object> ();
		if (contextAction != null) {
			if(contextAction.equals("GlobalInvestigations")) {
				DSQueueObject dSQueueObject = new DSQueueObject();
				dSQueueObject.setDSQueueType(NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS);
				NBSContext.store(session, "DSQueueObject", dSQueueObject);
			} else if(contextAction.equals("ReturnToMyInvestigations")) {
				forceEJBcall = true;
			}
			try {
				investigationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationList);
				investigationSummaryVOsFull = (ArrayList<Object> ) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationListFull);
			}catch(Exception ex) {
				logger.info("No DSInvestigationList");
			}
			
			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS116", contextAction);
			String sCurrTask = NBSContext.getCurrentTask(session);
			request.setAttribute("viewHref", sCurrTask + ".do?ContextAction=" + tm.get("InvestigationID"));
			// Add a new link for ViewFile
			request.setAttribute("ViewFileHref", sCurrTask + ".do?ContextAction=" + tm.get("ViewFile"));
			NBSContext.store(session, NBSConstantUtil.DSInvestigationList, investigationSummaryVOs);
			NBSContext.store(session, NBSConstantUtil.DSInvestigationListFull, investigationSummaryVOsFull);
		}

		try {
			
			if (forceEJBcall) {
				paForm.setMsgBlock("");
				MainSessionHolder mainSessionHolder = new MainSessionHolder();
				MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(session);
				String sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
				String sMethod = "getMyInvestigations";
				ArrayList<Object> arr = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, null);
				investigationSummaryVOs = (ArrayList<Object> ) arr.get(0);
				NBSContext.store(session, NBSConstantUtil.DSInvestigationList, investigationSummaryVOs);
				NBSContext.store(session, NBSConstantUtil.DSInvestigationListFull, investigationSummaryVOs);
				//Set InvSummaryVOColl as property of Form first time since this list is used for distinct dropdown values in the form
				paForm.initializeDropDowns(investigationSummaryVOs);
				paForm.getAttributeMap().put("InvestigatorsCount",new Integer(paForm.getInvestigators().size()));
				paForm.getAttributeMap().put("JurisdictionsCount",new Integer(paForm.getJurisdictions().size()));
				paForm.getAttributeMap().put("ConditionsCount",new Integer(paForm.getConditions().size()));
				paForm.getAttributeMap().put("caseStatusCount",new Integer(paForm.getCaseStatuses().size()));
				paForm.getAttributeMap().put("dateFilterListCount",new Integer(paForm.getStartDateDropDowns().size()));
				paForm.getAttributeMap().put("notificationsCount",new Integer(paForm.getNotifications().size()));
			} else {
				investigationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationList);
			}

			// Make PatientName and Condition as Hyperlinks and put the list in the request for new lists
			boolean existing = request.getParameter("existing") == null ? false : true;
			if(contextAction != null && contextAction.equalsIgnoreCase("ReturnToMyInvestigations")) {
				updatePatPhcLinks(investigationSummaryVOs, request);
				//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
				Collection<Object>  filteredColl = filterInvs(paForm, request);
				if(filteredColl != null)
					investigationSummaryVOs = (ArrayList<Object> ) filteredColl;
				sortInvs(paForm, investigationSummaryVOs, true, request);
			} else {
				sortInvs(paForm, investigationSummaryVOs, existing, request);
				
				if(!existing) {
					updatePatPhcLinks(investigationSummaryVOs, request);
				} else
					filterInvs(paForm, request);
			}
			
			investigationSummaryVOs = (ArrayList<Object> )_handleFilterForCharts(paForm, investigationSummaryVOs, request);
			
			//To make sure SelectAll is checked, see if no criteria is applied
			if(paForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));			
			
			request.setAttribute("investigationList", investigationSummaryVOs);
			request.setAttribute("queueCount", String.valueOf(investigationSummaryVOs.size()));
			// Put the Inv List(with hyperlinks) in attributeMap for reuse
			NBSContext.store(session, NBSConstantUtil.DSInvestigationList, investigationSummaryVOs);
			//paForm.getAttributeMap().put("investigationList",investigationSummaryVOs);
			request.setAttribute("PageTitle","Open Investigations Queue");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in ProgramAreaLoad in getting investigationSummaryVOs from EJB");
			return mapping.findForward(NEDSSConstants.ERROR_PAGE);
		}
		return PaginationUtil.paginate(paForm, request, "OpenInvestigations",mapping);
	}

	/**
	 * filterInvestigatonSubmit used when Filters applied on the Open Investigation Queue
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return org.apache.struts.action.ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward filterInvestigatonSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		ProgramAreaForm prgmAreaForm = (ProgramAreaForm) form;
		Collection<Object>  investigationSummaryVOs = filterInvs(prgmAreaForm, request);
		if(investigationSummaryVOs != null){
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
		//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
		}else{
			try {
			investigationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSInvestigationListFull);
			}
			catch(Exception ex) {
				logger.debug(NBSConstantUtil.DSInvestigationListFull +"is null in Program Area Load");
				investigationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSInvestigationList);
			}
		}
		//NBSContext.store(request.getSession(true), NBSConstantUtil.DSInvestigationList, investigationSummaryVOs);
		//prgmAreaForm.getAttributeMap().put("DSInvestigationList",investigationSummaryVOs);
		sortInvs(prgmAreaForm, investigationSummaryVOs, true, request);
		request.setAttribute("investigationList", investigationSummaryVOs);
		request.setAttribute("queueCount", String.valueOf(investigationSummaryVOs.size()));
		request.setAttribute("PageTitle","Open Investigations Queue");
		prgmAreaForm.getAttributeMap().put("PageNumber", "1");

		return PaginationUtil.paginate(prgmAreaForm, request, "OpenInvestigations",mapping);			
	}
	

	/**
	 * filterInvs in Open Investigation Queue applies individual filter on the SummaryCollection  and returns
	 * @param prgmAreaForm
	 * @param request
	 * @return java.util.Collection
	 */
	@SuppressWarnings("unchecked")
	private Collection<Object>  filterInvs(ProgramAreaForm prgmAreaForm, HttpServletRequest request) {
		
		Collection<Object>  investigationSummaryVOs = new ArrayList<Object> ();
		
		String srchCriteriaInv = null;
		String srchCriteriaJuris = null;
		String srchCriteriaCond = null;
		String srchCriteriaStatus = null;
		String sortOrderParam = null;
		String srchCriteriaDate = null;
		String srchCriteriaNotif = null;
		
		try {
			
			Map<Object,Object> searchCriteriaMap = prgmAreaForm.getSearchCriteriaArrayMap();
			// Get the existing SummaryVO collection in the form
			ArrayList<Object> invSummaryVOs = null;
			try {
				if("true".equalsIgnoreCase( request.getParameter("fromAssign"))) {
					//String contextAction = request.getParameter("ContextAction");
					TreeMap<Object,Object> tm = NBSContext.getPageContext(request.getSession(true), "PS116", "GlobalInvestigations");
					String sCurrTask = NBSContext.getCurrentTask(request.getSession(true));
					request.setAttribute("viewHref", sCurrTask + ".do?ContextAction=" + tm.get("InvestigationID"));
					// Add a new link for ViewFile
					request.setAttribute("ViewFileHref", sCurrTask + ".do?ContextAction=" + tm.get("ViewFile"));
					
					MainSessionHolder mainSessionHolder = new MainSessionHolder();
					MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(request.getSession(true));
					String sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
					String sMethod = "getMyInvestigations";
					ArrayList<Object> arr = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, null);
					invSummaryVOs  = (ArrayList<Object> ) arr.get(0);
					
					
					
					prgmAreaForm.initializeDropDowns(invSummaryVOs);
					prgmAreaForm.getAttributeMap().put("InvestigatorsCount",new Integer(prgmAreaForm.getInvestigators().size()));
					prgmAreaForm.getAttributeMap().put("JurisdictionsCount",new Integer(prgmAreaForm.getJurisdictions().size()));
					prgmAreaForm.getAttributeMap().put("ConditionsCount",new Integer(prgmAreaForm.getConditions().size()));
					prgmAreaForm.getAttributeMap().put("caseStatusCount",new Integer(prgmAreaForm.getCaseStatuses().size()));
					prgmAreaForm.getAttributeMap().put("dateFilterListCount",new Integer(prgmAreaForm.getStartDateDropDowns().size()));
					prgmAreaForm.getAttributeMap().put("notificationsCount",new Integer(prgmAreaForm.getNotifications().size()));
					
					updatePatPhcLinks(invSummaryVOs, request);
					invSummaryVOs = (ArrayList<Object> )_handleFilterForCharts(prgmAreaForm, invSummaryVOs, request);
								
					NBSContext.store(request.getSession(true), NBSConstantUtil.DSInvestigationListFull, invSummaryVOs);
					
					
					
					
				}
				
				
				
				
				
				
				invSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSInvestigationListFull);
				
			
			}
			catch(Exception ex) {
				logger.debug(NBSConstantUtil.DSInvestigationListFull +"is null in Program Area Load filterInvs");
				invSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSInvestigationList);
			}
			// Filter by the investigator
			investigationSummaryVOs = paUtil.getFilteredInvestigation(invSummaryVOs, searchCriteriaMap);
			NBSContext.store(request.getSession(true),NBSConstantUtil.DSInvestigationList,  investigationSummaryVOs);
			String[] inv = (String[]) searchCriteriaMap.get("INVESTIGATOR");
			String[] juris = (String[]) searchCriteriaMap.get("JURISDICTION");
			String[] cond = (String[]) searchCriteriaMap.get("CONDITION");
			String[] status = (String[]) searchCriteriaMap.get("CASESTATUS");
			String[] startDate = (String[]) searchCriteriaMap.get("STARTDATE");
			String[] notif = (String[]) searchCriteriaMap.get("NOTIFICATION");
			
			String filterPatient = null;
			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				filterPatient = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
				request.setAttribute("PATIENT", filterPatient);
			}

			String filterInvestigationId = null;
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				filterInvestigationId = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
				request.setAttribute("INVESTIGATIONID", filterInvestigationId);
			}

			
			Integer invCount = new Integer(inv == null ? 0 : inv.length);
			Integer jurisCount = new Integer(juris == null  ? 0 : juris.length);
			Integer condCount = new Integer(cond == null  ? 0 : cond.length);
			Integer statusCount = new Integer(status == null  ? 0 : status.length);
			Integer startDateCount = new Integer(startDate == null ? 0 : startDate.length);
			Integer notifCount = new Integer(notif == null ? 0 : notif.length);
			
			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if(invCount.equals((prgmAreaForm.getAttributeMap().get("InvestigatorsCount"))) &&
					(jurisCount.equals(prgmAreaForm.getAttributeMap().get("JurisdictionsCount"))) && 
					(condCount.equals(prgmAreaForm.getAttributeMap().get("ConditionsCount"))) &&
					(statusCount.equals(prgmAreaForm.getAttributeMap().get("caseStatusCount"))) &&
					(startDateCount.equals(prgmAreaForm.getAttributeMap().get("dateFilterListCount"))) &&
					(notifCount.equals(prgmAreaForm.getAttributeMap().get("notificationsCount"))) &&
					filterPatient == null &&
					filterInvestigationId ==null) {
				
				String sortMethod = getSortMethod(request, prgmAreaForm);
				String direction = getSortDirection(request, prgmAreaForm);			
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<Object,Object> sColl =  prgmAreaForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) prgmAreaForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = paUtil.getSortCriteria(direction, sortMethod);
				}				
				Map<Object,Object> searchCriteriaColl = new TreeMap<Object,Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				prgmAreaForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
				return null;				
			}
						
			
			ArrayList<Object> invList = prgmAreaForm.getInvestigators();
			ArrayList<Object> jurisList = prgmAreaForm.getJurisdictions();
			ArrayList<Object> condList = prgmAreaForm.getConditions();
			ArrayList<Object> statusList = prgmAreaForm.getCaseStatuses();
			ArrayList<Object> dateList = prgmAreaForm.getStartDateDropDowns();
			ArrayList<Object> notifList = prgmAreaForm.getNotifications();
			
			Map<Object,Object> searchCriteriaColl = new TreeMap<Object,Object>();
			String sortMethod = getSortMethod(request, prgmAreaForm);
			String direction = getSortDirection(request, prgmAreaForm);			
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object,Object> sColl =  prgmAreaForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) prgmAreaForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = paUtil.getSortCriteria(direction, sortMethod);
			}
			
			srchCriteriaInv = queueUtil.getSearchCriteria(invList, inv, NEDSSConstants.FILTERBYINVESTIGATOR);
			srchCriteriaJuris = queueUtil.getSearchCriteria(jurisList, juris, NEDSSConstants.FILTERBYJURISDICTION);
			srchCriteriaCond = queueUtil.getSearchCriteria(condList, cond, NEDSSConstants.FILTERBYCONDITION);
			srchCriteriaStatus = queueUtil.getSearchCriteria(statusList, status, NEDSSConstants.FILTERBYSTATUS);
			srchCriteriaDate = queueUtil.getSearchCriteria(dateList, startDate, NEDSSConstants.FILTERBYDATE);
			srchCriteriaNotif = queueUtil.getSearchCriteria(notifList, notif, NEDSSConstants.FILTERBYNOTIF);
			/*
			String[] inv1 = null;
			String localId = null;
			String selectedInvestigator = prgmAreaForm.getInvestigatorSelected();
			StringTokenizer st1 = new StringTokenizer(selectedInvestigator, ",");
			StringTokenizer st2 =new StringTokenizer(st1.nextToken(), " ");
			String firstName = "";
			String lastName = "";
			
			while(st2.hasMoreTokens()) {
				firstName = st2.nextToken();
				lastName = st2.nextToken();
			}
			ArrayList<Object> investigators=prgmAreaForm.getInvestigators();
			for (int i = 0; i < investigators.size(); i++)
            {
				DropDownCodeDT dt=(DropDownCodeDT) investigators.get(i);
				if(dt.getValue().equalsIgnoreCase(selectedInvestigator)) {
					localId=dt.getKey();
				}
            }
			 
			//String uidString = prgmAreaForm.getAttributeMap().get( "INV207Uid").toString();
			//String localId = uidString.split("|")[0];
			
			
			if(inv!=null && inv.length>0){
				boolean isLocalIdAlreadyThere = false;
				for(String invLocalId: inv) {
					if(localId!=null && localId.equalsIgnoreCase(invLocalId)) {
						isLocalIdAlreadyThere=true;
					}
				}
				if(!isLocalIdAlreadyThere) {
					inv1 = new String[inv.length+1];
					System.arraycopy(inv, 0, inv1, 0, inv.length);
					inv1[inv.length] = localId;
					searchCriteriaMap.put("INVESTIGATOR",inv1);
					
				}
				
			}
			if(srchCriteriaCond!=null && inv1!=null) {
				srchCriteriaInv = queueUtil.getSearchCriteria(invList, inv1, NEDSSConstants.FILTERBYINVESTIGATOR);
			}
			
			*/
			

			//set the error message to the form
			if(sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if(srchCriteriaDate != null)
				searchCriteriaColl.put("INV147", srchCriteriaDate);
			if(srchCriteriaInv != null)
				searchCriteriaColl.put("INV100", srchCriteriaInv);
			if(srchCriteriaJuris != null)
				searchCriteriaColl.put("INV107", srchCriteriaJuris);
			if(srchCriteriaCond != null)
				searchCriteriaColl.put("INV169", srchCriteriaCond);
			if(srchCriteriaStatus != null)
				searchCriteriaColl.put("INV163", srchCriteriaStatus);
			if(srchCriteriaNotif != null)
				searchCriteriaColl.put("NOT118", srchCriteriaNotif);
			if(filterPatient != null){
				searchCriteriaColl.put("PATIENT", filterPatient);
				request.setAttribute("PATIENT", filterPatient);
			}	
				if(filterInvestigationId != null){
					searchCriteriaColl.put("INVESTIGATIONID", filterInvestigationId);
					request.setAttribute("INVESTIGATIONID", filterInvestigationId);
	
			}
			prgmAreaForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering the investigation by Investigator: "+ e.toString());
			
		} 
		return investigationSummaryVOs;
	}

	/**
	 * sortInvs in Open Investigation Queue applies sort, direction on the SummaryCollection  and returns
	 * @param paForm
	 * @param investigationSummaryVOs
	 * @param existing
	 * @param request
	 */
	private void sortInvs(ProgramAreaForm paForm, Collection<Object>  investigationSummaryVOs, boolean existing, HttpServletRequest request) {

		// Retrieve sort-order and sort-direction from displaytag params
		String sortMethod = getSortMethod(request, paForm);
		String direction = getSortDirection(request, paForm);

		boolean invDirectionFlag = true;
		if (direction != null && direction.equals("2"))
			invDirectionFlag = false;

		//Read from properties file to determine default sort order
		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				sortMethod = "getActivityFromTime";
				invDirectionFlag = !properties.getMyProgramAreasQueueSortbyNewestInvStartdate();
		}
		
		NedssUtils util = new NedssUtils();
		if (sortMethod != null && investigationSummaryVOs != null
				&& investigationSummaryVOs.size() > 0) {
			updateInvestigationSummaryVObeforeSort(investigationSummaryVOs);
			util.sortObjectByColumnGeneric(sortMethod,
					(Collection<Object>) investigationSummaryVOs, invDirectionFlag);
			//after sorting if sortMethod is getActivityFromTime replace fictious time with nulls
			_updateSummaryVosForDate(investigationSummaryVOs);
			updateInvestigationSummaryVOAfterSort(investigationSummaryVOs);
			
		}
		if(!existing) {
			//Finally put sort criteria in form
			String sortOrderParam = paUtil.getSortCriteria(invDirectionFlag == true ? "1" : "2", sortMethod);
			Map<Object,Object> searchCriteriaColl = new TreeMap<Object,Object>();
			searchCriteriaColl.put("sortSt", sortOrderParam);
			paForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}

	}
	
	private String getSortMethod(HttpServletRequest request, ProgramAreaForm paForm) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
		} else{
			return paForm.getAttributeMap().get("methodName") == null ? null : (String) paForm.getAttributeMap().get("methodName");
		}
		
	}

	private String getSortDirection(HttpServletRequest request, ProgramAreaForm paForm) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		} else{
			return paForm.getAttributeMap().get("sortOrder") == null ? "1": (String) paForm.getAttributeMap().get("sortOrder");
		}
		
	}
	
	
	private void updatePatPhcLinks(ArrayList<Object>  investigationList,
			HttpServletRequest request) {
		ObservationReviewQueueUtil util = new ObservationReviewQueueUtil();
    	 
		if (investigationList != null && investigationList.size() != 0) {
			for (int i = 0; i < investigationList.size(); i++) {
				InvestigationSummaryVO inv = (InvestigationSummaryVO) investigationList.get(i);
				

				String invFullNm = getInvestigatorFullName(inv);
				inv.setInvestigatorFullName(invFullNm);
				String patFullName = getPatientFullName(inv);
				inv.setPatientFullName(patFullName);
			 
			
				String viewFileHref = request.getAttribute("ViewFileHref") == null ? "" : (String) request.getAttribute("ViewFileHref");
				if(!viewFileHref.equals("")) {
					String patLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewFileHref+ "&MPRUid="+ String.valueOf(inv.getMPRUid())+"\'"+")"+"\" >"+ patFullName + "</a>";
 					inv.setPatientFullNameLnk(patLink);
				}
				String condDesc = inv.getConditionCodeText();
				inv.setConditionCodeText(condDesc);				
				String viewHref = request.getAttribute("viewHref") == null ? "": (String) request.getAttribute("viewHref");
				if(!viewHref.equals("")) {
					String phcLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewHref+ 
							"&publicHealthCaseUID="+ String.valueOf(inv.getPublicHealthCaseUid())+
							"&investigationFormCode="+ String.valueOf(inv.getInvestigationFormCode())+"\'"+")"+"\" >"+ condDesc + "</a>"
				;
					inv.setConditionCodeTextLnk(phcLink);
				}
				
				util.setPatientFormat(inv);
				
			}

		}
	}
	
	private String getPatientFullName(InvestigationSummaryVO inv) {
		StringBuffer buff = new StringBuffer();
		if (inv.getPatientLastName() != null && inv.getPatientLastName().trim() != "")
			buff.append(inv.getPatientLastName().trim());
		if (inv.getPatientLastName() != null
				&& inv.getPatientFirstName() != null
				&& inv.getPatientLastName().trim() != ""
				&& inv.getPatientFirstName() != "")
			buff.append(",   ");
		if (inv.getPatientFirstName() != null
				&& inv.getPatientFirstName() != "")
			buff.append(inv.getPatientFirstName());
		String patientFullName = buff.toString();
		return patientFullName;
	}

	private String getInvestigatorFullName(InvestigationSummaryVO inv) {
		String investigatorFullName = "";
		try {
		if (inv.getInvestigatorLocalId() == null
				|| inv.getInvestigatorLocalId().trim().equalsIgnoreCase("")) {
			investigatorFullName = "";
		} else {
			StringBuffer buff = new StringBuffer();
			if (inv.getInvestigatorLastName() != null
					&& inv.getInvestigatorLastName().trim() != "")
				buff.append(inv.getInvestigatorLastName().trim());
			if (inv.getInvestigatorLastName() != null
					&& inv.getInvestigatorFirstName() != null
					&& inv.getInvestigatorLastName().trim() != ""
					&& inv.getInvestigatorFirstName() != "")
				buff.append(",   ");
			if (inv.getInvestigatorFirstName() != null
					&& inv.getInvestigatorFirstName() != "")
				buff.append(inv.getInvestigatorFirstName());
			investigatorFullName = buff.toString();
		}
		} catch (Exception ex) {
			logger.error("Error getInvestigatorFullName: "+ex.getMessage());
			ex.printStackTrace();
		}		
		return investigatorFullName;
	}
	
	private void _updateSummaryVosForDate(Collection<Object> invSummaryVOs) {
		
		Iterator<Object> iter = invSummaryVOs.iterator();
		while (iter.hasNext()) {
			InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter.next();
			if (invSummaryVO.getActivityFromTime() != null && invSummaryVO.getActivityFromTime().getTime() >  System.currentTimeMillis()) {
				invSummaryVO.setActivityFromTime(null);
			}
		}
	}
	private void updateInvestigationSummaryVObeforeSort(Collection<Object> invSummaryVOs) {
		Iterator<Object> iter = invSummaryVOs.iterator();
		while (iter.hasNext()) {
			InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter.next();
			if (invSummaryVO.getInvestigatorFullName() == null || (invSummaryVO.getInvestigatorFullName() != null && invSummaryVO.getInvestigatorFullName().equals(""))) {
					invSummaryVO.setInvestigatorFullName("ZZZZZ");
			}

			if (invSummaryVO.getCaseClassCodeTxt() == null || invSummaryVO.getCaseClassCodeTxt() != null && invSummaryVO.getCaseClassCodeTxt().equals("")) {
				invSummaryVO.setCaseClassCodeTxt("ZZZZZ");
			}
			if(invSummaryVO.getActivityFromTime() == null || (invSummaryVO.getActivityFromTime()!= null && invSummaryVO.equals(""))) {
			   Timestamp ts = new Timestamp (0) ; 
			   Calendar cal = GregorianCalendar.getInstance(); 
			   cal.setTimeInMillis (0); 
			   cal.set( 5000,0,1) ; 
			   ts.setTime(cal.getTimeInMillis()); 
			   invSummaryVO.setActivityFromTime(ts);
		   } 
			
			if (invSummaryVO.getNotifRecordStatusCd() == null || invSummaryVO.getNotifRecordStatusCd() != null && invSummaryVO.getNotifRecordStatusCd().equals("")) {
				invSummaryVO.setNotifRecordStatusCd("ZZZZZ");
			}
			
		
		}
		
	}
	private void updateInvestigationSummaryVOAfterSort(Collection<Object> invSummaryVOs) {
		Iterator<Object> iter = invSummaryVOs.iterator();
		while (iter.hasNext()) {
			InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter.next();
			if (invSummaryVO.getInvestigatorFullName() != null && invSummaryVO.getInvestigatorFullName().equals("ZZZZZ")) {
				invSummaryVO.setInvestigatorFullName("");
			}
			if (invSummaryVO.getCaseClassCodeTxt() != null && invSummaryVO.getCaseClassCodeTxt().equals("ZZZZZ")) {
				invSummaryVO.setCaseClassCodeTxt("");
			}
			
			if (invSummaryVO.getNotifRecordStatusCd() != null && invSummaryVO.getNotifRecordStatusCd().equals("ZZZZZ")) {
				invSummaryVO.setNotifRecordStatusCd("");
			}
		}
		
	}
	
	/**
	 * This method serves the purpose of filtering queue Information from the Context of Graphical Charts
	 * @param paForm
	 * @param request
	 */
	private Collection<Object>  _handleFilterForCharts(ProgramAreaForm paForm, Collection<Object>  investigationSummaryVOs, HttpServletRequest request) {
		
		String chartId = request.getParameter("chartId") == null ? "" : request.getParameter("chartId");
		if(chartId == "") return investigationSummaryVOs;
		//First Chart with filters Cases created in Last 7 Days
		String cCond = request.getParameter("section") == null ? "" : request.getParameter("section");
		//Condition
		String [] conditionList = new String[1];
		conditionList[0] = cCond;
		paForm.setAnswerArray("CONDITION", conditionList);
		//Just for I Chart, filter by Last 7 Days...
		if(chartId.equalsIgnoreCase(ChartConstants.C001)) {
			//Date
			String [] dateList = new String[1]; 
			dateList[0] = "7DAYS";		
			paForm.setAnswerArray("STARTDATE",dateList);
		} 
		//Populate the rest from the corresponding attributes
		if(chartId.equalsIgnoreCase(ChartConstants.C003)) { //For chart3, do not filter by StartDate
			if(paForm.getStartDateDropDowns().size() > 0) {
				paForm.getSearchCriteriaArrayMap().put("STARTDATE", _makeAnswerList(paForm.getStartDateDropDowns()));
			}
		}
		if(paForm.getInvestigators().size() > 0) {
			paForm.getSearchCriteriaArrayMap().put("INVESTIGATOR", _makeAnswerList(paForm.getInvestigators()));
		}
		if(paForm.getJurisdictions().size() > 0) {
			paForm.getSearchCriteriaArrayMap().put("JURISDICTION", _makeAnswerList(paForm.getJurisdictions()));
		}
		if(paForm.getCaseStatuses().size() > 0) {
			paForm.getSearchCriteriaArrayMap().put("CASESTATUS", _makeAnswerList(paForm.getCaseStatuses()));
		}
		if(paForm.getNotifications().size() > 0) {
			paForm.getSearchCriteriaArrayMap().put("NOTIFICATION", _makeAnswerList(paForm.getNotifications()));
		}		
		
		investigationSummaryVOs = filterInvs(paForm, request);		
		return investigationSummaryVOs;		
	}
	
	private String[] _makeAnswerList (ArrayList<Object>  list) {
		String[] returnSt = new String[list.size()];
		for(int i=0; i<list.size(); i++) {
			DropDownCodeDT cdDT = (DropDownCodeDT) list.get(i);
			returnSt[i] = cdDT.getKey();
		}
		return returnSt;		
	}
	

}