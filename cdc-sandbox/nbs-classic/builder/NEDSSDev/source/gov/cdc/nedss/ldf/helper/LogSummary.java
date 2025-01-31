package gov.cdc.nedss.ldf.helper;

import java.util.*;
import java.sql.*;
import java.io.Serializable;
import gov.cdc.nedss.ldf.importer.dao.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.importer.dt.*;
import gov.cdc.nedss.util.*;

public class LogSummary implements Serializable{

  private Long importVersionNbr = null;
  private Timestamp importDate = null;
  private String error = null;
  private String adminComments = null;
  private String status = null;
  private Collection<Object>  DataLogSummaryColl = null;

  public LogSummary() {
  }

  public Long getImportVersionNbr() {
    return importVersionNbr;
  }

  public void setImportVersionNbr(Long aImportVersionNbr) {
    importVersionNbr = aImportVersionNbr;
  }

  public Timestamp getImportDate() {
    return importDate;
  }

  public void setImportDate(Timestamp aImportDate) {
    importDate = aImportDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String aStatus) {
    status = aStatus;
  }

  public String getAdminComments() {
    return adminComments;
  }

  public void setAdminComments(String aAdminComments) {
    adminComments = aAdminComments;
  }

  public String getError() {
    return error;
  }

  public void setError(String aError) {
    error = aError;
  }

  public Collection<Object>  getDataLogSummaryColl() {
    return DataLogSummaryColl;
  }

  public void setDataLogSummaryColl(Long importVersionNbr, Long importLogUid) throws
      NEDSSAppException {
    ArrayList<Object> aDataLogSummaryColl = new ArrayList<Object> ();
    CdfSubformImportDataLogDAOImpl cdfSubformImportDataLogDAO = new
        CdfSubformImportDataLogDAOImpl();
    Collection<Object>  importDataLogColl = cdfSubformImportDataLogDAO.select(
        importLogUid);
    for (Iterator<Object> it = importDataLogColl.iterator(); it.hasNext(); ) {
      CdfSubformImportDataLogDT dt = (CdfSubformImportDataLogDT) it.next();
      if(dt!=null){
        DataLogSummary dataLogSummary = new DataLogSummary(dt);
        if (dt.getDataType().equalsIgnoreCase(NEDSSConstants.DF_DATA_TYPE)) {
          dataLogSummary.setDFMDSummary(importVersionNbr, dt.getDataOid());
        }
        else if (dt.getDataType().equalsIgnoreCase(NEDSSConstants.SF_DATA_TYPE)) {
          dataLogSummary.setSubformSummary(importVersionNbr, dt.getDataOid());
        }
        aDataLogSummaryColl.add(dataLogSummary);
      }
    }
        DataLogSummaryColl = aDataLogSummaryColl;

  }

}