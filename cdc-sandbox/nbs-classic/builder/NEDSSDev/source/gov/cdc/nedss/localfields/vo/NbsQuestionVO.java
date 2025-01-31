package gov.cdc.nedss.localfields.vo;


import gov.cdc.nedss.localfields.dt.NbsQuestionDT;
import gov.cdc.nedss.localfields.dt.NbsUiMetadataDT;
import gov.cdc.nedss.util.AbstractVO;

/**
 * NbsQuestionVO is a representation of NBSQuestion and NBSUIMetadata
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NbsQuestionVO.java
 * Sep 5, 2008
 * @version
 */
public class NbsQuestionVO extends AbstractVO{

	private static final long serialVersionUID = 1L;
	public NbsQuestionDT question = new NbsQuestionDT();
	public NbsUiMetadataDT uiMetadata = new NbsUiMetadataDT();
	private Long tabId;
	private Long sectionId;
	private String sectionName;
	private Long subSectionId;
	private String subSectionName;
	private String tabName;
	private String linkUrl;
	
	
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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

	public NbsQuestionDT getQuestion() {
		return question;
	}

	public void setQuestion(NbsQuestionDT question) {
		this.question = question;
	}

	public NbsUiMetadataDT getUiMetadata() {
		return uiMetadata;
	}

	public void setUiMetadata(NbsUiMetadataDT uiMetadata) {
		this.uiMetadata = uiMetadata;
	}

	public Long getTabId() {
		return tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Long getSubSectionId() {
		return subSectionId;
	}

	public void setSubSectionId(Long subSectionId) {
		this.subSectionId = subSectionId;
	}

	public String getSubSectionName() {
		return subSectionName;
	}

	public void setSubSectionName(String subSectionName) {
		this.subSectionName = subSectionName;
	}

	public void reset() {
		this.tabId = null;
		this.sectionId = null;
		this.sectionName = null;
		this.tabName = null;
		this.sectionName = null;
		this.subSectionName = null;
		this.linkUrl = null;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
}
