//Source file: c:\\dev\\source\\gov\\cdc\\nedss\\nnd\\ejb\\nndmessageprocessor\\helpers\\NNDMarshallerHeader.java

/**
 * Title:        NNDMarshallerHeader
 * Description:  populate the fields in the Investigation_Message Header
 *               Return a filled in message header.
 * Copyright:    Copyright (c) 2002
 * Company:      CSC for CDC NEDSS Project
 * @author Greg Tucker
 * @version 1.0
 */

package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.messageframework.notificationmastermessage.MessageHeader;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.nnd.util.NNDUtils;
import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.act.notification.dt.*;

import java.util.*;


public class NNDMarshallerHeader
{

   /**
    * @roseuid 3D6B6E67001F
    */
   public NNDMarshallerHeader()
   {

   }
static final LogUtils logger = new LogUtils(NNDMarshallerHeader.class.getName());
private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
   /**
    * @param notificationLocalId
    * @param notificationType
    * @return gov.cdc.nedss.messageframework.notificationmastermessage.MessageHeader
    * @roseuid 3D4F28500213
    */
   public MessageHeader marshallHeader(NotificationDT notificationDT) throws NNDException
   {
      MessageHeader msgHdr = new MessageHeader();
      NNDUtils nndUtil = new NNDUtils();

      /* id not currently populated */

      /* creationTime set to current time GMT */
   try {
      //java.sql.Timestamp currentTime = new java.sql.Timestamp(new Date().getTime());

      //Date gmtDate = nndUtil.getGMTDate(currentTime);
      Date currentDate = new Date();
      msgHdr.setCreationTime(currentDate);

      /* set the Version */
      msgHdr.setVersion(propertyUtil.getNND_MESSAGE_VERSION());

      /* set the Interaction ID depending on autoResendInd */
            if (notificationDT.getCd().equals("NOTF")){
              if (notificationDT.getAutoResendInd().equals("T"))
                msgHdr.setInteractionId(NNDConstantUtil.NND_AUTO_RESEND_INTERACTION_ID);
              else if (notificationDT.getAutoResendInd().equals("F"))
                msgHdr.setInteractionId(NNDConstantUtil.NND_NEW_NOT_INTERACTION_ID);
              else
                logger.error("notificationDT.getAutoResendInd() not T or F");
            }
            else if (notificationDT.getCd().equals("NSUM"))
              //set the interaction id to summary
              msgHdr.setInteractionId(NNDConstantUtil.NND_SUMMARY_INTERACTION_ID);
            else
               logger.error("notificationDT.getCd() <> NOTF or NSUM");



      /* set the NBS State Code */
      msgHdr.setNbsStateCode(propertyUtil.getStateSiteCode());

      /* set ABCState */
    String abcState = propertyUtil.getABCSTATE();
    if (abcState != null && abcState.equalsIgnoreCase(NNDConstantUtil.ABCSTATE_ABCQUESTION))
      msgHdr.setAbcState(NNDConstantUtil.YES);
    else
      msgHdr.setAbcState(NNDConstantUtil.NO);

    /* set FirstReportedToCDC to GMT date for now */
    /* later may map to the date of the first notification sent */
    msgHdr.setFirstReportedToCDC(new Date());

    /* use Notification Local ID for message id local id */
    msgHdr.setMessageIdLocalId(notificationDT.getLocalId());
    msgHdr.setMessageIdTypeCode(NNDConstantUtil.MSG_ID_TYPE_CD);
    msgHdr.setMessageIdTypeCodeDescText(NNDConstantUtil.MSG_ID_TYPE_DESC_TXT);
    /* use NBS and NEDSS Base System for Local ID Assigning Auth Code and Desc */
    msgHdr.setMessageIdAssigningAuthCode(NNDConstantUtil.MSG_ID_ASSIGN_AUTH_CD);
    msgHdr.setMessageIdAssigningAuthDescText(NNDConstantUtil.MSG_ID_ASSIGN_AUTH_DESC_TXT);

    /* processing code is Test or Production */
    msgHdr.setProcessingCode(NNDConstantUtil.NBS_PRODUCTION_PROCESSING_CODE);
    msgHdr.setProcessingCodeDescText(NNDConstantUtil.NBS_PRODUCTION_PROCESSING_DESC_TXT);

    /* set sending application fields */
    msgHdr.setSendingApplicationUniqueId(propertyUtil.getNNDRootExtensiontTxt());
    msgHdr.setSendingApplicationAssigningDescText(NNDConstantUtil.MSGOUT_SENDING_ASSIGNING_AUTHORITY_DESC_TXT);
    msgHdr.setSendingApplicationAssigningAuthCode(NNDConstantUtil.MSGOUT_SENDING_ASSIGNING_AUTHORITY_CD);

    /* set receiving application fields */
    msgHdr.setReceivingApplicationAssigningAuthCode(NNDConstantUtil.MSGOUT_RECEIVING_ASSIGNING_AUTHORITY_CD);
    msgHdr.setReceivingApplicationAssigningDescText(NNDConstantUtil.MSGOUT_RECEIVING_ASSIGNING_AUTHORITY_DESC_TXT);

    /* return castor generated MESSAGE_HEADER */
   }
   catch (Exception e)
   {
    NNDException nndOther = new NNDException("Exception in marshallHeader " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerHeader.marshallHeader");
    throw nndOther;
   }
    return msgHdr;

   }
}
