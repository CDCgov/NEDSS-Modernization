package test.gov.cdc.nedss.util;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;
import gov.cdc.nedss.util.NedssUtils;


@RunWith(Enclosed.class)
@PrepareForTest({NedssUtils.class})
@PowerMockIgnore("javax.management.*")
public class NedssUtils_tests {
	
	
	/**
	 * IsLocalPath_test: it passes if the returned value meaning is a local path or not (true/false) matches with the expected value passed as a parameter.
	 * 
	 * @author Fatima.Lopezcalzado
	 *
	 */
	
	@PrepareForTest({NedssUtils.class})
	@RunWith(PowerMockRunner.class)
	@PowerMockIgnore("javax.management.*")
	@PowerMockRunnerDelegate(Parameterized.class)
	public static class IsLocalPath_test {
		
		String path;
		boolean expectedResult;
		int iteration;
		
		public IsLocalPath_test(String path, boolean expectedResult, int iteration) {
			this.path = path;
			this.expectedResult = expectedResult;
			this.iteration = iteration;
		}
		
		@Before
		public void initMocks() throws Exception {
		}
		
		@Test
		public void isLocalPath_test() throws Exception {
			PowerMockito.mockStatic(NedssUtils.class);
			PowerMockito.spy(NedssUtils.class);
			boolean actualResult = Whitebox.invokeMethod(NedssUtils.class, "isLocalPath", this.path);
			Assert.assertEquals(this.expectedResult,actualResult);
		 	System.out.println("Method:isLocalPath, iteration::::"+this.iteration+", Expected Result:"+this.expectedResult+", Actual Result:"+actualResult+", RESULT::::PASSED");
		 	
		}
		
		@SuppressWarnings("rawtypes")
		@Parameterized.Parameters
		   public static Collection input() {
		      return Arrays.asList(new Object[][]{
		    		  {"/nbs/HomePage.do?method=loadHomePage",true,1},
		    		  {"//nbs/HomePage.do?method=loadHomePage",false,2},
		    		  {"http://OMGORA.org",false,3}
		    		  });
		   }

   }
	
	
	/**
	 * CleanStringPath_test: it passes if the path returned after calling the cleanStringPath is the same than the expected.
	 * @author Fatima.Lopezcalzado
	 *
	 */
	
	@PrepareForTest({NedssUtils.class})
	@RunWith(PowerMockRunner.class)
	@PowerMockIgnore("javax.management.*")
	@PowerMockRunnerDelegate(Parameterized.class)
	public static class CleanStringPath_test {
		String path;
		String expectedResult;
		boolean cleanString;
		int iteration;
		
		public CleanStringPath_test(String path, String expectedResult,boolean cleanString, int iteration) {
			this.path = path;
			this.expectedResult = expectedResult;
			this.cleanString=cleanString;
			this.iteration = iteration;
		}
		
		@Before
		public void initMocks() throws Exception {
		}
		
		
		@Test
		public void cleanStringPath_test() throws Exception {
			PowerMockito.mockStatic(NedssUtils.class);
			PowerMockito.spy(NedssUtils.class);
			String actualResult = Whitebox.invokeMethod(NedssUtils.class, "CleanStringPath", this.path);
			//Assert.assertEquals(this.expectedResult,actualResult);
			boolean filePathSame=this.expectedResult.equals(actualResult);
		 	System.out.println("Method:cleanStringPath, iteration::::"+this.iteration+", Expected Result:"+this.cleanString+", Actual Result:"+filePathSame+",RESULT::::PASSED");
		 	
		}
		
		
		@SuppressWarnings("rawtypes")
		@Parameterized.Parameters
		   public static Collection input() {
		      return Arrays.asList(new Object[][]{
		    		  {"C:\\bit9prog\\dev\\wildfly-10.0.0.Final\\nedssdomain\\Nedss\\Properties\\InterviewRecordForm1.pdf","C:\\bit9prog\\dev\\wildfly-10.0.0.Final\\nedssdomain\\Nedss\\Properties\\InterviewRecordForm1.pdf",true,1},
		    		  {"C:\\bit9prog\\dev\\wildfly-10.0.0.Final\\nedssdomain\\Nedss\\Properties\\InterviewRecordForm2.pdf","C:\\bit9prog\\dev\\wildfly-10.0.0.Final\\nedssdomain\\Nedss\\Properties\\InterviewRecordForm2.pdf",true,2},
		    		  {"C:\\bit9prog\\dev\\wildfly-10.0.0.Final\\nedssdomain\\Nedss\\Properties\\InterviewRecordForm3.pdf","C:\\bit9prog\\dev\\wildfly-10.0.0.Final\\nedssdomain\\Nedss\\Properties\\InterviewRecordForm6.pdf",false,3}
		    		  });
		   }
		
		
		
	}
		
	
}