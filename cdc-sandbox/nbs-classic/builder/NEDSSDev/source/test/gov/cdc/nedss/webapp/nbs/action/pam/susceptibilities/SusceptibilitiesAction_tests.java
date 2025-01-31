package test.gov.cdc.nedss.webapp.nbs.action.pam.susceptibilities;


import static org.mockito.Mockito.when;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pam.susceptibilities.SusceptibilitiesAction;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;




/**
For this test case, only 1 method is tested so far:
- splitVerCtrlNbr
 */




/**
Description:

This is a simple method, that, based on the contextAction received in the request, the correct JSP will be displayed.
If the contextAction is not the expected, this test case will fail.
If the contextAction is the expected, the test case will pass.


Lessons learnt:

- ...



*/


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pam.susceptibilities.SusceptibilitiesAction","gov.cdc.nedss.util.LogUtils"})
@RunWith(Enclosed.class)
@PrepareForTest({PageCreateHelper.class,LogUtils.class, PageForm.class})
@PowerMockIgnore("javax.management.*")
public class SusceptibilitiesAction_tests {
	
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pam.susceptibilities.SusceptibilitiesAction","gov.cdc.nedss.util.LogUtils"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class, PageForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SusceptibilitiesLoad_test {



	
	private String contextAction;
	private String expectedValue;
	private int iteration;
	
	 public SusceptibilitiesLoad_test(String contextAction, String expectedValue, int it){
		 super();
		 this.contextAction = contextAction;
		 this.expectedValue = expectedValue;
		 this.iteration = it;

	 }

	 
	   @Parameterized.Parameters
	   public static Collection input() {
		   
		  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
		   //The areas are the lines where the new method escapeCharacterSequence method is called
	      return Arrays.asList(new Object[][]{
	    		  //ContextAction, expected value, iteration
	    		  {"loadSusceptibilities","/pam/susceptibilities/susceptibility/Susceptibilities.jsp",1},//PASS
	    		//  {"loadSusceptibilities2","/pam/susceptibilities/susceptibility/Susceptibilities.jsp",2},//FAIL
	    		  
	    		  });
	   }
	 
	 
	 
	/*
	 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
	 * */

	@org.mockito.Mock
	LogUtils loggerMock;
	
	@Mock
	PageForm form;
	
	@org.mockito.Mock
	HttpServletResponse response;
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	ActionMapping mapping;
	
	@InjectMocks
	SusceptibilitiesAction susceptibilitiesAction = Mockito.spy(new SusceptibilitiesAction());
	
		 @Before
		 public void initMocks() throws Exception {

			 loggerMock = Mockito.mock(LogUtils.class);
			

			 Whitebox.setInternalState(SusceptibilitiesAction.class, "logger", loggerMock);
		 }
		 
		 
		 		@Test
				public void susceptibilitiesLoad_test() throws Exception{
				
					
				System.out.println("******************* Starting test case named: susceptibilitiesLoad_test *******************");
			
				String path="/pam/susceptibilities/susceptibility/Susceptibilities.jsp";
				ActionForward actionForward = new ActionForward();
				actionForward.setPath(path);
				
				
				when(request.getParameter("ContextAction")).thenReturn(contextAction);
				when(mapping.findForward("loadSusceptibilities")).thenReturn(actionForward);
		
				ActionForward forwardAction = susceptibilitiesAction.susceptibilitiesLoad(mapping, form, request, response);
				System.out.println(forwardAction.toString());
				
				String actualResult = forwardAction.getPath();
				String expectedResult = this.expectedValue;
				System.out.println("Iteration: #"+iteration+"\nContext Action sent: "+this.contextAction+"\nExpected value: "+expectedResult+"\nActual value: "+actualResult);
				Assert.assertEquals(expectedResult, actualResult);
				
				System.out.println("PASSED");
				System.out.println("******************* End test case named: susceptibilitiesLoad_test *******************");
		
			}

}

}
	
