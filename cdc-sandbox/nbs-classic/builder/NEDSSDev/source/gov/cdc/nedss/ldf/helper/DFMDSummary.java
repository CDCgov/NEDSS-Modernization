package gov.cdc.nedss.ldf.helper;

import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.*;
import gov.cdc.nedss.ldf.dt.*;
import java.io.Serializable;
import gov.cdc.nedss.exception.*;

public class DFMDSummary implements Serializable{

  private String pageSet = null;
  private String label = null;
  private String oid = null;

  public DFMDSummary() {
  }

  public DFMDSummary(Long importVersionNbr, String ldfOid) throws NEDSSAppException{
    StateDefinedFieldMetaDataDAOImpl stateDefinedFieldMetaDataDAO = new StateDefinedFieldMetaDataDAOImpl();
    StateDefinedFieldMetaDataDT dt = stateDefinedFieldMetaDataDAO.selectMetaDataByLdfOid(ldfOid, importVersionNbr);
    if(dt !=null)
    {
        String pageSetID = dt.getLdfPageId();
        PageSets pageSets = new PageSets();
        pageSet = pageSets.getPageSetDT(pageSetID).getCodeShortDescTxt();
        label = dt.getLabelTxt();
        oid = dt.getLdfOid();
    }
  }

  public DFMDSummary(StateDefinedFieldMetaDataDT dt) {
   String pageSetID = dt.getLdfPageId();
   pageSet = pageSetID; //TODO: get pageset by SRT
   label = dt.getLabelTxt();
   oid = dt.getLdfOid();
 }


  public String getPageSet() {
    return pageSet;
  }

  public void setPageSet(String aPageSet) {
    pageSet = aPageSet;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String aLabel) {
    label = aLabel;
  }

  public String getOid() {
    return oid;
  }

  public void setOid(String aOid) {
    oid = aOid;
  }

}