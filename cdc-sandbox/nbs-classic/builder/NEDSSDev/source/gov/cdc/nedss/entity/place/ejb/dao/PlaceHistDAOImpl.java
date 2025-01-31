package gov.cdc.nedss.entity.place.ejb.dao;

import gov.cdc.nedss.entity.place.dt.PlaceDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;


/**
 * Title: Description: Copyright: Copyright (c) 2002 Company:
 * 
 * @author
 * @version 1.0
 */

public class PlaceHistDAOImpl extends BMPBase
{
    static final LogUtils      logger            = new LogUtils(PlaceHistDAOImpl.class.getName());
    private long               placeUid          = -1;
    private short              versionCtrlNbr    = 0;

    public static final String INSERT_PLACE_HIST = "insert into place_hist(place_uid, version_ctrl_nbr, add_reason_cd, add_time, add_user_id, cd, cd_desc_txt, description, duration_amt, duration_unit_cd, from_time, last_chg_reason_cd, last_chg_time, last_chg_user_id, "
                                                         + "local_id, nm, record_status_cd, record_status_time, status_cd, status_time, to_time, user_affiliation_txt ) "
                                                         + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public final String        SELECT_PLACE_HIST = "select place_uid \"placeUid\", "
                                                         + "version_ctrl_nbr \"versionCtrlNbr\", "
                                                         + "add_reason_cd \"addReasonCd\", "
                                                         + "add_time \"addTime\", "
                                                         + "add_user_id \"addUserId\", "
                                                         + "cd \"cd\", cd_desc_txt \"cdDescTxt\", "
                                                         + "description \"description\", duration_amt \"durationAmt\", "
                                                         + "place_type \"placeType\", place_url \"placeUrl\", place_app_nm \"placeAppNm\", "
                                                         + "duration_unit_cd \"durationUnitCd\", from_time \"fromTime\", "
                                                         + "last_chg_reason_cd \"lastChgReasonCd\", "
                                                         + "last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", "
                                                         + "local_id \"localId\", nm \"nm\", "
                                                         + "record_status_cd \"recordStatusCd\", "
                                                         + "record_status_time \"recordStatusTime\", status_cd \"statusCd\", "
                                                         + "status_time \"statusTime\", to_time \"toTime\", "
                                                         + "user_affiliation_txt \"userAffiliationTxt\" "
                                                         + "from place_hist where place_uid = ? and version_ctrl_nbr = ?";

    /**
     * Default constructor
     */
    public PlaceHistDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException
    {

    }// end of constructor

    /**
     * Initializes the class attributes placeUid and versionCtrlNbr.
     * 
     * @param placeUid
     *            : long
     * @param versionCtrlNbr
     *            : short
     */
    public PlaceHistDAOImpl(long placeUid, short versionCtrlNbr) throws NEDSSDAOSysException, NEDSSSystemException
    {
        this.placeUid = placeUid;
        this.versionCtrlNbr = versionCtrlNbr;

    }// end of constructor

