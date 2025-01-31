package gov.cdc.nedss.webapp.nbs.action.investigation.common;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;

public class InvestigationCreateSubmit
    extends CommonAction
{

  //For logging
  static final LogUtils logger = new LogUtils(InvestigationCreateSubmit.class.
                                              getName());
  static String strLock = "lock";

  public InvestigationCreateSubmit()
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws
      IOException, ServletException
  {
    HttpSession session = request.getSession(false);
    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
    String sContextAction = request.getParameter("ContextAction");
    if (sContextAction == null)
    {
      session.setAttribute("error", "null ContextAction in investigationCreateSubmit");
      throw new ServletException("null ContextAction in investigationCreateSubmit");
    }

    if (sContextAction.equals(NBSConstantUtil.SUBMIT) ||
        sContextAction.equals(NBSConstantUtil.SubmitNoViewAccess))
    {
        String investigationFormCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCode);
        return mapping.findForward(this.getActionForward(investigationFormCd));
    }

    boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
    boolean checkInvestigationAutoCreatePermission = nbsSecurityObj.
        getPermission(NBSBOLookup.INVESTIGATION,
                      NBSOperationLookup.AUTOCREATE,
                      ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
                      ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
                      ProgramAreaJurisdictionUtil.SHAREDISTRUE);
    if (checkInvestigationAutoCreatePermission && !viewInvestigation)
    {
      sContextAction = NBSConstantUtil.SubmitNoViewAccess;
    }
    return mapping.findForward(sContextAction);

  }


  /**
   * this method returns string of condition specific action class
   * @param investigationFormCd : String
   * @return Strings
   */
   private String getActionForward(String investigationFormCd)
   {
       String path = "/error";

       if (NBSConstantUtil.INV_FORM_GEN.equals(investigationFormCd))
       {
           path = "GenericCreateSubmit";
       }
       else if (NBSConstantUtil.INV_FORM_MEA.equals(investigationFormCd))
       {
           path = "MeaslesCreateSubmit";
       }
       else if (NBSConstantUtil.INV_FORM_CRS.equals(investigationFormCd))
       {
         path = "CrsCreateSubmit";
       }
       else if (NBSConstantUtil.INV_FORM_RUB.equals(investigationFormCd))
       {
         path = "RubellaCreateSubmit";
       }
       else if (NBSConstantUtil.INV_FORM_PER.equals(investigationFormCd))
       {
         path = "PertussisCreateSubmit";
       }
       else if (investigationFormCd.startsWith("INV_FORM_HEP"))
       {
         path = "HepatitisCreateSubmit";
       }
       else if (investigationFormCd.startsWith("INV_FORM_BMD"))
       {
         path = "BmirdCreateSubmit";
       }
       else if (NBSConstantUtil.INV_FORM_RVCT.equals(investigationFormCd))
   	   {
    	   path = "TuberculosisCreateSubmit";
   	   }
       else if (NBSConstantUtil.INV_FORM_VAR.equals(investigationFormCd))
   	   {
    	   path = "VaricellaCreateSubmit";
   	   }

             return path;
   }


  }