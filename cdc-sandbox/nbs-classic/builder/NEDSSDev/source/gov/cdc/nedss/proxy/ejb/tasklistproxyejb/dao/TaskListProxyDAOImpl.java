package gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao;

import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseDAOImpl;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryVO;
import gov.cdc.nedss.proxy.ejb.queue.dao.MessageLogDAOImpl;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.vo.CustomQueueVO;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.vo.TaskListItemVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.CtrlCdDisplaySummaryVO;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TaskListProxyDAOImpl extends DAOBase
{
    public TaskListProxyDAOImpl()
    {
     
    }

    private static final LogUtils logger = new LogUtils(TaskListProxyDAOImpl.class.getName());
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
      
    
    /**
     * isExistingCustomQueueName: it checks if the custom queue name already exists in the DB within public queues, or within private queues for that specific user
     * @param queueName
     * @return
     * @throws Exception
     */
    public boolean isExistingCustomQueueName(String queueName, String currentUser) throws Exception{
      	
      	boolean existing = false;
      	String aQuery = "SELECT COUNT(*) FROM custom_queues where queue_name = '"+queueName+"' and record_status_cd = 'ACTIVE' and (list_of_users='ALL' or (list_of_users="+currentUser+"))";
      	
      	 try{
   	         logger.debug(aQuery);
   	         Integer count = null;
  	         logger.debug("Begin execution of TaskListProxyDAOImpl.isExistingCustomQueueName() query :"+aQuery);
  	         count = ((Integer)preparedStmtMethod(null,null,aQuery,NEDSSConstants.SELECT_COUNT));
  	         logger.debug("End execution of TaskListProxyDAOImpl.isExistingCustomQueueName() query :"+aQuery);
  	         logger.info("getCount - count = " + count);
  	
  	         if(count>0)
  	        	 existing = true;
       	 }catch(Exception ex){
       		 logger.fatal("TaskListProxyDAOImpl.java >  isExistingCustomQueueName = "+ex.getMessage(), ex);
       		 throw new Exception(ex.toString());
       	 }

      	return existing;
      	
      }
    
    /**
     * isCurrentUserOwnerOfCustomQueue: it returns true if the current user is the owner of the Custom Private Queue. If either
     * the current user is not the owner or it is not a private queue, the user won't be able to soft delete the queue
     * @param queueName
     * @return
     * @throws Exception
     */
    public boolean isCurrentUserOwnerOfCustomPrivateQueue(String queueName, String currentUser) throws Exception{
      	
      	boolean existing = false;
      	String aQuery = "SELECT COUNT(*) FROM custom_queues where queue_name = '"+queueName+"' and list_of_users = '"+currentUser+"' and add_user_id = '"+currentUser+"'";
      	
      	 try{
   	         logger.debug(aQuery);
   	         Integer count = null;
  	         logger.debug("Begin execution of TaskListProxyDAOImpl.isCurrentUserOwnerOfCustomQueue() query :"+aQuery);
  	         count = ((Integer)preparedStmtMethod(null,null,aQuery,NEDSSConstants.SELECT_COUNT));
  	         logger.debug("End execution of TaskListProxyDAOImpl.isCurrentUserOwnerOfCustomQueue() query :"+aQuery);
  	         logger.info("getCount - count = " + count);
  	
  	         if(count>0)
  	        	 existing = true;
       	 }catch(Exception ex){
       		 logger.fatal("TaskListProxyDAOImpl.java >  isExistingCustomQueueName = "+ex.getMessage(), ex);
       		 throw new Exception(ex.toString());
       	 }

      	return existing;
      	
      }

    
    /**
     * insertCustomQueue: it will add a new custom queue to the custom_queues table
     * @param queueName
     * @param sourceTable
     * @param searchCriteriaString
     * @param description
     * @param userId
     * @param nbsSecurityObj
     */
 public String insertCustomQueue(String queueName,String sourceTable,String queryString,String description,String listOfUsers, Long userId, String searchCriteriaDesc, String searchCriteriaCd, NBSSecurityObj nbsSecurityObj){
    	
    	java.util.Date dateTime = new java.util.Date();
    	Timestamp systemTime = new Timestamp(dateTime.getTime());
    	String querySelect="";
    	//The query needs to be shortened because the max length of column in SQL is 8000 characteres. We can remove the select part
    	//knowing it is always the same
    	  	
    	logger.debug("before Truncating query::::::::::::::"+queryString);
    	
     	if("LR".equalsIgnoreCase(sourceTable) && null != queryString){
    		int whereClauseIndex=queryString.toUpperCase().indexOf(" WHERE ");
    		querySelect=queryString.substring(0,whereClauseIndex);
    		queryString = queryString.substring(whereClauseIndex);
       		
    		//Remove OIDS from Where Clause because database length restrictions
    		int oidStartIndex=  querySelect.indexOf("((obs.program_jurisdiction_oid");
    		if(querySelect!=null && oidStartIndex !=-1){
    			int oidEndIndex= querySelect.toLowerCase().indexOf("))) and");
    			if(oidEndIndex !=-1) {
    				//querySelect= new StringBuffer(querySelect).delete(oidStartIndex, oidEndIndex+7).toString();
    				querySelect= new StringBuffer(querySelect).replace(oidStartIndex,oidEndIndex+3,"{program_jurisdiction_oid}").toString();
    			}
    		}
    		
    		int oidStartIndex1=  queryString.indexOf("((obs.program_jurisdiction_oid");
    		if(queryString!=null && oidStartIndex1 !=-1){
    			int oidEndIndex1= queryString.toLowerCase().indexOf("))) and");
    			if(oidEndIndex1 !=-1) {
    				//queryString= new StringBuffer(queryString).delete(oidStartIndex1, oidEndIndex1+7).toString();
    				queryString= new StringBuffer(queryString).replace(oidStartIndex1, oidEndIndex1+3,"{program_jurisdiction_oid}").toString();
    			}
    		}
     		//Add place holder Ex: TOP 100 will be like TOP {COUNT}
    		
    		int countRows=  propertyUtil.getLabNumberOfRows(); 		
    		if(null != queryString && queryString.trim().length() > 0) {
    			int initialCountIndex=queryString.toLowerCase().indexOf("top "+countRows);
    			if(initialCountIndex !=-1) {
    				queryString= queryString.replaceAll("top "+countRows, "top {count}");
    			}
    		}
    		
    		logger.debug("querySelect:::::::::::"+querySelect);
    		logger.debug("queryString:::::::::::"+queryString);
    		
    		
    	} else {
    	String stringToCut = "from Public_health_case Public_Health_Case with (nolock) LEFT";
    	if(queryString!=null && queryString.indexOf(stringToCut)!=-1){
    		querySelect = queryString.substring(0,queryString.indexOf(stringToCut));
    		queryString = queryString.substring(queryString.indexOf(stringToCut));
    				
    				
    	}
    	//Removing oids before storing in db
    	String oidsToRemove =" and ((Public_Health_Case.program_jurisdiction_oid ";
    	if(queryString!=null && queryString.indexOf(oidsToRemove)!=-1){
    		String part1 = queryString.substring(0,queryString.indexOf(oidsToRemove));
    		String part2 = 	queryString.substring(queryString.indexOf(oidsToRemove));
    		String part3 = part2.substring(part2.indexOf(")))")+3);
    		queryString = part1+part3;
    		
    				
    	}
    	
    	}
    	logger.debug("after Truncating query where cluase"+queryString);
    	
    	String aQuery="INSERT INTO custom_queues values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	ArrayList<Object> paramList = new ArrayList<Object>();
    	paramList.add(queueName);
    	paramList.add(sourceTable);
    	paramList.add(querySelect);
    	paramList.add(queryString);
    	paramList.add(description);
    	paramList.add(listOfUsers);//List of users allowed to see the queue separated by |, or ALL, or PUBLIC, etc.
    	paramList.add(searchCriteriaDesc);//search criteria description
    	paramList.add(searchCriteriaCd);//search criteria code
    	paramList.add("ACTIVE");//record_status_cd
    	paramList.add(systemTime);//record_status_time
    	paramList.add(systemTime);//last_chg_time
    	paramList.add(userId);//last_chg_user_id
    	paramList.add(systemTime);//add_time
    	paramList.add(userId);//add_user_id
    	
    	
    	try{
  	        logger.debug(aQuery);
  	        Integer count = null;
             logger.debug("Begin execution of TaskListProxyDAOImpl.insertCustomQueue() query :"+aQuery);
             count = ((Integer)preparedStmtMethod(null,paramList,aQuery,NEDSSConstants.UPDATE));
             logger.debug("End execution of TaskListProxyDAOImpl.insertCustomQueue() query :"+aQuery);
             logger.info("getCount - count = " + count);
             return "0";
            // return count;
     	 }catch(Exception ex){
     		 logger.fatal("Exception  = "+ex.getMessage(), ex);
     		 ex.printStackTrace();
     		 return "2";
     	 }
    	 
    	 
    }

    
    /**
     * deleteCustomQueue: it deletes logically the queue with same name as received as a parameter
     * @param queueName
     * @param nbsSecurityObj
     * @return
     */
    public String deleteCustomQueue(String queueName, String userId, NBSSecurityObj nbsSecurityObj){
    	
    	java.util.Date dateTime = new java.util.Date();
    	Timestamp systemTime = new Timestamp(dateTime.getTime());
    	

    	
    	String aQuery="UPDATE custom_queues SET record_status_cd = 'LOG_DEL', last_chg_time = '"+systemTime+"', record_status_time = '"+systemTime+"' where queue_name = '"+queueName+"' and list_of_users in ('ALL','"+userId+"')";
    	ArrayList<Object> paramList = new ArrayList<Object>();

    	//paramList.add(systemTime);//last_chg_time
    	//paramList.add(queueName);
  		
    	try{
  	        logger.debug(aQuery);
  	        Integer count = null;
             logger.debug("Begin execution of TaskListProxyDAOImpl.deleteCustomQueue() query :"+aQuery);
             count = ((Integer)preparedStmtMethod(null,paramList,aQuery,NEDSSConstants.UPDATE));
             logger.debug("End execution of TaskListProxyDAOImpl.deleteCustomQueue() query :"+aQuery);
             logger.info("getCount - count = " + count);
             return "0";
            // return count;
     	 }catch(Exception ex){
     		 logger.fatal("Exception  = "+ex.getMessage(), ex);
     		 return "2";
     		// throw new Exception(ex.toString());
     	 }
    	 
    	 
    }

 

    /**
     * getCustomQueues: reads from the database the custom queues defined by users
     * @param nbssecurityObj
     * @return
     * @throws Exception
     */
  	public ArrayList<TaskListItemVO> getCustomQueues(NBSSecurityObj nbssecurityObj) throws Exception
    {
  	   CustomQueueVO customQueueVO = new CustomQueueVO();
  	   TaskListItemVO  taskListVO=null;
       Map<String, ArrayList<CustomQueueVO>> customQueuesHashMap = new HashMap<String, ArrayList<CustomQueueVO>>();
       ArrayList<Object>  ar = new ArrayList<Object> ();
       ArrayList<Object> queryParams  = new ArrayList<Object> ();
       ArrayList<List<TaskListItemVO>> userCustomQueues  = new ArrayList<List<TaskListItemVO>> ();
       ArrayList<TaskListItemVO>   reportCustomQueues  = new ArrayList<TaskListItemVO> ();

  	   	String userIdString = nbssecurityObj.getTheUserProfile().getTheUser().getEntryID();
  	   	Long userId = Long.parseLong(userIdString);
   	
       try
       {
              String aQuery;
               aQuery = WumSqlQuery.SELECT_CUSTOM_QUEUES_SQL;
               aQuery+="OR list_of_users like '%"+userId+"%')";
               aQuery+=" order by source_table, queue_name, last_chg_time asc";
               logger.info("aQuery = " + aQuery);
             //  queryParams.add(userId);
               logger.info("getCustomQueues  - aQuery = " + aQuery);
               ar = (ArrayList<Object> )preparedStmtMethod(customQueueVO,queryParams, aQuery, NEDSSConstants.SELECT);
               if(ar!=null) {            	           
               for (Iterator<Object> anIterator = ar.iterator(); anIterator.hasNext(); ) {
   	                 logger.debug("getCustomQueues anIterator.hasNext()" + anIterator.hasNext());
   	                 customQueueVO = (CustomQueueVO) anIterator.next();
   	                 if(null != customQueueVO ) {
   	                	taskListVO= new TaskListItemVO();
   	                   	taskListVO.setTaskListItemName(customQueueVO.getQueueName());
   	                   	taskListVO.setTaskListItemCount(0);//TODO: remove?
   	                   	taskListVO.setCustom(true);
   	                   	taskListVO.setSourceTable(customQueueVO.getSourceTable());
   	                   	taskListVO.setQueryStringPart1(customQueueVO.getQueryStringPart1());
   	                   	taskListVO.setQueryStringPart2(customQueueVO.getQueryStringPart2());
   	                   	taskListVO.setListOfUsers(customQueueVO.getListOfUsers());
   	                   	taskListVO.setDescription(customQueueVO.getDescription());
   	                   	taskListVO.setSearchCriteriaDesc(customQueueVO.getSearchCriteriaDesc());
   	                   	taskListVO.setSearchCriteriaCd(customQueueVO.getSearchCriteriaCd());
   	                   	reportCustomQueues.add(taskListVO);
   	                 } 
               } 
               
            }   
   	              logger.debug("custom Ques size::::"+reportCustomQueues.size());
          
       }catch(Exception ex){
    	   logger.info("Exception While getting custom Queues");
           ex.printStackTrace();
       }
       return reportCustomQueues;
    }

  	
  	

      /**
       * getCustomQueuesByType: returns the custom queues that are same type than the one received as a parameter
       * @param arCustom
       * @param type
       * @return
       */
      public ArrayList<Object> getCustomQueuesByType (HashMap<String,ArrayList<CustomQueueVO>> arCustom, String type){
     	 //Translating the type from constants to the value stored in the database 
     	 
     	 switch(type){
     	 //All but I are part of a different POC so they are removed for now.
  	    	/* case (NEDSSConstants.OPEN_INVESTIGATIONS):
  		    	 	type = "OpenInvestigations";
  	    	 break;
  	    	 case (NEDSSConstants.ELRS_NEEDING_PROGRAM_OR_JURISDICTION_ASSIGNMENT):
  		    	 	type = "DRSAQ";
  	    	 break;
  	    	 case (NEDSSConstants.NND_NOTIFICATIONS_FOR_APPROVAL):
  		    	 	type = "ApprovalNotifications";
  	    	 break;
  	    	 case (NEDSSConstants.NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL):
  		    	 	type = "UpdatedNotifications";
  	    	 break;*/
  	    	 case ("I"):
  		    	 	type = "I";//Investigation
  	    	 break;
  	    	//TODO: continue in case it is extended for other events, like Lab Reports, etc.
     	 
     	 }
     	 ArrayList<Object> queuesByType = new ArrayList<Object>();
     	 ArrayList<CustomQueueVO> ar = arCustom.get(type);
     	 
     	 if(ar!=null)
     		 for(int i =0; i< ar.size(); i++){
     			 
     			String nameCustomQUeue = ((CustomQueueVO)ar.get(i)).getQueueName();
     			String searchCriteria1 = ((CustomQueueVO)ar.get(i)).getQueryStringPart1();//select part
      		    String searchCriteria2 = ((CustomQueueVO)ar.get(i)).getQueryStringPart2();
     			String listOfUsers = ((CustomQueueVO)ar.get(i)).getListOfUsers();
     			String description = ((CustomQueueVO)ar.get(i)).getDescription();
     			String searchCriteriaDesc = ((CustomQueueVO)ar.get(i)).getSearchCriteriaDesc();
     			String searchCriteriaCd = ((CustomQueueVO)ar.get(i)).getSearchCriteriaCd();
   			 
         		 
     	 TaskListItemVO taskListItemVOOCustom = new TaskListItemVO();
     	 taskListItemVOOCustom
  					.setTaskListItemName(nameCustomQUeue);
  			try {
  				//ArrayList<Object> myInvestigations = (ArrayList<Object>) taskListProxyDAOImpl
  					//	.getInvestigationSummaryVOCollection(nbssecurityObj);
  				taskListItemVOOCustom.setTaskListItemCount(0);//TODO: remove?
  				taskListItemVOOCustom.setCustom(true);
  				taskListItemVOOCustom.setSourceTable(type);
  				taskListItemVOOCustom.setQueryStringPart1(searchCriteria1);
  				taskListItemVOOCustom.setQueryStringPart2(searchCriteria2);
  				taskListItemVOOCustom.setListOfUsers(listOfUsers);
  				taskListItemVOOCustom.setDescription(description);
  				taskListItemVOOCustom.setSearchCriteriaDesc(searchCriteriaDesc);
  				taskListItemVOOCustom.setSearchCriteriaCd(searchCriteriaCd);
  				
  				
  			} catch (NEDSSSystemException e) {
  				logger.fatal(
  						"TaskListProxyEJB.getTaskListItems: errro while getting open investigations count: "
  								+ e.getMessage(), e);
  				// throw new NEDSSSystemException(e.getMessage(),e);
  			}
  			
  			queuesByType.add(taskListItemVOOCustom);
     		 }
     	 return queuesByType;
     	 
      }
    /**
     * getCount
     * returns the count for task list available
     * @param aQuery String
     * @return Integer
     * @throws Exception
     */
     public Integer getCount(String aQuery) throws Exception
     {
    	 try{
	        logger.debug(aQuery);
	        Integer count = null;
            logger.debug("Begin execution of TaskListProxyDAOImpl.getCount() query :"+aQuery);
            count = ((Integer)preparedStmtMethod(null,null,aQuery,NEDSSConstants.SELECT_COUNT));
            logger.debug("End execution of TaskListProxyDAOImpl.getCount() query :"+aQuery);
            logger.info("getCount - count = " + count);

            return count;
    	 }catch(Exception ex){
    		 logger.fatal("Exception  = "+ex.getMessage(), ex);
    		 throw new Exception(ex.toString());
    	 }
     }
     
	public Integer getCountbySP(String aQuery) throws Exception {
		try {
			logger.debug(aQuery);
			Integer count = null;
			//aQuery = aQuery.replace("count(*)", "count(*) \"t_count\"");
			logger.debug("Begin execution of TaskListProxyDAOImpl.getCountbySP() query :" + aQuery);
			ArrayList<Object> inArrayList = new ArrayList<Object>();
			ArrayList<Object> outArrayList = new ArrayList<Object>();
			ArrayList<Object> arrayList = new ArrayList<Object>();
			inArrayList.add(aQuery);// Query to be executed
			outArrayList.add(new Integer(java.sql.Types.BIGINT)); // count

			String sQuery = "{call GETCOUNT_SP(?,?)}";
			arrayList = (ArrayList<Object>) callStoredProcedureMethod(sQuery, inArrayList, outArrayList);

			if (arrayList != null && arrayList.size() > 0) {
				count = new Integer(((Long)arrayList.get(0)).intValue());
			}
			logger.debug("End execution of TaskListProxyDAOImpl.getCountbySP() query :" + aQuery);
			logger.info("getCount - count = " + count);

			return count;
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}

     // called by TaskListProxyEJB.getMyInvestigations method
     @SuppressWarnings("unchecked")
	public Collection<Object>  getInvestigationSummaryVOCollection(NBSSecurityObj nbssecurityObj) throws Exception
     {
        InvestigationSummaryVO investigationSummaryVO = new InvestigationSummaryVO();
        ArrayList<Object>  ar = new ArrayList<Object> ();
        logger.debug("getInvestigationSummaryVOCollection  - ar = " + ar);
        logger.debug("Begin execution of TaskListProxyDAOImpl.getInvestigationSummaryVOCollection  method");
        // Get Security Properties for MyPAInvestigations 
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        logger.debug("TaskListProxyDAOImpl.getInvestigationSummaryVOCollection(NBSSecurityObj) - myPAInvestigationsSecurity = " + myPAInvestigationsSecurity);
      
        if (myPAInvestigationsSecurity==null)
            throw new NEDSSSystemException("TaskListProxyDAOImpl.getInvestigationSummaryVOCollection  - Security property settings missing for MyPAInvestigations");
       		
        if ( !(myPAInvestigationsSecurity.equals(NBSOperationLookup.VIEW)) &&
           	 !(myPAInvestigationsSecurity.equals(NBSOperationLookup.EDIT)) )
               throw new NEDSSSystemException("TaskListProxyDAOImpl.getInvestigationSummaryVOCollection  - Security property settings for MyPAInvestigations is incorrect");
        
        try
        {
            if(nbssecurityObj.getPermission(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity))
            {
                String dataAccessWhereClause = nbssecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
                if(dataAccessWhereClause == null)
                   dataAccessWhereClause = "";
                else
                   dataAccessWhereClause = "AND " + dataAccessWhereClause;

                String aQuery = WumSqlQuery.SELECT_MY_INVESTIGATIONS_SQL +
                            "where (Public_health_case.investigation_status_cd = '" + NEDSSConstants.STATUS_OPEN +
                            "' AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
                            "' " + dataAccessWhereClause+")";
                logger.info("aQuery = " + aQuery);

                logger.info("getInvestigationSummaryVOCollection  - aQuery = " + aQuery);
                ar = (ArrayList<Object> )preparedStmtMethod(investigationSummaryVO, null, aQuery, NEDSSConstants.SELECT);
                CachedDropDownValues cache = new CachedDropDownValues();
                TreeMap<?, ?> mapPhcSts = cache.getCodedValuesAsTreeMap("PHC_IN_STS");
                mapPhcSts = cache.reverseMap(mapPhcSts); // we can add another method that do not do reverse
                TreeMap<Object, Object> mapConditionCode = cache.getConditionCodes();
                TreeMap<Object, Object> mapJurisdiction = cache.getJurisdictionCodedValuesWithAll();
                TreeMap<?, ?> mapPhcClass = cache.getCodedValuesAsTreeMap("PHC_CLASS");
                mapPhcClass = cache.reverseMap(mapPhcClass); // we can add another method that do not do reverse
                String  phcLocal = null;
                for (Iterator<Object> anIterator = ar.iterator(); anIterator.hasNext(); ) {
                	                    logger.debug(
                      "WorkupProxyEJB:getInvestgationSummaryVO anIterator.hasNext()" +
                      anIterator.hasNext());
                  investigationSummaryVO = (InvestigationSummaryVO) anIterator.next();
                  if(phcLocal!=null && phcLocal.equalsIgnoreCase(investigationSummaryVO.getLocalId())){
                	continue;
                  }
                  phcLocal=investigationSummaryVO.getLocalId();
                  if (investigationSummaryVO.getCaseClassCd() != null
                      && investigationSummaryVO.getCaseClassCd().trim().length() != 0) {
                    investigationSummaryVO.setCaseClassCodeTxt( (String) mapPhcClass.get(
                        investigationSummaryVO.getCaseClassCd()));
                  }

                  if (investigationSummaryVO.getInvestigationStatusCd() != null
                      &&
                      investigationSummaryVO.getInvestigationStatusCd().trim().length() != 0) {
                    investigationSummaryVO.setInvestigationStatusDescTxt( (String) mapPhcSts.
                        get(investigationSummaryVO.getInvestigationStatusCd()));
                  }

                  if (investigationSummaryVO.getCd() != null
                      && investigationSummaryVO.getCd().trim().length() != 0) {
                    // map = cache.reverseMap(map); // we can add another method that do not do reverse
                    investigationSummaryVO.setConditionCodeText( (String) mapConditionCode.get(
                        investigationSummaryVO.getCd()));
                  }

                  if (investigationSummaryVO.getJurisdictionCd() != null
                      &&
                      investigationSummaryVO.getJurisdictionCd().trim().length() != 0) {
                    //   map = cache.reverseMap(map); // we can add another method that do not do reverse
                    investigationSummaryVO.setJurisdictionDescTxt( (String) mapJurisdiction.get(
                        investigationSummaryVO.getJurisdictionCd()));
                  }
                }

            }
            else
            {
                throw new NEDSSSystemException("NO PERMISSIONS");
            }
        }
        catch(Exception ex)
        {
            logger.fatal("Error in TaskListProxyDAOImpl getInvestigationSummaryVOCollection  = " +  ex.getMessage(), ex);
            throw new Exception(ex.toString());
        }
        logger.debug("End execution of TaskListProxyDAOImpl.getInvestigationSummaryVOCollection  method");
        logger.info("returning ar - " + ar.toString());
        return ar;
     }

     // called by TaskListProxyEJB.getObservationsNeedingReview method
     public Collection<Object>  getObservationsNeedingReview (NBSSecurityObj nbssecurityObj) throws Exception
     {
        Connection dbConnection = null;
        ObservationSummaryVO observationSummaryVO = new ObservationSummaryVO();
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> ar = new ArrayList<Object> ();
        logger.debug("Begin execution of TaskListProxyDAOImpl.getObservationsNeedingReview method");
        // Get Security Properties for ObsNeedingReview
        String obsNeedingReviewSecurity = propertyUtil.getObservationsNeedingReviewSecurity();
        logger.debug("TaskListProxyDAOImpl.getObservationsNeedingReview - obsNeedingReviewSecurity = " + obsNeedingReviewSecurity);
      
        if (obsNeedingReviewSecurity==null) 
            throw new NEDSSSystemException("TaskListProxyDAOImpl.getTaskListItems - Security property settings missing for ObsNeedingReview");

        if ( !(obsNeedingReviewSecurity.equals(NBSOperationLookup.VIEW)) &&
        	 !(obsNeedingReviewSecurity.equals(NBSOperationLookup.EDIT)) )
            throw new NEDSSSystemException("TaskListProxyDAOImpl.getTaskListItems - Security property settings for ObsNeedingReview is incorrect");
                
        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining dbConnection in getObservationsNeedingReview() = " + nsex);
            nsex.printStackTrace();
            throw new Exception(nsex.toString());
        }
        
        try
        {
            if(nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, obsNeedingReviewSecurity))
            {
                String dataAccessWhereClause = nbssecurityObj.getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT, obsNeedingReviewSecurity, DataTables.OBSERVATION_TABLE);
                if(dataAccessWhereClause == null)
                   dataAccessWhereClause = "";
                else
                   dataAccessWhereClause = "AND " + dataAccessWhereClause;

                String aQuery = null;
                    aQuery = "select " +
                    "Observation.add_time addTime, " +
                    "Person.local_id localId, " +
                    "Person.person_uid subjectUid, " +
                    "labTest.nbs_test_desc_txt cdDescTxt, " +
                    "Observation.observation_uid observationUid, " +
                    "Observation.local_id observationLocalId, " +
                    "Observation.cd cd, " +
                    "Observation.cd_desc_txt cdDescTxt, " +
                    "Observation.record_status_cd recordStatusCd, " +
                    "Observation.activity_from_time activityFromTime, " +
                    "Observation.effective_from_time effectiveFromTime " +
                    "from Observation Observation " +
                    "inner join Participation Participation " +
                    "on Participation.act_uid = Observation.observation_uid " +
                    "inner join  Person Person  "  +
                    "on Participation.subject_entity_uid = Person.person_uid " +
                    "and upper(Person.record_status_cd) != 'LOG_DEL' " +
                    "left outer join nbs_srte..nbs_lab_test labTest " +
                    "on Observation.cd = labTest.nbs_test_code " +
                    "where Observation.record_status_cd = 'UNPROCESSED' " +
                    "and Observation.ctrl_cd_display_form = 'LabReport' " +
                    "and Participation.type_cd = 'PATSBJ' " +
                    "and Participation.subject_class_cd = 'PSN' " +
                    "and Participation.act_class_cd = 'OBS' " +
                    dataAccessWhereClause;
               
				// put this back in and use it wehn file gets updated
				// to use ORACLE  <<<<<<<<<<------------------------
				/*                if (propertyUtil.getDatabaseServerType() != null && propertyUtil.getDatabaseServerType().equalsIgnoreCase(NEDSSConstants.ORACLE_ID))
				                {
				                    logger.info("propertyUtil.getDatabaseServerType() = " + propertyUtil.getDatabaseServerType());
				                    aQuery = WumSqlQuery.SELECT_MY_INVESTIGATIONS_ORACLE +
				                            "investigation_status_cd = " + NEDSSConstants.STATUS_OPEN +
				                            " " + dataAccessWhereClause;
				                }
				                else
				                {
				                    logger.info("propertyUtil.getDatabaseServerType() = " + propertyUtil.getDatabaseServerType());
				                    aQuery = WumSqlQuery.SELECT_MY_INVESTIGATIONS_SQL +
				                            "where (investigation_status_cd = " + NEDSSConstants.STATUS_OPEN +
				                            " " + dataAccessWhereClause + ")";
				                }
				*/
                logger.debug("getObservationsNeedingReview - aQuery = " + aQuery);
                preparedStmt = dbConnection.prepareStatement(aQuery);
                resultSet = preparedStmt.executeQuery();
                resultSetMetaData = resultSet.getMetaData();
                String rows = "rows = " + resultSet.getRow();
                ar = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, observationSummaryVO.getClass(), ar);
            }
            else
            {
                throw new NEDSSSystemException("NO PERMISSIONS");
            }
        }
        catch(Exception sex)
        {
            logger.fatal("Error in TaskListProxyDAOImpl getObservationsNeedingReview = " +  sex.getMessage(), sex);
            throw new Exception(sex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        logger.debug("End execution of TaskListProxyDAOImpl.getObservationsNeedingReview method");
        logger.info("returning ar - " + ar.toString());
        return ar;
     }// end getObservationsNeedingReview

     @SuppressWarnings("unchecked")
	public List<?>[] findObservationsNeedingReview (NBSSecurityObj nbssecurityObj)
     {
        String SELECT_OBSUIDS;
        ArrayList<Object> labUidList = new ArrayList<Object> ();
        String aQuery = "";

        // Get Security Properties for ObsNeedingReview
        String obsNeedingReviewSecurity = propertyUtil.getObservationsNeedingReviewSecurity();
        logger.debug("TaskListProxyDAOImpl.findObservationsNeedingReview - obsNeedingReviewSecurity = " + obsNeedingReviewSecurity);
      
        if (obsNeedingReviewSecurity==null) 
            throw new NEDSSSystemException("TaskListProxyDAOImpl.getTaskListItems - Security property settings missing for ObsNeedingReview");

        if ( !(obsNeedingReviewSecurity.equals(NBSOperationLookup.VIEW)) &&
        	 !(obsNeedingReviewSecurity.equals(NBSOperationLookup.EDIT)) )
            throw new NEDSSSystemException("TaskListProxyDAOImpl.getTaskListItems - Security property settings for ObsNeedingReview is incorrect");
        
        try {
          String labDataAccessWhereClause = nbssecurityObj.getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,obsNeedingReviewSecurity);
          labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
          String morbDataAccessWhereClause = nbssecurityObj.getDataAccessWhereClause(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,obsNeedingReviewSecurity);
          morbDataAccessWhereClause = morbDataAccessWhereClause != null ? "AND " + morbDataAccessWhereClause : "";
          if(nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,obsNeedingReviewSecurity)
           && nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,obsNeedingReviewSecurity))
          { aQuery = "SELECT "+
                "observation_uid \"uid\", " +
                "ctrl_cd_display_form \"ctrlCdDisplayForm\"" +
                "FROM observation  " +
                "WHERE (record_status_cd = '" + NEDSSConstants.OBS_UNPROCESSED +
                "') " +
                "AND ((ctrl_cd_display_form = '" + NEDSSConstants.MOB_CTRLCD_DISPLAY +
                "' " + morbDataAccessWhereClause + ") " +
                "OR (ctrl_cd_display_form = '" + NEDSSConstants.LAB_CTRLCD_DISPLAY + "' " +
                labDataAccessWhereClause + "))";
          }
          else if((nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,obsNeedingReviewSecurity))
           && !(nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,obsNeedingReviewSecurity)))
          {
            aQuery = "SELECT "+
                  "observation_uid \"uid\", " +
                  "ctrl_cd_display_form \"ctrlCdDisplayForm\"" +
                  "FROM observation  " +
                  "WHERE (record_status_cd = '" + NEDSSConstants.OBS_UNPROCESSED +
                  "') " +
                  "AND "  +
                  " (ctrl_cd_display_form = '" + NEDSSConstants.LAB_CTRLCD_DISPLAY + "' " +
                  labDataAccessWhereClause + ")";

          }
          else if(!(nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,obsNeedingReviewSecurity))
          && nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,obsNeedingReviewSecurity))
         {
           aQuery = "SELECT "+
                        "observation_uid \"uid\", " +
                        "ctrl_cd_display_form \"ctrlCdDisplayForm\"" +
                        "FROM observation  " +
                        "WHERE (record_status_cd = '" + NEDSSConstants.OBS_UNPROCESSED +
                        "') " +
                        "AND "  +
                        " (ctrl_cd_display_form = '" + NEDSSConstants.MOB_CTRLCD_DISPLAY + "' " +
                        morbDataAccessWhereClause + ")";


         }


         CtrlCdDisplaySummaryVO ctrlCdDisplaySummaryVO = new CtrlCdDisplaySummaryVO();
         aQuery = aQuery + " order by rpt_to_state_time ";
        // System.out.println("\n\n aQuery " +aQuery );
         labUidList = (ArrayList<Object> ) preparedStmtMethod(ctrlCdDisplaySummaryVO, null,
                                                  aQuery,
                                                  NEDSSConstants.SELECT);
        List<?>[] labList = null;
        labList = this.buildObservationUidList(labUidList);
          return labList;
        }
        catch (Exception ex) {
	        logger.fatal("Error while getting observations for Review"+ex.getMessage(), ex);
	        throw new NEDSSSystemException(ex.toString());
        }
     }// end findObservationsNeedingReview

     private List<?>[] buildObservationUidList(ResultSet resultSet) throws SQLException, NEDSSSystemException
     {
       List<Object> labReportUidList = null;
       List<Object> morbReportUidList = null;
       try{
              while(resultSet.next())
              {
                long observationUid =  resultSet.getLong(1);
                String ctrlCdDisplayForm = resultSet.getString(2);

                if(ctrlCdDisplayForm != null && ctrlCdDisplayForm.equals(NEDSSConstants.LAB_REPORT))
                {
                  if(labReportUidList == null) labReportUidList = new ArrayList<Object> ();
                  labReportUidList.add(new Long(observationUid));
                }
                else if(ctrlCdDisplayForm != null && ctrlCdDisplayForm.equals(NEDSSConstants.MORBIDITY_REPORT))
                {
                  if (morbReportUidList == null) morbReportUidList = new ArrayList<Object> ();
                  morbReportUidList.add(new Long(observationUid));
                }
              }

              List<?>[] returnObsUids = {labReportUidList, morbReportUidList};

              return returnObsUids;
       }catch(SQLException ex){
    	   logger.fatal("SQLException  = "+ex.getMessage(), ex);
    	   throw new SQLException(ex.toString());
       }catch(Exception ex){
    	   logger.fatal("Exception  = "+ex.getMessage(), ex);
    	   throw new NEDSSSystemException(ex.toString());
       }
     }

  private List<?>[] buildObservationUidList(ArrayList<Object>  ctrlCdDisplaySummaryVOColl) throws SQLException, NEDSSSystemException
 {
	   List<Object> labReportUidList = null;
	   List<Object> morbReportUidList = null;
	   try{
		   if(ctrlCdDisplaySummaryVOColl!=null)
		   {
		    Iterator<Object>  itor = ctrlCdDisplaySummaryVOColl.iterator();
		     while (itor.hasNext()) {
		       CtrlCdDisplaySummaryVO ctrlCdDisplaySummaryVO = (CtrlCdDisplaySummaryVO)itor.next();
		       Long observationUid = ctrlCdDisplaySummaryVO.getUid();
		       String ctrlCdDisplayForm = ctrlCdDisplaySummaryVO.getCtrlCdDisplayForm();
		
		       if (ctrlCdDisplayForm != null &&
		           ctrlCdDisplayForm.equals(NEDSSConstants.LAB_REPORT)) {
		         if (labReportUidList == null)
		           labReportUidList = new ArrayList<Object> ();
		         labReportUidList.add(observationUid);
		       }
		       else if (ctrlCdDisplayForm != null &&
		                ctrlCdDisplayForm.equals(NEDSSConstants.MORBIDITY_REPORT)) {
		         if (morbReportUidList == null)
		           morbReportUidList = new ArrayList<Object> ();
		         morbReportUidList.add(observationUid);
		       }
		     }
		   }
	
	      List<?>[] returnObsUids = {labReportUidList, morbReportUidList};
	
          return returnObsUids;
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new NEDSSSystemException(ex.toString());
	   }
 }


     @SuppressWarnings("unchecked")
	public List<?>[] findObservationsNeedingSecurityAssignment (NBSSecurityObj securityObj)
     {
        ArrayList<Object> labUidList = new ArrayList<Object> ();
        String aQuery = "";
        try
        {
	         if(securityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.ASSIGNSECURITY)
	         && securityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.ASSIGNSECURITY))
	         {aQuery = "SELECT "+
	               "observation_uid \"uid\", " +
	               "ctrl_cd_display_form \"ctrlCdDisplayForm\"" +
	               "FROM observation  " +
	               "WHERE (prog_area_cd IS NULL " +
	               "OR jurisdiction_cd IS NULL) " +
	               "AND (ctrl_cd_display_form = '" + NEDSSConstants.MOB_CTRLCD_DISPLAY +
	               "' " +
	               "OR ( ctrl_cd_display_form = '" + NEDSSConstants.LAB_REPORT + "' and "+
						     " obs_domain_cd_st_1 = 'Order' ))";
	         }
	         if(securityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.ASSIGNSECURITY)
	         && !securityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.ASSIGNSECURITY))
	        {aQuery = "SELECT "+
	          "observation_uid \"uid\", " +
	          "ctrl_cd_display_form \"ctrlCdDisplayForm\"" +
	          "FROM observation  " +
	          "WHERE (prog_area_cd IS NULL " +
	          "OR jurisdiction_cd IS NULL) " +
	          "AND (( ctrl_cd_display_form = '" + NEDSSConstants.LAB_REPORT + "'"+
						 " and obs_domain_cd_st_1 = 'Order'))";
	       }
	       if (!securityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
	                                     NBSOperationLookup.ASSIGNSECURITY)
	           &&
	           securityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
	                                      NBSOperationLookup.ASSIGNSECURITY)) {
	         aQuery = "SELECT " +
             "observation_uid \"uid\", " +
             "ctrl_cd_display_form \"ctrlCdDisplayForm\"" +
             "FROM observation  " +
             "WHERE (prog_area_cd IS NULL " +
             "OR jurisdiction_cd IS NULL) " +
             "AND (ctrl_cd_display_form = '" + NEDSSConstants.MOB_CTRLCD_DISPLAY+ "')";
       		}



           aQuery = aQuery + " order by rpt_to_state_time ";
           logger.debug("aQuery:  " + aQuery);

           CtrlCdDisplaySummaryVO ctrlCdDisplaySummaryVO = new CtrlCdDisplaySummaryVO();
           labUidList = (ArrayList<Object> ) preparedStmtMethod(ctrlCdDisplaySummaryVO, null,
                                                     aQuery,
                                                     NEDSSConstants.SELECT);
           List<?>[] labList = null;
           labList = this.buildObservationUidList(labUidList);
           return labList;

        }
        catch(SQLException sex)
        {
            logger.fatal("Error in TaskListProxyDAOImpl findObservationsNeedingReview = " +  sex.getMessage(), sex);
            throw new NEDSSSystemException(sex.toString());
        }
     }// end findObservationsNeedingSecurityAssignment



     // called by TaskListProxyEJB.getObservationsNeedingSecurityAssignment method
     public Collection<Object>  getObservationsNeedingSecurityAssignment (NBSSecurityObj nbssecurityObj) throws Exception
     {
        logger.debug("getObservationsNeedingSecurityAssignment");
        Connection dbConnection = null;
        ObservationSummaryVO observationSummaryVO = new ObservationSummaryVO();
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> ar = new ArrayList<Object> ();
        logger.debug("Begin execution of TaskListProxyDAOImpl.getObservationsNeedingSecurityAssignment method");
        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining dbConnection in getObservationsNeedingSecurityAssignment() = " + nsex.getMessage(), nsex);
            throw new Exception(nsex.toString());
        }

        try
        {
            if(nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITY))
            {

                String dataAccessWhereClause = nbssecurityObj.getDataAccessWhereClause(
                            NBSBOLookup.OBSERVATIONLABREPORT,
                            NBSOperationLookup.VIEW,
                            DataTables.OBSERVATION_TABLE);
                if(dataAccessWhereClause == null)
                   dataAccessWhereClause = "";
                else
                   dataAccessWhereClause = "AND " + dataAccessWhereClause;

                logger.debug("dataAccessWhereClause = " + dataAccessWhereClause);

                  String aQuery = null;
                      aQuery = "select " +
                      "Observation.add_time addTime, " +
                      "Observation.obs_domain_cd obsDomainCd, labTest.nbs_test_desc_txt cdDescTxt, " +
                      "Observation.observation_uid observationUid, " +
                      "Observation.local_id observationLocalId, " +
                      "Observation.cd cd, " +
                      "Observation.cd_desc_txt cdDescTxt, " +
                      "Observation.status_cd statusCd, " +
                      "Observation.record_status_cd recordStatusCd, " +
                      "Observation.activity_from_time activityFromTime, " +
                      "Observation.effective_from_time effectiveFromTime " +
                      "from Observation Observation " +
                      "left outer join nbs_srte..nbs_lab_test labTest " +
                      "on Observation.cd = labTest.nbs_test_code " +
                      "where Observation.electronic_ind = 'Y' " +
                      "and Observation.ctrl_cd_display_form = 'LabReport'" +
                      "and (Observation.prog_area_cd is null  " +
                      "or  Observation.jurisdiction_cd is null) ";
                                logger.info("getObservationsNeedingSecurityAssignment - aQuery = " + aQuery);
                preparedStmt = dbConnection.prepareStatement(aQuery);
                resultSet = preparedStmt.executeQuery();
                resultSetMetaData = resultSet.getMetaData();
                String rows = "rows = " + resultSet.getRow();
                ar = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, observationSummaryVO.getClass(), ar);



            }
            else
            {
                throw new NEDSSSystemException("NO PERMISSIONS");
            }
        }
        catch(Exception sex)
        {
            logger.fatal("Error in TaskListProxyDAOImpl getObservationsNeedingSecurityAssignment = " +  sex.getMessage(), sex);
            throw new Exception(sex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        logger.debug("End execution of TaskListProxyDAOImpl.getObservationsNeedingSecurityAssignment method");
        return ar;
     }// end getObservationsNeedingSecurityAssignment

    // getTaskListItems -------(The queries in this method are modified for better performance civil00017522)-------------------------
    public Collection<Object>  getTaskListItems (NBSSecurityObj nbssecurityObj) throws javax.ejb.EJBException, Exception
    {
    	//HashMap<String,ArrayList<CustomQueueVO>> arCustom = (HashMap<String,ArrayList<CustomQueueVO>>)getCustomQueues(nbssecurityObj);
        
    	ArrayList<TaskListItemVO> arCustom = getCustomQueues(nbssecurityObj);
        TaskListProxyDAOImpl taskListProxyDAOImpl = new TaskListProxyDAOImpl();
        ArrayList<Object> ar = new ArrayList<Object> ();
        
        // Get Security Properties for ObsNeedingReview
        String obsNeedingReviewSecurity = propertyUtil.getObservationsNeedingReviewSecurity();
      
        if (obsNeedingReviewSecurity==null) 
            throw new NEDSSSystemException("TaskListProxyDAOImpl.getTaskListItems - Security property settings missing for ObsNeedingReview");
        
        if ( (!(obsNeedingReviewSecurity.equals(NBSOperationLookup.VIEW))) &&
        	 (!(obsNeedingReviewSecurity.equals(NBSOperationLookup.EDIT))) )
            throw new NEDSSSystemException("TaskListProxyDAOImpl.getTaskListItems - Security property settings for ObsNeedingReview is incorrect");
                
        try
        {
        	
           // OPEN INVESTIGATIONS
			String myPAInvestigationsSecurity = propertyUtil
					.getMyProgramAreaSecurity();
			logger.debug("TaskListProxyDAOImpl.getInvestigationSummaryVOCollection(NBSSecurityObj) - myPAInvestigationsSecurity = "
					+ myPAInvestigationsSecurity);

			ar.addAll(arCustom);
			
			if (myPAInvestigationsSecurity != null
					&& nbssecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
							myPAInvestigationsSecurity)) {
				
				logger.debug("Open Investigation - getPermission is true --- ");
				String dataAccessWhereClause = nbssecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
                if(dataAccessWhereClause == null)
                   dataAccessWhereClause = "";
                else
                   dataAccessWhereClause = "AND " + dataAccessWhereClause;
               
                logger.debug("dataAccessWhereClause = " + dataAccessWhereClause);
                String aQuery = WumSqlQuery.SELECT_COUNT_OF_OPEN_INVESTIGATION + dataAccessWhereClause;
                TaskListItemVO taskListItemVO = new TaskListItemVO();
				try {
					Integer count = taskListProxyDAOImpl.getCountbySP(aQuery);
					logger.info("count = " + count);

					taskListItemVO
							.setTaskListItemName(NEDSSConstants.OPEN_INVESTIGATIONS);
					if (count == null)
						taskListItemVO.setTaskListItemCount(new Integer(0));
					else
						taskListItemVO.setTaskListItemCount(count);
				} catch (NEDSSSystemException e) {
					logger.fatal(
							"TaskListProxyEJB.getTaskListItems: errro while getting open investigations count: "
									+ e.getMessage(), e);
					// throw new NEDSSSystemException(e.getMessage(),e);
				}
				ar.add(taskListItemVO);
			}
          //  }
            
			
            
            // NND_NOTIFICATIONS_FOR_APPROVAL
        	logger.debug("Begin execution of TaskListProxyDAOImpl.getTaskListItems NND_NOTIFICATIONS_FOR_APPROVAL");
            if(nbssecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW))
            {
                logger.debug("NND_NOTIFICATIONS_FOR_APPROVAL - getPermission is true --- ");
                String dataAccessWhereClause = nbssecurityObj.getDataAccessWhereClause(NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW, DataTables.NOTIFICATION_TABLE);
                if(dataAccessWhereClause == null)
                   dataAccessWhereClause = "";
                else
                   dataAccessWhereClause = "AND " + dataAccessWhereClause;

                logger.debug("dataAccessWhereClause = " + dataAccessWhereClause);

                String aQuery =  WumSqlQuery.SELECT_COUNT_OF_NOTIFICATIONS_FOR_APPROVAL_SQL_REVISITED + dataAccessWhereClause;
                logger.debug("NND_NOTIFICATIONS_FOR_APPROVAL Query = " + aQuery);
                TaskListItemVO taskListItemVO = new TaskListItemVO();
				try {
					Integer count = taskListProxyDAOImpl.getCountbySP(aQuery);
					logger.info("count = " + count);

					taskListItemVO
							.setTaskListItemName(NEDSSConstants.NND_NOTIFICATIONS_FOR_APPROVAL);
					if (count == null)
						taskListItemVO.setTaskListItemCount(new Integer(0));
					else
						taskListItemVO.setTaskListItemCount(count);
				} catch (NEDSSSystemException e) {
					logger.fatal(
							"TaskListProxyEJB.getTaskListItems: errro while getting NND_NOTIFICATIONS_FOR_APPROVAL count: "
									+ e.getMessage(), e);
				}

                ar.add(taskListItemVO);
                logger.info("ar = " + ar.toString());
            }// end of ---- NND_NOTIFICATIONS_FOR_APPROVAL
            logger.debug("End execution of TaskListProxyDAOImpl.getTaskListItems NND_NOTIFICATIONS_FOR_APPROVAL");

            logger.debug("Begin execution of TaskListProxyDAOImpl.getTaskListItems NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL");
			// NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL
			if(nbssecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW))
			{
				logger.debug("NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL - getPermission is true --- ");
				String dataAccessWhereClause = nbssecurityObj.getDataAccessWhereClause(NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW, DataTables.NOTIFICATION_TABLE);
				if(dataAccessWhereClause == null)
				   dataAccessWhereClause = "";
				else
				   dataAccessWhereClause = "AND " + dataAccessWhereClause;

				logger.debug("dataAccessWhereClause = " + dataAccessWhereClause);

				String aQuery =  WumSqlQuery.SELECT_COUNT_OF_UPDATED_NOTIFICATIONS_FOR_APPROVAL_SQL_REVISITED+ dataAccessWhereClause;
				
				 logger.debug("NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL Query = " + aQuery);
				 TaskListItemVO taskListItemVO = new TaskListItemVO();
				try {
					Integer count = taskListProxyDAOImpl.getCountbySP(aQuery);
					logger.info("count = " + count);

					taskListItemVO
							.setTaskListItemName(NEDSSConstants.NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL);
					if (count == null)
						taskListItemVO.setTaskListItemCount(new Integer(0));
					else
						taskListItemVO.setTaskListItemCount(count);
				} catch (NEDSSSystemException e) {
					logger.fatal(
							"TaskListProxyEJB.getTaskListItems: errro while getting UPDATED_NOTIFICATIONS count: "
									+ e.getMessage(), e);
				}

				ar.add(taskListItemVO);
				logger.info("ar = " + ar.toString());
			}// end of ---- NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL
			logger.debug("End execution of TaskListProxyDAOImpl.getTaskListItems NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL");
			// Rejected Notifications
			
			logger.debug("Begin execution of TaskListProxyDAOImpl.getTaskListItems REJECTED_NOTIFICATIONS");
			if((nbssecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE)
					|| (nbssecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL))))
			{
				logger.debug("REJECTED NOTIFICATIONS - getPermission is true --- ");
				boolean check1 = nbssecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE,
						ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
				boolean check2 = nbssecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL,
						ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
				String dataAccessWhereClause = "";
				String dataAccessWhereClause1 = null;
				String dataAccessWhereClause2 = null;
				if(check1)
					dataAccessWhereClause1 = nbssecurityObj.getDataAccessWhereClause(NBSBOLookup.NOTIFICATION,
						NBSOperationLookup.CREATE, "Notification");
				if(check2)
					dataAccessWhereClause2 = nbssecurityObj.getDataAccessWhereClause(NBSBOLookup.NOTIFICATION,
						NBSOperationLookup.CREATENEEDSAPPROVAL, "Notification");
				//Need to get the where clause for both the permissions as user might have Create and Create Needs Approval in different PA Jurisdictions
				if(dataAccessWhereClause1 == null && dataAccessWhereClause2 == null)
				   dataAccessWhereClause = "";
				else if(dataAccessWhereClause1 == null && dataAccessWhereClause2 != null && dataAccessWhereClause2.trim().length()>0)
					dataAccessWhereClause = " AND " + dataAccessWhereClause2;
				else if(dataAccessWhereClause1 != null && dataAccessWhereClause1.trim().length()>0 && dataAccessWhereClause2 == null)
					dataAccessWhereClause = " AND " + dataAccessWhereClause1;
				else if(dataAccessWhereClause1 != null && dataAccessWhereClause1.trim().length()>0 && dataAccessWhereClause2 != null && dataAccessWhereClause2.trim().length()>0)
					dataAccessWhereClause = " AND (" + dataAccessWhereClause1 +" or "+ dataAccessWhereClause2+")";

				logger.debug("dataAccessWhereClause = " + dataAccessWhereClause);

				String aQuery =  WumSqlQuery.SELECT_COUNT_OF_REJECTED_NOTIFICATIONS_FOR_APPROVAL_SQL_REVISITED
					+ PropertyUtil.getInstance().getRejectedNotificationDaysLimit()+ dataAccessWhereClause;

				 logger.debug("REJECTED_NOTIFICATIONS Query = " + aQuery);
				TaskListItemVO taskListItemVO = new TaskListItemVO();
				try {
					Integer count = taskListProxyDAOImpl.getCountbySP(aQuery);
					logger.info("count = " + count);
					taskListItemVO
							.setTaskListItemName(NEDSSConstants.NND_REJECTED_NOTIFICATIONS_FOR_APPROVAL);
					if (count == null)
						taskListItemVO.setTaskListItemCount(new Integer(0));
					else
						taskListItemVO.setTaskListItemCount(count);
				} catch (NEDSSSystemException e) {
					logger.fatal(
							"TaskListProxyEJB.getTaskListItems: errro while getting REJECTED_NOTIFICATIONS count: "
									+ e.getMessage(), e);
				}

				ar.add(taskListItemVO);
				logger.info("ar = " + ar.toString());
			}// end of ---- REJECTED_NOTIFICATIONS_FOR_APPROVAL
			logger.debug("End execution of TaskListProxyDAOImpl.getTaskListItems REJECTED_NOTIFICATIONS");

            // Observations Needing Program or Jurisdiction Assignment
			logger.debug("Begin execution of TaskListProxyDAOImpl.getTaskListItems OBS_NEEDING_PROG_JUR_ASSIGNMENT");
            if(nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITY,ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,ProgramAreaJurisdictionUtil.ANY_JURISDICTION)||
              nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ASSIGNSECURITY,ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,ProgramAreaJurisdictionUtil.ANY_JURISDICTION ))
            {
            boolean flag1 = false;
            boolean flag2 = false;
            boolean flag3 = false;
            String aQuery="";
            String aQueryDoc="";
            flag1 = nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITY,ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
            flag2 = nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ASSIGNSECURITY,ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
            flag3 = nbssecurityObj.getPermission(NBSBOLookup.DOCUMENT, NBSOperationLookup.ASSIGNSECURITY,ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
            if(flag1 && flag2)
            {
            	        	aQuery = "select " +
	                  "count(*) " +
	                  "from " + DataTables.OBSERVATION_TABLE + " with (nolock) " +
	                  "where " +
	                  "(" + DataTables.OBSERVATION_TABLE + ".ctrl_cd_display_form in ('" +
	                  NEDSSConstants.LAB_REPORT + "','"+NEDSSConstants.MOB_CTRLCD_DISPLAY + "')  and "+DataTables.OBSERVATION_TABLE+".obs_domain_cd_st_1 = 'Order')" +
	                  "and (  " + DataTables.OBSERVATION_TABLE +
	                  ".prog_area_cd is null " +
	                  "or  " + DataTables.OBSERVATION_TABLE +
	                  ".jurisdiction_cd is null) ";


            }
            else if(flag1 && !flag2)
            	
					  aQuery = "select " +
					  "count(*) " +
					  "from " + DataTables.OBSERVATION_TABLE + " with (nolock) " +
					  "where " +
					  "("+ DataTables.OBSERVATION_TABLE + ".ctrl_cd_display_form = '" +
					   NEDSSConstants.LAB_REPORT + "' and "+DataTables.OBSERVATION_TABLE+".obs_domain_cd_st_1 = 'Order')" + 
					  "and  ( " + DataTables.OBSERVATION_TABLE +
					  ".prog_area_cd is null " +
					  "or  " + DataTables.OBSERVATION_TABLE +
					  ".jurisdiction_cd is null) ";
            	
           else if(!flag1 && flag2)
        	   		aQuery = "select " +
     				  "count(*) " +
     				  "from " + DataTables.OBSERVATION_TABLE + " with (nolock) " +
     				  "where " +
     				  "("+ DataTables.OBSERVATION_TABLE + ".ctrl_cd_display_form = '" +
     				  	NEDSSConstants.MOB_CTRLCD_DISPLAY + "' and "+DataTables.OBSERVATION_TABLE+".obs_domain_cd_st_1 = 'Order')" + 
     				  "and  ( " + DataTables.OBSERVATION_TABLE +
     				  ".prog_area_cd is null " +
     				  "or  " + DataTables.OBSERVATION_TABLE +
     				  ".jurisdiction_cd is null) ";


            // For PHCR Documents needing security Assignment
           	if(flag3)
           {
               	
               	/*	aQueryDoc = 	
               				"WITH added_row_number AS("+
               				"select *,ROW_NUMBER() OVER(PARTITION BY nbs_document.sending_app_event_id,nbs_document.sending_facility_nm ORDER BY nbs_document.add_time desc) AS row_number from nbs_document with (nolock) where " + 
              		"( nbs_document.doc_type_cd ='"+ NEDSSConstants.PHC_236 +"'"+
              		" ))SELECT count(*) FROM added_row_number WHERE row_number = 1 AND record_status_cd = 'UNPROCESSED'"+
               	 " and (prog_area_cd is null or " +
           		"  jurisdiction_cd is null)";*/
           		
           		aQueryDoc = 	"select count(*) from nbs_document with (nolock) where " + 
                  		"(  nbs_document.prog_area_cd is null or " +
                  		"  nbs_document.jurisdiction_cd is null) and nbs_document.doc_type_cd ='"+ NEDSSConstants.PHC_236 +"'"+
                  		" and nbs_document.record_status_cd='UNPROCESSED'";
           		

           }

            /*else if(!flag1 && !flag2 && !flag3)
            {
               throw new SecurityException("Expected the user to have view lab, morb report and View Document permissions.");
            }*/
            logger.debug("OBS_NEEDING_PROG_JUR_ASSIGNMENT Query = " + aQuery);
            Integer count = new Integer(0);
            Integer countObs = new Integer(0);
            Integer countDoc = new Integer(0);
            TaskListItemVO taskListItemVO = new TaskListItemVO();
            try{
					if (!flag3 && aQuery != null && !aQuery.isEmpty())
						count = taskListProxyDAOImpl.getCountbySP(aQuery);
					else {
						if (aQueryDoc != null && !aQueryDoc.isEmpty())
							countDoc = taskListProxyDAOImpl.getCountbySP(aQueryDoc);
						if (aQuery != null && !aQuery.isEmpty())
							countObs = taskListProxyDAOImpl.getCountbySP(aQuery);

						count = new Integer(countObs.intValue()
								+ countDoc.intValue());
					}
              taskListItemVO.setTaskListItemName(NEDSSConstants.
                  ELRS_NEEDING_PROGRAM_OR_JURISDICTION_ASSIGNMENT);
              if (count == null)
                taskListItemVO.setTaskListItemCount(new Integer(0));
              else
                taskListItemVO.setTaskListItemCount(count);
            } catch (NEDSSSystemException e) {
				logger.fatal(
						"TaskListProxyEJB.getTaskListItems: errro while getting DOC_NEEDING_PROG_JUR_ASSIGNMENT count: "
								+ e.getMessage(), e);
			}
              ar.add(taskListItemVO);
          } //end of Observation Needing Assignment
            logger.debug("End execution of TaskListProxyDAOImpl.getTaskListItems OBS_NEEDING_PROG_JUR_ASSIGNMENT");
            // Observation Needing Review starts 
            logger.debug("Begin execution of TaskListProxyDAOImpl.getTaskListItems OBS_NEEDING_REVIEW");
            if(nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, obsNeedingReviewSecurity)
                || (nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, obsNeedingReviewSecurity)
                )||(nbssecurityObj.getPermission(NBSBOLookup.DOCUMENT,"VIEW")))
            {
             	 
              boolean flag1;
              boolean flag2;
              boolean flag3;
               flag1 = nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,obsNeedingReviewSecurity);
               flag2 = nbssecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,obsNeedingReviewSecurity);
               flag3= nbssecurityObj.getPermission(NBSBOLookup.DOCUMENT,"VIEW"); 
              String aQuery = "";
             
              String dataAccessWhereClauseLab = nbssecurityObj.
                  getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		  					obsNeedingReviewSecurity,
                		  					DataTables.OBSERVATION_TABLE);
              if (dataAccessWhereClauseLab == null)
                dataAccessWhereClauseLab = "";
              else
                dataAccessWhereClauseLab = " AND " + dataAccessWhereClauseLab;
              String dataAccessWhereClauseMorb = nbssecurityObj.
                  getDataAccessWhereClause(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
                		  					obsNeedingReviewSecurity,
                		  					DataTables.OBSERVATION_TABLE);
              if (dataAccessWhereClauseMorb == null)
                dataAccessWhereClauseMorb = "";
              else
                dataAccessWhereClauseMorb = " AND " + dataAccessWhereClauseMorb;
              String dataAccessWhereClauseDoc = nbssecurityObj.
              getDataAccessWhereClause(NBSBOLookup.DOCUMENT,
            		  					"VIEW",
            		  					DataTables.NBS_DOCUMENT_TABLE);
            		  					
              if (dataAccessWhereClauseDoc == null)
            	  dataAccessWhereClauseDoc = "";
              else
            	  dataAccessWhereClauseDoc = " AND " + dataAccessWhereClauseDoc;
           
              String aQueryDoc="";
              
              if (flag1 && !flag2) {
		                aQuery = "select " +
	                    "count(*) " +
	                    "from " + DataTables.OBSERVATION_TABLE + " observation with (nolock) " +
	                    "where " +
	                    "(observation.record_status_cd = '" +
	                    NEDSSConstants.OBSERVATION_RECORD_STATUS_CD + "') " +
	                    "and observation.ctrl_cd_display_form = '" +
	                    NEDSSConstants.LAB_CTRLCD_DISPLAY + "' " +
	                    dataAccessWhereClauseLab;
                
              }
              else if (!flag1 && flag2) {
                aQuery = "select " +
                    "count(*) " +
                    "from " + DataTables.OBSERVATION_TABLE + " observation with (nolock) " +
                    "where " +
                    "(observation.record_status_cd = '" +
                    NEDSSConstants.OBSERVATION_RECORD_STATUS_CD + "') " +
                    "and observation.ctrl_cd_display_form = '" +
                    NEDSSConstants.MOB_CTRLCD_DISPLAY + "' " +
                    dataAccessWhereClauseMorb;
              }
              else if (flag1 && flag2) {
            	  aQuery = "select " +
	                    "count(*) " +
	                    "from " + DataTables.OBSERVATION_TABLE + " observation with (nolock) " +
	                    "where " +
	                    "(observation.record_status_cd = '" +
	                    NEDSSConstants.OBSERVATION_RECORD_STATUS_CD + "') " +
	                    "and ((observation.ctrl_cd_display_form = '" +
	                    NEDSSConstants.LAB_CTRLCD_DISPLAY + "'" +
	                    dataAccessWhereClauseLab + ")" +
	                    " or " +
	                    "(observation.ctrl_cd_display_form = '" +
	                    NEDSSConstants.MOB_CTRLCD_DISPLAY + "'" +
	                    dataAccessWhereClauseMorb + "))";
     
                    

              }
              // Include Document Query
              if(flag3) {
            	  	  dataAccessWhereClauseDoc = dataAccessWhereClauseDoc.replaceAll("NBS_document.program_jurisdiction_oid","program_jurisdiction_oid");
                /*	  aQueryDoc =
                			  "WITH added_row_number AS( "+
                			  "select *,ROW_NUMBER() OVER(PARTITION BY nbs_document.sending_app_event_id,nbs_document.sending_facility_nm ORDER BY nbs_document.add_time desc) AS row_number from nbs_document with (nolock)  WHERE "+  
	            	  "  nbs_document.doc_type_cd ='"+ NEDSSConstants.PHC_236 +"' "
	            	  +" )SELECT count(*) FROM added_row_number WHERE row_number = 1 AND record_status_cd = 'UNPROCESSED'" + dataAccessWhereClauseDoc;
*/
            	  	  
            	  	 aQueryDoc ="select count(*) from nbs_document with (nolock)  WHERE "+  
       	            	  " (nbs_document.record_status_cd = 'UNPROCESSED'" +") "+ 
       	            	  " and nbs_document.doc_type_cd ='"+ NEDSSConstants.PHC_236 +"' "+ dataAccessWhereClauseDoc;
            	  	 
              }
           
