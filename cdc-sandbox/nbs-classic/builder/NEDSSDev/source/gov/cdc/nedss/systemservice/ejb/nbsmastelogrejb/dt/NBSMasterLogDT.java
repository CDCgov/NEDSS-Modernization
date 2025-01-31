package gov.cdc.nedss.systemservice.ejb.nbsmastelogrejb.dt;

import java.sql.Timestamp;
import java.util.Collection;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;


public class NBSMasterLogDT extends AbstractVO implements RootDTInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long actUid;;
	private Long  entityUid;
	private String eventType;
	 private String statusCd;
	 private Timestamp startTime;
	 private Timestamp endTime;
	 private String processMessageTxt;
	 private String localId;
	 private Collection<Object> nBSConversionDetailDTCollection;
	 
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		
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


	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
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


	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}

	public Long getActUid() {
		return actUid;
	}

	public void setActUid(Long actUid) {
		this.actUid = actUid;
	}

	public Long getEntityUid() {
		return entityUid;
	}

	public void setEntityUid(Long entityUid) {
		this.entityUid = entityUid;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getProcessMessageTxt() {
		return processMessageTxt;
	}

	public void setProcessMessageTxt(String processMessageTxt) {
		this.processMessageTxt = processMessageTxt;
	}

	public Collection<Object> getNBSConversionDetailDTCollection() {
		return nBSConversionDetailDTCollection;
	}

	public void setNBSConversionDetailDTCollection(
			Collection<Object> conversionDetailDTCollection) {
	}
	
}
