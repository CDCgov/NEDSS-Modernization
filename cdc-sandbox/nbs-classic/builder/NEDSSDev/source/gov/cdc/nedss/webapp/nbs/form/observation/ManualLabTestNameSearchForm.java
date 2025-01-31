package gov.cdc.nedss.webapp.nbs.form.observation;

/**
 * Title:        Actions
 * Description:  This is definately some code
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */

import org.apache.struts.action.*;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;

public class ManualLabTestNameSearchForm extends ActionForm {

  private OrganizationSearchVO organizationSearch = null;

  public OrganizationSearchVO getOrganizationSearch(){
    if (organizationSearch == null)
	organizationSearch = new OrganizationSearchVO();

	return this.organizationSearch;
  }

  public void reset(){
   organizationSearch=null;
  }
}