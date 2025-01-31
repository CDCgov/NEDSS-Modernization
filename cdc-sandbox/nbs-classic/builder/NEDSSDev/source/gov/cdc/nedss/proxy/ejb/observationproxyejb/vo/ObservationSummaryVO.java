/**
 * Title: ObservationSummaryVO class.
 * Description: A ValueObject comprising of selected DT fields to display in the Workup for
 * available Observations
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import java.sql.Timestamp;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.observation.dt.*;

import gov.cdc.nedss.systemservice.util.*;


/**
 *
 */
public class ObservationSummaryVO extends AbstractVO implements RootDTInterface
{
   private Timestamp addTime;
   private String localId;
   private String observationLocalId;
   private String obsDomainCd;
   private String cdDescTxt;
   private Long observationUid;
   private String cd;
   private String statusCd;
   private String cdDisplayFormTxt;
   private String parTypeCd;
   private String typeCd;
   private Long targetActUid;
   private String recordStatusCd;
   private boolean isTouched;
   private boolean isAssociated;
   private Character isRadioBtnAssociated;

   /**
    * from Observation.ctrlCdDisplayForm
    */
   private String ctrlCdDisplayForm;

   /**
    * from Observation.activityFromTime
    */
   private Timestamp activityFromTime;

    /**
    * from Observation.cdShortDescTxt
    */
   private String cdShortDescTxt;
      /**
   * index
   * (Not for display purposes)
   */
    private String index;
     /**
    * From Obs_value_date.from_time
    */
   private Timestamp fromTime;

   /**
    * From Obs_value_coded.code
    */
   private String morbidityReportType;

   /**
    * SRT call where code_value_general.code_set_nm = 'morb_rpt_type'
    */
   private String morbidityReportTypeSRTText;

   /**
    * Observation.cd
    */
   private String morbidityCondition;

   /**
    * from codition_code.condition_short_nm where condition_code.condition_cd =
    * ObservationSummaryVO.morbidityCondition
    */
   private String morbidityConditionSRTText;

   /**
    * Person.personUid
    */
   private Long subjectUid;

   /**
    * Observation.effectiveFromTime
    */
   private Timestamp effectiveFromTime;

   /**
    * Person.localId
    */
   private String subjectLocalId;
   /**
    * @param observationDT
    * @roseuid 3C51864200AC
    */
    private Long sourceActUid;

   /**
    * Single  argument constructor taking ObservationDT as parameter
    * @param ObservationDT observationDT
    */

   public ObservationSummaryVO(ObservationDT observationDT)
   {
        setAddTime( observationDT.getAddTime() );
		setActivityFromTime(observationDT.getActivityFromTime());
		setLocalId(observationDT.getLocalId());
        setCdDescTxt(observationDT.getCdDescTxt());
		setObsDomainCd(observationDT.getObsDomainCd());
		setCd(observationDT.getCd());
		setObservationUid(observationDT.getObservationUid());
		setRecordStatusCd(observationDT.getRecordStatusCd());
		setStatusCd(observationDT.getStatusCd());
   }

   /**
    * Default no argument constructor
    * @roseuid 3C0FA93900F4
    */
   public ObservationSummaryVO()
   {

   }

     /**
    * Access method for the index property.
    * @return   java.lang.String - the current value of the index  property
    */
   public String getIndex()
   {
      return index;
   }
 /**
    * Sets the value of the index property.
    * @param java.lang.String - aPersonUid the new value of the index property
    */
   public void setIndex(String aIndex)
   {
      index = aIndex;
   }

   /**
    * Access method for the localId property.
    * @return java.lang.String - the current value of the localId property
    */
   public String getLocalId()
   {
      return localId;
   }
   /*
    * Access method for the addTime property.
    * @return   java.sql.Timestamp - the current value of the addTime property
    */
  public Timestamp getAddTime()

   {
      return addTime;
   }

   /*
    * Sets the value of the addTime property.
    * @param java.sql.Timestamp - aAddTime the new value of the addTime property
    */
  public void setAddTime(Timestamp aAddTime)

