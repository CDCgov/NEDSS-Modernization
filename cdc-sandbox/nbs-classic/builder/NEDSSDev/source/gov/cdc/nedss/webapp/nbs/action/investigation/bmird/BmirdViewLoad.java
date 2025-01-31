
package gov.cdc.nedss.webapp.nbs.action.investigation.bmird;
/**
 *
 * <p>Title: BmirdViewLoad</p>
 * <p>Description: This is a Load action class for view Invesigation for the Bmird conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import java.io.*;
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
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;


public class BmirdViewLoad
    extends BaseViewLoad
{

    //For logging
    static final LogUtils logger = new LogUtils(BmirdViewLoad.class.getName());

     /**
       * This is constructor
       *
       */
    public BmirdViewLoad()
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
	    session.setAttribute("error", "No Context Action in InvestigationLoad");
	    throw new ServletException("No Context Action in InvestigationLoad");
	}
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

	boolean autoCreatePermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.AUTOCREATE);

	boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);

	if (sContextAction.equals(NBSConstantUtil.SubmitNoViewAccess) || (autoCreatePermission &&!viewInvestigation))
	{
	    return this.viewConfirmationPage(mapping, form, request, session);
	}
	else
	{
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

        boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
								 NBSOperationLookup.VIEW);
        boolean viewContactTracing = false;
        String investigationFormCd = null;
        String sCurrentTask = null;
        boolean addContactTracing = false;
        boolean manageCT = false;
    	String contactCaseUrl="";
    	String 	viewFileUrl ="";
        
    	try{
    		String conditionCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
    		String ContactTracingByConditionCd = GenericInvestigationUtil.getConditionTracingEnableInd(conditionCd);

    		if(ContactTracingByConditionCd.equalsIgnoreCase(NEDSSConstants.CONTACT_TRACING_ENABLE_IND))
    			viewContactTracing = true;
    		else
    			viewContactTracing = false;
    		if(viewContactTracing)
    			viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
    					NBSOperationLookup.VIEW);

    		addContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
    				NBSOperationLookup.ADD);
    		manageCT = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
    				NBSOperationLookup.MANAGE);

    		if (!viewInvestigation)
    		{
    			logger.fatal("Do not have permission to view intervention.");
    			session.setAttribute("Error", " No permission to view intervention.");

    			throw new ServletException("Do not have permission to view intervention.");
    		}


    		Map<Object, Object> map = super.setContextForView(request, session);



    		if(map.get("sCurrentTask") != null)
    			sCurrentTask = (String)map.get("sCurrentTask");


    		if(map.get("urlForViewFile")!=null){
    			viewFileUrl =map.get("urlForViewFile").toString();
    		}
    		if(map.get("ContactCase")!=null){
    			contactCaseUrl =map.get("ContactCase").toString();

    		}



    		InvestigationForm investigationForm = (InvestigationForm)form;
    		String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
    		//InvestigationProxyVO investigationProxyVO = this.getOldProxyObject(sPublicHealthCaseUID,  investigationForm,  session);
    		//the above line is commented, because proxy is aleady got in InvestigationViewLoad
    		InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();

    		if (investigationProxyVO != null)
    		{

    			Collection<Object>  notificationSummaryVOCollection  = (ArrayList<Object> )investigationProxyVO.getTheNotificationSummaryVOCollection();
    			Collection<Object>  observationSummaryVOCollection  = (ArrayList<Object> )investigationProxyVO.getTheObservationSummaryVOCollection();
    			String sortMethod = "getAddTime";

    			if (sortMethod != null && notificationSummaryVOCollection  != null &&
    					notificationSummaryVOCollection.size() > 0)
    			{

    				NedssUtils util = new NedssUtils();
    				util.sortObjectByColumn(sortMethod, notificationSummaryVOCollection);
    			}

    			if (sortMethod != null && observationSummaryVOCollection  != null &&
    					observationSummaryVOCollection.size() > 0)
    			{
    				logger.debug("Obs list is : " + observationSummaryVOCollection);

    				NedssUtils util = new NedssUtils();
    				util.sortObjectByColumn(sortMethod, observationSummaryVOCollection);
    			}
    		}

    		investigationFormCd = convertProxyToRequestObj(investigationForm, request);

    		//special case for path from associate to investigation - no Edit or Manage buttons
    		if ( sCurrentTask.equalsIgnoreCase("ViewInvestigation7") || sCurrentTask.equalsIgnoreCase("ViewInvestigation8") ||
    				sCurrentTask.equalsIgnoreCase("ViewInvestigation9")  ||  sCurrentTask.equalsIgnoreCase("ViewInvestigation10") ||
    				sCurrentTask.equalsIgnoreCase("ViewInvestigation11") ||  sCurrentTask.equalsIgnoreCase("ViewInvestigation12") ) 
    		{
    			request.setAttribute("editInv", "false");
    			request.setAttribute("checkManageEvents", "false");
    			request.setAttribute("checkTransfer", "false");
    			addContactTracing = false;
    			manageCT = false;
    		}
    	}catch (Exception e) {
    		logger.error("Exception in Bmird View Load: " + e.toString());
    		e.printStackTrace();
    		throw new ServletException("Error during Bmird View Load : "+e.getMessage());
    	}

	String sContextAction = request.getParameter("ContextAction");
	logger.debug(" before display of createMeasles page");

	return (this.getForwardPage(investigationFormCd, sCurrentTask, sContextAction, viewContactTracing, addContactTracing, manageCT, contactCaseUrl, viewFileUrl));
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
	    investigationId = super.getPublicHealthCaseLocalID(investigationUID,session,request);
	}
	catch( Exception e)
	{
	    throw new ServletException(e.toString());
	}
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

	String conditionCd = "";
	String investigationFormCd = "";
	InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();
        CachedDropDownValues cdv = new CachedDropDownValues();

	HttpSession session = request.getSession(false);
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	super.setSecurityForView(investigationProxyVO, request, nbsSecurityObj);

        //LDF stuff
        String businessObjNm = cdv.getLDFMap(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
        conditionCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();

        super.createXSP(businessObjNm, investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), investigationProxyVO,conditionCd,request);
        super.createXSP(NEDSSConstants.PATIENT_LDF, investigationForm.getOldRevision().getThePersonDT().getPersonUid(), investigationForm.getOldRevision(), null, request);

        NBSContext.store(session, "DSPatientPersonLocalID", investigationForm.getPatient().getThePersonDT().getLocalId());

        //display revision patient tab
        PersonUtil.convertPersonToRequestObj(investigationForm.getPatient(), request,
                                             "AddPatientFromEvent", new ArrayList<Object>());

	String programAreaCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
        NBSContext.store(session, NBSConstantUtil.DSProgramArea, programAreaCd);
        ProgramAreaVO programAreaVO = cdv.getProgramAreaConditionLevel(programAreaCd, conditionCd);
        investigationFormCd = programAreaVO.getInvestigationFormCd();

        String sCurrentTask = NBSContext.getCurrentTask(session);
	this.setABCStateIndicator(request);
        Long mprUid = investigationForm.getPatient().getThePersonDT().getPersonParentUid();
        if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_BMDGEN))
	{

	    BMIRDInvestigationUtil BMIRDiu = new BMIRDInvestigationUtil();
	    BMIRDiu.setGenericRequestForView(investigationProxyVO, request);
            BMIRDiu.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_BMDGAS))
	{
	    BMIRDInvestigationUtil BMIRDiu = new BMIRDInvestigationUtil();
	    BMIRDiu.setGenericRequestForView(investigationProxyVO, request);
	    BMIRDiu.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
            BMIRDiu.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_BMDGBS))
	{
	    BMIRDInvestigationUtil BMIRDiu = new BMIRDInvestigationUtil();
	    BMIRDiu.setBatchEntryToFormForView(investigationForm);
	    BMIRDiu.setGenericRequestForView(investigationProxyVO, request);
	    BMIRDiu.convertBatchEntryToRequestForGBS(investigationForm, request);
	    BMIRDiu.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
            BMIRDiu.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_BMDHI))
	{
	    BMIRDInvestigationUtil BMIRDiu = new BMIRDInvestigationUtil();
	    BMIRDiu.setGenericRequestForView(investigationProxyVO, request);
	    BMIRDiu.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
            BMIRDiu.getVaccinationSummaryRecords(mprUid,request);
        }
	else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_BMDNM))
	{
	    BMIRDInvestigationUtil BMIRDiu = new BMIRDInvestigationUtil();
	    BMIRDiu.setGenericRequestForView(investigationProxyVO, request);
	    BMIRDiu.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
            BMIRDiu.getVaccinationSummaryRecords(mprUid,request);
	}
	else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_BMDSP))
	{
	    BMIRDInvestigationUtil BMIRDiu = new BMIRDInvestigationUtil();
	    BMIRDiu.setBatchEntryToFormForView(investigationForm);
	    BMIRDiu.setGenericRequestForView(investigationProxyVO, request);
	    BMIRDiu.convertBatchEntryToRequestForSP(investigationForm, request);
	    BMIRDiu.setSuplimentalObseravationsToFormOnLoad(investigationForm, request);
            BMIRDiu.getVaccinationSummaryRecords(mprUid,request);
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
					 String sContextAction, boolean viewPerm, boolean addPerm,boolean manageCTPerm, String contactCaseUrl,String viewFileUrl)
    {

	String path = "/error";
	String addpermString = new Boolean(addPerm).toString();

	if (NBSConstantUtil.INV_FORM_BMDGEN.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage") && viewPerm)
            path = "/diseaseform/bmird/generic_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/bmird/generic_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/bmird/generic_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view";
          else
            path = "/diseaseform/bmird/generic_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_BMDGAS.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/bmird/strepa_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/bmird/strepa_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/bmird/strepa_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view";
          else
            path = "/diseaseform/bmird/strepa_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
        }
	else if (NBSConstantUtil.INV_FORM_BMDGBS.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/bmird/generic_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/bmird/generic_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/bmird/generic_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view";
          else
            path = "/diseaseform/bmird/generic_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
      // as per enhancement requst 10431 always do not display GroupB strep, display generic Bmird

        /**if (sContextAction.equalsIgnoreCase("PrintPage"))
            path = "/diseaseform/bmird/strepb_print";
          else
            path = "/diseaseform/bmird/strepb_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view";**/
        }
	else if (NBSConstantUtil.INV_FORM_BMDHI.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/bmird/hi_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/bmird/hi_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/bmird/hi_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view";
          else
            path = "/diseaseform/bmird/hi_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
        }
	else if (NBSConstantUtil.INV_FORM_BMDNM.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/bmird/nm_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/bmird/nm_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/bmird/nm_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view";
          else
            path = "/diseaseform/bmird/nm_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
        }
	else if (NBSConstantUtil.INV_FORM_BMDSP.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewPerm)
            path = "/diseaseform/bmird/sp_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewPerm)
        	  path = "/diseaseform/bmird/sp_print_no_contact";
          else if(!viewPerm)
        	  path = "/diseaseform/bmird/sp_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view";
          else
            path = "/diseaseform/bmird/sp_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm+
              "&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;
        }

	logger.debug(" path: " + path);

	return new ActionForward(path);
    }


    /**
      * To set ABCStateIndicator from request.
      * @param request : HttpServletRequest the request
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