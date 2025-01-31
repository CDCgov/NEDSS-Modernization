package gov.cdc.nedss.systemservice.ejb.questionmapejb.dt;

import java.lang.reflect.Field;
import java.util.Collection;
import gov.cdc.nedss.util.AbstractVO;

public class RulesDT extends AbstractVO{
	private Long ruleUid;
	private Long ruleInstanceUid;
	private String ruleName;
	private String ruleComment;
	private String formCode;
	private String comments;
	private String conseqIndCode;
	private Collection<Object>  sourceColl;
	private Collection<Object>  targetColl;
	private String questionIdentifer;
	private String questionLabel;
	private String conseqIndtxt;
	private String viewLink;
	private String editLink;
	private String deleteLink;
	private String previewLink;
	private String errMessageCode;
	private String errMessagetxt;
	private String pamLabelCode;
	private String pamLabeltxt;
	private String pamIdentifiertxt;
	private String opTypeCode;
	private String opTypetxt;
	private Long conseqIndUID;
	private Long errMessageUID;
	private Long opTypeUID;
	private Long pamQuestionUID;
	private Long nbsMetadataRuleUID;

	public Collection<Object>  getSourceColl() {
		return sourceColl;
	}

	public void setSourceColl(Collection<Object> sourceColl) {
		this.sourceColl = sourceColl;
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

	public Long getRuleUid() {
		return ruleUid;
	}

	public void setRuleUid(Long ruleUid) {
		this.ruleUid = ruleUid;
	}

	public Long getRuleInstanceUid() {
		return ruleInstanceUid;
	}

	public void setRuleInstanceUid(Long ruleInstanceUid) {
		this.ruleInstanceUid = ruleInstanceUid;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleComment() {
		return ruleComment;
	}

	public void setRuleComment(String ruleComment) {
		this.ruleComment = ruleComment;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getConseqIndCode() {
		return conseqIndCode;
	}

	public void setConseqIndCode(String conseqIndCode) {
		this.conseqIndCode = conseqIndCode;
	}

	public Collection<Object>  getTargetColl() {
		return targetColl;
	}

	public void setTargetColl(Collection<Object> targetColl) {
		this.targetColl = targetColl;
	}

	public String getQuestionIdentifer() {
		return questionIdentifer;
	}

	public void setQuestionIdentifer(String questionIdentifer) {
		this.questionIdentifer = questionIdentifer;
	}

	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

	public String getConseqIndtxt() {
		return conseqIndtxt;
	}

	public void setConseqIndtxt(String conseqIndtxt) {
		this.conseqIndtxt = conseqIndtxt;
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

	public String getErrMessageCode() {
		return errMessageCode;
	}

	public void setErrMessageCode(String errMessageCode) {
		this.errMessageCode = errMessageCode;
	}

	public String getErrMessagetxt() {
		return errMessagetxt;
	}

	public void setErrMessagetxt(String errMessagetxt) {
		this.errMessagetxt = errMessagetxt;
	}

	public String getPamLabelCode() {
		return pamLabelCode;
	}

	public void setPamLabelCode(String pamLabelCode) {
		this.pamLabelCode = pamLabelCode;
	}

	public String getPamLabeltxt() {
		return pamLabeltxt;
	}

	public void setPamLabeltxt(String pamLabeltxt) {
		this.pamLabeltxt = pamLabeltxt;
	}

	public String getPamIdentifiertxt() {
		return pamIdentifiertxt;
	}

	public void setPamIdentifiertxt(String pamIdentifiertxt) {
		this.pamIdentifiertxt = pamIdentifiertxt;
	}

	public String getOpTypeCode() {
		return opTypeCode;
	}

	public void setOpTypeCode(String opTypeCode) {
		this.opTypeCode = opTypeCode;
	}

	public String getOpTypetxt() {
		return opTypetxt;
	}

	public void setOpTypetxt(String opTypetxt) {
		this.opTypetxt = opTypetxt;
	}

	public String getPreviewLink() {
		return previewLink;
	}

	public void setPreviewLink(String previewLink) {
		this.previewLink = previewLink;
	}

	public Long getConseqIndUID() {
		return conseqIndUID;
	}

	public void setConseqIndUID(Long conseqIndUID) {
		this.conseqIndUID = conseqIndUID;
	}

	public Long getErrMessageUID() {
		return errMessageUID;
	}

	public void setErrMessageUID(Long errMessageUID) {
		this.errMessageUID = errMessageUID;
	}

	public Long getOpTypeUID() {
		return opTypeUID;
	}

	public void setOpTypeUID(Long opTypeUID) {
		this.opTypeUID = opTypeUID;
	}

	public Long getPamQuestionUID() {
		return pamQuestionUID;
	}

	public void setPamQuestionUID(Long pamQuestionUID) {
		this.pamQuestionUID = pamQuestionUID;
	}

	public Long getNbsMetadataRuleUID() {
		return nbsMetadataRuleUID;
	}

	public void setNbsMetadataRuleUID(Long nbsMetadataRuleUID) {
		this.nbsMetadataRuleUID = nbsMetadataRuleUID;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = RulesDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

}
