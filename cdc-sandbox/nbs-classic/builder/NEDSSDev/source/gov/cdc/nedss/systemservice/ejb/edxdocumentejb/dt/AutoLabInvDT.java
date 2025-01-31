package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt;

import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * 
 * @author Pradeep Sharma
 *
 */
public class AutoLabInvDT  extends AbstractVO implements RootDTInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long autoLabInvUid;
	private Long orderObservationUid;
	private String orderObservationLocalId;
	private Long resultObservationUid;
	private String conditionCode;
	private Long publicHealthCaseUid;
	private String publicHealthCaseLocalId;
	private Timestamp refreshTime;
	private String invCreated;
	private String comment;
	
	
	public String getOrderObservationLocalId() {
		return orderObservationLocalId;
	}


	public void setOrderObservationLocalId(String orderObservationLocalId) {
		this.orderObservationLocalId = orderObservationLocalId;
	}


	public String getPublicHealthCaseLocalId() {
		return publicHealthCaseLocalId;
	}


	public void setPublicHealthCaseLocalId(String publicHealthCaseLocalId) {
		this.publicHealthCaseLocalId = publicHealthCaseLocalId;
	}


	public Long getAutoLabInvUid() {
		return autoLabInvUid;
	}


	public void setAutoLabInvUid(Long autoLabInvUid) {
		this.autoLabInvUid = autoLabInvUid;
	}


	public Long getOrderObservationUid() {
		return orderObservationUid;
	}


	public void setOrderObservationUid(Long orderObservationUid) {
		this.orderObservationUid = orderObservationUid;
	}


	public Long getResultObservationUid() {
		return resultObservationUid;
	}


	public void setResultObservationUid(Long resultObservationUid) {
		this.resultObservationUid = resultObservationUid;
	}


	public String getConditionCode() {
		return conditionCode;
	}


	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}


	public Long getPublicHealthCaseUid() {
		return publicHealthCaseUid;
	}


	public void setPublicHealthCaseUid(Long publicHealthCaseUid) {
		this.publicHealthCaseUid = publicHealthCaseUid;
	}


	public Timestamp getRefreshTime() {
		return refreshTime;
	}


	public void setRefreshTime(Timestamp refreshTime) {
		this.refreshTime = refreshTime;
	}


	public String getInvCreated() {
		return invCreated;
	}


	public void setInvCreated(String invCreated) {
		this.invCreated = invCreated;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = NbsCaseAnswerDT.class.getDeclaredFields();
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

}
