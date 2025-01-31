package gov.cdc.nedss.util;

import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationSRTCodeLookupTranslationDAOImpl;
import gov.cdc.nedss.systemservice.ejb.trigercodeejb.dao.TriggerCodeDAOImpl;

public class Coded {
	private String code;
	private String codeDescription;
	private String codeSystemCd;
	private String localCode;
	private String localCodeDescription;
	private String localCodeSystemCd;

	private Long codesetGroupId;
	private String codesetName;
	private String codesetTableName;
	private boolean flagNotFound;//this has been created as a fix for eicr ND-11745
	public Coded() {
	}
	
	public Coded(String code, Long codesetGroupId) {
		this.setCode(code);
		this.setCodesetGroupId(codesetGroupId);
		findCodeInfoWithCodeSetGroupId();
	}
	
	
	public Coded(String code, String codesetTableName, String codeSetName) {
		this.setCode(code);
		this.setCodesetTableName(codesetTableName);
		this.setCodesetName(codeSetName);
		findCodeInfoWithCodeSetName();
	}

	
	public Coded(String cdSystemName , String code) {
		this.setCode(code);
		this.setCodeSystemCd(cdSystemName);
		findCodeInfoWithCodeSystemName();
	}

	public static Coded createCoded(String code, String codesetTableName, String codeSetName) {
		try {
			return new Coded(code,codesetTableName,codeSetName);
		} catch (Exception e) {
			// If we get an exception, then there was a code lookup problem, so we'll just
			// create a coded with only the code set and the description and oid will remain 'UNDEFINED'
			Coded coded = new Coded();
			coded.setCode(code);
			return coded;
		}
		
	}
	
	
	private void findCodeInfoWithCodeSetGroupId() {
		NotificationSRTCodeLookupTranslationDAOImpl notificationSRTCodeLookupTranslationDAOImpl = new NotificationSRTCodeLookupTranslationDAOImpl();
		notificationSRTCodeLookupTranslationDAOImpl.retrieveCodeDescAndCodingSystemWithCodesetGroupId(this);
	}

	private void findCodeInfoWithCodeSetName() {
		NotificationSRTCodeLookupTranslationDAOImpl notificationSRTCodeLookupTranslationDAOImpl = new NotificationSRTCodeLookupTranslationDAOImpl();
		notificationSRTCodeLookupTranslationDAOImpl.retrieveCodeDescAndCodingSystemWithCodesetName(this);
	}
	
	
	
	
	private void findCodeInfoWithCodeSystemName(){
		TriggerCodeDAOImpl trigCodeDAOImpl = new TriggerCodeDAOImpl();
		trigCodeDAOImpl.getConditionFromTriggerCode(this);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeDescription() {
		if (codeDescription != null)
			return codeDescription;
		else 
			return NEDSSConstants.UNDEFINED;
		
	}
	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}
	public String getCodeSystemCd() {
		if (codeSystemCd != null)
			return codeSystemCd;
		else
			return NEDSSConstants.UNDEFINED;
	}
	public void setCodeSystemCd(String codeSystemCd) {
		this.codeSystemCd = codeSystemCd;
	}
	public String getLocalCode() {
		return localCode;
	}
	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}
	public String getLocalCodeDescription() {
		return localCodeDescription;
	}
	public void setLocalCodeDescription(String localCodeDescription) {
		this.localCodeDescription = localCodeDescription;
	}
	public String getLocalCodeSystemCd() {
		return localCodeSystemCd;
	}
	public void setLocalCodeSystemCd(String localCodeSystemCd) {
		this.localCodeSystemCd = localCodeSystemCd;
	}
	public Long getCodesetGroupId() {
		return codesetGroupId;
	}
	public void setCodesetGroupId(Long codesetGroupId) {
		this.codesetGroupId = codesetGroupId;
	}
	public String getCodesetName() {
		return codesetName;
	}
	public void setCodesetName(String codesetName) {
		this.codesetName = codesetName;
	}

	public String getCodesetTableName() {
		return codesetTableName;
	}

	public void setCodesetTableName(String codesetTableName) {
		this.codesetTableName = codesetTableName;
	}

	public boolean isFlagNotFound() {
		return flagNotFound;
	}

	public void setFlagNotFound(boolean flagNotFound) {
		this.flagNotFound = flagNotFound;
	}
	
}
