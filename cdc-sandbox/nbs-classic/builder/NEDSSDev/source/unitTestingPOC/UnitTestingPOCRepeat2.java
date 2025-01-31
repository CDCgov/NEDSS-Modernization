package unitTestingPOC;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.trigercodeejb.dao.TriggerCodeDAOImpl;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;



/*
 * Move to powermockito to being able to mock static elements
 * Powermockito 1.6.6 gives this type of error: No methods matching the name(s) prepareStatement were found in the class hierarchy of class java.lang.Object.
 * 
 *
 * */




@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil", "gov.cdc.nedss.systemservice.ejb.trigercodeejb.dao.TriggerCodeDAOImpl"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({TriggerCodeDAOImpl.class, Connection.class,PreparedStatement.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
public class UnitTestingPOCRepeat2 {
	 private long myNumber;
	 private int iteration;
	 
	 public UnitTestingPOCRepeat2(long number, int it){
		 super();
		 this.myNumber = number;
		 this.iteration = it;
		 
		 
	 }
	
 
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
	
 
	
	@InjectMocks
	@Spy //@Spy is needed to mock methods inside the class that we are testing.
	TriggerCodeDAOImpl triggerCodeDAOImpl=  Mockito.spy(TriggerCodeDAOImpl.class);
	
		 @Before
		 public void initMocks() throws Exception {


			 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
			 
		     // Mocking the DB object
			 exportReceivingFacMock = Mockito.mock(ExportReceivingFacilityDT.class);
		    		
		 }
		 
		 
		 
		   @Parameterized.Parameters
		   public static Collection input() {
			   
			   ArrayList<Long> myArray = new ArrayList<Long>();
			   
			 /*  myArray.add(new Random().nextLong());
			   myArray.add(new Random().nextLong());
			   myArray.add(new Random().nextLong());
			   myArray.add(new Random().nextLong());*/
				
		      return Arrays.asList(new Object[][]{{new Random().nextLong(),1},
		    		  {new Random().nextLong(),2},
		    		  {new Random().nextLong(),3},
		    		  {new Random().nextLong(),4},
		    		  {new Random().nextLong(),5},
		    		  {new Random().nextLong(),6},
		    		  {new Random().nextLong(),7},
		    		  {new Random().nextLong(),8},
		    		  {new Random().nextLong(),9},
		    		  {new Random().nextLong(),10},
		    		  {new Random().nextLong(),11},
		    		  {new Random().nextLong(),12}
		    		  
		    		  });
		   }
		   
		   
		   
			@Test
				public void repeatingTestCase() throws Exception{			
				System.out.println("******************* Starting test case named: repeatingTestCase (ITERATION "+iteration+")*******************");

		/************MOCKING: START**********/
				Mockito.when(exportReceivingFacMock.getReceivingSystemOid()).thenReturn("PHDC2");  //This is the way we mock methods from a spy class
				Mockito.when(exportReceivingFacMock.getReceivingSystemOwnerOid()).thenReturn("PHDC2");   //This is the way we mock methods from a spy class
				

				
				when(c.prepareStatement(any(String.class))).thenReturn(stmt);

				Mockito.when(stmt.executeQuery()).thenReturn(resultSet);  
				Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
				Mockito.when(resultSet.getLong(1)).thenReturn(myNumber);

				// when(ds.getConnection()).thenReturn(c);//Normally, people use ds.getConnection to mock this call. In our case, we are using getconnection from triggerCodeDAOImpl, regardless it is extending from ds, we need to mock it from this level.
					
				Mockito.doReturn(c).when(triggerCodeDAOImpl).getConnection();
					        
		/************MOCKING: END**********/
		
		/************TEST CASE: START**********/
				Long actualResult = triggerCodeDAOImpl.getReceivingSystem(exportReceivingFacMock);
		/************TEST CASE: END**********/	
				
		/************VALIDATION: START**********/
				Long expectedResult = myNumber;
				System.out.println("Expected result:" +expectedResult);
				System.out.println("Actual result:" +actualResult);
				
				Assert.assertEquals(actualResult,actualResult);
		/************VALIDATION: END**********/
				
				
				
				
				System.out.println("******************* End test case named: repeatingTestCase *******************");
				}
			
			
			
		
		
		
		
		
	/*We should have these 3 sets on each test*/
	//Arrange
	
	//Act
	
	//Assert
	
	
	
}
	
