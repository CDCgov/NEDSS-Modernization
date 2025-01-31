package gov.cdc.nedss.nbsactentity.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
* Name:		NbsCaseEntityDAO.java
* Description:	DAO Objecto for Pam answers.
* Copyright:	Copyright (c) 2008
* Company: 	Computer Sciences Corporation
* @author	Pradeep Sharma
*/

public class NbsActEntityDAO extends DAOBase{
	static final LogUtils logger = new LogUtils(NbsActEntityDAO.class.getName());
	private final String SELECT_ACT_ENTITY_COLLECTION="SELECT nbs_act_entity_uid \"nbsActEntityUid\", add_time \"addTime\", add_user_id \"addUserId\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", act_uid \"actUid\", entity_uid \"entityUid\", type_cd \"typeCd\", entity_version_ctrl_nbr \"entityVersionCtrlNbr\" FROM "+ DataTables.NBS_ACT_ENTITY_TABLE +" where act_uid=?";
	private final String INSERT_NBS_ACT_ENTITY="INSERT INTO "+ DataTables.NBS_ACT_ENTITY_TABLE +"(add_time, add_user_id, last_chg_time, last_chg_user_id, act_uid, entity_uid, type_cd , record_status_cd, record_status_time, entity_version_ctrl_nbr) VALUES(?,?,?,?,?,?,?,?,?,?)";
	private final String DELETE_NBS_ACT_ENTITY= "DELETE from  "+ DataTables.NBS_ACT_ENTITY_TABLE 	+ " where nbs_act_entity_uid= ?";
	private final String UPDATE_NBS_ACT_ENTITY= "UPDATE "+ DataTables.NBS_ACT_ENTITY_TABLE 	+ "	SET last_chg_time = ?, last_chg_user_id = ?,  entity_uid = ?, record_status_cd = ?, record_status_time = ?, entity_version_ctrl_nbr=?  WHERE  nbs_act_entity_uid=?";
	private static final String LOGICALLY_DELETE_NBS_ACT_ENTITY= "UPDATE  "+ DataTables.NBS_ACT_ENTITY_TABLE +"  Set last_chg_time=?, last_chg_user_id=?,  record_status_cd=?, record_status_time=?, entity_version_ctrl_nbr=? WHERE nbs_act_entity_uid=?";
	/**
	 * gets the NbsCaseEntityDT Collection<Object>  Object for a given publicHealthCaseUID
	 * @return Collection<Object>  of NbsCaseEntityDT
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  getActEntityDTCollection(Long actUid){
		NbsActEntityDT pamCaseEntityDT  = new NbsActEntityDT();
		ArrayList<Object> pamEntityDTCollection  = new ArrayList<Object> ();
		pamEntityDTCollection.add(actUid);
		try
		{
			pamEntityDTCollection  = (ArrayList<Object> )preparedStmtMethod(pamCaseEntityDT, pamEntityDTCollection, SELECT_ACT_ENTITY_COLLECTION, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			logger.fatal("Exception in getPamEntityDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return pamEntityDTCollection;
	}

	
	public void insertActEntityDTCollection(Collection<Object> pamDTCollection, RootDTInterface rootDTInterface) throws  NEDSSSystemException{
		try{
			if(pamDTCollection.size()>0){
				Iterator<Object> it  = pamDTCollection.iterator();
				while(it.hasNext()){
					NbsActEntityDT pamCaseEntityDT = (NbsActEntityDT)it.next();
					insertActEntityDT(pamCaseEntityDT, rootDTInterface);
				}
			}
		}
		catch(NEDSSSystemException ex){
			logger.fatal("Exception in insertActEntityDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	public void storeActEntityDTCollection(Collection<Object> pamDTCollection, RootDTInterface rootDTInterface) throws  NEDSSSystemException{
		try{
			if(pamDTCollection.size()>0){
				Iterator<Object> it  = pamDTCollection.iterator();
				while(it.hasNext()){
					NbsActEntityDT pamCaseEntityDT = (NbsActEntityDT)it.next();
					 if(pamCaseEntityDT.isItDelete()){
							removeNbsActEntity(pamCaseEntityDT);
						}
					 else if(pamCaseEntityDT.isItDirty())
					{
						updateNbsActEntity(pamCaseEntityDT, rootDTInterface);
					}
					else
						insertActEntityDT(pamCaseEntityDT, rootDTInterface);
				}
			}
		}
		catch(NEDSSSystemException ex){
			logger.fatal("Exception in storeActEntityDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	public void removeActEntityDTCollection(Collection<Object> actEntityDTCollection) throws  NEDSSSystemException{
		try{
			if(actEntityDTCollection.size()>0){
				Iterator<Object> it  = actEntityDTCollection.iterator();
				while(it.hasNext()){
					NbsActEntityDT pamCaseEntityDT = (NbsActEntityDT)it.next();
					 if(pamCaseEntityDT.isItDelete()){
							removeNbsActEntity(pamCaseEntityDT);
						}
				}
			}
		}
		catch(NEDSSSystemException ex){
			logger.fatal("Exception in removeActEntityDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}


	private void insertActEntityDT (NbsActEntityDT pamCaseEntityDT,RootDTInterface rootDTInterface) throws  NEDSSSystemException
	{
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		logger.debug("INSERT_NBS_CASE_ENTITY="+INSERT_NBS_ACT_ENTITY);
		dbConnection = getConnection();
		try{
			int resultCount = 0;
			preparedStmt = dbConnection.prepareStatement(INSERT_NBS_ACT_ENTITY);
			int i = 1;
			if(pamCaseEntityDT.getAddTime()==null)
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new Date().getTime())); //1
			else
				preparedStmt.setTimestamp(i++, rootDTInterface.getAddTime()); //1
			preparedStmt.setLong(i++, pamCaseEntityDT.getAddUserId().longValue()); //2
			preparedStmt.setTimestamp(i++, rootDTInterface.getLastChgTime()); //5
			if(pamCaseEntityDT.getLastChgUserId()==null)
				preparedStmt.setLong(i++, pamCaseEntityDT.getAddUserId().longValue()); //6
			else
				preparedStmt.setLong(i++, pamCaseEntityDT.getLastChgUserId().longValue()); //6
			preparedStmt.setLong(i++,  rootDTInterface.getUid().longValue()); //6
			preparedStmt.setLong(i++, pamCaseEntityDT.getEntityUid().longValue()); //7
			preparedStmt.setString(i++, pamCaseEntityDT.getTypeCd()); //8
		    preparedStmt.setString(i++, rootDTInterface.getRecordStatusCd()); //9
		    preparedStmt.setTimestamp(i++, rootDTInterface.getRecordStatusTime()); //10
	    	preparedStmt.setInt(i++, pamCaseEntityDT.getEntityVersionCtrlNbr().intValue()); //11

			resultCount = preparedStmt.executeUpdate();
			if ( resultCount != 1 )
			{
				logger.fatal("Exception while inserting into NBS_CASE_ENTITY, NbsActEntityDT="+ pamCaseEntityDT.toString());
				throw new NEDSSSystemException ("Error: none or more than one pamCaseEntityDT inserted at a time, resultCount = " + resultCount);
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException while inserting " +
					"NbsActEntityDT into Pam_Case_Entity: \n", sex);
			logger.fatal("SQLException while inserting into Nbs_Case_Entity, NbsActEntityDT="+ pamCaseEntityDT.toString(), sex);
			throw new NEDSSSystemException( sex.toString() );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while inserting into Nbs_Case_Entity, NbsActEntityDT="+ pamCaseEntityDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}//end of insert method

	private void removeNbsActEntity(NbsActEntityDT nbsCaseEntityDT)	throws NEDSSDAOSysException, NEDSSSystemException {

		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger.debug("$$$$###Delete DELETE_NBS_CASE_ENTITY being called :"+ DELETE_NBS_ACT_ENTITY);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELETE_NBS_ACT_ENTITY);
			preparedStmt.setLong(1,nbsCaseEntityDT.getNbsActEntityUid().longValue());
			preparedStmt.executeUpdate();
		} catch (SQLException se) {
		    logger.fatal("SQLException while removeNbsCaseEntity " +nbsCaseEntityDT.toString(), se);
			throw new NEDSSDAOSysException("Error: SQLException while deleting\n" + se.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	
	private void updateNbsActEntity (NbsActEntityDT nbsCaseEntityDT,RootDTInterface rootDTInterface)throws  NEDSSSystemException
    {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug(" UPDATE_NBS_CASE_ENTITY="+ UPDATE_NBS_ACT_ENTITY);
			try{
				dbConnection = getConnection();
			}catch(NEDSSSystemException nsex){
				logger.fatal("SQLException while obtaining database connection for updateNbsActEntity ", nsex);
				throw new NEDSSSystemException(nsex.toString());
			}
			try{
				preparedStmt = dbConnection.prepareStatement( UPDATE_NBS_ACT_ENTITY);
				int i = 1;
				preparedStmt.setTimestamp(i++, rootDTInterface.getLastChgTime()); //1
				preparedStmt.setLong(i++, rootDTInterface.getLastChgUserId().longValue()); //2
				preparedStmt.setLong(i++, nbsCaseEntityDT.getEntityUid().longValue()); //3
				preparedStmt.setString(i++, rootDTInterface.getRecordStatusCd()); //4
			    preparedStmt.setTimestamp(i++, rootDTInterface.getRecordStatusTime()); //5
			    preparedStmt.setLong(i++,nbsCaseEntityDT.getEntityVersionCtrlNbr().intValue()); //6
			    preparedStmt.setLong(i++,nbsCaseEntityDT.getNbsActEntityUid().longValue()); //7
			    
			
			    resultCount = preparedStmt.executeUpdate();
			    logger.debug("resultCount is " + resultCount);
			}
			catch(SQLException sqlex)
			{
				logger.fatal("SQLException while updateNbsCaseEntity i:"+nbsCaseEntityDT.toString(), sqlex);
				throw new NEDSSDAOSysException( sqlex.toString() );
			}
			catch(Exception ex)
			{
				logger.fatal("Error while update into UPDATE_NBS_CASE_ENTITY, nbsCaseEntityDT="+ nbsCaseEntityDT.toString(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
			finally
			{
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}

    }//end of update method
	
	public void logDelActEntityDTCollection(Collection<Object> pamDTCollection, RootDTInterface rootDTInterface) throws NEDSSSystemException{
		try{
			if(pamDTCollection!=null){
				Iterator<Object> it  = pamDTCollection.iterator();
				while(it.hasNext()){
					NbsActEntityDT nbsCaseEntityDT =(NbsActEntityDT) it.next();
				    	logDelActEntityDT(nbsCaseEntityDT,rootDTInterface);
					}
				}
		}
		catch(NEDSSSystemException ex){
			logger.error("error thrown at logDelPamCaseEntityDTCollection  method"+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	private void logDelActEntityDT (NbsActEntityDT nbsCaseEntityDT,RootDTInterface rootDTInterface)throws  NEDSSSystemException
    {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug(" LOGICALLY_DELETE_NBS_CASE_ENTITY="+ LOGICALLY_DELETE_NBS_ACT_ENTITY);
			try{
				dbConnection = getConnection();
			}catch(NEDSSSystemException nsex){
				logger.fatal("SQLException while obtaining database connection for logDelActEntityDT ", nsex);
				throw new NEDSSSystemException(nsex.toString());
			}
			try{
				preparedStmt = dbConnection.prepareStatement( LOGICALLY_DELETE_NBS_ACT_ENTITY);
				int i = 1;
				preparedStmt.setTimestamp(i++, rootDTInterface.getLastChgTime()); //1
				preparedStmt.setLong(i++, rootDTInterface.getLastChgUserId().longValue()); //2
				preparedStmt.setString(i++, rootDTInterface.getRecordStatusCd()); //3
			    preparedStmt.setTimestamp(i++, rootDTInterface.getRecordStatusTime()); //4
			    if(nbsCaseEntityDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.PHC_PATIENT))
				    preparedStmt.setLong(i++,nbsCaseEntityDT.getEntityVersionCtrlNbr().intValue()+1); //5
			    else
			    	preparedStmt.setLong(i++,nbsCaseEntityDT.getEntityVersionCtrlNbr().intValue()); //5
			    preparedStmt.setLong(i++,nbsCaseEntityDT.getNbsActEntityUid().longValue()); //6
			    resultCount = preparedStmt.executeUpdate();
				logger.debug("resultCount is " + resultCount);
		}
		catch(SQLException sqlex)
		{
		    logger.fatal("SQLException while logDelPamCaseEntityrDT called:"+nbsCaseEntityDT.toString(), sqlex);
		    throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
		    logger.fatal("Error in method logDelPamCaseEntityrDT, nbsCaseEntityDT="+ nbsCaseEntityDT.toString(), ex);
		    throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
		  closeStatement(preparedStmt);
		  releaseConnection(dbConnection);
		}

    }//end of update method
		
}
