package test.gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.matchers.Any;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;




/**
Methods that have been unit tested so far:

- splitVerCtrlNbr
- getCodeFromCodeDescription
- getValueFromForm
- validateIfNCriteriasMet
- checkIfRowValuesExistInRepeatingBlock_test
- setVerCritTBLogicOnSubmit_test
- validateRule1_test
- validateRule2_test
- validateRule3_test
- validateRule6_test
- validateRule7_test
- validateRule8_test
- ...
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class PageCreateHelper_tests {



@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ValidateRule1_test{
	

//TODO: we might not need this now that we are using inner classes.
/*	private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public ValidateRule1_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   ArrayList<String> validateRule1_test_100= new ArrayList<String>();
	   validateRule1_test_100.add("INV1140");
	   validateRule1_test_100.add("PHC72");
	   validateRule1_test_100.add("true");
	   
	   ArrayList<String> validateRule1_test_101= new ArrayList<String>();
	   validateRule1_test_101.add("INV1140");
	   validateRule1_test_101.add("C0683157");
	   validateRule1_test_101.add("false");
	   
	   ArrayList<String> validateRule1_test_102= new ArrayList<String>();
	   validateRule1_test_102.add("INV1140");
	   validateRule1_test_102.add("OTH");
	   validateRule1_test_102.add("false");
	   
	   ArrayList<String> validateRule1_test_103= new ArrayList<String>();
	   validateRule1_test_103.add("INV1140");
	   validateRule1_test_103.add("PHC721");
	   validateRule1_test_103.add("false");
	   
	   ArrayList<String> validateRule1_test_104= new ArrayList<String>();
	   validateRule1_test_104.add("INV1140");
	   validateRule1_test_104.add("183944003");
	   validateRule1_test_104.add("false");
	   
	   ArrayList<String> validateRule1_test_105= new ArrayList<String>();
	   validateRule1_test_105.add("INV1140");
	   validateRule1_test_105.add("PHC165");
	   validateRule1_test_105.add("false");
	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule1_test
    		    
    		  {"validateRule1_test"+"_"+it++,validateRule1_test_100},
    		  {"validateRule1_test"+"_"+it++,validateRule1_test_101},
    		  {"validateRule1_test"+"_"+it++,validateRule1_test_102},
    		  {"validateRule1_test"+"_"+it++,validateRule1_test_103},
    		  {"validateRule1_test"+"_"+it++,validateRule1_test_104},
    		  {"validateRule1_test"+"_"+it++,validateRule1_test_105}
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void validateRule1_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: validateRule1_test *******************");
			
				String codeRule1 = parameters.get(0);
				String valueToReturnRule1 = parameters.get(1);
				boolean expectedValue7 = Boolean.parseBoolean(parameters.get(2));
				
			
				PowerMockito.doReturn(valueToReturnRule1).when(PageCreateHelper.class, "getValueFromForm",  form,codeRule1);
	
	
				boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "validateRule1", form);
			
				System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue7+"\nActual value: "+actualResult);
				Assert.assertEquals(expectedValue7, actualResult);
				System.out.println("PASSED");
				System.out.println("******************* End test case named: validateRule1_test *******************");
			
		}		
	
}
	






@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ValidateRule2_test{
	

//TODO: we might not need this now that we are using inner classes.
/*	private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public ValidateRule2_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Paramters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	   /*Parameters for validateRule2_test*/
	   
	   ArrayList<String> validateRule2_test_100= new ArrayList<String>();
	   validateRule2_test_100.add("TUB122");
	   validateRule2_test_100.add("10828004");
	   validateRule2_test_100.add("TUB130");
	   validateRule2_test_100.add("10828004");
	   validateRule2_test_100.add("true");//expected value to be return by method checking if row exists in the repeating block
	   validateRule2_test_100.add("true");//expected result returned by validateRule2
	   

	   ArrayList<String> validateRule2_test_101= new ArrayList<String>();
	   validateRule2_test_101.add("TUB122");
	   validateRule2_test_101.add("10828004");
	   validateRule2_test_101.add("TUB130");
	   validateRule2_test_101.add("10828004");
	   validateRule2_test_101.add("false");//expected value to be return by method checking if row exists in the repeating block
	   validateRule2_test_101.add("true");//expected result returned by validateRule2
	   
	   ArrayList<String> validateRule2_test_102= new ArrayList<String>();
	   validateRule2_test_102.add("TUB122");
	   validateRule2_test_102.add("10828004");
	   validateRule2_test_102.add("TUB130");
	   validateRule2_test_102.add("10828001");
	   validateRule2_test_102.add("false");//expected value to be return by method checking if row exists in the repeating block
	   validateRule2_test_102.add("true");//expected result returned by validateRule2
	   
		
	   ArrayList<String> validateRule2_test_103= new ArrayList<String>();
	   validateRule2_test_103.add("TUB122");
	   validateRule2_test_103.add("10828001");
	   validateRule2_test_103.add("TUB130");
	   validateRule2_test_103.add("10828001");
	   validateRule2_test_103.add("false");//expected value to be return by method checking if row exists in the repeating block
	   validateRule2_test_103.add("false");//expected result returned by validateRule2
	   
	   ArrayList<String> validateRule2_test_104= new ArrayList<String>();
	   validateRule2_test_104.add("TUB122");
	   validateRule2_test_104.add("10828001");
	   validateRule2_test_104.add("TUB130");
	   validateRule2_test_104.add("10828001");
	   validateRule2_test_104.add("true");//expected value to be return by method checking if row exists in the repeating block
	   validateRule2_test_104.add("true");//expected result returned by validateRule2
	   
	   
	   ArrayList<String> validateRule2_test_105= new ArrayList<String>();
	   validateRule2_test_105.add("TUB122");
	   validateRule2_test_105.add("10828001");
	   validateRule2_test_105.add("TUB130");
	   validateRule2_test_105.add("10828004");
	   validateRule2_test_105.add("false");//expected value to be return by method checking if row exists in the repeating block
	   validateRule2_test_105.add("true");//expected result returned by validateRule2
	   
	   
   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule2_test
  		    
    		  {"validateRule2_test"+"_"+it++,validateRule2_test_100},
    		  {"validateRule2_test"+"_"+it++,validateRule2_test_101},
    		  {"validateRule2_test"+"_"+it++,validateRule2_test_102},
    		  {"validateRule2_test"+"_"+it++,validateRule2_test_103},
    		  {"validateRule2_test"+"_"+it++,validateRule2_test_104},
    		  {"validateRule2_test"+"_"+it++,validateRule2_test_105},
    		 
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	 @Test
		public void validateRule2_test() throws Exception{
				

					System.out.println("******************* Starting test case named: validateRule2_test *******************");
				
					String subsectionId = "NBS_UI_GA27017";
					
					//This will simulate the values selected from the UI
					String codeRule1 = parameters.get(0);
					String valueToReturnRule1 = parameters.get(1);
					String codeRule2 = parameters.get(2);
					String valueToReturnRule2 = parameters.get(3);
					boolean existsInRepeatingBlock =  Boolean.parseBoolean(parameters.get(4));
					boolean expectedValue =  Boolean.parseBoolean(parameters.get(5));
					
				
					//We don't need to change this values, this is just to be able to make the internal call that will be mocked.
					HashMap <String, ArrayList<String>> codeValuesRule2 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values2_1 = new ArrayList<String>();
					ArrayList<String> values2_2 = new ArrayList<String>();
					

					values2_1.add("50941-4");//Culture
					values2_2.add("10828004");//Positive
					
					codeValuesRule2.put("INV290",values2_1);//Test type
					codeValuesRule2.put("INV291",values2_2);//Test result
					
					
					
					PowerMockito.doReturn(valueToReturnRule1).when(PageCreateHelper.class, "getValueFromForm",  form,codeRule1);
					PowerMockito.doReturn(valueToReturnRule2).when(PageCreateHelper.class, "getValueFromForm",  form,codeRule2);
					
					PowerMockito.doReturn(existsInRepeatingBlock).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule2);
 	
					boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "validateRule2", form);
				
					System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue+"\nActual value: "+actualResult);
					Assert.assertEquals(expectedValue, actualResult);
					System.out.println("PASSED");
					System.out.println("******************* End test case named: validateRule1_test *******************");
				}
					
		
	
	 
	 }

			





@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ValidateRule3_test{
	

//TODO: we might not need this now that we are using inner classes.
/*	private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public ValidateRule3_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {


	   
	   	/*Parameters for validateRule3_test*/
	   
	   ArrayList<String> validateRule3_test_100= new ArrayList<String>();
	   validateRule3_test_100.add("TUB135");
	   validateRule3_test_100.add("10828004");
	   validateRule3_test_100.add("true");//expected value to be return by method checking if row exists in the repeating block
	   validateRule3_test_100.add("true");//expected result returned by validateRule2
	   
	   
	   ArrayList<String> validateRule3_test_101= new ArrayList<String>();
	   validateRule3_test_101.add("TUB135");
	   validateRule3_test_101.add("10828004");
	   validateRule3_test_101.add("false");//expected value to be return by method checking if row exists in the repeating block
	   validateRule3_test_101.add("true");//expected result returned by validateRule2
	   
	   ArrayList<String> validateRule3_test_102= new ArrayList<String>();
	   validateRule3_test_102.add("TUB135");
	   validateRule3_test_102.add("10828001");
	   validateRule3_test_102.add("true");//expected value to be return by method checking if row exists in the repeating block
	   validateRule3_test_102.add("true");//expected result returned by validateRule2
	   
	   ArrayList<String> validateRule3_test_103= new ArrayList<String>();
	   validateRule3_test_103.add("TUB135");
	   validateRule3_test_103.add("10828001");
	   validateRule3_test_103.add("false");//expected value to be return by method checking if row exists in the repeating block
	   validateRule3_test_103.add("false");//expected result returned by validateRule2
	   
	   ArrayList<String> validateRule3_test_104= new ArrayList<String>();
	   validateRule3_test_104.add("TUB136");
	   validateRule3_test_104.add("10828004");
	   validateRule3_test_104.add("true");//expected value to be return by method checking if row exists in the repeating block
	   validateRule3_test_104.add("true");//expected result returned by validateRule2
	   
	   
	   ArrayList<String> validateRule3_test_105= new ArrayList<String>();
	   validateRule3_test_105.add("TUB136");
	   validateRule3_test_105.add("10828004");
	   validateRule3_test_105.add("false");//expected value to be return by method checking if row exists in the repeating block
	   validateRule3_test_105.add("false");//expected result returned by validateRule2
	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule3_test
  		    
    		  {"validateRule3_test"+"_"+it++,validateRule3_test_100},
    		  {"validateRule3_test"+"_"+it++,validateRule3_test_101},
    		  {"validateRule3_test"+"_"+it++,validateRule3_test_102},
    		  {"validateRule3_test"+"_"+it++,validateRule3_test_103},
    		  {"validateRule3_test"+"_"+it++,validateRule3_test_104},
    		  {"validateRule3_test"+"_"+it++,validateRule3_test_105},
    		 
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

		@Test
		public void validateRule3_test() throws Exception{
				

					System.out.println("******************* Starting test case named: validateRule3_test *******************");
				
					String subsectionId = "NBS_UI_GA27017";
					
					//This will simulate the values selected from the UI
					String codeRule1 = parameters.get(0);
					String valueToReturnRule1 = parameters.get(1);
					boolean existsInRepeatingBlock =  Boolean.parseBoolean(parameters.get(2));
					boolean expectedValue =  Boolean.parseBoolean(parameters.get(3));
					
				
					//We don't need to change this values, this is just to be able to make the internal call that will be mocked.
					HashMap <String, ArrayList<String>> codeValuesRule3 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values2_1 = new ArrayList<String>();
					ArrayList<String> values2_2 = new ArrayList<String>();
					

					values2_1.add("LAB673");//NAA Test
					values2_2.add("10828004");//Positive
					
					codeValuesRule3.put("INV290",values2_1);//Test Type
					codeValuesRule3.put("INV291",values2_2);//Test Result

					
					PowerMockito.doReturn(valueToReturnRule1).when(PageCreateHelper.class, "getValueFromForm",  form,codeRule1);
				
					PowerMockito.doReturn(existsInRepeatingBlock).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule3);
 	
					boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "validateRule3", form);
				
					System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue+"\nActual value: "+actualResult);
					Assert.assertEquals(expectedValue, actualResult);
					System.out.println("PASSED");
					System.out.println("******************* End test case named: validateRule3_test *******************");
				}	
	 }








@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ValidateRule6_test{
	

//TODO: we might not need this now that we are using inner classes.
	/*private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public ValidateRule6_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {


	   /*Parameters for validateRule6_test*/
	   
	   
	   ArrayList<String> validateRule6_test_100= new ArrayList<String>();
	   validateRule6_test_100.add("INV1115");
	   validateRule6_test_100.add("PHC165");
	   validateRule6_test_100.add("true");
	   
	   ArrayList<String> validateRule6_test_101= new ArrayList<String>();
	   validateRule6_test_101.add("INV1115");
	   validateRule6_test_101.add("C0683157");
	   validateRule6_test_101.add("false");
	   
	   ArrayList<String> validateRule6_test_102= new ArrayList<String>();
	   validateRule6_test_102.add("INV1115");
	   validateRule6_test_102.add("OTH");
	   validateRule6_test_102.add("false");
	   
	   ArrayList<String> validateRule6_test_103= new ArrayList<String>();
	   validateRule6_test_103.add("INV1115");
	   validateRule6_test_103.add("PHC72");
	   validateRule6_test_103.add("false");
	   
    		
    		
	   ArrayList<String> validateRule6_test_104= new ArrayList<String>();
	   validateRule6_test_104.add("INV1115");
	   validateRule6_test_104.add("183944003");
	   validateRule6_test_104.add("false");
	   
   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule6_test
	    		   
	    		  {"validateRule6_test"+"_"+it++,validateRule6_test_100},
	    		  {"validateRule6_test"+"_"+it++,validateRule6_test_101},
	    		  {"validateRule6_test"+"_"+it++,validateRule6_test_102},
	    		  {"validateRule6_test"+"_"+it++,validateRule6_test_103},
	    		  {"validateRule6_test"+"_"+it++,validateRule6_test_104},
	    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 

		@Test
		public void validateRule6_test() throws Exception{
				

					System.out.println("******************* Starting test case named: validateRule6_test *******************");
				
					String codeRule1 = parameters.get(0);
					String valueToReturnRule1 = parameters.get(1);
					boolean expectedValue7 = Boolean.parseBoolean(parameters.get(2));
					
					PowerMockito.doReturn(valueToReturnRule1).when(PageCreateHelper.class, "getValueFromForm",  form,codeRule1);
		
 	
					boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "validateRule6", form);
				
					System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue7+"\nActual value: "+actualResult);
					Assert.assertEquals(expectedValue7, actualResult);
					System.out.println("PASSED");
					System.out.println("******************* End test case named: validateRule6_test *******************");
				}
				
	
		

	 }

			





@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ValidateRule7_test{
	

//TODO: we might not need this now that we are using inner classes.
/*	private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public ValidateRule7_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {


	   
		
	   /*Parameters for validateRule7_test*/
	   
	   ArrayList<String> validateRule7_test_100= new ArrayList<String>();
	   validateRule7_test_100.add("INV1115");
	   validateRule7_test_100.add("PHC162");
	   validateRule7_test_100.add("true");
	   
	   ArrayList<String> validateRule7_test_101= new ArrayList<String>();
	   validateRule7_test_101.add("INV1115");
	   validateRule7_test_101.add("PHC165");
	   validateRule7_test_101.add("false");
	   
	   ArrayList<String> validateRule7_test_102= new ArrayList<String>();
	   validateRule7_test_102.add("INV1115");
	   validateRule7_test_102.add("OTH");
	   validateRule7_test_102.add("false");
	   
	   ArrayList<String> validateRule7_test_103= new ArrayList<String>();
	   validateRule7_test_103.add("INV1115");
	   validateRule7_test_103.add("PHC72");
	   validateRule7_test_103.add("false");
	   
	   ArrayList<String> validateRule7_test_104= new ArrayList<String>();
	   validateRule7_test_104.add("INV1115");
	   validateRule7_test_104.add("183944003");
	   validateRule7_test_104.add("false");
	   
   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  
    		  //validateRule7_test
    		    
    		  {"validateRule7_test"+"_"+it++,validateRule7_test_100},
    		  {"validateRule7_test"+"_"+it++,validateRule7_test_101},
    		  {"validateRule7_test"+"_"+it++,validateRule7_test_102},
    		  {"validateRule7_test"+"_"+it++,validateRule7_test_103},
    		  {"validateRule7_test"+"_"+it++,validateRule7_test_104},
    	  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 


		@Test
		public void validateRule7_test() throws Exception{
				
		
					System.out.println("******************* Starting test case named: validateRule7_test *******************");
				
					String codeRule1 = parameters.get(0);
					String valueToReturnRule1 = parameters.get(1);
					boolean expectedValue7 = Boolean.parseBoolean(parameters.get(2));
					
					PowerMockito.doReturn(valueToReturnRule1).when(PageCreateHelper.class, "getValueFromForm",  form,codeRule1);
		
 	
					boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "validateRule7", form);
				
					System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue7+"\nActual value: "+actualResult);
					Assert.assertEquals(expectedValue7, actualResult);
					System.out.println("PASSED");
					System.out.println("******************* End test case named: validateRule7_test *******************");
				}
	 }



		




@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ValidateRule8_test{
	

//TODO: we might not need this now that we are using inner classes.
/*	private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public ValidateRule8_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {


	   
	   /*Parameters for validateRule8_test*/
	   
	   
	   ArrayList<String> validateRule8_test_100= new ArrayList<String>();
	   validateRule8_test_100.add("INV1115");
	   validateRule8_test_100.add("415684004");
	   validateRule8_test_100.add("true");
	   
	   
	   
	   ArrayList<String> validateRule8_test_101= new ArrayList<String>();
	   validateRule8_test_101.add("INV1115");
	   validateRule8_test_101.add("PHC165");
	   validateRule8_test_101.add("false");
	   
	   ArrayList<String> validateRule8_test_102= new ArrayList<String>();
	   validateRule8_test_102.add("INV1115");
	   validateRule8_test_102.add("OTH");
	   validateRule8_test_102.add("false");
	   
	   
	   ArrayList<String> validateRule8_test_103= new ArrayList<String>();
	   validateRule8_test_103.add("INV1115");
	   validateRule8_test_103.add("PHC162");
	   validateRule8_test_103.add("false");

	   
	   ArrayList<String> validateRule8_test_104= new ArrayList<String>();
	   validateRule8_test_104.add("INV1115");
	   validateRule8_test_104.add("183944003");
	   validateRule8_test_104.add("false");

	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule8_test
  		    
    		  {"validateRule8_test"+"_"+it++,validateRule8_test_100},
    		  {"validateRule8_test"+"_"+it++,validateRule8_test_101},
    		  {"validateRule8_test"+"_"+it++,validateRule8_test_102},
    		  {"validateRule8_test"+"_"+it++,validateRule8_test_103},
    		  {"validateRule8_test"+"_"+it++,validateRule8_test_104},
    		  
    		
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 

		
		
		@Test
		public void validateRule8_test() throws Exception{
				
				
					System.out.println("******************* Starting test case named: validateRule8_test *******************");
				
					String codeRule1 = parameters.get(0);
					String valueToReturnRule1 = parameters.get(1);
					boolean expectedValue7 = Boolean.parseBoolean(parameters.get(2));
					
					
				
					PowerMockito.doReturn(valueToReturnRule1).when(PageCreateHelper.class, "getValueFromForm",  form,codeRule1);
		
 	
					boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "validateRule8", form);
				
					System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue7+"\nActual value: "+actualResult);
					Assert.assertEquals(expectedValue7, actualResult);
					System.out.println("PASSED");
					System.out.println("******************* End test case named: validateRule8_test *******************");
				}
			}		
		
		

	 



			




@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SplitVerCtrlNbr_test{
	

//TODO: we might not need this now that we are using inner classes.
	/*private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public SplitVerCtrlNbr_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {



	   /*Values for test: splitVerCtrlNbr_test*/
	   
	   
	   ArrayList<String> splitVerCtrlNbr_test_100 = new ArrayList<String>();
	   splitVerCtrlNbr_test_100.add("24217|1");
	   splitVerCtrlNbr_test_100.add("1");
	   
	   
	   ArrayList<String> splitVerCtrlNbr_test_101 = new ArrayList<String>();
	   splitVerCtrlNbr_test_101.add("24217342|2");
	   splitVerCtrlNbr_test_101.add("2");
	   
	   ArrayList<String> splitVerCtrlNbr_test_102 = new ArrayList<String>();
	   splitVerCtrlNbr_test_102.add("24217342|211");
	   splitVerCtrlNbr_test_102.add("211");
	   
	   
	   ArrayList<String> splitVerCtrlNbr_test_103 = new ArrayList<String>();
	   splitVerCtrlNbr_test_103.add("24217");
	   splitVerCtrlNbr_test_103.add("1");
	   
	   ArrayList<String> splitVerCtrlNbr_test_104 = new ArrayList<String>();
	   splitVerCtrlNbr_test_104.add("");
	   splitVerCtrlNbr_test_104.add("1");
	   
	   ArrayList<String> splitVerCtrlNbr_test_105 = new ArrayList<String>();
	   splitVerCtrlNbr_test_105.add("|");
	   splitVerCtrlNbr_test_105.add("1");
	 
	   ArrayList<String> splitVerCtrlNbr_test_106 = new ArrayList<String>();
	   splitVerCtrlNbr_test_106.add("|224124");
	   splitVerCtrlNbr_test_106.add("224124");
	   
	   ArrayList<String> splitVerCtrlNbr_test_107 = new ArrayList<String>();
	   splitVerCtrlNbr_test_107.add("|224124|4");
	   splitVerCtrlNbr_test_107.add("224124|4");
	   
	   ArrayList<String> splitVerCtrlNbr_test_108 = new ArrayList<String>();
	   splitVerCtrlNbr_test_108.add("24217|");
	   splitVerCtrlNbr_test_108.add("1");
	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //Parameters for splitVerCtrlNbr_test
    		  {"splitVerCtrlNbr_test"+"_"+it++,splitVerCtrlNbr_test_100},
    		  {"splitVerCtrlNbr_test"+"_"+it++,splitVerCtrlNbr_test_101},
    		  {"splitVerCtrlNbr_test"+"_"+it++,splitVerCtrlNbr_test_102},
    		  {"splitVerCtrlNbr_test"+"_"+it++,splitVerCtrlNbr_test_103},
    		  {"splitVerCtrlNbr_test"+"_"+it++,splitVerCtrlNbr_test_104},
    		  {"splitVerCtrlNbr_test"+"_"+it++,splitVerCtrlNbr_test_105},
    		  {"splitVerCtrlNbr_test"+"_"+it++,splitVerCtrlNbr_test_106},
    		  {"splitVerCtrlNbr_test"+"_"+it++,splitVerCtrlNbr_test_107},
    		  {"splitVerCtrlNbr_test"+"_"+it++,splitVerCtrlNbr_test_108},

    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 

		@Test
		public void splitVerCtrlNbr_test() throws Exception{
		
				System.out.println("******************* Starting test case named: splitVerCtrlNbr_test *******************");
				
				String strUid=parameters.get(0);
				String actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "splitVerCtrlNbr", strUid);
				String expectedResult=parameters.get(1);
				System.out.println("Iteration: #"+iteration+"\nValue sent: "+strUid+"\nExpected value: "+expectedResult+"\nActual value: "+actualResult);
				Assert.assertEquals(expectedResult, actualResult);
				System.out.println("PASSED");
				System.out.println("******************* End test case named: splitVerCtrlNbr_test *******************");
			
	}

	 }



			











			



@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class GetCodeFromCodeDescription_test{
	

//TODO: we might not need this now that we are using inner classes.
/*	private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public GetCodeFromCodeDescription_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	   /*Values for test: getCodeFromCodeDescription_test*/
	   
		  
	   ArrayList<String> getCodeFromCodeDescription_test_100 = new ArrayList<String>();
	   getCodeFromCodeDescription_test_100.add("code$$description");
	   getCodeFromCodeDescription_test_100.add("code");
	   
	   ArrayList<String> getCodeFromCodeDescription_test_101 = new ArrayList<String>();
	   getCodeFromCodeDescription_test_101.add("code$$descri$$ption");
	   getCodeFromCodeDescription_test_101.add("code");
	   
	   ArrayList<String> getCodeFromCodeDescription_test_102 = new ArrayList<String>();
	   getCodeFromCodeDescription_test_102.add("$$");
	   getCodeFromCodeDescription_test_102.add("");
	   
	   ArrayList<String> getCodeFromCodeDescription_test_103 = new ArrayList<String>();
	   getCodeFromCodeDescription_test_103.add("asdasdsadasd$$sad");
	   getCodeFromCodeDescription_test_103.add("asdasdsadasd");
	   
	   ArrayList<String> getCodeFromCodeDescription_test_104 = new ArrayList<String>();
	   getCodeFromCodeDescription_test_104.add("1$$2");
	   getCodeFromCodeDescription_test_104.add("1");
	   
	   ArrayList<String> getCodeFromCodeDescription_test_105 = new ArrayList<String>();
	   getCodeFromCodeDescription_test_105.add("1$111111$$2");
	   getCodeFromCodeDescription_test_105.add("1$111111");
	   
	   ArrayList<String> getCodeFromCodeDescription_test_106 = new ArrayList<String>();
	   getCodeFromCodeDescription_test_106.add(null);
	   getCodeFromCodeDescription_test_106.add("");
	   
	   ArrayList<String> getCodeFromCodeDescription_test_107 = new ArrayList<String>();
	   getCodeFromCodeDescription_test_107.add("code1$$");
	   getCodeFromCodeDescription_test_107.add("code1");
	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		//Parameters for getCodeFromCodeDescription_test
  	    	
    		  {"getCodeFromCodeDescription_test"+"_"+it++,getCodeFromCodeDescription_test_100},
    		  {"getCodeFromCodeDescription_test"+"_"+it++,getCodeFromCodeDescription_test_101},
    		  {"getCodeFromCodeDescription_test"+"_"+it++,getCodeFromCodeDescription_test_102},
    		  {"getCodeFromCodeDescription_test"+"_"+it++,getCodeFromCodeDescription_test_103},
    		  {"getCodeFromCodeDescription_test"+"_"+it++,getCodeFromCodeDescription_test_104},
    		  {"getCodeFromCodeDescription_test"+"_"+it++,getCodeFromCodeDescription_test_105},
    		  {"getCodeFromCodeDescription_test"+"_"+it++,getCodeFromCodeDescription_test_106},
    		  {"getCodeFromCodeDescription_test"+"_"+it++,getCodeFromCodeDescription_test_107},

    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 

		
		@Test
		public void getCodeFromCodeDescription_test() throws Exception{
			
	
				System.out.println("******************* Starting test case named: getCodeFromCodeDescription_test *******************");

				
				String actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "getCodeFromCodeDescription", parameters.get(0));
			
				System.out.println("Iteration: #"+iteration+"\nValue sent: "+parameters.get(0)+"\nExpected value: "+parameters.get(1)+"\nActual value: "+actualResult);
				Assert.assertEquals(parameters.get(1), actualResult);
				System.out.println("PASSED");
				System.out.println("******************* End test case named: getCodeFromCodeDescription_test *******************");
			}
		

	 }



			








@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class GetValueFromForm_test{
	

//TODO: we might not need this now that we are using inner classes.
	/*private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public GetValueFromForm_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Values for test: getValueFromForm_test*/
	   
	   ArrayList<String> getValueFromForm_test_100= new ArrayList<String>();
	   getValueFromForm_test_100.add("TUB120");
	   getValueFromForm_test_100.add("10828004");
	   

	   ArrayList<String> getValueFromForm_test_101= new ArrayList<String>();
	   getValueFromForm_test_101.add(null);
	   getValueFromForm_test_101.add(null);
	   
	   ArrayList<String> getValueFromForm_test_102= new ArrayList<String>();
	   getValueFromForm_test_102.add("AAAAA");
	   getValueFromForm_test_102.add(null);
	   
	   ArrayList<String> getValueFromForm_test_103= new ArrayList<String>();
	   getValueFromForm_test_103.add("INV1140");
	   getValueFromForm_test_103.add("PHC72");
	   
	   ArrayList<String> getValueFromForm_test_104= new ArrayList<String>();
	   getValueFromForm_test_104.add("TUB122");
	   getValueFromForm_test_104.add("10828004");
	   
	   ArrayList<String> getValueFromForm_test_105= new ArrayList<String>();
	   getValueFromForm_test_105.add("TUB130");
	   getValueFromForm_test_105.add("10828004");
	   
	   ArrayList<String> getValueFromForm_test_106= new ArrayList<String>();
	   getValueFromForm_test_106.add("TUB135");
	   getValueFromForm_test_106.add("10828004");
	   
	   ArrayList<String> getValueFromForm_test_107= new ArrayList<String>();
	   getValueFromForm_test_107.add("TUB126");
	   getValueFromForm_test_107.add("10828004");	
	   
	   ArrayList<String> getValueFromForm_test_108= new ArrayList<String>();
	   getValueFromForm_test_108.add("");
	   getValueFromForm_test_108.add(null);
	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  
    		  //Parameters for getValueFromForm
    		  
    		  {"getValueFromForm_test"+"_"+it++,getValueFromForm_test_100},
    		  {"getValueFromForm_test"+"_"+it++,getValueFromForm_test_101},
    		  {"getValueFromForm_test"+"_"+it++,getValueFromForm_test_102},
    		  {"getValueFromForm_test"+"_"+it++,getValueFromForm_test_103},
    		  {"getValueFromForm_test"+"_"+it++,getValueFromForm_test_104},
    		  {"getValueFromForm_test"+"_"+it++,getValueFromForm_test_105},
    		  {"getValueFromForm_test"+"_"+it++,getValueFromForm_test_106},
    		  {"getValueFromForm_test"+"_"+it++,getValueFromForm_test_107},
    		  {"getValueFromForm_test"+"_"+it++,getValueFromForm_test_108},
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		 pageClientVO = Mockito.mock(PageClientVO.class);
		 form = Mockito.mock(PageForm.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 

		@Test
		public void getValueFromForm_test() throws Exception{
			
	
				System.out.println("******************* Starting test case named: getValueFromForm_test *******************");
			
				Map<Object, Object> answerMap = new HashMap<Object, Object>();
				answerMap.put("INV1140", "PHC72");
				answerMap.put("TUB122", "10828004");
				answerMap.put("TUB130", "10828004");
				answerMap.put("TUB135", "10828004");
				answerMap.put("TUB120", "10828004");
				answerMap.put("TUB126", "10828004");
					
				when(pageClientVO.getAnswerMap()).thenReturn(answerMap);  
				when(form.getPageClientVO()).thenReturn(pageClientVO);  

				String actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "getValueFromForm", form, parameters.get(0));
			
				System.out.println("Iteration: #"+iteration+"\nValue sent: "+parameters.get(0)+"\nExpected value: "+ parameters.get(1)+"\nActual value: "+actualResult);
				Assert.assertEquals(parameters.get(1), actualResult);
				System.out.println("PASSED");
				System.out.println("******************* End test case named: getValueFromForm_test *******************");
			
		}
		
	 }



			










