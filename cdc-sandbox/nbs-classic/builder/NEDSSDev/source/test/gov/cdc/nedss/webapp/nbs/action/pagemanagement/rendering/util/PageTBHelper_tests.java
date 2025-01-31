package test.gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;


import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.tb.ejb.dao.TBDAOImpl;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageTBHelper;

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
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;



@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageTBHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageTBHelper.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class PageTBHelper_tests {



@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageTBHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageTBHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class IsCaseNumberUnique_test{
	

	private String iteration;
	private String publicHealthCaseUid;
	private String caseNumber;
	private String selectedPhcUid;
	private boolean expectedResult;
	private String tbCodes;//this attribute represents the tb codes in the DB (nbs_configuration table) plus 10220.
	
 public IsCaseNumberUnique_test(String it, String publicHealthCaseUid, String caseNumber, String selectedPhcUid, boolean expectedResult, String tbCodes){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.publicHealthCaseUid = publicHealthCaseUid;
	 this.caseNumber = caseNumber;
	 this.selectedPhcUid = selectedPhcUid;
	 this.expectedResult = expectedResult;
	 this.tbCodes=tbCodes;
	 

	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   

	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule1_test
    		    
    		  {"isCaseNumberUnique_test"+"_"+it++,"1015570004","1212","1015573002",false,"102201,502582,10220"},
    		  {"isCaseNumberUnique_test"+"_"+it++,"10032318","2008-OK-ABCD56789",null,true,"102201,502582,10220" },
    		  {"isCaseNumberUnique_test"+"_"+it++,"1015570004","1212","1015573001",false,"102201,502582,10220"},
    		  {"isCaseNumberUnique_test"+"_"+it++,"1015570004","1212","1015573001",false,"102201,502582,10220"},
    		  {"isCaseNumberUnique_test"+"_"+it++,"10032318","2008-OK-ABCD56789",null,true,"102201,502582,10220" },
    		  

    		  
    
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



@Mock
PageClientVO pageClientVO;

@org.mockito.Mock
PropertyUtil propertyUtil;

@org.mockito.Mock
LogUtils loggerMock;

@Mock
TBDAOImpl tbDAOImpl;

@Spy
@InjectMocks
PageTBHelper pageTBHelper = Mockito.spy(new PageTBHelper());

	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageTBHelper.class);
	        
		// propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		// 
	//	 Whitebox.setInternalState(PropertyUtil.class,"property", propertyUtilMocked);
		    
		
		 //  propertyUtil = PowerMockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 tbDAOImpl = Mockito.mock(TBDAOImpl.class);
		 propertyUtil= Mockito.mock(PropertyUtil.class);
		 
		 Whitebox.setInternalState(PageTBHelper.class, "logger", loggerMock);
	      Whitebox.setInternalState(PageTBHelper.class,"propertyUtil", propertyUtil);
	 }
	 
	

	@Test
	public void isCaseNumberUnique_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: isCaseNumberUnique_test *******************");
			
		        when(tbDAOImpl.selectCaseNumber(caseNumber, publicHealthCaseUid, tbCodes)).thenReturn(selectedPhcUid);   
		        PowerMockito.whenNew(TBDAOImpl.class).withNoArguments().thenReturn(tbDAOImpl);
		        
		        PowerMockito.mockStatic(PropertyUtil.class);
		        PowerMockito.when(propertyUtil.getTBConditionsCodes()).thenReturn("102201,502582");
		  
			
				boolean actualResult = Whitebox.invokeMethod(new PageTBHelper(), "isCaseNumberUnique", caseNumber, publicHealthCaseUid);
			
				System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedResult+"\nActual value: "+actualResult);
				Assert.assertEquals(expectedResult, actualResult);
				System.out.println("PASSED");
				System.out.println("******************* End test case named: isCaseNumberUnique_test *******************");
			
		}		
	
}
	




}
	
