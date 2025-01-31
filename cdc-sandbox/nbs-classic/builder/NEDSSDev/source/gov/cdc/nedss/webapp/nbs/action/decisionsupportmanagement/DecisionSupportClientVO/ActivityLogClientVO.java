package gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO;

import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;

public class ActivityLogClientVO extends ClientVO {

	private String[] processStatus;
	private String processingDateFrom;
	private String processingDateTo;
	private String processingTimeFrom;
	private String processingTimeTo;
	
	public String[] getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String[] status) {
		this.processStatus = new String[status.length];
		this.processStatus = status;
	}
	public String getProcessingDateFrom() {
		return processingDateFrom;
	}
	public void setProcessingDateFrom(String processingDateFrom) {
		this.processingDateFrom = processingDateFrom;
	}
	public String getProcessingDateTo() {
		return processingDateTo;
	}
	public void setProcessingDateTo(String processingDateTo) {
		this.processingDateTo = processingDateTo;
	}
	public String getProcessingTimeFrom() {
		return processingTimeFrom;
	}
	public void setProcessingTimeFrom(String processingTimeFrom) {
		this.processingTimeFrom = processingTimeFrom;
	}
	public String getProcessingTimeTo() {
		return processingTimeTo;
	}
	public void setProcessingTimeTo(String processingTimeTo) {
		this.processingTimeTo = processingTimeTo;
	}

}
