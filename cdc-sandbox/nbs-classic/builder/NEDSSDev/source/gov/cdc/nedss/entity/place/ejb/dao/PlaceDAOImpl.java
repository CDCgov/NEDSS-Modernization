/**
 * Name:		PlaceDAOImpl.java
 * Description:	This is the implementation of NEDSSDAOInterface for the
 *               Place value object in the Placeentity bean.
 *               This class encapsulates all the JDBC calls made by the PlaceEJB
 *               for a Place object. Actual logic of
 *               inserting/reading/updating/deleting the data in relational
 *               database tables to mirror the state of PlaceEJB is
 *               implemented here.
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	Keith Welch
 * @version	1.0
 */

package gov.cdc.nedss.entity.place.ejb.dao;

import gov.cdc.nedss.entity.place.dt.PlaceDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;

public class PlaceDAOImpl extends BMPBase
{
    public final String   SELECT_PLACE_UID = "SELECT place_uid FROM place WHERE place_uid = ?";
    public final String   DELETE_PLACE     = "DELETE FROM place WHERE (place_uid = ?)";
    public final String   INSERT_ENTITY    = "INSERT INTO entity(entity_uid, class_cd) VALUES (?, ?)";
    public final String   SELECT_PLACE     = "SELECT " + "place_uid \"placeUid\", " 
                                                   + "add_reason_cd \"addReasonCd\", "
                                                   + "add_time \"addTime\", " 
                                                   + "add_user_id \"addUserId\", "
                                                   + "cd \"cd\", " 
                                                   + "cd_desc_txt \"cdDescTxt\", "
                                                   + "description \"description\", "
                                                   + "duration_amt \"durationAmt\", "
                                                   + "duration_unit_cd \"durationUnitCd\", "
                                                   + "from_time \"fromTime\", "
                                                   + "last_chg_reason_cd \"lastChgReasonCd\", "
                                                   + "last_chg_time \"lastChgTime\", "
                                                   + "last_chg_user_id \"lastChgUserId\", " 
                                                   + "nm \"nm\", "
                                                   + "record_status_cd \"recordStatusCd\", "
                                                   + "record_status_time \"recordStatusTime\", "
                                                   + "status_cd \"statusCd\", " 
                                                   + "status_time \"statusTime\", "
                                                   + "to_time \"toTime\", "
                                                   + "user_affiliation_txt \"userAffiliationTxt\", "
                                                   + "version_ctrl_nbr \"versionCtrlNbr\", "
                                                   + "local_id \"localId\" "
                                                   + "FROM  place " 
                                                   + "WHERE (place_uid=?)";
    
    public final String   UPDATE_PLACE     = "UPDATE place SET add_reason_cd = ?, add_time = ?, "
                                                   + "add_user_id = ?, cd = ?, cd_desc_txt = ?, "
                                                   + "description = ?, " 
                                                   + "duration_amt = ?, duration_unit_cd = ?, from_time = ?, "
                                                   + "last_chg_reason_cd = ?, last_chg_time = ?, "
                                                   + "last_chg_user_id = ?, nm = ?, record_status_cd = ?, "
                                                   + "record_status_time = ?, status_cd = ?, status_time = ?, "
                                                   + "to_time = ?, user_affiliation_txt = ? , "   
                                                   + "version_ctrl_nbr = ? "
                                                   + "WHERE (place_uid = ? AND version_ctrl_nbr = ?)";
    
    public final String   INSERT_PLACE     = "INSERT INTO place (place_uid, "
                                                   + "add_reason_cd, "
                                                   + "add_time, "
                                                   + "add_user_id, "
                                                   + "cd, "
                                                   + "cd_desc_txt, "
                                                   + "description, "
                                                   + "duration_amt, "
                                                   + "duration_unit_cd, "
                                                   + "from_time, "
                                                   + "last_chg_reason_cd, "
                                                   + "last_chg_time, "
                                                   + "last_chg_user_id, "
                                                   + "nm, "
                                                   + "record_status_cd, "
                                                   + "record_status_time, "
                                                   + "status_cd, "
                                                   + "status_time, "
                                                   + "to_time, "
                                                   + "user_affiliation_txt, " 
                                                   + "local_id, "
                                                   + "version_ctrl_nbr) "
                                                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private long          placeUID         = -1;

    // For logging
    static final LogUtils logger           = new LogUtils(PlaceDAOImpl.class.getName());

    public PlaceDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException
    {

    }

