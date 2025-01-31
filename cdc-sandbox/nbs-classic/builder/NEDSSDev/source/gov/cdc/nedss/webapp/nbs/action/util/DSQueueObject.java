package gov.cdc.nedss.webapp.nbs.action.util;

public class DSQueueObject {
	
	private String dSSortColumn;
	private String dSSortDirection;
	private String dSFromIndex;
	private String dSQueueType;
	public String getDSFromIndex() {
		return dSFromIndex;
	}
	public void setDSFromIndex(String fromIndex) {
		dSFromIndex = fromIndex;
	}
	public String getDSQueueType() {
		return dSQueueType;
	}
	public void setDSQueueType(String queueType) {
		dSQueueType = queueType;
	}
	public String getDSSortColumn() {
		return dSSortColumn;
	}
	public void setDSSortColumn(String sortColumn) {
		dSSortColumn = sortColumn;
	}
	public String getDSSortDirection() {
		return dSSortDirection;
	}
	public void setDSSortDirection(String sortDirection) {
		dSSortDirection = sortDirection;
	}

}
