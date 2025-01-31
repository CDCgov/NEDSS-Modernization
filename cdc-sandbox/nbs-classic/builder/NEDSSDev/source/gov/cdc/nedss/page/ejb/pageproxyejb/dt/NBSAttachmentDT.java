package gov.cdc.nedss.page.ejb.pageproxyejb.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

/**
 * @author habraham2
 *
 */
public class NBSAttachmentDT extends AbstractVO implements RootDTInterface{
	
	private Long nbsAttachmentUid;
	private Long attachmentParentUid;
	private String descTxt;
	private Timestamp lastChgTime;
	private Long lastChgUserId;	
	private byte[] attachment;
	private String fileNmTxt;	
	private String deleteLink;
	private String viewLink;
    private String typeCd;
    private String lastChgUserNm;


	public Long getNbsAttachmentUid() {
		return nbsAttachmentUid;
	}

	public void setNbsAttachmentUid(Long nbsAttachmentUid) {
		this.nbsAttachmentUid = nbsAttachmentUid;
	}

	public Long getAttachmentParentUid() {
		return attachmentParentUid;
	}

	public void setAttachmentParentUid(Long attachmentParentUid) {
		this.attachmentParentUid = attachmentParentUid;
	}

	public String getDescTxt() {
		return descTxt;
	}

	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public String getFileNmTxt() {
		return fileNmTxt;
	}

	public void setFileNmTxt(String fileNmTxt) {
		this.fileNmTxt = fileNmTxt;
	}
        	
	public String getDeleteLink() {
		return deleteLink;
	}

	public void setDeleteLink(String deleteLink) {
		this.deleteLink = deleteLink;
	}

	public String getViewLink() {
		return viewLink;
	}

	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}
	

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
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
		return lastChgTime;
	}

	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
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
		return this.itDelete;
	}

	@Override
	public boolean isItDirty() {
		return this.itDirty;
	}

	@Override
	public boolean isItNew() {
		return this.itNew;
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
		this.itDelete = itDelete;		
	}

	@Override
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
		
	}

	@Override
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
		
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
		lastChgTime = aLastChgTime;
		
	}

	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		lastChgUserId = aLastChgUserId;
		
	}
	
	public String getLastChgUserNm() {
		return lastChgUserNm;
	}

	public void setLastChgUserNm(String lastChgUserNm) {
		this.lastChgUserNm = lastChgUserNm;
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
