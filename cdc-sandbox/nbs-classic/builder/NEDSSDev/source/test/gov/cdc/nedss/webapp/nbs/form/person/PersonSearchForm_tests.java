package test.gov.cdc.nedss.webapp.nbs.form.person;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;

@RunWith(Enclosed.class)
public class PersonSearchForm_tests {
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PersonSearchForm.class,WebContextFactory.class,CallProxyEJB.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	 public static class DeleteCustomQueue_test{
		 private String queueName;
		 private String expectedValue;
		 private int iteration;
		 
		 public DeleteCustomQueue_test(String queueName, String expectedValue, int it){
			this.queueName = queueName;
			this.expectedValue = expectedValue;
			this.iteration = it;
     	}
		 	 
		@Mock
		WebContext wcontext;
			
		@Mock
		HttpServletRequest request;

		@InjectMocks
		PersonSearchForm  pForm; 
		
		 @Before
		 public void initMocks() throws Exception {
			 wcontext=Mockito.mock(WebContext.class);
			 request=Mockito.mock(HttpServletRequest.class);
 		 }
		 
		 @Test
		    public void deleteCustomQueue_test() throws Exception {
		     	String qName=this.queueName;
		     	PowerMockito.mockStatic(WebContextFactory.class);
		     	PowerMockito.mockStatic(CallProxyEJB.class);
		     	when(WebContextFactory.get()).thenReturn(wcontext);
		     	when(wcontext.getHttpServletRequest()).thenReturn(request);
		        when(CallProxyEJB.callProxyEJB(any(Object[].class),any(String.class),any(String.class),any(HttpSession.class))).thenReturn("one");
		        String actualResult = Whitebox.invokeMethod(new PersonSearchForm(), "deleteCustomQueue", qName);
		    	String expectedResult=this.expectedValue;
				Assert.assertEquals(expectedResult, actualResult);
				System.out.println("Class NAme:PersonSearchForm.java,  Method NAme: deleteCustomQueue, Iteration: "+iteration+". Queue NAme: "+this.queueName+" Expected value: "+expectedResult+"; Actual value: "+actualResult+", RESULT:::::PASSED");;

		    }
		 
		 
		    @SuppressWarnings("rawtypes")
			@Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {"QueueName1","one",1},
			    		  {"QueueName2","one",2},
			    		  {"QueueName3","one",3},
			    		  {"QueueName4","one",4},
			    		  {"QueueName5","one",5},
			    		  {"QueueName6","one",6},
			    		  {"QueueName7","one",7},
			    		  {"QueueName8","one",8},
			    		  {"QueueName9","one",9},
			    		  {"QueueName10","one",10}
		    		  });
			   }
			 
		 
		 
	 }
	
	
	
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PersonSearchForm.class,WebContextFactory.class,CallProxyEJB.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class StoreCustomQueue_test{
		private String queueName;
		private String descriptionQueue;
		private String sourceTable;
		private boolean publicQueue;
		private String expectedValue;
		private int iteration;
		private String FINALQUERY="select";
		private String SEARCH_CRITRIA_CODE="searchCriteriaCd";
		private String SEARCH_CRITRIA_DESC="searchCriteriaDesc";
		 
		 public StoreCustomQueue_test(String queueName, String descriptionQueue, String sourceTable, boolean publicQueue,String expectedValue, int it){
			this.queueName = queueName.trim();
			this.descriptionQueue=descriptionQueue.trim();
			this.sourceTable=sourceTable;
			this.publicQueue=publicQueue;
			this.expectedValue = expectedValue;
			this.iteration = it;
    	}
		 	 
		@Mock
		WebContext wcontext;
			
		@Mock
		HttpServletRequest request;
		
		@Mock
		HttpSession httpSession;

		@InjectMocks
		PersonSearchForm  pForm; 
		
		 @Before
		public void initMocks() throws Exception {
			 wcontext=Mockito.mock(WebContext.class);
			 request=Mockito.mock(HttpServletRequest.class);
			 httpSession=Mockito.mock(HttpSession.class);
		}	
		 
		 @Test
		 public void storeCustomQueue_test() throws Exception{
	     	 PowerMockito.mockStatic(WebContextFactory.class);
		     PowerMockito.mockStatic(CallProxyEJB.class);
		     when(WebContextFactory.get()).thenReturn(wcontext);
		     when(wcontext.getHttpServletRequest()).thenReturn(request);
		     when(request.getSession()).thenReturn(httpSession);
		     when(httpSession.getAttribute("finalQueryString")).thenReturn(FINALQUERY);
		     when(httpSession.getAttribute("searchCriteriaCd")).thenReturn(SEARCH_CRITRIA_CODE);
		     when(httpSession.getAttribute("searchCriteriaDesc")).thenReturn(SEARCH_CRITRIA_DESC);
	 	     when(CallProxyEJB.callProxyEJB(any(Object[].class),any(String.class),any(String.class),any(HttpSession.class))).thenReturn("one");
		     Object[] oParams = new Object[] {this.queueName,this.descriptionQueue,this.sourceTable,this.publicQueue,"select"};
		     String actualResult = Whitebox.invokeMethod(new PersonSearchForm(), "storeCustomQueue", oParams);
		     String expectedResult=this.expectedValue;
			 Assert.assertEquals(expectedResult, actualResult);
			 System.out.println("Class Name:PersonSearchForm, Method NAme: storeCustomQueue, Iteration: "+iteration+". Queue NAme: "+this.queueName+" Expected value: "+expectedResult+"; Actual value: "+actualResult+", RESULT:::::PASSED");;
				
	
			 
			 
		 }
		 
		  @SuppressWarnings("rawtypes")
			@Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {"QueueName1","QueueName1","I",true,"one",1},
			    		  {"QueueName2 ","QueueName2","LR",true,"one",2},
			    		  {"QueueName3","QueueName3","I",true,"one",3},
			    		  {"QueueName4 ","QueueName4","LR",true,"one",4},
			    		  {"QueueName5","QueueName5","I",true,"one",5},
			    		  {" QueueName6 ","QueueName6","LR",true,"one",6},
			    		  {"QueueName7","QueueName7","I",true,"one",7},
			    		  {"   QueueName8  ","QueueName8","LR",true,"one",8},
			    		  {"QueueName9","QueueName9","I",true,"one",9},
			    		  {"     QueueName10       ","QueueName10","LR",true,"one",10},
		    		  });
			   }
	}
   

}