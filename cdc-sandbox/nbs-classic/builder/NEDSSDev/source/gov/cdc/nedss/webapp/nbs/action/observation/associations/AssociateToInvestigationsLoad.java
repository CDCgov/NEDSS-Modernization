package gov.cdc.nedss.webapp.nbs.action.observation.associations;

import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.homepage.charts.ChartConstants;
import gov.cdc.nedss.webapp.nbs.action.myinvestigation.ProgramAreaUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.observation.AssociateToInvestigationsForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

/**
 * Title: AssociateToInvestigations is an action class for associating a Lab Report or Morb Report to
 * an investigation. Note that a Morb Report can only be associated to a single investigation whereas
 * a Lab Report can be associated to multiple investigations.
 * Description: This is actions class View Lab or Morb Associate Investigations next action
 * Note: DSPatientPersonUID, DSObservationUID and DSPatientPersonDT are expected in context
 * Note: This is based on the ProgramAreaLoad action class. 
 * Copyright: Copyright (c) 2013 Company: Leidos
 *
 * @author Gregory Tucker 
 * @version 1.0
 */

public class AssociateToInvestigationsLoad extends DispatchAction {

	static final LogUtils logger = new LogUtils(AssociateToInvestigationsLoad.class.getName());
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
	public ActionForward loadQueue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Long obsUID = null;  //Lab or Morb report UID
		Long personUID = null;  //patient
		PersonVO personVO = null; //patient VO for name line
		String dsProgramArea = null;
		ArrayList<String> dsConditionList = new ArrayList<String>();

		AssociateToInvestigationsForm atiForm = (AssociateToInvestigationsForm) form;
		boolean forceEJBcall = false;
		// Reset Pagination first time
		
		boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
		if (initLoad && !PaginationUtil._dtagAccessed(request)) {
			atiForm.clearAll();
			forceEJBcall = true;
			// get the number of records to be displayed per page
			Integer queueSize = properties.getQueueSize(NEDSSConstants.ASSOCIATE_INVESTIGATIONS_QUEUE_SIZE);
			atiForm.getAttributeMap().put("queueSize", queueSize);
		}
		
		HttpSession session = request.getSession();
		if (session == null)
		{
			logger.fatal("error no session, go back to login screen");
			return mapping.findForward("login");
		}
		
		// get page context
		String contextAction = request.getParameter("ContextAction");
		if (contextAction != null) {
			
			//NBSContextMap -> <NBSPage id="PS500" name="Manage Investigation Association">
			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS500", contextAction);
			String sCurrTask = NBSContext.getCurrentTask(session);
			//set the ObsTypeCd
			if (sCurrTask.contains("AssociateToInvestigations2") || sCurrTask.contains("AssociateToInvestigations3")
					||sCurrTask.contains("AssociateToInvestigations10"))
				atiForm.setObsTypeCd(NEDSSConstants.LAB_REPORT);
			else
				atiForm.setObsTypeCd(NEDSSConstants.MORBIDITY_REPORT);
			//used for the submit button in the jsp
			
			String strFormHref = "/nbs/" + sCurrTask + ".do?method=associateToInvestigationsSubmit&ContextAction=" + tm.get(NBSConstantUtil.SUBMIT);
			atiForm.setFormHref(strFormHref);
			
			//used for the cancel button in the jsp
			request.setAttribute("ContextAction", tm.get(NBSConstantUtil.SUBMIT));
			String strCancelButtonHref = "/nbs/" + sCurrTask + ".do?method=associateToInvestigationsSubmit&ContextAction=" + tm.get(NBSConstantUtil.CANCEL);
			atiForm.setCancelButtonHref(strCancelButtonHref);
			
			//used for the condition links in the jsp
			String strViewHref = sCurrTask + ".do?method=associateToInvestigationsSubmitLink&ContextAction=" + tm.get("InvestigationID");
			atiForm.setViewHref(strViewHref);
			
			//needed to build the condition links
			request.setAttribute("viewHref", atiForm.getViewHref());

		}
		
		//Get the Morb or Lab Report UID
		
		 try
		 {
			 obsUID = (Long) NBSContext.retrieve(session, "DSObservationUID");
		 }
		 catch (NullPointerException ne)
		 {
			 logger.fatal("Can not retrieve DSObservationUID from Object Store.");
	     }
		 
		 
	    try
	    {
	    	personUID = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
	    }
	    catch (NullPointerException ne)
	    {
	    	logger.fatal("Can not retrieve DSPatientPersonUID from Object Store.");
	    }
	    try
	    {
	    	personVO = (PersonVO) NBSContext.retrieve(session, "DSPatientPersonVO");
	    }
	    catch (NullPointerException ne)
	    {
	    	logger.fatal("Can not retrieve DSPatientPersonDT from Object Store.");
	    }
	    try
	    {
	    	dsConditionList = (ArrayList) NBSContext.retrieve(session, "DSConditionList");
	    }
	    catch (NullPointerException ne)
	    {
	    	//conditionList may not be present 
	    	logger.debug("Can not retrieve DSConditionList from Object Store.");
	    	///personUID = new Long(10517024);  //for testing
	    }
	    try
	    {
	    	dsProgramArea = (String) NBSContext.retrieve(session, "DSProgramArea");

	    }
	    catch (NullPointerException ne)
	    {
	    	//conditionList may not be present 
	    	logger.debug("Can not retrieve DSProgramArea from Object Store.");
	    	///personUID = new Long(10517024);  //for testing
	    }
	    //check if condition list has Syphilis and set flag
	    CommonAction ca = new CommonAction();
	    atiForm.setProcessingDecisionLogic(ca.checkIfSyphilisIsInConditionList(dsProgramArea, dsConditionList, atiForm.getObsTypeCd()));
	    //place patient info in request for the line at the top
	    placePersonInfoInRequest(personVO, request);
	    