   {
      addTime = aAddTime;
   }

      /*
    * Access method for the observationLocalID property.
    * @return   java.lang.String - the current value of the observationLocalID property
    */
  public String getObservationLocalId()

   {
      return observationLocalId;
   }
  /**
    * Sets the value of the observationLocalID property.
    * @param java.lang.String - aObservationLocalID the new value of the observationLocalID property
    */
   public void setObservationLocalId(String aObservationLocalId)
   {
      observationLocalId = aObservationLocalId;
   }

   /**
    * Sets the value of the localId property.
    * @param java.lang.String - aLocalId the new value of the localId property
    */
   public void setLocalId(String aLocalId)
   {
      localId = aLocalId;
   }

   /**
    * Access method for the obsDomainCd property.
    * @return java.lang.String - the current value of the obsDomainCd property
    */
   public String getObsDomainCd()
   {
      return obsDomainCd;
   }

   /**
    * Sets the value of the obsDomainCd property.
    * @param java.lang.String - aObsDomainCd the new value of the obsDomainCd property
    */
     public void setObsDomainCd(String aObsDomainCd)
   {
      obsDomainCd = aObsDomainCd;
   }

   /**
    * Access method for the cdDescTxt property.
    * @return   java.lang.String - the current value of the cdDescTxt property
    */
   public String getCdDescTxt()
   {
      return cdDescTxt;
   }

   /**
    * Sets the value of the cdDescTxt property.
    * @param java.lang.String - aCdDescTxt the new value of the cdDescTxt property
    */
   public void setCdDescTxt(String aCdDescTxt)
   {
      cdDescTxt = aCdDescTxt;
   }

   /**
    * Access method for the observationUid property.
    * @return   java.lang.Long - the current value of the observationUid property
    */
   public Long getObservationUid()
   {
      return observationUid;
   }

   /**
    * Sets the value of the observationUid property.
    * @param java.lang.Long - aObservationUid the new value of the observationUid property
    */
   public void setObservationUid(Long aObservationUid)
   {
      observationUid = aObservationUid;
   }

   /**
    * Access method for the cd property.
    * @return   java.lang.String - the current value of the cd property
    */
   public String getCd()
   {
      return cd;
   }

   /**
    * Sets the value of the cd property.
    * @param java.lang.String - aCd the new value of the cd property
    */
   public void setCd(String aCd)
   {
      cd = aCd;
   }

   /**
    * Access method for the statusCd property.
    * @return   java.lang.String - the current value of the statusCd property
    */
   public String getStatusCd()
   {
      return statusCd;
   }

   /**
    * Sets the value of the statusCd property.
    * @param java.lang.String - aStatusCd the new value of the statusCd property
    */
   public void setStatusCd(String aStatusCd)
   {
      statusCd = aStatusCd;
   }

    /**
    * Access method for the parTypeCd property.
    * @return   java.lang.String - the current value of parTypeCd property
    */
   public String getParTypeCd()
   {
      return parTypeCd;
   }

   /**
    * Sets the value of the parTypeCd property.
    * @param  java.lang.String - aStatusCd the new value of the parTypeCd property
    */
   public void setParTypeCd(String aParTypeCd)
   {
      parTypeCd = aParTypeCd;
   }

    /**
    * Access method for the typeCd property.
    * @return  java.lang.String - the current value of typeCd property
    */
   public String getTypeCd()
   {
      return typeCd;
   }

   /**
    * Sets the value of the typeCd property.
    * @param  java.lang.String - aStatusCd the new value of the typeCd property
    */
   public void setTypeCd(String aTypeCd)
   {
      typeCd = aTypeCd;
   }
   /**
    * Access method for the cdShortDescTxt property.
    * @return    java.lang.String - the current value of the cdShortDescTxt property
    */
   public String getCdShortDescTxt()
   {
      return cdShortDescTxt;
   }

   /**
    * Sets the value of the recordStatusCd property.
    * @param  java.lang.String - aRecordStatusCd the new value of the recordStatusCd property
    */
   public void setCdShortDescTxt(String aCdShortDescTxt)
   {
      cdShortDescTxt = aCdShortDescTxt;
   }

