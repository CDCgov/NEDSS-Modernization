package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;



public class WaRuleMetadataDT extends AbstractVO implements RootDTInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Collection<String> targetQuestionIdentifierDTColl; 
	private Collection<String> sourceQuestionIdentifierDTColl; 
	private Long waRuleMetadataUid;
	private Long waTemplateUid;
	private String ruleDescTxt;
	private String ruleCd;//function
	private String errMsgTxt;
	private String sourceQuestionIdentifierString;
	private String viewLink;
	private String editLink;
	private String ruleDescription;
	private String  targetQuestionIdentifierString;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private Timestamp addTime;
	private Long addUserId;
	private Long lastChgUserId;
	private String ruleExpression;
	private Map<Integer, RuleElementDT> ruleElementMap;
	private String javascriptFunction;
	private String javascriptFunctionNm;
	private String QuestionId;
	private Timestamp lastChgTime;
	private String sourceValues;
	private String logicValues;
	private String userRuleId;
	private Map<String, String> lableMap = new HashMap<String, String>();
	
	private String targetType;
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = WaRuleMetadataDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public Collection<String> getTargetQuestionIdentifierDTColl() {
		return targetQuestionIdentifierDTColl;
	}
	public void setTargetQuestionIdentifierDTColl(Collection<String> targetQuestionIdentifierDTColl) {
		this.targetQuestionIdentifierDTColl = targetQuestionIdentifierDTColl;
	}
	public Collection<String> getSourceQuestionIdentifierDTColl() {
		return sourceQuestionIdentifierDTColl;
	}
	public void setSourceQuestionIdentifierDTColl(Collection<String> sourceQuestionIdentifierDTColl) {
		this.sourceQuestionIdentifierDTColl = sourceQuestionIdentifierDTColl;
	}
	public String getTargetQuestionIdentifierString() {
		if(this.targetQuestionIdentifierDTColl!=null && targetQuestionIdentifierString==null){
			Iterator<String> it = targetQuestionIdentifierDTColl.iterator();
			StringBuffer targetQuestionBuffer = new StringBuffer("");
			int i =0;
			while(it.hasNext()){
				if(i==0)
					targetQuestionBuffer.append(it.next());
				else{
					targetQuestionBuffer.append(",");
					targetQuestionBuffer.append(it.next());
				}
				i++;
			}
			this.targetQuestionIdentifierString= targetQuestionBuffer.toString();
		}
		else if(this.targetQuestionIdentifierDTColl==null){
			targetQuestionIdentifierString="";
		}
		return targetQuestionIdentifierString;
	}
	public String setTargetQuestionIdentifierString(String targetQuestionIdentifierString) {
		this.targetQuestionIdentifierString = targetQuestionIdentifierString;
		return targetQuestionIdentifierString;
	}
	
	public String setSourceQuestionIdentifierString(String sourceQuestionIdentifierString) {
		this.sourceQuestionIdentifierString = sourceQuestionIdentifierString;
		return sourceQuestionIdentifierString;
	}
	
	/**
	 * @return
	 */
	public String getSourceQuestionIdentifierString() {
		if(this.sourceQuestionIdentifierDTColl!=null && sourceQuestionIdentifierString==null){
			Iterator<String> it = sourceQuestionIdentifierDTColl.iterator();
			StringBuffer sourceQuestionBuffer = new StringBuffer("");
			int i =0;
			while(it.hasNext()){
				if(i==0)
					sourceQuestionBuffer.append(it.next());
				else{
					sourceQuestionBuffer.append(",");
					sourceQuestionBuffer.append(it.next());
				}
				i++;
			}
			this.sourceQuestionIdentifierString= sourceQuestionBuffer.toString();
		}
		else if(this.sourceQuestionIdentifierDTColl==null){
			sourceQuestionIdentifierString="";
		}
		return sourceQuestionIdentifierString;
	}
	
	public Long getWaRuleMetadataUid() {
		return waRuleMetadataUid;
	}
	public void setWaRuleMetadataUid(Long waRuleMetadataUid) {
		this.waRuleMetadataUid = waRuleMetadataUid;
	}
	public Long getWaTemplateUid() {
		return waTemplateUid;
	}
	public void setWaTemplateUid(Long waTemplateUid) {
		this.waTemplateUid = waTemplateUid;
	}
	public String getRuleDescTxt() {
		return ruleDescTxt;
	}
	public void setRuleDescTxt(String ruleDescTxt) {
		this.ruleDescTxt = ruleDescTxt;
	}
	public String getRuleCd() {
		return ruleCd;
	}
	public void setRuleCd(String ruleCd) {
		this.ruleCd = ruleCd;
	}
	public String getErrMsgTxt() {
		return errMsgTxt;
	}
	public void setErrMsgTxt(String errMsgTxt) {
		this.errMsgTxt = errMsgTxt;
	}
	public String getViewLink() {
		return viewLink;
	}
	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}
	public String getEditLink() {
		return editLink;
	}
	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}
	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}
	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public Long getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	public Long getLastChgUserId() {
		return lastChgUserId;
	}
	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}
	public String getRuleExpression() {
		return ruleExpression;
	}
	public void setRuleExpression(String ruleExpression) {
		this.ruleExpression = ruleExpression;
	}
	public Map<Integer, RuleElementDT> getRuleElementMap() {
		return ruleElementMap;
	}
	public void setRuleElementMap(Map<Integer, RuleElementDT> ruleElementMap) {
		this.ruleElementMap = ruleElementMap;
	}
	public String getJavascriptFunction() {
		return javascriptFunction;
	}
	public void setJavascriptFunction(String javascriptFunction) {
		this.javascriptFunction = javascriptFunction;
	}
	public String getJavascriptFunctionNm() {
		return javascriptFunctionNm;
	}
	public void setJavascriptFunctionNm(String javascriptFunctionNm) {
		this.javascriptFunctionNm = javascriptFunctionNm;
	}
	public String getLocalId() {
		return null;
	}
	public void setLocalId(String setLocalId) {
	}
	
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	public void setSourceQuestionString(String sourceQuestionIdentifierString) {
		this.sourceQuestionIdentifierString = sourceQuestionIdentifierString;
	}
	public void setTargetQuestionString(String targetQuestionIdentifierString) {
		this.targetQuestionIdentifierString = targetQuestionIdentifierString;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getSourceValues() {
		return sourceValues;
	}
	public void setSourceValues(String sourceValues) {
		this.sourceValues = sourceValues;
	}
	public String getLogicValues() {
		return logicValues;
	}
	public void setLogicValues(String logicValues) {
		this.logicValues = logicValues;
	}
	public String getUserRuleId() {
		return userRuleId;
	}
	public void setUserRuleId(String userRuleId) {
		this.userRuleId = userRuleId;
	}
	public Map<String, String> getLableMap() {
		return lableMap;
	}
	public void setLableMap(Map<String, String> lableMap) {
		this.lableMap = lableMap;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	
}
