package gov.cdc.nedss.webapp.nbs.action.transferowner;
/**
 * Title:        TransferOwnershipSubmit
 * Description:  this class transfers ownership of unassigned or assigned
 * observations.
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author CSC
 * @version 1.0
 */

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.form.observation.*;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class TransferOwnershipSubmit
    extends Action
{

    protected static NedssUtils nedssUtils = null;
    //For logging
    static final LogUtils logger = new LogUtils(TransferOwnershipSubmit.class.getName());

    /**
     * Default Constructor
     */

    public TransferOwnershipSubmit()
    {
    }
    /**
     * This method does following:
     * 1. It tries to get programarea and jurisdiction from form and if values
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

	
	HttpSession session = request.getSession();

	if (session == null)
	{


	    return mapping.findForward("login");
	}

	Object obj = session.getAttribute("NBSSecurityObject");

	if (obj != null) {
	}

	// are we edit or create?
	String contextAction = request.getParameter("ContextAction");

	if (contextAction == null)
	    contextAction = (String)request.getAttribute("ContextAction");

	//  CREATE

	NBSContext.getCurrentTask(session);

	if (contextAction.equalsIgnoreCase("Submit"))
	{

	    // we need to determine what kind of submit this is: add or edit
	    // determine this from the current task

	ObservationGeneralForm observationGeneralForm = (ObservationGeneralForm)form;
        String juris = "";
       if(observationGeneralForm != null && observationGeneralForm.getOrderedTest()!= null && observationGeneralForm.getOrderedTest().getTheObservationDT()!=null)
       {
         juris = observationGeneralForm.getOrderedTest().
             getTheObservationDT().getJurisdictionCd();
         if (juris == null || juris.equals("")) {
           try {
             juris = (String) NBSContext.retrieve(request.getSession(),
            		 NBSConstantUtil.DSJurisdiction);

           }
           catch (Exception e) {
           }
         }
       }
      String program = "";
      if(observationGeneralForm != null && observationGeneralForm.getOrderedTest()!=null && observationGeneralForm.getOrderedTest().getTheObservationDT() !=null)
        program = observationGeneralForm.getOrderedTest().getTheObservationDT().getProgAreaCd();
	if (program==null || program.equals(""))
	{
	    try{
		program=(String)NBSContext.retrieve(request.getSession(),NBSConstantUtil.DSProgramArea);
	    }catch(Exception e){
	    }
	}

       if(program.equals(""))
          program = request.getParameter("orderedTest.theObservationDT.progAreaCd");
       if(juris.equals(""))
          juris = request.getParameter("orderedTest.theObservationDT.jurisdictionCd");
          System.out.println("\n\n class name " + NBSContext.retrieve(session,"DSObservationUID").getClass().toString());
          Long obsUID = null;
          
          obsUID = (Long)NBSContext.retrieve(session,"DSObservationUID");


       try{

       this.setTransferOwnership(obsUID,program,juris,session);

       }

       catch (gov.cdc.nedss.exception.NEDSSConcurrentDataException e)
	     {
		  logger.fatal("ERROR - NEDSSConcurrentDataException, The data has been modified by another user, please recheck! ", e);
		  return mapping.findForward("dataerror");
	      }

       catch (Exception e)
		    {
    	   logger.error("Exception in TransferOwnership Submit call to EJB: " + e.getMessage());
    	   e.printStackTrace();
			return (mapping.findForward("error"));
		    }

	request.setAttribute("OperationType", "observation");
	NBSContext.store(session,NBSConstantUtil.DSJurisdiction, juris);
	NBSContext.store(session,NBSConstantUtil.DSProgramArea, program);

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


private Long setTransferOwnership(Long observationUID, String newProgramAreaCode,
				       String newJurisdictionCode, HttpSession session)
			 throws  java.rmi.RemoteException,
				javax.ejb.EJBException, NEDSSConcurrentDataException, Exception
    {

	try {
		
	/**
       * Call the mainsessioncommand
       */
	MainSessionCommand msCommand = null;
	String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
	String sMethod = "transferOwnership";
	Object[] oParams = { observationUID,newProgramAreaCode,newJurisdictionCode,NEDSSConstants.NON_CASCADING };

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
	    Long result = (Long)resultUIDArr.get(0);

	    return result;
	}
	else

	    return null;
	} catch (NEDSSAppConcurrentDataException e) {
		logger.error("Data Concurrency Exception in TransferOwnership Submit call to EJB: " + e.getMessage());
		e.printStackTrace();
		throw new ServletException("Data Concurrency Exception in Transfer Ownership Submit call : "+e.getMessage());
	} catch (Exception e) {
		logger.error("Exception in TransferOwnership Submit call to EJB: " + e.getMessage());
		e.printStackTrace();
		throw new ServletException("Exception occurred in Transfer Ownership Submit call : "+e.getMessage());
	}
    }

}