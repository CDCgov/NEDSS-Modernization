package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt.TriggerCodesDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import java.util.ArrayList;
import java.util.Collection;

/**
* Name:		TriggerCodeDAO.java
* Description:	DAO Object for Trigger Code.
* Copyright:	Copyright (c) 2017
* Company: 	CSRA
* @author	Fatima Lopez Calzado
*/

public class TriggerCodeDAO extends DAOBase{
	
	private static final LogUtils logger = new LogUtils(TriggerCodeDAO.class
			.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

	
	private static final String SELECT_TRIGGER_CODE_SQL="SELECT  cc.code_system_cd \"codeSystemCd\" "
			+", cc.code_system_desc_txt \"codingSystem\" "
			+",cc.code \"code\""
			+", cc.code_desc_txt \"codeDescTxt\""
			+",cc.code_system_version_id \"codeSystemVersionId\" "
			+", cc.condition_cd \"conditionCd\""
			+",ISNULL(cc.disease_nm,'') \"diseaseNm\""
			+",cc.status_cd \"statusCd\""
			+",cc.status_time \"statusTime\" "
			+ ",cc.nbs_uid \"nbsUid\" "
			+ ",cc.effective_from_time \"effectiveFromTime\" "
			+ ",cc.effective_to_time \"effectiveToTime\" "
			+ ",cc1.prog_area_cd \"programArea\" "
			+" FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..code_to_condition cc "
			+" LEFT JOIN "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Condition_code cc1 on "
			+"  cc.condition_cd = cc1.condition_cd ";
		

/**
 * getTriggerCodeResultDTCollection: method that gets the collection of trigger codes based on the where clause
 * @param whereClause
 * @return
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
	
@SuppressWarnings("unchecked")
public Collection<Object> getTriggerCodeResultDTCollection(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {
		
		String codeSql =SELECT_TRIGGER_CODE_SQL;
		ArrayList<Object>  triggerCodeDTCollection = new ArrayList<Object> ();
		try {
	        codeSql = codeSql + whereClause;
	
			TriggerCodesDT triggerCodeDT = new TriggerCodesDT();
			
			triggerCodeDTCollection = (ArrayList<Object> ) preparedStmtMethod(triggerCodeDT, triggerCodeDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			
		} catch (Exception ex) {
			logger.fatal("Exception in getTriggerCodeResultDTCollection: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return triggerCodeDTCollection;
		
	}
}