  /**
    * Access method for the recordStatusCd property.
    * @return java.lang.String - the current value of the recordStatusCd property
    */
   public String getRecordStatusCd()
   {
      return recordStatusCd;
   }

   /**
    * Sets the value of the recordStatusCd property.
    * @param  java.lang.String - aRecordStatusCd the new value of the recordStatusCd property
    */
   public void setRecordStatusCd(String aRecordStatusCd)
   {
      recordStatusCd = aRecordStatusCd;
   }
    /**
    * Access method for the cdDisplayFormTxt property.
    * @return  java.lang.String - the current value of the ctrlCdDisplayForm property
    */
   public String getCdDisplayFormTxt()
   {
      return cdDisplayFormTxt;
   }

   /**
    * Sets the value of the cdDisplayFormTxt property.
    * @param  java.lang.String - acdDisplayFormTxt the new value of the ctrlCdDisplayForm property
    */
   public void setCdDisplayFormTxt(String aCdDisplayFormTxt)
   {
      cdDisplayFormTxt = aCdDisplayFormTxt;
   }

    /**
    * Access method for the ctrlCdDisplayForm property.
    * @return  java.lang.String - the current value of the ctrlCdDisplayForm property
    */
   public String getCtrlCdDisplayForm()
   {
      return ctrlCdDisplayForm;
   }
   /**
    * Sets the value of the ctrlCdDisplayForm property.
    * @param  java.lang.String - aCtrlCdDisplayForm the new value of the ctrlCdDisplayForm property
    */
   public void setCtrlCdDisplayForm(String aCtrlCdDisplayForm)
   {
      ctrlCdDisplayForm = aCtrlCdDisplayForm;
   }
   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C0FA9390108
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * Sets the dirty flag by taking a boolean parameter
    * @roseuid 3C0FA939011D
    *  @param boolean - itDirty
    */
   public void setItDirty(boolean itDirty)
   {
        this.itDirty = itDirty;
   }

   /**
    * Checks and returns boolean for a dirty Object
    * @roseuid 3C0FA9390130
    * @return boolean
    */
   public boolean isItDirty()
   {
    return this.itDirty;
   }

   /**
    * Sets the passed value to true
    * @roseuid 3C0FA939013A
    * @param boolean - itNew
    */
   public void setItNew(boolean itNew)
   {
        this.itNew = itNew;
   }

   /**
    * Checks and returns boolean for a new Object
    * @roseuid 3C0FA9390145
    * @return boolean
    */
   public boolean isItNew()
   {
    return this.itNew;
   }