@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ValidateIfNCriteriasMet_test{
	

//TODO: we might not need this now that we are using inner classes.
	/*private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public ValidateIfNCriteriasMet_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   
	   /*Parameters for validateIfNCriteriasMet_test*/
	   
	   
	   
	   ArrayList<String> validateIfNCriteriasMet_test_100= new ArrayList<String>();
	   validateIfNCriteriasMet_test_100.add("6038$$Y");
	   validateIfNCriteriasMet_test_100.add("9384$$Y");
	   validateIfNCriteriasMet_test_100.add("2");
	   validateIfNCriteriasMet_test_100.add("true");
	   
	   
	   ArrayList<String> validateIfNCriteriasMet_test_101= new ArrayList<String>();
	   validateIfNCriteriasMet_test_101.add("10109$$Y");
	   validateIfNCriteriasMet_test_101.add("PHC1888$$Y");
	   validateIfNCriteriasMet_test_101.add("2");
	   validateIfNCriteriasMet_test_101.add("true");
	   
	   
	   ArrayList<String> validateIfNCriteriasMet_test_102= new ArrayList<String>();
	   validateIfNCriteriasMet_test_102.add("NBS456$$Y");
	   validateIfNCriteriasMet_test_102.add("4110$$Y");
	   validateIfNCriteriasMet_test_102.add("2");
	   validateIfNCriteriasMet_test_102.add("true");
	   
	   
	   ArrayList<String> validateIfNCriteriasMet_test_103= new ArrayList<String>();
	   validateIfNCriteriasMet_test_103.add("");
	   validateIfNCriteriasMet_test_103.add("7833$$Y");
	   validateIfNCriteriasMet_test_103.add("3");
	   validateIfNCriteriasMet_test_103.add("false");
	   
	   
	   ArrayList<String> validateIfNCriteriasMet_test_104= new ArrayList<String>();
	   validateIfNCriteriasMet_test_104.add("NBS456$$Y");
	   validateIfNCriteriasMet_test_104.add("7833$$Y");
	   validateIfNCriteriasMet_test_104.add("4110$$Y");
	   validateIfNCriteriasMet_test_104.add("4");
	   validateIfNCriteriasMet_test_104.add("false");

	   ArrayList<String> validateIfNCriteriasMet_test_105= new ArrayList<String>();
	   validateIfNCriteriasMet_test_105.add("NBS456$$Y");
	   validateIfNCriteriasMet_test_105.add("7833$$Y");
	   validateIfNCriteriasMet_test_105.add("4110$$Y");
	   validateIfNCriteriasMet_test_105.add("1");
	   validateIfNCriteriasMet_test_105.add("true");

	   ArrayList<String> validateIfNCriteriasMet_test_106= new ArrayList<String>();
	   validateIfNCriteriasMet_test_106.add("6038$$Y");
	   validateIfNCriteriasMet_test_106.add("2");
	   validateIfNCriteriasMet_test_106.add("false");
	   
	   ArrayList<String> validateIfNCriteriasMet_test_107= new ArrayList<String>();
	   validateIfNCriteriasMet_test_107.add("");
	   validateIfNCriteriasMet_test_107.add("2");
	   validateIfNCriteriasMet_test_107.add("false");
	   
	   ArrayList<String> validateIfNCriteriasMet_test_108= new ArrayList<String>();
	   validateIfNCriteriasMet_test_108.add("11111$$1111");
	   validateIfNCriteriasMet_test_108.add("2222$$222222");
	   validateIfNCriteriasMet_test_108.add("2");
	   validateIfNCriteriasMet_test_108.add("false");
	   
	   ArrayList<String> validateIfNCriteriasMet_test_109= new ArrayList<String>();
	   validateIfNCriteriasMet_test_109.add("6038$$Y");
	   validateIfNCriteriasMet_test_109.add("9384$$N");
	   validateIfNCriteriasMet_test_109.add("2");
	   validateIfNCriteriasMet_test_109.add("false");
	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  
    		  //Parameters for validateIfNCriteriasMet_test
    		  
    		  {"validateIfNCriteriasMet_test"+"_"+it++,validateIfNCriteriasMet_test_100},
    		  {"validateIfNCriteriasMet_test"+"_"+it++,validateIfNCriteriasMet_test_101},
    		  {"validateIfNCriteriasMet_test"+"_"+it++,validateIfNCriteriasMet_test_102},
    		  {"validateIfNCriteriasMet_test"+"_"+it++,validateIfNCriteriasMet_test_103},
    		  {"validateIfNCriteriasMet_test"+"_"+it++,validateIfNCriteriasMet_test_104},
    		  {"validateIfNCriteriasMet_test"+"_"+it++,validateIfNCriteriasMet_test_105},
    		  {"validateIfNCriteriasMet_test"+"_"+it++,validateIfNCriteriasMet_test_106},
    		  {"validateIfNCriteriasMet_test"+"_"+it++,validateIfNCriteriasMet_test_107},
    		  {"validateIfNCriteriasMet_test"+"_"+it++,validateIfNCriteriasMet_test_108},
    		  {"validateIfNCriteriasMet_test"+"_"+it++,validateIfNCriteriasMet_test_109},
    		  
    		
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 


	 
	 
		@Test
		public void validateIfNCriteriasMet_test() throws Exception{
		
	    
		
				
				HashMap<String, String> criterias = new HashMap<String, String>();

		    	criterias.put("6038","Y");
		    	criterias.put("9384","Y");
		    	criterias.put("8987","Y");
		    	criterias.put("4110","Y");
		    	criterias.put("10109","Y");
		    	criterias.put("55672","Y");
		    	criterias.put("35617","Y");
		    	criterias.put("4127","Y");
		    	criterias.put("641","Y");
		    	criterias.put("6099","Y");
		    	criterias.put("78903","Y");
		    	criterias.put("2551","Y");
		    	criterias.put("82122","Y");
		    	criterias.put("7623","Y");
		    	criterias.put("139462","Y");
		    	criterias.put("PHC1888","Y");
		    	criterias.put("3007","Y");
		    	criterias.put("7833","Y");
		    	criterias.put("190376","Y");
		    	criterias.put("1364504","Y");
		    	criterias.put("PHC1889","Y");
		    	criterias.put("2592","Y");
		    	criterias.put("2198359","Y");
		    	criterias.put("NBS456","Y");
		    	
		    	
		    	
				System.out.println("******************* Starting test case named: validateIfNCriteriasMet_test *******************");
			
				
				String code = "";
				String value = "";
				
				if(parameters!=null){
					for(int i =0; i<parameters.size()-1; i++){
						String codeValueString = parameters.get(i);
						
						if(codeValueString!=null && codeValueString.indexOf("$$")!=-1){
							
							String[] codVal = codeValueString.split("\\$\\$");
							code = codVal[0];
							value = codVal[1];

							
							PowerMockito.doReturn(value).when(PageCreateHelper.class, "getValueFromForm",  form,code);
							
						}
						
						
					}
					
					
				}
				
				String expectedValue = parameters.get(parameters.size()-1);
				boolean expectedVal = false;
				
				if(expectedValue!=null && expectedValue.equalsIgnoreCase("true"))
					expectedVal = true;

				int n = Integer.parseInt(parameters.get(parameters.size()-2));
				boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "validateIfNCriteriasMet", form, criterias,n);
				System.out.println("Iteration: #"+iteration+"\nValue sent: "+parameters.get(0)+"\nExpected value: "+expectedVal+"\nActual value: "+actualResult);
				Assert.assertEquals(expectedVal, actualResult);
			
				System.out.println("PASSED");
				System.out.println("******************* End test case named: validateIfNCriteriasMet_test *******************");
			}
		
		
		
		
	 }



			














@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ValidateIfMultiIsPartOfValues_test{
	

//TODO: we might not need this now that we are using inner classes.
	private String iteration;
	ArrayList<String> parameters;
	
	
 public ValidateIfMultiIsPartOfValues_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	   /*Parameters for validateIfMultiIsPartOfValues_test*/
	   
	   
	   ArrayList<String> validateIfMultiIsPartOfValues_test_100= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_100.add("INV1133");
	   validateIfMultiIsPartOfValues_test_100.add("281778006____aaaaa");
	   validateIfMultiIsPartOfValues_test_100.add("281778006____39607008____3120008");
	   validateIfMultiIsPartOfValues_test_100.add("true");
	   
	   ArrayList<String> validateIfMultiIsPartOfValues_test_101= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_101.add("INV1133");
	   validateIfMultiIsPartOfValues_test_101.add("bbbbbbbbbbbb____281778006");
	   validateIfMultiIsPartOfValues_test_101.add("281778006____39607008____3120008");
	   validateIfMultiIsPartOfValues_test_101.add("true");
	   
	   
	   ArrayList<String> validateIfMultiIsPartOfValues_test_102= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_102.add("INV1133");
	   validateIfMultiIsPartOfValues_test_102.add("bbbbbbbbbbbb____cccc____aaaaa");
	   validateIfMultiIsPartOfValues_test_102.add("281778006____39607008____3120008");
	   validateIfMultiIsPartOfValues_test_102.add("false");
	 
	   
	   
	   ArrayList<String> validateIfMultiIsPartOfValues_test_103= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_103.add("INV1133");
	   validateIfMultiIsPartOfValues_test_103.add("bbbbbbbbbbbb____cccc____aaaaa");
	   validateIfMultiIsPartOfValues_test_103.add("281778006____cccc____3120008");
	   validateIfMultiIsPartOfValues_test_103.add("true");
	 
	   ArrayList<String> validateIfMultiIsPartOfValues_test_104= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_104.add("INV1133");
	   validateIfMultiIsPartOfValues_test_104.add("281778006");
	   validateIfMultiIsPartOfValues_test_104.add("281778006");
	   validateIfMultiIsPartOfValues_test_104.add("true");
	 
	   
	   ArrayList<String> validateIfMultiIsPartOfValues_test_105= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_105.add("INV1133");
	   validateIfMultiIsPartOfValues_test_105.add("aaa");
	   validateIfMultiIsPartOfValues_test_105.add("281778006");
	   validateIfMultiIsPartOfValues_test_105.add("false");
	 
	   
	   ArrayList<String> validateIfMultiIsPartOfValues_test_106= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_106.add("INV1133");
	   validateIfMultiIsPartOfValues_test_106.add("aaa");
	   validateIfMultiIsPartOfValues_test_106.add(null);
	   validateIfMultiIsPartOfValues_test_106.add("false");

	   ArrayList<String> validateIfMultiIsPartOfValues_test_107= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_107.add("INV1133");
	   validateIfMultiIsPartOfValues_test_107.add("");
	   validateIfMultiIsPartOfValues_test_107.add("");
	   validateIfMultiIsPartOfValues_test_107.add("true");
	 
	   
	   ArrayList<String> validateIfMultiIsPartOfValues_test_108= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_108.add("INV1133");
	   validateIfMultiIsPartOfValues_test_108.add("");
	   validateIfMultiIsPartOfValues_test_108.add("281778006");
	   validateIfMultiIsPartOfValues_test_108.add("false");
	   
	   ArrayList<String> validateIfMultiIsPartOfValues_test_109= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_109.add("INV1133");
	   validateIfMultiIsPartOfValues_test_109.add(null);
	   validateIfMultiIsPartOfValues_test_109.add("281778006");
	   validateIfMultiIsPartOfValues_test_109.add("false");
	   
	   ArrayList<String> validateIfMultiIsPartOfValues_test_110= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_110.add("INV1133");
	   validateIfMultiIsPartOfValues_test_110.add("");
	   validateIfMultiIsPartOfValues_test_110.add("281778006");
	   validateIfMultiIsPartOfValues_test_110.add("false");
	   
	   
	   ArrayList<String> validateIfMultiIsPartOfValues_test_111= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_111.add("INV1133");
	   validateIfMultiIsPartOfValues_test_111.add("");
	   validateIfMultiIsPartOfValues_test_111.add("");
	   validateIfMultiIsPartOfValues_test_111.add("true");
	   
	 
	   ArrayList<String> validateIfMultiIsPartOfValues_test_112= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_112.add("INV1133");
	   validateIfMultiIsPartOfValues_test_112.add("281778006____39607008____3120008");
	   validateIfMultiIsPartOfValues_test_112.add("281778006____39607008____3120008");
	   validateIfMultiIsPartOfValues_test_112.add("true");
	   
	 
	   ArrayList<String> validateIfMultiIsPartOfValues_test_113= new ArrayList<String>();
	   validateIfMultiIsPartOfValues_test_113.add("INV1133");
	   validateIfMultiIsPartOfValues_test_113.add("281778006____39607008____3120008");
	   validateIfMultiIsPartOfValues_test_113.add("3120008");
	   validateIfMultiIsPartOfValues_test_113.add("true");
	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  
    		  //Parameters for validateIfNCriteriasMet_test
    		  
    		  //validateIfMultiIsPartOfValues_test
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_100},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_101},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_102},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_103},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_104},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_105},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_106},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_107},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_108},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_109},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_110},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_111},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_112},
	    		  {"validateIfMultiIsPartOfValues_test"+"_"+it++,validateIfMultiIsPartOfValues_test_113},
	    		   
    		  
    		
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 pageClientVO = Mockito.mock(PageClientVO.class);
		 form = Mockito.mock(PageForm.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 


	 

		@Test
		public void validateIfMultiIsPartOfValues_test() throws Exception{
			
		
				System.out.println("******************* Starting test case named: validateIfMultiIsPartOfValues_test *******************");
			

					
					String questionId=parameters.get(0);
					String valuesSelectedFromF = parameters.get(1);
					
					String[] valuesSelectedFromForm= {""};
					
					if(valuesSelectedFromF!=null)
						valuesSelectedFromForm = valuesSelectedFromF.split("____");//Example of Values selected from the form
					//Criterias to be met
					String criterias = parameters.get(2);
					String[] criteriasToBeMet = {""};
					
					if(criterias!=null)
						
						criteriasToBeMet = criterias.split("____");
					
					boolean expectedValue6=Boolean.parseBoolean(parameters.get(3));
					
				
				
				Map<Object, Object> answerMap = new HashMap<Object, Object>();
				answerMap.put(questionId, valuesSelectedFromForm);
		
				//Mocking methods	
				when(pageClientVO.getAnswerArrayMap()).thenReturn(answerMap);  
				when(form.getPageClientVO()).thenReturn(pageClientVO);  

				
				
				
				ArrayList<String> INV1133Values = new ArrayList<String>();
						
				if(criteriasToBeMet!=null){
					for(int i = 0; i<criteriasToBeMet.length; i++){
						INV1133Values.add(criteriasToBeMet[i]);	
					}
				}

	
				boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "validateIfMultiIsPartOfValues", form, INV1133Values,"INV1133");
			
				System.out.println("Iteration: #"+iteration+"\nCriterias to be met: "+INV1133Values.toString()+"\n Values in the form: "+Arrays.toString(valuesSelectedFromForm)+"\nExpected value: "+expectedValue6+"\nActual value: "+actualResult);
				Assert.assertEquals(expectedValue6, actualResult);
				System.out.println("PASSED");
				System.out.println("******************* End test case named: validateIfMultiIsPartOfValues_test *******************");
			}
		
		
		
	 }



			










@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetVerCritTBLogicOnSubmit_test{
	

//TODO: we might not need this now that we are using inner classes.
	/*private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public SetVerCritTBLogicOnSubmit_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameter
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   

	   /*Parameters for setVerCritTBLogicOnSubmit_test_100*/
	   
	   ArrayList<String> setVerCritTBLogicOnSubmit_test_100= new ArrayList<String>();
	   setVerCritTBLogicOnSubmit_test_100.add("1");
	   
	   ArrayList<String> setVerCritTBLogicOnSubmit_test_101= new ArrayList<String>();
	   setVerCritTBLogicOnSubmit_test_101.add("2");
	   
	   ArrayList<String> setVerCritTBLogicOnSubmit_test_102= new ArrayList<String>();
	   setVerCritTBLogicOnSubmit_test_102.add("3");
	   
	   ArrayList<String> setVerCritTBLogicOnSubmit_test_103= new ArrayList<String>();
	   setVerCritTBLogicOnSubmit_test_103.add("4");
	   
	   ArrayList<String> setVerCritTBLogicOnSubmit_test_104= new ArrayList<String>();
	   setVerCritTBLogicOnSubmit_test_104.add("5_1");
	   
	   ArrayList<String> setVerCritTBLogicOnSubmit_test_105= new ArrayList<String>();
	   setVerCritTBLogicOnSubmit_test_105.add("5_2");
	  
	   ArrayList<String> setVerCritTBLogicOnSubmit_test_106= new ArrayList<String>();
	   setVerCritTBLogicOnSubmit_test_106.add("6");
	   
	   ArrayList<String> setVerCritTBLogicOnSubmit_test_107= new ArrayList<String>();
	   setVerCritTBLogicOnSubmit_test_107.add("7");
	   
	   ArrayList<String> setVerCritTBLogicOnSubmit_test_108= new ArrayList<String>();
	   setVerCritTBLogicOnSubmit_test_108.add("8");
	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  
    		  //Parameters for validateIfNCriteriasMet_test
    		  
    		  //setVerCritTBLogicOnSubmit_test
    		  {"setVerCritTBLogicOnSubmit_test"+"_"+it++,setVerCritTBLogicOnSubmit_test_100},
    		  {"setVerCritTBLogicOnSubmit_test"+"_"+it++,setVerCritTBLogicOnSubmit_test_101},
    		  {"setVerCritTBLogicOnSubmit_test"+"_"+it++,setVerCritTBLogicOnSubmit_test_102},
    		  {"setVerCritTBLogicOnSubmit_test"+"_"+it++,setVerCritTBLogicOnSubmit_test_103},
    		  {"setVerCritTBLogicOnSubmit_test"+"_"+it++,setVerCritTBLogicOnSubmit_test_104},
    		  {"setVerCritTBLogicOnSubmit_test"+"_"+it++,setVerCritTBLogicOnSubmit_test_105},
    		  {"setVerCritTBLogicOnSubmit_test"+"_"+it++,setVerCritTBLogicOnSubmit_test_106},
    		  {"setVerCritTBLogicOnSubmit_test"+"_"+it++,setVerCritTBLogicOnSubmit_test_107},
    		  {"setVerCritTBLogicOnSubmit_test"+"_"+it++,setVerCritTBLogicOnSubmit_test_108},
    		  
    		
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 pageClientVO = Mockito.mock(PageClientVO.class);
		 form = Mockito.mock(PageForm.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 


	 
	 
		@Test
		public void setVerCritTBLogicOnSubmit_test() throws Exception{
			

				System.out.println("******************* Starting test case named: setVerCritTBLogicOnSubmit_test *******************");
			
				String ruleValidated =parameters.get(0);//this represents which one of the rules returned true (criteria met).
				
				Map<Object, Object> answerMap = new HashMap<Object, Object>();	
				when(pageClientVO.getAnswerMap()).thenReturn(answerMap);  
				when(form.getPageClientVO()).thenReturn(pageClientVO);  
				
				
				if(ruleValidated!=null){
					switch(ruleValidated){
					case "1":
						PowerMockito.doReturn(true).when(PageCreateHelper.class, "validateRule1", form);
						break;
					case "2":
						PowerMockito.doReturn(true).when(PageCreateHelper.class, "validateRule2", form);
						break;
					case "3":
						PowerMockito.doReturn(true).when(PageCreateHelper.class, "validateRule3", form);
						break;
					case "4":
						PowerMockito.doReturn(true).when(PageCreateHelper.class, "validateRule4", form);
						break;
					case "5_1":
						PowerMockito.doReturn(true).when(PageCreateHelper.class, "validateRule5_part1", form);
						break;
					case "5_2":
						PowerMockito.doReturn(false).when(PageCreateHelper.class, "validateRule5_part1", form);
						PowerMockito.doReturn(true).when(PageCreateHelper.class, "validateRule5_part2", form);
						break;
					case "6":
						PowerMockito.doReturn(true).when(PageCreateHelper.class, "validateRule6", form);
						break;
					case "7":
						PowerMockito.doReturn(true).when(PageCreateHelper.class, "validateRule7", form);
						break;
					case "8":
						PowerMockito.doReturn(true).when(PageCreateHelper.class, "validateRule8", form);
						break;
					}
				}
				
				
				
				
				Whitebox.invokeMethod(new PageCreateHelper(), "setVerCritTBLogicOnSubmit", form);
			
				String INV1115 = "";
				String INV163 = "";
				String criteriaMetForRule= "0";//No criteria has been met yet
				if(ruleValidated!=null){
					
					INV1115 = (String)answerMap.get("INV1115");
					INV163 = (String)answerMap.get("INV163");
					
					
					
					
						if(ruleValidated.equalsIgnoreCase("1")){
							if(INV1115!=null && INV1115.equalsIgnoreCase("PHC162") && INV163!=null && INV163.equalsIgnoreCase("N"))
								criteriaMetForRule = "1";
						}
						if(ruleValidated.equalsIgnoreCase("2")){
							if(INV1115!=null && INV1115.equalsIgnoreCase("PHC97") && INV163!=null && INV163.equalsIgnoreCase("C"))
								criteriaMetForRule = "2";
						}
						if(ruleValidated.equalsIgnoreCase("3")){
							if(INV1115!=null && INV1115.equalsIgnoreCase("PHC653") && INV163!=null && INV163.equalsIgnoreCase("C"))
								criteriaMetForRule = "3";
						}
						if(ruleValidated.equalsIgnoreCase("4")){
							if(INV1115!=null && INV1115.equalsIgnoreCase("PHC98") && INV163!=null && INV163.equalsIgnoreCase("C"))
								criteriaMetForRule = "4";
						}
						if(ruleValidated.equalsIgnoreCase("5_1") || ruleValidated.equalsIgnoreCase("5_2")){
							if(INV1115!=null && INV1115.equalsIgnoreCase("PHC654") && INV163!=null && INV163.equalsIgnoreCase("C"))
								criteriaMetForRule = "5";
						}
						if(ruleValidated.equalsIgnoreCase("6")){
							if(INV163!=null && INV163.equalsIgnoreCase("C"))
								criteriaMetForRule = "6";
						}
						if(ruleValidated.equalsIgnoreCase("7")){
							if(INV163!=null && INV163.equalsIgnoreCase("N"))
								criteriaMetForRule = "7";
						}
						
						if(ruleValidated.equalsIgnoreCase("8")){
							if(INV163!=null && INV163.equalsIgnoreCase("S"))
								criteriaMetForRule = "8";
						}
					}
					
				
				
				if(ruleValidated!=null && ruleValidated.contains("5_"))
					ruleValidated="5";
				
				if(ruleValidated!=null && (ruleValidated.equalsIgnoreCase("6")|| ruleValidated.equalsIgnoreCase("7")|| ruleValidated.equalsIgnoreCase("8")))
					System.out.println("Iteration: #"+iteration+"\nRule to verify: "+ruleValidated+"\nCase Status: "+INV163);
				else
					System.out.println("Iteration: #"+iteration+"\nRule to verify: "+ruleValidated+"\nCase Verification: "+INV1115+"\nCase Status: "+INV163);
					
				Assert.assertEquals(criteriaMetForRule, ruleValidated);
				System.out.println("PASSED");
				System.out.println("******************* End test case named: setVerCritTBLogicOnSubmit_test *******************");
			}
		}
		
		
	
		
		
		
	 



			












@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class CheckIfRowValuesExistInRepeatingBlock_test{
	

//TODO: we might not need this now that we are using inner classes.
	/*private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public CheckIfRowValuesExistInRepeatingBlock_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   
	   	/*Parameters for setVerCritTBLogicOnSubmit_test*/
	   
	   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_100= new ArrayList<String>();
	   checkIfRowValuesExistInRepeatingBlock_test_100.add("NBS_UI_GA27017");//subsectionId
	   checkIfRowValuesExistInRepeatingBlock_test_100.add("{INV290,50941-4$$Culture;INV291,10828004$$Positive}____{INV290,50941-4$$Culture;INV291,410530007$$Not offered}");//simulating values entered from UI"
	   checkIfRowValuesExistInRepeatingBlock_test_100.add("{INV290,50941-4;INV291,10828004}");//criteria that needs to be met
	   checkIfRowValuesExistInRepeatingBlock_test_100.add("true");
		
	   
	   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_101= new ArrayList<String>();
	   checkIfRowValuesExistInRepeatingBlock_test_101.add("NBS_UI_GA27017");//subsectionId
	   checkIfRowValuesExistInRepeatingBlock_test_101.add("{INV290,50941-4$$Culture;INV291,385660001$$NotDone;LAB165,119334006$$Sputum}____{INV290,50941-4$$Culture;INV291,410530007$$Not offered}");//simulating values entered from UI"
	   checkIfRowValuesExistInRepeatingBlock_test_101.add("{INV290,50941-4;LAB165,119334006;INV291,385660001#UNK#82334004#443390004#410530007#PHC2092}");//criteria that needs to be met
	   checkIfRowValuesExistInRepeatingBlock_test_101.add("true");
		

	   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_102= new ArrayList<String>();
	   checkIfRowValuesExistInRepeatingBlock_test_102.add("NBS_UI_GA28004");//subsectionId
	   checkIfRowValuesExistInRepeatingBlock_test_102.add("LAB677,399208008$$valuee;LAB678,PHC1873$$ConsistentWithTB");//simulating values entered from UI"
	   checkIfRowValuesExistInRepeatingBlock_test_102.add("LAB677,169069000#399208008;LAB678,PHC1873;");//criteria that needs to be met
	   checkIfRowValuesExistInRepeatingBlock_test_102.add("true");
		
	   
	   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_103= new ArrayList<String>();
	   checkIfRowValuesExistInRepeatingBlock_test_103.add("NBS_UI_GA28004");//subsectionId
	   checkIfRowValuesExistInRepeatingBlock_test_103.add("LAB677,399208008$$valuee;LAB678,PHC1873$$ConsistentWithTB");//simulating values entered from UI"
	   checkIfRowValuesExistInRepeatingBlock_test_103.add("LAB677,169069000#169069001#169069002#169069003#399208008;LAB678,PHC1871#PHC1872#PHC1873;");//criteria that needs to be met
	   checkIfRowValuesExistInRepeatingBlock_test_103.add("true");
	   
	   
	   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_104= new ArrayList<String>();
	   checkIfRowValuesExistInRepeatingBlock_test_104.add("NBS_UI_GA27017");//subsectionId
	   checkIfRowValuesExistInRepeatingBlock_test_104.add("{INV290,50941-1$$Culture;INV291,385660001$$NotDone;LAB165,119334006$$Sputum}____{INV290,50941-4$$Culture;INV291,410530007$$Not offered}");//simulating values entered from UI"
	   checkIfRowValuesExistInRepeatingBlock_test_104.add("{INV290,50941-4;LAB165,119334006;INV291,385660001#UNK#82334004#443390004#410530007#PHC2092}");//criteria that needs to be met
	   checkIfRowValuesExistInRepeatingBlock_test_104.add("false");
		
	   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_105= new ArrayList<String>();
	   checkIfRowValuesExistInRepeatingBlock_test_105.add("NBS_UI_GA27017");//subsectionId
	   checkIfRowValuesExistInRepeatingBlock_test_105.add("{INV290,50941-1$$Culture1;INV291,385660001$$NotDone____INV290,50941-2$$Culture2;INV291,385660001$$NotDone____INV290,50941-4$$Culture;INV291,385660001$$NotDone;LAB165,119334006$$Sputum}____{INV290,50941-4$$Culture;INV291,410530007$$Not offered}");//simulating values entered from UI"
	   checkIfRowValuesExistInRepeatingBlock_test_105.add("{INV290,50941-4;LAB165,119334006;INV291,385660001#UNK#82334004#443390004#410530007#PHC2092}");//criteria that needs to be met
	   checkIfRowValuesExistInRepeatingBlock_test_105.add("true");
		

	   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_106= new ArrayList<String>();
	   checkIfRowValuesExistInRepeatingBlock_test_106.add("NBS_UI_GA27017");//subsectionId
	   checkIfRowValuesExistInRepeatingBlock_test_106.add("{INV290,50941-4$$Culture;INV291,10828004$$Positive}____{INV290,50941-4$$Culture;INV291,410530007$$Not offered}");//simulating values entered from UI"
	   checkIfRowValuesExistInRepeatingBlock_test_106.add("{NOT_CONTAIN_INV290,50941-4;INV291,10828004}");//criteria that needs to be met
	   checkIfRowValuesExistInRepeatingBlock_test_106.add("FALSE");
		
	   
	   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_107= new ArrayList<String>();
	   checkIfRowValuesExistInRepeatingBlock_test_107.add("NBS_UI_GA27017");//subsectionId
	   checkIfRowValuesExistInRepeatingBlock_test_107.add("{INV290,50941-5$$Culture;INV291,10828004$$Positive}____{INV290,50941-4$$Culture;INV291,410530007$$Not offered}");//simulating values entered from UI"
	   checkIfRowValuesExistInRepeatingBlock_test_107.add("{NOT_CONTAIN_INV290,50941-4;INV291,10828004}");//criteria that needs to be met
	   checkIfRowValuesExistInRepeatingBlock_test_107.add("true");
		
	   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_108= new ArrayList<String>();
	   checkIfRowValuesExistInRepeatingBlock_test_108.add("NBS_UI_GA27017");//subsectionId
	   checkIfRowValuesExistInRepeatingBlock_test_108.add("{INV290,50941-5$$Culture;INV291,10828004$$Positive}____{INV290,50941-4$$Culture;INV291,410530007$$Not offered}");//simulating values entered from UI"
	   checkIfRowValuesExistInRepeatingBlock_test_108.add("{NOT_CONTAIN_INV290,50941-4;NOT_CONTAIN_INV291,10828004}");//criteria that needs to be met
	   checkIfRowValuesExistInRepeatingBlock_test_108.add("false");
		
	   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_109= new ArrayList<String>();
	   checkIfRowValuesExistInRepeatingBlock_test_109.add("NBS_UI_GA27017");//subsectionId
	   checkIfRowValuesExistInRepeatingBlock_test_109.add("{INV290,50941-5$$Culture;INV291,10828004$$Positive}____{INV290,50941-1$$Culture;INV291,410530007$$Not offered}");//simulating values entered from UI"
	   checkIfRowValuesExistInRepeatingBlock_test_109.add("{NOT_CONTAIN_INV290,50941-4;NOT_CONTAIN_INV291,10828004}");//criteria that needs to be met
	   checkIfRowValuesExistInRepeatingBlock_test_109.add("true");
	   
	   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_110= new ArrayList<String>();
	   checkIfRowValuesExistInRepeatingBlock_test_110.add("NBS_UI_GA27017");//subsectionId
	   checkIfRowValuesExistInRepeatingBlock_test_110.add("INV290,50941-5$$Culture;INV291,10828005$$Positive1");//simulating values entered from UI"
	   checkIfRowValuesExistInRepeatingBlock_test_110.add("NOT_CONTAIN_INV290,50941-4;INV291,10828004");//criteria that needs to be met
	   checkIfRowValuesExistInRepeatingBlock_test_110.add("false");
	   
	  
	 
	 
		
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    	
    		  //checkIfRowValuesExistInRepeatingBlock_test

    		  {"checkIfRowValuesExistInRepeatingBlock_test"+"_"+it++,checkIfRowValuesExistInRepeatingBlock_test_100},
    		  {"checkIfRowValuesExistInRepeatingBlock_test"+"_"+it++,checkIfRowValuesExistInRepeatingBlock_test_101},
    		  {"checkIfRowValuesExistInRepeatingBlock_test"+"_"+it++,checkIfRowValuesExistInRepeatingBlock_test_102},
    		  {"checkIfRowValuesExistInRepeatingBlock_test"+"_"+it++,checkIfRowValuesExistInRepeatingBlock_test_103},
    		  {"checkIfRowValuesExistInRepeatingBlock_test"+"_"+it++,checkIfRowValuesExistInRepeatingBlock_test_104},
    		  {"checkIfRowValuesExistInRepeatingBlock_test"+"_"+it++,checkIfRowValuesExistInRepeatingBlock_test_105},
    		  {"checkIfRowValuesExistInRepeatingBlock_test"+"_"+it++,checkIfRowValuesExistInRepeatingBlock_test_106},
    		  {"checkIfRowValuesExistInRepeatingBlock_test"+"_"+it++,checkIfRowValuesExistInRepeatingBlock_test_107},
    		  {"checkIfRowValuesExistInRepeatingBlock_test"+"_"+it++,checkIfRowValuesExistInRepeatingBlock_test_108},
    		  {"checkIfRowValuesExistInRepeatingBlock_test"+"_"+it++,checkIfRowValuesExistInRepeatingBlock_test_109},
    		  {"checkIfRowValuesExistInRepeatingBlock_test"+"_"+it++,checkIfRowValuesExistInRepeatingBlock_test_110},
    		  
    		
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 pageClientVO = Mockito.mock(PageClientVO.class);
		 form = Mockito.mock(PageForm.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 

	 
	 
		@Test
		public void checkIfRowValuesExistInRepeatingBlock_test() throws Exception{
				
	
					System.out.println("******************* Starting test case named: checkIfRowValuesExistInRepeatingBlock_test *******************");
				
					
					
					/*   ArrayList<String> checkIfRowValuesExistInRepeatingBlock_test_100= new ArrayList<String>();
					   checkIfRowValuesExistInRepeatingBlock_test_100.add("NBS_UI_GA27017");//subsectionId
					   checkIfRowValuesExistInRepeatingBlock_test_100.add("{INV290___50941-4$$Culture,INV291____10828004$$Positive},{INV290____50941-4$$Culture,INV291____410530007$$Not offered}");//simulating values entered from UI"
					   checkIfRowValuesExistInRepeatingBlock_test_100.add("{INV290____50941-4},{INV291____10828004}");//criteria that needs to be met
						*/
						
					   
					   
					String subsectionId = parameters.get(0);
					
					form.getBatchEntryMap().get(subsectionId);
					
			
					
						
						
					String valuesUI = parameters.get(1);
					
					String[] valuesRows = valuesUI.split("____");
					ArrayList<BatchEntry> subsectionElements = new ArrayList<BatchEntry>();
					 BatchEntry batchEntry = new BatchEntry();
					 
					for(int i =0; i<valuesRows.length; i++){
						String[] row = valuesRows[i].split(";");
						 batchEntry = new BatchEntry();

							//Simulating values entered in the repeating block from UI:
						    Map<String, String> answerMap = new HashMap<String, String>();
						    
						    
						for(int j=0; j<row.length; j++){
							
							String[] codeValue = row[j].split(",");
							String code = codeValue[0];
							String value = codeValue[1];
							
							code=code.replace("{","");
							value=value.replace("}","");
						    answerMap.put(code, value);
	  

							 
						}
						 batchEntry.setAnswerMap(answerMap);
					
							subsectionElements.add(batchEntry);
						
					}
					
				
				
					
					
					
					//Simulating values entered in the repeating block from UI:
				/*    Map<String, String> answerMap = new HashMap<String, String>();
				    answerMap.put("INV290", "50941-4$$Culture");
				    answerMap.put("INV291", "10828004$$Positive");
				    BatchEntry batchEntry = new BatchEntry();
				    batchEntry.setAnswerMap(answerMap);
					 
				  	Map<String, String> answerMap2 = new HashMap<String, String>();
					answerMap2.put("INV290", "50941-4$$Culture");
					answerMap2.put("INV291", "410530007$$Not offered");
				    BatchEntry batchEntry2 = new BatchEntry();
				    batchEntry2.setAnswerMap(answerMap2);
			*/
					
					 
					Map<Object, ArrayList<BatchEntry>> batchEntryMap = new HashMap <Object, ArrayList<BatchEntry>>();

					batchEntryMap.put(subsectionId,subsectionElements);
					when(form.getBatchEntryMap()).thenReturn(batchEntryMap);  
			
					
					String expectedValueString =parameters.get(3);
					boolean expectedValue =  Boolean.parseBoolean(expectedValueString);
					
					HashMap<String, ArrayList<String>> codeValues = new HashMap<String, ArrayList<String>>();
				
			
					
					//Criteria to be met:
					
					String valuesCriteria = parameters.get(2);
					String[] valCriteria = valuesCriteria.split(";");
	
					for(int i =0; i<valCriteria.length; i++){
						String[] row = valCriteria[i].split("\\&\\&");
						
						String code = "";
						ArrayList<String> vals1 = new ArrayList<String>();
						for(int j=0; j<row.length; j++){
							
							String[] codeValue = row[j].split(",");
							code = codeValue[0];
							code = code.replace("{","");
							String value = codeValue[1];
							
							String[] values=value.split("#");//Array of values
							for(int k=0; k<values.length;k++){
								String value1 = values[k];
								value1=value1.replace("}","");
								vals1.add(value1);
							
							}
						}
						codeValues.put(code,vals1);
					}
					
				
				
					/*
					
					
					//Validation values
					ArrayList<String> vals1 = new ArrayList<String>();
					vals1.add("50941-4");
					codeValues.put("INV290",vals1);
					
					
					ArrayList<String> vals2 = new ArrayList<String>();
					vals2.add("10828004");
					codeValues.put("INV291",vals2);
*/
	
					boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "checkIfRowValuesExistInRepeatingBlock", form, subsectionId,codeValues);
				
					System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue+"\nActual value: "+actualResult);
					Assert.assertEquals(expectedValue, actualResult);
					System.out.println("PASSED");
					System.out.println("******************* End test case named: checkIfRowValuesExistInRepeatingBlock_test *******************");
				}
			}		
		









