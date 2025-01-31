/* ---------------------------------------------------------------------------------------------------
 * Emergint
 * 501 East Broadway
 * Suite 250, Louisville, KY 40202
 * Copyright © 2002
 *  -----------------------------------------------------------------------------------------------------
 *  Author:
 *  Description :
 *  Modifications : 09/11/2003 BP changed TRANSPORT_ACTION_SEND to read from config file
 *-------------------------------------------------------------------------------------------------------
 */
package nndm;

import java.util.Map;

/** This class is a data abstraction object to the Transport_Out queue; it exposes
 *  methods to send messages to the transport out queue.  In addition, this object
 *  characterizes itself as the API to the ebXML data interchange system; i.e.Public
 *  Health Message Transport System - PHMTS , encompassing the PHMTS nomenclature.
 */
public interface nndmTransport_Out
{

   // *********************  PHMTS ACCEPTABLE VALUES  ***************************

   /**
    * Status flag for the PHMTS to indicate that a message was delivered to routeInfo
    * successfully - value is <code> success </code> .
    */
   public static final String TRANSPORT_SUCCESS_STATUS = "success";

   /**
    * Status flag for the PHMTS to indicate that a message failed to be delivered
    * to routeInfo - value is <code> failure </code> .
    */
   public static final String TRANSPORT_FAILURE_STATUS = "failure";

   /**
    * Status flag for the PHMTS to indicate the name of the action (or method)
    * within the PHMTS service that gets invoked by the SOAP receiver when it
    * receives the message.  - value is <code> send </code> .
    */
   // public static final String TRANSPORT_ACTION_SEND = "send";
   public static final String TRANSPORT_ACTION_SEND = nndmConfig.getNndmConfigurations(nndmConfig.ACTION_PROPERTY_NAME);

   /**
    * Status flag for the PHMTS to indicate that a message is in the transport out
    * table, and ready for processing by the PHMTS - value is <code> queued </code> .
    */
   public static final String PROCESS_QUEUED_STATUS = "queued";

   /**
    * Status flag for the PHMTS to indicate that a message is being processed by
    * the PHMTS - value is <code> attempted </code> .
    */
   public static final String PROCESS_ATTEMPTED_STATUS = "attempted";

   /**
    * Status flag for the PHMTS to indicate that a message is being sent by
    * the PHMTS to another destination - value is <code> sent </code> .
    */
   public static final String PROCESS_SENT_STATUS = "sent";

   /**
    * Status flag for the PHMTS to indicate that a message was received by the
    * destination system - value is <code> received </code> .
    */
   public static final String PROCESS_RECEIVED_STATUS = "received";

   /**
    * Status flag for the PHMTS to indicate that a message has been completely
    * processed by the PHMTS - value is <code> done </code> .
    */
   public static final String PROCESS_DONE_STATUS = "done";

   // ********************  END PHMTS ACCEPTABLE VALUES  ***********************


    // ************  ALLOWABLE CONFIG PROPERTY VALUES for PHMTS ****************
   /**
    *  The property name in the configuration / properties file for the
    *  <i> integer </i> denoting <code> priority </code> of the PHMTS request.
    *  1 is the highest priority and higher integers are lower priority. 0 is
    *  reserved for ping messages. - config entry name is <code> PRIORITY </code> .
    */
   public static final String TRANSPORT_OUT_PRIORITY = "PRIORITY";

   /**
    *  The property name in the configuration / properties file for the PHMTS to
    *  determine if encryption is enabled. [Yes/No] If yes, XML encryption will
    *  be applied to payload - config entry name is <code> ENCRYPTION </code> .
    */
   public static final String TRANSPORT_OUT_ENCRYPTION = "ENCRYPTION";

   /**
    *  The property name in the configuration / properties file for the PHMTS to
    *  determine if XML signing is enabled. [Yes/No] If yes, XML signature will
    *  be applied to payload - config entry name is <code> SIGNATURE </code> .
    */
   public static final String TRANSPORT_OUT_SIGNATURE = "SIGNATURE";

   /**
    *  The property name in the configuration / properties file specifing the
    *  LDAP address of the LDAP directory server for the PHMTS - config entry
    *  name is <code> PUBLIC_KEY_LDAP_ADDRESS </code> .
    */
   public static final String TRANSPORT_OUT_PUBLIC_KEY_LDAP_ADDRESS = "PUBLIC_KEY_LDAP_ADDRESS";

