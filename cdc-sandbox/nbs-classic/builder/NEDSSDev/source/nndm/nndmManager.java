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


/** This class is receives a process command from a caller and manages all stack calls
 * in the notification manager workflow.
 * <p>
 * Notification Manager Workflow:
 * <ul>
 * <li>
 * The primary function of this Notification Manager system is to filter notification messages from
 * the NEDSS Base System , and through the rules defined by the Notifiable Disease guides, create
 * and send the NEDSS Notifiable Disease Message (NNDM) on for further processing.
 * </li>
 * <li>
 * The secondary function of this Notification Manager system is to manage the state of the message
 * as it passes from queue to queue (DB table to DB table acting as queues).  This queue management
 * task is to keep the status fields up-to-date for the Message_Out queue and for the Transport_Out queue.
 * </li>
 * </ul>
 * </p>
 *
 */
public class nndmManager
{


   /**
    * The name of the global message id across the message out and transport out queues,
    * used by this Notification Manger for the sake of identification of the message
    * that will be processed for Notifiable Diseases and then transported to the Public Health
    * System. - this ID means the same as <code> msg_id_root_txt </code> in the message
    * out queue context, and <code> messageId </code> in the transport out queue context.
    * This id is usually the <code> key </code> parameter in all methods of the nndm objects.
    */
    public static final String NOTIFIABLE_DISEASE_MESSAGE_ID = "nndmID";


   /** Instance of the nndmMessage_Out class for interactions with the Message_Out database.
   *  persist for the life of the object.
   */
   private nndmMessage_Out mmessageOut = null;

   /** Instance of the nndmTransport_Out for sending messages to the transport mechanism */
   private nndmTransport_Out mtransportOut = null;

   /** Instance of the nndmTransformer for transforming messages */
   private nndmTransformer mtransformer = null;


   /**
    * Constructs this Manager object.
    */
   public nndmManager()

   {
      super();
      mtransformer = new nndmTransformer();
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


    /** This method retrieves a list of messages that are ready to be transformed
     *  into disease-specific public health messages. A call to
      * nndmMessage_Out.listMessages(<code> nndmMessage_Out.READY_FOR_TRANSFORM_STATUS </code>)
      * method and iterates the list making calls to the process() method of this
      * class to transform and send each message.
     */
	public final void processAll()
	{
      //Retrieve a list of keys to process messages that are ready for transformations.
      for (Iterator<MessageKey> i = mmessageOut.listMessages(nndmMessage_Out.READY_FOR_TRANSFORM_STATUS).iterator();i.hasNext();){
         //Iterate the list and call process(key) for each item in the collection
         MessageKey key = (MessageKey)i.next();
         process(key);
         Thread.yield();
      }
	}


    /** This method retrieves the xml payload from the nndmMessage_Out.getMessage(key) method,
      * updates the status of the message to in-progress, validates and transforms
      * using nndmTransformer methods. The method also becomes the transaction manager
      * for a two-part transaction to send the message using nndmTransport_Out and
      * update status via nndmMessage_Out.setStatus. Any failure in the process
      * method results in a call to setError method of nndmMessage_Out with
      * the error information.
      * @param  String key that contains the message id of the notification to process
     */
	public final void process(MessageKey key)
	{
      String transformedMessage = "";
      String cleanedMessage = "";
      String stylesheetURI = "";
      // 1) Call the Message_Out getMessage(key) method to get the record
      Map<String,String> messageData = mmessageOut.getMessage(key.getMessageUid(),nndmMessage_Out.READY_FOR_TRANSFORM_STATUS);
      if (!(messageData.isEmpty())){
         String message = (String)messageData.get(nndmMessage_Out.MESSAGE_OUT_ATTACHEMNT);
         // 2) Call the Message_Out updateStatus( ) method to update status to In Progress
         mmessageOut.setStatus(key.getMessageUid(),nndmMessage_Out.TRANSFORM_IN_PROGRESS_STATUS);
         // 3) Try Call the Transformer to transform the notification XML into disease-specific.
         try {
            // get the disease-specific stylesheet URI from the the nndmRouter .
            stylesheetURI = mtransformer.transform(message,"nndmRouter.xsl");
            // create the disease-specific message for transport ...
            transformedMessage = mtransformer.transform(message,stylesheetURI);
            // apply integrity rules, then remove empty nodes that resulted from disease transformations
            cleanedMessage = mtransformer.transform(mtransformer.transform(transformedMessage,"nndm_integrity.xsl"),"nndm_finalizer.xsl");
            messageData.put(nndmTransport_Out.TRANSPORT_OUT_PAYLOAD_CONTENT,cleanedMessage);
            // 4) Call the Message_Out  updateStatus( ) method to change status to Complete.
            mmessageOut.setStatus(key.getMessageUid(),nndmMessage_Out.TRANSFORM_COMPLETE_STATUS);
            // 5) Try Call the Transport_Out Data Object to send the resulting transformed XML.
            try{
               mtransportOut.sendMessage(messageData);
               // 6) Update status in message out queue to Transport In Progress"
               mmessageOut.setStatus(key.getMessageUid(),nndmMessage_Out.READY_FOR_PHINMS_STATUS);
            } catch (Exception e){
               // Failures result in nndmMessage_Out.setError()
               mmessageOut.setError(key.getMessageUid(),key.getRootExtText(), e.getMessage(),nndmMessage_Out.TRANSPORT_ERROR_STATUS);
            }
         } catch (Exception e){
            // Failures result in nndmMessage_Out.setError()
            Exception ne = new nndmException("Transformation error: " + e.getMessage(),e);
            mmessageOut.setError(key.getMessageUid(),key.getRootExtText(), ne.getMessage(),nndmMessage_Out.TRANSFORM_ERROR_STATUS);
         }
      }
	}

}
/* END CLASS DEFINITION nndmManager */
