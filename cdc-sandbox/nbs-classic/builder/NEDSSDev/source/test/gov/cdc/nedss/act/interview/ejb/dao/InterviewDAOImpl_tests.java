package test.gov.cdc.nedss.act.interview.ejb.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.interview.dt.InterviewDT;
import gov.cdc.nedss.act.interview.ejb.dao.InterviewDAOImpl;
import gov.cdc.nedss.entity.entityid.dao.EntityIdDAOImpl;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;





import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;



/*
 * Mover to powermockito to being able to mock static elements
 * Powermockito 1.6.6 gives this type of error: No methods matching the name(s) prepareStatement were found in the class hierarchy of class java.lang.Object.
 * 
 *
 * */



@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil", "gov.cdc.nedss.act.interview.ejb.dao.InterviewDAOImpl"})
@RunWith(Enclosed.class)
@PrepareForTest({ResultSetUtils.class,LogUtils.class, InterviewDAOImpl.class, Connection.class,PreparedStatement.class})
@PowerMockIgnore("javax.management.*")
public class InterviewDAOImpl_tests {

	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil", "gov.cdc.nedss.act.interview.ejb.dao.InterviewDAOImpl"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({ResultSetUtils.class,LogUtils.class, InterviewDAOImpl.class, Connection.class,PreparedStatement.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SelectInterview_test {

	/*
	 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
	 * */

	
	
	@org.mockito.Mock
	PropertyUtil propertyUtilMocked;
	@org.mockito.Mock
	ExportReceivingFacilityDT exportReceivingFacMock;
	
	
	//For the database connection under the method we are testing
	@org.mockito.Mock
     Connection c;
	@org.mockito.Mock
     PreparedStatement stmt;
	@org.mockito.Mock
	ResultSet resultSet;
	
    @org.mockito.Mock
    LogUtils loggerMock;
    @org.mockito.Mock
    ResultSetUtils resultSetUtils;

	
	@InjectMocks
	@Spy //@Spy is needed to mock methods inside the class that we are testing.
	InterviewDAOImpl interviewDAOImpl=  Mockito.spy(InterviewDAOImpl.class);
	
	
		Long interviewUID;
		int numberOfAssociated;
		int numberOfAssociatedExpected;//This parameter is only used by test case: selectNumberOfContactsAssociatedToInterview_test
		boolean expectedValue;//This parameter is only used by test case: selectInterview_test
		int iteration;
		
		
		 public SelectInterview_test(Long interviewUID, int numberOfAssociated, int numberOfAssociatedExpected, boolean expectedValue, int interation){
			 super();
			 this.interviewUID = interviewUID;
			 this.numberOfAssociated = numberOfAssociated;
			 this.expectedValue = expectedValue;//For test case: selectInterview_test
			 this.numberOfAssociatedExpected = numberOfAssociatedExpected;//For test case: selectNumberOfContactsAssociatedToInterview_test
			 this.iteration = interation;

		 }
		 
		   @Parameterized.Parameters
		   public static Collection input() {
			   
			   int iter = 1;
			  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
			   //The areas are the lines where the new method escapeCharacterSequence method is called
		      return Arrays.asList(new Object[][]{
		    		 
		    		  //set to PASS
		    		  {111111111L, 1, 1,true, iter++},
		    		  {222222222L, 10, 10,true, iter++},
		    		  {333333333L,0, 0,false, iter++},
		    		  {111111111L,-1, -1,false, iter++},
		    		  {222222222L,2, 2,true,iter++},
		    		  {333333333L,10, 10,true, iter++}/*,
		    		  
		    		  //set to FAIL
		    		  {111111111L, 10,1,false,iter++},
		    		  {222222222L, 0,10,true,iter++},
		    		  {333333333L,-1, 0,true,iter++},
		    		  {111111111L, 0,-1,true,iter++},
		    		  {222222222L, -2,2,true,iter++},
		    		  {333333333L,-10, 10,true,iter++}*/
		    		  
		    		  
		    		  });
		   }
		 
		   
		   
		 
	
		 @Before
		 public void initMocks() throws Exception {

			 
			// Mocking the DB object
			 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
 
		     // Mocking the DB object
			 exportReceivingFacMock = Mockito.mock(ExportReceivingFacilityDT.class);
			 
			 loggerMock = Mockito.mock(LogUtils.class);
			 
			 stmt = Mockito.mock(PreparedStatement.class) ;
			 c = Mockito.mock(Connection.class) ;
			 resultSet = Mockito.mock(ResultSet.class) ;
		
			 
			 
		

		 }
		 
		 
			@Test
				public void selectInterview_test() throws Exception{

				System.out.println("******************* Starting test case named: selectInterview_test *******************");

				Whitebox.setInternalState(InterviewDAOImpl.class, "logger", loggerMock);

				Mockito.when(stmt.executeQuery()).thenReturn(resultSet);  
				when(c.prepareStatement(any(String.class))).thenReturn(stmt);
				Mockito.doReturn(c).when(interviewDAOImpl).getConnection();
				Mockito.doReturn(numberOfAssociated).when(interviewDAOImpl).selectNumberOfContactsAssociatedToInterview(interviewUID);
					 
				
				InterviewDT actualResultDT = interviewDAOImpl.selectInterview(interviewUID);
				boolean actualResult = actualResultDT.isAssociated();
				
				System.out.println("Iteration: #"+iteration+"\nNumber Of Contacts Associated To Interview sent: "+numberOfAssociated+"\nExpected value: "+expectedValue+"\nActual value: "+actualResult);
				Assert.assertEquals(actualResult,expectedValue);
				System.out.println("PASSED");
				
				System.out.println("******************* End test case named: selectInterview_test *******************");
				}

			
			
				
				
			
			
