package gov.cdc.nedss.systemservice.ejb.questionmapejb.dt;

import java.util.Collection;

import gov.cdc.nedss.util.AbstractVO;

public class SourceFieldDT extends AbstractVO{
	
	private Long sourceFieldUid;
	private Long ruleInstanceUid;
	private String questionIdentifier;
	private Long sourceFieldSeqNbr;
	private String investigationFormCd;
	private String questionLabel;
	private Collection<Object>  sourceValueColl;
	private Long pamQuestionUID;

	private String viewLink;
	private String editLink;
	private String deleteLink;


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

	public Long getSourceFieldUid() {
		return sourceFieldUid;
	}

	public void setSourceFieldUid(Long sourceFieldUid) {
		this.sourceFieldUid = sourceFieldUid;
	}

	public Long getRuleInstanceUid() {
		return ruleInstanceUid;
	}

	public void setRuleInstanceUid(Long ruleInstanceUid) {
		this.ruleInstanceUid = ruleInstanceUid;
	}

	public String getQuestionIdentifier() {
		return questionIdentifier;
	}

	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}

	public Long getSourceFieldSeqNbr() {
		return sourceFieldSeqNbr;
	}

	public void setSourceFieldSeqNbr(Long sourceFieldSeqNbr) {
		this.sourceFieldSeqNbr = sourceFieldSeqNbr;
	}
	
	public String getInvestigationFormCd() {
		return investigationFormCd;
	}

	public void setInvestigationFormCd(String investigationFormCd) {
		this.investigationFormCd = investigationFormCd;
	}
	

	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

	public Collection<Object>  getSourceValueColl() {
		return sourceValueColl;
	}

	public void setSourceValueColl(Collection<Object> sourceValueColl) {
		this.sourceValueColl = sourceValueColl;
	}

	public Long getPamQuestionUID() {
		return pamQuestionUID;
	}

	public void setPamQuestionUID(Long pamQuestionUID) {
		this.pamQuestionUID = pamQuestionUID;
	}


	

}
