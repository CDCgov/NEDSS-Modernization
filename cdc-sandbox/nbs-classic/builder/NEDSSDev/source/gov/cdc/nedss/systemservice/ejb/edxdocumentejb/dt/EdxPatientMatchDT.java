package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
/**
 * 
 * @author lukkireddys
 *
 */

public class  EdxPatientMatchDT extends AbstractVO implements RootDTInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long edxPatientMatchUid;
	private Long patientUid;
	private String matchString;
	private String typeCd;
	private Long matchStringHashCode; 
	
	private Long addUserId;
	private Long lastChgUserId;
	private Timestamp addTime;
	private Timestamp lastChgTime;
	private boolean multipleMatch = false;
	
	


	public boolean isMultipleMatch() {
		return multipleMatch;
	}

	public void setMultipleMatch(boolean multipleMatch) {
		this.multipleMatch = multipleMatch;
	}

	public Long getEdxPatientMatchUid() {
		return edxPatientMatchUid;
	}

	public void setEdxPatientMatchUid(Long edxPatientMatchUid) {
		this.edxPatientMatchUid = edxPatientMatchUid;
	}

	public Long getPatientUid() {
		return patientUid;
	}

	public void setPatientUid(Long patientUid) {
		this.patientUid = patientUid;
	}

	public String getMatchString() {
		return matchString;
	}

	public void setMatchString(String matchString) {
		this.matchString = matchString;
	}

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public Long getMatchStringHashCode() {
		return matchStringHashCode;
	}

	public void setMatchStringHashCode(Long matchStringHashCode) {
		this.matchStringHashCode = matchStringHashCode;
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
		return addTime;
	}

	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return addUserId;
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
		
		return lastChgTime;
	}

	@Override
	public Long getLastChgUserId() {
		
		return lastChgUserId;
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
		this.addTime = aAddTime;
		
	}

	@Override
	public void setAddUserId(Long aAddUserId) {
		this.addUserId = aAddUserId;
		
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
		this.lastChgTime = aLastChgTime;
		
	}

	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		this.lastChgUserId = aLastChgUserId; 
		
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
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = EdxPatientMatchDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
}
