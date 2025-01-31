package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.SnomedDT;
import gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.util.SRTAdminUtilDAOImpl;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class SnomedCodeDAOImpl extends DAOBase{

	private static final LogUtils logger = new LogUtils(SnomedCodeDAOImpl.class
			.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private static final String FIND_EXISTING_SNOMED_SQL = "SELECT snomed_cd\"snomedCd\""
		+",snomed_desc_txt \"snomedDescTx\" "
		+", source_concept_id\"sourceConceptId\""
		+",source_version_id\"sourceVersionId\""
		+",pa_derivation_exclude_cd\"paDerivationExcludeCd\""
		+"FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Snomed_code ";
	
	private static final String UPDATE_EXISTING_SNOMED_SQL = "UPDATE "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Snomed_code "
		+" SET snomed_desc_txt = ?," + "source_concept_id = ?,"
		+ " source_version_id = ? "
		+ ", pa_derivation_exclude_cd = ? "
		+ "WHERE " + " snomed_cd = ?";
	
	private static final String CREATE_SNOMED_CODE_SQL = "INSERT INTO "
		+  NEDSSConstants.SYSTEM_REFERENCE_TABLE
	    + "..Snomed_code(	snomed_cd , "
	    + " snomed_desc_txt , "
	    + " source_concept_id , "
	    + " effective_from_time, "
	    + " status_cd, "
	    + " status_time, "
	    + " source_version_id, pa_derivation_exclude_cd )"
	    + "VALUES(?, ?, ?, ?,?,?,?,?)";
	
@SuppressWarnings("unchecked")
public Collection<Object> getSnomedDTCollection(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =FIND_EXISTING_SNOMED_SQL;
		
        codeSql = codeSql + whereClause;

		SnomedDT snomedDt = new SnomedDT();
		ArrayList<Object>  snomedDtCollection = new ArrayList<Object> ();
		
		try {			
			snomedDtCollection = (ArrayList<Object> ) preparedStmtMethod(snomedDt, snomedDtCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in getSnomedDTCollection: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return snomedDtCollection;
		
	}

public void updateSnomedCode(SnomedDT snomedDt)
{
	String codeSql=UPDATE_EXISTING_SNOMED_SQL;
	
	 String paDerExcludeCd = snomedDt.getPaDerivationExcludeCd(); 
	 ArrayList<Object>  paramList = new ArrayList<Object> ();
	 
	 paramList.add(snomedDt.getSnomedDescTx());
	 paramList.add(snomedDt.getSourceConceptId());
	 paramList.add(snomedDt.getSourceVersionId());
	 if(paDerExcludeCd.equals("1")) paramList.add("Y");
		else  paramList.add("N");
	 
	 paramList.add(snomedDt.getSnomedCd());

	 try {			
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in updateSnomedCode: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
}

public void CreateSnomedCode( SnomedDT snomedDt)
{
	String codeSql=CREATE_SNOMED_CODE_SQL;
	
	String paDevExcludCd = snomedDt.getPaDerivationExcludeCd() == null ? "" : snomedDt.getPaDerivationExcludeCd(); 
	ArrayList<Object>  paramList = new ArrayList<Object> ();
	
	paramList.add(snomedDt.getSnomedCd());
	paramList.add(snomedDt.getSnomedDescTx());
	paramList.add(snomedDt.getSourceConceptId());
	paramList.add(new Timestamp(new Date().getTime()));
	paramList.add("A");
	paramList.add(new Timestamp(new Date().getTime()));
	paramList.add(snomedDt.getSourceVersionId());
	if(paDevExcludCd.equals("1"))paramList.add("Y");
	else  paramList.add("N");
	try{
		preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);
		
	}catch (Exception ex) {
			logger.fatal("Exception in CreateSnomedCode: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
	}
}

}
