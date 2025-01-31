package gov.cdc.nedss.webapp.nbs.action.alert.emailAlert;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.alert.AlertAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.alert.emailAlert.util.EmailAlertUtil;
import gov.cdc.nedss.webapp.nbs.form.alert.EmailAlert.EmailAlertUserForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * Title:        EmailAlertAction
 * Description:  This class will access the information related to User EmailAlerts
 * Company:      CSC
 * @author Sita Siram
 */
public class EmailAlertAction extends DispatchAction {

	static final LogUtils logger = new LogUtils(EmailAlertAction.class.getName());

	public EmailAlertAction() {
		// TODO Auto-generated constructor stub
	}
	public ActionForward loadEmail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
			EmailAlertUserForm emailAlertUserForm = (EmailAlertUserForm) form;

		try {
			HttpSession session = request.getSession();
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
		    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.DECISION_SUPPORT_ADMIN);
		    if (securityCheck != true) {
		      session.setAttribute("error", "Failed at security checking.");
		      throw new ServletException("Failed at security checking.");
		    }
			EmailAlertUtil.handleEmail(emailAlertUserForm, NEDSSConstants.VIEW_LOAD_ACTION, request);
			request.setAttribute("usersList", emailAlertUserForm.getUsersList());
			//request.setAttribute("activeAlertUsersList", emailAlertUserForm.getActiveAlertUsersList());
			emailAlertUserForm.setConfirmationMessage("");

			} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in resultsLoad: " + e.getMessage());
		}finally{
			request.setAttribute(AlertAdminConstants.PAGE_TITLE ,AlertAdminConstants.VIEW_EMAIL);
		}
		request.setAttribute(AlertAdminConstants.ALERT_ADMIN_HREF ,AlertAdminConstants.ALERT_ADMIN_HREF);
		return (mapping.findForward("createEmailAlert"));
	}
	public ActionForward updateEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EmailAlertUserForm emailAlertUserForm = (EmailAlertUserForm) form;
		try {
			HttpSession session = request.getSession();
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
		    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.DECISION_SUPPORT_ADMIN);
		    if (securityCheck != true) {
		      session.setAttribute("error", "Failed at security checking.");
		      throw new ServletException("Failed at security checking.");
		    }
			EmailAlertUtil.handleEmail(emailAlertUserForm, NEDSSConstants.EDIT_LOAD_ACTION, request);
			request.setAttribute("usersList", emailAlertUserForm.getUsersList());
			if( emailAlertUserForm.getUsersList()!=null && !emailAlertUserForm.getUsersList().equals(""))
			{
				if(( emailAlertUserForm.getEmailAlertClientVO().getEmailId1()!=null  && !emailAlertUserForm.getEmailAlertClientVO().getEmailId1().trim().equals("")) ||
					(emailAlertUserForm.getEmailAlertClientVO().getEmailId2()!=null && !emailAlertUserForm.getEmailAlertClientVO().getEmailId2().trim().equals("")) ||
					( emailAlertUserForm.getEmailAlertClientVO().getEmailId3()!=null && !emailAlertUserForm.getEmailAlertClientVO().getEmailId3().trim().equals("")))
				{
						emailAlertUserForm.setConfirmationMessage("User Email Address(es) Updated Successfully");
					}
				else if( (emailAlertUserForm.getEmailAlertClientVO().getEmailId1()==null || emailAlertUserForm.getEmailAlertClientVO().getEmailId1().trim().equals(""))
					 && (emailAlertUserForm.getEmailAlertClientVO().getEmailId2()==null || emailAlertUserForm.getEmailAlertClientVO().getEmailId2().trim().equals(""))
					 && (emailAlertUserForm.getEmailAlertClientVO().getEmailId3()==null || emailAlertUserForm.getEmailAlertClientVO().getEmailId3().trim().equals("")))
				{
						emailAlertUserForm.setConfirmationMessage("");
					}
			}
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in resultsLoad: " + e.getMessage());
		} finally{
			request.setAttribute(AlertAdminConstants.PAGE_TITLE ,AlertAdminConstants.VIEW_EMAIL);
		}
		request.setAttribute(AlertAdminConstants.ALERT_ADMIN_HREF ,AlertAdminConstants.ALERT_ADMIN_HREF);
		return (mapping.findForward("updateEmailAlert"));
	}

}


