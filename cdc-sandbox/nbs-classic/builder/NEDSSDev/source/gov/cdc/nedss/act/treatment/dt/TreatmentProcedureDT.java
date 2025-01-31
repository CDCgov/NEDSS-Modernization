//Source file: C:\\RationalRoseDevelopment\\gov\\cdc\\nedss\\act\\treatment\\dt\\TreatmentProcedureDT.java

package gov.cdc.nedss.act.treatment.dt;

import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.StringUtils;
import java.sql.Timestamp;

public class TreatmentProcedureDT extends AbstractVO
{
   private static final long serialVersionUID = 1L;
   private Long treatmentUid;
   private Integer treatmentProcedureSeq;
   private String approachSiteCd;
   private String approachSiteDescTxt;
   private String cd;
   private String cdDescTxt;
   private String cdSystemCd;
   private String cdSystemDescTxt;
   private String cdVersion;
   private String effectiveDurationAmt;
   private String effectiveDurationUnitCd;
   private Timestamp effectiveFromTime;
   private Timestamp effectiveToTime;
   private String statusCd;
   private Timestamp statusTime;

   /**
    * @roseuid 3E9AE7AB005D
    */
   public TreatmentProcedureDT()
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
    * Access method for the treatmentProcedureSeq property.
    *
    * @return   the current value of the treatmentProcedureSeq property
    */
   public Integer getTreatmentProcedureSeq()
   {
      return treatmentProcedureSeq;
   }

   /**
    * Sets the value of the treatmentProcedureSeq property.
    *
    * @param aTreatmentProcedureSeq the new value of the treatmentProcedureSeq property
    */
   public void setTreatmentProcedureSeq(Integer aTreatmentProcedureSeq)
   {
      treatmentProcedureSeq = aTreatmentProcedureSeq;
   }

   /**
    * Access method for the approachSiteCd property.
    *
    * @return   the current value of the approachSiteCd property
    */
   public String getApproachSiteCd()
   {
      return approachSiteCd;
   }

   /**
    * Sets the value of the approachSiteCd property.
    *
    * @param aApproachSiteCd the new value of the approachSiteCd property
    */
   public void setApproachSiteCd(String aApproachSiteCd)
   {
      approachSiteCd = aApproachSiteCd;
   }

   /**
    * Access method for the approachSiteDescTxt property.
    *
    * @return   the current value of the approachSiteDescTxt property
    */
   public String getApproachSiteDescTxt()
   {
      return approachSiteDescTxt;
   }

   /**
    * Sets the value of the approachSiteDescTxt property.
    *
    * @param aApproachSiteDescTxt the new value of the approachSiteDescTxt property
    */
   public void setApproachSiteDescTxt(String aApproachSiteDescTxt)
   {
      approachSiteDescTxt = aApproachSiteDescTxt;
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
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3EAECC290230
    */
   public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3EAECC2A005B
    */
   public void setItDirty(boolean itDirty)
   {
     this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * @roseuid 3EAECC2A01B3
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * @param itNew
    * @roseuid 3EAECC2A0240
    */
   public void setItNew(boolean itNew)
   {
     this.itNew = itNew;
   }

   /**
    * @return boolean
    * @roseuid 3EAECC2A0369
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * @param itDelete
    * @roseuid 3EAECC2B002D
    */
   public void setItDelete(boolean itDelete)
   {
     this.itDelete = itDelete;
   }

   /**
    * @return boolean
    * @roseuid 3EAECC2B0155
    */
   public boolean isItDelete()
   {
    return itDelete;
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

}
