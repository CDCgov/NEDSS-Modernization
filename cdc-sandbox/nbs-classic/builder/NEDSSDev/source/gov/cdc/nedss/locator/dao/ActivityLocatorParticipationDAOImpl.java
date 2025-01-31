/**
* Name:		ActivityLocatorParticipationDAOImpl.java
* Description:	This extends BMPBase which implements NEDSSDAOInterface for the
*               ActivityLocatorParticipation value object.
*               This class encapsulates all the JDBC calls required to
*               create,load and store values into the activity_locator_participation table
*
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen
* @modified     Add modification Information here. Each person should add ones own.
* @version	1.0 10/04/2001
*/

package gov.cdc.nedss.locator.dao;

///////////////////////////////// Import(s) ////////////////////////////////////

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.ActivityLocatorParticipationDT;
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
import java.util.Date;
import java.util.Iterator;

// ... end imports


public class ActivityLocatorParticipationDAOImpl extends BMPBase
{
 //For logging
        static final LogUtils logger = new LogUtils(ActivityLocatorParticipationDAOImpl.class.getName());

        private static final String SPACE = " ";
        private static final String COMMA = ",";

        public static final String SELECT_ACT_LOCATOR_PARTICIPATIONS =
          "SELECT"+ SPACE+
          "act_uid AS \"actUid\" "+ COMMA+
          "locator_uid AS \"locatorUid\" "+ COMMA+
          "entity_uid AS\"entityUid\" "+ COMMA+
          "add_reason_cd AS \"addReasonCd\" "+ COMMA+
          "add_time AS \"addTime\" "+ COMMA+
          "add_user_id AS \"addUserId\" "+ COMMA+
          "duration_amt AS \"durationAmt\" "+ COMMA+
          "duration_unit_cd AS \"durationUnitCd\" "+ COMMA+
          "from_time AS \"fromTime\" "+ COMMA+
          "last_chg_reason_cd AS \"lastChgReasonCd\" "+ COMMA+
          "last_chg_time AS \"lastChgTime\" "+ COMMA+
          "last_chg_user_id AS \"lastChgUserId\" "+ COMMA+
          "record_status_cd AS \"recordStatusCd\" "+ COMMA+
          "record_status_time AS \"recordStatusTime\" "+ COMMA+
          "to_time AS \"toTime\" "+ COMMA+
          "status_cd AS \"statusCd\" "+ COMMA+
          "status_time AS \"statusTime\" "+ COMMA+
          "type_cd AS \"typeCd\" "+ COMMA+
          "type_desc_txt AS \"typeDescTxt\" "+ COMMA+
          "user_affiliation_txt AS \"userAffiliationTxt\" "+ SPACE+
            "FROM"+ SPACE+
             DataTables.ACTIVITY_LOCATOR_PARTICIPATION_TABLE+ SPACE+
            "WHERE"+ SPACE+
            "act_uid = ?";

     public static final String INSERT_ACT_LOCATOR_PARTICIPATION =
      "INSERT INTO"+ SPACE+
      DataTables.ACTIVITY_LOCATOR_PARTICIPATION_TABLE+ SPACE+
      "("+
      "act_uid"+ COMMA+
      "locator_uid"+ COMMA+
      "entity_uid"+ COMMA+
      "add_reason_cd"+ COMMA+
      "add_time"+ COMMA+
      "add_user_id"+ COMMA+
      "duration_amt"+ COMMA+
      "duration_unit_cd"+ COMMA+
      "from_time"+ COMMA+
      "last_chg_reason_cd"+ COMMA+
      "last_chg_time"+ COMMA+
      "last_chg_user_id"+ COMMA+
      "record_status_cd"+ COMMA+
      "record_status_time"+ COMMA+
      "to_time"+ COMMA+
      "status_cd"+ COMMA+
      "status_time"+ COMMA+
      "type_cd"+ COMMA+
      "type_desc_txt"+ COMMA+
      "user_affiliation_txt"+ SPACE+
      ")"+
      "VALUES"+ SPACE+
          "("+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
            "?"+ COMMA+
             "?"+
          ")";