    /**
     * This method calls the insert methods to insert the Place value object
     * into the database and set the actual UID generated by UID Generator
     * 
     * @param Object
     *            -- Place Value Object
     * @return PlaceUid -- the newly created UID
     * @throws NEDSSSystemException
     */
    public long create(Object obj) throws NEDSSDAOSysException, NEDSSSystemException,
            NEDSSSystemException
    {
    	try{
	        placeUID = insertPlace((PlaceDT) obj);
	        ((PlaceDT) obj).setItNew(false);
	        ((PlaceDT) obj).setItDirty(false);
	        return placeUID;
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * This method is used to edit or update the existing record in the database
     * 
     * @param Object
     *            -- The Place Value Object
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException,
            NEDSSConcurrentDataException
    {
    	try{
    		updatePlace((PlaceDT) obj);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Remove the Place and Objects related to it corrosponding to the UID
     * 
     * @param placeUID
     *            -- Uid of the object to be removed
     * @throws NEDSSSystemException
     * @throws NEDSSDAOSysException
     */
    public void remove(long placeUID) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
    		removePlace(placeUID);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Object loadObject(long placeUID) throws NEDSSDAOSysException, NEDSSSystemException, NEDSSSystemException
    {
        PlaceDT placeDT = selectPlace(placeUID);
        placeDT.setItNew(false);
        placeDT.setItDirty(false);
        return placeDT;
    }

    /**
     * finds if the record exists in the database
     * 
     * @param PlaceUID
     * @return placePK
     */
    public Long findByPrimaryKey(long placeUID) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        if (placeExists(placeUID))
	            return (new Long(placeUID));
	        else
	            logger.error("No place found for this primary key :" + placeUID);
	        return null;
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * finds if the record exists in the database
     * 
     * @param PlaceUID
     * @return placePK
     */
    protected boolean placeExists(long placeUID) throws NEDSSDAOSysException, NEDSSSystemException,
            NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
        }
        catch (NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining dbConnection " + "for checking place existence: ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_PLACE_UID);
            preparedStmt.setLong(1, placeUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                placeUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch (SQLException sex)
        {
            logger.fatal("SQLException while checking for an" + " existing place's uid in place table-> " + placeUID,
                    sex);
            throw new NEDSSDAOSysException(sex.getMessage());
        }
        catch (Exception ex)
        {
            logger.fatal("Exception while checking for an" + " existing place's uid in place table-> " + placeUID, ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return returnValue;
    }

    /**
     * insert the new record for Place
     * 
     * @param PlaceDT
     *            -- the place DT
     * @return PlaceUid -- The uid for newly created Place Object
     * @throws NEDSSSystemException
     */
    private long insertPlace(PlaceDT placeDT) throws NEDSSSystemException, NEDSSSystemException
    {
        /**
         * Starts inserting a new place
         */
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null; 
        // long placeUID = -1;
        String localUID = null;
        UidGeneratorHelper uidGen = null;
        int resultCount = 0;

        try
        {
            try
            {
                dbConnection = getConnection();
            }
            catch (NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining db connection while inserting into place table", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                uidGen = new UidGeneratorHelper();
                placeUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
                preparedStmt = dbConnection.prepareStatement(INSERT_ENTITY);

                int i = 1;
                preparedStmt.setLong(i++, placeUID);
                preparedStmt.setString(i++, NEDSSConstants.PLACE);
                resultCount = preparedStmt.executeUpdate();

                // close statement before reuse
                preparedStmt.close();
                preparedStmt = null;
            }
            catch (SQLException sex)
            {
                logger.fatal("SQLException while generating " + "uid for PLACE_TABLE: \n", sex);
                throw new NEDSSSystemException(sex.toString());
            }
            catch (Exception ex)
            {
                logger.fatal("Error while inserting into ENTITY_TABLE, placeUID = " + placeUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }

            try
            {
                if (resultCount != 1)
                {
                    throw new NEDSSSystemException("Error while inserting "
                            + "uid into ENTITY_TABLE for a new place, resultCount = " + resultCount);
                }
                
                uidGen = new UidGeneratorHelper();
                localUID = uidGen.getLocalID(UidClassCodes.PLACE_CLASS_CODE);

                
                placeDT.setPlaceUid(new Long(placeUID));

                int i = 1;
                preparedStmt = dbConnection.prepareStatement(INSERT_PLACE);
                
                preparedStmt.setLong(i++, placeDT.getPlaceUid().longValue());
               
                preparedStmt.setString(i++, placeDT.getAddReasonCd());

                if (placeDT.getAddTime() == null)
                {
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                {
                    preparedStmt.setTimestamp(i++, placeDT.getAddTime());
                }
                 
                if (placeDT.getAddUserId() == null)
                {
                    preparedStmt.setNull(i++, Types.BIGINT);
                }
                else
                {
                    preparedStmt.setLong(i++, placeDT.getAddUserId().longValue());
                }
                preparedStmt.setString(i++, placeDT.getCd());
                preparedStmt.setString(i++, placeDT.getCdDescTxt());
                preparedStmt.setString(i++, placeDT.getDescription());
                
                preparedStmt.setString(i++, placeDT.getDurationAmt());
                preparedStmt.setString(i++, placeDT.getDurationUnitCd());

                if (placeDT.getFromTime() == null)
                {
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                {
                    preparedStmt.setTimestamp(i++, placeDT.getFromTime());
                }
                preparedStmt.setString(i++, placeDT.getLastChgReasonCd());
                if (placeDT.getLastChgTime() == null)
                {
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                {
                    preparedStmt.setTimestamp(i++, placeDT.getLastChgTime());
                }
                if (placeDT.getLastChgUserId() == null)
                {
                    preparedStmt.setNull(i++, Types.BIGINT);
                }
                else
                {
                    preparedStmt.setLong(i++, placeDT.getLastChgUserId().longValue());
                }
                preparedStmt.setString(i++, placeDT.getNm());
             
                preparedStmt.setString(i++, placeDT.getRecordStatusCd());  
                if (placeDT.getRecordStatusTime() == null)
                {
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                {
                    preparedStmt.setTimestamp(i++, placeDT.getRecordStatusTime());
                }
                
                preparedStmt.setString(i++, placeDT.getStatusCd() ); 
                if (placeDT.getStatusTime() == null)
                {
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                {
                    preparedStmt.setTimestamp(i++, placeDT.getStatusTime());
                } 
                
                if (placeDT.getToTime() == null)
                {
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                {
                    preparedStmt.setTimestamp(i++, placeDT.getToTime());
                }

                preparedStmt.setString(i++, placeDT.getUserAffiliationTxt()); 
                preparedStmt.setString(i++, localUID);
                preparedStmt.setShort(i++, placeDT.getVersionCtrlNbr().shortValue());
            
                resultCount = preparedStmt.executeUpdate();
                logger.debug("done insert place! placeUID = " + placeUID);

                return placeUID;
            }
            catch (SQLException sex)
            {
                logger.fatal("SQLException while inserting into PLACE_TABLE: \n"+sex.getMessage(), sex);
                throw new NEDSSSystemException(sex.toString());
            }
            catch (Exception ex)
            {
                logger.fatal("Error while inserting into PLACE_TABLE, id = " + placeUID+" "+ex.getMessage(), ex);
                throw new NEDSSSystemException(ex.toString());
            }
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }// end of inserting place

    /**
     * This method will Update the Place Object in the database
     * 
     * @param PlaceVO
     *            -the place value Object to be updated
     * @throws NEDSSSystemException
     */
    private void updatePlace(PlaceDT placeDT) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection();
        }
        catch (NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining db connection " + "while updating place table", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            // Updates place table
            if (placeDT != null)
            {
                logger.debug("Updating placeDT: UID = " + placeDT.getPlaceUid().longValue());

                preparedStmt = dbConnection.prepareStatement(UPDATE_PLACE);

                int i = 1;

                preparedStmt.setString(i++, placeDT.getAddReasonCd()); // 1
                preparedStmt.setTimestamp(i++, placeDT.getAddTime()); // 2
                if (placeDT.getAddUserId() == null) // 3
                {
                    preparedStmt.setNull(i++, Types.BIGINT);
                }
                else
                {
                    preparedStmt.setLong(i++, placeDT.getAddUserId().longValue());
                }
                preparedStmt.setString(i++, placeDT.getCd()); // 4
                preparedStmt.setString(i++, placeDT.getCdDescTxt()); // 5
                preparedStmt.setString(i++, placeDT.getDescription()); // 6
                preparedStmt.setString(i++, placeDT.getDurationAmt()); // 7
                preparedStmt.setString(i++, placeDT.getDurationUnitCd()); // 8
                if (placeDT.getFromTime() == null) // 9
                {
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                    preparedStmt.setTimestamp(i++, placeDT.getFromTime());
                preparedStmt.setString(i++, placeDT.getLastChgReasonCd()); // 10
                if (placeDT.getLastChgTime() == null) // 11
                {
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                }
                else
                    preparedStmt.setTimestamp(i++, placeDT.getLastChgTime());
                if (placeDT.getLastChgUserId() == null) // 12
                {
                    preparedStmt.setNull(i++, Types.BIGINT);
                }
                else
                {
                    preparedStmt.setLong(i++, placeDT.getLastChgUserId().longValue());
                }
                preparedStmt.setString(i++, placeDT.getNm()); // 13

                preparedStmt.setString(i++, placeDT.getRecordStatusCd()); // 14
                preparedStmt.setTimestamp(i++, placeDT.getRecordStatusTime()); // 15
                if (placeDT.getStatusCd() == null) // 16
                {
                    logger.error("PlaceDAOImpl:updatePlace :- The status cd is null and databse doesn't allow this");
                }
                else
                    preparedStmt.setString(i++, placeDT.getStatusCd());
                if (placeDT.getStatusTime() == null) // 17
                {
                    logger.error("PlaceDAOImpl:updatePlace :- The status time is null and databse doesn't allow this");
                }
                else
                    preparedStmt.setTimestamp(i++, placeDT.getStatusTime());
                preparedStmt.setTimestamp(i++, placeDT.getToTime()); // 18
                preparedStmt.setString(i++, placeDT.getUserAffiliationTxt()); // 19
                
                preparedStmt.setInt(i++, (placeDT.getVersionCtrlNbr().intValue())); // 20
                preparedStmt.setLong(i++, placeDT.getPlaceUid().longValue()); // 21
                preparedStmt.setInt(i++, placeDT.getVersionCtrlNbr().intValue() - 1); // 22

                resultCount = preparedStmt.executeUpdate();

                logger.debug("Done updating place, UID = " + (placeDT.getPlaceUid()).longValue());
                if (resultCount <= 0)
                {
                    logger.error("Error: none or more than one place updated at a time, resultCount = " + resultCount);
                    throw new NEDSSConcurrentDataException(
                            "NEDSSConcurrentDataException: PlaceDAOImpl.UpdatePlace The data have been modified by other user, please verify!");
                }
            }
        }
        catch (SQLException sqx)
        {
            logger.fatal("SQLException while updating " + "place into PLACE_TABLE: \n"+sqx.getMessage(), sqx);
            throw new NEDSSSystemException(sqx.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }// end of updating place table

    /**
     * Load the PlaceVO object corrosponding to the passed Uid
     * 
     * @param placeUID
     * @return PlaceDT
     * @throws NEDSSSystemException
     */
    private PlaceDT selectPlace(long placeUID) throws NEDSSSystemException, NEDSSSystemException
    {
        PlaceDT placeDT = new PlaceDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object>();

        try
        {
            dbConnection = getConnection();
        }
        catch (NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " + "for selectPlace ", nsex);
            throw new NEDSSSystemException(nsex.getMessage());
        }

        /**
         * Selects place from Place table
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_PLACE);
            preparedStmt.setLong(1, placeUID);
            resultSet = preparedStmt.executeQuery();

            logger.debug("PlaceDT object for: placeUID = " + placeUID);

            resultSetMetaData = resultSet.getMetaData();

            placeDT.setPlaceUid(new Long(placeUID));

            pList = (ArrayList<Object>) resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData,
                    placeDT.getClass(), pList);

            placeDT.setPlaceUid(new Long(placeUID));

            for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
            {
                placeDT = (PlaceDT) anIterator.next();
                placeDT.setItNew(false);
                placeDT.setItDirty(false);
            }

        }
        catch (SQLException sex)
        {
            logger.fatal("SQLException while selecting " + "place vo; id = " + placeUID, sex);
            throw new NEDSSSystemException(sex.getMessage());
        }
        catch (Exception ex)
        {
            logger.fatal("Exception while selecting " + "place vo; id = " + placeUID, ex);
            throw new NEDSSSystemException(ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        logger.debug("returning place DT object");
        return placeDT;
    }// end of selecting place

    /**
     * This method will remove the place object from the database corrosponding
     * to the passed UID
     * 
     * @param PlaceUID
     * @throws NEDSSSystemException
     */
    private void removePlace(long placeUID) throws NEDSSSystemException, NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection();
        }
        catch (NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " + "for deleting place ethnic groups ",
                    nsex);
            throw new NEDSSSystemException(nsex.getMessage());
        }

        /**
         * Deletes places
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(DELETE_PLACE);
            preparedStmt.setLong(1, placeUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error("Error: cannot delete place from PLACE_TABLE!! resultCount = " + resultCount);
            }
        }
        catch (SQLException sex)
        {
            logger.fatal("SQLException while removing " + "place; id = " + placeUID, sex);
            throw new NEDSSSystemException(sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }// end of removing places

}// end of PlaceDAOImpl class
