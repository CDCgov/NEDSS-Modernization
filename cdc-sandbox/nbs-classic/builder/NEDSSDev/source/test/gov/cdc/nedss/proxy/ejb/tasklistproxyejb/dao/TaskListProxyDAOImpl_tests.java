package test.gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.directwebremoting.WebContextFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseDAOImpl;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.MessageBuilderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.queue.dao.MessageLogDAOImpl;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.bean.TaskListProxyEJB;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao.TaskListProxyDAOImpl;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.vo.CustomQueueVO;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.vo.TaskListItemVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRUpdateHelper;
import gov.cdc.nedss.systemservice.ejb.trigercodeejb.dao.TriggerCodeDAOImpl;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class TaskListProxyDAOImpl_tests  {
	
	/**
	 * DeleteCustomQueue_test: this test method validates the method returns 0 meaning deleting the custom queue was successful.
	 * @author Fatima.Lopezcalzado
	 *
	 */
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao.TaskListProxyDAOImpl"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({TaskListProxyDAOImpl.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	 public static class DeleteCustomQueue_test {
		
		 private int iteration;
		 private String queueName;
		 private String expectedValue;
		 
		@Mock
		 NBSSecurityObj nbsSecurityObj;

    	@Mock
		LogUtils loggerMock;
		
		@InjectMocks
		@Spy 
		TaskListProxyDAOImpl taskListProxyDAOImpl=  Mockito.spy(TaskListProxyDAOImpl.class);
		 
		 public DeleteCustomQueue_test(String queueName, String expectedValue, int it){
			 this.queueName = queueName;
			 this.expectedValue=expectedValue;
			 this.iteration = it;
		 }
		 

		 @Before
		 public void initMocks() throws Exception {
			 nbsSecurityObj=Mockito.mock(NBSSecurityObj.class);
			 loggerMock = Mockito.mock(LogUtils.class);
			 Whitebox.setInternalState(TaskListProxyDAOImpl.class, "logger", loggerMock);
		 }
		 
		 @Test
		   public void deleteCustomQueue_test() throws Exception {
			   String userId="10000000";
			   Mockito.doReturn(0).when(taskListProxyDAOImpl).preparedStmtMethod(any(RootDTInterface.class),any(ArrayList.class),any(String.class),any(String.class));
			   String actualResult = taskListProxyDAOImpl.deleteCustomQueue(this.queueName, userId, nbsSecurityObj);
			   Assert.assertEquals(expectedValue, actualResult);
			   System.out.println("Class Name: TaskListProxyDAOImpl.java, Method NAme: deleteCustomQueue, Iteration: "+iteration+", QueueName: "+this.queueName+" Expected value: "+expectedValue+"; Actual value: "+actualResult+"; RESULT:::::PASSED");;

		   }
		


		@SuppressWarnings("rawtypes")
		  @Parameterized.Parameters
		  public static Collection input() {
				return Arrays.asList(new Object[][] {
					{ "QueueName1", "0", 1 },
					{ "QueueName2", "0", 2 },
					{ "QueueName3", "0", 3 },
					{ "QueueName4", "0", 4 },
					{ "QueueName5", "0", 5 },
					{ "QueueName6", "0", 6 },
					{ "QueueName7", "0", 7 },
					{ "QueueName8", "0", 8 },
					{ "WMS_6M_NBS_CHANGES", "0", 9 },
					{ "QueueName10", "0", 10 }
				});
		  	}
	 	 }
	 
	/**
	 * IsExistingCustomQueueName_test: this test method validates that for the specific queue name and user id, the method returns
	 * true if the custom queue name exists. The internal method called to return if the queue exists or not, always returns 1,
	 * meaning the custom queue exists. Other test cases should have been created to return 0 meaning the custom queue doesn't exist
	 * and validate that scenario.
	 * 
	 * @author Fatima.Lopezcalzado
	 *
	 */
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil", "gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao.TaskListProxyDAOImpl"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({TaskListProxyDAOImpl.class, Connection.class,PreparedStatement.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	 public static class IsExistingCustomQueueName_test {
		 private int iteration;
		 private String queueName;
		 private boolean expectedValue;
		
		@Mock
		LogUtils loggerMock;
		

		@InjectMocks
		@Spy 
		TaskListProxyDAOImpl taskListProxyDAOImpl=  Mockito.spy(TaskListProxyDAOImpl.class);
		 
		 public IsExistingCustomQueueName_test(String queueName, boolean expectedValue, int it){
			 this.queueName = queueName;
			 this.expectedValue=expectedValue;
			 this.iteration = it;
		 }
		 
		 @Before
		 public void initMocks() throws Exception {
			 loggerMock = Mockito.mock(LogUtils.class);
			 Whitebox.setInternalState(TaskListProxyDAOImpl.class, "logger", loggerMock);
		 }
		 
		  @Test
		   public void isExistingCustomQueueName_Test() throws Exception {
			  Mockito.doReturn(1).when(taskListProxyDAOImpl).preparedStmtMethod(any(RootDTInterface.class),any(ArrayList.class),any(String.class),any(String.class));
		      String userId="10000000";
			  boolean actualValue=taskListProxyDAOImpl.isExistingCustomQueueName(this.queueName, userId);
			  Assert.assertEquals(expectedValue, actualValue);
			  System.out.println("Class Name: TaskListProxyDAOImpl.java, Method Name:isExistingCustomQueueName, Iteration: "+iteration+", QueueName: "+this.queueName+" Expected value: "+expectedValue+"; Actual value: "+actualValue+"; RESULT:::::PASSED");;
		   }
		 
		 @SuppressWarnings("rawtypes")
		  @Parameterized.Parameters
		   public static Collection input() {
				return Arrays.asList(new Object[][] {
					{ "QueueName1",true, 1 },
					{ "QueueName2",true, 2 },
					{ "QueueName3",true, 3 },
					{ "QueueName4", true, 4 },
					{ "QueueName5", true, 5 },
					{ "QueueName6", true, 6 },
					{ "QueueName7", true, 7 },
					{ "QueueName8", true, 8 },
					{ "WMS_6M_NBS_CHANGES",true, 9 },
					{ "QueueName10",true, 10 }
				});
					
				}
	 }
		 
	 /**
	  * InsertCustomQueue_test: validates the method runs successfully by making sure the value returned is 0, meaning
	  * the custom queue was successfully inserted.
	  * @author Fatima.Lopezcalzado
	  *
	  */
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao.TaskListProxyDAOImpl"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({TaskListProxyDAOImpl.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
    public static class InsertCustomQueue_test{
		
		private String queueName;
		private String sourceTable;
		private static String queryString="SELECT  obs.observation_uid as ObservationUid,  obs.local_id as LocalId,  obs.shared_ind as sharedInd,  OBS.electronic_ind as electronicInd,  OBS.add_user_id as addUserId,  obs.jurisdiction_cd as JurisdictionCd,  obs.prog_area_cd as ProgramArea ,  obs.rpt_to_state_time as DateReceived,  obs.version_ctrl_nbr as versionCtrlNbr,  obs.ctrl_cd_display_form as ctrlCdDisplayForm,  obs.status_cd as Status,  obs1.cd_desc_txt as resultedTest, obs1.cd as resultedTestCd,  obs1.cd_system_cd as resultedTestCdSystemCd,  obs.record_status_cd as recordStatusCd,   ISNULL(pnm.last_nm,'No Last') as lastName, ISNULL(pnm.first_nm,'No First') as firstName, p.curr_sex_cd as currentSex,  p.local_id as personLocalID,   p.birth_time as birthTime,   p.person_parent_uid as MPRUid,  p.age_reported as ageReported,  p.age_reported_unit_cd as ageUnit  FROM observation obs with(nolock)  inner join act_relationship act with(nolock)  on obs.observation_uid = act.target_act_uid  inner join observation obs1 with(nolock)  on act.source_act_uid = obs1.observation_uid  inner join Participation part with(nolock)  on part.act_uid = obs.observation_uid  inner join person p with(nolock)  on p.person_uid = part.subject_entity_uid  inner join person_name pnm  with(nolock)  on pnm.person_uid = p.person_uid  AND obs1.obs_domain_cd_st_1 = 'Result' AND (act.source_act_uid = obs1.observation_uid) AND (act.target_class_cd = 'OBS') AND (act.type_cd = 'COMP')  AND (act.source_class_cd = 'OBS') AND (act.record_status_cd = 'ACTIVE') and (obs.record_status_cd in ('PROCESSED', 'UNPROCESSED')) and obs.ctrl_cd_display_form = 'LabReport' and (((obs.program_jurisdiction_oid in (1300400010, 1300400011, 1300400008, 1300400009, 1300400014, 1300400015, 1300400012, 1300400013, 1300400016, 1300500012, 1300500013, 1300500014, 1300500015, 1300500008, 1300500009, 1300500010, 1300500011, 1300200016, 1300200015, 1300200014, 1300200013, 1300200012, 1300200011, 1300500016, 1300200010, 1300200009, 1300200008, 1300600013, 1300600012, 1300600015, 1300600014, 1300600009, 1300600008, 1300100016, 1300600011, 1300600010, 1300100013, 1300100012, 1300100015, 1300100014, 1300100009, 1300100008, 1300600016, 1300100011, 1300100010, 1300300009, 1300300008, 1300300011, 1300300010, 1300300013, 1300300012, 1300300015, 1300300014, 1300300016))) AND  obs.RPT_TO_STATE_TIME  >= DATEADD(M, -6, GETDATE())) and (obs.record_status_cd='UNPROCESSED') AND  obs.prog_area_cd  in ('','BMIRD','GCD','HEP') AND  (upper( obs.electronic_ind) =   'Y' or obs.electronic_ind='N' or obs.electronic_ind='E' ) AND  obs.version_ctrl_nbr = '1' and part.type_cd='PATSBJ' where obs.observation_uid in ( select  distinct  top 100  obs.observation_uid FROM observation obs with(nolock)  inner join act_relationship act with(nolock)  on obs.observation_uid = act.target_act_uid  inner join observation obs1 with(nolock)  on act.source_act_uid = obs1.observation_uid  inner join Participation part with(nolock)  on part.act_uid = obs.observation_uid  inner join person p with(nolock)  on p.person_uid = part.subject_entity_uid  inner join person_name pnm  with(nolock)  on pnm.person_uid = p.person_uid  AND obs1.obs_domain_cd_st_1 = 'Result' AND (act.source_act_uid = obs1.observation_uid) AND (act.target_class_cd = 'OBS') AND (act.type_cd = 'COMP')  AND (act.source_class_cd = 'OBS') AND (act.record_status_cd = 'ACTIVE') and (obs.record_status_cd in ('PROCESSED', 'UNPROCESSED')) and obs.ctrl_cd_display_form = 'LabReport' and (((obs.program_jurisdiction_oid in (1300400010, 1300400011, 1300400008, 1300400009, 1300400014, 1300400015, 1300400012, 1300400013, 1300400016, 1300500012, 1300500013, 1300500014, 1300500015, 1300500008, 1300500009, 1300500010, 1300500011, 1300200016, 1300200015, 1300200014, 1300200013, 1300200012, 1300200011, 1300500016, 1300200010, 1300200009, 1300200008, 1300600013, 1300600012, 1300600015, 1300600014, 1300600009, 1300600008, 1300100016, 1300600011, 1300600010, 1300100013, 1300100012, 1300100015, 1300100014, 1300100009, 1300100008, 1300600016, 1300100011, 1300100010, 1300300009, 1300300008, 1300300011, 1300300010, 1300300013, 1300300012, 1300300015, 1300300014, 1300300016))) AND  obs.RPT_TO_STATE_TIME  >= DATEADD(M, -6, GETDATE())) and (obs.record_status_cd='UNPROCESSED') AND  obs.prog_area_cd  in ('','BMIRD','GCD','HEP') AND  (upper( obs.electronic_ind) =   'Y' or obs.electronic_ind='N' or obs.electronic_ind='E' ) AND  obs.version_ctrl_nbr = '1' and obs.ctrl_cd_display_form='LabReport' and obs.record_status_cd<> 'LOG_DEL')";
		private String description;
		private String listOfUsers;
		private String searchCriteriaDesc;
		private String searchCriteriaCd;
		private boolean expectedValue;
		private int iteration;
		private String userId;
		 
		@Mock
		 NBSSecurityObj nbsSecurityObj;

		@Mock
		LogUtils loggerMock;
		
		@Mock
		PropertyUtil propertyUtil;
		
		@InjectMocks
		@Spy
		TaskListProxyDAOImpl taskListProxyDAOImpl=Mockito.spy(TaskListProxyDAOImpl.class);
	
		 
    	
		 public InsertCustomQueue_test(String queueName, String sourceTable, String description, String listOfUsers,String userId,
					String searchCriteriaDesc, String searchCriteriaCd, boolean expectedValue, int iteration) {
				this.queueName = queueName;
				this.sourceTable = sourceTable;
				this.description = description;
				this.listOfUsers = listOfUsers;
				this.userId=userId;
				this.searchCriteriaDesc = searchCriteriaDesc;
				this.searchCriteriaCd = searchCriteriaCd;
				this.expectedValue = expectedValue;
				this.iteration=iteration;
			}   	

    	 @Before
		 public void initMocks() throws Exception {
    		 nbsSecurityObj=Mockito.mock(NBSSecurityObj.class);
			 loggerMock = Mockito.mock(LogUtils.class);
			 propertyUtil = Mockito.mock(PropertyUtil.class);
			 Whitebox.setInternalState(TaskListProxyDAOImpl.class, "logger", loggerMock);
			 Whitebox.setInternalState(TaskListProxyDAOImpl.class, "propertyUtil", propertyUtil);
		 }
    	 
    	 @Test
		 public void insertCustomQueue_test() throws Exception {
    		 when(propertyUtil.getLabNumberOfRows()).thenReturn(10);
			 ArrayList<Object> oparam1=getParametersForSaveQueue();
			 Mockito.doReturn(0).when(taskListProxyDAOImpl).preparedStmtMethod(any(RootDTInterface.class),any(ArrayList.class),any(String.class),any(String.class));
    		 String actualValue= taskListProxyDAOImpl.insertCustomQueue(this.queueName, this.sourceTable, InsertCustomQueue_test.queryString, this.description, this.listOfUsers, new Long(10000000), this.searchCriteriaDesc, this.searchCriteriaCd, nbsSecurityObj);
    		 boolean actualResult=("0".equalsIgnoreCase(actualValue))?true:false;
 			 Assert.assertEquals(this.expectedValue,actualResult);
			 System.out.println("Class Name: TaskListProxyDAOImpl.java, Method: saveCustomQueue, Iteration: "+iteration+", QueueName: "+this.queueName+" Expected value: "+expectedValue+"; Actual value: "+actualResult+"; RESULT:::::PASSED");

    	 }
    	 
    	 @SuppressWarnings("rawtypes")
		  @Parameterized.Parameters
		   public static Collection input() {
				return Arrays.asList(new Object[][] {
					{ "QueueName1","I","QueueName1","ALL","10000000","searchCriteriaDesc","searchCriteriaCd", true, 1 },
					{ "QueueName2","LR","QueueName2","CURRENT","10000000","searchCriteriaDesc","searchCriteriaCd", true, 2 },
					{ "QueueName3","I","QueueName3","ALL","10000000","searchCriteriaDesc","searchCriteriaCd", true, 3 },
					{ "QueueName4","LR","QueueName4","ALL","10000000","searchCriteriaDesc","searchCriteriaCd", true, 4 },
					{ "QueueName5","I","QueueName5","ALL","10000000","searchCriteriaDesc","searchCriteriaCd", true, 5 },
					{ "QueueName6","LR","QueueName6","CURRENT","10000000","searchCriteriaDesc","searchCriteriaCd", true, 6 },
					{ "QueueName7","I","QueueName7","ALL","10000000","searchCriteriaDesc","searchCriteriaCd", true, 7 },
					{ "QueueName8","LR","QueueName8","CURRENT","10000000","searchCriteriaDesc","searchCriteriaCd", true, 8 },
					{ "QueueName9","I","QueueName9","CURRENT","10000000","searchCriteriaDesc","searchCriteriaCd", true, 9 },
					{ "QueueName10","LR","QueueName10","ALL","10000000","searchCriteriaDesc","searchCriteriaCd", true, 10 }
					
				});
					
				}
    	 
    	 
    	 private ArrayList<Object> getParametersForSaveQueue() {
    		 java.util.Date dateTime = new java.util.Date();
		     Timestamp systemTime = new Timestamp(dateTime.getTime());
    		 ArrayList<Object> paramList = new ArrayList<Object>();
		     paramList.add(this.queueName);
		     paramList.add(this.sourceTable);
		     paramList.add("select");
		     paramList.add("where");
		     paramList.add(this.description);
		     paramList.add(this.listOfUsers);
		     paramList.add(this.searchCriteriaDesc);
		     paramList.add(this.searchCriteriaCd);
		     paramList.add("ACTIVE");
		     paramList.add(systemTime);
		     paramList.add(systemTime);
		     paramList.add(new Long(this.userId));
		     paramList.add(systemTime);
		     paramList.add(new Long(this.userId));
		     return paramList;
    	 }
    	 
    	
	 }
	
	
	/**
	 * GetCustomQueues_test: this method will pass if the size of the array returned is the expected one, meaning the size of the list
	 * of custom queues returned by the method is correct.
	 * @author Fatima.Lopezcalzado
	 *
	 */
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao.TaskListProxyDAOImpl"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({TaskListProxyDAOImpl.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
    public static class GetCustomQueues_test{
		private boolean expectedValue;
		private int iteration;
		private int customVoCollection;
		private String finalQuery;
		private int resultIteration;
		
		@Mock
		 NBSSecurityObj nbsSecurityObj;
		@Mock
		LogUtils loggerMock;
		
		@Mock
		PropertyUtil propertyUtil;
		
				
		@Mock
		UserProfile userProfile;
		
		@Mock
		CustomQueueVO customQueueVo;
		
		@Mock
		User user;
		
		@InjectMocks
		@Spy
		TaskListProxyDAOImpl taskListProxyDAOImpl=Mockito.spy(TaskListProxyDAOImpl.class);
		
		public GetCustomQueues_test(String finalQuery,int resultIteration,int customVoCollection,boolean expectedValue,int iteration) {
			this.finalQuery=finalQuery;
			this.resultIteration=resultIteration;
			this.customVoCollection=customVoCollection;
			this.expectedValue = expectedValue;
			this.iteration = iteration;
		}
		
		
		 @Before
		 public void initMocks() throws Exception {
			 nbsSecurityObj=Mockito.mock(NBSSecurityObj.class);
			 userProfile=Mockito.mock(UserProfile.class);
			 user=Mockito.mock(User.class);		
			 loggerMock = Mockito.mock(LogUtils.class);
			 propertyUtil = Mockito.mock(PropertyUtil.class);
			 customQueueVo = Mockito.mock(CustomQueueVO.class);
			 Whitebox.setInternalState(TaskListProxyDAOImpl.class, "logger", loggerMock);
			 Whitebox.setInternalState(TaskListProxyDAOImpl.class,"propertyUtil", propertyUtil);
			 Whitebox.setInternalState(ResultSetUtils.class, "propertyUtil", propertyUtil);
		 }
		 
		 
		 @SuppressWarnings({ "unchecked", "rawtypes" })
		 @Test
		 public void getCustomQueues_test() throws Exception {
			 String userIdString="10000000";
			 when(nbsSecurityObj.getTheUserProfile()).thenReturn(userProfile);
			 when(userProfile.getTheUser()).thenReturn(user);
			 when(user.getEntryID()).thenReturn(userIdString);
    		 //when(propertyUtil.getDatabaseServerType()).thenReturn("sql");
			 ArrayList<Object> queueList=retriveCustomQueueVOList(this.customVoCollection);
			 Mockito.doReturn(queueList).when(taskListProxyDAOImpl).preparedStmtMethod(any(RootDTInterface.class),any(ArrayList.class),any(String.class),any(String.class));
			 ArrayList<TaskListItemVO> actualQueueList=taskListProxyDAOImpl.getCustomQueues(nbsSecurityObj);
			 boolean actualResult=null != actualQueueList && actualQueueList.size()==queueList.size()?true:false;
    		 Assert.assertEquals(this.expectedValue,actualResult);
 			 System.out.println("Class Name: TaskListProxyDAOImpl.java, Method:getCustomQueue, Iteration: "+iteration+", Expected value: "+expectedValue+"; Actual value: "+actualResult+"; RESULT:::::PASSED");

		 }
		
		
		
		private ArrayList<Object> retriveCustomQueueVOList(int listSize){
			ArrayList<Object>   reportCustomQueues  = new ArrayList<Object> ();
			String sourceTable="LR";
			for(int i=1; i <= listSize; i++) { 
			   if(i%2 == 0) sourceTable="I";
			   reportCustomQueues.add(populateCustomQueueVOObjects("QueueName"+i,sourceTable, "SELECT", "WHERE", "ALL", "QueueName"+i, "searchCriteriaDesc","searchCriteriaCd"));	
			}
			return reportCustomQueues;
		}
		
			
		 
		 private CustomQueueVO populateCustomQueueVOObjects(String QueueName, String SourceTable,String queryPart1, String queryPart2, String listOfUsers,
                 String queueDescription, String searchCriteriaDesc, String searchCriteriaCd) {
			 customQueueVo.setQueueName(QueueName);
			 customQueueVo.setSourceTable(SourceTable);
			 customQueueVo.setQueryStringPart1(queryPart1);
			 customQueueVo.setQueryStringPart2(queryPart2);
			 customQueueVo.setListOfUsers(listOfUsers);
			 customQueueVo.setDescription(queueDescription);
			 customQueueVo.setSearchCriteriaDesc(searchCriteriaDesc);
			 customQueueVo.setSearchCriteriaCd(searchCriteriaCd);
			 return customQueueVo;
          }

		
		 @SuppressWarnings("rawtypes")
		  @Parameterized.Parameters
		   public static Collection input() {
				return Arrays.asList(new Object[][] {
					{ "select",11,11,true, 1 },
					{ "select",4,4,true, 2 },
					{ "select",6,6,true, 3 },
					{ "select",7,7,true, 4 },
					{ "select",8,8,true, 5 },
					{ "select",11,11,true, 6 },
					{ "select",11,11,true, 7 },
					{ "select",1,11,true, 8 },
					{ "select",11,11,true, 9 },
					{ "select",11,11,true, 10 }
					
				});
					
				}

		}
		
	/**
	 * 	GetTaskListItems_test: this method passes if the method GetTaskListItems runs successfully for the values received as parameters.
	 * @author Fatima.Lopezcalzado
	 *
	 */
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao.TaskListProxyDAOImpl","gov.cdc.nedss.proxy.ejb.queue.dao.MessageLogDAOImpl"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({TaskListProxyDAOImpl.class,PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
    public static class GetTaskListItems_test{
		
		private int expectedValue;
		private int iteration;
		private boolean labReportAssignment; //flag1
		private boolean morbReportAssignment;//flag2
		private boolean docAssignment;//flag3
		private boolean labReportReview; //flag1
		private boolean morbReportReview; //flag2
		private boolean documentReview; //flag3
		
		
		public GetTaskListItems_test(boolean labReportAssignment,
				boolean morbReportAssignment, boolean docAssignment, boolean labReportReview, boolean morbReportReview,
				boolean documentReview, int expectedValue, int iteration) {
			super();
			this.expectedValue = expectedValue;
			this.iteration = iteration;
			this.labReportAssignment = labReportAssignment;
			this.morbReportAssignment = morbReportAssignment;
			this.docAssignment = docAssignment;
			this.labReportReview = labReportReview;
			this.morbReportReview = morbReportReview;
			this.documentReview = documentReview;
		}

		@Mock
		PropertyUtil propertyUtil;
		
		@Mock
		LogUtils logger;
		
		@Mock
		 NBSSecurityObj nbsSecurityObj;
		
		@Mock
		TaskListItemVO taskListItemVO;
		
		@Mock
		UserProfile  userProfile;
		
		@Mock
		User  user;
		
		@Mock
		MessageLogDAOImpl messageLogDao;
		
		@Mock
		PublicHealthCaseDAOImpl PHCDao;
		
		@InjectMocks
		@Spy
		TaskListProxyDAOImpl taskListProxyDAOImpl=Mockito.spy(new TaskListProxyDAOImpl());
		
			
		 @Before
		 public void initMocks() throws Exception {
			 nbsSecurityObj=Mockito.mock(NBSSecurityObj.class);
			 propertyUtil = Mockito.mock(PropertyUtil.class);
			 userProfile= Mockito.mock(UserProfile.class);
			 messageLogDao=Mockito.mock(MessageLogDAOImpl.class);
			 PHCDao=Mockito.mock(PublicHealthCaseDAOImpl.class);
			 user= Mockito.mock(User.class);
			 Whitebox.setInternalState(TaskListProxyDAOImpl.class,"propertyUtil", propertyUtil);
			 Whitebox.setInternalState(MessageLogDAOImpl.class,"propUtil", propertyUtil);
			 
			 logger = Mockito.mock(LogUtils.class);
			 Whitebox.setInternalState(TaskListProxyDAOImpl.class, "logger", logger);
 
		 }
		 
		 
		 @SuppressWarnings({"rawtypes", "unchecked" })
		 @Test
		 public void getTaskListItems_test() throws Exception {
			 taskListItemVO=Mockito.mock(TaskListItemVO.class);
			 PowerMockito.mockStatic(PropertyUtil.class);
			 Mockito.when(PropertyUtil.getInstance()).thenReturn(propertyUtil);
			 PowerMockito.whenNew(TaskListProxyDAOImpl.class).withNoArguments().thenReturn(taskListProxyDAOImpl);
			 PowerMockito.whenNew(MessageLogDAOImpl.class).withNoArguments().thenReturn(messageLogDao);
			 PowerMockito.whenNew(PublicHealthCaseDAOImpl.class).withNoArguments().thenReturn(PHCDao);
			 Mockito.doReturn(new ArrayList()).when(taskListProxyDAOImpl).getCustomQueues(any(NBSSecurityObj.class));
			 Mockito.when(propertyUtil.getObservationsNeedingReviewSecurity()).thenReturn("VIEW");
			 Mockito.when(propertyUtil.getMyProgramAreaSecurity()).thenReturn("VIEW");
			 Mockito.when(nbsSecurityObj.getPermission(any(String.class),any(String.class))).thenReturn(true);
			 Mockito.when(nbsSecurityObj.getDataAccessWhereClause(any(String.class), any(String.class), any(String.class))).thenReturn("WhereClause");
			 Mockito.doReturn(new ArrayList()).when(taskListProxyDAOImpl).callStoredProcedureMethod(any(String.class),any(ArrayList.class),any(ArrayList.class));
			 Mockito.doReturn(new Integer(this.expectedValue)).when(taskListProxyDAOImpl).getCountbySP(any(String.class));
			 when(taskListProxyDAOImpl.getCountbySP(any(String.class))).thenReturn(new Integer(this.expectedValue));
			 Mockito.doNothing().when(taskListItemVO).setTaskListItemName(any(String.class));
			 Mockito.doNothing().when(taskListItemVO).setTaskListItemCount(any(Integer.class));
			 Mockito.when(nbsSecurityObj.getPermission(any(String.class), any(String.class),any(String.class), any(String.class))).thenReturn(true);
			 Mockito.when(nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITY,ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION)).thenReturn(this.labReportAssignment);
			 Mockito.when(nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ASSIGNSECURITY,ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION)).thenReturn(this.morbReportAssignment);
			 Mockito.when(nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT, NBSOperationLookup.ASSIGNSECURITY,ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION)).thenReturn(this.docAssignment);
			 Mockito.when(nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,"VIEW")).thenReturn(this.labReportReview);
			 Mockito.when(nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,"VIEW")).thenReturn(this.morbReportReview);
			 Mockito.when(nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT, "VIEW")).thenReturn(this.documentReview);
			 Mockito.when(propertyUtil.getRejectedNotificationDaysLimit()).thenReturn("30"); 
			 Mockito.when(nbsSecurityObj.getTheUserProfile()).thenReturn(userProfile);
			 Mockito.when(userProfile.getTheUser()).thenReturn(user);
			 Mockito.when(user.getProviderUid()).thenReturn(1234L);
			 Mockito.doReturn(new Integer(5)).when(messageLogDao).getCount(any(MessageLogDT.class),any(NBSSecurityObj.class));
			 when(messageLogDao.getCount(any(MessageLogDT.class),any(NBSSecurityObj.class))).thenReturn(5);
			 when(PHCDao.getPublicHealthCasesBySupervisorCount(any(NBSSecurityObj.class))).thenReturn(5);
		     Object[] oParams=new Object[] {nbsSecurityObj};
	         ArrayList<Object> arr= Whitebox.invokeMethod(taskListProxyDAOImpl, "getTaskListItems", oParams);
	         System.out.println("Method:getTaskListItems, Iteration:"+this.iteration+", RESULT::::PASSED"); 

		 }
		
		
		
		
		
		 @SuppressWarnings("rawtypes")
		  @Parameterized.Parameters
		   public static Collection input() {
				return Arrays.asList(new Object[][] {
					{true,true,true,true,true,true,3, 1 },
					{true,true,false,true,true,false,4, 2 },
					{false,true,true,false,true,true, 5, 3 },
					{true,false,true,true,false,true, 6, 4},
					{false,false,true,false,false,true, 7, 5 },
				});
					
				}

		}
		 
}
	
