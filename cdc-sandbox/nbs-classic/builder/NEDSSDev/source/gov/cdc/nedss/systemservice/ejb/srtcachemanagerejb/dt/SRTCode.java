package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt;

/**
 * <p>Title: SRTCode</p>
 * <p>Description: Holds code and description data. </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corp</p>
 * @author Mark Hankey
 *
 */

public interface SRTCode {
  public String getCode();
  public String getDesc();
  public Long getIndentLevel();
}