package gov.cdc.nedss.rhapsody.customfilters;

import gov.cdc.nedss.elr.dt.NbsInterfaceDT;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.orchestral.rhapsody.message.Message;
import com.orchestral.rhapsody.module.Configuration;
import com.orchestral.rhapsody.module.filter.AbstractFilter;
import com.orchestral.rhapsody.module.filter.FilterConfigurationException;
import com.orchestral.rhapsody.module.filter.FilterInfo;
import com.orchestral.rhapsody.module.filter.FilterProcessingException;



public class NbsInterfaceFilter  extends AbstractFilter {

	public static final String INSERT_NBS_INTERFACE_ORA = "insert into NBS_Interface(" 
			+" payload," 
			+" imp_exp_ind_cd, "	
			+" record_status_cd,"
			+" record_status_time, "
			+" add_time, "
			+" system_nm,"
			+" doc_type_cd)"
			+" values(EMPTY_BLOB(),?,?,?,?,?,?) ";
	public static final String INSERT_NBS_INTERFACE_SQL ="insert into NBS_Interface(" 
			+" payload," 
			+" imp_exp_ind_cd, "	
			+" record_status_cd,"
			+" record_status_time, "
			+" add_time, "
			+" system_nm,"
			+" doc_type_cd)"
			+" values(?,?,?,?,?,?,?) SELECT @@IDENTITY AS 'Identity'";

	private String databaseType;
	private String databaseHost;
	private String databasePort;
	private String databaseName;
	private String databaseUsername;
	private String databaseUserPassword;

	public static final String[] props = { "DatabaseType|*s||Database type||Please enter 'ORACLE' or 'SQL SERVER'",
		"DatabaseHost|*s||Database host||Please enter IP or Hostname of Database Instance",
		"DatabasePort|*s||Database port||Please enter Database port number",
		"DatabaseName|*s|nbs|Database name||Please enter Database name",
		"DatabaseUsername|*s|nbs_ods|Database username||Please enter Database username",
	"DatabaseUserPassword|*s||Database user password||Please enter Database user password"};
	/**
	 * Constructor that received Rhapsody unique id
	 */
	public NbsInterfaceFilter(final FilterInfo info) {
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

		if (databaseType == null || ((!databaseType.equalsIgnoreCase("ORACLE")) && (!databaseType.equalsIgnoreCase("SQL SERVER"))))
			throw new FilterConfigurationException(
					"Failed to set DatabaseType property correctly.  Should be 'ORACLE' or 'SQL SERVER'.");
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
			Timestamp time = new Timestamp(new Date().getTime());
			NbsInterfaceDT nbsInterfaceDT = new NbsInterfaceDT();
			try {
				nbsInterfaceDT.setXmlPayLoadContent(msg.getProperty("pELRMessageContent").getValue());
				nbsInterfaceDT.setImpExpIndCd("I");
				nbsInterfaceDT.setRecordStatusCd("QUEUED");
				nbsInterfaceDT.setSystemNm("NBS");
				nbsInterfaceDT.setDocTypeCd("11648804");
				nbsInterfaceDT.setAddTime(time);
				nbsInterfaceDT.setRecordStatusTime(time);
				//nbsInterfaceDT.setProcessingStatus(msg.getProperty("pPHINProcessingStatus"));
				if(databaseType.equalsIgnoreCase("ORACLE")){
					insertNBSInterfaceOracle(nbsInterfaceDT);
				}
				else{
					insertNBSInterfaceSQL(nbsInterfaceDT);
				}
			} catch (Exception e) {
				System.out.println("databaseType"+databaseType+", databasePort"+databasePort+", databaseName"+databaseName+
						", databaseUsername"+databaseUsername+" Error thrown! And Exception caught:"+e);
				throw new FilterProcessingException(e.getMessage());
			}
		}
		else if(message.length > 1){
			throw new FilterProcessingException("NbsInterfaceFilter can only process one message at a time.");
		}

