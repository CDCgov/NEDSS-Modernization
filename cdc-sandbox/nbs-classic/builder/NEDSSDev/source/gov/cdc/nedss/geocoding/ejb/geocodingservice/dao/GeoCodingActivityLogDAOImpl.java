package gov.cdc.nedss.geocoding.ejb.geocodingservice.dao;

import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.geocoding.dao.GeoCodingResultDAOHelper;
import gov.cdc.nedss.geocoding.util.GeoCodingActivityLogDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

/**
 * Geocoding Activity Log DAO implementation.
 * 
 * @author rdodge
 *
 */
public class GeoCodingActivityLogDAOImpl extends BMPBase
{
	static final LogUtils logger = new LogUtils(GeoCodingActivityLogDAOImpl.class.getName());

	private static Integer zeroInteger = new Integer(0);

	private static final String INSERT_ACTIVITYLOG_DT = "INSERT INTO Geocoding_activity_log (" +
	                                             "geocoding_activity_log_uid, batch_run_mode, " +
	                                             "batch_start_time, batch_end_time, " +
	                                             "completed_ind, completion_reason, " +
	                                             "total_nbr, single_match_nbr, multi_match_nbr, " +
	                                             "zero_match_nbr, error_record_nbr, error_nbr) " +
	                                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_ACTIVITYLOG_DT = "UPDATE Geocoding_activity_log SET " +
	                                             "batch_run_mode = ?, batch_start_time = ?, " +
	                                             "batch_end_time = ?, completed_ind = ?, " +
	                                             "completion_reason = ?, total_nbr = ?, " +
	                                             "single_match_nbr = ?, multi_match_nbr = ?, " +
	                                             "zero_match_nbr = ?, error_record_nbr = ?, error_nbr = ? " +
	                                             "WHERE geocoding_activity_log_uid = ?";

	private static final String UPDATE_ACTIVITYLOG_STATUS_DT = "UPDATE Geocoding_activity_log SET " +
                                                 "completed_ind = ?, completion_reason = ?, " +
                                                 "total_nbr = ?, single_match_nbr = ?, " +
                                                 "multi_match_nbr = ?, zero_match_nbr = ?, " +
                                                 "error_record_nbr = ?, error_nbr = ? " +
                                                 "WHERE geocoding_activity_log_uid = ?";

	private static final String UPDATE_FINAL_ACTIVITYLOG_DT = "UPDATE Geocoding_activity_log SET " +
	                                             "batch_end_time = ?, completed_ind = ? " +
	                                             "WHERE geocoding_activity_log_uid = ?";

	private static final String SELECT_MAX_ACTIVITYLOG_DT = "SELECT MAX(batch_end_time) " +
	                                             "FROM Geocoding_activity_log " +
	                                             "WHERE completed_ind = 'Y'";

	/** Default (empty) constructor. */
	public GeoCodingActivityLogDAOImpl() {
	}


	/**
	 * Creates a log entry in the GeoCoding Activity Log table for the
	 * specified activity.
	 * 
	 * @param isFullBatch
	 * @param startTime
	 * @return
	 * @throws NEDSSSystemException
	 * @throws NEDSSDAOSysException
	 */
	public Long createNew(boolean isFullBatch, Timestamp startTime) throws NEDSSSystemException, NEDSSDAOSysException {

		// Create new entry with default values //
		GeoCodingActivityLogDT alDT = new GeoCodingActivityLogDT();

		alDT.setNumTotal(zeroInteger);
		alDT.setNumSingleMatches(zeroInteger);
		alDT.setNumMultiMatches(zeroInteger);
		alDT.setNumZeroMatches(zeroInteger);
		alDT.setNumErrorRecords(zeroInteger);
		alDT.setNumErrors(zeroInteger);

		// Set Run Mode & Batch Start Time //
		if (isFullBatch) {
			alDT.setBatchRunModeFull();
		}
		else {
			alDT.setBatchRunModeIncremental();
		}
		alDT.setBatchStartTime(startTime);

		return create(alDT);
	}

