package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.util.*;
import java.util.*;
/**
 * <p>Title: ObservationSummaryVOCollection</p>
 * <p>Description: ObservationSummaryVOCollection  contains collection of
 * LabReportSummaryVO collections and MorbReportSummaryVO Collections
 * </p>
 * <p>Copyright: CSC Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author pradeep Kumar Sharma
 * @version 1.0
 */

public class ObservationSummaryVOCollection
    extends AbstractVO {
	private static final long serialVersionUID = 1L;

  private Collection<Object>  theLabReportSummaryVOCollection;
  private Collection<Object>  theMorbReportSummaryVOCollection;

  public ObservationSummaryVOCollection() {
  }

  public Collection<Object>  getTheLabReportSummaryVOCollection() {
     return theLabReportSummaryVOCollection;
   }

   public void setTheLabReportSummaryVOCollection(Collection<Object> aTheLabReportSummaryVOCollection) {
     theLabReportSummaryVOCollection  = aTheLabReportSummaryVOCollection;
   }

   public Collection<Object>  getTheMorbReportSummaryVOCollection() {
      return theMorbReportSummaryVOCollection;
    }

    public void setTheMorbReportSummaryVOCollection(Collection<Object> aTheMorbReportSummaryVOCollection) {
      theMorbReportSummaryVOCollection  = aTheMorbReportSummaryVOCollection;
    }


  public boolean isItNew() {
    return itNew;
  }

  public boolean isItDirty() {
    return itDirty;
  }

  public boolean isItDelete() {
    return itDelete;
  }

  public void setItNew(boolean itNew) {
    this.itNew = itNew;
  }

  public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    return true;
  }

  public void setItDelete(boolean itDelete) {
    this.itDelete = itDelete;
  }

  public void setItDirty(boolean itDirty) {
    this.itDirty = itDirty;
  }

}