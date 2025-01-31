package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Collection;

public class RuleElementDT extends AbstractVO implements RootDTInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sourceQuestionIdentifier;
	private Collection<String> targetQuestionIdentifierColl;
	private String comparator;
	private Collection<String> sourceValues;
	private String initialValue;
	private String finalValue;
	private String range;
	
	public Collection<String> getTargetQuestionIdentifierColl() {
		return targetQuestionIdentifierColl;
	}
	public void setTargetQuestionIdentifierColl(Collection<String> targetQuestionIdentifierColl) {
		this.targetQuestionIdentifierColl = targetQuestionIdentifierColl;
	}
	public Collection<String> getSourceValues() {
		return sourceValues;
	}
	public void setSourceValues(Collection<String> sourceValues) {
		this.sourceValues = sourceValues;
	}
	public String getSourceQuestionIdentifier() {
		return sourceQuestionIdentifier;
	}
	public void setSourceQuestionIdentifier(String sourceQuestionIdentifier) {
		this.sourceQuestionIdentifier= sourceQuestionIdentifier;
	}
	public String getComparator() {
		return comparator;
	}
	public void setComparator(String comparator) {
		this.comparator = comparator;
	}
	public String getInitialValue() {
		return initialValue;
	}
	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}
	public String getFinalValue() {
		return finalValue;
	}
	public void setFinalValue(String finalValue) {
		this.finalValue = finalValue;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = RuleElementDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		//  Auto-generated method stub
		return false;
	}
	@Override
	public Timestamp getAddTime() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public Long getAddUserId() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public String getJurisdictionCd() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public String getLastChgReasonCd() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getLastChgTime() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public Long getLastChgUserId() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public String getLocalId() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public String getProgAreaCd() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public Long getProgramJurisdictionOid() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public String getRecordStatusCd() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getRecordStatusTime() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public String getSharedInd() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public String getStatusCd() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getStatusTime() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public String getSuperclass() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public Long getUid() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public Integer getVersionCtrlNbr() {
		//  Auto-generated method stub
		return null;
	}
	@Override
	public boolean isItDelete() {
		//  Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItDirty() {
		//  Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItNew() {
		//  Auto-generated method stub
		return false;
	}
	@Override
	public void setAddTime(Timestamp aAddTime) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setAddUserId(Long aAddUserId) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setItDelete(boolean itDelete) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setItDirty(boolean itDirty) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setItNew(boolean itNew) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setLocalId(String aLocalId) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setSharedInd(String aSharedInd) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setStatusCd(String aStatusCd) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		//  Auto-generated method stub
		
	}
	
	


}
