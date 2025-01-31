package gov.cdc.nedss.webapp.nbs.form.hivpartnerservices;

import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class HivPartnerServicesForm extends ActionForm {
	
	/**
	 * Manage HIV Partner Services file generation.
	 * Partner Services file is generated twice a year
	 * for the HIV program area only.
	 */
	private static final long serialVersionUID = 1L;
	
	private String reportingMonth;
	private String reportingYear;
	private String contactPerson;
	private String confirmationMessage;
	private String invFormCode; //phs form code
	private String ixsFormCode; //interview form code
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private NotificationProxyVO notificationProxyVO;
	
	public String getNotificationProxyVO() {
		return reportingMonth;
	}
	
	public void setNotificationProxyVO(NotificationProxyVO notificationProxyVO) {
		this.notificationProxyVO = notificationProxyVO;
	}
	
	/**
	 * @return the reportingMonth
	 * 3 or 9, March or Sept
	 */
	public String getReportingMonth() {
		return reportingMonth;
	}
	/**
	 * @param reportingMonth the reportingMonth to set
	 */
	public void setReportingMonth(String reportingMonth) {
		this.reportingMonth = reportingMonth;
	}
	/**
	 * @return the reportingYear
	 * 2012,2013,2014,2015,2016
	 */
	public String getReportingYear() {
		return reportingYear;
	}
	/**
	 * @param reportingYear the reportingYear to set
	 */
	public void setReportingYear(String reportingYear) {
		this.reportingYear = reportingYear;
	}
	/**
	 * @return the contactPerson
	 * Can include name,email,phone other text
	 */
	public String getContactPerson() {
		return contactPerson;
	}
	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
	  ActionErrors errors = super.validate(mapping,request);
			 if(errors == null)
			 errors = new ActionErrors();
			
	      return errors;
	  }
	
	public String getConfirmationMessage() {
		return confirmationMessage;
	}

	public void setConfirmationMessage(String confirmationMessage) {
		this.confirmationMessage = confirmationMessage;
	}
	
	/**
	 * @return the invFormCode
	 */
	public String getInvFormCode() {
		return invFormCode;
	}
	/**
	 * @param invFormCode the invFormCode to set
	 */
	public void setInvFormCode(String invFormCode) {
		this.invFormCode = invFormCode;
	}
	/**
	 * @return the ixsFormCode
	 */
	public String getIxsFormCode() {
		return ixsFormCode;
	}
	/**
	 * @param ixsFormCode the ixsFormCode to set
	 */
	public void setIxsFormCode(String ixsFormCode) {
		this.ixsFormCode = ixsFormCode;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
			super.reset(mapping, request);
			//clear attributes
	}

	public Map<Object, Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<Object, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}
	

}
