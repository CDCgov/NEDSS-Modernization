package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Pradeep Kumar Sharma
 * @version 1.0
 */

public class ResultedTestSummaryVO
    extends AbstractVO implements RootDTInterface{
  private Long sourceActUid;
	private  String localId;
  private Long observationUid;
	private String ctrlCdUserDefined1;
  private String resultedTest;
  private String codedResultValue;
  private String organismName;
  private String numericResultCompare;
  private BigDecimal numericResultValue1;
  private String numericResultSeperator;
  private BigDecimal numericResultValue2;
  private String numericResultUnits;
  private String textResultValue;
  private String type;
  private String status;
  private String resultedTestStatusCd;
  private String resultedTestStatus;
  private String drugName;
  private String orderedTest;
  private Collection<Object>  theSusTestSummaryVOColl;
  private String cdSystemCd;
  private String resultedTestCd;
  private String organismCodeSystemCd;
  private String recordStatusCode;
  private String highRange;
  private Integer numericScale1;
  
  private String uniqueMapKey;
  
  private Integer numericScale2;
  public String getHighRange() {
	return highRange;
}

public void setHighRange(String highRange) {
	this.highRange = highRange;
}

public String getLowRange() {
	return lowRange;
}

public void setLowRange(String lowRange) {
	this.lowRange = lowRange;
}

private String lowRange;

  public ResultedTestSummaryVO() {
  }

   public String getCtrlCdUserDefined1()
    {
        return ctrlCdUserDefined1;
    }

    public void setCtrlCdUserDefined1(String aCtrlCdUserDefined1)
    {
        ctrlCdUserDefined1 = aCtrlCdUserDefined1;

    }

	public Long getSourceActUid(){
		return this.sourceActUid;
	}

	public void setSourceActUid(Long sourceActUid){
		this.sourceActUid = sourceActUid;
	}

	public String getLocalId() {
    return localId;
  }

  public void setLocalId(String aLocalId) {
    localId = aLocalId;
  }

  public Long getObservationUid() {
    return observationUid;
  }

  public void setObservationUid(Long aObservationUid) {
    observationUid = aObservationUid;
  }

  public String getResultedTest() {
    return resultedTest;
  }

  public void setResultedTest(String aResultedTest) {
    resultedTest = aResultedTest;
  }

  public String getCodedResultValue() {
    return codedResultValue;
  }

  public void setCodedResultValue(String aCodedResultValue) {
    codedResultValue = aCodedResultValue;
  }

  public String getOrganismName() {
    return organismName;
  }

  public void setOrganismName(String aOrganismName) {
    organismName = aOrganismName;
  }

  public String getNumericResultCompare() {
    return numericResultCompare;
  }

  public void setNumericResultCompare(String aNumericResultCompare) {
    numericResultCompare = aNumericResultCompare;
  }

  public BigDecimal getNumericResultValue1() {
	// Get the precision scale from the database and then translate the
	// value read from database to right precision
	// It will fix the issue where entering 2.0 in the front end was getting
	// translated to 2.00000 in SQL Server and to 2 in case of oracle for
	// both numeric_value_1 and numeric_value_2.
	if (numericResultValue1 != null && this.getNumericScale1() != null
			&& this.getNumericScale1().intValue() <= 5)
		return numericResultValue1.setScale(this.getNumericScale1()
				.intValue());
	return numericResultValue1;

  }

  public void setNumericResultValue1(BigDecimal aNumericResultValue1) {
    numericResultValue1 = aNumericResultValue1;
  }

  public String getNumericResultSeperator() {
    return numericResultSeperator;
  }

  public void setNumericResultSeperator(String aNumericResultSeperator) {
    numericResultSeperator = aNumericResultSeperator;
  }

  public BigDecimal getNumericResultValue2() {
	// Get the precision scale from the database and then translate the
		// value read from database to right precision
		// It will fix the issue where entering 2.0 in the front end was getting
		// translated to 2.00000 in SQL Server and to 2 in case of oracle for
		// both numeric_value_1 and numeric_value_2.
		if (numericResultValue2 != null && this.getNumericScale2() != null
				&& this.getNumericScale2().intValue() <= 5)
			return numericResultValue2.setScale(this.getNumericScale2()
					.intValue());
		return numericResultValue2;
  }

  public void setNumericResultValue2(BigDecimal aNumericResultValue2) {
    numericResultValue2 = aNumericResultValue2;
  }

  public String getNumericResultUnits() {
    return numericResultUnits;
  }

  public void setNumericResultUnits(String aNumericResultUnits) {
    numericResultUnits = aNumericResultUnits;
  }

  public String getTextResultValue() {
    return textResultValue;
  }

  public void setTextResultValue(String aTextResultValue) {
    textResultValue = aTextResultValue;
  }

  public String getType() {
    return type;
  }

  public void setType(String aType) {
    type = aType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String aStatus) {
    status = aStatus;
  }

  public String getDrugName() {
    return drugName;
  }

  public void setDrugName(String aDrugName) {
    drugName = aDrugName;
  }

  public String getOrderedTest() {
    return orderedTest;
  }

  public void setOrderedTest(String aOrderedTest) {
    orderedTest = aOrderedTest;
  }

  public Collection<Object> getTheSusTestSummaryVOColl() {
    return theSusTestSummaryVOColl;
  }

  public void setTheSusTestSummaryVOColl(Collection<Object> aTheSusTestSummaryVOColl) {
    theSusTestSummaryVOColl = aTheSusTestSummaryVOColl;
  }

  public boolean isEqual(java.lang.Object objectname1,
                         java.lang.Object objectname2, Class<?> voClass) {
    return true;
  }

  public void setItDirty(boolean itDirty) {
  }

  public boolean isItDirty() {
    return true;
  }

  public void setItNew(boolean itNew) {
  }

  public boolean isItNew() {
    return true;
  }

  public void setItDelete(boolean itDelete) {
  }

  public boolean isItDelete() {
    return true;
  }
  /**
   * Access method for the statusCd property.
   *
   * @return   the current value of the statusCd property
   */
  public String getStatusCd()
  {
    return null;
  }

  /**
   * Sets the value of the statusCd property.
   *
   * @param aStatusCd the new value of the statusCd property
   */
  public void setStatusCd(String aStatusCd)
  {
  }

     /**
      * Access method for the jurisdictionCd property.
      *
      * @return   the current value of the jurisdictionCd property
      */
     public String getJurisdictionCd()
     {
       return null;
     }


     /**
      * Sets the value of the jurisdictionCd property.
      *
      * @param aJurisdictionCd the new value of the jurisdictionCd property
      */
     public void setJurisdictionCd(String aJurisdictionCd)
     {
     }

     /**
      */
     public Integer getVersionCtrlNbr()
     {
        return null;
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
* A setter for add time
*/
public void setAddTime(java.sql.Timestamp aAddTime)
{

}

/**
* A getter for add time
*/
public Timestamp getAddTime()
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
  * @return java.lang.String
  */
 public String getSuperclass()
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
  * @param aStatusTime
  */
 public Timestamp getStatusTime()
 {
   return null;
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
 public String getRecordStatusCd (){
   return null;
 }
 public void setRecordStatusCd (String aRecordStatusCd){

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
  * @return java.lang.String
  */
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
  public String getCdSystemCd() {
    return cdSystemCd;
  }
  public void setCdSystemCd(String cdSystemCd) {
    this.cdSystemCd = cdSystemCd;
  }
  public String getResultedTestCd() {
    return resultedTestCd;
  }
  public void setResultedTestCd(String resultedTestCd) {
    this.resultedTestCd = resultedTestCd;
  }
  public String getOrganismCodeSystemCd() {
    return organismCodeSystemCd;
  }
  public void setOrganismCodeSystemCd(String organismCodeSystemCd) {
    this.organismCodeSystemCd = organismCodeSystemCd;
  }

public String getResultedTestStatusCd() {
	return resultedTestStatusCd;
}

public void setResultedTestStatusCd(String resultedTestStatusCd) {
	this.resultedTestStatusCd = resultedTestStatusCd;
}

public String getResultedTestStatus() {
	return resultedTestStatus;
}

public void setResultedTestStatus(String resultedTestStatus) {
	this.resultedTestStatus = resultedTestStatus;
}

public String getRecordStatusCode() {
	return recordStatusCode;
}

public void setRecordStatusCode(String recordStatusCode) {
	this.recordStatusCode = recordStatusCode;
}

/**
 * @return the numericScale1
 */
public Integer getNumericScale1() {
	return numericScale1;
}

/**
 * @param numericScale1 the numericScale1 to set
 */
public void setNumericScale1(Integer numericScale1) {
	this.numericScale1 = numericScale1;
}

/**
 * @return the numericScale2
 */
public Integer getNumericScale2() {
	return numericScale2;
}

/**
 * @param numericScale2 the numericScale2 to set
 */
public void setNumericScale2(Integer numericScale2) {
	this.numericScale2 = numericScale2;
}

public String getUniqueMapKey() {
	return uniqueMapKey;
}

public void setUniqueMapKey(String uniqueMapKey) {
	this.uniqueMapKey = uniqueMapKey;
}



}