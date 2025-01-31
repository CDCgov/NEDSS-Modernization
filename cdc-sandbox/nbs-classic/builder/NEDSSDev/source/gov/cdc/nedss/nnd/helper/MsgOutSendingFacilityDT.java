//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\nnd\\helpers\\MsgOutSendingFacilityDT.java

package gov.cdc.nedss.nnd.helper;

import  gov.cdc.nedss.util.*;
import java.sql.Timestamp;

public class MsgOutSendingFacilityDT extends AbstractVO
{
   private Long sendingFacilityEntityUid;
   private String assigningAuthorityCd;
   private String assigningAuthorityDescTxt;
   private String nmUseCd;
   private String recordStatusCd;
   private Timestamp recordStatusTime;
   private String rootExtensionTxt;
   private String sendingFacilityNm;
   private String statusCd;
   private Timestamp statusTime;
   private String typeCd;

   /**
    * @roseuid 3D625712029F
    */
   public MsgOutSendingFacilityDT()
   {

   }

   /**
    * Access method for the sendingFacilityEntityUid property.
    *
    * @return   the current value of the sendingFacilityEntityUid property
    */
   public Long getSendingFacilityEntityUid()
   {
      return sendingFacilityEntityUid;
   }

   /**
    * Sets the value of the sendingFacilityEntityUid property.
    *
    * @param aSendingFacilityEntityUid the new value of the sendingFacilityEntityUid property
    */
   public void setSendingFacilityEntityUid(Long aSendingFacilityEntityUid)
   {
      sendingFacilityEntityUid = aSendingFacilityEntityUid;
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
    * Access method for the sendingFacilityNm property.
    *
    * @return   the current value of the sendingFacilityNm property
    */
   public String getSendingFacilityNm()
   {
      return sendingFacilityNm;
   }

   /**
    * Sets the value of the sendingFacilityNm property.
    *
    * @param aSendingFacilityNm the new value of the sendingFacilityNm property
    */
   public void setSendingFacilityNm(String aSendingFacilityNm)
   {
      sendingFacilityNm = aSendingFacilityNm;
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
    * @roseuid 3D62571202AF
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3D62571202C2
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571202CF
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3D62571202D0
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571202DF
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3D62571202E0
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571202EF
    */
   public boolean isItDelete()
   {
    return true;
   }
}
