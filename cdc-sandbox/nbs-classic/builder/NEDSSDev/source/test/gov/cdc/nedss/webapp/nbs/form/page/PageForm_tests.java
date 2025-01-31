package test.gov.cdc.nedss.webapp.nbs.form.page;


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

import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.util.LogUtils"})
@PrepareForTest({PageForm.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class PageForm_tests {



@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({PageForm.class,LogUtils.class, WebContextFactory.class, PropertyUtil.class, CallProxyEJB.class})
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
	PageForm pageForm;
	
	@Before
	public void initMocks() throws Exception {
		loggerMock = Mockito.mock(LogUtils.class);
		wcontext = Mockito.mock(WebContext.class);
		nbsContext = Mockito.mock(NBSContext.class);
		request = Mockito.mock(HttpServletRequest.class);
	 }
	
	@Test
	public void checkForExistingNotificationsByPublicHealthCaseUid_test() throws Exception{
		PowerMockito.whenNew(PageForm.class).withNoArguments().thenReturn(pageForm);
		PowerMockito.spy(PageForm.class);
		PowerMockito.mockStatic(WebContextFactory.class);
	    PowerMockito.mockStatic(CallProxyEJB.class);
	    when(WebContextFactory.get()).thenReturn(wcontext);
	    when(wcontext.getHttpServletRequest()).thenReturn(request);
	    when(request.getSession()).thenReturn(httpSession);
	    when(CallProxyEJB.callProxyEJB(any(Object[].class),any(String.class),any(String.class),any(HttpSession.class))).thenReturn(true);
	    Object[] oParams = new Object[] {this.uid};
	    Boolean actualResult = Whitebox.invokeMethod(pageForm, "checkForExistingNotificationsByPublicHealthCaseUid", oParams);
		System.out.println("Iteration: #"+iteration+"  Expected value: "+expectedValue+"  Actual value: "+actualResult);
		Assert.assertEquals(expectedValue, actualResult);
		System.out.println("Iteration: #"+iteration+"  TestCase PASSED");
	}
				
	 @SuppressWarnings("rawtypes") //allowing all are success, otherwise maven build is failing when run build with test cases.
		@Parameterized.Parameters
		   public static Collection input() {
		      return Arrays.asList(new Object[][]{
		    		  {"123456",true,1},
		    		  {"1234567",true,2},
		    		  {"12345678",true,3},
		    		  {"123456789",true,4},
	    		  });
		   }
}



}
	