	/**
	 * Creates a log entry in the GeoCoding Activity Log table for the
	 * specified activity.
	 * 
	 * @param alDT
	 * @return
	 * @throws NEDSSSystemException
	 * @throws NEDSSDAOSysException
	 */
	public Long create(GeoCodingActivityLogDT alDT) throws NEDSSSystemException, NEDSSDAOSysException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		Long uid = null;

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining database connection while creating a GeoCodingActivityLog.", nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_ACTIVITYLOG_DT);

			// Generate UID //
			UidGeneratorHelper uidGen = new UidGeneratorHelper();
			uid = uidGen.getNbsIDLong(UidClassCodes.GEOCODING_LOG_CLASS_CODE);

			// Populate DT with UID //
			alDT.setGeoCodingActivityLogUid(uid);

			// Set parameters //
			populateStatementFromActivity(dbConnection, preparedStmt, alDT, true, false);

			// Execute SQL statement //
			preparedStmt.executeUpdate();
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while inserting a GeoCodingActivityLog [uid=" +
					uid + "].", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error while generating UID for GeoCodingActivityLog.", nsex);
			throw nsex;
		}
		catch (Exception ex) {
			logger.fatal("Error while generating UID for GeoCodingActivityLog.", ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		// Flag as committed //
		alDT.setItNew(false);
		alDT.setItDirty(false);

		// Exit //
		return uid;
	}

	/**
	 * Creates a log entry in the GeoCoding Activity Log table for each element
	 * in the specified collection.
	 * 
	 * @param inOutCollection
	 * @throws NEDSSSystemException
	 * @throws NEDSSDAOSysException
	 */
	public void create(Collection<Object> inOutCollection) throws NEDSSSystemException, NEDSSDAOSysException {

		// Iterate over collection //
		Iterator<Object> iterator = inOutCollection.iterator();
		while (iterator.hasNext()) {
			create((GeoCodingActivityLogDT) iterator.next());
		}
	}

	/**
	 * Finalizes the specified log entry in the GeoCoding Activity Log table by applying the
	 * ending timestamp and completion status.
	 * 
	 * @param uid
	 * @param endTime
	 * @param isCompleted
	 * @throws NEDSSSystemException
	 * @throws NEDSSDAOSysException
	 */
	public void updateFinal(Long uid, Timestamp endTime, Boolean isCompleted) throws NEDSSSystemException, NEDSSDAOSysException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining database connection while finalizing a GeoCodingActivityLog.", nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(UPDATE_FINAL_ACTIVITYLOG_DT);

			// Set parameters //
			preparedStmt.setTimestamp(1, endTime);
			preparedStmt.setString(2, Boolean.TRUE.equals(isCompleted) ? "Y" : "N");
			preparedStmt.setLong(3, uid.longValue());

			// Execute SQL statement //
			preparedStmt.executeUpdate();
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while finalizing a GeoCodingActivityLog [uid=" +
					uid + "].", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		catch (Exception ex) {
			logger.fatal("Exception while finalizing a GeoCodingActivityLog [uid=" +
					uid + "].", ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	/**
	 * Updates the specified log entry in the GeoCoding Activity Log table.
	 * 
	 * @param alDT
	 * @throws NEDSSSystemException
	 * @throws NEDSSDAOSysException
	 */
	public void update(GeoCodingActivityLogDT alDT) throws NEDSSSystemException, NEDSSDAOSysException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		Long uid = null;

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining database connection while updating a GeoCodingActivityLog.", nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			uid = alDT.getGeoCodingActivityLogUid();
			preparedStmt = dbConnection.prepareStatement(UPDATE_ACTIVITYLOG_DT);

			// Set parameters //
			populateStatementFromActivity(dbConnection, preparedStmt, alDT, false, false);

			// Execute SQL statement //
			preparedStmt.executeUpdate();
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while updating a GeoCodingActivityLog [uid=" +
					uid + "].", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		catch (Exception ex) {
			logger.fatal("Exception while updating a GeoCodingActivityLog [uid=" +
					uid + "].", ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		// Flag as committed //
		alDT.setItNew(false);
		alDT.setItDirty(false);
	}

	/**
	 * Updates a log entry in the GeoCoding Activity Log table for each element
	 * in the specified collection.
	 * 
	 * @param inOutCollection
	 * @throws NEDSSSystemException
	 * @throws NEDSSDAOSysException
	 */
	public void update(Collection<Object> inCollection) throws NEDSSSystemException, NEDSSDAOSysException {

		// Iterate over collection //
		Iterator<Object> iterator = inCollection.iterator();
		while (iterator.hasNext()) {
			update((GeoCodingActivityLogDT) iterator.next());
		}
	}

	/**
	 * Updates the status of the specified log entry in the GeoCoding Activity Log table.
	 * 
	 * @param alDT
	 * @throws NEDSSSystemException
	 * @throws NEDSSDAOSysException
	 */
	public void updateStatus(GeoCodingActivityLogDT alDT) throws NEDSSSystemException, NEDSSDAOSysException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		Long uid = null;

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining database connection while updating status for a GeoCodingActivityLog.", nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			uid = alDT.getGeoCodingActivityLogUid();
			preparedStmt = dbConnection.prepareStatement(UPDATE_ACTIVITYLOG_STATUS_DT);

			// Set parameters //
			populateStatementFromActivity(dbConnection, preparedStmt, alDT, false, true);

			// Execute SQL statement //
			preparedStmt.executeUpdate();
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while updating a GeoCodingActivityLog [uid=" +
					uid + "].", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		catch (Exception ex) {
			logger.fatal("Exception while updating a GeoCodingActivityLog [uid=" +
					uid + "].", ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		// Flag as committed //
		alDT.setItNew(false);
		alDT.setItDirty(false);
	}

	/**
	 * Retrieves the completion timestamp of the most recent activity
	 * from the GeoCoding Activity Log.
	 *
	 * @return
	 * @throws NEDSSSystemException
	 * @throws NEDSSDAOSysException
	 */
	public Timestamp selectLastActivityTimestamp() throws NEDSSSystemException, NEDSSDAOSysException {

		Timestamp timestamp = null;

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining database connection while selecting last Geocoding activity timestamp.", nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(SELECT_MAX_ACTIVITYLOG_DT);

			// Execute SQL statement //
			resultSet = preparedStmt.executeQuery();

			// Result is MAX(timestamp) //
			if (resultSet.next()) {
				timestamp = resultSet.getTimestamp(1);
			}
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while selecting last Geocoding activity timestamp.", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		catch (Exception ex) {
			logger.fatal("Exception while selecting last Geocoding activity timestamp.", ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		// Exit //
		return timestamp;
	}


	// Helpers //

	/**
	 * Adds activity log members to the parameter list.
	 * 
	 * @param dbConnection
	 * @param statement
	 * @param alDT
	 * @param isCreate
	 * @throws SQLException
	 */
	private void populateStatementFromActivity(Connection dbConnection, PreparedStatement statement,
			GeoCodingActivityLogDT alDT, boolean isCreate, boolean isStatus) throws SQLException {

		int i = 1;

		if (isCreate) {
			statement.setLong(i++, alDT.getGeoCodingActivityLogUid().longValue());  // INSERT
		}
		if (!isStatus) {
			statement.setString(i++, alDT.getBatchRunMode());
			statement.setTimestamp(i++, alDT.getBatchStartTime());
			statement.setTimestamp(i++, alDT.getBatchEndTime());
		}
		statement.setString(i++, Boolean.TRUE.equals(alDT.getCompleted()) ? "Y" : "N");
		GeoCodingResultDAOHelper.setStringParameter(statement, i++, alDT.getCompletionReason(), 1000);

		statement.setInt(i++, alDT.getNumTotal().intValue());
		statement.setInt(i++, alDT.getNumSingleMatches().intValue());
		statement.setInt(i++, alDT.getNumMultiMatches().intValue());
		statement.setInt(i++, alDT.getNumZeroMatches().intValue());
		statement.setInt(i++, alDT.getNumErrorRecords().intValue());
		statement.setInt(i++, alDT.getNumErrors().intValue());

		if (!isCreate) {
			statement.setLong(i++, alDT.getGeoCodingActivityLogUid().longValue());  // UPDATE
		}
	}
}
