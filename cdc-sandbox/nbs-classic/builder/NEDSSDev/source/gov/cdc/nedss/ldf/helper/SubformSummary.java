package gov.cdc.nedss.ldf.helper;

import java.util.*;
import gov.cdc.nedss.ldf.subform.dao.*;
import gov.cdc.nedss.ldf.subform.dt.*;
import gov.cdc.nedss.ldf.importer.dao.*;
import gov.cdc.nedss.ldf.importer.dt.*;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.*;
import gov.cdc.nedss.ldf.dt.*;
import java.io.*;
import gov.cdc.nedss.exception.*;

public class SubformSummary implements Serializable{

  private String pageSet = null;
  private String label = null; //same as subform name
  private String oid = null;
  private Collection<Object>  DFMDSummaryColl = null;

  public SubformSummary() {
  }

  public SubformSummary(Long importVersionNbr, String subformOid) throws NEDSSAppException{
    CustomSubformMetadataDAO customSubformMetadataDAO = new CustomSubformMetadataDAO();
    CustomSubformMetadataDT dt = (CustomSubformMetadataDT)customSubformMetadataDAO.selectBySubformOid(subformOid, importVersionNbr);
    if(dt != null)
    {
      String pageSetID = dt.getPageSetId();
      PageSets pageSetsHelp = new PageSets();
      pageSet = pageSetsHelp.getPageSetDT(pageSetID).getCodeShortDescTxt();
      label = dt.getSubformNm();

      oid = subformOid;

      ArrayList<Object> aDFMDSummaryColl = new ArrayList<Object> ();
      StateDefinedFieldMetaDataDAOImpl stateDefinedFieldMetaDataDAOImpl = new
          StateDefinedFieldMetaDataDAOImpl();
      Collection<Object>  dfmdColl = stateDefinedFieldMetaDataDAOImpl.
          selectMetaDataBySubformUid(dt.getCustomSubformMetadataUid());

      for (Iterator<Object> it = dfmdColl.iterator(); it.hasNext(); ) {
        StateDefinedFieldMetaDataDT dfmddt = (StateDefinedFieldMetaDataDT) it.
            next();

        DFMDSummary dFMDSummary = new DFMDSummary(dfmddt);
        aDFMDSummaryColl.add(dFMDSummary);
      }

      DFMDSummaryColl = aDFMDSummaryColl;
    }
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

  public Collection<Object>  getDFMDSummaryColl() {
    return DFMDSummaryColl;
  }

  public void setDFMDSummaryColl(Collection<Object> aDFMDSummaryColl) {
    DFMDSummaryColl = aDFMDSummaryColl;
  }


}