package gov.cdc.nedss.webapp.nbs.action.investigation.hepatitis;

/**
 *
 * <p>Title: HepatitisCreateDiagnosis</p>
 * <p>Description: This is a load action class for change of Hepatitis diagnosis conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
/**
 * Title:        CoreDemographic
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author Jay Kim
 * @version 1.0
 */
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.bmird.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.hepatitis.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.measles.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.crs.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.pertussis.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.rubella.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

public class HepatitisCreateDiagnosis extends BaseCreateLoad {

	static final LogUtils logger =
		new LogUtils(HepatitisCreateDiagnosis.class.getName());

	public HepatitisCreateDiagnosis() {
	}

	/**
	     * Process the specified HTTP request, and create the corresponding HTTP response
	 * (or forward to another web component that will create it). Return an ActionForward
	 * instance describing where and how control should be forwarded, or null if the response
	 * has already been completed.
	 * @param mapping - The ActionMapping used to select this instance
	 * @param form - The ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return mapping.findForward("XSP") -- ActionForward instance describing where and how control should be forwarded
	 * @throws IOException -- if an input/output error occurs
	 * @throws ServletException -- if a servlet exception occurs
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {
		HttpSession session = request.getSession(false);

		String sContextAction = request.getParameter("ContextAction");
		if (sContextAction == null) {
			session.setAttribute(
				"error",
				"No Context Action in InvestigationCreateLoad");
			throw new ServletException("No Context Action in InvestigationCreateLoad");
		}

		InvestigationForm investigationForm = (InvestigationForm) form;
		NBSSecurityObj nbsSecurityObj =
			(NBSSecurityObj) session.getAttribute("NBSSecurityObject");
		//for change Diagnosis get conditionCd from HEP128, this could be
		//empty string change it back to 999999 i.e Hep Generic in method getHepProgramAreaVO
		String conditionCd =
			request.getParameter(
				"proxy.observationVO_s[30].obsValueCodedDT_s[0].code");

		CachedDropDownValues cdv = new CachedDropDownValues();
		String programAreaFromSecurityObj =
			WumUtil.getProgramAreaFromSecurityObj(nbsSecurityObj);
		ProgramAreaVO programAreaVO =
			this.getHepProgramAreaVO(
				conditionCd,
				programAreaFromSecurityObj,
				cdv);
		String investigationFormCd = programAreaVO.getInvestigationFormCd();
		//security stuff
		boolean checkCreatePermission =
			super.setSecurityForCreateLoad(
				request,
				nbsSecurityObj,
				programAreaVO);
		if (!checkCreatePermission) {
			logger.error("You do not have access to Create Investigation");
			session.setAttribute(
				"error",
				"You do not have access to Create Investigation");

			return (mapping.findForward("error"));
		}
		Long mprUid = null;
		HepatitisInvestigationUtil hepatitisInvestigationUtil =
				new HepatitisInvestigationUtil();
		String sCurrentTask = "";
		try {
		InvestigationProxyVO investigationProxyVO =
			investigationForm.getProxy();

		// this is need to remember the ldf entries we entered, Shannon Zheng 03/11/2004
		updateLdfCollectionInFormObject(investigationForm, conditionCd, request);

		String businessObjNm = cdv.getLDFMap(conditionCd);
		if (businessObjNm == null || businessObjNm.trim().length() == 0) {
			businessObjNm = NEDSSConstants.INVESTIGATION_HEP_LDF;
		}
		if (conditionCd != null && conditionCd.equalsIgnoreCase("999999")) {
			super.createXSP(businessObjNm, investigationProxyVO, null, request);
		} else {
			super.createXSP(
				businessObjNm,
				investigationProxyVO,
				conditionCd,
				request);
		}

		NBSContext.store(
			session,
			NBSConstantUtil.DSInvestigationCode,
			investigationFormCd);

		mprUid =
			(Long) NBSContext.retrieve(
				session,
				NBSConstantUtil.DSPersonSummary);
		PersonVO personVO = null;

		if (mprUid != null) {
			personVO =
				super.findMasterPatientRecord(mprUid, session, nbsSecurityObj);

			//super.createXSP(NEDSSConstants.PATIENT_LDF, personVO, null, request);
			//PersonUtil.convertPersonToRequestObj(personVO, request, "AddPatientFromEvent", new ArrayList<Object>());
		}
		super.createXSP(
			NEDSSConstants.PATIENT_LDF,
			investigationForm.getPatient(),
			null,
			request);

		if (investigationFormCd == null) {
			logger.error(" could not get investigationFormCd");
			session.setAttribute(
				"error",
				"ConditionCd or InvestigationFormCd is null");

			return (mapping.findForward("error"));
		}

		super.setJurisdictionForCreate(request, nbsSecurityObj, programAreaVO);
		//CONTEXT
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
			String jurisdiction =
				(String) NBSContext.retrieve(
					session,
					NBSConstantUtil.DSInvestigationJurisdiction);
			request.setAttribute("jurisdiction", jurisdiction);
		}

		//filter DiagnosedConditions based on programAreas

		String filterDiagnosisCondition =
			cdv.getDiagnosisCodeFilteredOnPA(programAreaFromSecurityObj);
		request.setAttribute(
			"filterDiagnosisCondition",
			filterDiagnosisCondition);

		PersonUtil.setPatientForEventCreate(
			investigationForm.getPatient(),
			request);
		PersonUtil.convertPersonToRequestObj(
			investigationForm.getPatient(),
			request,
			"AddPatientFromEvent",
			new ArrayList<Object>());
		investigationForm.setPatient(null);


		hepatitisInvestigationUtil.setMultipleSelects(
			request,
			investigationForm.getProxy().getTheObservationVOCollection());
		hepatitisInvestigationUtil.setGenericRequestForView(
			investigationForm.getProxy(),
			request);
		hepatitisInvestigationUtil.prepareEntitiesForReload(request);
		hepatitisInvestigationUtil.setMultiSelectReload(request);
		//initialize the observation index for supplimental obs questions
		investigationForm.setSupplementalCollection(null);
		if (programAreaVO != null) {
			request.setAttribute("conditionCd", programAreaVO.getConditionCd());
			request.setAttribute(
				"conditionCdDescTxt",
				programAreaVO.getConditionShortNm());
			request.setAttribute(
				"programAreaCd",
				programAreaVO.getStateProgAreaCode());
		}
		HepatitisInvestigationUtil.showConditionSpecificLDF(
			programAreaVO.getConditionCd(),
			request);
		
		}catch (Exception e) {
			logger.error("Exception in Hepatitis Create Diag: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while Hepatitis Create Diag : "+e.getMessage());
		}	
		
		return (
			this.getForwardPage(
				mprUid,
				investigationFormCd,
				sCurrentTask,
				sContextAction,
				request,
				hepatitisInvestigationUtil));
	}

	/**
	     * this method returns ActionForward with the actual struts mapping of XSP page
	 * @param mprUid :Long
	 * @param investigationFormCd : String
	 * @param sCurrentTask : String
	 * @param sContextAction : String
	 * @param request : HttpServletRequst
	 * @return ActionForward
	 */
	private ActionForward getForwardPage(
		Long mprUid,
		String investigationFormCd,
		String sCurrentTask,
		String sContextAction,
		HttpServletRequest request,
		HepatitisInvestigationUtil hepatitisInvestigationUtil) {

		String path = "/error";
		// check if user wants to select a diagnosis to send a message but not enter any supplimental questions
		String showTabFlag = request.getParameter("hiddenShowTabFlag");

		if ((showTabFlag != null)
			&& (showTabFlag.trim().equalsIgnoreCase("No"))) {
			request.setAttribute("showConditionSpecificLDF", new Boolean(true));
			return new ActionForward(
				"/diseaseform/hepatitis/hep_core_create?CurrentTask="
					+ sCurrentTask);
		} else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPA)) {
			hepatitisInvestigationUtil.getVaccinationSummaryRecords(
				mprUid,
				request);
			return new ActionForward(
				"/diseaseform/hepatitis/hep_acute_a_create?CurrentTask="
					+ sCurrentTask);
		} else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPB)) {
			hepatitisInvestigationUtil.getVaccinationSummaryRecords(
				mprUid,
				request);
			return new ActionForward(
				"/diseaseform/hepatitis/hep_acute_b_create?CurrentTask="
					+ sCurrentTask);
		} else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPC)) {
			return new ActionForward(
				"/diseaseform/hepatitis/hep_acute_c_create?CurrentTask="
					+ sCurrentTask);
		} else if (
			investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPBV)) {
			hepatitisInvestigationUtil.getVaccinationSummaryRecords(
				mprUid,
				request);
			return new ActionForward(
				"/diseaseform/hepatitis/hep_perinatal_HBV_infection_create?CurrentTask="
					+ sCurrentTask);
		} else if (
			investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPCV)) {
			return new ActionForward(
				"/diseaseform/hepatitis/hep_HCV_infection_create?CurrentTask="
					+ sCurrentTask);
		} else if (
			investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPGEN)) {
			return new ActionForward(
				"/diseaseform/hepatitis/hep_core_create?CurrentTask="
					+ sCurrentTask);
		}

		return new ActionForward(path);
	}

	private ProgramAreaVO getHepProgramAreaVO(
		String conditionCd,
		String programAreaFromSecurityObj,
		CachedDropDownValues cdv) {
		ProgramAreaVO programAreaVO = null;
		if (conditionCd == null || conditionCd.trim().equals("")) {
			conditionCd = "999999";
			programAreaVO =
				cdv.getProgramAreaCondition(
					programAreaFromSecurityObj,
					conditionCd);
		} else {
			programAreaVO =
				cdv.getProgramAreaCondition(
					programAreaFromSecurityObj,
					2,
					conditionCd);
		}

		return programAreaVO;
	}

    // changed message signature to handle multiselect LDF values
    // xz (01/05/2005)
	private void updateLdfCollectionInFormObject(
		InvestigationForm investigationForm,
		String conditionCd,
		HttpServletRequest request) {

		if (investigationForm.getLdfCollection() != null) {
	        // use the new API to retrieve custom field collection
	        // to handle multiselect fields (xz 01/11/2005)
	        Collection<Object>  coll = extractLdfDataCollection(investigationForm, request);
			HashMap<Object, Object> map = new HashMap<Object,Object>();
			HashMap<Object, Object> invLdfMap = new HashMap<Object,Object>();
			HashMap<Object, Object> patientLdfMap = new HashMap<Object,Object>();
			Iterator<Object> it = coll.iterator();
			while (it.hasNext()) {
				StateDefinedFieldDataDT stateDT =
					(StateDefinedFieldDataDT) it.next();
				if (stateDT != null && stateDT.getBusinessObjNm() != null) {
					if (stateDT
						.getBusinessObjNm()
						.equalsIgnoreCase(
							NEDSSConstants.INVESTIGATION_HEP_LDF)) {
						boolean toKeep = false;
						if (checkCoditionCd(stateDT, conditionCd)) {
							// DT has the same condition code
							toKeep = true;
						} else if (
							conditionCd != null
								&& conditionCd.trim().length() != 0
								&& !conditionCd.equalsIgnoreCase("999999")
								&& (stateDT.getConditionCd() == null
									|| stateDT.getConditionCd().trim().length()
										== 0
									|| stateDT.getConditionCd().equalsIgnoreCase(
										"999999"))) {
							// we are getting to a condition specific page, keep the generic ones
							toKeep = true;
						}
						if (toKeep) {
							stateDT.setItDirty(false);
							stateDT.setItNew(true);
							//				businessObjName = stateDT.getBusinessObjNm();
							if (stateDT.getLdfUid() != null)
								invLdfMap.put(stateDT.getLdfUid(), stateDT);
						}
					}
					if (stateDT
						.getBusinessObjNm()
						.equalsIgnoreCase(NEDSSConstants.PATIENT_LDF)) {
						stateDT.setItDirty(false);
						stateDT.setItNew(true);
						//				businessObjName = stateDT.getBusinessObjNm();
						if (stateDT.getLdfUid() != null)
							patientLdfMap.put(stateDT.getLdfUid(), stateDT);
					}
				}
			}
			if (invLdfMap != null && invLdfMap.values().size() > 0)
				investigationForm
					.getProxy()
					.setTheStateDefinedFieldDataDTCollection(
					new ArrayList<Object>(invLdfMap.values()));
			investigationForm
				.getPatient()
				.setTheStateDefinedFieldDataDTCollection(
				new ArrayList<Object>(patientLdfMap.values()));

			ArrayList<Object> newList = new ArrayList<Object> ();
			investigationForm.setLdfCollection(newList);
		}
	}
}