@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ValidateRule4_test{
	

//TODO: we might not need this now that we are using inner classes.
/*	private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public ValidateRule4_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {


	   
	   	/*Parameters for validateRule4_test*/
	   
	   ArrayList<String> validateRule4_test_100= new ArrayList<String>();
	   
	   validateRule4_test_100.add("TUB120");
	   validateRule4_test_100.add("10828004");
	   validateRule4_test_100.add("TUB126");
	   validateRule4_test_100.add("10828004");
	   validateRule4_test_100.add("true");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_100.add("true");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_100.add("true");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_100.add("true");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_100.add("TUB122");
	   validateRule4_test_100.add("UNK");
	   validateRule4_test_100.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_100.add("TUB130");
	   validateRule4_test_100.add("UNK");
	   validateRule4_test_100.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_100.add("TUB135");
	   validateRule4_test_100.add("UNK");
	   validateRule4_test_100.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_100.add("true");//expected value

	   
	   
	   
	   ArrayList<String> validateRule4_test_101= new ArrayList<String>();
	   
	   validateRule4_test_101.add("TUB120");
	   validateRule4_test_101.add("10828001");
	   validateRule4_test_101.add("TUB126");
	   validateRule4_test_101.add("10828001");
	   validateRule4_test_101.add("false");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_101.add("false");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_101.add("false");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_101.add("true");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_101.add("TUB122");
	   validateRule4_test_101.add("UNK");
	   validateRule4_test_101.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_101.add("TUB130");
	   validateRule4_test_101.add("UNK");
	   validateRule4_test_101.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_101.add("TUB135");
	   validateRule4_test_101.add("UNK");
	   validateRule4_test_101.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_101.add("true");//expected value

	   
	   
	   
	   
	   
	   ArrayList<String> validateRule4_test_102= new ArrayList<String>();
	   
	   validateRule4_test_102.add("TUB120");
	   validateRule4_test_102.add("10828001");
	   validateRule4_test_102.add("TUB126");
	   validateRule4_test_102.add("10828001");
	   validateRule4_test_102.add("false");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_102.add("false");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_102.add("false");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_102.add("false");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_102.add("TUB122");
	   validateRule4_test_102.add("UNK");
	   validateRule4_test_102.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_102.add("TUB130");
	   validateRule4_test_102.add("UNK");
	   validateRule4_test_102.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_102.add("TUB135");
	   validateRule4_test_102.add("UNK");
	   validateRule4_test_102.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_102.add("false");//expected value

	   
	   
	   
	   
	   
	   
	   ArrayList<String> validateRule4_test_103= new ArrayList<String>();
	   
	   validateRule4_test_103.add("TUB120");
	   validateRule4_test_103.add("10828004");
	   validateRule4_test_103.add("TUB126");
	   validateRule4_test_103.add("10828004");
	   validateRule4_test_103.add("true");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_103.add("true");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_103.add("true");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_103.add("true");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_103.add("TUB122");
	   validateRule4_test_103.add("UNKKKKKK");
	   validateRule4_test_103.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_103.add("TUB130");
	   validateRule4_test_103.add("UNK");
	   validateRule4_test_103.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_103.add("TUB135");
	   validateRule4_test_103.add("UNK");
	   validateRule4_test_103.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_103.add("true");//expected value

	   
	   
	   
	   
	   
	   
	   ArrayList<String> validateRule4_test_104= new ArrayList<String>();
	   
	   validateRule4_test_104.add("TUB120");
	   validateRule4_test_104.add("10828004");
	   validateRule4_test_104.add("TUB126");
	   validateRule4_test_104.add("10828004");
	   validateRule4_test_104.add("true");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_104.add("true");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_104.add("true");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_104.add("true");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_104.add("TUB122");
	   validateRule4_test_104.add("UNK");
	   validateRule4_test_104.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_104.add("TUB130");
	   validateRule4_test_104.add("UNK");
	   validateRule4_test_104.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_104.add("TUB135");
	   validateRule4_test_104.add("UNK");
	   validateRule4_test_104.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_104.add("true");//expected value

	   
	   
	   
	   
	   ArrayList<String> validateRule4_test_105= new ArrayList<String>();
	   
	   validateRule4_test_105.add("TUB120");
	   validateRule4_test_105.add("10828004");
	   validateRule4_test_105.add("TUB126");
	   validateRule4_test_105.add("10828004");
	   validateRule4_test_105.add("true");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_105.add("true");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_105.add("true");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_105.add("true");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_105.add("TUB122");
	   validateRule4_test_105.add("UNKK");
	   validateRule4_test_105.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_105.add("TUB130");
	   validateRule4_test_105.add("UNK");
	   validateRule4_test_105.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_105.add("TUB135");
	   validateRule4_test_105.add("UNK");
	   validateRule4_test_105.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_105.add("false");//expected value

	   
	   
	   
	   ArrayList<String> validateRule4_test_106= new ArrayList<String>();
	   
	   validateRule4_test_106.add("TUB120");
	   validateRule4_test_106.add("10828004");
	   validateRule4_test_106.add("TUB126");
	   validateRule4_test_106.add("10828004");
	   validateRule4_test_106.add("true");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_106.add("true");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_106.add("true");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_106.add("true");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_106.add("TUB122");
	   validateRule4_test_106.add("UNK");
	   validateRule4_test_106.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_106.add("TUB130");
	   validateRule4_test_106.add("UNKK");
	   validateRule4_test_106.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_106.add("TUB135");
	   validateRule4_test_106.add("UNK");
	   validateRule4_test_106.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_106.add("true");//expected value

	   
	   
	   
	   
	   ArrayList<String> validateRule4_test_107= new ArrayList<String>();
	   
	   validateRule4_test_107.add("TUB120");
	   validateRule4_test_107.add("10828004");
	   validateRule4_test_107.add("TUB126");
	   validateRule4_test_107.add("10828004");
	   validateRule4_test_107.add("true");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_107.add("true");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_107.add("true");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_107.add("true");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_107.add("TUB122");
	   validateRule4_test_107.add("UNK");
	   validateRule4_test_107.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_107.add("TUB130");
	   validateRule4_test_107.add("UNKK");
	   validateRule4_test_107.add("false");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_107.add("TUB135");
	   validateRule4_test_107.add("UNK");
	   validateRule4_test_107.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_107.add("false");//expected value

	   
	   
	   
	   
	   ArrayList<String> validateRule4_test_108= new ArrayList<String>();
	   
	   validateRule4_test_108.add("TUB120");
	   validateRule4_test_108.add("10828004");
	   validateRule4_test_108.add("TUB126");
	   validateRule4_test_108.add("10828004");
	   validateRule4_test_108.add("true");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_108.add("true");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_108.add("true");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_108.add("true");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_108.add("TUB122");
	   validateRule4_test_108.add("UNK");
	   validateRule4_test_108.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_108.add("TUB130");
	   validateRule4_test_108.add("UNK");
	   validateRule4_test_108.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_108.add("TUB135");
	   validateRule4_test_108.add("UNKK");
	   validateRule4_test_108.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_108.add("true");//expected value

	   


	   
	   
	   ArrayList<String> validateRule4_test_109= new ArrayList<String>();
	   
	   validateRule4_test_109.add("TUB120");
	   validateRule4_test_109.add("10828004");
	   validateRule4_test_109.add("TUB126");
	   validateRule4_test_109.add("10828004");
	   validateRule4_test_109.add("true");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_109.add("true");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_109.add("true");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_109.add("true");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_109.add("TUB122");
	   validateRule4_test_109.add("UNK");
	   validateRule4_test_109.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_109.add("TUB130");
	   validateRule4_test_109.add("UNK");
	   validateRule4_test_109.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_109.add("TUB135");
	   validateRule4_test_109.add("UNKK");
	   validateRule4_test_109.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_109.add("false");//expected value

	   
	   
	   
	   
	   
	   //Only repeatings are true
	   ArrayList<String> validateRule4_test_110= new ArrayList<String>();
	   
	   validateRule4_test_110.add("TUB120");
	   validateRule4_test_110.add("108280041");
	   validateRule4_test_110.add("TUB126");
	   validateRule4_test_110.add("108280041");
	   validateRule4_test_110.add("true");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_110.add("true");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_110.add("true");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_110.add("true");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_110.add("TUB122");
	   validateRule4_test_110.add("UNK1");
	   validateRule4_test_110.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_110.add("TUB130");
	   validateRule4_test_110.add("UNK1");
	   validateRule4_test_110.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_110.add("TUB135");
	   validateRule4_test_110.add("UNK1");
	   validateRule4_test_110.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_110.add("true");//expected value

	   
	   
	   
	   
	   
	   
	   //Only discreate are true
	   ArrayList<String> validateRule4_test_111= new ArrayList<String>();
	   
	   validateRule4_test_111.add("TUB120");
	   validateRule4_test_111.add("10828004");
	   validateRule4_test_111.add("TUB126");
	   validateRule4_test_111.add("10828004");
	   validateRule4_test_111.add("false");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_111.add("false");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_111.add("false");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_111.add("false");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_111.add("TUB122");
	   validateRule4_test_111.add("UNK");
	   validateRule4_test_111.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_111.add("TUB130");
	   validateRule4_test_111.add("UNK");
	   validateRule4_test_111.add("false");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_111.add("TUB135");
	   validateRule4_test_111.add("UNK");
	   validateRule4_test_111.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_111.add("true");//expected value

	   
	   
	   
	   
	   
	   
	   ArrayList<String> validateRule4_test_112= new ArrayList<String>();
	   
	   validateRule4_test_112.add("TUB120");
	   validateRule4_test_112.add("108280041");
	   validateRule4_test_112.add("TUB126");
	   validateRule4_test_112.add("108280041");
	   validateRule4_test_112.add("false");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_112.add("true");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_112.add("false");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_112.add("false");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_112.add("TUB122");
	   validateRule4_test_112.add("UNK");
	   validateRule4_test_112.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_112.add("TUB130");
	   validateRule4_test_112.add("UNKK");
	   validateRule4_test_112.add("false");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_112.add("TUB135");
	   validateRule4_test_112.add("UNK");
	   validateRule4_test_112.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_112.add("false");//expected value

	   
	   
	   
	   

	   ArrayList<String> validateRule4_test_113= new ArrayList<String>();
	   
	   validateRule4_test_113.add("TUB120");
	   validateRule4_test_113.add("");
	   validateRule4_test_113.add("TUB126");
	   validateRule4_test_113.add("");
	   validateRule4_test_113.add("true");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_113.add("true");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_113.add("true");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_113.add("true");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_113.add("TUB122");
	   validateRule4_test_113.add("");
	   validateRule4_test_113.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_113.add("TUB130");
	   validateRule4_test_113.add("");
	   validateRule4_test_113.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_113.add("TUB135");
	   validateRule4_test_113.add("");
	   validateRule4_test_113.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_113.add("true");//expected value

	   
	   
	   


	   ArrayList<String> validateRule4_test_114= new ArrayList<String>();
	   
	   validateRule4_test_114.add("TUB120");
	   validateRule4_test_114.add("");
	   validateRule4_test_114.add("TUB126");
	   validateRule4_test_114.add("");
	   validateRule4_test_114.add("false");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_114.add("false");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_114.add("false");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_114.add("false");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_114.add("TUB122");
	   validateRule4_test_114.add("");
	   validateRule4_test_114.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_114.add("TUB130");
	   validateRule4_test_114.add("");
	   validateRule4_test_114.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_114.add("TUB135");
	   validateRule4_test_114.add("");
	   validateRule4_test_114.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_114.add("false");//expected value

	   
	   
	     


	   ArrayList<String> validateRule4_test_115= new ArrayList<String>();
	   
	   validateRule4_test_115.add("TUB120");
	   validateRule4_test_115.add(null);
	   validateRule4_test_115.add("TUB126");
	   validateRule4_test_115.add(null);
	   validateRule4_test_115.add("false");//Smear: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_115.add("false");//Cytology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_115.add("false");//Pathology: expected value to be return by method checking if row exists in the repeating block
	   validateRule4_test_115.add("false");//PathologyCytology: expected value to be return by method checking if row exists in the repeating block
		  
	   //AND
	   
	   
	   validateRule4_test_115.add("TUB122");
	   validateRule4_test_115.add(null);
	   validateRule4_test_115.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule4_test_115.add("TUB130");
	   validateRule4_test_115.add(null);
	   validateRule4_test_115.add("true");//CultureNotContain: expected value to be return by method checking if row exists in the repeating block
		
	   //AND
	   
	   validateRule4_test_115.add("TUB135");
	   validateRule4_test_115.add(null);
	   validateRule4_test_115.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
	 
	   //Expected value
	   validateRule4_test_115.add("false");//expected value

	   
	   
	     
	   
	   
	   
	   
	   int it = 0;
		String testName = "validateRule4_test";
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule4_test
  		    
    	
    		  {testName+"_"+it++,validateRule4_test_100},
    		  {testName+"_"+it++,validateRule4_test_101},
    		  {testName+"_"+it++,validateRule4_test_102},
    		  {testName+"_"+it++,validateRule4_test_103},
    		  {testName+"_"+it++,validateRule4_test_104},
    		  {testName+"_"+it++,validateRule4_test_105},
    		  {testName+"_"+it++,validateRule4_test_106},
    		  {testName+"_"+it++,validateRule4_test_107},
    		  {testName+"_"+it++,validateRule4_test_108},
    		  {testName+"_"+it++,validateRule4_test_109},
    		  {testName+"_"+it++,validateRule4_test_110},
    		  {testName+"_"+it++,validateRule4_test_111},
    		  {testName+"_"+it++,validateRule4_test_112},
    		  {testName+"_"+it++,validateRule4_test_113},
    		  {testName+"_"+it++,validateRule4_test_114},
    		  {testName+"_"+it++,validateRule4_test_115},
    		  
    		  
    		  
    		  
    		  
    		  
    		  
    		  
    		
    		  

    		 
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

		@Test
		public void validateRule4_test() throws Exception{
				

					System.out.println("******************* Starting test case named: validateRule4_test *******************");
				
					String subsectionId = "NBS_UI_GA27017";
					
					//This will simulate the values selected from the UI
					
				
					
				
					//We don't need to change this values, this is just to be able to make the internal call that will be mocked.

					HashMap <String, ArrayList<String>> codeValuesRule4_1 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values4_1_1 = new ArrayList<String>();
					ArrayList<String> values4_1_2 = new ArrayList<String>();
					

					values4_1_1.add("20431-3");//Smear
					values4_1_2.add("10828004");//Positive
					
					codeValuesRule4_1.put("INV290",values4_1_1);
					codeValuesRule4_1.put("INV291",values4_1_2);

					HashMap <String, ArrayList<String>> codeValuesRule4_2 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values4_2_1 = new ArrayList<String>();
					ArrayList<String> values4_2_2 = new ArrayList<String>();
					

					values4_2_1.add("10525-4");//Cytology
					values4_2_2.add("10828004");//Positive
					
					codeValuesRule4_2.put("INV290",values4_2_1);
					codeValuesRule4_2.put("INV291",values4_2_2);

					HashMap <String, ArrayList<String>> codeValuesRule4_3 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values4_3_1 = new ArrayList<String>();
					ArrayList<String> values4_3_2 = new ArrayList<String>();
					

					values4_3_1.add("50595-8");//Pathology
					values4_3_2.add("10828004");//Positive
					
					codeValuesRule4_3.put("INV290",values4_3_1);
					codeValuesRule4_3.put("INV291",values4_3_2);

					HashMap <String, ArrayList<String>> codeValuesRule4_4 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values4_4_1 = new ArrayList<String>();
					ArrayList<String> values4_4_2 = new ArrayList<String>();
					

					values4_4_1.add("LAB719");//Pathology/Cytology
					values4_4_2.add("10828004");//Positive
					
					codeValuesRule4_4.put("INV290",values4_4_1);
					codeValuesRule4_4.put("INV291",values4_4_2);
					
					HashMap <String, ArrayList<String>> codeValuesRule4_5 = new HashMap<String, ArrayList<String>>();
					
					
					ArrayList<String> values4_5_1 = new ArrayList<String>();
					ArrayList<String> values4_5_2= new ArrayList<String>();
					ArrayList<String> values4_5_3= new ArrayList<String>();
					

					values4_5_1.add("50941-4");//Culture
					
					values4_5_2.add("385660001");
					values4_5_2.add("UNK");
					values4_5_2.add("82334004");
					values4_5_2.add("443390004");
					values4_5_2.add("410530007");
					values4_5_2.add("PHC2092");
					
					values4_5_3.add("119334006");//Sputum
					
					codeValuesRule4_5.put("INV290",values4_5_1);
					codeValuesRule4_5.put("INV291",values4_5_2);
					codeValuesRule4_5.put("LAB165",values4_5_3);//Specimen Source Site

					
					HashMap <String, ArrayList<String>> codeValuesRule4_7 = new HashMap<String, ArrayList<String>>();
					
					
					ArrayList<String> values4_7_1 = new ArrayList<String>();
					ArrayList<String> values4_7_2= new ArrayList<String>();
					ArrayList<String> values4_7_3= new ArrayList<String>();
					

					values4_7_1.add("50941-4");//Culture
					
					values4_7_2.add("385660001");
					values4_7_2.add("UNK");
					values4_7_2.add("82334004");
					values4_7_2.add("443390004");
					values4_7_2.add("410530007");
					values4_7_2.add("PHC2092");
					
					values4_7_3.add("119334006");//Sputum
					
					codeValuesRule4_7.put("INV290",values4_7_1);
					codeValuesRule4_7.put("INV291",values4_7_2);
					codeValuesRule4_7.put("NOT_CONTAIN_LAB165",values4_7_3);//Specimen Source Site


					HashMap <String, ArrayList<String>> codeValuesRule4_5_1 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values4_5_1_1 = new ArrayList<String>();
					

					values4_5_1_1.add("50941-4");//Culture		
					codeValuesRule4_5_1.put("INV290",values4_5_1_1);

					HashMap <String, ArrayList<String>> codeValuesRule4_6 = new HashMap<String, ArrayList<String>>();
					
					
					ArrayList<String> values4_6_1 = new ArrayList<String>();
					ArrayList<String> values4_6_2= new ArrayList<String>();
					

					values4_6_1.add("LAB673");//Culture
					
					values4_6_2.add("385660001");
					values4_6_2.add("UNK");
					values4_6_2.add("82334004");
					values4_6_2.add("443390004");
					values4_6_2.add("410530007");
					values4_6_2.add("PHC2092");
					
					codeValuesRule4_6.put("INV290",values4_6_1);
					codeValuesRule4_6.put("INV291",values4_6_2);

					HashMap <String, ArrayList<String>> codeValuesRule4_6_1 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values4_6_1_1 = new ArrayList<String>();
					

					values4_6_1_1.add("LAB673");//Nucleic Acid Amplification Test	
					codeValuesRule4_6_1.put("INV290",values4_6_1_1);
					
					
					
					
					
					/***************/
					
					//Values to be passed as parameters
					

					
					String TUB120 = parameters.get(0);
					String TUB120Value =  parameters.get(1);
					String TUB126 =  parameters.get(2);
					String TUB126Value = parameters.get(3);
					boolean existsInRepeatingBlockSmear =  Boolean.parseBoolean(parameters.get(4));
					boolean existsInRepeatingBlockCytology =  Boolean.parseBoolean(parameters.get(5));
					boolean existsInRepeatingBlockPathology=  Boolean.parseBoolean(parameters.get(6));
					boolean existsInRepeatingBlockPathologyCytology =  Boolean.parseBoolean(parameters.get(7));
				
					
					
					String TUB122 =  parameters.get(8);
					String TUB122Value =  parameters.get(9);
					boolean existsInRepeatingBlockCulture =  Boolean.parseBoolean(parameters.get(10));
					
					
					
					String TUB130 =  parameters.get(11);
					String TUB130Value =  parameters.get(12);
					boolean existsInRepeatingBlockCultureNotContain =  Boolean.parseBoolean(parameters.get(13));
					
					String TUB135 =  parameters.get(14);
					String TUB135Value =  parameters.get(15);
					boolean existsInRepeatingBlockNAA =  Boolean.parseBoolean(parameters.get(16));
					
					
					
			

			
					
					boolean expectedValue =  Boolean.parseBoolean(parameters.get(17));
					
					
					
					
					PowerMockito.doReturn(TUB120Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB120);
					PowerMockito.doReturn(TUB126Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB126);
					PowerMockito.doReturn(TUB122Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB122);
					PowerMockito.doReturn(TUB130Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB130);
					PowerMockito.doReturn(TUB135Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB135);
					
					//We could use this, for simplicity
					//PowerMockito.doReturn(existsInRepeatingBlock).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,Mockito.anyMapOf(String.class, ArrayList.class));
					//But if we want to have control over which values are in the repeating block and which ones are not, so we can test different scenarios, then it is better to do this
					PowerMockito.doReturn(existsInRepeatingBlockSmear).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule4_1);
					PowerMockito.doReturn(existsInRepeatingBlockCytology).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule4_2);
					PowerMockito.doReturn(existsInRepeatingBlockPathology).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule4_3);
					PowerMockito.doReturn(existsInRepeatingBlockPathologyCytology).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule4_4);
					PowerMockito.doReturn(existsInRepeatingBlockCulture).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule4_5);
					PowerMockito.doReturn(existsInRepeatingBlockCultureNotContain).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule4_7);
					PowerMockito.doReturn(existsInRepeatingBlockNAA).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule4_6);
					
					
					
					
					boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "validateRule4", form);
				
					System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue+"\nActual value: "+actualResult);
					Assert.assertEquals(expectedValue, actualResult);
					System.out.println("PASSED");
					System.out.println("******************* End test case named: validateRule4_test *******************");
				}	
	 }
















