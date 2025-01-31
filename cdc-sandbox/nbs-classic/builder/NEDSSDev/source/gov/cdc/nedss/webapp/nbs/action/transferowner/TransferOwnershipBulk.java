package gov.cdc.nedss.webapp.nbs.action.transferowner;


import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.webapp.nbs.form.nbsdocument.NbsDocumentForm;
import gov.cdc.nedss.webapp.nbs.form.observation.*;
import gov.cdc.nedss.webapp.nbs.form.observationsecurityassgn.ObservationNeedingSecurityReviewForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.action.nbsDocument.util.DocumentTransferOwnershipUtil;
import gov.cdc.nedss.webapp.nbs.action.nbsDocument.util.NBSDocumentActionUtil;
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
import java.sql.Timestamp;
import java.util.*;

import javax.ejb.EJBException;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.w3c.dom.ranges.DocumentRange;


public class TransferOwnershipBulk
    extends Action
{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
     throws IOException, ServletException
{
		ObservationNeedingSecurityReviewForm observationGeneralForm = (ObservationNeedingSecurityReviewForm)form;
		String progAreaSelected = observationGeneralForm.getProgramAreaSelected();
		String jurisdictionSelected = observationGeneralForm.getJurisdictionSelected();
	    
		
		String checkBoxIdsLabs = observationGeneralForm.getSelectedcheckboxIdsLabs();
		String checkBoxIdsMorbs = observationGeneralForm.getSelectedcheckboxIdsMorbs();
		String checkBoxIdsCases = observationGeneralForm.getSelectedcheckboxIdsCases();
		
		ArrayList<String> UIDLabs = convertFromStringToArray(checkBoxIdsLabs);
		ArrayList<String> UIDMorb = convertFromStringToArray(checkBoxIdsMorbs);
		ArrayList<String> UIDCase = convertFromStringToArray(checkBoxIdsCases);
		
		
		//LOOP FOR PROCESSING LABS
		String observationUIDString;
		Long observationUID;
		
		for(int i=0; i<UIDLabs.size(); i++){
			observationUIDString = UIDLabs.get(i);
			if(!observationUIDString.isEmpty()){
				observationUID=Long.parseLong(observationUIDString);
				transferOwnerShipLabPermissions(mapping, form, request, response, progAreaSelected, jurisdictionSelected, observationUID);
			}
		}

    	
		//LOOP FOR PROCESSING MORBS
		for(int i=0; i<UIDMorb.size(); i++){
			observationUIDString = UIDMorb.get(i);
			if(!observationUIDString.isEmpty()){
				observationUID=Long.parseLong(observationUIDString);
				transferOwnerShipMorbPermissions(mapping, form, request, response, progAreaSelected, jurisdictionSelected, observationUID);  
			}
		}
		
		//LOOP FOR PROCESSING CASES
		for(int i=0; i<UIDCase.size(); i++){
			observationUIDString = UIDCase.get(i);
			if(!observationUIDString.isEmpty()){
				observationUID=Long.parseLong(observationUIDString);
				transferOwnershipCaseReportSubmit(mapping, form, request, response, progAreaSelected, jurisdictionSelected, observationUID);  
			}
		}
		
		int numDocuments = UIDLabs.size() + UIDMorb.size() + UIDCase.size();
		
		/*String confirmationMessage ="";
		
		
		if(numDocuments>1)
			confirmationMessage="The selected <b>"+numDocuments+"</b> documents have been successfully transfered to ";
		else
			confirmationMessage="The selected <b>"+numDocuments+"</b> document has been successfully transfered to ";
		
		if(progAreaSelected!=null && !progAreaSelected.isEmpty()){
			confirmationMessage+="<b>"+progAreaSelected+"</b> program area";
			if(jurisdictionSelected!=null && !jurisdictionSelected.isEmpty())
				confirmationMessage+=" and ";
		}
		if(jurisdictionSelected!=null && !jurisdictionSelected.isEmpty()){
			jurisdictionSelected = CachedDropDowns.getJurisdictionDesc(jurisdictionSelected);
			confirmationMessage+="<b>"+jurisdictionSelected+"</b> jurisdiction";
		}
		request.getSession().setAttribute("msgBlock", confirmationMessage);*/
		
		String confirmationMessage1  ="The selected";
		String msgBlock ="";
		if(numDocuments>1)
			msgBlock="documents have been successfully transfered to ";
		else
			msgBlock="document has been successfully transfered to ";
		
		String confirmationMessage2 ="";
		String confirmationMessage3 ="";
		String confirmationMessage4 ="";
		
		if(progAreaSelected!=null && !progAreaSelected.isEmpty()){
			confirmationMessage2="program area";
			if(jurisdictionSelected!=null && !jurisdictionSelected.isEmpty())
				confirmationMessage3=" and ";
		}
		
		if(jurisdictionSelected!=null && !jurisdictionSelected.isEmpty()){
			jurisdictionSelected = CachedDropDowns.getJurisdictionDesc(jurisdictionSelected);
			confirmationMessage4=" jurisdiction";
		}
		 
		
		request.setAttribute("numDocuments",numDocuments);
		request.setAttribute("progAreaSelected",progAreaSelected);
		request.setAttribute("jurisdictionSelected",jurisdictionSelected);
		request.setAttribute("confirmationMessage1",confirmationMessage1);
		request.setAttribute("confirmationMessage2",confirmationMessage2);
		request.setAttribute("confirmationMessage3",confirmationMessage3);
		request.setAttribute("confirmationMessage4",confirmationMessage4);
		request.setAttribute("msgBlock",msgBlock);
		
    	return mapping.findForward("transferBulkConfirmation");
}
	/**
	 * convertFromStringToArray: returns an ArrayList<String> with the UIDs parsed from the checkBoxIds string
	 * @param checkBoxIds
	 * @return
	 */

	public ArrayList<String> convertFromStringToArray(String checkBoxIds){
		
		ArrayList<String> UIDarray = new ArrayList<String>();
		if(checkBoxIds!=null){
			StringTokenizer st = new StringTokenizer(checkBoxIds,"|");
			
			while (st.hasMoreTokens()) { 
				String token = st.nextToken();
				UIDarray.add((token.toString()));
			}
		}
		return UIDarray;
	}
    
    //For logging
    static final LogUtils logger = new LogUtils(TransferOwnershipBulk.class.getName());
  /**
   * Default constructor
   */
    public TransferOwnershipBulk()
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


    public void transferOwnerShipLabPermissions(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response, String progArea,
                                 String jurisdiction, long observationUID)
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


        String contextAction = request.getParameter("ContextAction");

            ErrorMessageHelper.setErrMsgToRequest(request);

        ObservationNeedingSecurityReviewForm observationGeneralForm = (ObservationNeedingSecurityReviewForm)form;

            boolean permission = false;
               permission = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.ASSIGNSECURITY);
           if(permission)
            {

           	try {//TODO: use the same one than the load class?
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
           
            }
            else  throw new ServletException(); //  No permission to go to tranferOwnership page

            transferOwnerShipLabMorbSubmit(mapping, observationGeneralForm, request, response, jurisdiction, progArea, observationUID);
    
    }

    public void transferOwnerShipMorbPermissions(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response, String progArea,
            String jurisdiction, long observationUID)
     throws IOException, ServletException
    {

        logger.debug(
                "remove the result session holding the organizationSearch object");
        request.getSession().removeAttribute("result");
        logger.info("inside the TransferOwnershipLoadMorb");


        HttpSession session = request.getSession(false);

        if (session == null)
        {

        	throw new ServletException("Session is null");
        }

        NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
                                        "NBSSecurityObject");



        String contextAction = request.getParameter("ContextAction");
       
            ErrorMessageHelper.setErrMsgToRequest(request);

        String strUID = request.getParameter("organizationUID");


        ObservationNeedingSecurityReviewForm observationGeneralForm = (ObservationNeedingSecurityReviewForm)form;

    
           boolean permission = false;
         
              permission = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.ASSIGNSECURITY);
        
           if(permission)
           {
        	 
           	try {//TODO: use the same one than the load class?
					Integer associtaedLabsCount = setTransferOwnership(observationUID,session);
					String associatedInvestigationMassage = "";
					if(associtaedLabsCount!=null && associtaedLabsCount.intValue()>0){
						associatedInvestigationMassage = "<b>NOTE: This Morbidity Report is associated with an Investigation. " +
								"Transferring this Morbidity Report will not result in the Investigation being transferred.</b>";
					}
					
					 
           	} catch (RemoteException e) {
           		logger.error("Remote Exception in TransferOwnership Load Morb call to EJB: " + e.getMessage());
					e.printStackTrace();
				} catch (EJBException e) {
					logger.error("EJB Exception in TransferOwnership Load Morb call to EJB: " + e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("Exception in TransferOwnership Load Morb call to EJB: " + e.getMessage());
					e.printStackTrace();
				}
            

        }
           else throw new ServletException();
           
           transferOwnerShipLabMorbSubmit(mapping, observationGeneralForm, request, response, jurisdiction, progArea, observationUID);
           
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
    


    public void transferOwnerShipLabMorbSubmit(ActionMapping mapping, ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response, String juris, String program, Long obsUID)
			  throws IOException, ServletException
    {

	
	HttpSession session = request.getSession();

	Object obj = session.getAttribute("NBSSecurityObject");

	if (obj != null) {
	}

	// are we edit or create?
	String contextAction = request.getParameter("ContextAction");

	if (contextAction == null)
	    contextAction = (String)request.getAttribute("ContextAction");

	//  CREATE

	NBSContext.getCurrentTask(session);


       try{

       this.setTransferOwnership(obsUID,program,juris,session);

       }

       catch (gov.cdc.nedss.exception.NEDSSConcurrentDataException e)
	     {
		  logger.fatal("ERROR - NEDSSConcurrentDataException, The data has been modified by another user, please recheck! ", e);

	      }

       catch (Exception e)
		    {
    	   logger.error("Exception in TransferOwnership Submit call to EJB: " + e.getMessage());
    	   e.printStackTrace();
		    }

	
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
/*
public ActionForward transferOwnershipLoadCaseReport(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
	NbsDocumentForm nbsDocForm = (NbsDocumentForm) form;
	try {
		//HttpSession session = request.getSession();
		DocumentTransferOwnershipUtil.loadTransferOwnership(nbsDocForm, request);
		// Check to see if investigation is associated with this document
		//NbsDocumentForm docForm = (NbsDocumentForm)form;
		//NBSDocumentDT nbsDocDt = docForm.getNbsDocumentDt();
		//Boolean isInvAsso = util.getInvestigationAssoWithDocumentColl(session, nbsDocDt.getNbsDocumentUid());
		//if(isInvAsso.booleanValue())
		//{
		//	request.setAttribute("IsInvAsso", isInvAsso);
	//	}
	} catch (Exception e) {
		e.printStackTrace();
		throw new ServletException("Error while loading transferOwnership for PAM Case: "+e.getMessage(),e);
	}		
	return (mapping.findForward("transferOwnershipLoad"));
}*/

public void transferOwnershipCaseReportSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response, String program, String juris, Long obsUID) throws IOException, ServletException{
	ObservationNeedingSecurityReviewForm nbsDocForm = (ObservationNeedingSecurityReviewForm) form;
	ActionForward forward;
	try {
		//forward = DocumentTransferOwnershipUtil.storeTransferOwnership(mapping, nbsDocForm, request, response);
		if(program.isEmpty())
			program=null;
		storeTransferOwnershipCaseReport(mapping, request, response, juris, program, obsUID);
		
	} catch (Exception e) {
		e.printStackTrace();
		throw new ServletException("Error while transferOwnership for case report Case: "+e.getMessage(),e);
	}		
	//return forward;
}

public static ActionForward storeTransferOwnershipCaseReport(ActionMapping mapping, HttpServletRequest request, HttpServletResponse res, String juris, String program, Long obsUID) throws Exception {
		//ObservationNeedingSecurityReviewForm docForm = (ObservationNeedingSecurityReviewForm)form;
	NBSDocumentVO nbsDocVo = new NBSDocumentVO();
	String currentTask = NBSContext.getCurrentTask(request.getSession());
	String contextAction = "";
	String clickHereLink = null;
	HttpSession session = request.getSession();
	//TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS233", contextAction);
	//Long personUID = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
	//String documentUID = (String) docForm.getAttributeMap().get("DSDocumentUid");
	//String url = null;
	/*
	
	if(currentTask.equalsIgnoreCase("ViewDocument1"))
	{
		url = "/nbs/LoadObsNeedingAssignment1.do?method=loadQueue&ContextAction=" + tm.get("ReturnToObservationsNeedingAssignment");
		clickHereLink = "/nbs/LoadViewFile1.do?ContextAction=ViewFile";  
	}else if(currentTask.equalsIgnoreCase("ViewDocument2"))
	{
		url = "/nbs/LoadNewLabReview1.do?method=loadQueue&ContextAction="+tm.get("TransferOwnership");
		clickHereLink = "/nbs/NewLabReview1.do?ContextAction=" + tm.get("ViewFile")+"&MPRUid="+personUID+"&observationUID="+documentUID;
	}
	else
	{	
		contextAction = NEDSSConstants.ReturnToFileSummary;
		url = "/nbs/LoadViewFile1.do?ContextAction=" + contextAction;	
	}*/
	//String oldJurisdDesc = (String) docForm.getAttributeMap().get("oldJurisdiction");
	//String newJurisd = request.getParameter("juris") == null ? "" : (String)request.getParameter("juris"); 
	//String newJurisdDesc = cdv.getJurisdictionDesc(newJurisd);
	//String newProgArea = request.getParameter("progArea") == null ? "" : (String)request.getParameter("progArea");
	//if(newProgArea.equals("null"))
	//	newProgArea = (String) docForm.getAttributeMap().get("oldProgramAreaCd");
	//String newProgAreaDesc = cdv.getProgramAreaDesc(newProgArea);
	//String docUid = docForm.getAttributeMap().get("DSDocumentUid") == null ? "" : (String) docForm.getAttributeMap().get("DSDocumentUid");
	//Long DSDocumentUid = Long.valueOf(docUid) ;
	//String DSDocumentLocalID = docForm.getAttributeMap().get("DSDocumentLocalID") == null ? "" : (String) docForm.getAttributeMap().get("DSDocumentLocalID"); 
	StringBuffer sb = new StringBuffer();
	String firstNm = null;
	String lastNm = null;
	
	
	try {
		nbsDocVo = setTransferOwnershipCaseReport(obsUID, juris, program, session);
		firstNm = nbsDocVo.getPatientVO().getThePersonDT().getFirstNm() == null ? "" : nbsDocVo.getPatientVO().getThePersonDT().getFirstNm();
		lastNm = nbsDocVo.getPatientVO().getThePersonDT().getLastNm() == null ? "" : nbsDocVo.getPatientVO().getThePersonDT().getLastNm();
	}
	catch (gov.cdc.nedss.exception.NEDSSAppConcurrentDataException e) {
		logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",e);
		return mapping.findForward("dataerror");
	}
	
	catch (Exception e) {
		logger.fatal("ERROR , The error has occured, please recheck! ", e);
		throw new ServletException("ERROR , The error has occured, please recheck! "+e.getMessage(),e);
	}	
	//if(currentTask.equalsIgnoreCase("ViewDocument2"))
	//{
		//sb.append("Case Report <b>").append(DSDocumentLocalID).append("</b> for <b>").append(firstNm).append("</b>  <b>").append(lastNm).append("</b> has been successfully transferred to <b>")
	//		.append(newJurisdDesc).append("</b> jurisdiction with a program area of <b>").append(newProgAreaDesc).append("</b>.").append("&nbsp;");
			//.append("<a href=\"").append(clickHereLink).append("\">").append("Click Here").append("</a>").append(" to view the patient's File.");
	//	request.getSession().setAttribute("clickHereLink", clickHereLink);
		
	//}else if(currentTask.equalsIgnoreCase("ViewDocument1"))
	//{
	//	sb.append("Case Report ").append(DSDocumentLocalID).append(" for ").append(firstNm).append(" ").append(lastNm).append(" has been successfully transferred to ")
	//	.append(newJurisdDesc).append(" jurisdiction with a program area of ").append(newProgAreaDesc).append(".").append(" ");
	//	request.getSession().setAttribute("clickHereLink", clickHereLink);
	
	//}
	//else
	//{
	//	sb.append("Case Report ").append(DSDocumentLocalID).append(" has been successfully transferred from ").append(oldJurisdDesc).append(" Jurisdiction to ").append(newJurisdDesc).append(" Jurisdiction.");
	//}
	//request.getSession().setAttribute("docTOwnershipConfMsg", sb.toString());
	//request.getSession().setAttribute("DSFileTab","3");
	//res.sendRedirect(url);
	return null;

}


private static NBSDocumentVO setTransferOwnershipCaseReport(Long nbsDocUid, String newJurisdictionCode,String newProgAreaCode, HttpSession ses)
		throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSAppConcurrentDataException, Exception
	{
		NBSDocumentDT nbsDocumentDT = null;
		NBSDocumentDT newNbsDocumentDT = null;
		
		NBSDocumentActionUtil util = new NBSDocumentActionUtil();
		 NBSDocumentVO nbsDocVo = new NBSDocumentVO();
		//nbsDocumentDT 
		try{
			nbsDocVo = util.getNBSDocument(ses,nbsDocUid);
		}
		catch(Exception e){
			 throw new ServletException(e.toString());
		 }
		
		nbsDocumentDT = nbsDocVo.getNbsDocumentDT();
		Long DSPatientPersonUID= nbsDocVo.getPatientVO().getThePersonDT().getPersonParentUid();
		if(DSPatientPersonUID!=null)
			NBSContext.store(ses, NBSConstantUtil.DSPatientPersonUID, DSPatientPersonUID);
		
		logger.info("user has add permissions for setDocumentProxy");
			
		nbsDocumentDT.setItDirty(true);
		if(newJurisdictionCode!=null && !newJurisdictionCode.isEmpty())
			nbsDocumentDT.setJurisdictionCd(newJurisdictionCode);
		if(newProgAreaCode != null)
			nbsDocumentDT.setProgAreaCd(newProgAreaCode);
		nbsDocVo.setFromSecurityQueue(true);
		//nbsDocVo.getNbsDocumentDT().setRecordStatusCd(NEDSSConstants.OBS_PROCESSED);
		Date dat = new Date();
		Timestamp aupdateTime = new Timestamp(dat.getTime());
		nbsDocVo.getNbsDocumentDT().setRecordStatusTime(aupdateTime);
		nbsDocVo.getNbsDocumentDT().setLastChgTime(aupdateTime);
		nbsDocVo = util.setNBSDocumentVOforUpdate(nbsDocVo);
		try{
			util.setDocumentforUpdateforTransferOwnership(ses,nbsDocVo);
		}catch(Exception e){
			throw new ServletException(e.toString());
		}
		return nbsDocVo;
}
}
