package gov.cdc.nedss.webapp.nbs.action.transferowner;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.form.investigation.InvestigationForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class TransferOwnerShipLoadInvesAction extends DispatchAction{
	
	static final LogUtils logger = new LogUtils(TransferOwnerShipLoadInvesAction.class.getName());
	private static CachedDropDownValues cdv = new CachedDropDownValues();
	
	public ActionForward transferOwnershipLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		InvestigationForm invForm = (InvestigationForm)form;
		PublicHealthCaseDT publicHealthCaseDT = (PublicHealthCaseDT)invForm.getOldProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT();
		try {
			String DSInvestigationLocalID = publicHealthCaseDT.getLocalId();
			String  DSInvestigationUid = invForm.getOldProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid().toString();
			String jurisCd = publicHealthCaseDT.getJurisdictionCd();
			String jurisDesc = cdv.getJurisdictionDesc(jurisCd);
			invForm.getProxy().getPublicHealthCaseVO_s().getThePublicHealthCaseDT().setJurisdictionCd(jurisDesc);
			invForm.getAttributeMap().put("DSInvestigationLocalID", DSInvestigationLocalID);
			invForm.getAttributeMap().put("oldJurisdiction", jurisDesc);
			invForm.getAttributeMap().put("DSInvestigationUid", DSInvestigationUid);
			request.setAttribute("DSInvestigationLocalID", DSInvestigationLocalID);
			request.setAttribute("oldJurisdiction", jurisDesc);
			
		} catch (Exception e) {
			logger.error("Error while loading transferOwnership for investigation Case: " + e.toString());
			throw new ServletException("Error while loading transferOwnership for investigation Case: "+e.getMessage(),e);
		}		
		return (mapping.findForward("transferOwnership"));
	}	
	public ActionForward transferOwnershipSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		InvestigationForm invForm = (InvestigationForm)form;
		ActionForward forward;
		try {
			forward = storeTransferOwnership(mapping, invForm, request, response);
			
		} catch (Exception e) {
			logger.error("Error while transferOwnership for Investigation Case: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while transferOwnership for Investigation Case: "+e.getMessage(),e);
		}		
		return forward;
	}	
	
	public static ActionForward storeTransferOwnership(ActionMapping mapping, InvestigationForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InvestigationForm invForm = (InvestigationForm)form;
		String currentTask = NBSContext.getCurrentTask(request.getSession());
		String contextAction = "";
		/*  Note: In the BaseViewLoad class, contextAction is stored in request, 
		in order to get the  values here we are adding constants in NEDSSConstants. */  
		if(currentTask.equalsIgnoreCase("ViewInvestigation1"))
		{
			contextAction = NEDSSConstants.ReturnToFileSummary;
		}else if(currentTask.equalsIgnoreCase("ViewInvestigation2"))
		{
			contextAction = NEDSSConstants.FileSummary;
		}else if(currentTask.equalsIgnoreCase("ViewInvestigation3"))
		{
			contextAction = NEDSSConstants.ReturnToFileEvents;
		}
		else if(currentTask.equalsIgnoreCase("ViewInvestigation4"))
		{
			contextAction = NEDSSConstants.ReturnToFileEvents;
		}
		else if(currentTask.equalsIgnoreCase("ViewInvestigation6"))
		{
			contextAction = NEDSSConstants.FileSummary;
		}
		String url = "/nbs/" + currentTask + ".do?ContextAction=" + contextAction;		
		String oldJurisdDesc = (String) invForm.getAttributeMap().get("oldJurisdiction");
		String newJurisd = request.getParameter("Jurisdiction") == null ? "" : (String)request.getParameter("Jurisdiction"); 
		String newJurisdDesc = cdv.getJurisdictionDesc(newJurisd);
		String comment = request.getParameter("comment") == null ? "" : (String)request.getParameter("comment"); 
		String exportFacility = request.getParameter("exportFacility") == null ? "" : (String)request.getParameter("exportFacility"); 
		String invUid = invForm.getAttributeMap().get("DSInvestigationUid") == null ? "" : (String) invForm.getAttributeMap().get("DSInvestigationUid");
		Long DSInvestigationUid = Long.valueOf(invUid) ;
		String DSInvestigationLocalID = invForm.getAttributeMap().get("DSInvestigationLocalID") == null ? "" : (String) invForm.getAttributeMap().get("DSInvestigationLocalID"); 
		StringBuffer sb = new StringBuffer();
		
		try {
			if(exportFacility!=null && exportFacility.trim().length()>0){
				setExportOwnership(form.getOldProxy().getPublicHealthCaseVO(), exportFacility, comment,
						newJurisd, request);
			}else{
				setTransferOwnership(DSInvestigationUid, newJurisd, request);
			}
				
			
		} catch (gov.cdc.nedss.exception.NEDSSAppConcurrentDataException e) {
			logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",e);
			return mapping.findForward("dataerror");
		}

		catch (Exception e) {
			logger.fatal("Error in Transfer Ownership Load Inv: ", e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error occurred in Transfer Ownership Load Inv:  "+e.getMessage(),e);
		}		
		
		if(exportFacility != null && !exportFacility.equals(""))
			sb.append(DSInvestigationLocalID).append(" has been successfully submitted for transfer from ").append(oldJurisdDesc).append(" to ").append(newJurisdDesc).append(".");
		else
			sb.append(DSInvestigationLocalID).append(" has been successfully transferred from ").append(oldJurisdDesc).append(" Jurisdiction to ").append(newJurisdDesc).append(" Jurisdiction.");
		
		request.getSession().setAttribute("DSFileTab","3");
		request.getSession().setAttribute("pamTOwnershipConfMsg", sb.toString());
		
		response.sendRedirect(url);
		return null;
	}
	private static Long setTransferOwnership(Long DSInvestigationUid, 
			String newJurisdictionCode, HttpServletRequest req)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			NEDSSAppConcurrentDataException, Exception {
		try {
		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
		String sMethod = "transferOwnership";
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		
		if (msCommand == null) {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(req.getSession());
		}
    	Object[] oParams = { DSInvestigationUid, newJurisdictionCode, new Boolean(false)};
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);        	
		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			Long result = (Long) resultUIDArr.get(0);
			return result;
		} else
			return null;
		} catch (NEDSSAppConcurrentDataException e) {
			logger.error("Data Concurrency Exception in Transfer Ownership call to EJB: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Exception in Transfer Ownership call to EJB: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private static Long setExportOwnership(PublicHealthCaseVO publicHealthCaseVO, String exportFacility, String comment,
			String newJurisdictionCode, HttpServletRequest request)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			NEDSSAppConcurrentDataException, Exception {
		NotificationProxyVO notProxyVO =InvestigationUtil.setShareNotificationProxyVO(publicHealthCaseVO, exportFacility, comment, newJurisdictionCode, request);
		notProxyVO.getTheNotificationVO().getTheNotificationDT().setCd(NEDSSConstants.CLASS_CD_EXP_NOTF);

		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
		String sMethod = "exportOwnership";
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		
		if (msCommand == null) {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(request.getSession());
		}
    	Object[] oParams = { notProxyVO, newJurisdictionCode};
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);        	
		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			Long result = (Long) resultUIDArr.get(0);
			return result;
		} else
			return null;
	}

	public ActionForward shareCaseLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		InvestigationForm invForm = (InvestigationForm)form;
		PublicHealthCaseDT publicHealthCaseDT = (PublicHealthCaseDT)invForm.getOldProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT();
		try {
			String DSInvestigationLocalID = publicHealthCaseDT.getLocalId();
			String  DSInvestigationUid = invForm.getOldProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid().toString();
			String jurisCd = publicHealthCaseDT.getJurisdictionCd();
			String jurisDesc = cdv.getJurisdictionDesc(jurisCd);
			invForm.getProxy().getPublicHealthCaseVO_s().getThePublicHealthCaseDT().setJurisdictionCd(jurisDesc);
			invForm.getAttributeMap().put("DSInvestigationLocalID", DSInvestigationLocalID);
			invForm.getAttributeMap().put("DSInvestigationUid", DSInvestigationUid);
			request.setAttribute("DSInvestigationLocalID", DSInvestigationLocalID);
			request.setAttribute("oldJurisdiction", jurisDesc);
			
		} catch (Exception e) {
			logger.error("Error while loading transferOwnership for investigation Case: " + e.toString());
			throw new ServletException("Error while loading transferOwnership for investigation Case: "+e.getMessage(),e);
		}		
		return (mapping.findForward("shareCase"));
	}	

	public ActionForward shareCaseSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		InvestigationForm invForm = (InvestigationForm)form;
		String currentTask = NBSContext.getCurrentTask(request.getSession());
		String contextAction = "";
		
		String url = "/nbs/" + currentTask + ".do?ContextAction=CaseReporting";		
		
		String oldJurisdDesc = (String) invForm.getOldProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd();
		String recipient = request.getParameter("exportFacility") == null ? "" : (String)request.getParameter("exportFacility"); 
		String comment = request.getParameter("comment") == null ? "" : (String)request.getParameter("comment"); 
		String invUid = invForm.getAttributeMap().get("DSInvestigationUid") == null ? "" : (String) invForm.getAttributeMap().get("DSInvestigationUid");
		String DSInvestigationLocalID = invForm.getAttributeMap().get("DSInvestigationLocalID") == null ? "" : (String) invForm.getAttributeMap().get("DSInvestigationLocalID"); 
		StringBuffer sb = new StringBuffer();
		Long phcUid =null;
		
		try {
				 phcUid = setShareCase(invForm.getOldProxy().getPublicHealthCaseVO(), recipient, comment,  oldJurisdDesc, request);
				
		} catch (gov.cdc.nedss.exception.NEDSSAppConcurrentDataException e) {
			logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",e);
			return mapping.findForward("dataerror");
		}
		catch (Exception e) {
			logger.fatal("Error in Share Case Submit: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error occurred in Share Case Submit "+e.getMessage(),e);
		}
		
		String recFacDesc = CachedDropDowns.getReceivingFacilityDescTxt(recipient);
		if(phcUid!= null && phcUid.compareTo(new Long(1))==0){
			sb.append(DSInvestigationLocalID).append(" has already been shared with ").append(recFacDesc);
		}else{			
			sb.append(DSInvestigationLocalID).append(" has been successfully shared with ").append(recFacDesc);
		}
		
		request.getSession().setAttribute(NBSConstantUtil.ConfirmationMsg, sb.toString());
		//String returnURL = url.substring(url.indexOf("\"")+1, url.indexOf(">")-1);
		response.sendRedirect(url);
		return null;
	}
	private static Long setShareCase(PublicHealthCaseVO publicHealthCaseVO, String exportFacility, String comment,
			String newJurisdictionCode, HttpServletRequest request)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			
			NEDSSAppConcurrentDataException, Exception {
		
		try {
		NotificationProxyVO notProxyVO =InvestigationUtil.setShareNotificationProxyVO(publicHealthCaseVO, exportFacility, comment, newJurisdictionCode, request);
		notProxyVO.getTheNotificationVO().getTheNotificationDT().setCd(NEDSSConstants.CLASS_CD_SHARE_NOTF);

		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.CASE_NOTIFICATION_EJB;
		String sMethod = "shareCase";
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		
		if (msCommand == null) {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(request.getSession());
		}
    	Object[] oParams = { notProxyVO};
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);        	
		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			Long result = (Long) resultUIDArr.get(0);
			return result;
		} else
			return null;
		} catch (NEDSSAppConcurrentDataException e) {
			logger.error("Data Concurrency Exception in setShareCase call to EJB: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Exception in Set Share Case call to EJB: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	
	
}
