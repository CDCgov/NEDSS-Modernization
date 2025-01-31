/* ---------------------------------------------------------------------------------------------------
 * Emergint
 * 501 East Broadway
 * Suite 250, Louisville, KY 40202
 * Copyright � 2002
 *  -----------------------------------------------------------------------------------------------------
 *  Author:
 *  Description :
 *-------------------------------------------------------------------------------------------------------
 */
package nndm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/** This class is a data abstraction object to the Transport_Out queue; it exposes
 *  methods to send messages to the transport out queue.  This class additionally formats
 *  SQL statements for Oracle 8i/9i RDBMS operations.
 */
public class nndmTransport_OutOracleImpl implements nndmTransport_Out {

	/**
	 * The single instance of the Transport_Out object for use by all nndm objects.
	 * Use the getNndmTransport_Out() to get this instance.
	 * @see #getNndmTransport_Out()
	 */
	private static final nndmTransport_Out transportOut = new nndmTransport_OutOracleImpl();

	/**
	 * Constructs this data access object to the Transport Out Database.
	 */
	private nndmTransport_OutOracleImpl() {
		super();
	}

	/**
	 * Get the global instance of this nndmTransport_OutOracleImpl object for access
	 * to the Transport Out Database.
	 */
	public static nndmTransport_Out getNndmTransport_Out() {
		return nndmTransport_OutOracleImpl.transportOut;
	}

	/** Send message to the transport out queue.  Specifically, handle an insert into
	 * the transportq_out table with in an Oracle database.
	 * Errors in the ebXML transport call will bubble an exception to the caller.
	 * @param messageDetails the map of the message and its corresponding details to
	 * send for further processing.
	 * @exception nndmException if error while sending message,
	 */

