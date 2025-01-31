package test.gov.cdc.nedss.pagemanagement.wa.xml.util;


import gov.cdc.nedss.pagemanagement.wa.xml.util.MaskToJavaScriptFunction;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;

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
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;




/**
Methods that have been unit tested so far:

- ...
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.pagemanagement.wa.xml.util.MaskToJavaScriptFunction","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({MaskToJavaScriptFunction.class,LogUtils.class})
@RunWith(Enclosed.class)
public class MaskToJavaScriptFunction_tests {


/**
 * GetStyleClassForMask_test: this method will validate if the StyleClass returned by the actual method
 * is the correct for the specific mask received as a parameter.
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO.MaskToJavaScriptFunction","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({MaskToJavaScriptFunction.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
public static class GetStyleClassForMask_test{
	

	private String iteration;
	private String strMask;
	private String expectedValue;
	
	
 public GetStyleClassForMask_test(String it, String strMask, String expectedValue){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.strMask = strMask;
	 this.expectedValue = expectedValue;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/

	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule1_test
    		    
    		  {"getStyleClassForMask_test"+"_"+it++,"TXT_EMAIL", "emailField"},
    		  {"getStyleClassForMask_test"+"_"+it++,"TXT_IDTB","txt_idtbField"},
    		  {"getStyleClassForMask_test"+"_"+it++,"anyOtherText",""},
    		  {"getStyleClassForMask_test"+"_"+it++,"",""},
    		  {"getStyleClassForMask_test"+"_"+it++,null,""},
    		  {"getStyleClassForMask_test"+"_"+it++,"11111111",""},
    		  {"getStyleClassForMask_test"+"_"+it++,"txt_idtb","txt_idtbField"},
    		  
    		      		 
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
MaskToJavaScriptFunction maskToJavaScriptFunction = Mockito.spy(new MaskToJavaScriptFunction());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(MaskToJavaScriptFunction.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(MaskToJavaScriptFunction.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void getStyleClassForMask_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: getStyleClassForMask_test *******************");
			
				String actualResult = Whitebox.invokeMethod(new MaskToJavaScriptFunction(), "getStyleClassForMask", strMask);
				
				System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue+"\nActual value: "+actualResult);
				Assert.assertEquals(expectedValue, actualResult);
		
				System.out.println("PASSED");
				System.out.println("******************* End test case named: getStyleClassForMask_test *******************");
			
		}		
	
}
	






@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO.MaskToJavaScriptFunction","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({MaskToJavaScriptFunction.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
public static class GetJavaScriptForOnKeyUpMask_test{
	

	private String iteration;
	private String strMask;
	private String existingJS;
	private String expectedValue;
	
	
 public GetJavaScriptForOnKeyUpMask_test(String it, String strMask, String existingJS, String expectedValue){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.strMask = strMask;
	 this.existingJS = existingJS;
	 this.expectedValue = expectedValue;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/

	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule1_test
    		    
    		  
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM","","isNumericCharacterEntered(this)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_ZIP","","ZipMask(this,event)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_PHONE","","TeleMask(this, event)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_SSN","","SSNMask(this, event)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_YYYY","","YearMask(this, event)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_MM","","isMonthCharEntered(this)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_DD","","isDayOfMonthCharEntered(this)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_EXT","","isNumericCharacterEntered(this)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_TEMP","","isTemperatureCharEntered(this)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_ID10","","isAlphaNumericCharacterEntered(this);UpperCaseMask(this);chkMaxLength(this,10)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_ID12","","isAlphaNumericCharacterEntered(this);UpperCaseMask(this);chkMaxLength(this,12)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_ID15","","isAlphaNumericCharacterEntered(this);UpperCaseMask(this);chkMaxLength(this,15)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_IDTB","","CaseNumberMask(this, null, event)"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_SN","","isStructuredNumericCharEntered(this)"},
    		  
    		  //TODO: Same but with random existing JS
    		  
    		  
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM","OtherJavaScriptMethod()","isNumericCharacterEntered(this);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_ZIP","OtherJavaScriptMethod()","ZipMask(this,event);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_PHONE","OtherJavaScriptMethod()","TeleMask(this, event);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_SSN","OtherJavaScriptMethod()","SSNMask(this, event);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_YYYY","OtherJavaScriptMethod()","YearMask(this, event);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_MM","OtherJavaScriptMethod()","isMonthCharEntered(this);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_DD","OtherJavaScriptMethod()","isDayOfMonthCharEntered(this);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_EXT","OtherJavaScriptMethod()","isNumericCharacterEntered(this);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_TEMP","OtherJavaScriptMethod()","isTemperatureCharEntered(this);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_ID10","OtherJavaScriptMethod()","isAlphaNumericCharacterEntered(this);UpperCaseMask(this);chkMaxLength(this,10);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_ID12","OtherJavaScriptMethod()","isAlphaNumericCharacterEntered(this);UpperCaseMask(this);chkMaxLength(this,12);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_ID15","OtherJavaScriptMethod()","isAlphaNumericCharacterEntered(this);UpperCaseMask(this);chkMaxLength(this,15);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_IDTB","OtherJavaScriptMethod()","CaseNumberMask(this, null, event);OtherJavaScriptMethod()"},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"NUM_SN","OtherJavaScriptMethod()","isStructuredNumericCharEntered(this);OtherJavaScriptMethod()"},
    		  
    		  
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"","OtherJavaScriptMethod()",""},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,null,"OtherJavaScriptMethod()",""},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"","",""},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,null,null,""},
    		  {"getJavaScriptForOnKeyUpMask_test"+"_"+it++,"TXT_IDTB",null,"CaseNumberMask(this, null, event)"},
    		        		 
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
MaskToJavaScriptFunction maskToJavaScriptFunction = Mockito.spy(new MaskToJavaScriptFunction());

	 @Before
	 public void initMocks() throws Exception {

		 
		  PowerMockito.spy(MaskToJavaScriptFunction.class);
	        
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);
		

		 Whitebox.setInternalState(MaskToJavaScriptFunction.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void getJavaScriptForOnKeyUpMask_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: getJavaScriptForOnKeyUpMask_test *******************");
			
				String actualResult = Whitebox.invokeMethod(new MaskToJavaScriptFunction(), "getJavaScriptForOnKeyUpMask", strMask, existingJS);
				
				System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue+"\nActual value: "+actualResult);
				Assert.assertEquals(expectedValue, actualResult);
		
				System.out.println("PASSED");
				System.out.println("******************* End test case named: getJavaScriptForOnKeyUpMask_test *******************");
			
		}		
	
}
	



}
	
