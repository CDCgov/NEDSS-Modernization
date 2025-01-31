package gov.cdc.nedss.localfields.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

/**
 * LocalFieldsDT is the representation of LDF in the list
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NBSQuestionDT.java
 * Sep 2, 2008
 * @version
 */
public class LocalFieldsDT extends AbstractVO implements RootDTInterface {

	private static final long serialVersionUID = 1L;
	
	private Long nbsUiMetadataUid;	
	private String questionLabel;
	private String typeCdDesc;
	private Integer orderNbr;
	private Long nbsQuestionUid;
	private Long parentUid;
	private String tab;
	private String section;
	private String subSection;
	private String viewLink;
	private String editLink;
	private String deleteLink;
	
	
	
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
	public Long getNbsUiMetadataUid() {
		return nbsUiMetadataUid;
	}
	public void setNbsUiMetadataUid(Long nbsUiMetadataUid) {
		this.nbsUiMetadataUid = nbsUiMetadataUid;
	}
	public String getQuestionLabel() {
		return questionLabel;
	}
	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}
	public String getTypeCdDesc() {
		return typeCdDesc;
	}
	public void setTypeCdDesc(String typeCdDesc) {
		this.typeCdDesc = typeCdDesc;
	}
	public Integer getOrderNbr() {
		return orderNbr;
	}
	public void setOrderNbr(Integer orderNbr) {
		this.orderNbr = orderNbr;
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
	public String getDeleteLink() {
		return deleteLink;
	}
	public void setDeleteLink(String deleteLink) {
		this.deleteLink = deleteLink;
	}
	public Long getNbsQuestionUid() {
		return nbsQuestionUid;
	}
	public void setNbsQuestionUid(Long nbsQuestionUid) {
		this.nbsQuestionUid = nbsQuestionUid;
	}
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getSubSection() {
		return subSection;
	}
	public void setSubSection(String subSection) {
		this.subSection = subSection;
	}
	public Long getParentUid() {
		return parentUid;
	}
	public void setParentUid(Long parentUid) {
		this.parentUid = parentUid;
	}
}
