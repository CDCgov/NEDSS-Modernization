package gov.cdc.nedss.webapp.nbs.action.investigation.common;
/**
 *
 * <p>Title: InvestigationCreateLoad</p>
 * <p>Description: This is a load action class for create investigation for redirecting the request to conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;


public class InvestigationCreateLoad
    extends Action
{

    static final LogUtils logger = new LogUtils(InvestigationCreateLoad.class.getName());
    /**
     * empty constructor
     */
    public InvestigationCreateLoad()
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
	HttpSession session = request.getSession(false);

	//context
	String sContextAction = request.getParameter("ContextAction");
	if (sContextAction == null)
	{
	    logger.error("ContextAction is null");
	    session.setAttribute("error", "No Context Action in InvestigationCreateLoad");

	    throw new ServletException("ContextAction is null");
	}

	return this.createInvestigation(mapping, form, request, session);
    }

    /**
     *
     * @param mapping : ActionMapping
     * @param form : ActionForm
     * @param request : HttpServletRequest
     * @param session : HttpSession
     * @return ActionForward
     * @throws ServletException
     */

    private ActionForward createInvestigation(ActionMapping mapping, ActionForm form,
					      HttpServletRequest request, HttpSession session)
				       throws ServletException
    {
      String sContextAction = request.getParameter("ContextAction");

      InvestigationForm investigationForm = (InvestigationForm) form;
      String conditionCd = null;
      String investigationFormCd = null;
      try {
    	  if (! (sContextAction.equals("DiagnosedCondition")))
    		  investigationForm.reset();

    	  NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
    	  conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
    	   //= (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationFormCd);
    	  
    	  String programArea = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
    	  Long mprUid = (Long) NBSContext.retrieve(session, NBSConstantUtil.DSPersonSummary);
    	  NBSContext.store(session, NBSConstantUtil.DSPersonSummary, mprUid);

    	  ProgramAreaVO programAreaVO = null;
    	  CachedDropDownValues cdv = new CachedDropDownValues();
    	  programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", conditionCd);
    	  investigationFormCd = programAreaVO.getInvestigationFormCd();
    	  NBSContext.store(session,NBSConstantUtil.DSInvestigationFormCd,investigationFormCd);
    	  boolean createInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.
    			  INVESTIGATION,
    			  NBSOperationLookup.ADD,
    			  programAreaVO.getStateProgAreaCode(),
    			  ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
    			  ProgramAreaJurisdictionUtil.SHAREDISTRUE);
    	  boolean checkInvestigationAutoCreatePermission = nbsSecurityObj.
    			  getPermission(
    					  NBSBOLookup.INVESTIGATION,
    					  NBSOperationLookup.AUTOCREATE,
    					  ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
    					  ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
    					  ProgramAreaJurisdictionUtil.SHAREDISTRUE);

    	  if (!createInvestigation && !checkInvestigationAutoCreatePermission)
    	  {
    		  logger.error("You do not have access to Create Investigation");
    		  session.setAttribute("error", "You do not have access to Create Investigation");

    		  return (mapping.findForward("error"));
    	  }


    	  // set tab order before we forward, This trumps any logic we may have before on tab selection xz 11/03/2004 
    	  request.setAttribute("DSFileTab", new Integer(PropertyUtil.getInstance().getDefaultInvTabOrder()).toString());

    	  ArrayList<Object> contactNamedByPatList = new ArrayList<Object>();
    	  ArrayList<Object> patNamedByContactsList = new ArrayList<Object>();

    	  request.setAttribute("contactNamedByPatList",contactNamedByPatList);
    	  request.setAttribute("patNamedByContactsList",patNamedByContactsList);


    	  //set the quick entry stuff
    	  request.setAttribute("investigatorDemographics", "There is no Investigator selected.");
    	  request.setAttribute("reportingOrgDemographics", "There is no Reporting Source selected.");
    	  request.setAttribute("reporterDemographics", "There is no Reporter selected.");
    	  request.setAttribute("physicianDemographics", "There is no Physician selected.");
    	  request.setAttribute("hospitalDemographics", "There is no Hospital selected.");	 	 
      }catch (Exception e) {
    	  logger.error("Exception in Investigation Create Load execute: " + e.getMessage());
    	  e.printStackTrace();
    	  throw new ServletException("Error in Investigation Create Load execute : "+e.getMessage());
      }
      return this.getActionForward(mapping, investigationFormCd, conditionCd, sContextAction);
    }

    /**
     * this method returns ActionForward to the condition specific action class
     * @param mapping : ActionMapping
     * @param investigationFormCd : String
     * @param sContextAction : String
     * @return ActionForward
     */

    private ActionForward getActionForward(ActionMapping mapping, String investigationFormCd,String conditionCd, String sContextAction)
    {
	String path = "/error";

	if (NBSConstantUtil.INV_FORM_GEN.equals(investigationFormCd)) {
			path = "GenericCreateLoad";
		}
	//For new PAMs, path is always "PamCreateLoad" and based on the formCd, appropriate action is called...
	else if (NBSConstantUtil.INV_FORM_RVCT.equals(investigationFormCd))// || ...etc
	{
	    path = "PamCreateLoad";
	}
	else if (NBSConstantUtil.INV_FORM_MEA.equals(investigationFormCd))
	{
	    path = "MeaslesCreateLoad";
	}
	else if (NBSConstantUtil.INV_FORM_CRS.equals(investigationFormCd))
	{
          path = "CrsCreateLoad";
	}
	else if (NBSConstantUtil.INV_FORM_RUB.equals(investigationFormCd))
        {
          path = "RubellaCreateLoad";
		//path = "PageDemoCreateLoad";
	}
	else if (NBSConstantUtil.INV_FORM_PER.equals(investigationFormCd))
	{
          path = "PertussisCreateLoad";
        }
	else if (investigationFormCd.startsWith("INV_FORM_HEP"))
	{
          path = "HepatitisCreateLoad";
          if(sContextAction.equals("DiagnosedCondition"))
          path = "HepatitisCreateDiagnosis";
        }
	else if (investigationFormCd.startsWith("INV_FORM_BMD"))
	{
          path = "BmirdCreateLoad";
          if(sContextAction.equals("DiagnosedCondition"))
          path = "BmirdCreateDiagnosis";
    }
	else if (NBSConstantUtil.INV_FORM_VAR.equals(investigationFormCd))
	{
	    path = "PamVarCreateLoad";
	}
	else 
	{
		//path = "PageCreateLoad";
		path = "DMBCreateLoad";
		 // path = "PageDemoCreateLoad";
    }
	
	return mapping.findForward(path);
    }

}
