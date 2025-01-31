package gov.cdc.nedss.entity.entitygroup.ejb.dao;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.entitygroup.vo.*;
import gov.cdc.nedss.entity.entitygroup.dt.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.systemservice.uidgenerator.*;
import gov.cdc.nedss.entity.sqlscript.*;
/**
* Name:	EntityGroupDAOImpl.java
* Description:	This class encapsulates all the JDBC calls made by the EntityGroupEJB
*               for a EntityGroup object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of EntityGroupEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation

* @version	1.0
*/
public class EntityGroupDAOImpl extends BMPBase
{
  /**
   *Description: final String SELECT_ENTITYGROUP_UID : The Sql satement that finds if a given UID exists.
   */
  public static final String SELECT_ENTITYGROUP_UID =
          " SELECT "
         +" entity_group_uid FROM "
         + DataTables.ENTITY_GROUP_TABLE
         + " WHERE entity_group_uid = ?";
  /**
   *Description:  final String INSERT_ENTITYGROUP = : The Sql statement that is used to insert a EntityGroupDT.
   */
   public static final String INSERT_ENTITYGROUP =
         " INSERT INTO Entity_group (entity_group_uid, add_reason_cd, add_time, add_user_id, cd, "+
          "cd_desc_txt, description, duration_amt, duration_unit_cd, from_time, group_cnt, last_chg_reason_cd, "+
          "last_chg_time, last_chg_user_id, local_id, nm, record_status_cd, record_status_time, status_cd, "+
          "status_time, to_time, user_affiliation_txt, version_ctrl_nbr) VALUES "+
          "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  /**
   *Description:  final String UPDATE_ENTITYGROUP = : The Sql statement that is used to update a EntityGroupDT.
   */
  public static final String UPDATE_ENTITYGROUP =
          "UPDATE Entity_group SET add_reason_cd = ?, add_time = ?, add_user_id = ?, "+
          "cd = ?, cd_desc_txt = ?, description = ?, duration_amt = ?, duration_unit_cd = ?, "+
          "from_time = ?, group_cnt = ?, last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, "+
          "local_id = ?, nm = ?, record_status_cd = ?, record_status_time = ?, status_cd = ?, status_time = ?, "+
          "to_time = ?, user_affiliation_txt = ?, version_ctrl_nbr = ? WHERE entity_group_uid = ? AND version_ctrl_nbr = ?";

  /**
   *Description: final String SELECT_ENTITYGROUP = : The Sql statement that is used to return a EntityGroupDT.
   */
  public static final String SELECT_ENTITYGROUP =
          " SELECT "
          + " entity_group_uid \"entityGroupUid\", "
          + " add_reason_cd \"addReasonCd\", "
          + " add_time \"addTime\", "
          + " add_user_id \"addUserId\", "
          + " cd \"cd\", "
          + " cd_desc_txt \"cdDescTxt\", "
          + " description \"description\", "
          + " duration_amt \"durationAmt\", "
          + " duration_unit_cd \"durationUnitCd\", "
          + " from_time \"fromTime\", "
          + " group_cnt \"groupCnt\", "
          + " last_chg_reason_cd \"lastChgReasonCd\", "
          + " last_chg_time \"lastChgTime\", "
          + " last_chg_user_id \"lastChgUserId\", "
          + " local_id \"localId\", "
          + " nm \"nm\", "
        //  + " org_access_permis \"orgAccessPermis\", "
       //   + " prog_area_access_permis \"progAreaAccessPermis\", "
          + " record_status_cd \"recordStatusCd\", "
          + " record_status_time \"recordStatusTime\", "
          + " status_cd \"statusCd\", "
          + " status_time \"statusTime\", "
          + " to_time \"toTime\", "
          + " user_affiliation_txt \"userAffiliationTxt\" "
          + " version_ctrl_nbr \"versionCtrlNbr\" "
          + " FROM "
          + DataTables.ENTITY_GROUP_TABLE
          + " WHERE entity_group_uid = ?";

  /**
   *Description:  final String DELETE_ENTITYGROUP = : The Sql statement that is used to delete a EntityGroupDT.
   */
  public static final String DELETE_ENTITYGROUP =
          " DELETE FROM "
          + DataTables.ENTITY_GROUP_TABLE
          + " WHERE (entity_group_uid = ?)";