		return message;
	}

	private  void  insertNBSInterfaceOracle( NbsInterfaceDT dt) throws Exception
	{	
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		Statement stmt2 = null;
		int resultCount = 0;
		int i = 1;
		String sql="";
		Long interfaceUid = null;
		sql = INSERT_NBS_INTERFACE_ORA;
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String dbString = "jdbc:oracle:thin:@"
					+ databaseHost
					+ ":"
					+ databasePort
					+ ":"
					+ databaseName;
			dbConnection = DriverManager.getConnection(dbString,databaseUsername,databaseUserPassword);
			pStmt = dbConnection.prepareStatement(sql);   
			pStmt.setString(i++, dt.getImpExpIndCd());
			pStmt.setString(i++, dt.getRecordStatusCd());
			pStmt.setTimestamp(i++, dt.getRecordStatusTime());
			pStmt.setTimestamp(i++, dt.getAddTime());
			pStmt.setString(i++, dt.getSystemNm()); 
			pStmt.setString(i++, dt.getDocTypeCd());
			resultCount = pStmt.executeUpdate();
			if (resultCount != 1)
			{
				System.out.println("NbsInterfaceFilter.insertNBSInterfaceOracle:  none or more than one records inserted at a time, resultCount ="+resultCount);
					throw new Exception("NbsInterfaceFilter.insertNBSInterfaceOracle:  none or more than one records inserted at a time, resultCount = " 
			+ resultCount);
			}
			releaseConnection(dbConnection);
			dbConnection = DriverManager.getConnection(dbString,databaseUsername,databaseUserPassword);
			stmt2 = dbConnection.createStatement();
			stmt2.execute("select max(nbs_interface_uid) from nbs_interface where imp_exp_ind_cd='I'  and doc_type_cd='11648804'");
			rs = stmt2.getResultSet();
			while(rs.next()){
				interfaceUid = new Long(rs.getLong(1));
			}
			releaseConnection(dbConnection);
			pStmt.close();
			rs.close();
			dbConnection = DriverManager.getConnection(dbString,databaseUsername,databaseUserPassword);
			boolean autoCommit = dbConnection.getAutoCommit();
			if (autoCommit == true) {
				dbConnection.setAutoCommit(false);
			}
			stmt2 = dbConnection.createStatement();
			stmt2.execute("SELECT payload FROM NBS_Interface WHERE nbs_interface_uid ="+interfaceUid.longValue()+" FOR UPDATE");
			rs = stmt2.getResultSet();
			oracle.sql.BLOB blob = null;
			while(rs.next()){
				blob = (oracle.sql.BLOB)(rs.getBlob("PAYLOAD"));
			}
	
			writeBinaryDataToBlob(blob, dt.getXmlPayLoadContent());
			dbConnection.commit();
		}
		catch(SQLException sqlex)
		{
			dbConnection.rollback();
			String errorMsg = "NbsInterfaceDAOImpl.insertNBSInterfaceOracle:  SQLException while inserting BLOB into NBS_Interface:" +", for databaseType:'"+databaseType+"', and exceptions is:"+sqlex;
			System.out.println(errorMsg);
			throw new Exception( errorMsg, sqlex );
		}
		catch(Exception ex)
		{
			dbConnection.rollback();
			String errorMsg = "NbsInterfaceDAOImpl.insertNBSInterfaceOracle:  SQLException while inserting BLOB into NBS_Interface:" +", for databaseType:'"+databaseType+"'\n"+ ex;
			System.out.println(errorMsg);
			throw new Exception(errorMsg + ex.toString(), ex);
		}
		finally{
			try{
				if (pStmt != null) {
					pStmt.close();
				}
				if (stmt2 != null) {
					stmt2.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (dbConnection != null && !dbConnection.isClosed()) {
					dbConnection.close();
				}
			}
			catch (Exception ex){
				String errorMsg = "NbsInterfaceDAOImpl.insertNBSInterfaceOracle:  SQLException while inserting BLOB into NBS_Interface:" +", for databaseType:'"+databaseType+"'";
				System.out.println(errorMsg);
				throw new Exception(errorMsg , ex);
				
			}

		}
	}

	protected void releaseConnection(Connection connection)
			throws Exception {

		try {

			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException sqlex) {
			throw new Exception("Unable to release connection, Please check!", sqlex);
		}
	} 
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

	private void insertNBSInterfaceSQL(NbsInterfaceDT dt) throws Exception{
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		int resultCount = 0;
		int i = 1;
		ResultSet rs = null;
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();

			String dbString = "jdbc:sqlserver://" + databaseHost + ":"
					+ databasePort
					+ ";SelectMethod=cursor;DatabaseName="
					+ databaseName;
			dbConnection = DriverManager.getConnection(dbString,databaseUsername,databaseUserPassword);
			pStmt = dbConnection.prepareStatement(INSERT_NBS_INTERFACE_SQL);   
			pStmt.setString(i++, dt.getXmlPayLoadContent());
			pStmt.setString(i++, dt.getImpExpIndCd());
			pStmt.setString(i++, dt.getRecordStatusCd());
			pStmt.setTimestamp(i++, dt.getRecordStatusTime());
			pStmt.setTimestamp(i++, dt.getAddTime());
			pStmt.setString(i++, dt.getSystemNm());
			pStmt.setString(i++, dt.getDocTypeCd());
			resultCount = pStmt.executeUpdate();
			if (resultCount != 1)
			{
				throw new Exception("NbsInterfaceFilter.insertNBSInterfaceSQL:  none or more than one records inserted at a time, resultCount = " +
						resultCount+", for databaseType:'"+databaseType+"'");
			}
		}	
		catch(SQLException sqlex)
		{
			String errorMsg = "NbsInterfaceFilter.insertNBSInterfaceSQL:  SQLException while inserting message into NBS_Interface:" +", for databaseType:'"+databaseType+"'";
			System.out.println(errorMsg+ sqlex);
			throw new Exception( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			String errorMsg = "NbsInterfaceFilter.insertNBSInterfaceSQL:  Exception while inserting message into NBS_Interface:" +", for databaseType:'"+databaseType+"'";
			System.out.println(errorMsg + ex);
			throw new Exception(errorMsg , ex);
		}			

		finally{
			releaseConnection(dbConnection);
			if(rs!=null)
				rs.close();
			if(pStmt!=null)
				pStmt.close();
		}
	}	
}

