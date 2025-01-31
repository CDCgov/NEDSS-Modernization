//Source file: C:\\CDC\\Code Frameworks\\gov\\cdc\\nedss\\helpers\\TaskListItemVO.java

package gov.cdc.nedss.proxy.ejb.tasklistproxyejb.vo;

import  gov.cdc.nedss.util.*;

public class TaskListItemVO extends AbstractVO
{

	private boolean custom;//Used from Home Page > Custom Queues, so we can differentiate this type of queues from the pre-defined ones (Open investigation, DRRQ, etc)
	
	private String sourceTable;//Used from Home Page > Custom Queues, to know the type of Event Search used. Right now only available from Event Search > Investigations (I) but in case it is extended in future.
	
	private String queryStringPart1;//The Select part of the query
	
	private String queryStringPart2;//Used from Home Page > Custom Queues, the query used to retrieve the patients to be shown from the custom queue.
	
	private String listOfUsers;//Used from Home Page > Custom Queues, meaning ALL = public, user id = private and only visible by user with corresponding user id.

	private String description;//Description of the queue. It will be shown as a tooltip from the home page custom queues
   
	private String searchCriteriaDesc;//Description of the search criteria applied when the custom queue was created
	
	private String searchCriteriaCd;//Description (code friendly) of the search criteria applied when the custom queue was created
	
	/**
    * holds name of Task List<Object> Item (e.g. "My Program Area's Investigations")
    */
   private String taskListItemName;

   /**
    * holds count of items in the particular Task List<Object> Item (e.g. "My Program Area's
    * Investigations (32)")
    */
   private Integer taskListItemCount;

   /**
    * @roseuid 3C55DAD101BE
    */
   public TaskListItemVO()
   {

   }

   /**
    * Access method for the taskListItemName property.
    *
    * @return   the current value of the taskListItemName property
    */
   public String getTaskListItemName()
   {
      return taskListItemName;
   }

   /**
    * Sets the value of the taskListItemName property.
    *
    * @param aTaskListItemName the new value of the taskListItemName property
    */
   public void setTaskListItemName(String aTaskListItemName)
   {
      taskListItemName = aTaskListItemName;
   }

   /**
    * Access method for the taskListItemCount property.
    *
    * @return   the current value of the taskListItemCount property
    */
   public Integer getTaskListItemCount()
   {
      return taskListItemCount;
   }

   /**
    * Sets the value of the taskListItemCount property.
    *
    * @param aTaskListItemCount the new value of the taskListItemCount property
    */
   public void setTaskListItemCount(Integer aTaskListItemCount)
   {
      taskListItemCount = aTaskListItemCount;
   }

   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
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

    


    public String getSourceTable() {
 	return sourceTable;
 }

 public void setSourceTable(String sourceTable) {
 	this.sourceTable = sourceTable;
 }

 public boolean isCustom() {
 	return custom;
 }

 public void setCustom(boolean custom) {
 	this.custom = custom;
 }


 public String getListOfUsers() {
 	return listOfUsers;
 }

 public void setListOfUsers(String listOfUsers) {
 	this.listOfUsers = listOfUsers;
 }

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getSearchCriteriaDesc() {
	return searchCriteriaDesc;
}

public void setSearchCriteriaDesc(String searchCriteriaDesc) {
	this.searchCriteriaDesc = searchCriteriaDesc;
}

public String getSearchCriteriaCd() {
	return searchCriteriaCd;
}

public void setSearchCriteriaCd(String searchCriteriaCd) {
	this.searchCriteriaCd = searchCriteriaCd;
}

public String getQueryStringPart1() {
	return queryStringPart1;
}

public void setQueryStringPart1(String queryStringPart1) {
	this.queryStringPart1 = queryStringPart1;
}

public String getQueryStringPart2() {
	return queryStringPart2;
}

public void setQueryStringPart2(String queryStringPart2) {
	this.queryStringPart2 = queryStringPart2;
}
 

}


