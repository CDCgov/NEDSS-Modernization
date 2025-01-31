package gov.cdc.nedss.webapp.nbs.form.organization;

/**
 * Title:        Actions
 * Description:  This is definately some code
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */

import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class OrganizationSearchForm extends BaseForm {

  private OrganizationSearchVO organizationSearch = null;
  private String state;
  private ArrayList<Object> stateList ;
  private String dwrOrganizationDetails;
  static final LogUtils logger = new LogUtils(PamForm.class.getName());

  public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

public OrganizationSearchVO getOrganizationSearch(){
    if (organizationSearch == null)
	organizationSearch = new OrganizationSearchVO();

	return this.organizationSearch;
  }

  public void reset(){
   organizationSearch=null;
  }

  public Object getCodedValue(String codesetNm) {
		return CachedDropDowns.getCodedValueForType(codesetNm);
  }

  public ArrayList<Object> getStateList(){
		this.stateList = CachedDropDowns.getStateList();
		return stateList;
	}
  public String getDwrOrganizationDetailsByUid(String organizationUid) {

	   WebContext ctx = WebContextFactory.get();
	   HttpServletRequest req = ctx.getHttpServletRequest();
	   Map<Object,Object> returnMap= new HashMap<Object,Object>();

	try {

		ArrayList<?> list = (ArrayList<?> )req.getSession().getAttribute("OrganizationSrchResults");

		if(organizationUid != null && organizationUid.trim().length() > 0)

			returnMap = NedssCodeLookupServlet.buildOrganizationResult(list, organizationUid, req.getSession());
			//returnMap = NedssCodeLookupServlet.getOrgValue(organizationUid, req.getSession());
	} catch (Exception e) {
		dwrOrganizationDetails =   "";
		logger.error("Exception raised in getDwrOrganizationDetailsByUid: " + e);
	}
	dwrOrganizationDetails =   (String)returnMap.get("result");
	getAttributeMap().put("organizationUid", organizationUid);
	getAttributeMap().put("completeOrganizationSearchResult",dwrOrganizationDetails);
   return dwrOrganizationDetails;

 }
  public void clearDWROrganization() {
		getAttributeMap().remove("organizationUid");
		getAttributeMap().remove("completeOrganizationSearchResult");
	}
  public Object getCodedValueNoBlnk(String key) {
		ArrayList<?> list = (ArrayList<?> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		return list;
	}

}