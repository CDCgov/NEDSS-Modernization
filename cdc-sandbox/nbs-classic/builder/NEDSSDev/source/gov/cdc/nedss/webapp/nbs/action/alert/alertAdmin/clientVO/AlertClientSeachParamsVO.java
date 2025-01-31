package gov.cdc.nedss.webapp.nbs.action.alert.alertAdmin.clientVO;

public class AlertClientSeachParamsVO {
	private String event_CD;
	private String condition_CD;
	private String jurisdiction_CD;
	public String getEvent_CD() {
		return event_CD;
	}
	public void setEvent_CD(String event_CD) {
		this.event_CD = event_CD;
	}
	public String getCondition_CD() {
		return condition_CD;
	}
	public void setCondition_CD(String condition_CD) {
		this.condition_CD = condition_CD;
	}
	public String getJurisdiction_CD() {
		return jurisdiction_CD;
	}
	public void setJurisdiction_CD(String jurisdiction_CD) {
		this.jurisdiction_CD = jurisdiction_CD;
	}
}
