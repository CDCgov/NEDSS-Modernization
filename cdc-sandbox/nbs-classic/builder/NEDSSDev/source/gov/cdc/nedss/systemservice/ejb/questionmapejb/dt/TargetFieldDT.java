package gov.cdc.nedss.systemservice.ejb.questionmapejb.dt;

import java.util.Collection;

import gov.cdc.nedss.util.AbstractVO;

/**
 * @author habraham2
 *
 */
public class TargetFieldDT extends AbstractVO{
	
	private Long targetFieldUid;
	private Long ruleInstanceUid;
	private String questionIdentifier;
	private String questionLabel;
	private Long pamQuestionUID;
	private Collection<Object>  targetValueColl;
	private String viewLink;
	private String editLink;
	private String deleteLink;
	

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

	public Long getTargetFieldUid() {
		return targetFieldUid;
	}

	public void setTargetFieldUid(Long targetFieldUid) {
		this.targetFieldUid = targetFieldUid;
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
	
	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

	public Collection<Object>  getTargetValueColl() {
		return targetValueColl;
	}

	public void setTargetValueColl(Collection<Object> targetValueColl) {
		this.targetValueColl = targetValueColl;
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

	public Long getPamQuestionUID() {
		return pamQuestionUID;
	}

	public void setPamQuestionUID(Long pamQuestionUID) {
		this.pamQuestionUID = pamQuestionUID;
	}

}