	    //get the investigations associated with the person
		try {
			ArrayList<Object> investigationSummaryVOs = new ArrayList<Object> ();
			if (forceEJBcall) {
				MainSessionHolder mainSessionHolder = new MainSessionHolder();
				MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(session);
				String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
				String sMethod = "getAssociatedInvestigations";
				//pass the patient to get the investigations for and the obsUID to set the existing associate flag
				Object[] oParms = new Object[] {personUID,obsUID};
				ArrayList<Object> arr = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, oParms);
				investigationSummaryVOs = (ArrayList<Object> ) arr.get(0);
				atiForm.getAttributeMap().put("DSInvestigationList",investigationSummaryVOs);
				atiForm.setObsUid(obsUID);
				atiForm.setMprUid(personUID);
			
				
				//Set InvSummaryVOColl as property of Form first time since this list is used for distinct dropdown values in the form
				atiForm.setInvSummaryVOColl(investigationSummaryVOs);
				atiForm.initializeDropDowns();
				atiForm.getAttributeMap().put("InvestigatorsCount",new Integer(atiForm.getInvestigators().size()));
				atiForm.getAttributeMap().put("JurisdictionsCount",new Integer(atiForm.getJurisdictions().size()));
				atiForm.getAttributeMap().put("ConditionsCount",new Integer(atiForm.getConditions().size()));
				atiForm.getAttributeMap().put("caseStatusCount",new Integer(atiForm.getCaseStatuses().size()));
				atiForm.getAttributeMap().put("dateFilterListCount",new Integer(atiForm.getStartDateDropDowns().size()));
				atiForm.getAttributeMap().put("invStatusCount",new Integer(atiForm.getInvStatuses().size()));
			} else {
				investigationSummaryVOs = (ArrayList<Object> ) atiForm.getAttributeMap().get("DSInvestigationList");
			}

			// Make Condition as Hyperlinks and put the list in the request for new lists
			boolean existing = request.getParameter("existing") == null ? false : true;
			if(contextAction != null && contextAction.equalsIgnoreCase("ReturnToMyInvestigations")) {
				updatePhcLinks(investigationSummaryVOs, request, atiForm.getViewHref());
				//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
				Collection<Object>  filteredColl = filterInvs(atiForm, request);
				if(filteredColl != null)
					investigationSummaryVOs = (ArrayList<Object> ) filteredColl;
				sortInvs(atiForm, investigationSummaryVOs, true, request);
			} else {
				sortInvs(atiForm, investigationSummaryVOs, existing, request);
				
				if(!existing) {
					updatePhcLinks(investigationSummaryVOs, request, atiForm.getViewHref());
				} else
					filterInvs(atiForm, request);
			}
			
			investigationSummaryVOs = (ArrayList<Object> )_handleFilterForCharts(atiForm, investigationSummaryVOs, request);
			
