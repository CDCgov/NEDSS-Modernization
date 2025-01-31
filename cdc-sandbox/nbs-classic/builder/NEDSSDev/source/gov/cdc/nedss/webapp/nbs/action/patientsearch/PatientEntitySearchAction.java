package gov.cdc.nedss.webapp.nbs.action.patientsearch;

import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PatientEntitySearchAction extends DispatchAction {
	static final LogUtils logger = new LogUtils(
			PatientEntitySearchAction.class.getName());

	public ActionForward searchLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("PatientEntitySearch", "true");
		PersonSearchForm personSearchForm = (PersonSearchForm) form;
		personSearchForm.reset();
		return (mapping.findForward("SearchLoad"));
	}

	public ActionForward refineSearchLoad(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		PersonSearchForm psForm = (PersonSearchForm)form;
		PatientSearchVO psVO = psForm.getPersonSearch();
	    psVO.setLocalID(psVO.getOldLocalID());
	    psVO.setOldLocalID(new String());
		psForm.setPersonSearch(psVO);
		request.setAttribute("PatientEntitySearch", "true");
		request.setAttribute("LNameOp", psVO.getLastNameOperator());
		request.setAttribute("FNameOp", psVO.getFirstNameOperator());
		request.setAttribute("DOBOp", psVO.getBirthTimeOperator());
		request.setAttribute("StrAddOp", psVO.getStreetAddr1Operator());
		request.setAttribute("CityOp", psVO.getCityDescTxtOperator());
		return (mapping.findForward("SearchLoad"));
	}

	@SuppressWarnings("unchecked")
	public ActionForward searchSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		PersonSearchForm personSearchForm = (PersonSearchForm) form;

		PatientSearchVO psVO = personSearchForm.getPersonSearch();
		psVO.setActive(true);
		String contextAction = request.getParameter("ContextAction");
		
		if (contextAction.equalsIgnoreCase("Submit")) {
			PersonUtil.findPeople(personSearchForm, session, request, false,
					true);
			personSearchForm.clearSelections();
			String scString = PersonUtil.buildSearchCriteriaString(psVO);
			personSearchForm.getAttributeMap().put("DSSearchCriteriaString",
					scString);
		}

		request.setAttribute("DSSearchCriteriaString", personSearchForm
				.getAttributeMap().get("DSSearchCriteriaString"));
		request.setAttribute("refineSearchHref",
				"/nbs/PatientEntitySearch.do?method=refineSearchLoad");
		request.setAttribute("newSearchHref",
				"/nbs/PatientEntitySearch.do?method=searchLoad");
		SearchResultPersonUtil srpUtil = new SearchResultPersonUtil();
		if (contextAction != null
				&& "filterPatientSubmit".equalsIgnoreCase(contextAction)) {
			return srpUtil
					.filterPatientSubmit(mapping, form, request, response);
		} else if (contextAction != null
				&& "removeFilter".equalsIgnoreCase(contextAction)) {
			String totalRecords = (String) personSearchForm.getAttributeMap()
					.get("totalRecords");
			personSearchForm.clearAll();
			personSearchForm.getAttributeMap()
					.put("totalRecords", totalRecords);
			return srpUtil
					.filterPatientSubmit(mapping, form, request, response);
		} else if (contextAction != null
				&& "sortingByColumn".equalsIgnoreCase(contextAction)) {
			ArrayList<Object> patientVoCollection = (ArrayList<Object>) request
					.getSession().getAttribute(NBSConstantUtil.DSPersonList);
			if(patientVoCollection == null || patientVoCollection.isEmpty()) 
				patientVoCollection = (ArrayList<Object> )NBSContext.retrieve(request.getSession() ,NBSConstantUtil.DSPersonList);
			
			try {
				srpUtil.sortPatientLibarary(personSearchForm,
						patientVoCollection, false, request);
				request.setAttribute("personList", patientVoCollection);
			} catch (Exception e) {
				logger.error("Error while sorting the patient list");
				e.printStackTrace();
			}
			return PaginationUtil.personPaginate(personSearchForm, request,
					"searchResultLoad", mapping);
		}
		return PaginationUtil.personPaginate(personSearchForm, request,
				"searchResultLoad", mapping);
	}

}
