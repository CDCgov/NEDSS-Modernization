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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;



/** This class is a data abstraction object for the Message_Out queue; it exposes
 *  methods to read notification messages, and update status in Message_Out database
 *  tables. This class additionally formats SQL statements for Microsoft SQL Server
 *  2000 RDBMS operations.
 *  <p>
 *  NOTE: It is assummed that the MsgOut_Message table will only allow for inserts by
 *  the entry system, thus we can be assured that no updates will occur while processing
 *  a specific message, and before we can update its status field, two atomic actions by nature.
 *  The restraint is to not use Store Procedures in an attempt to remain RDBMS independant.
 * </p>
*/
public class nndmMessage_OutMicrosoftImpl implements nndmMessage_Out
{

   /**
    * The single instance of the Message_Out object for use by all nndm objects.
    * Use the getNndmMessage_Out() to get this instance.
    * @see #getNndmMessage_Out()
    */
   private static final nndmMessage_Out messageOut = new nndmMessage_OutMicrosoftImpl();

   /**
    * Constructs this data access object to the Message Out Database.
    */
	private nndmMessage_OutMicrosoftImpl()
	{
		super();
	}


   /**
    * Get the global instance of this nndmMessage_OutMicrosoftImpl object for access
    * to the Message Out Database.
    */
   public static nndmMessage_Out getNndmMessage_Out(){
      return nndmMessage_OutMicrosoftImpl.messageOut;
   }

   /** This method returns a collection of message id keys with the given status.
    * @param status String that indicates the status of the message record; use one
    * of the acceptable values of nndmMessage_Out.READY_FOR_TRANSFORM_STATUS, or
    * nndmMessage_Out.TRANSFORM_IN_PROGRESS_STATUS, etc.
    * @return list containing the collection of keys
    * @see nndmMessage_Out#READY_FOR_TRANSFORM_STATUS
    * @see nndmMessage_Out#TRANSFORM_IN_PROGRESS_STATUS
    * @see nndmMessage_Out#TRANSFORM_COMPLETE_STATUS
    * @see nndmMessage_Out#TRANSFORM_ERROR_STATUS
    * @see nndmMessage_Out#TRANSPORT_IN_PROGRESS_STATUS
    * @see nndmMessage_Out#TRANSPORT_ERROR_STATUS
    * @see nndmMessage_Out#TRANSPORT_COMPLETE_STATUS
   */
   public List<MessageKey> listMessages(String status)
   {
      List<MessageKey> idList = new ArrayList<MessageKey>();
      String sql = "SELECT message_uid, msg_id_root_txt FROM msgout_message WHERE status_cd = '"+ status  +"'";
      Statement stmt = null;
      ResultSet rs = null;
      Connection con = null;
      try{
      	con = nndmDatabaseConnection.createConnection();
      	
         // make the call
         stmt = con.createStatement();
         rs = stmt.executeQuery(sql);
         while (rs.next()) {
            //load the results to collection or array
         	MessageKey key = new MessageKey();
         	
         	long id = rs.getLong(1);
         	String rootTxt = rs.getString(2);
         	
         	key.setMessageUid(new Long(id));
         	key.setRootExtText(rootTxt);
         	
            idList.add(key);
         }
      } catch (Exception e){
         Exception ne = new nndmException(" Could not retrieve the list of available message when trying to listMessages('" + status + "').  ", e);
      } finally {
         if (rs != null){
            try{
               rs.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the result set object in listMessages() ", sqle);
            }
         }
         if (stmt != null){
            try{
               stmt.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the statement object in listMessages() " , sqle);
            }
         }
         // be sure to release connection
         if (con != null){
            try{
               con.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the database connection object in listMessages() " , sqle);
            }
         }
      }
       //return the results
       return idList;
    }