/*              else if (!flag1 && !flag2 && !flag3)
              {
                throw new SecurityException("Expected the user to have view lab Report, morb report and Document permissions.");
              }*/
              Integer countDoc= new Integer(0);
              Integer countObs=new Integer(0);
              Integer count=new Integer(0);
              TaskListItemVO taskListItemVO = new TaskListItemVO();
				try {
						logger.debug("OBS_NEEDING_REVIEW Query = " + aQuery);
						if (!flag3 && aQuery != null && !aQuery.isEmpty())
							count = taskListProxyDAOImpl.getCountbySP(aQuery);
						else {
							if (aQueryDoc != null && !aQueryDoc.isEmpty())
								countDoc = taskListProxyDAOImpl
										.getCountbySP(aQueryDoc);
							if (aQuery != null && !aQuery.isEmpty())
								countObs = taskListProxyDAOImpl
										.getCountbySP(aQuery);

							count = new Integer(countObs.intValue()
									+ countDoc.intValue());
						}
						logger.info("count = " + count);
						taskListItemVO
								.setTaskListItemName(NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW);
						if (count == null)
							taskListItemVO.setTaskListItemCount(new Integer(0));
						else
							taskListItemVO.setTaskListItemCount(count);
				} catch (NEDSSSystemException e) {
					logger.fatal(
							"TaskListProxyEJB.getTaskListItems: errro while getting DOC_NEEDING_REVIEW count: "
									+ e.getMessage(), e);
				}

                ar.add(taskListItemVO);
                logger.info("ar = " + ar.toString());
            }// end of --- New Lab Reports For Review
            logger.debug("End execution of TaskListProxyDAOImpl.getTaskListItems OBS_NEEDING_REVIEW");

            if (nbssecurityObj.getPermission(NBSBOLookup.QUEUES, NBSOperationLookup.MESSAGE))
            { 
                // Message_Log Queue.
                MessageLogDAOImpl messageLogDao = new MessageLogDAOImpl();
                MessageLogDT messageLogDt = new MessageLogDT();
                Long id = nbssecurityObj.getTheUserProfile().getTheUser().getProviderUid(); 
                messageLogDt.setAssignedToUid(id);
                TaskListItemVO taskListItemVO = new TaskListItemVO();
                taskListItemVO.setTaskListItemName(NEDSSConstants.MESSAGE_QUEUE);
                taskListItemVO.setTaskListItemCount(messageLogDao.getCount(messageLogDt, nbssecurityObj));
                ar.add(taskListItemVO);
            }
            
            if (nbssecurityObj.getPermission(NBSBOLookup.QUEUES, NBSOperationLookup.SUPERVISORREVIEW))
            {
                // Supervisor Review Queue 
                TaskListItemVO supervisor = new TaskListItemVO();
                supervisor.setTaskListItemName(NEDSSConstants.SUPERVISOR_REVIEW_QUEUE);
                PublicHealthCaseDAOImpl dao = new PublicHealthCaseDAOImpl();
                supervisor.setTaskListItemCount(dao.getPublicHealthCasesBySupervisorCount(nbssecurityObj) );
                ar.add(supervisor); 
            }
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error in getTaskListItems "  +  nsex.getMessage(), nsex);
            throw new Exception(nsex.toString());
        }
        logger.info("Returning ar = " + ar.size() );
        logger.debug("End execution of TaskListProxyDAOImpl.getTaskListItems method");
        return ar;
    } // end getTaskListItems ---------------------------------
}
