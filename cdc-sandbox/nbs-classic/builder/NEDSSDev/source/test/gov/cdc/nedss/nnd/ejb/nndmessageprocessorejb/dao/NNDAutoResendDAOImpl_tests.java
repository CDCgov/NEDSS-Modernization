package test.gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
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

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NNDAutoResendDAOImpl;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;

@SuppressStaticInitializationFor ({"gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NNDAutoResendDAOImpl","gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(Enclosed.class)
@PrepareForTest({NNDAutoResendDAOImpl.class})
@PowerMockIgnore("javax.management.*")
public class NNDAutoResendDAOImpl_tests {
	
	/**
	 * GetCountOfExistingNotificationsByUid_test: based on the parameter value received by the test, which will represent the count,
	 * which will represent the query result, if it matched the parameter expectedValue, it will pass, otherswise, it will fail.
	 * @author Fatima.Lopezcalzado
	 *
	 */
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({NNDAutoResendDAOImpl.class,LogUtils.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class GetCountOfExistingNotificationsByUid_test{
		private Long uid;
		private int value;
		private int expectedValue;
		private int iteration;
		
		
		public GetCountOfExistingNotificationsByUid_test(Long uid, int value, int expectedValue, int iteration) {
			super();
			this.uid = uid;
			this.value=value;
			this.expectedValue = expectedValue;
			this.iteration = iteration;
		}
		
		@Mock
		LogUtils logger;
		
		@Mock
		PropertyUtil propertyUtil;
		
	
		@InjectMocks
		@Spy
		NNDAutoResendDAOImpl nndAutoResendDAOImpl=Mockito.spy(new NNDAutoResendDAOImpl());
		
		@Before
		public void initMocks() throws Exception {
			logger = Mockito.mock(LogUtils.class);
			Whitebox.setInternalState(NNDAutoResendDAOImpl.class, "logger", logger);
		 }
		
		
		@SuppressWarnings("unchecked")
		@Test
		public void getCountOfExistingNotificationsByUid_test() throws Exception{
			Mockito.doReturn(new Integer(this.value)).when(nndAutoResendDAOImpl).preparedStmtMethod(any(PublicHealthCaseDT.class),any(ArrayList.class),any(String.class),any(String.class));
			Object[] oParams = new Object[] {this.uid};
		    int actualResult = Whitebox.invokeMethod(nndAutoResendDAOImpl, "getCountOfExistingNotificationsByUid", oParams);
			System.out.println("Iteration: #"+iteration+"  Expected value: "+expectedValue+"  Actual value: "+actualResult);
			Assert.assertEquals(expectedValue, actualResult);
			System.out.println("Iteration: #"+iteration+"  TestCase PASSED");
		}
		
		
		
		 @SuppressWarnings("rawtypes") //allowing all are success, otherwise maven build is failing when run build with test cases.
			@Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {123456L,4,4,1},
			    		  {1234567L,5,5,2},
			    		  {12345678L,10,10,3},
			    		  {123456789L,7,7,4},
		    		  });
			   }
		
	}
	
}
	