	/*We should have these 3 sets on each test*/
	//Arrange
	
	//Act
	
	//Assert
}




@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil", "gov.cdc.nedss.act.interview.ejb.dao.InterviewDAOImpl"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({ResultSetUtils.class,LogUtils.class, InterviewDAOImpl.class, Connection.class,PreparedStatement.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SelectNumberOfContactsAssociatedToInterview_test {

	/*
	 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
	 * */

	
	
	@org.mockito.Mock
	PropertyUtil propertyUtilMocked;
	@org.mockito.Mock
	ExportReceivingFacilityDT exportReceivingFacMock;
	
	
	//For the database connection under the method we are testing
	@org.mockito.Mock
     Connection c;
	@org.mockito.Mock
     PreparedStatement stmt;
	//@org.mockito.Mock
   //  DataSource ds;
	@org.mockito.Mock
	ResultSet resultSet;
	
//	InitialContext initCntx = null;
	
    @org.mockito.Mock
    LogUtils loggerMock;
    @org.mockito.Mock
    ResultSetUtils resultSetUtils;

	
	@InjectMocks
	@Spy //@Spy is needed to mock methods inside the class that we are testing.
	InterviewDAOImpl interviewDAOImpl=  PowerMockito.spy(new InterviewDAOImpl());
	
		Long interviewUID;
		int numberOfAssociated;
		int numberOfAssociatedExpected;//This parameter is only used by test case: selectNumberOfContactsAssociatedToInterview_test
		boolean expectedValue;//This parameter is only used by test case: selectInterview_test
		int iteration;
		
		
		 public SelectNumberOfContactsAssociatedToInterview_test(Long interviewUID, int numberOfAssociated, int numberOfAssociatedExpected, boolean expectedValue, int interation){
			 super();
			 this.interviewUID = interviewUID;
			 this.numberOfAssociated = numberOfAssociated;
			 this.expectedValue = expectedValue;//For test case: selectInterview_test
			 this.numberOfAssociatedExpected = numberOfAssociatedExpected;//For test case: selectNumberOfContactsAssociatedToInterview_test
			 this.iteration = interation;

		 }
		 
		   @Parameterized.Parameters
		   public static Collection input() {
			   
			   int iter = 1;
			  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
			   //The areas are the lines where the new method escapeCharacterSequence method is called
		      return Arrays.asList(new Object[][]{
		    		 
		    		  //set to PASS
		    		  {111111111L, 1, 1,true, iter++},
		    		  {222222222L, 10, 10,true, iter++},
		    		  {333333333L,0, 0,false, iter++},
		    		  {111111111L,-1, -1,false, iter++},
		    		  {222222222L,2, 2,true,iter++},
		    		  {333333333L,10, 10,true, iter++}/*,
		    		  
		    		  //set to FAIL
		    		  {111111111L, 10,1,false,iter++},
		    		  {222222222L, 0,10,true,iter++},
		    		  {333333333L,-1, 0,true,iter++},
		    		  {111111111L, 0,-1,true,iter++},
		    		  {222222222L, -2,2,true,iter++},
		    		  {333333333L,-10, 10,true,iter++}*/
		    		  
		    		  
		    		  });
		   }
		 
		   
		   
		 
	
		 @Before
		 public void initMocks() throws Exception {

			 
			// Mocking the DB object
			 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
 
		     // Mocking the DB object
			 exportReceivingFacMock = Mockito.mock(ExportReceivingFacilityDT.class);
			 
			 loggerMock = Mockito.mock(LogUtils.class);

			 c = Mockito.mock(Connection.class);
			 stmt = Mockito.mock(PreparedStatement.class);
			 resultSet = Mockito.mock(ResultSet.class);
			
				
		 }
		 
		 
			


		 @Test
		public void selectNumberOfContactsAssociatedToInterview_test() throws Exception{
	
		
		System.out.println("******************* Starting test case named: selectNumberOfContactsAssociatedToInterview_test *******************");
	
		Whitebox.setInternalState(InterviewDAOImpl.class, "logger", loggerMock);
	
		Mockito.when(stmt.executeQuery()).thenReturn(resultSet);  
		when(c.prepareStatement(any(String.class))).thenReturn(stmt);
		Mockito.doReturn(c).when(interviewDAOImpl).getConnection();
		Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
	
	
		Mockito.when(resultSet.getString(1)).thenReturn(numberOfAssociated+"");
		
	//	Mockito.doReturn(valueToReturn).when(interviewDAOImpl).selectNumberOfContactsAssociatedToInterview(interviewUID);
			 
	
		int actualResult = interviewDAOImpl.selectNumberOfContactsAssociatedToInterview(interviewUID);
		
		System.out.println("Iteration: #"+iteration+"\nValue returned by query: "+numberOfAssociated+"\nExpected value: "+numberOfAssociatedExpected+"\nActual value: "+actualResult);
		Assert.assertEquals(actualResult,numberOfAssociatedExpected);
		System.out.println("PASSED");
		
		System.out.println("******************* End test case named: selectNumberOfContactsAssociatedToInterview_test *******************");
		}

				
				
			
			
	/*We should have these 3 sets on each test*/
	//Arrange
	
	//Act
	
	//Assert
}






}
	
