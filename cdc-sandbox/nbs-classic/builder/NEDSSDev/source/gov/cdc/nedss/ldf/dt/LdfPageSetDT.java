//Source file: C:\\All NEDSS Related\\scratch\\gov\\gov\\cdc\\nedss\\ldf\\dt\\StateDefinedFieldDataDT.java

package gov.cdc.nedss.ldf.dt;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import java.sql.Timestamp;


public class LdfPageSetDT extends AbstractVO implements RootDTInterface
{
   private String ldfPageId;
   private String businessObjNm;
   private String conditionCd;
   private String uiDisplay;
   private Integer indentLevelNbr;
   private String parentIsCd;
   private String codeSetNm;
   private Integer seqNum;
   private String codeVerson;
   private Integer nbsUid;
   private Timestamp effectiveFromTime;
   private Timestamp effectiveToTime;
   private String statusCd;
   private String codeShortDescTxt;
   private Integer displayRow;
   private Integer displayColumn;

   /**
    * @roseuid 3F4E4AFF016E
    */
   public LdfPageSetDT()
   {

   }

   public String getLdfPageId()
   {
      return ldfPageId;
   }

   public void setLdfPageId(String aLdfPageId)
   {
      ldfPageId = aLdfPageId;
   }

   public String getBusinessObjNm()
   {
      return businessObjNm;
   }

   public void setBusinessObjNm(String aBusinessObjNm)
   {
      businessObjNm = aBusinessObjNm;
   }

   public String getConditionCd(){
     return this.conditionCd;
   }
   public void setConditionCd(String aConditionCd){
     conditionCd = aConditionCd;
   }
   public String getUiDisplay(){
     return this.uiDisplay;
   }
   public void setUiDisplay(String aUiDisplay){
     uiDisplay = aUiDisplay;
   }
   public Integer getIndentLevelNbr(){
     return this.indentLevelNbr;
   }
   public void setIndentLevelNbr(Integer aIndentLevelNbr){
     indentLevelNbr = aIndentLevelNbr;
   }

   public String getParentIsCd(){
     return parentIsCd;
   }
   public void setParentIsCd(String aParentIsCd){
     parentIsCd = aParentIsCd;
   }

   public String getCodeSetNm(){
     return codeSetNm;
   }
   public void setCodeSetNm(String aCodeSetNm){
     codeSetNm = aCodeSetNm;
   }

   public Integer getSeqNum(){
     return seqNum;
   }
   public void setSeqNum(Integer aSeqNum){
     seqNum = aSeqNum;
   }

   public String getCodeVerson(){
     return codeVerson;
   }
   public void setCodeVerson(String aCodeVerson){
     codeVerson = aCodeVerson;
   }

   public Integer getNbsUid(){
   return nbsUid;
  }
  public void setNbsUid(Integer aNbsUid){
    nbsUid = aNbsUid;
  }

  public Timestamp getEffectiveFromTime(){
    return effectiveFromTime;
  }
  public void setEffectiveFromTime(Timestamp anEffectiveFromTime){
    effectiveFromTime = anEffectiveFromTime;
  }

  public Timestamp getEffectiveToTime(){
    return effectiveToTime;
  }
  public void setEffectiveToTime(Timestamp anEffectiveToTime){
    effectiveFromTime = anEffectiveToTime;
  }

  public String getStatusCd(){
    return statusCd;
  }
  public void setldfValueString(String aStatusCd){
    statusCd = aStatusCd;
  }

  public String getCodeShortDescTxt(){
  return codeShortDescTxt;
}
public void setCodeShortDescTxt(String aCodeShortDescTxt){
  codeShortDescTxt = aCodeShortDescTxt;
}


public Integer getDisplayRow(){
  return displayRow;
}
public void setDisplayRow(Integer aDisplayRow){
  displayRow = aDisplayRow;
}

public Integer getDisplayColumn(){
  return displayRow;
}
public void setDisplayColumn(Integer aDisplayColumn){
  displayColumn = aDisplayColumn;
}









   /**
    * Access method for the addTime property.
    *
    * @return   the current value of the addTime property
    */
   public Timestamp getAddTime()
   {
      return null;
   }

