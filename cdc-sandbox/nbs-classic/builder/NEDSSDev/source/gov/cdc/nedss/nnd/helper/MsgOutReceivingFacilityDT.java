//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\nnd\\helpers\\MsgOutReceivingFacilityDT.java

package gov.cdc.nedss.nnd.helper;

import  gov.cdc.nedss.util.*;
import java.sql.Timestamp;

public class MsgOutReceivingFacilityDT extends AbstractVO
{
   private Long receivingFacilityEntityUid;
   private String nmUseCd;
   private String assigningAuthorityCd;
   private String assigningAuthorityDescTxt;
   private String receivingFacilityNm;
   private String recordStatusCd;
   private Timestamp recordStatusTime;
   private String rootExtensionTxt;
   private String statusCd;
   private Timestamp statusTime;
   private String typeCd;

   /**
    * @roseuid 3D6257110280
    */
   public MsgOutReceivingFacilityDT()
   {

   }

   /**
    * Access method for the receivingFacilityEntityUid property.
    *
    * @return   the current value of the receivingFacilityEntityUid property
    */
   public Long getReceivingFacilityEntityUid()
   {
      return receivingFacilityEntityUid;
   }

   /**
    * Sets the value of the receivingFacilityEntityUid property.
    *
    * @param aReceivingFacilityEntityUid the new value of the receivingFacilityEntityUid property
    */
   public void setReceivingFacilityEntityUid(Long aReceivingFacilityEntityUid)
   {
      receivingFacilityEntityUid = aReceivingFacilityEntityUid;
   }

   /**
    * Access method for the nmUseCd property.
    *
    * @return   the current value of the nmUseCd property
    */
   public String getNmUseCd()
   {
      return nmUseCd;
   }

   /**
    * Sets the value of the nmUseCd property.
    *
    * @param aNmUseCd the new value of the nmUseCd property
    */
   public void setNmUseCd(String aNmUseCd)
   {
      nmUseCd = aNmUseCd;
   }

   /**
    * Access method for the assigningAuthorityCd property.
    *
    * @return   the current value of the assigningAuthorityCd property
    */
   public String getAssigningAuthorityCd()
   {
      return assigningAuthorityCd;
   }

   /**
    * Sets the value of the assigningAuthorityCd property.
    *
    * @param aAssigningAuthorityCd the new value of the assigningAuthorityCd property
    */
   public void setAssigningAuthorityCd(String aAssigningAuthorityCd)
   {
      assigningAuthorityCd = aAssigningAuthorityCd;
   }

   /**
    * Access method for the assigningAuthorityDescTxt property.
    *
    * @return   the current value of the assigningAuthorityDescTxt property
    */
   public String getAssigningAuthorityDescTxt()
   {
      return assigningAuthorityDescTxt;
   }

   /**
    * Sets the value of the assigningAuthorityDescTxt property.
    *
    * @param aAssigningAuthorityDescTxt the new value of the assigningAuthorityDescTxt property
    */
   public void setAssigningAuthorityDescTxt(String aAssigningAuthorityDescTxt)
   {
      assigningAuthorityDescTxt = aAssigningAuthorityDescTxt;
   }

   /**
    * Access method for the receivingFacilityNm property.
    *
    * @return   the current value of the receivingFacilityNm property
    */
   public String getReceivingFacilityNm()
   {
      return receivingFacilityNm;
   }

   /**
    * Sets the value of the receivingFacilityNm property.
    *
    * @param aReceivingFacilityNm the new value of the receivingFacilityNm property
    */
   public void setReceivingFacilityNm(String aReceivingFacilityNm)
   {
      receivingFacilityNm = aReceivingFacilityNm;
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
    * Access method for the rootExtensionTxt property.
    *
    * @return   the current value of the rootExtensionTxt property
    */
   public String getRootExtensionTxt()
   {
      return rootExtensionTxt;
   }

   /**
    * Sets the value of the rootExtensionTxt property.
    *
    * @param aRootExtensionTxt the new value of the rootExtensionTxt property
    */
   public void setRootExtensionTxt(String aRootExtensionTxt)
   {
      rootExtensionTxt = aRootExtensionTxt;
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
    * Access method for the typeCd property.
    *
    * @return   the current value of the typeCd property
    */
   public String getTypeCd()
   {
      return typeCd;
   }

   /**
    * Sets the value of the typeCd property.
    *
    * @param aTypeCd the new value of the typeCd property
    */
   public void setTypeCd(String aTypeCd)
   {
      typeCd = aTypeCd;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3D6257110290
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3D62571102A0
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571102AF
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3D62571102B0
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571102B2
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3D62571102BF
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571102C1
    */
   public boolean isItDelete()
   {
    return true;
   }
}