@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ValidateRule5_part1_test{
	

//TODO: we might not need this now that we are using inner classes.
/*	private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public ValidateRule5_part1_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {


	   
	   	/*Parameters for validateRule5_test_100*/
	   
	   ArrayList<String> validateRule5_test_100= new ArrayList<String>();
	   
	   validateRule5_test_100.add("INV1133");
	   validateRule5_test_100.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_100.add("TUB122");
	   validateRule5_test_100.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_100.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_100.add("TUB130");
	   validateRule5_test_100.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_100.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_100.add("TUB135");
	   validateRule5_test_100.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_100.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_100.add("TUB141");
	   validateRule5_test_100.add("PHC1873");
	   validateRule5_test_100.add("TUB144");
	   validateRule5_test_100.add("PHC1873");
	   
	   validateRule5_test_100.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_100.add("TUB147");
	   validateRule5_test_100.add("10828004");
	   validateRule5_test_100.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_100.add("TUB150");
	   validateRule5_test_100.add("10828004");
	   validateRule5_test_100.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_100.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_100.add("true");
	   
	   
	   
	   
	   

	   	/*Parameters for validateRule5_test_101*/
	   
	   ArrayList<String> validateRule5_test_101= new ArrayList<String>();
	   
	   validateRule5_test_101.add("INV1133");
	   validateRule5_test_101.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_101.add("TUB122");
	   validateRule5_test_101.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_101.add("false");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_101.add("TUB130");
	   validateRule5_test_101.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_101.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_101.add("TUB135");
	   validateRule5_test_101.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_101.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_101.add("TUB141");
	   validateRule5_test_101.add("PHC1873");
	   validateRule5_test_101.add("TUB144");
	   validateRule5_test_101.add("PHC1873");
	   
	   validateRule5_test_101.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_101.add("TUB147");
	   validateRule5_test_101.add("10828004");
	   validateRule5_test_101.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_101.add("TUB150");
	   validateRule5_test_101.add("10828004");
	   validateRule5_test_101.add("false");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_101.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_101.add("true");
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_102*/
	   
	   ArrayList<String> validateRule5_test_102= new ArrayList<String>();
	   
	   validateRule5_test_102.add("INV1133");
	   validateRule5_test_102.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_102.add("TUB122");
	   validateRule5_test_102.add("2603850091");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_102.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_102.add("TUB130");
	   validateRule5_test_102.add("2603850091");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_102.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_102.add("TUB135");
	   validateRule5_test_102.add("UNK1");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_102.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_102.add("TUB141");
	   validateRule5_test_102.add("PHC18731");
	   validateRule5_test_102.add("TUB144");
	   validateRule5_test_102.add("PHC18731");
	   
	   validateRule5_test_102.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_102.add("TUB147");
	   validateRule5_test_102.add("108280041");
	   validateRule5_test_102.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_102.add("TUB150");
	   validateRule5_test_102.add("108280041");
	   validateRule5_test_102.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_102.add("true");//2 criterias met

	   //Expected value
	   validateRule5_test_102.add("true");
	   
	   
	   
	   
	   
	   
	   
	   
	/*Parameters for validateRule5_test_103*/
	   
	   ArrayList<String> validateRule5_test_103= new ArrayList<String>();
	   
	   validateRule5_test_103.add("INV1133");
	   validateRule5_test_103.add("false");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_103.add("TUB122");
	   validateRule5_test_103.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_103.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_103.add("TUB130");
	   validateRule5_test_103.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_103.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_103.add("TUB135");
	   validateRule5_test_103.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_103.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_103.add("TUB141");
	   validateRule5_test_103.add("PHC1873");
	   validateRule5_test_103.add("TUB144");
	   validateRule5_test_103.add("PHC1873");
	   
	   validateRule5_test_103.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_103.add("TUB147");
	   validateRule5_test_103.add("10828004");
	   validateRule5_test_103.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_103.add("TUB150");
	   validateRule5_test_103.add("10828004");
	   validateRule5_test_103.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_103.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_103.add("false");
	   
	   
	   
	   

	   	/*Parameters for validateRule5_test_104*/
	   
	   ArrayList<String> validateRule5_test_104= new ArrayList<String>();
	   
	   validateRule5_test_104.add("INV1133");
	   validateRule5_test_104.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_104.add("TUB122");
	   validateRule5_test_104.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_104.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_104.add("TUB130");
	   validateRule5_test_104.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_104.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_104.add("TUB135");
	   validateRule5_test_104.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_104.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_104.add("TUB141");
	   validateRule5_test_104.add("PHC1873");
	   validateRule5_test_104.add("TUB144");
	   validateRule5_test_104.add("PHC1873");
	   
	   validateRule5_test_104.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_104.add("TUB147");
	   validateRule5_test_104.add("10828004");
	   validateRule5_test_104.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_104.add("TUB150");
	   validateRule5_test_104.add("10828004");
	   validateRule5_test_104.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_104.add("false");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_104.add("false");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_105*/
	   
	   ArrayList<String> validateRule5_test_105= new ArrayList<String>();
	   
	   validateRule5_test_105.add("INV1133");
	   validateRule5_test_105.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_105.add("TUB122");
	   validateRule5_test_105.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_105.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_105.add("TUB130");
	   validateRule5_test_105.add("260385001");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_105.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_105.add("TUB135");
	   validateRule5_test_105.add("UNK1");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_105.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_105.add("TUB141");
	   validateRule5_test_105.add("PHC1873");
	   validateRule5_test_105.add("TUB144");
	   validateRule5_test_105.add("PHC1873");
	   
	   validateRule5_test_105.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_105.add("TUB147");
	   validateRule5_test_105.add("10828004");
	   validateRule5_test_105.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_105.add("TUB150");
	   validateRule5_test_105.add("10828004");
	   validateRule5_test_105.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_105.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_105.add("false");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_106*/
	   
	   ArrayList<String> validateRule5_test_106= new ArrayList<String>();
	   
	   validateRule5_test_106.add("INV1133");
	   validateRule5_test_106.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_106.add("TUB122");
	   validateRule5_test_106.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_106.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_106.add("TUB130");
	   validateRule5_test_106.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_106.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_106.add("TUB135");
	   validateRule5_test_106.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_106.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_106.add("TUB141");
	   validateRule5_test_106.add("PHC1871");
	   validateRule5_test_106.add("TUB144");
	   validateRule5_test_106.add("PHC1871");
	   
	   validateRule5_test_106.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_106.add("TUB147");
	   validateRule5_test_106.add("10828004");
	   validateRule5_test_106.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_106.add("TUB150");
	   validateRule5_test_106.add("10828004");
	   validateRule5_test_106.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_106.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_106.add("true");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_107*/
	   
	   ArrayList<String> validateRule5_test_107= new ArrayList<String>();
	   
	   validateRule5_test_107.add("INV1133");
	   validateRule5_test_107.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_107.add("TUB122");
	   validateRule5_test_107.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_107.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_107.add("TUB130");
	   validateRule5_test_107.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_107.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_107.add("TUB135");
	   validateRule5_test_107.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_107.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_107.add("TUB141");
	   validateRule5_test_107.add("PHC1873");
	   validateRule5_test_107.add("TUB144");
	   validateRule5_test_107.add("PHC1873");
	   
	   validateRule5_test_107.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_107.add("TUB147");
	   validateRule5_test_107.add("10828001");
	   validateRule5_test_107.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_107.add("TUB150");
	   validateRule5_test_107.add("10828001");
	   validateRule5_test_107.add("false");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_107.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_107.add("false");
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_108*/
	   
	   ArrayList<String> validateRule5_test_108= new ArrayList<String>();
	   
	   validateRule5_test_108.add("INV1133");
	   validateRule5_test_108.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_108.add("TUB122");
	   validateRule5_test_108.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_108.add("false");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_108.add("TUB130");
	   validateRule5_test_108.add("260385001");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_108.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_108.add("TUB135");
	   validateRule5_test_108.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_108.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_108.add("TUB141");
	   validateRule5_test_108.add("PHC1871");
	   validateRule5_test_108.add("TUB144");
	   validateRule5_test_108.add("PHC1873");
	   
	   validateRule5_test_108.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_108.add("TUB147");
	   validateRule5_test_108.add("10828004");
	   validateRule5_test_108.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_108.add("TUB150");
	   validateRule5_test_108.add("10828001");
	   validateRule5_test_108.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_108.add("false");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_108.add("false");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_109*/
	   
	   ArrayList<String> validateRule5_test_109= new ArrayList<String>();
	   
	   validateRule5_test_109.add("INV1133");
	   validateRule5_test_109.add("false");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_109.add("TUB122");
	   validateRule5_test_109.add("260385001");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_109.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_109.add("TUB130");
	   validateRule5_test_109.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_109.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_109.add("TUB135");
	   validateRule5_test_109.add("UNK1");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_109.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_109.add("TUB141");
	   validateRule5_test_109.add("PHC1873");
	   validateRule5_test_109.add("TUB144");
	   validateRule5_test_109.add("PHC1873");
	   
	   validateRule5_test_109.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_109.add("TUB147");
	   validateRule5_test_109.add("108280041");
	   validateRule5_test_109.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_109.add("TUB150");
	   validateRule5_test_109.add("10828004");
	   validateRule5_test_109.add("false");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_109.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_109.add("false");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_110*/
	   
	   ArrayList<String> validateRule5_test_110= new ArrayList<String>();
	   
	   validateRule5_test_110.add("INV1133");
	   validateRule5_test_110.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_110.add("TUB122");
	   validateRule5_test_110.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_110.add("false");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_110.add("TUB130");
	   validateRule5_test_110.add("2603850091");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_110.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_110.add("TUB135");
	   validateRule5_test_110.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_110.add("falkse");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_110.add("TUB141");
	   validateRule5_test_110.add("PHC18731");
	   validateRule5_test_110.add("TUB144");
	   validateRule5_test_110.add("PHC18731");
	   
	   validateRule5_test_110.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_110.add("TUB147");
	   validateRule5_test_110.add("10828004");
	   validateRule5_test_110.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_110.add("TUB150");
	   validateRule5_test_110.add("108280041");
	   validateRule5_test_110.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_110.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_110.add("true");
	   
	   
	   
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_111*/
	   
	   ArrayList<String> validateRule5_test_111= new ArrayList<String>();
	   
	   validateRule5_test_111.add("INV1133");
	   validateRule5_test_111.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_111.add("TUB122");
	   validateRule5_test_111.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_111.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_111.add("TUB130");
	   validateRule5_test_111.add("");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_111.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_111.add("TUB135");
	   validateRule5_test_111.add("");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_111.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_111.add("TUB141");
	   validateRule5_test_111.add("PHC1873");
	   validateRule5_test_111.add("TUB144");
	   validateRule5_test_111.add("");
	   
	   validateRule5_test_111.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_111.add("TUB147");
	   validateRule5_test_111.add("");
	   validateRule5_test_111.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_111.add("TUB150");
	   validateRule5_test_111.add("");
	   validateRule5_test_111.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_111.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_111.add("true");
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_112*/
	   
	   ArrayList<String> validateRule5_test_112= new ArrayList<String>();
	   
	   validateRule5_test_112.add("INV1133");
	   validateRule5_test_112.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_112.add("TUB122");
	   validateRule5_test_112.add("2603850091");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_112.add("false");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_112.add("TUB130");
	   validateRule5_test_112.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_112.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_112.add("TUB135");
	   validateRule5_test_112.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_112.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_112.add("TUB141");
	   validateRule5_test_112.add("PHC1873");
	   validateRule5_test_112.add("TUB144");
	   validateRule5_test_112.add("PHC1873");
	   
	   validateRule5_test_112.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_112.add("TUB147");
	   validateRule5_test_112.add("10828004");
	   validateRule5_test_112.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_112.add("TUB150");
	   validateRule5_test_112.add("10828004");
	   validateRule5_test_112.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_112.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_112.add("false");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_113*/
	   
	   ArrayList<String> validateRule5_test_113= new ArrayList<String>();
	   
	   validateRule5_test_113.add("INV1133");
	   validateRule5_test_113.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_113.add("TUB122");
	   validateRule5_test_113.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_113.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_113.add("TUB130");
	   validateRule5_test_113.add("1");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_113.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_113.add("TUB135");
	   validateRule5_test_113.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_113.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_113.add("TUB141");
	   validateRule5_test_113.add("PHC1873");
	   validateRule5_test_113.add("TUB144");
	   validateRule5_test_113.add("PHC1873");
	   
	   validateRule5_test_113.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_113.add("TUB147");
	   validateRule5_test_113.add("10828004");
	   validateRule5_test_113.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_113.add("TUB150");
	   validateRule5_test_113.add("10828004");
	   validateRule5_test_113.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_113.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_113.add("false");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_114*/
	   
	   ArrayList<String> validateRule5_test_114= new ArrayList<String>();
	   
	   validateRule5_test_114.add("INV1133");
	   validateRule5_test_114.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_114.add("TUB122");
	   validateRule5_test_114.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_114.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_114.add("TUB130");
	   validateRule5_test_114.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_114.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_114.add("TUB135");
	   validateRule5_test_114.add("1");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_114.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_114.add("TUB141");
	   validateRule5_test_114.add("PHC1873");
	   validateRule5_test_114.add("TUB144");
	   validateRule5_test_114.add("PHC1873");
	   
	   validateRule5_test_114.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_114.add("TUB147");
	   validateRule5_test_114.add("10828004");
	   validateRule5_test_114.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_114.add("TUB150");
	   validateRule5_test_114.add("10828004");
	   validateRule5_test_114.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_114.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_114.add("false");
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_115*/
	   
	   ArrayList<String> validateRule5_test_115= new ArrayList<String>();
	   
	   validateRule5_test_115.add("INV1133");
	   validateRule5_test_115.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_115.add("TUB122");
	   validateRule5_test_115.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_115.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_115.add("TUB130");
	   validateRule5_test_115.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_115.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_115.add("TUB135");
	   validateRule5_test_115.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_115.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_115.add("TUB141");
	   validateRule5_test_115.add("1");
	   validateRule5_test_115.add("TUB144");
	   validateRule5_test_115.add("1");
	   
	   validateRule5_test_115.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_115.add("TUB147");
	   validateRule5_test_115.add("10828004");
	   validateRule5_test_115.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_115.add("TUB150");
	   validateRule5_test_115.add("10828004");
	   validateRule5_test_115.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_115.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_115.add("false");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_116*/
	   
	   ArrayList<String> validateRule5_test_116= new ArrayList<String>();
	   
	   validateRule5_test_116.add("INV1133");
	   validateRule5_test_116.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_116.add("TUB122");
	   validateRule5_test_116.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_116.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_116.add("TUB130");
	   validateRule5_test_116.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_116.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_116.add("TUB135");
	   validateRule5_test_116.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_116.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_116.add("TUB141");
	   validateRule5_test_116.add("PHC1873");
	   validateRule5_test_116.add("TUB144");
	   validateRule5_test_116.add("PHC1873");
	   
	   validateRule5_test_116.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_116.add("TUB147");
	   validateRule5_test_116.add("1");
	   validateRule5_test_116.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_116.add("TUB150");
	   validateRule5_test_116.add("1");
	   validateRule5_test_116.add("false");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_116.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_116.add("false");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_117*/
	   
	   ArrayList<String> validateRule5_test_117= new ArrayList<String>();
	   
	   validateRule5_test_117.add("INV1133");
	   validateRule5_test_117.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_117.add("TUB122");
	   validateRule5_test_117.add(null);//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_117.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_117.add("TUB130");
	   validateRule5_test_117.add(null);//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_117.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_117.add("TUB135");
	   validateRule5_test_117.add(null);//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_117.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_117.add("TUB141");
	   validateRule5_test_117.add("PHC1873");
	   validateRule5_test_117.add("TUB144");
	   validateRule5_test_117.add(null);
	   
	   validateRule5_test_117.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_117.add("TUB147");
	   validateRule5_test_117.add(null);
	   validateRule5_test_117.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_117.add("TUB150");
	   validateRule5_test_117.add(null);
	   validateRule5_test_117.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_117.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_117.add("true");
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_118*/
	   
	   ArrayList<String> validateRule5_test_118= new ArrayList<String>();
	   
	   validateRule5_test_118.add("INV1133");
	   validateRule5_test_118.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_118.add("TUB122");
	   validateRule5_test_118.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_118.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_118.add("TUB130");
	   validateRule5_test_118.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_118.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_118.add("TUB135");
	   validateRule5_test_118.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_118.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_118.add("TUB141");
	   validateRule5_test_118.add("11111");
	   validateRule5_test_118.add("TUB144");
	   validateRule5_test_118.add("11111");
	   
	   validateRule5_test_118.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_118.add("TUB147");
	   validateRule5_test_118.add("10828004");
	   validateRule5_test_118.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_118.add("TUB150");
	   validateRule5_test_118.add("10828004");
	   validateRule5_test_118.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_118.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_118.add("true");
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_119*/
	   
	   ArrayList<String> validateRule5_test_119= new ArrayList<String>();
	   
	   validateRule5_test_119.add("INV1133");
	   validateRule5_test_119.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_119.add("TUB122");
	   validateRule5_test_119.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_119.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_119.add("TUB130");
	   validateRule5_test_119.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_119.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_119.add("TUB135");
	   validateRule5_test_119.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_119.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_119.add("TUB141");
	   validateRule5_test_119.add("PHC1873");
	   validateRule5_test_119.add("TUB144");
	   validateRule5_test_119.add("PHC1873");
	   
	   validateRule5_test_119.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_119.add("TUB147");
	   validateRule5_test_119.add("10828004");
	   validateRule5_test_119.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_119.add("TUB150");
	   validateRule5_test_119.add("10828004");
	   validateRule5_test_119.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_119.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_119.add("true");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_120*/
	   
	   ArrayList<String> validateRule5_test_120= new ArrayList<String>();
	   
	   validateRule5_test_120.add("INV1133");
	   validateRule5_test_120.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_120.add("TUB122");
	   validateRule5_test_120.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_120.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_120.add("TUB130");
	   validateRule5_test_120.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_120.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_120.add("TUB135");
	   validateRule5_test_120.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_120.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_120.add("TUB141");
	   validateRule5_test_120.add("PHC1873");
	   validateRule5_test_120.add("TUB144");
	   validateRule5_test_120.add("PHC1873");
	   
	   validateRule5_test_120.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_120.add("TUB147");
	   validateRule5_test_120.add("10828004");
	   validateRule5_test_120.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_120.add("TUB150");
	   validateRule5_test_120.add("1");
	   validateRule5_test_120.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_120.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_120.add("true");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_121*/
	   
	   ArrayList<String> validateRule5_test_121= new ArrayList<String>();
	   
	   validateRule5_test_121.add("INV1133");
	   validateRule5_test_121.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_121.add("TUB122");
	   validateRule5_test_121.add("385660001");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_121.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_121.add("TUB130");
	   validateRule5_test_121.add("385660001");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_121.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_121.add("TUB135");
	   validateRule5_test_121.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_121.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_121.add("TUB141");
	   validateRule5_test_121.add("PHC1873");
	   validateRule5_test_121.add("TUB144");
	   validateRule5_test_121.add("PHC1873");
	   
	   validateRule5_test_121.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_121.add("TUB147");
	   validateRule5_test_121.add("10828004");
	   validateRule5_test_121.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_121.add("TUB150");
	   validateRule5_test_121.add("10828004");
	   validateRule5_test_121.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_121.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_121.add("true");
	   
	   

	   
	   	/*Parameters for validateRule5_test_122*/
	/*   
	  ArrayList<String> validateRule5_test_122= new ArrayList<String>();
	   
	   validateRule5_test_122.add("INV1133");
	   validateRule5_test_122.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_122.add("TUB122");
	   validateRule5_test_122.add("UNK");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_122.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_122.add("TUB130");
	   validateRule5_test_122.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_122.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_122.add("TUB135");
	   validateRule5_test_122.add("385660001");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_122.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_122.add("TUB141");
	   validateRule5_test_122.add("PHC1873");
	   validateRule5_test_122.add("TUB144");
	   validateRule5_test_122.add("PHC1873");
	   
	   validateRule5_test_122.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_122.add("TUB147");
	   validateRule5_test_122.add("10828004");
	   validateRule5_test_122.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_122.add("TUB150");
	   validateRule5_test_122.add("10828004");
	   validateRule5_test_122.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_122.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_122.add("true");
	   
	   */
	   

	   
	   	/*Parameters for validateRule5_test_123*/
	   /*
	   ArrayList<String> validateRule5_test_123= new ArrayList<String>();
	   
	   validateRule5_test_123.add("INV1133");
	   validateRule5_test_123.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_123.add("TUB122");
	   validateRule5_test_123.add("82334004");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_123.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_123.add("TUB130");
	   validateRule5_test_123.add("82334004");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_123.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_123.add("TUB135");
	   validateRule5_test_123.add("82334004");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_123.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_123.add("TUB141");
	   validateRule5_test_123.add("PHC1873");
	   validateRule5_test_123.add("TUB144");
	   validateRule5_test_123.add("PHC1873");
	   
	   validateRule5_test_123.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_123.add("TUB147");
	   validateRule5_test_123.add("10828004");
	   validateRule5_test_123.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_123.add("TUB150");
	   validateRule5_test_123.add("10828004");
	   validateRule5_test_123.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_123.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_123.add("true");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_124*/
	  /*
	   ArrayList<String> validateRule5_test_124= new ArrayList<String>();
	   
	   validateRule5_test_124.add("INV1133");
	   validateRule5_test_124.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_124.add("TUB122");
	   validateRule5_test_124.add("443390004");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_124.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_124.add("TUB130");
	   validateRule5_test_124.add("443390004");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_124.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_124.add("TUB135");
	   validateRule5_test_124.add("443390004");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_124.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_124.add("TUB141");
	   validateRule5_test_124.add("PHC1873");
	   validateRule5_test_124.add("TUB144");
	   validateRule5_test_124.add("PHC1873");
	   
	   validateRule5_test_124.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_124.add("TUB147");
	   validateRule5_test_124.add("10828004");
	   validateRule5_test_124.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_124.add("TUB150");
	   validateRule5_test_124.add("10828004");
	   validateRule5_test_124.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_124.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_124.add("true");
	   
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_125*/
	/*   
	   ArrayList<String> validateRule5_test_125= new ArrayList<String>();
	   
	   validateRule5_test_125.add("INV1133");
	   validateRule5_test_125.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_125.add("TUB122");
	   validateRule5_test_125.add("410530007");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_125.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_125.add("TUB130");
	   validateRule5_test_125.add("410530007");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_125.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_125.add("TUB135");
	   validateRule5_test_125.add("410530007");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_125.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_125.add("TUB141");
	   validateRule5_test_125.add("PHC1873");
	   validateRule5_test_125.add("TUB144");
	   validateRule5_test_125.add("PHC1873");
	   
	   validateRule5_test_125.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_125.add("TUB147");
	   validateRule5_test_125.add("10828004");
	   validateRule5_test_125.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_125.add("TUB150");
	   validateRule5_test_125.add("10828004");
	   validateRule5_test_125.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_125.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_125.add("true");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_test_126*/
	   
	 /*  ArrayList<String> validateRule5_test_126= new ArrayList<String>();
	   
	   validateRule5_test_126.add("INV1133");
	   validateRule5_test_126.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_126.add("TUB122");
	   validateRule5_test_126.add("PHC2092");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_126.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_126.add("TUB130");
	   validateRule5_test_126.add("PHC2092");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_126.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_126.add("TUB135");
	   validateRule5_test_126.add("PHC2092");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_126.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_126.add("TUB141");
	   validateRule5_test_126.add("PHC1873");
	   validateRule5_test_126.add("TUB144");
	   validateRule5_test_126.add("PHC1873");
	   
	   validateRule5_test_126.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_126.add("TUB147");
	   validateRule5_test_126.add("10828004");
	   validateRule5_test_126.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_126.add("TUB150");
	   validateRule5_test_126.add("10828004");
	   validateRule5_test_126.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_126.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_126.add("true");
	   
	   
	   */
	   

	   
	   	/*Parameters for validateRule5_test_127*/
	   
	   ArrayList<String> validateRule5_test_127= new ArrayList<String>();
	   
	   validateRule5_test_127.add("INV1133");
	   validateRule5_test_127.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_test_127.add("TUB122");
	   validateRule5_test_127.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_test_127.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_test_127.add("TUB130");
	   validateRule5_test_127.add("");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_test_127.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_test_127.add("TUB135");
	   validateRule5_test_127.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_test_127.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_test_127.add("TUB141");
	   validateRule5_test_127.add("PHC1873");
	   validateRule5_test_127.add("TUB144");
	   validateRule5_test_127.add("PHC1873");
	   
	   validateRule5_test_127.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   
	   //AND
	   
	   validateRule5_test_127.add("TUB147");
	   validateRule5_test_127.add("10828004");
	   validateRule5_test_127.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_test_127.add("TUB150");
	   validateRule5_test_127.add("10828004");
	   validateRule5_test_127.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_test_127.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_test_127.add("true");
	   
	   
	   
	
	   
	   int it = 0;
		String testName = "validateRule5_test";
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule4_test
  		    
    	
    		  {testName+"_"+it++,validateRule5_test_100},
    		  {testName+"_"+it++,validateRule5_test_101},
    		  {testName+"_"+it++,validateRule5_test_102},
    		  {testName+"_"+it++,validateRule5_test_103},
    		  {testName+"_"+it++,validateRule5_test_104},
    		  {testName+"_"+it++,validateRule5_test_105},
    		  {testName+"_"+it++,validateRule5_test_106},
    		  {testName+"_"+it++,validateRule5_test_107},
    		  {testName+"_"+it++,validateRule5_test_108},
    		  {testName+"_"+it++,validateRule5_test_109},
    		  {testName+"_"+it++,validateRule5_test_110},
    		  {testName+"_"+it++,validateRule5_test_111},
    		  {testName+"_"+it++,validateRule5_test_112},
    		  {testName+"_"+it++,validateRule5_test_113},
    		  {testName+"_"+it++,validateRule5_test_114},
    		  {testName+"_"+it++,validateRule5_test_115},
    		  {testName+"_"+it++,validateRule5_test_116},
    		  {testName+"_"+it++,validateRule5_test_117},
    		  {testName+"_"+it++,validateRule5_test_118},
    		  {testName+"_"+it++,validateRule5_test_119},
    		//  {testName+"_"+it++,validateRule5_test_120},
    		//  {testName+"_"+it++,validateRule5_test_121},
    	//	  {testName+"_"+it++,validateRule5_test_122},
    		 // {testName+"_"+it++,validateRule5_test_123},
    		 // {testName+"_"+it++,validateRule5_test_124},
    		//  {testName+"_"+it++,validateRule5_test_125},
    		 // {testName+"_"+it++,validateRule5_test_126},
    		   {testName+"_"+it++,validateRule5_test_127},
    		  
    		  //I can't uncomment all the scenarios, because a too many arguments exception will throw. We can uncomment and comment one by one,
    		   //so we don't execute too many at the same time, and we can also see that all scenarios are working.
    		  
    		  
	
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

		@Test
		public void validateRule5_part1_test() throws Exception{
				

					System.out.println("******************* Starting test case named: validateRule5_part1_test *******************");
				
					String subsectionId = "NBS_UI_GA27017";
					String subsectionIdChestStudy = "NBS_UI_GA28004";
					
					//This will simulate the values selected from the UI
					
				
					
				
					//We don't need to change this values, this is just to be able to make the internal call that will be mocked.

					HashMap <String, ArrayList<String>> codeValuesRule5_1 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values5_1_1 = new ArrayList<String>();//Culture
					ArrayList<String> values5_1_2 = new ArrayList<String>();//Test Result
					ArrayList<String> values5_1_3 = new ArrayList<String>();//Specimen Source
					
					

					values5_1_1.add("50941-4");
					
					values5_1_2.add("260385009");
					values5_1_2.add("385660001");
					values5_1_2.add("UNK");
					values5_1_2.add("82334004");
					values5_1_2.add("443390004");
					values5_1_2.add("410530007");
					values5_1_2.add("PHC2092");
					
					
					values5_1_3.add("119334006");//Sputum
					
					
					codeValuesRule5_1.put("INV290",values5_1_1);//Test Type
					codeValuesRule5_1.put("INV291",values5_1_2);//Test Result
					codeValuesRule5_1.put("LAB165",values5_1_3);//Specimen Source Site

					HashMap <String, ArrayList<String>> codeValuesRule5_2 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values5_2_1 = new ArrayList<String>();
					

					values5_2_1.add("50941-4");//Culture		
					codeValuesRule5_2.put("INV290",values5_2_1);//Test Type

			    	ArrayList<String> INV1133Values = new ArrayList<String>();
					
			    	INV1133Values.add("281778006");
			    	INV1133Values.add("39607008");
			    	INV1133Values.add("3120008");
			    	
			    	
			    	ArrayList<String> TUB122Values = new ArrayList<String>();
					
			    	TUB122Values.add("260385009");
			    	TUB122Values.add("385660001");
			    	TUB122Values.add("UNK");
			    	TUB122Values.add("82334004");
			    	TUB122Values.add("443390004");
			    	TUB122Values.add("410530007");
			    	TUB122Values.add("PHC2092");
			    	
			    	
			    	
			    	ArrayList<String> TUB130Values = new ArrayList<String>();
					
			    	TUB130Values.add("260385009");
			    	TUB130Values.add("385660001");
			    	TUB130Values.add("UNK");
			    	TUB130Values.add("82334004");
			    	TUB130Values.add("443390004");
			    	TUB130Values.add("410530007");
			    	TUB130Values.add("PHC2092");
			    	TUB130Values.add("");
			    	
			    	
			    	
			    	ArrayList<String> TUB135Values = new ArrayList<String>();
			    	
			    	TUB135Values.add("260385009");
			    	TUB135Values.add("385660001");
			    	TUB135Values.add("UNK");
			    	TUB135Values.add("82334004");
			    	TUB135Values.add("443390004");
			    	TUB135Values.add("410530007");
			    	TUB135Values.add("PHC2092");

					HashMap <String, ArrayList<String>> codeValuesRule5_3 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values5_3_1 = new ArrayList<String>();
					ArrayList<String> values5_3_2 = new ArrayList<String>();
					

					values5_3_1.add("LAB673");
					
					values5_3_2.add("260385009");
					values5_3_2.add("385660001");
					values5_3_2.add("UNK");
					values5_3_2.add("82334004");
					values5_3_2.add("443390004");
					values5_3_2.add("410530007");
					values5_3_2.add("PHC2092");
					
					codeValuesRule5_3.put("INV290",values5_3_1);//Test Type
					codeValuesRule5_3.put("INV291",values5_3_2);//Test Result

					
					HashMap <String, ArrayList<String>> codeValuesRule5_6 = new HashMap<String, ArrayList<String>>();
					
					
					ArrayList<String> values5_6_1 = new ArrayList<String>();
					ArrayList<String> values5_6_2= new ArrayList<String>();
					ArrayList<String> values5_6_3= new ArrayList<String>();
					

					values5_6_1.add("50941-4");//Culture
					
					values5_6_2.add("260385009");
					values5_6_2.add("385660001");
					values5_6_2.add("UNK");
					values5_6_2.add("82334004");
					values5_6_2.add("443390004");
					values5_6_2.add("410530007");
					values5_6_2.add("PHC2092");
					
					values5_6_3.add("119334006");//Sputum
					
					codeValuesRule5_6.put("INV290",values5_6_2);//Test Type
					codeValuesRule5_6.put("INV291",values5_6_2);//Test Result
					codeValuesRule5_6.put("NOT_CONTAIN_LAB165",values5_6_3);//Specimen Source Site

					HashMap <String, ArrayList<String>> codeValuesRule5_4 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values5_4_1 = new ArrayList<String>();
					ArrayList<String> values5_4_2 = new ArrayList<String>();
					

					values5_4_1.add("169069000");
					values5_4_1.add("399208008");
					
					values5_4_2.add("PHC1873");
					
					//Test type doesn't matter
					codeValuesRule5_4.put("LAB677",values5_4_1);//Type of Chest Study
					codeValuesRule5_4.put("LAB678",values5_4_2);//Result of Chest Study


					HashMap <String, ArrayList<String>> codeValuesRule5_5 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values5_5_1 = new ArrayList<String>();
					ArrayList<String> values5_5_2 = new ArrayList<String>();
					

					values5_5_1.add("TB119");
					
					values5_5_2.add("10828004");//10828004 (Positive))]
					
					codeValuesRule5_5.put("INV290",values5_5_1);//Test Type
					codeValuesRule5_5.put("INV291",values5_5_2);//Test Result
					
					
					HashMap <String, ArrayList<String>> codeValuesRule5_7 = new HashMap<String, ArrayList<String>>();
					ArrayList<String> values5_7_1 = new ArrayList<String>();
					ArrayList<String> values5_7_2 = new ArrayList<String>();
					

					values5_7_1.add("LAB671");
					values5_7_1.add("LAB672");
					values5_7_1.add("LAB720");
					values5_7_1.add("71773-6");
					
					values5_7_2.add("10828004");//10828004 (Positive))]
					
					codeValuesRule5_7.put("INV290",values5_7_1);//Test Type
					codeValuesRule5_7.put("INV291",values5_7_2);//Test Result
					
					
					
					HashMap<String, String> criterias = new HashMap<String, String>();
					
					

			    	criterias.put("6038","Y");
			    	criterias.put("9384","Y");
			    	criterias.put("8987","Y");
			    	criterias.put("4110","Y");
			    	criterias.put("10109","Y");
			    	criterias.put("55672","Y");
			    	criterias.put("35617","Y");
			    	criterias.put("4127","Y");
			    	criterias.put("641","Y");
			    	criterias.put("6099","Y");
			    	criterias.put("78903","Y");
			    	criterias.put("2551","Y");
			    	criterias.put("82122","Y");
			    	criterias.put("7623","Y");
			    	criterias.put("139462","Y");
			    	criterias.put("PHC1888","Y");
			    	criterias.put("3007","Y");
			    	criterias.put("7833","Y");
			    	criterias.put("190376","Y");
			    	criterias.put("1364504","Y");
			    	criterias.put("PHC1889","Y");
			    	criterias.put("2592","Y");
			    	criterias.put("2198359","Y");
			    	criterias.put("NBS456","Y");
				
				
				
			    	
			    	
					
					/***************/
					
					//Values to be passed as parameters
				
					
			    	String INV1133 = parameters.get(0);//Site of TB Disease
			    	boolean INV1133Value = Boolean.parseBoolean(parameters.get(1));//Site of TB Disease
			    	String TUB122 = parameters.get(2);//Sputum Culture Result
			    	String TUB122Value = parameters.get(3);//Sputum Culture Result
					boolean existsInRepeatingBlockSputum =  Boolean.parseBoolean(parameters.get(4));
					
					
			    	String TUB130 =  parameters.get(5);//Other Culture Results
			    	String TUB130Value =  parameters.get(6);//Other Culture Results
					boolean existsInRepeatingBlockCulture =  Boolean.parseBoolean(parameters.get(7));
					
					
			    	String TUB135 =  parameters.get(8);//Nucleic Acid Amplification Results
			    	String TUB135Value =  parameters.get(9);//Nucleic Acid Amplification Results
			    	boolean existsInRepeatingBlockNAA =  Boolean.parseBoolean(parameters.get(10));
			    	
			    	String TUB141 = parameters.get(11);//Initial Chest X-Ray Result
			    	String TUB141Value = parameters.get(12);//Initial Chest X-Ray Result
					String TUB144 =  parameters.get(13);//Initial Chest CT Scan Result
					String TUB144Value  =  parameters.get(14);//Initial Chest CT Scan Result
				 	boolean existsInRepeatingBlockChest =  Boolean.parseBoolean(parameters.get(15));
				 	
				 	
					String TUB147 =  parameters.get(16);//Tuberculin Skin Test Result
					String TUB147Value =  parameters.get(17);//Tuberculin Skin Test Result
					boolean existsInRepeatingBlockTB =  Boolean.parseBoolean(parameters.get(18));
				 	
					String TUB150 = parameters.get(19);//IGRA Qualitative Test Result
					String TUB150Value = parameters.get(20);//IGRA Qualitative Test Result
					boolean existsInRepeatingBlockIGRA =  Boolean.parseBoolean(parameters.get(21));
					
					boolean validateNCriteriaMet = Boolean.parseBoolean(parameters.get(22));
			
					boolean expectedValue =  Boolean.parseBoolean(parameters.get(23));
					
					
	
					PowerMockito.doReturn(INV1133Value).when(PageCreateHelper.class, "validateIfMultiIsPartOfValues",  form,INV1133Values, INV1133);
					
				//	PowerMockito.doReturn(INV1133Value).when(PageCreateHelper.class, "getValueFromForm",  form,INV1133);
					PowerMockito.doReturn(TUB122Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB122);
					PowerMockito.doReturn(TUB130Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB130);
					PowerMockito.doReturn(TUB135Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB135);
					PowerMockito.doReturn(TUB141Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB141);
					PowerMockito.doReturn(TUB144Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB144);
					PowerMockito.doReturn(TUB147Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB147);
					PowerMockito.doReturn(TUB150Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB150);
					
					
					
					
					
					
					//We could use this, for simplicity
					//PowerMockito.doReturn(existsInRepeatingBlock).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,Mockito.anyMapOf(String.class, ArrayList.class));
					//But if we want to have control over which values are in the repeating block and which ones are not, so we can test different scenarios, then it is better to do this
					PowerMockito.doReturn(existsInRepeatingBlockSputum).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule5_1);
					PowerMockito.doReturn(existsInRepeatingBlockCulture).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule5_6);
					PowerMockito.doReturn(existsInRepeatingBlockNAA).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule5_3);
					PowerMockito.doReturn(existsInRepeatingBlockChest).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionIdChestStudy,codeValuesRule5_4);
					PowerMockito.doReturn(existsInRepeatingBlockTB).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule5_5);
					PowerMockito.doReturn(existsInRepeatingBlockIGRA).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule5_7);
					PowerMockito.doReturn(validateNCriteriaMet).when(PageCreateHelper.class, "validateIfNCriteriasMet",  form,criterias,2);

			  		
					
					
					
					boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "validateRule5_part1", form);
				
					System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue+"\nActual value: "+actualResult);
					Assert.assertEquals(expectedValue, actualResult);
					System.out.println("PASSED");
					System.out.println("******************* End test case named: validateRule5_part1_test *******************");
				}	
	 }








