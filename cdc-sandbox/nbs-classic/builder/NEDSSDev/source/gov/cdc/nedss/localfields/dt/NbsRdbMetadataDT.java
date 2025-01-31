package gov.cdc.nedss.localfields.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * NbsRdbMetadataDT is the parent Table to store RDB Metadata
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NbsRdbMetadataDT.java
 * @version
 */
public class NbsRdbMetadataDT extends AbstractVO  implements RootDTInterface {
	private Long nbsRdbMetadataUid;
	private Long nbsPageUid;
	private Long nbsUiMetadataUid;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private Long lastChgUserId;
	private Timestamp lastChgTime;
	private String localId;
	private String rptAdminColumnNm;
	private String rdbTableNm;
	private String userDefinedColumnNm;
	private String rdbColumnNm;
	private Integer dataMartRepeatNbr;


	private static final long serialVersionUID = 1L;
	
	public NbsRdbMetadataDT() {}
	
	public Long getNbsRdbMetadataUid() {
		return nbsRdbMetadataUid;
	}
	public void setNbsRdbMetadataUid(Long nbsRdbMetadataUid) {
		this.nbsRdbMetadataUid = nbsRdbMetadataUid;
	}
	public Long getNbsPageUid() {
		return nbsPageUid;
	}
	public void setNbsPageUid(Long nbsPageUid) {
		this.nbsPageUid = nbsPageUid;
	}
	public Long getNbsUiMetadataUid() {
		return nbsUiMetadataUid;
	}
	public void setNbsUiMetadataUid(Long nbsUiMetadataUid) {
		this.nbsUiMetadataUid = nbsUiMetadataUid;
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
	public Long getLastChgUserId() {
		return lastChgUserId;
	}
	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public String getRptAdminColumnNm() {
		return rptAdminColumnNm;
	}
	public void setRptAdminColumnNm(String rptAdminColumnNm) {
		this.rptAdminColumnNm = rptAdminColumnNm;
	}
	public String getRdbTableNm() {
		return rdbTableNm;
	}
	public void setRdbTableNm(String rdbTableNm) {
		this.rdbTableNm = rdbTableNm;
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
	public void setAddTime(Timestamp addTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAddUserId(Long addUserId) {
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
	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSharedInd(String sharedInd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatusCd(String statusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}

	public String getRdbColumnNm() {
		return rdbColumnNm;
	}

	public void setRdbColumnNm(String rdbColumnNm) {
		this.rdbColumnNm = rdbColumnNm;
	}

	public String getUserDefinedColumnNm() {
		return userDefinedColumnNm;
	}

	public void setUserDefinedColumnNm(String userDefinedColumnNm) {
		this.userDefinedColumnNm = userDefinedColumnNm;
	}

	public Integer getDataMartRepeatNbr() {
		return dataMartRepeatNbr;
	}

	public void setDataMartRepeatNbr(Integer dataMartRepeatNbr) {
		this.dataMartRepeatNbr = dataMartRepeatNbr;
	}
	
}
