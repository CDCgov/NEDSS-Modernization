package gov.cdc.nedss.webapp.nbs.action.investigation.crs;
/**
 *
 * <p>Title: CrsViewLoad</p>
 * <p>Description: This is a Load action class for view investigation for the Congenital Rubella Syndrome conditions.</p>
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


public class CrsViewLoad
    extends BaseViewLoad
{

    //For logging
    static final LogUtils logger = new LogUtils(CrsViewLoad.class.getName());

     /**
       * This is constructor
       *
       */
    public CrsViewLoad()
    {
    }

    /**
      * Get values from investigation form and forward to next action.
      * @param mapping : ActionMapping the mapping
      * @param form : ActionForm the form contain values
      * @param request : HttpServletRequest the request
      * @param response : HttpServletResponse the response
      * @return  ActionForward Object
      * @throws IOException
      * @throws ServletException
      */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {

	HttpSession session = request.getSession(false);
	//context
	String sContextAction = request.getParameter("ContextAction");
	if (sContextAction == null)
	{
	    logger.error("sContextAction is null");
	    session.setAttribute("error", "No Context Action in InvestigationLoad");
	    throw new ServletException("sContextAction is null");
	}
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

	boolean checkInvestigationAutoCreatePermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.AUTOCREATE);

        boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);


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

    /**
      * Get values from investigation form and stored to Object.
      * @param mapping : ActionMapping the mapping
      * @param form : ActionForm the form contain values
      * @param request : HttpServletRequest the request
      * @param session : HttpSession
      * @return  ActionForward : to the calling method with ActionForward Object
      * @throws Exception
      */
    private ActionForward viewInvestigation(ActionMapping mapping, ActionForm form,
					    HttpServletRequest request, HttpSession session)
				     throws Exception
    {
	//to set security permissions for investigations
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
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

	boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);

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

    	try {

    		InvestigationForm investigationForm = (InvestigationForm)form;
    		String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
    		InvestigationProxyVO investigationProxyVO =investigationForm.getOldProxy();

    		//investigationForm.setObservationMap(super.mapObsQA(investigationProxyVO.getTheObservationVOCollection()));

    		this.setSecurityForView(investigationProxyVO, request, nbsSecurityObj);
    		//added code for ldf
    		//createXSP("PHC",investigationForm.getOldProxy(),investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd(),request);
    		CachedDropDownValues cdv = new CachedDropDownValues();
    		String businessObjNm = cdv.getLDFMap(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
    		String code = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();

    		super.createXSP(businessObjNm, investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), investigationProxyVO,investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd(),request);
    		super.createXSP(NEDSSConstants.PATIENT_LDF, investigationForm.getOldRevision().getThePersonDT().getPersonUid(), investigationForm.getOldRevision(), null, request);
    		//display revision patient tab
    		PersonUtil.convertPersonToRequestObj(investigationForm.getPatient(), request,
    				"AddPatientFromEvent", new ArrayList<Object>());

    		Long mprUid = investigationForm.getPatient().getThePersonDT().getPersonParentUid();
    		CRSInvestigationUtil crsInvestigationUtil = new CRSInvestigationUtil();
    		crsInvestigationUtil.setGenericRequestForView(investigationProxyVO, request);
    		crsInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
    	} catch (Exception ex) {
    		logger.error("Error happened in viewInvestigation: "+ex.getMessage());
    		ex.printStackTrace();
    		throw ex;
    	}
	String sContextAction = request.getParameter("ContextAction");
	return this.getForwardPage(sCurrentTask, sContextAction,viewContactTracing, addContactTracing, manageCTPerm,contactCaseUrl,viewFileUrl);
    }

      /**
      * Get values from investigation form and stored to Object.
      * @param sCurrentTask : String
      * @param sContextAction : String
      * @return  to the calling method with ActionForward Object
      */
    private ActionForward getForwardPage(String sCurrentTask,
					 String sContextAction, boolean viewPerm, boolean addPerm,boolean manageCTPerm ,String contactCaseUrl,String viewFileUrl)
    {

	String path = "";
	String addpermString = new Boolean(addPerm).toString();

        if (sContextAction.equalsIgnoreCase("PrintPage") && viewPerm)
            path = "/diseaseform/nip/crs/investigation_crs_print";
        else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	path = "/diseaseform/nip/crs/investigation_crs_print_no_contact";
        else if(!viewPerm)
        	path = "/diseaseform/nip/crs/investigation_crs_view_No_Contact?CurrentTask=" +
            sCurrentTask +  "&ContextAction=" + sContextAction;
        else
            path = "/diseaseform/nip/crs/investigation_crs_view?CurrentTask=" +
              sCurrentTask +  "&ContextAction=" + sContextAction +"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;


	return new ActionForward(path);
    }

}