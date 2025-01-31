package gov.cdc.nedss.proxy.ejb.tasklistproxyejb.vo;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class CustomQueueVO extends AbstractVO implements RootDTInterface
{


   private String queueName;//Name of the custom queue, must be unique
   private String sourceTable;//In case it is extended in future. Right now, only available for investigations, so it is indicated as I, but if it is extended for Lab Reports, it could be indicated as LR.
   private String queryStringPart1;//The select part of the query.
   private String queryStringPart2;//The actual query that needs to be run to retrieve the patients to be shown in the custom queue
   private String description;//Description of the custom queue
   private String listOfUsers;//ALL = Public, or user id, in case it is private and only visible by that user
   private String recordStatusCd;
   private Timestamp recordStatusTime;
   private Timestamp lastChgTime;
   private Long lastChgUserId;
   private Timestamp addTime;
   private Long addUserId;
   private String searchCriteriaDesc;//Description of the search criteria applied when the queue was created
   private String searchCriteriaCd;//Description (code friendly) of the search criteria applied when the queue was created
   
   
   

   public String getQueueName() {
	return queueName;
}
	
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	public String getSourceTable() {
		return sourceTable;
	}
	
	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}	
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
 
	public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass){
    return true;
   }

   public void setItDirty(boolean itDirty)
   {

   }

    public boolean isItDirty()
   {
    return itDirty;
   }

   public void setItNew(boolean itNew)
   {

   }

    public boolean isItNew()
   {
    return itNew;
   }

   public void setItDelete(boolean itDelete)
   {

   }
    public boolean isItDelete()
   {
    return itDelete;
   }

	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getListOfUsers() {
		return listOfUsers;
	}

	public void setListOfUsers(String listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

	public String getSearchCriteriaDesc() {
		return searchCriteriaDesc;
	}

	public String getSearchCriteriaCd() {
		return searchCriteriaCd;
	}

	public void setSearchCriteriaDesc(String searchCriteriaDesc) {
		this.searchCriteriaDesc = searchCriteriaDesc;
	}

	public void setSearchCriteriaCd(String searchCriteriaCd) {
		this.searchCriteriaCd = searchCriteriaCd;
	}

	public String getQueryStringPart1() {
		return queryStringPart1;
	}

	public String getQueryStringPart2() {
		return queryStringPart2;
	}

	public void setQueryStringPart1(String queryStringPart1) {
		this.queryStringPart1 = queryStringPart1;
	}

	public void setQueryStringPart2(String queryStringPart2) {
		this.queryStringPart2 = queryStringPart2;
	}

}
