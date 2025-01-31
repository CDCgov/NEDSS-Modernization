package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
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

public class CodeValueGeneralDAOImpl extends DAOBase{
	private static final LogUtils logger = new LogUtils(CodeValueGeneralDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private static final String GET_EXISTING_CODE_SET_SQL = "SELECT code_set_nm \"codeSetNm\" "+
    	"FROM "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE +
    	"..code_value_general" ;
	
 
	private static final String RETRIEVE_CODE_SET_VAL_GEN_FIELDS_SQL = "SELECT "
		+" code_set_nm \"codeSetNm\","
		+" code \"code\","
		+" code_desc_txt \"codeDescTxt\","
		+" code_short_desc_txt \"codeShortDescTxt\","
		+" code_system_cd \"codeSystemCd\","
		+" code_system_desc_txt \"codeSystemDescTxt\","
		+" effective_from_time \"effectiveFromTime\","
		+" effective_to_time \"effectiveToTime\","
		+" indent_level_nbr \"indentLevelNbr\","
		+" is_modifiable_ind \"isModifiableInd\"," 
		+" parent_is_cd \"parentIsCd\","
		+" nbs_uid \"nbsUid\","
		+" source_concept_id \"sourceConceptId\","
		+" super_code_set_nm \"superCodeSetNm\","
		+" super_code \"superCode\","
		+" status_cd \"statusCd\","
		+" concept_type_cd \"conceptTypeCd\","
		+" concept_code \"conceptCode\","
		+" status_time \"statusTime\","
		
		+" concept_nm \"conceptNm\","
		+" concept_preferred_nm \"conceptPreferredNm\","
		+" concept_status_cd \"conceptStatusCd\","
		+" concept_status_time \"conceptStatusTime\","
		+" code_system_version_nbr \"codeSystemVersionNbr\","
		+" concept_order_nbr \"conceptOrderNbr\","
		+" admin_comments \"adminComments\","
		+" add_time \"addTime\","
		+" add_user_id \"addUserId\" "
		+" FROM "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE 
		+"..code_value_general"
		+" WHERE code_set_nm =? order by code_short_desc_txt";
	
	
	private static final String UPDATE_CODE_VAL_GEN_SQL = "UPDATE "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general"
		+ " SET code_desc_txt=?,"
		+ " code_short_desc_txt=?, "
		+ "code_system_cd=?,"
		+ " code_system_desc_txt=?,"
		+ " parent_is_cd=?, " + "source_concept_id=?,"
		+ " concept_type_cd=?, concept_code=?,"
		+ " concept_nm=?, concept_preferred_nm=?,"
		+ " add_time=?,"
		+ " super_code_set_nm=?, " + "super_code=? , "
		+ " admin_comments=?, " + "status_cd=?  , "
		+ " effective_from_time=?, " + "effective_to_time=?"
		+ " WHERE code=?"
		+ " and code_set_nm=?";

	
	private static final String CREATE_CODE_VALUE_GENERAL_CODE_SQL = "INSERT INTO "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
		+ "..Code_value_general(code_set_nm, code, "
		+ " code_desc_txt, code_short_desc_txt,"
	    + " code_system_cd, code_system_desc_txt, "
	    + " indent_level_nbr, parent_is_cd,"
	    + " concept_type_cd, concept_code,"
	    + " source_concept_id, super_code_set_nm, "
	    + " super_code, status_cd,"
	    + " status_time, effective_from_time,"
	    + " nbs_uid, is_modifiable_ind,"
	    + "concept_nm, concept_preferred_nm, " 
	    + "concept_status_cd, concept_status_time, "
	    + "code_system_version_nbr, concept_order_nbr, "
	    + "admin_comments, add_time,add_user_id , effective_to_time"
	    + ") Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	

	
	private static final String TEMP_CVG_DUPL_CODE_SQL = "select count(*) from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general where CODE_SET_NM=? AND code=? ";
	
	private static final String CREATE_CODESET_SQL = "INSERT INTO "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
		+ "..Codeset(code_set_nm, assigning_authority_cd, assigning_authority_desc_txt, code_set_desc_txt, code_system_cd, code_system_desc_txt, class_cd, effective_from_time, effective_to_time, is_modifiable_ind, nbs_uid, source_version_txt, source_domain_nm, status_cd, status_to_time,code_set_group_id" 
		+ "admin_comments, value_set_nm, ldf_picklist_ind_cd, value_set_code, value_set_type_cd,	value_set_oid,	value_set_status_cd, value_set_status_time,	parent_is_cd,add_time,	add_user_id) "
		+ " VALUES(?,?,?,?,?,?,?,'code_value_general',?,?,'Y','',NULL,NULL,'A',?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	
	private static final String SELECT_CODE_SET_GROUP_ID_SQL = "SELECT MAX(code_set_group_id) FROM  NBS_SRTE..CODESET where CODE_SET_NM=? ";
	
	private static final String GET_CODE_DESC_TXT_SQL = "SELECT code_set_desc_txt FROM NBS_SRTE..CODESET where CODE_SET_NM=? ";
	
	private static final String UPDATE_CODE_VAL_GEN_OTH_CODE_SQL = "UPDATE "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general"
		+ " SET code=?"
		+ " WHERE concept_code=? and code_set_nm=?";
	
	/**
	 * 
	 * @param searchParams
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> getExistingCodeSets( String searchParams) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =GET_EXISTING_CODE_SET_SQL;
		
        CodeValueGeneralDT codeValueGenDT = new CodeValueGeneralDT();
		ArrayList<Object>  codeValueGenDTCollection = new ArrayList<Object> ();
		codeValueGenDTCollection.add(searchParams);
		
		try {			
			codeValueGenDTCollection = (ArrayList<Object> ) preparedStmtMethod(codeValueGenDT, codeValueGenDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in getExistingCodeSets: ERROR = " + codeValueGenDT.toString(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return codeValueGenDTCollection;
		
	}	
	
@SuppressWarnings("unchecked")
public Collection<Object> retrieveCodeSetValGenFields( String searchParams) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =RETRIEVE_CODE_SET_VAL_GEN_FIELDS_SQL;
		
       CodeValueGeneralDT codeValueGenDT = new CodeValueGeneralDT();
		ArrayList<Object>  codeValueGenDTCollection = new ArrayList<Object> ();
		codeValueGenDTCollection.add(searchParams.toUpperCase());
		
		try {			
			codeValueGenDTCollection = (ArrayList<Object> ) preparedStmtMethod(codeValueGenDT, codeValueGenDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in retrieveCodeSetValGenFields: ERROR = " +codeValueGenDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return codeValueGenDTCollection;
		
	}

public void updateCodeValueGeneral(CodeValueGeneralDT codeValGenDT) throws NEDSSDAOSysException, NEDSSSystemException {
	
	String codeSql =UPDATE_CODE_VAL_GEN_SQL;
	
   
    System.out.println(codeValGenDT.getCodeDescTxt());
    System.out.println(codeValGenDT.getCodeShortDescTxt());
    System.out.println(codeValGenDT.getCodeSystemCd());
    System.out.println(codeValGenDT.getCodeSystemDescTxt());
    System.out.println(codeValGenDT.getParentIsCd());
    System.out.println(codeValGenDT.getSourceConceptId());
    System.out.println(codeValGenDT.getConceptTypeCd());
    System.out.println(codeValGenDT.getConceptCode());
    System.out.println(codeValGenDT.getConceptNm());
    
    
    logger.debug("****************Sql Query***************"+UPDATE_CODE_VAL_GEN_SQL);
    logger.debug("Code Set Num Val  *************** "+codeValGenDT.getCodeSetNm());
    logger.debug("Code val *************** "+codeValGenDT.getCode());
    
	ArrayList<Object>  paramList = new ArrayList<Object> ();
	paramList.add(codeValGenDT.getCodeDescTxt());
	paramList.add(codeValGenDT.getCodeShortDescTxt());
	paramList.add(codeValGenDT.getCodeSystemCd());
	paramList.add(codeValGenDT.getCodeSystemDescTxt());
	paramList.add(codeValGenDT.getParentIsCd());
	paramList.add(codeValGenDT.getSourceConceptId());
	paramList.add(codeValGenDT.getConceptTypeCd());
	paramList.add(codeValGenDT.getConceptCode());
	paramList.add(codeValGenDT.getConceptNm());
	paramList.add(codeValGenDT.getConceptPreferredNm());
	Timestamp now = new Timestamp(System.currentTimeMillis());
	paramList.add(now);
	paramList.add(codeValGenDT.getSuperCodeSetNm());
	paramList.add(codeValGenDT.getSuperCode());
	
	//Added By jayasudha to store the admin comments and status when edit mode
	paramList.add(codeValGenDT.getAdminComments());
	paramList.add(codeValGenDT.getStatusCd());
	paramList.add(codeValGenDT.getLocalEffectiveFromTime());
	paramList.add(codeValGenDT.getLocalEffectiveToTime());
	
	paramList.add(codeValGenDT.getCode());
	paramList.add(codeValGenDT.getCodeSetNm());	
	
	try {			
		preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
		
	} catch (Exception ex) {
		logger.fatal("Exception in updateCodeValueGeneral: ERROR = " +codeValGenDT.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	}		
}

/**
 * createCodeValueGeneral comprises of the following steps
 * (1) Temp Fix: check to avoid duplicate code for the same codeset
 * (2) If exists, just insert data into Code Value General.
 * 
 * @param codeValGenDT
 */
	public void createCodeValueGeneral(CodeValueGeneralDT codeValGenDT) {
		Integer count = null;
		try{
			// Check for the duplicate codesetnm and Code
			count = duplicateCodesetNm(codeValGenDT);
	
			if (count != null && count.intValue() > 0) {
				throw new NEDSSSystemException(
						"Exception while calling insertCVGWithExistingSeqNo");
			} else {
				insertCodeValueGeneral(codeValGenDT);
			}
		}catch(NEDSSSystemException ex){
			logger.fatal("Exception while calling insertCVGWithExistingSeqNo "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.getMessage());
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getLocalizedMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}

	}

public void insertCodeValueGeneral(CodeValueGeneralDT codeValGenDT) {
	
	String codeSql = CREATE_CODE_VALUE_GENERAL_CODE_SQL;
	
	ArrayList<Object>  paramList = new ArrayList<Object> ();
	 paramList.add(codeValGenDT.getCodeSetNm().toUpperCase());
	 paramList.add(codeValGenDT.getCode());
	 paramList.add(codeValGenDT.getCodeDescTxt());
	 paramList.add(codeValGenDT.getCodeShortDescTxt());
	 paramList.add(codeValGenDT.getCodeSystemCd());
	 paramList.add(codeValGenDT.getCodeSystemDescTxt());
	 paramList.add(codeValGenDT.getIndentLevelNbr());
	 paramList.add(codeValGenDT.getParentIsCd());
	 paramList.add(codeValGenDT.getConceptTypeCd());
	 paramList.add(codeValGenDT.getConceptCode());
	 paramList.add(codeValGenDT.getSourceConceptId());
	 paramList.add(codeValGenDT.getSuperCodeSetNm());
	 paramList.add(codeValGenDT.getSuperCode());
	 paramList.add(codeValGenDT.getStatusCd());
	 paramList.add(new Timestamp(new Date().getTime()));
	 paramList.add(codeValGenDT.getLocalEffectiveFromTime());
	 paramList.add("");
	 paramList.add(codeValGenDT.getIsModifiableInd());
	 paramList.add(codeValGenDT.getConceptNm());
	 paramList.add(codeValGenDT.getConceptPreferredNm());
	 paramList.add(codeValGenDT.getConceptStatusCd());
	 paramList.add(new Timestamp(new Date().getTime()));
	 paramList.add(codeValGenDT.getCodeSystemVersionNbr());
	 paramList.add(codeValGenDT.getConceptOrderNbr());
	 paramList.add(codeValGenDT.getAdminComments());
	 Timestamp now = new Timestamp(System.currentTimeMillis());
	 paramList.add(now);
	 paramList.add(codeValGenDT.getAddUserId());
	 paramList.add(codeValGenDT.getLocalEffectiveToTime());
	 
	 try {			
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.CREATE, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in insertCodeValueGeneral: ERROR = "+codeValGenDT.toString() , ex);
			throw new NEDSSSystemException(ex.toString());
		}	
	
	}

public void UpdateCodeValueGeneralForOth(CodeValueGeneralDT codeValGenDT)
{

	
	String codeSql =UPDATE_CODE_VAL_GEN_OTH_CODE_SQL;
	
	ArrayList<Object>  paramList = new ArrayList<Object> ();
	paramList.add("OTH");
	paramList.add(codeValGenDT.getConceptCode());
	paramList.add(codeValGenDT.getCodeSetNm().toUpperCase());	
	try {			
		preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
		
	} catch (Exception ex) {
		logger.fatal("Exception in UpdateCodeValueGeneralForOth: ERROR = " +codeValGenDT.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	}		

}

private void insertCodeSet(CodeValueGeneralDT codeValGenDT) {

		Connection dbConnection = null;
		dbConnection = getConnection();
		PreparedStatement preparedStmt2 = null;
		Long groupId=null;
		String codeSql = "";
		ArrayList<Object>  paramList = new ArrayList<Object> ();
		//Need to get the groupId for the same CodesetNm.
		codeSql = SELECT_CODE_SET_GROUP_ID_SQL;
			int i=1;
	    			try {
	    				preparedStmt2 = dbConnection.prepareStatement(codeSql);
	    				preparedStmt2.setString(i++, codeValGenDT.getCodeSetNm());
	    				ResultSet  rs1 = preparedStmt2.executeQuery();
	    				 if (rs1.next()) {
	    				        logger.debug("GroupID = " + rs1.getLong(1));
	    				        groupId=new Long( rs1.getLong(1));
	    				 }
	    			}catch (Exception ex) {
	    				logger.fatal("Exception while getting count of 'code_set_nm' : ERROR = " + ex);
	    				throw new NEDSSSystemException(ex.toString());
	    			} finally {
	    				paramList = new ArrayList<Object> ();
	    	}
	    String CodeDescTxt=null;
		codeSql = GET_CODE_DESC_TXT_SQL;
			int j =1;
				try {
	    				preparedStmt2 = dbConnection.prepareStatement(codeSql);
	    				preparedStmt2.setString(j++, codeValGenDT.getCodeSetNm());
	    				ResultSet  rs1 = preparedStmt2.executeQuery();
	    				 if (rs1.next()) {
	    					 CodeDescTxt= rs1.getString(1); 
	    				 }
	    			}catch (Exception ex) {
	    				logger.fatal("Exception while getting count of 'code_set_nm' : ERROR = " + ex);
	    				throw new NEDSSSystemException(ex.toString());
	    			} finally {
	    				paramList = new ArrayList<Object> ();
	    	}
	codeSql = CREATE_CODESET_SQL;
    
	 paramList.add(codeValGenDT.getCodeSetNm());
	 paramList.add(CodeDescTxt);
	 paramList.add(codeValGenDT.getCodeSystemCd());
	 paramList.add(codeValGenDT.getCodeSystemDescTxt());
	 paramList.add(new Timestamp(new Date().getTime()));
	 paramList.add(new Timestamp(new Date().getTime()));
	 paramList.add(new Timestamp(new Date().getTime()));
	 paramList.add(groupId);
	  try {			
		 preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.CREATE, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in insertCodeSet while inserting into CodeSet: ERROR = " + codeValGenDT.toString(),ex);
			throw new NEDSSSystemException(ex.toString());
		}			
	
}


private Integer duplicateCodesetNm(CodeValueGeneralDT codeValGenDT) {
	ArrayList<Object>  paramList = new ArrayList<Object> ();
	String codeSql = "";
	Integer count = null;
	//Throw exception if duplicate code is entered for the same codeset (Temporary Fix: see civil00015697)
	codeSql = TEMP_CVG_DUPL_CODE_SQL;
	try {			
		paramList.add(codeValGenDT.getCodeSetNm());
		paramList.add(codeValGenDT.getCode());
		count =  (Integer) preparedStmtMethod(codeValGenDT, paramList, codeSql, NEDSSConstants.SELECT_COUNT, NEDSSConstants.SRT);
		//If a duplicate code is found, throw an exception (temp Fix)
		if(count != null && count.intValue() >= 1) {
			logger.error("SRT Administration - Create CodeValueGeneral will not permit to add duplicate codes ");
			throw new NEDSSSystemException("SQLException PK Violated - Duplicate Code " + codeValGenDT.getCode() + " entered for codeset: " + codeValGenDT.getCodeSetNm());
		}		
	} catch (Exception ex) {
		logger.fatal("Exception while duplicateCodesetNm(): ERROR = " +codeValGenDT.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	} finally {
		paramList = new ArrayList<Object> ();
	}	
	return count;
}


}