			//To make sure SelectAll is checked, see if no criteria is applied
			if(atiForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));			
			
			request.setAttribute("investigationList", investigationSummaryVOs);
			request.setAttribute("queueCount", String.valueOf(investigationSummaryVOs.size()));
			// Put the Inv List(with hyperlinks) in attributeMap for reuse
			atiForm.getAttributeMap().put("investigationList",investigationSummaryVOs);
			//Set page title
			if (atiForm.getObsTypeCd().equals("LabReport"))
				request.setAttribute("PageTitle","Associate Lab Report to Investigation(s)");
			else
				request.setAttribute("PageTitle","Associate Morbidity Report to an Investigation");
			//set form action links
			request.setAttribute("formHref", atiForm.getFormHref());
			request.setAttribute("cancelButtonHref", atiForm.getCancelButtonHref());

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in AssociateToInvestigationsLoad in getting investigationSummaryVOs from EJB");
			return mapping.findForward(NEDSSConstants.ERROR_PAGE);
		}
		return PaginationUtil.paginate(atiForm, request, "Edit",mapping);
	}

	/**
	 * filterInvestigatonSubmit used when Filters applied on the Associate Investigation Queue
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return org.apache.struts.action.ActionForward
	 */
	public ActionForward filterInvestigatonSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		AssociateToInvestigationsForm assocForm = (AssociateToInvestigationsForm) form;
		Collection<Object>  investigationSummaryVOs = filterInvs(assocForm, request);
		if(investigationSummaryVOs != null){
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
		//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
		}else{
			investigationSummaryVOs = (ArrayList<Object> ) assocForm.getInvSummaryVOColl();
		}
		assocForm.getAttributeMap().put("DSInvestigationList",investigationSummaryVOs);
		sortInvs(assocForm, investigationSummaryVOs, true, request);
		request.setAttribute("investigationList", investigationSummaryVOs);
		request.setAttribute("queueCount", String.valueOf(investigationSummaryVOs.size()));
		if (assocForm.getObsTypeCd().equals("LabReport"))
			request.setAttribute("PageTitle","Associate Lab Report to Investigation(s)");
		else
			request.setAttribute("PageTitle","Associate Morbidity Report to an Investigation");
		assocForm.getAttributeMap().put("PageNumber", "1");
		
		//set form action links
		request.setAttribute("formHref", assocForm.getFormHref());
		request.setAttribute("cancelButtonHref", assocForm.getCancelButtonHref());
		request.setAttribute("viewHref", assocForm.getViewHref());

		return PaginationUtil.paginate(assocForm, request, "Edit",mapping);			
	}
	

	/**
	 * filterInvs in Open Investigation Queue applies individual filter on the SummaryCollection  and returns
	 * @param assocForm
	 * @param request
	 * @return java.util.Collection
	 */
	private Collection<Object>  filterInvs(AssociateToInvestigationsForm assocForm, HttpServletRequest request) {
		
		Collection<Object>  investigationSummaryVOs = new ArrayList<Object> ();
		
		String srchCriteriaInv = null;
		String srchCriteriaJuris = null;
		String srchCriteriaCond = null;
		String srchCriteriaStatus = null;
		String sortOrderParam = null;
		String srchCriteriaDate = null;
		String srchCriteriaInvStatus = null;
		
		try {
			
			Map<Object,Object> searchCriteriaMap = assocForm.getSearchCriteriaArrayMap();
			// Get the existing SummaryVO collection in the form
			ArrayList<Object> invSummaryVOs = (ArrayList<Object> ) assocForm.getInvSummaryVOColl();
			
			// Filter by the investigator
			investigationSummaryVOs = paUtil.getFilteredInvestigation(invSummaryVOs, searchCriteriaMap);

			String[] inv = (String[]) searchCriteriaMap.get("INVESTIGATOR");
			String[] juris = (String[]) searchCriteriaMap.get("JURISDICTION");
			String[] cond = (String[]) searchCriteriaMap.get("CONDITION");
			String[] status = (String[]) searchCriteriaMap.get("CASESTATUS");
			String[] startDate = (String[]) searchCriteriaMap.get("STARTDATE");
			String[] invStatus = (String[]) searchCriteriaMap.get("INVSTATUS");
			
			
			Integer invCount = new Integer(inv == null ? 0 : inv.length);
			Integer jurisCount = new Integer(juris == null  ? 0 : juris.length);
			Integer condCount = new Integer(cond == null  ? 0 : cond.length);
			Integer statusCount = new Integer(status == null  ? 0 : status.length);
			Integer startDateCount = new Integer(startDate == null ? 0 : startDate.length);
			Integer invStatusCount = new Integer(invStatus == null ? 0 : invStatus.length);
			
			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if(invCount.equals((assocForm.getAttributeMap().get("InvestigatorsCount"))) &&
					(jurisCount.equals(assocForm.getAttributeMap().get("JurisdictionsCount"))) && 
					(condCount.equals(assocForm.getAttributeMap().get("ConditionsCount"))) &&
					(statusCount.equals(assocForm.getAttributeMap().get("caseStatusCount"))) &&
					(startDateCount.equals(assocForm.getAttributeMap().get("dateFilterListCount"))) &&
					(invStatusCount.equals(assocForm.getAttributeMap().get("invStatusCount"))) ) {
				
				String sortMethod = getSortMethod(request, assocForm);
				String direction = getSortDirection(request, assocForm);			
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<Object,Object> sColl =  assocForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) assocForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = paUtil.getSortCriteria(direction, sortMethod);
				}				
				Map<Object,Object> searchCriteriaColl = new TreeMap<Object,Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				assocForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
				return null;				
			}
						
			
			ArrayList<Object> invList = assocForm.getInvestigators();
			ArrayList<Object> jurisList = assocForm.getJurisdictions();
			ArrayList<Object> condList = assocForm.getConditions();
			ArrayList<Object> statusList = assocForm.getCaseStatuses();
			ArrayList<Object> dateList = assocForm.getStartDateDropDowns();
			ArrayList<Object> invStatusList = assocForm.getInvStatuses();
			
			Map<Object,Object> searchCriteriaColl = new TreeMap<Object,Object>();
			String sortMethod = getSortMethod(request, assocForm);
			String direction = getSortDirection(request, assocForm);			
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object,Object> sColl =  assocForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) assocForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = paUtil.getSortCriteria(direction, sortMethod);
			}
			
			srchCriteriaInv = queueUtil.getSearchCriteria(invList, inv, NEDSSConstants.FILTERBYINVESTIGATOR);
			srchCriteriaJuris = queueUtil.getSearchCriteria(jurisList, juris, NEDSSConstants.FILTERBYJURISDICTION);
			srchCriteriaCond = queueUtil.getSearchCriteria(condList, cond, NEDSSConstants.FILTERBYCONDITION);
			srchCriteriaStatus = queueUtil.getSearchCriteria(statusList, status, NEDSSConstants.FILTERBYSTATUS);
			srchCriteriaDate = queueUtil.getSearchCriteria(dateList, startDate, NEDSSConstants.FILTERBYDATE);
			srchCriteriaInvStatus = queueUtil.getSearchCriteria(invStatusList, invStatus, NEDSSConstants.FILTERBYDSMSTATUS);

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
			if(srchCriteriaInvStatus != null)
				searchCriteriaColl.put("INV109", srchCriteriaInvStatus);
			//if(filterPatient != null){
			//	searchCriteriaColl.put("PATIENT", filterPatient);
			//	request.setAttribute("PATIENT", filterPatient);
			//}	
			assocForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering the investigation by Investigator: "+ e.toString());
			
		} 
		return investigationSummaryVOs;
	}

	/**
	 * sortInvs in Associate Investigation Queue applies sort, direction on the SummaryCollection  and returns
	 * @param atiForm
	 * @param investigationSummaryVOs
	 * @param existing
	 * @param request
	 */
	private void sortInvs(AssociateToInvestigationsForm atiForm, Collection<Object>  investigationSummaryVOs, boolean existing, HttpServletRequest request) {

		// Retrieve sort-order and sort-direction from displaytag params
		String sortMethod = getSortMethod(request, atiForm);
		String direction = getSortDirection(request, atiForm);
		boolean sortMethodWasNone = false;
		
		boolean invDirectionFlag = true;
		if (direction != null && direction.equals("2"))
			invDirectionFlag = false;
		
		//default sort is open/closed then date time
		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
			if (!investigationSummaryVOs.isEmpty())
					sortMethodWasNone = true;
		}
		
		//Read from properties file to determine default sort order
		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				sortMethod = "getActivityFromTime";
				invDirectionFlag = !properties.getMyProgramAreasQueueSortbyNewestInvStartdate();
		}
		
		NedssUtils util = new NedssUtils();
		if (sortMethod != null && investigationSummaryVOs != null
				&& investigationSummaryVOs.size() > 0) {
			updateInvestigationSummaryVObeforeSort(investigationSummaryVOs);
			util.sortObjectByColumn(sortMethod,
					(Collection<Object>) investigationSummaryVOs, invDirectionFlag);
			//after sorting if sortMethod is getActivityFromTime replace fictious time with nulls
			_updateSummaryVosForDate(investigationSummaryVOs);
			updateInvestigationSummaryVOAfterSort(investigationSummaryVOs);
			
		}
		//if no sort selected we sort on Status (Open, Closed) then Date
		if (sortMethodWasNone) {
					util.sortObjectByColumn("getInvestigationStatusDescTxt",
							(Collection<Object>) investigationSummaryVOs, invDirectionFlag);
		}
		if(!existing) {
			//Finally put sort criteria in form
			String sortOrderParam = paUtil.getSortCriteria(invDirectionFlag == true ? "1" : "2", sortMethod);
			Map<Object,Object> searchCriteriaColl = new TreeMap<Object,Object>();
			searchCriteriaColl.put("sortSt", sortOrderParam);
			atiForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}

	}
	
	private String getSortMethod(HttpServletRequest request, AssociateToInvestigationsForm atiForm) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
		} else{
			return atiForm.getAttributeMap().get("methodName") == null ? null : (String) atiForm.getAttributeMap().get("methodName");
		}
		
	}

	private String getSortDirection(HttpServletRequest request, AssociateToInvestigationsForm atiForm) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		} else{
			return atiForm.getAttributeMap().get("sortOrder") == null ? "1": (String) atiForm.getAttributeMap().get("sortOrder");
		}
		
	}
	
	
	private void updatePhcLinks(ArrayList<Object>  investigationList,
			HttpServletRequest request, String strViewHref) {
		
		if (investigationList != null && investigationList.size() != 0) {
			CachedDropDownValues cddv = new CachedDropDownValues();
			for (int i = 0; i < investigationList.size(); i++) {
				InvestigationSummaryVO inv = (InvestigationSummaryVO) investigationList.get(i);
				//populate Investigator Full Name
				String invFullNm = getInvestigatorFullName(inv);
				inv.setInvestigatorFullName(invFullNm);
				//update investigation status by appending current process state
				String currentStateDesc = null;
				if (inv.getCurrProcessStateCd() != null)
					currentStateDesc = cddv
							.getCodeShortDescTxt(
									inv.getCurrProcessStateCd(),
									NEDSSConstants.CM_PROCESS_STAGE);
				if (inv.getInvestigationStatusDescTxt() != null) {
					inv.setInvestigationStatusDescTxt(currentStateDesc == null ? inv
							.getInvestigationStatusDescTxt() : inv
							.getInvestigationStatusDescTxt()
							+ " ("
							+ currentStateDesc + ")");
				}
				if(inv.getInvestigationStatusDescTxt() != null && inv.getInvestigationStatusDescTxt().contains("Open")){
					inv.setInvestigationStatusDescTxt("<b><font color=\"#006000\">"+inv.getInvestigationStatusDescTxt()+"</font></b>");
				}
				//update condition code link
				String condDesc = inv.getConditionCodeText();
				inv.setConditionCodeText(condDesc);
				String viewHref = strViewHref;
				if (viewHref == null || viewHref.isEmpty())
						viewHref = request.getAttribute("viewHref") == null ? "": (String) request.getAttribute("viewHref");
				if(!viewHref.equals("")) {
					String phcLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewHref+ "&publicHealthCaseUID="+ String.valueOf(inv.getPublicHealthCaseUid())+"\'"+")"+"\" >"+ condDesc + "</a>";
					inv.setConditionCodeTextLnk(phcLink);
				}
				//also set the logic that STD investigation association cannot be removed if lab/morb report had triggered the investigation.
				if(inv.getIsAssociated() && PropertyUtil.isStdOrHivProgramArea(inv.getProgAreaCd()) && inv.getAddTime()!=null && inv.getActFromTime()!=null && inv.getAddTime().equals(inv.getActFromTime()))
					inv.setDisabled(NEDSSConstants.DISABLED);
			}

		}
	}
	

	private String getInvestigatorFullName(InvestigationSummaryVO inv) {
		String investigatorFullName = "";

			StringBuffer buff = new StringBuffer();
			if (inv.getInvestigatorLastName() != null
					&& inv.getInvestigatorLastName() != "")
				buff.append(inv.getInvestigatorLastName());
			if (inv.getInvestigatorLastName() != null
					&& inv.getInvestigatorFirstName() != null
					&& inv.getInvestigatorLastName() != ""
					&& inv.getInvestigatorFirstName() != "")
				buff.append(",   ");
			if (inv.getInvestigatorFirstName() != null
					&& inv.getInvestigatorFirstName() != "")
				buff.append(inv.getInvestigatorFirstName());
			investigatorFullName = buff.toString();
		
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
		}
		
	}
	
	/**
	 * This method serves the purpose of filtering queue Information from the Context of Graphical Charts
	 * @param atiForm
	 * @param request
	 */
	private Collection<Object>  _handleFilterForCharts(AssociateToInvestigationsForm atiForm, Collection<Object>  investigationSummaryVOs, HttpServletRequest request) {
		
		String chartId = request.getParameter("chartId") == null ? "" : request.getParameter("chartId");
		if(chartId == "") return investigationSummaryVOs;
		//First Chart with filters Cases created in Last 7 Days
		String cCond = request.getParameter("section") == null ? "" : request.getParameter("section");
		//Condition
		String [] conditionList = new String[1];
		conditionList[0] = cCond;
		atiForm.setAnswerArray("CONDITION", conditionList);
		//Just for I Chart, filter by Last 7 Days...
		if(chartId.equalsIgnoreCase(ChartConstants.C001)) {
			//Date
			String [] dateList = new String[1]; 
			dateList[0] = "7DAYS";		
			atiForm.setAnswerArray("STARTDATE",dateList);
		} 
		//Populate the rest from the corresponding attributes
		if(chartId.equalsIgnoreCase(ChartConstants.C003)) { //For chart3, do not filter by StartDate
			if(atiForm.getStartDateDropDowns().size() > 0) {
				atiForm.getSearchCriteriaArrayMap().put("STARTDATE", _makeAnswerList(atiForm.getStartDateDropDowns()));
			}
		}
		if(atiForm.getInvestigators().size() > 0) {
			atiForm.getSearchCriteriaArrayMap().put("INVESTIGATOR", _makeAnswerList(atiForm.getInvestigators()));
		}
		if(atiForm.getJurisdictions().size() > 0) {
			atiForm.getSearchCriteriaArrayMap().put("JURISDICTION", _makeAnswerList(atiForm.getJurisdictions()));
		}
		if(atiForm.getCaseStatuses().size() > 0) {
			atiForm.getSearchCriteriaArrayMap().put("CASESTATUS", _makeAnswerList(atiForm.getCaseStatuses()));
		}
		if(atiForm.getInvStatuses().size() > 0) {
			atiForm.getSearchCriteriaArrayMap().put("INVSTATUS", _makeAnswerList(atiForm.getInvStatuses()));
		}		
		
		investigationSummaryVOs = filterInvs(atiForm, request);		
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
	
	/*********************************************************************************
	 * associateToInvestigationsSubmit
	 * Action Class to do the backend processing to associate the observation
	 *    to one or more investigations
	 *
	 * @param mapping - ActionMapping
	 * @param mapping - form
	 * @param mapping - request
	 * @param request - response
	 */
	public ActionForward associateToInvestigationsSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		ActionForward forwardNew = new ActionForward();
		Collection<String>  confirmMsgAssociated= new ArrayList<String> ();
		Collection<String>  confirmMsgDisassociated= new ArrayList<String> ();

		try {
		String contextAction = request.getParameter("ContextAction");
		HttpSession session = request.getSession(false);

	   // NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
		if (session == null)
		{
			logger.fatal("error no session, go back to login screen");
			return mapping.findForward("login");
		}
		
		if (contextAction == null)
		{
			contextAction = (String) request.getAttribute("ContextAction");
		}
		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL))
		{
			return mapping.findForward(contextAction);
		}  
		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.SUBMIT))
		{

			boolean allInvestigationsDisassociated = false;
			Collection<Object>  newEventSummaryCollection= new ArrayList<Object> ();
			Collection<Object>  addedRelationsCollection= new ArrayList<Object> ();
			Collection<Object>  deletedRelationsCollection= new ArrayList<Object> ();
			
			AssociateToInvestigationsForm atiForm = (AssociateToInvestigationsForm) form;
			atiForm.clearContext(); //for next request coming in

			String[] checkBoxIds = atiForm.getSelectedcheckboxIds();
			if(checkBoxIds !=null){

				for(int i = 0 ; i<checkBoxIds.length ; i++){
					String observationUID = checkBoxIds[i];
					StringTokenizer st = new StringTokenizer(observationUID,"|"); 
					while (st.hasMoreTokens()) { 
						String token = st.nextToken();
						newEventSummaryCollection.add((token.toString()));
					}				
				}
				//if nothing checked, see if something checked got unchecked..
				if (newEventSummaryCollection.size() == 0) {
						Collection<Object>  isvoColl = atiForm.getInvSummaryVOColl();
					      Iterator<Object>  isvoIter = isvoColl.iterator();
					       while (isvoIter.hasNext()) {
					    	   InvestigationSummaryVO investigationSummaryVO = (InvestigationSummaryVO) isvoIter.next();

					    	   if (investigationSummaryVO.getIsAssociated()) {
					        	  //if true with need to delete the relationship
					        		  Long phcUID =   investigationSummaryVO.getPublicHealthCaseUid();
					        		  deletedRelationsCollection.add(phcUID.longValue());
					        		  confirmMsgDisassociated.add(investigationSummaryVO.getConditionCodeText() + ", (" +  investigationSummaryVO.getLocalId() + ")");
									  allInvestigationsDisassociated = true;
					    	   } //if set
					       } //iter
				} //size=0
				
				//items are checked, see if they were unchecked going in, add
				if (newEventSummaryCollection.size() > 0) {
					Iterator<Object>  chkboxIter = newEventSummaryCollection.iterator();
					while (chkboxIter.hasNext()) {
						String strPhcUID = (String) chkboxIter.next();
						if (!strPhcUID.isEmpty()) {
							Long phcUID = new Long(strPhcUID);
							//find it in the list
							Collection<Object>  isvoColl = atiForm.getInvSummaryVOColl();
						    Iterator<Object>  isvoIter = isvoColl.iterator();
						    while (isvoIter.hasNext()) {
						    	   InvestigationSummaryVO investigationSummaryVO = (InvestigationSummaryVO) isvoIter.next();

						    	   if (investigationSummaryVO.getPublicHealthCaseUid().longValue() == phcUID.longValue()) {
						        	  //if it wasn't set before it is a new relation 
						    		   if (!investigationSummaryVO.getIsAssociated()) {
						        		  addedRelationsCollection.add(phcUID.longValue());
						        		  confirmMsgAssociated.add(investigationSummaryVO.getConditionCodeText() + " (" +  investigationSummaryVO.getLocalId() + ")");
						    		   } //if it wasn't assoc before
						         } //if found the phcUID
						      } //isvo iter
						} //not empty string
					} //find the passed checkbox value in the list
				} //walk through the checkbox list
			
				//we need to walk through one more time to see if something needs to 
				//be added to the deleted list
				if (newEventSummaryCollection.size() > 0) {
					Collection<Object>  isvoColl = atiForm.getInvSummaryVOColl();
				    Iterator<Object>  isvoIter = isvoColl.iterator();
				    while (isvoIter.hasNext()) {
				    	   InvestigationSummaryVO investigationSummaryVO = (InvestigationSummaryVO) isvoIter.next();
				    	   if (investigationSummaryVO.getIsAssociated()) {
				    		   Long formPHCUid = investigationSummaryVO.getPublicHealthCaseUid();
				    		   boolean found = false;
				        	  //if it was set and it's not in the list, we need to add to deleted.. 
				    		   Iterator<Object>  chkboxIter = newEventSummaryCollection.iterator();
				    		   while (chkboxIter.hasNext()) {
				    			   String strPhcUID = (String) chkboxIter.next();
				    			   if (!strPhcUID.isEmpty()) {
				    				   Long phcUID = new Long(strPhcUID);
				    				   if (phcUID.longValue() == formPHCUid.longValue())
				    					   found = true;
				    			   } //isEmpty
				    		   } //iter checkbox
				    		   if (!found) {
				    			   deletedRelationsCollection.add(formPHCUid.longValue());
				    		       confirmMsgDisassociated.add(investigationSummaryVO.getConditionCodeText() + " (" +  investigationSummaryVO.getLocalId() + ")");
				    		   }
				    	   }//isAssoc in form
					} //checkBox iter
				} //items in checkbox list
							
				if (addedRelationsCollection.isEmpty() && deletedRelationsCollection.isEmpty())
						return mapping.findForward(contextAction);  //nothing to do - return
				
				try 
				{
					Long obsUID = atiForm.getObsUid();
					String businessTriggerSpecified = "";
					if (allInvestigationsDisassociated)
						businessTriggerSpecified = NEDSSConstants.OBS_LAB_DISASSOCIATE;
					MainSessionHolder mainSessionHolder = new MainSessionHolder();
					MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(session);
					String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
					String sMethod = "setAssociatedInvestigations";
					//pass the patient to get the investigations for and the obsUID to set the existing associate flag
					Object[] oParms = new Object[] {obsUID, atiForm.getObsTypeCd(), atiForm.getProcessingDecision(), addedRelationsCollection, deletedRelationsCollection, businessTriggerSpecified};
					ArrayList<Object> arr = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, oParms);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error in AssociateToInvestigationsLoad in storing new relations");
					session.setAttribute("error", "Application Error while associating Investigations to an Observation");
					// throw new ServletException("Application Error while associating Investigations to Observation");
					return mapping.findForward(NEDSSConstants.ERROR_PAGE);
				}
	            //prepare confirmation (success) message to be displayed back on the Lab or Morb report
				if (!confirmMsgDisassociated.isEmpty() || !confirmMsgAssociated.isEmpty()) {
					String successMsg;
					if (atiForm.getObsTypeCd().contentEquals("LabReport"))
						successMsg = "Lab Report";
					else
						successMsg = "Morbidity Report";
					if (!confirmMsgAssociated.isEmpty()) {
						if (confirmMsgAssociated.size() > 1)
							successMsg = successMsg.concat(" successfully associated to investigations: ");
						else
							successMsg = successMsg.concat(" successfully associated to investigation: ");
						Iterator<String>  assocIter = confirmMsgAssociated.iterator();
						while (assocIter.hasNext()) {
		    			   String strConditionCaseID = (String) assocIter.next();
		    			   successMsg = successMsg.concat(strConditionCaseID);
		    			   if (assocIter.hasNext())
		    				   successMsg = successMsg.concat(", ");
						}
					}
					if (!confirmMsgDisassociated.isEmpty()) {
						if (confirmMsgAssociated.size() > 1)
							successMsg = successMsg.concat(":");
						if (confirmMsgDisassociated.size() > 1)
							successMsg = successMsg.concat(" successfully disassociated from investigations: ");
						else
							successMsg = successMsg.concat(" successfully disassociated from investigation: ");
						Iterator<String>  disAssocIter = confirmMsgDisassociated.iterator();
						while (disAssocIter.hasNext()) {
		    			   String strConditionCaseID = (String) disAssocIter.next();
		    			   successMsg = successMsg.concat(strConditionCaseID);
		    			   if (disAssocIter.hasNext())
		    				   successMsg = successMsg.concat(", ");
						}
					}					
					if (!successMsg.isEmpty()){
						ActionMessages messages = new ActionMessages();
    				messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
    						new ActionMessage(NBSPageConstants.INV_ASSOCIATION_SUCCESS, successMsg));
    				request.setAttribute("success_messages", messages);
					request.setAttribute("displayInformationExists", successMsg); //gst delete
					}
				}
				
				return mapping.findForward(contextAction);  //return to Vew Lab or View Morb
				
			}// submit context  
		} 
		else//not submit context??
		{
			session.setAttribute("error", "No suitable Context Action found");
			//throw new ServletException("No suitable Context Action found");
		}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return forwardNew;
	}
	
	
	/**
	 * The Condition Link comes to this action to forward to open the Investigation page
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 */
	public ActionForward associateToInvestigationsSubmitLink(ActionMapping mapping, ActionForm form,
             HttpServletRequest request,
             HttpServletResponse response)
     throws IOException, ServletException
     {

		 HttpSession session = request.getSession();

		 if (session == null)
		 {
			 logger.fatal("error no session");
			 return mapping.findForward("login");
		 }

		 //get page context for debugging purposes
		 String contextAction = request.getParameter("ContextAction");
		 if (contextAction == null)
			 contextAction = (String)request.getAttribute("ContextAction");
		 NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

		 if (nbsSecurityObj == null) {
			 logger.fatal("Error: no nbsSecurityObj in the session, go back to login screen");
			 return mapping.findForward("login");
		 }

		 logger.debug(NBSBOLookup.INVESTIGATION);
		 logger.debug(NBSOperationLookup.VIEW);
		 logger.debug(ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA);
		 logger.debug(ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
		 logger.debug("get permissions = " + nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                      NBSOperationLookup.VIEW,
                      ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
                      ProgramAreaJurisdictionUtil.ANY_JURISDICTION));

		 if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
                      NBSOperationLookup.VIEW,
                      ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
                      ProgramAreaJurisdictionUtil.ANY_JURISDICTION))
		 {
			 logger.fatal(
					 "Error: no permisstion for ProgramArea, go back to login screen");

			 return mapping.findForward("login");
		 }


		 String sUid = request.getParameter("publicHealthCaseUID");
		 if( sUid !=null)
			 NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, sUid);

