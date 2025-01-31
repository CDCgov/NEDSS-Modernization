package gov.cdc.nedss.pagemanagement.wa.dt;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BatchEntry extends AbstractVO  implements RootDTInterface {
	private static final long serialVersionUID = 1L;

	
	Map<String,String> answerMap = new HashMap<String,String>();
	Map<String,String> attributeMap = new HashMap<String,String>();
	
	Map<Object, Object> dsmAnswerMap = new HashMap<Object, Object>();
	
	private Map<String,Object> answerDTMap = new HashMap<String,Object>();
	private ArrayList<Map<String,String>> answerMaps = new ArrayList<Map<String,String>>();
	
	public Map<String, String> getAnswerMap() {
		return answerMap;
	}

	public void setAnswerMap(Map<String, String> answerMap) {
		this.answerMap = answerMap;
	}
	
	
	public Map<Object, Object> getDsmAnswerMap() {
		return dsmAnswerMap;
	}

	public void setDsmAnswerMap(Map<Object, Object> dsmAnswerMap) {
		this.dsmAnswerMap = dsmAnswerMap;
	}

	public Map<String, Object> getAnswerDTMap() {
		return answerDTMap;
	}

	public void setAnswerDTMap(Map<String, Object> answerDTMap) {
		this.answerDTMap = answerDTMap;
	}


	private String questionIden1 ;
	private String questionIden2 ;
	private String questionIden3 ;
	
	private String ansValue1 ;
	private String ansValue2 ;
	private String ansValue3 ;
	
	private String subsecNm;
	
	private String localId;
	
	private int Id;
	private static int nextId = 1;
	private boolean isExisting=false;
	
	//private boolean isExisting=false;
	
	private int answerGrpSeqID;

	public int getAnswerGrpSeqID() {
		return answerGrpSeqID;
	}

	public void setAnswerGrpSeqID(int answerGrpSeqID) {
		this.answerGrpSeqID = answerGrpSeqID;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
//		setAnswerGrpSeqID(getNextId());		
	}

	public String getSubsecNm() {
		return subsecNm;
	}

	public void setSubsecNm(String subsecNm) {
		this.subsecNm = subsecNm;
	}

	public String getQuestionIden1() {
		return questionIden1;
	}

	public void setQuestionIden1(String questionIden1) {
		this.questionIden1 = questionIden1;
	}

	public String getQuestionIden2() {
		return questionIden2;
	}

	public void setQuestionIden2(String questionIden2) {
		this.questionIden2 = questionIden2;
	}

	public String getQuestionIden3() {
		return questionIden3;
	}

	public void setQuestionIden3(String questionIden3) {
		this.questionIden3 = questionIden3;
	}

	public String getAnsValue1() {
		return ansValue1;
	}

	public void setAnsValue1(String ansValue1) {
		this.ansValue1 = ansValue1;
	}

	public String getAnsValue2() {
		return ansValue2;
	}

	public void setAnsValue2(String ansValue2) {
		this.ansValue2 = ansValue2;
	}

	public String getAnsValue3() {
		return ansValue3;
	}

	public void setAnsValue3(String ansValue3) {
		this.ansValue3 = ansValue3;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return this.localId;
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
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getRecordStatusTime() {
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
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
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
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocalId(String aLocalId) {
		this.localId = aLocalId;
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
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
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
	
	public static synchronized int getNextId()
    {
        return nextId++;
    } 
	
	
	public void setExisting(boolean b) {
		// TODO Auto-generated method stub
		isExisting = b;
		//return isExisting;
	}
	
	public boolean getExisting() {
		// TODO Auto-generated method stub
		return(isExisting);
		//return isExisting;
	}

	public Map<String, String> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<String, String> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public ArrayList<Map<String, String>> getAnswerMaps() {
		return answerMaps;
	}

	public void setAnswerMaps(ArrayList<Map<String, String>> answerMaps) {
		this.answerMaps = answerMaps;
	}

}