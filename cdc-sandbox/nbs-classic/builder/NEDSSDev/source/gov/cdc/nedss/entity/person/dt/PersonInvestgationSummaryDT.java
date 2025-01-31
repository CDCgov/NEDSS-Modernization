package gov.cdc.nedss.entity.person.dt;

import gov.cdc.nedss.util.AbstractVO;

public class PersonInvestgationSummaryDT  extends AbstractVO{
	private String actionLink;
	private String rscSecRef;
	private String startDate;
	private String status;
	private String conditions;
	private String caseStatus;
	private String notification;
	private String jurisdiction;
	private String investigator;
	private String investigationId;
	private String coinfectionId;
	private String disposition;
	private String mergeInvStatus;


	public String getActionLink() {
		return actionLink;
	}
	public void setActionLink(String actionLink) {
		this.actionLink = actionLink;
	}
	/*
	 * Record Search Closure/Secondary Referral
	 * is derived from the other data
	 */
	public String getRscSecRef() {
		return rscSecRef;
	}
	public void setRscSecRef(String rscSecRef) {
		this.rscSecRef = rscSecRef;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public String getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getJurisdiction() {
		return jurisdiction;
	}
	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
	public String getInvestigator() {
		return investigator;
	}
	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}
	public String getInvestigationId() {
		return investigationId;
	}
	public void setInvestigationId(String investigationId) {
		this.investigationId = investigationId;
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
	public String getCoinfectionId() {
		return coinfectionId;
	}
	public void setCoinfectionId(String coinfectionId) {
		this.coinfectionId = coinfectionId;
	}
	public String getDisposition() {
		return disposition;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public String getMergeInvStatus() {
		return mergeInvStatus;
	}
	public void setMergeInvStatus(String mergeInvStatus) {
		this.mergeInvStatus = mergeInvStatus;
	}
	



}