@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
public static class ValidateRule5_part2_test{
	

//TODO: we might not need this now that we are using inner classes.
/*	private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public ValidateRule5_part2_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {


	   
	   	/*Parameters for validateRule5_part2_test_100*/
	   
	   ArrayList<String> validateRule5_part2_test_100= new ArrayList<String>();
	   
	   validateRule5_part2_test_100.add("INV1133");
	   validateRule5_part2_test_100.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_100.add("TUB122");
	   validateRule5_part2_test_100.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_100.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_100.add("TUB130");
	   validateRule5_part2_test_100.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_100.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_100.add("TUB135");
	   validateRule5_part2_test_100.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_100.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_part2_test_100.add("TUB147");
	   validateRule5_part2_test_100.add("10828004");
	   validateRule5_part2_test_100.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_100.add("TUB150");
	   validateRule5_part2_test_100.add("10828004");
	   validateRule5_part2_test_100.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_100.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_100.add("true");


	   	/*Parameters for validateRule5_part2_test_101*/
	   
	   ArrayList<String> validateRule5_part2_test_101= new ArrayList<String>();
	   
	   validateRule5_part2_test_101.add("INV1133");
	   validateRule5_part2_test_101.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_101.add("TUB122");
	   validateRule5_part2_test_101.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_101.add("false");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_101.add("TUB130");
	   validateRule5_part2_test_101.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_101.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_101.add("TUB135");
	   validateRule5_part2_test_101.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_101.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	 
	   
	   //AND
	   
	   validateRule5_part2_test_101.add("TUB147");
	   validateRule5_part2_test_101.add("10828004");
	   validateRule5_part2_test_101.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_101.add("TUB150");
	   validateRule5_part2_test_101.add("10828004");
	   validateRule5_part2_test_101.add("false");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_101.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_101.add("true");
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_102*/
	   
	   ArrayList<String> validateRule5_part2_test_102= new ArrayList<String>();
	   
	   validateRule5_part2_test_102.add("INV1133");
	   validateRule5_part2_test_102.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_102.add("TUB122");
	   validateRule5_part2_test_102.add("2603850091");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_102.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_102.add("TUB130");
	   validateRule5_part2_test_102.add("2603850091");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_102.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_102.add("TUB135");
	   validateRule5_part2_test_102.add("UNK1");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_102.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	
	   
	   
	   //AND
	   
	   validateRule5_part2_test_102.add("TUB147");
	   validateRule5_part2_test_102.add("108280041");
	   validateRule5_part2_test_102.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_102.add("TUB150");
	   validateRule5_part2_test_102.add("108280041");
	   validateRule5_part2_test_102.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_102.add("true");//2 criterias met

	   //Expected value
	   validateRule5_part2_test_102.add("true");
	   
	   
	   
	   
	   
	   
	   
	   
	/*Parameters for validateRule5_part2_test_103*/
	   
	   ArrayList<String> validateRule5_part2_test_103= new ArrayList<String>();
	   
	   validateRule5_part2_test_103.add("INV1133");
	   validateRule5_part2_test_103.add("false");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_103.add("TUB122");
	   validateRule5_part2_test_103.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_103.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_103.add("TUB130");
	   validateRule5_part2_test_103.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_103.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_103.add("TUB135");
	   validateRule5_part2_test_103.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_103.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   
	   
	   
	   //AND
	   
	   validateRule5_part2_test_103.add("TUB147");
	   validateRule5_part2_test_103.add("10828004");
	   validateRule5_part2_test_103.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_103.add("TUB150");
	   validateRule5_part2_test_103.add("10828004");
	   validateRule5_part2_test_103.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_103.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_103.add("false");
	   
	   
	   
	   

	   	/*Parameters for validateRule5_part2_test_104*/
	   
	   ArrayList<String> validateRule5_part2_test_104= new ArrayList<String>();
	   
	   validateRule5_part2_test_104.add("INV1133");
	   validateRule5_part2_test_104.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_104.add("TUB122");
	   validateRule5_part2_test_104.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_104.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_104.add("TUB130");
	   validateRule5_part2_test_104.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_104.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_104.add("TUB135");
	   validateRule5_part2_test_104.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_104.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	    
	   //AND
	   
	   validateRule5_part2_test_104.add("TUB147");
	   validateRule5_part2_test_104.add("10828004");
	   validateRule5_part2_test_104.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_104.add("TUB150");
	   validateRule5_part2_test_104.add("10828004");
	   validateRule5_part2_test_104.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_104.add("false");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_104.add("false");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_105*/
	   
	   ArrayList<String> validateRule5_part2_test_105= new ArrayList<String>();
	   
	   validateRule5_part2_test_105.add("INV1133");
	   validateRule5_part2_test_105.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_105.add("TUB122");
	   validateRule5_part2_test_105.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_105.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_105.add("TUB130");
	   validateRule5_part2_test_105.add("260385001");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_105.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_105.add("TUB135");
	   validateRule5_part2_test_105.add("UNK1");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_105.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	    
	   //AND
	   
	   validateRule5_part2_test_105.add("TUB147");
	   validateRule5_part2_test_105.add("10828004");
	   validateRule5_part2_test_105.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_105.add("TUB150");
	   validateRule5_part2_test_105.add("10828004");
	   validateRule5_part2_test_105.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_105.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_105.add("false");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_106*/
	   
	   ArrayList<String> validateRule5_part2_test_106= new ArrayList<String>();
	   
	   validateRule5_part2_test_106.add("INV1133");
	   validateRule5_part2_test_106.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_106.add("TUB122");
	   validateRule5_part2_test_106.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_106.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_106.add("TUB130");
	   validateRule5_part2_test_106.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_106.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_106.add("TUB135");
	   validateRule5_part2_test_106.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_106.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	     
	   
	   //AND
	   
	   validateRule5_part2_test_106.add("TUB147");
	   validateRule5_part2_test_106.add("10828004");
	   validateRule5_part2_test_106.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_106.add("TUB150");
	   validateRule5_part2_test_106.add("10828004");
	   validateRule5_part2_test_106.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_106.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_106.add("true");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_107*/
	   
	   ArrayList<String> validateRule5_part2_test_107= new ArrayList<String>();
	   
	   validateRule5_part2_test_107.add("INV1133");
	   validateRule5_part2_test_107.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_107.add("TUB122");
	   validateRule5_part2_test_107.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_107.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_107.add("TUB130");
	   validateRule5_part2_test_107.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_107.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_107.add("TUB135");
	   validateRule5_part2_test_107.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_107.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	    
	   
	   //AND
	   
	   validateRule5_part2_test_107.add("TUB147");
	   validateRule5_part2_test_107.add("10828001");
	   validateRule5_part2_test_107.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_107.add("TUB150");
	   validateRule5_part2_test_107.add("10828001");
	   validateRule5_part2_test_107.add("false");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_107.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_107.add("false");
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_108*/
	   
	   ArrayList<String> validateRule5_part2_test_108= new ArrayList<String>();
	   
	   validateRule5_part2_test_108.add("INV1133");
	   validateRule5_part2_test_108.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_108.add("TUB122");
	   validateRule5_part2_test_108.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_108.add("false");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_108.add("TUB130");
	   validateRule5_part2_test_108.add("260385001");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_108.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_108.add("TUB135");
	   validateRule5_part2_test_108.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_108.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	 	   
	   //AND
	   
	   validateRule5_part2_test_108.add("TUB147");
	   validateRule5_part2_test_108.add("10828004");
	   validateRule5_part2_test_108.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_108.add("TUB150");
	   validateRule5_part2_test_108.add("10828001");
	   validateRule5_part2_test_108.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_108.add("false");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_108.add("false");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_109*/
	   
	   ArrayList<String> validateRule5_part2_test_109= new ArrayList<String>();
	   
	   validateRule5_part2_test_109.add("INV1133");
	   validateRule5_part2_test_109.add("false");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_109.add("TUB122");
	   validateRule5_part2_test_109.add("260385001");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_109.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_109.add("TUB130");
	   validateRule5_part2_test_109.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_109.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_109.add("TUB135");
	   validateRule5_part2_test_109.add("UNK1");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_109.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
		   
	   
	   //AND
	   
	   validateRule5_part2_test_109.add("TUB147");
	   validateRule5_part2_test_109.add("108280041");
	   validateRule5_part2_test_109.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_109.add("TUB150");
	   validateRule5_part2_test_109.add("10828004");
	   validateRule5_part2_test_109.add("false");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_109.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_109.add("false");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_110*/
	   
	   ArrayList<String> validateRule5_part2_test_110= new ArrayList<String>();
	   
	   validateRule5_part2_test_110.add("INV1133");
	   validateRule5_part2_test_110.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_110.add("TUB122");
	   validateRule5_part2_test_110.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_110.add("false");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_110.add("TUB130");
	   validateRule5_part2_test_110.add("2603850091");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_110.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_110.add("TUB135");
	   validateRule5_part2_test_110.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_110.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	      //AND
	   
	   validateRule5_part2_test_110.add("TUB147");
	   validateRule5_part2_test_110.add("10828004");
	   validateRule5_part2_test_110.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_110.add("TUB150");
	   validateRule5_part2_test_110.add("108280041");
	   validateRule5_part2_test_110.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_110.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_110.add("true");
	   
	   
	   
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_111*/
	   
	   ArrayList<String> validateRule5_part2_test_111= new ArrayList<String>();
	   
	   validateRule5_part2_test_111.add("INV1133");
	   validateRule5_part2_test_111.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_111.add("TUB122");
	   validateRule5_part2_test_111.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_111.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_111.add("TUB130");
	   validateRule5_part2_test_111.add("");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_111.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_111.add("TUB135");
	   validateRule5_part2_test_111.add("");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_111.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	     
	   
	   //AND
	   
	   validateRule5_part2_test_111.add("TUB147");
	   validateRule5_part2_test_111.add("");
	   validateRule5_part2_test_111.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_111.add("TUB150");
	   validateRule5_part2_test_111.add("");
	   validateRule5_part2_test_111.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_111.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_111.add("true");
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_112*/
	   
	   ArrayList<String> validateRule5_part2_test_112= new ArrayList<String>();
	   
	   validateRule5_part2_test_112.add("INV1133");
	   validateRule5_part2_test_112.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_112.add("TUB122");
	   validateRule5_part2_test_112.add("2603850091");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_112.add("false");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_112.add("TUB130");
	   validateRule5_part2_test_112.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_112.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_112.add("TUB135");
	   validateRule5_part2_test_112.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_112.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	 	   
	   
	   //AND
	   
	   validateRule5_part2_test_112.add("TUB147");
	   validateRule5_part2_test_112.add("10828004");
	   validateRule5_part2_test_112.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_112.add("TUB150");
	   validateRule5_part2_test_112.add("10828004");
	   validateRule5_part2_test_112.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_112.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_112.add("false");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_113*/
	   
	   ArrayList<String> validateRule5_part2_test_113= new ArrayList<String>();
	   
	   validateRule5_part2_test_113.add("INV1133");
	   validateRule5_part2_test_113.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_113.add("TUB122");
	   validateRule5_part2_test_113.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_113.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_113.add("TUB130");
	   validateRule5_part2_test_113.add("1");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_113.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_113.add("TUB135");
	   validateRule5_part2_test_113.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_113.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	     
	   
	   //AND
	   
	   validateRule5_part2_test_113.add("TUB147");
	   validateRule5_part2_test_113.add("10828004");
	   validateRule5_part2_test_113.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_113.add("TUB150");
	   validateRule5_part2_test_113.add("10828004");
	   validateRule5_part2_test_113.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_113.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_113.add("false");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_114*/
	   
	   ArrayList<String> validateRule5_part2_test_114= new ArrayList<String>();
	   
	   validateRule5_part2_test_114.add("INV1133");
	   validateRule5_part2_test_114.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_114.add("TUB122");
	   validateRule5_part2_test_114.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_114.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_114.add("TUB130");
	   validateRule5_part2_test_114.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_114.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_114.add("TUB135");
	   validateRule5_part2_test_114.add("1");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_114.add("false");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
		   
	   
	   //AND
	   
	   validateRule5_part2_test_114.add("TUB147");
	   validateRule5_part2_test_114.add("10828004");
	   validateRule5_part2_test_114.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_114.add("TUB150");
	   validateRule5_part2_test_114.add("10828004");
	   validateRule5_part2_test_114.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_114.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_114.add("false");
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_115*/
	   
	   ArrayList<String> validateRule5_part2_test_115= new ArrayList<String>();
	   
	   validateRule5_part2_test_115.add("INV1133");
	   validateRule5_part2_test_115.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_115.add("TUB122");
	   validateRule5_part2_test_115.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_115.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_115.add("TUB130");
	   validateRule5_part2_test_115.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_115.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_115.add("TUB135");
	   validateRule5_part2_test_115.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_115.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	    
	   
	   //AND
	   
	   validateRule5_part2_test_115.add("TUB147");
	   validateRule5_part2_test_115.add("10828004");
	   validateRule5_part2_test_115.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_115.add("TUB150");
	   validateRule5_part2_test_115.add("10828004");
	   validateRule5_part2_test_115.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_115.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_115.add("true");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_116*/
	   
	   ArrayList<String> validateRule5_part2_test_116= new ArrayList<String>();
	   
	   validateRule5_part2_test_116.add("INV1133");
	   validateRule5_part2_test_116.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_116.add("TUB122");
	   validateRule5_part2_test_116.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_116.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_116.add("TUB130");
	   validateRule5_part2_test_116.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_116.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_116.add("TUB135");
	   validateRule5_part2_test_116.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_116.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	      
	   
	   //AND
	   
	   validateRule5_part2_test_116.add("TUB147");
	   validateRule5_part2_test_116.add("1");
	   validateRule5_part2_test_116.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_116.add("TUB150");
	   validateRule5_part2_test_116.add("1");
	   validateRule5_part2_test_116.add("false");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_116.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_116.add("false");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_117*/
	   
	   ArrayList<String> validateRule5_part2_test_117= new ArrayList<String>();
	   
	   validateRule5_part2_test_117.add("INV1133");
	   validateRule5_part2_test_117.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_117.add("TUB122");
	   validateRule5_part2_test_117.add(null);//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_117.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_117.add("TUB130");
	   validateRule5_part2_test_117.add(null);//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_117.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_117.add("TUB135");
	   validateRule5_part2_test_117.add(null);//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_117.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	    
	   
	   //AND
	   
	   validateRule5_part2_test_117.add("TUB147");
	   validateRule5_part2_test_117.add(null);
	   validateRule5_part2_test_117.add("false");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_117.add("TUB150");
	   validateRule5_part2_test_117.add(null);
	   validateRule5_part2_test_117.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_117.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_117.add("true");
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_118*/
	   
	   ArrayList<String> validateRule5_part2_test_118= new ArrayList<String>();
	   
	   validateRule5_part2_test_118.add("INV1133");
	   validateRule5_part2_test_118.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_118.add("TUB122");
	   validateRule5_part2_test_118.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_118.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_118.add("TUB130");
	   validateRule5_part2_test_118.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_118.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_118.add("TUB135");
	   validateRule5_part2_test_118.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_118.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	 	   
	   //AND
	   
	   validateRule5_part2_test_118.add("TUB147");
	   validateRule5_part2_test_118.add("10828004");
	   validateRule5_part2_test_118.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_118.add("TUB150");
	   validateRule5_part2_test_118.add("10828004");
	   validateRule5_part2_test_118.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_118.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_118.add("true");
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_119*/
	   
	   ArrayList<String> validateRule5_part2_test_119= new ArrayList<String>();
	   
	   validateRule5_part2_test_119.add("INV1133");
	   validateRule5_part2_test_119.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_119.add("TUB122");
	   validateRule5_part2_test_119.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_119.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_119.add("TUB130");
	   validateRule5_part2_test_119.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_119.add("false");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_119.add("TUB135");
	   validateRule5_part2_test_119.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_119.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	     
	   
	   //AND
	   
	   validateRule5_part2_test_119.add("TUB147");
	   validateRule5_part2_test_119.add("10828004");
	   validateRule5_part2_test_119.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_119.add("TUB150");
	   validateRule5_part2_test_119.add("10828004");
	   validateRule5_part2_test_119.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_119.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_119.add("true");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_120*/
	   
	   ArrayList<String> validateRule5_part2_test_120= new ArrayList<String>();
	   
	   validateRule5_part2_test_120.add("INV1133");
	   validateRule5_part2_test_120.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_120.add("TUB122");
	   validateRule5_part2_test_120.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_120.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_120.add("TUB130");
	   validateRule5_part2_test_120.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_120.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_120.add("TUB135");
	   validateRule5_part2_test_120.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_120.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
		   
	   //AND
	   
	   validateRule5_part2_test_120.add("TUB147");
	   validateRule5_part2_test_120.add("10828004");
	   validateRule5_part2_test_120.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_120.add("TUB150");
	   validateRule5_part2_test_120.add("1");
	   validateRule5_part2_test_120.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_120.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_120.add("true");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_121*/
	   
	   ArrayList<String> validateRule5_part2_test_121= new ArrayList<String>();
	   
	   validateRule5_part2_test_121.add("INV1133");
	   validateRule5_part2_test_121.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_121.add("TUB122");
	   validateRule5_part2_test_121.add("385660001");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_121.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_121.add("TUB130");
	   validateRule5_part2_test_121.add("385660001");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_121.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_121.add("TUB135");
	   validateRule5_part2_test_121.add("260385009");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_121.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   
	   //AND
	   
	   validateRule5_part2_test_121.add("TUB147");
	   validateRule5_part2_test_121.add("10828004");
	   validateRule5_part2_test_121.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_121.add("TUB150");
	   validateRule5_part2_test_121.add("10828004");
	   validateRule5_part2_test_121.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_121.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_121.add("true");
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_122*/
	/*   
	  ArrayList<String> validateRule5_part2_test_122= new ArrayList<String>();
	   
	   validateRule5_part2_test_122.add("INV1133");
	   validateRule5_part2_test_122.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_122.add("TUB122");
	   validateRule5_part2_test_122.add("UNK");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_122.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_122.add("TUB130");
	   validateRule5_part2_test_122.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_122.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_122.add("TUB135");
	   validateRule5_part2_test_122.add("385660001");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_122.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		

	   
	   //AND
	   
	   validateRule5_part2_test_122.add("TUB147");
	   validateRule5_part2_test_122.add("10828004");
	   validateRule5_part2_test_122.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_122.add("TUB150");
	   validateRule5_part2_test_122.add("10828004");
	   validateRule5_part2_test_122.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_122.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_122.add("true");
	   
	   */
	   

	   
	   	/*Parameters for validateRule5_part2_test_123*/
	   /*
	   ArrayList<String> validateRule5_part2_test_123= new ArrayList<String>();
	   
	   validateRule5_part2_test_123.add("INV1133");
	   validateRule5_part2_test_123.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_123.add("TUB122");
	   validateRule5_part2_test_123.add("82334004");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_123.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_123.add("TUB130");
	   validateRule5_part2_test_123.add("82334004");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_123.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_123.add("TUB135");
	   validateRule5_part2_test_123.add("82334004");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_123.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	
	   
	   //AND
	   
	   validateRule5_part2_test_123.add("TUB147");
	   validateRule5_part2_test_123.add("10828004");
	   validateRule5_part2_test_123.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_123.add("TUB150");
	   validateRule5_part2_test_123.add("10828004");
	   validateRule5_part2_test_123.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_123.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_123.add("true");
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_124*/
	  /*
	   ArrayList<String> validateRule5_part2_test_124= new ArrayList<String>();
	   
	   validateRule5_part2_test_124.add("INV1133");
	   validateRule5_part2_test_124.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_124.add("TUB122");
	   validateRule5_part2_test_124.add("443390004");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_124.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_124.add("TUB130");
	   validateRule5_part2_test_124.add("443390004");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_124.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_124.add("TUB135");
	   validateRule5_part2_test_124.add("443390004");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_124.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	
	   
	   //AND
	   
	   validateRule5_part2_test_124.add("TUB147");
	   validateRule5_part2_test_124.add("10828004");
	   validateRule5_part2_test_124.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_124.add("TUB150");
	   validateRule5_part2_test_124.add("10828004");
	   validateRule5_part2_test_124.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_124.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_124.add("true");
	   
	   
	   
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_125*/
	/*   
	   ArrayList<String> validateRule5_part2_test_125= new ArrayList<String>();
	   
	   validateRule5_part2_test_125.add("INV1133");
	   validateRule5_part2_test_125.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_125.add("TUB122");
	   validateRule5_part2_test_125.add("410530007");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_125.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_125.add("TUB130");
	   validateRule5_part2_test_125.add("410530007");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_125.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_125.add("TUB135");
	   validateRule5_part2_test_125.add("410530007");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_125.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   //AND
	   
	   validateRule5_part2_test_125.add("TUB147");
	   validateRule5_part2_test_125.add("10828004");
	   validateRule5_part2_test_125.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_125.add("TUB150");
	   validateRule5_part2_test_125.add("10828004");
	   validateRule5_part2_test_125.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_125.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_125.add("true");
	   
	   
	   
	   

	   
	   	/*Parameters for validateRule5_part2_test_126*/
	   
	 /*  ArrayList<String> validateRule5_part2_test_126= new ArrayList<String>();
	   
	   validateRule5_part2_test_126.add("INV1133");
	   validateRule5_part2_test_126.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_126.add("TUB122");
	   validateRule5_part2_test_126.add("PHC2092");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_126.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_126.add("TUB130");
	   validateRule5_part2_test_126.add("PHC2092");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_126.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_126.add("TUB135");
	   validateRule5_part2_test_126.add("PHC2092");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_126.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
	
	   
	   //AND
	   
	   validateRule5_part2_test_126.add("TUB147");
	   validateRule5_part2_test_126.add("10828004");
	   validateRule5_part2_test_126.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_126.add("TUB150");
	   validateRule5_part2_test_126.add("10828004");
	   validateRule5_part2_test_126.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_126.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_126.add("true");
	   
	   
	   */
	   

	   
	   	/*Parameters for validateRule5_part2_test_127*/
	   
	   ArrayList<String> validateRule5_part2_test_127= new ArrayList<String>();
	   
	   validateRule5_part2_test_127.add("INV1133");
	   validateRule5_part2_test_127.add("true");//Valid values: 281778006, 39607008, 3120008
	   
	   validateRule5_part2_test_127.add("TUB122");
	   validateRule5_part2_test_127.add("260385009");//Valid values: 260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092
	   validateRule5_part2_test_127.add("true");//Sputum: expected value to be return by method checking if row exists in the repeating block
	
	   //AND
	   
	   
	   validateRule5_part2_test_127.add("TUB130");
	   validateRule5_part2_test_127.add("");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092, or blank)
	   validateRule5_part2_test_127.add("true");//Culture: expected value to be return by method checking if row exists in the repeating block
		 
	   
	   //AND
	   
	   
	   validateRule5_part2_test_127.add("TUB135");
	   validateRule5_part2_test_127.add("UNK");//Valid values: (260385009, 385660001, UNK, 82334004, 443390004, 410530007, PHC2092)
	   validateRule5_part2_test_127.add("true");//NAA: expected value to be return by method checking if row exists in the repeating block
		
	   
	   
	   
	   //AND
	   
	   validateRule5_part2_test_127.add("TUB147");
	   validateRule5_part2_test_127.add("10828004");
	   validateRule5_part2_test_127.add("true");//TB: expected value to be return by method checking if row exists in the repeating block
	 
	   
	   //OR
	   validateRule5_part2_test_127.add("TUB150");
	   validateRule5_part2_test_127.add("10828004");
	   validateRule5_part2_test_127.add("true");//IGRA: expected value to be return by method checking if row exists in the repeating block
		 
	   //AND
	   
	   validateRule5_part2_test_127.add("true");//2 criterias met
		 
		  
	   
	   //Expected value
	   validateRule5_part2_test_127.add("true");
	   
	   
	   
	
	   
	   int it = 0;
		String testName = "validateRule5_part2_test";
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule5_test
  		    
    	
    		  {testName+"_"+it++,validateRule5_part2_test_100},
    		  {testName+"_"+it++,validateRule5_part2_test_101},
    		  {testName+"_"+it++,validateRule5_part2_test_102},
    		  {testName+"_"+it++,validateRule5_part2_test_103},
    		  {testName+"_"+it++,validateRule5_part2_test_104},
    		  {testName+"_"+it++,validateRule5_part2_test_105},
    		  {testName+"_"+it++,validateRule5_part2_test_106},
    		  {testName+"_"+it++,validateRule5_part2_test_107},
    		  {testName+"_"+it++,validateRule5_part2_test_108},
    		  {testName+"_"+it++,validateRule5_part2_test_109},
    		  {testName+"_"+it++,validateRule5_part2_test_110},
    		  {testName+"_"+it++,validateRule5_part2_test_111},
    		  {testName+"_"+it++,validateRule5_part2_test_112},
    		  {testName+"_"+it++,validateRule5_part2_test_113},
    		  {testName+"_"+it++,validateRule5_part2_test_114},
    		  {testName+"_"+it++,validateRule5_part2_test_115},
    		  {testName+"_"+it++,validateRule5_part2_test_116},
    		  {testName+"_"+it++,validateRule5_part2_test_117},
    		  {testName+"_"+it++,validateRule5_part2_test_118},
    		  {testName+"_"+it++,validateRule5_part2_test_119},
    		//  {testName+"_"+it++,validateRule5_part2_test_120},
    		//  {testName+"_"+it++,validateRule5_part2_test_121},
    	//	  {testName+"_"+it++,validateRule5_part2_test_122},
    		 // {testName+"_"+it++,validateRule5_part2_test_123},
    		 // {testName+"_"+it++,validateRule5_part2_test_124},
    		//  {testName+"_"+it++,validateRule5_part2_test_125},
    		 // {testName+"_"+it++,validateRule5_part2_test_126},
    		   {testName+"_"+it++,validateRule5_part2_test_127},
    		  
    		  //I can't uncomment all the scenarios, because a too many arguments exception will throw. We can uncomment and comment one by one,
    		   //so we don't execute too many at the same time, and we can also see that all scenarios are working.
    		  
    		
	
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper = Mockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

		@Test
		public void validateRule5_part2_test() throws Exception{
				

					System.out.println("******************* Starting test case named: validateRule5_part2_test *******************");
				
					String subsectionId = "NBS_UI_GA27017";
					String subsectionIdChestStudy = "NBS_UI_GA28004";
					
					//This will simulate the values selected from the UI
					
				
					
				
					//We don't need to change this values, this is just to be able to make the internal call that will be mocked.

					

			    	
			    	ArrayList<String> INV1133Values = new ArrayList<String>();
					

			    	INV1133Values.add("23451007");
			    	INV1133Values.add("362102006");
			    	INV1133Values.add("53505006");
			    	INV1133Values.add("66754008");
			    	INV1133Values.add("87612001");
			    	INV1133Values.add("59820001");
			    	//INV1133Values.add("PHC5");//ND-26685
			    	INV1133Values.add("110522009");
			    	INV1133Values.add("14016003");
			    	INV1133Values.add("12738006");
			    	INV1133Values.add("76752008");
			    	INV1133Values.add("17401000");
			    	INV1133Values.add("71854001");
			    	INV1133Values.add("38848004");
			    	INV1133Values.add("110547006");
			    	INV1133Values.add("32849002");
			    	INV1133Values.add("16014003");
			    	INV1133Values.add("PHC4");
			    	INV1133Values.add("C0230999");
			    	INV1133Values.add("28231008");
			    	INV1133Values.add("80891009");
			    	INV1133Values.add("110611003");
			    	INV1133Values.add("48477009");
			    	INV1133Values.add("10200004");	
			    	INV1133Values.add("PHC2");
			    	INV1133Values.add("PHC3");
			    	INV1133Values.add("1231004");
			    	INV1133Values.add("110708006");
			    	INV1133Values.add("123851003");
			    	INV1133Values.add("45206002");
			    	INV1133Values.add("71836000");
			    	INV1133Values.add("OTH");
			    	INV1133Values.add("15776009");
			    	INV1133Values.add("120228005");
			    	INV1133Values.add("76848001");
			    	INV1133Values.add("83670000");
			    	INV1133Values.add("54066008");
			    	INV1133Values.add("56329008");
			    	INV1133Values.add("110973009");
			    	INV1133Values.add("34402009");
			    	INV1133Values.add("385294005");
			    	INV1133Values.add("39937001");
			    	INV1133Values.add("2748008");
			    	INV1133Values.add("78961009");
			    	INV1133Values.add("69695003");
			    	INV1133Values.add("21514008");
			    	INV1133Values.add("281777001");
			    	INV1133Values.add("69831007");
			    	INV1133Values.add("25087005");
			    	INV1133Values.add("71966008");
			    	INV1133Values.add("9875009");
			    	INV1133Values.add("297261005");
			    	INV1133Values.add("21974007");
			    	INV1133Values.add("303337002");
			    	INV1133Values.add("44567001");
			    	
			    	
			    	ArrayList<String> TUB122Values = new ArrayList<String>();
			    	
			    	TUB122Values.add("260385009");
			    	TUB122Values.add("385660001");
			    	TUB122Values.add("UNK");
			    	TUB122Values.add("82334004");
			    	TUB122Values.add("443390004");
			    	TUB122Values.add("410530007");
			    	TUB122Values.add("PHC2092");
				 
			    	ArrayList<String> TUB130Values = new ArrayList<String>();

			    	TUB130Values.add("260385009");
			    	TUB130Values.add("385660001");
			    	TUB130Values.add("UNK");
			    	TUB130Values.add("82334004");
			    	TUB130Values.add("443390004");
			    	TUB130Values.add("410530007");
			    	TUB130Values.add("PHC2092");
			    	TUB130Values.add("");
			    	
				
					HashMap <String, ArrayList<String>> codeValuesRule5_1 = new HashMap<String, ArrayList<String>>();

					ArrayList<String> values5_1_1 = new ArrayList<String>();
					ArrayList<String> values5_1_2= new ArrayList<String>();
					ArrayList<String> values5_1_3= new ArrayList<String>();
					

					values5_1_1.add("50941-4");//Culture
					
					
					values5_1_2.add("260385009");
					values5_1_2.add("385660001");
					values5_1_2.add("UNK");
					values5_1_2.add("82334004");
					values5_1_2.add("443390004");
					values5_1_2.add("410530007");
					values5_1_2.add("PHC2092");
					
					values5_1_3.add("119334006");//Sputum
					
					
					codeValuesRule5_1.put("INV290",values5_1_1);
					codeValuesRule5_1.put("INV291",values5_1_2);
					codeValuesRule5_1.put("LAB165",values5_1_3);
					
					
					
					HashMap <String, ArrayList<String>> codeValuesRule5_4 = new HashMap<String, ArrayList<String>>();

					ArrayList<String> values5_4_1 = new ArrayList<String>();
					ArrayList<String> values5_4_2= new ArrayList<String>();
					ArrayList<String> values5_4_3= new ArrayList<String>();
					

					values5_4_1.add("50941-4");//Culture
					
					values5_4_2.add("260385009");
					values5_4_2.add("385660001");
					values5_4_2.add("UNK");
					values5_4_2.add("82334004");
					values5_4_2.add("443390004");
					values5_4_2.add("410530007");
					values5_4_2.add("PHC2092");
					
					values5_4_3.add("119334006");//Sputum
					
					
					codeValuesRule5_4.put("INV290",values5_4_1);
					codeValuesRule5_4.put("INV291",values5_4_2);
					codeValuesRule5_4.put("NOT_CONTAIN_LAB165",values5_4_3);
					
					
					
					
					
					
					HashMap <String, ArrayList<String>> codeValuesRule5_2 = new HashMap<String, ArrayList<String>>();

					ArrayList<String> values5_2_1 = new ArrayList<String>();
					ArrayList<String> values5_2_2= new ArrayList<String>();

					values5_2_1.add("LAB673");//Nucleic Acid Amplification Test
					
					values5_2_2.add("260385009");
					values5_2_2.add("385660001");
					values5_2_2.add("UNK");
					values5_2_2.add("82334004");
					values5_2_2.add("443390004");
					values5_2_2.add("410530007");
					values5_2_2.add("PHC2092");
					
					
					codeValuesRule5_2.put("INV290",values5_2_1);
					codeValuesRule5_2.put("INV291",values5_2_2);
					
					ArrayList<String> TUB135Values = new ArrayList<String>();
					
					TUB135Values.add("260385009");
					TUB135Values.add("385660001");
					TUB135Values.add("UNK");
					TUB135Values.add("82334004");
					TUB135Values.add("443390004");
					TUB135Values.add("410530007");
					TUB135Values.add("PHC2092");
					
					HashMap <String, ArrayList<String>> codeValuesRule5_3 = new HashMap<String, ArrayList<String>>();
					
					
					ArrayList<String> values5_3_1 = new ArrayList<String>();
					ArrayList<String> values5_3_2= new ArrayList<String>();
					

					values5_3_1.add("TB119");//Culture

					values5_3_2.add("10828004");
					
					
					codeValuesRule5_3.put("INV290",values5_3_1);
					codeValuesRule5_3.put("INV291",values5_3_2);
					
					
					HashMap <String, ArrayList<String>> codeValuesRule5_5 = new HashMap<String, ArrayList<String>>();
					
					
					ArrayList<String> values5_5_1 = new ArrayList<String>();
					ArrayList<String> values5_5_2= new ArrayList<String>();
					

					values5_5_1.add("LAB671");
					values5_5_1.add("LAB672");
					values5_5_1.add("LAB720");
					values5_5_1.add("71773-6");

					values5_5_2.add("10828004");
					
					codeValuesRule5_5.put("INV290",values5_5_1);
					codeValuesRule5_5.put("INV291",values5_5_2);
					
					
					
					HashMap<String, String> criterias = new HashMap<String, String>();
				
			    	criterias.put("6038","Y");
			    	criterias.put("9384","Y");
			    	criterias.put("8987","Y");
			    	criterias.put("4110","Y");
			    	criterias.put("10109","Y");
			    	criterias.put("55672","Y");
			    	criterias.put("35617","Y");
			    	criterias.put("4127","Y");
			    	criterias.put("641","Y");
			    	criterias.put("6099","Y");
			    	criterias.put("78903","Y");
			    	criterias.put("2551","Y");
			    	criterias.put("82122","Y");
			    	criterias.put("7623","Y");
			    	criterias.put("139462","Y");
			    	criterias.put("PHC1888","Y");
			    	criterias.put("3007","Y");
			    	criterias.put("7833","Y");
			    	criterias.put("190376","Y");
			    	criterias.put("1364504","Y");
			    	criterias.put("PHC1889","Y");
			    	criterias.put("2592","Y");
			    	criterias.put("2198359","Y");
			    	criterias.put("NBS456","Y");
					
					/***************/
					
					//Values to be passed as parameters
				
					
			    	String INV1133 = parameters.get(0);//Site of TB Disease
			    	boolean INV1133Value = Boolean.parseBoolean(parameters.get(1));//Site of TB Disease
			    	String TUB122 = parameters.get(2);//Sputum Culture Result
			    	String TUB122Value = parameters.get(3);//Sputum Culture Result
					boolean existsInRepeatingBlockSputum =  Boolean.parseBoolean(parameters.get(4));
					
					
			    	String TUB130 =  parameters.get(5);//Other Culture Results
			    	String TUB130Value =  parameters.get(6);//Other Culture Results
					boolean existsInRepeatingBlockCulture =  Boolean.parseBoolean(parameters.get(7));
					
					
			    	String TUB135 =  parameters.get(8);//Nucleic Acid Amplification Results
			    	String TUB135Value =  parameters.get(9);//Nucleic Acid Amplification Results
			    	boolean existsInRepeatingBlockNAA =  Boolean.parseBoolean(parameters.get(10));
			    	
			    /*	String TUB141 = parameters.get(11);//Initial Chest X-Ray Result
			    	String TUB141Value = parameters.get(12);//Initial Chest X-Ray Result
					String TUB144 =  parameters.get(13);//Initial Chest CT Scan Result
					String TUB144Value  =  parameters.get(14);//Initial Chest CT Scan Result
				 	boolean existsInRepeatingBlockChest =  Boolean.parseBoolean(parameters.get(15));
				*/ 	
				 	
					String TUB147 =  parameters.get(11);//Tuberculin Skin Test Result
					String TUB147Value =  parameters.get(12);//Tuberculin Skin Test Result
					boolean existsInRepeatingBlockTB =  Boolean.parseBoolean(parameters.get(13));
				 	
					String TUB150 = parameters.get(14);//IGRA Qualitative Test Result
					String TUB150Value = parameters.get(15);//IGRA Qualitative Test Result
					boolean existsInRepeatingBlockIGRA =  Boolean.parseBoolean(parameters.get(16));
					
					boolean validateNCriteriaMet = Boolean.parseBoolean(parameters.get(17));
			
					boolean expectedValue =  Boolean.parseBoolean(parameters.get(18));
					
					
	
					PowerMockito.doReturn(INV1133Value).when(PageCreateHelper.class, "validateIfMultiIsPartOfValues",  form,INV1133Values, INV1133);
					
				//	PowerMockito.doReturn(INV1133Value).when(PageCreateHelper.class, "getValueFromForm",  form,INV1133);
					PowerMockito.doReturn(TUB122Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB122);
					PowerMockito.doReturn(TUB130Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB130);
					PowerMockito.doReturn(TUB135Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB135);
					//PowerMockito.doReturn(TUB141Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB141);
				//	PowerMockito.doReturn(TUB144Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB144);
					PowerMockito.doReturn(TUB147Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB147);
					PowerMockito.doReturn(TUB150Value).when(PageCreateHelper.class, "getValueFromForm",  form,TUB150);
					
					
					
					
					
					
					//We could use this, for simplicity
					//PowerMockito.doReturn(existsInRepeatingBlock).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,Mockito.anyMapOf(String.class, ArrayList.class));
					//But if we want to have control over which values are in the repeating block and which ones are not, so we can test different scenarios, then it is better to do this
					PowerMockito.doReturn(existsInRepeatingBlockSputum).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule5_1);
					PowerMockito.doReturn(existsInRepeatingBlockCulture).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule5_4);
					PowerMockito.doReturn(existsInRepeatingBlockNAA).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule5_2);
					PowerMockito.doReturn(existsInRepeatingBlockTB).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule5_3);
					PowerMockito.doReturn(existsInRepeatingBlockIGRA).when(PageCreateHelper.class, "checkIfRowValuesExistInRepeatingBlock",  form,subsectionId,codeValuesRule5_5);
					PowerMockito.doReturn(validateNCriteriaMet).when(PageCreateHelper.class, "validateIfNCriteriasMet",  form,criterias,2);

			  		
					
					
					
					boolean actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "validateRule5_part2", form);
				
					System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue+"\nActual value: "+actualResult);
					Assert.assertEquals(expectedValue, actualResult);
					System.out.println("PASSED");
					System.out.println("******************* End test case named: validateRule5_part2_test *******************");
				}	
	 }






