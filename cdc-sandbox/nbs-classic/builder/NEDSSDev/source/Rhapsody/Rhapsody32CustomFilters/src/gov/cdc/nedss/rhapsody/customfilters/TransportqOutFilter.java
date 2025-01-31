package gov.cdc.nedss.rhapsody.customfilters;

import gov.cdc.nedss.nnd.dt.TransportQOutDT;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.orchestral.rhapsody.message.Message;
import com.orchestral.rhapsody.module.Configuration;
import com.orchestral.rhapsody.module.filter.AbstractFilter;
import com.orchestral.rhapsody.module.filter.FilterConfigurationException;
import com.orchestral.rhapsody.module.filter.FilterInfo;
import com.orchestral.rhapsody.module.filter.FilterProcessingException;



public class TransportqOutFilter extends AbstractFilter {
	private final String INSERT_TRANSPORTQOUT_ORACLE = "INSERT INTO transportq_out "
			+ "(recordId,messageCreationTime,messageId,payloadContent,processingStatus,routeInfo,service,action,"
			+ "priority,encryption,signature,publicKeyLdapAddress,publicKeyLdapBaseDN,publicKeyLdapDN,certificateURL,"
			+ "destinationFilename,messageRecipient) "
			+ "VALUES (?,TO_CHAR(SYSDATE,'YYYY-MM-DD\"T\"HH24:MI:SS'),?,EMPTY_BLOB(),?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final String INSERT_TRANSPORTQOUT_SQL = "INSERT INTO transportq_out "
			+ "(messageCreationTime,messageId,payloadContent,processingStatus,routeInfo,service,action,"
			+ "priority,encryption,signature,publicKeyLdapAddress,publicKeyLdapBaseDN,publicKeyLdapDN,certificateURL,"
			+ "destinationFilename,messageRecipient) "
			+ "VALUES (CONVERT(varchar(19),GETDATE(),126),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
			+ "SELECT @@IDENTITY AS 'Identity'";

	private final String NEW_CASE_NOTIFICATION = "CDCNND1";
	private final String UPDATED_CASE_NOTIFICATION = "CDCNND2";

	private final String FINAL = "F";
	private final String CORRECTED = "C";

	private String databaseType;
	private String databaseHost;
	private String databasePort;
	private String databaseName;
	private String databaseUsername;
	private String databaseUserPassword;

	public static final String[] props = { "DatabaseType|*s||Database type||Please enter 'ORACLE' or 'SQLSERVER'",
										   "DatabaseHost|*s||Database host||Please enter IP or Hostname of Database Instance",
										   "DatabasePort|*s||Database port||Please enter Database port number",
										   "DatabaseName|*s|nbs|Database name||Please enter Database name",
   										   "DatabaseUsername|*s|nbs_ods|Database username||Please enter Database username",
										   "DatabaseUserPassword|*s||Database user password||Please enter Database user password"};

	/**
	 * Constructor that received Rhapsody unique id
	 */
	public TransportqOutFilter(final FilterInfo info) {
		super(info);
	}

	/**
	 * Return the list of properties that are used to configure the filter.
	 * See ‘Filter Properties’ section for the layout of these strings.
	 * @return a property list for the one required string property "DatabaseType".
	 */
	public String[] getPropertyList() {
		return props;
	}

	/**
	 * Returns the type of kind of concurrency this filter will work with.
	 * @returns Filter.LIMITED_CONCURRENCY
	 */
	public FilterKind getKind() {
		return FilterKind.LIMITED_CONCURRENCY;
	}

	/**
	 * Initializes this filter with the passed in DatabaseType - ORACLE or SQLSERVER.
	 */
	public void doConfigure(Configuration config) throws FilterConfigurationException {
		databaseType = config.get("DatabaseType");
		databaseHost = config.get("DatabaseHost");
		databasePort = config.get("DatabasePort");
		databaseName = config.get("DatabaseName");
		databaseUsername = config.get("DatabaseUsername");
		databaseUserPassword = config.get("DatabaseUserPassword");

		if (databaseType == null || (!databaseType.equalsIgnoreCase("ORACLE") && !databaseType.equalsIgnoreCase("SQLSERVER")))
			throw new FilterConfigurationException(
					"Failed to set DatabaseType property correctly.  Should be 'ORACLE' or 'SQLSERVER'.");
		if (databaseHost == null || (databaseHost.length() < 1))
			throw new FilterConfigurationException(
					"Failed to set DatabaseHost property.");
		if (databasePort == null || (databasePort.length() < 1))
			throw new FilterConfigurationException(
					"Failed to set DatabasePort property.");
		if (databaseName == null || (databaseName.length() < 1))
			throw new FilterConfigurationException(
					"Failed to set DatabaseName property.");
		if (databaseUsername == null || (databaseUsername.length() < 1))
			throw new FilterConfigurationException(
					"Failed to set DatabaseUsername property.");
		if (databaseUserPassword == null || (databaseUserPassword.length() < 1))
			throw new FilterConfigurationException(
					"Failed to set DatabaseUserPassword property.");
	}

