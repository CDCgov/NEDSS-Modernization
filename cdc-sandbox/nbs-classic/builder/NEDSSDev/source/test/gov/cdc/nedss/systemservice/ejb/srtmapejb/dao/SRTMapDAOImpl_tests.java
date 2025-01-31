package test.gov.cdc.nedss.systemservice.ejb.srtmapejb.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeMap;

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


@SuppressStaticInitializationFor ({ "gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(Enclosed.class)
@PrepareForTest({SRTMapDAOImpl.class, PropertyUtil.class, NEDSSConstants.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public class SRTMapDAOImpl_tests {


@SuppressStaticInitializationFor ({ "gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({SRTMapDAOImpl.class, PropertyUtil.class, NEDSSConstants.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class GetOrganismListSNM_test {

	
	 private ArrayList<Object> listToReturnByQuery;
	 private ArrayList<Object> cacheCodes;
	 private int iteration;
	 private int expectedSize;
	 
	 
	 
	 
	 public GetOrganismListSNM_test(ArrayList<Object> listToReturn, ArrayList<Object> cacheCodes, int it, int expectedSize){
		 super();
		 this.listToReturnByQuery = listToReturn;
		 this.cacheCodes = cacheCodes;
		 this.iteration = it;
		 this.expectedSize = expectedSize;
	 }
	 
	 
	 

	 
	   @Parameterized.Parameters
	   public static Collection input() {
		   
		   ArrayList<Long> myArray = new ArrayList<Long>();
		   
		   
		   ArrayList<Object> array1 = new ArrayList<Object>();
		   
		   DropDownCodeDT dt1 = new DropDownCodeDT();
		   dt1.setKey("key1");
		   dt1.setValue("value1");
		   
		   DropDownCodeDT dt2 = new DropDownCodeDT();
		   dt2.setKey("key2");
		   dt2.setValue("value2");
		   
		   array1.add(dt1);
		   array1.add(dt2);
		   
		   
		   ArrayList<Object> array2 = new ArrayList<Object>();
		   DropDownCodeDT dt3 = new DropDownCodeDT();
		   dt3.setKey("key1");
		   dt3.setValue("value1");
		   
		   DropDownCodeDT dt4 = new DropDownCodeDT();
		   dt4.setKey("key2");
		   dt4.setValue("value2");
		   
		   DropDownCodeDT dt5 = new DropDownCodeDT();
		   dt5.setKey("key3");
		   dt5.setValue("value3");
		   
		   
		   array2.add(dt3);
		   array2.add(dt4);
		   array2.add(dt5);
		   
		   
		   int it =1;
		   
		   
	      return Arrays.asList(new Object[][]{
	    		  
	    //The test will PASS with the following values:
	    		  
	    		  //Without Cache values
	    		  {new ArrayList<Object>(),null,it++, 0},//empty list, no cache
	    		  {array1,null, it++, 2},//list with 2 objects, no cache
	    		  {array2,null, it++, 3},//list with 2 object, no cache
	    		  
	    		  
	    		  //With Cache values
	    		  {new ArrayList<Object>(),array1,it++, 2},//empty list, cache with 2 objects
	    		  {array1,array1, it++, 2},//list with 2 objects, cache with 2 objects
	    		  {array2,array1, it++, 2},//list with 3 objects, cache with 2 objects
	    		  {new ArrayList<Object>(),array2,it++, 3},//empty list, cache with 3 objects
	    		  {array1,array2, it++, 3},//list with 2 objects, cache with 3 objects
	    		  {array2,array2, it++, 3},//list with 3 objects, cache with 3 objects
	    		  {array2,new ArrayList<Object>(), it++, 0},//list with 3 objects, empty cache	
	    		 
	      //The test will FAIL with the following values:
	    		 /*
	    		  //Without Cache values
	    		  {new ArrayList<Object>(),null,it++, 1},//empty list, no cache
	    		  {array1,null, it++, 1},//list with 2 objects, no cache
	    		  {array2,null, it++, 0},//list with 2 object, no cache
	    		  
	    		  
	    		  //With Cache values
	    		  {new ArrayList<Object>(),array1,it++, 0},//empty list, cache with 2 objects
	    		  {array1,array1, it++, 1},//list with 2 objects, cache with 2 objects
	    		  {array2,array1, it++, 1},//list with 3 objects, cache with 2 objects
	    		  {new ArrayList<Object>(),array2,it++, 0},//empty list, cache with 3 objects
	    		  {array1,array2, it++, 10},//list with 2 objects, cache with 3 objects
	    		  {array2,array2, it++, 20},//list with 3 objects, cache with 3 objects
	    		  {array2,new ArrayList<Object>(), it++, 1},//list with 3 objects, empty cache	
	    		 
	    		  */
	    		  
	      
	      
	      });
	   }
	   
	@Mock
	PropertyUtil propertyUtil;

	@InjectMocks
	@Spy //@Spy is needed to mock methods inside the class that we are testing.
	SRTMapDAOImpl srtMapDAOImpl=  Mockito.spy(SRTMapDAOImpl.class);
	
	
		 @Before
		 public void initMocks() throws Exception {
			/*
			 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
			 * */

			 MockitoAnnotations.initMocks(this);
			 propertyUtil = PowerMockito.mock(PropertyUtil.class);
		 }
		 
		 
			@Test
				public void getOrganismListSNM_test() throws Exception{

				System.out.println("******************* Starting test case named: getOrganismListSNM_test *******************");

				//Mockito.when(propertyUtilMocked.getDatabaseServerType()).thenReturn("Oracle");
				//when(propertyUtil.getDatabaseServerType()).thenReturn("sql");   
				 Whitebox.setInternalState(SRTMapDAOImpl.class, "propertyUtil", propertyUtil);
				 Whitebox.setInternalState(NEDSSConstants.class, "SYSTEM_REFERENCE_TABLE", "srte");
				 Whitebox.setInternalState(SRTMapDAOImpl.class, "cacheSnomedCodes", cacheCodes);
					
				 Mockito.doReturn(listToReturnByQuery).when(srtMapDAOImpl).preparedStmtMethod(any(RootDTInterface.class),any(ArrayList.class),any(String.class),any(String.class));
			    
			    //whitebox used for private or inner classes, so in this case, we could have also just called the method.
			    TreeMap<Object,Object> actualResult = Whitebox.invokeMethod(srtMapDAOImpl, "getOrganismListSNM");
				//It can also be called this way:
				// TreeMap<Object,Object> actualResult =  srtMapDAOImpl.getOrganismListSNM();
				
				 System.out.println("ITERATION: #"+iteration+ " \nExpected result size:" +expectedSize);
				System.out.println("Actual result size:" +actualResult.size());
				
				
				Assert.assertEquals(actualResult.size(),expectedSize);
				System.out.println("PASSED");

				System.out.println("******************* End test case named: getOrganismListSNM_test *******************");
				}
		
	/*We should have these 3 sets on each test*/
	//Arrange
	
	//Act
	
	//Assert
	
	
	
}
}
	