    /**
    *  The property name in the configuration / properties file specifing the
    *  LDAP Base DN of the public key (e.g., o=Centers for Disease Control and Prevention)
    *  for the PHMTS - config entry name is <code> PUBLIC_KEY_LDAP_BASE_DN </code> .
    */
   public static final String TRANSPORT_OUT_PUBLIC_KEY_LDAP_BASE_DN = "PUBLIC_KEY_LDAP_BASE_DN";

   /**
    *  The property name in the configuration / properties file specifing the
    *  LDAP DN of public key (e.g., cn=Emergint ) for the PHMTS - config entry
    *  name is <code> PUBLIC_KEY_LDAP_DN </code> .
    */
   public static final String TRANSPORT_OUT_PUBLIC_KEY_LDAP_DN = "PUBLIC_KEY_LDAP_DN";

   /**
    *  The property name in the configuration / properties file specifing the
    *  URL of a recipient’s public key certificate for the PHMTS - config entry
    *  name is <code> CERTIFICATE_URL </code>.   This field is used if
    *  ldapKeyRetrieval=false in sender.xml. (By default, ldapKeyRetrieval=true).
    *  Only http:// or file:/// URLs may be specified (not HTTPS), and only BASE64
    *  encoded certificate files are supported.
    */
   public static final String TRANSPORT_OUT_CERTIFICATE_URL = "CERTIFICATE_URL";

    // ************  END ALLOWABLE CONFIG PROPERTY VALUES for PHMTS *************


   // ********* NEDSS BASE SYSTEM (NBS) SET VALUES FOR PHMTS USE ****************
   //  These constanst represent the values that where fetched from Message Out queue
   //  and where initially set by the NBS.
   /**
    *  Represents the pointer to the routemap table pointing to the message route
    *  needed by PHMTS.  This value gets set by the NBS and equates to
    *  <code> MsgOut_Receiving_facility.root_extension_txt </code> .
    */
   public static final String TRANSPORT_OUT_ROUTE_INFO = "routeInfo";

   /**
    *  Represents the service that gets invoked by the SOAP receiver when it
    *  receives the message, and is needed by PHMTS.  This value gets set by
    *  the NBS and equates to <code> MsgOut_Message.interaction_id </code> .
    */
   public static final String TRANSPORT_OUT_SERVICE = "service";
   // ******* END NEDSS BASE SYSTEM (NBS) SET VALUES FOR PHMTS USE **************

   /**
    *  Represents the Disease Message, <i> after </i> transformation, and ready for transport.
    */
   public static final String TRANSPORT_OUT_PAYLOAD_CONTENT = "payloadContent";

   
   /**
    *  Represents the Disease Intervention type assigned by the NBS.
    */
   public static final String TRANSPORT_OUT_INTERACTION_ID = "interactionId";
   
   /**
    *  Represents the status of the message being transported by PHMTS - which sets
    *  this value to either sucess or failure.
    *  @see #TRANSPORT_SUCCESS_STATUS
    *  @see #TRANSPORT_FAILURE_STATUS
    */
   public static final String TRANSPORT_OUT_TRANSPORT_STATUS = "transportStatus";

   /**
    *  Represents the error description set by PHMTS when the message failed
    *  to be transported by PHMTS.
    *  @see #TRANSPORT_SUCCESS_STATUS
    *  @see #TRANSPORT_FAILURE_STATUS
    */
   public static final String TRANSPORT_OUT_TRANSPORT_ERROR_CODE = "transportErrorCode";

   /** Send message to the transport out queue.
     * Errors in the ebXML transport call will bubble an exception to the caller.
     * @param messageDetails the map of the message and its corresponding details to
     * send for further processing.
     * @exception nndmException if error while sending message,
     */
	public void sendMessage(Map<String,String> messageDetails) throws nndmException;


   /** This method retrieves the message details for given the key as
    * indicated by the status.  The data is retreived from the transport out database
    * using the processingStatus field.
    * @param  key String that contains the record identifier to get status information
    * @param  status String that indicates the processing status of the message record; use one
    * of the acceptable values of nndmTransport_Out.PROCESS_QUEUED_STATUS, or
    * nndmTransport_Out.PROCESS_DONE_STATUS, etc.
    * @see #PROCESS_QUEUED_STATUS
    * @see #PROCESS_ATTEMPTED_STATUS
    * @see #PROCESS_SENT_STATUS
    * @see #PROCESS_RECEIVED_STATUS
    * @see #PROCESS_DONE_STATUS
    */
   public Map<String,String> getStatus(String key, String status);



}
/* END CLASS DEFINITION nndmTransport_Out */
