package test.gov.cdc.nedss.page.ejb.pageproxyejb.bean;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;
import gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyEJB;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;

@SuppressStaticInitializationFor ({"gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyEJB","gov.cdc.nedss.util.LogUtils"})
@PrepareForTest({PageProxyEJB.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class PageProxyEJB_tests {

	/**
	 * CheckForExistingNotificationsByPublicHealthUid_test: this method was created to always return true from the internal method call.
	 * It will verify that, if the internal method returns true, the method returns true representing there's an existing notification
	 * for the public health case uid sent as a parameter.
	 * @author Fatima.Lopezcalzado
	 *
	 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyEJB","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({PageProxyEJB.class,NNDMessageSenderHelper.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class CheckForExistingNotificationsByPublicHealthUid_test {
	private Long uid;
	private boolean expectedValue;
	private int iteration;
	
	public CheckForExistingNotificationsByPublicHealthUid_test(Long uid, boolean expectedValue, int iteration) {
		super();
		this.uid = uid;
		this.expectedValue = expectedValue;
		this.iteration = iteration;
	}
		
	@Mock
	LogUtils logger;

	
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	NNDMessageSenderHelper nndMessageSenderHelper;

	@Mock
	NBSSecurityObj nbsSecurityObj;
	
	@InjectMocks
	PageProxyEJB proxyEjb;
	
	@Before
	public void initMocks() throws Exception {
		logger = Mockito.mock(LogUtils.class);
		Whitebox.setInternalState(PageProxyEJB.class, "logger", logger);
	 }
	
	@Test
	public void checkForExistingNotificationsByPublicHealthUid_test() throws Exception{
		PowerMockito.whenNew(PageProxyEJB.class).withNoArguments().thenReturn(proxyEjb);
		PowerMockito.spy(PageProxyEJB.class);
		PowerMockito.mockStatic(NNDMessageSenderHelper.class);
		when(NNDMessageSenderHelper.getInstance()).thenReturn(nndMessageSenderHelper);
		when(nndMessageSenderHelper.checkForExistingNotificationsByUid(any(Long.class))).thenReturn(true);
	    Object[] oParams = new Object[] {this.uid,nbsSecurityObj};
		Boolean actualResult = Whitebox.invokeMethod(proxyEjb, "checkForExistingNotificationsByPublicHealthUid", oParams);
		System.out.println("Iteration: #"+iteration+"  Expected value: "+expectedValue+"  Actual value: "+actualResult);
		Assert.assertEquals(expectedValue, actualResult);
		System.out.println("Iteration: #"+iteration+"  TestCase PASSED");
	}
				
	 @SuppressWarnings("rawtypes")
		@Parameterized.Parameters
		   public static Collection input() { 
		      return Arrays.asList(new Object[][]{
		    		  {123456L,true,1},
		    		  {1234567L,true,2},
		    		  {12345678L,true,3},
		    		  {123456789L,true,4},
	    		  });
		   }
}



}
	
