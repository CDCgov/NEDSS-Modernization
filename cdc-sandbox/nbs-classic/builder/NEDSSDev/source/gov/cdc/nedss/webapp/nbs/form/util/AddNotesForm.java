package gov.cdc.nedss.webapp.nbs.form.util;

import org.apache.struts.action.ActionForm;
import gov.cdc.nedss.util.NEDSSConstants;

public class AddNotesForm extends ActionForm 
{
	private String notes;
	private String publicPrivateInd;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAccessModifier() {
		return publicPrivateInd;
	}

	public void setAccessModifier(String accessModifier) {
		this.publicPrivateInd = accessModifier;
	}

	public void reset() {
		notes = "";
		publicPrivateInd = NEDSSConstants.PUBLIC;
	}
}