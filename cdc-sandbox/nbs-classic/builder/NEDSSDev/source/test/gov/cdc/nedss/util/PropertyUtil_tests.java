package test.gov.cdc.nedss.util;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.reflect.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;

import org.mockito.Mockito;





@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
//@RunWith(PowerMockRunner.class)
@RunWith(Enclosed.class)
@PrepareForTest({PropertyUtil.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public class PropertyUtil_tests {
	
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class GetCOVIDCRUpdate_test{
	
	    boolean isValid;
	    String  value;
	    String expectedValue;
	    int iteration;
		
		@Mock
		Properties properties;
		
		@Mock
		LogUtils logger;
		
		@InjectMocks
		PropertyUtil propertyUtil;
		
			public GetCOVIDCRUpdate_test(boolean isValid, String value, String expectedValue, int iteration){
				this.isValid=isValid;
				this.value=value;
				this.expectedValue=expectedValue;
				this.iteration=iteration;
			}
			
			
			 @Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {true,"S","S",1},{false,"F",null,2}
			  
			       });
			   }
			 
			 
			 @Before
			 public void initMocks() throws Exception {
				 logger = Mockito.mock(LogUtils.class);
				 propertyUtil = Mockito.spy(PropertyUtil.class);
				 properties=Mockito.mock(Properties.class);
				 Whitebox.setInternalState(PropertyUtil.class, "logger", logger);
				 Whitebox.setInternalState(PropertyUtil.class, "properties", properties);
			 }
				 
		
			 @Test
			 public void getCOVIDCRUpdate_test() throws Exception { 
				 Mockito.when(propertyUtil.isValid()).thenReturn(this.isValid);
				 Mockito.when(properties.getProperty(any(String.class))).thenReturn(this.value);
				 String actualResult = Whitebox.invokeMethod(propertyUtil, "getCOVIDCRUpdate");
				 Assert.assertEquals(this.value,actualResult);
			 	 System.out.println("Method:getCOVIDCRUpdate, iteration::::"+this.iteration+", Expected Result:"+this.value+", Actual Result:"+actualResult+", RESULT::::PASSED");
			 }
		
			
		}
	
	
	
	
	
	
	
	
	
	
	
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class GetCOVIDCRUpdateSendingSystem_test{
	
	    boolean isValid;
	    String  value;
	    String expectedValue;
	    int iteration;
		
		@Mock
		Properties properties;
		
		@Mock
		LogUtils logger;
		
		@InjectMocks
		PropertyUtil propertyUtil;
		
			public GetCOVIDCRUpdateSendingSystem_test(boolean isValid, String value, String expectedValue, int iteration){
				this.isValid=isValid;
				this.value=value;
				this.expectedValue=expectedValue;
				this.iteration=iteration;
			}
			
			
			 @Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {true,"S","S",1},{false,"F",null,2}
			  
			       });
			   }
			 
			 
			 @Before
			 public void initMocks() throws Exception {
				 logger = Mockito.mock(LogUtils.class);
				 propertyUtil = Mockito.spy(PropertyUtil.class);
				 properties=Mockito.mock(Properties.class);
				 Whitebox.setInternalState(PropertyUtil.class, "logger", logger);
				 Whitebox.setInternalState(PropertyUtil.class, "properties", properties);
			 }
				 
		
			 @Test
			 public void getCOVIDCRUpdateSendingSystem_test() throws Exception{
				 Mockito.when(propertyUtil.isValid()).thenReturn(this.isValid);
				 Mockito.when(properties.getProperty(any(String.class))).thenReturn(this.value);
				 String actualResult = Whitebox.invokeMethod(propertyUtil, "getCOVIDCRUpdateSendingSystem");
				 Assert.assertEquals(this.expectedValue,actualResult);
			 	 System.out.println("Method:getCOVIDCRUpdateSendingSystem, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+actualResult+", RESULT::::PASSED"); 
			 }
		
			
		}
	
	
	
	

	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class GetCOVIDCRUpdateIgnore_test{
	
	    boolean isValid;
	    String  value;
	    String expectedValue;
	    int iteration;
		
		@Mock
		Properties properties;
		
		@Mock
		LogUtils logger;
		
		@InjectMocks
		PropertyUtil propertyUtil;
		
			public GetCOVIDCRUpdateIgnore_test(boolean isValid, String value, String expectedValue, int iteration){
				this.isValid=isValid;
				this.value=value;
				this.expectedValue=expectedValue;
				this.iteration=iteration;
			}
			
			
			 @Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {true,"S","S",1},{false,"F",null,2}
			  
			       });
			   }
			 
			 
			 @Before
			 public void initMocks() throws Exception {
				 logger = Mockito.mock(LogUtils.class);
				 propertyUtil = Mockito.spy(PropertyUtil.class);
				 properties=Mockito.mock(Properties.class);
				 Whitebox.setInternalState(PropertyUtil.class, "logger", logger);
				 Whitebox.setInternalState(PropertyUtil.class, "properties", properties);
			 }
				 
		
			 @Test
			 public void getCOVIDCRUpdateIgnore_test() throws Exception{
				 Mockito.when(propertyUtil.isValid()).thenReturn(this.isValid);
				 Mockito.when(properties.getProperty(any(String.class))).thenReturn(this.value);
				 String actualResult = Whitebox.invokeMethod(propertyUtil, "getCOVIDCRUpdateIgnore");
				 Assert.assertEquals(this.expectedValue,actualResult);
			 	 System.out.println("Method:getCOVIDCRUpdateIgnore, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+actualResult+", RESULT::::PASSED"); 

			 }
		
			
		}
	
	
	
	


	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class GetCOVIDCRUpdateTimeframe_test{
	
	    boolean isValid;
	    String  value;
	    String expectedValue;
	    int iteration;
		
		@Mock
		Properties properties;
		
		@Mock
		LogUtils logger;
		
		@InjectMocks
		PropertyUtil propertyUtil;
		
			public GetCOVIDCRUpdateTimeframe_test(boolean isValid, String value, String expectedValue, int iteration){
				this.isValid=isValid;
				this.value=value;
				this.expectedValue=expectedValue;
				this.iteration=iteration;
			}
			
			
			 @Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {true,"S","S",1},{false,"F",null,2}
			  
			       });
			   }
			 
			 
			 @Before
			 public void initMocks() throws Exception {
				 logger = Mockito.mock(LogUtils.class);
				 propertyUtil = Mockito.spy(PropertyUtil.class);
				 properties=Mockito.mock(Properties.class);
				 Whitebox.setInternalState(PropertyUtil.class, "logger", logger);
				 Whitebox.setInternalState(PropertyUtil.class, "properties", properties);
			 }
				 
		
			 @Test
			 public void getCOVIDCRUpdateTimeframe_test() throws Exception{
				 Mockito.when(propertyUtil.isValid()).thenReturn(this.isValid);
				 Mockito.when(properties.getProperty(any(String.class))).thenReturn(this.value);
				 String actualResult = Whitebox.invokeMethod(propertyUtil, "getCOVIDCRUpdateTimeframe");
				 Assert.assertEquals(this.expectedValue,actualResult);
			 	 System.out.println("Method:getCOVIDCRUpdateTimeframe, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+actualResult+", RESULT::::PASSED"); 

			 }
		
			
		}
	
	
	
	
	

	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class GetCOVIDCRUpdateClosed_test{
	
	    boolean isValid;
	    String  value;
	    String expectedValue;
	    int iteration;
		
		@Mock
		Properties properties;
		
		@Mock
		LogUtils logger;
		
		@InjectMocks
		PropertyUtil propertyUtil;
		
			public GetCOVIDCRUpdateClosed_test(boolean isValid, String value, String expectedValue, int iteration){
				this.isValid=isValid;
				this.value=value;
				this.expectedValue=expectedValue;
				this.iteration=iteration;
			}
			
			
			 @Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {true,"S","S",1},{false,"F",null,2}
			  
			       });
			   }
			 
			 
			 @Before
			 public void initMocks() throws Exception {
				 logger = Mockito.mock(LogUtils.class);
				 propertyUtil = Mockito.spy(PropertyUtil.class);
				 properties=Mockito.mock(Properties.class);
				 Whitebox.setInternalState(PropertyUtil.class, "logger", logger);
				 Whitebox.setInternalState(PropertyUtil.class, "properties", properties);
			 }
				 
		
			 @Test
			 public void getCOVIDCRUpdateClosed_test() throws Exception{
				 Mockito.when(propertyUtil.isValid()).thenReturn(this.isValid);
				 Mockito.when(properties.getProperty(any(String.class))).thenReturn(this.value);
				 String actualResult = Whitebox.invokeMethod(propertyUtil, "getCOVIDCRUpdateClosed");
				 Assert.assertEquals(this.expectedValue,actualResult);
			 	 System.out.println("Method:getCOVIDCRUpdateClosed, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+actualResult+", RESULT::::PASSED"); 

			 }
		
			
		}
	
	
	
	
	

	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class GetCOVIDCRUpdateMultiClosed_test{
	
	    boolean isValid;
	    String  value;
	    String expectedValue;
	    int iteration;
		
		@Mock
		Properties properties;
		
		@Mock
		LogUtils logger;
		
		@InjectMocks
		PropertyUtil propertyUtil;
		
			public GetCOVIDCRUpdateMultiClosed_test(boolean isValid, String value, String expectedValue, int iteration){
				this.isValid=isValid;
				this.value=value;
				this.expectedValue=expectedValue;
				this.iteration=iteration;
			}
			
			
			 @Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {true,"S","S",1},{false,"F",null,2}
			  
			       });
			   }
			 
			 
			 @Before
			 public void initMocks() throws Exception {
				 logger = Mockito.mock(LogUtils.class);
				 propertyUtil = Mockito.spy(PropertyUtil.class);
				 properties=Mockito.mock(Properties.class);
				 Whitebox.setInternalState(PropertyUtil.class, "logger", logger);
				 Whitebox.setInternalState(PropertyUtil.class, "properties", properties);
			 }
				 
		
			 @Test
			 public void getCOVIDCRUpdateMultiClosed_test() throws Exception{
				 Mockito.when(propertyUtil.isValid()).thenReturn(this.isValid);
				 Mockito.when(properties.getProperty(any(String.class))).thenReturn(this.value);
				 String actualResult = Whitebox.invokeMethod(propertyUtil, "getCOVIDCRUpdateMultiClosed");
				 Assert.assertEquals(this.expectedValue,actualResult);
			 	 System.out.println("Method:getCOVIDCRUpdateMultiClosed, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+actualResult+", RESULT::::PASSED"); 

			 }
			 
		
			
		}
	
	
	
	


	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class GetCOVIDCRUpdateMultiOpen_test{
	
	    boolean isValid;
	    String  value;
	    String expectedValue;
	    int iteration;
		
		@Mock
		Properties properties;
		
		@Mock
		LogUtils logger;
		
		@InjectMocks
		PropertyUtil propertyUtil;
		
			public GetCOVIDCRUpdateMultiOpen_test(boolean isValid, String value, String expectedValue, int iteration){
				this.isValid=isValid;
				this.value=value;
				this.expectedValue=expectedValue;
				this.iteration=iteration;
			}
			
			
			 @Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {true,"S","S",1},{false,"F",null,2}
			  
			       });
			   }
			 
			 
			 @Before
			 public void initMocks() throws Exception {
				 logger = Mockito.mock(LogUtils.class);
				 propertyUtil = Mockito.spy(PropertyUtil.class);
				 properties=Mockito.mock(Properties.class);
				 Whitebox.setInternalState(PropertyUtil.class, "logger", logger);
				 Whitebox.setInternalState(PropertyUtil.class, "properties", properties);
			 }
				 
		
			 @Test
			 public void getCOVIDCRUpdateMultiOpen_test() throws Exception{
				 Mockito.when(propertyUtil.isValid()).thenReturn(this.isValid);
				 Mockito.when(properties.getProperty(any(String.class))).thenReturn(this.value);
				 String actualResult = Whitebox.invokeMethod(propertyUtil, "getCOVIDCRUpdateMultiOpen");
				 Assert.assertEquals(this.expectedValue,actualResult);
			 	 System.out.println("Method:getCOVIDCRUpdateMultiOpen, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+actualResult+", RESULT::::PASSED"); 

			 }
			 
		
			
		}
	
	
	


	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class GetTBConditionsCodes_test{
	
	    boolean isValid;
	    String  value;
	    String expectedValue;
	    int iteration;
		
		@Mock
		Properties properties;
		
		@Mock
		LogUtils logger;
		
		@InjectMocks
		PropertyUtil propertyUtil;
		
			public GetTBConditionsCodes_test(boolean isValid, String value, String expectedValue, int iteration){
				this.isValid=isValid;
				this.value=value;
				this.expectedValue=expectedValue;
				this.iteration=iteration;
			}
			
			
			 @Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {true,"102201,502582","102201,502582",1},
			    		  {false,"102201,502582",null,2},//not valid
			    		  {true,"aaaaaa","aaaaaa",3},
			    		  
			  
			       });
			   }
			 
			 
			 @Before
			 public void initMocks() throws Exception {
				 logger = Mockito.mock(LogUtils.class);
				 propertyUtil = Mockito.spy(PropertyUtil.class);
				 properties=Mockito.mock(Properties.class);
				 Whitebox.setInternalState(PropertyUtil.class, "logger", logger);
				 Whitebox.setInternalState(PropertyUtil.class, "properties", properties);
			 }
				 
		
			 
			 @Test
			 public void getTBConditionsCodes_test() throws Exception{
				 Mockito.when(propertyUtil.isValid()).thenReturn(this.isValid);
				 Mockito.when(properties.getProperty("TB_CONDITION_CODES")).thenReturn(this.value);
				 String actualResult = Whitebox.invokeMethod(propertyUtil, "getTBConditionsCodes");
				 Assert.assertEquals(this.expectedValue,actualResult);
			 	 System.out.println("Method:getTBConditionsCodes, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+actualResult+", RESULT::::PASSED"); 

			 }
			 
			 
		
			
		}
	
	
	
	
	



	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class IsTBCode_test{
		
		int iteration;
		String conditionCode;
	    boolean expectedValue;
		String code1;
		String code2;
		 
		@Mock
		Properties properties;
		
		@Mock
		LogUtils logger;
		
		@InjectMocks
		PropertyUtil propertyUtil;
		
		
			public IsTBCode_test(int iteration, String conditionCode, boolean expectedValue, String code1, String code2){
				
				this.iteration = iteration;
				this.conditionCode = conditionCode;
				this.expectedValue = expectedValue;
				this.code1 = code1;
				this.code2 = code2;
				
			}
			
			
			 @Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {1, "102201", true, "102201", "502582"},
			    		  {2, "102201", true, "102201", ""},
			    		  {3, "502582", true, "102201", "502582"},
			    		  {4, "502582", true, "", "502582"},
			    		  {5, "502582", false, "102201", ""},
			    		  {6, "102201", false, "502582", ""},
			    		  {7, "102201", false, null, ""},
			    		  {8, "102201", false, "", null},
			    		  {9, null, false, "102201", "502582"},
			    		  
			    		  
			    		  
			  
			       });
			   }
			 
			 
			 @Before
			 public void initMocks() throws Exception {
				 logger = Mockito.mock(LogUtils.class);
				 propertyUtil = Mockito.spy(PropertyUtil.class);
				 properties=Mockito.mock(Properties.class);
				 Whitebox.setInternalState(PropertyUtil.class, "logger", logger);
				 Whitebox.setInternalState(PropertyUtil.class, "properties", properties);
			 }
				 
		
			 
			 @Test
			 public void isTBCode_test() throws Exception{
				 
			
				 
				ArrayList<Object> cachedTBList= new ArrayList<Object>();
				 
				if(code1!=null)
					cachedTBList.add(code1);
				
				if(code2!=null)
					cachedTBList.add(code2);
				
				
				Whitebox.setInternalState(PropertyUtil.class,"cachedTBList", cachedTBList);
				//PropertyUtil testSpy = PowerMockito.spy(new PropertyUtil());
				
				propertyUtil  = PowerMockito.spy(new PropertyUtil());
				PowerMockito.spy(PropertyUtil.class);
				PowerMockito.doNothing().when(PropertyUtil.class, "cachedTbConditionCodes");


				 boolean actualResult = Whitebox.invokeMethod(propertyUtil, "isTBCode", conditionCode);
				 Assert.assertEquals(this.expectedValue,actualResult);
			 	 System.out.println("Method:getTBConditionsCodes, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+actualResult+", RESULT::::PASSED"); 

			 }
			 
			 
		
			
		}
	
	
	
	


	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class CachedTbConditionCodes_test{
		
		int iteration;
	    int expectedValue;
		String codes;
		 
		@Mock
		Properties properties;
		
		@Mock
		LogUtils logger;
		
		@InjectMocks
		PropertyUtil propertyUtil;
		
		
			public CachedTbConditionCodes_test(int iteration, int expectedValue, String codes){
				
				this.iteration = iteration;
				this.expectedValue = expectedValue;
				this.codes = codes;
				
			}
			
			
			 @Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  {1, 2,"102201,502582"},
			    		  {2, 1, "102201"},
			    		  {3, 4, "102201,502582,102202,502583"},
			    		  {4, 0, ""}
			    		  
			    		  
			    		  
			    		  
			  
			       });
			   }
			 
			 
			 @Before
			 public void initMocks() throws Exception {
				 logger = Mockito.mock(LogUtils.class);
				 propertyUtil = Mockito.spy(PropertyUtil.class);
				 properties=Mockito.mock(Properties.class);
				 Whitebox.setInternalState(PropertyUtil.class, "logger", logger);
				 Whitebox.setInternalState(PropertyUtil.class, "properties", properties);
			 }
				 
		
			 
			 @Test
			 public void cachedTbConditionCodes_test() throws Exception{
				 
		
				 
				propertyUtil  = PowerMockito.spy(new PropertyUtil());
				PowerMockito.spy(PropertyUtil.class);
				PowerMockito.doReturn(propertyUtil).when(PropertyUtil.class, "getInstance");//.thenReturn(propertyUtil);

				 PowerMockito.when(propertyUtil.getTBConditionsCodes()).thenReturn(codes);
				 ArrayList<Object> cachedTBList= new ArrayList<Object>();
				 Whitebox.setInternalState(PropertyUtil.class,"cachedTBList", cachedTBList);
				 Whitebox.invokeMethod(propertyUtil, "cachedTbConditionCodes");
				 
				 int actualSize = cachedTBList.size();
				 Assert.assertEquals(expectedValue,actualSize);
			 	 System.out.println("Method:cachedTbConditionCodes, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+actualSize+", RESULT::::PASSED"); 

			 }
			 
			 
		
			
		}
	
	
	
	
	
		
}
