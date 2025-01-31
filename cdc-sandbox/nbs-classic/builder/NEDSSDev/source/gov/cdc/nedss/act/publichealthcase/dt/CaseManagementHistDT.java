package gov.cdc.nedss.act.publichealthcase.dt;

/**
 * Data Transfer object to create and view  Case Management questions for history(DT Object
 * since Release 4.5)
 * @author Pradeep Kumar Sharma
 * @company: Leidos
 *
 */

public class CaseManagementHistDT extends CaseManagementDT {
	
	private static final long serialVersionUID = 1L;

	private Long  caseManagementHistUid;
	private Integer versionCtrlNbr;
	
	public Long getCaseManagementHistUid() {
		return caseManagementHistUid;
	}
	
	public void setCaseManagementHistUid(Long caseManagementHistUid) {
		this.caseManagementHistUid = caseManagementHistUid;
	}
	
	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}
	
	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
	}
}
