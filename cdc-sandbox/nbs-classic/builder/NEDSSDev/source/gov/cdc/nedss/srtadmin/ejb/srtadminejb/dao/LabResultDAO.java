package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;

import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.LabResultDT;
import gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.util.SRTAdminUtilDAOImpl;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
* Name:		LabResultDAO.java
* Description:	DAO Object for Lab_Result.
* Copyright:	Copyright (c) 2008
* Company: 	Computer Sciences Corporation
* @author	Kabita Sahoo
*/

public class LabResultDAO extends DAOBase{
	
	private static final LogUtils logger = new LogUtils(LabResultDAO.class
			.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private static final String SELECT_LAB_RESULT_SQL="SELECT  lab_result_cd \"labResultCd\" "
		+", laboratory_id \"laboratoryId\" "
		+",lab_result_desc_txt \"labResultDescTxt\""
		+", effective_from_time \"effectiveFromTime\""
		+",effective_to_time \"effectiveToTime\" "
		+", nbs_uid \"nbsUid\""
		+",default_prog_area_cd \"defaultProgAreaCd\""
		+",organism_name_ind \"organismNameInd\""
		+",default_condition_cd \"defaultConditionCd\" "
		+ ",pa_derivation_exclude_cd \"paDerivationExcludeCd\" "
		+"FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Lab_result ";
	

	private static final String CREATE_LABRESULT_SQL = "INSERT INTO "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
		+ "..Lab_result(	lab_result_cd , "
		+"laboratory_id ,"
		+"lab_result_desc_txt "
		+", effective_from_time"
		+",effective_to_time  "
		+", nbs_uid"
		+",default_prog_area_cd "
		+",organism_name_ind"
		+",default_condition_cd, " + "pa_derivation_exclude_cd) " + "VALUES(?, ?, ?, ?, ?,'', ?, ?, ?, ?);";

private static final String UPDATE_LABRESULT_SQL = "UPDATE "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Lab_result "
		+ "SET 	lab_result_desc_txt=?, " + "default_prog_area_cd=?, "
		+ "default_condition_cd=?, " 
		+ "organism_name_ind=? , " + "pa_derivation_exclude_cd=?  " + "WHERE "
		+ "lab_result_cd=? "+ "AND laboratory_id=?;";


	
	/**
	 * gets the LabResultDT Collection<Object> Object for a given LabResultCd
	 * @return Collection<Object> of LabResultDT 
	 */
	
@SuppressWarnings("unchecked")
public Collection<Object> getLabResultDTCollection(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =SELECT_LAB_RESULT_SQL;
		ArrayList<Object>  labResultDTCollection = new ArrayList<Object> ();
		try {

	        codeSql = codeSql + whereClause;
	
			LabResultDT labResultDT = new LabResultDT();
			
			labResultDTCollection = (ArrayList<Object> ) preparedStmtMethod(labResultDT, labResultDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findLocallyDefinedTest: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return labResultDTCollection;
		
	}




/**
 * 
 * @param labTestDT
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
public void createLocallyDefinedResult(LabResultDT labResultDT) throws NEDSSDAOSysException, NEDSSSystemException {
	
	String codeSql =CREATE_LABRESULT_SQL;
	
	try {
		
	    String organismInd = labResultDT.getOrganismNameInd()== null ? "" : labResultDT.getOrganismNameInd(); ;
	    String paDevExcludCd = labResultDT.getPaDerivationExcludeCd() == null ? "" : labResultDT.getPaDerivationExcludeCd(); 
	    
		ArrayList<Object>  paramList = new ArrayList<Object> ();
		paramList.add(labResultDT.getLabResultCd());
		paramList.add(labResultDT.getLaboratoryId());
		paramList.add(labResultDT.getLabResultDescTxt());
		paramList.add(new Timestamp(new Date().getTime()));
		paramList.add(labResultDT.getEffectiveToTime());
		paramList.add(labResultDT.getDefaultProgAreaCd());
		if(organismInd!=null && organismInd.equals("1")) 
			paramList.add("Y");
		else  
			paramList.add("N");
		paramList.add(labResultDT.getDefaultConditionCd());
		if(paDevExcludCd.equals("1"))paramList.add("Y");
		else  paramList.add("N");
				
		preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
		
	} catch (Exception ex) {
		logger.fatal("Exception in findLocallyDefinedTest: ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}		
}


/**
 * 
 * @param labTestDT
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
public void updateLocallyDefinedResult(LabResultDT labResultDT) throws NEDSSDAOSysException, NEDSSSystemException {
	
	String codeSql =UPDATE_LABRESULT_SQL;
	
	try {
	 	
	    String organismInd = labResultDT.getOrganismNameInd(); 
	    String paDerExcludeCd = labResultDT.getPaDerivationExcludeCd(); 
	    
		ArrayList<Object>  paramList = new ArrayList<Object> ();
		paramList.add(labResultDT.getLabResultDescTxt());
		paramList.add(labResultDT.getDefaultProgAreaCd());
		paramList.add(labResultDT.getDefaultConditionCd());
		if(organismInd!=null && organismInd.equals("1")) 
			paramList.add("Y");
		else  
			paramList.add("N");
		if(paDerExcludeCd.equals("1")) paramList.add("Y");
		else  paramList.add("N");
		paramList.add(labResultDT.getLabResultCd());
		paramList.add(labResultDT.getLaboratoryId());
	
				
		preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
		
	} catch (Exception ex) {
		logger.fatal("Exception in findLocallyDefinedTest: ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}		
}

	public String findCodeSetGroupIdFromQuestionIdentifier (String questionIdentifier){
		
		String codeSetGroupId="";
		
		String query = "";
		query = NEDSSSqlQuery.SELECT_CODE_SET_GROUP_ID_SQL;
		query += " where question_identifier = ?";
				 Connection dbConnection = null;
	     PreparedStatement preparedStmt = null;
		 ResultSet resultSet = null;

	    try
	    {
	      dbConnection = getConnection();

	      preparedStmt = dbConnection.prepareStatement(query);
	      preparedStmt.setString(1, questionIdentifier);
	      resultSet = preparedStmt.executeQuery();

	      while ( resultSet.next() )
	      {
	    	  codeSetGroupId = resultSet.getString(1);
	      }
	    }
	    catch(Exception e)
	    {
	      logger.fatal("Exception ="+e.getMessage(), e);
	      throw new NEDSSSystemException(e.toString());
	    }
	    finally
	    {
	      closeResultSet(resultSet);
	      closeStatement(preparedStmt);
	      releaseConnection(dbConnection);
	    }
				 
	    
		return codeSetGroupId;
		
	}

}
