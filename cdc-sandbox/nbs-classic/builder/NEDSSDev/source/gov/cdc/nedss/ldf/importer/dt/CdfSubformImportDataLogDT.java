/*
 * Created on Jan 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CdfSubformImportDataLogDT
    extends AbstractVO implements RootDTInterface {

  private Long importLogUid;
  private String dataOid;
  private String dataType;
  private String actionType;
  private Timestamp importTime;
  private String logMessageTxt;
  private String processCd;
  private Integer versionCtrlNbr;

  private boolean itDirty = false;
  private boolean itNew = true;
  private boolean itDelete = false;

  /**
   * @roseuid 3F4E4AFF016E
   */
  public CdfSubformImportDataLogDT() {

  }

  /**
   * Access method for the versionCtrlNbr property.
   *
   * @return   the current value of the versionCtrlNbr property
   */
  public Integer getVersionCtrlNbr() {
    return versionCtrlNbr;
  }

  /**
   * Sets the value of the versionCtrlNbr property.
   *
   * @param aVersionCtrlNbr the new value of the versionCtrlNbr property
   */
  public void setVersionCtrlNbr(Integer aVersionCtrlNbr) {
    versionCtrlNbr = aVersionCtrlNbr;
  }

  /**
   * @param itDirty
   * @roseuid 3E3040CB01C4
   */
  public void setItDirty(boolean itDirty) {
    this.itDirty = itDirty;
  }

  /**
   * @return boolean
   * @roseuid 3E3040CB01E2
   */
  public boolean isItDirty() {
    return itDirty;
  }

  /**
   * @param itNew
   * @roseuid 3E3040CB01F6
   */
  public void setItNew(boolean itNew) {
    this.itNew = itNew;
  }

  /**
   * @return boolean
   * @roseuid 3E3040CB0214
   */
  public boolean isItNew() {
    return itNew;
  }

  /**
   * @param itDelete
   * @roseuid 3E3040CB021E
   */
  public void setItDelete(boolean itDelete) {
    this.itDelete = itDelete;
  }

  /**
   * @return boolean
   * @roseuid 3E3040CB023C
   */
  public boolean isItDelete() {
    return itDelete;
  }

  /**
   * @param objectname1
   * @param objectname2
   * @param voClass
   * @return boolean
   * @roseuid 3E3040CB0188
   */
  public boolean isEqual(
      Object objectname1,
      Object objectname2,
      Class<?> voClass) {
    return true;
  }

  /**
   * @param aSharedInd
   * @roseuid 3E3046570338
   */
  public void setSharedInd(String aSharedInd) {

  }

  /**
   * @return java.lang.String
   * @roseuid 3E3046570324
   */
  public String getSharedInd() {
    return null;
  }

  /**
   * @return java.lang.Long
   * @roseuid 3E30465702E8
   */
  public Long getProgramJurisdictionOid() {
    return null;
  }

  /**
   * @param aProgramJurisdictionOid
   * @roseuid 3E30465702FC
   */
  public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {

  }

  /**
   * @return java.lang.Long
   * @roseuid 3E30465702CA
   */
  public Long getUid() {
    return null;
  }

  /**
   * @return java.lang.String
   * @roseuid 3E30465702B6
   */
  public String getSuperclass() {
    return null;
  }

  /**
   * @return java.sql.Timestamp
   * @roseuid 3E304657027A
   */
  public Timestamp getStatusTime() {
    return null;
  }

  /**
   * @param aStatusTime
   * @roseuid 3E304657028E
   */
  public void setStatusTime(Timestamp aStatusTime) {

  }

  /**
   * @return java.lang.String
   * @roseuid 3E304657023E
   */
  public String getStatusCd() {
    return null;
  }

  /**
   * @param aStatusCd
   * @roseuid 3E3046570252
   */
  public void setStatusCd(String aStatusCd) {

  }

  /**
   * @return java.sql.Timestamp
   * @roseuid 3E3046570202
   */
  public Timestamp getRecordStatusTime() {
    return null;
  }

  /**
   * @param aRecordStatusTime
   * @roseuid 3E3046570216
   */
  public void setRecordStatusTime(Timestamp aRecordStatusTime) {

  }

  /**
   * @return java.lang.String
   * @roseuid 3E30465701C5
   */
  public String getRecordStatusCd() {
    return null;
  }

  /**
   * @param aRecordStatusCd
   * @roseuid 3E30465701DA
   */
  public void setRecordStatusCd(String aRecordStatusCd) {

  }

  /**
   * @return java.lang.String
   * @roseuid 3E3046570193
   */
  public String getLastChgReasonCd() {
    return null;
  }

  /**
   * @param aLastChgReasonCd
   * @roseuid 3E30465701A7
   */
  public void setLastChgReasonCd(String aLastChgReasonCd) {

  }

  /**
   * @return java.lang.Long
   * @roseuid 3E3046570085
   */
  public Long getLastChgUserId() {
    return null;
  }

  /**
   * @param aLastChgUserId
   * @roseuid 3E3046570099
   */
  public void setLastChgUserId(Long aLastChgUserId) {

  }

  /**
   * @return java.lang.Long
   * @roseuid 3E3046570157
   */
  public Long getAddUserId() {
    return null;
  }

  /**
   * @param aAddUserId
   * @roseuid 3E304657016B
   */
  public void setAddUserId(Long aAddUserId) {

  }

  /**
   * @return java.lang.String
   * @roseuid 3E3046570125
   */
  public String getLocalId() {
    return null;
  }

  /**
   * @param aLocalId
   * @roseuid 3E3046570139
   */
  public void setLocalId(String aLocalId) {

  }

  /**
   * @return java.lang.String
   * @roseuid 3E30465700E9
   */
  public String getProgAreaCd() {
    return null;
  }

  /**
   * @param aProgAreaCd
   * @roseuid 3E30465700FD
   */
  public void setProgAreaCd(String aProgAreaCd) {

  }

  /**
   * @return java.lang.String
   * @roseuid 3E30465700B7
   */
  public String getJurisdictionCd() {
    return null;
  }

  /**
   * @param aJurisdictionCd
   * @roseuid 3E30465700CB
   */
  public void setJurisdictionCd(String aJurisdictionCd) {

  }

  /**
   * Access method for the addTime property.
   *
   * @return   the current value of the addTime property
   */
  public Timestamp getAddTime() {
    return null;
  }

  /**
   * Sets the value of the addTime property.
   *
   * @param aAddTime the new value of the addTime property
   */
  public void setAddTime(Timestamp aAddTime) {
  }

  /**
   * Access method for the lastChgTime property.
   *
   * @return   the current value of the lastChgTime property
   */
  public Timestamp getLastChgTime() {
    return null;
  }

  /**
   * Sets the value of the lastChgTime property.
   *
   * @param aLastChgTime the new value of the lastChgTime property
   */
  public void setLastChgTime(Timestamp aLastChgTime) {
  }

  public Long getImportLogUid()
     {
         return importLogUid;
     }

     public void setImportLogUid(Long aImportLogUid)
     {
         importLogUid = aImportLogUid;
         setItDirty(true);
     }


     public String getDataOid()
     {
         return dataOid;
     }

     public void setDataOid(String aDataOid)
     {
         dataOid = aDataOid;
         setItDirty(true);
     }


     public String getDataType()
     {
         return dataType;
     }

     public void setDataType(String aDataType)
     {
         dataType = aDataType;
         setItDirty(true);
     }


     public String getActionType()
     {
         return actionType;
     }

     public void setActionType(String aActionType)
     {
         actionType = aActionType;
         setItDirty(true);
     }


     public Timestamp getImportTime()
     {
         return importTime;
     }

     public void setImportTime(Timestamp aImportTime)
     {
         importTime = aImportTime;
         setItDirty(true);
     }


     public String getLogMessageTxt()
     {
         return logMessageTxt;
     }

     public void setLogMessageTxt(String aLogMessageTxt)
     {
         logMessageTxt = aLogMessageTxt;
         setItDirty(true);
     }


     public String getProcessCd()
     {
         return processCd;
     }

     public void setProcessCd(String aProcessCd)
     {
         processCd = aProcessCd;
         setItDirty(true);
     }

}
