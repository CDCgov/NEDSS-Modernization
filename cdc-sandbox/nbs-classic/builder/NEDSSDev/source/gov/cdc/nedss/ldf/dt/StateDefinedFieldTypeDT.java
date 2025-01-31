package gov.cdc.nedss.ldf.dt;

import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import java.sql.Timestamp;

public class StateDefinedFieldTypeDT extends AbstractVO implements RootDTInterface
{

    private String operationType;
    private String fieldName;
    private String fieldDescription;
    private String srtFunctionName;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;


   /**
    * @roseuid 3E3040CB015F
    */
   public StateDefinedFieldTypeDT()
   {

   }
   /**
    * Access method for the operationType property.
    *
    * @return   the current value of the operationType property
    */
   public String getOperationType()
   {
      return operationType;
   }

   /**
    * Sets the value of the operationType property.
    *
    * @param aOperationType the new value of the operationType property
    */
   public void setOperationType(String aOperationType)
   {
      operationType = aOperationType;
   }

   /**
    * Access method for the fieldName property.
    *
    * @return   the current value of the fieldName property
    */
   public String getFieldName()
   {
      return fieldName;
   }

   /**
    * Sets the value of the fieldName property.
    *
    * @param aFieldName the new value of the fieldName property
    */
   public void setFieldName(String aFieldName)
   {
      fieldName = aFieldName;
   }

   /**
    * Access method for the fieldDescription property.
    *
    * @return   the current value of the fieldDescription property
    */
   public String getFieldDescription()
   {
      return fieldDescription;
   }

   /**
    * Sets the value of the fieldDescription property.
    *
    * @param aFieldDescription the new value of the fieldDescription property
    */
   public void setFieldDescription(String aFieldDescription)
   {
      fieldDescription = aFieldDescription;
   }

   /**
    * Access method for the srtFunctionName property.
    *
    * @return   the current value of the srtFunctionName property
    */
   public String getSrtFunctionName()
   {
      return srtFunctionName;
   }

   /**
    * Sets the value of the srtFunctionName property.
    *
    * @param aSrtFunctionName the new value of the srtFunctionName property
    */
   public void setSrtFunctionName(String aSrtFunctionName)
   {
      srtFunctionName = aSrtFunctionName;
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
    * @roseuid 3E304657023E
    */
   public String getStatusCd()
   {
    return null;
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
    * @return java.lang.String
    * @roseuid 3E30465702B6
    */
   public String getSuperclass()
   {
    return null;
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
    * @return java.lang.String
    * @roseuid 3E3046570324
    */
   public String getSharedInd()
   {
    return null;
   }

   /**
    * @param aSharedInd
    * @roseuid 3E3046570338
    */
   public void setSharedInd(String aSharedInd)
   {

   }

   /**
    * @roseuid 3E304657036A
    */
   public Integer getVersionCtrlNbr()
   {
      return null;
   }
      /**
    * @return java.lang.Long
    * @roseuid 3E30465702E8
    */
   public Timestamp getAddTime()
   {
    return null;
   }

   /**
    * @param aaAddTime
    * @roseuid 3E30465702FC
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

}
