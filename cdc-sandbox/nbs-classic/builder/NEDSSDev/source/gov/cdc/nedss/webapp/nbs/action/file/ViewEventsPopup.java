package gov.cdc.nedss.webapp.nbs.action.file;

/**
 * Title:        ViewEventsPopup
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xsp file
 * Copyright:    Copyright (c) 2001
 * Company:      SAIC/Leidos
 * @author 		 SAIC/Leidos
 * @version 1.0
 */

import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.entity.person.dt.PersonInvestgationSummaryDT;
import gov.cdc.nedss.entity.person.dt.PersonReportsSummaryDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.bean.WorkupProxyHome;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.vo.WorkupProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;
import gov.cdc.nedss.webapp.nbs.action.localfields.util.LocalFieldGenerator;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DecoratorUtil;
import gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewEventsPopup extends BaseLdf {

	// For logging
	static final LogUtils logger = new LogUtils(ViewEventsPopup.class.getName());
	private static CachedDropDownValues cdv = new CachedDropDownValues();

	

	public static Map<Object, Object> caseMap = new HashMap<Object, Object>();

	/**
	 * Default Constructor
	 */

	public ViewEventsPopup() {
	}

	/**
	 * This method does following: 1. Creates boolean for different permissions
	 * and put them in request object. XSP will use them to display or hide
	 * buttons. 2. Sets forward action classes according to context for all
	 * links. 3. Reads WorkupProxyEKB from back end and sets appropriate values
	 * in request object. 4. Forwards to next page according to context.
	 *
	 * @param ActionMapping
	 *            mapping
	 * @param ActionForm
	 *            aForm
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @return ActionForward
	 * @throws IOException
	 *             , ServletException
	 */

	public ActionForward execute(ActionMapping mapping, ActionForm aForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		WorkupProxyVO workupProxyVO = null;
		ArrayList<Object> vaccineSumArrayList = null;
		ArrayList<Object> labObservationSummaryList = null;
		ArrayList<Object> morbObservationSummaryList = null;
		ArrayList<Object> unprocessedLabObservationSummaryList = null;
		ArrayList<Object> unprocessedMorbObservationSummaryList = null;
		ArrayList<Object> treatmentSumArrayList = null;
		ArrayList<Object> docSummaryColl = null;
		ArrayList<Object> theContactSummaryVOColl = null;
		// Temp Fix for varicella PAM
		request.getSession().removeAttribute("SupplementalInfo");
		// NBSSecurityObj secObj = null;
		HttpSession session = request.getSession(false);

		if (session == null) {
			logger.debug("error no session");
			throw new ServletException("error no session");
		}

		NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");

		request.setAttribute("viewEventsOnly", "true");

		// to check security permission for summary tab
		// boolean checkSummaryPermission =
		// secObj.getPermission(NBSBOLookup.PATIENT,
		// NBSOperationLookup.VIEWWORKUP);
		request.setAttribute("viewSumm", String.valueOf(false));

		// to check security permissions for vaccine tab and also its components
		boolean viewVaccine = secObj.getPermission(
				NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
		request.setAttribute("viewVacc", String.valueOf(viewVaccine));
		if (!viewVaccine) {
			request.setAttribute("viewVaccError", "You do not have access to view the information on this tab");
		}

		boolean editVaccine = secObj.getPermission(
				NBSBOLookup.INTERVENTIONVACCINERECORD,
				NBSOperationLookup.EDIT);
		request.setAttribute("editVacc", String.valueOf(editVaccine));

		request.setAttribute(NEDSSConstants.ADDVACCINE, String.valueOf(false));

		// to check security permissions for investigation tab and also its
		// components
		boolean viewInvestigation = secObj.getPermission(
				NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
		request.setAttribute("viewInves", String.valueOf(viewInvestigation));

		boolean editInvestigation = secObj.getPermission(
				NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT);
		request.setAttribute("editInves", String.valueOf(editInvestigation));

		request.setAttribute(NEDSSConstants.ADDINVS, String.valueOf(false));

		// to check security permissions for observation tab and also its
		// components
		boolean editObservation = secObj.getPermission(
				NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.EDIT);
		request.setAttribute("editObs", String.valueOf(editObservation));

		request.setAttribute(NEDSSConstants.ADDLAB, String.valueOf(false));

		request.setAttribute(NEDSSConstants.ADDMORB, String.valueOf(false));

		boolean viewObservation = secObj.getPermission(
				NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
		request.setAttribute("viewObs", String.valueOf(viewObservation));
		boolean viewMorbity = secObj.getPermission(
				NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
				NBSOperationLookup.VIEW,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
		request.setAttribute("viewMorb", String.valueOf(viewMorbity));

		boolean viewDoc = secObj.getPermission(NBSBOLookup.DOCUMENT,
				NBSOperationLookup.VIEW,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
		request.setAttribute("viewDoc", String.valueOf(viewDoc));

		request.setAttribute(NEDSSConstants.DELETEBUTTON, "false");

		boolean viewTreat = secObj.getPermission(NBSBOLookup.TREATMENT,
				NBSOperationLookup.VIEW,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
		request.setAttribute("viewTreat", String.valueOf(viewTreat));

		request.setAttribute(NEDSSConstants.DELETEBUTTON, "false");

		// to check security permissions for Demographic tab

		boolean bEditPersonButton = secObj.getPermission(NBSBOLookup.PATIENT,
				NBSOperationLookup.EDIT);
		request.setAttribute(NEDSSConstants.EDITBUTTON,
				String.valueOf(bEditPersonButton));

		String fileTab = session.getAttribute("DSFileTab") == null ? null
				: (String) session.getAttribute("DSFileTab");
		if (fileTab != null && fileTab.trim().length() > 0) {
			request.setAttribute("DSFileTab", fileTab);
			session.removeAttribute("DSFileTab");
		} else {
			if (request.getParameter("uid") == null
					&& NBSContext.retrieve(session, "DSFileTab") != null) {
				request.setAttribute("DSFileTab",
						(String) NBSContext.retrieve(session, "DSFileTab"));
				fileTab = (String) NBSContext.retrieve(session, "DSFileTab");
			} else {
				request.setAttribute("DSFileTab", "1");
			}
			// if the control comes back upon closing the contact record popup.
			if (request.getAttribute("ContactTabtoFocus") != null)
				request.setAttribute("DSFileTab", "3");
		}

		if (request.getParameter("uid") != null) {
			request.setAttribute("uid", (String) request.getParameter("uid"));
		} else {
			Long uid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
			request.setAttribute("uid", uid);
		}

		String contextAction = request.getParameter("ContextAction");

		if (contextAction == null) {
			contextAction = (String) request.getAttribute("ContextAction");

			// context management
		}
		// Set up the tab focus in new file
		if (contextAction != null && contextAction.equals("ReturnToFileEvents")) {
			request.setAttribute("focusTab", "tabs0head1");// File Events Tab
		} else {
			request.setAttribute("focusTab", "tabs0head0");// File Summary Tab
		}

		if (fileTab != null && fileTab.equals("2")) {
			request.setAttribute("focusTab", "tabs0head2");// Demographics Tab
		}

		if (request.getAttribute("ContactTabtoFocus") != null) {
			request.setAttribute("focusTab", "tabs0head1");// File Events Tab
		}


		String sCurrTask = null;
		TreeMap<Object, Object> tm = null;
		if (contextAction != null) {
			 tm = NBSContext.getTheContextMap();

			NBSContext.lookInsideTreeMap(tm);
		sCurrTask = NBSContext.getCurrentTask(session);
		String previousPage = NBSContext.getPrevPageID(session);


		// buttons
		request.setAttribute("editButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("EditPatient"));
		request.setAttribute("addInvButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("AddInvestigation"));
		request.setAttribute("addLabButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("AddLab"));
		request.setAttribute("addMorbButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("AddMorb"));
		request.setAttribute("addVacButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("AddVaccination"));

		// links summary
		request.setAttribute("obsLabIdSummaryRef", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ObservationLabIDOnSummary"));
		request.setAttribute("obsMorbIdSummaryRef", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ObservationMorbIDOnSummary"));
		request.setAttribute("investigationIdSummaryRef", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("InvestigationIDOnSummary"));
		// links events
		request.setAttribute("obsLabIdEventRef", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ObservationLabIDOnEvents"));
		request.setAttribute("obsMorbIdEventRef", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ObservationMorbIDOnEvents"));
		request.setAttribute("investigationIdEventRef", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("InvestigationIDOnEvents"));
		request.setAttribute("vacIdEventref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get(NBSConstantUtil.ViewVaccination));
		request.setAttribute("treatmentEventRef", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("TreatmentIDOnEvents"));
		request.setAttribute("documentIDOnEvents", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("DocumentIDOnEvents"));
		request.setAttribute("ContactTracingHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ContactTracing"));
		request.setAttribute("viewHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ViewFile"));
		request.setAttribute("viewFileHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("InvestigationID"));
		}
		try {

			PersonVO personVO = null;
			Long personUID;
			if (request.getParameter("uid") == null || request.getParameter("uid").trim().equals("")) {
				personUID = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
			} else {
				personUID = new Long(request.getParameter("uid").toString());
				NBSContext.store(session, "DSPatientPersonUID", personUID);
			}

			String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
			String sMethod = "getWorkupProxy";
			Object[] oParams = new Object[] { personUID };
			MainSessionHolder holder = new MainSessionHolder();
			MainSessionCommand msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			workupProxyVO = (WorkupProxyVO) arr.get(0);

			// Storing workupProxyVO in the ObjectStore
			personVO = workupProxyVO.getThePersonVO();
			setPermissionsIfPatientNotActive(personVO.getThePersonDT().getRecordStatusCd(), request);
			CompleteDemographicForm pForm = (CompleteDemographicForm) aForm;
			if (request.getParameter("mode") != null && request.getParameter("mode").equals("edit")) {
				pForm.setActionMode("Edit");
			} else {
				pForm.setActionMode("View");
			}
			pForm.setPerson(personVO);
			SearchResultPersonUtil util = new SearchResultPersonUtil();

			ArrayList<Object> InvSumAR = (ArrayList<Object>) workupProxyVO.getTheInvestigationSummaryVOCollection();
			if (InvSumAR != null && InvSumAR.size() > 0) {
				NedssUtils nUtil = new NedssUtils();
				nUtil.sortObjectByColumn("getInvestigationStatusCd", InvSumAR, false);
			}

			this.setInvestigationSummary(InvSumAR, request, NEDSSConstants.SUMMARY, tm, sCurrTask);
			this.setInvestigationSummary(InvSumAR, request, NEDSSConstants.EVENT, tm, sCurrTask);

			logger.debug("VaccinationSummaryList started");

			if (
					workupProxyVO != null
					&& workupProxyVO.getTheVaccinationSummaryVOCollection() != null
					&& workupProxyVO.getTheVaccinationSummaryVOCollection()
							.values() != null) {
				logger.debug(workupProxyVO.getTheVaccinationSummaryVOCollection());
				vaccineSumArrayList = new ArrayList<Object>(workupProxyVO.getTheVaccinationSummaryVOCollection().values());
				this.setVaccinationDTList(vaccineSumArrayList, request, tm, sCurrTask);
			}

			logger.debug("TreatmentSummaryList started");

			labObservationSummaryList = (ArrayList<Object>) workupProxyVO.getTheLabReportSummaryVOCollection();
			morbObservationSummaryList = (ArrayList<Object>) workupProxyVO.getTheMorbReportSummaryVOCollection();
			setMorbidityConditionDescription(morbObservationSummaryList);
			unprocessedLabObservationSummaryList = (ArrayList<Object>) workupProxyVO.getTheUnprocessedLabReportSummaryVOCollection();
			unprocessedMorbObservationSummaryList = (ArrayList<Object>) workupProxyVO.getTheUnprocessedMorbReportSummaryVOCollection();
			setMorbidityConditionDescription(unprocessedMorbObservationSummaryList);

			if (
					workupProxyVO != null
					&& workupProxyVO.getTheTreatmentSummaryVOCollection() != null
					&& workupProxyVO.getTheTreatmentSummaryVOCollection()
							.values() != null) {
				logger.debug(workupProxyVO.getTheTreatmentSummaryVOCollection());
				treatmentSumArrayList = new ArrayList<Object>(workupProxyVO.getTheTreatmentSummaryVOCollection().values());

				request.setAttribute("openTreatmentList", treatmentSumArrayList);
				this.setTreatmentDTList(treatmentSumArrayList, morbObservationSummaryList, request, tm, sCurrTask);
			}

			Collection<PersonReportsSummaryDT> UnprocessedReports = new ArrayList<PersonReportsSummaryDT>();

			if (labObservationSummaryList != null) {
				logger.debug("the if condition is :" + (labObservationSummaryList.size() > 0));
			}

			if (
					(unprocessedLabObservationSummaryList != null && unprocessedLabObservationSummaryList.size() > 0)
					||
					(unprocessedMorbObservationSummaryList != null && unprocessedMorbObservationSummaryList.size() > 0)) {
				UnprocessedReports.addAll(this.getLabReportsDisplayList(unprocessedLabObservationSummaryList,unprocessedMorbObservationSummaryList, request,NEDSSConstants.SUMMARY, tm, sCurrTask));
			}
			if (unprocessedMorbObservationSummaryList != null && unprocessedMorbObservationSummaryList.size() > 0) {
				UnprocessedReports.addAll(this.getMorbReportsDisplayList(unprocessedMorbObservationSummaryList, request, NEDSSConstants.SUMMARY, tm, sCurrTask));
			}
			// Document section is starting
			docSummaryColl = (ArrayList<Object>) workupProxyVO.getTheDocumentSummaryColl();
			ArrayList<Object> unProcessedList = new ArrayList<Object>();
			if (docSummaryColl != null && docSummaryColl.size() > 0) {
				Iterator<Object> ite = docSummaryColl.iterator();
				while (ite.hasNext()) {
					SummaryDT docSummaryDT = (SummaryDT) ite.next();
					if (docSummaryDT.getRecordStatusCd().equals("UNPROCESSED")) {
						unProcessedList.add(docSummaryDT);
					}
				}
				UnprocessedReports.addAll(this.getDocDisplayList(unProcessedList, request, NEDSSConstants.SUMMARY, tm, sCurrTask));
			}

			if (UnprocessedReports.size() != 0) {
				request.setAttribute("reportListSize", new Integer(UnprocessedReports.size()).toString());
			} else {
				request.setAttribute("reportListSize", "0");
			}

			request.setAttribute("unprocessedReports", UnprocessedReports);
			if (docSummaryColl != null) {
				request.setAttribute("eventSummaryDocList", this.getDocDisplayList(docSummaryColl, request, NEDSSConstants.EVENT, tm, sCurrTask));
				request.setAttribute("eventSummaryDocListSize", new Integer(docSummaryColl.size()).toString());
			}

			// Event Tab

			if (
					(labObservationSummaryList != null && labObservationSummaryList.size() > 0)
					||
					(morbObservationSummaryList != null && morbObservationSummaryList.size() > 0)) {
				ArrayList<PersonReportsSummaryDT> eventLabReportList = (ArrayList<PersonReportsSummaryDT>) this
						.getLabReportsDisplayList(labObservationSummaryList, morbObservationSummaryList, request, NEDSSConstants.EVENT, tm, sCurrTask);
				request.setAttribute("eventLabReportList", eventLabReportList);
				request.setAttribute("eventLabReportListSize", eventLabReportList.size() != 0 ? new Integer(eventLabReportList.size()).toString() : "0");
			}

			if (morbObservationSummaryList != null && morbObservationSummaryList.size() > 0) {
				ArrayList<PersonReportsSummaryDT> eventMorbReportList = (ArrayList<PersonReportsSummaryDT>) this.getMorbReportsDisplayList(morbObservationSummaryList, request, NEDSSConstants.EVENT, tm, sCurrTask);
				request.setAttribute("eventMorbReportList", eventMorbReportList);
				request.setAttribute("eventMorbReportListsize", eventMorbReportList.size() != 0 ? new Integer(eventMorbReportList.size()).toString() : "0");
			}

			// contact Named By Patient and contact Named By Patient section is
			// starting
			theContactSummaryVOColl = (ArrayList<Object>) workupProxyVO.getTheContactSummaryVOColl();
			if (theContactSummaryVOColl != null && theContactSummaryVOColl.size() > 0) {
				this.setContactPatientsDTList(theContactSummaryVOColl, request, tm, sCurrTask);
			}

			// process the patient tab
			ArrayList<Object>  stateList = new ArrayList<Object>();

			PersonUtil.convertPersonToRequestObj(personVO, request, "ViewFile",
					stateList);
			PersonUtil.prepareAddressCounties(request, stateList);
			NBSContext.store(session, "DSPatientPersonLocalID", personVO.getThePersonDT().getLocalId());

			// setting request object to tell demographics we are inside workup
			request.setAttribute("workupPage", "true");
			int eventCount = 0;
			if (workupProxyVO.getActiveRevisionUidsColl() != null && workupProxyVO.getActiveRevisionUidsColl().size() > 1) {
				eventCount = workupProxyVO.getActiveRevisionUidsColl().size();
			}
			request.setAttribute("EventCount", eventCount);
			request.setAttribute("personVO", personVO);
			LocalFieldGenerator.loadPatientLDFs(pForm.getPamClientVO(), NEDSSConstants.VIEW_LOAD_ACTION, request, ":");
			util.DisplayInfoforViewFile(personVO, pForm, request, "");
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
			return (mapping.findForward("error"));
		} catch (Exception ex) {
			ex.printStackTrace();
			session.setAttribute("error", "View Events is not available due to error in application" + ex.toString() + "\n: " + ex.getMessage());
			return (mapping.findForward("error"));
		}

		// TransferOwnership Confirmation Msg related stuff
		String confMsg = session.getAttribute("pamTOwnershipConfMsg") == null ? null : (String) session.getAttribute("pamTOwnershipConfMsg");
		if (confMsg != null && confMsg.trim().length() > 0) {
			request.setAttribute("pamTOwnershipMsg", confMsg);
			session.removeAttribute("pamTOwnershipConfMsg");
		}
		String confMsgforDoc = session.getAttribute("docTOwnershipConfMsg") == null ? null : (String) session.getAttribute("docTOwnershipConfMsg");
		if (confMsgforDoc != null && confMsgforDoc.trim().length() > 0) {
			request.setAttribute("docTOwnershipConfMsg", confMsgforDoc);
			session.removeAttribute("docTOwnershipConfMsg");
		}
		String confMsgPage = session.getAttribute("pageTOwnershipConfMsg") == null ? null : (String) session.getAttribute("pageTOwnershipConfMsg");
		if (confMsgPage != null && confMsgPage.trim().length() > 0) {
			request.setAttribute("pageTOwnershipConfMsg", confMsgPage);
			session.removeAttribute("pageTOwnershipConfMsg");
		}

		request.setAttribute("PageTitle", "View Events");

		if ("print".equalsIgnoreCase(contextAction)) {
			request.setAttribute("mode", contextAction);
			return mapping.findForward("printView");
		} else {
			return (mapping.findForward("XSP"));
		}
	}

	private void setMorbidityConditionDescription(
			ArrayList<Object> morbidityList) {
		CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();

		if (morbidityList == null || morbidityList.size() == 0) {
			return;
		}

		try {
			for (int i = 0; i < morbidityList.size(); i++) {
				MorbReportSummaryVO vo = (MorbReportSummaryVO) morbidityList.get(i);
				vo.setConditionDescTxt((String) cachedDropDownValues.getConditionCodes().get(vo.getCondition()));
			}
		} catch (Exception e) {
			logger.error("Error while populating the Morbidity Condition code descriptions" + e);
		}
	}

	public void setInvestigationSummary(
			Collection<Object> investigationSummaryVOCollection,
			HttpServletRequest request, String tabName,
			TreeMap<Object, Object> tm, String sCurrTask) {

		Collection<PersonInvestgationSummaryDT> investigationSummaryList = new ArrayList<PersonInvestgationSummaryDT>();
		Collection<PersonInvestgationSummaryDT> investigationEventList = new ArrayList<PersonInvestgationSummaryDT>();

		if (investigationSummaryVOCollection == null) {
			logger.debug("investigation summary collection arraylist is null");
		} else {
			try {
				Iterator<Object> itr = investigationSummaryVOCollection.iterator();
				while (itr.hasNext()) {
					PersonInvestgationSummaryDT dt = new PersonInvestgationSummaryDT();

					InvestigationSummaryVO investigation = (InvestigationSummaryVO) itr.next();

					if (investigation != null && investigation.getPublicHealthCaseUid() != null) {
						caseMap.put(investigation.getLocalId(), investigation.getPublicHealthCaseUid());
						String str = investigation.getActivityFromTime() == null ? "No Date " : StringUtils.formatDate(investigation.getActivityFromTime());
						dt.setStartDate(str);
						if (investigation.getNotifRecordStatusCd() != null && (investigation.getNotifRecordStatusCd().equalsIgnoreCase(NEDSSConstants.REJECTED) || (investigation.getNotifRecordStatusCd().equalsIgnoreCase(NEDSSConstants.PEND_APPR)))) {
							dt.setNotification("<b><font color=\"brown\">" + investigation.getNotifRecordStatusCd() + "</font></b>");
						} else {
							dt.setNotification((investigation.getNotifRecordStatusCd() == null) ? "" : investigation.getNotifRecordStatusCd());
						}
						String investigatorFirstName = investigation.getInvestigatorFirstName() == null ? "" : investigation.getInvestigatorFirstName();
						String investigatorLastName = investigation.getInvestigatorLastName() == null ? "" : investigation.getInvestigatorLastName();
						dt.setInvestigator(investigatorFirstName + " " + investigatorLastName);
						dt.setConditions((investigation.getConditionCodeText() == null) ? "" : investigation.getConditionCodeText());
						dt.setCaseStatus((investigation.getCaseClassCodeTxt() == null) ? "" : investigation.getCaseClassCodeTxt());
						dt.setJurisdiction((investigation.getJurisdictionDescTxt() == null) ? "" : investigation.getJurisdictionDescTxt());
						dt.setInvestigationId((investigation.getLocalId() == null) ? "" : investigation.getLocalId());
						if (investigation.getInvestigationStatusCd().equals("O")) {
							String status = "<b><font color=\"#006000\">Open</font></b>";
							dt.setStatus(status);
						} else {
							dt.setStatus(investigation.getInvestigationStatusDescTxt());
						}
					}
					if (investigation.getInvestigationStatusDescTxt() != null && investigation.getInvestigationStatusCd().equals("O")) {
						investigationSummaryList.add(dt);
					}
					if(investigation.getCoinfectionId() != null)  dt.setCoinfectionId(investigation.getCoinfectionId());
					investigationEventList.add(dt);

				}
				if (tabName.equals(NEDSSConstants.SUMMARY)) {
					request.setAttribute("investigationSummaryDtList", investigationSummaryList);
					request.setAttribute("investigationSummarySize", investigationSummaryList.size() == 0 ? "0" : new Integer(investigationSummaryList.size()).toString());
				} else {
					request.setAttribute("strInvestigationEventList", investigationEventList);
					request.setAttribute("strInvestigationEventSize", investigationEventList.size() == 0 ? "0" : new Integer(investigationEventList.size()).toString());
				}
			} catch (Exception ex) {
				logger.error("Error in setInvestigationSummary: ", ex);
				ex.printStackTrace();
			}
		}
	}

	@SuppressWarnings("null")
	private Collection<PersonReportsSummaryDT> getLabReportsDisplayList(ArrayList<Object> LabSummaryList, ArrayList<Object> morbSummaryList, HttpServletRequest request, String tabName, TreeMap<Object, Object> tm, String sCurrTask) {
		Collection<PersonReportsSummaryDT> summaryLabReportList = new ArrayList<PersonReportsSummaryDT>();
		if (LabSummaryList == null || LabSummaryList.size() == 0) {
			logger.debug("Observation summary collection arraylist is null");
		} else {
			try {
				Iterator<Object> obsIterator = LabSummaryList.iterator();

				while (obsIterator.hasNext()) {
					logger.debug("Inside iterator.hasNext()...");
					LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO) obsIterator.next();
					PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
					String eventType = null;
					eventType = "Lab Report";
					dt.setEventType(eventType);
					String startDate = labReportSummaryVO.getDateReceived() == null ? "No Date" : StringUtils.formatDate(labReportSummaryVO.getDateReceived());
					if (tabName.equals(NEDSSConstants.EVENT)) {
						startDate = startDate + "<br>" + StringUtils.formatDatewithHrMin(labReportSummaryVO.getDateReceived());
					} else {
						startDate = startDate + "<br>" + StringUtils.formatDatewithHrMin(labReportSummaryVO.getDateReceived());
					}
					dt.setDateReceived(startDate);

					// Append Electronic Ind
					if (labReportSummaryVO.getElectronicInd() != null && labReportSummaryVO.getElectronicInd().equals("Y")) {
						dt.setEventType(eventType + "<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
						if (tabName.equals(NEDSSConstants.EVENT)) {
							dt.setDateReceived(startDate + "<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
						}
					}
					String provider = this.getProviderFullName(
							labReportSummaryVO.getProviderPrefix(),
							labReportSummaryVO.getProviderFirstName(),
							labReportSummaryVO.getProviderLastName(),
							labReportSummaryVO.getProviderSuffix());
					provider = provider == null ? "" : "<b>Ordering Provider:</b><br>" + provider;
					
					String orderingFacility =  labReportSummaryVO.getOrderingFacility() == null ? "" : "<b>Ordering Facility:</b><br>" + labReportSummaryVO.getOrderingFacility();;

					String facility = labReportSummaryVO.getReportingFacility() == null ? "" : "<b>Reporting Facility:</b><br>" + labReportSummaryVO.getReportingFacility();
					dt.setProviderFacility(facility + "<br>" + provider + "<br>" + orderingFacility);
					String dateCollected = null;
					if (tabName.equals(NEDSSConstants.SUMMARY)) {
						dateCollected = labReportSummaryVO.getDateCollected() == null ? "No Date" : "<b>Date Collected:</b><br>" + StringUtils.formatDate(labReportSummaryVO.getDateCollected());
					} else {
						dateCollected = labReportSummaryVO.getDateCollected() == null ? "No Date" : StringUtils.formatDate(labReportSummaryVO.getDateCollected());
					}
					dt.setDateCollected(dateCollected);
					dt.setDescription(DecoratorUtil.getResultedTestsStringForWorup(labReportSummaryVO.getTheResultedTestSummaryVOCollection()));
					String URL = null;
					URL = "replaceLocalID";
					dt.setAssociatedWith(this.getAssociatedString(labReportSummaryVO.getAssociationsMap(), URL));
					dt.setEventId(labReportSummaryVO.getLocalId() == null ? "" : labReportSummaryVO.getLocalId());
					dt.setJurisdiction(labReportSummaryVO.getJurisdiction());

					summaryLabReportList.add(dt);
				} // while
			} catch (Exception ex) {
				logger.error("Error in getLabReportsDisplayList: ", ex);
				ex.printStackTrace();
			}
		}

		return summaryLabReportList;
	} // setLabReports

	private Collection<PersonReportsSummaryDT> getMorbReportsDisplayList(
			ArrayList<Object> morbSummaryList, HttpServletRequest request,
			String tabName, TreeMap<Object, Object> tm, String sCurrTask) {
		Collection<PersonReportsSummaryDT> summaryMorbReportList = new ArrayList<PersonReportsSummaryDT>();
		if (morbSummaryList == null) {
			logger.debug("Observation summary collection arraylist is null");
		} else {
			try {
				Iterator<Object> obsIterator = morbSummaryList.iterator();

				while (obsIterator.hasNext()) {

					MorbReportSummaryVO morbReportSummaryVO = (MorbReportSummaryVO) obsIterator.next();
					if (morbReportSummaryVO.getRecordStatusCd() != null && !morbReportSummaryVO.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.OBS_MORB_PROCESS)) {
						PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
						String eventType = null;
						eventType = "Morb Report";
						dt.setEventType(eventType);
						String startDate = morbReportSummaryVO.getDateReceived() == null ? "No Date" : StringUtils.formatDate(morbReportSummaryVO.getDateReceived());
						startDate = startDate + "<br>" + StringUtils.formatDatewithHrMin(morbReportSummaryVO.getDateReceived());
						dt.setDateReceived(startDate);

						String provider = this.getProviderFullName(
								morbReportSummaryVO.getProviderPrefix(),
								morbReportSummaryVO.getProviderFirstName(),
								morbReportSummaryVO.getProviderLastName(),
								morbReportSummaryVO.getProviderSuffix());
						if (tabName.equals(NEDSSConstants.SUMMARY)) {
							dt.setProviderFacility((provider == null ? "" : "<b>Provider:</b><br>" + provider));
						} else {
							dt.setProviderFacility((provider == null ? "" : provider));
						}
						String dateCollected = null;
						if (tabName.equals(NEDSSConstants.SUMMARY)) {
							dateCollected = morbReportSummaryVO.getReportDate() == null ? "No Date" : "<b>Report Date:</b><br>" + StringUtils.formatDate(morbReportSummaryVO.getReportDate());
						} else {
							dateCollected = morbReportSummaryVO.getReportDate() == null ? "No Date" : StringUtils.formatDate(morbReportSummaryVO.getReportDate());
						}
						dt.setDateCollected(dateCollected);
						dt.setDescription(morbReportSummaryVO.getConditionDescTxt() == null ? "" : "<b>" + morbReportSummaryVO.getConditionDescTxt() + "</b>");
						dt.setEventId(morbReportSummaryVO.getLocalId() == null ? "" : morbReportSummaryVO.getLocalId());
						dt.setJurisdiction(morbReportSummaryVO.getJurisdiction());

						String URL = null;
						URL = "replaceLocalID";
						dt.setAssociatedWith(this.getAssociatedString(morbReportSummaryVO.getAssociationsMap(), URL));
						StringBuffer desc = new StringBuffer(dt.getDescription());
						// Lab reports created within morb report
						boolean flag = true;
						if ((morbReportSummaryVO.getTheLabReportSummaryVOColl() != null && morbReportSummaryVO.getTheLabReportSummaryVOColl().size() > 0)) {
							ArrayList<Object> labFromMorbList = (ArrayList<Object>) morbReportSummaryVO.getTheLabReportSummaryVOColl();
							if (labFromMorbList == null || labFromMorbList.size() == 0) {
								logger.debug("Observation summary collection arraylist is null");
							} else {
								Iterator<Object> labIterator = labFromMorbList.iterator();
								while (labIterator.hasNext()) {
									flag = false;
									LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO) labIterator.next();
									desc.append(DecoratorUtil.getResultedTestsStringForWorup(labReportSummaryVO.getTheResultedTestSummaryVOCollection()));
								}// While
								dt.setDescription(desc.toString());
							}
						}
						// Treatments created within morb report
						if ((morbReportSummaryVO.getTheTreatmentSummaryVOColl() != null && morbReportSummaryVO.getTheTreatmentSummaryVOColl().size() > 0)) {
							ArrayList<Object> treatmentFromMorbList = (ArrayList<Object>) morbReportSummaryVO.getTheTreatmentSummaryVOColl();

							if (treatmentFromMorbList == null || treatmentFromMorbList.size() == 0) {
								logger.debug("Observation summary collection arraylist is null");
							} else {
								NedssUtils nUtil = new NedssUtils();
								nUtil.sortObjectByColumn("getCustomTreatmentNameCode", morbReportSummaryVO.getTheTreatmentSummaryVOColl(), true);
								if (flag) {
									desc.append("<br>");
								}
								desc.append("<b>Treatment Info:</b><UL>");
								Iterator<Object> treatmentIterator = treatmentFromMorbList.iterator();
								while (treatmentIterator.hasNext()) {
									logger.debug("Inside iterator.hasNext()...");
									TreatmentSummaryVO treatment = (TreatmentSummaryVO) treatmentIterator.next();
									desc.append(treatment.getCustomTreatmentNameCode() == null ? "" : "<LI><b>" + treatment.getCustomTreatmentNameCode() + "</b></LI>");
								}// While
								desc.append("<UL>");
								dt.setDescription(desc.toString());
							}
						}
						summaryMorbReportList.add(dt);
					}
				} // while
			} catch (Exception ex) {
				logger.error("Error in getMorbReportsDisplayList: ", ex);
				ex.printStackTrace();
			}
		}
		return summaryMorbReportList;
	}

	private Collection<PersonReportsSummaryDT> getDocDisplayList(
			ArrayList<Object> docSummaryList, HttpServletRequest request,
			String tabName, TreeMap<Object, Object> tm, String sCurrTask) {

		Collection<PersonReportsSummaryDT> summaryDocList = new ArrayList<PersonReportsSummaryDT>();

		if (docSummaryList == null) {
			logger.debug("Document summary collection arraylist is null");
		} else {
			try {
				Iterator<Object> docIterator = docSummaryList.iterator();

				while (docIterator.hasNext()) {
					SummaryDT docSummaryDT = (SummaryDT) docIterator.next();
					PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
					if (docSummaryDT.getDocType() != null && docSummaryDT.getDocType().length() > 0) {
						docSummaryDT.setDocTypeConditionDescTxt(cdv.getCodeShortDescTxt(docSummaryDT.getDocType(), "PUBLIC_HEALTH_EVENT"));
					}
					if (docSummaryDT.getDocPurposeCd() != null && docSummaryDT.getDocPurposeCd().length() > 0) {
						docSummaryDT.setDocPurposeCdConditionDescTxt(cdv.getCodeShortDescTxt(docSummaryDT.getDocPurposeCd(), "NBS_DOC_PURPOSE"));
					}
					if (docSummaryDT.getDocStatusCd() != null && docSummaryDT.getDocStatusCd().length() > 0) {
						docSummaryDT.setDocStatusCdConditionDescTxt(cdv.getCodeShortDescTxt(docSummaryDT.getDocStatusCd(), "NBS_DOC_STATUS"));
					}
					String eventType = "Case Report";
					dt.setEventType(eventType);
					String startDate = docSummaryDT.getAddTime() == null ? "No Date" : StringUtils.formatDate(docSummaryDT.getAddTime());
					if (tabName.equals(NEDSSConstants.EVENT)) {
						startDate = startDate + "<br>" + StringUtils.formatDatewithHrMin(docSummaryDT.getAddTime());
					} else {
						startDate = startDate + "<br>" + StringUtils.formatDatewithHrMin(docSummaryDT.getAddTime());
					}
					dt.setDateReceived(startDate);
					dt.setDescription(docSummaryDT.getCdDescTxt() == null ? "" : "<b>" + docSummaryDT.getCdDescTxt());
					if (tabName.equals(NEDSSConstants.SUMMARY)) {
						dt.setProviderFacility(docSummaryDT.getSendingFacilityNm() == null ? "" : "<b>Sending Facility</b><br>" + docSummaryDT.getSendingFacilityNm());
					} else {
						dt.setProviderFacility(docSummaryDT.getSendingFacilityNm() == null ? "" : docSummaryDT.getSendingFacilityNm());
					}
					String dateCollected = null;
					if (tabName.equals(NEDSSConstants.SUMMARY)) {
						dateCollected = docSummaryDT.getAddTime() == null ? "No Date" : "<b>Date Reported:</b><br>" + StringUtils.formatDate(docSummaryDT.getAddTime());
					} else {
						dateCollected = docSummaryDT.getAddTime() == null ? "No Date" : StringUtils.formatDate(docSummaryDT.getAddTime());
					}
					dt.setDateCollected(dateCollected);
					String URL = null;
					URL = "replaceLocalID";

					dt.setAssociatedWith(this.getAssociatedString(docSummaryDT.getAssociationMap(), URL));
					dt.setEventId(docSummaryDT.getLocalId() == null ? "" : docSummaryDT.getLocalIdForUpdatedAndNewDoc());

					summaryDocList.add(dt);
				} // while
			} catch (Exception ex) {
				logger.error("Error in getDocDisplayList: ", ex);
				ex.printStackTrace();
			}

		}
		return summaryDocList;
	} // getDocDisplayList

	private void setVaccinationDTList(
			Collection<Object> vaccinationSummaryVOColl,
			HttpServletRequest request, TreeMap<Object, Object> tm,
			String sCurrTask) {

		Collection<PersonReportsSummaryDT> eventVaccinationDTList = new ArrayList<PersonReportsSummaryDT>();

		if (vaccinationSummaryVOColl == null) {
			logger.debug("vaccination summary collection arraylist is null");
		} else {
			try {
				Iterator<Object> itr = vaccinationSummaryVOColl.iterator();

				while (itr.hasNext()) {

					VaccinationSummaryVO vaccination = (VaccinationSummaryVO) itr.next();

					if (vaccination != null && vaccination.getInterventionUid() != null) {
						PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
						String startDate = vaccination.getCreateDate() == null ? "No Date" : StringUtils.formatDate(vaccination.getCreateDate());
						startDate = startDate + "<br>" + StringUtils.formatDatewithHrMin(vaccination.getCreateDate());
						dt.setDateReceived(startDate);
						String provider = this.getProviderFullName(vaccination.getProviderPrefix(), vaccination.getProviderFirstName(), vaccination.getProviderLastName(), vaccination.getProviderSuffix());
						dt.setProviderFacility((provider == null ? "" : provider));

						String dateCollected = vaccination.getActivityFromTime() == null ? "No Date" : StringUtils.formatDate(vaccination.getActivityFromTime());
						dt.setDateCollected(dateCollected);

						dt.setDescription(vaccination.getVaccineAdministered() == null ? "" : "<b>" + vaccination.getVaccineAdministered());
						String URL = "replaceLocalID";
						dt.setAssociatedWith(this.getAssociatedString(vaccination.getAssociationsMap(), URL));
						dt.setEventId(vaccination.getLocalId());
						eventVaccinationDTList.add(dt);
					}
				}
			} catch (Exception ex) {
				logger.error("Error in getVaccinationDTList: ", ex);
				ex.printStackTrace();
			}
		}

		request.setAttribute("eventVaccinationDTList", eventVaccinationDTList);
		request.setAttribute("eventVaccinationDTListSize", new Integer(eventVaccinationDTList.size()).toString());
	}

	private void setTreatmentDTList(Collection<Object> treatmentSummaryVOColl,
			Collection<Object> morbSummaryVOColl, HttpServletRequest request,
			TreeMap<Object, Object> tm, String sCurrTask) {

		Collection<PersonReportsSummaryDT> eventTreatmentDTList = new ArrayList<PersonReportsSummaryDT>();

		if (treatmentSummaryVOColl == null) {
			logger.debug("treatment summary collection arraylist is null");
		} else {
			try {
				Iterator<Object> itr = treatmentSummaryVOColl.iterator();
				while (itr.hasNext()) {

					TreatmentSummaryVO treatment = (TreatmentSummaryVO) itr.next();

					if (treatment != null && treatment.getTreatmentUid() != null) {
						PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
						String startDate = treatment.getCreateDate() == null ? "No Date" : StringUtils.formatDate(treatment.getCreateDate());

						startDate = startDate + "<br>" + StringUtils.formatDatewithHrMin(treatment.getCreateDate());
						dt.setDateReceived(startDate);
						String dateCollected = treatment.getActivityFromTime() == null ? "No Date" : StringUtils.formatDate(treatment.getActivityFromTime());
						dt.setDateCollected(dateCollected);
						String provider = this.getProviderFullName(
								treatment.getProviderPrefix(),
								treatment.getProviderFirstName(),
								treatment.getProviderLastName(),
								treatment.getProviderSuffix());
						dt.setProviderFacility((provider == null ? "" : provider));
						String URL = "replaceLocalID";

						dt.setAssociatedWith(this.getAssociatedString(treatment.getAssociationMap(), URL));
						dt.setDescription(treatment.getCustomTreatmentNameCode() == null ? "" : "<b>" + treatment.getCustomTreatmentNameCode());
						dt.setEventId(treatment.getLocalId() == null ? "" : treatment.getLocalId());
						eventTreatmentDTList.add(dt);
					}
				}

			} catch (Exception ex) {
				logger.error("Error in getTreatmentDTList: ", ex);
				ex.printStackTrace();
			}
		}
		request.setAttribute("eventTreatmentDTList", eventTreatmentDTList);
		request.setAttribute("eventTreatmentDTListSize", new Integer(eventTreatmentDTList.size()).toString());
	}

	private void setContactPatientsDTList(
			Collection<Object> contactSummaryVOColl,
			HttpServletRequest request, TreeMap<Object, Object> tm,
			String sCurrTask) {

		CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
		Map<Object, Object> contactNamedByPatDTListMap = new HashMap<Object, Object>();
		Map<Object, Object> patNamedByContactDTListMap = new HashMap<Object, Object>();
		int i = 0;
		if (contactSummaryVOColl == null) {
			logger.debug("contact summary named by patient collection  is null");
		} else {
			try {
				Iterator<Object> itr = contactSummaryVOColl.iterator();
				while (itr.hasNext()) {
					CTContactSummaryDT contactsumDT = (CTContactSummaryDT) itr.next();
					PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
					if (contactsumDT != null && contactsumDT.isContactNamedByPatient()) {
						String startDate = contactsumDT.getCreateDate() == null ? "No Date" : StringUtils.formatDate(contactsumDT.getCreateDate());
						startDate = startDate + "<br>" + StringUtils.formatDatewithHrMin(contactsumDT.getCreateDate());
						dt.setCreateDate(startDate);

						String priority = (contactsumDT.getPriorityCd() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_PRIORITY, contactsumDT.getPriorityCd());

						String disposition = (contactsumDT.getDispositionCd() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_DISPOSITION, contactsumDT.getDispositionCd());
						String cdDescTxt = contactsumDT.getContactPhcCd() == null ? "" : cachedDropDownValues.getConditionDesc(contactsumDT.getContactPhcCd());
						dt.setDescription(getContactRecordDesc(cdDescTxt, priority, disposition));
						dt.setDateNamed(contactsumDT.getNamedOnDate() == null ? "No Date" : StringUtils.formatDate(contactsumDT.getNamedOnDate()));
						String name = contactsumDT.getName() == null ? "" : contactsumDT.getName();
						dt.setNamedBy(name);
						String publicHealthLocalId = contactsumDT.getContactPhcLocalId() == null ? "" : contactsumDT.getContactPhcLocalId();
						String conditiondDesc = contactsumDT.getContactPhcCd() == null ? "" : cachedDropDownValues.getConditionDesc(contactsumDT.getContactPhcCd());
						dt.setAssociatedWith(publicHealthLocalId.equals("") ? "" : publicHealthLocalId + "<br><b>" + conditiondDesc + "</b>");
						dt.setEventId(contactsumDT.getLocalId());
						if (contactNamedByPatDTListMap.get(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd())) != null) {
							((ArrayList<Object>) contactNamedByPatDTListMap.get(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd()))).add(dt);
						} else {
							ArrayList<Object> list = new ArrayList<Object>();
							list.add(dt);
							contactNamedByPatDTListMap.put(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd()), list);
						}
					}
					if (contactsumDT != null && contactsumDT.isPatientNamedByContact()) {
						String startDate = contactsumDT.getCreateDate() == null ? "No Date" : StringUtils.formatDate(contactsumDT.getCreateDate());
						startDate = startDate + "<br>" + StringUtils.formatDatewithHrMin(contactsumDT.getCreateDate());
						dt.setCreateDate(startDate);

						String priority = (contactsumDT.getPriorityCd() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_PRIORITY, contactsumDT.getPriorityCd());

						String disposition = (contactsumDT.getDispositionCd() == null) ? "" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_DISPOSITION, contactsumDT.getDispositionCd());
						String cdDescTxt = contactsumDT.getSubjectPhcCd() == null ? "" : cachedDropDownValues.getConditionDesc(contactsumDT.getSubjectPhcCd());
						dt.setDescription(getContactRecordDesc(cdDescTxt, priority, disposition));
						dt.setDateNamed(contactsumDT.getNamedOnDate() == null ? "No Date" : StringUtils.formatDate(contactsumDT.getNamedOnDate()));
						String name = contactsumDT.getNamedBy() == null ? "" : contactsumDT.getNamedBy();
						dt.setNamedBy(name);
						String publicHealthLocalId = contactsumDT.getSubjectPhcLocalId() == null ? "" : contactsumDT.getSubjectPhcLocalId();
						String conditiondDesc = contactsumDT.getSubjectPhcCd() == null ? "" : cachedDropDownValues.getConditionDesc(contactsumDT.getSubjectPhcCd());
						dt.setAssociatedWith(publicHealthLocalId.equals("") ? "" : publicHealthLocalId + "<br><b>" + conditiondDesc + "</b>");
						dt.setEventId(contactsumDT.getLocalId());
						if (patNamedByContactDTListMap.get(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd())) != null) {
							((ArrayList<Object>) patNamedByContactDTListMap.get(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd()))).add(dt);
						} else {
							ArrayList<Object> list = new ArrayList<Object>();
							list.add(dt);
							patNamedByContactDTListMap.put(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd()), list);
						}
					}
					i++;
				}
			} catch (Exception ex) {
				logger.error("Error in setContactPatientsDTList: ", ex);
				ex.printStackTrace();
			}
		}
		request.setAttribute("contactNamedByPatListMap", contactNamedByPatDTListMap);
		request.setAttribute("patNamedByContactsListMap", patNamedByContactDTListMap);
		request.setAttribute("contactDTListSize", new Integer(i).toString());
	}

	public String getAssociatedString(Map<Object, Object> associationMap, String URL) {
		StringBuffer sb = new StringBuffer("");
		CachedDropDownValues cddv = new CachedDropDownValues();
		if (associationMap != null && associationMap.size() > 0) {
			Iterator<Object> ite = associationMap.keySet().iterator();
			while (ite.hasNext()) {
				String caseId = (String) ite.next();
				if(caseId!=null && caseMap!=null && caseMap.get(caseId)!=null){
					String URL1 = URL.replaceAll("replaceUID", ((Long) caseMap.get(caseId)).toString());
					String URL2 = URL1.replaceAll("replaceLocalID", caseId);
					sb.append(URL2 + "<br>");
					sb.append("<b>" + cddv.getConditionDesc((String) associationMap.get(caseId)) + "</b><br>");
				}
			}
		}
		return sb.toString();
	}

	public String getProviderFullName(String prefix, String firstNm, String lastNm, String sufix) {
		StringBuffer sb = new StringBuffer("");
		if (prefix != null && !prefix.equals("")) {
			sb.append(prefix).append(" ");
		}
		if (firstNm != null && !firstNm.equals("")) {
			sb.append(firstNm).append(" ");
		}
		if (lastNm != null && !lastNm.equals("")) {
			sb.append(lastNm).append(" ");
		}
		if (sufix != null && !sufix.equals("")) {
			sb.append(sufix).append(" ");
		}
		if (sb.toString().trim().equals(""))
			return null;
		else
			return sb.toString();
	}

	public String getContactRecordDesc(String condition, String priority, String disposition) {
		StringBuffer sb = new StringBuffer("");
		if (condition != null && !condition.trim().equals("")) {
			sb.append("<b>").append(condition).append("</b><br>");
		}
		if (priority != null && !priority.trim().equals("")) {
			sb.append("<b>Priority:</b> ").append(priority).append("<br>");
		}
		if (disposition != null && !disposition.trim().equals("")) {
			sb.append("<b>Disposition:</b> ").append(disposition);
		}
		return sb.toString();
	}

	public void setPermissionsIfPatientNotActive(String recordStatusCd, HttpServletRequest request) {
		if (recordStatusCd == null) {
			return;
		} else {
			if (recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_INACTIVE) ||
					recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE) ||
					recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_SUPERCEDED)) {
				request.setAttribute(NEDSSConstants.EDITBUTTON,String.valueOf(false));
				request.setAttribute(NEDSSConstants.DELETEBUTTON, String.valueOf(false));
				request.setAttribute(NEDSSConstants.ADDLAB, String.valueOf(false));
				request.setAttribute(NEDSSConstants.ADDMORB, String.valueOf(false));
				request.setAttribute(NEDSSConstants.ADDINVS, String.valueOf(false));
				request.setAttribute(NEDSSConstants.ADDVACCINE, String.valueOf(false));
				request.setAttribute(NEDSSConstants.ADDLAB, String.valueOf(false));
				if (recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE)) {
					recordStatusCd = NEDSSConstants.RECORD_STATUS_INACTIVE;
				}
				request.setAttribute(NEDSSConstants.RECORDSTATUSCD, "<b><font color=\"#990000\">" + recordStatusCd + "</b></font>");
			}
		}
	}
}
