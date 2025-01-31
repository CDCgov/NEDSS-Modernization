package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.NotificationConditionEntityIdentifierDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import java.util.ArrayList;
import java.util.TreeMap;


/**
* Name:		NotificationConditionCodeDAOImpl.java
* Description:	DAO Object for to Retrieve NND Conditions and (Summary and Individual) Entity Identifiers for NBSNNDIntermediaryMessage
* Copyright:	Copyright (c) 2008
* Company: 	Computer Sciences Corporation
* @author	Beau Bannerman
*/

public class NotificationConditionCodeDAOImpl extends DAOBase{
	static final LogUtils logger = new LogUtils(NotificationConditionCodeDAOImpl.class.getName());

	private String SELECT_INDIVIDUAL_NND_CONDITIONS_ENTITY_IDENTIFIER_SQL_SERVER = "SELECT condition_cd \"conditionCd\", nnd_entity_identifier \"nndEntityIdentifier\" " +
						"FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code where nnd_entity_identifier IS NOT NULL";
	
	private String SELECT_INDIVIDUAL_NND_CONDITIONS_ENTITY_IDENTIFIER_FOR_CONDITION_SQL_SERVER = "SELECT condition_cd \"conditionCd\", nnd_entity_identifier \"nndEntityIdentifier\" " +
			"FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code where condition_cd = ?";


	private String SELECT_SUMMARY_NND_CONDITIONS_ENTITY_IDENTIFIER_SQL_SERVER = "SELECT condition_cd \"conditionCd\", nnd_summary_entity_identifier \"nndEntityIdentifier\" " +
						"FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code where nnd_summary_entity_identifier IS NOT NULL";
	
	public TreeMap<Object, Object> getEntityIdentiferForNNDConditions(String entityIdentifierType) throws NEDSSSystemException{
		return getEntityIdentiferForNNDConditions(entityIdentifierType, null);
	}
	
	/**
	 * Gets the Conditions and the nnd_entity_identifier for NND Conditions
	 * @return TreeMap<Object, Object> 
	 */
	@SuppressWarnings("unchecked")
	public TreeMap<Object, Object> getEntityIdentiferForNNDConditions(String entityIdentifierType, String conditionCd) throws NEDSSSystemException{
		TreeMap<Object, Object> codeMap = new TreeMap<Object, Object>();
		NotificationConditionEntityIdentifierDT nndPamConditionEntityIdentifierDT = new NotificationConditionEntityIdentifierDT();
		String sql;
		
		if (entityIdentifierType.equalsIgnoreCase(NEDSSConstants.INDIVIDUAL)) { 
	        if (conditionCd!=null)
	        	sql = SELECT_INDIVIDUAL_NND_CONDITIONS_ENTITY_IDENTIFIER_FOR_CONDITION_SQL_SERVER;
	        else
	        	sql = SELECT_INDIVIDUAL_NND_CONDITIONS_ENTITY_IDENTIFIER_SQL_SERVER;
		} else {
	        	sql = SELECT_SUMMARY_NND_CONDITIONS_ENTITY_IDENTIFIER_SQL_SERVER;
		}
        
		logger.debug("SELECT_NND_CONDITIONS = " + sql);
		
		ArrayList<Object> parms = new ArrayList<Object>();
	
		ArrayList<Object> nndConditionCollection  = new ArrayList<Object> ();
		try
		{
			if(conditionCd !=null) {
				parms.add(conditionCd);
				nndConditionCollection  = (ArrayList<Object> )preparedStmtMethod(nndPamConditionEntityIdentifierDT, parms, sql, NEDSSConstants.SELECT, NEDSSConstants.SRT);
			}
			else
				nndConditionCollection  = (ArrayList<Object> )preparedStmtMethod(nndPamConditionEntityIdentifierDT, nndConditionCollection, sql, NEDSSConstants.SELECT);
		}
		 catch (Exception ex) {
			
			String errorMsg = "Exception in NNDPamConditionCodeDAOImpl.getNNDPamConditions:  ERROR = "; 
			logger.fatal(errorMsg + ex.toString(),ex);
			throw new NEDSSSystemException(errorMsg + ex.toString());
		}
		 
	      for (int count = 0; count < nndConditionCollection.size(); count++) {
	    	  NotificationConditionEntityIdentifierDT dt = (NotificationConditionEntityIdentifierDT) nndConditionCollection.get(count);
	          codeMap.put(dt.getConditionCd(), dt.getNndEntityIdentifier());
	       }
		 
		return codeMap;
	}
}
