package gov.cdc.nedss.geocoding.dao;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.geocoding.dt.GeoCodingResultDT;
import gov.cdc.nedss.geocoding.util.GeoCodingResultSetUtils;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.util.*;

import java.sql.*;
import java.util.Collection;

/**
 * Geocoding Result History DAO.  Supports operations to migrate
 * records to history table(s).
 * 
 * @author rdodge
 * @author bbannerm
 *
 */
public class GeoCodingResultHistDAOImpl extends BMPBase {

	static final LogUtils logger = new LogUtils(GeoCodingResultHistDAOImpl.class.getName());

	public static final String SELECT_GEOCODING_NEXT_SEQ = "SELECT MAX(version_ctrl_nbr) FROM Geocoding_result_hist WHERE geocoding_result_uid = ?";

	public static final String SELECT_GEOCODING_RESULT_HIST = "SELECT geocoding_result_uid \"geocodingResultUid\", postal_locator_uid \"postalLocatorUid\", " +
		"version_ctrl_nbr \"versionCtrlNbr\", add_time \"addTime\", add_user_id \"addUserId\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", " +
		"firm_name \"firmName\", street_addr1 \"streetAddr1\", street_addr2 \"streetAddr2\", city \"city\", state \"state\", zip_cd \"zipCd\", country \"country\", " +
		"cnty_cd \"cntyCd\", house_number \"houseNumber\", prefix_direction \"prefixDirection\", street_name \"streetName\", street_type \"streetType\", " +
		"postfix_direction \"postfixDirection\", unit_number \"unitNumber\", unit_type \"unitType\", zip5_cd \"zip5Cd\", zip4_cd \"zip4Cd\", " + 
		"longitude \"longitude\", latitude \"latitude\", census_block_id \"censusBlockId\", segment_id \"segmentId\", data_type \"dataType\", " +
		"location_quality_cd \"locationQualityCd\", match_cd \"matchCd\", score \"score\", result_type \"resultType\", num_matches \"numMatches\", " + 
		"next_score_diff \"nextScoreDiff\", next_score_count \"nextScoreCount\", entity_uid \"entityUid\", entity_class_cd \"entityClassCd\", " + 
		"record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\" FROM Geocoding_result_hist WHERE geocoding_result_uid = ?";

	public static final String SELECT_GEOCODING_RESULT_HIST_ROW = SELECT_GEOCODING_RESULT_HIST + " AND version_ctrl_nbr = ?";

	public static final String INSERT_GEOCODING_RESULT_HIST = "INSERT INTO Geocoding_result_hist (geocoding_result_uid, postal_locator_uid, " +
		"version_ctrl_nbr, add_time, add_user_id, last_chg_time, last_chg_user_id, firm_name, street_addr1, street_addr2, city, state, zip_cd, country, cnty_cd, " + 
		"house_number, prefix_direction, street_name, street_type, postfix_direction, unit_number, unit_type, zip5_cd, zip4_cd, longitude, " +
		"latitude, census_block_id, segment_id, data_type, location_quality_cd, match_cd, score, result_type, num_matches, next_score_diff, " +
		"next_score_count, entity_uid, entity_class_cd, record_status_cd, record_status_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


	/** Default (empty) constructor. */
	public GeoCodingResultHistDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException {
	}

	/**
	 * Loads the collection of Geocoding Result History objects for the
	 * indicated entity UID from the database.
	 * 
	 * @param entityUID
	 * @param histSeq
	 * @return
	 * @throws NEDSSSystemException
	 */
	public Collection<?>  load(Long entityUID) throws NEDSSSystemException {

		return (Collection<?>) selectGeoCodingResultHist(entityUID, null, SELECT_GEOCODING_RESULT_HIST_ROW);
	}

	/**
	 * Loads the Geocoding Result History object for the indicated
	 * entity UID / sequence number combination from the database.
	 * 
	 * @param entityUID
	 * @param histSeq
	 * @return
	 * @throws NEDSSSystemException
	 */
	public Object loadObject(Long entityUID, Integer histSeq) throws NEDSSSystemException {

		return selectGeoCodingResultHist(entityUID, histSeq, SELECT_GEOCODING_RESULT_HIST);
	}

