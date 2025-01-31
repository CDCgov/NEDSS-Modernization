package gov.cdc.nedss.entity.person.dt;
import gov.cdc.nedss.util.*;
import java.sql.Timestamp;
import gov.cdc.nedss.systemservice.util.*;

/**
* Name:		    PersonHistDT
* Description:	PersonHist data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/

public class PersonHistDT
    extends AbstractVO
    implements RootDTInterface{
	private static final long serialVersionUID = 1L;
    private Long personUid;
    private Long personParentUid;
    private Integer versionCtrlNbr;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String administrativeGenderCd;
    private Integer ageCalc;
    private Timestamp ageCalcTime;
    private String ageCalcUnitCd;
    private String ageCategoryCd;
    private String ageReported;
    private Timestamp ageReportedTime;
    private String ageReportedUnitCd;
    private Timestamp asOfDateAdmin;
    private Timestamp asOfDateEthnicity;
    private Timestamp asOfDateGeneral;
    private Timestamp asOfDateMorbidity;
    private Timestamp asOfDateSex;
    private String birthGenderCd;
    private Integer birthOrderNbr;
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
    private Integer ethnicityGroupSeqNbr;
    private Integer adultsInHouseNbr;
    private Integer childrenInHouseNbr;
    private String birthCityCd;
    private String birthCityDescTxt;
    private String birthCntryCd;
    private String birthStateCd;
    private String raceDescTxt;
    private String ethnicGroupDescTxt;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;

    /**
     * access method for personUid
     * @return  Long personUid
     */
    public Long getPersonUid() {

        return personUid;
    }

    /**
     * sets personUid
     * @param Long aPersonUid
     */
    public void setPersonUid(Long aPersonUid) {
        personUid = aPersonUid;
        setItDirty(true);
    }
	/**
 	* gets personParentUid
 	* @return Long
 	*/
	public Long getPersonParentUid(){
		return this.personParentUid;
	}

	/**
	 * sets personParentUid
	 * @param aPersonParentUid
	 */
	public void setPersonParentUid(Long aPersonParentUid){
		this.personParentUid = aPersonParentUid;
		setItDirty(true);
	}

    /**
     * gets VersionCtrlNbr
     * @return Integer versionCtrlNbr
     */
    public Integer getVersionCtrlNbr() {

        return versionCtrlNbr;
    }

    /**
     * sets versionCtrlNbr
     * @param Integer aVersionCtrlNbr
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr) {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
     * gets addReasonCd
     * @return String addReasonCd
     */
    public String getAddReasonCd() {

        return addReasonCd;
    }

    /**
     * sets addReasonCd
     * @param String aAddReasonCd
     */
    public void setAddReasonCd(String aAddReasonCd) {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     * gets addTime
     * @return Timestamp addTime
     */
    public Timestamp getAddTime() {

        return addTime;
    }

    /**
     * sets addTime
     * @param Timestamp aAddTime
     */
    public void setAddTime(Timestamp aAddTime) {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     * sets AddTime_s
     * converts string to timestamp
     * @param String strTime
     */
    public void setAddTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets addUserId
     * @return Long addUserId
     */
    public Long getAddUserId() {

        return addUserId;
    }

    /**
     * sets addUserId
     * @param Long aAddUserId
     */
    public void setAddUserId(Long aAddUserId) {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    /**
     * gets administrativeGenderCd
     * @return String administrativeGenderCd
     */
    public String getAdministrativeGenderCd() {

        return administrativeGenderCd;
    }

    /**
     * sets administrativeGenderCd
     * @param String aAdministrativeGenderCd
     */
    public void setAdministrativeGenderCd(String aAdministrativeGenderCd) {
        administrativeGenderCd = aAdministrativeGenderCd;
        setItDirty(true);
    }

    /**
     * gets ageCalc
     * @return Integer ageCalc
     */
    public Integer getAgeCalc() {

        return ageCalc;
    }

    /**
     *  sets ageCalc
     * @param Integer aAgeCalc
     */
    public void setAgeCalc(Integer aAgeCalc) {
        ageCalc = aAgeCalc;
        setItDirty(true);
    }

    /**
     * gets ageCalcTime
     * @return Timestamp ageCalcTime
     */
    public Timestamp getAgeCalcTime() {

        return ageCalcTime;
    }

    /**
     * sets ageCalcTime
     * @param Timestamp ageCalcTime
     */
    public void setAgeCalcTime(Timestamp aAgeCalcTime) {
        ageCalcTime = aAgeCalcTime;
        setItDirty(true);
    }

    /**
     * sets AgeCalcTime_s
     * takes a String and converts to a Timestamp
     * @param String strTime
     */
    public void setAgeCalcTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setAgeCalcTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets ageCalcUnitCd
     * @return String ageCalcUnitCd
     */
    public String getAgeCalcUnitCd() {

        return ageCalcUnitCd;
    }

    /**
     * sets ageCalcUnitCd
     * @param String ageCalcUnitCd
     */
    public void setAgeCalcUnitCd(String aAgeCalcUnitCd) {
        ageCalcUnitCd = aAgeCalcUnitCd;
        setItDirty(true);
    }

    /**
     * getsc ageCategoryCd
     * @return String ageCategoryCd
     */
    public String getAgeCategoryCd() {

        return ageCategoryCd;
    }

    /**
     * sets ageCategoryCd
     * @param String ageCategoryCd
     */
    public void setAgeCategoryCd(String aAgeCategoryCd) {
        ageCategoryCd = aAgeCategoryCd;
        setItDirty(true);
    }

    /**
     * gets ageReported
     * @return String ageReported
     */
    public String getAgeReported() {

        return ageReported;
    }

    /**
     * sets ageReported
     * @param String ageReported
     */
    public void setAgeReported(String aAgeReported) {
        ageReported = aAgeReported;
        setItDirty(true);
    }

    /**
     * gets ageReportedTime
     * @return Timestamp ageReportedTime
     */
    public Timestamp getAgeReportedTime() {

        return ageReportedTime;
    }

    /**
     * sets ageReportedTime
     * @param Timestamp ageReportedTime
     */
    public void setAgeReportedTime(Timestamp aAgeReportedTime) {
        ageReportedTime = aAgeReportedTime;
        setItDirty(true);
    }

    /**
     * sets ageReportedTime_s
     * takes a Sting and converts to a Timestamp
     * @param String strTime
     */
    public void setAgeReportedTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setAgeReportedTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets ageReportedUnitCd
     * @return String ageReportedUnitCd
     */
    public String getAgeReportedUnitCd() {

        return ageReportedUnitCd;
    }

    /**
     * sets ageReportedUnitCd
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
		return this.asOfDateAdmin;
	}

	/**
	 * 
	 * @param aAsOfDateAdmin
	 */
	public void setAsOfDateAdmin(Timestamp aAsOfDateAdmin){
		this.asOfDateAdmin = aAsOfDateAdmin;
		setItDirty(true);
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
	 */
	public void setAsOfDateEthinicity(Timestamp aAsOfDateEthnicity){
		this.asOfDateEthnicity = aAsOfDateEthnicity;
		setItDirty(true);
	}
	
	/**
	 * gets asOfDateGeneral
	 * @return Timestamp
	 */
	public Timestamp getAsOfDateGeneral(){
		return this.asOfDateGeneral;
	}
	
	/**
	 * sets asOfDateGeneral
	 * @param aAsOfDateGeneral
	 */
	public void setAsOfDateGeneral(Timestamp aAsOfDateGeneral){
		this.asOfDateGeneral = aAsOfDateGeneral;
		setItDirty(true);
	}
	
	/**
	 * 
	 * @return Timestamp
	 */
	public Timestamp getAsOfDateMorbidity(){
		return this.asOfDateMorbidity;
	}

	/**
	 * sets asOfDateMorbidity
	 * @param aAsOfDateMorbidity
	 */
	public void setAsOfDateMorbidity(Timestamp aAsOfDateMorbidity){
		this.asOfDateMorbidity = aAsOfDateMorbidity;
		setItDirty(true);
	}

	/**
	 * gets asOfDateSex
	 * @return Timestamp
	 */
	public Timestamp getAsOfDateSex(){
		return this.asOfDateSex;
	}


	/**
	 * sets asOfDateSex
	 * @param aAsOfDateSex
	 */
	public void setAsOfDateSex(Timestamp aAsOfDateSex){
		this.asOfDateSex = aAsOfDateSex;
		setItDirty(true);
	}


    /**
     * gets birthGenderCd
     * @return String birthGenderCd
     */
    public String getBirthGenderCd() {

        return birthGenderCd;
    }

    /**
     * setBirthGenderCd
     * @param Steing birthGenderCd
     */
    public void setBirthGenderCd(String aBirthGenderCd) {
        birthGenderCd = aBirthGenderCd;
        setItDirty(true);
    }

    /**
     * gets birthOrderNbr
     * @return Integer birthOrderNbr
     */
    public Integer getBirthOrderNbr() {

        return birthOrderNbr;
    }

    /**
     * sets birthOrderNbr
     * @param Integer birthOrderNbr
     */
    public void setBirthOrderNbr(Integer aBirthOrderNbr) {
        birthOrderNbr = aBirthOrderNbr;
        setItDirty(true);
    }

    /**
     * gets birthTime
     * @return Timestamp birthTime
     */
    public Timestamp getBirthTime() {

        return birthTime;
    }

    /**
     * sets BirthTime
     * @param Timestamp birthTime
     */
    public void setBirthTime(Timestamp aBirthTime) {
        birthTime = aBirthTime;
        setItDirty(true);
    }

    /**
     * sets birthTime
     * takes a String and converts to Timestamp
     * @param String strTime
     */
    public void setBirthTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setBirthTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets birthTimeCalc
     * @return Timestamp birthTimeCalc
     */
    public Timestamp getBirthTimeCalc() {

        return birthTimeCalc;
    }

    /**
     * sets birthTimeCalc
     * @param aBirthTimeCalc
     */
    public void setBirthTimeCalc(Timestamp aBirthTimeCalc) {
        birthTimeCalc = aBirthTimeCalc;
        setItDirty(true);
    }

    /**
     * sets birthTimeCalc
     * takes a String and converts to a Timestamp
     * @param String strTime
     */
    public void setBirthTimeCalc_s(String strTime) {

        if (strTime == null)

            return;

        this.setBirthTimeCalc(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets cd
     * @return String cd
     */
    public String getCd() {

        return cd;
    }

    /**
     * sets cd
     * @param String aCd
     */
    public void setCd(String aCd) {
        cd = aCd;
        setItDirty(true);
    }

    /**
     * gets cdDescTxt
     * @return String cdDescTxt
     */
    public String getCdDescTxt() {

        return cdDescTxt;
    }

    /**
     * sets cdDescTxt
     * @param String cdDescTxt
     */
    public void setCdDescTxt(String aCdDescTxt) {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

    /**
     * gets currSexCd
     * @return String currSexCd
     */
    public String getCurrSexCd() {

        return currSexCd;
    }

    /**
     * sets currSexCd
     * @param String currSexCd
     */
    public void setCurrSexCd(String aCurrSexCd) {
        currSexCd = aCurrSexCd;
        setItDirty(true);
    }

    /**
     * gets deceasedIndCd
     * @return String deceasedIndCd
     */
    public String getDeceasedIndCd() {

        return deceasedIndCd;
    }

    /**
     * sets deceasedIndCd
     * @param String deceasedIndCd
     */
    public void setDeceasedIndCd(String aDeceasedIndCd) {
        deceasedIndCd = aDeceasedIndCd;
        setItDirty(true);
    }

    /**
     * gets deceasedTime
     * @return Timestamp deceasedTime
     */
    public Timestamp getDeceasedTime() {

        return deceasedTime;
    }

    /**
     * sets deceasedTime
     * @param Timestamp deceasedTime
     */
    public void setDeceasedTime(Timestamp aDeceasedTime) {
        deceasedTime = aDeceasedTime;
        setItDirty(true);
    }

    /**
     * sets deceasedTime
     * takes a String and converts to a Timestamp
     * @param String strTime
     */
    public void setDeceasedTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setDeceasedTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets description
     * @return String description
     */
    public String getDescription() {

        return description;
    }

    /**
     * sets description
     * @param String description
     */
    public void setDescription(String aDescription) {
        description = aDescription;
        setItDirty(true);
    }

    /**
     * gets educationLevelCd
     * @return String educationLevelCd
     */
    public String getEducationLevelCd() {

        return educationLevelCd;
    }

    /**
     * sets educationLevelCd
     * @param String educationLevelCd
     */
    public void setEducationLevelCd(String aEducationLevelCd) {
        educationLevelCd = aEducationLevelCd;
        setItDirty(true);
    }

    /**
     * gets educationLevelDescTxt
     * @return String educationLevelDescTxt
     */
    public String getEducationLevelDescTxt() {

        return educationLevelDescTxt;
    }

    /**
     * sets educationLevelDescTxt
     * @param String educationLevelDescTxt
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
	 * @param aElectronicUid
	 */
	public void setElectronicInd(String aElectronicUid){
		this.electronicInd = aElectronicUid;
		setItDirty(true);
	}

    /**
     * gets ethnicGroupInd
     * @return String ethnicGroupInd
     */
    public String getEthnicGroupInd() {

        return ethnicGroupInd;
    }

    /**
     * sets ethnicGroupInd
     * @param Strign ethnicGroupInd
     */
    public void setEthnicGroupInd(String aEthnicGroupInd) {
        ethnicGroupInd = aEthnicGroupInd;
        setItDirty(true);
    }

    /**
     * gets lastChgReasonCd
     * @return String lastChgReasonCd
     */
    public String getLastChgReasonCd() {

        return lastChgReasonCd;
    }

    /**
     * sets lastChgReasonCd
     * @param String lastChgReasonCd
     */
    public void setLastChgReasonCd(String aLastChgReasonCd) {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     * gets lastChgTime
     * @return Timestamp lastChgTime
     */
    public Timestamp getLastChgTime() {

        return lastChgTime;
    }

    /**
     * sets lastChgTime
     * @param Timestamp lastChgTime
     */
    public void setLastChgTime(Timestamp aLastChgTime) {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     * sets lastChgTime
     * takes a String adn converts to Timestamp
     * @param String strTime
     */
    public void setLastChgTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets lastChgUserId
     * @return Long lastChgUserId
     */
    public Long getLastChgUserId() {

        return lastChgUserId;
    }

   /**
    * Sets the value of the lastChgUserId property.
    *
    * @param aLastChgUserId the new value of the lastChgUserId property
    */
    public void setLastChgUserId(Long aLastChgUserId) {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    /**
     * gets localId
     * @return String localId
     */
    public String getLocalId() {

        return localId;
    }

    /**
     * sets localId
     * @param String localId
     */
    public void setLocalId(String aLocalId) {
        localId = aLocalId;
        setItDirty(true);
    }

   /**
    * Access method for the maritalStatusCd property.
    *
    * @return   the current value of the maritalStatusCd property
    */
    public String getMaritalStatusCd() {

        return maritalStatusCd;
    }

   /**
    * Sets the value of the maritalStatusCd property.
    *
    * @param aMaritalStatusCd the new value of the maritalStatusCd property
    */
    public void setMaritalStatusCd(String aMaritalStatusCd) {
        maritalStatusCd = aMaritalStatusCd;
        setItDirty(true);
    }

   /**
    * Access method for the maritalStatusDescTxt property.
    *
    * @return   the current value of the maritalStatusDescTxt property
    */
    public String getMaritalStatusDescTxt() {

        return maritalStatusDescTxt;
    }

   /**
    * Sets the value of the maritalStatusDescTxt property.
    *
    * @param aMaritalStatusDescTxt the new value of the maritalStatusDescTxt property
    */
    public void setMaritalStatusDescTxt(String aMaritalStatusDescTxt) {
        maritalStatusDescTxt = aMaritalStatusDescTxt;
        setItDirty(true);
    }

   /**
    * Access method for the mothersMaidenNm property.
    *
    * @return   the current value of the mothersMaidenNm property
    */
   public String getMothersMaidenNm()
   {
      return mothersMaidenNm;
   }

   /**
    * Sets the value of the mothersMaidenNm property.
    *
    * @param aMothersMaidenNm the new value of the mothersMaidenNm property
    */
    public void setMothersMaidenNm(String aMothersMaidenNm) {
        mothersMaidenNm = aMothersMaidenNm;
        setItDirty(true);
    }

   /**
    * Access method for the multipleBirthInd property.
    *
    * @return   the current value of the multipleBirthInd property
    */
   public String getMultipleBirthInd()
   {
      return multipleBirthInd;
   }

   /**
    * Sets the value of the multipleBirthInd property.
    *
    * @param aMultipleBirthInd the new value of the multipleBirthInd property
    */
    public void setMultipleBirthInd(String aMultipleBirthInd) {
        multipleBirthInd = aMultipleBirthInd;
        setItDirty(true);
    }

   /**
    * Access method for the occupationCd property.
    *
    * @return   the current value of the occupationCd property
    */
   public String getOccupationCd()
   {
      return occupationCd;
   }

   /**
    * Sets the value of the occupationCd property.
    *
    * @param aOccupationCd the new value of the occupationCd property
    */
    public void setOccupationCd(String aOccupationCd) {
        occupationCd = aOccupationCd;
        setItDirty(true);
    }

   /**
    * Access method for the preferredGenderCd property.
    *
    * @return   the current value of the preferredGenderCd property
    */
   public String getPreferredGenderCd()
   {
      return preferredGenderCd;
   }

   /**
    * Sets the value of the preferredGenderCd property.
    *
    * @param aPreferredGenderCd the new value of the preferredGenderCd property
    */
    public void setPreferredGenderCd(String aPreferredGenderCd) {
        preferredGenderCd = aPreferredGenderCd;
        setItDirty(true);
    }

   /**
    * Access method for the primLangCd property.
    *
    * @return   the current value of the primLangCd property
    */
   public String getPrimLangCd()
   {
      return primLangCd;
   }

   /**
    * Sets the value of the primLangCd property.
    *
    * @param aPrimLangCd the new value of the primLangCd property
    */
    public void setPrimLangCd(String aPrimLangCd) {
        primLangCd = aPrimLangCd;
        setItDirty(true);
    }

   /**
    * Access method for the primLangDescTxt property.
    *
    * @return   the current value of the primLangDescTxt property
    */
   public String getPrimLangDescTxt()
   {
      return primLangDescTxt;
   }

   /**
    * Sets the value of the primLangDescTxt property.
    *
    * @param aPrimLangDescTxt the new value of the primLangDescTxt property
    */
    public void setPrimLangDescTxt(String aPrimLangDescTxt) {
        primLangDescTxt = aPrimLangDescTxt;
        setItDirty(true);
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
    public void setRecordStatusCd(String aRecordStatusCd) {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
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
    public void setRecordStatusTime(Timestamp aRecordStatusTime) {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

   /**
    * Sets the value of the recordStatusTime property.
    * takes a String and converts to Timestamp
    * @param aRecordStatusTime the new value of the recordStatusTime property
    */
    public void setRecordStatusTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
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
    public void setStatusCd(String aStatusCd) {
        statusCd = aStatusCd;
        setItDirty(true);
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
    public void setStatusTime(Timestamp aStatusTime) {
        statusTime = aStatusTime;
        setItDirty(true);
    }

   /**
    * Sets the value of the statusTime property.
    * takes a String and converts to Timestamp
    * @param aStatusTime the new value of the statusTime property
    */
    public void setStatusTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

   /**
    * Access method for the survivedIndCd property.
    *
    * @return   the current value of the survivedIndCd property
    */
   public String getSurvivedIndCd()
   {
      return survivedIndCd;
   }

   /**
    * Sets the value of the survivedIndCd property.
    *
    * @param aSurvivedIndCd the new value of the survivedIndCd property
    */
    public void setSurvivedIndCd(String aSurvivedIndCd) {
        survivedIndCd = aSurvivedIndCd;
        setItDirty(true);
    }

   /**
    * Access method for the userAffiliationTxt property.
    *
    * @return   the current value of the userAffiliationTxt property
    */
   public String getUserAffiliationTxt()
   {
      return userAffiliationTxt;
   }

   /**
    * Sets the value of the userAffiliationTxt property.
    *
    * @param aUserAffiliationTxt the new value of the userAffiliationTxt property
    */
    public void setUserAffiliationTxt(String aUserAffiliationTxt) {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

   /**
    * Access method for the firstNm property.
    *
    * @return   the current value of the firstNm property
    */
   public String getFirstNm()
   {
      return firstNm;
   }

   /**
    * Sets the value of the firstNm property.
    *
    * @param aFirstNm the new value of the firstNm property
    */
    public void setFirstNm(String aFirstNm) {
        firstNm = aFirstNm;
        setItDirty(true);
    }

   /**
    * Access method for the lastNm property.
    *
    * @return   the current value of the lastNm property
    */
   public String getLastNm()
   {
      return lastNm;
   }

   /**
    * Sets the value of the lastNm property.
    *
    * @param aLastNm the new value of the lastNm property
    */
    public void setLastNm(String aLastNm) {
        lastNm = aLastNm;
        setItDirty(true);
    }

   /**
    * Access method for the middleNm property.
    *
    * @return   the current value of the middleNm property
    */
   public String getMiddleNm()
   {
      return middleNm;
   }

   /**
    * Sets the value of the middleNm property.
    *
    * @param aMiddleNm the new value of the middleNm property
    */
    public void setMiddleNm(String aMiddleNm) {
        middleNm = aMiddleNm;
        setItDirty(true);
    }

   /**
    * Access method for the nmPrefix property.
    *
    * @return   the current value of the nmPrefix property
    */
   public String getNmPrefix()
   {
      return nmPrefix;
   }

   /**
    * Sets the value of the nmPrefix property.
    *
    * @param aNmPrefix the new value of the nmPrefix property
    */
    public void setNmPrefix(String aNmPrefix) {
        nmPrefix = aNmPrefix;
        setItDirty(true);
    }

   /**
    * Access method for the nmSuffix property.
    *
    * @return   the current value of the nmSuffix property
    */
   public String getNmSuffix()
   {
      return nmSuffix;
   }

   /**
    * Sets the value of the nmSuffix property.
    *
    * @param aNmSuffix the new value of the nmSuffix property
    */
    public void setNmSuffix(String aNmSuffix) {
        nmSuffix = aNmSuffix;
        setItDirty(true);
    }

   /**
    * Access method for the preferredNm property.
    *
    * @return   the current value of the preferredNm property
    */
   public String getPreferredNm()
   {
      return preferredNm;
   }

   /**
    * Sets the value of the preferredNm property.
    *
    * @param aPreferredNm the new value of the preferredNm property
    */
    public void setPreferredNm(String aPreferredNm) {
        preferredNm = aPreferredNm;
        setItDirty(true);
    }

   /**
    * Access method for the hmStreetAddr1 property.
    *
    * @return   the current value of the hmStreetAddr1 property
    */
   public String getHmStreetAddr1()
   {
      return hmStreetAddr1;
   }

   /**
    * Sets the value of the hmStreetAddr1 property.
    *
    * @param aHmStreetAddr1 the new value of the hmStreetAddr1 property
    */
    public void setHmStreetAddr1(String aHmStreetAddr1) {
        hmStreetAddr1 = aHmStreetAddr1;
        setItDirty(true);
    }

   /**
    * Access method for the hmStreetAddr2 property.
    *
    * @return   the current value of the hmStreetAddr2 property
    */
   public String getHmStreetAddr2()
   {
      return hmStreetAddr2;
   }

   /**
    * Sets the value of the hmStreetAddr2 property.
    *
    * @param aHmStreetAddr2 the new value of the hmStreetAddr2 property
    */
    public void setHmStreetAddr2(String aHmStreetAddr2) {
        hmStreetAddr2 = aHmStreetAddr2;
        setItDirty(true);
    }

   /**
    * Access method for the hmCityCd property.
    *
    * @return   the current value of the hmCityCd property
    */
   public String getHmCityCd()
   {
      return hmCityCd;
   }

   /**
    * Sets the value of the hmCityCd property.
    *
    * @param aHmCityCd the new value of the hmCityCd property
    */
    public void setHmCityCd(String aHmCityCd) {
        hmCityCd = aHmCityCd;
        setItDirty(true);
    }

   /**
    * Access method for the hmCityDescTxt property.
    *
    * @return   the current value of the hmCityDescTxt property
    */
   public String getHmCityDescTxt()
   {
      return hmCityDescTxt;
   }

   /**
    * Sets the value of the hmCityDescTxt property.
    *
    * @param aHmCityDescTxt the new value of the hmCityDescTxt property
    */
    public void setHmCityDescTxt(String aHmCityDescTxt) {
        hmCityDescTxt = aHmCityDescTxt;
        setItDirty(true);
    }

   /**
    * Access method for the hmStateCd property.
    *
    * @return   the current value of the hmStateCd property
    */
   public String getHmStateCd()
   {
      return hmStateCd;
   }

   /**
    * Sets the value of the hmStateCd property.
    *
    * @param aHmStateCd the new value of the hmStateCd property
    */
    public void setHmStateCd(String aHmStateCd) {
        hmStateCd = aHmStateCd;
        setItDirty(true);
    }

   /**
    * Access method for the hmZipCd property.
    *
    * @return   the current value of the hmZipCd property
    */
   public String getHmZipCd()
   {
      return hmZipCd;
   }

   /**
    * Sets the value of the hmZipCd property.
    *
    * @param aHmZipCd the new value of the hmZipCd property
    */
    public void setHmZipCd(String aHmZipCd) {
        hmZipCd = aHmZipCd;
        setItDirty(true);
    }

   /**
    * Access method for the hmCntyCd property.
    *
    * @return   the current value of the hmCntyCd property
    */
   public String getHmCntyCd()
   {
      return hmCntyCd;
   }

   /**
    * Sets the value of the hmCntyCd property.
    *
    * @param aHmCntyCd the new value of the hmCntyCd property
    */
    public void setHmCntyCd(String aHmCntyCd) {
        hmCntyCd = aHmCntyCd;
        setItDirty(true);
    }

   /**
    * Access method for the hmCntryCd property.
    *
    * @return   the current value of the hmCntryCd property
    */
   public String getHmCntryCd()
   {
      return hmCntryCd;
   }

   /**
    * Sets the value of the hmCntryCd property.
    *
    * @param aHmCntryCd the new value of the hmCntryCd property
    */
    public void setHmCntryCd(String aHmCntryCd) {
        hmCntryCd = aHmCntryCd;
        setItDirty(true);
    }

   /**
    * Access method for the hmPhoneNbr property.
    *
    * @return   the current value of the hmPhoneNbr property
    */
   public String getHmPhoneNbr()
   {
      return hmPhoneNbr;
   }

   /**
    * Sets the value of the hmPhoneNbr property.
    *
    * @param aHmPhoneNbr the new value of the hmPhoneNbr property
    */
    public void setHmPhoneNbr(String aHmPhoneNbr) {
        hmPhoneNbr = aHmPhoneNbr;
        setItDirty(true);
    }

   /**
    * Access method for the hmPhoneCntryCd property.
    *
    * @return   the current value of the hmPhoneCntryCd property
    */
   public String getHmPhoneCntryCd()
   {
      return hmPhoneCntryCd;
   }

   /**
    * Sets the value of the hmPhoneCntryCd property.
    *
    * @param aHmPhoneCntryCd the new value of the hmPhoneCntryCd property
    */
    public void setHmPhoneCntryCd(String aHmPhoneCntryCd) {
        hmPhoneCntryCd = aHmPhoneCntryCd;
        setItDirty(true);
    }

   /**
    * Access method for the hmEmailAddr property.
    *
    * @return   the current value of the hmEmailAddr property
    */
   public String getHmEmailAddr()
   {
      return hmEmailAddr;
   }

   /**
    * Sets the value of the hmEmailAddr property.
    *
    * @param aHmEmailAddr the new value of the hmEmailAddr property
    */
    public void setHmEmailAddr(String aHmEmailAddr) {
        hmEmailAddr = aHmEmailAddr;
        setItDirty(true);
    }

   /**
    * Access method for the cellPhoneNbr property.
    *
    * @return   the current value of the cellPhoneNbr property
    */
   public String getCellPhoneNbr()
   {
      return cellPhoneNbr;
   }

   /**
    * Sets the value of the cellPhoneNbr property.
    *
    * @param aCellPhoneNbr the new value of the cellPhoneNbr property
    */
    public void setCellPhoneNbr(String aCellPhoneNbr) {
        cellPhoneNbr = aCellPhoneNbr;
        setItDirty(true);
    }

   /**
    * Access method for the wkStreetAddr1 property.
    *
    * @return   the current value of the wkStreetAddr1 property
    */
   public String getWkStreetAddr1()
   {
      return wkStreetAddr1;
   }

   /**
    * Sets the value of the wkStreetAddr1 property.
    *
    * @param aWkStreetAddr1 the new value of the wkStreetAddr1 property
    */
    public void setWkStreetAddr1(String aWkStreetAddr1) {
        wkStreetAddr1 = aWkStreetAddr1;
        setItDirty(true);
    }

   /**
    * Access method for the wkStreetAddr2 property.
    *
    * @return   the current value of the wkStreetAddr2 property
    */
   public String getWkStreetAddr2()
   {
      return wkStreetAddr2;
   }

   /**
    * Sets the value of the wkStreetAddr2 property.
    *
    * @param aWkStreetAddr1 the new value of the wkStreetAddr2 property
    */
    public void setWkStreetAddr2(String aWkStreetAddr2) {
        wkStreetAddr2 = aWkStreetAddr2;
        setItDirty(true);
    }

   /**
    * Access method for the wkCityCd property.
    *
    * @return   the current value of the wkCityCd property
    */
   public String getWkCityCd()
   {
      return wkCityCd;
   }

   /**
    * Sets the value of the wkCityCd property.
    *
    * @param aWkCityCd the new value of the wkCityCd property
    */
    public void setWkCityCd(String aWkCityCd) {
        wkCityCd = aWkCityCd;
        setItDirty(true);
    }

   /**
    * Access method for the wkCityDescTxt property.
    *
    * @return   the current value of the wkCityDescTxt property
    */
   public String getWkCityDescTxt()
   {
      return wkCityDescTxt;
   }

   /**
    * Sets the value of the wkCityDescTxt property.
    *
    * @param aWkCityDescTxt the new value of the wkCityDescTxt property
    */
    public void setWkCityDescTxt(String aWkCityDescTxt) {
        wkCityDescTxt = aWkCityDescTxt;
        setItDirty(true);
    }

   /**
    * Access method for the wkStateCd property.
    *
    * @return   the current value of the wkStateCd property
    */
   public String getWkStateCd()
   {
      return wkStateCd;
   }

   /**
    * Sets the value of the wkStateCd property.
    *
    * @param aWkStateCd the new value of the wkStateCd property
    */
    public void setWkStateCd(String aWkStateCd) {
        wkStateCd = aWkStateCd;
        setItDirty(true);
    }

   /**
    * Access method for the wkZipCd property.
    *
    * @return   the current value of the wkZipCd property
    */
   public String getWkZipCd()
   {
      return wkZipCd;
   }

   /**
    * Sets the value of the wkZipCd property.
    *
    * @param aWkZipCd the new value of the wkZipCd property
    */
    public void setWkZipCd(String aWkZipCd) {
        wkZipCd = aWkZipCd;
        setItDirty(true);
    }

   /**
    * Access method for the wkCntyCd property.
    *
    * @return   the current value of the wkCntyCd property
    */
   public String getWkCntyCd()
   {
      return wkCntyCd;
   }

   /**
    * Sets the value of the wkCntyCd property.
    *
    * @param aWkCntyCd the new value of the wkCntyCd property
    */
    public void setWkCntyCd(String aWkCntyCd) {
        wkCntyCd = aWkCntyCd;
        setItDirty(true);
    }

   /**
    * Access method for the wkCntryCd property.
    *
    * @return   the current value of the wkCntryCd property
    */
   public String getWkCntryCd()
   {
      return wkCntryCd;
   }

   /**
    * Sets the value of the wkCntryCd property.
    *
    * @param aWkCntryCd the new value of the wkCntryCd property
    */
    public void setWkCntryCd(String aWkCntryCd) {
        wkCntryCd = aWkCntryCd;
        setItDirty(true);
    }

   /**
    * Access method for the wkPhoneNbr property.
    *
    * @return   the current value of the wkPhoneNbr property
    */
   public String getWkPhoneNbr()
   {
      return wkPhoneNbr;
   }

   /**
    * Sets the value of the wkPhoneNbr property.
    *
    * @param aWkPhoneNbr the new value of the wkPhoneNbr property
    */
    public void setWkPhoneNbr(String aWkPhoneNbr) {
        wkPhoneNbr = aWkPhoneNbr;
        setItDirty(true);
    }

   /**
    * Access method for the wkPhoneCntryCd property.
    *
    * @return   the current value of the wkPhoneCntryCd property
    */
   public String getWkPhoneCntryCd()
   {
      return wkPhoneCntryCd;
   }

   /**
    * Sets the value of the wkPhoneCntryCd property.
    *
    * @param aWkPhoneCntryCd the new value of the wkPhoneCntryCd property
    */
    public void setWkPhoneCntryCd(String aWkPhoneCntryCd) {
        wkPhoneCntryCd = aWkPhoneCntryCd;
        setItDirty(true);
    }

   /**
    * Access method for the wkEmailAddr property.
    *
    * @return   the current value of the wkEmailAddr property
    */
   public String getWkEmailAddr()
   {
      return wkEmailAddr;
   }

   /**
    * Sets the value of the wkEmailAddr property.
    *
    * @param aWkEmailAddr the new value of the wkEmailAddr property
    */
    public void setWkEmailAddr(String aWkEmailAddr) {
        wkEmailAddr = aWkEmailAddr;
        setItDirty(true);
    }

   /**
    * Access method for the SSN property.
    *
    * @return   the current value of the SSN property
    */
   public String getSSN()
   {
      return SSN;
   }

   /**
    * Sets the value of the SSN property.
    *
    * @param aSSN the new value of the SSN property
    */
    public void setSSN(String aSSN) {
        SSN = aSSN;
        setItDirty(true);
    }

   /**
    * Access method for the medicaidNum property.
    *
    * @return   the current value of the medicaidNum property
    */
   public String getMedicaidNum()
   {
      return medicaidNum;
   }

   /**
    * Sets the value of the medicaidNum property.
    *
    * @param aMedicaidNum the new value of the medicaidNum property
    */
    public void setMedicaidNum(String aMedicaidNum) {
        medicaidNum = aMedicaidNum;
        setItDirty(true);
    }

   /**
    * Access method for the dlNum property.
    *
    * @return   the current value of the dlNum property
    */
   public String getDlNum()
   {
      return dlNum;
   }

   /**
    * Sets the value of the dlNum property.
    *
    * @param aDlNum the new value of the dlNum property
    */
    public void setDlNum(String aDlNum) {
        dlNum = aDlNum;
        setItDirty(true);
    }

   /**
    * Access method for the dlStateCd property.
    *
    * @return   the current value of the dlStateCd property
    */
   public String getDlStateCd()
   {
      return dlStateCd;
   }

   /**
    * Sets the value of the dlStateCd property.
    *
    * @param aDlStateCd the new value of the dlStateCd property
    */
    public void setDlStateCd(String aDlStateCd) {
        dlStateCd = aDlStateCd;
        setItDirty(true);
    }

   /**
    * Access method for the raceCd property.
    *
    * @return   the current value of the raceCd property
    */
   public String getRaceCd()
   {
      return raceCd;
   }

   /**
    * Sets the value of the raceCd property.
    *
    * @param aRaceCd the new value of the raceCd property
    */
    public void setRaceCd(String aRaceCd) {
        raceCd = aRaceCd;
        setItDirty(true);
    }

   /**
    * Access method for the raceSeqNbr property.
    *
    * @return   the current value of the raceSeqNbr property
    */
   public Integer getRaceSeqNbr()
   {
      return raceSeqNbr;
   }

   /**
    * Sets the value of the raceSeqNbr property.
    *
    * @param aRaceSeqNbr the new value of the raceSeqNbr property
    */
    public void setRaceSeqNbr(Integer aRaceSeqNbr) {
        raceSeqNbr = aRaceSeqNbr;
        setItDirty(true);
    }

   /**
    * Access method for the raceCategoryCd property.
    *
    * @return   the current value of the raceCategoryCd property
    */
   public String getRaceCategoryCd()
   {
      return raceCategoryCd;
   }

   /**
    * Sets the value of the raceCategoryCd property.
    *
    * @param aRaceCategoryCd the new value of the raceCategoryCd property
    */
    public void setRaceCategoryCd(String aRaceCategoryCd) {
        raceCategoryCd = aRaceCategoryCd;
        setItDirty(true);
    }

   /**
    * Access method for the ethnicityGroupCd property.
    *
    * @return   the current value of the ethnicityGroupCd property
    */
   public String getEthnicityGroupCd()
   {
      return ethnicityGroupCd;
   }

   /**
    * Sets the value of the ethnicityGroupCd property.
    *
    * @param aEthnicityGroupCd the new value of the ethnicityGroupCd property
    */
    public void setEthnicityGroupCd(String aEthnicityGroupCd) {
        ethnicityGroupCd = aEthnicityGroupCd;
        setItDirty(true);
    }

   /**
    * Access method for the ethnicityGroupSeqNbr property.
    *
    * @return   the current value of the ethnicityGroupSeqNbr property
    */
   public Integer getEthnicityGroupSeqNbr()
   {
      return ethnicityGroupSeqNbr;
   }

   /**
    * Sets the value of the ethnicityGroupSeqNbr property.
    *
    * @param aEthnicityGroupSeqNbr the new value of the ethnicityGroupSeqNbr property
    */
    public void setEthnicityGroupSeqNbr(Integer aEthnicityGroupSeqNbr) {
        ethnicityGroupSeqNbr = aEthnicityGroupSeqNbr;
        setItDirty(true);
    }

   /**
    * Access method for the adultsInHouseNbr property.
    *
    * @return   the current value of the adultsInHouseNbr property
    */
   public Integer getAdultsInHouseNbr()
   {
      return adultsInHouseNbr;
   }

   /**
    * Sets the value of the adultsInHouseNbr property.
    *
    * @param aAdultsInHouseNbr the new value of the adultsInHouseNbr property
    */
    public void setAdultsInHouseNbr(Integer aAdultsInHouseNbr) {
        adultsInHouseNbr = aAdultsInHouseNbr;
        setItDirty(true);
    }

   /**
    * Access method for the childrenInHouseNbr property.
    *
    * @return   the current value of the childrenInHouseNbr property
    */
   public Integer getChildrenInHouseNbr()
   {
      return childrenInHouseNbr;
   }

   /**
    * Sets the value of the childrenInHouseNbr property.
    *
    * @param aChildrenInHouseNbr the new value of the childrenInHouseNbr property
    */
    public void setChildrenInHouseNbr(Integer aChildrenInHouseNbr) {
        childrenInHouseNbr = aChildrenInHouseNbr;
        setItDirty(true);
    }

   /**
    * Access method for the birthCityCd property.
    *
    * @return   the current value of the birthCityCd property
    */
   public String getBirthCityCd()
   {
      return birthCityCd;
   }

   /**
    * Sets the value of the birthCityCd property.
    *
    * @param aBirthCityCd the new value of the birthCityCd property
    */
    public void setBirthCityCd(String aBirthCityCd) {
        birthCityCd = aBirthCityCd;
        setItDirty(true);
    }

   /**
    * Access method for the birthCityDescTxt property.
    *
    * @return   the current value of the birthCityDescTxt property
    */
   public String getBirthCityDescTxt()
   {
      return birthCityDescTxt;
   }

   /**
    * Sets the value of the birthCityDescTxt property.
    *
    * @param aBirthCityDescTxt the new value of the birthCityDescTxt property
    */
    public void setBirthCityDescTxt(String aBirthCityDescTxt) {
        birthCityDescTxt = aBirthCityDescTxt;
        setItDirty(true);
    }

   /**
    * Access method for the birthCntryCd property.
    *
    * @return   the current value of the birthCntryCd property
    */
   public String getBirthCntryCd()
   {
      return birthCntryCd;
   }

   /**
    * Sets the value of the birthCntryCd property.
    *
    * @param aBirthCntryCd the new value of the birthCntryCd property
    */
    public void setBirthCntryCd(String aBirthCntryCd) {
        birthCntryCd = aBirthCntryCd;
        setItDirty(true);
    }

   /**
    * Access method for the birthStateCd property.
    *
    * @return   the current value of the birthStateCd property
    */
   public String getBirthStateCd()
   {
      return birthStateCd;
   }

   /**
    * Sets the value of the birthStateCd property.
    *
    * @param aBirthStateCd the new value of the birthStateCd property
    */
    public void setBirthStateCd(String aBirthStateCd) {
        birthStateCd = aBirthStateCd;
        setItDirty(true);
    }

   /**
    * Access method for the raceDescTxt property.
    *
    * @return   the current value of the raceDescTxt property
    */
   public String getRaceDescTxt()
   {
      return raceDescTxt;
   }

   /**
    * Sets the value of the raceDescTxt property.
    *
    * @param aRaceDescTxt the new value of the raceDescTxt property
    */
    public void setRaceDescTxt(String aRaceDescTxt) {
        raceDescTxt = aRaceDescTxt;
        setItDirty(true);
    }

   /**
    * Access method for the ethnicGroupDescTxt property.
    *
    * @return   the current value of the ethnicGroupDescTxt property
    */
   public String getEthnicGroupDescTxt()
   {
      return ethnicGroupDescTxt;
   }

   /**
    * Sets the value of the ethnicGroupDescTxt property.
    *
    * @param aEthnicGroupDescTxt the new value of the ethnicGroupDescTxt property
    */
    public void setEthnicGroupDescTxt(String aEthnicGroupDescTxt) {
        ethnicGroupDescTxt = aEthnicGroupDescTxt;
        setItDirty(true);
    }

   /**
    * Access method for the progAreaCd property.
    *
    * @return   the current value of the progAreaCd property
    */
    public String getProgAreaCd() {

        return progAreaCd;
    }

   /**
    * Sets the value of the progAreaCd property.
    *
    * @param aProgAreaCd the new value of the progAreaCd property
    */
    public void setProgAreaCd(String aProgAreaCd) {
        progAreaCd = aProgAreaCd;
        setItDirty(true);
    }

   /**
    * Access method for the jurisdictionCd property.
    *
    * @return   the current value of the jurisdictionCd property
    */
    public String getJurisdictionCd() {

        return jurisdictionCd;
    }

   /**
    * Sets the value of the jurisdictionCd property.
    *
    * @param aJurisdictionCd the new value of the jurisdictionCd property
    */
    public void setJurisdictionCd(String aJurisdictionCd) {
        jurisdictionCd = aJurisdictionCd;
        setItDirty(true);
    }

   /**
    * Access method for the programJurisdictionOid property.
    *
    * @return   the current value of the programJurisdictionOid property
    */
    public Long getProgramJurisdictionOid() {

        return programJurisdictionOid;
    }

   /**
    * Sets the value of the programJurisdictionOid property.
    *
    * @param aProgramJurisdictionOid the new value of the programJurisdictionOid property
    */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

   /**
    * Access method for the sharedInd property.
    *
    * @return   the current value of the sharedInd property
    */
    public String getSharedInd() {

        return sharedInd;
    }

   /**
    * Sets the value of the sharedInd property.
    *
    * @param aSharedInd the new value of the sharedInd property
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
        voClass = ((PersonHistDT)objectname1).getClass();

        NedssUtils compareObjs = new NedssUtils();

        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
     * Marks this object as a class that has been modified.
     * @param itDirty
     */
    public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
    }

    /**
     * Returns boolean indicating if this object has been modified.
     * @return boolean itDirty
     */
    public boolean isItDirty() {

        return itDirty;
    }

    /**
     * Sets boolean value indicating if this object is new and that it does not
     * exist in the database.
     * @param itNew
     */
    public void setItNew(boolean itNew) {
      this.itNew = itNew;
    }

    /**
     * Returns boolean indicating if this is a new object.
     * @return itNew  boolean
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     * Marks this object for deletion from the database.s
     * @param itDelete
     */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
    }

    /**
     * Returns boolean indicating if object is marked for deletion from
     * database.
     * @return itDelete boolean
     */
    public boolean isItDelete() {

        return itDelete;
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
}