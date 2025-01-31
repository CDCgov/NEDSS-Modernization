package gov.cdc.nedss.proxy.ejb.pamproxyejb.vo;

import java.sql.Timestamp;
import java.util.Collection;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class AggregateSummaryResultVO  extends AbstractVO implements RootDTInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PublicHealthCaseDT publicHealthCaseDT;
	private Collection<Object>  nbsCaseAnswerDTColl;
	private NotificationDT notificationDT;
	private String viewLink;
	private String editLink;
	
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

	public PublicHealthCaseDT getPublicHealthCaseDT() {
		return publicHealthCaseDT;
	}

	public void setPublicHealthCaseDT(PublicHealthCaseDT publicHealthCaseDT) {
		this.publicHealthCaseDT = publicHealthCaseDT;
	}

	public Collection<Object>  getNbsCaseAnswerDTColl() {
		return nbsCaseAnswerDTColl;
	}

	public void setNbsCaseAnswerDTColl(Collection<Object> nbsCaseAnswerDTColl) {
		this.nbsCaseAnswerDTColl = nbsCaseAnswerDTColl;
	}

	public NotificationDT getNotificationDT() {
		return notificationDT;
	}

	public void setNotificationDT(NotificationDT notificationDT) {
		this.notificationDT = notificationDT;
	}

}
