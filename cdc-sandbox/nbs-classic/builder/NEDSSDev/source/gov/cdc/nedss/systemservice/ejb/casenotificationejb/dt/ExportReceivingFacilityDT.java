package gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

public class ExportReceivingFacilityDT extends AbstractVO implements RootDTInterface  {
	private static final long serialVersionUID = 1L;
	private Timestamp addTime;
	private String reportType;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private Long exportReceivingFacilityUid;
	private String recordStatusCd;
	private String receivingSystemNm;
	private String  receivingSystemOid;
	private String receivingSystemShortName;
	private String receivingSystemOwner;
	private String receivingSystemOwnerOid;
	private String receivingSystemDescTxt;
	private String sendingIndCd;
	private String receivingIndCd;
	private String allowTransferIndCd;
	private String aComment;
	
	private String sendingIndDescTxt;
	private String receivingIndDescTxt;
	private String allowTransferIndDescTxt;
	private String reportTypeDescTxt;
	
	private String recordStatusCdDescTxt;
	
	private String jurDeriveIndCd;
	public String getJurDeriveIndCd() {
		return jurDeriveIndCd;
	}
	public void setJurDeriveIndCd(String jurDeriveIndCd) {
		this.jurDeriveIndCd = jurDeriveIndCd;
	}
	public String getJurDeriveDescTxt() {
		return jurDeriveDescTxt;
	}
	public void setJurDeriveDescTxt(String jurDeriveDescTxt) {
		this.jurDeriveDescTxt = jurDeriveDescTxt;
	}
	private String jurDeriveDescTxt;
	
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public Long getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	public Long getLastChgUserId() {
		return lastChgUserId;
	}
	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}
	public Long getExportReceivingFacilityUid() {
		return exportReceivingFacilityUid;
	}
	public void setExportReceivingFacilityUid(Long exportReceivingFacilityUid) {
		this.exportReceivingFacilityUid = exportReceivingFacilityUid;
	}
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}
	public String getReceivingSystemNm() {
		return receivingSystemNm;
	}
	public void setReceivingSystemNm(String receivingSystemNm) {
		this.receivingSystemNm = receivingSystemNm;
	}
	
	public String getReceivingSystemShortName() {
		return receivingSystemShortName;
	}
	public void setReceivingSystemShortName(String receivingSystemShortName) {
		this.receivingSystemShortName = receivingSystemShortName;
	}
	public String getReceivingSystemOwner() {
		return receivingSystemOwner;
	}
	public void setReceivingSystemOwner(String receivingSystemOwner) {
		this.receivingSystemOwner = receivingSystemOwner;
	}
	public String getReceivingSystemDescTxt() {
		return receivingSystemDescTxt;
	}
	public void setReceivingSystemDescTxt(String receivingSystemDescTxt) {
		this.receivingSystemDescTxt = receivingSystemDescTxt;
	}
	public String getSendingIndCd() {
		return sendingIndCd;
	}
	public void setSendingIndCd(String sendingIndCd) {
		this.sendingIndCd = sendingIndCd;
	}
	public String getReceivingIndCd() {
		return receivingIndCd;
	}
	public void setReceivingIndCd(String receivingIndCd) {
		this.receivingIndCd = receivingIndCd;
	}
	public String getAllowTransferIndCd() {
		return allowTransferIndCd;
	}
	public void setAllowTransferIndCd(String allowTransferIndCd) {
		this.allowTransferIndCd = allowTransferIndCd;
	}
	public String getAdminComment() {
		return aComment;
	}
	public void setAdminComment(String adminComment) {
		this.aComment = adminComment;
	}
	
	public String getRecordStatusCdDescTxt() {
		return recordStatusCdDescTxt;
	}
	public void setRecordStatusCdDescTxt(String recordStatusCdDescTxt) {
		this.recordStatusCdDescTxt = recordStatusCdDescTxt;
	}
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getLastChgReasonCd() {
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
	public void setLocalId(String localId) {
		// TODO Auto-generated method stub
		
	}
	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
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
	public String getReceivingSystemOwnerOid() {
		return receivingSystemOwnerOid;
	}
	public void setReceivingSystemOwnerOid(String receivingSystemOwnerOid) {
		this.receivingSystemOwnerOid = receivingSystemOwnerOid;
	}
	public String getReceivingSystemOid() {
		return receivingSystemOid;
	}
	public void setReceivingSystemOid(String receivingSystemOid) {
		this.receivingSystemOid = receivingSystemOid;
	}
	public String getSendingIndDescTxt() {
		return sendingIndDescTxt;
	}
	public void setSendingIndDescTxt(String sendingIndDescTxt) {
		this.sendingIndDescTxt = sendingIndDescTxt;
	}
	public String getReceivingIndDescTxt() {
		return receivingIndDescTxt;
	}
	public void setReceivingIndDescTxt(String receivingIndDescTxt) {
		this.receivingIndDescTxt = receivingIndDescTxt;
	}
	public String getAllowTransferIndDescTxt() {
		return allowTransferIndDescTxt;
	}
	public void setAllowTransferIndDescTxt(String allowTransferIndDescTxt) {
		this.allowTransferIndDescTxt = allowTransferIndDescTxt;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getReportTypeDescTxt() {
		return reportTypeDescTxt;
	}
	public void setReportTypeDescTxt(String reportTypeDescTxt) {
		this.reportTypeDescTxt = reportTypeDescTxt;
	}
	
}
