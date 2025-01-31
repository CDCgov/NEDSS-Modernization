package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;


import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.LabTestDT;
import gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.util.SRTAdminUtilDAOImpl;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * LabTestDAOImpl finds an existing lab test by name, inserts and updates
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LabTestDAOImpl.java
 * Jun 1, 2008
 * @version
 */
public class LabTestDAOImpl extends DAOBase {

	private static final LogUtils logger = new LogUtils(LabTestDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private static final String FIND_EXISTING_TESTS_SQL = "SELECT lab_test_cd \"labTestCd\" "
			+ ",laboratory_id \"laboratoryId\" "
			+ ",lab_test_desc_txt \"labTestDescTxt\" "
			+ ",test_type_cd \"testTypeCd\" "
			+ ",nbs_uid \"nbsUid\" "
			+ ",effective_from_time \"effectiveFromTime\" "
			+ ",effective_to_time \"effectiveToTime\" "
			+ ",default_prog_area_cd \"defaultProgAreaCd\" "
			+ ",default_condition_cd \"defaultConditionCd\" "
			+ ",drug_test_ind \"drugTestInd\" "
			+ ",organism_result_test_ind \"organismResultTestInd\" "
			+ ",indent_level_nbr \"indentLevelNbr\" "
			+ ",pa_derivation_exclude_cd \"paDerivationExcludeCd\" "
			+ " FROM "
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
			+ "..Lab_test ";

	private static final String CREATE_LABTEST_SQL = "INSERT INTO "
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
			+ "..Lab_test(	lab_test_cd, " + "laboratory_id, "
			+ "lab_test_desc_txt," + "test_type_cd," + "nbs_uid, "
			+ "effective_from_time, " + "effective_to_time, "
			+ "default_prog_area_cd, " + "default_condition_cd, "
			+ "drug_test_ind, " + "organism_result_test_ind, "
			+ "indent_level_nbr, " + "pa_derivation_exclude_cd) " + "VALUES(?, ?, ?, ?, '', ?, ?, ?, ?, ?, ?, ?, ?)";


	private static final String UPDATE_LABTEST_SQL = "UPDATE "
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Lab_test "
			+ "SET 	lab_test_desc_txt=?, " + "default_prog_area_cd=?, "
			+ "default_condition_cd=?, " + "drug_test_ind=?, "
			+ "organism_result_test_ind=?, " + "indent_level_nbr=?, "
			+ "effective_from_time=?, " + "pa_derivation_exclude_cd=? " + "WHERE " + "lab_test_cd=? "
			+ "AND laboratory_id=?";

	
	/**
	 * Returns a Collection<Object>  of LocallyDefined LabTests with a single LabTest Code.
	 * @param searchParams
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> findLocallyDefinedTests(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =FIND_EXISTING_TESTS_SQL;
		LabTestDT labTestDT = new LabTestDT();
		ArrayList<Object>  labTestDTCollection = new ArrayList<Object> ();
		try {
			
			codeSql = codeSql + whereClause;
        
			labTestDTCollection = (ArrayList<Object> ) preparedStmtMethod(labTestDT, labTestDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findLocallyDefinedTest: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return labTestDTCollection;
		
	}
	
	/**
	 * 
	 * @param labTestDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void createLocallyDefinedTest(LabTestDT labTestDT) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =CREATE_LABTEST_SQL;
		
		try {
			
	        String drugInd = labTestDT.getDrugTestInd() == null ? "" : labTestDT.getDrugTestInd();
	        String organismInd = labTestDT.getOrganismResultTestInd() == null ? "" : labTestDT.getOrganismResultTestInd(); 
	        String paDevExcludCd = labTestDT.getPaDerivationExcludeCd() == null ? "" : labTestDT.getPaDerivationExcludeCd();
	        String testTypeCd = labTestDT.getTestTypeCd() == null ? "" : labTestDT.getTestTypeCd();
	        
			ArrayList<Object>  paramList = new ArrayList<Object> ();
			paramList.add(labTestDT.getLabTestCd());
			paramList.add(labTestDT.getLaboratoryId());
			paramList.add(labTestDT.getLabTestDescTxt());
			paramList.add(labTestDT.getTestTypeCd());
			paramList.add(new Timestamp(new Date().getTime()));
			paramList.add(labTestDT.getEffectiveToTime());
			paramList.add(labTestDT.getDefaultProgAreaCd());
			paramList.add(labTestDT.getDefaultConditionCd());
			if(drugInd.equals("1")) paramList.add("Y");
			else  paramList.add("N");
			if(organismInd.equals("1")) paramList.add("Y");
			else  paramList.add("N");
			paramList.add(labTestDT.getIndentLevelNbr());
			
			if(testTypeCd.equals("O"))
				paramList.add("N");
			else if(testTypeCd.equals("R"))
			{
				if(paDevExcludCd.equals("1"))paramList.add("Y");
				else  paramList.add("N");
			}
		
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
	public void updateLocallyDefinedTest(LabTestDT labTestDT) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =UPDATE_LABTEST_SQL;
		
		try {
						
	        String drugInd = labTestDT.getDrugTestInd();
	        String organismInd = labTestDT.getOrganismResultTestInd(); 
	       // String paDerExcludeCd = labTestDT.getPaDerivationExcludeCd(); 
	        String paDerExcludeCd = labTestDT.getPaDerivationExcludeCd() == null ? "" : labTestDT.getPaDerivationExcludeCd();
	        String testTypeCd = labTestDT.getTestTypeCd(); 
	        
			ArrayList<Object>  paramList = new ArrayList<Object> ();
			paramList.add(labTestDT.getLabTestDescTxt());
			paramList.add(labTestDT.getDefaultProgAreaCd());
			paramList.add(labTestDT.getDefaultConditionCd());
			if(drugInd.equals("1")) paramList.add("Y");
			else  paramList.add("N");
			if(organismInd.equals("1")) paramList.add("Y");
			else  paramList.add("N");
			paramList.add(labTestDT.getIndentLevelNbr());
			paramList.add(new Timestamp(new Date().getTime()));
			if(testTypeCd.equals("O"))
				paramList.add("N");
			else if(testTypeCd.equals("R"))
			{
				if(paDerExcludeCd.equals("1")) paramList.add("Y");
				else  paramList.add("N");
			}
			paramList.add(labTestDT.getLabTestCd());
			paramList.add(labTestDT.getLaboratoryId());
		
					
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findLocallyDefinedTest: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
	}
	
	
	
	
}