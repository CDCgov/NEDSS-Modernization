package gov.cdc.nedss.act.actid.dao;


import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * This is the implementation of NEDSSDAOInterface for the
 * ActivityId value object in the Observation activity bean.
 * This class encapsulates all the JDBC calls made by the ObservationEJB
 * for a ActivityId object. Actual logic of
 * inserting/reading/updating/deleting the data in relational
 * database tables to mirror the state of ObservationEJB is
 * implemented here.
 * 
 * @author John Park, Brent Chen, & NEDSS Development Team
 * @version 1.0
 */
public class ActivityIdDAOImpl extends BMPBase
{
    //For logging
    /**
     * An instance of the LogUtils class.
     */
    static final LogUtils logger = new LogUtils(ActivityIdDAOImpl.class.getName());

    //SQL Statements
    /**
     * SELECT statement for ActIDUid
     */
    private static final String SELECT_ACTIVITY_ID_UID =
    	"SELECT act_uid FROM "
		+ DataTables.ACTIVITY_ID_TABLE
		+ " WHERE act_uid = ?";


	/**
	 * INSERT statement for ActIDUid
	 */
	private static final String INSERT_ACTIVITY_ID =
		"INSERT INTO " + DataTables.ACTIVITY_ID_TABLE
		+ "(act_uid, act_id_seq, add_reason_cd, add_time, add_user_id,"
		+ "assigning_authority_cd, assigning_authority_desc_txt, duration_amt, "
		+ "duration_unit_cd, last_chg_reason_cd, last_chg_time, last_chg_user_id, "
		+ "record_status_cd, record_status_time, root_extension_txt, "
		+ "status_cd, status_time, type_cd, type_desc_txt, user_affiliation_txt, "
		+ "valid_from_time, valid_to_time) VALUES "
		+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	/**
	 * UPDATE statement for ActIDUid
	 */
	private static final String UPDATE_ACTIVITY_ID =
		"UPDATE " + DataTables.ACTIVITY_ID_TABLE + " set add_reason_cd = ?"
		+ ", add_time  = ?, add_user_id = ?, assigning_authority_cd = ?"
		+ ", assigning_authority_desc_txt = ?, duration_amt = ?, duration_unit_cd = ?"
		+ ", last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?"
		+ ", record_status_cd = ?, record_status_time = ?, root_extension_txt = ?"
		+ ", status_cd = ?, status_time = ?, type_cd = ?, type_desc_txt = ?"
		+ ", user_affiliation_txt = ?, valid_from_time = ?, valid_to_time = ?"
		+ " WHERE act_uid = ? AND act_id_seq = ?";


	/**
	 * SELECT statement for ActIDs
	 */
	private static final String SELECT_ACTIVITY_IDS =
        "SELECT act_uid \"actUid\", act_id_seq \"actIdSeq\", "
		+ "add_reason_cd \"addReasonCd\", add_time \"addTime\", "
		+ "add_user_id \"addUserId\", "
		+ "assigning_authority_cd \"assigningAuthorityCd\", "
		+ "assigning_authority_desc_txt \"assigningAuthorityDescTxt\", "
		+ "duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\", "
		+ "last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", "
		+ "last_chg_user_id \"lastChgUserId\", record_status_cd \"recordStatusCd\", "
		+ "record_status_time \"recordStatusTime\", "
		+ "root_extension_txt \"rootExtensionTxt\", status_cd \"statusCd\", "
		+ "status_time \"statusTime\", type_cd \"typeCd\", "
		+ "type_desc_txt \"typeDescTxt\", "
		+ "user_affiliation_txt \"userAffiliationTxt\", "
		+ "valid_from_time \"validFromTime\", valid_to_time \"validToTime \"FROM "
		+ DataTables.ACTIVITY_ID_TABLE + " WHERE act_uid = ?";

	/**
	 * DELETE statement for ActIDs.
	 */
	private static final String DELETE_ACTIVITY_IDS =
		"DELETE FROM " + DataTables.ACTIVITY_ID_TABLE + " WHERE act_uid = ?";

