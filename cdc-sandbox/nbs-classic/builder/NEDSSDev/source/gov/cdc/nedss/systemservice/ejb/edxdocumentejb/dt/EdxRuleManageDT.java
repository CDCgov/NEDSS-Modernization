package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;


/**
 * Utility class to capture Rule action and behavior at question level
 * @author Pradeep Kumar Sharma
 *
 */
public class EdxRuleManageDT implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;
	private Collection<Object> defaultCodedValueColl;
	private String defaultNumericValue;
	private String defaultStringValue;
	private String behavior;
	private Long dsmAlgorithmUid;
	private String defaultCommentValue;
	private String logic;
	private String questionId;
	private boolean isAdvanceCriteria;
	private String type;
	private String participationTypeCode;
	private String participationClassCode;
	private Long participationUid;
	
	
	public String getParticipationTypeCode() {
		return participationTypeCode;
	}
	public void setParticipationTypeCode(String participationTypeCode) {
		this.participationTypeCode = participationTypeCode;
	}
	public String getParticipationClassCode() {
		return participationClassCode;
	}
	public void setParticipationClassCode(String participationClassCode) {
		this.participationClassCode = participationClassCode;
	}
	public Long getParticipationUid() {
		return participationUid;
	}
	public void setParticipationUid(Long participationUid) {
		this.participationUid = participationUid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public boolean isAdvanceCriteria() {
		return isAdvanceCriteria;
	}
	public void setAdvanceCriteria(boolean isAdvanceCriteria) {
		this.isAdvanceCriteria = isAdvanceCriteria;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Collection<Object> getDefaultCodedValueColl() {
		return defaultCodedValueColl;
	}
	public void setDefaultCodedValueColl(Collection<Object> defaultCodedValueColl) {
		this.defaultCodedValueColl = defaultCodedValueColl;
	}
	public String getDefaultNumericValue() {
		return defaultNumericValue;
	}
	public void setDefaultNumericValue(String defaultNumericValue) {
		this.defaultNumericValue = defaultNumericValue;
	}
	public String getDefaultStringValue() {
		return defaultStringValue;
	}
	public void setDefaultStringValue(String defaultStringValue) {
		this.defaultStringValue = defaultStringValue;
	}
	public String getBehavior() {
		return behavior;
	}
	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}
	public Long getDsmAlgorithmUid() {
		return dsmAlgorithmUid;
	}
	public void setDsmAlgorithmUid(Long dsmAlgorithmUid) {
		this.dsmAlgorithmUid = dsmAlgorithmUid;
	}
	public String getLogic() {
		return logic;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = EdxRuleManageDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public void setLogic(String logic) {
		this.logic = logic;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getDefaultCommentValue() {
		return defaultCommentValue;
	}
	public void setDefaultCommentValue(String defaultCommentValue) {
		this.defaultCommentValue = defaultCommentValue;
	}
}
