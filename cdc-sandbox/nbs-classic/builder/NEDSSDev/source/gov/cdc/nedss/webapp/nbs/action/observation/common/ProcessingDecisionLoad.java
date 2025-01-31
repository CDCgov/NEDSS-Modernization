package gov.cdc.nedss.webapp.nbs.action.observation.common;

import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.form.observation.ObservationForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
/**
 * @author Pradeep.Sharma
 * Description: This is a pre-existing code that has been updated to fix data loss issue
 * Page Builder: Data Loss on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 
 *
 */
public class ProcessingDecisionLoad extends DispatchAction{
	
	static final LogUtils logger = new LogUtils(ProcessingDecisionLoad.class.getName());
	private static String EVENT = "event";
	
	public ActionForward processingDecisionLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		ObservationForm obsForm = (ObservationForm)form;
		String context = null;
		String event = null;
		String isCreateInvFromViewDocument = "false";
 		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
		try {
			event = (String)request.getParameter(EVENT);
			context = (String)request.getParameter("context");
			isCreateInvFromViewDocument = (String)request.getParameter("isCreateInvFromViewDocument");
			String buttonName = (String)request.getParameter("buttonName");
			request.setAttribute(EVENT, (event!=null && event.contains("InvCreate"))?"InvCreate":event);
			if(event!=null && event.contains("CaseReport"))
				request.setAttribute("typeDocument","CaseReport");
			String condition = (String)request.getParameter(NEDSSConstants.CONDITION_CD);
			String[] conds = null;
			if(condition!=null)
				conds=condition.split("\\|");
			
			//In case there's more than 1 condition associated to the eICR, we need to see if there's any STD to show the correct
			//Processing decision list.
			if(conds!=null && conds.length>1)
				for(int i=0; i<conds.length;i++){
					String cond1 = conds[i];
					String pa1 = CachedDropDowns.getProgramAreaForCondition(cond1).getStateProgAreaCode();
					if(pa1!=null && pa1.equalsIgnoreCase("STD"))
						condition = cond1;
				}
			
			request.setAttribute("cond", condition);
			String pa = (String)request.getParameter(NEDSSConstants.PROG_AREA_CD);
			if(condition!=null && pa==null)
				pa = CachedDropDowns.getProgramAreaForCondition(condition).getStateProgAreaCode();
			obsForm.setProgramArea((String)request.getParameter(NEDSSConstants.PROG_AREA_CD));
			Long mprUid = null;
			if(request.getParameter("MprUid")!=null)
					mprUid = new Long(request.getParameter("MprUid"));
			else		
				mprUid = (Long) NBSContext.retrieve(request.getSession(),
					NBSConstantUtil.DSPersonSummary);
			PageLoadUtil pageLoadUtil = new PageLoadUtil();
			ArrayList<Object> coinfectionInvList = pageLoadUtil.getCoinfectionInvList(condition,mprUid, request);
			/*
			String referralBasis = request.getParameter("referralBasis");
			if(referralBasis!=null && referralBasis.equals(CTConstants.CongenitalInfantFollowupM1))
				condition = CTConstants.CongenitalSyphilisConditionCode;
			if(referralBasis!=null && referralBasis.equals(CTConstants.CongenitalMotherFollowupM2))
				condition = CTConstants.SyphilisUnknownConditionCode;
			
			if (referralBasis!=null && referralBasis.equals(CTConstants.CongenitalInfantFollowupM1)) {
				String condition2 = CTConstants.CongenitalSyphilisConditionCode;
				ArrayList<Object> openInvestigationList = PageLoadUtil.getOpenInvList(condition2, mprUid, request);
				if(openInvestigationList!=null && openInvestigationList.size()>0){
					request.setAttribute("condition", CachedDropDowns.getConditionDesc(condition2));
					context = "invAlreadyExists";
					return (mapping.findForward(context));
				}
			}
			if (referralBasis!=null && referralBasis.equals(CTConstants.CongenitalMotherFollowupM2)) {
				String condition2 = CTConstants.SyphilisUnknownConditionCode;
				ArrayList<Object> openInvestigationList = PageLoadUtil.getOpenInvList(condition2, mprUid, request);
				if(openInvestigationList!=null && openInvestigationList.size()>0){
					request.setAttribute("condition", CachedDropDowns.getConditionDesc(condition2));
					context = "invAlreadyExists";
					return (mapping.findForward(context));
				}
			}	*/		
			
			HashMap<Object, Object> coinfectionScenarios = getCoinfectionScenarios(condition,coinfectionInvList,nbsSecurityObj);
			if(coinfectionScenarios != null){

				if (coinfectionScenarios.get(NEDSSConstants.COINFECTION_LOGIC)
						.equals(NEDSSConstants.INVESTIGATION_EXISTS) && event!=null && event.contains("Lab")){
					context = "associateReport";
					CoinfectionSummaryVO csVO = (CoinfectionSummaryVO) coinfectionScenarios
							.get(NEDSSConstants.COINFECTION_INV);
					if (csVO != null){
					
					NBSContext.store(request.getSession(),
							NBSConstantUtil.DSCoinfectionInvSummVO,
							csVO);
					}
					NBSContext.store(request.getSession(),
							NBSConstantUtil.DSInvestigationType,
							NEDSSConstants.ASSOCIATE);
					request.setAttribute("report", NEDSSConstants.LAB_REPORT_DESC);
					request.setAttribute("permission", coinfectionScenarios
							.get(NEDSSConstants.LAB_REPORT_ASSOC_PERM));
					request.setAttribute("investigator", ((CoinfectionSummaryVO)coinfectionInvList
							.get(0)).getInvestigator());
				}
				else if (coinfectionScenarios.get(NEDSSConstants.COINFECTION_LOGIC)
						.equals(NEDSSConstants.INVESTIGATION_EXISTS) && event!=null && event.contains("Morb")){
					context = "associateReport";
					CoinfectionSummaryVO csVO = (CoinfectionSummaryVO) coinfectionScenarios
							.get(NEDSSConstants.COINFECTION_INV);
					if (csVO != null){
					NBSContext.store(request.getSession(),
							NBSConstantUtil.DSCoinfectionInvSummVO,
							csVO);
					}
					NBSContext.store(request.getSession(),
							NBSConstantUtil.DSInvestigationType,
							NEDSSConstants.ASSOCIATE);
					request.setAttribute("report", NEDSSConstants.MORB_REPORT_DESC);
					request.setAttribute("permission", coinfectionScenarios
							.get(NEDSSConstants.MORB_REPORT_ASSOC_PERM));
					request.setAttribute("investigator", ((CoinfectionSummaryVO)coinfectionInvList
							.get(0)).getInvestigator());
					
					// Set requestForm to know from which button request came, based on it renders associate_report_inv.jsp's 'Ok' button.
					request.setAttribute("requestFrom", buttonName);
					
					// Set CurrentTask in request attribute to build URL dynamically in MorbiditySpecific.js's submitMorbAndCreateInv() function.
					String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
					request.setAttribute("CurrentTask", sCurrentTask);
				}
				else if (coinfectionScenarios.get(NEDSSConstants.COINFECTION_LOGIC)
						.equals(NEDSSConstants.INVESTIGATION_EXISTS)){
					context = "invAlreadyExists";
					request.setAttribute("condition", CachedDropDowns.getConditionDesc(condition));
					return (mapping.findForward(context));
				}
				else if (coinfectionScenarios.get(NEDSSConstants.COINFECTION_LOGIC)
						.equals(NEDSSConstants.COINFECTION_INV_EXISTS) && event!=null && event.contains("contactRecord")){
					CoinfectionSummaryVO csVO = (CoinfectionSummaryVO) coinfectionScenarios
							.get(NEDSSConstants.COINFECTION_INV);
					if (csVO != null){
					NBSContext.store(request.getSession(),
							NBSConstantUtil.DSCoinfectionInvSummVO,
							csVO);
					}
					request.setAttribute("permission", coinfectionScenarios
							.get(NEDSSConstants.INV_EDIT_PERM));
				}

				else if  (coinfectionScenarios.get(NEDSSConstants.COINFECTION_LOGIC)
							.equals(NEDSSConstants.COINFECTION_INV_EXISTS)) {
						CoinfectionSummaryVO csVO = (CoinfectionSummaryVO) coinfectionScenarios
								.get(NEDSSConstants.COINFECTION_INV);
					if (csVO != null)
						NBSContext.store(request.getSession(),
								NBSConstantUtil.DSCoinfectionInvSummVO,	csVO);
						request.setAttribute("permission", coinfectionScenarios
								.get(NEDSSConstants.INV_EDIT_PERM));
						context = "loadProcessDecisionCreateInvCoinf";
					}
			}

			//if there are no co-infection investigation, check for any existing open investigations.
			else{
				ArrayList<Object> openInvestigationList = PageLoadUtil.getOpenInvList(condition, mprUid, request);
				if(openInvestigationList!=null && openInvestigationList.size()>0){
					context = "invAlreadyExists";
					request.setAttribute("condition", CachedDropDowns.getConditionDesc(condition));
					return (mapping.findForward(context));
				}
			}
			request.setAttribute("coInfectionInvestigationList", getCandidateCoinfectionList(coinfectionInvList));
			if(condition!=null)
				request.setAttribute("condition", CachedDropDowns.getConditionDesc(condition));
			ArrayList<Object> conditionList = new ArrayList<Object>();
			if(condition!=null && !condition.trim().equals(""))
				conditionList.add(condition);
			String PDLogic = (String)request.getParameter("PDLogic");
			CommonAction ca = new CommonAction();
			if (isCreateInvFromViewDocument == null || (isCreateInvFromViewDocument !=null && !"true".equals(isCreateInvFromViewDocument))) {
				if(PDLogic == null && context!=null && context.equalsIgnoreCase("loadMAR"))
					PDLogic = ca.checkIfSyphilisIsInConditionList(pa, conditionList,(String)request.getParameter(EVENT));
				else if(PDLogic == null && context!=null && context.contains("loadProcessDecisionCreateInv"))
					PDLogic = ca.checkIfSyphilisIsInConditionListForCreateInv(pa, conditionList, (String)request.getParameter(EVENT));
				else if ((PDLogic == null || PDLogic.equalsIgnoreCase("null") || PDLogic.equalsIgnoreCase("")) && context!=null && (context.contains("loadProcessDecisionCreateInv")))//ND-17832
					PDLogic = ca.checkIfSyphilisIsInConditionListForCreateInv(pa,
						conditionList, NEDSSConstants.MORBIDITY_REPORT);
				obsForm.setProcessingDecisionLogic(PDLogic);
			} else if (isCreateInvFromViewDocument !=null && "true".equals(isCreateInvFromViewDocument)) {
				String referralBasisDocument = (String)request.getParameter("referralBasisDocument");
				if (referralBasisDocument!=null && NEDSSConstants.REFERRAL_BASIS_MORB.equals(referralBasisDocument)) {
					PDLogic = ca.checkIfSyphilisIsInConditionListForCreateInv(pa, conditionList, NEDSSConstants.MORBIDITY_REPORT);
				} else {
					PDLogic = NEDSSConstants.STD_CR_PROC_DECISION_NOINV;
				}
				
				obsForm.setProcessingDecisionLogic(PDLogic);
			}
			
		} catch (Exception e) {
			logger.error("Error while loading processing decsion for "+event+": " + e.toString());
			e.printStackTrace();
			throw new ServletException("Error while loading loading processing decsion for lab and morb report: "+e.getMessage(),e);
		}		
		return (mapping.findForward(context));
	}	
	
	
