package test.gov.cdc.nedss.proxy.ejb.tasklistproxyejb.bean;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.bean.TaskListProxyEJB;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao.TaskListProxyDAOImpl;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class TaskListProxyEJB_tests {
	
	/**
	 * DeleteCustomQueue_test: this method was created to validate the method runs successfully simulating the custom queue is
	 * always deleted as the internal method always returns 0 and public and owner are always set to true. It should have
	 * been created with other test cases.
	 * @author Fatima.Lopezcalzado
	 *
	 */
		@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.ejb.tasklistproxyejb.bean.TaskListProxyEJB","gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao.TaskListProxyDAOImpl"})
		@RunWith(PowerMockRunner.class)
		@PrepareForTest({TaskListProxyEJB.class})
		@PowerMockRunnerDelegate(Parameterized.class)
		@PowerMockIgnore("javax.management.*")
	    public static class DeleteCustomQueue_test{
			private String queueName;
			private String expectedValue;
			private int iteration;
	
		
			 @Mock
			 TaskListProxyDAOImpl taskListProxyDAOImpl;
			 
			@Mock
			 NBSSecurityObj nbsSecurityObjMock;
			
			@Mock
			UserProfile userProfile;
			
			@Mock
			User user;
			
			 @InjectMocks
			 TaskListProxyEJB tProxyEjbMock;
			 
			 
			public DeleteCustomQueue_test(String queueName, String expectedValue, int it){
				this.queueName = queueName;
				this.expectedValue = expectedValue;
				this.iteration = it;
	     	}
			
			@Before
			 public void initMocks() throws Exception {
				nbsSecurityObjMock=Mockito.mock(NBSSecurityObj.class);
				userProfile=Mockito.mock(UserProfile.class);
				user=Mockito.mock(User.class);	
				taskListProxyDAOImpl=Mockito.mock(TaskListProxyDAOImpl.class);
			 }
		 
			
			 @Test
			  public void deleteCustomQueue_test() throws Exception {
				 String userIdString="10000000";
				 PowerMockito.whenNew(TaskListProxyDAOImpl.class).withNoArguments().thenReturn(taskListProxyDAOImpl);
				 //PowerMockito.whenNew(NBSSecurityObj.class).withNoArguments().thenReturn(nbsSecurityObjMock);
				 when(nbsSecurityObjMock.getTheUserProfile()).thenReturn(userProfile);
				 when(userProfile.getTheUser()).thenReturn(user);
				 when(user.getEntryID()).thenReturn(userIdString);
		  	     when(taskListProxyDAOImpl.isCurrentUserOwnerOfCustomPrivateQueue(any(String.class), any(String.class))).thenReturn(true);
				 when(nbsSecurityObjMock.getPermission(any(String.class), any(String.class))).thenReturn(true);
				 when(taskListProxyDAOImpl.deleteCustomQueue(any(String.class),any(String.class),any(NBSSecurityObj.class))).thenReturn("0");
				 Object[] oParams = new Object[] {this.queueName,nbsSecurityObjMock};
				 String actualResult = Whitebox.invokeMethod(new TaskListProxyEJB(), "deleteCustomQueue", oParams);
				 String expectedResult=this.expectedValue;
				 Assert.assertEquals(expectedResult, actualResult);
				 System.out.println("Class Name: TaskListProxyEJB.java, Method:deleteCustomQueue, Iteration: "+iteration+". Value: "+this.queueName+" Expected value: "+expectedResult+"; Actual value: "+actualResult+"; RESULT:::::PASSED");;
			 }
			
			 
			    @SuppressWarnings("rawtypes")
				@Parameterized.Parameters
				   public static Collection input() {
				      return Arrays.asList(new Object[][]{
				    		  {"QueueName1","0",1},
				    		  {"QueueName2","0",2},
				    		  {"QueueName3","0",3},
				    		  {"QueueName4","0",4},
				    		  {"QueueName5","0",5},
				    		  {"QueueName6","0",6},
				    		  {"QueueName7","0",7},
				    		  {"QueueName8","0",8},
				    		  {"QueueName9","0",9},
				    		  {"QueueName10","0",10}
			    		  });
				   }
		 
	    
		}
		
		/**
		 * SaveCustomQueue_test: this test case returns 0 if the custom queue has been able to get inserted.
		 * The way this test case was created, it is always simulating it can create the custom queue, so it will always return 0.
		 * Other test cases should have been created.
		 * @author Fatima.Lopezcalzado
		 *
		 */
		@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.ejb.tasklistproxyejb.bean.TaskListProxyEJB","gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao.TaskListProxyDAOImpl"})
		@RunWith(PowerMockRunner.class)
		@PrepareForTest({TaskListProxyEJB.class})
		@PowerMockRunnerDelegate(Parameterized.class)
		@PowerMockIgnore("javax.management.*")
		public static class SaveCustomQueue_test{
			private String queueName;
			private String sourceTable;
			private String queryString;
			private String descriptionQueue;
			private String listOfUsers;
			private String searchCriteriaDesc;
			private String searchCriteriaCd;
			private String expectedValue;
			private int iteration;
			
			
			 @Mock
			 TaskListProxyDAOImpl taskListProxyDAOImpl;
			 
			@Mock
			NBSSecurityObj nbsSecurityObjMock;
			
			@Mock
			UserProfile userProfile;
			
			@Mock
			User user;
			
			@InjectMocks
			TaskListProxyEJB tProxyEjbMock;
			
			
			public SaveCustomQueue_test(String queueName, String sourceTable, String queryString,
					String descriptionQueue, String listOfUsers, String searchCriteriaDesc, String searchCriteriaCd,
					String expectedValue, int iteration) {
				this.queueName = queueName;
				this.sourceTable = sourceTable;
				this.queryString = queryString;
				this.descriptionQueue = descriptionQueue;
				this.listOfUsers = listOfUsers;
				this.searchCriteriaDesc = searchCriteriaDesc;
				this.searchCriteriaCd = searchCriteriaCd;
				this.expectedValue = expectedValue;
				this.iteration = iteration;
			}
			
			
			@Before
			 public void initMocks() throws Exception {
				nbsSecurityObjMock=Mockito.mock(NBSSecurityObj.class);
				userProfile=Mockito.mock(UserProfile.class);
				user=Mockito.mock(User.class);	
				taskListProxyDAOImpl=Mockito.mock(TaskListProxyDAOImpl.class);

			 }
			
			@Test
			public void saveCustomQueue_test() throws Exception{
				String userIdString="10000000";
				PowerMockito.whenNew(TaskListProxyDAOImpl.class).withNoArguments().thenReturn(taskListProxyDAOImpl);
				PowerMockito.mockStatic(Long.class);
				when(nbsSecurityObjMock.getTheUserProfile()).thenReturn(userProfile);
				when(userProfile.getTheUser()).thenReturn(user);
				when(user.getEntryID()).thenReturn(userIdString);
				when(Long.parseLong(userIdString)).thenReturn(new Long(userIdString));
				when(taskListProxyDAOImpl.isExistingCustomQueueName(any(String.class), any(String.class))).thenReturn(false);
			    when(taskListProxyDAOImpl.insertCustomQueue(any(String.class),any(String.class),any(String.class),any(String.class),any(String.class),any(Long.class),any(String.class),any(String.class),any(NBSSecurityObj.class))).thenReturn("0");
			    Object[] oParams = new Object[] {this.queueName,this.sourceTable,this.queryString,this.descriptionQueue,this.listOfUsers,this.searchCriteriaDesc,this.searchCriteriaCd,nbsSecurityObjMock};
			    String actualResult = Whitebox.invokeMethod(new TaskListProxyEJB(), "saveCustomQueue", oParams);
			    String expectedResult=this.expectedValue;
				Assert.assertEquals(expectedResult, actualResult);
				System.out.println("Class Name: TaskListProxyEJB.java, Method Name: SaveCustomQueue, Iteration: "+iteration+". Value: "+this.queueName+" Expected value: "+expectedResult+"; Actual value: "+actualResult+"; RESULT:::::PASSED");
			}
			
			
			 @SuppressWarnings("rawtypes")
				@Parameterized.Parameters
				   public static Collection input() {
				      return Arrays.asList(new Object[][]{
				    		  {"QueueName1","I","sql","QueueName1","ALL","searchCriteriaDesc","searchCriteriaCd","0",1},
				    		  {"QueueName2","LR","sql","QueueName2","CURRENT","searchCriteriaDesc","searchCriteriaCd","0",2},
				    		  {"QueueName3","LR","sql","QueueName3","ALL","searchCriteriaDesc","searchCriteriaCd","0",3},
				    		  {"QueueName4","I","sql","QueueName4","CURRENT","searchCriteriaDesc","searchCriteriaCd","0",4},
				    		  {"QueueName5","I","sql","QueueName5","ALL","searchCriteriaDesc","searchCriteriaCd","0",5},
				    		  {"QueueName6","LR","sql","QueueName6","CURRENT","searchCriteriaDesc","searchCriteriaCd","0",6},
				    		  {"QueueName7","LR","sql","QueueName7","ALL","searchCriteriaDesc","searchCriteriaCd","0",7},
				    		  {"QueueName8","I","sql","QueueName8","CURRENT","searchCriteriaDesc","searchCriteriaCd","0",8},
				    		  {"QueueName9","I","sql","QueueName9","ALL","searchCriteriaDesc","searchCriteriaCd","0",9},
				    		  {"QueueName10","LR","sql","QueueName10","CURRENT","searchCriteriaDesc","searchCriteriaCd","0",10},
				    	  });
				   }
			
		}
		
		
		
		
	
}
	
