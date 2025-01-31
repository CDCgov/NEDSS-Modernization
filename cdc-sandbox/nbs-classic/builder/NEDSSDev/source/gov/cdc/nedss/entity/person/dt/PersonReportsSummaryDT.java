package gov.cdc.nedss.entity.person.dt;

import java.util.Map;

import gov.cdc.nedss.util.AbstractVO;

public class PersonReportsSummaryDT  extends AbstractVO{
	
	private String eventType;
	private String dateReceived;
	private String providerFacility;
	private String dateCollected;
	private String description;
	private String testResults;
	private String associatedWith;
	private String eventId;
	private String createDate;
	private String dateNamed;
	  private String[] selectedcheckboxIds;
	  private String checkBoxId;
	  private Long observationUid;
	private String progArea;
	private String jurisdiction;
	  private boolean isReactor = false;
	  private String disabled = "";
	  
	  private boolean isAssociated = false;
	  
	public String getDateNamed() {
		return dateNamed;
	}
	public void setDateNamed(String dateNamed) {
		this.dateNamed = dateNamed;
	}
	public String getNamedBy() {
		return namedBy;
	}
	public void setNamedBy(String namedBy) {
		this.namedBy = namedBy;
	}
	private String namedBy;
	
	
	
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}
	public String getProviderFacility() {
		return providerFacility;
	}
	public void setProviderFacility(String providerFacility) {
		this.providerFacility = providerFacility;
	}
	public String getDateCollected() {
		return dateCollected;
	}
	public void setDateCollected(String dateCollected) {
		this.dateCollected = dateCollected;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTestResults() {
		return testResults;
	}
	public void setTestResults(String testResults) {
		this.testResults = testResults;
	}
	public String getAssociatedWith() {
		return associatedWith;
	}
	public void setAssociatedWith(String associatedWith) {
		this.associatedWith = associatedWith;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	
	
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}
	public boolean isReactor() {
		return isReactor;
	}
	public void setReactor(boolean isReactor) {
		this.isReactor = isReactor;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String[] getSelectedcheckboxIds() {
		return selectedcheckboxIds;
	}

	public void setSelectedcheckboxIds(String[] selectedcheckboxIds) {
		this.selectedcheckboxIds = selectedcheckboxIds;
	}

	public String getCheckBoxId() {
		if(isAssociated) {
			checkBoxId = "checked=\"true\"";
		}
		return checkBoxId;
	}

	public void setCheckBoxId(String checkBoxId) {
		this.checkBoxId = checkBoxId;
	}
	
  public boolean getIsAssociated() {
	    return isAssociated;
	  }

	  public void setItAssociated(boolean associated) {
	    isAssociated = associated;
	  }
	public Long getObservationUid() {
		return observationUid;
	}
	public void setObservationUid(Long observationUid) {
		this.observationUid = observationUid;
	}
	public String getProgArea() {
		return progArea;
	}
	public void setProgArea(String progArea) {
		this.progArea = progArea;
	}
	
	public String getJurisdiction() {
		return jurisdiction;
	}
	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
}