    /**
     * Default constructor.
     */
    public ActivityIdDAOImpl()
    {
    }

    /**
     * Method used to create an ActID.
     * 
     * @param observationUid : long value
     * @param coll : Collection
     * @exception NEDSSSystemException 
     * @return : long value
     */
    public long create(long observationUid, Collection<Object> coll) throws NEDSSSystemException
    {

    	try{
        insertActivityIDs(observationUid, coll);
        return observationUid;
    	}catch(Exception ex){
    		logger.fatal("observationUid: "+observationUid+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    }
    }

    /**
     * Method used to store an ActID.
     * 
     * @param coll Collection
     * @exception NEDSSSystemException 
     */
    public void store(Collection<Object> coll) throws NEDSSSystemException
    {
        updateActivityIDs(coll);
    }

    /**
     * Method used to retreive an ActID.
     * 
     * @param observationUid : long value
     * @exception NEDSSSystemException 
     * @return Collection
     */
    public Collection<Object> load(long observationUid) throws
		NEDSSSystemException
    {
        Collection<Object> idColl = selectActivityIDs(observationUid);
        return idColl;
    }

    /**
     * Method used to remove an ActID.
     * 
     * @param observationUid : long value
     * @exception NEDSSSystemException 
     */
    public void remove(long observationUid) throws  NEDSSSystemException
    {
        removeActivityIDs(observationUid);
    }

    /**
     * Finds an ActID by it's primary key value.
     * 
     * @param observationUid : long value
     * @exception NEDSSSystemException 
     * @return Long
     */
    public Long findByPrimaryKey(long observationUid) throws
		NEDSSSystemException
    {
    	try{
	        if (ActivityIdExists(observationUid))
	            return (new Long(observationUid));
	        else
	            logger.error("Primary key not found in ACTIVITY_ID_TABLE:"
	            + observationUid);
	            return null;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }


    /**
     * Checks if and ActID exists.
     * 
     * @param observationUid : long value
     * @exception NEDSSSystemException 
     * @return : boolean value
     */
    protected boolean ActivityIdExists (long observationUid) throws
            NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();

            preparedStmt = dbConnection.prepareStatement(SELECT_ACTIVITY_ID_UID);

            logger.debug("observationUid = " + observationUid);
            preparedStmt.setLong(1, observationUid);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                observationUid = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an"
                            + " existing activity's uid in ACTIVITY_ID_TABLE -> "
                            + observationUid , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an"
                            + " existing activity's uid -> " + observationUid , nsex);
            throw new NEDSSDAOSysException( nsex.getMessage());
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
     * Method to insert a collection of ActIDs
     * 
     * @param observationUid : long value
     * @param activityIDs : Collection
     * @exception NEDSSSystemException 
     */
    private void insertActivityIDs(long observationUid, Collection<Object> activityIDs)
                throws NEDSSSystemException
    {
        Iterator<Object> anIterator = null;
        ArrayList<Object>  activityList = (ArrayList<Object> )activityIDs;

        try
        {
            /**
             * Inserts activity ids
             */
            anIterator = activityList.iterator();

            while(anIterator.hasNext())
            {
            logger.debug("Number of elements: " + activityList.size());
                ActIdDT activityID = (ActIdDT)anIterator.next();

                if (activityID != null)
                insertActivityID(observationUid, activityID);
                //else
                //throw new NEDSSObservationDAOAppException("Empty person name collection");
                activityID.setActUid(new Long(observationUid));
            }
        }
        catch(Exception ex)
        {
            logger.fatal("observationUid: "+observationUid+" Exception while inserting " +
                    "activity ids into ACTIVITY_ID_TABLE: \n"+ex.getMessage(), ex);
            throw new NEDSSDAOSysException( ex.toString() );
        }
      logger.debug("Done inserting all activity ids");
    }//end of inserting activity ids

    /**
     * Inserts an individual ActID
     * 
     * @param observationUid : long value
     * @param activityID : ActIdDT
     * @exception NEDSSSystemException 
     */
    private void insertActivityID(long observationUid, ActIdDT activityID)
            throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining dbConnection " +
                "for inserting activity ids", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(INSERT_ACTIVITY_ID);

            int i = 1;

            logger.debug("Observation Uid = " + observationUid);
            logger.debug("Activity  id seq number = " + activityID.getActIdSeq());
            preparedStmt.setLong(i++, observationUid);
            if(activityID.getActIdSeq() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setInt(i++, (activityID.getActIdSeq()).intValue());
            preparedStmt.setString(i++, activityID.getAddReasonCd());
            preparedStmt.setTimestamp(i++, activityID.getAddTime());
            if(activityID.getAddUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, (activityID.getAddUserId()).longValue());
            preparedStmt.setString(i++, activityID.getAssigningAuthorityCd());
            preparedStmt.setString(i++, activityID.getAssigningAuthorityDescTxt());
            preparedStmt.setString(i++, activityID.getDurationAmt());
            preparedStmt.setString(i++, activityID.getDurationUnitCd());
            preparedStmt.setString(i++, activityID.getLastChgReasonCd());
            preparedStmt.setTimestamp(i++, activityID.getLastChgTime());
            if(activityID.getLastChgUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, (activityID.getLastChgUserId()).longValue());
            preparedStmt.setString(i++, activityID.getRecordStatusCd());
            preparedStmt.setTimestamp(i++, activityID.getRecordStatusTime());
            preparedStmt.setString(i++, activityID.getRootExtensionTxt());
            if(activityID.getStatusCd() == null)
                preparedStmt.setString(i++, "A");
            else
                preparedStmt.setString(i++, activityID.getStatusCd());
            if(activityID.getStatusTime() == null)
                preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, activityID.getStatusTime());
            preparedStmt.setString(i++, activityID.getTypeCd());
            preparedStmt.setString(i++, activityID.getTypeDescTxt());
            preparedStmt.setString(i++, activityID.getUserAffiliationTxt());
            preparedStmt.setTimestamp(i++, activityID.getValidFromTime());
            preparedStmt.setTimestamp(i++, activityID.getValidToTime());

            resultCount = preparedStmt.executeUpdate();

            if ( resultCount != 1 )
                    logger.error
                        ("Error: none or more than one activity ids inserted at a time, " +
                        "resultCount = " + resultCount);
            else
            {
                activityID.setItDirty(false);
                activityID.setItNew(false);
                activityID.setItDelete(false);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while inserting " +
                    "an activity id into ACTIVITY_ID_TABLE: \n"+sex.getMessage(), sex);
            throw new NEDSSDAOSysException( "Table Name : "+ DataTables.ACTIVITY_ID_TABLE + "  For observationUid: "+observationUid+"  "+sex.toString(), sex );
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }


