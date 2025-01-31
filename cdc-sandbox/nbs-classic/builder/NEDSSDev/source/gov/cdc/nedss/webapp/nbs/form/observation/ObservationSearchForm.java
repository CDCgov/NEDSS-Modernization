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
import gov.cdc.nedss.entity.observation.vo.*;

public class ObservationSearchForm extends ActionForm {

  private ObservationSearchVO observationSearch = null;

  public ObservationSearchVO getObservationSearch(){
    if (observationSearch == null)
	observationSearch = new ObservationSearchVO();

	return this.observationSearch;
  }

  public void reset(){
   observationSearch=null;
  }
}