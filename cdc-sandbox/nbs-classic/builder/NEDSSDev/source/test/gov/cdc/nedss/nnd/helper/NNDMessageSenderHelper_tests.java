package test.gov.cdc.nedss.nnd.helper;


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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NNDAutoResendDAOImpl;
import gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyEJB;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;


@SuppressStaticInitializationFor ({"gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper","gov.cdc.nedss.util.LogUtils"})
@PrepareForTest({NNDMessageSenderHelper.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class NNDMessageSenderHelper_tests {


/**
 * CheckForExistingNotificationsByUid_test: this tests validates that it returns true if the number of existing notifications is >0, otherwise
 * it should return false.
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({NNDMessageSenderHelper.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class CheckForExistingNotificationsByUid_test {
	private Long uid;
	private int value;
	private boolean expectedValue;
	private int iteration;
	
	public CheckForExistingNotificationsByUid_test(Long uid, int value, boolean expectedValue, int iteration) {
		super();
		this.uid = uid;
		this.value=value;
		this.expectedValue = expectedValue;
		this.iteration = iteration;
	}

	@Mock
	NNDAutoResendDAOImpl nndAutoResendDAOImpl;
		
	
	@org.mockito.Mock
	LogUtils logger;
	
	@Mock
	PropertyUtil propertyUtil;

	@InjectMocks
	NNDMessageSenderHelper nndMessageSenderHelper;
	
	@Before
	public void initMocks() throws Exception {
		logger = Mockito.mock(LogUtils.class);
		Whitebox.setInternalState(NNDMessageSenderHelper.class, "logger", logger);
	 }
	
	@Test
	public void checkForExistingNotificationsByUid_test() throws Exception{
		PowerMockito.whenNew(NNDMessageSenderHelper.class).withNoArguments().thenReturn(nndMessageSenderHelper);
		PowerMockito.whenNew(NNDAutoResendDAOImpl.class).withNoArguments().thenReturn(nndAutoResendDAOImpl);
		PowerMockito.spy(NNDMessageSenderHelper.class);
		when(nndAutoResendDAOImpl.getCountOfExistingNotificationsByUid(any(Long.class))).thenReturn(this.value);
		Object[] oParams = new Object[] {this.uid};
	    Boolean actualResult = Whitebox.invokeMethod(nndMessageSenderHelper, "checkForExistingNotificationsByUid", oParams);
		System.out.println("Iteration: #"+iteration+"  Expected value: "+expectedValue+"  Actual value: "+actualResult);
		Assert.assertEquals(expectedValue, actualResult);
		System.out.println("Iteration: #"+iteration+"  TestCase PASSED");
	}
				
	 @SuppressWarnings("rawtypes") //allowing all are success, otherwise maven build is failing when run build with test cases.
		@Parameterized.Parameters
		   public static Collection input() {
		      return Arrays.asList(new Object[][]{
		    		  {123456L,1,true,1},
		    		  {1234567L,2,true,2},
		    		  {12345678L,3,true,3},
		    		  {123456789L,4,true,4},
	    		  });
		   }
}



}
	
