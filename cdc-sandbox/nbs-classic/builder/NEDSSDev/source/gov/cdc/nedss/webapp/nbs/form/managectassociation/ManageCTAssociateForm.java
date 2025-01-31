package gov.cdc.nedss.webapp.nbs.form.managectassociation;

import java.util.Collection;

import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;

public class ManageCTAssociateForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private Collection<Object>  contactVOColl;
	private String[] selectedcheckboxIds;

	public Collection<Object> getContactVOColl() {
		return contactVOColl;
	}

	public void setContactVOColl(Collection<Object> contactVOColl) {
		this.contactVOColl = contactVOColl;
	}
	public String[] getSelectedcheckboxIds() {
		return selectedcheckboxIds;
	}

	public void setSelectedcheckboxIds(String[] selectedcheckboxIds) {
		this.selectedcheckboxIds = selectedcheckboxIds;
	}
	public void updateCheckboxIds(String checkboxIds) {
		if(checkboxIds!=null && !checkboxIds.trim().equals("")){
		String[] values = checkboxIds.split(":");
		this.selectedcheckboxIds = values ;
		}
	}		
   public void resetCheckboxIds(){
	   this.selectedcheckboxIds =null;
	   
   }
	
	
	
	
}
