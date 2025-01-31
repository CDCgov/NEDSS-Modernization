package gov.cdc.nedss.ldf.helper;

import java.util.*;
import java.io.Serializable;
import gov.cdc.nedss.ldf.importer.dt.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.importer.*;


public class DataLogSummary implements Serializable{

  private String status = null;
  private String error = null;
  private DFMDSummary dfmdSummary = null;
  private SubformSummary subformSummary = null;

  public DataLogSummary() {
  }

  public DataLogSummary(CdfSubformImportDataLogDT dt){
    if(dt!=null){
      if(dt.getProcessCd().equalsIgnoreCase(ImportConstants.SUCCESS_CODE)){
        status = ImportConstants.SUCCESS_CODE;
      }else{
        status = ImportConstants.FAILURE_CODE;
      }
      error = dt.getLogMessageTxt();
    }
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String aStatus) {
    status = aStatus;
  }

  public String getError() {
    return error;
  }

  public void setError(String aError) {
    error = aError;
  }

  public DFMDSummary getDFMDSummary() {
    return dfmdSummary;
  }

  public void setDFMDSummary(Long importVersionNbr, String dataOid) throws NEDSSAppException{
    dfmdSummary = new DFMDSummary(importVersionNbr, dataOid);
  }

  public SubformSummary getSubformSummary() {
    return subformSummary;
  }

  public void setSubformSummary(Long importVersionNbr, String dataOid) throws NEDSSAppException{
    subformSummary = new SubformSummary(importVersionNbr, dataOid);
  }

}