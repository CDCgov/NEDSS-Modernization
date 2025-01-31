
/**
 * Title:        Actions
 * Description:  This is definately some code
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */
package gov.cdc.nedss.webapp.nbs.form.patientsearch;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PatientSearchForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	private String actionMode;
	private String returnToLink;
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private PatientSearchVO patientSearchVO = new PatientSearchVO();
	
	private String patientSearch;
	
	
	private String personMprUid;
	
	public String getPersonMprUid() {
		return personMprUid;
	}
	public void setPersonMprUid(String personMprUid) {
		this.personMprUid = personMprUid;
	}
	
	public Map<Object,Object> getSearchMap() {
		return searchMap;
	}
	public void setSearchMap(Map<Object,Object> searchMap) {
		this.searchMap = searchMap;
	}
	public String getActionMode() {
		return actionMode;
	}
	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}
	public String getReturnToLink() {
		return returnToLink;
	}
	public void setReturnToLink(String returnToLink) {
		this.returnToLink = returnToLink;
	}
	public Map<Object,Object> getAttributeMap() {
		return attributeMap;
	}
	public void setAttributeMap(Map<Object,Object> attributeMap) {
		this.attributeMap = attributeMap;
	}
	
	public String getPatientSearch() {
		return patientSearch;
	}
	public void setPatientSearch(String patientSearch) {
		this.patientSearch = patientSearch;
	}
	
	
	public void clearSelections() {
		setSearchMap(new HashMap<Object,Object>());
		setAttributeMap(new HashMap<Object,Object>());
		setActionMode(null);
		setPatientSearchVO(new PatientSearchVO());
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public PatientSearchVO getPatientSearchVO() {
		return patientSearchVO;
	}
	public void setPatientSearchVO(PatientSearchVO patientSearchVO) {
		this.patientSearchVO = patientSearchVO;
	}	
	
	
  

  public void reset(){
	  patientSearchVO=null;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {


	  ActionErrors errors = super.validate(mapping,request);
	    if(errors == null)
	    	errors = new ActionErrors();
      return errors;
  }

	public Object getCodedValue(String codesetNm) {
		return CachedDropDowns.getCodedValueForType(codesetNm);
	}
	
	public Object getCodedValueNoBlnk(String key) {
		ArrayList<Object> list = (ArrayList<Object> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		return list;
	}
}
