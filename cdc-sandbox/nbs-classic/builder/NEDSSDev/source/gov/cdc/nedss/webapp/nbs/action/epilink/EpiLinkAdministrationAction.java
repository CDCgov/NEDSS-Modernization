package gov.cdc.nedss.webapp.nbs.action.epilink;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.bean.WorkupProxy;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.bean.WorkupProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.alert.AlertAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.epilink.EpiLinkIdForm;

import java.io.IOException;
import java.util.ArrayList;

import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;

public class EpiLinkAdministrationAction extends DispatchAction{
static final LogUtils logger = new LogUtils(EpiLinkAdministrationAction.class.getName());

	public EpiLinkAdministrationAction() {
	}



	public ActionForward mergeEpilink(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

		try {
			EpiLinkIdForm epiLinkIdForm =(EpiLinkIdForm)form;
			HttpSession session = request.getSession();
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
		    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.EPILINKADMIN);
		    request.setAttribute(AlertAdminConstants.PAGE_TITLE ,"Manage Epi-Link ID: Merge");

		    epiLinkIdForm.getAttributeMap().put("cancel", "/nbs/EpiLinkAdmin.do?method=mergeEpilink");
		    epiLinkIdForm.getAttributeMap().put("submit", "/nbs/EpiLinkAdmin.do?method=submitMergeEpilink");


		} catch (Exception e) {
			logger.error("Error while loading the user mergeEpilink: " + e.toString());
			throw new ServletException("Error while loading the mergeEpilink: "+e.getMessage(),e);
		}


		return (mapping.findForward("addEpiLink"));
	}

	public ActionForward submitMergeEpilink(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

		try {
			EpiLinkIdForm epiLinkIdForm =(EpiLinkIdForm)form;
			HttpSession session = request.getSession();
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
		    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,NBSOperationLookup.EDIT);
		    request.setAttribute(AlertAdminConstants.PAGE_TITLE ,"Manage Epi-Link ID: Merge");

		    String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
	         String sMethod = "updateEpilink";
	         Object[] oParams = new Object[] {epiLinkIdForm.getCurrentEpiLinkId(),epiLinkIdForm.getNewEpiLinkId()};
	         MainSessionHolder holder = new MainSessionHolder();
	         MainSessionCommand msCommand = holder.getMainSessionCommand(session);
	         ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
	                                                  oParams);

	         epiLinkIdForm.setConfirmationMessage(epiLinkIdForm.getCurrentEpiLinkId() + " has been successfully merged into " + epiLinkIdForm.getNewEpiLinkId());

		} catch(NEDSSAppException nae){

			ActionErrors errors = (ActionErrors)request.getAttribute("error_messages");
			if(errors == null) errors = new ActionErrors();
			String errorCode =  nae.getErrorCd().substring(nae.getErrorCd().lastIndexOf(":") + 1, nae.getErrorCd().length());
			logger.debug("submitMergeEpilink - error description :" + errorCode);
		    errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY, new ActionMessage("errors.invalid",errorCode ));
			request.setAttribute("error_messages", errors);
		}catch (Exception e) {
			logger.error("Error while loading the mergeEpilink: " + e.toString());
			throw new ServletException("Error while loading the mergeEpilink: "+e.getMessage(),e);
		}

		return (mapping.findForward("addEpiLink"));
	}

	public ActionForward returnLink(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
				return (mapping.findForward("returnEpiLink"));
	}


}
