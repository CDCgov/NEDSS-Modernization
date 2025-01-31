package gov.cdc.nedss.webapp.nbs.form.provider;

/**
 * Title:        Actions
 * Description:  This is definately some code
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.*;

import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

public class ProviderSearchForm extends ActionForm {

  private ProviderSearchVO providerSearch = null;

  public ProviderSearchVO getProviderSearch(){
    if (providerSearch == null)
	providerSearch = new ProviderSearchVO();

	return this.providerSearch;
  }

  public void reset(){
   providerSearch=null;
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