/**
 * create_test contains a test method to check how many times the setVerCritTBLogicOnSubmit has been called.
 * @author Fatima.Lopezcalzado
 *
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({NBSContext.class, DynamicBeanBinding.class,PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
public static class Create_test{
	

//TODO: we might not need this now that we are using inner classes.
/*	private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public Create_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   ArrayList<String> validateCreate_100= new ArrayList<String>();
	   validateCreate_100.add("102201");//TB code
	   validateCreate_100.add("1");//times the setVerCritTBLogicOnSubmit method has ben called.
	   
	   ArrayList<String> validateCreate_101= new ArrayList<String>();
	   validateCreate_101.add("");//TB code
	   validateCreate_101.add("0");//times the setVerCritTBLogicOnSubmit method has ben called.
	   
	   ArrayList<String> validateCreate_102= new ArrayList<String>();
	   validateCreate_102.add("50258");//LTBI code
	   validateCreate_102.add("0");//times the setVerCritTBLogicOnSubmit method has ben called.
	   
	   ArrayList<String> validateCreate_103= new ArrayList<String>();
	   validateCreate_103.add("502582");//LTBI(2020)  code
	   validateCreate_103.add("0");//times the setVerCritTBLogicOnSubmit method has ben called.
	   
	   
	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule1_test
    		    
    		  {"validateRule1_test"+"_"+it++,validateCreate_100},
    		  {"validateRule1_test"+"_"+it++,validateCreate_101},
    		  {"validateRule1_test"+"_"+it++,validateCreate_102},
    		  {"validateRule1_test"+"_"+it++,validateCreate_103},
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
HttpSession session;

@Mock
NBSSecurityObj nbsSecurityObject;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Mock
ProgramAreaVO programAreaVO;

@Mock
UserProfile userProfile;

@Mock
User user;

@Mock
PageActProxyVO pageActProxyVO;


@Mock
PageClientVO pageClientVO;

@Mock
PamVO page;

@Mock
 HttpServletRequest request;
	
//@Spy
@InjectMocks
PageCreateHelper pageCreateHelper;// = PowerMockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		//  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 programAreaVO = Mockito.mock(ProgramAreaVO.class);
		 form = Mockito.mock(PageForm.class);
		 loggerMock = Mockito.mock(LogUtils.class);
	     request = Mockito.mock(HttpServletRequest.class);
	     session = Mockito.mock(HttpSession.class);
	     nbsSecurityObject = Mockito.mock(NBSSecurityObj.class); 
	     user = Mockito.mock(User.class);
	     userProfile = Mockito.mock(UserProfile.class);
	     pageClientVO = Mockito.mock(PageClientVO.class);
	     page = Mockito.mock(PamVO.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void create_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: create_test *******************");
				
				//Parameters
				String conditionCode = parameters.get(0);
				int timesCalled = Integer.parseInt(parameters.get(1));
				
				
				
				ProgramAreaVO programAreaVO2 = new ProgramAreaVO();
				Map<Object, Object> attributeMap = new HashMap<Object, Object>();
				 Map<Object, Object> answerMap = new HashMap<Object, Object>();
				 CoinfectionSummaryVO coinfectionSummaryVO = new CoinfectionSummaryVO();
				
				pageCreateHelper = PowerMockito.spy(new PageCreateHelper());
				
				
				Mockito.doReturn("TB").when(form).getPageFormCd();//This is just a random value to make it work
				
				PowerMockito.whenNew(PageCreateHelper.class).withNoArguments().thenReturn(pageCreateHelper);
				PowerMockito.doNothing().when(pageCreateHelper).loadQuestions(Matchers.any(String.class)); // 
				PowerMockito.doNothing().when(pageCreateHelper).handleFormRules(form,NEDSSConstants.CREATE_SUBMIT_ACTION);
		
			
				PowerMockito.mockStatic(PageCreateHelper.class);

				when(PageCreateHelper.getProgAreaVO(request.getSession())).thenReturn(programAreaVO2);
				    
				    
				when(request.getSession()).thenReturn(session);
			    when(nbsSecurityObject.getTheUserProfile()).thenReturn(userProfile);
			    when(nbsSecurityObject.getTheUserProfile().getTheUser()).thenReturn(user);
			    when(nbsSecurityObject.getTheUserProfile().getTheUser().getEntryID()).thenReturn("1000000L");
			    when(session.getAttribute("NBSSecurityObject")).thenReturn(nbsSecurityObject);
			    

				
				
				 attributeMap.put("headerConditionCode",conditionCode);//condition. If it is tb, it should call the setVerCritTBLogicOnSubmit method
				
				 when(form.getAttributeMap()).thenReturn(attributeMap);
				 when(pageClientVO.getAnswerMap()).thenReturn(answerMap);
				 when(form.getPageClientVO()).thenReturn(pageClientVO);
				
				
				 PowerMockito.mockStatic(NBSContext.class);
				 when(NBSContext.retrieve(session, NBSConstantUtil.DSPersonSummary)).thenReturn(123456789L);//Random value to make it work
				 when(NBSContext.retrieve(session,"DSCoinfectionInvSummVO")).thenReturn(coinfectionSummaryVO);//Random value to make it work
					
				
				 
				 
				 PowerMockito.mockStatic(DynamicBeanBinding.class);
				 when(DynamicBeanBinding.transferBeanValues(any(HashMap.class),any(Object.class), any(HashMap.class), any(String.class))).thenReturn(true);
					
				 
				PageProxyVO actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "create", form,request);
				
			
				PowerMockito.verifyStatic( Mockito.times(timesCalled)); // Verify that the following mock method was called exactly 1 time
				pageCreateHelper.setVerCritTBLogicOnSubmit(form);
				
			
				System.out.println("PASSED");
				System.out.println("******************* End test case named: create_test *******************");
			
		}		
	
}






/**
 * editHandler_test contains a test method to check how many times the setVerCritTBLogicOnSubmit has been called.
 * @author Fatima.Lopezcalzado
 *
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
public static class EditHandler_test{
	

//TODO: we might not need this now that we are using inner classes.
/*	private  enum Type {splitVerCtrlNbr_test, getCodeFromCodeDescription_test, getValueFromForm_test, validateIfNCriteriasMet_test,
		validateIfMultiIsPartOfValues_test, validateRule1_test,validateRule2_test, validateRule3_test,validateRule6_test,validateRule7_test,validateRule8_test, setVerCritTBLogicOnSubmit_test,checkIfRowValuesExistInRepeatingBlock_test};
	private Type type;*/
	private String iteration;
	ArrayList<String> parameters;
	
	
 public EditHandler_test(String it, ArrayList<String> parameters){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.parameters = parameters;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   ArrayList<String> validateEdit_100= new ArrayList<String>();
	   validateEdit_100.add("102201");//TB code
	   validateEdit_100.add("1");//times the setVerCritTBLogicOnSubmit method has ben called.
	   
	   ArrayList<String> validateEdit_101= new ArrayList<String>();
	   validateEdit_101.add("");//TB code
	   validateEdit_101.add("0");//times the setVerCritTBLogicOnSubmit method has ben called.
	   
	   ArrayList<String> validateEdit_102= new ArrayList<String>();
	   validateEdit_102.add("50258");//LTBI code
	   validateEdit_102.add("0");//times the setVerCritTBLogicOnSubmit method has ben called.
	   
	   ArrayList<String> validateEdit_103= new ArrayList<String>();
	   validateEdit_103.add("502582");//LTBI(2020) code
	   validateEdit_103.add("0");//times the setVerCritTBLogicOnSubmit method has ben called.
	   
	   ArrayList<String> validateEdit_104= new ArrayList<String>();
	   validateEdit_104.add(null);//LTBI(2020)  code
	   validateEdit_104.add("0");//times the setVerCritTBLogicOnSubmit method has ben called.
	   
	   ArrayList<String> validateEdit_105= new ArrayList<String>();
	   validateEdit_105.add("1022011");//TB code
	   validateEdit_105.add("0");//times the setVerCritTBLogicOnSubmit method has ben called.
	 
	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule1_test
    		    
    		  {"validateRule1_test"+"_"+it++,validateEdit_100},
    		  {"validateRule1_test"+"_"+it++,validateEdit_101},
    		  {"validateRule1_test"+"_"+it++,validateEdit_102},
    		  {"validateRule1_test"+"_"+it++,validateEdit_103},
    		  {"validateRule1_test"+"_"+it++,validateEdit_104},
    		  {"validateRule1_test"+"_"+it++,validateEdit_105},
    		  
    		  
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

@Mock
PageForm form;

@Mock
HttpSession session;

@Mock
NBSSecurityObj nbsSecurityObject;

@org.mockito.Mock
PropertyUtil propertyUtilMocked;

@org.mockito.Mock
LogUtils loggerMock;

@Mock
ProgramAreaVO programAreaVO;

@Mock
UserProfile userProfile;

@Mock
User user;

@Mock
PageActProxyVO pageActProxyVO;

@Mock
PersonVO personVO;

@Mock
PageClientVO pageClientVO;

@Mock
PropertyUtil propertyUtil;

@Mock
PageActProxyVO pageProxyVO;

@Mock
PublicHealthCaseVO publicHealthCaseVO;

@Mock
PublicHealthCaseDT publicHealthCaseDT;


@Mock
PamVO page;

@Mock
PageActProxyVO proxyVO;
@Mock
PersonDT personDT;

@Mock
 HttpServletRequest request;
	
//@Spy
@InjectMocks
PageCreateHelper pageCreateHelper;// = PowerMockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		//  PowerMockito.spy(PageCreateHelper.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 programAreaVO = Mockito.mock(ProgramAreaVO.class);
		 form = Mockito.mock(PageForm.class);
		 loggerMock = Mockito.mock(LogUtils.class);
	     request = Mockito.mock(HttpServletRequest.class);
	     session = Mockito.mock(HttpSession.class);
	     nbsSecurityObject = Mockito.mock(NBSSecurityObj.class); 
	     user = Mockito.mock(User.class);
	     userProfile = Mockito.mock(UserProfile.class);
	     pageClientVO = Mockito.mock(PageClientVO.class);
	     propertyUtil = PowerMockito.mock(PropertyUtil.class);
	     personVO = Mockito.mock(PersonVO.class);
	     pageProxyVO = Mockito.mock(PageActProxyVO.class);
	     personDT = Mockito.mock(PersonDT.class);
	     publicHealthCaseVO = Mockito.mock(PublicHealthCaseVO.class);
	     publicHealthCaseDT = Mockito.mock(PublicHealthCaseDT.class);
	     page = Mockito.mock(PamVO.class);
		 proxyVO = Mockito.mock(PageActProxyVO.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void editHandler_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: editHandler_test *******************");
				
				//Parameters
				String conditionCode = parameters.get(0);
				int timesCalled = Integer.parseInt(parameters.get(1));

				ProgramAreaVO programAreaVO2 = new ProgramAreaVO();
				Map<Object, Object> attributeMap = new HashMap<Object, Object>();
				Map<Object, Object> answerMap = new HashMap<Object, Object>();
				CoinfectionSummaryVO coinfectionSummaryVO = new CoinfectionSummaryVO();
				
				pageCreateHelper = PowerMockito.spy(new PageCreateHelper());
				
				
				Mockito.doReturn("TB").when(form).getPageFormCd();//This is just a random value to make it work
				
				PowerMockito.whenNew(PageCreateHelper.class).withNoArguments().thenReturn(pageCreateHelper);
				PowerMockito.doNothing().when(pageCreateHelper).loadQuestions(Matchers.any(String.class)); // 
				PowerMockito.doNothing().when(pageCreateHelper).handleFormRules(form,NEDSSConstants.CREATE_SUBMIT_ACTION);
				PowerMockito.doNothing().when(form).setDwrStateSiteCounties(any(ArrayList.class));
				PowerMockito.doNothing().when(pageCreateHelper).setRepeatingQuestionsBatch(any(PageForm.class),any(PageActProxyVO.class),any(String.class),any(String.class), any(Long.class));
					


				Map<Object, Object> answerDTMap = new HashMap<Object, Object>();
				when(page.getPageRepeatingAnswerDTMap()).thenReturn(answerDTMap);
				
				
			    when(proxyVO.getPageVO()).thenReturn(page);
				PowerMockito.whenNew(PageActProxyVO.class).withNoArguments().thenReturn(proxyVO);
				
				ArrayList<Object> countyCodes = new ArrayList<Object>();
				PowerMockito.mockStatic(CachedDropDowns.class);
				when(CachedDropDowns.getCountyCodes(any(String.class))).thenReturn(countyCodes);
	
				
				PowerMockito.mockStatic(PageCreateHelper.class);

				when(PageCreateHelper.getProgAreaVO(request.getSession())).thenReturn(programAreaVO2);
				
				
				    
				when(request.getSession()).thenReturn(session);
			    when(nbsSecurityObject.getTheUserProfile()).thenReturn(userProfile);
			    when(nbsSecurityObject.getTheUserProfile().getTheUser()).thenReturn(user);
			    when(nbsSecurityObject.getTheUserProfile().getTheUser().getEntryID()).thenReturn("1000000L");
			    when(session.getAttribute("NBSSecurityObject")).thenReturn(nbsSecurityObject);
			    

				
				
				 attributeMap.put("headerConditionCode",conditionCode);//condition. If it is tb, it should call the setVerCritTBLogicOnSubmit method
				
				 when(form.getAttributeMap()).thenReturn(attributeMap);
				 when(pageClientVO.getAnswerMap()).thenReturn(answerMap);
				 when(form.getPageClientVO()).thenReturn(pageClientVO);
				 when(form.getPageClientVO().getOldPageProxyVO()).thenReturn(pageProxyVO);
				 
				 ArrayList<Object> notificationCollection = new ArrayList<Object>();
				 when(pageProxyVO.getTheNotificationSummaryVOCollection()).thenReturn(notificationCollection);
				 
			
				 when(publicHealthCaseVO.getThePublicHealthCaseDT()).thenReturn(publicHealthCaseDT);
				 when(pageProxyVO.getPublicHealthCaseVO()).thenReturn(publicHealthCaseVO);
				 
				
				 PowerMockito.mockStatic(NBSContext.class);
				 when(NBSContext.retrieve(session, NBSConstantUtil.DSPersonSummary)).thenReturn(123456789L);//Random value to make it work
				 when(NBSContext.retrieve(session,"DSCoinfectionInvSummVO")).thenReturn(coinfectionSummaryVO);//Random value to make it work
			
				 PowerMockito.mockStatic(PropertyUtil.class);
				 PowerMockito.when(PropertyUtil.getInstance()).thenReturn(propertyUtil);
				 
				 
				 PowerMockito.mockStatic(DynamicBeanBinding.class);
				 when(DynamicBeanBinding.transferBeanValues(any(HashMap.class),any(Object.class), any(HashMap.class), any(String.class))).thenReturn(true);
					
				 
				 when(personDT.getPersonUid()).thenReturn(11111111L);//This is just an example
				 when(personVO.getThePersonDT()).thenReturn(personDT);
					
				 
				 
				 PowerMockito.mockStatic(PageLoadUtil.class);
				 PageLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, form.getPageClientVO().getOldPageProxyVO());
				 when(PageLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, form.getPageClientVO().getOldPageProxyVO())).thenReturn(personVO);
	
				 PageProxyVO actualResult = Whitebox.invokeMethod(new PageCreateHelper(), "editHandler", form,request);
				
			
				 PowerMockito.verifyStatic( Mockito.times(timesCalled)); // Verify that the following mock method was called exactly 1 time
				 pageCreateHelper.setVerCritTBLogicOnSubmit(form);
				
			
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: editHandler_test *******************");
			
		}		
	
}

