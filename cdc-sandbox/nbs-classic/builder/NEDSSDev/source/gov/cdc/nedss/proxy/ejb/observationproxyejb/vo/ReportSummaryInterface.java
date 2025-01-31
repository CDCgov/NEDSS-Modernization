package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import java.sql.*;

/**
 * <p>Title: </p>
 * <p>Description: interface for LabReportSummaryVO</p>
 * <p>Copyright: CSC Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Pradeep Kumar Sharma
 * @version 1.0
 */

public interface ReportSummaryInterface {
  public boolean getIsTouched();

  public void setItTouched(boolean touched);

  public boolean getIsAssociated();

  public void setItAssociated(boolean associated);

  public Long getObservationUid();

  public void setObservationUid(Long observationUid);

  public Timestamp getActivityFromTime();

  public void setActivityFromTime(Timestamp aActivityFromTime);

}