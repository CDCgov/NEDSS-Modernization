package gov.cdc.nedss.geocoding.dao;

import gov.cdc.nedss.geocoding.dt.GeoCodingResultDT;
import gov.cdc.nedss.geocoding.util.GeoCodingHelper;
import gov.cdc.nedss.geocoding.util.GeoCodingResultSetUtils;
import gov.cdc.nedss.locator.dao.PostalLocatorDAOImpl;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.systemservice.uidgenerator.*;

import java.sql.*;

/**
 * Geocoding Result DAO.  Supports CRUD operations.
 * 
 * @author rdodge
 * @author bbannerm
 *
 */
public class GeoCodingResultDAOImpl extends BMPBase {

	static final LogUtils logger = new LogUtils(GeoCodingResultDAOImpl.class.getName());

	public static final String SELECT_GEOCODING_RESULT = "SELECT geocoding_result_uid \"geocodingResultUid\", postal_locator_uid \"postalLocatorUid\", " +
		"add_time \"addTime\", add_user_id \"addUserId\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", firm_name \"firmName\", " +
		"street_addr1 \"streetAddr1\", street_addr2 \"streetAddr2\", city \"city\", state \"state\", zip_cd \"zipCd\", country \"country\", cnty_cd \"cntyCd\", " +
		"house_number \"houseNumber\", prefix_direction \"prefixDirection\", street_name \"streetName\", street_type \"streetType\", " +
		"postfix_direction \"postfixDirection\", unit_number \"unitNumber\", unit_type \"unitType\", zip5_cd \"zip5Cd\", zip4_cd \"zip4Cd\", " +
		"longitude \"longitude\", latitude \"latitude\", census_block_id \"censusBlockId\", segment_id \"segmentId\", data_type \"dataType\", " +
		"location_quality_cd \"locationQualityCd\", match_cd \"matchCd\", score \"score\", result_type \"resultType\", num_matches \"numMatches\", " +
		"next_score_diff \"nextScoreDiff\", next_score_count \"nextScoreCount\", entity_uid \"entityUid\", entity_class_cd \"entityClassCd\", " +
		"record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\" FROM Geocoding_result WHERE geocoding_result_uid = ?";

	public static final String SELECT_GEOCODING_RESULT_USING_POSTAL_LOCATOR = "SELECT geocoding_result_uid \"geocodingResultUid\", postal_locator_uid \"postalLocatorUid\", " +
		"add_time \"addTime\", add_user_id \"addUserId\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", firm_name \"firmName\", " +
		"street_addr1 \"streetAddr1\", street_addr2 \"streetAddr2\", city \"city\", state \"state\", zip_cd \"zipCd\", country \"country\", cnty_cd \"cntyCd\", " +
		"house_number \"houseNumber\", prefix_direction \"prefixDirection\", street_name \"streetName\", street_type \"streetType\", " +
		"postfix_direction \"postfixDirection\", unit_number \"unitNumber\", unit_type \"unitType\", zip5_cd \"zip5Cd\", zip4_cd \"zip4Cd\", " +
		"longitude \"longitude\", latitude \"latitude\", census_block_id \"censusBlockId\", segment_id \"segmentId\", data_type \"dataType\", " +
		"location_quality_cd \"locationQualityCd\", match_cd \"matchCd\", score \"score\", result_type \"resultType\", num_matches \"numMatches\", " +
		"next_score_diff \"nextScoreDiff\", next_score_count \"nextScoreCount\", entity_uid \"entityUid\", entity_class_cd \"entityClassCd\", " +
		"record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\" FROM Geocoding_result WHERE postal_locator_uid = ?";

