package gov.cdc.nedss.webapp.nbs.action.transferowner;


import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.webapp.nbs.form.observation.*;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;


/**

 * Title:        TransferOwnershipLoadLab
 * Description: This class places appropriate attributes in request object when
 * user persses submit on transfer ownership for Lab page.
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @version 1.0

 */
import java.io.*;
import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.EJBException;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class TransferOwnershipLoadLab
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils(TransferOwnershipLoadLab.class.getName());
  /**
   * Default constructor
   */
    public TransferOwnershipLoadLab()
    {
    }
    /**
     * This method binds appropriate varibales to request object.
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
        logger.info("inside the TransferOwnershipLoadLab");


        HttpSession session = request.getSession(false);

        if (session == null)
        {

        	throw new ServletException("Session is null");
        }

        NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
                                        "NBSSecurityObject");


        //setting operation type for the XSP
        request.setAttribute("OperationType", NEDSSConstants.LAB_CTRLCD_DISPLAY);

        String contextAction = request.getParameter("ContextAction");

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

        ObservationGeneralForm observationGeneralForm = (ObservationGeneralForm)form;
        //context
        TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS151",
                                               contextAction);
        NBSContext.lookInsideTreeMap(tm);

        String sCurrTask = NBSContext.getCurrentTask(session);
        String jurisdiction = null ;String progArea = null;  String sharedInd = null;
        if(!sCurrTask.equalsIgnoreCase("TransferOwnershipObservationLab9"))
        {
        
             progArea = (String) NBSContext.retrieve(session,
            		 NBSConstantUtil.DSProgramArea);

             jurisdiction = (String) NBSContext.retrieve(session,
            		 NBSConstantUtil.DSJurisdiction);
             sharedInd = (String) NBSContext.retrieve(session,
                                                   "DSObservationSharedInd");
        }
        //  Transfer action
        if (contextAction.equalsIgnoreCase("TransferOwnership"))
        {

            boolean permission = false;
            if(sCurrTask.equals("TransferOwnershipObservationLab9"))
               permission = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.ASSIGNSECURITY);
            else
               permission = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.TRANSFERPERMISSIONS,progArea,jurisdiction,sharedInd);
            if(permission)
            {

            	Long observationUID = (Long)NBSContext.retrieve(session,"DSObservationUID");
            	try {
					Integer associtaedLabsCount = setTransferOwnership(observationUID,session);
					String associatedInvestigationMassage = "";
					if(associtaedLabsCount!=null && associtaedLabsCount.intValue()>0){
						associatedInvestigationMassage = "<b>NOTE: This Lab Report is associated with " +associtaedLabsCount+
					 " Investigation(s).  Transferring this Lab Report will not result in the Investigation(s) being transferred.</b>";
					}
					
					 request.setAttribute("associatedObs", associatedInvestigationMassage);
            	} catch (RemoteException e) {
                	logger.error("Remote Exception in Transfer Ownership Load lab: " + e.getMessage());
                	e.printStackTrace();
				} catch (EJBException e) {
					logger.error("EJB Exception in Transfer Ownership Load lab: " + e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("Exception in Transfer Ownership Load lab: " + e.getMessage());
					e.printStackTrace();
				}
              request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");

              request.setAttribute("ContextAction", tm.get("Submit"));

              request.setAttribute("cancelButtonHref",
                                   "/nbs/" + sCurrTask +
                                   ".do?ContextAction=" + tm.get("Cancel"));
              //    organizationForm.reset();

              return mapping.findForward("XSP");
            }
            else  throw new ServletException(); //  No permission to go to tranferOwnership page

        }
        throw new ServletException();
    }

    /**
     * 
     * @param investigationUID
     * @param newJurisdictionCode
     * @param session
     * @return
     * @throws java.rmi.RemoteException
     * @throws javax.ejb.EJBException
     * @throws NEDSSAppConcurrentDataException
     * @throws Exception
     */
    private Integer setTransferOwnership(Long observationUID, HttpSession session)
    throws  java.rmi.RemoteException,
    javax.ejb.EJBException, Exception
    {
    	try {
    	/**
    	 * Call the mainsessioncommand
    	 */
    	MainSessionCommand msCommand = null;
    	String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
    	String sMethod = "associatedInvestigationCheck";
    	Object[] oParams = { observationUID};
    	
    	if (msCommand == null)
    	{
    		
    		MainSessionHolder holder = new MainSessionHolder();
    		msCommand = holder.getMainSessionCommand(session);
    	}
    	
    	ArrayList<?> resultUIDArr = new ArrayList<Object> ();
    	resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
    			oParams);
    	
    	if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
    	{
    		
    		Integer result = (Integer)resultUIDArr.get(0);
    		
    		return result;
    	}
    	else
    		
    		return null;
		} catch (NEDSSAppConcurrentDataException e) {
			logger.error("Data Concurrency Exception in TransferOwnership Load Lab call to EJB: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Data Concurrency Exception in TransferOwnership call : "+e.getMessage());
		} catch (Exception e) {
			logger.error("Exception in TransferOwnership Load Lab call to EJB: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Exception in TransferOwnership Load Lab call : "+e.getMessage());
		}
    }

}