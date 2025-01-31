//Source file: C:\\rational_rose_dev\\source\\gov\\nedss\\SystemServices\\Messaging\\NND\\MessageSender\\Helpers\\Design\\NNDMarshallerNotification.java

package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.messageframework.notificationmastermessage.NedssEvent;
import gov.cdc.nedss.messageframework.notificationmastermessage.Act;
import gov.cdc.nedss.messageframework.notificationmastermessage.Notification;
import gov.cdc.nedss.act.notification.dt.*;
import gov.cdc.nedss.nnd.util.*;
import gov.cdc.nedss.nnd.exception.NNDException;

public class NNDMarshallerNotification
{
  NNDUtils util = new NNDUtils();
   /**
    * @roseuid 3E9701340394
    */
   public NNDMarshallerNotification()
   {

   }

   /**
    * @param notificationDT
    * @return java.lang.Object
    * @roseuid 3E931DE603AE
    */
   public Object marshallNotification(NotificationDT notificationDT) throws NNDException
   {
     //creating Instance of CASTOR Act class
     Act  act = new Act();
     act.setActTempId(notificationDT.getLocalId());
     act.setClassCd(NNDConstantUtil.NOTIFICATION_CD_NOTF); //String = "NOTF"
     Notification notification = new Notification();

     try{
       //copying values from NotificationDT to CASTOR Notification object
       notification = (Notification) util.copyObjects(notificationDT, notification);

     }
     catch (NNDException e){
       e.printStackTrace();
      throw new NNDException("Error in nndMarshallerNotification.marshallNotification() method  "
                             + e.getMessage());
     }

     notification.setNotificationTempId(notificationDT.getLocalId());
     //associating the Notification with intance of Act
     act.setNotification(notification);
     NedssEvent nedssEvent = new NedssEvent();
     //adding Act instance to nedssEvent
     nedssEvent.addAct(act);

    return nedssEvent;
   }
}
