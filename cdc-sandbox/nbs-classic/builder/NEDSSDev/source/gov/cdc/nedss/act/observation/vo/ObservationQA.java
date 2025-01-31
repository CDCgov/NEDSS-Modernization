package gov.cdc.nedss.act.observation.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import  gov.cdc.nedss.util.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ObservationQA extends AbstractVO{

	private static final long serialVersionUID = 1L;
      private Long observationUid;
      private Long obsCodeUid;
      private Long obsDateUid;
      private Long obsNumericUid;
      private Long obsTxtUid;
      private Integer versionCtrlNbr;
      private String sharedInd;
      private String cd;
      private String ctrlCdDisplayForm;
      private String code;
      private String originalTxt;
      private String codeSystemDescTxt;
      private String durationAmt;
      private String durationUnitCd;
      private Timestamp fromTime;
      private Timestamp toTime;
      private Integer obsValueDateSeq;
      private BigDecimal numericValue1;
      private BigDecimal numericValue2;
      private Integer numericScale1;
      private Integer numericScale2;
      private String numericUnitCd;
      private Integer obsValueNumericSeq;
      private String valueTxt;
      private Integer obsValueTxtSeq;
      private Long sourceActUid;
      private Long targetActUid;
      private String typeCd;
      private String localId;
      private String cdDescTxt;
      private String cdSystemDescTxt;
      private String cdSystemCd;
      private String cdVersion;


  public ObservationQA() {
  }
  public Long getObservationUid() {
        return observationUid;
  }
  public void setObservationUid(Long aObservationUid){
        observationUid = aObservationUid;
        setItDirty(true);
  }

    public Long getObsCodeUid() {
        return obsCodeUid;
  }
  public void setObsCodeUid(Long aObservationUid){
        obsCodeUid = aObservationUid;
        setItDirty(true);
  }
  public Long getObsDateUid() {
        return obsDateUid;
  }
  public void setObsDateUid(Long aObservationUid){
        obsDateUid = aObservationUid;
        setItDirty(true);
  }
  public Long getObsNumericUid() {
        return obsNumericUid;
  }
  public void setObsNumericUid(Long aObservationUid){
        obsNumericUid = aObservationUid;
        setItDirty(true);
  }
  public Long getObsTxtUid() {
        return obsTxtUid;
  }
  public void setObsTxtUid(Long aObservationUid){
        obsTxtUid = aObservationUid;
        setItDirty(true);
  }

  public Integer getVersionCtrlNbr(){
        return versionCtrlNbr;
  }
  public void setVersionCtrlNbr(Integer aVersionCtrlNbr){
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
  }
  public String getSharedInd(){
        return sharedInd;
  }
  public void setSharedInd(String aSharedInd){
        sharedInd = aSharedInd;
        setItDirty(true);
  }


  public String getCd(){
        return cd;
  }
  public void setCd(String aCd){
        cd = aCd;
        setItDirty(true);
  }
  public String getCtrlCdDisplayForm(){
        return ctrlCdDisplayForm;
  }
  public void setCtrlCdDisplayForm(String aCtrlCdDisplayForm){
        ctrlCdDisplayForm = aCtrlCdDisplayForm;
        setItDirty(true);
  }
  public String getCode(){
        return code;
  }
  public void setCode(String aCode){
        code = aCode;
        setItDirty(true);
  }
  public String getOriginalTxt(){
        return originalTxt;
  }
  public void setOriginalTxt(String aOriginalTxt){
        originalTxt = aOriginalTxt;
        setItDirty(true);
  }
  public String getCodeSystemDescTxt(){
        return codeSystemDescTxt;
  }
  public void setCodeSystemDescTxt(String aCodeSystemDescTxt){
        codeSystemDescTxt = aCodeSystemDescTxt;
        setItDirty(true);
  }
  public String getDurationAmt(){
        return durationAmt;
  }
  public void setDurationAmt(String aDurationAmt){
        durationAmt = aDurationAmt;
        setItDirty(true);
  }
  public String getDurationUnitCd(){
        return durationUnitCd;
  }
  public void setDurationUnitCd(String aDurationUnitCd){
        durationUnitCd = aDurationUnitCd;
        setItDirty(true);
  }
  public Timestamp getFromTime(){
        return fromTime;
  }
  public void setFromTime(Timestamp aFromTime){
        fromTime = aFromTime;
        setItDirty(true);
  }
  public void setFromTime_s(String strTime){
      if (strTime == null) return;
      this.setFromTime(StringUtils.stringToStrutsTimestamp(strTime));
  }
  public Timestamp getToTime(){
        return toTime;
  }
  public void setToTime(Timestamp aToTime){
        toTime = aToTime;
        setItDirty(true);
  }
  public Integer getObsValueDateSeq(){
        return obsValueDateSeq;
  }
  public void setObsValueDateSeq(Integer aObsValueDateSeq){
        obsValueDateSeq = aObsValueDateSeq;
        setItDirty(true);
  }
  public BigDecimal getNumericValue1(){
        return numericValue1;
  }
  public void setNumericValue1(BigDecimal aNumericValue1){
        numericValue1 = aNumericValue1;
        setItDirty(true);
  }
  public BigDecimal getNumericValue2(){
        return numericValue2;
  }
  public void setNumericValue2(BigDecimal aNumericValue2){
        numericValue2 = aNumericValue2;
        setItDirty(true);
  }
  public String getNumericUnitCd(){
        return numericUnitCd;
  }
  public void setNumericUnitCd(String aNumericUnitCd){
        numericUnitCd = aNumericUnitCd;
        setItDirty(true);
  }
  public Integer getObsValueNumericSeq(){
        return obsValueNumericSeq;
  }
  public void setObsValueNumericSeq(Integer aObsValueNumericSeq){
        obsValueNumericSeq = aObsValueNumericSeq;
        setItDirty(true);
  }

  public String getValueTxt(){
        return valueTxt;
  }
  public void setValueTxt(String aValueTxt){
        valueTxt = aValueTxt;
        setItDirty(true);
  }
  public Integer getObsValueTxtSeq(){
        return obsValueTxtSeq;
  }
  public void setObsValueTxtSeq(Integer aObsValueTxtSeq){
        obsValueTxtSeq = aObsValueTxtSeq;
        setItDirty(true);
  }

  public void setSourceActUid(Long aSourceActUid){
        sourceActUid = aSourceActUid;
        setItDirty(true);
  }
  public Long getSourceActUid(){
        return sourceActUid;
  }
  public void setTargetActUid(Long aTargetActUid){
        targetActUid = aTargetActUid;
        setItDirty(true);
  }
  public Long getTargetActUid(){
        return targetActUid;
  }
  public void setTypeCd(String aSetTypeCd){
        typeCd = aSetTypeCd;
        setItDirty(true);
  }
    public String getTypeCd(){
        return typeCd;
  }

  public String getLocalId(){
        return localId;
  }
  public void setLocalId(String aLocalId){
        localId = aLocalId;
        setItDirty(true);
  }

  public String getCdDescTxt(){
        return cdDescTxt;
  }
  public void setCdDescTxt(String aCdDescTxt){
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
  }
  public String getCdSystemDescTxt(){
        return cdSystemDescTxt;
  }
  public void setCdSystemDescTxt(String aCdDescTxt){
        cdSystemDescTxt = aCdDescTxt;
        setItDirty(true);
  }
  public String getCdSystemCd(){
        return cdSystemCd;
  }
  public void setCdSystemCd(String aCdSystemCd){
        cdSystemCd = aCdSystemCd;
        setItDirty(true);
  }
  public String getCdVersion(){
        return cdVersion;
  }
  public void setCdVersion(String aCdVersion){
        cdVersion = aCdVersion;
        setItDirty(true);
  }
   public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }


   public boolean isItDirty() {
     return itDirty;
   }


   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }


   public boolean isItNew() {
     return itNew;
   }
   public boolean isItDelete() {
     return itDelete;
   }


    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }

    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( ObservationQA) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }

    public Integer getNumericScale1() {
		return numericScale1;
	}
	public void setNumericScale1(Integer numericScale1) {
		this.numericScale1 = numericScale1;
	}
	public Integer getNumericScale2() {
		return numericScale2;
	}
	public void setNumericScale2(Integer numericScale2) {
		this.numericScale2 = numericScale2;
	}

}