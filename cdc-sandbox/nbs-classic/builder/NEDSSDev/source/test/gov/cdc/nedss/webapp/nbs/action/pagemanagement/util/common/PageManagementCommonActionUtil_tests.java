package test.gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.interview.dt.InterviewDT;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.struts.mock.MockHttpServletRequest;
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



@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(Enclosed.class)
@PrepareForTest({PageManagementCommonActionUtil.class, PropertyUtil.class, NEDSSConstants.class, PageForm.class, PageActProxyVO.class})
@PowerMockIgnore("javax.management.*")
public class PageManagementCommonActionUtil_tests {
	


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageManagementCommonActionUtil.class, PropertyUtil.class, NEDSSConstants.class, PageForm.class, PageActProxyVO.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetCommonAnswersForGenericViewEdit_test {


	 boolean isAssociated;//true or false
	 String state;
	 String stateCode;
	 String businessObj;//IXS or VAC 
	 int iteration;
	 boolean expectedValue;
	 
	@Mock
	PageClientVO pageClientVO;
	@Mock
	PageProxyVO pageProxyVO;
    @Mock
	InterviewVO interviewVO;
	@Mock
	InterviewDT interviewDT;	
	@Mock
	PageActProxyVO proxyVO;
	//We will be using the request provided by Mockito in order to be able to read the value modified from the test case
	//@Mock
	//HttpServletRequest request;
	 
	
	MockHttpServletRequest request = new MockHttpServletRequest();
	   
	   
	@Mock
	PropertyUtil propertyUtil;
	@Mock
	PageForm form;
	
	@InjectMocks
	@Spy //@Spy is needed to mock methods inside the class that we are testing.
	PageManagementCommonActionUtil pageManagementCommonActUtil=  Mockito.spy(PageManagementCommonActionUtil.class);
	

	 
	 public SetCommonAnswersForGenericViewEdit_test(boolean isAssociated, String state, String stateCode, String businessObj, int it, boolean expectedValue){
		 super();
		 this.isAssociated = isAssociated;//value to set in the request
		 this.state = state;
		 this.stateCode = stateCode;
		 this.businessObj = businessObj;
		 this.iteration=it;
		 this.expectedValue = expectedValue;//if it is an interview, it should be the same than isAssociated
	 }
	 
	 
	 

	 
	   @Parameterized.Parameters
	   public static Collection input() {
		   
		   ArrayList<Long> myArray = new ArrayList<Long>();
		   int it =1;
		   
		   
	       return Arrays.asList(new Object[][]{
	    		  
	    		  //The test will PASS with the following values:
	    		 //If it is an interview, we will set the value of isAssociated in the request.
	    		  
	    		 {true,"GA","13","IXS",it++,true},
	    		 {false,"AL","12","IXS",it++, false},
	    		 {true,null,"13","IXS",it++,true},
	    		 {false,null,"12","IXS",it++, false},
	    		 {true,"GA",null,"IXS",it++,true},
	    		 {false,"AL",null,"IXS",it++, false},
	    		  {true,null,"12","VAC",it++, false},//if vaccination, request won't be updated
	    		  {false,"GA","13","VAC",it++, false},//if vaccination, request won't be updated
	    		  {false,null,"13","VAC",it++, false},//if vaccination, request won't be updated
	    		  {false,"AL","12","VAC",it++, false},//if vaccination, request won't be updated
	    		  {true,"GA","13","VAC",it++, false},//if vaccination, request won't be updated
	    		  {true,"AL","12","VAC",it++, false},//if vaccination, request won't be updated
	    		  {true,null,"12","VAC",it++, false}//if vaccination, request won't be updated	      
	      });
	   }

	   
	   
	   

		 @Before
		 public void initMocks() throws Exception {
			/*
			 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
			 * */

			 MockitoAnnotations.initMocks(this);
			 propertyUtil = PowerMockito.mock(PropertyUtil.class);
			 
		
		 }
		 
		 
		@Test
		public void setCommonAnswersForGenericViewEdit_test() throws Exception{
		 
			System.out.println("******************* Starting test case named: setCommonAnswersForGenericViewEdit_test *******************");
			
			when(form.getBusinessObjectType()).thenReturn(businessObj);  
			Whitebox.setInternalState(NEDSSConstants.class, "INTERVIEW_BUSINESS_OBJECT_TYPE", "IXS");
			Whitebox.setInternalState(NEDSSConstants.class, "VACCINATION_BUSINESS_OBJECT_TYPE", "VAC");
		    PowerMockito.spy(PageManagementCommonActionUtil.class);
		    PowerMockito.doNothing().when(PageManagementCommonActionUtil.class, "setInterviewInformationOnForm",  Matchers.any(PageForm.class),Matchers.any(PageActProxyVO.class));
		    PowerMockito.doNothing().when(PageManagementCommonActionUtil.class, "setVaccinationInformationOnForm",  Matchers.any(PageForm.class),Matchers.any(PageActProxyVO.class));
			        
			        			
				 
			PowerMockito.when(form.getPageClientVO()).thenReturn(pageClientVO);
			PowerMockito.when(form.getPageClientVO().getOldPageProxyVO()).thenReturn(pageProxyVO);
			PowerMockito.when(form.getPageClientVO().getOldPageProxyVO().getInterviewVO()).thenReturn(interviewVO);
			PowerMockito.when(form.getPageClientVO().getOldPageProxyVO().getInterviewVO().getTheInterviewDT()).thenReturn(interviewDT);
			PowerMockito.when(form.getPageClientVO().getOldPageProxyVO().getInterviewVO().getTheInterviewDT().isAssociated()).thenReturn(isAssociated);
			
			
					
			//STATE
			PowerMockito.when(form.getPageClientVO().getAnswer("DEM162")).thenReturn(state);// so it doesn't go through any of the if, since there were no changes in there.
			PowerMockito.when(form.getPageClientVO().getAnswer("DEM162_W")).thenReturn(stateCode);
			
	
	
			PowerMockito.doNothing().when(form).setDwrStateSiteCounties(any(ArrayList.class));
	
			
			PowerMockito.mockStatic(CachedDropDowns.class);
			PowerMockito.when(CachedDropDowns.getCountyCodes(any(String.class))).thenReturn(new ArrayList<Object>());
				

			PowerMockito.mockStatic(PropertyUtil.class);
			PowerMockito.when(propertyUtil.getNBS_STATE_CODE()).thenReturn(stateCode);
			PowerMockito.when(PropertyUtil.getInstance()).thenReturn(propertyUtil);
			
				
			 
						
			 Whitebox.invokeMethod(pageManagementCommonActUtil, "setCommonAnswersForGenericViewEdit",form, proxyVO, request);
				 
			
			boolean actualValue = false;
			if(businessObj!=null && businessObj.equalsIgnoreCase("IXS"))
				actualValue = (boolean)request.getAttribute("isAssociated");
			
			
			Assert.assertEquals(actualValue,expectedValue);
			System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedValue+"\nActual value: "+actualValue);
			System.out.println("PASSED");	
			System.out.println("******************* End test case named: setCommonAnswersForGenericViewEdit_test *******************");
		}
		
	/*We should have these 3 sets on each test*/
	//Arrange
	
	//Act
	
	//Assert
	
	
}
}
	
