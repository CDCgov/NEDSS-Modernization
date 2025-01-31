package gov.cdc.nedss.webapp.nbs.form.epilink;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class EpiLinkIdForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String currentEpiLinkId;
	private String confirmationMessage;
	private String newEpiLinkId;
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();

	public String getCurrentEpiLinkId() {
		return currentEpiLinkId;
	}

	public void setCurrentEpiLinkId(String currentEpiLinkId) {
		this.currentEpiLinkId = currentEpiLinkId;
	}

	public String getConfirmationMessage() {
		return confirmationMessage;
	}

	public void setConfirmationMessage(String confirmationMessage) {
		this.confirmationMessage = confirmationMessage;
	}

	public String getNewEpiLinkId() {
		return newEpiLinkId;
	}

	public void setNewEpiLinkId(String newEpiLinkId) {
		this.newEpiLinkId = newEpiLinkId;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
	  ActionErrors errors = super.validate(mapping,request);
			 if(errors == null)
			 errors = new ActionErrors();
			
	      return errors;
	  }
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
			super.reset(mapping, request);
			currentEpiLinkId="";
			newEpiLinkId="";
		}

	public Map<Object, Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<Object, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}
	
	
}
