package gov.cdc.nedss.srtadmin.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * 
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LabCodingSystemDT.java
 * Jun 12, 2008
 * @version
 */
public class LabCodingSystemDT extends AbstractVO implements RootDTInterface {


	private static final long serialVersionUID = 1L;
	private String laboratoryId;
	private String laboratoryIdTmp;
	private String laboratorySystemDescTxt;
	private String codingSystemCd;
	private String codeSystemDescTxt;	
	private String electronicLabInd;
	private java.util.Date effectiveFromTime;
	private java.util.Date effectiveToTime;	
	private String assigningAuthorityCd;
	private String assigningAuthorityDescTxt;
	private Long nbsUid;
	private String viewLink;
	private String editLink;
	public String getLaboratoryId() {
		return laboratoryId;
	}
	public void setLaboratoryId(String laboratoryId) {
		this.laboratoryId = laboratoryId;
	}
	public String getLaboratorySystemDescTxt() {
		return laboratorySystemDescTxt;
	}
	public void setLaboratorySystemDescTxt(String laboratorySystemDescTxt) {
		this.laboratorySystemDescTxt = laboratorySystemDescTxt;
	}
	public String getCodingSystemCd() {
		return codingSystemCd;
	}
	public void setCodingSystemCd(String codingSystemCd) {
		this.codingSystemCd = codingSystemCd;
	}
	public String getCodeSystemDescTxt() {
		return codeSystemDescTxt;
	}
	public void setCodeSystemDescTxt(String codeSystemDescTxt) {
		this.codeSystemDescTxt = codeSystemDescTxt;
	}
	public String getElectronicLabInd() {
		return electronicLabInd;
	}
	public void setElectronicLabInd(String electronicLabInd) {
		this.electronicLabInd = electronicLabInd;
	}
	public java.util.Date getEffectiveFromTime() {
		return effectiveFromTime;
	}
	public void setEffectiveFromTime(java.util.Date effectiveFromTime) {
		this.effectiveFromTime = effectiveFromTime;
	}
	public java.util.Date getEffectiveToTime() {
		return effectiveToTime;
	}
	public void setEffectiveToTime(java.util.Date effectiveToTime) {
		this.effectiveToTime = effectiveToTime;
	}
	public String getAssigningAuthorityCd() {
		return assigningAuthorityCd;
	}
	public void setAssigningAuthorityCd(String assigningAuthorityCd) {
		this.assigningAuthorityCd = assigningAuthorityCd;
	}
	public String getAssigningAuthorityDescTxt() {
		return assigningAuthorityDescTxt;
	}
	public void setAssigningAuthorityDescTxt(String assigningAuthorityDescTxt) {
		this.assigningAuthorityDescTxt = assigningAuthorityDescTxt;
	}
	public Long getNbsUid() {
		return nbsUid;
	}
	public void setNbsUid(Long nbsUid) {
		this.nbsUid = nbsUid;
	}
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
	public String getLaboratoryIdTmp() {
		return laboratoryIdTmp;
	}
	public void setLaboratoryIdTmp(String laboratoryIdTmp) {
		this.laboratoryIdTmp = laboratoryIdTmp;
	}
	
	
}