   /**
    * Sets the passed value to true
    * @roseuid 3C0FA939014E
    * @param itDelete
    */
   public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }

   /**
    * Checks and returns boolean for a delete Object
    * @roseuid 3C0FA9390162
    * @return boolean
    */
   public boolean isItDelete()
   {
    return this.itDelete;
   }

   /**
    * Access method for the activityFromTime property.
    * @roseuid 3C51864203BA
    * @return   java.sql.TimeStamp - the current value of the activityFromTime property
    */
   public Timestamp getActivityFromTime()
   {
      return activityFromTime;
   }

   /**
    * Sets the value of the activityFromTime property.
    * @param java.sql.TimeStamp - aActivityFromTime the new value of the activityFromTime property
    * @roseuid 3C518643004A
    */
   public void setActivityFromTime(java.sql.Timestamp aActivityFromTime)
   {
       activityFromTime = aActivityFromTime;
   }

   /**
    * Checks whether the Object is associated by taking a boolean param
    * @param boolean
    */
   public void setIsAssociated(boolean flag)
   {
       isAssociated=flag;
   }

   /**
    * Gets  the associated Object and returns a boolean
    * @return boolean
    */

  public boolean getIsAssociated()
   {
      return isAssociated;
   }
   /**
    * logically same as set/getIsAssocaited
    * but character used to be able to sort VO arrays.
    * @param boolean
    */

   public void setIsRadioBtnAssociated(boolean flag) {
	   if( flag == true )
	      isRadioBtnAssociated = new Character('F');
	   else
	      isRadioBtnAssociated = new Character('T');
   }

   /**
    * logically same as set/getIsRadioBtnAssociated
    * but character used as return type to be able to sort VO arrays.
    * @return Character
    */

   public Character getIsRadioBtnAssociated() {
	   return isRadioBtnAssociated;
   }


   /**
    * Access method for the subjectUid property.
    * @return   java.lang.Long - the current value of the subjectUid property
    */
   public Long getSubjectUid()
   {
      return subjectUid;
   }

   /**
    * Sets the value of the subjectUid property.
    * @param java.lang.Long - aSubjectUid the new value of the subjectUid property
    */
   public void setSubjectUid(Long aSubjectUid)
   {
      subjectUid = aSubjectUid;
   }

   /**
    * Access method for the effectiveFromTime property.
    * @return java.sql.Timestamp - the current value of the effectiveFromTime property
    */
   public Timestamp getEffectiveFromTime()
   {
      return effectiveFromTime;
   }

   /**
    * Sets the value of the effectiveFromTime property.
    * @param java.sql.Timestamp - aEffectiveFromTime the new value of the effectiveFromTime property
    */
   public void setEffectiveFromTime(Timestamp aEffectiveFromTime)
   {
      effectiveFromTime = aEffectiveFromTime;
   }


   /**
    * Access method for the subjectLocalId property.
    * @return  java.lang.String - the current value of the subjectLocalId property
    */
   public String getSubjectLocalId()
   {
      return subjectLocalId;
   }

   /**
    * Sets the value of the subjectLocalId property.
    * @param  java.lang.String - aSubjectLocalId the new value of the subjectLocalId property
    */
   public void setSubjectLocalId(String aSubjectLocalId)
   {
      subjectLocalId = aSubjectLocalId;
   }

  /**
   * setIsTouched sets the boolean parameter
   * @param boolean
   */
   public void setIsTouched(boolean flag)
   {
       isTouched=flag;
   }

  /**
   * getIsTouched returns the boolean parameter
   * @return boolean
   */
   public boolean getIsTouched()
   {
      return isTouched;
   }

  /**
   * getTargetActUid returns the Long parameter
   * @return java.lang.Long
   */
   public Long getTargetActUid()
   {
      return targetActUid;
   }

   /**
    * Sets the value of the targetActUid property.
    * @param java.lang.Long - aTargetActUid the new value of the targetActUid property
    */
   public void setTargetActUid(Long aTargetActUid)
   {
      targetActUid = aTargetActUid;
   }

    /**
    * Access method for the fromTime property.
    * @return java.sql.Timestamp - the current value of the fromTime property
    */
   public Timestamp getFromTime()
   {
      return fromTime;
   }

   /**
    * Sets the value of the fromTime property.
    * @param java.sql.Timestamp - aFromTime the new value of the fromTime property
    */
   public void setFromTime(Timestamp aFromTime)
   {
      fromTime = aFromTime;
   }

   /**
    * Access method for the morbidityReportType property.
    * @return java.lang.String - the current value of the morbidityReportType property
    */
   public String getMorbidityReportType()
   {
      return morbidityReportType;
   }

   /**
    * Sets the value of the morbidityReportType property.
    * @param java.lang.String - aMorbidityReportType the new value of the morbidityReportType property
    */
   public void setMorbidityReportType(String aMorbidityReportType)
   {
      morbidityReportType = aMorbidityReportType;
   }

   /**
    * Access method for the morbidityReportTypeSRTText property.
    * @return  java.lang.String - the current value of the morbidityReportTypeSRTText property
    */
   public String getMorbidityReportTypeSRTText()
   {
      return morbidityReportTypeSRTText;
   }

   /**
    * Sets the value of the morbidityReportTypeSRTText property.
    * @param java.lang.String - aMorbidityReportTypeSRTText the new value of the morbidityReportTypeSRTText property
    */
   public void setMorbidityReportTypeSRTText(String aMorbidityReportTypeSRTText)
   {
      morbidityReportTypeSRTText = aMorbidityReportTypeSRTText;
   }

   /**
    * Access method for the morbidityCondition property.
    * @return java.lang.String - the current value of the morbidityCondition property
    */
   public String getMorbidityCondition()
   {
      return morbidityCondition;
   }

   /**
    * Sets the value of the morbidityCondition property.
    * @param java.lang.String - aMorbidityCondition the new value of the morbidityCondition property
    */
   public void setMorbidityCondition(String aMorbidityCondition)
   {
      morbidityCondition = aMorbidityCondition;
   }

   /**
    * Access method for the morbidityConditionSRTText property.
    * @return java.lang.String - the current value of the morbidityConditionSRTText property
    */
   public String getMorbidityConditionSRTText()
   {
      return morbidityConditionSRTText;
   }

   /**
    * Sets the value of the morbidityConditionSRTText property.
    * @param java.lang.String - aMorbidityConditionSRTText the new value of the morbidityConditionSRTText property
    */
   public void setMorbidityConditionSRTText(String aMorbidityConditionSRTText)
   {
      morbidityConditionSRTText = aMorbidityConditionSRTText;
   }

    /**
    * Access method for the sourceActUid property.
    * @return java.lang.Long - the current value of the sourceActUid property
    */
   public Long getSourceActUid()
   {
      return sourceActUid;
   }

   /**
    * Sets the value of the sourceActUid property.
    * @param java.lang.Long- aSourceActUid the new value of the sourceActUid property
    */
   public void setSourceActUid(Long aSourceActUid)
   {
      sourceActUid = aSourceActUid;
   }
   /**
* @return java.lang.Long
*/
public Long getLastChgUserId()
{
return null;
}