      public static final String UPDATE_ACT_LOCATOR_PARTICIPATION =
      "UPDATE"+ SPACE+
      DataTables.ACTIVITY_LOCATOR_PARTICIPATION_TABLE+ SPACE+
      "SET"+ SPACE+
      "add_reason_cd = ?"+ COMMA+
      "add_time  = ?"+ COMMA+
      "add_user_id  = ?"+ COMMA+
      "duration_amt = ?"+ COMMA+
      "duration_unit_cd = ?"+ COMMA+
      "from_time = ?"+ COMMA+
      "last_chg_reason_cd = ?"+ COMMA+
      "last_chg_time = ?"+ COMMA+
      "last_chg_user_id = ?"+ COMMA+
      "record_status_cd = ?"+ COMMA+
      "record_status_time = ?"+ COMMA+
      "to_time = ?"+ COMMA+
      "status_cd = ?"+ COMMA+
      "status_time = ?"+ COMMA+
      "type_cd = ?"+ COMMA+
      "type_desc_txt = ?"+ COMMA+
      "user_affiliation_txt = ?"+ SPACE+
      "WHERE"+ SPACE+
      "act_uid = ?"+ SPACE+
      "AND"+ SPACE+
      "entity_uid = ?"+ SPACE+
      "AND"+ SPACE+
      "locator_uid = ?";

    public static final String SELECT_ENTITY_LOCATOR_PARTICIPATION =
      "SELECT"+ SPACE+
        "entity_uid"+ COMMA+
        "locator_uid"+ SPACE+
        "FROM"+ SPACE+
        DataTables.ENTITY_LOCATOR_PARTICIPATION_TABLE+ SPACE+
        "WHERE"+ SPACE+
        "entity_uid = ?"+ SPACE+
        "AND"+ SPACE+
        "locator_uid = ?";

    public static final String SELECT_ACT_LOCATOR_PARTICIPATION =
      "SELECT"+ SPACE+
      "act_uid"+ COMMA+
      "entity_uid"+ COMMA+
      "locator_uid"+ SPACE+
      "FROM"+ SPACE+
      DataTables.ACTIVITY_LOCATOR_PARTICIPATION_TABLE+ SPACE+
      "WHERE"+ SPACE+
      "act_uid = ?"+ SPACE+
      "AND"+ SPACE+
      "entity_uid = ?"+ SPACE+
      "AND"+ SPACE+
      "locator_uid = ?";


/////////////////////////////// Constructor(s) /////////////////////////////////
/**
 * ActivityLocatorParticipationDAOImpl is the default constructor requires no parameters
 */
    public ActivityLocatorParticipationDAOImpl()
    {
    }
///////////////////////////////// Methods(s) ///////////////////////////////////


/**
 * @methodname create
 * Iterates through an ArrayList<Object> of ActivityLocatorDTs and calls the create method with the activityUID and the value object
 * @param long Activity Unique ID
 * @param Collection<Object>  an ArrayList<Object> of ActivityLocators
 * @return  long a positive value if successful
 * @exception NEDSSDAOSysException This is an exception thrown in the event of ir-recoverable system errors.
 * @exception NEDSSSystemException This is an exception thrown in the event of application errors.
 * @exception NEDSSSystemException This is an exception thrown by the NEDSSUIDGenerator
 * Need to check if the locator already exists otherwise add
 */
    public long create(long activityUID, Collection<Object>  coll) throws NEDSSSystemException

