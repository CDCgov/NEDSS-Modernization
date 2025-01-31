package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PhinVadsSystemVO;
import gov.cdc.nedss.srtadmin.dt.CodeSetDT;
import gov.cdc.nedss.srtadmin.dt.CodeSetGpMetaDataDT;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ManageCodeSetDAOImpl extends DAOBase{
	
	private static final LogUtils logger = new LogUtils(ManageCodeSetDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private static final String GET_EXISTING_CODE_SET_SQL = "SELECT Codeset_Group_Metadata.code_set_nm \"codeSetNm\", " +
		" Codeset_Group_Metadata.code_set_group_id \"codeSetGroupId\", " +
		" Codeset_Group_Metadata.vads_value_set_code \"vadsValueSetCode\"," + 
	    " Codeset_Group_Metadata.code_set_desc_txt \"codeSetDescTxt\", " +
	    " Codeset_Group_Metadata.code_set_short_desc_txt \"codeSetShortDescTxt\"," + 
	    " Codeset_Group_Metadata.ldf_picklist_ind_cd \"ldfPickListInd\"," + 
	    " Codeset_Group_Metadata.phin_std_val_ind \"phinStadValueSetInd\"," + 
	   
	    " Codeset.assigning_authority_cd  \"assigningAuthorityCd\", " +
	    " Codeset.assigning_authority_desc_txt \"assigningAuthorityDescTxt\", " +
	   
	    " Codeset.class_cd \"classCD\", " +
	    " Codeset.effective_to_time \"effectiveFromTime\", " +
	    " Codeset.effective_from_time  \"effectiveFromTime\", " +
	    " Codeset.is_modifiable_ind \"isModifiableInd\"," + 
	    " Codeset.nbs_uid \"nbsUid\", " +
	    " Codeset.source_version_txt \"sourceVersionTxt\", " +
	    " Codeset.source_domain_nm \"sourceDomainNm\", " +
	    " Codeset.status_cd \"statusCd\", " +
	    " Codeset.status_to_time\"statusTime\", "+
	    
	    " Codeset.admin_comments\"adminComments\", "+
	    " Codeset.value_set_nm\"valueSetNm\", "+
	    " Codeset.ldf_picklist_ind_cd\"ldfPicklistIndCd\", "+
	    " Codeset.value_set_code\"valueSetCode\", "+
	    " Codeset.value_set_type_cd\"valueSetTypeCd\", "+
	    " Codeset.value_set_oid\"valueSetOid\", "+
	    " Codeset.value_set_status_cd\"valueSetStatusCd\", "+
	    " Codeset.value_set_status_time\"valueSetStatusTime\", "+
	    " Codeset.parent_is_cd\"parentIsCd\", "+
	    " Codeset.code_set_desc_txt\"codeSetDescTxt\", "+
	    " Codeset.add_time\"addTime\", "+
	    " Codeset.add_user_id\"addUserId\" "+
	    
	    " FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Codeset_Group_Metadata INNER JOIN "
	    + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Codeset ON Codeset_Group_Metadata.code_set_group_id = Codeset.code_set_group_id and class_cd='code_value_general'";


	
	
	

	private static final String UPDATE_CODESET_SQL = "UPDATE "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..CodeSet "
		+ "SET  assigning_authority_cd=?, "
		+ "assigning_authority_desc_txt=?, code_set_desc_txt=?, " +
		"class_cd=?, " +
		"effective_from_time=?, " +
		"effective_to_time=?, " +
		"is_modifiable_ind=?, " +
		"nbs_uid=?, " +
		"source_version_txt=?, " +
		"source_domain_nm=?, " +
		"status_cd=?, " +
		"status_to_time=?, " +
		"code_set_group_id=?, " +
		"value_set_type_cd=?, " +
		"value_set_code=?, " +
		"value_set_nm=?, " +
		"add_time=?, " +
		"add_user_id=? " +
		" WHERE code_set_nm=? and class_cd='code_value_general'";

	
	
	private static final String UPDATE_CODESET_GR_METADATA_SQL = "UPDATE "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Codeset_Group_Metadata " 
		+ "SET code_set_desc_txt=?, code_set_group_id=?, vads_value_set_code=?, code_set_short_desc_txt=?, ldf_picklist_ind_cd=?, phin_std_val_ind=? "
		+ "WHERE code_set_nm=? and code_set_group_id=?";
	

	private static final String CREATE_CODESET_SQL = "INSERT INTO "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
		+ "..Codeset(code_set_nm, assigning_authority_cd, assigning_authority_desc_txt, code_set_desc_txt, class_cd, effective_from_time, effective_to_time, is_modifiable_ind, nbs_uid, source_version_txt, source_domain_nm, status_cd, status_to_time, code_set_group_id, admin_comments, value_set_nm, ldf_picklist_ind_cd, value_set_code, value_set_type_cd, value_set_oid, value_set_status_cd, value_set_status_time,parent_is_cd,add_time,	add_user_id) "
		+ " VALUES(?,?,?,?,'code_value_general',?,?,?,'',?,?,'A',?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	
	private static final String CREATE_CODESET_GROUP_META_DATA_SQL = "INSERT INTO "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
		+ "..Codeset_Group_Metadata(code_set_nm,code_set_group_id,vads_value_set_code,code_set_desc_txt,code_set_short_desc_txt,ldf_picklist_ind_cd,phin_std_val_ind) "
		+ " VALUES(?,?,?,?,?,?,?) ";
	private static final String SELECT_CODE_SET_GROUP_ID_SQL = "SELECT MAX(code_set_group_id) FROM nbs_srte..Codeset_Group_Metadata ";
	private static final String DUPL_CODESETNM_SQL = "select count(*) from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Codeset where CODE_SET_NM=? and CLASS_CD='code_value_general'";
	private static final String GET_CODE_SET_GROUP_ID_SQL ="SELECT code_set_group_id FROM  NBS_SRTE..CODESET where code_set_group_id > 99900";
	
	private static final String GET_CODE_SET_NM_SQL ="SELECT count(*) FROM "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Codeset_Group_Metadata where code_set_nm=?";
	
	private static final String UPDATE_CODESET_STATUS_SQL ="UPDATE "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..CodeSet SET status_cd=?, status_to_time=? WHERE CODE_SET_NM=? and CLASS_CD='code_value_general'";
		
	
	private static final String SELECT_COUNT_NM_SQL = "select count(*) from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Codeset_Group_Metadata metadata,"
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..CodeSet codeSet WHERE metadata.code_set_short_desc_txt=? and codeSet.CLASS_CD='code_value_general' and " +
					"metadata.code_set_nm = codeSet.code_set_nm";
		/**
	 * 
	 * @param searchParams
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> getExistingCodeSets() throws NEDSSDAOSysException, NEDSSSystemException {
		
		
		String codeSql = GET_EXISTING_CODE_SET_SQL;
        
        CodeSetDT codeSetDT = new CodeSetDT();
		ArrayList<Object>  codeSetDTCollection = new ArrayList<Object> ();
		
		
		try {			
			codeSetDTCollection = (ArrayList<Object> ) preparedStmtMethod(codeSetDT, codeSetDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in getExistingCodeSets: ERROR = " + codeSetDT.toString(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return codeSetDTCollection;
		
	}	
	
	
	/**
	 * 
	 * @param codeSetDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateCodeSet(CodeSetDT codeSetDT) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =UPDATE_CODESET_SQL;
		
		
			ArrayList<Object>  paramList = new ArrayList<Object> ();
			Timestamp now = new Timestamp(System.currentTimeMillis());
			paramList.add(codeSetDT.getAssigningAuthorityCd());
			paramList.add(codeSetDT.getAssigningAuthorityDescTxt());
			paramList.add(codeSetDT.getCodeSetDescTxt());
			paramList.add(codeSetDT.getClassCD());
			paramList.add(codeSetDT.getEffectiveFromTime());
			paramList.add(codeSetDT.getEffectiveToTime());
			paramList.add(codeSetDT.getIsModifiableInd());
			paramList.add(codeSetDT.getNbsUid());
			paramList.add(codeSetDT.getSourceVersionTxt());
			paramList.add(codeSetDT.getSourceDomainNm());
			paramList.add(codeSetDT.getStatusCd());
			paramList.add(new Timestamp(new Date().getTime()));
			paramList.add(codeSetDT.getCodeSetGroupId());
			paramList.add(codeSetDT.getValueSetTypeCd());
			paramList.add(codeSetDT.getValueSetCode());
			paramList.add(codeSetDT.getValueSetNm());
			paramList.add(now);
			paramList.add(codeSetDT.getAddUserId());
			paramList.add(codeSetDT.getCodeSetNm());
			
		
		try {			
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
			
		}
		catch (Exception ex) {
			logger.fatal("Error while updateCodeSet codeset, codeSetDT = " + codeSetDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
	}
	
	
	
	/**
	 * 
	 * @param codeSetDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateCodeSetGRMetadata(CodeSetGpMetaDataDT codeSetMetadataDt) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =UPDATE_CODESET_GR_METADATA_SQL;
		
		
			ArrayList<Object>  paramList = new ArrayList<Object> ();
			paramList.add(codeSetMetadataDt.getCodeSetDescTxt());
			paramList.add(codeSetMetadataDt.getCodeSetGroupId());
			paramList.add(codeSetMetadataDt.getVads_value_set_code());
			paramList.add(codeSetMetadataDt.getCodeSetShortDescTxt());
			paramList.add(codeSetMetadataDt.getLdfPicklistIndCd());
			paramList.add(codeSetMetadataDt.getPhinStadValueSetInd());
			paramList.add(codeSetMetadataDt.getCodeSetNm());
			paramList.add(codeSetMetadataDt.getCodeSetGroupId());
			
		try {			
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE,NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Error while updateCodeSetGRMetadata into codeset_Group_metadata, codeSetMetadataDt = " + codeSetMetadataDt.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
	}
	public void createCodeSet(CodeSetDT codeSetDT,CodeSetGpMetaDataDT codeSetGpMetaDataDT){
		  Long groupId=null;
		  Integer count = null;
		  String codeSql = "";
		  ArrayList<Object>  paramList = new ArrayList<Object> ();
		  Connection dbConnection = null;
		  /*dbConnection = getConnection();*/
		  PreparedStatement preparedStmt2 = null;
		  ResultSet rs = null;
		  
		  Map<Object, Object> errorMap = new HashMap<Object, Object>();
		  boolean isError=false;
		   
		 //Throw exception if duplicate codeset is entered  
			codeSql = DUPL_CODESETNM_SQL;
			try {			
				paramList.add(codeSetDT.getCodeSetNm());
				count =  (Integer) preparedStmtMethod(codeSetDT, paramList, codeSql, NEDSSConstants.SELECT_COUNT, NEDSSConstants.SRT);
				//If a duplicate codeset is found, throw an exception 
				if(count != null && count.intValue() >= 1) {
					logger.error("SRT Administration - Create CodeSet will not permit to add duplicate codesetNm");
					throw new NEDSSSystemException("SQLException PK Violated - Duplicate Code " + codeSetDT.getCodeSetNm() + " entered for codeset: " );
				}		
			} catch (Exception ex) {
				logger.fatal("Exception while getting count of 'code_set_nm' : ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			} finally {
				paramList = new ArrayList<Object> ();
			}
		//Throw exception if duplicate valuesetNm is entered  	
			int countRet = 0;
			countRet = uniqueCodesetNm(codeSetDT);
			try{
				if(countRet >= 1)
				{
					logger.error("SRT Administration - Create CodeSet will not permit to add duplicate ValueSetName");
					throw new NEDSSSystemException("SRT Administration - Create CodeSet will not permit to add duplicate ValueSetName");
				}
			}catch (Exception ex) {
				logger.fatal("Exception while getting count of 'Value Set Name' : ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			} 
			
		//CodeSet GroupId need to start with 99910.If it is there need to increase by 10 for max,if not set it to 99910.
			codeSql = GET_CODE_SET_GROUP_ID_SQL;
			try {
				dbConnection = getConnection();
				preparedStmt2 = dbConnection.prepareStatement(codeSql);
				rs = preparedStmt2.executeQuery();
				 if (rs.next()) {
				        logger.debug("GroupID = " + rs.getLong(1));
				        groupId=new Long( rs.getLong(1));
			      }
				closeResultSet(rs);
 	            closeStatement(preparedStmt2);
 	            
				if(groupId !=null){
					ResultSet rs1 = null;
					PreparedStatement preparedStmt3 = null;
					codeSql = SELECT_CODE_SET_GROUP_ID_SQL;
					    			try {
					    				preparedStmt3 = dbConnection.prepareStatement(codeSql);
			
					    				rs1 = preparedStmt3.executeQuery();
					    				 if (rs1.next()) {
					    				        logger.debug("GroupID = " + rs1.getLong(1));
					    				        groupId=new Long( rs1.getLong(1));
					    				 }
					    			}catch (Exception ex) {
					    				logger.fatal("Exception while getting count of 'code_set_nm' : ERROR = " + ex);
					    				throw new NEDSSSystemException(ex.toString());
					    			} finally {
					    				paramList = new ArrayList<Object> ();
					    				closeResultSet(rs1);
					    			    closeStatement(preparedStmt3);
					    			}
			    			long intgroupId = groupId.longValue()+10;
					    	Long CdgroupId = new Long(intgroupId);
					    	codeSetGpMetaDataDT.setCodeSetGroupId(CdgroupId);
					    	insertCodeSetGroupMetaData(codeSetGpMetaDataDT);
					    	//seq_num is always '1'
						//	codeSetDT.setSeqNum(new Integer(1));
		    				codeSetDT.setCodeSetGroupId(CdgroupId);
		    				insertCodeSet(codeSetDT);
		    				
		    	    	}
		    	    		else {
		    				Long CdgroupId = new Long("99910");
		    				codeSetGpMetaDataDT.setCodeSetGroupId(CdgroupId);
					    	insertCodeSetGroupMetaData(codeSetGpMetaDataDT);
		    				//seq_num is always '1'
		    				//codeSetDT.setSeqNum(new Integer(1));
		    				codeSetDT.setCodeSetGroupId(CdgroupId);
		    				insertCodeSet(codeSetDT);		    				
		    	    	}
			    			}catch (Exception ex) {
			    				logger.fatal("Exception while getting count of 'code_set_nm' : ERROR = " + ex.getMessage(), ex);
			    				throw new NEDSSSystemException(ex.toString());
			    			} finally {
			    				paramList = new ArrayList<Object> ();
			    				
			    	            releaseConnection(dbConnection);
			    			}
   }
	
	private int uniqueCodesetNm(CodeSetDT codesetDT) {
		
		ArrayList<Object>  paramList = new ArrayList<Object> ();
		int count =0;
		Integer intCount = null;
		String codeSql = "";
		codeSql = SELECT_COUNT_NM_SQL;
			try {			
			paramList.add(codesetDT.getCodeSetShortDescTxt());
			intCount = (Integer) preparedStmtMethod(codesetDT, paramList, codeSql, NEDSSConstants.SELECT_COUNT, NEDSSConstants.SRT);
			count = intCount.intValue();
		} catch (Exception ex) {
			logger.fatal("Exception while getting count of uniqueCodesetNm: ERROR = " + codesetDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			paramList = new ArrayList<Object> ();
		}
		return count;
	}
   
	   private void insertCodeSet(CodeSetDT codeSetDT) {
			
			String codeSql = CREATE_CODESET_SQL;
			 ArrayList<Object>  paramList = new ArrayList<Object> ();
		
			 paramList.add(codeSetDT.getCodeSetNm().toUpperCase());
			 paramList.add(codeSetDT.getAssigningAuthorityCd());
			 paramList.add(codeSetDT.getAssigningAuthorityDescTxt());
			 paramList.add(codeSetDT.getCodeSetDescTxt());
			 paramList.add(new Timestamp(new Date().getTime()));
			 paramList.add(new Timestamp(new Date().getTime()));
			 paramList.add(codeSetDT.getIsModifiableInd());
			 paramList.add(codeSetDT.getSourceVersionTxt());
			 paramList.add(codeSetDT.getSourceDomainNm());
			 paramList.add(new Timestamp(new Date().getTime()));
			 paramList.add(codeSetDT.getCodeSetGroupId());
			 paramList.add(codeSetDT.getAdminComments());
			 paramList.add(codeSetDT.getValueSetNm());
			 paramList.add(codeSetDT.getLdfPicklistIndCd());
			 paramList.add(codeSetDT.getValueSetCode().toUpperCase());
			 paramList.add(codeSetDT.getValueSetTypeCd());
			 paramList.add(codeSetDT.getValueSetOid());
			 paramList.add(codeSetDT.getValueSetStatusCd());
			 paramList.add(codeSetDT.getValueSetStatusTime());
			 paramList.add(codeSetDT.getParentIsCd());
			 Timestamp now = new Timestamp(System.currentTimeMillis());
			 paramList.add(now);
			 paramList.add(codeSetDT.getAddUserId());
			 
			 
			 
			 try {			
					preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.CREATE, NEDSSConstants.SRT);
					
				} catch (Exception ex) {
					logger.fatal("Error while insertCodeSet into codeset, codeSetDT = " + codeSetDT.toString(), ex);
					throw new NEDSSSystemException(ex.toString());
				}	
			
			}
	   private void insertCodeSetGroupMetaData(CodeSetGpMetaDataDT codeSetGpMetaDataDT) {
			
			String codeSql = CREATE_CODESET_GROUP_META_DATA_SQL;
	
			 ArrayList<Object>  paramList = new ArrayList<Object> ();
			 paramList.add(codeSetGpMetaDataDT.getCodeSetNm().toUpperCase());
			 paramList.add(codeSetGpMetaDataDT.getCodeSetGroupId());
			 paramList.add(codeSetGpMetaDataDT.getVads_value_set_code().toUpperCase());
			 paramList.add(codeSetGpMetaDataDT.getCodeSetDescTxt());
			 paramList.add(codeSetGpMetaDataDT.getCodeSetShortDescTxt());
			 paramList.add(codeSetGpMetaDataDT.getLdfPicklistIndCd());
			 paramList.add(codeSetGpMetaDataDT.getPhinStadValueSetInd());
			 
			 try {			
					preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.CREATE, NEDSSConstants.SRT);
					
			 } catch (Exception ex) {
				 logger.fatal("Error while insertCodeSetGroupMetaData into codeset_Group_metadata, codeSetGpMetaDataDT = " + codeSetGpMetaDataDT.toString(), ex);
					throw new NEDSSSystemException(ex.toString());
				}	
			
			}
	   public boolean checkVADSInSystem(CodeSetDT codeSetDt)
	   {
		   boolean returnValue = false;
		   ArrayList<Object>  codeSetDTCollection = new ArrayList<Object> ();
		   
		   String codeSql = GET_EXISTING_CODE_SET_SQL;
		   codeSql = codeSql + " AND Codeset_Group_Metadata.vads_value_set_code = ?";
		   CodeSetDT codeSetDT = new CodeSetDT();
		   codeSetDTCollection.add(codeSetDt.getValueSetCode().toUpperCase());
			try {			
				codeSetDTCollection = (ArrayList<Object> ) preparedStmtMethod(codeSetDT, codeSetDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
				if(codeSetDTCollection.size()>0)
					returnValue = true;
				
			} catch (Exception ex) {
				logger.fatal("Exception in checkVADSInSystem: ERROR = " + codeSetDt.toString(),ex);
				throw new NEDSSSystemException(ex.toString());
			}
			return returnValue;
	   }
	   
	   private boolean checkCodeSetMetaDataForCodeSetNm(CodeSetDT codesetDt)
	   {
		   boolean isCodesetMetaData = false;
		   Integer count = null;
		   ArrayList<Object>  paramList = new ArrayList<Object> ();
		   String codeSql = GET_CODE_SET_NM_SQL;
			
			try {			
				paramList.add(codesetDt.getCodeSetNm());
				count =  (Integer) preparedStmtMethod(codesetDt, paramList, codeSql, NEDSSConstants.SELECT_COUNT, NEDSSConstants.SRT);
				//If a duplicate codeset is found, throw an exception 
				if(count != null && count.intValue() >= 1) {
					isCodesetMetaData = true;
				}		
			} catch (Exception ex) {
				logger.fatal("Exception while getting count of checkCodeSetMetaDataForCodeSetNm() : ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
			return isCodesetMetaData;
	   }
	   public void importValueSet(PhinVadsSystemVO phinVadsVo)
	   {
		   CodeValueGeneralDAOImpl codeValueGeneralDAOImpl = new CodeValueGeneralDAOImpl();
		   Long codeSetGrId = null;
		   try {
			   // get codeset_group_id
			   
			   CodeSetDT codesetDt = phinVadsVo.getCodeSetDT();
			   codeSetGrId = getCodesetGroupId(codesetDt);
					  
			 //Insert into codeset_group_metadata table
			   CodeSetGpMetaDataDT codesetGrMetaDataDt = phinVadsVo.getCodeSetGpMetaDataDT();
			   codesetGrMetaDataDt.setCodeSetGroupId(codeSetGrId);
			   insertCodeSetGroupMetaData(codesetGrMetaDataDt);
			   
			 //Insert into codeset table
			  
			   codesetDt.setCodeSetGroupId(codeSetGrId);
			   insertCodeSet(codesetDt);
				  
			   
			  // createCodeSet(phinVadsVo.getCodeSetDT(), phinVadsVo.getCodeSetGpMetaDataDT());
			   // insert into code_value_general table
			   ArrayList<Object> codeValueGenaralDtCollection = phinVadsVo.getTheCodeValueGenaralDtCollection();
			   if(codeValueGenaralDtCollection.size()>0){
				   Iterator<Object> iter = codeValueGenaralDtCollection.iterator();
				   while(iter.hasNext())
				   {
					   CodeValueGeneralDT codeValueGeneralDt = (CodeValueGeneralDT)iter.next();
					   codeValueGeneralDAOImpl.insertCodeValueGeneral(codeValueGeneralDt);
					   if(codeValueGeneralDt.getConceptCode() != null && codeValueGeneralDt.getConceptCode().equals("OTH"))
						   codeValueGeneralDAOImpl.UpdateCodeValueGeneralForOth(codeValueGeneralDt);
				   }
			   }
		   }catch (Exception ex) {
				logger.fatal("Exception in checkVADSInSystem: ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
		   
	   }
	   
	   private Long getCodesetGroupId(CodeSetDT codesetDt)
	   {
			  Long groupId=null;
			  long intgroupId = 0;
			  String codeSql = "";
			  Connection dbConnection = null;
			  PreparedStatement preparedStmt2 = null;
			  ResultSet rs = null;
			  try{
					dbConnection = getConnection();
			  }catch(NEDSSSystemException nsex){
				  logger.fatal("SQLException while obtaining database connection for getCodesetGroupId ", nsex);
				  throw new NEDSSSystemException(nsex.toString());
			  }
			  
			//CodeSet GroupId need to start with 99910.If it is there need to increase by 10 for max,if not set it to 99910.
				codeSql = GET_CODE_SET_GROUP_ID_SQL;
				try {
					preparedStmt2 = dbConnection.prepareStatement(codeSql);
					rs = preparedStmt2.executeQuery();
					 if (rs.next()) {
					        logger.debug("GroupID = " + rs.getLong(1));
					        groupId=new Long( rs.getLong(1));
				      }
					
	 	            
					if(groupId !=null){
						ResultSet rs1 = null;
						PreparedStatement preparedStmt3 = null;
						codeSql = SELECT_CODE_SET_GROUP_ID_SQL;
						    			try {
						    				preparedStmt3 = dbConnection.prepareStatement(codeSql);
				
						    				rs1 = preparedStmt3.executeQuery();
						    				 if (rs1.next()) {
						    				        logger.debug("GroupID = " + rs1.getLong(1));
						    				        groupId=new Long( rs1.getLong(1));
						    				 }
						    			}catch (Exception ex) {
						    				logger.fatal("Exception while getting count of 'code_set_nm' : ERROR = " + ex);
						    				throw new NEDSSSystemException(ex.toString());
						    			} finally {
						    				closeResultSet(rs1);
						    			    closeStatement(preparedStmt3);
						    			}
						    			intgroupId = groupId.longValue()+10;
						    			groupId = new Long(intgroupId);
					}
    	    		else {
    				groupId = new Long("99910");
    	    		}
		   }catch (Exception ex) {
				logger.fatal("Exception while getting count of 'code_set_nm' : ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
		   } finally {
				closeResultSet(rs);
	            closeStatement(preparedStmt2);
	            releaseConnection(dbConnection);
		   }
		   return groupId;
	   }
	   
	   public void inactivateCodeset(String codesetNm)
	   {
		   try{
			   logger.debug(" in Codeset DAO - inactivating codeset ..");
			   updateCodesetStatus( codesetNm, gov.cdc.nedss.util.NEDSSConstants.STATUS_INACTIVE);
		   }catch(Exception ex){
			   logger.fatal("Exception = "+ex.getMessage(), ex);
			   throw new NEDSSSystemException(ex.toString());
		   }
	   }
	   
	   public void activateCodeset(String codesetNm)
	   {
		   try{
			   logger.debug(" in Codeset DAO - activating codeset ..");
			   updateCodesetStatus( codesetNm, gov.cdc.nedss.util.NEDSSConstants.STATUS_ACTIVE);
		   }catch(Exception ex){
			   logger.fatal("Exception = "+ex.getMessage(), ex);
			   throw new NEDSSSystemException(ex.toString());
		   }
	   }
	   
	   
	   /**
	    * method updateConditionStatus -
	    *  To Activate, status is set to Active.
	    * @param conditionCd
	    * @param newStatus - either I or A
	    * @throws NEDSSDAOSysException
	    * @throws NEDSSSystemException
	    */
	   private void updateCodesetStatus(String codesetNm, String newStatus) throws NEDSSDAOSysException, NEDSSSystemException {

	   	logger.debug(" in Codeset DAO - updating codeset status to " + newStatus + "for " + codesetNm);

	   	String codeSql =UPDATE_CODESET_STATUS_SQL;

	   	ArrayList<Object>  paramList = new ArrayList<Object> ();
	   	paramList.add(newStatus);
	   	paramList.add (new Timestamp(new Date().getTime()));
	   	paramList.add(codesetNm);

	   	try {
	   		preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);

	   	} catch (Exception ex) {
	   		logger.fatal("codesetNm: "+codesetNm+", newStatus: "+newStatus+"Exception in Update codeset Status: ERROR = " + ex.getMessage(), ex);
	   		throw new NEDSSSystemException(ex.toString());
	   	}

	   	logger.debug(" ...leaving Codeset DAO - updated codeset status ..");
	   } //updateCodesetStatus
}