	/**
	 * Processes a message by loading its properties to a TransportQOutDT and then persisting that 
	 * DT to either ORACLE or SQLSERVER depending on the DatabaseType property.
	 * @throws FilterProcessingException 
	 */
	public Message[] doProcessMessage(final Message[] message) throws FilterProcessingException {
        if(message.length == 1){
            Message msg = message[0];
            
            //Build destination_filename column
            //Consists of Report status code (pReportStatusCd) -> "CDCNND1" for new/final notificaitons or "CDCNND2" for updated/corrected notifications
            //concatenated with Notification local id (pNotificationID). 
            //pReportStatusCd is the report_status_cd field from CN_TRANSPORTQ_OUT table.
            //Note, no support for "CDCNND3" for summary notifications yet.
            String caseType = "";
            if (msg.getProperty("pReportStatusCd").getValue().equalsIgnoreCase(FINAL))
            	caseType = NEW_CASE_NOTIFICATION;
            else if (msg.getProperty("pReportStatusCd").getValue().equalsIgnoreCase(CORRECTED))
            	caseType = UPDATED_CASE_NOTIFICATION;            
            String destinationFilename = caseType + msg.getProperty("pNotificationID").getValue();
            
            TransportQOutDT transportQOutDT = new TransportQOutDT();
			try {
				transportQOutDT.setPayloadContent(msg.getProperty("pPHINMessageContent2").getValue());
				transportQOutDT.setDestinationFilename(destinationFilename);
				transportQOutDT.setService(msg.getProperty("pPHINService").getValue());
				transportQOutDT.setAction(msg.getProperty("pPHINAction").getValue());
				transportQOutDT.setEncryption(msg.getProperty("pPHINEncryption").getValue());
				transportQOutDT.setRouteInfo(msg.getProperty("pPHINRoute").getValue());
				transportQOutDT.setSignature(msg.getProperty("pPHINSignature").getValue());
				transportQOutDT.setProcessingStatus(msg.getProperty("pPHINProcessingStatus").getValue());
				transportQOutDT.setPublicKeyLdapAddress(msg.getProperty("pPHINPublicKeyLdapAddress").getValue());
				transportQOutDT.setPublicKeyLdapBaseDN(msg.getProperty("pPHINPublicKeyLdapBaseDN").getValue());
				transportQOutDT.setPublicKeyLdapDN(msg.getProperty("pPHINPublicKeyLdapDN").getValue());
				transportQOutDT.setMessageRecipient(msg.getProperty("pPHINMessageRecipient").getValue());
				transportQOutDT.setMessageId(msg.getProperty("pPHINMessageID").getValue());
				transportQOutDT.setPriority(Integer.valueOf(msg.getProperty("pPHINPriority").getValue()));

				if (databaseType.equalsIgnoreCase("ORACLE")) {
					insertOracleRecord(transportQOutDT);
				} else {
					insertSQLRecord(transportQOutDT);
				}
			} catch (Exception e) {
				throw new FilterProcessingException(e.getMessage(), e);
			}
        }
        else if(message.length > 1){
            throw new FilterProcessingException("TransportQOut Filter can only process one message at a time.");
        }

		return message;
	}