   /**
    * Sets the value of the addTime property.
    *
    * @param aAddTime the new value of the addTime property
    */
   public void setAddTime(Timestamp aAddTime)
   {

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




   /**
    * Access method for the versionCtrlNbr property.
    *
    * @return   the current value of the versionCtrlNbr property
    */
   public Integer getVersionCtrlNbr()
   {
      return null;
   }

   /**
    * Sets the value of the versionCtrlNbr property.
    *
    * @param aVersionCtrlNbr the new value of the versionCtrlNbr property
    */
   public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
   {

   }

   /**
   * @param itDirty
   * @roseuid 3E3040CB01C4
   */
  public void setItDirty(boolean itDirty)
  {
       this.itDirty = itDirty;
  }

  /**
   * @return boolean
   * @roseuid 3E3040CB01E2
   */
  public boolean isItDirty()
  {
   return itDirty;
  }

  /**
   * @param itNew
   * @roseuid 3E3040CB01F6
   */
  public void setItNew(boolean itNew)
  {
       this.itNew = itNew;
  }

  /**
   * @return boolean
   * @roseuid 3E3040CB0214
   */
  public boolean isItNew()
  {
   return itNew;
  }

  /**
   * @param itDelete
   * @roseuid 3E3040CB021E
   */
  public void setItDelete(boolean itDelete)
  {
     this.itDelete = itDelete;
  }

  /**
   * @return boolean
   * @roseuid 3E3040CB023C
   */
  public boolean isItDelete()
  {
   return itDelete;
  }
  /**
 * @param objectname1
 * @param objectname2
 * @param voClass
 * @return boolean
 * @roseuid 3E3040CB0188
 */
public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
{
 return true;
}

  /**
   * @param aSharedInd
   * @roseuid 3E3046570338
   */
  public void setSharedInd(String aSharedInd)
  {

  }
  /**
 * @return java.lang.String
 * @roseuid 3E3046570324
 */
public String getSharedInd()
{
 return null;
}

  /**
   * @return java.lang.Long
   * @roseuid 3E30465702E8
   */
  public Long getProgramJurisdictionOid()
  {
   return null;
  }

  /**
   * @param aProgramJurisdictionOid
   * @roseuid 3E30465702FC
   */
  public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
  {

  }
  /**
    * @return java.lang.Long
    * @roseuid 3E30465702CA
    */
   public Long getUid()
   {
    return null;
   }


   /**
    * @return java.lang.String
    * @roseuid 3E30465702B6
    */
   public String getSuperclass()
   {
    return null;
   }

   /**
    * @return java.sql.Timestamp
    * @roseuid 3E304657027A
    */
   public Timestamp getStatusTime()
   {
    return null;
   }

   /**
    * @param aStatusTime
    * @roseuid 3E304657028E
    */
   public void setStatusTime(Timestamp aStatusTime)
   {

   }


/**
 * @param aStatusCd
 * @roseuid 3E3046570252
 */
public void setStatusCd(String aStatusCd)
{

}
/**
 * @return java.sql.Timestamp
 * @roseuid 3E3046570202
 */
public Timestamp getRecordStatusTime()
{
 return null;
}

/**
 * @param aRecordStatusTime
 * @roseuid 3E3046570216
 */
public void setRecordStatusTime(Timestamp aRecordStatusTime)
{

}
/**
 * @return java.lang.String
 * @roseuid 3E30465701C5
 */
public String getRecordStatusCd()
{
 return null;
}

/**
 * @param aRecordStatusCd
 * @roseuid 3E30465701DA
 */
public void setRecordStatusCd(String aRecordStatusCd)
{

}
/**
  * @return java.lang.String
  * @roseuid 3E3046570193
  */
 public String getLastChgReasonCd()
 {
  return null;
 }

 /**
  * @param aLastChgReasonCd
  * @roseuid 3E30465701A7
  */
 public void setLastChgReasonCd(String aLastChgReasonCd)
 {

 }

 /**
   * @return java.lang.Long
   * @roseuid 3E3046570085
   */
  public Long getLastChgUserId()
  {
   return null;
  }

  /**
   * @param aLastChgUserId
   * @roseuid 3E3046570099
   */
  public void setLastChgUserId(Long aLastChgUserId)
  {

  }
  /**
   * @return java.lang.Long
   * @roseuid 3E3046570157
   */
  public Long getAddUserId()
  {
   return null;
  }

  /**
   * @param aAddUserId
   * @roseuid 3E304657016B
   */
  public void setAddUserId(Long aAddUserId)
  {

  }
  /**
   * @return java.lang.String
   * @roseuid 3E3046570125
   */
  public String getLocalId()
  {
   return null;
  }

  /**
   * @param aLocalId
   * @roseuid 3E3046570139
   */
  public void setLocalId(String aLocalId)
  {

  }
  /**
   * @return java.lang.String
   * @roseuid 3E30465700E9
   */
  public String getProgAreaCd()
  {
   return null;
  }

  /**
   * @param aProgAreaCd
   * @roseuid 3E30465700FD
   */
  public void setProgAreaCd(String aProgAreaCd)
  {

  }
  /**
    * @return java.lang.String
    * @roseuid 3E30465700B7
    */
   public String getJurisdictionCd()
   {
    return null;
   }

   /**
    * @param aJurisdictionCd
    * @roseuid 3E30465700CB
    */
   public void setJurisdictionCd(String aJurisdictionCd)
   {

   }

}
