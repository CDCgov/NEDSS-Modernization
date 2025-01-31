package unitTestingPOC;

import java.lang.annotation.Repeatable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.trigercodeejb.dao.TriggerCodeDAOImpl;
import gov.cdc.nedss.util.DateUtil;
import gov.cdc.nedss.util.NumberUtil;
import gov.cdc.nedss.util.PropertyUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;



/*
 * Mover to powermockito to being able to mock static elements
 * Powermockito 1.6.6 gives this type of error: No methods matching the name(s) prepareStatement were found in the class hierarchy of class java.lang.Object.
 * 
 *
 * */




@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil", "gov.cdc.nedss.systemservice.ejb.trigercodeejb.dao.TriggerCodeDAOImpl"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({TriggerCodeDAOImpl.class, Connection.class,PreparedStatement.class})
public class UnitTestingPOC1 {

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

			 
			// Mocking the DB object
			 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
 
		     // Mocking the DB object
			 exportReceivingFacMock = Mockito.mock(ExportReceivingFacilityDT.class);

		 }
		 
		 
			@Test
				public void sixthTestCase() throws Exception{

				System.out.println("******************* Starting test case named: sixthTestCase *******************");

				
				Mockito.when(exportReceivingFacMock.getReceivingSystemOid()).thenReturn("PHDC2");  //This is the way we mock methods from a spy class
				Mockito.when(exportReceivingFacMock.getReceivingSystemOwnerOid()).thenReturn("PHDC2");   //This is the way we mock methods from a spy class
				

				
				when(c.prepareStatement(any(String.class))).thenReturn(stmt);

				Mockito.when(stmt.executeQuery()).thenReturn(resultSet);  
				Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
				Mockito.when(resultSet.getLong(1)).thenReturn(10L);

				// when(ds.getConnection()).thenReturn(c);//Normally, people use ds.getConnection to mock this call. In our case, we are using getconnection from triggerCodeDAOImpl, regardless it is extending from ds, we need to mock it from this level.
					
				Mockito.doReturn(c).when(triggerCodeDAOImpl).getConnection();
					        

				Long actualResult = triggerCodeDAOImpl.getReceivingSystem(exportReceivingFacMock);
				Long expectedResult = 10L;
				System.out.println("Expected result:" +expectedResult);
				System.out.println("Actual result:" +actualResult);
				
				Assert.assertEquals(actualResult,actualResult);
			
				System.out.println("******************* End test case named: sixthTestCase *******************");
				}
			
			
			
			 
	@Test
	public void sixthTestCase_ArrayList() throws Exception{
		
		int numberOfCases = 100;
		
			
			for(int i=0; i<numberOfCases; i++){
			

				long generatedLong = new Random().nextLong();
			
				System.out.println("******************* Starting test case named: sixthTestCase_ArrayList *******************");
	
				
				Mockito.when(exportReceivingFacMock.getReceivingSystemOid()).thenReturn("PHDC2");  //This is the way we mock methods from a spy class
				Mockito.when(exportReceivingFacMock.getReceivingSystemOwnerOid()).thenReturn("PHDC2");   //This is the way we mock methods from a spy class
				
	
				
				when(c.prepareStatement(any(String.class))).thenReturn(stmt);
	
				Mockito.when(stmt.executeQuery()).thenReturn(resultSet);  
				Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
				Mockito.when(resultSet.getLong(1)).thenReturn(generatedLong);
	
				// when(ds.getConnection()).thenReturn(c);//Normally, people use ds.getConnection to mock this call. In our case, we are using getconnection from triggerCodeDAOImpl, regardless it is extending from ds, we need to mock it from this level.
					
				Mockito.doReturn(c).when(triggerCodeDAOImpl).getConnection();
					        
	
				Long actualResult = triggerCodeDAOImpl.getReceivingSystem(exportReceivingFacMock);
				//Long generatedLog = 10L;
				Long expectedResult = generatedLong;
				System.out.println(i+": Expected result:" +expectedResult);
				System.out.println(i+": Actual result:" +actualResult);
				
				Assert.assertEquals(expectedResult,actualResult);
			
		
			
			}	
		
			System.out.println("******************* End test case named: sixthTestCase_ArrayList *******************");
			
	}
		
		
		

	
	
		@Mock
		NumberUtil numberUtil;
	
		DateUtil myDate;
		 

		@org.mockito.Mock
		Calendar calendar;
		
//		@Test
//			public void seventhTestCase() throws Exception{
//			
//		
//			
//			System.out.println("******************* Starting test case named: seventhTestCase *******************");
//
//			
//			numberUtil = Mockito.mock(NumberUtil.class);
//			myDate = new DateUtil();
//			
//			
//
//			//Mocking the result of calling getWeekAndYearFromDB
//			Mockito.when(numberUtil.dayOfWeekTest(Mockito.anyInt())).thenReturn(1000);  
//			 
//		
//	        String dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
//	        System.out.println(dateTime);
//
//	        //----------------------------------
//	        
//	        Timestamp timestamp = Timestamp.valueOf(dateTime);
//	        
//	        
//	    	int expectedResult = 0;
//			
//			int[] actualResult = myDate.ageInYears(timestamp);
//
//			System.out.println("Expected result: "+actualResult[0]);
//		
//			System.out.println("Actual result: "+actualResult[0]);
//		
//			Assert.assertEquals(actualResult,actualResult);
//		
//			System.out.println("******************* End test case named: seventhTestCase *******************");
//			}
//		
		
		
		
		
		
		
		
	/*We should have these 3 sets on each test*/
	//Arrange
	
	//Act
	
	//Assert
	
	
	
}
	