    {
        ArrayList<Object> activityLocatorArray =  (ArrayList<Object> )coll;
       Iterator<Object>  iterator = activityLocatorArray.iterator();
        /**
          * Selects matching entity_locator_participation, if one and only one match found,
          * insert the activity_locator_participation into the table.
          */
       try{
	         while (iterator.hasNext())
	         {
	            ActivityLocatorParticipationDT  activityLocatorVO = (ActivityLocatorParticipationDT)iterator.next();
	
	            if (activityLocatorVO.getLocatorUid() != null && activityLocatorVO.getEntityUid() != null)
	            {
	                if(entityLocatorParticipationExists(activityLocatorVO.getEntityUid().longValue(), activityLocatorVO.getLocatorUid().longValue()))
	                insertActivityLocatorParticipation(activityUID, activityLocatorVO);
	                ////##!! System.out.println("Inserts a activity locator participation for act uid = " + activityUID);
	                logger.debug("Inserts a activity locator participation for act uid = " + activityUID);
	            }
	         }
	         return activityUID;
       }catch(Exception ex){
    	   logger.fatal("activityUID: "+activityUID+" Exception  = "+ex.getMessage(), ex);
    	   throw new NEDSSSystemException(ex.toString());
       }
    }//end of create()

 /**
 * @methodname store
 * Iterates through an ArrayList<Object> of ActivityLocatorDTs and calls the storeActivityLocatorParticipation method with the value object
 * @param obj Object with modified information of type ActivityLocatorParticipationDT
 * @return void
 * @exception NEDSSDAOSysException This is an exception thrown in the event of ir-recoverable system errors.
 * @exception NEDSSSystemException This is an exception thrown in the event of application errors.
 * @exception NEDSSSystemException This is an exception thrown by the BMPBase super class
 */
    public void store(Collection<Object> coll) throws NEDSSSystemException
   {
        ArrayList<Object> activityLocatorArray =  (ArrayList<Object> )coll;
       Iterator<Object>  iterator = activityLocatorArray.iterator();
        ActivityLocatorParticipationDT activityLocatorVO = new ActivityLocatorParticipationDT();

        try {
       // //##!! System.out.println("Start updating activity locators");
       logger.debug("Start updating activity locators");
         while (iterator.hasNext())
         {
            activityLocatorVO = (ActivityLocatorParticipationDT)iterator.next();

            if(activityLocatorVO.getActUid() != null &&
               activityLocatorVO.getEntityUid() != null &&
               activityLocatorVO.getLocatorUid() != null)
            {
				////##!! System.out.println("Check dirty1:" + activityLocatorVO.isItDirty());
				logger.debug("Check dirty1:" + activityLocatorVO.isItDirty());
                if(activityLocatorParticipationExists(activityLocatorVO.getActUid().longValue(),
                   activityLocatorVO.getEntityUid().longValue(), activityLocatorVO.getLocatorUid().longValue()))
                {
				////##!! System.out.println("Check dirty2:" + activityLocatorVO.isItDirty());
				logger.debug("Check dirty2:" + activityLocatorVO.isItDirty());
                    if( activityLocatorVO.isItDirty())
                        updateActivityLocatorParticipation(activityLocatorVO);
                }
                else if(entityLocatorParticipationExists(activityLocatorVO.getEntityUid().longValue(),
                                  activityLocatorVO.getLocatorUid().longValue()))
                {
                    if(activityLocatorVO.isItNew())
                    insertActivityLocatorParticipation(activityLocatorVO.getActUid().longValue(), activityLocatorVO);
                }
            }
			////##!! System.out.println("Done updating activity locators");
			logger.debug("Done updating activity locators");
         }
     } catch (NEDSSSystemException nae) {
              logger.fatal("NEDSSSystemException = "+nae.getMessage(), nae);
              throw new NEDSSDAOSysException("Error while generating UID "
                    + nae.toString() + " \n" + nae.getMessage());
      }
      catch(Exception ex)
      {
    	  logger.fatal("Exception = "+ex.getMessage(), ex);
          throw new NEDSSDAOSysException("Error while updating activity locator.");
      }
    }//end of store()


/**
 * @methodname load
 * Selects all the locator id for a given activityID from the activity_locator_participation table
 * Populates the ActivityLocatorParticipation value object by calling the load method with activityUID and the locatorUID
 * @param activityLocatorUID unique Activity identifier of the rows to be selected
 * @return Collection<Object>  of Objects of type ActivityLocatorParticipationDT
 * @exception NEDSSDAOSysException This is an exception thrown in the event of ir-recoverable system errors.
 * @exception NEDSSSystemException This is an exception thrown in the event of application errors.
 * @exception NEDSSSystemException This is an exception thrown by the BMPBase super class
 */


