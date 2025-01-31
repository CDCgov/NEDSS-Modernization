package test.gov.cdc.nedss.webapp.nbs.logicsheet.helper;

import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMap;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

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


@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil", "gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues"})
@RunWith(Enclosed.class)
@PrepareForTest({PropertyUtil.class, ResultSetUtils.class,LogUtils.class, CachedDropDownValues.class, Connection.class,PreparedStatement.class, NEDSSConstants.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time

public class CachedDropDownValues_tests {


@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil", "gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PropertyUtil.class, ResultSetUtils.class,LogUtils.class, CachedDropDownValues.class, Connection.class,PreparedStatement.class, NEDSSConstants.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time

public static class GetOrganismListDescSNM_test {

	/*
	 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
	 * */

	
	
	@org.mockito.Mock
	ExportReceivingFacilityDT exportReceivingFacMock;
	
	@org.mockito.Mock
	SRTMap srtMap;
	
	@org.mockito.Mock
	PropertyUtil propertyUtil;

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
	NEDSSConstants nedssConstants;
    @org.mockito.Mock
    LogUtils loggerMock;
    @org.mockito.Mock
    ResultSetUtils resultSetUtils;

	
	@InjectMocks
	@Spy //@Spy is needed to mock methods inside the class that we are testing.
	CachedDropDownValues cachedDropdownValues=  Mockito.spy(CachedDropDownValues.class);
	
	String codeToFind;
	String code;
	 String desc;
	 String expectedDescription;
	 int iteration =1;
	 String codeCache;
	 String descCache;
	 
	 
	 
	
				
		 public GetOrganismListDescSNM_test(String codeToFind,String code, String desc, String codeCache, String descCache, String expectedDescription,int iteration){
			 super();
			 this.codeToFind=codeToFind;
			 this.code = code;//Simulates the code from DB
			 this.desc = desc;//Simulates the description from DB
			 this.codeCache = codeCache;//Simulates the code from Cache
			 this.descCache = descCache;//Simulates the description from Cache
			 this.expectedDescription = expectedDescription;
			 this.iteration = iteration;
			 

		 }
		 
		   @Parameterized.Parameters
		   public static Collection input() {
			   
			   int iter = 1;
			  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
			   //The areas are the lines where the new method escapeCharacterSequence method is called
		      return Arrays.asList(new Object[][]{
		    		 
		    		  //set to PASS
		    		  //Find a code, with empty cache-> should bring it from DB
		    		  {"123","123", "123descriptiooooooooooooo","","","123descriptiooooooooooooo", iter++},
		    		  {"123231","123231", "123231Description","","", "123231Description", iter++},
		    		  {"1","1", "", "","","", iter++},
		    		  {"99999999","99999999",  "99999999Desc desc","","","99999999Desc desc", iter++},
		    		  
		    		  
		    		  //Find a code, with the code existing in the cache-> should bring it from Cache
		    		  {"123Cache","123", "123desc","123Cache","123desc Cache","123desc Cache", iter++},
		    		  {"123231Cache", "123231","123231Desc", "123231Cache","123231desc Cache","123231desc Cache", iter++},
		    		  { "1Cache","1", "1desc ", "1Cache","1desc Cache","1desc Cache", iter++},
		    		  {"99999999Cache","99999999", "99999999desc ","99999999Cache","99999999desc Cache","99999999desc Cache", iter++},
		    		  
		    		  
		    		  //Find a code, with the code not existing in the cache-> should bring it from DB
		    		  {"123","123", "123desc","123Cache","123desc Cache","123desc", iter++},
		    		  {"123231", "123231","123231Desc", "123231Cache","123231desc Cache","123231Desc", iter++},
		    		  { "1","1", "1desc", "1Cache","1desc Cache","1desc", iter++},
		    		  {"99999999","99999999", "99999999desc","99999999Cache","99999999desc Cache","99999999desc", iter++},
		    		
				    	
		    		 //SET TO FAIL:
		    		  
		    		  
		    		  
		    		  /*
		    		  //Find a code, with empty cache-> should bring it from DB
		    		  {"123","123", "123descriptiooooooooooooo","","","", iter++},
		    		  {"123231","123231", "123231Description","","", "", iter++},
		    		  {"1","1", "", "","","2222", iter++},
		    		  {"99999999","99999999",  "99999999Desc desc","","","", iter++},
		    		  
		    		  
		    		  //Find a code, with the code existing in the cache-> should bring it from Cache
		    		  {"123Cache","123", "123desc","123Cache","123desc Cache","123desc", iter++},
		    		  {"123231Cache", "123231","123231Desc", "123231Cache","123231desc Cache","123231Desc", iter++},
		    		  { "1Cache","1", "1desc ", "1Cache","1desc Cache","1desc", iter++},
		    		  {"99999999Cache","99999999", "99999999desc ","99999999Cache","99999999desc Cache","99999999desc", iter++},
		    		  
		    		  
		    		  //Find a code, with the code not existing in the cache-> should bring it from DB
		    		  {"123","123", "123desc","123Cache","123desc Cache","123desc Cache", iter++},
		    		  {"123231", "123231","123231Desc", "123231Cache","123231desc Cache","123231Desc Cache", iter++},
		    		  { "1","1", "1desc", "1Cache","1desc Cache","1desc Cache", iter++},
		    		  {"99999999","99999999", "99999999desc","99999999Cache","99999999desc Cache","99999999desc Cache", iter++},
		    		
		    		 */
		    		  });
		   }
		 
		   
		   
		 
	
		 @Before
		 public void initMocks() throws Exception {

			 
			// Mocking the DB object
			 propertyUtil = PowerMockito.mock(PropertyUtil.class);
			 
 
		     // Mocking the DB object
			 exportReceivingFacMock = Mockito.mock(ExportReceivingFacilityDT.class);
			 
			 loggerMock = Mockito.mock(LogUtils.class);

		 }
		 
		 
			@Test
				public void getOrganismListDescSNM_test() throws Exception{

				System.out.println("******************* Starting test case named: getOrganismListDescSNM_test *******************");
				
				//ARANGE
				PowerMockito.mockStatic(PropertyUtil.class);
				PowerMockito.when(PropertyUtil.getInstance()).thenReturn(propertyUtil);
				
				 Whitebox.setInternalState(NEDSSConstants.class, "SRT_MAX_LENGTH", 5000);
				 
				 //Simulating values in the Cache:
				 
				 HashMap<Object, Object>mapForDesc = new  HashMap<Object, Object>();//Simulating values in the cache
				 TreeMap<Object, Object> valueForORGANISM_SNM_LIST = new TreeMap<Object, Object>();
				 
				 
				 valueForORGANISM_SNM_LIST.put(codeCache, descCache);
				 valueForORGANISM_SNM_LIST.put("anotherCode1", "anotherCode1");
				 valueForORGANISM_SNM_LIST.put("anotherCode2", "anotherCode2");
				 valueForORGANISM_SNM_LIST.put("anotherCode3", "anotherCode3");
				 
				 mapForDesc.put("ORGANISM_SNM_LIST", valueForORGANISM_SNM_LIST);
				 Whitebox.setInternalState(CachedDropDownValues.class, "mapForDesc", mapForDesc);
				 
				 
				 
				 //Simulating values from the DB:
				 
				 TreeMap<Object,Object> mapReturnByQuery = new  TreeMap<Object, Object>();//Simulating hashMap returned by query
		
				 mapReturnByQuery.put(code, desc);
				 mapReturnByQuery.put("anotherCode1", "anotherCode1");
				 mapReturnByQuery.put("anotherCode2", "anotherCode2");
				 mapReturnByQuery.put("anotherCode3", "anotherCode3");
				 
				 

				 Mockito.doReturn(mapReturnByQuery).when(srtMap).getOrganismListSNM();
				 PowerMockito.spy(CachedDropDownValues.class);
				 PowerMockito.doReturn(srtMap).when(cachedDropdownValues, "getSRTMapEJBRef");
				
			 
				 //ACT: Calling the method we are testing:
				String descriptionActualResult = cachedDropdownValues.getOrganismListDescSNM(codeToFind);
			
				//ASSERT: Comparing the values
				System.out.println("Iteration: #"+iteration+"\nCode: "+code+"\nExpected description: "+expectedDescription+"\nActual description: "+descriptionActualResult);
				Assert.assertEquals(descriptionActualResult,expectedDescription);
				System.out.println("PASSED");
					
			
				System.out.println("******************* End test case named: getOrganismListDescSNM_test *******************");
				}

			
			
			
				
				
			
			
	/*We should have these 3 sets on each test*/
	//Arrange
	
	//Act
	
	//Assert
}
	
}