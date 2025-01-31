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

import java.io.*;
import java.util.*;
import java.sql.*;

/** This class is a data abstraction object to the Transport_Out queue; it exposes
 *  methods to send messages to the transport out queue.  This class additionally formats
 *  SQL statements for Microsoft SQL Server 2000 RDBMS operations.
 */
public class nndmTransport_OutMicrosoftImpl implements nndmTransport_Out
{

   /**
    * The single instance of the Transport_Out object for use by all nndm objects.
    * Use the getNndmTransport_Out() to get this instance.
    * @see #getNndmTransport_Out()
    */
   private static final nndmTransport_Out transportOut = new nndmTransport_OutMicrosoftImpl();

   /**
    * Constructs this data access object to the Transport Out Database.
    */
	private nndmTransport_OutMicrosoftImpl()
	{
		super();
	}


   /**
    * Get the global instance of this nndmTransport_OutMicrosoftImpl object for access
    * to the Transport Out Database.
    */
   public static nndmTransport_Out getNndmTransport_Out(){
      return nndmTransport_OutMicrosoftImpl.transportOut;
   }

   /** Send message to the transport out queue.  Specifically, handle an insert into
     * the transportqueue_out table with in a Microsoft SQL Server database.
     * Errors in the ebXML transport call will bubble an exception to the caller.
     * @param messageDetails the map of the message and its corresponding details to
     * send for further processing.
     * @exception nndmException Throws nndmException if error while sending message - inserting message into transport out queue.
     */
	public void sendMessage(Map<String,String> messageDetails) throws nndmException
	{
      String methodSignature = "nndmTransport_OutMicrosoftImpl.sendMessage()";
      //sanity check
      if ((messageDetails == null) || (messageDetails.isEmpty())){
         throw new nndmException(methodSignature + ":  Unable to send message because the given message Map is null or empty.");
      }
      PreparedStatement pstmt = null;
      String data = (String)messageDetails.get("payloadContent");
      Connection con = null;
      try{
      	 con = nndmDatabaseConnection.createConnection();
      	 
         // payloadContent has an IMAGE datatype .
         String sqlInsertMessage = "INSERT INTO transportq_out "
                              + "(messageCreationTime,messageId,payloadContent,processingStatus,routeInfo,service,action,"
                              + "priority,encryption,signature,publicKeyLdapAddress,publicKeyLdapBaseDN,publicKeyLdapDN,certificateURL,"
                              + "destinationFilename,messageRecipient) "
                              + "VALUES (CONVERT(varchar(19),GETDATE(),126),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         pstmt = con.prepareStatement(sqlInsertMessage);
         // get the needed values for the PHMTS to properly process our message.
         int priority;
         try {
            priority = Integer.parseInt(nndmConfig.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_PRIORITY));
         } catch (NumberFormatException nfe){
            // ok, make a note of the situation, but go on...
            Exception e = new nndmException("Unable to set PHMTS priority -  it cannot be parsed as an integer. "
                     + "Invalid config entry for " + nndmTransport_Out.TRANSPORT_OUT_PRIORITY  + " .",nfe);
            priority = 1;
         }
         //  set the values for the SQL insert
         pstmt.setString (1, (String)messageDetails.get(nndmManager.NOTIFIABLE_DISEASE_MESSAGE_ID));
         pstmt.setBinaryStream(2,new ByteArrayInputStream(data.getBytes()),data.length());
         pstmt.setString (3, nndmTransport_Out.PROCESS_QUEUED_STATUS);
         pstmt.setString (4, (String)messageDetails.get(nndmTransport_Out.TRANSPORT_OUT_ROUTE_INFO));
         pstmt.setString (5, (String)messageDetails.get(nndmTransport_Out.TRANSPORT_OUT_SERVICE));
         pstmt.setString (6,nndmTransport_Out.TRANSPORT_ACTION_SEND);
         pstmt.setInt (7, priority);
         pstmt.setString (8, nndmConfig.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_ENCRYPTION));
         pstmt.setString (9, nndmConfig.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_SIGNATURE));
         pstmt.setString (10, nndmConfig.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_PUBLIC_KEY_LDAP_ADDRESS));
         pstmt.setString (11, nndmConfig.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_PUBLIC_KEY_LDAP_BASE_DN));
         pstmt.setString (12, nndmConfig.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_PUBLIC_KEY_LDAP_DN));
         pstmt.setString (13, nndmConfig.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_CERTIFICATE_URL));
         // set the destinationFilename
         pstmt.setString (14, (String)messageDetails.get(nndmTransport_Out.TRANSPORT_OUT_INTERACTION_ID)+(String)messageDetails.get(nndmManager.NOTIFIABLE_DISEASE_MESSAGE_ID));
         // set the messageRecipient
         pstmt.setString (15, (String)messageDetails.get(nndmTransport_Out.TRANSPORT_OUT_ROUTE_INFO));
         int result = pstmt.executeUpdate();
         pstmt.close();
      } catch (Exception e){
         throw new nndmException(methodSignature + ": " + e.getMessage(),e);
      } finally {
         if (pstmt != null){
            try{pstmt.close();}catch(SQLException sqle){}
         }
         if (con != null){
            try{con.close();}catch(SQLException sqle){}
         }
      }

   }

   /** This method retrieves the message details for given the key as
    * indicated by the status.  The data is retreived from the transport out database
    * using the processingStatus field.
    * @param  key String that contains the record identifier to get status information
    * @param  status String that indicates the processing status of the message record; use one
    * of the acceptable values of nndmTransport_Out.PROCESS_QUEUED_STATUS, or
    * nndmTransport_Out.PROCESS_DONE_STATUS, etc.
    * @return Map containing the mapping of column name to its value fetched from the database,
    * the map keys being
    * <ol>
    * <li><code> messageId </code> the record identifier for the message </li>
    * <li><code> transportStatus </code> the returned transport level status from PHMTS ('success' = indicates message
    * was delivered to routeInfo successfully, 'failure' = message delivery failure). </li>
    * <li><code> transportErrorCode </code> the error code describing transport failure </li>
    *  </ol>
    *
    * @see nndmTransport_Out#PROCESS_QUEUED_STATUS
    * @see nndmTransport_Out#PROCESS_ATTEMPTED_STATUS
    * @see nndmTransport_Out#PROCESS_SENT_STATUS
    * @see nndmTransport_Out#PROCESS_RECEIVED_STATUS
    * @see nndmTransport_Out#PROCESS_DONE_STATUS
    */
   public Map<String,String> getStatus(String key, String status){
      Map<String,String> resultSetData = new HashMap<String,String>();
      //sanity check
      if ((key == null) || (key.length() == 0)){
         Exception e = new nndmException("Unable to nndmTransport_OutMicrosoftImpl.getMessage(String key, String status).  The given key is null or empty.");
         return resultSetData;
      }
      String sql = "SELECT transportStatus, transportErrorCode FROM transportq_out WHERE messageId = '"
                 + key
                 + "' AND processingStatus = '"+ status  +"'";
      Statement stmt = null;
      ResultSet rs = null;
      Connection con = null;
      try{
      	con = nndmDatabaseConnection.createConnection();
         // make the call
         stmt = con.createStatement();
         rs = stmt.executeQuery(sql);
         while (rs.next()) {
            String transportStatus = (rs.getString(1) == null)? "STATUS VALUE WAS null." : rs.getString(1).trim();
            String error = (rs.getString(2) == null)? "ERROR CODE WAS null." : rs.getString(2).trim();
            //load the results to the Map
            resultSetData.put("messageId",key);
            resultSetData.put("transportStatus",transportStatus);
            resultSetData.put("transportErrorCode",error);
         }
      } catch (Exception e){
         Exception ne = new nndmException("Error while retrieving message in nndmTransport_OutMicrosoftImpl.getStatus('"+ status + "') for message id = " + key, e);
      } finally {
         if (rs != null){
            try{
               rs.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the result set object in nndmTransport_OutMicrosoftImpl.getStatus() " , sqle);
            }
         }
         if (stmt != null){
            try{
               stmt.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the statement object in nndmTransport_OutMicrosoftImpl.getStatus() " , sqle);
            }
         }
         // be sure to release our connection
         if (con != null){
            try{
               con.close();
            } catch(SQLException sqle){
               Exception e = new nndmException("Could not close the database connection object in nndmTransport_OutMicrosoftImpl.getStatus() " , sqle);
            }
         }
      }
      //return the results
      return resultSetData;
   }



}
/* END CLASS DEFINITION nndmTransport_OutMicrosoftImpl */
