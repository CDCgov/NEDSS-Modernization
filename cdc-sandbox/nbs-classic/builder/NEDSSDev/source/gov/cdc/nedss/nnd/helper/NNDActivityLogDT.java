//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\nnd\\helpers\\NNDActivityLogDT.java

package gov.cdc.nedss.nnd.helper;

import  gov.cdc.nedss.util.*;
import java.sql.Timestamp;

public class NNDActivityLogDT extends AbstractVO
{
   private Long nndActivityLogUid;
   private Integer nndActivityLogSeq;
   private String errorMessageTxt;
   private String localId;
   private String recordStatusCd;
   private Timestamp recordStatusTime;
   private String statusCd;
   private Timestamp statusTime;
   private String subjectNm;
   private String service;

   /**
    * @roseuid 3D77B5B7007D
    */
   public NNDActivityLogDT()
   {

   }

   public void setService(String service) {
     this.service = service;
   }

   public String getService() {
     return service;
   }

   /**
    * Access method for the nndActivityLogUid property.
    *
    * @return   the current value of the nndActivityLogUid property
    */
   public Long getNndActivityLogUid()
   {
      return nndActivityLogUid;
   }

   /**
    * Sets the value of the nndActivityLogUid property.
    *
    * @param aNndActivityLogUid the new value of the nndActivityLogUid property
    */
   public void setNndActivityLogUid(Long aNndActivityLogUid)
   {
      nndActivityLogUid = aNndActivityLogUid;
   }

   /**
    * Access method for the nndActivityLogSeq property.
    *
    * @return   the current value of the nndActivityLogSeq property
    */
   public Integer getNndActivityLogSeq()
   {
      return nndActivityLogSeq;
   }

   /**
    * Sets the value of the nndActivityLogSeq property.
    *
    * @param aNndActivityLogSeq the new value of the nndActivityLogSeq property
    */
   public void setNndActivityLogSeq(Integer aNndActivityLogSeq)
   {
      nndActivityLogSeq = aNndActivityLogSeq;
   }

   /**
    * Access method for the errorMessageTxt property.
    *
    * @return   the current value of the errorMessageTxt property
    */
   public String getErrorMessageTxt()
   {
      return errorMessageTxt;
   }

   /**
    * Sets the value of the errorMessageTxt property.
    *
    * @param aErrorMessageTxt the new value of the errorMessageTxt property
    */
   public void setErrorMessageTxt(String aErrorMessageTxt)
   {
      errorMessageTxt = aErrorMessageTxt;
   }

   /**
    * Access method for the localId property.
    *
    * @return   the current value of the localId property
    */
   public String getLocalId()
   {
      return localId;
   }

   /**
    * Sets the value of the localId property.
    *
    * @param aLocalId the new value of the localId property
    */
   public void setLocalId(String aLocalId)
   {
      localId = aLocalId;
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
   public void setRecordStatusCd(String aRecordStatusCd)
   {
      recordStatusCd = aRecordStatusCd;
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
   public void setRecordStatusTime(Timestamp aRecordStatusTime)
   {
      recordStatusTime = aRecordStatusTime;
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
    * Access method for the subjectNm property.
    *
    * @return   the current value of the subjectNm property
    */
   public String getSubjectNm()
   {
      return subjectNm;
   }

   /**
    * Sets the value of the subjectNm property.
    *
    * @param aSubjectNm the new value of the subjectNm property
    */
   public void setSubjectNm(String aSubjectNm)
   {
      subjectNm = aSubjectNm;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3D77B5B7008C
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3D77B5B7009C
    */
   public void setItDirty(boolean itDirty)
   {
     this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * @roseuid 3D77B5B7009E
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * @param itNew
    * @roseuid 3D77B5B700AB
    */
   public void setItNew(boolean itNew)
   {
     this.itNew = itNew;
   }

   /**
    * @return boolean
    * @roseuid 3D77B5B700AD
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * @param itDelete
    * @roseuid 3D77B5B700BB
    */
   public void setItDelete(boolean itDelete)
   {
     this.itDelete = itDelete;
   }

   /**
    * @return boolean
    * @roseuid 3D77B5B700BD
    */
   public boolean isItDelete()
   {
    return itDelete;
   }
}
