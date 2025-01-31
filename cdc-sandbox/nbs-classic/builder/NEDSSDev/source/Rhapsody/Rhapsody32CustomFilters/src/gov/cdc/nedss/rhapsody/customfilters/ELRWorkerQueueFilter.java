package gov.cdc.nedss.rhapsody.customfilters;

import gov.cdc.nedss.elr.dt.ELRWorkerQueueDT;

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



public class ELRWorkerQueueFilter extends AbstractFilter {

	private final String INSERT_INTO = "INSERT INTO ";
	private final String INSERT_ELRWORKERQUEUE_ORACLE = " (RECORDID, MESSAGEID, PAYLOADNAME, PAYLOADTEXTCONTENT, "
		+ "SERVICE, ACTION, ARGUMENTS, FROMPARTYID, MESSAGERECIPIENT, ERRORCODE, PROCESSINGSTATUS, ENCRYPTION, RECEIVEDTIME, LASTUPDATETIME) "
		+ "VALUES (?,?,?,EMPTY_CLOB(),?,?,?,?,?,?,?,?,TO_CHAR(SYSDATE,'YYYY-MM-DD\"T\"HH24:MI:SS'), TO_CHAR(SYSDATE,'YYYY-MM-DD\"T\"HH24:MI:SS'))";
			
	private final String INSERT_ELRWORKERQUEUE_SQL = " (messageId, payloadName, "
		+ "payloadTextContent, service, action, arguments, fromPartyId, messageRecipient, errorCode, processingStatus, "
	    + "encryption, receivedTime, lastUpdateTime) VALUES (?,?,?,?,?,?,?,?,?,?,?,CONVERT(varchar(19),GETDATE(),126),CONVERT(varchar(19),GETDATE(),126)) "
		+ "SELECT @@IDENTITY AS 'Identity'";
 
	public static final String[] props = { "DatabaseTableName|*s||Database table name||Please enter table name (ELRWorkerQueue typically)",
										   "DatabaseType|*s||Database type||Please enter 'ORACLE' or 'SQLSERVER'",
										   "DatabaseHost|*s||Database host||Please enter IP or Hostname of Database Instance",
										   "DatabasePort|*s||Database port||Please enter Database port number",
										   "DatabaseName|*s|nbs|Database name||Please enter Database name",
   										   "DatabaseUsername|*s|nbs_ods|Database username||Please enter Database username",
										   "DatabaseUserPassword|*s||Database user password||Please enter Database user password"};

	
	private String databaseTableName;
	private String databaseType;
	private String databaseHost;
	private String databasePort;
	private String databaseName; 
	private String databaseUsername;
	private String databaseUserPassword;

