package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.AutoLabInvDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
/**
 * Investigation created from Labs are all extracted from this DAO. THis dao also helps in updating AUTO_LAB_INV table.
 * @author Pradeep Sharma
 *@version 4.1
 */
public class AutoLabDAO extends DAOBase{ 
	static final LogUtils logger = new LogUtils(AutoLabDAO.class.getName());
	String query="select condition_cd 'ConditionCode', order1.observation_uid \"orderObservationUid\", order1.local_id \"orderObservationLocalId\", result.observation_uid \"resultObservationUid\" from observation order1, Observation result, act_relationship act, "  +
	" nbs_srte..LOINC_code cd, nbs_srte..Loinc_condition lc where " +
	" order1.observation_uid=act.target_act_uid "  +
	" and act.source_act_uid=result.observation_uid " + 
	" and result.cd=cd.loinc_cd "  +
	" and cd.loinc_cd=lc.loinc_cd "  + 
	" and order1.observation_uid not in (select order_Observation_uid from auto_lab_inv) "  +
	" and order1.observation_uid not in (select target_act_uid from act_relationship where type_cd='LabReport') " + 
	" and order1.prog_area_cd is not null and order1.jurisdiction_cd is not null "+
	" order by order1.observation_uid";
	

	@SuppressWarnings({ "unchecked" })
	public Collection<Object> getUnassociatedLabs() throws NEDSSSystemException{
		AutoLabInvDT  autoLabInvDT  = new AutoLabInvDT();
		ArrayList<Object>  unassociatedLabColl  = new ArrayList<Object> ();
		Timestamp time = new java.sql.Timestamp(new Date().getTime());
		try
		{
			unassociatedLabColl  = (ArrayList<Object> )preparedStmtMethod(autoLabInvDT, unassociatedLabColl, query, NEDSSConstants.SELECT);
			if(unassociatedLabColl!=null){
				Iterator it = unassociatedLabColl.iterator();
				while(it.hasNext()){
					AutoLabInvDT aLabInvDT = (AutoLabInvDT)it.next();
					aLabInvDT.setRefreshTime(time);
					insertAutoLabInvDT(aLabInvDT);
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in getPamAnswerDTCollection:  ERROR = " + ex.getMessage(), ex);
					throw new NEDSSSystemException(ex.toString(), ex);
		}
		return unassociatedLabColl;
	}
	
	private void insertAutoLabInvDT (AutoLabInvDT  autoLabInvDT)throws  NEDSSSystemException
    {
		
		String INSERT_AUTO_INV = "INSERT INTO auto_lab_inv (condition_code,order_observation_uid, result_observation_uid,public_health_case_uid,refresh_time,inv_created,comment, order_observation_local_id)VALUES(?,?,?,?,?,?,?,?)";
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug("INSERT_AUTO_INV="+INSERT_AUTO_INV);
			try{
				dbConnection = getConnection();
			}catch(NEDSSSystemException nsex){
				logger.fatal("SQLException while obtaining database connection for insertAutoLabInvDT ", nsex);
				throw new NEDSSSystemException(nsex.toString());
			}
			try{
			preparedStmt = dbConnection.prepareStatement(INSERT_AUTO_INV);
			int i = 1;
		    preparedStmt.setString(i++, autoLabInvDT.getConditionCode()); //1
		    if(autoLabInvDT.getOrderObservationUid()==null)
		    	preparedStmt.setNull(i++, Types.INTEGER); //2
		    else
		    	preparedStmt.setLong(i++, autoLabInvDT.getOrderObservationUid().longValue()); //2
		    if(autoLabInvDT.getResultObservationUid()==null)
		    	preparedStmt.setNull(i++, Types.INTEGER); //3
		    else
		    	preparedStmt.setLong(i++, autoLabInvDT.getResultObservationUid().longValue()); //3
		    if(autoLabInvDT.getPublicHealthCaseUid()==null)
		    	preparedStmt.setNull(i++, Types.INTEGER); //4
		    else
		    	preparedStmt.setLong(i++, autoLabInvDT.getPublicHealthCaseUid().longValue()); //4
		    if(autoLabInvDT.getRefreshTime()==null)
		    	preparedStmt.setNull(i++, Types.TIMESTAMP);//5
		    else	//4
		    	preparedStmt.setTimestamp(i++,autoLabInvDT.getRefreshTime() );//5
		    preparedStmt.setString(i++,"Initial Process.");
		    preparedStmt.setString(i++,"BATCH PROCESS:Laboratory case identified that can be used to create Investigation!");
		    preparedStmt.setString(i++,autoLabInvDT.getOrderObservationLocalId());
		    
		    	resultCount = preparedStmt.executeUpdate();
		    logger.debug("resultCount in insertAutoLabInvDT is " + resultCount);
		}
		catch(SQLException sqlex)
		{
		    logger.fatal("SQLException while inserting " +
		            "insertAutoLabInvDT into INSERT_AUTO_INV:"+autoLabInvDT.toString(), sqlex);
		    throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
		    logger.fatal("Error while inserting into INSERT_AUTO_INV, insertAutoLabInvDT="+ autoLabInvDT.toString(), ex);
		    throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
		  closeStatement(preparedStmt);
		  releaseConnection(dbConnection);
		}

    }//end of insert method
	
	public void updateAutoLabInvDT (AutoLabInvDT autoLabInvDT)throws  NEDSSSystemException
    {
		java.util.Date dateTime = new java.util.Date();
		Timestamp time = new Timestamp(dateTime.getTime());
		autoLabInvDT.setRefreshTime(time);
		String UPDATE_LAB_INV="UPDATE auto_lab_inv SET condition_code =?, order_observation_uid =?, result_observation_uid =?, public_health_case_uid=?, refresh_time =?, inv_created =?, comment = ?, order_observation_local_id=? where auto_lab_inv_uid=? ";
				 
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug(" UPDATE_LAB_INV="+ UPDATE_LAB_INV);
			try{
				dbConnection = getConnection();
			}catch(NEDSSSystemException nsex){
				logger.fatal("SQLException while obtaining database connection for updateAutoLabInvDT ", nsex);
				throw new NEDSSSystemException(nsex.toString());
			}
			try{
				preparedStmt = dbConnection.prepareStatement(UPDATE_LAB_INV);
				int i = 1;
			    preparedStmt.setString(i++, autoLabInvDT.getConditionCode()); //1
			    if(autoLabInvDT.getOrderObservationUid()==null)
			    	preparedStmt.setNull(i++, Types.INTEGER); //2
			    else
			    	preparedStmt.setLong(i++, autoLabInvDT.getOrderObservationUid().longValue()); //2
			    if(autoLabInvDT.getResultObservationUid()==null)
			    	preparedStmt.setNull(i++, Types.INTEGER); //3
			    else
			    	preparedStmt.setLong(i++, autoLabInvDT.getResultObservationUid().longValue()); //3
			    if(autoLabInvDT.getPublicHealthCaseUid()==null)
			    	preparedStmt.setNull(i++, Types.INTEGER); //4
			    else
			    	preparedStmt.setLong(i++, autoLabInvDT.getPublicHealthCaseUid().longValue()); //4
			    if(autoLabInvDT.getRefreshTime()==null)
			    	preparedStmt.setNull(i++, Types.TIMESTAMP);//5
			    else	//4
			    	preparedStmt.setTimestamp(i++,autoLabInvDT.getRefreshTime() );//5
			    preparedStmt.setString(i++,autoLabInvDT.getInvCreated());
			    preparedStmt.setString(i++,autoLabInvDT.getComment());
			    preparedStmt.setString(i++,autoLabInvDT.getOrderObservationLocalId());
			    preparedStmt.setLong(i++,autoLabInvDT.getAutoLabInvUid());
			    
			    resultCount = preparedStmt.executeUpdate();
			    logger.debug("resultCount is " + resultCount);
			}
			catch(SQLException sqlex)
			{
				logger.fatal("SQLException while updateNbsCaseEntity i:"+autoLabInvDT.toString(), sqlex);
				throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
			}
			catch(Exception ex)
			{
				logger.fatal("Error while update into UPDATE_NBS_CASE_ENTITY, nbsCaseEntityDT="+ autoLabInvDT.toString(), ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			finally
			{
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}

    }//end of update method
	
	
	@SuppressWarnings("unchecked")
	public Collection<Object>  getAutoLabInvDTCollection(String sqlQuery, ArrayList<Object> autoLabInvDTCollection){
			AutoLabInvDT autoLabInvDT  = new AutoLabInvDT();
		try
		{
			autoLabInvDTCollection  = (ArrayList<Object> )preparedStmtMethod(autoLabInvDT, autoLabInvDTCollection, sqlQuery, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			logger.fatal("Exception in getAutoLabInvDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return autoLabInvDTCollection;
	}
	
	public String getProgramAreaCd (String conditionCode) throws
	NEDSSSystemException
	{
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		String progAreaCode="";
		try
		{
			String query="select prog_area_cd from nbs_srte..condition_code where condition_cd =?";

			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(query);
			  preparedStmt.setString(1, conditionCode);
			  logger.debug("conditionCode = " + conditionCode);
			resultSet = preparedStmt.executeQuery();

			if (!resultSet.next())
			   {
				  //Error this condition is not mapped!
			   }
			   else
			   {
				   progAreaCode = resultSet.getString(1);
			   }
			
		}
		catch(SQLException sqlException)
		{
			logger.fatal("SQLException while checking for an"
					+ " getProgramAreaCd -> "
					+ conditionCode, sqlException);
			throw new NEDSSDAOSysException( sqlException.getMessage(), sqlException);
		}
		catch(NEDSSSystemException nedssSysException)
		{
			logger.fatal("Exception while getting dbConnection for checking for an"
					+ " getProgramAreaCd for condition code-> " + conditionCode, nedssSysException);
			throw new NEDSSDAOSysException( nedssSysException.getMessage(), nedssSysException);
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return progAreaCode;
	}


}
