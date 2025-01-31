package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxEntityMatchDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

public class EdxEntityMatchDAO extends DAOBase{ 
	static final LogUtils logger = new LogUtils(EdxEntityMatchDAO.class.getName());
	
	
	
	public void setEdxEntityMatchDT (EdxEntityMatchDT  edxEntityMatchDT)throws  NEDSSSystemException
    {
	
		
		String INSERT_EDX_ENTITY_MATCH = "INSERT INTO EDX_ENTITY_MATCH (ENTITY_UID,MATCH_STRING, TYPE_CD,MATCH_STRING_HASHCODE)VALUES(?,?,?,?)";
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug("INSERT_EDX_ENTITY_MATCH="+INSERT_EDX_ENTITY_MATCH);
			dbConnection = getConnection();
			try{
			preparedStmt = dbConnection.prepareStatement(INSERT_EDX_ENTITY_MATCH);
			int i = 1;
		  
		    if(edxEntityMatchDT.getEntityUid()==null)
		    	preparedStmt.setNull(i++, Types.INTEGER); //1
		    else
		    	  preparedStmt.setLong(i++, edxEntityMatchDT.getEntityUid()); //1
		    preparedStmt.setString(i++, edxEntityMatchDT.getMatchString()); //2
		   	preparedStmt.setString(i++, edxEntityMatchDT.getTypeCd()); //3
		    if(edxEntityMatchDT.getMatchStringHashCode()==null)
		    	preparedStmt.setNull(i++, Types.INTEGER);//4
		    else	
		    	preparedStmt.setLong(i++,edxEntityMatchDT.getMatchStringHashCode() );//4
		   	resultCount = preparedStmt.executeUpdate();
		    logger.debug("resultCount in insertEdxEntityMatchDT is " + resultCount);
		}
		catch(SQLException sqlex)
		{
		    logger.fatal("SQLException while inserting " +
		            "edxEntityMatchDT into EDX_ENTITY_MATCH: ", sqlex);
		    throw new NEDSSDAOSysException("Table Name : EDX_ENTITY_MATCH  "+ sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
		    logger.fatal("Error while inserting into EDX_ENTITY_MATCH, edxEntityMatchDT ", ex);
		    throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
		  closeStatement(preparedStmt);
		  releaseConnection(dbConnection);
		}

    }//end of insert method
	
	public void updateEdxEntityMatchDT (EdxEntityMatchDT edxEntityMatchDT)throws  NEDSSSystemException
    {
		String UPDATE_EDX_ENTITY_MATCH="UPDATE EDX_ENTITY_MATCH SET ENTITY_UID =?, MATCH_STRING =?, TYPE_CD =?, MATCH_STRING_HASHCODE=?";
				 
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug(" UPDATE_EDX_ENTITY_MATCH="+ UPDATE_EDX_ENTITY_MATCH);
			
			try{
				dbConnection = getConnection();
				preparedStmt = dbConnection.prepareStatement(UPDATE_EDX_ENTITY_MATCH);
				int i = 1;
			 
			    if(edxEntityMatchDT.getEntityUid()==null)
			    	preparedStmt.setNull(i++, Types.INTEGER); //1
			    else
			    	preparedStmt.setLong(i++, edxEntityMatchDT.getEntityUid().longValue()); //2
			    preparedStmt.setString(i++,edxEntityMatchDT.getMatchString());
			    preparedStmt.setString(i++,edxEntityMatchDT.getTypeCd());
			    preparedStmt.setLong(i++,edxEntityMatchDT.getMatchStringHashCode());
			   
			    
			    resultCount = preparedStmt.executeUpdate();
			    logger.debug("resultCount is " + resultCount);
			}
			catch(SQLException sqlex)
			{
				logger.fatal("SQLException while updateEdxEntityMatchDT i:"+edxEntityMatchDT.toString(), sqlex);
				throw new NEDSSDAOSysException("Table Name : EDX_ENTITY_MATCH  "+ sqlex.toString(), sqlex);
			}
			catch(Exception ex)
			{
				logger.fatal("Error while update into UPDATE_EDX_ENTITY_MATCH, edxEntityMatchDT="+ edxEntityMatchDT.toString(), ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			finally
			{
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}

    }//end of update method
	
	public void updateMPR(EdxEntityMatchDT edxEntityMatchDT)throws  NEDSSSystemException
    {
		String UPDATE_PERSON ="UPDATE PERSON SET EDX_IND = 'Y' where Person_Uid = ?";
		String UPDATE_ORGANIZATION = "UPDATE ORGANIZATION SET EDX_IND = 'Y' where organization_uid = ?";
		Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug(" UPDATE_PERSON ="+ UPDATE_PERSON);
			logger.debug(" UPDATE_ORGANIZATION ="+ UPDATE_ORGANIZATION);
			String tableToUpdate="";
			try{
				dbConnection = getConnection();
				if(edxEntityMatchDT.getTypeCd().equalsIgnoreCase("PRV")){
					preparedStmt = dbConnection.prepareStatement(UPDATE_PERSON);
					tableToUpdate = "PERSON";
				}
				else{
					preparedStmt = dbConnection.prepareStatement(UPDATE_ORGANIZATION);
					tableToUpdate = "ORGANIZATION";
				}
				
				int i = 1;
			 
			    if(edxEntityMatchDT.getEntityUid()==null)
			    	preparedStmt.setNull(i++, Types.INTEGER); //1
			    else
			    	preparedStmt.setLong(i++, edxEntityMatchDT.getEntityUid().longValue()); //2
			   			    
			    resultCount = preparedStmt.executeUpdate();
			    logger.debug("resultCount is " + resultCount);
			}
			catch(SQLException sqlex)
			{
				logger.fatal("SQLException while updateEdxEntityMatchDT i:"+edxEntityMatchDT.toString(), sqlex);
				throw new NEDSSDAOSysException("Table Name : "+tableToUpdate+"  "+ sqlex.toString(), sqlex);
			}
			catch(Exception ex)
			{
				logger.fatal("Error while update into UPDATE_EDX_ENTITY_MATCH, edxEntityMatchDT="+ edxEntityMatchDT.toString(), ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			finally
			{
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}

    }//end of update method
	
	
	public EdxEntityMatchDT getEdxEntityMatch(Long edxEntityMatchUid)
	throws NEDSSDAOSysException, NEDSSSystemException {
		String SELECT_EDX_ENTITY_MATCH = "Select ENTITY_UID \"entityUid\" ,MATCH_STRING \"matchString\", TYPE_CD \"typeCd\" ,MATCH_STRING_HASHCODE \"matchStringHashCode\" from EDX_ENTITY_MATCH Where edx_entity_match_uid = ? ";
		EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(edxEntityMatchUid);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(edxEntityMatchDT,
			paramList, SELECT_EDX_ENTITY_MATCH, 
			NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (EdxEntityMatchDT) paramList.get(0);
		} catch (Exception ex) {
			logger
			.fatal("Exception in EdxEntityMatchDAOImpl.getEdxEntityMatch for match uid ="
					+ edxEntityMatchUid + ": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return edxEntityMatchDT;

}
	
	public ArrayList<Object> getEdxEntityMatchColl(String typeCd)
	throws NEDSSDAOSysException, NEDSSSystemException {
		String SELECT_EDX_ENTITY_MATCH = "Select ENTITY_UID \"entityUid\" ,MATCH_STRING \"matchString\", TYPE_CD \"typeCd\" ,MATCH_STRING_HASHCODE \"matchStringHashCode\" from EDX_ENTITY_MATCH Where type_cd = ? ";
		EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		ArrayList<Object> edxEntityMatchColl = new ArrayList<Object>();
		paramList.add(typeCd);
		try {
			edxEntityMatchColl = (ArrayList<Object>) preparedStmtMethod(edxEntityMatchDT,paramList, SELECT_EDX_ENTITY_MATCH,NEDSSConstants.SELECT);
						
		} catch (Exception ex) {
			logger
			.fatal("Exception in EdxEntityMatchDAOImpl.getEdxEntityMatchColl for type Code  ="
					+ typeCd + ": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return edxEntityMatchColl;

}
	
	/*
	public EdxEntityMatchDT getEdxEntityMatch(int matchHashCd)
	throws NEDSSDAOSysException, NEDSSSystemException {
		String SELECT_EDX_ENTITY_MATCH = "Select ENTITY_UID \"entityUid\" ,MATCH_STRING \"matchString\", TYPE_CD \"typeCd\" ,MATCH_STRING_HASHCODE \"matchStringHashCode\" from EDX_ENTITY_MATCH Where MATCH_STRING_HASHCODE = ? order by ENTITY_UID desc ";
		EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(matchHashCd);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(edxEntityMatchDT,
			paramList, SELECT_EDX_ENTITY_MATCH, 
			NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (EdxEntityMatchDT) paramList.get(0);
		} catch (Exception ex) {
			logger
			.fatal("Exception in EdxEntityMatchDAOImpl.getEdxEntityMatch for matchHashCode ="
					+ matchHashCd + ": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return edxEntityMatchDT;
	}
	*/

	/*
	 *  There was an issue with duplicate hash codes for different strings on rare occasions.
	 *  We added this routine, to replace the above routine. Note that the column 
	 *  EDX_ENTITY_MATCH.match_string should be indexed. ALso not that the match_string
	 *  is all upper case. 
	 *  
	 */
		public EdxEntityMatchDT getEdxEntityMatch(String typeCd, String matchString )
		throws NEDSSDAOSysException, NEDSSSystemException {
			//String SELECT_EDX_ENTITY_MATCH = "Select ENTITY_UID \"entityUid\" ,MATCH_STRING \"matchString\", TYPE_CD \"typeCd\" ,MATCH_STRING_HASHCODE \"matchStringHashCode\" from EDX_ENTITY_MATCH Where TYPE_CD = ? and MATCH_STRING = ? order by ENTITY_UID desc ";
			EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
			if (typeCd == null || matchString == null || matchString.isEmpty())
				return edxEntityMatchDT;
			//ArrayList<Object> paramList = new ArrayList<Object>();
			//paramList.add(typeCd);  //ORG or PRV
			//paramList.add(matchString);
			try {
				ArrayList<Object> inArrayList= new ArrayList<Object> ();
		        ArrayList<Object>  outArrayList= new ArrayList<Object> ();
		        ArrayList<Object> arrayList = new ArrayList<Object> ();
		        inArrayList.add(typeCd);
		        inArrayList.add(matchString);
		        outArrayList.add(new Integer(java.sql.Types.BIGINT));
				String sQuery  = "{call GETEDXENTITYMATCH_SP(?,?,?)}";
				arrayList = (ArrayList<Object> )callStoredProcedureMethod(sQuery,inArrayList,outArrayList);
			/* Code replaced with a Stored Procedure for for PHDC Import query optimization
				paramList = (ArrayList<Object>) preparedStmtMethod(edxEntityMatchDT,
				paramList, SELECT_EDX_ENTITY_MATCH, 
				NEDSSConstants.SELECT);
				if (paramList.size() > 0)
					return (EdxEntityMatchDT) paramList.get(0);
			*/
				if(arrayList != null && arrayList.size() > 0)
				{
					edxEntityMatchDT.setEntityUid((Long) arrayList.get(0));
					edxEntityMatchDT.setTypeCd(typeCd);
					edxEntityMatchDT.setMatchString(matchString);
				}
			} catch (Exception ex) {
				logger
				.fatal("Exception in EdxEntityMatchDAO.getEdxEntityMatch( for Type="
						+ typeCd + " and Match =" + matchString + ": ERROR = " + ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			return edxEntityMatchDT;
		}
	
}
