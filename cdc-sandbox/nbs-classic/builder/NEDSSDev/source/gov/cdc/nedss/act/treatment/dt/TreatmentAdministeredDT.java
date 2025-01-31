//Source file: C:\\RationalRoseDevelopment\\gov\\cdc\\nedss\\act\\treatment\\dt\\TreatmentAdministeredDT.java

package gov.cdc.nedss.act.treatment.dt;

import gov.cdc.nedss.util.AbstractVO;
import java.sql.Timestamp;
import gov.cdc.nedss.util.StringUtils;

public class TreatmentAdministeredDT
    extends AbstractVO
{
	private static final long serialVersionUID = 1L;
  private Long treatmentUid;
  private Integer treatmentAdministeredSeq;
  private String cd;
  private String cdDescTxt;
  private String cdSystemCd;
  private String cdSystemDescTxt;
  private String cdVersion;
  private String doseQty;
  private String doseQtyUnitCd;
  private String effectiveDurationAmt;
  private String effectiveDurationUnitCd;
  private Timestamp effectiveFromTime;
  private Timestamp effectiveToTime;
  private String formCd;
  private String formDescTxt;
  private String intervalCd;
  private String intervalDescTxt;
  private String rateQty;
  private String rateQtyUnitCd;
  private String routeCd;
  private String routeDescTxt;
  private String statueCd;
  private Timestamp statusTime;

  /**
   * @roseuid 3E9AE7AA008C
   */
  public TreatmentAdministeredDT()
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
   * Access method for the treatmentAdministeredSeq property.
   *
   * @return   the current value of the treatmentAdministeredSeq property
   */
  public Integer getTreatmentAdministeredSeq()
  {
    return treatmentAdministeredSeq;
  }

  /**
   * Sets the value of the treatmentAdministeredSeq property.
   *
   * @param aTreatmentAdministeredSeq the new value of the treatmentAdministeredSeq property
   */
  public void setTreatmentAdministeredSeq(Integer aTreatmentAdministeredSeq)
  {
    treatmentAdministeredSeq = aTreatmentAdministeredSeq;
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
   * Access method for the doseQty property.
   *
   * @return   the current value of the doseQty property
   */
  public String getDoseQty()
  {
    return doseQty;
  }

  /**
   * Sets the value of the doseQty property.
   *
   * @param aDoseQty the new value of the doseQty property
   */
  public void setDoseQty(String aDoseQty)
  {
    doseQty = aDoseQty;
  }

  /**
   * Access method for the doseQtyUnitCd property.
   *
   * @return   the current value of the doseQtyUnitCd property
   */
  public String getDoseQtyUnitCd()
  {
    return doseQtyUnitCd;
  }

  /**
   * Sets the value of the doseQtyUnitCd property.
   *
   * @param aDoseQtyUnitCd the new value of the doseQtyUnitCd property
   */
  public void setDoseQtyUnitCd(String aDoseQtyUnitCd)
  {
    doseQtyUnitCd = aDoseQtyUnitCd;
  }

  /**
   * Access method for the effectiveDurationAmt property.
   *
   * @return   the current value of the effectiveDurationAmt property
   */
  public String getEffectiveDurationAmt()
  {
    return effectiveDurationAmt;
  }

  /**
   * Sets the value of the effectiveDurationAmt property.
   *
   * @param aEffectiveDurationAmt the new value of the effectiveDurationAmt property
   */
  public void setEffectiveDurationAmt(String aEffectiveDurationAmt)
  {
    effectiveDurationAmt = aEffectiveDurationAmt;
  }

  /**
   * Access method for the effectiveDurationUnitCd property.
   *
   * @return   the current value of the effectiveDurationUnitCd property
   */
  public String getEffectiveDurationUnitCd()
  {
    return effectiveDurationUnitCd;
  }

  /**
   * Sets the value of the effectiveDurationUnitCd property.
   *
   * @param aEffectiveDurationUnitCd the new value of the effectiveDurationUnitCd property
   */
  public void setEffectiveDurationUnitCd(String aEffectiveDurationUnitCd)
  {
    effectiveDurationUnitCd = aEffectiveDurationUnitCd;
  }

  /**
   * Access method for the effectiveFromTime property.
   *
   * @return   the current value of the effectiveFromTime property
   */
  public Timestamp getEffectiveFromTime()
  {
    return effectiveFromTime;
  }

  /**
   * Sets the value of the effectiveFromTime property.
   *
   * @param aEffectiveFromTime the new value of the effectiveFromTime property
   */
  public void setEffectiveFromTime(Timestamp aEffectiveFromTime)
  {
    effectiveFromTime = aEffectiveFromTime;
  }

  /**
   * sets setEffectiveFromTime( convenient method for struts)
   * @param strTime : String value
   */
  public void setEffectiveFromTime_s(String strTime)
  {
    if (strTime == null)
    {
      return;
    }
    this.setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(strTime));
  }

  /**
   * Access method for the effectiveToTime property.
   *
   * @return   the current value of the effectiveToTime property
   */
  public Timestamp getEffectiveToTime()
  {
    return effectiveToTime;
  }

  /**
   * Sets the value of the effectiveToTime property.
   *
   * @param aEffectiveToTime the new value of the effectiveToTime property
   */
  public void setEffectiveToTime(Timestamp aEffectiveToTime)
  {
    effectiveToTime = aEffectiveToTime;
  }

  /**
   * sets setEffectiveToTime( convenient method for struts)
   * @param strTime : String value
   */
  public void setEffectiveToTime_s(String strTime)
  {
    if (strTime == null)
    {
      return;
    }
    this.setEffectiveToTime(StringUtils.stringToStrutsTimestamp(strTime));
  }

  /**
   * Access method for the formCd property.
   *
   * @return   the current value of the formCd property
   */
  public String getFormCd()
  {
    return formCd;
  }

  /**
   * Sets the value of the formCd property.
   *
   * @param aFormCd the new value of the formCd property
   */
  public void setFormCd(String aFormCd)
  {
    formCd = aFormCd;
  }

  /**
   * Access method for the formDescTxt property.
   *
   * @return   the current value of the formDescTxt property
   */
  public String getFormDescTxt()
  {
    return formDescTxt;
  }

  /**
   * Sets the value of the formDescTxt property.
   *
   * @param aFormDescTxt the new value of the formDescTxt property
   */
  public void setFormDescTxt(String aFormDescTxt)
  {
    formDescTxt = aFormDescTxt;
  }

  /**
   * Access method for the intervalCd property.
   *
   * @return   the current value of the intervalCd property
   */
  public String getIntervalCd()
  {
    return intervalCd;
  }

  /**
   * Sets the value of the intervalCd property.
   *
   * @param aIntervalCd the new value of the intervalCd property
   */
  public void setIntervalCd(String aIntervalCd)
  {
    intervalCd = aIntervalCd;
  }

  /**
   * Access method for the intervalDescTxt property.
   *
   * @return   the current value of the intervalDescTxt property
   */
  public String getIntervalDescTxt()
  {
    return intervalDescTxt;
  }

  /**
   * Sets the value of the intervalDescTxt property.
   *
   * @param aIntervalDescTxt the new value of the intervalDescTxt property
   */
  public void setIntervalDescTxt(String aIntervalDescTxt)
  {
    intervalDescTxt = aIntervalDescTxt;
  }

  /**
   * Access method for the rateQty property.
   *
   * @return   the current value of the rateQty property
   */
  public String getRateQty()
  {
    return rateQty;
  }

  /**
   * Sets the value of the rateQty property.
   *
   * @param aRateQty the new value of the rateQty property
   */
  public void setRateQty(String aRateQty)
  {
    rateQty = aRateQty;
  }

  /**
   * Access method for the rateQtyUnitCd property.
   *
   * @return   the current value of the rateQtyUnitCd property
   */
  public String getRateQtyUnitCd()
  {
    return rateQtyUnitCd;
  }

  /**
   * Sets the value of the rateQtyUnitCd property.
   *
   * @param aRateQtyUnitCd the new value of the rateQtyUnitCd property
   */
  public void setRateQtyUnitCd(String aRateQtyUnitCd)
  {
    rateQtyUnitCd = aRateQtyUnitCd;
  }

  /**
   * Access method for the routeCd property.
   *
   * @return   the current value of the routeCd property
   */
  public String getRouteCd()
  {
    return routeCd;
  }

  /**
   * Sets the value of the routeCd property.
   *
   * @param aRouteCd the new value of the routeCd property
   */
  public void setRouteCd(String aRouteCd)
  {
    routeCd = aRouteCd;
  }

  /**
   * Access method for the routeDescTxt property.
   *
   * @return   the current value of the routeDescTxt property
   */
  public String getRouteDescTxt()
  {
    return routeDescTxt;
  }

  /**
   * Sets the value of the routeDescTxt property.
   *
   * @param aRouteDescTxt the new value of the routeDescTxt property
   */
  public void setRouteDescTxt(String aRouteDescTxt)
  {
    routeDescTxt = aRouteDescTxt;
  }

  /**
   * Access method for the statueCd property.
   *
   * @return   the current value of the statueCd property
   */
  public String getStatueCd()
  {
    return statueCd;
  }

  /**
   * Sets the value of the statueCd property.
   *
   * @param aStatueCd the new value of the statueCd property
   */
  public void setStatueCd(String aStatueCd)
  {
    statueCd = aStatueCd;
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
   * @param objectname1
   * @param objectname2
   * @param voClass
   * @return boolean
   * @roseuid 3EAECC36024F
   */
  public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
  {
    return true;
  }

  /**
   * @param itDirty
   * @roseuid 3EAECC360398
   */
  public void setItDirty(boolean itDirty)
  {
    this.itDirty = itDirty;
  }

  /**
   * @return boolean
   * @roseuid 3EAECC3700B9
   */
  public boolean isItDirty()
  {
    return itDirty;
  }

  /**
   * @param itNew
   * @roseuid 3EAECC370175
   */
  public void setItNew(boolean itNew)
  {
    this.itNew = itNew;
  }

  /**
   * @return boolean
   * @roseuid 3EAECC37025F
   */
  public boolean isItNew()
  {
    return itNew;
  }

  /**
   * @param itDelete
   * @roseuid 3EAECC37033A
   */
  public void setItDelete(boolean itDelete)
  {
    this.itDelete = itDelete;
  }

  /**
   * @return boolean
   * @roseuid 3EAECC38005B
   */
  public boolean isItDelete()
  {
    return itDelete;
  }
}