  private long entitygroupUID = -1;

  /**
   *Description: String LogUtils = : used for logging purposes.
   */
  static final LogUtils logger = new LogUtils(EntityGroupDAOImpl.class.getName());

  /**
   * Description: Constructor used to create a EntityGroupDAOImpl object.
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  public EntityGroupDAOImpl()
		throws NEDSSDAOSysException, NEDSSSystemException
  {

  }

  /**
   * Description: This method is used to create a EntityGroupDT object. The object is first converted
   * to EntityGroupDT object and then it uses private method(insertItem) to insert
   * the EntityGroupDT object in Database.
   * @param obj : Object that needs to be converted back to EntityGroupDT
   * @return : long value that represents the EntityGroupDT thus created.
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  public long create(Object obj ) throws NEDSSDAOSysException, NEDSSSystemException
  {
        EntityGroupDT entityGroupDT = (EntityGroupDT) obj;

     entitygroupUID = insertItem(entityGroupDT);
     entityGroupDT.setItNew(false);
     entityGroupDT.setItDirty(false);
     entityGroupDT.setItDelete(false);
     return entitygroupUID;
  }

  /**
   * Description: This method is used to update a EntityGroupDT object. The object is first converted
   * to EntityGroupDT object and then it uses private method(updateItem) to update
   * the EntityGroupDT object in Database.
   * @param obj : Object that needs to be converted back to EntityGroupDT
   * @throws NEDSSDAOSysException
   * @throws NEDSSConcurrentDataException
   * @throws NEDSSSystemException
   */
  public void store(Object obj)
		throws NEDSSDAOSysException, NEDSSConcurrentDataException, NEDSSSystemException
  {
      updateItem( (EntityGroupDT) obj);
  }

  /**
   * Description: This method is used to remove a EntityGroupDT object for a given entitygroupUID.
   * This method uses private method(removeItem) to removethe EntityGroupDT object in Database.
   * @param entitygroupUID : The uid value representing EntityGroupDT object.
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  public void remove(long entitygroupUID)
          throws NEDSSDAOSysException, NEDSSSystemException
  {
      removeItem(entitygroupUID);
  }

  /**
   * Description: This method loads a EntityGroupDT object represented by a entitygroupUID.
   * @param entitygroupUID : The long value representing entityGroupDT).
   * @return : object representing EntityGroupDT object.
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  public Object loadObject(long entitygroupUID) throws NEDSSDAOSysException,
		 NEDSSSystemException
    {
        EntityGroupDT entityGroupDT = selectItem(entitygroupUID);
        entityGroupDT.setItNew(false);
        entityGroupDT.setItDirty(false);
        entityGroupDT.setItDelete(false);
        return entityGroupDT;
    }

    /**
     * Description: findByPrimaryKey method takes entitygroupUID parameter and finds if a given
     * value exists in the database. Returns the Long value of the entitygroupUID(in
     * case it exists) or null(if the value doesnot exists in the database).
     * This method internally uses another method(itemExists).
     * @param entitygroupUID : the long value representing EntityGroupDT
     * @return : Long value if the corresponding value for entityGroupDT exists
     * else returns null.
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public Long findByPrimaryKey(long entitygroupUID)
    	throws NEDSSDAOSysException, NEDSSSystemException
    {
        if (itemExists(entitygroupUID))
            return (new Long(entitygroupUID));
        else
            logger.error("No entitygroup found for this primary key :" + entitygroupUID);
            return null;
    }

    /**
     * Description: Protected method that returns a boolean value true if that entitygroupUID
     * exists in the database, else return false.
     * @param entitygroupUID : long value representing EntityGroupDT
     * @return : boolean value if the corresponding value for entityGroupDT exists
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    protected boolean itemExists (long entitygroupUID) throws NEDSSDAOSysException,
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
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining dbConnection " +
                "for checking entitygroup existence: ", nsex);
            throw new NEDSSSystemException( nsex.toString() );
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_ENTITYGROUP_UID);
            preparedStmt.setLong(1, entitygroupUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                entitygroupUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an"
                            + " existing entitygroup's uid in entitygroup table-&gt; " + entitygroupUID, sex);
            throw new NEDSSDAOSysException(sex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while checking for an"
                            + " existing entitygroup's uid in entitygroup table-&gt; " +
                            entitygroupUID , ex);
            throw new NEDSSDAOSysException( ex.toString());
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
     * Description: Private method used to create a new EntityGroupDT. This method returns a
     * long value representing EntityGroupDT created in the process.
     * @param entityGroupDT : EntityGroupDT object.
     * @return : long value representing EntityGroupDT thus created.
     * @throws NEDSSSystemException
     */
    private long insertItem(EntityGroupDT entityGroupDT)
                throws NEDSSSystemException
    {
        /**
         * Starts inserting a new entitygroup
         */
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        String localUID = null;
        UidGeneratorHelper uidGen = null;
        int resultCount = 0;

        try
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining db connection " +
                    "while inserting into Entity table", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                uidGen = new UidGeneratorHelper();
                entitygroupUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
                preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY);

