package gov.cdc.nedss.webapp.nbs.action.investigation.bmird;
/**
 *
 * <p>Title: BmirdCreateDiagnosis</p>
 * <p>Description: This is a load action class for change of diagnosis condition.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
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
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

public class BmirdCreateDiagnosis extends BaseCreateLoad {

	static final LogUtils logger =
		new LogUtils(BmirdCreateDiagnosis.class.getName());
	/**
	 * empty constructor
	 */
	public BmirdCreateDiagnosis() {
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

		logger.debug("inside the BmirdCreateLoad");
		HttpSession session = request.getSession(false);

		//context
		String sContextAction = request.getParameter("ContextAction");
		if (sContextAction == null) {
			logger.error("ContextAction is null");
			session.setAttribute(
				"error",
				"No Context Action in InvestigationCreateLoad");
			throw new ServletException("ContextAction is null");
		}

		return this.createInvestigation(mapping, form, request, session);
	}

	/**
	 * reads the condition for Diagnosis in BMD120, reads LDF for the diagnoised condition
	 * sets the security for create Load, jurisdiction, Bmird default values, multiselect,
	 * entities for reload. sets condition and programArea to request.
	 * finally forwards to proper XSP
	 * @param mapping :ActionMapping
	 * @param form : ActionForm
	 * @param request :HttpServletRequest
	 * @param session :HttpSession
	 * @return :ActionForward
	 * @throws ServletException
	 */

	private ActionForward createInvestigation(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpSession session)
		throws ServletException {
		String sContextAction = request.getParameter("ContextAction");

		InvestigationForm investigationForm = (InvestigationForm) form;
		NBSSecurityObj nbsSecurityObj =
			(NBSSecurityObj) session.getAttribute("NBSSecurityObject");
		Long mprUid = null;
		String sCurrentTask = null;
		String investigationFormCd = null;
		
		try {
			String conditionCd =
					(String) NBSContext.retrieve(
							session,
							NBSConstantUtil.DSInvestigationCondition);
			String programArea =
					(String) NBSContext.retrieve(
							session,
							NBSConstantUtil.DSInvestigationProgramArea);

			//since this is Diagnosied condition we need to changed conditionCd to BMD120's value
			conditionCd =
					WumUtil.getObservationCode(investigationForm.getProxy(), "BMD120");

			CachedDropDownValues cdv = new CachedDropDownValues();

			ProgramAreaVO programAreaVO =
					cdv.getProgramAreaConditionLevel(programArea, conditionCd);
			investigationFormCd = programAreaVO.getInvestigationFormCd();

			InvestigationProxyVO investigationProxyVO =
					investigationForm.getProxy();

			// this is need to remember the ldf entries we entered, Shannon Zheng 03/11/2004
			updateLdfCollectionInFormObject(investigationForm, conditionCd, request);
			String businessObjNm = cdv.getLDFMap(conditionCd);
			if (businessObjNm == null || businessObjNm.trim().length() == 0) {
				businessObjNm = NEDSSConstants.INVESTIGATION_BMD_LDF;
			}
			request.setAttribute("showConditionSpecificLDF", new Boolean(false));
			super.createXSP(
					businessObjNm,
					investigationProxyVO,
					conditionCd,
					request);

			mprUid =
					(Long) NBSContext.retrieve(
							session,
							NBSConstantUtil.DSPersonSummary);
			PersonVO personVO = null;

			if (mprUid != null) {
				personVO =
						this.findMasterPatientRecord(mprUid, session, nbsSecurityObj);
				//          super.createXSP(NEDSSConstants.PATIENT_LDF, personVO, null, request);
				ArrayList<Object> stateList = new ArrayList<Object> ();
				PersonUtil.convertPersonToRequestObj(
						personVO,
						request,
						"AddPatientFromEvent",
						stateList);

			}
			super.createXSP(
					NEDSSConstants.PATIENT_LDF,
					investigationForm.getPatient(),
					null,
					request);

			super.setSecurityForCreateLoad(request, nbsSecurityObj, programAreaVO);
			super.setJurisdictionForCreate(request, nbsSecurityObj, programAreaVO);
			this.setBmirdDefaultValues(personVO, request);
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

			//diagnosis specific stuff
			//reset batchentry when reload on change pathogen
			// request.setAttribute("reportingSourceCollection", super.convertReportingSourcesToRequest(NEDSSConstants.REPORTING_SOURCE_CREATE,null, conditionCd));
			investigationForm.setAntibioticBatchEntryCollection(null);
			investigationForm.setSourceBatchEntryCollection(null);
			investigationForm.setSupplementalCollection(null);

			BMIRDInvestigationUtil bmirdInvestigationUtil =
					new BMIRDInvestigationUtil();
			bmirdInvestigationUtil.setMultipleSelects(
					request,
					investigationForm.getProxy());
			bmirdInvestigationUtil.setGenericRequestForView(
					investigationForm.getProxy(),
					request);
			bmirdInvestigationUtil.prepareEntitiesForReload(request);

			request.setAttribute("conditionCd", programAreaVO.getConditionCd());
			request.setAttribute(
					"conditionCdDescTxt",
					programAreaVO.getConditionShortNm());
			request.setAttribute(
					"programAreaCd",
					programAreaVO.getStateProgAreaCode());
			request.setAttribute(
					"programAreaDescTxt",
					programAreaVO.getStateProgAreaCdDesc());
		}catch (Exception e) {
			logger.error("Exception in BmirdCreateDiagnosis.createInvestigation: " + e.toString());
			throw new ServletException("Error while BmirdCreateDiagnosis.createInvestigation : "+e.getMessage());
		}
		return (
			this.getForwardPage(
				mprUid,
				investigationFormCd,
				sCurrentTask,
				sContextAction,
				request));
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
		HttpServletRequest request) {

		String path = "/error";

		if (NBSConstantUtil.INV_FORM_BMDGEN.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			biu.investigationCreateLoad(request);
			path =
				"/diseaseform/bmird/generic_create?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=add";

		} else if (
			NBSConstantUtil.INV_FORM_BMDGAS.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			biu.investigationCreateLoad(request);
			path =
				"/diseaseform/bmird/strepa_create?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=add";
		} else if (
			NBSConstantUtil.INV_FORM_BMDGBS.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			biu.investigationCreateLoad(request);
			// as per enhancement requst 10431 always do not display GroupB strep, display generic Bmird
			//path = "/diseaseform/bmird/strepb_create?CurrentTask=" + sCurrentTask + "&ContextAction=" + sContextAction + "&mode=add";
			path =
				"/diseaseform/bmird/generic_create?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=add";

		} else if (
			NBSConstantUtil.INV_FORM_BMDHI.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			biu.investigationCreateLoad(request);
			biu.getVaccinationSummaryRecords(mprUid, request);
			path =
				"/diseaseform/bmird/hi_create?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=add";
		} else if (
			NBSConstantUtil.INV_FORM_BMDNM.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			biu.investigationCreateLoad(request);
			biu.getVaccinationSummaryRecords(mprUid, request);
			path =
				"/diseaseform/bmird/nm_create?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=add";
		} else if (
			NBSConstantUtil.INV_FORM_BMDSP.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			biu.investigationCreateLoad(request);
			biu.getVaccinationSummaryRecords(mprUid, request);
			path =
				"/diseaseform/bmird/sp_create?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=add";
		}
		return new ActionForward(path);
	}
	/**
	 * this method reads the values from PropertyUtil sets the default values for Bmird
	 * also sets the default values based on age of person
	 * @param personVO : PersonVO
	 * @param request : HttpServletRequest
	 */

	private void setBmirdDefaultValues(
		PersonVO personVO,
		HttpServletRequest request) {
		String abcIndicator = PropertyUtil.getInstance().getABCSTATE();
		logger.debug("abcIndicator: " + abcIndicator);
		if (abcIndicator != null
			&& abcIndicator.trim().equals("ABCSTATE_ABCQUESTION")) {
			logger.debug("ABCSTATE_ABCQUESTION abcIndicator: " + abcIndicator);
			request.setAttribute("abcCheckbox", "T");
			request.setAttribute("abcsInd", "T");
		} else if (
			abcIndicator != null
				&& abcIndicator.trim().equals("ABCQUESTION")) {
			logger.debug("ABCQUESTION abcIndicator: " + abcIndicator);
			request.setAttribute("abcCheckbox", "F");
			request.setAttribute("abcsInd", "T");
		} else if (
			abcIndicator != null && abcIndicator.trim().equals("NONE")) {
			logger.debug("NONE abcIndicator: " + abcIndicator);
			request.setAttribute("abcCheckbox", "F");
			request.setAttribute("abcsInd", "F");
		} else {
			logger.debug("else abcIndicator: " + abcIndicator);
			request.setAttribute("abcCheckbox", "F");
			request.setAttribute("abcsInd", "F");
		}

		if (personVO != null) {
			// for bmird less than month age should be defaulted
			if (personVO.getThePersonDT().getBirthTime() != null) {
				if (DateUtil
					.lessThanMonthAge(personVO.getThePersonDT().getBirthTime())
					>= 0)
					request.setAttribute("BMD113", "Y");

				int age[] =
					DateUtil.ageInYears(
						personVO.getThePersonDT().getBirthTime());
				if (age[0] > 0 || age[1] > 1)
					request.setAttribute("BMD113", "N");

				if (DateUtil
					.lessThanMonthAge(personVO.getThePersonDT().getBirthTime())
					<= 6)
					request.setAttribute("ageSixDays", "T");

				if (DateUtil
					.moreThanMonthAge(personVO.getThePersonDT().getBirthTime())
					> 0)
					request.setAttribute("BMD113", "N");

			}
		}
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
							NEDSSConstants.INVESTIGATION_BMD_LDF)) {
						boolean toKeep = false;
						if (checkCoditionCd(stateDT, conditionCd)) {
							// DT has the same condition code
							toKeep = true;
						} else if (
							conditionCd != null
								&& (stateDT.getConditionCd() == null
									|| stateDT.getConditionCd().trim().length()
										== 0)) {
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
//			newList.addAll(invLdfMap.values());
//			newList.addAll(patientLdfMap.values());
			investigationForm.setLdfCollection(newList);
		}
	}

}