return mapping.findForward(contextAction);

}
	
	
	
	/**
	 * Place the Person info in request for the JSP line at the top
	 *
	 * @param personVO - the patient the Lab Report or Morb Report was for
	 * @param request - the current request
	 */
	private void placePersonInfoInRequest(PersonVO personVO, HttpServletRequest request)
	{
		
		PersonDT personDT = personVO.getThePersonDT();
		if (personDT.getBirthTime() != null) {
			PersonUtil.setCurrentAgeToRequest(request, personDT);
			request.setAttribute("birthTime",StringUtils.formatDate(personDT.getBirthTime()));
		}
		else {
			request.setAttribute("currentAge","");
		    request.setAttribute("currentAgeUnitCd", "");
		    request.setAttribute("birthTime","");
		}
		//Patient Name
        String	strPName = ( (personDT.getFirstNm() == null) ? "" :
            personDT.getFirstNm()) +" "+ ( (personDT.getMiddleNm() == null) ? "" :
          	  personDT.getMiddleNm())+ " "+( (personDT.getLastNm() == null) ? "" :
          		  personDT.getLastNm());
		  if(null == strPName || strPName.equalsIgnoreCase("null")){
			 strPName ="";
		   }
		  //request.setAttribute("patientFullLegalName",strPName);		
		  //request.setAttribute("patientSuffixName", ( (personDT.getNmSuffix() == null) ? " " : cachedDropDownValues.getCodeShortDescTxt(personDT.getNmSuffix(), "P_NM_SFX")));
		  
		  placeLegalNameInRequest(personVO, request);
	      request.setAttribute("currSexCd", (personDT.getCurrSexCd() == null) ? "" : personDT.getCurrSexCd());
	      
	      String displayLocalID = PersonUtil.getDisplayLocalID(personDT.getLocalId());
	      if (displayLocalID != null)
	         request.setAttribute("personLocalID", displayLocalID);
	      else
	    	 request.setAttribute("personLocalID", "");

	         
	      return;
	}
	/**
	 * Get the legal name from the names collection in the person vo.
	 *
	 * @param personVO - the patient the Lab Report or Morb Report was for
	 * @param request - the current request
	 */
	private void placeLegalNameInRequest(PersonVO personVO, HttpServletRequest request)
	{
		
		Collection<Object>  names = personVO.getThePersonNameDTCollection();

		if (names == null) {
			logger.info("Names Collection was empty??");
			return;
		}
    
		Iterator<Object>  iter = names.iterator();
		Timestamp mostRecentNameAOD = null;
		//look through the names and find the most current legal name
		while (iter.hasNext()) {
			PersonNameDT name = (PersonNameDT) iter.next();
			if (name != null) {
             // for personInfo
				if (name != null && name.getNmUseCd() != null &&
						name.getNmUseCd().equals(NEDSSConstants.LEGAL) &&
						name.getStatusCd() != null &&
						name.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
						name.getRecordStatusCd() != null &&
						name.getRecordStatusCd().
						equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					if (mostRecentNameAOD == null ||
							(name.getAsOfDate() != null &&
							!name.getAsOfDate().before(mostRecentNameAOD))) {
								mostRecentNameAOD = name.getAsOfDate();
								String	strPName = ( (name.getFirstNm() == null) ? "" :
									name.getFirstNm()) +" "+ ( (name.getMiddleNm() == null) ? "" :
										name.getMiddleNm())+ " "+( (name.getLastNm() == null) ? "" :
											name.getLastNm());
								if(null == strPName || strPName.equalsIgnoreCase("null")){
									strPName ="";
								}
								request.setAttribute("patientFullLegalName",strPName);
								if (name.getNmSuffix() == null) //almost always null..
									request.setAttribute("patientSuffixName"," ");
								else {
									CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
									String suffix = cachedDropDownValues.getCodeShortDescTxt(name.getNmSuffix(), "P_NM_SFX");
									request.setAttribute("patientSuffixName", suffix);
								}
						}	 //most recent rec
					} //active legal name
			} //name not null
       } //has next
	} // end of placeLegalNameInRequest()
	
	
	/**
	 * ActionForward for the Processing Decision popup
	 * It is called from the OpenProcessingDecisionPopup() function
	 * 	in associateInvestigation.js. The jsp is get_STD_Processing_Decision.jsp.
	 * @param programArea - the progArea of the Lab or Morb
	 * @param conditionList - the list of conditions (usually only one) assoc with the lab or morb
	 * @param atiForm - the form
	 */
	public ActionForward processingDecisionLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		AssociateToInvestigationsForm atiForm = (AssociateToInvestigationsForm)form;
		atiForm.setProcessingDecision(null);
		String EVENT = "event";
		try {
			request.setAttribute(EVENT, "LabReport");
		} catch (Exception e) {
			logger.error("Error while obtaining STD Processing Decision: " + e.toString());
			throw new ServletException("Error while obtaining STD Processing Decision: "+e.getMessage(),e);
		}		
		return (mapping.findForward("loadStdPD"));
	}	
	
	
}