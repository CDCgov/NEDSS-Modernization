

package gov.cdc.nedss.act.observation.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

  /**
    * Title:            ObservationHistDT is a class
    * Description:	This is a class which sets/gets(retrieves)
    *                   all the fields to the database table
    * Copyright:	Copyright (c) 2001
    * Company: 	        Computer Sciences Corporation
    * @author           NEDSS TEAM
    * @version	        1.0
    */

public class ObservationHistDT extends AbstractVO
{
	
	private static final long serialVersionUID = 1L;

    private Long observationUid;

    private Integer versionCtrlNbr;

    private String activityDurationAmt;

    private String activityDurationUnitCd;

    private Timestamp activityFromTime;

    private Timestamp activityToTime;

    private String addReasonCd;

    private Timestamp addTime;

    private Long addUserId;
    
    private String altCd;
    
    private String altCdDescTxt;
    
    private String altCdSystemCd;
    
    private String altCdSystemDescTxt;
    
    private String cd;

	private String cdDerivedInd;
	
    private String cdDescTxt;

    private String cdSystemCd;

    private String cdSystemDescTxt;

    private String confidentialityCd;

    private String confidentialityDescTxt;

    private String ctrlCdDisplayForm;

    private String ctrlCdUserDefined1;

    private String ctrlCdUserDefined2;

    private String ctrlCdUserDefined3;

    private String ctrlCdUserDefined4;

    private Integer derivationExp;

    private String effectiveDurationAmt;

    private String effectiveDurationUnitCd;

    private Timestamp effectiveFromTime;

    private Timestamp effectiveToTime;

    private String electronicInd;

    private String groupLevelCd;

    private String jurisdictionCd;

    private String lastChgReasonCd;

    private Timestamp lastChgTime;

    private Long lastChgUserId;

    private String labConditionCd;

    private String localId;

    private String obsDomainCd;

    private String obsDomainCdSt1;

    private String pnuCd;

    private String priorityCd;

    private String priorityDescTxt;

    private String progAreaCd;

    private String recordStatusCd;

    private Timestamp recordStatusTime;

    private Integer repeatNbr;
    
    private Timestamp rptToStateTime;

    private String statusCd;

    private Timestamp statusTime;

    private Long subjectPersonUid;

    private String targetSiteCd;

    private String targetSiteDescTxt;

    private String txt;

    private String userAffiliationTxt;

    private String valueCd;

    private String ynuCd;

    private Long programJurisdictionOid;

    private String sharedInd;

    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;

    /**
    * Access method for the ObservationUid.
    *
    * @return  Long the current value of the ObservationUid
    */
    public Long getObservationUid()
    {
        return observationUid;
    }

    /**
    * Sets the value of the ObservationUid.
    *
    * @param Long the aObservationUid
    */
    public void setObservationUid(Long aObservationUid)
    {
        observationUid = aObservationUid;
        setItDirty(true);
    }

     /**
    * Access method for the VersionCtrlNbr.
    *
    * @return  Integer the current value of the VersionCtrlNbr
    */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
    * Sets the value of the VersionCtrlNbr.
    *
    * @param Integer the aVersionCtrlNbr
    */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

     /**
    * Access method for the ActivityDurationAmt.
    *
    * @return  String the current value of the ActivityDurationAmt
    */
    public String getActivityDurationAmt()
    {
        return activityDurationAmt;
    }

    /**
    * Sets the value of the ActivityDurationAmt.
    *
    * @param String the aActivityDurationAmt
    */
    public void setActivityDurationAmt(String aActivityDurationAmt)
    {
        activityDurationAmt = aActivityDurationAmt;
        setItDirty(true);
    }

     /**
    * Access method for the ActivityDurationUnitCd.
    *
    * @return  String the current value of the ActivityDurationUnitCd
    */
    public String getActivityDurationUnitCd()
    {
        return activityDurationUnitCd;
    }

    /**
    * Sets the value of the ActivityDurationUnitCd.
    *
    * @param String the aActivityDurationUnitCd
    */
    public void setActivityDurationUnitCd(String aActivityDurationUnitCd)
    {
        activityDurationUnitCd = aActivityDurationUnitCd;
        setItDirty(true);
    }

     /**
    * Access method for the ActivityFromTime.
    *
    * @return  Timestamp the current value of the ActivityFromTime
    */
    public Timestamp getActivityFromTime()
    {
        return activityFromTime;
    }