/**
 * SetRaceForCreate_test: this unit test method consits of making sure the total number of races in ThePersonRaceDTCollection is the expected one based on the individual and detailed races sent as parameters,
 * and, specifically, making sure the values set for White races are correct. We could have expanded this specific testing to other races,
 * but we decided to just do it for White, as the others as pretty similar.
 * @author Fatima.Lopezcalzado
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetRaceForCreate_test{
	

	private String iteration;
	private String userId;
	private Long personUid;
	

	private int unknownRace;
	private int otherRace;
	private int refusedToAnswer;
	private int notAsked;
	private int africanAmerican;
	private int americanIndianAlaskanRace;
	private int whiteRace;
	private int asianRace;
	private int hawaiianRace;
	private String[] detailedRaceAsian;
	private String[] detailedRaceHawaii;
	private String[] detailedRaceWhite;
	private String[] detailedAfricanAmerican;
	private String[] detailedAian;
	private int numberOfExpectedRacesInCollection;
	
	
 public SetRaceForCreate_test(String it, String userId, Long personUid, int unknownRace, int otherRace, int refusedToAnswer, int notAsked,
		 int africanAmerican, int americanIndianAlaskanRace, int whiteRace, int asianRace, int hawaiianRace, String[] detailedRaceAsian, 
		 String[] detailedRaceHawaii, String[] detailedRaceWhite, String[] detailedAfricanAmerican, String[] detailedAian, int numberOfExpectedRacesInCollection){
	 
	 super();

	 this.iteration = it;
	 this.userId = userId;
	 this.personUid = personUid;
	 this.unknownRace = unknownRace;
	 this.otherRace = otherRace;
	 this.refusedToAnswer = refusedToAnswer;
	 this.notAsked = notAsked;
	 this.africanAmerican = africanAmerican;
	 this.americanIndianAlaskanRace = americanIndianAlaskanRace;
	 this.whiteRace = whiteRace;
	 this.asianRace = asianRace;
	 this.hawaiianRace = hawaiianRace;
	 this.detailedRaceAsian = detailedRaceAsian;
	 this.detailedRaceHawaii = detailedRaceHawaii;
	 this.detailedRaceWhite = detailedRaceWhite;
	 this.detailedAfricanAmerican = detailedAfricanAmerican;
	 this.detailedAian = detailedAian;
	 this.numberOfExpectedRacesInCollection = numberOfExpectedRacesInCollection;
	 
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	   
	   int it = 0;

      return Arrays.asList(new Object[][]{

    		
    		   //iteration, userId, personUid,unknownRace, otherRace, refusedToAnswer, notAsked, africanAmerican, americanIndianAlaskanRace
    			//whiteRace, asianRace, hawaiianRace, detailedRaceAsian, detailedRaceHawaii, detailedRaceWhite, detailedAfricanAmerican, detailedAian
    		  {"setRaceForCreate_test"+"_"+it++,"100000000", 100000L,0,0,0,0,0,0,0,0,0, new String[]{"2029-7","2031-3"},
    			  new String[]{"2100-6","2085-9"},new String[]{"2129-5"},new String[]{"2060-2","2058-6"},
    			  new String[]{"1002-5","1004-1"}, 9},
    			  {"setRaceForCreate_test"+"_"+it++,"100000000", 100000L,0,1,1,0,0,0,1,1,1, new String[]{"2029-7",""},
        			  new String[]{"2100-6","2085-9"},new String[]{"2129-5"},new String[]{"2060-2","2058-6"},
        			  new String[]{"1002-5","1004-1"}, 13}, 
        			  {"setRaceForCreate_test"+"_"+it++,"100000000", 100000L,0,0,0,0,0,0,0,0,0, new String[]{},
            			  new String[]{},new String[]{},new String[]{},
            			  new String[]{}, 0},
            			  {"setRaceForCreate_test"+"_"+it++,"100000000", 100000L,1,1,1,1,1,1,1,1,1, new String[]{},
                			  new String[]{},new String[]{},new String[]{},
                			  new String[]{}, 9},
                			  {"setRaceForCreate_test"+"_"+it++,"100000000", 100000L,0,0,0,0,0,0,0,0,0, new String[]{"2029-7","2031-3"},
                    			  new String[]{"2100-6","2085-9"},new String[]{"2106-3", "2106-3", "2106-3"},new String[]{"2060-2","2058-6"},
                    			  new String[]{"1002-5","1004-1"}, 11},
                    			  {"setRaceForCreate_test"+"_"+it++,"100000000", 100000L,0,1,1,0,0,0,1,1,1, new String[]{"2029-7",""},
                        			  new String[]{"2100-6","2085-9"},new String[]{"2129-5","2108"},new String[]{"2060-2","2058-6"},
                        			  new String[]{"1002-5","1004-1"}, 14}, 
                        			  {"setRaceForCreate_test"+"_"+it++,"100000000", 100000L,1,0,0,0,0,0,0,0,0, new String[]{},
                            			  new String[]{"2085-9"},new String[]{"2108"},new String[]{"2058-6"},
                            			  new String[]{"1004-1"}, 5}, 
                            			  {"setRaceForCreate_test"+"_"+it++,"100000000", 100000L,1,0,0,0,0,0,0,0,0, null,
                                			  null,null,null,
                                			 null, 1}, 
                			    
    			  
    		 
    		  
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



@org.mockito.Mock
LogUtils loggerMock;
@Mock
PersonVO personVO;
@Mock
PageClientVO clientVO;
@Mock
PageActProxyVO proxyVO;
@Mock
PersonDT personDT;

@InjectMocks
PageCreateHelper pageCreateHelper;// = PowerMockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		
		 loggerMock = Mockito.mock(LogUtils.class);
	     personVO = Mockito.mock(PersonVO.class);
	     personDT = Mockito.mock(PersonDT.class);
		 proxyVO = Mockito.mock(PageActProxyVO.class);
		 clientVO = Mockito.mock(PageClientVO.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setRaceForCreate_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setRaceForCreate_test *******************");
				Map<Object, Object> answerMap = new HashMap <Object, Object>();
				
			
				//personVO.getThePersonDT().getPersonUid();
				//In this case we are using real objects, instead of mocked object because we need to read the information set in the PersonVO1
				//by the time the method that we are testing finished, to make sure the method works correctly (meaning, read this value:  personVO.setThePersonRaceDTCollection(raceColl);)
				
				PersonVO personVO1 = new PersonVO();
				PersonDT personDT1 = new PersonDT();
				personDT1.setPersonUid(personUid);
				personVO1.setThePersonDT(personDT1);
			
				//personVO.getThePersonDT().getPersonUid();
			//	when(personDT.getPersonUid()).thenReturn(personUid);  
		//		when(personVO.getThePersonDT()).thenReturn(personDT);  

				
				//String asOfDate = getVal(clientVO.getAnswerMap().get(PageConstants.RACE_INFORMATION_AS_OF));
				String asOfDate = "11/10/2022";
				answerMap.put(PageConstants.RACE_INFORMATION_AS_OF, asOfDate);
				when(clientVO.getAnswerMap()).thenReturn(answerMap);  
				
			
				
				when(clientVO.getUnKnownRace()).thenReturn(unknownRace);  
				when(clientVO.getOtherRace()).thenReturn(otherRace);  
				when(clientVO.getRefusedToAnswer()).thenReturn(refusedToAnswer);  
				when(clientVO.getNotAsked()).thenReturn(notAsked);  
				when(clientVO.getAfricanAmericanRace()).thenReturn(africanAmerican);  
				when(clientVO.getAmericanIndianAlskanRace()).thenReturn(americanIndianAlaskanRace);  
				when(clientVO.getWhiteRace()).thenReturn(whiteRace);  
				when(clientVO.getAsianRace()).thenReturn(asianRace);  
				when(clientVO.getHawaiianRace()).thenReturn(hawaiianRace);  
				when(clientVO.getAnswerArray(PageConstants.DETAILED_RACE_ASIAN)).thenReturn(detailedRaceAsian);
				when(clientVO.getAnswerArray(PageConstants.DETAILED_RACE_HAWAII)).thenReturn(detailedRaceHawaii);			
				when(clientVO.getAnswerArray(PageConstants.DETAILED_RACE_WHITE)).thenReturn(detailedRaceWhite);
				when(clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AFRICAN_AMERICAN)).thenReturn(detailedAfricanAmerican);
				when(clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE)).thenReturn(detailedAian);
				
				
				
				Whitebox.invokeMethod(new PageCreateHelper(), "setRaceForCreate", personVO1, clientVO, proxyVO, userId);
				Collection<Object> races = personVO1.getThePersonRaceDTCollection();
				int actualNumberRacesInCollectionActual = 0;
				 if(races!=null)
				 	actualNumberRacesInCollectionActual = personVO1.getThePersonRaceDTCollection().size();
				 
				System.out.println("Iteration: #"+iteration+"\nExpected number of races in the collection: "+numberOfExpectedRacesInCollection+"\nActual number of races in the collection: "+actualNumberRacesInCollectionActual);
				Assert.assertEquals(numberOfExpectedRacesInCollection, actualNumberRacesInCollectionActual);
					int iWhite = 0;
					boolean firstRace = true;
					
					
					System.out.println("-------------------------------------------------------------------------------------");
					if(races!=null){
					Iterator ite = races.iterator();
					while (ite.hasNext()) {
						PersonRaceDT personRaceDT = (PersonRaceDT) ite.next();
						

						String categoryActual = personRaceDT.getRaceCategoryCd();
						//Checking on a deeper level for White
						if(categoryActual!=null && categoryActual.equalsIgnoreCase("2106-3")){//white
							System.out.println("\nWhite Race testing:\n");
							Long personUidActual = personRaceDT.getPersonUid();
							Long userIdActual = personRaceDT.getAddUserId();
							String recordStatusCdActual = personRaceDT.getRecordStatusCd();
							String raceActual = personRaceDT.getRaceCd();
							
							String catergoryExpected = "2106-3";
							String recordStatusCdExpected = NEDSSConstants.RECORD_STATUS_ACTIVE;
							String raceExpected = "";
							
							if(iWhite==0 && whiteRace==1 && firstRace){
								raceExpected = "2106-3";
								firstRace = false;
							}
							else{
								raceExpected=detailedRaceWhite[iWhite];
								iWhite++;
							}
									
							System.out.println("\nRace cd: "+ personRaceDT.getRaceCd());
							System.out.println("\nExpected person uid: "+personUid+"\nActual person uid: "+personUidActual);
							System.out.println("\nExpected category: "+catergoryExpected+"\nActual category: "+categoryActual);
							System.out.println("\nExpected add user id: "+userId+"\nActual add user id: "+userIdActual);
							System.out.println("\nExpected record status cd: "+recordStatusCdExpected+"\nActual record status cd: "+recordStatusCdActual);
							System.out.println("\nExpected race cd: "+raceExpected+"\nActual race cd: "+raceActual);
							
						
							Assert.assertEquals(personUid, personUidActual);
							Assert.assertEquals(catergoryExpected, categoryActual);
							Assert.assertEquals(userId, userIdActual+"");
							Assert.assertEquals(recordStatusCdExpected, recordStatusCdActual);
							Assert.assertEquals(raceExpected, raceActual);
							
							
							
						}	
							
					}
				}
	
			
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setRaceForCreate_test *******************");
			
		}		
	
}



/**
 * SetRaceForCreate2_test: this unit test method consits of making sure the total number of races in ThePersonRaceDTCollection is the expected one based on the individual and detailed races sent as parameters,
 * and, specifically, making sure the values set for White races are correct. We could have expanded this specific testing to other races,
 * but we decided to just do it for White, as the others as pretty similar.
 * @author Fatima.Lopezcalzado
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetRaceForCreate2_test{
	
	private String iteration;
	private String userId;
	private Long personUid;
	

	private int unknownRace;
	private int otherRace;
	private int refusedToAnswer;
	private int notAsked;
	private int africanAmerican;
	private int americanIndianAlaskanRace;
	private int whiteRace;
	private int asianRace;
	private int hawaiianRace;
	private String[] detailedRaceAsian;
	private String[] detailedRaceHawaii;
	private String[] detailedRaceWhite;
	private String[] detailedAfricanAmerican;
	private String[] detailedAian;
	private int numberOfExpectedRacesInCollection;
	
	
 public SetRaceForCreate2_test(String it, String userId, Long personUid, int unknownRace, int otherRace, int refusedToAnswer, int notAsked,
		 int africanAmerican, int americanIndianAlaskanRace, int whiteRace, int asianRace, int hawaiianRace, String[] detailedRaceAsian, 
		 String[] detailedRaceHawaii, String[] detailedRaceWhite, String[] detailedAfricanAmerican, String[] detailedAian, int numberOfExpectedRacesInCollection){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.userId = userId;
	 this.personUid = personUid;
	 this.unknownRace = unknownRace;
	 this.otherRace = otherRace;
	 this.refusedToAnswer = refusedToAnswer;
	 this.notAsked = notAsked;
	 this.africanAmerican = africanAmerican;
	 this.americanIndianAlaskanRace = americanIndianAlaskanRace;
	 this.whiteRace = whiteRace;
	 this.asianRace = asianRace;
	 this.hawaiianRace = hawaiianRace;
	 this.detailedRaceAsian = detailedRaceAsian;
	 this.detailedRaceHawaii = detailedRaceHawaii;
	 this.detailedRaceWhite = detailedRaceWhite;
	 this.detailedAfricanAmerican = detailedAfricanAmerican;
	 this.detailedAian = detailedAian;
	 this.numberOfExpectedRacesInCollection = numberOfExpectedRacesInCollection;
	 
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   
	  int it = 0;

      return Arrays.asList(new Object[][]{

    		
    		   //iteration, userId, personUid,unknownRace, otherRace, refusedToAnswer, notAsked, africanAmerican, americanIndianAlaskanRace
    			//whiteRace, asianRace, hawaiianRace, detailedRaceAsian, detailedRaceHawaii, detailedRaceWhite, detailedAfricanAmerican, detailedAian
    		  {"setRaceForCreate2_test"+"_"+it++,"100000000", 100000L,0,0,0,0,0,0,0,0,0, new String[]{"2029-7","2031-3"},
    			  new String[]{"2100-6","2085-9"},new String[]{"2129-5"},new String[]{"2060-2","2058-6"},
    			  new String[]{"1002-5","1004-1"}, 9},
    			  {"setRaceForCreate2_test"+"_"+it++,"100000000", 100000L,0,1,1,0,0,0,1,1,1, new String[]{"2029-7",""},
        			  new String[]{"2100-6","2085-9"},new String[]{"2129-5"},new String[]{"2060-2","2058-6"},
        			  new String[]{"1002-5","1004-1"}, 13}, 
        			  {"setRaceForCreate2_test"+"_"+it++,"100000000", 100000L,0,0,0,0,0,0,0,0,0, new String[]{},
            			  new String[]{},new String[]{},new String[]{},
            			  new String[]{}, 0},
            			  {"setRaceForCreate2_test"+"_"+it++,"100000000", 100000L,1,1,1,1,1,1,1,1,1, new String[]{},
                			  new String[]{},new String[]{},new String[]{},
                			  new String[]{}, 9},
                			  {"setRaceForCreate2_test"+"_"+it++,"100000000", 100000L,0,0,0,0,0,0,0,0,0, new String[]{"2029-7","2031-3"},
                    			  new String[]{"2100-6","2085-9"},new String[]{"2106-3", "2106-3", "2106-3"},new String[]{"2060-2","2058-6"},
                    			  new String[]{"1002-5","1004-1"}, 11},
                    			  {"setRaceForCreate2_test"+"_"+it++,"100000000", 100000L,0,1,1,0,0,0,1,1,1, new String[]{"2029-7",""},
                        			  new String[]{"2100-6","2085-9"},new String[]{"2129-5","2108"},new String[]{"2060-2","2058-6"},
                        			  new String[]{"1002-5","1004-1"}, 14}, 
                        			  {"setRaceForCreate2_test"+"_"+it++,"100000000", 100000L,1,0,0,0,0,0,0,0,0, new String[]{},
                            			  new String[]{"2085-9"},new String[]{"2108"},new String[]{"2058-6"},
                            			  new String[]{"1004-1"}, 5}, 
                            			  {"setRaceForCreate2_test"+"_"+it++,"100000000", 100000L,1,0,0,0,0,0,0,0,0, null,
                                			  null,null,null,
                                			 null, 1}, 
                            			
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



@org.mockito.Mock
LogUtils loggerMock;
@Mock
PersonVO personVO;
@Mock
PageClientVO clientVO;
@Mock
PersonDT personDT;

@InjectMocks
PageCreateHelper pageCreateHelper;// = PowerMockito.spy(new PageCreateHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		
		 loggerMock = Mockito.mock(LogUtils.class);
	     personVO = Mockito.mock(PersonVO.class);
	     personDT = Mockito.mock(PersonDT.class);
	     clientVO = Mockito.mock(PageClientVO.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setRaceForCreate2_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setRaceForCreate2_test *******************");
				Map<Object, Object> answerMap = new HashMap <Object, Object>();
				
			
				//personVO.getThePersonDT().getPersonUid();
				//In this case we are using real objects, instead of mocked object because we need to read the information set in the PersonVO1
				//by the time the method that we are testing finished, to make sure the method works correctly (meaning, read this value:  personVO.setThePersonRaceDTCollection(raceColl);)
				
				PersonVO personVO1 = new PersonVO();
				PersonDT personDT1 = new PersonDT();
				personDT1.setPersonUid(personUid);
				personVO1.setThePersonDT(personDT1);
			
				//personVO.getThePersonDT().getPersonUid();
			//	when(personDT.getPersonUid()).thenReturn(personUid);  
		//		when(personVO.getThePersonDT()).thenReturn(personDT);  

				
				//String asOfDate = getVal(clientVO.getAnswerMap().get(PageConstants.RACE_INFORMATION_AS_OF));
				String asOfDate = "11/10/2022";
				answerMap.put(PageConstants.RACE_INFORMATION_AS_OF, asOfDate);
				when(clientVO.getAnswerMap()).thenReturn(answerMap);  
				
			
				
				when(clientVO.getUnKnownRace()).thenReturn(unknownRace);  
				when(clientVO.getOtherRace()).thenReturn(otherRace);  
				when(clientVO.getRefusedToAnswer()).thenReturn(refusedToAnswer);  
				when(clientVO.getNotAsked()).thenReturn(notAsked);  
				when(clientVO.getAfricanAmericanRace()).thenReturn(africanAmerican);  
				when(clientVO.getAmericanIndianAlskanRace()).thenReturn(americanIndianAlaskanRace);  
				when(clientVO.getWhiteRace()).thenReturn(whiteRace);  
				when(clientVO.getAsianRace()).thenReturn(asianRace);  
				when(clientVO.getHawaiianRace()).thenReturn(hawaiianRace);  
				when(clientVO.getAnswerArray(PageConstants.DETAILED_RACE_ASIAN)).thenReturn(detailedRaceAsian);
				when(clientVO.getAnswerArray(PageConstants.DETAILED_RACE_HAWAII)).thenReturn(detailedRaceHawaii);			
				when(clientVO.getAnswerArray(PageConstants.DETAILED_RACE_WHITE)).thenReturn(detailedRaceWhite);
				when(clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AFRICAN_AMERICAN)).thenReturn(detailedAfricanAmerican);
				when(clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE)).thenReturn(detailedAian);
				
				
				
				Whitebox.invokeMethod(new PageCreateHelper(), "setRaceForCreate", personVO1, clientVO, userId);
				Collection<Object> races = personVO1.getThePersonRaceDTCollection();
				int actualNumberRacesInCollectionActual = 0;
				 if(races!=null)
				 	actualNumberRacesInCollectionActual = personVO1.getThePersonRaceDTCollection().size();
				 
				System.out.println("Iteration: #"+iteration+"\nExpected number of races in the collection: "+numberOfExpectedRacesInCollection+"\nActual number of races in the collection: "+actualNumberRacesInCollectionActual);
				Assert.assertEquals(numberOfExpectedRacesInCollection, actualNumberRacesInCollectionActual);
					int iWhite = 0;
					boolean firstRace = true;
					System.out.println("-------------------------------------------------------------------------------------");
				if(races!=null){
					Iterator ite = races.iterator();
					while (ite.hasNext()) {
						PersonRaceDT personRaceDT = (PersonRaceDT) ite.next();
						

						String categoryActual = personRaceDT.getRaceCategoryCd();
						//Checking on a deeper level for White
						if(categoryActual!=null && categoryActual.equalsIgnoreCase("2106-3")){//white
							System.out.println("\nWhite Race testing:\n");
							Long personUidActual = personRaceDT.getPersonUid();
							Long userIdActual = personRaceDT.getAddUserId();
							String recordStatusCdActual = personRaceDT.getRecordStatusCd();
							String raceActual = personRaceDT.getRaceCd();
							
							String catergoryExpected = "2106-3";
							String recordStatusCdExpected = NEDSSConstants.RECORD_STATUS_ACTIVE;
							String raceExpected = "";
							
							if(iWhite==0 && whiteRace==1 && firstRace){
								raceExpected = "2106-3";
								firstRace = false;
							}
							else{
								raceExpected=detailedRaceWhite[iWhite];
								iWhite++;
							}
									
							System.out.println("\nRace cd: "+ personRaceDT.getRaceCd());
							System.out.println("\nExpected person uid: "+personUid+"\nActual person uid: "+personUidActual);
							System.out.println("\nExpected category: "+catergoryExpected+"\nActual category: "+categoryActual);
							System.out.println("\nExpected add user id: "+userId+"\nActual add user id: "+userIdActual);
							System.out.println("\nExpected record status cd: "+recordStatusCdExpected+"\nActual record status cd: "+recordStatusCdActual);
							System.out.println("\nExpected race cd: "+raceExpected+"\nActual race cd: "+raceActual);
							
						
							Assert.assertEquals(personUid, personUidActual);
							Assert.assertEquals(catergoryExpected, categoryActual);
							Assert.assertEquals(userId, userIdActual+"");
							Assert.assertEquals(recordStatusCdExpected, recordStatusCdActual);
							Assert.assertEquals(raceExpected, raceActual);
							
							
							
						}	
							
					}
				}
	
			
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setRaceForCreate2_test *******************");
			
		}		
	
}





/**
 * SetRaceForEdit_test: this unit test method consists of several verifications:
 * 
 * - Making sure the number of races in the collection PersonRaceDTCollection is correct based on the individual races and detailed races, and knowing that, by default,
 * we have included some default races selected in the PersonRaceDTCollection to simulate the edition of existing races plus new selected races.
 * - Making sure that, for race White, the values set are the expected one. We could have done the same one with other races, but we decided to only check
 * one of them, because they are pretty similar.
 * - The number of races in PersonRaceDTCollection set for add / edit are the expected ones.
 * - The number of races under ClientVO are the expected one, knowing that only detailed races will be added if the parent race is added, otherwise, it would be removed.
 * @author Fatima.Lopezcalzado
 *
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetRaceForEdit_test{
	
	private String iteration;
	private Long personUid;
	

	private int unknownRace;
	private int otherRace;
	private int refusedToAnswer;
	private int notAsked;
	private int africanAmerican;
	private int americanIndianAlaskanRace;
	private int whiteRace;
	private int asianRace;
	private int hawaiianRace;
	private String[] detailedRaceAsian;
	private String[] detailedRaceHawaii;
	private String[] detailedRaceWhite;
	private String[] detailedAfricanAmerican;
	private String[] detailedAian;
	private int numberOfExpectedRacesInCollection;
	private int expectedNew;
	private int expectedUpdate;
	
	
 public SetRaceForEdit_test(String it, Long personUid, int unknownRace, int otherRace, int refusedToAnswer, int notAsked,
		 int africanAmerican, int americanIndianAlaskanRace, int whiteRace, int asianRace, int hawaiianRace, String[] detailedRaceAsian, 
		 String[] detailedRaceHawaii, String[] detailedRaceWhite, String[] detailedAfricanAmerican, String[] detailedAian, int numberOfExpectedRacesInCollection, int expectedNew, int expectedUpdated){
	 
	 super();

	 this.iteration = it;
	// this.userId = userId;
	 this.personUid = personUid;
	 this.unknownRace = unknownRace;
	 this.otherRace = otherRace;
	 this.refusedToAnswer = refusedToAnswer;
	 this.notAsked = notAsked;
	 this.africanAmerican = africanAmerican;
	 this.americanIndianAlaskanRace = americanIndianAlaskanRace;
	 this.whiteRace = whiteRace;
	 this.asianRace = asianRace;
	 this.hawaiianRace = hawaiianRace;
	 this.detailedRaceAsian = detailedRaceAsian;
	 this.detailedRaceHawaii = detailedRaceHawaii;
	 this.detailedRaceWhite = detailedRaceWhite;
	 this.detailedAfricanAmerican = detailedAfricanAmerican;
	 this.detailedAian = detailedAian;
	 this.numberOfExpectedRacesInCollection = numberOfExpectedRacesInCollection;//It will only count if the main class has been selected, otherwise, the detailed race is removed from the client answer array map and cannot be count in the PersonRaceDTCollection
	 this.expectedNew = expectedNew;//New races that are not already part of the PersonRaceDTCollection
	 this.expectedUpdate = expectedUpdated;//according to our test, 3 of them will be always updted, because they are already in the collection
	 
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;

      return Arrays.asList(new Object[][]{
    		  
    		
    		   //iteration, userId, personUid
    		  //unknownRace, otherRace, refusedToAnswer, notAsked, africanAmerican, americanIndianAlaskanRace
    			//whiteRace, asianRace, hawaiianRace, detailedRaceAsian, detailedRaceHawaii, detailedRaceWhite, detailedAfricanAmerican, detailedAian
    		 //total in collection
    		  //new, updated
    	
    		  
    		  
    		  
    			  {"setRaceForEdit_test"+"_"+it++,100000L,0/*U*/,1/*other*/,1/*refused*/,0/*not asked*/,0/*african american*/,
    				  0/*american indian alaskan*/,1/*white*/,1/*asian*/,1/*hawaiian*/,
    				  new String[]{"2029-7"/*Asian*/},
        			  new String[]{"2100-6","2085-9"/*hawaiian*/},new String[]{"2129-5","2108-9"/*White*/},new String[]{"2060-2"}/*African american*/,
        			  new String[]{"1002-5"/*1735-0*/}, 11, 8, 3}, 
        
        			  

        			  {"setRaceForEdit_test"+"_"+it++,100000L,0/*U*/,0/*other*/,0/*refused*/,0/*not asked*/,0/*african american*/,
        				  0/*american indian alaskan*/,1/*white*/,1/*asian*/,1/*hawaiian*/,
        				  new String[]{"2029-7"/*Asian*/},
            			  new String[]{"2100-6","2085-9"/*hawaiian*/},new String[]{"2129-5","2108-9"/*White*/},new String[]{"2060-2"}/*African american*/,
            			  new String[]{"1002-5"/*1735-0*/}, 9, 6, 3}, 
            			  

            			  {"setRaceForEdit_test"+"_"+it++,100000L,1/*U*/,1/*other*/,1/*refused*/,1/*not asked*/,1/*african american*/,
            				  1/*american indian alaskan*/,1/*white*/,1/*asian*/,1/*hawaiian*/,
            				  new String[]{"2029-7"/*Asian*/},
                			  new String[]{"2100-6","2085-9"/*hawaiian*/},new String[]{"2129-5","2108-9"/*White*/},new String[]{"2060-2"}/*African american*/,
                			  new String[]{"1002-5"/*1735-0*/}, 16, 13, 3}, 
                			  
                			  
                			  
                			  {"setRaceForEdit_test"+"_"+it++,100000L,1/*U*/,1/*other*/,1/*refused*/,1/*not asked*/,1/*african american*/,
                				  1/*american indian alaskan*/,1/*white*/,1/*asian*/,1/*hawaiian*/,
                				  new String[]{"2029-7", "2030-5","2031-3","2032-1","2033-9","2034-7","2036-2","2037-0","2038-8","2048-7","2039-6","2040-4","2041-2","2052-9","2042-0","2049-5","2050-3", "2043-8", "2044-6", "2051-1", "2045-3", "2035-4", "2046-1", "2047-9"/*Asian*/},
                    			  new String[]{"2100-6","2085-9","2500-7", "2078-4"/*hawaiian*/},new String[]{"2129-5","2108-9","2118-8"/*White*/},new String[]{"2060-2","2058-6","2067-7","2068-5","2056-0","2070-1","2069-3","2071-9","2072-7","2073-5","2074-3"}/*African american*/,
                    			  new String[]{"1002-5","1004-1"/*1735-0*/}, 53, 50, 3}, 
                    			  
                			  
                    			  {"setRaceForEdit_test"+"_"+it++,100000L,0/*U*/,0/*other*/,0/*refused*/,0/*not asked*/,0/*african american*/,
                    				  0/*american indian alaskan*/,0/*white*/,0/*asian*/,0/*hawaiian*/,
                    				  new String[]{},
                        			  new String[]{},new String[]{/*White*/},new String[]{}/*African american*/,
                        			  new String[]{}, 3, 0, 3}, 
                        			  
                        			  {"setRaceForEdit_test"+"_"+it++,100000L,1/*U*/,0/*other*/,0/*refused*/,0/*not asked*/,0/*african american*/,
                        				  0/*american indian alaskan*/,0/*white*/,0/*asian*/,0/*hawaiian*/,
                        				  new String[]{"2029-7"/*Asian*/},
                            			  new String[]{"2100-6","2085-9"/*hawaiian*/},new String[]{"2129-5","2108-9"/*White*/},new String[]{"2060-2"}/*African american*/,
                            			  new String[]{"1002-5"/*1735-0*/}, 3, 0, 3}, 
                            			  
                            			  {"setRaceForEdit_test"+"_"+it++,100000L,0/*U*/,0/*other*/,0/*refused*/,0/*not asked*/,0/*african american*/,
                            				  0/*american indian alaskan*/,0/*white*/,1/*asian*/,0/*hawaiian*/,
                            				  new String[]{"2029-7", "2030-5","2031-3","2032-1","2033-9","2034-7","2036-2","2037-0","2038-8","2048-7","2039-6","2040-4","2041-2","2052-9","2042-0","2049-5","2050-3", "2043-8", "2044-6", "2051-1", "2045-3", "2035-4", "2046-1", "2047-9"/*Asian*/},
                                			  new String[]{/*hawaiian*/},new String[]{/*White*/},new String[]{}/*African american*/,
                                			  new String[]{/*1735-0*/}, 27, 24, 3}, 

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



@org.mockito.Mock
LogUtils loggerMock;
@Mock
PersonVO personVO;


@Mock
PageActProxyVO proxyVO;
@Mock
PersonDT personDT;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper=Mockito.spy(new PageCreateHelper());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageCreateHelper.class);
		 loggerMock = Mockito.mock(LogUtils.class);
	     personVO = Mockito.mock(PersonVO.class);
	     personDT = Mockito.mock(PersonDT.class);
		 proxyVO = Mockito.mock(PageActProxyVO.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setRaceForEdit_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setRaceForEdit_test *******************");
				Map<Object, Object> answerMap = new HashMap <Object, Object>();
				
			
				//personVO.getThePersonDT().getPersonUid();
				//In this case we are using real objects, instead of mocked object because we need to read the information set in the PersonVO1
				//by the time the method that we are testing finished, to make sure the method works correctly (meaning, read this value:  personVO.setThePersonRaceDTCollection(raceColl);)
				
				PersonVO personVO1 = new PersonVO();
				PersonDT personDT1 = new PersonDT();
				personDT1.setPersonUid(personUid);
				personVO1.setThePersonDT(personDT1);
			
				//personVO.getThePersonDT().getPersonUid();
			//	when(personDT.getPersonUid()).thenReturn(personUid);  
		//		when(personVO.getThePersonDT()).thenReturn(personDT);  

				
				//ClientVO
				
				PageClientVO clientVO = new PageClientVO();
				
				
				
				//String asOfDate = getVal(clientVO.getAnswerMap().get(PageConstants.RACE_INFORMATION_AS_OF));
				String asOfDate = "11102022";//TODO: Fatima, is this format correct?? If not, get it while debugging from a real scenario
				answerMap.put(PageConstants.RACE_INFORMATION_AS_OF, asOfDate);
				//when(clientVO.getAnswerMap()).thenReturn(answerMap);  
				
				clientVO.setAnswerMap(answerMap);
				
				clientVO.setUnKnownRace(unknownRace);
				clientVO.setOtherRace(otherRace);
				clientVO.setRefusedToAnswer(refusedToAnswer);
				clientVO.setNotAsked(notAsked);
				clientVO.setAfricanAmericanRace(africanAmerican);
				clientVO.setAmericanIndianAlskanRace(americanIndianAlaskanRace);
				clientVO.setWhiteRace(whiteRace);
				clientVO.setAsianRace(asianRace);
				clientVO.setHawaiianRace(hawaiianRace);
				
			
				Map<Object,Object> answerArrayMap = new HashMap<Object,Object>();
				answerArrayMap.put(PageConstants.DETAILED_RACE_ASIAN, detailedRaceAsian);
				answerArrayMap.put(PageConstants.DETAILED_RACE_HAWAII, detailedRaceHawaii);
				answerArrayMap.put(PageConstants.DETAILED_RACE_WHITE, detailedRaceWhite);
				answerArrayMap.put(PageConstants.DETAILED_RACE_AFRICAN_AMERICAN, detailedAfricanAmerican);
				answerArrayMap.put(PageConstants.DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE, detailedAian);
				
				clientVO.setAnswerArrayMap(answerArrayMap);
				
				//default values for personVO.getThePersonRaceDTCollection() (simulating pre-existing for races)
				//Asian, Unknown and white
				PersonRaceDT raceDTWhite = new PersonRaceDT();
				raceDTWhite.setRaceCd("2106-3");
				raceDTWhite.setItNew(false);
				
				PersonRaceDT raceDTUnknown = new PersonRaceDT();
				raceDTUnknown.setItNew(false);
				raceDTUnknown.setRaceCd("U");
				
				PersonRaceDT raceDTAsian = new PersonRaceDT();
				raceDTAsian.setItNew(false);
				raceDTAsian.setRaceCd("2028-9");
		
				
				Collection<Object> collection = new ArrayList<Object>();
				
				collection.add(raceDTWhite);
				collection.add(raceDTUnknown);
				collection.add(raceDTAsian);
				
				personVO1.setThePersonRaceDTCollection(collection);
			
		
				PowerMockito.doNothing().when(PageCreateHelper.class, "setRaceForCreate",personVO, clientVO, proxyVO, "10000000");

				Whitebox.invokeMethod(new PageCreateHelper(), "setRaceForEdit", personVO1, clientVO, proxyVO, "10000000");
				Collection<Object> races = personVO1.getThePersonRaceDTCollection();
				int actualNumberRacesInCollectionActual = 0;
				 if(races!=null)
				 	actualNumberRacesInCollectionActual = personVO1.getThePersonRaceDTCollection().size();
				 
				System.out.println("Iteration: #"+iteration+"\n");
				System.out.println("-Verifying Races in the PersonRaceDTCollection:\n");
				System.out.println("Expected number of races in the collection: "+numberOfExpectedRacesInCollection+"\nActual number of races in the collection: "+actualNumberRacesInCollectionActual);
				Assert.assertEquals(numberOfExpectedRacesInCollection, actualNumberRacesInCollectionActual);
					
				int newRaces = 0;
				int racesUpdate = 0;
				System.out.println("-------------------------------------------------------------------------------------");
				if(races!=null){
					Iterator ite = races.iterator();
					int iWhite = 0;
					while (ite.hasNext()) {
						PersonRaceDT personRaceDT = (PersonRaceDT) ite.next();
						
						
						if(personRaceDT.isItNew())
							newRaces++;
						else
							racesUpdate++;
						
						
						

						String categoryActual = personRaceDT.getRaceCategoryCd();
						//Checking on a deeper level for White
						if(categoryActual!=null && categoryActual.equalsIgnoreCase("2106-3")){//white
							System.out.println("\nWhite Race testing:\n");
							Long personUidActual = personRaceDT.getPersonUid();
							String recordStatusCdActual = personRaceDT.getRecordStatusCd();
							String raceActual = personRaceDT.getRaceCd();
							
							String catergoryExpected = "2106-3";
							String recordStatusCdExpected = NEDSSConstants.RECORD_STATUS_ACTIVE;
							String raceExpected = detailedRaceWhite[iWhite];
									
							System.out.println("\nRace cd: "+ personRaceDT.getRaceCd());
							System.out.println("\nExpected person uid: "+personUid+"\nActual person uid: "+personUidActual);
							System.out.println("\nExpected category: "+catergoryExpected+"\nActual category: "+categoryActual);
						//	System.out.println("\nExpected add user id: "+userId+"\nActual add user id: "+userIdActual);
							System.out.println("\nExpected record status cd: "+recordStatusCdExpected+"\nActual record status cd: "+recordStatusCdActual);
							System.out.println("\nExpected race cd: "+raceExpected+"\nActual race cd: "+raceActual);
							
							iWhite++;
							
					
							
							
							Assert.assertEquals(personUid, personUidActual);
							Assert.assertEquals(catergoryExpected, categoryActual);
						//	Assert.assertEquals(userId, userIdActual+"");
							Assert.assertEquals(recordStatusCdExpected, recordStatusCdActual);
							Assert.assertEquals(raceExpected, raceActual);
							
							
							
						}	
							
					}
					
					System.out.println("-------------------------------------------------------------------------------------");
					System.out.println("\n- Verifying the races to add and edit in the personRaceDTCollection is correct:");
					System.out.println("\nNumber of races to add: Actual: "+ newRaces+" Expected: "+expectedNew);
					System.out.println("Number of races to update: Expected: "+ racesUpdate+" Expected: "+expectedUpdate);
				
					Assert.assertEquals(expectedNew, newRaces);
					Assert.assertEquals(expectedUpdate, racesUpdate);
					
					
					//Verifying data in the clientVO (if the main race has been deleted, the detailed race is removed from the client:
					System.out.println("-------------------------------------------------------------------------------------");
					System.out.println("\n- Verifying the data in clientVO.getArrayAnswerMap() is correct:\n");
					int actualTotalNumberOfDetailedRaces = clientVO.getArrayAnswerMap().size();
					int expectedTotalClientVO = americanIndianAlaskanRace+asianRace+africanAmerican+hawaiianRace+whiteRace;
					System.out.println("Number of races in the ClientVO: Actual: "+actualTotalNumberOfDetailedRaces);
					System.out.println("Number of races in the ClientVO: Expected: "+expectedTotalClientVO);
					
					Assert.assertEquals(expectedTotalClientVO, actualTotalNumberOfDetailedRaces);
					
				}
	
			
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setRaceForEdit_test *******************");
			
		}		
	
}


