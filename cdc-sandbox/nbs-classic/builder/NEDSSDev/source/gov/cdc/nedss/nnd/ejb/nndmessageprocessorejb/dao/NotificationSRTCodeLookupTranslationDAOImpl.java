package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.CaseNotificationDataDT;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
* Name:		NNDSRTCodeLookupTranslationDAOImpl.java
* Description:	Lookup Codes in SRT for OID/Description and Translations to NND Standard codes
* Copyright:	Copyright (c) 2008
* Company: 	Computer Sciences Corporation
* @author	Beau Bannerman
*/

public class NotificationSRTCodeLookupTranslationDAOImpl extends DAOBase{
	private static final LogUtils logger = new LogUtils(NotificationSRTCodeLookupTranslationDAOImpl.class.getName());
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
    
    private static Map<Object, Object> codeMap = new HashMap<Object, Object>();
    private static Map<Object, Object> codeSetMap = new HashMap<Object, Object>();

	private static String SELECT_CODESET_INFO_SQL = "SELECT code_set_nm \"codeSetNm\", class_cd \"classCd\" from " + 
			NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Codeset where code_set_group_id = ?";
	//GST ND-8763 -changed for 5.3 Rel private static String SELECT_SRTCODE_INFO_SQL = "SELECT concept_nm \"codedValueDescription\", code_system_cd \"codedValueCodingSystem\", concept_code \"codedValue\" FROM "+
	private static String SELECT_SRTCODE_INFO_SQL = "SELECT concept_preferred_nm \"codedValueDescription\", code_system_cd \"codedValueCodingSystem\", concept_code \"codedValue\" FROM "+
			NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..";
	//GST ND-8763 -changed for 5.3 Rel private static String SELECT_SRTCODE_INFO_ORACLE = "SELECT concept_nm \"codedValueDescription\", code_system_cd \"codedValueCodingSystem\", concept_code \"codedValue\" FROM "+
	private static String SELECT_SRTCODE_INFO_WHERECLAUSE = " WHERE code_set_nm = ? AND code = ?";

	
	
	private static String SELECT_SRT_CODE_TRANSLATION_PHIN_SQL = "SELECT to_code \"codedValue\", to_code_desc_txt \"codedValueDescription\", to_code_system_cd \"codedValueCodingSystem\" FROM "+
			NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..";
	private static String SELECT_SRT_CODE_TRANSLATION_PHIN_WHERECLAUSE = " WHERE from_code_set_nm = ? and from_code = ?";
	
	private static String SELECT_SRT_CODE_TRANSLATION_NBS_SQL = "SELECT from_code \"codedValue\", from_code_desc_txt \"codedValueDescription\", to_code_system_cd \"codedValueCodingSystem\" FROM "+
	NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..";
	private static String SELECT_SRT_CODE_TRANSLATION_NBS_WHERECLAUSE = " WHERE to_code_set_nm = ? and to_code = ?";
	
