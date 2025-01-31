// -- Java Code Generation Process --

package gov.cdc.nedss.proxy.ejb.tasklistproxyejb.bean;

// Import Statements
import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.queue.dao.MessageLogDAOImpl;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao.TaskListProxyDAOImpl;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.vo.TaskListItemVO;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.RulesDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class TaskListProxyEJB implements javax.ejb.SessionBean
{
    /**
     * For logging
     */
    private static final LogUtils logger = new LogUtils(TaskListProxyEJB.class.getName());
 
    /**
     * For access to NEDSS.properties
     */
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

    /**
     * @roseuid 3C1BA5470210
     * @J2EE_METHOD  --  TaskListProxyEJB
     */
    public TaskListProxyEJB    ()
    {

    }



    /**
     * saveCustomQueue: inserts the custom queue in the DB table
     * @param queueName
     * @param sourceTable
     * @param searchCriteriaString
     * @param description
     * @param listOfUsers
     * @param nbsSecurityObj
     * @return
     * @throws javax.ejb.EJBException
     * @throws Exception
     */
    public String saveCustomQueue (String queueName, String sourceTable, String searchCriteriaString, String description, String listOfUsers, String searchCriteriaDesc,String searchCriteriaCd, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, Exception
    {

        TaskListProxyDAOImpl taskListProxyDAOImpl = new TaskListProxyDAOImpl();
    	String messageInserted = "";
    	String userIdString = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
    	Long userId = Long.parseLong(userIdString);
    	
    	if(listOfUsers!=null && listOfUsers.equalsIgnoreCase("current"))
    		listOfUsers = userId+"";
           try {
        	   
     
        	 boolean existing = taskListProxyDAOImpl.isExistingCustomQueueName(queueName, userId+"");  
        	 if(!existing){//if the custom queue name doesn't exist yet in the custom_queue table, then insert it
        		 messageInserted = taskListProxyDAOImpl.insertCustomQueue(queueName,sourceTable,searchCriteriaString,description,listOfUsers,userId,searchCriteriaDesc,searchCriteriaCd,nbsSecurityObj);
      
        	 }
        	else
        		messageInserted="1";
        	 
           }
           catch(NEDSSSystemException nsex)
           {
           	 logger.fatal("TaskListProxyEJB.saveCustomQueue: " + nsex.getMessage(), nsex);
           	 throw new Exception(nsex.getMessage(),nsex);
           }
        return messageInserted;   
           
    }
    
    
    /**
     * deleteCustomQueue: deletes custom queue from DB if current user created it. It won't be accessible by anyone else anymore.
     * @param queueName
     * @param sourceTable
     * @param searchCriteriaString
     * @param description
     * @param listOfUsers
     * @param nbsSecurityObj
     * @return
     * @throws javax.ejb.EJBException
     * @throws Exception
     */
    public String deleteCustomQueue (String queueName, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, Exception
    {

        TaskListProxyDAOImpl taskListProxyDAOImpl = new TaskListProxyDAOImpl();
    	String messageInserted = "";
    	String userIdString = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
  
    
           try {
        	   
        	 boolean privateOwner = taskListProxyDAOImpl.isCurrentUserOwnerOfCustomPrivateQueue(queueName, userIdString);
        	 
             boolean permissionPublicQueue=nbsSecurityObj.getPermission(NBSBOLookup.PUBLICQUEUES, NBSOperationLookup.ADDINACTIVATE);
             //either the user has permissions to delete public queues or the user is the owner of the private queue
             
        	 if(privateOwner || permissionPublicQueue){//if the custom queue was created by current user
        		 messageInserted = taskListProxyDAOImpl.deleteCustomQueue(queueName,userIdString,nbsSecurityObj);
      
        	 }
        	else	
        		messageInserted="1";//The current user didn't create the queue or it is a public queue and the user doesn't have permissions to delete it	 	 
        	 
           }
           catch(NEDSSSystemException nsex)
           {
           	 logger.fatal("TaskListProxyEJB.deleteCustomQueue: " + nsex.getMessage(), nsex);
           	 throw new Exception(nsex.getMessage(),nsex);
           }
        return messageInserted;   
           
    }
    
    
    
    /**
     * @roseuid 3C1BA547022E
     * @J2EE_METHOD  --  ejbRemove
     * A container invokes this method before it ends the life of the session object. This
     * happens as a result of a client's invoking a remove operation, or when a container
     * decides to terminate the session object after a timeout. This method is called with
     * no transaction context.
     */
    public void ejbRemove    ()
    {

    }

    /**
     * @roseuid 3C1BA5470238
     * @J2EE_METHOD  --  ejbActivate
     * The activate method is called when the instance is activated from its 'passive' state.
     * The instance should acquire any resource that it has released earlier in the ejbPassivate()
     * method. This method is called with no transaction context.
     */
    public void ejbActivate    ()
    {

    }

    /**
     * @roseuid 3C1BA547024C
     * @J2EE_METHOD  --  ejbPassivate
     * The passivate method is called before the instance enters the 'passive' state. The
     * instance should release any resources that it can re-acquire later in the ejbActivate()
     * method. After the passivate method completes, the instance must be in a state that
     * allows the container to use the Java Serialization protocol to externalize and store
     * away the instance's state. This method is called with no transaction context.
     */
    public void ejbPassivate    ()
    {

    }

    /**
     * @roseuid 3C1BA71500E8
     * @J2EE_METHOD  --  setSessionContext
     * Set the associated session context. The container calls this method after the instance
     * creation. The enterprise Bean instance should store the reference to the context
     * object in an instance variable. This method is called with no transaction context.
     */
    public void setSessionContext    (SessionContext sessioncontext) throws EJBException,RemoteException
    {

    }

    /**
     * @roseuid 3C51803001AE
     * @J2EE_METHOD  --  ejbCreate
     * Called by the container to create a session bean instance. Its parameters typically
     * contain the information the client uses to customize the bean instance for its use.
     * It requires a matching pair in the bean class and its home interface.
     */
    public void ejbCreate    ()
    {

    }

    /**
     * @roseuid 3C518857014B
     * @J2EE_METHOD  --  getTaskListItems
     */
    public Collection<Object>  getTaskListItems (NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, Exception
    {
    	 logger.info("toDEL -----------------");
//    	interviewAnswerTester(nbsSecurityObj);
    	
        logger.info("getTaskListItems-----------------");
        logger.debug("Begin execution of TaskListProxyEJB.getTaskListItems method");
        TaskListProxyDAOImpl taskListProxyDAOImpl = new TaskListProxyDAOImpl();
        ArrayList<Object> ar = new ArrayList<Object> ();
        try
        {
            ar = (ArrayList<Object> )taskListProxyDAOImpl.getTaskListItems(nbsSecurityObj);
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("TaskListProxyEJB.getTaskListItems: " + nsex.getMessage(), nsex);
            throw new NEDSSSystemException(nsex.getMessage(),nsex);
        }
        logger.debug("End execution of TaskListProxyEJB.getTaskListItems method");
        return ar;
    }

    /**
     * @roseuid 3C51885701A5
     * @J2EE_METHOD  --  getMyInvestigations
     */
    public Collection<Object>  getMyInvestigations    (NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, Exception
    {
        TaskListProxyDAOImpl taskListProxyDAOImpl = new TaskListProxyDAOImpl();
        ArrayList<Object> ar = new ArrayList<Object> ();
        TaskListItemVO taskListItemVO = new TaskListItemVO();
        try
        {
            ar = (ArrayList<Object> )taskListProxyDAOImpl.getInvestigationSummaryVOCollection(nbsSecurityObj);
        }
        catch(NEDSSSystemException nsex)
        {
        	 logger.fatal("TaskListProxyEJB.getMyInvestigations: " + nsex.getMessage(), nsex);
        	 throw new Exception(nsex.getMessage(),nsex);
        }
        return ar;
    }

    //*****************************************************************************************/
    //BEGIN: getObservationsNeedingSecurityAssignment (NBSSecurityObj nbsSecurityObj)
    //*****************************************************************************************/
    /**
     * @roseuid 3C51885701FF
     * @J2EE_METHOD  --  getObservationsNeedingSecurityAssignment
     */
    public Collection<Object>  getObservationsNeedingSecurityAssignment    (NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, Exception
    {
      try {
		if(nbsSecurityObj == null) throw new NullPointerException("Expected a valid nbs security object");

		  //Check security permission
		  checkPermissionToAssignSecurity(nbsSecurityObj);

		  //Finds all observation uids needing review
		  TaskListProxyDAOImpl taskListProxyDAOImpl = new TaskListProxyDAOImpl();
		  List[] observationUidsForSecurityAssignment = taskListProxyDAOImpl.findObservationsNeedingSecurityAssignment(nbsSecurityObj);

		//Retrieves report summary and returns
		return retrieveReportSummaryVOColl(observationUidsForSecurityAssignment, nbsSecurityObj);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		 logger.fatal("TaskListProxyEJB.getObservationsNeedingSecurityAssignment: " + e.getMessage(), e);
		 throw new Exception(e.getMessage(),e);
	}
    }

    public Collection<Object>  getObservationsNeedingSecurity (NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, Exception
    {
      try {
		Collection<Object>  reportCollection  = new ArrayList<Object> ();
		  if(nbsSecurityObj == null) throw new NullPointerException("Expected a valid nbs security object");

		  //Check security permission
		  checkPermissionToAssignSecurity(nbsSecurityObj);
		  ObservationProcessor helper = new ObservationProcessor();
		  NbsDocumentDAOImpl docProcess = new NbsDocumentDAOImpl();
		  //reportCollection  = helper.getLabReportSummaryVOCollForSecurity(nbsSecurityObj);
		  reportCollection  = helper.getLabReportSummaryVOColl(nbsSecurityObj, true);
		  Collection<Object>  morbColl = helper.getMorbReportSummaryVOColl(nbsSecurityObj, true);
		  Collection<Object>  docColl = docProcess.getDocReportSummaryVOCollForSecurity(nbsSecurityObj);
		  
		  reportCollection.addAll(morbColl);
		  reportCollection.addAll(docColl);
  
		//Retrieves report summary and returns
		return reportCollection;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		 logger.fatal("TaskListProxyEJB.getObservationsNeedingSecurity: " + e.getMessage(), e);
		 throw new Exception(e.getMessage(),e);
	}

    }

    public Collection<Object>  getObservationForReview(NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, Exception
    {
      try {
		Collection<Object>  reportCollection  = new ArrayList<Object> ();
		  if(nbsSecurityObj == null) throw new NullPointerException("Expected a valid nbs security object");

		  // Get Security Properties for ObsNeedingReview
		  String obsNeedingReviewSecurity = propertyUtil.getObservationsNeedingReviewSecurity();
		  logger.debug("TaskListProxyEJB.getObservationForReview - obsNeedingReviewSecurity = " + obsNeedingReviewSecurity);
		
		  if (obsNeedingReviewSecurity==null) 
		      throw new NEDSSSystemException("TaskListProxyEJB.getObservationForReview - Security property settings missing for ObsNeedingReview");

		  if ( !(obsNeedingReviewSecurity.equals(NBSOperationLookup.VIEW)) &&
		  	 !(obsNeedingReviewSecurity.equals(NBSOperationLookup.EDIT)) )
		      throw new NEDSSSystemException("TaskListProxyEJB.getObservationForReview - Security property settings for ObsNeedingReview is incorrect");

		  //Check security permission
		  //checkPermissionToViewObservation(nbsSecurityObj);
		  ObservationProcessor helper = new ObservationProcessor();
	
		  logger.info("\n DRR Performance - Starting to get Lab Report Summary VO Collection at " +LocalDateTime.now());
		  reportCollection  = helper.getLabReportSummaryVOColl(nbsSecurityObj, false);
		  logger.info("\n DRR Performance - Completed getting Lab Report Summary VO Collection at " +LocalDateTime.now()+" Size is "+reportCollection.size());
		  logger.info("\n DRR Performance - Starting to get Morb Report Summary VO Collection at " +LocalDateTime.now());
		  Collection<Object>  morbColl = helper.getMorbReportSummaryVOColl(nbsSecurityObj, false);
		  logger.info("\n DRR Performance - Completed getting Morb Report Summary VO Collection at " +LocalDateTime.now() +" Size is "+morbColl.size());
		  //Collection<Object>  docColl = helper.getMorbReportSummaryVOColl(nbsSecurityObj);
		 
		  reportCollection.addAll(morbColl);
		  return reportCollection;

		//Retrieves report summary and returns
		//return retrieveReportSummaryVOColl(observationUidsForSecurityAssignment, nbsSecurityObj);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		 logger.fatal("TaskListProxyEJB.getObservationForReview: " + e.getMessage(), e);
		 throw new Exception(e.getMessage(),e);
	}
    }

   private void checkPermissionToAssignSecurity(NBSSecurityObj securityObj)
   {
     //Aborts the operation if not getting the permission
     if(!securityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.ASSIGNSECURITY)
        && !securityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.ASSIGNSECURITY) 
        && !securityObj.getPermission(NBSBOLookup.DOCUMENT,NBSOperationLookup.ASSIGNSECURITY))
     throw new SecurityException("Expected the user to have view lab and morb report permissions.");
    }
    //#######################################################################################/
    //END: getObservationsNeedingSecurityAssignment (NBSSecurityObj nbsSecurityObj)
    //#######################################################################################/



    /**
     * @roseuid 3C518857024F
     * @J2EE_METHOD  --  getInvestigationsNeedingSecurityAssignment
     */
    public Collection<Object>  getInvestigationsNeedingSecurityAssignment    (NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, Exception
    {
        TaskListProxyDAOImpl taskListProxyDAOImpl = new TaskListProxyDAOImpl();
        ArrayList<Object> ar = new ArrayList<Object> ();
        TaskListItemVO taskListItemVO = new TaskListItemVO();
        try
        {
         //  ar = (ArrayList<Object> )taskListProxyDAOImpl.getInvestigationsNeedingSecurityAssignment(nbsSecurityObj);
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("TaskListProxyEJB.getInvestigationsNeedingSecurityAssignment: " + nsex.getMessage(), nsex);
            throw new Exception(nsex.getMessage(),nsex);
        }
        return ar;
    }

    //*****************************************************************************************/
    //BEGIN: getObservationsNeedingReview (NBSSecurityObj nbsSecurityObj)
    //*****************************************************************************************/
    /**
     * @roseuid 3C51885702A9
     * @J2EE_METHOD  --  getObservationsNeedingReview
     */
    public Collection<Object>  getObservationsNeedingReview    (NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, Exception
    {
      try {
		if(nbsSecurityObj == null) throw new NullPointerException("Expected a valid nbs security object");

		  //Check security permission
		  checkPermissionToViewObservation(nbsSecurityObj);

		  //Finds all observation uids needing review
		  TaskListProxyDAOImpl taskListProxyDAOImpl = new TaskListProxyDAOImpl();
		  List[] observationUidsForReview = taskListProxyDAOImpl.findObservationsNeedingReview(nbsSecurityObj);

		  //Retrieves report summary and returns
		  return retrieveReportSummaryVOColl(observationUidsForReview, nbsSecurityObj);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		 logger.fatal("TaskListProxyEJB.getObservationsNeedingReview: " + e.getMessage(), e);
		 throw new Exception(e.getMessage(),e);
	}
    }

    private void checkPermissionToViewObservation(NBSSecurityObj securityObj)
   {
     //Aborts the operation if not getting the permission to view labreport and morbreport
     if(!securityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.VIEW)
        && !securityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.VIEW))
     throw new SecurityException("Expected the user to have view lab and morb report permissions.");
    }

    private Collection<Object>  retrieveReportSummaryVOColl(List[] observationUidsForReview, NBSSecurityObj securityObj) throws javax.ejb.EJBException, Exception
    {
      try {
		int LAB_REPORT_UID_LIST = 0, MORB_REPORT_UID_LIST = 1;

		  List<Object> labReportUids = observationUidsForReview[LAB_REPORT_UID_LIST];
		  List<Object> morbReportUids = observationUidsForReview[MORB_REPORT_UID_LIST];
		  ObservationProcessor helper = null;
		  Collection<Object>  reportSummaryColl = null;

		  long beforeLab =0; long afterLab = 0; long totalLab = 0;
		  long beforeMorb =0; long afterMorb = 0; long totalMorb = 0;
		  long t1begin = 0; long t1end = 0;


		  if(labReportUids != null && labReportUids.size()>0 && securityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.VIEW))
		  {
		    beforeLab = System.currentTimeMillis();
		    logger.debug("labReportUids.size(): "+ labReportUids.size());
		    helper = new ObservationProcessor();
		    reportSummaryColl = new ArrayList<Object> ();
		    String uidType = "LABORATORY_UID";
		    Collection<Object>  newLabReportSummaryVOCollection  = new ArrayList<Object> ();
		    Collection<?>  labReportSummaryVOCollection  = new ArrayList<Object> ();
		    LabReportSummaryVO labReportSummaryVOs = new LabReportSummaryVO();
		    Collection<Object>  labColl = new ArrayList<Object> ();
		    HashMap<Object,Object> labSumVOMap = new HashMap<Object,Object>();
		    Collection<Object>  labReportSummaryList = new ArrayList<Object> ();
		    labReportUids = helper.getUidSummaryVOArrayList(labReportUids);
		    
		   // Collection<Object>  labReportSummaryList = helper.retrieveLabReportSummary(labReportUids, securityObj);
		    labSumVOMap = helper.retrieveLabReportSummaryRevisited(
		    		labReportUids,false, securityObj, uidType);
		    if(labSumVOMap !=null)
			{
				if(labSumVOMap.containsKey("labEventList"))
				{
				  labReportSummaryVOCollection  = (ArrayList<?> )labSumVOMap.get("labEventList");
				 Iterator<?>  iterator = labReportSummaryVOCollection.iterator();
		  		  while( iterator.hasNext())
		  		  {
		  			 labReportSummaryVOs = (LabReportSummaryVO) iterator. next();
		  			labReportSummaryList.add(labReportSummaryVOs);
		  			 
		  		  }
				}
			}
		    logger.debug("labReportSummaryList.size()" + labReportSummaryList.size());
		    if(labReportSummaryList != null) reportSummaryColl.addAll(labReportSummaryList);
		      afterLab = System.currentTimeMillis();
		  }
		  if(morbReportUids != null && morbReportUids.size() > 0 && securityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.VIEW))
		  {
		    beforeMorb = System.currentTimeMillis();
		    logger.debug("morbReportUids.size(): " + morbReportUids.size());
		    if(helper == null) helper = new ObservationProcessor();
		    String uidType = "MORBIDITY_UID";
		    HashMap<Object,Object> morbReportSummaryMap = new HashMap<Object,Object>();
		    Collection<Object>  morbReportSummaryList = new ArrayList<Object> ();
		    Collection<Object>  newMobReportSummaryVOCollection  = new ArrayList<Object> ();
		    Collection<?>  mobReportSummaryVOCollection  = new ArrayList<Object> ();
		    MorbReportSummaryVO mobReportSummaryVOs = new MorbReportSummaryVO();
		    if(reportSummaryColl == null) reportSummaryColl = new ArrayList<Object> ();
		    {
		    	morbReportUids = helper.getUidSummaryVOArrayList(morbReportUids);
		        //Collection<Object>  morbReportSummaryList = helper.retrieveMorbReportSummary(morbReportUids, securityObj);
		    	morbReportSummaryMap = helper.retrieveMorbReportSummaryRevisited(morbReportUids, false, securityObj, uidType);
		    	if(morbReportSummaryMap !=null)
		    	{
		    		if(morbReportSummaryMap.containsKey("MorbEventColl"))
		    		{
		    		  mobReportSummaryVOCollection  = (ArrayList<?> )morbReportSummaryMap.get("MorbEventColl");
		    		 Iterator<?>  iterator = mobReportSummaryVOCollection.iterator();
		      		  while( iterator.hasNext())
		      		  {
		      			mobReportSummaryVOs = (MorbReportSummaryVO) iterator. next();
		      			morbReportSummaryList.add(mobReportSummaryVOs);
		      			 
		      		  }
		    		}
		    	}
		      logger.debug("morbReportSummaryList.size()" +
		                   morbReportSummaryList.size());
		      if (morbReportSummaryList != null)
		        reportSummaryColl.addAll(morbReportSummaryList);

		    }
		    afterMorb = System.currentTimeMillis();
		  }
		  logger.debug("reportSummaryColl.size()" + reportSummaryColl.size());
		  System.out.println("Total time for Lab: " + new Long(afterLab-beforeLab));
		  System.out.println("Total time for Morb: " + new Long(afterMorb-beforeMorb));
		  System.out.println("Total time for Both: " + new Long((afterMorb-beforeMorb) + (afterLab-beforeLab)));
		  return reportSummaryColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		 logger.fatal("TaskListProxyEJB.retrieveReportSummaryVOColl: " + e.getMessage(), e);
		 throw new Exception(e.getMessage(),e);
	}
    }

    //#######################################################################################/
    //END: getObservationsNeedingReview (NBSSecurityObj nbsSecurityObj)
    //#######################################################################################/



    /**
     * @roseuid 3C518857030E
     * @J2EE_METHOD  --  getNotificationsForApproval
     */
    public Collection<Object>  getNotificationsForApproval    (NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException
    {
  return null;
    }
    
	public Collection<Object>  getDocumentForReview(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		 logger.info("\n DRR Performance - Starting Document Collection at " +LocalDateTime.now());
		try {
			NbsDocumentDAOImpl nbsDocDAO = new NbsDocumentDAOImpl();
			Collection<Object>  docColl = nbsDocDAO.getDocSummaryVOCollForReview(nbsSecurityObj);
			logger.info("\n DRR Performance - Completed getting Document Collection at " +LocalDateTime.now() +" Size is: "+docColl.size());	
					
			return docColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 logger.fatal("TaskListProxyEJB.getDocumentForReview: " + e.getMessage(), e);
			 throw new Exception(e.getMessage(),e);
		}
	}
	
	public Collection<Object>  getMessageQueue(Long assignedToId, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		 try {
			MessageLogDAOImpl dao = new MessageLogDAOImpl();
			 MessageLogDT messageLogDt = new MessageLogDT();
			 List<Object> messageVOs = new ArrayList<Object>();
			 Long id = nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
			 messageLogDt.setAssignedToUid(id);
			 messageVOs = dao.getMessageLog(assignedToId, nbsSecurityObj);
			 return messageVOs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 logger.fatal("TaskListProxyEJB.getMessageQueue: " + e.getMessage(), e);
			 throw new Exception(e.getMessage(),e);
		}
	}


}