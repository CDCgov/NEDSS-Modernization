/* ---------------------------------------------------------------------------------------------------
 * Emergint
 * 501 East Broadway
 * Suite 250, Louisville, KY 40202
 * Copyright © 2002
 *  -----------------------------------------------------------------------------------------------------
 *  Author:
 *  Description :
 *-------------------------------------------------------------------------------------------------------
 */
package nndm;

import java.util.*;


/** This class is responsible for managing the state of a disease message that was created
 *  by the nndmManager as it is being processed by the Public Health Message Transport System (PHMTS).
 *  This manager object will poll the Transport Out queue, checking the status of the PHMTS processing,
 *  and when appropriate, will update the Message Out queue with a status corresponding to the
 *  found status in Transport Out queue.
 */
public class phmtsManager
{

   /** Instance of the nndmMessage_Out class for interactions with the Message_Out database.
   *  persist for the life of the object.
   */
   private nndmMessage_Out mmessageOut = null;

   /** Instance of the nndmTransport_Out for sending messages to the transport mechanism */
   private nndmTransport_Out mtransportOut = null;

   /**
    * Constructs this Manager object.
    */
   public phmtsManager()
   {
      super();
      // try and determine what RDBMS is being used for Message Out queue, just assume either microsoft or oracle.
      if (nndmConfig.getNndmConfigurations(nndmConfig.DATABASE_TYPE).equalsIgnoreCase("SQL SERVER")){
         mmessageOut = nndmMessage_OutMicrosoftImpl.getNndmMessage_Out();
      } else if(nndmConfig.getNndmConfigurations(nndmConfig.DATABASE_TYPE).equalsIgnoreCase("ORACLE")){
         mmessageOut = nndmMessage_OutOracleImpl.getNndmMessage_Out();
      }else{
      	throw new RuntimeException("Unknown database type encountered in DATABASE TYPE property");
      }
      // try and determine what RDBMS is being used for Transport Out queue, just assume either microsoft or oracle.
      if (nndmConfig.getNndmConfigurations(nndmConfig.DATABASE_TYPE).equalsIgnoreCase("SQL SERVER")){
         mtransportOut = nndmTransport_OutMicrosoftImpl.getNndmTransport_Out();
      } else if(nndmConfig.getNndmConfigurations(nndmConfig.DATABASE_TYPE).equalsIgnoreCase("ORACLE")){
         mtransportOut = nndmTransport_OutOracleImpl.getNndmTransport_Out();
      }else{
      	throw new RuntimeException("Unknown database type encountered in DATABASE TYPE property");
      }
   }


    /** This method retrieves a list of messages that are in process of being
     *  transported from the  nndmMessage_Out.listMessages( <code> nndmMessage_Out.TRANSPORT_IN_PROGRESS_STATUS </code> )
     *  method and iterates the list making calls to the process() method of this
     *  class to check status in the Transport Out queue.
     */
	public final void processAll()
	{
      //Retrieve a list of keys to process messages that are in progress of being transported
      for (Iterator<MessageKey> i = mmessageOut.listMessages(nndmMessage_Out.READY_FOR_PHINMS_STATUS).iterator();i.hasNext();){
         //Iterate the list and call process(key) for each item in the collection
         MessageKey key = (MessageKey)i.next();
         process(key);
         Thread.yield();
      }

	}


    /** This process is to update the message out queue with the proper transport
     *  status, given that PHMTS is finished processing the record.  Hence, if the
     *  PHMTS transport status is <code> nndmTransport_Out.TRANSPORT_SUCCESS_STATUS </code> ,
     *  then update message out with <code> nndmMessage_Out.TRANSPORT_COMPLETE_STATUS </code>.
     *  Otherwise, set error in message out with <code> nndmMessage_Out.TRANSPORT_ERROR_STATUS </code>
     *  and found PHMTS error description.
     * @param key String that contains the message id of the item in the transport out queue to process.
     */
	public void process(MessageKey key)
	{
      // 1) Get the information from the transport out queue for the given key
      Map<String,String> messageDetails = mtransportOut.getStatus(key.getRootExtText(),nndmTransport_Out.PROCESS_DONE_STATUS);
      if (!(messageDetails.isEmpty())){
         // 2)IF the PHMTS transportStatus is 'success' , THEN update message out with transport complete status.
         //   ELSE 'failure' , THEN update message out with a transport error status and the PHMTS transportErrorCode.
         String transportStatus = ((String)messageDetails.get(nndmTransport_Out.TRANSPORT_OUT_TRANSPORT_STATUS)).trim();
         String errorDescription = (String)messageDetails.get(nndmTransport_Out.TRANSPORT_OUT_TRANSPORT_ERROR_CODE);
         if(transportStatus.equalsIgnoreCase(nndmTransport_Out.TRANSPORT_SUCCESS_STATUS)){
            mmessageOut.setStatus(key.getMessageUid(),nndmMessage_Out.PHINMS_COMPLETE_STATUS);
         } else if (transportStatus.equalsIgnoreCase(nndmTransport_Out.TRANSPORT_FAILURE_STATUS)){
            mmessageOut.setError(key.getMessageUid(), key.getRootExtText(), errorDescription,nndmMessage_Out.PHINMS_ERROR_STATUS);
         } else {
            mmessageOut.setError(key.getMessageUid(), key.getRootExtText(), "Transportq_out ERROR:transportStatus=" +transportStatus +" transportErrorCode=" + errorDescription,nndmMessage_Out.PHINMS_ERROR_STATUS);
         }
      }

	}

}
/* END CLASS DEFINITION phmtsManager */
