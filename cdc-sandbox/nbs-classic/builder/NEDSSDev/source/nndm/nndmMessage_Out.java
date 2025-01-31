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

import java.util.Map;
import java.util.List;

/** This is the interface for the Message_Out queue; it exposes
 *  methods to read notification messages, and update status in Message_Out database
 *  tables. Objects that impliment this interface will format SQL statements for their
 *  particular RDBMS operations.  Lastly, this class characterizes itself as the API
 *  to the NEDSS Base System (NBS), encompassing the NBS nomenclature.
 *  <p>
 *  NOTE: It is assummed that the MsgOut_Message table will only allow for inserts by
 *  the entry system, thus we can be assured that no updates will occur while processing
 *  a specific message, and before we can update its status field, two atomic actions by nature.
 *  The restraint is to not use Store Procedures in an attempt to remain RDBMS independant.
 * </p>
*/
public interface nndmMessage_Out
{

   /**
    * Status flag to indicate that a message is ready for retreival from message
    * out queue and to be transformed into a disease-specific message
    * format - value is <code> A </code> .
    */
   public static final String READY_FOR_TRANSFORM_STATUS = "RDY_FOR_TRNSFRM";

   /**
    * Status flag to indicate that a message is in the process of being transformed
    * into a disease-specific message format - value is <code> TRNSFRM_IN_PROG </code> .
    */
   public static final String TRANSFORM_IN_PROGRESS_STATUS = "TRNSFRM_IN_PROG";


   /**
    * Status flag to indicate that a message has been successfully transformed
    * into a disease-specific message format - value is <code> TRNSFRM_COMP </code> .
    */
   public static final String TRANSFORM_COMPLETE_STATUS = "TRNSFRM_COMP";


   /**
    * Status flag to indicate that an error has occurred while transforming a
    * message into a disease-specific message format, (transformation unsuccessful)
    * - value is <code> TRNSFRM_ERR </code> .
    */
   public static final String TRANSFORM_ERROR_STATUS = "TRNSFRM_ERR";


   /**
    * Status flag to indicate that an error has occurred while <i> transporting </i> a
    * a disease-specific message to the transport out queue, (write to queue failed),
    * or, the ebXML application errored while processing the message.
    * - value is <code> TRNSPRT_ERR </code> .
    */
   public static final String TRANSPORT_ERROR_STATUS = "TRNSPRT_ERR";


   /**
    * Status flag to indicate that a disease-specific message is ready to be processed
    * by <i> PHINMS </i> data interchange application
    * - value is <code> RDY_FOR_PHINMS </code> .
    */
   public static final String READY_FOR_PHINMS_STATUS = "RDY_FOR_PHINMS";


   /**
    * Status flag to indicate that a message has been successfully transformed
    * into a disease-specific message format - value is <code> PHINMS_COMP </code> .
    */
   public static final String PHINMS_COMPLETE_STATUS = "PHINMS_COMP";

   /**
    * Status flag to indicate that a message has been failed while processing in
    * PHINMS stage - value is <code> PHINMS_ERR </code>
    */
   public static final String PHINMS_ERROR_STATUS = "PHINMS_ERR";


   /**
    *  Represents the Disease Message, <i> before </i> disease_specific transformation.
    */
   public static final String MESSAGE_OUT_ATTACHEMNT = "attachment_txt";


   /** This method returns a collection of message id keys with the given status.
    * @param status String that indicates the status of the message record; use one
    * of the acceptable values of nndmMessage_Out.ACTIVE_STATUS, or
    * nndmMessage_Out.TRANSFORM_IN_PROGRESS_STATUS, etc.
    * @return list containing the collection of keys
    * @see #ACTIVE_STATUS
    * @see #TRANSFORM_IN_PROGRESS_STATUS
    * @see #TRANSFORM_COMPLETE_STATUS
    * @see #TRANSFORM_ERROR_STATUS
    * @see #READY_FOR_PHINMS_STATUS
    * @see #TRANSPORT_ERROR_STATUS
    * @see #PHINMS_COMPLETE_STATUS
    */
   public List<MessageKey> listMessages(String status);


    /** This method retrieves the message record from the message_out database.
     * The SELECT sql is formatted to support specific database implementations
     * (currently MSSQL 2000 and Oracle 8i).
     * Only records with a status_cd of <code> nndmMessage_Out.ACTIVE_STATUS </code> can be retrieved.
     * @param  key String that contains the record identifier for the message that failed.
     * @param status String that indicates the status of the message record; use one
     * of the acceptable values of nndmMessage_Out.ACTIVE_STATUS, or
     * nndmMessage_Out.TRANSFORM_IN_PROGRESS_STATUS, etc.
     * @return Map containing the mapping of column name to its value fetched from the database.
     * @see #ACTIVE_STATUS
    */
	public Map<String,String> getMessage(Long messageUid, String status);


   /** This method updates the message record indicated by the key argument as
    * indicated by the second argument.
    * @param  key String that contains the record identifier to set status for
    * @param  status String that indicates the status of the message record; use one
    * of the acceptable values of nndmMessage_Out.ACTIVE_STATUS, or
    * nndmMessage_Out.TRANSFORM_IN_PROGRESS_STATUS, etc.
    * @see #ACTIVE_STATUS
    * @see #TRANSFORM_IN_PROGRESS_STATUS
    * @see #TRANSFORM_COMPLETE_STATUS
    * @see #TRANSFORM_ERROR_STATUS
    * @see #READY_FOR_PHINMS_STATUS
    * @see #TRANSPORT_ERROR_STATUS
    * @see #PHINMS_COMPLETE_STATUS
    */
   public void setStatus(Long messageUid, String status);


    /** This method adds an error log record to the Message_Out database and
      * sets the status appropriately.
      * @param  key String that contains the record identifier for the message that failed.
      * @param  errorMessage String the contains the short description of the error.
      * @param  status String that indicates the status of the message record; use one
      * of the acceptable values of nndmMessage_Out.TRANSFORM_ERROR_STATUS, or
      * nndmMessage_Out.TRANSPORT_ERROR_STATUS.
     */
   public void setError(Long messageUid, String notificationLocalId, String errorMessage, String status);

}
/* END CLASS DEFINITION nndmMessage_Out */

