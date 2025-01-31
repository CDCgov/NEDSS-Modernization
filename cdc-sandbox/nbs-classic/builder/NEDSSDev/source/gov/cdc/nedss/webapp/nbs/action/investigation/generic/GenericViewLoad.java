package gov.cdc.nedss.webapp.nbs.action.investigation.generic;
/**
 * <p>Title: GenericViewLoad</p>
 * <p>Description: This is a Load action class for view investigation for all the Generic conditions.</p>
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
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.bmird.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.generic.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.hepatitis.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.measles.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.crs.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.pertussis.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.rubella.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;


public class GenericViewLoad
    extends BaseViewLoad
{

    //For logging
    static final LogUtils logger = new LogUtils(GenericViewLoad.class.getName());

     /**
       * This is constructor
       *
       */
    public GenericViewLoad()
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

	HttpSession session = request.getSession(false);
	//context
	String sContextAction = request.getParameter("ContextAction");
	if (sContextAction == null)
	{
	    logger.error("sContextAction is null");
	    session.setAttribute("error", "No Context Action in InvestigationLoad");
	    throw new ServletException("No Context Action in InvestigationLoad");
	}

	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

	boolean checkAutoCreatePermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
								 NBSOperationLookup.AUTOCREATE);

	 boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
								 NBSOperationLookup.VIEW);

	if (sContextAction.equals(NBSConstantUtil.SubmitNoViewAccess) || (checkAutoCreatePermission &&!viewInvestigation))
	{
	    return this.viewConfirmationPage(mapping, form, request, session);
	}
	else
	{
            logger.debug( "before page display of ViewInvestigation in perform ContextAction: |" + sContextAction + "|");
            return this.viewInvestigation(mapping, form, request, session);
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
				     throws ServletException
    {
	//to set securitypermissions for investgations
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	boolean viewContactTracing = false;
	String conditionCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
	String ContactTracingByConditionCd = GenericInvestigationUtil.getConditionTracingEnableInd(conditionCd);
	
	if(ContactTracingByConditionCd.equalsIgnoreCase(NEDSSConstants.CONTACT_TRACING_ENABLE_IND))
		viewContactTracing = true;
	else
		viewContactTracing = false;
	
	boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
								 NBSOperationLookup.VIEW);
 
	if (!viewInvestigation)
	{
	    logger.fatal("Do not have permission to view intervention.");
	    session.setAttribute("Error", " No permission to view intervention.");

	    throw new ServletException("Do not have permission to view intervention.");
	}
	
	if(viewContactTracing)
	viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
            NBSOperationLookup.VIEW);
 
	boolean addContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
            NBSOperationLookup.ADD);
	boolean manageCTPerm = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
            NBSOperationLookup.MANAGE);
	
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
	//String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
	InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();

        //investigationForm.setObservationMap(super.mapObsQA(investigationProxyVO.getTheObservationVOCollection()));
	if (investigationProxyVO != null)
	{

	    Collection<Object>  notificationSummaryVOCollection  = (ArrayList<Object> )investigationProxyVO.getTheNotificationSummaryVOCollection();
	    Collection<Object>  observationSummaryVOCollection  = (ArrayList<Object> )investigationProxyVO.getTheObservationSummaryVOCollection();
	    String sortMethod = "getAddTime";
	    logger.debug("Sort method is:" + sortMethod);

	    if (sortMethod != null && notificationSummaryVOCollection  != null &&
		notificationSummaryVOCollection.size() > 0)
	    {
		logger.debug("List is : " + notificationSummaryVOCollection);

		NedssUtils util = new NedssUtils();
		//Dont need to sort notifications
		//util.sortObjectByColumn(sortMethod, notificationSummaryVOCollection);
	    }

	    if (sortMethod != null && observationSummaryVOCollection  != null &&
		observationSummaryVOCollection.size() > 0)
	    {
		logger.debug("Obs list is : " + observationSummaryVOCollection);

		NedssUtils util = new NedssUtils();
		util.sortObjectByColumn(sortMethod, observationSummaryVOCollection);
	    }
	}
    try {
	this.convertProxyToRequestObj(investigationForm, request);
    }catch(Exception ex) {
    	throw new ServletException(ex.getMessage());
    }
	
	//special case for path from associate to investigation - no Edit or Manage buttons
	if ( sCurrentTask.equalsIgnoreCase("ViewInvestigation7") || sCurrentTask.equalsIgnoreCase("ViewInvestigation8") ||
			sCurrentTask.equalsIgnoreCase("ViewInvestigation9")  ||  sCurrentTask.equalsIgnoreCase("ViewInvestigation10") ||
			sCurrentTask.equalsIgnoreCase("ViewInvestigation11") ||  sCurrentTask.equalsIgnoreCase("ViewInvestigation12") ) 
	{
		request.setAttribute("editInv", "false");
		request.setAttribute("checkManageEvents", "false");
		request.setAttribute("checkTransfer", "false");
		addContactTracing = false;
		manageCTPerm = false;
	}
	String sContextAction = request.getParameter("ContextAction");
	return (this.getForwardPage(sCurrentTask, sContextAction, viewContactTracing, addContactTracing,manageCTPerm,contactCaseUrl,viewFileUrl));

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
	request.setAttribute("homeHref", "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Home"));
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
      * @param investigationForm : InvestigationForm the investigationForm
      * @param request : HttpServletRequest the request
      */
    private void convertProxyToRequestObj(InvestigationForm investigationForm,
					    HttpServletRequest request) throws NEDSSAppException
    {

	InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();
        CachedDropDownValues cdv = new CachedDropDownValues();

        //printAge and printAgeUnits information is REQUIRED for displaying patient Age as header on Printable Forms (PDF)
        PersonDT person = investigationForm.getPatient().getThePersonDT();
        String personAge = person.getAgeReported();
        String ageUnits = cdv.getDescForCode("P_AGE_UNIT", person.getAgeReportedUnitCd());
        request.setAttribute("printAge", personAge);
        request.setAttribute("printAgeUnits", ageUnits);

	HttpSession session = request.getSession(false);
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	super.setSecurityForView(investigationProxyVO, request, nbsSecurityObj);

        //LDF stuff
        String businessObjNm = cdv.getLDFMap(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
        super.createXSP(businessObjNm, investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), investigationProxyVO,investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd(),request);
        super.createXSP(NEDSSConstants.PATIENT_LDF, investigationForm.getOldRevision().getThePersonDT().getPersonUid(), investigationForm.getOldRevision(), null, request);

        //display revision patient tab
        PersonUtil.convertPersonToRequestObj(investigationForm.getOldRevision(), request, "AddPatientFromEvent", new ArrayList<Object>());
        //display investigation
        GenericInvestigationUtil util = new GenericInvestigationUtil();
        util.setGenericRequestForView(investigationProxyVO, request);

    }


      /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      */
    private ActionForward getForwardPage(String sCurrentTask,
					 String sContextAction, boolean viewPerm, boolean addPerm, boolean manageCTPerm,String contactCaseUrl,String viewFileUrl)

    {

	String path = "/error";
		String addpermString = new Boolean(addPerm).toString();
        if (sContextAction.equalsIgnoreCase("PrintPage") && viewPerm)
        	path = "/diseaseform/generic/investigation_generic_print";
        else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	 path = "/diseaseform/generic/investigation_generic_print_no_contact";
        else if(!viewPerm)
        	path = "/diseaseform/generic/investigation_generic_view_No_Contact?CurrentTask=" + sCurrentTask + "&ContextAction=" + sContextAction;

        else
        	path = "/diseaseform/generic/investigation_generic_view?CurrentTask=" + sCurrentTask + "&ContextAction=" + sContextAction +"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+ "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

	return new ActionForward(path);
    }


}