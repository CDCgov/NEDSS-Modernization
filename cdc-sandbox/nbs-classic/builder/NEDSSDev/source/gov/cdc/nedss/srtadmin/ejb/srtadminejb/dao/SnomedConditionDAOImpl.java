package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.SnomedConditionDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class SnomedConditionDAOImpl extends DAOBase{
	
	private static final LogUtils logger = new LogUtils(LoincConditionDAOImpl.class
			.getName());
	
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private static final String GET_EXISTING_SNOMED_CONDITION_LINK_SQL = "SELECT"
		+ " condition_cd \"conditionCd\"," 
		+ " snomed_cd \"snomedCd\","
		+ " disease_nm  \"diseaseNm\" "
		+ " FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE 
		+ " ..snomed_condition";
	
	private static final String CREATE_SNOMEDCOND_LINK_SQL = "INSERT INTO "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
		+ "..snomed_condition(snomed_cd, "
		+ "condition_cd, disease_nm, status_cd, effective_from_time) values(?,?,?,?,?);";
	

	@SuppressWarnings("unchecked")
	public Collection<Object> findSnomedConditionLink(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
	
		String codeSql =GET_EXISTING_SNOMED_CONDITION_LINK_SQL;
		
       codeSql = codeSql + whereClause;
		
        SnomedConditionDT snomedConditionDT = new SnomedConditionDT();
		ArrayList<Object>  snomedConditionDTCollection = new ArrayList<Object> ();
				
		try {			
			snomedConditionDTCollection  = (ArrayList<Object> ) preparedStmtMethod(snomedConditionDT, snomedConditionDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findSnomedConditionLink: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return snomedConditionDTCollection;
		
	}
	
	public void createSnomedConditionLink(SnomedConditionDT snomedConditionDT) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql = CREATE_SNOMEDCOND_LINK_SQL;
			
	    
	     ArrayList<Object>  paramList = new ArrayList<Object> ();
		 paramList.add(snomedConditionDT.getSnomedCd());
		 paramList.add(snomedConditionDT.getConditionCd());
		 paramList.add(snomedConditionDT.getDiseaseNm());
		 paramList.add("A");
		 paramList.add(new Timestamp(new Date().getTime()));
		 		 
		 try{			
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.CREATE, NEDSSConstants.SRT);
			
		 } catch (Exception ex) {
			logger.fatal("Exception in createSnomedConditionLink: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		 }		
		
	}
			
	
}