	/**
	 * Code lifted from the TransportQOutDAOImpl.java from NBS
	 * @param dt
	 * @throws Exception
	 */
	private void insertOracleRecord(TransportQOutDT dt) throws Exception {
		Connection dbConn = null;
		oracle.sql.BLOB messageBLOB = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		Statement stmt2 = null;

		Long uid = null;
		ResultSet blobRS = null;

		try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");

			String dbString = "jdbc:oracle:thin:@"
				 + databaseHost
				 + ":"
				 + databasePort
				 + ":"
				 + databaseName;
			
			dbConn = DriverManager.getConnection(dbString,databaseUsername,databaseUserPassword);

			stmt = dbConn.createStatement();
			ResultSet uidSet = stmt
					.executeQuery("select nbs_msgoute.transport_record_count.nextval from dual");

			uidSet.next();
			uid = new Long(uidSet.getLong(1));
			uidSet.close();
			stmt.close();
			int i = 1;
			String blobData = dt.getPayloadContent();

			pstmt = (PreparedStatement) dbConn
					.prepareStatement(INSERT_TRANSPORTQOUT_ORACLE);

			pstmt.setLong(i++, uid.longValue());
			if (dt.getMessageId() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getMessageId());
			if (dt.getProcessingStatus() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getProcessingStatus());
			if (dt.getRouteInfo() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getRouteInfo());
			if (dt.getService() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getService());
			if (dt.getAction() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getAction());
			if (dt.getPriority() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setInt(i++, dt.getPriority().intValue());
			if (dt.getEncryption() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getEncryption());
			if (dt.getSignature() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getSignature());
			if (dt.getPublicKeyLdapAddress() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getPublicKeyLdapAddress());
			if (dt.getPublicKeyLdapBaseDN() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getPublicKeyLdapBaseDN());
			if (dt.getPublicKeyLdapDN() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getPublicKeyLdapDN());
			if (dt.getCertificateURL() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getCertificateURL());
			if (dt.getDestinationFilename() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getDestinationFilename());
			if (dt.getMessageRecipient() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getMessageRecipient());
			pstmt.executeUpdate();

			//now update the empty blob created by "EMPTY_BLOB()" IN insert sql statement
			boolean autoCommit = dbConn.getAutoCommit();
			if (autoCommit == true) {
				dbConn.setAutoCommit(false);
			}
			stmt2 = dbConn.createStatement();
            stmt2.execute("select payloadcontent from transportq_out where recordid = "+uid.longValue()+" for update");
			blobRS = stmt2.getResultSet();
			blobRS.next();
			messageBLOB = (oracle.sql.BLOB) blobRS.getBlob("payloadcontent");
			writeBinaryDataToBlob(messageBLOB, blobData);
		} catch (Exception e) {
			throw new FilterProcessingException(e.getMessage());
		} finally {
			if (blobRS != null) {
				blobRS.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (stmt2 != null) {
				stmt2.close();
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new FilterProcessingException(e.getMessage());
				}
			}
			if (dbConn != null && !dbConn.isClosed()) {
				dbConn.close();
			}
		}
	}

	/**
	 * Code lifted from the TransportQOutDAOImpl.java from NBS
	 * @param messageBLOB
	 * @param blobData
	 * @throws Exception
	 */
	private void writeBinaryDataToBlob(oracle.sql.BLOB messageBLOB, String blobData) throws Exception {
		java.io.InputStream is = new ByteArrayInputStream(blobData.getBytes());
		java.io.OutputStream os = messageBLOB.getBinaryOutputStream();

		byte[] inBytes = new byte[65534];
		int numBytes = is.read(inBytes);

		while (numBytes > 0) {
			os.write(inBytes, 0, numBytes);
			numBytes = is.read(inBytes);
		}

		os.flush();
		os.close();
	}

	/**
	 * Code lifted from the TransportQOutDAOImpl.java from NBS
	 * @param dt
	 * @throws Exception
	 */
	private void insertSQLRecord(TransportQOutDT dt) throws Exception {
		Connection dbConn = null;
		PreparedStatement pstmt = null;

		try {
			String dbString = "jdbc:sqlserver://"
				              + databaseHost
				              + ":"
				              + databasePort
				              + ";DatabaseName="
				              + databaseName
  				              + ";user="
  				              + databaseUsername
					          + ";password="
					          + databaseUserPassword;

			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 dbConn = java.sql.DriverManager.getConnection(dbString);			
//			 dbConn = java.sql.DriverManager.getConnection("jdbc:sqlserver://cdcdevdbsql:1433;DatabaseName=nbs_msgoute;user=nbs_ods;password=ods");			

			if (dbConn==null) 
				throw new FilterProcessingException("Failed to get SQL Server Connection for TransportQOut insert.");
			
			int i = 1;
			pstmt = (PreparedStatement) dbConn.prepareStatement(INSERT_TRANSPORTQOUT_SQL);

			if (dt.getMessageId() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getMessageId());
			pstmt.setBinaryStream(i++, new ByteArrayInputStream(dt
					.getPayloadContent().getBytes("utf-8")), dt
					.getPayloadContent().length());
			if (dt.getProcessingStatus() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getProcessingStatus());
			if (dt.getRouteInfo() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getRouteInfo());
			if (dt.getService() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getService());
			if (dt.getAction() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getAction());
			if (dt.getPriority() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setInt(i++, dt.getPriority().intValue());
			if (dt.getEncryption() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getEncryption());
			if (dt.getSignature() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getSignature());
			if (dt.getPublicKeyLdapAddress() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getPublicKeyLdapAddress());
			if (dt.getPublicKeyLdapBaseDN() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getPublicKeyLdapBaseDN());
			if (dt.getPublicKeyLdapDN() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getPublicKeyLdapDN());
			if (dt.getCertificateURL() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getCertificateURL());
			// set the destinationFilename
			if (dt.getDestinationFilename() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getDestinationFilename());
			// set the messageRecipient
			if (dt.getMessageRecipient() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getMessageRecipient());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.close();
		} catch (Exception e) {
			throw new FilterProcessingException(e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();

				} catch (SQLException e) {
					throw new FilterProcessingException(e.getMessage());
				}
			}//end of if
			if (dbConn != null && !dbConn.isClosed()) {
				dbConn.close();
			}//end of final
		}
	}
}