/**
 * getCoinfectionScenarios
 * @param condition
 * @param coinfectionInvList
 * @param securityObj
 * @return hashmap
 * @throws ServletException
 */
			
	
	private HashMap<Object, Object> getCoinfectionScenarios(String condition,
			ArrayList<Object> coinfectionInvList, NBSSecurityObj securityObj) throws ServletException{

		HashMap<Object, Object> returnMap = new HashMap<Object, Object>();
		boolean invEditPerm = true;
		boolean assocLabPermission = true;
		boolean assocMorbPermission = true;
		try{
		if (condition == null || coinfectionInvList == null
				|| coinfectionInvList.size() == 0)
			return null;
		// Check1: check if investigation already exists for same condition for
		// patient and user has associate Permissions
		for (Object cSummaryVO : coinfectionInvList) {
			CoinfectionSummaryVO csVO = (CoinfectionSummaryVO) cSummaryVO;
			if (csVO.getConditionCd() != null
					&& csVO.getConditionCd().equals(condition)) {
				returnMap.put(NEDSSConstants.COINFECTION_LOGIC,
						NEDSSConstants.INVESTIGATION_EXISTS);
				// check for associations permissions
				assocLabPermission = securityObj.getPermission(
						NBSBOLookup.INVESTIGATION,
						NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS,
						csVO.getProgAreaCd(), csVO.getJurisdictionCd());

				returnMap.put(NEDSSConstants.LAB_REPORT_ASSOC_PERM,
						assocLabPermission);

				assocMorbPermission = securityObj
						.getPermission(
								NBSBOLookup.INVESTIGATION,
								NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS,
								csVO.getProgAreaCd(), csVO.getJurisdictionCd());

				returnMap.put(NEDSSConstants.MORB_REPORT_ASSOC_PERM,
						assocMorbPermission);

				returnMap.put(NEDSSConstants.COINFECTION_INV, csVO);
				return returnMap;
			}
		}

		// Check2: check if investigation exists in same co-infection group and
		// user has edit permissions to all the co-infection investigations
		for (Object cSummaryVO : coinfectionInvList) {
			CoinfectionSummaryVO csVO = (CoinfectionSummaryVO) cSummaryVO;
			invEditPerm = securityObj.getPermission(NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.EDIT, csVO.getProgAreaCd(),
					csVO.getJurisdictionCd());
			if (!invEditPerm)
				break;
		}

		returnMap.put(NEDSSConstants.COINFECTION_LOGIC,
				NEDSSConstants.COINFECTION_INV_EXISTS);
		returnMap.put(NEDSSConstants.INV_EDIT_PERM, invEditPerm);
		returnMap
				.put(NEDSSConstants.COINFECTION_INV, coinfectionInvList.get(0));
		}catch(Exception e){
			logger.error("Error while getting coinfection scenarios: "
					+ e.toString());
			throw new ServletException(
					"Error while getting coinfection scenarios:"
							+ e.getMessage(), e);
		}
		
		return returnMap;
	}
	
	private ArrayList<Object> getCandidateCoinfectionList(
			ArrayList<Object> coinfectionList) {
		ArrayList<Object> returnList = new ArrayList<Object>();
		if (coinfectionList == null || coinfectionList.size() == 0)
			return returnList;
		// Since the list is ordered by add time, the first one in the list will
		// be the candidate coinfection Inv
		String candidateCoinfectionID = ((CoinfectionSummaryVO) coinfectionList
				.get(0)).getCoinfectionId();
		for (Object csVO : coinfectionList) {
			CoinfectionSummaryVO summVO = (CoinfectionSummaryVO) csVO;
			if (summVO.getCoinfectionId()!=null && summVO.getCoinfectionId().equals(candidateCoinfectionID))
				returnList.add(csVO);
		}
		return returnList;
	}
}
