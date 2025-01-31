package gov.cdc.nedss.systemservice.dt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class PrePopMappingDT extends AbstractVO implements RootDTInterface{
	
	private static final long serialVersionUID = 1L;
	private Long lookupQuestionUid;
	private Long lookupAnswerUid;
	private String fromQuestionIdentifier;
	private String fromCodeSystemCode;
	private String fromDataType;
	private String fromFormCd;
	private String toFormCd;
	private String toQuestionIdentifier;
	private String toCodeSystemCd;
	private String toDataType;
	private String fromAnswerCode;
	private String fromAnsCodeSystemCd;
	private String toAnswerCode;
	private String toAnsCodeSystemCd;
	
	public Object deepCopy() throws CloneNotSupportedException, IOException, ClassNotFoundException
	  {
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      ObjectOutputStream oos = new ObjectOutputStream(baos);
	      oos.writeObject(this);
	      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	      ObjectInputStream ois = new ObjectInputStream(bais);
	      Object deepCopy = ois.readObject();

	      return  deepCopy;
	  }

	public Long getLookupQuestionUid() {
		return lookupQuestionUid;
	}

	public void setLookupQuestionUid(Long lookupQuestionUid) {
		this.lookupQuestionUid = lookupQuestionUid;
	}

	public Long getLookupAnswerUid() {
		return lookupAnswerUid;
	}

	public void setLookupAnswerUid(Long lookupAnswerUid) {
		this.lookupAnswerUid = lookupAnswerUid;
	}

	public String getFromQuestionIdentifier() {
		return fromQuestionIdentifier;
	}

	public void setFromQuestionIdentifier(String fromQuestionIdentifier) {
		this.fromQuestionIdentifier = fromQuestionIdentifier;
	}

	public String getFromCodeSystemCode() {
		return fromCodeSystemCode;
	}

	public void setFromCodeSystemCode(String fromCodeSystemCode) {
		this.fromCodeSystemCode = fromCodeSystemCode;
	}

	public String getFromDataType() {
		return fromDataType;
	}

	public void setFromDataType(String fromDataType) {
		this.fromDataType = fromDataType;
	}

	public String getFromFormCd() {
		return fromFormCd;
	}

	public void setFromFormCd(String fromFormCd) {
		this.fromFormCd = fromFormCd;
	}

	public String getToFormCd() {
		return toFormCd;
	}

	public void setToFormCd(String toFormCd) {
		this.toFormCd = toFormCd;
	}

	public String getToQuestionIdentifier() {
		return toQuestionIdentifier;
	}

	public void setToQuestionIdentifier(String toQuestionIdentifier) {
		this.toQuestionIdentifier = toQuestionIdentifier;
	}

	public String getToCodeSystemCd() {
		return toCodeSystemCd;
	}

	public void setToCodeSystemCd(String toCodeSystemCd) {
		this.toCodeSystemCd = toCodeSystemCd;
	}

	public String getToDataType() {
		return toDataType;
	}

	public void setToDataType(String toDataType) {
		this.toDataType = toDataType;
	}

	public String getFromAnswerCode() {
		return fromAnswerCode;
	}

	public void setFromAnswerCode(String fromAnswerCode) {
		this.fromAnswerCode = fromAnswerCode;
	}

	public String getFromAnsCodeSystemCd() {
		return fromAnsCodeSystemCd;
	}

	public void setFromAnsCodeSystemCd(String fromAnsCodeSystemCd) {
		this.fromAnsCodeSystemCd = fromAnsCodeSystemCd;
	}

	public String getToAnswerCode() {
		return toAnswerCode;
	}

	public void setToAnswerCode(String toAnswerCode) {
		this.toAnswerCode = toAnswerCode;
	}

	public String getToAnsCodeSystemCd() {
		return toAnsCodeSystemCd;
	}

	public void setToAnsCodeSystemCd(String toAnsCodeSystemCd) {
		this.toAnsCodeSystemCd = toAnsCodeSystemCd;
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
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgAreaCd(String aProgAreaCd) {
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
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocalId(String aLocalId) {
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
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
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
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
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
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

}