                int i = 1;
                preparedStmt.setLong(i++, entitygroupUID);
                preparedStmt.setString(i++,  NEDSSConstants.ENTITYGROUP);
                resultCount = preparedStmt.executeUpdate();
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while generating " +
                        "uid for ENTITYGROUP_TABLE: \n" , sex);
                throw new NEDSSSystemException( sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into ENTITY_TABLE, entitygroupUID = " + entitygroupUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }

            try
            {
                if (resultCount != 1)
                {
                    logger.error("Error while inserting " +
                            "uid into ENTITY_TABLE for a new entitygroup, resultCount = " +
                            resultCount);
                }

                 // insert into ENTITYGROUP_TABLE

                preparedStmt = dbConnection.prepareStatement(INSERT_ENTITYGROUP);
                uidGen = new UidGeneratorHelper();
                localUID = uidGen.getLocalID(UidClassCodes.GROUP_CLASS_CODE);

                    int i = 1;
                // Set auto generated PK field
                 preparedStmt.setLong(i++,entitygroupUID);

                // Set all non generated fields
                 preparedStmt.setString(i++,entityGroupDT.getAddReasonCd());
                if (entityGroupDT.getAddTime() == null)
                    preparedStmt.setNull(i++, Types.DATE);
                else
                    preparedStmt.setTimestamp(i++,entityGroupDT.getAddTime());
                if (entityGroupDT.getAddUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++,entityGroupDT.getAddUserId().longValue());
                preparedStmt.setString(i++,entityGroupDT.getCd());
                preparedStmt.setString(i++,entityGroupDT.getCdDescTxt());
                preparedStmt.setString(i++,entityGroupDT.getDescription());
                preparedStmt.setString(i++,entityGroupDT.getDurationAmt());
                preparedStmt.setString(i++,entityGroupDT.getDurationUnitCd());
                if (entityGroupDT.getFromTime() == null)
                    preparedStmt.setNull(i++, Types.DATE);
                else
                    preparedStmt.setTimestamp(i++,entityGroupDT.getFromTime());
                if (entityGroupDT.getGroupCnt() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setInt(i++,entityGroupDT.getGroupCnt().intValue());
                preparedStmt.setString(i++,entityGroupDT.getLastChgReasonCd());
                if (entityGroupDT.getLastChgTime() == null)
                    preparedStmt.setNull(i++, Types.DATE);
                else
                    preparedStmt.setTimestamp(i++,entityGroupDT.getLastChgTime());
                if (entityGroupDT.getLastChgUserId() == null)
                    preparedStmt.setNull(i++, Types.INTEGER);
                else
                    preparedStmt.setLong(i++,entityGroupDT.getLastChgUserId().longValue());
                preparedStmt.setString(i++,entityGroupDT.getLocalId());
                preparedStmt.setString(i++,entityGroupDT.getNm());
                preparedStmt.setString(i++,entityGroupDT.getRecordStatusCd());
                if (entityGroupDT.getRecordStatusTime() == null)
                    preparedStmt.setNull(i++, Types.DATE);
                else
                    preparedStmt.setTimestamp(i++,entityGroupDT.getRecordStatusTime());
                  preparedStmt.setString(i++,entityGroupDT.getStatusCd());
                if (entityGroupDT.getStatusTime() == null)
                    logger.debug("The status time cannot be null, the datatbse doesn't allow this!");
                else
                    preparedStmt.setTimestamp(i++,entityGroupDT.getStatusTime());
                if (entityGroupDT.getToTime() == null)
                    preparedStmt.setNull(i++, Types.DATE);
                else
                    preparedStmt.setTimestamp(i++,entityGroupDT.getToTime());
                preparedStmt.setString(i++,entityGroupDT.getUserAffiliationTxt());
               /*if (entityGroupDT.getVersionCtrlNbr() == null)
                    logger.debug("The version control number cannot be null, the datatbse doesn't allow this!");
                else
                    preparedStmt.setInt(i++,entityGroupDT.getVersionCtrlNbr().intValue());
                    */
                preparedStmt.setInt(i++,entityGroupDT.getVersionCtrlNbr().intValue());

                resultCount = preparedStmt.executeUpdate();
    logger.debug("done insert entitygroup! entitygroupUID = " + entitygroupUID);

                return entitygroupUID;
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while inserting " +
                        "entitygroup into ENTITYGROUP_TABLE: \n", sex);
                throw new NEDSSSystemException(sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while inserting into ENTITYGROUP_TABLE, id = " + entitygroupUID, ex);
                throw new NEDSSSystemException(ex.toString());
            }
        }
        finally
        {
            //closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }//end of inserting entitygroup

    /**
     * Description: This is a private method used to update a EntityGroupDT object.
     * @param entityGroupDT : EntityGroupDT object thus updated.
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    private void updateItem(EntityGroupDT entityGroupDT) throws NEDSSSystemException, NEDSSConcurrentDataException
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
            logger.fatal("Error obtaining db connection " +
                "while updating entitygroup table", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            //Updates EntityGroupDT table
            if (entityGroupDT != null)
            {

                preparedStmt = dbConnection.prepareStatement(UPDATE_ENTITYGROUP);

		int i = 1;

		// first set non-PK on UPDATE statement
                preparedStmt.setString(i++,entityGroupDT.getAddReasonCd());
                if (entityGroupDT.getAddTime() == null)
	          preparedStmt.setNull(i++, Types.DATE);
	        else
	          preparedStmt.setTimestamp(i++,entityGroupDT.getAddTime());
                if (entityGroupDT.getAddUserId() == null)
	          preparedStmt.setNull(i++, Types.INTEGER);
	        else
	          preparedStmt.setLong(i++,entityGroupDT.getAddUserId().longValue());
                preparedStmt.setString(i++,entityGroupDT.getCd());
                preparedStmt.setString(i++,entityGroupDT.getCdDescTxt());
                preparedStmt.setString(i++,entityGroupDT.getDescription());
                preparedStmt.setString(i++,entityGroupDT.getDurationAmt());
                preparedStmt.setString(i++,entityGroupDT.getDurationUnitCd());
                if (entityGroupDT.getFromTime() == null)
	          preparedStmt.setNull(i++, Types.DATE);
	        else
	          preparedStmt.setTimestamp(i++,entityGroupDT.getFromTime());
                if (entityGroupDT.getGroupCnt() == null)
	          preparedStmt.setNull(i++, Types.INTEGER);
	        else
	          preparedStmt.setInt(i++,entityGroupDT.getGroupCnt().intValue());
                preparedStmt.setString(i++,entityGroupDT.getLastChgReasonCd());
                if (entityGroupDT.getLastChgTime() == null)
	          preparedStmt.setNull(i++, Types.DATE);
	        else
	          preparedStmt.setTimestamp(i++,entityGroupDT.getLastChgTime());
                if (entityGroupDT.getLastChgUserId() == null)
	          preparedStmt.setNull(i++, Types.INTEGER);
	        else
	          preparedStmt.setLong(i++,entityGroupDT.getLastChgUserId().longValue());
                preparedStmt.setString(i++,entityGroupDT.getLocalId());
                preparedStmt.setString(i++,entityGroupDT.getNm());
                preparedStmt.setString(i++,entityGroupDT.getRecordStatusCd());
                if (entityGroupDT.getRecordStatusTime() == null)
	          preparedStmt.setNull(i++, Types.DATE);
	        else
	          preparedStmt.setTimestamp(i++,entityGroupDT.getRecordStatusTime());
                  preparedStmt.setString(i++,entityGroupDT.getStatusCd());
                if (entityGroupDT.getStatusTime() == null)
	          preparedStmt.setNull(i++,Types.TIMESTAMP);
	        else
	          preparedStmt.setTimestamp(i++,entityGroupDT.getStatusTime());
                if (entityGroupDT.getToTime() == null)
	          preparedStmt.setNull(i++, Types.DATE);
	        else
	          preparedStmt.setTimestamp(i++,entityGroupDT.getToTime());
                preparedStmt.setString(i++,entityGroupDT.getUserAffiliationTxt());

            if (entityGroupDT.getVersionCtrlNbr() == null) {
	          logger.debug("The version control number cannot be null, the database doesn't allow this!");
            } else {
                // Increment the version number by 1 and update with the record. This will be helpful
                // to check for data concurrency issues during next update
                preparedStmt.setInt(i++, (entityGroupDT.getVersionCtrlNbr().intValue()));
            }

            preparedStmt.setLong(i++,entityGroupDT.getEntityGroupUid().longValue());
            // Checking the current version in the WHERE clause for data concurrency
            preparedStmt.setInt(i++, entityGroupDT.getVersionCtrlNbr().intValue()-1);


            resultCount = preparedStmt.executeUpdate();

            if ( resultCount != 1 )
                {
                    logger.error("Error: none or more than one entitygroup updated at a time, " + "resultCount = " + resultCount);
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: EntityGroupDAOImpl.UpdateItem:The data has been modified by other user, please verify!");
                }
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while updating " +
                    "entitygroup into ENTITYGROUP_TABLE: \n", sex);
            throw new NEDSSSystemException( sex.toString() );
        }
        finally
        {
           // closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }

    /**
     * Description: This is a private method that returns a EntityGroupDT object for a entitygroupUID.
     * @param entitygroupUID : long value representing entitygroupUID.
     * @return : EntityGroupDT object.
     * @throws NEDSSSystemException
     */
    private EntityGroupDT selectItem(long entitygroupUID)
    	throws NEDSSSystemException
    {
        EntityGroupDT entityGroupDT = new EntityGroupDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " + "for selectentity_group ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        /**
         * Selects entitygroup from entity_group table
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_ENTITYGROUP);
            preparedStmt.setLong(1, entitygroupUID);
            resultSet = preparedStmt.executeQuery();

            logger.debug("entityGroupDT object for: entitygroupUID = " + entitygroupUID);

            resultSetMetaData = resultSet.getMetaData();

            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, entityGroupDT.getClass(), pList);

            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
            {
                entityGroupDT = (EntityGroupDT)anIterator.next();
                entityGroupDT.setItNew(false);
                entityGroupDT.setItDirty(false);
                entityGroupDT.setItDelete(false);
            }

        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while selecting " +
                            "entityGroup vo; id = " + entitygroupUID, sex);
            throw new NEDSSSystemException( sex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "entityGroup vo; id = " + entitygroupUID, ex);
            throw new NEDSSSystemException( ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        logger.debug("returning entityGroup DT object");
        return entityGroupDT;
    }//end of selecting item

    /**
     * Description: This is a private method that removes a EntityGroupDT object for a given EntitygroupUID.
     * @param entitygroupUID : long value representing EntityGroupDT.
     * @throws NEDSSSystemException
     */
    private void removeItem(long entitygroupUID) throws NEDSSSystemException
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
                            "for deleting entitygroup ", nsex);
            throw new NEDSSSystemException( nsex.toString());
        }

        /**
         * Deletes entitygroups
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(DELETE_ENTITYGROUP);
            preparedStmt.setLong(1, entitygroupUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete entitygroup from ENTITYGROUP_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "entitygroup; id = " + entitygroupUID, sex);
            throw new NEDSSSystemException(sex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }

}
