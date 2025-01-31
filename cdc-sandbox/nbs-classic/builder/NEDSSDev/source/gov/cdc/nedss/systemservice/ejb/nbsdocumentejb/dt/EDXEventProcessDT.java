package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class EDXEventProcessDT extends AbstractVO implements RootDTInterface {
  
	private static final long serialVersionUID = 1L;
	private Long eDXEventProcessUid;

	private Long nbsDocumentUid;
	private String sourceEventId;
	private Long nbsEventUid;
	private String docEventTypeCd;
	private String docEventSource;
	private Long addUserId;
	private Timestamp addTime;
	private String jurisdictionCd;
	private String progAreaCd;
	private Long programJurisdictionOid;
	private String localId;
	private String parsedInd;
	private Long edxDocumentUid;

	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public Long geteDXEventProcessUid() {
		return eDXEventProcessUid;
	}
	public void seteDXEventProcessUid(Long eDXEventProcessUid) {
		this.eDXEventProcessUid = eDXEventProcessUid;
	}
	public Long getNbsDocumentUid() {
		return nbsDocumentUid;
	}
	public void setNbsDocumentUid(Long nbsDocumentUid) {
		this.nbsDocumentUid = nbsDocumentUid;
	}
	public Long getNbsEventUid() {
		return nbsEventUid;
	}
	public String getSourceEventId() {
		return sourceEventId;
	}
	public void setSourceEventId(String sourceEventId) {
		this.sourceEventId = sourceEventId;
	}
	public void setNbsEventUid(Long nbsEventUid) {
		this.nbsEventUid = nbsEventUid;
	}
	public String getDocEventTypeCd() {
		return docEventTypeCd;
	}
	public void setDocEventTypeCd(String docEventTypeCd) {
		this.docEventTypeCd = docEventTypeCd;
	}
	public String getDocEventSource() {
		return docEventSource;
	}
	public void setDocEventSource(String docEventSource) {
		this.docEventSource = docEventSource;
	}
	public Long getEdxDocumentUid() {
		return edxDocumentUid;
	}
	public void setEdxDocumentUid(Long edxDocumentUid) {
		this.edxDocumentUid = edxDocumentUid;
	}
	
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	public String getLastChgReasonCd() {
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

	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return itDelete;
	}
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return itDirty;
	}
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return itNew;
	}
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
		
	}
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
		
	}
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
		
	}
	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	public void setStatusCd(String statusCd) {
	
		
	}
	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
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

	public String getJurisdictionCd() {
		return jurisdictionCd;
	}
	public void setJurisdictionCd(String jurisdictionCd) {
		this.jurisdictionCd = jurisdictionCd;
	}
	public String getProgAreaCd() {
		return progAreaCd;
	}
	public void setProgAreaCd(String progAreaCd) {
		this.progAreaCd = progAreaCd;
	}
	public Long getProgramJurisdictionOid() {
		return programJurisdictionOid;
	}
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		this.programJurisdictionOid = programJurisdictionOid;
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
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
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
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getParsedInd() {
		return parsedInd;
	}
	public void setParsedInd(String parsedInd) {
		this.parsedInd = parsedInd;
	}
 	 
}
