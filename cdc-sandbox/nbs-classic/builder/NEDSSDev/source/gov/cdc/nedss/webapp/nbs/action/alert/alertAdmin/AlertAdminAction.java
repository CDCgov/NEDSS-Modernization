/**
 *
 */
package gov.cdc.nedss.webapp.nbs.action.alert.alertAdmin;




import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.alert.AlertAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.alert.alertAdmin.util.AlertUserUtil;
import gov.cdc.nedss.webapp.nbs.form.alert.alertAdmin.AlertUserForm;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * Title:        AlertUserAction
 * Description:  This class will access the information related to User Alerts
 * Company:      CSC
 * @author aperiaswamy
 */
public class AlertAdminAction extends DispatchAction {

	static final LogUtils logger = new LogUtils(AlertAdminAction.class.getName());

	public ActionForward alertAdminUser(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

		try {
			HttpSession session = request.getSession();
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
		    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.DECISION_SUPPORT_ADMIN);
		    if (securityCheck != true) {
		      session.setAttribute("error", "Failed at security checking.");
		      return (mapping.findForward("error"));
		    }
		    request.getSession().removeAttribute("searchParamsVO");
		    request.setAttribute(AlertAdminConstants.PAGE_TITLE ,AlertAdminConstants.MANAGE_ALERTS);
		    AlertUserForm userForm = (AlertUserForm)form ;
		    userForm.setConfirmationMessage(new String());
		   	userForm.reset();
		   	userForm.setSelectedUserList(new ArrayList<Object>());
			userForm.setActionMode(" ");


		} catch (Exception e) {
			logger.error("Error while loading the user Alert: " + e.toString());
			throw new ServletException("Error while loading the user Alert: "+e.getMessage(),e);
		}

		request.setAttribute(AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS ,AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS);
		request.setAttribute(AlertAdminConstants.ALERT_ADMIN_HREF ,AlertAdminConstants.ALERT_ADMIN_HREF);

		return (mapping.findForward("alertAdminUser"));
	}

	public ActionForward addAlert(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		try {
			AlertUserForm userForm = (AlertUserForm)form ;
			AlertUserUtil.addNewAlert(userForm, request);
		} catch (Exception e) {
			logger.fatal("Error while loading the user Alert: " + e.toString());
			throw new ServletException("Error while loading the user Alert: "+e.getMessage(),e);
		}
		request.setAttribute(AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS ,AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS);
	    request.setAttribute(AlertAdminConstants.PAGE_TITLE ,AlertAdminConstants.MANAGE_ALERTS);
	    request.setAttribute(AlertAdminConstants.ALERT_ADMIN_HREF ,AlertAdminConstants.ALERT_ADMIN_HREF);
		return (mapping.findForward("addAlert"));
	}
	public ActionForward searchAlert(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		try {
			AlertUserForm userForm = (AlertUserForm)form ;
			AlertUserUtil.loadUserAlert(userForm, request);
		} catch (Exception e) {
			logger.error("Error while loading the user Alert: " + e.toString());
			throw new ServletException("Error while loading the user Alert: "+e.getMessage(),e);
		}
		request.setAttribute(AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS ,AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS);
	    request.setAttribute(AlertAdminConstants.PAGE_TITLE ,AlertAdminConstants.MANAGE_ALERTS);
	    request.setAttribute(AlertAdminConstants.ALERT_ADMIN_HREF ,AlertAdminConstants.ALERT_ADMIN_HREF);
		return (mapping.findForward("searchAlert"));
	}

	public ActionForward deleteAlert(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		try {
			AlertUserForm userForm = (AlertUserForm)form ;
			AlertUserUtil.deleteAlert(userForm, request);

		} catch (Exception e) {
			logger.fatal("Error while deleting the  Alert: " + e.toString());
			throw new ServletException("Error while deleting the  Alert: "+e.getMessage(),e);
		}
		request.setAttribute(AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS ,AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS);
	    request.setAttribute(AlertAdminConstants.PAGE_TITLE ,AlertAdminConstants.MANAGE_ALERTS);
	    request.setAttribute(AlertAdminConstants.ALERT_ADMIN_HREF ,AlertAdminConstants.ALERT_ADMIN_HREF);
		return (mapping.findForward("deleteAlert"));
	}
	public ActionForward updateAlert(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		try {
			AlertUserForm userForm = (AlertUserForm)form ;
			AlertUserUtil.loadUpdateAlert(userForm, request);
		} catch (Exception e) {
			logger.fatal("Error while deleting the  Alert: " + e.toString());
			throw new ServletException("Error while deleting the  Alert: "+e.getMessage(),e);
		}
		request.setAttribute(AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS ,AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS);
	    request.setAttribute(AlertAdminConstants.PAGE_TITLE ,AlertAdminConstants.MANAGE_ALERTS);
	    request.setAttribute(AlertAdminConstants.ALERT_ADMIN_HREF ,AlertAdminConstants.ALERT_ADMIN_HREF);
		return (mapping.findForward("updateAlert"));
	}
	public ActionForward sendSimulateMessage(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		try {
			AlertUserForm userForm = (AlertUserForm)form ;
			AlertUserUtil.sendSimulate(userForm, request);
		} catch (Exception e) {
			logger.error("Error while deleting the  Alert: " + e.toString());
			throw new ServletException("Error while deleting the  Alert: "+e.getMessage(),e);
		}
		request.setAttribute(AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS ,AlertAdminConstants.ALERT_ADMIN_REQUIRED_FIELDS);
	    request.setAttribute(AlertAdminConstants.PAGE_TITLE ,AlertAdminConstants.MANAGE_ALERTS);
	    request.setAttribute(AlertAdminConstants.ALERT_ADMIN_HREF ,AlertAdminConstants.ALERT_ADMIN_HREF);

		return (mapping.findForward("simulate"));
	}



}