	public Integer getNextVal() throws nndmException {
		String methodSignature = "nndmTransport_OutOracleImpl.getNextVal()";
		String nextValStmt = "select TRANSPORT_RECORD_COUNT.nextval from dual";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;

		int value;
		try {
			con = nndmDatabaseConnection.createConnection();

			pstmt = con.prepareStatement(nextValStmt);
			rs = pstmt.executeQuery();
			rs.next();
			value = rs.getInt(1);
		} catch (Exception e) {
			throw new nndmException(methodSignature + ": " + e.getMessage(), e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqle) {
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqle) {
				}

			}
		}
		return new Integer(value);

	}

	public void sendMessage(Map<String,String> messageDetails) throws nndmException {
		String methodSignature = "nndmTransport_OutOracleImpl.sendMessage()";
		//sanity check
		if ((messageDetails == null) || (messageDetails.isEmpty())) {
			throw new nndmException(
					methodSignature
							+ ":  Unable to send message because the given message Map is null or empty.");
		}

		PreparedStatement pstmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		String data = ((String) messageDetails
				.get(nndmTransport_Out.TRANSPORT_OUT_PAYLOAD_CONTENT)).trim();
		String sqlInsertMessage = "";
		java.sql.Blob messageBlob = null;

		Connection con = null;
		try {

			Integer index = getNextVal();
			con = nndmDatabaseConnection.createConnection();
			// payloadContent has a BLOB datatype .
			sqlInsertMessage = "INSERT INTO transportq_out "
					+ "(recordId,messageCreationTime,messageId,payloadContent,processingStatus,routeInfo,service,action,"
					+ "priority,encryption,signature,publicKeyLdapAddress,publicKeyLdapBaseDN,publicKeyLdapDN,certificateURL,"
					+ "destinationFilename,messageRecipient) "
					+ "VALUES (?,TO_CHAR(SYSDATE,'YYYY-MM-DD\"T\"HH24:MI:SS'),?, EMPTY_BLOB(),?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//dbConn.releaseConnection();
			pstmt = con.prepareStatement(sqlInsertMessage);
			// get the needed values for the PHMTS to properly process our message.
			int priority;
			try {
				priority = Integer
						.parseInt(nndmConfig
								.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_PRIORITY));
			} catch (NumberFormatException nfe) {
				// ok, make a note of the situation, but go on...
				Exception e = new nndmException(
						"Unable to set PHMTS priority -  it cannot be parsed as an integer. "
								+ "Invalid config entry for "
								+ nndmTransport_Out.TRANSPORT_OUT_PRIORITY
								+ " .", nfe);
				priority = 1;
			}
			//  set the values for the SQL insert
			int i = 0;
			pstmt.setInt(++i, index.intValue());
			pstmt.setString(++i, (String) messageDetails
					.get(nndmManager.NOTIFIABLE_DISEASE_MESSAGE_ID));
			pstmt.setString(++i, nndmTransport_Out.PROCESS_QUEUED_STATUS);
			pstmt.setString(++i, (String) messageDetails
					.get(nndmTransport_Out.TRANSPORT_OUT_ROUTE_INFO));
			pstmt.setString(++i, (String) messageDetails
					.get(nndmTransport_Out.TRANSPORT_OUT_SERVICE));
			pstmt.setString(++i, nndmTransport_Out.TRANSPORT_ACTION_SEND);
			pstmt.setInt(++i, priority);
			pstmt
					.setString(
							++i,
							nndmConfig
									.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_ENCRYPTION));
			pstmt
					.setString(
							++i,
							nndmConfig
									.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_SIGNATURE));
			pstmt
					.setString(
							++i,
							nndmConfig
									.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_PUBLIC_KEY_LDAP_ADDRESS));
			pstmt
					.setString(
							++i,
							nndmConfig
									.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_PUBLIC_KEY_LDAP_BASE_DN));
			pstmt
					.setString(
							++i,
							nndmConfig
									.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_PUBLIC_KEY_LDAP_DN));
			pstmt
					.setString(
							++i,
							nndmConfig
									.getNndmConfigurations(nndmTransport_Out.TRANSPORT_OUT_CERTIFICATE_URL));
			// set the destinationFilename
			pstmt.setString(++i, (String) messageDetails
					.get(nndmTransport_Out.TRANSPORT_OUT_INTERACTION_ID)
					+ (String) messageDetails
							.get(nndmManager.NOTIFIABLE_DISEASE_MESSAGE_ID));
			// set the messageRecipient
			pstmt.setString(++i, (String) messageDetails
					.get(nndmTransport_Out.TRANSPORT_OUT_ROUTE_INFO));
			pstmt.executeUpdate();
			//now update the empty blob created by "EMPTY_BLOB()" IN insert sql statement
			boolean autoCommit = con.getAutoCommit();
			if (autoCommit == true) {
				//System.out.println("getAutoCommit is true" );
				con.setAutoCommit(false);
			}
			stmt2 = con.createStatement();
			stmt2
					.execute("select payloadcontent from transportq_out where recordid = "
							+ index.longValue() + " for update");
			rs = stmt2.getResultSet();
			rs.next();

			 //messageBlob = rs.getBlob("payloadcontent");
			  messageBlob = (java.sql.Blob) rs.getBlob("payloadcontent");
			writeBinaryDataToBlob(messageBlob, data);
			con.setAutoCommit(autoCommit); //set autoCommit to what is used to be

		} catch (Exception e) {
			throw new nndmException(methodSignature + ": " + e.getMessage(), e);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqle) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
				}
			}

		}

	}

	private void writeBinaryDataToBlob(java.sql.Blob messageBLOB, String blobData)
			throws Exception {
		messageBLOB.setBytes(1, blobData.getBytes());
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
	 * </ol>
	 * @see nndmTransport_Out#PROCESS_QUEUED_STATUS
	 * @see nndmTransport_Out#PROCESS_ATTEMPTED_STATUS
	 * @see nndmTransport_Out#PROCESS_SENT_STATUS
	 * @see nndmTransport_Out#PROCESS_RECEIVED_STATUS
	 * @see nndmTransport_Out#PROCESS_DONE_STATUS
	 */
	public Map<String,String> getStatus(String key, String status) {
		Map<String,String> resultSetData = new HashMap<String,String>();
		//sanity check
		if ((key == null) || (key.length() == 0)) {
			Exception e = new nndmException(
					"Unable to nndmTransport_OutOracleImpl.getMessage(String key, String status).  The given key is null or empty.");
			return resultSetData;
		}
		String sql = "SELECT transportStatus, transportErrorCode FROM transportq_out WHERE messageId = '"
				+ key + "' AND processingStatus = '" + status + "'";
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = nndmDatabaseConnection.createConnection();
			// make the call
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String transportStatus = (rs.getString(1) == null) ? "STATUS VALUE WAS null."
						: rs.getString(1).trim();
				String error = (rs.getString(2) == null) ? "ERROR CODE WAS null."
						: rs.getString(2).trim();
				//load the results to the Map
				resultSetData.put(nndmManager.NOTIFIABLE_DISEASE_MESSAGE_ID,
						key);
				resultSetData.put(
						nndmTransport_Out.TRANSPORT_OUT_TRANSPORT_STATUS,
						transportStatus);
				resultSetData.put(
						nndmTransport_Out.TRANSPORT_OUT_TRANSPORT_ERROR_CODE,
						error);
			}
		} catch (Exception e) {
			Exception ne = new nndmException(
					"Error while retrieving message in nndmTransport_OutOracleImpl.getStatus('"
							+ status + "') for message id = " + key, e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					Exception e = new nndmException(
							"Could not close the result set object in nndmTransport_OutOracleImpl.getStatus() ",
							sqle);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqle) {
					Exception e = new nndmException(
							"Could not close the statement object in nndmTransport_OutOracleImpl.getStatus() ",
							sqle);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					Exception e = new nndmException(
							"Could not close the connection object in nndmTransport_OutOracleImpl.getStatus() ",
							sqle);
				}
			}

		}
		//return the results
		return resultSetData;
	}

}
/* END CLASS DEFINITION nndmTransport_OutOracleImpl */
