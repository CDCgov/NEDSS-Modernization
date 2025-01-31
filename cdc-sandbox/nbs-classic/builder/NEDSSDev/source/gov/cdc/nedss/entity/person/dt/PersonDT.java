/**
* Name:		    PersonDT
* Description:	Person data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/
package gov.cdc.nedss.entity.person.dt;

import java.sql.Timestamp;
import java.util.Calendar;

import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;


public class PersonDT
    extends AbstractVO
    implements RootDTInterface {
	private static final long serialVersionUID = 1L;
    private String dedupMatchInd;
    private Integer groupNbr;
    private Timestamp groupTime;
    private Long personUid;
    private Long personParentUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String admGenderCd;
    private Integer ageCalc;
    private Timestamp ageCalcTime;
    private String ageCalcUnitCd;
    private String ageCategoryCd;
    private String ageReported;
    private Timestamp ageReportedTime;
    private String ageReportedUnitCd;
    private Timestamp asOfDateAdm;
    private Timestamp asOfDateEthnicity;
    private Timestamp asOfDateGeneral;
    private Timestamp asOfDateMorbidity;
    private Timestamp asOfDateSex;
    private String birthGenderCd;
    private Integer birthOrderNbr;
    private String birthOrderNbrStr;
    private Timestamp birthTime;
    private Timestamp birthTimeCalc;
    private String cd;
    private String cdDescTxt;
    private String currSexCd;
    private String deceasedIndCd;
    private Timestamp deceasedTime;
    private String description;
    private String educationLevelCd;
    private String educationLevelDescTxt;
    private String electronicInd;
    private String ethnicGroupInd;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String maritalStatusCd;
    private String maritalStatusDescTxt;
    private String mothersMaidenNm;
    private String multipleBirthInd;
    private String occupationCd;

    private String preferredGenderCd;
    private String primLangCd;
    private String primLangDescTxt;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String statusCd;
    private Timestamp statusTime;
    private String survivedIndCd;
    private String userAffiliationTxt;
    private String firstNm;
    private String lastNm;
    private String middleNm;
    private String nmPrefix;
    private String nmSuffix;
    private String preferredNm;
    private String hmStreetAddr1;
    private String hmStreetAddr2;
    private String hmCityCd;
    private String hmCityDescTxt;
    private String hmStateCd;
    private String hmZipCd;
    private String hmCntyCd;
    private String hmCntryCd;
    private String hmPhoneNbr;
    private String hmPhoneCntryCd;
    private String hmEmailAddr;
    private String cellPhoneNbr;
    private String wkStreetAddr1;
    private String wkStreetAddr2;
    private String wkCityCd;
    private String wkCityDescTxt;
    private String wkStateCd;
    private String wkZipCd;
    private String wkCntyCd;
    private String wkCntryCd;
    private String wkPhoneNbr;
    private String wkPhoneCntryCd;
    private String wkEmailAddr;
    private String SSN;
    private String medicaidNum;
    private String dlNum;
    private String dlStateCd;
    private String raceCd;
    private Integer raceSeqNbr;
    private String raceCategoryCd;
    private String ethnicityGroupCd;
    private Integer ethnicGroupSeqNbr;
    private Integer adultsInHouseNbr;
    private String adultsInHouseNbrStr;
    private Integer childrenInHouseNbr;
    private String childrenInHouseNbrStr;
    private String birthCityCd;
    private String birthCityDescTxt;
    private String birthCntryCd;
    private String birthStateCd;
    private String raceDescTxt;
    private String ethnicGroupDescTxt;
    private Integer versionCtrlNbr;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;
    private String edxInd =null;
    private boolean isCaseInd= false;
    private String speaksEnglishCd;
    private String additionalGenderCd;
    private String eharsId;
    private String ethnicUnkReasonCd;
    private String sexUnkReasonCd;
    private boolean isReentrant= false;

    
   public boolean isReentrant() {
		return isReentrant;
	}

	public void setReentrant(boolean isReentrant) {
		this.isReentrant = isReentrant;
	}

	public boolean isCaseInd() {
		return isCaseInd;
	}

	public void setCaseInd(boolean isCaseInd) {
		this.isCaseInd = isCaseInd;
	}

	/**
     *
     * @return Long
     */
    public Long getPersonUid() {

        return personUid;
    }

    public void setDedupMatchInd(String dedupMatchInd) {
      this.dedupMatchInd = dedupMatchInd;
    }
    public String getDedupMatchInd() {
      return dedupMatchInd;
    }

    public void setGroupNbr(Integer groupNbr) {
      this.groupNbr = groupNbr;
    }
    public Integer getGroupNbr() {
      return groupNbr;
    }

    public void setGroupTime(Timestamp groupTime) {
      this.groupTime = groupTime;
    }
    public Timestamp getGroupTime() {
      return groupTime;
    }

    /**
     *
     * @param aPersonUid
     */
    public void setPersonUid(Long aPersonUid) {
        personUid = aPersonUid;
        setItDirty(true);
    }

    /**
     *
     * @return Long
     */
    public Long getPersonParentUid() {

        return personParentUid;
    }

    /**
     *
     * @param aPersonParentUid
     */
    public void setPersonParentUid(Long aPersonParentUid) {
        personParentUid = aPersonParentUid;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getAddReasonCd() {

        return addReasonCd;
    }

    /**
     *
     * @param aAddReasonCd
     */
    public void setAddReasonCd(String aAddReasonCd) {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     *
     * @return Timestamp
     */
    public Timestamp getAddTime() {

        return addTime;
    }

    /**
     *
     * @param aAddTime
     */
    public void setAddTime(Timestamp aAddTime) {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setAddTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return Long
     */
    public Long getAddUserId() {

        return addUserId;
    }

    /**
     *
     * @param aAddUserId
     */
    public void setAddUserId(Long aAddUserId) {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getAdministrativeGenderCd() {

        return admGenderCd;
    }

    /**
     *
     * @param aAdministrativeGenderCd
     */
    public void setAdministrativeGenderCd(String aAdministrativeGenderCd) {
        admGenderCd = aAdministrativeGenderCd;
        setItDirty(true);
    }

    /**
     *
     * @return Integer
     */
    public Integer getAgeCalc() {

        return ageCalc;
    }

    /**
     *
     * @param aAgeCalc
     */
    public void setAgeCalc(Integer aAgeCalc) {
        ageCalc = aAgeCalc;
        setItDirty(true);
    }

    /**
     *
     * @return Timestamp
     */
    public Timestamp getAgeCalcTime() {

        return ageCalcTime;
    }

    /**
     *
     * @param aAgeCalcTime
     */
    public void setAgeCalcTime(Timestamp aAgeCalcTime) {
        ageCalcTime = aAgeCalcTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setAgeCalcTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setAgeCalcTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return String
     */
    public String getAgeCalcUnitCd() {

        return ageCalcUnitCd;
    }

    /**
     *
     * @param aAgeCalcUnitCd
     */
    public void setAgeCalcUnitCd(String aAgeCalcUnitCd) {
        ageCalcUnitCd = aAgeCalcUnitCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getAgeCategoryCd() {

        return ageCategoryCd;
    }

    /**
     *
     * @param aAgeCategoryCd
     */
    public void setAgeCategoryCd(String aAgeCategoryCd) {
        ageCategoryCd = aAgeCategoryCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getAgeReported() {

        return ageReported;
    }

    /**
     *
     * @param aAgeReported
     */
    public void setAgeReported(String aAgeReported) {
        ageReported = aAgeReported;
        setItDirty(true);
    }

    /**
     *
     * @return Timestamp
     */
    public Timestamp getAgeReportedTime() {

        return ageReportedTime;
    }

    /**
     *
     * @param aAgeReportedTime
     */
    public void setAgeReportedTime(Timestamp aAgeReportedTime) {
        ageReportedTime = aAgeReportedTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setAgeReportedTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setAgeReportedTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return String
     */
    public String getAgeReportedUnitCd() {

        return ageReportedUnitCd;
    }


    /**
     *
     * @param aAgeReportedUnitCd
     */
    public void setAgeReportedUnitCd(String aAgeReportedUnitCd) {
        ageReportedUnitCd = aAgeReportedUnitCd;
        setItDirty(true);
    }

	/**
	 *
	 * @return Timestamp
	 */
	public Timestamp getAsOfDateAdmin(){
		return this.asOfDateAdm;
	}

	/**
	 *
	 * @param aAsOfDateAdmin
	 */
	public void setAsOfDateAdmin(Timestamp aAsOfDateAdmin){
		asOfDateAdm = aAsOfDateAdmin;
		setItDirty(true);

	}


	public void setAsOfDateAdmin_s(String strTime){

		if (strTime == null)  return;

	    this.setAsOfDateAdmin(StringUtils.stringToStrutsTimestamp(strTime));
	}
	
	public String getAsOfDateAdmin_s(){
        if(this.getAsOfDateAdmin() != null)
		return StringUtils.formatDate(this.getAsOfDateAdmin());
        else
        return null;

	  //  this.setAsOfDateAdmin(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 *
	 * @return Timestamp
	 */
	public Timestamp getAsOfDateEthnicity(){
		return this.asOfDateEthnicity;

	}


	/**
	 *
	 * @param aAsOfDateEthnicity
	 * @return
	 */
	public void setAsOfDateEthnicity(Timestamp aAsOfDateEthnicity){
		this.asOfDateEthnicity = aAsOfDateEthnicity;
		setItDirty(true);
	}

        public void setAsOfDateEthnicity_s(String strTime){

                if (strTime == null)  return;

            this.setAsOfDateEthnicity(StringUtils.stringToStrutsTimestamp(strTime));
        }
        
        public String getAsOfDateEthnicity_s(){
            if(this.getAsOfDateEthnicity() != null)
    		return StringUtils.formatDate(this.getAsOfDateEthnicity());
            else
            return null;

    	  //  this.setAsOfDateAdmin(StringUtils.stringToStrutsTimestamp(strTime));
    	}


	/**
	 *
	 * @return Timestamp
	 */
	public Timestamp getAsOfDateGeneral(){
		return this.asOfDateGeneral;
	}


	/**
	 *
	 * @param aAsOfDateGeneral
	 */
	public void setAsOfDateGeneral(Timestamp aAsOfDateGeneral){
		this.asOfDateGeneral = aAsOfDateGeneral;
		setItDirty(true);
	}

        public void setAsOfDateGeneral_s(String strTime) {

               if (strTime == null)

                   return;

               this.setAsOfDateGeneral(StringUtils.stringToStrutsTimestamp(strTime));
           }
        
        public String getAsOfDateGeneral_s(){
            if(this.getAsOfDateGeneral() != null)
    		return StringUtils.formatDate(this.getAsOfDateGeneral());
            else
            return null;

    	  //  this.setAsOfDateAdmin(StringUtils.stringToStrutsTimestamp(strTime));
    	}


	/**
	 *
	 * @return Timestamp
	 */
	public Timestamp getAsOfDateMorbidity(){
		return this.asOfDateMorbidity;
	}

	/**
	 *
	 * @param aAsOfDateMorbidity
	 */
	public void setAsOfDateMorbidity(Timestamp aAsOfDateMorbidity){
		this.asOfDateMorbidity = aAsOfDateMorbidity;
		setItDirty(true);
	}

        public void setAsOfDateMorbidity_s(String strTime) {

              if (strTime == null)

                  return;

              this.setAsOfDateMorbidity(StringUtils.stringToStrutsTimestamp(strTime));
          }
        
        public String getAsOfDateMorbidity_s(){
            if(this.getAsOfDateMorbidity() != null)
    		return StringUtils.formatDate(this.getAsOfDateMorbidity());
            else
            return null;

    	  //  this.setAsOfDateAdmin(StringUtils.stringToStrutsTimestamp(strTime));
    	}

	/**
	 *
	 * @return Timestamp
	 */
	public Timestamp getAsOfDateSex(){
		return this.asOfDateSex;
	}


	/**
	 *
	 * @param aAsOfDateSex
	 */
	public void setAsOfDateSex(Timestamp aAsOfDateSex){
		
		//Remove the time from AsOfDateSex
		if(aAsOfDateSex!=null){
			Calendar c = Calendar.getInstance();
			c.setTime(aAsOfDateSex);
			c.set(Calendar.SECOND, 0);c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.HOUR_OF_DAY, 0);
			aAsOfDateSex = new java.sql.Timestamp(c.getTime().getTime());
		}
		
		this.asOfDateSex = aAsOfDateSex;
		setItDirty(true);
	}

        public void setAsOfDateSex_s(String strTime) {

             if (strTime == null)

                 return;

             this.setAsOfDateSex(StringUtils.stringToStrutsTimestamp(strTime));
         }
        
        public String getAsOfDateSex_s(){
            if(this.getAsOfDateSex() != null)
    		return StringUtils.formatDate(this.getAsOfDateSex());
            else
            return null;

    	  //  this.setAsOfDateAdmin(StringUtils.stringToStrutsTimestamp(strTime));
    	}


    /**
     *
     * @return String
     */
    public String getBirthGenderCd() {

        return birthGenderCd;
    }

    /**
     *
     * @param aBirthGenderCd
     */
    public void setBirthGenderCd(String aBirthGenderCd) {
        birthGenderCd = aBirthGenderCd;
        setItDirty(true);
    }

    /**
     *
     * @return Integer
     */
    public Integer getBirthOrderNbr() {

        return birthOrderNbr;
    }

    /**
     *
     * @param aBirthOrderNbr
     */
    public void setBirthOrderNbr(Integer aBirthOrderNbr) {
        birthOrderNbr = aBirthOrderNbr;
        setItDirty(true);
    }

    /**
     *
     * @return Timestamp
     */
    public Timestamp getBirthTime() {

        return birthTime;
    }

    /**
     *
     * @param aBirthTime
     */
    public void setBirthTime(Timestamp aBirthTime) {
        birthTime = aBirthTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setBirthTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setBirthTime(StringUtils.stringToStrutsTimestamp(strTime));
    }
    
    public String getBirthTime_s() {
    
            if(this.getBirthTime() != null)
    		return StringUtils.formatDate(this.getBirthTime());
            else
            return null;

    	  
    	}
    public String getBirthTimeCalc_s() {
        
        if(this.getBirthTimeCalc() != null)
		return StringUtils.formatDate(this.getBirthTimeCalc());
        else
        return null;

	  
	}

    /**
     *
     * @return Timestamp
     */
    public Timestamp getBirthTimeCalc() {

        return birthTimeCalc;
    }

    /**
     *
     * @param aBirthTimeCalc
     */
    public void setBirthTimeCalc(Timestamp aBirthTimeCalc) {
        birthTimeCalc = aBirthTimeCalc;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setBirthTimeCalc_s(String strTime) {

        if (strTime == null)

            return;

        this.setBirthTimeCalc(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return String
     */
    public String getCd() {

        return cd;
    }

    /**
     *
     * @param aCd
     */
    public void setCd(String aCd) {
        cd = aCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getCdDescTxt() {

        return cdDescTxt;
    }

    /**
     *
     * @param aCdDescTxt
     */
    public void setCdDescTxt(String aCdDescTxt) {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getCurrSexCd() {

        return currSexCd;
    }

    /**
     *
     * @param aCurrSexCd
     */
    public void setCurrSexCd(String aCurrSexCd) {
        currSexCd = aCurrSexCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getDeceasedIndCd() {

        return deceasedIndCd;
    }

    /**
     *
     * @param aDeceasedIndCd
     */
    public void setDeceasedIndCd(String aDeceasedIndCd) {
        deceasedIndCd = aDeceasedIndCd;
        setItDirty(true);
    }

    /**
     *
     * @return Timestamp
     */
    public Timestamp getDeceasedTime() {

        return deceasedTime;
    }

    /**
     *
     * @param aDeceasedTime
     */
    public void setDeceasedTime(Timestamp aDeceasedTime) {
        deceasedTime = aDeceasedTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setDeceasedTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setDeceasedTime(StringUtils.stringToStrutsTimestamp(strTime));
    }
    
    public String getDeceasedTime_s(){
        if(this.getDeceasedTime() != null)
		return StringUtils.formatDate(this.getDeceasedTime());
        else
        return null;

	  //  this.setAsOfDateAdmin(StringUtils.stringToStrutsTimestamp(strTime));
	}


    /**
     *
     * @return String
     */
    public String getDescription() {

        return description;
    }

    /**
     *
     * @param aDescription
     */
    public void setDescription(String aDescription) {
        description = aDescription;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getEducationLevelCd() {

        return educationLevelCd;
    }

    /**
     *
     * @param aEducationLevelCd
     */
    public void setEducationLevelCd(String aEducationLevelCd) {
        educationLevelCd = aEducationLevelCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getEducationLevelDescTxt() {

        return educationLevelDescTxt;
    }

    /**
     *
     * @param aEducationLevelDescTxt
     */
    public void setEducationLevelDescTxt(String aEducationLevelDescTxt) {
        educationLevelDescTxt = aEducationLevelDescTxt;
        setItDirty(true);
    }

	/**
	 *
	 * @return String
	 */
	public String getElectronicInd(){
		return this.electronicInd;
	}

	/**
	 *
	 * @param aElectronicInd
	 */
	public void setElectronicInd(String aElectronicInd){
		this.electronicInd = aElectronicInd;
		setItDirty(true);
	}


    /**
     *
     * @return String
     */
    public String getEthnicGroupInd() {

        return ethnicGroupInd;
    }

    /**
     *
     * @param aEthnicGroupInd
     */
    public void setEthnicGroupInd(String aEthnicGroupInd) {
        ethnicGroupInd = aEthnicGroupInd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getLastChgReasonCd() {

        return lastChgReasonCd;
    }

    /**
     *
     * @param aLastChgReasonCd
     */
    public void setLastChgReasonCd(String aLastChgReasonCd) {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public Timestamp getLastChgTime() {

        return lastChgTime;
    }

    /**
     *
     * @param aLastChgTime
     */
    public void setLastChgTime(Timestamp aLastChgTime) {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setLastChgTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return Long
     */
    public Long getLastChgUserId() {

        return lastChgUserId;
    }

    /**
     *
     * @param aLastChgUserId
     */
    public void setLastChgUserId(Long aLastChgUserId) {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getLocalId() {

        return localId;
    }

    /**
     *
     * @param aLocalId
     */
    public void setLocalId(String aLocalId) {
        localId = aLocalId;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getMaritalStatusCd() {

        return maritalStatusCd;
    }

    /**
     *
     * @param aMaritalStatusCd
     */
    public void setMaritalStatusCd(String aMaritalStatusCd) {
        maritalStatusCd = aMaritalStatusCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getMaritalStatusDescTxt() {

        return maritalStatusDescTxt;
    }

    /**
     *
     * @param aMaritalStatusDescTxt
     */
    public void setMaritalStatusDescTxt(String aMaritalStatusDescTxt) {
        maritalStatusDescTxt = aMaritalStatusDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getMothersMaidenNm() {

        return mothersMaidenNm;
    }

    /**
     *
     * @param aMothersMaidenNm
     */
    public void setMothersMaidenNm(String aMothersMaidenNm) {
        mothersMaidenNm = aMothersMaidenNm;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getMultipleBirthInd() {

        return multipleBirthInd;
    }

    /**
     *
     * @param aMultipleBirthInd
     */
    public void setMultipleBirthInd(String aMultipleBirthInd) {
        multipleBirthInd = aMultipleBirthInd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getOccupationCd() {

        return occupationCd;
    }

    /**
     *
     * @param aOccupationCd
     */
    public void setOccupationCd(String aOccupationCd) {
        occupationCd = aOccupationCd;
        setItDirty(true);
    }



    /**
     *
     * @return String
     */
    public String getPreferredGenderCd() {

        return preferredGenderCd;
    }

    /**
     *
     * @param aPreferredGenderCd
     */
    public void setPreferredGenderCd(String aPreferredGenderCd) {
        preferredGenderCd = aPreferredGenderCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getPrimLangCd() {

        return primLangCd;
    }

    /**
     *
     * @param aPrimLangCd
     */
    public void setPrimLangCd(String aPrimLangCd) {
        primLangCd = aPrimLangCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getPrimLangDescTxt() {

        return primLangDescTxt;
    }

    /**
     *
     * @param aPrimLangDescTxt
     */
    public void setPrimLangDescTxt(String aPrimLangDescTxt) {
        primLangDescTxt = aPrimLangDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getRecordStatusCd() {

        return recordStatusCd;
    }

    /**
     *
     * @param aRecordStatusCd
     */
    public void setRecordStatusCd(String aRecordStatusCd) {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public Timestamp getRecordStatusTime() {

        return recordStatusTime;
    }

    /**
     *
     * @param aRecordStatusTime
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime) {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setRecordStatusTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return String
     */
    public String getStatusCd() {

        return statusCd;
    }

    /**
     *
     * @param aStatusCd
     */
    public void setStatusCd(String aStatusCd) {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public Timestamp getStatusTime() {

        return statusTime;
    }

    /**
     *
     * @param aStatusTime
     */
    public void setStatusTime(Timestamp aStatusTime) {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setStatusTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return String
     */
    public String getSurvivedIndCd() {

        return survivedIndCd;
    }

    /**
     *
     * @param aSurvivedIndCd
     */
    public void setSurvivedIndCd(String aSurvivedIndCd) {
        survivedIndCd = aSurvivedIndCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getUserAffiliationTxt() {

        return userAffiliationTxt;
    }

    /**
     *
     * @param aUserAffiliationTxt
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt) {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getFirstNm() {

        return firstNm;
    }

    /**
     *
     * @param aFirstNm
     */
    public void setFirstNm(String aFirstNm) {
        firstNm = aFirstNm;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getLastNm() {

        return lastNm;
    }

    /**
     *
     * @param aLastNm
     */
    public void setLastNm(String aLastNm) {
        lastNm = aLastNm;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getMiddleNm() {

        return middleNm;
    }

    /**
     *
     * @param aMiddleNm
     */
    public void setMiddleNm(String aMiddleNm) {
        middleNm = aMiddleNm;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getNmPrefix() {

        return nmPrefix;
    }

    /**
     *
     * @param aNmPrefix
     */
    public void setNmPrefix(String aNmPrefix) {
        nmPrefix = aNmPrefix;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getNmSuffix() {

        return nmSuffix;
    }

    /**
     *
     * @param aNmSuffix
     */
    public void setNmSuffix(String aNmSuffix) {
        nmSuffix = aNmSuffix;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getPreferredNm() {

        return preferredNm;
    }

    /**
     *
     * @param aPreferredNm
     */
    public void setPreferredNm(String aPreferredNm) {
        preferredNm = aPreferredNm;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getHmStreetAddr1() {

        return hmStreetAddr1;
    }

    /**
     *
     * @param aHmStreetAddr1
     */
    public void setHmStreetAddr1(String aHmStreetAddr1) {
        hmStreetAddr1 = aHmStreetAddr1;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getHmStreetAddr2() {

        return hmStreetAddr2;
    }

    /**
     *
     * @param aHmStreetAddr2
     */
    public void setHmStreetAddr2(String aHmStreetAddr2) {
        hmStreetAddr2 = aHmStreetAddr2;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getHmCityCd() {

        return hmCityCd;
    }

    /**
     *
     * @param aHmCityCd
     */
    public void setHmCityCd(String aHmCityCd) {
        hmCityCd = aHmCityCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getHmCityDescTxt() {

        return hmCityDescTxt;
    }

    /**
     *
     * @param aHmCityDescTxt
     */
    public void setHmCityDescTxt(String aHmCityDescTxt) {
        hmCityDescTxt = aHmCityDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getHmStateCd() {

        return hmStateCd;
    }

    /**
     *
     * @param aHmStateCd
     */
    public void setHmStateCd(String aHmStateCd) {
        hmStateCd = aHmStateCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getHmZipCd() {

        return hmZipCd;
    }

    /**
     *
     * @param aHmZipCd
     */
    public void setHmZipCd(String aHmZipCd) {
        hmZipCd = aHmZipCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getHmCntyCd() {

        return hmCntyCd;
    }

    /**
     *
     * @param aHmCntyCd
     */
    public void setHmCntyCd(String aHmCntyCd) {
        hmCntyCd = aHmCntyCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getHmCntryCd() {

        return hmCntryCd;
    }

    /**
     *
     * @param aHmCntryCd
     */
    public void setHmCntryCd(String aHmCntryCd) {
        hmCntryCd = aHmCntryCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getHmPhoneNbr() {

        return hmPhoneNbr;
    }

    /**
     *
     * @param aHmPhoneNbr
     */
    public void setHmPhoneNbr(String aHmPhoneNbr) {
        hmPhoneNbr = aHmPhoneNbr;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getHmPhoneCntryCd() {

        return hmPhoneCntryCd;
    }

    /**
     *
     * @param aHmPhoneCntryCd
     */
    public void setHmPhoneCntryCd(String aHmPhoneCntryCd) {
        hmPhoneCntryCd = aHmPhoneCntryCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getHmEmailAddr() {

        return hmEmailAddr;
    }

    /**
     *
     * @param aHmEmailAddr
     */
    public void setHmEmailAddr(String aHmEmailAddr) {
        hmEmailAddr = aHmEmailAddr;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getCellPhoneNbr() {

        return cellPhoneNbr;
    }

    /**
     *
     * @param aCellPhoneNbr
     */
    public void setCellPhoneNbr(String aCellPhoneNbr) {
        cellPhoneNbr = aCellPhoneNbr;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getWkStreetAddr1() {

        return wkStreetAddr1;
    }

    /**
     *
     * @param aWkStreetAddr1
     */
    public void setWkStreetAddr1(String aWkStreetAddr1) {
        wkStreetAddr1 = aWkStreetAddr1;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getWkStreetAddr2() {

        return wkStreetAddr2;
    }

    /**
     *
     * @param aWkStreetAddr2
     */
    public void setWkStreetAddr2(String aWkStreetAddr2) {
        wkStreetAddr2 = aWkStreetAddr2;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getWkCityCd() {

        return wkCityCd;
    }

    /**
     *
     * @param aWkCityCd
     */
    public void setWkCityCd(String aWkCityCd) {
        wkCityCd = aWkCityCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getWkCityDescTxt() {

        return wkCityDescTxt;
    }

    /**
     *
     * @param aWkCityDescTxt
     */
    public void setWkCityDescTxt(String aWkCityDescTxt) {
        wkCityDescTxt = aWkCityDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getWkStateCd() {

        return wkStateCd;
    }

    /**
     *
     * @param aWkStateCd
     */
    public void setWkStateCd(String aWkStateCd) {
        wkStateCd = aWkStateCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getWkZipCd() {

        return wkZipCd;
    }

    /**
     *
     * @param aWkZipCd
     */
    public void setWkZipCd(String aWkZipCd) {
        wkZipCd = aWkZipCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getWkCntyCd() {

        return wkCntyCd;
    }

    /**
     *
     * @param aWkCntyCd
     */
    public void setWkCntyCd(String aWkCntyCd) {
        wkCntyCd = aWkCntyCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getWkCntryCd() {

        return wkCntryCd;
    }

    /**
     *
     * @param aWkCntryCd
     */
    public void setWkCntryCd(String aWkCntryCd) {
        wkCntryCd = aWkCntryCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getWkPhoneNbr() {

        return wkPhoneNbr;
    }

    /**
     *
     * @param aWkPhoneNbr
     */
    public void setWkPhoneNbr(String aWkPhoneNbr) {
        wkPhoneNbr = aWkPhoneNbr;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getWkPhoneCntryCd() {

        return wkPhoneCntryCd;
    }

    /**
     *
     * @param aWkPhoneCntryCd
     */
    public void setWkPhoneCntryCd(String aWkPhoneCntryCd) {
        wkPhoneCntryCd = aWkPhoneCntryCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getWkEmailAddr() {

        return wkEmailAddr;
    }

    /**
     *
     * @param aWkEmailAddr
     */
    public void setWkEmailAddr(String aWkEmailAddr) {
        wkEmailAddr = aWkEmailAddr;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getSSN() {

        return SSN;
    }

    /**
     *
     * @param aSSN
     */
    public void setSSN(String aSSN) {
        SSN = aSSN;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getMedicaidNum() {

        return medicaidNum;
    }

    /**
     *
     * @param aMedicaidNum
     */
    public void setMedicaidNum(String aMedicaidNum) {
        medicaidNum = aMedicaidNum;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getDlNum() {

        return dlNum;
    }

    /**
     *
     * @param aDlNum
     */
    public void setDlNum(String aDlNum) {
        dlNum = aDlNum;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getDlStateCd() {

        return dlStateCd;
    }

    /**
     *
     * @param aDlStateCd
     */
    public void setDlStateCd(String aDlStateCd) {
        dlStateCd = aDlStateCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getRaceCd() {

        return raceCd;
    }

    /**
     *
     * @param aRaceCd
     */
    public void setRaceCd(String aRaceCd) {
        raceCd = aRaceCd;
        setItDirty(true);
    }

    /**
     *
     * @return Integer
     */
    public Integer getRaceSeqNbr() {

        return raceSeqNbr;
    }

    /**
     *
     * @param aRaceSeqNbr
     */
    public void setRaceSeqNbr(Integer aRaceSeqNbr) {
        raceSeqNbr = aRaceSeqNbr;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getRaceCategoryCd() {

        return raceCategoryCd;
    }

    /**
     *
     * @param aRaceCategoryCd
     */
    public void setRaceCategoryCd(String aRaceCategoryCd) {
        raceCategoryCd = aRaceCategoryCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getEthnicityGroupCd() {

        return ethnicityGroupCd;
    }

    /**
     *
     * @param aEthnicityGroupCd
     */
    public void setEthnicityGroupCd(String aEthnicityGroupCd) {
        ethnicityGroupCd = aEthnicityGroupCd;
        setItDirty(true);
    }

    /**
     *
     * @return Integer
     */
    public Integer getEthnicGroupSeqNbr() {

        return ethnicGroupSeqNbr;
    }

    /**
     *
     * @param aEthnicGroupSeqNbr
     */
    public void setEthnicGroupSeqNbr(Integer aEthnicGroupSeqNbr) {
        ethnicGroupSeqNbr = aEthnicGroupSeqNbr;
        setItDirty(true);
    }

    /**
     *
     * @return Integer
     */
    public Integer getAdultsInHouseNbr() {

        return adultsInHouseNbr;
    }

    /**
     *
     * @param aAdultsInHouseNbr
     */
    public void setAdultsInHouseNbr(Integer aAdultsInHouseNbr) {
    	if(adultsInHouseNbr!=null)
    		adultsInHouseNbrStr=adultsInHouseNbr.toString();
    	adultsInHouseNbr = aAdultsInHouseNbr;
        setItDirty(true);
    }

    /**
     *
     * @return Integer
     */
    public Integer getChildrenInHouseNbr() {

        return childrenInHouseNbr;
    }

    /**
     *
     * @param aChildrenInHouseNbr
     */
    public void setChildrenInHouseNbr(Integer aChildrenInHouseNbr) {
    	
    	if(childrenInHouseNbr!=null)
    		childrenInHouseNbrStr=childrenInHouseNbr.toString();
    	childrenInHouseNbr = aChildrenInHouseNbr;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getBirthCityCd() {

        return birthCityCd;
    }

    /**
     *
     * @param aBirthCityCd
     */
    public void setBirthCityCd(String aBirthCityCd) {
        birthCityCd = aBirthCityCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getBirthCityDescTxt() {

        return birthCityDescTxt;
    }

    /**
     *
     * @param aBirthCityDescTxt
     */
    public void setBirthCityDescTxt(String aBirthCityDescTxt) {
        birthCityDescTxt = aBirthCityDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getBirthCntryCd() {

        return birthCntryCd;
    }

    /**
     *
     * @param aBirthCntryCd
     */
    public void setBirthCntryCd(String aBirthCntryCd) {
        birthCntryCd = aBirthCntryCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getBirthStateCd() {

        return birthStateCd;
    }

    /**
     *
     * @param aBirthStateCd
     */
    public void setBirthStateCd(String aBirthStateCd) {
        birthStateCd = aBirthStateCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getRaceDescTxt() {

        return raceDescTxt;
    }

    /**
     *
     * @param aRaceDescTxt
     */
    public void setRaceDescTxt(String aRaceDescTxt) {
        raceDescTxt = aRaceDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getEthnicGroupDescTxt() {

        return ethnicGroupDescTxt;
    }

    /**
     *
     * @param aEthnicGroupDescTxt
     */
    public void setEthnicGroupDescTxt(String aEthnicGroupDescTxt) {
        ethnicGroupDescTxt = aEthnicGroupDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return Integer
     */
    public Integer getVersionCtrlNbr() {

        return versionCtrlNbr;
    }

    /**
     *
     * @param aVersionCtrlNbr
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr) {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getProgAreaCd() {

        return progAreaCd;
    }

    /**
     *
     * @param aProgAreaCd
     */
    public void setProgAreaCd(String aProgAreaCd) {
        progAreaCd = aProgAreaCd;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getJurisdictionCd() {

        return jurisdictionCd;
    }

    /**
     *
     * @param aJurisdictionCd
     */
    public void setJurisdictionCd(String aJurisdictionCd) {
        jurisdictionCd = aJurisdictionCd;
        setItDirty(true);
    }

    /**
     *
     * @return Long
     */
    public Long getProgramJurisdictionOid() {

        return programJurisdictionOid;
    }

    /**
     *
     * @param aProgramJurisdictionOid
     */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

    /**
     *
     * @return String
     */
    public String getSharedInd() {

        return sharedInd;
    }

    /**
     *
     * @param aSharedInd
     */
    public void setSharedInd(String aSharedInd) {
        sharedInd = aSharedInd;
        setItDirty(true);
    }

    /**
     *
     * @param objectname1
     * @param objectname2
     * @param voClass
     * @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        voClass = ((PersonDT)objectname1).getClass();

        NedssUtils compareObjs = new NedssUtils();

        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
     *
     * @param itDirty
     */
    public void setItDirty(boolean itDirty) {
        this.itDirty = itDirty;
    }

    /**
     *
     * @return boolean
     */
    public boolean isItDirty() {

        return itDirty;
    }

    /**
     *
     * @param itNew
     */
    public void setItNew(boolean itNew) {
        this.itNew = itNew;
    }

    /**
     *
     * @return boolean
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     *
     * @return boolean
     */
    public boolean isItDelete() {

        return itDelete;
    }

    /**
     *
     * @param itDelete
     */
    public void setItDelete(boolean itDelete) {
        this.itDelete = itDelete;
    }

    /**
     *
     * @return Long
     */
    public Long getUid() {

        return personUid;
    }

    /**
     *
     * @return String
     */
    public String getSuperclass() {

        return NEDSSConstants.CLASSTYPE_ENTITY;
    }

	public String getChildrenInHouseNbrStr() {
		if(childrenInHouseNbr==null){
			childrenInHouseNbrStr=null;
			return childrenInHouseNbrStr;
		}
			else{
				childrenInHouseNbrStr= childrenInHouseNbr.toString();
				return  childrenInHouseNbrStr;
			}
	}

	public void setChildrenInHouseNbrStr(String childrenInHouseNbrStr) {
		this.childrenInHouseNbrStr=childrenInHouseNbrStr;
		if(childrenInHouseNbrStr==null || childrenInHouseNbrStr.trim().equalsIgnoreCase("")){
			childrenInHouseNbr=null;
		}
		else{
				childrenInHouseNbr= new Integer(childrenInHouseNbrStr);
			}
	}

	public String getBirthOrderNbrStr() {
		if(birthOrderNbr==null){
			birthOrderNbrStr=null;
			return birthOrderNbrStr;
		}
			else{
				birthOrderNbrStr= birthOrderNbr.toString();
				return  birthOrderNbrStr;
			}
	}

	public void setBirthOrderNbrStr(String birthOrderNbrStr) {
		this.birthOrderNbrStr=birthOrderNbrStr;
		if(birthOrderNbrStr==null || birthOrderNbrStr.trim().equalsIgnoreCase("")){
			birthOrderNbr=null;
		}
		else{
			birthOrderNbr= new Integer(birthOrderNbrStr);
			}
	}

	public String getAdultsInHouseNbrStr() {
		if(adultsInHouseNbr==null){
			adultsInHouseNbrStr=null;
			return adultsInHouseNbrStr;
		}
			else{
				adultsInHouseNbrStr= adultsInHouseNbr.toString();
				return  adultsInHouseNbrStr;
			}
	}

	public void setAdultsInHouseNbrStr(String adultsInHouseNbrStr) {
		this.adultsInHouseNbrStr=adultsInHouseNbrStr;
		if(adultsInHouseNbrStr==null || adultsInHouseNbrStr.trim().equalsIgnoreCase("")){
			adultsInHouseNbr=null;
		}
		else{
			adultsInHouseNbr= new Integer(adultsInHouseNbrStr);
			}
	}

	public String getEdxInd() {
		return edxInd;
	}

	public void setEdxInd(String edxInd) {
		this.edxInd = edxInd;
	}

	/**
	 * @return the speaksEnglishCd
	 */
	public String getSpeaksEnglishCd() {
		return speaksEnglishCd;
	}

	/**
	 * @param speaksEnglishCd the speaksEnglishCd to set
	 */
	public void setSpeaksEnglishCd(String speaksEnglishCd) {
		this.speaksEnglishCd = speaksEnglishCd;
	}

	/**
	 * @return the additionalGenderCd
	 */
	public String getAdditionalGenderCd() {
		return additionalGenderCd;
	}

	/**
	 * @param additionalGenderCd the additionalGenderCd to set
	 */
	public void setAdditionalGenderCd(String additionalGenderCd) {
		this.additionalGenderCd = additionalGenderCd;
	}

	/**
	 * @return the eharsId
	 */
	public String getEharsId() {
		return eharsId;
	}

	/**
	 * @param eharsId the eharsId to set
	 */
	public void setEharsId(String eharsId) {
		this.eharsId = eharsId;
	}

	public String getEthnicUnkReasonCd() {
		return ethnicUnkReasonCd;
	}

	public void setEthnicUnkReasonCd(String ethnicUnkReasonCd) {
		this.ethnicUnkReasonCd = ethnicUnkReasonCd;
	}

	public String getSexUnkReasonCd() {
		return sexUnkReasonCd;
	}

	public void setSexUnkReasonCd(String sexUnkReasonCd) {
		this.sexUnkReasonCd = sexUnkReasonCd;
	}
	
	
	// Convenient method to get full name. 
	// Consistent acrosss all the places.
	
	public String getFullName()
	{
	    String n = StringUtils.combine(new String[]{ getLastNm(),  getFirstNm() }, ",");
        String n1 = StringUtils.combine(new String[]{n, getMiddleNm()}," " );
        return StringUtils.combine(new String[]{n1, getNmSuffix()},"," );
	}
	
}