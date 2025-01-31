package gov.cdc.nedss.webapp.nbs.action.investigation.hepatitis;
/**
 * <p>Title: HepatitisEditLoad</p>
 * <p>Description: This is a Load action class for edit investigation for all the Hepatitis conditions.</p>
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
import gov.cdc.nedss.webapp.nbs.action.investigation.util.generic.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.hepatitis.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;


public class HepatitisEditLoad
    extends BaseEditLoad
{

    //For logging
    static final LogUtils logger = new LogUtils(HepatitisEditLoad.class.getName());

    public HepatitisEditLoad()
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

	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	boolean editInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT);
	if (!editInvestigation)
	{
	    session.setAttribute("error", "You do not have access to edit Investigation");
	    throw new ServletException("You do not have access to edit Investigation");
	}


	String sCurrentTask = this.setContextForEdit(request, session);

	String sContextAction = request.getParameter("ContextAction");

        TreeMap<Object,Object> obsmap = super.mapObsQA(investigationForm.getOldProxy().getTheObservationVOCollection());
        investigationForm.setObservationMap(obsmap);
        String conditionCode = investigationForm.getOldProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
        CachedDropDownValues cdv = new CachedDropDownValues();
        String investigationFormCd = convertProxyToRequestObj(form, request, session, cdv);

        String businessObjNm = cdv.getLDFMap(conditionCode);
		if(conditionCode.equalsIgnoreCase("999999"))
			businessObjNm = NEDSSConstants.INVESTIGATION_HEP_LDF;
        //added code for ldf
        if(conditionCode != null && conditionCode.equalsIgnoreCase("999999"))
        	super.createXSP(businessObjNm, investigationForm.getOldProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), investigationForm.getOldProxy(),null,request);
        else
		super.createXSP(NEDSSConstants.INVESTIGATION_HEP_LDF, investigationForm.getOldProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), investigationForm.getOldProxy(),conditionCode,request);
        super.createXSP(NEDSSConstants.PATIENT_LDF, investigationForm.getOldRevision().getThePersonDT().getPersonUid(), investigationForm.getOldRevision(), null, request);
        //there Hep conditions which loads generic hep page but may have specific LDF

        /* Commented out this as per defect 11162: Dont show conditionspecific LDF for formCd "INV_FORM_HEPGEN" and !conditionCode.equals("999999")
        if(investigationFormCd.equalsIgnoreCase("INV_FORM_HEPGEN") && !conditionCode.equals("999999"))
        request.setAttribute("showConditionSpecificLDF", new Boolean(true));
        */
        //display revision patient tab
        GenericInvestigationUtil util = new GenericInvestigationUtil();
        try {
        util.displayRevisionPatient(investigationForm.getOldProxy(), request);
        }catch(Exception ex) {
			throw new ServletException(ex.getMessage());
		}
        investigationForm.setOldRevision(util.getMPRevision(NEDSSConstants.PHC_PATIENT, investigationForm.getOldProxy()));


        //filter DiagnosedConditions based on programAreas
        String programAreaFromSecurityObj = WumUtil.getProgramAreaFromSecurityObj(nbsSecurityObj);
        String filterDiagnosisCondition = cdv.getDiagnosisCodeFilteredOnPA(programAreaFromSecurityObj);
        request.setAttribute("filterDiagnosisCondition", filterDiagnosisCondition);

        //Added for CQ11445
        HepatitisInvestigationUtil.showConditionSpecificLDF(conditionCode, request);

        super.getNBSSecurityJurisdictions(request,nbsSecurityObj);
        boolean viewContactTracing = false;
       
        String ContactTracingByConditionCd = GenericInvestigationUtil.getConditionTracingEnableInd(conditionCode);
    	
    	if(ContactTracingByConditionCd.equalsIgnoreCase(NEDSSConstants.CONTACT_TRACING_ENABLE_IND))
    		viewContactTracing = true;
    	else
    		viewContactTracing = false;
        if(viewContactTracing)
        viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
                NBSOperationLookup.VIEW);
	return (this.getForwardPage(investigationFormCd, sCurrentTask, sContextAction, viewContactTracing));

    }

    private String convertProxyToRequestObj(ActionForm form, HttpServletRequest request, HttpSession session, CachedDropDownValues cdv) throws ServletException
    {

	String conditionCd = "";
        String programAreaCd = "";
	String investigationFormCd = "";

	InvestigationForm investigationForm = (InvestigationForm)form;
	InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();

        Long mprUid = (Long) NBSContext.retrieve(session, NEDSSConstants.DSPatientPersonUID);
	//gov.cdc.nedss.utils.VOTester.createReport(investigationProxyVO, "edit-load");

        conditionCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
        programAreaCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();

        ProgramAreaVO programAreaVO = cdv.getProgramAreaConditionLevel(programAreaCd, conditionCd);
        investigationFormCd = programAreaVO.getInvestigationFormCd();

	if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPA))
	{
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    investigationForm.setSupplementalCollection(null);
	    // checking if this diagnosis has supplimental questions, if not then it is incomplete hep investigation
		//	user is allowed to change the diagnosis with no supplimental observations tied to this investigation
            hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
            //investigationFormCd=NBSConstantUtil.INV_FORM_HEPGEN;	// changing the form cd so that hep core matcher will hit, doesn't change the value of the observation itself
            hepatitisInvestigationUtil.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPB))
	{
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    investigationForm.setSupplementalCollection(null);
	    hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
	    //	investigationFormCd=NBSConstantUtil.INV_FORM_HEPGEN;
	    hepatitisInvestigationUtil.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPBV))
	{
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    investigationForm.setSupplementalCollection(null);
	    hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
	    //	investigationFormCd=NBSConstantUtil.INV_FORM_HEPGEN;
	    hepatitisInvestigationUtil.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPC))
	{
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    investigationForm.setSupplementalCollection(null);
	    hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
	    //	investigationFormCd=NBSConstantUtil.INV_FORM_HEPGEN;
	    hepatitisInvestigationUtil.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPCV))
	{
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    investigationForm.setSupplementalCollection(null);
	    hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
	    //	investigationFormCd=NBSConstantUtil.INV_FORM_HEPGEN;
	    hepatitisInvestigationUtil.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
	}


	else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPGEN))
	{
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    investigationForm.setSupplementalCollection(null);
	    hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
	    hepatitisInvestigationUtil.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
	}

	return investigationFormCd;
    }


    private ActionForward getForwardPage(String investigationFormCd, String sCurrentTask,
					 String sContextAction, boolean viewContactTracing)
    {
	String path = "/error";

	//Hepatitis specific conditions
	if (NBSConstantUtil.INV_FORM_HEPA.equals(investigationFormCd)&& viewContactTracing)
		    path = "/diseaseform/hepatitis/hep_acute_a_edit?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;
	else if (NBSConstantUtil.INV_FORM_HEPB.equals(investigationFormCd)&& viewContactTracing)
	    path = "/diseaseform/hepatitis/hep_acute_b_edit?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;
	else if (NBSConstantUtil.INV_FORM_HEPC.equals(investigationFormCd)&& viewContactTracing)
		    path = "/diseaseform/hepatitis/hep_acute_c_edit?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;
	else if (NBSConstantUtil.INV_FORM_HEPBV.equals(investigationFormCd)&& viewContactTracing)
	    path = "/diseaseform/hepatitis/hep_perinatal_HBV_infection_edit?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;
	else if (NBSConstantUtil.INV_FORM_HEPCV.equals(investigationFormCd)&& viewContactTracing)
	    path = "/diseaseform/hepatitis/hep_HCV_infection_edit?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;

	else if (NBSConstantUtil.INV_FORM_HEPGEN.equals(investigationFormCd)&& viewContactTracing)
	    path = "/diseaseform/hepatitis/hep_core_edit?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;// from here no contact
	else if (NBSConstantUtil.INV_FORM_HEPA.equals(investigationFormCd)&& !viewContactTracing)
	    path = "/diseaseform/hepatitis/hep_acute_a_edit_No_Contact?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;
	else if (NBSConstantUtil.INV_FORM_HEPB.equals(investigationFormCd)&& !viewContactTracing)
	    path = "/diseaseform/hepatitis/hep_acute_b_edit_No_Contact?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;
	else if (NBSConstantUtil.INV_FORM_HEPC.equals(investigationFormCd)&& !viewContactTracing)
		    path = "/diseaseform/hepatitis/hep_acute_c_edit_No_Contact?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;
	else if (NBSConstantUtil.INV_FORM_HEPBV.equals(investigationFormCd)&& !viewContactTracing)
	    path = "/diseaseform/hepatitis/hep_perinatal_HBV_infection_edit_No_Contact?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;
	else if (NBSConstantUtil.INV_FORM_HEPCV.equals(investigationFormCd)&& !viewContactTracing)
	    path = "/diseaseform/hepatitis/hep_HCV_infection_edit_No_Contact?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;

	else if (NBSConstantUtil.INV_FORM_HEPGEN.equals(investigationFormCd)&& !viewContactTracing)
	    path = "/diseaseform/hepatitis/hep_core_edit_No_Contact?CurrentTask=" + sCurrentTask + "&ContextAction=" +
		   sContextAction;

	return new ActionForward(path);
    }





}