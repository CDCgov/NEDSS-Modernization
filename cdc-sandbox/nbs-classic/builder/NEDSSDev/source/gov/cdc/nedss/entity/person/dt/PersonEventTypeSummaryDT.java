package gov.cdc.nedss.entity.person.dt;

import gov.cdc.nedss.util.AbstractVO;

public class PersonEventTypeSummaryDT  extends AbstractVO{
	
	private String eventType;
	private String dateReceived;
	private String providerFacility;
	private String eventDate;
	private String description;
	
	
	
	
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
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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

	
	
	
}
