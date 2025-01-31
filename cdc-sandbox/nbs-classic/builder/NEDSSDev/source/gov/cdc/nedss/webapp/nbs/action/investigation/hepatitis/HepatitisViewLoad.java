package gov.cdc.nedss.webapp.nbs.action.investigation.hepatitis;
/**
 *
 * <p>Title: HepatitisViewLoad</p>
 * <p>Description: This is a Load action class for view investigation for the Hepatitis conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.bmird.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.generic.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.hepatitis.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.measles.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.crs.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.pertussis.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.rubella.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;

/**
 * Title:        InvestigationViewLoad is a class
 * Description:  This class retrieves data from EJB and puts them into request
 *               object for use in the xml file
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Jay Kim
 * @version      1.0
 */

public class HepatitisViewLoad
    extends BaseViewLoad
{

    //For logging
    static final LogUtils logger = new LogUtils(HepatitisViewLoad.class.getName());

     /**
       * This is constructor
       *
       */
    public HepatitisViewLoad()
    {
    }

    /**
      * Get values from investigation form and forward to next action.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  ActionForward Object
      * @throws ServletException and IOException
      */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {

	logger.debug("inside the investigation View Load");
	HttpSession session = request.getSession(false);

	String sContextAction = request.getParameter("ContextAction");
	logger.info("sContextAction in InvestigationLoad = " + sContextAction);

	if (sContextAction == null)
	{
	    logger.error("sContextAction is null");
	    session.setAttribute("error", "No Context Action in InvestigationLoad");
	    throw new ServletException("sContextAction is null");
	}
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

	boolean autoCreatePermission = nbsSecurityObj.getPermission(
								 NBSBOLookup.INVESTIGATION,
								 NBSOperationLookup.AUTOCREATE);

	 boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
								 NBSOperationLookup.VIEW);

	if (sContextAction.equals(NBSConstantUtil.SubmitNoViewAccess) || (autoCreatePermission &&!viewInvestigation))
	{
	    return this.viewConfirmationPage(mapping, form, request, session);
	}
	else
	{
            logger.debug( "before page display of ViewInvestigation in execute ContextAction: |" + sContextAction + "|");
            try
            {
              return this.viewInvestigation(mapping, form, request, session);
            }
            catch (Exception ex)
            {
              logger.error(ex.toString());
              return mapping.findForward(NEDSSConstants.ERROR_PAGE);
            }
	}
    }

    /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      * @throws ServletException
      */
    private ActionForward viewInvestigation(ActionMapping mapping, ActionForm form,
					    HttpServletRequest request, HttpSession session)
				     throws Exception
    {

	//to set securitypermissions for investgations
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

	 boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
								 NBSOperationLookup.VIEW);
	 boolean viewContactTracing = false;
     String conditionCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
     String ContactTracingByConditionCd = GenericInvestigationUtil.getConditionTracingEnableInd(conditionCd);
 	
 	if(ContactTracingByConditionCd.equalsIgnoreCase(NEDSSConstants.CONTACT_TRACING_ENABLE_IND))
 		viewContactTracing = true;
 	else
 		viewContactTracing = false;
     if(viewContactTracing) 
	 viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
	            NBSOperationLookup.VIEW);
	 
	boolean addContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
	            NBSOperationLookup.ADD);
	
	boolean manageCTPerm = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
            NBSOperationLookup.MANAGE);


	if (!viewInvestigation)
	{
	    logger.fatal("Do not have permission to view intervention.");
	    session.setAttribute("Error", " No permission to view intervention.");

	    throw new ServletException("Do not have permission to view intervention.");
	}

	Map<Object, Object> map = super.setContextForView(request, session);
	
	String sCurrentTask = null;
	
	if(map.get("sCurrentTask") != null)
		sCurrentTask = (String)map.get("sCurrentTask");
	
	String contactCaseUrl="";
	String 	viewFileUrl ="";
	if(map.get("urlForViewFile")!=null){
		viewFileUrl =map.get("urlForViewFile").toString();
	}
	if(map.get("ContactCase")!=null){
		contactCaseUrl =map.get("ContactCase").toString();

	}


	InvestigationForm investigationForm = (InvestigationForm)form;
	String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
	InvestigationProxyVO investigationProxyVO = this.getOldProxyObject(sPublicHealthCaseUID,  investigationForm,  session);

        //investigationForm.setObservationMap(super.mapObsQA(investigationProxyVO.getTheObservationVOCollection()));

	String investigationFormCd = this.convertProxyToRequestObj(investigationForm, request);

	String sContextAction = request.getParameter("ContextAction");
	logger.debug(" before display of createMeasles page");
        //session.setAttribute("beforeXSPLoad", String.valueOf(new Date().getTime()));
	return (this.getForwardPage(investigationFormCd, sCurrentTask, sContextAction, viewContactTracing, addContactTracing,manageCTPerm, contactCaseUrl,viewFileUrl));

    }



    /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      * @throws ServletException
      */
    private ActionForward viewConfirmationPage(ActionMapping mapping, ActionForm form,
					       HttpServletRequest request, HttpSession session)
					throws ServletException
    {

	String investigationId = "";

	//to set securitypermissions for investgations
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	String sContextAction = request.getParameter("ContextAction");

	TreeMap<Object, Object> tm = NBSContext.getPageContext(session, "PS155", sContextAction);
	String sCurrentTask = NBSContext.getCurrentTask(session);
	String investigationIdContext  =  (String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationUid);
	Long investigationUID = new Long(investigationIdContext);
	String jurisdiction = (String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationJurisdiction);
	String programArea = (String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationProgramArea);
	request.setAttribute("homeHref",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Home"));
	// Here we are getting the publicHealthCaseLocalID .. Have to rename InvestigationUid to InvestigationLocalId
	try
	{
	    investigationId = this.getPublicHealthCaseLocalID(investigationUID,session,request);
	}
	catch( Exception e)
	{
	    throw new ServletException(e.toString());
	}
	logger.debug("The value of local Id "+ investigationId);
	request.setAttribute("InvestigationUid", investigationId);
	request.setAttribute("programArea", programArea);
	request.setAttribute("jurisdiction", jurisdiction);

	return mapping.findForward("XSP");
    }



     /**
      * Get investigationForm from the servlet request object for next call.
      * @param InvestigationForm the investigationForm
      * @param HttpServletRequest the request
      */
    private String convertProxyToRequestObj(InvestigationForm investigationForm,
					    HttpServletRequest request) throws NEDSSAppException
    {


	InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();

	HttpSession session = request.getSession(false);
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	this.setSecurityForView(investigationProxyVO, request, nbsSecurityObj);
        //added code for ldf
        CachedDropDownValues cdv = new CachedDropDownValues();
        String businessObjNm = cdv.getLDFMap(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
        String conditionCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
        if(conditionCd.equalsIgnoreCase("999999"))
	        businessObjNm = NEDSSConstants.INVESTIGATION_HEP_LDF;
        request.setAttribute("businessObjNm", businessObjNm);
        if(conditionCd != null && conditionCd.equalsIgnoreCase("999999"))
        	super.createXSP(businessObjNm, investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), investigationProxyVO,null,request);
        else
			super.createXSP(NEDSSConstants.INVESTIGATION_HEP_LDF, investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), investigationProxyVO, conditionCd, request);
        super.createXSP(NEDSSConstants.PATIENT_LDF, investigationForm.getOldRevision().getThePersonDT().getPersonUid(), investigationForm.getOldRevision(), null, request);

        //display revision patient tab
        PersonUtil.convertPersonToRequestObj(investigationForm.getPatient(), request,
                                             "AddPatientFromEvent", new ArrayList<Object>());


       conditionCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
       String programAreaCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
       ProgramAreaVO programAreaVO = cdv.getProgramAreaConditionLevel(programAreaCd, conditionCd);
       String investigationFormCd = investigationFormCd = programAreaVO.getInvestigationFormCd();
       //there Hep conditions which loads generic hep page but may have specific LDF
       if(investigationFormCd.equalsIgnoreCase("INV_FORM_HEPGEN") && !conditionCd.equals("999999"))
       request.setAttribute("showConditionSpecificLDF", new Boolean(true));

       String sCurrentTask = NBSContext.getCurrentTask(session);


       String programAreaFromSecurityObj = WumUtil.getProgramAreaFromSecurityObj(nbsSecurityObj);
       String filterDiagnosisCondition = cdv.getDiagnosisCodeFilteredOnPA(programAreaFromSecurityObj);
       request.setAttribute("filterDiagnosisCondition", filterDiagnosisCondition);

      Long mprUid = investigationForm.getOldRevision().getThePersonDT().getPersonParentUid();

      if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPA))
      {
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    // checking if this diagnosis has supplimental questions, if not then it is incomplete hep investigation
	    //	user is allowed to change the diagnosis with no supplimental observations tied to this investigation
	    hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
	    //	investigationFormCd=NBSConstantUtil.INV_FORM_HEPGEN;	// changing the form cd so that hep core matcher will hit, doesn't change the value of the observation itself
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
            if(request.getAttribute("ContactTabtoFocus")!=null)
              	 request.setAttribute("DSFileTab", "4");
      }
      else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPB))
      {
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
	    //	investigationFormCd=NBSConstantUtil.INV_FORM_HEPGEN;
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
            if(request.getAttribute("ContactTabtoFocus")!=null)
           	 request.setAttribute("DSFileTab", "4");
      }
      else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPBV))
      {
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
	    //	investigationFormCd=NBSConstantUtil.INV_FORM_HEPGEN;
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
            if(request.getAttribute("ContactTabtoFocus")!=null)
           	 request.setAttribute("DSFileTab", "4");
      }
      else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPC))
      {
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
	    //	investigationFormCd=NBSConstantUtil.INV_FORM_HEPGEN;
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
            if(request.getAttribute("ContactTabtoFocus")!=null)
              	 request.setAttribute("DSFileTab", "4");
      }
      else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPCV))
      {
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
	    //	investigationFormCd=NBSConstantUtil.INV_FORM_HEPGEN;
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
            if(request.getAttribute("ContactTabtoFocus")!=null)
              	 request.setAttribute("DSFileTab", "4");
      }
      else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_HEPGEN))
      {
	    HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
	    hepatitisInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
            hepatitisInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
      }

	return investigationFormCd;
    }



      /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      */
    private ActionForward getForwardPage(String investigationFormCd, String sCurrentTask,
					 String sContextAction, boolean viewPerm, boolean addPerm,boolean manageCTPerm,String contactCaseUrl,String viewFileUrl)

    {
	logger.debug("in getForwardPage investigationFormCd: " + investigationFormCd +
		" sCurrentTask: " + sCurrentTask + " sContextAction: " + sContextAction);

	String path = "/error";
	String addpermString = new Boolean(addPerm).toString();
	

	if (NBSConstantUtil.INV_FORM_GEN.equals(investigationFormCd)) {

	  if (sContextAction.equalsIgnoreCase("PrintPage") && viewPerm)
		path = "/diseaseform/generic/investigation_generic_print";
	  else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
		  path = "/diseaseform/generic/investigation_generic_print_no_contact";
	  else if(!viewPerm)
		  path = "/diseaseform/generic/investigation_generic_view_No_Contact?CurrentTask=" + sCurrentTask + "&ContextAction=" + sContextAction;

	  else
		path = "/diseaseform/generic/investigation_generic_view?CurrentTask=" + sCurrentTask + "&ContextAction=" + sContextAction +"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
	}
	else if (NBSConstantUtil.INV_FORM_MEA.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/measles/investigation_measles_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/measles/investigation_measles_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/measles/investigation_measles_view_No_Contact?CurrentTask=" +sCurrentTask +"&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/measles/investigation_measles_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm;
        }
	else if (NBSConstantUtil.INV_FORM_CRS.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/nip/crs/investigation_crs_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/nip/crs/investigation_crs_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/nip/crs/investigation_crs_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/nip/crs/investigation_crs_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm;
        }
	else if (NBSConstantUtil.INV_FORM_RUB.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/nip/rubella/investigation_rubella_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/nip/rubella/investigation_rubella_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/nip/rubella/investigation_rubella_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/nip/rubella/investigation_rubella_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm;
        }
	else if (NBSConstantUtil.INV_FORM_PER.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/nip/pertussis/investigation_pertussis_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/nip/pertussis/investigation_pertussis_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/nip/pertussis/investigation_pertussis_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/nip/pertussis/investigation_pertussis_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm;
        }
	//Hepatitis specific conditions
        else if (NBSConstantUtil.INV_FORM_HEPGEN.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/hepatitis/hep_core_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/hepatitis/hep_core_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/hepatitis/hep_core_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/hepatitis/hep_core_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
        }

	else if (NBSConstantUtil.INV_FORM_HEPA.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/hepatitis/hep_acute_a_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/hepatitis/hep_acute_a_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/hepatitis/hep_acute_a_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/hepatitis/hep_acute_a_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
        }
	else if (NBSConstantUtil.INV_FORM_HEPB.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
        	  path = "/diseaseform/hepatitis/hep_acute_b_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/hepatitis/hep_acute_b_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/hepatitis/hep_acute_b_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
        	  path = "/diseaseform/hepatitis/hep_acute_b_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
        }
	else if (NBSConstantUtil.INV_FORM_HEPC.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
        	  path = "/diseaseform/hepatitis/hep_acute_c_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/hepatitis/hep_acute_c_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/hepatitis/hep_acute_c_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
        	  path = "/diseaseform/hepatitis/hep_acute_c_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
        }
	else if (NBSConstantUtil.INV_FORM_HEPBV.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/hepatitis/hep_perinatal_HBV_infection_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/hepatitis/hep_perinatal_HBV_infection_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/hepatitis/hep_perinatal_HBV_infection_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/hepatitis/hep_perinatal_HBV_infection_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
        }
	else if (NBSConstantUtil.INV_FORM_HEPCV.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/hepatitis/hep_HCV_infection_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/hepatitis/hep_HCV_infection_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/hepatitis/hep_HCV_infection_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/hepatitis/hep_HCV_infection_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
        }

	logger.debug(" path: " + path);

	return new ActionForward(path);
    }


}