package gov.cdc.nedss.localfields.dt;

import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * NbsPageDT is the parent Table to store Page Metadata
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NBSPageDT.java
 * @version
 */
public class NbsPageDT extends AbstractVO  implements RootDTInterface {
	private Long nbsPageUid;
	private Long waTemplateUid;
	private String formCd;
	private String descTxt;
	private byte[] jspPayload;
	private String datamartNm;
	private String localId;
	private String busObjType;
	private Long lastChgUserId;
	private Timestamp lastChgTime;
	private String recordStatusCd;
	private Timestamp recordStatusTime;

	private static final long serialVersionUID = 1L;

	public NbsPageDT() {}
	
	public NbsPageDT(WaTemplateDT waTemplateDT) {
		setWaTemplateUid(waTemplateDT.getWaTemplateUid());
		setFormCd(waTemplateDT.getFormCd());
		setDescTxt(waTemplateDT.getDescTxt());
		setBusObjType(waTemplateDT.getBusObjType());
		setDatamartNm(waTemplateDT.getDataMartNm());
		
	}
	
	
	public Long getNbsPageUid() {
		return nbsPageUid;
	}
	public void setNbsPageUid(Long nbsPageUid) {
		this.nbsPageUid = nbsPageUid;
	}
	public Long getWaTemplateUid() {
		return waTemplateUid;
	}
	public void setWaTemplateUid(Long waTemplateUid) {
		this.waTemplateUid = waTemplateUid;
	}
	public String getFormCd() {
		return formCd;
	}
	public void setFormCd(String formCd) {
		this.formCd = formCd;
	}
	public String getDescTxt() {
		return descTxt;
	}
	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}
	public byte[] getJspPayload() {
		return jspPayload;
	}
	public void setJspPayload(byte[] jspPayload) {
		this.jspPayload = jspPayload;
	}
	public String getDatamartNm() {
		return datamartNm;
	}
	public void setDatamartNm(String datamartNm) {
		this.datamartNm = datamartNm;
	}
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public String getBusObjType() {
		return busObjType;
	}
	public void setBusObjType(String busObjType) {
		this.busObjType = busObjType;
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
			
}
