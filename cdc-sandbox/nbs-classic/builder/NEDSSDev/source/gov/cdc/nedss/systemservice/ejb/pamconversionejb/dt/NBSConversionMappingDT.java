package gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;
/**
 * @updated: Release 4.1
 * @author Pradeep Sharma
 *	NBSConversionMappingDT maps NBS_Conversion_Mapping table
 */
public class NBSConversionMappingDT extends AbstractVO implements RootDTInterface {
	
	private Long nbsConversionMappingUid;
	private String fromCode ;
	private String fromCodeSetNm;
	private String fromDataType; 
	private String fromQuestionId; 
	private Long conditionCdGroupId; 
	private String toCode; 
	private String toCodeSetNm; 
	private String toDataType; 
	private String toQuestionId; 
	private String translationRequiredInd; 
	private String fromDbLocation; 
	private String toDbLocation; 
	private String fromLabel; 
	private String toLabel;	
	private String legacyBlockInd;	
	private Integer blockIdNbr;	
	private String otherInd;	
	private String unitInd;
	private String unitTypeCd;	
	private String unitValue;
	private String triggerQuestionId;
	private String triggerQuestionValue;
	private String fromOtherQuestionId;
	private String conversionType;
	private Integer answerGroupSeqNbr;	
	
	public String getToLabel() {
		return toLabel;
	}
	public void setToLabel(String toLabel) {
		this.toLabel = toLabel;
	}
	public String getLegacyBlockInd() {
		return legacyBlockInd;
	}
	public void setLegacyBlockInd(String legacyBlockInd) {
		this.legacyBlockInd = legacyBlockInd;
	}
	public Integer getBlockIdNbr() {
		return blockIdNbr;
	}
	public void setBlockIdNbr(Integer blockIdNbr) {
		this.blockIdNbr = blockIdNbr;
	}
	public String getOtherInd() {
		return otherInd;
	}
	public void setOtherInd(String otherInd) {
		this.otherInd = otherInd;
	}
	public String getUnitInd() {
		return unitInd;
	}
	public void setUnitInd(String unitInd) {
		this.unitInd = unitInd;
	}
	public String getUnitTypeCd() {
		return unitTypeCd;
	}
	public void setUnitTypeCd(String unitTypeCd) {
		this.unitTypeCd = unitTypeCd;
	}
	public String getUnitValue() {
		return unitValue;
	}
	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}
	private static final long serialVersionUID = 1L;
	public Long getNbsConversionMappingUid() {
		return nbsConversionMappingUid;
	}
	public void setNbsConversionMappingUid(Long nbsConversionMappingUid) {
		this.nbsConversionMappingUid = nbsConversionMappingUid;
	}
	public String getFromCode() {
		return fromCode;
	}
	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}
	public String getFromCodeSetNm() {
		return fromCodeSetNm;
	}
	public void setFromCodeSetNm(String fromCodeSetNm) {
		this.fromCodeSetNm = fromCodeSetNm;
	}
	public String getFromQuestionId() {
		return fromQuestionId;
	}
	public void setFromQuestionId(String fromQuestionId) {
		this.fromQuestionId = fromQuestionId;
	}
	public String getToCode() {
		return toCode;
	}
	public void setToCode(String toCode) {
		this.toCode = toCode;
	}
	public String getToCodeSetNm() {
		return toCodeSetNm;
	}
	public void setToCodeSetNm(String toCodeSetNm) {
		this.toCodeSetNm = toCodeSetNm;
	}
	public String getToQuestionId() {
		return toQuestionId;
	}
	public void setToQuestionId(String toQuestionId) {
		this.toQuestionId = toQuestionId;
	}
	public String getTranslationRequiredInd() {
		return translationRequiredInd;
	}
	public void setTranslationRequiredInd(String translationRequiredInd) {
		this.translationRequiredInd = translationRequiredInd;
	}
	public String getFromDbLocation() {
		return fromDbLocation;
	}
	public void setFromDbLocation(String fromDbLocation) {
		if(fromDbLocation!=null)
			this.fromDbLocation=fromDbLocation.toUpperCase();
		else
			this.fromDbLocation = fromDbLocation;
	}
	public String getToDbLocation() {
		return toDbLocation;
	}
	public void setToDbLocation(String toDbLocation) {
		if(toDbLocation!=null)
			this.toDbLocation=toDbLocation.toUpperCase();
		else
			this.toDbLocation = toDbLocation;
	}
	public Integer getAnswerGroupSeqNbr() {
		return answerGroupSeqNbr;
	}
	public void setAnswerGroupSeqNbr(Integer answerGroupSeqNbr) {
		this.answerGroupSeqNbr = answerGroupSeqNbr;
	}

	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getLastChgUserId() {
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
	public String getRecordStatusCd() {
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
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public Timestamp getStatusTime() {
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
	public void setAddTime(Timestamp addTime) {
		// TODO Auto-generated method stub
		
	}
	public void setAddUserId(Long addUserId) {
		// TODO Auto-generated method stub
		
	}
	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		// TODO Auto-generated method stub
		
	}
	public void setLastChgUserId(Long lastChgUserId) {
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
	public void setRecordStatusCd(String recordStatusCd) {
		// TODO Auto-generated method stub
		
	}
	public void setRecordStatusTime(Timestamp recordStatusTime) {
		// TODO Auto-generated method stub
		
	}
	public void setSharedInd(String sharedInd) {
		// TODO Auto-generated method stub
		
	}
	public void setStatusCd(String statusCd) {
		// TODO Auto-generated method stub
		
	}
	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}
	public Long getConditionCdGroupId() {
		return conditionCdGroupId;
	}
	public void setConditionCdGroupId(Long conditionCdGroupId) {
		this.conditionCdGroupId = conditionCdGroupId;
	}
	public String getFromDataType() {
		return fromDataType;
	}
	public void setFromDataType(String fromDataType) {
		this.fromDataType = fromDataType;
	}
	public String getToDataType() {
		return toDataType;
	}
	public void setToDataType(String toDataType) {
		this.toDataType = toDataType;
	}
	public String getFromLabel() {
		return fromLabel;
	}
	public void setFromLabel(String fromLabel) {
		this.fromLabel = fromLabel;
	}
	public String getTriggerQuestionId() {
		return triggerQuestionId;
	}
	public void setTriggerQuestionId(String triggerQuestionId) {
		this.triggerQuestionId = triggerQuestionId;
	}
	public String getTriggerQuestionValue() {
		return triggerQuestionValue;
	}
	public void setTriggerQuestionValue(String triggerQuestionValue) {
		this.triggerQuestionValue = triggerQuestionValue;
	}
	public String getFromOtherQuestionId() {
		return fromOtherQuestionId;
	}
	public void setFromOtherQuestionId(String fromOtherQuestionId) {
		this.fromOtherQuestionId = fromOtherQuestionId;
	}
	public String getConversionType() {
		return conversionType;
	}
	public void setConversionType(String conversionType) {
		this.conversionType = conversionType;
	}
}
