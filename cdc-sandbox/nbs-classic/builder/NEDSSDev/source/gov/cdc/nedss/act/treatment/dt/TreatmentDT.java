//Source file: C:\\RationalRoseDevelopment\\gov\\cdc\\nedss\\act\\treatment\\dt\\TreatmentDT.java

package gov.cdc.nedss.act.treatment.dt;

import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.StringUtils;
import java.sql.Timestamp;

public class TreatmentDT
    extends AbstractVO
    implements RootDTInterface
{
  private static final long serialVersionUID = 1L;
  private Long treatmentUid;
  private Timestamp activityFromTime;
  private Timestamp activityToTime;
  private Timestamp addTime;
  public Timestamp getAddTime() {
	return addTime;
}

public void setAddTime(Timestamp addTime) {
	this.addTime = addTime;
}

private String addReasonCd;
  private Long addUserId;
  private String cd;
  private String cdDescTxt;
  private String cdSystemCd;
  private String cdSystemDescTxt;
  private String cdVersion;
  private String classCd;
  private String lastChgReasonCd;
  private Timestamp lastChgTime;
  private Long lastChgUserId;
  private String localId;
  private String progAreaCd;
  private Long programJurisdictionOid;
  private String recordStatusCd;
  private Timestamp recordStatusTime;
  private String sharedInd;
  private String statusCd;
  private Timestamp statusTime;
  private String txt;
  private Integer versionCtrlNbr;

  /**
   * @roseuid 3E9AE7AA029F
   */
  public TreatmentDT()
  {

  }

  /**
   * Access method for the treatmentUid property.
   *
   * @return   the current value of the treatmentUid property
   */
  public Long getTreatmentUid()
  {
    return treatmentUid;
  }

  /**
   * Sets the value of the treatmentUid property.
   *
   * @param aTreatmentUid the new value of the treatmentUid property
   */
  public void setTreatmentUid(Long aTreatmentUid)
  {
    treatmentUid = aTreatmentUid;
  }

  /**
   * Access method for the activityFromTime property.
   *
   * @return   the current value of the activityFromTime property
   */
  public Timestamp getActivityFromTime()
  {
    return activityFromTime;
  }

  /**
   * Sets the value of the activityFromTime property.
   *
   * @param aActivityFromTime the new value of the activityFromTime property
   */
  public void setActivityFromTime(Timestamp aActivityFromTime)
  {
    activityFromTime = aActivityFromTime;
  }

  /**
   * sets ActivityToTime(convenient method for struts)
   * @param strTime : String value
   */
  public void setActivityFromTime_s(String strTime)
  {
    if (strTime == null)
    {
      return;
    }
    this.setActivityFromTime(StringUtils.stringToStrutsTimestamp(strTime));
  }

  /**
   * Access method for the activityToTime property.
   *
   * @return   the current value of the activityToTime property
   */
  public Timestamp getActivityToTime()
  {
    return activityToTime;
  }

  /**
   * Sets the value of the activityToTime property.
   *
   * @param aActivityToTime the new value of the activityToTime property
   */
  public void setActivityToTime(Timestamp aActivityToTime)
  {
    activityToTime = aActivityToTime;
  }

  /**
   * sets ActivityToTime(convenient method for struts)
   * @param strTime : String value
   */
  public void setActivityToTime_s(String strTime)
  {
    if (strTime == null)
    {
      return;
    }
    this.setActivityToTime(StringUtils.stringToStrutsTimestamp(strTime));
  }

  /**
   * Access method for the addReasonCd property.
   *
   * @return   the current value of the addReasonCd property
   */
  public String getAddReasonCd()
  {
    return addReasonCd;
  }

  /**
   * Sets the value of the addReasonCd property.
   *
   * @param aAddReasonCd the new value of the addReasonCd property
   */
  public void setAddReasonCd(String aAddReasonCd)
  {
    addReasonCd = aAddReasonCd;
  }

  /**
   * Access method for the addUserId property.
   *
   * @return   the current value of the addUserId property
   */
  public Long getAddUserId()
  {
    return addUserId;
  }

  /**
   * Sets the value of the addUserId property.
   *
   * @param aAddUserId the new value of the addUserId property
   */
  public void setAddUserId(Long aAddUserId)
  {
    addUserId = aAddUserId;
  }

  /**
   * Access method for the cd property.
   *
   * @return   the current value of the cd property
   */
  public String getCd()
  {
    return cd;
  }

  /**
   * Sets the value of the cd property.
   *
   * @param aCd the new value of the cd property
   */
  public void setCd(String aCd)
  {
    cd = aCd;
  }

  /**
   * Access method for the cdDescTxt property.
   *
   * @return   the current value of the cdDescTxt property
   */
  public String getCdDescTxt()
  {
    return cdDescTxt;
  }

  /**
   * Sets the value of the cdDescTxt property.
   *
   * @param aCdDescTxt the new value of the cdDescTxt property
   */
  public void setCdDescTxt(String aCdDescTxt)
  {
    cdDescTxt = aCdDescTxt;
  }

  /**
   * Access method for the cdSystemCd property.
   *
   * @return   the current value of the cdSystemCd property
   */
  public String getCdSystemCd()
  {
    return cdSystemCd;
  }

  /**
   * Sets the value of the cdSystemCd property.
   *
   * @param aCdSystemCd the new value of the cdSystemCd property
   */
  public void setCdSystemCd(String aCdSystemCd)
  {
    cdSystemCd = aCdSystemCd;
  }

  /**
   * Access method for the cdSystemDescTxt property.
   *
   * @return   the current value of the cdSystemDescTxt property
   */
  public String getCdSystemDescTxt()
  {
    return cdSystemDescTxt;
  }

  /**
   * Sets the value of the cdSystemDescTxt property.
   *
   * @param aCdSystemDescTxt the new value of the cdSystemDescTxt property
   */
  public void setCdSystemDescTxt(String aCdSystemDescTxt)
  {
    cdSystemDescTxt = aCdSystemDescTxt;
  }

  /**
   * Access method for the cdVersion property.
   *
   * @return   the current value of the cdVersion property
   */
  public String getCdVersion()
  {
    return cdVersion;
  }

  /**
   * Sets the value of the cdVersion property.
   *
   * @param aCdVersion the new value of the cdVersion property
   */
  public void setCdVersion(String aCdVersion)
  {
    cdVersion = aCdVersion;
  }

  /**
   * Access method for the classCd property.
   *
   * @return   the current value of the classCd property
   */
  public String getClassCd()
  {
    return classCd;
  }

  /**
   * Sets the value of the classCd property.
   *
   * @param aClassCd the new value of the classCd property
   */
  public void setClassCd(String aClassCd)
  {
    classCd = aClassCd;
  }

  /**
   * Access method for the lastChgReasonCd property.
   *
   * @return   the current value of the lastChgReasonCd property
   */
  public String getLastChgReasonCd()
  {
    return lastChgReasonCd;
  }

  /**
   * Sets the value of the lastChgReasonCd property.
   *
   * @param aLastChgReasonCd the new value of the lastChgReasonCd property
   */
  public void setLastChgReasonCd(String aLastChgReasonCd)
  {
    lastChgReasonCd = aLastChgReasonCd;
  }

  /**
   * Access method for the lastChgTime property.
   *
   * @return   the current value of the lastChgTime property
   */
  public Timestamp getLastChgTime()
  {
    return lastChgTime;
  }

  /**
   * Sets the value of the lastChgTime property.
   *
   * @param aLastChgTime the new value of the lastChgTime property
   */
  public void setLastChgTime(Timestamp aLastChgTime)
  {
    lastChgTime = aLastChgTime;
  }

  /**
   * sets LastChgTime( convenient method for struts)
   * @param strTime : String value
   */
  public void setLastChgTime_s(String strTime)
  {
    if (strTime == null)
    {
      return;
    }
    this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
  }

  /**
   * Access method for the lastChgUserId property.
   *
   * @return   the current value of the lastChgUserId property
   */
  public Long getLastChgUserId()
  {
    return lastChgUserId;
  }

  /**
   * Sets the value of the lastChgUserId property.
   *
   * @param aLastChgUserId the new value of the lastChgUserId property
   */
  public void setLastChgUserId(Long aLastChgUserId)
  {
    lastChgUserId = aLastChgUserId;
  }

  /**
   * Access method for the localId property.
   *
   * @return   the current value of the localId property
   */
  public String getLocalId()
  {
    return localId;
  }

  /**
   * Sets the value of the localId property.
   *
   * @param aLocalId the new value of the localId property
   */
  public void setLocalId(String aLocalId)
  {
    localId = aLocalId;
  }

  /**
   * Access method for the programAreaCd property.
   *
   * @return   the current value of the programAreaCd property
   */
  public String getProgAreaCd()
  {
    return progAreaCd;
  }

  /**
   * Sets the value of the programAreaCd property.
   *
   * @param aProgramAreaCd the new value of the programAreaCd property
   */
  public void setProgAreaCd(String aProgAreaCd)
  {
    progAreaCd = aProgAreaCd;
  }

  /**
   * Access method for the programJurisdictionOid property.
   *
   * @return   the current value of the programJurisdictionOid property
   */
  public Long getProgramJurisdictionOid()
  {
    return programJurisdictionOid;
  }

  /**
   * Sets the value of the programJurisdictionOid property.
   *
   * @param aProgramJurisdictionOid the new value of the programJurisdictionOid property
   */
  public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
  {
    programJurisdictionOid = aProgramJurisdictionOid;
  }

  /**
   * Access method for the recordStatusCd property.
   *
   * @return   the current value of the recordStatusCd property
   */
  public String getRecordStatusCd()
  {
    return recordStatusCd;
  }

  /**
   * Sets the value of the recordStatusCd property.
   *
   * @param aRecordStatusCd the new value of the recordStatusCd property
   */
  public void setRecordStatusCd(String aRecordStatusCd)
  {
    recordStatusCd = aRecordStatusCd;
  }

  /**
   * Access method for the recordStatusTime property.
   *
   * @return   the current value of the recordStatusTime property
   */
  public Timestamp getRecordStatusTime()
  {
    return recordStatusTime;
  }

  /**
   * Sets the value of the recordStatusTime property.
   *
   * @param aRecordStatusTime the new value of the recordStatusTime property
   */
  public void setRecordStatusTime(Timestamp aRecordStatusTime)
  {
    recordStatusTime = aRecordStatusTime;
  }

  /**
   * sets RecordStatusTime(convenient method for struts)
   * @param strTime : String value
   */
  public void setRecordStatusTime_s(String strTime)
  {
    if (strTime == null)
    {
      return;
    }
    this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
  }

  /**
   * Access method for the sharedInd property.
   *
   * @return   the current value of the sharedInd property
   */
  public String getSharedInd()
  {
    return sharedInd;
  }

  /**
   * Sets the value of the sharedInd property.
   *
   * @param aSharedInd the new value of the sharedInd property
   */
  public void setSharedInd(String aSharedInd)
  {
    sharedInd = aSharedInd;
  }

  /**
   * Access method for the statusCd property.
   *
   * @return   the current value of the statusCd property
   */
  public String getStatusCd()
  {
    return statusCd;
  }

  /**
   * Sets the value of the statusCd property.
   *
   * @param aStatusCd the new value of the statusCd property
   */
  public void setStatusCd(String aStatusCd)
  {
    statusCd = aStatusCd;
  }

  /**
   * Access method for the statusTime property.
   *
   * @return   the current value of the statusTime property
   */
  public Timestamp getStatusTime()
  {
    return statusTime;
  }

  /**
   * Sets the value of the statusTime property.
   *
   * @param aStatusTime the new value of the statusTime property
   */
  public void setStatusTime(Timestamp aStatusTime)
  {
    statusTime = aStatusTime;
  }

  /**
   * sets StatusTime( convenient method for struts)
   * @param strTime : String value
   */
  public void setStatusTime_s(String strTime)
  {
    if (strTime == null)
    {
      return;
    }
    this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
  }

  /**
   * Access method for the txt property.
   *
   * @return   the current value of the txt property
   */
  public String getTxt()
  {
    return txt;
  }

  /**
   * Sets the value of the txt property.
   *
   * @param aTxt the new value of the txt property
   */
  public void setTxt(String aTxt)
  {
    txt = aTxt;
  }

  /**
   * Access method for the versionCtrlNbr property.
   *
   * @return   the current value of the versionCtrlNbr property
   */
  public Integer getVersionCtrlNbr()
  {
    return versionCtrlNbr;
  }

  /**
   * Sets the value of the versionCtrlNbr property.
   *
   * @param aVersionCtrlNbr the new value of the versionCtrlNbr property
   */
  public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
  {
    versionCtrlNbr = aVersionCtrlNbr;
  }

  /**
   * @param objectname1
   * @param objectname2
   * @param voClass
   * @return boolean
   * @roseuid 3EAECC070398
   */
  public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
  {
    return true;
  }

  /**
   * @return java.lang.String
   * @roseuid 3EAECC0B0165
   */
  public String getJurisdictionCd()
  {
    return null;
  }

  /**
   * @param aJurisdictionCd
   * @roseuid 3EAECC0B03D6
   */
  public void setJurisdictionCd(String aJurisdictionCd)
  {

  }

  /**
   * @return java.lang.String
   * @roseuid 3EAECC0D02BD
   */
  public String getSuperclass()
  {
   return NEDSSConstants.CLASSTYPE_ACT;
 }


  /**
   * @return java.lang.Long
   * @roseuid 3EAECC0D0369
   */
  public Long getUid()
  {
    return treatmentUid;
  }

  /**
   * @return boolean
   * @roseuid 3EAECC0E01E2
   */
  public boolean isItNew()
  {
    return itNew;
  }

  /**
   * @param itNew
   * @roseuid 3EAECC0E028E
   */
  public void setItNew(boolean itNew)
  {
    this.itNew = itNew;
  }

  /**
   * @return boolean
   * @roseuid 3EAECC0E03A7
   */
  public boolean isItDirty()
  {
    return itDirty;
  }

  /**
   * @param itDirty
   * @roseuid 3EAECC0F006B
   */
  public void setItDirty(boolean itDirty)
  {
    this.itDirty = itDirty;
  }

  /**
   * @return boolean
   * @roseuid 3EAECC0F01B3
   */
  public boolean isItDelete()
  {
    return itDelete;
  }

  /**
   * @param itDelete
   * @roseuid 3EAECC0F02AD
   */
  public void setItDelete(boolean itDelete)
  {
    this.itDelete = itDelete;
  }
}
