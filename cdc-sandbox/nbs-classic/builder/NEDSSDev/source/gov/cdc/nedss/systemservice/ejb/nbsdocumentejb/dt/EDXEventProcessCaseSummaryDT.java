package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt;


public class EDXEventProcessCaseSummaryDT extends EDXEventProcessDT {
  
	private static final long serialVersionUID = 1L;
	
	private String conditionCd;
	private Long personParentUid;
	private Long personUid;
	private String personLocalId;
	public String getConditionCd() {
		return conditionCd;
	}
	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}
	public Long getPersonParentUid() {
		return personParentUid;
	}
	public void setPersonParentUid(Long personParentUid) {
		this.personParentUid = personParentUid;
	}
	public Long getPersonUid() {
		return personUid;
	}
	public void setPersonUid(Long personUid) {
		this.personUid = personUid;
	}
	public String getPersonLocalId() {
		return personLocalId;
	}
	public void setPersonLocalId(String personLocalId) {
		this.personLocalId = personLocalId;
	}
 	 
}