	public static final String INSERT_GEOCODING_RESULT = "INSERT INTO Geocoding_result (geocoding_result_uid, postal_locator_uid, " +
		"add_time, add_user_id, last_chg_time, last_chg_user_id, firm_name, street_addr1, street_addr2, city, state, zip_cd, country, cnty_cd, " +
		"house_number, prefix_direction, street_name, street_type, postfix_direction, unit_number, unit_type, zip5_cd, zip4_cd, longitude, " +
		"latitude, census_block_id, segment_id, data_type, location_quality_cd, match_cd, score, result_type, num_matches, next_score_diff, " +
		"next_score_count, entity_uid, entity_class_cd, record_status_cd, record_status_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String UPDATE_GEOCODING_RESULT = "UPDATE Geocoding_result SET postal_locator_uid = ?, " +
		"add_time = ?, add_user_id = ?, last_chg_time = ?, last_chg_user_id = ?, firm_name = ?, street_addr1 = ?, street_addr2 = ?, city = ?, " + 
		"state = ?, zip_cd = ?, country = ?, cnty_cd = ?, house_number = ?, prefix_direction = ?, street_name = ?, street_type = ?, " +
		"postfix_direction = ?, unit_number = ?, unit_type = ?, zip5_cd = ?, zip4_cd = ?, longitude = ?, latitude = ?, census_block_id = ?, " +
		"segment_id = ?, data_type = ?, location_quality_cd = ?, match_cd = ?, score = ?, result_type = ?, num_matches = ?, next_score_diff = ?, " +
		"next_score_count = ?, entity_uid = ?, entity_class_cd = ?, record_status_cd = ?, record_status_time = ? WHERE geocoding_result_uid = ?";

	public static final String UPDATE_GEOCODING_RESULT_FOR_INACTIVE_POSTAL_LOCATORS = "UPDATE geocoding_result SET geocoding_result.record_status_cd = 'INACTIVE' " + "" +
		"WHERE EXISTS (SELECT * FROM entity_locator_participation WHERE entity_locator_participation.locator_uid = geocoding_result.postal_locator_uid AND " +
		"entity_locator_participation.record_status_cd = 'INACTIVE')";
	
	/**
	 * Default (empty) constructor.
	 * 
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public GeoCodingResultDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException {
	}

	/**
	 * Creates a new Geocoding Result object in the database.
	 * 
	 * Note: Users and timestamps must be set prior to calling this method.
	 *  
	 * @param obj
	 * @return Object UID
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public long create(Object obj) throws NEDSSDAOSysException, NEDSSSystemException {

		Long result = null;
		GeoCodingResultDT vo = (GeoCodingResultDT) obj;

		if (vo != null) {
			result = insertGeoCodingResult(vo);
		}
		else {
			logger.info("No GeoCodingResultDT object supplied to create().");
		}
		return result != null ? result.longValue() : -1L;
	}

	/**
	 * Persists a modified Geocoding Result object to the database.
	 * 
	 * Note: Setting of users and timestamps should be delegated to this method.
	 * 
	 * @param obj
	 * @param userId
	 * @return Object UID
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Long store(Object obj, Long userId) throws NEDSSDAOSysException, NEDSSSystemException {

		Long uid = null;
		GeoCodingResultDT newGeoCodingResultVO = (GeoCodingResultDT) obj;
		GeoCodingHelper helper = GeoCodingHelper.getInstance();

		if (newGeoCodingResultVO != null) {

			long postalLocatorUID = newGeoCodingResultVO.getPostalLocatorUid().longValue();
			GeoCodingResultDT oldGeoCodingResultVO = (GeoCodingResultDT) findGeoCodingResultWithPostalLocator(postalLocatorUID);

	 		// Handle both insert and update cases //
			if (oldGeoCodingResultVO == null || oldGeoCodingResultVO.getGeocodingResultUid() == null) {

				// Insert //
				prepareVOForInsert(newGeoCodingResultVO, userId);
				uid = insertGeoCodingResult(newGeoCodingResultVO);
			}
			else {
				// Update //
				uid = oldGeoCodingResultVO.getGeocodingResultUid();
				prepareVOForUpdate(newGeoCodingResultVO, oldGeoCodingResultVO, userId);
				updateGeoCodingResult(newGeoCodingResultVO, oldGeoCodingResultVO);
			}

			// Flag postal locator row as geocoded //
			PostalLocatorDAOImpl dao = GeoCodingHelper.getInstance().getPostalLocatorDAO();
			try {
				dao.setGeocodeMatchIndicator(postalLocatorUID);
			}
			catch (NEDSSSystemException e) {
				helper.addException(GeoCodingHelper.EXCEPTION_UPDATE_PL, helper.getBufferIndex(), -1);
				throw e;
			}
		}
		else {
			logger.info("No GeoCodingResultDT object supplied to store().");
		}

		return uid;
	}

	/**
	 * Loads a Geocoding Result object from the database.
	 * 
	 * @param geoCodingResultUID
	 * @return Geocoding Result object (of type <code>GeoCodingResultDT</code>)
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Object loadObject(long geoCodingResultUID) throws NEDSSDAOSysException, NEDSSSystemException {

		return selectGeoCodingResult(geoCodingResultUID, SELECT_GEOCODING_RESULT);
	}

	/**
	 * Loads the Geocoding Result object from the database with the
	 * indicated postal locator UID.  (Is expecting either zero or one
	 * result row(s) to be returned.)
	 * 
	 * @param postalLocatorUID
	 * @return Geocoding Result object (of type <code>GeoCodingResultDT</code>)
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Object findGeoCodingResultWithPostalLocator(long postalLocatorUID) throws NEDSSDAOSysException, NEDSSSystemException {

		return selectGeoCodingResult(postalLocatorUID, SELECT_GEOCODING_RESULT_USING_POSTAL_LOCATOR);
	}

	/**
	 * Prepares the Geocoding Result object for an insert, adding any
	 * supplementary information required.
	 * 
	 * @param vo
	 * @param userId
	 */
	public void prepareVOForInsert(GeoCodingResultDT vo, Long userId) {

		// Set additional information //
		Timestamp now = new Timestamp(System.currentTimeMillis());
		vo.setAddTime(now);
		vo.setLastChgTime(now);

		vo.setAddUserId(userId);
		vo.setLastChgUserId(userId);
	}

