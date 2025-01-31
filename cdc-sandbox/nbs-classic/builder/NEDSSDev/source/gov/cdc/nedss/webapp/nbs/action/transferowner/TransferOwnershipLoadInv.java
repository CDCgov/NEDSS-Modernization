package gov.cdc.nedss.webapp.nbs.action.transferowner;


import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.form.investigation.InvestigationForm;
//import gov.cdc.nedss.systemservice.nbsErrorLog.*;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
/**

 * Title:        TransferOwnershipLoadInv
 * Description:  This class places appropriate attributes in request object when
 * user persses submit on transfer ownership for investigation page.
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @version 1.0

 */
import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class TransferOwnershipLoadInv
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils(TransferOwnershipLoadInv.class.getName());
  /**
   * Default constructor
   */
    public TransferOwnershipLoadInv()
    {
    }
    /**
     * This method binds appropriate variables to request object.
     * @param ActionMapping mapping
     * @param ActionForm Form
     * @param HttpServletRequest request
     * @param HttpServletResponse response
     * @return ActionForward
     * @throws IOException, ServletException
     */


    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {
        logger.debug(
                "remove the result session holding the organizationSearch object");
        request.getSession().removeAttribute("result");
        logger.info("inside the TransferOwnershipLoadInv");


        HttpSession session = request.getSession(false);

        if (session == null)
        {

        	throw new ServletException("Session is null");
        }
        try {
        	NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
        			"NBSSecurityObject");

        	String contextAction = request.getParameter("ContextAction");

        	//setting operation type for the XSP

        	request.setAttribute("OperationType", "investigation");

        	if (contextAction == null)
        		contextAction = (String)request.getAttribute("ContextAction");
        	ErrorMessageHelper.setErrMsgToRequest(request);

        	String strUID = request.getParameter("organizationUID");

        	if (strUID == null)
        	{
        		strUID = (String)request.getAttribute("organizationUID");
        	}
        	else
        	{
        		strUID = request.getParameter("organizationUID");
        	}

        	InvestigationForm investigationForm = (InvestigationForm)form;

        	//  Transfer action
        	if (contextAction.equalsIgnoreCase("TransferOwnership"))
        	{
        		//context
        		TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS151",
        				contextAction);
        		NBSContext.lookInsideTreeMap(tm);

        		String sCurrTask = NBSContext.getCurrentTask(session);

        		request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");

        		request.setAttribute("ContextAction", tm.get("Submit"));

        		request.setAttribute("cancelButtonHref",
        				"/nbs/" + sCurrTask +
        				".do?ContextAction=" + tm.get("Cancel"));

        		String invFormCd = request.getParameter("invFormCd");
        		//Check and see if the formCd = TB or Varicella or Malaria etc.... and populate message accordingly...
        		if(invFormCd != null && (invFormCd.equals(NBSConstantUtil.INV_FORM_RVCT)|| (invFormCd.equals(NBSConstantUtil.INV_FORM_VAR)) || (invFormCd.equals(NBSConstantUtil.INV_FORM_MALR)) ) ) {
        			request.setAttribute("invFormCd", invFormCd);
        		}

        		return mapping.findForward("XSP");
        	}
        }catch (Exception e) {
        	logger.error("Exception in Transfer Ownership Load Inv: " + e.getMessage());
        	e.printStackTrace();
        	throw new ServletException("Error occurred in Transfer Ownership Load Inv: "+e.getMessage());
        } 
        throw new ServletException();
    }
}