    /**
     * Updateds a collection of ActIDs
     * 
     * @param activityIDs : Collection
     * @exception NEDSSSystemException 
     */
    private void updateActivityIDs(Collection<Object> activityIDs) throws NEDSSSystemException
    {
        Iterator<Object> anIterator = null;


        /**
         * Updates activity ids
         */

        if(activityIDs != null )
        {
            try
            {
                for(anIterator = activityIDs.iterator(); anIterator.hasNext(); )
                {
                    ActIdDT activityID = (ActIdDT)anIterator.next();
                    if(activityID != null && activityID.getActUid() != null
                        && activityID.getActIdSeq() != null )
                    {
                      logger.debug("Check dirty markers for Store activity id: new = " + activityID.isItNew() + ", dirty = " + activityID.isItDirty());
                      logger.debug("Store activity id: UID = " + activityID.getActUid() + "id seq number:" + activityID.getActIdSeq());
                      if(activityID.isItNew())
                        insertActivityID((activityID.getActUid()).longValue(), activityID);
                      else
                      {
                        if(activityID.isItDirty())
                          updateActivityID(activityID);
                      }
                    }
                    else
                    {
                            logger.error("Error: Empty activity id collection");
                    }
                }
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    "activity ids, \n"+ex.getMessage() , ex);
                throw new NEDSSDAOSysException( ex.toString() );
            }
        }
    }//end of updating activity id table

    /**
     * Updates an individual ActID.
     * 
     * @param activityID : ActIdDT
     * @exception NEDSSSystemException 
     */
    private void updateActivityID(ActIdDT activityID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Updates an activity id
         */

        if(activityID != null && activityID.getActUid() != null
              && activityID.getActIdSeq() != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "for updating activity ids"+nsex.getMessage(), nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                preparedStmt = dbConnection.prepareStatement(UPDATE_ACTIVITY_ID);

                int i = 1;

logger.debug("Activity id seq = " + activityID.getActIdSeq());

                preparedStmt.setString(i++, activityID.getAddReasonCd());
                preparedStmt.setTimestamp(i++, activityID.getAddTime());
                if(activityID.getAddUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, (activityID.getAddUserId()).longValue());
                preparedStmt.setString(i++, activityID.getAssigningAuthorityCd());
                preparedStmt.setString(i++, activityID.getAssigningAuthorityDescTxt());
                preparedStmt.setString(i++, activityID.getDurationAmt());
                preparedStmt.setString(i++, activityID.getDurationUnitCd());
                preparedStmt.setString(i++, activityID.getLastChgReasonCd());
                preparedStmt.setTimestamp(i++, activityID.getLastChgTime());
                if(activityID.getLastChgUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, (activityID.getLastChgUserId()).longValue());
                preparedStmt.setString(i++, activityID.getRecordStatusCd());
                preparedStmt.setTimestamp(i++, activityID.getRecordStatusTime());
                preparedStmt.setString(i++, activityID.getRootExtensionTxt());
                if(activityID.getStatusCd() == null)
                preparedStmt.setString(i++, "A");
                else
                    preparedStmt.setString(i++, activityID.getStatusCd());
                if(activityID.getStatusTime() == null)
                    preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
                else
                    preparedStmt.setTimestamp(i++, activityID.getStatusTime());
                preparedStmt.setString(i++, activityID.getTypeCd());
                preparedStmt.setString(i++, activityID.getTypeDescTxt());
                preparedStmt.setString(i++, activityID.getUserAffiliationTxt());
                preparedStmt.setTimestamp(i++, activityID.getValidFromTime());
                preparedStmt.setTimestamp(i++, activityID.getValidToTime());
                preparedStmt.setLong(i++, (activityID.getActUid()).longValue());
                preparedStmt.setInt(i++, (activityID.getActIdSeq()).intValue());

                resultCount = preparedStmt.executeUpdate();
    logger.debug("Done updating an activity id");
                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one activity id updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    "activity ids, \n"+sex.getMessage(), sex);
                throw new NEDSSDAOSysException("Table Name : "+ DataTables.ACTIVITY_ID_TABLE + "  "+sex.toString(), sex );
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating activity id table


    /**
     * Method for retrieving a collection of ActIDs.
     * 
     * @param observationUid : long value
     * @exception NEDSSSystemException 
     * @return Collection
     */
    private Collection<Object> selectActivityIDs (long observationUid) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ActIdDT activityID = new ActIdDT();
        ResultSetUtils resultSetUtils = new ResultSetUtils();


        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectActivityIDs "+nsex.getMessage() , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects activity ids
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_ACTIVITY_IDS);

            preparedStmt.setLong(1, observationUid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  idList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();
            idList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, activityID.getClass(), idList);


            /*if (idList.isEmpty())
                throw new NEDSSObservationDAOAppException("No activity ids for this observationUid: " +
                                   observationUid);
            else */
            for(Iterator<Object> anIterator = idList.iterator(); anIterator.hasNext(); )
            {
                ActIdDT reSetName = (ActIdDT)anIterator.next();
                reSetName.setItNew(false);
                reSetName.setItDirty(false);
                reSetList.add(reSetName);
                logger.debug("returned rootextensiontxt: " + reSetName.getRootExtensionTxt());
            }

            logger.debug("Return activity id collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "activity id collection; uid = " + observationUid , se);
            throw new NEDSSDAOSysException(se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Error in result set handling while selecting activity ids.", rsuex);
            throw new NEDSSDAOSysException(rsuex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selection " +
                  "activity ids; uid = " + observationUid, ex);
            throw new NEDSSDAOSysException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    /**
     * Method to remove an ActID.
     * 
     * @param observationUid : long value
     * @exception NEDSSSystemException 
     */
    private void removeActivityIDs (long observationUid) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for deleting activity ids " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Deletes activity ids
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(DELETE_ACTIVITY_IDS);

            preparedStmt.setLong(1, observationUid);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete activity ids from ACTIVITY_ID_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "activity ids; observationUid = " + observationUid, sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }
//   test stuff main and getconnection
/*    protected synchronized Connection getConnection(){
    Connection conn = null;
    try{
      Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver");
      dbConnection = DriverManager.getConnection("jdbc:sssw:odbc:nedss1", "sa","sapasswd");

    } catch (ClassNotFoundException cnf) {
      logger.debug("Can not load Database Driver in main try");
    } catch (SQLException se) {
      logger.debug(se);
    }
    return dbConnection;
}

//test main
    public static void main(String args[])
    {
      logger.debug("ActivityIdDAOImpl - Doing the main test thingy");
      try
      {
        ActivityIdDAOImpl activityDAO = new ActivityIdDAOImpl();
        Long actuid = new Long(12);
        Integer idSeq = new Integer(3);
        Long tactuid = new Long(12);
        Integer tidSeq = new Integer(4);

        ActIdDT activityDT = new ActIdDT();
        activityDT.setActUid(actuid);
        activityDT.setActIdSeq(idSeq);
        activityDT.setAddTime(new Timestamp(new java.util.Date().getTime()));
        activityDT.setAssigningAuthorityCd("MIGovt");
        activityDT.setAssigningAuthorityDescTxt("MI Government");
        activityDT.setLastChgTime(new Timestamp(new java.util.Date().getTime()));
        activityDT.setRecordStatusTime(new Timestamp(new java.util.Date().getTime()));
        activityDT.setRootExtensionTxt("444-77-7834");
        activityDT.setStatusCd("A");
        activityDT.setStatusTime(new Timestamp(new java.util.Date().getTime()));
        activityDT.setTypeCd("SS");
        activityDT.setTypeDescTxt("Social Security Number");
        activityDT.setItNew(false);
        activityDT.setItDirty(true);

        ActIdDT tActivityDT = new ActIdDT();
        tActivityDT.setActUid(tactuid);
        tActivityDT.setActIdSeq(tidSeq);
        tActivityDT.setAddTime(new Timestamp(new java.util.Date().getTime()));
        tActivityDT.setAssigningAuthorityCd("CAGovt");
        tActivityDT.setAssigningAuthorityDescTxt("CA Government");
        tActivityDT.setLastChgTime(new Timestamp(new java.util.Date().getTime()));
        tActivityDT.setRecordStatusTime(new Timestamp(new java.util.Date().getTime()));
        tActivityDT.setRootExtensionTxt("333-77-7834");
        tActivityDT.setStatusCd("A");
        tActivityDT.setStatusTime(new Timestamp(new java.util.Date().getTime()));
        tActivityDT.setTypeCd("SS");
        tActivityDT.setTypeDescTxt("Social Security Number");
        tActivityDT.setItNew(false);
        tActivityDT.setItDirty(true);

        Collection<Object>  cActivityDT = new ArrayList<Object> ();
        logger.debug("activityDT.isItNew: " + activityDT.isItNew());
        logger.debug("activityDT.isDirty: " + activityDT.isItDirty());
        cActivityDT.add(activityDT);
        cActivityDT.add(tActivityDT);


        //activityDAO.create(actuid.longValue(), cActivityDT);
        //activityDAO.load(actuid.longValue());
        //activityDAO.remove(actuid.longValue());
        activityDAO.load(12);
        logger.debug("went through without errors...i think.. " );//+ obserDT.getObservationUid);


      }
      catch(Exception e)
      {
        logger.debug("\n\nObservationRootDAOImpl ERROR : Not good = \n" + e);
      }
    }
*/
//end of test stuff





}//end of ActivityIdDAOImpl class
