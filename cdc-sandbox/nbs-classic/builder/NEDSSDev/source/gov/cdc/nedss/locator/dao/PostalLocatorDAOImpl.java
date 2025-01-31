//Source file: C:\\Project Stuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\cdm\\dao\\PostalLocatorDAOImpl.java
/**
 * Name:		PostalLocatorDAOImpl.java
 * Description:	This is the implementation of NEDSSDAOInterface for the
 *               PostalLocator value object.
 *               This class encapsulates all the JDBC calls required to
 *               create,load and store values into the postal_locator table
 *
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	Venu Pannirselvam
 * @modified     Add modification Information here. Each person should add ones own.
 * @version	1.0 10/04/2001
 */
package gov.cdc.nedss.locator.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.geocoding.dt.GeoCodingPostalLocatorDT;
import gov.cdc.nedss.geocoding.util.GeoCodingHelper;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * ... end imports
 */
public class PostalLocatorDAOImpl
    extends BMPBase {
  //For logging
  static final LogUtils logger = new LogUtils(PostalLocatorDAOImpl.class.
                                              getName());
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

  public static final String SELECT_POSTAL_LOCATOR = "SELECT postal_locator_uid \"postalLocatorUid\", add_reason_cd \"addReasonCd\", " +
      "add_time \"addTime\", add_user_id \"addUserId\", census_block_cd \"censusBlockCd\", census_minor_civil_division_cd \"censusMinorCivilDivisionCd\", " +
      "census_track_cd \"censusTrackCd\", city_cd \"cityCd\", city_desc_txt \"cityDescTxt\", cntry_cd \"cntryCd\", cntry_desc_txt \"cntryDescTxt\", " +
      "cnty_cd \"cntyCd\", cnty_desc_txt \"cntyDescTxt\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", " +
      "MSA_congress_district_cd \"MSACongressDistrictCd\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", " +
      "region_district_cd \"regionDistrictCd\", state_cd \"stateCd\", street_addr1 \"streetAddr1\", street_addr2 \"streetAddr2\", " +
      "user_affiliation_txt \"userAffiliationTxt\", zip_cd \"zipCd\", geocode_match_ind \"geocodeMatchInd\", within_city_limits_ind \"withinCityLimitsInd\", census_tract \"censusTract\" FROM Postal_locator WHERE postal_locator_uid = ?";
  public static final String INSERT_POSTAL_LOCATOR = "INSERT INTO Postal_locator (postal_locator_uid, add_reason_cd, add_time, add_user_id, " +
      "census_block_cd, census_minor_civil_division_cd, census_track_cd, city_cd, city_desc_txt, cntry_cd, cntry_desc_txt, cnty_cd, cnty_desc_txt, " +
      "last_chg_reason_cd, last_chg_time, last_chg_user_id, MSA_congress_district_cd, record_status_cd, record_status_time, region_district_cd, state_cd, " +
      "street_addr1, street_addr2, user_affiliation_txt, zip_cd, geocode_match_ind, within_city_limits_ind, census_tract) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String UPDATE_POSTAL_LOCATOR = "UPDATE Postal_locator SET add_reason_cd = ?, add_time = ?, add_user_id = ?, census_block_cd = ?, " +
      "census_minor_civil_division_cd = ?, census_track_cd = ?, city_cd = ?, city_desc_txt = ?, cntry_cd = ?, cntry_desc_txt = ?, cnty_cd = ?, cnty_desc_txt = ?, " +
      "last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, MSA_congress_district_cd = ?, record_status_cd = ?, record_status_time = ?, " +
      "region_district_cd = ?, state_cd = ?, street_addr1 = ?, street_addr2 = ?, user_affiliation_txt = ?, zip_cd = ?, geocode_match_ind = ?, within_city_limits_ind = ?, census_tract = ? WHERE postal_locator_uid = ?";

  public static final String UPDATE_POSTAL_LOCATOR_SET_GEOCODING_IND = "UPDATE Postal_locator SET geocode_match_ind = 'Y' WHERE postal_locator_uid = ?";
  public static final String UPDATE_POSTAL_LOCATOR_SET_GEOCODING_IND_ERROR = "UPDATE Postal_locator SET geocode_match_ind = 'E' WHERE postal_locator_uid = ?";
  public static final String UPDATE_POSTAL_LOCATOR_CLEAR_GEOCODING_INDS_BODY = "UPDATE Postal_locator SET geocode_match_ind = NULL WHERE postal_locator_uid IN (SELECT Postal_locator.postal_locator_uid";
  public static final String SELECT_POSTAL_LOCATORS_FOR_GEOCODING_BODY = " Postal_locator.postal_locator_uid \"postalLocatorUid\", Postal_locator.add_reason_cd \"addReasonCd\", " +
	  "Postal_locator.add_time \"addTime\", Postal_locator.add_user_id \"addUserId\", Postal_locator.census_block_cd \"censusBlockCd\", Postal_locator.census_minor_civil_division_cd \"censusMinorCivilDivisionCd\", " +
  	  "Postal_locator.census_track_cd \"censusTrackCd\", Postal_locator.city_cd \"cityCd\", Postal_locator.city_desc_txt \"cityDescTxt\", Postal_locator.cntry_cd \"cntryCd\", Postal_locator.cntry_desc_txt \"cntryDescTxt\", " +
  	  "Postal_locator.cnty_cd \"cntyCd\", Postal_locator.cnty_desc_txt \"cntyDescTxt\", Postal_locator.last_chg_reason_cd \"lastChgReasonCd\", Postal_locator.last_chg_time \"lastChgTime\", Postal_locator.last_chg_user_id \"lastChgUserId\", " +
  	  "Postal_locator.MSA_congress_district_cd \"MSACongressDistrictCd\", Postal_locator.record_status_cd \"recordStatusCd\", Postal_locator.record_status_time \"recordStatusTime\", " +
  	  "Postal_locator.region_district_cd \"regionDistrictCd\", Postal_locator.state_cd \"stateCd\", Postal_locator.street_addr1 \"streetAddr1\", Postal_locator.street_addr2 \"streetAddr2\", " +
  	  "Postal_locator.user_affiliation_txt \"userAffiliationTxt\", Postal_locator.zip_cd \"zipCd\", Postal_locator.geocode_match_ind \"geocodeMatchInd\", Postal_locator.within_city_limits_ind \"withinCityLimitsInd\", Entity_locator_participation.entity_uid \"entityUid\", Entity.class_cd \"entityClassCd\" ";
  public static final String JOIN_POSTAL_LOCATORS_FOR_GEOCODING_SQLSERVER = " FROM Entity_locator_participation INNER JOIN Entity ON Entity_locator_participation.entity_uid = Entity.entity_uid INNER JOIN " +
      "Postal_locator ON Entity_locator_participation.locator_uid = Postal_locator.postal_locator_uid WHERE Entity_locator_participation.record_status_cd = 'ACTIVE'";
  public static final String GEOCODING_IND_NULL_CLAUSE = " AND Postal_locator.geocode_match_ind IS NULL";
  public static final String GEOCODING_IND_NOT_ERROR_CLAUSE = " AND (Postal_locator.geocode_match_ind IS NULL OR Postal_locator.geocode_match_ind <> 'E')";
  public static final String GEOCODING_IND_ERROR_CLAUSE = " AND Postal_locator.geocode_match_ind = 'E'";
  
  
  /**
   * ///////////////////////////// Constructor(s) /////////////////////////////////
   * PostalLocatorDAOImpl is the default constructor requires no parameters
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
   * @roseuid 3C0D5B4B0084
   */
  public PostalLocatorDAOImpl() throws NEDSSDAOSysException,
      NEDSSSystemException {

  }

  /**
       * /////////////////////////////// Methods(s) ///////////////////////////////////
   * @methodname create
   * Creates an Object of type PostalLocatorDT and inserts a row into the
   * postal_locator table
   * Calls insertPostalLocator which does the JDBC call
   * @param obj Object to be created
   * @return the unique id of the created object
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
   * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
       * @exception NEDSSDAOSysException This is an exception thrown in the event of
   * ir-recoverable system errors.
       * @exception NEDSSSystemException This is an exception thrown in the event of
   * application errors.
   * @exception NEDSSSystemException This is an exception thrown by the
   * NEDSSUIDGenerator
   * @roseuid 3C0D5B4B01E2
   */
  public long create(Object obj) throws NEDSSDAOSysException,
      NEDSSSystemException {
	  try{
		  return (insertPostalLocator(obj));
	  }catch(NEDSSDAOSysException ex){
		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		  throw new NEDSSDAOSysException(ex.toString());
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  /**
   * @methodname store
   * Updates a row in the postal_locator table
   * Calls updatePostalLocator method which does the JDBC call
   * @param obj Object with modified information of type PostalLocatorDT
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
   * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
       * @exception NEDSSDAOSysException This is an exception thrown in the event of
   * ir-recoverable system errors.
       * @exception NEDSSSystemException This is an exception thrown in the event of
   * application errors.
   * @exception NEDSSSystemException This is an exception thrown by the BMPBase
   * super class
   * @roseuid 3C0D5B4B03CE
   */
  public void store(Object obj) throws NEDSSDAOSysException,
      NEDSSSystemException {
	  try{
		  updatePostalLocator(obj);
	  }catch(NEDSSDAOSysException ex){
		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		  throw new NEDSSDAOSysException(ex.toString());
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  /**
   * @methodname load
   * Selects a row in the postal_locator table
   * @param postalLocatorUID unique identifier of the row to be selected
   * @return Object of type PostalLocatorDT
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
   * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
       * @exception NEDSSDAOSysException This is an exception thrown in the event of
   * ir-recoverable system errors.
       * @exception NEDSSSystemException This is an exception thrown in the event of
   * application errors.
   * @exception NEDSSSystemException This is an exception thrown by the BMPBase
   * super class
   * @roseuid 3C0D5B4C01F8
   */
  public Object loadObject(long postalLocatorUID) throws NEDSSDAOSysException,
      NEDSSSystemException {

    PostalLocatorDT postalLocatorVO = new PostalLocatorDT();
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    try {
      dbConnection = getConnection();

      preparedStmt = dbConnection.prepareStatement(SELECT_POSTAL_LOCATOR);
      preparedStmt.setLong(1, postalLocatorUID);
      resultSet = preparedStmt.executeQuery();
      ResultSetMetaData rsMetaData = resultSet.getMetaData();
      ResultSetUtils populateBean = new ResultSetUtils();
      Class PostalLocatorClass = postalLocatorVO.getClass();
      return (populateBean.mapRsToBean(resultSet, rsMetaData,
                                       PostalLocatorClass));

    }
    catch (ResultSetUtilsException resue) {
      logger.fatal("postalLocatorUID: "+postalLocatorUID+" ResultSetUtilsException ="+resue.getMessage(), resue);
      return null;

    }
    catch (SQLException sex) {
      logger.fatal("postalLocatorUID: "+postalLocatorUID+" SQLException while loading " +
                   "a PostalLocator, postalLocatorUID = " + postalLocatorUID,
                   sex);

      throw new NEDSSDAOSysException(sex.toString());

    }

    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
  }

	/**
	 * Will be used to return a collection of PostalLocatorDT objects
	 * representing the postal_locator where the geocode_match_ind is null. If
	 * there are a large # of postal locators needing geocoding this method will
	 * not return more than a configurable maximum number (block size).
	 * 
	 * @return Collection<Object>  PostalLocatorDT's
	 * @throws NEDSSSystemException
	 */
	public Collection<Object>  findPostalLocatorsNeedingGeoCoding() throws NEDSSSystemException {

		GeoCodingPostalLocatorDT geocodingPostalLocatorVO = new GeoCodingPostalLocatorDT();
		ArrayList<Object> arrayList = new ArrayList<Object> ();

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();

			int blockSize = GeoCodingHelper.getInstance().getBlockSize();

			// Build select string //
			String selectString = "SELECT" + (" TOP " + blockSize) +
					SELECT_POSTAL_LOCATORS_FOR_GEOCODING_BODY +
					(JOIN_POSTAL_LOCATORS_FOR_GEOCODING_SQLSERVER + GEOCODING_IND_NULL_CLAUSE) +
					" ORDER BY Postal_locator.zip_cd";

			// Prepare & execute SQL statement //
			preparedStmt = dbConnection.prepareStatement(selectString);
			resultSet = preparedStmt.executeQuery();

			ResultSetMetaData rsMetaData = resultSet.getMetaData();
			ResultSetUtils populateBean = new ResultSetUtils();

			arrayList = (ArrayList<Object> ) populateBean.mapRsToBeanList(
			      				    resultSet,
			      				    rsMetaData,
			      				    geocodingPostalLocatorVO.getClass(),
					  				arrayList);

			// Flag elements as not needing persistence //
			for (Iterator<Object> it = arrayList.iterator(); it.hasNext(); ) {
				AbstractVO vo = (AbstractVO) it.next();
				vo.setItNew(false);
				vo.setItDirty(false);
			}
			return arrayList;
		}
		catch (ResultSetUtilsException resue) {
			logger.fatal("ResultSetUtilsException ="+resue.getMessage(), resue);
			return null;
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while loading PostalLocators for GeoCoding." + sex.getMessage(), sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}	  
  
  
  /**
   * @methodname insertPostalLocator
   * Inserts a row into the postal_locator table
   * @param obj Object to be created
   * @return the unique id of the created object
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
   * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
   * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
       * @exception NEDSSDAOSysException This is an exception thrown in the event of
   * ir-recoverable system errors.
       * @exception NEDSSSystemException This is an exception thrown in the event of
   * application errors.
   * @exception NEDSSSystemException This is an exception thrown by the
   * NEDSSUIDGenerator
   * @exception NEDSSSystemException This is an exception thrown by the BMPBase
   * super class
   * @roseuid 3C0D5B4C03CE
   */
  private long insertPostalLocator(Object obj) throws NEDSSDAOSysException,
      NEDSSSystemException {
    PostalLocatorDT postalLocatorVO = new PostalLocatorDT();
    postalLocatorVO = (PostalLocatorDT) obj;
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    int resultCount = 0;
    long postalLocatorUID = -1;

    if (postalLocatorVO != null) {
      try {
        dbConnection = getConnection();
      }
      catch (NEDSSSystemException nsex) {
        logger.fatal("SQLException while obtaining database connection " +
                     "for insertPostalLocator ", nsex);
        throw new NEDSSSystemException(nsex.getMessage());
      }

      try {
        preparedStmt = dbConnection.prepareStatement(INSERT_POSTAL_LOCATOR);
        UidGeneratorHelper uidGen = new UidGeneratorHelper();
        postalLocatorUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).
            longValue();
        int i = 1;
        preparedStmt.setLong(i++, postalLocatorUID);
        preparedStmt.setString(i++, postalLocatorVO.getAddReasonCd());
        if (postalLocatorVO.getAddTime() == null) {
          preparedStmt.setNull(i++, Types.TIMESTAMP);
        }
        else {
          preparedStmt.setTimestamp(i++, postalLocatorVO.getAddTime());
        }
        if (postalLocatorVO.getAddUserId() == null) {
          preparedStmt.setNull(i++, Types.INTEGER);
        }
        else {
          preparedStmt.setLong(i++, postalLocatorVO.getAddUserId().longValue());
        }
        preparedStmt.setString(i++, postalLocatorVO.getCensusBlockCd());
        preparedStmt.setString(i++,
                               postalLocatorVO.getCensusMinorCivilDivisionCd());
        preparedStmt.setString(i++, postalLocatorVO.getCensusTrackCd());
        preparedStmt.setString(i++, postalLocatorVO.getCityCd());
        if (postalLocatorVO.getCityDescTxt() != null &&
            postalLocatorVO.getCityDescTxt().trim().length() > 0) {
          preparedStmt.setString(i++, postalLocatorVO.getCityDescTxt());
        }
        else {
          preparedStmt.setNull(i++, Types.VARCHAR);

        }
        preparedStmt.setString(i++, postalLocatorVO.getCntryCd());
        preparedStmt.setString(i++, postalLocatorVO.getCntryDescTxt());
        preparedStmt.setString(i++, postalLocatorVO.getCntyCd());
        preparedStmt.setString(i++, postalLocatorVO.getCntyDescTxt());
        preparedStmt.setString(i++, postalLocatorVO.getLastChgReasonCd());
        if (postalLocatorVO.getLastChgTime() == null) {
          preparedStmt.setNull(i++, Types.TIMESTAMP);
        }
        else {
          preparedStmt.setTimestamp(i++, postalLocatorVO.getLastChgTime());
        }
        if (postalLocatorVO.getLastChgUserId() == null) {
          preparedStmt.setNull(i++, Types.INTEGER);
        }
        else {
          preparedStmt.setLong(i++,
                               postalLocatorVO.getLastChgUserId().longValue());
        }
        preparedStmt.setString(i++, postalLocatorVO.getMSACongressDistrictCd());
        preparedStmt.setString(i++, postalLocatorVO.getRecordStatusCd());
        if (postalLocatorVO.getRecordStatusTime() == null) {
          preparedStmt.setNull(i++, Types.TIMESTAMP);
        }
        else {
          preparedStmt.setTimestamp(i++, postalLocatorVO.getRecordStatusTime());
        }
        preparedStmt.setString(i++, postalLocatorVO.getRegionDistrictCd());

        if (postalLocatorVO.getStateCd() != null &&
            postalLocatorVO.getStateCd().trim().length() > 0) {
          preparedStmt.setString(i++, postalLocatorVO.getStateCd());
        }
        else {
          preparedStmt.setNull(i++, Types.VARCHAR);

        }
        preparedStmt.setString(i++, postalLocatorVO.getStreetAddr1());
        preparedStmt.setString(i++, postalLocatorVO.getStreetAddr2());
        preparedStmt.setString(i++, postalLocatorVO.getUserAffiliationTxt());
        if (postalLocatorVO.getZipCd() != null &&
            postalLocatorVO.getZipCd().trim().length() > 0) {
          preparedStmt.setString(i++, postalLocatorVO.getZipCd());
        }
        else {
          preparedStmt.setNull(i++, Types.VARCHAR);

        }
        preparedStmt.setNull(i++, Types.VARCHAR); //Set geocode_match_ind to null on insert
        preparedStmt.setString(i++, postalLocatorVO.getWithinCityLimitsInd());
		if (postalLocatorVO.getCensusTract() != null
				&& postalLocatorVO.getCensusTract().trim().length() > 0) {
			preparedStmt.setString(i++,
					postalLocatorVO.getCensusTract());
		} else {
			preparedStmt.setNull(i++, Types.VARCHAR);

		}
        resultCount = preparedStmt.executeUpdate();
        postalLocatorVO.setPostalLocatorUid(new Long(postalLocatorUID));

      }
      catch (SQLException sex) {
        logger.fatal("SQLException while inserting " +
                     "POSTAL_LOCATOR_TABLE: \n " + INSERT_POSTAL_LOCATOR, sex);
        throw new NEDSSDAOSysException( "Table Name : Postal_locator  " + sex.getMessage(), sex);
      }
      catch (NEDSSSystemException nae) {
        logger.fatal("Error while generating UID ", nae);
        throw new NEDSSSystemException(nae.toString());
      }
      catch (Exception ex) {
        logger.fatal("Error while generating UID ", ex);
        throw new NEDSSSystemException(ex.toString());
      }

      finally {
        closeStatement(preparedStmt);
        releaseConnection(dbConnection);
      }

      postalLocatorVO.setItNew(false);
      postalLocatorVO.setItDirty(false);
    } // end if

    return postalLocatorUID;
  }

  /**
   * end of inserting postal locator
   * @methodname updatePostalLocator
   * Updates a row in the postal_locator table
   * @param obj Object with modified information of type PostalLocatorDT
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
   * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
   * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
       * @exception NEDSSDAOSysException This is an exception thrown in the event of
   * ir-recoverable system errors.
       * @exception NEDSSSystemException This is an exception thrown in the event of
   * application errors.
   * @exception NEDSSSystemException This is an exception thrown by the BMPBase
   * super class
   * @roseuid 3C0D5B4D02A3
   */
  private void updatePostalLocator(Object obj) throws NEDSSDAOSysException,
      NEDSSSystemException {

    PostalLocatorDT postalLocatorVO = new PostalLocatorDT();
    postalLocatorVO = (PostalLocatorDT) obj;
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    int i = 1;
    int resultCount = 0;

    //Updates Postal Locator
    if (postalLocatorVO != null) {
      try {
        dbConnection = getConnection();
      }
      catch (NEDSSSystemException nsex) {
        logger.fatal("Error obtaining dbConnection " +
                     "while updating person address", nsex);
        throw new NEDSSSystemException(nsex.toString());
      }

      try {
        //updates postal_locator
        preparedStmt = dbConnection.prepareStatement(UPDATE_POSTAL_LOCATOR);
        preparedStmt.setString(i++, postalLocatorVO.getAddReasonCd());
        if (postalLocatorVO.getAddTime() == null) {
          preparedStmt.setNull(i++, Types.TIMESTAMP);
        }
        else {
          preparedStmt.setTimestamp(i++, postalLocatorVO.getAddTime());
        }
        if (postalLocatorVO.getAddUserId() == null) {
          preparedStmt.setNull(i++, Types.INTEGER);
        }
        else {
          preparedStmt.setLong(i++, postalLocatorVO.getAddUserId().longValue());
        }
        preparedStmt.setString(i++, postalLocatorVO.getCensusBlockCd());
        preparedStmt.setString(i++,
                               postalLocatorVO.getCensusMinorCivilDivisionCd());
        preparedStmt.setString(i++, postalLocatorVO.getCensusTrackCd());
        preparedStmt.setString(i++, postalLocatorVO.getCityCd());
        preparedStmt.setString(i++, postalLocatorVO.getCityDescTxt());
        preparedStmt.setString(i++, postalLocatorVO.getCntryCd());
        preparedStmt.setString(i++, postalLocatorVO.getCntryDescTxt());
        preparedStmt.setString(i++, postalLocatorVO.getCntyCd());
        preparedStmt.setString(i++, postalLocatorVO.getCntyDescTxt());
        preparedStmt.setString(i++, postalLocatorVO.getLastChgReasonCd());
        if (postalLocatorVO.getLastChgTime() == null) {
          preparedStmt.setNull(i++, Types.TIMESTAMP);
        }
        else {
          preparedStmt.setTimestamp(i++, postalLocatorVO.getLastChgTime());
        }
        if (postalLocatorVO.getLastChgUserId() == null) {
          preparedStmt.setNull(i++, Types.INTEGER);
        }
        else {
          preparedStmt.setLong(i++,
                               postalLocatorVO.getLastChgUserId().longValue());
        }
        preparedStmt.setString(i++, postalLocatorVO.getMSACongressDistrictCd());
        preparedStmt.setString(i++, postalLocatorVO.getRecordStatusCd());
        if (postalLocatorVO.getRecordStatusTime() == null) {
          preparedStmt.setNull(i++, Types.TIMESTAMP);
        }
        else {
          preparedStmt.setTimestamp(i++, postalLocatorVO.getRecordStatusTime());
        }
        preparedStmt.setString(i++, postalLocatorVO.getRegionDistrictCd());
        preparedStmt.setString(i++, postalLocatorVO.getStateCd());
        preparedStmt.setString(i++, postalLocatorVO.getStreetAddr1());
        preparedStmt.setString(i++, postalLocatorVO.getStreetAddr2());
        preparedStmt.setString(i++, postalLocatorVO.getUserAffiliationTxt());
        preparedStmt.setString(i++, postalLocatorVO.getZipCd());
        preparedStmt.setNull(i++, Types.VARCHAR); //updating postal locator - set geocode_match_ind to null
        preparedStmt.setString(i++, postalLocatorVO.getWithinCityLimitsInd());
        if (postalLocatorVO.getCensusTract() != null
				&& postalLocatorVO.getCensusTract().trim().length() > 0) {
			preparedStmt.setString(i++,
					postalLocatorVO.getCensusTract());
		} else {
			preparedStmt.setNull(i++, Types.VARCHAR);

		}
        if (postalLocatorVO.getPostalLocatorUid() == null) {
          preparedStmt.setNull(i++, Types.INTEGER);
        }
        else {
          preparedStmt.setLong(i++,
                               postalLocatorVO.getPostalLocatorUid().longValue());
        }
        resultCount = preparedStmt.executeUpdate();
      }
      catch (SQLException sex) {
        logger.fatal("SQLException while updating " +
                     "postal locator uid = " +
                     postalLocatorVO.getPostalLocatorUid(), sex);
        throw new NEDSSDAOSysException("Table Name : Postal_locator  " + sex.getMessage(), sex);
      }

      finally {
        closeStatement(preparedStmt);
        releaseConnection(dbConnection);
      }
    }
  }

	/**
	 * @methodname setGeocodeMatchIndicator
	 *     Sets the specified row's geocode_match_ind to 'Y'
	 * @param postalLocatorUID Postal locator UID
	 * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
	 * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
	 * @exception NEDSSDAOSysException This is an exception thrown in the event of
	 *     unrecoverable system errors.
	 * @exception NEDSSSystemException This is an exception thrown by the BMPBase
	 *     superclass
	 */
	public void setGeocodeMatchIndicator(long postalLocatorUID) throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			setGeocodeMatchIndicator(postalLocatorUID, UPDATE_POSTAL_LOCATOR_SET_GEOCODING_IND);
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * @methodname setGeocodeMatchIndicator
	 *     Sets the specified row's geocode_match_ind to 'E'
	 * @param postalLocatorUID Postal locator UID
	 * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
	 * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
	 * @exception NEDSSDAOSysException This is an exception thrown in the event of
	 *     unrecoverable system errors.
	 * @exception NEDSSSystemException This is an exception thrown by the BMPBase
	 *     superclass
	 */
	public void setGeocodeMatchIndicatorError(long postalLocatorUID) throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			setGeocodeMatchIndicator(postalLocatorUID, UPDATE_POSTAL_LOCATOR_SET_GEOCODING_IND_ERROR);
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * @methodname setGeocodeMatchIndicator
	 *     Sets the specified row's geocode_match_ind to the value indicated by
	 *     <code>updateString</code>
	 * @param postalLocatorUID Postal locator UID
	 * @param updateString SQL update statement (which includes indicated value)
	 * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
	 * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
	 * @exception NEDSSDAOSysException This is an exception thrown in the event of
	 *     unrecoverable system errors.
	 * @exception NEDSSSystemException This is an exception thrown by the BMPBase
	 *     superclass
	 */
	private void setGeocodeMatchIndicator(long postalLocatorUID, String updateString)
			throws NEDSSDAOSysException, NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
	
		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining dbConnection while setting Postal_locator geocode_match_ind", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
	
		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(updateString);
			preparedStmt.setLong(1, postalLocatorUID);
			preparedStmt.executeUpdate();
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while setting Postal_locator geocode_match_ind", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	/**
	 * @methodname clearAllGeocodeMatchIndicators Sets rows'
	 *             geocode_match_ind to NULL for rows that are Active and not flagged as errors
	 * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
	 * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
	 * @exception NEDSSDAOSysException
	 *                This is an exception thrown in the event of ir-recoverable
	 *                system errors.
	 * @exception NEDSSSystemException
	 *                This is an exception thrown by the BMPBase super class
	 */
	public void clearAllGeocodeMatchIndicators() throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			clearGeocodeMatchIndicators(false);
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * @methodname clearAllGeocodeMatchIndicators Sets rows'
	 *             geocode_match_ind to NULL for rows that are Active and not flagged as errors
	 * @param obj
	 *            Object with modified information of type PostalLocatorDT
	 * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
	 * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
	 * @exception NEDSSDAOSysException
	 *                This is an exception thrown in the event of ir-recoverable
	 *                system errors.
	 * @exception NEDSSSystemException
	 *                This is an exception thrown by the BMPBase super class
	 */
	public void clearErrorGeocodeMatchIndicators() throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			clearGeocodeMatchIndicators(true);
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * @methodname clearAllGeocodeMatchIndicators Sets all rows'
	 *             geocode_match_ind to NULL
	 * @param clearErrors
	 *            <code>true</code> if errors are to be cleared;
	 *            <code>false</code> if non-errors are to be cleared
	 * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
	 * @throws gov.cdc.nedss.exceptions.NEDSSSystemException
	 * @exception NEDSSDAOSysException
	 *                This is an exception thrown in the event of ir-recoverable
	 *                system errors.
	 * @exception NEDSSSystemException
	 *                This is an exception thrown by the BMPBase super class
	 */
	private void clearGeocodeMatchIndicators(boolean clearErrors) throws NEDSSDAOSysException, NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;

		// Obtain database connection //
		try {
			dbConnection = getConnection();
		}
		catch (NEDSSSystemException nsex) {
			logger.fatal("Error obtaining dbConnection while clearing Postal_locator geocode_match_ind's",
					nsex);
			throw new NEDSSSystemException(nsex.toString());
		}

		// Build select string //
		String selectString = UPDATE_POSTAL_LOCATOR_CLEAR_GEOCODING_INDS_BODY +
				(JOIN_POSTAL_LOCATORS_FOR_GEOCODING_SQLSERVER) +
				(clearErrors ? GEOCODING_IND_ERROR_CLAUSE : GEOCODING_IND_NOT_ERROR_CLAUSE) + ")";

		// Prepare & execute statement //
		try {
			preparedStmt = dbConnection.prepareStatement(selectString);
			resultCount = preparedStmt.executeUpdate();
		}
		catch (SQLException sex) {
			logger.fatal("SQLException while clearing Postal_locator geocode_match_ind's", sex);
			throw new NEDSSDAOSysException(sex.toString());
		}
		finally {
			closeStatement(preparedStmt);
			preparedStmt = null;
			releaseConnection(dbConnection);
		}
	}

	/**
	 * @methodname isValidData Checks if certian attributes of the value object
	 *             are not null
	 * @param obj
	 *            Object of type PostalLocatorDT
	 * @param Obj
	 * @return boolean
	 * @roseuid 3C0D5B4E0074
	 */
	protected boolean isValidData(Object Obj) {

		boolean validData = true;
		try{
			PostalLocatorDT postalLocatorVO = (PostalLocatorDT) Obj;
	
			if ((postalLocatorVO.getAddReasonCd() == null)
					|| (postalLocatorVO.getAddTime() == null)
					|| (postalLocatorVO.getAddUserId() == null)) {
				validData = false;
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return validData;
	}
}
/**
 * 
 * 
 * end of PostalLocatorDAOImpl class
 * 
 * 
 */
