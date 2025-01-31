package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.LabTestDT;
import gov.cdc.nedss.srtadmin.dt.LabTestLoincDT;
import gov.cdc.nedss.srtadmin.dt.LoincDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * LabTestLoincDAOImpl finds an existing labtest loinc link  by component name, inserts new link.
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LabTestLoincDAOImpl.java
 * Jun 1, 2008
 * @version
 */
public class LabTestLoincDAOImpl extends DAOBase {

	private static final LogUtils logger = new LogUtils(LabTestLoincDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	
	private static final String FIND_EXISTING_LINK_SQL = "SELECT Labtest_loinc.lab_test_cd \"labTestCd\", "
			+ "Labtest_loinc.laboratory_id  \"laboratoryId\", "
			+ "Labtest_loinc.loinc_cd \"loincCd\" "
			+ "FROM "
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
			+ "..Labtest_loinc INNER JOIN "
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
			+ "..Lab_test ON Labtest_loinc.lab_test_cd = Lab_test.lab_test_cd AND Labtest_loinc.laboratory_id = Lab_test.laboratory_id "
			+ "WHERE     (Lab_test.test_type_cd = 'R') AND ";

	
	private static final String CREATE_LINK_SQL = "INSERT INTO "
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
			+ "..Labtest_loinc(	lab_test_cd, "
			+ "laboratory_id, loinc_cd, status_cd, status_time, effective_from_time) values(?,?,?,'A',?,?);";


	private static final String FIND_LOINC_SQL = " SELECT distinct loinc_cd \"loincCd\", component_name \"componentName\" FROM "
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..LOINC_code ";

	private static final String FIND_LABTEST_SQL = "SELECT distinct lab_test_cd \"labTestCd\", lab_test_desc_txt \"labTestDescTxt\", laboratory_id \"laboratoryId\" FROM "
			+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Lab_test where test_type_cd='R' ";

	
	private static final String FIND_MANAGE_LOINC_SQL = " SELECT distinct loinc_cd \"loincCd\", component_name \"componentName\",property \"property\",time_aspect \"time_aspect\",system_cd \"system_cd\",scale_type \"scale_type\",method_type \"method_type\",display_nm \"display_name\",related_class_cd \"related_class_cd\",pa_derivation_exclude_cd \"paDerivationExcludeCd\" FROM "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..LOINC_code ";

	/**
	 * Returns a Collection<Object> of LocallyDefined LabTestLoincDTs with a single LabTest Loinc.
	 * @param searchParams
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> findLabTestLoincLink(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =FIND_EXISTING_LINK_SQL;
		ArrayList<Object>  labTestLoincDTCollection = new ArrayList<Object> ();
		try {
			
	        codeSql = codeSql + whereClause;
	        
	        LabTestLoincDT labTestLoincDT = new LabTestLoincDT();
			
			labTestLoincDTCollection = (ArrayList<Object> ) preparedStmtMethod(labTestLoincDT, labTestLoincDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findLabTestLoincLink: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return labTestLoincDTCollection;
		
	}
	
	/**
	 * 
	 * @param labTestLoincDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void createLabTestLoincLink(LabTestLoincDT labTestLoincDT) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =CREATE_LINK_SQL;
		
		try {

	        
			ArrayList<Object>  paramList = new ArrayList<Object> ();
			paramList.add(labTestLoincDT.getLabTestCd());
			paramList.add(labTestLoincDT.getLaboratoryId());
			paramList.add(labTestLoincDT.getLoincCd());
			paramList.add(new Timestamp(new Date().getTime()));
			paramList.add(new Timestamp(new Date().getTime()));
		
					
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in createLabTestLoincLink: ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString());
		}		
	}

	/**
	 * 
	 * @param whereClause
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> findLoinc(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =FIND_LOINC_SQL;
		ArrayList<Object>  loincDTCollection = new ArrayList<Object> ();
		try {
			
	        codeSql = codeSql + whereClause;
	        
	        LoincDT loincDT = new LoincDT();
					
			loincDTCollection = (ArrayList<Object> ) preparedStmtMethod(loincDT, loincDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findLoinc: ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return loincDTCollection;
		
	}	
	
	/**
	 * 
	 * @param whereClause
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> findLabTest(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =FIND_LABTEST_SQL;
		ArrayList<Object>  labTestDTCollection = new ArrayList<Object> ();
		try {
			
	        codeSql = codeSql + whereClause;
	        
	        LabTestDT LabTestDT = new LabTestDT();
		
			labTestDTCollection = (ArrayList<Object> ) preparedStmtMethod(LabTestDT, labTestDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findLoinc: ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return labTestDTCollection;
		
	}	
	
	/**
	 * 
	 * @param whereClause
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> findManageLoinc(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =FIND_MANAGE_LOINC_SQL;
		ArrayList<Object>  loincDTCollection = new ArrayList<Object> ();
		try {
			
	        codeSql = codeSql + whereClause;
	        
	        LoincDT loincDT = new LoincDT();
			
			loincDTCollection = (ArrayList<Object> ) preparedStmtMethod(loincDT, loincDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findManageLoinc: ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return loincDTCollection;
		
	}	
	
	
	
	
}