/**
* @param aLastChgUserId
*/
public void setLastChgUserId(Long aLastChgUserId)
{

}

/**
* @return java.lang.String
*/
public String getJurisdictionCd()
{
return null;
}

/**
* @param aJurisdictionCd
*/
public void setJurisdictionCd(String aJurisdictionCd)
{

}

/**
* @return java.lang.String
*/
public String getProgAreaCd()
{
return null;
}

/**
* @param aProgAreaCd
*/
public void setProgAreaCd(String aProgAreaCd)
{
}


/**
* @return java.lang.Long
*/
public Long getAddUserId()
{
return null;
}

/**
* @param aAddUserId
*/
public void setAddUserId(Long aAddUserId)
{

}

/**
* @return java.lang.String
*/
public String getLastChgReasonCd()
{
return null;
}

/**
* @param aLastChgReasonCd
*/
public void setLastChgReasonCd(String aLastChgReasonCd)
{

}


/**
* @return java.sql.Timestamp
*/
public Timestamp getRecordStatusTime()
{
return null;
}

/**
* @param aRecordStatusTime
*/
public void setRecordStatusTime(Timestamp aRecordStatusTime)
{

}


/**
* @return java.sql.Timestamp
*/
public Timestamp getStatusTime()
{
return null;
}

/**
* @param aStatusTime
*/
public void setStatusTime(Timestamp aStatusTime)
{

}

/**
* @return java.lang.String
*/
public String getSuperclass()
{
return null;
}

/**
* @return java.lang.Long
*/
public Long getUid()
{
return null;
}
public void setUid(Long aUid)
{
}
/**
* @return java.lang.Long
*/
public Long getProgramJurisdictionOid()
{
return null;
}

/**
* @param aProgramJurisdictionOid
*/
public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
{

}

/**
* @return java.lang.String
*/
public String getSharedInd()
{
return null;
}

/**
* @param aSharedInd
*/
public void setSharedInd(String aSharedInd)
{

}

/**
*/
public Integer getVersionCtrlNbr()
{
return null;
}
 /**
* Access method for the lastChgTime property.
*
* @return   the current value of the lastChgTime property
*/
public Timestamp getLastChgTime()
{
return null;
}

/**
* Sets the value of the lastChgTime property.
*
* @param aLastChgTime the new value of the lastChgTime property
*/
public void setLastChgTime(Timestamp aLastChgTime)
{

}

}
