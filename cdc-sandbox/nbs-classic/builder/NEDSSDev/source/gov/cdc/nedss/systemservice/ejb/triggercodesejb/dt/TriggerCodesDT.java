package gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt;

import java.sql.Timestamp;
import java.util.ArrayList;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class TriggerCodesDT extends AbstractVO implements RootDTInterface  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String displayName2;
	String versionId2;
	private String recordStatusCdDescTxt;
	private String jurDeriveIndCd;
	private String codeColumn;
	private String programArea;
	private String codingSystem;
	private String condition;
	private String status;
	private String codingSystemDescription;
	private String displayName;
	private Integer nbsUid;
	private String codeDescTxt;
	private String codeSystemCd;
	private String code;
	private String codeSystemVersionId;
	private String conditionCd;
	private String diseaseNm;
	private String statusCd;
	private Timestamp statusTime;
	private Timestamp effectiveFromTime;
	private Timestamp effectiveToTime;
	private String programAreas;
	private String codingSystems;
	private String StatusValues;
	private String conditions;

	/**
	 * @return the programAreas
	 */
	public String getProgramAreas() {
		return programAreas;
	}
	/**
	 * @param programAreas the programAreas to set
	 */
	public void setProgramAreas(String programAreas) {
		this.programAreas = programAreas;
	}
	/**
	 * @return the codingSystems
	 */
	public String getCodingSystems() {
		return codingSystems;
	}
	/**
	 * @param codingSystems the codingSystems to set
	 */
	public void setCodingSystems(String codingSystems) {
		this.codingSystems = codingSystems;
	}
	/**
	 * @return the statusValues
	 */
	public String getStatusValues() {
		return StatusValues;
	}
	/**
	 * @param statusValues the statusValues to set
	 */
	public void setStatusValues(String statusValues) {
		StatusValues = statusValues;
	}
	/**
	 * @return the conditions
	 */
	public String getConditions() {
		return conditions;
	}
	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public String getJurDeriveIndCd() {
		return jurDeriveIndCd;
	}
	public void setJurDeriveIndCd(String jurDeriveIndCd) {
		this.jurDeriveIndCd = jurDeriveIndCd;
	}
	public String getJurDeriveDescTxt() {
		return jurDeriveDescTxt;
	}
	public void setJurDeriveDescTxt(String jurDeriveDescTxt) {
		this.jurDeriveDescTxt = jurDeriveDescTxt;
	}
	private String jurDeriveDescTxt;
	
	
	public String getRecordStatusCdDescTxt() {
		return recordStatusCdDescTxt;
	}
	public void setRecordStatusCdDescTxt(String recordStatusCdDescTxt) {
		this.recordStatusCdDescTxt = recordStatusCdDescTxt;
	}
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}
	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	public void setLocalId(String localId) {
		// TODO Auto-generated method stub
		
	}
	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}
	public void setRecordStatusTime(Timestamp recordStatusTime) {
		// TODO Auto-generated method stub
		
	}
	public void setSharedInd(String sharedInd) {
		// TODO Auto-generated method stub
		
	}
	
	
	public String getCodingSystemDescription() {
		return codingSystemDescription;
	}
	public void setCodingSystemDescription(String codingSystemDescription) {
		this.codingSystemDescription = codingSystemDescription;
	}
	public String getCodeDescTxt() {
		return codeDescTxt;
	}
	public void setCodeDescTxt(String codeDescTxt) {
		this.codeDescTxt = codeDescTxt;
	}
	public String getCodeSystemVersionId() {
		return codeSystemVersionId;
	}
	public void setCodeSystemVersionId(String codeSystemVersionId) {
		this.codeSystemVersionId = codeSystemVersionId;
	}
	public String getConditionCd() {
		return conditionCd;
	}
	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}
	public String getDiseaseNm() {
		return diseaseNm;
	}
	public void setDiseaseNm(String diseaseNm) {
		this.diseaseNm = diseaseNm;
	}

	public Timestamp getEffectiveFromTime() {
		return effectiveFromTime;
	}
	public void setEffectiveFromTime(Timestamp effectiveFromTime) {
		this.effectiveFromTime = effectiveFromTime;
	}
	public Timestamp getEffectiveToTime() {
		return effectiveToTime;
	}
	public void setEffectiveToTime(Timestamp effectiveToTime) {
		this.effectiveToTime = effectiveToTime;
	}

	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getCodeSystemCd() {
		return codeSystemCd;
	}
	public void setCodeSystemCd(String codeSystemCd) {
		this.codeSystemCd = codeSystemCd;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Timestamp getStatusTime() {
		return statusTime;
	}
	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
	}
	public String getStatusCd() {
		return statusCd;
	}
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
}
	public Integer getNbsUid() {
		return nbsUid;
	}
	public void setNbsUid(Integer nbsUid) {
		this.nbsUid = nbsUid;
	}
	public String getDisplayName2() {
		return displayName2;
	}
	public void setDisplayName2(String displayName2) {
		this.displayName2 = displayName2;
	}
	
	public String getVersionId2() {
		return versionId2;
	}

	public void setVersionId2(String versionId2) {
		this.versionId2 = versionId2;
	}
	/**
	 * @return the codeColumn
	 */
	public String getCodeColumn() {
		return codeColumn;
	}
	/**
	 * @param codeColumn the codeColumn to set
	 */
	public void setCodeColumn(String codeColumn) {
		this.codeColumn = codeColumn;
	}
	/**
	 * @return the programArea
	 */
	public String getProgramArea() {
		return programArea;
	}
	/**
	 * @param programArea the programArea to set
	 */
	public void setProgramArea(String programArea) {
		this.programArea = programArea;
	}
	/**
	 * @return the codingSystem
	 */
	public String getCodingSystem() {
		return codingSystem;
	}
	/**
	 * @param codingSystem the codingSystem to set
	 */
	public void setCodingSystem(String codingSystem) {
		this.codingSystem = codingSystem;
	}
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