   /** This method updates the message record indicated by the key argument as
    * indicated by the second argument.
    * @param  key String that contains the record identifier to set status for
    * @param  status String that indicates the status of the message record; use one
    * of the acceptable values of nndmMessage_Out.READY_FOR_TRANSFORM_STATUS, or
    * nndmMessage_Out.TRANSFORM_IN_PROGRESS_STATUS, etc.
    * @see nndmMessage_Out#READY_FOR_TRANSFORM_STATUS
    * @see nndmMessage_Out#TRANSFORM_IN_PROGRESS_STATUS
    * @see nndmMessage_Out#TRANSFORM_COMPLETE_STATUS
    * @see nndmMessage_Out#TRANSFORM_ERROR_STATUS
    * @see nndmMessage_Out#TRANSPORT_IN_PROGRESS_STATUS
    * @see nndmMessage_Out#TRANSPORT_ERROR_STATUS
    */
   public void setStatus(Long messageUid, String status)
   {
      // make the call
      String sql = "UPDATE msgout_message SET status_cd = '"
           + status + "', status_time = GETDATE()"
           + " WHERE message_uid = " + messageUid ;
      Statement stmt = null;
      Connection con = null;
      try{
      	con = nndmDatabaseConnection.createConnection();
         // perform the update
         stmt = con.createStatement();
         int result = stmt.executeUpdate(sql);
      } catch (Exception e){
          // don't throw exceptions here, just log them accordingly...
          Exception ne = new nndmException(" ****** Could not perform UPDATE when trying to setStatus('" + status +"') on message id = " + messageUid, e);
      } finally {
         if (stmt != null){
            try{
               stmt.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the statement object in setStatus(String key, String status) " , sqle);
            }
         }
         if (con != null){
            try{
               con.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the database connection object in setStatus(String key, String status) " , sqle);
            }
         }
      }
   }


    /** This method adds an error log record to the Message_Out database and
      * sets the status appropriately.
      * @param  key String that contains the record identifier for the message that failed.
      * @param  errorMessage String the contains the short description of the error.
      * @param  status String that indicates the status of the message record; use one
      * of the acceptable values of nndmMessage_Out.TRANSFORM_ERROR_STATUS, or
      * nndmMessage_Out.TRANSPORT_ERROR_STATUS.
     */
   public void setError(Long messageUid, String notificationLocalId, String errorMessage, String status)
   {
      // perform the update - a two part transaction
      // 1) Update the MsgOut_Message.status_cd field to the proper error status
      this.setStatus(messageUid,status);
      // mmmhhhh:  Error message too long for the DB to handle, DB needs to be changed?
      // Also, look for single quotes, replace with a space, so our insert doesn't get hosed...
      String errMessage = "";
      StringTokenizer st = new StringTokenizer((errorMessage.length() < 300) ? errorMessage : errorMessage.substring(0,299),"'");
      while (st.hasMoreTokens()){
         errMessage = errMessage + st.nextToken();
      }
      String sqlInsertErrorLog = "INSERT INTO msgout_error_log "
               + "(message_uid,error_message_txt, record_status_cd, record_status_time, "+
			   "status_cd,status_time, notification_local_uid, processed_log) values("
			   + messageUid + ",'" + errMessage +"','NNDM_ERROR',GETDATE(),'" + status 
			   +"',GETDATE(),'" + notificationLocalId +"','N')";
      
      Statement stmt = null;
      Connection con = null;
      try{
      	con = nndmDatabaseConnection.createConnection();
      	
         // 2) Insert a new row in the MsgOut_Error_log table detailing the
         //    problem with processing the message.
         stmt = con.createStatement();
         // insert a row in the MsgOut_Error_Log
         int resultInsertErrorLog = stmt.executeUpdate(sqlInsertErrorLog);

      } catch (Exception sqle){
         Exception e = new nndmException(" ****** Could not perform INSERT when trying to setError() on message id = " + messageUid, sqle);
      } finally {
         if (stmt != null){
            try{
               stmt.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the statement object in setError() " , sqle);
            }
         }
         if (con != null){
            try{
               con.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the database connection object in setError() " , sqle);
            }
         }
      }
   }