	/**
	 * Prepares the Geocoding Result object for an update, adding any
	 * supplementary information required.
	 * 
	 * @param newVO
	 * @param oldVO
	 * @param userId
	 */
	public void prepareVOForUpdate(GeoCodingResultDT newVO, GeoCodingResultDT oldVO, Long userId) {

		// Set identity information //
		newVO.setGeocodingResultUid(oldVO.getGeocodingResultUid());  // NEW.UID <= OLD.UID
		newVO.setPostalLocatorUid(oldVO.getPostalLocatorUid());  // just to be sure; should already be equal

		// Set additional information //
		Timestamp now = new Timestamp(System.currentTimeMillis());
		newVO.setAddTime(oldVO.getAddTime());
		newVO.setLastChgTime(now);

		newVO.setAddUserId(oldVO.getAddUserId());
		newVO.setLastChgUserId(userId);
	}


	// Internal Methods //

	/**
	 * Implements <code>create()</code>.
	 * 
	 * @param vo
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	private Long insertGeoCodingResult(GeoCodingResultDT vo) throws NEDSSDAOSysException, NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		Long geoCodingResultUID = null;

		logger.debug("Inserting GR " + vo.toString());

		// Ensure key is available //
		Long postalLocatorUID = vo.getPostalLocatorUid();
		if (postalLocatorUID == null) {
	        throw new NEDSSDAOSysException("Cannot update GeocodingResult: PL UID not assigned.");
		}

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining database connection while inserting a GeocodingResult.", nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_GEOCODING_RESULT);

			UidGeneratorHelper uidGen = new UidGeneratorHelper();
			geoCodingResultUID = uidGen.getNbsIDLong(UidClassCodes.GEOCODING_CLASS_CODE);

			// Set parameters //
			GeoCodingResultDAOHelper.populateStatementFromGeoCodingResult(dbConnection,
					preparedStmt, vo, geoCodingResultUID, postalLocatorUID, null, true);

			// Execute SQL statement //
			preparedStmt.executeUpdate();

			// Populate object with UID //
			vo.setGeocodingResultUid(geoCodingResultUID);
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while inserting a GeocodingResult [uid=" +
					geoCodingResultUID + "].", sex);
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

		// Flag as committed //
		vo.setItNew(false);
		vo.setItDirty(false);

		return geoCodingResultUID;
	}

	/**
	 * Implements <code>update()</code>.
	 * 
	 * Note: <code>newVO</code> must already have its identity information set.
	 * 
	 * @param newVO
	 * @param oldVO
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	private void updateGeoCodingResult(GeoCodingResultDT newVO, GeoCodingResultDT oldVO)
			throws NEDSSDAOSysException, NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		GeoCodingHelper helper = GeoCodingHelper.getInstance();

		logger.debug("Updating GR to " + newVO.toString());

		// Ensure keys are available //
		Long geoCodingResultUID = newVO.getGeocodingResultUid();
		if (geoCodingResultUID == null) {
	        throw new NEDSSDAOSysException("Cannot update GeocodingResult: UID not assigned.");
		}
		Long postalLocatorUID = newVO.getPostalLocatorUid();
		if (postalLocatorUID == null) {
	        throw new NEDSSDAOSysException("Cannot update GeocodingResult: PL UID not assigned.");
		}

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining dbConnection while updating a GeocodingResult.", nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(UPDATE_GEOCODING_RESULT);

			// Set parameters //
			GeoCodingResultDAOHelper.populateStatementFromGeoCodingResult(dbConnection,
					preparedStmt, newVO, geoCodingResultUID, postalLocatorUID, null, false);

			// Execute SQL statement //
			preparedStmt.executeUpdate();

			// Move old record into history table //
			GeoCodingResultHistDAOImpl dao = helper.getGeoCodingResultHistDAO();
			try {
				dao.store(oldVO);
			}
			catch (NEDSSSystemException e) {
				helper.addException(GeoCodingHelper.EXCEPTION_PERSIST_GRH, helper.getBufferIndex(), -1);
				throw e;
			}
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while updating a GeocodingResult [uid=" +
					geoCodingResultUID + "].", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		// Flag as committed //
		newVO.setItNew(false);
		newVO.setItDirty(false);
	}

	/**
	 * Implements <code>select()</code>.
	 * 
	 * @param uid
	 * @param selectString
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	private Object selectGeoCodingResult(long uid, String selectString) throws NEDSSDAOSysException, NEDSSSystemException {
		
		Object result = null;

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining dbConnection while loading a GeocodingResult [uid=" +
					uid + "].", nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(selectString);
			preparedStmt.setLong(1, uid);

			// Execute SQL statement //
			resultSet = preparedStmt.executeQuery();

			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			GeoCodingResultSetUtils populateBean = new GeoCodingResultSetUtils();

			// Populate result object //
			result = populateBean.mapRsToBean(resultSet, resultSetMetaData,
					GeoCodingResultDAOHelper.getResultClass());
		}
		catch (ResultSetUtilsException resue) {
			logger.fatal("Error in result set handling while loading a GeocodingResult [uid=" +
					uid + "].", resue);
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while loading a GeocodingResult [uid=" + uid + "].", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		catch (Exception ex) {
			logger.fatal("Exception while loading a GeocodingResult [uid=" + uid + "].", ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		return result;
	}
	
	/**
	 * Updates <code>geocoding_result.record_status_cd</code> column for each result where
	 * the associated <code>postal_locator</code> (via <code>entity_locator_participation</code>) 
	 * has been set to INACTIVE (thus deleted from the entity it was attached to).
	 * 
	 * @param 
	 * @return void
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateGeoCodingResultForInactivatedPostalLocators() throws NEDSSDAOSysException, NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining dbConnection while updating geocoding_results for inactivated postal locators", nsex);
			throw nsex;
		}

		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(UPDATE_GEOCODING_RESULT_FOR_INACTIVE_POSTAL_LOCATORS);

			// Execute SQL statement //
			preparedStmt.executeUpdate();
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while updating geocoding_results for inactivated postal locators", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		catch (Exception ex) {
			logger.fatal("Exception while updating geocoding_results for inactivated postal locators", ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}	
}
