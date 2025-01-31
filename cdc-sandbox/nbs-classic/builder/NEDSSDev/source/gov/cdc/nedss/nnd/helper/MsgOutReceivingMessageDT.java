//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\nnd\\helpers\\MsgOutReceivingMessageDT.java

package gov.cdc.nedss.nnd.helper;

import  gov.cdc.nedss.util.*;
import java.sql.Timestamp;

public class MsgOutReceivingMessageDT extends AbstractVO
{
   private Long receivingFacilityEntityUid;
   private Long messageUid;
   private String statusCd;
   private Timestamp statusTime;

   /**
    * @roseuid 3D62571200BB
    */
   public MsgOutReceivingMessageDT()
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
    * Access method for the messageUid property.
    *
    * @return   the current value of the messageUid property
    */
   public Long getMessageUid()
   {
      return messageUid;
   }

   /**
    * Sets the value of the messageUid property.
    *
    * @param aMessageUid the new value of the messageUid property
    */
   public void setMessageUid(Long aMessageUid)
   {
      messageUid = aMessageUid;
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
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3D62571200BC
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3D62571200CE
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571200DB
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3D62571200DC
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571200EB
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3D62571200EC
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D62571200FA
    */
   public boolean isItDelete()
   {
    return true;
   }
}
