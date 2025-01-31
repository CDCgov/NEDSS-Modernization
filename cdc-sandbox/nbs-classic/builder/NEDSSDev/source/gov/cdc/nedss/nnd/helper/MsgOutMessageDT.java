//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\nnd\\helpers\\MsgOutMessageDT.java

package gov.cdc.nedss.nnd.helper;

import  gov.cdc.nedss.util.*;
import java.sql.Timestamp;

public class MsgOutMessageDT extends AbstractVO
{
   private Long messageUid;
   private String attachmentTxt;
   private Timestamp addTime;
   private String interactionId;
   private String msgIdAssignAuthCd;
   private String msgIdAssignAuthDescTxt;
   private String msgIdRootTxt;
   private String msgIdTypeCd;
   private String msgIdTypeDescTxt;
   private Long sendingFacilityEntityUid;
   private String statusCd;
   private Timestamp statusTime;
   private String processingCd;
   private String processingDescTxt;
   private String recordStatusCd;
   private Timestamp recordStatusTime;
   private String versionId;

   /**
    * @roseuid 3D62571003C8
    */
   public MsgOutMessageDT()
   {

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
    * Access method for the attachmentTxt property.
    *
    * @return   the current value of the attachmentTxt property
    */
   public String getAttachmentTxt()
   {
      return attachmentTxt;
   }

   /**
    * Sets the value of the attachmentTxt property.
    *
    * @param aAttachmentTxt the new value of the attachmentTxt property
    */
   public void setAttachmentTxt(String aAttachmentTxt)
   {
      attachmentTxt = aAttachmentTxt;
   }

   /**
    * Access method for the addTime property.
    *
    * @return   the current value of the addTime property
    */
   public Timestamp getAddTime()
   {
      return addTime;
   }

   /**
    * Sets the value of the addTime property.
    *
    * @param aAddTime the new value of the addTime property
    */
   public void setAddTime(Timestamp aAddTime)
   {
      addTime = aAddTime;
   }

   /**
    * Access method for the interactionId property.
    *
    * @return   the current value of the interactionId property
    */
   public String getInteractionId()
   {
      return interactionId;
   }

   /**
    * Sets the value of the interactionId property.
    *
    * @param aInteractionId the new value of the interactionId property
    */
   public void setInteractionId(String aInteractionId)
   {
      interactionId = aInteractionId;
   }

   /**
    * Access method for the msgIdAssignAuthCd property.
    *
    * @return   the current value of the msgIdAssignAuthCd property
    */
   public String getMsgIdAssignAuthCd()
   {
      return msgIdAssignAuthCd;
   }

   /**
    * Sets the value of the msgIdAssignAuthCd property.
    *
    * @param aMsgIdAssignAuthCd the new value of the msgIdAssignAuthCd property
    */
   public void setMsgIdAssignAuthCd(String aMsgIdAssignAuthCd)
   {
      msgIdAssignAuthCd = aMsgIdAssignAuthCd;
   }

   /**
    * Access method for the msgIdAssignAuthDesc_txt property.
    *
    * @return   the current value of the msgIdAssignAuthDesc_txt property
    */
   public String getMsgIdAssignAuthDescTxt()
   {
      return msgIdAssignAuthDescTxt;
   }

   /**
    * Sets the value of the msgIdAssignAuthDescTxt property.
    *
    * @param aMsgIdAssignAuthDescTxt the new value of the msgIdAssignAuthDescTxt property
    */
   public void setMsgIdAssignAuthDescTxt(String aMsgIdAssignAuthDescTxt)
   {
      msgIdAssignAuthDescTxt = aMsgIdAssignAuthDescTxt;
   }

   /**
    * Access method for the msgIdRootTxt property.
    *
    * @return   the current value of the msgIdRootTxt property
    */
   public String getMsgIdRootTxt()
   {
      return msgIdRootTxt;
   }

   /**
    * Sets the value of the msgIdRootTxt property.
    *
    * @param aMsgIdRootTxt the new value of the msgIdRootTxt property
    */
   public void setMsgIdRootTxt(String aMsgIdRootTxt)
   {
      msgIdRootTxt = aMsgIdRootTxt;
   }

   /**
    * Access method for the msgIdTypeCd property.
    *
    * @return   the current value of the msgIdTypeCd property
    */
   public String getMsgIdTypeCd()
   {
      return msgIdTypeCd;
   }

   /**
    * Sets the value of the msgIdTypeCd property.
    *
    * @param aMsgIdTypeCd the new value of the msgIdTypeCd property
    */
   public void setMsgIdTypeCd(String aMsgIdTypeCd)
   {
      msgIdTypeCd = aMsgIdTypeCd;
   }

   /**
    * Access method for the msgIdTypeDescTxt property.
    *
    * @return   the current value of the msgIdTypeDescTxt property
    */
   public String getMsgIdTypeDescTxt()
   {
      return msgIdTypeDescTxt;
   }

   /**
    * Sets the value of the msgIdTypeDescTxt property.
    *
    * @param aMsgIdTypeDescTxt the new value of the msgIdTypeDescTxt property
    */
   public void setMsgIdTypeDescTxt(String aMsgIdTypeDescTxt)
   {
      msgIdTypeDescTxt = aMsgIdTypeDescTxt;
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
    * Access method for the processingCd property.
    *
    * @return   the current value of the processingCd property
    */
   public String getProcessingCd()
   {
      return processingCd;
   }

   /**
    * Sets the value of the processingCd property.
    *
    * @param aProcessingCd the new value of the processingCd property
    */
   public void setProcessingCd(String aProcessingCd)
   {
      processingCd = aProcessingCd;
   }

   /**
    * Access method for the processingDescTxt property.
    *
    * @return   the current value of the processingDescTxt property
    */
   public String getProcessingDescTxt()
   {
      return processingDescTxt;
   }

   /**
    * Sets the value of the processingDescTxt property.
    *
    * @param aProcessingDescTxt the new value of the processingDescTxt property
    */
   public void setProcessingDescTxt(String aProcessingDescTxt)
   {
      processingDescTxt = aProcessingDescTxt;
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
    * Access method for the versionId property.
    *
    * @return   the current value of the versionId property
    */
   public String getVersionId()
   {
      return versionId;
   }

   /**
    * Sets the value of the versionId property.
    *
    * @param aVersionId the new value of the versionId property
    */
   public void setVersionId(String aVersionId)
   {
      versionId = aVersionId;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3D6257110000
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3D625711000F
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D6257110011
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3D625711001F
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D6257110021
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3D6257110022
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D625711002F
    */
   public boolean isItDelete()
   {
    return true;
   }
}
