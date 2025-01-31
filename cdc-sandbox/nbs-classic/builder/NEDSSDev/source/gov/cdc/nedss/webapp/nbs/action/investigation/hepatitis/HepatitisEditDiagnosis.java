package gov.cdc.nedss.webapp.nbs.action.investigation.hepatitis;
/**
 * <p>Title: HepatitisEditDiagnosis</p>
 * <p>Description: This is a Load action class for edit diagnosis investigation for all the Hepatitis conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.WumUtil;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.BaseEditLoad;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.hepatitis.HepatitisInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.form.investigation.InvestigationForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.SRTValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HepatitisEditDiagnosis extends BaseEditLoad {

	//For logging
	static final LogUtils logger =
		new LogUtils(HepatitisEditDiagnosis.class.getName());

	public HepatitisEditDiagnosis() {
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
			logger.error("You do not have access to edit Investigation");
			session.setAttribute(
				"error",
				"You do not have access to edit Investigation");
			throw new ServletException("You do not have access to edit Investigation");
		}

		String sCurrentTask = this.setContextForEdit(request, session);
		String sContextAction = request.getParameter("ContextAction");


		//filter DiagnosedConditions based on programAreas
		String programAreaFromSecurityObj =
			WumUtil.getProgramAreaFromSecurityObj(nbsSecurityObj);

		String conditionCd =
			request.getParameter(
				"proxy.observationVO_s[30].obsValueCodedDT_s[0].code");

		ProgramAreaVO programAreaVO =
			this.getHepProgramAreaVO(conditionCd, programAreaFromSecurityObj);
		String investigationFormCd = programAreaVO.getInvestigationFormCd();

		CachedDropDownValues cdv = new CachedDropDownValues();
		String businessObjNm = cdv.getLDFMap(conditionCd);

		// this is need to remember the ldf entries we entered, Shannon Zheng 03/11/2004
		updateLdfCollectionInFormObject(investigationForm, conditionCd, request);
		if (businessObjNm == null || businessObjNm.trim().length() == 0) {
			businessObjNm = NEDSSConstants.INVESTIGATION_HEP_LDF;
		}
		//		if (conditionCd != null && conditionCd.equalsIgnoreCase("999999"))
		//			businessObjNm = NEDSSConstants.INVESTIGATION_HEP_LDF;
		Long hepUid =
			investigationForm
				.getOldProxy()
				.getPublicHealthCaseVO()
				.getThePublicHealthCaseDT()
				.getPublicHealthCaseUid();
		if (conditionCd != null && conditionCd.equalsIgnoreCase("999999")) {
			updateBusinessObjectGroupRelationship(
				hepUid,
				businessObjNm,
				null,
				request);
			super.createXSP(
				NEDSSConstants.INVESTIGATION_HEP_LDF,
				hepUid,
				investigationForm.getProxy(),
				null,
				request);
		}
		//if(!investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPGEN))
		else {
			updateBusinessObjectGroupRelationship(
				hepUid,
				businessObjNm,
				conditionCd,
				request);
			super.createXSP(
				businessObjNm,
				hepUid,
				investigationForm.getProxy(),
				conditionCd,
				request);
		}
		super.createXSP(
			NEDSSConstants.PATIENT_LDF,
			investigationForm.getOldRevision().getThePersonDT().getPersonUid(),
			investigationForm.getPatient(),
			null,
			request);

		//display revision patient tab
		try {
		PersonUtil.convertPersonToRequestObj(
			investigationForm.getPatient(),
			request,
			"EditPatientFromEvent",
			new ArrayList<Object>());
		this.patientRaceReload(request);
		}catch(Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		String filterDiagnosisCondition =
			cdv.getDiagnosisCodeFilteredOnPA(programAreaFromSecurityObj);
		request.setAttribute(
			"filterDiagnosisCondition",
			filterDiagnosisCondition);

		HepatitisInvestigationUtil hepatitisInvestigationUtil =
			new HepatitisInvestigationUtil();
		hepatitisInvestigationUtil = new HepatitisInvestigationUtil();

		// This is to fix the diagnosis dropdown issue (Defect16353).
		//If notification exist for a publicHealtcase,user should get message if they select empty string as a diagnosis.
		InvestigationProxyVO oldProxy = investigationForm.getOldProxy();
		InvestigationProxyVO newProxy = investigationForm.getProxy();
		if(oldProxy !=null && oldProxy.getAssociatedNotificationsInd()== true){
			newProxy.setAssociatedNotificationsInd(true);
		}
		hepatitisInvestigationUtil.setGenericRequestForView(newProxy,request);
		// Defect16353 end
		hepatitisInvestigationUtil.prepareEntitiesForReload(request);
		//initialize the observation index for supplimental obs questions
		investigationForm.setSupplementalCollection(null);
		request.setAttribute("conditionCd", programAreaVO.getConditionCd());
		request.setAttribute(
			"conditionCdDescTxt",
			programAreaVO.getConditionShortNm());
		request.setAttribute(
			"programAreaCd",
			programAreaVO.getStateProgAreaCode());
		request.setAttribute("HEP128", conditionCd);
		super.getNBSSecurityJurisdictions(request, nbsSecurityObj);

		logger.debug(" before display of Edit Investigation page");
		HepatitisInvestigationUtil.showConditionSpecificLDF(
			programAreaVO.getConditionCd(),
			request);
		return (
			this.getForwardPage(
				investigationFormCd,
				sCurrentTask,
				sContextAction,
				request));

	}

	private ActionForward getForwardPage(
		String investigationFormCd,
		String sCurrentTask,
		String sContextAction,
		HttpServletRequest request) {

		String path = "/error";

		//Hepatitis specific conditions
		String showTabFlag = request.getParameter("hiddenShowTabFlag");

		if ((showTabFlag != null)
			&& (showTabFlag.trim().equalsIgnoreCase("No"))) {
			request.setAttribute("showConditionSpecificLDF", new Boolean(true));
			return new ActionForward(
				"/diseaseform/hepatitis/hep_core_edit?CurrentTask="
					+ sCurrentTask);
		}

		if (NBSConstantUtil.INV_FORM_HEPA.equals(investigationFormCd))
			path =
				"/diseaseform/hepatitis/hep_acute_a_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction;
		else if (NBSConstantUtil.INV_FORM_HEPB.equals(investigationFormCd))
			path =
				"/diseaseform/hepatitis/hep_acute_b_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction;
		else if (NBSConstantUtil.INV_FORM_HEPC.equals(investigationFormCd))
			path =
				"/diseaseform/hepatitis/hep_acute_c_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction;
		else if (NBSConstantUtil.INV_FORM_HEPBV.equals(investigationFormCd))
			path =
				"/diseaseform/hepatitis/hep_perinatal_HBV_infection_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction;
		else if (NBSConstantUtil.INV_FORM_HEPCV.equals(investigationFormCd))
			path =
				"/diseaseform/hepatitis/hep_HCV_infection_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction;
		else if (NBSConstantUtil.INV_FORM_HEPGEN.equals(investigationFormCd))
			path =
				"/diseaseform/hepatitis/hep_core_edit?CurrentTask="
					+ sCurrentTask
					+ "&ContextAction="
					+ sContextAction;

		return new ActionForward(path);
	}

	private ProgramAreaVO getHepProgramAreaVO(
		String conditionCd,
		String programAreaFromSecurityObj) {
		ProgramAreaVO programAreaVO = null;
		SRTValues srtv = new SRTValues();
		TreeMap<Object, Object> programAreaTreeMap = null;
		if (conditionCd == null || conditionCd.trim().equals("")) {
			conditionCd = "999999";
			programAreaTreeMap =
				srtv.getProgramAreaConditions(programAreaFromSecurityObj);
		} else {
			programAreaTreeMap =
				srtv.getProgramAreaConditions(programAreaFromSecurityObj, 2);
		}

		if (programAreaTreeMap != null)
			programAreaVO = (ProgramAreaVO) programAreaTreeMap.get(conditionCd);

		return programAreaVO;

	}

	private void patientRaceReload(HttpServletRequest request) {
		request.setAttribute(
			"raceAsOfDate",
			request.getParameter("raceAsOfDate"));
		request.setAttribute("unknownRace", request.getParameter("unknown"));
		request.setAttribute(
			"americanIndianController",
			request.getParameter("1002-5"));
		request.setAttribute("asianController", request.getParameter("2028-9"));
		request.setAttribute(
			"africanAmericanController",
			request.getParameter("2054-5"));
		request.setAttribute(
			"hawaiianController",
			request.getParameter("2076-8"));
		request.setAttribute("whiteController", request.getParameter("2106-3"));
		request.setAttribute("OtherRace", request.getParameter("otherRaceCd"));
		request.setAttribute(
			"OtherRaceDescText",
			request.getParameter("raceDescTxt"));
	}

	private void updateBusinessObjectGroupRelationship(
		Long busObjUid,
		String busObjNm,
		String condCd,
		HttpServletRequest request) {

		List<Object> uids = new ArrayList<Object> ();

		// this is the generic LDF/DMDF and current group
		List<Object> metadataCollection  =
			getDefinedFieldMetadataCollection(
				busObjNm,
				busObjUid,
				null,
				false,
				request);
		for (Iterator<Object> iter = metadataCollection.iterator(); iter.hasNext();) {
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