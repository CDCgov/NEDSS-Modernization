package gov.cdc.nedss.webapp.nbs.action.investigation.bmird;
/**
 *
 * <p>Title: BmirdEditDiagnosis</p>
 * <p>Description: This is a load action class for change diagnosis for the Bmird conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.bmird.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.generic.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

public class BmirdEditDiagnosis extends BaseEditLoad {

	//For logging
	static final LogUtils logger =
		new LogUtils(BmirdEditDiagnosis.class.getName());
	/**
	 * empty constructor
	 */
	public BmirdEditDiagnosis() {
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

		InvestigationForm investigationForm = (InvestigationForm) form;
		HttpSession session = request.getSession(false);
		
		NBSSecurityObj nbsSecurityObj =
			(NBSSecurityObj) session.getAttribute("NBSSecurityObject");
		boolean editInvestigation =
			nbsSecurityObj.getPermission(
				NBSBOLookup.INVESTIGATION,
				NBSOperationLookup.EDIT);
		if (!editInvestigation) {
			logger.info("You do not have access to edit Investigation");
			session.setAttribute(
				"error",
				"You do not have access to edit Investigation");
			throw new ServletException("You do not have access to edit Investigation");
		}

		String investigationFormCd = null;
		String sCurrentTask = this.setContextForEdit(request, session);
		String sContextAction = request.getParameter("ContextAction");
		try {
			String conditionCode =
					request.getParameter(
							"proxy.observationVO_s[9].obsValueCodedDT_s[0].code");

			CachedDropDownValues cdv = new CachedDropDownValues();
			String businessObjNm = cdv.getLDFMap(conditionCode);
			if (businessObjNm == null || businessObjNm.trim().length() == 0) {
				businessObjNm = NEDSSConstants.INVESTIGATION_BMD_LDF;
			}
			request.setAttribute("showConditionSpecificLDF", new Boolean(false));

			// this is need to remember the ldf entries we entered, Shannon Zheng 03/11/2004
			updateLdfCollectionInFormObject(investigationForm, conditionCode, request);

			Long bmirdUid =
					investigationForm
					.getOldProxy()
					.getPublicHealthCaseVO()
					.getThePublicHealthCaseDT()
					.getPublicHealthCaseUid();
			updateBusinessObjectGroupRelationship(
					bmirdUid,
					businessObjNm,
					conditionCode,
					request);
			super.createXSP(
					businessObjNm,
					bmirdUid,
					investigationForm.getProxy(),
					conditionCode,
					request);
			super.createXSP(
					NEDSSConstants.PATIENT_LDF,
					investigationForm.getOldRevision().getThePersonDT().getPersonUid(),
					investigationForm.getPatient(),
					null,
					request);
			request.setAttribute("showConditionSpecificLDF", new Boolean(false));

			//for ABC states display additional questions
			this.setABCStateIndicator(request);
			//added code for ldf

			//display revision patient tab
			GenericInvestigationUtil util = new GenericInvestigationUtil();
			util.displayRevisionPatient(investigationForm.getOldProxy(), request);
			investigationForm.setOldRevision(
					util.getMPRevision(
							NEDSSConstants.PHC_PATIENT,
							investigationForm.getOldProxy()));

			String programAreaFromSecurityObj =
					WumUtil.getProgramAreaFromSecurityObj(nbsSecurityObj);
			String filterDiagnosisCondition =
					cdv.getDiagnosisCodeFilteredOnPA(programAreaFromSecurityObj);
			request.setAttribute(
					"filterDiagnosisCondition",
					filterDiagnosisCondition);

			//this is executed if DiagnosisCondition
			PersonUtil.setPatientForEventEdit(
					investigationForm.getPatient(),
					request);
			PersonUtil.convertPersonToRequestObj(
					investigationForm.getPatient(),
					request,
					"AddPatientFromEvent",
					new ArrayList<Object>());
			String conditionCd =
					WumUtil.getObservationCode(investigationForm.getProxy(), "BMD120");
			String programAreas =
					WumUtil.getProgramAreaFromSecurityObj(nbsSecurityObj);
			String programAreaCd =
					request.getParameter(
							"proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.progAreaCd");
			ProgramAreaVO programAreaVO =
					cdv.getProgramAreaConditionLevel(programAreaCd, conditionCd);

			//investigationFormCd changes here for DiagnosedCondition
			investigationFormCd = programAreaVO.getInvestigationFormCd();
			request.setAttribute("programAreaCd", programAreaVO.getProgAreaCd());

			//reset batch-entry when reload on change pathogen
			investigationForm.setAntibioticBatchEntryCollection(null);
			investigationForm.setSourceBatchEntryCollection(null);
			investigationForm.setSupplementalCollection(null);
			BMIRDInvestigationUtil bmirdInvestigationUtil =
					new BMIRDInvestigationUtil();
			//bmirdInvestigationUtil.setBatchEntryToFormForView(investigationForm);
			bmirdInvestigationUtil.setGenericRequestForView(
					investigationForm.getProxy(),
					request);
			bmirdInvestigationUtil.prepareEntitiesForReload(request);
			bmirdInvestigationUtil.setMultiSelectReload(request);
			bmirdInvestigationUtil.setConfirmationMethodReload(request);
			request.setAttribute("conditionCd", programAreaVO.getConditionCd());
			request.setAttribute(
					"conditionCdDescTxt",
					programAreaVO.getConditionShortNm());

			super.getNBSSecurityJurisdictions(request, nbsSecurityObj);
		}catch (Exception e) {
			logger.error("Exception in createLoad: " + e.toString(),e);
			throw new ServletException("Error while create load Algorithm : "+e.getMessage(),e);
		}
		return (
			this.getForwardPage(
				investigationFormCd,
				sCurrentTask,
				sContextAction));

	}

	/**
	 * sets all the values of proxy to request attribute based on Bmird type
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param session : HttpSession
	 * @param cdv : CachedDropDownValues
	 * @return : String
	 * @throws ServletException
	 */

	private String convertProxyToRequestObj(
		ActionForm form,
		HttpServletRequest request,
		HttpSession session,
		CachedDropDownValues cdv)
		throws ServletException {

		String investigationFormCd = null;
		try {
		InvestigationForm investigationForm = (InvestigationForm) form;
		InvestigationProxyVO investigationProxyVO =
			investigationForm.getOldProxy();

		String conditionCd =
			investigationProxyVO
				.getPublicHealthCaseVO()
				.getThePublicHealthCaseDT()
				.getCd();
		String programAreaCd =
			investigationProxyVO
				.getPublicHealthCaseVO()
				.getThePublicHealthCaseDT()
				.getProgAreaCd();

		ProgramAreaVO programAreaVO =
			cdv.getProgramAreaConditionLevel(programAreaCd, conditionCd);
		investigationFormCd = programAreaVO.getInvestigationFormCd();

		Long mprUid =
			(Long) NBSContext.retrieve(
				session,
				NEDSSConstants.DSPatientPersonUID);
		if (NBSConstantUtil.INV_FORM_BMDGEN.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			investigationForm.setSupplementalCollection(null);
			biu.setGenericRequestForView(
				investigationForm.getOldProxy(),
				request);
			biu.setSuplimentalObseravationsToFormOnLoad(
				investigationForm,
				request);
			biu.getVaccinationSummaryRecords(mprUid, request);
		} else if (
			NBSConstantUtil.INV_FORM_BMDGAS.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			biu.setGenericRequestForView(
				investigationForm.getOldProxy(),
				request);
			biu.setSuplimentalObseravationsToFormOnLoad(
				investigationForm,
				request);
			biu.getVaccinationSummaryRecords(mprUid, request);
		} else if (
			NBSConstantUtil.INV_FORM_BMDGBS.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			biu.setBatchEntryToFormForView(investigationForm);
			biu.setGenericRequestForView(
				investigationForm.getOldProxy(),
				request);
			biu.convertBatchEntryToRequestForGBS(investigationForm, request);
			biu.setSuplimentalObseravationsToFormOnLoad(
				investigationForm,
				request);
			biu.getVaccinationSummaryRecords(mprUid, request);
		} else if (
			NBSConstantUtil.INV_FORM_BMDHI.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			biu.setGenericRequestForView(
				investigationForm.getOldProxy(),
				request);
			biu.setSuplimentalObseravationsToFormOnLoad(
				investigationForm,
				request);
			biu.getVaccinationSummaryRecords(mprUid, request);
		} else if (
			NBSConstantUtil.INV_FORM_BMDNM.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			biu.setGenericRequestForView(
				investigationForm.getOldProxy(),
				request);
			biu.setSuplimentalObseravationsToFormOnLoad(
				investigationForm,
				request);
			biu.getVaccinationSummaryRecords(mprUid, request);
		} else if (
			NBSConstantUtil.INV_FORM_BMDSP.equals(investigationFormCd)) {
			BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
			biu.setBatchEntryToFormForView(investigationForm);
			biu.setGenericRequestForView(
				investigationForm.getOldProxy(),
				request);
			biu.convertBatchEntryToRequestForSP(investigationForm, request);
			biu.setSuplimentalObseravationsToFormOnLoad(
				investigationForm,
				request);
			biu.getVaccinationSummaryRecords(mprUid, request);
		}
		logger.debug(
			"Investigation Edit Load class investigationFormCd: "
				+ investigationFormCd);
		
		} catch (Exception ex) {
			logger.error("Error in convertProxyToRequestObj: "+ex.getMessage());
			ex.printStackTrace();
		}
		return investigationFormCd;
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
		String investigationFormCd,
		String sCurrentTask,
		String sContextAction) {

		String path = "/error";

		if (NBSConstantUtil.INV_FORM_BMDGEN.equals(investigationFormCd))
			path =
				"/diseaseform/bmird/generic_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=edit";
		else if (NBSConstantUtil.INV_FORM_BMDGAS.equals(investigationFormCd))
			path =
				"/diseaseform/bmird/strepa_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=edit";
		else if (NBSConstantUtil.INV_FORM_BMDGBS.equals(investigationFormCd))
			path =
				"/diseaseform/bmird/generic_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=edit";
		else if (NBSConstantUtil.INV_FORM_BMDHI.equals(investigationFormCd))
			path =
				"/diseaseform/bmird/hi_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=edit";
		else if (NBSConstantUtil.INV_FORM_BMDNM.equals(investigationFormCd))
			path =
				"/diseaseform/bmird/nm_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=edit";
		else if (NBSConstantUtil.INV_FORM_BMDSP.equals(investigationFormCd))
			path =
				"/diseaseform/bmird/sp_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction
					+ "&mode=edit";

		return new ActionForward(path);
	}

	/**
	* this method reads the values from PropertyUtil sets the default values for Bmird
	* also sets the default values based on age of person
	* @param personVO : PersonVO
	* @param request : HttpServletRequest
	*/
	private void setABCStateIndicator(HttpServletRequest request) {
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
	}

	private void updateBusinessObjectGroupRelationship(
		Long busObjUid,
		String busObjNm,
		String condCd,
		HttpServletRequest request) {

		List< Object> uids = new ArrayList<Object> ();

		// this is the generic LDF/DMDF and current group
		List< Object> metadataCollection  =
			getDefinedFieldMetadataCollection(
				busObjNm,
				busObjUid,
				null,
				false,
				request);
		for (Iterator< Object> iter = metadataCollection.iterator(); iter.hasNext();) {
			StateDefinedFieldMetaDataDT element =
				(StateDefinedFieldMetaDataDT) iter.next();
			if (element.getConditionCd() == null
				|| element.getConditionCd().trim().length() == 0) {
				uids.add(element.getLdfUid());
			}
		}

		if (condCd != null) {
			// these are the new ones
			uids.addAll(
				getDefinedFieldUids(busObjNm, null, condCd, false, request));
		}

		updateBusinessObjectGroupRelationship(busObjUid, uids, request);
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
			Iterator< Object> it = coll.iterator();
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
							stateDT.setItDirty(true);
							stateDT.setItNew(true);
							//				businessObjName = stateDT.getBusinessObjNm();
							if (stateDT.getLdfUid() != null)
								invLdfMap.put(stateDT.getLdfUid(), stateDT);
						}
					}
					if (stateDT
						.getBusinessObjNm()
						.equalsIgnoreCase(NEDSSConstants.PATIENT_LDF)) {
						stateDT.setItDirty(true);
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
					new ArrayList< Object>(invLdfMap.values()));
			investigationForm
				.getPatient()
				.setTheStateDefinedFieldDataDTCollection(
				new ArrayList< Object>(patientLdfMap.values()));

			ArrayList<Object> newList = new ArrayList<Object> ();
			//			newList.addAll(invLdfMap.values());
			//			newList.addAll(patientLdfMap.values());
			investigationForm.setLdfCollection(newList);
		}
	}
}