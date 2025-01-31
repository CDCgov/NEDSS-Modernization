package gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement;

import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DSMLibraryActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement.DecisionSupportLibraryForm;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class DSMLibraryAction extends DispatchAction {
	static final LogUtils logger = new LogUtils(
			DSMLibraryAction.class.getName());

	public ActionForward loadqueue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DecisionSupportLibraryForm dsmLibForm = (DecisionSupportLibraryForm) form;
		ArrayList<Object> displayList = null;
		boolean existing = request.getParameter("existing") == null ? false
				: true; // sort

		request.getSession().removeAttribute("DSForm");

		boolean initLoad = request.getParameter("initLoad") == null ? false
				: Boolean.valueOf(request.getParameter("initLoad"))
						.booleanValue();

		if (initLoad) {
			try {
				if (request.getParameterMap() != null
						&& request.getParameterMap().size() > 0) {
					request.getParameterMap().clear();
					String[] strInitLoad = new String[] {String.valueOf(initLoad)};
					request.getParameterMap().put("initLoad", strInitLoad);
				}
			} catch (Exception e) {
				logger.debug(e.toString());
			}
		}

		try {

			if (initLoad && !PaginationUtil._dtagAccessed(request)) {
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
				dsmLibForm.clearAll();
			}

			DSMLibraryActionUtil util = new DSMLibraryActionUtil();
			displayList = (ArrayList<Object>) util
					.getAlgorithmSummaries(request.getSession());
			dsmLibForm.setAlgorithmList(displayList);
			request.setAttribute("algorithmList", displayList);
			dsmLibForm.getAttributeMap().put("algorithmList", displayList);

			dsmLibForm.initializeDropDowns();
			dsmLibForm.getAttributeMap().put("eventTypeCount",
					new Integer(dsmLibForm.getEventType().size()));
			dsmLibForm.getAttributeMap().put("actionCount",
					new Integer(dsmLibForm.getAction().size()));
			dsmLibForm.getAttributeMap().put("statusCount",
					new Integer(dsmLibForm.getStatus().size()));
			dsmLibForm.getAttributeMap().put("dateFilterListCount",
					new Integer(dsmLibForm.getDateFilterList().size()));

			Collection sortedFilteredList = DSMLibraryActionUtil
					.filterDSMLibrary(dsmLibForm, request);
			if (sortedFilteredList != null) {
				displayList = (ArrayList<Object>) sortedFilteredList;
				DSMLibraryActionUtil.sortDSMLibarary(dsmLibForm, displayList,existing, request);
			}
			if (displayList != null) {
				request.setAttribute("queueCount",
						String.valueOf(displayList.size()));
				request.setAttribute("algorithmList", displayList);
				dsmLibForm.getAttributeMap().put("queueCount",
						String.valueOf(displayList.size()));
			}
		} catch (Exception e) {
			logger.error("Exception occurred when loading Algorithm Library: " + e.toString(),e);
			throw new ServletException(
					"Error while attempting to view Algorithm Library: "
							+ e.getMessage(), e);
		}
		dsmLibForm.setPageTitle(
				"Manage Workflow Decision Support: Algorithm Library", request);
		return PaginationUtil.paginate(dsmLibForm, request, "dsmLib", mapping);

	}

	/**
	 * filterDSMLibrarySubmit apply any filter criteria to the Algorithm Library
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return org.apache.struts.action.ActionForward
	 * @throws ServletException
	 */
	public ActionForward filterDSMLibrarySubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		DecisionSupportLibraryForm dsmLibForm = (DecisionSupportLibraryForm) form;
		Collection<Object> dsmSummaryColl = null;
		try {
			dsmSummaryColl = DSMLibraryActionUtil.filterDSMLibrary(dsmLibForm,
					request);

			if (dsmSummaryColl != null) {
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
				// When all filters are applied (equivalent to no filter
				// applied), Collection<Object> is set to null in which case
				// retrieve it from Form.
			} else {
				dsmSummaryColl = (ArrayList<Object>) dsmLibForm
						.getAlgorithmList();
			}

			request.setAttribute("algorithmList", dsmSummaryColl);
			dsmLibForm.getAttributeMap().put("algorithmList", dsmSummaryColl);
			request.setAttribute("queueCount",
					String.valueOf(dsmSummaryColl.size()));
			dsmLibForm.getAttributeMap().put("queueCount",
					String.valueOf(dsmSummaryColl.size()));
			dsmLibForm.setPageTitle(
					"Manage Workflow Decision Support: Algorithm Library",
					request);
			dsmLibForm.getAttributeMap().put("PageNumber", "1");
		} catch (Exception e) {
			logger.error("Exception occurred while filtering the DSM Library: " + e.toString(),e);
			throw new ServletException(
					"Error while attempting to view Algorithm Library: "
							+ e.getMessage(), e);
		}

		return PaginationUtil.paginate(dsmLibForm, request, "dsmLib", mapping);
	}
}