    public Collection<Object> load(long activityUID) throws  NEDSSSystemException
    {
      ActivityLocatorParticipationDT actLocatorPartDT = new ActivityLocatorParticipationDT();
      Connection dbConnection = null;
      PreparedStatement preparedStmt = null;
      ResultSet resultSet = null;
      ResultSetMetaData metaData = null;
      ArrayList<Object>  locatorList = new ArrayList<Object> ();
      ArrayList<Object>  reSetList = null;

      try {
            dbConnection = getConnection();

            preparedStmt = dbConnection.prepareStatement(SELECT_ACT_LOCATOR_PARTICIPATIONS);
            preparedStmt.setLong(1, activityUID);
            ////##!! System.out.println("Sql for activity locator befor execution" + WumSqlQuery.SELECT_ACT_LOCATOR_PARTICIPATIONS);
            logger.debug("Sql for activity locator befor execution" + SELECT_ACT_LOCATOR_PARTICIPATIONS);
            resultSet = preparedStmt.executeQuery();

            metaData = resultSet.getMetaData();
            ResultSetUtils  populateBean = new ResultSetUtils();
            reSetList = new ArrayList<Object> ();

            locatorList = (ArrayList<Object> ) populateBean.mapRsToBeanList(resultSet, metaData, actLocatorPartDT.getClass(), locatorList);

            if (!locatorList.isEmpty())
            {
                for(Iterator<Object> anIterator = locatorList.iterator(); anIterator.hasNext(); )
                {
                    ActivityLocatorParticipationDT reSetLocator = (ActivityLocatorParticipationDT)anIterator.next();
                    reSetLocator.setItNew(false);
                    reSetLocator.setItDirty(false);
                    reSetList.add(reSetLocator);
                }
            }

        }
        catch (ResultSetUtilsException resue)
        {
        	logger.error("activityUID: "+activityUID+" Exception = "+resue.getMessage(), resue);
        }
        catch (SQLException sex)
        {
        	logger.fatal("activityUID: "+activityUID+" Exception = "+sex.getMessage(), sex);
            throw new NEDSSDAOSysException("SQLException while loading " +
                  "a ActivityLocatorParticipation, activityUID = " + activityUID);

        }
        catch(Exception ex)
        {
        	logger.fatal("activityUID: "+activityUID+" Exception = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException("Exception while loading " +
                  "a ActivityLocatorParticipation, activityUID = " + activityUID);
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return reSetList;
    }//end of load()


/**
 * @methodname insertActivityLocatorParticipation
 * Inserts a row into the activity_locator_participation table
 * @param obj Object to be created
 * @return the unique id of the created object
 * @exception NEDSSDAOSysException This is an exception thrown in the event of ir-recoverable system errors.
 * @exception NEDSSSystemException This is an exception thrown in the event of application errors.
 * @exception NEDSSSystemException This is an exception thrown by the NEDSSUIDGenerator
 * @exception NEDSSSystemException This is an exception thrown by the BMPBase super class
 */



    private void insertActivityLocatorParticipation(long activityUID, ActivityLocatorParticipationDT activityLocatorVO)
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
        	logger.fatal("Exception = "+nsex.getMessage(), nsex);
            throw new NEDSSSystemException("SQLException while obtaining database connection " +
                            "for insertActivityLocatorParticipation " + nsex.getMessage());
        }

        //Inserts a Activity Locator Participation Row


