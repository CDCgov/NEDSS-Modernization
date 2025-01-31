package gov.cdc.nedss.webapp.nbs.action.summary;

import java.io.IOException;

import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.SummaryDataDAOImpl;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.summary.util.AggregateSearchUtil;
import gov.cdc.nedss.webapp.nbs.action.summary.util.AggregateSummaryUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.summary.AggregateSummaryForm;

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

/**
 * Struts Action Class to view, edit Create and Submit Aggregate Summary Report along with Notification
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * AggregateSummaryAction.java
 * Aug 3, 2009
 * @version 1.0
 */
public class AggregateSummaryAction extends DispatchAction 
{
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	static LogUtils logger = new LogUtils(AggregateSummaryAction.class.getName());
	
	/**
	 * Pre-populate and load the search form where users can perform
	 * searches summary data searches by multiple criteria. 
	 * @param mapping
	 * @param aForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward searchLoadSummaryData(ActionMapping mapping, ActionForm aForm,
			HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException 
	{
		AggregateSummaryForm form = (AggregateSummaryForm) aForm;
		// set the action mode to null to enable conditional display in JSPs
		form.setActionMode(null); 
		HttpSession session = request.getSession();
		String conditionCd = HTMLEncoder.encodeHtml(request.getParameter("conditionCd")); 
		AggregateSummaryUtil.loadSearchSpecificMetaData(conditionCd, form);
		SummaryDataDAOImpl summaryDAO = new SummaryDataDAOImpl();
		String progAreaCd = summaryDAO.getProgAreaCd(conditionCd);
		String strProgramAreas = AggregateSearchUtil.loadProgramAreasToString(request);
		if(!strProgramAreas.contains(progAreaCd))
		{
			session.setAttribute("error", "Failed at security checking.");
            throw new ServletException("Failed at security checking.");
		}
		form.setStrProgramAreas(strProgramAreas);
		form.setPageTitle("Aggregate Reporting", request);
		request.setAttribute("SearchSummaryDataForm", form);
		
		return mapping.findForward("default");
	}

	/**
	 * Takes the Search Criteria and searches for any existing Summary Data
	 * @param mapping
	 * @param aForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward searchSummaryDataSubmit(ActionMapping mapping, ActionForm aForm, HttpServletRequest request,HttpServletResponse response)  {		

		AggregateSummaryForm form = (AggregateSummaryForm) aForm;
		try {
			form.setActionMode(NEDSSConstants.SEARCH);
			form.setPageTitle("Aggregate Reporting", request);                     
			AggregateSearchUtil.searchSubmit(form, request);
        }
        catch (Exception e) {
        	logger.error("Exception in searchSummaryDataSubmit: " + e.getMessage());
        	e.printStackTrace();
            logger.fatal("ERROR in searchSummaryDataSubmit calling PAM_PROXY_EJB from mainsession control: ", e);
        } 

        return PaginationUtil.paginate(form, request, "default", mapping);
	}
	
	/**
	 * Loads the Summary Data to be viewed/edited/created 
	 * @param mapping
	 * @param aForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createLoadSummaryData(ActionMapping mapping, ActionForm aForm,
			HttpServletRequest request,HttpServletResponse response)  
	{
		AggregateSummaryForm form = (AggregateSummaryForm) aForm;
		form.setActionMode(NEDSSConstants.CREATE);	
		form.setPageTitle("Aggregate Reporting", request);
		//Prepopulate Search Criteria specific Questions to relevant Answers
		AggregateSummaryUtil.createLoadUtil(form, request);
		request.setAttribute("categoryTables", form.getCategoryTableList());
		
		return PaginationUtil.paginate(form, request, "default", mapping);
	}

	/**
	 * 
	 * @param mapping
	 * @param aForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createSubmitSummaryData(ActionMapping mapping, ActionForm aForm,HttpServletRequest request,HttpServletResponse response)  {
		
		AggregateSummaryForm form = (AggregateSummaryForm) aForm;	
		if(PaginationUtil.cvgPagination(request)) {
			form.setActionMode(NEDSSConstants.VIEW);
			return searchSummaryDataSubmit(mapping, aForm, request, response);
		}	
			
		String contextAction = request.getParameter("ContextAction") == null ? "" : request.getParameter("ContextAction");
		Long phcUid = AggregateSummaryUtil.createSubmitUtil(form, contextAction, request);
		if(phcUid == null) {
			AggregateSummaryUtil._LoadAnswersToTable(form, form.getAnswerMap(), request);
			return mapping.findForward("default");
		}
		
		request.setAttribute("phcUid", phcUid);
		
		// set appropriate action messages depending on context action
		
		// for save only
		if (contextAction.trim().equals("")) {
			ActionMessages messages = new ActionMessages();
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage("aggregateReport.successfullySaved"));
			request.setAttribute("aggregateReport_successMessages", messages);
		}
		// for save and send notification
		else if (contextAction.trim().equals("SubmitAndSendNotif")) {
			ActionMessages messages = new ActionMessages();
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage("aggregateReport.successfullySavedAndSentNotification"));
			request.setAttribute("aggregateReport_successMessages", messages);
		}
		AggregateSearchUtil.searchSubmit(form, request);
		return viewSummaryData(mapping, aForm, request, response);		
	}	
	
	public ActionForward viewSummaryData(ActionMapping mapping, ActionForm aForm,HttpServletRequest request,
				HttpServletResponse response)
	{
		// set the phcUid in request for display purposes
		String phcUid = request.getParameter("phcUid") == null ? "" : request.getParameter("phcUid");
		if(phcUid.equals("")) {
			phcUid = request.getAttribute("phcUid") == null ? "" : request.getAttribute("phcUid").toString();
		}
		request.setAttribute("phcUid", phcUid);

		AggregateSummaryForm form = (AggregateSummaryForm) aForm;
		form.setActionMode(NEDSSConstants.VIEW);
		form.setPageTitle("Aggregate Reporting", request);
		AggregateSummaryUtil.loadSummaryData(form, request);
		
		return PaginationUtil.paginate(form, request, "default", mapping);
	}
	
	public ActionForward editSummaryData(ActionMapping mapping, ActionForm aForm,HttpServletRequest request,
				HttpServletResponse response)  {
		// set the phcUid in request for display purposes
		String phcUid = request.getParameter("phcUid") == null ? "" : request.getParameter("phcUid");
		if(phcUid.equals("")) {
			phcUid = request.getAttribute("phcUid") == null ? "" : request.getAttribute("phcUid").toString();
		}
		request.setAttribute("phcUid", phcUid);
		
		AggregateSummaryForm form = (AggregateSummaryForm) aForm;
		form.setActionMode(NEDSSConstants.EDIT);
		AggregateSummaryUtil.loadSummaryData(form, request);
		form.setPageTitle("Aggregate Reporting", request);
		
		return PaginationUtil.paginate(form, request, "default", mapping);
	}
	
	
	public ActionForward updateSummaryData(ActionMapping mapping, ActionForm aForm,HttpServletRequest request,HttpServletResponse response)  {

		AggregateSummaryForm form = (AggregateSummaryForm) aForm;		
		String contextAction = request.getParameter("ContextAction") == null ? "" : request.getParameter("ContextAction");
		boolean success = AggregateSummaryUtil.editSubmitUtil(form, contextAction, request);
		if(success) {
			// set appropriate action messages depending on context action
			if (contextAction.trim().equals("")) {
				ActionMessages messages = new ActionMessages();
				messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
						new ActionMessage("aggregateReport.successfullySaved"));
				request.setAttribute("aggregateReport_successMessages", messages);
			}
			// for save and send notification
			else if (contextAction.trim().equals("SubmitAndSendNotif")) {
				ActionMessages messages = new ActionMessages();
				messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
						new ActionMessage("aggregateReport.successfullySavedAndSentNotification"));
				request.setAttribute("aggregateReport_successMessages", messages);
			}
			AggregateSearchUtil.searchSubmit(form, request);
			return viewSummaryData(mapping, form, request, response);						
		} else {
			return editSummaryData(mapping, form, request, response);
		}
	}		

	public ActionForward deleteSummaryData(ActionMapping mapping, ActionForm aForm,HttpServletRequest request,HttpServletResponse response)  {

		AggregateSummaryForm form = (AggregateSummaryForm) aForm;
		boolean isDeleted = AggregateSummaryUtil.deleteSummary(form, request);
		form.setActionMode(NEDSSConstants.SEARCH);
		if(isDeleted)
			return searchSummaryDataSubmit(mapping, aForm, request, response);
		else
			return viewSummaryData(mapping, aForm, request, response);
		
	}		
	
	/**
	 * Creates a Notification in View Mode
	 * @param mapping
	 * @param aForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward sendNotification(ActionMapping mapping, ActionForm aForm,HttpServletRequest request,HttpServletResponse response)  {
		
		AggregateSummaryForm form = (AggregateSummaryForm) aForm;
		AggregateSummaryUtil.createNotification(form, request);
		form.setManageList(null);
		ActionMessages messages = new ActionMessages();
		messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
				new ActionMessage("aggregateReport.successfulySentNotification"));
		request.setAttribute("aggregateReport_successMessages", messages);
		AggregateSearchUtil.searchSubmit(form, request);
		return viewSummaryData(mapping, form, request, response);
	}
	
}