    /**
     * Results in the insertion of a place record into history
     * 
     * @param obj
     *            : Object The PlaceDT object to be inserted
     * @return void
     */
    public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        PlaceDT dt = (PlaceDT) obj;
	        if (dt == null)
	            throw new NEDSSSystemException("Error: try to store null PlaceDT object.");
	        insertPlaceHist(dt);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }// end of store()

    /**
     * Results in multiple insertions of place records into history
     * 
     * @param coll
     *            : Collection<Object> A collection of PlaceDT objects
     * @return void
     */
    public void store(Collection<Object> coll) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        Iterator<Object> it = coll.iterator();
	        while (it.hasNext())
	        {
	            this.store(it.next());
	        }// end of while
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }// end of store

    /**
     * Loads a Place record from history
     * 
     * @param placeUid
     *            : Long
     * @param versionCtrlNbr
     *            : Integer
     * @return PlaceDT This is a PlaceDT object which is stored in history
     */
    public PlaceDT load(Long placeUid, Integer versionCtrlNbr) throws NEDSSSystemException
    {
    	try{
	        logger.info("Starts loadObject() for a place history...");
	        PlaceDT plDT = selectPlaceHist(placeUid.longValue(), versionCtrlNbr.intValue());
	        plDT.setItNew(false);
	        plDT.setItDirty(false);
	        logger.info("Done loadObject() for a place history - return: " + plDT.toString());
	        return plDT;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }// end of load

    /**
     * Returns the versionCtrlNbr representing this object
     * 
     * @return versionCtrlNbr
     */
    public short getVersionCtrlNbr()
    {
        return this.versionCtrlNbr;
    }

     // ///////////////////////////private class
    // methods//////////////////////////////////

    /**
     * Results in the retrieval of a Place history record
     * 
     * @param placeUid
     *            : long
     * @param versionCtrlNbr
     *            : int
     * @return PlaceDT
     */
    private PlaceDT selectPlaceHist(long placeUid, int versionCtrlNbr) throws NEDSSSystemException
    {

        PlaceDT plDT = new PlaceDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch (NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " + "for selectPlaceHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_PLACE_HIST);
            preparedStmt.setLong(1, placeUid);
            preparedStmt.setLong(2, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

            logger.debug("PlaceDT object for place History: placeUid = " + placeUid);

            resultSetMetaData = resultSet.getMetaData();

            plDT = (PlaceDT) resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, plDT.getClass());

            plDT.setItNew(false);
            plDT.setItDirty(false);
        }
        catch (SQLException se)
        {
            logger.fatal("SQLException while selecting " + "place history; PlaceUid = " + placeUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Exception while selecting " + "place dt; PlaceUid = " + placeUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        logger.info("return PlaceDT for place history");

        return plDT;
    }// end of selectPlaceHist(...)

    /**
     * Results in the inertion of a place record into history
     * 
     * @param dt
     *            : PlaceDT
     * @return void
     */
    private void insertPlaceHist(PlaceDT dt) throws NEDSSDAOSysException, NEDSSSystemException
    {
        if (dt.getPlaceUid() != null)
        {
            int resultCount = 0;

            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;
            try
            {
                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(INSERT_PLACE_HIST);
                pStmt.setLong(i++, dt.getPlaceUid().longValue());
                pStmt.setShort(i++, dt.getVersionCtrlNbr().shortValue());

                if (dt.getAddReasonCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getAddReasonCd());

                if (dt.getAddTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getAddTime());

                if (dt.getAddUserId() == null)
                    pStmt.setNull(i++, Types.BIGINT);
                else
                    pStmt.setLong(i++, dt.getAddUserId().longValue());

                if (dt.getCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getCd());

                if (dt.getCdDescTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getCdDescTxt());

                if (dt.getDescription() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getDescription());

                if (dt.getDurationAmt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getDurationAmt());

                if (dt.getDurationUnitCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getDurationUnitCd());

                if (dt.getFromTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getFromTime());

                if (dt.getLastChgReasonCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getLastChgReasonCd());

                if (dt.getLastChgTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getLastChgTime());

                if (dt.getLastChgUserId() == null)
                    pStmt.setNull(i++, Types.BIGINT);
                else
                    pStmt.setLong(i++, dt.getLastChgUserId().longValue());

                if (dt.getLocalId() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getLocalId());

                if (dt.getNm() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getNm());

                /*
                 * if(dt.getOrgAccessPermis() == null) pStmt.setNull(i++,
                 * Types.VARCHAR); else pStmt.setString(i++,
                 * dt.getOrgAccessPermis());
                 * 
                 * if(dt.getProgAreaAccessPermis() == null) pStmt.setNull(i++,
                 * Types.VARCHAR); else pStmt.setString(i++,
                 * dt.getProgAreaAccessPermis());
                 */
                if (dt.getRecordStatusCd() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getRecordStatusCd());

                if (dt.getRecordStatusTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getRecordStatusTime());

                pStmt.setString(i++, dt.getStatusCd());
                pStmt.setTimestamp(i++, dt.getStatusTime());

                if (dt.getToTime() == null)
                    pStmt.setNull(i++, Types.TIMESTAMP);
                else
                    pStmt.setTimestamp(i++, dt.getToTime());

                if (dt.getUserAffiliationTxt() == null)
                    pStmt.setNull(i++, Types.VARCHAR);
                else
                    pStmt.setString(i++, dt.getUserAffiliationTxt());

                resultCount = pStmt.executeUpdate();
                if (resultCount != 1)
                {
                    throw new NEDSSSystemException(
                            "Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
                }

            }
            catch (SQLException se)
            {
            	logger.fatal("SQLException  = "+se.getMessage(), se);
                throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
            }
            finally
            {
                closeStatement(pStmt);
                releaseConnection(dbConnection);
            }// end of finally
        }// end of if
    }// end of insertPatientEncounterHist()

}// end of PlaceHistDAOImpl