            try
            {
                preparedStmt = dbConnection.prepareStatement(INSERT_ACT_LOCATOR_PARTICIPATION);

                int i = 1;
                preparedStmt.setLong(i++, activityUID);
                preparedStmt.setLong(i++, activityLocatorVO.getLocatorUid().longValue());
                preparedStmt.setLong(i++, activityLocatorVO.getEntityUid().longValue());
                preparedStmt.setString(i++, activityLocatorVO.getAddReasonCd());
                if(activityLocatorVO.getAddTime() == null)
                    preparedStmt.setTimestamp(i++, new Timestamp((new Date()).getTime()));
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getAddTime());
                if(activityLocatorVO.getAddUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, (activityLocatorVO.getAddUserId()).longValue());
                preparedStmt.setString(i++, activityLocatorVO.getDurationAmt());
                preparedStmt.setString(i++, activityLocatorVO.getDurationUnitCd());
                if(activityLocatorVO.getFromTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getFromTime());
                preparedStmt.setString(i++, activityLocatorVO.getLastChgReasonCd());
                if(activityLocatorVO.getLastChgTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getLastChgTime());
                if(activityLocatorVO.getLastChgUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, activityLocatorVO.getLastChgUserId().longValue());
                preparedStmt.setString(i++, activityLocatorVO.getRecordStatusCd());
                if(activityLocatorVO.getRecordStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getRecordStatusTime());
                if(activityLocatorVO.getToTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getToTime());
                if(activityLocatorVO.getStatusCd() == null)
                    preparedStmt.setString(i++, "A");
                else
                    preparedStmt.setString(i++, activityLocatorVO.getStatusCd());
                if(activityLocatorVO.getStatusTime() == null)
                    preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getStatusTime());
                preparedStmt.setString(i++, activityLocatorVO.getTypeCd());
                preparedStmt.setString(i++, activityLocatorVO.getTypeDescTxt());
                preparedStmt.setString(i++, activityLocatorVO.getUserAffiliationTxt());

                resultCount = preparedStmt.executeUpdate();

                if(resultCount != 1)
                {
                    throw new NEDSSDAOSysException
                        ("Error: none or more than one activity locator participations inserted at a time, " +
                        "resultCount = " + resultCount);
                }
                activityLocatorVO.setItNew(false);
                activityLocatorVO.setItDirty(false);

            }
            catch(SQLException sex)
            {
                logger.error("activityUID: "+activityUID+" Exception ="+sex.getMessage(), sex);
                throw new NEDSSDAOSysException("Table Name : "+ DataTables.ACTIVITY_LOCATOR_PARTICIPATION_TABLE+ "  For activityUID: "+activityUID+"  "+sex.toString(), sex);
             }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
    }//end of inserting Activity locator participation row
