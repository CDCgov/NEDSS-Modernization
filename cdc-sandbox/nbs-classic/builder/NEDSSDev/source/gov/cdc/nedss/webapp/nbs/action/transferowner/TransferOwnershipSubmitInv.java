package gov.cdc.nedss.webapp.nbs.action.transferowner;
/**
 * Title:        TransferOwnershipSubmitInv
 * Description:  This class transfers ownership of Investigation.
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author CSC
 * @version 1.0
 */
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.form.investigation.InvestigationForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sas.rmi.RemoteException;


public class TransferOwnershipSubmitInv
    extends Action
{

    protected static NedssUtils nedssUtils = null;

    //For logging
    static final LogUtils logger = new LogUtils(TransferOwnershipSubmitInv.class.getName());

    /**
     * Default Constructor
     */
    public TransferOwnershipSubmitInv()
    {
    }

    /**
      * This method does following:
     * 1. It tries to get program area and jurisdiction form from and if values
     * are null, tries to get it from context.
     * It call setTransferOwnership method to change the ownership.
     * @param ActionMapping mapping
     * @param ActionForm aForm
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

        NBSSecurityObj securityObj = null;
        logger.debug("Handling TransferOwnershipSubmit Inv");

       
        HttpSession session = request.getSession();

        if (session == null)
        {
            logger.debug("error no session");

            return mapping.findForward("login");
        }

        Object obj = session.getAttribute("NBSSecurityObject");

        if (obj != null)
            securityObj = (NBSSecurityObj)obj;

        // are we edit or create?
        String contextAction = request.getParameter("ContextAction");

        if (contextAction == null)
            contextAction = (String)request.getAttribute("ContextAction");

        //  CREATE

        String sCurrentTask = NBSContext.getCurrentTask(session);
        Long obsUIDtest = null;
        try{
          obsUIDtest = new Long((String)NBSContext.retrieve(session,"DSInvestigationUID"));
          }
        catch(NullPointerException e){
        }

        if(contextAction.equalsIgnoreCase("Submit") && obsUIDtest == null){
          request.setAttribute("OperationType", "investigation");
          return mapping.findForward(contextAction);
        }
        if (contextAction.equalsIgnoreCase("Submit"))
        {

            // we need to determine what kind of submit this is: add or edit
            // determine this from the current task

        InvestigationForm investigationForm = (InvestigationForm)form;


       String juris = investigationForm.getProxy().getPublicHealthCaseVO_s().getThePublicHealthCaseDT().getJurisdictionCd();

       String program = investigationForm.getProxy().getPublicHealthCaseVO_s().getThePublicHealthCaseDT().getProgAreaCd();

       Long obsUID = new Long((String)NBSContext.retrieve(session,"DSInvestigationUID"));

       try{
       this.setTransferOwnership(obsUID,juris,request);

       }

       catch (gov.cdc.nedss.exception.NEDSSAppConcurrentDataException e)
             {
                  logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ", e);
                  return mapping.findForward("dataerror");
              }

          catch (Exception e)
                    {
                        logger.fatal(
                                "Error in transfer ownership submit " +e.getMessage());
                        e.printStackTrace();
                        return (mapping.findForward("error"));
                    }
        request.setAttribute("OperationType", "investigation");

        NBSContext.store(session,"DSInvestigationJurisdiction", juris);


        return mapping.findForward(contextAction);



        }
        else if (contextAction.equalsIgnoreCase("Cancel"))
        {

            return mapping.findForward(contextAction);
        }

        throw new ServletException();
    }
    /**
     * This method calls backend to transfer the ownership of observation.
     * @param Long observationUID
     * @param String newProgramAreaCode
     * @param String newJurisdictionCode
     * @param HttpSession session
     * @return Long
     * @throws RemoteException,EJBException, NEDSSConcurrentDataException, Exception
     */


    private Long setTransferOwnership(Long investigationUID,
                                       String newJurisdictionCode, HttpServletRequest req)
                         throws  java.rmi.RemoteException,
                                javax.ejb.EJBException, NEDSSAppConcurrentDataException, Exception
    {
    	
    	try {
    	String invFormCd = req.getParameter("invFormCd");
    	
        MainSessionCommand msCommand = null;
        String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
        String sMethod = "transferOwnership";
        if (msCommand == null) {
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(req.getSession());
        }
        ArrayList<?> resultUIDArr = new ArrayList<Object> ();
        
        if(invFormCd != null && (invFormCd.equals(NBSConstantUtil.INV_FORM_RVCT)|| (invFormCd.equals(NBSConstantUtil.INV_FORM_VAR)) || (invFormCd.equals(NBSConstantUtil.INV_FORM_MALR)) ) ) {    	  
            Object[] oParams = { investigationUID,newJurisdictionCode};
            resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);
            
        } else {
        	sBeanJndiName = JNDINames.PAM_PROXY_EJB;
        	Object obj = new Object();
    		try {
   			
    			Map<?,?> questionMap = (Map<?,?>) QuestionsCache.getQuestionMap().get(NBSConstantUtil.INV_FORM_RVCT);
    			obj =  questionMap.get(PamConstants.JURISDICTION);	
    		} catch (Exception e) {
    			logger.error("Error in TransferOwnershipSubmitInv while fetching TUB237 from QuestionMap: " + e.toString());
    		}
        	
        	Object[] oParams = { investigationUID, obj, newJurisdictionCode};
            resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);        	
        }

        if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
            Long result = (Long)resultUIDArr.get(0);
            return result;
        }
        else
            return null;
	} catch (NEDSSAppConcurrentDataException e) {
		logger.error("Data Concurrency Exception in TransferOwnership Submit INV call to EJB: " + e.getMessage());
		e.printStackTrace();
		throw new ServletException("Data Concurrency Exception in Transfer Ownership Submit INV call : "+e.getMessage());
	} catch (Exception e) {
		logger.error("Exception in TransferOwnership Submit INV call to EJB: " + e.getMessage());
		e.printStackTrace();
		throw new ServletException("Exception occurred in Transfer Ownership Submit INV call : "+e.getMessage());
	}
    }

}