package gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DsmLogSearchDT implements Serializable{
	
	/**
	 * @author tiej
	 */
	private static final long serialVersionUID = 1L;
	private List<String> processStatus;
	private Date fromDateTime;
    private Date toDateTime;
    private String docType;
    
	
	public List<String> getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(List<String> processStatus) {
		this.processStatus = processStatus;
	}
	public Date getFromDateTime() {
		return fromDateTime;
	}
	public void setFromDateTime(Date fromDate) {
		this.fromDateTime = fromDate;
	}
	public Date getToDateTime() {
		return toDateTime;
	}
	public void setToDateTime(Date toDate) {
		this.toDateTime = toDate;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	
}