	/**
	 * Converts a Geocoding Result object to a Geocoding Result History object 
	 * by generating a sequence number for it and storing it in the history table.
	 * 
	 * @param obj
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
	{
		GeoCodingResultDT geoCodingResultVO = (GeoCodingResultDT) obj;

		if (geoCodingResultVO != null) {
			insertGeoCodingResultHist(geoCodingResultVO);
		}
		else {
			logger.info("No GeoCodingResultDT object for history is stored.");
		}
	}


	// Internal Methods //

	/**
	 * Implements <code>store()</code>.
	 * 
	 * @param dt
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	private Integer insertGeoCodingResultHist(GeoCodingResultDT vo) throws NEDSSDAOSysException, NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		Integer histSeq = null;

		logger.debug("Inserting GRH " + vo.toString());

		// Ensure keys are available //
		Long geoCodingResultUID = vo.getGeocodingResultUid();
		if (geoCodingResultUID == null) {
	        throw new NEDSSDAOSysException("Cannot insert GeocodingResultHist: UID not assigned.");
		}
		Long postalLocatorUID = vo.getPostalLocatorUid();
		if (postalLocatorUID == null) {
	        throw new NEDSSDAOSysException("Cannot insert GeocodingResultHist: PL UID not assigned.");
		}

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining database connection while inserting a GeocodingResultHist.", nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_GEOCODING_RESULT_HIST);
			histSeq = returnNextSeqNbr(geoCodingResultUID);
			logger.debug("GRH (grUID, versionCtrlNbr) = (" + geoCodingResultUID + ", " + histSeq + ")");

			// Set parameters //
			GeoCodingResultDAOHelper.populateStatementFromGeoCodingResult(dbConnection,
					preparedStmt, vo, geoCodingResultUID, postalLocatorUID, histSeq, true);

			// Execute SQL statement //
			preparedStmt.executeUpdate();
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while inserting a GeocodingResultHist [uid=" +
					geoCodingResultUID + "][versionCtrlNbr=" + histSeq + "].", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error while generating UID.", nsex);
			throw nsex;
		}
		catch (Exception ex) {
			logger.fatal("Error while generating UID.", ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		// Flag as committed (note: is persisted in the history table, not the active table) //
		vo.setItNew(false);
		vo.setItDirty(false);

		// Exit //
		return histSeq;
	}

	/**
	 * Implements <code>load()</code> and <code>loadObject()</code>.
	 * 
	 * Returns an instance of <code>GeoCodingResultHistDT</code> if
	 * <code>histSeq</code> is null; otherwise, returns a <code>Collection</code>.
	 * 
	 * @param uid
	 * @param histSeq
	 * @param selectString The select string must be compatible with the # of parameters
	 *                     suggested by histSeq (i.e., null => 1 param, non-null => 2 params).
	 * @return
	 * @throws NEDSSSystemException
	 */
	private Object selectGeoCodingResultHist(Long uid, Integer histSeq, String selectString) throws NEDSSSystemException {

		Object result = null;
		String errorString = null;

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			errorString = " [uid=" + uid + (histSeq != null ? "][versionCtrlNbr=" + histSeq : "");
			logger.fatal("Error obtaining dbConnection while loading a GeocodingResultHist" +
					errorString, nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(selectString);
			preparedStmt.setLong(1, uid.longValue());

			// A non-null value indicates a 2nd parameter is expected //
			if (histSeq != null) {
				preparedStmt.setLong(2, histSeq.intValue());
			}

			// Execute SQL statement //
			resultSet = preparedStmt.executeQuery();

			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			GeoCodingResultSetUtils populateBean = new GeoCodingResultSetUtils();

			// Populate result object //
			if (histSeq != null) {

				// Sequence # non-null: Object <= One result row indexed by (uid, sequence #) //
				result = populateBean.mapRsToBean(resultSet, resultSetMetaData,
						GeoCodingResultDAOHelper.getResultHistClass());
			}
			else {
				// Sequence # null: Collection<Object>  <= Zero or more rows indexed by (uid) //
				result = populateBean.mapRsToBeanList(resultSet, resultSetMetaData,
						GeoCodingResultDAOHelper.getResultHistClass(), null);
			}
		}
		catch (ResultSetUtilsException resue) {
			errorString = " [uid=" + uid + (histSeq != null ? "][versionCtrlNbr=" + histSeq : "");
			logger.fatal("Error in result set handling while loading a GeocodingResultHist" +
					errorString, resue);
		}
		catch (SQLException sex) {
			errorString = " [uid=" + uid + (histSeq != null ? "][versionCtrlNbr=" + histSeq : "");
			logger.fatal("SQLException while loading a GeocodingResultHist" + errorString, sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		catch (Exception ex) {
			errorString = " [uid=" + uid + (histSeq != null ? "][versionCtrlNbr=" + histSeq : "");
			logger.fatal("Exception while loading a GeocodingResultHist" + errorString, ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		// Exit //
		return result;
	}

	/**
	 * Returns next sequence number (version_ctrl_nbr).
	 * 
	 * @param uid
	 * @return
	 * @throws NEDSSSystemException
	 */
	private Integer returnNextSeqNbr(Long uid) throws NEDSSSystemException {

		int result = 1;  // default (indicating 1st row for this uid is being added)

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining dbConnection while extracting GeocodingResultHist seq. # [uid=" +
					uid + "].", nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(SELECT_GEOCODING_NEXT_SEQ);
			preparedStmt.setLong(1, uid.longValue());

			// Execute SQL statement //
			resultSet = preparedStmt.executeQuery();

			// Result is MAX(sequence number) + 1 //
			if (resultSet.next()) {
				result = resultSet.getInt(1) + 1;
			}
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while extracting GeocodingResultHist seq. # [uid=" +
					uid + "].", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		catch (Exception ex) {
			logger.fatal("Exception while extracting GeocodingResultHist seq. # [uid=" +
					uid + "].", ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		// Exit //
		return new Integer(result);
	}
}
