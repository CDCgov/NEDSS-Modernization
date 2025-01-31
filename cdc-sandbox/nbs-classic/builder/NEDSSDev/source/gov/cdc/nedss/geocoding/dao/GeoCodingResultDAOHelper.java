package gov.cdc.nedss.geocoding.dao;

import gov.cdc.nedss.geocoding.dt.GeoCodingResultDT;
import gov.cdc.nedss.geocoding.dt.GeoCodingResultHistDT;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * Geocoding Result DAO helper class.  Contains methods
 * needed by multiple classes in this package.
 * 
 * @author rdodge
 *
 */
public class GeoCodingResultDAOHelper {

	// Members //
	private static Class<?>resultClass = new GeoCodingResultDT().getClass();
	private static Class<?>resultHistClass = new GeoCodingResultHistDT().getClass();


	/** Returns the <code>Class</code> object for the Geocoding Result class. */
	protected static Class<?> getResultClass() {
		return resultClass;
	}

	/** Returns the <code>Class</code> object for the Geocoding Result History class. */
	protected static Class<?>getResultHistClass() {
		return resultHistClass;
	}

	/**
	 * Populates a JDBC prepared statement from a geocoding result or result history object.
	 * 
	 * @param dbConnection
	 * @param statement
	 * @param vo
	 * @param geoCodingResultUID
	 * @param postalLocatorUID
	 * @param versionCtrlNbr
	 * @param isCreate
	 * @return The value of <code>i</code> (parameter index) after parameters are added
	 * @throws SQLException
	 */
	protected static int populateStatementFromGeoCodingResult(Connection dbConnection,
			PreparedStatement statement, GeoCodingResultDT vo, Long geoCodingResultUID,
			Long postalLocatorUID, Integer versionCtrlNbr, boolean isCreate) throws SQLException {

		int i = 1;

		if (isCreate) {
			statement.setLong(i++, geoCodingResultUID.longValue());  // INSERT
		}
		statement.setLong(i++, postalLocatorUID.longValue());

		// A non-null value indicates we are populating a history object //
		if (versionCtrlNbr != null) {
			statement.setInt(i++, versionCtrlNbr.intValue());
		}

		setTimestampParameter(statement, i++, vo.getAddTime());
		setLongParameter(statement, i++, vo.getAddUserId());
		setTimestampParameter(statement, i++, vo.getLastChgTime());
		setLongParameter(statement, i++, vo.getLastChgUserId());

		statement.setString(i++, vo.getFirmName());
		statement.setString(i++, vo.getStreetAddr1());
		statement.setString(i++, vo.getStreetAddr2());
		statement.setString(i++, vo.getCity());
		statement.setString(i++, vo.getState());
		statement.setString(i++, vo.getZipCd());
		statement.setString(i++, vo.getCountry());
		statement.setString(i++, vo.getCntyCd());

		statement.setString(i++, vo.getHouseNumber());
		statement.setString(i++, vo.getPrefixDirection());
		statement.setString(i++, vo.getStreetName());
		statement.setString(i++, vo.getStreetType());
		statement.setString(i++, vo.getPostfixDirection());
		statement.setString(i++, vo.getUnitNumber());
		statement.setString(i++, vo.getUnitType());
		statement.setString(i++, vo.getZip5Cd());
		statement.setString(i++, vo.getZip4Cd());

		setIntegerParameter(statement, i++, vo.getLongitude());
		setIntegerParameter(statement, i++, vo.getLatitude());
		statement.setString(i++, vo.getCensusBlockId());

		statement.setString(i++, vo.getSegmentId());
		statement.setString(i++, vo.getDataType());
		statement.setString(i++, vo.getLocationQualityCd());
		statement.setString(i++, vo.getMatchCd());
		setFloatParameter(statement, i++, vo.getScore());

		statement.setString(i++, vo.getResultType());
		setIntegerParameter(statement, i++, vo.getNumMatches());
		setFloatParameter(statement, i++, vo.getNextScoreDiff());
		setTinyIntParameter(statement, i++, vo.getNextScoreCount());

		setLongParameter(statement, i++, vo.getEntityUid());
		statement.setString(i++, vo.getEntityClassCd());

		statement.setString(i++, NEDSSConstants.RECORD_STATUS_ACTIVE);
		setTimestampParameter(statement, i++, new Timestamp(System.currentTimeMillis()));

		if (!isCreate) {
			statement.setLong(i++, geoCodingResultUID.longValue());  // UPDATE
		}

		// Return current index //
		return i;
	}

	/**
	 * Sets a SQL Long/INTEGER parameter.
	 * 
	 * @param statement
	 * @param position
	 * @param value
	 * @throws SQLException
	 */
	public static void setLongParameter(PreparedStatement statement, int position, Long value) throws SQLException {
		if (value == null) {
			statement.setNull(position, Types.INTEGER);
		}
		else {
			statement.setLong(position, value.longValue());
		}
	}

	/**
	 * Sets a SQL Integer/INTEGER parameter.
	 * 
	 * @param statement
	 * @param position
	 * @param value
	 * @throws SQLException
	 */
	public static void setIntegerParameter(PreparedStatement statement, int position, Integer value) throws SQLException {
		if (value == null) {
			statement.setNull(position, Types.INTEGER);
		}
		else {
			statement.setInt(position, value.intValue());
		}
	}

	/**
	 * Sets a SQL Integer/TINYINT parameter.
	 * 
	 * @param statement
	 * @param position
	 * @param value
	 * @throws SQLException
	 */
	public static void setTinyIntParameter(PreparedStatement statement, int position, Integer value) throws SQLException {
		if (value == null) {
			statement.setNull(position, Types.TINYINT);
		}
		else {
			statement.setInt(position, value.intValue());
		}
	}

	/**
	 * Sets a SQL Float/NUMERIC parameter.
	 * 
	 * @param statement
	 * @param position
	 * @param value
	 * @throws SQLException
	 */
	public static void setFloatParameter(PreparedStatement statement, int position, Float value) throws SQLException {
		if (value == null) {
			statement.setNull(position, Types.NUMERIC);
		}
		else {
			statement.setFloat(position, value.floatValue());
		}
	}

	/**
	 * Sets a SQL Timestamp/TIMESTAMP parameter.
	 * 
	 * @param statement
	 * @param position
	 * @param value
	 * @throws SQLException
	 */
	public static void setTimestampParameter(PreparedStatement statement, int position, Timestamp value) throws SQLException {
		if (value == null) {
			statement.setNull(position, Types.TIMESTAMP);
		}
		else {
			statement.setTimestamp(position, value);
		}
	}

	/**
	 * Sets a VARCHAR parameter; ensures string is not too long.
	 * 
	 * @param statement
	 * @param position
	 * @param value
	 * @param maxLength
	 * @throws SQLException
	 */
	public static void setStringParameter(PreparedStatement statement, int position, String value, int maxLength) throws SQLException {

		// Truncate string if needed //
		String normalizedString = value;
		if (value != null && value.length() > maxLength) {
			normalizedString = value.substring(0, maxLength);
		}
		statement.setString(position, normalizedString);
	}
}
