package gov.cdc.nedss.webapp.nbs.form.alert.EmailAlert;

import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.alert.emailAlert.clientVO.EmailAlertClientVO;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class EmailAlertUserForm extends ActionForm {
			
	private static final long serialVersionUID = 1L;
	private EmailAlertClientVO emailAlertClientVO = new EmailAlertClientVO();
	private String confirmationMessage= "";
	
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
	  ActionErrors errors = super.validate(mapping,request);
			 if(errors == null)
			 errors = new ActionErrors();
	      return errors;
	  }
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
			super.reset(mapping, request);
			this.getEmailAlertClientVO().setEmailId1("");
			this.getEmailAlertClientVO().setEmailId2("");
			this.getEmailAlertClientVO().setEmailId3("");
			this.getEmailAlertClientVO().setNedssEntryId(0);
		}

	public EmailAlertClientVO getEmailAlertClientVO() {
		return emailAlertClientVO;
	}

	public void setEmailAlertClientVO(EmailAlertClientVO emailAlertClientVO) {
		this.emailAlertClientVO = emailAlertClientVO;
	}		
	
	public ArrayList<Object> getUsersList(){
		CachedDropDowns.resetCachedValues(NEDSSConstants.Users_With_Valid_Email);
		return CachedDropDowns.getEmailUsers();
		
	}
	public ArrayList<Object> getActiveAlertUsersList(){
		return CachedDropDowns.getUsersWithValidEmailLst();
	}

	public String getConfirmationMessage() {
		return confirmationMessage;
	}

	public void setConfirmationMessage(String confirmationMessage) {
		this.confirmationMessage = confirmationMessage;
	}

}
