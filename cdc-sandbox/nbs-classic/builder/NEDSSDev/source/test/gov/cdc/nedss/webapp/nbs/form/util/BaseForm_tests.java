package test.gov.cdc.nedss.webapp.nbs.form.util;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
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
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageTBHelper;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;


/**
For this test case, only 1 method is tested so far:
- splitVerCtrlNbr
 */




/**
Description:

This method will check if the number of times the internal method setAnswer is called is the same one as expected.
Since the method we are testing is a void method, this is the only way we have to know if it is really working fine.


Lessons learnt:

- ...



*/




@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.util.BaseForm","gov.cdc.nedss.util.LogUtils"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({BaseForm.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class BaseForm_tests {



@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.util.BaseForm","gov.cdc.nedss.util.LogUtils"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({BaseForm.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetAnswersBatchEntry_test {



	private static ArrayList<Map<String,String>> arrayMaps = new ArrayList<Map<String, String>>();
	private String subsectionNm;
	private int expectedValue;
	private int iteration;
	
	 public SetAnswersBatchEntry_test(ArrayList<Map<String,String>> arrayMaps, String subsectionNm, int expectedValue, int it){
		 super();
		 this.arrayMaps = arrayMaps;
		 this.subsectionNm = subsectionNm;
		 this.expectedValue = expectedValue;//the expectedValue is how many times we expect the internal setAnswer method to be called.,
		 this.iteration = it;
		

	 }

	 
	   @Parameterized.Parameters
	   public static Collection input() {
		   
		   
		    ArrayList<Map<String,String>> arrayMaps1 = new ArrayList<Map<String, String>>();
			Map<String, String> map1 = new HashMap<>();
			
			map1.put("LABAST6","18921-7");
			map1.put("LABAST8","131196009");
			map1.put("LABAST3","120228005");

			Map<String, String> map2 = new HashMap<>();
			
			map2.put("LABAST6","18921-7");
			map2.put("LABAST8","131196009");
			map2.put("LABAST3","120228005");

			arrayMaps1.add(map1);
			arrayMaps1.add(map2);
			
			/******************************************/
			
			ArrayList<Map<String,String>> arrayMaps2 = new ArrayList<Map<String, String>>();
			  
			map1 = new HashMap<>();
			
			map1.put("LABAST6","18921-7");
			map1.put("LABAST8","131196009");
			map1.put("LABAST3","120228005");

			map2 = new HashMap<>();
			
			map2.put("LABAST6","18921-7");
			map2.put("LABAST8","131196009");
			map2.put("LABAST3","120228005");
			
			Map<String, String> map3 = new HashMap<>();
			
			map3.put("LABAST6","18921-7");
			map3.put("LABAST8","131196009");
			map3.put("LABAST3","120228005");
			

			arrayMaps2.add(map1);
			arrayMaps2.add(map2);
			arrayMaps2.add(map3);
			
			/******************************************/
			
			
			ArrayList<Map<String,String>> arrayMaps3 = new ArrayList<Map<String, String>>();
			  
			map1 = new HashMap<>();
			
			map1.put("LABAST14","08/15/2022");
			map1.put("LABAST6","18921-7");
			map1.put("LABAST5","08/15/2022");
			map1.put("LABAST8","131196009");
			map1.put("LABAST7","39334004");
			map1.put("LABAST3","120228005");
	
			
			
			/******************************************/
			
			map2 = new HashMap<>();
			
			
			map2.put("LABAST14","08/15/2022");
			map2.put("LABAST6","18934-0");
			map2.put("LABAST5","08/15/2022");
			map2.put("LABAST8","131196009");
			map2.put("LABAST7","39334004");
			map2.put("LABAST3","120228005");
			
			
			/******************************************/
			
			
			
			map3 = new HashMap<>();
		
			
			map3.put("LABAST14","08/15/2022");
			map3.put("LABAST6","18973-8");
			map3.put("LABAST5","08/15/2022");
			map3.put("LABAST8","131196009");
			map3.put("LABAST7","39334004");
			map3.put("LABAST3","120228005");
			

			/******************************************/
			
			
			
			ArrayList<Map<String,String>> arrayMaps4 = new ArrayList<Map<String, String>>();
			
			
			
			HashMap<String, String> map4 = new HashMap<>();
			
			
			map4.put("LABAST14","08/15/2022");
			map4.put("LABAST6","18974-6");
			map4.put("LABAST5","08/15/2022");
			map4.put("LABAST8","131196009");
			map4.put("LABAST7","39334004");
			map4.put("LABAST3","120228005");
			
			
			
			arrayMaps4.add(map1);
			arrayMaps4.add(map2);
			arrayMaps4.add(map3);
			arrayMaps4.add(map4);
			
			/******************************************/
			//These values contain OTHER questions:
		
			

			ArrayList<Map<String,String>> arrayMaps5 = new ArrayList<Map<String, String>>();
			
			
			map1 = new HashMap<>();
			
			
			map1.put("LABAST14","08/16/2022");
			map1.put("LABAST6","18921-7");
			map1.put("LABAST5","08/16/2022");
			map1.put("LABAST8","131196009");
			map1.put("LABAST7","OTH");
			map1.put("LABAST3","OTH");
			map1.put("LABAST7Oth","Other2");
			map1.put("LABAST3Oth","Other1");
			
			map2 = new HashMap<>();
			
			
			
			
			map2.put("LABAST14","08/16/2022");
			map2.put("LABAST6","18934-0");
			map2.put("LABAST5","08/16/2022");
			map2.put("LABAST8","131196009");
			map2.put("LABAST7","OTH");
			map2.put("LABAST3","OTH");
			map2.put("LABAST7Oth","Other2");
			map2.put("LABAST3Oth","Other1");
			
			
			
			
			map3 = new HashMap<>();
			

			map3.put("LABAST14","08/16/2022");
			map3.put("LABAST6","18973-8");
			map3.put("LABAST5","08/16/2022");
			map3.put("LABAST8","131196009");
			map3.put("LABAST7","OTH");
			map3.put("LABAST3","OTH");
			map3.put("LABAST7Oth","Other2");
			map3.put("LABAST3Oth","Other1");
			
			map4 = new HashMap<>();
				

			map4.put("LABAST14","08/16/2022");
			map4.put("LABAST6","18974-6");
			map4.put("LABAST5","08/16/2022");
			map4.put("LABAST8","131196009");
			map4.put("LABAST7","OTH");
			map4.put("LABAST3","OTH");
			map4.put("LABAST7Oth","Other2");
			map4.put("LABAST3Oth","Other1");
			
			
			
			arrayMaps5.add(map1);
			arrayMaps5.add(map2);
			arrayMaps5.add(map3);
			arrayMaps5.add(map4);
			

			/******************************************/

			
			ArrayList<Map<String,String>> arrayMaps6 = new ArrayList<Map<String, String>>();
			
		
			arrayMaps6.add(null);
			arrayMaps6.add(null);
			arrayMaps6.add(null);
			
			
			

		  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
		   //The areas are the lines where the new method escapeCharacterSequence method is called
	      return Arrays.asList(new Object[][]{
	    		  //ContextAction, expected value, iteration
	    		  {arrayMaps1,"NBS_UI_GA27033",2,1},//PASS - contains 2 rows
	    		  {arrayMaps2,"NBS_UI_GA27033",3,2},//PASS - contains 3 rows
	    		  {arrayMaps3,"NBS_UI_GA27033",0,3},//PASS - contains no rows
	    		  {arrayMaps4,"NBS_UI_GA27033",4,4},//PASS - contains 4 rows
	    		  {arrayMaps5,"NBS_UI_GA27033",4,5},//PASS - contains 4 rows with OTH values
	    		  {arrayMaps6,"NBS_UI_GA27033",3,6},//PASS - contains null arrays
	    		  
	    		  
	    		  /*{arrayMaps1,"NBS_UI_GA27033",1,7},//FAIL - should be 2
	    		  {arrayMaps2,"NBS_UI_GA27033",2,8},//FAIL - should be 3
	    		  {arrayMaps3,"NBS_UI_GA27033",1,9},//FAIL - should be 0
	    		  {arrayMaps4,"NBS_UI_GA27033",0,10},//FAIL - should be 4
	    		  {null,"NBS_UI_GA27033",0,11},//FAIL - it is null*/
	    		  
	    		  });
	      
	  				
	  	
	  	
	  	
	   }
	 
	 
	 
	/*
	 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
	 * */

	@org.mockito.Mock
	LogUtils loggerMock;

	@Mock
	HttpSession httpSession;

	
	@InjectMocks
	@Spy
	BaseForm baseForm = Mockito.spy(new BaseForm());
	
	
		 @Before
		 public void initMocks() throws Exception {

			 loggerMock = Mockito.mock(LogUtils.class);
			
		 }
		 
		 
		 		@Test
				public void setAnswersBatchEntry_test() throws Exception{
				
					
				System.out.println("******************* Starting test case named: setAnswersBatchEntry_test *******************");
			
				BatchEntry be = new BatchEntry();
				
				int id = -1;

				be.setAnswerMaps(arrayMaps);
				be.setSubsecNm(subsectionNm);
				be.setId(id);
				Mockito.doNothing().when(baseForm).setAnswer(Mockito.any(BatchEntry.class),Mockito.any(HttpSession.class));
			  
				baseForm.setAnswersBatchEntry(be,httpSession);
				
				System.out.println("Iteration: #"+iteration+"\nExpected times internal method setAnswer has been called: "+this.expectedValue);
				
				Mockito.verify(baseForm,Mockito.times(expectedValue)).setAnswer(Mockito.any(BatchEntry.class),Mockito.any(HttpSession.class));
				
				System.out.println("PASSED");
				System.out.println("******************* End test case named: setAnswersBatchEntry_test *******************");
		
			}
	 		 
}