    /** This method retrieves the message record from the message_out database.
     * The SELECT sql is formatted to support Microsoft SQL Server 2000 database implementation.
     * Only records with the given status can be retrieved.
     * @param  key String that contains the record identifier for the message that failed.
     * @param  status String that indicates the status of the message record; use one
     * of the acceptable values of nndmMessage_Out.READY_FOR_TRANSFORM_STATUS, or
     * nndmMessage_Out.TRANSFORM_IN_PROGRESS_STATUS, etc.
     * @return Map containing the mapping of column name to its value fetched from the database,
     * the map keys being
     * <ol>
     * <li><code> msg_id_root_txt </code> the record identifier for the message </li>
     * <li><code> attachment_txt </code> the XML message to transform into disease-specific message </li>
     * <li><code> service </code> the <i> required </i> transport service name for PHMTS to transport the disease-message </li>
     * <li><code> routeInfo </code> the <i> required </i> pointer to the routemap table for PHMTS to transport the disease-message </li>
     *  </ol>
    */
	public Map getMessage(Long messageUid, String status)
	{
      Map resultSetData = new HashMap();
      String sql = "SELECT m.attachment_txt, m.interaction_id, f.root_extension_txt, m.msg_id_root_txt "
                  + "FROM msgout_message m, msgout_receiving_facility f, msgout_receiving_message r "
                  + "WHERE r.receiving_facility_entity_uid = f.receiving_facility_entity_uid "
                  + "AND m.message_uid = r.message_uid "
                  + "AND m.message_uid = "
                  + messageUid + " AND m.status_cd = '"
                  + status  +"'";
      Statement stmt = null;
      ResultSet rs = null;
      Connection con = null;
      String key = null;
      try{
      	con = nndmDatabaseConnection.createConnection();
         // make the call
         stmt = con.createStatement();
         rs = stmt.executeQuery(sql);
         if (rs.next()) {
            String messageData = rs.getString(1);
            String interactionId = rs.getString(2).trim();
            String service = "";
            if (interactionId.equalsIgnoreCase("CDCNND1") ||
                interactionId.equalsIgnoreCase("CDCNND2") ||
                interactionId.equalsIgnoreCase("CDCNND3") ||
                interactionId.equalsIgnoreCase("CDCNND4")){
               // reset the service to valid value for the PHMTS to accept!
               // service = "NNDM";
               service = nndmConfig.getNndmConfigurations(nndmConfig.SERVICE_PROPERTY_NAME);
               if (service=="" || service==null) { service = "NNDM";} else{}
            } else {
               // stop processing.... will never be able to transport without this 'service'!
               throw new nndmException("The msgout_message.interaction_id does not contain a valid value! "
                                 + " Error occurred on message with msgout_message.message_uid=" + messageUid);
            }
            String routeInfo = rs.getString(3);
            if (routeInfo == null || routeInfo.trim().length() == 0){
               // stop processing.... will never be able to transport without this 'route information'!
               throw new nndmException("The msgout_receiving_facility.root_extension_txt does not contain a valid value! "
                                 + " Error occurred on message with msgout_message.message_uid =" + messageUid);
            }
            key = rs.getString(4);
            if (key == null || key.trim().length() == 0){
               throw new nndmException("The msgout_receiving_facility.msg_id_root_txt does not contain a valid value! "
                                 + " Error occurred on message with msgout_message.message_uid =" + messageUid);
            }

            //load the results to the Map
            resultSetData.put(nndmManager.NOTIFIABLE_DISEASE_MESSAGE_ID,key);
            resultSetData.put(nndmMessage_Out.MESSAGE_OUT_ATTACHEMNT,messageData.trim());
            resultSetData.put(nndmTransport_Out.TRANSPORT_OUT_SERVICE,service);
            resultSetData.put(nndmTransport_Out.TRANSPORT_OUT_ROUTE_INFO,routeInfo);
            resultSetData.put(nndmTransport_Out.TRANSPORT_OUT_INTERACTION_ID,interactionId);
         }
      } catch (nndmException ne){
         // Transport process is dependant on the message out values above, thus not there, then really
         // a transport error condition, not a transform error!
         setError(messageUid,key, ne.getMessage(),nndmMessage_Out.TRANSPORT_ERROR_STATUS);
      } catch (Exception e){
         Exception ne = new nndmException("Error while retrieving message in nndmMessage_OutMicrosoftImpl.getMessage('"+ status + "') for message id = " + messageUid, e);
      } finally {
         if (rs != null){
            try{
               rs.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the result set object in nndmMessage_OutMicrosoftImpl.getMessage() " , sqle);
            }
         }
         if (stmt != null){
            try{
               stmt.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the statement object in nndmMessage_OutMicrosoftImpl.getMessage() " , sqle);
            }
         }
         if (con != null){
            try{
               con.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the database connection object in nndmMessage_OutMicrosoftImpl.getMessage() " , sqle);
            }
         }
      }
      //return the results
      return resultSetData;
    }

}
/* END CLASS DEFINITION nndmMessage_OutMicrosoftImpl */

