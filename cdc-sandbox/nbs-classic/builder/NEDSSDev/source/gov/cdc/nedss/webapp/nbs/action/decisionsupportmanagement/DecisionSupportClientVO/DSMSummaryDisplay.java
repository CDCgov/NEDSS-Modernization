package gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO;

import java.sql.Timestamp;

public class DSMSummaryDisplay {

	private String eventType;
	private String algorithmName;
	private String relatedConditions;
	private String relatedConditionsPrint;
	private String advancedCriteria;
	private String action;
	private String status;
	private String viewLink;
	private String editLink;
	private Timestamp lastChgTime;
	private Long DSMUid;
	
	public Long getDSMUid() {
		return DSMUid;
	}
	public void setDSMUid(Long dSMUid) {
		DSMUid = dSMUid;
	}
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getAlgorithmName() {
		return algorithmName;
	}
	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	public String getRelatedConditions() {
		return relatedConditions;
	}
	public void setRelatedConditions(String relatedConditions) {
		this.relatedConditions = relatedConditions;
	}
	public String getAdvancedCriteria() {
		return advancedCriteria;
	}
	public void setAdvancedCriteria(String advancedCriteria) {
		this.advancedCriteria = advancedCriteria;
	}
	public String getRelatedConditionsPrint() {
		return relatedConditionsPrint;
	}
	public void setRelatedConditionsPrint(String relatedConditionsPrint) {
		this.relatedConditionsPrint = relatedConditionsPrint;
	}
}
