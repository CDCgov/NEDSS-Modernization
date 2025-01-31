/**
* Name:		EntityIdHistDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for old the
*               EntityIdHist value object inserting into EntityIdHist table.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Ning Peng
* @version	1.0
*/

package gov.cdc.nedss.entity.entityid.dao;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.entity.sqlscript.*;

public class EntityIdHistDAOImpl extends BMPBase
{
	static final LogUtils logger = new LogUtils(EntityIdHistDAOImpl.class.getName());

	private int nextSeqNum = -1;

	//for the time being, using default change_user_id for testing
	private long defaultChangeUserId = 0;

	//for testing
//	  NedssUtils nu = new NedssUtils();
//	  Connection dbConnection = nu.getTestConnection();

	public EntityIdHistDAOImpl()
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
	}
  /**
   * Description:
   *    EntityIdHistDAOImpl constructor
   * @param seqNum   Corresponding to entity_id_hist_seq in entity_id_hist table.
   *
   */

	public EntityIdHistDAOImpl(int seqNum)
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
	  this.nextSeqNum = seqNum;
	}

  /**
   * Description:
   *    This function takes a collection of DTs and stores them into history table
   * @param coll  A collection of DTs.
   * @return void
   *
   */

	public void store(Collection<Object> coll)
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
		Iterator<Object> iterator = null;
		if(coll != null)
		{
			iterator = coll.iterator();
			while(iterator.hasNext())
			{
				store(iterator.next());
			}
		}
	}

  /**
   * Description:
   *    This function takes a single DT and stores them into history table
   * @param obj   A DT object
   *
   * @return void
   *
   */
	public void store(Object obj)
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
		EntityIdDT dt = (EntityIdDT)obj;
		if(dt == null)
		   throw new NEDSSSystemException("Error: try to store null EntityIdDT object.");
		  insertEntityIdHist(dt);

	}

	/**
	 * Loads the record associated with hte person uid and version ctrl nbr
	 * @param personUID   The person uid to be search on.
	 * @param versionCtrlNbr  Represents the versionCtrlNbr of the record to load.
	 * @return Collection<Object>  A collection of entity id
	 */
	public Collection<Object> load(Long personUID, Integer versionCtrlNbr)
	throws NEDSSSystemException,NEDSSSystemException

	{
		Collection<Object> idColl = selectEntityIDsHist(personUID.longValue(), versionCtrlNbr.intValue());
		return idColl;
	}

	/**
	 * Performs a search for the indicated personUid and versionCtrlNbr
	 * @param personUid  The primitive long value representing the personUid
	 * @param versionCtrlNbr  The versionCtrlNbr representing the record to search for associated with the personUid
	 * @return Collection<Object>  The collection of EntityId related to the parameters passed in.
	 */
	private Collection<Object> selectEntityIDsHist (long personUID, int versionCtrlNbr)
	throws NEDSSSystemException,NEDSSSystemException
	{

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		EntityIdDT entityID = new EntityIdDT();
		ResultSetUtils resultSetUtils = new ResultSetUtils();

		try
		{
			dbConnection = getConnection();
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("SQLException while obtaining database connection " +
							"for selectEntityIDs hist ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}

		try
		{
			preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.SELECT_ENTITY_IDS_HIST);
			preparedStmt.setLong(1, personUID);
		preparedStmt.setLong(2, versionCtrlNbr);
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			ArrayList<Object>  idList = new ArrayList<Object> ();
			ArrayList<Object>  reSetList = new ArrayList<Object> ();
			idList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, entityID.getClass(), idList);

			for(Iterator<Object> anIterator = idList.iterator(); anIterator.hasNext(); )
			{
				EntityIdDT reSetName = (EntityIdDT)anIterator.next();
				reSetName.setItNew(false);
				reSetName.setItDirty(false);
				reSetList.add(reSetName);
			}

			logger.debug("Return entity id hist collection");
			return reSetList;
		}
		catch(SQLException se)
		{
			logger.fatal("SQLException while selecting " +
							"entity id hist collection; uid = " + personUID , se);
			throw new NEDSSSystemException(se.toString());
		}
		catch(ResultSetUtilsException rsuex)
		{
			logger.fatal("Error in result set handling while selecting entity ids hist.", rsuex);
			throw new NEDSSSystemException(rsuex.toString());
		}
		catch(Exception ex)
		{
			logger.fatal("Exception while selection " +
				  "entity ids hist; uid = " + personUID, ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	/**
	 * Private method to be used to insert a record into the Entity Id History table.
	 * @param dt  Represents the EntityIdDT to be saved.
	 * @return void
	 */
	private void insertEntityIdHist(EntityIdDT dt)
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
		int resultCount = 0;
		//PreparedStatement preparedStmt = getPreparedStatement(NEDSSSqlQuery.INSERT_ENTITY_ID_HIST);

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		if (dt.getEntityUid() != null)
		{
		  try
		  {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY_ID_HIST);
		   // setPreparedStatement(preparedStmt, dt);
			int i = 1;
			preparedStmt.setLong(i++, dt.getEntityUid().longValue());
			preparedStmt.setInt(i++, dt.getEntityIdSeq().intValue());
			preparedStmt.setInt(i++, nextSeqNum);

			preparedStmt.setString(i++, dt.getAddReasonCd());
			if( dt.getAddTime()==null)
				preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
			else
				preparedStmt.setTimestamp(i++, dt.getAddTime() );
			if( dt.getAddUserId()==null)
				preparedStmt.setNull(i++, Types.BIGINT );
			else
				preparedStmt.setLong(i++, dt.getAddUserId().longValue() );
				preparedStmt.setTimestamp(i++, dt.getAsOfDate());
				preparedStmt.setString(i++, dt.getAssigningAuthorityCd());
				preparedStmt.setString(i++, dt.getAssigningAuthorityDescTxt());
				preparedStmt.setString(i++, dt.getDurationAmt());
				preparedStmt.setString(i++, dt.getDurationUnitCd());
				preparedStmt.setString(i++, dt.getLastChgReasonCd());
			if( dt.getLastChgTime()==null)
				preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
			else
				preparedStmt.setTimestamp(i++, dt.getLastChgTime() );




			if( dt.getLastChgUserId()==null)
				preparedStmt.setNull(i++, Types.BIGINT );
			else
				preparedStmt.setLong(i++, dt.getLastChgUserId().longValue() );

			preparedStmt.setString(i++, dt.getRecordStatusCd());
			if( dt.getRecordStatusTime()==null)
				preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
			else
				preparedStmt.setTimestamp(i++, dt.getRecordStatusTime() );
			preparedStmt.setString(i++, dt.getRootExtensionTxt());
			preparedStmt.setString(i++, dt.getStatusCd());
			preparedStmt.setTimestamp(i++, dt.getStatusTime());
			preparedStmt.setString(i++, dt.getTypeCd());
			preparedStmt.setString(i++, dt.getTypeDescTxt());
			preparedStmt.setString(i++, dt.getUserAffiliationTxt());
			if( dt.getValidFromTime()==null)
				preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
			else
				preparedStmt.setTimestamp(i++, dt.getValidFromTime() );
			if( dt.getValidToTime()==null)
				preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
			else
				preparedStmt.setTimestamp(i++, dt.getValidToTime() );
			preparedStmt.setString(i++, dt.getAssigningAuthorityIdType());
			resultCount = preparedStmt.executeUpdate();
			if ( resultCount != 1 )
			{
				throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
			}
		  }
		  catch(SQLException se)
		  {
			se.printStackTrace();
			throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
		  }
		  finally
		  {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		  }
		}
	}

   /* private PreparedStatement getPreparedStatement(String sqlStmt)
		throws NEDSSDAOSysException
	{
		PreparedStatement preparedStmt = null;

		try
		{
			if ( dbConnection == null || dbConnection.isClosed() ) getConnection();
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			throw new NEDSSDAOSysException("Error: SQLException while obtaining database connection.\n" + se.getMessage());
		}
		catch(NEDSSSystemException nse)
		{
			nse.printStackTrace();
			throw new NEDSSDAOSysException("Error: NEDSSSystemException while obtaining database connection.\n" + nse.getMessage());
		}

		try
		{
			return dbConnection.prepareStatement(sqlStmt);
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			throw new NEDSSDAOSysException("Error: SQLException while obtaining prepared statement: " + sqlStmt + ".\n" + se.getMessage());
		}
	} */

 /*   private int setPreparedStatement(PreparedStatement preparedStmt, EntityIdDT dt)
	throws SQLException
	{

		int i = 1;
		preparedStmt.setLong(i++, dt.getEntityUid().longValue());

		preparedStmt.setInt(i++, dt.getEntityIdSeq().intValue());

		preparedStmt.setShort(i++, nextSeqNum);

		preparedStmt.setString(i++, dt.getAddReasonCd());

		if( dt.getAddTime()==null)
			preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
		else
			preparedStmt.setTimestamp(i++, dt.getAddTime() );

		if( dt.getAddUserId()==null)
			preparedStmt.setNull(i++, Types.BIGINT );
		else
			preparedStmt.setLong(i++, dt.getAddUserId().longValue() );

		preparedStmt.setString(i++, dt.getAssigningAuthorityCd());

		preparedStmt.setString(i++, dt.getAssigningAuthorityDescTxt());

		preparedStmt.setString(i++, dt.getLastChgReasonCd());

		preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));

		preparedStmt.setLong(i++, defaultChangeUserId);

		preparedStmt.setString(i++, dt.getDurationAmt());

		preparedStmt.setString(i++, dt.getDurationUnitCd());

		preparedStmt.setString(i++, dt.getLastChgReasonCd());

		if( dt.getLastChgTime()==null)
			preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
		else
			preparedStmt.setTimestamp(i++, dt.getLastChgTime() );

		if( dt.getLastChgUserId()==null)
			preparedStmt.setNull(i++, Types.BIGINT );
		else
			preparedStmt.setLong(i++, dt.getLastChgUserId().longValue() );

		preparedStmt.setString(i++, dt.getRecordStatusCd());

		if( dt.getRecordStatusTime()==null)
			preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
		else
			preparedStmt.setTimestamp(i++, dt.getRecordStatusTime() );

		preparedStmt.setString(i++, dt.getRootExtensionTxt());

		preparedStmt.setString(i++, dt.getStatusCd());

		preparedStmt.setTimestamp(i++, dt.getStatusTime());

		preparedStmt.setString(i++, dt.getTypeCd());

		preparedStmt.setString(i++, dt.getTypeDescTxt());

		preparedStmt.setString(i++, dt.getUserAffiliationTxt());

		if( dt.getValidFromTime()==null)
			preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
		else
			preparedStmt.setTimestamp(i++, dt.getValidFromTime() );

		if( dt.getValidToTime()==null)
			preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
		else
			preparedStmt.setTimestamp(i++, dt.getValidToTime() );

		return i;
	} */

}






