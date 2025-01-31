package gov.cdc.nedss.webapp.nbs.action.investigation.bmird;
/**
 *
 * <p>Title: BmirdEditLoad</p>
 * <p>Description: This is a load action class for Edit Investigation for the Bmird conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.bmird.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.generic.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;


public class BmirdEditLoad
    extends BaseEditLoad
{

    //For logging
    static final LogUtils logger = new LogUtils(BmirdEditLoad.class.getName());
    /**
     * empty constructor
     */
    public BmirdEditLoad()
    {
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
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {
	InvestigationForm investigationForm = (InvestigationForm)form;
	HttpSession session = request.getSession(false);
	boolean viewContactTracing = false;
	
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	boolean editInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT);
	if (!editInvestigation)
	{
	    session.setAttribute("error", "You do not have access to edit Investigation");
	    throw new ServletException("You do not have access to edit Investigation");
	}

	String sCurrentTask = super.setContextForEdit(request, session);
	String sContextAction = request.getParameter("ContextAction");
	String investigationFormCd = null;
	try {

        String conditionCode = investigationForm.getOldProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
        CachedDropDownValues cdv = new CachedDropDownValues();
        String businessObjNm = cdv.getLDFMap(conditionCode);
        request.setAttribute("showConditionSpecificLDF", new Boolean(false));

        super.createXSP(businessObjNm,investigationForm.getOldProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), investigationForm.getOldProxy(), investigationForm.getOldProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd(),request);
        super.createXSP(NEDSSConstants.PATIENT_LDF, investigationForm.getOldRevision().getThePersonDT().getPersonUid(), investigationForm.getOldRevision(), null, request);

        investigationFormCd = this.convertProxyToRequestObj(form, request, session, cdv);
        TreeMap<Object,Object> obsmap = super.mapObsQA(investigationForm.getOldProxy().getTheObservationVOCollection());
        investigationForm.setObservationMap(obsmap);

	//for ABC states display additional questions for BMIRD only
	this.setABCStateIndicator(request);
        //added code for ldf

        //display revision patient tab
        GenericInvestigationUtil util = new GenericInvestigationUtil();
        util.displayRevisionPatient(investigationForm.getOldProxy(), request);
        investigationForm.setOldRevision(util.getMPRevision(NEDSSConstants.PHC_PATIENT, investigationForm.getOldProxy()));

        //filter the diagnosis based on SecurityObj ProgramArea
        String programAreaFromSecurityObj = WumUtil.getProgramAreaFromSecurityObj(nbsSecurityObj);
        String filterDiagnosisCondition = cdv.getDiagnosisCodeFilteredOnPA(programAreaFromSecurityObj);
        request.setAttribute("filterDiagnosisCondition", filterDiagnosisCondition);

        super.getNBSSecurityJurisdictions(request,nbsSecurityObj);
        
        String ContactTracingByConditionCd = GenericInvestigationUtil.getConditionTracingEnableInd(conditionCode);
    	
    	if(ContactTracingByConditionCd.equalsIgnoreCase(NEDSSConstants.CONTACT_TRACING_ENABLE_IND))
    		viewContactTracing = true;
    	else
    		viewContactTracing = false;
       if(viewContactTracing)
    	viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
                NBSOperationLookup.VIEW);
	}catch (Exception e) {
		logger.error("Exception in Bmird Edit Load: " + e.toString());
		e.printStackTrace();
		throw new ServletException("Error during Bmird Edit Load : "+e.getMessage());
	}
	return (this.getForwardPage(investigationFormCd, sCurrentTask, sContextAction, viewContactTracing));

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
    private String convertProxyToRequestObj(ActionForm form, HttpServletRequest request, HttpSession session, CachedDropDownValues cdv) throws ServletException
    {

	String conditionCd = "";
	String investigationFormCd = "";
	String programAreaCd = "";
        ProgramAreaVO programAreaVO = null;
	InvestigationForm investigationForm = (InvestigationForm)form;
	InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();

        conditionCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
        programAreaCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
        programAreaVO = cdv.getProgramAreaConditionLevel(programAreaCd, conditionCd);
        investigationFormCd = programAreaVO.getInvestigationFormCd();

        //set for BMIRD pathegen readonly in edit BMIRD
        if(conditionCd.equals("10150") || conditionCd.equals("10590") || conditionCd.equals("11710") || conditionCd.equals("11715") || conditionCd.equals("11717"))
        request.setAttribute("hasPathogen", "true");

        Long mprUid = (Long) NBSContext.retrieve(session, NEDSSConstants.DSPatientPersonUID);
	if (NBSConstantUtil.INV_FORM_BMDGEN.equals(investigationFormCd))
	{
	BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
	investigationForm.setSupplementalCollection(null);
	biu.setGenericRequestForView(investigationForm.getOldProxy(), request);
	biu.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
        biu.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (NBSConstantUtil.INV_FORM_BMDGAS.equals(investigationFormCd))
	{
	BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
	biu.setGenericRequestForView(investigationForm.getOldProxy(), request);
	biu.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
        biu.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (NBSConstantUtil.INV_FORM_BMDGBS.equals(investigationFormCd))
	{
	BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
	biu.setBatchEntryToFormForView(investigationForm);
	biu.setGenericRequestForView(investigationForm.getOldProxy(), request);
	biu.convertBatchEntryToRequestForGBS(investigationForm, request);
	biu.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
        biu.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (NBSConstantUtil.INV_FORM_BMDHI.equals(investigationFormCd))
	{
	BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
	biu.setGenericRequestForView(investigationForm.getOldProxy(), request);
	biu.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
        biu.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (NBSConstantUtil.INV_FORM_BMDNM.equals(investigationFormCd))
	{
	BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
	biu.setGenericRequestForView(investigationForm.getOldProxy(), request);
	biu.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
        biu.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (NBSConstantUtil.INV_FORM_BMDSP.equals(investigationFormCd))
	{
	BMIRDInvestigationUtil biu = new BMIRDInvestigationUtil();
	biu.setBatchEntryToFormForView(investigationForm);
	biu.setGenericRequestForView(investigationForm.getOldProxy(), request);
	biu.convertBatchEntryToRequestForSP(investigationForm, request);
	biu.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
        biu.getVaccinationSummaryRecords(mprUid,request);
	}
	logger.debug("Investigation Edit Load class investigationFormCd: " + investigationFormCd);

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
    private ActionForward getForwardPage(String investigationFormCd, String sCurrentTask,
					 String sContextAction, boolean viewContactTracing)
    {

	String path = "/error";

	if (NBSConstantUtil.INV_FORM_BMDGEN.equals(investigationFormCd) && viewContactTracing)
	    path = "/diseaseform/bmird/generic_edit?CurrentTask=" + sCurrentTask +
		   "&ContextAction=" + sContextAction + "&mode=edit";
	else if (NBSConstantUtil.INV_FORM_BMDGAS.equals(investigationFormCd) && viewContactTracing)
	    path = "/diseaseform/bmird/strepa_edit?CurrentTask=" + sCurrentTask +
		   "&ContextAction=" + sContextAction + "&mode=edit";
	else if (NBSConstantUtil.INV_FORM_BMDGBS.equals(investigationFormCd)&& viewContactTracing)
          path = "/diseaseform/bmird/generic_edit?CurrentTask=" + sCurrentTask +
                 "&ContextAction=" + sContextAction + "&mode=edit";
        // as per enhancement requst 10431 always do not display GroupB strep, display generic Bmird
          //path = "/diseaseform/bmird/strepb_edit?CurrentTask=" + sCurrentTask +
		//   "&ContextAction=" + sContextAction + "&mode=edit";
	else if (NBSConstantUtil.INV_FORM_BMDHI.equals(investigationFormCd)&& viewContactTracing)
	    path = "/diseaseform/bmird/hi_edit?CurrentTask=" + sCurrentTask +
		   "&ContextAction=" + sContextAction + "&mode=edit";
	else if (NBSConstantUtil.INV_FORM_BMDNM.equals(investigationFormCd)&& viewContactTracing)
	    path = "/diseaseform/bmird/nm_edit?CurrentTask=" + sCurrentTask +
		   "&ContextAction=" + sContextAction + "&mode=edit";
	else if (NBSConstantUtil.INV_FORM_BMDSP.equals(investigationFormCd)&& viewContactTracing)
	    path = "/diseaseform/bmird/sp_edit?CurrentTask=" + sCurrentTask +
		   "&ContextAction=" + sContextAction + "&mode=edit";// from here no contact
	else if (NBSConstantUtil.INV_FORM_BMDGEN.equals(investigationFormCd) && !viewContactTracing)
	    path = "/diseaseform/bmird/generic_edit_No_Contact?CurrentTask=" + sCurrentTask +
		   "&ContextAction=" + sContextAction + "&mode=edit";
	else if (NBSConstantUtil.INV_FORM_BMDGAS.equals(investigationFormCd) && !viewContactTracing)
	    path = "/diseaseform/bmird/strepa_edit_No_Contact?CurrentTask=" + sCurrentTask +
		   "&ContextAction=" + sContextAction + "&mode=edit";
	else if (NBSConstantUtil.INV_FORM_BMDGBS.equals(investigationFormCd)&& !viewContactTracing)
       path = "/diseaseform/bmird/generic_edit_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=edit";
     // as per enhancement requst 10431 always do not display GroupB strep, display generic Bmird
       //path = "/diseaseform/bmird/strepb_edit?CurrentTask=" + sCurrentTask +
		//   "&ContextAction=" + sContextAction + "&mode=edit";
	else if (NBSConstantUtil.INV_FORM_BMDHI.equals(investigationFormCd)&& !viewContactTracing)
	    path = "/diseaseform/bmird/hi_edit_No_Contact?CurrentTask=" + sCurrentTask +
		   "&ContextAction=" + sContextAction + "&mode=edit";
	else if (NBSConstantUtil.INV_FORM_BMDNM.equals(investigationFormCd)&& !viewContactTracing)
	    path = "/diseaseform/bmird/nm_edit_No_Contact?CurrentTask=" + sCurrentTask +
		   "&ContextAction=" + sContextAction + "&mode=edit";
	else if (NBSConstantUtil.INV_FORM_BMDSP.equals(investigationFormCd)&& !viewContactTracing)
	    path = "/diseaseform/bmird/sp_edit_No_Contact?CurrentTask=" + sCurrentTask +
		   "&ContextAction=" + sContextAction + "&mode=edit";

	logger.debug(" path: " + path);

	return new ActionForward(path);
    }
    /**
    * this method reads the values from PropertyUtil sets the default values for Bmird
    * also sets the default values based on age of person
    * @param personVO : PersonVO
    * @param request : HttpServletRequest
    */
    private void setABCStateIndicator(HttpServletRequest request)
    {
	String abcIndicator = PropertyUtil.getInstance().getABCSTATE();
	logger.debug("abcIndicator: " + abcIndicator);
	if(abcIndicator != null && abcIndicator.trim().equals("ABCSTATE_ABCQUESTION"))
	{
	   logger.debug("ABCSTATE_ABCQUESTION abcIndicator: " + abcIndicator);
	   request.setAttribute("abcCheckbox", "T");
	   request.setAttribute("abcsInd", "T");
	}
	else if(abcIndicator != null && abcIndicator.trim().equals("ABCQUESTION"))
	{
	   logger.debug("ABCQUESTION abcIndicator: " + abcIndicator);
	   request.setAttribute("abcCheckbox", "F");
	   request.setAttribute("abcsInd", "T");
	}
	else if(abcIndicator != null && abcIndicator.trim().equals("NONE"))
	{
	   logger.debug("NONE abcIndicator: " + abcIndicator);
	   request.setAttribute("abcCheckbox", "F");
	   request.setAttribute("abcsInd", "F");
	}
	else
	{
	   logger.debug("else abcIndicator: " + abcIndicator);
	   request.setAttribute("abcCheckbox", "F");
	   request.setAttribute("abcsInd", "F");
	}
    }




}