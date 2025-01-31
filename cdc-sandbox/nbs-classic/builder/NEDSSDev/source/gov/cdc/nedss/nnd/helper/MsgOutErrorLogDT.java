//Source file: C:\\rational_rose_dev\\source\\gov\\cdc\\nedss\\nnd\\helpers\\MsgOutErrorLogDT.java

package gov.cdc.nedss.nnd.helper;

import gov.cdc.nedss.util.AbstractVO;
import java.sql.Timestamp;

public class MsgOutErrorLogDT extends AbstractVO
{
   private Long messageUid;
   private String errorMessageTxt;
   private String recordStatusCd;
   private Timestamp recordStatusTime;
   private String statusCd;
   private Timestamp statusTime;
   private String notificationLocalId;
   private String processedLog;
   private boolean itDirty = false;

   /**
    * @roseuid 3D62570F03B9
    */
   public MsgOutErrorLogDT()
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
    * Accesses the value of the notificationLocalId property.
    * @return String value notificationLocalId
    */
   public String getNotificationLocalId()
   {
     return this.notificationLocalId;
   }

   /**
    * Sets the value of the notificationLocalId property.
    * @param notificationLocalId String
    */
   public void setNotificationLocalId(String notificationLocalId)
   {
     this.notificationLocalId = notificationLocalId;
     this.setItDirty(true);
   }

   /**
    * Accesses the value of the processedLog property.
    * @return String processedLog
    */
   public String getProcessedLog()
   {
     return this.processedLog;
   }


   /**
    * Sets the value of the processedLog property.
    * @param processedLog String
    */
   public void setProcessedLog(String processedLog)
   {
     this.processedLog = processedLog;
     this.setItDirty(true);
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3D62570F03BA
    */
   public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3D62570F03CB
    */
   public void setItDirty(boolean itDirty)
   {
     this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * @roseuid 3D62570F03D9
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3D62570F03DA
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D6257100000
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3D6257100001
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D625710000F
    */
   public boolean isItDelete()
   {
    return true;
   }
}