	/**
	 * Constructor that received Rhapsody unique id
	 */
	public ELRWorkerQueueFilter(final FilterInfo info) {
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
		databaseTableName = config.get("DatabaseTableName");
		databaseType = config.get("DatabaseType");
		databaseHost = config.get("DatabaseHost");
		databasePort = config.get("DatabasePort");
		databaseName = config.get("DatabaseName");
		databaseUsername = config.get("DatabaseUsername");
		databaseUserPassword = config.get("DatabaseUserPassword");

		if (databaseTableName == null || (databaseTableName.length() < 1))
			throw new FilterConfigurationException(
					"Failed to set DatabaseTableName property.");
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
	 * Processes a message by loading its properties to a ELRWorkerQueueDT and then persisting that
	 * DT to either ORACLE or SQLSERVER depending on the DatabaseType property.
	 * @throws FilterProcessingException
	 */
	public Message[] doProcessMessage(final Message[] message) throws FilterProcessingException {
        if(message.length == 1){
            Message msg = message[0];
            ELRWorkerQueueDT elrWorkerQueueDT = new ELRWorkerQueueDT();
			try {
			    elrWorkerQueueDT.setMessageId(msg.getProperty("pMessageId").getValue());
			    elrWorkerQueueDT.setPayloadName(msg.getProperty("pPayloadName").getValue());
			    elrWorkerQueueDT.setPayloadTextContent(msg.getProperty("payload").getValue());
			    elrWorkerQueueDT.setService(msg.getProperty("pService").getValue());
			    elrWorkerQueueDT.setAction(msg.getProperty("pAction").getValue());
			    elrWorkerQueueDT.setArguments(msg.getProperty("pArguments").getValue());
			    elrWorkerQueueDT.setFromPartyId(msg.getProperty("pFromPartyId").getValue());
			    elrWorkerQueueDT.setMessageRecipient(msg.getProperty("pMessageRecipient").getValue());
			    elrWorkerQueueDT.setErrorCode(msg.getProperty("pErrorCode").getValue());
			    elrWorkerQueueDT.setProcessingStatus(msg.getProperty("pProcessingStatus").getValue());
			    elrWorkerQueueDT.setEncryption(msg.getProperty("pEncryption").getValue());

				if (databaseType.equalsIgnoreCase("ORACLE")) {
					insertOracleRecords(elrWorkerQueueDT, msg);
				} else {
					insertSQLRecord(elrWorkerQueueDT, msg);
				}
			} catch (Exception e) {
				throw new FilterProcessingException(elrWorkerQueueDT.toString() + ":" + e.getMessage(), e);
			}
        }
        else if(message.length > 1){
            throw new FilterProcessingException("ELRWorkerQueueFilter can only process one message at a time.");
        }

		return message;
	}

	private void insertOracleRecords(ELRWorkerQueueDT dt, Message msg) throws FilterProcessingException {
		try {
			String message = dt.getPayloadTextContent();
			if (!message.startsWith("FHS")) {
				throw new FilterProcessingException("insertOracleRecords; Message does not begin with FHS = "+message);
			}

			String fhsText = message.substring(message.indexOf("FHS"),message.indexOf("\r"));
			insertOracleRecord(dt, createFHSPayload(fhsText, msg), msg, "FHSAppGen");
			message = message.substring(message.indexOf("\r") + 1);
			if (!message.startsWith("BHS")) {
				throw new FilterProcessingException("insertOracleRecords; Message does not continue from FHS with BHS = "+message);
			}
			String bhsText = message.substring(message.indexOf("BHS"),message.indexOf("\r"));
			insertOracleRecord(dt, createBHSPayload(bhsText, msg), msg, "BHSAppGen");
			message = message.substring(message.indexOf("\r") + 1);
	
			if (!message.startsWith("MSH")) {
				throw new FilterProcessingException("insertOracleRecords; Message missing MSH = "+message);
			}
			
			while (message.startsWith("MSH")) {
				String mshText = message.substring(message.indexOf("MSH"),message.indexOf("\r") + 1);
				message = message.substring(message.indexOf("\r") + 1);
				while (!message.startsWith("MSH") && !message.startsWith("BTS")) {
					mshText = mshText + message.substring(0,message.indexOf("\r") + 1);
					message = message.substring(message.indexOf("\r") + 1);
				}
				insertOracleRecord(dt, createMSHPayload(mshText, msg), msg, "");
			}
			
			if (!message.startsWith("BTS")) {
				throw new FilterProcessingException("insertOracleRecords; Message missing BTS = "+message);
			}
			String btsText = message.substring(message.indexOf("BTS"),message.indexOf("\r"));
			insertOracleRecord(dt, createBTSPayload(btsText, msg), msg, "BTSAppGen");
			message = message.substring(message.indexOf("\r") + 1);
			if (!message.startsWith("FTS")) {
				throw new FilterProcessingException("insertOracleRecords; Message missing FTS = "+message);
			}
			String ftsText = message.substring(message.indexOf("FTS"),message.indexOf("\r"));
			insertOracleRecord(dt, createFTSPayload(ftsText, msg), msg, "FTSAppGen");
			if (message.length() > (message.indexOf("\r") + 1)) { 
				message = message.substring(message.indexOf("\r") + 1);
			} else {
				message = "";
			}
		} catch (Exception e) {
			throw new FilterProcessingException(dt.toString() + ":" + e.getMessage(), e);
		}
	}

	private String createFHSPayload(String fhsText, Message msg) {
		return "<Result>ZUH|^~\\&|" + msg.getProperty("ReceivingApplication").getValue() + "|" + msg.getProperty("ReceivingFacility").getValue()
			+ "|" + msg.getProperty("DateTimeOfMessage").getValue() + "||FHS^|" + msg.getProperty("DateTimeOfMessage").getValue() + "|||</EndOfSegment> "
			+ fhsText + " </EndOfSegment></Segment></Result>";		
		
	}
	private String createBHSPayload(String bhsText, Message msg) {
		return "<Result>ZUH|^~\\&|" + msg.getProperty("ReceivingApplication").getValue() + "|" + msg.getProperty("ReceivingFacility").getValue()
			+ "|" + msg.getProperty("DateTimeOfMessage").getValue() + "||BHS^|" + msg.getProperty("DateTimeOfMessage").getValue() + "|||</EndOfSegment> "
			+ bhsText + " </EndOfSegment></Segment></Result>";
	}
	private String createMSHPayload(String mshText, Message msg) {
		return "<Result>ZUH|^~\\&|" + msg.getProperty("ReceivingApplication").getValue() + "|" + msg.getProperty("ReceivingFacility").getValue()
			+ "|" + msg.getProperty("DateTimeOfMessage").getValue() + "||ORU^R01|" + msg.getProperty("MessageControlID").getValue()
			+ "||00000001||NoKey</EndOfSegment> " + mshText + " </EndOfSegment></Segment></Result>";
	}
	private String createBTSPayload(String btsText, Message msg) {
		return "<Result>ZUH|^~\\&|" + msg.getProperty("ReceivingApplication").getValue() + "|" + msg.getProperty("ReceivingFacility").getValue()
			+ "|" + msg.getProperty("DateTimeOfMessage").getValue() + "||BTS^|" + msg.getProperty("DateTimeOfMessage").getValue()
			+ "|||</EndOfSegment> " + btsText + " </EndOfSegment></Segment></Result>";
	}
	private String createFTSPayload(String ftsText, Message msg) {
		return "<Result>ZUH|^~\\&|" + msg.getProperty("ReceivingApplication").getValue() + "|" + msg.getProperty("ReceivingFacility").getValue()
			+ "|" + msg.getProperty("DateTimeOfMessage").getValue() + "||FTS^|" + msg.getProperty("DateTimeOfMessage").getValue()
			+ "|||</EndOfSegment> " + ftsText + " </EndOfSegment></Segment></Result>";
	}
	
	
	/**
	 * @param dt
	 * @throws Exception
	 */
	private void insertOracleRecord(ELRWorkerQueueDT dt, String payload, Message msg, String prefix) throws Exception {
		String receivingApplication = msg.getProperty("ReceivingApplication").getValue();
		String arguments = msg.getProperty("pArguments").getValue();
		String messageRecipient = msg.getProperty("pMessageRecipient").getValue();
		String timestamp = msg.getProperty("pTimestamp").getValue();

		Connection dbConn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		Statement stmt2 = null;

		Long uid = null;
		ResultSet clobRS = null;

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
			ResultSet uidSet = stmt.executeQuery("select nbs_msgine.elrworkerqueue_record_count.nextval from dual");

			uidSet.next();
			uid = new Long(uidSet.getLong(1));
			uidSet.close();
			stmt.close();
			int i = 1;
			String clobData = payload;

			pstmt = (PreparedStatement) dbConn.prepareStatement(INSERT_INTO + databaseTableName + INSERT_ELRWORKERQUEUE_ORACLE);

			pstmt.setLong(i++, uid.longValue());
			if (timestamp == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, timestamp);
			if (timestamp == null)
				pstmt.setString(i++, receivingApplication+prefix+".txt");
			else
				pstmt.setString(i++, receivingApplication+prefix+timestamp+".txt");
			if (dt.getService() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getService());
			if (dt.getAction() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getAction());
			pstmt.setString(i++, arguments);
			if (dt.getFromPartyId() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getFromPartyId());
			pstmt.setString(i++, messageRecipient);
			if (dt.getErrorCode() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getErrorCode());
			if (dt.getProcessingStatus() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getProcessingStatus());
			if (dt.getEncryption() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getEncryption());
			pstmt.executeUpdate();

			
			//now update the empty clob created by "EMPTY_CLOB()" IN insert sql statement
			boolean autoCommit = dbConn.getAutoCommit();
			if (autoCommit == true) {
				dbConn.setAutoCommit(false);
			}
			stmt2 = dbConn.createStatement();
			clobRS = stmt2.executeQuery("SELECT PAYLOADTEXTCONTENT FROM ELRWORKERQUEUE WHERE RECORDID = "+uid.longValue()+" FOR UPDATE NOWAIT");
			if(clobRS.next()){				
				oracle.sql.CLOB clob = (oracle.sql.CLOB)clobRS.getClob("PAYLOADTEXTCONTENT");
				clob.putString(1,clobData);
                clob.trim(clobData.length());
			}

		} catch (Exception e) {
			throw new FilterProcessingException(e.getMessage());
		} finally {
			if (clobRS != null) {
				clobRS.close();
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
	 * @param dt
	 * @throws Exception
	 */
	private void insertSQLRecord(ELRWorkerQueueDT dt, Message msg) throws Exception {
		String arguments = msg.getProperty("pArguments").getValue();
		String messageRecipient = msg.getProperty("pMessageRecipient").getValue();
		String timestamp = msg.getProperty("pTimestamp").getValue();
		String sqlMessageIdPrefix = msg.getProperty("pSQLMessageIdPrefix").getValue();
		
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

			if (dbConn==null)
				throw new FilterProcessingException("Failed to get SQL Server Connection for ELRWorkerQueue insert.");

			int i = 1;
			pstmt = (PreparedStatement) dbConn.prepareStatement(INSERT_INTO + databaseTableName + INSERT_ELRWORKERQUEUE_SQL);

			pstmt.setString(i++, sqlMessageIdPrefix + timestamp);
			pstmt.setString(i++, messageRecipient+timestamp+".txt");
			pstmt.setString(i++, dt.getPayloadTextContent());
			if (dt.getService() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getService());
			if (dt.getAction() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getAction());
			pstmt.setString(i++, arguments);
			if (dt.getFromPartyId() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getFromPartyId());
			pstmt.setString(i++, messageRecipient);
			if (dt.getErrorCode() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getErrorCode());
			if (dt.getProcessingStatus() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getProcessingStatus());
			if (dt.getEncryption() == null)
				pstmt.setNull(i++, Types.VARCHAR);
			else
				pstmt.setString(i++, dt.getEncryption());
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