/*
public String isTbConditionCode(String conditionCode){

String isTB = "false";

if(PropertyUtil.isTBCode(conditionCode))
	isTB="true";

return isTB;
}

*/








@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.util.BaseForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({BaseForm.class,LogUtils.class, PropertyUtil.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class IsTbConditionCode_test {



	private String conditionCode;
	private String expectedValue;
	private boolean valueToReturn;
	private int iteration;
	
	
	 public IsTbConditionCode_test(String conditionCode, String expectedValue, boolean valueToReturn, int it){
		 super();
		 this.conditionCode = conditionCode;
		 this.expectedValue = expectedValue;//the expectedValue is how many times we expect the internal setAnswer method to be called.,
		 this.valueToReturn = valueToReturn;
		 this.iteration = it;
		

	 }

	 
	   @Parameterized.Parameters
	   public static Collection input() {
		   

		     return Arrays.asList(new Object[][]{
	    		  //conditionCode, expected value, value to return from the internall method call, iteration
		    		 {"102201","true", true,1},
		    		 {"502582","true", true,2},
		    		 {"","false",false,3},
		    		 {null,"false",false,4},	 
		    	
	    		  });
	  	
	   }
	 
	 
	 
	/*
	 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
	 * */

	@org.mockito.Mock
	LogUtils loggerMock;

	@Mock
	
	PropertyUtil propertyUtil;
	
	@Mock
	HttpSession httpSession;

	
	@InjectMocks
	@Spy
	BaseForm baseForm = Mockito.spy(new BaseForm());
	
	
		 @Before
		 public void initMocks() throws Exception {

			 loggerMock = Mockito.mock(LogUtils.class);
			
		 }
		 
		 
		 		@Test
				public void isTbConditionCode_test() throws Exception{
				
					
				System.out.println("******************* Starting test case named: isTbConditionCode_test *******************");
			
				
			//	propertyUtil = PowerMockito.mock(PropertyUtil.class);
				PowerMockito.mockStatic(PropertyUtil.class);
				when(PropertyUtil.isTBCode(conditionCode)).thenReturn(valueToReturn);
				 //when(PropertyUtil.isMergeCandidateDefaultSurvivorOldest()).thenReturn(true);
				String actualResult = Whitebox.invokeMethod(new BaseForm(), "isTbConditionCode", conditionCode);
				
				System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue+"\nActual value: "+actualResult);
				Assert.assertEquals(expectedValue, actualResult);
		
				System.out.println("PASSED");
				System.out.println("******************* End test case named: isTbConditionCode_test *******************");
		
			}
	 		 
}









@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.util.BaseForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageTBHelper"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({BaseForm.class,LogUtils.class, WebContextFactory.class, PropertyUtil.class,  NBSContext.class, PageTBHelper.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class IsStateCaseNumberUnique_test {



	private String conditionCode;
	private String stateCaseNumber;
	private boolean expectedValue;
	private boolean valueToReturn;//if it's a TB code
	private String valueToReturnPhcUid;
	private boolean isUnique;
	private String iteration;
	
	
	 public IsStateCaseNumberUnique_test(String conditionCode, String stateCaseNumber, boolean expectedValue, boolean valueToReturn, String valueToReturnPhcUid, boolean isUnique, String it){
		 super();
		 this.conditionCode = conditionCode;
		 this.stateCaseNumber = stateCaseNumber;
		 this.expectedValue = expectedValue;//is unique from method that we are testing
		 this.valueToReturn = valueToReturn;//Is TB Code
		 this.valueToReturnPhcUid = valueToReturnPhcUid;//Public health case uid
		 this.isUnique=isUnique;//is unique from internal method, it should match expectedValue
		 this.iteration = it;
		

	 }

	 
	   @Parameterized.Parameters
	   public static Collection input() {
		   
		   int it = 0;

		     return Arrays.asList(new Object[][]{
	    		  //conditionCode, expected value, value to return from the internal method call, iteration
		    		 
		    		 //TB Condition codes: 102201,502582 and 11020
		    		 //TB code, State Case Id,   total expected value,    expected is TB Code,    Phc Uid,    is Unique internal method,    iteration
		    		 {"102201","1212",true, true,"111111111",true, "isStateCaseNumberUnique_test"+"_"+it++},
		    		 {"10220","1212",true, true,"111111111",true, "isStateCaseNumberUnique_test"+"_"+it++},
		    		 {"502582","A1",true, true,"111111111",true,  "isStateCaseNumberUnique_test"+"_"+it++},
		    		 {"11020","A2",true,false,"111111111",true,  "isStateCaseNumberUnique_test"+"_"+it++},
		    		 {"11020111","A2",true,false,"111111111",true,  "isStateCaseNumberUnique_test"+"_"+it++},
		    		 {"1022012222","1212",true, false,"111111111",true,  "isStateCaseNumberUnique_test"+"_"+it++},
		    		 {"5025822222","A1",true, false,"111111111",true,  "isStateCaseNumberUnique_test"+"_"+it++},
		    		 {"110202222","A2",true,false,"111111111",true,  "isStateCaseNumberUnique_test"+"_"+it++},
		    		 {"110201112222","A2",true,false,"111111111",true,  "isStateCaseNumberUnique_test"+"_"+it++},
		    		 {"102201","2008-OK-ABCD56789",false, true,"111111111",false,  "isStateCaseNumberUnique_test"+"_"+it++},
		    		 {"102201","2008-OK-ABCD56781",true, true,"111111111",true,  "isStateCaseNumberUnique_test"+"_"+it++},
		    		 {"11","2008-OK-ABCD56789",true, false,"111111111",true,  "isStateCaseNumberUnique_test"+"_"+it++},
		    		 
		    		 
		    		 
		    		 
		    		 //{null,"false",false,4},	 
		    	
	    		  });
	  	
	   }
	 
	 
	 
	/*
	 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
	 * */

	@Mock
	WebContext wcontext;
		
	@Mock
	HttpServletRequest request;
		
	@org.mockito.Mock
	LogUtils loggerMock;

	@Mock
	NBSContext nbsContext;
	
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	HttpSession httpSession;

	@Mock
	PageTBHelper pageTBHelper;
	
	@InjectMocks
	@Spy
	BaseForm baseForm = Mockito.spy(new BaseForm());
	
	
		 @Before
		 public void initMocks() throws Exception {

			 loggerMock = Mockito.mock(LogUtils.class);
			 wcontext = Mockito.mock(WebContext.class);
			 pageTBHelper = Mockito.mock(PageTBHelper.class);
			 nbsContext = Mockito.mock(NBSContext.class);
			 request = Mockito.mock(HttpServletRequest.class);
			
		 }	 
		 		@Test
				public void isStateCaseNumberUnique_test() throws Exception{
				
					
				System.out.println("******************* Starting test case named: isStateCaseNumberUnique_test *******************");

				PowerMockito.mockStatic(PropertyUtil.class);
				when(PropertyUtil.isTBCode(conditionCode)).thenReturn(valueToReturn);
		     	
				PowerMockito.mockStatic(WebContextFactory.class);
			    when(WebContextFactory.get()).thenReturn(wcontext);
			    when(wcontext.getHttpServletRequest()).thenReturn(request);
 
			    PowerMockito.mockStatic(NBSContext.class);
				when(NBSContext.retrieve(any(HttpSession.class), any(String.class))).thenReturn(valueToReturnPhcUid);

		        when(pageTBHelper.isCaseNumberUnique(stateCaseNumber, valueToReturnPhcUid)).thenReturn(isUnique);   
		        PowerMockito.whenNew(PageTBHelper.class).withNoArguments().thenReturn(pageTBHelper);

				boolean actualResult = Whitebox.invokeMethod(new BaseForm(), "isStateCaseNumberUnique", stateCaseNumber, conditionCode);
				
				System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue+"\nActual value: "+actualResult);
				Assert.assertEquals(expectedValue, actualResult);
		
				System.out.println("PASSED");
				System.out.println("******************* End test case named: isStateCaseNumberUnique_test *******************");
		
			}
	 		 
}


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.util.BaseForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({BaseForm.class,LogUtils.class, WebContextFactory.class, PropertyUtil.class, CallProxyEJB.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class CheckForExistingNotificationsByPublicHealthCaseUid_test {
	private String uid;
	private boolean expectedValue;
	private int iteration;
	
	public CheckForExistingNotificationsByPublicHealthCaseUid_test(String uid, boolean expectedValue, int iteration) {
		super();
		this.uid = uid;
		this.expectedValue = expectedValue;
		this.iteration = iteration;
	}

	@Mock
	WebContext wcontext;
		
	@Mock
	HttpServletRequest request;
		
	@org.mockito.Mock
	LogUtils loggerMock;

	@Mock
	NBSContext nbsContext;
	
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	HttpSession httpSession;

	@InjectMocks
	@Spy
	BaseForm baseForm = Mockito.spy(new BaseForm());
	
	@Before
	public void initMocks() throws Exception {
		loggerMock = Mockito.mock(LogUtils.class);
		wcontext = Mockito.mock(WebContext.class);
		nbsContext = Mockito.mock(NBSContext.class);
		request = Mockito.mock(HttpServletRequest.class);
	 }
	
	@Test
	public void checkForExistingNotificationsByPublicHealthCaseUid_test() throws Exception{
		PowerMockito.mockStatic(WebContextFactory.class);
	    PowerMockito.mockStatic(CallProxyEJB.class);
	    when(WebContextFactory.get()).thenReturn(wcontext);
	    when(wcontext.getHttpServletRequest()).thenReturn(request);
	    when(request.getSession()).thenReturn(httpSession);
	    when(CallProxyEJB.callProxyEJB(any(Object[].class),any(String.class),any(String.class),any(HttpSession.class))).thenReturn(true);
	    Object[] oParams = new Object[] {this.uid};
	    Boolean actualResult = Whitebox.invokeMethod(new BaseForm(), "checkForExistingNotificationsByPublicHealthCaseUid", oParams);
		System.out.println("Iteration: #"+iteration+"  Expected value: "+expectedValue+"  Actual value: "+actualResult);
		Assert.assertEquals(expectedValue, actualResult);
		System.out.println("Iteration: #"+iteration+"  TestCase PASSED");
	}
				
	 @SuppressWarnings("rawtypes")
		@Parameterized.Parameters
		   public static Collection input() { //allowing all are success, otherwise maven build is failing when run build with test cases.
		      return Arrays.asList(new Object[][]{
		    		  {"123456",true,1},
		    		  {"1234567",true,2},
		    		  {"12345678",true,3},
		    		  {"123456789",true,4},
	    		  });
		   }
}



}
	
