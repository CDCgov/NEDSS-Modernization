package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.LabResultDT;
import gov.cdc.nedss.srtadmin.dt.LabResultSnomedDT;
import gov.cdc.nedss.srtadmin.dt.SnomedDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class LocallyDefinedResultAndSnomedDAOImpl extends DAOBase{

	private static final LogUtils logger = new LogUtils(LabResultDAO.class
			.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	
	private static final String FIND_RESULT_SNOMED_LINK_SQL="SELECT lab_result_cd \"labResultCd\" "
		+", laboratory_id \"laboratoryID\" "
		+", snomed_cd \"snomedCd\" "
		+"FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Lab_result_Snomed ";
	
	private static final String CREATE_LINK_SQL="INSERT INTO "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE 
	+"..Lab_result_Snomed  (lab_result_cd, laboratory_id, snomed_cd, status_cd,status_time,effective_from_time)"
	+"values(?,?,?,?,?,?)";
		
	private static final String FIND_LABRESULT_SQL = " SELECT DISTINCT lab_result_cd \"labResultCd\", laboratory_id \"laboratoryId\" ,lab_result_desc_txt \"labResultDescTxt\" FROM "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Lab_result ";
		
	private static final String FIND_SNOMED_SQL = " SELECT snomed_cd \"snomedCd\", snomed_desc_txt \"snomedDescTx\" FROM "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Snomed_code ";

	
	
	/**
	 * gets the LabResultSnomedDT Collection<Object> Object for a given LabResultCd and LaboratoryID
	 * @return Collection<Object> of LabResultSnomedDT 
	 */
	
@SuppressWarnings("unchecked")
public Collection<Object> getLabResultSnomedDTCollection(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =FIND_RESULT_SNOMED_LINK_SQL;
		
		LabResultSnomedDT labResultSnomedDt = new LabResultSnomedDT();
		ArrayList<Object>  labResultSnomedDtCollection = new ArrayList<Object> ();
		
		try {			
			labResultSnomedDtCollection = (ArrayList<Object> ) preparedStmtMethod(labResultSnomedDt, labResultSnomedDtCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("whereClause: "+whereClause+" Exception in getLabResultSnomedDTCollection: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return labResultSnomedDtCollection;
		
	}
	/**
	 * 
	 * @param labResultSmonedDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void createLabResultSnomedLink(LabResultSnomedDT labResultSnomedDT) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =CREATE_LINK_SQL;	    
	    
		ArrayList<Object>  paramList = new ArrayList<Object> ();
		paramList.add(labResultSnomedDT.getLabResultCd());
		paramList.add(labResultSnomedDT.getLaboratoryID());
		paramList.add(labResultSnomedDT.getSnomedCd());
		paramList.add("A");
		paramList.add(new Timestamp(new Date().getTime()));
		paramList.add(new Timestamp(new Date().getTime()));
		
		try {			
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in createLabResultSnomedLink: ERROR = " + ex.getMessage(), ex);
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
	public Collection<Object> findLabResult(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =FIND_LABRESULT_SQL;
		
        codeSql = codeSql + whereClause;
        
        LabResultDT labResultDT = new LabResultDT();
		ArrayList<Object>  labResultDTCollection = new ArrayList<Object> ();
		
		try {			
			labResultDTCollection = (ArrayList<Object> ) preparedStmtMethod(labResultDT, labResultDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("whereClause: "+whereClause+" Exception in findLabResult: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return labResultDTCollection;
		
	}	
	
	
	/**
	 * 
	 * @param whereClause
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> findSnomed(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =FIND_SNOMED_SQL;
		
         codeSql = codeSql + whereClause;
        
        SnomedDT snomedDT = new SnomedDT();
		ArrayList<Object>  snomedDTCollection = new ArrayList<Object> ();
		
		try {			
			snomedDTCollection = (ArrayList<Object> ) preparedStmtMethod(snomedDT, snomedDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("whereClause: "+whereClause+" Exception in findSnomed: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return snomedDTCollection;
		
	}	
	
}
