package gov.cdc.nedss.webapp.nbs.action.investigation.hepatitis;

/**
 * 
 * <p>
 * Title: HepatitisCreateLoad
 * </p>
 * <p>
 * Description: This is a load action class for create investigation for the
 * group of Hepatitis conditions.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Computer Science Corporation
 * </p>
 * 
 * @author Shailender Rachamalla
 */

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.bmird.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.generic.GenericInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.measles.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.crs.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.pertussis.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.rubella.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

public class HepatitisCreateLoad extends BaseCreateLoad {

	static final LogUtils logger = new LogUtils(HepatitisCreateLoad.class
			.getName());

	public HepatitisCreateLoad() {
	}

	/**
	 * Process the specified HTTP request, and create the corresponding HTTP
	 * response (or forward to another web component that will create it).
	 * Return an ActionForward instance describing where and how control should
	 * be forwarded, or null if the response has already been completed.
	 * 
	 * @param mapping -
	 *            The ActionMapping used to select this instance
	 * @param form -
	 *            The ActionForm bean for this request (if any)
	 * @param request -
	 *            The HTTP request we are processing
	 * @param response -
	 *            The HTTP response we are creating
	 * @return mapping.findForward("XSP") -- ActionForward instance describing
	 *         where and how control should be forwarded
	 * @throws IOException --
	 *             if an input/output error occurs
	 * @throws ServletException --
	 *             if a servlet exception occurs
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		//context
		String sContextAction = request.getParameter("ContextAction");
		if (sContextAction == null) {
			session.setAttribute("error",
					"No Context Action in InvestigationCreateLoad");
			throw new ServletException("No Context Action in InvestigationCreateLoad");
		}

		return this.createInvestigation(mapping, form, request, session);
	}

	private ActionForward createInvestigation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpSession session)
			throws ServletException {
		String sContextAction = request.getParameter("ContextAction");
		InvestigationForm investigationForm = (InvestigationForm) form;
		//if it is DiagnosedCondition do not reset form, and now diagnosis is
		// different action class
		//if (!(sContextAction.equals("DiagnosedCondition")))
		investigationForm.reset();

		String conditionCd = (String) NBSContext.retrieve(session,
				NBSConstantUtil.DSInvestigationCondition);
		String programArea = (String) NBSContext.retrieve(session,
				NBSConstantUtil.DSInvestigationProgramArea);
		ProgramAreaVO programAreaVO = null;
		String investigationFormCd = null;

		CachedDropDownValues cdv = new CachedDropDownValues();
		boolean viewContactTracing = false;
		String sCurrentTask = "";
		Long mprUid = null;
		try {
			programAreaVO = cdv.getProgramAreaConditionLevel(programArea,
					conditionCd);

			conditionCd = programAreaVO.getConditionCd();
			investigationFormCd = programAreaVO.getInvestigationFormCd();

			request.setAttribute("conditionCd", programAreaVO.getConditionCd());
			request.setAttribute("conditionCdDescTxt", programAreaVO
					.getConditionShortNm());
			request.setAttribute("programAreaCd", programAreaVO
					.getStateProgAreaCode());
			request.setAttribute("programAreaDescTxt", programAreaVO
					.getStateProgAreaCdDesc());
			request.setAttribute("sharedIndicator", "T");
			NBSContext.store(session, NBSConstantUtil.DSInvestigationCode,
					investigationFormCd);

			//security stuff
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			boolean checkCreatePermission = super.setSecurityForCreateLoad(request,
					nbsSecurityObj, programAreaVO);
			if (!checkCreatePermission) {
				logger.error("You do not have access to Create Investigation");
				session.setAttribute("error",
						"You do not have access to Create Investigation");

				return (mapping.findForward("error"));
			}

			InvestigationProxyVO investigationProxyVO = investigationForm
					.getProxy();
			if (investigationProxyVO == null) {
				investigationProxyVO = new InvestigationProxyVO();
			}
			// use the new API to retrieve custom field collection
			// to handle multiselect fields (xz 01/11/2005)
			Collection<Object>  coll = extractLdfDataCollection(investigationForm, request);
			if (coll != null) {
				investigationProxyVO.setTheStateDefinedFieldDataDTCollection(coll);
			}
			String businessObjNm = cdv.getLDFMap(conditionCd);

			if (investigationFormCd.equalsIgnoreCase("INV_FORM_HEPGEN"))
				businessObjNm = NEDSSConstants.INVESTIGATION_HEP_LDF;

			request.setAttribute("showConditionSpecificLDF", new Boolean(false));
			super.createXSP(businessObjNm, investigationProxyVO, null, request);

			mprUid = (Long) NBSContext.retrieve(session,
					NBSConstantUtil.DSPersonSummary);
			PersonVO personVO = null;

			if (mprUid != null) {
				personVO = super.findMasterPatientRecord(mprUid, session,
						nbsSecurityObj);
				super
				.createXSP(NEDSSConstants.PATIENT_LDF, personVO, null,
						request);
				ArrayList<Object> stateList = new ArrayList<Object> ();
				PersonUtil.convertPersonToRequestObj(personVO, request,
						"AddPatientFromEvent", stateList);

			}

			if (investigationFormCd == null) {
				logger.error(" could not get investigationFormCd");
				session.setAttribute("error",
						"ConditionCd or InvestigationFormCd is null");

				return (mapping.findForward("error"));
			}

			super.setJurisdictionForCreate(request, nbsSecurityObj, programAreaVO);
			//filter DiagnosedConditions based on programAreas
			String programAreaFromSecurityObj = WumUtil
					.getProgramAreaFromSecurityObj(nbsSecurityObj);
			String filterDiagnosisCondition = cdv
					.getDiagnosisCodeFilteredOnPA(programAreaFromSecurityObj);
			request.setAttribute("filterDiagnosisCondition",
					filterDiagnosisCondition);

			//  CONTEXT
			sCurrentTask = super.setContextForCreate(request, session);

			if (sCurrentTask.equals("CreateInvestigation1")) {
				String strJurisdiction = personVO.getDefaultJurisdictionCd();
				request.setAttribute("jurisdiction", strJurisdiction);
			}

			if (sCurrentTask.equals("CreateInvestigation2")
					|| sCurrentTask.equals("CreateInvestigation3")
					|| sCurrentTask.equals("CreateInvestigation4")
					|| sCurrentTask.equals("CreateInvestigation5")
					|| sCurrentTask.equals("CreateInvestigation6")
					|| sCurrentTask.equals("CreateInvestigation7")
					|| sCurrentTask.equals("CreateInvestigation8")
					|| sCurrentTask.equals("CreateInvestigation9")) {
				String jurisdiction = (String) NBSContext.retrieve(session,
						NBSConstantUtil.DSInvestigationJurisdiction);
				request.setAttribute("jurisdiction", jurisdiction);
			}
			if (sCurrentTask.equals("CreateInvestigation5")
					|| sCurrentTask.equals("CreateInvestigation6")
					|| sCurrentTask.equals("CreateInvestigation7")
					|| sCurrentTask.equals("CreateInvestigation8")) {
				//this is from morb and for generic investigation only
				TreeMap<Object, Object> DSMorbMap = (TreeMap<Object, Object>) NBSContext.retrieve(session,
						NBSConstantUtil.DSMorbMap);
				this.prepopulateMorbValuesHep(DSMorbMap, request);
			}
			if (sCurrentTask.equals("CreateInvestigation2")
					||sCurrentTask.equals("CreateInvestigation3")
					|| sCurrentTask.equals("CreateInvestigation4")
					|| sCurrentTask.equals("CreateInvestigation9")) {
				//this is from lab and for generic investigation only
				TreeMap<Object, Object> labMap = (TreeMap<Object, Object>) NBSContext.retrieve(session,
						NBSConstantUtil.DSLabMap);
				this.prepopulateMorbValuesHep(labMap, request);
			}
			if( sCurrentTask.equals("CreateInvestigation10")|| sCurrentTask.equals("CreateInvestigation11") && NBSContext.retrieve(request.getSession(),"DSDocumentUID")!=null){
				Long DSDocumentUID=(Long)NBSContext.retrieve(request.getSession(),"DSDocumentUID");
				Map<Object, Object> map = InvestigationUtil.getPublicHealthCaseAndObsColl(DSDocumentUID, request);
				PublicHealthCaseVO publicHealthCaseVO= (PublicHealthCaseVO)map.get("PHCVO");
				Collection<ObservationVO>  obsColl = (ArrayList<ObservationVO> )map.get("OBSERVATIONCOLLECTION");
				investigationProxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
				if(obsColl!=null)
					investigationProxyVO.setTheObservationVOCollection(obsColl);
				GenericInvestigationUtil genericInvestigationUtil = new GenericInvestigationUtil();
				genericInvestigationUtil.convertPublicHealthCaseToRequest(investigationProxyVO, request);
				genericInvestigationUtil.convertObservationsToRequest(investigationProxyVO, request);
				publicHealthCaseVO.getThePublicHealthCaseDT().setProgAreaCd( programAreaVO.getStateProgAreaCode());
				publicHealthCaseVO.getThePublicHealthCaseDT().setCd(programAreaVO.getConditionCd());
				request.setAttribute("conditionCd", programAreaVO.getConditionCd());
				request.setAttribute("conditionCdDescTxt", programAreaVO.getConditionShortNm());
				request.setAttribute("programAreaCd", programAreaVO.getStateProgAreaCode());
				request.setAttribute("programAreaDescTxt", programAreaVO.getStateProgAreaCdDesc());
				request.setAttribute("sharedIndicator", "T");
				investigationForm.getProxy().setPublicHealthCaseVO(null);
				investigationForm.getProxy().setTheObservationVOCollection(null);
			}
				       
			String ContactTracingByConditionCd = GenericInvestigationUtil.getConditionTracingEnableInd(conditionCd);

			if(ContactTracingByConditionCd.equalsIgnoreCase(NEDSSConstants.CONTACT_TRACING_ENABLE_IND))
				viewContactTracing = true;
			else
				viewContactTracing = false;
			if(viewContactTracing)
				viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
						NBSOperationLookup.VIEW);
		}catch (Exception e) {
			logger.error("Exception in Hepatitis Create Load: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while Hepatitis Create Load : "+e.getMessage());
		}        
		
		return (this.getForwardPage(mprUid, investigationFormCd, sCurrentTask,
				sContextAction, request, viewContactTracing));
	}

	/**
	 * this method returns ActionForward with the actual struts mapping of XSP
	 * page
	 * 
	 * @param mprUid
	 *            :Long
	 * @param investigationFormCd :
	 *            String
	 * @param sCurrentTask :
	 *            String
	 * @param sContextAction :
	 *            String
	 * @param request :
	 *            HttpServletRequst
	 * @return ActionForward
	 */
	private ActionForward getForwardPage(Long mprUid,
			String investigationFormCd, String sCurrentTask,
			String sContextAction, HttpServletRequest request, boolean viewContactTracing) {
		/*
		 * System.out.println("in getForwardPage investigationFormCd: " +
		 * investigationFormCd + " sCurrentTask: " + sCurrentTask + "
		 * sContextAction: " + sContextAction);
		 */

		String path = "/error";

		if (NBSConstantUtil.INV_FORM_HEPGEN.equals(investigationFormCd)&& viewContactTracing) {
			// disdplaying error msg from NBSErrorMap.xml file

			path = "/diseaseform/hepatitis/hep_core_create?CurrentTask="
					+ sCurrentTask + "&ContextAction=" + sContextAction;
		}else if(NBSConstantUtil.INV_FORM_HEPGEN.equals(investigationFormCd)&& !viewContactTracing)
			path = "/diseaseform/hepatitis/hep_core_create_No_Contact?CurrentTask="
				+ sCurrentTask + "&ContextAction=" + sContextAction;
		return new ActionForward(path);
	}

