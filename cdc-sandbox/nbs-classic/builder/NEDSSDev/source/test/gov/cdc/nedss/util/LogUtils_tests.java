package test.gov.cdc.nedss.util;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
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



@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class LogUtils_tests {


/**
 * SetLogLevel_test: this test method verifies the methods 	timesCalledReconfiguredLevel and timesCalledSetRooLevel have been called once.
 * @author Fatima.Lopezcalzado
 *
 */
	
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","org.apache.logging.log4j.core.config.Configurator"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({LogUtils.class, Configurator.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class SetLogLevel_test {

	private String iteration;
	private String fileName;
	private int newLevel;
	private int timesCalledReconfiguredLevel;
	private int timesCalledSetRooLevel;
	
	@Mock
	Configurator configurator;

	@InjectMocks
	LogUtils logUtils;

	
	static final Level Levels[] = { Level.OFF, // 0 LowestLevel
		Level.FATAL, // 1
		Level.ERROR, // 2
		Level.WARN, // 3
		Level.INFO, // 4
		Level.DEBUG, // 5
		Level.ALL // 6 HighestLevel
};
	
	
	
	public SetLogLevel_test(String iteration, String fileName, int newLevel, int timesCalledReconfiguredLevel, int timesCalledSetRooLevel){
		

		this.iteration=iteration;
		this.fileName = fileName;
		this.newLevel = newLevel;
		this.timesCalledReconfiguredLevel = timesCalledReconfiguredLevel;
		this.timesCalledSetRooLevel = timesCalledSetRooLevel;
		
		
	}
	
	
	 
	 @Parameterized.Parameters
	   public static Collection input() {
	      return Arrays.asList(new Object[][]{
	    		  
	    		  //Workflow that calls Configurator.reconfigure(uri);
	    		  {"1", "source\\Configuration Files\\LogConfig\\LOG_CONFIG_ALL.xml", 1, 1, 0},
	    		  {"2", "source\\Configuration Files\\LogConfig\\LOG_CONFIG_ALL.xml", 2, 1, 0},
	    		  {"3", "source\\Configuration Files\\LogConfig\\LOG_CONFIG_ALL.xml", 3, 1, 0},
	    		  {"4", "source\\Configuration Files\\LogConfig\\LOG_CONFIG_ALL.xml", 4, 1, 0},
	    		  {"5", "source\\Configuration Files\\LogConfig\\LOG_CONFIG_ALL.xml", 5, 1, 0},
	    		  {"6", "source\\Configuration Files\\LogConfig\\LOG_CONFIG_ALL.xml", 6, 1, 0},
	    		  
	    		  //Workflow that calls Configurator.setRootLevel(Levels[currentLevel]);
	    		  {"7", "fileDoesntExist", 1, 0 , 1},
	    		  {"8", "fileDoesntExist", 2, 0 , 1},
	    		  {"9", "fileDoesntExist", 3, 0 , 1},
	    		  {"10", "fileDoesntExist", 4, 0 , 1},
	    		  {"11", "fileDoesntExist", 5, 0 , 1},
	    		  {"12", "fileDoesntExist", 6, 0 , 1}
	    		  
	    		  
	    		  
	       });
	   }
	 
	 
	 @Before
	 public void initMocks() throws Exception {
		 logUtils = Mockito.mock(LogUtils.class);
		 configurator = Mockito.mock(Configurator.class);
		 PowerMockito.spy(LogUtils.class);

	 }
		 

	 @Test
	 public void setLogLevel_test() throws Exception { 
		 
			System.out.println("******************* Starting test case named: setLogLevel *******************");

			System.out.println("Level set to: "+newLevel);
			
			File file = new File(fileName);
			URI uri = file.toURI();
			

			PowerMockito.mockStatic(Configurator.class);

			PowerMockito.doNothing().when(Configurator.class, "reconfigure", uri);	
			PowerMockito.doNothing().when(Configurator.class, "setRootLevel", Levels[newLevel]);	

			PowerMockito.doReturn(fileName).when(LogUtils.class, "getLogLevelConfigurationFilename");
		//	PowerMockito.doNothing().when(Configurator.class, "reconfigure", uri);
			
		
			Whitebox.setInternalState(LogUtils.class, "Levels", Levels);

			//The call
			Whitebox.invokeMethod(logUtils, "setLogLevel", newLevel);

			//The verification: verifying the method reconfigure is called N times (1 if we want the test case to pass)
			PowerMockito.verifyStatic(Mockito.times(timesCalledReconfiguredLevel)); // Verify that the following mock method was called exactly 1 time
			Configurator.reconfigure(uri);
		
			
			//The verification: verifying the method setRootLevel is called N times (1 if we want the test case to pass)
			PowerMockito.verifyStatic(Mockito.times(timesCalledSetRooLevel)); // Verify that the following mock method was called exactly 1 time
			Configurator.setRootLevel(Levels[newLevel]);
			
			
			//if we get to this point, that means the previous verification about how many times the methods have been called PASSED
			
			System.out.println("Iteration: #"+iteration+"\nReconfigure method has been called: "+this.timesCalledReconfiguredLevel
					+"\nSetRootLevel method has been called: "+this.timesCalledSetRooLevel);
			
	
			
			System.out.println("PASSED");
			System.out.println("******************* End test case named: setLogLevel *******************");

	 }


}
		
	
}