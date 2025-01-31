package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;


import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.LabCodingSystemDT;
import gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.util.SRTAdminUtilDAOImpl;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class LabCodingSystemDAOImpl extends DAOBase {

	private static final LogUtils logger = new LogUtils(LabCodingSystemDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private static final String FIND_LABORATORY_SQL = "SELECT 	laboratory_id \"laboratoryId\", "
			+ "laboratory_system_desc_txt \"laboratorySystemDescTxt\", "
			+ "coding_system_cd \"codingSystemCd\", "
			+ "code_system_desc_txt \"codeSystemDescTxt\", "
			+ "electronic_lab_ind \"electronicLabInd\", "
			+ "effective_from_time \"effectiveFromTime\", "
			+ "effective_to_time \"effectiveToTime\", "
			+ "assigning_authority_cd \"assigningAuthorityCd\", "
			+ "assigning_authority_desc_txt \"assigningAuthorityDescTxt\", "
			+ "nbs_uid \"nbsUid\" "
			+ "FROM "
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Lab_coding_system ";

		private static final String CREATE_LABORATORY_SQL = "INSERT INTO "
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
			+ "..Lab_coding_system(laboratory_id, laboratory_system_desc_txt, coding_system_cd, code_system_desc_txt, electronic_lab_ind, effective_from_time,assigning_authority_cd, assigning_authority_desc_txt, nbs_uid) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, '')";


	private static final String UPDATE_LABORATORY_SQL = "UPDATE "
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Lab_coding_system "
			+ "SET laboratory_system_desc_txt=?, coding_system_cd=?, code_system_desc_txt=?, "
			+ "assigning_authority_cd=?, assigning_authority_desc_txt=?, "
			+ "electronic_lab_ind=? WHERE laboratory_id=?";


	
	/**
	 * Returns a Collection<Object> of Laboratories (CLIAs) searching by laboratory_system_desc_txt
	 * @param searchParams
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> findLaboratory(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =FIND_LABORATORY_SQL;
		ArrayList<Object>  labCodingSystemCollection = new ArrayList<Object> ();
		try {
	       codeSql = codeSql + whereClause;
	        
	        LabCodingSystemDT labCodingSystemDT = new LabCodingSystemDT();
			
			labCodingSystemCollection = (ArrayList<Object> ) preparedStmtMethod(labCodingSystemDT, labCodingSystemCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findLaboratory: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return labCodingSystemCollection;
		
	}
	
	/**
	 * 
	 * @param LabCodingSystemDT dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void createLaboratory(LabCodingSystemDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =CREATE_LABORATORY_SQL;
		try {
			
	        
	        
			ArrayList<Object>  paramList = new ArrayList<Object> ();
			paramList.add(dt.getLaboratoryId());
			paramList.add(dt.getLaboratorySystemDescTxt());
			paramList.add(dt.getCodingSystemCd());
			paramList.add(dt.getCodeSystemDescTxt());
			paramList.add(dt.getElectronicLabInd());
			paramList.add(new Timestamp(new Date().getTime()));
			paramList.add(dt.getAssigningAuthorityCd());
			paramList.add(dt.getAssigningAuthorityDescTxt());

					
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in createLaboratory: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
	}

	/**
	 * 
	 * @param LabCodingSystemDT dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateLaboratory(LabCodingSystemDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =UPDATE_LABORATORY_SQL;
		try {
	        
			ArrayList<Object>  paramList = new ArrayList<Object> ();
			paramList.add(dt.getLaboratorySystemDescTxt());
			paramList.add(dt.getCodingSystemCd());
			paramList.add(dt.getCodeSystemDescTxt());
			paramList.add(dt.getAssigningAuthorityCd());
			paramList.add(dt.getAssigningAuthorityDescTxt());
			paramList.add(dt.getElectronicLabInd());
			paramList.add(dt.getLaboratoryId());
		
					
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in updateLaboratory: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
	}
	
	
	
	
}