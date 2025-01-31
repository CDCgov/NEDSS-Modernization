package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ReportSummaryInterface;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class DocumentSummaryVO extends AbstractVO implements ReportSummaryInterface, RootDTInterface{
  private static final long serialVersionUID = 1L;
  private Long nbsDocumentUid;
  private String docPayload;
  private String docType;
  private String recordStatusCd;
  private Timestamp recordStatusTime;
  private Long addUserID;
  private String txt;
  private Long MPRUid;
  private String jurisdiction;
  private String programArea;
  private String type;
  private Timestamp dateReceived;
  private String localId;
  private Collection<Object> theDocumentResultedTestSummaryVO;
  private String cd;
  private String cdDescTxt;
  private String firstName;
  private String lastName;
  private Timestamp addTime;
  private Map<Object,Object> associationMap;  
  private String sendingFacilityNm;
  private String progAreaCd;
  public String getProgAreaCd() {
	return progAreaCd;
}

public void setProgAreaCd(String progAreaCd) {
	this.progAreaCd = progAreaCd;
}

public String getJurisdictionCd() {
	return jurisdictionCd;
}

public void setJurisdictionCd(String jurisdictionCd) {
	this.jurisdictionCd = jurisdictionCd;
}

private String jurisdictionCd;
  
  public Map<Object, Object> getAssociationMap() {
	return associationMap;
}

public void setAssociationMap(Map<Object, Object> associationMap) {
	this.associationMap = associationMap;
}

public String getSendingFacilityNm() {
	return sendingFacilityNm;
}

public void setSendingFacilityNm(String sendingFacilityNm) {
	this.sendingFacilityNm = sendingFacilityNm;
}


  
  
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public Timestamp getActivityFromTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getIsAssociated() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getIsTouched() {
		// TODO Auto-generated method stub
		return false;
	}

	public Long getObservationUid() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setActivityFromTime(Timestamp activityFromTime) {
		// TODO Auto-generated method stub
		
	}

	public void setItAssociated(boolean associated) {
		// TODO Auto-generated method stub
		
	}

	public void setItTouched(boolean touched) {
		// TODO Auto-generated method stub
		
	}

	public void setObservationUid(Long observationUid) {
		// TODO Auto-generated method stub
		
	}

	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return addTime;
	}

	public Long getAddUserId() {
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

	public String getLocalId (){
	    return localId;
	}

	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return recordStatusCd;
	}

	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return recordStatusTime;
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

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
		
	}

	public void setAddUserId(Long addUserId) {
		// TODO Auto-generated method stub
		
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

	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgTime(Timestamp lastChgTime) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgUserId(Long lastChgUserId) {
		// TODO Auto-generated method stub
		
	}

	public void setLocalId (String aLocalId){
	    localId = aLocalId;
	  }


	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		 this.recordStatusCd = aRecordStatusCd;
		
	}

	public void setRecordStatusTime(java.sql.Timestamp recordStatusTime) {
		// TODO Auto-generated method stub
		 this.recordStatusTime = recordStatusTime;
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

	public Long getNbsDocumentUid() {
		return nbsDocumentUid;
	}

	public void setNbsDocumentUid(Long nbsDocumentUid) {
		this.nbsDocumentUid = nbsDocumentUid;
	}

	public String getDocPayload() {
		return docPayload;
	}

	public void setDocPayload(String docPayload) {
		this.docPayload = docPayload;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Long getAddUserID() {
		return addUserID;
	}

	public void setAddUserID(Long addUserID) {
		this.addUserID = addUserID;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public Long getMPRUid() {
		return MPRUid;
	}

	public void setMPRUid(Long uid) {
		MPRUid = uid;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String getProgramArea() {
		return programArea;
	}

	public void setProgramArea(String programArea) {
		this.programArea = programArea;
	}

	public Collection<Object> getTheDocumentResultedTestSummaryVO() {
		return theDocumentResultedTestSummaryVO;
	}

	public void setTheDocumentResultedTestSummaryVO(
			Collection<Object> theDocumentResultedTestSummaryVO) {
		this.theDocumentResultedTestSummaryVO = theDocumentResultedTestSummaryVO;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Timestamp dateReceived) {
		this.dateReceived = dateReceived;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getCdDescTxt() {
		return cdDescTxt;
	}

	public void setCdDescTxt(String cdDescTxt) {
		this.cdDescTxt = cdDescTxt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	

}
