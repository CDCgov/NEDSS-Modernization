
package gov.cdc.nedss.webapp.nbs.action.investigation.common;

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.CompareUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.form.investigation.InvestigationForm;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Title:         InvestigationViewSubmit is an action class
 * Description:   This class retrieves data from EJB and puts them into request
 *                object for use in the xml file
 * Copyright:     Copyright (c) 2001
 * Company:       CSC
 * @author        NEDSS TEAM
 * @version       1.0
 */

public class InvestigationViewCompareSubmit
    extends CommonAction {

   //For logging
   static final LogUtils logger = new LogUtils(InvestigationViewCompareSubmit.class.
                                               getName());
   static String strLock = "lock";

   /**
    * This is constructor
    */
   public InvestigationViewCompareSubmit() {
   }

   /**
    * Get values from investigation form and forward to next action.
    * @param mapping the ActionMapping Object
    * @param form the ActionForm that contain values
    * @param HttpServletRequest the request
    * @param HttpServletResponse the response
    * @return  ActionForward Object
    * @throws ServletException and IOException
    */
   public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response) throws
       IOException, ServletException, NEDSSAppException {
	   
        HttpSession session = request.getSession();
        InvestigationForm investigationForm = null;
        String invFormCd = request.getParameter("invFormCd") == null ? "" : (String) request.getParameter("invFormCd");

        NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
        // Context
        // are we edit or create?
        String sContextAction = HTMLEncoder.encodeHtml(request.getParameter("ContextAction"));
        investigationForm = (InvestigationForm) form;
        if (sContextAction.equalsIgnoreCase("PatientSearch")
                || sContextAction.equalsIgnoreCase(NBSConstantUtil.DSFilePath)
                || sContextAction.equalsIgnoreCase("ContactTracing"))
        {
        }
     
        boolean isDSInvestigationUidStored = false;
        if ("ContactCase".equalsIgnoreCase(sContextAction))
        {
            isDSInvestigationUidStored = true;
            NBSContext.store(session, "DSInvestigationUID", request.getParameter("publicHealthCaseUID"));
        }
        String sCurrentTask = NBSContext.getCurrentTask(session);
        if (sContextAction == null || sCurrentTask == null)
        {
            session.setAttribute("error", "contextAction is " + sContextAction + " sCurrentTask " + sCurrentTask);
            throw new ServletException("ContextAction is null and CurrentTask is null");
        }
        String isDeleteCalled = request.getParameter("delete");

        // changes made for SRT Filtering : Add Treatment and Add Lab from manage
        if (isDeleteCalled != null && isDeleteCalled.equalsIgnoreCase("true"))
        {
            if (sContextAction.equalsIgnoreCase("ReturnToFileEvents"))
            {
                NBSContext.store(session, NBSConstantUtil.DSFileTab, "3");
            }
            else
                NBSContext.store(session, NBSConstantUtil.DSFileTab, "1");
            try
            {
                String deleteString = deleteProxyObject(investigationForm.getOldProxy().getPublicHealthCaseVO()
                        .getThePublicHealthCaseDT().getUid(), investigationForm, invFormCd, session);
                if (deleteString != null && !deleteString.trim().equalsIgnoreCase(""))
                {
                    NBSContext.store(session, NBSConstantUtil.DSRejectedDeleteString, deleteString);
                    ActionForward af = mapping.findForward("DeleteDenied");
                    ActionForward forwardNew = new ActionForward();
                    StringBuffer strURL = new StringBuffer(af.getPath());
                    String DSInvestigationUid = (String) NBSContext.retrieve(request.getSession(),
                            NBSConstantUtil.DSInvestigationUid1);

                    strURL.append("?ContextAction=DeleteDenied&publicHealthCaseUID==").append(DSInvestigationUid);
                    forwardNew.setPath(strURL.toString());
                    forwardNew.setRedirect(true);
                    return forwardNew;
                }
            }
            catch (Exception e)
            {
                logger.error("Exception happened in InvestigationViewSubmit" + e);
                e.printStackTrace();
                throw new ServletException("Exception happened in InvestigationViewSubmit" + e.getMessage(), e);
            }
        }
        else if (sContextAction.equalsIgnoreCase(NBSConstantUtil.ObservationLabID)
                || sContextAction.equalsIgnoreCase(NBSConstantUtil.ObservationMorbID))
        {
            String observationUID = request.getParameter("observationUID");
            NBSContext.store(session, NBSConstantUtil.DSObservationUID, observationUID);
        }
        else if (sContextAction.equalsIgnoreCase(NBSConstantUtil.ViewVaccination))
        {
            String vaccinationUID = request.getParameter("interventionUID");
            NBSContext.store(session, NBSConstantUtil.DSVaccinationUID, vaccinationUID);
        }
        else if (sContextAction.equalsIgnoreCase(NBSConstantUtil.FileSummary)
                || sContextAction.equalsIgnoreCase(NBSConstantUtil.ReturnToFileSummary))
        {
            NBSContext.store(session, NBSConstantUtil.DSFileTab, "1");
        }
        else if (sContextAction.equalsIgnoreCase("ReturnToFileEvents"))
        {
            NBSContext.store(session, NBSConstantUtil.DSFileTab, "3");
        }
        if (investigationForm.getAttributeMap().size() == 0)
            session.removeAttribute("investigationForm");

        
        //Complex functionality: Interview validation error for presumptive interviews with a date after an initial interview
        
        try{
        	
        	
        String survivor = request.getParameter("survivor");
        String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
        //Second investigation
        String sPublicHealthCaseUID1 = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid1);
        boolean validationErrorPresumptiveAfterInitial = false;
        
        if(survivor!=null && survivor.equals("2"))
        	validationErrorPresumptiveAfterInitial=CompareUtil.presumptiveInterviewValidationError(sPublicHealthCaseUID1, sPublicHealthCaseUID, request);
        else
        	validationErrorPresumptiveAfterInitial=CompareUtil.presumptiveInterviewValidationError(sPublicHealthCaseUID, sPublicHealthCaseUID1, request);
        
        
    	PageProxyVO pageProxyVO = PageLoadUtil.getProxyObject(sPublicHealthCaseUID, request.getSession());
    	PageProxyVO pageProxyVO2 = PageLoadUtil.getProxyObject(sPublicHealthCaseUID1, request.getSession());
    	
    	String condition = pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCdDescTxt();
    	
    	//At this point it doesn't matter to differentiate which one is the survivor/superseded for the message
    	String localIdSuperceded=pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
    	String localIdSurvivor= pageProxyVO2.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
    	
    	
        String messageError = "The <b>"+condition+"</b> investigations <b>"+localIdSurvivor+"</b> and <b>"+ localIdSuperceded +" cannot</b> be merged due to the following errors:<ul>";
        boolean presumptiveReinterviewError=false;
        
        
        if(validationErrorPresumptiveAfterInitial){
        	presumptiveReinterviewError=true;
        	NBSContext.store(session, "DSFileTab", "1");

        	messageError+="<li>Merging investigations would result in at least one presumptive interview with an interview date that is greater than the initial interview date.</li>";
        			
        	//request.setAttribute("error_merge",messageError);
        	//return mapping.findForward("ReturnToFileEvents");
        }
        
        // Complex functionality: validate if the interview to be changed to reinterview is associated to a coinfection
        
        PageProxyVO proxyVOSurvivor = null;
        PageProxyVO proxyVOSuperseded = null;
        
        if(survivor!=null && survivor.equals("2")){
        	proxyVOSurvivor = PageLoadUtil.getProxyObject(sPublicHealthCaseUID1, request.getSession());
            proxyVOSuperseded = PageLoadUtil.getProxyObject(sPublicHealthCaseUID, request.getSession());
        }else{
        	proxyVOSurvivor = PageLoadUtil.getProxyObject(sPublicHealthCaseUID, request.getSession());
            proxyVOSuperseded = PageLoadUtil.getProxyObject(sPublicHealthCaseUID1, request.getSession());
        }

		Long interviewUid = CompareUtil.getInterviewUidToChangeToReinterview(proxyVOSurvivor, proxyVOSuperseded);
		if(interviewUid!=null){
			boolean isAssociatedToACoinfection = CompareUtil.validateIfReinterviewIsAssociatedToACoinfection(interviewUid);

			if(isAssociatedToACoinfection){
				presumptiveReinterviewError=true;
				NBSContext.store(session, "DSFileTab", "1");
	        	//String messageError = "This merge cannot be completed. Changing the initial interview to a re-interview would cause there to be no initial interview for related co-infection investigations.";               		
	        	
	        	messageError+= "<li>Merging investigations would result in changing at least one initial interview to a re-interview, resulting in no initial interviews for related co-infection investigations.</li>";               		
	        	
			}
		}
		
		messageError+="</ul>";
		
		if(presumptiveReinterviewError){
			request.setAttribute("error_merge",messageError);
        	return mapping.findForward("ReturnToFileEvents");
		}
		
        }catch(NEDSSAppException e){
			logger.error("Error in InvestigationViewCompareSubmit:execute: "+e.getMessage());
			throw new NEDSSAppException("InvestigationViewCompareSubmit:execute");
        	
        }
        
        NBSContext.store(session, NBSConstantUtil.DSInvestigationFormCd, invFormCd);
        return mapping.findForward(sContextAction);

    }
   
   private String deleteProxyObject(Long publicHealthCaseUID,
		   InvestigationForm investigationForm,
		   String invFormCd,
		   HttpSession session) throws NEDSSAppConcurrentDataException, NEDSSAppConcurrentDataException, Exception
	{

	   	Map<?, ?> returnMap = new HashMap<Object,Object>();
		MainSessionCommand msCommand = null;
		String deleteError = "";
		try {
			String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
			String sMethod = "deleteInvestigationProxy";

			//Call PAM specific delete API by checking FormCd
			if(invFormCd != null && (invFormCd.equalsIgnoreCase(NBSConstantUtil.INV_FORM_RVCT) || invFormCd.equalsIgnoreCase(NBSConstantUtil.INV_FORM_VAR)) ) {
				sBeanJndiName = JNDINames.PAM_PROXY_EJB;
				sMethod = "deletePamProxy";
			}

			Object[] oParams = new Object[] { publicHealthCaseUID };

			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

			returnMap = (Map<?, ?>) arr.get(0);

			Integer labCount = (Integer)returnMap.get(NEDSSConstants.LAB_REPORT);
			logger.debug(" InvestigationViewSubmit.deleteProxyObject:  labCount returned from InvestigationProxyEJB is "+ labCount);
			if(labCount==null || labCount.intValue()<0)
				labCount = new Integer(0);
			Integer morbCount = (Integer)returnMap.get(NEDSSConstants.MORBIDITY_REPORT);
			logger.debug(" InvestigationViewSubmit.deleteProxyObject:  morbCount returned from InvestigationProxyEJB is "+ morbCount);
			if(morbCount==null || morbCount.intValue()<0)
				morbCount = new Integer(0);
			Integer vaccCount = (Integer)returnMap.get(NEDSSConstants.AR_TYPE_CODE);
			logger.debug(" InvestigationViewSubmit.deleteProxyObject:  vaccCount returned from InvestigationProxyEJB is "+ vaccCount);
			if(vaccCount==null || vaccCount.intValue()<0)
				vaccCount = new Integer(0);


			String securityException = "";
			if(returnMap.get(NEDSSConstants.SECURITY_FAIL)!=null)
				securityException = (String)returnMap.get(NEDSSConstants.SECURITY_FAIL);
			if(securityException.equalsIgnoreCase(NEDSSConstants.SECURITY_FAIL) )
				throw new Exception("Security Exception happens as securityException is not empty "+ securityException);

					  	if(labCount.intValue()>0 || morbCount.intValue()>0 || vaccCount.intValue()>0)
			{
				deleteError="You cannot Delete this Investigation as there is:\r\n\r\n " +
				labCount+ " Associated Lab Report(s)\r\n "
				+morbCount+ " Associated Morbidity Report(s)\r\n "
				+vaccCount+ " Associated Vaccination(s)\r\n\r\n "
				+"Disassociate the Event(s) and try again. \r\n Note: You can only disassociate Events you have access to disassociate.";
			}
		} catch (NEDSSAppConcurrentDataException ncde) {
			throw new NEDSSAppConcurrentDataException("Concurrent access occurred in InvestigationViewSubmit : "
					+ ncde.toString());
		}  catch (Exception exp) {
			logger.error("Error in InvestigationViewSubmit:deleteProxyObject: "+exp.getMessage());
			throw new Exception("InvestigationViewSubmit:deleteProxyObject");
		}

		return deleteError;
	}

}