	private void prepopulateMorbValuesHep(TreeMap<Object, Object> DSMorbMap,
			HttpServletRequest request) {

		String INV111 = (String) DSMorbMap.get("INV111");

		//Reporting Source
		String INV183UID = (String) DSMorbMap.get("INV183UID");
		String INV183ORG = (String) DSMorbMap.get("INV183ORG");

		//Physician
		String INV182UID = (String) DSMorbMap.get("INV182UID");
		String INV182PRV = (String) DSMorbMap.get("INV182PRV");

		//Reporter
		String INV181UID = (String) DSMorbMap.get("INV181UID");
		String INV181PRV = (String) DSMorbMap.get("INV181PRV");

		//Hospital
		String INV184UID = (String) DSMorbMap.get("INV184UID");
		String INV184ORG = (String) DSMorbMap.get("INV184ORG");

		String INV128 = (String) DSMorbMap.get("INV128");

		String PHC108 = (String) DSMorbMap.get("PHC108");
		String INV137 = (String) DSMorbMap.get("INV137");
		String INV163 = (String) DSMorbMap.get("INV163");
		String INV145 = (String) DSMorbMap.get("INV145");
		String INV146 = (String) DSMorbMap.get("INV146");
		String INV178 = (String) DSMorbMap.get("INV178");
		String INV149 = (String) DSMorbMap.get("INV149");
		String INV148 = (String) DSMorbMap.get("INV148");
		String INV114 = (String) DSMorbMap.get("INV114");

		//new for Rel 1.1
		String INV132 = (String) DSMorbMap.get("INV132");
		String INV133 = (String) DSMorbMap.get("INV133");
		String INV136 = (String) DSMorbMap.get("INV136");

		request.setAttribute("dateOfReport", INV111);

		//Reporting Source Information
		request.setAttribute("reportingOrgUID", INV183UID);
		request.setAttribute("reportingOrgDemographics", INV183ORG);

		//Physician Information
		request.setAttribute("physicianPersonUid", INV182UID);
		request.setAttribute("physicianDemographics", INV182PRV);

		//Reporter Information
		request.setAttribute("reporterPersonUid", INV181UID);
		request.setAttribute("reporterDemographics", INV181PRV);

		request.setAttribute("INV128", INV128);

		//Hospital Information
		request.setAttribute("hospitalOrgUID", INV184UID);
		request.setAttribute("hospitalDemographics", INV184ORG);

		request.setAttribute("illnessOnsetDate", INV137);
		if (INV137 != null && INV137.trim().length() == 10)
			request.setAttribute("HEP102", "Y");

		request.setAttribute("caseStatus", INV163);
		request.setAttribute("didThePatientDie", INV145);
		request.setAttribute("dateOfDeath", INV146);
		request.setAttribute("INV178", INV178);
		request.setAttribute("INV148", INV148);
		request.setAttribute("INV149", INV149);
		request.setAttribute("reportingSourceName", INV114);

		//new for Rel 1.1
		request.setAttribute("INV132-fromTime", INV132);
		request.setAttribute("INV133-fromTime", INV133);
		request.setAttribute("diagnosisDate", INV136);

		request.setAttribute("reportingOrg.organizationUID-values", INV183ORG);
		request.setAttribute("physician.personUid-values", INV182PRV);
		request.setAttribute("reporter.personUid-values", INV181PRV);
		request.setAttribute("hospitalOrg.organizationUID-values", INV184ORG);

		request.setAttribute("HEP106", DSMorbMap.get("INV178"));

	}

	private String getCountyDescTxt(String sStateCd, String sCountyCd) {

		String countyDesc = "";
		if (sStateCd != null && !sStateCd.trim().equals("")
				&& sCountyCd != null && !sCountyCd.trim().equals("")) {
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap<?, ?> treemap = srtValues.getCountyCodes(sStateCd);
			if (treemap != null && treemap.get(sCountyCd) != null) {
				countyDesc = (String) treemap.get(sCountyCd);
			}
		}

		return countyDesc;
	}

}