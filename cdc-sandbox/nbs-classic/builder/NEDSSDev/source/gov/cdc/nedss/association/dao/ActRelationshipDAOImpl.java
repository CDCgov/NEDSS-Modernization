//Source file: C:\\development\\source\\gov\\cdc\\nedss\\dao\\ActRelationshipDAOImpl.java
package gov.cdc.nedss.association.dao;

import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.util.*;

import java.sql.*;

import java.util.*;


public class ActRelationshipDAOImpl
    extends BMPBase
{

    //For logging
    static final LogUtils logger = new LogUtils(ActRelationshipDAOImpl.class.getName());
    private static final String SET_PK = "where target_act_uid = ? and source_act_uid = ? and type_cd = ?";
    private static final String SET_UID = "where target_act_uid = ?";
    private static final String SET_TYPE = "where target_act_uid = ? and type_cd = ?";
    private static final String SET_SOURCE = "where source_act_uid = ?";
    private static final String SET_SOURCE_AND_TYPE = "where source_act_uid = ? and type_cd = ?";
    private static final String SELECT = "SELECT " +
                                         "target_act_uid \"TargetActUid\", " +
                                         "source_act_uid \"SourceActUid\", " +
                                         "add_reason_cd \"AddReasonCd\", " +
                                         "add_time \"AddTime\", " +
                                         "add_user_id \"AddUserId\", " +
                                         "duration_amt \"DurationAmt\", " +
                                         "duration_unit_cd \"DurationUnitCd\", " +
                                         "from_time \"FromTime\", " +
                                         "last_chg_reason_cd \"LastChgReasonCd\", " +
                                         "last_chg_time \"LastChgTime\", " +
                                         "last_chg_user_id \"LastChgUserId\", " +
                                         "record_status_cd \"RecordStatusCd\", " +
                                         "record_status_time \"RecordStatusTime\", " +
                                         "sequence_nbr \"SequenceNbr\", " +
                                         "status_cd \"StatusCd\", " +
                                         "status_time \"StatusTime\", " +
                                         "to_time \"ToTime\", " +
                                         "type_cd \"TypeCd\", " +
                                         "target_class_cd \"TargetClassCd\", " +
                                         "source_class_cd \"SourceClassCd\", " +
                                         "type_desc_txt \"TypeDescTxt\", " +
                                         "user_affiliation_txt \"UserAffiliationTxt\" " +
                                         "from Act_relationship WITH (NOLOCK) ";
    private static final String SELECT_BY_PK = SELECT + SET_PK;
    private static final String SELECT_BY_UID = SELECT + SET_UID;
    private static final String SELECT_BY_TYPE = SELECT + SET_TYPE;
    private static final String SELECT_BY_SOURCE = SELECT + SET_SOURCE;
    private static final String SELECT_BY_SOURCE_AND_TYPE =
            SELECT + SET_SOURCE_AND_TYPE;
    private static final String DELETE_BY_PK = "DELETE from Act_relationship " +
                                               SET_PK;
    private static final String DELETE_BY_UID = "DELETE from Act_relationship " +
                                                SET_UID;
    private static final String UPDATE = "UPDATE Act_relationship SET " +
                                         "target_act_uid = ?, " +
                                         "source_act_uid = ?, " +
                                         "add_reason_cd = ?, " +
                                         "add_time = ?, " +
                                         "add_user_id = ?, " +
                                         "duration_amt = ?, " +
                                         "duration_unit_cd = ?, " +
                                         "from_time = ?, " +
                                         "last_chg_reason_cd = ?, " +
                                         "last_chg_time = ?, " +
                                         "last_chg_user_id = ?, " +
                                         "record_status_cd = ?, " +
                                         "record_status_time = ?, " +
                                         "sequence_nbr = ?, " +
                                         "status_cd = ?, " +
                                         "to_time = ?, " + "type_cd = ?, " +
                                         "target_class_cd = ?, " +
                                         "source_class_cd = ?, " +
                                         "type_desc_txt = ?, " +
                                         "user_affiliation_txt = ? " +
                                         SET_PK;
    private static final String INSERT = "INSERT INTO Act_relationship ( " +
                                         "target_act_uid, " +
                                         "source_act_uid, " +
                                         "add_reason_cd, " + "add_time, " +
                                         "add_user_id, " + "duration_amt, " +
                                         "duration_unit_cd, " +
                                         "from_time, " +
                                         "last_chg_reason_cd, " +
                                         "last_chg_time, " +
                                         "last_chg_user_id, " +
                                         "record_status_cd, " +
                                         "record_status_time, " +
                                         "sequence_nbr, " + "status_cd, " +
                                         "status_time, " + "to_time, " +
                                         "type_cd, " + "target_class_cd, " +
                                         "source_class_cd, " +
                                         "type_desc_txt, " +
                                         "user_affiliation_txt " +
                                         " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";


    /**
    * The default constructor
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434D87035A
    */
    public ActRelationshipDAOImpl()
                           throws NEDSSDAOSysException, NEDSSSystemException
    {
    }

    /**
    * A method used to insert an act relationship
    * @param uid the uid
    * @param obj the act relationship object to be inserted
    * @return long a uid
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434D8800E5
    */
    public long create(long uid, Object obj)
                throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{

	        ActRelationshipDT dt = (ActRelationshipDT)obj;
	
	        if (dt == null)
	        {
	            logger.error(
	                    "ActRelationDAOImpl, create(): try to create null ActRelationshipDT object.");
	            throw new NEDSSDAOSysException("Error: try to create null ActRelationshipDT object.");
	        }
	
	        insertActRelationship((ActRelationshipDT)obj);
    	}catch(NEDSSDAOSysException dex){
    		logger.fatal("Exception  = "+dex.getMessage(), dex);
    		throw new NEDSSSystemException(dex.toString());
    	}catch(NEDSSSystemException sex){
    		logger.fatal("Exception  = "+sex.getMessage(), sex);
    		throw new NEDSSSystemException(sex.toString());
    	}
        return uid;
    }

    /**
    * A mehtod used to update an act relationship
    * @param obj the object to be updated
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434D880320
    */
    public void store(Object obj)
               throws NEDSSDAOSysException, NEDSSSystemException
    {

    	try{
	        ActRelationshipDT dt = (ActRelationshipDT)obj;
	
	        if (dt == null)
	        {
	            logger.error(
	                    "ActRelationDAOImpl, store(): try to create null ActRelationshipDT object.");
	            throw new NEDSSDAOSysException("Error: try to store null ActRelationshipDT object.");
	        }
	
	        if (dt.isItNew())
	        {
	            logger.debug("Inserting act relationship");
	            insertActRelationship(dt);
	        }
	        else if (dt.isItDelete())
	        {
	            logger.debug("Deleting act relationship");
	            remove(dt);
	        }
	        else if (dt.isItDirty())
	        {
	            logger.debug("updating act relationship");
	
	            if (dt.getTargetActUid() != null &&
	                dt.getSourceActUid() != null && dt.getTypeCd() != null)
	                updateActRelationship(dt);
	            else
	                logger.error(
	                        "None of the primary key values can be null, TargetUID: " +
	                        dt.getTargetActUid() + " TypeCd : " +
	                        dt.getTypeCd() + " SourceUid :" +
	                        dt.getSourceActUid() + " please check!");
	        }
    	}catch(NEDSSDAOSysException dex){
    		logger.fatal("Exception  = "+dex.getMessage(), dex);
    		throw new NEDSSSystemException(dex.toString());
    	}catch(NEDSSSystemException sex){
    		logger.fatal("Exception  = "+sex.getMessage(), sex);
    		throw new NEDSSSystemException(sex.toString());
    	}
    }

    /**
    * A mehtod used to retrieve act relationships
    * @param uid the target act uid
    * @return java.util.Collection<Object>  the retrieved act relationships
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434D8900C8
    */
    public Collection<Object> load(long targetActUid)
                    throws NEDSSDAOSysException, NEDSSSystemException
    {

        return selectActRelationship(targetActUid, SELECT_BY_UID);
    }

    /**
     * A mehtod used to retrieve act relationships
     * @param targetActUid the target act uid
     * @param sourceActUid the source act uid
     * @return   the retrieved act relationships
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public Collection<Object> load(long targetActUid, long sourceActUid)
                    throws NEDSSDAOSysException, NEDSSSystemException
    {

        return selectActRelationship(targetActUid, sourceActUid);
    }

    /**
     * A mehtod used to retrieve act relationships
     * @param targetActUid the target act uid
     * @param typeCd the act relationship type code
     * @return a collection of act relationships
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public Collection<Object> load(long targetActUid, String typeCd)
                    throws NEDSSDAOSysException, NEDSSSystemException
    {

        return selectActRelationshipByType(targetActUid, typeCd,
                                           SELECT_BY_TYPE);
    }

    /**
     * A mehtod used to retrieve an act relationship
     * @param targetActUid the target act uid
     * @param sourceActUid the source act uid
     * @param typeCd the act relationship type code
     * @return an ActRelationshipDT object
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public ActRelationshipDT load(long targetActUid, long sourceActUid,
                                  String typeCd)
                           throws NEDSSDAOSysException, NEDSSSystemException
    {

        return selectActRelationshipByPK(targetActUid, sourceActUid, typeCd,
                                         SELECT_BY_PK);
    }

    /**
     * A mehtod used to retrieve act relationships
     * @param sourceActUid the source act uid
     * @return a collection of act relationships
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public Collection<Object> loadSource(long sourceActUid)
                          throws NEDSSDAOSysException, NEDSSSystemException
    {

        return selectActRelationship(sourceActUid, SELECT_BY_SOURCE);
    }

    /**
     * A mehtod used to retrieve act relationships
     * @param sourceActUid the source act uid
     * @param typeCd the act relationship type code
     * @return a collection of act relationships
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public Collection<Object> loadSource(long sourceActUid, String typeCd)
                          throws NEDSSDAOSysException, NEDSSSystemException
    {

        return selectActRelationshipByType(sourceActUid, typeCd,
                                           SELECT_BY_SOURCE_AND_TYPE);
    }

    /**
    * A mehtod used to delete a act relationship
    * @param uid the act relationship uid
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434D8902DC
    */
    public void remove(ActRelationshipDT dt)
                throws NEDSSDAOSysException, NEDSSSystemException
    {
        removeActRelationship(dt);
    }

    /**
    * @param dt
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434D8A0065
    */
    private void insertActRelationship(ActRelationshipDT dt)
                                throws NEDSSDAOSysException,
                                       NEDSSSystemException
    {

        int resultCount = 0;

        //PreparedStatement preparedStmt = getPreparedStatement(INSERT);
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;

        try
        {

            //if ( dbConnection == null || dbConnection.isClosed() )
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(INSERT);

            //setPreparedStatement(preparedStmt, dt);
            int i = 1;
            preparedStmt.setLong(i++, dt.getTargetActUid().longValue());
            preparedStmt.setLong(i++, dt.getSourceActUid().longValue());
            preparedStmt.setString(i++, dt.getAddReasonCd());

            if (dt.getAddTime() == null)
                preparedStmt.setTimestamp(i++,
                                          new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getAddTime());

            if (dt.getAddUserId() == null)
                preparedStmt.setNull(i++, Types.BIGINT);
            else
                preparedStmt.setLong(i++, dt.getAddUserId().longValue());

            preparedStmt.setString(i++, dt.getDurationAmt());
            preparedStmt.setString(i++, dt.getDurationUnitCd());

            if (dt.getFromTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, dt.getFromTime());

            preparedStmt.setString(i++, dt.getLastChgReasonCd());

            if (dt.getLastChgTime() == null)
                preparedStmt.setTimestamp(i++,
                                          new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getLastChgTime());

            if (dt.getLastChgUserId() == null)
                preparedStmt.setNull(i++, Types.BIGINT);
            else
                preparedStmt.setLong(i++, dt.getLastChgUserId().longValue());

            preparedStmt.setString(i++, dt.getRecordStatusCd());

            if (dt.getRecordStatusTime() == null)
                preparedStmt.setTimestamp(i++,
                                          new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getRecordStatusTime());

            if (dt.getSequenceNbr() == null)
                preparedStmt.setNull(i++, Types.SMALLINT);
            else
                preparedStmt.setInt(i++, dt.getSequenceNbr().intValue());

            preparedStmt.setString(i++, dt.getStatusCd());

            if (dt.getStatusTime() == null)
                preparedStmt.setTimestamp(i++,
                                          new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getStatusTime());

            if (dt.getToTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, dt.getToTime());

            if (dt.getTypeCd() == null)
                preparedStmt.setString(i++, "PK"); //This is added temp for changing to primary key not accepting null.
            else
                preparedStmt.setString(i++, dt.getTypeCd());

            preparedStmt.setString(i++, dt.getTargetClassCd());
            preparedStmt.setString(i++, dt.getSourceClassCd());
            preparedStmt.setString(i++, dt.getTypeDescTxt());
            preparedStmt.setString(i++, dt.getUserAffiliationTxt());
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error(
                        "ActRelationshipDAOImpl, insertActRelationship(): none or more than one role inserted at a time");
                throw new NEDSSDAOSysException("Error: none or more than one role inserted at a time, resultCount = " +
                                               resultCount);
            }
            else
            {
                dt.setItNew(false);
                dt.setItDirty(false);
            }
        }
        catch (SQLException se)
        {
            se.printStackTrace();
            logger.fatal(
                    "ActRelationshipDAOImpl, insertActRelationship(): SQLException while inserting.",
                    se);
            logger.fatal("target source typeCd: \n ",
                         dt.getTargetActUid() + "  " + dt.getSourceActUid() +
                         "  " + dt.getTypeCd());
            throw new NEDSSDAOSysException("Table Name : Act_relationship  "+se.getMessage(), se);
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    /**
    * @param dt
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434D8A02BE
    */
    private void updateActRelationship(ActRelationshipDT dt)
                                throws NEDSSDAOSysException,
                                       NEDSSSystemException
    {

        int resultCount = 0;
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;

        try
        {

            //if ( dbConnection == null || dbConnection.isClosed() )
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(UPDATE);

            // preparedStmt = getPreparedStatement(UPDATE);
            //  int i = setPreparedStatement(preparedStmt, dt);
            int i = 1;
            preparedStmt.setLong(i++, dt.getTargetActUid().longValue());
            preparedStmt.setLong(i++, dt.getSourceActUid().longValue());
            preparedStmt.setString(i++, dt.getAddReasonCd());

            if (dt.getAddTime() == null)
                preparedStmt.setTimestamp(i++,
                                          new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getAddTime());

            if (dt.getAddUserId() == null)
                preparedStmt.setNull(i++, Types.BIGINT);
            else
                preparedStmt.setLong(i++, dt.getAddUserId().longValue());

            preparedStmt.setString(i++, dt.getDurationAmt());
            preparedStmt.setString(i++, dt.getDurationUnitCd());

            if (dt.getFromTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, dt.getFromTime());

            preparedStmt.setString(i++, dt.getLastChgReasonCd());

            if (dt.getLastChgTime() == null)
                preparedStmt.setTimestamp(i++,
                                          new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getLastChgTime());

            if (dt.getLastChgUserId() == null)
                preparedStmt.setNull(i++, Types.BIGINT);
            else
                preparedStmt.setLong(i++, dt.getLastChgUserId().longValue());

            preparedStmt.setString(i++, dt.getRecordStatusCd());

            if (dt.getRecordStatusTime() == null)
                preparedStmt.setTimestamp(i++,
                                          new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getRecordStatusTime());

            if (dt.getSequenceNbr() == null)
                preparedStmt.setNull(i++, Types.SMALLINT);
            else
                preparedStmt.setInt(i++, dt.getSequenceNbr().intValue());

            preparedStmt.setString(i++, dt.getStatusCd());

            //preparedStmt.setTimestamp(i++, dt.getStatusTime());
            if (dt.getToTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, dt.getToTime());

            preparedStmt.setString(i++, dt.getTypeCd());
            preparedStmt.setString(i++, dt.getTargetClassCd());
            preparedStmt.setString(i++, dt.getSourceClassCd());
            preparedStmt.setString(i++, dt.getTypeDescTxt());
            preparedStmt.setString(i++, dt.getUserAffiliationTxt());

            //primary keys values for selection
            preparedStmt.setLong(i++, dt.getTargetActUid().longValue());
            preparedStmt.setLong(i++, dt.getSourceActUid().longValue());
            preparedStmt.setString(i++, dt.getTypeCd());
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error(
                        "ActRelationshipDAOImpl, updateActRelationship(): none or more than one entity updated at a time, resultCount = " +
                        resultCount);
                throw new NEDSSDAOSysException("Error: none or more than one entity updated at a time, resultCount = " +
                                               resultCount);
            }
            else
            {
                dt.setItNew(false);
                dt.setItDirty(false);
            }
        }
        catch (SQLException se)
        {
            logger.fatal(
                    "ActRelationshipDAOImpl, updateActRelationship(): SQLException while updating", se);
            throw new NEDSSDAOSysException("Table Name : Act_relationship  "+se.getMessage(), se);
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    /**
    * @param uid sqlStatement
    * @return java.util.Collection
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434D8B00C1
    */
    private Collection<Object> selectActRelationship(long uid, String sqlStatement)
                                      throws NEDSSDAOSysException,
                                             NEDSSSystemException
    {

        // PreparedStatement preparedStmt = getPreparedStatement(sqlStatement);
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ActRelationshipDT dt = new ActRelationshipDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;

        try
        {

            //if ( dbConnection == null || dbConnection.isClosed() )
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(sqlStatement);
            preparedStmt.setLong(1, uid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();

            ArrayList<Object>  retval = new ArrayList<Object> ();
            ArrayList<Object>  resetList = new ArrayList<Object> ();
            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                               resultSetMetaData,
                                                               dt.getClass(),
                                                               retval);

            for (Iterator<Object> anIterator = retval.iterator();
                 anIterator.hasNext();)
            {

                ActRelationshipDT newdt = (ActRelationshipDT)anIterator.next();
                newdt.setItNew(false);
                newdt.setItDirty(false);
                resetList.add(newdt);
            }

            return resetList;
        }
        catch (SQLException se)
        {
            logger.fatal(
                    "ActRelationshipDAOImpl, selectActRelationship(): SQLException while selecting."+se.getMessage(),se);
            throw new NEDSSDAOSysException("Error: SQLException while selecting \n" +
                                           se.getMessage());
        }
        catch (ResultSetUtilsException rsuex)
        {
            logger.error(
                    "ActRelationshipDAOImpl, selectActRelationship():Error in result set handling while populate ActRelationshipDTs."+rsuex.getMessage(),rsuex);
            throw new NEDSSDAOSysException("Error in result set handling while populate ActRelationshipDTs.");
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    private Collection<Object> selectActRelationship(long targetActUid,
                                             long sourceActUid)
                                      throws NEDSSDAOSysException,
                                             NEDSSSystemException
    {

        // PreparedStatement preparedStmt = getPreparedStatement(SELECT_BY_PK);
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ActRelationshipDT dt = new ActRelationshipDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;

        try
        {

            //if ( dbConnection == null || dbConnection.isClosed() )
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_BY_PK);
            preparedStmt.setLong(1, targetActUid);
            preparedStmt.setLong(2, sourceActUid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();

            ArrayList<Object>  retval = new ArrayList<Object> ();
            ArrayList<Object>  resetList = new ArrayList<Object> ();
            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                               resultSetMetaData,
                                                               dt.getClass(),
                                                               retval);

            for (Iterator<Object> anIterator = retval.iterator();
                 anIterator.hasNext();)
            {

                ActRelationshipDT newdt = (ActRelationshipDT)anIterator.next();
                newdt.setItNew(false);
                newdt.setItDirty(false);
                resetList.add(newdt);
            }

            return resetList;
        }
        catch (SQLException se)
        {
            logger.fatal(
                    "ActRelationshipDAOImpl, selectActRelationship(): SQLException while selecting."+se.getMessage(),se);
            throw new NEDSSDAOSysException("Error: SQLException while selecting \n" +
                                           se.getMessage());
        }
        catch (ResultSetUtilsException rsuex)
        {
            logger.error(
                    "ActRelationshipDAOImpl, selectActRelationship():Error in result set handling while populate ActRelationshipDTs."+rsuex.getMessage(),rsuex);
            throw new NEDSSDAOSysException("Error in result set handling while populate ActRelationshipDTs.");
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    private Collection<Object> selectActRelationshipByType(long actUid, String typeCd,
                                                   String sqlStat)
                                            throws NEDSSDAOSysException,
                                                   NEDSSSystemException
    {

        // PreparedStatement preparedStmt = getPreparedStatement(sqlStat);
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ActRelationshipDT dt = new ActRelationshipDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;

        try
        {

            //if ( dbConnection == null || dbConnection.isClosed() )
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(sqlStat);
            preparedStmt.setLong(1, actUid);
            preparedStmt.setString(2, typeCd);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();

            ArrayList<Object>  retval = new ArrayList<Object> ();
            ArrayList<Object>  resetList = new ArrayList<Object> ();
            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                               resultSetMetaData,
                                                               dt.getClass(),
                                                               retval);

            for (Iterator<Object> anIterator = retval.iterator();
                 anIterator.hasNext();)
            {

                ActRelationshipDT newdt = (ActRelationshipDT)anIterator.next();
                newdt.setItNew(false);
                newdt.setItDirty(false);
                resetList.add(newdt);
            }

            return resetList;
        }
        catch (SQLException se)
        {
            logger.fatal(
                    "ActRelationshipDAOImpl,selectActRelationshipByType(): SQLException while selecting"+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while selecting \n" +
                                           se.getMessage());
        }
        catch (ResultSetUtilsException rsuex)
        {
            logger.error(
                    "ActRelationshipDAOImpl,selectActRelationshipByType(): Error in result set handling while populate ActRelationshipDTs."+rsuex.getMessage(),rsuex);
            throw new NEDSSDAOSysException("Error in result set handling while populate ActRelationshipDTs.");
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    private ActRelationshipDT selectActRelationshipByPK(long targetActUid,
                                                        long sourceActUid,
                                                        String typeCd,
                                                        String sqlStat)
                                                 throws NEDSSDAOSysException,
                                                        NEDSSSystemException
    {

        // PreparedStatement preparedStmt = getPreparedStatement(sqlStat);
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ActRelationshipDT returnDT = null;
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;

        try
        {

            //if ( dbConnection == null || dbConnection.isClosed() )
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(sqlStat);
            preparedStmt.setLong(1, targetActUid);
            preparedStmt.setLong(2, sourceActUid);
            preparedStmt.setString(3, typeCd);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();

            ActRelationshipDT dt = new ActRelationshipDT();
            dt = (ActRelationshipDT)resultSetUtils.mapRsToBean(resultSet,
                                                               resultSetMetaData,
                                                               dt.getClass());
            logger.error(
                    "ActRelationship dt targetUID is: " +
                    dt.getTargetActUid() + " sourceUid = " +
                    dt.getSourceActUid() + " and TypeCd = " +
                    dt.getTypeCd());

            if (dt.getTargetActUid() != null &&
                dt.getSourceActUid() != null && dt.getTypeCd() != null)
            {
                returnDT = dt;
            }
        }
        catch (SQLException se)
        {
            logger.fatal(
                    "ActRelationshipDAOImpl,selectActRelationshipByPK(): SQLException while selecting"+se.getMessage(),se);
            throw new NEDSSDAOSysException("Error: SQLException while selecting \n" +
                                           se.getMessage());
        }
        catch (ResultSetUtilsException rsuex)
        {
            logger.error(
                    "ActRelationshipDAOImpl,selectActRelationshipByPK(): Error in result set handling while populate ActRelationshipDT."+rsuex.getMessage(),rsuex);
            throw new NEDSSDAOSysException("Error in result set handling while populate ActRelationshipDT.");
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

        return returnDT;
    }

    /**
    * @param uid
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    * @roseuid 3C434D8B0283
    */
    private void removeActRelationship(ActRelationshipDT dt)
                                throws NEDSSDAOSysException,
                                       NEDSSSystemException
    {

        // PreparedStatement preparedStmt = getPreparedStatement(DELETE_BY_UID);
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        logger.debug(
                "remove act relationship for: " + dt.getTargetActUid() +
                ": " + dt.getSourceActUid() + " : " + dt.getTypeCd());

        try
        {

            //if ( dbConnection == null || dbConnection.isClosed() )
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(DELETE_BY_PK);
            preparedStmt.setLong(1, dt.getTargetActUid().longValue());
            preparedStmt.setLong(2, dt.getSourceActUid().longValue());
            preparedStmt.setString(3, dt.getTypeCd());

            int resCount = preparedStmt.executeUpdate();
            logger.debug("Deleted " + resCount + " numbers of records");
        }
        catch (SQLException se)
        {
            logger.fatal(
                    "ActRelationshipDAOImpl, removeActRelationship(): SQLException while deleting"+se.getMessage(),se);
            throw new NEDSSDAOSysException("Error: SQLException while deleting\n" +
                                           se.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }
    
    /**
     * @param uid
     * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
     * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
     * @roseuid 3C434D8B0283
     */
     public void removeAllActRelationships(long targetActUid)
                                 throws NEDSSDAOSysException,
                                        NEDSSSystemException
     {

         // PreparedStatement preparedStmt = getPreparedStatement(DELETE_BY_UID);
         Connection dbConnection = null;
         PreparedStatement preparedStmt = null;
         logger.debug(
                 "removing all act relationships for: " + targetActUid );

         try
         {

             //if ( dbConnection == null || dbConnection.isClosed() )
             dbConnection = getConnection();
             preparedStmt = dbConnection.prepareStatement(DELETE_BY_UID);
             preparedStmt.setLong(1, targetActUid);


             int resCount = preparedStmt.executeUpdate();
             logger.debug("Deleted " + resCount + " act relationship records");
         }
         catch (SQLException se)
         {
             logger.fatal(
                     "ActRelationshipDAOImpl, removeAllActRelationships(): SQLException while deleting"+se.getMessage(),se);
             throw new NEDSSDAOSysException("Error: SQLException while deleting\n" +
                                            se.getMessage());
         }
         finally
         {
             closeStatement(preparedStmt);
             releaseConnection(dbConnection);
         }
     }
    
     /**
      * @param uid
      * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
      * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
      * @roseuid 3C434D8B0283
      */
      public void removeAllActRelationshipsButNotifications(long targetActUid)
                                  throws NEDSSDAOSysException,
                                         NEDSSSystemException
      {

          // PreparedStatement preparedStmt = getPreparedStatement(DELETE_BY_UID);
          Connection dbConnection = null;
          PreparedStatement preparedStmt = null;
          logger.debug(
                  "removing all act relationships but notifications for: " + targetActUid );

          try
          {

              //if ( dbConnection == null || dbConnection.isClosed() )
              dbConnection = getConnection();
              preparedStmt = dbConnection.prepareStatement(DELETE_BY_UID + " and not source_class_cd = 'NOTF' ");
              preparedStmt.setLong(1, targetActUid);


              int resCount = preparedStmt.executeUpdate();
              logger.debug("Deleted " + resCount + " total act relationship records");
          }
          catch (SQLException se)
          {
              logger.fatal(
                      "ActRelationshipDAOImpl, removeAllActRelationshipsButNotifications(): SQLException while deleting all but NOTFs "+se.getMessage(),se);
              throw new NEDSSDAOSysException("Error: SQLException while deleting\n" +
                                             se.getMessage());
          }
          finally
          {
              closeStatement(preparedStmt);
              releaseConnection(dbConnection);
          }
      }
    
}