/**
 * SetRaceForEdit2_test: this unit test method consists of several verifications:
 * 
 * - Making sure the number of races in the collection PersonRaceDTCollection is correct based on the individual races and detailed races, and knowing that, by default,
 * we have included some default races selected in the PersonRaceDTCollection to simulate the edition of existing races plus new selected races.
 * - Making sure that, for race White, the values set are the expected one. We could have done the same one with other races, but we decided to only check
 * one of them, because they are pretty similar.
 * - The number of races in PersonRaceDTCollection set for add / edit are the expected ones.
 * - The number of races under ClientVO are the expected one, knowing that only detailed races will be added if the parent race is added, otherwise, it would be removed.
 * @author Fatima.Lopezcalzado
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetRaceForEdit2_test{
	

	private String iteration;
	private Long personUid;
	

	private int unknownRace;
	private int otherRace;
	private int refusedToAnswer;
	private int notAsked;
	private int africanAmerican;
	private int americanIndianAlaskanRace;
	private int whiteRace;
	private int asianRace;
	private int hawaiianRace;
	private String[] detailedRaceAsian;
	private String[] detailedRaceHawaii;
	private String[] detailedRaceWhite;
	private String[] detailedAfricanAmerican;
	private String[] detailedAian;
	private int numberOfExpectedRacesInCollection;
	private int expectedNew;
	private int expectedUpdate;
	
	
 public SetRaceForEdit2_test(String it, Long personUid, int unknownRace, int otherRace, int refusedToAnswer, int notAsked,
		 int africanAmerican, int americanIndianAlaskanRace, int whiteRace, int asianRace, int hawaiianRace, String[] detailedRaceAsian, 
		 String[] detailedRaceHawaii, String[] detailedRaceWhite, String[] detailedAfricanAmerican, String[] detailedAian, int numberOfExpectedRacesInCollection, int expectedNew, int expectedUpdated){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	// this.userId = userId;
	 this.personUid = personUid;
	 this.unknownRace = unknownRace;
	 this.otherRace = otherRace;
	 this.refusedToAnswer = refusedToAnswer;
	 this.notAsked = notAsked;
	 this.africanAmerican = africanAmerican;
	 this.americanIndianAlaskanRace = americanIndianAlaskanRace;
	 this.whiteRace = whiteRace;
	 this.asianRace = asianRace;
	 this.hawaiianRace = hawaiianRace;
	 this.detailedRaceAsian = detailedRaceAsian;
	 this.detailedRaceHawaii = detailedRaceHawaii;
	 this.detailedRaceWhite = detailedRaceWhite;
	 this.detailedAfricanAmerican = detailedAfricanAmerican;
	 this.detailedAian = detailedAian;
	 this.numberOfExpectedRacesInCollection = numberOfExpectedRacesInCollection;//It will only count if the main class has been selected, otherwise, the detailed race is removed from the client answer array map and cannot be count in the PersonRaceDTCollection
	 this.expectedNew = expectedNew;//New races that are not already part of the PersonRaceDTCollection
	 this.expectedUpdate = expectedUpdated;//according to our test, 3 of them will be always updted, because they are already in the collection
	 
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		
    		   //iteration, userId, personUid
    		  //unknownRace, otherRace, refusedToAnswer, notAsked, africanAmerican, americanIndianAlaskanRace
    			//whiteRace, asianRace, hawaiianRace, detailedRaceAsian, detailedRaceHawaii, detailedRaceWhite, detailedAfricanAmerican, detailedAian
    		 //total in collection
    		  //new, updated
    	
    		  
    		  
    		  
    			  {"setRaceForEdit2_test"+"_"+it++,100000L,0/*U*/,1/*other*/,1/*refused*/,0/*not asked*/,0/*african american*/,
    				  0/*american indian alaskan*/,1/*white*/,1/*asian*/,1/*hawaiian*/,
    				  new String[]{"2029-7"/*Asian*/},
        			  new String[]{"2100-6","2085-9"/*hawaiian*/},new String[]{"2129-5","2108-9"/*White*/},new String[]{"2060-2"}/*African american*/,
        			  new String[]{"1002-5"/*1735-0*/}, 11, 8, 3}, 
        
        			  

        			  {"setRaceForEdit2_test"+"_"+it++,100000L,0/*U*/,0/*other*/,0/*refused*/,0/*not asked*/,0/*african american*/,
        				  0/*american indian alaskan*/,1/*white*/,1/*asian*/,1/*hawaiian*/,
        				  new String[]{"2029-7"/*Asian*/},
            			  new String[]{"2100-6","2085-9"/*hawaiian*/},new String[]{"2129-5","2108-9"/*White*/},new String[]{"2060-2"}/*African american*/,
            			  new String[]{"1002-5"/*1735-0*/}, 9, 6, 3}, 
            			  

            			  {"setRaceForEdit2_test"+"_"+it++,100000L,1/*U*/,1/*other*/,1/*refused*/,1/*not asked*/,1/*african american*/,
            				  1/*american indian alaskan*/,1/*white*/,1/*asian*/,1/*hawaiian*/,
            				  new String[]{"2029-7"/*Asian*/},
                			  new String[]{"2100-6","2085-9"/*hawaiian*/},new String[]{"2129-5","2108-9"/*White*/},new String[]{"2060-2"}/*African american*/,
                			  new String[]{"1002-5"/*1735-0*/}, 16, 13, 3}, 
                			  
                			  
                			  
                			  {"setRaceForEdit2_test"+"_"+it++,100000L,1/*U*/,1/*other*/,1/*refused*/,1/*not asked*/,1/*african american*/,
                				  1/*american indian alaskan*/,1/*white*/,1/*asian*/,1/*hawaiian*/,
                				  new String[]{"2029-7", "2030-5","2031-3","2032-1","2033-9","2034-7","2036-2","2037-0","2038-8","2048-7","2039-6","2040-4","2041-2","2052-9","2042-0","2049-5","2050-3", "2043-8", "2044-6", "2051-1", "2045-3", "2035-4", "2046-1", "2047-9"/*Asian*/},
                    			  new String[]{"2100-6","2085-9","2500-7", "2078-4"/*hawaiian*/},new String[]{"2129-5","2108-9","2118-8"/*White*/},new String[]{"2060-2","2058-6","2067-7","2068-5","2056-0","2070-1","2069-3","2071-9","2072-7","2073-5","2074-3"}/*African american*/,
                    			  new String[]{"1002-5","1004-1"/*1735-0*/}, 53, 50, 3}, 
                    			  
                			  
                    			  {"setRaceForEdit2_test"+"_"+it++,100000L,0/*U*/,0/*other*/,0/*refused*/,0/*not asked*/,0/*african american*/,
                    				  0/*american indian alaskan*/,0/*white*/,0/*asian*/,0/*hawaiian*/,
                    				  new String[]{},
                        			  new String[]{},new String[]{/*White*/},new String[]{}/*African american*/,
                        			  new String[]{}, 3, 0, 3}, 
                        			  
                        			  {"setRaceForEdit2_test"+"_"+it++,100000L,1/*U*/,0/*other*/,0/*refused*/,0/*not asked*/,0/*african american*/,
                        				  0/*american indian alaskan*/,0/*white*/,0/*asian*/,0/*hawaiian*/,
                        				  new String[]{"2029-7"/*Asian*/},
                            			  new String[]{"2100-6","2085-9"/*hawaiian*/},new String[]{"2129-5","2108-9"/*White*/},new String[]{"2060-2"}/*African american*/,
                            			  new String[]{"1002-5"/*1735-0*/}, 3, 0, 3}, 
                            			  
                            			  {"setRaceForEdit2_test"+"_"+it++,100000L,0/*U*/,0/*other*/,0/*refused*/,0/*not asked*/,0/*african american*/,
                            				  0/*american indian alaskan*/,0/*white*/,1/*asian*/,0/*hawaiian*/,
                            				  new String[]{"2029-7", "2030-5","2031-3","2032-1","2033-9","2034-7","2036-2","2037-0","2038-8","2048-7","2039-6","2040-4","2041-2","2052-9","2042-0","2049-5","2050-3", "2043-8", "2044-6", "2051-1", "2045-3", "2035-4", "2046-1", "2047-9"/*Asian*/},
                                			  new String[]{/*hawaiian*/},new String[]{/*White*/},new String[]{}/*African american*/,
                                			  new String[]{/*1735-0*/}, 27, 24, 3}, 

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



@org.mockito.Mock
LogUtils loggerMock;
@Mock
PersonVO personVO;

@Mock
PersonDT personDT;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper=Mockito.spy(new PageCreateHelper());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageCreateHelper.class);
		 loggerMock = Mockito.mock(LogUtils.class);
	     personVO = Mockito.mock(PersonVO.class);
	     personDT = Mockito.mock(PersonDT.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setRaceForEdit2_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setRaceForEdit2_test *******************");
				Map<Object, Object> answerMap = new HashMap <Object, Object>();
				
			
				//personVO.getThePersonDT().getPersonUid();
				//In this case we are using real objects, instead of mocked object because we need to read the information set in the PersonVO1
				//by the time the method that we are testing finished, to make sure the method works correctly (meaning, read this value:  personVO.setThePersonRaceDTCollection(raceColl);)
				
				PersonVO personVO1 = new PersonVO();
				PersonDT personDT1 = new PersonDT();
				personDT1.setPersonUid(personUid);
				personVO1.setThePersonDT(personDT1);
			
				//personVO.getThePersonDT().getPersonUid();
			//	when(personDT.getPersonUid()).thenReturn(personUid);  
		//		when(personVO.getThePersonDT()).thenReturn(personDT);  

				
				//ClientVO
				
				PageClientVO clientVO = new PageClientVO();
				
				
				
				//String asOfDate = getVal(clientVO.getAnswerMap().get(PageConstants.RACE_INFORMATION_AS_OF));
				String asOfDate = "11102022";//TODO: Fatima, is this format correct?? If not, get it while debugging from a real scenario
				answerMap.put(PageConstants.RACE_INFORMATION_AS_OF, asOfDate);
				//when(clientVO.getAnswerMap()).thenReturn(answerMap);  
				
				clientVO.setAnswerMap(answerMap);
				
				clientVO.setUnKnownRace(unknownRace);
				clientVO.setOtherRace(otherRace);
				clientVO.setRefusedToAnswer(refusedToAnswer);
				clientVO.setNotAsked(notAsked);
				clientVO.setAfricanAmericanRace(africanAmerican);
				clientVO.setAmericanIndianAlskanRace(americanIndianAlaskanRace);
				clientVO.setWhiteRace(whiteRace);
				clientVO.setAsianRace(asianRace);
				clientVO.setHawaiianRace(hawaiianRace);
				
			
				Map<Object,Object> answerArrayMap = new HashMap<Object,Object>();
				answerArrayMap.put(PageConstants.DETAILED_RACE_ASIAN, detailedRaceAsian);
				answerArrayMap.put(PageConstants.DETAILED_RACE_HAWAII, detailedRaceHawaii);
				answerArrayMap.put(PageConstants.DETAILED_RACE_WHITE, detailedRaceWhite);
				answerArrayMap.put(PageConstants.DETAILED_RACE_AFRICAN_AMERICAN, detailedAfricanAmerican);
				answerArrayMap.put(PageConstants.DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE, detailedAian);
				
				clientVO.setAnswerArrayMap(answerArrayMap);
				
				//default values for personVO.getThePersonRaceDTCollection() (simulating pre-existing for races)
				//Asian, Unknown and white
				PersonRaceDT raceDTWhite = new PersonRaceDT();
				raceDTWhite.setRaceCd("2106-3");
				raceDTWhite.setItNew(false);
				
				PersonRaceDT raceDTUnknown = new PersonRaceDT();
				raceDTUnknown.setItNew(false);
				raceDTUnknown.setRaceCd("U");
				
				PersonRaceDT raceDTAsian = new PersonRaceDT();
				raceDTAsian.setItNew(false);
				raceDTAsian.setRaceCd("2028-9");
		
				
				Collection<Object> collection = new ArrayList<Object>();
				
				collection.add(raceDTWhite);
				collection.add(raceDTUnknown);
				collection.add(raceDTAsian);
				
				personVO1.setThePersonRaceDTCollection(collection);
			
		
				PowerMockito.doNothing().when(PageCreateHelper.class, "setRaceForCreate",personVO, clientVO, "10000000");
				//setRaceForEdit(PersonVO personVO, ClientVO clientVO, String userId) {
				Whitebox.invokeMethod(new PageCreateHelper(), "setRaceForEdit", personVO1, clientVO, "10000000");
				Collection<Object> races = personVO1.getThePersonRaceDTCollection();
				int actualNumberRacesInCollectionActual = 0;
				 if(races!=null)
				 	actualNumberRacesInCollectionActual = personVO1.getThePersonRaceDTCollection().size();
				 
				System.out.println("Iteration: #"+iteration+"\n");
				System.out.println("-Verifying Races in the PersonRaceDTCollection:\n");
				System.out.println("Expected number of races in the collection: "+numberOfExpectedRacesInCollection+"\nActual number of races in the collection: "+actualNumberRacesInCollectionActual);
				Assert.assertEquals(numberOfExpectedRacesInCollection, actualNumberRacesInCollectionActual);
					
				int newRaces = 0;
				int racesUpdate = 0;
				
				if(races!=null){
					Iterator ite = races.iterator();
					int iWhite = 0;
					
					System.out.println("-------------------------------------------------------------------------------------");
					
					while (ite.hasNext()) {
						PersonRaceDT personRaceDT = (PersonRaceDT) ite.next();
						
						
						if(personRaceDT.isItNew())
							newRaces++;
						else
							racesUpdate++;
						
						
						

						String categoryActual = personRaceDT.getRaceCategoryCd();
						//Checking on a deeper level for White
						if(categoryActual!=null && categoryActual.equalsIgnoreCase("2106-3")){//white
							System.out.println("\nWhite Race testing:\n");
							Long personUidActual = personRaceDT.getPersonUid();
							Long userIdActual = personRaceDT.getAddUserId();
							String recordStatusCdActual = personRaceDT.getRecordStatusCd();
							String raceActual = personRaceDT.getRaceCd();
							
							String catergoryExpected = "2106-3";
							String recordStatusCdExpected = NEDSSConstants.RECORD_STATUS_ACTIVE;
							String raceExpected = detailedRaceWhite[iWhite];
									
							System.out.println("\nRace cd: "+ personRaceDT.getRaceCd());
							System.out.println("\nExpected person uid: "+personUid+"\nActual person uid: "+personUidActual);
							System.out.println("\nExpected category: "+catergoryExpected+"\nActual category: "+categoryActual);
						//	System.out.println("\nExpected add user id: "+userId+"\nActual add user id: "+userIdActual);
							System.out.println("\nExpected record status cd: "+recordStatusCdExpected+"\nActual record status cd: "+recordStatusCdActual);
							System.out.println("\nExpected race cd: "+raceExpected+"\nActual race cd: "+raceActual);
							
							iWhite++;
							
					
							
							
							Assert.assertEquals(personUid, personUidActual);
							Assert.assertEquals(catergoryExpected, categoryActual);
						//	Assert.assertEquals(userId, userIdActual+"");
							Assert.assertEquals(recordStatusCdExpected, recordStatusCdActual);
							Assert.assertEquals(raceExpected, raceActual);
							
							
							
						}	
							
					}
					
					System.out.println("-------------------------------------------------------------------------------------");
					System.out.println("\n- Verifying the races to add and edit in the personRaceDTCollection is correct:");
					System.out.println("\nNumber of races to add: Actual: "+ newRaces+" Expected: "+expectedNew);
					System.out.println("Number of races to update: Expected: "+ racesUpdate+" Expected: "+expectedUpdate);
				
					Assert.assertEquals(expectedNew, newRaces);
					Assert.assertEquals(expectedUpdate, racesUpdate);
					
					
					//Verifying data in the clientVO (if the main race has been deleted, the detailed race is removed from the client:
					
					System.out.println("-------------------------------------------------------------------------------------");
					
					System.out.println("\n- Verifying the data in clientVO.getArrayAnswerMap() is correct:\n");
					int actualTotalNumberOfDetailedRaces = clientVO.getArrayAnswerMap().size();
					int expectedTotalClientVO = americanIndianAlaskanRace+asianRace+africanAmerican+hawaiianRace+whiteRace;
					System.out.println("Number of races in the ClientVO: Actual: "+actualTotalNumberOfDetailedRaces);
					System.out.println("Number of races in the ClientVO: Expected: "+expectedTotalClientVO);
					
					Assert.assertEquals(expectedTotalClientVO, actualTotalNumberOfDetailedRaces);
					
				}
	
			
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setRaceForEdit2_test *******************");
			
		}		
	
}









/**
 * SetPatientForEventCreate_test: the change on this method consisted of updating tempId type from int to Long. As part of this test, we are making sure
 * whatever value we pass to the method as tempId, is set in the personVO correctly.
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetPatientForEventCreate_test{
	

	private String iteration;
	private Long patientUid;
	private Long tempID;
	private String userId;

	
	

	
 public SetPatientForEventCreate_test(String it, Long patientUid, Long tempId, String userId){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.patientUid = patientUid;
	 this.tempID = tempId;
	 this.userId = userId;
	

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setPatientForEventCreate_test"+"_"+it++, 1L, 2L, "111111111"}, 
    		  {"setPatientForEventCreate_test"+"_"+it++, 2L, 3L, "111111111"}, 
    		  {"setPatientForEventCreate_test"+"_"+it++, 4444444L, 111111L, "111111111"}, 
    		  {"setPatientForEventCreate_test"+"_"+it++, 5555L, 6666666L, "111111111"},
    		  
			  
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



   @Mock
   HttpServletRequest request;
   @Mock
   HttpSession session;



@Mock
PageActProxyVO pageActProxyVO;


@Mock
LogUtils loggerMock;



@Mock
NBSSecurityObj secObj;


@Spy
@InjectMocks
PageCreateHelper pageCreateHelper=Mockito.spy(new PageCreateHelper());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageCreateHelper.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 session = Mockito.mock(HttpSession.class);
		 secObj = Mockito.mock(NBSSecurityObj.class);
		 pageActProxyVO = Mockito.mock(PageActProxyVO.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setPatientForEventCreate_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setPatientForEventCreate_test *******************");
				
				
				
				PageForm form = new PageForm();

				 when((HttpSession) request.getSession()).thenReturn(session);
				 
				 when((NBSSecurityObj) session.getAttribute("NBSSecurityObject")).thenReturn(secObj);
				 
				 when(secObj.getPermission(any(String.class), any(String.class))).thenReturn(true);
				 
				PersonVO personVO = Whitebox.invokeMethod(new PageCreateHelper(), "setPatientForEventCreate", patientUid, tempID, pageActProxyVO, form, request, userId);
			
				
				
				Long personUid = personVO.getThePersonDT().getPersonUid();
				
				
				System.out.println("Iteration: #"+iteration+"\nPersonUID sent: "+tempID+"\nPersonUID set in PersonVO: "+personUid);
				
				Assert.assertEquals(tempID, personUid);
				
				
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setPatientForEventCreate_test *******************");
			
		}		
	
}






/**
 * SetPatientForEventCreate_test: the change on this method consisted of updating revisionPatientUID type from int to Long. As part of this test, we are making sure
 * whatever value we pass to the method as tempId, the method still works as expected and ActEntityDTCollection contains one DT created within that method.
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetEntitiesForCreateEdit_test{
	

	private String iteration;
	private String userId;
	private Long revisionPatientUID;
	private String versionCtrlNbr;
	private Long actUid;
	 

	
	

	
 public SetEntitiesForCreateEdit_test(String it, String userId, Long revisionPatientUID, String versionCtrlNbr, Long actUid){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.userId = userId;
	 this.revisionPatientUID=revisionPatientUID;
	 this.versionCtrlNbr=versionCtrlNbr;
	this.actUid = actUid;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 2L, "1", 2L},
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 1111111L, "1", 1L},
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 99999999L, "1", 3L},
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 88888888L, "1", 4L},
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 13242352L, "1", 5L},
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 13242352L, "1", 0L},
    		  
    		  
			  
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */




@Mock
PageActProxyVO pageActProxyVO;


@Mock
LogUtils loggerMock;

@Mock
NbsActEntityDT oldDT;

@Mock
NbsActEntityDT entityDT;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper=Mockito.spy(new PageCreateHelper());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageCreateHelper.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 entityDT = Mockito.mock(NbsActEntityDT.class);
		 oldDT = Mockito.mock(NbsActEntityDT.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setEntitiesForCreateEdit_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setEntitiesForCreateEdit_test *******************");
				
				Long phcUid = 111111L;
				
				PageForm form = new PageForm();

				PageActProxyVO proxyVO = new PageActProxyVO();
				PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();
				PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();
				publicHealthCaseDT.setPublicHealthCaseUid(phcUid);
				proxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
				
				PageClientVO pageClientVO = new PageClientVO();
				PageActProxyVO oldPageProxyVO = new PageActProxyVO();
				PamVO pageVO = new PamVO();
				
				pageVO.setActEntityDTCollection(new ArrayList<Object>());
				oldPageProxyVO.setPageVO(pageVO);
				pageClientVO.setOldPageProxyVO(oldPageProxyVO);
				form.setPageClientVO(pageClientVO);
				((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPageVO();
				
				
		
				PowerMockito.doReturn(entityDT).when(PageCreateHelper.class, "createPamCaseEntity",  any(Long.class), any(String.class),  any(String.class), any(String.class), any(String.class));
	
			
				PowerMockito.doReturn(oldDT).when(PageCreateHelper.class, "getNbsCaseEntity",   any(String.class), any(Collection.class));
				
				
				 when(entityDT.getActUid()).thenReturn(actUid);
					
	
				
				
				Whitebox.invokeMethod(new PageCreateHelper(), "setEntitiesForCreateEdit", form, proxyVO, revisionPatientUID, versionCtrlNbr, userId);
			
				int actualSize = proxyVO.getPageVO().getActEntityDTCollection().size();
				int expectedSize = 1;
	
				System.out.println("Iteration: #"+iteration+"\nExpected size of ActEntityDTCollection: "+expectedSize+"\nActual size of ActEntityDTCollection: "+actualSize);
				
				Assert.assertEquals(expectedSize, actualSize);
				
				
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setEntitiesForCreateEdit_test *******************");
			
		}		
	
}






/**
 * setParticipationsForCreate_test: the change on this method consisted of updating revisionPatientUID type from int to Long. As part of this test, we are making sure
 * whatever value we pass to the method as tempId, the method still works as expected and getTheParticipationDTCollection contains one DT created within that method.
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetParticipationsForCreate_test{
	

	private String iteration;
	private String userId;
	private Long revisionPatientUID;

	
	

	
 public SetParticipationsForCreate_test(String it, String userId, Long revisionPatientUID){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.userId = userId;
	 this.revisionPatientUID=revisionPatientUID;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setParticipationsForCreate_test"+"_"+it++,"111111111", 2L},
    		  {"setParticipationsForCreate_test"+"_"+it++,"111111111", 1111111L},
    		  {"setParticipationsForCreate_test"+"_"+it++,"111111111", 99999999L},
    		  {"setParticipationsForCreate_test"+"_"+it++,"111111111", 88888888L},
    		  {"setParticipationsForCreate_test"+"_"+it++,"111111111", 13242352L},
    		  {"setParticipationsForCreate_test"+"_"+it++,"111111111", 13242352L},
    		  
    		  
			  
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */




@Mock
PageActProxyVO pageActProxyVO;


@Mock
LogUtils loggerMock;

@Mock
NbsActEntityDT oldDT;

@Mock
ParticipationDT participationDT;

@Mock
HttpServletRequest request;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper=Mockito.spy(new PageCreateHelper());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageCreateHelper.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 participationDT = Mockito.mock(ParticipationDT.class);
		 oldDT = Mockito.mock(NbsActEntityDT.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setParticipationsForCreate_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setParticipationsForCreate_test *******************");
				
				Long phcUid = 111111L;
				
				PageForm form = new PageForm();

				PageActProxyVO proxyVO = new PageActProxyVO();
				PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();
				PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();
				publicHealthCaseDT.setPublicHealthCaseUid(phcUid);
				proxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
				
				PowerMockito.doReturn(participationDT).when(PageCreateHelper.class, "createParticipation",  any(Long.class), any(Timestamp.class),  any(String.class), any(Long.class), any(String.class), any(String.class), any(String.class), any(String.class));


				Whitebox.invokeMethod(new PageCreateHelper(), "setParticipationsForCreate", userId, proxyVO, form, revisionPatientUID, request);
			
				int actualSize = proxyVO.getTheParticipationDTCollection().size();
				int expectedSize = 1;
	
				System.out.println("Iteration: #"+iteration+"\nExpected size of getTheParticipationDTCollection: "+expectedSize+"\nActual size of getTheParticipationDTCollection: "+actualSize);
				
				Assert.assertEquals(expectedSize, actualSize);
				
				
				
				System.out.println("PASSED");
				System.out.println("******************* End test case named: setParticipationsForCreate_test *******************");
			
		}		
	
}







/**
 * HandleSupervisorReviewQueueForCreate_test: this method will pass if the case close date doesn't change after executing this method. Previously, it was set to null under first scenario.
 * now we need to make sure that it is not set to null under any scenario.
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class HandleSupervisorReviewQueueForCreate_test{
	

	private String iteration;
	private String initFollUp;
	private String fldFollUpDispo;
	private String caseReviewStatus;
	boolean isSupervisionReviewReady;

	
	

	
 public HandleSupervisorReviewQueueForCreate_test(String it, String initFollUp, String fldFollUpDispo, String caseReviewStatus, boolean isSupervisionReviewReady){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.initFollUp = initFollUp;
	 this.fldFollUpDispo = fldFollUpDispo;
	 this.caseReviewStatus = caseReviewStatus;
	 this.isSupervisionReviewReady = isSupervisionReviewReady;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		
    			
    		  
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","G", "REJECT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","H", "REJECT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","J", "REJECT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","K", "REJECT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","AA", "REJECT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","BB", "REJECT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","CC", "REJECT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","DD", "REJECT", false},
    		  
    		  
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","G", "REJECT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","H", "REJECT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","J", "REJECT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","K", "REJECT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","AA", "REJECT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","BB", "REJECT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","CC", "REJECT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","DD", "REJECT", false},
    		  
    		
    		  
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","G", "ACCEPT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","H", "ACCEPT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","J", "ACCEPT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","K", "ACCEPT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","AA", "ACCEPT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","BB", "ACCEPT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","CC", "ACCEPT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","DD", "ACCEPT", false},
    		  
    		  
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","G", "ACCEPT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","H", "ACCEPT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","J", "ACCEPT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","K", "ACCEPT", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","AA", "ACCEPT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","BB", "ACCEPT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","CC", "ACCEPT", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","DD", "ACCEPT", false},
    		  
    		  
    		  

    		  
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","G", "OTHER", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","H", "OTHER", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","J", "OTHER", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","K", "OTHER", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","AA", "OTHER", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","BB", "OTHER", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","CC", "OTHER", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "FF","DD", "OTHER", false},
    		  
    		  
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","G", "OTHER", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","H", "OTHER", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","J", "OTHER", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","K", "OTHER", true},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","AA", "OTHER", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","BB", "OTHER", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","CC", "OTHER", false},
    		  {"handleSupervisorReviewQueueForCreate_test"+"_"+it++, "AA","DD", "OTHER", false},
    		  
    		  
    		    
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */




@Mock
PageActProxyVO pageActProxyVO;


@Mock
LogUtils loggerMock;

@Mock
NbsActEntityDT oldDT;

@Mock
ParticipationDT participationDT;

@Mock
HttpServletRequest request;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper=Mockito.spy(new PageCreateHelper());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageCreateHelper.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 participationDT = Mockito.mock(ParticipationDT.class);
		 oldDT = Mockito.mock(NbsActEntityDT.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void handleSupervisorReviewQueueForCreate_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: handleSupervisorReviewQueueForCreate_test *******************");
			
				CaseManagementDT cdt = new CaseManagementDT();
				

				cdt.setInitFollUp(initFollUp);
				cdt.setFldFollUpDispo(fldFollUpDispo);
				cdt.setCaseClosedDate(new Timestamp(11112000));//random date
				cdt.setCaseReviewStatus(caseReviewStatus);
			
			//	PowerMockito.mockStatic(PageCreateHelper.class);
				PowerMockito.doReturn(isSupervisionReviewReady).when(PageCreateHelper.class,"isSupervisorQueueReady",cdt.getFldFollUpDispo());
			
				Timestamp caseClosedDateExpected = cdt.getCaseClosedDate();
				Whitebox.invokeMethod(new PageCreateHelper(), "handleSupervisorReviewQueueForCreate", cdt);
				Timestamp caseClosedDateActual = cdt.getCaseClosedDate();
				
				Assert.assertEquals(caseClosedDateExpected, caseClosedDateActual);
				
				
				System.out.println("Iteration: "+iteration+". Verified caseClosedDate value hasn't changed to null. PASSED");
				System.out.println("******************* End test case named: handleSupervisorReviewQueueForCreate_test *******************");
			
		}		
	
}





/**
 * HandleSupervisorReviewQueueForEdit_test: this method will pass if the case close date doesn't change after executing this method. Previously, it was set to null under a few scenarios.
 * now we need to make sure that it is not set to null under any scenario.
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageCreateHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class HandleSupervisorReviewQueueForEdit_test{
	

	private String iteration;
	private String initFollUp;
	private String fldFollUpDispo;
	private String caseReviewStatus;
	boolean isSupervisionReviewReady;
	private String oldFupDispo;

	
	

	
 public HandleSupervisorReviewQueueForEdit_test(String it, String initFollUp, String fldFollUpDispo, String caseReviewStatus, boolean isSupervisionReviewReady, String oldFupDispo){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.initFollUp = initFollUp;
	 this.fldFollUpDispo = fldFollUpDispo;
	 this.caseReviewStatus = caseReviewStatus;
	 this.isSupervisionReviewReady = isSupervisionReviewReady;
	 this.oldFupDispo = oldFupDispo;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		

    		  
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","G", "REJECT", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","H", "REJECT", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","J", "REJECT", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","K", "REJECT", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","AA", "REJECT", false, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","BB", "REJECT", false, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","CC", "REJECT", false, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","DD", "REJECT", false, "FF"},
    		  
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","G", "REJECT", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","H", "REJECT", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","J", "REJECT", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","K", "REJECT", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","AA", "REJECT", false, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","BB", "REJECT", false, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","CC", "REJECT", false, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","DD", "REJECT", false, "II"},
    		  
    		  
    		  
    		  
    		  

    		  
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","G", "ACCEPT", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","H", "ACCEPT", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","J", "ACCEPT", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","K", "ACCEPT", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","AA", "ACCEPT", false, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","BB", "ACCEPT", false, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","CC", "ACCEPT", false, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","DD", "ACCEPT", false, "FF"},
    		  
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","G", "ACCEPT", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","H", "ACCEPT", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","J", "ACCEPT", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","K", "ACCEPT", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","AA", "ACCEPT", false, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","BB", "ACCEPT", false, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","CC", "ACCEPT", false, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","DD", "ACCEPT", false, "II"},
    		  
    		  
    		  
    		  
    		  

    		  
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","G", "READY", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","H", "READY", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","J", "READY", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","K", "READY", true, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","AA", "READY", false, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","BB", "READY", false, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","CC", "READY", false, "FF"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","DD", "READY", false, "FF"},
    		  
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","G", "READY", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","H", "READY", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","J", "READY", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","K", "READY", true, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","AA", "READY", false, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","BB", "READY", false, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","CC", "READY", false, "II"},
    		  {"handleSupervisorReviewQueueForEdit_test"+"_"+it++, "FF","DD", "READY", false, "II"},
    		  
    		  
    		  
			  
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */




@Mock
PageActProxyVO pageActProxyVO;


@Mock
LogUtils loggerMock;

@Mock
NbsActEntityDT oldDT;

@Mock
ParticipationDT participationDT;

@Mock
HttpServletRequest request;

@Spy
@InjectMocks
PageCreateHelper pageCreateHelper=Mockito.spy(new PageCreateHelper());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageCreateHelper.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 participationDT = Mockito.mock(ParticipationDT.class);
		 oldDT = Mockito.mock(NbsActEntityDT.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void handleSupervisorReviewQueueForEdit_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: handleSupervisorReviewQueueForEdit_test *******************");
			
				CaseManagementDT cdt = new CaseManagementDT();
				
				cdt.setInitFollUp(initFollUp);
				cdt.setFldFollUpDispo(fldFollUpDispo);
				cdt.setCaseClosedDate(new Timestamp(11112000));
				cdt.setCaseReviewStatus(caseReviewStatus);
			
				cdt.setCaseClosedDate(new Timestamp(123456));
			
				Timestamp caseClosedDateExpected = cdt.getCaseClosedDate();
				Whitebox.invokeMethod(pageCreateHelper, "handleSupervisorReviewQueueForEdit", cdt, oldFupDispo);
				Timestamp caseClosedDateActual = cdt.getCaseClosedDate();
				
				Assert.assertEquals(caseClosedDateExpected, caseClosedDateActual);
				
				System.out.println("Iteration: "+iteration+". Verified caseClosedDate value hasn't changed to null. PASSED");
				
				
				System.out.println("******************* End test case named: handleSupervisorReviewQueueForEdit_test *******************");
			
		}		
	
}





}
	
