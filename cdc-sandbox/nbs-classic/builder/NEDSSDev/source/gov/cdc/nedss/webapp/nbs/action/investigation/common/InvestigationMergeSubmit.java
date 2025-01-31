package gov.cdc.nedss.webapp.nbs.action.investigation.common;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.bmird.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.hepatitis.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

public class InvestigationMergeSubmit extends CommonAction  {


  //For logging
  static final LogUtils logger = new LogUtils(InvestigationMergeSubmit.class.getName());
  static String strLock = "lock";

   public InvestigationMergeSubmit()
   {
   }


   public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
   throws IOException, ServletException {

      HttpSession session = request.getSession();
      NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
      // Context
      String sContextAction = request.getParameter("ContextAction");
      String sCurrentTask = NBSContext.getCurrentTask(session);

      if(sContextAction == null)
      {
	session.setAttribute("error no contextAction ", "contextAction is " +  sContextAction +" sCurrentTask " + sCurrentTask);
	throw new ServletException("ContextAction is null");
      }

      if(sContextAction.equals("Submit"))
      {
         logger.debug("Handling edit action in InvestigationEditSubmit");
         InvestigationForm investigationForm = (InvestigationForm)form;
         String investigationFormCd = this.getInvestigationFormCd(investigationForm.getProxy());

         return this.getActionForward(investigationFormCd, mapping);
      }

      return mapping.findForward(sContextAction);

  }

  /**
   * this method returns string of condition specific action class
   * @param investigationFormCd : String
   * @return Strings
   */
    private ActionForward getActionForward(String investigationFormCd, ActionMapping mapping)
    {
        String path = "/error";

        if (NBSConstantUtil.INV_FORM_GEN.equals(investigationFormCd))
        {
            path = "GenericEditSubmit";
        }
        else if (NBSConstantUtil.INV_FORM_MEA.equals(investigationFormCd))
        {
            path = "MeaslesEditSubmit";
        }
        else if (NBSConstantUtil.INV_FORM_CRS.equals(investigationFormCd))
        {
          path = "CrsEditSubmit";
        }
        else if (NBSConstantUtil.INV_FORM_RUB.equals(investigationFormCd))
        {
          path = "RubellaEditSubmit";
        }
        else if (NBSConstantUtil.INV_FORM_PER.equals(investigationFormCd))
        {
          path = "PertussisEditSubmit";
        }
        else if (investigationFormCd.startsWith("INV_FORM_HEP"))
        {
          path = "HepatitisEditSubmit";
        }
        else if (investigationFormCd.startsWith("INV_FORM_BMD"))
        {
          path = "BmirdEditSubmit";
        }
        else if (NBSConstantUtil.INV_FORM_RVCT.equals(investigationFormCd))
    	{
    	    path = "TuberculosisEditSubmit";
    	}
        else if (NBSConstantUtil.INV_FORM_VAR.equals(investigationFormCd))
    	{
    	    path = "VaricellaEditSubmit";
    	}
              return mapping.findForward(path);
    }



}//InvestigationEditSubmit