/**
 * @methodname updateActivityLocatorParticipation
 * Updates a row in the activity_locator_participation table
 * @param obj Object with modified information of type ActivityLocatorParticipationDT
 * @return void
 * @exception NEDSSDAOSysException This is an exception thrown in the event of ir-recoverable system errors.
 * @exception NEDSSSystemException This is an exception thrown in the event of application errors.
 * @exception NEDSSSystemException This is an exception thrown by the BMPBase super class
 */


    private void updateActivityLocatorParticipation (ActivityLocatorParticipationDT activityLocatorVO)
    throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int i = 1;
        int resultCount = 0;

       if(activityLocatorVO != null)
        {
          try
          {
            dbConnection = getConnection();
          }
          catch(NEDSSSystemException nsex)
          {
        	  logger.fatal("Exception = "+nsex.getMessage(), nsex);
            throw new NEDSSSystemException("Error obtaining dbConnection " +
                "while updating Activity_Locator_Participation");
          }

        //Updates Activity Locator Participation Row
           try
            {
                //updates activity_locator_participation
                preparedStmt = dbConnection.prepareStatement(UPDATE_ACT_LOCATOR_PARTICIPATION);

                preparedStmt.setString(i++, activityLocatorVO.getAddReasonCd());
                if(activityLocatorVO.getAddTime() == null)
                    preparedStmt.setTimestamp(i++, new Timestamp((new Date()).getTime()));
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getAddTime());
                if(activityLocatorVO.getAddUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, activityLocatorVO.getAddUserId().longValue());
                preparedStmt.setString(i++, activityLocatorVO.getDurationAmt());
                preparedStmt.setString(i++, activityLocatorVO.getDurationUnitCd());
                if(activityLocatorVO.getFromTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getFromTime());
                preparedStmt.setString(i++, activityLocatorVO.getLastChgReasonCd());
                if(activityLocatorVO.getLastChgTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getLastChgTime());
                if(activityLocatorVO.getLastChgUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++, activityLocatorVO.getLastChgUserId().longValue());
                preparedStmt.setString(i++, activityLocatorVO.getRecordStatusCd());
                if(activityLocatorVO.getRecordStatusTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getRecordStatusTime());
                if(activityLocatorVO.getToTime() == null)
                    preparedStmt.setNull(i++, Types.TIMESTAMP);
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getToTime());
                if(activityLocatorVO.getStatusCd() == null)
                    preparedStmt.setString(i++, "A");
                else
                    preparedStmt.setString(i++, activityLocatorVO.getStatusCd());
                if(activityLocatorVO.getStatusTime() == null)
                    preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
                else
                    preparedStmt.setTimestamp(i++, activityLocatorVO.getStatusTime());
                preparedStmt.setString(i++, activityLocatorVO.getTypeCd());
                preparedStmt.setString(i++, activityLocatorVO.getTypeDescTxt());
                preparedStmt.setString(i++, activityLocatorVO.getUserAffiliationTxt());
                preparedStmt.setLong(i++, activityLocatorVO.getActUid().longValue());
                preparedStmt.setLong(i++, activityLocatorVO.getEntityUid().longValue());
                preparedStmt.setLong(i++, activityLocatorVO.getLocatorUid().longValue());
                resultCount = preparedStmt.executeUpdate();
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating activity locator uid = " +activityLocatorVO.getActUid() + " \n" + sex.toString() + " \n" , sex);
                throw new NEDSSDAOSysException("Table Name : "+ DataTables.ACTIVITY_LOCATOR_PARTICIPATION_TABLE+"  "+sex.toString(), sex);
            }

            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updateActivityLocatorParticipation()

    /**
     * Determines if the EntityLocatorParticipation exists based on the parameters passed in.
     * @param entityUID : long
     * @param locatorUID :long
     * @return returnValue : boolean
     * @throws NEDSSSystemException
     */
    private boolean entityLocatorParticipationExists (long entityUID, long locatorUID) throws
            NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_ENTITY_LOCATOR_PARTICIPATION);
            preparedStmt.setLong(1, entityUID);
            preparedStmt.setLong(2, locatorUID);
            resultSet = preparedStmt.executeQuery();

            if(!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                returnValue = true;
            }
            return returnValue;
        }
        catch(SQLException sex)
        {
        	logger.fatal("entityUID: "+entityUID+", locatorUID: "+locatorUID+" Exception = "+sex.getMessage(), sex);
            throw new NEDSSDAOSysException("SQLException while checking for an"
                            + " existing entity locator participation's uid in " +
                            "ENTITY_LOCATOR_PARTICIPATION_TABLE, entityUID = "
                            + entityUID + ", locatorUID = " + locatorUID + " :\n" + sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
        	logger.fatal("entityUID: "+entityUID+", locatorUID: "+locatorUID+" Exception = "+nsex.getMessage(), nsex);
            throw new NEDSSDAOSysException("Exception while getting dbConnection for checking for an"
                            + " existing entity locator participation's uid -> " + entityUID + " :\n" + nsex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of activityLocatorParticipationExists()

    /**
     * Determines if the EntityLocatorParticipation exists based on the parameters passed in.
     * @param entityUID : long
     * @param locatorUID :long
     * @return returnValue : boolean
     * @throws NEDSSSystemException
     */
    private boolean activityLocatorParticipationExists(long activityUID, long entityUID, long locatorUID)
            throws  NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_ACT_LOCATOR_PARTICIPATION);
            preparedStmt.setLong(1, activityUID);
            preparedStmt.setLong(2, entityUID);
            preparedStmt.setLong(3, locatorUID);
            resultSet = preparedStmt.executeQuery();

            if(!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                returnValue = true;
            }
            return returnValue;
        }
        catch(SQLException sex)
        {
        	logger.fatal("activityUID: "+activityUID+", locatorUID: "+locatorUID+" Exception = "+sex.getMessage(), sex);
            throw new NEDSSDAOSysException("SQLException while checking for an"
                            + " existing activity locator participation's uid in " +
                            "ENTITY_LOCATOR_PARTICIPATION_TABLE -> "
                            + entityUID + " :\n" + sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
        	logger.fatal("activityUID: "+activityUID+", locatorUID: "+locatorUID+ "Exception = "+nsex.getMessage(), nsex);
            throw new NEDSSDAOSysException("Exception while getting dbConnection for checking for an"
                            + " existing activity locator participation's uid -> " + entityUID + " :\n" + nsex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of activityLocatorParticipationExists()

/**
 * @methodname isValidData
 * Checks if certian attributes of the value object are not null
 * @param obj Object of type ActivityLocatorParticipationDT
 * @return boolean
 */

    private boolean isValidData (Object Obj)
    {

          boolean validData = true;
          
          try{
			  ActivityLocatorParticipationDT activityLocatorVO = (ActivityLocatorParticipationDT) Obj;
			
			  if ((activityLocatorVO.getAddReasonCd() == null) ||
			       (activityLocatorVO.getAddTime() == null)    ||
			       (activityLocatorVO.getAddUserId() == null)  ||
			       (activityLocatorVO.getStatusCd()        == null)  ||
			       (activityLocatorVO.getRecordStatusCd() == null) ||
			       (activityLocatorVO.getStatusCd()       == null)
			      )
			    validData = false;
          }catch(Exception ex){
        	  logger.error("Exception = "+ex.getMessage(),ex);
        	  throw new NEDSSSystemException(ex.toString(), ex);
          }
          return validData;
    }
   /**
    * The following is for testing.
    */
/*   public static void main(String args[])
   {
    try
      {
        ActivityLocatorParticipationDAOImpl aDAO = new ActivityLocatorParticipationDAOImpl();
        ArrayList<Object> al = new ArrayList<Object> ();
        Long actUID = new Long(77);

        for(int i = 0; i<3; i++)
        {
            Long entityUID = new Long(283);
            Long locatorUID = new Long(582+i);
            ActivityLocatorParticipationDT aDT = new ActivityLocatorParticipationDT();
            aDT.setActUid(actUID);
            aDT.setEntityUid(entityUID);
            aDT.setLocatorUid(locatorUID);
            aDT.setDurationUnitCd("aNew");
            aDT.setItNew(true);
            aDT.setItDirty(false);
            al.add(aDT);
        }
        //aDAO.create(actUID.longValue(), al);
        //Collection<Object>  coll = aDAO.load(actUID.longValue());
        aDAO.store(al);
        //##!! System.out.println("Executed well!!!");
      }
      catch(Exception e)
      {
        //##!! System.out.println("DAO ERROR : turkey no worky = " + e);

      }
    }

 protected synchronized Connection getConnection()
 {

    try{
      Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver");
      dbConnection = DriverManager.getConnection("jdbc:sssw:odbc:nedss2", "nbs_ods", "ods");

    } catch (ClassNotFoundException cnf) {
      //##!! System.out.println("Can not load Database Driver");
    } catch (SQLException se) {
      //##!! System.out.println(se);
    }
    return dbConnection;
  }// end of getConnection() method

  public void finalize()
  {
    try{
          dbConnection.close();
        }
    catch(SQLException e)
        {
        }
  }// end of finalize() method

*/
}//end of ActivityLocatorParticipationDAOImpl class