    /**
    * Sets the value of the ActivityFromTime.
    *
    * @param Timestamp the aActivityFromTime
    */
    public void setActivityFromTime(Timestamp aActivityFromTime)
    {
        activityFromTime = aActivityFromTime;
        setItDirty(true);
    }

    /**
    * Sets the value of the ActivityFromTime.
    *
    * @param String the aActivityFromTime
    */
   public void setActivityFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setActivityFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    * Access method for the ActivityToTime.
    *
    * @return  Timestamp the current value of the ActivityToTime
    */
    public Timestamp getActivityToTime()
    {
        return activityToTime;
    }

    /**
    * Sets the value of the ActivityToTime.
    *
    * @param Timestamp the aActivityToTime
    */
    public void setActivityToTime(Timestamp aActivityToTime)
    {
        activityToTime = aActivityToTime;
        setItDirty(true);
    }

    /**
    * Sets the value of the ActivityToTime_s.
    *
    * @param String the strTime
    */
   public void setActivityToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setActivityToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    * Access method for the AddReasonCd.
    *
    * @return  String the current value of the AddReasonCd
    */
    public String getAddReasonCd()
    {
        return addReasonCd;
    }

    /**
    * Sets the value of the AddReasonCd.
    *
    * @param String the aAddReasonCd
    */
    public void setAddReasonCd(String aAddReasonCd)
    {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

     /**
    * Access method for the AddTime.
    *
    * @return  Timestamp the current value of the AddTime
    */
    public Timestamp getAddTime()
    {
        return addTime;
    }

    /**
    * Sets the value of the AddTime.
    *
    * @param Timestamp the aAddTime
    */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
    * Sets the value of the AddTime_s.
    *
    * @param String the strTime
    */
   public void setAddTime_s(String strTime)
   {
      if (strTime == null) return;
            this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    * Access method for the AddUserId.
    *
    * @return  Long the current value of the AddUserId
    */
    public Long getAddUserId()
    {
        return addUserId;
    }

    /**
    * Sets the value of the AddUserId.
    *
    * @param Long the aAddUserId
    */
    public void setAddUserId(Long aAddUserId)
    {
        addUserId = aAddUserId;
        setItDirty(true);
    }

	/**
	 * gets AltCd
	 * @return String 
	 */
	public String getAltCd(){
		return this.altCd;
	}

	/**
	 * sets AltCd
	 * @param aAltCd
	 */
	public void setAltCd(String aAltCd){
		this.altCd = aAltCd;
		setItDirty(true);
	}

	/**
	 * get AltCdDescTxt
	 * @return
	 */
	public String getAltCdDescTxt(){
		return this.altCdDescTxt;
	}
	
	/**
	 * sets AltCdDescTxt
	 * @param aAltCdDescTxt
	 */
	public void setAltCdDescTxt(String aAltCdDescTxt){
		this.altCdDescTxt = aAltCdDescTxt;
		setItDirty(true);
	}
	

	/**
	 * gets AltCdSystemCd
	 * @return String
	 */
	public String getAltCdSystemCd(){
		return this.altCdSystemCd;
	}
	
	/**
	 * sets AltCdSystemCd
	 * @param aAltCdSystemCd
	 */
	public void setAltCdSystemCd(String aAltCdSystemCd){
		this.altCdSystemCd = aAltCdSystemCd;
		setItDirty(true);
	}
	
	/**
	 * gets AltCdSystemDescTxt
	 * @return String
	 */
	public String getAltCdSystemDescTxt(){
		return this.altCdSystemDescTxt;
	}
	
	/**
	 * sets AltCdSystemDescTxt
	 * @param aAltCdSystemDescTxt
	 */
	public void setAltCdSystemDescTxt(String aAltCdSystemDescTxt){
		this.altCdSystemDescTxt = aAltCdSystemDescTxt;
		setItDirty(true);
	}

    /**
    * Access method for the Cd.
    *
    * @return  String the current value of the Cd
    */
    public String getCd()
    {
        return cd;
    }

    /**
    * Sets the value of the Cd.
    *
    * @param String the aCd
    */
    public void setCd(String aCd)
    {
        cd = aCd;
        setItDirty(true);
    }

	/**
	 * gets CdDerivedInd
	 * @return String 
	 */
	public String getCdDerivedInd(){
		return this.cdDerivedInd;
	}

	/**
	 * set CdDerivedind
	 * @param aCdDerivedInd
	 */
	public void setCdDerivedInd(String aCdDerivedInd){
		this.cdDerivedInd = aCdDerivedInd;
		setItDirty(true);
	}

     /**
    * Access method for the CdDescTxt.
    *
    * @return  String the current value of the CdDescTxt
    */
    public String getCdDescTxt()
    {
        return cdDescTxt;
    }

    /**
    * Sets the value of the CdDescTxt.
    *
    * @param String the aCdDescTxt
    */
    public void setCdDescTxt(String aCdDescTxt)
    {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

     /**
    * Access method for the CdSystemCd.
    *
    * @return  String the current value of the CdSystemCd
    */
    public String getCdSystemCd()
    {
        return cdSystemCd;
    }

    /**
    * Sets the value of the CdSystemCd.
    *
    * @param String the aCdSystemCd
    */
    public void setCdSystemCd(String aCdSystemCd)
    {
        cdSystemCd = aCdSystemCd;
        setItDirty(true);
    }

     /**
    * Access method for the CdSystemDescTxt.
    *
    * @return  String the current value of the CdSystemDescTxt
    */
    public String getCdSystemDescTxt()
    {
        return cdSystemDescTxt;
    }

    /**
    * Sets the value of the CdSystemDescTxt.
    *
    * @param String the aCdSystemDescTxt
    */
    public void setCdSystemDescTxt(String aCdSystemDescTxt)
    {
        cdSystemDescTxt = aCdSystemDescTxt;
        setItDirty(true);
    }

     /**
    * Access method for the ConfidentialityCd.
    *
    * @return  String the current value of the ConfidentialityCd
    */
    public String getConfidentialityCd()
    {
        return confidentialityCd;
    }

    /**
    * Sets the value of the ConfidentialityCd.
    *
    * @param String the aConfidentialityCd
    */
    public void setConfidentialityCd(String aConfidentialityCd)
    {
        confidentialityCd = aConfidentialityCd;
        setItDirty(true);
    }

     /**
    * Access method for the ConfidentialityDescTxt.
    *
    * @return  String the current value of the ConfidentialityDescTxt
    */
    public String getConfidentialityDescTxt()
    {
        return confidentialityDescTxt;
    }

    /**
    * Sets the value of the ConfidentialityDescTxt.
    *
    * @param String the aConfidentialityDescTxt
    */
    public void setConfidentialityDescTxt(String aConfidentialityDescTxt)
    {
        confidentialityDescTxt = aConfidentialityDescTxt;
        setItDirty(true);
    }

     /**
    * Access method for the CtrlCdDisplayForm.
    *
    * @return  String the current value of the CtrlCdDisplayForm
    */
    public String getCtrlCdDisplayForm()
    {
        return ctrlCdDisplayForm;
    }

    /**
    * Sets the value of the SharedInd.
    *
    * @param String the aSharedInd
    */
    public void setCtrlCdDisplayForm(String aCtrlCdDisplayForm)
    {
        ctrlCdDisplayForm = aCtrlCdDisplayForm;
        setItDirty(true);
    }

     /**
    * Access method for the CtrlCdDisplayForm.
    *
    * @return  String the current value of the CtrlCdDisplayForm
    */
    public String getCtrlCdUserDefined1()
    {
        return ctrlCdUserDefined1;
    }

    /**
    * Sets the value of the CtrlCdUserDefined1.
    *
    * @param String the aCtrlCdUserDefined1
    */
    public void setCtrlCdUserDefined1(String aCtrlCdUserDefined1)
    {
        ctrlCdUserDefined1 = aCtrlCdUserDefined1;
        setItDirty(true);
    }

     /**
    * Access method for the CtrlCdUserDefined2.
    *
    * @return  String the current value of the CtrlCdUserDefined2
    */
    public String getCtrlCdUserDefined2()
    {
        return ctrlCdUserDefined2;
    }

    /**
    * Sets the value of the CtrlCdUserDefined2.
    *
    * @param String the aCtrlCdUserDefined2
    */
    public void setCtrlCdUserDefined2(String aCtrlCdUserDefined2)
    {
        ctrlCdUserDefined2 = aCtrlCdUserDefined2;
        setItDirty(true);
    }

     /**
    * Access method for the CtrlCdUserDefined3.
    *
    * @return  String the current value of the CtrlCdUserDefined3
    */
    public String getCtrlCdUserDefined3()
    {
        return ctrlCdUserDefined3;
    }

    /**
    * Sets the value of the CtrlCdUserDefined3.
    *
    * @param String the aCtrlCdUserDefined3
    */
    public void setCtrlCdUserDefined3(String aCtrlCdUserDefined3)
    {
        ctrlCdUserDefined3 = aCtrlCdUserDefined3;
        setItDirty(true);
    }


     /**
    * Access method for the CtrlCdUserDefined4.
    *
    * @return  String the current value of the CtrlCdUserDefined4
    */
    public String getCtrlCdUserDefined4()
    {
        return ctrlCdUserDefined4;
    }

    /**
    * Sets the value of the CtrlCdUserDefined4.
    *
    * @param String the CtrlCdUserDefined4
    */
    public void setCtrlCdUserDefined4(String aCtrlCdUserDefined4)
    {
        ctrlCdUserDefined4 = aCtrlCdUserDefined4;
        setItDirty(true);
    }


     /**
    * Access method for the DerivationExp.
    *
    * @return  Integer the current value of the DerivationExp
    */
    public Integer getDerivationExp()
    {
        return derivationExp;
    }

    /**
    * Sets the value of the DerivationExp.
    *
    * @param Integer the aDerivationExp
    */
    public void setDerivationExp(Integer aDerivationExp)
    {
        derivationExp = aDerivationExp;
        setItDirty(true);
    }

     /**
    * Access method for the EffectiveDurationAmt.
    *
    * @return  String the current value of the EffectiveDurationAmt
    */
    public String getEffectiveDurationAmt()
    {
        return effectiveDurationAmt;
    }

    /**
    * Sets the value of the EffectiveDurationAmt.
    *
    * @param String the aEffectiveDurationAmt
    */
    public void setEffectiveDurationAmt(String aEffectiveDurationAmt)
    {
        effectiveDurationAmt = aEffectiveDurationAmt;
        setItDirty(true);
    }

     /**
    * Access method for the EffectiveDurationUnitCd.
    *
    * @return  String the current value of the EffectiveDurationUnitCd
    */
    public String getEffectiveDurationUnitCd()
    {
        return effectiveDurationUnitCd;
    }

    /**
    * Sets the value of the EffectiveDurationUnitCd.
    *
    * @param String the aEffectiveDurationUnitCd
    */
    public void setEffectiveDurationUnitCd(String aEffectiveDurationUnitCd)
    {
        effectiveDurationUnitCd = aEffectiveDurationUnitCd;
        setItDirty(true);
    }

     /**
    * Access method for the EffectiveFromTime.
    *
    * @return  Timestamp the current value of the EffectiveFromTime
    */
    public Timestamp getEffectiveFromTime()
    {
        return effectiveFromTime;
    }

    /**
    * Sets the value of the EffectiveFromTime.
    *
    * @param Timestamp the aEffectiveFromTime
    */
    public void setEffectiveFromTime(Timestamp aEffectiveFromTime)
    {
        effectiveFromTime = aEffectiveFromTime;
        setItDirty(true);
    }

    /**
    * Sets the value of the EffectiveFromTime_s.
    *
    * @param String the strTime
    */
   public void setEffectiveFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    * Access method for the EffectiveToTime.
    *
    * @return  Timestamp the current value of the EffectiveToTime
    */
    public Timestamp getEffectiveToTime()
    {
        return effectiveToTime;
    }

    /**
    * Sets the value of the EffectiveToTime.
    *
    * @param Timestamp the aEffectiveToTime
    */
    public void setEffectiveToTime(Timestamp aEffectiveToTime)
    {
        effectiveToTime = aEffectiveToTime;
        setItDirty(true);
    }

    /**
    * Sets the value of the EffectiveToTime_s.
    *
    * @param String the strTime
    */
   public void setEffectiveToTime_s(String strTime)
   {
      if (strTime == null) return;
            this.setEffectiveToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    * Access method for the ElectronicInd.
    *
    * @return  String the current value of the ElectronicInd
    */
    public String getElectronicInd()
    {
        return electronicInd;
    }

    /**
    * Sets the value of the ElectronicInd.
    *
    * @param String the aElectronicInd
    */
    public void setElectronicInd(String aElectronicInd)
    {
        electronicInd = aElectronicInd;
        setItDirty(true);
    }

     /**
    * Access method for the GroupLevelCd.
    *
    * @return  String the current value of the GroupLevelCd
    */
    public String getGroupLevelCd()
    {
        return groupLevelCd;
    }

    /**
    * Sets the value of the GroupLevelCd.
    *
    * @param String the aGroupLevelCd
    */
    public void setGroupLevelCd(String aGroupLevelCd)
    {
        groupLevelCd = aGroupLevelCd;
        setItDirty(true);
    }

     /**
    * Access method for the JurisdictionCd.
    *
    * @return  String the current value of the JurisdictionCd
    */
    public String getJurisdictionCd()
    {
        return jurisdictionCd;
    }

    /**
    * Sets the value of the JurisdictionCd.
    *
    * @param String the aJurisdictionCd
    */
    public void setJurisdictionCd(String aJurisdictionCd)
    {
        jurisdictionCd = aJurisdictionCd;
        setItDirty(true);
    }

     /**
    * Access method for the LastChgReasonCd.
    *
    * @return  String the current value of the LastChgReasonCd
    */
    public String getLastChgReasonCd()
    {
        return lastChgReasonCd;
    }

    /**
    * Sets the value of the LastChgReasonCd.
    *
    * @param String the aLastChgReasonCd
    */
    public void setLastChgReasonCd(String aLastChgReasonCd)
    {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

     /**
    * Access method for the LastChgTime.
    *
    * @return  Timestamp the current value of the LastChgTime
    */
    public Timestamp getLastChgTime()
    {
        return lastChgTime;
    }

    /**
    * Sets the value of the LastChgTime.
    *
    * @param Timestamp the aLastChgTime
    */
    public void setLastChgTime(Timestamp aLastChgTime)
    {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
    * Sets the value of the LastChgTime_s.
    *
    * @param String the strTime
    */
   public void setLastChgTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    * Access method for the LastChgUserId.
    *
    * @return  String the current value of the LastChgUserId
    */
    public Long getLastChgUserId()
    {
        return lastChgUserId;
    }

    /**
    * Sets the value of the LastChgUserId.
    *
    * @param String the aLastChgUserId
    */
    public void setLastChgUserId(Long aLastChgUserId)
    {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

     /**
    * Access method for the LabConditionCd.
    *
    * @return  String the current value of the LabConditionCd
    */
    public String getLabConditionCd()
    {
        return labConditionCd;
    }

    /**
    * Sets the value of the LabConditionCd.
    *
    * @param String the aLabConditionCd
    */
    public void setLabConditionCd(String aLabConditionCd)
    {
        labConditionCd = aLabConditionCd;
        setItDirty(true);
    }

     /**
    * Access method for the LocalId.
    *
    * @return  String the current value of the LocalId
    */
    public String getLocalId()
    {
        return localId;
    }

    /**
    * Sets the value of the LocalId.
    *
    * @param String the aLocalId
    */
    public void setLocalId(String aLocalId)
    {
        localId = aLocalId;
        setItDirty(true);
    }

     /**
    * Access method for the ObsDomainCd.
    *
    * @return  String the current value of the ObsDomainCd
    */
    public String getObsDomainCd()
    {
        return obsDomainCd;
    }

    /**
    * Sets the value of the ObsDomainCd.
    *
    * @param String the aObsDomainCd
    */
    public void setObsDomainCd(String aObsDomainCd)
    {
        obsDomainCd = aObsDomainCd;
        setItDirty(true);
    }

     /**
    * Access method for the ObsDomainCdSt1.
    *
    * @return  String the current value of the ObsDomainCdSt1
    */
    public String getObsDomainCdSt1()
    {
        return obsDomainCdSt1;
    }

    /**
    * Sets the value of the ObsDomainCdSt1.
    *
    * @param String the aObsDomainCdSt1
    */
    public void setObsDomainCdSt1(String aObsDomainCdSt1)
    {
        obsDomainCdSt1 = aObsDomainCdSt1;
        setItDirty(true);
    }

     /**
    * Access method for the PnuCd.
    *
    * @return  String the current value of the PnuCd
    */
    public String getPnuCd()
    {
        return pnuCd;
    }
    /**
    * Sets the value of the PnuCd.
    *
    * @param String the aPnuCd
    */
    public void setPnuCd(String aPnuCd)
    {
        pnuCd = aPnuCd;
        setItDirty(true);
    }

     /**
    * Access method for the PriorityCd.
    *
    * @return  String the current value of the PriorityCd
    */
    public String getPriorityCd()
    {
        return priorityCd;
    }

    /**
    * Sets the value of the PriorityCd.
    *
    * @param String the aPriorityCd
    */
    public void setPriorityCd(String aPriorityCd)
    {
        priorityCd = aPriorityCd;
        setItDirty(true);
    }

     /**
    * Access method for the PriorityDescTxt.
    *
    * @return  String the current value of the PriorityDescTxt
    */
    public String getPriorityDescTxt()
    {
        return priorityDescTxt;
    }

    /**
    * Sets the value of the PriorityDescTxt.
    *
    * @param String the aPriorityDescTxt
    */
    public void setPriorityDescTxt(String aPriorityDescTxt)
    {
        priorityDescTxt = aPriorityDescTxt;
        setItDirty(true);
    }

     /**
    * Access method for the ProgAreaCd.
    *
    * @return  String the current value of the ProgAreaCd
    */
    public String getProgAreaCd()
    {
        return progAreaCd;
    }

    /**
    * Sets the value of the ProgAreaCd.
    *
    * @param String the aProgAreaCd
    */
    public void setProgAreaCd(String aProgAreaCd)
    {
        progAreaCd = aProgAreaCd;
        setItDirty(true);
    }

     /**
    * Access method for the RecordStatusCd.
    *
    * @return  String the current value of the RecordStatusCd
    */
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }

    /**
    * Sets the value of the RecordStatusCd.
    *
    * @param String the aRecordStatusCd
    */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

     /**
    * Access method for the RecordStatusTime.
    *
    * @return  Timestamp the current value of the RecordStatusTime
    */
    public Timestamp getRecordStatusTime()
    {
        return recordStatusTime;
    }

    /**
    * Sets the value of the RecordStatusTime.
    *
    * @param Timestamp the aRecordStatusTime
    */
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

    /**
    * Sets the value of the RecordStatusTime_s.
    *
    * @param String the strTime
    */
   public void setRecordStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    * Access method for the RepeatNbr.
    *
    * @return  Integer the current value of the RepeatNbr
    */
    public Integer getRepeatNbr()
    {
        return repeatNbr;
    }

    /**
    * Sets the value of the RepeatNbr.
    *
    * @param Integer the aRepeatNbr
    */
    public void setRepeatNbr(Integer aRepeatNbr)
    {
        repeatNbr = aRepeatNbr;
        setItDirty(true);
    }

	/**
	 * gets RptToStateTime
	 * @return Timestamp
	 */
	public Timestamp getRptToStateTime(){
		return this.rptToStateTime;
	}


	/**
	 * sets RptToStateTime
	 * @param aRptToStateTime
	 */
	public void setRptToStateTime(Timestamp aRptToStateTime){
		this.rptToStateTime = aRptToStateTime;
		setItDirty(true);
	}

     /**
    * Access method for the StatusCd.
    *
    * @return  String the current value of the StatusCd
    */
    public String getStatusCd()
    {
        return statusCd;
    }

    /**
    * Sets the value of the StatusCd.
    *
    * @param String the aStatusCd
    */
    public void setStatusCd(String aStatusCd)
    {
        statusCd = aStatusCd;
        setItDirty(true);
    }

     /**
    * Access method for the StatusTime.
    *
    * @return  Timestamp the current value of the StatusTime
    */
    public Timestamp getStatusTime()
    {
        return statusTime;
    }

    /**
    * Sets the value of the StatusTime.
    *
    * @param Timestamp the aStatusTime
    */
    public void setStatusTime(Timestamp aStatusTime)
    {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
    * Sets the value of the StatusTime_s.
    *
    * @param String the strTime
    */
   public void setStatusTime_s(String strTime)
   {
      if (strTime == null) return;
           this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    * Access method for the SubjectPersonUid.
    *
    * @return  Long the current value of the SubjectPersonUid
    */
    public Long getSubjectPersonUid()
    {
        return subjectPersonUid;
    }
    /**
    * Sets the value of the SubjectPersonUid.
    *
    * @param Long the aSubjectPersonUid
    */
    public void setSubjectPersonUid(Long aSubjectPersonUid)
    {
        subjectPersonUid = aSubjectPersonUid;
        setItDirty(true);
    }

     /**
    * Access method for the TargetSiteCd.
    *
    * @return  String the current value of the TargetSiteCd
    */
    public String getTargetSiteCd()
    {
        return targetSiteCd;
    }

    /**
    * Sets the value of the TargetSiteCd.
    *
    * @param String the aTargetSiteCd
    */
    public void setTargetSiteCd(String aTargetSiteCd)
    {
        targetSiteCd = aTargetSiteCd;
        setItDirty(true);
    }

     /**
    * Access method for the TargetSiteDescTxt.
    *
    * @return  String the current value of the TargetSiteDescTxt
    */
    public String getTargetSiteDescTxt()
    {
        return targetSiteDescTxt;
    }

    /**
    * Sets the value of the TargetSiteDescTxt.
    *
    * @param String the aTargetSiteDescTxt
    */
    public void setTargetSiteDescTxt(String aTargetSiteDescTxt)
    {
        targetSiteDescTxt = aTargetSiteDescTxt;
        setItDirty(true);
    }

     /**
    * Access method for the Txt.
    *
    * @return  String the current value of the Txt
    */
    public String getTxt()
    {
        return txt;
    }

    /**
    * Sets the value of the Txt.
    *
    * @param String the aTxt
    */
    public void setTxt(String aTxt)
    {
        txt = aTxt;
        setItDirty(true);
    }

     /**
    * Access method for the UserAffiliationTxt.
    *
    * @return  String the current value of the UserAffiliationTxt
    */
    public String getUserAffiliationTxt()
    {
        return userAffiliationTxt;
    }

    /**
    * Sets the value of the UserAffiliationTxt.
    *
    * @param String the aUserAffiliationTxt
    */
    public void setUserAffiliationTxt(String aUserAffiliationTxt)
    {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

     /**
    * Access method for the ValueCd.
    *
    * @return  String the current value of the ValueCd
    */
    public String getValueCd()
    {
        return valueCd;
    }


    /**
    * Sets the value of the ValueCd.
    *
    * @param String the aValueCd
    */
    public void setValueCd(String aValueCd)
    {
        valueCd = aValueCd;
        setItDirty(true);
    }

     /**
    * Access method for the YnuCd.
    *
    * @return  String the current value of the YnuCd
    */
    public String getYnuCd()
    {
        return ynuCd;
    }

    /**
    * Sets the value of the YnuCd.
    *
    * @param String the aYnuCd
    */
    public void setYnuCd(String aYnuCd)
    {
        ynuCd = aYnuCd;
        setItDirty(true);
    }

     /**
    * Access method for the ProgramJurisdictionOid.
    *
    * @return  String the current value of the ProgramJurisdictionOid
    */
    public Long getProgramJurisdictionOid()
    {
        return programJurisdictionOid;
    }

    /**
    * Sets the value of the ProgramJurisdictionOid.
    *
    * @param String the aProgramJurisdictionOid
    */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
    {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

     /**
    * Access method for the Code.
    *
    * @return  String the current value of the Code
    */
    public String getSharedInd()
    {
        return sharedInd;
    }

    /**
    * Sets the value of the SharedInd.
    *
    * @param String the aSharedInd
    */
    public void setSharedInd(String aSharedInd)
    {
        sharedInd = aSharedInd;
        setItDirty(true);
    }


   /**
    * get the value of the boolean property.
    * @param objectname1 Object the object name
    * @param objectname2 Object the object name
    * @param voClass Class the class
    * @return isEqual the value of the boolean value
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( ObservationHistDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }


      /**
    * Sets the value of the itDirty property.
    *
    * @param itDirty boolean the new value of the ItDirty property
    */
   public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }


   /**
    * get the value of the boolean property.
    *
    * @return ItDirty the value of the boolean vlue
    */
   public boolean isItDirty() {
     return itDirty;
   }

 /**
    * Sets the value of the ItNew property.
    *
    * @param itNew boolean the new value of the boolean property
    */
   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }

  /**
    * get the value of the itNew property.
    *
    * @return itNew the value of the boolean property
    */
   public boolean isItNew() {
     return itNew;
   }

   /**
    * gets the value of the ItDelete property.
    *
    * @return ItDelete the new value of the boolean property
    */
   public boolean isItDelete() {
     return itDelete;
   }

  /**
    * Sets the value of the ItDelete property.
    *
    * @param itDelete boolean the value of the boolean property
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }



}