	public void retrieveCodeDescAndCodingSystemWithCodesetGroupId(CaseNotificationDataDT caseNotificationDataDT) throws NEDSSSystemException {
		try{
			Coded codeToLookup = new Coded();
			codeToLookup.setCode(caseNotificationDataDT.getCodedValue());
			codeToLookup.setCodesetGroupId(caseNotificationDataDT.getCodesetGroupId());
			
			retrieveCodeSetInfo(codeToLookup);
	        retrieveSRTCodeInfo(codeToLookup);
			caseNotificationDataDT.setCodeSetNm(codeToLookup.getCodesetName());
			caseNotificationDataDT.setClassCd(codeToLookup.getCodesetTableName());
			// New logic for NBS 4.0 - if code_system_code is 'L' then move code, code description and code system code to local, otherwise
	        // it is a standardized code and should be in code, code description, code system code
	        if (codeToLookup.getCodeSystemCd().equalsIgnoreCase(NEDSSConstants.NND_LOCALLY_CODED)) {
	        	caseNotificationDataDT.setCodedValue(null);
				caseNotificationDataDT.setCodedValueDescription(null);
				caseNotificationDataDT.setCodedValueCodingSystem(null);
	        	caseNotificationDataDT.setLocalCodedValue(codeToLookup.getCode());  
				caseNotificationDataDT.setLocalCodedValueDescription(codeToLookup.getCodeDescription()); //concept_preferred_nm
				caseNotificationDataDT.setLocalCodedValueCodingSystem(codeToLookup.getCodeSystemCd()); //Will be 'L' for local
	        } else {
	        	caseNotificationDataDT.setCodedValue(codeToLookup.getCode()); //concept_code
				caseNotificationDataDT.setCodedValueDescription(codeToLookup.getCodeDescription()); //Now concept_preferred_nm
				caseNotificationDataDT.setCodedValueCodingSystem(codeToLookup.getCodeSystemCd()); //Will be standard's OID
	        }
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void retrieveCodeDescAndCodingSystemWithCodesetGroupId(Coded codeToLookup) throws NEDSSSystemException {
		try{
			retrieveCodeSetInfo(codeToLookup);
	        retrieveSRTCodeInfo(codeToLookup);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void retrieveCodeDescAndCodingSystemWithCodesetName(Coded codeToLookup) throws NEDSSSystemException {
		try{
			retrieveSRTCodeInfo(codeToLookup);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	

	
	
	
	
	public Coded translateNBSCodetoPHINCode(Coded codeToTranslate, String translationTableName) {
		try{
			CaseNotificationDataDT translatedCode  = translateNBSCodetoPHINCode(codeToTranslate.getCode(),
									   											codeToTranslate.getCodesetName(),
									   											translationTableName);
			
			codeToTranslate.setLocalCode(codeToTranslate.getCode());
			codeToTranslate.setLocalCodeDescription(codeToTranslate.getCodeDescription());
			codeToTranslate.setLocalCodeSystemCd(codeToTranslate.getCodeSystemCd());
			codeToTranslate.setCode(translatedCode.getCodedValue());
			codeToTranslate.setCodeDescription(translatedCode.getCodedValueDescription());
			codeToTranslate.setCodeSystemCd(translatedCode.getCodedValueCodingSystem());
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return codeToTranslate;
	}
	
	public CaseNotificationDataDT translateNBSCodetoPHINCode(String code, String codeSetName, String translationTableName) throws NEDSSSystemException {
		try{
			CaseNotificationDataDT translatedCode = new CaseNotificationDataDT();
			translatedCode.setCodeSetNm(codeSetName);
			translatedCode.setLocalCodedValue(code);
			translatedCode.setTranslationTableNm(translationTableName);
	
			retrieveNBSCodetoPHINCodeTranslation(translatedCode);
			
			return translatedCode;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void translateNBSCodetoPHINCode(CaseNotificationDataDT caseNotificationDataDT) throws NEDSSSystemException
	{
		try{
			retrieveNBSCodetoPHINCodeTranslation(caseNotificationDataDT);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	public CaseNotificationDataDT translatePHINCodetoNBSCode(String code, String codeSetName, String translationTableName) {
		CaseNotificationDataDT translatedCode = new CaseNotificationDataDT();
		try{
			translatedCode.setCodeSetNm(codeSetName);
			translatedCode.setCodedValue(code);
			translatedCode.setTranslationTableNm(translationTableName);
			
			retrievePHINCodetoNBSCodeTranslation(translatedCode);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return translatedCode;
	}

	public void translatePHINCodetoNBSCode(CaseNotificationDataDT caseNotificationDataDT) {
		try{
			retrievePHINCodetoNBSCodeTranslation(caseNotificationDataDT);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void retrieveCodeSetInfo(Coded codeToLookup) throws NEDSSSystemException {
		ArrayList<Object> NNDPamCaseDataDTCollection  = new ArrayList<Object> ();
		ArrayList<Object> queryArugments = new ArrayList<Object> ();
		queryArugments.add(codeToLookup.getCodesetGroupId());
		CaseNotificationDataDT newCaseNotificationDataDT = new CaseNotificationDataDT();
		String sql;
		boolean found = false;
		
        sql = SELECT_CODESET_INFO_SQL;
		
        Long codeSetLookup = codeToLookup.getCodesetGroupId();
        
		if (codeSetMap.containsKey(codeSetLookup))
			NNDPamCaseDataDTCollection = (ArrayList<Object>) codeSetMap.get(codeSetLookup);
		else {
			try {
				NNDPamCaseDataDTCollection = (ArrayList<Object>) preparedStmtMethod(newCaseNotificationDataDT,
						queryArugments, sql, NEDSSConstants.SELECT);
				codeSetMap.put(codeSetLookup, NNDPamCaseDataDTCollection);
			} catch (Exception ex) {
				String errString = "NotificationSRTCodeLookupTranslationDAOImpl.retrieveCodeSetInfo:  Failed to retrieve codeset name for code_set_group_id = "
						+ codeToLookup.getCodesetGroupId() + "  -  SQL Exception:  " + ex.toString();

				logger.fatal(errString, ex);
				throw new NEDSSSystemException(errString);
			}
		}
	   Iterator<Object>  it  = NNDPamCaseDataDTCollection.iterator();
		while (it.hasNext()) {
		    	newCaseNotificationDataDT = (CaseNotificationDataDT)it.next();
		    	codeToLookup.setCodesetTableName(newCaseNotificationDataDT.getClassCd());
		    	codeToLookup.setCodesetName(newCaseNotificationDataDT.getCodeSetNm());
		    	found = true;
		}
		
		if (!found) {
	    	String errString = "NotificationSRTCodeLookupTranslationDAOImpl.retrieveCodeSetInfo:  Failed to retrieve codeset name for code_set_group_id = " + codeToLookup.getCodesetGroupId();
			logger.fatal(errString);
			throw new NEDSSSystemException(errString);
		}

    }//end of retrieveCodeSetInfo method
	
	@SuppressWarnings("unchecked")
	public void retrieveSRTCodeInfo(Coded codeToLookup) throws NEDSSSystemException
	{
		ArrayList<Object> NNDPamCaseDataDTCollection  = new ArrayList<Object> ();
		ArrayList<Object> queryArugments = new ArrayList<Object> ();
		queryArugments.add(codeToLookup.getCodesetName());
		queryArugments.add(codeToLookup.getCode());
		CaseNotificationDataDT newCaseNotificationDataDT = new CaseNotificationDataDT();
		String sql;
		boolean found = false;
		
        sql = SELECT_SRTCODE_INFO_SQL;
        
        String codeLookup = codeToLookup.getCodesetName()+"$"+codeToLookup.getCode();
        
        if(codeMap.containsKey(codeLookup))
        	NNDPamCaseDataDTCollection = (ArrayList<Object>)codeMap.get(codeLookup);
		else {
			try {
				NNDPamCaseDataDTCollection = (ArrayList<Object>) preparedStmtMethod(newCaseNotificationDataDT,
						queryArugments, sql + codeToLookup.getCodesetTableName() + SELECT_SRTCODE_INFO_WHERECLAUSE,
						NEDSSConstants.SELECT);
				codeMap.put(codeLookup, NNDPamCaseDataDTCollection);
			}

			catch (Exception ex) {
				String errString = "NotificationSRTCodeLookupTranslationDAOImpl:  Failed to retrieve code oid for code: "
						+ codeToLookup.getCode() + " in codeset: " + codeToLookup.getCodesetName() + " from table:  "
						+ codeToLookup.getCodesetTableName() + ", originally looked up with code_set_group_id: "
						+ codeToLookup.getCodesetGroupId() + "  -  Exception:  " + ex.toString();
				logger.fatal(errString, ex);
				throw new NEDSSSystemException(errString);
			}
		}

	   Iterator<Object>  it  = NNDPamCaseDataDTCollection.iterator();
		while (it.hasNext()) {
		    	newCaseNotificationDataDT = (CaseNotificationDataDT)it.next();;
		    	codeToLookup.setCode(newCaseNotificationDataDT.getCodedValue());
		    	codeToLookup.setCodeDescription(newCaseNotificationDataDT.getCodedValueDescription());
		    	codeToLookup.setCodeSystemCd(newCaseNotificationDataDT.getCodedValueCodingSystem());
		    	found = true;
		}
		
		if (!found) {
            codeToLookup.setCode(codeToLookup.getCode());
            codeToLookup.setFlagNotFound(true);
            codeToLookup.setCodeSystemCd(propertyUtil.getMsgSendingApplicationOID());
            codeToLookup.setCodeDescription(codeToLookup.getCode()); 
            String  errString = "NotificationSRTCodeLookupTranslationDAOImpl.retrieveSRTCodeInfo:  Failed to retrieve code oid for code: " + 
            codeToLookup.getCode() + " in codeset: " + codeToLookup.getCodesetName() +
            " from table:  " + codeToLookup.getCodesetTableName() + ", originally looked up with code_set_group_id: " +
            codeToLookup.getCodesetGroupId() +", so writing as local code in NND"  ;
            logger.debug(errString);
            //Fatima Lopez Calzado: it is necessary to comment out this line otherwise it will cause issues after running
            //the msgoutprocessor if there are investigations with labs associated, because if the code is null or the code is not found in code_value_general
            //it will throw the exception and the msgOutProcessor will fail.
            //throw new NEDSSSystemException(errString);
        }

	}
	
	@SuppressWarnings("unchecked")
	private void retrieveNBSCodetoPHINCodeTranslation(CaseNotificationDataDT codeToTranslate) throws NEDSSSystemException
	{
		ArrayList<Object> caseNotificationDataDTCollection  = new ArrayList<Object> ();
		ArrayList<Object> queryArugments = new ArrayList<Object> ();
		String translationTableName = codeToTranslate.getTranslationTableNm();
		String codeSetName = codeToTranslate.getCodeSetNm();
		String code = codeToTranslate.getLocalCodedValue();

		queryArugments.add(codeSetName);
		queryArugments.add(code);
		boolean found = false;
		
		String sql;
        sql = SELECT_SRT_CODE_TRANSLATION_PHIN_SQL;
        
		String where = SELECT_SRT_CODE_TRANSLATION_PHIN_WHERECLAUSE;

        try
		{
			caseNotificationDataDTCollection  = (ArrayList<Object> )preparedStmtMethod(
														codeToTranslate, 
														queryArugments, 
														sql + translationTableName + where, 
														NEDSSConstants.SELECT);
		}
		 catch (Exception ex) {
	    	String errString = "NotificationSRTCodeLookupTranslationDAOImpl.retrieveNBSCodetoPHINCodeTranslation:  Failed to translate local code: " + 
	    					   code + " using codeset: " + codeSetName + " from:  " + translationTableName + " -  SQL Exception:  " + ex.toString();

			logger.fatal(errString, ex);
			throw new NEDSSSystemException(errString);
		}

	   Iterator<Object>  it  = caseNotificationDataDTCollection.iterator();
	    CaseNotificationDataDT translatedCode = new CaseNotificationDataDT();
		while (it.hasNext()) {
			Object object = it.next();
		    if(object instanceof CaseNotificationDataDT)
			{
		    	translatedCode = (CaseNotificationDataDT)object;
		    	found = true;
			}
		}

		if (!found) {
	    	String errString = "NotificationSRTCodeLookupTranslationDAOImpl.retrieveNBSCodetoPHINCodeTranslation:  Failed to translate local code: " + 
	    					   code + " using codeset: " + codeSetName + " from:  " + translationTableName;
			logger.fatal(errString);
			throw new NEDSSSystemException(errString);
		}
		if((translatedCode.getCodedValue()==null || translatedCode.getCodedValueDescription()==null ||translatedCode.getCodedValueCodingSystem()==null )){
	    	String errString = "NotificationSRTCodeLookupTranslationDAOImpl.retrieveNBSCodetoPHINCodeTranslation:  The translation of one of the columns is missing: " + 
					   code + " using codeset: " + codeSetName + " from:  " + translationTableName;
	    	logger.fatal(errString);
	    	throw new NEDSSSystemException(errString);
		}
		codeToTranslate.setCodedValue(translatedCode.getCodedValue());
		codeToTranslate.setCodedValueDescription(translatedCode.getCodedValueDescription());
		codeToTranslate.setCodedValueCodingSystem(translatedCode.getCodedValueCodingSystem()); 
	}

	@SuppressWarnings("unchecked")
	private void retrievePHINCodetoNBSCodeTranslation(CaseNotificationDataDT codeToTranslate) throws NEDSSSystemException
	{
		ArrayList<Object> caseNotificationDataDTCollection  = new ArrayList<Object> ();
		ArrayList<Object> queryArugments = new ArrayList<Object> ();
		String translationTableName = codeToTranslate.getTranslationTableNm();
		String codeSetName = codeToTranslate.getCodeSetNm();
		String code = codeToTranslate.getCodedValue();

		queryArugments.add(codeSetName);
		queryArugments.add(code);
		boolean found = false;
		
		String sql;
		sql = SELECT_SRT_CODE_TRANSLATION_NBS_SQL;
        
		String where = SELECT_SRT_CODE_TRANSLATION_NBS_WHERECLAUSE;

        try
		{
			caseNotificationDataDTCollection  = (ArrayList<Object> )preparedStmtMethod(
														codeToTranslate, 
														queryArugments, 
														sql + translationTableName + where, 
														NEDSSConstants.SELECT);
		}
		 catch (Exception ex) {
	    	String errString = "NotificationSRTCodeLookupTranslationDAOImpl.retrievePHINCodetoNBSCodeTranslation:  Failed to translate local code: " + 
	    					   code + " using codeset: " + codeSetName + " from:  " + translationTableName + " -  SQL Exception:  " + ex.toString();

			logger.fatal(errString, ex);
			throw new NEDSSSystemException(errString);
		}

	   Iterator<Object>  it  = caseNotificationDataDTCollection.iterator();
	    CaseNotificationDataDT translatedCode = new CaseNotificationDataDT();
		while (it.hasNext()) {
			Object object = it.next();
		    if(object instanceof CaseNotificationDataDT)
			{
		    	translatedCode = (CaseNotificationDataDT)object;
		    	found = true;
			}
		}

		if (!found) {
	    	String errString = "NotificationSRTCodeLookupTranslationDAOImpl.retrievePHINCodetoNBSCodeTranslation:  Failed to translate local code: " + 
	    					   code + " using codeset: " + codeSetName + " from:  " + translationTableName;
			logger.fatal(errString);
			throw new NEDSSSystemException(errString);
		}
		
		codeToTranslate.setLocalCodedValue(translatedCode.getCodedValue());
		codeToTranslate.setLocalCodedValueDescription(translatedCode.getCodedValueDescription());
		codeToTranslate.setLocalCodedValueCodingSystem(translatedCode.getCodedValueCodingSystem()); 
	}
}
